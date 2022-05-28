package ru.netology.pages;


public class CustomPageObject extends PageObjects {

	public static void setClassMap() {
		classMap.put("Главная страница", MainPage.class);
		classMap.put("Оповещение об успехе", SuccessNotification.class);
		classMap.put("Оповещение о перепланировании", ReplanNotification.class);
	}
}
