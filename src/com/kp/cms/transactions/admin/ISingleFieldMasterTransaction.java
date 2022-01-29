package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplicationStatus;
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
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.LocationBo;
import com.kp.cms.bo.phd.PhdResearchPublication;
import com.kp.cms.forms.admin.SingleFieldMasterForm;

public interface ISingleFieldMasterTransaction {
	public List<Caste> getCasteMasterFields() throws Exception;

	public List<Country> getCountryMasterFields() throws Exception;

	public Country isCountryDuplcated(Country oldcountry) throws Exception;

	public Caste isCastDuplcated(Caste oldcaste) throws Exception;

	public boolean addCountry(Country country, String mode) throws Exception;

	public boolean addCaste(Caste country, String mode) throws Exception;

	public boolean deleteSingleFieldMaster(int id, Boolean activate,
			String boName, SingleFieldMasterForm singleFieldMasterForm)
			throws Exception;

	public List<InterviewStatus> getAdmissionStatusFields() throws Exception;

	public List<CourseScheme> getCourseSchemeFields() throws Exception;

	public List<Occupation> getOccupationFields() throws Exception;

	public List<MotherTongue> getMotherTongueFields() throws Exception;

	public List<Prerequisite> getPrerequisiteFields() throws Exception;

	public InterviewStatus isAdmissionStatusDuplcated(
			InterviewStatus oldadmissionStatus) throws Exception;

	public boolean addAdmissionStatus(InterviewStatus admissionStatus,
			String mode) throws Exception;

	public boolean addCourseScheme(CourseScheme courseScheme, String mode)
			throws Exception;

	public CourseScheme isCourseSchemeDuplcated(CourseScheme oldcourseScheme)
			throws Exception;

	public boolean addPrerequisite(Prerequisite prerequisite, String mode)
			throws Exception;

	public Prerequisite isPrerequisiteDuplcated(Prerequisite oldprerequisite)
			throws Exception;

	public boolean addMotherTongue(MotherTongue motherTongue, String mode)
			throws Exception;

	public MotherTongue isMotherTongueDuplcated(MotherTongue oldmotherTongue)
			throws Exception;

	public boolean addOccupation(Occupation occupation, String mode)
			throws Exception;

	public Occupation isOccupationDuplcated(Occupation oldoccupation)
			throws Exception;

	public List<DocType> getDocTypeFields() throws Exception;

	public List<Region> getRegionFields() throws Exception;

	public List<ResidentCategory> getResidentCategoryFields() throws Exception;

	public List<MeritSet> getMeritSetFields() throws Exception;

	public DocType isDocTypeDuplcated(DocType olddocType) throws Exception;

	public boolean addDocType(DocType docType, String mode) throws Exception;

	public Region isRegionDuplcated(Region oldregion) throws Exception;

	public boolean addRegion(Region region, String mode) throws Exception;

	public ResidentCategory isResidentCategoryDuplcated(
			ResidentCategory oldresidentCategory) throws Exception;

	public boolean addResidentCategory(ResidentCategory residentCategory,
			String mode) throws Exception;

	public MeritSet isMeritSetDuplcated(MeritSet oldmeritSet) throws Exception;

	public boolean addMeritSet(MeritSet meritSet, String mode) throws Exception;

	public List<Religion> getReligionFields() throws Exception;

	public List<FeePaymentMode> getFeePaymentModeFields() throws Exception;

	public List<ProgramType> getProgramTypeFields() throws Exception;

	public boolean addReligion(Religion religion, String mode) throws Exception;

	public Religion isReligionDuplcated(Religion oldReligion) throws Exception;

	public boolean addFeePaymentMode(FeePaymentMode feePaymentMode, String mode)
			throws Exception;

	public FeePaymentMode isFeePaymentModeDuplcated(
			FeePaymentMode oldFeePaymentMode) throws Exception;

	public boolean addProgramType(ProgramType programType, String mode)
			throws Exception;

	public ProgramType isProgramTypeDuplcated(ProgramType oldprogramType)
			throws Exception;

	public List<University> getUniversityFields() throws Exception;

	public List<Department> getDepartmentFields() throws Exception;

	public Department isDepartmentDuplcated(Department oldDepartment)
			throws Exception;

	public boolean addDepartment(Department department, String mode)
			throws Exception;

	public University isUniversityDuplcated(University oldUniversity)
			throws Exception;

	public boolean addUniversity(University university, String mode)
			throws Exception;

	public Roles isRolesDuplcated(Roles oldRoles) throws Exception;

