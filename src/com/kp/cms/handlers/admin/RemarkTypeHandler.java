package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.RemarkType;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.RemarkTypeForm;
import com.kp.cms.helpers.admin.RemarkTypeHelper;
import com.kp.cms.to.admin.RemarkTypeTO;
import com.kp.cms.transactions.admin.IRemarkTypeTransaction;
import com.kp.cms.transactionsimpl.admin.RemarkTypeTransactionImpl;

public class RemarkTypeHandler {
	private static final Log log = LogFactory.getLog(RemarkTypeHandler.class);
	public static volatile RemarkTypeHandler remarkTypeHandler = null;

	public static RemarkTypeHandler getInstance() {
		if (remarkTypeHandler == null) {
			remarkTypeHandler = new RemarkTypeHandler();
			return remarkTypeHandler;
		}
		return remarkTypeHandler;
	}
	
	/**
	 * method used to get list of remark type from table.
	 * @return
	 * @throws Exception
	 */
	public List<RemarkTypeTO> getRemarks() throws Exception {
		IRemarkTypeTransaction iRemarkTypeTransaction = RemarkTypeTransactionImpl.getInstance();
		List<RemarkType> remarkTypeList = iRemarkTypeTransaction.getRemarks();
		List<RemarkTypeTO> remarkTypeTOList = RemarkTypeHelper .getInstance().copyRemarkTypeBosToTos(remarkTypeList);
		log.debug("leaving getRemarks");
		return remarkTypeTOList;
	}
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RemarkType getRemarksByID(int id) throws Exception {
		IRemarkTypeTransaction iRemarkTypeTransaction = RemarkTypeTransactionImpl.getInstance();
		RemarkType remarkType = iRemarkTypeTransaction.getRemarkTypeById(id);
		log.debug("leaving getRemarksByID");
		return remarkType;
	}
	/**
	 * method to add remark type
	 */
	public boolean addRemarkType(RemarkTypeForm remarkTypeForm, String mode)	throws Exception {
		IRemarkTypeTransaction iRemarkTypeTransaction = RemarkTypeTransactionImpl.getInstance();
		boolean isAdded = false;
		RemarkType duplRemarkType = iRemarkTypeTransaction.isRemarkTypeDuplcated(remarkTypeForm.getRemarkType(), remarkTypeForm.getId());
		if (duplRemarkType != null && duplRemarkType.getIsActive()) {
			throw new DuplicateException();
		}
		else if (duplRemarkType != null && !duplRemarkType.getIsActive())
		{
			remarkTypeForm.setDuplId(duplRemarkType.getId());
			throw new ReActivateException();
		}
		
		RemarkType remarkType = RemarkTypeHelper.getInstance().copyDataFromFormToBO(remarkTypeForm);
		isAdded = iRemarkTypeTransaction.addRemarkType(remarkType, mode); 
		log.debug("leaving addRemarkType");
		return isAdded;
	}
	
	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteRemarkType(int id, Boolean activate, RemarkTypeForm remarkTypeForm)	throws Exception {
		IRemarkTypeTransaction iRemarkTypeTransaction = RemarkTypeTransactionImpl.getInstance();
		return iRemarkTypeTransaction.deleteRemarks(id, activate, remarkTypeForm);
	}

}
