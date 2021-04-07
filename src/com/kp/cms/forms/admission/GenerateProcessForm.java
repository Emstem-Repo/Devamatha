package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.to.admission.InterviewTimeChangeTO;

public class GenerateProcessForm  extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<InterviewTimeChangeTO> selectedCandidates;
	private List<ProgramTypeTO> programTypeList;
	private String appliedYear;
	private String[] interviewType;
	private String interviewStartDate;
	private String startingTimeHours;
	private String startingTimeMins;
	private String endingTimeHours;
	private String endingTimeMins;
	private String method;
	private String appNoForm;
	private String appNoTo;
	private String interviewEndDate;
	private String applicationNo;
	private String dateOfBirth;
	private String templateDescription;
	private boolean displaySemister;
	private AdmissionStatusTO admissionStatusTO;
	private String admStatus;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStartingTimeHours() {
		return startingTimeHours;
	}

	public void setStartingTimeHours(String startingTimeHours) {
		this.startingTimeHours = startingTimeHours;
	}

	public String getStartingTimeMins() {
		return startingTimeMins;
	}

	public void setStartingTimeMins(String startingTimeMins) {
		this.startingTimeMins = startingTimeMins;
	}

	public String getEndingTimeHours() {
		return endingTimeHours;
	}

	public void setEndingTimeHours(String endingTimeHours) {
		this.endingTimeHours = endingTimeHours;
	}

	public String getEndingTimeMins() {
		return endingTimeMins;
	}

	public void setEndingTimeMins(String endingTimeMins) {
		this.endingTimeMins = endingTimeMins;
	}

	public String getInterviewStartDate() {
		return interviewStartDate;
	}

	public void setInterviewStartDate(String interviewStartDate) {
		this.interviewStartDate = interviewStartDate;
	}

	public String[] getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String[] interviewType) {
		this.interviewType = interviewType;
	}

	public String getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<InterviewTimeChangeTO> getSelectedCandidates() {
		return selectedCandidates;
	}

	public void setSelectedCandidates(List<InterviewTimeChangeTO> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}
	
	public String getAppNoForm() {
		return appNoForm;
	}

	public void setAppNoForm(String appNoForm) {
		this.appNoForm = appNoForm;
	}

	public String getAppNoTo() {
		return appNoTo;
	}

	public void setAppNoTo(String appNoTo) {
		this.appNoTo = appNoTo;
	}

	public String getInterviewEndDate() {
		return interviewEndDate;
	}

	public void setInterviewEndDate(String interviewEndDate) {
		this.interviewEndDate = interviewEndDate;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public boolean isDisplaySemister() {
		return displaySemister;
	}

	public void setDisplaySemister(boolean displaySemister) {
		this.displaySemister = displaySemister;
	}

	public AdmissionStatusTO getAdmissionStatusTO() {
		return admissionStatusTO;
	}

	public void setAdmissionStatusTO(AdmissionStatusTO admissionStatusTO) {
		this.admissionStatusTO = admissionStatusTO;
	}

	public String getAdmStatus() {
		return admStatus;
	}

	public void setAdmStatus(String admStatus) {
		this.admStatus = admStatus;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		this.setAppliedYear(null);
		super.setInterviewTypeId(null);
		this.setInterviewStartDate(null);
		this.setStartingTimeHours(null);
		this.setStartingTimeMins(null);
		this.setEndingTimeHours(null);
		this.setEndingTimeMins(null);
		this.appNoForm=null;
		this.appNoTo=null;
		this.interviewType=null;
		this.interviewEndDate=null;
	}
	
	public void clear()
	{
		this.admStatus=null;
		this.applicationNo=null;
		this.dateOfBirth=null;
		this.templateDescription = "";
		this.displaySemister=false;
	}
	
	public void clearadmissionStatusTO(){
		this.admissionStatusTO=null;
	}
}