	public boolean addRoles(Roles roles, String mode) throws Exception;

	public List<Roles> getRolesFields() throws Exception;

	public List<Designation> getDesignationFields() throws Exception;

	public Designation isDesignationDuplcated(Designation oldDesignation)
			throws Exception;

	public boolean addDesignation(Designation designation, String mode)
			throws Exception;

	public EmployeeCategory isEmployeeCategoryDuplcated(
			EmployeeCategory oldEmployeeCategory) throws Exception;

	public boolean addEmployeeCategory(EmployeeCategory employeeCategory,
			String mode) throws Exception;

	public List<EmployeeCategory> getEmployeeCategoryFields() throws Exception;

	public List<LeaveType> getLeaveTypeFields() throws Exception;

	public LeaveType isLeaveTypeDuplcated(LeaveType oldLeaveType)
			throws Exception;

	public boolean addLeaveType(LeaveType leaveType, String mode)
			throws Exception;

	public Nationality isNationalityDuplcated(Nationality oldNationality)
			throws Exception;

	public boolean addNationality(Nationality nationality, String mode)
			throws Exception;

	public List<Nationality> getNationalityFields() throws Exception;

	public Income isIncomeDuplcated(Income oldIncome) throws Exception;

	public boolean addIncome(Income income, String mode) throws Exception;

	public List<Income> getIncomeFields() throws Exception;

	public HlFacility isHlFacilityDuplcated(HlFacility oldHlFacility)
			throws Exception;

	public List<HlFacility> getHlFacilityFields() throws Exception;

	public boolean addHlFacility(HlFacility hlFacility, String mode)
			throws Exception;

	public List<HlComplaintType> getHlComplaintTypeFields() throws Exception;

	public HlComplaintType isHlComplaintTypeDuplcated(
			HlComplaintType oldHlComplaintType) throws Exception;

	public boolean addHlComplaintType(HlComplaintType hlComplaintType,
			String mode) throws Exception;

	public boolean addHlLeaveType(HlLeaveType hlHlLeaveType, String mode)
			throws Exception;

	public HlLeaveType isHlLeaveTypeDuplcated(HlLeaveType oldHlLeaveType)
			throws Exception;

	public List<HlLeaveType> getHlLeaveTypeFields() throws Exception;

	public InvUom isInvUomDuplcated(InvUom oldinvUom) throws Exception;

	public boolean addInvUom(InvUom invUom, String mode) throws Exception;

	public List<InvUom> getUOMFields() throws Exception;

	public List<InvLocation> getInventoryLocations() throws Exception;

	public List<InvItemCategory> getItemCategory() throws Exception;

	public InvLocation isInvLocationDuplcated(InvLocation oldLocation)
			throws Exception;

	public InvItemCategory isInvItemCategoryDuplcated(
			InvItemCategory olditemCatg) throws Exception;

	public boolean addInvLocation(InvLocation invLocation, String mode)
			throws Exception;

	public boolean addInvItemCategory(InvItemCategory invItemCategory,
			String mode) throws Exception;

	public EmpFunctionalArea isFunctionalAreaDuplicated(
			EmpFunctionalArea oldArea) throws Exception;

	public boolean addFunctionalArea(EmpFunctionalArea functionalArea,
			String mode) throws Exception;

	public List<EmpFunctionalArea> getFunctionalArea() throws Exception;

	public EmpLeaveType isEmpLeaveTypeDuplicated(EmpLeaveType oldLeaveType)
			throws Exception;

	public boolean addEmpLeaveType(EmpLeaveType empLeaveType, String mode)
			throws Exception;

	public List<EmpLeaveType> getEmployeeLeaveType() throws Exception;

	public EmpQualificationLevel isEmpQualificationDuplicated(
			EmpQualificationLevel oldEmpQualification) throws Exception;

	public boolean addEmpQualification(
			EmpQualificationLevel empQualificationLevel, String mode)
			throws Exception;

	public List<EmpQualificationLevel> getEmpQualidication() throws Exception;

	public EmpIndustryType isEmpIndustryTypeDuplicated(
			EmpIndustryType oldEmpIndustryType) throws Exception;

	public boolean addEmpIndustryType(EmpIndustryType empIndustryType,
			String mode) throws Exception;

	public List<EmpIndustryType> getIndustryType() throws Exception;

	public EmpAgeofRetirement isAgeOfRetirementExists(
			EmpAgeofRetirement oldageAgeofRetirement) throws Exception;

	public boolean addEmpAgeofRetirement(EmpAgeofRetirement ageofRetirement,
			String mode) throws Exception;

