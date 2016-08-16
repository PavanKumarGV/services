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

	/**
	 * Steps: GET list of demand filter by Type(REQUISITION_STATUS) Validation:
	 * Asserting Open Requisition Status in response body
	 */

	@Test(groups = { "sanity", "verifyLookupservices","P1" })
	public void verifyLookupservices() {
		lookUpConsumer = new LookUpResourcesConsumer();
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

	@Test(groups = { "sanity", "verifyLookupservicesByTypeNKeyword","P1" })
	public void verifyLookupservicesByTypeNKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByTypeNKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
		// Asserting Response Body
		Assertion.assertTrue(responseBody.contains("Joined"), "Joined demand filter is not available.");
		Logging.log("Joined demand filter is available.");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter with
	 *         Blank demand filter type Validation: Status code 500, Response
	 *         message
	 */
	@Test(groups = { "sanity", "getListOfDemandFilterWithBlankType","P2" })
	public void getListOfDemandFilterWithoutType() {
		lookUpConsumer = new LookUpResourcesConsumer();
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
	@Test(groups = { "sanity", "getListOfDemandFilterWithInvalidType","P2" })
	public void getListOfDemandFilterWithInvalidType() {
		lookUpConsumer = new LookUpResourcesConsumer();
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

	@Test(groups = { "sanity", "verifyLookUpServiceByBlankTypeNBlankKeyword","P2" })
	public void verifyLookUpServiceByBlankTypeNBlankKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByBlankTypeNBlankKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertTrue(response.getStatus()!=200, "response code expected not equal to 200 but found as:"+response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);

	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter by
	 *         Primary skill Type and Keyword j Validation: Asserting Status
	 *         code 200, Response Message
	 */
	@Test(groups = { "sanity", "verifyLookupservicesByPrimarySkillTypeNKeyword","P1" })
	public void verifyLookupservicesByPrimarySkillTypeNKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByPrimarySKillTypeNKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
		// Asserting Response Body
		Assertion.assertTrue(responseBody.contains("Java"), "Java primary skill is not available.");
		Logging.log("Java Primary skill is available.");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter by
	 *         blank Type and Keyword Validation: Status code 500, Response
	 *         message
	 */

	@Test(groups = { "sanity", "verifyLookUpServiceByBlankTypeNKeyword","P2" })
	public void verifyLookUpServiceByBlankTypeNKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByBlankTypeNKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertTrue(response.getStatus()!=200, "response code expected not equal to 200 but found as:"+response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);

	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter by
	 *         blank Type and Keyword Validation: Status code 500, Response
	 *         message
	 */

	@Test(groups = { "sanity", "verifyLookUpServiceByTypeNBlankKeyword","P2" })
	public void verifyLookUpServiceByTypeNBlankKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		// Executes Get request and returns Response
		Response response = lookUpConsumer.getListOfDemandFilterByTypeNBlankKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		// Asserting Response Code
		Assertion.assertTrue(response.getStatus()!=200, "response code expected not equal to 200 but found as:"+response.getStatus());
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);

	}
	
	/**
     * @author Pritisudha Pattanaik
     * Steps: Get list of demand filter by passing special characters
     * Validation: asserting status code
     */
    @Test(groups={"sanity","verifylistofdemandfilterwithSpecialcharacters"})
    public void verifylistofdemandfilterwithSpecialcharacters()
    {
        lookUpConsumer = new LookUpResourcesConsumer();
     // Executes Get request and returns Response
        Response response=lookUpConsumer.getListOfDemandFilterWithSpecialCharacter(hostName);
        Logging.log("RESPONSE CODE >>" + response.getStatus());
     // Asserting Response Code
        Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull: Expected Response Code 200 but found :"+response.getStatus());
        
    }
}