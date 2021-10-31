package org.grab.com.fraud.detector.model;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class AccountMetadata {
	
	@NonNull
	private String accountNumber;
	
	@NonNull
	private String country;
	
	@NonNull
	private Timestamp firstTransactionTimestamp;
	
	private Timestamp previousTransactionTimestamp;
	
	private AtomicInteger transactionCount = new AtomicInteger(1);
	
	private AtomicInteger unsuccessfulTransactionCount = new AtomicInteger(0);
	
	@NonNull
	private Boolean isFraud;
	
	public void incrementTransaction() {
		transactionCount.incrementAndGet();
	}
	
	public void incrementUnSuccessfulTransaction() {
		unsuccessfulTransactionCount.incrementAndGet();
	}
	
	public int getUnSuccessfulTransactionCount() {
		return unsuccessfulTransactionCount.get();
	}
}
