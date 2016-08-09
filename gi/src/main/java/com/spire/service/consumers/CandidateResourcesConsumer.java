package com.spire.service.consumers;

import javax.ws.rs.core.Response;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

public class CandidateResourcesConsumer extends BaseServiceConsumerNew {

	String fetchCandidateURL = getServiceEndPoint("FETCH_CANDIDATES");
	String getResumeURL = getServiceEndPoint("GET_RESUME");

	public int getCandidates(String hostName) {
		String serviceEndPoint = fetchCandidateURL.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		System.out.println(response.readEntity(String.class));
		return response.getStatus();
	}
	
	public int getResume(String hostName){
		String serviceEndPoint = getResumeURL.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		System.out.println(response.readEntity(String.class));
		return response.getStatus();
	}
}
