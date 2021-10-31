package org.grab.com.fraud.detector.exceptions;

import java.nio.file.Path;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidArgumentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780338065917852471L;

	private Path inputPath;
	
	@Override
	public String getMessage() {
		StringBuilder str = new StringBuilder();
		str.append("Invalid Arguments. Path: ").append(inputPath.toString())
		.append(" Doesn't exists!");
		return str.toString();
	}
}
