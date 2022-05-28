package ru.netology.core.annotations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;

public class Locator {
	private Locator() {
	}

	public static By getLocator(String locator) {
		Pattern typeExtractor = Pattern.compile(
				"^(id\\s*\\=|xpath\\s*\\=|link\\s*\\=|css\\=|jquery\\s*\\=|tag\\s*\\=|partialLink\\s*\\=)(.*)$");
		Matcher matcher = typeExtractor.matcher(locator);
		if (!matcher.find()) {
			return By.xpath(locator);
		} else {
			String type = matcher.group(1);
			String expr = matcher.group(2).trim();
			if (type.contains("id")) {
				return By.id(expr);
			} else if (type.contains("xpath")) {
				return By.xpath(expr);
			} else if (type.contains("link")) {
				return By.linkText(expr);
			} else if (type.contains("css")) {
				return By.cssSelector(expr);
			} else if (type.contains("partialLink")) {
				return By.partialLinkText(expr);
			} else if (type.contains("tag")) {
				return By.tagName(expr);
			}
		}
		return By.xpath(locator);
	}
}