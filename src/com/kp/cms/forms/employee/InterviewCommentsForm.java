package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.AchievementsTO;
import com.kp.cms.to.employee.EdicationDetailsTO;
import com.kp.cms.to.employee.InterviewCommentsTO;
import com.kp.cms.to.employee.ProfessionalExperienceTO;

public class InterviewCommentsForm extends BaseActionForm{
	private int id;
	private int interviewCommentsId;
	private String name;
	private String email;
	private String dateOfInterview;
	private String comments;
	private String evaluatedBy;
	private String marksCards;
	private String experienceCertificate;
	
	private InterviewCommentsTO interviewCommentsTO;
	private List<InterviewCommentsTO> listOfDetails;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	

	public void setDateOfInterview(String dateOfInterview) {
		this.dateOfInterview = dateOfInterview;
	}

	public String getDateOfInterview() {
		return dateOfInterview;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMarksCards() {
		return marksCards;
	}

	public void setMarksCards(String marksCards) {
		this.marksCards = marksCards;
	}

	public String getExperienceCertificate() {
		return experienceCertificate;
	}

	public void setExperienceCertificate(String experienceCertificate) {
		this.experienceCertificate = experienceCertificate;
	}

	public void setEvaluatedBy(String evaluatedBy) {
		this.evaluatedBy = evaluatedBy;
	}

	public String getEvaluatedBy() {
		return evaluatedBy;
	}

	public void setInterviewCommentsTO(InterviewCommentsTO interviewCommentsTO) {
		this.interviewCommentsTO = interviewCommentsTO;
	}

	public InterviewCommentsTO getInterviewCommentsTO() {
		return interviewCommentsTO;
	}

	public void setListOfDetails(List<InterviewCommentsTO> listOfDetails) {
		this.listOfDetails = listOfDetails;
	}

	public List<InterviewCommentsTO> getListOfDetails() {
		return listOfDetails;
	}

	public void setInterviewCommentsId(int interviewCommentsId) {
		this.interviewCommentsId = interviewCommentsId;
	}

	public int getInterviewCommentsId() {
		return interviewCommentsId;
	}
	public void resetFields(){
		this.setName(null);
		this.setDateOfInterview(null);
		this.setComments(null);
		this.setEvaluatedBy(null);
		this.setMarksCards(null);
		this.setExperienceCertificate(null);
	}
	public void clear(){
		this.setDateOfInterview(null);
		this.setComments(null);
		this.setEvaluatedBy(null);
		this.setMarksCards(null);
		this.setExperienceCertificate(null);
	}
}
