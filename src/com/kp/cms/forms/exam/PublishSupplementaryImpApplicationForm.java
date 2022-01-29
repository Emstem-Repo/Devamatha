package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.PublishSupplementaryImpApplicationTo;

public class PublishSupplementaryImpApplicationForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String examId;
	private String startDate;
	private String endDate;
	private String examType;
	private Map<Integer,String> examNameList;
	private String mode;
	private List<PublishSupplementaryImpApplicationTo> toList;
	private String[] classCodeIdsFrom;
	private String[] classCodeIdsTo;
	private Map<Integer, String> mapSelectedClass;
	private String[] stayClass;
	private Map<Integer, String> mapClass;
	private String extendedDate;
	private String fineStartDate;
	private String fineEndDate;
	private String superFIneStartDate;
	private String superFineEndDate;
	private String fineFee;
	private String superFineFee;
	
	
	
	public Map<Integer, String> getMapClass() {
		return mapClass;
	}
	public void setMapClass(Map<Integer, String> mapClass) {
		this.mapClass = mapClass;
	}
	public String[] getStayClass() {
		return stayClass;
	}
	public void setStayClass(String[] stayClass) {
		this.stayClass = stayClass;
	}
	public String[] getClassCodeIdsFrom() {
		return classCodeIdsFrom;
	}
	public void setClassCodeIdsFrom(String[] classCodeIdsFrom) {
		this.classCodeIdsFrom = classCodeIdsFrom;
	}
	public String[] getClassCodeIdsTo() {
		return classCodeIdsTo;
	}
	public void setClassCodeIdsTo(String[] classCodeIdsTo) {
		this.classCodeIdsTo = classCodeIdsTo;
	}
	public Map<Integer, String> getMapSelectedClass() {
		return mapSelectedClass;
	}
	public void setMapSelectedClass(Map<Integer, String> mapSelectedClass) {
		this.mapSelectedClass = mapSelectedClass;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	
	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}
	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public List<PublishSupplementaryImpApplicationTo> getToList() {
		return toList;
	}
	public void setToList(List<PublishSupplementaryImpApplicationTo> toList) {
		this.toList = toList;
	}
	
	public String getExtendedDate() {
		return extendedDate;
	}
	public void setExtendedDate(String extendedDate) {
		this.extendedDate = extendedDate;
	}
	public String getFineStartDate() {
		return fineStartDate;
	}
	public void setFineStartDate(String fineStartDate) {
		this.fineStartDate = fineStartDate;
	}
	public String getFineEndDate() {
		return fineEndDate;
	}
	public void setFineEndDate(String fineEndDate) {
		this.fineEndDate = fineEndDate;
	}
	public String getSuperFIneStartDate() {
		return superFIneStartDate;
	}
	public void setSuperFIneStartDate(String superFIneStartDate) {
		this.superFIneStartDate = superFIneStartDate;
	}
	public String getSuperFineEndDate() {
		return superFineEndDate;
	}
	public void setSuperFineEndDate(String superFineEndDate) {
		this.superFineEndDate = superFineEndDate;
	}
	public String getFineFee() {
		return fineFee;
	}
	public void setFineFee(String fineFee) {
		this.fineFee = fineFee;
	}
	public String getSuperFineFee() {
		return superFineFee;
	}
	public void setSuperFineFee(String superFineFee) {
		this.superFineFee = superFineFee;
	}
	/**
	 * 
	 */
	public void resetFields(){
		this.examId=null;
		this.examType="Supplementary";
		this.startDate=null;
		this.endDate=null;
		this.id=0;
		this.mode="add";
		this.toList=null;
		this.mapClass=null;
		this.stayClass=null;
		this.mapSelectedClass=null;
		this.extendedDate=null;
		this.fineStartDate=null;
		this.fineEndDate=null;
		this.superFIneStartDate=null;
		this.superFineEndDate=null;
		this.fineFee=null;
		this.superFineFee=null;
		
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}	
}
