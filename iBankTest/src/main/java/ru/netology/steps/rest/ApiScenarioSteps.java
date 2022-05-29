package ru.netology.steps.rest;

import static org.junit.Assert.assertEquals;


import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.netology.steps.AbstractStepsHolder;

public class ApiScenarioSteps extends AbstractStepsHolder {

	@Step
	public void sendPOST(String url, String body) {
		Response response = Request.sendPOST(url, body, ContentType.JSON);
		setVariable("responseСode", response.getStatusCode());
	}

	@Step
	public void checkResponseCode(String code) {
		assertEquals(String.format("Статус ответа [%s] не соответствует ожидаемому [%s]",
				evalVariable("responseСode"), code), code, evalVariable("#{responseСode}"));
	}
}
