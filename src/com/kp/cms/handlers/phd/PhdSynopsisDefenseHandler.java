package com.kp.cms.handlers.phd;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdSynopsisDefenseBO;
import com.kp.cms.forms.phd.PhdSynopsisDefenseForm;
import com.kp.cms.helpers.phd.PhdSynopsisDefenseHelper;
import com.kp.cms.to.phd.PhdSynopsisDefenseTO;
import com.kp.cms.transactions.phd.IPhdSynopsisDefenseTransactions;
import com.kp.cms.transactionsimpl.phd.PhdSynopsisDefenseImpl;

public class PhdSynopsisDefenseHandler {
	private static final Log log = LogFactory.getLog(PhdSynopsisDefenseHandler.class);
	private static volatile PhdSynopsisDefenseHandler examCceFactorHandler = null;

	public static PhdSynopsisDefenseHandler getInstance() {
		if (examCceFactorHandler == null) {
			examCceFactorHandler = new PhdSynopsisDefenseHandler();
		}
		return examCceFactorHandler;
	}
  	IPhdSynopsisDefenseTransactions trancations=new PhdSynopsisDefenseImpl();
   /**
 * @param objForm
 * @param errors
 * @param session 
 * @return
 */
    public boolean addSynopsisDefense(PhdSynopsisDefenseForm objForm,String mode) throws Exception{
    	PhdSynopsisDefenseBO synopsisBO = PhdSynopsisDefenseHelper.getInstance().convertFormToBos(objForm);
        boolean isAdded = trancations.addSynopsisDefense(synopsisBO,mode);
        return isAdded;
    }
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void editSynopsisDefense(PhdSynopsisDefenseForm objForm) throws Exception{
		PhdSynopsisDefenseBO synopsisDefenseBO = trancations.getSynopsisDefenseById(objForm.getId());
		PhdSynopsisDefenseHelper.getInstance().setDataBoToForm(objForm, synopsisDefenseBO);
	}
	 /**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteSynopsisDefense(PhdSynopsisDefenseForm objForm)
      throws Exception{
      boolean isDeleted = trancations.deleteSynopsisDefense(objForm.getId());
      return isDeleted;
  }

	public boolean updateSynopsisDefense(PhdSynopsisDefenseForm objForm,String mode) throws Exception{

		PhdSynopsisDefenseBO examCceFactor = PhdSynopsisDefenseHelper.getInstance().convertToBos(objForm);
        boolean isUpdated =trancations.addSynopsisDefense(examCceFactor,mode);
        return isUpdated;
    }

	public  void getStudentDetails(PhdSynopsisDefenseForm objForm, ActionErrors errors) throws Exception {
		List<Object[]> studentdetails=trancations.getStudentDetails(objForm,errors);
	    PhdSynopsisDefenseHelper.getInstance().setdatatolist(studentdetails,objForm);
	}

	public List<PhdSynopsisDefenseTO> getPhdSynopsisdefence(PhdSynopsisDefenseForm objForm) throws Exception{
		List<PhdSynopsisDefenseBO> phdSynopsis=trancations.getPhdSynopsisdefence(objForm);
		List<PhdSynopsisDefenseTO> phdSynopsisto=PhdSynopsisDefenseHelper.getInstance().setdatatogrid(phdSynopsis);
		return phdSynopsisto;
	}
	public boolean duplicateCheck(PhdSynopsisDefenseForm objForm,ActionErrors errors, HttpSession session) {
		boolean duplicate=trancations.duplicateCheck(objForm,errors,session);
		return duplicate;
	}
	public boolean reactivateSynopsisDefense(PhdSynopsisDefenseForm objForm,String userId) throws Exception{
	     return trancations.reactivateSynopsisDefense(objForm);
	}
}
