package ru.netology.fields;

import static com.codeborne.selenide.Condition.attribute;


import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class CheckBox extends AbstractField {

	final static String INPUT = ".//input";

	public CheckBox(SelenideElement element) {
		super(element);
	}

	@Override
	public void type(String value) {
		if (!getFieldValue().equals(value)) {
			element.click();
		}
	}

	@Override
	public String getFieldValue() {
		return String.valueOf(element.$(By.xpath(INPUT)).has(attribute("checked")));
	}


}