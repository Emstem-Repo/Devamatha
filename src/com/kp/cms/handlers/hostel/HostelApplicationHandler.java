package com.kp.cms.handlers.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.forms.hostel.AssignRoomMasterForm;
import com.kp.cms.forms.hostel.HostelApplicationForm;
import com.kp.cms.helpers.hostel.HostelApplicationHelper;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.to.hostel.RoomTypeWithAmountTO;
import com.kp.cms.transactions.hostel.IHostelApplicationTransaction;
import com.kp.cms.transactions.hostel.IHostelEntryTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelApplicationTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelEntryTransactionImpl;

public class HostelApplicationHandler {
	
	private static final Log log = LogFactory.getLog(HostelApplicationHandler.class);
	
	public static volatile HostelApplicationHandler hostelApplicationHandler = null;

	private HostelApplicationHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static HostelApplicationHandler getInstance() {
		if (hostelApplicationHandler == null) {
			hostelApplicationHandler = new HostelApplicationHandler();
		}
		return hostelApplicationHandler;
	}
	
	/**
	 * Used to get all hostel details
	 */
	public List<HostelTO> getHostelDetails()throws Exception{
		log.info("Entering into getHostelDetails of HostelApplicationHandler");
		IHostelEntryTransactions transaction = new HostelEntryTransactionImpl();
		List<HlHostel> hostelList = transaction.getHostelDeatils();
		log.info("Leaving into getHostelDetails of HostelApplicationHandler");
		return HostelApplicationHelper.getInstance().copyHostelBOToTO(hostelList);
	}
	/**
	 * Used to get Room Type Names based on the hostel Id
	 * @param roomForm 
	 */
	public List<RoomTypeTO> getRoomTypesonHostelId(int hostelId, AssignRoomMasterForm roomForm)throws Exception{
		log.info("Entering into getRoomTypesonHostelId of HostelApplicationHandler");
		IHostelApplicationTransaction transaction = new HostelApplicationTransactionImpl();
		List<HlRoomType> roomTypeList = transaction.getRoomTypesonHostelId(hostelId);
		log.info("Leaving into getRoomTypesonHostelId of HostelApplicationHandler");
		return HostelApplicationHelper.getInstance().copyRoomTypeBOToTOs(roomTypeList,roomForm);
	}
	
	/**
	 * Used to submit the application details of student
	 */
	public boolean submitApplicationStudentDetails(HostelApplicationForm applicationForm, String studentid, String studentLoginId)throws Exception{
		log.info("Entering into submitApplicationStudentDetails of HostelApplicationHandler");
		IHostelApplicationTransaction transaction = new HostelApplicationTransactionImpl();
		HlApplicationForm hlApplicationFormBO = HostelApplicationHelper.getInstance().prepareApplicationInformation(applicationForm, studentid, studentLoginId);
		log.info("Leaving into submitApplicationStudentDetails of HostelApplicationHandler");
		return transaction.submitApplicationStudentDetails(hlApplicationFormBO);
	}
	/**
	 * Used for viewing the Terms & Condition
	 * 
	 */
	public HostelTO viewTermsCondition(HostelApplicationForm applicationForm,HttpServletRequest request)throws Exception{
		log.info("Entering into viewTermsCondition of HostelApplicationHandler");
		int hostelId = Integer.parseInt(applicationForm.getHostelId());
		IHostelApplicationTransaction transaction = new HostelApplicationTransactionImpl();
		HlHostel hostel = transaction.getTermsConditionforHostel(hostelId);
		log.info("Leaving into viewTermsCondition of HostelApplicationHandler");
		return HostelApplicationHelper.getInstance().prepareTermsConditionView(hostel,applicationForm, request);
	}
	
	/**
	 * Used to get Hostel details and roomtypes based on HostelId
	 * Used to view hostel details
	 */
	public void getHostelRoomTypesByHostelID(HostelApplicationForm applicationForm, HttpServletRequest request)throws Exception{
		int hostelId = Integer.parseInt(applicationForm.getHostelId());
		IHostelApplicationTransaction transaction = new HostelApplicationTransactionImpl();
		HlHostel hostelBO = transaction.getHostelRoomTypesByHostelID(hostelId);
		HostelApplicationHelper.getInstance().preparePropertiesToView(hostelBO, applicationForm,request);
	}
	/**
	 * Used to get Room Type Names based on the hostel Id
	 */
	public List<RoomTypeWithAmountTO> getRoomTypwiseFees(int hostelId)throws Exception{
		log.info("Entering into getRoomTypwiseFees of HostelApplicationHandler");
		IHostelApplicationTransaction transaction = new HostelApplicationTransactionImpl();
		List<HlFees> hlFeesList = transaction.getRoomTypewiseHostelFeesonHostelId(hostelId);
		log.info("Leaving into getRoomTypwiseFees of HostelApplicationHandler");
		return HostelApplicationHelper.getInstance().copyFeesBOsToTO(hlFeesList);
	}	
}
