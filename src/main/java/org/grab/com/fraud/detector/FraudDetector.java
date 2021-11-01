package org.grab.com.fraud.detector;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grab.com.fraud.detector.business.logic.Gatekeeper;
import org.grab.com.fraud.detector.business.logic.StreamedFraudDetection;
import org.grab.com.fraud.detector.exceptions.ConfigurationException;
import org.grab.com.fraud.detector.exceptions.InvalidArgumentException;
import org.grab.com.fraud.detector.exceptions.InvalidArgumentLengthException;
import org.grab.com.fraud.detector.model.Summary;
import org.grab.com.fraud.detector.utils.ConfigurationManager;
import org.grab.com.fraud.detector.utils.FilePaths;

public class FraudDetector {

	private static final Logger LOGGER = LogManager.getLogger();

	private static final int expectedArgumentLength = 1;

	private static final String argumentList = "input data absolute path";

	public static void main(String[] args) throws Exception {

		checkArgumentLength(args);
		
		ConfigurationManager configurationManager = ConfigurationManager
				.getInstance(FraudDetector.class.getResourceAsStream(FilePaths.SYSTEM_CONFIG_FILE_PATH));
		
		
		Gatekeeper gatekeeper = new Gatekeeper(configurationManager, FraudDetector.class.getResourceAsStream(FilePaths.RULE_BOOK_FILE_PATH));
		
		Summary summary = new Summary();
		
		try {
			gatekeeper.readRuleBook();
		} catch (URISyntaxException | IOException | ConfigurationException e) {
			LOGGER.error("Exception while creating Gate keeper using rule book!");
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		Path inputTransactionFileAbsolutePath = validateInputDataPath(args);
		Path outputTempFile = getOutputTempFile(inputTransactionFileAbsolutePath);
		
		StreamedFraudDetection streamedFraudDetection = new StreamedFraudDetection(inputTransactionFileAbsolutePath.toString(), outputTempFile.toString(), gatekeeper, summary);
		
		try {
			streamedFraudDetection.startFraudDetection();
		} catch (IOException e) {
			LOGGER.error("Exception while performing fraud detection message!");
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		renameTempToInputFileName(inputTransactionFileAbsolutePath, outputTempFile);
		
		printSummaryLogs(summary, inputTransactionFileAbsolutePath);
	}

	private static void printSummaryLogs(Summary summary, Path inputTransactionFileAbsolutePath) {
		LOGGER.debug("Successfully completed the fraud detection process");
		LOGGER.debug("Output can be found in same file as input with one additional column.");
		LOGGER.debug("Output file path: "+inputTransactionFileAbsolutePath.toString());
		LOGGER.debug(summary);
	}

	private static void renameTempToInputFileName(Path inputTransactionFileAbsolutePath, Path outputTempFile) throws IOException {
		Path outputTempDirectory = outputTempFile.getParent();
		outputTempFile.toFile().renameTo(inputTransactionFileAbsolutePath.toFile());
		Files.delete(outputTempDirectory);
	}

	private static void checkArgumentLength(String[] args) throws InvalidArgumentLengthException {
		if(args.length < expectedArgumentLength) {
			throw new InvalidArgumentLengthException(args, expectedArgumentLength, argumentList);
		}
	}

	private static Path getOutputTempFile(Path inputTransactionFileAbsolutePath) throws IOException {
		String inputDataParentFolder = inputTransactionFileAbsolutePath.getParent().toString();
		Path outputTempDirectory = Paths.get(inputDataParentFolder + File.separator + "tmp");
		Files.createDirectories(outputTempDirectory);
		return Paths.get(outputTempDirectory + File.separator + inputTransactionFileAbsolutePath.getFileName().toString());
	}

	private static Path validateInputDataPath(String[] args) throws InvalidArgumentException {
		Path inputTransactionFileAbsolutePath = Paths.get(args[0]);
		if(!inputTransactionFileAbsolutePath.isAbsolute() && !inputTransactionFileAbsolutePath.toFile().exists())
			throw new InvalidArgumentException(inputTransactionFileAbsolutePath);
		return inputTransactionFileAbsolutePath;
	}

}
