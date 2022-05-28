package ru.netology.steps.fields;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import io.qameta.allure.Step;
import ru.netology.pages.AbstractPageObject;
import ru.netology.pages.IPageObject;
import ru.netology.steps.AbstractStepsHolder;

public class FieldScenarioSteps {

	@Step("поле \"{fieldName}\" заполняется значением \"{value}\"")
	public void fillField(String fieldName, String value) {
		IPageObject page = AbstractPageObject.getCurrentPage();
		page.getField(fieldName).type(value);
	}

	@Step("выполнено нажатие на {fieldName}")
	public void clickField(String fieldName) {
		IPageObject page = AbstractPageObject.getCurrentPage();
		page.getField(fieldName).click();
	}

	@Step("значение поля \"{fieldName}\" равно \"{expected}\"")
	public void checkFieldValue(String fieldName, String expected) {
		String actual = getFieldValue(fieldName);
		expected = AbstractStepsHolder.evalVariable(expected);
		assertEquals(String.format("Значение поля [%s] не соответствует ожидаемому [%s]", fieldName,
				expected), expected, actual);
	}

	@Step
	public String getFieldValue(String fieldName) {
		IPageObject page = AbstractPageObject.getCurrentPage();
		return page.getField(fieldName).getFieldValue().trim().replaceAll("\u00A0", " ");
	}

	@Step
	public void fieldIsNotPresent(String fieldName) {
		boolean isPresent = fieldIsPresent(fieldName);
		assertFalse("Поле " + fieldName + " не существует на странице", isPresent);
	}

	@Step
	public boolean fieldIsPresent(String fieldName) {
		IPageObject page = AbstractPageObject.getCurrentPage();
		return page.getField(fieldName).isDisplayed();
	}

	@Step("поле {fieldName} очищено")
	public void clearField(String fieldName) {
		IPageObject page = AbstractPageObject.getCurrentPage();
		page.getField(fieldName).clear();
	}

}