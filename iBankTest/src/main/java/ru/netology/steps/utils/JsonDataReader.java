package ru.netology.steps.utils;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import ru.netology.properties.TestProperties;
import ru.netology.utils.User;

public class JsonDataReader {
	private final String testDataFilesPath;

	public JsonDataReader() {
		testDataFilesPath = TestProperties.getInstance().getProperties().getProperty("file.path");
	}

	public User getUserData(String fileName) {
		Gson gson = new Gson();
		Path pathToFile = Paths.get(testDataFilesPath + "/" + fileName);
		try {
			User user = gson.fromJson(Files.newBufferedReader(pathToFile), User.class);
			user.setJsonBody(Files.newBufferedReader(pathToFile).readLine());
			return user;
		} catch (IOException e) {
			throw new RuntimeException(
					String.format("Ошибка при чтении тестового файла. Попытка прочесть: %s",
							pathToFile.toString()), e);
		}
	}
}