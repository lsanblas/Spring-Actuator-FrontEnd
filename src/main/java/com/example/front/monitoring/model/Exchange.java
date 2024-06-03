package com.example.front.monitoring.model;

public record Exchange(
        String timestamp,
        Request request,
        Response response,
        String timeTaken) {
}
