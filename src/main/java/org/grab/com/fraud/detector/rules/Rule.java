package org.grab.com.fraud.detector.rules;

import org.grab.com.fraud.detector.model.Transaction;

public interface Rule {
	
	public boolean isFraud(Transaction transaction);
}
