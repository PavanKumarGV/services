package com.spire.service.consumers;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

public class RequisitionResourceConsumer extends BaseServiceConsumerNew {
	
	String endPointURL_REQ = getServiceEndPoint("REQUISITION_SEARCH");
	
	
	public RequisitionResourceConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		       System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
		}

	
	public void getRequisition(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_REQ.replaceAll("hostAddress",
				hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() == 200) {
			System.out.println("********** pass **************");
		} else {
			Assert.fail();
		}

	}  
	//test

}


