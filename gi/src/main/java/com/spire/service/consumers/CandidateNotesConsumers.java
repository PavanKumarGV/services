package com.spire.service.consumers;

import javax.ws.rs.core.Response;



import org.apache.http.impl.client.BasicResponseHandler;


import com.spire.base.service.BaseServiceConsumerNew;

public class CandidateNotesConsumers extends BaseServiceConsumerNew 
{
	String endPointURLCandidatenoteslist = getServiceEndPoint("CANDIDATE_NOTES_LIST");
	String endPointURLCandidatenotessearch=getServiceEndPoint("CANDIDATE_NOTES_SEARCH");
	String endPointURLCandidatenoteswithoutinterval=getServiceEndPoint("CANDIDATE_NOTES_LIST_WITHOUT_INTERVAL");
	String endpointURLCandidatenotelistwithoutentityid=getServiceEndPoint("CANDIDATE_NOTES_LIST_WITHOUT_ENTITYID");
	String endpointURLCandidatenotesearchwithoutsearchtext=getServiceEndPoint("CANDIDATE_NOTES_SEARCH_WITHOUT_SEARCHTEXT");
	String endpointURLCandidatenotesearchwithoutentityid=getServiceEndPoint("CANDIDATE_NOTES_SEARCH_WITHOUT_ENTITYID");
	
	public void getCandidatenoteslist(String hostName) {
		String serviceEndPoint = endPointURLCandidatenoteslist.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		System.out.println(response.getEntity());
	}
	public void getCandidatenotesearch(String hostName) {
		String serviceEndPoint =endPointURLCandidatenotessearch.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		System.out.println(response.getEntity());
	}
	
	public Response getCandidatenoteslistwithoutInterval(String hostName)
	{
		String serviceEndPoint =endPointURLCandidatenoteswithoutinterval.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		/*Assert.assertEquals(200, response.getStatus());
		String responsebody=response.readEntity(String.class);
		System.out.println(responsebody);*/
		return response;
		
		
	}
	public Response getCandidatenoteslistwithoutEntityid(String hostName)
	{
		String serviceEndPoint =endpointURLCandidatenotelistwithoutentityid.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		/*Assert.assertEquals(200, response.getStatus());
		String responsebody=response.readEntity(String.class);
		System.out.println(responsebody);*/
		return response;
		
	}
	
	public Response getCandidatenotessearchwithoutsearchtext(String hostName)
	{
		String serviceEndPoint =endpointURLCandidatenotesearchwithoutsearchtext.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		/*Assert.assertEquals(200, response.getStatus());
		String responsebody=response.readEntity(String.class);
		System.out.println(responsebody);*/
		return response;
		
		
	}
	public Response getCandidatenotessearchwithoutentityid(String hostName)
	{
		String serviceEndPoint =endpointURLCandidatenotesearchwithoutentityid.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		/*Assert.assertEquals(200, response.getStatus());
		String responsebody=response.readEntity(String.class);
		System.out.println(responsebody);*/
		return response;
		
		
	}

}
