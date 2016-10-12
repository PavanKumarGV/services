package com.spire.lookUpResources;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
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
import com.spire.service.consumers.SearchResourcesConsumer;

public class LookUpResourcesTestPlan extends TestPlan {

	String hostName;
	LookUpResourcesConsumer lookUpConsumer = null;
	String userId;
	String password;
	
	public static String INVALID_VALUE = ReadingServiceEndPointsProperties.getServiceEndPoint("invalid_value");
	public static String LOOKUP_FILTER_REQUISITION_TYPE = ReadingServiceEndPointsProperties.getServiceEndPoint("lookup_filter_requisition_type");
	public static String LOOKUP_FILTER_REQUISITION_ENTITY_TYPE = ReadingServiceEndPointsProperties.getServiceEndPoint("lookup_filter_requisition_entity_type");
	public static String LOOKUP_FILTER_CANDIDATE_TYPE = ReadingServiceEndPointsProperties.getServiceEndPoint("lookup_filter_candidate_type");
	public static String LOOKUP_FILTER_CANDIDATE_ENTITY_TYPE = ReadingServiceEndPointsProperties.getServiceEndPoint("lookup_filter_candidate_entity_type");
	
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
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b> generic-services/api/lookup/filter
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of demand filter by type,entity type
	* </p>
	* <p>
	* <b>Input :</b> valid type & entityType
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
	* <p>
	* @author Jyoti
	* </p>
	*/  
	@Test(groups = { "sanity", "testGetLookupFilterByValidTypeNEntityTypeForRequisition_PositiveFunctional","P1","NA" })
	public void testGetLookupFilterByValidTypeNEntityTypeForRequisition_PositiveFunctional() {
		Logging.log("Service Name: generic-services/api/lookup/filter"
			+ "\nDescription: Get the list of demand filter by type,entity type"
			+ "\nInput: valid type & entityType \nExpected Output: Response status 200");

		// Get authentication token
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		
		// Executes GET request and returns Response
		Response responsebody = lookUpConsumer.getLookupFilterByTypeAndEntityType(hostName, LOOKUP_FILTER_REQUISITION_TYPE, LOOKUP_FILTER_REQUISITION_ENTITY_TYPE);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
		Assert.assertTrue(StringUtils.containsIgnoreCase(response,"Open"));
	}
	
      /**
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b> generic-services/api/lookup/filter
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of candidate filter by type,entity type
	* </p>
	* <p>
	* <b>Input :</b> valid type & entityType
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
	* <p>
	* @author Jyoti
	* </p>
	*/  
	@Test(groups = { "sanity", "testGetLookupFilterByValidTypeNEntityTypeForCandidate_PositiveFunctional","P1","NA" })
	public void testGetLookupFilterByValidTypeNEntityTypeForCandidate_PositiveFunctional() {
		Logging.log("Service Name: generic-services/api/lookup/filter"
	+ "\nDescription: Get the list of candidate filter by type,entity type"
	+ "\nInput: valid type & entityType \nExpected Output: Response status 200");

		// Get authentication token
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		
		// Executes GET request and returns Response
		Response responsebody = lookUpConsumer.getLookupFilterByTypeAndEntityType(hostName, LOOKUP_FILTER_CANDIDATE_TYPE, LOOKUP_FILTER_CANDIDATE_ENTITY_TYPE);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
		Assert.assertTrue(StringUtils.containsIgnoreCase(response,"Spire"));
	}																		
	
	/**
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b> generic-services/api/lookup/filter
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of demand or candidate filter by type,entity type
	* </p>
	* <p>
	* <b>Input :</b> invalid type & valid entityType
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 200
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/  
	@Test(groups = { "sanity", "testGetLookupFilterByInvalidType_NegativeFunctional","P1","NA" })
	public void testGetLookupFilterByInvalidType_NegativeFunctional() {
		Logging.log("Service Name: generic-services/api/lookup/filter"
			+ "\nDescription: Get the list of demand or candidate filter by type,entity type"
			+ "\nInput: invalid type & valid entityType \nExpected Output: Response status 200");

		// Get authentication token
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		
		// Executes GET request and returns Response
		Response responsebody = lookUpConsumer.getLookupFilterByTypeAndEntityType(hostName, INVALID_VALUE, LOOKUP_FILTER_REQUISITION_ENTITY_TYPE);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
		Assert.assertTrue(!StringUtils.containsIgnoreCase(response,"Open"));
	}
	
      /**
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b> generic-services/api/lookup/filter
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of demand or candidate filter by type,entity type
	* </p>
	* <p>
	* <b>Input :</b> valid type & invalid entityType
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 400
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/  
	@Test(groups = { "sanity", "testGetLookupFilterByInvalidEntityType_NegativeFunctional","P1","NA" })
	public void testGetLookupFilterByInvalidEntityType_NegativeFunctional() {
		Logging.log("Service Name: generic-services/api/lookup/filter"
			+ "\nDescription: Get the list of demand or candidate filter by type,entity type"
			+ "\nInput: invalid type & valid entityType \nExpected Output: Response status 400");

		// Get authentication token
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		
		// Executes GET request and returns Response
		Response responsebody = lookUpConsumer.getLookupFilterByTypeAndEntityType(hostName, LOOKUP_FILTER_REQUISITION_TYPE, INVALID_VALUE);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() == 400, "Response unsuccessful, Expected 400 status code");
		Assert.assertTrue(StringUtils.containsIgnoreCase(response,"Invalid Entity Type"));
	}
	
      /**
	* @throws IOException
	* @throws ClientProtocolException
	* <p>
	* <b>Target Service URL :</b> generic-services/api/lookup/filter
	* </p>
	* <p>
	* <b>Test Case Description :</b>
	* </p>
	* <p>
	* Get the list of demand or candidate filter by type,entity type
	* </p>
	* <p>
	* <b>Input :</b> invalid type & invalid entityType
	* </p>
	* <p>
	* <b>Expected Output :</b> Response status 400
	* </p>
	* <p>
	* <b>Category :</b> Negative - Functional Test Case
	* </p>
	* <p>
	* <b>Bug Level :</b><font color=#E6A001> P3</font>
	* </p>
	* <p>
	* @author Jyoti
	* </p>
	*/  
	@Test(groups = { "sanity", "testGetLookupFilterByInvalidTypeNEntityType_NegativeFunctional","P1","NA" })
	public void testGetLookupFilterByInvalidTypeNEntityType_NegativeFunctional() {
		Logging.log("Service Name: generic-services/api/lookup/filter"
			+ "\nDescription: Get the list of demand or candidate filter by type,entity type"
			+ "\nInput: invalid type & invalid entityType \nExpected Output: Response status 400");

		// Get authentication token
		lookUpConsumer = new LookUpResourcesConsumer(userId, password, hostName);
		
		// Executes GET request and returns Response
		Response responsebody = lookUpConsumer.getLookupFilterByTypeAndEntityType(hostName, INVALID_VALUE, INVALID_VALUE);
		String response = responsebody.readEntity(String.class);
		
		Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
		
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() == 400, "Response unsuccessful, Expected 400 status code");
		Assert.assertTrue(StringUtils.containsIgnoreCase(response,"Invalid Entity Type"));
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