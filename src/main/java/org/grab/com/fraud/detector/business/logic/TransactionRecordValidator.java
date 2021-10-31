package org.grab.com.fraud.detector.business.logic;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.grab.com.fraud.detector.exceptions.IncompleteRecordException;
import org.grab.com.fraud.detector.exceptions.InvalidColumnFormatException;

public class TransactionRecordValidator {

	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd hh:mm:ss";

	private static final int expectedNumberOfColumns = 8;

	private static final Pattern PATTERN = Pattern
			.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	private static SimpleDateFormat expectedTimestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);

	public static boolean isValidNumberOfColumns(String record, String[] splittedRecord) throws IncompleteRecordException {
		if (splittedRecord.length == expectedNumberOfColumns)
			return true;
		else
			throw new IncompleteRecordException(record, expectedNumberOfColumns, splittedRecord.length);
	}

	public static String parseIpAddress(String record, String ipColumnName, String ipAddress)
			throws InvalidColumnFormatException {
		String expectedIpFormat = "[0-255].[0-255].[0-255].[0-255]";
		if (PATTERN.matcher(ipAddress).matches())
			return ipAddress;
		else
			throw new InvalidColumnFormatException(record, ipColumnName, expectedIpFormat, ipAddress);
	}

	public static Timestamp parseTimeStamp(String record, String timeStampColumnName, String timeStampValue)
			throws InvalidColumnFormatException {
		Timestamp columnValueAsTimestamp = null;
		try {
			columnValueAsTimestamp = new Timestamp(expectedTimestampFormat.parse(timeStampValue).getTime());
			return columnValueAsTimestamp;
		} catch (ParseException e) {
			throw new InvalidColumnFormatException(record, timeStampColumnName, TIMESTAMP_FORMAT, timeStampValue);
		}
	}
	
	public static String validateStringColumn(String record, String columnName, String expected, String actual) throws InvalidColumnFormatException {
		if(StringUtils.isNotBlank(actual))
			return StringUtils.strip(actual);
		else {
			throw new InvalidColumnFormatException(record, columnName, expected, actual);
		}
	}
}
