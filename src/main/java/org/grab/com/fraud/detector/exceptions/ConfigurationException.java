package org.grab.com.fraud.detector.exceptions;

public class ConfigurationException extends Exception{

	private static final long serialVersionUID = -751940490926620992L;

	private String configElementName;
	
	private String expectedValue;
	
	private String actualValue;
	
	public ConfigurationException(String configElementName, String expectedValue, String actualValue) {
		this.configElementName = configElementName;
		this.expectedValue = expectedValue;
		this.actualValue = actualValue;
		
	}
	
	@Override
	public String getMessage() {
		StringBuilder str = new StringBuilder();
		str.append("Invalid configuration detected: ")
		.append("For Configuration Property: ").append(this.configElementName)
		.append("Expected value: ").append(this.expectedValue)
		.append("Actual value: ").append(this.actualValue);
		return str.toString();
	}
}
