package com.neoflex.SOLIDbot.botConfig;

import com.neoflex.SOLIDbot.SolidBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class SolidBotConfig {

    private String webhookPath;
    private String botUsername;
    private String botToken;

    @Bean
    public SolidBot getSolidBot(){
        DefaultBotOptions options = new DefaultBotOptions();
        SolidBot bot = new SolidBot(options);
        bot.setBotToken(botToken);
        bot.setWebhookPath(webhookPath);
        bot.setBotUsername(botUsername);
        return bot;
    }
}
