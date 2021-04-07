package com.kp.cms.transactions.phd;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdDocumentSubmissionBO;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.bo.phd.PhdInternalGuideBo;
import com.kp.cms.bo.phd.PhdResearchPublication;
import com.kp.cms.forms.phd.PhdDocumentSubmissionForm;


public interface IPhdDocumentSubmissionTransactions {

	public List<PhdDocumentSubmissionSchedule> getDocumentSubmissionByReg(PhdDocumentSubmissionForm objForm) throws Exception;
	
	public List<Object[]> getStudentDetails(PhdDocumentSubmissionForm objForm,ActionErrors errors) throws Exception;
	
	public List<PhdEmployee> getGuideDetails() throws Exception;
	
	public List<PhdEmployee> getCoGuideDetails() throws Exception;
	
	public PhdDocumentSubmissionBO getSubmissionDetails(PhdDocumentSubmissionForm objForm, ActionErrors errors) throws Exception;
	
	public boolean addDocumentList(List<PhdDocumentSubmissionSchedule> documentList) throws Exception;
	
	public boolean addPhdDocumentSubmission(PhdDocumentSubmissionBO synopsisBO,PhdDocumentSubmissionForm objForm, ActionErrors errors, HttpSession session) throws Exception;

	public List<Object[]> guideDetailsSearch(Date startDate, Date endDate) throws Exception;

	public List<PhdResearchPublication> getResearchPublication() throws Exception;

	public String getPanelNameById(int i, PhdDocumentSubmissionForm objForm, String mode) throws Exception;

	public Map<String, String> guideShipMap() throws Exception;

	public boolean deletesynopsisList(int id, String userId, String mode) throws Exception;

	public boolean deleteResearchPublication(PhdDocumentSubmissionForm objForm) throws Exception;

	public List<PhdInternalGuideBo> setInternalGuideDetails() throws Exception;

	public List<PhdInternalGuideBo> setInternalCoGuideDetails() throws Exception;

	public String getPanelNameEmployeeById(int id,PhdDocumentSubmissionForm objForm) throws Exception;

	public void validateNoofGuides(PhdDocumentSubmissionForm objForm,ActionErrors errors) throws Exception;

	public boolean deleteConference(PhdDocumentSubmissionForm objForm) throws Exception;

}
