package com.neoflex.SOLIDbot.repo;

import com.neoflex.SOLIDbot.model.UserSubPercent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubPercentRepo extends JpaRepository<UserSubPercent, Long> {

    @Modifying
    @Query(value = "delete from user_sub_percent where user_id = :userId", nativeQuery = true)
    void deleteUserSubPercentByUserId(@Param(value = "userId")Long userId);


    void deleteUserSubPercentByUserIdAndCandleSymbolAndPricePercent(Long userId, String candleSymbol, Integer percent);


    UserSubPercent findUserSubPercentByUserIdAndAndCandleSymbolAndPricePercent(Long userId, String candleSymbol, Integer percent);

}
