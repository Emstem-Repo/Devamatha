package com.kp.cms.transactions.phd;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.bo.phd.PhdStudyAggrementBO;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.forms.phd.PhdStudyAggrementForm;


public interface IPhdStudyAggrementTransactions {

	public boolean deleteStudyAggrement(int id) throws Exception;

	public List<Object[]> getStudentDetails(PhdStudyAggrementForm objForm, ActionErrors errors) throws Exception;

	public List<PhdStudyAggrementBO> getPhdStudyAggrement(PhdStudyAggrementForm objForm) throws Exception;

	public boolean addStudyAggrement(PhdStudyAggrementBO synopsisBO,PhdStudyAggrementForm objForm, ActionErrors errors, HttpSession session) throws Exception;

	public boolean reactivateStudyAggrement(PhdStudyAggrementForm objForm) throws Exception;

	public List<PhdGuideDetailsBO> getGuideDetails() throws Exception;

	public List<Object[]> guideDetailsSearch(Date startDate, Date endDate) throws Exception;

	public void setUpdateGeneratedDate(PhdDocumentSubmissionScheduleForm objForm) throws Exception;


}
