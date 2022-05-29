package ru.netology.pages;

import ru.netology.core.annotations.Container;

@Container(ref = "Личный кабинет")
public class PersonalAccount extends CustomAbstractPageObject {
	public boolean isLoaded() {
		this.getField("Заголовок").waitElementVisibility();
		waitUntilJQueryReady();
		return this.getField("Заголовок").isDisplayed();
	}
}
