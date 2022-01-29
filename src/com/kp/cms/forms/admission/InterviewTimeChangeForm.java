package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.InterviewTimeChangeTO;

public class InterviewTimeChangeForm  extends BaseActionForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<InterviewTimeChangeTO> selectedCandidates;
	private List<ProgramTypeTO> programTypeList;
	private String appliedYear;
	private String[] interviewType;
	private String interviewDate;
	private String startingTimeHours;
	private String startingTimeMins;
	private String endingTimeHours;
	private String endingTimeMins;
	private String method;
	private List originalCandidates;
	private String appNoForm;
	private String appNoTo;
	private List<InterviewTimeChangeTO> rescheduledHistory;
	/* code added by sudhir */
	private String applicantName;
	private String applnNo;

	public List getOriginalCandidates() {
		return originalCandidates;
	}

	public void setOriginalCandidates(List originalCandidates) {
		this.originalCandidates = originalCandidates;
	}

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

	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
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
		this.setInterviewDate(null);
		this.setStartingTimeHours(null);
		this.setStartingTimeMins(null);
		this.setEndingTimeHours(null);
		this.setEndingTimeMins(null);
		this.appNoForm=null;
		this.appNoTo=null;
		this.interviewType=null;
		
	}

	public List<InterviewTimeChangeTO> getRescheduledHistory() {
		return rescheduledHistory;
	}

	public void setRescheduledHistory(List<InterviewTimeChangeTO> rescheduledHistory) {
		this.rescheduledHistory = rescheduledHistory;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplnNo() {
		return applnNo;
	}

	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}

}
