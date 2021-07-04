package com.neoflex.SOLIDbot.repo;

import com.neoflex.SOLIDbot.model.Candles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandleRepo extends JpaRepository<Candles, Integer> {
    Candles findCandlesByCandleId(Long candleId);
    Candles findCandlesBySymbol(String symbol);
}
