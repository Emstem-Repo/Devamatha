package com.kp.cms.helpers.employee;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpDependents;
import com.kp.cms.bo.admin.EmpImmigration;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpAcademicQualificationBO;
import com.kp.cms.bo.employee.EmpAwardDetailsBO;
import com.kp.cms.bo.employee.EmpBooksPublishedDetailsBO;
import com.kp.cms.bo.employee.EmpDutiesDetailsBO;
import com.kp.cms.bo.employee.EmpEducationalDetails;
import com.kp.cms.bo.employee.EmpFeeConcession;
import com.kp.cms.bo.employee.EmpFinancial;
import com.kp.cms.bo.employee.EmpGuideshipDetailsBo;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.EmpIncentives;
import com.kp.cms.bo.employee.EmpIntrestsBO;
import com.kp.cms.bo.employee.EmpLoan;
import com.kp.cms.bo.employee.EmpMemeberShipDetailsBO;
import com.kp.cms.bo.employee.EmpPaperPresentationBO;
import com.kp.cms.bo.employee.EmpPayAllowanceDetails;
import com.kp.cms.bo.employee.EmpPreviousExperience;
import com.kp.cms.bo.employee.EmpProfessionalDevelopmentBO;
import com.kp.cms.bo.employee.EmpProjectResearchDetailsBO;
import com.kp.cms.bo.employee.EmpReaserchPublishcationDetailsBO;
import com.kp.cms.bo.employee.EmpRemarks;
import com.kp.cms.bo.employee.EmpResearchBo;
import com.kp.cms.bo.employee.EmpSeminarAttendedDetailsBO;
import com.kp.cms.bo.employee.GuestEducationalDetails;
import com.kp.cms.bo.employee.GuestImages;
import com.kp.cms.bo.employee.GuestPreviousExperience;
import com.kp.cms.forms.employee.EmployeeInfoViewForm;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.handlers.employee.EmployeeViewHandler;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.auditorium.EmpPaperPresentationTO;
import com.kp.cms.to.employee.EmpAcademicQualificationTO;
import com.kp.cms.to.employee.EmpAwardTO;
import com.kp.cms.to.employee.EmpBooksPublishedTO;
import com.kp.cms.to.employee.EmpDutiesPerformedTO;
import com.kp.cms.to.employee.EmpFeeConcessionTO;
import com.kp.cms.to.employee.EmpFieldResearchTO;
import com.kp.cms.to.employee.EmpFinancialTO;
import com.kp.cms.to.employee.EmpGuideShipDetailsTO;
import com.kp.cms.to.employee.EmpIncentivesTO;
import com.kp.cms.to.employee.EmpInterestsTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpLoanTO;
import com.kp.cms.to.employee.EmpMemberShipAcademicBodyTO;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpProfessionalDevelopmentTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpRemarksTO;
import com.kp.cms.to.employee.EmpResearchPublicationTO;
import com.kp.cms.to.employee.EmpResearchTO;
import com.kp.cms.to.employee.EmpSeminarAttendedDetailsTO;
import com.kp.cms.to.employee.EmployeeInfoEditNewTO;
import com.kp.cms.transactions.employee.IEmployeeViewTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeViewTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PayAllowance;

public class EmployeeViewHelper {
		
		private static volatile EmployeeViewHelper instance=null;
		
		/**
		 * 
		 */
		private EmployeeViewHelper(){
			
		}
		
		/**
		 * @return
		 */
		public static EmployeeViewHelper getInstance(){
			if(instance==null){
				instance=new EmployeeViewHelper();
			}
			return instance;
		}

		IEmployeeViewTransaction txn = new EmployeeViewTransactionImpl();

		
		/**
		 * @param EmployeeInfoViewForm
		 * @return
		 */
				
		public String getQueryByselectedEmpTypeId(String empTypeId) throws Exception {
			String query="from EmpType e where e.isActive=true and e.id= "+empTypeId; 
			return query;
		}
		
		public String getLeaveByEmpTypeId(String empTypeId) throws Exception {
			String query="from EmpLeaveAllotment r where r.isActive=true and r.empType.id="+empTypeId; 
			return query;
		}
		
		public String getQueryByselectedPayscaleId(String payScaleId) throws Exception {
			String query="select p.scale from PayScaleBO p where p.isActive=true and p.id="+payScaleId; 
			return query;
		}

