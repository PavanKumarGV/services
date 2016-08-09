package com.spire.lookUpResources;

import javax.ws.rs.core.Response;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.service.consumers.CandidateResourcesConsumer;
import com.spire.service.consumers.LookUpResourcesConsumer;

public class LookUpResourcesTestPlan extends TestPlan {

	String hostName;
	LookUpResourcesConsumer lookUpConsumer = null;

	/**
	 * Passing HostName,UserName and Password from the xml.
	 */

	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
	}
	
	/**GET list of demand filter by REQUISITION_STATUS
	 * Verify: Response Code/Response body - Open requisition*/
	@Test(groups = { "sanity" , "verifyLookupservices" })
	public void verifyLookupservices(){
		lookUpConsumer = new LookUpResourcesConsumer();
		Response response = lookUpConsumer.getListOfDemandFilter(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
		Assertion.assertTrue(responseBody.contains("Open"),  "Open demand filter is not available.");
		Logging.log("Open demand filter is available.");
	}
}