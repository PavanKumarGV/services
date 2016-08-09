package com.spire.service.consumers;

import javax.ws.rs.core.Response;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

public class LookUpResourcesConsumer extends BaseServiceConsumerNew {

	String endPointURL = getServiceEndPoint("LOOK_UP");

	public Response getListOfDemandFilter(String hostName) {
		String serviceEndPoint = endPointURL.replaceAll("hostAddress", hostName);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		
		return response;
	}
}
