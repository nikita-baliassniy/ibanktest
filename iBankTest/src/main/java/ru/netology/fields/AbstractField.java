package ru.netology.fields;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.DriverManager;

public abstract class AbstractField {

	protected SelenideElement element;

	public AbstractField(SelenideElement element) {
		this.element = element;
	}

	public void waitElementVisibility() {
		element.waitUntil(Condition.visible, DriverManager.getDefaultImplicitlyWait());
	}

	public String getFieldValue() {
		String value = element.getValue();
		if (value == null) {
			return element.getText();
		}
		return value;
	}

	public String getText() {
		return element.getText();
	}

	public boolean isDisplayed() {
		return element.isDisplayed();
	}

	public void clear() {
		element.clear();
	}

	public void type(String value) {
		element.setValue(value);
		element.pressEnter();
	}

	public void click() {
		element.waitUntil(Condition.enabled, DriverManager.getDefaultImplicitlyWait()).click();
	}

	public String getLabel() {
		return null;
	}
}

