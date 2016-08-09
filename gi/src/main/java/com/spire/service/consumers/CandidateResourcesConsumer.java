package com.spire.service.consumers;

import javax.ws.rs.core.Response;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

public class CandidateResourcesConsumer extends BaseServiceConsumerNew {

	String endPointURL = getServiceEndPoint("FETCH_CANDIDATES");

	public void getCandidates(String hostName) {
		String serviceEndPoint = endPointURL.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		System.out.println(response.getEntity());
	}
}
