package com.kp.cms.forms.examallotment;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.InvigilatorsDatewiseExemptionTO;
import com.kp.cms.to.examallotment.InvigilatorsForExamTo;

public class ExamInviligatorAllotmentForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String examId;
	private String workLocationId;
	private String academicYear;
	private String fromDate;
	private String toDate;
	private String allotmentDate;
	private String allotmentSession;
	private Map<String,String> examMap;
	private Map<String,String> workLocationMap;
	private String examType;
	private String previousDate;
	private List<InvigilatorsForExamTo> availableList;
	private List<InvigilatorsDatewiseExemptionTO> datewiseExemptionList;
	private int examSessionId;
	private int orderNo;
	private String sessionDescription;
	private String tempyear;
	
	
	
	public String getTempyear() {
		return tempyear;
	}

	public void setTempyear(String tempyear) {
		this.tempyear = tempyear;
	}

	public List<InvigilatorsDatewiseExemptionTO> getDatewiseExemptionList() {
		return datewiseExemptionList;
	}

	public void setDatewiseExemptionList(
			List<InvigilatorsDatewiseExemptionTO> datewiseExemptionList) {
		this.datewiseExemptionList = datewiseExemptionList;
	}

	public void resetFields()
	{
		this.academicYear=null;
		this.examId=null;
		this.allotmentDate=null;
		this.fromDate=null;
		this.toDate=null;
		this.workLocationId=null;
		this.examType=null;
		this.allotmentDate=null;
		this.allotmentSession=null;
		this.previousDate=null;
		
	}

	public List<InvigilatorsForExamTo> getAvailableList() {
		return availableList;
	}

	public void setAvailableList(List<InvigilatorsForExamTo> availableList) {
		this.availableList = availableList;
	}

	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public String getExamId() {
		return examId;
	}




	public void setExamId(String examId) {
		this.examId = examId;
	}




	public String getWorkLocationId() {
		return workLocationId;
	}




	public void setWorkLocationId(String workLocationId) {
		this.workLocationId = workLocationId;
	}




	public String getAcademicYear() {
		return academicYear;
	}




	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}




	public String getFromDate() {
		return fromDate;
	}




	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}




	public String getToDate() {
		return toDate;
	}




	public void setToDate(String toDate) {
		this.toDate = toDate;
	}




	public Map<String, String> getExamMap() {
		return examMap;
	}




	public void setExamMap(Map<String, String> examMap) {
		this.examMap = examMap;
	}




	public Map<String, String> getWorkLocationMap() {
		return workLocationMap;
	}




	public void setWorkLocationMap(Map<String, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}


	public String getAllotmentDate() {
		return allotmentDate;
	}


	public void setAllotmentDate(String allotmentDate) {
		this.allotmentDate = allotmentDate;
	}


	public String getAllotmentSession() {
		return allotmentSession;
	}


	public void setAllotmentSession(String allotmentSession) {
		this.allotmentSession = allotmentSession;
	}


	public String getExamType() {
		return examType;
	}


	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getPreviousDate() {
		return previousDate;
	}

	public void setPreviousDate(String previousDate) {
		this.previousDate = previousDate;
	}

	public int getExamSessionId() {
		return examSessionId;
	}

	public void setExamSessionId(int examSessionId) {
		this.examSessionId = examSessionId;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getSessionDescription() {
		return sessionDescription;
	}

	public void setSessionDescription(String sessionDescription) {
		this.sessionDescription = sessionDescription;
	}


}
