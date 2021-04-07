package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.HlStudentFacilityAllotted;
import com.kp.cms.forms.hostel.HostelStudentCheckOutForm;
import com.kp.cms.transactions.hostel.IHostelStudentCheckOutTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelStudentCheckOutTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelStudentCheckOutHandler {
	
	private static final Log log=LogFactory.getLog(HostelStudentCheckOutHandler.class);
	public static volatile HostelStudentCheckOutHandler hlAdmissionHandler=null;
	
	public static HostelStudentCheckOutHandler getInstance(){
		if(hlAdmissionHandler==null)
		{
			hlAdmissionHandler=new HostelStudentCheckOutHandler();
			return hlAdmissionHandler;
		}
		return hlAdmissionHandler;
	}
	
	public HlAdmissionBo verifyRegisterNumberAndGetStudentDetails(HostelStudentCheckOutForm checkOutForm) throws Exception
	{
		IHostelStudentCheckOutTransaction iDetailsTransaction = HostelStudentCheckOutTransactionImpl.getInstance();
		 HlAdmissionBo hlAdmissionBo=iDetailsTransaction.verifyRegisterNumberAndGetDetails(checkOutForm);
		return hlAdmissionBo;
		
	}

	public List<HlStudentFacilityAllotted> getStudentFacilitiesAlloted(HostelStudentCheckOutForm checkOutForm) throws Exception
	{
		
		IHostelStudentCheckOutTransaction iDetailsTransaction = HostelStudentCheckOutTransactionImpl.getInstance();
		List<HlStudentFacilityAllotted> facilityList = iDetailsTransaction.getStudentFacilitiesAlloted(checkOutForm);
		
		return facilityList;
	}

	public List<HlAdmissionBo> getStudentCheckOutInformation(HostelStudentCheckOutForm checkOutForm) throws Exception
	{
		
		IHostelStudentCheckOutTransaction iDetailsTransaction = HostelStudentCheckOutTransactionImpl.getInstance();
		List<HlAdmissionBo> checkOutList = iDetailsTransaction.getStudentCheckOutInformation(checkOutForm);
		
		return checkOutList;
	}
	
	public boolean addCheckOut(HostelStudentCheckOutForm checkOutForm)throws Exception
	{
		log.info("Inside addCheckOut of HostelStudentCheckOutHandler");
		IHostelStudentCheckOutTransaction iDetailsTransaction = HostelStudentCheckOutTransactionImpl.getInstance();
		
		HlAdmissionBo hlAdmissionBo=iDetailsTransaction.getDetailsForMerge(checkOutForm);
		
		if(hlAdmissionBo!=null){
//			hlAdmissionBo.setIsCheckedIn(false);
			hlAdmissionBo.setCheckOutRemarks(checkOutForm.getCheckOutRemarks());
			hlAdmissionBo.setCheckOutDate(CommonUtil.ConvertStringToSQLDate(checkOutForm.getDate()));
			hlAdmissionBo.setCheckOut(true);
			hlAdmissionBo.setIsFacilityVerified(true);
			
			hlAdmissionBo.setModifiedBy(checkOutForm.getUserId());
			hlAdmissionBo.setLastModifiedDate(new Date());

		}
		if(hlAdmissionBo!=null){
			return iDetailsTransaction.addCheckOut(hlAdmissionBo);
		}
		log.info("Leaving of addCheckOut of HostelStudentCheckOutHandler");
		return false;
	}
	

}
