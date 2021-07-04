package com.neoflex.coinUpdater.repository;

import com.neoflex.coinUpdater.model.Candles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandleRepo extends JpaRepository<Candles,Integer> {
}
