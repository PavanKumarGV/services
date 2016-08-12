package com.spire.candidateResources;

import javax.ws.rs.core.Response;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.base.service.Constants;
import com.spire.base.service.utils.CandidateResourceServiceUtil;
import com.spire.service.consumers.CandidateResourcesConsumer;
import com.spire.service.consumers.CandidateStatsConsumer;

import junit.framework.Assert;
import spire.talent.gi.beans.CandidateStatsRequestBean;
import spire.talent.gi.beans.GetCandidateRequestBean;

public class CandidateResourcesTestPlan extends TestPlan {

	String hostName;
	CandidateResourcesConsumer candConsumer = null;
	String userId;
	String password;
	CandidateStatsRequestBean candStatsReqBean = null;
	CandidateStatsConsumer candStatsConsumer = null;
	GetCandidateRequestBean candRequestBean = null;

	/**
	 * Passing HostName,UserName and Password from the xml.
	 */
	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
		//userId = (String) ContextManager.getThreadContext().getUserid();
		//password = (String) ContextManager.getThreadContext().getPassword();
		userId = Constants.user_Id;
		password = Constants.password;
		Logging.log("Start :: Login with Username: " + userId + "Password: "
				+ password + "and hostName: " + hostName);
		
	}
	/**GET candidates
	 * Verify: Response Code*/
	@Test(groups = {  "verifyFetchCandidatesRequest" })
	public void verifyFetchCandidatesRequest(){
		candConsumer = new CandidateResourcesConsumer();
		int actualStatusCode = candConsumer.getCandidates(hostName);
		Assertion.assertEquals(200, actualStatusCode, "Candidate Fetch Request Fail - Status Code: "+actualStatusCode);
		Logging.log("Candidate Fetch Request Pass");
	}
	
	/**GET Resume
	 * Verify: Response Code*/
	@Test(groups = {  "verifyGetResumeRequest" })
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
		Assertion.assertTrue(responsebody.getStatus()!=200, "response code expected not equal to 200 but found as:"+responsebody.getStatus());
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
		Assertion.assertTrue(responsebody.getStatus()!=200, "response code expected not equal to 200 but found as:"+responsebody.getStatus());
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
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate stats with valid parameter(Requiistion Id and attribute)
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateStatsWithValidParameter" })
	public void getCandidateStatsWithValidParameter() {
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStats(Constants.requisition_Id,Constants.requisition_Id1);
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean,hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains("Automatched")||responseBody.contains("Customer Mapped"), "Get Candidate Stats Unsuccessful");
		Logging.log("Get Candidate Stats successful and contains Automatched or Customer Mapped status count");
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate stats with valid parameter(Requisition Id and attribute Gender)
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateStatsWithValidParameterGender" })
	public void getCandidateStatsWithValidParameterGender() {
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsGender(Constants.requisition_Id,Constants.requisition_Id1);
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean,hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains("F")||responseBody.contains("M"), "Get Candidate Stats Unsuccessful");
		Logging.log("Get Candidate Stats successful and contains gender count ");
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate stats with blank parameter
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateStatsWithBlankParameter" })
	public void getCandidateStatsWithBlankParameter() {
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsBlank();
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean,hostName);
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats unsuccessful with respone: INVALID_PARAMETER ");
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate stats with Invalid parameter(Requisition Id ) blank attribute
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateStatsWithReqIdParameter" })
	public void getCandidateStatsWithReqIdParameter() {
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsReqId(Constants.requisition_Id);
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean,hostName);
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats unsuccessful with respone: INVALID_PARAMETER ");
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate stats with Invalid parameter(Requisition Id ) blank attribute
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateStatsWithAttributeParameter" })
	public void getCandidateStatsWithAttributeParameter() {
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsAttribute();
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean,hostName);
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats unsuccessful with respone: INVALID_PARAMETER ");
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate stats with Invalid parameter(Requisition Id ) blank attribute
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateStatsWithInvalidParameter" })
	public void getCandidateStatsWithInvalidParameter() {
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsInvalid();
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean,hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains(""), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats successful with invalid parameter Returning Blank Response ");
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate List with valid parameter(Candidate Id and Projection Type)
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateListWithValidParameterBASIC" })
	public void getCandidateListWithValidParameterBASIC() {
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBasic(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateList(candRequestBean,hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains(Constants.candidate_Id), "Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: "+Constants.candidate_Id);
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate List with valid parameter(Candidate Id and Projection Type)
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateListWithValidParameterFULL" })
	public void getCandidateListWithValidParameterFULL() {
		candRequestBean = CandidateResourceServiceUtil.getCandidateListFull(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateList(candRequestBean,hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains(Constants.candidate_Id), "Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: "+Constants.candidate_Id);
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate List with valid parameter(Candidate Id and Projection Type)
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateListWithValidParameterCUSTOM" })
	public void getCandidateListWithValidParameterCUSTOM() {
		candRequestBean = CandidateResourceServiceUtil.getCandidateListCustom(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateList(candRequestBean,hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains(Constants.candidate_Id), "Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: "+Constants.candidate_Id);
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate List with blank parameter(Candidate Id and Projection Type)
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateListWithBlankParameter" })
	public void getCandidateListWithBlankParameter() {
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBlank();
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateList(candRequestBean,hostName);
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate List Successful");
		Logging.log("Get candidate list Unsuccessful with INVALID_PARAMETER");
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate List with Invalid parameter(Candidate Id blank and Projection Type)
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateListWithBlankCandidateId" })
	public void getCandidateListWithBlankCandidateId() {
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBlankCId();
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateList(candRequestBean,hostName);
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate List Successful");
		Logging.log("Get candidate list Unsuccessful with INVALID_PARAMETER");
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate List with parameter(Candidate Id )
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateListWithBlankProjectionType" })
	public void getCandidateListWithBlankProjectionType() {
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBlankPojectiontype(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateList(candRequestBean,hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains(Constants.candidate_Id), "Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: "+Constants.candidate_Id);
	}
	
	/**
	 * @author Radharani Patra 12/08/16 
	 * Steps:Get Candidate List with parameter(Candidate Id )
	 *         Validation: Response code and body
	 */

	@Test(groups = { "sanity","getCandidateListWithInvalidProjectionType" })
	public void getCandidateListWithInvalidProjectionType() {
		candRequestBean = CandidateResourceServiceUtil.getCandidateListInvalidCandidateId(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateList(candRequestBean,hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains(""), "Get Candidate List successfull");
		Logging.log("Response is blank for invalid candidate id");
	}
	
	/**priti- GET candidate profile without headers
     * Validation on response code
     *
     */
    @Test(groups={"sanity","verifyGetCandidateProfilewithoutheaderRequest"})
    public void verifyGetCandidateProfilewithoutheaderRequest()
    {
        candConsumer = new CandidateResourcesConsumer();
        Response responsebody = candConsumer.getcandidateprofile(hostName);
        Assertion.assertTrue((responsebody.getStatus())!= 200, "response code expected not equal to 200 but found as:"+responsebody.getStatus());
        
        
        
    }
}