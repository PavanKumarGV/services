package com.spire.requisitionResources;

import java.io.IOException;
import javax.ws.rs.core.Response;
import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.service.consumers.RequisitionResourceConsumer;
import com.spire.service.consumers.SearchResourcesConsumer;

public class RequisitionResourcesTestPlan extends TestPlan {

	String hostName;
	String userId;
	String password;

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
	@Test(groups = { "sanity", "searchRequisitionWithInSearchCriteria" })
	public void searchRequisitionWithInSearchCriteria(){
		
	}

	}
	
	
	
	
