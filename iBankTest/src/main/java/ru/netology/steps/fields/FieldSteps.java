package ru.netology.steps.fields;

import static org.junit.Assert.assertTrue;


import cucumber.api.DataTable;
import cucumber.api.java.en.When;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import ru.netology.steps.AbstractStepsHolder;

public class FieldSteps extends AbstractStepsHolder {

	FieldScenarioSteps fieldScenarioSteps = new FieldScenarioSteps();


	@When("^поле (.*) заполняется значением (.*)$")
	public void stepFillField(String field, String value) {
		fieldScenarioSteps.fillField(field, evalVariable(value));
	}

	@When("^заполняются поля:")
	public void stepFillFields(DataTable fields) {
		fields.asMap(String.class, String.class)
				.forEach((k, v) -> fieldScenarioSteps.fillField(k, evalVariable(v).toString()));
	}

	@When("выполнено нажатие на (.*)")
	public void stepClickField(String field) {
		fieldScenarioSteps.clickField(field);
	}

	@Тогда("^поле (.+) видимо$")
	public void stepFieldIsDisplayed(String fieldName) {
		boolean isPresent = fieldScenarioSteps.fieldIsPresent(fieldName);
		assertTrue("Поле " + fieldName + " не существует на странице", isPresent);
	}

	@Когда("^переменная \"([^\"]*)\" принимает значение \"([^\"]*)\"$")
	public void variableGetsValue(String variable, String value) {
		System.out.println(value);
		setVariable(variable, value);
	}

	@Когда("^переменная (.*) принимает значение из файла (.*)")
	public void stepSaveCustomValueToVariable(String value, String variable) {
		setVariable(variable, value);
	}

	@Когда("^приостановлено выполнение на (.+) (?:секунд|секунду|секунды)$")
	public void stopProcessing(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@When("поле (.*) очищено")
	public void stepClearField(String field) {
		fieldScenarioSteps.clearField(field);
	}
}

