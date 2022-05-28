package ru.netology.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldProperties {
	private final Properties properties = new Properties();
	private static FieldProperties INSTANCE = null;

	private FieldProperties() {
		try {
			InputStreamReader inR = null;
			InputStream in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("field.properties");
			inR = new InputStreamReader(in, "windows-1251");
			properties.load(inR);
		} catch (IOException e) {
			log.error(e.toString());
		}
	}

	public static FieldProperties getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FieldProperties();
		}
		return INSTANCE;
	}

	public Properties getProperties() {
		return properties;
	}
}
