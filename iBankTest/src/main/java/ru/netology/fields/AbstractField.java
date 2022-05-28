package ru.netology.fields;

import static java.time.temporal.ChronoUnit.SECONDS;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.InvalidStateException;
import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import ru.netology.DriverManager;

public abstract class AbstractField {

	protected SelenideElement element;

	public AbstractField(SelenideElement element) {
		this.element = element;
	}


	public void scrollToElement() {
		element.scrollTo();
	}

	public void waitElementVisibility() {
		element.waitUntil(Condition.visible, DriverManager.getDefaultImplicitlyWait());
	}

	public void waitElementEnabled() {
		element.waitUntil(Condition.enabled, DriverManager.getDefaultImplicitlyWait());
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

	public String getErrorMsg() {
		return null;
	}

	public String getSuggestMsg() {
		return null;
	}

	public boolean getErrorMsgVisibility() {
		return false;
	}

	public boolean isEditable() {
		return false;
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

	public void multiType(ArrayList<String> values) {
		StringBuilder sb = new StringBuilder();
		values.forEach(sb::append);
		element.setValue(sb.toString());
		element.pressEnter();
	}

	public void typeAndPressEnter(String value) {
		element.setValue(value);
		element.pressTab();
	}

	public void click() {
		element.waitUntil(Condition.enabled, DriverManager.getDefaultImplicitlyWait()).click();
	}

	public String getAttribute(String attribute) {
		return element.getAttribute(attribute);
	}

	public boolean isEnabled() {
		return element.isEnabled();
	}

	public String getLabel() {
		return null;
	}

	public boolean exists() {
		return element.exists();
	}

	public void waitElementInvisibility() {
		DriverManager.setImplicitlyWait(3);
		element.waitUntil(Condition.hidden, DriverManager.getDefaultImplicitlyWait());
		DriverManager.setDefaultImplicitlyWait();
	}

	public boolean waitElementExistence() {
		element.waitUntil(Condition.exist, DriverManager.getDefaultImplicitlyWait());
		return exists();
	}

	public void waitElementNotPresent() {
		Wait<WebDriver> fluentWait = new FluentWait<>(DriverManager.getDriver())
				.withTimeout(Duration.of(DriverManager.getDefaultImplicitlyWait(), SECONDS));
		fluentWait.until(driver -> !isPresent());
	}

	public boolean isPresent() {
		try {
			DriverManager.setImplicitlyWait(5);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} finally {
			DriverManager.setDefaultImplicitlyWait();
		}
	}

	public void doubleClick() {
		element.doubleClick();
	}

	public void moveToElement() {
		new Actions(DriverManager.getWebDriver()).moveToElement(element).perform();
	}

	public void openContextMenu() {
		new Actions(DriverManager.getWebDriver()).contextClick(element).perform();
	}

	public boolean elementNotClickable() {
		try {
			element.click();
			return true;
		} catch (ElementNotFound e) {
			return false;
		}
	}

	public String getCssValue(String cssValue) {
		return element.getCssValue(cssValue);
	}

	public boolean isNotEditable() {
		try {
			element.sendKeys("123");
			return false;
		} catch (ElementNotFound | InvalidStateException e) {
			return true;
		}
	}

	public void checkFieldColor(String colorField, String value) {
	}

	public void pastValue() {
		element.sendKeys(Keys.CONTROL + "V");
	}

	public String getFieldLineColor() {
		return null;
	}
}

