package com.spire.dataprovider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.spire.base.service.utils.PropertiesPicker;

public class RestServiceTokenProvider {

	static JSONObject jsonObj = null;
	static String filePathForAnalytics = null;
	static String authEndPoint = null;
	static String token = null;
	static int tenantId = 0;
	static String realmName = null;
	static String loginId = null;
	static String userId = null;
	static String userName = null;
	static String roleDetailsBeans = null;

	public static String[] getToken(String env, String filePath) {
		filePathForAnalytics = filePath;
		String values[] = { token, realmName, String.valueOf(tenantId) };
		if (env.equalsIgnoreCase("staging")) {
			authEndPoint = PropertiesPicker.getValues(filePathForAnalytics, "EP_Auth_EEStaging").toString();

			jsonObj = new JSONObject(PropertiesPicker.getValues(filePathForAnalytics, "userDetails_Staging").toString());
		} else if ((env.equalsIgnoreCase("UAT")) || (env.equalsIgnoreCase("PROD"))) {
			authEndPoint = PropertiesPicker.getValues(filePathForAnalytics, "EP_AUTH_PROD").toString();
			jsonObj = new JSONObject(PropertiesPicker.getValues(filePathForAnalytics, "userDetails_UAT").toString());
		}
		authEndPoint = authEndPoint + PropertiesPicker.getValues(filePathForAnalytics, "authResource").toString();

		HttpURLConnection connection = null;
		try {
			System.out.println("authEndPoint" + " " + authEndPoint);
			URL url = new URL(authEndPoint);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

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
				System.out.println("tokenID extracted is" + " " + values[0]);
				values[1] = String.valueOf(jObj.getInt("tenantId"));
				System.out.println("tenantId extracted is" + " " + values[1]);
				values[2] = jObj.getString("realmName");
				System.out.println("realmName extracted is" + " " + values[2]);
				loginId = jObj.getString("loginId");
				System.out.println("loginId extracted is" + " " + loginId);
				userId = jObj.getString("userId");
				System.out.println("userId extracted is" + " " + userId);
				userName = jObj.getString("userName");

			} else {
				System.out.println("Error Code in Response" + " " + connection.getResponseCode());
				System.out.println("Response Body" + " " + connection.getResponseMessage());

			}
		} catch (Exception exception) {
			System.out.println("test" + " " + exception.toString());

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return values;
	}

}
