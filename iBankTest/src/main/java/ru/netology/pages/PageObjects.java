package ru.netology.pages;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class PageObjects {

	@Getter
	@Setter
	protected static Map<String, Class<? extends CustomAbstractPageObject>> classMap =
			new HashMap<>();

	public static String getKey(Map<String, Class<? extends CustomAbstractPageObject>> classMap,
								Class className) {
		if (classMap.entrySet().stream().filter(entry -> className.equals(entry.getValue()))
				.map(Map.Entry::getKey).findFirst().isPresent()) {
			return classMap.entrySet().stream().filter(entry -> className.equals(entry.getValue()))
					.map(Map.Entry::getKey).findFirst().get();
		} else {
			return null;
		}
	}

}