package org.grab.com.fraud.detector.exceptions;

public class IncompleteRecordException extends Exception{

	private static final long serialVersionUID = -751940490926620992L;

	private String record;
	
	private int expectedColumnCount;
	
	private int actualColumnCount;
	
	public IncompleteRecordException(String record, int expectedColumnCount, int actualColumnCount) {
		this.record = record;
		this.expectedColumnCount = expectedColumnCount;
		this.actualColumnCount = actualColumnCount;
	}
	
	
	@Override
	public String getMessage() {
		StringBuilder str = new StringBuilder();
		str.append("Incomplete Record Detected. Actual columns: ")
		.append(this.actualColumnCount).append(" Expected Column count: ")
		.append(this.expectedColumnCount).append("\n").append("Record: ").append(this.record);
		return str.toString();
	}

}
