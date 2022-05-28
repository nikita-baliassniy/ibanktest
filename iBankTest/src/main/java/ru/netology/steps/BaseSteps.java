package ru.netology.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import ru.netology.DriverManager;
import ru.netology.enums.BrowserEnum;
import ru.netology.fields.CustomFieldMapping;
import ru.netology.pages.CustomPageObject;

@Slf4j
public class BaseSteps {

	@Before
	public static void beforeScenario() {
		CustomPageObject.setClassMap();
		CustomFieldMapping.set_FIELD_TYPE();
		DriverManager.initDriver(BrowserEnum.CHROME);
	}

	@After
	public static void afterScenario() {
		if (DriverManager.getDriver() != null) {
			DriverManager.getDriver().quit();
		}
	}
}
