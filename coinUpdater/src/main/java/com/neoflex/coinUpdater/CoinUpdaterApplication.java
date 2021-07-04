package com.neoflex.coinUpdater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoflex.coinUpdater.model.CandlesAPIAnswer;
import com.neoflex.coinUpdater.repository.CandleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootApplication
public class CoinUpdaterApplication {

	public static void main(String[] args){
		System.getProperties().put( "server.port", 8181 );
		SpringApplication.run(CoinUpdaterApplication.class, args);
	}

}
