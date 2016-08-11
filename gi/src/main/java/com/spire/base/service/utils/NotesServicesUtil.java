package com.spire.base.service.utils;

/**@author Radharani Patra 10/08/16*/

import java.util.Date;
import java.util.Random;

import com.spire.base.controller.Logging;

import spire.talent.gi.beans.NoteBean;

public class NotesServicesUtil {

	public static NoteBean getNoteBean() {
		NoteBean noteBeanreq = new NoteBean();
		/* Preparing Note Bean Request */
		noteBeanreq.setNoteTitle("Test" + getRandom(1, 10000));
		noteBeanreq.setNoteDes("Description");
		noteBeanreq.setActivityType("ActivityType");
		noteBeanreq.setId("ID" + getRandom(1, 10000));
		noteBeanreq.setCreatedBy("Radha");
		noteBeanreq.setCreatedOn(todaysDate());
		noteBeanreq.setEntityId("EntityId" + getRandom(1, 10000));
		System.out.println(noteBeanreq.getId());
		System.out.println(noteBeanreq.getEntityId());
		return noteBeanreq;

	}

	public static int getRandom(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	public static Date todaysDate() {
		Date date = new Date();
		return date;
	}

	public static NoteBean getNoteBeanWithBlankEntity() {
		NoteBean noteBeanreq = new NoteBean();
		/* Preparing Note Bean Request */
		noteBeanreq.setNoteTitle("Test" + getRandom(1, 10000));
		noteBeanreq.setNoteDes("Description");
		noteBeanreq.setActivityType("ActivityType");
		// noteBeanreq.setId("ID"+getRandom(1, 10000));
		noteBeanreq.setCreatedBy("Radha");
		noteBeanreq.setCreatedOn(todaysDate());
		noteBeanreq.setEntityId("EntityId" + getRandom(1, 10000));
		// System.out.println(noteBeanreq.getId());
		System.out.println(noteBeanreq.getEntityId());
		return noteBeanreq;

	}

	public static NoteBean getNoteBeanWithBlankParameter() {
		NoteBean noteBeanreq = new NoteBean();
		return noteBeanreq;
	}
	
	public static NoteBean getNoteBeanWithOnlyEntityId() {
		NoteBean noteBeanreq = new NoteBean();
		noteBeanreq.setEntityId("ID" + getRandom(1, 10000));
		System.out.println(noteBeanreq.getEntityId());
		return noteBeanreq;
	}
	
	public static NoteBean getNoteBeanWithExistingEntityId(String id) {
		NoteBean noteBeanreq = new NoteBean();
		noteBeanreq.setNoteTitle("Test" + getRandom(1, 10000));
		noteBeanreq.setNoteDes("Description");
		noteBeanreq.setId(id);
		noteBeanreq.setCreatedBy("Radha");
		noteBeanreq.setCreatedOn(todaysDate());
		noteBeanreq.setEntityId("EntityId" + getRandom(1, 10000));
		System.out.println(noteBeanreq.getId());
		System.out.println(noteBeanreq.getEntityId());
		return noteBeanreq;
	}
}
