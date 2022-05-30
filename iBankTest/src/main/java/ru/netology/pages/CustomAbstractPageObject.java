package ru.netology.pages;

import static ru.netology.DriverManager.getWebDriver;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.netology.utils.TimeOutUtils;

public abstract class CustomAbstractPageObject extends AbstractPageObject {

	private static final String xpathLoadWindow =
			"//*[contains(text(),'Выполняется загрузка данных')]";
	private static Wait<WebDriver> wait = new WebDriverWait(getWebDriver(), 200);
	private static final String spinnerXPath =
			"//table[contains(@id,'j_idt')]//td[contains(text(),'Пожалуйста, ожидайте.')]";

	private static void waitForJQueryLoad() {
		TimeOutUtils.waitTimeOut(5);
		ExpectedCondition<Boolean> jQueryLoad = driver -> (
				(Long) ((JavascriptExecutor) getWebDriver()).executeScript("return jQuery.active")
						== 0);
		try {
			boolean jqueryReady = (Boolean) ((JavascriptExecutor) getWebDriver())
					.executeScript("return jQuery.active==0");
			if (!jqueryReady) {
				wait.until(jQueryLoad);
			}
		} catch (Exception e) {
			wait.until(jQueryLoad);
		}
	}

	private static void waitUntilJSReady() {
		TimeOutUtils.waitTimeOut(2);
		JavascriptExecutor jsExec = (JavascriptExecutor) getWebDriver();
		ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) getWebDriver())
				.executeScript("return document.readyState").toString().equals("complete");
		boolean jsReady =
				jsExec.executeScript("return document.readyState").toString().equals("complete");
		if (!jsReady) {
			wait.until(jsLoad);
		}
	}

	public static void waitUntilJQueryReady() {
		Boolean jQueryDefined = (Boolean) ((JavascriptExecutor) getWebDriver())
				.executeScript("return typeof jQuery != 'undefined'");
		if (jQueryDefined) {
			waitUntilJSReady();
			waitForJQueryLoad();
		}
	}
}