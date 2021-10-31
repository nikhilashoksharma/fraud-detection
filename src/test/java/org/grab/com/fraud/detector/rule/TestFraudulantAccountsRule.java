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
import org.grab.com.fraud.detector.rules.FraudulantAccountsRule;
import org.grab.com.fraud.detector.utils.MockitoProvider;
import org.junit.Before;
import org.junit.Test;

public class TestFraudulantAccountsRule {
	
	static AccountMetadata accountMetadata;
	
	static AccountMetadata accountMetadata2;
	
	@Before
	public void init() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		accountMetadata = new AccountMetadata("409000362497", "SG", new Timestamp(dateFormat.parse("2021-01-01 13:00:00").getTime()) , true);
		accountMetadata2 = new AccountMetadata("409000722645", "SG", new Timestamp(dateFormat.parse("2021-01-01 13:00:00").getTime()) , true);
		MockitoProvider.mockMetaDataProvider.when(() -> AccountMetadataManager.getAccountMetadata("409000362497"))
				.thenReturn(accountMetadata);
		MockitoProvider.mockMetaDataProvider.when(() -> AccountMetadataManager.getAccountMetadata("409000722645"))
		.thenReturn(accountMetadata2);
		
	}
	
	@Test
	public void testIsFraudFromAccountIsFraud() throws ParseException, InvalidColumnFormatException, IncompleteRecordException {
		Transaction transaction = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,409000362497,409000722645,1001,SGD,39");
		FraudulantAccountsRule fraudAccountRule = new FraudulantAccountsRule();
		assertTrue(fraudAccountRule.isFraud(transaction));
	}
	
	
	@Test
	public void testIsFraudToAccountIsFraud() throws ParseException, InvalidColumnFormatException, IncompleteRecordException {
		Transaction transaction = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,300000000,409000722645,1001,SGD,39");
		FraudulantAccountsRule fraudAccountRule = new FraudulantAccountsRule();
		assertTrue(fraudAccountRule.isFraud(transaction));
	}
	
	@Test
	public void testIsFraudToAccountAndFromAccountMetadataNull() throws ParseException, InvalidColumnFormatException, IncompleteRecordException {
		Transaction transaction = TransactionRecordReader.read("1705402,2021-01-01 13:41:50,1.32.77.191,30000000000,40000000,1001,SGD,39");
		FraudulantAccountsRule fraudAccountRule = new FraudulantAccountsRule();
		assertFalse(fraudAccountRule.isFraud(transaction));
	}

}
