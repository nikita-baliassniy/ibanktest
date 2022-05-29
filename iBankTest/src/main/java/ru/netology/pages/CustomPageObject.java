package ru.netology.pages;


public class CustomPageObject extends PageObjects {

	public static void setClassMap() {
		classMap.put("Главная страница", MainPage.class);
		classMap.put("Оповещение об ошибке", ErrorNotification.class);
		classMap.put("Личный кабинет", PersonalAccount.class);
	}
}
