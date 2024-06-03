package com.example.front.monitoring.model;

import org.springframework.data.domain.Page;

public record ActuatorResponsePaginated(Integer ok, Integer notFound, Integer badRequest, Integer internal, Page<Exchange> exchanges) {
}