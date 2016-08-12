package com.spire.service.consumers;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;

import spire.commons.userservice.bean.LoginResponseBean;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

public class SearchResourcesConsumer extends BaseServiceConsumerNew {

	String endPointURL = getServiceEndPoint("SEARCH_CANDI_SAVED_SEARCH");
	String endPointURL1 = getServiceEndPoint("SIMILAR_PROFILES").replace(":",
			"%3A");
	String endPointURL2 = getServiceEndPoint("SIMILAR_PROFILES1").replace(":",
			"%3A");
	String endPointURLSuggest = getServiceEndPoint("SEARCH_SUGGEST");
	String endPointURLSuggestValidation = getServiceEndPoint("SUGGEST_VALIDATION");
	String endPointURLSuggestForSkillwithMultipleWords = getServiceEndPoint(
			"SEARCH_SUGGESTFORMULTIPLEWORDS").replace(" ", "%20");
	String endPointURLSuggestForSkillwithSpecialCharacter = getServiceEndPoint("SEARCH_SUGGESTWITHSPECIALCHARACTER");
        String searchCandidateEndPointUrl = getServiceEndPoint("SEARCH_CANDIDATE");
	String endPointURLSavedSearch = getServiceEndPoint("SAVED_SEARCH");
	String endPointURLSavedSearchById = getServiceEndPoint("SAVED_SEARCHBYID");
	String endPointURLSavedSearchByIdNonExistent = getServiceEndPoint("SAVED_SEARCHBYIDNONEXISTENT");

	public SearchResourcesConsumer(String username, String password,
			String hostName) {
		Logging.log("Inside of Login");
		System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
	}

	public Response getSemilarProfiles(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL1.replaceAll("hostAddress",
				hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() == 200) {
			System.out.println("********** pass **************");
		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;

	}

	public Response getSuggest(String hostName) throws ClientProtocolException,
			IOException {

		String serviceEndPoint = endPointURLSuggest.replaceAll("hostAddress",
				hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		if (response.getStatus() == 200) {
			System.out.println("***** PASS ******RESPONSE CODE >>"
					+ response.getStatus());

		} else {
			Assert.fail();
			Logging.log("Response Code >>" + response.getStatus());
		}
		Logging.log("Response Code >>" + response.getStatus());
		return response;

	}

	public void suggestValidation(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSuggestValidation.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
			if (responsebody.getStatus() != 200) {
			System.out.println("********** PASS **************");
			Logging.log("Response Code >>" + responsebody.getStatus());
		} else {
			Assert.fail();
			System.out.println("********** FAIL **************");
			Logging.log("Response Code >>" + responsebody.getStatus());
		}

	}

	public Response getSuggestForSkillwithMultipleWords(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPointM = endPointURLSuggestForSkillwithMultipleWords
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointM);
		Response responseM = executeGET(serviceEndPointM);
		if (responseM.getStatus() == 200) {
			System.out.println("***** PASS ******RESPONSE CODE >>"
					+ responseM.getStatus());

		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + responseM.getStatus());
		return responseM;

	}

	public Response getSuggestForSkillwithSpecialCharacter(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPointS = endPointURLSuggestForSkillwithSpecialCharacter
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointS);
		Response responseS = executeGET(serviceEndPointS);
		if (responseS.getStatus() == 200) {
			System.out.println("***** PASS ******RESPONSE CODE >>"
					+ responseS.getStatus());

		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + responseS.getStatus());
		return responseS;

	}
	
	/*
	 * get Similar Profile with invalid  Test case
	 */
	
	public Response getSemilarProfilesNegi(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL2.replaceAll("hostAddress",
				hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		Logging.log("Response " +response1);
		if (response1.getStatus() == 204) {
			System.out.println("********** pass **************");
		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
public Response searchCandidate(com.spire.base.service.utils.SearchInputRequest inputBean,String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPointC = searchCandidateEndPointUrl
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointC);
		
		Entity<com.spire.base.service.utils.SearchInputRequest> searchInputRequest = Entity.entity(inputBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPointC, searchInputRequest);
		return response;

	}
	
	public Response getSavedSearch(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearch
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("***** PASS ******RESPONSE CODE >>"
					+ responseS.getStatus());

		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + responseS.getStatus());
		return responseS;

	}	
	
	public Response getSavedSearchById(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchById
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("***** PASS ******RESPONSE CODE >>"
					+ responseS.getStatus());

		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + responseS.getStatus());
		return responseS;

	}	
	
	public Response getSavedSearchByNonExistingId(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchByIdNonExistent
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("***** PASS ******RESPONSE CODE >>"
					+ responseS.getStatus());

		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + responseS.getStatus());
		return responseS;

	}	


}
