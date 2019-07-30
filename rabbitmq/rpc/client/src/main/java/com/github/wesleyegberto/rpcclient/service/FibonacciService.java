package com.github.wesleyegberto.rpcclient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.wesleyegberto.rpcclient.entity.Calculation;
import com.github.wesleyegberto.rpcclient.repository.CalculationRepository;

@Service
public class FibonacciService {
	private static final Logger LOG = LoggerFactory.getLogger(FibonacciService.class);

	private CalculationRepository calculationRepository;
	private RestTemplate restTemplate;
	private FibonacciQueuer queuer;

	@Autowired
	public FibonacciService(CalculationRepository calculationRepository, RestTemplate restTemplate,
			FibonacciQueuer queuer) {
		this.calculationRepository = calculationRepository;
		this.restTemplate = restTemplate;
		this.queuer = queuer;
	}

	public String createMessage(long number) {
		LOG.info("Creating message for {}", number);
		
		var calculation = Calculation.of(number);
		this.calculationRepository.save(calculation);

		try {
			this.queuer.calculateUsingMq(calculation);
			this.calculationRepository.updateResult(calculation);
		} catch (NonResponseException ex) {
			calculation.setTimedOut(ex.getMessage());
			this.calculationRepository.updateError(calculation);
		} catch (Exception ex) {
			calculation.setError(ex.getMessage());
			this.calculationRepository.updateError(calculation);
		}

		return "Here is your magic number: " + calculation.getResult();
	}

	public String getCalculationByCorrelationid(String correlationId) {
		return null;
	}

	@SuppressWarnings("unused")
	private long calculateUsingRest(long number) {
		LOG.info("Requesting Fibonacci API");
		Long result = this.restTemplate.getForEntity("http://localhost:8085/fibonacci/{number}", Long.class, number)
				.getBody();

		LOG.info("Received {} from API", result);
		if (result == null)
			return -1;

		return result;
	}

}
