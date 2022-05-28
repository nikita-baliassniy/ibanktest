package ru.netology.pages;

import ru.netology.core.annotations.Container;

@Container(ref = "Оповещение о перепланировании")
public class ReplanNotification extends CustomAbstractPageObject {
	public boolean isLoaded() {
		this.getField("Заголовок").waitElementVisibility();
		waitUntilJQueryReady();
		return this.getField("Заголовок").isDisplayed();
	}
}