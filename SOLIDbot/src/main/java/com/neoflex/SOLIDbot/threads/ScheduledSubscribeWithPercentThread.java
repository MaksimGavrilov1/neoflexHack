package com.neoflex.SOLIDbot.threads;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class ScheduledSubscribeWithPercentThread {
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public void startSend(Runnable action) {

        final ScheduledFuture<?> messageSender =
                scheduler.scheduleAtFixedRate(action, 0, 3, SECONDS);

    }
}
