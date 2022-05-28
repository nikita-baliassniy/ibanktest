package ru.netology.utils;

import java.util.Properties;
import ru.netology.properties.TestProperties;

public class TimeOutUtils {

	private final static String standardDefaultMiddleTimeout = "120";
	private final static String standardDefaultShortTimeout = "30";

	private final static Properties properties = TestProperties.getInstance().getProperties();
	private final static Integer shortDefaultTimeOut = Integer.valueOf(
			properties.getProperty("short.implicit.timeout", standardDefaultShortTimeout));
	private final static Integer middleDefaultTimeOut = Integer.valueOf(
			properties.getProperty("implicitly.timeout.wait", standardDefaultMiddleTimeout));

	public static Integer getMiddleDefaultTimeOut() {
		return middleDefaultTimeOut;
	}

	public static void waitTimeOut(double sec) {
		try {
			Thread.sleep((long) (1000 * sec));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
