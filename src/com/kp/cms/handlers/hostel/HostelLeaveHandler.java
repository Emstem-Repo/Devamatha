package com.kp.cms.handlers.hostel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HlLeaveType;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.hostel.HostelLeaveForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.hostel.HostelEntryHelper;
import com.kp.cms.to.hostel.HostelBlocksTO;
import com.kp.cms.to.hostel.HostelLeaveApprovalTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelUnitsTO;
import com.kp.cms.transactions.employee.IHolidayDetailsTransaction;
import com.kp.cms.transactions.hostel.IHostelEntryTransactions;
import com.kp.cms.transactions.hostel.IHostelLeaveTransaction;
import com.kp.cms.transactionsimpl.employee.HolidayDetailsTxnImpl;
import com.kp.cms.transactionsimpl.hostel.HostelEntryTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelLeaveTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.JobScheduler;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class HostelLeaveHandler {

	
private static final Log log = LogFactory.getLog(HostelLeaveHandler.class);
	
	public static volatile HostelLeaveHandler hostelLeaveHandler = null;
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static HostelLeaveHandler getInstance() {
		if (hostelLeaveHandler == null) {
			hostelLeaveHandler = new HostelLeaveHandler();
		}
		return hostelLeaveHandler;
	}
	IHostelLeaveTransaction leaveTransaction = HostelLeaveTransactionImpl.getInstance();
	
	
	public List<HlLeave> getLeaveTypeList() throws Exception{
		log.info("entering of getLeaveTypeList in HostelLeaveHandler class..");
		
		log.info("exit of getLeaveTypeList in HostelLeaveHandler class..");
		return leaveTransaction.getLeaveTypeList();
	}
	
	/**
	 * used to save the Leave Details
	 * @param hostelLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveOrUpdateLeaveDetails(HostelLeaveForm hostelLeaveForm,HttpSession session,String mode) throws Exception{
		Session session1 = null;
		HlLeave leaveType = new HlLeave();
		HlLeaveType type = new HlLeaveType();
		if(mode.equalsIgnoreCase("save"))
		{
			leaveType.setCreatedBy(hostelLeaveForm.getUserId());
			leaveType.setCreatedDate(new Date());
			HlAdmissionBo hlAdmissionBo=new HlAdmissionBo();
			if(session.getAttribute("hostelAdmissionId").toString()!=null && !session.getAttribute("hostelAdmissionId").toString().isEmpty() ){
			Integer admissionId=(Integer) session.getAttribute("hostelAdmissionId");
			hlAdmissionBo.setId(admissionId);
			leaveType.setHlAdmissionBo(hlAdmissionBo);
			leaveType.setIsApproved(Boolean.TRUE);
			}
		}
		else
		{
			/*leaveType.setModifiedBy(hostelLeaveForm.getUserId());
			leaveType.setLastModifiedDate(new Date());
			leaveType.setId(Integer.parseInt(hostelLeaveForm.getId()));
			HlAdmissionBo hlAdmissionBo=new HlAdmissionBo();
			if(session.getAttribute("hostelLeaveAdmId").toString()!=null && !session.getAttribute("hostelLeaveAdmId").toString().isEmpty() ){
			Integer hlAdmisionId=(Integer) session.getAttribute("hostelLeaveAdmId");
			hlAdmissionBo.setId(hlAdmisionId);
			leaveType.setHlAdmissionBo(hlAdmissionBo);
			}*/
			session1 = HibernateUtil.getSession();
			leaveType = (HlLeave) session1.get(HlLeave.class, Integer.parseInt(hostelLeaveForm.getId()));
			leaveType.setModifiedBy(hostelLeaveForm.getUserId());
			leaveType.setLastModifiedDate(new Date());
		}
		if(hostelLeaveForm.getLeaveType()!=null && !hostelLeaveForm.getLeaveType().isEmpty())
		{
		type.setId(Integer.parseInt(hostelLeaveForm.getLeaveType()));
		leaveType.setHlLeaveType(type);
		}
		leaveType.setLeaveFrom(CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveFrom()));
		leaveType.setLeaveTo(CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveTo()));
		leaveType.setReasons(hostelLeaveForm.getReasons());
		
		leaveType.setIsActive(Boolean.TRUE);
		leaveType.setIsCanceled(Boolean.FALSE);
		leaveType.setFromSession(hostelLeaveForm.getLeaveFromSession());
		leaveType.setToSession(hostelLeaveForm.getLeaveToSession());
		leaveType.setRemarks(hostelLeaveForm.getRemarks());
		leaveType.setRequestType(hostelLeaveForm.getRequestType());
		leaveType.setAcademicYear(Integer.parseInt(hostelLeaveForm.getAcademicYear1()));
		return leaveTransaction.saveOrUpdateHostelLeaveDetails(leaveType,mode);
	}
	
	/**
	 * used to get Hostel Details 
	 * @return
	 * @throws Exception
	 */
	public List<HostelTO> getHostelDetails() throws Exception {
		log.debug("inside getHostelDetails");
		IHostelEntryTransactions iHostelEntryTransactions = HostelEntryTransactionImpl.getInstance();
		List<HlHostel> hostelList = iHostelEntryTransactions.getHostelDeatils();
		List<HostelTO> hostelTOList = HostelEntryHelper.getInstance().copyHostelBosToTos(hostelList);
		log.debug("leaving getHostelDetails");
		return hostelTOList;
	}
	
	public boolean duplicateHostelLeaveCheck(HostelLeaveForm hostelLeaveForm) throws Exception
	{
		String query="select l from HlLeave l join l.hlAdmissionBo adm join adm.studentId s where s.registerNo='"+hostelLeaveForm.getRegisterNo()+"'"+ 
		" and  l.isCanceled=0"+
		" and ((('"+CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveFrom())+"') between l.leaveFrom and l.leaveTo )" +
		" or (('"+CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveTo())+"') between l.leaveFrom and l.leaveTo)" +
		" or (('"+CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveFrom())+"') <= l.leaveFrom " +
		" and ('"+CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveTo())+"') >= l.leaveTo )) ";
		return leaveTransaction.duplicateHostelLeaveCheck(query,hostelLeaveForm);
		
	}
	
	public HlAdmissionBo verifyRegisterNumberAndGetName(BaseActionForm actionForm) throws Exception
	{
		IHostelEntryTransactions iHostelEntryTransactions = HostelEntryTransactionImpl.getInstance();
		HlAdmissionBo hlAdmissionBo=iHostelEntryTransactions.verifyRegisterNumberAndGetNameAjaxCall(actionForm);
		return hlAdmissionBo;
		
	}
	public List<HostelTO> getStudentLeaveDetails(HostelLeaveForm leaveForm) throws Exception
	{
		List<HlLeave> hlLeaveList=leaveTransaction.getStudentLeaveDetails(leaveForm);
		HostelTO hostelTO=null;
		List<HostelTO> hostelTOList=new ArrayList<HostelTO>();
		if(hlLeaveList!=null && !hlLeaveList.isEmpty())
		{
			Iterator iterator=hlLeaveList.iterator();
			while (iterator.hasNext()) {
				HlLeave hlLeave = (HlLeave) iterator.next();
			    hostelTO=new HostelTO();
		      	if(hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null && !hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty())
		      	{
		      	hostelTO.setName(hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln().getPersonalData().getFirstName());
		      	}
		    	if(hlLeave.getHlAdmissionBo().getHostelId().getName()!=null && !hlLeave.getHlAdmissionBo().getHostelId().getName().isEmpty())
		      	{
		      	hostelTO.setHostelName(hlLeave.getHlAdmissionBo().getHostelId().getName());
		      	}
		    	if(hlLeave.getLeaveFrom()!=null)
		      	{
		      	hostelTO.setLeaveFrom(CommonUtil.formatDates(hlLeave.getLeaveFrom()));
		      	}
		    	if(hlLeave.getLeaveTo()!=null)
		      	{
		      	hostelTO.setLeaveTo(CommonUtil.formatDates(hlLeave.getLeaveTo()));
		      	}
		    	if(hlLeave.getHlLeaveType().getName()!=null && !hlLeave.getHlLeaveType().getName().isEmpty())
		      	{
		      	hostelTO.setLeaveType(hlLeave.getHlLeaveType().getName());
		      	}
		    	if(hlLeave.getId()!=0)
		      	{
		      	hostelTO.setId(hlLeave.getId());
		      	}
		    	/*added status property by sudhir*/
		    	if(hlLeave.getIsRejected()!=null || hlLeave.getIsApproved()!=null){
					if(hlLeave.getIsRejected()!=null && hlLeave.getIsRejected()){
						hostelTO.setStatus("Rejected");
					}else if(hlLeave.getIsApproved()!=null && hlLeave.getIsApproved()){
						hostelTO.setStatus("Approved");
					}else{
						hostelTO.setStatus("Pending");
					}
				}else{
					hostelTO.setStatus("Pending");
				}
		    	/*---------------------------*/
		    	hostelTOList.add(hostelTO);
			}
		}
		return hostelTOList;
		
	}
	public void editStudentLeaveDetails(HostelLeaveForm form,HttpSession session) throws Exception
	{
		int id=Integer.parseInt(form.getId());
		HlLeave hlLeave=leaveTransaction.editStudentLeaveDetails(id);
			if(hlLeave.getAcademicYear()!=0 )
	      	{
			form.setAcademicYear1(String.valueOf(hlLeave.getAcademicYear()));
			form.setTempAcademicYear(String.valueOf(hlLeave.getAcademicYear()));
	      	}
			if(hlLeave.getLeaveFrom()!=null )
	      	{
			form.setLeaveFrom(CommonUtil.formatDates(hlLeave.getLeaveFrom()));
	      	}
			if(hlLeave.getLeaveTo()!=null)
	      	{
			form.setLeaveTo(CommonUtil.formatDates(hlLeave.getLeaveTo()));
	      	}
			if(hlLeave.getHlLeaveType().getId()!=0)
	      	{
			form.setLeaveType(String.valueOf(hlLeave.getHlLeaveType().getId()));
	      	}
			if(hlLeave.getFromSession()!=null && !hlLeave.getFromSession().isEmpty())
	      	{
			form.setLeaveFromSession(hlLeave.getFromSession());
	      	}
			if(hlLeave.getToSession()!=null && !hlLeave.getToSession().isEmpty())
	      	{
			form.setLeaveToSession(hlLeave.getToSession());
	      	}
			if(hlLeave.getReasons()!=null && !hlLeave.getReasons().isEmpty())
	      	{
			form.setReasons(hlLeave.getReasons());
	      	}
			if(hlLeave.getRemarks()!=null && !hlLeave.getRemarks().isEmpty())
	      	{
			form.setRemarks(hlLeave.getRemarks());
	      	}
			if(hlLeave.getRequestType()!=null && !hlLeave.getRequestType().isEmpty())
	      	{
			form.setRequestType(hlLeave.getRequestType());
	      	}
			if(hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null  && !hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty())
	      	{
			form.setStudentName(hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln().getPersonalData().getFirstName());
	      	}
			if(hlLeave.getHlAdmissionBo().getStudentId().getRegisterNo()!=null && !hlLeave.getHlAdmissionBo().getStudentId().getRegisterNo().isEmpty())
	      	{
			form.setRegisterNo(hlLeave.getHlAdmissionBo().getStudentId().getRegisterNo());
			session.setAttribute("registerNumber", hlLeave.getHlAdmissionBo().getStudentId().getRegisterNo());
	      	}
			if(hlLeave.getHlAdmissionBo().getHostelId().getName()!=null  && !hlLeave.getHlAdmissionBo().getHostelId().getName().isEmpty())
	      	{
			form.setHostelName(hlLeave.getHlAdmissionBo().getHostelId().getName());
	      	}
			if(hlLeave.getHlAdmissionBo().getRoomId()!=null)
			{
			if(hlLeave.getHlAdmissionBo().getRoomId().getName()!=null  && !hlLeave.getHlAdmissionBo().getRoomId().getName().isEmpty())
	      	{
			form.setRoomNo(hlLeave.getHlAdmissionBo().getRoomId().getName());
	      	}
			if(hlLeave.getHlAdmissionBo().getRoomId().getHlBlock().getName()!=null  && !hlLeave.getHlAdmissionBo().getRoomId().getHlBlock().getName().isEmpty())
	      	{
			form.setStudentBlock(hlLeave.getHlAdmissionBo().getRoomId().getHlBlock().getName());
	      	}
			if(hlLeave.getHlAdmissionBo().getRoomId().getHlUnit().getName()!=null  && !hlLeave.getHlAdmissionBo().getRoomId().getHlUnit().getName().isEmpty())
	      	{
			form.setStudentUnit(hlLeave.getHlAdmissionBo().getRoomId().getHlUnit().getName());
	      	}
			}
			if(hlLeave.getHlAdmissionBo().getBedId()!=null)
			{
			if(hlLeave.getHlAdmissionBo().getBedId().getBedNo()!=null  && !hlLeave.getHlAdmissionBo().getBedId().getBedNo().isEmpty())
	      	{
			form.setBedNo(hlLeave.getHlAdmissionBo().getBedId().getBedNo());
	      	}
			}
			if(hlLeave.getHlAdmissionBo().getId()!=0)
	      	{
			session.setAttribute("hostelLeaveAdmId", hlLeave.getHlAdmissionBo().getId());
	      	}
	}
	public boolean updateReasonForCancelLeave(HostelLeaveForm hostelLeaveForm,String id) throws Exception
	{
		String mode="update";
		HlLeave leaves=leaveTransaction.getHostelLeaveById(id);
		
		leaves.setReasons(hostelLeaveForm.getReasons());
		leaves.setModifiedBy(hostelLeaveForm.getUserId());
		leaves.setLastModifiedDate(new Date());
		leaves.setIsCanceled(Boolean.TRUE);
		leaves.setIsApproved(Boolean.FALSE);
		leaves.setIsRejected(Boolean.FALSE);
		
		/*HlLeaveType type = new HlLeaveType();
		HlLeave hlLeave=new HlLeave();
		Iterator iterator=leaves.iterator();
		while (iterator.hasNext()) {
			HlLeave leave = (HlLeave) iterator.next();
			
		
		hlLeave.setCreatedBy(leave.getCreatedBy());
		hlLeave.setCreatedDate(leave.getCreatedDate());
		HlAdmissionBo hlAdmissionBo=new HlAdmissionBo();
		hlAdmissionBo.setId(leave.getHlAdmissionBo().getId());
		hlLeave.setHlAdmissionBo(hlAdmissionBo);
		type.setId(leave.getHlLeaveType().getId());
		hlLeave.setHlLeaveType(type);
        hlLeave.setLeaveFrom(leave.getLeaveFrom());
        hlLeave.setLeaveTo(leave.getLeaveTo());
        hlLeave.setIsActive(leave.getIsActive());
        hlLeave.setFromSession(leave.getFromSession());
        hlLeave.setToSession(leave.getToSession());
        hlLeave.setRemarks(leave.getRemarks());
        hlLeave.setRequestType(leave.getRequestType());
        hlLeave.setAcademicYear(leave.getAcademicYear());
		hlLeave.setModifiedBy(hostelLeaveForm.getUserId());
		hlLeave.setLastModifiedDate(new Date());
		hlLeave.setIsCanceled(Boolean.TRUE);
		hlLeave.setId(Integer.parseInt(id));
		hlLeave.setReasons(hostelLeaveForm.getReasons());
		}*/
		return leaveTransaction.saveOrUpdateHostelLeaveDetails(leaves,mode);
		
	}
	public HlAdmissionBo getStudentDetailsByFormCall(HostelLeaveForm actionForm) throws Exception
	{
		IHostelEntryTransactions iHostelEntryTransactions = HostelEntryTransactionImpl.getInstance();
		HlAdmissionBo hlAdmissionBo=iHostelEntryTransactions.getStudentDetailsByFormCall(actionForm);
		return hlAdmissionBo;
		
	}

	/**
	 * @param hostelLeaveForm
	 * @param studentId 
	 * @param errors 
	 * @throws Exception
	 */
	public void setStudentHostelDetailsToForm(HostelLeaveForm hostelLeaveForm, String studentId) throws Exception{
		HlUnits bo = leaveTransaction.getStudentHostelDetails(studentId,hostelLeaveForm);
		if(bo != null){
			if(bo.getBlocks() != null && bo.getBlocks().getHlHostel() != null && bo.getBlocks().getHlHostel().getName() != null){
				hostelLeaveForm.setHostelName(bo.getBlocks().getHlHostel().getName());
			}
			if(bo.getBlocks() != null && bo.getBlocks().getName() != null){
				hostelLeaveForm.setBlockName(bo.getBlocks().getName());
			}
			if(bo.getName() != null){
				hostelLeaveForm.setUnitName(bo.getName());
			}
			HostelUnitsTO hostelUnitsTO = new HostelUnitsTO();
			hostelUnitsTO.setId(bo.getId());
			if (bo.getName() != null && !bo.getName().isEmpty()) {
				hostelUnitsTO.setName(bo.getName());
			}
			if (bo.getFloorNo() != null){
				hostelUnitsTO.setNoOfFloors(bo.getFloorNo());
			}
			if (bo.getPrimaryContactDesignation() != null && !bo.getPrimaryContactDesignation().isEmpty()){
				hostelUnitsTO.setPrimaryContactDesignation(bo.getPrimaryContactDesignation());
			}
			if (bo.getPrimaryContactEmail() != null && !bo.getPrimaryContactEmail().isEmpty()){
				hostelUnitsTO.setPrimaryContactEmail(bo.getPrimaryContactEmail());
			}
			if (bo.getPrimaryContactMobile() != null && !bo.getPrimaryContactMobile().isEmpty()){
				hostelUnitsTO.setPrimaryContactMobile(bo.getPrimaryContactMobile());
			}
			if (bo.getPrimaryContactName() != null && !bo.getPrimaryContactName().isEmpty()){
				hostelUnitsTO.setPrimaryContactName(bo.getPrimaryContactName());
			}
			if (bo.getPrimaryContactPhone() != null && !bo.getPrimaryContactPhone().isEmpty()){
				hostelUnitsTO.setPrimaryContactPhone(bo.getPrimaryContactPhone());
			}
			if (bo.getSecondaryContactDesignation() != null && !bo.getSecondaryContactDesignation().isEmpty()){
				hostelUnitsTO.setSecContactDesignation(bo.getSecondaryContactDesignation());
			}
			if (bo.getSecondaryContactEmail() != null && !bo.getSecondaryContactEmail().isEmpty()){
				hostelUnitsTO.setSecContactEmail(bo.getSecondaryContactEmail());
			}
			if (bo.getSecondaryContactMobile() != null && !bo.getSecondaryContactMobile().isEmpty()){
				hostelUnitsTO.setSecContactMobile(bo.getSecondaryContactMobile());
			}
			if (bo.getSecondaryContactName() != null && !bo.getSecondaryContactName().isEmpty()){
				hostelUnitsTO.setSecContactName(bo.getSecondaryContactName());
			}
			if (bo.getSecondaryContactPhone() != null && !bo.getSecondaryContactPhone().isEmpty()){
				hostelUnitsTO.setSecContactPhone(bo.getSecondaryContactPhone());
			}
			HostelTO hostelTO = new HostelTO();
			if(bo.getBlocks()!=null){
				if(bo.getBlocks().getHlHostel().getId()>0){
					hostelTO.setName(bo.getBlocks().getHlHostel().getName());
					hostelTO.setId(bo.getBlocks().getHlHostel().getId());
					hostelUnitsTO.setHostelTO(hostelTO);
				}
			}
			HostelBlocksTO hostelBlocksTO = new HostelBlocksTO();
			if(bo.getBlocks()!=null){
				if(bo.getBlocks().getId()>0){
					hostelBlocksTO.setName(bo.getBlocks().getName());
					hostelBlocksTO.setId(bo.getBlocks().getId());
					hostelUnitsTO.setHostelBlocksTO(hostelBlocksTO);
				}
			}
			if(bo.getOnlineLeave()!=null && bo.getOnlineLeave()){
				hostelUnitsTO.setOnlineLeave("yes");
			}else{
				hostelUnitsTO.setOnlineLeave("no");
			}
			if(bo.getApplyBeforeTime()!=null && !bo.getApplyBeforeTime().isEmpty()){
				hostelUnitsTO.setApplyBeforeHours(bo.getApplyBeforeTime().substring(0,2));
				hostelUnitsTO.setApplyBeforeMin(bo.getApplyBeforeTime().substring(3,5));
			}
			if(bo.getLeaveBeforeNoOfDays()!=null && !bo.getLeaveBeforeNoOfDays().isEmpty()){
				hostelUnitsTO.setLeaveBeforeNoOfDays(bo.getLeaveBeforeNoOfDays());
			}
			if(bo.getIntervalMails()!=null && !bo.getIntervalMails().isEmpty()){
				hostelUnitsTO.setIntervalMails(bo.getIntervalMails());
			}
			if(bo.getApplyBeforeNextDayTime()!=null && !bo.getApplyBeforeNextDayTime().isEmpty()){
				hostelUnitsTO.setApplyBeforeNextDayHours(bo.getApplyBeforeNextDayTime().substring(0,2));
				hostelUnitsTO.setApplyBeforeNextDayMin(bo.getApplyBeforeNextDayTime().substring(3,5));
			}
			if(bo.getSmsForParents()!=null && bo.getSmsForParents()){
				hostelUnitsTO.setSmsForParents("yes");
			}else{
				hostelUnitsTO.setSmsForParents("no");
			}
			if(bo.getSmsForParents()!=null && bo.getSmsForParents()){
				hostelUnitsTO.setSmsForParents("yes");
			}else{
				hostelUnitsTO.setSmsForParents("no");
			}
			if(bo.getSmsForPrimaryCon()!=null && bo.getSmsForPrimaryCon()){
				hostelUnitsTO.setSmsForPrimaryCon("yes");
			}else{
				hostelUnitsTO.setSmsForPrimaryCon("no");
			}
			if(bo.getSmsForSecondCon()!=null && bo.getSmsForSecondCon()){
				hostelUnitsTO.setSmsForSecondCon("yes");
			}else{
				hostelUnitsTO.setSmsForSecondCon("no");
			}
			if(bo.getSmsOnEvening()!=null && bo.getSmsOnEvening()){
				hostelUnitsTO.setSmsOnEvening("yes");
			}else{
				hostelUnitsTO.setSmsOnEvening("no");
			}
			if(bo.getSmsOnMorning()!=null && bo.getSmsOnMorning()){
				hostelUnitsTO.setSmsOnMorning("yes");
			}else{
				hostelUnitsTO.setSmsOnMorning("no");
			}
			hostelLeaveForm.setUnitsTo(hostelUnitsTO);
		}else{
			hostelLeaveForm.setNoRecordFound("NO Records Found");
		}
		
	}

	/**
	 * @param hostelLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveLeave(HostelLeaveForm hostelLeaveForm) throws Exception{
		HlLeave leave = new HlLeave();
		HlLeaveType type = new HlLeaveType();
		
		type.setId(Integer.parseInt(hostelLeaveForm.getLeaveType()));
		leave.setHlLeaveType(type);
		leave.setCreatedBy(hostelLeaveForm.getUserId());
		leave.setCreatedDate(new Date());
		HlAdmissionBo hlAdmissionBo=new HlAdmissionBo();
		if(hostelLeaveForm.getHlAdmId() != null && !hostelLeaveForm.getHlAdmId().isEmpty())
			hlAdmissionBo.setId(Integer.parseInt(hostelLeaveForm.getHlAdmId()));
		leave.setHlAdmissionBo(hlAdmissionBo);
		
		leave.setModifiedBy(hostelLeaveForm.getUserId());
		leave.setLastModifiedDate(new Date());
		leave.setLeaveFrom(CommonUtil.ConvertStringToDate(hostelLeaveForm.getStartDate()));
		leave.setLeaveTo(CommonUtil.ConvertStringToDate(hostelLeaveForm.getEndDate()));
		leave.setReasons(hostelLeaveForm.getReasons());
		leave.setIsApproved(true);
		leave.setIsActive(Boolean.TRUE);
		leave.setIsCanceled(Boolean.FALSE);
		leave.setFromSession(hostelLeaveForm.getLeaveFromSession());
		leave.setToSession(hostelLeaveForm.getLeaveToSession());
		leave.setRequestType("Self Request");
		if(hostelLeaveForm.getAcademicYear() != null && !hostelLeaveForm.getAcademicYear().isEmpty())
			leave.setAcademicYear(Integer.parseInt(hostelLeaveForm.getAcademicYear()));
		
		return leaveTransaction.saveStudentLeave(leave);
	}

	/**
	 * @param hostelLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkForDuplicateLeave(HostelLeaveForm hostelLeaveForm) throws Exception{
		boolean duplicate = false;
		String query = getLeaveQuery(hostelLeaveForm);
		List<HlLeave> list = leaveTransaction.getDataForQuery(query);
		if(list != null && !list.isEmpty()){
			Iterator<HlLeave> iterator = list.iterator();
			while (iterator.hasNext()) {
				HlLeave leave = (HlLeave) iterator.next();
				if(leave.getLeaveTo().compareTo(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getStartDate()))==0){
					/* code modified by sudhir 
					 * Once student is applied for leave from start date to end date , again he can not apply leave from end date with different session.
				   if(leave.getToSession().equalsIgnoreCase(hostelLeaveForm.getLeaveFromSession())){
					duplicate=true;
					break;
				 }else{
					duplicate=false;
				 }*/
					hostelLeaveForm.setErrorMessage("true");
					duplicate=true;
					break;
				}else if(leave.getLeaveFrom().compareTo(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getStartDate()))==0){
					if(leave.getFromSession().equalsIgnoreCase(hostelLeaveForm.getLeaveFromSession())){
						duplicate=true;
						
					 }else{
						duplicate=false;
					 }
				}else if (leave.getLeaveFrom().compareTo(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getEndDate()))==0){
					if(leave.getFromSession().equalsIgnoreCase(hostelLeaveForm.getLeaveToSession())){
						duplicate=true;
						break;
					}
				}else{
					duplicate=true;
				}
				
			}
		}
		return duplicate;
	}

	/**
	 * @param hostelLeaveForm
	 * @return
	 * @throws Exception
	 */
	private String getLeaveQuery(HostelLeaveForm hostelLeaveForm) throws Exception{

		String query="from HlLeave l where l.isCanceled=0" +
				" and l.hlAdmissionBo.id="+hostelLeaveForm.getHlAdmId()+
				" and ((('"+CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getStartDate())+"') between l.leaveFrom and l.leaveTo )" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getEndDate())+"') between l.leaveFrom and l.leaveTo)" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getStartDate())+"') <= l.leaveFrom " +
				" and ('"+CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getEndDate())+"') >= l.leaveTo )) ";
		return query;
	}

	/**
	 * @param studentId
	 * @param hostelLeaveForm 
	 * @return
	 * @throws Exception
	 */
	public List<HostelUnitsTO> getTotalLeaves(String studentId, HostelLeaveForm hostelLeaveForm) throws Exception {
		List<HlLeave> leaves = leaveTransaction.getTotalLeaves(studentId);
		Iterator<HlLeave> iterator = leaves.iterator();
		List<HostelUnitsTO> tos = new ArrayList<HostelUnitsTO>();
		String date="";
		while (iterator.hasNext()) {
			HlLeave hlLeave = (HlLeave) iterator.next();
			HostelUnitsTO to = new HostelUnitsTO();
			to.setHlLeaveId(hlLeave.getId());
			date = "";
			if(hlLeave.getLeaveFrom() != null){
				date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(hlLeave.getLeaveFrom()), HostelLeaveHandler.SQL_DATEFORMAT,	HostelLeaveHandler.FROM_DATEFORMAT);
				to.setStartDate(date + " ("+hlLeave.getFromSession()+")");
			}
			date="";
			if(hlLeave.getLeaveTo() != null){
				date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(hlLeave.getLeaveTo()), HostelLeaveHandler.SQL_DATEFORMAT,	HostelLeaveHandler.FROM_DATEFORMAT);
				to.setEndDate(date + " ("+hlLeave.getToSession()+")");
			}
			if(hlLeave.getIsApproved() != null && hlLeave.getIsApproved()){
				to.setStatus("Approved");
			}else if(hlLeave.getIsCanceled() != null && hlLeave.getIsCanceled()){
				to.setStatus("Canceled");
			}else{
				to.setStatus("Pending");
			}
				
			tos.add(to);
		}
		return tos;
	}

	/**
	 * @param hlLeaveId
	 * @return
	 * @throws Exception
	 */
	public boolean cancelLeave(int hlLeaveId) throws Exception{
		
		return leaveTransaction.cancelLeave(hlLeaveId);
	}

	/**
	 * @param hostelLeaveForm
	 * @return 
	 * @throws Exception
	 */
	public boolean sendSMSTOParent(HostelLeaveForm hostelLeaveForm) throws Exception{
		Properties prop = new Properties();
        InputStream in1 = HostelWaitingListHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop.load(in1);
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.STUDENT_HOSTEL_LEAVE_TEMPLATE);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
			if(hostelLeaveForm.getStudentName() != null)
				desc = desc.replace("[NAME]", hostelLeaveForm.getStudentName());
			else
				desc = desc.replace("[NAME]", "");
			if(hostelLeaveForm.getRegNo() != null)
				desc = desc.replace("[REGISTER_NO]", hostelLeaveForm.getRegNo());
			else
				desc = desc.replace("[REGISTER_NO]", "");
