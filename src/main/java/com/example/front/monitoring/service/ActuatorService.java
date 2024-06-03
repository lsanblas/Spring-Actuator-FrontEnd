package com.example.front.monitoring.service;

import com.example.front.monitoring.model.ActuatorData;
import com.example.front.monitoring.model.ActuatorResponse;
import com.example.front.monitoring.model.ActuatorResponsePaginated;
import com.example.front.monitoring.model.Exchange;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ActuatorService {

    private static final Logger log = LoggerFactory.getLogger(ActuatorService.class);
    private static final int OK = 200;
    private static final int BAD_REQUEST = 400;
    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_ERROR = 500;

    @Value("${spring.rest.actuator.url}")
    private String url;

    // USE ONLY FOR TESTING WITHOUT THE BACKEND SERVICES
    //private final static ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    public ActuatorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ActuatorResponse getActuatorRequestsData(){
        log.info("Calling Actuator Service");
        ActuatorData data = restTemplate.getForObject(url, ActuatorData.class);
        log.info("Data retrieve from Actuator Service");
        ActuatorResponse response = null;
        if(data != null){
            Map<Integer, Integer> responsesTotals = this.responsesTotals(data);
            response = new ActuatorResponse(
                    responsesTotals.get(OK) != null ? responsesTotals.get(OK) : 0,
                    responsesTotals.get(NOT_FOUND) != null ? responsesTotals.get(NOT_FOUND) : 0,
                    responsesTotals.get(BAD_REQUEST) != null ? responsesTotals.get(BAD_REQUEST) : 0,
                    responsesTotals.get(INTERNAL_ERROR) != null ? responsesTotals.get(INTERNAL_ERROR) : 0,
                    data
            );
        } else {
            log.info("Empty response found!!!");
            response = new ActuatorResponse(
                    0,
                    0,
                    0,
                    0,
                    data
            );
        }

        return response;
    }

    private Map<Integer, Integer> responsesTotals(ActuatorData data){
        Map<Integer, Integer> responses = new HashMap<>();
        for(Exchange exchange : data.exchanges()){
            Integer status = switch (exchange.response().status()) {
                case OK -> responses.put(OK, responses.get(OK) != null ? responses.get(OK) + 1 : 1);
                case BAD_REQUEST -> responses.put(BAD_REQUEST, responses.get(BAD_REQUEST) != null ? responses.get(BAD_REQUEST) + 1 : 1);
                case NOT_FOUND -> responses.put(NOT_FOUND, responses.get(NOT_FOUND) != null ? responses.get(NOT_FOUND) + 1 : 1);
                case INTERNAL_ERROR -> responses.put(INTERNAL_ERROR, responses.get(INTERNAL_ERROR) != null ? responses.get(INTERNAL_ERROR) + 1 : 1);
                default      -> {
                    log.info("Status {} of the request is not mapped", exchange.response().status());
                    yield 0;
                }
            };
        }
        return responses;
    }

    public ActuatorResponsePaginated getActuatorRequestsDataPaginated(Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        log.info("Calling Actuator Service");
        ActuatorData data = restTemplate.getForObject(url, ActuatorData.class);
        // USE ONLY FOR TESTING WITHOUT THE BACKEND SERVICES
        //ActuatorData data = this.getMockData();
        log.info("Data retrieve from Actuator Service");
        ActuatorResponsePaginated response = null;
        Page<Exchange> exchanges = null;
        if(data != null){
            if(data.exchanges().size() < startItem){
                log.info("startItem Exceed the request available !!!");
                exchanges = new PageImpl<Exchange>(Collections.emptyList(), PageRequest.of(currentPage, pageSize), data.exchanges().size());
            } else {
                int toIndex = Math.min(startItem + pageSize, data.exchanges().size());
                exchanges = new PageImpl<Exchange>(data.exchanges().subList(startItem, toIndex), PageRequest.of(currentPage, pageSize), data.exchanges().size());
            }
            Map<Integer, Integer> responsesTotals = this.responsesTotals(data);
            response = new ActuatorResponsePaginated(
                    responsesTotals.get(OK) != null ? responsesTotals.get(OK) : 0,
                    responsesTotals.get(NOT_FOUND) != null ? responsesTotals.get(NOT_FOUND) : 0,
                    responsesTotals.get(BAD_REQUEST) != null ? responsesTotals.get(BAD_REQUEST) : 0,
                    responsesTotals.get(INTERNAL_ERROR) != null ? responsesTotals.get(INTERNAL_ERROR) : 0,
                    exchanges
            );
        } else {
            log.info("Empty response found !!!");
            response = new ActuatorResponsePaginated(
                    0,
                    0,
                    0,
                    0,
                    new PageImpl<Exchange>(Collections.emptyList(), PageRequest.of(currentPage, pageSize), data.exchanges().size())
            );
        }
        return response;
    }

    // USE ONLY FOR TESTING WITHOUT THE BACKEND SERVICES
//    private ActuatorData getMockData(){
//        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json")) {
//            ActuatorData data = objectMapper.readValue(inputStream, ActuatorData.class);
//            log.info("Reading exchanges from JSON data. Total size {}", data.exchanges().size());
//            return data;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read JSON data", e);
//        }
//    }
}
