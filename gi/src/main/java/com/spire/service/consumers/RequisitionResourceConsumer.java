package com.spire.service.consumers;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

import spire.talent.gi.beans.NoteBean;
import spire.talent.gi.beans.RequisitionStatusBean;
import spire.talent.gi.beans.SearchRequisitionRequestBean;

public class RequisitionResourceConsumer extends BaseServiceConsumerNew {

	String endPointURL_REQ = getServiceEndPoint("REQUISITION_SEARCH");
	String endPointURL_JOBDES_BY_ID = getServiceEndPoint("JOB_DES_BY_ID");
	String endPointURL_REQInvalid = getServiceEndPoint("INVALID_REQ_SEARCH");
	String endPointURL_REQBlank = getServiceEndPoint("REQ_BLANK");
	String endPointURL_REQInvalid1 = getServiceEndPoint("INVALID1_REQ_SEARCH");
	String searchReqURLEndpoint = getServiceEndPoint("SEARCH_REQUISITION");
	String endPointURL_JD_BY_SPLCHAR_ID = getServiceEndPoint("SEARCH_REQUISITION_SPL");
	String endPointURL_JD_BY_Blank_ID = getServiceEndPoint("SEARCH_REQUISITION_Blk");

	

	String endPointURL_JD_BY_WRONG_ID = getServiceEndPoint("JOB_DES_BY_WRONG_ID");
	String endPointURL_MATCHING_REQ1 = getServiceEndPoint("MATCHING_REQS_LIMIT_TEN");
	String endPointURL_MATCHING_REQ2 = getServiceEndPoint("MATCHING_REQS_LIMIT_TWENTY");
	String endPointURL_MATCHING_REQ3 = getServiceEndPoint("MATCHING_REQS_WITH_ALL_FEILDS");
	String endPointURL_MATCHING_REQ4 = getServiceEndPoint("MATCHING_REQS_WITH_ID_OFSET");
	String endPointURL_MATCHING_REQ5 = getServiceEndPoint("MATCHING_REQS_ID_ONLY");
	String createCandidateStasEndPOint=getServiceEndPoint("CREATE_CANDIDATE_STAS");
	String changeReqURL = getServiceEndPoint("CHANGE_REQ_STATUS");

