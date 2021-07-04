package com.neoflex.SOLIDbot.repo;

import com.neoflex.SOLIDbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User, Long> {

    User findUserByUserTelegramId( Long userId);
}
