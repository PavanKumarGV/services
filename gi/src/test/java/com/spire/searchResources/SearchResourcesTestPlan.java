package com.spire.searchResources;

import org.testng.annotations.Test;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.JsonParser;
import com.spire.base.service.Constants;
import com.spire.base.service.ReadingServiceEndPointsProperties;
// import spire.talent.gi.beans.SavedSearchDetails;
import com.spire.base.service.utils.SavedSearchDetails;
import com.spire.base.service.utils.SearchUtil;

import spire.talent.gi.beans.SearchInput;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.service.consumers.SearchResourcesConsumer;

/**
 * @author jyoti
 *
 * @param <SearchCriteriaBean>
 */
/**
 * @author jyoti
 *
 * @param <SearchCriteriaBean>
 */
public class SearchResourcesTestPlan<SearchCriteriaBean> extends TestPlan {

    String		    hostName;
    String		    userId;
    String		    password;

    SearchResourcesConsumer candConsumer      = null;
    SearchInput		    SearchBeanRequest = null;
    static String	    Input	      = null;
    SearchUtil		    searchUtil	      = null;
    static String	    savedSearchId     = null;

    /**
     * Passing HostName,UserName and Password from the xml.
     */

    @BeforeTest(alwaysRun = true)
    public void setUp() {
	hostName = (String) ContextManager.getThreadContext().getHostAddress();
	userId = ReadingServiceEndPointsProperties.getServiceEndPoint("user_Id");
	password = ReadingServiceEndPointsProperties.getServiceEndPoint("password");
	// userId = (String) ContextManager.getThreadContext().getUserid();
	// password = (String) ContextManager.getThreadContext().getPassword();
	Logging.log("Start :: Login with Username: " + userId + "Password: " + password + "and hostName: " + hostName);

    }

