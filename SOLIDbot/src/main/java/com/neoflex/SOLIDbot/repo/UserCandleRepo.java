package com.neoflex.SOLIDbot.repo;

import com.neoflex.SOLIDbot.model.UserCandle;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCandleRepo extends JpaRepository<UserCandle, Integer> {
    List<UserCandle> findUserCandleByUserId(Long userId);
}
