package ru.netology.steps.utils;

import static ru.netology.steps.AbstractStepsHolder.setVariable;


import cucumber.api.java.ru.Дано;
import java.lang.reflect.Field;
import ru.netology.utils.FakerClass;
import ru.netology.utils.User;

public class DataParamsSteps {

	JsonDataReader jsonDataReader = new JsonDataReader();

	@Дано("сгенерирован тестовый пользователь со статусом заблокирован")
	public void loadBlockedUserData() {
		FakerClass.getInstance().saveUser();
		saveUserToRAM(jsonDataReader.getRandomUserData());
	}

	@Дано("сгенерирован тестовый пользователь со статусом активен")
	public void loadActiveUserData() {
		FakerClass.getInstance().saveUser();
		User user = jsonDataReader.getRandomUserData();
		user.setStatus("active");
		saveUserToRAM(jsonDataReader.getRandomUserData());
	}

	private void saveUserToRAM(User user) {
		for (Field field : user.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				setVariable(field.getName(), field.get(user));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

}
