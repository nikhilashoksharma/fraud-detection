package org.grab.com.fraud.detector.business.logic;

import java.sql.Timestamp;

import org.grab.com.fraud.detector.exceptions.IncompleteRecordException;
import org.grab.com.fraud.detector.exceptions.InvalidColumnFormatException;
import org.grab.com.fraud.detector.model.Transaction;

public class TransactionRecordReader {

	private static final String TRANSACTION_ID_COLUMN_NAME = "transactionId";
	private static final String SOURCE_IP_COLUMN_NAME = "sourceIp";
	private static final String TIMESTAMP_COLUMN_NAME = "timestamp";
	private static final String TO_ACCOUNT_COLUMN_NAME = "toAccount";
	private static final String FROM_ACCOUNT_COLUMN_NAME = "fromAccount";
	private static final String STATUS_CODE_COLUMN_NAME = "statucCode";
	private static final String CURRENCY_COLUMN_NAME = "currency";
	private static final String AMOUNT_COLUMN_NAME = "amount";
	
	private static final String expectedFromStringColumn = "Not Null, Not empty";

	public static Transaction read(String transactionRecord)
			throws InvalidColumnFormatException, IncompleteRecordException {
		String[] transactionProperties = transactionRecord.split(",");
		if (TransactionRecordValidator.isValidNumberOfColumns(transactionRecord, transactionProperties)) {
			String transactionId = TransactionRecordValidator.validateStringColumn(transactionRecord,
					TRANSACTION_ID_COLUMN_NAME, expectedFromStringColumn, transactionProperties[0]);
			Timestamp timestamp;
			timestamp = TransactionRecordValidator.parseTimeStamp(transactionRecord, TIMESTAMP_COLUMN_NAME,
					transactionProperties[1]);
			String sourceId = TransactionRecordValidator.parseIpAddress(transactionRecord, SOURCE_IP_COLUMN_NAME,
					transactionProperties[2]);
			String toAccount = TransactionRecordValidator.validateStringColumn(transactionRecord,
					TO_ACCOUNT_COLUMN_NAME, expectedFromStringColumn, transactionProperties[3]);
			String fromAccount = TransactionRecordValidator.validateStringColumn(transactionRecord,
					FROM_ACCOUNT_COLUMN_NAME, expectedFromStringColumn, transactionProperties[4]);
			String statusCode = TransactionRecordValidator.validateStringColumn(transactionRecord,
					STATUS_CODE_COLUMN_NAME, expectedFromStringColumn, transactionProperties[5]);
			String currency = TransactionRecordValidator.validateStringColumn(transactionRecord,
					CURRENCY_COLUMN_NAME, expectedFromStringColumn, transactionProperties[6]);
			String amount = TransactionRecordValidator.validateStringColumn(transactionRecord,
					AMOUNT_COLUMN_NAME, expectedFromStringColumn, transactionProperties[7]);
			return new Transaction(transactionId, timestamp, sourceId, toAccount, fromAccount, statusCode, currency,
					amount);
		}
		return null;
	}

}
