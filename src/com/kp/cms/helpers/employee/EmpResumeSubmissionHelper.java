package com.kp.cms.helpers.employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts.upload.FormFile;
import org.eclipse.birt.report.model.elements.Style;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpPreviousOrg;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.bo.employee.EmployeeSubject;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmpResumeSubmissionForm;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.utilities.CommonUtil;

public class EmpResumeSubmissionHelper {
	
	private static volatile EmpResumeSubmissionHelper instance=null;
	
	/**
	 * 
	 */
	private EmpResumeSubmissionHelper(){
		
	}
	
	/**
	 * @return
	 */
	public static EmpResumeSubmissionHelper getInstance(){
		if(instance==null){
			instance=new EmpResumeSubmissionHelper();
		}
		return instance;
	}

	/**
	 * @param empResumeSubmissionForm
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ApplicationException 
	 */
	public EmpOnlineResume convertFormToBo(EmpResumeSubmissionForm empResumeSubmissionForm,HttpSession session) throws FileNotFoundException, IOException, ApplicationException {
		EmpOnlineResume empOnlineResume=new EmpOnlineResume();
		Set<EmpOnlinePreviousExperience> previousSet=new HashSet<EmpOnlinePreviousExperience>();
		Set<EmpOnlineEducationalDetails> educationalDeatialSet=new HashSet<EmpOnlineEducationalDetails>();
		try{
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo()!=null){
			if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences()!=null){
				List<EmpPreviousOrgTo> list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences();
				if(list!=null){
					Iterator<EmpPreviousOrgTo> iterator=list.iterator();
					while(iterator.hasNext()){
						EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
						EmpOnlinePreviousExperience empPreviousExp=new EmpOnlinePreviousExperience();
						if(empPreviousOrgTo!=null){
							if(empPreviousOrgTo.getCurrentOrganisation()!=null && !empPreviousOrgTo.getCurrentOrganisation().isEmpty() 
									|| empPreviousOrgTo.getCurrentDesignation()!=null && !empPreviousOrgTo.getCurrentDesignation().isEmpty() 
									|| empPreviousOrgTo.getIndustryFromDate()!=null && !empPreviousOrgTo.getIndustryFromDate().isEmpty()
									|| empPreviousOrgTo.getIndustryToDate()!=null && !empPreviousOrgTo.getIndustryToDate().isEmpty()
									|| empPreviousOrgTo.getIndustryExpYears()!=null && !empPreviousOrgTo.getIndustryExpYears().isEmpty()
									|| empPreviousOrgTo.getIndustryExpMonths()!=null && !empPreviousOrgTo.getIndustryExpMonths().isEmpty())
							{
								if(CMSConstants.LINK_FOR_CJC)
								{
									if(empPreviousOrgTo.getIndustryFromDate()!=null && !empPreviousOrgTo.getIndustryFromDate().isEmpty()){
										empPreviousExp.setFromDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getIndustryFromDate()));
									}
									if(empPreviousOrgTo.getIndustryToDate()!=null && !empPreviousOrgTo.getIndustryToDate().isEmpty()){
										empPreviousExp.setToDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getIndustryToDate()));
									}
									if(empPreviousOrgTo.getIndustryGrossSalary()!=null && !empPreviousOrgTo.getIndustryGrossSalary().isEmpty()){
										empPreviousExp.setGrossSalary(empPreviousOrgTo.getIndustryGrossSalary());
									}
								}
								else
								{
								/* ------------------------ code added by sudhir-----------------------------------*/
								if(empPreviousOrgTo.getIndustryFromDate()!=null && !empPreviousOrgTo.getIndustryFromDate().isEmpty()){
									empPreviousExp.setFromDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getIndustryFromDate()));
								}
								if(empPreviousOrgTo.getIndustryToDate()!=null && !empPreviousOrgTo.getIndustryToDate().isEmpty()){
									empPreviousExp.setToDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getIndustryToDate()));
								}
								}
								/*---------------------------------------------------------------------------------*/
								if(empPreviousOrgTo.getIndustryExpYears()!=null && !empPreviousOrgTo.getIndustryExpYears().isEmpty()){
									empPreviousExp.setExpYears(Integer.parseInt(empPreviousOrgTo.getIndustryExpYears()));
								}
								if(empPreviousOrgTo.getIndustryExpMonths()!=null && !empPreviousOrgTo.getIndustryExpMonths().isEmpty()){
									empPreviousExp.setExpMonths(Integer.parseInt(empPreviousOrgTo.getIndustryExpMonths()));
								}
								
								if(empPreviousOrgTo.getCurrentDesignation()!=null && !empPreviousOrgTo.getCurrentDesignation().isEmpty()){
									empPreviousExp.setEmpDesignation(empPreviousOrgTo.getCurrentDesignation());
								}
								
								if(empPreviousOrgTo.getCurrentOrganisation()!=null && !empPreviousOrgTo.getCurrentOrganisation().isEmpty()){
									empPreviousExp.setEmpOrganization(empPreviousOrgTo.getCurrentOrganisation());
								}
								
								empPreviousExp.setIndustryExperience(true);
								empPreviousExp.setActive(true);
								empPreviousExp.setCreatedBy(empResumeSubmissionForm.getUserId());
								empPreviousExp.setCreatedDate(new Date());
								empPreviousExp.setModifiedBy(empResumeSubmissionForm.getUserId());
								empPreviousExp.setModifiedDate(new Date());
								previousSet.add(empPreviousExp);
							}
						}
					}
				}
			}
			if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience()!=null){
				List<EmpPreviousOrgTo> list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience();
				if(list!=null){
					Iterator<EmpPreviousOrgTo> iterator=list.iterator();
					while(iterator.hasNext()){
						EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
						EmpOnlinePreviousExperience empPreviousExp=new EmpOnlinePreviousExperience();
						if(empPreviousOrgTo!=null){
							if(empPreviousOrgTo.getCurrentTeachingOrganisation()!=null && !empPreviousOrgTo.getCurrentTeachingOrganisation().isEmpty() 
								|| empPreviousOrgTo.getCurrentTeachnigDesignation()!=null && !empPreviousOrgTo.getCurrentTeachnigDesignation().isEmpty() 
								|| empPreviousOrgTo.getTeachingFromDate()!=null && !empPreviousOrgTo.getTeachingFromDate().isEmpty()
								|| empPreviousOrgTo.getTeachingToDate()!=null && !empPreviousOrgTo.getTeachingToDate().isEmpty()
								|| empPreviousOrgTo.getTeachingExpYears()!=null && !empPreviousOrgTo.getTeachingExpYears().isEmpty()
								|| empPreviousOrgTo.getTeachingExpMonths()!=null && !empPreviousOrgTo.getTeachingExpMonths().isEmpty()){
								if(CMSConstants.LINK_FOR_CJC)
								{
									if(empPreviousOrgTo.getTeachingFromDate()!=null && !empPreviousOrgTo.getTeachingFromDate().isEmpty()){
										empPreviousExp.setFromDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getTeachingFromDate()));
									}
									if(empPreviousOrgTo.getTeachingToDate()!=null && !empPreviousOrgTo.getTeachingToDate().isEmpty()){
										empPreviousExp.setToDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getTeachingToDate()));
									}
									if(empPreviousOrgTo.getTeachingGrossSalary()!=null && !empPreviousOrgTo.getTeachingGrossSalary().isEmpty()){
										empPreviousExp.setGrossSalary(empPreviousOrgTo.getTeachingGrossSalary());
									}
								}else
								{
								/* ------------------------ code added by sudhir-----------------------------------*/
								if(empPreviousOrgTo.getTeachingFromDate()!=null && !empPreviousOrgTo.getTeachingFromDate().isEmpty()){
									empPreviousExp.setFromDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getTeachingFromDate()));
								}
								if(empPreviousOrgTo.getTeachingToDate()!=null && !empPreviousOrgTo.getTeachingToDate().isEmpty()){
									empPreviousExp.setToDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getTeachingToDate()));
								}
								}
								/*---------------------------------------------------------------------------------*/
								if(empPreviousOrgTo.getTeachingExpYears()!=null && !empPreviousOrgTo.getTeachingExpYears().isEmpty()){
									empPreviousExp.setExpYears(Integer.parseInt(empPreviousOrgTo.getTeachingExpYears()));
								}
								
								if(empPreviousOrgTo.getTeachingExpMonths()!=null && !empPreviousOrgTo.getTeachingExpMonths().isEmpty()){
									empPreviousExp.setExpMonths(Integer.parseInt(empPreviousOrgTo.getTeachingExpMonths()));
								}
								
								if(empPreviousOrgTo.getCurrentTeachnigDesignation()!=null && !empPreviousOrgTo.getCurrentTeachnigDesignation().isEmpty()){
									empPreviousExp.setEmpDesignation(empPreviousOrgTo.getCurrentTeachnigDesignation());
								}
								
								if(empPreviousOrgTo.getCurrentTeachingOrganisation()!=null && !empPreviousOrgTo.getCurrentTeachingOrganisation().isEmpty()){
									empPreviousExp.setEmpOrganization(empPreviousOrgTo.getCurrentTeachingOrganisation());
								}
								empPreviousExp.setActive(true);
								empPreviousExp.setTeachingExperience(true);
								empPreviousExp.setCreatedBy(empResumeSubmissionForm.getUserId());
								empPreviousExp.setCreatedDate(new Date());
								empPreviousExp.setModifiedBy(empResumeSubmissionForm.getUserId());
								empPreviousExp.setModifiedDate(new Date());
								previousSet.add(empPreviousExp);
							}
						}
					}
				}
				
			}
		
		if(empResumeSubmissionForm.getCurrentlyWorking()!=null && !empResumeSubmissionForm.getCurrentlyWorking().isEmpty()
				&& empResumeSubmissionForm.getCurrentlyWorking().equalsIgnoreCase("yes")){
			empOnlineResume.setCurrentlyWorking(true);
			if(empResumeSubmissionForm.getDesignationPfId()!=null && !empResumeSubmissionForm.getDesignationPfId().isEmpty()){
				empOnlineResume.setCurrentDesignation(empResumeSubmissionForm.getDesignationPfId());
			}
			
			if(empResumeSubmissionForm.getOrgAddress()!=null && !empResumeSubmissionForm.getOrgAddress().isEmpty()){
				empOnlineResume.setCurrentOrganization(empResumeSubmissionForm.getOrgAddress());
			}
		
		}else{
			empOnlineResume.setCurrentlyWorking(false);
			empOnlineResume.setCurrentDesignation(null);
			empOnlineResume.setCurrentOrganization(null);
		}
		empOnlineResume.setPreviousExpSet(previousSet);
		
		
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationFixedTo()!=null){
			List<EmpQualificationLevelTo> qualificationFixedTo=empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationFixedTo();
			Iterator< EmpQualificationLevelTo> iterator=qualificationFixedTo.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo qualificationFixed=iterator.next();
				EmpOnlineEducationalDetails educationalDeatails=null;
				if(qualificationFixed!=null){
					if(qualificationFixed.getInstitute()!=null && !qualificationFixed.getInstitute().isEmpty()){
						educationalDeatails=new EmpOnlineEducationalDetails();
						if(qualificationFixed.getEducationId()!=null && !qualificationFixed.getEducationId().isEmpty()){
							QualificationLevelBO level=new QualificationLevelBO();
							level.setId(Integer.parseInt(qualificationFixed.getEducationId()));
							educationalDeatails.setEmpQualificationLevel(level);
						}
						
						if(qualificationFixed.getCourse()!=null && !qualificationFixed.getCourse().isEmpty()){
							educationalDeatails.setCourse(qualificationFixed.getCourse());
						}
						
						if(qualificationFixed.getSpecialization()!=null && !qualificationFixed.getSpecialization().isEmpty()){
							educationalDeatails.setSpecialization(qualificationFixed.getSpecialization());
						}
						
						if(qualificationFixed.getYearOfComp()!=null && !qualificationFixed.getYearOfComp().trim().isEmpty()){
							educationalDeatails.setYearOfCompletion(Integer.valueOf(qualificationFixed.getYearOfComp()));
						}
						
						if(qualificationFixed.getGrade()!=null && !qualificationFixed.getGrade().isEmpty()){
							educationalDeatails.setGrade(qualificationFixed.getGrade());
						}
						
						if(qualificationFixed.getInstitute()!=null && !qualificationFixed.getInstitute().isEmpty()){
							educationalDeatails.setInstitute(qualificationFixed.getInstitute());
						}
						educationalDeatails.setActive(true);
						educationalDeatails.setCreatedBy(empResumeSubmissionForm.getUserId());
						educationalDeatails.setCreatedDate(new Date());
						educationalDeatails.setModifiedBy(empResumeSubmissionForm.getUserId());
						educationalDeatails.setModifiedDate(new Date());
						
						
						educationalDeatialSet.add(educationalDeatails);
					}
				}
			}
		}
		
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos()!=null){
			Iterator<EmpQualificationLevelTo> iterator=empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos().iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo levelTo=iterator.next();
				EmpOnlineEducationalDetails educationalDetails=null;
				if(levelTo!=null){
					if(levelTo.getInstitute()!=null && !levelTo.getInstitute().isEmpty()){
						educationalDetails=new EmpOnlineEducationalDetails();
						if(levelTo.getEducationId()!=null && !levelTo.getEducationId().isEmpty()){
							QualificationLevelBO level=new QualificationLevelBO();
							level.setId(Integer.parseInt(levelTo.getEducationId()));
							educationalDetails.setEmpQualificationLevel(level);
						}
						
						if(levelTo.getCourse()!=null && !levelTo.getCourse().isEmpty()){
							educationalDetails.setCourse(levelTo.getCourse());
						}
						
						if(levelTo.getSpecialization()!=null && !levelTo.getSpecialization().isEmpty()){
							educationalDetails.setSpecialization(levelTo.getSpecialization());
						}
						
						if(levelTo.getGrade()!=null && !levelTo.getGrade().isEmpty()){
							educationalDetails.setGrade(levelTo.getGrade());
						}
						
						if(levelTo.getYear()!=null && !levelTo.getYear().trim().isEmpty()){
							educationalDetails.setYearOfCompletion(Integer.parseInt(levelTo.getYear()));
						}
						
						if(levelTo.getInstitute()!=null && !levelTo.getInstitute().isEmpty()){
							educationalDetails.setInstitute(levelTo.getInstitute());
						}
						educationalDetails.setActive(true);
						educationalDetails.setCreatedBy(empResumeSubmissionForm.getUserId());
						educationalDetails.setCreatedDate(new Date());
						educationalDetails.setModifiedBy(empResumeSubmissionForm.getUserId());
						educationalDetails.setModifiedDate(new Date());
						
						educationalDeatialSet.add(educationalDetails);
					}
				}
			}
		}
		empOnlineResume.setEducationalDetailsSet(educationalDeatialSet);
		
	/*	if(empResumeSubmissionForm.getDesignationId()!=null && !empResumeSubmissionForm.getDesignationId().isEmpty()){
			Designation designation=new Designation();
			designation.setId(Integer.parseInt(empResumeSubmissionForm.getDesignationId()));
			empOnlineResume.setDesignation(designation);
		}*/
		if(empResumeSubmissionForm.getDesignationId()!=null && !empResumeSubmissionForm.getDesignationId().isEmpty()){
			empOnlineResume.setPostAppliedDesig(empResumeSubmissionForm.getDesignationId());
		}
		if(empResumeSubmissionForm.getDepartmentId()!=null && !empResumeSubmissionForm.getDepartmentId().isEmpty()){
			Department department=new Department();
			department.setId(Integer.parseInt(empResumeSubmissionForm.getDepartmentId()));
			empOnlineResume.setDepartment(department);
		}
		
		if(empResumeSubmissionForm.getJobCode()!=null && !empResumeSubmissionForm.getJobCode().isEmpty()){
			empOnlineResume.setJobCode(empResumeSubmissionForm.getJobCode());
		}
		
		if(empResumeSubmissionForm.getName()!=null && !empResumeSubmissionForm.getName().isEmpty()){
			empOnlineResume.setName(empResumeSubmissionForm.getName().toUpperCase());
		}
		
		if(empResumeSubmissionForm.getSameAddress()!=null && !empResumeSubmissionForm.getSameAddress().isEmpty()){
			String Value= empResumeSubmissionForm.getSameAddress();
			if(Value.equals("true"))
				empOnlineResume.setIsSameAddress(true);
			else
				empOnlineResume.setIsSameAddress(false);
		}
		if(empResumeSubmissionForm.getCurrentAddressLine1()!=null && !empResumeSubmissionForm.getCurrentAddressLine1().isEmpty()){
			empOnlineResume.setCurrentAddressLine1(empResumeSubmissionForm.getCurrentAddressLine1());
		}
		
		if(empResumeSubmissionForm.getCurrentAddressLine2()!=null && !empResumeSubmissionForm.getCurrentAddressLine2().isEmpty()){
			empOnlineResume.setCurrentAddressLine2(empResumeSubmissionForm.getCurrentAddressLine2());
		}
		
		if(empResumeSubmissionForm.getCurrentAddressLine3()!=null && !empResumeSubmissionForm.getCurrentAddressLine3().isEmpty()){
			empOnlineResume.setCurrentAddressLine3(empResumeSubmissionForm.getCurrentAddressLine3());
		}
		
		if(empResumeSubmissionForm.getCurrentZipCode()!=null && !empResumeSubmissionForm.getCurrentZipCode().isEmpty()){
			empOnlineResume.setCurrentZipCode(empResumeSubmissionForm.getCurrentZipCode());
		}
		
		if(empResumeSubmissionForm.getCurrentCountryId()!=null && !empResumeSubmissionForm.getCurrentCountryId().isEmpty()){
			Country currentCountry=new Country();
			currentCountry.setId(Integer.parseInt(empResumeSubmissionForm.getCurrentCountryId()));
			empOnlineResume.setCurrentCountry(currentCountry);
		}
		
		if(empResumeSubmissionForm.getCurrentState()!=null && !empResumeSubmissionForm.getCurrentState().isEmpty())
			if(!empResumeSubmissionForm.getCurrentState().equalsIgnoreCase("other")){
				State currentState=new State();
				currentState.setId(Integer.parseInt(empResumeSubmissionForm.getCurrentState()));
				empOnlineResume.setCurrentState(currentState);
				empOnlineResume.setCurrentStateOther(null);
			}else{
				if(empResumeSubmissionForm.getOtherCurrentState()!=null && !empResumeSubmissionForm.getOtherCurrentState().isEmpty()){
					empOnlineResume.setCurrentStateOther(empResumeSubmissionForm.getOtherCurrentState());
					empOnlineResume.setCurrentState(null);
				}
			}
		
		if(empResumeSubmissionForm.getCurrentCity()!=null && !empResumeSubmissionForm.getCurrentCity().isEmpty()){
			empOnlineResume.setCurrentCity(empResumeSubmissionForm.getCurrentCity());
		}
		
		
		if(empResumeSubmissionForm.getSameAddress().equalsIgnoreCase("true")){
		
			if(empResumeSubmissionForm.getCurrentAddressLine1()!=null && !empResumeSubmissionForm.getCurrentAddressLine1().isEmpty()){
				empOnlineResume.setAddressLine1(empResumeSubmissionForm.getCurrentAddressLine1());
			}
			
			if(empResumeSubmissionForm.getCurrentAddressLine2()!=null && !empResumeSubmissionForm.getCurrentAddressLine2().isEmpty()){
				empOnlineResume.setAddressLine2(empResumeSubmissionForm.getCurrentAddressLine2());
			}
			
			if(empResumeSubmissionForm.getCurrentAddressLine3()!=null && !empResumeSubmissionForm.getCurrentAddressLine3().isEmpty()){
				empOnlineResume.setAddressLine3(empResumeSubmissionForm.getCurrentAddressLine3());
			}
			
			if(empResumeSubmissionForm.getCurrentZipCode()!=null && !empResumeSubmissionForm.getCurrentZipCode().isEmpty()){
				empOnlineResume.setPermanentZipCode(empResumeSubmissionForm.getCurrentZipCode());
			}
			
			if(empResumeSubmissionForm.getCurrentCountryId()!=null && !empResumeSubmissionForm.getCurrentCountryId().isEmpty()){
				Country currentCountry=new Country();
				currentCountry.setId(Integer.parseInt(empResumeSubmissionForm.getCurrentCountryId()));
				empOnlineResume.setCountry(currentCountry);
			}
			
			if(empResumeSubmissionForm.getCurrentState()!=null && !empResumeSubmissionForm.getCurrentState().isEmpty())
				if(!empResumeSubmissionForm.getCurrentState().equalsIgnoreCase("other")){
					State currentState=new State();
					currentState.setId(Integer.parseInt(empResumeSubmissionForm.getCurrentState()));
					empOnlineResume.setState(currentState);
					empOnlineResume.setPermanentStateOther(null);
				}else{ 
					if(empResumeSubmissionForm.getOtherCurrentState()!=null && !empResumeSubmissionForm.getOtherCurrentState().isEmpty()){
						empOnlineResume.setPermanentStateOther(empResumeSubmissionForm.getOtherCurrentState());
						empOnlineResume.setState(null);
					}
				}
			
			if(empResumeSubmissionForm.getCurrentCity()!=null && !empResumeSubmissionForm.getCurrentCity().isEmpty()){
				empOnlineResume.setCity(empResumeSubmissionForm.getCurrentCity());
			}
			
		}else{
			if(empResumeSubmissionForm.getAddressLine1()!=null && !empResumeSubmissionForm.getAddressLine1().isEmpty()){
				empOnlineResume.setAddressLine1(empResumeSubmissionForm.getAddressLine1());
			}
			
			if(empResumeSubmissionForm.getAddressLine2()!=null && !empResumeSubmissionForm.getAddressLine2().isEmpty()){
				empOnlineResume.setAddressLine2(empResumeSubmissionForm.getAddressLine2());
			}
			
			if(empResumeSubmissionForm.getAddressLine3()!=null && !empResumeSubmissionForm.getAddressLine3().isEmpty()){
				empOnlineResume.setAddressLine3(empResumeSubmissionForm.getAddressLine3());
			}
			
			if(empResumeSubmissionForm.getPermanentZipCode()!=null && !empResumeSubmissionForm.getPermanentZipCode().isEmpty()){
				empOnlineResume.setPermanentZipCode(empResumeSubmissionForm.getPermanentZipCode());
			}
			
			if(empResumeSubmissionForm.getCountryId()!=null && !empResumeSubmissionForm.getCountryId().isEmpty()){
				Country country=new Country();
				country.setId(Integer.parseInt(empResumeSubmissionForm.getCountryId()));
				empOnlineResume.setCountry(country);
			}
			
			if(empResumeSubmissionForm.getStateId()!=null && !empResumeSubmissionForm.getStateId().isEmpty())
				if(!empResumeSubmissionForm.getStateId().equalsIgnoreCase("other")){
					State state=new State();
					state.setId(Integer.parseInt(empResumeSubmissionForm.getStateId()));
					empOnlineResume.setState(state);
					empOnlineResume.setPermanentStateOther(null);
				}else{
					if(empResumeSubmissionForm.getOtherPermanentState()!=null && !empResumeSubmissionForm.getOtherPermanentState().isEmpty()){
						empOnlineResume.setPermanentStateOther(empResumeSubmissionForm.getOtherPermanentState());
						empOnlineResume.setState(null);
					}
				}
			
			if(empResumeSubmissionForm.getCity()!=null && !empResumeSubmissionForm.getCity().isEmpty()){
				empOnlineResume.setCity(empResumeSubmissionForm.getCity());
			}
		}
		if(empResumeSubmissionForm.getNationalityId()!=null && !empResumeSubmissionForm.getNationalityId().isEmpty()){
			Nationality nationality=new Nationality();
			nationality.setId(Integer.parseInt(empResumeSubmissionForm.getNationalityId()));
			empOnlineResume.setNationality(nationality);
		}
		
		if(empResumeSubmissionForm.getGender()!=null && !empResumeSubmissionForm.getGender().isEmpty()){
			empOnlineResume.setGender(empResumeSubmissionForm.getGender());
		}
		
		if(empResumeSubmissionForm.getMaritalStatus()!=null && !empResumeSubmissionForm.getMaritalStatus().isEmpty()){
			empOnlineResume.setMaritalStatus(empResumeSubmissionForm.getMaritalStatus());
		}
		
		if(empResumeSubmissionForm.getDateOfBirth()!=null && !empResumeSubmissionForm.getDateOfBirth().isEmpty()){
			empOnlineResume.setDateOfBirth(CommonUtil.ConvertStringToDate(empResumeSubmissionForm.getDateOfBirth()));
		}
		
		if(empResumeSubmissionForm.getEmail()!=null && !empResumeSubmissionForm.getEmail().isEmpty()){
			empOnlineResume.setEmail(empResumeSubmissionForm.getEmail());
		}
			
		if(empResumeSubmissionForm.getReservationCategory()!=null && empResumeSubmissionForm.getReservationCategory().length>0){
			StringBuilder reservationCategory =new StringBuilder();
			String[] categories=empResumeSubmissionForm.getReservationCategory();
			for(int i=0;i<categories.length;){
				reservationCategory.append(categories[i]);
				i++;
				if(i<categories.length)
				     reservationCategory.append(",");
			}
			empOnlineResume.setReservationCategory(reservationCategory.toString());
		}
		 if(empResumeSubmissionForm.getHandicappedDescription()!=null && !empResumeSubmissionForm.getHandicappedDescription().trim().isEmpty())
				empOnlineResume.setHandicappedDescription(empResumeSubmissionForm.getHandicappedDescription());	
		
		if(empResumeSubmissionForm.getPhNo1()!=null && !empResumeSubmissionForm.getPhNo1().isEmpty()){
			empOnlineResume.setPhNo1(empResumeSubmissionForm.getPhNo1());
		}
		
		if(empResumeSubmissionForm.getPhNo2()!=null && !empResumeSubmissionForm.getPhNo2().isEmpty()){
			empOnlineResume.setPhNo2(empResumeSubmissionForm.getPhNo2());
		}
		
		if(empResumeSubmissionForm.getPhNo3()!=null && !empResumeSubmissionForm.getPhNo3().isEmpty()){
			empOnlineResume.setPhNo3(empResumeSubmissionForm.getPhNo3());
		}
		if(empResumeSubmissionForm.getWorkPhNo1()!=null && !empResumeSubmissionForm.getWorkPhNo1().isEmpty()){
			empOnlineResume.setWorkPhNo1(empResumeSubmissionForm.getWorkPhNo1());
		}
		
		if(empResumeSubmissionForm.getWorkPhNo2()!=null && !empResumeSubmissionForm.getWorkPhNo2().isEmpty()){
			empOnlineResume.setWorkPhNo2(empResumeSubmissionForm.getWorkPhNo2());
		}
		
		if(empResumeSubmissionForm.getWorkPhNo3()!=null && !empResumeSubmissionForm.getWorkPhNo3().isEmpty()){
			empOnlineResume.setWorkPhNo3(empResumeSubmissionForm.getWorkPhNo3());
		}
		
		if(empResumeSubmissionForm.getMobileNo1()!=null && !empResumeSubmissionForm.getMobileNo1().isEmpty()){
			empOnlineResume.setMobileNo1(empResumeSubmissionForm.getMobileNo1());
		}
		
		if(empResumeSubmissionForm.getEmpSubjectAreaId()!=null && !empResumeSubmissionForm.getEmpSubjectAreaId().isEmpty()){
			SubjectAreaBO subjectArea=new SubjectAreaBO();
			subjectArea.setId(Integer.parseInt(empResumeSubmissionForm.getEmpSubjectAreaId()));
			empOnlineResume.setEmpSubjectArea(subjectArea);
		}
		//added by Smitha for new field industry functional area
		if(empResumeSubmissionForm.getIndustryFunctionalArea()!=null && !empResumeSubmissionForm.getIndustryFunctionalArea().trim().isEmpty())
			empOnlineResume.setIndustryFunctionalArea(empResumeSubmissionForm.getIndustryFunctionalArea());
		
		if(empResumeSubmissionForm.getEmpJobTypeId()!=null && !empResumeSubmissionForm.getEmpJobTypeId().isEmpty()){
			
			empOnlineResume.setEmpJobType(empResumeSubmissionForm.getEmpJobTypeId());
		}
		
		if(empResumeSubmissionForm.getEmploymentStatus()!=null && !empResumeSubmissionForm.getEmploymentStatus().isEmpty()){
			empOnlineResume.setEmploymentStatus(empResumeSubmissionForm.getEmploymentStatus());
		}
		
		if(empResumeSubmissionForm.getExpectedSalaryLakhs()!=null && !empResumeSubmissionForm.getExpectedSalaryLakhs().isEmpty()){
			empOnlineResume.setExpectedSalaryLakhs(Integer.parseInt(empResumeSubmissionForm.getExpectedSalaryLakhs()));
		}
		
		if(empResumeSubmissionForm.getExpectedSalaryThousands()!=null && !empResumeSubmissionForm.getExpectedSalaryThousands().isEmpty()){
			empOnlineResume.setExpectedSalaryThousands(Integer.parseInt(empResumeSubmissionForm.getExpectedSalaryThousands()));
		}
		
		/*if(empResumeSubmissionForm.getEligibilityTest()!=null && empResumeSubmissionForm.getEligibilityTest().length>0){
			String eligibilityTest="";
			String[] eligibility=empResumeSubmissionForm.getEligibilityTest();
			for(int i=0;i<eligibility.length;i++){
				if(!eligibility[i].equalsIgnoreCase("Other")){
//				eligibilityTest=eligibilityTest+eligibility[i];
				if(i<eligibility.length){
					if(!eligibilityTest.isEmpty())
						eligibilityTest=eligibilityTest+","+eligibility[i];
					else
						eligibilityTest=eligibility[i];
					}
				}
			}
			empOnlineResume.setEligibilityTest(eligibilityTest);
		}
		//added by smitha for eligibility test other*/
		
		if (empResumeSubmissionForm.getEligibilityList() != null && !empResumeSubmissionForm.getEligibilityList().isEmpty()) {
			String eligibilityTest="";
		
			Iterator<EligibilityTestTO> itr = empResumeSubmissionForm.getEligibilityList().iterator();
			while (itr.hasNext()) {
				EligibilityTestTO to = (EligibilityTestTO) itr.next();
				if (to.getChecked() != null && !to.getChecked().isEmpty())
				{
					if(!eligibilityTest.trim().isEmpty())
						eligibilityTest=eligibilityTest+","+to.getChecked();
					else eligibilityTest=to.getChecked();
					
				  if(to.getChecked().equalsIgnoreCase("OTHER")){
					  if(empResumeSubmissionForm.getOtherEligibilityTestValue()!=null && !empResumeSubmissionForm.getOtherEligibilityTestValue().trim().isEmpty()){
						  empOnlineResume.setEligibilityTestOther(empResumeSubmissionForm.getOtherEligibilityTestValue());
						}
					}
				}
			}
			empOnlineResume.setEligibilityTest(eligibilityTest);
		}
		 
		if(empResumeSubmissionForm.getNoOfPublicationsRefered()!=null && !empResumeSubmissionForm.getNoOfPublicationsRefered().isEmpty()){
			empOnlineResume.setNoOfPublicationsRefered(Integer.parseInt(empResumeSubmissionForm.getNoOfPublicationsRefered()));
		}
		
		if(empResumeSubmissionForm.getNoOfPublicationsNotRefered()!=null && !empResumeSubmissionForm.getNoOfPublicationsNotRefered().isEmpty()){
			empOnlineResume.setNoOfPublicationsNotRefered(Integer.parseInt(empResumeSubmissionForm.getNoOfPublicationsNotRefered()));
		}
		
		
		if(empResumeSubmissionForm.getBooks()!=null && !empResumeSubmissionForm.getBooks().isEmpty()){
			empOnlineResume.setBooks(Integer.parseInt(empResumeSubmissionForm.getBooks()));
		}
		
		if(empResumeSubmissionForm.getOtherInfo()!=null && !empResumeSubmissionForm.getOtherInfo().isEmpty()){
			empOnlineResume.setOtherInfo(empResumeSubmissionForm.getOtherInfo());
		}
		
		if(empResumeSubmissionForm.getQualificationId()!=null && !empResumeSubmissionForm.getQualificationId().isEmpty()){
			QualificationLevelBO qualificationLevelBO=new QualificationLevelBO();
			qualificationLevelBO.setId(Integer.parseInt(empResumeSubmissionForm.getQualificationId()));
			empOnlineResume.setEmpQualificationLevel(qualificationLevelBO);
		}
		
		if(empResumeSubmissionForm.getFile()!=null && empResumeSubmissionForm.getFile().getFileData().length>0){
			FormFile file=empResumeSubmissionForm.getFile();
			byte[] data=file.getFileData();
			if(data.length>0){
				empOnlineResume.setEmpPhoto(data);
			}
			
			if(empResumeSubmissionForm.getExpYears()!=null && !empResumeSubmissionForm.getExpYears().isEmpty()){
				empOnlineResume.setTotalExpYear(Integer.parseInt(empResumeSubmissionForm.getExpYears()));
			}
			
			if(empResumeSubmissionForm.getExpMonths()!=null && !empResumeSubmissionForm.getExpMonths().isEmpty()){
				empOnlineResume.setTotalExpMonths(Integer.parseInt(empResumeSubmissionForm.getExpMonths()));
			}
		}
		else if(session.getAttribute("photo")!=null){
			empOnlineResume.setEmpPhoto((byte[]) session.getAttribute("photo"));
		}
		// added newly to save religion and blood group by smitha
		if(empResumeSubmissionForm.getBloodGroup()!=null && !empResumeSubmissionForm.getBloodGroup().isEmpty()){
			empOnlineResume.setBloodGroup(empResumeSubmissionForm.getBloodGroup());
		}
		if(empResumeSubmissionForm.getReligionId()!=null && !empResumeSubmissionForm.getReligionId().isEmpty()){
			Religion religion=new Religion();
			religion.setId(Integer.parseInt(empResumeSubmissionForm.getReligionId()));
			empOnlineResume.setReligion(religion);
			
		}
		empOnlineResume.setStatus("Application Submitted Online");
		empOnlineResume.setStatusDate(new Date());
		if(empResumeSubmissionForm.getResearchPapersRefereed()!=null && !empResumeSubmissionForm.getResearchPapersRefereed().isEmpty()){
			empOnlineResume.setResearchPapersRefereed(Integer.parseInt(empResumeSubmissionForm.getResearchPapersRefereed()));
		}
		if(empResumeSubmissionForm.getResearchPapersNonRefereed()!=null && !empResumeSubmissionForm.getResearchPapersNonRefereed().isEmpty()){
			empOnlineResume.setResearchPapersNonRefereed(Integer.parseInt(empResumeSubmissionForm.getResearchPapersNonRefereed()));
		}
		if(empResumeSubmissionForm.getResearchPapersProceedings()!=null && !empResumeSubmissionForm.getResearchPapersProceedings().isEmpty()){
			empOnlineResume.setResearchPapersProceedings(Integer.parseInt(empResumeSubmissionForm.getResearchPapersProceedings()));
		}
		if(empResumeSubmissionForm.getNationalPublications()!=null && !empResumeSubmissionForm.getNationalPublications().isEmpty()){
			empOnlineResume.setNationalBookPublications(Integer.parseInt(empResumeSubmissionForm.getNationalPublications()));
		}
		if(empResumeSubmissionForm.getInternationalPublications()!=null && !empResumeSubmissionForm.getInternationalPublications().isEmpty()){
			empOnlineResume.setInternationalBookPublications(Integer.parseInt(empResumeSubmissionForm.getInternationalPublications()));
		}
		if(empResumeSubmissionForm.getLocalPublications()!=null && !empResumeSubmissionForm.getLocalPublications().isEmpty()){
			empOnlineResume.setLocalBookPublications(Integer.parseInt(empResumeSubmissionForm.getLocalPublications()));
		}
		if(empResumeSubmissionForm.getInternational()!=null && !empResumeSubmissionForm.getInternational().isEmpty()){
			empOnlineResume.setChaptersEditedBooksInternational(Integer.parseInt(empResumeSubmissionForm.getInternational()));
		}
		if(empResumeSubmissionForm.getNational()!=null && !empResumeSubmissionForm.getNational().isEmpty()){
			empOnlineResume.setChaptersEditedBooksNational(Integer.parseInt(empResumeSubmissionForm.getNational()));
		}
		if(empResumeSubmissionForm.getMajorProjects()!=null && !empResumeSubmissionForm.getMajorProjects().isEmpty()){
			empOnlineResume.setMajorSponseredProjects(Integer.parseInt(empResumeSubmissionForm.getMajorProjects()));
		}
		if(empResumeSubmissionForm.getMinorProjects()!=null && !empResumeSubmissionForm.getMinorProjects().isEmpty()){
			empOnlineResume.setMinorSponseredProjects(Integer.parseInt(empResumeSubmissionForm.getMinorProjects()));
		}
		if(empResumeSubmissionForm.getConsultancyPrjects1()!=null && !empResumeSubmissionForm.getConsultancyPrjects1().isEmpty()){
			empOnlineResume.setConsultancy1SponseredProjects(Integer.parseInt(empResumeSubmissionForm.getConsultancyPrjects1()));
		}
		if(empResumeSubmissionForm.getConsultancyProjects2()!=null && !empResumeSubmissionForm.getConsultancyProjects2().isEmpty()){
			empOnlineResume.setConsultancy2SponseredProjects(Integer.parseInt(empResumeSubmissionForm.getConsultancyProjects2()));
		}
		if(empResumeSubmissionForm.getPhdResearchGuidance()!=null && !empResumeSubmissionForm.getPhdResearchGuidance().isEmpty()){
			empOnlineResume.setPhdResearchGuidance(Integer.parseInt(empResumeSubmissionForm.getPhdResearchGuidance()));
		}
		if(empResumeSubmissionForm.getMphilResearchGuidance()!=null && !empResumeSubmissionForm.getMphilResearchGuidance().isEmpty()){
			empOnlineResume.setMphilResearchGuidance(Integer.parseInt(empResumeSubmissionForm.getMphilResearchGuidance()));
		}
		if(empResumeSubmissionForm.getFdp1Training()!=null && !empResumeSubmissionForm.getFdp1Training().isEmpty()){
			empOnlineResume.setTrainingAttendedFdp1Weeks(Integer.parseInt(empResumeSubmissionForm.getFdp1Training()));
		}
		if(empResumeSubmissionForm.getFdp2Training()!=null && !empResumeSubmissionForm.getFdp2Training().isEmpty()){
			empOnlineResume.setTrainingAttendedFdp2Weeks(Integer.parseInt(empResumeSubmissionForm.getFdp2Training()));
		}
		if(empResumeSubmissionForm.getRegionalConference()!=null && !empResumeSubmissionForm.getRegionalConference().isEmpty()){
			empOnlineResume.setRegionalConferencePresentaion(Integer.parseInt(empResumeSubmissionForm.getRegionalConference()));
		}
		if(empResumeSubmissionForm.getInternationalConference()!=null && !empResumeSubmissionForm.getInternationalConference().isEmpty()){
			empOnlineResume.setInternationalConferencePresentaion(Integer.parseInt(empResumeSubmissionForm.getInternationalConference()));
		}
		if(empResumeSubmissionForm.getNationalConference()!=null && !empResumeSubmissionForm.getNationalConference().isEmpty()){
			empOnlineResume.setNationalConferencePresentaion(Integer.parseInt(empResumeSubmissionForm.getNationalConference()));
		}
		if(empResumeSubmissionForm.getLocalConference()!=null && !empResumeSubmissionForm.getLocalConference().isEmpty()){
			empOnlineResume.setLocalConferencePresentaion(Integer.parseInt(empResumeSubmissionForm.getLocalConference()));
		}
		if(empResumeSubmissionForm.getFatherName()!=null && !empResumeSubmissionForm.getFatherName().isEmpty())
			empOnlineResume.setFatherName(empResumeSubmissionForm.getFatherName());
		if(empResumeSubmissionForm.getMotherName()!=null && !empResumeSubmissionForm.getMotherName().isEmpty())
			empOnlineResume.setMotherName(empResumeSubmissionForm.getMotherName());
		}
		if(empResumeSubmissionForm.getAlternativeMobile()!=null && !empResumeSubmissionForm.getAlternativeMobile().isEmpty())
			empOnlineResume.setAlternateMobile(empResumeSubmissionForm.getAlternativeMobile());
		if(empResumeSubmissionForm.getEmpSubjectId()!=null && !empResumeSubmissionForm.getEmpSubjectId().isEmpty()){
			EmployeeSubject empSubject = new EmployeeSubject();
			empSubject.setId(Integer.parseInt(empResumeSubmissionForm.getEmpSubjectId()));
			empOnlineResume.setEmpSubject(empSubject);
		}
	}catch (Exception e) {
			throw new ApplicationException(e);
		}
		empOnlineResume.setIsActive(true);
		empOnlineResume.setCreatedBy(empResumeSubmissionForm.getUserId());
		empOnlineResume.setCreatedDate(new Date());
		empOnlineResume.setModifiedBy(empResumeSubmissionForm.getUserId());
		empOnlineResume.setLastModifiedDate(new Date());
		empOnlineResume.setDateOfSubmission(new Date());
		
		return empOnlineResume;
	}
}
