package org.grab.com.fraud.detector.exceptions;

import java.util.Arrays;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidArgumentLengthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780338065917852471L;

	private String[] arguments;
	
	private int expected;
	
	private String argumentList; 
	
	@Override
	public String getMessage() {
		StringBuilder str = new StringBuilder();
		str.append("Invalid Arguments. Expected number of arguments: ").append(expected)
		.append(" Expected argument list: ").append(argumentList)
		.append(" Actual Arguments: ").append(Arrays.toString(arguments));
		return str.toString();
	}
}
