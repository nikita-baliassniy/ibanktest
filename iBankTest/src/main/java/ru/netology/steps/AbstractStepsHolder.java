package ru.netology.steps;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.netology.properties.TestProperties;

public class AbstractStepsHolder {

	private final static String VAR_PATTERN = "\\#\\{(?<var>[^{^(^}^)]*)\\}";

	private final static String NOW_PATTERN =
			"\\#\\{\\s*now\\s*\\(:?(?<format>.*?)\\)\\s*((?<sign>[\\+\\-])\\s*(?<amount>\\d+)\\s*(?<points>[ydMmsS]))?\\s*\\}";

	private final static String DATE_PATTERN =
			"\\#\\{\\s*date\\s*\\((.*)\\)\\s*(([\\+\\-])\\s*(\\d+)\\s*([ydMmsS]))*\\}";

	public final static Pattern VAR_PATTERN_COMPILED = Pattern.compile(VAR_PATTERN);

	public final static Pattern NOW_PATTERN_COMPILED = Pattern.compile(NOW_PATTERN);

	public final static Pattern DATE_PATTERN_COMPILED = Pattern.compile(DATE_PATTERN);

	private static ThreadLocal<Map<Object, Object>> variables =
			new ThreadLocal<Map<Object, Object>>() {
				protected HashMap initialValue() {
					return new HashMap<Object, Object>() {
						{
							putAll(TestProperties.getInstance().getProperties());
						}
					};
				}
			};
	private static ThreadLocal<Map<Object, Object>> variablesFromReport =
			new ThreadLocal<Map<Object, Object>>() {
				protected HashMap initialValue() {
					return new HashMap();

				}
			};

	public static Map<Object, Object> getVariables() {
		return variables.get();
	}

	public static void setVariable(String name, Object value) {
		variables.get().put(name, value);
	}

	public static Object getVariable(String name) {
		return variables.get().get(name);
	}

	public static <T> T evalVariable(String param) {
		try {
			if (param.trim().replaceAll("\n|\r", "").matches(".*" + VAR_PATTERN + ".*")) {
				Matcher varMatcher = VAR_PATTERN_COMPILED.matcher(param);
				StringBuffer varSB = new StringBuffer();
				while (varMatcher.find()) {
					String value = String.valueOf(variables.get().get(varMatcher.group("var")));
					varMatcher.appendReplacement(varSB, value);
				}
				varMatcher.appendTail(varSB);
				return evalVariable(varSB.toString());
			} else if (param.trim().replaceAll("\n|\r", "").matches(".*" + NOW_PATTERN + ".*")) {
				Matcher nowMatcher = NOW_PATTERN_COMPILED.matcher(param);
				StringBuffer nowSB = new StringBuffer();
				while (nowMatcher.find()) {
					String value = String.valueOf(evalNow(nowMatcher.group(0)));
					nowMatcher.appendReplacement(nowSB, value);
				}
				nowMatcher.appendTail(nowSB);
				return evalVariable(nowSB.toString());
			} else if (param.trim().matches(".*" + DATE_PATTERN + ".*")) {
				Matcher dateMatcher = DATE_PATTERN_COMPILED.matcher(param);
				StringBuffer dateSB = new StringBuffer();
				while (dateMatcher.find()) {
					String value = "";
					try {
						value = String.valueOf(evalDate(dateMatcher.group(0)));
						dateMatcher.appendReplacement(dateSB, value);
					} catch (ParseException error) {
						throw new IllegalStateException(error);
					}
				}
				dateMatcher.appendTail(dateSB);
				return evalVariable(dateSB.toString());
			} else if (param.trim().equals("null")) {
				return null;
			}
			return (T) param;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			throw new AssertionError(String.format("", param), e);
		}
	}

	private static String evalNow(String command) {
		Matcher match = NOW_PATTERN_COMPILED.matcher(command);
		if (!match.find()) {
			return null;
		}
		Calendar cl = Calendar.getInstance();
		String format = match.group("format");
		if (format.isEmpty()) {
			format = "dd.MM.yyyy";
		}
		String sign = match.group("sign");
		String amount = match.group("amount");
		String points = match.group("points");
		if (sign != null) {
			int fld = -1;
			switch (points) {
				case "y":
					fld = Calendar.YEAR;
					break;
				case "M":
					fld = Calendar.MONTH;
					break;
				case "d":
					fld = Calendar.DATE;
					break;
				case "h":
					fld = Calendar.HOUR;
					break;
				case "m":
					fld = Calendar.MINUTE;
					break;
				case "s":
					fld = Calendar.SECOND;
					break;
				case "S":
					fld = Calendar.MILLISECOND;
					break;
			}
			cl.add(fld, (sign.equals("-") ? -1 : 1) * Integer.valueOf(amount));
		}
		DateFormat formatter;
		if (format.contains("llll")) {
			formatter = new SimpleDateFormat(format.replace("llll", "MMMM"),
					monthGenitiveFormatSymbols);
		} else {
			formatter = new SimpleDateFormat(format, monthNominativeFormatSymbols);
		}
		return formatter.format(cl.getTime());
	}

	private static DateFormatSymbols monthNominativeFormatSymbols = new DateFormatSymbols() {

		@Override
		public String[] getMonths() {
			return new String[] {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
					"Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
		}

	};

	private static DateFormatSymbols monthGenitiveFormatSymbols = new DateFormatSymbols() {

		@Override
		public String[] getMonths() {
			return new String[] {"января", "февраля", "марта", "апреля", "мая", "июня", "июля",
					"августа", "сентября", "октября", "ноября", "декабря"};
		}

	};

	private static String evalDate(String command) throws ParseException {
		Matcher match = DATE_PATTERN_COMPILED.matcher(command);
		if (!match.find()) {
			return null;
		}
		Calendar cl = Calendar.getInstance();
		String args = match.group(1);
		if (args.isEmpty()) {
			return command;
		}
		String[] parts = args.split("\\s*\\,\\s*");
		String format = parts.length >= 2 ? parts[1] : "dd.MM.yyyy";
		String value = evalVariable(parts[0]);
		value = value.trim().replaceAll("^\\\"", "").replaceAll("\\\"$", "");
		DateFormat formatter = new SimpleDateFormat(format);
		cl.setTime(formatter.parse(value));
		String sign = match.group(3);
		String amount = match.group(4);
		String points = match.group(5);
		if (sign != null) {
			int fld = -1;
			switch (points) {
				case "y":
					fld = Calendar.YEAR;
					break;
				case "M":
					fld = Calendar.MONTH;
					break;
				case "d":
					fld = Calendar.DATE;
					break;
				case "h":
					fld = Calendar.HOUR;
					break;
				case "m":
					fld = Calendar.MINUTE;
					break;
				case "s":
					fld = Calendar.SECOND;
					break;
				case "S":
					fld = Calendar.MILLISECOND;
					break;
			}
			cl.add(fld, (sign.equals("-") ? -1 : 1) * Integer.valueOf(amount));
		}
		return formatter.format(cl.getTime());
	}
}
