package org.grab.com.fraud.detector.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransactionWithValidity{

    private Transaction transaction;
	
	private boolean isFraud;
	
	public String toCSVString() {
		StringBuilder csvString = new StringBuilder();
		 csvString.append(transaction.getTransactionId()).append(",")
		.append(transaction.getTimestamp()).append(",")
		.append(transaction.getSourceIP()).append(",")
		.append(transaction.getToAccount()).append(",")
		.append(transaction.getFromAccount()).append(",")
		.append(transaction.getStatusCode()).append(",")
		.append(transaction.getCurrency()).append(",")
		.append(transaction.getAmount()).append(",")
		.append(isFraud ? ValidityEnum.Fraud.getValue() : ValidityEnum.Valid.getValue());
		return csvString.toString();
	}
	
	public static String getHeader() {
		return "transactionId,timestamp,sourceId,toAccount,fromAccount,statusCode,currency,amount,validity";
	}

}
