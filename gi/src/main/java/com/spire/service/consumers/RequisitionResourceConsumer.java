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
	
	String endPointURL_JD_BY_WRONG_ID = getServiceEndPoint("JOB_DES_BY_WRONG_ID");
	String endPointURL_MATCHING_REQ1 = getServiceEndPoint("MATCHING_REQS_LIMIT_TEN");
	String endPointURL_MATCHING_REQ2 = getServiceEndPoint("MATCHING_REQS_LIMIT_TWENTY");
	String endPointURL_MATCHING_REQ3 = getServiceEndPoint("MATCHING_REQS_WITH_ALL_FEILDS");
	String endPointURL_MATCHING_REQ4 = getServiceEndPoint("MATCHING_REQS_WITH_ID_OFSET");
	String endPointURL_MATCHING_REQ5 = getServiceEndPoint("MATCHING_REQS_ID_ONLY");

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
	
	/*
	 * 11-08-2016 Negetive test case Vasista - Get the job description by
	 * requisition id Passing wrong requisition id
	 */

	public Response getJobDesByWrongreqID(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_JD_BY_WRONG_ID.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		// String response = response1.readEntity(String.class);

		if (response1.getStatus() == 400) {
			Logging.log("Status Code 400");
		} else {
			Assert.fail();
		}
		System.out.println("response code:" + response1.getStatus());
		// Assertion.assertEquals(response1.getStatus(),500,
		// "response expected 500 but found response code as:"+response1.getStatus());
		// Assert.assertEquals(response1.getStatus(), 500);
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}

	/*
	 * 11- 08 -2016  
	 * vasista - Get the list of matching requisition id Passing
	 * half Req id and limit 10 (it will display >= 10 requisitions ) ex: s741
	 */
	
	
	public Response getMatchingReqsOnlyLimit(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ1.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() == 200) {
			Logging.log("Status Code 200");
		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	
	
	/*
	 * 11- 08 -2016  
	 * vasista - Get the list of matching requisition id Passing
	 * half Req id and limit 20 (it will display >= 20 requisitions ) 
	 */
	
	
	public Response getMatchingReqsOnlyLimit20(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ2.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() == 200) {
			Logging.log("Status Code 200");
		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	
	/*
	 * 11- 08 -2016  
	 * vasista - Get the list of matching requisition ids Passing
	 * half Req id and limit 10 (it will display >= 10 requisitions ) ex: s741
	 */
	
	
	public Response getMatchingReqWithAllFeilds(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ3.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() == 200) {
			Logging.log("Status Code 200");
		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	
	
	/*
	 * 11- 08 -2016  
	 * vasista - Get the list of matching requisition id Passing
	 * half Req id and offset = 5 
	 */
	
	
	public Response getMatchingReqWithOfSet(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ4.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() == 200) {
			Logging.log("Status Code 200");
		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	
	/*
	 * 11- 08 -2016  
	 * vasista - Get the list of matching requisition id Passing
	 * half Req id and offset = 5 
	 */
	
	
	public Response getMatchingReqIDOnly(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ5.replaceAll(
				"hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() == 200) {
			Logging.log("Status Code 200");
		} else {
			Assert.fail();
		}
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	
}