		public void convertBoToForm(Employee empApplicantDetails,EmployeeInfoViewForm objform) throws Exception {
			if(empApplicantDetails!=null){
				if (empApplicantDetails.getEligibilityTest() != null && !empApplicantDetails.getEligibilityTest().isEmpty()) {
					if(empApplicantDetails.getEligibilityTestOther()!=null && !empApplicantDetails.getEligibilityTestOther().isEmpty())
					{
						String empEligibAddOther=empApplicantDetails.getEligibilityTest()+"  --Other description:-"+empApplicantDetails.getEligibilityTestOther();
						 objform.setEligibilityTestdisplay(empEligibAddOther);
					}
					else
						 objform.setEligibilityTestdisplay(empApplicantDetails.getEligibilityTest());
				}
				if(empApplicantDetails.getIndustryFunctionalArea()!=null && !empApplicantDetails.getIndustryFunctionalArea().trim().isEmpty()){
					objform.setIndustryFunctionalArea(empApplicantDetails.getIndustryFunctionalArea());
				}			
				if(empApplicantDetails.getReservationCategory() !=null && !empApplicantDetails.getReservationCategory().isEmpty()){
					if("GM".equalsIgnoreCase(empApplicantDetails.getReservationCategory())){
						objform.setReservationCategory(empApplicantDetails.getReservationCategory());
					}
					if("SC".equalsIgnoreCase(empApplicantDetails.getReservationCategory())){
						objform.setReservationCategory(empApplicantDetails.getReservationCategory());
					}
					if("ST".equalsIgnoreCase(empApplicantDetails.getReservationCategory())){
						objform.setReservationCategory(empApplicantDetails.getReservationCategory());
					}
					if("OBC".equalsIgnoreCase(empApplicantDetails.getReservationCategory())){
						objform.setReservationCategory(empApplicantDetails.getReservationCategory());
					}
					if("Minority".equalsIgnoreCase(empApplicantDetails.getReservationCategory())){
						objform.setReservationCategory(empApplicantDetails.getReservationCategory());
					}
					if("Person With Disability".equalsIgnoreCase(empApplicantDetails.getReservationCategory())){
						
						String personWithDisability=empApplicantDetails.getReservationCategory()+"  --Handicap description:-"+empApplicantDetails.getHandicappedDescription();
							objform.setReservationCategory(personWithDisability);
						
						}
				}
				
				
				
				////...............................................................Photo.....................................................
			
				
				if(empApplicantDetails.getEmpImages()!=null && !empApplicantDetails.getEmpImages().isEmpty()){
					Iterator<EmpImages> itr=empApplicantDetails.getEmpImages().iterator();
					while (itr.hasNext()) {
						EmpImages bo = itr.next();
						if(bo.getEmpPhoto()!=null)
							objform.setPhotoBytes(bo.getEmpPhoto());
					}
				
				}
			////...............................................................Photo.....................................................
				if(empApplicantDetails.getId()>0){
					 objform.setEmployeeId(String.valueOf(empApplicantDetails.getId()));
				}
				if(StringUtils.isNotEmpty(String.valueOf(empApplicantDetails.getTeachingStaff()))){
					String Value= String.valueOf(empApplicantDetails.getTeachingStaff());
					if(Value.equals("true"))
						objform.setTeachingStaff("Teaching Staff");
					else
						objform.setTeachingStaff("Non-teaching Staff");
					 
				}
					
				
				if(StringUtils.isNotEmpty(String.valueOf(empApplicantDetails.getActive()))){
					String Value= String.valueOf(empApplicantDetails.getActive());
					if(Value.equals("true"))
						objform.setActive("Active");
					else
						objform.setActive("Not Active");
					 
				}
				if(StringUtils.isNotEmpty(String.valueOf(empApplicantDetails.getIsSameAddress()))){
					String Value= String.valueOf(empApplicantDetails.getIsSameAddress());
					if(Value.equals("true"))
						objform.setSameAddress("true");
					else
						objform.setSameAddress("false");
					 
				}
				if(StringUtils.isNotEmpty(String.valueOf(empApplicantDetails.getCurrentlyWorking()))){
					String Value= String.valueOf(empApplicantDetails.getCurrentlyWorking());
					if(Value.equals("true"))
						objform.setCurrentlyWorking("YES");
					else
						objform.setCurrentlyWorking("NO");
					 
				}
				
				if(empApplicantDetails.getPayScaleId()!=null && empApplicantDetails.getPayScaleId().getId()>0){
					  objform.setPayScaleId(String.valueOf(empApplicantDetails.getPayScaleId().getPayScale()));
				}
				
				if(StringUtils.isNotEmpty(empApplicantDetails.getFirstName()) && empApplicantDetails.getFirstName()!=null){
					  objform.setName(empApplicantDetails.getFirstName());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getSmartCardNo()) && empApplicantDetails.getSmartCardNo()!=null){
					  objform.setSmartCardNo(empApplicantDetails.getSmartCardNo());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getUid()) && empApplicantDetails.getUid()!=null){
					  objform.setuId(empApplicantDetails.getUid());
				}
				if(empApplicantDetails.getTitleId()!=null && empApplicantDetails.getTitleId().getId()>0){
					  objform.setTitleId(String.valueOf(empApplicantDetails.getTitleId().getTitle()));
				}
				if(empApplicantDetails.getDepartment()!=null && empApplicantDetails.getDepartment().getId()>0){
					  objform.setDepartmentId(String.valueOf(empApplicantDetails.getDepartment().getName()));
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getCode())&& empApplicantDetails.getCode()!=null){
					  objform.setCode(empApplicantDetails.getCode());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getFingerPrintId()) && empApplicantDetails.getFingerPrintId()!=null){
					  objform.setFingerPrintId(empApplicantDetails.getFingerPrintId());
				}
				if(empApplicantDetails.getGender()!=null && !empApplicantDetails.getGender().isEmpty()){
					 objform.setGender(empApplicantDetails.getGender());
				}
				if(empApplicantDetails.getNationality()!=null && empApplicantDetails.getNationality().getId()>0){
					  objform.setNationalityId(String.valueOf(empApplicantDetails.getNationality().getName()));
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getMaritalStatus()) && empApplicantDetails.getMaritalStatus()!=null){
					  objform.setMaritalStatus(String.valueOf(empApplicantDetails.getMaritalStatus()));
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getBloodGroup()) && empApplicantDetails.getBloodGroup()!=null){
					  objform.setBloodGroup(String.valueOf(empApplicantDetails.getBloodGroup()));
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getPanNo()) && empApplicantDetails.getPanNo()!=null){
					  objform.setPanno(String.valueOf(empApplicantDetails.getPanNo()));
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getEmail()) && empApplicantDetails.getEmail()!=null){
					  objform.setEmail(String.valueOf(empApplicantDetails.getEmail()));
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail()) && empApplicantDetails.getWorkEmail()!=null){
					  objform.setOfficialEmail(String.valueOf(empApplicantDetails.getWorkEmail()));
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentAddressMobile1()) && empApplicantDetails.getCurrentAddressMobile1()!=null){
					  objform.setMobileNo1(empApplicantDetails.getCurrentAddressMobile1());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getBankAccNo()) && empApplicantDetails.getBankAccNo()!=null){
					  objform.setBankAccNo(empApplicantDetails.getBankAccNo());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getPfNo()) && empApplicantDetails.getPfNo()!=null){
					  objform.setPfNo(empApplicantDetails.getPfNo());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getFourWheelerNo()) && empApplicantDetails.getFourWheelerNo()!=null){
					  objform.setFourWheelerNo(empApplicantDetails.getFourWheelerNo());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getTwoWheelerNo()) && empApplicantDetails.getTwoWheelerNo()!=null){
					  objform.setTwoWheelerNo(empApplicantDetails.getTwoWheelerNo());
				}
				
				if(empApplicantDetails.getReligionId()!=null && empApplicantDetails.getReligionId().getId()>0){
					  objform.setReligionId(String.valueOf(empApplicantDetails.getReligionId().getName()));
				}
				if(empApplicantDetails.getEmptype()!=null && empApplicantDetails.getEmptype().getId()>0){
					  objform.setEmptypeId(String.valueOf(empApplicantDetails.getEmptype().getName()));
				}
				if(empApplicantDetails.getEmpQualificationLevel()!=null && empApplicantDetails.getEmpQualificationLevel().getId()>0){
					  objform.setQualificationId(String.valueOf(empApplicantDetails.getEmpQualificationLevel().getName()));
				}
				//Modification    ........................................................
				
				if(StringUtils.isNotEmpty(empApplicantDetails.getPermanentAddressLine1()) && empApplicantDetails.getPermanentAddressLine1()!=null){
					  objform.setAddressLine1(empApplicantDetails.getPermanentAddressLine1());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getPermanentAddressLine2()) && empApplicantDetails.getPermanentAddressLine2()!=null ){
					  objform.setAddressLine2(empApplicantDetails.getPermanentAddressLine2());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getPermanentAddressCity()) && empApplicantDetails.getPermanentAddressCity()!=null){
					  objform.setCity(empApplicantDetails.getPermanentAddressCity());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getPermanentAddressZip()) && empApplicantDetails.getPermanentAddressZip()!=null){
					  objform.setPermanentZipCode(empApplicantDetails.getPermanentAddressZip());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getPermanentAddressStateOthers()) && empApplicantDetails.getPermanentAddressStateOthers()!=null){
					  objform.setOtherPermanentState(empApplicantDetails.getPermanentAddressStateOthers());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getCommunicationAddressLine1()) && empApplicantDetails.getCommunicationAddressLine1()!=null){
					  objform.setCurrentAddressLine1(empApplicantDetails.getCommunicationAddressLine1());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getCommunicationAddressLine2()) && empApplicantDetails.getCommunicationAddressLine2()!=null){
					  objform.setCurrentAddressLine2(empApplicantDetails.getCommunicationAddressLine2());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getCommunicationAddressCity()) && empApplicantDetails.getCommunicationAddressCity()!=null){
					  objform.setCurrentCity(empApplicantDetails.getCommunicationAddressCity());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getCommunicationAddressStateOthers()) && empApplicantDetails.getCommunicationAddressStateOthers()!=null){
					  objform.setOtherCurrentState(empApplicantDetails.getCommunicationAddressStateOthers());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getCommunicationAddressZip()) && empApplicantDetails.getCommunicationAddressZip()!=null){
					  objform.setCurrentZipCode(empApplicantDetails.getCommunicationAddressZip());
				}
				if(empApplicantDetails.getCountryByPermanentAddressCountryId()!=null && empApplicantDetails.getCountryByPermanentAddressCountryId().getId()>0){
					  objform.setCountryId(String.valueOf(empApplicantDetails.getCountryByPermanentAddressCountryId().getName()));
				}
				if(empApplicantDetails.getCountryByCommunicationAddressCountryId()!=null && empApplicantDetails.getCountryByCommunicationAddressCountryId().getId()>0){
					  objform.setCurrentCountryId(String.valueOf(empApplicantDetails.getCountryByCommunicationAddressCountryId().getName()));
				}
				if(empApplicantDetails.getCurrentAddressHomeTelephone1()!=null && !empApplicantDetails.getCurrentAddressHomeTelephone1().isEmpty()){
					objform.setHomePhone1(empApplicantDetails.getCurrentAddressHomeTelephone1());
				}
				
				if(empApplicantDetails.getCurrentAddressHomeTelephone2()!=null && !empApplicantDetails.getCurrentAddressHomeTelephone2().isEmpty()){
					objform.setHomePhone2(empApplicantDetails.getCurrentAddressHomeTelephone2());
				}
				
				if(empApplicantDetails.getCurrentAddressHomeTelephone3()!=null && !empApplicantDetails.getCurrentAddressHomeTelephone3().isEmpty()){
					objform.setHomePhone3(empApplicantDetails.getCurrentAddressHomeTelephone3());
				}
				if(empApplicantDetails.getCurrentAddressWorkTelephone1()!=null && !empApplicantDetails.getCurrentAddressWorkTelephone1().isEmpty()){
					objform.setWorkPhNo1(empApplicantDetails.getCurrentAddressWorkTelephone1());
				}
				
				if(empApplicantDetails.getCurrentAddressWorkTelephone2()!=null && !empApplicantDetails.getCurrentAddressWorkTelephone2().isEmpty()){
					objform.setWorkPhNo2(empApplicantDetails.getCurrentAddressWorkTelephone2());
				}
				
				if(empApplicantDetails.getCurrentAddressWorkTelephone3()!=null && !empApplicantDetails.getCurrentAddressWorkTelephone3().isEmpty()){
					objform.setWorkPhNo3(empApplicantDetails.getCurrentAddressWorkTelephone3());
				}

				if(StringUtils.isNotEmpty(empApplicantDetails.getDesignationName()) && empApplicantDetails.getDesignationName()!=null){
					  objform.setDesignation(empApplicantDetails.getDesignationName());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getOrganistionName()) && empApplicantDetails.getOrganistionName()!=null ){
					  objform.setOrgAddress(empApplicantDetails.getOrganistionName());
				}
				if(empApplicantDetails.getDesignation()!=null && empApplicantDetails.getDesignation().getId()>0){
					  objform.setDesignationPfId(String.valueOf(empApplicantDetails.getDesignation().getName()));
				}
				if(empApplicantDetails.getDepartment()!=null && empApplicantDetails.getDepartment().getId()>0){
					  objform.setDepartmentId(String.valueOf(empApplicantDetails.getDepartment().getName()));
				}
				if(empApplicantDetails.getEmployeeByReportToId()!=null && empApplicantDetails.getEmployeeByReportToId().getId()>0){
					  objform.setReportToId(String.valueOf(empApplicantDetails.getEmployeeByReportToId().getFirstName()));
				}
			/*	if(empApplicantDetails.getGrade()!=null && !empApplicantDetails.getGrade().isEmpty()){
					  objform.setGrade(String.valueOf(empApplicantDetails.getGrade()));
				}*/
				/*if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentOrganization()) && empApplicantDetails.getCurrentOrganization()!=null){
					  objform.setOrgAddress(empApplicantDetails.getCurrentOrganization());
				}*/
				if(empApplicantDetails.getBooks()!=null && !empApplicantDetails.getBooks().isEmpty()){
					  objform.setBooks(String.valueOf(empApplicantDetails.getBooks()));
				}
				if(empApplicantDetails.getNoOfPublicationsNotRefered()!=null && !empApplicantDetails.getNoOfPublicationsNotRefered().isEmpty()){
					  objform.setNoOfPublicationsNotRefered(empApplicantDetails.getNoOfPublicationsNotRefered());
				}
				if(empApplicantDetails.getNoOfPublicationsRefered()!=null && !empApplicantDetails.getNoOfPublicationsRefered().isEmpty()){
					  objform.setNoOfPublicationsRefered(empApplicantDetails.getNoOfPublicationsRefered());
				}
								
				if(empApplicantDetails.getEmpQualificationLevel()!=null && empApplicantDetails.getEmpQualificationLevel().getId()>0){
					  objform.setQualificationId(String.valueOf(empApplicantDetails.getEmpQualificationLevel().getName()));
				}
				if(empApplicantDetails.getDesignation()!=null && empApplicantDetails.getDesignation().getId()>0){
					  objform.setDesignationPfId(String.valueOf(empApplicantDetails.getDesignation().getName()));
				}
				if(empApplicantDetails.getEmpSubjectArea()!=null && empApplicantDetails.getEmpSubjectArea().getId()>0){
					  objform.setEmpSubjectAreaId(String.valueOf(empApplicantDetails.getEmpSubjectArea().getName()));
				}
				if(empApplicantDetails.getDob()!=null && !empApplicantDetails.getDob().toString().isEmpty() ){
					objform.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getDob().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(empApplicantDetails.getDateOfLeaving()!=null && !empApplicantDetails.getDateOfLeaving().toString().isEmpty()){
					objform.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getDateOfLeaving().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(empApplicantDetails.getDateOfResignation()!=null && !empApplicantDetails.getDateOfResignation().toString().isEmpty()){
					objform.setDateOfResignation(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getDateOfResignation().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(empApplicantDetails.getDoj()!=null && !empApplicantDetails.getDoj().toString().isEmpty()){
					objform.setDateOfJoining(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getDoj().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(empApplicantDetails.getRejoinDate()!=null && !empApplicantDetails.getRejoinDate().toString().isEmpty()){
					objform.setRejoinDate(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getRejoinDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(empApplicantDetails.getRetirementDate()!=null && !empApplicantDetails.getRetirementDate().toString().isEmpty()){
					objform.setRetirementDate(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getRetirementDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(empApplicantDetails.getTotalExpMonths()!=null && !empApplicantDetails.getTotalExpMonths().isEmpty()){
					  objform.setExpMonths(String.valueOf(empApplicantDetails.getTotalExpMonths()));
				}
				if(empApplicantDetails.getTotalExpYear()!=null && !empApplicantDetails.getTotalExpYear().isEmpty()){
					  objform.setExpYears(String.valueOf(empApplicantDetails.getTotalExpYear()));
				}
				/*if(empApplicantDetails.getActive()){
					  objform.setActive(String.valueOf(empApplicantDetails.getActive()));
				}*/
				
				
				if(StringUtils.isNotEmpty(empApplicantDetails.getEmergencyContName()) && empApplicantDetails.getEmergencyContName()!=null){
					  objform.setEmContactName(empApplicantDetails.getEmergencyContName());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getEmContactAddress()) && empApplicantDetails.getEmContactAddress()!=null){
					  objform.setEmContactAddress(empApplicantDetails.getEmContactAddress());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getEmergencyHomeTelephone()) && empApplicantDetails.getEmergencyHomeTelephone()!=null){
					  objform.setEmContactHomeTel(empApplicantDetails.getEmergencyHomeTelephone());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getEmergencyMobile()) && empApplicantDetails.getEmergencyMobile()!=null){
					  objform.setEmContactMobile(empApplicantDetails.getEmergencyMobile());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getEmergencyWorkTelephone()) && empApplicantDetails.getEmergencyWorkTelephone()!=null){
					  objform.setEmContactWorkTel(empApplicantDetails.getEmergencyWorkTelephone());
				}
				
				if(StringUtils.isNotEmpty(empApplicantDetails.getGrossPay()) && empApplicantDetails.getGrossPay()!=null){
					  objform.setGrossPay(empApplicantDetails.getGrossPay());
				}
				/*if(StringUtils.isNotEmpty(empApplicantDetails.getHalfDayEndTime())){
					  objform.setHalfDayEndTime(empApplicantDetails.getHalfDayEndTime());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getHalfDayStartTime())){
					  objform.setHalfDayStartTime(empApplicantDetails.getHalfDayStartTime());
				}*/
				if(StringUtils.isNotEmpty(empApplicantDetails.getHighQualifForAlbum()) && empApplicantDetails.getHighQualifForAlbum()!=null){
					  objform.setHighQualifForAlbum(empApplicantDetails.getHighQualifForAlbum());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getOtherInfo()) && empApplicantDetails.getOtherInfo()!=null){
					  objform.setOtherInfo(empApplicantDetails.getOtherInfo());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail()) && empApplicantDetails.getWorkEmail()!=null){
					  objform.setOfficialEmail(empApplicantDetails.getWorkEmail());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getPanNo()) && empApplicantDetails.getPanNo()!=null){
					  objform.setPanno(empApplicantDetails.getPanNo());
				}
				
							
				
				if(StringUtils.isNotEmpty(empApplicantDetails.getReasonOfLeaving()) && empApplicantDetails.getReasonOfLeaving()!=null){
					  objform.setReasonOfLeaving(empApplicantDetails.getReasonOfLeaving());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getRelationship()) && empApplicantDetails.getRelationship()!=null){
					  objform.setEmContactRelationship(empApplicantDetails.getRelationship());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getRelevantExpMonths()) && empApplicantDetails.getRelevantExpMonths()!=null ){
					  objform.setRelevantExpMonths(empApplicantDetails.getRelevantExpMonths());
				}
				if(StringUtils.isNotEmpty(empApplicantDetails.getRelevantExpYears()) && empApplicantDetails.getRelevantExpYears()!=null ){
					  objform.setRelevantExpYears(empApplicantDetails.getRelevantExpYears());
				}
				/*if(StringUtils.isNotEmpty(empApplicantDetails.getSaturdayTimeOut())){
					  objform.setSaturdayTimeOut(empApplicantDetails.getSaturdayTimeOut());
				}*/
				if(StringUtils.isNotEmpty(empApplicantDetails.getScale()) && empApplicantDetails.getScale()!=null){
					  objform.setScale(empApplicantDetails.getScale());
				}
				if(empApplicantDetails.getStateByCommunicationAddressStateId()!=null && empApplicantDetails.getStateByCommunicationAddressStateId().getId()>0){
					  objform.setCurrentState(String.valueOf(empApplicantDetails.getStateByCommunicationAddressStateId().getName()));
				}
				if(empApplicantDetails.getStateByPermanentAddressStateId()!=null && empApplicantDetails.getStateByPermanentAddressStateId().getId()>0){
					  objform.setStateId(String.valueOf(empApplicantDetails.getStateByPermanentAddressStateId().getName()));
				}
				if(empApplicantDetails.getStreamId() != null && empApplicantDetails.getStreamId().getId()>0){
					objform.setStreamId(String.valueOf(empApplicantDetails.getStreamId().getName()));
					
				}
				
				if(empApplicantDetails.getTimeIn()!=null && !empApplicantDetails.getTimeIn().isEmpty()){
					objform.setTimeIn(empApplicantDetails.getTimeIn().substring(0,2));
					objform.setTimeInMin(empApplicantDetails.getTimeIn().substring(3,5));
				}
			
				if(empApplicantDetails.getTimeInEnds()!=null && !empApplicantDetails.getTimeInEnds().isEmpty()){
					objform.setTimeInEnds(empApplicantDetails.getTimeInEnds().substring(0,2));
					objform.setTimeInEndMIn(empApplicantDetails.getTimeInEnds().substring(3,5));
				}
				
				if(empApplicantDetails.getTimeOut()!=null && !empApplicantDetails.getTimeOut().isEmpty()){
					objform.setTimeOut(empApplicantDetails.getTimeOut().substring(0,2));
					objform.setTimeOutMin(empApplicantDetails.getTimeOut().substring(3,5));
				}
				
				if(empApplicantDetails.getSaturdayTimeOut()!=null && !empApplicantDetails.getSaturdayTimeOut().isEmpty()){
					objform.setSaturdayTimeOut(empApplicantDetails.getSaturdayTimeOut().substring(0,2));
					objform.setSaturdayTimeOutMin(empApplicantDetails.getSaturdayTimeOut().substring(3,5));
				}
				
				if(empApplicantDetails.getHalfDayStartTime()!=null && !empApplicantDetails.getHalfDayStartTime().isEmpty()){
					objform.setHalfDayStartTime(empApplicantDetails.getHalfDayStartTime().substring(0,2));
					objform.setHalfDayStartTimeMin(empApplicantDetails.getHalfDayStartTime().substring(3,5));
				}
				
				if(empApplicantDetails.getHalfDayEndTime()!=null && !empApplicantDetails.getHalfDayEndTime().isEmpty()){
					objform.setHalfDayEndTime(empApplicantDetails.getHalfDayEndTime().substring(0,2));
					objform.setHalfDayEndTimeMin(empApplicantDetails.getHalfDayEndTime().substring(3,5));
				}
			
				
				
				
				
				
				
				
				
				/*if(empApplicantDetails.getTimeIn()!=null && !empApplicantDetails.getTimeIn().isEmpty()){
					objform.setTimeIn(empApplicantDetails.getTimeIn().substring(0,2));
					objform.setTimeInMin(empApplicantDetails.getTimeIn().substring(3,5));
				}
			
				if(empApplicantDetails.getTimeInEnds()!=null && !empApplicantDetails.getTimeInEnds().isEmpty()){
					objform.setTimeInEnds(empApplicantDetails.getTimeInEnds().substring(0,2));
					objform.setTimeInEndMIn(empApplicantDetails.getTimeInEnds().substring(3,5));
				}
				
				if(empApplicantDetails.getTimeOut()!=null && !empApplicantDetails.getTimeOut().isEmpty()){
					objform.setTimeOut(empApplicantDetails.getTimeOut().substring(0,2));
					objform.setTimeOutMin(empApplicantDetails.getTimeOut().substring(3,5));
				}
				
				if(empApplicantDetails.getSaturdayTimeOut()!=null && !empApplicantDetails.getSaturdayTimeOut().isEmpty()){
					objform.setSaturdayTimeOut(empApplicantDetails.getSaturdayTimeOut().substring(0,2));
					objform.setSaturdayTimeOutMin(empApplicantDetails.getSaturdayTimeOut().substring(3,5));
				}
				
				if(empApplicantDetails.getHalfDayStartTime()!=null && !empApplicantDetails.getHalfDayStartTime().isEmpty()){
					objform.setHalfDayStartTime(empApplicantDetails.getHalfDayStartTime().substring(0,2));
					objform.setHalfDayStartTimeMin(empApplicantDetails.getHalfDayStartTime().substring(3,5));
				}
				
				if(empApplicantDetails.getHalfDayEndTime()!=null && !empApplicantDetails.getHalfDayEndTime().isEmpty()){
					objform.setHalfDayEndTime(empApplicantDetails.getHalfDayEndTime().substring(0,2));
					objform.setHalfDayEndTimeMin(empApplicantDetails.getHalfDayEndTime().substring(3,5));
				}*/
								
				if(StringUtils.isNotEmpty(empApplicantDetails.getWorkEmail()) && empApplicantDetails.getWorkEmail()!=null){
					  objform.setOfficialEmail(empApplicantDetails.getWorkEmail());
				}
				if(empApplicantDetails.getWorkLocationId()!=null && empApplicantDetails.getWorkLocationId().getId()>0){
					  objform.setWorkLocationId(String.valueOf(empApplicantDetails.getWorkLocationId().getName()));
				}
				if(empApplicantDetails.getEmptype()!=null && empApplicantDetails.getEmptype().getId()>0){
					  objform.setEmptypeId(String.valueOf(empApplicantDetails.getEmptype().getName()));
				}
				
				if(empApplicantDetails.getEducationalDetailsSet()!=null){
					List<EmpQualificationLevelTo> fixed=null;
					if(objform.getEmployeeInfoTONew()!=null){
						if(objform.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
							fixed=objform.getEmployeeInfoTONew().getEmpQualificationFixedTo();
						}
						List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
						Set<EmpEducationalDetails> empEducationalDetailsSet=empApplicantDetails.getEducationalDetailsSet();
						Iterator<EmpEducationalDetails> iterator=empEducationalDetailsSet.iterator();
						while(iterator.hasNext()){
							EmpEducationalDetails empEducationalDetails=iterator.next();
							if(empEducationalDetails!=null){
								boolean flag=false;
								if(empEducationalDetails.getEmpQualificationLevel()!=null 
										&& empEducationalDetails.getEmpQualificationLevel().isFixedDisplay()!=null){
									flag=empEducationalDetails.getEmpQualificationLevel().isFixedDisplay();
									if(flag && fixed!=null){
										Iterator<EmpQualificationLevelTo> iterator2=fixed.iterator();
										while(iterator2.hasNext()){
											EmpQualificationLevelTo empQualificationLevelTo=iterator2.next();
											if(empQualificationLevelTo!=null && StringUtils.isNotEmpty(empQualificationLevelTo.getEducationId())){
												if(empEducationalDetails.getEmpQualificationLevel().getId()>0)
													if(empQualificationLevelTo.getEducationId().equalsIgnoreCase(String.valueOf(empEducationalDetails.getEmpQualificationLevel().getId()))){
														
														if(empEducationalDetails.getId()>0){
															empQualificationLevelTo.setQualification(empEducationalDetails.getEmpQualificationLevel().getName());
															}
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getCourse())){
															empQualificationLevelTo.setCourse(empEducationalDetails.getCourse());
														}
														
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getSpecialization())){
															empQualificationLevelTo.setSpecialization(empEducationalDetails.getSpecialization());
														}
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getGrade())){
															empQualificationLevelTo.setGrade(empEducationalDetails.getGrade());
														}
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getInstitute())){
															empQualificationLevelTo.setInstitute(empEducationalDetails.getInstitute());
														}
														
														if(empEducationalDetails.getYearOfCompletion()>0){
															empQualificationLevelTo.setYearOfComp(String.valueOf(empEducationalDetails.getYearOfCompletion()));
														}
													}
											}
										}
									}else{
										EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
										
										if(empEducationalDetails.getId()>0){
											empQualificationLevelTo.setQualification(empEducationalDetails.getEmpQualificationLevel().getName());
											}
										if(StringUtils.isNotEmpty(empEducationalDetails.getCourse())){
												empQualificationLevelTo.setCourse(empEducationalDetails.getCourse());
											}
											
											if(StringUtils.isNotEmpty(empEducationalDetails.getSpecialization())){
												empQualificationLevelTo.setSpecialization(empEducationalDetails.getSpecialization());
											}
											
											if(StringUtils.isNotEmpty(empEducationalDetails.getGrade())){
												empQualificationLevelTo.setGrade(empEducationalDetails.getGrade());
											}
											
											if(StringUtils.isNotEmpty(empEducationalDetails.getInstitute())){
												empQualificationLevelTo.setInstitute(empEducationalDetails.getInstitute());
											}
											
											if(empEducationalDetails.getYearOfCompletion()>0){
												empQualificationLevelTo.setYearOfComp(String.valueOf(empEducationalDetails.getYearOfCompletion()));
											}
											if(empEducationalDetails.getEmpQualificationLevel().getId()>0){
												empQualificationLevelTo.setEducationId(String.valueOf(empEducationalDetails.getEmpQualificationLevel().getId()));
											}
										level.add(empQualificationLevelTo);
									}
										
									}
								}
						}
						objform.getEmployeeInfoTONew().setEmpQualificationLevelTos(level);
					}
				}
			}
				
				
				
				
			if(empApplicantDetails.getEmpAcheivements()!=null)
			{
				Set<EmpAcheivement> empAcheivements=empApplicantDetails.getEmpAcheivements();
				if(empAcheivements != null && !empAcheivements.isEmpty())
				{
				Iterator<EmpAcheivement> iterator=empAcheivements.iterator();
				List<EmpAcheivementTO> empAcheivementTOs=new ArrayList<EmpAcheivementTO>();
				
				while(iterator.hasNext()){
					EmpAcheivement empAcheiv=iterator.next();
					if(empAcheiv!=null){
						EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
					
						if(empAcheiv.getId()>0)
						{
							empAcheivementTO.setId(empAcheiv.getId());
						}
						if(StringUtils.isNotEmpty(empAcheiv.getAcheivementName())){
							empAcheivementTO.setAcheivementName(empAcheiv.getAcheivementName());
						}
						
						if(StringUtils.isNotEmpty(empAcheiv.getDetails())){
							empAcheivementTO.setDetails(empAcheiv.getDetails());
						}
						
						empAcheivementTOs.add(empAcheivementTO);
					
				
			}
					}
				objform.getEmployeeInfoTONew().setEmpAcheivements(empAcheivementTOs);
				}
			else
			{
				
				List<EmpAcheivementTO> flist=new ArrayList<EmpAcheivementTO>();
				EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
				empAcheivementTO.setAcheivementName("");
				empAcheivementTO.setDetails("");
				objform.setAchievementListSize(String.valueOf(flist.size()));
				flist.add(empAcheivementTO);
				objform.getEmployeeInfoTONew().setEmpAcheivements(flist);
			}
			}
			
			if(empApplicantDetails.getEmpDependentses()!=null)
			{
				Set<EmpDependents> empDependents=empApplicantDetails.getEmpDependentses();
				if(empDependents != null && !empDependents.isEmpty())
				{
				Iterator<EmpDependents> iterator=empDependents.iterator();
				List<EmpDependentsTO> empDependentsTOs=new ArrayList<EmpDependentsTO>();
				
				while(iterator.hasNext()){
					EmpDependents empDepen=iterator.next();
					if(empDepen!=null){
						EmpDependentsTO empDepenTOs=new EmpDependentsTO();
					
						if(empDepen.getId()>0)
						{
							empDepenTOs.setId(String.valueOf(empDepen.getId()));
						}
						if(StringUtils.isNotEmpty(empDepen.getDependentName())){
							empDepenTOs.setDependantName(empDepen.getDependentName());
						}
						
						if(StringUtils.isNotEmpty(empDepen.getDependentRelationship())){
							empDepenTOs.setDependentRelationship(empDepen.getDependentRelationship());
						}
						
						if(empDepen.getDependantDOB()!=null){
							empDepenTOs.setDependantDOB(CommonUtil.ConvertStringToDateFormat(empDepen.getDependantDOB().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empDependentsTOs.add(empDepenTOs);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpDependentses(empDependentsTOs);
				}
			else
			{
				List<EmpDependentsTO> empDependentses=new ArrayList<EmpDependentsTO>();
				EmpDependentsTO empDependentsTO=new EmpDependentsTO();
				empDependentsTO.setDependantDOB("");
				empDependentsTO.setDependantName("");
				empDependentsTO.setDependentRelationship("");
				objform.setDependantsListSize(String.valueOf(empDependentses.size()));
				empDependentses.add(empDependentsTO);
				
				objform.getEmployeeInfoTONew().setEmpDependentses(empDependentses);
				
			}
			}
			
			if(empApplicantDetails.getEmpFeeConcession()!=null)
			{
				Set<EmpFeeConcession> empFeeConcession=empApplicantDetails.getEmpFeeConcession();
				if(empFeeConcession != null && !empFeeConcession.isEmpty())
				{
				Iterator<EmpFeeConcession> iterator=empFeeConcession.iterator();
				List<EmpFeeConcessionTO> empFeeConcessionTOs=new ArrayList<EmpFeeConcessionTO>();
				
				while(iterator.hasNext()){
					EmpFeeConcession empFeeConc=iterator.next();
					if(empFeeConc!=null){
						EmpFeeConcessionTO empFeeConcTO=new EmpFeeConcessionTO();
						if(empFeeConc.getId()>0)
						{
							empFeeConcTO.setId(empFeeConc.getId());
						}
						if(StringUtils.isNotEmpty(empFeeConc.getFeeConcessionDetails())){
							empFeeConcTO.setFeeConcessionDetails(empFeeConc.getFeeConcessionDetails());
						}
						
						if(StringUtils.isNotEmpty(empFeeConc.getFeeConcessionAmount())){
							empFeeConcTO.setFeeConcessionAmount(empFeeConc.getFeeConcessionAmount());
						}
						
						if(empFeeConc.getFeeConcessionDate()!=null){
							empFeeConcTO.setFeeConcessionDate(CommonUtil.ConvertStringToDateFormat(empFeeConc.getFeeConcessionDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empFeeConcessionTOs.add(empFeeConcTO);
					
				
			}
			}objform.getEmployeeInfoTONew().setEmpFeeConcession(empFeeConcessionTOs);
				}
			else
			{
				List<EmpFeeConcessionTO> list=new ArrayList<EmpFeeConcessionTO>();
				EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
				empFeeConcessionTO.setFeeConcessionAmount("");
				empFeeConcessionTO.setFeeConcessionDate("");
				empFeeConcessionTO.setFeeConcessionDetails("");
				objform.setFeeListSize(String.valueOf(list.size()));
				list.add(empFeeConcessionTO);
				objform.getEmployeeInfoTONew().setEmpFeeConcession(list);
				
			}
			}
			
			if(empApplicantDetails.getEmpFinancial()!=null)
			{
				Set<EmpFinancial> empFinancial=empApplicantDetails.getEmpFinancial();
				if(empFinancial != null && !empFinancial.isEmpty())
				{
				Iterator<EmpFinancial> iterator=empFinancial.iterator();
				List<EmpFinancialTO> empFinancialTOs=new ArrayList<EmpFinancialTO>();
				
				while(iterator.hasNext()){
					EmpFinancial empFinancial2=iterator.next();
					if(empFinancial2!=null){
						EmpFinancialTO empFinancialTO=new EmpFinancialTO();
						if(empFinancial2.getId()>0)
						{
							empFinancialTO.setId(empFinancial2.getId());
						}
						if(StringUtils.isNotEmpty(empFinancial2.getFinancialAmount())){
							empFinancialTO.setFinancialAmount(empFinancial2.getFinancialAmount());
						}
						
						if(StringUtils.isNotEmpty(empFinancial2.getFinancialDetails())){
							empFinancialTO.setFinancialDetails(empFinancial2.getFinancialDetails());
						}
						
						if(empFinancial2.getFinancialDate()!=null){
							empFinancialTO.setFinancialDate(CommonUtil.ConvertStringToDateFormat(empFinancial2.getFinancialDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empFinancialTOs.add(empFinancialTO);
					
				
			}
			}objform.getEmployeeInfoTONew().setEmpFinancial(empFinancialTOs);
				}
				else
				{
					List<EmpFinancialTO> flist=new ArrayList<EmpFinancialTO>();
					EmpFinancialTO empFinancialTO=new EmpFinancialTO();
					empFinancialTO.setFinancialAmount("");
					empFinancialTO.setFinancialDate("");
					empFinancialTO.setFinancialDetails("");
					objform.setFinancialListSize(String.valueOf(flist.size()));
					flist.add(empFinancialTO);
					objform.getEmployeeInfoTONew().setEmpFinancial(flist);
					
				}
			}
			
			if(empApplicantDetails.getEmpIncentives()!=null)
			{
				Set<EmpIncentives> empIncentives=empApplicantDetails.getEmpIncentives();
				if(empIncentives != null && !empIncentives.isEmpty())
				{
				Iterator<EmpIncentives> iterator=empIncentives.iterator();
				List<EmpIncentivesTO> empIncentivesTOs=new ArrayList<EmpIncentivesTO>();
				
				while(iterator.hasNext()){
					EmpIncentives empIncen=iterator.next();
					if(empIncen!=null){
						EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
					
						if(empIncen.getId()>0)
						{
							empIncentivesTO.setId(empIncen.getId());
						}
						if(StringUtils.isNotEmpty(empIncen.getIncentivesAmount())){
							empIncentivesTO.setIncentivesAmount(empIncen.getIncentivesAmount());
						}
						
						if(StringUtils.isNotEmpty(empIncen.getIncentivesDetails())){
							empIncentivesTO.setIncentivesDetails(empIncen.getIncentivesDetails());
						}
						
						if(empIncen.getIncentivesDate()!=null){
							empIncentivesTO.setIncentivesDate(CommonUtil.ConvertStringToDateFormat(empIncen.getIncentivesDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empIncentivesTOs.add(empIncentivesTO);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpIncentives(empIncentivesTOs);
				}
				else
				{
					List<EmpIncentivesTO> list=new ArrayList<EmpIncentivesTO>();
					EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
					empIncentivesTO.setIncentivesAmount("");
					empIncentivesTO.setIncentivesDate("");
					empIncentivesTO.setIncentivesDetails("");
					objform.setIncentivesListSize(String.valueOf(list.size()));
					list.add(empIncentivesTO);
					objform.getEmployeeInfoTONew().setEmpIncentives(list);
				}
			}
			if(empApplicantDetails.getEmpLoan()!=null)
			{
				Set<EmpLoan> empLoan=empApplicantDetails.getEmpLoan();
				if(empLoan != null && !empLoan.isEmpty())
				{
				Iterator<EmpLoan> iterator=empLoan.iterator();
				List<EmpLoanTO> empLoanTOs=new ArrayList<EmpLoanTO>();
				
				while(iterator.hasNext()){
					EmpLoan eLoan=iterator.next();
					if(eLoan!=null){
						EmpLoanTO eLoanTO=new EmpLoanTO();
						if(eLoan.getId()>0)
						{
							eLoanTO.setId(eLoan.getId());
						}
						
						if(StringUtils.isNotEmpty(eLoan.getLoanAmount())){
							eLoanTO.setLoanAmount(eLoan.getLoanAmount());
						}
						
						if(StringUtils.isNotEmpty(eLoan.getLoanDetails())){
							eLoanTO.setLoanDetails(eLoan.getLoanDetails());
						}
						
						if(eLoan.getLoanDate()!=null){
							eLoanTO.setLoanDate(CommonUtil.ConvertStringToDateFormat(eLoan.getLoanDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empLoanTOs.add(eLoanTO);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpLoan(empLoanTOs);
				}
				else
				{
					List<EmpLoanTO> list=new ArrayList<EmpLoanTO>();
					EmpLoanTO emploanTo=new EmpLoanTO();
					emploanTo.setLoanAmount("");
					emploanTo.setLoanDate("");
					emploanTo.setLoanDetails("");
					objform.setLoanListSize(String.valueOf(list.size()));
					list.add(emploanTo);
					objform.getEmployeeInfoTONew().setEmpLoan(list);
				}
			}
			
			
			if(empApplicantDetails.getEmpRemarks()!=null)
			{
				Set<EmpRemarks> empRemarks=empApplicantDetails.getEmpRemarks();
				if(empRemarks != null && !empRemarks.isEmpty())
				{
				Iterator<EmpRemarks> iterator=empRemarks.iterator();
				List<EmpRemarksTO> empRemarkTOs=new ArrayList<EmpRemarksTO>();
				
				while(iterator.hasNext()){
					EmpRemarks eRemarks=iterator.next();
					if(eRemarks!=null){
						EmpRemarksTO eRemarksTO=new EmpRemarksTO();
						if(eRemarks.getId()>0)
						{
							eRemarksTO.setId(eRemarks.getId());
						}
						if(StringUtils.isNotEmpty(eRemarks.getRemarksDetails())){
							eRemarksTO.setRemarkDetails(eRemarks.getRemarksDetails());
						}
						
						if(StringUtils.isNotEmpty(eRemarks.getRemarksEnteredBy())){
							eRemarksTO.setEnteredBy(eRemarks.getRemarksEnteredBy());
						}
						
						if(eRemarks.getRemarksDate()!=null){
							eRemarksTO.setRemarkDate(CommonUtil.ConvertStringToDateFormat(eRemarks.getRemarksDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empRemarkTOs.add(eRemarksTO);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpRemarks(empRemarkTOs);
				}
				else
				{
				List<EmpRemarksTO> flist=new ArrayList<EmpRemarksTO>();
				EmpRemarksTO empRemarksTO=new EmpRemarksTO();
				empRemarksTO.setEnteredBy("");
				empRemarksTO.setRemarkDate("");
				empRemarksTO.setRemarkDetails("");
				objform.setRemarksListSize(String.valueOf(flist.size()));
				flist.add(empRemarksTO);
				
				
				objform.getEmployeeInfoTONew().setEmpRemarks(flist);
					
					}
				}


		/*	if(empApplicantDetails.getEmpLeaves()!=null)
			{
				Set<EmpLeave> empLeaves=empApplicantDetails.getEmpLeaves();
				if(empLeaves != null && !empLeaves.isEmpty())
				{
				Iterator<EmpLeave> iterator=empLeaves.iterator();
 				List<EmpLeaveAllotTO> empLeaveTOs=new ArrayList<EmpLeaveAllotTO>();
				
				while(iterator.hasNext()){
					EmpLeave eLeave=iterator.next();
					if(eLeave!=null){
						EmpLeaveAllotTO eLeaveTO=new EmpLeaveAllotTO();
						
						if(eLeave.getId()>0)
						{
							eLeaveTO.setEmpLeaveId(eLeave.getId());
						}
						
					if(eLeave.getEmpLeaveType().getId()>0){
						EmpLeaveType leavetype=new EmpLeaveType();
						leavetype.setId(eLeave.getEmpLeaveType().getId());
						eLeaveTO.setEmpLeaveType(leavetype);
					}										
						
						if(StringUtils.isNotEmpty(eLeave.getEmpLeaveType().getName())){
							eLeaveTO.setLeaveType(eLeave.getEmpLeaveType().getName());
						}
						
						if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesAllocated()))){
							eLeaveTO.setAllottedLeave(String.valueOf(eLeave.getLeavesAllocated()));
						}
						if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesSanctioned()))){
							eLeaveTO.setSanctionedLeave(String.valueOf(eLeave.getLeavesSanctioned()));
						}
												
						empLeaveTOs.add(eLeaveTO);
						}
			     }
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
				}
			else
			{
				String empTypeId=null;
				if(empApplicantDetails.getEmptype()!=null && empApplicantDetails.getEmptype().getId()>0)
				{
				empTypeId=String.valueOf(empApplicantDetails.getEmptype().getId());
				List<EmpLeaveAllotTO> empLeaveToList;
				try {
				empLeaveToList = EmployeeViewHandler.getInstance().getEmpLeaveList(empTypeId);
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}

		}*/
			//Leaves............................................
			
		/*	if(empApplicantDetails.getEmpLeaves()!=null)
			{
				int month=txn.getInitializationMonth(empApplicantDetails.getId());
				int currentMonth=currentMonth();
				int year=Calendar.getInstance().get(Calendar.YEAR);
				int year1=0;
				Set<EmpLeave> empLeaves=empApplicantDetails.getEmpLeaves();
				List<EmpLeaveAllotTO> extEmpLeaveTos = new ArrayList<EmpLeaveAllotTO>();
				List<EmpLeaveAllotTO> empLeaveTOs=new ArrayList<EmpLeaveAllotTO>();
				if(empLeaves != null && !empLeaves.isEmpty())
				{
				Iterator<EmpLeave> iterator=empLeaves.iterator();
 				
				
				while(iterator.hasNext()){
					EmpLeave eLeave=iterator.next();
					EmpLeaveAllotTO eLeaveTO=new EmpLeaveAllotTO();
					if(eLeave!=null){
						if(month==6 && currentMonth < month && year > eLeave.getYear()){
						      year1=year-1;
					     }
						if(eLeave.getYear()==year1){
						   if(eLeave.getId()>0)
						   {
						 	  eLeaveTO.setEmpLeaveId(eLeave.getId());
						   }
						
					       if(eLeave.getEmpLeaveType().getId()>0){
						      EmpLeaveType leavetype=new EmpLeaveType();
						      leavetype.setId(eLeave.getEmpLeaveType().getId());
						      eLeaveTO.setEmpLeaveType(leavetype);
					       }										
						
						   if(StringUtils.isNotEmpty(eLeave.getEmpLeaveType().getName())){
							   eLeaveTO.setLeaveType(eLeave.getEmpLeaveType().getName());
						   }
						
						   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesAllocated()))){
							   eLeaveTO.setAllottedLeave(String.valueOf(eLeave.getLeavesAllocated()));
						   }
						   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesSanctioned()))){
							   eLeaveTO.setSanctionedLeave(String.valueOf(eLeave.getLeavesSanctioned()));
						   }
						   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesRemaining()))){
							   eLeaveTO.setRemainingLeave(String.valueOf(eLeave.getLeavesRemaining()));
						   }
							if(StringUtils.isNotEmpty(String.valueOf(eLeave.getYear()))){
								eLeaveTO.setYear(eLeave.getYear());
							}
							String monthString = new DateFormatSymbols().getMonths()[month-1];

							if(StringUtils.isNotEmpty(monthString)){
								eLeaveTO.setMonth(monthString);
							}
							empLeaveTOs.add(eLeaveTO);
						}else{
							if(eLeave.getId()>0)
							   {
							 	  eLeaveTO.setEmpLeaveId(eLeave.getId());
							   }
							
						       if(eLeave.getEmpLeaveType().getId()>0){
							      EmpLeaveType leavetype=new EmpLeaveType();
							      leavetype.setId(eLeave.getEmpLeaveType().getId());
							      eLeaveTO.setEmpLeaveType(leavetype);
						       }										
							
							   if(StringUtils.isNotEmpty(eLeave.getEmpLeaveType().getName())){
								   eLeaveTO.setLeaveType(eLeave.getEmpLeaveType().getName());
							   }
							
							   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesAllocated()))){
								   eLeaveTO.setAllottedLeave(String.valueOf(eLeave.getLeavesAllocated()));
							   }
							   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesSanctioned()))){
								   eLeaveTO.setSanctionedLeave(String.valueOf(eLeave.getLeavesSanctioned()));
							   }
							   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesRemaining()))){
								   eLeaveTO.setRemainingLeave(String.valueOf(eLeave.getLeavesRemaining()));
							   }
								if(StringUtils.isNotEmpty(String.valueOf(eLeave.getYear()))){
									eLeaveTO.setYear(eLeave.getYear());
								}
								String monthString = new DateFormatSymbols().getMonths()[month-1];

								if(StringUtils.isNotEmpty(monthString)){
									eLeaveTO.setMonth(monthString);
								}
								extEmpLeaveTos.add(eLeaveTO);
						}
						
						}
			     }
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
				objform.setEmpLeaveAllotTo(extEmpLeaveTos);
				}
			else
			{
				String empTypeId=null;
				if(objform.getEmptypeId()!=null && !objform.getEmptypeId().isEmpty())
				{
				empTypeId=objform.getEmptypeId();
				List<EmpLeaveAllotTO> empLeaveToList;
				try {
				empLeaveToList = EmployeeViewHandler.getInstance().getEmpLeaveList(empTypeId,objform);
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			
			}*/


			
			
			
			
			if (empApplicantDetails.getEmpLeaves() != null) {
				int month=0;
				if(empApplicantDetails.getEmptype()!=null)
				month = txn.getInitializationMonth(empApplicantDetails.getEmptype().getId());
				int currentMonth = currentMonth();
				int year = Calendar.getInstance().get(Calendar.YEAR);
				int year1 = 0;
				Set<EmpLeave> empLeaves = empApplicantDetails.getEmpLeaves();
				List<EmpLeaveAllotTO> extEmpLeaveTos = new ArrayList<EmpLeaveAllotTO>();
				if (empLeaves != null && !empLeaves.isEmpty()) {
					Iterator<EmpLeave> iterator = empLeaves.iterator();
					List<EmpLeaveAllotTO> empLeaveTOs = new ArrayList<EmpLeaveAllotTO>();

					while (iterator.hasNext()) {
						EmpLeave eLeave = iterator.next();
						EmpLeaveAllotTO eLeaveTO = new EmpLeaveAllotTO();
						if (eLeave != null) {
							if (month == 6 && currentMonth < month
									&& year > eLeave.getYear()) {
								year1 = year - 1;
							} else {
								year1 = year;
							}
							if (eLeave.getYear() == year1) {
								if (eLeave.getId() > 0) {
									eLeaveTO.setEmpLeaveId(eLeave.getId());
								}

								if (eLeave.getEmpLeaveType().getId() > 0) {
									EmpLeaveType leavetype = new EmpLeaveType();
									leavetype.setId(eLeave.getEmpLeaveType()
											.getId());
									eLeaveTO.setEmpLeaveType(leavetype);
								}

								if (StringUtils.isNotEmpty(eLeave.getEmpLeaveType()
										.getName())) {
									eLeaveTO.setLeaveType(eLeave.getEmpLeaveType()
											.getName());
								}

								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesAllocated()))) {
									eLeaveTO.setAllottedLeave(String.valueOf(eLeave
											.getLeavesAllocated()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesSanctioned()))) {
									eLeaveTO.setSanctionedLeave(String
											.valueOf(eLeave.getLeavesSanctioned()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesRemaining()))) {
									eLeaveTO.setRemainingLeave(String
											.valueOf(eLeave.getLeavesRemaining()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getYear()))) {
									eLeaveTO.setYear(eLeave.getYear());
								}
								String monthString = new DateFormatSymbols()
										.getMonths()[month - 1];

								if (StringUtils.isNotEmpty(monthString)) {
									eLeaveTO.setMonth(monthString);
								}
								empLeaveTOs.add(eLeaveTO);
							} else {
								if (eLeave.getId() > 0) {
									eLeaveTO.setEmpLeaveId(eLeave.getId());
								}

								if (eLeave.getEmpLeaveType().getId() > 0) {
									EmpLeaveType leavetype = new EmpLeaveType();
									leavetype.setId(eLeave.getEmpLeaveType()
											.getId());
									eLeaveTO.setEmpLeaveType(leavetype);
								}

								if (StringUtils.isNotEmpty(eLeave.getEmpLeaveType()
										.getName())) {
									eLeaveTO.setLeaveType(eLeave.getEmpLeaveType()
											.getName());
								}

								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesAllocated()))) {
									eLeaveTO.setAllottedLeave(String.valueOf(eLeave
											.getLeavesAllocated()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesSanctioned()))) {
									eLeaveTO.setSanctionedLeave(String
											.valueOf(eLeave.getLeavesSanctioned()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesRemaining()))) {
									eLeaveTO.setRemainingLeave(String
											.valueOf(eLeave.getLeavesRemaining()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getYear()))) {
									eLeaveTO.setYear(eLeave.getYear());
								}
								String monthString = new DateFormatSymbols()
										.getMonths()[month - 1];

								if (StringUtils.isNotEmpty(monthString)) {
									eLeaveTO.setMonth(monthString);
								}
								extEmpLeaveTos.add(eLeaveTO);
							}

						}
					}
					Collections.sort(empLeaveTOs);
					objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
					objform.setEmpLeaveAllotTo(extEmpLeaveTos);
				} else {
					String empTypeId = null;
					if (objform.getEmptypeId() != null
							&& !objform.getEmptypeId().isEmpty()) {
						empTypeId = objform.getEmptypeId();
						List<EmpLeaveAllotTO> empLeaveToList;
						try {
							empLeaveToList = EmployeeViewHandler.getInstance().getEmpLeaveList(empTypeId, objform);
							Collections.sort(empLeaveToList);
							objform.getEmployeeInfoTONew().setEmpLeaveToList(
									empLeaveToList);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
			
			
			
			
			
			
			
			
			
			
			
			
			
//Leaves Code Ends.....................................
			if(empApplicantDetails.getEmpImmigrations()!=null)
			{
				Set<EmpImmigration> empImmigration=empApplicantDetails.getEmpImmigrations();
				Iterator<EmpImmigration> iterator=empImmigration.iterator();
				List<EmpImmigrationTO> empImmigrationTOs=new ArrayList<EmpImmigrationTO>();
				
				while(iterator.hasNext()){
					EmpImmigration eImmigration=iterator.next();
					if(eImmigration!=null){
						EmpImmigrationTO eImmigrationTO=new EmpImmigrationTO();
						
						
						if(eImmigration.getId()>0)
						{
							eImmigrationTO.setId(String.valueOf(eImmigration.getId()));
						}
						if(StringUtils.isNotEmpty(eImmigration.getPassportComments())){
							eImmigrationTO.setPassportComments(eImmigration.getPassportComments());
						}
						
						if(StringUtils.isNotEmpty(eImmigration.getPassportNo())){
							eImmigrationTO.setPassportNo(eImmigration.getPassportNo());
						}
						if(StringUtils.isNotEmpty(eImmigration.getPassportReviewStatus())){
							eImmigrationTO.setPassportReviewStatus(eImmigration.getPassportReviewStatus());
						}
						if(StringUtils.isNotEmpty(eImmigration.getPassportStatus())){
							eImmigrationTO.setPassportStatus(eImmigration.getPassportStatus());
						}
						if(eImmigration.getCountryByPassportCountryId()!=null && eImmigration.getCountryByPassportCountryId().getId()>0){
							
							eImmigrationTO.setPassportCountryId(String.valueOf(eImmigration.getCountryByPassportCountryId().getName()));
						}
						if(eImmigration.getPassportDateOfExpiry()!=null){
							eImmigrationTO.setPassportExpiryDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getPassportDateOfExpiry().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(eImmigration.getPassportIssueDate()!=null){
							eImmigrationTO.setPassportIssueDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getPassportIssueDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaComments())){
							eImmigrationTO.setVisaComments(eImmigration.getVisaComments());
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaNo())){
							eImmigrationTO.setVisaNo(eImmigration.getVisaNo());
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaReviewStatus())){
							eImmigrationTO.setVisaReviewStatus(eImmigration.getVisaReviewStatus());
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaStatus())){
							eImmigrationTO.setVisaStatus(eImmigration.getVisaStatus());
						}
						if(eImmigration.getCountryByVisaCountryId()!=null && eImmigration.getCountryByVisaCountryId().getId()>0){
						
							eImmigrationTO.setVisaCountryId(String.valueOf(eImmigration.getCountryByVisaCountryId().getName()));
						}
						if(eImmigration.getVisaDateOfExpiry()!=null){
							eImmigrationTO.setVisaExpiryDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getVisaDateOfExpiry().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(eImmigration.getVisaIssueDate()!=null){
							eImmigrationTO.setVisaIssueDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getVisaIssueDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empImmigrationTOs.add(eImmigrationTO);
					
				
			}
					objform.getEmployeeInfoTONew().setEmpImmigration(empImmigrationTOs);	
				}
				}
			
			
			
			if(empApplicantDetails.getEmpPayAllowance()!=null)
			{
				Set<EmpPayAllowanceDetails> empPayAllowanceDetails=empApplicantDetails.getEmpPayAllowance();
				if(empPayAllowanceDetails != null && !empPayAllowanceDetails.isEmpty())
				{
				Iterator<EmpPayAllowanceDetails> iterator=empPayAllowanceDetails.iterator();
				//List<EmpPayAllowanceTO> empPayAllowanceTOs=new ArrayList<EmpPayAllowanceTO>();
				List<EmpAllowanceTO> empPayAllowanceTOs=new ArrayList<EmpAllowanceTO>();
				
				List<EmpAllowanceTO> fixed=null;
				if(objform.getEmployeeInfoTONew()!=null){
				if(objform.getEmployeeInfoTONew().getPayscaleFixedTo()!=null){
				fixed=objform.getEmployeeInfoTONew().getPayscaleFixedTo();
				}
				
				//
				while(iterator.hasNext()){
					//EmpPayAllowanceTO ePayAllotTO=new EmpPayAllowanceTO();
					EmpPayAllowanceDetails ePayAlloDet=iterator.next();
					EmpAllowanceTO eAllotTO=new EmpAllowanceTO();
					if(ePayAlloDet!=null){
						
						if(fixed!=null){
							Iterator<EmpAllowanceTO> iterator2=fixed.iterator();
							while(iterator2.hasNext()){
								EmpAllowanceTO empAllTO=iterator2.next();
							if(empAllTO!=null && (empAllTO.getId()>0)){
							if(ePayAlloDet.getEmpAllowance()!= null && ePayAlloDet.getEmpAllowance().getId()>0){
								if(empAllTO.getId()==(ePayAlloDet.getEmpAllowance().getId())){
									eAllotTO.setId(ePayAlloDet.getEmpAllowance().getId());	
									//check the above line......
									if(ePayAlloDet.getId()>0){
										//ePayAllotTO.setId(ePayAlloDet.getId());
										eAllotTO.setEmpPayAllowanceId(ePayAlloDet.getId());
									}
									
									if(StringUtils.isNotEmpty(ePayAlloDet.getAllowanceValue())){
										eAllotTO.setAllowanceName(ePayAlloDet.getAllowanceValue());
										//ePayAllotTO.setAllowanceValue(eAllotTO.getAllowanceName());
									}
									
									if(StringUtils.isNotEmpty(String.valueOf(empAllTO.getName()))){
										eAllotTO.setName(empAllTO.getName());
										
									}
									eAllotTO.setDisplayOrder(empAllTO.getDisplayOrder());
									empPayAllowanceTOs.add(eAllotTO);
									
								}
							}
			     }
							}
						}
					}
				}
				Collections.sort(empPayAllowanceTOs,new PayAllowance());
				objform.getEmployeeInfoTONew().setPayscaleFixedTo(empPayAllowanceTOs);
				}
		
				}
				else
				{
					 try {
						 List<EmpAllowanceTO> payscaleFixedTo=txn.getPayAllowanceFixedMap();
					
					 if(payscaleFixedTo!=null){
						 objform.getEmployeeInfoTONew().setPayscaleFixedTo(payscaleFixedTo);
					 }
					 } catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
				}
				}
			
			
			
			
	
			if(empApplicantDetails.getPreviousExpSet()!=null){
				int teachingFlag=0;
				int industryFlag=0;
				Set<EmpPreviousExperience> empOnlinePreviousExperiencesSet=empApplicantDetails.getPreviousExpSet();
				if(empOnlinePreviousExperiencesSet != null && !empOnlinePreviousExperiencesSet.isEmpty()){
					
					Iterator<EmpPreviousExperience> iterator=empOnlinePreviousExperiencesSet.iterator();
					List<EmpPreviousOrgTo> industryExp=new ArrayList<EmpPreviousOrgTo>();
					List<EmpPreviousOrgTo> teachingExp=new ArrayList<EmpPreviousOrgTo>();
					while(iterator.hasNext()){
						EmpPreviousExperience empOnlinePreviousExperiences=iterator.next();
						if(empOnlinePreviousExperiences!=null){
							EmpPreviousOrgTo empOnliPreviousExperienceTO=new EmpPreviousOrgTo();
							
							if(empOnlinePreviousExperiences.isIndustryExperience())
							{
								if(empOnlinePreviousExperiences.getId()>0){
									empOnliPreviousExperienceTO.setId(empOnlinePreviousExperiences.getId());
								}
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpDesignation())){
									empOnliPreviousExperienceTO.setCurrentDesignation(empOnlinePreviousExperiences.getEmpDesignation());
								}
								
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpOrganization())){
									empOnliPreviousExperienceTO.setCurrentOrganisation(empOnlinePreviousExperiences.getEmpOrganization());
								}
								
								if( empOnlinePreviousExperiences.getExpMonths()>0 ){
									empOnliPreviousExperienceTO.setIndustryExpMonths(String.valueOf(empOnlinePreviousExperiences.getExpMonths()));
								}
								
								if(empOnlinePreviousExperiences.getExpYears()>0){
									empOnliPreviousExperienceTO.setIndustryExpYears(String.valueOf(empOnlinePreviousExperiences.getExpYears()));
								}
								/*code added by sudhir */
								if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
									empOnliPreviousExperienceTO.setIndustryFromDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getFromDate()));
								}
								if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
									empOnliPreviousExperienceTO.setIndustryToDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getToDate()));
								}
									/*code added by sudhir */
								industryFlag=1;
								industryExp.add(empOnliPreviousExperienceTO);
							}else if(empOnlinePreviousExperiences.isTeachingExperience())
							{
								if(empOnlinePreviousExperiences.getId()>0){
									empOnliPreviousExperienceTO.setId(empOnlinePreviousExperiences.getId());
								}
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpDesignation())){
									empOnliPreviousExperienceTO.setCurrentTeachnigDesignation(empOnlinePreviousExperiences.getEmpDesignation());
								}
								
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpOrganization())){
									empOnliPreviousExperienceTO.setCurrentTeachingOrganisation(empOnlinePreviousExperiences.getEmpOrganization());
								}
								
								if( empOnlinePreviousExperiences.getExpMonths()>0 ){
									empOnliPreviousExperienceTO.setTeachingExpMonths(String.valueOf(empOnlinePreviousExperiences.getExpMonths()));
								}
								
								if(empOnlinePreviousExperiences.getExpYears()>0){
									empOnliPreviousExperienceTO.setTeachingExpYears(String.valueOf(empOnlinePreviousExperiences.getExpYears()));
								}
								/*code added by sudhir */
								if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
									empOnliPreviousExperienceTO.setTeachingFromDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getFromDate()));
								}
								if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
									empOnliPreviousExperienceTO.setTeachingToDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getToDate()));
								}
									/*code added by sudhir */
								teachingFlag=1;
								teachingExp.add(empOnliPreviousExperienceTO);
																
							}
						}
							objform.getEmployeeInfoTONew().setExperiences(industryExp);
							objform.getEmployeeInfoTONew().setTeachingExperience(teachingExp);
					}
				}else {
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					
					List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					/*code added by sudhir */
					empPreviousOrgTo.setTeachingFromDate("");
					empPreviousOrgTo.setTeachingToDate("");
					/*--------------------- */
					objform.setTeachingExpLength(String.valueOf(teachingList.size()));
					teachingList.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
				}
				if(teachingFlag==1 && industryFlag==0)
				{
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
				}
				if(teachingFlag==0 && industryFlag==1)
				{
					List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					/*code added by sudhir */
					empPreviousOrgTo.setTeachingFromDate("");
					empPreviousOrgTo.setTeachingToDate("");
					/*--------------------- */
					objform.setTeachingExpLength(String.valueOf(teachingList.size()));
					teachingList.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
				}
				if(teachingFlag==1 && industryFlag==0)
				{
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
				}
				if(teachingFlag==0 && industryFlag==0)
				{
					List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					/*code added by sudhir */
					empPreviousOrgTo.setTeachingFromDate("");
					empPreviousOrgTo.setTeachingToDate("");
					/*--------------------- */
					objform.setTeachingExpLength(String.valueOf(teachingList.size()));
					teachingList.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
					
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
				}
			}
			/*----------------------------------- code added by sudhir---------------------------------------*/
			/* calculating Experience in cjc (or) cu from joining Date to present day*/
			Date joiningDate = null;
			String fromDateDay = null;
			String fromDateMonth = null;
			if(objform.getDateOfJoining()!=null && !objform.getDateOfJoining().isEmpty()){
				 joiningDate = CommonUtil.ConvertStringToDate(objform.getDateOfJoining());
				 fromDateDay = objform.getDateOfJoining().substring(0, 2);
				 fromDateMonth = objform.getDateOfJoining().substring(3, 5);
			}
			String todaysDate = CommonUtil.getTodayDate();
			String toDateDay = todaysDate.substring(0, 2);
			String toDateMonth = todaysDate.substring(3, 5);
			Date toDate = CommonUtil.ConvertStringToDate(todaysDate);
			if(joiningDate!=null && toDate!=null){
				
			/*int years = CommonUtil.getYearsDiff(joiningDate,toDate);
			int months = CommonUtil.getMonthsBetweenTwoYears(joiningDate, toDate,fromDate,toDateNumber);*/
				double msPerGregorianYear = 365.25 * 86400 * 1000;
			 	double years1 =(toDate.getTime() - joiningDate.getTime()) / msPerGregorianYear;
			 	int yy = (int) years1;
		        int mm = (int) ((years1 - yy) * 12);
		        if(fromDateDay.equals(toDateDay)){
		        	if(fromDateMonth.equals(toDateMonth)){
		        		mm=0;
		        		yy = (int) Math.round(years1);
		        	}
		        }
		        if(fromDateDay.equals(toDateDay)){
		        	if(!fromDateMonth.equals(toDateMonth)){
		        		mm = (int) Math.round(((years1 - yy) * 12));
		        	}
		        }
			objform.setExperienceInYears(yy);
			objform.setExperienceInMonths(mm);
			}
			/* calculating TotalCurrent Experience Years and Months based on Recognised Experience and Experience in cjc (or) cu
			int totalYears = Integer.parseInt(objform.getRelevantExpYears())+objform.getExperienceInYears();
			int totalMonths1 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getExperienceInMonths() % 12;
			int totalMonths2 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getExperienceInMonths() / 12;
			totalYears = totalYears + totalMonths2;
			objform.setTotalCurrentExpYears(totalYears);
			objform.setTotalCurrentExpMonths(totalMonths1);*/
			
			/*-----------------------------------------------------------------------------------------------*/
			if(empApplicantDetails.getExtensionNumber()!=null && !empApplicantDetails.getExtensionNumber().isEmpty()){
				objform.setExtensionNumber(empApplicantDetails.getExtensionNumber());
			}
			
			//For Showing Employee Information New by Bhargav 
			EmployeeInfoEditNewTO employeeInfoEditNewTO = new EmployeeInfoEditNewTO();
			if (empApplicantDetails.getEmpAcademicDeatils()  != null) {
				Set<EmpAcademicQualificationBO> empAcademic = empApplicantDetails
						.getEmpAcademicDeatils();
				if (empAcademic != null && !empAcademic.isEmpty()) {
					Iterator<EmpAcademicQualificationBO> iterator = empAcademic.iterator();
					List<EmpAcademicQualificationTO> empAcademicQualificationTOs = new ArrayList<EmpAcademicQualificationTO>();

					while (iterator.hasNext()) {
						EmpAcademicQualificationBO empAcademicbo = iterator.next();
						if (empAcademic != null) {
							EmpAcademicQualificationTO empAcademicTo = new EmpAcademicQualificationTO();

							if (empAcademicTo.getId() > 0) {
								empAcademicTo.setId(empAcademicbo.getId());
							}
							if (StringUtils.isNotEmpty(empAcademicbo
									.getCourseName())) {
								empAcademicTo.setCourseName(empAcademicbo
										.getCourseName());
							}

							if (StringUtils.isNotEmpty(empAcademicbo.getUniversity())) {
								empAcademicTo.setUniversityName(empAcademicbo.getUniversity());
							}
							if (StringUtils.isNotEmpty(empAcademicbo.getYear())) {
								empAcademicTo.setYear(empAcademicbo.getYear());
							}
							if (StringUtils.isNotEmpty(empAcademicbo.getGrade())) {
								empAcademicTo.setGrade(empAcademicbo.getGrade());
							}


							empAcademicQualificationTOs.add(empAcademicTo);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpqualification(empAcademicQualificationTOs);
					employeeInfoEditNewTO.setEmpqualification(empAcademicQualificationTOs);
				} 
			}
			
			if (empApplicantDetails.getEmpIntrestDetails()  != null) {
				Set<EmpIntrestsBO> empIntrestsBOs =empApplicantDetails.getEmpIntrestDetails();
				if (empIntrestsBOs != null && !empIntrestsBOs.isEmpty()) {
					Iterator<EmpIntrestsBO> iterator = empIntrestsBOs.iterator();
					List<EmpInterestsTO> empInterestsTOs = new ArrayList<EmpInterestsTO>();

					while (iterator.hasNext()) {
						EmpIntrestsBO empIntrestsBO = iterator.next();
						if (empIntrestsBO != null) {
							EmpInterestsTO empInterestsTO = new EmpInterestsTO();

							if (empInterestsTO.getId() > 0) {
								empInterestsTO.setId(empIntrestsBO.getId());
							}
							if (StringUtils.isNotEmpty(empIntrestsBO
									.getTopic())) {
								empInterestsTO.setTopic(empIntrestsBO
										.getTopic());
							}

							empInterestsTOs.add(empInterestsTO);

						}
					}
//				objform.getEmployeeInfoEditNewTO().setInterest(empInterestsTOs);
					employeeInfoEditNewTO.setInterest(empInterestsTOs);
				}
			}
			
			
			if (empApplicantDetails.getEmpResearchDetails()  != null) {
				Set<EmpResearchBo> empResearchBos =empApplicantDetails.getEmpResearchDetails();
				if (empResearchBos != null && !empResearchBos.isEmpty()) {
					Iterator<EmpResearchBo> iterator = empResearchBos.iterator();
					List<EmpFieldResearchTO> empResearchTOs = new ArrayList<EmpFieldResearchTO>();

					while (iterator.hasNext()) {
						EmpResearchBo empResearchBo = iterator.next();
						if (empResearchBo != null) {
							EmpFieldResearchTO empResearchTO = new EmpFieldResearchTO();

							if (empResearchTO.getId() > 0) {
								empResearchTO.setId(empResearchBo.getId());
							}
							if (StringUtils.isNotEmpty(empResearchBo
									.getTitle())) {
								empResearchTO.setTopic(empResearchBo
										.getTitle());
							}

							empResearchTOs.add(empResearchTO);

						}
					}
//				objform.getEmployeeInfoEditNewTO().setResearch(empResearchTOs);
					employeeInfoEditNewTO.setResearch(empResearchTOs);
				} 
			}
			
			
			if (empApplicantDetails.getEmpGuideshipDetails()  != null) {
				Set<EmpGuideshipDetailsBo> empGuideshipDetailsBos =empApplicantDetails.getEmpGuideshipDetails();
				if (empGuideshipDetailsBos != null && !empGuideshipDetailsBos.isEmpty()) {
					Iterator<EmpGuideshipDetailsBo> iterator = empGuideshipDetailsBos.iterator();
					List<EmpGuideShipDetailsTO> empGuideShipDetailsTOs = new ArrayList<EmpGuideShipDetailsTO>();

					while (iterator.hasNext()) {
						EmpGuideshipDetailsBo empGuideshipDetailsBo = iterator.next();
						if (empGuideshipDetailsBo != null) {
							EmpGuideShipDetailsTO empGuideShipDetailsTO = new EmpGuideShipDetailsTO();

							if (empGuideShipDetailsTO.getId() > 0) {
								empGuideShipDetailsTO.setId(empGuideshipDetailsBo.getId());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getScholarName())) {
								empGuideShipDetailsTO.setPhdScholarName(empGuideshipDetailsBo
										.getScholarName());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getRegistraionYear())) {
								empGuideShipDetailsTO.setRegistrationYear(empGuideshipDetailsBo
										.getRegistraionYear());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getAwarded())) {
								empGuideShipDetailsTO.setAwarded(empGuideshipDetailsBo
										.getAwarded());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getAwardedYear())) {
								empGuideShipDetailsTO.setYear(empGuideshipDetailsBo
										.getAwardedYear());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getAwardedThesisName())) {
								empGuideShipDetailsTO.setThesisName(empGuideshipDetailsBo
										.getAwardedThesisName());
							}

							empGuideShipDetailsTOs.add(empGuideShipDetailsTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setGuideship(empGuideShipDetailsTOs);
					employeeInfoEditNewTO.setGuideship(empGuideShipDetailsTOs);
				} 
			}
			
			if (empApplicantDetails.getEmpDutiesDeatils()  != null) {
				Set<EmpDutiesDetailsBO> empDutiesDetailsBOs =empApplicantDetails.getEmpDutiesDeatils();
				if (empDutiesDetailsBOs != null && !empDutiesDetailsBOs.isEmpty()) {
					Iterator<EmpDutiesDetailsBO> iterator = empDutiesDetailsBOs.iterator();
					List<EmpDutiesPerformedTO> empDutiesPerformedTOs = new ArrayList<EmpDutiesPerformedTO>();

					while (iterator.hasNext()) {
						EmpDutiesDetailsBO empDutiesDetailsBO = iterator.next();
						if (empDutiesDetailsBO != null) {
							EmpDutiesPerformedTO empDutiesPerformedTO = new EmpDutiesPerformedTO();

							if (empDutiesPerformedTO.getId() > 0) {
								empDutiesPerformedTO.setId(empDutiesDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empDutiesDetailsBO
									.getPosition())) {
								empDutiesPerformedTO.setPositionName(empDutiesDetailsBO
										.getPosition());
							}
							if (empDutiesDetailsBO
									.getFromDate() != null) {
								empDutiesPerformedTO.setFromDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							if (empDutiesDetailsBO.getToDate() != null) {
								empDutiesPerformedTO.setToDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							

							empDutiesPerformedTOs.add(empDutiesPerformedTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setDuties(empDutiesPerformedTOs);
					employeeInfoEditNewTO.setDuties(empDutiesPerformedTOs);
				} 
			}
			
			if (empApplicantDetails.getEmpProjectDetails()  != null) {
				Set<EmpProjectResearchDetailsBO> empProjectResearchDetailsBOs =empApplicantDetails.getEmpProjectDetails();
				if (empProjectResearchDetailsBOs != null && !empProjectResearchDetailsBOs.isEmpty()) {
					Iterator<EmpProjectResearchDetailsBO> iterator = empProjectResearchDetailsBOs.iterator();
					List<EmpResearchTO> empResearchProjectTOs = new ArrayList<EmpResearchTO>();

					while (iterator.hasNext()) {
						EmpProjectResearchDetailsBO empResearchDetailsBO = iterator.next();
						if (empResearchDetailsBO != null) {
							EmpResearchTO empResearchProjectTO = new EmpResearchTO();

							if (empResearchProjectTO.getId() > 0) {
								empResearchProjectTO.setId(empResearchDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle()
									)) {
								empResearchProjectTO.setProjectName(empResearchDetailsBO.getPrjectName());
							}
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle())) {
						       empResearchProjectTO.setTitle(empResearchDetailsBO.getTitle());
					        }
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getFundingAgency())) {
						   empResearchProjectTO.setFindingAgencyName(empResearchDetailsBO.getFundingAgency());
					        }
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getAmount())) {
						      empResearchProjectTO.setAmount(empResearchDetailsBO.getAmount());
					           }
							if (empResearchDetailsBO
									.getFromDate() != null) {
								empResearchProjectTO.setFromDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO
										.getFromDate() .toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							if (empResearchDetailsBO.getToDate() != null) {
								empResearchProjectTO.setToDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							

							empResearchProjectTOs.add(empResearchProjectTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setResearchProject(empResearchProjectTOs);
					employeeInfoEditNewTO.setResearchProject(empResearchProjectTOs);
				} 
			}
			
			if (empApplicantDetails.getEmpResearchPublication() != null) {
				Set<EmpReaserchPublishcationDetailsBO> empProjePublishcationDetailsBOs =empApplicantDetails.getEmpResearchPublication();
				if (empProjePublishcationDetailsBOs != null && !empProjePublishcationDetailsBOs.isEmpty()) {
					Iterator<EmpReaserchPublishcationDetailsBO> iterator = empProjePublishcationDetailsBOs.iterator();
					List<EmpResearchPublicationTO> empResearchPublicationTOs = new ArrayList<EmpResearchPublicationTO>();

					while (iterator.hasNext()) {
						EmpReaserchPublishcationDetailsBO empReaserchPublishcationDetailsBO = iterator.next();
						if (empReaserchPublishcationDetailsBO != null) {
							EmpResearchPublicationTO empResearchPublicationTO = new EmpResearchPublicationTO();

							if (empResearchPublicationTO.getId() > 0) {
								empResearchPublicationTO.setId(empReaserchPublishcationDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getTitle())) {
								empResearchPublicationTO.setPaperTitle(empReaserchPublishcationDetailsBO.getTitle());
							}
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getJournalName())) {
								empResearchPublicationTO.setJournalName(empReaserchPublishcationDetailsBO.getJournalName());
					        }
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getUGC())) {
								empResearchPublicationTO.setUGCNonUGC(empReaserchPublishcationDetailsBO.getUGC());
					        }
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getISBNISSNNo())) {
								empResearchPublicationTO.setISBNISSNNo(empReaserchPublishcationDetailsBO.getISBNISSNNo());
					        }
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getYear())) {
								empResearchPublicationTO.setYear(empReaserchPublishcationDetailsBO.getYear());
					        }
							
							empResearchPublicationTOs.add(empResearchPublicationTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setResearchPubliction(empResearchPublicationTOs);
					employeeInfoEditNewTO.setResearchPubliction(empResearchPublicationTOs);
				} 
			}
			
			if (empApplicantDetails.getEmpBooksPublished() != null) {
				Set<EmpBooksPublishedDetailsBO> empBooksPublishedDetailsBOs =empApplicantDetails.getEmpBooksPublished();
				if (empBooksPublishedDetailsBOs != null && !empBooksPublishedDetailsBOs.isEmpty()) {
					Iterator<EmpBooksPublishedDetailsBO> iterator = empBooksPublishedDetailsBOs.iterator();
					List<EmpBooksPublishedTO> empBooksPublishedTOs = new ArrayList<EmpBooksPublishedTO>();

					while (iterator.hasNext()) {
						EmpBooksPublishedDetailsBO empBooksPublishedDetailsBO = iterator.next();
						if (empBooksPublishedDetailsBO != null) {
							EmpBooksPublishedTO empBooksPublishedTO = new EmpBooksPublishedTO();

							if (empBooksPublishedTO.getId() > 0) {
								empBooksPublishedTO.setId(empBooksPublishedDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getBookName())) {
								empBooksPublishedTO.setTitleName(empBooksPublishedDetailsBO.getBookName());
							}
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getPublisherName())) {
								empBooksPublishedTO.setPublisherName(empBooksPublishedDetailsBO.getPublisherName());
					        }
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getContribution())) {
								empBooksPublishedTO.setContibutionName(empBooksPublishedDetailsBO.getContribution());
					        }
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getYear())) {
								empBooksPublishedTO.setYear(empBooksPublishedDetailsBO.getYear());
					        }
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getISBNISSNNo())) {
								empBooksPublishedTO.setISBNISSN(empBooksPublishedDetailsBO.getISBNISSNNo());
					        }
							
							empBooksPublishedTOs.add(empBooksPublishedTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpBooks(empBooksPublishedTOs);
					employeeInfoEditNewTO.setEmpBooks(empBooksPublishedTOs);
				} 
			}
			
			if (empApplicantDetails.getEmpPaperPresentaion() != null) {
				Set<EmpPaperPresentationBO> empPaperPresentationBOs =empApplicantDetails.getEmpPaperPresentaion();
				if (empPaperPresentationBOs != null && !empPaperPresentationBOs.isEmpty()) {
					Iterator<EmpPaperPresentationBO> iterator = empPaperPresentationBOs.iterator();
					List<EmpPaperPresentationTO> empPresentationTOs = new ArrayList<EmpPaperPresentationTO>();

					while (iterator.hasNext()) {
						EmpPaperPresentationBO empPaperPresentationBO = iterator.next();
						if (empPaperPresentationBO != null) {
							EmpPaperPresentationTO empPaperPresentationTO = new EmpPaperPresentationTO();

							if (empPaperPresentationTO.getId() > 0) {
								empPaperPresentationTO.setId(empPaperPresentationBO.getId());
							}
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getInstitution())) {
								empPaperPresentationTO.setOrganisation(empPaperPresentationBO.getInstitution());
							}
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getInterRegoinal())) {
								empPaperPresentationTO.setRegional(empPaperPresentationBO.getInterRegoinal());
					        }
							if (empPaperPresentationBO.getDate() != null) {
								empPaperPresentationTO.setDate1(CommonUtil.ConvertStringToDateFormat(empPaperPresentationBO.getDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getProceedingsTile())) {
								empPaperPresentationTO.setSeminarName(empPaperPresentationBO.getProceedingsTile());
					        }
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getPaperTile())) {
								empPaperPresentationTO.setPaperName(empPaperPresentationBO.getPaperTile());
					        }
							
							empPresentationTOs.add(empPaperPresentationTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpPaper(empPresentationTOs);
					employeeInfoEditNewTO.setEmpPaper(empPresentationTOs);
				} 
			}
			
			if (empApplicantDetails.getEmpSeminarAttended() != null) {
				Set<EmpSeminarAttendedDetailsBO> empSeminarAttendedDetailsBOs =empApplicantDetails.getEmpSeminarAttended();
				if (empSeminarAttendedDetailsBOs != null && !empSeminarAttendedDetailsBOs.isEmpty()) {
					Iterator<EmpSeminarAttendedDetailsBO> iterator = empSeminarAttendedDetailsBOs.iterator();
					List<EmpSeminarAttendedDetailsTO> empSeminarAttendedDetailsTOs = new ArrayList<EmpSeminarAttendedDetailsTO>();

					while (iterator.hasNext()) {
						EmpSeminarAttendedDetailsBO empSeminarAttendedDetailsBO = iterator.next();
						if (empSeminarAttendedDetailsBO != null) {
							EmpSeminarAttendedDetailsTO empSeminarAttendedDetailsTO = new EmpSeminarAttendedDetailsTO();

							if (empSeminarAttendedDetailsTO.getId() > 0) {
								empSeminarAttendedDetailsTO.setId(empSeminarAttendedDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInstitution())) {
								empSeminarAttendedDetailsTO.setOrganisation(empSeminarAttendedDetailsBO.getInstitution());
							}
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInterRegional())) {
								empSeminarAttendedDetailsTO.setInterRegional(empSeminarAttendedDetailsBO.getInterRegional());
					        }
							if (empSeminarAttendedDetailsBO.getFromDate2() != null) {
								empSeminarAttendedDetailsTO.setFromDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getFromDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (empSeminarAttendedDetailsBO.getToDate2() != null) {
								empSeminarAttendedDetailsTO.setToDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getToDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getParticipation())) {
								empSeminarAttendedDetailsTO.setParticipation(empSeminarAttendedDetailsBO.getParticipation());
					        }
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getTitle())) {
								empSeminarAttendedDetailsTO.setSeminarName(empSeminarAttendedDetailsBO.getTitle());
					        }
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getWorkShop())) {
								empSeminarAttendedDetailsTO.setSeminar(empSeminarAttendedDetailsBO.getWorkShop());
					        }
							
							empSeminarAttendedDetailsTOs.add(empSeminarAttendedDetailsTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpSeminarAttendedDetails(empSeminarAttendedDetailsTOs);
					employeeInfoEditNewTO.setEmpSeminarAttendedDetails(empSeminarAttendedDetailsTOs);
				} 
			}
			
			if (empApplicantDetails.getEmpProfeDevelopment() != null) {
				Set<EmpProfessionalDevelopmentBO> empProfessionalDevelopmentBOs =empApplicantDetails.getEmpProfeDevelopment();
				if (empProfessionalDevelopmentBOs != null && !empProfessionalDevelopmentBOs.isEmpty()) {
					Iterator<EmpProfessionalDevelopmentBO> iterator = empProfessionalDevelopmentBOs.iterator();
					List<EmpProfessionalDevelopmentTO> empProfessionalDevelopmentTOs = new ArrayList<EmpProfessionalDevelopmentTO>();

					while (iterator.hasNext()) {
						EmpProfessionalDevelopmentBO empProfessionalDevelopmentBO = iterator.next();
						if (empProfessionalDevelopmentBO != null) {
							EmpProfessionalDevelopmentTO empProfessionalDevelopmentTO = new EmpProfessionalDevelopmentTO();

							if (empProfessionalDevelopmentTO.getId() > 0) {
								empProfessionalDevelopmentTO.setId(empProfessionalDevelopmentBO.getId());
							}
							if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getName())) {
								empProfessionalDevelopmentTO.setName(empProfessionalDevelopmentBO.getName());
							}
							if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getInstitute())) {
								empProfessionalDevelopmentTO.setOrganisation(empProfessionalDevelopmentBO.getInstitute());
					        }
							if (empProfessionalDevelopmentBO.getFromDate() != null) {
								empProfessionalDevelopmentTO.setFromDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (empProfessionalDevelopmentBO.getToDate() != null) {
								empProfessionalDevelopmentTO.setToDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							
							empProfessionalDevelopmentTOs.add(empProfessionalDevelopmentTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpPersonaldevelopment(empProfessionalDevelopmentTOs);
					employeeInfoEditNewTO.setEmpPersonaldevelopment(empProfessionalDevelopmentTOs);
					
				} 
			}
			
			if (empApplicantDetails.getEmpAwardDetails() != null) {
				Set<EmpAwardDetailsBO> empAwardDetailsBOs =empApplicantDetails.getEmpAwardDetails();
				if (empAwardDetailsBOs != null && !empAwardDetailsBOs.isEmpty()) {
					Iterator<EmpAwardDetailsBO> iterator = empAwardDetailsBOs.iterator();
					List<EmpAwardTO> empAwardTOs = new ArrayList<EmpAwardTO>();

					while (iterator.hasNext()) {
						EmpAwardDetailsBO empAwardDetailsBO = iterator.next();
						if (empAwardDetailsBO != null) {
							EmpAwardTO empAwardTO = new EmpAwardTO();

							if (empAwardTO.getId() > 0) {
								empAwardTO.setId(empAwardDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getActivityName())) {
								empAwardTO.setActivityname(empAwardDetailsBO.getActivityName());
							}
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getAwardName())) {
								empAwardTO.setAwardName(empAwardDetailsBO.getAwardName());
					        }
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
								empAwardTO.setAwardbodyName(empAwardDetailsBO.getAwardBody());
					        }
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getYear())) {
								empAwardTO.setYear(empAwardDetailsBO.getYear());
					        }
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
								empAwardTO.setRecognitionName(empAwardDetailsBO.getAward());
					        }
							empAwardTOs.add(empAwardTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpAward(empAwardTOs);
					employeeInfoEditNewTO.setEmpAward(empAwardTOs);
				} 
			}
			
			if (empApplicantDetails.getEmpMemDetailsBOs() != null) {
				Set<EmpMemeberShipDetailsBO> empMemeberShipDetailsBOs =empApplicantDetails.getEmpMemDetailsBOs();
				if (empMemeberShipDetailsBOs != null && !empMemeberShipDetailsBOs.isEmpty()) {
					Iterator<EmpMemeberShipDetailsBO> iterator = empMemeberShipDetailsBOs.iterator();
					List<EmpMemberShipAcademicBodyTO> empMemberShipAcademicBodyTOs = new ArrayList<EmpMemberShipAcademicBodyTO>();

					while (iterator.hasNext()) {
						EmpMemeberShipDetailsBO empMemeberShipDetailsBO = iterator.next();
						if (empMemeberShipDetailsBO != null) {
							EmpMemberShipAcademicBodyTO empMemberShipAcademicBodyTO = new EmpMemberShipAcademicBodyTO();

							if (empMemberShipAcademicBodyTO.getId() > 0) {
								empMemberShipAcademicBodyTO.setId(empMemeberShipDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empMemeberShipDetailsBO.getName())) {
								empMemberShipAcademicBodyTO.setName(empMemeberShipDetailsBO.getName());
							}
							if (empMemeberShipDetailsBO.getFromDate() != null) {
								empMemberShipAcademicBodyTO.setFromDate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (empMemeberShipDetailsBO.getToDate() != null) {
								empMemberShipAcademicBodyTO.setTodate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							
							empMemberShipAcademicBodyTOs.add(empMemberShipAcademicBodyTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpMemberShip(empMemberShipAcademicBodyTOs);
					employeeInfoEditNewTO.setEmpMemberShip(empMemberShipAcademicBodyTOs);
				} 
			}
			objform.setEmployeeInfoEditNewTO(employeeInfoEditNewTO);
			
			
			}	
			
		
		/**
		 * @param employeelist
		 * @param departmentId
		 * @param designationId
		 * @return
		 * @throws Exception
		 */
		public List<EmployeeTO> convertEmployeeTOtoBO(List<Employee> employeelist, int departmentId ,int designationId) throws Exception 
		{
			
			List<Department> departmentList = txn.getEmployeeDepartment();
			List<Designation> designationList=txn.getEmployeeDesignation();
			
			List<EmployeeTO> employeeTos = new ArrayList<EmployeeTO>();
			String name = "";
			if (employeelist != null) {
				Iterator<Employee> stItr = employeelist.iterator();
				while (stItr.hasNext()) {
					name = "";
					Employee emp=(Employee)stItr.next();
					EmployeeTO empTo = new EmployeeTO();
					if (emp.getId()>0) {
						empTo.setId(emp.getId());
					}
					if (emp.getUid() != null) {
						empTo.setDummyUid(emp.getUid());
					}
					if (emp.getCode() != null) {
						
							empTo.setDummyCode(emp.getCode());
					}
					if (emp.getFingerPrintId() != null) {
						
						empTo.setDummyFingerPrintId(emp.getFingerPrintId());
					}
					if (emp.getFirstName() != null) {
						
						empTo.setDummyFirstName(emp.getFirstName());
					}
                    
					if(emp.getDesignation()!=null && emp.getDesignation().getId()>0){
						int DesigId=emp.getDesignation().getId();
						String DesigName= null;
						if (designationList != null) {
							Iterator<Designation> desItr = designationList.iterator();
							while (desItr.hasNext()) {
								Designation des=(Designation)desItr.next();
								int desigId =des.getId();
								if(desigId== DesigId)
								{
								DesigName=des.getName();
								}
							}
						}
						empTo.setDummyDesignationName(DesigName);
					}
						if(emp.getDepartment()!=null && emp.getDepartment().getId()>0){
							int DepId=emp.getDepartment().getId();
							String DepName= null;
							if (departmentList != null) {
								Iterator<Department> depItr = departmentList.iterator();
								while (depItr.hasNext()) {
									Department dep=(Department)depItr.next();
									int depId =dep.getId();
									if(depId== DepId)
									{
										DepName=dep.getName();
									}
								}
							}
							empTo.setDummyDepartmentName(DepName);
						}	
						
						if(emp.getEmpImages()!=null && !emp.getEmpImages().isEmpty())
						{
							Iterator<EmpImages> itr=emp.getEmpImages().iterator();
							while (itr.hasNext()) {
								EmpImages bo =itr.next();
								if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
									empTo.setIsPhoto(true);
									break;
								}
							}
							
						}
				employeeTos.add(empTo);
				}
				
			}
			
		
			return employeeTos;
		}
		public int currentMonth(){
			 Date date=new Date();
			 Calendar c=Calendar.getInstance();
			 c.setTime(date);
			 int month=c.get(Calendar.MONTH);
			 return month+1;
		}
		
		
		
		public List<EmployeeTO> convertEmployeeTOtoBO(List<Employee> employeelist) throws Exception 
		{
			List<Designation> designationList=txn.getEmployeeDesignation();
			List<EmployeeTO> employeeTos = new ArrayList<EmployeeTO>();
			String name = "";
			if (employeelist != null) {
				Iterator<Employee> stItr = employeelist.iterator();
				while (stItr.hasNext()) {
					name = "";
					Employee emp=(Employee)stItr.next();
					EmployeeTO empTo = new EmployeeTO();
					if (emp.getId()>0) {
						empTo.setId(emp.getId());
					}
					if (emp.getUid() != null) {
						empTo.setDummyUid(emp.getUid());
					}
					if (emp.getCode() != null) {
						
							empTo.setDummyCode(emp.getCode());
					}
					if (emp.getFingerPrintId() != null) {
						
						empTo.setDummyFingerPrintId(emp.getFingerPrintId());
					}
					if (emp.getFirstName() != null) {
						
						empTo.setDummyFirstName(emp.getFirstName());
					}
					if(emp.getDepartment().getName()!=null && !emp.getDepartment().getName().isEmpty()){
						empTo.setDeptName(emp.getDepartment().getName());
					}
					if(emp.getDesignation()!=null && emp.getDesignation().getId()>0){
						int DesigId=emp.getDesignation().getId();
						String DesigName= null;
						if (designationList != null) {
							Iterator<Designation> desItr = designationList.iterator();
							while (desItr.hasNext()) {
								Designation des=(Designation)desItr.next();
								int desigId =des.getId();
								if(desigId== DesigId)
								{
								DesigName=des.getName();
								}
							}
						}
						empTo.setDummyDesignationName(DesigName);
					}
					if(emp.getEmpImages()!=null && !emp.getEmpImages().isEmpty())
						{
							Iterator<EmpImages> itr=emp.getEmpImages().iterator();
							while (itr.hasNext()) {
								EmpImages bo =itr.next();
								if(bo.getEmpPhoto()!=null && bo.getEmpPhoto().length >0){
									empTo.setIsPhoto(true);
									break;
								}
							}
							
						}
				employeeTos.add(empTo);
				}
			}
			return employeeTos;
		}

		public void convertBoToFormNew(Users userApplicantDetails,EmployeeInfoViewForm objform) throws Exception{
			if(userApplicantDetails!=null && userApplicantDetails.getEmployee()!=null){
				
					
				
				if (userApplicantDetails.getEmployee().getEligibilityTest() != null && !userApplicantDetails.getEmployee().getEligibilityTest().isEmpty()) {
					if(userApplicantDetails.getEmployee().getEligibilityTestOther()!=null && !userApplicantDetails.getEmployee().getEligibilityTestOther().isEmpty())
					{
						String empEligibAddOther=userApplicantDetails.getEmployee().getEligibilityTest()+"  --Other description:-"+userApplicantDetails.getEmployee().getEligibilityTestOther();
						 objform.setEligibilityTestdisplay(empEligibAddOther);
					}
					else
						 objform.setEligibilityTestdisplay(userApplicantDetails.getEmployee().getEligibilityTest());
				}
				if(userApplicantDetails.getEmployee().getIndustryFunctionalArea()!=null && !userApplicantDetails.getEmployee().getIndustryFunctionalArea().trim().isEmpty()){
					objform.setIndustryFunctionalArea(userApplicantDetails.getEmployee().getIndustryFunctionalArea());
				}			
				if(userApplicantDetails.getEmployee().getReservationCategory() !=null && !userApplicantDetails.getEmployee().getReservationCategory().isEmpty()){
					if("GM".equalsIgnoreCase(userApplicantDetails.getEmployee().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getEmployee().getReservationCategory());
					}
					if("SC".equalsIgnoreCase(userApplicantDetails.getEmployee().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getEmployee().getReservationCategory());
					}
					if("ST".equalsIgnoreCase(userApplicantDetails.getEmployee().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getEmployee().getReservationCategory());
					}
					if("OBC".equalsIgnoreCase(userApplicantDetails.getEmployee().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getEmployee().getReservationCategory());
					}
					if("Minority".equalsIgnoreCase(userApplicantDetails.getEmployee().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getEmployee().getReservationCategory());
					}
					if("Person With Disability".equalsIgnoreCase(userApplicantDetails.getEmployee().getReservationCategory())){
						
						String personWithDisability=userApplicantDetails.getEmployee().getReservationCategory()+"  --Handicap description:-"+userApplicantDetails.getEmployee().getHandicappedDescription();
							objform.setReservationCategory(personWithDisability);
						
						}
				}
				
				
				
				////...............................................................Photo.....................................................
			
				
				if(userApplicantDetails.getEmployee().getEmpImages()!=null && !userApplicantDetails.getEmployee().getEmpImages().isEmpty()){
					Iterator<EmpImages> itr=userApplicantDetails.getEmployee().getEmpImages().iterator();
					while (itr.hasNext()) {
						EmpImages bo = itr.next();
						if(bo.getEmpPhoto()!=null)
							objform.setPhotoBytes(bo.getEmpPhoto());
					}
				
				}
			////...............................................................Photo.....................................................
				if(userApplicantDetails.getEmployee().getId()>0){
					 objform.setEmployeeId(String.valueOf(userApplicantDetails.getEmployee().getId()));
				}
				if(StringUtils.isNotEmpty(String.valueOf(userApplicantDetails.getEmployee().getTeachingStaff()))){
					String Value= String.valueOf(userApplicantDetails.getEmployee().getTeachingStaff());
					if(Value.equals("true"))
						objform.setTeachingStaff("Teaching Staff");
					else
						objform.setTeachingStaff("Non-teaching Staff");
					 
				}
					
				
				if(StringUtils.isNotEmpty(String.valueOf(userApplicantDetails.getEmployee().getActive()))){
					String Value= String.valueOf(userApplicantDetails.getEmployee().getActive());
					if(Value.equals("true"))
						objform.setActive("Active");
					else
						objform.setActive("Not Active");
					 
				}
				if(StringUtils.isNotEmpty(String.valueOf(userApplicantDetails.getEmployee().getIsSameAddress()))){
					String Value= String.valueOf(userApplicantDetails.getEmployee().getIsSameAddress());
					if(Value.equals("true"))
						objform.setSameAddress("true");
					else
						objform.setSameAddress("false");
					 
				}
				if(StringUtils.isNotEmpty(String.valueOf(userApplicantDetails.getEmployee().getCurrentlyWorking()))){
					String Value= String.valueOf(userApplicantDetails.getEmployee().getCurrentlyWorking());
					if(Value.equals("true"))
						objform.setCurrentlyWorking("YES");
					else
						objform.setCurrentlyWorking("NO");
					 
				}
				
				if(userApplicantDetails.getEmployee().getPayScaleId()!=null && userApplicantDetails.getEmployee().getPayScaleId().getId()>0){
					  objform.setPayScaleId(String.valueOf(userApplicantDetails.getEmployee().getPayScaleId().getPayScale()));
				}
				
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getFirstName()) && userApplicantDetails.getEmployee().getFirstName()!=null){
					  objform.setName(userApplicantDetails.getEmployee().getFirstName());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getSmartCardNo()) && userApplicantDetails.getEmployee().getSmartCardNo()!=null){
					  objform.setSmartCardNo(userApplicantDetails.getEmployee().getSmartCardNo());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getUid()) && userApplicantDetails.getEmployee().getUid()!=null){
					  objform.setuId(userApplicantDetails.getEmployee().getUid());
				}
				if(userApplicantDetails.getEmployee().getTitleId()!=null && userApplicantDetails.getEmployee().getTitleId().getId()>0){
					  objform.setTitleId(String.valueOf(userApplicantDetails.getEmployee().getTitleId().getTitle()));
				}
				if(userApplicantDetails.getEmployee().getDepartment()!=null && userApplicantDetails.getEmployee().getDepartment().getId()>0){
					  objform.setDepartmentId(String.valueOf(userApplicantDetails.getEmployee().getDepartment().getName()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getCode())&& userApplicantDetails.getEmployee().getCode()!=null){
					  objform.setCode(userApplicantDetails.getEmployee().getCode());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getFingerPrintId()) && userApplicantDetails.getEmployee().getFingerPrintId()!=null){
					  objform.setFingerPrintId(userApplicantDetails.getEmployee().getFingerPrintId());
				}
				if(userApplicantDetails.getEmployee().getGender()!=null && !userApplicantDetails.getEmployee().getGender().isEmpty()){
					 objform.setGender(userApplicantDetails.getEmployee().getGender());
				}
				if(userApplicantDetails.getEmployee().getNationality()!=null && userApplicantDetails.getEmployee().getNationality().getId()>0){
					  objform.setNationalityId(String.valueOf(userApplicantDetails.getEmployee().getNationality().getName()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getMaritalStatus()) && userApplicantDetails.getEmployee().getMaritalStatus()!=null){
					  objform.setMaritalStatus(String.valueOf(userApplicantDetails.getEmployee().getMaritalStatus()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getBloodGroup()) && userApplicantDetails.getEmployee().getBloodGroup()!=null){
					  objform.setBloodGroup(String.valueOf(userApplicantDetails.getEmployee().getBloodGroup()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getPanNo()) && userApplicantDetails.getEmployee().getPanNo()!=null){
					  objform.setPanno(String.valueOf(userApplicantDetails.getEmployee().getPanNo()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getEmail()) && userApplicantDetails.getEmployee().getEmail()!=null){
					  objform.setEmail(String.valueOf(userApplicantDetails.getEmployee().getEmail()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getWorkEmail()) && userApplicantDetails.getEmployee().getWorkEmail()!=null){
					  objform.setOfficialEmail(String.valueOf(userApplicantDetails.getEmployee().getWorkEmail()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getCurrentAddressMobile1()) && userApplicantDetails.getEmployee().getCurrentAddressMobile1()!=null){
					  objform.setMobileNo1(userApplicantDetails.getEmployee().getCurrentAddressMobile1());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getBankAccNo()) && userApplicantDetails.getEmployee().getBankAccNo()!=null){
					  objform.setBankAccNo(userApplicantDetails.getEmployee().getBankAccNo());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getPfNo()) && userApplicantDetails.getEmployee().getPfNo()!=null){
					  objform.setPfNo(userApplicantDetails.getEmployee().getPfNo());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getFourWheelerNo()) && userApplicantDetails.getEmployee().getFourWheelerNo()!=null){
					  objform.setFourWheelerNo(userApplicantDetails.getEmployee().getFourWheelerNo());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getTwoWheelerNo()) && userApplicantDetails.getEmployee().getTwoWheelerNo()!=null){
					  objform.setTwoWheelerNo(userApplicantDetails.getEmployee().getTwoWheelerNo());
				}
				
				if(userApplicantDetails.getEmployee().getReligionId()!=null && userApplicantDetails.getEmployee().getReligionId().getId()>0){
					  objform.setReligionId(String.valueOf(userApplicantDetails.getEmployee().getReligionId().getName()));
				}
				if(userApplicantDetails.getEmployee().getEmptype()!=null && userApplicantDetails.getEmployee().getEmptype().getId()>0){
					  objform.setEmptypeId(String.valueOf(userApplicantDetails.getEmployee().getEmptype().getName()));
				}
				if(userApplicantDetails.getEmployee().getEmpQualificationLevel()!=null && userApplicantDetails.getEmployee().getEmpQualificationLevel().getId()>0){
					  objform.setQualificationId(String.valueOf(userApplicantDetails.getEmployee().getEmpQualificationLevel().getName()));
				}
				//Modification    ........................................................
				
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getPermanentAddressLine1()) && userApplicantDetails.getEmployee().getPermanentAddressLine1()!=null){
					  objform.setAddressLine1(userApplicantDetails.getEmployee().getPermanentAddressLine1());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getPermanentAddressLine2()) && userApplicantDetails.getEmployee().getPermanentAddressLine2()!=null ){
					  objform.setAddressLine2(userApplicantDetails.getEmployee().getPermanentAddressLine2());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getPermanentAddressCity()) && userApplicantDetails.getEmployee().getPermanentAddressCity()!=null){
					  objform.setCity(userApplicantDetails.getEmployee().getPermanentAddressCity());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getPermanentAddressZip()) && userApplicantDetails.getEmployee().getPermanentAddressZip()!=null){
					  objform.setPermanentZipCode(userApplicantDetails.getEmployee().getPermanentAddressZip());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getPermanentAddressStateOthers()) && userApplicantDetails.getEmployee().getPermanentAddressStateOthers()!=null){
					  objform.setOtherPermanentState(userApplicantDetails.getEmployee().getPermanentAddressStateOthers());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getCommunicationAddressLine1()) && userApplicantDetails.getEmployee().getCommunicationAddressLine1()!=null){
					  objform.setCurrentAddressLine1(userApplicantDetails.getEmployee().getCommunicationAddressLine1());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getCommunicationAddressLine2()) && userApplicantDetails.getEmployee().getCommunicationAddressLine2()!=null){
					  objform.setCurrentAddressLine2(userApplicantDetails.getEmployee().getCommunicationAddressLine2());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getCommunicationAddressCity()) && userApplicantDetails.getEmployee().getCommunicationAddressCity()!=null){
					  objform.setCurrentCity(userApplicantDetails.getEmployee().getCommunicationAddressCity());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getCommunicationAddressStateOthers()) && userApplicantDetails.getEmployee().getCommunicationAddressStateOthers()!=null){
					  objform.setOtherCurrentState(userApplicantDetails.getEmployee().getCommunicationAddressStateOthers());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getCommunicationAddressZip()) && userApplicantDetails.getEmployee().getCommunicationAddressZip()!=null){
					  objform.setCurrentZipCode(userApplicantDetails.getEmployee().getCommunicationAddressZip());
				}
				if(userApplicantDetails.getEmployee().getCountryByPermanentAddressCountryId()!=null && userApplicantDetails.getEmployee().getCountryByPermanentAddressCountryId().getId()>0){
					  objform.setCountryId(String.valueOf(userApplicantDetails.getEmployee().getCountryByPermanentAddressCountryId().getName()));
				}
				if(userApplicantDetails.getEmployee().getCountryByCommunicationAddressCountryId()!=null && userApplicantDetails.getEmployee().getCountryByCommunicationAddressCountryId().getId()>0){
					  objform.setCurrentCountryId(String.valueOf(userApplicantDetails.getEmployee().getCountryByCommunicationAddressCountryId().getName()));
				}
				if(userApplicantDetails.getEmployee().getCurrentAddressHomeTelephone1()!=null && !userApplicantDetails.getEmployee().getCurrentAddressHomeTelephone1().isEmpty()){
					objform.setHomePhone1(userApplicantDetails.getEmployee().getCurrentAddressHomeTelephone1());
				}
				
				if(userApplicantDetails.getEmployee().getCurrentAddressHomeTelephone2()!=null && !userApplicantDetails.getEmployee().getCurrentAddressHomeTelephone2().isEmpty()){
					objform.setHomePhone2(userApplicantDetails.getEmployee().getCurrentAddressHomeTelephone2());
				}
				
				if(userApplicantDetails.getEmployee().getCurrentAddressHomeTelephone3()!=null && !userApplicantDetails.getEmployee().getCurrentAddressHomeTelephone3().isEmpty()){
					objform.setHomePhone3(userApplicantDetails.getEmployee().getCurrentAddressHomeTelephone3());
				}
				if(userApplicantDetails.getEmployee().getCurrentAddressWorkTelephone1()!=null && !userApplicantDetails.getEmployee().getCurrentAddressWorkTelephone1().isEmpty()){
					objform.setWorkPhNo1(userApplicantDetails.getEmployee().getCurrentAddressWorkTelephone1());
				}
				
				if(userApplicantDetails.getEmployee().getCurrentAddressWorkTelephone2()!=null && !userApplicantDetails.getEmployee().getCurrentAddressWorkTelephone2().isEmpty()){
					objform.setWorkPhNo2(userApplicantDetails.getEmployee().getCurrentAddressWorkTelephone2());
				}
				
				if(userApplicantDetails.getEmployee().getCurrentAddressWorkTelephone3()!=null && !userApplicantDetails.getEmployee().getCurrentAddressWorkTelephone3().isEmpty()){
					objform.setWorkPhNo3(userApplicantDetails.getEmployee().getCurrentAddressWorkTelephone3());
				}

				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getDesignationName()) && userApplicantDetails.getEmployee().getDesignationName()!=null){
					  objform.setDesignation(userApplicantDetails.getEmployee().getDesignationName());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getOrganistionName()) && userApplicantDetails.getEmployee().getOrganistionName()!=null ){
					  objform.setOrgAddress(userApplicantDetails.getEmployee().getOrganistionName());
				}
				if(userApplicantDetails.getEmployee().getDesignation()!=null && userApplicantDetails.getEmployee().getDesignation().getId()>0){
					  objform.setDesignationPfId(String.valueOf(userApplicantDetails.getEmployee().getDesignation().getName()));
				}
				if(userApplicantDetails.getEmployee().getDepartment()!=null && userApplicantDetails.getEmployee().getDepartment().getId()>0){
					  objform.setDepartmentId(String.valueOf(userApplicantDetails.getEmployee().getDepartment().getName()));
				}
				if(userApplicantDetails.getEmployee().getEmployeeByReportToId()!=null && userApplicantDetails.getEmployee().getEmployeeByReportToId().getId()>0){
					  objform.setReportToId(String.valueOf(userApplicantDetails.getEmployee().getEmployeeByReportToId().getFirstName()));
				}
			/*	if(userApplicantDetails.getEmployee().getGrade()!=null && !userApplicantDetails.getEmployee().getGrade().isEmpty()){
					  objform.setGrade(String.valueOf(userApplicantDetails.getEmployee().getGrade()));
				}*/
				/*if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getCurrentOrganization()) && userApplicantDetails.getEmployee().getCurrentOrganization()!=null){
					  objform.setOrgAddress(userApplicantDetails.getEmployee().getCurrentOrganization());
				}*/
				if(userApplicantDetails.getEmployee().getBooks()!=null && !userApplicantDetails.getEmployee().getBooks().isEmpty()){
					  objform.setBooks(String.valueOf(userApplicantDetails.getEmployee().getBooks()));
				}
				if(userApplicantDetails.getEmployee().getNoOfPublicationsNotRefered()!=null && !userApplicantDetails.getEmployee().getNoOfPublicationsNotRefered().isEmpty()){
					  objform.setNoOfPublicationsNotRefered(userApplicantDetails.getEmployee().getNoOfPublicationsNotRefered());
				}
				if(userApplicantDetails.getEmployee().getNoOfPublicationsRefered()!=null && !userApplicantDetails.getEmployee().getNoOfPublicationsRefered().isEmpty()){
					  objform.setNoOfPublicationsRefered(userApplicantDetails.getEmployee().getNoOfPublicationsRefered());
				}
								
				if(userApplicantDetails.getEmployee().getEmpQualificationLevel()!=null && userApplicantDetails.getEmployee().getEmpQualificationLevel().getId()>0){
					  objform.setQualificationId(String.valueOf(userApplicantDetails.getEmployee().getEmpQualificationLevel().getName()));
				}
				if(userApplicantDetails.getEmployee().getDesignation()!=null && userApplicantDetails.getEmployee().getDesignation().getId()>0){
					  objform.setDesignationPfId(String.valueOf(userApplicantDetails.getEmployee().getDesignation().getName()));
				}
				if(userApplicantDetails.getEmployee().getEmpSubjectArea()!=null && userApplicantDetails.getEmployee().getEmpSubjectArea().getId()>0){
					  objform.setEmpSubjectAreaId(String.valueOf(userApplicantDetails.getEmployee().getEmpSubjectArea().getName()));
				}
				if(userApplicantDetails.getEmployee().getDob()!=null && !userApplicantDetails.getEmployee().getDob().toString().isEmpty() ){
					objform.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getEmployee().getDob().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(userApplicantDetails.getEmployee().getDateOfLeaving()!=null && !userApplicantDetails.getEmployee().getDateOfLeaving().toString().isEmpty()){
					objform.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getEmployee().getDateOfLeaving().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(userApplicantDetails.getEmployee().getDateOfResignation()!=null && !userApplicantDetails.getEmployee().getDateOfResignation().toString().isEmpty()){
					objform.setDateOfResignation(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getEmployee().getDateOfResignation().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(userApplicantDetails.getEmployee().getDoj()!=null && !userApplicantDetails.getEmployee().getDoj().toString().isEmpty()){
					objform.setDateOfJoining(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getEmployee().getDoj().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(userApplicantDetails.getEmployee().getRejoinDate()!=null && !userApplicantDetails.getEmployee().getRejoinDate().toString().isEmpty()){
					objform.setRejoinDate(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getEmployee().getRejoinDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(userApplicantDetails.getEmployee().getRetirementDate()!=null && !userApplicantDetails.getEmployee().getRetirementDate().toString().isEmpty()){
					objform.setRetirementDate(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getEmployee().getRetirementDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(userApplicantDetails.getEmployee().getTotalExpMonths()!=null && !userApplicantDetails.getEmployee().getTotalExpMonths().isEmpty()){
					  objform.setExpMonths(String.valueOf(userApplicantDetails.getEmployee().getTotalExpMonths()));
				}
				if(userApplicantDetails.getEmployee().getTotalExpYear()!=null && !userApplicantDetails.getEmployee().getTotalExpYear().isEmpty()){
					  objform.setExpYears(String.valueOf(userApplicantDetails.getEmployee().getTotalExpYear()));
				}
				/*if(userApplicantDetails.getEmployee().getActive()){
					  objform.setActive(String.valueOf(userApplicantDetails.getEmployee().getActive()));
				}*/
				
				
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getEmergencyContName()) && userApplicantDetails.getEmployee().getEmergencyContName()!=null){
					  objform.setEmContactName(userApplicantDetails.getEmployee().getEmergencyContName());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getEmContactAddress()) && userApplicantDetails.getEmployee().getEmContactAddress()!=null){
					  objform.setEmContactAddress(userApplicantDetails.getEmployee().getEmContactAddress());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getEmergencyHomeTelephone()) && userApplicantDetails.getEmployee().getEmergencyHomeTelephone()!=null){
					  objform.setEmContactHomeTel(userApplicantDetails.getEmployee().getEmergencyHomeTelephone());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getEmergencyMobile()) && userApplicantDetails.getEmployee().getEmergencyMobile()!=null){
					  objform.setEmContactMobile(userApplicantDetails.getEmployee().getEmergencyMobile());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getEmergencyWorkTelephone()) && userApplicantDetails.getEmployee().getEmergencyWorkTelephone()!=null){
					  objform.setEmContactWorkTel(userApplicantDetails.getEmployee().getEmergencyWorkTelephone());
				}
				
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getGrossPay()) && userApplicantDetails.getEmployee().getGrossPay()!=null){
					  objform.setGrossPay(userApplicantDetails.getEmployee().getGrossPay());
				}
				/*if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getHalfDayEndTime())){
					  objform.setHalfDayEndTime(userApplicantDetails.getEmployee().getHalfDayEndTime());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getHalfDayStartTime())){
					  objform.setHalfDayStartTime(userApplicantDetails.getEmployee().getHalfDayStartTime());
				}*/
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getHighQualifForAlbum()) && userApplicantDetails.getEmployee().getHighQualifForAlbum()!=null){
					  objform.setHighQualifForAlbum(userApplicantDetails.getEmployee().getHighQualifForAlbum());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getOtherInfo()) && userApplicantDetails.getEmployee().getOtherInfo()!=null){
					  objform.setOtherInfo(userApplicantDetails.getEmployee().getOtherInfo());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getWorkEmail()) && userApplicantDetails.getEmployee().getWorkEmail()!=null){
					  objform.setOfficialEmail(userApplicantDetails.getEmployee().getWorkEmail());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getPanNo()) && userApplicantDetails.getEmployee().getPanNo()!=null){
					  objform.setPanno(userApplicantDetails.getEmployee().getPanNo());
				}
				
							
				
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getReasonOfLeaving()) && userApplicantDetails.getEmployee().getReasonOfLeaving()!=null){
					  objform.setReasonOfLeaving(userApplicantDetails.getEmployee().getReasonOfLeaving());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getRelationship()) && userApplicantDetails.getEmployee().getRelationship()!=null){
					  objform.setEmContactRelationship(userApplicantDetails.getEmployee().getRelationship());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getRelevantExpMonths()) && userApplicantDetails.getEmployee().getRelevantExpMonths()!=null ){
					  objform.setRelevantExpMonths(userApplicantDetails.getEmployee().getRelevantExpMonths());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getRelevantExpYears()) && userApplicantDetails.getEmployee().getRelevantExpYears()!=null ){
					  objform.setRelevantExpYears(userApplicantDetails.getEmployee().getRelevantExpYears());
				}
				/*if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getSaturdayTimeOut())){
					  objform.setSaturdayTimeOut(userApplicantDetails.getEmployee().getSaturdayTimeOut());
				}*/
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getScale()) && userApplicantDetails.getEmployee().getScale()!=null){
					  objform.setScale(userApplicantDetails.getEmployee().getScale());
				}
				if(userApplicantDetails.getEmployee().getStateByCommunicationAddressStateId()!=null && userApplicantDetails.getEmployee().getStateByCommunicationAddressStateId().getId()>0){
					  objform.setCurrentState(String.valueOf(userApplicantDetails.getEmployee().getStateByCommunicationAddressStateId().getName()));
				}
				if(userApplicantDetails.getEmployee().getStateByPermanentAddressStateId()!=null && userApplicantDetails.getEmployee().getStateByPermanentAddressStateId().getId()>0){
					  objform.setStateId(String.valueOf(userApplicantDetails.getEmployee().getStateByPermanentAddressStateId().getName()));
				}
				if(userApplicantDetails.getEmployee().getStreamId() != null && userApplicantDetails.getEmployee().getStreamId().getId()>0){
					objform.setStreamId(String.valueOf(userApplicantDetails.getEmployee().getStreamId().getName()));
					
				}
				
				if(userApplicantDetails.getEmployee().getTimeIn()!=null && !userApplicantDetails.getEmployee().getTimeIn().isEmpty()){
					objform.setTimeIn(userApplicantDetails.getEmployee().getTimeIn().substring(0,2));
					objform.setTimeInMin(userApplicantDetails.getEmployee().getTimeIn().substring(3,5));
				}
			
				if(userApplicantDetails.getEmployee().getTimeInEnds()!=null && !userApplicantDetails.getEmployee().getTimeInEnds().isEmpty()){
					objform.setTimeInEnds(userApplicantDetails.getEmployee().getTimeInEnds().substring(0,2));
					objform.setTimeInEndMIn(userApplicantDetails.getEmployee().getTimeInEnds().substring(3,5));
				}
				
				if(userApplicantDetails.getEmployee().getTimeOut()!=null && !userApplicantDetails.getEmployee().getTimeOut().isEmpty()){
					objform.setTimeOut(userApplicantDetails.getEmployee().getTimeOut().substring(0,2));
					objform.setTimeOutMin(userApplicantDetails.getEmployee().getTimeOut().substring(3,5));
				}
				
				if(userApplicantDetails.getEmployee().getSaturdayTimeOut()!=null && !userApplicantDetails.getEmployee().getSaturdayTimeOut().isEmpty()){
					objform.setSaturdayTimeOut(userApplicantDetails.getEmployee().getSaturdayTimeOut().substring(0,2));
					objform.setSaturdayTimeOutMin(userApplicantDetails.getEmployee().getSaturdayTimeOut().substring(3,5));
				}
				
				if(userApplicantDetails.getEmployee().getHalfDayStartTime()!=null && !userApplicantDetails.getEmployee().getHalfDayStartTime().isEmpty()){
					objform.setHalfDayStartTime(userApplicantDetails.getEmployee().getHalfDayStartTime().substring(0,2));
					objform.setHalfDayStartTimeMin(userApplicantDetails.getEmployee().getHalfDayStartTime().substring(3,5));
				}
				
				if(userApplicantDetails.getEmployee().getHalfDayEndTime()!=null && !userApplicantDetails.getEmployee().getHalfDayEndTime().isEmpty()){
					objform.setHalfDayEndTime(userApplicantDetails.getEmployee().getHalfDayEndTime().substring(0,2));
					objform.setHalfDayEndTimeMin(userApplicantDetails.getEmployee().getHalfDayEndTime().substring(3,5));
				}
			
				
				
				
				
				
				
				
				
				/*if(userApplicantDetails.getEmployee().getTimeIn()!=null && !userApplicantDetails.getEmployee().getTimeIn().isEmpty()){
					objform.setTimeIn(userApplicantDetails.getEmployee().getTimeIn().substring(0,2));
					objform.setTimeInMin(userApplicantDetails.getEmployee().getTimeIn().substring(3,5));
				}
			
				if(userApplicantDetails.getEmployee().getTimeInEnds()!=null && !userApplicantDetails.getEmployee().getTimeInEnds().isEmpty()){
					objform.setTimeInEnds(userApplicantDetails.getEmployee().getTimeInEnds().substring(0,2));
					objform.setTimeInEndMIn(userApplicantDetails.getEmployee().getTimeInEnds().substring(3,5));
				}
				
				if(userApplicantDetails.getEmployee().getTimeOut()!=null && !userApplicantDetails.getEmployee().getTimeOut().isEmpty()){
					objform.setTimeOut(userApplicantDetails.getEmployee().getTimeOut().substring(0,2));
					objform.setTimeOutMin(userApplicantDetails.getEmployee().getTimeOut().substring(3,5));
				}
				
				if(userApplicantDetails.getEmployee().getSaturdayTimeOut()!=null && !userApplicantDetails.getEmployee().getSaturdayTimeOut().isEmpty()){
					objform.setSaturdayTimeOut(userApplicantDetails.getEmployee().getSaturdayTimeOut().substring(0,2));
					objform.setSaturdayTimeOutMin(userApplicantDetails.getEmployee().getSaturdayTimeOut().substring(3,5));
				}
				
				if(userApplicantDetails.getEmployee().getHalfDayStartTime()!=null && !userApplicantDetails.getEmployee().getHalfDayStartTime().isEmpty()){
					objform.setHalfDayStartTime(userApplicantDetails.getEmployee().getHalfDayStartTime().substring(0,2));
					objform.setHalfDayStartTimeMin(userApplicantDetails.getEmployee().getHalfDayStartTime().substring(3,5));
				}
				
				if(userApplicantDetails.getEmployee().getHalfDayEndTime()!=null && !userApplicantDetails.getEmployee().getHalfDayEndTime().isEmpty()){
					objform.setHalfDayEndTime(userApplicantDetails.getEmployee().getHalfDayEndTime().substring(0,2));
					objform.setHalfDayEndTimeMin(userApplicantDetails.getEmployee().getHalfDayEndTime().substring(3,5));
				}*/
								
				if(StringUtils.isNotEmpty(userApplicantDetails.getEmployee().getWorkEmail()) && userApplicantDetails.getEmployee().getWorkEmail()!=null){
					  objform.setOfficialEmail(userApplicantDetails.getEmployee().getWorkEmail());
				}
				if(userApplicantDetails.getEmployee().getWorkLocationId()!=null && userApplicantDetails.getEmployee().getWorkLocationId().getId()>0){
					  objform.setWorkLocationId(String.valueOf(userApplicantDetails.getEmployee().getWorkLocationId().getName()));
				}
				if(userApplicantDetails.getEmployee().getEmptype()!=null && userApplicantDetails.getEmployee().getEmptype().getId()>0){
					  objform.setEmptypeId(String.valueOf(userApplicantDetails.getEmployee().getEmptype().getName()));
				}
				
				if(userApplicantDetails.getEmployee().getEducationalDetailsSet()!=null){
					List<EmpQualificationLevelTo> fixed=null;
					if(objform.getEmployeeInfoTONew()!=null){
						if(objform.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
							fixed=objform.getEmployeeInfoTONew().getEmpQualificationFixedTo();
						}
						List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
						Set<EmpEducationalDetails> empEducationalDetailsSet=userApplicantDetails.getEmployee().getEducationalDetailsSet();
						Iterator<EmpEducationalDetails> iterator=empEducationalDetailsSet.iterator();
						while(iterator.hasNext()){
							EmpEducationalDetails empEducationalDetails=iterator.next();
							if(empEducationalDetails!=null){
								boolean flag=false;
								if(empEducationalDetails.getEmpQualificationLevel()!=null 
										&& empEducationalDetails.getEmpQualificationLevel().isFixedDisplay()!=null){
									flag=empEducationalDetails.getEmpQualificationLevel().isFixedDisplay();
									if(flag && fixed!=null){
										Iterator<EmpQualificationLevelTo> iterator2=fixed.iterator();
										while(iterator2.hasNext()){
											EmpQualificationLevelTo empQualificationLevelTo=iterator2.next();
											if(empQualificationLevelTo!=null && StringUtils.isNotEmpty(empQualificationLevelTo.getEducationId())){
												if(empEducationalDetails.getEmpQualificationLevel().getId()>0)
													if(empQualificationLevelTo.getEducationId().equalsIgnoreCase(String.valueOf(empEducationalDetails.getEmpQualificationLevel().getId()))){
														
														if(empEducationalDetails.getId()>0){
															empQualificationLevelTo.setQualification(empEducationalDetails.getEmpQualificationLevel().getName());
															}
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getCourse())){
															empQualificationLevelTo.setCourse(empEducationalDetails.getCourse());
														}
														
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getSpecialization())){
															empQualificationLevelTo.setSpecialization(empEducationalDetails.getSpecialization());
														}
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getGrade())){
															empQualificationLevelTo.setGrade(empEducationalDetails.getGrade());
														}
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getInstitute())){
															empQualificationLevelTo.setInstitute(empEducationalDetails.getInstitute());
														}
														
														if(empEducationalDetails.getYearOfCompletion()>0){
															empQualificationLevelTo.setYearOfComp(String.valueOf(empEducationalDetails.getYearOfCompletion()));
														}
													}
											}
										}
									}else{
										EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
										
										if(empEducationalDetails.getId()>0){
											empQualificationLevelTo.setQualification(empEducationalDetails.getEmpQualificationLevel().getName());
											}
										if(StringUtils.isNotEmpty(empEducationalDetails.getCourse())){
												empQualificationLevelTo.setCourse(empEducationalDetails.getCourse());
											}
											
											if(StringUtils.isNotEmpty(empEducationalDetails.getSpecialization())){
												empQualificationLevelTo.setSpecialization(empEducationalDetails.getSpecialization());
											}
											
											if(StringUtils.isNotEmpty(empEducationalDetails.getGrade())){
												empQualificationLevelTo.setGrade(empEducationalDetails.getGrade());
											}
											
											if(StringUtils.isNotEmpty(empEducationalDetails.getInstitute())){
												empQualificationLevelTo.setInstitute(empEducationalDetails.getInstitute());
											}
											
											if(empEducationalDetails.getYearOfCompletion()>0){
												empQualificationLevelTo.setYearOfComp(String.valueOf(empEducationalDetails.getYearOfCompletion()));
											}
											if(empEducationalDetails.getEmpQualificationLevel().getId()>0){
												empQualificationLevelTo.setEducationId(String.valueOf(empEducationalDetails.getEmpQualificationLevel().getId()));
											}
										level.add(empQualificationLevelTo);
									}
										
									}
								}
						}
						objform.getEmployeeInfoTONew().setEmpQualificationLevelTos(level);
					}
				}
			
				
				
				
				
			if(userApplicantDetails.getEmployee().getEmpAcheivements()!=null)
			{
				Set<EmpAcheivement> empAcheivements=userApplicantDetails.getEmployee().getEmpAcheivements();
				if(empAcheivements != null && !empAcheivements.isEmpty())
				{
				Iterator<EmpAcheivement> iterator=empAcheivements.iterator();
				List<EmpAcheivementTO> empAcheivementTOs=new ArrayList<EmpAcheivementTO>();
				
				while(iterator.hasNext()){
					EmpAcheivement empAcheiv=iterator.next();
					if(empAcheiv!=null){
						EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
					
						if(empAcheiv.getId()>0)
						{
							empAcheivementTO.setId(empAcheiv.getId());
						}
						if(StringUtils.isNotEmpty(empAcheiv.getAcheivementName())){
							empAcheivementTO.setAcheivementName(empAcheiv.getAcheivementName());
						}
						
						if(StringUtils.isNotEmpty(empAcheiv.getDetails())){
							empAcheivementTO.setDetails(empAcheiv.getDetails());
						}
						
						empAcheivementTOs.add(empAcheivementTO);
					
				
			}
					}
				objform.getEmployeeInfoTONew().setEmpAcheivements(empAcheivementTOs);
				}
			else
			{
				
				List<EmpAcheivementTO> flist=new ArrayList<EmpAcheivementTO>();
				EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
				empAcheivementTO.setAcheivementName("");
				empAcheivementTO.setDetails("");
				objform.setAchievementListSize(String.valueOf(flist.size()));
				flist.add(empAcheivementTO);
				objform.getEmployeeInfoTONew().setEmpAcheivements(flist);
			}
			}
			
			if(userApplicantDetails.getEmployee().getEmpDependentses()!=null)
			{
				Set<EmpDependents> empDependents=userApplicantDetails.getEmployee().getEmpDependentses();
				if(empDependents != null && !empDependents.isEmpty())
				{
				Iterator<EmpDependents> iterator=empDependents.iterator();
				List<EmpDependentsTO> empDependentsTOs=new ArrayList<EmpDependentsTO>();
				
				while(iterator.hasNext()){
					EmpDependents empDepen=iterator.next();
					if(empDepen!=null){
						EmpDependentsTO empDepenTOs=new EmpDependentsTO();
					
						if(empDepen.getId()>0)
						{
							empDepenTOs.setId(String.valueOf(empDepen.getId()));
						}
						if(StringUtils.isNotEmpty(empDepen.getDependentName())){
							empDepenTOs.setDependantName(empDepen.getDependentName());
						}
						
						if(StringUtils.isNotEmpty(empDepen.getDependentRelationship())){
							empDepenTOs.setDependentRelationship(empDepen.getDependentRelationship());
						}
						
						if(empDepen.getDependantDOB()!=null){
							empDepenTOs.setDependantDOB(CommonUtil.ConvertStringToDateFormat(empDepen.getDependantDOB().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empDependentsTOs.add(empDepenTOs);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpDependentses(empDependentsTOs);
				}
			else
			{
				List<EmpDependentsTO> empDependentses=new ArrayList<EmpDependentsTO>();
				EmpDependentsTO empDependentsTO=new EmpDependentsTO();
				empDependentsTO.setDependantDOB("");
				empDependentsTO.setDependantName("");
				empDependentsTO.setDependentRelationship("");
				objform.setDependantsListSize(String.valueOf(empDependentses.size()));
				empDependentses.add(empDependentsTO);
				
				objform.getEmployeeInfoTONew().setEmpDependentses(empDependentses);
				
			}
			}
			
			if(userApplicantDetails.getEmployee().getEmpFeeConcession()!=null)
			{
				Set<EmpFeeConcession> empFeeConcession=userApplicantDetails.getEmployee().getEmpFeeConcession();
				if(empFeeConcession != null && !empFeeConcession.isEmpty())
				{
				Iterator<EmpFeeConcession> iterator=empFeeConcession.iterator();
				List<EmpFeeConcessionTO> empFeeConcessionTOs=new ArrayList<EmpFeeConcessionTO>();
				
				while(iterator.hasNext()){
					EmpFeeConcession empFeeConc=iterator.next();
					if(empFeeConc!=null){
						EmpFeeConcessionTO empFeeConcTO=new EmpFeeConcessionTO();
						if(empFeeConc.getId()>0)
						{
							empFeeConcTO.setId(empFeeConc.getId());
						}
						if(StringUtils.isNotEmpty(empFeeConc.getFeeConcessionDetails())){
							empFeeConcTO.setFeeConcessionDetails(empFeeConc.getFeeConcessionDetails());
						}
						
						if(StringUtils.isNotEmpty(empFeeConc.getFeeConcessionAmount())){
							empFeeConcTO.setFeeConcessionAmount(empFeeConc.getFeeConcessionAmount());
						}
						
						if(empFeeConc.getFeeConcessionDate()!=null){
							empFeeConcTO.setFeeConcessionDate(CommonUtil.ConvertStringToDateFormat(empFeeConc.getFeeConcessionDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empFeeConcessionTOs.add(empFeeConcTO);
					
				
			}
			}objform.getEmployeeInfoTONew().setEmpFeeConcession(empFeeConcessionTOs);
				}
			else
			{
				List<EmpFeeConcessionTO> list=new ArrayList<EmpFeeConcessionTO>();
				EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
				empFeeConcessionTO.setFeeConcessionAmount("");
				empFeeConcessionTO.setFeeConcessionDate("");
				empFeeConcessionTO.setFeeConcessionDetails("");
				objform.setFeeListSize(String.valueOf(list.size()));
				list.add(empFeeConcessionTO);
				objform.getEmployeeInfoTONew().setEmpFeeConcession(list);
				
			}
			}
			
			if(userApplicantDetails.getEmployee().getEmpFinancial()!=null)
			{
				Set<EmpFinancial> empFinancial=userApplicantDetails.getEmployee().getEmpFinancial();
				if(empFinancial != null && !empFinancial.isEmpty())
				{
				Iterator<EmpFinancial> iterator=empFinancial.iterator();
				List<EmpFinancialTO> empFinancialTOs=new ArrayList<EmpFinancialTO>();
				
				while(iterator.hasNext()){
					EmpFinancial empFinancial2=iterator.next();
					if(empFinancial2!=null){
						EmpFinancialTO empFinancialTO=new EmpFinancialTO();
						if(empFinancial2.getId()>0)
						{
							empFinancialTO.setId(empFinancial2.getId());
						}
						if(StringUtils.isNotEmpty(empFinancial2.getFinancialAmount())){
							empFinancialTO.setFinancialAmount(empFinancial2.getFinancialAmount());
						}
						
						if(StringUtils.isNotEmpty(empFinancial2.getFinancialDetails())){
							empFinancialTO.setFinancialDetails(empFinancial2.getFinancialDetails());
						}
						
						if(empFinancial2.getFinancialDate()!=null){
							empFinancialTO.setFinancialDate(CommonUtil.ConvertStringToDateFormat(empFinancial2.getFinancialDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empFinancialTOs.add(empFinancialTO);
					
				
			}
			}objform.getEmployeeInfoTONew().setEmpFinancial(empFinancialTOs);
				}
				else
				{
					List<EmpFinancialTO> flist=new ArrayList<EmpFinancialTO>();
					EmpFinancialTO empFinancialTO=new EmpFinancialTO();
					empFinancialTO.setFinancialAmount("");
					empFinancialTO.setFinancialDate("");
					empFinancialTO.setFinancialDetails("");
					objform.setFinancialListSize(String.valueOf(flist.size()));
					flist.add(empFinancialTO);
					objform.getEmployeeInfoTONew().setEmpFinancial(flist);
					
				}
			}
			
			if(userApplicantDetails.getEmployee().getEmpIncentives()!=null)
			{
				Set<EmpIncentives> empIncentives=userApplicantDetails.getEmployee().getEmpIncentives();
				if(empIncentives != null && !empIncentives.isEmpty())
				{
				Iterator<EmpIncentives> iterator=empIncentives.iterator();
				List<EmpIncentivesTO> empIncentivesTOs=new ArrayList<EmpIncentivesTO>();
				
				while(iterator.hasNext()){
					EmpIncentives empIncen=iterator.next();
					if(empIncen!=null){
						EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
					
						if(empIncen.getId()>0)
						{
							empIncentivesTO.setId(empIncen.getId());
						}
						if(StringUtils.isNotEmpty(empIncen.getIncentivesAmount())){
							empIncentivesTO.setIncentivesAmount(empIncen.getIncentivesAmount());
						}
						
						if(StringUtils.isNotEmpty(empIncen.getIncentivesDetails())){
							empIncentivesTO.setIncentivesDetails(empIncen.getIncentivesDetails());
						}
						
						if(empIncen.getIncentivesDate()!=null){
							empIncentivesTO.setIncentivesDate(CommonUtil.ConvertStringToDateFormat(empIncen.getIncentivesDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empIncentivesTOs.add(empIncentivesTO);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpIncentives(empIncentivesTOs);
				}
				else
				{
					List<EmpIncentivesTO> list=new ArrayList<EmpIncentivesTO>();
					EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
					empIncentivesTO.setIncentivesAmount("");
					empIncentivesTO.setIncentivesDate("");
					empIncentivesTO.setIncentivesDetails("");
					objform.setIncentivesListSize(String.valueOf(list.size()));
					list.add(empIncentivesTO);
					objform.getEmployeeInfoTONew().setEmpIncentives(list);
				}
			}
			if(userApplicantDetails.getEmployee().getEmpLoan()!=null)
			{
				Set<EmpLoan> empLoan=userApplicantDetails.getEmployee().getEmpLoan();
				if(empLoan != null && !empLoan.isEmpty())
				{
				Iterator<EmpLoan> iterator=empLoan.iterator();
				List<EmpLoanTO> empLoanTOs=new ArrayList<EmpLoanTO>();
				
				while(iterator.hasNext()){
					EmpLoan eLoan=iterator.next();
					if(eLoan!=null){
						EmpLoanTO eLoanTO=new EmpLoanTO();
						if(eLoan.getId()>0)
						{
							eLoanTO.setId(eLoan.getId());
						}
						
						if(StringUtils.isNotEmpty(eLoan.getLoanAmount())){
							eLoanTO.setLoanAmount(eLoan.getLoanAmount());
						}
						
						if(StringUtils.isNotEmpty(eLoan.getLoanDetails())){
							eLoanTO.setLoanDetails(eLoan.getLoanDetails());
						}
						
						if(eLoan.getLoanDate()!=null){
							eLoanTO.setLoanDate(CommonUtil.ConvertStringToDateFormat(eLoan.getLoanDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empLoanTOs.add(eLoanTO);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpLoan(empLoanTOs);
				}
				else
				{
					List<EmpLoanTO> list=new ArrayList<EmpLoanTO>();
					EmpLoanTO emploanTo=new EmpLoanTO();
					emploanTo.setLoanAmount("");
					emploanTo.setLoanDate("");
					emploanTo.setLoanDetails("");
					objform.setLoanListSize(String.valueOf(list.size()));
					list.add(emploanTo);
					objform.getEmployeeInfoTONew().setEmpLoan(list);
				}
			}
			
			
			if(userApplicantDetails.getEmployee().getEmpRemarks()!=null)
			{
				Set<EmpRemarks> empRemarks=userApplicantDetails.getEmployee().getEmpRemarks();
				if(empRemarks != null && !empRemarks.isEmpty())
				{
				Iterator<EmpRemarks> iterator=empRemarks.iterator();
				List<EmpRemarksTO> empRemarkTOs=new ArrayList<EmpRemarksTO>();
				
				while(iterator.hasNext()){
					EmpRemarks eRemarks=iterator.next();
					if(eRemarks!=null){
						EmpRemarksTO eRemarksTO=new EmpRemarksTO();
						if(eRemarks.getId()>0)
						{
							eRemarksTO.setId(eRemarks.getId());
						}
						if(StringUtils.isNotEmpty(eRemarks.getRemarksDetails())){
							eRemarksTO.setRemarkDetails(eRemarks.getRemarksDetails());
						}
						
						if(StringUtils.isNotEmpty(eRemarks.getRemarksEnteredBy())){
							eRemarksTO.setEnteredBy(eRemarks.getRemarksEnteredBy());
						}
						
						if(eRemarks.getRemarksDate()!=null){
							eRemarksTO.setRemarkDate(CommonUtil.ConvertStringToDateFormat(eRemarks.getRemarksDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empRemarkTOs.add(eRemarksTO);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpRemarks(empRemarkTOs);
				}
				else
				{
				List<EmpRemarksTO> flist=new ArrayList<EmpRemarksTO>();
				EmpRemarksTO empRemarksTO=new EmpRemarksTO();
				empRemarksTO.setEnteredBy("");
				empRemarksTO.setRemarkDate("");
				empRemarksTO.setRemarkDetails("");
				objform.setRemarksListSize(String.valueOf(flist.size()));
				flist.add(empRemarksTO);
				
				
				objform.getEmployeeInfoTONew().setEmpRemarks(flist);
					
					}
				}


		/*	if(userApplicantDetails.getEmployee().getEmpLeaves()!=null)
			{
				Set<EmpLeave> empLeaves=userApplicantDetails.getEmployee().getEmpLeaves();
				if(empLeaves != null && !empLeaves.isEmpty())
				{
				Iterator<EmpLeave> iterator=empLeaves.iterator();
 				List<EmpLeaveAllotTO> empLeaveTOs=new ArrayList<EmpLeaveAllotTO>();
				
				while(iterator.hasNext()){
					EmpLeave eLeave=iterator.next();
					if(eLeave!=null){
						EmpLeaveAllotTO eLeaveTO=new EmpLeaveAllotTO();
						
						if(eLeave.getId()>0)
						{
							eLeaveTO.setEmpLeaveId(eLeave.getId());
						}
						
					if(eLeave.getEmpLeaveType().getId()>0){
						EmpLeaveType leavetype=new EmpLeaveType();
						leavetype.setId(eLeave.getEmpLeaveType().getId());
						eLeaveTO.setEmpLeaveType(leavetype);
					}										
						
						if(StringUtils.isNotEmpty(eLeave.getEmpLeaveType().getName())){
							eLeaveTO.setLeaveType(eLeave.getEmpLeaveType().getName());
						}
						
						if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesAllocated()))){
							eLeaveTO.setAllottedLeave(String.valueOf(eLeave.getLeavesAllocated()));
						}
						if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesSanctioned()))){
							eLeaveTO.setSanctionedLeave(String.valueOf(eLeave.getLeavesSanctioned()));
						}
												
						empLeaveTOs.add(eLeaveTO);
						}
			     }
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
				}
			else
			{
				String empTypeId=null;
				if(userApplicantDetails.getEmployee().getEmptype()!=null && userApplicantDetails.getEmployee().getEmptype().getId()>0)
				{
				empTypeId=String.valueOf(userApplicantDetails.getEmployee().getEmptype().getId());
				List<EmpLeaveAllotTO> empLeaveToList;
				try {
				empLeaveToList = EmployeeViewHandler.getInstance().getEmpLeaveList(empTypeId);
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}

		}*/
			//Leaves............................................
			
		/*	if(userApplicantDetails.getEmployee().getEmpLeaves()!=null)
			{
				int month=txn.getInitializationMonth(userApplicantDetails.getEmployee().getId());
				int currentMonth=currentMonth();
				int year=Calendar.getInstance().get(Calendar.YEAR);
				int year1=0;
				Set<EmpLeave> empLeaves=userApplicantDetails.getEmployee().getEmpLeaves();
				List<EmpLeaveAllotTO> extEmpLeaveTos = new ArrayList<EmpLeaveAllotTO>();
				List<EmpLeaveAllotTO> empLeaveTOs=new ArrayList<EmpLeaveAllotTO>();
				if(empLeaves != null && !empLeaves.isEmpty())
				{
				Iterator<EmpLeave> iterator=empLeaves.iterator();
 				
				
				while(iterator.hasNext()){
					EmpLeave eLeave=iterator.next();
					EmpLeaveAllotTO eLeaveTO=new EmpLeaveAllotTO();
					if(eLeave!=null){
						if(month==6 && currentMonth < month && year > eLeave.getYear()){
						      year1=year-1;
					     }
						if(eLeave.getYear()==year1){
						   if(eLeave.getId()>0)
						   {
						 	  eLeaveTO.setEmpLeaveId(eLeave.getId());
						   }
						
					       if(eLeave.getEmpLeaveType().getId()>0){
						      EmpLeaveType leavetype=new EmpLeaveType();
						      leavetype.setId(eLeave.getEmpLeaveType().getId());
						      eLeaveTO.setEmpLeaveType(leavetype);
					       }										
						
						   if(StringUtils.isNotEmpty(eLeave.getEmpLeaveType().getName())){
							   eLeaveTO.setLeaveType(eLeave.getEmpLeaveType().getName());
						   }
						
						   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesAllocated()))){
							   eLeaveTO.setAllottedLeave(String.valueOf(eLeave.getLeavesAllocated()));
						   }
						   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesSanctioned()))){
							   eLeaveTO.setSanctionedLeave(String.valueOf(eLeave.getLeavesSanctioned()));
						   }
						   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesRemaining()))){
							   eLeaveTO.setRemainingLeave(String.valueOf(eLeave.getLeavesRemaining()));
						   }
							if(StringUtils.isNotEmpty(String.valueOf(eLeave.getYear()))){
								eLeaveTO.setYear(eLeave.getYear());
							}
							String monthString = new DateFormatSymbols().getMonths()[month-1];

							if(StringUtils.isNotEmpty(monthString)){
								eLeaveTO.setMonth(monthString);
							}
							empLeaveTOs.add(eLeaveTO);
						}else{
							if(eLeave.getId()>0)
							   {
							 	  eLeaveTO.setEmpLeaveId(eLeave.getId());
							   }
							
						       if(eLeave.getEmpLeaveType().getId()>0){
							      EmpLeaveType leavetype=new EmpLeaveType();
							      leavetype.setId(eLeave.getEmpLeaveType().getId());
							      eLeaveTO.setEmpLeaveType(leavetype);
						       }										
							
							   if(StringUtils.isNotEmpty(eLeave.getEmpLeaveType().getName())){
								   eLeaveTO.setLeaveType(eLeave.getEmpLeaveType().getName());
							   }
							
							   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesAllocated()))){
								   eLeaveTO.setAllottedLeave(String.valueOf(eLeave.getLeavesAllocated()));
							   }
							   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesSanctioned()))){
								   eLeaveTO.setSanctionedLeave(String.valueOf(eLeave.getLeavesSanctioned()));
							   }
							   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesRemaining()))){
								   eLeaveTO.setRemainingLeave(String.valueOf(eLeave.getLeavesRemaining()));
							   }
								if(StringUtils.isNotEmpty(String.valueOf(eLeave.getYear()))){
									eLeaveTO.setYear(eLeave.getYear());
								}
								String monthString = new DateFormatSymbols().getMonths()[month-1];

								if(StringUtils.isNotEmpty(monthString)){
									eLeaveTO.setMonth(monthString);
								}
								extEmpLeaveTos.add(eLeaveTO);
						}
						
						}
			     }
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
				objform.setEmpLeaveAllotTo(extEmpLeaveTos);
				}
			else
			{
				String empTypeId=null;
				if(objform.getEmptypeId()!=null && !objform.getEmptypeId().isEmpty())
				{
				empTypeId=objform.getEmptypeId();
				List<EmpLeaveAllotTO> empLeaveToList;
				try {
				empLeaveToList = EmployeeViewHandler.getInstance().getEmpLeaveList(empTypeId,objform);
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			
			}*/


			
			
			
			
			if (userApplicantDetails.getEmployee().getEmpLeaves() != null) {
				int month=0;
				if(userApplicantDetails.getEmployee().getEmptype()!=null)
				month = txn.getInitializationMonth(userApplicantDetails.getEmployee().getEmptype().getId());
				int currentMonth = currentMonth();
				int year = Calendar.getInstance().get(Calendar.YEAR);
				int year1 = 0;
				Set<EmpLeave> empLeaves = userApplicantDetails.getEmployee().getEmpLeaves();
				List<EmpLeaveAllotTO> extEmpLeaveTos = new ArrayList<EmpLeaveAllotTO>();
				if (empLeaves != null && !empLeaves.isEmpty()) {
					Iterator<EmpLeave> iterator = empLeaves.iterator();
					List<EmpLeaveAllotTO> empLeaveTOs = new ArrayList<EmpLeaveAllotTO>();

					while (iterator.hasNext()) {
						EmpLeave eLeave = iterator.next();
						EmpLeaveAllotTO eLeaveTO = new EmpLeaveAllotTO();
						if (eLeave != null) {
							if (month == 6 && currentMonth < month
									&& year > eLeave.getYear()) {
								year1 = year - 1;
							} else {
								year1 = year;
							}
							if (eLeave.getYear() == year1) {
								if (eLeave.getId() > 0) {
									eLeaveTO.setEmpLeaveId(eLeave.getId());
								}

								if (eLeave.getEmpLeaveType().getId() > 0) {
									EmpLeaveType leavetype = new EmpLeaveType();
									leavetype.setId(eLeave.getEmpLeaveType()
											.getId());
									eLeaveTO.setEmpLeaveType(leavetype);
								}

								if (StringUtils.isNotEmpty(eLeave.getEmpLeaveType()
										.getName())) {
									eLeaveTO.setLeaveType(eLeave.getEmpLeaveType()
											.getName());
								}

								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesAllocated()))) {
									eLeaveTO.setAllottedLeave(String.valueOf(eLeave
											.getLeavesAllocated()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesSanctioned()))) {
									eLeaveTO.setSanctionedLeave(String
											.valueOf(eLeave.getLeavesSanctioned()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesRemaining()))) {
									eLeaveTO.setRemainingLeave(String
											.valueOf(eLeave.getLeavesRemaining()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getYear()))) {
									eLeaveTO.setYear(eLeave.getYear());
								}
								String monthString = new DateFormatSymbols()
										.getMonths()[month - 1];

								if (StringUtils.isNotEmpty(monthString)) {
									eLeaveTO.setMonth(monthString);
								}
								empLeaveTOs.add(eLeaveTO);
							} else {
								if (eLeave.getId() > 0) {
									eLeaveTO.setEmpLeaveId(eLeave.getId());
								}

								if (eLeave.getEmpLeaveType().getId() > 0) {
									EmpLeaveType leavetype = new EmpLeaveType();
									leavetype.setId(eLeave.getEmpLeaveType()
											.getId());
									eLeaveTO.setEmpLeaveType(leavetype);
								}

								if (StringUtils.isNotEmpty(eLeave.getEmpLeaveType()
										.getName())) {
									eLeaveTO.setLeaveType(eLeave.getEmpLeaveType()
											.getName());
								}

								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesAllocated()))) {
									eLeaveTO.setAllottedLeave(String.valueOf(eLeave
											.getLeavesAllocated()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesSanctioned()))) {
									eLeaveTO.setSanctionedLeave(String
											.valueOf(eLeave.getLeavesSanctioned()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesRemaining()))) {
									eLeaveTO.setRemainingLeave(String
											.valueOf(eLeave.getLeavesRemaining()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getYear()))) {
									eLeaveTO.setYear(eLeave.getYear());
								}
								String monthString = new DateFormatSymbols()
										.getMonths()[month - 1];

								if (StringUtils.isNotEmpty(monthString)) {
									eLeaveTO.setMonth(monthString);
								}
								extEmpLeaveTos.add(eLeaveTO);
							}

						}
					}
					Collections.sort(empLeaveTOs);
					objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
					objform.setEmpLeaveAllotTo(extEmpLeaveTos);
				} else {
					String empTypeId = null;
					if (objform.getEmptypeId() != null
							&& !objform.getEmptypeId().isEmpty()) {
						empTypeId = objform.getEmptypeId();
						List<EmpLeaveAllotTO> empLeaveToList;
						try {
							empLeaveToList = EmployeeViewHandler.getInstance().getEmpLeaveList(empTypeId, objform);
							Collections.sort(empLeaveToList);
							objform.getEmployeeInfoTONew().setEmpLeaveToList(
									empLeaveToList);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
			
			
			
			
			
			
			
			
			
			
			
			
			
//Leaves Code Ends.....................................
			if(userApplicantDetails.getEmployee().getEmpImmigrations()!=null)
			{
				Set<EmpImmigration> empImmigration=userApplicantDetails.getEmployee().getEmpImmigrations();
				Iterator<EmpImmigration> iterator=empImmigration.iterator();
				List<EmpImmigrationTO> empImmigrationTOs=new ArrayList<EmpImmigrationTO>();
				
				while(iterator.hasNext()){
					EmpImmigration eImmigration=iterator.next();
					if(eImmigration!=null){
						EmpImmigrationTO eImmigrationTO=new EmpImmigrationTO();
						
						
						if(eImmigration.getId()>0)
						{
							eImmigrationTO.setId(String.valueOf(eImmigration.getId()));
						}
						if(StringUtils.isNotEmpty(eImmigration.getPassportComments())){
							eImmigrationTO.setPassportComments(eImmigration.getPassportComments());
						}
						
						if(StringUtils.isNotEmpty(eImmigration.getPassportNo())){
							eImmigrationTO.setPassportNo(eImmigration.getPassportNo());
						}
						if(StringUtils.isNotEmpty(eImmigration.getPassportReviewStatus())){
							eImmigrationTO.setPassportReviewStatus(eImmigration.getPassportReviewStatus());
						}
						if(StringUtils.isNotEmpty(eImmigration.getPassportStatus())){
							eImmigrationTO.setPassportStatus(eImmigration.getPassportStatus());
						}
						if(eImmigration.getCountryByPassportCountryId()!=null && eImmigration.getCountryByPassportCountryId().getId()>0){
							
							eImmigrationTO.setPassportCountryId(String.valueOf(eImmigration.getCountryByPassportCountryId().getName()));
						}
						if(eImmigration.getPassportDateOfExpiry()!=null){
							eImmigrationTO.setPassportExpiryDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getPassportDateOfExpiry().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(eImmigration.getPassportIssueDate()!=null){
							eImmigrationTO.setPassportIssueDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getPassportIssueDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaComments())){
							eImmigrationTO.setVisaComments(eImmigration.getVisaComments());
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaNo())){
							eImmigrationTO.setVisaNo(eImmigration.getVisaNo());
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaReviewStatus())){
							eImmigrationTO.setVisaReviewStatus(eImmigration.getVisaReviewStatus());
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaStatus())){
							eImmigrationTO.setVisaStatus(eImmigration.getVisaStatus());
						}
						if(eImmigration.getCountryByVisaCountryId()!=null && eImmigration.getCountryByVisaCountryId().getId()>0){
						
							eImmigrationTO.setVisaCountryId(String.valueOf(eImmigration.getCountryByVisaCountryId().getName()));
						}
						if(eImmigration.getVisaDateOfExpiry()!=null){
							eImmigrationTO.setVisaExpiryDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getVisaDateOfExpiry().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(eImmigration.getVisaIssueDate()!=null){
							eImmigrationTO.setVisaIssueDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getVisaIssueDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empImmigrationTOs.add(eImmigrationTO);
					
				
			}
					objform.getEmployeeInfoTONew().setEmpImmigration(empImmigrationTOs);	
				}
				}
			
			
			
			if(userApplicantDetails.getEmployee().getEmpPayAllowance()!=null)
			{
				Set<EmpPayAllowanceDetails> empPayAllowanceDetails=userApplicantDetails.getEmployee().getEmpPayAllowance();
				if(empPayAllowanceDetails != null && !empPayAllowanceDetails.isEmpty())
				{
				Iterator<EmpPayAllowanceDetails> iterator=empPayAllowanceDetails.iterator();
				//List<EmpPayAllowanceTO> empPayAllowanceTOs=new ArrayList<EmpPayAllowanceTO>();
				List<EmpAllowanceTO> empPayAllowanceTOs=new ArrayList<EmpAllowanceTO>();
				
				List<EmpAllowanceTO> fixed=null;
				if(objform.getEmployeeInfoTONew()!=null){
				if(objform.getEmployeeInfoTONew().getPayscaleFixedTo()!=null){
				fixed=objform.getEmployeeInfoTONew().getPayscaleFixedTo();
				}
				
				//
				while(iterator.hasNext()){
					//EmpPayAllowanceTO ePayAllotTO=new EmpPayAllowanceTO();
					EmpPayAllowanceDetails ePayAlloDet=iterator.next();
					EmpAllowanceTO eAllotTO=new EmpAllowanceTO();
					if(ePayAlloDet!=null){
						
						if(fixed!=null){
							Iterator<EmpAllowanceTO> iterator2=fixed.iterator();
							while(iterator2.hasNext()){
								EmpAllowanceTO empAllTO=iterator2.next();
							if(empAllTO!=null && (empAllTO.getId()>0)){
							if(ePayAlloDet.getEmpAllowance()!= null && ePayAlloDet.getEmpAllowance().getId()>0){
								if(empAllTO.getId()==(ePayAlloDet.getEmpAllowance().getId())){
									eAllotTO.setId(ePayAlloDet.getEmpAllowance().getId());	
									//check the above line......
									if(ePayAlloDet.getId()>0){
										//ePayAllotTO.setId(ePayAlloDet.getId());
										eAllotTO.setEmpPayAllowanceId(ePayAlloDet.getId());
									}
									
									if(StringUtils.isNotEmpty(ePayAlloDet.getAllowanceValue())){
										eAllotTO.setAllowanceName(ePayAlloDet.getAllowanceValue());
										//ePayAllotTO.setAllowanceValue(eAllotTO.getAllowanceName());
									}
									
									if(StringUtils.isNotEmpty(String.valueOf(empAllTO.getName()))){
										eAllotTO.setName(empAllTO.getName());
										
									}
									eAllotTO.setDisplayOrder(empAllTO.getDisplayOrder());
									empPayAllowanceTOs.add(eAllotTO);
									
								}
							}
			     }
							}
						}
					}
				}
				Collections.sort(empPayAllowanceTOs,new PayAllowance());
				objform.getEmployeeInfoTONew().setPayscaleFixedTo(empPayAllowanceTOs);
				}
		
				}
				else
				{
					 try {
						 List<EmpAllowanceTO> payscaleFixedTo=txn.getPayAllowanceFixedMap();
					
					 if(payscaleFixedTo!=null){
						 objform.getEmployeeInfoTONew().setPayscaleFixedTo(payscaleFixedTo);
					 }
					 } catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
				}
				}
			
			
			
			
	
			if(userApplicantDetails.getEmployee().getPreviousExpSet()!=null){
				int teachingFlag=0;
				int industryFlag=0;
				Set<EmpPreviousExperience> empOnlinePreviousExperiencesSet=userApplicantDetails.getEmployee().getPreviousExpSet();
				if(empOnlinePreviousExperiencesSet != null && !empOnlinePreviousExperiencesSet.isEmpty()){
					
					Iterator<EmpPreviousExperience> iterator=empOnlinePreviousExperiencesSet.iterator();
					List<EmpPreviousOrgTo> industryExp=new ArrayList<EmpPreviousOrgTo>();
					List<EmpPreviousOrgTo> teachingExp=new ArrayList<EmpPreviousOrgTo>();
					while(iterator.hasNext()){
						EmpPreviousExperience empOnlinePreviousExperiences=iterator.next();
						if(empOnlinePreviousExperiences!=null){
							EmpPreviousOrgTo empOnliPreviousExperienceTO=new EmpPreviousOrgTo();
							
							if(empOnlinePreviousExperiences.isIndustryExperience())
							{
								if(empOnlinePreviousExperiences.getId()>0){
									empOnliPreviousExperienceTO.setId(empOnlinePreviousExperiences.getId());
								}
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpDesignation())){
									empOnliPreviousExperienceTO.setCurrentDesignation(empOnlinePreviousExperiences.getEmpDesignation());
								}
								
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpOrganization())){
									empOnliPreviousExperienceTO.setCurrentOrganisation(empOnlinePreviousExperiences.getEmpOrganization());
								}
								
								if( empOnlinePreviousExperiences.getExpMonths()>0 ){
									empOnliPreviousExperienceTO.setIndustryExpMonths(String.valueOf(empOnlinePreviousExperiences.getExpMonths()));
								}
								
								if(empOnlinePreviousExperiences.getExpYears()>0){
									empOnliPreviousExperienceTO.setIndustryExpYears(String.valueOf(empOnlinePreviousExperiences.getExpYears()));
								}
								/*code added by sudhir */
								if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
									empOnliPreviousExperienceTO.setIndustryFromDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getFromDate()));
								}
								if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
									empOnliPreviousExperienceTO.setIndustryToDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getToDate()));
								}
									/*code added by sudhir */
								industryFlag=1;
								industryExp.add(empOnliPreviousExperienceTO);
							}else if(empOnlinePreviousExperiences.isTeachingExperience())
							{
								if(empOnlinePreviousExperiences.getId()>0){
									empOnliPreviousExperienceTO.setId(empOnlinePreviousExperiences.getId());
								}
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpDesignation())){
									empOnliPreviousExperienceTO.setCurrentTeachnigDesignation(empOnlinePreviousExperiences.getEmpDesignation());
								}
								
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpOrganization())){
									empOnliPreviousExperienceTO.setCurrentTeachingOrganisation(empOnlinePreviousExperiences.getEmpOrganization());
								}
								
								if( empOnlinePreviousExperiences.getExpMonths()>0 ){
									empOnliPreviousExperienceTO.setTeachingExpMonths(String.valueOf(empOnlinePreviousExperiences.getExpMonths()));
								}
								
								if(empOnlinePreviousExperiences.getExpYears()>0){
									empOnliPreviousExperienceTO.setTeachingExpYears(String.valueOf(empOnlinePreviousExperiences.getExpYears()));
								}
								/*code added by sudhir */
								if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
									empOnliPreviousExperienceTO.setTeachingFromDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getFromDate()));
								}
								if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
									empOnliPreviousExperienceTO.setTeachingToDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getToDate()));
								}
									/*code added by sudhir */
								teachingFlag=1;
								teachingExp.add(empOnliPreviousExperienceTO);
																
							}
						}
							objform.getEmployeeInfoTONew().setExperiences(industryExp);
							objform.getEmployeeInfoTONew().setTeachingExperience(teachingExp);
					}
				}else {
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					
					List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					/*code added by sudhir */
					empPreviousOrgTo.setTeachingFromDate("");
					empPreviousOrgTo.setTeachingToDate("");
					/*--------------------- */
					objform.setTeachingExpLength(String.valueOf(teachingList.size()));
					teachingList.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
				}
				if(teachingFlag==1 && industryFlag==0)
				{
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
				}
				if(teachingFlag==0 && industryFlag==1)
				{
					List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					/*code added by sudhir */
					empPreviousOrgTo.setTeachingFromDate("");
					empPreviousOrgTo.setTeachingToDate("");
					/*--------------------- */
					objform.setTeachingExpLength(String.valueOf(teachingList.size()));
					teachingList.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
				}
				if(teachingFlag==1 && industryFlag==0)
				{
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
				}
				if(teachingFlag==0 && industryFlag==0)
				{
					List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					/*code added by sudhir */
					empPreviousOrgTo.setTeachingFromDate("");
					empPreviousOrgTo.setTeachingToDate("");
					/*--------------------- */
					objform.setTeachingExpLength(String.valueOf(teachingList.size()));
					teachingList.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
					
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
				}
			}
			/*----------------------------------- code added by sudhir---------------------------------------*/
			/* calculating Experience in cjc (or) cu from joining Date to present day*/
			Date joiningDate = null;
			String fromDateDay = null;
			String fromDateMonth = null;
			if(objform.getDateOfJoining()!=null && !objform.getDateOfJoining().isEmpty()){
				 joiningDate = CommonUtil.ConvertStringToDate(objform.getDateOfJoining());
				 fromDateDay = objform.getDateOfJoining().substring(0, 2);
				 fromDateMonth = objform.getDateOfJoining().substring(3, 5);
			}
			String todaysDate = CommonUtil.getTodayDate();
			String toDateDay = todaysDate.substring(0, 2);
			String toDateMonth = todaysDate.substring(3, 5);
			Date toDate = CommonUtil.ConvertStringToDate(todaysDate);
			if(joiningDate!=null && toDate!=null){
				
			/*int years = CommonUtil.getYearsDiff(joiningDate,toDate);
			int months = CommonUtil.getMonthsBetweenTwoYears(joiningDate, toDate,fromDate,toDateNumber);*/
				double msPerGregorianYear = 365.25 * 86400 * 1000;
			 	double years1 =(toDate.getTime() - joiningDate.getTime()) / msPerGregorianYear;
			 	int yy = (int) years1;
		        int mm = (int) ((years1 - yy) * 12);
		        if(fromDateDay.equals(toDateDay)){
		        	if(fromDateMonth.equals(toDateMonth)){
		        		mm=0;
		        		yy = (int) Math.round(years1);
		        	}
		        }
		        if(fromDateDay.equals(toDateDay)){
		        	if(!fromDateMonth.equals(toDateMonth)){
		        		mm = (int) Math.round(((years1 - yy) * 12));
		        	}
		        }
			objform.setExperienceInYears(yy);
			objform.setExperienceInMonths(mm);
			}
			/* calculating TotalCurrent Experience Years and Months based on Recognised Experience and Experience in cjc (or) cu
			int totalYears = Integer.parseInt(objform.getRelevantExpYears())+objform.getExperienceInYears();
			int totalMonths1 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getExperienceInMonths() % 12;
			int totalMonths2 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getExperienceInMonths() / 12;
			totalYears = totalYears + totalMonths2;
			objform.setTotalCurrentExpYears(totalYears);
			objform.setTotalCurrentExpMonths(totalMonths1);*/
			
			/*-----------------------------------------------------------------------------------------------*/
			if(userApplicantDetails.getEmployee().getExtensionNumber()!=null && !userApplicantDetails.getEmployee().getExtensionNumber().isEmpty()){
				objform.setExtensionNumber(userApplicantDetails.getEmployee().getExtensionNumber());
			}
			
			//For Showing Employee Information New by Bhargav 
			EmployeeInfoEditNewTO employeeInfoEditNewTO = new EmployeeInfoEditNewTO();
			if (userApplicantDetails.getEmployee().getEmpAcademicDeatils()  != null) {
				Set<EmpAcademicQualificationBO> empAcademic = userApplicantDetails.getEmployee()
						.getEmpAcademicDeatils();
				if (empAcademic != null && !empAcademic.isEmpty()) {
					Iterator<EmpAcademicQualificationBO> iterator = empAcademic.iterator();
					List<EmpAcademicQualificationTO> empAcademicQualificationTOs = new ArrayList<EmpAcademicQualificationTO>();

					while (iterator.hasNext()) {
						EmpAcademicQualificationBO empAcademicbo = iterator.next();
						if (empAcademic != null) {
							EmpAcademicQualificationTO empAcademicTo = new EmpAcademicQualificationTO();

							if (empAcademicTo.getId() > 0) {
								empAcademicTo.setId(empAcademicbo.getId());
							}
							if (StringUtils.isNotEmpty(empAcademicbo
									.getCourseName())) {
								empAcademicTo.setCourseName(empAcademicbo
										.getCourseName());
							}

							if (StringUtils.isNotEmpty(empAcademicbo.getUniversity())) {
								empAcademicTo.setUniversityName(empAcademicbo.getUniversity());
							}
							if (StringUtils.isNotEmpty(empAcademicbo.getYear())) {
								empAcademicTo.setYear(empAcademicbo.getYear());
							}
							if (StringUtils.isNotEmpty(empAcademicbo.getGrade())) {
								empAcademicTo.setGrade(empAcademicbo.getGrade());
							}


							empAcademicQualificationTOs.add(empAcademicTo);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpqualification(empAcademicQualificationTOs);
					employeeInfoEditNewTO.setEmpqualification(empAcademicQualificationTOs);
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpIntrestDetails()  != null) {
				Set<EmpIntrestsBO> empIntrestsBOs =userApplicantDetails.getEmployee().getEmpIntrestDetails();
				if (empIntrestsBOs != null && !empIntrestsBOs.isEmpty()) {
					Iterator<EmpIntrestsBO> iterator = empIntrestsBOs.iterator();
					List<EmpInterestsTO> empInterestsTOs = new ArrayList<EmpInterestsTO>();

					while (iterator.hasNext()) {
						EmpIntrestsBO empIntrestsBO = iterator.next();
						if (empIntrestsBO != null) {
							EmpInterestsTO empInterestsTO = new EmpInterestsTO();

							if (empInterestsTO.getId() > 0) {
								empInterestsTO.setId(empIntrestsBO.getId());
							}
							if (StringUtils.isNotEmpty(empIntrestsBO
									.getTopic())) {
								empInterestsTO.setTopic(empIntrestsBO
										.getTopic());
							}

							empInterestsTOs.add(empInterestsTO);

						}
					}
//				objform.getEmployeeInfoEditNewTO().setInterest(empInterestsTOs);
					employeeInfoEditNewTO.setInterest(empInterestsTOs);
				}
			}
			
			
			if (userApplicantDetails.getEmployee().getEmpResearchDetails()  != null) {
				Set<EmpResearchBo> empResearchBos =userApplicantDetails.getEmployee().getEmpResearchDetails();
				if (empResearchBos != null && !empResearchBos.isEmpty()) {
					Iterator<EmpResearchBo> iterator = empResearchBos.iterator();
					List<EmpFieldResearchTO> empResearchTOs = new ArrayList<EmpFieldResearchTO>();

					while (iterator.hasNext()) {
						EmpResearchBo empResearchBo = iterator.next();
						if (empResearchBo != null) {
							EmpFieldResearchTO empResearchTO = new EmpFieldResearchTO();

							if (empResearchTO.getId() > 0) {
								empResearchTO.setId(empResearchBo.getId());
							}
							if (StringUtils.isNotEmpty(empResearchBo
									.getTitle())) {
								empResearchTO.setTopic(empResearchBo
										.getTitle());
							}

							empResearchTOs.add(empResearchTO);

						}
					}
//				objform.getEmployeeInfoEditNewTO().setResearch(empResearchTOs);
					employeeInfoEditNewTO.setResearch(empResearchTOs);
				} 
			}
			
			
			if (userApplicantDetails.getEmployee().getEmpGuideshipDetails()  != null) {
				Set<EmpGuideshipDetailsBo> empGuideshipDetailsBos =userApplicantDetails.getEmployee().getEmpGuideshipDetails();
				if (empGuideshipDetailsBos != null && !empGuideshipDetailsBos.isEmpty()) {
					Iterator<EmpGuideshipDetailsBo> iterator = empGuideshipDetailsBos.iterator();
					List<EmpGuideShipDetailsTO> empGuideShipDetailsTOs = new ArrayList<EmpGuideShipDetailsTO>();

					while (iterator.hasNext()) {
						EmpGuideshipDetailsBo empGuideshipDetailsBo = iterator.next();
						if (empGuideshipDetailsBo != null) {
							EmpGuideShipDetailsTO empGuideShipDetailsTO = new EmpGuideShipDetailsTO();

							if (empGuideShipDetailsTO.getId() > 0) {
								empGuideShipDetailsTO.setId(empGuideshipDetailsBo.getId());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getScholarName())) {
								empGuideShipDetailsTO.setPhdScholarName(empGuideshipDetailsBo
										.getScholarName());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getRegistraionYear())) {
								empGuideShipDetailsTO.setRegistrationYear(empGuideshipDetailsBo
										.getRegistraionYear());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getAwarded())) {
								empGuideShipDetailsTO.setAwarded(empGuideshipDetailsBo
										.getAwarded());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getAwardedYear())) {
								empGuideShipDetailsTO.setYear(empGuideshipDetailsBo
										.getAwardedYear());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getAwardedThesisName())) {
								empGuideShipDetailsTO.setThesisName(empGuideshipDetailsBo
										.getAwardedThesisName());
							}

							empGuideShipDetailsTOs.add(empGuideShipDetailsTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setGuideship(empGuideShipDetailsTOs);
					employeeInfoEditNewTO.setGuideship(empGuideShipDetailsTOs);
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpDutiesDeatils()  != null) {
				Set<EmpDutiesDetailsBO> empDutiesDetailsBOs =userApplicantDetails.getEmployee().getEmpDutiesDeatils();
				if (empDutiesDetailsBOs != null && !empDutiesDetailsBOs.isEmpty()) {
					Iterator<EmpDutiesDetailsBO> iterator = empDutiesDetailsBOs.iterator();
					List<EmpDutiesPerformedTO> empDutiesPerformedTOs = new ArrayList<EmpDutiesPerformedTO>();

					while (iterator.hasNext()) {
						EmpDutiesDetailsBO empDutiesDetailsBO = iterator.next();
						if (empDutiesDetailsBO != null) {
							EmpDutiesPerformedTO empDutiesPerformedTO = new EmpDutiesPerformedTO();

							if (empDutiesPerformedTO.getId() > 0) {
								empDutiesPerformedTO.setId(empDutiesDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empDutiesDetailsBO
									.getPosition())) {
								empDutiesPerformedTO.setPositionName(empDutiesDetailsBO
										.getPosition());
							}
							if (empDutiesDetailsBO
									.getFromDate() != null) {
								empDutiesPerformedTO.setFromDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							if (empDutiesDetailsBO.getToDate() != null) {
								empDutiesPerformedTO.setToDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							

							empDutiesPerformedTOs.add(empDutiesPerformedTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setDuties(empDutiesPerformedTOs);
					employeeInfoEditNewTO.setDuties(empDutiesPerformedTOs);
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpProjectDetails()  != null) {
				Set<EmpProjectResearchDetailsBO> empProjectResearchDetailsBOs =userApplicantDetails.getEmployee().getEmpProjectDetails();
				if (empProjectResearchDetailsBOs != null && !empProjectResearchDetailsBOs.isEmpty()) {
					Iterator<EmpProjectResearchDetailsBO> iterator = empProjectResearchDetailsBOs.iterator();
					List<EmpResearchTO> empResearchProjectTOs = new ArrayList<EmpResearchTO>();

					while (iterator.hasNext()) {
						EmpProjectResearchDetailsBO empResearchDetailsBO = iterator.next();
						if (empResearchDetailsBO != null) {
							EmpResearchTO empResearchProjectTO = new EmpResearchTO();

							if (empResearchProjectTO.getId() > 0) {
								empResearchProjectTO.setId(empResearchDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle()
									)) {
								empResearchProjectTO.setProjectName(empResearchDetailsBO.getPrjectName());
							}
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle())) {
						       empResearchProjectTO.setTitle(empResearchDetailsBO.getTitle());
					        }
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getFundingAgency())) {
						   empResearchProjectTO.setFindingAgencyName(empResearchDetailsBO.getFundingAgency());
					        }
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getAmount())) {
						      empResearchProjectTO.setAmount(empResearchDetailsBO.getAmount());
					           }
							if (empResearchDetailsBO
									.getFromDate() != null) {
								empResearchProjectTO.setFromDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO
										.getFromDate() .toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							if (empResearchDetailsBO.getToDate() != null) {
								empResearchProjectTO.setToDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							

							empResearchProjectTOs.add(empResearchProjectTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setResearchProject(empResearchProjectTOs);
					employeeInfoEditNewTO.setResearchProject(empResearchProjectTOs);
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpResearchPublication() != null) {
				Set<EmpReaserchPublishcationDetailsBO> empProjePublishcationDetailsBOs =userApplicantDetails.getEmployee().getEmpResearchPublication();
				if (empProjePublishcationDetailsBOs != null && !empProjePublishcationDetailsBOs.isEmpty()) {
					Iterator<EmpReaserchPublishcationDetailsBO> iterator = empProjePublishcationDetailsBOs.iterator();
					List<EmpResearchPublicationTO> empResearchPublicationTOs = new ArrayList<EmpResearchPublicationTO>();

					while (iterator.hasNext()) {
						EmpReaserchPublishcationDetailsBO empReaserchPublishcationDetailsBO = iterator.next();
						if (empReaserchPublishcationDetailsBO != null) {
							EmpResearchPublicationTO empResearchPublicationTO = new EmpResearchPublicationTO();

							if (empResearchPublicationTO.getId() > 0) {
								empResearchPublicationTO.setId(empReaserchPublishcationDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getTitle())) {
								empResearchPublicationTO.setPaperTitle(empReaserchPublishcationDetailsBO.getTitle());
							}
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getJournalName())) {
								empResearchPublicationTO.setJournalName(empReaserchPublishcationDetailsBO.getJournalName());
					        }
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getUGC())) {
								empResearchPublicationTO.setUGCNonUGC(empReaserchPublishcationDetailsBO.getUGC());
					        }
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getISBNISSNNo())) {
								empResearchPublicationTO.setISBNISSNNo(empReaserchPublishcationDetailsBO.getISBNISSNNo());
					        }
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getYear())) {
								empResearchPublicationTO.setYear(empReaserchPublishcationDetailsBO.getYear());
					        }
							
							empResearchPublicationTOs.add(empResearchPublicationTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setResearchPubliction(empResearchPublicationTOs);
					employeeInfoEditNewTO.setResearchPubliction(empResearchPublicationTOs);
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpBooksPublished() != null) {
				Set<EmpBooksPublishedDetailsBO> empBooksPublishedDetailsBOs =userApplicantDetails.getEmployee().getEmpBooksPublished();
				if (empBooksPublishedDetailsBOs != null && !empBooksPublishedDetailsBOs.isEmpty()) {
					Iterator<EmpBooksPublishedDetailsBO> iterator = empBooksPublishedDetailsBOs.iterator();
					List<EmpBooksPublishedTO> empBooksPublishedTOs = new ArrayList<EmpBooksPublishedTO>();

					while (iterator.hasNext()) {
						EmpBooksPublishedDetailsBO empBooksPublishedDetailsBO = iterator.next();
						if (empBooksPublishedDetailsBO != null) {
							EmpBooksPublishedTO empBooksPublishedTO = new EmpBooksPublishedTO();

							if (empBooksPublishedTO.getId() > 0) {
								empBooksPublishedTO.setId(empBooksPublishedDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getBookName())) {
								empBooksPublishedTO.setTitleName(empBooksPublishedDetailsBO.getBookName());
							}
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getPublisherName())) {
								empBooksPublishedTO.setPublisherName(empBooksPublishedDetailsBO.getPublisherName());
					        }
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getContribution())) {
								empBooksPublishedTO.setContibutionName(empBooksPublishedDetailsBO.getContribution());
					        }
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getYear())) {
								empBooksPublishedTO.setYear(empBooksPublishedDetailsBO.getYear());
					        }
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getISBNISSNNo())) {
								empBooksPublishedTO.setISBNISSN(empBooksPublishedDetailsBO.getISBNISSNNo());
					        }
							
							empBooksPublishedTOs.add(empBooksPublishedTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpBooks(empBooksPublishedTOs);
					employeeInfoEditNewTO.setEmpBooks(empBooksPublishedTOs);
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpPaperPresentaion() != null) {
				Set<EmpPaperPresentationBO> empPaperPresentationBOs =userApplicantDetails.getEmployee().getEmpPaperPresentaion();
				if (empPaperPresentationBOs != null && !empPaperPresentationBOs.isEmpty()) {
					Iterator<EmpPaperPresentationBO> iterator = empPaperPresentationBOs.iterator();
					List<EmpPaperPresentationTO> empPresentationTOs = new ArrayList<EmpPaperPresentationTO>();

					while (iterator.hasNext()) {
						EmpPaperPresentationBO empPaperPresentationBO = iterator.next();
						if (empPaperPresentationBO != null) {
							EmpPaperPresentationTO empPaperPresentationTO = new EmpPaperPresentationTO();

							if (empPaperPresentationTO.getId() > 0) {
								empPaperPresentationTO.setId(empPaperPresentationBO.getId());
							}
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getInstitution())) {
								empPaperPresentationTO.setOrganisation(empPaperPresentationBO.getInstitution());
							}
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getInterRegoinal())) {
								empPaperPresentationTO.setRegional(empPaperPresentationBO.getInterRegoinal());
					        }
							if (empPaperPresentationBO.getDate() != null) {
								empPaperPresentationTO.setDate1(CommonUtil.ConvertStringToDateFormat(empPaperPresentationBO.getDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getProceedingsTile())) {
								empPaperPresentationTO.setSeminarName(empPaperPresentationBO.getProceedingsTile());
					        }
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getPaperTile())) {
								empPaperPresentationTO.setPaperName(empPaperPresentationBO.getPaperTile());
					        }
							
							empPresentationTOs.add(empPaperPresentationTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpPaper(empPresentationTOs);
					employeeInfoEditNewTO.setEmpPaper(empPresentationTOs);
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpSeminarAttended() != null) {
				Set<EmpSeminarAttendedDetailsBO> empSeminarAttendedDetailsBOs =userApplicantDetails.getEmployee().getEmpSeminarAttended();
				if (empSeminarAttendedDetailsBOs != null && !empSeminarAttendedDetailsBOs.isEmpty()) {
					Iterator<EmpSeminarAttendedDetailsBO> iterator = empSeminarAttendedDetailsBOs.iterator();
					List<EmpSeminarAttendedDetailsTO> empSeminarAttendedDetailsTOs = new ArrayList<EmpSeminarAttendedDetailsTO>();

					while (iterator.hasNext()) {
						EmpSeminarAttendedDetailsBO empSeminarAttendedDetailsBO = iterator.next();
						if (empSeminarAttendedDetailsBO != null) {
							EmpSeminarAttendedDetailsTO empSeminarAttendedDetailsTO = new EmpSeminarAttendedDetailsTO();

							if (empSeminarAttendedDetailsTO.getId() > 0) {
								empSeminarAttendedDetailsTO.setId(empSeminarAttendedDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInstitution())) {
								empSeminarAttendedDetailsTO.setOrganisation(empSeminarAttendedDetailsBO.getInstitution());
							}
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInterRegional())) {
								empSeminarAttendedDetailsTO.setInterRegional(empSeminarAttendedDetailsBO.getInterRegional());
					        }
							if (empSeminarAttendedDetailsBO.getFromDate2() != null) {
								empSeminarAttendedDetailsTO.setFromDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getFromDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (empSeminarAttendedDetailsBO.getToDate2() != null) {
								empSeminarAttendedDetailsTO.setToDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getToDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getParticipation())) {
								empSeminarAttendedDetailsTO.setParticipation(empSeminarAttendedDetailsBO.getParticipation());
					        }
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getTitle())) {
								empSeminarAttendedDetailsTO.setSeminarName(empSeminarAttendedDetailsBO.getTitle());
					        }
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getWorkShop())) {
								empSeminarAttendedDetailsTO.setSeminar(empSeminarAttendedDetailsBO.getWorkShop());
					        }
							
							empSeminarAttendedDetailsTOs.add(empSeminarAttendedDetailsTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpSeminarAttendedDetails(empSeminarAttendedDetailsTOs);
					employeeInfoEditNewTO.setEmpSeminarAttendedDetails(empSeminarAttendedDetailsTOs);
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpProfeDevelopment() != null) {
				Set<EmpProfessionalDevelopmentBO> empProfessionalDevelopmentBOs =userApplicantDetails.getEmployee().getEmpProfeDevelopment();
				if (empProfessionalDevelopmentBOs != null && !empProfessionalDevelopmentBOs.isEmpty()) {
					Iterator<EmpProfessionalDevelopmentBO> iterator = empProfessionalDevelopmentBOs.iterator();
					List<EmpProfessionalDevelopmentTO> empProfessionalDevelopmentTOs = new ArrayList<EmpProfessionalDevelopmentTO>();

					while (iterator.hasNext()) {
						EmpProfessionalDevelopmentBO empProfessionalDevelopmentBO = iterator.next();
						if (empProfessionalDevelopmentBO != null) {
							EmpProfessionalDevelopmentTO empProfessionalDevelopmentTO = new EmpProfessionalDevelopmentTO();

							if (empProfessionalDevelopmentTO.getId() > 0) {
								empProfessionalDevelopmentTO.setId(empProfessionalDevelopmentBO.getId());
							}
							if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getName())) {
								empProfessionalDevelopmentTO.setName(empProfessionalDevelopmentBO.getName());
							}
							if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getInstitute())) {
								empProfessionalDevelopmentTO.setOrganisation(empProfessionalDevelopmentBO.getInstitute());
					        }
							if (empProfessionalDevelopmentBO.getFromDate() != null) {
								empProfessionalDevelopmentTO.setFromDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (empProfessionalDevelopmentBO.getToDate() != null) {
								empProfessionalDevelopmentTO.setToDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							
							empProfessionalDevelopmentTOs.add(empProfessionalDevelopmentTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpPersonaldevelopment(empProfessionalDevelopmentTOs);
					employeeInfoEditNewTO.setEmpPersonaldevelopment(empProfessionalDevelopmentTOs);
					
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpAwardDetails() != null) {
				Set<EmpAwardDetailsBO> empAwardDetailsBOs =userApplicantDetails.getEmployee().getEmpAwardDetails();
				if (empAwardDetailsBOs != null && !empAwardDetailsBOs.isEmpty()) {
					Iterator<EmpAwardDetailsBO> iterator = empAwardDetailsBOs.iterator();
					List<EmpAwardTO> empAwardTOs = new ArrayList<EmpAwardTO>();

					while (iterator.hasNext()) {
						EmpAwardDetailsBO empAwardDetailsBO = iterator.next();
						if (empAwardDetailsBO != null) {
							EmpAwardTO empAwardTO = new EmpAwardTO();

							if (empAwardTO.getId() > 0) {
								empAwardTO.setId(empAwardDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getActivityName())) {
								empAwardTO.setActivityname(empAwardDetailsBO.getActivityName());
							}
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getAwardName())) {
								empAwardTO.setAwardName(empAwardDetailsBO.getAwardName());
					        }
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
								empAwardTO.setAwardbodyName(empAwardDetailsBO.getAwardBody());
					        }
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getYear())) {
								empAwardTO.setYear(empAwardDetailsBO.getYear());
					        }
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
								empAwardTO.setRecognitionName(empAwardDetailsBO.getAward());
					        }
							empAwardTOs.add(empAwardTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpAward(empAwardTOs);
					employeeInfoEditNewTO.setEmpAward(empAwardTOs);
				} 
			}
			
			if (userApplicantDetails.getEmployee().getEmpMemDetailsBOs() != null) {
				Set<EmpMemeberShipDetailsBO> empMemeberShipDetailsBOs =userApplicantDetails.getEmployee().getEmpMemDetailsBOs();
				if (empMemeberShipDetailsBOs != null && !empMemeberShipDetailsBOs.isEmpty()) {
					Iterator<EmpMemeberShipDetailsBO> iterator = empMemeberShipDetailsBOs.iterator();
					List<EmpMemberShipAcademicBodyTO> empMemberShipAcademicBodyTOs = new ArrayList<EmpMemberShipAcademicBodyTO>();

					while (iterator.hasNext()) {
						EmpMemeberShipDetailsBO empMemeberShipDetailsBO = iterator.next();
						if (empMemeberShipDetailsBO != null) {
							EmpMemberShipAcademicBodyTO empMemberShipAcademicBodyTO = new EmpMemberShipAcademicBodyTO();

							if (empMemberShipAcademicBodyTO.getId() > 0) {
								empMemberShipAcademicBodyTO.setId(empMemeberShipDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empMemeberShipDetailsBO.getName())) {
								empMemberShipAcademicBodyTO.setName(empMemeberShipDetailsBO.getName());
							}
							if (empMemeberShipDetailsBO.getFromDate() != null) {
								empMemberShipAcademicBodyTO.setFromDate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (empMemeberShipDetailsBO.getToDate() != null) {
								empMemberShipAcademicBodyTO.setTodate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							
							empMemberShipAcademicBodyTOs.add(empMemberShipAcademicBodyTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpMemberShip(empMemberShipAcademicBodyTOs);
					employeeInfoEditNewTO.setEmpMemberShip(empMemberShipAcademicBodyTOs);
				} 
			}
			objform.setEmployeeInfoEditNewTO(employeeInfoEditNewTO);
			
			
			}
			if(userApplicantDetails!=null && userApplicantDetails.getGuest()!=null){
				
					
				
				if (userApplicantDetails.getGuest().getEligibilityTest() != null && !userApplicantDetails.getGuest().getEligibilityTest().isEmpty()) {
					if(userApplicantDetails.getGuest().getEligibilityTestOther()!=null && !userApplicantDetails.getGuest().getEligibilityTestOther().isEmpty())
					{
						String empEligibAddOther=userApplicantDetails.getGuest().getEligibilityTest()+"  --Other description:-"+userApplicantDetails.getGuest().getEligibilityTestOther();
						 objform.setEligibilityTestdisplay(empEligibAddOther);
					}
					else
						 objform.setEligibilityTestdisplay(userApplicantDetails.getGuest().getEligibilityTest());
				}
				if(userApplicantDetails.getGuest().getIndustryFunctionalArea()!=null && !userApplicantDetails.getGuest().getIndustryFunctionalArea().trim().isEmpty()){
					objform.setIndustryFunctionalArea(userApplicantDetails.getGuest().getIndustryFunctionalArea());
				}			
				if(userApplicantDetails.getGuest().getReservationCategory() !=null && !userApplicantDetails.getGuest().getReservationCategory().isEmpty()){
					if("GM".equalsIgnoreCase(userApplicantDetails.getGuest().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getGuest().getReservationCategory());
					}
					if("SC".equalsIgnoreCase(userApplicantDetails.getGuest().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getGuest().getReservationCategory());
					}
					if("ST".equalsIgnoreCase(userApplicantDetails.getGuest().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getGuest().getReservationCategory());
					}
					if("OBC".equalsIgnoreCase(userApplicantDetails.getGuest().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getGuest().getReservationCategory());
					}
					if("Minority".equalsIgnoreCase(userApplicantDetails.getGuest().getReservationCategory())){
						objform.setReservationCategory(userApplicantDetails.getGuest().getReservationCategory());
					}
					if("Person With Disability".equalsIgnoreCase(userApplicantDetails.getGuest().getReservationCategory())){
						
						String personWithDisability=userApplicantDetails.getGuest().getReservationCategory()+"  --Handicap description:-"+userApplicantDetails.getGuest().getHandicappedDescription();
							objform.setReservationCategory(personWithDisability);
						
						}
				}
				
				
				
				////...............................................................Photo.....................................................
			
				
				if(userApplicantDetails.getGuest().getEmpImages()!=null && !userApplicantDetails.getGuest().getEmpImages().isEmpty()){
					Iterator<GuestImages> itr=userApplicantDetails.getGuest().getEmpImages().iterator();
					while (itr.hasNext()) {
						GuestImages bo = itr.next();
						if(bo.getEmpPhoto()!=null)
							objform.setPhotoBytes(bo.getEmpPhoto());
					}
				
				}
			////...............................................................Photo.....................................................
				if(userApplicantDetails.getGuest().getId()>0){
					 objform.setEmployeeId(String.valueOf(userApplicantDetails.getGuest().getId()));
				}
				if(StringUtils.isNotEmpty(String.valueOf(userApplicantDetails.getGuest().getTeachingStaff()))){
					String Value= String.valueOf(userApplicantDetails.getGuest().getTeachingStaff());
					if(Value.equals("true"))
						objform.setTeachingStaff("Teaching Staff");
					else
						objform.setTeachingStaff("Non-teaching Staff");
					 
				}
					
				
				if(StringUtils.isNotEmpty(String.valueOf(userApplicantDetails.getGuest().getActive()))){
					String Value= String.valueOf(userApplicantDetails.getGuest().getActive());
					if(Value.equals("true"))
						objform.setActive("Active");
					else
						objform.setActive("Not Active");
					 
				}
				if(StringUtils.isNotEmpty(String.valueOf(userApplicantDetails.getGuest().getIsSameAddress()))){
					String Value= String.valueOf(userApplicantDetails.getGuest().getIsSameAddress());
					if(Value.equals("true"))
						objform.setSameAddress("true");
					else
						objform.setSameAddress("false");
					 
				}
				if(StringUtils.isNotEmpty(String.valueOf(userApplicantDetails.getGuest().getCurrentlyWorking()))){
					String Value= String.valueOf(userApplicantDetails.getGuest().getCurrentlyWorking());
					if(Value.equals("true"))
						objform.setCurrentlyWorking("YES");
					else
						objform.setCurrentlyWorking("NO");
					 
				}
				
//				if(userApplicantDetails.getGuest().getPayScaleId()!=null && userApplicantDetails.getGuest().getPayScaleId().getId()>0){
//					  objform.setPayScaleId(String.valueOf(userApplicantDetails.getGuest().getPayScaleId().getPayScale()));
//				}
				
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getFirstName()) && userApplicantDetails.getGuest().getFirstName()!=null){
					  objform.setName(userApplicantDetails.getGuest().getFirstName());
				}
//				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getSmartCardNo()) && userApplicantDetails.getGuest().getSmartCardNo()!=null){
//					  objform.setSmartCardNo(userApplicantDetails.getGuest().getSmartCardNo());
//				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getUid()) && userApplicantDetails.getGuest().getUid()!=null){
					  objform.setuId(userApplicantDetails.getGuest().getUid());
				}
				if(userApplicantDetails.getGuest().getTitleId()!=null && userApplicantDetails.getGuest().getTitleId().getId()>0){
					  objform.setTitleId(String.valueOf(userApplicantDetails.getGuest().getTitleId().getTitle()));
				}
				if(userApplicantDetails.getGuest().getDepartment()!=null && userApplicantDetails.getGuest().getDepartment().getId()>0){
					  objform.setDepartmentId(String.valueOf(userApplicantDetails.getGuest().getDepartment().getName()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getCode())&& userApplicantDetails.getGuest().getCode()!=null){
					  objform.setCode(userApplicantDetails.getGuest().getCode());
				}
//				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getFingerPrintId()) && userApplicantDetails.getGuest().getFingerPrintId()!=null){
//					  objform.setFingerPrintId(userApplicantDetails.getGuest().getFingerPrintId());
//				}
				if(userApplicantDetails.getGuest().getGender()!=null && !userApplicantDetails.getGuest().getGender().isEmpty()){
					 objform.setGender(userApplicantDetails.getGuest().getGender());
				}
				if(userApplicantDetails.getGuest().getNationality()!=null && userApplicantDetails.getGuest().getNationality().getId()>0){
					  objform.setNationalityId(String.valueOf(userApplicantDetails.getGuest().getNationality().getName()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getMaritalStatus()) && userApplicantDetails.getGuest().getMaritalStatus()!=null){
					  objform.setMaritalStatus(String.valueOf(userApplicantDetails.getGuest().getMaritalStatus()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getBloodGroup()) && userApplicantDetails.getGuest().getBloodGroup()!=null){
					  objform.setBloodGroup(String.valueOf(userApplicantDetails.getGuest().getBloodGroup()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getPanNo()) && userApplicantDetails.getGuest().getPanNo()!=null){
					  objform.setPanno(String.valueOf(userApplicantDetails.getGuest().getPanNo()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getEmail()) && userApplicantDetails.getGuest().getEmail()!=null){
					  objform.setEmail(String.valueOf(userApplicantDetails.getGuest().getEmail()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getWorkEmail()) && userApplicantDetails.getGuest().getWorkEmail()!=null){
					  objform.setOfficialEmail(String.valueOf(userApplicantDetails.getGuest().getWorkEmail()));
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getCurrentAddressMobile1()) && userApplicantDetails.getGuest().getCurrentAddressMobile1()!=null){
					  objform.setMobileNo1(userApplicantDetails.getGuest().getCurrentAddressMobile1());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getBankAccNo()) && userApplicantDetails.getGuest().getBankAccNo()!=null){
					  objform.setBankAccNo(userApplicantDetails.getGuest().getBankAccNo());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getPfNo()) && userApplicantDetails.getGuest().getPfNo()!=null){
					  objform.setPfNo(userApplicantDetails.getGuest().getPfNo());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getFourWheelerNo()) && userApplicantDetails.getGuest().getFourWheelerNo()!=null){
					  objform.setFourWheelerNo(userApplicantDetails.getGuest().getFourWheelerNo());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getTwoWheelerNo()) && userApplicantDetails.getGuest().getTwoWheelerNo()!=null){
					  objform.setTwoWheelerNo(userApplicantDetails.getGuest().getTwoWheelerNo());
				}
				
				if(userApplicantDetails.getGuest().getReligionId()!=null && userApplicantDetails.getGuest().getReligionId().getId()>0){
					  objform.setReligionId(String.valueOf(userApplicantDetails.getGuest().getReligionId().getName()));
				}
//				if(userApplicantDetails.getGuest().getEmptype()!=null && userApplicantDetails.getGuest().getEmptype().getId()>0){
//					  objform.setEmptypeId(String.valueOf(userApplicantDetails.getGuest().getEmptype().getName()));
//				}
				if(userApplicantDetails.getGuest().getEmpQualificationLevel()!=null && userApplicantDetails.getGuest().getEmpQualificationLevel().getId()>0){
					  objform.setQualificationId(String.valueOf(userApplicantDetails.getGuest().getEmpQualificationLevel().getName()));
				}
				//Modification    ........................................................
				
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getPermanentAddressLine1()) && userApplicantDetails.getGuest().getPermanentAddressLine1()!=null){
					  objform.setAddressLine1(userApplicantDetails.getGuest().getPermanentAddressLine1());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getPermanentAddressLine2()) && userApplicantDetails.getGuest().getPermanentAddressLine2()!=null ){
					  objform.setAddressLine2(userApplicantDetails.getGuest().getPermanentAddressLine2());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getPermanentAddressCity()) && userApplicantDetails.getGuest().getPermanentAddressCity()!=null){
					  objform.setCity(userApplicantDetails.getGuest().getPermanentAddressCity());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getPermanentAddressZip()) && userApplicantDetails.getGuest().getPermanentAddressZip()!=null){
					  objform.setPermanentZipCode(userApplicantDetails.getGuest().getPermanentAddressZip());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getPermanentAddressStateOthers()) && userApplicantDetails.getGuest().getPermanentAddressStateOthers()!=null){
					  objform.setOtherPermanentState(userApplicantDetails.getGuest().getPermanentAddressStateOthers());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getCommunicationAddressLine1()) && userApplicantDetails.getGuest().getCommunicationAddressLine1()!=null){
					  objform.setCurrentAddressLine1(userApplicantDetails.getGuest().getCommunicationAddressLine1());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getCommunicationAddressLine2()) && userApplicantDetails.getGuest().getCommunicationAddressLine2()!=null){
					  objform.setCurrentAddressLine2(userApplicantDetails.getGuest().getCommunicationAddressLine2());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getCommunicationAddressCity()) && userApplicantDetails.getGuest().getCommunicationAddressCity()!=null){
					  objform.setCurrentCity(userApplicantDetails.getGuest().getCommunicationAddressCity());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getCommunicationAddressStateOthers()) && userApplicantDetails.getGuest().getCommunicationAddressStateOthers()!=null){
					  objform.setOtherCurrentState(userApplicantDetails.getGuest().getCommunicationAddressStateOthers());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getCommunicationAddressZip()) && userApplicantDetails.getGuest().getCommunicationAddressZip()!=null){
					  objform.setCurrentZipCode(userApplicantDetails.getGuest().getCommunicationAddressZip());
				}
				if(userApplicantDetails.getGuest().getCountryByPermanentAddressCountryId()!=null && userApplicantDetails.getGuest().getCountryByPermanentAddressCountryId().getId()>0){
					  objform.setCountryId(String.valueOf(userApplicantDetails.getGuest().getCountryByPermanentAddressCountryId().getName()));
				}
				if(userApplicantDetails.getGuest().getCountryByCommunicationAddressCountryId()!=null && userApplicantDetails.getGuest().getCountryByCommunicationAddressCountryId().getId()>0){
					  objform.setCurrentCountryId(String.valueOf(userApplicantDetails.getGuest().getCountryByCommunicationAddressCountryId().getName()));
				}
				if(userApplicantDetails.getGuest().getCurrentAddressHomeTelephone1()!=null && !userApplicantDetails.getGuest().getCurrentAddressHomeTelephone1().isEmpty()){
					objform.setHomePhone1(userApplicantDetails.getGuest().getCurrentAddressHomeTelephone1());
				}
				
				if(userApplicantDetails.getGuest().getCurrentAddressHomeTelephone2()!=null && !userApplicantDetails.getGuest().getCurrentAddressHomeTelephone2().isEmpty()){
					objform.setHomePhone2(userApplicantDetails.getGuest().getCurrentAddressHomeTelephone2());
				}
				
				if(userApplicantDetails.getGuest().getCurrentAddressHomeTelephone3()!=null && !userApplicantDetails.getGuest().getCurrentAddressHomeTelephone3().isEmpty()){
					objform.setHomePhone3(userApplicantDetails.getGuest().getCurrentAddressHomeTelephone3());
				}
				if(userApplicantDetails.getGuest().getCurrentAddressWorkTelephone1()!=null && !userApplicantDetails.getGuest().getCurrentAddressWorkTelephone1().isEmpty()){
					objform.setWorkPhNo1(userApplicantDetails.getGuest().getCurrentAddressWorkTelephone1());
				}
				
				if(userApplicantDetails.getGuest().getCurrentAddressWorkTelephone2()!=null && !userApplicantDetails.getGuest().getCurrentAddressWorkTelephone2().isEmpty()){
					objform.setWorkPhNo2(userApplicantDetails.getGuest().getCurrentAddressWorkTelephone2());
				}
				
				if(userApplicantDetails.getGuest().getCurrentAddressWorkTelephone3()!=null && !userApplicantDetails.getGuest().getCurrentAddressWorkTelephone3().isEmpty()){
					objform.setWorkPhNo3(userApplicantDetails.getGuest().getCurrentAddressWorkTelephone3());
				}

				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getDesignationName()) && userApplicantDetails.getGuest().getDesignationName()!=null){
					  objform.setDesignation(userApplicantDetails.getGuest().getDesignationName());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getOrganistionName()) && userApplicantDetails.getGuest().getOrganistionName()!=null ){
					  objform.setOrgAddress(userApplicantDetails.getGuest().getOrganistionName());
				}
				if(userApplicantDetails.getGuest().getDesignation()!=null && userApplicantDetails.getGuest().getDesignation().getId()>0){
					  objform.setDesignationPfId(String.valueOf(userApplicantDetails.getGuest().getDesignation().getName()));
				}
				if(userApplicantDetails.getGuest().getDepartment()!=null && userApplicantDetails.getGuest().getDepartment().getId()>0){
					  objform.setDepartmentId(String.valueOf(userApplicantDetails.getGuest().getDepartment().getName()));
				}
