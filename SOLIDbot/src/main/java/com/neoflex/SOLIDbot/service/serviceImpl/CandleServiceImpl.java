package com.neoflex.SOLIDbot.service.serviceImpl;

import com.neoflex.SOLIDbot.model.Candles;
import com.neoflex.SOLIDbot.repo.CandleRepo;
import com.neoflex.SOLIDbot.service.CandleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandleServiceImpl implements CandleService {

    private final CandleRepo candleRepo;

    @Override
    public List<Candles> getAllCandles() {
        return candleRepo.findAll();
    }

    @Override
    public Candles findByCandleId(Long candleId) {
        return candleRepo.findCandlesByCandleId(candleId);
    }

    @Override
    public Candles findBySymbol(String symbol) {
        return candleRepo.findCandlesBySymbol(symbol);
    }

}