/*         code added by sudhir */
//			 else if condition is added 
//			reason for adding else if is,when admin enters the leave entry ,
//			then fromdate and todate comes from getLeaveFrom and getLeaveTo properties of the formBean
//			if the leave apply is done by student then fromdate and todate comes from getStartDate and getEndDate properties
//			thats why additional else if condition is added here
			if(hostelLeaveForm.getStartDate() != null){
				desc = desc.replace("[FROM DATE]", hostelLeaveForm.getStartDate()+" ("+hostelLeaveForm.getLeaveFromSession()+")");
			}else if(hostelLeaveForm.getLeaveFrom()!=null){
				desc = desc.replace("[FROM DATE]", hostelLeaveForm.getLeaveFrom()+" ("+hostelLeaveForm.getLeaveFromSession()+")");
			}else{
				desc = desc.replace("[FROM DATE]", "");
			}
			if(hostelLeaveForm.getEndDate() != null){
				desc = desc.replace("[TO DATE]", hostelLeaveForm.getEndDate()+" ("+hostelLeaveForm.getLeaveToSession()+")");
			}else if(hostelLeaveForm.getLeaveTo()!=null){
				desc = desc.replace("[TO DATE]", hostelLeaveForm.getLeaveTo()+" ("+hostelLeaveForm.getLeaveToSession()+")");
			}else{
				desc = desc.replace("[TO DATE]", "");
			}