	public RequisitionResourceConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
	}

	public RequisitionResourceConsumer() {
		Logging.log("No Login Required");

	}

	/* Get RR status code */

	public Response getRequisition(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_REQ.replaceAll("hostAddress", hostName);
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

	/* Get RR status code for INVALID RR ID- Special character */

	public Response getRequisitionInvalid(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_REQInvalid.replaceAll("hostAddress", hostName);
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

	/* Get RR status code for Blank RR ID */
	public Response getRequisitionBlank(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_REQBlank.replaceAll("hostAddress", hostName);
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
	/* Get RR status code for INVALID RR ID */

	public Response getRequisitionInvalidInput(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_REQInvalid1.replaceAll("hostAddress", hostName);
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

	public Response getJobDesByreqID(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_JOBDES_BY_ID.replaceAll("hostAddress", hostName);
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

	public Response searchRequisition(String hostName, SearchRequisitionRequestBean searchReqrequestBean) {
		String serviceEndPoint = searchReqURLEndpoint.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<SearchRequisitionRequestBean> searchBean = Entity.entity(searchReqrequestBean,
				MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, searchBean);

		return response;

	}

	/*
	 * 11-08-2016 Negetive test case Vasista - Get the job description by
	 * requisition id Passing wrong requisition id
	 */

	public Response getJobDesByWrongreqID(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_JD_BY_WRONG_ID.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		// String response = response1.readEntity(String.class);

		if (response1.getStatus() !=200) {
			Logging.log("Status Code 400");
		} else {
			Assert.fail();
		}
		System.out.println("response code:" + response1.getStatus());
		// Assertion.assertEquals(response1.getStatus(),500,
		// "response expected 500 but found response code
		// as:"+response1.getStatus());
		// Assert.assertEquals(response1.getStatus(), 500);
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	/*
	 * Udhay- Get the job description by
	 * requisition id Passing special char requisition id: Testcase should fail
	 */

	public Response getJobDesBySplcharreqID(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_JD_BY_SPLCHAR_ID.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		// String response = response1.readEntity(String.class);

		if (response1.getStatus() != 200) {
			Logging.log("Status Code Not equal to 200");
		} else {
			Assert.fail();
		}
		System.out.println("response code:" + response1.getStatus());
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	
	/*
	 * Udhay- Get the job description by
	 * requisition id Passing Blank requisition id: Testcase should fail
	 */

	public Response getJobDesByBlankSpacereqID(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_JD_BY_Blank_ID.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		// String response = response1.readEntity(String.class);

		if (response1.getStatus() != 200) {
			Logging.log("Status Code Not equal to 200");
			} else {
			Assert.fail();
		}
		System.out.println("response code:" + response1.getStatus());
		Logging.log("Response Code >>" + response1.getStatus());
		return response1;
	}
	
	

	/*
	 * 11- 08 -2016 vasista - Get the list of matching requisition id Passing
	 * half Req id and limit 10 (it will display >= 10 requisitions ) ex: s741
	 */

	public Response getMatchingReqsOnlyLimit(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ1.replaceAll("hostAddress", hostName);
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
	 * 11- 08 -2016 vasista - Get the list of matching requisition id Passing
	 * half Req id and limit 20 (it will display >= 20 requisitions )
	 */

	public Response getMatchingReqsOnlyLimit20(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ2.replaceAll("hostAddress", hostName);
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
	 * 11- 08 -2016 vasista - Get the list of matching requisition ids Passing
	 * half Req id and limit 10 (it will display >= 10 requisitions ) ex: s741
	 */

	public Response getMatchingReqWithAllFeilds(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ3.replaceAll("hostAddress", hostName);
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
	 * 11- 08 -2016 vasista - Get the list of matching requisition id Passing
	 * half Req id and offset = 5
	 */

	public Response getMatchingReqWithOfSet(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ4.replaceAll("hostAddress", hostName);
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
	 * 11- 08 -2016 vasista - Get the list of matching requisition id Passing
	 * half Req id and offset = 5
	 */

	public Response getMatchingReqIDOnly(String hostName) throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL_MATCHING_REQ5.replaceAll("hostAddress", hostName);
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
	
	public String getTotalCount(Response response){
		String responseBody = response.readEntity(String.class);
		Assertion.assertTrue(responseBody.contains("totalResults"), "Requisition count not found");
		String[] str = responseBody.split("totalResults");
		String str1 = str[1];
		String[] str2 = str1.split(":");
		String str3 = str2[1].substring(0, str2[1].length() - 1);
		return str3.substring(0, str3.length() - 1);
	}
	/**
	 * priti
	 * @param hostName
	 * @param searchReqrequestBean
	 * @return
	 */
	public Response createcandidatestas(String hostName,SearchRequisitionRequestBean searchReqrequestBean)
	{
		String serviceEndPoint = createCandidateStasEndPOint.replaceAll("hostAddress",hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<SearchRequisitionRequestBean> searchBean = Entity.entity(searchReqrequestBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, searchBean);
		return response;
		
		
	}
	public Response changeReqStatus(RequisitionStatusBean serviceBean,String hostname,String reqId,String stats) {
		String serviceEndPoint = changeReqURL.replaceAll("hostAddress", hostname)+reqId+"/"+stats;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<RequisitionStatusBean> bean = Entity.entity(serviceBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePUT(serviceEndPoint, bean);
		return response;
	}
	
	public Response changeReqStatusBlnkRR(RequisitionStatusBean serviceBean,String hostname,String stats) {
		String serviceEndPoint = changeReqURL.replaceAll("hostAddress", hostname)+stats;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<RequisitionStatusBean> bean = Entity.entity(serviceBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePUT(serviceEndPoint, bean);
		return response;
	}
	
	public Response changeReqStatusBlnk(RequisitionStatusBean serviceBean,String hostname) {
		String serviceEndPoint = changeReqURL.replaceAll("hostAddress", hostname);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<RequisitionStatusBean> bean = Entity.entity(serviceBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePUT(serviceEndPoint, bean);
		return response;
	}


}
