package com.kp.cms.handlers.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.forms.hostel.HostelApplicationByAdminForm;
import com.kp.cms.helpers.hostel.HostelApplicationByAdminHelper;
import com.kp.cms.helpers.hostel.HostelApplicationHelper;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.hostel.HostelApplicationByAdminTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.to.hostel.RoomTypeWithAmountTO;
import com.kp.cms.transactions.hostel.IHostelApplicationByAdminTransactions;
import com.kp.cms.transactions.hostel.IHostelApplicationTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelApplicationByAdminTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelApplicationTransactionImpl;

public class HostelApplicationByAdminHandler {
	
	private static volatile HostelApplicationByAdminHandler hostelApplicationByAdminHandler = null;
	private static final Log log = LogFactory.getLog(HostelApplicationByAdminHandler.class);

	private HostelApplicationByAdminHandler() {

	}
	
	public static HostelApplicationByAdminHandler getInstance() {
		if (hostelApplicationByAdminHandler == null) {
			hostelApplicationByAdminHandler = new HostelApplicationByAdminHandler();
		}
		return hostelApplicationByAdminHandler;
	}
	
	public HostelApplicationByAdminTO getHostelApplicationTO(HostelApplicationByAdminForm hostelApplicationByAdminForm)
	throws Exception {
	    HostelApplicationByAdminHelper helper = HostelApplicationByAdminHelper.getInstance();
	    IHostelApplicationByAdminTransactions transaction = new HostelApplicationByAdminTransactionImpl();
	    AdmAppln admAppln=null;
	    Employee employee=null;
	    HostelApplicationByAdminTO hostelApplicationTO = null;
	    if((hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty())||
			(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty())||
			 (hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty())){
			String admApplnQuery = helper.getSearchQuery(hostelApplicationByAdminForm);
			admAppln = transaction.getAdmApplnByQuery(admApplnQuery);
			if (admAppln != null){
				hostelApplicationTO = helper.convertBOtoTOForStudent(admAppln,hostelApplicationByAdminForm);	
			}
	    }
	    else if(hostelApplicationByAdminForm.getStaffId().trim()!=null && !hostelApplicationByAdminForm.getStaffId().trim().isEmpty()){
			 String employeeId=hostelApplicationByAdminForm.getStaffId();
			 employee=transaction.getEmployee(employeeId);
			 if(employee!=null){
				 hostelApplicationTO = helper.convertBOtoTOForStaff(employee,hostelApplicationByAdminForm); 
			 }
	    }
		return hostelApplicationTO;
	}
	
	public List<RoomTypeTO> getRoomTypes(HostelApplicationByAdminForm hostelApplicationByAdminForm)throws Exception{
		
		IHostelApplicationByAdminTransactions transaction = new HostelApplicationByAdminTransactionImpl();
		int hostelId=Integer.parseInt(hostelApplicationByAdminForm.getHostelId());
		List<HlRoomType> roomTypes=transaction.getRoomTypesForHostel(hostelId);
		HostelApplicationByAdminHelper helper = HostelApplicationByAdminHelper.getInstance();
		List<RoomTypeTO> roomTypeTOList=helper.copyRoomTypeBOToTOs(roomTypes);
		return roomTypeTOList;
		
	}
	
	public boolean saveHlApplicationForm(HostelApplicationByAdminForm hostelApplicationByAdminForm)
	throws Exception {

		log.info("call of saveHlApplicationForm method in HostelDamageHandler class.");
		IHostelApplicationByAdminTransactions transaction = new HostelApplicationByAdminTransactionImpl();
		boolean hlApplicationSaved=false;
		HostelApplicationByAdminHelper helper = HostelApplicationByAdminHelper.getInstance();
		AdmAppln admAppln =null;
		 if((hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty())||
					(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty())||
					 (hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty())){
			 String admApplnQuery = helper.getSearchQuery(hostelApplicationByAdminForm);
			 admAppln = transaction.getAdmApplnByQuery(admApplnQuery);
		 }	 
		Employee employee=null;
		if(hostelApplicationByAdminForm.getStaffId().trim()!=null && !hostelApplicationByAdminForm.getStaffId().trim().isEmpty()){
			String employeeId=hostelApplicationByAdminForm.getStaffId();
			employee=transaction.getEmployee(employeeId);
		}
		HlApplicationForm hlApplicationForm=HostelApplicationByAdminHelper.getInstance().getHlApplicationForm(hostelApplicationByAdminForm,admAppln,employee);
		if(hlApplicationForm!=null){
			hlApplicationSaved = transaction.saveHlApplicationForm(hlApplicationForm);
		}
		log.info("end of saveHlApplicationForm method in HostelDamageHandler class.");
		return hlApplicationSaved;
	}
	
	public HostelTO viewTermsCondition(HostelApplicationByAdminForm applicationForm)throws Exception{
		log.info("Entering into viewTermsCondition of HostelApplicationByAdminHandler");
		int hostelId = Integer.parseInt(applicationForm.getHostelId());
		IHostelApplicationByAdminTransactions transaction = new HostelApplicationByAdminTransactionImpl();
		HlHostel hostel = transaction.getTermsConditionforHostel(hostelId);
		log.info("Leaving into viewTermsCondition of HostelApplicationByAdminHandler");
		HostelTO hostelTO=HostelApplicationByAdminHelper.getInstance().prepareTermsConditionView(hostel);
		return hostelTO;
	}
	
	public PersonalDataTO viewPersonalDetails(HostelApplicationByAdminForm hostelApplicationByAdminForm)throws Exception{
		log.info("Entering into viewPersonalDetails of HostelApplicationByAdminHandler");
		IHostelApplicationByAdminTransactions transaction = new HostelApplicationByAdminTransactionImpl();
		 HostelApplicationByAdminHelper helper = HostelApplicationByAdminHelper.getInstance();
		    AdmAppln admAppln=null;
		    Employee employee=null;
		    PersonalDataTO personalDataTO=null;
		    if((hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty())||
				(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty())||
				 (hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty())){
				String admApplnQuery = helper.getSearchQuery(hostelApplicationByAdminForm);
				admAppln = transaction.getAdmApplnByQuery(admApplnQuery);
				if (admAppln != null){
					personalDataTO=HostelApplicationByAdminHelper.getInstance().viewPersonalDetailsForStudent(admAppln);	
				}
		    }
		    else if(hostelApplicationByAdminForm.getStaffId().trim()!=null && !hostelApplicationByAdminForm.getStaffId().trim().isEmpty()){
				 String employeeId=hostelApplicationByAdminForm.getStaffId();
				 employee=transaction.getEmployee(employeeId);
				 if(employee!=null){
					 personalDataTO=HostelApplicationByAdminHelper.getInstance().viewPersonalDetailsForStaff(employee);	 
				 }
		    }
		log.info("Leaving into viewPersonalDetails of HostelApplicationByAdminHandler");
		
		return personalDataTO;
	}
	
	public List<RoomTypeWithAmountTO> getRoomTypesWithFeeDetails(int hostelId)throws Exception{
		log.info("Entering into getRoomTypwiseFees of HostelApplicationByAdminHandler");
		IHostelApplicationByAdminTransactions transaction = new HostelApplicationByAdminTransactionImpl();
		List<HlFees> hlFeesList = transaction.getRoomTypewiseHostelFeesonHostelId(hostelId);
		log.info("Leaving into getRoomTypwiseFees of HostelApplicationByAdminHandler");
		return HostelApplicationByAdminHelper.getInstance().copyFeesBOsToTO(hlFeesList);
	}	

}