//				if(userApplicantDetails.getGuest().getEmployeeByReportToId()!=null && userApplicantDetails.getGuest().getEmployeeByReportToId().getId()>0){
//					  objform.setReportToId(String.valueOf(userApplicantDetails.getGuest().getEmployeeByReportToId().getFirstName()));
//				}
			/*	if(userApplicantDetails.getGuest().getGrade()!=null && !userApplicantDetails.getGuest().getGrade().isEmpty()){
					  objform.setGrade(String.valueOf(userApplicantDetails.getGuest().getGrade()));
				}*/
				/*if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getCurrentOrganization()) && userApplicantDetails.getGuest().getCurrentOrganization()!=null){
					  objform.setOrgAddress(userApplicantDetails.getGuest().getCurrentOrganization());
				}*/
				if(userApplicantDetails.getGuest().getBooks()!=null && !userApplicantDetails.getGuest().getBooks().isEmpty()){
					  objform.setBooks(String.valueOf(userApplicantDetails.getGuest().getBooks()));
				}
				if(userApplicantDetails.getGuest().getNoOfPublicationsNotRefered()!=null && !userApplicantDetails.getGuest().getNoOfPublicationsNotRefered().isEmpty()){
					  objform.setNoOfPublicationsNotRefered(userApplicantDetails.getGuest().getNoOfPublicationsNotRefered());
				}
				if(userApplicantDetails.getGuest().getNoOfPublicationsRefered()!=null && !userApplicantDetails.getGuest().getNoOfPublicationsRefered().isEmpty()){
					  objform.setNoOfPublicationsRefered(userApplicantDetails.getGuest().getNoOfPublicationsRefered());
				}
								
				if(userApplicantDetails.getGuest().getEmpQualificationLevel()!=null && userApplicantDetails.getGuest().getEmpQualificationLevel().getId()>0){
					  objform.setQualificationId(String.valueOf(userApplicantDetails.getGuest().getEmpQualificationLevel().getName()));
				}
				if(userApplicantDetails.getGuest().getDesignation()!=null && userApplicantDetails.getGuest().getDesignation().getId()>0){
					  objform.setDesignationPfId(String.valueOf(userApplicantDetails.getGuest().getDesignation().getName()));
				}
				if(userApplicantDetails.getGuest().getEmpSubjectArea()!=null && userApplicantDetails.getGuest().getEmpSubjectArea().getId()>0){
					  objform.setEmpSubjectAreaId(String.valueOf(userApplicantDetails.getGuest().getEmpSubjectArea().getName()));
				}
				if(userApplicantDetails.getGuest().getDob()!=null && !userApplicantDetails.getGuest().getDob().toString().isEmpty() ){
					objform.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getGuest().getDob().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
//				if(userApplicantDetails.getGuest().getDateOfLeaving()!=null && !userApplicantDetails.getGuest().getDateOfLeaving().toString().isEmpty()){
//					objform.setDateOfLeaving(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getGuest().getDateOfLeaving().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
//				}
//				if(userApplicantDetails.getGuest().getDateOfResignation()!=null && !userApplicantDetails.getGuest().getDateOfResignation().toString().isEmpty()){
//					objform.setDateOfResignation(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getGuest().getDateOfResignation().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
//				}
//				if(userApplicantDetails.getGuest().getDoj()!=null && !userApplicantDetails.getGuest().getDoj().toString().isEmpty()){
//					objform.setDateOfJoining(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getGuest().getDoj().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
//				}
//				if(userApplicantDetails.getGuest().getRejoinDate()!=null && !userApplicantDetails.getGuest().getRejoinDate().toString().isEmpty()){
//					objform.setRejoinDate(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getGuest().getRejoinDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
//				}
//				if(userApplicantDetails.getGuest().getRetirementDate()!=null && !userApplicantDetails.getGuest().getRetirementDate().toString().isEmpty()){
//					objform.setRetirementDate(CommonUtil.ConvertStringToDateFormat(userApplicantDetails.getGuest().getRetirementDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
//				}
				if(userApplicantDetails.getGuest().getTotalExpMonths()!=null && !userApplicantDetails.getGuest().getTotalExpMonths().isEmpty()){
					  objform.setExpMonths(String.valueOf(userApplicantDetails.getGuest().getTotalExpMonths()));
				}
				if(userApplicantDetails.getGuest().getTotalExpYear()!=null && !userApplicantDetails.getGuest().getTotalExpYear().isEmpty()){
					  objform.setExpYears(String.valueOf(userApplicantDetails.getGuest().getTotalExpYear()));
				}
				/*if(userApplicantDetails.getGuest().getActive()){
					  objform.setActive(String.valueOf(userApplicantDetails.getGuest().getActive()));
				}*/
				
				
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getEmergencyContName()) && userApplicantDetails.getGuest().getEmergencyContName()!=null){
					  objform.setEmContactName(userApplicantDetails.getGuest().getEmergencyContName());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getEmContactAddress()) && userApplicantDetails.getGuest().getEmContactAddress()!=null){
					  objform.setEmContactAddress(userApplicantDetails.getGuest().getEmContactAddress());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getEmergencyHomeTelephone()) && userApplicantDetails.getGuest().getEmergencyHomeTelephone()!=null){
					  objform.setEmContactHomeTel(userApplicantDetails.getGuest().getEmergencyHomeTelephone());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getEmergencyMobile()) && userApplicantDetails.getGuest().getEmergencyMobile()!=null){
					  objform.setEmContactMobile(userApplicantDetails.getGuest().getEmergencyMobile());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getEmergencyWorkTelephone()) && userApplicantDetails.getGuest().getEmergencyWorkTelephone()!=null){
					  objform.setEmContactWorkTel(userApplicantDetails.getGuest().getEmergencyWorkTelephone());
				}
				
