package org.grab.com.fraud.detector.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class Transaction {
	
	private String transactionId;
	
	private Timestamp timestamp;
	
	private String sourceIP;
	
	private String toAccount;
	
	private String fromAccount;
	
	private String statusCode;
	
	private String currency;
	
	private String amount;

}
