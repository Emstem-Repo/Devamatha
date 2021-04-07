package com.kp.cms.forms.sap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.sap.ExamScheduleTo;

public class ExamScheduleForm extends BaseActionForm{

	private String examDate;
	private String session;
	private String month;
	private Map<Integer,String> workLocationMap;
	private Map<Integer,String> venueMap;
	private List<ExamScheduleTo> examSchToList;
	private String sessionOrder;
	private String examScheduleToListSize;
	private Map<Integer,String> mainTeachersMap;
	private List<ExamScheduleTo> searchExamSchToList;
	private int examScheduleDateId;
	private String mode;
	private Boolean examSchTo;
	private int deleteCount;
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void clear() {
		this.examSchToList=null;
		this.searchExamSchToList=null;
		this.examDate=null;
		this.session=null;
		this.venueMap=null;
		this.sessionOrder=null;
		this.examScheduleToListSize=null;
		this.mode="add";
		this.examScheduleDateId=0;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Map<Integer, String> getWorkLocationMap() {
		return workLocationMap;
	}

	public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}

	public Map<Integer, String> getVenueMap() {
		return venueMap;
	}

	public void setVenueMap(Map<Integer, String> venueMap) {
		this.venueMap = venueMap;
	}

	public List<ExamScheduleTo> getExamSchToList() {
		return examSchToList;
	}

	public void setExamSchToList(List<ExamScheduleTo> examSchToList) {
		this.examSchToList = examSchToList;
	}

	public String getSessionOrder() {
		return sessionOrder;
	}

	public void setSessionOrder(String sessionOrder) {
		this.sessionOrder = sessionOrder;
	}

	public String getExamScheduleToListSize() {
		return examScheduleToListSize;
	}

	public void setExamScheduleToListSize(String examScheduleToListSize) {
		this.examScheduleToListSize = examScheduleToListSize;
	}

	public Map<Integer, String> getMainTeachersMap() {
		return mainTeachersMap;
	}

	public void setMainTeachersMap(Map<Integer, String> mainTeachersMap) {
		this.mainTeachersMap = mainTeachersMap;
	}

	public List<ExamScheduleTo> getSearchExamSchToList() {
		return searchExamSchToList;
	}

	public void setSearchExamSchToList(List<ExamScheduleTo> searchExamSchToList) {
		this.searchExamSchToList = searchExamSchToList;
	}

	public int getExamScheduleDateId() {
		return examScheduleDateId;
	}

	public void setExamScheduleDateId(int examScheduleDateId) {
		this.examScheduleDateId = examScheduleDateId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Boolean getExamSchTo() {
		return examSchTo;
	}

	public void setExamSchTo(Boolean examSchTo) {
		this.examSchTo = examSchTo;
	}

	public int getDeleteCount() {
		return deleteCount;
	}

	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}
	

}
