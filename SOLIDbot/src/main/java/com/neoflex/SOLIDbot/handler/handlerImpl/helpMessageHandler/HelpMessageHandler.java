package com.neoflex.SOLIDbot.handler.handlerImpl.helpMessageHandler;

import com.neoflex.SOLIDbot.handler.MessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.neoflex.SOLIDbot.constants.SolidBotConstants.SOLID_HELP;

@Component
public class HelpMessageHandler implements MessageHandler {
    @Override
    public SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();
        return new SendMessage(chatId.toString(), SOLID_HELP);
    }

}
