package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.HlFeeType;
import com.kp.cms.forms.hostel.HostelFeesTypeForm;
import com.kp.cms.helpers.hostel.HostelFeesTypeHelper;
import com.kp.cms.to.hostel.HostelFeesTypeTo;
import com.kp.cms.transactions.hostel.IHostelFeesTypeTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelFeesTypeTransactionImpl;
import common.Logger;


/**
 * @author kolli.ramamohan
 * @version 1.0
 */
public class HostelFeesTypeHandler {

	private static final Logger log = Logger.getLogger(HostelFeesTypeHandler.class);
	private static volatile HostelFeesTypeHandler hostelFeesTypeHandler = null;

	IHostelFeesTypeTransaction iHostelFeesTypeTransaction = new HostelFeesTypeTransactionImpl();
	
	public static HostelFeesTypeHandler getInstance() {
		if (hostelFeesTypeHandler == null) {
			hostelFeesTypeHandler = new HostelFeesTypeHandler();
		}
		return hostelFeesTypeHandler;
	}
	
	/** 
	 * Used to add FeeFinancialYear	
	 * @param com.kp.cms.forms.fee.FeeFinancialYearEntryForm, javax.servlet.http.HttpServletRequest
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public boolean addHostelFeesType(HostelFeesTypeForm hostelFeesTypeForm, ActionErrors errors) throws Exception {
		log.info("Start of addHostelFeesType of HostelFeesTypeHandler");

		HlFeeType hlFeeType = null;
		HostelFeesTypeTo hostelFeesTypeTo = new HostelFeesTypeTo();
		
		hostelFeesTypeTo.setCreatedBy(hostelFeesTypeForm.getUserId());
		hostelFeesTypeTo.setModifiedBy(hostelFeesTypeForm.getUserId());		
		hostelFeesTypeTo.setHostelFeesType(hostelFeesTypeForm.getHostelFeesType());		
		hlFeeType = HostelFeesTypeHelper.getInstance().populateTOtoBO(hostelFeesTypeTo);
		if (hlFeeType != null && iHostelFeesTypeTransaction != null) {
			return iHostelFeesTypeTransaction.addHostelFeesType(hlFeeType);
		}		
		log.info("End of addHostelFeesType of HostelFeesTypeHandler");
		return false;
	}

	
	/** 
	 * Used to get FeeFinancialYear Details	
	 * @param void
	 * @return List<FeeFinancialYear>
	 * @throws Exception
	 */
	public List<HostelFeesTypeTo> getHostelFeesTypeDetails() throws Exception {
		log.info("Start of getHostelFeesTypeDetails of HostelFeesTypeHandler");

		if (iHostelFeesTypeTransaction != null) {
			List<HlFeeType> hostelFeesTypeList = iHostelFeesTypeTransaction.getHostelFeesTypeDetails();					
			if (hostelFeesTypeList != null) {
				return HostelFeesTypeHelper.getInstance().poupulateBOtoTO(hostelFeesTypeList);
			}
		}
		log.info("End of getHostelFeesTypeDetails of HostelFeesTypeHandler");
		return new ArrayList<HostelFeesTypeTo>();
	}
	
	/** 
	 * Used to get HlFeeType	
	 * @param java.lang.String
	 * @return com.kp.cms.bo.admin.FeeFinancialYear
	 * @throws Exception
	 */
	public HlFeeType getHostelFeesType(String feeType) throws Exception {
		log.info("Start of getHostelFeesType of HostelFeesTypeHandler");
		if (iHostelFeesTypeTransaction != null) {
			return iHostelFeesTypeTransaction.getHostelFeesType(feeType);
		}
		log.info("End of getHostelFeesType of HostelFeesTypeHandler");
		return new HlFeeType();
	}
	
	/** 
	 * Used to get HlFeeType with Id	
	 * @param int
	 * @return HostelFeesTypeTo
	 * @throws Exception
	 */
	public HostelFeesTypeTo getHostelFeesTypeDetailsWithId(int id) throws Exception {
		log.info("Start of getHostelFeesTypeDetailsWithId of HostelFeesTypeHandler");
		if (iHostelFeesTypeTransaction != null) {
			HlFeeType hlFeeType = iHostelFeesTypeTransaction.getHostelFeesTypeDetailsWithId(id);
			if (hlFeeType != null) {
				return HostelFeesTypeHelper.getInstance().populateBOtoTOEdit(hlFeeType);
			}
		}
		log.info("End of getHostelFeesTypeDetailsWithId of HostelFeesTypeHandler");
		return new HostelFeesTypeTo();
	}
	/** 
	 * Used to update FeeFinancialYear
	 * @param com.kp.cms.forms.fee.FeeFinancialYearEntryForm
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public boolean updateHostelFeesTypeDetails(HostelFeesTypeForm hostelFeesTypeForm, ActionErrors errors) throws Exception {
		log.info("Start of updateHostelFeesTypeDetails of HostelFeesTypeHandler");	
		
		HlFeeType hlFeeType = null;
		HostelFeesTypeTo hostelFeesTypeTo = new HostelFeesTypeTo();		
		
		if (hostelFeesTypeForm != null) {
			hostelFeesTypeTo.setId(hostelFeesTypeForm.getId());			
			hostelFeesTypeTo.setHostelFeesType(hostelFeesTypeForm.getHostelFeesType());
			hostelFeesTypeTo.setModifiedBy(hostelFeesTypeForm.getUserId());
			hostelFeesTypeTo.setCreatedBy(hostelFeesTypeForm.getUserId());
		}
		hlFeeType = HostelFeesTypeHelper.getInstance().populateTOtoBOUpdate(hostelFeesTypeTo);
		if (iHostelFeesTypeTransaction != null && hlFeeType != null) {
			return iHostelFeesTypeTransaction.updateHostelFeesType(hlFeeType);
		}
		
		log.info("End of updateHostelFeesTypeDetails of HostelFeesTypeHandler");
		return false;
	}
	
	/** 
	 * Used to delete HlFeeType Details
	 * @param int, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteHostelFeesTypeDetails(int id, String userId)
			throws Exception {
		log.info("Start of deleteHostelFeesTypeDetails of HostelFeesTypeHandler");
		if (iHostelFeesTypeTransaction != null) {
			return iHostelFeesTypeTransaction.deleteHostelFeesTypeDetails(id, userId);
		}
		log.info("End of deleteHostelFeesTypeDetails of HostelFeesTypeHandler");
		return false;
	}
	
	/** 
	 * Used to reactivate HlFeeType	
	 * @param java.lang.String, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reActivateHostelFeesType(String feeType, String userId)
			throws Exception {
		log.info("Start of reActivateHostelFeesType of HostelFeesTypeHandler");
		if (iHostelFeesTypeTransaction != null) {
			return iHostelFeesTypeTransaction.reActivateHostelFeesType(feeType, userId);
		}
		log.info("End of reActivateHostelFeesType of HostelFeesTypeHandler");
		return false;
	}
}
