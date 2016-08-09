package com.spire.candidateNotes;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.service.consumers.CandidateNotesConsumers;
import com.spire.service.consumers.CandidateResourcesConsumer;

public class CandidateNotesTestPlan extends TestPlan {

	String hostName;
	CandidateNotesConsumers candnoteConsumer = null;

	/**
	 * Passing HostName,UserName and Password from the xml.
	 */

	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
		
	}
	
	@Test(groups = { "sanity" , "verifycandidatenoteslistRequest" })
	public void verifycandidatenoteslistRequest(){
		candnoteConsumer = new CandidateNotesConsumers();
		candnoteConsumer.getCandidatenoteslist(hostName);
		
	}
	@Test(groups = { "sanity" , "verifycandidatenotessearchRequest"})
	public void verifycandidatenotessearchRequest()
	{
		candnoteConsumer = new CandidateNotesConsumers();
		candnoteConsumer.getCandidatenotesearch(hostName);
	}
	// in candidate notes list,Show interval cannot be null when user pass entity id without interval, asserting the error response.
	@Test(groups = { "sanity" , "verifycandidatenotelistwithoutintervalRequest"})
	public void verifycandidatenotelistwithoutintervalRequest()
	{
		candnoteConsumer = new CandidateNotesConsumers();
		
		
		Response responsebody=candnoteConsumer.getCandidatenoteslistwithoutInterval(hostName);
		System.out.println(responsebody);
		Assert.assertEquals(200, responsebody.getStatus());
		String response=responsebody.readEntity(String.class);
		Assert.assertTrue(response.contains("interval cannot be null"));
	}
	// in candidate note list,Show entityId cannot be null when user pass interval without entity id, asserting the error response.
		@Test(groups = { "sanity" , "verifycandidatenotelistwithoutentityidRequest"})
		public void verifycandidatenotelistwithoutentityidRequest()
		{
			candnoteConsumer = new CandidateNotesConsumers();
			Response responsebody=candnoteConsumer.getCandidatenoteslistwithoutEntityid(hostName);
			System.out.println(responsebody);
			Assert.assertEquals(200, responsebody.getStatus());
			String response=responsebody.readEntity(String.class);
			Assert.assertTrue(response.contains("entityId cannot be null"));
		}
	//in candidate note search,Show searchText cannot be null when user pass entityid without searchtext,asserting the response.
	@Test(groups = { "sanity" , "verifycandidatenotesearchwithoutsearchtextRequest"})
	public void verifycandidatenotesearchwithoutsearchtextRequest()
	{
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody=candnoteConsumer.getCandidatenotessearchwithoutsearchtext(hostName);
		System.out.println(responsebody);
		Assert.assertEquals(200, responsebody.getStatus());
		String response=responsebody.readEntity(String.class);
		Assert.assertTrue(response.contains("searchText cannot be null"));
	}
	//in candidate note search,Show entityId cannot be null when user pass searchtext without entityid,asserting the response.
		@Test(groups = { "sanity" , "verifycandidatenotesearchwithoutentityidRequest"})
		public void verifycandidatenotesearchwithoutentityidRequest()
		{
			candnoteConsumer = new CandidateNotesConsumers();
			Response responsebody=candnoteConsumer.getCandidatenotessearchwithoutentityid(hostName);
			System.out.println(responsebody);
			Assert.assertEquals(200, responsebody.getStatus());
			String response=responsebody.readEntity(String.class);
			Assert.assertTrue(response.contains("entityId cannot be null"));
		}
}