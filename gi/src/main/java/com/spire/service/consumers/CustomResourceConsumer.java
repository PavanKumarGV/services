package com.spire.service.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spire.base.controller.Logging;
import com.spire.base.service.*;

public class CustomResourceConsumer extends BaseServiceConsumerNew {
	String endPointURL_REQ_Suggestion = getServiceEndPoint("Custom_Requisition_Suggestion");
	String recommended_Candidates = getServiceEndPoint("Custom_Recomended_Candidate");
	String match_score_url = getServiceEndPoint("MATCH_SCORE");
	String getRecomendedCandidate = getServiceEndPoint("Custom_Recommended_candidatesByReqId");
	String getCandidateStatusUrl = getServiceEndPoint("Custom_Candidate_Status");
	String canditesListUrl = getServiceEndPoint("canditesListUrl");

	public CustomResourceConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
	}

	public Response getrequisitionsuggestionbyallvalidinput(String hostName) {
		String serviceEndPoint = endPointURL_REQ_Suggestion.replaceAll("hostAddress", hostName) + "/"
				+ getServiceEndPoint("custom_candidate_id") + "?limit=" + getServiceEndPoint("custom_candidate_limit");
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}

	public Response getreqsuggestionbyid(String hostName) {
		String serviceEndPoint = endPointURL_REQ_Suggestion.replaceAll("hostAddress", hostName) + "/"
				+ getServiceEndPoint("custom_candidate_id");
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}

	public Response getreqsuggestionbylimit(String hostName) {
		String serviceEndPoint = endPointURL_REQ_Suggestion.replaceAll("hostAddress", hostName) + "/"
				+ getServiceEndPoint("custom_candidate_limit");
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}

	public Response getreqsuggestionwithoutidnadlimit(String hostName) {
		String serviceEndPoint = endPointURL_REQ_Suggestion.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}

	public Response getreqsuggestionwithinvalidid(String hostName) {
		String serviceEndPoint = endPointURL_REQ_Suggestion.replaceAll("hostAddress", hostName) + "/"
				+ getServiceEndPoint("custom_invalid_candidate_id") + "?limit="
				+ getServiceEndPoint("custom_candidate_limit");
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}

	public Response getRecmendedCandidateByValidReqId(String hostName) {
		String serviceEndPoint = recommended_Candidates.replaceAll("hostAddress", hostName)
				+ getServiceEndPoint("custom_valid_reqId");
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}

	public Response getRecmendedCandidateByInValidReqId(String hostName) {
		String serviceEndPoint = recommended_Candidates.replaceAll("hostAddress", hostName)
				+ getServiceEndPoint("custom_invalid_reqId");
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}

	public Response getMatchScores(String hostName) {
		String serviceEndPoint = match_score_url.replaceAll("hostAddress", hostName)
				+ getServiceEndPoint("candidateId_matchScore") + "/matchScores?applicationStatus=APPLIED";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}

	public Response getMatchScoresInvalidId(String hostName) {
		String serviceEndPoint = match_score_url.replaceAll("hostAddress", hostName)
				+ getServiceEndPoint("candidateId_matchScore").substring(5) + "/matchScores?applicationStatus=APPLIED";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}

	public Response getMatchScoresWithInvalidStatus(String hostName) {
		String serviceEndPoint = match_score_url.replaceAll("hostAddress", hostName)
				+ getServiceEndPoint("candidateId_matchScore") + "/matchScores?applicationStatus=APPLIE";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response getMatchScoresWithNOT_APPLIEDStatus(String hostName) {
		String serviceEndPoint = match_score_url.replaceAll("hostAddress", hostName)
				+ getServiceEndPoint("candidateId_matchScore_NOT_APPLIED") + "/matchScores?applicationStatus=NOT_APPLIED";
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response getRecommendedCandsByReqId(String hostName){
		String reqBean = "{\"searchInput\": {\"searchQueryString\": \"\",\"searchAttributeMap\": {}},\"pageInfo\": {},\"displayId\": \""+ReadingServiceEndPointsProperties.getServiceEndPoint("GET_REC_CANDID_REQID") +"\",\"calculateRecordCount\":true}";
		
		String serviceEndPoint = getRecomendedCandidate.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);

		Entity<String> bean = Entity.entity(reqBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, bean);
		return response;
	}
	
	
	public Response getRecommendedCandsByInvalidReqId(String hostName){
		String reqBean = "{\"searchInput\": {\"searchQueryString\": \"\",\"searchAttributeMap\": {}},\"pageInfo\": {},\"displayId\": \""+ReadingServiceEndPointsProperties.getServiceEndPoint("GET_REC_CANDID_REQID").substring(1) +"\",\"calculateRecordCount\":true}";
		
		String serviceEndPoint = getRecomendedCandidate.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);

		Entity<String> bean = Entity.entity(reqBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, bean);
		return response;
	}
	
	public Response getRecommendedCandsByBlankReqId(String hostName){
		String reqBean = "{\"searchInput\": {\"searchQueryString\": \"\",\"searchAttributeMap\": {}},\"pageInfo\": {},\"calculateRecordCount\":true}";
		
		String serviceEndPoint = getRecomendedCandidate.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);

		Entity<String> bean = Entity.entity(reqBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, bean);
		return response;
	}
	
	public Response getCandidateStatus(String hostName){
		String reqBean = "[{\"id\": \""+ReadingServiceEndPointsProperties.getServiceEndPoint("GetStatusForRequisitionId")+"\",\"statusDisplay\": \"\"}]";
		
		String serviceEndPoint = getCandidateStatusUrl.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);

		Entity<String> bean = Entity.entity(reqBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, bean);
		return response;
	}
	public Response getCandidateStatusBlank(String hostName){
		String reqBean = "[{\"id\": \"\",\"statusDisplay\": \"\"}]";
		
		String serviceEndPoint = getCandidateStatusUrl.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);

		Entity<String> bean = Entity.entity(reqBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, bean);
		return response;
	}
	
	public Response getCandidateForParticularStatus(String hostName){
		String reqBean = "{\"searchInput\": {\"searchQueryString\": \"\",\"searchAttributeMap\": { }},\"pageInfo\": {},\"requisitionId\": \""+ReadingServiceEndPointsProperties.getServiceEndPoint("GetStatusForRequisitionId")+"\",\"status\": \"\"}";
		
		String serviceEndPoint = canditesListUrl.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);

		Entity<String> bean = Entity.entity(reqBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, bean);
		return response;
	}
	
	public Response getCandidateForParticularStatusWithoutReq(String hostName){
		String reqBean = "{\"searchInput\": {\"searchQueryString\": \"\",\"searchAttributeMap\": { }},\"pageInfo\": {},\"requisitionId\": \"\",\"status\": \"\"}";
		
		String serviceEndPoint = canditesListUrl.replaceAll("hostAddress", hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);

		Entity<String> bean = Entity.entity(reqBean, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, bean);
		return response;
	}
}