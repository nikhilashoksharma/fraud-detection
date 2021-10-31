package org.grab.com.fraud.detector.model;

public enum StatusCodeEnum {
	
	SUCCESS("1001"),
	INSUFFICIENT_BALANCE("1005"),
	INCORRECT_OTP_OR_PIN("1006"),
	DAILY_LIMIT_EXCEEDED("1007"),
	TRANSACTION_TIMEOUT("1008");
	
	private String value;
	
	private StatusCodeEnum(String val) {
		value = val;
	}
	
	public String getValue() {
		return value;
	}
	public static StatusCodeEnum customValueOf(String value) {
	    for (StatusCodeEnum statusCodeEnum : values()) {
	        if (statusCodeEnum.value.equals(value)) {
	            return statusCodeEnum;
	        }
	    }
	    return null;
	}
}
