package org.grab.com.fraud.detector.business.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.grab.com.fraud.detector.exceptions.ConfigurationException;
import org.grab.com.fraud.detector.model.AccountMetadata;
import org.grab.com.fraud.detector.model.AccountMetadataManager;
import org.grab.com.fraud.detector.model.Summary;
import org.grab.com.fraud.detector.model.Transaction;
import org.grab.com.fraud.detector.model.TransactionWithValidity;
import org.grab.com.fraud.detector.rules.Rule;
import org.grab.com.fraud.detector.rules.RuleFactory;
import org.grab.com.fraud.detector.utils.ConfigurationManager;
import org.grab.com.fraud.detector.utils.IpToCountry;

public class Gatekeeper {
	
	private List<Rule> rules;
	
	private ConfigurationManager configurationManager;
	
	private InputStream ruleBookStream;
	
	public Gatekeeper(ConfigurationManager configurationManager, InputStream ruleBookStream){
		rules = new ArrayList<>();
		this.configurationManager = configurationManager;
		this.ruleBookStream = ruleBookStream;
	}
	
	public void readRuleBook() throws URISyntaxException, FileNotFoundException, IOException, ConfigurationException {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(this.ruleBookStream))){
				String ruleName = null;
				while((ruleName = br.readLine()) != null) {
					this.rules.add(RuleFactory.getRuleObject(ruleName, configurationManager));
				}
			}
	}
	
	public TransactionWithValidity validate(Transaction transaction, Summary summary) {
		boolean isFraud = rules.stream().anyMatch(rule -> rule.isFraud(transaction));
		updateSummary(summary, isFraud);
		TransactionWithValidity transactionWithValidity = new TransactionWithValidity(transaction, isFraud);
		updateAccountMetadata(transactionWithValidity);
		return transactionWithValidity;
	}

	private void updateSummary(Summary summary, boolean isFraud) {
		if(isFraud)
			summary.incrementFraudRecords();
		else
			summary.incrementValidRecords();
	}
	
	public void updateAccountMetadata(TransactionWithValidity transactionWithValidity) {
		Transaction transaction = transactionWithValidity.getTransaction();
		String fromAccount = transaction.getFromAccount();
		AccountMetadata accountMetadata = AccountMetadataManager.getAccountMetadata(fromAccount);
		if(accountMetadata == null) {
			String country = IpToCountry.getCountry(transaction.getSourceIP());
			accountMetadata = new AccountMetadata(fromAccount, country, transaction.getTimestamp(), transactionWithValidity.isFraud());
			AccountMetadataManager.addAccountMetadata(fromAccount, accountMetadata);
		}else {
			accountMetadata.setPreviousTransactionTimestamp(transaction.getTimestamp());
			accountMetadata.incrementTransaction();
		}
	}

}
