package com.spire.service.consumers;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;

import spire.commons.userservice.bean.LoginResponseBean;

import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;

public class SearchResourcesConsumer extends BaseServiceConsumerNew {

	String endPointURL = getServiceEndPoint("SEARCH_CANDI_SAVED_SEARCH");
	String endPointURL1 = getServiceEndPoint("SIMILAR_PROFILES").replace(":",
			"%3A");
	String endPointURLSuggest = getServiceEndPoint("SEARCH_SUGGEST");
	String endPointURLSuggestValidation = getServiceEndPoint("SUGGEST_VALIDATION");
	
	public SearchResourcesConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		       System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
		}

	
	public void getSemilarProfiles(String hostName)
			throws ClientProtocolException, IOException {
		String serviceEndPoint = endPointURL1.replaceAll("hostAddress",
				hostName);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response1 = executeGET(serviceEndPoint);
		if (response1.getStatus() == 200) {
			System.out.println("********** pass **************");
		} else {
			Assert.fail();
		}

	}
	
public Response getSuggest(String hostName) throws ClientProtocolException, IOException {
		
				String serviceEndPoint = endPointURLSuggest.replaceAll("hostAddress", hostName);		
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
				if (response.getStatus() == 200) {
			System.out.println("***** PASS ******RESPONSE CODE >>" + response.getStatus());
					
		} else {
			Assert.fail();
		}
				Logging.log("Response Code >>"+response.getStatus());
				return response;
	
	}

public void suggestValidation(String hostName) throws ClientProtocolException, IOException {
	
	String serviceEndPoint = endPointURLSuggestValidation.replaceAll("hostAddress", hostName);		
System.out.println(" EndPoint URL >>" + serviceEndPoint);
Response response = executeGET(serviceEndPoint);
	if (response.getStatus() == 500) {
System.out.println("********** PASS **************");
} else {
Assert.fail();
}

}



}
