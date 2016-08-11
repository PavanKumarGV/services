package com.spire.base.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spire.talent.gi.beans.SearchRequisitionRequestBean;

public class RequisitionResourceServiceUtil {

	
	public static SearchRequisitionRequestBean getSearchRequisition(){
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		List<String> list = new ArrayList<String>();
		list.add("open");
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		map.put("statusDisplay",list);
		searchReqBean.setInSearchCriteria(map);
		System.out.println(searchReqBean.getInSearchCriteria());
		//searchReqBean.setExprncFromMnth(1);
		return searchReqBean;
	}
	
	public static SearchRequisitionRequestBean getSearchRequisitionWithoutCriteria(){
		SearchRequisitionRequestBean searchReqBean = new SearchRequisitionRequestBean();
		return searchReqBean;
	}
}
