package com.neoflex.SOLIDbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Data
@Entity
public class Candles {

    @Id
    @Column(name = "candle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candleId;

    @Column(name = "id")
    private String id;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name")
    private String name;

    @Column(name = "supply")
    private Double supply;

    @Column(name = "max_supply")
    private Double maxSupply;

    @Column(name = "market_cap_usd")
    private Double marketCapUsd;

    @Column(name = "volume_usd_24_hr")
    private Double volumeUsd24Hr;

    @Column(name = "price_usd")
    private Double priceUsd;

    @Column(name = "change_percent_24_hr")
    private Double changePercent24Hr;

    @Column(name = "vwap_24_Hr")
    private Double vwap24Hr;

    @Column(name = "explorer")
    private String explorer;

}
