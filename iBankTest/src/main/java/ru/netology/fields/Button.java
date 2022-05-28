package ru.netology.fields;

import static ru.netology.DriverManager.getDefaultImplicitlyWait;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class Button extends AbstractField {

	public Button(SelenideElement element) {
		super(element);
	}

	@Override
	public void click() {
		element.waitUntil(Condition.enabled, getDefaultImplicitlyWait()).click();
	}

}