    /**
     * Vasista - Get -Similler profiles
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "GetSimillerProfiles", "NA" })
    public void GetSimillerProfiles() throws ClientProtocolException, IOException {

	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// suggestConsumer.getSemilarProfiles(hostName);
	Response responsebody = suggestConsumer.getSemilarProfiles(hostName);
	Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE ******" + response);
	Assert.assertTrue(response.contains("id"));
	Logging.log("Response: " + response);
    }

    /**
     * Bhagyasree - Get suggestion when passing keyword
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/

    @Test(groups = { "sanity", "verifySuggestRequest", "NA" })
    public void verifySuggestRequest() throws ClientProtocolException, IOException {
	SearchResourcesConsumer suggestConsumer = null;
	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes Get request and returns Response
	Response responsebody = suggestConsumer.getSuggest(hostName);
	Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected status 200");
	Logging.log("Response successful");
	Logging.log(" RESPONSE BODY>>" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	System.out.println(" RESPONSE>>" + response);
	Logging.log(" RESPONSE CODE>>" + response);
	// Asserting Response Code
	Assert.assertTrue(response.contains("java"));

    }

    /**
     * Bhagyasree - Get error code when keyword is blank
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/_suggest
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Get error code when keyword is blank and expecting failure
     *             response.
     *             </p>
     *             <p>
     *             <b>Input :</b> blank
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 400
     *             </p>
     *             <p>
     *             <b>Category :</b> Negative - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#E6A001> P3</font>
     *             </p>
     */
    @Test(groups = { "sanity", "verifySuggestValidation", "NA" })
    public void verifySuggestValidation() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/_suggest"
		+ "\nDescription: Get error code when keyword is blank and expecting failure response."
		+ "\nInput: keyword is blank " + "\nExpected Output: Response status 400");

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes Get request and returns Response
	Response responsebody = suggestConsumer.suggestValidation(hostName);
	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertTrue(responsebody.getStatus() == 400,
		"response code expected not equal to 400 but found as:" + responsebody.getStatus());
	Assert.assertTrue(response.contains("Keyword cannot be null or empty"), "Keyword can be null or empty");
    }

    /**
     * Author - Bhagyasree Test case description - Get suggestion when passing
     * keyword having multiple words(Like project planning, project management)
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "verifySuggestForSkillwithMultipleWords", "NA" })
    public void verifySuggestForSkillwithMultipleWords() throws ClientProtocolException, IOException {
	SearchResourcesConsumer suggestConsumer = null;
	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes Get request and returns Response
	Response responsebody = suggestConsumer.getSuggestForSkillwithMultipleWords(hostName);
	Assertion.assertTrue(responsebody.getStatus() == 200,
		"response code expected equal to 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE ******" + response);
	// Asserting Response Code
	Assert.assertTrue(response.contains(
		ReadingServiceEndPointsProperties.getServiceEndPoint("suggest_Multiple_words").replace("%20", " ")));

    }

    /**
     * Author - Bhagyasree Test case description - Get suggestion when passing
     * keyword having SpecialCharacters(Like C#, .Net, C++)
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/_suggest?keyword=.net
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Get suggestion when passing keyword having
     *             SpecialCharacters(Like C#, .Net, C++) and expecting success
     *             response.
     *             </p>
     *             <p>
     *             <b>Input :</b> .net
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200
     *             </p>
     *             <p>
     *             <b>Category :</b> Positive - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#C90000> P1</font>
     *             </p>
     */
    @Test(groups = { "sanity", "verifySuggestForSkillwithSpecialCharacter", "NA" })
    public void verifySuggestForSkillwithSpecialCharacter() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/_suggest?keyword=.net"
		+ "\nDescription: Get suggestion when passing keyword having SpecialCharacters(Like C#, .Net, C++) and expecting success response."
		+ "\nInput: .net " + "\nExpected Output: Response status 200");

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes Get request and returns Response
	Response responsebody = suggestConsumer.getSuggestForSkillwithSpecialCharacter(hostName);
	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertTrue(responsebody.getStatus() == 200,
		"response code expected equal to 200 but found as:" + responsebody.getStatus());
	Assert.assertTrue(response.contains(".net"), "Response doesnot contain .net as skill");
    }

    /**
     * Vasista - Get -Similler profiles
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "GetSimilarProfilesNegative", "NA" })
    public void GetSimilarProfilesNegative() throws ClientProtocolException, IOException {

	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getSemilarProfilesNegi(hostName);
	Assertion.assertTrue(responsebody.getStatus() == 200,
		"response code expected not equal to 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log("Response: " + response);
	// Response responsebody = suggestConsumer.getSemilarProfiles(hostName);
	// String response = responsebody.readEntity(String.class);
	// System.out.println("***** RESPONSE ******" + response);
	// Assert.assertTrue(response.contains(" "));

    }

    /**
     *
     * Author - Bhagyasree Test case description - Search candidates for skill
     * search Updated by Jyoti
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/_candidates
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Verifying 'Get Candidates by Savedsearch' Service as per
     *             given skills as correct parameter and expecting success
     *             response.
     *             </p>
     *             <p>
     *             <b>Input :</b> (skill:MySQL and skill:java)
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200
     *             </p>
     *             <p>
     *             <b>Category :</b> Positive - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#C90000> P1</font>
     *             </p>
     */
    @Test(groups = { "sanity", "searchCandidatesWithSkill", "NA" })
    public void searchCandidatesWithSkill() throws ClientProtocolException, IOException {
	// Get input bean
	com.spire.base.service.utils.SearchInputRequest inputBean = SearchUtil.getSearchInputBeanWithSkill();
	Logging.log("inputBean " + inputBean);

	Logging.log("Service Name: /generic-services/api/search/_candidates"
		+ "\nDescription: Verifying 'Get Candidates by Savedsearch' Service as per given skills as correct parameter and expecting success response."
		+ "\nInput: " + inputBean + "\nExpected Output: Response status 200");

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.searchCandidate(inputBean, hostName);
	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(
		StringUtils.containsIgnoreCase(response, "MySQL") || StringUtils.containsIgnoreCase(response, "java"),
		"Response does not contain MySQL or java as skill");
    }

    /**
     * Updated by Jyoti
     * <p>
     * <b>Target Service URL :</b> /generic-services/api/search/_candidates
     * </p>
     * <p>
     * <b>Test Case Description :</b>
     * </p>
     * <p>
     * Verifying 'Get Candidates by Savedsearch' Service as per given skills and
     * locations as correct parameters and expecting success response.
     * </p>
     * <p>
     * <b>Input :</b> (skill:java and skill:MySQL) AND (location:Bangalore AND
     * location:Mumbai)
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
    @Test(groups = { "sanity", "searchCandidatesWithSkillAndLocation", "NA" })
    public void searchCandidatesWithSkillAndLocation() throws ClientProtocolException, IOException {
	com.spire.base.service.utils.SearchInputRequest inputBean = SearchUtil.getSearchInputBeanWithSkillAndLocation();
	Logging.log("inputBean " + inputBean);

	Logging.log("Service Name: /generic-services/api/search/_candidates"
		+ "\nDescription: Verifying 'Get Candidates by Savedsearch' Service as per given skills as correct parameter and expecting success response."
		+ "\nInput: " + inputBean + "\nExpected Output: Response status 200");

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.searchCandidate(inputBean, hostName);

	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(
		StringUtils.containsIgnoreCase(response, "MySQL") && StringUtils.containsIgnoreCase(response, "java")
			&& StringUtils.containsIgnoreCase(response, "Bangalore")
			&& StringUtils.containsIgnoreCase(response, "Mumbai"),
		"Response does not contain MySQL/java/Bangalore/Mumbai");
    }

    /**
     * Bhagyasree - Get list of saved search
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "getSavedSearch", "NA" })
    public void getSavedSearch() throws ClientProtocolException, IOException {

	SearchResourcesConsumer suggestConsumer = null;
	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	suggestConsumer.getSavedSearch(hostName);
	// Executes get request and returns Response
	Response responsebody = suggestConsumer.getSavedSearch(hostName);
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE ******" + response);

    }

    /**
     * Bhagyasree - Get particular saved search by ID that exist
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "getSavedSearchById" })
    public void getSavedSearchById() throws ClientProtocolException, IOException {

	SearchResourcesConsumer suggestConsumer = null;
	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes get request and returns Response
	suggestConsumer.getSavedSearchById(hostName);
	Response responsebody = suggestConsumer.getSavedSearchById(hostName);
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE ******" + response);

    }

    /**
     * Bhagyasree - Get particular saved search by ID that doesnt not exist
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/{nonExistingId}
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Verifying saved search get service with incorrect parameter
     *             and expecting failure response.
     *             </p>
     *             <p>
     *             <b>Input :</b> Using Id that not present in the system -
     *             12345678
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 500
     *             </p>
     *             <p>
     *             <b>Category :</b> Negative - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#E6A001> P3</font>
     *             </p>
     */
    @Test(groups = { "sanity", "getSavedSearchByNonExistingId" })
    public void getSavedSearchByNonExistingId() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/save_search/12345678"
		+ "\nDescription: Verifying candidates notes search service with incorrect parameter and expecting failure response."
		+ "\nInput: Using Id that not present in the system" + "\nExpected Output: Response status 500");

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	suggestConsumer.getSavedSearchByNonExistingId(hostName);

	// Executes get request and returns Response
	Response responsebody = suggestConsumer.getSavedSearchByNonExistingId(hostName);
	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertTrue(responsebody.getStatus() == 500, "Response not successful");
	Assertion.assertTrue(response.contains("Invalid Savedsearch Id"),
		"getSavedSearchById found a non existent id : 12345678");
    }

    /**
     * priti- Get autocomplete with full skill
     */
    @Test(groups = { "sanity", "getautocompletewithfullskillRequest", "NA" })
    public void getautocompletewithfullskillRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletefullskillsearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.skill), "skill not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "full_skill_to_search")), "full skill not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with partial skill
     */
    @Test(groups = { "sanity", "getautocompletewithpartialskillRequest", "NA" })
    public void getautocompletewithpartialskillRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletepartialskillsearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.skill), "skill not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "partial_Skill_to_search")),
	 * "partial skill search not found in response");
	 */
    }

    /**
     * priti- Get autocomplete with full institute
     */

    @Test(groups = { "sanity", "getautocompletewithfullinstituteRequest", "NA" })
    public void getautocompletewithfullinstituteRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletefullinstitutesearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.institute), "institute not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "full_institute_to_search")),
	 * "full institute not found in response");
	 */
    }

    /**
     * priti- Get autocomplete with partial institute
     */

    @Test(groups = { "sanity", "getautocompletewithpartialinstituteRequest", "NA" })
    public void getautocompletewithpartialinstituteRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletepartialinstitutesearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.institute), "institute not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "partial_institute_to_search")),
	 * "partial institute not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with full education
     */

    @Test(groups = { "sanity", "getautocompletewithfulleducationRequest", "NA" })
    public void getautocompletewithfulleducationRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletefulleducationsearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.education), "education not found in the response.");
	/*
	 * Assertion.assertTrue(
	 * response.contains("Diploma in Computer Applications"),
	 * "full education not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with partial education
     */

    @Test(groups = { "sanity", "getautocompletewithpartialeducationRequest", "NA" })
    public void getautocompletewithpartialeducationRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletepartialeducationsearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.education), "education not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains("Diploma"),
	 * "partial education not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with full employer
     */

    @Test(groups = { "sanity", "getautocompletewithfullemployerRequest", "NA" })
    public void getautocompletewithfullemployerRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletefullemployersearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.employer), "employer not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "full_employer_to_search")), "full employer not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with partial employer
     */

    @Test(groups = { "sanity", "getautocompletepartialemployerRequest", "NA" })
    public void getautocompletepartialemployerRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletepartialemployersearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.employer), "employer not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "partial_employer_to_search")),
	 * "partial employer not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with full location
     */
    @Test(groups = { "sanity", "getautocompletfulllocationRequest", "NA" })
    public void getautocompletfulllocationRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletefulllocationsearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.location), "location not found in the response.");
	Assertion.assertTrue(
		response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("full_location_to_search")),
		"full location not found in response");
    }

    /**
     * priti-Get autocomplete with partial location
     */
    @Test(groups = { "sanity", "getautocompletpartiallocationRequest", "NA" })
    public void getautocompletpartiallocationRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletepartiallocationsearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.location), "location not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "partial_location_to_search")),
	 * "partial location not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with full sourcetype
     */
    @Test(groups = { "sanity", "getautocompletfullsourcetypeRequest", "NA" })
    public void getautocompletfullsourcetypeRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletefullsourcetypesearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.sourcetype), "sourcetype not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "full_sourcetype_to_search")),
	 * "full sourcetype not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with partial sourcetype
     */
    @Test(groups = { "sanity", "getautocompletpartialsourcetypeRequest", "NA" })
    public void getautocompletpartialsourcetypeRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletepartialsourcetypesearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.sourcetype), "sourcetype not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "partial_sourcetype_to_search")),
	 * "partial sourcetype not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with full sourcename
     */
    @Test(groups = { "sanity", "getautocompletfullsourcenameRequest", "NA" })
    public void getautocompletfullsourcenameRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletefullsourcenamesearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.sourcename), "sourcename not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "full_sourcename_to_search")),
	 * "full sourcename not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with partial sourcename
     */
    @Test(groups = { "sanity", "getautocompletpartialsourcenameRequest", "NA" })
    public void getautocompletpartialsourcenameRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletepartialsourcenamesearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.sourcename), "sourcename not found in the response.");
	// Assertion.assertTrue(response.contains("N"),
	// "partial sourcename not found in response");
    }

    /**
     * priti-Get autocomplete with full status
     */
    @Test(groups = { "sanity", "getautocompletfullstatusRequest", "NA" })
    public void getautocompletfullstatusRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletefullstatussearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.status), "status not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "full_status_to_search")), "full status not found in response");
	 */
    }

    /**
     * priti-Get autocomplete with partial status
     */
    @Test(groups = { "sanity", "getautocompletpartialstatusRequest", "NA" })
    public void getautocompletpartialstatusRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletepartialstatussearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.status), "status not found in the response.");
	/*
	 * Assertion.assertTrue(response.contains(
	 * ReadingServiceEndPointsProperties.getServiceEndPoint(
	 * "partial_status_to_search")),
	 * "partial status not found in response");
	 */
    }

    /**
     * priti-Get autocomplete without keyword
     */

    @Test(groups = { "sanity", "getautocompletewithoutkeywordRequest", "NA" })
    public void getautocompletewithoutkeywordRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletewithoutkey(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.skill), "skill not found in the response.");

    }

    /**
     * priti-Get autocomplete without type
     */

    @Test(groups = { "sanity", "getautocompletewithouttypeRequest", "NA" })
    public void getautocompletewithouttypeRequest() {
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletewithouttype(hostName);
	// Assertion.assertEquals(200, responsebody.getStatus(),
	// "Response expected 200 but found as:"+responsebody.getStatus());
	Assertion.assertTrue(responsebody.getStatus() != 200, "response is unsuccessfull");
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains("Search input cannot be null or empty"),
		"Search input cannot be null or empty not found in the response.");
	// Assertion.assertTrue(response.contains("ja"),
	// "full institute not found in response");
    }

    /**
     * 12.08.2016 Bhagyasree - Get list of saved search based on sort by
     * Modified On Ascending
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/list?sortBy=modifiedOn&orderBy=asc
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Verifying saved search list service with correct parameter
     *             and expecting success response.
     *             </p>
     *             <p>
     *             <b>Input :</b> Using sortBy & orderBy
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200
     *             </p>
     *             <p>
     *             <b>Category :</b> Positive - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#C90000> P1</font>
     *             </p>
     */
    @Test(groups = { "sanity", "listSavedSearchWithSortByModifiedOnAsc", "NA" })
    public void listSavedSearchWithSortByModifiedOnAsc() throws ClientProtocolException, IOException {

	Logging.log("Service Name: /generic-services/api/search/save_search/list?sortBy=createdOn&orderBy=asc"
		+ "\nDescription: Verifying saved search list service with correct parameter and expecting success response."
		+ "\nInput: Using sortBy & orderBy " + "\nExpected Output: Response status 200");

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes get request and returns Response
	Response responsebody = suggestConsumer.listSavedSearchWithSortByModifiedOnAsc(hostName);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");

	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// comparing modifiedOn dates if all are in ascending order
	JSONObject obj = new JSONObject(response);
	JSONArray savedSearchDetails = obj.getJSONObject("response").getJSONArray("savedSearchDetails");
	final int n = savedSearchDetails.length();
	if (n >= 2) {
	    long[] modifiedOnArr = new long[n];
	    for (int i = 0; i < n; i++) {
		JSONObject savedSearchDetail = savedSearchDetails.getJSONObject(i);
		modifiedOnArr[i] = savedSearchDetail.getLong("modifiedOn");
	    }
	    Assertion.assertTrue(isSortedInAscOrder(modifiedOnArr), "modifiedOn is not sorted in ascending order");
	}
    }

    /**
     * 12.08.2016 Bhagyasree - Get list of saved search based on sort by
     * Modified On Descending
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/list?sortBy=modifiedOn&orderBy=dsc
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Verifying saved search list service with correct parameter
     *             and expecting success response.
     *             </p>
     *             <p>
     *             <b>Input :</b> Using sortBy & orderBy
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200
     *             </p>
     *             <p>
     *             <b>Category :</b> Positive - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#C90000> P1</font>
     *             </p>
     */
    @Test(groups = { "sanity", "listSavedSearchWithSortByModifiedOnDsc", "NA" })
    public void listSavedSearchWithSortByModifiedOnDsc() throws ClientProtocolException, IOException {

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes get request and returns Response
	Response responsebody = suggestConsumer.listSavedSearchWithSortByModifiedOnDsc(hostName);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");

	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// comparing modifiedOn dates if all are in descending order
	JSONObject obj = new JSONObject(response);
	JSONArray savedSearchDetails = obj.getJSONObject("response").getJSONArray("savedSearchDetails");
	final int n = savedSearchDetails.length();
	if (n >= 2) {
	    long[] modifiedOnArr = new long[n];
	    for (int i = 0; i < n; i++) {
		JSONObject savedSearchDetail = savedSearchDetails.getJSONObject(i);
		modifiedOnArr[i] = savedSearchDetail.getLong("modifiedOn");
	    }
	    Assertion.assertTrue(isSortedInDscOrder(modifiedOnArr), "modifiedOn is not sorted in descending order");
	}
    }

    /**
     * 12.08.2016 Bhagyasree - Get list of saved search based on sort by
     * CreatedOn Ascending
     * 
     * @throws IOException
     * @throws ClientProtocolException
     * 
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/list?sortBy=createdOn&orderBy=asc
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Verifying saved search list service with correct parameter
     *             and expecting success response.
     *             </p>
     *             <p>
     *             <b>Input :</b> Using sortBy & orderBy
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200
     *             </p>
     *             <p>
     *             <b>Category :</b> Positive - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#C90000> P1</font>
     *             </p>
     */
    @Test(groups = { "sanity", "listSavedSearchWithSortByCreatedOnAsc", "NA" })
    public void listSavedSearchWithSortByCreatedOnAsc() throws ClientProtocolException, IOException {

	Logging.log("Service Name: /generic-services/api/search/save_search/list?sortBy=createdOn&orderBy=asc"
		+ "\nDescription: Verifying saved search list service with correct parameter and expecting success response."
		+ "\nInput: Using sortBy & orderBy " + "\nExpected Output: Response status 200");

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes get request and returns Response
	Response responsebody = suggestConsumer.listSavedSearchWithSortByCreatedOnAsc(hostName);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");

	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// comparing createdOn dates if all are in ascending order
	JSONObject obj = new JSONObject(response);
	JSONArray savedSearchDetails = obj.getJSONObject("response").getJSONArray("savedSearchDetails");
	final int n = savedSearchDetails.length();
	if (n >= 2) {
	    long[] createdOnArr = new long[n];
	    for (int i = 0; i < n; i++) {
		JSONObject savedSearchDetail = savedSearchDetails.getJSONObject(i);
		createdOnArr[i] = savedSearchDetail.getLong("createdOn");
	    }
	    Assertion.assertTrue(isSortedInAscOrder(createdOnArr), "createdOn is not sorted in ascending order");
	}
    }

    private boolean isSortedInAscOrder(long[] data) {
	for (int i = 0; i < data.length - 1; i++) {
	    if (data[i] > data[i + 1]) {
		return false;
	    }
	}
	return true;
    }

    private boolean isSortedInDscOrder(long[] data) {
	for (int i = 0; i < data.length - 1; i++) {
	    if (data[i] < data[i + 1]) {
		return false;
	    }
	}
	return true;
    }

    /**
     * 12.08.2016 Bhagyasree - Get list of saved search based on sort by
     * CreatedOn Descending
     * 
     * @throws IOException
     * @throws ClientProtocolException
     * 
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/list?sortBy=createdOn&orderBy=dsc
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Verifying saved search list service with correct parameter
     *             and expecting success response.
     *             </p>
     *             <p>
     *             <b>Input :</b> Using sortBy & orderBy
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200
     *             </p>
     *             <p>
     *             <b>Category :</b> Positive - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#C90000> P1</font>
     *             </p>
     */
    @Test(groups = { "sanity", "listSavedSearchWithSortByCreatedOnDsc", "NA" })
    public void listSavedSearchWithSortByCreatedOnDsc() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/save_search/list?sortBy=createdOn&orderBy=dsc"
		+ "\nDescription: Verifying saved search list service with correct parameter and expecting success response."
		+ "\nInput: Using sortBy & orderBy " + "\nExpected Output: Response status 200");

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes get request and returns Response
	Response responsebody = suggestConsumer.listSavedSearchWithSortByCreatedOnDsc(hostName);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");

	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// comparing createdOn dates if all are in descending order
	JSONObject obj = new JSONObject(response);
	JSONArray savedSearchDetails = obj.getJSONObject("response").getJSONArray("savedSearchDetails");
	final int n = savedSearchDetails.length();
	if (n >= 2) {
	    long[] createdOnArr = new long[n];
	    for (int i = 0; i < n; i++) {
		JSONObject savedSearchDetail = savedSearchDetails.getJSONObject(i);
		createdOnArr[i] = savedSearchDetail.getLong("createdOn");
	    }
	    Assertion.assertTrue(isSortedInDscOrder(createdOnArr), "createdOn is not sorted in descending order");
	}
    }

    /**
     * Bhagyasree - Get suggestion when passing Invalid keyword
     * 
     * @throws IOException
     * @throws ClientProtocolException
     * 
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/_suggest?keyword=zxcvbnm
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Get suggestion when passing invalid keyword and expecting
     *             empty response.
     *             </p>
     *             <p>
     *             <b>Input :</b> zxcvbnm
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200 with empty
     *             values
     *             </p>
     *             <p>
     *             <b>Category :</b> Negative - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#E6A001> P3</font>
     *             </p>
     */
    @Test(groups = { "sanity", "verifySuggestRequestForInvalidKeyword", "NA" })
    public void verifySuggestRequestForInvalidKeyword() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/_suggest?keyword=zxcvbnm"
		+ "\nDescription: Get suggestion when passing invalid keyword and expecting empty response."
		+ "\nInput: zxcvbnm" + "\nExpected Output: Response status 200 with empty values");

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes get request and returns Response
	Response responsebody = suggestConsumer.getSuggestForInvalidKeyword(hostName);
	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertTrue(responsebody.getStatus() == 200,
		"response code expected equal to 200 but found as:" + responsebody.getStatus());
	Assert.assertFalse(response.contains("zxcvbnm"), "Response contains zxcvbnm as keyword");
    }

    /**
     * Bhagyasree - Get particular existing saved search by ID followed by space
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/{existingId}%20
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Get particular existing saved search by ID followed by space
     *             and expecting success response.
     *             </p>
     *             <p>
     *             <b>Input :</b> {existingId}%20
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200
     *             </p>
     *             <p>
     *             <b>Category :</b> Positive - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#C90000> P1</font>
     *             </p>
     */
    @Test(groups = { "sanity", "getSavedSearchByIdWithSpace", "NA" })
    public void getSavedSearchByIdWithSpace() throws ClientProtocolException, IOException {
	System.out.println("Create Saved Search with skill before getting ");
	Logging.log("Create Saved Search with skill before getting");

	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkill();
	Logging.log("inputBean " + inputBean);

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.createSavedSearchWithSkill(inputBean, hostName);

	System.out.println("***** RESPONSE : responsebody : ******" + responsebody);
	Logging.log("response " + responsebody.getStatus());

	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : response : ******" + response);
	Logging.log("response " + response);

	JSONObject obj = new JSONObject(response);
	JSONObject res = obj.getJSONObject("response");
	String id = res.getString("id");

	Logging.log("Service Name: /generic-services/api/search/save_search/" + id + "%20"
		+ "\nDescription: Get particular existing saved search by ID followed by space and expecting success response."
		+ "\nInput: " + id + "%20 " + "\nExpected Output: Response status 200");

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes get request and returns Response
	Response getresponsebody = suggestConsumer.getSavedSearchByIdWithSpace(hostName, id);
	String getresponse = getresponsebody.readEntity(String.class);

	System.out.println("***** GET RESPONSE CODE ******" + getresponsebody.getStatus()
		+ "\n***** GET RESPONSE ******" + getresponse);
	Logging.log("***** GET RESPONSE CODE ******" + getresponsebody.getStatus() + "\n***** GET RESPONSE ******"
		+ getresponse);

	// Asserting Response Code
	Assertion.assertEquals(getresponsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(getresponse.contains(id),
		"getSavedSearchById service could not find savedSearch with id : " + id);
    }

    /**
     * Bhagyasree - Create saved search with skill
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "createSavedSearchWithSkill", "NA" })
    public void createSavedSearchWithSkill() throws ClientProtocolException, IOException {

	System.out.println("Create Saved Search with skill");
	Logging.log("Create Saved Search with skill");
	searchUtil = new SearchUtil();
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkill();
	Logging.log("inputBean " + inputBean);
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.createSavedSearchWithSkill(inputBean, hostName);
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	System.out.println("***** RESPONSE : responsebody : ******" + responsebody);
	Logging.log("response " + responsebody.getStatus());

	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : response : ******" + response);
	Logging.log("response " + response);

    }

    /**
     * Bhagyasree - Create saved search with skill and location
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "createSavedSearchInputBeanWithSkillAndLocation", "NA" })
    public void createSavedSearchInputBeanWithSkillAndLocation() throws ClientProtocolException, IOException {

	System.out.println("Create Saved Search with skill and location");
	Logging.log("Create Saved Search with skill and location");
	searchUtil = new SearchUtil();
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkillAndLocation();
	Logging.log("inputBean " + inputBean);
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.createSavedSearchInputBeanWithSkillAndLocation(inputBean, hostName);
	System.out.println("***** RESPONSE : responsebody : ******" + responsebody);
	Logging.log("responsebody " + responsebody);

	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : response : ******" + response);
	Logging.log("response " + response);
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
    }

    /**
     * Bhagyasree - Create a public saved search with skill
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "createPublicSavedSearchWithSkill", "NA" })
    public void createPublicSavedSearchWithSkill() throws ClientProtocolException, IOException {

	System.out.println("Create  a public Saved Search with skill");
	Logging.log("Create  a public Saved Search with skill");
	searchUtil = new SearchUtil();
	// Executes get request and returns Response
	SavedSearchDetails inputBean = SearchUtil.createPublicSavedSearchInputBeanWithSkill();
	Logging.log("inputBean " + inputBean);
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.createPublicSavedSearchWithSkill(inputBean, hostName);
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	System.out.println("***** RESPONSE : responsebody : ******" + responsebody);
	Logging.log("responsebody " + responsebody);

	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : response : ******" + response);
	Logging.log("response " + response);
	savedSearchId = suggestConsumer.getIdFromResponse(response);

    }

    /**
     * Bhagyasree - delete particular saved search by ID that exist
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "deleteSavedSearchById", "NA" })
    public void deleteSavedSearchById() throws ClientProtocolException, IOException {
	System.out.println("Delete particular saved search by ID that exist");
	Logging.log("Delete particular saved search by ID that exist");

	/* starts - create saved search before deleting */
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkill();
	Logging.log("inputBean " + inputBean);
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes POST request and returns Response
	Response createResponsebody = suggestConsumer.createSavedSearchWithSkill(inputBean, hostName);
	System.out.println("***** RESPONSE : createResponsebody : ******" + createResponsebody);
	Logging.log("createResponsebody " + createResponsebody);

	String createResponse = createResponsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : createResponse : ******" + createResponse);
	Logging.log("createResponse " + createResponse);

	JSONObject createResponseJson = new JSONObject(createResponse);
	System.out.println("***** RESPONSE : createResponseJson : ******" + createResponseJson);
	JSONObject responseJson = createResponseJson.getJSONObject("response");
	String id = responseJson.getString("id");
	System.out.println("***** RESPONSE : id : ******" + id);
	Logging.log("id " + id);

	/* ends - create saved search before deleting */

	// Executes DELETE request and returns Response
	/* starts - delete saved search */
	Response responsebody = suggestConsumer.deleteSavedSearchById(hostName, id);
	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE ******" + response);
	Logging.log("response " + response);
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	/* ends - delete saved search */
    }

    /**
     * Bhagyasree - delete particular saved search by ID that doesnt exist
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "deleteSavedSearchByIdNonExisting", "NA" })
    public void deleteSavedSearchByIdNonExisting() throws ClientProtocolException, IOException {
	System.out.println("Delete particular saved search by ID that doesnt exist");
	Logging.log("Delete particular saved search by ID that doesnt exist");
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes DELETE request and returns Response
	Response responsebody = suggestConsumer.deleteSavedSearchByIdNonExisting(hostName);
	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE ******" + response);
	Logging.log("response " + response);
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 500, "Deleted Successfully");

    }

    @Test(groups = { "sanity", "searchCandidatesWithNoSearchQueryString", "NA" })
    public void searchCandidatesWithNoSearchQueryString() throws ClientProtocolException, IOException {

	System.out.println("Search candidates with no query string");
	Logging.log("Search candidates with no query string");
	searchUtil = new SearchUtil();
	com.spire.base.service.utils.SearchInputRequest inputBean = SearchUtil
		.searchCandidatesWithNoSearchQueryString();
	Logging.log("inputBean " + inputBean);
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes GET request and returns Response
	Response responsebody = suggestConsumer.searchCandidate(inputBean, hostName);
	System.out.println("***** RESPONSE : responsebody : ******" + responsebody);
	Logging.log("responsebody " + responsebody);

	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : response : ******" + response);
	Logging.log("response " + response);
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");

    }

    /**
     * Bhagyasree - update particular saved search by ID that exist
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Update particular saved search by ID that exist and expecting
     *             success response.
     *             </p>
     *             <p>
     *             <b>Input :</b> savedSearch Object
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200
     *             </p>
     *             <p>
     *             <b>Category :</b> Positive - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#C90000> P1</font>
     *             </p>
     */
    @Test(groups = { "sanity", "updateSavedSearchById", "NA" })
    public void updateSavedSearchById() throws ClientProtocolException, IOException {
	System.out.println("Update particular saved search by ID that exist");
	Logging.log("Update particular saved search by ID that exist");

	/* starts - create saved search before updating */
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkill();
	Logging.log("inputBean " + inputBean);

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	Response createResponsebody = suggestConsumer.createSavedSearchWithSkill(inputBean, hostName);
	System.out.println("***** RESPONSE : createResponsebody : ******" + createResponsebody);
	Logging.log("createResponsebody " + createResponsebody);

	String createResponse = createResponsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : createResponse : ******" + createResponse);
	Logging.log("createResponse " + createResponse);

	JSONObject createResponseJson = new JSONObject(createResponse);
	System.out.println("***** RESPONSE : createResponseJson : ******" + createResponseJson);
	JSONObject responseJson = createResponseJson.getJSONObject("response");
	String id = responseJson.getString("id");
	System.out.println("***** RESPONSE : id : ******" + id);
	Logging.log("id " + id);
	/* ends - create saved search before updating */

	// Executes PUT request and returns Response
	/* starts - update saved search */
	inputBean = SearchUtil.updateSavedSearchInputBeanWithSkill(inputBean);

	Logging.log("Service Name: /generic-services/api/search/save_search"
		+ "\nDescription: Update particular saved search by ID that exist" + "\nInput: " + inputBean
		+ "\nExpected Output: Response status 200");

	Response updateteResponsebody = suggestConsumer.updateSavedSearchWithSkill(inputBean, hostName, id);
	System.out.println("***** RESPONSE : updateteResponsebody : ******" + updateteResponsebody);
	Logging.log("updateteResponsebody " + updateteResponsebody);

	String updateResponse = updateteResponsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : updateResponse : ******" + updateResponse);
	Logging.log("updateResponse " + updateResponse);

	// Asserting Response Code
	Assertion.assertEquals(updateteResponsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(updateResponse.contains("Savedsearch updated successfully"), "Savedsearch update failed");
	/* ends - update saved search */
    }

    /**
     * Bhagyasree - update particular saved search by ID that doesnt exist
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/{InvalidSavedSearchId}
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Update particular saved search by ID that does not exist and
     *             expecting failure response.
     *             </p>
     *             <p>
     *             <b>Input :</b> InvalidSavedSearchId & savedSearch Object
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 500
     *             </p>
     *             <p>
     *             <b>Category :</b> Negative - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#E6A001> P3</font>
     *             </p>
     */
    @Test(groups = { "sanity", "updateSavedSearchByIdNonExisting", "NA" })
    public void updateSavedSearchByIdNonExisting() throws ClientProtocolException, IOException {
	System.out.println("Update particular saved search by ID that doesnt exist");
	Logging.log("Update particular saved search by ID that doesnt exist");

	/* starts - create saved search before updating */
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkill();
	Logging.log("inputBean " + inputBean);

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes PUT request and returns Response
	String id = "2233445500";

	/* starts - update saved search */
	inputBean = SearchUtil.updateSavedSearchInputBeanWithSkill(inputBean);

	Logging.log("Service Name: /generic-services/api/search/save_search/2233445500"
		+ "\nDescription: Update particular saved search by ID that does not exist" + "\nInput: " + inputBean
		+ "\nExpected Output: Response status 500");

	Response updateteResponsebody = suggestConsumer.updateSavedSearchWithSkill(inputBean, hostName, id);
	System.out.println("***** RESPONSE : updateteResponsebody : ******" + updateteResponsebody);
	Logging.log("***** RESPONSE : updateteResponsebody : ******" + updateteResponsebody);

	String updateResponse = updateteResponsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : updateResponse : ******" + updateResponse);
	Logging.log("***** RESPONSE : updateResponse : ****** " + updateResponse);

	// Asserting Response Code
	Assertion.assertEquals(updateteResponsebody.getStatus(), 500, "Non existent ID updated Succesfully");
	Assertion.assertTrue(updateResponse.contains("Invalid Savedsearch Id"),
		"Savedsearch update succeeded with Invalid Savedsearch Id");
	/* ends - update saved search */
    }

    /**
     * Bhagyasree - Get saved search when passing search text
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/search?searchText={searchText}
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Verifying saved search service for searching searchText with
     *             correct parameter and expecting success response.
     *             </p>
     *             <p>
     *             <b>Input :</b> searchText = bhagyasree
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 200
     *             </p>
     *             <p>
     *             <b>Category :</b> Positive - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#C90000> P1</font>
     *             </p>
     */
    @Test(groups = { "sanity", "savedSearchUsingSearchText", "NA" })
    public void savedSearchUsingSearchText() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/save_search/search?searchText={searchText}"
		+ "\nDescription: Verifying saved search service for searching searchText with correct parameter and expecting success response."
		+ "\nInput: searchText = bhagyasree " + "\nExpected Output: Response status 200");

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes GET request and returns Response
	Response responsebody = suggestConsumer.getSavedSearchUsingSearchText(hostName);

	String response = responsebody.readEntity(String.class);

	System.out.println(
		"***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(StringUtils.containsIgnoreCase(response, "bhagyasree"),
		"searchText=bhagyasree is not found in response");

    }

    /**
     * Bhagyasree - Validation on search text in saved search when passing null
     * 
     * @throws IOException
     * @throws ClientProtocolException
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/search?searchText={blankInput}
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Validation on search text in saved search when passing null
     *             and expecting failure response.
     *             </p>
     *             <p>
     *             <b>Input :</b> searchText = {blankInput}
     *             </p>
     *             <p>
     *             <b>Expected Output :</b> Response status 400
     *             </p>
     *             <p>
     *             <b>Category :</b> Negative - Functional Test Case
     *             </p>
     *             <p>
     *             <b>Bug Level :</b><font color=#E6A001> P3</font>
     *             </p>
     */
    @Test(groups = { "sanity", "validationOnSavedSearchUsingSearchText", "NA" })
    public void validationOnSavedSearchUsingSearchText() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/save_search/search?searchText={blankInput}"
		+ "\nDescription: Validation on search text in saved search when passing null and expecting failure response."
		+ "\nInput: searchText={blankInput}" + "\nExpected Output: Response status 400");

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes GET request and returns Response
	Response responsebody = suggestConsumer.validationOnSavedSearchUsingSearchText(hostName);
	String response = responsebody.readEntity(String.class);

	System.out.println("RESPONSE >>" + response);
	Logging.log("RESPONSE >>" + response);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 400, "Search Text is not empty");
	Assertion.assertTrue(response.contains("Invalid input. searchText cannot be null or empty."),
		"service is working with null input");
    }

    /**
     * Bhagyasree - Get saved search when passing partial search text
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/

    @Test(groups = { "sanity", "savedSearchUsingPartialSearchText", "NA" })
    public void savedSearchUsingPartialSearchText() throws ClientProtocolException, IOException {
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes GET request and returns Response
	Response responsebody = suggestConsumer.getSavedSearchUsingPartialSearchText(hostName);
	String response = responsebody.readEntity(String.class);
	System.out.println("RESPONSE >>" + response);
	Logging.log("RESPONSE >>" + response);
	String skill = "jav";
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	Assert.assertTrue(response.contains(skill), "Response body doesnt contain the partial text");

    }

    /**
     *
     * Author - Bhagyasree Test case description - Search candidates for skill
     * search
     * 
     * 
     */

    @Test(groups = { "sanity", "getCandidatesFromSavedSearch", "NA" },
	    dependsOnGroups = { "createPublicSavedSearchWithSkill" })
    public void getCandidatesFromSavedSearch() throws ClientProtocolException, IOException {

	System.out.println("Get candidates from saved search");
	Logging.log("Get candidates from saved search");

	// Get input bean
	/*
	 * com.spire.base.service.utils.SearchInput inputBean = SearchUtil
	 * .getSearchInputBeanWithSkillWithoutPageInfo();
	 * Logging.log("inputBean " + inputBean);
	 */
	String inputBean = "{\"searchInput\": {\"searchQueryString\": \"(skill:MySQL or skill:ajax)\",\"searchAttributeMap\": {\"skill\": [\"MySQL\",\"ajax\"]}}}}";
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	System.out.println("****" + savedSearchId);
	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.getCandidatesFromSavedSearch(inputBean, hostName, savedSearchId);
	Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
	System.out.println("***** RESPONSE : responsebody : ******" + responsebody);
	Logging.log("responsebody " + responsebody);

	String response = responsebody.readEntity(String.class);
	System.out.println("***** RESPONSE : response : ******" + response);
	Logging.log("response " + response);
	// Asserting Response Code
	// Assertion.assertEquals(responsebody.getStatus(), 200,
	// "Response not successful");

    }

    /**
     * Bhagyasree - Create a duplicate saved search
     * 
     * @throws IOException
     * @throws ClientProtocolException
     **/
    @Test(groups = { "sanity", "createSavedSearchWithExistingSavedSearchName", "NA" })
    public void createSavedSearchWithExistingSavedSearchName() throws ClientProtocolException, IOException {

	System.out.println("Create a duplicate saved search ");
	Logging.log("Create a duplicate saved search ");
	searchUtil = new SearchUtil();
	// Executes get request and returns Response
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchBeanWithExistingSavedSearchName();
	Logging.log("inputBean " + inputBean);
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.createSavedSearchWithExistingSavedSearchName(inputBean, hostName);

	// Executes POST request and returns Response
	Response responsebody1 = suggestConsumer.createSavedSearchWithExistingSavedSearchName(inputBean, hostName);

	String response = responsebody1.readEntity(String.class);
	System.out.println("***** RESPONSE : response : ******" + response);
	Logging.log("response " + response);
	// Asserting Response Code
	Assertion.assertTrue(responsebody1.getStatus() != 200,
		"response code expected not equal to 200 but found as:" + responsebody.getStatus());
    }

}