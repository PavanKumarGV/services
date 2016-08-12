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
import com.spire.base.service.utils.RequisitionResourceServiceUtil;
import com.spire.service.consumers.RequisitionResourceConsumer;
import com.spire.service.consumers.SearchResourcesConsumer;

import spire.talent.gi.beans.SearchRequisitionRequestBean;

public class RequisitionResourcesTestPlan extends TestPlan {

	String hostName;
	String userId;
	String password;
	SearchRequisitionRequestBean searchReqrequestBean = null;
	SearchRequisitionRequestBean searchReqrequestBean1 = null;
	SearchRequisitionRequestBean candidatestasBean1=null;
	RequisitionResourceConsumer reqConsumer = null;

	/** 
	 * Passing HostName,UserName and Password from the xml.
	 */

	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
		userId = (String) "tester@logica.com";
		password = (String) "spire@123";
		//userId = (String) ContextManager.getThreadContext().getUserid();
		//password = (String) ContextManager.getThreadContext().getPassword();
		Logging.log("Start :: Login with Username: " + userId + "Password: "
				+ password + "and hostName: " + hostName);
		
	}


	/**
	 *  Udhay - Get -RequisitionSearch 
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/  
	@Test(groups = { "sanity", "GetRequisitionSearch" })
	public void GetRequisitionSearch() throws ClientProtocolException,
			IOException {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody =	reqConsumer.getRequisition(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("primarySkill"));
		Logging.log("contains the primary skill " );
		Assert.assertTrue(response.contains("jobLevel"));
		Logging.log("contains the jobLevel " );
		

	}
	
	/*
	 *  Vasista -Get the job description by requisition id
	 */
	
	@Test(groups = { "sanity", "GetJobDesByID" })
	public void GetJobDesByID() throws ClientProtocolException,
			IOException {
	
		RequisitionResourceConsumer reqConsumer = null;
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		reqConsumer.getJobDesByreqID(hostName);  
		Response responsebody =reqConsumer.getJobDesByreqID(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("primarySkill"));
		Logging.log("contains the primary skill " );
		Assert.assertTrue(response.contains("jobLevel"));
		Logging.log("contains the jobLevel " );
		
	}
	
	/* *  Udhay - /requisitions/{requisitionId}
	 * 
	 * Search RR with special character. The test case should not return 200 response 
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/  
	@Test(groups = { "sanity", "GetRequInvalidSrch" })
	public void GetRequInvalidSrch() throws ClientProtocolException,
			IOException {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody =	reqConsumer.getRequisitionInvalid(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("primarySkill"));
		Logging.log("contains the primary skill " );
		Assert.assertTrue(response.contains("jobLevel"));
		Logging.log("contains the jobLevel " );
		

	}	 
	
	
	
	/* *  Udhay - /requisitions/{requisitionId}
	 * 
	 * Search RR with Invalid Requisition. The test case should not return 200 response 
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/  
	@Test(groups = { "sanity", "GetRequInvalidInputSrch" })
	public void GetRequInvalidInputSrch() throws ClientProtocolException,
			IOException {
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody =	reqConsumer.getRequisitionInvalidInput(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("primarySkill"));
		Logging.log("contains the primary skill " );
		Assert.assertTrue(response.contains("jobLevel"));
		Logging.log("contains the jobLevel " );
		

	}	
	
	/**
	 * @author Radharani Patra 11/08/16 
	 * Steps:Post - Search requisition with mandatory field
	 *         Validation: Success Response Code, validate list of requiistion in response body
	 */
	@Test(groups = {  "searchRequisitionWithInSearchCriteria" })
	public void searchRequisitionWithInSearchCriteria(){
		searchReqrequestBean = RequisitionResourceServiceUtil.getSearchRequisition();
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response =	reqConsumer.searchRequisition(hostName,searchReqrequestBean);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull Expected:200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		//Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains("Open"), "Open requisition not found");
		Logging.log("InSearch Criteria : Open, Open requistions found");
	}
	
	/**
	 * @author Radharani Patra 11/08/16 
	 * Steps:Post - Search requisition with mandatory field
	 *         Validation: Success Response Code, validate list of requiistion in response body
	 */
	@Test(groups = {  "searchRequisitionWithoutSearchCriteria" })
	public void searchRequisitionWithoutSearchCriteria(){
		searchReqrequestBean1 = RequisitionResourceServiceUtil.getSearchRequisitionWithoutCriteria();
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response =	reqConsumer.searchRequisition(hostName,searchReqrequestBean1);
		Assertion.assertEquals(response.getStatus(), 400, "Response not successfull Expected:400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		/*String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains("Open"), "Open requisition not found");
		Logging.log("Open requiistions found");*/
	}
	/**
	 * @author Pritisudha Pattanaik 11/08/16
	 * Steps:Post - Get candidate stats for search criteria  with mandatory field
	 *         Validation: Success Response Code, validate list of requiistion in response body
	 */
	@Test(groups = { "sanity", "getcandidatestas" })
	public void getcandidatestas()
	{
		candidatestasBean1 = RequisitionResourceServiceUtil.getCandidateStasRequisition();
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response =	reqConsumer.searchRequisition(hostName,candidatestasBean1);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains("Open"), "Open requisition not found");
		Logging.log("Open requiistions found");
		
	}
	/**
	 * @author Pritisudha Pattanaik 11/08/16
	 * Steps:Post - Get candidate stats  without any field
	 *         Validation:  Response Code
	 */
	
	@Test(groups = {"sanity",  "candidatestasRequisitionWithoutSearchCriteria" })
	public void candidatestasRequisitionWithoutSearchCriteria(){
		searchReqrequestBean1 = RequisitionResourceServiceUtil.getCandiadteStasRequisitionWithoutCriteria();
		reqConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response response =	reqConsumer.searchRequisition(hostName,searchReqrequestBean1);
		Assertion.assertEquals(response.getStatus(), 400, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		
	}

	}
	
	
	
	
