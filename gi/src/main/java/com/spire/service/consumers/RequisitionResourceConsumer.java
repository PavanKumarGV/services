package com.spire.service.consumers;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

public class RequisitionResourceConsumer extends BaseServiceConsumerNew {
	
	String endPointURL_REQ = getServiceEndPoint("REQUISITION_SEARCH");
	String endPointURL_JOBDES_BY_ID = getServiceEndPoint("JOB_DES_BY_ID");

	
	public RequisitionResourceConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		       System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
		}

	
	public Response getRequisition(String hostName)
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
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	
/*
 * Get the job description by requisition id
 */
	
	public Response getJobDesByreqID(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_JOBDES_BY_ID.replaceAll("hostAddress",
				hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() == 200) {
			Logging.log("Status Code 200 ");		
			} else {
			Assert.fail();	
		}
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	

}


