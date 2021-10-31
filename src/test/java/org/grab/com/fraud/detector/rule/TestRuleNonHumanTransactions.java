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
import org.grab.com.fraud.detector.rules.NonHumanTransactionRule;
import org.grab.com.fraud.detector.utils.MockitoProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class TestRuleNonHumanTransactions {
	
	static AccountMetadata accountMetadata;
	
	@Before
	public void init() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		accountMetadata = new AccountMetadata("409000362497", "SG", new Timestamp(dateFormat.parse("2021-01-01 13:00:00").getTime()) , false);
		MockitoProvider.mockMetaDataProvider.when(() -> AccountMetadataManager.getAccountMetadata(Mockito.anyString()))
				.thenReturn(accountMetadata);
		
	}
	
	@Test
	public void testIsFraudNonHumanTransaction() throws ParseException, InvalidColumnFormatException, IncompleteRecordException {
		Transaction transaction = TransactionRecordReader.read("1705402,2021-01-01 13:00:00,1.32.77.191,409000362497,409000722645,1001,SGD,39");
		NonHumanTransactionRule nonHumanTransactionRule = new NonHumanTransactionRule(10);
		assertTrue(nonHumanTransactionRule.isFraud(transaction));
	}
	
	@Test
	public void testIsFraudHumanTransaction() throws ParseException, InvalidColumnFormatException, IncompleteRecordException {
		Transaction transaction = TransactionRecordReader.read("1705402,2021-01-01 13:01:00,1.32.77.191,409000362497,409000722645,1001,SGD,39");
		NonHumanTransactionRule nonHumanTransactionRule = new NonHumanTransactionRule(10);
		assertFalse(nonHumanTransactionRule.isFraud(transaction));
	}
	
}
