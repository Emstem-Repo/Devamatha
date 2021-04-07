package com.kp.cms.forms.phd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.phd.PhdStudyAggrementTO;

public class PhdStudyAggrementForm extends BaseActionForm {
    
	private int id;
	private String registerNo;
	private String studentId;
	private String studentName;
	private String courseName;
	private String courseId;
	private String batch;
	private String guide;
	private String guideId;
	private String guideEmpaneNo;
	private String coGuide;
	private String coGuideId;
	private String coGuideEmpaneNo;
	private String signedOn;
	private List<PhdStudyAggrementTO> studentDetails;
	
	
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
        this.studentId=null;
        this.studentName=null;
        this.courseId=null;
        this.courseName=null;
        this.studentName=null;
        this.batch=null;
        this.guide=null;
        this.guideId=null;
        this.guideEmpaneNo=null;
        this.coGuide=null;
        this.coGuideId=null;
        this.coGuideEmpaneNo=null;
        this.signedOn=null;
        this.studentDetails=null;
   }
public void clearPage1() 
    {
   this.guide=null;
   this.guideId=null;
   this.guideEmpaneNo=null;
   this.coGuideId=null;
   this.coGuide=null;
   this.coGuideEmpaneNo=null;
   this.signedOn=null;
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

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public String getGuideEmpaneNo() {
		return guideEmpaneNo;
	}

	public void setGuideEmpaneNo(String guideEmpaneNo) {
		this.guideEmpaneNo = guideEmpaneNo;
	}

	public String getCoGuide() {
		return coGuide;
	}

	public void setCoGuide(String coGuide) {
		this.coGuide = coGuide;
	}

	public String getCoGuideEmpaneNo() {
		return coGuideEmpaneNo;
	}

	public void setCoGuideEmpaneNo(String coGuideEmpaneNo) {
		this.coGuideEmpaneNo = coGuideEmpaneNo;
	}

	public String getSignedOn() {
		return signedOn;
	}

	public void setSignedOn(String signedOn) {
		this.signedOn = signedOn;
	}

	public List<PhdStudyAggrementTO> getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(List<PhdStudyAggrementTO> studentDetails) {
		this.studentDetails = studentDetails;
	}

	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public String getCoGuideId() {
		return coGuideId;
	}

	public void setCoGuideId(String coGuideId) {
		this.coGuideId = coGuideId;
	}

	
}
