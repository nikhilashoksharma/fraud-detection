package org.grab.com.fraud.detector.rules;

import java.sql.Timestamp;

import org.grab.com.fraud.detector.model.AccountMetadata;
import org.grab.com.fraud.detector.model.AccountMetadataManager;
import org.grab.com.fraud.detector.model.Transaction;
import org.grab.com.fraud.detector.utils.IpToCountry;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CountryChangeRule implements Rule{
	
	private int minTimeBetweenCountryChangeMinutes;

	@Override
	public boolean isFraud(Transaction transaction) {
		boolean isFraud = false;
		AccountMetadata accountMetadata = AccountMetadataManager.getAccountMetadata(transaction.getFromAccount());
		String countryCode = IpToCountry.getCountry(transaction.getSourceIP());
		if(accountMetadata != null) {
			if(!countryCode.equals(accountMetadata.getCountry())){
				Timestamp currentTransactionTimestamp = transaction.getTimestamp();
				Timestamp previousTransactionTimestamp = accountMetadata.getPreviousTransactionTimestamp();
				if(previousTransactionTimestamp == null)
					previousTransactionTimestamp = accountMetadata.getFirstTransactionTimestamp();
				float timeBetweenTransactionInMinutes = (Math.abs(currentTransactionTimestamp.getTime() - previousTransactionTimestamp.getTime())/1000)/60;
				if(timeBetweenTransactionInMinutes < minTimeBetweenCountryChangeMinutes) {
					isFraud = true;
				}
			}
		}
		return isFraud;
	}
	
	

}