	public List<EmpAgeofRetirement> getEmpAgeofRetirement() throws Exception;

	public EmpAllowance isEmpAllowanceTypeDuplicated(EmpAllowance olEmpAllowance)
			throws Exception;

	public boolean addEmpAllowances(EmpAllowance allowance, String mode)
			throws Exception;

	public List<EmpAllowance> getEmpAllowances() throws Exception;

	public List<EmpJobType> getEmpJobType() throws Exception;

	public EmpJobType isEmpJobTypeDuplicated(EmpJobType olEmpjobType)
			throws Exception;

	public boolean addEmpJobType(EmpJobType empJobType, String mode)
			throws Exception;

	public List<EmpWorkType> getEmpWorkType() throws Exception;

	public EmpWorkType isEmpWorkTypeDuplicated(EmpWorkType olEmpWorkType)
			throws Exception;

	public boolean addEmpWorkType(EmpWorkType empWorkType, String mode)
			throws Exception;

	public List<PcBankAccNumber> getBankAccNo() throws Exception;

	public PcBankAccNumber isPcBankAccNumberDuplicated(
			PcBankAccNumber oldPcBankAccNumber) throws Exception;

	public boolean addAccountNoEntry(PcBankAccNumber pcAccNumber, String mode)
			throws Exception;

	/**
	 * 
	 * Exam
	 */
	public List<Object> getListExamGenBO(String className) throws Exception;

	public ExamGenBO isExamGenBODuplicated(ExamGenBO oldExamGenBO,
			String className) throws Exception;

	public boolean addUpdateExamGenBO(ExamGenBO examGenBO, String className,
			boolean add) throws Exception;

	/**
	 * 
	 * Stream By lohith
	 */
	public List<EmployeeStreamBO> getEmployeeStream() throws Exception;

	public EmployeeStreamBO isEmployeeStreamDuplcated(
			EmployeeStreamBO objEmployeeStreamBo) throws Exception;

	public boolean addEmployeeStream(EmployeeStreamBO objEmployeeStreamBo,
			String mode) throws Exception;

	public List<EmployeeWorkLocationBO> getEmployeeWorkLocationStream()
			throws Exception;

	public EmployeeWorkLocationBO isEmployeeWorkLocationDuplcated(
			EmployeeWorkLocationBO objEWLBO) throws Exception;

	public boolean addEmployeeWorkLocation(EmployeeWorkLocationBO objEWLBO,
			String mode) throws Exception;

	public List<EmployeeTypeBO> getEmployeeType() throws Exception;

	public EmployeeTypeBO isEmployeeTypeDuplcated(EmployeeTypeBO objBO)
			throws Exception;

	public boolean addEmployeeType(EmployeeTypeBO objETypeBO, String mode)
			throws Exception;

	public List<CharacterAndConduct> getCharacterAndConduct() throws Exception;

	public CharacterAndConduct isCharacterAndConductDuplcated(
			CharacterAndConduct characterAndConduct) throws Exception;

	public boolean addCharacterAndConduct(
			CharacterAndConduct characterAndConduct, String mode) throws Exception;
	
	public List<Sports> getSportsMasterFields() throws Exception;
	
	public Sports isSportsDuplcated(Sports oldsports) throws Exception;
	
	public boolean addSports(Sports sports, String mode) throws Exception;

	public List<SubjectAreaBO> getSubjectArea()throws Exception;

	public SubjectAreaBO isSubjectAreaDuplicated(SubjectAreaBO subjectAreaBO)throws Exception;

	public boolean addSubjectArea(SubjectAreaBO subjectAreaBO, String mode)throws Exception;
	
	public EmpJobTitle isEmpJobTitleDuplicated(EmpJobTitle empJobTitle)throws Exception;
	
	public boolean addEmpJobTitle(EmpJobTitle empJobTitle, String mode)throws Exception;
	
	public List<EmpJobTitle> getEmpJobTitle()throws Exception;

	public List<ApplicationStatus> getApplicationStatus()throws Exception;

	public ApplicationStatus isApplicationStatusDuplicated(ApplicationStatus applicationStatus) throws Exception;

	public boolean addApplicationStatus(ApplicationStatus applicationStatus,String mode)throws Exception;

	public List<InvCampus> getInvCampus()throws Exception ;

	public InvCampus isInvCampusDuplcated(InvCampus invCampus) throws Exception ;

	public boolean addInvCampus(InvCampus invCampus, String mode) throws Exception ;

	public List getMasterEntryData(Class class1) throws Exception;

