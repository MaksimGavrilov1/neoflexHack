package com.neoflex.SOLIDbot.service.serviceImpl;

import com.neoflex.SOLIDbot.model.Candles;
import com.neoflex.SOLIDbot.model.UserCandle;
import com.neoflex.SOLIDbot.repo.CandleRepo;
import com.neoflex.SOLIDbot.repo.UserCandleRepo;
import com.neoflex.SOLIDbot.repo.UserRepo;
import com.neoflex.SOLIDbot.service.UserCandleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCandlesImpl implements UserCandleService {

    private final UserRepo userRepo;
    private final CandleRepo candleRepo;
    private final UserCandleRepo userCandleRepo;

    @Override
    public List<Candles> getConnectedCandles(Long userId) {
        List<UserCandle> candlesId = userCandleRepo.findUserCandleByUserId(userId);
        System.out.print(candlesId);
        System.out.println("- user candlesID");
        List<Candles> candles = new ArrayList<>();
        for (UserCandle candle : candlesId) {
            candles.add(candleRepo.findCandlesByCandleId(candle.getCandleName()));

        }

        return candles;
    }

    @Override
    public void insertUserCandle(UserCandle userCandle) {
        userCandleRepo.save(userCandle);
    }

    @Override
    public List<UserCandle> findAll() {
        return userCandleRepo.findAll();
    }

    @Override
    public void delete(Long userId, Long candleId) {
        for(UserCandle candle : userCandleRepo.findUserCandleByUserId(userId)){
            if (candle.getCandleName().equals(candleId)){
                userCandleRepo.delete(candle);
            }
        }
    }
}
