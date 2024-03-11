package com.project.arima.controllers;



import com.project.arima.analytics.Arima;
import com.project.arima.models.ForecastResultModel;
import com.project.arima.models.TimeSeriesModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/j-arima")
public class JArimaController {

	@RequestMapping(
			value = "/predict",
			method = RequestMethod.POST)
	public ForecastResultModel calculateRArima(
			 @RequestBody TimeSeriesModel rArima)
					throws Exception {

		ForecastResultModel forecastResult = Arima.forecast_arima(
				rArima.getTSData(), rArima.getForecastPeriod());

		return forecastResult;
	}
}
