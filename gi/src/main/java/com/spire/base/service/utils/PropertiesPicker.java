package com.spire.base.service.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Utility Class to fetch Values from a property file given a Property file and
 * Key
 * 
 * @author Garnepudi V
 *
 */

public class PropertiesPicker {
	// static String propFile;
	static String returnValue = null;

	

	/**
	 * This method accepts Property File and a Key from the file and returns
	 * value based on matching key
	 * 
	 * @param propFile
	 * @param key
	 * @return
	 */
	public static String getValues(String propFile, String key) {

		try {
			File file = new File(propFile);
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			if (properties.containsKey(key)) {
				returnValue = properties.getProperty(key);
			} else {
				System.out.println("No such key exists in property file" + "" + propFile);
				returnValue = null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

}
