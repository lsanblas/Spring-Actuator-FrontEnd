package com.example.front.monitoring;

import com.example.front.monitoring.model.ActuatorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MonitoringApplication {

//	final static ObjectMapper objectMapper = new ObjectMapper();

//	private static final Logger log = LoggerFactory.getLogger(MonitoringApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner runner() {
//		return args -> {
//			try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json")) {
//				ActuatorData data = objectMapper.readValue(inputStream, ActuatorData.class);
//				System.out.println("Reading exchanges from JSON data" + data.exchanges().size());
//			} catch (IOException e) {
//				throw new RuntimeException("Failed to read JSON data", e);
//			}
//		};
//	}

//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.build();
//	}
//
//	@Bean
//	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//		return args -> {
//			ActuatorData quote = restTemplate.getForObject(
//					"http://localhost:8081/actuator/httpexchanges", ActuatorData.class);
//			log.info(quote.toString());
//		};
//	}
}
