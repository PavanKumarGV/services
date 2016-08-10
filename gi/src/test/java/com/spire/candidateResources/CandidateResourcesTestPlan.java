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
	String userId;
	String password;

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
	/** Get Candidate Profile
	 * Verify:Response code and asserting response.
	 */
	@Test(groups={"sanity" , "verifyGetCandidateProfileRequest" })
	public void verifyGetCandidateProfileRequest()
	{
		candConsumer = new CandidateResourcesConsumer();
		candConsumer.getToken(userId, password, hostName);
		
		Response responsebody = candConsumer.getcandidateprofile(hostName);
		Assertion.assertEquals(responsebody.getStatus(),200, "response expected 200 but found response code as:"+responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		System.out.println(response);
		Assertion.assertTrue(response.contains("6002:6005:19c5a4a6aabb4336a5718e079e26528e"), "not matching with the response body");
		
	}
	/** Get Candidate Profile without any parameter
	 * Verify:Response code 
	 */
	@Test(groups={"sanity" , "verifyGetCandidateProfilewithoutanyparameterRequest" })
	public void verifyGetCandidateProfilewithoutanyparameterRequest()
	{
		candConsumer = new CandidateResourcesConsumer();
		candConsumer.getToken(userId, password, hostName);
		
		Response responsebody = candConsumer.getCandidateprofilewithoutanyparameter(hostName);
		Assertion.assertEquals(responsebody.getStatus(),404, "response expected 404 but found response code as:"+responsebody.getStatus());
		
	}
	/**GET Candidate profile without id
	 * Verify:Response code
	 * 
	 */
	@Test(groups={"sanity" , "verifyGetCandidateProfilewithoutidRequest" })
	public void verifyGetCandidateProfilewithoutidRequest()
	{
		candConsumer = new CandidateResourcesConsumer();
		candConsumer.getToken(userId, password, hostName);
		Response responsebody = candConsumer.getCandidateprofilewithoutid(hostName);
		Assertion.assertEquals(responsebody.getStatus(),404, "response expected 404 but found response code as:"+responsebody.getStatus());
	}
	/** GET Candidate profile without projection
	 */
	@Test(groups={"sanity" , "verifyGetCandidateProfilewithoutprojectionRequest" })
	public void verifyGetCandidateProfilewithoutprojectionRequest()
	{
		candConsumer = new CandidateResourcesConsumer();
		candConsumer.getToken(userId, password, hostName);
		Response responsebody = candConsumer.getCandidateprofilewithoutprojection(hostName);
		Assertion.assertEquals(responsebody.getStatus(),200, "response expected 200 but found response code as:"+responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		System.out.println(response);
		Assertion.assertTrue(response.contains("6002:6005:19c5a4a6aabb4336a5718e079e26528e"), "not matching with the response body");
	}
	
}