	public Object isDuplicated(Class class1, String name) throws Exception;

	public boolean addMaster(Object object, String mode) throws Exception;

	public Object getMasterEntryDataById(Class class1, int studentId) throws Exception;

	public List<InvCompany> getInvCompany() throws Exception;

	public InvCompany isInvCompanyDuplicated(InvCompany invCompany) throws Exception;

	public boolean addInvCompany(InvCompany invCompany, String mode) throws Exception;
	public List<ApplicantFeedback> getApplicantFeedback() throws Exception;
	public ApplicantFeedback isApplicantFeedbackDuplcated(ApplicantFeedback applicantFeedback) throws Exception;
	public boolean addApplicantFeedback(ApplicantFeedback applicantFeedback, String mode) throws Exception;
	public List<DisciplineBo> getDisciplines() throws Exception;
	public DisciplineBo isDisciplineDuplcated(DisciplineBo disciplineBo) throws Exception;
	public boolean addDisciplineDetails(DisciplineBo disciplineBo, String mode) throws Exception;
	public List<LocationBo> getLocations() throws Exception;
	public LocationBo isLocationDuplcated(LocationBo locationBo) throws Exception;
	public boolean addLocationDetails(LocationBo locationBo, String mode) throws Exception;
	/*public List<FineCategoryBo> getFineCategory() throws Exception;
	public FineCategoryBo isFineCategoryDuplcated(FineCategoryBo fineCategoryBo) throws Exception;
	public boolean addFineCategoryDetails(FineCategoryBo fineCategoryBo, String mode) throws Exception;*/

	public List<PhdResearchPublication> getResearchPublication() throws Exception;

	public PhdResearchPublication isResearchPublicationDuplcated(PhdResearchPublication researchPublication)  throws Exception;

	public boolean addResearchPublication(PhdResearchPublication researchPublication, String mode) throws Exception;
	
	public List<BlockDetails> getBlockDetails()throws Exception;
	
	public BlockDetails isBlockDetailsDuplicated(BlockDetails blocks) throws Exception;
	
	public BookingRequirements isBookingRequirementsDuplicated(BookingRequirements requirements) throws Exception;
	
	public List<BookingRequirements> getBookingRequirements()throws Exception;
	
	public boolean addBlockDetails(BlockDetails blocks, String mode) throws Exception;
	
	public boolean addBookingRequirements(BookingRequirements bookingRequirements, String mode) throws Exception;
	public EventLocation isEventLocationDuplcated(EventLocation eventLocation) throws Exception;
	public boolean addEventLocation(EventLocation eventLocation, String mode) throws Exception;
	public List<EventLocation> getEventLocation() throws Exception;

	public List<ClassGroup> getClassGroup()throws Exception;

	public ClassGroup isClassGroupDuplicated(ClassGroup classGroup)throws Exception;

	public boolean addClassGroupBO(ClassGroup classGroup, String mode)throws Exception;

	public List<ExamInviligatorExemption> getExamInviligatorExemption()throws Exception;

	public ExamInviligatorExemption isExamInviligatorExemptionDuplicated(ExamInviligatorExemption examInviligatorExemption) throws Exception;

	public boolean addExamInviligatorExemptionBO(ExamInviligatorExemption examInviligatorExemption, String mode)throws Exception;

	public List<SubjectCodeGroup> getSubjectsCodeGroup()throws Exception;

	public SubjectCodeGroup isStudentCodeGroupDuplicated(SubjectCodeGroup subjectCodeGroup)throws Exception;

	public boolean addStudentCodeGroup(SubjectCodeGroup subjectCodeGroup,String mode)throws Exception;
			
	public List<EmployeeSubject> getEmployeeSubjects()throws Exception;	

	public EmployeeSubject isEmployeeSubjectDuplicated(EmployeeSubject empSubject)throws Exception;
	
	public boolean addEmployeeSubject(EmployeeSubject empSubject,String mode)throws Exception;
	
	public List<Services> getServices()throws Exception;
	
	public Services isServicesDuplicated(Services services)throws Exception;
	
	public boolean addServices(Services services,String mode)throws Exception;
	
	public List<ExamSubjectTypeMasterBO> getSubjectTypeMasterFields() throws Exception;
	
	public ExamSubjectTypeMasterBO isSubjectTypeDuplcated(ExamSubjectTypeMasterBO oldSubjectType) throws Exception ;
	
	public boolean addSubjectType(ExamSubjectTypeMasterBO subjectType, String mode) throws Exception;
	
}
