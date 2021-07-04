package com.neoflex.SOLIDbot.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId;

    @Column(name = "user_id")
    private Long userTelegramId;

}
