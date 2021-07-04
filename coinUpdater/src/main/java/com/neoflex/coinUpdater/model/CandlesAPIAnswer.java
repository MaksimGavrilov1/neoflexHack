package com.neoflex.coinUpdater.model;

import lombok.Data;

import java.util.List;

@Data
public class CandlesAPIAnswer {
    private List<Candles> data;
    private Long timestamp;
}
