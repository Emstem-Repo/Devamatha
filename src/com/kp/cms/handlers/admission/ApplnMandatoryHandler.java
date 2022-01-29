package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplnMandatoryBO;
import com.kp.cms.forms.admission.ApplnMandatoryForm;
import com.kp.cms.helpers.admission.ApplnFormMandatoryHelper;
import com.kp.cms.to.admission.ApplnMandatoryTO;
import com.kp.cms.transactions.admission.IApplnFormMandatoryTransactions;
import com.kp.cms.transactionsimpl.admission.ApplnMandatoryTxnImpl;

public class ApplnMandatoryHandler {
	/**
	 * Singleton object of DocumentExamEntryHandler
	 */
	private static volatile ApplnMandatoryHandler applnMandatoryHandler = null;
	private static final Log log = LogFactory.getLog(ApplnMandatoryHandler.class);
	private ApplnMandatoryHandler() {
		
	}
	/**
	 * return singleton object of DocumentExamEntryHandler.
	 * @return
	 */
	public static ApplnMandatoryHandler getInstance() {
		if (applnMandatoryHandler == null) {
			applnMandatoryHandler = new ApplnMandatoryHandler();
		}
		return applnMandatoryHandler;
	}
	
	IApplnFormMandatoryTransactions transactions = ApplnMandatoryTxnImpl.getInstance();
	
	
	public List<ApplnMandatoryTO> getList() throws Exception{
		List<ApplnMandatoryBO> boList = transactions.getList();
		return ApplnFormMandatoryHelper.getInstance().copyFromBotoTo(boList);
	}
	
	public boolean updateMandatory(ApplnMandatoryForm applnMandatoryForm) throws Exception{
		List<ApplnMandatoryBO> applnMandatoryBO = ApplnFormMandatoryHelper.getInstance().copyFormtoBo(applnMandatoryForm);
		return transactions.updateMandatoryField(applnMandatoryBO);
	}
}
