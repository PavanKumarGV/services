package com.spire.service.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.base.service.Constants;


import spire.talent.gi.beans.GetCandidateRequestBean;

public class CandidateResourcesConsumer extends BaseServiceConsumerNew {

	String fetchCandidateURL = getServiceEndPoint("FETCH_CANDIDATES");
	String getResumeURL = getServiceEndPoint("GET_RESUME");
	String getcandidateprofileURL = getServiceEndPoint("GET_CANDIDATE_PROFILE");
	String deallocUrl = getServiceEndPoint("CANDIDATE_DEALLOCATE");

	public CandidateResourcesConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
	}

	public CandidateResourcesConsumer() {
		Logging.log("Inside of Login");

	}

	public int getCandidates(String hostName) {
		String serviceEndPoint = fetchCandidateURL.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		System.out.println(response.readEntity(String.class));
		return response.getStatus();
	}

	public int getResume(String hostName) {
		String serviceEndPoint = getResumeURL.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		System.out.println(response.readEntity(String.class));
		return response.getStatus();
	}

	public void getToken(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
	}

	/**
	 * Pass parameter id and projection type Returns Respsonse
	 */
	public Response getcandidateprofile(String hostName) {
		String serviceEndPoint = getcandidateprofileURL.replaceAll("hostAddress", hostName)
				+ "/"+Constants.candidate_Id1+"?projection=full";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		//call Get Operation
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	/**
	 * Pass parameter id Returns Respsonse
	 */
	public Response getCandidateprofilewithoutanyparameter(String hostName) {
		String serviceEndPoint = getcandidateprofileURL.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		//call Get Operation
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;

	}

	public Response getCandidateprofilewithoutprojection(String hostName) {
		String serviceEndPoint = getcandidateprofileURL.replaceAll("hostAddress", hostName) +"/"+Constants.candidate_Id1;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		//call Get Operation
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}

	/**
	 * Pass parameter Projection type Returns Respsonse
	 */
	public Response getCandidateprofilewithoutid(String hostName) {
		String serviceEndPoint = getcandidateprofileURL.replaceAll("hostAddress", hostName)+"?projection=full";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		//call Get Operation
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}


	public Response getCandidateList(GetCandidateRequestBean reqBean, String hostname) {
		String serviceEndPoint = fetchCandidateURL.replaceAll("hostAddress", hostname);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<GetCandidateRequestBean> bean = Entity.entity(reqBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, bean);
		return response;

	}
	
	public Response getCandidateResume(String cid,String hostname){
		String serviceEndPoint = getResumeURL.replaceAll("hostAddress", hostname)+"/"+cid;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		//call Get Operation
		Response response = executeGET(serviceEndPoint);
		return response;
		
	}
	
	public void assertResponse(Response response){
		String responseBody = response.readEntity(String.class);
		String[] str = responseBody.split("fileName");
		String[] str1 = str[1].substring(3).split("\"");
		String fileType = str1[0].substring(str1[0].length()-4, str1[0].length());
		System.out.println("******"+fileType);
		//Asserting response Body
		Assertion.assertTrue(fileType.contains("doc")||fileType.contains("docx")||fileType.contains("pdf")||fileType.contains("txt"), "Get Candidate Resume Unsuccessfull");
		Logging.log("Get Candidate Resume successful, File Type: "+fileType );
	}
	
	public Response getCandidateResumeBlank(String hostname){
		String serviceEndPoint = getResumeURL.replaceAll("hostAddress", hostname);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		//call Get Operation
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response getCandidateResumeInvalid(String cid,String hostname){
		String str1 = cid.substring(0, 10);
		String serviceEndPoint = getResumeURL.replaceAll("hostAddress", hostname)+"/"+str1;
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		//call Get Operation
		Response response = executeGET(serviceEndPoint);
		return response;
		
	}
	public Response deallocatecandidate(String hostname,String reqBean) {
		String serviceEndPoint = deallocUrl.replaceAll("hostAddress", hostname);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		
		Entity<String> bean = Entity.entity(reqBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, bean);
		return response;

	}
}
