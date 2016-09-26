package spire.talent.gi.beans;

import java.util.List;

import spire.talent.gi.utils.ProjectionType;

public class GetCandidateRequestBean {
	private List<String> candidateIds;
	private ProjectionType projectionType;

	public List<String> getCandidateIds() {
		return candidateIds;
	}

	public void setCandidateIds(List<String> candidateIds) {
		this.candidateIds = candidateIds;
	}

	public ProjectionType getProjectionType() {
		return projectionType;
	}

	public void setProjectionType(ProjectionType projectionType) {
		this.projectionType = projectionType;
	}

	public enum ProjectionType {

		BASIC("basic"), FULL("full"), CUSTOM("custom"), INVALID("notvalid");

		String projectionType;

		public String getProjectionType() {
			return projectionType;
		}

		private ProjectionType(String projectionType) {
			this.projectionType = projectionType;
		}
	}
}