//				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getGrossPay()) && userApplicantDetails.getGuest().getGrossPay()!=null){
//					  objform.setGrossPay(userApplicantDetails.getGuest().getGrossPay());
//				}
				/*if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getHalfDayEndTime())){
					  objform.setHalfDayEndTime(userApplicantDetails.getGuest().getHalfDayEndTime());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getHalfDayStartTime())){
					  objform.setHalfDayStartTime(userApplicantDetails.getGuest().getHalfDayStartTime());
				}*/
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getHighQualifForAlbum()) && userApplicantDetails.getGuest().getHighQualifForAlbum()!=null){
					  objform.setHighQualifForAlbum(userApplicantDetails.getGuest().getHighQualifForAlbum());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getOtherInfo()) && userApplicantDetails.getGuest().getOtherInfo()!=null){
					  objform.setOtherInfo(userApplicantDetails.getGuest().getOtherInfo());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getWorkEmail()) && userApplicantDetails.getGuest().getWorkEmail()!=null){
					  objform.setOfficialEmail(userApplicantDetails.getGuest().getWorkEmail());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getPanNo()) && userApplicantDetails.getGuest().getPanNo()!=null){
					  objform.setPanno(userApplicantDetails.getGuest().getPanNo());
				}
				
							
				
//				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getReasonOfLeaving()) && userApplicantDetails.getGuest().getReasonOfLeaving()!=null){
//					  objform.setReasonOfLeaving(userApplicantDetails.getGuest().getReasonOfLeaving());
//				}
//				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getRelationship()) && userApplicantDetails.getGuest().getRelationship()!=null){
//					  objform.setEmContactRelationship(userApplicantDetails.getGuest().getRelationship());
//				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getRelevantExpMonths()) && userApplicantDetails.getGuest().getRelevantExpMonths()!=null ){
					  objform.setRelevantExpMonths(userApplicantDetails.getGuest().getRelevantExpMonths());
				}
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getRelevantExpYears()) && userApplicantDetails.getGuest().getRelevantExpYears()!=null ){
					  objform.setRelevantExpYears(userApplicantDetails.getGuest().getRelevantExpYears());
				}
				/*if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getSaturdayTimeOut())){
					  objform.setSaturdayTimeOut(userApplicantDetails.getGuest().getSaturdayTimeOut());
				}*/
