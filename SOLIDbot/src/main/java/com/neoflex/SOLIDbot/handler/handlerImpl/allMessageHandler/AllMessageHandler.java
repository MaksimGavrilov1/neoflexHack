package com.neoflex.SOLIDbot.handler.handlerImpl.allMessageHandler;

import com.neoflex.SOLIDbot.dto.CandleDto;
import com.neoflex.SOLIDbot.handler.MessageHandler;
import com.neoflex.SOLIDbot.model.Candles;
import com.neoflex.SOLIDbot.service.serviceImpl.CandleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AllMessageHandler implements MessageHandler {

    private final CandleServiceImpl candleService;

    @Override
    public SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();

        StringBuilder sb = new StringBuilder();
        for (Candles candle : candleService.getAllCandles()){
            sb.append(CandleDto.candlesToDto(candle).toString());

        }

        return new SendMessage(chatId.toString(),sb.toString());
    }

}
