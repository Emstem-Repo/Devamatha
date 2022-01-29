package com.kp.cms.transactions.phd;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdCompletionDetailsBO;
import com.kp.cms.forms.phd.PhdCompletionDetailsForm;

public interface IPhdCompletionDetailsTransaction {
	
	public List<Object[]> getStudentDetails(PhdCompletionDetailsForm objForm, ActionErrors errors) throws Exception;

	public List<PhdCompletionDetailsBO> getPhdCompletionDetails(PhdCompletionDetailsForm objForm) throws Exception;

	public boolean addCompletionDetails(PhdCompletionDetailsBO synopsisBO,PhdCompletionDetailsForm objForm, ActionErrors errors, HttpSession session) throws Exception;
	
	public boolean deleteCompletionDetails(int id) throws Exception;
	
	public boolean reactivateCompletionDetails(PhdCompletionDetailsForm objForm) throws Exception;

}
