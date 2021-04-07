package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.MobileSMSCriteriaBO;
import com.kp.cms.forms.attendance.MobileSmsCriteriaForm;
import com.kp.cms.helpers.attendance.MobileSMSCriteriaHelper;
import com.kp.cms.to.attendance.MobileSMSCriteriaTO;
import com.kp.cms.transactions.attandance.IMobileSMSCriteriaTransaction;
import com.kp.cms.transactionsimpl.attendance.MobileSMSCriteriaTransactionImpl;

public class MobileSMSCriteriaHandler {

	private static Log log = LogFactory.getLog(MobileSMSCriteriaHandler.class);
	public static volatile MobileSMSCriteriaHandler mobileSMSCriteriaHandler = null;

	public static MobileSMSCriteriaHandler getInstance() {
		if (mobileSMSCriteriaHandler == null) {
			mobileSMSCriteriaHandler = new MobileSMSCriteriaHandler();
			return mobileSMSCriteriaHandler;
		}
		return mobileSMSCriteriaHandler;
	}

	public String[] getSmsClassList(int currentYear,
			int smsTimehours, int smsMinitue)throws Exception {
        log.debug("call of getSmsClassList method in MobileSMSCriteriaHandler.class");
		IMobileSMSCriteriaTransaction transaction = new MobileSMSCriteriaTransactionImpl().getInstance();
		String[] smsClassMap=transaction.getSmsClassMap(currentYear,smsTimehours,smsMinitue);
		log.debug("end of getSmsClassList method in MobileSMSCriteriaHandler.class");
		return smsClassMap;
	}

	public boolean isDuplicated(MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception {
		log.debug("call of isDuplicated mehod in MobileSMSCriteriaHandler.class");
		boolean isDuplicated=false;
		boolean isFullDeleted=false;
		IMobileSMSCriteriaTransaction transaction = new MobileSMSCriteriaTransactionImpl().getInstance();
		if(mobileSmsCriteriaForm.getParticular().equalsIgnoreCase("false"))
		{
			isFullDeleted=transaction.deleteAllOldSMSCriteria(mobileSmsCriteriaForm);
			if(isFullDeleted)
			{
				isDuplicated=false;
			}
		}
		else
		{
		isDuplicated=transaction.isDuplicateClass(mobileSmsCriteriaForm);
		}
		log.debug("end of isDuplicated mehod in MobileSMSCriteriaHandler.class");
		return isDuplicated;
	}

	public boolean addSMSCriteria(MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception{
		log.debug("call of addSMSCriteria method in MobileSMSCriteriaHandler.class");
		boolean isAdded=false;
		IMobileSMSCriteriaTransaction transaction = new MobileSMSCriteriaTransactionImpl().getInstance();
		if(mobileSmsCriteriaForm.getParticular().equalsIgnoreCase("true"))
		{
			MobileSMSCriteriaBO mobileSMSCriteriaBO=MobileSMSCriteriaHelper.getInstance().copyFormToBO(mobileSmsCriteriaForm);
			isAdded=transaction.addSMSCriteria(mobileSMSCriteriaBO);
		}
		else if(mobileSmsCriteriaForm.getParticular().equalsIgnoreCase("false"))
		{
			List<MobileSMSCriteriaBO> boList=MobileSMSCriteriaHelper.getInstance().copyAllClasstoBO(mobileSmsCriteriaForm);
			isAdded=transaction.addSMSCriteriaForAllClass(boList);
		}
		log.debug("end of addSMSCriteria method in MobileSMSCriteriaHandler.class");
		return isAdded;
	}

	public List<MobileSMSCriteriaTO> getAllSMSCriterias() throws Exception {
		log.debug("call of getAllSMSCriterias method in MobileSMSCriteriaHandler.class");
		List<MobileSMSCriteriaTO> mobileSMSCriteriaTOs= new ArrayList<MobileSMSCriteriaTO>();
		IMobileSMSCriteriaTransaction transaction = new MobileSMSCriteriaTransactionImpl().getInstance();
		List<MobileSMSCriteriaBO> boCriteriaBOlist=transaction.getAllSMSCriteriaList();
		mobileSMSCriteriaTOs=MobileSMSCriteriaHelper.getInstance().convertBOtoTO(boCriteriaBOlist);
		log.debug("end  of getAllSMSCriterias method in MobileSMSCriteriaHandler.class");
		return mobileSMSCriteriaTOs;
	}

	public boolean deleteSMSCriteria(MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception {
		log.debug("call of deleteSMSCriteria in MobileSMSCriteriaHandler.class ");
		boolean isDeletd=false;
		IMobileSMSCriteriaTransaction transaction = new MobileSMSCriteriaTransactionImpl().getInstance();
		isDeletd=transaction.deleteSMSCriteria(mobileSmsCriteriaForm);
		log.debug("end of deleteSMSCriteria in MobileSMSCriteriaHandler.class ");
		return isDeletd;
	}

	public MobileSmsCriteriaForm populatedataTOform(
			MobileSmsCriteriaForm mobileSmsCriteriaForm, String smsCriteriaId)throws Exception {
		log.debug("call of populatedataTOform method in MobileSMSCriteriaHandler.class");
		IMobileSMSCriteriaTransaction transaction = new MobileSMSCriteriaTransactionImpl().getInstance();
		MobileSMSCriteriaBO criteriaBO=transaction.getSMScriteriaDetails(smsCriteriaId);
		mobileSmsCriteriaForm=MobileSMSCriteriaHelper.getInstance().populateDatetoForm(criteriaBO,mobileSmsCriteriaForm);
		log.debug("end of populatedataTOform method in MobileSMSCriteriaHandler.class");
		return mobileSmsCriteriaForm;
	}

	public boolean updateSMSCriteria(MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception {
		log.debug("call of updateSMSCriteria method in MobileSMSCriteriaHandler.class");
		boolean isUpdated=false;
		IMobileSMSCriteriaTransaction transaction = new MobileSMSCriteriaTransactionImpl().getInstance();
		MobileSMSCriteriaBO smsCriteBo=MobileSMSCriteriaHelper.getInstance().copyFormToBOForUpdate(mobileSmsCriteriaForm);
		isUpdated=transaction.updateSMSCriteria(smsCriteBo);
		log.debug("end of updateSMSCriteria method in MobileSMSCriteriaHandler.class");
		return isUpdated;
	}

	
}
