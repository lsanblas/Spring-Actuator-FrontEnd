package com.example.front.monitoring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record Headers (
    @JsonProperty("host")
     List<String> host,
     List<String> connection,
    @JsonProperty("sec-ch-ua")
     List<String> secChUa,
     List<String> accept,
    @JsonProperty("sec-ch-ua-mobile")
     List<String> secChUaMobile,
    @JsonProperty("user-agent")
     List<String> userAgent,
    @JsonProperty("sec-ch-ua-platform")
     List<String> secChUaPlatform,
    @JsonProperty("sec-gpc")
     List<String> secGpc,
    @JsonProperty("accept-language")
     List<String> acceptLanguage,
    @JsonProperty("sec-fetch-site")
     List<String> secFetchSite,
    @JsonProperty("sec-fetch-mode")
     List<String> secFetchMode,
    @JsonProperty("sec-fetch-dest")
     List<String> secFetchDest,
     List<String> referer,
    @JsonProperty("accept-encoding")
     List<String> acceptEncoding,
    @JsonProperty("content-length")
     List<String> contentLengthR,
    @JsonProperty("content-type")
     List<String> contentTypeR,
     List<String> origin,
    @JsonProperty("Content-Type")
     List<String> contentType,
    @JsonProperty("Transfer-Encoding")
     List<String> transferEncoding,
    @JsonProperty("Date")
     List<String> date,
    @JsonProperty("Keep-Alive")
     List<String> keepAlive,
    @JsonProperty("Connection")
     List<String> connectionResponse,
    @JsonProperty("Content-Length")
     List<String> contentLength) {
}
