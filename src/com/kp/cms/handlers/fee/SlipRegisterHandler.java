package com.kp.cms.handlers.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.fee.SlipRegisterForm;
import com.kp.cms.handlers.reports.AddressReportHandler;
import com.kp.cms.helpers.fee.SlipRegisterHelper;
import com.kp.cms.helpers.reports.AddressReportHelper;
import com.kp.cms.to.fee.SlipRegisterTO;
import com.kp.cms.to.reports.StudentAddressTo;
import com.kp.cms.transactions.fee.ISlipRegisterTransaction;
import com.kp.cms.transactionsimpl.fee.SlipRegisterTransactionImpl;

public class SlipRegisterHandler {
	private static final Log log = LogFactory.getLog(AddressReportHandler.class);
	
	private static SlipRegisterHandler slipRegisterHandler = null;

	private SlipRegisterHandler() {
	}
	/**
	 * 
	 * @returns a single instance when called
	 */
	public static SlipRegisterHandler getInstance() {
		if (slipRegisterHandler == null) {
			slipRegisterHandler = new SlipRegisterHandler();
		}
		return slipRegisterHandler;
	}
	
	
		public List<SlipRegisterTO> getSlipRegisterTOList(SlipRegisterForm slipRegisterForm)
				throws Exception {
			log.info("entering into getAbsenceInformationSummaryTOList of AbsenceInformationSummaryHandler class.");
			ISlipRegisterTransaction slipRegisterTransaction= new SlipRegisterTransactionImpl();
			String dynamicQuery = SlipRegisterHelper.buildQuery(slipRegisterForm);
			List<Object[]> slipRegisterBo = slipRegisterTransaction.getSlipRegisterRecords(dynamicQuery);
			List<SlipRegisterTO> SlipRegisterTOList =SlipRegisterHelper.copyStudentAddressBOToTO(slipRegisterBo);
	    	log.info("Leaving into getAddres Details of of AddressReportHandler");
			return  SlipRegisterTOList;
	
		}
		
	

}
