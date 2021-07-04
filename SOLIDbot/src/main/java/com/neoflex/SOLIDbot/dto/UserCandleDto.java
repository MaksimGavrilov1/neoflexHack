package com.neoflex.SOLIDbot.dto;

import lombok.Data;

@Data
public class UserCandleDto {

    private Long userTelegramId;
    private Long candleId;

    private String symbol;
    private String name;
    private Double price;

    @Override
    public String toString() {
        return String.format("Name: %s\t  FIGI: %s\t  Price: %.2f$ \n\n",this.name,this.symbol,this.price);
    }
}
