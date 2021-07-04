package com.neoflex.SOLIDbot.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "UserSubPercent")
public class UserSubPercent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "candle_symbol")
    private String candleSymbol;

    @Column(name = "price_percent")
    private Integer pricePercent;
}
