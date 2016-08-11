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

	RequisitionResourceConsumer candConsumer = null;

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
		candConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody =	candConsumer.getRequisition(hostName);  
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
	
		RequisitionResourceConsumer candConsumer = null;
		candConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		candConsumer.getJobDesByreqID(hostName);  
		Response responsebody =candConsumer.getJobDesByreqID(hostName);  
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
		candConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody =	candConsumer.getRequisitionInvalid(hostName);  
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
		candConsumer = new RequisitionResourceConsumer(userId, password, hostName);
		Response responsebody =	candConsumer.getRequisitionInvalidInput(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("primarySkill"));
		Logging.log("contains the primary skill " );
		Assert.assertTrue(response.contains("jobLevel"));
		Logging.log("contains the jobLevel " );
		

	}	

	}
	
	
	
	
