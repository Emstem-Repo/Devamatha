package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamEvaluatorTypeMarksTo;
import com.kp.cms.to.exam.ExamMarksVerificationCorrectionTo;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamMarksVerificationCorrectionForm extends BaseActionForm{
	private String examId;
	private String registerNo;
	private Map<Integer, String> examMap;
	private int studentId;
	private List<ExamEvaluatorTypeMarksTo> verificationlist;
	private boolean flag;
	private String className;
	private String studentName;
	private String examType;
	private String year;
	private String focusValue;
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public Map<Integer, String> getExamMap() {
		return examMap;
	}
	public void setExamMap(Map<Integer, String> examMap) {
		this.examMap = examMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	/**
	 * 
	 */
	public void resetFields() {
		this.registerNo=null;
		this.examId=null;
		this.examMap=null;
		this.verificationlist =null;
		this.flag=false;
		this.examType="Regular";
		this.year=null;
	}
	public void resetFieldsForSaveData() {
		this.registerNo=null;
		//this.examId=null;
		this.examMap=null;
		this.verificationlist =null;
		this.flag=false;
		this.focusValue="regNo";
	}
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public List<ExamEvaluatorTypeMarksTo> getVerificationlist() {
		return verificationlist;
	}
	public void setVerificationlist(List<ExamEvaluatorTypeMarksTo> verificationlist) {
		this.verificationlist = verificationlist;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getFocusValue() {
		return focusValue;
	}
	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}
	
}
