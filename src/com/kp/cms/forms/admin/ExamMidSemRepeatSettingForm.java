package com.kp.cms.forms.admin;

import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ExamMidSemRepeatSettingTo;

public class ExamMidSemRepeatSettingForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String applicationOpenFrom;
	private String applicationOpenTill;
	private String feesPerSubject;
	private String feePaymentTill;
	private Map<Integer, String> examNameMap;
	private Map<Integer, String> examTypeMap;
	private int repeatSettingId;
	private Map<Integer, ExamMidSemRepeatSettingTo> repeatSettingMap;
	private String disableFields;
	private String origAcademicYear;
	private String origExamType;
	private String origExamName;
	private String origApplicationStartDate;
	private String origApplicationEndDate;
	private String origFeePaymentEndDate;
	private String origFeePerSubject;
	
	

	public String getOrigAcademicYear() {
		return origAcademicYear;
	}
	public void setOrigAcademicYear(String origAcademicYear) {
		this.origAcademicYear = origAcademicYear;
	}
	public String getOrigExamType() {
		return origExamType;
	}
	public void setOrigExamType(String origExamType) {
		this.origExamType = origExamType;
	}
	public String getOrigExamName() {
		return origExamName;
	}
	public void setOrigExamName(String origExamName) {
		this.origExamName = origExamName;
	}
	public String getOrigApplicationStartDate() {
		return origApplicationStartDate;
	}
	public void setOrigApplicationStartDate(String origApplicationStartDate) {
		this.origApplicationStartDate = origApplicationStartDate;
	}
	public String getOrigApplicationEndDate() {
		return origApplicationEndDate;
	}
	public void setOrigApplicationEndDate(String origApplicationEndDate) {
		this.origApplicationEndDate = origApplicationEndDate;
	}
	public String getOrigFeePaymentEndDate() {
		return origFeePaymentEndDate;
	}
	public void setOrigFeePaymentEndDate(String origFeePaymentEndDate) {
		this.origFeePaymentEndDate = origFeePaymentEndDate;
	}
	public String getOrigFeePerSubject() {
		return origFeePerSubject;
	}
	public void setOrigFeePerSubject(String origFeePerSubject) {
		this.origFeePerSubject = origFeePerSubject;
	}
	public String getDisableFields() {
		return disableFields;
	}
	public void setDisableFields(String disableFields) {
		this.disableFields = disableFields;
	}
	public Map<Integer, ExamMidSemRepeatSettingTo> getRepeatSettingMap() {
		return repeatSettingMap;
	}
	public void setRepeatSettingMap(
			Map<Integer, ExamMidSemRepeatSettingTo> repeatSettingMap) {
		this.repeatSettingMap = repeatSettingMap;
	}
	public int getRepeatSettingId() {
		return repeatSettingId;
	}
	public void setRepeatSettingId(int repeatSettingId) {
		this.repeatSettingId = repeatSettingId;
	}
	public Map<Integer, String> getExamTypeMap() {
		return examTypeMap;
	}
	public void setExamTypeMap(Map<Integer, String> examTypeMap) {
		this.examTypeMap = examTypeMap;
	}
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}
	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
	public String getApplicationOpenFrom() {
		return applicationOpenFrom;
	}
	public void setApplicationOpenFrom(String applicationOpenFrom) {
		this.applicationOpenFrom = applicationOpenFrom;
	}
	public String getApplicationOpenTill() {
		return applicationOpenTill;
	}
	public void setApplicationOpenTill(String applicationOpenTill) {
		this.applicationOpenTill = applicationOpenTill;
	}
	public String getFeesPerSubject() {
		return feesPerSubject;
	}
	public void setFeesPerSubject(String feesPerSubject) {
		this.feesPerSubject = feesPerSubject;
	}
	public String getFeePaymentTill() {
		return feePaymentTill;
	}
	public void setFeePaymentTill(String feePaymentTill) {
		this.feePaymentTill = feePaymentTill;
	}
	
	public void reset(){
		this.applicationOpenFrom=null;
		this.applicationOpenTill=null;
		this.feePaymentTill=null;
		this.feesPerSubject=null;
		this.examNameMap=null;
		this.examTypeMap=null;
		this.repeatSettingId=0;
		this.repeatSettingMap=null;
		this.disableFields=null;
		this.origAcademicYear=null;
		this.origApplicationEndDate=null;
		this.origApplicationStartDate=null;
		this.origExamName=null;
		this.origExamType=null;
		this.origFeePaymentEndDate=null;
		this.origFeePerSubject=null;
		super.clear3();
	}
	
	
	
	
	

}
