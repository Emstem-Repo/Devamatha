package com.kp.cms.helpers.employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.EmpDependents;
import com.kp.cms.bo.admin.EmpImmigration;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.admin.PfGratuityNominees;
import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.employee.EmpEducationalDetails;
import com.kp.cms.bo.employee.EmpFeeConcession;
import com.kp.cms.bo.employee.EmpFinancial;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.EmpIncentives;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmpLoan;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmpPayAllowanceDetails;
import com.kp.cms.bo.employee.EmpPreviousExperience;
import com.kp.cms.bo.employee.EmpRemarks;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.EmpOnlinePreviousExperienceTO;
import com.kp.cms.to.admin.PfGratuityNomineesTO;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpEventVacationTo;
import com.kp.cms.to.employee.EmpFeeConcessionTO;
import com.kp.cms.to.employee.EmpFinancialTO;
import com.kp.cms.to.employee.EmpImagesTO;
import com.kp.cms.to.employee.EmpIncentivesTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpLoanTO;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpRemarksTO;
import com.kp.cms.to.employee.EmployeeInfoTONew;
import com.kp.cms.to.employee.PayScaleTO;
import com.kp.cms.transactions.employee.IEmployeeInfoNewTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoNewTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeInfoHelperNew{
	
	private static volatile EmployeeInfoHelperNew instance=null;
	
	/**
	 * 
	 */
	private EmployeeInfoHelperNew(){
		
	}
	
	/**
	 * @return
	 */
	public static EmployeeInfoHelperNew getInstance(){
		if(instance==null){
			instance=new EmployeeInfoHelperNew();
		}
		return instance;
	}

	/**
	 * @param employeeInfoFormNew
	 * @throws Exception 
	 */
	public Employee convertFormToBo(EmployeeInfoFormNew employeeInfoFormNew) throws Exception {
		Employee employee=new Employee();
			
		
		// Creating EmpLoan Object and setting into EMployee Object
		Set<EmpLoan> empLoan = getEmpLoanBoObjects(employeeInfoFormNew);
		employee.setEmpLoan(empLoan);
		
		Set<EmpFinancial> empFinancial = getEmpFinancialBoObjects(employeeInfoFormNew);
		employee.setEmpFinancial(empFinancial);
		
		Set<EmpFeeConcession> empFeeConcession = getEmpFeeConcessionBoObjects(employeeInfoFormNew);
		employee.setEmpFeeConcession(empFeeConcession);
		
		Set<EmpIncentives> empIncentives = getEmpIncentivesBoObjects(employeeInfoFormNew);
		employee.setEmpIncentives(empIncentives);
		
		Set<EmpRemarks> empRemarks = getEmpRemarksBoObjects(employeeInfoFormNew);
		employee.setEmpRemarks(empRemarks);

		Set<EmpImmigration> empImmigrations = getEmpImmigrationBoObjects(employeeInfoFormNew);
		employee.setEmpImmigrations(empImmigrations);
		
		Set<EmpDependents> empDependentses = getEmpDependantsBoObjects(employeeInfoFormNew);
		employee.setEmpDependentses(empDependentses);
		
		Set<PfGratuityNominees> PfGratuityNominees = getEmpPfGratuityNomineesObjects(employeeInfoFormNew);
		employee.setPfGratuityNominees(PfGratuityNominees);
		
		Set<EmpAcheivement> empAcheivements= getEmpAchievementBoObjects(employeeInfoFormNew);
		employee.setEmpAcheivements(empAcheivements);
		
		Set<EmpPayAllowanceDetails> empPayAllowance = getPayAllowanceBoObjects(employeeInfoFormNew);
		employee.setEmpPayAllowance(empPayAllowance);
		
		Set<EmpLeave> empLeave = getLeavesBoObjects(employeeInfoFormNew);
		employee.setEmpLeaves(empLeave);
		
		Set<EmpImages> empImages = getEmpImagesBoObjects(employeeInfoFormNew);
		employee.setEmpImages(empImages);
		employee.setDisplayInWebsite(true);
		
	  //  Set<EmpFinancial> empFinancial = new HashSet<EmpFinancial>();
	   /// Set<EmpImmigration> empImmigrations = new HashSet<EmpImmigration>();
		//Set<EmpIncentives> empIncentives = new HashSet<EmpIncentives>();
		//Set<EmpFeeConcession> empFeeConcession = new HashSet<EmpFeeConcession>();
		//Set<EmpRemarks> empRemarks = new HashSet<EmpRemarks>();
		Set<EmpPreviousExperience> previousSet=new HashSet<EmpPreviousExperience>();
		Set<EmpEducationalDetails> educationalDeatialSet=new HashSet<EmpEducationalDetails>();
		try{
		
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null){
			if(employeeInfoFormNew.getEmployeeInfoTONew().getExperiences()!=null){
				List<EmpPreviousOrgTo> list=employeeInfoFormNew.getEmployeeInfoTONew().getExperiences();
				if(list!=null){
					Iterator<EmpPreviousOrgTo> iterator=list.iterator();
					while(iterator.hasNext()){
						EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
						EmpPreviousExperience empPreviousExp=new EmpPreviousExperience();
						if(empPreviousOrgTo!=null){
							if((empPreviousOrgTo.getCurrentOrganisation()!=null && !empPreviousOrgTo.getCurrentOrganisation().isEmpty())
									||(empPreviousOrgTo.getIndustryExpYears()!=null && !empPreviousOrgTo.getIndustryExpYears().isEmpty())
									|| (empPreviousOrgTo.getIndustryExpMonths()!=null && !empPreviousOrgTo.getIndustryExpMonths().isEmpty())
									|| (empPreviousOrgTo.getCurrentDesignation()!=null && !empPreviousOrgTo.getCurrentDesignation().isEmpty())){
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
								/*--------------------------code added by sudhir---------------------------*/
								if(empPreviousOrgTo.getIndustryFromDate()!=null && !empPreviousOrgTo.getIndustryFromDate().isEmpty()){
									empPreviousExp.setFromDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getIndustryFromDate()));
								}
								if(empPreviousOrgTo.getIndustryToDate()!=null && !empPreviousOrgTo.getIndustryToDate().isEmpty()){
									empPreviousExp.setToDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getIndustryToDate()));
								}
								/*--------------------------------------------------------------------------*/
								empPreviousExp.setIndustryExperience(true);
								empPreviousExp.setActive(true);
								empPreviousExp.setCreatedBy(employeeInfoFormNew.getUserId());
								empPreviousExp.setCreatedDate(new Date());
								empPreviousExp.setModifiedBy(employeeInfoFormNew.getUserId());
								empPreviousExp.setModifiedDate(new Date());
								previousSet.add(empPreviousExp);
							}
						}
					}
				}
			}
			if(employeeInfoFormNew.getEmployeeInfoTONew().getTeachingExperience()!=null){
				List<EmpPreviousOrgTo> list=employeeInfoFormNew.getEmployeeInfoTONew().getTeachingExperience();
				if(list!=null){
					Iterator<EmpPreviousOrgTo> iterator=list.iterator();
					while(iterator.hasNext()){
						EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
						EmpPreviousExperience empPreviousExp=new EmpPreviousExperience();
						if(empPreviousOrgTo!=null){
							if((empPreviousOrgTo.getCurrentTeachingOrganisation()!=null && !empPreviousOrgTo.getCurrentTeachingOrganisation().isEmpty())
									||(empPreviousOrgTo.getTeachingExpYears()!=null && !empPreviousOrgTo.getTeachingExpYears().isEmpty())
									|| (empPreviousOrgTo.getTeachingExpMonths()!=null && !empPreviousOrgTo.getTeachingExpMonths().isEmpty())
									|| (empPreviousOrgTo.getCurrentTeachnigDesignation()!=null && !empPreviousOrgTo.getCurrentTeachnigDesignation().isEmpty())){
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
								/*----------------------code added by sudhir--------------------*/
								if(empPreviousOrgTo.getTeachingFromDate()!=null && !empPreviousOrgTo.getTeachingFromDate().isEmpty()){
									empPreviousExp.setFromDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getTeachingFromDate()));
								}
								if(empPreviousOrgTo.getTeachingToDate()!=null && !empPreviousOrgTo.getTeachingToDate().isEmpty()){
									empPreviousExp.setToDate(CommonUtil.ConvertStringToDate(empPreviousOrgTo.getTeachingToDate()));
								}
								
								/*---------------------------------------------------------------*/
								
								empPreviousExp.setTeachingExperience(true);
								empPreviousExp.setActive(true);
								empPreviousExp.setCreatedBy(employeeInfoFormNew.getUserId());
								empPreviousExp.setCreatedDate(new Date());
								empPreviousExp.setModifiedBy(employeeInfoFormNew.getUserId());
								empPreviousExp.setModifiedDate(new Date());
								previousSet.add(empPreviousExp);
							}
						}
					}
				}
				
			}
		}
				
	
		
		
		if(employeeInfoFormNew.getCurrentlyWorking()!=null && !employeeInfoFormNew.getCurrentlyWorking().isEmpty()
				&& employeeInfoFormNew.getCurrentlyWorking().equalsIgnoreCase("YES")){
		    employee.setCurrentlyWorking(true);
			if(employeeInfoFormNew.getDesignation()!=null && !employeeInfoFormNew.getDesignation().isEmpty()){
				employee.setDesignationName(employeeInfoFormNew.getDesignation());
			}
			
			if(employeeInfoFormNew.getOrgAddress()!=null && !employeeInfoFormNew.getOrgAddress().isEmpty()){
				employee.setOrganistionName(employeeInfoFormNew.getOrgAddress());
			}
		
		}else{
			employee.setCurrentlyWorking(false);
			employee.setDesignationName(null);
			employee.setOrganistionName(null);
		}
		employee.setPreviousExpSet(previousSet);
		
		
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
			List<EmpQualificationLevelTo> qualificationFixedTo=employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationFixedTo();
			Iterator< EmpQualificationLevelTo> iterator=qualificationFixedTo.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo qualificationFixed=iterator.next();
				EmpEducationalDetails educationalDeatails=null;
				if(qualificationFixed!=null){
					if((qualificationFixed.getInstitute()!=null && !qualificationFixed.getInstitute().isEmpty())
							|| (qualificationFixed.getCourse()!=null && !qualificationFixed.getCourse().isEmpty())
							|| (qualificationFixed.getSpecialization()!=null && !qualificationFixed.getSpecialization().isEmpty())
							|| (qualificationFixed.getGrade()!=null && !qualificationFixed.getGrade().isEmpty()))
					{
						educationalDeatails=new EmpEducationalDetails();
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
						educationalDeatails.setCreatedBy(employeeInfoFormNew.getUserId());
						educationalDeatails.setCreatedDate(new Date());
						educationalDeatails.setModifiedBy(employeeInfoFormNew.getUserId());
						educationalDeatails.setModifiedDate(new Date());
						educationalDeatialSet.add(educationalDeatails);
					}
				}
			}
		}
		
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
			Iterator<EmpQualificationLevelTo> iterator=employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos().iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo levelTo=iterator.next();
				EmpEducationalDetails educationalDetails=null;
				if(levelTo!=null){
					if((levelTo.getInstitute()!=null && !levelTo.getInstitute().isEmpty())
							|| (levelTo.getCourse()!=null && !levelTo.getCourse().isEmpty())
							|| (levelTo.getSpecialization()!=null && !levelTo.getSpecialization().isEmpty())
							|| (levelTo.getGrade()!=null && !levelTo.getGrade().isEmpty())){
						educationalDetails=new EmpEducationalDetails();
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
						educationalDetails.setCreatedBy(employeeInfoFormNew.getUserId());
						educationalDetails.setCreatedDate(new Date());
						educationalDetails.setModifiedBy(employeeInfoFormNew.getUserId());
						educationalDetails.setModifiedDate(new Date());
						
						
						educationalDeatialSet.add(educationalDetails);
					}
				}
			}
		}
		
		employee.setEducationalDetailsSet(educationalDeatialSet);
		
		
		if(employeeInfoFormNew.getDesignationPfId()!=null && !employeeInfoFormNew.getDesignationPfId().isEmpty()){
			Designation designation=new Designation();
			designation.setId(Integer.parseInt(employeeInfoFormNew.getDesignationPfId()));
			employee.setDesignation(designation);
		}
		if(employeeInfoFormNew.getAlbumDesignation()!=null && !employeeInfoFormNew.getAlbumDesignation().isEmpty()){
			Designation designation=new Designation();
			designation.setId(Integer.parseInt(employeeInfoFormNew.getAlbumDesignation()));
			employee.setAlbumDesignation(designation);
		}
		
		if(employeeInfoFormNew.getDepartmentId()!=null && !employeeInfoFormNew.getDepartmentId().isEmpty()){
			Department department=new Department();
			department.setId(Integer.parseInt(employeeInfoFormNew.getDepartmentId()));
			employee.setDepartment(department);
		}

		if (employeeInfoFormNew.getEligibilityList() != null && !employeeInfoFormNew.getEligibilityList().isEmpty()) {
			String eligibilityTest="";
			Iterator<EligibilityTestTO> itr = employeeInfoFormNew.getEligibilityList().iterator();
			while (itr.hasNext()) {
				EligibilityTestTO to = (EligibilityTestTO) itr.next();
				if (to.getChecked() != null && !to.getChecked().isEmpty())
				{
					if(!eligibilityTest.trim().isEmpty())
						eligibilityTest=eligibilityTest+","+to.getChecked();
					else eligibilityTest=to.getChecked();
					
				  if(to.getChecked().equalsIgnoreCase("OTHER")){
					  if(employeeInfoFormNew.getOtherEligibilityTestValue()!=null && !employeeInfoFormNew.getOtherEligibilityTestValue().trim().isEmpty()){
							employee.setEligibilityTestOther(employeeInfoFormNew.getOtherEligibilityTestValue());
						}
					}
				}
			}
			employee.setEligibilityTest(eligibilityTest);
		}
		
		if(employeeInfoFormNew.getIndustryFunctionalArea()!=null && !employeeInfoFormNew.getIndustryFunctionalArea().trim().isEmpty()){
			employee.setIndustryFunctionalArea(employeeInfoFormNew.getIndustryFunctionalArea());
		}
		if(employeeInfoFormNew.getName()!=null && !employeeInfoFormNew.getName().isEmpty()){
			employee.setFirstName(employeeInfoFormNew.getName().toUpperCase());
		}
		if(employeeInfoFormNew.getLicGratuityNo()!=null && !employeeInfoFormNew.getLicGratuityNo().isEmpty()){
			employee.setLicGratuityNo(employeeInfoFormNew.getLicGratuityNo());
		}
		if(employeeInfoFormNew.getLicGratuityDate()!=null && !employeeInfoFormNew.getLicGratuityDate().isEmpty()){
			employee.setLicGratuityDate(CommonUtil.ConvertStringToDate(employeeInfoFormNew.getLicGratuityDate()));
		}
		if(employeeInfoFormNew.getNomineePfNo()!=null && !employeeInfoFormNew.getNomineePfNo().isEmpty()){
			employee.setNomineePfNo(employeeInfoFormNew.getNomineePfNo());
		}
		if(employeeInfoFormNew.getNomineePfDate()!=null && !employeeInfoFormNew.getNomineePfDate().isEmpty()){
			employee.setNomineePfDate(CommonUtil.ConvertStringToDate(employeeInfoFormNew.getNomineePfDate()));
		}
		if(employeeInfoFormNew.getuId()!=null && !employeeInfoFormNew.getuId().isEmpty()){
			employee.setUid(employeeInfoFormNew.getuId());
		}
		if(employeeInfoFormNew.getCode()!=null && !employeeInfoFormNew.getCode().isEmpty()){
			employee.setCode(employeeInfoFormNew.getCode());
		}
		if(employeeInfoFormNew.getFingerPrintId()!=null && !employeeInfoFormNew.getFingerPrintId().isEmpty()){
			employee.setFingerPrintId(employeeInfoFormNew.getFingerPrintId());
		}
		if(employeeInfoFormNew.getCurrentAddressLine1()!=null && !employeeInfoFormNew.getCurrentAddressLine1().isEmpty()){
			employee.setCommunicationAddressLine1(employeeInfoFormNew.getCurrentAddressLine1());
		}
		
		if(employeeInfoFormNew.getCurrentAddressLine2()!=null && !employeeInfoFormNew.getCurrentAddressLine2().isEmpty()){
			employee.setCommunicationAddressLine2(employeeInfoFormNew.getCurrentAddressLine2());
		}
		
		if(employeeInfoFormNew.getCurrentZipCode()!=null && !employeeInfoFormNew.getCurrentZipCode().isEmpty()){
			employee.setCommunicationAddressZip(employeeInfoFormNew.getCurrentZipCode());
		}
		
		if(employeeInfoFormNew.getCurrentCountryId()!=null && !employeeInfoFormNew.getCurrentCountryId().isEmpty()){
			Country currentCountry=new Country();
			currentCountry.setId(Integer.parseInt(employeeInfoFormNew.getCurrentCountryId()));
			employee.setCountryByCommunicationAddressCountryId(currentCountry);
		}
		
		if(employeeInfoFormNew.getCurrentState()!=null && !employeeInfoFormNew.getCurrentState().isEmpty()){
			if(employeeInfoFormNew.getCurrentState().equalsIgnoreCase("other"))
			{
				if(employeeInfoFormNew.getOtherCurrentState()!=null && !employeeInfoFormNew.getOtherCurrentState().isEmpty()){
					employee.setCommunicationAddressStateOthers(employeeInfoFormNew.getOtherCurrentState());
				}
			}
			else
			{
			State currentState=new State();
			currentState.setId(Integer.parseInt(employeeInfoFormNew.getCurrentState()));
			employee.setStateByCommunicationAddressStateId(currentState);
			}
		}
		
		if(employeeInfoFormNew.getCurrentCity()!=null && !employeeInfoFormNew.getCurrentCity().isEmpty()){
			employee.setCommunicationAddressCity(employeeInfoFormNew.getCurrentCity());
		}
		
		if(employeeInfoFormNew.getSameAddress().equalsIgnoreCase("true")){
			if(employeeInfoFormNew.getCurrentAddressLine1()!=null && !employeeInfoFormNew.getCurrentAddressLine1().isEmpty()){
				employee.setPermanentAddressLine1(employeeInfoFormNew.getCurrentAddressLine1());
			}
			
			if(employeeInfoFormNew.getCurrentAddressLine2()!=null && !employeeInfoFormNew.getCurrentAddressLine2().isEmpty()){
				employee.setPermanentAddressLine2(employeeInfoFormNew.getCurrentAddressLine2());
			}
			
						
			if(employeeInfoFormNew.getCurrentZipCode()!=null && !employeeInfoFormNew.getCurrentZipCode().isEmpty()){
				employee.setPermanentAddressZip(employeeInfoFormNew.getCurrentZipCode());
			}
			
			if(employeeInfoFormNew.getCurrentCountryId()!=null && !employeeInfoFormNew.getCurrentCountryId().isEmpty()){
				Country currentCountry=new Country();
				currentCountry.setId(Integer.parseInt(employeeInfoFormNew.getCurrentCountryId()));
				employee.setCountryByPermanentAddressCountryId(currentCountry);
			}
			
			if(employeeInfoFormNew.getCurrentState()!=null && !employeeInfoFormNew.getCurrentState().isEmpty()){
				if(employeeInfoFormNew.getCurrentState().equalsIgnoreCase("other"))
				{
					if(employeeInfoFormNew.getOtherCurrentState()!=null && !employeeInfoFormNew.getOtherCurrentState().isEmpty()){
						employee.setPermanentAddressStateOthers(employeeInfoFormNew.getOtherCurrentState());
					}
				}
				else
				{
				State currentState=new State();
				currentState.setId(Integer.parseInt(employeeInfoFormNew.getCurrentState()));
				employee.setStateByPermanentAddressStateId(currentState);
				}
			}
			
			if(employeeInfoFormNew.getCurrentCity()!=null && !employeeInfoFormNew.getCurrentCity().isEmpty()){
				employee.setPermanentAddressCity(employeeInfoFormNew.getCurrentCity());
			}
			
		}else{
			if(employeeInfoFormNew.getAddressLine1()!=null && !employeeInfoFormNew.getAddressLine1().isEmpty()){
				employee.setPermanentAddressLine1(employeeInfoFormNew.getAddressLine1());
			}
			
			if(employeeInfoFormNew.getAddressLine2()!=null && !employeeInfoFormNew.getAddressLine2().isEmpty()){
				employee.setPermanentAddressLine2(employeeInfoFormNew.getAddressLine2());
			}
			
			if(employeeInfoFormNew.getPermanentZipCode()!=null && !employeeInfoFormNew.getPermanentZipCode().isEmpty()){
				employee.setPermanentAddressZip(employeeInfoFormNew.getPermanentZipCode());
			}
			
			if(employeeInfoFormNew.getCountryId()!=null && !employeeInfoFormNew.getCountryId().isEmpty()){
				Country country=new Country();
				country.setId(Integer.parseInt(employeeInfoFormNew.getCountryId()));
				employee.setCountryByPermanentAddressCountryId(country);
			}
			
			if(employeeInfoFormNew.getStateId()!=null && !employeeInfoFormNew.getStateId().isEmpty()){
				if(employeeInfoFormNew.getStateId().equalsIgnoreCase("other"))
				{
					if(employeeInfoFormNew.getOtherPermanentState()!=null && !employeeInfoFormNew.getOtherPermanentState().isEmpty()){
						employee.setPermanentAddressStateOthers(employeeInfoFormNew.getOtherPermanentState());
					}
				}
				else
				{
				State state=new State();
				state.setId(Integer.parseInt(employeeInfoFormNew.getStateId()));
				employee.setStateByPermanentAddressStateId(state);
			}
			}
			
			if(employeeInfoFormNew.getCity()!=null && !employeeInfoFormNew.getCity().isEmpty()){
				employee.setPermanentAddressCity(employeeInfoFormNew.getCity());
			}
		}
		
		
		if(employeeInfoFormNew.getNationalityId()!=null && !employeeInfoFormNew.getNationalityId().isEmpty()){
			Nationality nationality=new Nationality();
			nationality.setId(Integer.parseInt(employeeInfoFormNew.getNationalityId()));
			employee.setNationality(nationality);
		}
		
		if(employeeInfoFormNew.getGender()!=null && !employeeInfoFormNew.getGender().isEmpty()){
			employee.setGender(employeeInfoFormNew.getGender());
		}
		
		if(employeeInfoFormNew.getMaritalStatus()!=null && !employeeInfoFormNew.getMaritalStatus().isEmpty()){
			employee.setMaritalStatus(employeeInfoFormNew.getMaritalStatus());
		}
		
		if(employeeInfoFormNew.getDateOfBirth()!=null && !employeeInfoFormNew.getDateOfBirth().isEmpty()){
			employee.setDob(CommonUtil.ConvertStringToDate(employeeInfoFormNew.getDateOfBirth()));
		}
		if(employeeInfoFormNew.getDateOfJoining()!=null && !employeeInfoFormNew.getDateOfJoining().isEmpty()){
			employee.setDoj(CommonUtil.ConvertStringToDate(employeeInfoFormNew.getDateOfJoining()));
		}
		if(employeeInfoFormNew.getDateOfLeaving()!=null && !employeeInfoFormNew.getDateOfLeaving().isEmpty()){
			employee.setDateOfLeaving(CommonUtil.ConvertStringToDate(employeeInfoFormNew.getDateOfLeaving()));
		}
		if(employeeInfoFormNew.getDateOfResignation()!=null && !employeeInfoFormNew.getDateOfResignation().isEmpty()){
			employee.setDateOfResignation(CommonUtil.ConvertStringToDate(employeeInfoFormNew.getDateOfResignation()));
		}
		if(employeeInfoFormNew.getEmail()!=null && !employeeInfoFormNew.getEmail().isEmpty()){
			employee.setEmail(employeeInfoFormNew.getEmail());
		}
		if(employeeInfoFormNew.getReservationCategory()!=null && !employeeInfoFormNew.getReservationCategory().isEmpty()){
			employee.setReservationCategory(employeeInfoFormNew.getReservationCategory());
		}
		 if(employeeInfoFormNew.getHandicappedDescription()!=null && !employeeInfoFormNew.getHandicappedDescription().trim().isEmpty())
			 employee.setHandicappedDescription(employeeInfoFormNew.getHandicappedDescription());	
				
		if(employeeInfoFormNew.getHomePhone1()!=null && !employeeInfoFormNew.getHomePhone1().isEmpty()){
			employee.setCurrentAddressHomeTelephone1(employeeInfoFormNew.getHomePhone1());
		}
		
		if(employeeInfoFormNew.getHomePhone2()!=null && !employeeInfoFormNew.getHomePhone2().isEmpty()){
			employee.setCurrentAddressHomeTelephone2(employeeInfoFormNew.getHomePhone2());
		}
		
		if(employeeInfoFormNew.getHomePhone3()!=null && !employeeInfoFormNew.getHomePhone3().isEmpty()){
			employee.setCurrentAddressHomeTelephone3(employeeInfoFormNew.getHomePhone3());
		}
		if(employeeInfoFormNew.getWorkPhNo1()!=null && !employeeInfoFormNew.getWorkPhNo1().isEmpty()){
			employee.setCurrentAddressWorkTelephone1(employeeInfoFormNew.getWorkPhNo1());
		}
		
		if(employeeInfoFormNew.getWorkPhNo2()!=null && !employeeInfoFormNew.getWorkPhNo2().isEmpty()){
			employee.setCurrentAddressWorkTelephone2(employeeInfoFormNew.getWorkPhNo2());
		}
		if(employeeInfoFormNew.getWorkPhNo3()!=null && !employeeInfoFormNew.getWorkPhNo3().isEmpty()){
			employee.setCurrentAddressWorkTelephone3(employeeInfoFormNew.getWorkPhNo3());
		}
		if(employeeInfoFormNew.getMobileNo1()!=null && !employeeInfoFormNew.getMobileNo1().isEmpty()){
			employee.setCurrentAddressMobile1(employeeInfoFormNew.getMobileNo1());
		}
		if(employeeInfoFormNew.getHighQualifForAlbum()!=null && !employeeInfoFormNew.getHighQualifForAlbum().isEmpty()){
			employee.setHighQualifForAlbum(employeeInfoFormNew.getHighQualifForAlbum());
		}
		
		if(employeeInfoFormNew.getEmpSubjectAreaId()!=null && !employeeInfoFormNew.getEmpSubjectAreaId().isEmpty()){
			SubjectAreaBO subjectArea=new SubjectAreaBO();
			subjectArea.setId(Integer.parseInt(employeeInfoFormNew.getEmpSubjectAreaId()));
			employee.setEmpSubjectArea(subjectArea);
		}
				
		if(employeeInfoFormNew.getQualificationId()!=null && !employeeInfoFormNew.getQualificationId().isEmpty()){
			QualificationLevelBO qual=new QualificationLevelBO();
			qual.setId(Integer.parseInt(employeeInfoFormNew.getQualificationId()));
			employee.setEmpQualificationLevel(qual);
		}
		
			
		if(employeeInfoFormNew.getNoOfPublicationsRefered()!=null && !employeeInfoFormNew.getNoOfPublicationsRefered().isEmpty()){
			employee.setNoOfPublicationsRefered(employeeInfoFormNew.getNoOfPublicationsRefered());
		}
		else
		{
			employee.setNoOfPublicationsRefered("0");
		}
		
		if(employeeInfoFormNew.getNoOfPublicationsNotRefered()!=null && !employeeInfoFormNew.getNoOfPublicationsNotRefered().isEmpty()){
			employee.setNoOfPublicationsNotRefered(employeeInfoFormNew.getNoOfPublicationsNotRefered());
		}
		else
		{
			employee.setNoOfPublicationsNotRefered("0");
		}
				
		if(employeeInfoFormNew.getBooks()!=null && !employeeInfoFormNew.getBooks().isEmpty()){
			employee.setBooks(employeeInfoFormNew.getBooks());
		}
		else
		{
			employee.setBooks("0");
		}
		
		if(employeeInfoFormNew.getDateOfResignation()!=null && !employeeInfoFormNew.getDateOfResignation().isEmpty()){
			employee.setDateOfResignation(CommonUtil.ConvertStringToDate(employeeInfoFormNew.getDateOfResignation()));
		}
		if(employeeInfoFormNew.getDateOfLeaving()!=null && !employeeInfoFormNew.getDateOfLeaving().isEmpty()){
			employee.setDateOfLeaving(CommonUtil.ConvertStringToDate(employeeInfoFormNew.getDateOfLeaving()));
		}
		if(employeeInfoFormNew.getReasonOfLeaving()!=null && !employeeInfoFormNew.getReasonOfLeaving().isEmpty()){
			employee.setReasonOfLeaving(employeeInfoFormNew.getReasonOfLeaving());
		}
		
	
		
		if(employeeInfoFormNew.getPayScaleId()!=null && !employeeInfoFormNew.getPayScaleId().isEmpty()){
			PayScaleBO payScaleBo=new PayScaleBO();
			payScaleBo.setId(Integer.parseInt(employeeInfoFormNew.getPayScaleId()));
			employee.setPayScaleId(payScaleBo);
		}
		if(employeeInfoFormNew.getScale()!=null && !employeeInfoFormNew.getScale().isEmpty()){
			employee.setScale(employeeInfoFormNew.getScale());
		}
			
	
		if(employeeInfoFormNew.getGrossPay()!=null && !employeeInfoFormNew.getGrossPay().isEmpty()){
			employee.setGrossPay(employeeInfoFormNew.getGrossPay());
		}
		
		if(employeeInfoFormNew.getRetirementDate()!=null && !employeeInfoFormNew.getRetirementDate().isEmpty()){
			employee.setRetirementDate(CommonUtil.ConvertStringToSQLDate(employeeInfoFormNew.getRetirementDate()));
		}
		if(employeeInfoFormNew.getRejoinDate()!=null && !employeeInfoFormNew.getRejoinDate().isEmpty()){
			employee.setRejoinDate(CommonUtil.ConvertStringToDate(employeeInfoFormNew.getRejoinDate()));
		}
		if(employeeInfoFormNew.getTimeIn()!=null && !employeeInfoFormNew.getTimeIn().isEmpty() && employeeInfoFormNew.getTimeInMin()!=null && !employeeInfoFormNew.getTimeInMin().isEmpty()){
			employee.setTimeIn(employeeInfoFormNew.getTimeIn()+":"+employeeInfoFormNew.getTimeInMin());
		}
		
		if(employeeInfoFormNew.getTimeInEnds()!=null && !employeeInfoFormNew.getTimeInEnds().isEmpty()&& employeeInfoFormNew.getTimeInEndMIn()!=null && !employeeInfoFormNew.getTimeInEndMIn().isEmpty()){
			employee.setTimeInEnds(employeeInfoFormNew.getTimeInEnds()+":"+employeeInfoFormNew.getTimeInEndMIn());
		}
		
		if(employeeInfoFormNew.getTimeOut()!=null && !employeeInfoFormNew.getTimeOut().isEmpty() && employeeInfoFormNew.getTimeOutMin()!=null && !employeeInfoFormNew.getTimeOutMin().isEmpty()){
			employee.setTimeOut(employeeInfoFormNew.getTimeOut()+":"+employeeInfoFormNew.getTimeOutMin());
		}
		
		if(employeeInfoFormNew.getSaturdayTimeOut()!=null && !employeeInfoFormNew.getSaturdayTimeOut().isEmpty() && employeeInfoFormNew.getSaturdayTimeOutMin()!=null && !employeeInfoFormNew.getSaturdayTimeOutMin().isEmpty()){
			employee.setSaturdayTimeOut(employeeInfoFormNew.getSaturdayTimeOut()+":"+employeeInfoFormNew.getSaturdayTimeOutMin());
		}
		
		if(employeeInfoFormNew.getHalfDayStartTime()!=null && !employeeInfoFormNew.getHalfDayStartTime().isEmpty() && employeeInfoFormNew.getHalfDayStartTimeMin()!=null && !employeeInfoFormNew.getHalfDayStartTimeMin().isEmpty()){
			employee.setHalfDayStartTime(employeeInfoFormNew.getHalfDayStartTime()+":"+employeeInfoFormNew.getHalfDayStartTimeMin());
		}
		
		if(employeeInfoFormNew.getHalfDayEndTime()!=null && !employeeInfoFormNew.getHalfDayEndTime().isEmpty() && employeeInfoFormNew.getHalfDayEndTimeMin()!=null && !employeeInfoFormNew.getHalfDayEndTimeMin().isEmpty()){
			employee.setHalfDayEndTime(employeeInfoFormNew.getHalfDayEndTime()+":"+employeeInfoFormNew.getHalfDayEndTimeMin());
		}
	
		if(employeeInfoFormNew.getFourWheelerNo()!=null && !employeeInfoFormNew.getFourWheelerNo().isEmpty()){
			employee.setFourWheelerNo(employeeInfoFormNew.getFourWheelerNo());
		}
		if(employeeInfoFormNew.getTwoWheelerNo()!=null && !employeeInfoFormNew.getTwoWheelerNo().isEmpty()){
			employee.setTwoWheelerNo(employeeInfoFormNew.getTwoWheelerNo());
		}
		/*if(employeeInfoFormNew.getVehicleNo()!=null && !employeeInfoFormNew.getVehicleNo().isEmpty()){
			employee.setVehicleNo(employeeInfoFormNew.getVehicleNo());
		}*/
		if(employeeInfoFormNew.getPfNo()!=null && !employeeInfoFormNew.getPfNo().isEmpty()){
			employee.setPfNo(employeeInfoFormNew.getPfNo());
		}
		if(employeeInfoFormNew.getBankAccNo()!=null && !employeeInfoFormNew.getBankAccNo().isEmpty()){
			employee.setBankAccNo(employeeInfoFormNew.getBankAccNo());
		}
		if(employeeInfoFormNew.getStreamId()!=null && !employeeInfoFormNew.getStreamId().isEmpty()){
			EmployeeStreamBO empStream=new EmployeeStreamBO();
			empStream.setId(Integer.parseInt(employeeInfoFormNew.getStreamId()));
			employee.setStreamId(empStream);
		}
		if(employeeInfoFormNew.getWorkLocationId()!=null && !employeeInfoFormNew.getWorkLocationId().isEmpty()){
			EmployeeWorkLocationBO empworkLoc=new EmployeeWorkLocationBO();
			empworkLoc.setId(Integer.parseInt(employeeInfoFormNew.getWorkLocationId()));
			employee.setWorkLocationId(empworkLoc);
		}
		if(employeeInfoFormNew.getActive()!=null && !employeeInfoFormNew.getActive().isEmpty()){
			String Value= employeeInfoFormNew.getActive();
			if(Value.equals("1"))
			employee.setActive(true);
			else
			employee.setActive(false);
		}
		if(employeeInfoFormNew.getTeachingStaff()!=null && !employeeInfoFormNew.getTeachingStaff().isEmpty()){
			String Value= employeeInfoFormNew.getTeachingStaff();
			if(Value.equals("1"))
			employee.setTeachingStaff(true);
			else
			employee.setTeachingStaff(false);
		}
		if(employeeInfoFormNew.getIsPunchingExcemption()!=null && !employeeInfoFormNew.getIsPunchingExcemption().isEmpty()){
			String Value= employeeInfoFormNew.getIsPunchingExcemption();
			if(Value.equals("1"))
			employee.setIsPunchingExcemption(true);
			else
			employee.setIsPunchingExcemption(false);
		}
		if(employeeInfoFormNew.getSameAddress()!=null && !employeeInfoFormNew.getSameAddress().isEmpty()){
			String Value= employeeInfoFormNew.getSameAddress();
			if(Value.equals("true"))
			employee.setIsSameAddress(true);
			else
			employee.setIsSameAddress(false);
		}
		
		if(employeeInfoFormNew.getPanno()!=null && !employeeInfoFormNew.getPanno().isEmpty()){
			employee.setPanNo(employeeInfoFormNew.getPanno());
		}
		if(employeeInfoFormNew.getExpYears()!=null && !employeeInfoFormNew.getExpYears().isEmpty()){
			employee.setTotalExpYear(employeeInfoFormNew.getExpYears());
		}
		if(employeeInfoFormNew.getExpMonths()!=null && !employeeInfoFormNew.getExpMonths().isEmpty()){
			employee.setTotalExpMonths(employeeInfoFormNew.getExpMonths());
		}
		if(employeeInfoFormNew.getRelevantExpMonths()!=null && !employeeInfoFormNew.getRelevantExpMonths().isEmpty()){
			employee.setRelevantExpMonths(employeeInfoFormNew.getRelevantExpMonths());
		}
		if(employeeInfoFormNew.getRelevantExpYears()!=null && !employeeInfoFormNew.getRelevantExpYears().isEmpty()){
			employee.setRelevantExpYears(employeeInfoFormNew.getRelevantExpYears());
		}
		
		if(employeeInfoFormNew.getReligionId()!=null && !employeeInfoFormNew.getReligionId().isEmpty()){
			Religion empReligion=new Religion();
			empReligion.setId(Integer.parseInt(employeeInfoFormNew.getReligionId()));
			employee.setReligionId(empReligion);
		}
		if(employeeInfoFormNew.getTitleId()!=null && !employeeInfoFormNew.getTitleId().isEmpty()){
			EmpJobTitle empJobTitle=new EmpJobTitle();
			empJobTitle.setId(Integer.parseInt(employeeInfoFormNew.getTitleId()));
			employee.setTitleId(empJobTitle);
		}
		if(employeeInfoFormNew.getOtherInfo()!=null && !employeeInfoFormNew.getOtherInfo().isEmpty()){
			employee.setOtherInfo(employeeInfoFormNew.getOtherInfo());
		}
		if(employeeInfoFormNew.getuId()!=null && !employeeInfoFormNew.getuId().isEmpty()){
			employee.setUid(employeeInfoFormNew.getuId());
		}
		if(employeeInfoFormNew.getOfficialEmail()!=null && !employeeInfoFormNew.getOfficialEmail().isEmpty()){
			employee.setWorkEmail(employeeInfoFormNew.getOfficialEmail());
		}
		if(employeeInfoFormNew.getEmail()!=null && !employeeInfoFormNew.getEmail().isEmpty()){
			employee.setOtherEmail(employeeInfoFormNew.getEmail());
		}
		if(employeeInfoFormNew.getEmContactWorkTel()!=null && !employeeInfoFormNew.getEmContactWorkTel().isEmpty()){
			employee.setEmergencyWorkTelephone(employeeInfoFormNew.getEmContactWorkTel());
		}
		if (employeeInfoFormNew.getEmContactAddress() != null
				&& !employeeInfoFormNew.getEmContactAddress().isEmpty()) {
			employee.setEmContactAddress(employeeInfoFormNew
					.getEmContactAddress());
		}
		if(employeeInfoFormNew.getEmContactHomeTel()!=null && !employeeInfoFormNew.getEmContactHomeTel().isEmpty()){
			employee.setEmergencyHomeTelephone(employeeInfoFormNew.getEmContactHomeTel());
		}
		if(employeeInfoFormNew.getEmContactMobile()!=null && !employeeInfoFormNew.getEmContactMobile().isEmpty()){
			employee.setEmergencyMobile(employeeInfoFormNew.getEmContactMobile());
		}
		if(employeeInfoFormNew.getEmContactRelationship()!=null && !employeeInfoFormNew.getEmContactRelationship().isEmpty()){
			employee.setRelationship(employeeInfoFormNew.getEmContactRelationship());
		}
		if(employeeInfoFormNew.getEmContactName()!=null && !employeeInfoFormNew.getEmContactName().isEmpty()){
			employee.setEmergencyContName(employeeInfoFormNew.getEmContactName());
		}
		if(employeeInfoFormNew.getReportToId()!=null && !employeeInfoFormNew.getReportToId().isEmpty()){
			Employee emp=new Employee();
			emp.setId(Integer.parseInt(employeeInfoFormNew.getReportToId()));
			employee.setEmployeeByReportToId(emp);
		}
		if(employeeInfoFormNew.getEmptypeId()!=null && !employeeInfoFormNew.getEmptypeId().isEmpty()){
			EmpType emptype=new EmpType();
			emptype.setId(Integer.parseInt(employeeInfoFormNew.getEmptypeId()));
			employee.setEmptype(emptype);
		}
		
		if(employeeInfoFormNew.getMaritalStatus()!=null && !employeeInfoFormNew.getMaritalStatus().isEmpty()){
			employee.setMaritalStatus(employeeInfoFormNew.getMaritalStatus());
		}
		if(employeeInfoFormNew.getBloodGroup()!=null && !employeeInfoFormNew.getBloodGroup().isEmpty()){
			employee.setBloodGroup(employeeInfoFormNew.getBloodGroup());
		}
		if(employeeInfoFormNew.getSmartCardNo()!=null && !employeeInfoFormNew.getSmartCardNo().isEmpty()){
			employee.setSmartCardNo(employeeInfoFormNew.getSmartCardNo());
		}
		/* code added by sudhir */
		if(employeeInfoFormNew.getExtensionNumber()!=null && !employeeInfoFormNew.getExtensionNumber().isEmpty()){
			employee.setExtensionNumber(employeeInfoFormNew.getExtensionNumber());
		}
		// added by venkat
		if(employeeInfoFormNew.getResearchPapersRefereed()!=null && !employeeInfoFormNew.getResearchPapersRefereed().isEmpty()){
			employee.setResearchPapersRefereed(Integer.parseInt(employeeInfoFormNew.getResearchPapersRefereed()));
		}
		if(employeeInfoFormNew.getResearchPapersNonRefereed()!=null && !employeeInfoFormNew.getResearchPapersNonRefereed().isEmpty()){
			employee.setResearchPapersNonRefereed(Integer.parseInt(employeeInfoFormNew.getResearchPapersNonRefereed()));
		}
		if(employeeInfoFormNew.getResearchPapersProceedings()!=null && !employeeInfoFormNew.getResearchPapersProceedings().isEmpty()){
			employee.setResearchPapersProceedings(Integer.parseInt(employeeInfoFormNew.getResearchPapersProceedings()));
		}
		if(employeeInfoFormNew.getInternationalPublications()!=null && !employeeInfoFormNew.getInternationalPublications().isEmpty()){
			employee.setInternationalBookPublications(Integer.parseInt(employeeInfoFormNew.getInternationalPublications()));
		}
		if(employeeInfoFormNew.getNationalPublications()!=null && !employeeInfoFormNew.getNationalPublications().isEmpty()){
			employee.setNationalBookPublications(Integer.parseInt(employeeInfoFormNew.getNationalPublications()));
		}
		if(employeeInfoFormNew.getLocalPublications()!=null && !employeeInfoFormNew.getLocalPublications().isEmpty()){
			employee.setLocalBookPublications(Integer.parseInt(employeeInfoFormNew.getLocalPublications()));
		}
		if(employeeInfoFormNew.getInternational()!=null && !employeeInfoFormNew.getInternational().isEmpty()){
			employee.setChaptersEditedBooksInternational(Integer.parseInt(employeeInfoFormNew.getInternational()));
		}
		if(employeeInfoFormNew.getNational()!=null && !employeeInfoFormNew.getNational().isEmpty()){
			employee.setChaptersEditedBooksNational(Integer.parseInt(employeeInfoFormNew.getNational()));
		}
		if(employeeInfoFormNew.getMajorProjects()!=null && !employeeInfoFormNew.getMajorProjects().isEmpty()){
			employee.setMajorSponseredProjects(Integer.parseInt(employeeInfoFormNew.getMajorProjects()));
		}
		if(employeeInfoFormNew.getMinorProjects()!=null && !employeeInfoFormNew.getMinorProjects().isEmpty()){
			employee.setMinorSponseredProjects(Integer.parseInt(employeeInfoFormNew.getMinorProjects()));
		}
		if(employeeInfoFormNew.getConsultancyPrjects1()!=null && !employeeInfoFormNew.getConsultancyPrjects1().isEmpty()){
			employee.setConsultancy1SponseredProjects(Integer.parseInt(employeeInfoFormNew.getConsultancyPrjects1()));
		}
		if(employeeInfoFormNew.getConsultancyProjects2()!=null && !employeeInfoFormNew.getConsultancyProjects2().isEmpty()){
			employee.setConsultancy2SponseredProjects(Integer.parseInt(employeeInfoFormNew.getConsultancyProjects2()));
		}
		if(employeeInfoFormNew.getPhdResearchGuidance()!=null && !employeeInfoFormNew.getPhdResearchGuidance().isEmpty()){
			employee.setPhdResearchGuidance(Integer.parseInt(employeeInfoFormNew.getPhdResearchGuidance()));
		}
		if(employeeInfoFormNew.getMphilResearchGuidance()!=null && !employeeInfoFormNew.getMphilResearchGuidance().isEmpty()){
			employee.setMphilResearchGuidance(Integer.parseInt(employeeInfoFormNew.getMphilResearchGuidance()));
		}
		if(employeeInfoFormNew.getFdp1Training()!=null && !employeeInfoFormNew.getFdp1Training().isEmpty()){
			employee.setTrainingAttendedFdp1Weeks(Integer.parseInt(employeeInfoFormNew.getFdp1Training()));
		}
		if(employeeInfoFormNew.getFdp2Training()!=null && !employeeInfoFormNew.getFdp2Training().isEmpty()){
			employee.setTrainingAttendedFdp2Weeks(Integer.parseInt(employeeInfoFormNew.getFdp2Training()));
		}
		if(employeeInfoFormNew.getInternationalConference()!=null && !employeeInfoFormNew.getInternationalConference().isEmpty()){
			employee.setInternationalConferencePresentaion(Integer.parseInt(employeeInfoFormNew.getInternationalConference()));
		}
		if(employeeInfoFormNew.getNationalConference()!=null && !employeeInfoFormNew.getNationalConference().isEmpty()){
			employee.setNationalConferencePresentaion(Integer.parseInt(employeeInfoFormNew.getNationalConference()));
		}
		if(employeeInfoFormNew.getLocalConference()!=null && !employeeInfoFormNew.getLocalConference().isEmpty()){
			employee.setLocalConferencePresentaion(Integer.parseInt(employeeInfoFormNew.getLocalConference()));
		}
		if(employeeInfoFormNew.getRegionalConference()!=null && !employeeInfoFormNew.getRegionalConference().isEmpty()){
			employee.setRegionalConferencePresentaion(Integer.parseInt(employeeInfoFormNew.getRegionalConference()));
		}
		if(employeeInfoFormNew.getIsCjc()){
			if(employeeInfoFormNew.getFatherName()!=null && !employeeInfoFormNew.getFatherName().isEmpty()){
				employee.setFatherName(employeeInfoFormNew.getFatherName());
			}
			if(employeeInfoFormNew.getMotherName()!=null && !employeeInfoFormNew.getMotherName().isEmpty()){
				employee.setMotherName(employeeInfoFormNew.getMotherName());
			}
		}
		if(employeeInfoFormNew.getDeputaionToDepartmentId()!=null && !employeeInfoFormNew.getDeputaionToDepartmentId().isEmpty()){
			Department department=new Department();
			department.setId(Integer.parseInt(employeeInfoFormNew.getDeputaionToDepartmentId()));
			employee.setDeputationToDepartmentId(department);
		}
		if(employeeInfoFormNew.getAppointmentLetterDate()!=null && !employeeInfoFormNew.getAppointmentLetterDate().isEmpty()){
			employee.setAppointmentLetterDate(CommonUtil.ConvertStringToSQLDate(employeeInfoFormNew.getAppointmentLetterDate()));
		}
		if(employeeInfoFormNew.getReferenceNumber()!=null && !employeeInfoFormNew.getReferenceNumber().isEmpty()){
			employee.setReferenceNumberForAppointment(employeeInfoFormNew.getReferenceNumber());
		}
		if(employeeInfoFormNew.getReleavingOrderDate()!=null && !employeeInfoFormNew.getReleavingOrderDate().isEmpty()){
			employee.setReleavingOrderDate(CommonUtil.ConvertStringToSQLDate(employeeInfoFormNew.getReleavingOrderDate()));
		}
		if(employeeInfoFormNew.getReferenceNumberForReleaving()!=null && !employeeInfoFormNew.getReferenceNumberForReleaving().isEmpty()){
			employee.setReferenceNubmerforReleaving(employeeInfoFormNew.getReferenceNumberForReleaving());
		}
		if(employeeInfoFormNew.getAdditionalRemarks()!=null && !employeeInfoFormNew.getAdditionalRemarks().isEmpty()){
			employee.setAdditionalRemarks(employeeInfoFormNew.getAdditionalRemarks());
		}
		/*------------------------*/
		
	/*	if(employeeInfoFormNew.getEmpPhoto()!=null) {
			
			FormFile file=employeeInfoFormNew.getEmpPhoto();
			byte[] data=file.getFileData();
			if(data.length>0){
				Set<EmpImages> images=new HashSet<EmpImages>();
				EmpImages image=new EmpImages();
				image.setEmpPhoto(data);
				Employee emp = new Employee();
				emp.setId(Integer.parseInt(employeeInfoFormNew.getEmployeeId()));
				image.setEmployee(emp);
				image.setCreatedBy(employeeInfoFormNew.getUserId());
				image.setCreatedDate(new Date());
				image.setLastModifiedDate(new Date());
				image.setModifiedBy(employeeInfoFormNew.getUserId());
				images.add(image);
				employee.setEmpImages(images);
			}
		}*/
				
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		employee.setIsActive(true);
		employee.setIsSCDataDelivered(false);
		employee.setIsSCDataGenerated(false);
		employee.setCreatedBy(employeeInfoFormNew.getUserId());
		employee.setCreatedDate(new Date());
		//employee.setLastModifiedDate(new Date());
		
		
		return employee;
	}
	
	private Set<PfGratuityNominees> getEmpPfGratuityNomineesObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		Set<PfGratuityNominees> pfGratuity =new HashSet<PfGratuityNominees>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee()!=null
				&& !employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee().isEmpty()){
			Iterator<PfGratuityNomineesTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee().iterator();
			while (itr.hasNext()) {
				PfGratuityNomineesTO pfTo = (PfGratuityNomineesTO) itr.next();
				PfGratuityNominees pfBo=new PfGratuityNominees();
				if(pfTo.getId()!=null){
					pfBo.setId(Integer.parseInt(pfTo.getId()));
				}
				if(pfTo.getNameAdressNominee()!=null && !pfTo.getNameAdressNominee().isEmpty())
				pfBo.setNameAdressNominee(pfTo.getNameAdressNominee());
				if(pfTo.getMemberRelationship()!=null && !pfTo.getMemberRelationship().isEmpty())
				pfBo.setMemberRelationship(pfTo.getMemberRelationship());
				if(pfTo.getDobMember()!=null && !pfTo.getDobMember().isEmpty())
				pfBo.setDobMember(CommonUtil.ConvertStringToDate(pfTo.getDobMember()));
				if(pfTo.getShare()!=null && !pfTo.getShare().isEmpty())
				pfBo.setShare(pfTo.getShare());
				if(pfTo.getNameAdressGuardian()!=null && !pfTo.getNameAdressGuardian().isEmpty())
				pfBo.setNameAdressGuardian(pfTo.getNameAdressGuardian());
				
				pfBo.setCreatedBy(employeeInfoFormNew.getUserId());
				pfBo.setCreatedDate(new Date());
				pfBo.setModifiedBy(employeeInfoFormNew.getUserId());
				pfBo.setLastModifiedDate(new Date());
				pfBo.setIsActive(true);
				pfGratuity.add(pfBo);
		}}
		return pfGratuity;
	}

	/**
	 * @param employeeInfoFormNew
	 * @return
	 */
	private Set<EmpLoan> getEmpLoanBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		Set<EmpLoan> loans =new HashSet<EmpLoan>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan()!=null
				&& !employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan().isEmpty()){
			Iterator<EmpLoanTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan().iterator();
			while (itr.hasNext()) {
				EmpLoanTO to = (EmpLoanTO) itr.next();
				EmpLoan loan=new EmpLoan();
				if(to.getLoanAmount()!=null && !to.getLoanAmount().isEmpty() || to.getLoanDetails()!=null && !to.getLoanDetails().isEmpty())
				{
				if(to.getId()>0){
					loan.setId(to.getId());
				}
				loan.setCreatedBy(employeeInfoFormNew.getUserId());
				loan.setCreatedDate(new Date());
				loan.setModifiedBy(employeeInfoFormNew.getUserId());
				loan.setLastModifiedDate(new Date());
				loan.setIsActive(true);
				loan.setLoanAmount(to.getLoanAmount());
				loan.setLoanDate(CommonUtil.ConvertStringToDate(to.getLoanDate()));
				loan.setLoanDetails(to.getLoanDetails());
				loans.add(loan);
			}
		}
		}
		return loans;
	}
	
	private Set<EmpFinancial> getEmpFinancialBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		Set<EmpFinancial> financial =new HashSet<EmpFinancial>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial()!=null
				&& !employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial().isEmpty()){
			Iterator<EmpFinancialTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial().iterator();
			while (itr.hasNext()) {
				EmpFinancialTO to = (EmpFinancialTO) itr.next();
				EmpFinancial fin=new EmpFinancial();
				if(to.getFinancialAmount()!=null && !to.getFinancialAmount().isEmpty() || to.getFinancialDetails()!=null && !to.getFinancialDetails().isEmpty())
				{
				if(to.getId()>0){
					fin.setId(to.getId());
				}
				fin.setCreatedBy(employeeInfoFormNew.getUserId());
				fin.setCreatedDate(new Date());
				fin.setModifiedBy(employeeInfoFormNew.getUserId());
				fin.setLastModifiedDate(new Date());
				fin.setIsActive(true);
				fin.setFinancialAmount(to.getFinancialAmount());
				fin.setFinancialDate(CommonUtil.ConvertStringToDate(to.getFinancialDate()));
				fin.setFinancialDetails(to.getFinancialDetails());
				
				financial.add(fin);
			}
			}
		}
		return financial;
	}

	private Set<EmpFeeConcession> getEmpFeeConcessionBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		Set<EmpFeeConcession> feeConcession =new HashSet<EmpFeeConcession>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession()!=null
				&& !employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession().isEmpty()){
			Iterator<EmpFeeConcessionTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession().iterator();
			while (itr.hasNext()) {
				EmpFeeConcessionTO to = (EmpFeeConcessionTO) itr.next();
				EmpFeeConcession fin=new EmpFeeConcession();
				if((to.getFeeConcessionAmount()!=null && !to.getFeeConcessionAmount().isEmpty()) || (to.getFeeConcessionDetails()!=null && !to.getFeeConcessionDetails().isEmpty()))
				{
				if(to.getId()>0){
					fin.setId(to.getId());
				}
				fin.setCreatedBy(employeeInfoFormNew.getUserId());
				fin.setCreatedDate(new Date());
				fin.setModifiedBy(employeeInfoFormNew.getUserId());
				fin.setLastModifiedDate(new Date());
				fin.setIsActive(true);
				fin.setFeeConcessionAmount(to.getFeeConcessionAmount());
				fin.setFeeConcessionDate(CommonUtil.ConvertStringToDate(to.getFeeConcessionDate()));
				fin.setFeeConcessionDetails(to.getFeeConcessionDetails());
				
				feeConcession.add(fin);
			}
			}
		}
		return feeConcession;
	}

	private Set<EmpIncentives> getEmpIncentivesBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		Set<EmpIncentives> incentives =new HashSet<EmpIncentives>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives()!=null
				&& !employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives().isEmpty()){
			Iterator<EmpIncentivesTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives().iterator();
			while (itr.hasNext()) {
				EmpIncentivesTO to = (EmpIncentivesTO) itr.next();
				EmpIncentives fin=new EmpIncentives();
				if((to.getIncentivesAmount()!=null && !to.getIncentivesAmount().isEmpty())|| (to.getIncentivesDetails()!=null && !to.getIncentivesDetails().isEmpty()))
				{
				if(to.getId()>0){
					fin.setId(to.getId());
				}
				fin.setCreatedBy(employeeInfoFormNew.getUserId());
				fin.setCreatedDate(new Date());
				fin.setModifiedBy(employeeInfoFormNew.getUserId());
				fin.setLastModifiedDate(new Date());
				fin.setIsActive(true);
				fin.setIncentivesAmount(to.getIncentivesAmount());
				fin.setIncentivesDate(CommonUtil.ConvertStringToDate(to.getIncentivesDate()));
				fin.setIncentivesDetails(to.getIncentivesDetails());
				
				incentives.add(fin);
			}
		}}
		return incentives;
	}
	
	private Set<EmpRemarks> getEmpRemarksBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		Set<EmpRemarks> remarks =new HashSet<EmpRemarks>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks()!=null
				&& !employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks().isEmpty()){
			Iterator<EmpRemarksTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks().iterator();
			while (itr.hasNext()) {
				EmpRemarksTO to = (EmpRemarksTO) itr.next();
				EmpRemarks fin=new EmpRemarks();
				if((to.getRemarkDetails()!=null && !to.getRemarkDetails().isEmpty()) ||(to.getEnteredBy()!=null && !to.getEnteredBy().isEmpty()))
				{
				if(to.getId()>0){
					fin.setId(to.getId());
				}
				fin.setCreatedBy(employeeInfoFormNew.getUserId());
				fin.setCreatedDate(new Date());
				fin.setModifiedBy(employeeInfoFormNew.getUserId());
				fin.setLastModifiedDate(new Date());
				fin.setIsActive(true);
				fin.setRemarksDate(CommonUtil.ConvertStringToDate(to.getRemarkDate()));
				fin.setRemarksDetails(to.getRemarkDetails());
				fin.setRemarksEnteredBy(to.getEnteredBy());
				remarks.add(fin);
			}}
		}
		return remarks;
	}
	
	
	private Set<EmpImmigration> getEmpImmigrationBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
	Set<EmpImmigration> empImmigrations =new HashSet<EmpImmigration>();
	if(employeeInfoFormNew.getEmployeeInfoTONew()!=null){
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpImmigration()!=null){
			
				Iterator<EmpImmigrationTO> iterator=employeeInfoFormNew.getEmployeeInfoTONew().getEmpImmigration().iterator();
				while(iterator.hasNext()){
					EmpImmigrationTO empImmigrationTO=iterator.next();
					EmpImmigration empEmg=new EmpImmigration();
					
					if(empImmigrationTO!=null){
						if((empImmigrationTO.getPassportComments()!=null && !empImmigrationTO.getPassportComments().isEmpty()) 
								|| (empImmigrationTO.getPassportNo()!=null && !empImmigrationTO.getPassportNo().isEmpty()) 
								|| (empImmigrationTO.getPassportReviewStatus()!=null && !empImmigrationTO.getPassportReviewStatus().isEmpty()) 
								|| (empImmigrationTO.getVisaComments()!=null && !empImmigrationTO.getVisaComments().isEmpty()) 
								|| (empEmg.getPassportStatus()!=null && !empImmigrationTO.getPassportStatus().isEmpty()) 
							    || (empEmg.getVisaComments()!=null && !empImmigrationTO.getVisaComments().isEmpty()) 
								|| (empImmigrationTO.getVisaNo()!=null && !empImmigrationTO.getVisaNo().isEmpty()) 
							    || (empImmigrationTO.getVisaReviewStatus()!=null && !empImmigrationTO.getVisaReviewStatus().isEmpty()) 
							    || (empImmigrationTO.getVisaStatus()!=null && !empImmigrationTO.getVisaStatus().isEmpty()) )
						{
							if(empImmigrationTO.getId()!=null){
								empEmg.setId(Integer.parseInt(empImmigrationTO.getId()));
							}
							if(empImmigrationTO.getPassportComments()!=null && !empImmigrationTO.getPassportComments().isEmpty()){
								empEmg.setPassportComments(empImmigrationTO.getPassportComments());
							}
							if(empImmigrationTO.getPassportCountryId()!=null && !empImmigrationTO.getPassportCountryId().isEmpty()){
								
								Country country=new Country();
								country.setId(Integer.parseInt(empImmigrationTO.getPassportCountryId()));
								empEmg.setCountryByPassportCountryId(country);
							}
							
							if(empImmigrationTO.getPassportExpiryDate()!=null && !empImmigrationTO.getPassportExpiryDate().isEmpty()){
								empEmg.setPassportDateOfExpiry(CommonUtil.ConvertStringToDate(empImmigrationTO.getPassportExpiryDate()));
							}
							if(empImmigrationTO.getPassportIssueDate()!=null && !empImmigrationTO.getPassportIssueDate().isEmpty()){
								empEmg.setPassportIssueDate(CommonUtil.ConvertStringToDate(empImmigrationTO.getPassportIssueDate()));
							}
							if(empImmigrationTO.getPassportNo()!=null && !empImmigrationTO.getPassportNo().isEmpty()){
								empEmg.setPassportNo(empImmigrationTO.getPassportNo());
							}
							if(empImmigrationTO.getPassportReviewStatus()!=null && !empImmigrationTO.getPassportReviewStatus().isEmpty()){
								empEmg.setPassportReviewStatus(empImmigrationTO.getPassportReviewStatus());
							}
							if(empImmigrationTO.getPassportStatus()!=null && !empImmigrationTO.getPassportStatus().isEmpty()){
								empEmg.setPassportStatus(empImmigrationTO.getPassportStatus());
							}
							if(empImmigrationTO.getVisaComments()!=null && !empImmigrationTO.getVisaComments().isEmpty()){
								empEmg.setVisaComments(empImmigrationTO.getVisaComments());
							}
							if(empImmigrationTO.getVisaCountryId()!=null && !empImmigrationTO.getVisaCountryId().isEmpty()){
								
								Country country=new Country();
								country.setId(Integer.parseInt(empImmigrationTO.getVisaCountryId()));
								empEmg.setCountryByVisaCountryId(country);
							}
							
							if(empImmigrationTO.getVisaExpiryDate()!=null && !empImmigrationTO.getVisaExpiryDate().isEmpty()){
								empEmg.setVisaDateOfExpiry(CommonUtil.ConvertStringToDate(empImmigrationTO.getVisaExpiryDate()));
							}
							if(empImmigrationTO.getVisaIssueDate()!=null && !empImmigrationTO.getVisaIssueDate().isEmpty()){
								empEmg.setVisaIssueDate(CommonUtil.ConvertStringToDate(empImmigrationTO.getVisaIssueDate()));
							}
							if(empImmigrationTO.getVisaNo()!=null && !empImmigrationTO.getVisaNo().isEmpty()){
								empEmg.setVisaNo(empImmigrationTO.getVisaNo());
							}
							if(empImmigrationTO.getVisaReviewStatus()!=null && !empImmigrationTO.getVisaReviewStatus().isEmpty()){
								empEmg.setVisaReviewStatus(empImmigrationTO.getVisaReviewStatus());
							}
							if(empImmigrationTO.getVisaStatus()!=null && !empImmigrationTO.getVisaStatus().isEmpty()){
								empEmg.setVisaStatus(empImmigrationTO.getVisaStatus());
							}
							empEmg.setIsActive(true);							
							empEmg.setCreatedBy(employeeInfoFormNew.getUserId());
							empEmg.setCreatedDate(new Date());
							empEmg.setModifiedBy(employeeInfoFormNew.getUserId());
							empEmg.setLastModifiedDate(new Date());
							empImmigrations.add(empEmg);
							
						}
					}
				}
	}}
	return empImmigrations;
	}
	
	private Set<EmpDependents> getEmpDependantsBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		Set<EmpDependents> depen =new HashSet<EmpDependents>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses()!=null
				&& !employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses().isEmpty()){
			Iterator<EmpDependentsTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses().iterator();
			while (itr.hasNext()) {
				EmpDependentsTO to = (EmpDependentsTO) itr.next();
				EmpDependents dep=new EmpDependents();
				if((to.getDependantName()!=null && !to.getDependantName().isEmpty()) || (to.getDependentRelationship()!=null && !to.getDependentRelationship().isEmpty()))
				{
				if(to.getId()!=null){
					dep.setId(Integer.parseInt(to.getId()));
				}
				dep.setCreatedBy(employeeInfoFormNew.getUserId());
				dep.setCreatedDate(new Date());
				dep.setModifiedBy(employeeInfoFormNew.getUserId());
				dep.setLastModifiedDate(new Date());
				dep.setIsActive(true);
				dep.setDependantDOB(CommonUtil.ConvertStringToDate(to.getDependantDOB()));
				dep.setDependentName(to.getDependantName());
				dep.setDependentRelationship(to.getDependentRelationship());
				depen.add(dep);
			}
		}}
		
		return depen;
	}
	private Set<EmpAcheivement> getEmpAchievementBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		Set<EmpAcheivement> depen =new HashSet<EmpAcheivement>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements()!=null
				&& !employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements().isEmpty()){
			Iterator<EmpAcheivementTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements().iterator();
			while (itr.hasNext()) {
				EmpAcheivementTO to = (EmpAcheivementTO) itr.next();
				EmpAcheivement dep=new EmpAcheivement();
				if((to.getAcheivementName()!=null && !to.getAcheivementName().isEmpty()) || (to.getDetails()!=null && !to.getDetails().isEmpty()))
				{
				if(to.getId()>0){
					dep.setId(to.getId());
				}
				dep.setCreatedBy(employeeInfoFormNew.getUserId());
				dep.setCreatedDate(new Date());
				dep.setModifiedBy(employeeInfoFormNew.getUserId());
				dep.setLastModifiedDate(new Date());
				dep.setIsActive(true);
				dep.setAcheivementName(to.getAcheivementName());
				dep.setDetails(to.getDetails());
				depen.add(dep);
			}
		}}
		
		return depen;
	}
	
	private Set<EmpPayAllowanceDetails> getPayAllowanceBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		Set<EmpPayAllowanceDetails> empPayScalteDetailsSet =new HashSet<EmpPayAllowanceDetails>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getPayscaleFixedTo()!=null
				&& !employeeInfoFormNew.getEmployeeInfoTONew().getPayscaleFixedTo().isEmpty()){
			Iterator<EmpAllowanceTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getPayscaleFixedTo().iterator();
			while (itr.hasNext()) {
				EmpAllowanceTO to = (EmpAllowanceTO) itr.next();
				EmpPayAllowanceDetails payScaleDetails=new EmpPayAllowanceDetails();
				if(to.getId()>0){
					EmpAllowance empAllowance=new EmpAllowance();
					empAllowance.setId(to.getId());
					payScaleDetails.setEmpAllowance(empAllowance);
					}
				payScaleDetails.setAllowanceValue(to.getAllowanceName());
				payScaleDetails.setCreatedBy(employeeInfoFormNew.getUserId());
				payScaleDetails.setCreatedDate(new Date());
				payScaleDetails.setModifiedBy(employeeInfoFormNew.getUserId());
				payScaleDetails.setModifiedDate(new Date());
				payScaleDetails.setIsActive(true);
				empPayScalteDetailsSet.add(payScaleDetails);
			}
		}
		
		return empPayScalteDetailsSet;
	}
	
	private Set<EmpLeave> getLeavesBoObjects(EmployeeInfoFormNew employeeInfoFormNew) {
		IEmployeeInfoNewTransaction transaction = EmployeeInfoNewTransactionImpl.getInstance();
		//String month=null;
		int month=0;
		//SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
		//String year=simpleDateformat.format(date);
		int year=Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth=EmployeeInfoEditHelper.currentMonth();
		int year1=0;
		try {
			 month=transaction.getInitializationMonth(Integer.parseInt(employeeInfoFormNew.getEmptypeId()));
			// month=transaction.getLeaveInitMonthEmpTypeId(employeeInfoFormNew.getEmptypeId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		Set<EmpLeave> depen =new HashSet<EmpLeave>();
		if(employeeInfoFormNew.getEmployeeInfoTONew()!=null && employeeInfoFormNew.getEmployeeInfoTONew().getEmpLeaveToList()!=null && !employeeInfoFormNew.getEmployeeInfoTONew().getEmpLeaveToList().isEmpty()){
			Iterator<EmpLeaveAllotTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getEmpLeaveToList().iterator();
			while (itr.hasNext()) {
				EmpLeaveAllotTO to = (EmpLeaveAllotTO) itr.next();
				EmpLeave dep=new EmpLeave();
				/*if(to.getId()>0){
					
					EmpLeaveType leavetype=new EmpLeaveType();
					leavetype.setId(to.getEmpLeaveType().getId());
					dep.setEmpLeaveType(leavetype);
				}*/
				dep.setEmpLeaveType(to.getEmpLeaveType());			
				if(to.getAllottedLeave()!=null && !to.getAllottedLeave().isEmpty())
				{
				dep.setLeavesAllocated(Double.parseDouble(to.getAllottedLeave()));
				}else
				{
					dep.setLeavesAllocated(0.0);
			}
				dep.setLeavesRemaining((0.0));
				if(to.getSanctionedLeave()!=null && !to.getSanctionedLeave().isEmpty())
				{
				dep.setLeavesSanctioned(Double.parseDouble(to.getSanctionedLeave()));
				}
				else
				{
					dep.setLeavesSanctioned(0.0);	
				}
				if(to.getRemainingLeave()!=null && !to.getRemainingLeave().isEmpty())
				{
				dep.setLeavesRemaining(Double.parseDouble(to.getRemainingLeave()));
				}else
				{
					dep.setLeavesRemaining(0.0);
				}
				if(month==6 && currentMonth < month ){
				      year1=year-1;
				      dep.setYear(year1);
			     }
				else
				{
					 year1=year;
				      dep.setYear(year1);
				}
				String monthString = new DateFormatSymbols().getMonths()[month-1];
				dep.setMonth(monthString);
				dep.setCreatedBy(employeeInfoFormNew.getUserId());
				dep.setCreatedDate(new Date());
				dep.setModifiedBy(employeeInfoFormNew.getUserId());
				dep.setLastModifiedDate(new Date());
				dep.setIsActive(true);
				depen.add(dep);
			}
		}
		
		return depen;
	}
	
	public static int currentMonth() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		return month + 1;
	}
	private Set<EmpImages> getEmpImagesBoObjects(EmployeeInfoFormNew employeeInfoEditForm) throws Exception {
		Set<EmpImages> images = new HashSet<EmpImages>();
		
				EmpImages img = new EmpImages();
				if (employeeInfoEditForm.getEmpPhoto() != null || employeeInfoEditForm.getPhotoBytes() != null)
					{
					//emp.setId(Integer.parseInt(employeeInfoEditForm.getEmployeeId()));
					//img.setEmployee(emp);
					if(employeeInfoEditForm.getEmpImageId()!=null && !employeeInfoEditForm.getEmpImageId().isEmpty() && Integer.parseInt(employeeInfoEditForm.getEmpImageId())>0)
					{
					img.setId(Integer.parseInt(employeeInfoEditForm.getEmpImageId()));
					}
					img.setCreatedBy(employeeInfoEditForm.getUserId());
					img.setCreatedDate(new Date());
					img.setModifiedBy(employeeInfoEditForm.getUserId());
					img.setLastModifiedDate(new Date());
					if (employeeInfoEditForm.getEmpPhoto() == null	|| employeeInfoEditForm.getPhotoBytes() != null) {
						
						byte[] data = employeeInfoEditForm.getPhotoBytes();
						if (data.length > 0) {
							img.setEmpPhoto(data);
						}
					}
					if (employeeInfoEditForm.getEmpPhoto() != null && employeeInfoEditForm.getEmpPhoto().getFileSize()>0) {
						FormFile file = employeeInfoEditForm.getEmpPhoto();
						byte[] data = file.getFileData();
						if (data.length > 0) {
							img.setEmpPhoto(data);
						}
					}
					
					images.add(img);
				}
			
		return images;
	}
	
	
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

	public void convertBoToForm(EmpOnlineResume empApplicantDetails,EmployeeInfoFormNew objform) {
		if(empApplicantDetails!=null){
			if(StringUtils.isNotEmpty(empApplicantDetails.getAddressLine1()) && empApplicantDetails.getAddressLine1()!=null ){
				  objform.setAddressLine1(empApplicantDetails.getAddressLine1());
			}
			if(StringUtils.isNotEmpty(String.valueOf(empApplicantDetails.getIsSameAddress()))){
				String Value= String.valueOf(empApplicantDetails.getIsSameAddress());
				if(Value.equals("true"))
					objform.setSameAddress("true");
				else
					objform.setSameAddress("false");
				 
			}
			if (empApplicantDetails.getEligibilityTest() != null
					&& !empApplicantDetails.getEligibilityTest().isEmpty()) {
				 String input = empApplicantDetails.getEligibilityTest();
				
				 List<String> eligibilityList = new ArrayList<String>();
				  String[] splittArray = null;
				    if (input != null || !input.equalsIgnoreCase("")){
				         splittArray = input.split(",");
				         System.out.println(input + " " + splittArray);
				    }
				    for (int i = 0; i < splittArray.length; i++) {
				    	eligibilityList.add(splittArray[i]);
				    }
				    List<EligibilityTestTO> list = new ArrayList<EligibilityTestTO>();
				    EligibilityTestTO  to1 = new EligibilityTestTO();
				    to1.setEligibilityTest("None");
				    if(eligibilityList.contains("None")){
				    	to1.setTempChecked("on");
				    }
				    list.add(to1);
				    EligibilityTestTO  to2 = new EligibilityTestTO();
				    to2.setEligibilityTest("NET");
				    if(eligibilityList.contains("NET")){
				    	to2.setTempChecked("on");
				    }
				    list.add(to2);
				    EligibilityTestTO  to3 = new EligibilityTestTO();
				    to3.setEligibilityTest("SLET");
				    if(eligibilityList.contains("SLET")){
				    	to3.setTempChecked("on");
				    }
				    list.add(to3);
				    EligibilityTestTO  to4 = new EligibilityTestTO();
				    to4.setEligibilityTest("SET");
				    if(eligibilityList.contains("SET")){
				    	to4.setTempChecked("on");
				    }
				    list.add(to4);
				    EligibilityTestTO  to5 = new EligibilityTestTO();
				    to5.setEligibilityTest("OTHER");
				    if(eligibilityList.contains("OTHER")){
				    	to5.setTempChecked("on");
				    }
				    list.add(to5);
				    objform.setEligibilityList(list);
			}
		//added by smitha for display of eligibilityTestOther
			if(empApplicantDetails.getEligibilityTestOther()!=null && !empApplicantDetails.getEligibilityTestOther().trim().isEmpty()){
				objform.setEligibilityTestOther("OTHER");
				objform.setOtherEligibilityTestValue(empApplicantDetails.getEligibilityTestOther());
			}
			if(empApplicantDetails.getIndustryFunctionalArea()!=null && !empApplicantDetails.getIndustryFunctionalArea().trim().isEmpty()){
				objform.setIndustryFunctionalArea(empApplicantDetails.getIndustryFunctionalArea());
			}
			
			if(StringUtils.isNotEmpty(empApplicantDetails.getAddressLine1()) && empApplicantDetails.getAddressLine1()!=null ){
				  objform.setAddressLine1(empApplicantDetails.getAddressLine1());
			}
			
			if(StringUtils.isNotEmpty(empApplicantDetails.getAddressLine2()) && empApplicantDetails.getAddressLine2()!=null){
				  objform.setAddressLine2(empApplicantDetails.getAddressLine2());
			}
			if(StringUtils.isNotEmpty(empApplicantDetails.getCity()) && empApplicantDetails.getCity()!=null){
				  objform.setCity(empApplicantDetails.getCity());
			}
			if(StringUtils.isNotEmpty(empApplicantDetails.getPermanentZipCode()) && empApplicantDetails.getPermanentZipCode()!=null){
				  objform.setPermanentZipCode(empApplicantDetails.getPermanentZipCode());
			}
			if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentAddressLine1()) && empApplicantDetails.getCurrentAddressLine1()!=null){
				  objform.setCurrentAddressLine1(empApplicantDetails.getCurrentAddressLine1());
			}
			if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentAddressLine2()) && empApplicantDetails.getCurrentAddressLine2()!=null){
				  objform.setCurrentAddressLine2(empApplicantDetails.getCurrentAddressLine2());
			}
			if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentCity()) && empApplicantDetails.getCurrentCity()!=null){
				  objform.setCurrentCity(empApplicantDetails.getCurrentCity());
			}
			if(empApplicantDetails.getPhNo1()!=null && !empApplicantDetails.getPhNo1().isEmpty() ){
				objform.setHomePhone1(empApplicantDetails.getPhNo1());
			}
			
			if(empApplicantDetails.getPhNo2()!=null && !empApplicantDetails.getPhNo2().isEmpty()){
				objform.setHomePhone2(empApplicantDetails.getPhNo2());
			}
			
			if(empApplicantDetails.getPhNo3()!=null && !empApplicantDetails.getPhNo3().isEmpty()){
				objform.setHomePhone3(empApplicantDetails.getPhNo3());
			}
			if(empApplicantDetails.getWorkPhNo1()!=null && !empApplicantDetails.getWorkPhNo1().isEmpty()){
				objform.setWorkPhNo1(empApplicantDetails.getWorkPhNo1());
			}
			
			if(empApplicantDetails.getWorkPhNo2()!=null && !empApplicantDetails.getWorkPhNo2().isEmpty()){
				objform.setWorkPhNo2(empApplicantDetails.getWorkPhNo2());
			}
			
			if(empApplicantDetails.getWorkPhNo3()!=null && !empApplicantDetails.getWorkPhNo3().isEmpty()){
				objform.setWorkPhNo3(empApplicantDetails.getWorkPhNo3());
			}

			if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentDesignation()) && empApplicantDetails.getCurrentDesignation()!=null){
				  objform.setDesignation(empApplicantDetails.getCurrentDesignation());
			}
			if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentOrganization()) && empApplicantDetails.getCurrentOrganization()!=null){
				  objform.setOrgAddress(empApplicantDetails.getCurrentOrganization());
			}
			if(StringUtils.isNotEmpty(empApplicantDetails.getMobileNo1()) && empApplicantDetails.getMobileNo1()!=null){
				  objform.setMobileNo1(empApplicantDetails.getMobileNo1());
			}
			
			if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentStateOther()) && empApplicantDetails.getCurrentStateOther()!=null){
				  objform.setOtherCurrentState(empApplicantDetails.getCurrentStateOther());
		    }
			if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentZipCode()) && empApplicantDetails.getCurrentZipCode()!=null){
				  objform.setCurrentZipCode(empApplicantDetails.getCurrentZipCode());
			}
			/*if(StringUtils.isNotEmpty(empApplicantDetails.getCurrentDesignation())){
				  objform.setDesignation(empApplicantDetails.getCurrentDesignation());
			}*/
			/*if(empApplicantDetails.getDesignation()!=null && empApplicantDetails.getDesignation().getId()>0){
				  objform.setDesignation(empApplicantDetails.getDesignation().getName());
			}*/
			if(empApplicantDetails.getState()!=null && empApplicantDetails.getState().getId()>0){
				  objform.setStateId(String.valueOf(empApplicantDetails.getState().getId()));
			}
			if(empApplicantDetails.getCurrentState()!=null && empApplicantDetails.getCurrentState().getId()>0){
				  objform.setCurrentState(String.valueOf(empApplicantDetails.getCurrentState().getId()));
			}
			if(StringUtils.isNotEmpty(empApplicantDetails.getEmail()) && empApplicantDetails.getEmail()!=null){
				  objform.setEmail(empApplicantDetails.getEmail());
			}
			if(empApplicantDetails.getEmpQualificationLevel()!=null && empApplicantDetails.getEmpQualificationLevel().getId()>0){
				  objform.setQualificationId(String.valueOf(empApplicantDetails.getEmpQualificationLevel().getId()));
			}
			if(empApplicantDetails.getEmpSubjectArea()!=null && empApplicantDetails.getEmpSubjectArea().getId()>0){
				  objform.setEmpSubjectAreaId(String.valueOf(empApplicantDetails.getEmpSubjectArea().getId()));
			}
			if(empApplicantDetails.getGender()!=null){
				  objform.setGender(empApplicantDetails.getGender());
			}
			if(empApplicantDetails.getBooks()!=null && empApplicantDetails.getBooks()>0){
				  objform.setBooks(String.valueOf(empApplicantDetails.getBooks()));
			}
			if(empApplicantDetails.getNoOfPublicationsNotRefered()!=null && empApplicantDetails.getNoOfPublicationsNotRefered()>0){
				  objform.setNoOfPublicationsNotRefered(String.valueOf(empApplicantDetails.getNoOfPublicationsNotRefered()));
			}
			if(empApplicantDetails.getNoOfPublicationsRefered()!=null && empApplicantDetails.getNoOfPublicationsRefered()>0){
				  objform.setNoOfPublicationsRefered(String.valueOf(empApplicantDetails.getNoOfPublicationsRefered()));
			}
			if(StringUtils.isNotEmpty(empApplicantDetails.getName()) && empApplicantDetails.getName()!=null){
				  objform.setName(empApplicantDetails.getName().toUpperCase());
			}
			if(empApplicantDetails.getNationality()!=null && empApplicantDetails.getNationality().getId()>0){
				  objform.setNationalityId(String.valueOf(empApplicantDetails.getNationality().getId()));
			}
			
			if(StringUtils.isNotEmpty(empApplicantDetails.getMaritalStatus()) && empApplicantDetails.getMaritalStatus()!=null){
				  objform.setMaritalStatus(String.valueOf(empApplicantDetails.getMaritalStatus()));
			}
			if(empApplicantDetails.getDateOfBirth()!=null){
				objform.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(empApplicantDetails.getDateOfBirth().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				
			}
			if (empApplicantDetails.getReservationCategory() != null
					&& !empApplicantDetails.getReservationCategory().isEmpty()) {
				if ("GM".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("SC".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("ST".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("OBC".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("Minority".equalsIgnoreCase(empApplicantDetails
						.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
				}
				if ("Person With Disability"
						.equalsIgnoreCase(empApplicantDetails
								.getReservationCategory())) {
					objform.setReservationCategory(empApplicantDetails
							.getReservationCategory());
					 if(empApplicantDetails.getHandicappedDescription()!=null && !empApplicantDetails.getHandicappedDescription().trim().isEmpty())
						 objform.setHandicappedDescription(empApplicantDetails.getHandicappedDescription());	
					
				}
			}
			if(empApplicantDetails.getTotalExpMonths()!=null && empApplicantDetails.getTotalExpMonths()>0){
				  objform.setExpMonths(String.valueOf(empApplicantDetails.getTotalExpMonths()));
			}
			if(empApplicantDetails.getTotalExpYear()!=null && empApplicantDetails.getTotalExpYear()>0){
				  objform.setExpYears(String.valueOf(empApplicantDetails.getTotalExpYear()));
			}
			//added for blood group and religion by smitha
			if(empApplicantDetails.getBloodGroup()!=null && !empApplicantDetails.getBloodGroup().isEmpty()){
				objform.setBloodGroup(empApplicantDetails.getBloodGroup());
			}
			if(empApplicantDetails.getReligion()!=null && empApplicantDetails.getReligion().getId()>0){
				objform.setReligionId(String.valueOf(empApplicantDetails.getReligion().getId()));
			}
			if(empApplicantDetails.getEducationalDetailsSet()!=null){
				List<EmpQualificationLevelTo> fixed=null;
				if(objform.getEmployeeInfoTONew()!=null){
					if(objform.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
						fixed=objform.getEmployeeInfoTONew().getEmpQualificationFixedTo();
					}
					List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
					Set<EmpOnlineEducationalDetails> empOnlineEducationalDetailsSet=empApplicantDetails.getEducationalDetailsSet();
					Iterator<EmpOnlineEducationalDetails> iterator=empOnlineEducationalDetailsSet.iterator();
					while(iterator.hasNext()){
						EmpOnlineEducationalDetails empOnlineEducationalDetails=iterator.next();
						if(empOnlineEducationalDetails!=null){
							boolean flag=false;
							if(empOnlineEducationalDetails.getEmpQualificationLevel()!=null 
									&& empOnlineEducationalDetails.getEmpQualificationLevel().isFixedDisplay()!=null){
								flag=empOnlineEducationalDetails.getEmpQualificationLevel().isFixedDisplay();
								if(flag && fixed!=null){
									Iterator<EmpQualificationLevelTo> iterator2=fixed.iterator();
									while(iterator2.hasNext()){
										EmpQualificationLevelTo empQualificationLevelTo=iterator2.next();
										if(empQualificationLevelTo!=null && StringUtils.isNotEmpty(empQualificationLevelTo.getEducationId())){
											if(empOnlineEducationalDetails.getEmpQualificationLevel().getId()>0)
												if(empQualificationLevelTo.getEducationId().equalsIgnoreCase(String.valueOf(empOnlineEducationalDetails.getEmpQualificationLevel().getId()))){
													if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getCourse())){
														empQualificationLevelTo.setCourse(empOnlineEducationalDetails.getCourse());
													}
													
													if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getSpecialization())){
														empQualificationLevelTo.setSpecialization(empOnlineEducationalDetails.getSpecialization());
													}
													
													if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getGrade())){
														empQualificationLevelTo.setGrade(empOnlineEducationalDetails.getGrade());
													}
													
													if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getInstitute())){
														empQualificationLevelTo.setInstitute(empOnlineEducationalDetails.getInstitute());
													}
													
													if(empOnlineEducationalDetails.getYearOfCompletion()>0){
														empQualificationLevelTo.setYearOfComp(String.valueOf(empOnlineEducationalDetails.getYearOfCompletion()));
													}
												}
										}
									}
								}else{
									EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
										if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getCourse())){
											empQualificationLevelTo.setCourse(empOnlineEducationalDetails.getCourse());
										}
										
										if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getSpecialization())){
											empQualificationLevelTo.setSpecialization(empOnlineEducationalDetails.getSpecialization());
										}
										
										if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getGrade())){
											empQualificationLevelTo.setGrade(empOnlineEducationalDetails.getGrade());
										}
										
										if(StringUtils.isNotEmpty(empOnlineEducationalDetails.getInstitute())){
											empQualificationLevelTo.setInstitute(empOnlineEducationalDetails.getInstitute());
										}
										
										if(empOnlineEducationalDetails.getYearOfCompletion()>0){
											empQualificationLevelTo.setYear(String.valueOf(empOnlineEducationalDetails.getYearOfCompletion()));
										}
										if(empOnlineEducationalDetails.getEmpQualificationLevel().getId()>0){
											empQualificationLevelTo.setEducationId(String.valueOf(empOnlineEducationalDetails.getEmpQualificationLevel().getId()));
										}
									level.add(empQualificationLevelTo);
								}
									
								}
							}
					}
					objform.getEmployeeInfoTONew().setEmpQualificationLevelTos(level);
				}
			}
			if(empApplicantDetails.getResearchPapersRefereed()!=null){
				objform.setResearchPapersRefereed(empApplicantDetails.getResearchPapersRefereed().toString());
			}
			if(empApplicantDetails.getResearchPapersNonRefereed()!=null){
				objform.setResearchPapersNonRefereed(empApplicantDetails.getResearchPapersNonRefereed().toString());
			}
			if(empApplicantDetails.getResearchPapersProceedings()!=null){
				objform.setResearchPapersProceedings(empApplicantDetails.getResearchPapersProceedings().toString());
			}//
			if(empApplicantDetails.getInternationalBookPublications()!=null){
				objform.setInternationalPublications(empApplicantDetails.getInternationalBookPublications().toString());
			}
			if(empApplicantDetails.getNationalBookPublications()!=null){
				objform.setNationalPublications(empApplicantDetails.getNationalBookPublications().toString());
			}
			if(empApplicantDetails.getLocalBookPublications()!=null){
				objform.setLocalPublications(empApplicantDetails.getLocalBookPublications().toString());
			}
			if(empApplicantDetails.getChaptersEditedBooksInternational()!=null){
				objform.setInternational(empApplicantDetails.getChaptersEditedBooksInternational().toString());
			}
			if(empApplicantDetails.getChaptersEditedBooksNational()!=null){
				objform.setNational(empApplicantDetails.getChaptersEditedBooksNational().toString());
			}
			if(empApplicantDetails.getMajorSponseredProjects()!=null){
				objform.setMajorProjects(empApplicantDetails.getMajorSponseredProjects().toString());
			}
			if(empApplicantDetails.getMinorSponseredProjects()!=null){
				objform.setMinorProjects(empApplicantDetails.getMinorSponseredProjects().toString());
			}
			if(empApplicantDetails.getConsultancy1SponseredProjects()!=null){
				objform.setConsultancyPrjects1(empApplicantDetails.getConsultancy1SponseredProjects().toString());
			}
			if(empApplicantDetails.getConsultancy2SponseredProjects()!=null){
				objform.setConsultancyProjects2(empApplicantDetails.getConsultancy2SponseredProjects().toString());
			}
			if(empApplicantDetails.getPhdResearchGuidance()!=null){
				objform.setPhdResearchGuidance(empApplicantDetails.getPhdResearchGuidance().toString());
			}
			if(empApplicantDetails.getMphilResearchGuidance()!=null){
				objform.setMphilResearchGuidance(empApplicantDetails.getMphilResearchGuidance().toString());
			}
			if(empApplicantDetails.getTrainingAttendedFdp1Weeks()!=null){
				objform.setFdp1Training(empApplicantDetails.getTrainingAttendedFdp1Weeks().toString());
			}
			if(empApplicantDetails.getTrainingAttendedFdp2Weeks()!=null){
				objform.setFdp2Training(empApplicantDetails.getTrainingAttendedFdp2Weeks().toString());
			}
			if(empApplicantDetails.getInternationalConferencePresentaion()!=null){
				objform.setInternationalConference(empApplicantDetails.getInternationalConferencePresentaion().toString());
			}
			if(empApplicantDetails.getNationalConferencePresentaion()!=null){
				objform.setNationalConference(empApplicantDetails.getNationalConferencePresentaion().toString());
			}
			if(empApplicantDetails.getRegionalConferencePresentaion()!=null){
				objform.setRegionalConference(empApplicantDetails.getRegionalConferencePresentaion().toString());
			}
			if(empApplicantDetails.getLocalConferencePresentaion()!=null){
				objform.setLocalConference(empApplicantDetails.getLocalConferencePresentaion().toString());
			}
			if(empApplicantDetails.getFatherName()!=null){
				objform.setFatherName(empApplicantDetails.getFatherName());
			}
			if(empApplicantDetails.getMotherName()!=null){
				objform.setMotherName(empApplicantDetails.getMotherName());
			}
		}
		//Code for photo display
		if (empApplicantDetails.getEmpPhoto() != null && empApplicantDetails.getEmpPhoto().length > 0) {
				byte[] myFileBytes = empApplicantDetails.getEmpPhoto();
				objform.setPhotoBytes(myFileBytes);
			}
			
		if(empApplicantDetails.getPreviousExpSet()!=null){
			int teachingFlag=0;
			int industryFlag=0;
			Set<EmpOnlinePreviousExperience> empOnlinePreviousExperiencesSet=empApplicantDetails.getPreviousExpSet();
			if(empOnlinePreviousExperiencesSet != null && !empOnlinePreviousExperiencesSet.isEmpty())
			{
			Iterator<EmpOnlinePreviousExperience> iterator=empOnlinePreviousExperiencesSet.iterator();
			List<EmpPreviousOrgTo> industryExp=new ArrayList<EmpPreviousOrgTo>();
			List<EmpPreviousOrgTo> teachingExp=new ArrayList<EmpPreviousOrgTo>();
			while(iterator.hasNext()){
				EmpOnlinePreviousExperience empOnlinePreviousExperiences=iterator.next();
				if(empOnlinePreviousExperiences!=null){
				EmpPreviousOrgTo empOnliPreviousExperienceTO=new EmpPreviousOrgTo();
				if(empOnlinePreviousExperiences.isIndustryExperience())
				{
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
					/* code added by sudhir*/
					if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
						empOnliPreviousExperienceTO.setIndustryFromDate(formatDate(empOnlinePreviousExperiences.getFromDate()));
					}
					if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
						empOnliPreviousExperienceTO.setIndustryToDate(formatDate(empOnlinePreviousExperiences.getToDate()));
					}
					
					/*-------------------*/
					industryFlag=1;
					industryExp.add(empOnliPreviousExperienceTO);
				}else if(empOnlinePreviousExperiences.isTeachingExperience())
					{
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
						/* code added by sudhir*/
						if(empOnlinePreviousExperiences.getFromDate()!=null && !empOnlinePreviousExperiences.getFromDate().toString().isEmpty()){
							empOnliPreviousExperienceTO.setTeachingFromDate(formatDate(empOnlinePreviousExperiences.getFromDate()));
						}
						if(empOnlinePreviousExperiences.getToDate()!=null && !empOnlinePreviousExperiences.getToDate().toString().isEmpty()){
							empOnliPreviousExperienceTO.setTeachingToDate(formatDate(empOnlinePreviousExperiences.getToDate()));
						}
						/*-------------------*/
						teachingFlag=1;
						teachingExp.add(empOnliPreviousExperienceTO);
					}
			}
		}
			if(objform.getEmployeeInfoTONew()!=null){
				if(industryExp!=null)
				objform.getEmployeeInfoTONew().setExperiences(industryExp);
				if(teachingExp!=null)
					objform.getEmployeeInfoTONew().setTeachingExperience(teachingExp);
		}
		
		
	}
			else {
				List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setId(1);
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				/* code added by sudhir*/
				empPreviousOrgTo.setIndustryFromDate("");
				empPreviousOrgTo.setIndustryToDate("");
				/*-------------------*/
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				
				List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
				empPreviousOrgTo.setId(1);
				empPreviousOrgTo.setTeachingExpYears("");
				empPreviousOrgTo.setTeachingExpMonths("");
				empPreviousOrgTo.setCurrentTeachingOrganisation("");
				empPreviousOrgTo.setCurrentTeachnigDesignation("");
				/* code added by sudhir*/
				empPreviousOrgTo.setTeachingFromDate("");
				empPreviousOrgTo.setTeachingToDate("");
				/*-------------------*/
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo);
				objform.getEmployeeInfoTONew().setExperiences(list);
				objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
			}
			if(teachingFlag==1 && industryFlag==0)
			{
				List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setId(1);
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				/* code added by sudhir*/
				empPreviousOrgTo.setIndustryFromDate("");
				empPreviousOrgTo.setIndustryToDate("");
				/*-------------------*/
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				objform.getEmployeeInfoTONew().setExperiences(list);
			}
			if(teachingFlag==0 && industryFlag==1)
			{
				List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setId(1);
				empPreviousOrgTo.setTeachingExpYears("");
				empPreviousOrgTo.setTeachingExpMonths("");
				empPreviousOrgTo.setCurrentTeachingOrganisation("");
				empPreviousOrgTo.setCurrentTeachnigDesignation("");
				/* code added by sudhir*/
				empPreviousOrgTo.setTeachingFromDate("");
				empPreviousOrgTo.setTeachingToDate("");
				/*-------------------*/
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo);
				objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
			}
			if(teachingFlag==1 && industryFlag==0)
			{
				List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setId(1);
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				/* code added by sudhir*/
				empPreviousOrgTo.setIndustryFromDate("");
				empPreviousOrgTo.setIndustryToDate("");
				/*-------------------*/
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				objform.getEmployeeInfoTONew().setExperiences(list);
			}
			if(teachingFlag==0 && industryFlag==0)
			{
				List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setId(1);
				empPreviousOrgTo.setTeachingExpYears("");
				empPreviousOrgTo.setTeachingExpMonths("");
				empPreviousOrgTo.setCurrentTeachingOrganisation("");
				empPreviousOrgTo.setCurrentTeachnigDesignation("");
				/* code added by sudhir*/
				empPreviousOrgTo.setTeachingFromDate("");
				empPreviousOrgTo.setTeachingToDate("");
				/*-------------------*/
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo);
				objform.getEmployeeInfoTONew().setTeachingExperience(teachingList);
				
				List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
				
				empPreviousOrgTo.setId(1);
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				/* code added by sudhir*/
				empPreviousOrgTo.setIndustryFromDate("");
				empPreviousOrgTo.setIndustryToDate("");
				/*-------------------*/
				objform.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				objform.getEmployeeInfoTONew().setExperiences(list);
			}
		}
	}	
		
	public Map<Integer, String> convertBoToForm(List<Object[]> list)throws Exception {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		if(list!=null){
			Iterator<Object[]> iterator = list.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				PayScaleTO payscaleTo= new PayScaleTO();
				if(objects[0]!=null){
					payscaleTo.setId(Integer.parseInt(objects[0].toString()));
				}
				if(objects[1]!=null){
					if(!objects[1].toString().isEmpty()){
						payscaleTo.setPayScale(objects[1].toString());
					}
				}
				
				map.put(payscaleTo.getId(), payscaleTo.getPayScale());
			}
		}
		map = (Map<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}		
	public String formatDate(Date date){
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String newDate=formatter.format(date);
		return newDate;
	}
	
}
