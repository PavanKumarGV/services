package com.spire.service.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

import spire.talent.gi.beans.CandidateStatsRequestBean;
import spire.talent.gi.beans.NoteBean;

public class CandidateStatsConsumer extends BaseServiceConsumerNew {

	String candidateStatsUrl = getServiceEndPoint("CANDIDATE_STATS");

	public CandidateStatsConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
	}

	public Response getCandidateStats(CandidateStatsRequestBean candStatsBean, String hostname) {
		String serviceEndPoint = candidateStatsUrl.replaceAll("hostAddress", hostname);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Entity<CandidateStatsRequestBean> noteBean = Entity.entity(candStatsBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, noteBean);
		return response;
	}

}
