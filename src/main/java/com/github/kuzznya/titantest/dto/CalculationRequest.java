package com.github.kuzznya.titantest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationRequest {
    private String function1;
    private String function2;
    private int count;
}
