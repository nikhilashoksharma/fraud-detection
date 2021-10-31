package org.grab.com.fraud.detector.utils;

import org.grab.com.fraud.detector.model.AccountMetadataManager;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


public class MockitoProvider {
	public final static MockedStatic<AccountMetadataManager> mockMetaDataProvider;

	static {
		mockMetaDataProvider = Mockito.mockStatic(AccountMetadataManager.class);
	}
	
}
