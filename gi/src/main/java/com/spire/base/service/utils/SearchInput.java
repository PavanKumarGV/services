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
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchInput implements Serializable {

    private String searchQueryString;
    private String freeSearchQueryString;
    Map<String,List<String>> searchAttributeMap;
    List<String>             customFields;
   // List<FacetTerm>          customFacets;

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

    public void setSearchAttributeMap(Map<String, List<String>> searchAttributeMap) {
	this.searchAttributeMap = searchAttributeMap;
    }

    public List<String> getCustomFields() {
	return customFields;
    }

    public void setCustomFields(List<String> customFields) {
	this.customFields = customFields;
    }

   /* public List<FacetTerm> getCustomFacets() {
	return customFacets;
    }

    public void setCustomFacets(List<FacetTerm> customFacets) {
	this.customFacets = customFacets;
    }*/

}


