package com.kp.cms.handlers.phd;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.bo.phd.PhdSettingBO;
import com.kp.cms.bo.phd.PhdStudyAggrementBO;
import com.kp.cms.bo.phd.PhdSynopsisDefenseBO;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.forms.phd.PhdSettingForm;
import com.kp.cms.forms.phd.PhdStudyAggrementForm;
import com.kp.cms.helpers.phd.PhdSettingHelper;
import com.kp.cms.helpers.phd.PhdStudyAggrementHelper;
import com.kp.cms.helpers.phd.PhdSynopsisDefenseHelper;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.to.phd.PhdGuideDetailsTO;
import com.kp.cms.to.phd.PhdSettingTO;
import com.kp.cms.to.phd.PhdStudyAggrementTO;
import com.kp.cms.transactions.phd.IPhdSettingTransactions;
import com.kp.cms.transactions.phd.IPhdStudyAggrementTransactions;
import com.kp.cms.transactionsimpl.phd.PhdSettingImpl;
import com.kp.cms.transactionsimpl.phd.PhdStudyAggrementImpl;
import com.kp.cms.utilities.CommonUtil;

public class PhdSettingHandler {
	private static final Log log = LogFactory.getLog(PhdSettingHandler.class);
	private static volatile PhdSettingHandler examCceFactorHandler = null;

	public static PhdSettingHandler getInstance() {
		if (examCceFactorHandler == null) {
			examCceFactorHandler = new PhdSettingHandler();
		}
		return examCceFactorHandler;
	}
  	IPhdSettingTransactions trancations=new PhdSettingImpl();
     /**
	 * @param objForm
	 * @throws Exception
	 */
	public List<PhdSettingTO> getSettingDetails(PhdSettingForm objForm) throws Exception {
		List<PhdSettingBO> settingdetails=trancations.getSettingDetails(objForm);
		List<PhdSettingTO> settingto=PhdSettingHelper.getInstance().setdataToForm(settingdetails);
		return settingto;
	}
    /**
     * @param objForm
     * @param session 
     * @param errors 
     * @return
     * @throws Exception
     */
	public boolean duplicateCheck(PhdSettingForm objForm, ActionErrors errors,HttpSession session) throws Exception{
		boolean duplicate=trancations.duplicateCheck(objForm,errors,session);
		return duplicate;
	}
    /**
     * @param objForm
     * @param errors
     * @param session
     * @return
     * @throws Exception
     */
	public boolean addSetting(PhdSettingForm objForm, String mode) throws Exception{
    	PhdSettingBO settingBO = PhdSettingHelper.getInstance().convertFormToBos(objForm);
        boolean isAdded = trancations.addSetting(settingBO,mode);
        return isAdded;
    }
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void editSetting(PhdSettingForm objForm) throws Exception{
		PhdSettingBO settingBO = trancations.getPhdSettingById(objForm.getId());
		PhdSettingHelper.getInstance().setDataBoToForm(objForm, settingBO);
	}
	
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteSetting(PhdSettingForm objForm) throws Exception{
	      boolean isDeleted = trancations.deleteSetting(objForm.getId());
	      return isDeleted;
	  }
	/**
	 * @param objForm
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reactivateSetting(PhdSettingForm objForm, String userId) throws Exception{
	     return trancations.reactivateSetting(objForm);
	}
}
