package spire.talent.gi.beans;

import java.io.Serializable;

public class RequisitionStatusBean implements Serializable{
	
	private static final long serialVersionUID = 729298927;

	private String id;
	
	private String statusDisplay;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}
	
	
}
