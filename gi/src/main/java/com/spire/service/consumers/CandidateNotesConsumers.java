package com.spire.service.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.impl.client.BasicResponseHandler;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

import spire.talent.gi.beans.NoteBean;

public class CandidateNotesConsumers extends BaseServiceConsumerNew {
	String endPointURLCandidatenoteslist = getServiceEndPoint("CANDIDATE_NOTES_LIST");
	String endPointURLCandidatenotessearch = getServiceEndPoint("CANDIDATE_NOTES_SEARCH");
	String createNoteEndPointUrl = getServiceEndPoint("CREATE_NOTE");

	public Response getCandidatenoteslist(String hostName) {
		String serviceEndPoint = endPointURLCandidatenoteslist.replaceAll("hostAddress", hostName)
				+ "?entityId=Testing1&interval=1";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("Response Code >>" + response.getStatus());
		return response;
	}

	public Response getCandidatenotesearch(String hostName) {
		String serviceEndPoint = endPointURLCandidatenotessearch.replaceAll("hostAddress", hostName)
				+ "?entityId=Testing1&searchText=candidate%20selected";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("Response Code >>" + response.getStatus());
		return response;
	}

	public Response getCandidatenoteslistwithoutInterval(String hostName) {
		// String serviceEndPoint = lookUp.replaceAll("hostAddress",
		// hostName)+"?type=REQUISITION_STATUS";
		String serviceEndPoint = endPointURLCandidatenoteslist.replaceAll("hostAddress", hostName)
				+ "?entityId=Testing1";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		Logging.log("Response Code >>" + response.getStatus());
		return response;

	}

	public Response getCandidatenoteslistwithoutEntityid(String hostName) {
		String serviceEndPoint = endPointURLCandidatenoteslist.replaceAll("hostAddress", hostName) + "?interval=1";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("Response Code >>" + response.getStatus());
		return response;

	}

	public Response getCandidatenotessearchwithoutsearchtext(String hostName) {
		String serviceEndPoint = endPointURLCandidatenotessearch.replaceAll("hostAddress", hostName)
				+ "?entityId=Testing1";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("Response Code >>" + response.getStatus());
		return response;

	}

	public Response getCandidatenotessearchwithoutentityid(String hostName) {
		String serviceEndPoint = endPointURLCandidatenotessearch.replaceAll("hostAddress", hostName)
				+ "?searchText=java";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("Response Code >>" + response.getStatus());
		return response;

	}

	public Response getCandidatenotelistwithoutanyparameter(String hostname) {
		String serviceEndPoint = endPointURLCandidatenoteslist.replaceAll("hostAddress", hostname);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("Response Code >>" + response.getStatus());
		return response;

	}

	public Response getCandidatenotesearchwithoutanyparameter(String hostname) {
		String serviceEndPoint = endPointURLCandidatenotessearch.replaceAll("hostAddress", hostname);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("Response Code >>" + response.getStatus());
		return response;
	}
	public Response getCandidatenotelistwithincorrectparameter(String hostname)
	{
		String serviceEndPoint =endPointURLCandidatenoteslist.replaceAll("hostAddress", hostname)+"entityId=1234&interval=asd";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("Response Code >>"+response.getStatus());
		return response;
		
		
		
	}
	
	public Response getCandidatenotesearchwithincorrectparameter(String hostname)
	{
		String serviceEndPoint =endPointURLCandidatenotessearch.replaceAll("hostAddress", hostname)+"?entityId=23123&searchText=asdasd123";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		System.out.println("RESPONSE CODE >>" + response.getStatus());
		Logging.log("Response Code >>"+response.getStatus());
		return response;
	}

	public Response createNote(NoteBean notesServiceBean,String hostname) {
		String serviceEndPoint = createNoteEndPointUrl.replaceAll("hostAddress", hostname);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<NoteBean> noteBean = Entity.entity(notesServiceBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, noteBean);

		return response;
	}

}
