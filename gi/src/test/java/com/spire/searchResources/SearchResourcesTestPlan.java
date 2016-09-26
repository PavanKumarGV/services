package com.spire.searchResources;

import org.testng.annotations.Test;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.JsonParser;
import com.spire.base.service.Constants;
import com.spire.base.service.ReadingServiceEndPointsProperties;
//import spire.talent.gi.beans.SavedSearchDetails;
import com.spire.base.service.utils.SavedSearchDetails;
import com.spire.base.service.utils.SearchUtil;

import spire.talent.gi.beans.SearchInput;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.service.consumers.SearchResourcesConsumer;

public class SearchResourcesTestPlan<SearchCriteriaBean> extends TestPlan {

	String hostName;
	String userId;
	String password;

	SearchResourcesConsumer candConsumer = null;
	SearchInput SearchBeanRequest = null;
	static String Input = null;
	SearchUtil searchUtil = null;
	static String savedSearchId = null;

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
		Logging.log("Start :: Login with Username: " + userId + "Password: "
				+ password + "and hostName: " + hostName);

	}

	/**
	 * Vasista - Get -Similler profiles
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "GetSimillerProfiles","NA" })
	public void GetSimillerProfiles() throws ClientProtocolException,
			IOException {

		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		//suggestConsumer.getSemilarProfiles(hostName);
		Response responsebody = suggestConsumer.getSemilarProfiles(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Assert.assertTrue(response
				.contains("id"));
		Logging.log("Response: "+response);
	}

	/**
	 * Bhagyasree - Get suggestion when passing keyword
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/

	@Test(groups = { "sanity", "verifySuggestRequest","NA" })
	public void verifySuggestRequest() throws ClientProtocolException,
			IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
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
	 **/

	@Test(groups = { "sanity", "verifySuggestValidation","NA" })
	public void verifySuggestValidation() throws ClientProtocolException,
			IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token

		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes Get request and returns Response

		Response responsebody = suggestConsumer.suggestValidation(hostName);
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() != 200,
				"response code expected not equal to 200 but found as:"
						+ responsebody.getStatus());
	}

	/**
	 * Author - Bhagyasree Test case description - Get suggestion when passing
	 * keyword having multiple words(Like project planning, project management)
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "verifySuggestForSkillwithMultipleWords","NA" })
	public void verifySuggestForSkillwithMultipleWords()
			throws ClientProtocolException, IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes Get request and returns Response
		Response responsebody = suggestConsumer
				.getSuggestForSkillwithMultipleWords(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200,
				"response code expected equal to 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		// Asserting Response Code
		Assert.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("suggest_Multiple_words").replace( "%20"," ")));

	}

	/**
	 * Author - Bhagyasree Test case description - Get suggestion when passing
	 * keyword having SpecialCharacters(Like C#, .Net, C++)
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "verifySuggestForSkillwithSpecialCharacter","NA" })
	public void verifySuggestForSkillwithSpecialCharacter()
			throws ClientProtocolException, IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes Get request and returns Response
		Response responsebody = suggestConsumer
				.getSuggestForSkillwithSpecialCharacter(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200,
				"response code expected equal to 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		// Asserting Response Code
		Assert.assertTrue(response.contains("net"));

	}

	/**
	 * Vasista - Get -Similler profiles
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "GetSimilarProfilesNegative","NA" })
	public void GetSimilarProfilesNegative() throws ClientProtocolException,
			IOException {

		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer.getSemilarProfilesNegi(hostName);
		Assertion.assertTrue(responsebody.getStatus() == 200,
				"response code expected not equal to 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log("Response: "+response);
		//Response responsebody = suggestConsumer.getSemilarProfiles(hostName);
		//String response = responsebody.readEntity(String.class);
		//System.out.println("***** RESPONSE ******" + response);
		//Assert.assertTrue(response.contains(" "));

	}
	/**
	 *
	 * Author - Bhagyasree Test case description - Search candidates for skill
	 * search
	 * 
	 * 
	 */

	@Test(groups = { "sanity", "searchCandidatesWithSkill","NA" })
	public void searchCandidatesWithSkill() throws ClientProtocolException,
			IOException {

		System.out.println("Search candidates with skill");
		Logging.log("Search candidates with skill");
		SearchUtil searchUtil = new SearchUtil();
		// Get input bean
		com.spire.base.service.utils.SearchInputRequest inputBean = SearchUtil
				.getSearchInputBeanWithSkill();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		// Executes POST request and returns Response
		Response responsebody = suggestConsumer.searchCandidate(inputBean,
				hostName);
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
		Logging.log("Response Code: " + responsebody.getStatus());

		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("Response Body: " + response);
		// Asserting Response Code
		

	}

	@Test(groups = { "sanity", "searchCandidatesWithSkillAndLocation","NA" })
	public void searchCandidatesWithSkillAndLocation()
			throws ClientProtocolException, IOException {

		System.out.println("Search candidates with skill and location");
		Logging.log("Search candidates with skill and location");
		searchUtil = new SearchUtil();
		com.spire.base.service.utils.SearchInputRequest inputBean = SearchUtil
				.getSearchInputBeanWithSkillAndLocation();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		// Executes POST request and returns Response
		Response responsebody = suggestConsumer.searchCandidate(inputBean,
				hostName);
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
		Logging.log("responsebody " + responsebody);

		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");

	}

	/**
	 * Bhagyasree - Get list of saved search
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "getSavedSearch","NA" })
	public void getSavedSearch() throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		suggestConsumer.getSavedSearch(hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer.getSavedSearch(hostName);
		// Asserting Response Code
				Assertion.assertEquals(responsebody.getStatus(), 200,
						"Response not successful");
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
	public void getSavedSearchById() throws ClientProtocolException,
			IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		suggestConsumer.getSavedSearchById(hostName);
		Response responsebody = suggestConsumer.getSavedSearchById(hostName);
		// Asserting Response Code
				Assertion.assertEquals(responsebody.getStatus(), 200,
						"Response not successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		

	}

	/**
	 * Bhagyasree - Get particular saved search by ID that doesnt not exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "getSavedSearchByNonExistingId" })
	public void getSavedSearchByNonExistingId() throws ClientProtocolException,
			IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		suggestConsumer.getSavedSearchByNonExistingId(hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.getSavedSearchByNonExistingId(hostName);
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus()!=200,
				"Response not successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("Response " + response);

	}

	/**
	 * priti- Get autocomplete with full skill
	 */
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
	 * <b>Input :</b> passing full skill present 
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
	 */

	@Test(groups = { "sanity", "getautocompletewithfullskillRequest", "NA" })
	public void getAutoCompleteWithFullSkillRequest() {
		Logging.log(
				"Service Name: /generic-services/api/search/auto_complete \n Verifying autocomplete with full skill  search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletefullskillsearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
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
	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with partial skill search service with correct
	 * parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b>passing  partial skill 
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
	 */
	@Test(groups = { "sanity", "getautocompletewithpartialskillRequest", "NA" })
	public void getAutoCompleteWithPartialSkillRequest() {
		Logging.log(
				"Service Name: /generic-services/api/search/auto_complete \n Verifying autocomplete with partial skill  search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletepartialskillsearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
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
	 * <b>Input :</b> passing with full institute
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
	 */

	@Test(groups = { "sanity", "getautocompletewithfullinstituteRequest", "NA" })
	public void getAutoCompleteWithFullInstituteRequest() {
		Logging.log(
				"Service Name: /generic-services/api/search/auto_complete \n Verifying autocomplete with full institute  search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletefullinstitutesearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.institute), "institute not found in the response.");
		/*
		 * Assertion.assertTrue(response.contains(
		 * ReadingServiceEndPointsProperties.getServiceEndPoint(
		 * "full_institute_to_search")), "full institute not found in response"
		 * );
		 */
	}

	/**
	 * priti- Get autocomplete with partial institute
	 */
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
	 * <b>Input :</b> passing with partial institute
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
	 */
	@Test(groups = { "sanity", "getautocompletewithpartialinstituteRequest", "NA" })
	public void getAutoCompleteWithPartialInstituteRequest() {
		Logging.log(
				"Service Name: /generic-services/api/search/auto_complete \n Verifying autocomplete with partial institute  search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletepartialinstitutesearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
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
	/**
	 * <p>
	 * <b>Target Service URL :</b>
	 * generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying full Education search service with correct parameter and expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Using Id that not present in the system
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
	 */
	@Test(groups = { "sanity", "getautocompletewithfulleducationRequest", "NA" })
	public void getAutoCompleteWithFullEducationRequest() {
		Logging.log("Service Name: /generic-services/api/search/auto_complete \n Verifying Education search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletefulleducationsearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as: " + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.education), "education not found in the response.");
		/*
		 * Assertion.assertTrue( response.contains(
		 * "Diploma in Computer Applications"),
		 * "full education not found in response");
		 */
	}

	/**
	 * priti-Get autocomplete with partial education
	 */
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
	 * <b>Input :</b> education present in the system
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
	 */
	@Test(groups = { "sanity", "getautocompletewithpartialeducationRequest", "NA" })
	public void getAutoCompleteWithPartialEducationRequest() {
		Logging.log(
				"Service Name: /generic-services/api/search/auto_complete \n Verifying autocomplete with partial education search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletepartialeducationsearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
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
	 * <b>Input :</b> Using Id that not present in the system
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
	 */
	@Test(groups = { "sanity", "getautocompletewithfullemployerRequest", "NA" })
	public void getAutoCompleteWithFullEmployerRequest() {
		Logging.log("Service Name: generic-services/api/search/auto_complete \n Verifying full employer search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletefullemployersearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
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
	/**
	 * <p>
	 * <b>Target Service URL :</b> generic-services/api/search/auto_complete
	 * </p>
	 * <p>
	 * <b>Test Case Description :</b>
	 * </p>
	 * <p>
	 * Verifying autocomplete with partial employer search service with correct parameter and
	 * expecting pass response.
	 * 
	 * </p>
	 * <p>
	 * <b>Input :</b> Using Id that not present in the system
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
	 */

	@Test(groups = { "sanity", "getautocompletepartialemployerRequest","NA" })
	public void getAutoCompletePartialEmployerRequest() {
		Logging.log("Service Name: generic-services/api/search/auto_complete \n Verifying full employer search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartialemployersearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Expected 200 status response but found actual response as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.employer),
				"employer not found in the response.");
	/*	Assertion.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("partial_employer_to_search")),
				"partial employer not found in response");*/
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
	 * <b>Input :</b> passing full location
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
	 */
	@Test(groups = { "sanity", "getautocompletfulllocationRequest", "NA" })
	public void getAutoCompletFullLocationRequest() {
		Logging.log("Service Name: generic-services/api/search/auto_complete \n Verifying full employer search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletefulllocationsearch(hostName);
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
	 * priti-Get autocomplete with partial location
	 */
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
	 * <b>Input :</b> passing partial location
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
	 */
	
	@Test(groups = { "sanity", "getautocompletpartiallocationRequest","NA" })
	public void getAutoCompletPartialLocationRequest() {
		Logging.log("Service Name: generic-services/api/search/auto_complete \n Verifying with partial location search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartiallocationsearch(hostName);
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
	 * <b>Input :</b> passing full sourcetype
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
	 */
	@Test(groups = { "sanity", "getautocompletfullsourcetypeRequest", "NA" })
	public void getAutoCompletFullSourceTypeRequest() {
		Logging.log(
				"Service Name: generic-services/api/search/auto_complete \n Verifying full sourcetype search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletefullsourcetypesearch(hostName);
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
	 * priti-Get autocomplete with partial sourcetype
	 */
	@Test(groups = { "sanity", "getautocompletpartialsourcetypeRequest","NA" })
	public void getAutoCompletPartialSourceTypeRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartialsourcetypesearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Expected 200 status response but found actual response as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.sourcetype),
				"sourcetype not found in the response.");
		/*Assertion.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("partial_sourcetype_to_search")),
				"partial sourcetype not found in response");*/
	}

	/**
	 * priti-Get autocomplete with full sourcename
	 */
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
	 * <b>Input :</b> passing full sourcename
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
	 */
	@Test(groups = { "sanity", "getautocompletfullsourcenameRequest", "NA" })
	public void getAutoCompletFullSourceNameRequest() {
		Logging.log(
				"Service Name: generic-services/api/search/auto_complete \n Verifying autocomplete with full sourcename search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletefullsourcenamesearch(hostName);
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
	 * priti-Get autocomplete with partial sourcename
	 */
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
	 * <b>Input :</b> passing with partial sourcename
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
	 */
	@Test(groups = { "sanity", "getautocompletpartialsourcenameRequest", "NA" })
	public void getAutoCompletPartialSourceNameRequest() {
		Logging.log(
				"Service Name: generic-services/api/search/auto_complete \n Verifying autocomplete with partial sourcename search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletepartialsourcenamesearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.sourcename), "sourcename not found in the response.");
		// Assertion.assertTrue(response.contains("N"),
		// "partial sourcename not found in response");
	}

	/**
	 * priti-Get autocomplete with full status
	 */
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
	 * <b>Input :</b> passing full location
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
	 */
	@Test(groups = { "sanity", "getautocompletfullstatusRequest", "NA" })
	public void getAutoCompletFullStatusRequest() {
		Logging.log(
				"Service Name: generic-services/api/search/auto_complete \n Verifying autocomplete  with full status search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletefullstatussearch(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Response expected 200 but found as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.status), "status not found in the response.");

		Assertion.assertTrue(
				response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("full_status_to_search")),
				"response is empty so full status not found in response");

	}

	/**
	 * priti-Get autocomplete with partial status
	 */
	@Test(groups = { "sanity", "getautocompletpartialstatusRequest" ,"NA"})
	public void getautocompletpartialstatusRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartialstatussearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.status),
				"status not found in the response.");
		/*Assertion.assertTrue(response.contains(ReadingServiceEndPointsProperties.getServiceEndPoint("partial_status_to_search")),
				"partial status not found in response");*/
	}

	/**
	 * priti-Get autocomplete without keyword
	 */
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
	 * <b>Input :</b> without keyword
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
	 */

	@Test(groups = { "sanity", "getautocompletewithoutkeywordRequest", "NA" })
	public void getAutoCompleteWithOutKeywordRequest() {
		Logging.log(
				"Service Name: generic-services/api/search/auto_complete \n Verifying autocomplete without keyword search service with correct parameter and expecting pass response.");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody = suggestConsumer.getautocompletewithoutkey(hostName);
		Assertion.assertEquals(200, responsebody.getStatus(),
				"Expected 200 status response but found actual response as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Logging.log(" Response Body >>" + response);
		Assertion.assertTrue(response.contains(Constants.skill), "skill not found in the response.");

	}

	/**
	 * priti-Get autocomplete without type
	 */
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
	 * <b>Input :</b>autocomplete without type in the system
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
	 */

	@Test(groups = { "sanity", "getautocompletewithouttypeRequest", "NA" })
	public void getAutoCompleteWithOutTypeRequest() {
		Logging.log(
				"Service Name: generic-services/api/search/auto_complete \n Verifying autocomplete without type and  search service with correct parameter and expecting pass response.");
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
	 **/
	@Test(groups = { "sanity", "listSavedSearchWithSortByModifiedOnAsc","NA" })
	public void listSavedSearchWithSortByModifiedOnAsc()
			throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByModifiedOnAsc(hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successfull");
	}

	/**
	 * 12.08.2016 Bhagyasree - Get list of saved search based on sort by
	 * Modified On Descending
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "listSavedSearchWithSortByModifiedOnDsc","NA" })
	public void listSavedSearchWithSortByModifiedOnDsc()
			throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByModifiedOnDsc(hostName);
		// Asserting Response Code
				Assertion.assertEquals(responsebody.getStatus(), 200,
						"Response not successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successfull");
	}

	/**
	 * 12.08.2016 Bhagyasree - Get list of saved search based on sort by Created
	 * On Ascending
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "listSavedSearchWithSortByCreatedOnAsc","NA" })
	public void listSavedSearchWithSortByCreatedOnAsc()
			throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByCreatedOnAsc(hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successfull");
	}

	/**
	 * 12.08.2016 Bhagyasree - Get list of saved search based on sort by Created
	 * On Descending
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "listSavedSearchWithSortByCreatedOnDsc","NA" })
	public void listSavedSearchWithSortByCreatedOnDsc()
			throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByCreatedOnDsc(hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successfull");

	}

	/**
	 * Bhagyasree - Get suggestion when passing Invalid keyword
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/

	@Test(groups = { "sanity", "verifySuggestRequestForInvalidKeyword","NA" })
	public void verifySuggestRequestForInvalidKeyword()
			throws ClientProtocolException, IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.getSuggestForInvalidKeyword(hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successfull");
	}

	/**
	 * Bhagyasree - Get particular saved search by ID that exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "getSavedSearchByIdWithSpace","NA" })
	public void getSavedSearchByIdWithSpace() throws ClientProtocolException,
			IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		suggestConsumer.getSavedSearchById(hostName);
		Response responsebody = suggestConsumer
				.getSavedSearchByIdWithSpace(hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
		String response = responsebody.readEntity(String.class);
		Logging.log("responsebody " + responsebody);
		System.out.println("***** RESPONSE ******" + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successfull");

	}

	/**
	 * Bhagyasree - Create saved search with skill
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "createSavedSearchWithSkill","NA" })
	public void createSavedSearchWithSkill() throws ClientProtocolException,
			IOException {

		System.out.println("Create Saved Search with skill");
		Logging.log("Create Saved Search with skill");
		searchUtil = new SearchUtil();
		SavedSearchDetails inputBean = SearchUtil
				.createSavedSearchInputBeanWithSkill();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);

		// Executes POST request and returns Response
		Response responsebody = suggestConsumer.createSavedSearchWithSkill(
				inputBean, hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
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
	@Test(groups = { "sanity", "createSavedSearchInputBeanWithSkillAndLocation","NA" })
	public void createSavedSearchInputBeanWithSkillAndLocation()
			throws ClientProtocolException, IOException {

		System.out.println("Create Saved Search with skill and location");
		Logging.log("Create Saved Search with skill and location");
		searchUtil = new SearchUtil();
		SavedSearchDetails inputBean = SearchUtil
				.createSavedSearchInputBeanWithSkillAndLocation();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		// Executes POST request and returns Response
		Response responsebody = suggestConsumer
				.createSavedSearchInputBeanWithSkillAndLocation(inputBean,
						hostName);
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
		Logging.log("responsebody " + responsebody);

		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
	}

	/**
	 * Bhagyasree - Create a public saved search with skill
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "createPublicSavedSearchWithSkill","NA" })
	public void createPublicSavedSearchWithSkill()
			throws ClientProtocolException, IOException {

		System.out.println("Create  a public Saved Search with skill");
		Logging.log("Create  a public Saved Search with skill");
		searchUtil = new SearchUtil();
		// Executes get request and returns Response
		SavedSearchDetails inputBean = SearchUtil
				.createPublicSavedSearchInputBeanWithSkill();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		// Executes POST request and returns Response
		Response responsebody = suggestConsumer
				.createPublicSavedSearchWithSkill(inputBean, hostName);
		// Asserting Response Code
				Assertion.assertEquals(responsebody.getStatus(), 200,
						"Response not successful");
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
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
	@Test(groups = { "sanity", "deleteSavedSearchById","NA" })
	public void deleteSavedSearchById() throws ClientProtocolException,
			IOException {
		System.out.println("Delete particular saved search by ID that exist");
		Logging.log("Delete particular saved search by ID that exist");

		/* starts - create saved search before deleting */
		SavedSearchDetails inputBean = SearchUtil
				.createSavedSearchInputBeanWithSkill();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		// Executes POST request and returns Response
		Response createResponsebody = suggestConsumer
				.createSavedSearchWithSkill(inputBean, hostName);
		System.out.println("***** RESPONSE : createResponsebody : ******"
				+ createResponsebody);
		Logging.log("createResponsebody " + createResponsebody);

		String createResponse = createResponsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : createResponse : ******"
				+ createResponse);
		Logging.log("createResponse " + createResponse);

		JSONObject createResponseJson = new JSONObject(createResponse);
		System.out.println("***** RESPONSE : createResponseJson : ******"
				+ createResponseJson);
		JSONObject responseJson = createResponseJson.getJSONObject("response");
		String id = responseJson.getString("id");
		System.out.println("***** RESPONSE : id : ******" + id);
		Logging.log("id " + id);

		/* ends - create saved search before deleting */

		// Executes DELETE request and returns Response
		/* starts - delete saved search */
		Response responsebody = suggestConsumer.deleteSavedSearchById(hostName,
				id);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
		/* ends - delete saved search */
	}

	/**
	 * Bhagyasree - delete particular saved search by ID that doesnt exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "deleteSavedSearchByIdNonExisting","NA" })
	public void deleteSavedSearchByIdNonExisting()
			throws ClientProtocolException, IOException {
		System.out
				.println("Delete particular saved search by ID that doesnt exist");
		Logging.log("Delete particular saved search by ID that doesnt exist");
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes DELETE request and returns Response
		Response responsebody = suggestConsumer
				.deleteSavedSearchByIdNonExisting(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 500,
				"Deleted Successfully");

	}

	@Test(groups = { "sanity", "searchCandidatesWithNoSearchQueryString","NA" })
	public void searchCandidatesWithNoSearchQueryString()
			throws ClientProtocolException, IOException {

		System.out.println("Search candidates with no query string");
		Logging.log("Search candidates with no query string");
		searchUtil = new SearchUtil();
		com.spire.base.service.utils.SearchInputRequest inputBean = SearchUtil
				.searchCandidatesWithNoSearchQueryString();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		// Executes GET request and returns Response
		Response responsebody = suggestConsumer.searchCandidate(inputBean,
				hostName);
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
		Logging.log("responsebody " + responsebody);

		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");

	}

	/**
	 * Bhagyasree - update particular saved search by ID that exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "updateSavedSearchById","NA" })
	public void updateSavedSearchById() throws ClientProtocolException,
			IOException {
		System.out.println("Update particular saved search by ID that exist");
		Logging.log("Update particular saved search by ID that exist");

		/* starts - create saved search before updating */
		SavedSearchDetails inputBean = SearchUtil
				.createSavedSearchInputBeanWithSkill();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);

		Response createResponsebody = suggestConsumer
				.createSavedSearchWithSkill(inputBean, hostName);
		System.out.println("***** RESPONSE : createResponsebody : ******"
				+ createResponsebody);
		Logging.log("createResponsebody " + createResponsebody);

		String createResponse = createResponsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : createResponse : ******"
				+ createResponse);
		Logging.log("createResponse " + createResponse);

		JSONObject createResponseJson = new JSONObject(createResponse);
		System.out.println("***** RESPONSE : createResponseJson : ******"
				+ createResponseJson);
		JSONObject responseJson = createResponseJson.getJSONObject("response");
		String id = responseJson.getString("id");
		System.out.println("***** RESPONSE : id : ******" + id);
		Logging.log("id " + id);

		/* ends - create saved search before updating */
		// Executes PUT request and returns Response
		/* starts - update saved search */
		inputBean = SearchUtil.updateSavedSearchInputBeanWithSkill(inputBean);
		Response updateteResponsebody = suggestConsumer
				.updateSavedSearchWithSkill(inputBean, hostName, id);
		System.out.println("***** RESPONSE : updateteResponsebody : ******"
				+ updateteResponsebody);
		Logging.log("updateteResponsebody " + updateteResponsebody);

		String updateResponse = updateteResponsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : updateResponse : ******"
				+ updateResponse);
		Logging.log("updateResponse " + updateResponse);
		// Asserting Response Code
		Assertion.assertEquals(updateteResponsebody.getStatus(), 200,
				"Response not successful");
		/* ends - update saved search */
	}

	/**
	 * Bhagyasree - update particular saved search by ID that doesnt exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "updateSavedSearchByIdNonExisting","NA" })
	public void updateSavedSearchByIdNonExisting()
			throws ClientProtocolException, IOException {
		System.out
				.println("Update particular saved search by ID that doesnt exist");
		Logging.log("Update particular saved search by ID that doesnt exist");

		/* starts - create saved search before updating */
		SavedSearchDetails inputBean = SearchUtil
				.createSavedSearchInputBeanWithSkill();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		// Executes PUT request and returns Response
		String id = "2233445500";
		/* starts - update saved search */
		inputBean = SearchUtil.updateSavedSearchInputBeanWithSkill(inputBean);
		Response updateteResponsebody = suggestConsumer
				.updateSavedSearchWithSkill(inputBean, hostName, id);
		System.out.println("***** RESPONSE : updateteResponsebody : ******"
				+ updateteResponsebody);
		Logging.log("updateteResponsebody " + updateteResponsebody);
		String updateResponse = updateteResponsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : updateResponse : ******"
				+ updateResponse);
		Logging.log("updateResponse " + updateResponse);
		// Asserting Response Code
		Assertion.assertEquals(updateteResponsebody.getStatus(), 500,
				"Non existent ID updated Succesfully");

	}

	/**
	 * Bhagyasree - Get saved search when passing search text
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/

	@Test(groups = { "sanity", "savedSearchUsingSearchText","NA" })
	public void savedSearchUsingSearchText() throws ClientProtocolException,
			IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes GET request and returns Response
		Response responsebody = suggestConsumer
				.getSavedSearchUsingSearchText(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("RESPONSE >>" + response);
		Logging.log("RESPONSE >>" + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");

	}

	/**
	 * Bhagyasree - Validation on search text in saved search when passing null
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/

	@Test(groups = { "sanity", "validationOnSavedSearchUsingSearchText","NA" })
	public void validationOnSavedSearchUsingSearchText()
			throws ClientProtocolException, IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes GET request and returns Response
		Response responsebody = suggestConsumer
				.validationOnSavedSearchUsingSearchText(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("RESPONSE >>" + response);
		Logging.log("RESPONSE >>" + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 400,
				"Search Text is not empty");

	}

	/**
	 * Bhagyasree - Get saved search when passing partial search text
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/

	@Test(groups = { "sanity", "savedSearchUsingPartialSearchText","NA" })
	public void savedSearchUsingPartialSearchText()
			throws ClientProtocolException, IOException {
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes GET request and returns Response
		Response responsebody = suggestConsumer
				.getSavedSearchUsingPartialSearchText(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("RESPONSE >>" + response);
		Logging.log("RESPONSE >>" + response);
		String skill = "jav";
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");
		Assert.assertTrue(response.contains(skill),
				"Response body doesnt contain the partial text");

	}

	/**
	 *
	 * Author - Bhagyasree Test case description - Search candidates for skill
	 * search
	 * 
	 * 
	 */

	@Test(groups = { "sanity", "getCandidatesFromSavedSearch","NA" },dependsOnGroups={"createPublicSavedSearchWithSkill"})
	public void getCandidatesFromSavedSearch() throws ClientProtocolException,
			IOException {

		System.out.println("Get candidates from saved search");
		Logging.log("Get candidates from saved search");

		// Get input bean
	/*	com.spire.base.service.utils.SearchInput inputBean = SearchUtil
				.getSearchInputBeanWithSkillWithoutPageInfo();
		Logging.log("inputBean " + inputBean);*/
		String inputBean = "{\"searchInput\": {\"searchQueryString\": \"(skill:MySQL or skill:ajax)\",\"searchAttributeMap\": {\"skill\": [\"MySQL\",\"ajax\"]}}}}";
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		System.out.println("****"+savedSearchId);
		// Executes POST request and returns Response
		Response responsebody = suggestConsumer.getCandidatesFromSavedSearch(
				inputBean, hostName,savedSearchId);
		Assertion.assertTrue(responsebody.getStatus() == 200, "Response unsuccessfull, Expected 200 status code");
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
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
	@Test(groups = { "sanity", "createSavedSearchWithExistingSavedSearchName","NA" })
	public void createSavedSearchWithExistingSavedSearchName()
			throws ClientProtocolException, IOException {

		System.out.println("Create a duplicate saved search ");
		Logging.log("Create a duplicate saved search ");
		searchUtil = new SearchUtil();
		// Executes get request and returns Response
		SavedSearchDetails inputBean = SearchUtil
				.createSavedSearchBeanWithExistingSavedSearchName();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		// Executes POST request and returns Response
		Response responsebody = suggestConsumer
				.createSavedSearchWithExistingSavedSearchName(inputBean,
						hostName);
		
		// Executes POST request and returns Response
		Response responsebody1 = suggestConsumer
				.createSavedSearchWithExistingSavedSearchName(inputBean,
						hostName);

		String response = responsebody1.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("response " + response);
		// Asserting Response Code
		Assertion.assertTrue(responsebody1.getStatus() != 200,
				"response code expected not equal to 200 but found as:"
						+ responsebody.getStatus());
	}

}