package com.car.config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * This class get the configuration from the properties file.
 *
 * @author xx
 * @version 1.0
 * @since 17/08/2016
 */
public class Configuration {
	
	
	/**
	 * get the value of properties for a given key from the config.properties file
	 *
	 * @param key the key
	 * @return the property
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getProperty(String key) throws IOException {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		String propValue = "";

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		propValue = prop.getProperty(key);
		inputStream.close();
		return (propValue==null)?"":propValue;
	}
	
	/**
	 * load log properties file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadLogProperties() throws IOException {
		Properties props = new Properties();
		props.load(getClass().getResourceAsStream(this.getProperty("LOG_PROPERTIES")));
		PropertyConfigurator.configure(props);
	}


}
