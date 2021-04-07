package com.kp.cms.handlers.phd;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdGuideRemunerations;
import com.kp.cms.bo.phd.PhdVoucherNumber;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.phd.PhdStudentReminderationForm;
import com.kp.cms.helpers.phd.PhdStudentReminderationHelper;
import com.kp.cms.helpers.phd.PhdStudyAggrementHelper;
import com.kp.cms.to.phd.PhdStudentReminderationTO;
import com.kp.cms.transactions.phd.IPhdStudentRemindetationTransactions;
import com.kp.cms.transactionsimpl.phd.PhdStudentReminderationImpl;
import com.kp.cms.utilities.CommonUtil;

public class PhdStudentReminderationHandler {
	private static final Log log = LogFactory.getLog(PhdStudentReminderationHandler.class);
	private static volatile PhdStudentReminderationHandler examCceFactorHandler = null;

	public static PhdStudentReminderationHandler getInstance() {
		if (examCceFactorHandler == null) {
			examCceFactorHandler = new PhdStudentReminderationHandler();
		}
		return examCceFactorHandler;
	}
  	IPhdStudentRemindetationTransactions trancations=new PhdStudentReminderationImpl();
  	
	/**
	 * @param objForm
	 * @param errors 
	 * @param search
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public void studentDetailsSearch(PhdStudentReminderationForm objForm, ActionErrors errors) throws DataNotFoundException,Exception {
		log.debug("Handler : Entering guideDetailsSearch");			  
		Date startDate = CommonUtil.ConvertStringToSQLDate(objForm.getStartDate());
		Date endDate = CommonUtil.ConvertStringToSQLDate(objForm.getEndDate());
		List<Object[]> guideList = trancations.studentDetailsSearch(startDate,endDate);
		List<PhdStudentReminderationTO> documentToList = PhdStudentReminderationHelper.getInstance().copyBotoToguide(guideList);
		if(documentToList==null || documentToList.isEmpty()){
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
		}
		objForm.setStudentDetailsList(documentToList);
		log.debug("Handler : Leaving feePaymentSearch");
	}
	
	public void generateStudentDetails(PhdStudentReminderationForm objForm) throws Exception{
		List<Object[]> guideList = trancations.generateStudentDetails(objForm);
		List<PhdStudentReminderationTO> documentToList = PhdStudentReminderationHelper.getInstance().generateStudentDetails(guideList,objForm);
		objForm.setGuideDetailList(documentToList);
		objForm.setCoGuideDetailList(documentToList);
		
	}
	public void setVoucherNo(PhdStudentReminderationForm objForm) throws Exception{
		PhdVoucherNumber guideBOs=trancations.getPhdVoucherNumber();
		PhdStudentReminderationHelper.getInstance().setVoucherNo(guideBOs,objForm);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void setUpdateGeneratedDate(PhdStudentReminderationForm objForm) throws Exception{
		trancations.setUpdateGeneratedDate(objForm);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void setUpdateVoucherNo(PhdStudentReminderationForm objForm) throws Exception{
		trancations.setUpdateVoucherNo(objForm);
	}
	/**
	 * @param objForm
	 * @return 
	 * @throws Exception
	 */
	public boolean saveGuideRemenderation(PhdStudentReminderationForm objForm) throws Exception{
    	List<PhdGuideRemunerations> guideBo=PhdStudentReminderationHelper.getInstance().guidesFormToBO(objForm);
    	boolean isAdded = trancations.saveGuideRemenderation(guideBo);
    	return isAdded;
    }

	public String getGuidetotalAmount(PhdStudentReminderationForm objForm) throws Exception{
		String gtotal=PhdStudentReminderationHelper.getInstance().getGuidetotalAmount(objForm);
		return gtotal;
	}

}
