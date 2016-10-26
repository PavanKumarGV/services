package com.spire.requisitionResources;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
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
	
	public static String MATCHING_KEYWORD_VALID_TYPE = ReadingServiceEndPointsProperties.getServiceEndPoint("matching_keyword_valid_type");
	public static String MATCHING_KEYWORD_VALID_KEYWORD = ReadingServiceEndPointsProperties.getServiceEndPoint("matching_keyword_valid_keyword");
	public static String VALID_OFFSET = ReadingServiceEndPointsProperties.getServiceEndPoint("valid_offset");
	public static String VALID_LIMIT = ReadingServiceEndPointsProperties.getServiceEndPoint("valid_limit");
	public static String INVALID_OFFSET_OR_LIMIT = ReadingServiceEndPointsProperties.getServiceEndPoint("invalid_offset_or_limit");
	public static String INVALID_TYPE_OR_KEYWORD = ReadingServiceEndPointsProperties.getServiceEndPoint("invalid_type_or_keyword");
	public static String REQUISITION_KEYWORD_VALID_TYPE = ReadingServiceEndPointsProperties.getServiceEndPoint("requisition_keyword_valid_type");

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
	* @author Udhay & Jyoti
	*/
	@Test(groups = { "sanity", "testGetRequisitionSearchValidRequisitionAndProjection_PositiveFunctional" })
	public void testGetRequisitionSearchValidRequisitionAndProjection_PositiveFunctional() throws ClientProtocolException, IOException {
		Logging.log("Service Name: generic-services/api/requisitions"
				+ "\nDescription: Get requisition using valid existing requisitionId."
				+ "\nInput: Valid requisitionId " + "\nExpected Output: Response status 200 with proper requisition");
		
		// Get authentication token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Executes GET request and returns Response
		Response responsebody = reqConsumer.getRequisition(hostName);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successfull");
		Assert.assertTrue(response.contains("primarySkill")); Logging.log("contains the primary skill ");
		Assert.assertTrue(response.contains("jobLevel")); Logging.log("contains the jobLevel ");
	}
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get requisition using valid existing requisitionId & Invalid projection
	* </p>
	* <p>
	* <b>Input :</b>Valid requisitionId & Invalid projection
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200 with proper requisition
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* @author Jyoti
	*/
	@Test(groups = { "sanity", "testGetRequisitionSearchInvalidProjection_NegativeFunctional" })
	public void testGetRequisitionSearchInvalidProjection_NegativeFunctional() throws ClientProtocolException, IOException {
	    
		Logging.log("Service Name: generic-services/api/requisitions"
				+ "\nDescription: Get requisition using valid existing requisitionId & invalid projection"
				+ "\nInput: Valid requisitionId & Invalid projection" + "\nExpected Output: Response status 200 with proper requisition");
		
		// Get authentication token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Executes GET request and returns Response
		Response responsebody = reqConsumer.getRequisition(hostName, RequisitionResourceConsumer.getServiceEndPoint("Requisition_JD"), "abc");
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successfull");
		Assert.assertTrue(response.contains("primarySkill"));  Logging.log("contains the primary skill ");
		Assert.assertTrue(response.contains("jobLevel")); Logging.log("contains the jobLevel ");
	}
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get requisition using Invalid requisitionId & valid projection
	* </p>
	* <p>
	* <b>Input :</b>Invalid requisitionId & valid projection
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 404 with invalid requisition
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* @author Jyoti
	*/
	@Test(groups = { "sanity", "testGetRequisitionSearchInvalidRequisition_NegativeFunctional" })
	public void testGetRequisitionSearchInvalidRequisition_NegativeFunctional() throws ClientProtocolException, IOException {
	    
		Logging.log("Service Name: generic-services/api/requisitions"
				+ "\nDescription: Get requisition using invalid requisitionId & valid projection"
				+ "\nInput: Invalid requisitionId & valid projection" + "\nExpected Output: Response status 404 with invalid requisition");
		
		// Get authentication token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Executes GET request and returns Response
		Response responsebody = reqConsumer.getRequisition(hostName, "abc", "full");
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 404, "Response not successfull");
		Assert.assertTrue(response.contains("Requisition doesn't exist for requisition id: abc"));  Logging.log("contains the primary skill ");
	}

	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/jd/{requisitionId}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the job description by requisition id
	* </p>
	* <p>
	* <b>Input :</b>valid requisitionId 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Vasista & Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetJobDesByID_PositiveFunctional","P2","NA"})
	public void testGetJobDesByID_PositiveFunctional() throws ClientProtocolException, IOException {
	    Logging.log("Service Name: generic-services/api/requisitions/jd/6100:6066:4f12a7a0193542c5a3bf5f179a1b4913"
			+ "\nDescription: Get Job Description service with Id as parameter and expecting 200 response."
			+ "\nInput: requisition Id" + "\nExpected Output: 200 Response");
		
	    	// Get authentication token
	    	RequisitionResourceConsumer reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
	    	// Executes GET request and returns Response
		Response responsebody = reqConsumer.getJobDesByreqID(hostName);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
		Assertion.assertTrue(response.contains("fileName") || response.contains("filename"),"Response unsuccessful, Expected 200 status code");
		
		Logging.log("Response Successful, Able to get JD");
	}

       /*
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/{requisitionId}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Search requisitionId with special character
	* </p>
	* <p>
	* <b>Input :</b>valid requisitionId with special character
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 404
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Udhay & Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetRequisitonWithSpecialChar_NegativeFunctional","P2","NA"})
	public void testGetRequisitonWithSpecialChar_NegativeFunctional() throws ClientProtocolException, IOException {
	    Logging.log("Service Name: generic-services/api/requisitions/{requisitionId}"
			+ "\nDescription: Search requisitionId with special character and expecting 404 response."
			+ "\nInput: requisition Id with special character" + "\nExpected Output: 404 Response");
	    
	    	// Get authentication token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Executes GET request and returns Response
		Response responsebody = reqConsumer.getRequisitionInvalid(hostName);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() == 404, "Response unsuccessful, Expected 404 status code");
		Assertion.assertTrue(response.contains("Requisition doesn't exist for requisition id"), "Got response from requisitionId with special character");
	}

	/*
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/{requisitionId}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Search requisitionId which is not present in the system
	* </p>
	* <p>
	* <b>Input :</b>Invalid requisitionId
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 404
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Udhay & Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetRequisitionWithInvalidReqId_NegativeFunctional","P2","NA"})
	public void testGetRequisitionWithInvalidReqId_NegativeFunctional() throws ClientProtocolException, IOException {
	    Logging.log("Service Name: generic-services/api/requisitions/{requisitionId}"
		+ "\nDescription: Search requisitionId with special character and expecting 404 response."
		+ "\nInput: requisition Id with special character" + "\nExpected Output: 404 Response");
		    
		// Get authentication token
	    	reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
			
		// Executes GET request and returns Response
		Response responsebody = reqConsumer.getRequisitionInvalidInput(hostName);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() == 404, "Response unsuccessful, Expected 404 status code");
		Assertion.assertTrue(response.contains("Requisition doesn't exist for requisition id"), "Got response for invalid requisitionId");
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/search
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* To get the list of requisition using InSearch Criteria
	* </p>
	* <p>
	* <b>Input :</b> InSearch Criteria
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#C90000> P1</font>
	* </p>
	* <p>
	* @author Radharani Patra & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>	
	*/
	@Test(groups = { "sanity", "testPostSearchRequisitionWithInSearchCriteria_PositiveFunctional", "P1","NA" })
	public void testPostSearchRequisitionWithInSearchCriteria_PositiveFunctional() {
	    Logging.log("Service Name: generic-services/api/requisitions/search"
			+ "\nDescription:  To get the list of requisition using InSearch Criteria and expecting 200 response."
			+ "\nInput:  InSearch Criteria" + "\nExpected Output: 200 Response");
			
		// Get Request Bean
		searchReqrequestBean = RequisitionResourceServiceUtil.getSearchRequisition();
		
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute POST Request
		Response responsebody = reqConsumer.searchRequisition(hostName, searchReqrequestBean);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful Expected:200");
		if(response.contains("\"totalResults\":0")){
		    Logging.log("No requistions found for InSearch Criteria");
		}else{
		    Assertion.assertTrue(response.contains("Open"), "Open requisition not found");
        	    Logging.log("InSearch Criteria : Open, Open requistions found");
		}
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/search
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Search requisition without InSearch Criteria
	* </p>
	* <p>
	* <b>Input :</b>no InSearch Criteria
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#C90000> P1</font>
	* </p>
	* <p>
	* @author Radharani Patra & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>	
	*/
	@Test(groups = { "sanity", "testPostSearchRequisitionWithoutSearchCriteria_NegativeFunctional", "P1","NA" })
	public void testPostSearchRequisitionWithoutSearchCriteria_NegativeFunctional() {
	    Logging.log("Service Name: generic-services/api/requisitions/search"
			+ "\nDescription: Search Requisition using no criteria as parameter and expecting 200 response."
			+ "\nInput: No criteria for search" + "\nExpected Output: 200 Response");
		
	    	// Get Request Bean
		searchReqrequestBean1 = RequisitionResourceServiceUtil.getSearchRequisitionWithoutCriteria();
		
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute POST Request
		Response responsebody = reqConsumer.searchRequisition(hostName, searchReqrequestBean1);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful Expected:200");
		if(response.contains("\"totalResults\":0")){
		    Logging.log("No requistions found for InSearch Criteria");
		}else{
		    Assertion.assertTrue(response.contains("Automatched") || response.contains("Hired")
			|| response.contains("APPLIED") || response.contains("New")
			|| response.contains("Active") || response.contains("Pending"), "Response not successful");
		}
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/candidate/stats
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate stats for search criteria
	* </p>
	* <p>
	* <b>Input :</b>InSearch criteria and experience from month and to month
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#C90000> P1</font>
	* </p>
	* <p>
	* @author Pritisudha Pattanaik & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>	
	*/	
	@Test(groups = { "sanity", "testPostGetCandidateStats_PositiveFunctional", "P1","NA" })
	public void testPostGetCandidateStats_PositiveFunctional() {
	    Logging.log("Service Name: generic-services/api/requisitions/candidate/stats"
			+ "\nDescription: Get candidate stats for search criteria and expecting 200 response."
			+ "\nInput: InSearch criteria and experience from month and to month" + "\nExpected Output: 200 Response");
	
	    	// Get Candidate Stats Bean
		candidatestasBean1 = RequisitionResourceServiceUtil.getCandidateStasRequisition();
		
		//Get user auth token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute POST Request
		Response responsebody = reqConsumer.createcandidatestas(hostName, candidatestasBean1);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
		if(response.contains("\"response\":{}")){
		    	Logging.log("No response found for given candidate stats");
		}else{
		     	Assertion.assertTrue(StringUtils.containsIgnoreCase(response, "Automatched") || StringUtils.containsIgnoreCase(response,"Customer Mapped")
				|| StringUtils.containsIgnoreCase(response,"Applied") || StringUtils.containsIgnoreCase(response,"New")
				|| StringUtils.containsIgnoreCase(response,"Active") || StringUtils.containsIgnoreCase(response,"Pending"),
			"Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
		     	Logging.log("Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
		}
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/candidate/stats
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate stats for search criteria
	* </p>
	* <p>
	* <b>Input :</b>without any field validation
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Pritisudha Pattanaik & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>	
	*/
	@Test(groups = { "sanity", "testPostCandidateStatsRequisitionWithoutSearchCriteria_PositiveFunctional","P2", "NA" })
	public void testPostCandidateStatsRequisitionWithoutSearchCriteria_PositiveFunctional() {
	    Logging.log("Service Name: generic-services/api/requisitions/candidate/stats"
			+ "\nDescription: Get candidate stats and expecting 200 response."
			+ "\nInput: without any field validation" + "\nExpected Output: 200 Response");
		
	    // Get search requisition request bean
	    searchReqrequestBean1 = RequisitionResourceServiceUtil.getCandiadteStasRequisitionWithoutCriteria();
		
	    //Get user auth token
	    reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
	    // Execute POST Request
	    Response responsebody = reqConsumer.createcandidatestas(hostName, searchReqrequestBean1);
	    String response = responsebody.readEntity(String.class);
	    
	    Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
	    // Asserting Response Code
	    Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successfull");
	    if(response.contains("\"response\":{}")){
	    	Logging.log("No response found for given candidate stats");
	    }else{	
		Assertion.assertTrue(StringUtils.containsIgnoreCase(response, "Automatched") || StringUtils.containsIgnoreCase(response,"Customer Mapped")
				|| StringUtils.containsIgnoreCase(response,"Applied") || StringUtils.containsIgnoreCase(response,"New")
				|| StringUtils.containsIgnoreCase(response,"Active") || StringUtils.containsIgnoreCase(response,"Pending"),
			"Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
		Logging.log("Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
	    }
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/candidate/stats
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate stats for search criteria
	* </p>
	* <p>
	* <b>Input :</b>with only InSearch criteria 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Pritisudha Pattanaik & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>	
	*/
	@Test(groups = { "sanity", "testPostCandidateStatsRequisitionWithOnlyInSearchCriteria_PositiveFunctional","P2","NA" })
	public void testPostCandidateStatsRequisitionWithOnlyInSearchCriteria_PositiveFunctional() {
	    Logging.log("Service Name: generic-services/api/requisitions/candidate/stats"
			+ "\nDescription: Get candidate stats and expecting 200 response."
			+ "\nInput: with only InSearch criteria " + "\nExpected Output: 200 Response");
	    	
	    	// Get search requisition request bean
		searchReqrequestBean1 = RequisitionResourceServiceUtil.getCandidateStasRequisitionwithInsearchcriteria();
		
		//Get user auth token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute POST Request
		Response responsebody = reqConsumer.createcandidatestas(hostName, searchReqrequestBean1);
		String response = responsebody.readEntity(String.class);
		    
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
			
	        // Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successfull");
		if(response.contains("\"response\":{}")){
		    	Logging.log("No response found for given candidate stats");
		}else{	
		        Assertion.assertTrue(StringUtils.containsIgnoreCase(response, "Automatched") || StringUtils.containsIgnoreCase(response,"Customer Mapped")
				|| StringUtils.containsIgnoreCase(response,"Applied") || StringUtils.containsIgnoreCase(response,"New")
				|| StringUtils.containsIgnoreCase(response,"Active") || StringUtils.containsIgnoreCase(response,"Pending"),
		        	"Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
		        Logging.log("Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
	         }
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/candidate/stats
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate stats for search criteria
	* </p>
	* <p>
	* <b>Input :</b> InSearch criteria and calculatedrecordcount as true
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Pritisudha Pattanaik & Jyoti
	* </p>
	* <p>
	* @since 12/08/16
	* </p>	
	*/
	@Test(groups = { "sanity", "testPostCandidateStatsRequisitionWithInSearchCriteriaAndRecordCountTrue_PositiveFunctional","P2","NA" })
	public void testPostCandidateStatsRequisitionWithInSearchCriteriaAndRecordCountTrue_PositiveFunctional() {
	    	Logging.log("Service Name: generic-services/api/requisitions/candidate/stats"
			+ "\nDescription: Get candidate stats and expecting 200 response."
			+ "\nInput: InSearch criteria and calculatedrecordcount as true " + "\nExpected Output: 200 Response");
	    	
	    	// Get search requisition request bean
		searchReqrequestBean1 = RequisitionResourceServiceUtil.getCandidateStasRequisitionwithsearchandcalculatedrecordtrue();
		
		//Get user auth token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute POST Request
		Response responsebody = reqConsumer.createcandidatestas(hostName, searchReqrequestBean1);
		String response = responsebody.readEntity(String.class);
		    
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successfull");
		if(response.contains("\"response\":{}")){
		    	Logging.log("No response found for given candidate stats");
		}else{
		    Assertion.assertTrue(StringUtils.containsIgnoreCase(response, "Automatched") || StringUtils.containsIgnoreCase(response,"Customer Mapped")
				|| StringUtils.containsIgnoreCase(response,"Applied") || StringUtils.containsIgnoreCase(response,"New")
				|| StringUtils.containsIgnoreCase(response,"Active") || StringUtils.containsIgnoreCase(response,"Pending"),
			"Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
		    Logging.log("Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
		}
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/candidate/stats
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate stats for search criteria
	* </p>
	* <p>
	* <b>Input :</b> InSearch criteria and calculatedrecordcount as false
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Pritisudha Pattanaik & Jyoti
	* </p>
	* <p>
	* @since 12/08/16
	* </p>	
	*/	
	@Test(groups = { "sanity", "testPostCandidateStatsRequisitionWithInSearchCriteriaAndRecordCountFalse_PositiveFunctional","P2","NA" })
	public void testPostCandidateStatsRequisitionWithInSearchCriteriaAndRecordCountFalse_PositiveFunctional() {
	    	Logging.log("Service Name: generic-services/api/requisitions/candidate/stats"
			+ "\nDescription: Get candidate stats and expecting 200 response."
			+ "\nInput: InSearch criteria and calculatedrecordcount as false"
			+ "\nExpected Output: 200 Response");
	    	
	    	// Get search requisition request bean
	    	searchReqrequestBean1 = RequisitionResourceServiceUtil
				.getCandidateStasRequisitionwithsearchandcalculatedrecordfalse();
		
		//Get user auth token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute POST Request
		Response responsebody = reqConsumer.createcandidatestas(hostName, searchReqrequestBean1);
		String response = responsebody.readEntity(String.class);
		    
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successfull");
		if(response.contains("\"response\":{}")){
		    	Logging.log("No response found for given candidate stats");
		}else{
			Assertion.assertTrue(StringUtils.containsIgnoreCase(response, "Automatched") || StringUtils.containsIgnoreCase(response,"Customer Mapped")
					|| StringUtils.containsIgnoreCase(response,"Applied") || StringUtils.containsIgnoreCase(response,"New")
					|| StringUtils.containsIgnoreCase(response,"Active") || StringUtils.containsIgnoreCase(response,"Pending"),
				"Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
			Logging.log("Automatched/Customer Mapped/Applied/New/Active/Pending requisition not found");
		}
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/search
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Search requisition with status and experience
	* </p>
	* <p>
	* <b>Input :</b> status Open and Closed and Experience from 2 to 4 years
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Radharani Patra & Jyoti
	* </p>
	* <p>
	* @since 12/08/16
	* </p>	
	*/
	@Test(groups = { "sanity", "testPostSearchRequisitionWithStatusAndExperienceRange_PositiveFunctional", "P1","NA" })
	public void testPostSearchRequisitionWithStatusAndExperienceRange_PositiveFunctional() {
	    	Logging.log("Service Name: generic-services/api/requisitions/search"
			+ "\nDescription: Get candidate stats and expecting 200 response."
			+ "\nInput: status Open and Closed and Experience from 2 to 4 years"
			+ "\nExpected Output: 200 Response");
	    
		// Get Request Bean
		searchReqrequestBean = RequisitionResourceServiceUtil.getOpenNClosedRequisitionWithExp();
		
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute POST Request
		Response responsebody = reqConsumer.searchRequisition(hostName, searchReqrequestBean);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successfull Expected:200");
		if(response.contains("\"totalResults\":0")){
		    Logging.log("totalResults are 0 so no Open and Closed Requisitions within experience 2 to 4 years");
		}else{
		    Assertion.assertTrue(response.contains("Open") || response.contains("Closed"),"Open/Closed requisition not found");
		    Logging.log("InSearch Criteria : Open and Closed status with experience 2 to 4 years ");
		}
		
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/search
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Search requisition with status and calculatedRecordCount 
	* </p>
	* <p>
	* <b>Input :</b> status Open and calculatedRecordCount parameter as true
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Radharani Patra & Jyoti
	* </p>
	* <p>
	* @since 12/08/16
	* </p>	
	*/
	@Test(groups = { "sanity", "testPostSearchRequisitionWithStatusAndCountTrue_PositiveFunctional", "P1","NA" })
	public void testPostSearchRequisitionWithStatusAndCountTrue_PositiveFunctional() {
	    Logging.log("Service Name: generic-services/api/requisitions/search"
			+ "\nDescription: Search Requisition With Status and Count and expecting 200 response."
			+ "\nInput: status Open and calculatedRecordCount parameter as true and no other criteria for search" + "\nExpected Output: 200 Response");
	    
	    	// Get Request Bean
		searchReqrequestBean = RequisitionResourceServiceUtil.getRequisitionWithCount();
		
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute POST Request
		Response responsebody = reqConsumer.searchRequisition(hostName, searchReqrequestBean);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful Expected:200");
		Assertion.assertTrue(response.contains("totalResults"),"Response was not successful");
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/search
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Search requisition with status and calculatedRecordCount 
	* </p>
	* <p>
	* <b>Input :</b> status Open and calculatedRecordCount parameter as false
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Radharani Patra & Jyoti
	* </p>
	* <p>
	* @since 12/08/16
	* </p>	
	*/	
	@Test(groups = { "sanity", "testPostSearchRequisitionWithStatusAndCountFalse_PositiveFunctional", "P1","NA" })
	public void testPostSearchRequisitionWithStatusAndCountFalse_PositiveFunctional() {
	        Logging.log("Service Name: generic-services/api/requisitions/search"
			+ "\nDescription: Search Requisition With Status and Count and expecting 200 response."
			+ "\nInput: status Open and calculatedRecordCount parameter as false and no other criteria for search" + "\nExpected Output: 200 Response");
	    
	    	// Get Request Bean
		searchReqrequestBean = RequisitionResourceServiceUtil.getRequisitionWithCountFalse();
		
		// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute POST Request
		Response responsebody = reqConsumer.searchRequisition(hostName, searchReqrequestBean);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successfull Expected:200");
		Assertion.assertTrue(!response.contains("\"totalResults\": 0"), "Total result count found");
		
		Logging.log("Total Result Count Not found showing \"totalResults\": 0");
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/match/requisitionId/SR
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get matching requisitions using limit
	* </p>
	* <p>
	* <b>Input :</b> valid requisition id upto 2 characters with limit 10
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Vasista & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>	
	*/
	@Test(groups = { "sanity", "testGetMatchingReqOnlyLimit10_PositiveFunctional","NA" })
	public void testGetMatchingReqOnlyLimit10_PositiveFunctional() throws ClientProtocolException, IOException {
	    Logging.log("Service Name: generic-services/api/requisitions/match/requisitionId/SR?limit=10"
			+ "\nDescription: Get matching requisitions using limit and expecting 200 response."
			+ "\nInput: type as SR and limit 10" + "\nExpected Output: 200 Response");
	    
	    	// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute GET Request
		Response responsebody = reqConsumer.getMatchingReqsOnlyLimit(hostName);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		if(response.contains("\"response\":{\"displayIds\":null}")){
			Logging.log("response has displayIds as null so no matching requisition found"); 
		}else{
		    	Assert.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match_First_Two_Chars"))); // S67
		}
	}

	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/match/requisitionId/SR
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get matching requisitions using limit
	* </p>
	* <p>
	* <b>Input :</b> valid requisition id upto 2 characters with limit 10 
	* valid id Should get less than or equals to 20
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Vasista & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingReqonlyLimit20_PositiveFunctional","NA" })
	public void testGetMatchingReqonlyLimit20_PositiveFunctional() throws ClientProtocolException, IOException {
	    	Logging.log("Service Name: generic-services/api/requisitions/match/requisitionId/SR?limit=20"
			+ "\nDescription: Get matching requisitions using limit and expecting 200 response."
			+ "\nInput: type as SR and limit 20" + "\nExpected Output: 200 Response");
	    
	    	// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute GET Request
		Response responsebody = reqConsumer.getMatchingReqsOnlyLimit20(hostName);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
		if(response.contains("\"response\":{\"displayIds\":null}")){
			Logging.log("response has displayIds as null so no matching requisition found"); 
		}else{
		    	Assert.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match_First_Two_Chars")));
		}
	}

      /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/match/requisitionId/SR?offset=5&limit=10
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get matching requisitions using offset & limit
	* </p>
	* <p>
	* <b>Input :</b> valid requisition id upto 2 characters with offset 0 & limit 10
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Vasista & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingReqWithAllFields_PositiveFunctional","NA" })
	public void testGetMatchingReqWithAllFields_PositiveFunctional() throws ClientProtocolException, IOException {
	    Logging.log("Service Name: generic-services/api/requisitions/match/requisitionId/SR?offset=5&limit=10"
			+ "\nDescription: Get matching requisitions using offset & limit and expecting 200 response."
			+ "\nInput: type as SR and offset 0 and limit 10" + "\nExpected Output: 200 Response");
	    
	    	// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute GET Request
		Response responsebody = reqConsumer.getMatchingReqWithAllFeilds(hostName);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
		if(response.contains("\"response\":{\"displayIds\":null}")){
			Logging.log("response has displayIds as null so no matching requisition found"); 
		}else{
		    	Assert.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match_First_Two_Chars")));
		}
	}

	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/match/requisitionId/SR
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get matching requisitions using limit
	* </p>
	* <p>
	* <b>Input :</b> valid requisition id upto 2 characters with offset 0
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Vasista & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingReqWithOffSet_PositiveFunctional","NA" })
	public void testGetMatchingReqWithOffSet_PositiveFunctional() throws ClientProtocolException, IOException {
	    Logging.log("Service Name: generic-services/api/requisitions/match/requisitionId/SR?offset=5&limit=10"
			+ "\nDescription: Get matching requisitions using offset & limit and expecting 200 response."
			+ "\nInput: type as SR and offset 0 and limit 10" + "\nExpected Output: 200 Response");
	    
	    	// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute GET Request
		Response responsebody = reqConsumer.getMatchingReqWithOfSet(hostName);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
		if(response.contains("\"response\":{\"displayIds\":null}")){
			Logging.log("response has displayIds as null so no matching requisition found"); 
		}else{
		    	Assert.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match_First_Two_Chars")));
		}
	}
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/requisitions/match/requisitionId/SR
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get matching requisitions
	* </p>
	* <p>
	* <b>Input :</b> valid requisition id upto 2 characters 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Vasista & Jyoti
	* </p>
	* <p>
	* @since 11/08/16
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingReqID_PositiveFunctional","NA" })
	public void testGetMatchingReqID_PositiveFunctional() throws ClientProtocolException, IOException {
	    	Logging.log("Service Name: generic-services/api/requisitions/match/requisitionId/SR"
			+ "\nDescription: Get matching requisitions and expecting 200 response."
			+ "\nInput: type as SR" + "\nExpected Output: 200 Response");
	    
	    	// Get user Token
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		
		// Execute GET Request
		Response responsebody = reqConsumer.getMatchingReqIDOnly(hostName);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
		if(response.contains("\"response\":{\"displayIds\":null}")){
			Logging.log("response has displayIds as null so no matching requisition found"); 
		}else{
        		Assert.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match")));
        		Logging.log("contains the " + ReadingServiceEndPointsProperties.getServiceEndPoint("Requisition_Match")
        				+ " matching requisition ");
		}
	}
	
	/**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/keyword
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> valid type, offset & limit
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#C90000> P1</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetRequisitionKeyword_PositiveFunctional","NA" })
	public void testGetRequisitionKeyword_PositiveFunctional() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/keyword"
			+ "\nDescription: Get the list of keywords using given parameters and expecting success response." 
		        + "\nInput: valid type, offset & limit \nExpected Output: Response status 200");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getRequisitionKeyword(hostName, REQUISITION_KEYWORD_VALID_TYPE, VALID_OFFSET, VALID_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
	}
	
	/**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/keyword
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> invalid type, valid offset & limit
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetRequisitionKeywordInvalidType_NegativeFunctional","NA" })
	public void testGetRequisitionKeywordInvalidType_NegativeFunctional() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/keyword"
			+ "\nDescription: Get the list of keywords using given parameters and expecting success response." 
		        + "\nInput: invalid type, valid offset & limit \nExpected Output: Response status 200");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getRequisitionKeyword(hostName, INVALID_TYPE_OR_KEYWORD, VALID_OFFSET, VALID_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
	}
	
	/**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/keyword
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> invalid offset, valid type & limit
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 500
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetRequisitionKeywordInvalidOffset_NegativeFunctional","NA" })
	public void testGetRequisitionKeywordInvalidOffset_NegativeFunctional() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/keyword"
			+ "\nDescription: Get the list of keywords using given parameters and expecting failure response." 
		        + "\nInput: invalid offset, valid type & limit \nExpected Output: Response status 500");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getRequisitionKeyword(hostName, REQUISITION_KEYWORD_VALID_TYPE, INVALID_OFFSET_OR_LIMIT, VALID_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 500, "Response unsuccessful, Expected 500 status code");
    	     Assertion.assertTrue(response.contains("Unable to fetch the keywords for the given type"),"Response succeeded with invalid offset");
	}	
	
	/**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/keyword
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> invalid limit, valid type & offset
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 500
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetRequisitionKeywordInvalidLimit_NegativeFunctional","NA" })
	public void testGetRequisitionKeywordInvalidLimit_NegativeFunctional() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/keyword"
			+ "\nDescription: Get the list of keywords using given parameters and expecting failure response." 
		        + "\nInput: invalid limit, valid type & offset \nExpected Output: Response status 500");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getRequisitionKeyword(hostName, REQUISITION_KEYWORD_VALID_TYPE, VALID_OFFSET, INVALID_OFFSET_OR_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 500, "Response unsuccessful, Expected 500 status code");
    	     Assertion.assertTrue(response.contains("Unable to fetch the keywords for the given type"),"Response succeeded with invalid limit");
	}
	
      /**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/match/{type}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of matching keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> valid type, keyword, offset & limit
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#C90000> P1</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingKeyword_PositiveFunctional","NA" })
	public void testGetMatchingKeyword_PositiveFunctional() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/match/{type}"
			+ "\nDescription: Get the list of matching keywords using given parameters and expecting success response." 
		        + "\nInput: valid type, keyword, offset & limit \nExpected Output: Response status 200");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getMatchingKeyword(hostName, MATCHING_KEYWORD_VALID_TYPE, MATCHING_KEYWORD_VALID_KEYWORD, VALID_OFFSET, VALID_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
	}
	
      /**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/match/{type}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of matching keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> Invalid type & valid keyword, offset & limit
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 500
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingKeywordUsingInvalidType_NegativeFunctional","NA" })
	public void testGetMatchingKeywordUsingInvalidType_NegativeFunctional() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/match/{type}"
			+ "\nDescription: Get the list of matching keywords using given parameters and expecting failure response." 
		        + "\nInput: Invalid type & valid keyword, offset & limit \nExpected Output: Response status 500");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getMatchingKeyword(hostName, INVALID_TYPE_OR_KEYWORD, MATCHING_KEYWORD_VALID_KEYWORD, VALID_OFFSET, VALID_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 500, "Response successful, Expected 500 status code");
	}
	
	
      /**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/match/{type}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of matching keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> Invalid keyword & valid type, offset & limit
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 500
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingKeywordUsingInvalidKeyword_NegativeFunctional","NA" })
	public void testGetMatchingKeywordUsingInvalidKeyword_NegativeFunctional() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/match/{type}"
			+ "\nDescription: Get the list of matching keywords using given parameters and expecting failure response." 
		        + "\nInput: Invalid keyword & valid type, offset & limit \nExpected Output: Response status 500");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getMatchingKeyword(hostName, MATCHING_KEYWORD_VALID_TYPE, INVALID_TYPE_OR_KEYWORD, VALID_OFFSET, VALID_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 500, "Response successful, Expected 500 status code");
	}
	
      /**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/match/{type}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of matching keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> Invalid offset & valid type, keyword & limit
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 500
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingKeywordUsingInvalidOffset_NegativeFunctional","NA" })
	public void testGetMatchingKeywordUsingInvalidOffset_NegativeFunctional() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/match/{type}"
			+ "\nDescription: Get the list of matching keywords using given parameters and expecting failure response." 
		        + "\nInput: Invalid offset & valid type, keyword & limit \nExpected Output: Response status 500");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getMatchingKeyword(hostName, MATCHING_KEYWORD_VALID_TYPE, MATCHING_KEYWORD_VALID_KEYWORD, INVALID_OFFSET_OR_LIMIT, VALID_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 500, "Response successful, Expected 500 status code");
	}
	
      /**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/match/{type}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of matching keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> Invalid limit & valid type, keyword & offset
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 500
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingKeywordUsingInvalidLimit_NegativeFunctional","NA" })
	public void testGetMatchingKeywordUsingInvalidLimit_NegativeFunctional() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/match/{type}"
			+ "\nDescription: Get the list of matching keywords using given parameters and expecting failure response." 
		        + "\nInput: Invalid limit & valid type, keyword & offset \nExpected Output: Response status 500");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getMatchingKeyword(hostName, MATCHING_KEYWORD_VALID_TYPE, MATCHING_KEYWORD_VALID_KEYWORD, VALID_OFFSET, INVALID_OFFSET_OR_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 500, "Response successful, Expected 500 status code");
	}
	
      /**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/match/{type}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of matching keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> valid types
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Boundary Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingKeywordValidTypes_BoundaryCase","NA" })
	public void testGetMatchingKeywordValidTypes_BoundaryCase() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/match/{type}"
			+ "\nDescription: Get the list of matching keywords using given parameters and expecting success response." 
		        + "\nInput: valid types \nExpected Output: Response status 200");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getMatchingKeyword(hostName, MATCHING_KEYWORD_VALID_TYPE, MATCHING_KEYWORD_VALID_KEYWORD, VALID_OFFSET, VALID_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
	}
	
      /**
	* 
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b>
	* generic-services/api/requisitions/match/{type}
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of matching keywords using given parameters
	* </p>
	* <p>
	* <b>Input :</b> valid keywords
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Boundary Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/
	@Test(groups = { "sanity", "testGetMatchingKeywordValidKeywords_BoundaryCase","NA" })
	public void testGetMatchingKeywordValidKeywords_BoundaryCase() throws ClientProtocolException, IOException {
	      Logging.log("Service Name: generic-services/api/requisitions/match/{type}"
			+ "\nDescription: Get the list of matching keywords using given parameters and expecting success response." 
		        + "\nInput: valid keywords \nExpected Output: Response status 200");
	      
	     // Get authentication token
    	     reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
    		
    	     // Executes POST request and returns Response
    	     Response responsebody = reqConsumer.getMatchingKeyword(hostName, MATCHING_KEYWORD_VALID_TYPE, MATCHING_KEYWORD_VALID_KEYWORD, VALID_OFFSET, VALID_LIMIT);
    	     String response = responsebody.readEntity(String.class);
    	     
    	     Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

    	     // Asserting Response Code
    	     Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
	}

	/*
	 * 11-08 -2016 Vasista -Get the job description by requisition id Passing
	 * invalid id Should fail
	 */

	@Test(groups = { "sanity", "GetJobDesByWrongID","NA" })
	public void GetJobDesByWrongID() throws ClientProtocolException, IOException {
	    Logging.log("Service Name: generic-services/api/requisitions/jd/abc"
			+ "\nDescription: Get Job Description using worng id as parameter and expecting 500 response."
			+ "\nInput: Using wrong ID that not present in the system" + "\nExpected Output: 500 Response");
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getJobDesByWrongreqID(hostName);
		//Assertion.assertTrue(responsebody.getStatus() == 500, "Response unsuccessfull, Expected status code not equal to 500");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("Unable to get JD/RESUME for ID") , "Response unsuccessfull, Expected status code not equal to 500");

	}

	/*
	 * Udhaya -Get the job description by requisition id Passing special char id
	 * Should fail
	 */

	@Test(groups = { "sanity", "GetJobDesBySpecialCharID","NA" })
	public void GetJobDesBySpecialCharID() throws ClientProtocolException, IOException {
	    Logging.log("Service Name: generic-services/api/requisitions/jd/6079%3A6066%3A0004C4D081D743BF8D72846537638DD2%40%23%24"
			+ "\nDescription: Get Job Description special character as parameter and expecting 500 response."
			+ "\nInput: Special charater with ID" + "\nExpected Output: 500 Response");
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqConsumer.getJobDesBySplcharreqID(hostName);
		Response responsebody = reqConsumer.getJobDesBySplcharreqID(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 500, "Response unsuccessfull, Expected status code not equal to 500");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("Unable to get JD/RESUME for ID"),"Response unsuccessfull, Expected status code not equal to 500" );

	}
	/*
	 * Udhaya-Get the job description by requisition id Passing Blank id Should
	 * fail
	 */

	@Test(groups = { "sanity", "GetJobDesByBlankSpace","NA" })
	public void GetJobDesByBlankSpace() throws ClientProtocolException, IOException {

	    Logging.log("Service Name: generic-services/api/requisitions/jd/"
			+ "\nDescription: Get Job Description service with Blank Space as parameter and expecting 404 response."
			+ "\nInput: Using Blank Keyword " + "\nExpected Output: 404 Response");
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqConsumer.getJobDesByBlankSpacereqID(hostName);
		Response response = reqConsumer.getJobDesByBlankSpacereqID(hostName);
		Assertion.assertTrue(response.getStatus() == 404, "Response unsuccessfull, Expected status code not equal to 404");
		Logging.log("Response successful");

		String responseBody = response.readEntity(String.class);
	        Assertion.assertTrue(responseBody.contains("Requisition doesn't exist for requisition id"), "Request Unsuccessful: Expected Empty Response but response was: " +responseBody);
	        
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
	    Logging.log("Service Name: generic-services/api/requisitions/Closed"
			+ "\nDescription: Update Requisition using blank RFR parameter and expecting 405 response."
			+ "\nInput: Blank RFR Parameter to update Requisition Status" + "\nExpected Output: 405 Response");
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Response response = reqConsumer.changeReqStatusBlnkRR(reqStatusBean, hostName,
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Assertion.assertTrue(response.getStatus() == 405, "Response not successfull");
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains("405"), "Response not successfull");

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
	    Logging.log("Service Name: generic-services/api/requisitions/"
			+ "\nDescription: Update Requisition using blank parameter and expecting 404 response."
			+ "\nInput: Blank Parameter to update Requisition Status" + "\nExpected Output: 404 Response");
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Response response = reqConsumer.changeReqStatusBlnk(reqStatusBean, hostName);
		Assertion.assertTrue(response.getStatus() == 404, "Response not successfull");
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains("404"), "Response not successfull");

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
	    Logging.log("Service Name: generic-services/api/requisitions/6100:6066:1419f6338d734d1296d4928f14b00e8d/Closed"
			+ "\nDescription: Update Requisition Status using no header and expecting 200 response."
			+ "\nInput: No header to update Requisition Status" + "\nExpected Output: 200 Response");
		reqConsumer = new RequisitionResourceConsumer();
		reqStatusBean = RequisitionResourceServiceUtil.changeReqStatus(
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Response response = reqConsumer.changeReqStatus(reqStatusBean, hostName,
				ReadingServiceEndPointsProperties.getServiceEndPoint("changeStats"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		Logging.log("RESPONSE CODE: " + response.getStatus());
		Assertion.assertTrue(response.getStatus() == 200, "Response not successfull");
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.isEmpty(), "Response not successfull");

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
	    Logging.log("Service Name: generic-services/api/requisitions/jd/abc"
			+ "\nDescription: Get Job Description using worng id as parameter and expecting 404 response."
			+ "\nInput: Using wrong ID that not present in the system" + "\nExpected Output: 404 Response");
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody = reqConsumer.getRequisitionBlank(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 404, "Response unsuccessfull, Expected status code not equal to 404");
		Logging.log("Response successful");
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("The requested resource is not available"),"Response unsuccessfull");

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
