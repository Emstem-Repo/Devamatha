package com.kp.cms.handlers.hostel;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.transactions.hostel.IHostelDailyReportTransactions;
import com.kp.cms.helpers.hostel.HostelDailyReportHelper;

import com.kp.cms.transactionsimpl.hostel.HostelDailyReportTransactionImpl;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.forms.hostel.HostelDailyReportForm;
import com.kp.cms.forms.hostel.HostelReqReportForm;
import com.kp.cms.helpers.hostel.HostelReqReportHelper;
import com.kp.cms.to.hostel.HostelDailyReportTo;
import com.kp.cms.to.hostel.HostelReqReportTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelReqReportTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelReqReportTransactionImpl;
import com.kp.cms.helpers.hostel.HostelDailyReportHelper;

public class HostelDailyReportHandler {
	
	private static Log log = LogFactory.getLog(HostelDailyReportHandler.class);
	private static volatile HostelDailyReportHandler hostelDailyReportHandler = null;
	IHostelDailyReportTransactions transaction = HostelDailyReportTransactionImpl.getInstance();

	private HostelDailyReportHandler() {
	}

	/**
	 * This method returns the single instance of this HostelReqReportHandler.
	 * 
	 * @return
	 */
	public static HostelDailyReportHandler getInstance() {
		if (hostelDailyReportHandler == null) {
			hostelDailyReportHandler = new HostelDailyReportHandler();
		}
		return hostelDailyReportHandler;
	}
	
	/**
	 * 
	 * @param getHostelDailyReportDetails
	 * @return List<HostelDailyReportTo> getHostelDailyReportDetails
	 * @throws Exception
	 */
	public List<HostelDailyReportTo> getHostelDailyReportDetails(HostelDailyReportForm hostelDailyReportForm) throws Exception {

		log.info("Entering getHostelDailyReportDetails of HostelDailyReportHandler");
		
		HostelDailyReportHelper hostelDailyReportHelper = HostelDailyReportHelper.getInstance();
		List<Object> hostelDailyReportDetailsBo = transaction.getHostelDailyReportDetails(hostelDailyReportHelper.getSearchCriteria(hostelDailyReportForm));
		List<HostelDailyReportTo> hostelDailyReportToList = hostelDailyReportHelper.convertBOtoTO(hostelDailyReportDetailsBo,hostelDailyReportForm );
		
		log.info("Exiting getHostelDailyReportDetails of HostelDailyReportHandler");
		return hostelDailyReportToList;
	}
	
	/**
	 * 
	 * @param HostelDailyReportHandler
	 * @return
	 * @throws Exception
	 */
	public List<HostelTO> getHostelDetails() throws Exception {
		log.info("Entering getHostelDetails of HostelReqReportHandler");
		List<HlHostel> hostelList = transaction.getHostelNames();
		List<HostelTO> hostelTOList = HostelDailyReportHelper.getInstance().copyHostelBosToTos(hostelList);
		log.info("Exitinging getHostelDetails of HostelReqReportHandler");
		return hostelTOList;
	}
}
