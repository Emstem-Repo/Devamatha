package com.kp.cms.handlers.phd;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdCompletionDetailsBO;
import com.kp.cms.forms.phd.PhdCompletionDetailsForm;
import com.kp.cms.helpers.phd.PhdCompletionDetailsHelper;
import com.kp.cms.to.phd.PhdCompletionDetailsTO;
import com.kp.cms.transactions.phd.IPhdCompletionDetailsTransaction;
import com.kp.cms.transactionsimpl.phd.PhdCompletionDetailsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class PhdCompletionDetailsHandler {
	
	private static final Log log = LogFactory.getLog(PhdCompletionDetailsHandler.class);
	private static volatile PhdCompletionDetailsHandler phdCompletionDetailsHandler = null;
	
	public static PhdCompletionDetailsHandler getInstance() {
		if (phdCompletionDetailsHandler == null) {
			phdCompletionDetailsHandler = new PhdCompletionDetailsHandler();
		}
		return phdCompletionDetailsHandler;
	}
	IPhdCompletionDetailsTransaction trancations=new PhdCompletionDetailsTransactionImpl();
	
	public List<PhdCompletionDetailsTO> getStudentDetails(PhdCompletionDetailsForm objForm, ActionErrors errors) throws Exception {
		List<Object[]> studentdetails=trancations.getStudentDetails(objForm,errors);
		List<PhdCompletionDetailsTO> phdSynopsisto=PhdCompletionDetailsHelper.getInstance().setdatatolist(studentdetails,objForm);
		return phdSynopsisto;
	}
	
	/**
     * @param objForm
     * @param session 
     * @param errors 
     * @return
     * @throws Exception
     */
	public boolean addCompletionDetails(PhdCompletionDetailsForm objForm, ActionErrors errors, HttpSession session) throws Exception{
		
    	PhdCompletionDetailsBO synopsisBO = PhdCompletionDetailsHelper.getInstance().convertFormToBos(objForm);
    	boolean isAdded=true;
    	if(synopsisBO==null){
    		return isAdded=false;
    	}
        isAdded = trancations.addCompletionDetails(synopsisBO,objForm,errors,session);
        return isAdded;
    }
	
	public boolean deleteCompletionDetails(PhdCompletionDetailsForm objForm) throws Exception{
		
    boolean isDeleted = trancations.deleteCompletionDetails(objForm.getId());
    return isDeleted;
	}
	
	public boolean reactivateCompletionDetails(PhdCompletionDetailsForm objForm,String userId) throws Exception{
	     return trancations.reactivateCompletionDetails(objForm);
	}
	
	

}
