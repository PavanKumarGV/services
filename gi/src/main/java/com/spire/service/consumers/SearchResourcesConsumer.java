package com.spire.service.consumers;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;


import com.spire.base.service.utils.SavedSearchDetails;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.base.service.Constants;

public class SearchResourcesConsumer extends BaseServiceConsumerNew {


	String endPointURL = getServiceEndPoint("SEARCH_CANDI_SAVED_SEARCH");
	String endPointURL1 = getServiceEndPoint("SIMILAR_PROFILES")+getServiceEndPoint("candidate_Id2");
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

	String endPointURLListSavedSearchWithSortByModifiedOnAsc = getServiceEndPoint("SAVED_SEARCH_LIST_SORT_BY_MODIFIED_ON_ASC");
	String endPointURLListSavedSearchWithSortByModifiedOnDsc = getServiceEndPoint("SAVED_SEARCH_LIST_SORT_BY_MODIFIED_ON_DSC");
	String endPointURLListSavedSearchWithSortByCreatedOnAsc = getServiceEndPoint("SAVED_SEARCH_LIST_SORT_BY_CREATED_ON_ASC");
	String endPointURLListSavedSearchWithSortByCreatedOnDsc = getServiceEndPoint("SAVED_SEARCH_LIST_SORT_BY_MODIFIED_ON_DSC");
	String endPointURLSuggestForInvalidKeyword = getServiceEndPoint("SEARCH_SUGGESTFORINVALIDKEYWORD");
	String endPointURLSavedSearchByIdWithSpace = getServiceEndPoint(
			"SAVED_SEARCHBYIDWITHSPACE").replace(" ", "%20");;
	String createSavedSearchCandidateEndPointUrl = getServiceEndPoint("CREATE_SAVEDSEARCH");
	String endPointURLSavedSearchByIdNonExisting = getServiceEndPoint("DELETE_SAVED_SEARCHBYIDNONEXISTING");
	String createSavedSearchInputBeanWithSkillAndLocationEndPointUrl = getServiceEndPoint("CREATE_SAVEDSEARCHWITHSKILLLOCATION");
	String createPublicSavedSearchWithSkill = getServiceEndPoint("CREATE_PUBLICSAVEDSEARCH");
	String EndPointUrlSearchCandidatesWithNoSearchQueryString = getServiceEndPoint("SEARCHCANDIDATE_NOQUERY");
	String endPointURLDeleteSavedSearchById = getServiceEndPoint("DELETE_SAVED_SEARCHBYID");
	String endpointURLautocomplete = getServiceEndPoint("AUTO_COMPLETE");
	String updateSavedSearchCandidateEndPointUrl = getServiceEndPoint("UPDATE_SAVED_SEARCHBYID");
	String endPointURLSavedSearchUsingSearchText = getServiceEndPoint("SAVEDSEARCH_SEARCHTEXT");
	String EndPointUrlGetCandidatesFromSavedSearch = getServiceEndPoint("GET_CANDIDATE_SAVEDSEARCH");

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
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
	/*	if (response1.getStatus() == 200) {
			System.out.println("********** pass **************");
		} else {
			Assert.fail();
		}*/
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
			System.out.println("RESPONSE CODE >>" + response.getStatus());

		} else {
			Assert.fail();
			Logging.log("Response Code >>" + response.getStatus());
		}
		Logging.log("Response Code >>" + response.getStatus());
		return response;

	}

	public Response suggestValidation(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSuggestValidation.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		Response responsebody = executeGET(serviceEndPoint);
		if (responsebody.getStatus() != 200) {
			System.out.println("RESPONSE CODE >>" + responsebody.getStatus());
			Logging.log("Response Code >>" + responsebody.getStatus());
		} else {
			Assert.fail();
			System.out.println("********** FAIL **************");
		}
		return response;
	}

	public Response getSuggestForSkillwithMultipleWords(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPointM = endPointURLSuggestForSkillwithMultipleWords
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointM);
		Response responseM = executeGET(serviceEndPointM);
		if (responseM.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + responseM.getStatus());
			Logging.log("Response Code >>" + responseM.getStatus());
		} else {
			Logging.log("Response Code >>" + responseM.getStatus());
			Assert.fail();
		}

		return responseM;

	}

	public Response getSuggestForSkillwithSpecialCharacter(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPointS = endPointURLSuggestForSkillwithSpecialCharacter
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointS);
		Response responseS = executeGET(serviceEndPointS);
		if (responseS.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());
			Logging.log("Response Code >>" + responseS.getStatus());
		} else {
			Logging.log("Response Code >>" + responseS.getStatus());
			Assert.fail();
		}

		return responseS;

	}

	/*
	 * get Similar Profile with invalid Test case
	 */

	public Response getSemilarProfilesNegi(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL2.replaceAll("hostAddress",
				hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		Logging.log("Response " + response1);
		if (response1.getStatus() == 200) {
			System.out.println("********** Fail **************");
			Logging.log("Response Code >>" + response1.getStatus());
			Assert.fail();
		} else {
			Logging.log("Response Code >>" + response1.getStatus());
			System.out.println("********** pass **************");
		}

		return response1;
	}

	public Response searchCandidate(
			com.spire.base.service.utils.SearchInputRequest inputBean,
			String hostName) throws ClientProtocolException, IOException {

		String serviceEndPointC = searchCandidateEndPointUrl.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointC);

		Entity<com.spire.base.service.utils.SearchInputRequest> searchInputRequest = Entity
				.entity(inputBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPointC, searchInputRequest);
		if (response.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + response.getStatus());
			Logging.log("Response Code >>" + response.getStatus());
		} else {
			Logging.log("Response Code >>" + response.getStatus());
			Assert.fail();

		}

		return response;

	}

	/**
	 * Bhagyasree 13-08-16
	 */
	public Response getSavedSearch(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearch.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());
			Logging.log("Response Code >>" + responseS.getStatus());
		} else {
			Logging.log("Response Code >>" + responseS.getStatus());
			Assert.fail();

		}

		return responseS;

	}

	public Response getSavedSearchById(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchById.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());
			Logging.log("Response Code >>" + responseS.getStatus());
		} else {
			Logging.log("Response Code >>" + responseS.getStatus());
			Assert.fail();
		}

		return responseS;

	}

	public Response getSavedSearchByNonExistingId(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchByIdNonExistent
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() != 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());
			Logging.log("Response Code >>" + responseS.getStatus());
		} else {
			Logging.log("Response Code >>" + responseS.getStatus());
			Assert.fail();
		}

		return responseS;

	}

	public Response listSavedSearchWithSortByModifiedOnAsc(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLListSavedSearchWithSortByModifiedOnAsc
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());
			Logging.log("Response Code >>" + responseS.getStatus());
		} else {
			Logging.log("Response Code >>" + responseS.getStatus());
			Assert.fail();
		}

		return responseS;

	}

	public Response listSavedSearchWithSortByModifiedOnDsc(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLListSavedSearchWithSortByModifiedOnDsc
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());
			Logging.log("Response Code >>" + responseS.getStatus());
		} else {
			Logging.log("Response Code >>" + responseS.getStatus());
			Assert.fail();
		}

		return responseS;

	}

	public Response listSavedSearchWithSortByCreatedOnAsc(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLListSavedSearchWithSortByCreatedOnAsc
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());
			Logging.log("	Response Code >>" + responseS.getStatus());
		} else {
			Logging.log("	Response Code >>" + responseS.getStatus());
			Assert.fail();

		}

		return responseS;

	}

	public Response listSavedSearchWithSortByCreatedOnDsc(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLListSavedSearchWithSortByCreatedOnDsc
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());
			Logging.log("Response Code >>" + responseS.getStatus());
		} else {
			Logging.log("Response Code >>" + responseS.getStatus());
			Assert.fail();

		}

		return responseS;

	}

	public Response getSuggestForInvalidKeyword(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURLSuggestForInvalidKeyword
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		if (response.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + response.getStatus());
			Logging.log("Response Code >>" + response.getStatus());
		} else {
			Logging.log("Response Code >>" + response.getStatus());
			Assert.fail();

		}
		return response;

	}

	/**
	 * priti 13-08-16
	 */
	public Response getautocompletefullskillsearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("full_skill_to_search") + "&type=" + Constants.skill;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response getautocompletepartialskillsearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("partial_Skill_to_search")
				+ "&type="
				+ Constants.skill;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletefullinstitutesearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("full_institute_to_search")
				+ "&type="
				+ Constants.institute;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletepartialinstitutesearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("partial_institute_to_search")
				+ "&type="
				+ Constants.institute;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletefulleducationsearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("full_education_to_search")
				+ "&type="
				+ Constants.education;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletepartialeducationsearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("partial_education_to_search")
				+ "&type="
				+ Constants.education;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletefullemployersearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("full_employer_to_search")
				+ "&type="
				+ Constants.employer;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletepartialemployersearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("partial_employer_to_search")
				+ "&type="
				+ Constants.employer;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletefulllocationsearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("full_location_to_search")
				+ "&type="
				+ Constants.location;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletepartiallocationsearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("partial_location_to_search")
				+ "&type="
				+ Constants.location;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletefullsourcetypesearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("full_sourcetype_to_search")
				+ "&type="
				+ Constants.sourcetype;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletepartialsourcetypesearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("partial_sourcetype_to_search")
				+ "&type="
				+ Constants.sourcetype;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletefullsourcenamesearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("full_sourcename_to_search")
				+ "&type="
				+ Constants.sourcename;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletepartialsourcenamesearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("partial_sourcename_to_search")
				+ "&type="
				+ Constants.sourcename;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletefullstatussearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("full_status_to_search") + "&type=" + Constants.status;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletepartialstatussearch(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint(".partial_status_to_search")
				+ "&type="
				+ Constants.status;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getautocompletewithouttype(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName)
				+ "?keyword="
				+ getServiceEndPoint("partial_status_to_search");
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response getautocompletewithoutkey(String hostName) {
		String serviceEndPoint = endpointURLautocomplete.replaceAll(
				"hostAddress", hostName) + "?type=" + Constants.skill;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response getSavedSearchByIdWithSpace(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchByIdWithSpace
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeGET(serviceEndPoint);
		if (responseS.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());

		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + responseS.getStatus());
		return responseS;

	}

	public Response createSavedSearchWithSkill(com.spire.base.service.utils.SavedSearchDetails inputBean,
			String hostName) throws ClientProtocolException, IOException {

		String serviceEndPointC = createSavedSearchCandidateEndPointUrl
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointC);

		Entity<SavedSearchDetails> savedSearchDetails = Entity.entity(
				inputBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPointC, savedSearchDetails);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response createSavedSearchInputBeanWithSkillAndLocation(
			SavedSearchDetails inputBean, String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPointC = createSavedSearchInputBeanWithSkillAndLocationEndPointUrl
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointC);

		Entity<SavedSearchDetails> searchInputRequest = Entity.entity(
				inputBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPointC, searchInputRequest);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response createPublicSavedSearchWithSkill(
			SavedSearchDetails inputBean, String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPointC = createPublicSavedSearchWithSkill.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointC);

		Entity<SavedSearchDetails> searchInput = Entity.entity(inputBean,
				MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPointC, searchInput);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response deleteSavedSearchById(String hostName, String id)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLDeleteSavedSearchById.replaceAll(
				"hostAddress", hostName);
		serviceEndPoint = serviceEndPoint + id;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);

		Response responseS = executeDELETE(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + responseS.getStatus());
		Logging.log("RESPONSE CODE >>" + responseS.getStatus());
		return responseS;

	}

	public Response deleteSavedSearchByIdNonExisting(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchByIdNonExisting
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response responseS = executeDELETE(serviceEndPoint);
		if (responseS.getStatus() != 200) {
			System.out.println("RESPONSE CODE >>" + responseS.getStatus());

		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + responseS.getStatus());
		return responseS;

	}

	/*
	 * 
	 * Bhagyasree - 14.08.2016
	 */

	public Response searchCandidatesWithNoSearchQueryString(
			com.spire.base.service.utils.SearchInputRequest inputBean,
			String hostName) throws ClientProtocolException, IOException {

		String serviceEndPointC = EndPointUrlSearchCandidatesWithNoSearchQueryString
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointC);

		Entity<com.spire.base.service.utils.SearchInputRequest> searchInputRequest = Entity
				.entity(inputBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPointC, searchInputRequest);
		if (response.getStatus() != 200) {
			System.out.println("RESPONSE CODE >>" + response.getStatus());

		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + response.getStatus());
		return response;

	}

	public Response updateSavedSearchWithSkill(SavedSearchDetails inputBean,
			String hostName, String id) throws ClientProtocolException,
			IOException {

		String serviceEndPoint = updateSavedSearchCandidateEndPointUrl
				.replaceAll("hostAddress", hostName);
		serviceEndPoint = serviceEndPoint + id;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);

		Entity<SavedSearchDetails> savedSearchDetails = Entity.entity(
				inputBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePUT(serviceEndPoint, savedSearchDetails);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response updateSavedSearchByIdNonExisting(
			SavedSearchDetails inputBean, String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchByIdNonExisting
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Entity<SavedSearchDetails> savedSearchDetails = Entity.entity(
				inputBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePUT(serviceEndPoint, savedSearchDetails);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	public Response getSavedSearchUsingSearchText(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchUsingSearchText
				.replaceAll("hostAddress", hostName);
		String user = "bhagyasree";
		serviceEndPoint = serviceEndPoint + user;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response validationOnSavedSearchUsingSearchText(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchUsingSearchText
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response getSavedSearchUsingPartialSearchText(String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPoint = endPointURLSavedSearchUsingSearchText
				.replaceAll("hostAddress", hostName);
		String skill = "jav";
		serviceEndPoint = serviceEndPoint + skill;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response getCandidatesFromSavedSearch(
			com.spire.base.service.utils.SearchInput inputBean, String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPointC = EndPointUrlGetCandidatesFromSavedSearch
				.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointC);
		Logging.log(" EndPoint URL >>" + serviceEndPointC);
		Entity<com.spire.base.service.utils.SearchInput> searchInput = Entity
				.entity(inputBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPointC, searchInput);
		// Logging.log(searchInput.toString());
		if (response.getStatus() == 200) {
			System.out.println("RESPONSE CODE >>" + response.getStatus());
			Logging.log("RESPONSE CODE >>" + response.getStatus());
		} else {
			Logging.log("RESPONSE CODE >>" + response.getStatus());
			Assert.fail();

		}

		return response;

	}

	public Response createSavedSearchWithExistingSavedSearchName(
			SavedSearchDetails inputBean, String hostName)
			throws ClientProtocolException, IOException {

		String serviceEndPointC = createPublicSavedSearchWithSkill.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPointC);

		Entity<SavedSearchDetails> searchInput = Entity.entity(inputBean,
				MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPointC, searchInput);
		// System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

}
