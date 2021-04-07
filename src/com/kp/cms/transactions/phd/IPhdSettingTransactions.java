package com.kp.cms.transactions.phd;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.bo.phd.PhdSettingBO;
import com.kp.cms.bo.phd.PhdStudyAggrementBO;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.forms.phd.PhdSettingForm;
import com.kp.cms.forms.phd.PhdStudyAggrementForm;


public interface IPhdSettingTransactions {
	
	public List<PhdSettingBO> getSettingDetails(PhdSettingForm objForm) throws Exception;
	
	public boolean duplicateCheck(PhdSettingForm objForm, ActionErrors errors,HttpSession session) throws Exception;
	
	public boolean addSetting(PhdSettingBO settingBO, String mode) throws Exception;
	
	public PhdSettingBO getPhdSettingById(int id) throws Exception;

	public boolean deleteSetting(int id) throws Exception;

	public boolean reactivateSetting(PhdSettingForm objForm) throws Exception;

}
