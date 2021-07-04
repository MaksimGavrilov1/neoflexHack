package com.neoflex.SOLIDbot.dto;

import lombok.Data;

@Data
public class UserSubWithPercentCandleDto {

    private String candleName;
    private Long userTelegramId;
    private Double candlePriceUsd;
    private Integer percent;
    private String candleSymbol;

    @Override
    public String toString() {
        return String.format("Стоимость валюты %s упала на %d%%",candleName, percent);
    }
}
