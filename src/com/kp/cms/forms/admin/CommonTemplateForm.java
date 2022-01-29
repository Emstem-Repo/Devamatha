package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Sports;
import com.kp.cms.forms.BaseActionForm;

public class CommonTemplateForm extends BaseActionForm {
	
	private String regNoFrom;
	private String regNoTo;
	private List<String> messageList;	
	private String date;
	private String attempts;
	private String printPage;
	private String method;
	private String academicYear;
	private String startDate;
	private String endDate;
	private String sportsId;
	private String examDate;
	private String regNo;
	private String examRegNo;
	private String examYear;
	private String result;
	private String subjectone;
	private String subjecttwo;
	private String subjectthree;
	private String subjectfore;
	private String subjectfive;
	private String subjectsix;
	private String ncc;
	private String motherTongue;
	private String campYear;
	
	
	public String getRegNoFrom() {
		return regNoFrom;
	}
	public String getRegNoTo() {
		return regNoTo;
	}
	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}
	public void setRegNoTo(String regNoTo) {
		this.regNoTo = regNoTo;
	}
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		regNoFrom = null;
		regNoTo = null;
		attempts = null;
		printPage = null;
		sportsId = null;
		startDate = null;
		endDate =null;
		examDate = null;
		regNo = null;
		examRegNo = null;
		examYear = null;
		result = null;
		subjectone = null;
		subjecttwo = null;
		subjectthree = null;
		subjectfore = null;
		subjectfive = null;
		subjectsix = null;
		ncc = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAttempts() {
		return attempts;
	}
	public void setAttempts(String attempts) {
		this.attempts = attempts;
	}
	public String getPrintPage() {
		return printPage;
	}
	public void setPrintPage(String printPage) {
		this.printPage = printPage;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
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
	public String getSportsId() {
		return sportsId;
	}
	public void setSportsId(String sportsId) {
		this.sportsId = sportsId;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	
	public String getExamRegNo() {
		return examRegNo;
	}
	public void setExamRegNo(String examRegNo) {
		this.examRegNo = examRegNo;
	}
	public String getExamYear() {
		return examYear;
	}
	public void setExamYear(String examYear) {
		this.examYear = examYear;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getSubjectone() {
		return subjectone;
	}
	public void setSubjectone(String subjectone) {
		this.subjectone = subjectone;
	}
	public String getSubjecttwo() {
		return subjecttwo;
	}
	public void setSubjecttwo(String subjecttwo) {
		this.subjecttwo = subjecttwo;
	}
	public String getSubjectthree() {
		return subjectthree;
	}
	public void setSubjectthree(String subjectthree) {
		this.subjectthree = subjectthree;
	}
	public String getSubjectfore() {
		return subjectfore;
	}
	public void setSubjectfore(String subjectfore) {
		this.subjectfore = subjectfore;
	}
	public String getSubjectfive() {
		return subjectfive;
	}
	public void setSubjectfive(String subjectfive) {
		this.subjectfive = subjectfive;
	}
	public String getSubjectsix() {
		return subjectsix;
	}
	public void setSubjectsix(String subjectsix) {
		this.subjectsix = subjectsix;
	}
	public String getNcc() {
		return ncc;
	}
	public void setNcc(String ncc) {
		this.ncc = ncc;
	}
	public String getMotherTongue() {
		return motherTongue;
	}
	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}
	public String getCampYear() {
		return campYear;
	}
	public void setCampYear(String campYear) {
		this.campYear = campYear;
	}
	
}
