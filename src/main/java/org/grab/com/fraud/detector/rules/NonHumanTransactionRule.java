package org.grab.com.fraud.detector.rules;

import java.sql.Timestamp;

import org.grab.com.fraud.detector.model.AccountMetadata;
import org.grab.com.fraud.detector.model.AccountMetadataManager;
import org.grab.com.fraud.detector.model.Transaction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NonHumanTransactionRule implements Rule {

	private int minTimeBetweenTwoTranactionsSeconds = 10;

	@Override
	public boolean isFraud(Transaction transaction) {
		boolean isFraud = false;
		AccountMetadata accountMetadata = AccountMetadataManager.getAccountMetadata(transaction.getFromAccount());
		if (accountMetadata != null) {
			Timestamp currentTransactionTimestamp = transaction.getTimestamp();
			Timestamp previousTransactionTimestamp = accountMetadata.getPreviousTransactionTimestamp();
			if (previousTransactionTimestamp == null)
				previousTransactionTimestamp = accountMetadata.getFirstTransactionTimestamp();
			float timeBetweenTransactionInMinutes = (Math.abs(currentTransactionTimestamp.getTime()
					- previousTransactionTimestamp.getTime()) / 1000);
			if (timeBetweenTransactionInMinutes < minTimeBetweenTwoTranactionsSeconds) {
				isFraud = true;
			}
		}
		return isFraud;
	}

}
