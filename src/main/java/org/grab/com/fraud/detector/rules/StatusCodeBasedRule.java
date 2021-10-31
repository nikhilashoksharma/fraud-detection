package org.grab.com.fraud.detector.rules;

import org.grab.com.fraud.detector.model.AccountMetadata;
import org.grab.com.fraud.detector.model.AccountMetadataManager;
import org.grab.com.fraud.detector.model.StatusCodeEnum;
import org.grab.com.fraud.detector.model.Transaction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StatusCodeBasedRule implements Rule{

	private int invalidTransactionLimitPerAccount;
	
	@Override
	public boolean isFraud(Transaction transaction) {
		boolean isFraud = false;
		AccountMetadata fromAccountAccountMetadata = AccountMetadataManager.getAccountMetadata(transaction.getFromAccount());
		
		switch(StatusCodeEnum.customValueOf(transaction.getStatusCode())) {
		
		case DAILY_LIMIT_EXCEEDED:
		case TRANSACTION_TIMEOUT:
		case INCORRECT_OTP_OR_PIN:
			isFraud = isFraudBasedOnNumberOfInvalidTransactionExceedLimit(fromAccountAccountMetadata);
			break;
		case INSUFFICIENT_BALANCE:
			isFraud = isFraudBasedOnInSufficientBalance(fromAccountAccountMetadata);
			break;
		case SUCCESS:
			break;
			
		}
		return isFraud;
	}

	private boolean isFraudBasedOnNumberOfInvalidTransactionExceedLimit(AccountMetadata fromAccountAccountMetadata) {
		if(fromAccountAccountMetadata != null) {
			fromAccountAccountMetadata.incrementUnSuccessfulTransaction();
			return (fromAccountAccountMetadata.getUnSuccessfulTransactionCount() > invalidTransactionLimitPerAccount);
		}else {
			return false;
		}
		
	}

	private boolean isFraudBasedOnInSufficientBalance(AccountMetadata fromAccountAccountMetadata) {
		return true;
	}


}
