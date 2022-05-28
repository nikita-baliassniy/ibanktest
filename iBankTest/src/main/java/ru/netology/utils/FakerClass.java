package ru.netology.utils;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import ru.netology.properties.TestProperties;

public class FakerClass {

	private static FakerClass fakerClass = null;
	private final String testDataFilesPath =
			TestProperties.getInstance().getProperties().getProperty("file.path");

	public static FakerClass getInstance() {
		if (fakerClass == null) {
			fakerClass = new FakerClass();
		}
		return fakerClass;
	}

	private User generateUser() {
		Faker faker = new Faker(new Locale("en-US"));
		return new User(faker.name().firstName() + faker.number().digits(4),
				faker.letterify("????????????"), "blocked");
	}

	public void saveUser() {
		Gson gson = new Gson();
		try {
			Files.createDirectories(Paths.get(testDataFilesPath));
			Path generatedFilePath = Paths.get(testDataFilesPath + "/user.json");
			try (Writer writer = Files.newBufferedWriter(generatedFilePath)) {
				gson.toJson(generateUser(), writer);
			} catch (IOException e) {
				throw new RuntimeException(
						String.format("Ошибка при генерации тестового файла. Создаваемый файл: %s",
								generatedFilePath.toString()), e);
			}
		} catch (IOException e) {
			throw new RuntimeException(String.format(
					"Ошибка при генерации папки для тестовых файлов. Создаваемый каталог: %s",
					testDataFilesPath), e);
		}
	}
}
