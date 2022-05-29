package ru.netology.pages;

import ru.netology.core.annotations.Container;

@Container(ref = "Главная страница")
public class MainPage extends CustomAbstractPageObject {
	public boolean isLoaded() {
		this.getField("Заголовок").waitElementVisibility();
		this.getField("Продолжить").waitElementVisibility();
		waitUntilJQueryReady();
		return this.getField("Продолжить").isDisplayed();
	}
}