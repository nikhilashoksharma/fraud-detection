package org.grab.com.fraud.detector.rules;

import org.grab.com.fraud.detector.exceptions.ConfigurationException;
import org.grab.com.fraud.detector.utils.ConfigurationManager;

public class RuleFactory {
	
	
	public static Rule getRuleObject(String ruleName, ConfigurationManager configurationManager) throws ConfigurationException {
		Rule rule = null;
		switch(ruleName) {
		case "FraudulantAccounts":
			rule = new FraudulantAccountsRule();
			break;
		case "NonHumanTransaction":
			rule = new NonHumanTransactionRule(configurationManager.getMinTimeBetweenTwoTranactionsSeconds());
			break;
		case "CountryChange":
			rule = new CountryChangeRule(configurationManager.getMinTimeBetweenCountryChangeMinutes());
			break;
		case "StatusBased":
			rule = new StatusCodeBasedRule(configurationManager.getInvalidTransactionLimitPerAccount());
			break;
		default:
			rule = new FraudulantAccountsRule();
			break;
		}
		return rule;
		
	}

}