//				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getScale()) && userApplicantDetails.getGuest().getScale()!=null){
//					  objform.setScale(userApplicantDetails.getGuest().getScale());
//				}
				if(userApplicantDetails.getGuest().getStateByCommunicationAddressStateId()!=null && userApplicantDetails.getGuest().getStateByCommunicationAddressStateId().getId()>0){
					  objform.setCurrentState(String.valueOf(userApplicantDetails.getGuest().getStateByCommunicationAddressStateId().getName()));
				}
				if(userApplicantDetails.getGuest().getStateByPermanentAddressStateId()!=null && userApplicantDetails.getGuest().getStateByPermanentAddressStateId().getId()>0){
					  objform.setStateId(String.valueOf(userApplicantDetails.getGuest().getStateByPermanentAddressStateId().getName()));
				}
				if(userApplicantDetails.getGuest().getStreamId() != null && userApplicantDetails.getGuest().getStreamId().getId()>0){
					objform.setStreamId(String.valueOf(userApplicantDetails.getGuest().getStreamId().getName()));
					
				}
				
				if(userApplicantDetails.getGuest().getTimeIn()!=null && !userApplicantDetails.getGuest().getTimeIn().isEmpty()){
					objform.setTimeIn(userApplicantDetails.getGuest().getTimeIn().substring(0,2));
					objform.setTimeInMin(userApplicantDetails.getGuest().getTimeIn().substring(3,5));
				}
			