//			added code ends here
		}
		if(StringUtils.isNumeric(hostelLeaveForm.getParentMob()) && (hostelLeaveForm.getParentMob().length()==12 && desc.length()<=160) && desc!=null && !desc.isEmpty()){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(hostelLeaveForm.getParentMob());
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			PropertyUtil.getInstance().save(mob);
		}
		return false;
		
	}

	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public boolean checkStudentDetails(int studentId) throws Exception{
		return leaveTransaction.checkStudentDetails(studentId);
	}

	public List<HostelLeaveApprovalTo> getPreviousList(String regNo, HostelLeaveForm hostelLeaveForm) throws Exception{
		List<HlLeave> hlLeavesList = leaveTransaction.getPreviousList(regNo);
		List<HostelLeaveApprovalTo> list = convertBoToToList(hlLeavesList,hostelLeaveForm);
		return list;
	}

	/**
	 * @param hlLeavesList
	 * @param hostelLeaveForm
	 * @return
	 * @throws Exception
	 */
	private List<HostelLeaveApprovalTo> convertBoToToList( List<HlLeave> hlLeavesList, HostelLeaveForm hostelLeaveForm)throws Exception {
		List<HostelLeaveApprovalTo> list = new ArrayList<HostelLeaveApprovalTo>();
		if(hlLeavesList!=null && !hlLeavesList.isEmpty()){
			Iterator<HlLeave> iterator = hlLeavesList.iterator();
			while (iterator.hasNext()) {
				HlLeave hlLeave = (HlLeave) iterator.next();
				HostelLeaveApprovalTo to = new HostelLeaveApprovalTo();
				if(hlLeave.getLeaveFrom()!=null && hlLeave.getFromSession()!=null){
					to.setDateAndTimeFrom(CommonUtil.formatDates(hlLeave.getLeaveFrom())+" ("+hlLeave.getFromSession()+")");
				}
				if(hlLeave.getLeaveTo()!=null && hlLeave.getToSession()!=null){
					to.setDateAndTimeTo(CommonUtil.formatDates(hlLeave.getLeaveTo())+" ("+hlLeave.getToSession()+")");
				}
				if(hlLeave.getIsRejected()!=null || hlLeave.getIsApproved()!=null){
					if(hlLeave.getIsRejected()!=null && hlLeave.getIsRejected()){
						to.setStatus("Rejected");
					}else if(hlLeave.getIsApproved()!=null && hlLeave.getIsApproved()){
						to.setStatus("Approved");
					}else{
						to.setStatus("Pending");
					}
				}else{
					to.setStatus("Pending");
				}
				if(hlLeave.getHlAdmissionBo()!=null && hlLeave.getHlAdmissionBo().getStudentId()!=null 
						&& hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln()!=null
						&hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln().getPersonalData()!=null 
						&& hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null){
					hostelLeaveForm.setStudentName(hlLeave.getHlAdmissionBo().getStudentId().getAdmAppln().getPersonalData().getFirstName());
				}
				if(hlLeave.getHlAdmissionBo()!=null && hlLeave.getHlAdmissionBo().getStudentId()!=null 
						&& hlLeave.getHlAdmissionBo().getStudentId().getClassSchemewise()!=null
						&& hlLeave.getHlAdmissionBo().getStudentId().getClassSchemewise().getClasses()!=null
						&& hlLeave.getHlAdmissionBo().getStudentId().getClassSchemewise().getClasses().getName()!=null){
					hostelLeaveForm.setClassName( hlLeave.getHlAdmissionBo().getStudentId().getClassSchemewise().getClasses().getName());
				}
				list.add(to);
			}
		}
		return list;
	}

	public void sendSMSAndEmailToStudents(HostelLeaveForm hostelLeaveForm)throws Exception {
			String fromName = CMSConstants.HOSTEL_LEAVE_APPROVAL_MAIL_FROM_NAME;
			String subject = "Leave Application Status";
			String fromAddress=CMSConstants.MAIL_USERID;
			String senderNumber=CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER;
			String senderName=CMSConstants.KNOWLEDGEPRO_SENDER_NAME;
			String templateName = "";
			Properties prop = new Properties();
			InputStream in = JobScheduler.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			try {
				prop.load(in);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			String sendSms= prop.getProperty("knowledgepro.sms.send");
				 templateName = CMSConstants.HOSTEL_LEAVE_APPROVAL;
					if(hostelLeaveForm.getStudentmail()!=null && !hostelLeaveForm.getStudentmail().isEmpty()){
							sendMailToStudent(hostelLeaveForm,fromName,fromAddress,subject,templateName);
					}
					if(hostelLeaveForm.getStudentMobile()!=null &&!hostelLeaveForm.getStudentMobile().isEmpty()){
							sendSmsToStudent(hostelLeaveForm,senderNumber,senderName,templateName,sendSms);
					}
	}
	/**
	 * @param to
	 * @param templateName 
	 * @param subject 
	 * @param fromAddress 
	 * @param fromName 
	 * @return
	 * @throws Exception
	 */
	private boolean sendMailToStudent (HostelLeaveForm hostelLeaveForm, String fromName, String fromAddress, String subject, String templateName) throws Exception{
		boolean flag=false;
		String desc="";
		List<GroupTemplate> list=null;
		//get template and replace dynamic data
		TemplateHandler temphandle=TemplateHandler.getInstance();
		 	list= temphandle.getDuplicateCheckList(templateName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
			desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO,hostelLeaveForm.getRegNo());
			desc=desc.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME,hostelLeaveForm.getStudentName());
			/*         code added by sudhir */
//			 else if condition is added 
//			reason for adding else if is,when admin enters the leave entry ,
//			then fromdate and todate comes from getLeaveFrom and getLeaveTo properties of the formBean
//			if the leave apply is done by student then fromdate and todate comes from getStartDate and getEndDate properties
//			thats why additional else if condition is added here
			if(hostelLeaveForm.getStartDate()!=null && hostelLeaveForm.getEndDate()!=null){
				desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_FROM_DATE,hostelLeaveForm.getStartDate()+" ("+hostelLeaveForm.getLeaveFromSession()+")");
				desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_TO_DATE,hostelLeaveForm.getEndDate()+" ("+hostelLeaveForm.getLeaveToSession()+")");
			}else if(hostelLeaveForm.getLeaveFrom()!=null && hostelLeaveForm.getLeaveTo()!=null){
				desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_FROM_DATE,hostelLeaveForm.getLeaveFrom()+" ("+hostelLeaveForm.getLeaveFromSession()+")");
				desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_TO_DATE,hostelLeaveForm.getLeaveTo()+" ("+hostelLeaveForm.getLeaveToSession()+")");
			}
