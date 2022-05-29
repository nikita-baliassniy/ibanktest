package ru.netology.steps.utils;

import static ru.netology.steps.AbstractStepsHolder.evalVariable;
import static ru.netology.steps.AbstractStepsHolder.setVariable;


import cucumber.api.java.ru.Дано;
import java.lang.reflect.Field;
import ru.netology.utils.User;

public class DataParamsSteps {

	JsonDataReader jsonDataReader = new JsonDataReader();

	@Дано("данные пользователя подгружены из файла (.*)$")
	public void loadUserData(String fileName) {
		User user = jsonDataReader.getUserData(evalVariable(fileName));
		for (Field f : user.getClass().getDeclaredFields()) {
			try {
				f.setAccessible(true);
				setVariable(f.getName(), f.get(user));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
