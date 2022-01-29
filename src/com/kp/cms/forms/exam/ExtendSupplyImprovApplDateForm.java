package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExtendSupplyImprApplDateTo;

public class ExtendSupplyImprovApplDateForm extends BaseActionForm{
	private String examId;
	private Map<Integer, String> examMap;
	private String examType;
	private List<ExtendSupplyImprApplDateTo> toList;
	private String extendedEndDate;
	private String extendedFineEndDate;
	private String extendedSuperFineEndDate;
	private String fineAmount;
	private String superFineAmount;
	private String extendedFineStartDate;
	private String extendedSuperFineStartDate;
	
	public String getExtendedEndDate() {
		return extendedEndDate;
	}
	public void setExtendedEndDate(String extendedEndDate) {
		this.extendedEndDate = extendedEndDate;
	}
	public List<ExtendSupplyImprApplDateTo> getToList() {
		return toList;
	}
	public void setToList(List<ExtendSupplyImprApplDateTo> toList) {
		this.toList = toList;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public Map<Integer, String> getExamMap() {
		return examMap;
	}
	public void setExamMap(Map<Integer, String> examMap) {
		this.examMap = examMap;
	}
	public String getExtendedFineEndDate() {
		return extendedFineEndDate;
	}
	public void setExtendedFineEndDate(String extendedFineEndDate) {
		this.extendedFineEndDate = extendedFineEndDate;
	}
	public String getExtendedSuperFineEndDate() {
		return extendedSuperFineEndDate;
	}
	public void setExtendedSuperFineEndDate(String extendedSuperFineEndDate) {
		this.extendedSuperFineEndDate = extendedSuperFineEndDate;
	}
	public String getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(String fineAmount) {
		this.fineAmount = fineAmount;
	}
	public String getSuperFineAmount() {
		return superFineAmount;
	}
	public void setSuperFineAmount(String superFineAmount) {
		this.superFineAmount = superFineAmount;
	}
	public String getExtendedFineStartDate() {
		return extendedFineStartDate;
	}
	public void setExtendedFineStartDate(String extendedFineStartDate) {
		this.extendedFineStartDate = extendedFineStartDate;
	}
	public String getExtendedSuperFineStartDate() {
		return extendedSuperFineStartDate;
	}
	public void setExtendedSuperFineStartDate(String extendedSuperFineStartDate) {
		this.extendedSuperFineStartDate = extendedSuperFineStartDate;
	}
	
	@Override
	public void clear() {
		this.extendedEndDate=null;
		this.extendedFineEndDate=null;
		this.extendedFineStartDate=null;
		this.extendedSuperFineEndDate=null;
		this.extendedSuperFineStartDate=null;
		this.fineAmount=null;
		this.superFineAmount=null;
		this.examId=null;
		this.examMap=null;
		this.toList=null;
	}

}
