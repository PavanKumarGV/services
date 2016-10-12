/**
 * 
 */
package com.spire.service.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

import spire.talent.gi.beans.CandidateSpireStatusPojo;
import spire.talent.gi.beans.CandidateStatusPojo;

/**
 * @author Santosh Ramanan
 *
 */
public class CandidateStatusConsumer extends BaseServiceConsumerNew {
	String candidateStatusURL = getServiceEndPoint("PUT_CANDIDATE_STATUS");
	String candidateSpireStatusURL= getServiceEndPoint("PUT_CANDIDATE_SPIRE_STATUS");
	
	public CandidateStatusConsumer() {
		Logging.log("Candidate Status Consumer intialization");
	}
	
	public CandidateStatusConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
	}
	
	public Response putCandidateStatus(List<CandidateStatusPojo> candStatusList, String hostname) {
		String serviceEndPoint = candidateStatusURL.replaceAll("hostAddress", hostname);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<List<CandidateStatusPojo>> inputBean = Entity.entity(candStatusList,
				MediaType.APPLICATION_JSON_TYPE);
		Response response = executePUT(serviceEndPoint, inputBean);	
		return response;
	}
	
	public Response putCandidateSpireStatus(List<CandidateSpireStatusPojo> candStatusList, String hostname) {
		String serviceEndPoint = candidateSpireStatusURL.replaceAll("hostAddress", hostname);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<List<CandidateSpireStatusPojo>> inputBean = Entity.entity(candStatusList,
				MediaType.APPLICATION_JSON_TYPE);
		Response response = executePUT(serviceEndPoint, inputBean);	
		return response;
	}
}
