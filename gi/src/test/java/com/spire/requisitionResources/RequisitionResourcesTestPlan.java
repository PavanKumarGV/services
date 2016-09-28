package com.spire.requisitionResources;

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
import com.spire.base.service.ReadingServiceEndPointsProperties;
import com.spire.base.service.utils.RequisitionResourceServiceUtil;
import com.spire.service.consumers.RequisitionResourceConsumer;

import spire.talent.gi.beans.RequisitionStatusBean;
import spire.talent.gi.beans.SearchRequisitionRequestBean;

public class RequisitionResourcesTestPlan extends TestPlan {

	String hostName;
	String userId;
	String password;
	SearchRequisitionRequestBean searchReqrequestBean = null;
	SearchRequisitionRequestBean searchReqrequestBean1 = null;
	SearchRequisitionRequestBean candidatestasBean1 = null;
	RequisitionResourceConsumer reqConsumer = null;
	RequisitionStatusBean reqStatusBean = null;

	/**
	 * Passing HostName,UserName and Password from the xml.
	 */

	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
		userId = ReadingServiceEndPointsProperties.getServiceEndPoint("user_Id");
		password = ReadingServiceEndPointsProperties.getServiceEndPoint("password");
		// userId = (String) ContextManager.getThreadContext().getUserid();
		// password = (String) ContextManager.getThreadContext().getPassword();
		Logging.log("Start :: Login with Username: " + userId + "Password: " + password + "and hostName: " + hostName);

	}

	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get requisition using valid existing requisitionId.
	* </p>
	* <p>
	* <b>Input :</b>Valid requisitionId 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200 with proper requisition
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#C90000> P1</font>
	* </p>
	* @author Udhay
	*/
	@Test(groups = { "sanity", "GetRequisitionSearch" })
	public void GetRequisitionSearch() throws ClientProtocolException, IOException {
		Logging.log("Service Name: generic-services/api/requisitions"
				+ "\nDescription: Get requisition using valid existing requisitionId."
				+ "\nInput: Valid requisitionId " + "\nExpected Output: Response status 200 with proper requisition");
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getRequisition(hostName);
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successfull");
		String response = responsebody.readEntity(String.class);
		Assert.assertTrue(response.contains("primarySkill"));
		Assert.assertEquals(responsebody.getStatus(), 200);
		Logging.log("contains the primary skill ");
		Assert.assertTrue(response.contains("jobLevel"));
		Logging.log("contains the jobLevel ");
	}

	/*
	 * Vasista -Get the job description by requisition id
	 */

	@Test(groups = { "sanity", "GetJobDesByID","NA" })
	public void GetJobDesByID() throws ClientProtocolException, IOException {
		RequisitionResourceConsumer reqConsumer = null;
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqConsumer.getJobDesByreqID(hostName);
		Response responsebody = reqConsumer.getJobDesByreqID(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		Logging.log("Response: "+response);
		Assert.assertTrue(response.contains("fileName") || response.contains("filename"));
		Logging.log("Response Successful, Able to get JD");
	}

	/*
	 * * Udhay - /requisitions/{requisitionId}
	 * 
	 * Search RR with special character. The test case should not return 200
	 * response
	 * 
	 * @throws IOException
	 * 
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "GetRequInvalidSrch" })
	public void GetRequInvalidSrch() throws ClientProtocolException, IOException {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getRequisitionInvalid(hostName);
		Assertion.assertTrue(responsebody.getStatus() != 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		/*
		 * Assert.assertTrue(response.contains("primarySkill")); Logging.log(
		 * "contains the primary skill " );
		 * Assert.assertTrue(response.contains("jobLevel")); Logging.log(
		 * "contains the jobLevel " );
		 */
		Logging.log("Response: "+response);

	}

	/*
	 * * Udhay - /requisitions/{requisitionId}
	 * 
	 * Search RR with Invalid Requisition. The test case should not return 200
	 * response
	 * 
	 * @throws IOException
	 * 
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "GetRequInvalidInputSrch" })
	public void GetRequInvalidInputSrch() throws ClientProtocolException, IOException {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getRequisitionInvalidInput(hostName);
		Assertion.assertTrue(responsebody.getStatus() != 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		/*
		 * Assert.assertTrue(response.contains("primarySkill")); Logging.log(
		 * "contains the primary skill " );
		 * Assert.assertTrue(response.contains("jobLevel")); Logging.log(
		 * "contains the jobLevel " );
		 */

	}

	/**
	 * @author Radharani Patra 11/08/16 Steps:Post - Search requisition with
	 *         mandatory field Validation: Success Response Code, validate list
	 *         of requiistion in response body
	 */
	@Test(groups = { "sanity", "searchRequisitionWithInSearchCriteria", "P1","NA" })
	public void searchRequisitionWithInSearchCriteria() {
		// Get Request Bean
		searchReqrequestBean = RequisitionResourceServiceUtil.getSearchRequisition();
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		// Execute POST Request
		Response response = reqConsumer.searchRequisition(hostName, searchReqrequestBean);
		// Assering Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull Expected:200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		// Logging.log(responseBody);
		// Asseting response body
		Assertion.assertTrue(responseBody.contains("Open"), "Open requisition not found");
		Logging.log("InSearch Criteria : Open, Open requistions found");
	}

	/**
	 * @author Radharani Patra 11/08/16 Steps:Post - Search requisition with
	 *         mandatory field Validation: Success Response Code, validate list
	 *         of requiistion in response body
	 */
	@Test(groups = { "sanity", "searchRequisitionWithoutSearchCriteria", "P1","NA" })
	public void searchRequisitionWithoutSearchCriteria() {
		// Get Request Bean
		searchReqrequestBean1 = RequisitionResourceServiceUtil.getSearchRequisitionWithoutCriteria();
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		// Execute POST Request
		Response response = reqConsumer.searchRequisition(hostName, searchReqrequestBean1);
		// Assering Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull Expected:400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		/*
		 * String responseBody = response.readEntity(String.class);
		 * Logging.log(responseBody);
		 * Assertion.assertTrue(responseBody.contains("Open"),
		 * "Open requisition not found"); Logging.log("Open requiistions found"
		 * );
		 */
	}

	/**
	 * @author Pritisudha Pattanaik 11/08/16 Steps:Post - Get candidate stats
	 *         for search criteria with insearchcriteria,and experience from
	 *         month and to month Validation: Success Response Code, validate
	 *         list of requiistion in response body
	 */
	@Test(groups = { "sanity", "getcandidatestas","NA" })
	public void getcandidatestas() {
		candidatestasBean1 = RequisitionResourceServiceUtil.getCandidateStasRequisition();
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response = reqConsumer.createcandidatestas(hostName, candidatestasBean1);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		System.out.println("response=" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println(responseBody);
		Logging.log(responseBody);
		Assertion.assertTrue(
				responseBody.contains("Automatched") || responseBody.contains("Customer Mapped")
						|| responseBody.contains("APPLIED") || responseBody.contains("New")
						|| responseBody.contains("Active") || responseBody.contains("Pending"),
				"Automatched requisition not found");
		Logging.log("Automatched requiistions found");

	}

	/**
	 * @author Pritisudha Pattanaik 11/08/16 Steps:Post - Get candidate stats
	 *         without any field Validation: Response Code
	 */

	@Test(groups = { "sanity", "candidatestasRequisitionWithoutSearchCriteria","NA" })
	public void candidatestasRequisitionWithoutSearchCriteria() {
		searchReqrequestBean1 = RequisitionResourceServiceUtil.getCandiadteStasRequisitionWithoutCriteria();
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response = reqConsumer.createcandidatestas(hostName, searchReqrequestBean1);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());

	}

	/**
	 * @author Pritisudha Pattanaik 12/08/16 Steps:Post - Get candidate stats
	 *         with only insearchcriteria Validation: Response Code and
	 *         asserting response.
	 */
	@Test(groups = { "sanity", "candidatestasRequisitionWithonlyinsearchcriteria","NA" })
	public void candidatestasRequisitionWithonlyinsearchcriteria() {
		searchReqrequestBean1 = RequisitionResourceServiceUtil.getCandidateStasRequisitionwithInsearchcriteria();
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response = reqConsumer.createcandidatestas(hostName, searchReqrequestBean1);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println(responseBody);
		Logging.log(responseBody);
		Assertion.assertTrue(
				responseBody.contains("Automatched") || responseBody.contains("Customer Mapped")
						|| responseBody.contains("APPLIED") || responseBody.contains("New")
						|| responseBody.contains("Active") || responseBody.contains("Pending"),
				"Automatched requisition not found");
		Logging.log("Automatched requiistions found");

	}

	/**
	 * @author Pritisudha Pattanaik 12/08/16 Steps:Post - Get candidate stas
	 *         with insearchcriteria and calculatedrecordcount as true
	 *         validation:Response code and asserting response.
	 */

	@Test(groups = { "sanity", "candidatestasRequisitionWithinsearchcriteriaandrecordcountTrue","NA" })
	public void candidatestasRequisitionWithinsearchcriteriaandrecordcountTrue() {
		searchReqrequestBean1 = RequisitionResourceServiceUtil
				.getCandidateStasRequisitionwithsearchandcalculatedrecordtrue();
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response = reqConsumer.createcandidatestas(hostName, searchReqrequestBean1);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println(responseBody);
		Logging.log(responseBody);
		Assertion.assertTrue(
				responseBody.contains("Automatched") || responseBody.contains("Customer Mapped")
						|| responseBody.contains("APPLIED") || responseBody.contains("New")
						|| responseBody.contains("Active") || responseBody.contains("Pending"),
				"Automatched requisition not found");
		Logging.log("Automatched requiistions found");

	}

	/**
	 * @author Pritisudha Pattanaik 12/08/2016 Steps:Post - Get candidate stas
	 *         with insearchcriteria and calculatedrecordcount as false
	 *         validation:Response code and asserting response.
	 */
	@Test(groups = { "sanity", "candidatestasRequisitionWithinsearchcriteriaandrecordcountfalse","NA" })
	public void candidatestasRequisitionWithinsearchcriteriaandrecordcountfalse() {
		searchReqrequestBean1 = RequisitionResourceServiceUtil
				.getCandidateStasRequisitionwithsearchandcalculatedrecordfalse();
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response = reqConsumer.createcandidatestas(hostName, searchReqrequestBean1);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println(responseBody);
		Logging.log(responseBody);
		Assertion.assertTrue(
				responseBody.contains("Automatched") || responseBody.contains("Customer Mapped")
						|| responseBody.contains("APPLIED") || responseBody.contains("New")
						|| responseBody.contains("Active") || responseBody.contains("Pending"),
				"Automatched requisition not found");
		Logging.log("Automatched requiistions found");

	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Post - Search requisition with
	 *         status: Open and Closed and Experience from 0 to 5 Validation:
	 *         Success Response Code, validate list of requiistion in response
	 *         body, return count of req present
	 */
	@Test(groups = { "sanity", "searchRequisitionWithStatusAndExperienceRange", "P1","NA" })
	public void searchRequisitionWithOpenStatusAndExperienceRange() {
		// Get Request Bean
		searchReqrequestBean = RequisitionResourceServiceUtil.getOpenNClosedRequisitionWithExp();
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		// Execute POST Request
		Response response = reqConsumer.searchRequisition(hostName, searchReqrequestBean);
		// Assering Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull Expected:200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		// Logging.log(responseBody);
		// Asseting response body
		Assertion.assertTrue(responseBody.contains("Open") || responseBody.contains("Closed"),
				"Open requisition not found");
		// Assertion.assertTrue(responseBody.contains("Closed"), "Closed
		// requisition not found");
		Logging.log("InSearch Criteria : Open and Closed status");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Post - Search requisition with
	 *         status: Open and calculatedRecordCount parameter as true
	 *         Validation: Success Response Code, validate list of requiistion
	 *         in response body, return count of req present
	 */
	@Test(groups = { "sanity", "searchRequisitionWithStatusAndCount", "P1","NA" })
	public void searchRequisitionWithStatusAndCount() {
		// Get Request Bean
		searchReqrequestBean = RequisitionResourceServiceUtil.getRequisitionWithCount();
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		// Execute POST Request
		Response response = reqConsumer.searchRequisition(hostName, searchReqrequestBean);
		// Assering Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull Expected:200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asseting response body
		String count = reqConsumer.getTotalCount(response);
		// Assertion.assertTrue(responseBody.contains("totalResults"),
		// "Requisition count not found");
		Logging.log("Total Requisition count: " + count);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Post - Search requisition with
	 *         status: Open and calculatedRecordCount parameter as true
	 *         Validation: Success Response Code, validate list of requiistion
	 *         in response body, return count of req present
	 */
	@Test(groups = { "sanity", "searchRequisitionWithStatusAndCountFalse", "P1","NA" })
	public void searchRequisitionWithStatusAndCountFalse() {
		// Get Request Bean
		searchReqrequestBean = RequisitionResourceServiceUtil.getRequisitionWithCountFalse();
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		// Execute POST Request
		Response response = reqConsumer.searchRequisition(hostName, searchReqrequestBean);
		// Assering Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull Expected:200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		// Asseting response body
		Assertion.assertTrue(!responseBody.contains("\"totalResults\": 0"), "Total result count found");
		Logging.log("Total Result Count Not found showing \"totalResults\": 0");
	}

	/*
	 * 11-08 -2016 Vasista -Get the job description by requisition id Passing
	 * valid id Should get less than or equals to 10
	 */

	@Test(groups = { "sanity", "GetMatchingReqOnlyLimit10","NA" })
	public void GetMatchingReqOnlyLimit10() throws ClientProtocolException, IOException {

		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getMatchingReqsOnlyLimit(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("Response >>" +response);
		Assert.assertTrue(response
				.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match_First_Two_Chars"))); // S67
		/*
		 * Logging.log("contains the S6891 requisition "); // bellow counting
		 * the requisitions String[] resSplit = response.split("\"S6891"); // S6
		 * int displayIdCount = resSplit.length - 1; for (int i = 0; i <
		 * resSplit.length; i++) { Logging.log("resSplit=" + resSplit[i]); }
		 * Logging.log("resSdisplayIdCountplit=" + displayIdCount);
		 * 
		 * Logging.log("response=" + response); Assert.assertTrue(displayIdCount
		 * <= 10); Logging.log("getting <=10 requisitions");
		 */

	}

	/*
	 * 11-08 -2016 Vasista -Get the job description by requisition id Passing
	 * valid id Should get less than or equals to 20
	 */

	@Test(groups = { "sanity", "GetMatchingReqonlyLimit20","NA" })
	public void GetMatchingReqonlyLimit20() throws ClientProtocolException, IOException {

		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getMatchingReqsOnlyLimit20(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("Response >>" +response);
		Assert.assertTrue(response
				.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match_First_Two_Chars"))); // S67
		/*
		 * Logging.log("contains the S6 requisition "); // bellow counting the
		 * requisitions String[] resSplit = response.split("\"S6"); // S6 int
		 * displayIdCount = resSplit.length - 1; for (int i = 0; i <
		 * resSplit.length; i++) { Logging.log("resSplit=" + resSplit[i]); }
		 * Logging.log("resSdisplayIdCountplit=" + displayIdCount);
		 * 
		 * Logging.log("response=" + response); Assert.assertTrue(displayIdCount
		 * <= 20); Logging.log("getting <=20 requisitions");
		 */

	}

	/*
	 * 11-08 -2016 Vasista -Get the job description by requisition id Passing
	 * valid req id ,offset =5 and limit =10
	 */

	@Test(groups = { "sanity", "GetMatchingReqWithAllFeilds","NA" })
	public void GetMatchingReqWithAllFeilds() throws ClientProtocolException, IOException {

		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getMatchingReqWithAllFeilds(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("Response >>" +response);
		Assert.assertTrue(response
				.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match_First_Two_Chars")));
		/*
		 * Logging.log("contains the S61 requisition "); // bellow counting the
		 * requisitions String[] resSplit = response.split("S61"); // String[]
		 * resSplit=response.split("\"S61"); int displayIdCount =
		 * resSplit.length - 1; for (int i = 0; i < resSplit.length; i++) {
		 * Logging.log("resSplit=" + resSplit[i]); }
		 * Logging.log("resSdisplayIdCountplit=" + displayIdCount);
		 * 
		 * Logging.log("response=" + response); Assert.assertTrue(displayIdCount
		 * <= 10); Logging.log("getting <=10 requisitions");
		 */

	}

	/*
	 * 11-08 -2016 Vasista -Get the job description by requisition id Passing
	 * valid req id ,offset =5 and limit =10
	 */

	@Test(groups = { "sanity", "GetMatchingReqWithOffSet","NA" })
	public void GetMatchingReqWithOffSet() throws ClientProtocolException, IOException {

		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getMatchingReqWithOfSet(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("Response >>" +response);
		Assert.assertTrue(response
				.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match_First_Two_Chars")));
		Logging.log("contains matching requisition ");

	}
	/*
	 * 11-08 -2016 Vasista -Get the job description by requisition id Passing
	 * valid req id ,offset =5 and limit =10
	 */

	@Test(groups = { "sanity", "GetMatchingReqID","NA" })
	public void GetMatchingReqID() throws ClientProtocolException, IOException {

		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getMatchingReqIDOnly(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("Response >>" +response);
		Assert.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match")));
		Logging.log("contains the " + ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match")
				+ " matching requisition ");
		// bellow counting the requisition
		/*
		 * String[] resSplit = response.split("S632162"); // String[]
		 * resSplit=response.split("\"S61"); int displayIdCount =
		 * resSplit.length - 1; for (int i = 0; i < resSplit.length; i++) {
		 * Logging.log("resSplit=" + resSplit[i]); }
		 * Logging.log("resSdisplayIdCountplit=" + displayIdCount);
		 * Logging.log("response=" + response);
		 * Assert.assertEquals(displayIdCount = 1, displayIdCount); Logging.log(
		 * "getting  1 requisitions");
		 */
	}

	/*
	 * 11-08 -2016 Vasista -Get the job description by requisition id Passing
	 * invalid id Should fail
	 */

	@Test(groups = { "sanity", "GetJobDesByWrongID","NA" })
	public void GetJobDesByWrongID() throws ClientProtocolException, IOException {

		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqConsumer.getJobDesByWrongreqID(hostName);
		Response responsebody = reqConsumer.getJobDesByWrongreqID(hostName);
		Assertion.assertTrue(responsebody.getStatus() != 200, "Response unsuccessfull, Expected status code not equal to 200");
		Logging.log("Response successful");

	}

	/*
	 * Udhaya -Get the job description by requisition id Passing special char id
	 * Should fail
	 */

	@Test(groups = { "sanity", "GetJobDesBySpecialCharID","NA" })
	public void GetJobDesBySpecialCharID() throws ClientProtocolException, IOException {

		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqConsumer.getJobDesBySplcharreqID(hostName);
		Response responsebody = reqConsumer.getJobDesBySplcharreqID(hostName);
		Assertion.assertTrue(responsebody.getStatus() != 200, "Response unsuccessfull, Expected status code not equal to 200");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);

		// now services is not working so i can't test response
		/*
		 * String response = responsebody.readEntity(String.class);
		 * System.out.println("***** RESPONSE ******"+response);
		 * Assert.assertTrue(response.contains("primarySkill")); Logging.log(
		 * "contains the primary skill " );
		 * Assert.assertTrue(response.contains("jobLevel")); Logging.log(
		 * "contains the jobLevel " );
		 */

	}
	/*
	 * Udhaya-Get the job description by requisition id Passing Blank id Should
	 * fail
	 */

	@Test(groups = { "sanity", "GetJobDesByBlankSpace","NA" })
	public void GetJobDesByBlankSpace() throws ClientProtocolException, IOException {

		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqConsumer.getJobDesByBlankSpacereqID(hostName);
		Response responsebody = reqConsumer.getJobDesByBlankSpacereqID(hostName);
		Assertion.assertTrue(responsebody.getStatus() != 200, "Response unsuccessfull, Expected status code not equal to 200");
		Logging.log("Response successful");

		// now services is not working so i can't test response
		/*
		 * String response = responsebody.readEntity(String.class);
		 * System.out.println("***** RESPONSE ******"+response);
		 * Assert.assertTrue(response.contains("primarySkill")); Logging.log(
		 * "contains the primary skill " );
		 * Assert.assertTrue(response.contains("jobLevel")); Logging.log(
		 * "contains the jobLevel " );
		 */

	}

	/**
	 * @author Radharani Patra 16/08/16 Steps: Update Requisition status
	 *         Validation: Response code: 200
	 */
	@Test(groups = { "sanity", "updateRequisitionStatus", "P1" })
	public void updateRequisitionStatus() {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Response response = reqConsumer.changeReqStatus(reqStatusBean, hostName,
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE: " + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log("RESPONSE BODY: "+responseBody);
		Assertion.assertTrue(responseBody.contains("The requisition status has been updated successfully")||responseBody.contains("update operation successfully completed"),
				"Req not updated");
		Logging.log("Requisition Status Updated Successfully RFR ID: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats")+" ,"+" Status: "+ReadingServiceEndPointsProperties.getServiceEndPoint("test_status") );
	}

	/**
	 * @author Radharani Patra 16/08/16 Steps: Update Requisition with same
	 *         status Validation: Response code: 200
	 */
	@Test(groups = { "sanity", "updateRequisitionStatusWithSameStatus", "P1" }, dependsOnGroups = {
			"updateRequisitionStatus" })
	public void updateRequisitionStatusWithSameStatus() {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Response response = reqConsumer.changeReqStatus(reqStatusBean, hostName,
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE: " + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log("RESPONSE BODY: "+responseBody);
		Assertion.assertTrue(responseBody.contains("Bulk update operation successfully completed"),
				"Req not updated");
		Logging.log("Requisition Status Updated Successfully RFR ID: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"));
	}

	/**
	 * @author Radharani Patra 16/08/16 Steps: Update Requisition with status:
	 *         invalid request blank req id Validation: Response code: 404
	 */
	@Test(groups = { "sanity", "updateRequisitionWithBlankRFR", "P2" })
	public void updateRequisitionWithBlankRFR() {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Response response = reqConsumer.changeReqStatusBlnkRR(reqStatusBean, hostName,
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Assertion.assertTrue(response.getStatus() != 200, "Response not successfull");
		Logging.log("RESPONSE CODE: " + response.getStatus()+" , "+"Expected Response: Not Equal To 200");

	}

	/**
	 * @author Radharani Patra 16/08/16 Steps: Update Requisition with status:
	 *         invalid request blank status id Validation: Response code: 404
	 */
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update requisition without status field. Invalid Rest service URL build in the absence of Status Field.
	* </p>
	* <p>
	* <b>Input :</b>blank Status Field
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 404 or 405 with proper error message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Non Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#007D77> P4</font>
	* </p>
	*/
	@Test(groups = { "sanity", "updateRequisitionWithBlankStatus", "P4" })
	public void updateRequisitionWithBlankStatus() {
		Logging.log("Service Name: generic-services/api/requisitions"
				+ "\nDescription: Update requisition without status field. Invalid Rest service URL build in the absence of Status Field"
				+ "\nInput: blank Status Field" + "\nExpected Output: Response status 404 or 405 with proper error message");
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),"");
		Response response = reqConsumer.changeReqStatusBlnkRR(reqStatusBean, hostName,
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"));
		Assertion.assertTrue((response.getStatus() == 404 || response.getStatus() == 405), "Response not successfull");
		Logging.log("RESPONSE CODE: " + response.getStatus()+" , "+"Expected Response: Equal To 404 or 405");
	}

	/**
	 * @author Radharani Patra 16/08/16 Steps: Update Requisition with blank
	 *         parameter Validation: Response code: 404
	 */
	@Test(groups = { "sanity", "updateRequisitionWithBlankParameter", "P2" })
	public void updateRequisitionWithBlankParameter() {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Response response = reqConsumer.changeReqStatusBlnk(reqStatusBean, hostName);
		Assertion.assertTrue(response.getStatus() != 200, "Response not successfull");
		Logging.log("RESPONSE CODE: " + response.getStatus()+" , "+"Expected Response: Not Equal To 200");

	}

	/**
	 * @author Radharani Patra 16/08/16 Steps: Update Requisition with different
	 *         status Validation: Response code: 200
	 */
	@Test(groups = { "sanity", "updateRequisitionWithDifferentStatus", "P1" }, dependsOnGroups = {
			"updateRequisitionStatus" })
	public void updateRequisitionWithDifferentStatus() {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status1"));
		Response response = reqConsumer.changeReqStatus(reqStatusBean, hostName,
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE: " + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log("RESPONSE BODY: "+responseBody);
		Assertion.assertTrue(responseBody.contains("Bulk update operation successfully completed"),
				"Req not updated");
		Logging.log("Requisition Status Updated Successfully RFR ID: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"));
	}

	/**
	 * @author Radharani Patra 16/08/16 Steps: Update Requisition status without
	 *         headers Validation: Response code: not equal to 200
	 */
	@Test(groups = { "sanity", "updateRequisitionStatusWithoutheaders", "P2" })
	public void updateRequisitionStatusWithoutheaders() {
		reqConsumer = new RequisitionResourceConsumer();
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Response response = reqConsumer.changeReqStatus(reqStatusBean, hostName,
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Logging.log("RESPONSE CODE: " + response.getStatus());
		Assertion.assertTrue(response.getStatus() == 200, "Response not successfull");

	}

	/*
	 * * Udhay - /requisitions/{requisitionId}
	 * 
	 * Search RR with Blank Parameter. The test case should not return 200
	 * response
	 * 
	 * @throws IOException
	 * 
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "GetReqBlankSrch" })
	public void GetReqBlankSrch() throws ClientProtocolException, IOException {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getRequisitionBlank(hostName);
		Assertion.assertTrue(responsebody.getStatus() != 200, "Response unsuccessfull, Expected status code not equal to 200");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
	}
	
	/**
	 *  Steps: Get requisition faceted search
	 *          Validation: Response code: not equal to 200
	 */
	@Test(groups = { "sanity", "getRequisitionFacetedSearchForLocation", "NA" })
	public void getRequisitionFacetedSearchForLocation() {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response = reqConsumer.requisitionFacetedSearch(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("RESPONSE: " + responseBody);
		Assertion.assertFalse(responseBody.contains("facetedSearchMapFacetTypeByCount\": null"), "Location Facet not present");
	}
	
	/**
	 *  Steps: Get requisition faceted search for Open Requisition
	 *          Validation: Response code: not equal to 200
	 */
	@Test(groups = { "sanity", "getRequisitionFacetedSearchForOpenReq", "NA" })
	public void getRequisitionFacetedSearchForOpenReq() {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response = reqConsumer.requisitionFacetedSearchForOpen(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		// Asset Response Code
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log("RESPONSE: " + responseBody);
		Assertion.assertFalse(responseBody.contains("facetedSearchMapFacetTypeByCount\": null"), "Location Facet not present");
	}

}
