package com.neoflex.SOLIDbot.handler.handlerImpl.unsubscribeMessageHandler;

import com.neoflex.SOLIDbot.handler.MessageHandler;
import com.neoflex.SOLIDbot.model.Candles;
import com.neoflex.SOLIDbot.model.User;
import com.neoflex.SOLIDbot.model.UserCandle;
import com.neoflex.SOLIDbot.repo.CandleRepo;
import com.neoflex.SOLIDbot.repo.UserRepo;
import com.neoflex.SOLIDbot.service.UserCandleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UnsubscribeMessageHandler implements MessageHandler {
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

        //handle unsubscribe with lower case messages
        String symbol = messageElements.get(1).toUpperCase(Locale.ROOT);

        for (Candles candle : candleRepo.findAll()) {

            if (symbol.equals(candle.getSymbol())) {
                requiredCandle = candle;

            }
        }
        User user = userRepo.findUserByUserTelegramId(userId);

        if (user != null) {
            for (Candles candle : userCandleService.getConnectedCandles(user.getUId())) {
                if (symbol.equals(candle.getSymbol())) {
                    userCandleService.delete(user.getUId(),candle.getCandleId());
                    return new SendMessage(chatId.toString(), "Вы успешно отписались");
                }
            }

        }
        return new SendMessage(chatId.toString(), "Вы не подписаны на эту валюту");


    }



}