//				if(userApplicantDetails.getGuest().getTimeInEnds()!=null && !userApplicantDetails.getGuest().getTimeInEnds().isEmpty()){
//					objform.setTimeInEnds(userApplicantDetails.getGuest().getTimeInEnds().substring(0,2));
//					objform.setTimeInEndMIn(userApplicantDetails.getGuest().getTimeInEnds().substring(3,5));
//				}
				
//				if(userApplicantDetails.getGuest().getTimeOut()!=null && !userApplicantDetails.getGuest().getTimeOut().isEmpty()){
//					objform.setTimeOut(userApplicantDetails.getGuest().getTimeOut().substring(0,2));
//					objform.setTimeOutMin(userApplicantDetails.getGuest().getTimeOut().substring(3,5));
//				}
				
//				if(userApplicantDetails.getGuest().getSaturdayTimeOut()!=null && !userApplicantDetails.getGuest().getSaturdayTimeOut().isEmpty()){
//					objform.setSaturdayTimeOut(userApplicantDetails.getGuest().getSaturdayTimeOut().substring(0,2));
//					objform.setSaturdayTimeOutMin(userApplicantDetails.getGuest().getSaturdayTimeOut().substring(3,5));
//				}
				
//				if(userApplicantDetails.getGuest().getHalfDayStartTime()!=null && !userApplicantDetails.getGuest().getHalfDayStartTime().isEmpty()){
//					objform.setHalfDayStartTime(userApplicantDetails.getGuest().getHalfDayStartTime().substring(0,2));
//					objform.setHalfDayStartTimeMin(userApplicantDetails.getGuest().getHalfDayStartTime().substring(3,5));
//				}
//				
//				if(userApplicantDetails.getGuest().getHalfDayEndTime()!=null && !userApplicantDetails.getGuest().getHalfDayEndTime().isEmpty()){
//					objform.setHalfDayEndTime(userApplicantDetails.getGuest().getHalfDayEndTime().substring(0,2));
//					objform.setHalfDayEndTimeMin(userApplicantDetails.getGuest().getHalfDayEndTime().substring(3,5));
//				}
			
				
				
				
				
				
				
				
				
				/*if(userApplicantDetails.getGuest().getTimeIn()!=null && !userApplicantDetails.getGuest().getTimeIn().isEmpty()){
					objform.setTimeIn(userApplicantDetails.getGuest().getTimeIn().substring(0,2));
					objform.setTimeInMin(userApplicantDetails.getGuest().getTimeIn().substring(3,5));
				}
			
				if(userApplicantDetails.getGuest().getTimeInEnds()!=null && !userApplicantDetails.getGuest().getTimeInEnds().isEmpty()){
					objform.setTimeInEnds(userApplicantDetails.getGuest().getTimeInEnds().substring(0,2));
					objform.setTimeInEndMIn(userApplicantDetails.getGuest().getTimeInEnds().substring(3,5));
				}
				
				if(userApplicantDetails.getGuest().getTimeOut()!=null && !userApplicantDetails.getGuest().getTimeOut().isEmpty()){
					objform.setTimeOut(userApplicantDetails.getGuest().getTimeOut().substring(0,2));
					objform.setTimeOutMin(userApplicantDetails.getGuest().getTimeOut().substring(3,5));
				}
				
				if(userApplicantDetails.getGuest().getSaturdayTimeOut()!=null && !userApplicantDetails.getGuest().getSaturdayTimeOut().isEmpty()){
					objform.setSaturdayTimeOut(userApplicantDetails.getGuest().getSaturdayTimeOut().substring(0,2));
					objform.setSaturdayTimeOutMin(userApplicantDetails.getGuest().getSaturdayTimeOut().substring(3,5));
				}
				
				if(userApplicantDetails.getGuest().getHalfDayStartTime()!=null && !userApplicantDetails.getGuest().getHalfDayStartTime().isEmpty()){
					objform.setHalfDayStartTime(userApplicantDetails.getGuest().getHalfDayStartTime().substring(0,2));
					objform.setHalfDayStartTimeMin(userApplicantDetails.getGuest().getHalfDayStartTime().substring(3,5));
				}
				
				if(userApplicantDetails.getGuest().getHalfDayEndTime()!=null && !userApplicantDetails.getGuest().getHalfDayEndTime().isEmpty()){
					objform.setHalfDayEndTime(userApplicantDetails.getGuest().getHalfDayEndTime().substring(0,2));
					objform.setHalfDayEndTimeMin(userApplicantDetails.getGuest().getHalfDayEndTime().substring(3,5));
				}*/
								
				if(StringUtils.isNotEmpty(userApplicantDetails.getGuest().getWorkEmail()) && userApplicantDetails.getGuest().getWorkEmail()!=null){
					  objform.setOfficialEmail(userApplicantDetails.getGuest().getWorkEmail());
				}
				if(userApplicantDetails.getGuest().getWorkLocationId()!=null && userApplicantDetails.getGuest().getWorkLocationId().getId()>0){
					  objform.setWorkLocationId(String.valueOf(userApplicantDetails.getGuest().getWorkLocationId().getName()));
				}
