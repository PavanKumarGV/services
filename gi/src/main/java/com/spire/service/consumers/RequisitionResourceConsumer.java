package com.spire.service.consumers;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

import spire.talent.gi.beans.NoteBean;
import spire.talent.gi.beans.SearchRequisitionRequestBean;

public class RequisitionResourceConsumer extends BaseServiceConsumerNew {
	
	String endPointURL_REQ = getServiceEndPoint("REQUISITION_SEARCH");
	String endPointURL_JOBDES_BY_ID = getServiceEndPoint("JOB_DES_BY_ID");
	String endPointURL_REQInvalid = getServiceEndPoint("INVALID_REQ_SEARCH");
	String endPointURL_REQInvalid1 = getServiceEndPoint("INVALID1_REQ_SEARCH");
	String searchReqURLEndpoint = getServiceEndPoint("SEARCH_REQUISITION");
	
	public RequisitionResourceConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		       System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
		}

	/* Get RR status code*/
	
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
	
	/* Get RR status code for INVALID RR ID- Special character*/
	
	public Response getRequisitionInvalid(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_REQInvalid.replaceAll("hostAddress",
				hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() != 200) {
			System.out.println("********** pass **************");
		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	
	/* Get RR status code for INVALID RR ID*/
	
	public Response getRequisitionInvalidInput(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_REQInvalid1.replaceAll("hostAddress",
				hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() != 200) {
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
	

	public Response searchRequisition(String hostName,SearchRequisitionRequestBean searchReqrequestBean){
		String serviceEndPoint = searchReqURLEndpoint.replaceAll("hostAddress",hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<SearchRequisitionRequestBean> searchBean = Entity.entity(searchReqrequestBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, searchBean);

		return response;
	}
}


