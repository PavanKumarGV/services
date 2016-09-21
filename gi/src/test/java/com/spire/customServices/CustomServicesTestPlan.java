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
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
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
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
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
		Assertion.assertTrue(response.getStatus() != 200, "Response unsuccessfull, Expected 200 status code");
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
		Assertion.assertTrue(response.getStatus() != 200, "Response unsuccessfull, Expected 200 status code");
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
		Assertion.assertTrue(response.getStatus() != 200, "Response unsuccessfull, Expected 200 status code");
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
	@Test(groups = { "getRecomendedCanByReqId" })
	public void getRecomendedCanByReqId() {

		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getRecmendedCandidateByValidReqId(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}

	/**
	 * 07/09/16 Steps: Get recommended Candidate by invalid requisition id
	 * Validation: Response code: 200
	 */
	@Test(groups = { "getRecomendedCanByInvalidReqId" })
	public void getRecomendedCanByInvalidReqId() {

		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getRecmendedCandidateByInValidReqId(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}

	/**
	 * 07/09/16 Steps: Get match score for requisition by invalid requisition id
	 * Validation: Response code: 200
	 */
	@Test(groups = { "CI", "getMatchScoreForRequisitions" })
	public void getMatchScoreForRequisitions() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getMatchScores(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(
				responseBody.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("candidateId_matchScore")),
				"Response is blank");
		Logging.log("Response is successful");
	}

	/**
	 * 07/09/16 Steps: Get match score by invalid requisition id Validation:
	 * Response code: 200
	 */
	@Test(groups = { "CI", "getMatchScoreForRequisitionsWithInvalidId" })
	public void getMatchScoreForRequisitionsWithInvalidId() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getMatchScoresInvalidId(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() != 200,
				"Response	unsuccessfull, Expected status code not equal to 200");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		System.out.println("printing response bdy");
		System.out.println("response--" + responseBody);
		Logging.log("response--" + responseBody);
	}

	/**
	 * 21/09/16 Steps: Get match score by valid requisition id and invalid
	 * application status Validation: Response code: 400
	 */
	@Test(groups = { "CI", "getMatchScoreForRequisitionsWithInvalidApplicantionStatus" })
	public void getMatchScoreForRequisitionsWithInvalidApplicantionStatus() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getMatchScoresWithInvalidStatus(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 400, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Response Successful");
		Logging.log("Response 400 for invalid applicantion status");
	}

	/**
	 * 21/09/16 Steps: Get match score by valid requisition id and NOT_APPLIED
	 * application status Validation: Response code: 200
	 */
	@Test(groups = { "CI", "getMatchScoreForRequisitionsWithNOT_AAPLIEDApplicantionStatus" })
	public void getMatchScoreForRequisitionsWithNOT_AAPLIEDApplicantionStatus() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getMatchScoresWithNOT_APPLIEDStatus(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(responseBody.contains("NOT_APPLIED"), "No candidate is present with NOT_APPLIED status");
		Logging.log("Response is successful");
	}

	/**
	 * 21/09/16 Steps: Get recommended candidates by valid requisition id
	 * Validation: Response code: 200
	 */
	@Test(groups = { "CI", "getRecommendedCandidatesByRequisitionId" })
	public void getRecommendedCandidatesByRequisitionId() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getRecommendedCandsByReqId(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(responseBody.contains("null"), "Recommended candidates available for the req id: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("GET_REC_CANDID_REQID"));
		Logging.log("No Recommended candidates available for requisition id: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("GET_REC_CANDID_REQID"));

	}

	/**
	 * 21/09/16 Steps: Get recommended candidates by valid requisition id
	 * Validation: Response code: 200
	 */
	@Test(groups = { "CI", "getRecommendedCandidatesByInvalidRequisitionId" })
	public void getRecommendedCandidatesByInvalidRequisitionId() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getRecommendedCandsByInvalidReqId(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() != 200, "Response unsuccessfull, Expected 200 status code");
		// Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(responseBody.contains("SYSTEM_ERROR"), "Recommended candidates available for the req id: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("GET_REC_CANDID_REQID").substring(1));
		Logging.log("No Recommended candidates available for requisition id: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("GET_REC_CANDID_REQID").substring(1));

	}

	/**
	 * 21/09/16 Steps: Get recommended candidates by valid requisition id
	 * Validation: Response code: 200
	 */
	@Test(groups = { "CI", "getRecommendedCandidatesByBlankRequisitionId" })
	public void getRecommendedCandidatesByBlankRequisitionId() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getRecommendedCandsByBlankReqId(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() != 200, "Response unsuccessfull, Expected 200 status code");
		// Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Recommended candidates available");
		Logging.log("Requsition id can not be blank or null");

	}

	/**
	 * 21/09/16 Steps: Get candidate status count for the requisition provided
	 * Validation: Response code: 200
	 */
	@Test(groups = { "CI", "getCandidateStatusCountPerRequisition" })
	public void getCandidateStatusCountPerRequisition() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getCandidateStatus(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		// Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(responseBody.contains("New")||responseBody.contains("Inactive")||responseBody.contains("Active")||responseBody.contains("Hired"), "Requisition has no candidates tagged");
		Logging.log("Multiple candidates tagged to the requisition "+ReadingServiceEndPointsProperties.getServiceEndPoint("GetStatusForRequisitionId")+" with statuses.");

	}
	
	/**
	 * 21/09/16 Steps: Get candidate status count for blank requisition provided
	 * Validation: Response code: 200
	 */
	@Test(groups = { "CI", "getCandidateStatusCountForBlankRequisition" })
	public void getCandidateStatusCountForBlankRequisition() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getCandidateStatusBlank(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		// Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(responseBody.contains("{}"), "Requisition candidates tagged");
		Logging.log("No candidates tagged with requisition");

	}
	
	/**
	 * 21/09/16 Steps: Get candidate status count for blank requisition provided
	 * Validation: Response code: 200
	 */
	@Test(groups = { "CI", "getCandidatesForParticularStatusForRequisition" })
	public void getCandidatesForParticularStatusForRequisition() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getCandidateForParticularStatus(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		// Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
//		Assertion.assertTrue(responseBody.contains("{}"), "Requisition candidates tagged");
		Logging.log("No candidates tagged with requisition");

	}
	
	/**
	 * 21/09/16 Steps: Get candidate status count for blank requisition provided
	 * Validation: Response code: 200
	 */
	@Test(groups = { "CI", "getCandidatesForParticularStatusWithoutRequisition" })
	public void getCandidatesForParticularStatusWithoutRequisition() {
		CustomConsumer = new CustomResourceConsumer(userId, password, hostName);
		Response response = CustomConsumer.getCandidateForParticularStatusWithoutReq(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 400, "Response unsuccessfull, Expected 200 status code");
		// Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("response--" + responseBody);
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Requisition candidates tagged");
		Logging.log("No candidates tagged with requisition");

	}

}
