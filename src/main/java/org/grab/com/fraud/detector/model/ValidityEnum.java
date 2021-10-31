package org.grab.com.fraud.detector.model;

public enum ValidityEnum {
	
	Valid("valid"),
	Fraud("fraud");

	private final String value;
	
	ValidityEnum(final String validity) {
		value = validity;
	}
	
	 public String getValue() { 
		 return value; 
	 }
}
