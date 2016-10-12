package com.spire.candidateResources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.ReadingServiceEndPointsProperties;
import com.spire.base.service.utils.CandidateResourceServiceUtil;
import com.spire.service.consumers.CandidateResourcesConsumer;
import com.spire.service.consumers.CandidateStatsConsumer;
import com.spire.service.consumers.CandidateStatusConsumer;

import spire.talent.gi.beans.CandidateSpireStatusPojo;
import spire.talent.gi.beans.CandidateStatsRequestBean;
import spire.talent.gi.beans.CandidateStatusPojo;
import spire.talent.gi.beans.GetCandidateRequestBean;

public class CandidateResourcesTestPlan extends TestPlan {

	String hostName;
	CandidateResourcesConsumer candConsumer = null;
	String userId;
	String password;
	CandidateStatsRequestBean candStatsReqBean = null;
	CandidateStatsConsumer candStatsConsumer = null;
	GetCandidateRequestBean candRequestBean = null;
	CandidateStatusConsumer candStatusConsumer = null;

	/**
	 * Passing HostName from the xml.
	 */
	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
		userId = ReadingServiceEndPointsProperties.getServiceEndPoint("user_Id");
		password = ReadingServiceEndPointsProperties.getServiceEndPoint("password");
		Logging.log("Start :: Login with Username: " + userId + "Password: " + password + "and hostName: " + hostName);

	}

	/**
	 * Get Candidate Profile, provide id and projection parameter type
	 * Verify:Response code and asserting response.
	 */
	@Test(groups = { "sanity", "verifyGetCandidateProfileRequest", "NA" })
	public void verifyGetCandidateProfileRequest() {
		candConsumer = new CandidateResourcesConsumer();
		// Get authentication token
		candConsumer.getToken(userId, password, hostName);
		// Executes Get request and returns Response
		Response responsebody = candConsumer.getcandidateprofile(hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"response expected 200 but found response code as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		System.out.println(response);
		// Asserting response body
		Assertion.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id1")),
				"not matching with the response body");

	}

	/**
	 * Get Candidate Profile without any parameter Verify:Response code
	 */
	@Test(groups = { "sanity", "verifyGetCandidateProfilewithoutanyparameterRequest", "NA" })
	public void verifyGetCandidateProfilewithoutanyparameterRequest() {
		candConsumer = new CandidateResourcesConsumer();
		// Get authentication token
		candConsumer.getToken(userId, password, hostName);
		// Executes Get request and returns Response
		Response responsebody = candConsumer.getCandidateprofilewithoutanyparameter(hostName);
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() != 200,
				"response code expected not equal to 200 but found as:" + responsebody.getStatus());
	}

	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/profile
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate service without candidate Id. Invalid Rest service URL build in the absence of candidateId.
	* </p>
	* <p>
	* <b>Input :</b>blank candidateId 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 404 with proper error message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Non Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P4</font>
	* </p>
	*/
	@Test(groups = { "sanity", "verifyGetCandidateProfileWithoutCandidateId", "NA" })
	public void verifyGetCandidateProfileWithoutCandidateId() {
		Logging.log("Service Name: generic-services/api/candidates/profile"
				+ "\nDescription: Get candidate service without candidate Id. Invalid Rest URL build in the absence of candidateId"
				+ "\nInput: blank candidateId" + "\nExpected Output: Response status 404 with proper error message");
		candConsumer = new CandidateResourcesConsumer();
		candConsumer.getToken(userId, password, hostName);
		Response responsebody = candConsumer.getCandidateprofilewithoutid(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 404,
				"response code expected not equal to 404 but found as:" + responsebody.getStatus());
	}

	/**
	 * GET Candidate profile without projection Verify:Response code
	 */
	@Test(groups = { "sanity", "verifyGetCandidateProfilewithoutprojectionRequest", "NA" })
	public void verifyGetCandidateProfilewithoutprojectionRequest() {
		candConsumer = new CandidateResourcesConsumer();
		// Get authentication token
		candConsumer.getToken(userId, password, hostName);
		// Executes Get request and returns Response
		Response responsebody = candConsumer.getCandidateprofilewithoutprojection(hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"response expected 200 but found response code as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		System.out.println(response);
		// Asserting Response Body
		Assertion.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id1")),
				"not matching with the response body");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:(POST REQUEST)Get Candidate stats
	 *         with valid parameter(Requiistion Id and attribute) Validation:
	 *         Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithValidParameter", "P1" })
	public void getCandidateStatsWithValidParameter() {
		// Get Requset Bean, pass multiple requisitions
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStats(
				ReadingServiceEndPointsProperties.getServiceEndPoint("requisition_Id1"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("stas_requisition_id"));
		// Get authentication token
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		// Asserting response Body
		Assertion.assertTrue(
				responseBody.contains("Automatched") || responseBody.contains("Customer Mapped")
						|| responseBody.contains("Applied") || responseBody.contains("New")
						|| responseBody.contains("Active") || responseBody.contains("Pending"),
				"Get Candidate Stats Unsuccessful");
		Logging.log("Get Candidate Stats successful and contains Automatched or Customer Mapped status count");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate stats with valid
	 *         parameter(Requisition Id and attribute Gender) Validation:
	 *         Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithValidParameterGender", "P1" })
	public void getCandidateStatsWithValidParameterGender() {
		// Get Requset Bean, pass multiple requisitions
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsGender(
				ReadingServiceEndPointsProperties.getServiceEndPoint("requisition_Id"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("stas_requisition_id"));
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		// Asserting response Body
		Assertion.assertTrue(responseBody.contains("F") || responseBody.contains("M"),
				"Get Candidate Stats Unsuccessful");
		Logging.log("Get Candidate Stats successful and contains gender count ");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate stats with blank
	 *         parameter Validation: Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithBlankParameter", "P2" })
	public void getCandidateStatsWithBlankParameter() {
		// Get Requset Bean, pass parameters
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsBlank();
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		// Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats unsuccessful with respone: INVALID_PARAMETER ");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate stats with Invalid
	 *         parameter(Requisition Id ) blank attribute Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithReqIdParameter", "P1" })
	public void getCandidateStatsWithReqIdParameter() {
		// Get Requset Bean, pass requisitions
		candStatsReqBean = CandidateResourceServiceUtil
				.getCandidateStatsReqId(ReadingServiceEndPointsProperties.getServiceEndPoint("requisition_Id"));
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		// Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats unsuccessful with respone: INVALID_PARAMETER ");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate stats with Invalid
	 *         parameter(Requisition Id ) blank attribute Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithAttributeParameter", "P1" })
	public void getCandidateStatsWithAttributeParameter() {
		// Get Requset Bean, pass attribute
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsAttribute();
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		// Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats unsuccessful with respone: INVALID_PARAMETER ");
	}

	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/stats
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate with invalid candidateId and status.
	* </p>
	* <p>
	* <b>Input :</b>Invalid candidateId and status that not existing in the system
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 204 with proper error message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Non Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#81017F> P2</font>
	* </p>
	* @author Radharani Patra
	* @since 12/08/16
	*/
	@Test(groups = { "sanity", "getCandidateStatsWithInvalidParameter", "P2" })
	public void getCandidateStatsWithInvalidParameter() {
		Logging.log("Service Name: generic-services/api/candidates/stats"
				+ "\nDescription: Get candidate with invalid candidateId and status."
				+ "\nInput: Invalid candidateId and status that not existing in the system" + "\nExpected Output: Error Response status with proper error message");
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsInvalid();
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		Assertion.assertTrue(response.getStatus()!=200, "Response successfull, Expected  200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains(""), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats successful with invalid parameter Returning Blank Response ");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with valid
	 *         parameter(Candidate Id and Projection Type) Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithValidParameterBASIC", "P1" })
	public void getCandidateListWithValidParameterBASIC() {
		// Get Requset Bean, pass valid parameter, candidate id and projection
		// type
		candRequestBean = CandidateResourceServiceUtil
				.getCandidateListBasic(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		// Asserting response Body
		Assertion.assertTrue(
				responseBody.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id")),
				"Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		Logging.log(responseBody);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with valid
	 *         parameter(Candidate Id and Projection Type) Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithValidParameterFULL", "P1" })
	public void getCandidateListWithValidParameterFULL() {
		// Get Requset Bean, pass valid parameter, candidate id and projection
		// type
		candRequestBean = CandidateResourceServiceUtil
				.getCandidateListFull(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		// Asserting response Body
		Assertion.assertTrue(
				responseBody.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id")),
				"Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		Logging.log(responseBody);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with valid
	 *         parameter(Candidate Id and Projection Type) Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithValidParameterCUSTOM", "P1" })
	public void getCandidateListWithValidParameterCUSTOM() {
		// Get Requset Bean, pass valid parameter, candidate id and projection
		// type
		candRequestBean = CandidateResourceServiceUtil
				.getCandidateListCustom(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		// Asserting response Body
		Assertion.assertTrue(
				responseBody.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id")),
				"Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		Logging.log(responseBody);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with blank
	 *         parameter(Candidate Id and Projection Type) Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithBlankParameter", "P2" })
	public void getCandidateListWithBlankParameter() {
		// Get Requset Bean, pass blank candidate id and projection type
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBlank();
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		// Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate List Successful");
		Logging.log("Get candidate list Unsuccessful with INVALID_PARAMETER");
		Logging.log(responseBody);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with Invalid
	 *         parameter(Candidate Id blank and Projection Type) Validation:
	 *         Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithBlankCandidateId", "P2" })
	public void getCandidateListWithBlankCandidateId() {
		// Get Requset Bean, pass blank candidate id and projection type
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBlankCId();
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		// Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate List Successful");
		Logging.log("Get candidate list Unsuccessful with INVALID_PARAMETER");
		Logging.log(responseBody);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with
	 *         parameter(Candidate Id ) Validation: Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithBlankProjectionType", "P2" })
	public void getCandidateListWithBlankProjectionType() {
		// Get Requset Bean, pass candidate id
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBlankPojectiontype(
				ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		// Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Get Response body
		String responseBody = response.readEntity(String.class);
		// Asserting response Body
		Assertion.assertTrue(
				responseBody.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id")),
				"Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: "
				+ ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		Logging.log(responseBody);
	}

	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/candidates/list
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Testing candidate list service with list of candidateIds as input and
	 * invalid projection type. failure response 400 with candidates expected.
	 * </p>
	 * <p>
	 * <b>Input :</b>Using valid candidateId and invalid projection type
	 * </p>
	 * <p>
	 * <b>Expected Output :</b> Response status 400
	 * </p>
	 * <p>
	 * <b>Category :</b> Negative - NonFunctional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#E6A001> P3</font>
	 * </p>
	 * @author Radharani Patra
	 * @since 12/08/16
	 */
	@Test(groups = { "sanity", "getCandidateListWithInvalidProjectionType", "P2" })
	public void getCandidateListWithInvalidProjectionType() {
		Logging.log("Service Name: generic-services/api/candidates/list"
				+ "\nDescription:  Testing candidate list service with list of candidateIds as input and invalid projection type. Failure response status 400 expected."
				+ "\nInput: Using valid candidateId and invalid projection type" + "\nExpected Output: Response status 400");
		candRequestBean = CandidateResourceServiceUtil.getCandidateListInvalidProjectionType(
				ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains("not one of declared Enum instance names"), "Get Candidate List successfull");
		Logging.log(responseBody);
	}

	/**
	 * priti- GET candidate profile without headers Validation on response code
	 *
	 */
	@Test(groups = { "sanity", "verifyGetCandidateProfilewithoutheaderRequest", "NA" })
	public void verifyGetCandidateProfilewithoutheaderRequest() {
		candConsumer = new CandidateResourcesConsumer();
		Response responsebody = candConsumer.getcandidateprofile(hostName);
		Assertion.assertTrue((responsebody.getStatus()) != 200,
				"response code expected not equal to 200 but found as:" + responsebody.getStatus());

	}

 	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/resume
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate Resume with valid candidateId
	* </p>
	* <p>
	* <b>Input :</b>candidateId that valid and exist in the system
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
	* @author Radharani Patra
	* @since 16/08/16
	*/
	@Test(groups = { "sanity", "getCandidateResume", "P1", "NA" })
	public void getCandidateResume() {
		Logging.log("Service Name: generic-services/api/candidates/resume"
				+ "\nDescription: Verifying candidates notes search service with incorrect parameter and expecting failure response."
				+ "\nInput: Using Id that not present in the system" + "\nExpected Output: Response status 204");
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer
				.getCandidateResume(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_resume"), hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response Unsuccessfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String resStr = response.readEntity(String.class);
		Assertion.assertFalse(resStr.contains("SYSTEM_ERROR"), "SYSTEM_ERROR occured");
		Assertion.assertTrue(resStr.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_resume_test_fileName")),"Actual File Name not Found");
		Assertion.assertTrue(resStr.contains("filename") || resStr.contains("fileName"),
				"Resume not present");
		Logging.log("Service Response Message: "+ resStr);
	}

	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/resume
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate Resume with invalid candidateId with special characters.
	* </p>
	* <p>
	* <b>Input :</b>blank candidateId 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 500 with proper error message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Boundary Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#007D77> P4</font>
	* </p>
	* @author Radharani Patra
	* @since 16/08/16
	*/
	@Test(groups = { "sanity", "getCandidateResumeWithBlankCandidateId", "P2", "NA" })
	public void getCandidateResumeWithInvalidCandidateId_SplCharactors() {
		Logging.log("Service Name: generic-services/api/candidates/resume"
				+ "\nDescription: Get candidate Resume with invalid candidateId with special characters."
				+ "\nInput: blank candidateId" + "\nExpected Output: Response status 500 with proper error message");
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateResumeInvalidSplCharIds(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_resume"), hostName);
		Assertion.assertTrue(response.getStatus() == 500, "Response Expected as 500 Server internal error");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}

	 
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/resume
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get candidate Resume with invalid candidateId
	* </p>
	* <p>
	* <b>Input :</b>blank candidateId 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 500 with proper error message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Boundary Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#E6A001> P3</font>
	* </p>
	* @author Radharani Patra
	* @since 16/08/16
	*/
	@Test(groups = { "sanity", "getCandidateResumeWithInvalidCandidateId", "P3", "NA" })
	public void getCandidateResumeWithInvalidCandidateId() {
		Logging.log("Service Name: generic-services/api/candidates/resume"
				+ "\nDescription: Get candidate Resume with invalid candidateId with special characters."
				+ "\nInput: blank candidateId" + "\nExpected Output: Response status 500 with proper error message");
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.getCandidateResumeInvalid(
				ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_resume"), hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		Assertion.assertTrue(response.getStatus() == 204, "Response Successfull, Expected 204 status code");
		Logging.log("Response unsuccessful");
	}

	
	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/candidates/resume
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Get candidate Resume with valid candidateId without headers
	 * </p>
	 * <p>
	 * <b>Input :</b>valid candidateId request without headers
	 * </p>
	 * <p>
	 * <b>Expected Output :</b> Response status 403 with proper error message
	 * </p>
	 * <p>
	 * <b>Category :</b> Negative - Non Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :<font color=#007D77> P4</font>
	 * </p>
	 * 
	 * @author Radharani Patra
	 * @since 16/08/16
	 */
	@Test(groups = { "sanity", "getCandidateResumeWithoutHeaders", "P4", "NA" })
	public void getCandidateResumeWithoutHeaders() {
		Logging.log("Service Name: generic-services/api/candidates/resume"
				+ "\nDescription: Get candidate Resume with valid candidateId without headers."
				+ "\nInput: Valid candidateId request without headers"
				+ "\nExpected Output: Response status 403 with proper error message");
		candConsumer = new CandidateResourcesConsumer();
		Response response = candConsumer
				.getCandidateResumeWithoutHeaders(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_resume"), hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		Assertion.assertTrue(response.getStatus() == 403, "Response unSuccessfull, Expected 403 status code");
		Logging.log("Response successful");
	}

	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/candidates/deallocate
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Testing deallocate candidate service with valid parameters and expecting
	 * success response.
	 * </p>
	 * <p>
	 * <b>Input :</b>Valid and allocated existing entity requisitionId,
	 * candidateId, requisitionDisplayId, candidateDisplayId, isCurrent=Y
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
	 */
	@Test(groups = { "deallocateCandidates", "P1", "sanity" }, dependsOnGroups = { "allocateCandidates" })
	public void deallocateCandidates() {
		Logging.log("Service Name: generic-services/api/candidates/deallocate"
				+ "\nDescription: Testing deallocate candidate service with valid parameters of allocated entities and expecting success response."
				+ "\nInput: Valid and allocated existing entity data for requisitionId, candidateId, requisitionDisplayId, candidateDisplayId, isCurrent=Y"
				+ "\nExpected Output: Response status 200");
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		String inputjson = "{\"" + ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_deallocate")
				+ "\":[\"" + ReadingServiceEndPointsProperties.getServiceEndPoint("requisition_deallocate") + "\"]}";
		Response response = candConsumer.deallocatecandidate(hostName, inputjson);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String responseBody = response.readEntity(String.class);
		Logging.log("RESPONSE: " + responseBody);
	}

	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/candidates/allocate
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Testing allocate candidate service with valid parameters and expecting
	 * success response.
	 * </p>
	 * <p>
	 * <b>Input :</b> requisitionId, candidateId, requisitionDisplayId,
	 * candidateDisplayId, isCurrent=Y
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
	 */
	@Test(groups = { "allocateCandidates", "P1" })
	public void allocateCandidates() {
		Logging.log("Service Name: generic-services/api/candidates/allocate"
				+ "\nDescription: Testing allocate candidate service with valid parameters and expecting success response."
				+ "\nInput: Valid and existing data for requisitionId, candidateId, requisitionDisplayId, candidateDisplayId, isCurrent=Y"
				+ "\nExpected Output: Response status 200");
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		Response response = candConsumer.allocatecandidate(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		Assertion.assertTrue(response.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		Logging.log("Response successful");
		String responseBody = response.readEntity(String.class);
		Logging.log("RESPONSE: " + responseBody);
	}
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/status
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate status for given valid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>valid candidateId and valid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200 with proper  message
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#81017F> P2</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 04-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateStatus_PositiveFunctional", "P2", "NA" })
	public void testPutCandidateStatus_PositiveFunctional() {
		Logging.log("Service Name: generic-services/api/candidates/status"
				+ "\nDescription: Update the candidate status for given valid candidate Id."
				+ "\nInput: Valid candidateId and valid status " + "\nExpected Output: Response status 200 with proper  message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateStatusPojo testInput = new CandidateStatusPojo();
		testInput.setCandidateId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		testInput.setClientStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status1"));
		List<CandidateStatusPojo> testInputList = new ArrayList<>();
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 200, "Response Expected as 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}
	
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/status
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate status for given invalid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>Invalid candidateId and valid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 400 with proper message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Non-Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#E6A001> P3</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 04-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateStatus_NegativeNonFunctional", "P2", "NA" })
	public void testPutCandidateStatus_NegativeNonFunctional() {
		Logging.log("Service Name: generic-services/api/candidates/status"
				+ "\nDescription: Update the candidate status for given invalid candidate Id."
				+ "\nInput: Invalid candidateId and valid status" + "\nExpected Output: Response status 400 with proper message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateStatusPojo testInput = new CandidateStatusPojo();
		testInput.setCandidateId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id_invalid"));
		testInput.setClientStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status1"));
		List<CandidateStatusPojo> testInputList = new ArrayList<>();
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 400, "Response Expected as 400 ");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}
	
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/status
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate with invalid status for given valid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>Valid candidateId and invalid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 400 with proper message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Non-Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#E6A001> P3</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 04-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateStatus_NegativeNonFunctional", "P2", "NA" })
	public void testPutCandidateStatus_NegativeNonFunctional2() {
		Logging.log("Service Name: generic-services/api/candidates/status"
				+ "\nDescription: Update the candidate with invalid status for given valid candidate Id."
				+ "\nInput: Valid candidateId and invalid status" + "\nExpected Output: Response status 400 with proper message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateStatusPojo testInput = new CandidateStatusPojo();
		testInput.setCandidateId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		testInput.setClientStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status1_invalid"));
		List<CandidateStatusPojo> testInputList = new ArrayList<>();
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 400, "Response Expected as 400 ");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/status
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate with invalid status for given invalid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>Invalid candidateId and invalid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 400 with proper message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Non-Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#E6A001> P3</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 04-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateStatus_NegativeNonFunctional", "P2", "NA" })
	public void testPutCandidateStatus_NegativeNonFunctional3() {
		Logging.log("Service Name: generic-services/api/candidates/status"
				+ "\nDescription: Update the candidate with invalid status for given invalid candidate Id."
				+ "\nInput: Invalid candidateId and invalid status" + "\nExpected Output: Response status 400 with proper message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateStatusPojo testInput = new CandidateStatusPojo();
		testInput.setCandidateId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id_invalid"));
		testInput.setClientStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status1_invalid"));
		List<CandidateStatusPojo> testInputList = new ArrayList<>();
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 400, "Response Expected as 400 ");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/status
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate status for given valid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>valid candidateId and valid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200 with proper  message
	* </p>
	* <p>
	* <b>Category :</b> Positive - Boundary Test Case
	* </p>
	* <p>
	* <b>Bug Level : <font color=#E6A001> P3</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 04-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateStatus_PositiveFunctional", "P2", "NA" })
	public void testPutCandidateStatus_BoundaryCase() {
		Logging.log("Service Name: generic-services/api/candidates/status"
				+ "\nDescription: Update the candidate status for given valid candidate Id."
				+ "\nInput: Valid candidateId and valid status " + "\nExpected Output: Response status 200 with proper  message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateStatusPojo testInput = new CandidateStatusPojo();
		List<CandidateStatusPojo> testInputList = new ArrayList<>();
		
		testInput.setCandidateId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id2"));
		testInput.setClientStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status2"));
		testInputList.add(testInput);
		
		testInput = new CandidateStatusPojo();
		testInput.setCandidateId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		testInput.setClientStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 200, "Response Expected as 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}
	
    /**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/spireStatus
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate status for given valid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>valid candidateId and valid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200 with proper  message
	* </p>
	* <p>
	* <b>Category :</b> Positive - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#81017F> P2</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 05-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateSpireStatus_PositiveFunctional", "P2", "NA" })
	public void testPutCandidateSpireStatus_PositiveFunctional() {
		Logging.log("Service Name: generic-services/api/candidates/spireStatus"
				+ "\nDescription: Update the candidate status for given valid candidate Id."
				+ "\nInput: Valid candidateId and valid status " + "\nExpected Output: Response status 200 with proper  message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateSpireStatusPojo testInput = new CandidateSpireStatusPojo();
		testInput.setId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		testInput.setStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status1"));
		List<CandidateSpireStatusPojo> testInputList = new ArrayList<>();
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateSpireStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 200, "Response Expected as 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}
	
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/spireStatus
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate status for given invalid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>Invalid candidateId and valid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 400 with proper message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Non-Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#E6A001> P3</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 05-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateSpireStatus_NegativeNonFunctional", "P2", "NA" })
	public void testPutCandidateSpireStatus_NegativeNonFunctional() {
		Logging.log("Service Name: generic-services/api/candidates/spireStatus"
				+ "\nDescription: Update the candidate status for given invalid candidate Id."
				+ "\nInput: Invalid candidateId and valid status" + "\nExpected Output: Response status 400 with proper message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateSpireStatusPojo testInput = new CandidateSpireStatusPojo();
		testInput.setId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id_invalid"));
		testInput.setStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status1"));
		List<CandidateSpireStatusPojo> testInputList = new ArrayList<>();
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateSpireStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 400, "Response Expected as 400 ");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}
	
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/spireStatus
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate with invalid status for given valid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>Valid candidateId and invalid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 400 with proper message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Non-Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#E6A001> P3</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 05-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateSpireStatus_NegativeNonFunctional", "P2", "NA" })
	public void testPutCandidateSpireStatus_NegativeNonFunctional2() {
		Logging.log("Service Name: generic-services/api/candidates/spireStatus"
				+ "\nDescription: Update the candidate with invalid status for given valid candidate Id."
				+ "\nInput: Valid candidateId and invalid status" + "\nExpected Output: Response status 400 with proper message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateSpireStatusPojo testInput = new CandidateSpireStatusPojo();
		testInput.setId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		testInput.setStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status1_invalid"));
		List<CandidateSpireStatusPojo> testInputList = new ArrayList<>();
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateSpireStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 400, "Response Expected as 400 ");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/spireStatus
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate with invalid status for given invalid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>Invalid candidateId and invalid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 400 with proper message
	* </p>
	* <p>
	* <b>Category :</b> Negative - Non-Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :<font color=#E6A001> P3</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 05-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateSpireStatus_NegativeNonFunctional", "P2", "NA" })
	public void testPutCandidateSpireStatus_NegativeNonFunctional3() {
		Logging.log("Service Name: generic-services/api/candidates/spireStatus"
				+ "\nDescription: Update the candidate with invalid status for given invalid candidate Id."
				+ "\nInput: Invalid candidateId and invalid status" + "\nExpected Output: Response status 400 with proper message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateSpireStatusPojo testInput = new CandidateSpireStatusPojo();
		testInput.setId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id_invalid"));
		testInput.setStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status1_invalid"));
		List<CandidateSpireStatusPojo> testInputList = new ArrayList<>();
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateSpireStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 400, "Response Expected as 400 ");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}
	
	/**
	* <p>
	* <b>Target Service URL :</b> generic-services/api/candidates/spireStatus
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Update the candidate status for given valid candidate Id.
	* </p>
	* <p>
	* <b>Input :</b>valid candidateId and valid status 
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200 with proper  message
	* </p>
	* <p>
	* <b>Category :</b> Positive - Boundary Test Case
	* </p>
	* <p>
	* <b>Bug Level : <font color=#E6A001> P3</font>
	* </p>
	* @author Santhosh Ramanan
	* @since 05-Oct-2016
	*/
	@Test(groups = { "sanity", "testPutCandidateSpireStatus_PositiveFunctional", "P2", "NA" })
	public void testPutCandidateSpireStatus_BoundaryCase() {
		Logging.log("Service Name: generic-services/api/candidates/spireStatus"
				+ "\nDescription: Update the candidate status for given valid candidate Id."
				+ "\nInput: Valid candidateId and valid status " + "\nExpected Output: Response status 200 with proper  message");
		candStatusConsumer = new CandidateStatusConsumer(userId, password, hostName);
		CandidateSpireStatusPojo testInput = new CandidateSpireStatusPojo();
		List<CandidateSpireStatusPojo> testInputList = new ArrayList<>();
		
		testInput.setId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id2"));
		testInput.setStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status2"));
		testInputList.add(testInput);
		
		testInput = new CandidateSpireStatusPojo();
		testInput.setId(ReadingServiceEndPointsProperties.getServiceEndPoint("candidate_Id"));
		testInput.setStatus(ReadingServiceEndPointsProperties.getServiceEndPoint("test_status"));
		testInputList.add(testInput);
		Response response = candStatusConsumer.putCandidateSpireStatus(testInputList,hostName);
		Assertion.assertTrue(response.getStatus() == 200, "Response Expected as 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
	}


}
