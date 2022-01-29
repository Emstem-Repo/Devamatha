package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SyllabusTrackerForSupplementaryTo;

public class SyllabusTrackerForSupplementaryForm extends BaseActionForm{
	
	private List<SyllabusTrackerForSupplementaryTo> syllabusTrackerForSupplementaryTo;
	private int numOfChancesLeft;
	private String paperCode;
	private String admitedYear;
	private int semNo;
	private int courseid;
	private String subjectName;
	private Map<Integer, String> examNameList;
	private Map<String, String>  deanaryList;
	private String examId;
	private String deanery;
	private int program;
	private String examResult;
	private Map<Integer, String> programList;
	private String fromDate;
	private String toDate;
	private String examType;
	
	
	
	public int getNumOfChancesLeft() {
		return numOfChancesLeft;
	}
	public void setNumOfChancesLeft(int numOfChancesLeft) {
		this.numOfChancesLeft = numOfChancesLeft;
	}
	public String getPaperCode() {
		return paperCode;
	}
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}
	public String getAdmitedYear() {
		return admitedYear;
	}
	public void setAdmitedYear(String admitedYear) {
		this.admitedYear = admitedYear;
	}
	public int getSemNo() {
		return semNo;
	}
	public void setSemNo(int semNo) {
		this.semNo = semNo;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}
	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}
	public String getDeanery() {
		return deanery;
	}
	public void setDeanery(String deanery) {
		this.deanery = deanery;
	}
	public int getProgram() {
		return program;
	}
	public void setProgram(int program) {
		this.program = program;
	}
	public String getExamResult() {
		return examResult;
	}
	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}
	public Map<Integer, String> getProgramList() {
		return programList;
	}
	public void setProgramList(Map<Integer, String> programList) {
		this.programList = programList;
	}
	
	public Map<String, String> getDeanaryList() {
		return deanaryList;
	}
	public void setDeanaryList(Map<String, String> deanaryList) {
		this.deanaryList = deanaryList;
	}
	
	public List<SyllabusTrackerForSupplementaryTo> getSyllabusTrackerForSupplementaryTo() {
		return syllabusTrackerForSupplementaryTo;
	}
	public void setSyllabusTrackerForSupplementaryTo(
			List<SyllabusTrackerForSupplementaryTo> syllabusTrackerForSupplementaryTo) {
		this.syllabusTrackerForSupplementaryTo = syllabusTrackerForSupplementaryTo;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	
	public void reset() {
		this.syllabusTrackerForSupplementaryTo=null;
		this.examResult="Applied";
		this.examId=null;
		this.deanery=null;
		this.program=0;
		this.fromDate=null;
		this.toDate=null;
		this.examType="TheoryPractical";
        
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
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
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	
}
