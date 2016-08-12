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

	@Test(groups = { "sanity", "verifyLookupservices" })
	public void verifyLookupservices() {
		lookUpConsumer = new LookUpResourcesConsumer();
		Response response = lookUpConsumer.getListOfDemandFilter(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
		Assertion.assertTrue(responseBody.contains("Open"), "Open demand filter is not available.");
		Logging.log("Open demand filter is available.");
	}

	/**
	 * Steps: GET list of demand filter by Type(REQUISITION_STATUS) and
	 * Keyword(o) Validation: Asserting Joined Requistion Status in response
	 * body
	 */

	@Test(groups = { "sanity", "verifyLookupservicesByTypeNKeyword" })
	public void verifyLookupservicesByTypeNKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		Response response = lookUpConsumer.getListOfDemandFilterByTypeNKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
		Assertion.assertTrue(responseBody.contains("Joined"), "Joined demand filter is not available.");
		Logging.log("Joined demand filter is available.");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter with
	 *         Blank demand filter type Validation: Status code 500, Response
	 *         message
	 */
	@Test(groups = { "sanity", "getListOfDemandFilterWithBlankType" })
	public void getListOfDemandFilterWithoutType() {
		lookUpConsumer = new LookUpResourcesConsumer();
		Response response = lookUpConsumer.verifyListOfDemandFilterWithoutType(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
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
	@Test(groups = { "sanity", "getListOfDemandFilterWithInvalidType" })
	public void getListOfDemandFilterWithInvalidType() {
		lookUpConsumer = new LookUpResourcesConsumer();
		Response response = lookUpConsumer.verifyListOfDemandFilterWithInvalidType(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
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

	@Test(groups = { "sanity", "verifyLookUpServiceByBlankTypeNBlankKeyword" })
	public void verifyLookUpServiceByBlankTypeNBlankKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		Response response = lookUpConsumer.getListOfDemandFilterByBlankTypeNBlankKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
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
	@Test(groups = { "sanity", "verifyLookupservicesByPrimarySkillTypeNKeyword" })
	public void verifyLookupservicesByPrimarySkillTypeNKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		Response response = lookUpConsumer.getListOfDemandFilterByPrimarySKillTypeNKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull");
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		System.out.println("RESPONSE CODE >>" + responseBody);
		Assertion.assertTrue(responseBody.contains("Java"), "Java primary skill is not available.");
		Logging.log("Java Primary skill is available.");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps: GET list of demand filter by
	 *         blank Type and Keyword Validation: Status code 500, Response
	 *         message
	 */

	@Test(groups = { "sanity", "verifyLookUpServiceByBlankTypeNKeyword" })
	public void verifyLookUpServiceByBlankTypeNKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		Response response = lookUpConsumer.getListOfDemandFilterByBlankTypeNKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
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

	@Test(groups = { "sanity", "verifyLookUpServiceByTypeNBlankKeyword" })
	public void verifyLookUpServiceByTypeNBlankKeyword() {
		lookUpConsumer = new LookUpResourcesConsumer();
		Response response = lookUpConsumer.getListOfDemandFilterByTypeNBlankKeyword(hostName);
		Logging.log("RESPONSE CODE >>" + response.getStatus());
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
        Response response=lookUpConsumer.getListOfDemandFilterWithSpecialCharacter(hostName);
        Logging.log("RESPONSE CODE >>" + response.getStatus());
        Assertion.assertEquals(response.getStatus(), 200, "Request Unsuccessfull: Expected Response Code 200 but found :"+response.getStatus());
        
    }
}