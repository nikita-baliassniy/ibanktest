package ru.netology.steps.rest;

import static io.restassured.RestAssured.given;
import static ru.netology.steps.AbstractStepsHolder.evalVariable;
import static ru.netology.steps.AbstractStepsHolder.getVariable;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Request {

	public static Response sendPOST(String url, String body, ContentType contentType) {
		log.info("Request: POST to " + url);
		if (getVariable(body) != null) {
			body = getVariable(body).toString();
		}
		log.info("Request BODY: " + evalVariable(body).toString());
		Response response = given().contentType(contentType).body(evalVariable(body).toString()).
				when().post(url);
		return response;
	}
}
