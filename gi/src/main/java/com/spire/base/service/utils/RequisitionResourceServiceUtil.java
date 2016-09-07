package com.spire.base.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spire.talent.gi.beans.RequisitionStatusBean;
import spire.talent.gi.beans.SearchRequisitionRequestBean;

public class RequisitionResourceServiceUtil {

	public static SearchRequisitionRequestBean getSearchRequisition() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("Open");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		System.out.println(searchReqBean.getInSearchCriteria());
		// searchReqBean.setExprncFromMnth(1);
		return searchReqBean;
	}

	public static SearchRequisitionRequestBean getSearchRequisitionWithoutCriteria() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		return searchReqBean;
	}
/**
 * priti 
 * @return
 */
	public static SearchRequisitionRequestBean getCandidateStasRequisition() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("Open");
		list.add("Closed");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		System.out.println(searchReqBean.getInSearchCriteria());
		searchReqBean.setExprncFromMnth(0);
		searchReqBean.setExprncToMnth(100);

		return searchReqBean;

	}
	/**
	 * priti-only with one insearchcriteria.
	 * @return 
	 */
	public static SearchRequisitionRequestBean getCandidateStasRequisitionwithInsearchcriteria()
	{
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("Open");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		System.out.println(searchReqBean.getInSearchCriteria());
		return searchReqBean;
		
	}
	/**
	 * priti-with insearchcriteria and calculatedrecordcount false
	 * @return
	 */
	public static SearchRequisitionRequestBean getCandidateStasRequisitionwithsearchandcalculatedrecordfalse()
	{
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("Open");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		System.out.println(searchReqBean.getInSearchCriteria());
		searchReqBean.setCalculateRecordCount(false);
		return searchReqBean;
		
		
	}
	/**
	 * priti-with insearchcriteria and calculatedrecordcount true
	 * @return
	 */
	public static SearchRequisitionRequestBean getCandidateStasRequisitionwithsearchandcalculatedrecordtrue()
	{
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("Open");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		System.out.println(searchReqBean.getInSearchCriteria());
		searchReqBean.setCalculateRecordCount(true);
		return searchReqBean;
		
		
	}
	/**
	 * priti -without any parameter candidatestas
	 * @return
	 */
	

	public static SearchRequisitionRequestBean getCandiadteStasRequisitionWithoutCriteria() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		return searchReqBean;
	}

	public static SearchRequisitionRequestBean getOpenNClosedRequisitionWithExp() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("open");
		list.add("Closed");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		System.out.println(searchReqBean.getInSearchCriteria());
		searchReqBean.setExprncFromMnth(24);
		searchReqBean.setExprncToMnth(48);
		return searchReqBean;
	}

	public static SearchRequisitionRequestBean getRequisitionWithCount() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("Open");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		searchReqBean.setCalculateRecordCount(true);
		return searchReqBean;
	}

	public static SearchRequisitionRequestBean getRequisitionWithCountFalse() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("Open");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		searchReqBean.setCalculateRecordCount(false);
		return searchReqBean;
	}
	
	public static RequisitionStatusBean changereqStatus(String rid,String status){
		RequisitionStatusBean reqStstusChange = new RequisitionStatusBean();
		reqStstusChange.setId(rid);
		reqStstusChange.setStatusDisplay(status);
		return reqStstusChange;
		
	}
}
