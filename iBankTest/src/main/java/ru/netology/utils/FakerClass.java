package ru.netology.utils;

import com.github.javafaker.Faker;
import java.util.Arrays;
import java.util.Locale;


public class FakerClass {

	private static FakerClass fakerClass = null;
	private final static String[] availableStatus = {"active", "blocked"};

	public static FakerClass getInstance() {
		if (fakerClass == null) {
			fakerClass = new FakerClass();
		}
		return fakerClass;
	}

	public User createUser(String status) {
		Faker faker = new Faker(new Locale("en-US"));
		if (Arrays.asList(availableStatus).contains(status)) {
			return new User(faker.name().firstName() + faker.number().digits(4),
					faker.letterify("????????????"), status, null);
		} else {
			return new User(faker.name().firstName() + faker.number().digits(4),
					faker.letterify("????????????"), faker.letterify("???????"), null);
		}
	}
}
