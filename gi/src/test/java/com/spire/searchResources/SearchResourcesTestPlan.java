package com.spire.searchResources;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.common.inject.matcher.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.service.consumers.SearchResourcesConsumer;

public class SearchResourcesTestPlan extends TestPlan {

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
		userId = (String) "tester@logica.com";
		password = (String) "spire@123";
		//userId = (String) ContextManager.getThreadContext().getUserid();
		//password = (String) ContextManager.getThreadContext().getPassword();
		Logging.log("Start :: Login with Username: " + userId + "Password: "
				+ password + "and hostName: " + hostName);
		
	}


	/**
	 *  Vasista - Get -Similler profiles 
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/  
	@Test(groups = { "sanity", "GetSimillerProfiles" })
	public void GetSimillerProfiles() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSemilarProfiles(hostName);  
		Response responsebody =suggestConsumer.getSemilarProfiles(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("6002:6005:c7133f48171543998a8ad4190e1353eb"));
	}
	
	
	/**
	 *  Bhagyasree - Get suggestion when passing keyword
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	
	@Test(groups = { "sanity" , "verifySuggestRequest" })
	public void verifySuggestRequest()throws ClientProtocolException,
	IOException {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody =suggestConsumer.getSuggest(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("java"));
		
				
	}
	
	/**
	 *  Bhagyasree - Get error code when keyword is blank
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	
	@Test(groups = { "sanity" , "verifySuggestValidation" })
	public void verifySuggestValidation()throws ClientProtocolException,
	IOException {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.suggestValidation(hostName);  
		
				
	}
	
	/**
	 *  Author - Bhagyasree 
	 *  Test case description - Get suggestion when passing keyword having multiple words(Like project planning, project management)
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity" , "verifySuggestForSkillwithMultipleWords" })
	public void verifySuggestForSkillwithMultipleWords()throws ClientProtocolException,
	IOException {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody =suggestConsumer.getSuggestForSkillwithMultipleWords(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("project planning"));
		
				
	}
	
	/**
	 *  Author - Bhagyasree 
	 *  Test case description - Get suggestion when passing keyword having SpecialCharacters(Like C#, .Net, C++)
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity" , "verifySuggestForSkillwithSpecialCharacter" })
	public void verifySuggestForSkillwithSpecialCharacter()throws ClientProtocolException,
	IOException {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody =suggestConsumer.getSuggestForSkillwithSpecialCharacter(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains(".net"));
		
				
	}

	/**
	 *  Vasista - Get -Similler profiles 
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/  
	@Test(groups = { "sanity", "GetSimillerProfilesNegetive" })
	public void GetSimillerProfilesNegetive() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSemilarProfilesNegi(hostName);  
		Response responsebody =suggestConsumer.getSemilarProfiles(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains(" "));
	
	}
 /**
     *
     *  Author - Bhagyasree 
	 *  Test case description - Search candidates for skill search
	 * 
	 * 
     */

  
    
	@Test(groups = {"sanity", "searchCandidatesWithSkill"})
    public void searchCandidatesWithSkill() throws ClientProtocolException,
	IOException  {

        System.out.println("Search candidates with skill");
        Logging.log("Search candidates with skill");
        searchUtil = new SearchUtil();
        com.spire.base.service.utils.SearchInputRequest inputBean = searchUtil.getSearchInputBeanWithSkill();
        Logging.log("inputBean " + inputBean);
        
        SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
        
		Response responsebody =suggestConsumer.searchCandidate(inputBean, hostName);
		System.out.println("***** RESPONSE : responsebody : ******"+responsebody);
		Logging.log("responsebody " + responsebody);
		
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******"+response);
		Logging.log("response " + response);
		
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
		
       /*List<CandidateSummary> skills = SearchResourcesConsumer
        for (CandidateSummary skill : skills) {
            Logging.log("Skills " + skill.getSkills());
            AssertJUnit.assertTrue(skill.getSkills().contains(skillMap), "Searched skill is not same as candidate displaying skill");
       */
		}
	
	@Test(groups = {"sanity", "searchCandidatesWithSkillAndLocation"})
    public void searchCandidatesWithSkillAndLocation() throws ClientProtocolException,
	IOException  {

        System.out.println("Search candidates with skill and location");
        Logging.log("Search candidates with skill and location");
        searchUtil = new SearchUtil();
        com.spire.base.service.utils.SearchInputRequest inputBean = searchUtil.getSearchInputBeanWithSkillAndLocation();
        Logging.log("inputBean " + inputBean);
        
        SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
        
		Response responsebody =suggestConsumer.searchCandidate(inputBean, hostName);
		System.out.println("***** RESPONSE : responsebody : ******"+responsebody);
		Logging.log("responsebody " + responsebody);
		
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******"+response);
		Logging.log("response " + response);
		
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
		
       /*List<CandidateSummary> skills = SearchResourcesConsumer
        for (CandidateSummary skill : skills) {
            Logging.log("Skills " + skill.getSkills());
            AssertJUnit.assertTrue(skill.getSkills().contains(skillMap), "Searched skill is not same as candidate displaying skill");
       */
		}
	
	
	/**
	 *  Bhagyasree - Get list of saved search
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "getSavedSearch" })
	public void getSavedSearch() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSavedSearch(hostName);  
		Response responsebody =suggestConsumer.getSavedSearch(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		
	}
	
	/**
	 *  Bhagyasree - Get particular saved search by ID that exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "getSavedSearchById" })
	public void getSavedSearchById() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSavedSearchById(hostName);  
		Response responsebody =suggestConsumer.getSavedSearchById(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
	//	Response response1 = sugestConsumer.readEntity(inputBean, hostName);
	//	Assertion.assertEquals(response1.getStatus(), 200, "Response not successfull");
		
	}
	/**
	 *  Bhagyasree - Get particular saved search by ID that doesnt not exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "getSavedSearchByNonExistingId" })
	public void getSavedSearchByNonExistingId() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSavedSearchByNonExistingId(hostName);  
		Response responsebody =suggestConsumer.getSavedSearchByNonExistingId(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		
	}


	
}