package com.spire.dataprovider;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.spire.base.controller.Logging;
import com.spire.base.service.utils.PropertiesPicker;

import io.swagger.util.Json;

public class RestServiceTokenProvider {
	/*
	 * static String endPoint =
	 * "http://192.168.2.198:9190/analytics-service/api/dataanalytics/_report";
	 * static String authEndPoint =
	 * "http://192.168.2.91:8083/user-service-web/api/authentication/login";
	 */

	static String filePathForAnalytics = "C:/Repos/gi/gi/src/test/java/src/test/resources/Analytic_Service.properties";
	 
	static String authEndPoint = null;
	static String token = null;
	static int tenantId = 0;
	static String realmName = null;
	static String loginId = null;
	static String userId = null;
	static String userName = null;
	static String roleDetailsBeans = null;

	//@DataProvider(name = "getToken")
	@Parameters({ "env" })
	@BeforeClass
	public static String[] getToken(String env) {
		String values[] = { token, realmName,  String.valueOf(tenantId)};//, loginId, userId, userName, roleDetailsBeans 
		if (env.equalsIgnoreCase("staging")) {
			authEndPoint = PropertiesPicker.getValues(filePathForAnalytics, "EP_Auth_EEStaging").toString()
					+ PropertiesPicker.getValues(filePathForAnalytics, "authResource").toString();
			 
		}
		JSONObject jsonObj = new JSONObject(PropertiesPicker.getValues(filePathForAnalytics, "userDetails").toString());

		HttpURLConnection connection = null;
		try {
			System.out.println("authEndPoint" + " " + authEndPoint);
			URL url = new URL(authEndPoint);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			//connection.setRequestProperty("Accept", "application/json");
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write(jsonObj.toString());
			streamWriter.flush();
			StringBuilder stringBuilder = new StringBuilder();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(streamReader);
				String response = null;
				while ((response = bufferedReader.readLine()) != null) {
					stringBuilder.append(response + "\n");
				}
				bufferedReader.close();

				System.out.println("Service response" + " " + stringBuilder.toString());

				JSONObject jObj = new JSONObject(stringBuilder.toString());
				values[0] = jObj.getString("tokenId");
				System.out.println("tokenID extracted is" + " " + token);
				values[1] = String.valueOf(jObj.getInt("tenantId"));
				System.out.println("tenantId extracted is" + " " + tenantId);
				values[2] = jObj.getString("realmName");
				System.out.println("realmName extracted is" + " " + realmName);
				loginId = jObj.getString("loginId");
				System.out.println("loginId extracted is" + " " + loginId);
				userId = jObj.getString("userId");
				System.out.println("userId extracted is" + " " + userId);
				userName = jObj.getString("userName");
				/*System.out.println("userName extracted is" + " " + userName);
				roleDetailsBeans = jObj.getString("roleDetailsBeans");
				System.out.println("roleDetailsBeans extracted is" + " " + roleDetailsBeans);*/
				 
			} else {
				System.out.println("Error Code in Response" + " " + connection.getResponseCode());
				System.out.println("Response Body" + " " + connection.getResponseMessage());

			}
		} catch (Exception exception) {
			System.out.println("test" + " " + exception.toString());
			// return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	 
		return  values;
	}

}
