package com.kp.cms.handlers.phd;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.forms.phd.PhdGuideDetailsForm;
import com.kp.cms.helpers.phd.PhdGuideDetailsHelper;
import com.kp.cms.to.phd.PhdGuideDetailsTO;
import com.kp.cms.transactions.phd.IPhdGuideDetailsTransactions;
import com.kp.cms.transactionsimpl.phd.PhdGuideDetailsImpl;

public class PhdGuideDetailsHandler {
	private static final Log log = LogFactory.getLog(PhdGuideDetailsHandler.class);
	private static volatile PhdGuideDetailsHandler examCceFactorHandler = null;

	public static PhdGuideDetailsHandler getInstance() {
		if (examCceFactorHandler == null) {
			examCceFactorHandler = new PhdGuideDetailsHandler();
		}
		return examCceFactorHandler;
	}
	IPhdGuideDetailsTransactions trancations=new PhdGuideDetailsImpl();
   /**
 * @param objForm
 * @param errors
 * @param session 
 * @return
 */
    public boolean addGuideDetails(PhdGuideDetailsForm objForm,String mode) throws Exception{
    	PhdGuideDetailsBO guideBO = PhdGuideDetailsHelper.getInstance().convertFormToBos(objForm);
        boolean isAdded = trancations.addSynopsisDefense(guideBO,mode);
        return isAdded;
    }

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<PhdGuideDetailsTO> getPhdGuideDetails(PhdGuideDetailsForm objForm) throws Exception{
		List<PhdGuideDetailsBO> phdGuide=trancations.getPhdGuideDetails(objForm);
		List<PhdGuideDetailsTO> phdGuideto=PhdGuideDetailsHelper.getInstance().setdatatogrid(phdGuide);
		return phdGuideto;
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void editGuideDetails(PhdGuideDetailsForm objForm) throws Exception{
		PhdGuideDetailsBO guideBO = trancations.getGuideDetailsById(objForm.getId());
		PhdGuideDetailsHelper.getInstance().setDataBoToForm(objForm, guideBO);
	}

	/**
	 * @param objForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean updateGuideDetails(PhdGuideDetailsForm objForm,String mode) throws Exception{
		PhdGuideDetailsBO guidebo = PhdGuideDetailsHelper.getInstance().convertToBos(objForm);
        boolean isUpdated =trancations.addSynopsisDefense(guidebo,mode);
        return isUpdated;
    }
	 /**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteGuideDetails(PhdGuideDetailsForm objForm)
      throws Exception{
      boolean isDeleted = trancations.deleteGuideDetails(objForm.getId());
      return isDeleted;
  }

	/**
	 * @param objForm
	 * @param errors
	 * @param session
	 * @return
	 */
	public boolean duplicateCheck(PhdGuideDetailsForm objForm,ActionErrors errors, HttpSession session) {
		boolean duplicate=trancations.duplicateCheck(objForm,errors,session);
		return duplicate;
	}
	/**
	 * @param objForm
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reactivateGuideDetails(PhdGuideDetailsForm objForm,String userId) throws Exception{
	     return trancations.reactivateSynopsisDefense(objForm);
	}
}
