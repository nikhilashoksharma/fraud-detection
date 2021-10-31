package org.grab.com.fraud.detector.rules;

import org.grab.com.fraud.detector.model.AccountMetadata;
import org.grab.com.fraud.detector.model.AccountMetadataManager;
import org.grab.com.fraud.detector.model.Transaction;

public class FraudulantAccountsRule implements Rule{
	
	
	@Override
	public boolean isFraud(Transaction transaction) {
		AccountMetadata fromAccountAccountMetadata = AccountMetadataManager.getAccountMetadata(transaction.getFromAccount());
		AccountMetadata toAccountAccountMetadata = AccountMetadataManager.getAccountMetadata(transaction.getToAccount());
		return (fromAccountAccountMetadata != null && fromAccountAccountMetadata.getIsFraud()) || 
				(toAccountAccountMetadata!= null && toAccountAccountMetadata.getIsFraud());
	}
	
}
