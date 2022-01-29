package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.bo.admin.CCGroup;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.EmpAgeofRetirement;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.EmpFunctionalArea;
import com.kp.cms.bo.admin.EmpIndustryType;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.EmpWorkType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeCategory;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.EventLocation;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.HlComplaintType;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlLeaveType;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.InterviewStatus;
import com.kp.cms.bo.admin.InvCampus;
import com.kp.cms.bo.admin.InvCompany;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvUom;
import com.kp.cms.bo.admin.LeaveType;
import com.kp.cms.bo.admin.MeritSet;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Region;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.Services;
import com.kp.cms.bo.admin.Sports;
import com.kp.cms.bo.admin.SubjectCodeGroup;
import com.kp.cms.bo.admin.SubjectType;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.auditorium.BlockDetails;
import com.kp.cms.bo.auditorium.BookingRequirements;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmployeeSubject;
import com.kp.cms.bo.exam.ExamGenBO;
import com.kp.cms.bo.exam.ExamSubjectTypeMasterBO;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.bo.examallotment.ClassGroup;
import com.kp.cms.bo.examallotment.ExamInviligatorExemption;
import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.LocationBo;
import com.kp.cms.bo.phd.PhdResearchPublication;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.SingleFieldMasterForm;
import com.kp.cms.helpers.admin.SingleFieldMasterHelper;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;

public class SingleFieldMasterHandler {
	public static volatile SingleFieldMasterHandler singleFieldMasterHandler = null;
    public static final Log log = LogFactory.getLog(SingleFieldMasterHandler.class);
	public static SingleFieldMasterHandler getInstance() {
		if (singleFieldMasterHandler == null) {
			singleFieldMasterHandler = new SingleFieldMasterHandler();
			return singleFieldMasterHandler;
		}
		return singleFieldMasterHandler;
	}

