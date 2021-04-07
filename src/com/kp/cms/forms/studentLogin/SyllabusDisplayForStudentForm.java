package com.kp.cms.forms.studentLogin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.studentLogin.SyllabusDisplayForStudentTo;

public class SyllabusDisplayForStudentForm extends BaseActionForm {
	private String registrNo;
	private List<SyllabusDisplayForStudentTo> syllabusDisplayForStudentToList;
	private int numOfChancesLeft;
	private String paperCode;
	private String admitedYear;
	private int semNo;
	private int courseid;
	private String subjectName;

	public String getRegistrNo() {
		return registrNo;
	}

	public void setRegistrNo(String registrNo) {
		this.registrNo = registrNo;
	}

	public List<SyllabusDisplayForStudentTo> getSyllabusDisplayForStudentToList() {
		return syllabusDisplayForStudentToList;
	}

	public void setSyllabusDisplayForStudentToList(
			List<SyllabusDisplayForStudentTo> syllabusDisplayForStudentToList) {
		this.syllabusDisplayForStudentToList = syllabusDisplayForStudentToList;
	}

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

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	
	public void reset() {
		this.syllabusDisplayForStudentToList=null;
		this.registrNo=null;
        
	}
}
