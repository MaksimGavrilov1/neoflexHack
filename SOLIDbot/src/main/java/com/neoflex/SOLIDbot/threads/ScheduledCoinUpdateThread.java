package com.neoflex.SOLIDbot.threads;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class ScheduledCoinUpdateThread {
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public void updateCoins() {

        final Runnable action = new Runnable() {
            @Override
            public void run() {
                HttpClient httpclient = HttpClients.createDefault();
                HttpGet get = new HttpGet("http://localhost:8181/");
                try {
                    httpclient.execute(get);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        final ScheduledFuture<?> coinUpdater =
                scheduler.scheduleAtFixedRate(action, 0, 10, SECONDS);

    }
}
