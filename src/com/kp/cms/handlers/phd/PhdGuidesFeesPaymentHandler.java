package com.kp.cms.handlers.phd;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdGuideRemunerations;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.phd.PhdStudentReminderationForm;
import com.kp.cms.helpers.phd.PhdGuidesFeesPaymentHelper;
import com.kp.cms.to.phd.PhdStudentReminderationTO;
import com.kp.cms.transactions.phd.IPhdGuidesFeesPaymentTransactions;
import com.kp.cms.transactionsimpl.phd.PhdGuidesFeesPaymentImpl;
import com.kp.cms.utilities.CommonUtil;

public class PhdGuidesFeesPaymentHandler {
	private static final Log log = LogFactory.getLog(PhdGuidesFeesPaymentHandler.class);
	private static volatile PhdGuidesFeesPaymentHandler phdGuidesFeesPaymentHandler = null;

	public static PhdGuidesFeesPaymentHandler getInstance() {
		if (phdGuidesFeesPaymentHandler == null) {
			phdGuidesFeesPaymentHandler = new PhdGuidesFeesPaymentHandler();
		}
		return phdGuidesFeesPaymentHandler;
	}
	IPhdGuidesFeesPaymentTransactions trancations=new PhdGuidesFeesPaymentImpl();
	
	/**
	 * @param objForm
	 * @param errors 
	 * @param search
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public void searchGuidesFeesPayment(PhdStudentReminderationForm objForm, ActionErrors errors) throws DataNotFoundException,Exception {
		log.debug("Handler : Entering searchGuidesFeesPayment");			  
		Date startDate = CommonUtil.ConvertStringToSQLDate(objForm.getStartDate());
		Date endDate = CommonUtil.ConvertStringToSQLDate(objForm.getEndDate());
		List<PhdGuideRemunerations> guideList = trancations.searchGuidesFeesPayment(startDate,endDate);
		List<PhdStudentReminderationTO> documentToList = PhdGuidesFeesPaymentHelper.getInstance().copyBotoToguide(guideList);
		if(documentToList==null || documentToList.isEmpty()){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
		}
		objForm.setGuideFeesPatmentList(documentToList);
		log.debug("Handler : Leaving feePaymentSearch");
	}
	/**
	 * @param objForm
	 * @param errors
	 */
	public boolean saveGuideFeesPayment(PhdStudentReminderationForm objForm,ActionErrors errors) throws Exception{
    	boolean isAdded = trancations.saveGuideFeesPayment(objForm);
		return isAdded;
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void editGuideDetails(PhdStudentReminderationForm objForm) throws Exception{
		List<Object[]> guideList = trancations.getGuideDetailsById(objForm);
		List<PhdStudentReminderationTO> documentToList = PhdGuidesFeesPaymentHelper.getInstance().generateStudentDetails(guideList,objForm);
		objForm.setGuideDetailList(documentToList);
		objForm.setCoGuideDetailList(documentToList);
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateGuideRemenderation(PhdStudentReminderationForm objForm) throws Exception{
    	List<PhdGuideRemunerations> guideBo=PhdGuidesFeesPaymentHelper.getInstance().updateGuideRemenderation(objForm);
    	boolean isAdded = trancations.updateGuideRemenderation(guideBo);
    	return isAdded;
    }
	
 
}
