package ru.netology.fields;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.netology.DriverManager;

public class CustomCondition {

	public static final Condition present = new Condition("present", true) {
		@Override
		public boolean apply(WebElement element) {
			try {
				DriverManager.setImplicitlyWait(3);
				return element.isDisplayed();
			} catch (NoSuchElementException elementHasDisappeared) {
				return false;
			} finally {
				DriverManager.setDefaultImplicitlyWait();
			}
		}
	};
}
