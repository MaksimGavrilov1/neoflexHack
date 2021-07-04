package com.neoflex.SOLIDbot.service;

import com.neoflex.SOLIDbot.model.Candles;
import com.neoflex.SOLIDbot.model.UserCandle;

import java.util.List;

public interface UserCandleService {
    List<Candles> getConnectedCandles(Long userId);

    void insertUserCandle(UserCandle userCandle);

    List<UserCandle> findAll();

    void delete(Long userId, Long candleId);
}
