package com.kp.cms.forms.phd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.phd.PhdCompletionDetailsTO;

public class PhdCompletionDetailsForm extends BaseActionForm {
	
	private int id;
	private String registerNo;
	private String studentId;
	private String studentName;
	private String courseName;
	private String courseId;
	private String batch;
	private String vivaVoice;
	private String title;
	private String discipline;
	private String oldVivaVoice;
	private String oldTitle;
	private String oldDiscipline;
	private List<PhdCompletionDetailsTO> studentDetails;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void clearPage() 
    {
	this.id=0;
   this.registerNo=null;
   this.title=null;
   this.discipline=null;
   this.studentId=null;
   this.studentName=null;
   this.courseId=null;
   this.courseName=null;
   this.studentName=null;
   this.batch=null;
   this.studentDetails=null;
   this.oldDiscipline=null;
   this.oldTitle=null;
   this.oldVivaVoice=null;
    }
	
	public void clearPage1() 
    {
		this.vivaVoice=null;
		this.title=null;
		this.discipline=null;
    }

	public String getOldDiscipline() {
		return oldDiscipline;
	}

	public void setOldDiscipline(String oldDiscipline) {
		this.oldDiscipline = oldDiscipline;
	}

	public String getOldVivaVoice() {
		return oldVivaVoice;
	}

	public void setOldVivaVoice(String oldVivaVoice) {
		this.oldVivaVoice = oldVivaVoice;
	}

	public String getOldTitle() {
		return oldTitle;
	}

	public void setOldTitle(String oldTitle) {
		this.oldTitle = oldTitle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getVivaVoice() {
		return vivaVoice;
	}

	public void setVivaVoice(String vivaVoice) {
		this.vivaVoice = vivaVoice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public List<PhdCompletionDetailsTO> getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(List<PhdCompletionDetailsTO> studentDetails) {
		this.studentDetails = studentDetails;
	}
	
}
