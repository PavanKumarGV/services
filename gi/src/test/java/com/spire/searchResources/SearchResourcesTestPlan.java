package com.spire.searchResources;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.Constants;
import com.spire.base.service.ReadingServiceEndPointsProperties;
// import spire.talent.gi.beans.SavedSearchDetails;
import com.spire.base.service.utils.SavedSearchDetails;
import com.spire.base.service.utils.SearchUtil;
import com.spire.service.consumers.SearchResourcesConsumer;

import spire.talent.gi.beans.SearchInput;

/**
 * @author jyoti
 *
 * @param <SearchCriteriaBean>
 */
public class SearchResourcesTestPlan<SearchCriteriaBean> extends TestPlan {

    String		    hostName;
    String		    userId;
    String		    password;

    SearchResourcesConsumer candConsumer		 = null;
    SearchInput		    SearchBeanRequest		 = null;
    static String	    Input			 = null;
    SearchUtil		    searchUtil			 = null;
    static String	    savedSearchId		 = null;

    public static String    SKILL_WITH_SPECIAL_CHARACTER = ReadingServiceEndPointsProperties
	    .getServiceEndPoint("skill_with_special_character");
    public static String    SKILL_JAVA			 = ReadingServiceEndPointsProperties
	    .getServiceEndPoint("skill_java");
    public static String    SKILL_MYSQL			 = ReadingServiceEndPointsProperties
	    .getServiceEndPoint("skill_mysql");
    public static String    LOCATION_MUMBAI		 = ReadingServiceEndPointsProperties
	    .getServiceEndPoint("location_mumbai");
    public static String    LOCATION_BANGALORE		 = ReadingServiceEndPointsProperties
	    .getServiceEndPoint("location_bangalore");
    public static String    INVALID_KEYWORD		 = ReadingServiceEndPointsProperties
	    .getServiceEndPoint("invalid_keyword");
    public static String    SEARCH_TEXT			 = ReadingServiceEndPointsProperties
	    .getServiceEndPoint("search_text");

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
     *  	   <p>
     *             @author Bhagyasree & jyoti
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
     *             Get suggestion when passing keyword having SpecialCharacters
     *             and expecting success response.
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "verifySuggestForSkillwithSpecialCharacter", "NA" })
    public void verifySuggestForSkillwithSpecialCharacter() throws ClientProtocolException, IOException {

	Logging.log("Service Name: /generic-services/api/search/_suggest?keyword=" + SKILL_WITH_SPECIAL_CHARACTER
		+ "\nDescription: Get suggestion when passing keyword having SpecialCharacters(Like C#, .Net, C++) and expecting success response."
		+ "\nInput: " + SKILL_WITH_SPECIAL_CHARACTER + "\nExpected Output: Response status 200");

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes Get request and returns Response
	Response responsebody = suggestConsumer.getSuggestForSkillwithSpecialCharacter(hostName);
	String response = responsebody.readEntity(String.class);

	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertTrue(responsebody.getStatus() == 200,
		"response code expected equal to 200 but found as:" + responsebody.getStatus());
	Assert.assertTrue(response.contains(SKILL_WITH_SPECIAL_CHARACTER), "Response doesnot contain .net as skill");
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "searchCandidatesWithSkill", "NA" })
    public void searchCandidatesWithSkill() throws ClientProtocolException, IOException {
	// Get input bean
	com.spire.base.service.utils.SearchInputRequest inputBean = SearchUtil.getSearchInputBeanWithSkill();

	Logging.log("Service Name: /generic-services/api/search/_candidates"
		+ "\nDescription: Verifying 'Get Candidates by Savedsearch' Service as per given skills as correct parameter and expecting success response."
		+ "\nInput: " + inputBean + "\nExpected Output: Response status 200");

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.searchCandidate(inputBean, hostName);
	String response = responsebody.readEntity(String.class);

	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(
		StringUtils.containsIgnoreCase(response, SKILL_MYSQL)
			&& StringUtils.containsIgnoreCase(response, SKILL_JAVA),
		"Response does not contain " + SKILL_MYSQL + " and " + SKILL_JAVA + " as skill");
    }

    /**
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
     * <p>
     * @author Bhagyasree & jyoti
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

	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(
		StringUtils.containsIgnoreCase(response, SKILL_MYSQL)
			&& StringUtils.containsIgnoreCase(response, SKILL_JAVA)
			&& StringUtils.containsIgnoreCase(response, LOCATION_BANGALORE)
			&& StringUtils.containsIgnoreCase(response, LOCATION_MUMBAI),
		"Response does not contain " + SKILL_MYSQL + "/" + SKILL_JAVA + "/" + LOCATION_BANGALORE + "/"
			+ LOCATION_MUMBAI);
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
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

	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertTrue(responsebody.getStatus() == 500, "Response not successful");
	Assertion.assertTrue(response.contains("Invalid Savedsearch Id"), "getSavedSearchById found a non existent id");
    }

    /**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with full skill search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid type.
	 * Keyword is "java" and type is "skill"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b> Positive Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=C90000> P1</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */

	@Test(groups = { "sanity", "getautocompletewithfullskillRequest", "NA" })
	public void getAutoCompleteWithFullSkillRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with full skill search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"java\" and type is \"skill\"" + "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompleteFullSkillSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.skill), "skill not found in the response.");

		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("full_skill_to_search")),
				"full skill not found in response");

	}

	

	/**
	 * 
	 *         <p>
	 *         <b>Target Service URL :</b>
	 *         generic-services/api/search/auto_complete
	 *         </p>
	 *         <p>
	 *         <b>Test Case Description :</b>
	 *         </p>
	 *         <p>
	 *         Verifying autocomplete with partial skill search service with
	 *         correct parameter and expecting pass response.
	 * 
	 *         </p>
	 *         <p>
	 *         <b>Input :</b>Valid keyword that exist in the system and valid type.Keyword is "ja" and type is "skill"
	 *         </p>
	 *         <p>
	 *         <b>Expected Output :</b>Positive Response status "200"
	 *         </p>
	 *         <p>
	 *         <b>Category :</b> Positive - Functional Test Case
	 *         </p>
	 *         <p>
	 *         <b>Bug Level :</b><font color=#C90000> P1</font>
	 *         </p>
	 *         @author Pritisudha
	 *         
	 */
	@Test(groups = { "sanity", "getautocompletewithpartialskillRequest", "NA" })
	public void getAutoCompleteWithPartialSkillRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with partial skill search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"ja\" and type is \"skill\"" + "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompletePartialSkillSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.skill), "skill not found in the response.");

		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("partial_Skill_to_search")),
				"partial skill search not found in response");

	}


	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with full institute search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b>Valid keyword that exist in the system and valid type.Keyword is "Bhopal%20University" and type is "institutename"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b>Positive Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * </p>
	 * @author Pritisudha
	 */

	@Test(groups = { "sanity", "getautocompletewithfullinstituteRequest", "NA" })
	public void getAutoCompleteWithFullInstituteRequest() {
		Logging.log(
				"Service Name: /generic-services/api/search/auto_complete \n Verifying autocomplete with full institute  search service with correct parameter and expecting pass response.");
		
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with full institute search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Bhopal%20University\" and type is \"institutename\"" + "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompleteFullInstituteSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.institute), "institute not found in the response.");
		
		  Assertion.assertTrue(response.contains(
		  ReadingServiceEndPointsProperties.getServiceEndPoint(
		  "full_institute_to_search")), "full institute not found in response"
		  );
		 
	}

	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with partial institute search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid type.Keyword is "University" and type is "institutename"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b>Positive Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */
	@Test(groups = { "sanity", "getautocompletewithpartialinstituteRequest", "NA" })
	public void getAutoCompleteWithPartialInstituteRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with partial institute search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"University\" and type is \"institutename\"" + "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompletePartialInstituteSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.institute), "institute not found in the response.");
		
		  Assertion.assertTrue(response.contains(
		  ReadingServiceEndPointsProperties.getServiceEndPoint(
		  "partial_institute_to_search")),
		  "partial institute not found in response");
		 
	}


	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying full Education search service with correct parameter and
	 * expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid
	 * type.Keyword is "Computer%20Science%20&%20Engineering" and type is
	 * "education"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b>Positive Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * 
	 *   </p>
	 *    @author Pritisudha
	 */
	@Test(groups = { "sanity", "getautocompletewithfulleducationRequest", "NA" })
	public void getAutoCompleteWithFullEducationRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with full Education search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Computer%20Science%20&%20Engineering\" and type is \"education\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletefulleducationsearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as: " + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.education), "education not found in the response.");

		Assertion.assertTrue(response.contains("Diploma in Computer Applications"),
				"full education not found in response");

	}



	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with partial education search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid type.Keyword is "Master" and type is "education"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b> Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */
	@Test(groups = { "sanity", "getautocompletewithpartialeducationRequest", "NA" })
	public void getAutoCompleteWithPartialEducationRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with partial education search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Master\" and type is \"education\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompletePartialEducationSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.education), "education not found in the response.");
		
		  Assertion.assertTrue(response.contains( ReadingServiceEndPointsProperties.getServiceEndPoint("partial_education_to_search")),
		  "partial education not found in response");
		 
	}


	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying full employer search service with correct parameter and
	 * expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid
	 * type.Keyword is "Wellsfargo%20India%20Pvt%20Ltd" and type is
	 * "employername"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b> Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=C90000> P1</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */
	@Test(groups = { "sanity", "getautocompletewithfullemployerRequest", "NA" })
	public void getAutoCompleteWithFullEmployerRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with full employer search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Wellsfargo%20India%20Pvt%20Ltd\" and type is \"employername\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompleteFullEmployerSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.employer), "employer not found in the response.");

		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("full_employer_to_search")),
				"full employer not found in response");

	}


	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with partial employer search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid
	 * type.Keyword is "Wellsf" and type is "employername"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b>Positive Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */

	@Test(groups = { "sanity", "getautocompletepartialemployerRequest", "NA" })
	public void getAutoCompletePartialEmployerRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with partial employer search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Wellsf\" and type is \"employername\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompletePartialEmployerSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.employer), "employer not found in the response.");
		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("partial_employer_to_search")),
				"partial employer not found in response");
	}


	/**
	 * priti-Get autocomplete with full location
	 */
	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with full location search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b>Valid keyword that exist in the system and valid
	 * type.Keyword is "Bangalore" and type is "locationname"
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
	 * @author Pritisudha
	 */
	@Test(groups = { "sanity", "getautocompletfulllocationRequest", "NA" })
	public void getAutoCompletFullLocationRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with full location search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Bangalore\" and type is \"locationname\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompleteFullLocationSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.location), "location not found in the response.");
		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("full_location_to_search")),
				"Response is Empty. So full location not found in response");
	}

	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with partial location search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid
	 * type.Keyword is "Ba" and type is "locationname"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b>Positive Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */
	
	@Test(groups = { "sanity", "getautocompletpartiallocationRequest","NA" })
	public void getAutoCompletPartialLocationRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with partial location search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Ba\" and type is \"locationname\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getAutoCompletePartialLocationSearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.location),
				"location not found in the response.");
		Assertion.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("partial_location_to_search")),
				"Response is Empty. So partial location not found in response");
	}


	/**
	 * priti-Get autocomplete with full sourcetype
	 */
	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with full sourcetype search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b>Valid keyword that exist in the system and valid
	 * type.Keyword is "Job%20Board" and type is "sourcetype"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b> Positive Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#C90000> P1</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */
	@Test(groups = { "sanity", "getautocompletfullsourcetypeRequest", "NA" })
	public void getAutoCompletFullSourceTypeRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with full sourcetype search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Job%20Board\" and type is \"sourcetype\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompleteFullSourceTypeSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.sourcetype), "sourcetype not found in the response.");

		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("full_sourcetype_to_search")),
				"Response is empty.So full sourcetype not found in response");

	}

	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with partial sourcetype search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid
	 * type.Keyword is "Job" and type is "sourcetype"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b> Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */
    @Test(groups = { "sanity", "getautocompletpartialsourcetypeRequest", "NA" })
    public void getautocompletpartialsourcetypeRequest() {
    	Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with partial sourcetype search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"job\" and type is \"sourcetype\""
				+ "\nExpected Output: Response status 200");
	SearchResourcesConsumer suggestConsumer = null;
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	Response responsebody = suggestConsumer.getautocompletepartialsourcetypesearch(hostName);
	Assertion.assertEquals(200, responsebody.getStatus(),
		"Response expected 200 but found as:" + responsebody.getStatus());
	String response = responsebody.readEntity(String.class);
	Logging.log(" Response Body >>" + response);
	Assertion.assertTrue(response.contains(Constants.sourcetype), "sourcetype not found in the response.");
	
	  Assertion.assertTrue(response.contains(
	  ReadingServiceEndPointsProperties.getServiceEndPoint(
	  "partial_sourcetype_to_search")),
	  "partial sourcetype not found in response");
	 
    }

    /**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with full sourcename search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid
	 * type.Keyword is "Candidate%20Referral" and type is "sourcename"
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
	 * 
	 * @author Pritisudha
	 */
	@Test(groups = { "sanity", "getautocompletfullsourcenameRequest", "NA" })
	public void getAutoCompletFullSourceNameRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with full sourcename search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Candidate%20Referral\" and type is \"sourcename\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompleteFullSourceNameSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.sourcename), "sourcename not found in the response.");
		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("full_sourcename_to_search")),
				"Response is Empty. So full sourcename not found in response");

	}


	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with partial sourcename search service with
	 * correct parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid
	 * type.Keyword is "Candidate" and type is "sourcename"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b> Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 * 
	 */
	@Test(groups = { "sanity", "getautocompletpartialsourcenameRequest", "NA" })
	public void getAutoCompletPartialSourceNameRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with partial sourcename search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Candidate\" and type is \"sourcename\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompletePartialSourceNameSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.sourcename), "sourcename not found in the response.");
		 Assertion.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("partial_sourcename_to_search")),
		 "partial sourcename not found in response");
	}


	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with full status search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid
	 * type.Keyword is "New" and type is "status"
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
	 * 
	 * @author Pritisudha
	 */
	@Test(groups = { "sanity", "getautocompletfullstatusRequest", "NA" })
	public void getAutoCompletFullStatusRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with full status search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"New\" and type is \"status\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompleteFullStatusSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.status), "status not found in the response.");

		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("full_status_to_search")),
				"response is empty so full status not found in response");

	}


	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with partial status search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Valid keyword that exist in the system and valid
	 * type.Keyword is "Ne" and type is "status"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b>Positive Response status "200"
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#C90000> P1</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */
	@Test(groups = { "sanity", "getautocompletpartialstatusRequest", "NA" })
	public void getautocompletpartialstatusRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete with partial status search service with correct parameter and expecting pass response."
				+ "\nInput: Valid keyword that exist in the system and valid type.Keyword is \"Ne\" and type is \"status\""
				+ "\nExpected Output: Response status 200");
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompletePartialStatusSearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.status), "status not found in the response.");

		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("partial_status_to_search")),
				"partial status not found in response");

	}

    /**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete without keyword search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b>  valid type exit in system. i.e type is "skill"
	 * 
	 * </p>
	 * <p>
	 * <b>Expected Output :</b>Positive Response status 200
	 * </p>
	 * <p>
	 * <b>Category :</b> Positive - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * </p>
	 * @author Pritisudha
	 */

	@Test(groups = { "sanity", "getautocompletewithoutkeywordRequest", "NA" })
	public void getAutoCompleteWithOutKeywordRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete only on type without Keyword search service with correct parameter and expecting pass response."
				+ "\nInput:  valid type exit in system. i.e type is \"skill\""
				+ "\nExpected Output: Response status 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompleteWithOutKey(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.skill), "skill not found in the response.");

	}



	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete without type search service with correct parameter
	 * and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b>Passing Keyword exit in system. i.e Keyword is "Ne"
	 * </p>
	 * <p>
	 * <b>Expected Output :</b> Response status not  "200"
	 * </p>
	 * <p>
	 * <b>Category :</b> Negative - Functional Test Case
	 * </p>
	 * <p>
	 * <b>Bug Level :</b><font color=#81017F> P2</font>
	 * </p>
	 * 
	 * @author Pritisudha
	 */

	@Test(groups = { "sanity", "getautocompletewithouttypeRequest", "NA" })
	public void getAutoCompleteWithOutTypeRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete"
				+ "\nDescription:  Verifying autocomplete only on Keyword without type search service with correct parameter and expecting pass response."
				+ "\nInput:  valid type exit in system. i.e type is \"skill\""
				+ "\nExpected Output: Response status not 200");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getAutoCompleteWithOutType(hostName);
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             12.08.2016
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
	    Assertion.assertTrue(SearchUtil.isSortedInAscOrder(modifiedOnArr),
		    "modifiedOn is not sorted in ascending order");
	}
    }

    /**
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             12.08.2016
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
	    Assertion.assertTrue(SearchUtil.isSortedInDscOrder(modifiedOnArr),
		    "modifiedOn is not sorted in descending order");
	}
    }

    /**
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             12.08.2016
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
	    Assertion.assertTrue(SearchUtil.isSortedInAscOrder(createdOnArr),
		    "createdOn is not sorted in ascending order");
	}
    }

    /**
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             12.08.2016
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
	    Assertion.assertTrue(SearchUtil.isSortedInDscOrder(createdOnArr),
		    "createdOn is not sorted in descending order");
	}
    }

    /**
     * @throws IOException
     * @throws ClientProtocolException
     * 
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/_suggest?keyword={invalidKeyword}
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "verifySuggestRequestForInvalidKeyword", "NA" })
    public void verifySuggestRequestForInvalidKeyword() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/_suggest?keyword=" + INVALID_KEYWORD
		+ "\nDescription: Get suggestion when passing invalid keyword and expecting empty response."
		+ "\nInput: " + INVALID_KEYWORD + "\nExpected Output: Response status 200 with empty values");

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes get request and returns Response
	Response responsebody = suggestConsumer.getSuggestForInvalidKeyword(hostName);
	String response = responsebody.readEntity(String.class);

	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertTrue(responsebody.getStatus() == 200,
		"response code expected equal to 200 but found as:" + responsebody.getStatus());
	Assert.assertFalse(response.contains(INVALID_KEYWORD), "Response contains " + INVALID_KEYWORD + " as keyword");
    }

    /**
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "getSavedSearchByIdWithSpace", "NA" })
    public void getSavedSearchByIdWithSpace() throws ClientProtocolException, IOException {
	Logging.log("Create Saved Search with skill before getting");

	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkill();
	Logging.log("inputBean " + inputBean);

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.createSavedSearchWithSkill(inputBean, hostName);

	Logging.log("Create responsebody " + responsebody.getStatus());

	String response = responsebody.readEntity(String.class);
	Logging.log("Create response " + response);

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

	Logging.log("***** GET RESPONSE CODE ******" + getresponsebody.getStatus() + "\n***** GET RESPONSE ******"
		+ getresponse);

	// Asserting Response Code
	Assertion.assertEquals(getresponsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(getresponse.contains(id),
		"getSavedSearchById service could not find savedSearch with id : " + id);
    }

    /**
     * @throws IOException
     * @throws ClientProtocolException
     * 
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Create saved search with skill
     *             </p>
     *             <p>
     *             <b>Input :</b> savedSearch object
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "createSavedSearchWithSkill", "NA" })
    public void createSavedSearchWithSkill() throws ClientProtocolException, IOException {
	Logging.log("Create Saved Search with skill");
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkill();

	Logging.log("Service Name: /generic-services/api/search/save_search"
		+ "\nDescription: Create saved search with skill." + "\nInput: " + inputBean
		+ "\nExpected Output: Response status 200");

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.createSavedSearchWithSkill(inputBean, hostName);
	String response = responsebody.readEntity(String.class);

	Logging.log("***** RESPONSE : responsebody : ******" + responsebody);
	Logging.log("***** RESPONSE : response : ******" + response);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(response.contains("Savedsearch created successfully"), "Savedsearch creation failed");
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
     * @throws IOException
     * @throws ClientProtocolException
     * 
     *             Updated by Jyoti
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/{savedSearchIdToBeDeleted}
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             delete particular saved search by ID that exist
     *             </p>
     *             <p>
     *             <b>Input :</b> savedSearchIdToBeDeleted like
     *             57e9ddcfb73f2a272ace61af
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "deleteSavedSearchById", "NA" })
    public void deleteSavedSearchById() throws ClientProtocolException, IOException {
	/* starts - create saved search before deleting */
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkill();
	Logging.log("create saved search before deleting : inputBean " + inputBean);

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	// Executes POST request and returns Response
	Response createResponsebody = suggestConsumer.createSavedSearchWithSkill(inputBean, hostName);

	String createResponse = createResponsebody.readEntity(String.class);
	Logging.log("***** RESPONSE : createResponsebody : ****** " + createResponsebody);
	Logging.log("***** RESPONSE : createResponse : ******" + createResponse);

	JSONObject createResponseJson = new JSONObject(createResponse);
	JSONObject responseJson = createResponseJson.getJSONObject("response");
	String id = responseJson.getString("id");

	Logging.log("***** RESPONSE : createResponseJson : ******" + createResponseJson);
	Logging.log("***** RESPONSE : id : ******" + id);
	/* ends - create saved search before deleting */

	// Executes DELETE request and returns Response
	/* starts - delete saved search */
	Logging.log("Service Name: /generic-services/api/search/save_search/search?searchText={blankInput}"
		+ "\nDescription: Delete particular saved search by ID that exist and expecting success response."
		+ "\nInput: id :" + id + "\nExpected Output: Response status 200");

	Response responsebody = suggestConsumer.deleteSavedSearchById(hostName, id);
	String response = responsebody.readEntity(String.class);

	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(response.contains("Savedsearch deleted successfully"), "Savedsearch deletion failed");
	/* ends - delete saved search */
    }

    /**
     * @throws IOException
     * @throws ClientProtocolException
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/{invalidSavedSearchId}
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             delete particular saved search by ID that does not exist
     *             </p>
     *             <p>
     *             <b>Input :</b> invalidSavedSearchId like 123454321
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "deleteSavedSearchByIdNonExisting", "NA" })
    public void deleteSavedSearchByIdNonExisting() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/save_search/{invalidSavedSearchId}"
		+ "\nDescription: Delete particular saved search by ID that does not exist and expecting failure response."
		+ "\nInput: invalidSavedSearchId :\nExpected Output: Response status 500");
	
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	
	// Executes DELETE request and returns Response
	Response responsebody = suggestConsumer.deleteSavedSearchByIdNonExisting(hostName);
	String response = responsebody.readEntity(String.class);
	
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	
	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 500, "Deleted Successfully");
	Assertion.assertTrue(response.contains("Invalid Savedsearch Id"), "Savedsearch deleted successfully");
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "updateSavedSearchById", "NA" })
    public void updateSavedSearchById() throws ClientProtocolException, IOException {
	Logging.log("Update particular saved search by ID that exist");

	/* starts - create saved search before updating */
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchInputBeanWithSkill();
	Logging.log("inputBean " + inputBean);

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	Response createResponsebody = suggestConsumer.createSavedSearchWithSkill(inputBean, hostName);
	Logging.log("createResponsebody " + createResponsebody);

	String createResponse = createResponsebody.readEntity(String.class);
	Logging.log("createResponse " + createResponse);

	JSONObject createResponseJson = new JSONObject(createResponse);
	JSONObject responseJson = createResponseJson.getJSONObject("response");
	String id = responseJson.getString("id");

	Logging.log("***** RESPONSE : id : ****** " + id);
	/* ends - create saved search before updating */

	// Executes PUT request and returns Response
	/* starts - update saved search */
	inputBean = SearchUtil.updateSavedSearchInputBeanWithSkill(inputBean);

	Logging.log("Service Name: /generic-services/api/search/save_search"
		+ "\nDescription: Update particular saved search by ID that exist" + "\nInput: " + inputBean
		+ "\nExpected Output: Response status 200");

	Response updateteResponsebody = suggestConsumer.updateSavedSearchWithSkill(inputBean, hostName, id);
	Logging.log("***** RESPONSE : updateteResponsebody : ******" + updateteResponsebody);

	String updateResponse = updateteResponsebody.readEntity(String.class);
	Logging.log("***** RESPONSE : updateResponse : ******" + updateResponse);

	// Asserting Response Code
	Assertion.assertEquals(updateteResponsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(updateResponse.contains("Savedsearch updated successfully"), "Savedsearch update failed");
	/* ends - update saved search */
    }

    /**
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "updateSavedSearchByIdNonExisting", "NA" })
    public void updateSavedSearchByIdNonExisting() throws ClientProtocolException, IOException {
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

	Logging.log("Service Name: /generic-services/api/search/save_search/" + id
		+ "\nDescription: Update particular saved search by ID that does not exist" + "\nInput: " + inputBean
		+ "\nExpected Output: Response status 500");

	Response updateteResponsebody = suggestConsumer.updateSavedSearchWithSkill(inputBean, hostName, id);
	Logging.log("***** RESPONSE : updateteResponsebody : ******" + updateteResponsebody);

	String updateResponse = updateteResponsebody.readEntity(String.class);
	Logging.log("***** RESPONSE : updateResponse : ****** " + updateResponse);

	// Asserting Response Code
	Assertion.assertEquals(updateteResponsebody.getStatus(), 500, "Non existent ID updated Succesfully");
	Assertion.assertTrue(updateResponse.contains("Invalid Savedsearch Id"),
		"Savedsearch update succeeded with Invalid Savedsearch Id");
	/* ends - update saved search */
    }

    /**
     * @throws IOException
     * @throws ClientProtocolException
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "savedSearchUsingSearchText", "NA" })
    public void savedSearchUsingSearchText() throws ClientProtocolException, IOException {
	Logging.log("Service Name: /generic-services/api/search/save_search/search?searchText={searchText}"
		+ "\nDescription: Verifying saved search service for searching searchText with correct parameter and expecting success response."
		+ "\nInput: searchText = " + SEARCH_TEXT + "\nExpected Output: Response status 200");

	SearchResourcesConsumer suggestConsumer = null;

	// Get authentication token
	suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes GET request and returns Response
	Response responsebody = suggestConsumer.getSavedSearchUsingSearchText(hostName);

	String response = responsebody.readEntity(String.class);

	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
	Assertion.assertTrue(StringUtils.containsIgnoreCase(response, SEARCH_TEXT),
		"searchText=" + SEARCH_TEXT + " is not found in response");

    }

    /**
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
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

	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

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
     *             <p>
     *             <b>Target Service URL :</b>
     *             /generic-services/api/search/save_search/{savedSearchId}/candidates
     *             </p>
     *             <p>
     *             <b>Test Case Description :</b>
     *             </p>
     *             <p>
     *             Search candidates for given skill
     *             </p>
     *             <p>
     *             <b>Input :</b> savedSearchId like 57e9ddcfb73f2a272ace61af and searchInput
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
     *  	   <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "getCandidatesFromSavedSearch", "NA" },
	    dependsOnGroups = { "createPublicSavedSearchWithSkill" })
    public void getCandidatesFromSavedSearch() throws ClientProtocolException, IOException {
	String inputBean = "{\"searchInput\": {\"searchQueryString\": \"(skill:MySQL or skill:ajax)\",\"searchAttributeMap\": {\"skill\": [\"MySQL\",\"ajax\"]}}}}";
	
	Logging.log("Service Name: /generic-services/api/search/save_search/{savedSearchId}/candidates"
		+ "\nDescription: Get candidates from saved search and expecting success response."
		+ "\nInput: "+inputBean + "\nExpected Output: Response status 200");
	
	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
	
	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.getCandidatesFromSavedSearch(inputBean, hostName, savedSearchId);
	String response = responsebody.readEntity(String.class);
	
	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);
	
	// Asserting Response Code
	Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessful, Expected 200 status code");
	
	//check if candidates are present in elastic search for given skills as searchInput
	JSONObject obj = new JSONObject(response);
	JSONArray entities = obj.getJSONObject("response").getJSONArray("entities");
	if(entities!=null){
	    Logging.log("Number of candidates present in elastic search for given skills as searchInput : "+entities.length());
	}
    }

    /**
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
     *             Create a duplicate saved search
     *             </p>
     *             <p>
     *             <b>Input :</b> saved search details with existing name
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
     *             <p>
     *             @author Bhagyasree & jyoti
     *             </p>
     */
    @Test(groups = { "sanity", "createSavedSearchWithExistingSavedSearchName", "NA" })
    public void createSavedSearchWithExistingSavedSearchName() throws ClientProtocolException, IOException {
	SavedSearchDetails inputBean = SearchUtil.createSavedSearchBeanWithExistingSavedSearchName();

	Logging.log("Service Name: /generic-services/api/search/save_search"
		+ "\nDescription: Creating a duplicate saved search and expecting failure response." + "\nInput: "
		+ inputBean + "\nExpected Output: Response status 400");

	// Get authentication token
	SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);

	// Executes POST request and returns Response
	Response responsebody = suggestConsumer.createSavedSearchWithExistingSavedSearchName(inputBean, hostName);

	// Executes POST request and returns Response
	Response responsebody1 = suggestConsumer.createSavedSearchWithExistingSavedSearchName(inputBean, hostName);
	String response = responsebody1.readEntity(String.class);

	Logging.log("***** RESPONSE CODE ******" + responsebody.getStatus() + "\n***** RESPONSE ******" + response);

	// Asserting Response Code
	Assertion.assertTrue(responsebody1.getStatus() == 400,
		"response code expected equal to 400 but found as:" + responsebody.getStatus());
	Assertion.assertTrue(response.contains("Invalid input. Savedsearch cannot be created using existing name"),
		"Savedsearch created with an existing name");
    }

}