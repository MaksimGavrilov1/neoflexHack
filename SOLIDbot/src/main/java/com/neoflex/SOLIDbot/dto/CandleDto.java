package com.neoflex.SOLIDbot.dto;

import com.neoflex.SOLIDbot.model.Candles;

import javax.persistence.Column;

public class CandleDto {

    public CandleDto(String symbol, String name, Double priceUsd) {
        this.symbol = symbol;
        this.name = name;
        this.priceUsd = priceUsd;
    }

    private String symbol;
    private String name;
    private Double priceUsd;

    public static CandleDto candlesToDto(Candles candle){

        CandleDto dto = new CandleDto(candle.getSymbol(),candle.getName(),candle.getPriceUsd());

        return dto;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\t  FIGI: %s\t  Price: %.2f$ \n\n",this.name,this.symbol,this.priceUsd);
    }
}
