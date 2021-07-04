package com.neoflex.SOLIDbot.threads;

import com.neoflex.SOLIDbot.model.Candles;
import com.neoflex.SOLIDbot.model.User;
import com.neoflex.SOLIDbot.model.UserCandle;
import com.neoflex.SOLIDbot.repo.UserRepo;
import com.neoflex.SOLIDbot.service.CandleService;
import com.neoflex.SOLIDbot.service.UserCandleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.meta.bots.AbsSender;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
@RequiredArgsConstructor
public class ScheduledSubscribeThread {

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public void startSend(Runnable action) {

        final ScheduledFuture<?> messageSender =
                scheduler.scheduleAtFixedRate(action, 0, 3, SECONDS);

    }
}
