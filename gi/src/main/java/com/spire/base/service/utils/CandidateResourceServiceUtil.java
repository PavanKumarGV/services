package com.spire.base.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spire.talent.gi.beans.CandidateStatsRequestBean;

import spire.talent.gi.beans.GetCandidateRequestBean;
import spire.talent.gi.beans.GetCandidateRequestBean.ProjectionType;

public class CandidateResourceServiceUtil {

	public static CandidateStatsRequestBean getCandidateStats(String rfr,String rfr1){
		CandidateStatsRequestBean req = new CandidateStatsRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add(rfr);
		lst.add(rfr1);
		req.setRequisitionIdList(lst);
		req.setAttribute("status");
		return req;
	}
	
	public static CandidateStatsRequestBean getCandidateStatsGender(String rfr,String rfr1){
		CandidateStatsRequestBean req = new CandidateStatsRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add(rfr);
		lst.add(rfr1);
		req.setRequisitionIdList(lst);
		req.setAttribute("genderDisplay");
		return req;
	}
	
	public static CandidateStatsRequestBean getCandidateStatsBlank(){
		CandidateStatsRequestBean req = new CandidateStatsRequestBean();
		return req;
	}
	
	public static CandidateStatsRequestBean getCandidateStatsReqId(String rfr){
		CandidateStatsRequestBean req = new CandidateStatsRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add(rfr);
		req.setRequisitionIdList(lst);
		return req;
	}
	
	public static CandidateStatsRequestBean getCandidateStatsAttribute(){
		CandidateStatsRequestBean req = new CandidateStatsRequestBean();
		req.setAttribute("status");
		return req;
	}
	
	public static CandidateStatsRequestBean getCandidateStatsInvalid(){
		CandidateStatsRequestBean req = new CandidateStatsRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add("asdfgh");
		lst.add("asdfghjk");
		req.setRequisitionIdList(lst);
		req.setAttribute("123456");
		return req;
	}
	
	public static GetCandidateRequestBean getCandidateListBasic(String cId){
		GetCandidateRequestBean req = new GetCandidateRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add(cId);
		req.setCandidateIds(lst);
		req.setProjectionType(ProjectionType.BASIC);
		return req;
	}
	
	public static GetCandidateRequestBean getCandidateListFull(String cId){
		GetCandidateRequestBean req = new GetCandidateRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add(cId);
		req.setCandidateIds(lst);
		req.setProjectionType(ProjectionType.FULL);
		return req;
	}
	
	public static GetCandidateRequestBean getCandidateListCustom(String cId){
		GetCandidateRequestBean req = new GetCandidateRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add(cId);
		req.setCandidateIds(lst);
		req.setProjectionType(ProjectionType.CUSTOM);
		return req;
	}
	
	public static GetCandidateRequestBean getCandidateListBlank(){
		GetCandidateRequestBean req = new GetCandidateRequestBean();
		return req;
	}
	
	public static GetCandidateRequestBean getCandidateListBlankCId(){
		GetCandidateRequestBean req = new GetCandidateRequestBean();
		req.setProjectionType(ProjectionType.CUSTOM);
		return req;
	}
	
	public static GetCandidateRequestBean getCandidateListBlankPojectiontype(String cId){
		GetCandidateRequestBean req = new GetCandidateRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add(cId);
		req.setCandidateIds(lst);
		return req;
	}
	
	public static GetCandidateRequestBean getCandidateListInvalidCandidateId(String cId){
		GetCandidateRequestBean req = new GetCandidateRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add(cId);
		req.setCandidateIds(lst);
		return req;
	}
	
	public static GetCandidateRequestBean getCandidateListInvalidProjectionType(String cId){
		GetCandidateRequestBean req = new GetCandidateRequestBean();
		List<String> lst = new ArrayList<String>();
		lst.add(cId);
		req.setCandidateIds(lst);
		req.setProjectionType(ProjectionType.INVALID);
		return req;
	}
	
	
	
	
	
}
