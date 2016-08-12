package com.spire.base.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static SearchRequisitionRequestBean getCandidateStasRequisition() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("open");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		System.out.println(searchReqBean.getInSearchCriteria());
		searchReqBean.setOffset(1);
		searchReqBean.setLimit(5);
		searchReqBean.setExprncToMnth(12);

		return searchReqBean;

	}

	public static SearchRequisitionRequestBean getCandiadteStasRequisitionWithoutCriteria() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		return searchReqBean;
	}

	public static SearchRequisitionRequestBean getOpenNClosedRequisitionWithExp() {
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("Open");
		list.add("Closed");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("statusDisplay", list);
		searchReqBean.setInSearchCriteria(map);
		System.out.println(searchReqBean.getInSearchCriteria());
		searchReqBean.setExprncFromMnth(0);
		searchReqBean.setExprncToMnth(5);
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
}
