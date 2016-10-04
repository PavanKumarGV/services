package com.spire.base.service.utils;

import java.util.Date;
import java.util.List;

public class SavedSearchDetails {

    private String	 id;
    private String	 name;
    private String	 searchDescription;
    private String	 createdById;
    private String	 createdByName;
    private String	 modifiedByName;
    private Date	 createdOn;
    private Date	 modifiedOn;
    private boolean	 publicPool;
    private SearchInput	 searchInput;
    private List<String> selectedFacets;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSearchDescription() {
	return searchDescription;
    }

    public void setSearchDescription(String searchDescription) {
	this.searchDescription = searchDescription;
    }

    public String getCreatedByName() {
	return createdByName;
    }

    public void setCreatedByName(String createdByName) {
	this.createdByName = createdByName;
    }

    public String getModifiedByName() {
	return modifiedByName;
    }

    public void setModifiedByName(String modifiedByName) {
	this.modifiedByName = modifiedByName;
    }

    public Date getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
	return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
	this.modifiedOn = modifiedOn;
    }

    public boolean isPublicPool() {
	return publicPool;
    }

    public void setPublicPool(boolean publicPool) {
	this.publicPool = publicPool;
    }

    public SearchInput getSearchInput() {
	return searchInput;
    }

    public void setSearchInput(SearchInput searchInput) {
	this.searchInput = searchInput;
    }

    public List<String> getSelectedFacets() {
	return selectedFacets;
    }

    public void setSelectedFacets(List<String> selectedFacets) {
	this.selectedFacets = selectedFacets;
    }

    @Override
    public String toString() {
	return "SavedSearchDetails [id=" + id + ", name=" + name + ", searchDescription=" + searchDescription
		+ ", createdByName=" + createdByName + ", modifiedByName=" + modifiedByName + ", createdOn=" + createdOn
		+ ", modifiedOn=" + modifiedOn + ", publicPool=" + publicPool + ", searchInput=" + searchInput
		+ ", selectedFacets=" + selectedFacets + "]";
    }

    public String getCreatedById() {
	return createdById;
    }

    public void setCreatedById(String createdById) {
	this.createdById = createdById;
    }
}
