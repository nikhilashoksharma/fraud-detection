package org.grab.com.fraud.detector.utils;

import java.io.InputStream;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grab.com.fraud.detector.exceptions.ConfigurationException;
import org.yaml.snakeyaml.Yaml;

public class ConfigurationManager {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private Map<String, String> configuration;
	
	private transient static ConfigurationManager configurationManager = null;
	
	public static ConfigurationManager getInstance(InputStream configurationFileName) {
		if(configurationManager != null) {
			return configurationManager;
		}else {
			synchronized (ConfigurationManager.class) {
				if(configurationManager != null) {
					return configurationManager;
				}else {
					configurationManager = new ConfigurationManager(configurationFileName);
					return configurationManager;
				}
			}
		}
	}
	
	private ConfigurationManager(InputStream configurationFileName){
		Yaml yaml = new Yaml();
		this.configuration = yaml.load(configurationFileName);
	}
	
	public enum ConfigElementName {
		RULE_BOOK_FILE_PATH("rule_book_file_path"),
		MAXMIND_DB_FILE_PATH("maxmind_db_file_path"),
		MIN_TIME_BETWEEN_TWO_TRANACTIONS_SECONDS("minTimeBetweenTwoTranactionsSeconds"),
		MIN_TIME_BETWEEN_COUNTRY_CHANGE_MINUTES("minTimeBetweenCountryChangeMinutes"),
		INVALID_TRANSACTION_LIMIT_PER_ACCOUNT("invalidTransactionLimitPerAccount");
		
		private String value;
		
		ConfigElementName(String val) {
			value = val;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	public int getMinTimeBetweenTwoTranactionsSeconds() throws ConfigurationException {
		String configElement = ConfigElementName.MIN_TIME_BETWEEN_TWO_TRANACTIONS_SECONDS.getValue();
		return extractIntConfigValue(configElement);
	}
	
	public int getMinTimeBetweenCountryChangeMinutes() throws ConfigurationException {
		String configElement = ConfigElementName.INVALID_TRANSACTION_LIMIT_PER_ACCOUNT.getValue();
		return extractIntConfigValue(configElement);
	}
	
	public int getInvalidTransactionLimitPerAccount() throws ConfigurationException {
		String configElement = ConfigElementName.INVALID_TRANSACTION_LIMIT_PER_ACCOUNT.getValue();
		return extractIntConfigValue(configElement);
	}

	private int extractIntConfigValue(String configElement) throws ConfigurationException {
		int returnValue;
		Object actualValue = configuration.get(configElement);
		if(actualValue instanceof String) {
			String actualValueStr = (String) actualValue;
			try {
				returnValue = Integer.parseInt(actualValueStr);
				return returnValue;
			}catch(NumberFormatException ex) {
				ConfigurationException configEx = new ConfigurationException(configElement, "int", actualValueStr);
				LOGGER.error(configEx.getMessage());
				throw configEx;
			}
		}else
			return (int) actualValue; 
	}
	
}
