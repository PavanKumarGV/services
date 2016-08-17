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

	/**
	 * Passing HostName,UserName and Password from the xml.
	 */

	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
		userId = Constants.user_Id;
		password = Constants.password;
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
	@Test(groups = { "sanity", "GetSimillerProfiles" })
	public void GetSimillerProfiles() throws ClientProtocolException,
			IOException {

		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		//suggestConsumer.getSemilarProfiles(hostName);
		Response responsebody = suggestConsumer.getSemilarProfiles(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Assert.assertTrue(response
				.contains(Constants.candidate_Id2));
	}

	/**
	 * Bhagyasree - Get suggestion when passing keyword
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/

	@Test(groups = { "sanity", "verifySuggestRequest" })
	public void verifySuggestRequest() throws ClientProtocolException,
			IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes Get request and returns Response
		Response responsebody = suggestConsumer.getSuggest(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println(" RESPONSE>>" + response);
		// Asserting Response Code
		Assert.assertTrue(response.contains("java"));

	}

	/**
	 * Bhagyasree - Get error code when keyword is blank
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/

	@Test(groups = { "sanity", "verifySuggestValidation" })
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
	@Test(groups = { "sanity", "verifySuggestForSkillwithMultipleWords" })
	public void verifySuggestForSkillwithMultipleWords()
			throws ClientProtocolException, IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes Get request and returns Response
		Response responsebody = suggestConsumer
				.getSuggestForSkillwithMultipleWords(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		// Asserting Response Code
		Assert.assertTrue(response.contains("project planning"));

	}

	/**
	 * Author - Bhagyasree Test case description - Get suggestion when passing
	 * keyword having SpecialCharacters(Like C#, .Net, C++)
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "verifySuggestForSkillwithSpecialCharacter" })
	public void verifySuggestForSkillwithSpecialCharacter()
			throws ClientProtocolException, IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes Get request and returns Response
		Response responsebody = suggestConsumer
				.getSuggestForSkillwithSpecialCharacter(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		// Asserting Response Code
		Assert.assertTrue(response.contains(".net"));

	}

	/**
	 * Vasista - Get -Similler profiles
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "GetSimilarProfilesNegative" })
	public void GetSimilarProfilesNegative() throws ClientProtocolException,
			IOException {

		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		suggestConsumer.getSemilarProfilesNegi(hostName);
		Response responsebody = suggestConsumer.getSemilarProfiles(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Assert.assertTrue(response.contains(" "));

	}
	/**
	 *
	 * Author - Bhagyasree Test case description - Search candidates for skill
	 * search
	 * 
	 * 
	 */

	@Test(groups = { "sanity", "searchCandidatesWithSkill" })
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

	@Test(groups = { "sanity", "searchCandidatesWithSkillAndLocation" })
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
	@Test(groups = { "sanity", "getSavedSearch" })
	public void getSavedSearch() throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		suggestConsumer.getSavedSearch(hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer.getSavedSearch(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");

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
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");

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
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("response " + response);

	}

	/**
	 * priti- Get autocomplete with full skill
	 */
	@Test(groups = { "sanity", "getautocompletewithfullskillRequest" })
	public void getautocompletewithfullskillRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletefullskillsearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.skill),
				"skill not found in the response.");
		Assertion.assertTrue(response.contains("java script"),
				"full skill not found in response");
	}

	/**
	 * priti-Get autocomplete with partial skill
	 */
	@Test(groups = { "sanity", "getautocompletewithpartialskillRequest" })
	public void getautocompletewithpartialskillRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartialskillsearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.skill),
				"skill not found in the response.");
		Assertion.assertTrue(response.contains("ja"),
				"partial skill search not found in response");
	}

	/**
	 * priti- Get autocomplete with full institute
	 */

	@Test(groups = { "sanity", "getautocompletewithfullinstituteRequest" })
	public void getautocompletewithfullinstituteRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletefullinstitutesearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.institute),
				"institute not found in the response.");
		Assertion.assertTrue(response.contains("M.D. University"),
				"full institute not found in response");
	}

	/**
	 * priti- Get autocomplete with partial institute
	 */

	@Test(groups = { "sanity", "getautocompletewithpartialinstituteRequest" })
	public void getautocompletewithpartialinstituteRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartialinstitutesearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.institute),
				"institute not found in the response.");
		Assertion.assertTrue(response.contains("Harvard"),
				"partial institute not found in response");
	}

	/**
	 * priti-Get autocomplete with full education
	 */

	@Test(groups = { "sanity", "getautocompletewithfulleducationRequest" })
	public void getautocompletewithfulleducationRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletefulleducationsearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.education),
				"education not found in the response.");
		Assertion.assertTrue(
				response.contains("Diploma in Computer Applications"),
				"full education not found in response");
	}

	/**
	 * priti-Get autocomplete with partial education
	 */

	@Test(groups = { "sanity", "getautocompletewithpartialeducationRequest" })
	public void getautocompletewithpartialeducationRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartialeducationsearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.education),
				"education not found in the response.");
		Assertion.assertTrue(response.contains("Diploma"),
				"partial education not found in response");
	}

	/**
	 * priti-Get autocomplete with full employer
	 */

	@Test(groups = { "sanity", "getautocompletewithfullemployerRequest" })
	public void getautocompletewithfullemployerRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletefullemployersearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.employer),
				"employer not found in the response.");
		Assertion.assertTrue(response.contains("WIPRO TECHNOLOGIES Bangalore"),
				"full employer not found in response");
	}

	/**
	 * priti-Get autocomplete with partial employer
	 */

	@Test(groups = { "sanity", "getautocompletepartialemployerRequest" })
	public void getautocompletepartialemployerRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartialemployersearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.employer),
				"employer not found in the response.");
		Assertion.assertTrue(response.contains("WIPRO"),
				"partial employer not found in response");
	}

	/**
	 * priti-Get autocomplete with full location
	 */
	@Test(groups = { "sanity", "getautocompletfulllocationRequest" })
	public void getautocompletfulllocationRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletefulllocationsearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.location),
				"location not found in the response.");
		Assertion.assertTrue(response.contains("Bangalore"),
				"full location not found in response");
	}

	/**
	 * priti-Get autocomplete with partial location
	 */
	@Test(groups = { "sanity", "getautocompletpartiallocationRequest" })
	public void getautocompletpartiallocationRequest() {
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

		Assertion.assertTrue(response.contains(Constants.location),
				"location not found in the response.");
		Assertion.assertTrue(response.contains("ba"),
				"partial location not found in response");
	}

	/**
	 * priti-Get autocomplete with full sourcetype
	 */
	@Test(groups = { "sanity", "getautocompletfullsourcetypeRequest" })
	public void getautocompletfullsourcetypeRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletefullsourcetypesearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.sourcetype),
				"sourcetype not found in the response.");
		Assertion.assertTrue(response.contains("Referral - Employee"),
				"full sourcetype not found in response");
	}

	/**
	 * priti-Get autocomplete with partial sourcetype
	 */
	@Test(groups = { "sanity", "getautocompletpartialsourcetypeRequest" })
	public void getautocompletpartialsourcetypeRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartialsourcetypesearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.sourcetype),
				"sourcetype not found in the response.");
		Assertion.assertTrue(response.contains("Referral"),
				"partial sourcetype not found in response");
	}

	/**
	 * priti-Get autocomplete with full sourcename
	 */
	@Test(groups = { "sanity", "getautocompletfullsourcenameRequest" })
	public void getautocompletfullsourcenameRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletefullsourcenamesearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.sourcename),
				"sourcename not found in the response.");
		Assertion.assertTrue(response.contains("Media - Alumni"),
				"full sourcename not found in response");
	}

	/**
	 * priti-Get autocomplete with partial sourcename
	 */
	@Test(groups = { "sanity", "getautocompletpartialsourcenameRequest" })
	public void getautocompletpartialsourcenameRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletepartialsourcenamesearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.sourcename),
				"sourcename not found in the response.");
		Assertion.assertTrue(response.contains("Alumni"),
				"partial sourcename not found in response");
	}

	/**
	 * priti-Get autocomplete with full status
	 */
	@Test(groups = { "sanity", "getautocompletfullstatusRequest" })
	public void getautocompletfullstatusRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletefullstatussearch(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.status),
				"status not found in the response.");
		Assertion.assertTrue(response.contains("Hr Interviewed"),
				"full status not found in response");
	}

	/**
	 * priti-Get autocomplete with partial status
	 */
	@Test(groups = { "sanity", "getautocompletpartialstatusRequest" })
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

		Assertion.assertTrue(response.contains(Constants.status),
				"status not found in the response.");
		Assertion.assertTrue(response.contains("Interviewed"),
				"partial status not found in response");
	}

	/**
	 * priti-Get autocomplete without keyword
	 */

	@Test(groups = { "sanity", "getautocompletewithoutkeywordRequest" })
	public void getautocompletewithoutkeywordRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletewithoutkey(hostName);
		Assertion.assertEquals(
				200,
				responsebody.getStatus(),
				"Response expected 200 but found as:"
						+ responsebody.getStatus());
		String response = responsebody.readEntity(String.class);

		Assertion.assertTrue(response.contains(Constants.skill),
				"skill not found in the response.");

	}

	/**
	 * priti-Get autocomplete without type
	 */

	@Test(groups = { "sanity", "getautocompletewithouttypeRequest" })
	public void getautocompletewithouttypeRequest() {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getautocompletewithouttype(hostName);
		// Assertion.assertEquals(200, responsebody.getStatus(),
		// "Response expected 200 but found as:"+responsebody.getStatus());
		Assertion.assertTrue(responsebody.getStatus() != 200,
				"response is unsuccessfull");
		String response = responsebody.readEntity(String.class);

		Assertion
				.assertTrue(response
						.contains("Search input cannot be null or empty"),
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
	@Test(groups = { "sanity", "listSavedSearchWithSortByModifiedOnAsc" })
	public void listSavedSearchWithSortByModifiedOnAsc()
			throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByModifiedOnAsc(hostName);
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
	@Test(groups = { "sanity", "listSavedSearchWithSortByModifiedOnDsc" })
	public void listSavedSearchWithSortByModifiedOnDsc()
			throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByModifiedOnDsc(hostName);
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
	@Test(groups = { "sanity", "listSavedSearchWithSortByCreatedOnAsc" })
	public void listSavedSearchWithSortByCreatedOnAsc()
			throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByCreatedOnAsc(hostName);
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
	@Test(groups = { "sanity", "listSavedSearchWithSortByCreatedOnDsc" })
	public void listSavedSearchWithSortByCreatedOnDsc()
			throws ClientProtocolException, IOException {

		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByCreatedOnDsc(hostName);
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

	@Test(groups = { "sanity", "verifySuggestRequestForInvalidKeyword" })
	public void verifySuggestRequestForInvalidKeyword()
			throws ClientProtocolException, IOException {
		SearchResourcesConsumer suggestConsumer = null;
		// Get authentication token
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		// Executes get request and returns Response
		Response responsebody = suggestConsumer
				.getSuggestForInvalidKeyword(hostName);
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
	@Test(groups = { "sanity", "getSavedSearchByIdWithSpace" })
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
	@Test(groups = { "sanity", "createSavedSearchWithSkill" })
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
	 * Bhagyasree - Create saved search with skill and location
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "createSavedSearchInputBeanWithSkillAndLocation" })
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
	@Test(groups = { "sanity", "createPublicSavedSearchWithSkill" })
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
	 * Bhagyasree - delete particular saved search by ID that exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "deleteSavedSearchById" })
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
	@Test(groups = { "sanity", "deleteSavedSearchByIdNonExisting" })
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

	@Test(groups = { "sanity", "searchCandidatesWithNoSearchQueryString" })
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
	@Test(groups = { "sanity", "updateSavedSearchById" })
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
	@Test(groups = { "sanity", "updateSavedSearchByIdNonExisting" })
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

	@Test(groups = { "sanity", "savedSearchUsingSearchText" })
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

	@Test(groups = { "sanity", "validationOnSavedSearchUsingSearchText" })
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

	@Test(groups = { "sanity", "savedSearchUsingPartialSearchText" })
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

	@Test(groups = { "sanity", "getCandidatesFromSavedSearch" })
	public void getCandidatesFromSavedSearch() throws ClientProtocolException,
			IOException {

		System.out.println("Get candidates from saved search");
		Logging.log("Get candidates from saved search");

		// Get input bean
		com.spire.base.service.utils.SearchInput inputBean = SearchUtil
				.getSearchInputBeanWithSkillWithoutPageInfo();
		Logging.log("inputBean " + inputBean);
		// Get authentication token
		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);
		// Executes POST request and returns Response
		Response responsebody = suggestConsumer.getCandidatesFromSavedSearch(
				inputBean, hostName);
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
	@Test(groups = { "sanity", "createSavedSearchWithExistingSavedSearchName" })
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