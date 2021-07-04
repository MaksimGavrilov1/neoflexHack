package com.neoflex.coinUpdater.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neoflex.coinUpdater.service.CandleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CandlePushController {

    private final CandleService candleService;

    @RequestMapping(method = RequestMethod.GET)
    public void sendCandles() throws JsonProcessingException {
        candleService.pushCandles();
    }

}
