package com.piyush.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to) {
		logger.info("\n Retrieve exchange value from {} to {}", from, to);
//		CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, 
//				BigDecimal.valueOf(90));
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("unable to Find the Data for" + from + "to" + to);
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		
		return currencyExchange;
	}

}
