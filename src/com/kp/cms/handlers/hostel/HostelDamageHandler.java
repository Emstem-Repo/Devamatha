package com.kp.cms.handlers.hostel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.forms.hostel.HostelDamageForm;
import com.kp.cms.helpers.hostel.HostelDamageHelper;
import com.kp.cms.to.hostel.HostelDamageTO;
import com.kp.cms.transactions.hostel.IHostelDamageTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelDamageTransactionImpl;

public class HostelDamageHandler {
	/**
	 * Singleton object of HostelDamageHandler
	 */
	private static volatile HostelDamageHandler hostelDamageHandler = null;
	private static final Log log = LogFactory.getLog(HostelDamageHandler.class);

	private HostelDamageHandler() {

	}

	/**
	 * return singleton object of HostelDamageHandler.
	 * 
	 * @return
	 */
	public static HostelDamageHandler getInstance() {
		if (hostelDamageHandler == null) {
			hostelDamageHandler = new HostelDamageHandler();
		}
		return hostelDamageHandler;
	}

	public HostelDamageTO getHostelDamageTO(HostelDamageForm hostelDamageForm)
			throws Exception {
		HostelDamageHelper helper = HostelDamageHelper.getInstance();
		String query = helper.getSearchQuery(hostelDamageForm);
		IHostelDamageTransaction transaction = new HostelDamageTransactionImpl();
		HlApplicationForm hlApplicationForm = transaction
				.getHlapplicationByQuery(query);
		HostelDamageTO hostelDamageTO = null;
		if (hlApplicationForm != null) {
			hostelDamageTO = helper.convertBOtoTO(hlApplicationForm,hostelDamageForm);
		}
		return hostelDamageTO;
	}

	/**
	 * adding the hostel Damage Entry
	 */
	public boolean addHostelDamageEntry(HostelDamageForm hostelDamageForm)
			throws Exception {

		log.info("call of addHostelDamageEntry method in HostelDamageHandler class.");
		IHostelDamageTransaction hostelDamageTransaction = HostelDamageTransactionImpl.getInstance();
		boolean hostelDamageAdded=false;
		HlDamage hlDamage=HostelDamageHelper.getInstance().getHostelDamage(hostelDamageForm);
		if(hlDamage!=null){
			hostelDamageAdded = hostelDamageTransaction.addHostelDamageEntry(hlDamage);
		}
		log.info("end of addHostelDamageEntry method in HostelDamageHandler class.");
		return hostelDamageAdded;
	}
	
	public boolean deleteHostelDamage(int hlDamageId, HostelDamageForm hostelDamageForm) throws Exception {
		log.info("call of deleteHostelDamage method in HostelDamageHandler class.");
		IHostelDamageTransaction hostelDamageTransaction = HostelDamageTransactionImpl .getInstance();
		boolean isDeleted = hostelDamageTransaction.deleteHostelDamage(hlDamageId,hostelDamageForm);
		log.info("end of deleteHostelDamage method in HostelDamageHandler class.");
		return isDeleted;
	}

}
