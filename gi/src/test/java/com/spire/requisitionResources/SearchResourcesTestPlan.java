package com.spire.requisitionResources;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.service.consumers.RequisitionResourceConsumer;
import com.spire.service.consumers.SearchResourcesConsumer;

public class SearchResourcesTestPlan extends TestPlan {

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
		userId = (String) ContextManager.getThreadContext().getUserid();
		password = (String) ContextManager.getThreadContext().getPassword();
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
		candConsumer.getRequisition(hostName);  

	}
}