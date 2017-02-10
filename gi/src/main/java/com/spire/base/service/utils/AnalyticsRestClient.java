/**
 * 
 */
package com.spire.base.service.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Parameters;

import com.spire.dataprovider.RestServiceTokenProvider;

/**
 * @author Garnepudi V
 *
 */
public class AnalyticsRestClient {

	static Map<String, String> headers = new TreeMap<String, String>();
	static String filePathForAnalytics = "C:/Repos/gi/gi/src/test/java/src/test/resources/Analytic_Service.properties";
	static String endPoint = null;
	static String[] tokenValues = null;

	@Parameters({ "env" })
	public AnalyticsRestClient(String env) {
		if (env.equalsIgnoreCase("staging")) {

			endPoint = PropertiesPicker.getValues(filePathForAnalytics, "endPoint_EEStaging").toString()
					+ PropertiesPicker.getValues(filePathForAnalytics, "analyticsResource").toString();
			tokenValues = RestServiceTokenProvider.getToken(env);

			headers.put("Content-Type", "application/json");
			headers.put("realmName", tokenValues[2]);
			headers.put("tetantId", tokenValues[1]);
			headers.put("Authorization", "Bearer" + " " + tokenValues[0]);
			System.out.println("Token obatined is " + " " + tokenValues[0]);
			System.out.println("endPoint obatined is " + " " + endPoint);

		}

	}

	public int []  postServiceResp(String reqBody) {

		String response = null;
		JSONObject jsonObj = null;
		String hasError = null;
		JSONArray errors = null;
		JSONArray values = null;
		JSONArray resultValues = null;
		int[] respValues = new int[2];
		try {

			URL url = new URL(endPoint);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");

			for (Map.Entry<String, String> iterateValues : headers.entrySet()) {
				httpConn.setRequestProperty(iterateValues.getKey(), iterateValues.getValue());
				System.out.println("Header key " + " " + iterateValues.getKey());
				System.out.println("Header Values " + " " + iterateValues.getValue());

			}
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);

			System.out.println("Request Body fetch is" + " " + reqBody);
			reqBody = reqBody.replaceFirst("valueForUsecase", "matchesviewedperuser");
			reqBody = reqBody.replaceFirst("valueForSiteId", "13");
			reqBody = reqBody.replaceFirst("valueForFrom", "1");
			reqBody = reqBody.replaceFirst("valueForTo", "" + Calendar.getInstance().getTimeInMillis() + "");

			System.out.println("Request Body updated to" + " " + reqBody);
			jsonObj = new JSONObject(reqBody);
			httpConn.connect();

			OutputStreamWriter streamWriter = new OutputStreamWriter(httpConn.getOutputStream());
			streamWriter.write(jsonObj.toString());
			streamWriter.flush();
			StringBuilder stringBuilder = new StringBuilder();
			if (httpConn.getResponseCode() == 200) {
				InputStreamReader streamReader = new InputStreamReader(httpConn.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(streamReader);

				while ((response = bufferedReader.readLine()) != null) {
					stringBuilder.append(response + "\n");
				}
				bufferedReader.close();

				System.out.println("Analytics Service success response" + " " + stringBuilder.toString());
			 

				jsonObj = new JSONObject(stringBuilder.toString());
				errors = jsonObj.getJSONArray("errors");
				JSONObject responseContent = jsonObj.getJSONObject("response");
				  resultValues = responseContent.getJSONArray("result");
				if(resultValues.length()>=0){
						values	= resultValues.getJSONObject(0).getJSONArray("values");
						System.out.println("Count of values found" + " " + values.get(0).toString());
						System.out.println("Number of records found" + " " + values.getJSONObject(0).getInt("value"));
						respValues[0]=httpConn.getResponseCode();
						respValues[1]=resultValues.length();
				}
				System.out.println("hasError" + " " + hasError);
				System.out.println("errors JSON Array Length" + " " + errors.length());
				

			} else {
				System.out.println("Analytics Service error response" + " " + httpConn.getResponseCode());
				jsonObj = new JSONObject(response);
				errors = jsonObj.getJSONArray("errors");
				jsonObj = new JSONObject(stringBuilder.toString());
				errors = jsonObj.getJSONArray("errors");
				JSONObject responseContent = jsonObj.getJSONObject("response");
				  resultValues = responseContent.getJSONArray("result");
				System.out.println("hasError" + " " + hasError);
				System.out.println("errors JSON Array Length" + " " + errors.length());
				respValues[0]=httpConn.getResponseCode();
				respValues[1]=resultValues.length();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return respValues;

	}

}
