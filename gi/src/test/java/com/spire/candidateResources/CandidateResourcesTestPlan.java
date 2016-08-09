package com.spire.candidateResources;

import javax.ws.rs.core.Response;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.service.consumers.CandidateResourcesConsumer;

import junit.framework.Assert;

public class CandidateResourcesTestPlan extends TestPlan {

	String hostName;
	CandidateResourcesConsumer candConsumer = null;

	/**
	 * Passing HostName,UserName and Password from the xml.
	 */

	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
	}
	
	/**GET candidates
	 * Verify: Response Code*/
	@Test(groups = { "sanity" , "verifyFetchCandidatesRequest" })
	public void verifyFetchCandidatesRequest(){
		candConsumer = new CandidateResourcesConsumer();
		int actualStatusCode = candConsumer.getCandidates(hostName);
		Assertion.assertEquals(200, actualStatusCode, "Candidate Fetch Request Fail - Status Code: "+actualStatusCode);
		Logging.log("Candidate Fetch Request Pass");
	}
	
	/**GET Resume
	 * Verify: Response Code*/
	@Test(groups = { "sanity" , "verifyGetResumeRequest" })
	public void verifyGetResumeRequest(){
		candConsumer = new CandidateResourcesConsumer();
		int actualStatusCode = candConsumer.getResume(hostName);
		Assertion.assertEquals(200, actualStatusCode, "Get Resume Request Fail - Status Code: "+actualStatusCode);
		Logging.log("Get Resume Request Pass");
	}
}