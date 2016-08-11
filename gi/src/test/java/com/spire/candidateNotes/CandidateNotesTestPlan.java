package com.spire.candidateNotes;

import javax.ws.rs.core.Response;

import junit.framework.Assert;
import spire.talent.gi.beans.NoteBean;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.base.service.utils.NotesServicesUtil;
import com.spire.service.consumers.CandidateNotesConsumers;
import com.spire.service.consumers.CandidateResourcesConsumer;

public class CandidateNotesTestPlan extends TestPlan {


	String hostName;
	CandidateNotesConsumers candnoteConsumer = null;
	NoteBean noteBeanRequest = null;
	NoteBean noteBeanRequestWithBlankEntity = null;
	NoteBean noteBeanRequestWithBlankParameter = null;
	NoteBean noteBeanRequestWithOnlyEntityId = null;
	static String entityId = null;

	/**
	 * Passing HostName,UserName and Password from the xml.
	 */

	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();

	}

	/**
	 * Steps: GET list of candidate notes Validation: Asserting candidate notes
	 * in response body
	 */

	@Test(groups = { "sanity", "verifycandidatenoteslistRequest" })
	public void verifycandidatenoteslistRequest() {
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenoteslist(hostName);
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("Testing1"), "not getting entityid in the response.");
		
	}

	/**
	 * Steps: Search Note for entity Validation: Asserting candidate notes in
	 * response body
	 */
	@Test(groups = { "sanity", "verifycandidatenotessearchRequest" })
	public void verifycandidatenotessearchRequest() {
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenotesearch(hostName);
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("Testing1"), "not getting entityid in the response.");
		
	}

	/**
	 * Steps:Get candidate notes list without interval Validation:asserting the
	 * error response.
	 */

	@Test(groups = { "sanity", "verifycandidatenotelistwithoutintervalRequest" })
	public void verifycandidatenotelistwithoutintervalRequest() {
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenoteslistwithoutInterval(hostName);
		System.out.println(responsebody);
		Assert.assertEquals(200, responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("interval cannot be null"), "not getting in response body as interval cannot be null");
		
	}

	/**
	 * Steps:Get candidate notes list without Entity Id Validation:asserting the
	 * error response.
	 */
	@Test(groups = { "sanity", "verifycandidatenotelistwithoutentityidRequest" })
	public void verifycandidatenotelistwithoutentityidRequest() {
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenoteslistwithoutEntityid(hostName);
		System.out.println(responsebody);
		Assertion.assertEquals(responsebody.getStatus(), 200, "expected response as 200 but found response as:"+responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("entityId cannot be null"), "not getting in response body");
		
	}

	/**
	 * Steps:Search Note for entity without searchtext Validation:asserting the
	 * error response.
	 */

	@Test(groups = { "sanity", "verifycandidatenotesearchwithoutsearchtextRequest" })
	public void verifycandidatenotesearchwithoutsearchtextRequest() {
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenotessearchwithoutsearchtext(hostName);
		System.out.println(responsebody);
		Assertion.assertEquals(responsebody.getStatus(), 200, "expected response as 200 but found response as:"+responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("searchText cannot be null"), "not getting in the response body");
		
	}

	/**
	 * Steps:Search Note for entity without entity id Validation:asserting the
	 * error response.
	 */
	@Test(groups = { "sanity", "verifycandidatenotesearchwithoutentityidRequest" })
	public void verifycandidatenotesearchwithoutentityidRequest() {
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenotessearchwithoutentityid(hostName);
		System.out.println(responsebody);
		Assertion.assertEquals(responsebody.getStatus(), 200, "expected response as 200 but found response as:"+responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("entityId cannot be null"), "not getting in the response body");
		
	}

	/**
	 * Steps:List Note without any parameter Validation:asserting the error
	 * response.
	 */
	@Test(groups = { "sanity", "verifycandidatenotesearchwithoutanyparameterRequest" })
	public void verifycandidatenotesearchwithoutanyparameterRequest() {
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenotesearchwithoutanyparameter(hostName);
		System.out.println(responsebody);
		Assertion.assertEquals(responsebody.getStatus(), 500, "expected response as 500 but found response as:"+responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("entityId cannot be null"), "not getting in the response body");
		Assertion.assertTrue(response.contains("searchText cannot be null"), "not getting in the response body");
		
	}

	/**
	 * Steps:Search Note without any parameter Validation:asserting the error
	 * response.
	 */
	@Test(groups = { "sanity", "verifycandidatenotelistwithoutanyparameterRequest" })
	public void verifycandidatenotelistwithoutanyparameterRequest() {
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenotelistwithoutanyparameter(hostName);
		System.out.println(responsebody);
		Assertion.assertEquals(responsebody.getStatus(), 500, "expected response as 500 but found response as:"+responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("entityId cannot be null"), "not getting in the response body");
		Assertion.assertTrue(response.contains("interval cannot be null"), "not getting in the response body");
		
	}
	/**
	 * Steps:List notes with incorrect parameter
	 * Validation:asserting the error response
	 */
	@Test(groups = { "sanity", "verifycandidatenotelistwithincorrectparameterRequest" })
	public void verifycandidatenotelistwithincorrectparameterRequest()
	{
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenotelistwithincorrectparameter(hostName);
		System.out.println(responsebody);
		Assertion.assertEquals(responsebody.getStatus(), 404, "expected 404 but found response as:"+responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		Assertion.assertTrue(response.contains("The requested resource is not available"), "not matching with response body");
		
	}
	/**
	 * Steps:search notes with incorrect parameter
	 * Validation:asserting the error response
	 */
	@Test(groups = { "sanity", "verifycandidatenotelistwithincorrectparameterRequest" })
	public void verifycandidatenotesearchwithincorrectparameterRequest()
	{
		candnoteConsumer = new CandidateNotesConsumers();
		Response responsebody = candnoteConsumer.getCandidatenotesearchwithincorrectparameter(hostName);
		System.out.println(responsebody);
		Assertion.assertEquals(responsebody.getStatus(), 200, "expected 200 response but found response as:"+responsebody.getStatus());
		
		

	}

	/**
	 * @author Radharani Patra 10/08/16 Steps:Create Note alongwith entity id
	 *         Validation: Successfull Note creation in response body
	 */

	@Test(groups = { "createNotes" })
	public void createNotes() {
		noteBeanRequest = NotesServicesUtil.getNoteBean();
		entityId = noteBeanRequest.getId();
		Logging.log("Entity ID: "+entityId);
		candnoteConsumer = new CandidateNotesConsumers();
		Response response = candnoteConsumer.createNote(noteBeanRequest, hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains(entityId), "Notes creation failed");
		Logging.log("Notes Created successfully with entity id: " + entityId);

	}

	/**
	 * @author Radharani Patra 10/08/16 Steps:Create Note without entity id
	 *         Validation: asserting the error response
	 */

	@Test(groups = { "sanity", "createNotesWithBlankEntityId" })
	public void createNotesWithBlankEntityId() {
		noteBeanRequestWithBlankEntity = NotesServicesUtil.getNoteBeanWithBlankEntity();
		candnoteConsumer = new CandidateNotesConsumers();
		Response response = candnoteConsumer.createNote(noteBeanRequestWithBlankEntity, hostName);
		Assertion.assertEquals(response.getStatus(), 500, "Response not successfull: Expected 500");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps:Create Note without any parameter
	 *         Validation: asserting the error response
	 */
	@Test(groups = { "sanity", "createNotesWithBlankParameter" })
	public void createNotesWithBlankParameter() {
		noteBeanRequestWithBlankParameter = NotesServicesUtil.getNoteBeanWithBlankParameter();
		candnoteConsumer = new CandidateNotesConsumers();
		Response response = candnoteConsumer.createNote(noteBeanRequestWithBlankParameter, hostName);
		Assertion.assertEquals(response.getStatus(), 500, "Response not successfull: Expected 500");
	}

	/**
	 * @author Radharani Patra 10/08/16 Steps:Create Note with only entity id
	 *         Validation: Successfull Note creation in response body
	 */
	@Test(groups = { "sanity", "createNotesWithOnlyEntityId" })
	public void createNotesWithOnlyEntityId() {
		noteBeanRequestWithOnlyEntityId = NotesServicesUtil.getNoteBeanWithOnlyEntityId();
		String entityId1 = noteBeanRequestWithOnlyEntityId.getId();
		Logging.log("entityId: "+entityId1);
		candnoteConsumer = new CandidateNotesConsumers();
		Response response = candnoteConsumer.createNote(noteBeanRequestWithOnlyEntityId, hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull: Expected 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains("Notes SuccessFul Created"), "Notes creation failed");
		Logging.log("Notes Created successfully with entity id: " + entityId1);
	}
	
	/**
	 * @author Radharani Patra 11/08/16 Steps:Create Note with existing entity id
	 *         Validation: Successfull Note creation in response body
	 */

	@Test(groups = { "sanity", "createNotesWithDuplicateId" },dependsOnGroups={"createNotes"})
	public void createNotesWithDuplicateId() {
		noteBeanRequest = NotesServicesUtil.getNoteBeanWithExistingEntityId(entityId);
		entityId = noteBeanRequest.getId();
		Logging.log("Entity ID: "+entityId);
		candnoteConsumer = new CandidateNotesConsumers();
		Response response = candnoteConsumer.createNote(noteBeanRequest, hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		Assertion.assertTrue(responseBody.contains(entityId), "Notes creation failed");
		Logging.log("Notes Created successfully with entity id: " + entityId);

	}

}