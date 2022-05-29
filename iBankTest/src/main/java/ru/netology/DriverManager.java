package ru.netology;

import com.codeborne.selenide.WebDriverRunner;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.netology.core.definitions.FieldDefinitions;
import ru.netology.enums.BrowserEnum;
import ru.netology.properties.TestProperties;
import ru.netology.utils.TimeOutUtils;

public class DriverManager {

	private static FieldDefinitions ELEMENT_DEFINITIONS = null;
	private static WebDriver driver = null;
	private static long currentImplicitWait = getDefaultImplicitlyWait();
	private static final Properties properties = TestProperties.getInstance().getProperties();

	public static WebDriver getDriver() {
		return driver;
	}

	public static WebDriver getWebDriver() {
		if (!WebDriverRunner.hasWebDriverStarted() && driver != null) {
			WebDriverRunner.setWebDriver(driver);
		}
		return WebDriverRunner.getWebDriver();
	}

	public static long getDefaultImplicitlyWait() {
		return TimeOutUtils.getMiddleDefaultTimeOut() * 1000;
	}

	public static void setDefaultImplicitlyWait() {
		getWebDriver().manage().timeouts()
				.implicitlyWait(TimeOutUtils.getMiddleDefaultTimeOut(), TimeUnit.SECONDS);
	}

	public static FieldDefinitions getElementDefinitions() {
		return ELEMENT_DEFINITIONS;
	}

	public static WebDriver initDriver(BrowserEnum browser) {
		setDefinitions();
		switch (browser) {
			case CHROME:
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
				options.addArguments("--disable-browser-side-navigation");
				options.addArguments("--start-maximized");
				options.addArguments("test-type");
				options.addArguments("enable-automation");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-infobars");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--disable-browser-side-navigation");
				options.addArguments("--disable-gpu");
				options.addArguments("--ignore-certificate-errors");
				System.setProperty("webdriver.chrome.driver",
						properties.getProperty("webdriver.chrome.driver"));
				driver = new ChromeDriver(options);
				break;
			case FIREFOX:
				System.setProperty("webdriver.gecko.driver",
						properties.getProperty("webdriver.gecko.driver"));
				driver = new FirefoxDriver();
				break;
		}
		WebDriverRunner.setWebDriver(driver);
		setDefaultImplicitlyWait();
		driver = WebDriverRunner.getWebDriver();
		driver.manage().timeouts().pageLoadTimeout(200, TimeUnit.SECONDS);
		return driver;
	}

	private static void setDefinitions() {
		String elementDefinitionFilePath = TestProperties.getInstance().getProperties()
				.getProperty("element.definitions.path");
		ELEMENT_DEFINITIONS = new FieldDefinitions();
		FieldDefinitions.load(elementDefinitionFilePath);
	}

	public static void setImplicitlyWait(long time) {
		currentImplicitWait = time;
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

}
