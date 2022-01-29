package com.kp.cms.handlers.fee;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeCriteria;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.AdmittedThroughForm;
import com.kp.cms.forms.fee.FeeCriteriaForm;
import com.kp.cms.helpers.fee.FeeCriteriaHelper;
import com.kp.cms.to.fee.FeeCriteriaTO;
import com.kp.cms.transactions.admin.IAdmittedThroughTransaction;
import com.kp.cms.transactions.fee.IFeeCriteriaTransaction;
import com.kp.cms.transactionsimpl.admin.AdmittedThroughTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeeCriteriaTransactionImpl;

public class FeeCriteriaHandler {
	private static final Log log = LogFactory.getLog(FeeCriteriaHandler.class);
	public static FeeCriteriaHandler feeCriteriaHandler = null;

	public static FeeCriteriaHandler getInstance() {
		if (feeCriteriaHandler == null) {
			feeCriteriaHandler = new FeeCriteriaHandler();
			return feeCriteriaHandler;
		}
		return feeCriteriaHandler;
	}	
	
	/**
	 * 
	 * @param feeCriteriaForm
	 * @return
	 * @throws Exception
	 */
	public boolean addFeeCriteria(FeeCriteriaForm feeCriteriaForm) throws Exception {
		log.debug("inside addFeeCriteria");
		IFeeCriteriaTransaction iFeeCriteriaTransaction = FeeCriteriaTransactionImpl.getInstance();
		if(iFeeCriteriaTransaction.checkDuplicate(feeCriteriaForm)){
			throw new DuplicateException();
		}
		boolean isAdded = false;
		FeeCriteria feeCriteria = FeeCriteriaHelper.getInstance().copyFromFormTOBO(feeCriteriaForm);
		isAdded = iFeeCriteriaTransaction.addFeeCriteriaToTable(feeCriteria); 
		log.debug("leaving addFeeCriteria");
		return isAdded;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getFeeAdditionalFeeGroup() throws Exception {
		log.debug("Handler : Entering getFeeAdditionalFeeGroup");
		IFeeCriteriaTransaction iFeeCriteriaTransaction = FeeCriteriaTransactionImpl.getInstance();
		   Map<Integer,String> feeOptionalGroupMap = iFeeCriteriaTransaction.getAllFeesGroup();
		   log.debug("Handler : Leaving getFeeAdditionalFeeGroup");
		   return feeOptionalGroupMap;
	}	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<FeeCriteriaTO> getFeeCriteria() throws Exception {
		IFeeCriteriaTransaction iFeeCriteriaTransaction = FeeCriteriaTransactionImpl.getInstance();
		List<FeeCriteria> feeCriteriaList = iFeeCriteriaTransaction.getFeeCriterias();
		List<FeeCriteriaTO> feeCriteriaTOList = FeeCriteriaHelper.getInstance().copyFeeCriteriaBOtoTos(feeCriteriaList);; 
		return feeCriteriaTOList;
	}
	
	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteFeeCriteria(int id) throws Exception {
		IFeeCriteriaTransaction iFeeCriteriaTransaction = FeeCriteriaTransactionImpl.getInstance();
		boolean result = iFeeCriteriaTransaction.deleteFeeCriteria(id);
		return result;
	}
	
}
