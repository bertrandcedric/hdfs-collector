package com.bigdata.log;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties("log")
public class LogSinkProperties {

	/**
	 * The name of the logger to use.
	 */
	@Value("${spring.application.name:log.sink}")
	private String name;

	/**
	 * A SpEL expression (against the incoming message) to evaluate as the logged message.
	 */
	private String expression = "payload";

	/**
	 * The level at which to log messages.
	 */
	private CustomLoggingHandler.Level level = CustomLoggingHandler.Level.INFO;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@NotNull
	public CustomLoggingHandler.Level getLevel() {
		return level;
	}

	public void setLevel(CustomLoggingHandler.Level level) {
		this.level = level;
	}

}
