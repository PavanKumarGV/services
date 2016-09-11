package com.spire.customServices;

import java.io.IOException;
import javax.ws.rs.core.Response;
import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.Constants;
import com.spire.base.service.ReadingServiceEndPointsProperties;
import com.spire.base.service.utils.RequisitionResourceServiceUtil;
import com.spire.service.consumers.CustomResourceConsumer;
import com.spire.service.consumers.RequisitionResourceConsumer;
import com.spire.service.consumers.SearchResourcesConsumer;

import spire.talent.gi.beans.RequisitionStatusBean;
import spire.talent.gi.beans.SearchRequisitionRequestBean;

public class CustomServicesTestPlan extends TestPlan {

	String hostName;
	String userId;
	String password;
	CustomResourceConsumer CustomConsumer = null;

	/**
	 * Passing HostName,UserName and Password from the xml.
	 */
	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
		userId = ReadingServiceEndPointsProperties.getServiceEndPoint("user_Id");
		password = ReadingServiceEndPointsProperties.getServiceEndPoint("password");
		Logging.log("Start :: Login with Username: " + userId + "Password: " + password + "and hostName: " + hostName);

	}

	/**
	 * 07/09/16 Steps: Get recommended requisition by candidate id Validation:
	 * Response code: Recommended Requisition Id
	 */
	@Test(groups = { "CI", "getRecomendedReqByCandIdAndLimit", "P1" })
	public void getRecomendedReqByCandIdAndLimit() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getrequisitionsuggestionbyallvalidinput(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response	unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}

	/**
	 * 07/09/16 Steps: Get recommended requisition by candidate id Validation:
	 * Response code: 400
	 */
	@Test(groups = { "CI", "getRecomendedReqByCandId" })
	public void getRecomendedReqByCandId() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getreqsuggestionbyid(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() != 200, "Response	unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}

	/**
	 * 07/09/16 Steps: Get recommended requisition by limit Validation: Response
	 * code: 400
	 */
	@Test(groups = { "CI", "getRecomendedReqByLimit" })
	public void getRecomendedReqByLimit() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getreqsuggestionbylimit(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() != 200, "Response	unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}

	/**
	 * 07/09/16 Steps: Get recommended requisition by blank field Validation:
	 * Response code: 400
	 */
	@Test(groups = { "CI", "getRecomendedReqwithoutidandlimit" })
	public void getRecomendedReqwithoutidandlimit() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getreqsuggestionwithoutidnadlimit(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() != 200, "Response	unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}

	/**
	 * 07/09/16 Steps: Get recommended requisition by blank field Validation:
	 * Response code: 400
	 */
	@Test(groups = { "CI", "verifyReqSuggestionWithInvalidCandidateId" })
	public void verifyReqSuggestionWithInvalidCandidateId() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getreqsuggestionwithinvalidid(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() != 200, "Response	unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}

	/**
	 * 07/09/16 Steps: Get recommended Candidate by requisition id Validation:
	 * Response code: 200
	 */
	@Test(groups = { "CI", "getRecomendedCanByReqId" })
	public void getRecomendedCanByReqId() {

		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getRecmendedCandidateByValidReqId(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response	unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}
	
	/**
	 * 07/09/16 Steps: Get recommended Candidate by invalid requisition id Validation:
	 * Response code: 200
	 */
	@Test(groups = { "CI", "getRecomendedCanByInvalidReqId" })
	public void getRecomendedCanByInvalidReqId() {

		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getRecmendedCandidateByInValidReqId(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response	unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}
	
	/**
	 * 07/09/16 Steps: Get recommended Candidate by invalid requisition id Validation:
	 * Response code: 200
	 */
	@Test(groups = { "CI", "getMatchScoreForRequisitions" })
	public void getMatchScoreForRequisitions() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getMatchScores(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response	unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(responseBody.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("candidateId_matchScore")), "Response is blank");
		Logging.log("Response is successful");
	}
	
	/**
	 * 07/09/16 Steps: Get recommended Candidate by invalid requisition id Validation:
	 * Response code: 200
	 */
	@Test(groups = { "CI", "getMatchScoreForRequisitionsWithInvalidId" })
	public void getMatchScoreForRequisitionsWithInvalidId() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getMatchScoresInvalidId(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() != 200, "Response	unsuccessfull, Expected status code not equal to 200");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}
}
