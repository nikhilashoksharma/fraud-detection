package org.grab.com.fraud.detector.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.grab.com.fraud.detector.business.logic.TransactionRecordReader;
import org.grab.com.fraud.detector.exceptions.IncompleteRecordException;
import org.grab.com.fraud.detector.exceptions.InvalidColumnFormatException;
import org.grab.com.fraud.detector.model.AccountMetadata;
import org.grab.com.fraud.detector.model.AccountMetadataManager;
import org.grab.com.fraud.detector.model.Transaction;
import org.grab.com.fraud.detector.rules.StatusCodeBasedRule;
import org.grab.com.fraud.detector.utils.MockitoProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class TestStatusCodeBasedRule {
	
	static AccountMetadata accountMetadata;
	
	@Before
	public void before() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		accountMetadata = new AccountMetadata("409000362497", "SG", new Timestamp(dateFormat.parse("2021-01-01 13:00:00").getTime()) , false);
		MockitoProvider.mockMetaDataProvider.when(() -> AccountMetadataManager.getAccountMetadata(Mockito.anyString()))
				.thenReturn(accountMetadata);
		
	}

	@Test
	public void testIsFraudWhenNotFraudOnIncorrectPinStatusCode() throws ParseException, InvalidColumnFormatException, IncompleteRecordException {
		Transaction transaction = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1006,SGD,39");
		StatusCodeBasedRule statusCodeBaseRule = new StatusCodeBasedRule(2);
		assertFalse(statusCodeBaseRule.isFraud(transaction));
	}
	
	@Test
	public void testIsFraudWhenFraudOnIncorrectPinStatusCode() throws ParseException, InvalidColumnFormatException, IncompleteRecordException {
		Transaction transaction = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1006,SGD,39");
		StatusCodeBasedRule statusCodeBaseRule = new StatusCodeBasedRule(2);
		assertFalse(statusCodeBaseRule.isFraud(transaction));
		Transaction transaction2 = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1006,SGD,39");
		assertFalse(statusCodeBaseRule.isFraud(transaction2));
		Transaction transaction3 = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1006,SGD,39");
		assertTrue(statusCodeBaseRule.isFraud(transaction3));
	}
	
	@Test
	public void testIsFraudWhenFraudOnInSufficientBalance() throws ParseException, InvalidColumnFormatException, IncompleteRecordException {
		Transaction transaction = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1005,SGD,39");
		StatusCodeBasedRule statusCodeBaseRule = new StatusCodeBasedRule(2);
		assertTrue(statusCodeBaseRule.isFraud(transaction));
	}
	
	@Test
	public void testIsFraudWhenFraudOnTransactionTimeout() throws ParseException, InvalidColumnFormatException, IncompleteRecordException {
		Transaction transaction = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1008,SGD,39");
		StatusCodeBasedRule statusCodeBaseRule = new StatusCodeBasedRule(2);
		assertFalse(statusCodeBaseRule.isFraud(transaction));
		Transaction transaction2 = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1008,SGD,39");
		assertFalse(statusCodeBaseRule.isFraud(transaction2));
		Transaction transaction3 = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1006,SGD,39");
		assertTrue(statusCodeBaseRule.isFraud(transaction3));
	}

}
