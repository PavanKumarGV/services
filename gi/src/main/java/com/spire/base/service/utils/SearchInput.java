/**
 * 
 */
package com.spire.base.service.utils;

/**
 * @author Bhagyasree
 *
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import spire.match.entities.FacetTerm;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchInput implements Serializable {

	private String searchQueryString;
	private String freeSearchQueryString;
	Map<String, List<String>> searchAttributeMap;
	List<String> customFields;
	// List<FacetTerm> customFacets;

	/*private String nameString;
	private String searchDescription="Test";
	private String createdByName="Bhagyasree";
	private Date createdOn;
	private Date modifiedOn;
	private String publicPool;*/
	private String SearchInputString;
	
	
	public String getSearchQueryString() {
		return searchQueryString;
	}

	public void setSearchQueryString(String searchQueryString) {
		this.searchQueryString = searchQueryString;
	}

	public String getFreeSearchQueryString() {
		return freeSearchQueryString;
	}

	public void setFreeSearchQueryString(String freeSearchQueryString) {
		this.freeSearchQueryString = freeSearchQueryString;
	}

	public Map<String, List<String>> getSearchAttributeMap() {
		return searchAttributeMap;
	}

	public void setSearchAttributeMap(
			Map<String, List<String>> searchAttributeMap) {
		this.searchAttributeMap = searchAttributeMap;
	}

	public List<String> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<String> customFields) {
		this.customFields = customFields;
	}

	/*
	 * public List<FacetTerm> getCustomFacets() { return customFacets; }
	 * 
	 * public void setCustomFacets(List<FacetTerm> customFacets) {
	 * this.customFacets = customFacets; }
	 */

	/*public String getnameString() {
		return nameString;
	}

	public void setnameString(String nameString) {
		this.nameString = nameString;
	}

	public String getsearchDescription() {
		return searchDescription;
	}

	public void setsearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
	}

	public String getcreatedByName() {
		return createdByName;
	}

	public void setcreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public Date getcreatedOn() {
		return createdOn;
	}

	public void setcreatedOn(Date date) {
		this.createdOn = date;
	}
	public Date getmodifiedOn() {
		return modifiedOn;
	}

	public Date setmodifiedOn(Date modifiedOn) {
		return this.modifiedOn = modifiedOn;
	}
	
	public void setpublicPool(String publicPool) {
		this.publicPool = publicPool;
	}*/
	
	public String getSearchInputString() {
		return SearchInputString;
	}

	public void setSearchInput(String SearchInputString) {
		this.SearchInputString = SearchInputString;
	}

}
