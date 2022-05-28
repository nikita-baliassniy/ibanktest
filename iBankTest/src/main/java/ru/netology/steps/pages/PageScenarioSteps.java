package ru.netology.steps.pages;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import io.qameta.allure.Step;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import org.openqa.selenium.TimeoutException;
import ru.netology.DriverManager;
import ru.netology.pages.AbstractPageObject;
import ru.netology.pages.IPageObject;
import ru.netology.pages.Page;

public class PageScenarioSteps {

	public void setPage(Page page) {
		AbstractPageObject.setCurrentPage(page.getAsClass());
	}

	private void pageShouldBeLoaded(Page page) {
		assertTrue("Страница \"" + page.toString() + "\" не загружена",
				AbstractPageObject.getCurrentPage().isLoaded());
	}

	private void pageShouldBeNotLoaded(Page page) {
		try {
			assertFalse("Страница \"" + page.toString() + "\" загружена",
					AbstractPageObject.getCurrentPage().isLoaded());
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	@Step("страница \"{page}\" загружена")
	public IPageObject stepLoadedPage(Page page) {
		setPage(page);
		pageShouldBeLoaded(page);
		return AbstractPageObject.getCurrentPage();
	}

	@Step("страница \"{page}\" не загружена")
	public void stepNotLoadedPage(Page page) {
		IPageObject pageObject = AbstractPageObject.getCurrentPage();
		setPage(page);
		pageShouldBeNotLoaded(page);
		AbstractPageObject.setCurrentPage(pageObject);
	}

	@Step("выполнен переход по ссылке {url}")
	public void navigatToUrl(String url) {
		DriverManager.getWebDriver().get(url);
	}

	public ArrayList<String> readFile(String fileName) {
		ArrayList<String> lines = new ArrayList<>();
		try {
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			String line = bfr.readLine();
			while (line != null) {
				lines.add(line);
				line = bfr.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
}
