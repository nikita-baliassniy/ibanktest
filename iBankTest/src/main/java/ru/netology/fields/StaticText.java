package ru.netology.fields;

import com.codeborne.selenide.SelenideElement;

public class StaticText extends AbstractField {

	public StaticText(SelenideElement element) {
		super(element);
	}

	@Override
	public boolean isDisplayed() {
		return element.isDisplayed();
	}

	@Override
	public String getErrorMsg() {
		return null;
	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public String getLabel() {
		return null;
	}
}