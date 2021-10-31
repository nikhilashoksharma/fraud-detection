package org.grab.com.fraud.detector.model;

import java.util.TreeMap;

public class AccountMetadataManager {
	
	private static TreeMap<String, AccountMetadata> accountMetadatDB = new TreeMap<>();
	
	public static AccountMetadata getAccountMetadata(String accountNumber) {
		return accountMetadatDB.get(accountNumber);
	}
	
	
	public static void addAccountMetadata(String accountNumber, AccountMetadata accountMetadata) {
		accountMetadatDB.put(accountNumber, accountMetadata);
	}
}
