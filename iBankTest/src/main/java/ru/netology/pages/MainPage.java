package ru.netology.pages;

import ru.netology.core.annotations.Container;

@Container(ref = "Главная страница")
public class MainPage extends CustomAbstractPageObject {
	public boolean isLoaded() {
		this.getField("Карта с доставкой").waitElementVisibility();
		this.getField("Запланировать").waitElementVisibility();
		waitUntilJQueryReady();
		return this.getField("Запланировать").isDisplayed();
	}
}