//			added code ends here
			flag=sendMail(hostelLeaveForm.getStudentmail(),fromName,fromAddress,subject,desc);
		}
		return flag;
	}
	/**
	 * @param emailId
	 * @param fromName
	 * @param fromAddress
	 * @param subject
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	private boolean sendMail(String mailID, String fromName, String fromAddress, String sub, String desc) throws Exception{
		boolean sent=false;
		String toAddress=mailID;
		// MAIL TO CONSTRUCTION
		String subject=sub;
		String msg=desc;
		MailTO mailto=new MailTO();
		mailto.setFromAddress(fromAddress);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg);
		mailto.setFromName(fromName);
		sent=CommonUtil.sendMail(mailto);
	return sent;
	}
	/**
	 * @param to
	 * @param templateName 
	 * @param senderName 
	 * @param senderNumber 
	 * @param sendSms 
	 * @return
	 * @throws Exception
	 */
	private boolean sendSmsToStudent(HostelLeaveForm hostelLeaveForm, String senderNumber, String senderName, String templateName, String sendSms) throws Exception{
		boolean isSmsSent = false;
		String desc="";
		String mobileNo = "";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templateName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
			desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO,hostelLeaveForm.getRegNo());
			desc=desc.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME,hostelLeaveForm.getStudentName());
			if(hostelLeaveForm.getStartDate()!=null && hostelLeaveForm.getEndDate()!=null){
				desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_FROM_DATE,hostelLeaveForm.getStartDate()+" ("+hostelLeaveForm.getLeaveFromSession()+")");
				desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_TO_DATE,hostelLeaveForm.getEndDate()+" ("+hostelLeaveForm.getLeaveToSession()+")");
			}else if(hostelLeaveForm.getLeaveFrom()!=null && hostelLeaveForm.getLeaveTo()!=null){
				desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_FROM_DATE,hostelLeaveForm.getLeaveFrom()+" ("+hostelLeaveForm.getLeaveFromSession()+")");
				desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_LEAVE_APPROVAL_TO_DATE,hostelLeaveForm.getLeaveTo()+" ("+hostelLeaveForm.getLeaveToSession()+")");
			}
			
			if(hostelLeaveForm.getStudentMobile()!=null && !hostelLeaveForm.getStudentMobile().isEmpty()){
				mobileNo = hostelLeaveForm.getStudentMobile();
			}
		}
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160) && desc!=null && !desc.isEmpty()){
			if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
				MobileMessaging mob=new MobileMessaging();
				mob.setDestinationNumber(mobileNo);
				mob.setMessageBody(desc);
				mob.setMessagePriority(3);
				mob.setSenderName(senderName);
				mob.setSenderNumber(senderNumber);
				mob.setMessageEnqueueDate(new Date());
				mob.setIsMessageSent(false);
				isSmsSent=PropertyUtil.getInstance().save(mob);
			}
		}
		return isSmsSent;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<String> getHolidays() throws Exception{
		IHolidayDetailsTransaction transaction=new HolidayDetailsTxnImpl();
		List<Holidays> list = transaction.getHolidaysList();
		List<String> holidaysList = convertHolidaysBoTOList(list);
		return holidaysList;
	}

	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<String> convertHolidaysBoTOList(List<Holidays> list) throws Exception{
		List<String> holidaysList = new ArrayList<String>();
		if(list!=null && !list.isEmpty()){
			Iterator<Holidays> iterator = list.iterator();
			while (iterator.hasNext()) {
				Holidays holidays = (Holidays) iterator.next();
				String date = CommonUtil.formatDates(holidays.getStartDate());
				holidaysList.add(date);
			}
		}
		return holidaysList;
	}

	public boolean getHolidays(String holiday)throws Exception {
		boolean isHoliday = leaveTransaction.checkIsHoliday(holiday);
		return isHoliday;
	}
}