package com.kp.cms.helpers.hostel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.HostelVisitorsInfoBo;
import com.kp.cms.forms.hostel.FineEntryForm;
import com.kp.cms.forms.hostel.HostelVisitorsInfoForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.hostel.HostelVisitorsInfoTo;
import com.kp.cms.transactions.hostel.IHostelVisitorsInfoTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelVisitorsInfoTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


public class HostelVisitorsInfoHelper {
	private static Log log = LogFactory.getLog(HostelVisitorsInfoHelper.class);
	IHostelVisitorsInfoTransaction transaction=HostelVisitorsInfoTransactionImpl.getInstance();
	/**
	 * instance()
	 */
	public static volatile HostelVisitorsInfoHelper hostelVisitorsInfoHelper = null;
	public static HostelVisitorsInfoHelper getInstance() {
		if (hostelVisitorsInfoHelper == null) {
			hostelVisitorsInfoHelper = new HostelVisitorsInfoHelper();
			return hostelVisitorsInfoHelper;
		}
		return hostelVisitorsInfoHelper;
	}
	public HostelVisitorsInfoBo convertFormToBo(HostelVisitorsInfoForm hostelVisitorsInfoForm,String hlAdminId)throws Exception{
		HostelVisitorsInfoBo hostelVisitorsInfoBo=null;
		if(hlAdminId!=null){
			hostelVisitorsInfoBo=new HostelVisitorsInfoBo();
			HlAdmissionBo hlAdmissionBo=new HlAdmissionBo();
			hlAdmissionBo.setId(Integer.parseInt(hlAdminId));
			hostelVisitorsInfoBo.setHlAdmissionId(hlAdmissionBo);
			hostelVisitorsInfoBo.setVisitorName(hostelVisitorsInfoForm.getVisitorName());
			if(hostelVisitorsInfoForm.getContactNo()!=null && !hostelVisitorsInfoForm.getContactNo().isEmpty()){
				hostelVisitorsInfoBo.setContactNo(hostelVisitorsInfoForm.getContactNo());
			}
			hostelVisitorsInfoBo.setInTime(CommonUtil.getTime(hostelVisitorsInfoForm.getInHours(),hostelVisitorsInfoForm.getInMins()));
			hostelVisitorsInfoBo.setOutTime(CommonUtil.getTime(hostelVisitorsInfoForm.getOutHours(),hostelVisitorsInfoForm.getOutMins()));
			hostelVisitorsInfoBo.setDate(CommonUtil.ConvertStringToDate(hostelVisitorsInfoForm.getDate()));
			hostelVisitorsInfoBo.setCreatedDate(new Date());
			hostelVisitorsInfoBo.setLastModifiedDate(new Date());
			hostelVisitorsInfoBo.setCreatedBy(hostelVisitorsInfoForm.getUserId());
			hostelVisitorsInfoBo.setModifiedBy(hostelVisitorsInfoForm.getUserId());
			hostelVisitorsInfoBo.setIsActive(true);
			
		}
		return hostelVisitorsInfoBo;
	}
	/**
	 * convert BoList to ToList
	 */
	public List<HostelVisitorsInfoTo> convertBoListToToList(List<HostelVisitorsInfoBo> listVisitorsInfoBos)throws Exception{
		List<HostelVisitorsInfoTo> listHostelVisitorsInfoTos=new ArrayList<HostelVisitorsInfoTo>();
		HostelVisitorsInfoTo hostelVisitorsInfoTo=null;
		Iterator< HostelVisitorsInfoBo> iterator=listVisitorsInfoBos.iterator();
		while (iterator.hasNext()) {
			HostelVisitorsInfoBo hostelVisitorsInfoBo = (HostelVisitorsInfoBo) iterator.next();
			hostelVisitorsInfoTo=new HostelVisitorsInfoTo();
			HlAdmissionBo admissionBo=transaction.getRegisterNo(hostelVisitorsInfoBo.getHlAdmissionId().getId());
			if(admissionBo!=null){
				hostelVisitorsInfoTo.setRegisterNo(admissionBo.getStudentId().getRegisterNo());
			hostelVisitorsInfoTo.setHostelName(admissionBo.getHostelId().getName());
			}
			hostelVisitorsInfoTo.setDate(CommonUtil.formatDates(hostelVisitorsInfoBo.getDate()));
			hostelVisitorsInfoTo.setVisitorName(hostelVisitorsInfoBo.getVisitorName());
			hostelVisitorsInfoTo.setInTime(hostelVisitorsInfoBo.getInTime());
			hostelVisitorsInfoTo.setOutTime(hostelVisitorsInfoBo.getOutTime());
			hostelVisitorsInfoTo.setId(hostelVisitorsInfoBo.getId());
			listHostelVisitorsInfoTos.add(hostelVisitorsInfoTo);
		}
		return listHostelVisitorsInfoTos;
	}
	
public void setBotoForm(HostelVisitorsInfoForm hostelVisitorsInfoForm,HostelVisitorsInfoBo hostelVisitorsInfoBo)throws Exception{
	if(hostelVisitorsInfoBo!=null){
		hostelVisitorsInfoForm.setHostelId(String.valueOf(hostelVisitorsInfoBo.getHlAdmissionId().getHostelId().getId()));
		hostelVisitorsInfoForm.setAcademicYear(hostelVisitorsInfoBo.getHlAdmissionId().getAcademicYear());
		hostelVisitorsInfoForm.setRegNo(hostelVisitorsInfoBo.getHlAdmissionId().getStudentId().getRegisterNo());
		hostelVisitorsInfoForm.setVisitorName(hostelVisitorsInfoBo.getVisitorName());
		if(hostelVisitorsInfoBo.getContactNo()!=null && !hostelVisitorsInfoBo.getContactNo().isEmpty()){
			hostelVisitorsInfoForm.setContactNo(hostelVisitorsInfoBo.getContactNo());
		}
		hostelVisitorsInfoForm.setDate(CommonUtil.formatDates(hostelVisitorsInfoBo.getDate()));
		hostelVisitorsInfoForm.setInHours(hostelVisitorsInfoBo.getInTime().substring(0,2));
		hostelVisitorsInfoForm.setInMins(hostelVisitorsInfoBo.getInTime().substring(3, 5));
		hostelVisitorsInfoForm.setOutHours(hostelVisitorsInfoBo.getOutTime().substring(0, 2));
		hostelVisitorsInfoForm.setOutMins(hostelVisitorsInfoBo.getOutTime().substring(3, 5));
	}
	
}
/**
 * convertUpdateForm to Bo
 * @param hostelVisitorsInfoForm
 * @param hlAdminId
 * @return
 * @throws Exception
 */
public HostelVisitorsInfoBo convertUpdateFormToBo(HostelVisitorsInfoForm hostelVisitorsInfoForm,HostelVisitorsInfoBo hostelVisitorsInfoBo)throws Exception{
	if(hostelVisitorsInfoForm!=null){
		hostelVisitorsInfoBo.setId(hostelVisitorsInfoForm.getId());
		hostelVisitorsInfoBo.setVisitorName(hostelVisitorsInfoForm.getVisitorName());
		if(hostelVisitorsInfoForm.getContactNo()!=null && !hostelVisitorsInfoForm.getContactNo().isEmpty()){
			hostelVisitorsInfoBo.setContactNo(hostelVisitorsInfoForm.getContactNo());
		}
		hostelVisitorsInfoBo.setInTime(CommonUtil.getTime(hostelVisitorsInfoForm.getInHours(),hostelVisitorsInfoForm.getInMins()));
		hostelVisitorsInfoBo.setOutTime(CommonUtil.getTime(hostelVisitorsInfoForm.getOutHours(),hostelVisitorsInfoForm.getOutMins()));
		hostelVisitorsInfoBo.setDate(CommonUtil.ConvertStringToDate(hostelVisitorsInfoForm.getDate()));
	}
	return hostelVisitorsInfoBo;
}
// /* code added by chandra for hostel Transaction screen
public void getStudentDetails(HostelVisitorsInfoForm hostelVisitorsInfoForm,String regNo,String academicYear)throws Exception{
	HlAdmissionBo hlAdmissionBo=CommonAjaxHandler.getInstance().getStudentDetailsForVisitors(academicYear,regNo);
	if(hlAdmissionBo!=null)
	{
		hostelVisitorsInfoForm.setStudentName(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			if(hlAdmissionBo.getCourseId()!=null){
				hostelVisitorsInfoForm.setStudentCourse(hlAdmissionBo.getCourseId().getName());
			}else {
				hostelVisitorsInfoForm.setStudentCourse(null);
			}
			if(hlAdmissionBo.getRoomId()!=null){
				hostelVisitorsInfoForm.setStudentRoomNo(hlAdmissionBo.getRoomId().getName());
				hostelVisitorsInfoForm.setStudentBlock(hlAdmissionBo.getRoomId().getHlBlock().getName());
				hostelVisitorsInfoForm.setStudentUnit(hlAdmissionBo.getRoomId().getHlUnit().getName());
			}else{
				hostelVisitorsInfoForm.setStudentRoomNo(null);
				hostelVisitorsInfoForm.setStudentBlock(null);
				hostelVisitorsInfoForm.setStudentUnit(null);
			}
			if(hlAdmissionBo.getBedId()!=null){
				hostelVisitorsInfoForm.setStudentBedNo(hlAdmissionBo.getBedId().getBedNo());
			}else {
				hostelVisitorsInfoForm.setStudentBedNo(null);
			}
			
	}else {
		hostelVisitorsInfoForm.setStudentName(null);
		hostelVisitorsInfoForm.setStudentCourse(null);
		hostelVisitorsInfoForm.setStudentRoomNo(null);
		hostelVisitorsInfoForm.setStudentBlock(null);
		hostelVisitorsInfoForm.setStudentUnit(null);
		hostelVisitorsInfoForm.setStudentBedNo(null);
	}
}
// */ code added by chandra
}
