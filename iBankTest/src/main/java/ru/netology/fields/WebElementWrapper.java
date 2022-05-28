package ru.netology.fields;

import com.codeborne.selenide.SelenideElement;

public class WebElementWrapper extends AbstractField {

	public WebElementWrapper(SelenideElement element) {
		super(element);
	}
}
