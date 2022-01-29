package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelApplicationByAdminForm;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.hostel.HlApplicationFeeTO;
import com.kp.cms.to.hostel.HostelApplicationByAdminTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.to.hostel.RoomTypeWithAmountTO;
import com.kp.cms.transactions.hostel.IHostelApplicationByAdminTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelApplicationByAdminTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelApplicationByAdminHelper {
	
	private static volatile HostelApplicationByAdminHelper hostelApplicationByAdminHelper = null;
	private static final Log log = LogFactory.getLog(HostelApplicationByAdminHelper.class);
	private HostelApplicationByAdminHelper() {
		
	}
	/**
	 * return singleton object of HostelDamageHelper.
	 * @return
	 */
	public static HostelApplicationByAdminHelper getInstance() {
		if (hostelApplicationByAdminHelper == null) {
			hostelApplicationByAdminHelper = new HostelApplicationByAdminHelper();
		}
		return hostelApplicationByAdminHelper;
	}
	
	public String getSearchQuery(HostelApplicationByAdminForm hostelApplicationByAdminForm) throws Exception{
		String searchQuery="";
	
			if(hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty()){
				searchQuery="select admAppln from AdmAppln admAppln where admAppln.applnNo="+hostelApplicationByAdminForm.getApplicationNo()+" and admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty()){
				searchQuery="select admAppln from AdmAppln admAppln join admAppln.students s where s.registerNo= '"+hostelApplicationByAdminForm.getRegisterNo()+"' and admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if(hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty()){
				searchQuery=searchQuery+"select admAppln from AdmAppln admAppln join admAppln.students s where s.rollNo= '"+hostelApplicationByAdminForm.getRollNo()+"' and admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
		
			}
			if((hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty())&&
					(hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty())){
				searchQuery="select admAppln from AdmAppln admAppln join admAppln.students s where s.rollNo= '"+hostelApplicationByAdminForm.getRollNo()+"'"
					 +" and admAppln.applnNo="+hostelApplicationByAdminForm.getApplicationNo()+" and admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if((hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty())&&
					(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty())){
				searchQuery="select admAppln from AdmAppln admAppln join admAppln.students s where s.registerNo= '"+hostelApplicationByAdminForm.getRegisterNo()+"'"
					 +" and admAppln.applnNo="+hostelApplicationByAdminForm.getApplicationNo()+" and admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if((hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty())&&
					 (hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty())){
				searchQuery="select admAppln from AdmAppln admAppln join admAppln.students s where s.registerNo= '"+hostelApplicationByAdminForm.getRegisterNo()+"'"
					 +" and s.rollNo= '"+hostelApplicationByAdminForm.getRollNo()+"' and admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if((hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty())&&
					(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty())&&
					 (hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty())){
				searchQuery="select admAppln from AdmAppln admAppln join admAppln.students s where s.registerNo= '"+hostelApplicationByAdminForm.getRegisterNo()+"'"
					 +" and s.rollNo= '"+hostelApplicationByAdminForm.getRollNo()+"'"+" and admAppln.applnNo="+hostelApplicationByAdminForm.getApplicationNo()+" and admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			
		return searchQuery;
	}
	
	/*if(hostelApplicationByAdminForm.getStaffId()!=null && !hostelApplicationByAdminForm.getStaffId().isEmpty()){
		searchQuery="select e from Employee e where e.id="+hostelApplicationByAdminForm.getStaffId();
	}*/
	
	public String getSearchQueryForHlAppln(HostelApplicationByAdminForm hostelApplicationByAdminForm) throws Exception{
		String searchQuery="from HlApplicationForm h " +
				" where h.isActive=1 and h.hlHostelByHlAppliedHostelId.id="+hostelApplicationByAdminForm.getHostelId();
			if(hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty()){
				searchQuery=searchQuery+" and h.admAppln.applnNo="+hostelApplicationByAdminForm.getApplicationNo()+" and h.admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty()){
				searchQuery="select h from HlApplicationForm h join h.admAppln.students s where h.hlHostelByHlAppliedHostelId.id="
					 +hostelApplicationByAdminForm.getHostelId()+" and s.registerNo= '"+hostelApplicationByAdminForm.getRegisterNo()+"' and h.admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if(hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty()){
				searchQuery="select h from HlApplicationForm h join h.admAppln.students s where h.hlHostelByHlAppliedHostelId.id="
				    +hostelApplicationByAdminForm.getHostelId()+" and s.rollNo= '"+hostelApplicationByAdminForm.getRollNo()+"' and h.admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if((hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty())&&
					(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty())){
				searchQuery="select h from HlApplicationForm h join h.admAppln.students s where h.hlHostelByHlAppliedHostelId.id="
					 +hostelApplicationByAdminForm.getHostelId()+" and s.registerNo= '"+hostelApplicationByAdminForm.getRegisterNo()+"'"+
					 " and h.admAppln.applnNo="+hostelApplicationByAdminForm.getApplicationNo()+" and h.admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if((hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty())&&
					(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty())&&
					 (hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty())){
				searchQuery="select h from HlApplicationForm h join h.admAppln.students s where h.hlHostelByHlAppliedHostelId.id="
					 +hostelApplicationByAdminForm.getHostelId()+" and s.registerNo= '"+hostelApplicationByAdminForm.getRegisterNo()+"'"+
					 " and s.rollNo= '"+hostelApplicationByAdminForm.getRollNo()+"'"+" and h.admAppln.applnNo="+hostelApplicationByAdminForm.getApplicationNo()+" and h.admAppln.appliedYear="+hostelApplicationByAdminForm.getAcademicYr();
			}
			if(hostelApplicationByAdminForm.getStaffId()!=null && !hostelApplicationByAdminForm.getStaffId().isEmpty()){
				searchQuery=searchQuery+" and h.employee.id="+hostelApplicationByAdminForm.getStaffId();
			}
		return searchQuery;
	}
	//IHostelApplicationByAdminTransactions transaction=new HostelApplicationByAdminTransactionImpl();

	public HostelApplicationByAdminTO convertBOtoTOForStudent(AdmAppln admAppln,HostelApplicationByAdminForm hostelApplicationByAdminForm) throws Exception{
		HostelApplicationByAdminTO hostelApplicationByAdminTO=new HostelApplicationByAdminTO();
		String studentName="";
		int admApplnId=admAppln.getId();
		int hostelId=0;
		hostelApplicationByAdminTO.setId(admApplnId);
		PersonalData personalData=null;
		if(admAppln!=null){
			personalData=admAppln.getPersonalData();
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
		
		if((hostelApplicationByAdminForm.getApplicationNo()!=null && !hostelApplicationByAdminForm.getApplicationNo().isEmpty())|| 
				(hostelApplicationByAdminForm.getRegisterNo()!=null && !hostelApplicationByAdminForm.getRegisterNo().isEmpty())||
				 hostelApplicationByAdminForm.getRollNo()!=null && !hostelApplicationByAdminForm.getRollNo().isEmpty()){
			    hostelApplicationByAdminTO.setStudentOrStaffName(studentName);	// for setting student name
			    if(personalData!=null){
			    	if(personalData.getPermanentAddressLine1()!=null){
			    		hostelApplicationByAdminTO.setStudentOrStaffPermanentAddressLine(personalData.getPermanentAddressLine1());
			    	}
			    	if(personalData.getPermanentAddressLine1().isEmpty()||personalData.getPermanentAddressLine1()==null)
			    	{
			    		if(personalData.getPermanentAddressLine2()!=null){
			    			hostelApplicationByAdminTO.setStudentOrStaffPermanentAddressLine(personalData.getPermanentAddressLine2());	
			    		}
			    	}
			    	if(personalData.getCurrentAddressLine1()!=null){
			    		
			    		hostelApplicationByAdminTO.setStudentOrStaffCurrentAddressLine(personalData.getCurrentAddressLine1());
			    	}
			    	if(personalData.getCurrentAddressLine1().isEmpty()||personalData.getCurrentAddressLine1()==null)
			    	{
			    		if(personalData.getCurrentAddressLine2()!=null){
			    			hostelApplicationByAdminTO.setStudentOrStaffCurrentAddressLine(personalData.getCurrentAddressLine2());
			    	    }
			    	}
			    }
		   }	
		return hostelApplicationByAdminTO;
	 } 
	
	public HostelApplicationByAdminTO convertBOtoTOForStaff(Employee employee,HostelApplicationByAdminForm hostelApplicationByAdminForm) throws Exception
	{	
		HostelApplicationByAdminTO hostelApplicationByAdminTO=new HostelApplicationByAdminTO();
		String staffName="";
		if(employee!=null)
		{
			/*if(employee.getMiddleName() == null && employee.getLastName() ==null){  
				staffName=employee.getFirstName();                       // for getting staffName
			}else if(employee.getLastName() ==null){
				staffName=employee.getFirstName() +" "+ employee.getMiddleName();*/
			/*}else if(employee.getMiddleName() ==null){
				staffName=employee.getFirstName() +" "+ employee.getLastName();
			}
			else
			{*/
				staffName=employee.getFirstName();/* +" "+ employee.getMiddleName()+" "+employee.getLastName();*/
			//}

			hostelApplicationByAdminTO.setStudentOrStaffName(staffName); // for setting employee/staff name
			
		    	if(employee.getPermanentAddressLine1()!=null){
		    		hostelApplicationByAdminTO.setStudentOrStaffCurrentAddressLine(employee.getPermanentAddressLine1());
		    	}
	    		if(employee.getPermanentAddressLine2()!=null){
	    			hostelApplicationByAdminTO.setStudentOrStaffPermanentAddressLine(employee.getPermanentAddressLine2());	
	    		}
		    	if(employee.getCommunicationAddressLine1()!=null){
		    		hostelApplicationByAdminTO.setStudentOrStaffCurrentAddressLine(employee.getCommunicationAddressLine1());
		    	}
	    		if(employee.getCommunicationAddressLine2()!=null){
	    			hostelApplicationByAdminTO.setStudentOrStaffCurrentAddressLine(employee.getCommunicationAddressLine2());
	    	    }
		 
			}
			return hostelApplicationByAdminTO;
	    }
	
	public HlApplicationForm getHlApplicationForm(HostelApplicationByAdminForm hostelApplicationByAdminForm,AdmAppln admAppln,Employee employee)
	throws Exception{
	 	
		HlApplicationForm hlApplicationForm=new HlApplicationForm();
		if(admAppln!=null){
			hlApplicationForm.setAdmAppln(admAppln);
			hlApplicationForm.setIsStaff(false);
		}
		if(employee!=null){
			hlApplicationForm.setEmployee(employee);
			hlApplicationForm.setIsStaff(true);
		}
		hlApplicationForm.setIsEnteredByAdmin(true);
		int hostelId=Integer.parseInt(hostelApplicationByAdminForm.getHostelId());
		HlHostel hostel=new HlHostel();
		hostel.setId(hostelId);
		hlApplicationForm.setHlHostelByHlAppliedHostelId(hostel);
		hlApplicationForm.setHlHostelByHlApprovedHostelId(hostel);
		IHostelApplicationByAdminTransactions transaction = new HostelApplicationByAdminTransactionImpl();
		Integer maxRequisitionNo=transaction.getMaxRequisitionNo();
		int newRequisitionNo=0;
		if(maxRequisitionNo!=null){
		  newRequisitionNo=maxRequisitionNo+1;  
		}
		else
		{   maxRequisitionNo=0;
			newRequisitionNo=maxRequisitionNo+1;
		}
		hlApplicationForm.setRequisitionNo(newRequisitionNo);   // setting requisitionNo to the table
		hostelApplicationByAdminForm.setMaxRequisitionNo(String.valueOf(newRequisitionNo)); // setting requisitionNo to form
		if(!hostelApplicationByAdminForm.getRoomTypeSelect().isEmpty()){
			int selectedRoomTypeId=Integer.parseInt(hostelApplicationByAdminForm.getRoomTypeSelect());
			HlRoomType roomType=new HlRoomType();
			roomType.setId(selectedRoomTypeId);
			hlApplicationForm.setHlRoomTypeByHlAppliedRoomTypeId(roomType);
			hlApplicationForm.setHlRoomTypeByHlApprovedRoomTypeId(roomType);
		}
		HlStatus hlStatus = new HlStatus();
		int statusId = CMSConstants.HOSTEL_STATUS_APPLIED_ID;
		hlStatus.setId(statusId);
		hlApplicationForm.setHlStatus(hlStatus);
		hlApplicationForm.setAppliedDate(new Date());
		hlApplicationForm.setCreatedDate(new Date());
		hlApplicationForm.setLastModifiedDate(new Date());
		hlApplicationForm.setCreatedBy(hostelApplicationByAdminForm.getUserId());
		hlApplicationForm.setModifiedBy(hostelApplicationByAdminForm.getUserId());
		hlApplicationForm.setIsActive(true);
		
		return hlApplicationForm;
	   	
	}
	
	public List<RoomTypeTO> copyRoomTypeBOToTOs(List<HlRoomType>roomTypeNameList)throws Exception{
		log.info("Entering into copyRoomTypeBOToTOs of HostelApplicationByAdminHelper");
		List<RoomTypeTO> nameList = new ArrayList<RoomTypeTO>();
		RoomTypeTO roomTypeTO = null;
		HostelTO hostelTO = null;
		if(roomTypeNameList!=null && !roomTypeNameList.isEmpty()){
			Iterator<HlRoomType> iterator = roomTypeNameList.iterator();
			while (iterator.hasNext()) {
				HlRoomType roomType = (HlRoomType)iterator.next();
				roomTypeTO = new RoomTypeTO();
				roomTypeTO.setId(String.valueOf(roomType.getId()));
					if(roomType.getName()!=null && !roomType.getName().isEmpty()){
					roomTypeTO.setName(roomType.getName());
					}
					hostelTO = new HostelTO();
					if(roomType.getHlHostel()!=null){
						hostelTO.setId(roomType.getHlHostel().getId());
					}
				roomTypeTO.setHostelTO(hostelTO);
				nameList.add(roomTypeTO);
			}
		}
		log.info("Leaving into copyRoomTypeBOToTOs of HostelApplicationByAdminHelper");
		return nameList;
	}
	
	/*public List<RoomTypeWithAmountTO> copyBosToTosForRoomTypeFees(List<HlRoomType> roomTypes){
		
		List<RoomTypeWithAmountTO> roomTypeWithAmountTOList=new ArrayList<RoomTypeWithAmountTO>();
		List<HlApplicationFeeTO> hlApplicationFeeToList=new ArrayList<HlApplicationFeeTO>();
		for(HlRoomType roomType:roomTypes){
			RoomTypeWithAmountTO roomTypeWithAmountTO=new RoomTypeWithAmountTO();
			HlApplicationFeeTO hlApplicationFeeTO=new HlApplicationFeeTO();
			double totalAmount=0.0;
			double amount=0.0;
			roomTypeWithAmountTO.setRoomType(roomType.getName());
			 if(roomType.getHlFeeses()!=null){
				for(HlFees hlFees:roomType.getHlFeeses()){
					if(hlFees.getHlFeeType()!=null){
						hlApplicationFeeTO.setFeeTypeName(hlFees.getHlFeeType().getName());
						if(hlFees.getFeeAmount()!=null){
							amount=hlFees.getFeeAmount().doubleValue();
							totalAmount=amount+totalAmount;
						}
						hlApplicationFeeTO.setAmount(amount+"");
						hlApplicationFeeTO.setTotalAmount(totalAmount+"");
						hlApplicationFeeToList.add(hlApplicationFeeTO);
						roomTypeWithAmountTO.setAmountList(hlApplicationFeeToList);
						roomTypeWithAmountTOList.add(roomTypeWithAmountTO);
					  }
				    }
			      }
			    }
		
		  return roomTypeWithAmountTOList;
		
	   }*/
	
      public HostelTO prepareTermsConditionView(HlHostel hostel)throws Exception{
		
		log.info("Entering into viewTermsConditions of hostelApplicationByAdminHelper");
		HostelTO hostelTO = null;
		if(hostel!=null){
			hostelTO = new HostelTO();
			//hostelTO.setId(hostel.getId());
			if(hostel.getTermsConditions()!=null){
				hostelTO.setTermsConditions(hostel.getTermsConditions());
			}
			if(hostel.getFileName()!=null){
				hostelTO.setFileName(hostel.getFileName());
			}
			if(hostel.getContentType()!=null){
				hostelTO.setContentType(hostel.getContentType());
			}
		}		
		log.info("Leaving into viewTermsConditions of hostelApplicationByAdminHelper");
		return hostelTO;
	  }
      

      
      
   public PersonalDataTO viewPersonalDetailsForStudent(AdmAppln admAppln)throws Exception{
		
	PersonalDataTO personalDataTO = null;
    PersonalData personalData=admAppln.getPersonalData();
	 
	if (personalData != null) {
		personalDataTO = new PersonalDataTO();

		personalDataTO.setFirstName(personalData.getFirstName());
		personalDataTO.setMiddleName(personalData.getMiddleName());
		personalDataTO.setLastName(personalData.getLastName());

		if (personalData.getDateOfBirth() != null) {
			personalDataTO.setDob(CommonUtil.getStringDate(personalData
					.getDateOfBirth()));
		}
		personalDataTO.setBirthPlace(personalData.getBirthPlace());

		if (personalData.getCountryOthers() != null
				&& personalData.getCountryOthers().trim().length() > 0) {
			personalDataTO.setCountryOfBirth(personalData
					.getCountryOthers());
		} else if (personalData.getCountryByCountryId() != null) {
			personalDataTO.setCountryOfBirth(personalData
					.getCountryByCountryId().getName());
		}
		List<Student> studentList=new ArrayList<Student>();
		studentList.addAll(admAppln.getStudents());
		if(studentList.get(0)!=null && studentList.size()>0){
			personalDataTO.setRollNo(studentList.get(0).getRollNo());
			personalDataTO.setRegisterNo(studentList.get(0).getRegisterNo());
			if(studentList.get(0).getClassSchemewise()!=null && studentList.get(0).getClassSchemewise().getClasses()!=null){
				personalDataTO.setStudentClass(studentList.get(0).getClassSchemewise().getClasses().getName());	
			}
		}

		if (personalData.getStateOthers() != null
				&& personalData.getStateOthers().trim().length() > 0) {
			personalDataTO.setStateOfBirth(personalData.getStateOthers());
		} else if (personalData.getStateByStateId() != null) {
			personalDataTO.setStateOfBirth(personalData.getStateByStateId()
					.getName());
		}

		if (personalData.getNationalityOthers() != null
				&& personalData.getNationalityOthers().trim().length() > 0) {
			personalDataTO.setCitizenship(personalData
					.getNationalityOthers());
		} else if (personalData.getNationality() != null) {
			personalDataTO.setCitizenship(personalData.getNationality()
					.getName());
		}

		if (personalData.getResidentCategory() != null) {
			personalDataTO.setResidentCategoryName(personalData
					.getResidentCategory().getName());
		}
		if (personalData.getReligionOthers() != null
				&& personalData.getReligionOthers().trim().length() > 0) {
			personalDataTO
					.setReligionName(personalData.getReligionOthers());
		} else if (personalData.getReligion() != null) {
			personalDataTO.setReligionName(personalData.getReligion()
					.getName());
		}

		if (personalData.getReligionSectionOthers() != null
				&& personalData.getReligionSectionOthers().trim().length() > 0) {
			personalDataTO.setSubregligionName(personalData
					.getReligionSectionOthers());
		} else if (personalData.getReligionSection() != null) {
			personalDataTO.setSubregligionName(personalData
					.getReligionSection().getName());
		}
		if (personalData.getCasteOthers() != null
				&& personalData.getCasteOthers().trim().length() > 0) {
			personalDataTO.setCasteCategory(personalData.getCasteOthers());
		} else if (personalData.getCaste() != null) {
			personalDataTO.setCasteCategory(personalData.getCaste()
					.getName());
		}
		personalDataTO.setRuralUrban(personalData.getRuralUrban());
		personalDataTO.setAreaType(personalData.getRuralUrban());
		personalDataTO.setGender(personalData.getGender());
		if (personalData.getIsSportsPerson() != null) {
			personalDataTO
					.setSportsPerson(personalData.getIsSportsPerson());
		}
		if (personalData.getSportsPersonDescription() != null) {
			personalDataTO.setSportsDescription(personalData
					.getSportsPersonDescription());
		}

		if (personalData.getIsHandicapped() != null) {
			personalDataTO.setHandicapped(personalData.getIsHandicapped());
		}
		if (personalData.getHandicappedDescription() != null) {
			personalDataTO.setHadnicappedDescription(personalData
					.getHandicappedDescription());
		}

		if (personalData.getSecondLanguage() != null) {
			personalDataTO.setSecondLanguage(personalData
					.getSecondLanguage());
		}

		personalDataTO.setBloodGroup(personalData.getBloodGroup());
		if (personalData.getPhNo1() != null
				&& personalData.getPhNo2() != null
				&& personalData.getPhNo3() != null) {
			personalDataTO.setLandlineNo(personalData.getPhNo1() + " "
					+ personalData.getPhNo2() + " "
					+ personalData.getPhNo3());
		} else if (personalData.getPhNo1() != null
				&& personalData.getPhNo2() != null) {
			personalDataTO.setLandlineNo(personalData.getPhNo1() + " "
					+ personalData.getPhNo2());
		} else if (personalData.getPhNo1() != null) {
			personalDataTO.setLandlineNo(personalData.getPhNo1());
		}
		if (personalData.getMobileNo1() != null
				&& personalData.getMobileNo2() != null
				&& personalData.getMobileNo3() != null) {
			personalDataTO.setMobileNo(personalData.getMobileNo1() + " "
					+ personalData.getMobileNo2() + " "
					+ personalData.getMobileNo3());
		} else if (personalData.getMobileNo1() != null
				&& personalData.getMobileNo2() != null) {
			personalDataTO.setMobileNo(personalData.getMobileNo1() + " "
					+ personalData.getMobileNo2());
		} else if (personalData.getMobileNo1() != null) {
			personalDataTO.setMobileNo(personalData.getMobileNo1());
		}

		personalDataTO.setEmail(personalData.getEmail());

		personalDataTO.setPassportNo(personalData.getPassportNo());
		if (personalData.getCountryByPassportCountryId() != null) {
			personalDataTO.setPassportIssuingCountry(personalData
					.getCountryByPassportCountryId().getName());
		}
		if (personalData.getPassportValidity() != null) {
			personalDataTO.setPassportValidity(CommonUtil
					.getStringDate(personalData.getPassportValidity()));
		}
		personalDataTO.setResidentPermitNo(personalData
				.getResidentPermitNo());
		if (personalData.getResidentPermitDate() != null) {
			personalDataTO.setResidentPermitDate(CommonUtil
					.ConvertStringToDateFormat(CommonUtil
							.getStringDate(personalData
									.getResidentPermitDate()),
							"dd-MMM-yyyy", "dd/MM/yyyy"));
		}

		// Setting applicant's permanent address details to personalData TO
		personalDataTO.setPermanentAddressLine1(personalData
				.getPermanentAddressLine1());
		personalDataTO.setPermanentAddressLine2(personalData
				.getPermanentAddressLine2());
		if (personalData.getCityByPermanentAddressCityId() != null) {
			personalDataTO.setPermanentCityName(personalData
					.getCityByPermanentAddressCityId());
		}
		if (personalData.getPermanentAddressStateOthers() != null
				&& personalData.getPermanentAddressStateOthers().trim()
						.length() > 0) {
			personalDataTO.setPermanentStateName(personalData
					.getPermanentAddressStateOthers());
		} else if (personalData.getStateByPermanentAddressStateId() != null) {
			personalDataTO.setPermanentStateName(personalData
					.getStateByPermanentAddressStateId().getName());
		}
		if (personalData.getPermanentAddressCountryOthers() != null
				&& personalData.getParentAddressCountryOthers().trim()
						.length() > 0) {
			personalDataTO.setPermanentCountryName(personalData
					.getPermanentAddressCountryOthers());
		} else if (personalData.getCountryByPermanentAddressCountryId() != null) {
			personalDataTO.setPermanentCountryName(personalData
					.getCountryByPermanentAddressCountryId().getName());
		}
		personalDataTO.setPermanentAddressZipCode(personalData
				.getPermanentAddressZipCode());

		// Setting applicant's current address details to personalData TO
		personalDataTO.setCurrentAddressLine1(personalData
				.getCurrentAddressLine1());
		personalDataTO.setCurrentAddressLine2(personalData
				.getCurrentAddressLine2());
		if (personalData.getCityByCurrentAddressCityId() != null) {
			personalDataTO.setCurrentCityName(personalData
					.getCityByCurrentAddressCityId());
		}
		if (personalData.getCurrentAddressStateOthers() != null
				&& personalData.getCurrentAddressStateOthers().trim()
						.length() > 0) {
			personalDataTO.setCurrentStateName(personalData
					.getCurrentAddressStateOthers());
		} else if (personalData.getStateByCurrentAddressStateId() != null) {
			personalDataTO.setCurrentStateName(personalData
					.getStateByCurrentAddressStateId().getName());
		}
		if (personalData.getCurrentAddressCountryOthers() != null
				&& personalData.getCurrentAddressCountryOthers().trim()
						.length() > 0) {
			personalDataTO.setCurrentCountryName(personalData
					.getCurrentAddressCountryOthers());
		} else if (personalData.getCountryByCurrentAddressCountryId() != null) {
			personalDataTO.setCurrentCountryName(personalData
					.getCountryByCurrentAddressCountryId().getName());
		}
		personalDataTO.setCurrentAddressZipCode(personalData
				.getCurrentAddressZipCode());

		// Setting applicant's father details to personalData TO
		personalDataTO.setFatherName(personalData.getFatherName());
		personalDataTO
				.setFatherEducation(personalData.getFatherEducation());
		if (personalData.getCurrencyByFatherIncomeCurrencyId() != null
				&& personalData.getIncomeByFatherIncomeId() != null
				&& personalData.getIncomeByFatherIncomeId() != null
				&& personalData.getIncomeByFatherIncomeId().getCurrency() != null) {
			personalDataTO.setFatherIncome(personalData
					.getCurrencyByFatherIncomeCurrencyId()
					.getCurrencyCode()
					+ " "
					+ personalData.getIncomeByFatherIncomeId()
							.getIncomeRange());
		} else if (personalData.getIncomeByFatherIncomeId() != null) {
			personalDataTO.setFatherIncome(personalData
					.getIncomeByFatherIncomeId().getIncomeRange());
		}
		if (personalData.getOccupationByFatherOccupationId() != null) {
			personalDataTO.setFatherOccupation(personalData
					.getOccupationByFatherOccupationId().getName());
		}
		personalDataTO.setFatherEmail(personalData.getFatherEmail());

		// Setting applicant's mother details to personalData TO
		personalDataTO.setMotherName(personalData.getMotherName());
		personalDataTO
				.setMotherEducation(personalData.getMotherEducation());

		if (personalData.getCurrencyByMotherIncomeCurrencyId() != null
				&& personalData.getIncomeByMotherIncomeId() != null
				&& personalData.getIncomeByMotherIncomeId().getCurrency() != null) {

			personalDataTO.setMotherIncome(personalData
					.getCurrencyByMotherIncomeCurrencyId()
					.getCurrencyCode()
					+ " "
					+ personalData.getIncomeByMotherIncomeId()
							.getIncomeRange());
		} else if (personalData.getIncomeByMotherIncomeId() != null) {
			personalDataTO.setMotherIncome(personalData
					.getIncomeByMotherIncomeId().getIncomeRange());
		}

		if (personalData.getOccupationByMotherOccupationId() != null) {
			personalDataTO.setMotherOccupation(personalData
					.getOccupationByMotherOccupationId().getName());
		}
		personalDataTO.setMotherEmail(personalData.getMotherEmail());

		// Setting applicant's parent address to personalData TO
		personalDataTO.setParentAddressLine1(personalData
				.getParentAddressLine1());
		personalDataTO.setParentAddressLine2(personalData
				.getParentAddressLine2());
		personalDataTO.setParentAddressLine3(personalData
				.getParentAddressLine3());
		if (personalData.getCityByParentAddressCityId() != null) {
			personalDataTO.setParentCityName(personalData
					.getCityByParentAddressCityId());
		}
		if (personalData.getParentAddressStateOthers() != null
				&& personalData.getParentAddressStateOthers().trim()
						.length() > 0) {
			personalDataTO.setParentStateName(personalData
					.getParentAddressStateOthers());
		} else if (personalData.getStateByParentAddressStateId() != null) {
			personalDataTO.setParentStateName(personalData
					.getStateByParentAddressStateId().getName());
		}
		if (personalData.getParentAddressCountryOthers() != null
				&& personalData.getParentAddressCountryOthers().trim()
						.length() > 0) {
			personalDataTO.setParentCountryName(personalData
					.getParentAddressCountryOthers());
		} else if (personalData.getCountryByParentAddressCountryId() != null) {
			personalDataTO.setParentCountryName(personalData
					.getCountryByParentAddressCountryId().getName());
		}
		personalDataTO.setParentAddressZipCode(personalData
				.getParentAddressZipCode());

		if (personalData.getParentPh1() != null
				&& personalData.getParentPh2() != null
				&& personalData.getParentPh3() != null) {
			personalDataTO.setParentPhone(personalData.getParentPh1() + " "
					+ personalData.getParentPh2() + " "
					+ personalData.getParentPh3());
		} else if (personalData.getParentPh1() != null
				&& personalData.getParentPh2() != null) {
			personalDataTO.setParentPhone(personalData.getParentPh1() + " "
					+ personalData.getParentPh2());
		} else if (personalData.getParentPh1() != null) {
			personalDataTO.setParentPhone(personalData.getParentPh1());
		}

		if (personalData.getParentMob1() != null
				&& personalData.getParentMob2() != null
				&& personalData.getParentMob3() != null) {
			personalDataTO.setParentMobile(personalData.getParentMob1()
					+ " " + personalData.getParentMob2() + " "
					+ personalData.getParentMob3());
		} else if (personalData.getParentMob1() != null
				&& personalData.getParentMob2() != null) {
			personalDataTO.setParentMobile(personalData.getParentMob1()
					+ " " + personalData.getParentMob2());
		} else if (personalData.getParentMob1() != null) {
			personalDataTO.setParentMobile(personalData.getParentMob1());
		}

		// guardian address
		personalDataTO.setGuardianName(personalData.getGuardianName());
		personalDataTO.setGuardianAddressLine1(personalData
				.getGuardianAddressLine1());
		personalDataTO.setGuardianAddressLine2(personalData
				.getGuardianAddressLine2());
		personalDataTO.setGuardianAddressLine3(personalData
				.getGuardianAddressLine3());
		if (personalData.getCityByGuardianAddressCityId() != null) {
			personalDataTO.setCityByGuardianAddressCityId(personalData
					.getCityByGuardianAddressCityId());
		}
		if (personalData.getGuardianAddressStateOthers() != null
				&& personalData.getGuardianAddressStateOthers().trim()
						.length() > 0) {
			personalDataTO.setGuardianStateName(personalData
					.getGuardianAddressStateOthers());
		} else if (personalData.getStateByGuardianAddressStateId() != null) {
			personalDataTO.setGuardianStateName(personalData
					.getStateByGuardianAddressStateId().getName());
		}
		if (personalData.getCountryByGuardianAddressCountryId() != null) {
			personalDataTO.setGuardianCountryName(personalData
					.getCountryByGuardianAddressCountryId().getName());
		}
		personalDataTO.setGuardianAddressZipCode(personalData
				.getGuardianAddressZipCode());
		personalDataTO.setGuardianPh1(personalData.getGuardianPh1());
		personalDataTO.setGuardianPh2(personalData.getGuardianPh2());
		personalDataTO.setGuardianPh3(personalData.getGuardianPh3());
		personalDataTO.setGuardianMob1(personalData.getGuardianMob1());
		personalDataTO.setGuardianMob2(personalData.getGuardianMob2());
		personalDataTO.setGuardianMob3(personalData.getGuardianMob3());

		personalDataTO.setBrotherName(personalData.getBrotherName());
		personalDataTO.setBrotherEducation(personalData
				.getBrotherEducation());
		personalDataTO.setBrotherOccupation(personalData
				.getBrotherOccupation());
		personalDataTO.setBrotherIncome(personalData.getBrotherIncome());
		personalDataTO.setBrotherAge(personalData.getBrotherAge());

		personalDataTO.setSisterName(personalData.getSisterName());
		personalDataTO
				.setSisterEducation(personalData.getSisterEducation());
		personalDataTO.setSisterOccupation(personalData
				.getSisterOccupation());
		personalDataTO.setSisterIncome(personalData.getSisterIncome());
		personalDataTO.setSisterAge(personalData.getSisterAge());

		if (personalData.getHeight() != null
				&& !StringUtils
						.isEmpty(personalData.getHeight().toString())
				&& StringUtils.isNumeric(personalData.getHeight()
						.toString())) {
			personalDataTO.setHeight(personalData.getHeight().toString());
		}

		if (personalData.getWeight() != null
				&& !StringUtils
						.isEmpty(personalData.getWeight().toString())
				&& CommonUtil.isValidDecimal(personalData.getWeight()
						.toString())) {
			personalDataTO.setWeight(personalData.getWeight().toString());
		}

		if (personalData.getLanguageByLanguageRead() != null
				&& !StringUtils.isEmpty(personalData
						.getLanguageByLanguageRead())) {
			personalDataTO.setLanguageByLanguageRead(personalData
					.getLanguageByLanguageRead());
		}
		if (personalData.getLanguageByLanguageWrite() != null
				&& !StringUtils.isEmpty(personalData
						.getLanguageByLanguageWrite())) {
			personalDataTO.setLanguageByLanguageWrite(personalData
					.getLanguageByLanguageWrite());
		}

		if (personalData.getLanguageByLanguageSpeak() != null
				&& !StringUtils.isEmpty(personalData
						.getLanguageByLanguageSpeak())) {
			personalDataTO.setLanguageByLanguageSpeak(personalData
					.getLanguageByLanguageSpeak());
		}

		if (personalData.getMotherTongue() != null
				&& !StringUtils.isEmpty(personalData.getMotherTongue()
						.toString())) {
			personalDataTO.setMotherTongue(personalData.getMotherTongue()
					.getName());
		}

		if (personalData.getTrainingDuration() != null
				&& !StringUtils.isEmpty(personalData.getTrainingDuration()
						.toString())
				&& StringUtils.isNumeric(personalData.getTrainingDuration()
						.toString())) {
			personalDataTO.setTrainingDuration(personalData
					.getTrainingDuration().toString());
		}

		personalDataTO.setTrainingInstAddress(personalData
				.getTrainingInstAddress());
		personalDataTO.setTrainingProgName(personalData
				.getTrainingProgName());
		personalDataTO
				.setTrainingPurpose(personalData.getTrainingPurpose());

		personalDataTO.setCourseKnownBy(personalData.getCourseKnownBy());
		personalDataTO
				.setCourseOptReason(personalData.getCourseOptReason());
		personalDataTO.setStrength(personalData.getStrength());
		personalDataTO.setWeakness(personalData.getWeakness());
		personalDataTO.setOtherAddnInfo(personalData.getOtherAddnInfo());
		personalDataTO.setSecondLanguage(personalData.getSecondLanguage());

	}
	return personalDataTO;
}
       
   public PersonalDataTO viewPersonalDetailsForStaff(Employee employee)throws Exception{
		
		log.info("Entering into viewPersonalDetailsForStaff of hostelApplicationByAdminHelper");
		PersonalDataTO personalDataTO = null;
		if(employee!=null){
			personalDataTO = new PersonalDataTO();
			personalDataTO.setFirstName(employee.getFirstName());
		//	personalDataTO.setMiddleName(employee.getMiddleName());
		//	personalDataTO.setLastName(employee.getLastName());
			if(employee.getDesignation()!=null){
				personalDataTO.setEmployeDesignation(employee.getDesignation().getName());	
			}
			if(employee.getDepartment()!=null){
				personalDataTO.setEmployeeDepartment(employee.getDepartment().getName());	
			}
			if (employee.getDob() != null) {
				personalDataTO.setDob(CommonUtil.getStringDate(employee.getDob()));		
			}
						
			 if (employee.getNationality() != null) {
				personalDataTO.setCitizenship(employee.getNationality().getName());			
			}
			 
			/*	if (employee.getEthinicRace()!= null){	 
					personalDataTO.setCasteCategory(employee.getEthinicRace());
				}*/
				personalDataTO.setGender(employee.getGender());
				
				personalDataTO.setBloodGroup(employee.getBloodGroup());
				if (employee.getCurrentAddressHomeTelephone1()!= null
						&&employee.getCurrentAddressHomeTelephone2()!= null
						&& employee.getCurrentAddressHomeTelephone3()!= null) {
					personalDataTO.setLandlineNo(employee.getCurrentAddressHomeTelephone1() + " "
							+ employee.getCurrentAddressHomeTelephone2() + " "
							+ employee.getCurrentAddressHomeTelephone3());
				} else if (employee.getCurrentAddressHomeTelephone1()!= null
						&& employee.getCurrentAddressHomeTelephone2() != null) {
					personalDataTO.setLandlineNo(employee.getCurrentAddressHomeTelephone1()+ " "
							+ employee.getCurrentAddressHomeTelephone2());
				} else if (employee.getCurrentAddressHomeTelephone1()!= null) {
					personalDataTO.setLandlineNo(employee.getCurrentAddressHomeTelephone1());
				}
			/*	if(employee.getMobile()!=null){
					personalDataTO.setMobileNo(employee.getMobile()+"");
				}*/
				else if (employee.getCurrentAddressMobile1()!= null){
						//&& employee.getCurrentAddressMobile2()!= null
						//&&employee.getCurrentAddressMobile3()!= null) {
					personalDataTO.setMobileNo(employee.getCurrentAddressMobile1());
							
							;
				} else if (employee.getCurrentAddressMobile1()!= null
						) {
					personalDataTO.setMobileNo(employee.getCurrentAddressMobile1());
				} else if (employee.getCurrentAddressMobile1()!= null) {
					personalDataTO.setMobileNo(employee.getCurrentAddressMobile1());
				}
				personalDataTO.setEmail(employee.getEmail());
				personalDataTO.setWorkEmail(employee.getWorkEmail());
				// Setting applicant's permanent address details to personalData TO
				personalDataTO.setPermanentAddressLine1(employee.getPermanentAddressLine1());		
				personalDataTO.setPermanentAddressLine2(employee.getPermanentAddressLine2());
						
				if (employee.getPermanentAddressCity()!= null) {
					personalDataTO.setPermanentCityName(employee.getPermanentAddressCity());		
				}
				
			   if(employee.getStateByPermanentAddressStateId()!= null) {
					personalDataTO.setPermanentStateName(employee.getStateByPermanentAddressStateId().getName());			
				}
				if (employee.getCountryByPermanentAddressCountryId() != null) {
					personalDataTO.setPermanentCountryName(employee.getCountryByPermanentAddressCountryId().getName());			
				}
				personalDataTO.setPermanentAddressZipCode(employee.getPermanentAddressZip());	

				personalDataTO.setCurrentAddressLine1(employee.getCommunicationAddressLine1());
	
				personalDataTO.setCurrentAddressLine2(employee.getCommunicationAddressLine1());
						
				if (employee.getCommunicationAddressCity()!= null) {
					personalDataTO.setCurrentCityName(employee.getCommunicationAddressCity());		
				}
				if (employee.getCommunicationAddressStateOthers()!= null
						&& employee.getCommunicationAddressStateOthers().trim()
								.length() > 0) {
					personalDataTO.setCurrentStateName(employee.getCommunicationAddressStateOthers());
							
				} else if (employee.getStateByCommunicationAddressStateId()!= null) {
					personalDataTO.setCurrentStateName(employee.getStateByCommunicationAddressStateId().getName());
				}
				if (employee.getCountryByCommunicationAddressCountryId()!= null) {
					personalDataTO.setCurrentCountryName(employee.getCountryByCommunicationAddressCountryId().getName());
				}
				personalDataTO.setCurrentAddressZipCode(employee.getCommunicationAddressZip());		

				//personalDataTO.setFatherName(employee.getFatherName());

		 }
		
		log.info("Leaving into viewPersonalDetailsForStaff of hostelApplicationByAdminHelper");
		return personalDataTO;
	  }  
   
      
      public List<RoomTypeWithAmountTO> copyFeesBOsToTO(List<HlFees> feesList)throws Exception{
  		log.info("Entering into copyFeesBOsToTO of HostelApplicationHelper");
  		Iterator<HlFees> feeIterator = feesList.iterator();
  		List<HlApplicationFeeTO> feeToList = new ArrayList<HlApplicationFeeTO>();
  		HlApplicationFeeTO applicationFeeTO;
  		Map<String, List<HlApplicationFeeTO>> feeMap = new HashMap<String, List<HlApplicationFeeTO>>();
  		Set<Integer> feeTypeCodemap = new HashSet<Integer>();
  		while(feeIterator.hasNext()){
  			applicationFeeTO = new HlApplicationFeeTO();
  			HlFees hlFees = feeIterator.next();
  			String roomTypeName = hlFees.getHlRoomType().getName();
  			if(roomTypeName!= null){
  				if(feeMap.containsKey(roomTypeName)){
  					feeToList = feeMap.get(roomTypeName); 
  				}
  				else
  				{
  					feeToList = new ArrayList<HlApplicationFeeTO>();
  				}
  			}
  			if(hlFees.getFeeAmount()!=null){
  			  applicationFeeTO.setAmount(hlFees.getFeeAmount().toString());
  			}
  			else
  			{
  				applicationFeeTO.setAmount("");
  			}
  			if(hlFees.getHlFeeType()!=null){
	  			applicationFeeTO.setFeeTypeId(hlFees.getHlFeeType().getId());
	  			applicationFeeTO.setFeeTypeName(hlFees.getHlFeeType().getName());
	  		}
  			feeToList.add(applicationFeeTO);
  			feeMap.put(roomTypeName, feeToList);
  			if(hlFees.getHlFeeType()!= null){
  				if(!feeTypeCodemap.contains(hlFees.getHlFeeType().getId())){
  					feeTypeCodemap.add(hlFees.getHlFeeType().getId());
  				}
  			}
  		}
  		
  		Iterator<String> keyIterator = feeMap.keySet().iterator();
  		HlApplicationFeeTO tempTo;  
  		List<RoomTypeWithAmountTO> roomTypeWithAmountList = new ArrayList<RoomTypeWithAmountTO>();
  		RoomTypeWithAmountTO roomAmountTO;
  		while (keyIterator.hasNext()) {
  			String string = (String) keyIterator.next();
  			feeToList = feeMap.get(string);
  			Iterator<HlApplicationFeeTO> tempItr = feeToList.iterator();
  			Set<Integer> tempMap = new HashSet<Integer>();
  			while(tempItr.hasNext()){
  				tempTo = tempItr.next();
  				tempMap.add(tempTo.getFeeTypeId());
  			}
  			HlApplicationFeeTO newTo; 
  			Iterator<Integer> origItr = feeTypeCodemap.iterator();
  			while(origItr.hasNext()){
  				int id = origItr.next();
  				if(!tempMap.contains(id)){
  					newTo = new HlApplicationFeeTO();
  					newTo.setFeeTypeId(id);
  					newTo.setAmount("");
  					feeToList.add(newTo);
  				}
  			}
  			roomAmountTO = new RoomTypeWithAmountTO();
  			roomAmountTO.setRoomType(string);
  			Collections.sort(feeToList);
  			roomAmountTO.setAmountList(feeToList);
  			roomTypeWithAmountList.add(roomAmountTO);
  		}		
  		
  		log.info("Leaving into copyFeesBOsToTO of HostelApplicationHelper");
  		return roomTypeWithAmountList;
  	}	
      

     
	
   }



 
