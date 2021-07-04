package com.neoflex.SOLIDbot.handler.handlerImpl.subscribeMessageHandler;

import com.neoflex.SOLIDbot.handler.MessageHandler;
import com.neoflex.SOLIDbot.model.Candles;
import com.neoflex.SOLIDbot.model.User;
import com.neoflex.SOLIDbot.model.UserCandle;
import com.neoflex.SOLIDbot.repo.CandleRepo;
import com.neoflex.SOLIDbot.repo.UserCandleRepo;
import com.neoflex.SOLIDbot.repo.UserRepo;
import com.neoflex.SOLIDbot.service.UserCandleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SubscribeMessageHandler implements MessageHandler {

    private final UserRepo userRepo;
    private final CandleRepo candleRepo;
    private final UserCandleService userCandleService;

    @Override
    public SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();
        List<String> messageElements = Arrays.asList(message.getText().split("\\s+"));
        Long userId = message.getFrom().getId();

        Candles requiredCandle = null;

        if (messageElements.size() == 1){
            return new SendMessage(chatId.toString(), "В команде необходимо указать от какой валюты вы хотите отписаться");
        }
        String symbol =  messageElements.get(1).toUpperCase(Locale.ROOT);
        for (Candles candle : candleRepo.findAll()) {

            if (symbol.equals(candle.getSymbol())) {
                requiredCandle = candle;

            }
        }

        if (requiredCandle == null){
            return  new SendMessage(chatId.toString(), "Валюта не найдена!");
        }

        List<User> users = userRepo.findAll();

        User user = userRepo.findUserByUserTelegramId(userId);

        if (user != null) {
            for (Candles candle : userCandleService.getConnectedCandles(user.getUId())) {
                if (symbol.equals(candle.getSymbol())) {
                    return new SendMessage(chatId.toString(), "Вы уже подписаны");
                }
            }

            UserCandle newUserCandle = new UserCandle();
            newUserCandle.setCandleName(requiredCandle.getCandleId());
            newUserCandle.setUserId(user.getUId());
            userCandleService.insertUserCandle(newUserCandle);
            return new SendMessage(chatId.toString(), "Подписка успешно оформлена");
        } else {
            User newUser = new User();
            newUser.setUserTelegramId(userId);

            userRepo.save(newUser);
            User userFromDb = userRepo.findUserByUserTelegramId(userId);

            UserCandle newUserCandle = new UserCandle();
            newUserCandle.setCandleName(requiredCandle.getCandleId());
            newUserCandle.setUserId(userFromDb.getUId());

            userCandleService.insertUserCandle(newUserCandle);
            return new SendMessage(chatId.toString(), "Подписка успешно оформлена!");
        }


    }


}
