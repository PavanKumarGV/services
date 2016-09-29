package com.spire.lookUpResources;

import javax.ws.rs.core.Response;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.base.service.Constants;
import com.spire.base.service.ReadingServiceEndPointsProperties;
import com.spire.service.consumers.CandidateResourcesConsumer;
import com.spire.service.consumers.LookUpResourcesConsumer;

public class LookUpResourcesTestPlan extends TestPlan {

	String hostName;
	LookUpResourcesConsumer lookUpConsumer = null;
	String userId;
	String password;

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
	 * Steps: GET list of demand filter by Type(REQUISITION_STATUS) Validation:
	 * Asserting Open Requisition Status in response body
	 */

	@Test(groups = { "sanity", "verifyLookupservices","P1","NA" })
	public void verifyLookupservices() {
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilter(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
		// Asserting Response Body
		Assertion.assertTrue(responseBody.contains("Open"), "Open demand filter is not available.");
		Logging.log("Open demand filter is available.");
	}

	/**
	 * Steps: GET list of demand filter by Type(REQUISITION_STATUS) and
	 * Keyword(o) Validation: Asserting Joined Requistion Status in response
	 * body
	 */

	@Test(groups = { "sanity", "verifyLookupservicesByTypeNKeyword","P1","NA" })
	public void verifyLookupservicesByTypeNKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByTypeNKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
		// Asserting Response Body
		Assertion.assertTrue(responseBody.contains("Closed")||responseBody.contains("Open"), "Closed/Open demand filter is not available.");
		Logging.log("Closed/Open demand filter is available.");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter with
	 *         Blank demand filter type Validation: Status code 500, Response
	 *         message
	 */
	@Test(groups = { "sanity", "getListOfDemandFilterWithBlankType","P2","NA" })
	public void getListOfDemandFilterWithoutType() {
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		// Executes Get request and returns Response
		Response response = lookUpConsumer.verifyListOfDemandFilterWithoutType(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertTrue(response.getStatus()!=200, "response code expected not equal to 200 but found as:"+response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter with
	 *         Blank demand filter type Validation: Status code 500, Response
	 *         message
	 */
	@Test(groups = { "sanity", "getListOfDemandFilterWithInvalidType","P2","NA" })
	public void getListOfDemandFilterWithInvalidType() {
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		// Executes Get request and returns Response
		Response response = lookUpConsumer.verifyListOfDemandFilterWithInvalidType(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);

	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter by
	 *         blank Type and Keyword Validation: Status code 500, Response
	 *         message
	 */

	@Test(groups = { "sanity", "verifyLookUpServiceByBlankTypeNBlankKeyword","P2","NA" })
	public void verifyLookUpServiceByBlankTypeNBlankKeyword() {

	    	Logging.log("Service Name: /generic-services/api/lookup/demand/filter/match"
				+ "\nDescription: Verifying Lookup service with Blank parameter and expecting failure response."
				+ "\nInput: Using Blank Keyword that not present in the system" + "\nExpected Output: Response status 400");
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByBlankTypeNBlankKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertTrue(response.getStatus()==400, "response code expected equal to 400 but found as:"+response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains("free text/type can't be blank"), "response is not correct");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter by
	 *         Primary skill Type and Keyword j Validation: Asserting Status
	 *         code 200, Response Message
	 */
	@Test(groups = { "sanity", "verifyLookupservicesByPrimarySkillTypeNKeyword","P1","NA" })
	public void verifyLookupservicesByPrimarySkillTypeNKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByPrimarySKillTypeNKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
		// Asserting Response Body
		Assertion.assertTrue(responseBody.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("lookup_Skill_keyword")), "primary skill is not available.");
		Logging.log("Primary skill is available.");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter by
	 *         blank Type and Keyword Validation: Status code 500, Response
	 *         message
	 */

	@Test(groups = { "sanity", "verifyLookUpServiceByBlankTypeNKeyword","P2","NA" })
	public void verifyLookUpServiceByBlankTypeNKeyword() {
	    	Logging.log("Service Name: generic-services/api/lookup/demand/filtermatch?keyword=ja"
			+ "\nDescription: Verifying Lookup service with Blank Type and Keyword parameter and expecting failure response."
			+ "\nInput: Using Blank Type and Keyword " + "\nExpected Output: Response status 404");
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByBlankTypeNKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertTrue(response.getStatus()==404, "response code expected equal to 404 but found as:"+response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains("HTTP Status 404"),"Expected 404 as status but actual is different");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter by
	 *         blank Type and Keyword Validation: Status code 500, Response
	 *         message
	 */

	@Test(groups = { "sanity", "verifyLookUpServiceByTypeNBlankKeyword","P2","NA" })
	public void verifyLookUpServiceByTypeNBlankKeyword() {
	    	Logging.log("Service Name: generic-services/api/lookup/demand/filtermatch?type=REQUISITION_STATUS"
			+ "\nDescription: Verifying Lookup service with Blank Type and Keyword parameter and expecting failure response."
			+ "\nInput: Using Blank Keyword " + "\nExpected Output: Response status 404");
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByTypeNBlankKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertTrue(response.getStatus()==404, "response code expected equal to 404 but found as: "+response.getStatus());
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains("HTTP Status 404"),"Expected 404 as status but actual is different");

	}
	
	/**
     * @author Pritisudha Pattanaik
     * Steps: Get list of demand filter by passing special characters
     * Validation: asserting status code
     */
	 @Test(groups={"sanity","verifylistofdemancharactersdfilterwithSpecialcharacters","NA"})
	    public void verifylistofdemandfilterwithSpecialcharacters()
	    {
		Logging.log("Service Name: generic-services/api/lookup/demand/filter?type=!%40%23%24%40"
			+ "\nDescription: Verifying Lookup service with Special Character as parameter and expecting Empty response."
			+ "\nInput: Special Characters " + "\nExpected Output: Empty Response");
	        lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
	        // Executes Get request and returns Response
	        Response response=lookUpConsumer.getListOfDemandFilterWithSpecialCharacter(hostName);
	        Logging.log("RESPONSE CODE >>" + response.getStatus());
	        // Asserting Response Code
	        //TODO: Expect 204 from service
	        Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull: Expected Response Code 200 but found :"+response.getStatus());
	        String responseBody = response.readEntity(String.class);
	        Assertion.assertTrue(responseBody.contains("\"response\":{}"), "Request Unsuccessful: Expected Empty Response but response was: " +responseBody);
	        
	    }
}