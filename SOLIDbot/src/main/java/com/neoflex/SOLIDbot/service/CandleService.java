package com.neoflex.SOLIDbot.service;

import com.neoflex.SOLIDbot.model.Candles;

import java.util.List;

public interface CandleService {
    List<Candles> getAllCandles();

    Candles findByCandleId(Long candleId);

    Candles findBySymbol(String symbol);
}
