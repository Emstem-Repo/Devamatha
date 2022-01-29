package com.kp.cms.transactions.phd;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.forms.phd.PhdGuideDetailsForm;


public interface IPhdGuideDetailsTransactions {

	public boolean deleteGuideDetails(int id) throws Exception;

	public List<PhdGuideDetailsBO> getPhdGuideDetails(PhdGuideDetailsForm objForm) throws Exception;

	public PhdGuideDetailsBO getGuideDetailsById(int id) throws Exception;

	public boolean duplicateCheck(PhdGuideDetailsForm objForm,ActionErrors errors, HttpSession session);

	public boolean addSynopsisDefense(PhdGuideDetailsBO guideBO,String mode) throws Exception;

	public boolean reactivateSynopsisDefense(PhdGuideDetailsForm objForm) throws Exception;

}
