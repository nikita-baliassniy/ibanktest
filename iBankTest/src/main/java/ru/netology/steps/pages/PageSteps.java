package ru.netology.steps.pages;

import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.Когда;
import ru.netology.DriverManager;
import ru.netology.pages.Page;
import ru.netology.properties.TestProperties;

public class PageSteps {

	PageScenarioSteps pageScenarioSteps = new PageScenarioSteps();

	@Дано("пользователь открыл главную страницу")
	public void openMainPage() {
		DriverManager.getWebDriver()
				.get(TestProperties.getInstance().getProperties().getProperty("app.url"));
	}

	@Когда("(?:загружена|загружено|загружен) (?:страница|виджет|диалоговое окно) \"(.*)\"")
	public void stepLoadPage(Page page) {
		pageScenarioSteps.stepLoadedPage(page);
	}
}

