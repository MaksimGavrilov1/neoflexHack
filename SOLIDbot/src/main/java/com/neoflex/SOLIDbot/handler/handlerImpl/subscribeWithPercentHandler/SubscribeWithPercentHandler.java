package com.neoflex.SOLIDbot.handler.handlerImpl.subscribeWithPercentHandler;

import com.neoflex.SOLIDbot.handler.MessageHandler;
import com.neoflex.SOLIDbot.model.Candles;
import com.neoflex.SOLIDbot.model.User;
import com.neoflex.SOLIDbot.model.UserCandle;
import com.neoflex.SOLIDbot.model.UserSubPercent;
import com.neoflex.SOLIDbot.repo.UserSubPercentRepo;
import com.neoflex.SOLIDbot.service.CandleService;
import com.neoflex.SOLIDbot.service.serviceImpl.CandleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.neoflex.SOLIDbot.constants.SolidBotConstants.*;

@Component
@RequiredArgsConstructor
public class SubscribeWithPercentHandler implements MessageHandler {

    private final CandleService candleService;
    private final UserSubPercentRepo subPercentRepo;

    @Override
    public SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();
        Long userId = message.getFrom().getId();
        String messageText = message.getText();
        List<String> messageElements = Arrays.asList(message.getText().split("\\s+"));
        String candleSymbol = messageElements.get(SUB_WITH_PERCENT_SYMBOL_POS);
        Integer percent = Integer.parseInt(messageElements.get(SUB_WITH_PERCENT_PERCENT_POS));
        candleSymbol = candleSymbol.toUpperCase(Locale.ROOT);

        Candles requiredCandle = candleService.findBySymbol(candleSymbol);


        if (messageElements.size() <= 1) {
            return new SendMessage(chatId.toString(), SUB_WITH_PERCENT_WRONG_COMMAND_SYNTAX);
        }

        if (requiredCandle == null) {
            return new SendMessage(chatId.toString(), SUB_WITH_PERCENT_NO_CANDLE_WARNING);
        }

        if (percent <= 0) {
            return new SendMessage(chatId.toString(), SUB_WITH_PERCENT_PERCENT_NEG_WARNING);
        }

        UserSubPercent userSubPercentFromDb = subPercentRepo.findUserSubPercentByUserIdAndAndCandleSymbolAndPricePercent(userId, candleSymbol,percent);

        if (userSubPercentFromDb == null) {

            UserSubPercent userSubPercent = new UserSubPercent();
            userSubPercent.setUserId(userId);
            userSubPercent.setCandleSymbol(candleSymbol);
            userSubPercent.setPricePercent(percent);

            subPercentRepo.save(userSubPercent);

            return new SendMessage(chatId.toString(), String.format(SUB_WITH_PERCENT_SUCCESSFUL_SUB, candleSymbol, percent));
        }

        if (userSubPercentFromDb.getPricePercent().equals(percent)) {
            return new SendMessage(chatId.toString(), SUB_WITH_PERCENT_ALREADY_SUBSCRIBED);
        }


        UserSubPercent userSubPercent = new UserSubPercent();
        userSubPercent.setUserId(userId);
        userSubPercent.setCandleSymbol(candleSymbol);
        userSubPercent.setPricePercent(percent);

        subPercentRepo.save(userSubPercent);

        return new SendMessage(chatId.toString(), String.format(SUB_WITH_PERCENT_SUCCESSFUL_SUB, candleSymbol, percent));

    }


}
