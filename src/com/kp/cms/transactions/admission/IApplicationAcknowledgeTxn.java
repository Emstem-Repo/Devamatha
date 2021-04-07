package com.kp.cms.transactions.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admission.AcknowledgeNumber;
import com.kp.cms.bo.admission.ApplnAcknowledgement;
import com.kp.cms.bo.admission.ReceivedThrough;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.admission.ApplicationAcknowledgeForm;
import com.kp.cms.to.admission.InterviewCardTO;

public interface IApplicationAcknowledgeTxn {
	public boolean saveAcknowledge(ApplnAcknowledgement acknowledge,ApplicationAcknowledgeForm appnAcknowledgementForm)throws Exception;
	public ApplnAcknowledgement getDetails(ApplicationAcknowledgeForm appnAcknowledgementForm)throws Exception;
	public Organisation getOrganizationDetails()throws Exception;
	public int getAcknowledgeId(String applnNo)throws Exception;
	public AcknowledgeNumber getSlipNumber()throws Exception;
	public void saveAckNumber(AcknowledgeNumber number,String mode)throws Exception;
	public ReceivedThrough getslipRequired(String receivedThrough)throws Exception;
	public ApplnAcknowledgement getApplnAcknowledgement(String appNo)throws Exception;
	
	public void getInterviewVenueFromExamCenter(String examCenter, String InterviewSelectionDate, ApplicationAcknowledgeForm admForm)throws Exception;
	
	public InterviewCardTO getInterviewScheduleDetails(ApplicationAcknowledgeForm admForm)throws Exception;
	
	public InterviewSchedule getInterviewSchedule(InterviewCardTO interviewCardTO,ApplicationAcknowledgeForm admForm) throws Exception;

	public InterviewCard getInterviewCardBo(int admApplnId, int interviewScheduleId) throws Exception;
	
	public boolean addSelectionProcessWorkflowData(List<InterviewCard> interviewCardsToSave,String user,ApplicationAcknowledgeForm admForm, Integer interViewPgmCourse) throws Exception;
	
	public void getDateFromSelectionProcessId(String InterviewSelectionDate,ApplicationAcknowledgeForm admForm)throws Exception;
	
	public AdmAppln getAdmApplnId(ApplicationAcknowledgeForm admForm) throws Exception;
	
	public Integer getInterViewPgmCourse(ApplicationAcknowledgeForm admForm)throws Exception; 
}
