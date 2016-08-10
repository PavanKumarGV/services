package com.spire.service.consumers;

import javax.ws.rs.core.Response;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

public class CandidateResourcesConsumer extends BaseServiceConsumerNew {

	String fetchCandidateURL = getServiceEndPoint("FETCH_CANDIDATES");
	String getResumeURL = getServiceEndPoint("GET_RESUME");
	String getcandidateprofileURL=getServiceEndPoint("GET_CANDIDATE_PROFILE");

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
	public void getToken(String username, String password, String hostName)
	{
		Logging.log("Inside of Login");
	       System.out.println("Inside of Login");
	getUserToken(username, password, hostName);
	}
	
	
	public Response getcandidateprofile(String hostName)
	{
		String serviceEndPoint = getcandidateprofileURL.replaceAll("hostAddress", hostName)+"/6002%3A6005%3A19c5a4a6aabb4336a5718e079e26528e?projection=full";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}
	
	public Response getCandidateprofilewithoutanyparameter(String hostName)
	{
		String serviceEndPoint = getcandidateprofileURL.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
		
		
	}
	public Response getCandidateprofilewithoutprojection(String hostName)
	{
		String serviceEndPoint = getcandidateprofileURL.replaceAll("hostAddress", hostName)+"?projection=full";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}
	public Response getCandidateprofilewithoutid(String hostName)
	{
		String serviceEndPoint = getcandidateprofileURL.replaceAll("hostAddress", hostName)+"/6002:6005:19c5a4a6aabb4336a5718e079e26528e";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		return response;
	}
	
	
	
}
