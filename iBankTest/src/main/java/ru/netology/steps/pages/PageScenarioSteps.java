package ru.netology.steps.pages;

import static org.junit.Assert.assertTrue;


import io.qameta.allure.Step;
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

	@Step("страница \"{page}\" загружена")
	public IPageObject stepLoadedPage(Page page) {
		setPage(page);
		pageShouldBeLoaded(page);
		return AbstractPageObject.getCurrentPage();
	}
}
