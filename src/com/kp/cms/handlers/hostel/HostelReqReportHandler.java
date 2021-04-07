package com.kp.cms.handlers.hostel;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.forms.hostel.HostelReqReportForm;
import com.kp.cms.helpers.hostel.HostelReqReportHelper;
import com.kp.cms.transactions.hostel.IHostelReqReportTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelReqReportTransactionImpl;
import com.kp.cms.to.hostel.HostelReqReportTo;

public class HostelReqReportHandler {
	private static Log log = LogFactory.getLog(HostelReqReportHandler.class);
	private static volatile HostelReqReportHandler hostelReqReportHandler = null;
	IHostelReqReportTransactions transaction = HostelReqReportTransactionImpl.getInstance();

	private HostelReqReportHandler() {
	}

	/**
	 * This method returns the single instance of this HostelReqReportHandler.
	 * 
	 * @return
	 */
	public static HostelReqReportHandler getInstance() {
		if (hostelReqReportHandler == null) {
			hostelReqReportHandler = new HostelReqReportHandler();
		}
		return hostelReqReportHandler;
	}
	
	/**
	 * 
	 * @param HostelReqReportForm
	 * @return
	 * @throws Exception
	 */
	public List<HostelReqReportTo> getReqReportDetails(
			HostelReqReportForm hostelReqReportForm) throws Exception {

		log.info("Entering getApplicantHostelDetails of HostelReqReportHandler");
		HostelReqReportHelper hostelReqReportHelper = HostelReqReportHelper
				.getInstance();
		List<Object> hostelReqReportDetailsBo = transaction.getHostelReqReportDetailsBo(hostelReqReportHelper.getSearchCriteria(hostelReqReportForm));
		List<HostelReqReportTo> hostelReqReportToList = hostelReqReportHelper
				.convertBOtoTO(hostelReqReportDetailsBo,hostelReqReportForm );
		List<Object> hostelRoomTypeBo = transaction.getHostelRoomTypeAvailabilityBo(hostelReqReportHelper.getRoomTypeCriteria(hostelReqReportForm));
		List<HostelReqReportTo> hostelRoomTypeToList = hostelReqReportHelper.convertRoomBOtoTO(hostelRoomTypeBo,hostelReqReportForm );
		hostelReqReportForm.setHostelRoomTypeToList(hostelRoomTypeToList);
		log.info("Exiting getApplicantHostelDetails of HostelReqReportHandler");
		return hostelReqReportToList;
	}
	/**
	 * 
	 * @param HostelReqReportHandler
	 * @return
	 * @throws Exception
	 */
	public List<HostelTO> getHostelDetails() throws Exception {
		log.info("Entering getHostelDetails of HostelReqReportHandler");
		List<HlHostel> hostelList = transaction.getHostelNames();
		List<HostelTO> hostelTOList = HostelReqReportHelper.getInstance().copyHostelBosToTos(hostelList);
		log.info("Exitinging getHostelDetails of HostelReqReportHandler");
		return hostelTOList;
	}

}
