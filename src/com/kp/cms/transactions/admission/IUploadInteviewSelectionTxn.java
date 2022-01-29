package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.forms.admission.UploadBypassInterviewForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admission.InterviewCardTO;

public interface IUploadInteviewSelectionTxn {

	
	public Map<Integer, Integer> getAdmApplnDetails(int year, UploadBypassInterviewForm bypassInterviewForm )
			throws Exception;

	public boolean addUploadedData(List<InterviewSelected> interviewSelectedList, String user, List<SingleFieldMasterTO> notSelectedList) throws Exception;
	public Map<Integer, Integer> getAdmApplnDetailsForNotSelected(int year, UploadBypassInterviewForm  bypassInterviewForm) throws Exception;

	public boolean addSelectionProcessWorkflowData(List<InterviewCard> interviewCardsToSave,String user,Map<Integer, Integer> intPrgCourseMap) throws Exception;

	public InterviewSchedule getInterviewSchedule(InterviewCardTO interviewCardTO,String user) throws Exception;

	public InterviewCard getInterviewCardBo(int admApplnId, int interviewScheduleId) throws Exception;

	public void sendSmsinBulk(List<MobileMessaging> mobileMessagesList) throws Exception;
}
