package org.grab.com.fraud.detector.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URISyntaxException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;

public class IpToCountry {
	
	private static DatabaseReader dbReader;
	
	static {
		
		try {
			InputStream database = IpToCountry.class.getClassLoader().getResourceAsStream("db/GeoLite2-Country.mmdb");
			dbReader = new DatabaseReader.Builder(database).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getCountry(String ip){
		InetAddress ipAddress;
		String countryCode = null;
		try {
			ipAddress = InetAddress.getByName(ip);
			CountryResponse response = dbReader.country(ipAddress);
			countryCode = response.getCountry().getIsoCode();
		} catch (IOException | GeoIp2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return countryCode;
	}

}
