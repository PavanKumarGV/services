package spire.talent.gi.beans;

import java.util.List;

public class CandidateStatsRequestBean {
	List<String> requisitionIdList;

	String attribute;

	public List<String> getRequisitionIdList() {
		return requisitionIdList;
	}

	public void setRequisitionIdList(List<String> requisitionIdList) {
		this.requisitionIdList = requisitionIdList;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}