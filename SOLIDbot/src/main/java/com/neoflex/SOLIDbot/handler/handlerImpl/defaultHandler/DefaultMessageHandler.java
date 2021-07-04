package com.neoflex.SOLIDbot.handler.handlerImpl.defaultHandler;

import com.neoflex.SOLIDbot.handler.MessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DefaultMessageHandler implements MessageHandler {

    @Override
    public SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();
        return new SendMessage(chatId.toString(), "This function not supported");
    }

}
