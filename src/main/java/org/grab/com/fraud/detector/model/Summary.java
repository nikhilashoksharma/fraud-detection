package org.grab.com.fraud.detector.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Summary {
	
	private long totalRecords = 0l;
	
	private long validRecords = 0l;
	
	private long fraudRecords = 0l;
	
	private long successfullyProessedRecord = 0l;
	
	private long errorRecords = 0l;
	
	public void incrementTotalRecords() {
		++totalRecords;
	}
	
	public void incrementSuccessfullyProessedRecord() {
		++successfullyProessedRecord;
	}
	
	public void incrementErrorRecords() {
		++errorRecords;
	}
	
	public void incrementValidRecords() {
		++validRecords;
	}
	
	public void incrementFraudRecords() {
		++fraudRecords;
	}
}
