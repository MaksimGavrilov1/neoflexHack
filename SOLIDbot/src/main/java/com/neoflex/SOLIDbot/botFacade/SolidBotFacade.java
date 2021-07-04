package com.neoflex.SOLIDbot.botFacade;

import com.neoflex.SOLIDbot.handler.MessageHandler;
import com.neoflex.SOLIDbot.handler.handlerImpl.allMessageHandler.AllMessageHandler;
import com.neoflex.SOLIDbot.handler.handlerImpl.defaultHandler.DefaultMessageHandler;
import com.neoflex.SOLIDbot.handler.handlerImpl.helpMessageHandler.HelpMessageHandler;
import com.neoflex.SOLIDbot.handler.handlerImpl.startHandler.StartMessageHandler;
import com.neoflex.SOLIDbot.handler.handlerImpl.subscribeMessageHandler.SubscribeMessageHandler;
import com.neoflex.SOLIDbot.handler.handlerImpl.subscribeWithPercentHandler.SubscribeWithPercentHandler;
import com.neoflex.SOLIDbot.handler.handlerImpl.unsubscribeMessageHandler.UnsubscribeMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

import static com.neoflex.SOLIDbot.constants.SolidBotConstants.PERCENT_COMMAND;

@Component
public class SolidBotFacade {

    final
    List<MessageHandler> handlers;

    public SolidBotFacade(List<MessageHandler> handlers) {
        this.handlers = handlers;
    }

    public SendMessage proccessMessage(Update update){
        SendMessage messageToReply = null;

        Message messageFromUser = update.getMessage();


        if (messageFromUser != null && messageFromUser.hasText()){
            messageToReply = this.handleInputMessage(messageFromUser);
        }
        return messageToReply;
    }

    private SendMessage handleInputMessage(Message message){
        String messageText = message.getText();
        Long chatId = message.getChat().getId();
        SendMessage messageToReply = null;

        List<String> messageElements = Arrays.asList(messageText.split("\\s+"));


        if (messageElements.size() == 3 && messageElements.get(0).equals(PERCENT_COMMAND)){
            messageToReply = handlers.stream()
                    .filter(x->x.getClass().equals(SubscribeWithPercentHandler.class))
                    .findFirst()
                    .orElse(new DefaultMessageHandler())
                    .handleMessage(message);
            return messageToReply;
        }

        if (messageText.contains("/subscribe")){

            messageToReply = handlers.stream()
                    .filter(x->x.getClass().equals(SubscribeMessageHandler.class))
                    .findFirst()
                    .orElse(new DefaultMessageHandler())
                    .handleMessage(message);
            return messageToReply;
        }
        if (messageText.contains("/unsubscribe")){

            messageToReply = handlers.stream()
                    .filter(x->x.getClass().equals(UnsubscribeMessageHandler.class))
                    .findFirst()
                    .orElse(new DefaultMessageHandler())
                    .handleMessage(message);
            return messageToReply;
        }

        switch (messageText){
            case "/start":

                messageToReply = handlers.stream()
                        .filter(x->x.getClass().equals(StartMessageHandler.class))
                        .findFirst()
                        .orElse(new DefaultMessageHandler())
                        .handleMessage(message);
                break;
            case "/help":
                messageToReply = handlers.stream()
                        .filter(x->x.getClass().equals(HelpMessageHandler.class))
                        .findFirst()
                        .orElse(new DefaultMessageHandler())
                        .handleMessage(message);
                break;
            case "/all":
                messageToReply = handlers.stream()
                        .filter(x->x.getClass().equals(AllMessageHandler.class))
                        .findFirst()
                        .orElse(new DefaultMessageHandler())
                        .handleMessage(message);
                break;

            default:
                System.out.println("default");
                messageToReply = new SendMessage(chatId.toString(), "Команда не распознана");
                break;
        }
        return messageToReply;
    }
}
