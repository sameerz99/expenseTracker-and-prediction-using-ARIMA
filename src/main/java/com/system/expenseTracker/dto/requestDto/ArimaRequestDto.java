package com.system.expenseTracker.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArimaRequestDto {
    private int forecastPeriod;
    private List<Double> tsData;
}
