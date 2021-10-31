package org.grab.com.fraud.detector.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidColumnFormatException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6904075457903538354L;

	private String record;
	
	private String invalidColumnName;
	
	private String expectedFormat;
	
	private String actualValue;
	
	@Override
	public String getMessage() {
		StringBuilder str = new StringBuilder();
		str.append("Invalid Column format Detected. Invalid columns: ").append(this.invalidColumnName)
		.append(" Expected Column format: ").append(this.expectedFormat)
		.append(" Actual Column format: ").append(this.actualValue)
		.append("\n")
		.append("Record: ").append(this.record);
		return str.toString();
	}

}
