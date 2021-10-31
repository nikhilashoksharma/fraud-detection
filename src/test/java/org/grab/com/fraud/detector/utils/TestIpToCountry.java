package org.grab.com.fraud.detector.utils;

import java.io.IOException;

import org.junit.Test;

import com.maxmind.geoip2.exception.GeoIp2Exception;

public class TestIpToCountry {
	
	@Test
	public void testIpToCountry() throws IOException, GeoIp2Exception {
		System.out.println(IpToCountry.getCountry("1.32.77.190"));
	}

}