	/**
	 * 
	 * @return list of SingleFieldMasterTO objects, will be used in UI to
	 *         dispaly.
	 * @throws Exception
	 */
	public List getsingleFieldMaster(String boName) throws Exception {
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl
				.getInstance();
		List<SingleFieldMasterTO> singleFieldMasterTo = null;
		if (boName.equalsIgnoreCase("Caste")) {

			List<Caste> casteList = singleFieldMasterTransaction
					.getCasteMasterFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(casteList, boName);

		} else if (boName.equalsIgnoreCase("ExamSubjectTypeMaster")) {
			List<ExamSubjectTypeMasterBO> countryList = singleFieldMasterTransaction
			.getSubjectTypeMasterFields();
	        singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
			.copySingleFieldMasterHelper(countryList, boName);
      }  else if (boName.equalsIgnoreCase("Country")) {
			List<Country> countryList = singleFieldMasterTransaction
					.getCountryMasterFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(countryList, boName);
		} else if (boName.equalsIgnoreCase("AdmissionStatus")) {
			List<InterviewStatus> admissionList = singleFieldMasterTransaction
					.getAdmissionStatusFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(admissionList, boName);
		} else if (boName.equalsIgnoreCase("AdmissionStatus")) {
			List<InterviewStatus> admissionList = singleFieldMasterTransaction
					.getAdmissionStatusFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(admissionList, boName);
		}

		else if (boName.equalsIgnoreCase("CourseScheme")) {
			List<CourseScheme> courseSchemeList = singleFieldMasterTransaction
					.getCourseSchemeFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(courseSchemeList, boName);
		} else if (boName.equalsIgnoreCase("PreRequisite")) {
			List<Prerequisite> prerequisiteList = singleFieldMasterTransaction
					.getPrerequisiteFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(prerequisiteList, boName);
		} else if (boName.equalsIgnoreCase("MotherTongue")) {
			List<MotherTongue> motherTongueList = singleFieldMasterTransaction
					.getMotherTongueFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(motherTongueList, boName);
		} else if (boName.equalsIgnoreCase("Occupation")) {
			List<Occupation> occupationList = singleFieldMasterTransaction
					.getOccupationFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(occupationList, boName);
		} else if (boName.equalsIgnoreCase("DocType")) {
			List<DocType> docTypeList = singleFieldMasterTransaction
					.getDocTypeFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(docTypeList, boName);
		} else if (boName.equalsIgnoreCase("Region")) {
			List<Region> regionList = singleFieldMasterTransaction
					.getRegionFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(regionList, boName);
		} else if (boName.equalsIgnoreCase("ResidentCategory")) {
			List<ResidentCategory> residentCategoryList = singleFieldMasterTransaction
					.getResidentCategoryFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(residentCategoryList, boName);
		} else if (boName.equalsIgnoreCase("MeritSet")) {
			List<MeritSet> meritSetList = singleFieldMasterTransaction
					.getMeritSetFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(meritSetList, boName);
		} else if (boName.equalsIgnoreCase("FeePaymentMode")) {
			List<FeePaymentMode> feePaymentMode = singleFieldMasterTransaction
					.getFeePaymentModeFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(feePaymentMode, boName);
		} else if (boName.equalsIgnoreCase("Religion")) {
			List<Religion> religion = singleFieldMasterTransaction
					.getReligionFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(religion, boName);
		} else if (boName.equalsIgnoreCase("ProgramType")) {
			List<ProgramType> programType = singleFieldMasterTransaction
					.getProgramTypeFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(programType, boName);
		} else if (boName.equalsIgnoreCase("University")) {
			List<University> university = singleFieldMasterTransaction
					.getUniversityFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(university, boName);
		} else if (boName.equalsIgnoreCase("Department")) {
			List<Department> department = singleFieldMasterTransaction
					.getDepartmentFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(department, boName);
		} else if (boName.equalsIgnoreCase("Roles")) {
			List<Roles> roles = singleFieldMasterTransaction.getRolesFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(roles, boName);
		} else if (boName.equalsIgnoreCase("Designation")) {
			List<Designation> designation = singleFieldMasterTransaction
					.getDesignationFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(designation, boName);
		} else if (boName.equalsIgnoreCase("EmployeeCategory")) {
			List<EmployeeCategory> employeeCategory = singleFieldMasterTransaction
					.getEmployeeCategoryFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(employeeCategory, boName);
		} else if (boName.equalsIgnoreCase("LeaveType")) {
			List<LeaveType> leaveType = singleFieldMasterTransaction
					.getLeaveTypeFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(leaveType, boName);
		} else if (boName.equalsIgnoreCase("Nationality")) {
			List<Nationality> nationality = singleFieldMasterTransaction
					.getNationalityFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(nationality, boName);
		} else if (boName.equalsIgnoreCase("Income")) {
			List<Income> income = singleFieldMasterTransaction
					.getIncomeFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(income, boName);
		} else if (boName.equalsIgnoreCase("HlFacility")) {
			List<HlFacility> hlFacility = singleFieldMasterTransaction
					.getHlFacilityFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(hlFacility, boName);
		} else if (boName.equalsIgnoreCase("HlComplaintType")) {
			List<HlComplaintType> hlComplaintType = singleFieldMasterTransaction
					.getHlComplaintTypeFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(hlComplaintType, boName);
		} else if (boName.equalsIgnoreCase("HlLeaveType")) {
			List<HlLeaveType> hlLeaveType = singleFieldMasterTransaction
					.getHlLeaveTypeFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(hlLeaveType, boName);
		} else if (boName.equalsIgnoreCase("InvUom")) {
			List<InvUom> invUom = singleFieldMasterTransaction.getUOMFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(invUom, boName);
		} else if (boName.equalsIgnoreCase("InvLocation")) {
			List<InvLocation> invLocation = singleFieldMasterTransaction
					.getInventoryLocations();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(invLocation, boName);
		} else if (boName.equalsIgnoreCase("InvItemCategory")) {
			List<InvItemCategory> invItemCategory = singleFieldMasterTransaction
					.getItemCategory();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(invItemCategory, boName);
		} else if (boName.equalsIgnoreCase("EmpFunctionalArea")) {
			List<EmpFunctionalArea> functionalArea = singleFieldMasterTransaction
					.getFunctionalArea();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(functionalArea, boName);
		} else if (boName.equalsIgnoreCase("EmpLeaveType")) {
			List<EmpLeaveType> empLeaveType = singleFieldMasterTransaction
					.getEmployeeLeaveType();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(empLeaveType, boName);
		} else if (boName.equalsIgnoreCase("EmpQualificationLevel")) {
			List<EmpQualificationLevel> empQualification = singleFieldMasterTransaction
					.getEmpQualidication();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(empQualification, boName);
		} else if (boName.equalsIgnoreCase("EmpIndustryType")) {
			List<EmpIndustryType> empIndustryType = singleFieldMasterTransaction
					.getIndustryType();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(empIndustryType, boName);
		} else if (boName.equalsIgnoreCase("EmpAgeofRetirement")) {
			List<EmpAgeofRetirement> empAgeofRetirement = singleFieldMasterTransaction
					.getEmpAgeofRetirement();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(empAgeofRetirement, boName);
		} else if (boName.equalsIgnoreCase("EmpAllowance")) {
			List<EmpAllowance> empAllowance = singleFieldMasterTransaction
					.getEmpAllowances();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(empAllowance, boName);
		} else if (boName.equalsIgnoreCase("EmpJobType")) {
			List<EmpJobType> empJobType = singleFieldMasterTransaction
					.getEmpJobType();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(empJobType, boName);
		} else if (boName.equalsIgnoreCase("EmpWorkType")) {
			List<EmpWorkType> empworkType = singleFieldMasterTransaction
					.getEmpWorkType();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(empworkType, boName);
		} else if (boName.equalsIgnoreCase("PcBankAccNumber")) {
			List<PcBankAccNumber> pcBankAccNumber = singleFieldMasterTransaction
					.getBankAccNo();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(pcBankAccNumber, boName);

		}else if (boName.equalsIgnoreCase("InvCampus")) {
			List<InvCampus> invCampus = singleFieldMasterTransaction.getInvCampus();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(invCampus, boName);
		}/**
		 * Fetch list of all the active entries in the DB for all single field
		 * entries in the Exams module
		 */
		else if (boName.contains("_Exam_")) {
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(
							singleFieldMasterTransaction
									.getListExamGenBO(boName), "_Exam_");

		} else if (boName.contains("ExamSubjectTypeMaster")) {
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
			.copySingleFieldMasterHelper(
					singleFieldMasterTransaction
							.getListExamGenBO(boName), "ExamSubjectTypeMaster");

       } else if (boName.equalsIgnoreCase("Stream")) {
			List<EmployeeStreamBO> objEmployeeStream = singleFieldMasterTransaction
					.getEmployeeStream();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(objEmployeeStream, boName);

		}

		else if (boName.equalsIgnoreCase("EmployeeWorkLocation")) {
			List<EmployeeWorkLocationBO> objWorkLocation = singleFieldMasterTransaction
					.getEmployeeWorkLocationStream();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(objWorkLocation, boName);

		} else if (boName.equalsIgnoreCase("EmployeeType")) {
			List<EmployeeTypeBO> objWorkLocation = singleFieldMasterTransaction
					.getEmployeeType();

			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(objWorkLocation, boName);

		}else if (boName.equalsIgnoreCase("CharacterAndConduct")) {
			List<CharacterAndConduct> objCharacterAndConduct = singleFieldMasterTransaction
			.getCharacterAndConduct();

			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
				.copySingleFieldMasterHelper(objCharacterAndConduct, boName);
		}else if(boName.equalsIgnoreCase("Sports")){
			List<Sports> sportsList = singleFieldMasterTransaction.getSportsMasterFields();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(sportsList, boName);
		}else if(boName.equalsIgnoreCase("SubjectAreaBO")){
			List<SubjectAreaBO> SubjectAreaList = singleFieldMasterTransaction.getSubjectArea();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(SubjectAreaList, boName);
		}
		else if(boName.equalsIgnoreCase("EmpJobTitle")){
			List<EmpJobTitle> jobTitleList = singleFieldMasterTransaction.getEmpJobTitle();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(jobTitleList, boName);
		}
		else if(boName.equalsIgnoreCase("ApplicationStatus")){
			List<ApplicationStatus> applicationStatus = singleFieldMasterTransaction.getApplicationStatus();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(applicationStatus, boName);
		}else if(boName.equalsIgnoreCase("CCGroup")){
			List<CCGroup> applicationStatus = singleFieldMasterTransaction.getMasterEntryData(CCGroup.class);
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(applicationStatus, boName);
		}
		else if(boName.equalsIgnoreCase("InvCompany")){
			List<InvCompany> invCompany = singleFieldMasterTransaction.getInvCompany();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(invCompany, boName);
		}
		else if(boName.equalsIgnoreCase("ApplicantFeedback")){
			List<ApplicantFeedback> applicantFeedback = singleFieldMasterTransaction.getApplicantFeedback();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(applicantFeedback, boName);
		}else if(boName.equalsIgnoreCase("Discipline")){
			List<DisciplineBo> disciplineBos = singleFieldMasterTransaction.getDisciplines();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(disciplineBos, boName);
		}else if(boName.equalsIgnoreCase("PhdResearchPublication")){
			List<PhdResearchPublication> researchPublication = singleFieldMasterTransaction.getResearchPublication();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(researchPublication, boName);
		}else if(boName.equalsIgnoreCase("Location")){
			List<LocationBo> locationBos = singleFieldMasterTransaction.getLocations();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(locationBos, boName);
		}/*else if(boName.equalsIgnoreCase("FineCategory")){
			List<FineCategoryBo> fineCategoryBos = singleFieldMasterTransaction.getFineCategory();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance()
					.copySingleFieldMasterHelper(fineCategoryBos, boName);
		}*/
		else if(boName.equalsIgnoreCase("BlockDetails")){
			List<BlockDetails> blockDetails = singleFieldMasterTransaction.getBlockDetails();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(blockDetails, boName);
			
		}else if(boName.equalsIgnoreCase("BookingRequirements")){
			List<BookingRequirements> requirements = singleFieldMasterTransaction.getBookingRequirements();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(requirements, boName);
			
		} else if(boName.equalsIgnoreCase("EventLocation")){
			List<EventLocation> eventLocation = singleFieldMasterTransaction.getEventLocation();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(eventLocation, boName);
			
		} 
// Code added by sujitha
		else if(boName.equalsIgnoreCase("ClassGroup")){
			List<ClassGroup> classGroup = singleFieldMasterTransaction.getClassGroup();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(classGroup, boName);
		}else if(boName.equalsIgnoreCase("ExamInviligatorExemption")){
			List<ExamInviligatorExemption> examInviligatorExemption =singleFieldMasterTransaction.getExamInviligatorExemption();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(examInviligatorExemption, boName);
		}else if(boName.equalsIgnoreCase("SubjectCodeGroup")){
			List<SubjectCodeGroup> subjectsCodeGroup =singleFieldMasterTransaction.getSubjectsCodeGroup();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(subjectsCodeGroup, boName);
		}else if(boName.equalsIgnoreCase("EmployeeSubject")){
			List<EmployeeSubject> empSubjects = singleFieldMasterTransaction.getEmployeeSubjects();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(empSubjects, boName);
		}else if(boName.equalsIgnoreCase("Services")){
			List<Services> services = singleFieldMasterTransaction.getServices();
			singleFieldMasterTo = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(services, boName);
		}
		
		
			
		log.error("ending of getsingleFieldMaster method in SingleFieldMasterHandler");
		return singleFieldMasterTo;
		
	}
	

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */
	public boolean addSingleFieldMaster(
			SingleFieldMasterForm singleFieldMasterForm, String mode,
			String boName) throws Exception {
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl
				.getInstance();
		boolean isAdded = false;
		Boolean originalNotChanged = false;

		String name = "";
		String origianlValue = "";
		if (singleFieldMasterForm.getName() != null
				&& !singleFieldMasterForm.getName().isEmpty()) {
			name = singleFieldMasterForm.getName().trim();
		}
		if (singleFieldMasterForm.getOriginalValue() != null
				&& !singleFieldMasterForm.getOriginalValue().isEmpty()) {
			origianlValue = singleFieldMasterForm.getOriginalValue().trim();
		}

		Caste caste = null;
		Country country = null;
		ExamSubjectTypeMasterBO examSubjectTypeMasterBO = null;
		SubjectType subjectType = null;
		Sports sports = null;
		MotherTongue motherTongue = null;
		Prerequisite prerequisite = null;
		DocType docType = null;
		Region region = null;
		MeritSet meritSet = null;
		InterviewStatus admissionStatus = null;
		CourseScheme courseScheme = null;
		Occupation occupation = null;
		ResidentCategory residentCategory = null;
		ProgramType programType = null;
		FeePaymentMode feePaymentMode = null;
		Religion religion = null;
		University university = null;
		Department department = null;
		Roles roles = null;
		Designation designation = null;
		EmployeeCategory employeeCategory = null;
		LeaveType leaveType = null;
		Nationality nationality = null;
		Income income = null;
		HlFacility hlFacility = null;
		HlComplaintType hlComplaintType = null;
		HlLeaveType hlLeaveType = null;
		InvUom invUom = null;
		InvLocation invLocation = null;
		InvItemCategory invItemCategory = null;
		EmpFunctionalArea empFunctionalArea = null;
		EmpLeaveType empLeaveType = null;
		EmpQualificationLevel empQualificationLevel = null;
		EmpIndustryType empIndustryType = null;
		EmpAgeofRetirement ageofRetirement = null;
		EmpAllowance empAllowance = null;
		EmpJobType empJobType = null;
		EmpWorkType empWorkType = null;
		PcBankAccNumber pcAccNumber = null;
		EmployeeStreamBO objEmployeeStreamBo = null;
		EmployeeWorkLocationBO objEWLBO = null;
		EmployeeTypeBO objETypeBO = null;
		CharacterAndConduct characterAndConduct = null;
		SubjectAreaBO subjectAreaBO =null;
		EmpJobTitle empJobTitle = null;
		ApplicationStatus applicationStatus = null;
		InvCampus invCampus=null;
		CCGroup ccGroup=null;
		InvCompany invCompany=null;
		ApplicantFeedback applicantFeedback=null;
		DisciplineBo disciplineBo=null;
		PhdResearchPublication researchPublication=null;
		LocationBo locationBo=null;
		BlockDetails blocks = null;
		BookingRequirements bookingRequirements = null;
		EventLocation eventLocation=null;
		ClassGroup classGroup=null;
		ExamInviligatorExemption examInviligatorExemption=null;
		SubjectCodeGroup subjectCodeGroup=null;
		EmployeeSubject empSubject = null;
		Services services = null;
		
		
		
		if (name.equalsIgnoreCase(origianlValue)) {
			originalNotChanged = true;
		}
		if (mode.equals("Add")) {
			originalNotChanged = false; // for add no need to check original
			// changed
		}

		if (boName.equalsIgnoreCase("Caste")) {
			if (!originalNotChanged) {
				caste = SingleFieldMasterHelper.getInstance()
						.populateFormToCast(singleFieldMasterForm);
				caste = singleFieldMasterTransaction.isCastDuplcated(caste);
				if (caste != null && caste.getIsActive()) {
					throw new DuplicateException();
				} else if (caste != null && !caste.getIsActive()) {
					singleFieldMasterForm.setReactivateid(caste.getId());
					throw new ReActivateException();
				}

			}
			caste = SingleFieldMasterHelper.getInstance().populateFormToCast(
					singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				caste.setCreatedDate(new Date());
				caste.setLastModifiedDate(new Date());
				caste.setModifiedBy(singleFieldMasterForm.getUserId());
				caste.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				caste.setLastModifiedDate(new Date());
				caste.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addCaste(caste, mode);

		} else if (boName.equalsIgnoreCase("Country")) {
			if (!originalNotChanged) {
				country = SingleFieldMasterHelper.getInstance()
						.populateFormToCountry(singleFieldMasterForm);
				country = singleFieldMasterTransaction
						.isCountryDuplcated(country);
				if (country != null && country.getIsActive()) {
					throw new DuplicateException();
				} else if (country != null && !country.getIsActive()) {
					singleFieldMasterForm.setReactivateid(country.getId());
					throw new ReActivateException();
				}

			}
			country = SingleFieldMasterHelper.getInstance()
					.populateFormToCountry(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				country.setCreatedDate(new Date());
				country.setLastModifiedDate(new Date());
				country.setModifiedBy(singleFieldMasterForm.getUserId());
				country.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				country.setModifiedBy(singleFieldMasterForm.getUserId());
				country.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addCountry(country, mode);

		}else if (boName.equalsIgnoreCase("ExamSubjectTypeMaster")) {
			if (!originalNotChanged) {
				examSubjectTypeMasterBO = SingleFieldMasterHelper.getInstance()
						.populateFormToSubjectType(singleFieldMasterForm);
				examSubjectTypeMasterBO = singleFieldMasterTransaction
						.isSubjectTypeDuplcated(examSubjectTypeMasterBO);
				if (examSubjectTypeMasterBO != null && examSubjectTypeMasterBO.getIsActive()) {
					throw new DuplicateException();
				} else if (examSubjectTypeMasterBO != null && !examSubjectTypeMasterBO.getIsActive()) {
					singleFieldMasterForm.setReactivateid(examSubjectTypeMasterBO.getId());
					throw new ReActivateException();
				}

			}
			examSubjectTypeMasterBO = SingleFieldMasterHelper.getInstance()
					.populateFormToSubjectType(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				examSubjectTypeMasterBO.setCreatedDate(new Date());
				examSubjectTypeMasterBO.setLastModifiedDate(new Date());
				examSubjectTypeMasterBO.setModifiedBy(singleFieldMasterForm.getUserId());
				examSubjectTypeMasterBO.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				examSubjectTypeMasterBO.setModifiedBy(singleFieldMasterForm.getUserId());
				examSubjectTypeMasterBO.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addSubjectType(examSubjectTypeMasterBO, mode);

		}else if (boName.equalsIgnoreCase("Sports")) {
			if (!originalNotChanged) {
				sports = SingleFieldMasterHelper.getInstance()
						.populateFormToSports(singleFieldMasterForm);
				sports = singleFieldMasterTransaction
						.isSportsDuplcated(sports);
				if (sports != null && sports.getIsActive()) {
					throw new DuplicateException();
				} else if (sports != null && !sports.getIsActive()) {
					singleFieldMasterForm.setReactivateid(sports.getId());
					throw new ReActivateException();
				}

			}
			sports = SingleFieldMasterHelper.getInstance()
					.populateFormToSports(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				sports.setCreatedDate(new Date());
				sports.setLastModifiedDate(new Date());
				sports.setModifiedBy(singleFieldMasterForm.getUserId());
				sports.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				sports.setModifiedBy(singleFieldMasterForm.getUserId());
				sports.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addSports(sports, mode);

		} else if (boName.equalsIgnoreCase("AdmissionStatus")) {
			{
				if (!originalNotChanged) {
					admissionStatus = SingleFieldMasterHelper.getInstance()
							.populateFormToAdmissionStatus(
									singleFieldMasterForm);
					admissionStatus = singleFieldMasterTransaction
							.isAdmissionStatusDuplcated(admissionStatus);
					if (admissionStatus != null
							&& admissionStatus.getIsActive()) {
						throw new DuplicateException();
					} else if (admissionStatus != null
							&& !admissionStatus.getIsActive()) {
						singleFieldMasterForm.setReactivateid(admissionStatus
								.getId());
						throw new ReActivateException();
					}

				}

				admissionStatus = SingleFieldMasterHelper.getInstance()
						.populateFormToAdmissionStatus(singleFieldMasterForm);
				if ("Add".equalsIgnoreCase(mode)) {
					admissionStatus.setCreatedDate(new Date());
					admissionStatus.setLastModifiedDate(new Date());
					admissionStatus.setModifiedBy(singleFieldMasterForm
							.getUserId());
					admissionStatus.setCreatedBy(singleFieldMasterForm
							.getUserId());
				} else // edit
				{
					admissionStatus.setLastModifiedDate(new Date());
					admissionStatus.setModifiedBy(singleFieldMasterForm
							.getUserId());

				}

				isAdded = singleFieldMasterTransaction.addAdmissionStatus(
						admissionStatus, mode);

			}
		} else if (boName.equalsIgnoreCase("CourseScheme")) {
			if (!originalNotChanged) {
				courseScheme = SingleFieldMasterHelper.getInstance()
						.populateFormToCourseScheme(singleFieldMasterForm);
				courseScheme = singleFieldMasterTransaction
						.isCourseSchemeDuplcated(courseScheme);
				if (courseScheme != null && courseScheme.getIsActive()) {
					throw new DuplicateException();
				} else if (courseScheme != null && !courseScheme.getIsActive()) {
					singleFieldMasterForm.setReactivateid(courseScheme.getId());
					throw new ReActivateException();
				}

			}
			courseScheme = SingleFieldMasterHelper.getInstance()
					.populateFormToCourseScheme(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				courseScheme.setCreatedDate(new Date());
				courseScheme.setLastModifiedDate(new Date());
				courseScheme.setModifiedBy(singleFieldMasterForm.getUserId());
				courseScheme.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				courseScheme.setLastModifiedDate(new Date());
				courseScheme.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addCourseScheme(
					courseScheme, mode);

		} else if (boName.equalsIgnoreCase("PreRequisite")) {
			if (!originalNotChanged) {
				prerequisite = SingleFieldMasterHelper.getInstance()
						.populateFormToPrerequisite(singleFieldMasterForm);
				prerequisite = singleFieldMasterTransaction
						.isPrerequisiteDuplcated(prerequisite);

				if (prerequisite != null && prerequisite.getIsActive()) {
					throw new DuplicateException();
				} else if (prerequisite != null && !prerequisite.getIsActive()) {
					/*
					 * singleFieldMasterForm.setId(prerequisite .getId());
					 */
					singleFieldMasterForm.setReactivateid(prerequisite.getId());
					throw new ReActivateException();
				}

			}
			prerequisite = SingleFieldMasterHelper.getInstance()
					.populateFormToPrerequisite(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				prerequisite.setModifiedBy(singleFieldMasterForm.getUserId());
				prerequisite.setCreatedBy(singleFieldMasterForm.getUserId());
				prerequisite.setCreatedDate(new Date());
				prerequisite.setLastModifiedDate(new Date());
			} else // edit
			{
				prerequisite.setModifiedBy(singleFieldMasterForm.getUserId());
				prerequisite.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addPrerequisite(
					prerequisite, mode);

		} else if (boName.equalsIgnoreCase("MotherTongue")) {
			if (!originalNotChanged) {
				motherTongue = SingleFieldMasterHelper.getInstance()
						.populateFormToMotherTongue(singleFieldMasterForm);
				motherTongue = singleFieldMasterTransaction
						.isMotherTongueDuplcated(motherTongue);
				if (motherTongue != null && motherTongue.getIsActive()) {
					throw new DuplicateException();
				} else if (motherTongue != null && !motherTongue.getIsActive()) {
					singleFieldMasterForm.setReactivateid(motherTongue.getId());
					throw new ReActivateException();
				}
			}

			motherTongue = SingleFieldMasterHelper.getInstance()
					.populateFormToMotherTongue(singleFieldMasterForm);
			if ("Add".equalsIgnoreCase(mode)) {
				motherTongue.setModifiedBy(singleFieldMasterForm.getUserId());
				motherTongue.setCreatedBy(singleFieldMasterForm.getUserId());
				motherTongue.setCreatedDate(new Date());
				motherTongue.setLastModifiedDate(new Date());
			} else // edit
			{
				motherTongue.setModifiedBy(singleFieldMasterForm.getUserId());
				motherTongue.setLastModifiedDate(new Date());

			}
			isAdded = singleFieldMasterTransaction.addMotherTongue(
					motherTongue, mode);

		} else if (boName.equalsIgnoreCase("Occupation")) {
			if (!originalNotChanged) {
				occupation = SingleFieldMasterHelper.getInstance()
						.populateFormToOccupation(singleFieldMasterForm);
				occupation = singleFieldMasterTransaction
						.isOccupationDuplcated(occupation);
				if (occupation != null && occupation.getIsActive()) {
					throw new DuplicateException();
				} else if (occupation != null && !occupation.getIsActive()) {
					singleFieldMasterForm.setReactivateid(occupation.getId());
					throw new ReActivateException();
				}

			}
			occupation = SingleFieldMasterHelper.getInstance()
					.populateFormToOccupation(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				occupation.setModifiedBy(singleFieldMasterForm.getUserId());
				occupation.setCreatedBy(singleFieldMasterForm.getUserId());
				occupation.setCreatedDate(new Date());
				occupation.setLastModifiedDate(new Date());
			} else // edit
			{
				occupation.setModifiedBy(singleFieldMasterForm.getUserId());
				occupation.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addOccupation(occupation,
					mode);
		} else if (boName.equalsIgnoreCase("DocType")) {
			if (!originalNotChanged) {
				docType = SingleFieldMasterHelper.getInstance()
						.populateFormToDocType(singleFieldMasterForm);
				docType = singleFieldMasterTransaction
						.isDocTypeDuplcated(docType);
				if (docType != null && docType.getIsActive()) {
					throw new DuplicateException();
				} else if (docType != null && !docType.getIsActive()) {
					singleFieldMasterForm.setReactivateid(docType.getId());
					throw new ReActivateException();
				}

			}
			docType = SingleFieldMasterHelper.getInstance()
					.populateFormToDocType(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				docType.setModifiedBy(singleFieldMasterForm.getUserId());
				docType.setCreatedBy(singleFieldMasterForm.getUserId());
				docType.setCreatedDate(new Date());
				docType.setLastModifiedDate(new Date());
			} else // edit
			{
				docType.setModifiedBy(singleFieldMasterForm.getUserId());
				docType.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addDocType(docType, mode);

		} else if (boName.equalsIgnoreCase("Region")) {
			if (!originalNotChanged) {
				region = SingleFieldMasterHelper.getInstance()
						.populateFormToRegion(singleFieldMasterForm);
				region = singleFieldMasterTransaction.isRegionDuplcated(region);
				if (region != null && region.getIsActive()) {
					throw new DuplicateException();
				} else if (region != null && !region.getIsActive()) {
					singleFieldMasterForm.setReactivateid(region.getId());
					throw new ReActivateException();
				}

			}
			region = SingleFieldMasterHelper.getInstance()
					.populateFormToRegion(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				region.setModifiedBy(singleFieldMasterForm.getUserId());
				region.setCreatedBy(singleFieldMasterForm.getUserId());
				region.setCreatedDate(new Date());
				region.setLastModifiedDate(new Date());
			} else // edit
			{
				region.setModifiedBy(singleFieldMasterForm.getUserId());
				region.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addRegion(region, mode);

		} else if (boName.equalsIgnoreCase("ResidentCategory")) {
			if (!originalNotChanged) {
				residentCategory = SingleFieldMasterHelper.getInstance()
						.populateFormToResidentCategory(singleFieldMasterForm);
				residentCategory = singleFieldMasterTransaction
						.isResidentCategoryDuplcated(residentCategory);
				if (residentCategory != null && residentCategory.getIsActive()) {
					throw new DuplicateException();
				} else if (residentCategory != null
						&& !residentCategory.getIsActive()) {
					singleFieldMasterForm.setReactivateid(residentCategory
							.getId());
					throw new ReActivateException();
				}

			}
			residentCategory = SingleFieldMasterHelper.getInstance()
					.populateFormToResidentCategory(singleFieldMasterForm);

			Employee employee = new Employee();
			employee.setId(1);
			if ("Add".equalsIgnoreCase(mode)) {
				residentCategory.setCreatedDate(new Date());
				residentCategory.setLastModifiedDate(new Date());
				residentCategory
						.setCreatedBy(singleFieldMasterForm.getUserId());
				residentCategory.setModifiedBy(singleFieldMasterForm
						.getUserId());
			} else // edit
			{
				residentCategory.setModifiedBy(singleFieldMasterForm
						.getUserId());
				residentCategory.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addResidentCategory(
					residentCategory, mode);

		} else if (boName.equalsIgnoreCase("MeritSet")) {
			if (!originalNotChanged) {
				meritSet = SingleFieldMasterHelper.getInstance()
						.populateFormToMeritSet(singleFieldMasterForm);
				meritSet = singleFieldMasterTransaction
						.isMeritSetDuplcated(meritSet);
				if (meritSet != null && meritSet.getIsActive()) {
					throw new DuplicateException();
				} else if (meritSet != null && !meritSet.getIsActive()) {
					singleFieldMasterForm.setReactivateid(meritSet.getId());
					throw new ReActivateException();
				}

			}
			meritSet = SingleFieldMasterHelper.getInstance()
					.populateFormToMeritSet(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				meritSet.setModifiedBy(singleFieldMasterForm.getUserId());
				meritSet.setCreatedBy(singleFieldMasterForm.getUserId());
				meritSet.setCreatedDate(new Date());
				meritSet.setLastModifiedDate(new Date());
			} else // edit
			{
				meritSet.setModifiedBy(singleFieldMasterForm.getUserId());
				meritSet.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addMeritSet(meritSet, mode);

		} else if (boName.equalsIgnoreCase("ProgramType")) {
			if (!originalNotChanged) {
				programType = SingleFieldMasterHelper.getInstance()
						.populateFormToProgramType(singleFieldMasterForm);
				programType = singleFieldMasterTransaction
						.isProgramTypeDuplcated(programType);
				if (programType != null && programType.getIsActive()) {
					throw new DuplicateException();
				} else if (programType != null && !programType.getIsActive()) {
					singleFieldMasterForm.setReactivateid(programType.getId());
					throw new ReActivateException();
				}

			}
			programType = SingleFieldMasterHelper.getInstance()
					.populateFormToProgramType(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				programType.setModifiedBy(singleFieldMasterForm.getUserId());
				programType.setCreatedBy(singleFieldMasterForm.getUserId());
				programType.setCreatedDate(new Date());
				programType.setLastModifiedDate(new Date());
			} else // edit
			{
				programType.setModifiedBy(singleFieldMasterForm.getUserId());
				programType.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addProgramType(programType,
					mode);

		} else if (boName.equalsIgnoreCase("FeePaymentMode")) {
			if (!originalNotChanged) {
				feePaymentMode = SingleFieldMasterHelper.getInstance()
						.populateFormToFeePaymentMode(singleFieldMasterForm);
				feePaymentMode = singleFieldMasterTransaction
						.isFeePaymentModeDuplcated(feePaymentMode);
				if (feePaymentMode != null && feePaymentMode.getIsActive()) {
					throw new DuplicateException();
				} else if (feePaymentMode != null
						&& !feePaymentMode.getIsActive()) {
					singleFieldMasterForm.setReactivateid(feePaymentMode
							.getId());
					throw new ReActivateException();
				}

			}
			feePaymentMode = SingleFieldMasterHelper.getInstance()
					.populateFormToFeePaymentMode(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				feePaymentMode.setModifiedBy(singleFieldMasterForm.getUserId());
				feePaymentMode.setCreatedBy(singleFieldMasterForm.getUserId());
				feePaymentMode.setCreatedDate(new Date());
				feePaymentMode.setLastModifiedDate(new Date());
			} else // edit
			{
				feePaymentMode.setModifiedBy(singleFieldMasterForm.getUserId());
				feePaymentMode.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addFeePaymentMode(
					feePaymentMode, mode);

		} else if (boName.equalsIgnoreCase("Religion")) {
			if (!originalNotChanged) {
				religion = SingleFieldMasterHelper.getInstance()
						.populateFormToReligion(singleFieldMasterForm);
				religion = singleFieldMasterTransaction
						.isReligionDuplcated(religion);
				if (religion != null && religion.getIsActive()) {
					throw new DuplicateException();
				} else if (religion != null && !religion.getIsActive()) {
					singleFieldMasterForm.setReactivateid(religion.getId());
					throw new ReActivateException();
				}

			}
			religion = SingleFieldMasterHelper.getInstance()
					.populateFormToReligion(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				religion.setModifiedBy(singleFieldMasterForm.getUserId());
				religion.setCreatedBy(singleFieldMasterForm.getUserId());
				religion.setCreatedDate(new Date());
				religion.setLastModifiedDate(new Date());
			} else // edit
			{
				religion.setModifiedBy(singleFieldMasterForm.getUserId());
				religion.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addReligion(religion, mode);

		} else if (boName.equalsIgnoreCase("University")) {
			if (!originalNotChanged) {
				university = SingleFieldMasterHelper.getInstance()
						.populateFormToUniversity(singleFieldMasterForm);
				university = singleFieldMasterTransaction
						.isUniversityDuplcated(university);
				if (university != null && university.getIsActive()) {
					throw new DuplicateException();
				} else if (university != null && !university.getIsActive()) {
					singleFieldMasterForm.setReactivateid(university.getId());
					throw new ReActivateException();
				}

			}
			university = SingleFieldMasterHelper.getInstance()
					.populateFormToUniversity(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				university.setModifiedBy(singleFieldMasterForm.getUserId());
				university.setCreatedBy(singleFieldMasterForm.getUserId());
				university.setCreatedDate(new Date());
				university.setLastModifiedDate(new Date());
			} else // edit
			{
				university.setModifiedBy(singleFieldMasterForm.getUserId());
				university.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addUniversity(university,
					mode);

		} else if (boName.equalsIgnoreCase("Department")) {
			if (!originalNotChanged) {
				department = SingleFieldMasterHelper.getInstance()
						.populateFormToDepartment(singleFieldMasterForm);
				department = singleFieldMasterTransaction
						.isDepartmentDuplcated(department);
				if (department != null && department.getIsActive()) {
					throw new DuplicateException();
				} else if (department != null && !department.getIsActive()) {
					singleFieldMasterForm.setReactivateid(department.getId());
					throw new ReActivateException();
				}

			}
			department = SingleFieldMasterHelper.getInstance()
					.populateFormToDepartment(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				department.setCreatedBy(singleFieldMasterForm.getUserId());
				department.setModifiedBy(singleFieldMasterForm.getUserId());
				department.setCreatedDate(new Date());
				department.setLastModifiedDate(new Date());
			} else // edit
			{
				department.setModifiedBy(singleFieldMasterForm.getUserId());
				department.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addDepartment(department,
					mode);

		} else if (boName.equalsIgnoreCase("Roles")) {
			if (!originalNotChanged) {
				roles = SingleFieldMasterHelper.getInstance()
						.populateFormToRoles(singleFieldMasterForm);
				roles = singleFieldMasterTransaction.isRolesDuplcated(roles);
				if (roles != null && roles.getIsActive()) {
					throw new DuplicateException();
				} else if (roles != null && !roles.getIsActive()) {
					singleFieldMasterForm.setReactivateid(roles.getId());
					throw new ReActivateException();
				}

			}
			roles = SingleFieldMasterHelper.getInstance().populateFormToRoles(
					singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				roles.setModifiedBy(singleFieldMasterForm.getUserId());
				roles.setCreatedBy(singleFieldMasterForm.getUserId());
				roles.setCreatedDate(new Date());
				roles.setLastModifiedDate(new Date());
			} else // edit
			{
				roles.setModifiedBy(singleFieldMasterForm.getUserId());
				roles.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addRoles(roles, mode);

		} else if (boName.equalsIgnoreCase("Designation")) {
			if (!originalNotChanged) {
				designation = SingleFieldMasterHelper.getInstance()
						.populateFormToDesignation(singleFieldMasterForm);
				designation = singleFieldMasterTransaction
						.isDesignationDuplcated(designation);
				if (designation != null && designation.getIsActive()) {
					throw new DuplicateException();
				} else if (designation != null && !designation.getIsActive()) {
					singleFieldMasterForm.setReactivateid(designation.getId());
					throw new ReActivateException();
				}

			}
			designation = SingleFieldMasterHelper.getInstance()
					.populateFormToDesignation(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				designation.setModifiedBy(singleFieldMasterForm.getUserId());
				designation.setCreatedBy(singleFieldMasterForm.getUserId());
				designation.setCreatedDate(new Date());
				designation.setLastModifiedDate(new Date());
			} else // edit
			{
				designation.setModifiedBy(singleFieldMasterForm.getUserId());
				designation.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addDesignation(designation,
					mode);

		} else if (boName.equalsIgnoreCase("EmployeeCategory")) {
			if (!originalNotChanged) {
				employeeCategory = SingleFieldMasterHelper.getInstance()
						.populateFormToEmployeeCategory(singleFieldMasterForm);
				employeeCategory = singleFieldMasterTransaction
						.isEmployeeCategoryDuplcated(employeeCategory);
				if (employeeCategory != null && employeeCategory.getIsActive()) {
					throw new DuplicateException();
				} else if (employeeCategory != null
						&& !employeeCategory.getIsActive()) {
					singleFieldMasterForm.setReactivateid(employeeCategory
							.getId());
					throw new ReActivateException();
				}

			}
			employeeCategory = SingleFieldMasterHelper.getInstance()
					.populateFormToEmployeeCategory(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				employeeCategory.setModifiedBy(singleFieldMasterForm
						.getUserId());
				employeeCategory
						.setCreatedBy(singleFieldMasterForm.getUserId());
				employeeCategory.setCreatedDate(new Date());
				employeeCategory.setLastModifiedDate(new Date());
			} else // edit
			{
				employeeCategory.setModifiedBy(singleFieldMasterForm
						.getUserId());
				employeeCategory.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmployeeCategory(
					employeeCategory, mode);

		} else if (boName.equalsIgnoreCase("LeaveType")) {
			if (!originalNotChanged) {
				leaveType = SingleFieldMasterHelper.getInstance()
						.populateFormToLeaveType(singleFieldMasterForm);
				leaveType = singleFieldMasterTransaction
						.isLeaveTypeDuplcated(leaveType);
				if (leaveType != null && leaveType.getIsActive()) {
					throw new DuplicateException();
				} else if (leaveType != null && !leaveType.getIsActive()) {
					singleFieldMasterForm.setReactivateid(leaveType.getId());
					throw new ReActivateException();
				}

			}
			leaveType = SingleFieldMasterHelper.getInstance()
					.populateFormToLeaveType(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				leaveType.setModifiedBy(singleFieldMasterForm.getUserId());
				leaveType.setCreatedBy(singleFieldMasterForm.getUserId());
				leaveType.setCreatedDate(new Date());
				leaveType.setLastModifiedDate(new Date());
			} else // edit
			{
				leaveType.setModifiedBy(singleFieldMasterForm.getUserId());
				leaveType.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction
					.addLeaveType(leaveType, mode);

		} else if (boName.equalsIgnoreCase("Nationality")) {
			if (!originalNotChanged) {
				nationality = SingleFieldMasterHelper.getInstance()
						.populateFormToNatiionality(singleFieldMasterForm);
				nationality = singleFieldMasterTransaction
						.isNationalityDuplcated(nationality);
				if (nationality != null && nationality.getIsActive() != null
						&& nationality.getIsActive()) {
					throw new DuplicateException();
				} else if (nationality != null
						&& nationality.getIsActive() != null
						&& !nationality.getIsActive()) {
					singleFieldMasterForm.setReactivateid(nationality.getId());
					throw new ReActivateException();
				}

			}
			nationality = SingleFieldMasterHelper.getInstance()
					.populateFormToNatiionality(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				nationality.setModifiedBy(singleFieldMasterForm.getUserId());
				nationality.setCreatedBy(singleFieldMasterForm.getUserId());
				nationality.setCreatedDate(new Date());
				nationality.setLastModifiedDate(new Date());
			} else // edit
			{
				nationality.setModifiedBy(singleFieldMasterForm.getUserId());
				nationality.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addNationality(nationality,
					mode);

		} else if (boName.equalsIgnoreCase("Income")) {
			if (!originalNotChanged) {
				income = SingleFieldMasterHelper.getInstance()
						.populateFormToIncome(singleFieldMasterForm);
				income = singleFieldMasterTransaction.isIncomeDuplcated(income);
				if (income != null && income.getIsActive() != null
						&& income.getIsActive()) {
					throw new DuplicateException();
				} else if (income != null && income.getIsActive() != null
						&& !income.getIsActive()) {
					singleFieldMasterForm.setReactivateid(income.getId());
					throw new ReActivateException();
				}

			}
			income = SingleFieldMasterHelper.getInstance()
					.populateFormToIncome(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				income.setModifiedBy(singleFieldMasterForm.getUserId());
				income.setCreatedBy(singleFieldMasterForm.getUserId());
				income.setCreatedDate(new Date());
				income.setLastModifiedDate(new Date());
			} else // edit
			{
				income.setModifiedBy(singleFieldMasterForm.getUserId());
				income.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addIncome(income, mode);

		} else if (boName.equalsIgnoreCase("HlFacility")) {
			if (!originalNotChanged) {
				hlFacility = SingleFieldMasterHelper.getInstance()
						.populateFormToHlFacility(singleFieldMasterForm);
				hlFacility = singleFieldMasterTransaction
						.isHlFacilityDuplcated(hlFacility);
				if (hlFacility != null && hlFacility.getIsActive() != null
						&& hlFacility.getIsActive()) {
					throw new DuplicateException();
				} else if (hlFacility != null
						&& hlFacility.getIsActive() != null
						&& !hlFacility.getIsActive()) {
					singleFieldMasterForm.setReactivateid(hlFacility.getId());
					throw new ReActivateException();
				}

			}
			hlFacility = SingleFieldMasterHelper.getInstance()
					.populateFormToHlFacility(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				hlFacility.setModifiedBy(singleFieldMasterForm.getUserId());
				hlFacility.setCreatedBy(singleFieldMasterForm.getUserId());
				hlFacility.setCreatedDate(new Date());
				hlFacility.setLastModifiedDate(new Date());
			} else // edit
			{
				hlFacility.setModifiedBy(singleFieldMasterForm.getUserId());
				hlFacility.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addHlFacility(hlFacility,
					mode);

		} else if (boName.equalsIgnoreCase("HlComplaintType")) {
			if (!originalNotChanged) {
				hlComplaintType = SingleFieldMasterHelper.getInstance()
						.populateFormToHlComplaintType(singleFieldMasterForm);
				hlComplaintType = singleFieldMasterTransaction
						.isHlComplaintTypeDuplcated(hlComplaintType);
				if (hlComplaintType != null
						&& hlComplaintType.getIsActive() != null
						&& hlComplaintType.getIsActive()) {
					throw new DuplicateException();
				} else if (hlComplaintType != null
						&& hlComplaintType.getIsActive() != null
						&& !hlComplaintType.getIsActive()) {
					singleFieldMasterForm.setReactivateid(hlComplaintType
							.getId());
					throw new ReActivateException();
				}

			}
			hlComplaintType = SingleFieldMasterHelper.getInstance()
					.populateFormToHlComplaintType(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				hlComplaintType
						.setModifiedBy(singleFieldMasterForm.getUserId());
				hlComplaintType.setCreatedBy(singleFieldMasterForm.getUserId());
				hlComplaintType.setCreatedDate(new Date());
				hlComplaintType.setLastModifiedDate(new Date());
			} else // edit
			{
				hlComplaintType
						.setModifiedBy(singleFieldMasterForm.getUserId());
				hlComplaintType.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addHlComplaintType(
					hlComplaintType, mode);

		} else if (boName.equalsIgnoreCase("HlLeaveType")) {
			if (!originalNotChanged) {
				hlLeaveType = SingleFieldMasterHelper.getInstance()
						.populateFormToHlLeaveType(singleFieldMasterForm);
				hlLeaveType = singleFieldMasterTransaction
						.isHlLeaveTypeDuplcated(hlLeaveType);
				if (hlLeaveType != null && hlLeaveType.getIsActive() != null
						&& hlLeaveType.getIsActive()) {
					throw new DuplicateException();
				} else if (hlLeaveType != null
						&& hlLeaveType.getIsActive() != null
						&& !hlLeaveType.getIsActive()) {
					singleFieldMasterForm.setReactivateid(hlLeaveType.getId());
					throw new ReActivateException();
				}

			}
			hlLeaveType = SingleFieldMasterHelper.getInstance()
					.populateFormToHlLeaveType(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				hlLeaveType.setModifiedBy(singleFieldMasterForm.getUserId());
				hlLeaveType.setCreatedBy(singleFieldMasterForm.getUserId());
				hlLeaveType.setCreatedDate(new Date());
				hlLeaveType.setLastModifiedDate(new Date());
			} else // edit
			{
				hlLeaveType.setModifiedBy(singleFieldMasterForm.getUserId());
				hlLeaveType.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addHlLeaveType(hlLeaveType,
					mode);

		} else if (boName.equalsIgnoreCase("InvUom")) {
			if (!originalNotChanged) {
				invUom = SingleFieldMasterHelper.getInstance()
						.populateFormToInvUom(singleFieldMasterForm);
				invUom = singleFieldMasterTransaction.isInvUomDuplcated(invUom);
				if (invUom != null && invUom.getIsActive() != null
						&& invUom.getIsActive()) {
					throw new DuplicateException();
				} else if (invUom != null && invUom.getIsActive() != null
						&& !invUom.getIsActive()) {
					singleFieldMasterForm.setReactivateid(invUom.getId());
					throw new ReActivateException();
				}

			}
			invUom = SingleFieldMasterHelper.getInstance()
					.populateFormToInvUom(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				invUom.setModifiedBy(singleFieldMasterForm.getUserId());
				invUom.setCreatedBy(singleFieldMasterForm.getUserId());
				invUom.setCreatedDate(new Date());
				invUom.setLastModifiedDate(new Date());
			} else // edit
			{
				invUom.setModifiedBy(singleFieldMasterForm.getUserId());
				invUom.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addInvUom(invUom, mode);

		} else if (boName.equalsIgnoreCase("InvLocation")) {
			if (!originalNotChanged) {
				invLocation = SingleFieldMasterHelper.getInstance()
						.populateFormToInvLocation(singleFieldMasterForm);
				invLocation = singleFieldMasterTransaction
						.isInvLocationDuplcated(invLocation);
				if (invLocation != null && invLocation.getIsActive() != null
						&& invLocation.getIsActive()) {
					throw new DuplicateException();
				} else if (invLocation != null
						&& invLocation.getIsActive() != null
						&& !invLocation.getIsActive()) {
					singleFieldMasterForm.setReactivateid(invLocation.getId());
					throw new ReActivateException();
				}

			}
			invLocation = SingleFieldMasterHelper.getInstance()
					.populateFormToInvLocation(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				invLocation.setModifiedBy(singleFieldMasterForm.getUserId());
				invLocation.setCreatedBy(singleFieldMasterForm.getUserId());
				invLocation.setCreatedDate(new Date());
				invLocation.setLastModifiedDate(new Date());
			} else // edit
			{
				invLocation.setModifiedBy(singleFieldMasterForm.getUserId());
				invLocation.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addInvLocation(invLocation,
					mode);

		}

		else if (boName.equalsIgnoreCase("InvItemCategory")) {
			if (!originalNotChanged) {
				invItemCategory = SingleFieldMasterHelper.getInstance()
						.populateFormToInvItemCategory(singleFieldMasterForm);
				invItemCategory = singleFieldMasterTransaction
						.isInvItemCategoryDuplcated(invItemCategory);
				if (invItemCategory != null
						&& invItemCategory.getIsActive() != null
						&& invItemCategory.getIsActive()) {
					throw new DuplicateException();
				} else if (invItemCategory != null
						&& invItemCategory.getIsActive() != null
						&& !invItemCategory.getIsActive()) {
					singleFieldMasterForm.setReactivateid(invItemCategory
							.getId());
					throw new ReActivateException();
				}

			}
			invItemCategory = SingleFieldMasterHelper.getInstance()
					.populateFormToInvItemCategory(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				invItemCategory
						.setModifiedBy(singleFieldMasterForm.getUserId());
				invItemCategory.setCreatedBy(singleFieldMasterForm.getUserId());
				invItemCategory.setCreatedDate(new Date());
				invItemCategory.setLastModifiedDate(new Date());
			} else // edit
			{
				invItemCategory
						.setModifiedBy(singleFieldMasterForm.getUserId());
				invItemCategory.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addInvItemCategory(
					invItemCategory, mode);

		} else if (boName.equalsIgnoreCase("EmpFunctionalArea")) {
			if (!originalNotChanged) {
				empFunctionalArea = SingleFieldMasterHelper.getInstance()
						.populateFormToFuctionalArea(singleFieldMasterForm);
				empFunctionalArea = singleFieldMasterTransaction
						.isFunctionalAreaDuplicated(empFunctionalArea);
				if (empFunctionalArea != null
						&& empFunctionalArea.getIsActive() != null
						&& empFunctionalArea.getIsActive()) {
					throw new DuplicateException();
				} else if (empFunctionalArea != null
						&& empFunctionalArea.getIsActive() != null
						&& !empFunctionalArea.getIsActive()) {
					singleFieldMasterForm.setReactivateid(empFunctionalArea
							.getId());
					throw new ReActivateException();
				}

			}
			empFunctionalArea = SingleFieldMasterHelper.getInstance()
					.populateFormToFuctionalArea(singleFieldMasterForm);
			;

			if ("Add".equalsIgnoreCase(mode)) {
				empFunctionalArea.setModifiedBy(singleFieldMasterForm
						.getUserId());
				empFunctionalArea.setCreatedBy(singleFieldMasterForm
						.getUserId());
				empFunctionalArea.setCreatedDate(new Date());
				empFunctionalArea.setLastModifiedDate(new Date());
			} else // edit
			{
				empFunctionalArea.setModifiedBy(singleFieldMasterForm
						.getUserId());
				empFunctionalArea.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addFunctionalArea(
					empFunctionalArea, mode);

		} else if (boName.equalsIgnoreCase("EmpLeaveType")) {
			if (!originalNotChanged) {
				empLeaveType = SingleFieldMasterHelper.getInstance()
						.populateFormToEmpLeaveType(singleFieldMasterForm);
				empLeaveType = singleFieldMasterTransaction
						.isEmpLeaveTypeDuplicated(empLeaveType);
				if (empLeaveType != null && empLeaveType.getIsActive() != null
						&& empLeaveType.getIsActive()) {
					throw new DuplicateException();
				} else if (empLeaveType != null
						&& empLeaveType.getIsActive() != null
						&& !empLeaveType.getIsActive()) {
					singleFieldMasterForm.setReactivateid(empLeaveType.getId());
					throw new ReActivateException();
				}

			}
			empLeaveType = SingleFieldMasterHelper.getInstance()
					.populateFormToEmpLeaveType(singleFieldMasterForm);
			;

			if ("Add".equalsIgnoreCase(mode)) {
				empLeaveType.setModifiedBy(singleFieldMasterForm.getUserId());
				empLeaveType.setCreatedBy(singleFieldMasterForm.getUserId());
				empLeaveType.setCreatedDate(new Date());
				empLeaveType.setLastModifiedDate(new Date());
			} else // edit
			{
				empLeaveType.setModifiedBy(singleFieldMasterForm.getUserId());
				empLeaveType.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmpLeaveType(
					empLeaveType, mode);

		} else if (boName.equalsIgnoreCase("EmpQualificationLevel")) {
			if (!originalNotChanged) {
				empQualificationLevel = SingleFieldMasterHelper
						.getInstance()
						.populateFormToQualificationLevel(singleFieldMasterForm);
				empQualificationLevel = singleFieldMasterTransaction
						.isEmpQualificationDuplicated(empQualificationLevel);
				if (empQualificationLevel != null
						&& empQualificationLevel.getIsActive() != null
						&& empQualificationLevel.getIsActive()) {
					throw new DuplicateException();
				} else if (empQualificationLevel != null
						&& empQualificationLevel.getIsActive() != null
						&& !empQualificationLevel.getIsActive()) {
					singleFieldMasterForm.setReactivateid(empQualificationLevel
							.getId());
					throw new ReActivateException();
				}

			}
			empQualificationLevel = SingleFieldMasterHelper.getInstance()
					.populateFormToQualificationLevel(singleFieldMasterForm);
			;

			if ("Add".equalsIgnoreCase(mode)) {
				empQualificationLevel.setModifiedBy(singleFieldMasterForm
						.getUserId());
				empQualificationLevel.setCreatedBy(singleFieldMasterForm
						.getUserId());
				empQualificationLevel.setCreatedDate(new Date());
				empQualificationLevel.setLastModifiedDate(new Date());
			} else // edit
			{
				empQualificationLevel.setModifiedBy(singleFieldMasterForm
						.getUserId());
				empQualificationLevel.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmpQualification(
					empQualificationLevel, mode);

		} else if (boName.equalsIgnoreCase("EmpIndustryType")) {
			if (!originalNotChanged) {
				empIndustryType = SingleFieldMasterHelper.getInstance()
						.populateFormToIndustryType(singleFieldMasterForm);
				empIndustryType = singleFieldMasterTransaction
						.isEmpIndustryTypeDuplicated(empIndustryType);
				if (empIndustryType != null
						&& empIndustryType.getIsActive() != null
						&& empIndustryType.getIsActive()) {
					throw new DuplicateException();
				} else if (empIndustryType != null
						&& empIndustryType.getIsActive() != null
						&& !empIndustryType.getIsActive()) {
					singleFieldMasterForm.setReactivateid(empIndustryType
							.getId());
					throw new ReActivateException();
				}

			}
			empIndustryType = SingleFieldMasterHelper.getInstance()
					.populateFormToIndustryType(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				empIndustryType
						.setModifiedBy(singleFieldMasterForm.getUserId());
				empIndustryType.setCreatedBy(singleFieldMasterForm.getUserId());
				empIndustryType.setCreatedDate(new Date());
				empIndustryType.setLastModifiedDate(new Date());
			} else // edit
			{
				empIndustryType
						.setModifiedBy(singleFieldMasterForm.getUserId());
				empIndustryType.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmpIndustryType(
					empIndustryType, mode);

		} else if (boName.equalsIgnoreCase("EmpAgeofRetirement")) {
			if (!originalNotChanged) {
				ageofRetirement = SingleFieldMasterHelper.getInstance()
						.populateFormToAgeOfRetirement(singleFieldMasterForm);
				ageofRetirement = singleFieldMasterTransaction
						.isAgeOfRetirementExists(ageofRetirement);
				if (ageofRetirement != null) {
					singleFieldMasterForm.setReactivateid(ageofRetirement
							.getId());
					throw new ReActivateException();
				}

			}
			ageofRetirement = SingleFieldMasterHelper.getInstance()
					.populateFormToAgeOfRetirement(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				ageofRetirement
						.setModifiedBy(singleFieldMasterForm.getUserId());
				ageofRetirement.setCreatedBy(singleFieldMasterForm.getUserId());
				ageofRetirement.setCreatedDate(new Date());
				ageofRetirement.setLastModifiedDate(new Date());
			} else // edit
			{
				ageofRetirement
						.setModifiedBy(singleFieldMasterForm.getUserId());
				ageofRetirement.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmpAgeofRetirement(
					ageofRetirement, mode);

		} else if (boName.equalsIgnoreCase("EmpAllowance")) {
			if (!originalNotChanged) {
				empAllowance = SingleFieldMasterHelper.getInstance()
						.populateFormToEmpAllowance(singleFieldMasterForm);
				empAllowance = singleFieldMasterTransaction
						.isEmpAllowanceTypeDuplicated(empAllowance);
				if (empAllowance != null && empAllowance.getIsActive() != null
						&& empAllowance.getIsActive()) {
					throw new DuplicateException();
				} else if (empAllowance != null
						&& empAllowance.getIsActive() != null
						&& !empAllowance.getIsActive()) {
					singleFieldMasterForm.setReactivateid(empAllowance.getId());
					throw new ReActivateException();
				}

			}
			empAllowance = SingleFieldMasterHelper.getInstance()
					.populateFormToEmpAllowance(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				empAllowance.setModifiedBy(singleFieldMasterForm.getUserId());
				empAllowance.setCreatedBy(singleFieldMasterForm.getUserId());
				empAllowance.setCreatedDate(new Date());
				empAllowance.setLastModifiedDate(new Date());
			} else // edit
			{
				empAllowance.setModifiedBy(singleFieldMasterForm.getUserId());
				empAllowance.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmpAllowances(
					empAllowance, mode);

		} else if (boName.equalsIgnoreCase("EmpJobType")) {
			if (!originalNotChanged) {
				empJobType = SingleFieldMasterHelper.getInstance()
						.populateFormToEmpJob(singleFieldMasterForm);
				empJobType = singleFieldMasterTransaction
						.isEmpJobTypeDuplicated(empJobType);
				if (empJobType != null && empJobType.getIsActive() != null
						&& empJobType.getIsActive()) {
					throw new DuplicateException();
				} else if (empJobType != null
						&& empJobType.getIsActive() != null
						&& !empJobType.getIsActive()) {
					singleFieldMasterForm.setReactivateid(empJobType.getId());
					throw new ReActivateException();
				}

			}
			empJobType = SingleFieldMasterHelper.getInstance()
					.populateFormToEmpJob(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				empJobType.setModifiedBy(singleFieldMasterForm.getUserId());
				empJobType.setCreatedBy(singleFieldMasterForm.getUserId());
				empJobType.setCreatedDate(new Date());
				empJobType.setLastModifiedDate(new Date());
			} else // edit
			{
				empJobType.setModifiedBy(singleFieldMasterForm.getUserId());
				empJobType.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmpJobType(empJobType,
					mode);

		} else if (boName.equalsIgnoreCase("EmpWorkType")) {
			if (!originalNotChanged) {
				empWorkType = SingleFieldMasterHelper.getInstance()
						.populateFormToEmpWorkType(singleFieldMasterForm);
				empWorkType = singleFieldMasterTransaction
						.isEmpWorkTypeDuplicated(empWorkType);
				if (empWorkType != null && empWorkType.getIsActive() != null
						&& empWorkType.getIsActive()) {
					throw new DuplicateException();
				} else if (empWorkType != null
						&& empWorkType.getIsActive() != null
						&& !empWorkType.getIsActive()) {
					singleFieldMasterForm.setReactivateid(empWorkType.getId());
					throw new ReActivateException();
				}

			}
			empWorkType = SingleFieldMasterHelper.getInstance()
					.populateFormToEmpWorkType(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				empWorkType.setModifiedBy(singleFieldMasterForm.getUserId());
				empWorkType.setCreatedBy(singleFieldMasterForm.getUserId());
				empWorkType.setCreatedDate(new Date());
				empWorkType.setLastModifiedDate(new Date());
			} else // edit
			{
				empWorkType.setModifiedBy(singleFieldMasterForm.getUserId());
				empWorkType.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmpWorkType(empWorkType,
					mode);

		} else if (boName.equalsIgnoreCase("PcBankAccNumber")) {
			if (!originalNotChanged) {
				pcAccNumber = SingleFieldMasterHelper.getInstance()
						.populateFormToBankAccountNo(singleFieldMasterForm);
				pcAccNumber = singleFieldMasterTransaction
						.isPcBankAccNumberDuplicated(pcAccNumber);
				if (pcAccNumber != null && pcAccNumber.getIsActive() != null
						&& pcAccNumber.getIsActive()) {
					throw new DuplicateException();
				} else if (pcAccNumber != null
						&& pcAccNumber.getIsActive() != null
						&& !pcAccNumber.getIsActive()) {
					singleFieldMasterForm.setReactivateid(pcAccNumber.getId());
					throw new ReActivateException();
				}

			}
			pcAccNumber = SingleFieldMasterHelper.getInstance()
					.populateFormToBankAccountNo(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				pcAccNumber.setModifiedBy(singleFieldMasterForm.getUserId());
				pcAccNumber.setCreatedBy(singleFieldMasterForm.getUserId());
				pcAccNumber.setCreatedDate(new Date());
				pcAccNumber.setLastModifiedDate(new Date());
			} else // edit
			{
				pcAccNumber.setModifiedBy(singleFieldMasterForm.getUserId());
				pcAccNumber.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addAccountNoEntry(
					pcAccNumber, mode);

		}

		// ---Lohith
		else if (boName.equalsIgnoreCase("Stream")) {
			if (!originalNotChanged) {
				objEmployeeStreamBo = SingleFieldMasterHelper.getInstance()
						.populateFormToEmployeeStream(singleFieldMasterForm);
				objEmployeeStreamBo = singleFieldMasterTransaction
						.isEmployeeStreamDuplcated(objEmployeeStreamBo);

				if (objEmployeeStreamBo != null
						&& objEmployeeStreamBo.getIsActive()) {
					throw new DuplicateException();
				} else if (objEmployeeStreamBo != null
						&& !objEmployeeStreamBo.getIsActive()) {
					singleFieldMasterForm.setReactivateid(objEmployeeStreamBo
							.getId());
					throw new ReActivateException();
				}

			}
			objEmployeeStreamBo = SingleFieldMasterHelper.getInstance()
					.populateFormToEmployeeStream(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				objEmployeeStreamBo.setCreatedDate(new Date());
				objEmployeeStreamBo.setLastModifiedDate(new Date());
				objEmployeeStreamBo.setModifiedBy(singleFieldMasterForm
						.getUserId());
				objEmployeeStreamBo.setCreatedBy(singleFieldMasterForm
						.getUserId());
			} else // edit
			{
				objEmployeeStreamBo.setModifiedBy(singleFieldMasterForm
						.getUserId());
				objEmployeeStreamBo.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmployeeStream(
					objEmployeeStreamBo, mode);

		} else if (boName.equalsIgnoreCase("EmployeeWorkLocation")) {
			if (!originalNotChanged) {
				objEWLBO = SingleFieldMasterHelper.getInstance()
						.populateFormToEmployeeWorkLocation(
								singleFieldMasterForm);
				objEWLBO = singleFieldMasterTransaction
						.isEmployeeWorkLocationDuplcated(objEWLBO);

				if (objEWLBO != null && objEWLBO.getIsActive()) {
					throw new DuplicateException();
				} else if (objEWLBO != null && !objEWLBO.getIsActive()) {
					singleFieldMasterForm.setReactivateid(objEWLBO.getId());
					throw new ReActivateException();
				}

			}
			objEWLBO = SingleFieldMasterHelper.getInstance()
					.populateFormToEmployeeWorkLocation(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				objEWLBO.setCreatedDate(new Date());
				objEWLBO.setLastModifiedDate(new Date());
				objEWLBO.setModifiedBy(singleFieldMasterForm.getUserId());
				objEWLBO.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				objEWLBO.setModifiedBy(singleFieldMasterForm.getUserId());
				objEWLBO.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmployeeWorkLocation(
					objEWLBO, mode);

		} else if (boName.equalsIgnoreCase("EmployeeType")) {
			if (!originalNotChanged) {
				objETypeBO = SingleFieldMasterHelper.getInstance()
						.populateFormToEmployeeType(singleFieldMasterForm);
				objETypeBO = singleFieldMasterTransaction
						.isEmployeeTypeDuplcated(objETypeBO);

				if (objETypeBO != null && objETypeBO.getIsActive()) {
					throw new DuplicateException();
				} else if (objETypeBO != null && !objETypeBO.getIsActive()) {
					singleFieldMasterForm.setReactivateid(objETypeBO.getId());
					throw new ReActivateException();
				}

			}
			objETypeBO = SingleFieldMasterHelper.getInstance()
					.populateFormToEmployeeType(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				objETypeBO.setCreatedDate(new Date());
				objETypeBO.setLastModifiedDate(new Date());
				objETypeBO.setModifiedBy(singleFieldMasterForm.getUserId());
				objETypeBO.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				objETypeBO.setModifiedBy(singleFieldMasterForm.getUserId());
				objETypeBO.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addEmployeeType(
					objETypeBO, mode);

		}
		/**
		 * Add entries in the DB for all single field entries in the Exams
		 * module
		 */
		else if (boName.contains("_Exam_")) {

			String className = ExamGenImpl.getInstance().convertBOToClassName(
					boName);

			ExamGenBO examGenBO = null;
			if (!originalNotChanged) {
				examGenBO = SingleFieldMasterHelper.getInstance()
						.populateFormToExamGenBO(singleFieldMasterForm);
				examGenBO = singleFieldMasterTransaction.isExamGenBODuplicated(
						examGenBO, className);
				if (examGenBO != null) {
					// object is present
					if (examGenBO.getIsActive()) {
						// if active its duplicate else we need to reactivate it
						throw new DuplicateException();
					} else {
						singleFieldMasterForm
								.setReactivateid(examGenBO.getId());
						throw new ReActivateException();
					}
				}

			}
			examGenBO = SingleFieldMasterHelper.getInstance()
					.populateFormToExamGenBO(singleFieldMasterForm);
			ExamGenBO egBO = new ExamGenBO();
			egBO.setName(examGenBO.getName());
			egBO.setModifiedBy(singleFieldMasterForm.getUserId());
			egBO.setLastModifiedDate(new Date());
			egBO.setIsActive(true);
			boolean add_flag = false;
			if ("Add".equalsIgnoreCase(mode)) {
				egBO.setCreatedBy(singleFieldMasterForm.getUserId());
				egBO.setCreatedDate(new Date());
				add_flag = true;
			} else // edit
			{
				egBO.setId(examGenBO.getId());

			}
			isAdded = singleFieldMasterTransaction.addUpdateExamGenBO(egBO,
					className, add_flag);
		}else if (boName.equalsIgnoreCase("CharacterAndConduct")) {
			if (!originalNotChanged) {
				characterAndConduct = SingleFieldMasterHelper.getInstance()
						.populateFormToCharacterAndConduct(singleFieldMasterForm);
				characterAndConduct = singleFieldMasterTransaction
						.isCharacterAndConductDuplcated(characterAndConduct);
				if (characterAndConduct != null && characterAndConduct.getIsActive()) {
					throw new DuplicateException();
				} else if (characterAndConduct != null && !characterAndConduct.getIsActive()) {
					singleFieldMasterForm.setReactivateid(characterAndConduct.getId());
					throw new ReActivateException();
				}

			}
			characterAndConduct = SingleFieldMasterHelper.getInstance()
					.populateFormToCharacterAndConduct(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				characterAndConduct.setCreatedDate(new Date());
				characterAndConduct.setLastModifiedDate(new Date());
				characterAndConduct.setModifiedBy(singleFieldMasterForm.getUserId());
				characterAndConduct.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				characterAndConduct.setModifiedBy(singleFieldMasterForm.getUserId());
				characterAndConduct.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addCharacterAndConduct(characterAndConduct, mode);

		}else if (boName.equalsIgnoreCase("SubjectAreaBO")){
			if(!originalNotChanged){
				subjectAreaBO = SingleFieldMasterHelper.getInstance().populateFormToSubjectArea(singleFieldMasterForm);
				subjectAreaBO = singleFieldMasterTransaction.isSubjectAreaDuplicated(subjectAreaBO);
			if(subjectAreaBO !=null && subjectAreaBO.getIsActive()){
					throw new DuplicateException();
				}else if (subjectAreaBO !=null && !subjectAreaBO.getIsActive()){
					singleFieldMasterForm.setReactivateid(subjectAreaBO.getId());
					throw new ReActivateException();
				}
			}
			subjectAreaBO = SingleFieldMasterHelper.getInstance().populateFormToSubjectArea(singleFieldMasterForm);
			if("Add".equalsIgnoreCase(mode)){
				subjectAreaBO.setCreatedBy(singleFieldMasterForm.getUserId());
				subjectAreaBO.setCreatedDate(new Date());
				subjectAreaBO.setModifiedBy(singleFieldMasterForm.getUserId());
				subjectAreaBO.setLastModifiedDate(new Date());
			}else //edit
			{
				subjectAreaBO.setModifiedBy(singleFieldMasterForm.getUserId());
				subjectAreaBO.setLastModifiedDate(new Date());
			}
			isAdded = singleFieldMasterTransaction.addSubjectArea(subjectAreaBO, mode);
		}
		else if (boName.equalsIgnoreCase("EmpJobTitle")){
			if(!originalNotChanged){
				empJobTitle = SingleFieldMasterHelper.getInstance().populateFormToEmpJobTitle(singleFieldMasterForm);
				empJobTitle = singleFieldMasterTransaction.isEmpJobTitleDuplicated(empJobTitle);
			if(empJobTitle !=null && empJobTitle.getIsActive()){
					throw new DuplicateException();
				}else if(empJobTitle !=null && !empJobTitle.getIsActive()){
					singleFieldMasterForm.setReactivateid(empJobTitle.getId());
					throw new ReActivateException();
				}
			}
			empJobTitle = SingleFieldMasterHelper.getInstance().populateFormToEmpJobTitle(singleFieldMasterForm);
			if("Add".equalsIgnoreCase(mode)){
				empJobTitle.setCreatedBy(singleFieldMasterForm.getUserId());
				empJobTitle.setCreatedDate(new Date());
				empJobTitle.setModifiedBy(singleFieldMasterForm.getUserId());
				empJobTitle.setLastModifiedDate(new Date());
			}else //edit
			{
				empJobTitle.setModifiedBy(singleFieldMasterForm.getUserId());
				empJobTitle.setLastModifiedDate(new Date());
			}
			isAdded = singleFieldMasterTransaction.addEmpJobTitle(empJobTitle, mode);
		}
		else if (boName.equalsIgnoreCase("ApplicationStatus")){
			if(!originalNotChanged){
				applicationStatus = SingleFieldMasterHelper.getInstance().populateFormToApplicationStatus(singleFieldMasterForm);
				applicationStatus = singleFieldMasterTransaction.isApplicationStatusDuplicated(applicationStatus);
				if(applicationStatus !=null && applicationStatus.getIsActive()){
					throw new DuplicateException();
				}else if(applicationStatus!=null && !applicationStatus.getIsActive()){
					singleFieldMasterForm.setReactivateid(applicationStatus.getId());
					throw new ReActivateException();
				}
			}
			applicationStatus = SingleFieldMasterHelper.getInstance().populateFormToApplicationStatus(singleFieldMasterForm);
			if("Add".equalsIgnoreCase(mode)){
				applicationStatus.setCreatedBy(singleFieldMasterForm.getUserId());
				applicationStatus.setCreatedDate(new Date());
				applicationStatus.setModifiedBy(singleFieldMasterForm.getUserId());
				applicationStatus.setLastModifiedDate(new Date());
			}else //edit
			{
				applicationStatus.setModifiedBy(singleFieldMasterForm.getUserId());
				applicationStatus.setLastModifiedDate(new Date());
			}
			isAdded = singleFieldMasterTransaction.addApplicationStatus(applicationStatus, mode);
		}
		else if (boName.equalsIgnoreCase("InvCampus")) {
			if (!originalNotChanged) {
				invCampus = SingleFieldMasterHelper.getInstance()
						.populateFormToInvCampus(singleFieldMasterForm);
				invCampus = singleFieldMasterTransaction.isInvCampusDuplcated(invCampus);
				if (invCampus != null && invCampus.getIsActive() != null
						&& invCampus.getIsActive()) {
					throw new DuplicateException();
				} else if (invCampus != null && invCampus.getIsActive() != null
						&& !invCampus.getIsActive()) {
					singleFieldMasterForm.setReactivateid(invCampus.getId());
					throw new ReActivateException();
				}

			}
			invCampus = SingleFieldMasterHelper.getInstance()
					.populateFormToInvCampus(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				invCampus.setModifiedBy(singleFieldMasterForm.getUserId());
				invCampus.setCreatedBy(singleFieldMasterForm.getUserId());
				invCampus.setCreatedDate(new Date());
				invCampus.setLastModifiedDate(new Date());
			} else // edit
			{
				invCampus.setModifiedBy(singleFieldMasterForm.getUserId());
				invCampus.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addInvCampus(invCampus, mode);

		}else if (boName.equalsIgnoreCase("CCGroup")){
			if(!originalNotChanged){
				ccGroup = SingleFieldMasterHelper.getInstance().populateFormToccGroup(singleFieldMasterForm);
				ccGroup = (CCGroup)singleFieldMasterTransaction.isDuplicated(CCGroup.class,ccGroup.getName());
				if(ccGroup !=null && ccGroup.getIsActive()){
					throw new DuplicateException();
				}else if(ccGroup!=null && !ccGroup.getIsActive()){
					singleFieldMasterForm.setReactivateid(ccGroup.getId());
					throw new ReActivateException();
				}
			}
			ccGroup = SingleFieldMasterHelper.getInstance().populateFormToccGroup(singleFieldMasterForm);
			if("Add".equalsIgnoreCase(mode)){
				ccGroup.setCreatedBy(singleFieldMasterForm.getUserId());
				ccGroup.setCreatedDate(new Date());
				ccGroup.setModifiedBy(singleFieldMasterForm.getUserId());
				ccGroup.setLastModifiedDate(new Date());
			}else //edit
			{
				ccGroup.setModifiedBy(singleFieldMasterForm.getUserId());
				ccGroup.setLastModifiedDate(new Date());
			}
			isAdded = singleFieldMasterTransaction.addMaster(ccGroup, mode);
		}
		else if (boName.equalsIgnoreCase("InvCompany")) {
			if (!originalNotChanged) {
				invCompany = SingleFieldMasterHelper.getInstance()
						.populateFormToInvCompany(singleFieldMasterForm);
				invCompany = singleFieldMasterTransaction.isInvCompanyDuplicated(invCompany);
				if (invCompany != null && invCompany.getIsActive() != null
						&& invCompany.getIsActive()) {
					throw new DuplicateException();
				} else if (invCompany != null && invCompany.getIsActive() != null
						&& !invCompany.getIsActive()) {
					singleFieldMasterForm.setReactivateid(invCompany.getId());
					throw new ReActivateException();
				}

			}
			invCompany = SingleFieldMasterHelper.getInstance()
					.populateFormToInvCompany(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				invCompany.setModifiedBy(singleFieldMasterForm.getUserId());
				invCompany.setCreatedBy(singleFieldMasterForm.getUserId());
				invCompany.setCreatedDate(new Date());
				invCompany.setLastModifiedDate(new Date());
			} else // edit
			{
				invCompany.setModifiedBy(singleFieldMasterForm.getUserId());
				invCompany.setLastModifiedDate(new Date());

			}

			isAdded = singleFieldMasterTransaction.addInvCompany(invCompany, mode);

		} else if (boName.equalsIgnoreCase("ApplicantFeedback")) {
			if (!originalNotChanged) {
				applicantFeedback = SingleFieldMasterHelper.getInstance()
						.populateFormToApplicantFeedback(singleFieldMasterForm);
				applicantFeedback = singleFieldMasterTransaction
						.isApplicantFeedbackDuplcated(applicantFeedback);
				if (applicantFeedback != null && applicantFeedback.getIsActive()) {
					throw new DuplicateException();
				} else if (applicantFeedback != null && !applicantFeedback.getIsActive()) {
					singleFieldMasterForm.setReactivateid(applicantFeedback.getId());
					throw new ReActivateException();
				}

			}
			applicantFeedback = SingleFieldMasterHelper.getInstance()
			.populateFormToApplicantFeedback(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				applicantFeedback.setCreatedDate(new Date());
				applicantFeedback.setLastModifiedDate(new Date());
				applicantFeedback.setModifiedBy(singleFieldMasterForm.getUserId());
				applicantFeedback.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				applicantFeedback.setLastModifiedDate(new Date());
				applicantFeedback.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addApplicantFeedback(
					applicantFeedback, mode);

		}else if (boName.equalsIgnoreCase("Discipline")) {
			if (!originalNotChanged) {
				disciplineBo = SingleFieldMasterHelper.getInstance()
						.populateFormToDisciplineBo(singleFieldMasterForm);
				disciplineBo = singleFieldMasterTransaction
						.isDisciplineDuplcated(disciplineBo);
				if (disciplineBo != null && disciplineBo.getIsActive()) {
					throw new DuplicateException();
				} else if (disciplineBo != null && !disciplineBo.getIsActive()) {
					singleFieldMasterForm.setReactivateid(disciplineBo.getId());
					throw new ReActivateException();
				}

			}
			disciplineBo = SingleFieldMasterHelper.getInstance()
			.populateFormToDisciplineBo(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				disciplineBo.setCreatedDate(new Date());
				disciplineBo.setLastModifiedDate(new Date());
				disciplineBo.setModifiedBy(singleFieldMasterForm.getUserId());
				disciplineBo.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				disciplineBo.setLastModifiedDate(new Date());
				disciplineBo.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addDisciplineDetails(
					disciplineBo, mode);

		}else if (boName.equalsIgnoreCase("PhdResearchPublication")) {
			if (!originalNotChanged) {
				researchPublication = SingleFieldMasterHelper.getInstance()
						.populateFormToPhdresearchPublication(singleFieldMasterForm);
				researchPublication = singleFieldMasterTransaction
						.isResearchPublicationDuplcated(researchPublication);
				if (researchPublication != null && researchPublication.getIsActive()) {
					throw new DuplicateException();
				} else if (researchPublication != null && !researchPublication.getIsActive()) {
					singleFieldMasterForm.setReactivateid(researchPublication.getId());
					throw new ReActivateException();
				}

			}
			researchPublication = SingleFieldMasterHelper.getInstance()
			.populateFormToPhdresearchPublication(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				researchPublication.setCreatedDate(new Date());
				researchPublication.setLastModifiedDate(new Date());
				researchPublication.setModifiedBy(singleFieldMasterForm.getUserId());
				researchPublication.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				researchPublication.setLastModifiedDate(new Date());
				researchPublication.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addResearchPublication(
					researchPublication, mode);

		}else if (boName.equalsIgnoreCase("Location")) {
			if (!originalNotChanged) {
				locationBo = SingleFieldMasterHelper.getInstance()
						.populateFormToLocationBo(singleFieldMasterForm);
				locationBo = singleFieldMasterTransaction
						.isLocationDuplcated(locationBo);
				if (locationBo != null && locationBo.getIsActive()) {
					throw new DuplicateException();
				} else if (locationBo != null && !locationBo.getIsActive()) {
					singleFieldMasterForm.setReactivateid(locationBo.getId());
					throw new ReActivateException();
				}

			}
			locationBo = SingleFieldMasterHelper.getInstance()
			.populateFormToLocationBo(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				locationBo.setCreatedDate(new Date());
				locationBo.setLastModifiedDate(new Date());
				locationBo.setModifiedBy(singleFieldMasterForm.getUserId());
				locationBo.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				locationBo.setLastModifiedDate(new Date());
				locationBo.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addLocationDetails(
					locationBo, mode);

		}/*else if (boName.equalsIgnoreCase("FineCategory")) {
			if (!originalNotChanged) {
				fineCategoryBo = SingleFieldMasterHelper.getInstance()
						.populateFormToFineCategoryBo(singleFieldMasterForm);
				fineCategoryBo = singleFieldMasterTransaction
						.isFineCategoryDuplcated(fineCategoryBo);
				if (fineCategoryBo != null && fineCategoryBo.getIsActive()) {
					throw new DuplicateException();
				} else if (fineCategoryBo != null && !fineCategoryBo.getIsActive()) {
					singleFieldMasterForm.setReactivateid(fineCategoryBo.getId());
					throw new ReActivateException();
				}

			}
			fineCategoryBo = SingleFieldMasterHelper.getInstance()
			.populateFormToFineCategoryBo(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				fineCategoryBo.setCreatedDate(new Date());
				fineCategoryBo.setLastModifiedDate(new Date());
				fineCategoryBo.setModifiedBy(singleFieldMasterForm.getUserId());
				fineCategoryBo.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				fineCategoryBo.setLastModifiedDate(new Date());
				fineCategoryBo.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addFineCategoryDetails(
					fineCategoryBo, mode);

		}							
		*/
		else if (boName.equalsIgnoreCase("BlockDetails")) {
			if (!originalNotChanged) {
				blocks = SingleFieldMasterHelper.getInstance()
						.populateFormToBlockDetails(singleFieldMasterForm);
				blocks = singleFieldMasterTransaction
						.isBlockDetailsDuplicated(blocks);
				if (blocks != null && blocks.getIsActive()) {
					throw new DuplicateException();
				} else if (blocks != null && !blocks.getIsActive()) {
					singleFieldMasterForm.setReactivateid(blocks.getId());
					throw new ReActivateException();
				}

			}
			blocks = SingleFieldMasterHelper.getInstance()
			.populateFormToBlockDetails(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				blocks.setCreatedDate(new Date());
				blocks.setLastModifiedDate(new Date());
				blocks.setModifiedBy(singleFieldMasterForm.getUserId());
				blocks.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				blocks.setLastModifiedDate(new Date());
				blocks.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addBlockDetails(
					blocks, mode);

		}else if (boName.equalsIgnoreCase("BookingRequirements")) {
			if (!originalNotChanged) {
				bookingRequirements = SingleFieldMasterHelper.getInstance()
						.populateFormToBookingRequirements(singleFieldMasterForm);
				bookingRequirements = singleFieldMasterTransaction
						.isBookingRequirementsDuplicated(bookingRequirements);
				if (bookingRequirements != null && bookingRequirements.getIsActive()) {
					throw new DuplicateException();
				} else if (bookingRequirements != null && !bookingRequirements.getIsActive()) {
					singleFieldMasterForm.setReactivateid(bookingRequirements.getId());
					throw new ReActivateException();
				}
			}
			bookingRequirements = SingleFieldMasterHelper.getInstance()
			.populateFormToBookingRequirements(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				bookingRequirements.setCreatedDate(new Date());
				bookingRequirements.setLastModifiedDate(new Date());
				bookingRequirements.setModifiedBy(singleFieldMasterForm.getUserId());
				bookingRequirements.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				bookingRequirements.setLastModifiedDate(new Date());
				bookingRequirements.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addBookingRequirements(
					bookingRequirements, mode);

		}else if (boName.equalsIgnoreCase("EventLocation")) {
			if (!originalNotChanged) {
				eventLocation = SingleFieldMasterHelper.getInstance()
						.populateFormToEventLocationBo(singleFieldMasterForm);
				eventLocation = singleFieldMasterTransaction
						.isEventLocationDuplcated(eventLocation);
				if (eventLocation != null && eventLocation.getIsActive()) {
					throw new DuplicateException();
				} else if (eventLocation != null && !eventLocation.getIsActive()) {
					singleFieldMasterForm.setReactivateid(eventLocation.getId());
					throw new ReActivateException();
				}

			}
			eventLocation = SingleFieldMasterHelper.getInstance()
			.populateFormToEventLocationBo(singleFieldMasterForm);

			if ("Add".equalsIgnoreCase(mode)) {
				eventLocation.setCreatedDate(new Date());
				eventLocation.setLastModifiedDate(new Date());
				eventLocation.setModifiedBy(singleFieldMasterForm.getUserId());
				eventLocation.setCreatedBy(singleFieldMasterForm.getUserId());
			} else // edit
			{
				eventLocation.setLastModifiedDate(new Date());
				eventLocation.setModifiedBy(singleFieldMasterForm.getUserId());

			}

			isAdded = singleFieldMasterTransaction.addEventLocation(
					eventLocation, mode);

		}else if(boName.equalsIgnoreCase("ClassGroup")){
			if (!originalNotChanged) {
				classGroup=SingleFieldMasterHelper.getInstance().populateFormToClassGroupBO(singleFieldMasterForm);
				classGroup=singleFieldMasterTransaction.isClassGroupDuplicated(classGroup);
				if(classGroup!=null){
					throw new DuplicateException();
				}
			}
				classGroup=SingleFieldMasterHelper.getInstance().populateFormToClassGroupBO(singleFieldMasterForm);
				if ("Add".equalsIgnoreCase(mode)){
					classGroup.setCreatedBy(singleFieldMasterForm.getUserId());
					classGroup.setModifiedBy(singleFieldMasterForm.getUserId());
					classGroup.setCreatedDate(new Date());
					classGroup.setLastModifiedDate(new Date());
					
				}else
				{
					classGroup.setModifiedBy(singleFieldMasterForm.getUserId());
					classGroup.setLastModifiedDate(new Date());
				}
				isAdded = singleFieldMasterTransaction.addClassGroupBO(classGroup,mode);
				
		}else if(boName.equalsIgnoreCase("ExamInviligatorExemption")){
			if (!originalNotChanged) {
				examInviligatorExemption=SingleFieldMasterHelper.getInstance().populateFormToExamInviligatorExemptionBO(singleFieldMasterForm);
				examInviligatorExemption=singleFieldMasterTransaction.isExamInviligatorExemptionDuplicated(examInviligatorExemption);
				if(examInviligatorExemption!=null){
					throw new DuplicateException();
				}
			}
			examInviligatorExemption=SingleFieldMasterHelper.getInstance().populateFormToExamInviligatorExemptionBO(singleFieldMasterForm);
				if ("Add".equalsIgnoreCase(mode)){
					examInviligatorExemption.setCreatedBy(singleFieldMasterForm.getUserId());
					examInviligatorExemption.setModifiedBy(singleFieldMasterForm.getUserId());
					examInviligatorExemption.setCreatedDate(new Date());
					examInviligatorExemption.setLastModifiedDate(new Date());
					
				}else
				{
					examInviligatorExemption.setModifiedBy(singleFieldMasterForm.getUserId());
					examInviligatorExemption.setLastModifiedDate(new Date());
				}
				isAdded = singleFieldMasterTransaction.addExamInviligatorExemptionBO(examInviligatorExemption,mode);
		}
		else if(boName.equalsIgnoreCase("SubjectCodeGroup")){
			if (!originalNotChanged) {
				subjectCodeGroup=SingleFieldMasterHelper.getInstance().populateFormToSubjectCodeGroup(singleFieldMasterForm);
				subjectCodeGroup=singleFieldMasterTransaction.isStudentCodeGroupDuplicated(subjectCodeGroup);
				if(subjectCodeGroup!=null){
					throw new DuplicateException();
				}
			}
			subjectCodeGroup=SingleFieldMasterHelper.getInstance().populateFormToSubjectCodeGroup(singleFieldMasterForm);
				if ("Add".equalsIgnoreCase(mode)){
					subjectCodeGroup.setCreatedBy(singleFieldMasterForm.getUserId());
					subjectCodeGroup.setModifiedBy(singleFieldMasterForm.getUserId());
					subjectCodeGroup.setCreatedDate(new Date());
					subjectCodeGroup.setLastModifiedDate(new Date());
					
				}else
				{
					subjectCodeGroup.setModifiedBy(singleFieldMasterForm.getUserId());
					subjectCodeGroup.setLastModifiedDate(new Date());
				}
				isAdded = singleFieldMasterTransaction.addStudentCodeGroup(subjectCodeGroup,mode);
		}else if(boName.equalsIgnoreCase("EmployeeSubject")){
			if (!originalNotChanged) {
				empSubject=SingleFieldMasterHelper.getInstance().populateFormToEmployeeSubject(singleFieldMasterForm);
				empSubject=singleFieldMasterTransaction.isEmployeeSubjectDuplicated(empSubject);
				if(empSubject!=null && empSubject.getIsActive()){
					throw new DuplicateException();
				}else if(empSubject!=null && !empSubject.getIsActive()){
					singleFieldMasterForm.setReactivateid(empSubject
							.getId());
					throw new ReActivateException();
				}
			}
			empSubject=SingleFieldMasterHelper.getInstance().populateFormToEmployeeSubject(singleFieldMasterForm);
				if ("Add".equalsIgnoreCase(mode)){
					empSubject.setCreatedBy(singleFieldMasterForm.getUserId());
					empSubject.setModifiedBy(singleFieldMasterForm.getUserId());
					empSubject.setCreatedDate(new Date());
					empSubject.setLastModifiedDate(new Date());
					
				}else
				{
					empSubject.setModifiedBy(singleFieldMasterForm.getUserId());
					empSubject.setLastModifiedDate(new Date());
				}
				isAdded = singleFieldMasterTransaction.addEmployeeSubject(empSubject,mode);
		}else if(boName.equalsIgnoreCase("Services")){
			if (!originalNotChanged) {
				services=SingleFieldMasterHelper.getInstance().populateFormToServices(singleFieldMasterForm);
				services=singleFieldMasterTransaction.isServicesDuplicated(services);
				if(services!=null && services.getIsActive()){
					throw new DuplicateException();
				}else if(services!=null && !services.getIsActive()){
					singleFieldMasterForm.setReactivateid(services.getId());
					throw new ReActivateException();
				}
			}
			services=SingleFieldMasterHelper.getInstance().populateFormToServices(singleFieldMasterForm);
				if ("Add".equalsIgnoreCase(mode)){
					services.setCreatedBy(singleFieldMasterForm.getUserId());
					services.setModifiedBy(singleFieldMasterForm.getUserId());
					services.setCreatedDate(new Date());
					services.setLastModifiedDate(new Date());
				}else{
					services.setModifiedBy(singleFieldMasterForm.getUserId());
					services.setLastModifiedDate(new Date());
				}
				isAdded = singleFieldMasterTransaction.addServices(services,mode);
		}
		else if (boName.equalsIgnoreCase(" ")){
			
		}
		log.error("ending of addSingleFieldMaster method in SingleFieldMasterHandler");
		return isAdded;
	}

	/**
	 * delete method for single field master
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteSingleFieldMaster(int id, Boolean activate,
			String boName, SingleFieldMasterForm singleFieldMasterForm)
			throws Exception {
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl
				.getInstance();
		boolean result = singleFieldMasterTransaction.deleteSingleFieldMaster(
				id, activate, boName, singleFieldMasterForm);
		log.error("ending of deleteSingleFieldMaster method in SingleFieldMasterHandler");
		return result;
	}

}
