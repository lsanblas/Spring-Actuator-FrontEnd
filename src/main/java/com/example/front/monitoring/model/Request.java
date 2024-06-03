package com.example.front.monitoring.model;

 public record Request(
     String uri,
     String method,
     Headers headers) {
}
