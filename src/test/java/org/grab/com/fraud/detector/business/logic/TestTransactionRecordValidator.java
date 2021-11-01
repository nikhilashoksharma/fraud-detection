package org.grab.com.fraud.detector.business.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.grab.com.fraud.detector.exceptions.IncompleteRecordException;
import org.grab.com.fraud.detector.exceptions.InvalidColumnFormatException;
import org.junit.Test;

public class TestTransactionRecordValidator {

	String sampleValidRecord = "1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1001,SGD,39";

	String sampleInValidRecord = "1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1001,SGD";

	String sampleRecordValidIp = "1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1001,SGD,39";

	String sampleRecordInValidIp = "1705402,2021-01-01 13:41:50,32.77.191,409000362497,409000722645,1001,SGD,39";

	String sampleValidRecordValidTimestamp = "1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1001,SGD,39";

	String sampleValidRecordInvalidTimestamp = "1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1001,SGD,39";
	
	String sampleRecordInvalidColumnValue = "1705402,2021-01-01 13:41:50,1.32.77.191, ,409000722645,1001,SGD,39";

	@Test
	public void testIsValidNumberOfColumnsPositive() throws IncompleteRecordException {
		assertTrue(TransactionRecordValidator.isValidNumberOfColumns(sampleValidRecord, sampleValidRecord.split(",")));
	}

	@Test(expected = IncompleteRecordException.class)
	public void testIsValidNumberOfColumnsNegative() throws IncompleteRecordException {
		TransactionRecordValidator.isValidNumberOfColumns(sampleInValidRecord, sampleInValidRecord.split(","));
	}

	@Test
	public void testParseIpAddressPositive() throws IncompleteRecordException, InvalidColumnFormatException {
		String expected = "1.32.77.191";
		String actual = TransactionRecordValidator.parseIpAddress(sampleRecordValidIp, "sourceIp",
				sampleValidRecord.split(",")[2]);
		assertEquals(expected, actual);

	}

	@Test(expected = InvalidColumnFormatException.class)
	public void testParseIpAddressNegative() throws InvalidColumnFormatException {
		TransactionRecordValidator.parseIpAddress(sampleRecordInValidIp, "sourceIp",
				sampleRecordInValidIp.split(",")[2]);
	}

	@Test
	public void testParseTimeStampPositive() throws IncompleteRecordException, InvalidColumnFormatException, ParseException {

		Timestamp expected = new Timestamp(
				TransactionRecordValidator.expectedTimestampFormat.parse("2021-01-01 13:41:50,1.32.77.191").getTime());
		Timestamp actual = TransactionRecordValidator.parseTimeStamp(sampleValidRecordValidTimestamp, "timestamp",
				sampleValidRecordValidTimestamp.split(",")[1]);
		assertEquals(expected, actual);

	}

	@Test(expected = InvalidColumnFormatException.class)
	public void testParseTimeStampNegative() throws InvalidColumnFormatException {
		TransactionRecordValidator.parseIpAddress(sampleValidRecordInvalidTimestamp, "timestamp",
				sampleValidRecordInvalidTimestamp.split(",")[1]);
	}

	@Test
	public void testValidateStringColumnPositive() throws InvalidColumnFormatException{
		String expected = "409000362497";
		String actual = TransactionRecordValidator.validateStringColumn(sampleValidRecord, "toAccount", "Not null"
				,sampleValidRecord.split(",")[3]);
		assertEquals(expected, actual);

	}
	
	@Test(expected = InvalidColumnFormatException.class)
	public void testValidateStringColumnNegative() throws InvalidColumnFormatException{
		TransactionRecordValidator.validateStringColumn(sampleRecordInvalidColumnValue, "toAccount", "Not null"
				,sampleRecordInvalidColumnValue.split(",")[3]);
	}
}
