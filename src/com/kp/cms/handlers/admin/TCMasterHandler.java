package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.admin.TCPrefix;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.TCMasterForm;
import com.kp.cms.helpers.admin.TCMasterHelper;
import com.kp.cms.to.admin.TCNumberTO;
import com.kp.cms.to.admin.TCPrefixTO;
import com.kp.cms.transactions.admin.ITCMasterTransaction;
import com.kp.cms.transactionsimpl.admin.TCMasterTransactionImpl;

public class TCMasterHandler {
	/**
	 * Singleton object of TCMasterHandler
	 */
	private static volatile TCMasterHandler tCMasterHandler = null;
	
	ITCMasterTransaction transaction=TCMasterTransactionImpl.getInstance();
	
	private static final Log log = LogFactory.getLog(TCMasterHandler.class);
	private TCMasterHandler() {
		
	}
	/**
	 * return singleton object of TCMasterHandler.
	 * @return
	 */
	public static TCMasterHandler getInstance() {
		if (tCMasterHandler == null) {
			tCMasterHandler = new TCMasterHandler();
		}
		return tCMasterHandler;
	}
	public List<TCNumberTO> getAllTCNumber() throws Exception {
		List<TCNumber> list=transaction.getAllTCNumber();
		return TCMasterHelper.getInstance().convertBOtoTOList(list);
	}
	/**
	 * @param tcMasterForm
	 * @param mode ........ this method  used to add or update
	 * @return
	 * @throws Exception
	 */
	public boolean addTCMaster(TCMasterForm tcMasterForm, String mode) throws Exception {
		log.debug("inside addTCMaster");
		//boolean isAdded = false;
		
		TCNumber duplTCNumber = transaction.isTCNumberDuplcated(tcMasterForm);  
		if(tcMasterForm.getId()!=0 && tcMasterForm.getId()!=duplTCNumber.getId()){
		if (duplTCNumber != null && duplTCNumber.getIsActive()) {
			throw new DuplicateException();
		}
		else if (duplTCNumber != null && !duplTCNumber.getIsActive())
		{
			tcMasterForm.setDuplId(duplTCNumber.getId());
			throw new ReActivateException();
		}		
		}else{
			if(mode.equalsIgnoreCase("add")){
				if (duplTCNumber != null && duplTCNumber.getIsActive()) {
					throw new DuplicateException();
				}
				else if (duplTCNumber != null && !duplTCNumber.getIsActive())
				{
					tcMasterForm.setDuplId(duplTCNumber.getId());
					throw new ReActivateException();
				}	
			}
		}
		TCNumber bo=TCMasterHelper.getInstance().convertFormToBo(tcMasterForm,mode);
		log.debug("leaving addTCMaster");
		return transaction.addTCMaster(bo,mode);
	}
	
	/**
	 * @param id
	 * @param activate
	 * @param tcMasterForm  .....this method used to delete or reactivate
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTCMaster(int id, Boolean activate, TCMasterForm tcMasterForm) throws Exception {
		return transaction.deleteTCMaster(id, activate, tcMasterForm);
	}
	
	public List<TCPrefixTO> getTcPrefix() throws Exception{ 
	List<TCPrefix> tcPrefixBo = transaction.getTcPrefix();
    List<TCPrefixTO> tcPrefixTo = TCMasterHelper.getInstance().convertTcPrefix(tcPrefixBo);
    return tcPrefixTo;
    }
}
