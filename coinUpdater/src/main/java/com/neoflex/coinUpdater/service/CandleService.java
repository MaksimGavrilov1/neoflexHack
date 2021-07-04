package com.neoflex.coinUpdater.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoflex.coinUpdater.model.Candles;
import com.neoflex.coinUpdater.model.CandlesAPIAnswer;
import com.neoflex.coinUpdater.repository.CandleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandleService {

    private final CandleRepo candleRepo;

    public void pushCandles() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String temp =  restTemplate.getForObject("https://api.coincap.io/v2/assets?ids=bitcoin,ethereum,binance-coin,dogecoin,polkadot,cardano", String.class);

        ObjectMapper mapper = new ObjectMapper();
        CandlesAPIAnswer candles = mapper.readValue(temp,CandlesAPIAnswer.class);

        for (int i=0;i<candles.getData().size();i++){
            Candles candle = candles.getData().get(i);
            candle.setCandleId(i+1);
            System.out.println(candle);
            candleRepo.save(candle);
        }
        System.out.println("updated");

    }
}
