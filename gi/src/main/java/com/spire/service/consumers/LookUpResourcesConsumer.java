package com.spire.service.consumers;

import javax.ws.rs.core.Response;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

public class LookUpResourcesConsumer extends BaseServiceConsumerNew {

	String lookUpByType = getServiceEndPoint("LOOK_UP");
	String lookUpByTypeNKeyword = getServiceEndPoint("LOOK_UP_BY_KEYWORD_TYPE");

	public Response getListOfDemandFilter(String hostName) {
		String serviceEndPoint = lookUpByType.replaceAll("hostAddress", hostName);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response getListOfDemandFilterByTypeNKeyword(String hostName) {
		String serviceEndPoint = lookUpByTypeNKeyword.replaceAll("hostAddress", hostName);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	
}
