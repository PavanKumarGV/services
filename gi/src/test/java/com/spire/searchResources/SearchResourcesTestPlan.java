package com.spire.searchResources;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
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
		candConsumer = new SearchResourcesConsumer(userId, password, hostName);
		candConsumer.getSemilarProfiles(hostName);  

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
	

	
}