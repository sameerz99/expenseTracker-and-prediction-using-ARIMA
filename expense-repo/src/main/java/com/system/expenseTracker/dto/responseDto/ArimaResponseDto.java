package com.system.expenseTracker.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArimaResponseDto {
    private List<Double> forecast;
    private List<Double> upperBound;
    private List<Double> lowerBound;
    private double maxNormalizedVariance;
    private double aic;
    private double rmse;
}