//				if(userApplicantDetails.getGuest().getEmptype()!=null && userApplicantDetails.getGuest().getEmptype().getId()>0){
//					  objform.setEmptypeId(String.valueOf(userApplicantDetails.getGuest().getEmptype().getName()));
//				}
				
				if(userApplicantDetails.getGuest().getEducationalDetailsSet()!=null){
					List<EmpQualificationLevelTo> fixed=null;
					if(objform.getEmployeeInfoTONew()!=null){
						if(objform.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
							fixed=objform.getEmployeeInfoTONew().getEmpQualificationFixedTo();
						}
						List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
						Set<GuestEducationalDetails> empEducationalDetailsSet=userApplicantDetails.getGuest().getEducationalDetailsSet();
						Iterator<GuestEducationalDetails> iterator=empEducationalDetailsSet.iterator();
						while(iterator.hasNext()){
							GuestEducationalDetails empEducationalDetails=iterator.next();
							if(empEducationalDetails!=null){
								boolean flag=false;
								if(empEducationalDetails.getEmpQualificationLevel()!=null 
										&& empEducationalDetails.getEmpQualificationLevel().isFixedDisplay()!=null){
									flag=empEducationalDetails.getEmpQualificationLevel().isFixedDisplay();
									if(flag && fixed!=null){
										Iterator<EmpQualificationLevelTo> iterator2=fixed.iterator();
										while(iterator2.hasNext()){
											EmpQualificationLevelTo empQualificationLevelTo=iterator2.next();
											if(empQualificationLevelTo!=null && StringUtils.isNotEmpty(empQualificationLevelTo.getEducationId())){
												if(empEducationalDetails.getEmpQualificationLevel().getId()>0)
													if(empQualificationLevelTo.getEducationId().equalsIgnoreCase(String.valueOf(empEducationalDetails.getEmpQualificationLevel().getId()))){
														
														if(empEducationalDetails.getId()>0){
															empQualificationLevelTo.setQualification(empEducationalDetails.getEmpQualificationLevel().getName());
															}
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getCourse())){
															empQualificationLevelTo.setCourse(empEducationalDetails.getCourse());
														}
														
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getSpecialization())){
															empQualificationLevelTo.setSpecialization(empEducationalDetails.getSpecialization());
														}
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getGrade())){
															empQualificationLevelTo.setGrade(empEducationalDetails.getGrade());
														}
														
														if(StringUtils.isNotEmpty(empEducationalDetails.getInstitute())){
															empQualificationLevelTo.setInstitute(empEducationalDetails.getInstitute());
														}
														
														if(empEducationalDetails.getYearOfCompletion()>0){
															empQualificationLevelTo.setYearOfComp(String.valueOf(empEducationalDetails.getYearOfCompletion()));
														}
													}
											}
										}
									}else{
										EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
										
										if(empEducationalDetails.getId()>0){
											empQualificationLevelTo.setQualification(empEducationalDetails.getEmpQualificationLevel().getName());
											}
										if(StringUtils.isNotEmpty(empEducationalDetails.getCourse())){
												empQualificationLevelTo.setCourse(empEducationalDetails.getCourse());
											}
											
											if(StringUtils.isNotEmpty(empEducationalDetails.getSpecialization())){
												empQualificationLevelTo.setSpecialization(empEducationalDetails.getSpecialization());
											}
											
											if(StringUtils.isNotEmpty(empEducationalDetails.getGrade())){
												empQualificationLevelTo.setGrade(empEducationalDetails.getGrade());
											}
											
											if(StringUtils.isNotEmpty(empEducationalDetails.getInstitute())){
												empQualificationLevelTo.setInstitute(empEducationalDetails.getInstitute());
											}
											
											if(empEducationalDetails.getYearOfCompletion()>0){
												empQualificationLevelTo.setYearOfComp(String.valueOf(empEducationalDetails.getYearOfCompletion()));
											}
											if(empEducationalDetails.getEmpQualificationLevel().getId()>0){
												empQualificationLevelTo.setEducationId(String.valueOf(empEducationalDetails.getEmpQualificationLevel().getId()));
											}
										level.add(empQualificationLevelTo);
									}
										
									}
								}
						}
						objform.getEmployeeInfoTONew().setEmpQualificationLevelTos(level);
					}
				}
			
				
				
				
				
		/*	if(userApplicantDetails.getGuest().getEmpAcheivements()!=null)
			{
				Set<EmpAcheivement> empAcheivements=userApplicantDetails.getGuest().getEmpAcheivements();
				if(empAcheivements != null && !empAcheivements.isEmpty())
				{
				Iterator<EmpAcheivement> iterator=empAcheivements.iterator();
				List<EmpAcheivementTO> empAcheivementTOs=new ArrayList<EmpAcheivementTO>();
				
				while(iterator.hasNext()){
					EmpAcheivement empAcheiv=iterator.next();
					if(empAcheiv!=null){
						EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
					
						if(empAcheiv.getId()>0)
						{
							empAcheivementTO.setId(empAcheiv.getId());
						}
						if(StringUtils.isNotEmpty(empAcheiv.getAcheivementName())){
							empAcheivementTO.setAcheivementName(empAcheiv.getAcheivementName());
						}
						
						if(StringUtils.isNotEmpty(empAcheiv.getDetails())){
							empAcheivementTO.setDetails(empAcheiv.getDetails());
						}
						
						empAcheivementTOs.add(empAcheivementTO);
					
				
			}
					}
				objform.getEmployeeInfoTONew().setEmpAcheivements(empAcheivementTOs);
				}
			else
			{
				
				List<EmpAcheivementTO> flist=new ArrayList<EmpAcheivementTO>();
				EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
				empAcheivementTO.setAcheivementName("");
				empAcheivementTO.setDetails("");
				objform.setAchievementListSize(String.valueOf(flist.size()));
				flist.add(empAcheivementTO);
				objform.getEmployeeInfoTONew().setEmpAcheivements(flist);
			}
			}*/
			
		/*	if(userApplicantDetails.getGuest().getEmpDependentses()!=null)
			{
				Set<EmpDependents> empDependents=userApplicantDetails.getGuest().getEmpDependentses();
				if(empDependents != null && !empDependents.isEmpty())
				{
				Iterator<EmpDependents> iterator=empDependents.iterator();
				List<EmpDependentsTO> empDependentsTOs=new ArrayList<EmpDependentsTO>();
				
				while(iterator.hasNext()){
					EmpDependents empDepen=iterator.next();
					if(empDepen!=null){
						EmpDependentsTO empDepenTOs=new EmpDependentsTO();
					
						if(empDepen.getId()>0)
						{
							empDepenTOs.setId(String.valueOf(empDepen.getId()));
						}
						if(StringUtils.isNotEmpty(empDepen.getDependentName())){
							empDepenTOs.setDependantName(empDepen.getDependentName());
						}
						
						if(StringUtils.isNotEmpty(empDepen.getDependentRelationship())){
							empDepenTOs.setDependentRelationship(empDepen.getDependentRelationship());
						}
						
						if(empDepen.getDependantDOB()!=null){
							empDepenTOs.setDependantDOB(CommonUtil.ConvertStringToDateFormat(empDepen.getDependantDOB().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empDependentsTOs.add(empDepenTOs);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpDependentses(empDependentsTOs);
				}
			else
			{
				List<EmpDependentsTO> empDependentses=new ArrayList<EmpDependentsTO>();
				EmpDependentsTO empDependentsTO=new EmpDependentsTO();
				empDependentsTO.setDependantDOB("");
				empDependentsTO.setDependantName("");
				empDependentsTO.setDependentRelationship("");
				objform.setDependantsListSize(String.valueOf(empDependentses.size()));
				empDependentses.add(empDependentsTO);
				
				objform.getEmployeeInfoTONew().setEmpDependentses(empDependentses);
				
			}
			}*/
			
		/*	if(userApplicantDetails.getGuest().getEmpFeeConcession()!=null)
			{
				Set<EmpFeeConcession> empFeeConcession=userApplicantDetails.getGuest().getEmpFeeConcession();
				if(empFeeConcession != null && !empFeeConcession.isEmpty())
				{
				Iterator<EmpFeeConcession> iterator=empFeeConcession.iterator();
				List<EmpFeeConcessionTO> empFeeConcessionTOs=new ArrayList<EmpFeeConcessionTO>();
				
				while(iterator.hasNext()){
					EmpFeeConcession empFeeConc=iterator.next();
					if(empFeeConc!=null){
						EmpFeeConcessionTO empFeeConcTO=new EmpFeeConcessionTO();
						if(empFeeConc.getId()>0)
						{
							empFeeConcTO.setId(empFeeConc.getId());
						}
						if(StringUtils.isNotEmpty(empFeeConc.getFeeConcessionDetails())){
							empFeeConcTO.setFeeConcessionDetails(empFeeConc.getFeeConcessionDetails());
						}
						
						if(StringUtils.isNotEmpty(empFeeConc.getFeeConcessionAmount())){
							empFeeConcTO.setFeeConcessionAmount(empFeeConc.getFeeConcessionAmount());
						}
						
						if(empFeeConc.getFeeConcessionDate()!=null){
							empFeeConcTO.setFeeConcessionDate(CommonUtil.ConvertStringToDateFormat(empFeeConc.getFeeConcessionDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empFeeConcessionTOs.add(empFeeConcTO);
					
				
			}
			}objform.getEmployeeInfoTONew().setEmpFeeConcession(empFeeConcessionTOs);
				}
			else
			{
				List<EmpFeeConcessionTO> list=new ArrayList<EmpFeeConcessionTO>();
				EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
				empFeeConcessionTO.setFeeConcessionAmount("");
				empFeeConcessionTO.setFeeConcessionDate("");
				empFeeConcessionTO.setFeeConcessionDetails("");
				objform.setFeeListSize(String.valueOf(list.size()));
				list.add(empFeeConcessionTO);
				objform.getEmployeeInfoTONew().setEmpFeeConcession(list);
				
			}
			}*/
			
		/*	if(userApplicantDetails.getGuest().getEmpFinancial()!=null)
			{
				Set<EmpFinancial> empFinancial=userApplicantDetails.getGuest().getEmpFinancial();
				if(empFinancial != null && !empFinancial.isEmpty())
				{
				Iterator<EmpFinancial> iterator=empFinancial.iterator();
				List<EmpFinancialTO> empFinancialTOs=new ArrayList<EmpFinancialTO>();
				
				while(iterator.hasNext()){
					EmpFinancial empFinancial2=iterator.next();
					if(empFinancial2!=null){
						EmpFinancialTO empFinancialTO=new EmpFinancialTO();
						if(empFinancial2.getId()>0)
						{
							empFinancialTO.setId(empFinancial2.getId());
						}
						if(StringUtils.isNotEmpty(empFinancial2.getFinancialAmount())){
							empFinancialTO.setFinancialAmount(empFinancial2.getFinancialAmount());
						}
						
						if(StringUtils.isNotEmpty(empFinancial2.getFinancialDetails())){
							empFinancialTO.setFinancialDetails(empFinancial2.getFinancialDetails());
						}
						
						if(empFinancial2.getFinancialDate()!=null){
							empFinancialTO.setFinancialDate(CommonUtil.ConvertStringToDateFormat(empFinancial2.getFinancialDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empFinancialTOs.add(empFinancialTO);
					
				
			}
			}objform.getEmployeeInfoTONew().setEmpFinancial(empFinancialTOs);
				}
				else
				{
					List<EmpFinancialTO> flist=new ArrayList<EmpFinancialTO>();
					EmpFinancialTO empFinancialTO=new EmpFinancialTO();
					empFinancialTO.setFinancialAmount("");
					empFinancialTO.setFinancialDate("");
					empFinancialTO.setFinancialDetails("");
					objform.setFinancialListSize(String.valueOf(flist.size()));
					flist.add(empFinancialTO);
					objform.getEmployeeInfoTONew().setEmpFinancial(flist);
					
				}
			}*/
			
		/*	if(userApplicantDetails.getGuest().getEmpIncentives()!=null)
			{
				Set<EmpIncentives> empIncentives=userApplicantDetails.getGuest().getEmpIncentives();
				if(empIncentives != null && !empIncentives.isEmpty())
				{
				Iterator<EmpIncentives> iterator=empIncentives.iterator();
				List<EmpIncentivesTO> empIncentivesTOs=new ArrayList<EmpIncentivesTO>();
				
				while(iterator.hasNext()){
					EmpIncentives empIncen=iterator.next();
					if(empIncen!=null){
						EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
					
						if(empIncen.getId()>0)
						{
							empIncentivesTO.setId(empIncen.getId());
						}
						if(StringUtils.isNotEmpty(empIncen.getIncentivesAmount())){
							empIncentivesTO.setIncentivesAmount(empIncen.getIncentivesAmount());
						}
						
						if(StringUtils.isNotEmpty(empIncen.getIncentivesDetails())){
							empIncentivesTO.setIncentivesDetails(empIncen.getIncentivesDetails());
						}
						
						if(empIncen.getIncentivesDate()!=null){
							empIncentivesTO.setIncentivesDate(CommonUtil.ConvertStringToDateFormat(empIncen.getIncentivesDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empIncentivesTOs.add(empIncentivesTO);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpIncentives(empIncentivesTOs);
				}
				else
				{
					List<EmpIncentivesTO> list=new ArrayList<EmpIncentivesTO>();
					EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
					empIncentivesTO.setIncentivesAmount("");
					empIncentivesTO.setIncentivesDate("");
					empIncentivesTO.setIncentivesDetails("");
					objform.setIncentivesListSize(String.valueOf(list.size()));
					list.add(empIncentivesTO);
					objform.getEmployeeInfoTONew().setEmpIncentives(list);
				}
			}*/
		/*	if(userApplicantDetails.getGuest().getEmpLoan()!=null)
			{
				Set<EmpLoan> empLoan=userApplicantDetails.getGuest().getEmpLoan();
				if(empLoan != null && !empLoan.isEmpty())
				{
				Iterator<EmpLoan> iterator=empLoan.iterator();
				List<EmpLoanTO> empLoanTOs=new ArrayList<EmpLoanTO>();
				
				while(iterator.hasNext()){
					EmpLoan eLoan=iterator.next();
					if(eLoan!=null){
						EmpLoanTO eLoanTO=new EmpLoanTO();
						if(eLoan.getId()>0)
						{
							eLoanTO.setId(eLoan.getId());
						}
						
						if(StringUtils.isNotEmpty(eLoan.getLoanAmount())){
							eLoanTO.setLoanAmount(eLoan.getLoanAmount());
						}
						
						if(StringUtils.isNotEmpty(eLoan.getLoanDetails())){
							eLoanTO.setLoanDetails(eLoan.getLoanDetails());
						}
						
						if(eLoan.getLoanDate()!=null){
							eLoanTO.setLoanDate(CommonUtil.ConvertStringToDateFormat(eLoan.getLoanDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empLoanTOs.add(eLoanTO);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpLoan(empLoanTOs);
				}
				else
				{
					List<EmpLoanTO> list=new ArrayList<EmpLoanTO>();
					EmpLoanTO emploanTo=new EmpLoanTO();
					emploanTo.setLoanAmount("");
					emploanTo.setLoanDate("");
					emploanTo.setLoanDetails("");
					objform.setLoanListSize(String.valueOf(list.size()));
					list.add(emploanTo);
					objform.getEmployeeInfoTONew().setEmpLoan(list);
				}
			}*/
			
			
		/*	if(userApplicantDetails.getGuest().getEmpRemarks()!=null)
			{
				Set<EmpRemarks> empRemarks=userApplicantDetails.getGuest().getEmpRemarks();
				if(empRemarks != null && !empRemarks.isEmpty())
				{
				Iterator<EmpRemarks> iterator=empRemarks.iterator();
				List<EmpRemarksTO> empRemarkTOs=new ArrayList<EmpRemarksTO>();
				
				while(iterator.hasNext()){
					EmpRemarks eRemarks=iterator.next();
					if(eRemarks!=null){
						EmpRemarksTO eRemarksTO=new EmpRemarksTO();
						if(eRemarks.getId()>0)
						{
							eRemarksTO.setId(eRemarks.getId());
						}
						if(StringUtils.isNotEmpty(eRemarks.getRemarksDetails())){
							eRemarksTO.setRemarkDetails(eRemarks.getRemarksDetails());
						}
						
						if(StringUtils.isNotEmpty(eRemarks.getRemarksEnteredBy())){
							eRemarksTO.setEnteredBy(eRemarks.getRemarksEnteredBy());
						}
						
						if(eRemarks.getRemarksDate()!=null){
							eRemarksTO.setRemarkDate(CommonUtil.ConvertStringToDateFormat(eRemarks.getRemarksDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empRemarkTOs.add(eRemarksTO);
					
				
			}
			}
				objform.getEmployeeInfoTONew().setEmpRemarks(empRemarkTOs);
				}
				else
				{
				List<EmpRemarksTO> flist=new ArrayList<EmpRemarksTO>();
				EmpRemarksTO empRemarksTO=new EmpRemarksTO();
				empRemarksTO.setEnteredBy("");
				empRemarksTO.setRemarkDate("");
				empRemarksTO.setRemarkDetails("");
				objform.setRemarksListSize(String.valueOf(flist.size()));
				flist.add(empRemarksTO);
				
				
				objform.getEmployeeInfoTONew().setEmpRemarks(flist);
					
					}
				}*/


		/*	if(userApplicantDetails.getGuest().getEmpLeaves()!=null)
			{
				Set<EmpLeave> empLeaves=userApplicantDetails.getGuest().getEmpLeaves();
				if(empLeaves != null && !empLeaves.isEmpty())
				{
				Iterator<EmpLeave> iterator=empLeaves.iterator();
 				List<EmpLeaveAllotTO> empLeaveTOs=new ArrayList<EmpLeaveAllotTO>();
				
				while(iterator.hasNext()){
					EmpLeave eLeave=iterator.next();
					if(eLeave!=null){
						EmpLeaveAllotTO eLeaveTO=new EmpLeaveAllotTO();
						
						if(eLeave.getId()>0)
						{
							eLeaveTO.setEmpLeaveId(eLeave.getId());
						}
						
					if(eLeave.getEmpLeaveType().getId()>0){
						EmpLeaveType leavetype=new EmpLeaveType();
						leavetype.setId(eLeave.getEmpLeaveType().getId());
						eLeaveTO.setEmpLeaveType(leavetype);
					}										
						
						if(StringUtils.isNotEmpty(eLeave.getEmpLeaveType().getName())){
							eLeaveTO.setLeaveType(eLeave.getEmpLeaveType().getName());
						}
						
						if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesAllocated()))){
							eLeaveTO.setAllottedLeave(String.valueOf(eLeave.getLeavesAllocated()));
						}
						if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesSanctioned()))){
							eLeaveTO.setSanctionedLeave(String.valueOf(eLeave.getLeavesSanctioned()));
						}
												
						empLeaveTOs.add(eLeaveTO);
						}
			     }
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
				}
			else
			{
				String empTypeId=null;
				if(userApplicantDetails.getGuest().getEmptype()!=null && userApplicantDetails.getGuest().getEmptype().getId()>0)
				{
				empTypeId=String.valueOf(userApplicantDetails.getGuest().getEmptype().getId());
				List<EmpLeaveAllotTO> empLeaveToList;
				try {
				empLeaveToList = EmployeeViewHandler.getInstance().getEmpLeaveList(empTypeId);
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}

		}*/
			//Leaves............................................
			
		/*	if(userApplicantDetails.getGuest().getEmpLeaves()!=null)
			{
				int month=txn.getInitializationMonth(userApplicantDetails.getGuest().getId());
				int currentMonth=currentMonth();
				int year=Calendar.getInstance().get(Calendar.YEAR);
				int year1=0;
				Set<EmpLeave> empLeaves=userApplicantDetails.getGuest().getEmpLeaves();
				List<EmpLeaveAllotTO> extEmpLeaveTos = new ArrayList<EmpLeaveAllotTO>();
				List<EmpLeaveAllotTO> empLeaveTOs=new ArrayList<EmpLeaveAllotTO>();
				if(empLeaves != null && !empLeaves.isEmpty())
				{
				Iterator<EmpLeave> iterator=empLeaves.iterator();
 				
				
				while(iterator.hasNext()){
					EmpLeave eLeave=iterator.next();
					EmpLeaveAllotTO eLeaveTO=new EmpLeaveAllotTO();
					if(eLeave!=null){
						if(month==6 && currentMonth < month && year > eLeave.getYear()){
						      year1=year-1;
					     }
						if(eLeave.getYear()==year1){
						   if(eLeave.getId()>0)
						   {
						 	  eLeaveTO.setEmpLeaveId(eLeave.getId());
						   }
						
					       if(eLeave.getEmpLeaveType().getId()>0){
						      EmpLeaveType leavetype=new EmpLeaveType();
						      leavetype.setId(eLeave.getEmpLeaveType().getId());
						      eLeaveTO.setEmpLeaveType(leavetype);
					       }										
						
						   if(StringUtils.isNotEmpty(eLeave.getEmpLeaveType().getName())){
							   eLeaveTO.setLeaveType(eLeave.getEmpLeaveType().getName());
						   }
						
						   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesAllocated()))){
							   eLeaveTO.setAllottedLeave(String.valueOf(eLeave.getLeavesAllocated()));
						   }
						   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesSanctioned()))){
							   eLeaveTO.setSanctionedLeave(String.valueOf(eLeave.getLeavesSanctioned()));
						   }
						   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesRemaining()))){
							   eLeaveTO.setRemainingLeave(String.valueOf(eLeave.getLeavesRemaining()));
						   }
							if(StringUtils.isNotEmpty(String.valueOf(eLeave.getYear()))){
								eLeaveTO.setYear(eLeave.getYear());
							}
							String monthString = new DateFormatSymbols().getMonths()[month-1];

							if(StringUtils.isNotEmpty(monthString)){
								eLeaveTO.setMonth(monthString);
							}
							empLeaveTOs.add(eLeaveTO);
						}else{
							if(eLeave.getId()>0)
							   {
							 	  eLeaveTO.setEmpLeaveId(eLeave.getId());
							   }
							
						       if(eLeave.getEmpLeaveType().getId()>0){
							      EmpLeaveType leavetype=new EmpLeaveType();
							      leavetype.setId(eLeave.getEmpLeaveType().getId());
							      eLeaveTO.setEmpLeaveType(leavetype);
						       }										
							
							   if(StringUtils.isNotEmpty(eLeave.getEmpLeaveType().getName())){
								   eLeaveTO.setLeaveType(eLeave.getEmpLeaveType().getName());
							   }
							
							   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesAllocated()))){
								   eLeaveTO.setAllottedLeave(String.valueOf(eLeave.getLeavesAllocated()));
							   }
							   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesSanctioned()))){
								   eLeaveTO.setSanctionedLeave(String.valueOf(eLeave.getLeavesSanctioned()));
							   }
							   if(StringUtils.isNotEmpty(String.valueOf(eLeave.getLeavesRemaining()))){
								   eLeaveTO.setRemainingLeave(String.valueOf(eLeave.getLeavesRemaining()));
							   }
								if(StringUtils.isNotEmpty(String.valueOf(eLeave.getYear()))){
									eLeaveTO.setYear(eLeave.getYear());
								}
								String monthString = new DateFormatSymbols().getMonths()[month-1];

								if(StringUtils.isNotEmpty(monthString)){
									eLeaveTO.setMonth(monthString);
								}
								extEmpLeaveTos.add(eLeaveTO);
						}
						
						}
			     }
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
				objform.setEmpLeaveAllotTo(extEmpLeaveTos);
				}
			else
			{
				String empTypeId=null;
				if(objform.getEmptypeId()!=null && !objform.getEmptypeId().isEmpty())
				{
				empTypeId=objform.getEmptypeId();
				List<EmpLeaveAllotTO> empLeaveToList;
				try {
				empLeaveToList = EmployeeViewHandler.getInstance().getEmpLeaveList(empTypeId,objform);
				objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			
			}*/


			
			
			
			
		/*	if (userApplicantDetails.getGuest().getEmpLeaves() != null) {
				int month=0;
				if(userApplicantDetails.getGuest().getEmptype()!=null)
				month = txn.getInitializationMonth(userApplicantDetails.getGuest().getEmptype().getId());
				int currentMonth = currentMonth();
				int year = Calendar.getInstance().get(Calendar.YEAR);
				int year1 = 0;
				Set<EmpLeave> empLeaves = userApplicantDetails.getGuest().getEmpLeaves();
				List<EmpLeaveAllotTO> extEmpLeaveTos = new ArrayList<EmpLeaveAllotTO>();
				if (empLeaves != null && !empLeaves.isEmpty()) {
					Iterator<EmpLeave> iterator = empLeaves.iterator();
					List<EmpLeaveAllotTO> empLeaveTOs = new ArrayList<EmpLeaveAllotTO>();

					while (iterator.hasNext()) {
						EmpLeave eLeave = iterator.next();
						EmpLeaveAllotTO eLeaveTO = new EmpLeaveAllotTO();
						if (eLeave != null) {
							if (month == 6 && currentMonth < month
									&& year > eLeave.getYear()) {
								year1 = year - 1;
							} else {
								year1 = year;
							}
							if (eLeave.getYear() == year1) {
								if (eLeave.getId() > 0) {
									eLeaveTO.setEmpLeaveId(eLeave.getId());
								}

								if (eLeave.getEmpLeaveType().getId() > 0) {
									EmpLeaveType leavetype = new EmpLeaveType();
									leavetype.setId(eLeave.getEmpLeaveType()
											.getId());
									eLeaveTO.setEmpLeaveType(leavetype);
								}

								if (StringUtils.isNotEmpty(eLeave.getEmpLeaveType()
										.getName())) {
									eLeaveTO.setLeaveType(eLeave.getEmpLeaveType()
											.getName());
								}

								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesAllocated()))) {
									eLeaveTO.setAllottedLeave(String.valueOf(eLeave
											.getLeavesAllocated()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesSanctioned()))) {
									eLeaveTO.setSanctionedLeave(String
											.valueOf(eLeave.getLeavesSanctioned()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesRemaining()))) {
									eLeaveTO.setRemainingLeave(String
											.valueOf(eLeave.getLeavesRemaining()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getYear()))) {
									eLeaveTO.setYear(eLeave.getYear());
								}
								String monthString = new DateFormatSymbols()
										.getMonths()[month - 1];

								if (StringUtils.isNotEmpty(monthString)) {
									eLeaveTO.setMonth(monthString);
								}
								empLeaveTOs.add(eLeaveTO);
							} else {
								if (eLeave.getId() > 0) {
									eLeaveTO.setEmpLeaveId(eLeave.getId());
								}

								if (eLeave.getEmpLeaveType().getId() > 0) {
									EmpLeaveType leavetype = new EmpLeaveType();
									leavetype.setId(eLeave.getEmpLeaveType()
											.getId());
									eLeaveTO.setEmpLeaveType(leavetype);
								}

								if (StringUtils.isNotEmpty(eLeave.getEmpLeaveType()
										.getName())) {
									eLeaveTO.setLeaveType(eLeave.getEmpLeaveType()
											.getName());
								}

								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesAllocated()))) {
									eLeaveTO.setAllottedLeave(String.valueOf(eLeave
											.getLeavesAllocated()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesSanctioned()))) {
									eLeaveTO.setSanctionedLeave(String
											.valueOf(eLeave.getLeavesSanctioned()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getLeavesRemaining()))) {
									eLeaveTO.setRemainingLeave(String
											.valueOf(eLeave.getLeavesRemaining()));
								}
								if (StringUtils.isNotEmpty(String.valueOf(eLeave
										.getYear()))) {
									eLeaveTO.setYear(eLeave.getYear());
								}
								String monthString = new DateFormatSymbols()
										.getMonths()[month - 1];

								if (StringUtils.isNotEmpty(monthString)) {
									eLeaveTO.setMonth(monthString);
								}
								extEmpLeaveTos.add(eLeaveTO);
							}

						}
					}
					Collections.sort(empLeaveTOs);
					objform.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveTOs);
					objform.setEmpLeaveAllotTo(extEmpLeaveTos);
				} else {
					String empTypeId = null;
					if (objform.getEmptypeId() != null
							&& !objform.getEmptypeId().isEmpty()) {
						empTypeId = objform.getEmptypeId();
						List<EmpLeaveAllotTO> empLeaveToList;
						try {
							empLeaveToList = EmployeeViewHandler.getInstance().getEmpLeaveList(empTypeId, objform);
							Collections.sort(empLeaveToList);
							objform.getEmployeeInfoTONew().setEmpLeaveToList(
									empLeaveToList);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}*/
			
			
			
			
			
			
			
			
			
			
			
			
			
