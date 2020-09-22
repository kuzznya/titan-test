package com.github.kuzznya.titantest.web;

import lombok.Data;

@Data
public class CalculationRequest {
    private String function1;
    private String function2;
    private int count;
}
