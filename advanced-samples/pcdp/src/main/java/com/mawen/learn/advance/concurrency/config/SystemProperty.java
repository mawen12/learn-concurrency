package com.mawen.learn.advance.concurrency.config;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/19
 */
public enum SystemProperty {
	numWorkers("numWorkers", "int", "Number of worker threads to create", Constants.AVAILABLE_PROCESSORS),
	showWarning("showWarning", "bool", "Show warning/debug messages", "false"),
	showRuntimeStats("showRuntimeStats", "bool", "Show executor service stats", "false");

	public static void setSystemProperty(final SystemProperty systemProperty, final Object value) {
		System.setProperty(systemProperty.getPropertyKey(), String.valueOf(value));
	}

	private final String propertyKey;

	private final String propertyType;

	private final String propertyDescription;

	private final String defaultValue;

	SystemProperty(String propertyKey, String propertyType, String propertyDescription, String defaultValue) {
		this.propertyKey = propertyKey;
		this.propertyType = propertyType;
		this.propertyDescription = propertyDescription;
		this.defaultValue = defaultValue;
	}

	private String getPropertyKey() {
		return propertyKey;
	}

	public String getPropertyValue() {
		final String systemProperty = System.getProperty(getPropertyKey());
		if (systemProperty == null || systemProperty.trim().isEmpty()) {
			return defaultValue;
		}
		else {
			return systemProperty;
		}
	}

	public void set(final Object value) {
		setProperty(value);
	}

	public void setProperty(final Object value) {
		setSystemProperty(this, value);
	}


	@Override
	public String toString() {
		final String configuredValue = System.getProperty(propertyKey);
		final String displayValue;
		if (configuredValue != null) {
			displayValue = configuredValue;
		}
		else {
			displayValue = "";
		}

		return String.format("%20s : type=%-6s, default=%-6s, current=%-6s, description=%s",
				propertyKey, propertyType, defaultValue, displayValue, propertyDescription);
	}

	private static class Constants {

		private static int availableProcessors = Runtime.getRuntime().availableProcessors();

		private static final String AVAILABLE_PROCESSORS = Integer.toString(availableProcessors);

		private static final String MAX_THREADS_DEFAULT = Integer.toString(Math.min(128, 10 * availableProcessors));
	}
}
