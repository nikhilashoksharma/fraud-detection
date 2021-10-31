package org.grab.com.fraud.detector.business.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grab.com.fraud.detector.exceptions.IncompleteRecordException;
import org.grab.com.fraud.detector.exceptions.InvalidColumnFormatException;
import org.grab.com.fraud.detector.model.Summary;
import org.grab.com.fraud.detector.model.Transaction;
import org.grab.com.fraud.detector.model.TransactionWithValidity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class StreamedFraudDetection {

	@NonNull
	private String inputTransactionFileAbsolutePath;

	@NonNull
	private String tempOutputFile;

	@NonNull
	private Gatekeeper gatekeeper;
	
	private static Logger LOGGER = LogManager.getLogger();

	@NonNull
	private Summary summary;


	public void startFraudDetection() throws FileNotFoundException, IOException {
		try (PrintWriter writer = new PrintWriter(tempOutputFile, "UTF-8")) {
			try (BufferedReader br = new BufferedReader(new FileReader(inputTransactionFileAbsolutePath))) {
				String transactionRecord;
				while ((transactionRecord = br.readLine()) != null) {
					try {
						if(transactionRecord.startsWith("transactionId")) {
							writer.println(TransactionWithValidity.getHeader());
							continue;
						}
							
						Transaction transaction = TransactionRecordReader.read(transactionRecord);
						TransactionWithValidity transactionWithValidity = gatekeeper.validate(transaction, summary);
						writer.println(transactionWithValidity.toCSVString());
						summary.incrementSuccessfullyProessedRecord();
					} catch (InvalidColumnFormatException | IncompleteRecordException e) {
						summary.incrementErrorRecords();
						LOGGER.log(Level.ERROR, e.getMessage());
					}
					summary.incrementTotalRecords();

				}
			}
		}
	}

}
