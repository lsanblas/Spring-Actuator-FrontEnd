package com.example.front.monitoring.model;

import org.springframework.data.domain.Page;

import java.util.List;

public record ActuatorResponse(Integer ok, Integer notFound, Integer badRequest, Integer internal, ActuatorData data) {
}
