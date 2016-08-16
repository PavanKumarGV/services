package com.spire.base.service.utils;

/**@author  10/08/16*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


//import spire.talent.gi.beans.SavedSearchDetails;
import com.spire.base.service.utils.SavedSearchDetails;
import com.spire.base.controller.Logging;

public class SearchUtil {

	public static SearchInputRequest getSearchInputBeanWithSkill() {
		SearchInput searchInput = new SearchInput();

		// String searchQueryString =
		// "(skill:java and skill:mysql AND skill:j2ee) AND (location:Bengalore AND location:Mumbai)";
		String searchQueryString = "(skill:MySQL and skill:java)";// ,
		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();

		List<String> skills = new ArrayList<String>();
		skills.add("MySQL");
		skills.add("java");

		searchAttributeMap.put("skill", skills);

		searchInput.setSearchQueryString(searchQueryString);
		searchInput.setSearchAttributeMap(searchAttributeMap);

		SearchInputRequest searchInputRequest = new SearchInputRequest();
		searchInputRequest.setSearchInput(searchInput);
		searchInputRequest.setPageInfo(getPageInfo());

		return searchInputRequest;
	}
	
	public static SearchInput getSearchInputBeanWithSkillWithoutPageInfo() {
		SearchInput searchInput = new SearchInput();
		String searchQueryString = "(skill:ajax)";// ,
		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();

		List<String> skills = new ArrayList<String>();
		skills.add("ajax");

		searchAttributeMap.put("skill", skills);

		searchInput.setSearchQueryString(searchQueryString);
		searchInput.setSearchAttributeMap(searchAttributeMap);

		return searchInput;
	}

	public static SearchInputRequest getSearchInputBeanWithSkillAndLocation() {
		SearchInput searchInput = new SearchInput();

		String searchQueryString = "(skill:java and skill:MySQL) AND (location:Bengalore AND location:Mumbai)";
		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();

		List<String> skills = new ArrayList<String>();
		skills.add("MySQL");
		skills.add("java");

		List<String> locations = new ArrayList<String>();
		locations.add("Bengalore");
		locations.add("Mumbai");

		searchAttributeMap.put("skill", skills);
		searchAttributeMap.put("location", locations);

		searchInput.setSearchQueryString(searchQueryString);
		searchInput.setSearchAttributeMap(searchAttributeMap);

		SearchInputRequest searchInputRequest = new SearchInputRequest();
		searchInputRequest.setSearchInput(searchInput);
		searchInputRequest.setPageInfo(getPageInfo());

		return searchInputRequest;
	}

	public static PageInfo getPageInfo() {

		PageInfo inputPageInfo = new PageInfo();

		inputPageInfo.setLimit(10);
		inputPageInfo.setOffset(0);

		return inputPageInfo;

	}

	/*public static SearchInputRequest getSearchInputBeanWithSkillAndExperienceFacet() {
		SearchInput searchInput = new SearchInput();

		String searchQueryString = "(skill:java and skill:MySQL)";
		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();

		List<String> skills = new ArrayList<String>();
		skills.add("MySQL");
		skills.add("java");

		searchAttributeMap.put("skill", skills);

		searchInput.setSearchQueryString(searchQueryString);
		searchInput.setSearchAttributeMap(searchAttributeMap);

		SearchInputRequest searchInputRequest = new SearchInputRequest();
		searchInputRequest.setSearchInput(searchInput);
		searchInputRequest.setPageInfo(getPageInfo());

		return searchInputRequest;
	}
*/
	public static int getRandom(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	

	public static SavedSearchDetails createSavedSearchInputBeanWithSkill() {

		SavedSearchDetails savedSearchDetails = new SavedSearchDetails();
		savedSearchDetails.setName("Test Saved Search" + getRandom(1, 10000));
		savedSearchDetails.setSearchDescription("Test");
		savedSearchDetails.setCreatedByName("Bhagyasree");
		SearchInput searchInput = new SearchInput();

		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();
		List<String> skills = new ArrayList<String>();
		skills.add("java");
		searchAttributeMap.put("skill", skills);

		String searchQueryString = "(skill:java)";

		searchInput.setSearchQueryString(searchQueryString);
		searchInput.setSearchAttributeMap(searchAttributeMap);
		savedSearchDetails.setSearchInput(searchInput);
		Logging.log("create : savedSearchDetails..................." + savedSearchDetails);

		return savedSearchDetails;
	}
	
	public static SavedSearchDetails createSavedSearchInputBeanWithSkillAndLocation() {

		SavedSearchDetails savedSearchDetails = new SavedSearchDetails();
		savedSearchDetails.setName("Test Saved Search" + getRandom(1, 10000));
		savedSearchDetails.setSearchDescription("Test");
		savedSearchDetails.setCreatedByName("Bhagyasree");
		savedSearchDetails.setPublicPool(false);
		SearchInput searchInput = new SearchInput();

		
		String searchQueryString = "(skill:java and skill:MySQL) AND (location:Bengalore AND location:Mumbai)";
		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();

		List<String> skills = new ArrayList<String>();
		skills.add("MySQL");
		skills.add("java");

		List<String> locations = new ArrayList<String>();
		locations.add("Bengalore");
		locations.add("Mumbai");

		searchAttributeMap.put("skill", skills);
		searchAttributeMap.put("location", locations);
		
		searchInput.setSearchQueryString(searchQueryString);
		searchInput.setSearchAttributeMap(searchAttributeMap);
		savedSearchDetails.setSearchInput(searchInput);
		Logging.log("savedSearchDetails..................." + savedSearchDetails);

		return savedSearchDetails;
	}
	
	public static SavedSearchDetails createPublicSavedSearchInputBeanWithSkill() {

		SavedSearchDetails savedSearchDetails = new SavedSearchDetails();
		savedSearchDetails.setName("Test Saved Search" + getRandom(1, 10000));
		savedSearchDetails.setSearchDescription("Test");
		savedSearchDetails.setCreatedByName("Bhagyasree");
		savedSearchDetails.setPublicPool(true);
		SearchInput searchInput = new SearchInput();

		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();
		List<String> skills = new ArrayList<String>();
		skills.add("java");
		searchAttributeMap.put("skill", skills);

		String searchQueryString = "(skill:java)";

		searchInput.setSearchQueryString(searchQueryString);
		searchInput.setSearchAttributeMap(searchAttributeMap);
		savedSearchDetails.setSearchInput(searchInput);
		Logging.log("savedSearchDetails..................." + savedSearchDetails);

		return savedSearchDetails;
	}
	public static SavedSearchDetails createSavedSearchBeanWithExistingSavedSearchName() {

		SavedSearchDetails savedSearchDetails = new SavedSearchDetails();
		savedSearchDetails.setName("Test Saved Search");
		savedSearchDetails.setSearchDescription("Test");
		savedSearchDetails.setCreatedByName("Bhagyasree");
		savedSearchDetails.setPublicPool(true);
		SearchInput searchInput = new SearchInput();

		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();
		List<String> skills = new ArrayList<String>();
		skills.add("java");
		searchAttributeMap.put("skill", skills);

		String searchQueryString = "(skill:java)";

		searchInput.setSearchQueryString(searchQueryString);
		searchInput.setSearchAttributeMap(searchAttributeMap);
		savedSearchDetails.setSearchInput(searchInput);
		Logging.log("savedSearchDetails..................." + savedSearchDetails);

		return savedSearchDetails;
	}
	public static SearchInputRequest searchCandidatesWithNoSearchQueryString() {
		SearchInput searchInput = new SearchInput();

		String searchQueryString = "()";// ,
		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();
		List<String> skills = new ArrayList<String>();
	

		searchAttributeMap.put("skill", skills);
		Logging.log(searchQueryString);
		
		searchInput.setSearchQueryString(searchQueryString);
		searchInput.setSearchAttributeMap(searchAttributeMap);
		
		SearchInputRequest searchInputRequest = new SearchInputRequest();
		searchInputRequest.setSearchInput(searchInput);
		searchInputRequest.setPageInfo(getPageInfo());
		
		return searchInputRequest;
	}
	
	public static SavedSearchDetails updateSavedSearchInputBeanWithSkill(SavedSearchDetails inputBean) {

		inputBean.setName("Test Saved Search _updated" + getRandom(1, 10000));
		inputBean.setModifiedByName("Bhagyasree S.");
		
		Logging.log("update : savedSearchDetails inputBean ..................." + inputBean);

		return inputBean;
	}
	

	
}