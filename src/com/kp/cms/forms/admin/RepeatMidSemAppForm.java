package com.kp.cms.forms.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;

public class RepeatMidSemAppForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String id;
	private String studentId;
	private String studentName;
	private String midSemExamId;
	private String midsemExamName;
	private String registerNo;
	private String checked;
	private String tempChecked;
	private String dataAvailable;
	Map<Integer, String> midsemExamList;
	private String year;
	
	
	public void resetFields() {
		this.studentId = null;
		this.studentName = null;
		this.midSemExamId=null;
		this.midsemExamName=null;
		this.registerNo=null;
		this.year=null;
		this.dataAvailable="false";
		this.tempChecked="false";
		this.checked="false";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getMidsemExamName() {
		return midsemExamName;
	}
	public void setMidsemExamName(String midsemExamName) {
		this.midsemExamName = midsemExamName;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	public String getDataAvailable() {
		return dataAvailable;
	}
	public void setDataAvailable(String dataAvailable) {
		this.dataAvailable = dataAvailable;
	}
	public Map<Integer, String> getMidsemExamList() {
		return midsemExamList;
	}
	public void setMidsemExamList(Map<Integer, String> midsemExamList) {
		this.midsemExamList = midsemExamList;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMidSemExamId() {
		return midSemExamId;
	}
	public void setMidSemExamId(String midSemExamId) {
		this.midSemExamId = midSemExamId;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	
}