//Leaves Code Ends.....................................
		/*	if(userApplicantDetails.getGuest().getEmpImmigrations()!=null)
			{
				Set<EmpImmigration> empImmigration=userApplicantDetails.getGuest().getEmpImmigrations();
				Iterator<EmpImmigration> iterator=empImmigration.iterator();
				List<EmpImmigrationTO> empImmigrationTOs=new ArrayList<EmpImmigrationTO>();
				
				while(iterator.hasNext()){
					EmpImmigration eImmigration=iterator.next();
					if(eImmigration!=null){
						EmpImmigrationTO eImmigrationTO=new EmpImmigrationTO();
						
						
						if(eImmigration.getId()>0)
						{
							eImmigrationTO.setId(String.valueOf(eImmigration.getId()));
						}
						if(StringUtils.isNotEmpty(eImmigration.getPassportComments())){
							eImmigrationTO.setPassportComments(eImmigration.getPassportComments());
						}
						
						if(StringUtils.isNotEmpty(eImmigration.getPassportNo())){
							eImmigrationTO.setPassportNo(eImmigration.getPassportNo());
						}
						if(StringUtils.isNotEmpty(eImmigration.getPassportReviewStatus())){
							eImmigrationTO.setPassportReviewStatus(eImmigration.getPassportReviewStatus());
						}
						if(StringUtils.isNotEmpty(eImmigration.getPassportStatus())){
							eImmigrationTO.setPassportStatus(eImmigration.getPassportStatus());
						}
						if(eImmigration.getCountryByPassportCountryId()!=null && eImmigration.getCountryByPassportCountryId().getId()>0){
							
							eImmigrationTO.setPassportCountryId(String.valueOf(eImmigration.getCountryByPassportCountryId().getName()));
						}
						if(eImmigration.getPassportDateOfExpiry()!=null){
							eImmigrationTO.setPassportExpiryDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getPassportDateOfExpiry().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(eImmigration.getPassportIssueDate()!=null){
							eImmigrationTO.setPassportIssueDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getPassportIssueDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaComments())){
							eImmigrationTO.setVisaComments(eImmigration.getVisaComments());
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaNo())){
							eImmigrationTO.setVisaNo(eImmigration.getVisaNo());
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaReviewStatus())){
							eImmigrationTO.setVisaReviewStatus(eImmigration.getVisaReviewStatus());
						}
						if(StringUtils.isNotEmpty(eImmigration.getVisaStatus())){
							eImmigrationTO.setVisaStatus(eImmigration.getVisaStatus());
						}
						if(eImmigration.getCountryByVisaCountryId()!=null && eImmigration.getCountryByVisaCountryId().getId()>0){
						
							eImmigrationTO.setVisaCountryId(String.valueOf(eImmigration.getCountryByVisaCountryId().getName()));
						}
						if(eImmigration.getVisaDateOfExpiry()!=null){
							eImmigrationTO.setVisaExpiryDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getVisaDateOfExpiry().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						if(eImmigration.getVisaIssueDate()!=null){
							eImmigrationTO.setVisaIssueDate(CommonUtil.ConvertStringToDateFormat(eImmigration.getVisaIssueDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
						}
						
						empImmigrationTOs.add(eImmigrationTO);
					
				
			}
					objform.getEmployeeInfoTONew().setEmpImmigration(empImmigrationTOs);	
				}
				}
			
			
			
			if(userApplicantDetails.getGuest().getEmpPayAllowance()!=null)
			{
				Set<EmpPayAllowanceDetails> empPayAllowanceDetails=userApplicantDetails.getGuest().getEmpPayAllowance();
				if(empPayAllowanceDetails != null && !empPayAllowanceDetails.isEmpty())
				{
				Iterator<EmpPayAllowanceDetails> iterator=empPayAllowanceDetails.iterator();
				//List<EmpPayAllowanceTO> empPayAllowanceTOs=new ArrayList<EmpPayAllowanceTO>();
				List<EmpAllowanceTO> empPayAllowanceTOs=new ArrayList<EmpAllowanceTO>();
				
				List<EmpAllowanceTO> fixed=null;
				if(objform.getEmployeeInfoTONew()!=null){
				if(objform.getEmployeeInfoTONew().getPayscaleFixedTo()!=null){
				fixed=objform.getEmployeeInfoTONew().getPayscaleFixedTo();
				}
				
				//
				while(iterator.hasNext()){
					//EmpPayAllowanceTO ePayAllotTO=new EmpPayAllowanceTO();
					EmpPayAllowanceDetails ePayAlloDet=iterator.next();
					EmpAllowanceTO eAllotTO=new EmpAllowanceTO();
					if(ePayAlloDet!=null){
						
						if(fixed!=null){
							Iterator<EmpAllowanceTO> iterator2=fixed.iterator();
							while(iterator2.hasNext()){
								EmpAllowanceTO empAllTO=iterator2.next();
							if(empAllTO!=null && (empAllTO.getId()>0)){
							if(ePayAlloDet.getEmpAllowance()!= null && ePayAlloDet.getEmpAllowance().getId()>0){
								if(empAllTO.getId()==(ePayAlloDet.getEmpAllowance().getId())){
									eAllotTO.setId(ePayAlloDet.getEmpAllowance().getId());	
									//check the above line......
									if(ePayAlloDet.getId()>0){
										//ePayAllotTO.setId(ePayAlloDet.getId());
										eAllotTO.setEmpPayAllowanceId(ePayAlloDet.getId());
									}
									
									if(StringUtils.isNotEmpty(ePayAlloDet.getAllowanceValue())){
										eAllotTO.setAllowanceName(ePayAlloDet.getAllowanceValue());
										//ePayAllotTO.setAllowanceValue(eAllotTO.getAllowanceName());
									}
									
									if(StringUtils.isNotEmpty(String.valueOf(empAllTO.getName()))){
										eAllotTO.setName(empAllTO.getName());
										
									}
									eAllotTO.setDisplayOrder(empAllTO.getDisplayOrder());
									empPayAllowanceTOs.add(eAllotTO);
									
								}
							}
			     }
							}
						}
					}
				}
				Collections.sort(empPayAllowanceTOs,new PayAllowance());
				objform.getEmployeeInfoTONew().setPayscaleFixedTo(empPayAllowanceTOs);
				}
		
				}
				else
				{
					 try {
						 List<EmpAllowanceTO> payscaleFixedTo=txn.getPayAllowanceFixedMap();
					
					 if(payscaleFixedTo!=null){
						 objform.getEmployeeInfoTONew().setPayscaleFixedTo(payscaleFixedTo);
					 }
					 } catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
				}
				}*/
			
			
			
			
	
			if(userApplicantDetails.getGuest().getPreviousExpSet()!=null){
				int teachingFlag=0;
				int industryFlag=0;
				Set<GuestPreviousExperience> empOnlinePreviousExperiencesSet=userApplicantDetails.getGuest().getPreviousExpSet();
				if(empOnlinePreviousExperiencesSet != null && !empOnlinePreviousExperiencesSet.isEmpty()){
					
					Iterator<GuestPreviousExperience> iterator=empOnlinePreviousExperiencesSet.iterator();
					List<EmpPreviousOrgTo> industryExp=new ArrayList<EmpPreviousOrgTo>();
					List<EmpPreviousOrgTo> teachingExp=new ArrayList<EmpPreviousOrgTo>();
					while(iterator.hasNext()){
						GuestPreviousExperience empOnlinePreviousExperiences=iterator.next();
						if(empOnlinePreviousExperiences!=null){
							EmpPreviousOrgTo empOnliPreviousExperienceTO=new EmpPreviousOrgTo();
							
							if(empOnlinePreviousExperiences.isIndustryExperience())
							{
								if(empOnlinePreviousExperiences.getId()>0){
									empOnliPreviousExperienceTO.setId(empOnlinePreviousExperiences.getId());
								}
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpDesignation())){
									empOnliPreviousExperienceTO.setCurrentDesignation(empOnlinePreviousExperiences.getEmpDesignation());
								}
								
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpOrganization())){
									empOnliPreviousExperienceTO.setCurrentOrganisation(empOnlinePreviousExperiences.getEmpOrganization());
								}
								
								if( empOnlinePreviousExperiences.getExpMonths()>0 ){
									empOnliPreviousExperienceTO.setIndustryExpMonths(String.valueOf(empOnlinePreviousExperiences.getExpMonths()));
								}
								
								if(empOnlinePreviousExperiences.getExpYears()>0){
									empOnliPreviousExperienceTO.setIndustryExpYears(String.valueOf(empOnlinePreviousExperiences.getExpYears()));
								}
								/*code added by sudhir */
//								if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
//									empOnliPreviousExperienceTO.setIndustryFromDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getFromDate()));
//								}
//								if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
//									empOnliPreviousExperienceTO.setIndustryToDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getToDate()));
//								}
									/*code added by sudhir */
								industryFlag=1;
								industryExp.add(empOnliPreviousExperienceTO);
							}else if(empOnlinePreviousExperiences.isTeachingExperience())
							{
								if(empOnlinePreviousExperiences.getId()>0){
									empOnliPreviousExperienceTO.setId(empOnlinePreviousExperiences.getId());
								}
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpDesignation())){
									empOnliPreviousExperienceTO.setCurrentTeachnigDesignation(empOnlinePreviousExperiences.getEmpDesignation());
								}
								
								if(StringUtils.isNotEmpty(empOnlinePreviousExperiences.getEmpOrganization())){
									empOnliPreviousExperienceTO.setCurrentTeachingOrganisation(empOnlinePreviousExperiences.getEmpOrganization());
								}
								
								if( empOnlinePreviousExperiences.getExpMonths()>0 ){
									empOnliPreviousExperienceTO.setTeachingExpMonths(String.valueOf(empOnlinePreviousExperiences.getExpMonths()));
								}
								
								if(empOnlinePreviousExperiences.getExpYears()>0){
									empOnliPreviousExperienceTO.setTeachingExpYears(String.valueOf(empOnlinePreviousExperiences.getExpYears()));
								}
								/*code added by sudhir */
//								if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
//									empOnliPreviousExperienceTO.setTeachingFromDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getFromDate()));
//								}
//								if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
//									empOnliPreviousExperienceTO.setTeachingToDate(CommonUtil.formatDates(empOnlinePreviousExperiences.getToDate()));
//								}
									/*code added by sudhir */
								teachingFlag=1;
								teachingExp.add(empOnliPreviousExperienceTO);
																
							}
						}
							objform.getEmployeeInfoTONew().setExperiences(industryExp);
							objform.getEmployeeInfoTONew().setTeachingExperience(teachingExp);
					}
				}else {
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					
					List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					/*code added by sudhir */
					empPreviousOrgTo.setTeachingFromDate("");
					empPreviousOrgTo.setTeachingToDate("");
					/*--------------------- */
					objform.setTeachingExpLength(String.valueOf(teachingList.size()));
					teachingList.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
				}
				if(teachingFlag==1 && industryFlag==0)
				{
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
				}
				if(teachingFlag==0 && industryFlag==1)
				{
					List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					/*code added by sudhir */
					empPreviousOrgTo.setTeachingFromDate("");
					empPreviousOrgTo.setTeachingToDate("");
					/*--------------------- */
					objform.setTeachingExpLength(String.valueOf(teachingList.size()));
					teachingList.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
				}
				if(teachingFlag==1 && industryFlag==0)
				{
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
				}
				if(teachingFlag==0 && industryFlag==0)
				{
					List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					/*code added by sudhir */
					empPreviousOrgTo.setTeachingFromDate("");
					empPreviousOrgTo.setTeachingToDate("");
					/*--------------------- */
					objform.setTeachingExpLength(String.valueOf(teachingList.size()));
					teachingList.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
					
					List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
					
					empPreviousOrgTo.setIfEmpty("1");
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*code added by sudhir */
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					/*--------------------- */
					objform.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					objform.getEmployeeInfoTONew().setExperiences(list);
				}
			}
			/*----------------------------------- code added by sudhir---------------------------------------*/
			/* calculating Experience in cjc (or) cu from joining Date to present day*/
			Date joiningDate = null;
			String fromDateDay = null;
			String fromDateMonth = null;
			if(objform.getDateOfJoining()!=null && !objform.getDateOfJoining().isEmpty()){
				 joiningDate = CommonUtil.ConvertStringToDate(objform.getDateOfJoining());
				 fromDateDay = objform.getDateOfJoining().substring(0, 2);
				 fromDateMonth = objform.getDateOfJoining().substring(3, 5);
			}
			String todaysDate = CommonUtil.getTodayDate();
			String toDateDay = todaysDate.substring(0, 2);
			String toDateMonth = todaysDate.substring(3, 5);
			Date toDate = CommonUtil.ConvertStringToDate(todaysDate);
			if(joiningDate!=null && toDate!=null){
				
			/*int years = CommonUtil.getYearsDiff(joiningDate,toDate);
			int months = CommonUtil.getMonthsBetweenTwoYears(joiningDate, toDate,fromDate,toDateNumber);*/
				double msPerGregorianYear = 365.25 * 86400 * 1000;
			 	double years1 =(toDate.getTime() - joiningDate.getTime()) / msPerGregorianYear;
			 	int yy = (int) years1;
		        int mm = (int) ((years1 - yy) * 12);
		        if(fromDateDay.equals(toDateDay)){
		        	if(fromDateMonth.equals(toDateMonth)){
		        		mm=0;
		        		yy = (int) Math.round(years1);
		        	}
		        }
		        if(fromDateDay.equals(toDateDay)){
		        	if(!fromDateMonth.equals(toDateMonth)){
		        		mm = (int) Math.round(((years1 - yy) * 12));
		        	}
		        }
			objform.setExperienceInYears(yy);
			objform.setExperienceInMonths(mm);
			}
			/* calculating TotalCurrent Experience Years and Months based on Recognised Experience and Experience in cjc (or) cu
			int totalYears = Integer.parseInt(objform.getRelevantExpYears())+objform.getExperienceInYears();
			int totalMonths1 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getExperienceInMonths() % 12;
			int totalMonths2 = Integer.parseInt(objform.getRelevantExpMonths()) + objform.getExperienceInMonths() / 12;
			totalYears = totalYears + totalMonths2;
			objform.setTotalCurrentExpYears(totalYears);
			objform.setTotalCurrentExpMonths(totalMonths1);*/
			
			/*-----------------------------------------------------------------------------------------------*/
//			if(userApplicantDetails.getGuest().getExtensionNumber()!=null && !userApplicantDetails.getGuest().getExtensionNumber().isEmpty()){
//				objform.setExtensionNumber(userApplicantDetails.getGuest().getExtensionNumber());
//			}
			
			//For Showing Employee Information New by Bhargav 
			EmployeeInfoEditNewTO employeeInfoEditNewTO = new EmployeeInfoEditNewTO();
			if (userApplicantDetails.getGuest().getEmpAcademicDeatils()  != null) {
				Set<EmpAcademicQualificationBO> empAcademic = userApplicantDetails.getGuest()
						.getEmpAcademicDeatils();
				if (empAcademic != null && !empAcademic.isEmpty()) {
					Iterator<EmpAcademicQualificationBO> iterator = empAcademic.iterator();
					List<EmpAcademicQualificationTO> empAcademicQualificationTOs = new ArrayList<EmpAcademicQualificationTO>();

					while (iterator.hasNext()) {
						EmpAcademicQualificationBO empAcademicbo = iterator.next();
						if (empAcademic != null) {
							EmpAcademicQualificationTO empAcademicTo = new EmpAcademicQualificationTO();

							if (empAcademicTo.getId() > 0) {
								empAcademicTo.setId(empAcademicbo.getId());
							}
							if (StringUtils.isNotEmpty(empAcademicbo
									.getCourseName())) {
								empAcademicTo.setCourseName(empAcademicbo
										.getCourseName());
							}

							if (StringUtils.isNotEmpty(empAcademicbo.getUniversity())) {
								empAcademicTo.setUniversityName(empAcademicbo.getUniversity());
							}
							if (StringUtils.isNotEmpty(empAcademicbo.getYear())) {
								empAcademicTo.setYear(empAcademicbo.getYear());
							}
							if (StringUtils.isNotEmpty(empAcademicbo.getGrade())) {
								empAcademicTo.setGrade(empAcademicbo.getGrade());
							}


							empAcademicQualificationTOs.add(empAcademicTo);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpqualification(empAcademicQualificationTOs);
					employeeInfoEditNewTO.setEmpqualification(empAcademicQualificationTOs);
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpIntrestDetails()  != null) {
				Set<EmpIntrestsBO> empIntrestsBOs =userApplicantDetails.getGuest().getEmpIntrestDetails();
				if (empIntrestsBOs != null && !empIntrestsBOs.isEmpty()) {
					Iterator<EmpIntrestsBO> iterator = empIntrestsBOs.iterator();
					List<EmpInterestsTO> empInterestsTOs = new ArrayList<EmpInterestsTO>();

					while (iterator.hasNext()) {
						EmpIntrestsBO empIntrestsBO = iterator.next();
						if (empIntrestsBO != null) {
							EmpInterestsTO empInterestsTO = new EmpInterestsTO();

							if (empInterestsTO.getId() > 0) {
								empInterestsTO.setId(empIntrestsBO.getId());
							}
							if (StringUtils.isNotEmpty(empIntrestsBO
									.getTopic())) {
								empInterestsTO.setTopic(empIntrestsBO
										.getTopic());
							}

							empInterestsTOs.add(empInterestsTO);

						}
					}
//				objform.getEmployeeInfoEditNewTO().setInterest(empInterestsTOs);
					employeeInfoEditNewTO.setInterest(empInterestsTOs);
				}
			}
			
			
			if (userApplicantDetails.getGuest().getEmpResearchDetails()  != null) {
				Set<EmpResearchBo> empResearchBos =userApplicantDetails.getGuest().getEmpResearchDetails();
				if (empResearchBos != null && !empResearchBos.isEmpty()) {
					Iterator<EmpResearchBo> iterator = empResearchBos.iterator();
					List<EmpFieldResearchTO> empResearchTOs = new ArrayList<EmpFieldResearchTO>();

					while (iterator.hasNext()) {
						EmpResearchBo empResearchBo = iterator.next();
						if (empResearchBo != null) {
							EmpFieldResearchTO empResearchTO = new EmpFieldResearchTO();

							if (empResearchTO.getId() > 0) {
								empResearchTO.setId(empResearchBo.getId());
							}
							if (StringUtils.isNotEmpty(empResearchBo
									.getTitle())) {
								empResearchTO.setTopic(empResearchBo
										.getTitle());
							}

							empResearchTOs.add(empResearchTO);

						}
					}
//				objform.getEmployeeInfoEditNewTO().setResearch(empResearchTOs);
					employeeInfoEditNewTO.setResearch(empResearchTOs);
				} 
			}
			
			
			if (userApplicantDetails.getGuest().getEmpGuideshipDetails()  != null) {
				Set<EmpGuideshipDetailsBo> empGuideshipDetailsBos =userApplicantDetails.getGuest().getEmpGuideshipDetails();
				if (empGuideshipDetailsBos != null && !empGuideshipDetailsBos.isEmpty()) {
					Iterator<EmpGuideshipDetailsBo> iterator = empGuideshipDetailsBos.iterator();
					List<EmpGuideShipDetailsTO> empGuideShipDetailsTOs = new ArrayList<EmpGuideShipDetailsTO>();

					while (iterator.hasNext()) {
						EmpGuideshipDetailsBo empGuideshipDetailsBo = iterator.next();
						if (empGuideshipDetailsBo != null) {
							EmpGuideShipDetailsTO empGuideShipDetailsTO = new EmpGuideShipDetailsTO();

							if (empGuideShipDetailsTO.getId() > 0) {
								empGuideShipDetailsTO.setId(empGuideshipDetailsBo.getId());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getScholarName())) {
								empGuideShipDetailsTO.setPhdScholarName(empGuideshipDetailsBo
										.getScholarName());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getRegistraionYear())) {
								empGuideShipDetailsTO.setRegistrationYear(empGuideshipDetailsBo
										.getRegistraionYear());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getAwarded())) {
								empGuideShipDetailsTO.setAwarded(empGuideshipDetailsBo
										.getAwarded());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getAwardedYear())) {
								empGuideShipDetailsTO.setYear(empGuideshipDetailsBo
										.getAwardedYear());
							}
							if (StringUtils.isNotEmpty(empGuideshipDetailsBo
									.getAwardedThesisName())) {
								empGuideShipDetailsTO.setThesisName(empGuideshipDetailsBo
										.getAwardedThesisName());
							}

							empGuideShipDetailsTOs.add(empGuideShipDetailsTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setGuideship(empGuideShipDetailsTOs);
					employeeInfoEditNewTO.setGuideship(empGuideShipDetailsTOs);
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpDutiesDeatils()  != null) {
				Set<EmpDutiesDetailsBO> empDutiesDetailsBOs =userApplicantDetails.getGuest().getEmpDutiesDeatils();
				if (empDutiesDetailsBOs != null && !empDutiesDetailsBOs.isEmpty()) {
					Iterator<EmpDutiesDetailsBO> iterator = empDutiesDetailsBOs.iterator();
					List<EmpDutiesPerformedTO> empDutiesPerformedTOs = new ArrayList<EmpDutiesPerformedTO>();

					while (iterator.hasNext()) {
						EmpDutiesDetailsBO empDutiesDetailsBO = iterator.next();
						if (empDutiesDetailsBO != null) {
							EmpDutiesPerformedTO empDutiesPerformedTO = new EmpDutiesPerformedTO();

							if (empDutiesPerformedTO.getId() > 0) {
								empDutiesPerformedTO.setId(empDutiesDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empDutiesDetailsBO
									.getPosition())) {
								empDutiesPerformedTO.setPositionName(empDutiesDetailsBO
										.getPosition());
							}
							if (empDutiesDetailsBO
									.getFromDate() != null) {
								empDutiesPerformedTO.setFromDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							if (empDutiesDetailsBO.getToDate() != null) {
								empDutiesPerformedTO.setToDate(CommonUtil.ConvertStringToDateFormat(empDutiesDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							

							empDutiesPerformedTOs.add(empDutiesPerformedTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setDuties(empDutiesPerformedTOs);
					employeeInfoEditNewTO.setDuties(empDutiesPerformedTOs);
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpProjectDetails()  != null) {
				Set<EmpProjectResearchDetailsBO> empProjectResearchDetailsBOs =userApplicantDetails.getGuest().getEmpProjectDetails();
				if (empProjectResearchDetailsBOs != null && !empProjectResearchDetailsBOs.isEmpty()) {
					Iterator<EmpProjectResearchDetailsBO> iterator = empProjectResearchDetailsBOs.iterator();
					List<EmpResearchTO> empResearchProjectTOs = new ArrayList<EmpResearchTO>();

					while (iterator.hasNext()) {
						EmpProjectResearchDetailsBO empResearchDetailsBO = iterator.next();
						if (empResearchDetailsBO != null) {
							EmpResearchTO empResearchProjectTO = new EmpResearchTO();

							if (empResearchProjectTO.getId() > 0) {
								empResearchProjectTO.setId(empResearchDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle()
									)) {
								empResearchProjectTO.setProjectName(empResearchDetailsBO.getPrjectName());
							}
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getTitle())) {
						       empResearchProjectTO.setTitle(empResearchDetailsBO.getTitle());
					        }
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getFundingAgency())) {
						   empResearchProjectTO.setFindingAgencyName(empResearchDetailsBO.getFundingAgency());
					        }
							if (StringUtils.isNotEmpty(empResearchDetailsBO.getAmount())) {
						      empResearchProjectTO.setAmount(empResearchDetailsBO.getAmount());
					           }
							if (empResearchDetailsBO
									.getFromDate() != null) {
								empResearchProjectTO.setFromDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO
										.getFromDate() .toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							if (empResearchDetailsBO.getToDate() != null) {
								empResearchProjectTO.setToDate1(CommonUtil.ConvertStringToDateFormat(empResearchDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
							}
							

							empResearchProjectTOs.add(empResearchProjectTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setResearchProject(empResearchProjectTOs);
					employeeInfoEditNewTO.setResearchProject(empResearchProjectTOs);
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpResearchPublication() != null) {
				Set<EmpReaserchPublishcationDetailsBO> empProjePublishcationDetailsBOs =userApplicantDetails.getGuest().getEmpResearchPublication();
				if (empProjePublishcationDetailsBOs != null && !empProjePublishcationDetailsBOs.isEmpty()) {
					Iterator<EmpReaserchPublishcationDetailsBO> iterator = empProjePublishcationDetailsBOs.iterator();
					List<EmpResearchPublicationTO> empResearchPublicationTOs = new ArrayList<EmpResearchPublicationTO>();

					while (iterator.hasNext()) {
						EmpReaserchPublishcationDetailsBO empReaserchPublishcationDetailsBO = iterator.next();
						if (empReaserchPublishcationDetailsBO != null) {
							EmpResearchPublicationTO empResearchPublicationTO = new EmpResearchPublicationTO();

							if (empResearchPublicationTO.getId() > 0) {
								empResearchPublicationTO.setId(empReaserchPublishcationDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getTitle())) {
								empResearchPublicationTO.setPaperTitle(empReaserchPublishcationDetailsBO.getTitle());
							}
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getJournalName())) {
								empResearchPublicationTO.setJournalName(empReaserchPublishcationDetailsBO.getJournalName());
					        }
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getUGC())) {
								empResearchPublicationTO.setUGCNonUGC(empReaserchPublishcationDetailsBO.getUGC());
					        }
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getISBNISSNNo())) {
								empResearchPublicationTO.setISBNISSNNo(empReaserchPublishcationDetailsBO.getISBNISSNNo());
					        }
							if (StringUtils.isNotEmpty(empReaserchPublishcationDetailsBO.getYear())) {
								empResearchPublicationTO.setYear(empReaserchPublishcationDetailsBO.getYear());
					        }
							
							empResearchPublicationTOs.add(empResearchPublicationTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setResearchPubliction(empResearchPublicationTOs);
					employeeInfoEditNewTO.setResearchPubliction(empResearchPublicationTOs);
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpBooksPublished() != null) {
				Set<EmpBooksPublishedDetailsBO> empBooksPublishedDetailsBOs =userApplicantDetails.getGuest().getEmpBooksPublished();
				if (empBooksPublishedDetailsBOs != null && !empBooksPublishedDetailsBOs.isEmpty()) {
					Iterator<EmpBooksPublishedDetailsBO> iterator = empBooksPublishedDetailsBOs.iterator();
					List<EmpBooksPublishedTO> empBooksPublishedTOs = new ArrayList<EmpBooksPublishedTO>();

					while (iterator.hasNext()) {
						EmpBooksPublishedDetailsBO empBooksPublishedDetailsBO = iterator.next();
						if (empBooksPublishedDetailsBO != null) {
							EmpBooksPublishedTO empBooksPublishedTO = new EmpBooksPublishedTO();

							if (empBooksPublishedTO.getId() > 0) {
								empBooksPublishedTO.setId(empBooksPublishedDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getBookName())) {
								empBooksPublishedTO.setTitleName(empBooksPublishedDetailsBO.getBookName());
							}
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getPublisherName())) {
								empBooksPublishedTO.setPublisherName(empBooksPublishedDetailsBO.getPublisherName());
					        }
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getContribution())) {
								empBooksPublishedTO.setContibutionName(empBooksPublishedDetailsBO.getContribution());
					        }
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getYear())) {
								empBooksPublishedTO.setYear(empBooksPublishedDetailsBO.getYear());
					        }
							if (StringUtils.isNotEmpty(empBooksPublishedDetailsBO.getISBNISSNNo())) {
								empBooksPublishedTO.setISBNISSN(empBooksPublishedDetailsBO.getISBNISSNNo());
					        }
							
							empBooksPublishedTOs.add(empBooksPublishedTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpBooks(empBooksPublishedTOs);
					employeeInfoEditNewTO.setEmpBooks(empBooksPublishedTOs);
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpPaperPresentaion() != null) {
				Set<EmpPaperPresentationBO> empPaperPresentationBOs =userApplicantDetails.getGuest().getEmpPaperPresentaion();
				if (empPaperPresentationBOs != null && !empPaperPresentationBOs.isEmpty()) {
					Iterator<EmpPaperPresentationBO> iterator = empPaperPresentationBOs.iterator();
					List<EmpPaperPresentationTO> empPresentationTOs = new ArrayList<EmpPaperPresentationTO>();

					while (iterator.hasNext()) {
						EmpPaperPresentationBO empPaperPresentationBO = iterator.next();
						if (empPaperPresentationBO != null) {
							EmpPaperPresentationTO empPaperPresentationTO = new EmpPaperPresentationTO();

							if (empPaperPresentationTO.getId() > 0) {
								empPaperPresentationTO.setId(empPaperPresentationBO.getId());
							}
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getInstitution())) {
								empPaperPresentationTO.setOrganisation(empPaperPresentationBO.getInstitution());
							}
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getInterRegoinal())) {
								empPaperPresentationTO.setRegional(empPaperPresentationBO.getInterRegoinal());
					        }
							if (empPaperPresentationBO.getDate() != null) {
								empPaperPresentationTO.setDate1(CommonUtil.ConvertStringToDateFormat(empPaperPresentationBO.getDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getProceedingsTile())) {
								empPaperPresentationTO.setSeminarName(empPaperPresentationBO.getProceedingsTile());
					        }
							if (StringUtils.isNotEmpty(empPaperPresentationBO.getPaperTile())) {
								empPaperPresentationTO.setPaperName(empPaperPresentationBO.getPaperTile());
					        }
							
							empPresentationTOs.add(empPaperPresentationTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpPaper(empPresentationTOs);
					employeeInfoEditNewTO.setEmpPaper(empPresentationTOs);
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpSeminarAttended() != null) {
				Set<EmpSeminarAttendedDetailsBO> empSeminarAttendedDetailsBOs =userApplicantDetails.getGuest().getEmpSeminarAttended();
				if (empSeminarAttendedDetailsBOs != null && !empSeminarAttendedDetailsBOs.isEmpty()) {
					Iterator<EmpSeminarAttendedDetailsBO> iterator = empSeminarAttendedDetailsBOs.iterator();
					List<EmpSeminarAttendedDetailsTO> empSeminarAttendedDetailsTOs = new ArrayList<EmpSeminarAttendedDetailsTO>();

					while (iterator.hasNext()) {
						EmpSeminarAttendedDetailsBO empSeminarAttendedDetailsBO = iterator.next();
						if (empSeminarAttendedDetailsBO != null) {
							EmpSeminarAttendedDetailsTO empSeminarAttendedDetailsTO = new EmpSeminarAttendedDetailsTO();

							if (empSeminarAttendedDetailsTO.getId() > 0) {
								empSeminarAttendedDetailsTO.setId(empSeminarAttendedDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInstitution())) {
								empSeminarAttendedDetailsTO.setOrganisation(empSeminarAttendedDetailsBO.getInstitution());
							}
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getInterRegional())) {
								empSeminarAttendedDetailsTO.setInterRegional(empSeminarAttendedDetailsBO.getInterRegional());
					        }
							if (empSeminarAttendedDetailsBO.getFromDate2() != null) {
								empSeminarAttendedDetailsTO.setFromDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getFromDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (empSeminarAttendedDetailsBO.getToDate2() != null) {
								empSeminarAttendedDetailsTO.setToDate2(CommonUtil.ConvertStringToDateFormat(empSeminarAttendedDetailsBO.getToDate2().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getParticipation())) {
								empSeminarAttendedDetailsTO.setParticipation(empSeminarAttendedDetailsBO.getParticipation());
					        }
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getTitle())) {
								empSeminarAttendedDetailsTO.setSeminarName(empSeminarAttendedDetailsBO.getTitle());
					        }
							if (StringUtils.isNotEmpty(empSeminarAttendedDetailsBO.getWorkShop())) {
								empSeminarAttendedDetailsTO.setSeminar(empSeminarAttendedDetailsBO.getWorkShop());
					        }
							
							empSeminarAttendedDetailsTOs.add(empSeminarAttendedDetailsTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpSeminarAttendedDetails(empSeminarAttendedDetailsTOs);
					employeeInfoEditNewTO.setEmpSeminarAttendedDetails(empSeminarAttendedDetailsTOs);
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpProfeDevelopment() != null) {
				Set<EmpProfessionalDevelopmentBO> empProfessionalDevelopmentBOs =userApplicantDetails.getGuest().getEmpProfeDevelopment();
				if (empProfessionalDevelopmentBOs != null && !empProfessionalDevelopmentBOs.isEmpty()) {
					Iterator<EmpProfessionalDevelopmentBO> iterator = empProfessionalDevelopmentBOs.iterator();
					List<EmpProfessionalDevelopmentTO> empProfessionalDevelopmentTOs = new ArrayList<EmpProfessionalDevelopmentTO>();

					while (iterator.hasNext()) {
						EmpProfessionalDevelopmentBO empProfessionalDevelopmentBO = iterator.next();
						if (empProfessionalDevelopmentBO != null) {
							EmpProfessionalDevelopmentTO empProfessionalDevelopmentTO = new EmpProfessionalDevelopmentTO();

							if (empProfessionalDevelopmentTO.getId() > 0) {
								empProfessionalDevelopmentTO.setId(empProfessionalDevelopmentBO.getId());
							}
							if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getName())) {
								empProfessionalDevelopmentTO.setName(empProfessionalDevelopmentBO.getName());
							}
							if (StringUtils.isNotEmpty(empProfessionalDevelopmentBO.getInstitute())) {
								empProfessionalDevelopmentTO.setOrganisation(empProfessionalDevelopmentBO.getInstitute());
					        }
							if (empProfessionalDevelopmentBO.getFromDate() != null) {
								empProfessionalDevelopmentTO.setFromDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (empProfessionalDevelopmentBO.getToDate() != null) {
								empProfessionalDevelopmentTO.setToDate3(CommonUtil.ConvertStringToDateFormat(empProfessionalDevelopmentBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							
							empProfessionalDevelopmentTOs.add(empProfessionalDevelopmentTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpPersonaldevelopment(empProfessionalDevelopmentTOs);
					employeeInfoEditNewTO.setEmpPersonaldevelopment(empProfessionalDevelopmentTOs);
					
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpAwardDetails() != null) {
				Set<EmpAwardDetailsBO> empAwardDetailsBOs =userApplicantDetails.getGuest().getEmpAwardDetails();
				if (empAwardDetailsBOs != null && !empAwardDetailsBOs.isEmpty()) {
					Iterator<EmpAwardDetailsBO> iterator = empAwardDetailsBOs.iterator();
					List<EmpAwardTO> empAwardTOs = new ArrayList<EmpAwardTO>();

					while (iterator.hasNext()) {
						EmpAwardDetailsBO empAwardDetailsBO = iterator.next();
						if (empAwardDetailsBO != null) {
							EmpAwardTO empAwardTO = new EmpAwardTO();

							if (empAwardTO.getId() > 0) {
								empAwardTO.setId(empAwardDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getActivityName())) {
								empAwardTO.setActivityname(empAwardDetailsBO.getActivityName());
							}
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getAwardName())) {
								empAwardTO.setAwardName(empAwardDetailsBO.getAwardName());
					        }
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
								empAwardTO.setAwardbodyName(empAwardDetailsBO.getAwardBody());
					        }
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getYear())) {
								empAwardTO.setYear(empAwardDetailsBO.getYear());
					        }
							if (StringUtils.isNotEmpty(empAwardDetailsBO.getAward())) {
								empAwardTO.setRecognitionName(empAwardDetailsBO.getAward());
					        }
							empAwardTOs.add(empAwardTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpAward(empAwardTOs);
					employeeInfoEditNewTO.setEmpAward(empAwardTOs);
				} 
			}
			
			if (userApplicantDetails.getGuest().getEmpMemDetailsBOs() != null) {
				Set<EmpMemeberShipDetailsBO> empMemeberShipDetailsBOs =userApplicantDetails.getGuest().getEmpMemDetailsBOs();
				if (empMemeberShipDetailsBOs != null && !empMemeberShipDetailsBOs.isEmpty()) {
					Iterator<EmpMemeberShipDetailsBO> iterator = empMemeberShipDetailsBOs.iterator();
					List<EmpMemberShipAcademicBodyTO> empMemberShipAcademicBodyTOs = new ArrayList<EmpMemberShipAcademicBodyTO>();

					while (iterator.hasNext()) {
						EmpMemeberShipDetailsBO empMemeberShipDetailsBO = iterator.next();
						if (empMemeberShipDetailsBO != null) {
							EmpMemberShipAcademicBodyTO empMemberShipAcademicBodyTO = new EmpMemberShipAcademicBodyTO();

							if (empMemberShipAcademicBodyTO.getId() > 0) {
								empMemberShipAcademicBodyTO.setId(empMemeberShipDetailsBO.getId());
							}
							if (StringUtils.isNotEmpty(empMemeberShipDetailsBO.getName())) {
								empMemberShipAcademicBodyTO.setName(empMemeberShipDetailsBO.getName());
							}
							if (empMemeberShipDetailsBO.getFromDate() != null) {
								empMemberShipAcademicBodyTO.setFromDate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getFromDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							if (empMemeberShipDetailsBO.getToDate() != null) {
								empMemberShipAcademicBodyTO.setTodate4(CommonUtil.ConvertStringToDateFormat(empMemeberShipDetailsBO.getToDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					        }
							
							empMemberShipAcademicBodyTOs.add(empMemberShipAcademicBodyTO);

						}
					}
//					objform.getEmployeeInfoEditNewTO().setEmpMemberShip(empMemberShipAcademicBodyTOs);
					employeeInfoEditNewTO.setEmpMemberShip(empMemberShipAcademicBodyTOs);
				} 
			}
			objform.setEmployeeInfoEditNewTO(employeeInfoEditNewTO);
			
			
			}
		}
      
		
	}



