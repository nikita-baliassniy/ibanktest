package ru.netology.steps.rest;

import static ru.netology.steps.AbstractStepsHolder.evalVariable;


import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class ApiSteps {

	ApiScenarioSteps apiScenarioSteps = new ApiScenarioSteps();

	@When("выполнен POST - запрос по url - \"(.*)\" c телом \"(.*)\"")
	public void stepSendPost(String url, String body) {
		apiScenarioSteps.sendPOST(evalVariable(url), body);
	}

	@And("и код ответа на запрос равен \"(.*)\"")
	public void stepSendPost(String code) {
		apiScenarioSteps.checkResponseCode(code);
	}
}
