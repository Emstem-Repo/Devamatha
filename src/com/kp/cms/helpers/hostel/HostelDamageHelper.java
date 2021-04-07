package com.kp.cms.helpers.hostel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.forms.hostel.HostelDamageForm;
import com.kp.cms.to.hostel.HlDamageTO;
import com.kp.cms.to.hostel.HostelDamageTO;
import com.kp.cms.transactions.hostel.IHostelDamageTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelDamageTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelDamageHelper 
{
	/**
	 * Singleton object of HostelDamageHelper
	 */
	private static volatile HostelDamageHelper hostelDamageHelper = null;
	private static final Log log = LogFactory.getLog(HostelDamageHelper.class);
	private HostelDamageHelper() {
		
	}
	/**
	 * return singleton object of HostelDamageHelper.
	 * @return
	 */
	public static HostelDamageHelper getInstance() {
		if (hostelDamageHelper == null) {
			hostelDamageHelper = new HostelDamageHelper();
		}
		return hostelDamageHelper;
	}
	
	public String getSearchQuery(HostelDamageForm hostelDamageForm) throws Exception{
		String searchQuery="from HlApplicationForm h " +
				" where h.isActive=1 and h.hlStatus.id=2 and h.hlHostelByHlAppliedHostelId.id="+hostelDamageForm.getHostelId();
			if(hostelDamageForm.getApplicationNo()!=null && !hostelDamageForm.getApplicationNo().isEmpty()){
				searchQuery=searchQuery+" and h.admAppln.applnNo= '"+hostelDamageForm.getApplicationNo()+"'"+" and h.admAppln.appliedYear= '"+hostelDamageForm.getAcademicYear()+"'";
			}
			if(hostelDamageForm.getRegisterNo()!=null && !hostelDamageForm.getRegisterNo().isEmpty()){
				searchQuery="select h from HlApplicationForm h join h.admAppln.students s where h.hlHostelByHlAppliedHostelId.id="
					 +hostelDamageForm.getHostelId()+" and h.hlStatus.id=2 and s.registerNo= '"+hostelDamageForm.getRegisterNo()+"'"+" and h.admAppln.appliedYear= '"+hostelDamageForm.getAcademicYear()+"'";
			}
			if(hostelDamageForm.getRollNo()!=null && !hostelDamageForm.getRollNo().isEmpty()){
				searchQuery="select h from HlApplicationForm h join h.admAppln.students s where h.hlHostelByHlAppliedHostelId.id="
				    +hostelDamageForm.getHostelId()+" and h.hlStatus.id=2 and s.rollNo= '"+hostelDamageForm.getRollNo()+"'"+" and h.admAppln.appliedYear= '"+hostelDamageForm.getAcademicYear()+"'";
			}
			if((hostelDamageForm.getApplicationNo()!=null && !hostelDamageForm.getApplicationNo().isEmpty())&&
					(hostelDamageForm.getRegisterNo()!=null && !hostelDamageForm.getRegisterNo().isEmpty())){
				searchQuery="select h from HlApplicationForm h join h.admAppln.students s where h.hlHostelByHlAppliedHostelId.id="
					 +hostelDamageForm.getHostelId()+" and h.hlStatus.id=2 and s.registerNo= '"+hostelDamageForm.getRegisterNo()+"'"+
					 " and h.admAppln.applnNo= '"+hostelDamageForm.getApplicationNo()+"'"+" and h.admAppln.appliedYear= '"+hostelDamageForm.getAcademicYear()+"'";
			}
			if((hostelDamageForm.getApplicationNo()!=null && !hostelDamageForm.getApplicationNo().isEmpty())&&
					(hostelDamageForm.getRegisterNo()!=null && !hostelDamageForm.getRegisterNo().isEmpty())&&
					 (hostelDamageForm.getRollNo()!=null && !hostelDamageForm.getRollNo().isEmpty())){
				searchQuery="select h from HlApplicationForm h join h.admAppln.students s where h.hlHostelByHlAppliedHostelId.id="
					 +hostelDamageForm.getHostelId()+" and h.hlStatus.id=2 and s.registerNo= '"+hostelDamageForm.getRegisterNo()+"'"+
					 " and s.rollNo= '"+hostelDamageForm.getRollNo()+"'"+" and h.admAppln.applnNo= '"+hostelDamageForm.getApplicationNo()+"'"+" and h.admAppln.appliedYear= '"+hostelDamageForm.getAcademicYear()+"'";	
			}
			if(hostelDamageForm.getStaffId()!=null && !hostelDamageForm.getStaffId().isEmpty()){
				searchQuery=searchQuery+" and h.employee.code='"+hostelDamageForm.getStaffId()+"'";
			}
		return searchQuery;
	}
	IHostelDamageTransaction transaction=new HostelDamageTransactionImpl();
	/**
	 *converting the Bo to To
	 */
	public HostelDamageTO convertBOtoTO(HlApplicationForm hlApplicationForm,HostelDamageForm hostelDamageForm) throws Exception{
		HostelDamageTO hostelDamageTO=new HostelDamageTO();
		String studentName="";
		String staffName="";
		int hlAppId=hlApplicationForm.getId();
		int hostelId=0;
		hostelDamageTO.setId(hlAppId);
		if(hlApplicationForm.getHlHostelByHlAppliedHostelId().getId()>0){
			hostelDamageTO.setHostelName(hlApplicationForm.getHlHostelByHlAppliedHostelId().getName());
		}
		if(hlApplicationForm.getHlHostelByHlAppliedHostelId().getName()!=null){
			hostelId=hlApplicationForm.getHlHostelByHlAppliedHostelId().getId();
			hostelDamageTO.setHostelId(hostelId);
		}
		AdmAppln admAppln=hlApplicationForm.getAdmAppln();
		if(admAppln!=null){
			PersonalData personalData=admAppln.getPersonalData();
			if(personalData!=null)
			{
				if(personalData.getMiddleName() == null && personalData.getLastName() ==null){  
					studentName=personalData.getFirstName();                       // for getting studentName
				}else if(personalData.getLastName() ==null){
					studentName=personalData.getFirstName() +" "+ personalData.getMiddleName();
				}else if(personalData.getMiddleName() ==null){
					studentName=personalData.getFirstName() +" "+ personalData.getLastName();
				}
				else
				{
					studentName=personalData.getFirstName() +" "+ personalData.getMiddleName()+" "+personalData.getLastName();
				}
			}
		}
		if(hlApplicationForm.getEmployee()!=null)
		{
			/*if(hlApplicationForm.getEmployee().getMiddleName() == null && hlApplicationForm.getEmployee().getLastName() ==null){  
				staffName=hlApplicationForm.getEmployee().getFirstName();                       // for getting staffName
			}else if(hlApplicationForm.getEmployee().getLastName() ==null){
				staffName=hlApplicationForm.getEmployee().getFirstName() +" "+ hlApplicationForm.getEmployee().getMiddleName();
			}else if(hlApplicationForm.getEmployee().getMiddleName() ==null){
				staffName=hlApplicationForm.getEmployee().getFirstName() +" "+ hlApplicationForm.getEmployee().getLastName();
			}
			else
			{
				staffName=hlApplicationForm.getEmployee().getFirstName() +" "+ hlApplicationForm.getEmployee().getMiddleName()+" "+hlApplicationForm.getEmployee().getLastName();
			}*/
		}
		if((hostelDamageForm.getApplicationNo()!=null && !hostelDamageForm.getApplicationNo().isEmpty())|| 
				(hostelDamageForm.getRegisterNo()!=null && !hostelDamageForm.getRegisterNo().isEmpty())||
				 hostelDamageForm.getRollNo()!=null && !hostelDamageForm.getRollNo().isEmpty()){
			hostelDamageTO.setStudentOrStaffDisplay(studentName);	// for setting student name
		}
		else if(hostelDamageForm.getStaffId()!=null && !hostelDamageForm.getStaffId().isEmpty()){
			if(hlApplicationForm.getEmployee()!=null){
			hostelDamageTO.setStudentOrStaffDisplay(staffName); // for setting employee/staff name
		  }
		}	
		List<HlDamage> damageList=transaction.getHostelDamagesByHlappId(hlAppId,hostelId);
		List<HlDamageTO> hlDamageTOList=convertHlDamageBOListToTOlist(damageList,hostelDamageForm);
		hostelDamageTO.setHlDamageTOList(hlDamageTOList);
		return hostelDamageTO;
	} 
	
	public List<HlDamageTO> convertHlDamageBOListToTOlist(List<HlDamage> hlDamages,HostelDamageForm hostelDamageForm)
	{
		List<HlDamageTO> hlDamageTOList=new ArrayList<HlDamageTO>();
	    Iterator<HlDamage> iterator=hlDamages.iterator();
		while (iterator.hasNext()) 
		{
			HlDamage hlDamage = (HlDamage) iterator.next();
			HlDamageTO hlDamageTO=new HlDamageTO();
			hlDamageTO.setId(hlDamage.getId());
			String applicationNo=hostelDamageForm.getApplicationNo().trim();
			String registerNo=hostelDamageForm.getRegisterNo().trim();
			String staffId=hostelDamageForm.getStaffId().trim();
			String rollNo=hostelDamageForm.getRollNo().trim();
			if(!applicationNo.isEmpty()
				&& registerNo !=null && !registerNo.isEmpty()&& rollNo!=null && !rollNo.isEmpty()){
			   hlDamageTO.setApplnNoRegisterStaffIdRollNo(applicationNo+"/"+registerNo+"/"+rollNo);
			}
			else if(!applicationNo.isEmpty()
					&& registerNo !=null && !registerNo.isEmpty()){
				   hlDamageTO.setApplnNoRegisterStaffIdRollNo(applicationNo+"/"+registerNo);
				}
			else if(!applicationNo.isEmpty()
					&& rollNo !=null && !rollNo.isEmpty()){
				   hlDamageTO.setApplnNoRegisterStaffIdRollNo(applicationNo+"/"+rollNo);
				}
			else if(!registerNo.isEmpty()
					&& rollNo !=null && !rollNo.isEmpty()){
				   hlDamageTO.setApplnNoRegisterStaffIdRollNo(registerNo+"/"+rollNo);
				}
			else if(!applicationNo.isEmpty()){
				hlDamageTO.setApplnNoRegisterStaffIdRollNo(applicationNo);
			}
			else if(!registerNo.isEmpty()){
				hlDamageTO.setApplnNoRegisterStaffIdRollNo(registerNo);	
			}
			else if(!staffId.isEmpty()){
				hlDamageTO.setApplnNoRegisterStaffIdRollNo(staffId);
			}
			else if(!rollNo.isEmpty()){
				hlDamageTO.setApplnNoRegisterStaffIdRollNo(rollNo);
			}
			String date="";
			String time="";
			if(hlDamage.getDate()!=null){
				date=CommonUtil.getStringDate(hlDamage.getDate());
			}
			if(hlDamage.getTime()!=null){
				time=hlDamage.getTime().substring(0, 5);
			}
			String dateAndTimeDisplay=date+" "+time;
            hlDamageTO.setDateAndTimeDisplay(dateAndTimeDisplay);
            
			/*if(hlDamage.getDate()!=null){
				hlDamageTO.setDate(CommonUtil.getStringDate(hlDamage.getDate()));
			}
			if(hlDamage.getTime()!=null)
			{
				hlDamageTO.setTime(hlDamage.getTime().substring(0, 5));
			}*/
			if(hlDamage.getAmount()!=null){
			hlDamageTO.setAmount(hlDamage.getAmount());
			}	
			hlDamageTO.setDescription(hlDamage.getDescription());
			if(hlDamage.getBillNo()!=null){
				hlDamageTO.setBillNo(String.valueOf(hlDamage.getBillNo()));
			}
			if(hlDamage.getIsPaid()!=null && hlDamage.getIsPaid()){
				hlDamageTO.setStatus("Paid");
			}else{
				hlDamageTO.setStatus("Not Paid");
			}
			hlDamageTOList.add(hlDamageTO);
		}
		return hlDamageTOList;	
	}
	public HlDamage getHostelDamage(HostelDamageForm hostelDamageForm)
	{
		HostelDamageTO hto=hostelDamageForm.getHostelDamageTO();
		HlApplicationForm hlAppForm=new HlApplicationForm();
		hlAppForm.setId(hto.getId());
		HlHostel hlhostel=new HlHostel();
		hlhostel.setId(hto.getHostelId());
		HlDamage hlDamage = new HlDamage();
		if(hostelDamageForm.getDate()!=null){
		hlDamage.setDate(CommonUtil.ConvertStringToSQLDate(hostelDamageForm
				.getDate()));
		}
		/*String time=hostelDamageForm.getTimeHours()+":"+hostelDamageForm.getTimeMins();*/
		
		String timeHour;
		String timeMin;
		if(hostelDamageForm.getTimeHours() != null && !hostelDamageForm.getTimeHours().isEmpty()){
			timeHour = hostelDamageForm.getTimeHours();
		}
		else{
			timeHour = "00";
		}
		if(hostelDamageForm.getTimeMins() != null && !hostelDamageForm.getTimeMins().isEmpty()){
			timeMin = hostelDamageForm.getTimeMins();
		}
		else{
			timeMin = "00";
		}			
		hlDamage.setTime(CommonUtil.getTime(timeHour,timeMin));
		if(hostelDamageForm.getAmount().trim()!=null && !hostelDamageForm.getAmount().trim().isEmpty()){
		hlDamage.setAmount(new BigDecimal(hostelDamageForm.getAmount()));
		}
		if(hostelDamageForm.getDescription()!=null){
			hlDamage.setDescription(hostelDamageForm.getDescription());
		}
		hlDamage.setCreatedDate(new Date());
		hlDamage.setIsActive(Boolean.TRUE);
		//hlDamage.setStaffId(hostelDamageForm.getStaffId());
		if(hostelDamageForm.getStaffId().trim()!=null && !hostelDamageForm.getStaffId().trim().isEmpty()){
		Employee emp=new Employee();
		emp.setId(Integer.parseInt(hostelDamageForm.getStaffId()));
		hlDamage.setEmployee(emp);
		}
		hlDamage.setCreatedBy(hostelDamageForm.getUserId());
		hlDamage.setModifiedBy(hostelDamageForm.getUserId());
		hlDamage.setLastModifiedDate(new Date());
		hlDamage.setHlHostel(hlhostel);
		hlDamage.setHlApplicationForm(hlAppForm);
		hlDamage.setIsPaid(false);
		return hlDamage;
	}
}
