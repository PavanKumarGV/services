package com.spire.searchResources;

import org.testng.annotations.Test;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import spire.talent.gi.beans.SavedSearchDetails;
import spire.talent.gi.beans.SearchInput;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.Constants;
import com.spire.base.service.utils.SearchUtil;
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer.getSuggest(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		suggestConsumer.suggestValidation(hostName);

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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getSuggestForSkillwithMultipleWords(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getSuggestForSkillwithSpecialCharacter(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Assert.assertTrue(response.contains(".net"));

	}

	/**
	 * Vasista - Get -Similler profiles
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "GetSimillerProfilesNegetive" })
	public void GetSimillerProfilesNegetive() throws ClientProtocolException,
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
		searchUtil = new SearchUtil();
		com.spire.base.service.utils.SearchInputRequest inputBean = searchUtil
				.getSearchInputBeanWithSkill();
		Logging.log("inputBean " + inputBean);

		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);

		Response responsebody = suggestConsumer.searchCandidate(inputBean,
				hostName);
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
		Logging.log("responsebody " + responsebody);

		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("response " + response);

		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");

		/*
		 * List<CandidateSummary> skills = SearchResourcesConsumer for
		 * (CandidateSummary skill : skills) { Logging.log("Skills " +
		 * skill.getSkills());
		 * AssertJUnit.assertTrue(skill.getSkills().contains(skillMap),
		 * "Searched skill is not same as candidate displaying skill");
		 */
	}

	@Test(groups = { "sanity", "searchCandidatesWithSkillAndLocation" })
	public void searchCandidatesWithSkillAndLocation()
			throws ClientProtocolException, IOException {

		System.out.println("Search candidates with skill and location");
		Logging.log("Search candidates with skill and location");
		searchUtil = new SearchUtil();
		com.spire.base.service.utils.SearchInputRequest inputBean = searchUtil
				.getSearchInputBeanWithSkillAndLocation();
		Logging.log("inputBean " + inputBean);

		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);

		Response responsebody = suggestConsumer.searchCandidate(inputBean,
				hostName);
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
		Logging.log("responsebody " + responsebody);

		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("response " + response);

		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");

		/*
		 * List<CandidateSummary> skills = SearchResourcesConsumer for
		 * (CandidateSummary skill : skills) { Logging.log("Skills " +
		 * skill.getSkills());
		 * AssertJUnit.assertTrue(skill.getSkills().contains(skillMap),
		 * "Searched skill is not same as candidate displaying skill");
		 */
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		suggestConsumer.getSavedSearch(hostName);
		Response responsebody = suggestConsumer.getSavedSearch(hostName);
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		suggestConsumer.getSavedSearchById(hostName);
		Response responsebody = suggestConsumer.getSavedSearchById(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		suggestConsumer.getSavedSearchByNonExistingId(hostName);
		Response responsebody = suggestConsumer
				.getSavedSearchByNonExistingId(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("response " + response);
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successfull");
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByModifiedOnAsc(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByModifiedOnDsc(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByCreatedOnAsc(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.listSavedSearchWithSortByCreatedOnDsc(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer
				.getSuggestForInvalidKeyword(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("responsebody " + responsebody);
		Logging.log("response " + response);
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
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		suggestConsumer.getSavedSearchById(hostName);
		Response responsebody = suggestConsumer
				.getSavedSearchByIdWithSpace(hostName);
		String response = responsebody.readEntity(String.class);
		Logging.log("responsebody " + responsebody);
		System.out.println("***** RESPONSE ******" + response);
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

		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);

		Response responsebody = suggestConsumer.createSavedSearchWithSkill(inputBean,
				hostName);
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
		Logging.log("responsebody " + responsebody);

		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("response " + response);

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
	public void createSavedSearchInputBeanWithSkillAndLocation() throws ClientProtocolException,
			IOException {

		System.out.println("Create Saved Search with skill and location");
		Logging.log("Create Saved Search with skill and location");
		searchUtil = new SearchUtil();
		SavedSearchDetails inputBean = SearchUtil
				.createSavedSearchInputBeanWithSkillAndLocation();
		Logging.log("inputBean " + inputBean);

		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);

		Response responsebody = suggestConsumer.createSavedSearchInputBeanWithSkillAndLocation(inputBean,
				hostName);
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
		Logging.log("responsebody " + responsebody);

		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("response " + response);

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
	public void createPublicSavedSearchWithSkill() throws ClientProtocolException,
			IOException {

		System.out.println("Create  a public Saved Search with skill");
		Logging.log("Create  a public Saved Search with skill");
		searchUtil = new SearchUtil();
		SavedSearchDetails inputBean = SearchUtil
				.createPublicSavedSearchInputBeanWithSkill();
		Logging.log("inputBean " + inputBean);

		SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(
				userId, password, hostName);

		Response responsebody = suggestConsumer.createPublicSavedSearchWithSkill(inputBean, hostName);
		System.out.println("***** RESPONSE : responsebody : ******"
				+ responsebody);
		Logging.log("responsebody " + responsebody);

		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******" + response);
		Logging.log("response " + response);

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
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer.deleteSavedSearchById(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("response " + response);
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");

	}
	
	/**
	 * Bhagyasree - delete particular saved search by ID that doesnt exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "deleteSavedSearchByIdNonExisting" })
	public void deleteSavedSearchByIdNonExisting() throws ClientProtocolException,
			IOException {
		System.out.println("Delete particular saved search by ID that doesnt exist");
		Logging.log("Delete particular saved search by ID that doesnt exist");
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password,
				hostName);
		Response responsebody = suggestConsumer.deleteSavedSearchByIdNonExisting(hostName);
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******" + response);
		Logging.log("response " + response);
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"Response not successful");

	}
	
	
}