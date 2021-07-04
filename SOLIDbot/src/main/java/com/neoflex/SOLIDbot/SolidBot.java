package com.neoflex.SOLIDbot;

import com.neoflex.SOLIDbot.botFacade.SolidBotFacade;
import com.neoflex.SOLIDbot.dto.UserCandleDto;
import com.neoflex.SOLIDbot.dto.UserSubWithPercentCandleDto;
import com.neoflex.SOLIDbot.handler.handlerImpl.subscribeMessageHandler.SubscribeMessageHandler;
import com.neoflex.SOLIDbot.model.Candles;
import com.neoflex.SOLIDbot.model.User;
import com.neoflex.SOLIDbot.model.UserCandle;
import com.neoflex.SOLIDbot.model.UserSubPercent;
import com.neoflex.SOLIDbot.repo.UserRepo;
import com.neoflex.SOLIDbot.repo.UserSubPercentRepo;
import com.neoflex.SOLIDbot.service.CandleService;
import com.neoflex.SOLIDbot.service.UserCandleService;
import com.neoflex.SOLIDbot.threads.ScheduledCoinUpdateThread;
import com.neoflex.SOLIDbot.threads.ScheduledSubscribeThread;
import com.neoflex.SOLIDbot.threads.ScheduledSubscribeWithPercentThread;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

@Setter
public class SolidBot extends TelegramWebhookBot {

    private String botUsername;
    private String botToken;
    private String webhookPath;
    private boolean flag;

    private List<UserCandle> userCandleList;
    private List<User> users;
    private List<Candles> candles;

    @Autowired
    private UserCandleService userCandleService;
    @Autowired
    private UserSubPercentRepo subPercentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CandleService candleService;

    @Autowired
    private SolidBotFacade botFacade;
    @Autowired
    private ScheduledSubscribeThread subscribeThread;
    @Autowired
    private ScheduledCoinUpdateThread coinUpdateThread;
    @Autowired
    private ScheduledSubscribeWithPercentThread subscribeWithPercentThread;


    public SolidBot(DefaultBotOptions options) {
        super(options);

    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChat().getId();
        try {
            //start sending message and update coin
            if (update.getMessage().getText().equals("/start") && !flag){
                subscribeThread.startSend(new SendSubMessageAction());
                coinUpdateThread.updateCoins();
                subscribeWithPercentThread.startSend(new SendSubWithPercentMessageAction());
                flag = true;
            }

            execute(botFacade.proccessMessage(update));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public String getBotPath() {
        return webhookPath;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private class SendSubMessageAction implements Runnable {

        @Override
        public void run() {
            List<UserCandleDto> sendMessageInfo = getData();
            for (UserCandleDto userCandle : sendMessageInfo) {
                try {
                    execute(new SendMessage(userCandle.getUserTelegramId().toString(), userCandle.toString()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        private List<UserCandleDto> getData() {
            userCandleList = userCandleService.findAll();
            users = userRepo.findAll();
            candles = candleService.getAllCandles();

            List<UserCandleDto> userCandleDtoList = new ArrayList<>();

            //get connected candles and form a dto
            for (UserCandle userCandle : userCandleList) {
                UserCandleDto userCandleDto = new UserCandleDto();
                Candles tempCandle= candleService.findByCandleId(userCandle.getCandleName());
                userCandleDto.setUserTelegramId(userRepo.findById(userCandle.getUserId()).orElse(null).getUserTelegramId());
                userCandleDto.setCandleId(tempCandle.getCandleId());
                userCandleDto.setName(tempCandle.getName());
                userCandleDto.setSymbol(tempCandle.getSymbol());
                userCandleDto.setPrice(tempCandle.getPriceUsd());
                userCandleDtoList.add(userCandleDto);
            }
            return userCandleDtoList;
        }
    }
    private class SendSubWithPercentMessageAction implements Runnable {

        @Override
        public void run() {

            List<UserSubWithPercentCandleDto> sendMessageInfo = getData();
            for (UserSubWithPercentCandleDto userSubWithPercentCandleDto : sendMessageInfo) {
                BigDecimal price = BigDecimal.valueOf(userSubWithPercentCandleDto.getCandlePriceUsd());
                Double priceWithPercentDouble = userSubWithPercentCandleDto.getCandlePriceUsd() - userSubWithPercentCandleDto.getCandlePriceUsd() * userSubWithPercentCandleDto.getPercent();
                BigDecimal priceWithPercent = BigDecimal.valueOf(priceWithPercentDouble);
                if (price.compareTo(priceWithPercent) <= 0){
                    try {
                        execute(new SendMessage(userSubWithPercentCandleDto.getUserTelegramId().toString(), userSubWithPercentCandleDto.toString()));
                        UserSubPercent user = subPercentRepo.findUserSubPercentByUserIdAndAndCandleSymbolAndPricePercent(userSubWithPercentCandleDto.getUserTelegramId(),userSubWithPercentCandleDto.getCandleSymbol(),userSubWithPercentCandleDto.getPercent());
                        subPercentRepo.deleteById(user.getId());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private List<UserSubWithPercentCandleDto> getData() {

            List<Candles> candles = candleService.getAllCandles();
            List<UserSubPercent> usersSubsWithPercent = subPercentRepo.findAll();
            List<UserSubWithPercentCandleDto> result = new ArrayList<>();

            for (UserSubPercent userSubPercent : usersSubsWithPercent){
                for (Candles candle : candles){
                    if (userSubPercent.getCandleSymbol().equals(candle.getSymbol())){
                        UserSubWithPercentCandleDto dto = new UserSubWithPercentCandleDto();
                        dto.setCandleName(candle.getName());
                        dto.setUserTelegramId(userSubPercent.getUserId());
                        dto.setCandlePriceUsd(candle.getPriceUsd());
                        dto.setPercent(userSubPercent.getPricePercent());
                        dto.setCandleSymbol(candle.getSymbol());
                        result.add(dto);
                    }
                }
            }
            return result;
        }
    }
}
