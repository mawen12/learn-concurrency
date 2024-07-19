package com.mawen.learn.advance.concurrency.config;

import java.io.InputStream;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
public final class Configuration {

	public static boolean showWarning;

	public static boolean showRuntimeStatus;

	public static String buildInfo;

	static {
		showWarning = readBooleanProperty(SystemProperty.showWarning);
		if (showWarning) {
			printConfiguredOptions();
		}
		showRuntimeStatus = readBooleanProperty(SystemProperty.showRuntimeStats);

		try {
			final Properties buildProperties = new Properties();
			final InputStream buildPropsStream = Configuration.class.getClassLoader().getResourceAsStream("/build.properties");
			buildProperties.load(buildPropsStream);
			buildInfo = buildProperties.getProperty("version") + ' ' + buildProperties.getProperty("buildTimestamp") + ' ' + buildProperties.getProperty("buildNumber");
		}
		catch (Exception e) {
			buildInfo = "";
		}
	}

	public static boolean readBooleanProperty(final SystemProperty systemProperty) {
		final Function<String, Boolean> converter = Boolean::parseBoolean;
		return extractProperty(systemProperty, converter);
	}

	public static int readIntProperty(final SystemProperty systemProperty) {
		final Function<String, Integer> converter = Integer::parseInt;
		return extractProperty(systemProperty, converter);
	}

	public static String readStringProperty(final SystemProperty systemProperty) {
		Function<String, String> converter = Function.identity();
		return extractProperty(systemProperty, converter);
	}

	public static void printConfiguredOptions() {
		System.err.println("Interpreter flags: ");
		for (SystemProperty systemProperty : SystemProperty.values()) {
			System.err.println(" " + systemProperty);
		}
	}

	private static <T> T extractProperty(final SystemProperty property, final Function<String, T> converter) {
		try {
			final String valueStr = property.getPropertyValue();
			return converter.apply(valueStr);
		}
		catch (Exception e) {
			throw new IllegalStateException("Error while converting property: " + property);
		}
	}

	private Configuration () {}

}