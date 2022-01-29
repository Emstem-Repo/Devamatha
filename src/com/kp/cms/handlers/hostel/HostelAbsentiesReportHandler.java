package com.kp.cms.handlers.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.hostel.HostelAbsentiesReportForm;
import com.kp.cms.forms.hostel.HostelDailyReportForm;
import com.kp.cms.helpers.hostel.HostelAbsentiesReportHelper;

import com.kp.cms.to.hostel.HostelAbsentiesReportTo;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.helpers.hostel.HostelDailyReportHelper;
import com.kp.cms.to.hostel.HlGroupTo;
import com.kp.cms.to.hostel.HostelDailyReportTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelDailyReportTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelDailyReportTransactionImpl;
import com.kp.cms.to.hostel.HostelAbsentiesReportTo;
import com.kp.cms.bo.admin.HlGroup;
import com.kp.cms.transactions.hostel.IHostelAbsentiesReportTransactions;

import com.kp.cms.transactionsimpl.hostel.HostelAbsentiesReportTransactionImpl;

public class HostelAbsentiesReportHandler {
	
	private static Log log = LogFactory.getLog(HostelAbsentiesReportHandler.class);
	private static volatile HostelAbsentiesReportHandler hostelAbsentiesReportHandler = null;
	IHostelAbsentiesReportTransactions transaction = HostelAbsentiesReportTransactionImpl.getInstance();

	private HostelAbsentiesReportHandler() {
	}

	/**
	 * This method returns the single instance of this HostelReqReportHandler.
	 * 
	 * @return
	 */
	public static HostelAbsentiesReportHandler getInstance() {
		if (hostelAbsentiesReportHandler == null) {
			hostelAbsentiesReportHandler = new HostelAbsentiesReportHandler();
		}
		return hostelAbsentiesReportHandler;
	}
	
	/**
	 * 
	 * @param getHostelDailyReportDetails
	 * @return List<HostelDailyReportTo> getHostelDailyReportDetails
	 * @throws Exception
	 */
	public List<HostelAbsentiesReportTo> getHostelAbsentDetails(HostelAbsentiesReportForm hostelAbsentiesReportForm) throws Exception {

		log.info("Entering getHostelAbsentDetails of HostelAbsentiesReportHandler");
		
		HostelAbsentiesReportHelper hostelAbsentiesReportHelper = HostelAbsentiesReportHelper.getInstance();
		List<Object> hostelAbsentiesDetailsBo = transaction.getHostelAbsentDetails(hostelAbsentiesReportHelper.getSearchCriteria(hostelAbsentiesReportForm));
		List<HostelAbsentiesReportTo> hostelAbsentiesToList = hostelAbsentiesReportHelper.convertBOtoTO(hostelAbsentiesDetailsBo,hostelAbsentiesReportForm);
		
		log.info("Exiting getHostelAbsentDetails of HostelAbsentiesReportHandler");
		return hostelAbsentiesToList;
	}
	/**
	 * 
	 * @param HostelAbsentiesReportHandler
	 * @return
	 * @throws Exception
	 */
	public List<HostelTO> getHostelDetails() throws Exception {
		log.info("Entering getHostelDetails of HostelAbsentiesReportHandler");
		List<HlHostel> hostelList = transaction.getHostelNames();
		List<HostelTO> hostelTOList = HostelAbsentiesReportHelper.getInstance().copyHostelBosToTos(hostelList);
		log.info("Exitinging getHostelDetails of HostelAbsentiesReportHandler");
		return hostelTOList;
	}
	
	
	/**
	 * 
	 * @param HostelAbsentiesReportHandler
	 * @return
	 * @throws Exception
	 */
	public List<HlGroupTo> getHlGroupDetails() throws Exception {
		log.info("Entering getHostelDetails of HostelAbsentiesReportHandler");
		List<HlGroup> hlGroupList = transaction.getHlGroupDetails();
		List<HlGroupTo> hlGroupTOList = HostelAbsentiesReportHelper.getInstance().copyHlGroupBosToTos(hlGroupList);
		log.info("Exitinging getHostelDetails of HostelAbsentiesReportHandler");
		return hlGroupTOList;
	}

}
