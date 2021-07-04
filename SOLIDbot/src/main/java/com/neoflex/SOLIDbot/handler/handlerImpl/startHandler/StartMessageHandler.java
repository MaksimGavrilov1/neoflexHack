package com.neoflex.SOLIDbot.handler.handlerImpl.startHandler;

import com.neoflex.SOLIDbot.handler.MessageHandler;
import com.neoflex.SOLIDbot.threads.ScheduledSubscribeThread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.neoflex.SOLIDbot.constants.SolidBotConstants.SOLID_GREETING;

@Component
@RequiredArgsConstructor
public class StartMessageHandler implements MessageHandler {

    private final ScheduledSubscribeThread thread;

    @Override
    public SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();

        return new SendMessage(chatId.toString(), SOLID_GREETING);
    }


}
