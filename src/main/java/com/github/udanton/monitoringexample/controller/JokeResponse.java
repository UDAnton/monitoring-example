package com.github.udanton.monitoringexample.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class JokeResponse {
    private String id;
    private String text;
}
