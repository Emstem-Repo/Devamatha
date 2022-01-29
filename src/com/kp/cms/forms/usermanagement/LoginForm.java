package com.kp.cms.forms.usermanagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DistrictTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.NewsEventsTO;
import com.kp.cms.to.admin.OccupationTO;
import com.kp.cms.to.admin.ProgressCardTo;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.DisciplineAndAchivementTo;
import com.kp.cms.to.admission.OnlinePaymentRecieptsTo;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.exam.ClearanceCertificateTO;
import com.kp.cms.to.exam.ConsolidateMarksCardTO;
import com.kp.cms.to.exam.ExamMidsemRepeatTO;
import com.kp.cms.to.exam.GrievanceStatusTo;
import com.kp.cms.to.exam.GrievanceStudentTo;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.to.fee.PrintChalanTO;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;
import com.kp.cms.to.serviceLearning.ServiceLearningActivityTo;
import com.kp.cms.to.serviceLearning.ServiceLearningMarksEntryTo;
import com.kp.cms.to.timetable.TimeTableClassTo;
import com.kp.cms.to.usermanagement.LoginTransactionTo;
import com.kp.cms.transactions.admin.StudentLoginTO;

@SuppressWarnings("serial")
public class LoginForm extends BaseActionForm {
	
	private String userName;
	
	private String password;	
	
	private String description;
	
	private List<LoginTransactionTo> moduleMenusList;
	
	private String encryptedPassword;
	
	private List<NewsEventsTO> newsEventsList;
	
	private String dateOfBirth;
	private String contactMail;
	private String studentName;
	private String fatherName;
	private String motherName;
	private String className;
	//new addition
	private String currentAddress1;
	private String currentAddress2;
	private String currentCity;
	private String currentState;
	private String currentCountry;
	private String currentPincode;
	private String permanentAddress1;
	private String permanentAddress2;
	private String permanentCity;
	private String permanentState;
	private String permanentCountry;
	private String permanentPincode;
	private String nationality;
	private String bloodGroup;
	private String phNo1;
	private String phNo2;
	//....ends
	private String photoPath;
	private int agreementId;
	private String agreement;
	private int examId;
	private HallTicketTo hallTicket;
	private int studentId;
	private int termNo;
	private int examIDForMCard;
	private int marksCardClassId;
	private int semesterYearNo;
	private MarksCardTO marksCardTo;
	private int  supHallTicketexamID;
	private int supMCexamID;
	private int supMCsemesterYearNo;
	private int supMCClassId;
	private String search;
	private Integer menuCount;
	private String blockId;
	private ClearanceCertificateTO cto;
	private String description1;
	private String mobileNo;
	private int personalDateId;
	// added for challan print
	private List<FeePaymentTO> feeToList;
	private String financialYear;
	private String billNo;
	private String applnNo;
	private String challanPrintedDate;
	private String installmentReferenceNo;
	private String concessionReferenceNo;
	private String scholarshipReferenceNo;
	private String currencyCode;
	private String chalanCreatedTime;
	private String paymentMode;
	private String accwiseTotalPrintString;
	private Map<Integer, Double> accountWiseNonOptionalAmount;
	private Map<Integer, Double> accountWiseOptionalAmount;
	private Map<Integer, String> allFeeAccountMap;
	private Map<Integer, Double> fullAccountWiseTotal;
	private List<PrintChalanTO> printChalanList;
	private Boolean isSinglePrint;
	private int lastNo;
	private String verifiedBy;
	ConsolidateMarksCardTO consolidateMarksCardTO;
	private String loginType;
	private List<Integer> revaluationRegClassId;
	private List<Integer> revaluationSupClassId;
	private Map<String,Double> revalationFeeMap;
	private Map<Integer,String> revaluationSubjects;
	private boolean checkRevaluation;
	private boolean checkDD;
	private String ddNo;
	private String ddDate;
	private String amount;
	private String marksCardType;
	private String bankName;
	private String branchName;
	Map<Integer,String> revDateMap;
	Map<Integer,String> suprevDateMap;
	private String revDate;
	private boolean attendanceLogin;
	private String bankAccNo;
	private Integer notifications;
	private boolean ciaEntrys;
	private boolean supHallTicket;
	private int count;
	private List<OnlinePaymentRecieptsTo> paymentList;
	List<MarksCardTO> regularExamList;
	List<MarksCardTO> suppExamList;
	private String regularExamId;
	private String suppExamId;
	private boolean agreementAccepted;
	private String enteredDob;
	private String regNo;
	private String smartCardNo;
	private String validThruMonth;
	private String validThruYear;
	private boolean feesNotConfigured;
	private double totalFees;
	private int finId;
	private int onlinePaymentId;
	private boolean printCertificateReq;
	private String printData;
	private String msg;
	private boolean peerEvaluationLinkPresent;
	private boolean researchLinkPresent;
	private String displaySem1and2;
	private String batch;
	private Boolean participatingConvocation;
	private Boolean guestPassRequired;
	private String relationshipWithGuest;
	private int convocationId;
	private boolean convocationRelation;
	private String homePage;
	private int sapId;
	private Boolean sapRegExist;
	private String univEmailId;
	private List<LoginTransactionTo> invigilationDutyAllotmentDetails;
	private Boolean isAllotmentDetails;
	private String status;
	private String sapExamDate;
	private Boolean statusIsPass;
	private Boolean statusIsFail;
	private Boolean statusIsIsOther;
	private Boolean linkForCJC;
	private String displayLinkExamName;
	private List<StudentMarkDetailsTO> studentMarkDetailsTOList;
	private String serverDownMessage;
	private String midSemStudentId;
	private String midSemRepeatId;
	private String midSemRepeatExamId;
	private String midSemExamId;
	private String repeatExamName;
	private String midSemStudentName;
	private String midSemRepeatRegNo;
	private String midSemClassName;
	private int midSemClassId;
	private BigDecimal midSemAmount;
	private List<ExamMidsemRepeatTO> midSemRepeatList;
	private boolean midSemPrint;
	private String isFeesPaid;
	private Boolean isDownloaded;
	private BigDecimal midSemTotalAmount;
	private String dob;
	private List<Integer> subjectIdList;
	private String attemtsCompleted;
	private String attemptsCount;
	private String midSemRepeatProgram;
	private String midSemRepeatReason;
	private String midSemFatherName;
	private String midSemGender;
	private String midSemAttemptsLeft;
	private String midSemCountWords;
	private String midSemAggreagatePrint;
	private String syllabusEntryBatch;
	private String feesExemption;
	private String feeEndDate;
	private Boolean studentPhotoUpload;
	Map<Integer, String> examNameMap;
	private String semString;
	List<MarksCardTO> studentList;
	private int revClassId;
	private boolean isSupplImpAppDisplay;
	List<Integer> examIdList;
	private Boolean isGrievance;
	private List<SubjectTO>  subjectTOList;
	private String grievanceSerialNo;
	private String hodStatus;
	private String controlerStatus;
	private String remark;
	private List<GrievanceStatusTo>  grievanceStatusToList;
	private List<GrievanceStudentTo>  grievanceStudentToList;
	private List<ConsolidateMarksCardTO>  consolidateMarksCardTOList;
	private String finalGrade;
	private String finalCCPA;
	private String finalResult;
	private String finalCredit;
	private String totalMarkAwarded;
	private String totalMaxMark;
	private String courseTitle;
	private String internalExamId;
	private List<DepartmentNameEntryTo> departmentNameEntryToList;
	Map<Integer, String> programeMap;
	private String startMins;
	private String startHours;
	private List<ServiceLearningActivityTo> serviceLearningActivityToList;
	private String learningAndOutcome;
	private String overallMark;
	List<ServiceLearningMarksEntryTo>  serviceLearningMarksEntryToList;
	List<ProgrammeEntryTo> programmeEntryToList;
	private Boolean isAttendanceShortage=false;
	private PersonalDataTO personalData;
	private List<ResidentCategoryTO> residentTypes;
	private Map<Integer,String> stateMap;
	private List<NationalityTO> nationalities;
	private List<CountryTO> countries;
	private String birthState;
	private int birthCountryId;
	private int curAddrCountyId;
	private int perAddrCountyId;
	private Map<Integer,String> curAddrStateMap;
	private Map<Integer,String> perAddrStateMap;
	private Map<Integer,String> currencyMap;
	private int parentAddrCountyId;
	private int guardianAddrCountyId;
	private Map<Integer,String> parentStateMap;
	private Map<Integer,String> guardianStateMap;
	private List<DistrictTO> editPermanentDistrict;
	private List<DistrictTO> editCurrentDistrict;
	private boolean sameTempAddr;
	private List<OccupationTO> occupations;
	private List<IncomeTO> incomeList;
	private List<CurrencyTO> currencyList;
	private boolean sameParentAddr;
	private List<ReligionTO> religions;
	private List<ReligionSectionTO> subReligions;
	private boolean handicapped;
	private boolean sportsPerson;
	private Map<Integer,String> subReligionMap;
	private Map<Integer,String> subCasteMap;
	private boolean dateExpired;
	private boolean aided;
	private String nssgrade;
	private boolean nsscertificate;
	private String sports;
	private String nccgrade;
	private boolean ncccertificate;
	private String arts;
	private String sportsParticipate;
	private String artsParticipate;
	private Integer checkReligionId;
	private String recomendedBy;
	private String focusValue;
	private AdmApplnTO applicantDetails;
	private boolean remove;
	private String submitDate;
	private FormFile studentPhoto;
	private boolean isUpdated;
	
	private List<Period> periodList;
	List<Integer> commonSubList;
	List<Integer> secLanguageList;
	List<Integer> electiveList;
	List<Integer> certificateList;
	Map<Integer,Map<Integer,String>> activityMap;
	
	List<Integer> batchList;
	Map<Integer,Map<Integer,String>> batchMap;
	List<Integer> activityBatchList;
	Map<Integer,Map<Integer,String>> activityBatchMap;
	
	Map<Integer,String> attTypeMap;
	
	private Boolean isStaff;
	private String empName;
	
	private int ttClassId;
	private String finalApprove;
	
	private List<StudentLoginTO> classTos;
	private Boolean ttClassHistoryExists;
	private Boolean isMobileAccess;
	private String doj;
	private String departmentName;
	private List<StudentTO> paymentListStudent;
	private String method;
	private String refNo;
	private String productinfo;
	private String test;
	private String txnAmt;
	private String displayPage;
	private String pgiStatus;
	private Boolean isTnxStatusSuccess;
	
	private String hash;
	private String txnDate;
	private String txnid;
	private String txnRefNo;
	private String candidateRefNo;
	private String transactionRefNO;
	private Boolean paymentSuccess;
	private String admApplnId;
	private String amount1;
	private boolean isSuccess;
	private String heading;
	private boolean isDisplayText;
	private boolean photoEdited;
    private boolean isPDataEdited;
    private String type;
    private List<DisciplineAndAchivementTo> disciplineAndAchivementToList;
    private String fileName;
    private HashMap<Integer, String> secondLanguageList;
    private EdnQualificationTO ednQualification;
    private Map<Integer,String> admSubjectMap;
	private Map<Integer,String> admSubjectLangMap;
	private List<UniversityTO> universities;
	private CandidateMarkTO detailMark;
	private List<DocChecklist> docCheckList;
	private List<ProgressCardTo> progressCardList;
	private List<AdmittedThroughTO> admittedThroughList = null;
	private Map<Integer, String> admCoreMap;
    private Map<Integer, String> admComplMap;
    private Map<Integer, String> admCommonMap;
    private Map<Integer, String> admMainMap;
    private Map<Integer, String> admSubMap;
    private String patternofStudy;
	
	public boolean getIsDisplayText() {
		return isDisplayText;
	}
	public void setIsDisplayText(boolean isDisplayText) {
		this.isDisplayText = isDisplayText;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getAmount1() {
		return amount1;
	}
	public void setAmount1(String amount1) {
		this.amount1 = amount1;
	}
	public String getCandidateRefNo() {
		return candidateRefNo;
	}
	public void setCandidateRefNo(String candidateRefNo) {
		this.candidateRefNo = candidateRefNo;
	}
	
	public String getTransactionRefNO() {
		return transactionRefNO;
	}
	public void setTransactionRefNO(String transactionRefNO) {
		this.transactionRefNO = transactionRefNO;
	}
	public Boolean getPaymentSuccess() {
		return paymentSuccess;
	}
	public void setPaymentSuccess(Boolean paymentSuccess) {
		this.paymentSuccess = paymentSuccess;
	}
	public String getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(String admApplnId) {
		this.admApplnId = admApplnId;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public String getTxnid() {
		return txnid;
	}
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public Boolean getIsTnxStatusSuccess() {
		return isTnxStatusSuccess;
	}
	public void setIsTnxStatusSuccess(Boolean isTnxStatusSuccess) {
		this.isTnxStatusSuccess = isTnxStatusSuccess;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Boolean getIsMobileAccess() {
		return isMobileAccess;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public void setIsMobileAccess(Boolean isMobileAccess) {
		this.isMobileAccess = isMobileAccess;
	}
	public List<ServiceLearningActivityTo> getServiceLearningActivityToList() {
		return serviceLearningActivityToList;
	}
	public void setServiceLearningActivityToList(
			List<ServiceLearningActivityTo> serviceLearningActivityToList) {
		this.serviceLearningActivityToList = serviceLearningActivityToList;
	}
	
	public String getStartMins() {
		return startMins;
	}
	public void setStartMins(String startMins) {
		this.startMins = startMins;
	}
	public String getStartHours() {
		return startHours;
	}
	public void setStartHours(String startHours) {
		this.startHours = startHours;
	}
	public Map<Integer, String> getProgrameMap() {
		return programeMap;
	}
	public void setProgrameMap(Map<Integer, String> programeMap) {
		this.programeMap = programeMap;
	}
	public List<DepartmentNameEntryTo> getDepartmentNameEntryToList() {
		return departmentNameEntryToList;
	}
	public void setDepartmentNameEntryToList(
			List<DepartmentNameEntryTo> departmentNameEntryToList) {
		this.departmentNameEntryToList = departmentNameEntryToList;
	}
	public String getInternalExamId() {
		return internalExamId;
	}
	public void setInternalExamId(String internalExamId) {
		this.internalExamId = internalExamId;
	}

	private List<ConsolidateMarksCardTO>  consolidateMarksCardTOList1;
	public List<ConsolidateMarksCardTO> getConsolidateMarksCardTOList1() {
		return consolidateMarksCardTOList1;
	}
	public void setConsolidateMarksCardTOList1(
			List<ConsolidateMarksCardTO> consolidateMarksCardTOList1) {
		this.consolidateMarksCardTOList1 = consolidateMarksCardTOList1;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getFinalGrade() {
		return finalGrade;
	}
	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}
	public String getFinalCCPA() {
		return finalCCPA;
	}
	public void setFinalCCPA(String finalCCPA) {
		this.finalCCPA = finalCCPA;
	}
	public String getFinalResult() {
		return finalResult;
	}
	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}
	public String getFinalCredit() {
		return finalCredit;
	}
	public void setFinalCredit(String finalCredit) {
		this.finalCredit = finalCredit;
	}
	public String getTotalMarkAwarded() {
		return totalMarkAwarded;
	}
	public void setTotalMarkAwarded(String totalMarkAwarded) {
		this.totalMarkAwarded = totalMarkAwarded;
	}
	public String getTotalMaxMark() {
		return totalMaxMark;
	}
	public void setTotalMaxMark(String totalMaxMark) {
		this.totalMaxMark = totalMaxMark;
	}
	public List<ConsolidateMarksCardTO> getConsolidateMarksCardTOList() {
		return consolidateMarksCardTOList;
	}
	public void setConsolidateMarksCardTOList(
			List<ConsolidateMarksCardTO> consolidateMarksCardTOList) {
		this.consolidateMarksCardTOList = consolidateMarksCardTOList;
	}

	private String resultType;
	
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public List<GrievanceStudentTo> getGrievanceStudentToList() {
		return grievanceStudentToList;
	}
	public void setGrievanceStudentToList(
			List<GrievanceStudentTo> grievanceStudentToList) {
		this.grievanceStudentToList = grievanceStudentToList;
	}
	public void setSupplImpAppDisplay(boolean isSupplImpAppDisplay) {
		this.isSupplImpAppDisplay = isSupplImpAppDisplay;
	}
	public List<GrievanceStatusTo> getGrievanceStatusToList() {
		return grievanceStatusToList;
	}
	public void setGrievanceStatusToList(
			List<GrievanceStatusTo> grievanceStatusToList) {
		this.grievanceStatusToList = grievanceStatusToList;
	}
	public String getGrievanceSerialNo() {
		return grievanceSerialNo;
	}
	public void setGrievanceSerialNo(String grievanceSerialNo) {
		this.grievanceSerialNo = grievanceSerialNo;
	}
	public String getHodStatus() {
		return hodStatus;
	}
	public void setHodStatus(String hodStatus) {
		this.hodStatus = hodStatus;
	}
	public String getControlerStatus() {
		return controlerStatus;
	}
	public void setControlerStatus(String controlerStatus) {
		this.controlerStatus = controlerStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<SubjectTO> getSubjectTOList() {
		return subjectTOList;
	}
	public void setSubjectTOList(List<SubjectTO> subjectTOList) {
		this.subjectTOList = subjectTOList;
	}
	
	public Boolean getIsGrievance() {
		return isGrievance;
	}

	public void setIsGrievance(Boolean isGrievance) {
		this.isGrievance = isGrievance;
	}

	public List<MarksCardTO> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<MarksCardTO> studentList) {
		this.studentList = studentList;
	}

	public String getSemString() {
		return semString;
	}

	public void setSemString(String semString) {
		this.semString = semString;
	}

	//reg raghu
	private String regAppExamId;
	private String userOTP;
	
	// vinodha
	private Map<Integer, Integer> schemeMap;
	private String schemeNumber;
	private Map<Integer, String> subjectMap;
	
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}

	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}

	public Boolean getStudentPhotoUpload() {
		return studentPhotoUpload;
	}

	public void setStudentPhotoUpload(Boolean studentPhotoUpload) {
		this.studentPhotoUpload = studentPhotoUpload;
	}

	public String getSyllabusEntryBatch() {
		return syllabusEntryBatch;
	}

	public void setSyllabusEntryBatch(String syllabusEntryBatch) {
		this.syllabusEntryBatch = syllabusEntryBatch;
	}

	public String getServerDownMessage() {
		return serverDownMessage;
	}

	public void setServerDownMessage(String serverDownMessage) {
		this.serverDownMessage = serverDownMessage;
	}

	public List<StudentMarkDetailsTO> getStudentMarkDetailsTOList() {
		return studentMarkDetailsTOList;
	}

	public void setStudentMarkDetailsTOList(
			List<StudentMarkDetailsTO> studentMarkDetailsTOList) {
		this.studentMarkDetailsTOList = studentMarkDetailsTOList;
	}


	public boolean isConvocationRelation() {
		return convocationRelation;
	}

	public void setConvocationRelation(boolean convocationRelation) {
		this.convocationRelation = convocationRelation;
	}

	public int getConvocationId() {
		return convocationId;
	}

	public void setConvocationId(int convocationId) {
		this.convocationId = convocationId;
	}

	public Integer getMenuCount() {
		return menuCount;
	}

	public void setMenuCount(Integer menuCount) {
		this.menuCount = menuCount;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public LoginForm() {
		this.moduleMenusList = new ArrayList<LoginTransactionTo>();
		this.ednQualification=new EdnQualificationTO();
	}
	
	public List<NewsEventsTO> getNewsEventsList() {
		return newsEventsList;
	}

	public void setNewsEventsList(List<NewsEventsTO> newsEventsList) {
		this.newsEventsList = newsEventsList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<LoginTransactionTo> getModuleMenusList() {
		return moduleMenusList;
	}

	public void setModuleMenusList(List<LoginTransactionTo> moduleMenusList) {
		this.moduleMenusList = moduleMenusList;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	
	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the contactMail
	 */
	public String getContactMail() {
		return contactMail;
	}

	/**
	 * @param contactMail the contactMail to set
	 */
	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public String getClassName() {
		return className;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void resetFields() {
		this.userName = null;
		this.password = null;
		this.serverDownMessage=null;
	}

	public void reset1()
	{
		this.participatingConvocation=null;
		this.guestPassRequired=null;
		this.relationshipWithGuest=null;
		this.serverDownMessage=null;
	}
	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public int getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(int agreementId) {
		this.agreementId = agreementId;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public HallTicketTo getHallTicket() {
		return hallTicket;
	}

	public void setHallTicket(HallTicketTo hallTicket) {
		this.hallTicket = hallTicket;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getTermNo() {
		return termNo;
	}

	public void setTermNo(int termNo) {
		this.termNo = termNo;
	}

	public int getExamIDForMCard() {
		return examIDForMCard;
	}

	public void setExamIDForMCard(int examIDForMCard) {
		this.examIDForMCard = examIDForMCard;
	}

	public int getMarksCardClassId() {
		return marksCardClassId;
	}

	public void setMarksCardClassId(int marksCardClassId) {
		this.marksCardClassId = marksCardClassId;
	}

	public int getSemesterYearNo() {
		return semesterYearNo;
	}

	public void setSemesterYearNo(int semesterYearNo) {
		this.semesterYearNo = semesterYearNo;
	}

	public MarksCardTO getMarksCardTo() {
		return marksCardTo;
	}

	public void setMarksCardTo(MarksCardTO marksCardTo) {
		this.marksCardTo = marksCardTo;
	}

	public int getSupHallTicketexamID() {
		return supHallTicketexamID;
	}

	public void setSupHallTicketexamID(int supHallTicketexamID) {
		this.supHallTicketexamID = supHallTicketexamID;
	}

	public int getSupMCexamID() {
		return supMCexamID;
	}

	public void setSupMCexamID(int supMCexamID) {
		this.supMCexamID = supMCexamID;
	}

	public int getSupMCsemesterYearNo() {
		return supMCsemesterYearNo;
	}

	public void setSupMCsemesterYearNo(int supMCsemesterYearNo) {
		this.supMCsemesterYearNo = supMCsemesterYearNo;
	}

	public int getSupMCClassId() {
		return supMCClassId;
	}

	public void setSupMCClassId(int supMCClassId) {
		this.supMCClassId = supMCClassId;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public ClearanceCertificateTO getCto() {
		return cto;
	}

	public void setCto(ClearanceCertificateTO cto) {
		this.cto = cto;
	}

	public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setPersonalDateId(int personalDateId) {
		this.personalDateId = personalDateId;
	}

	public int getPersonalDateId() {
		return personalDateId;
	}

	public List<FeePaymentTO> getFeeToList() {
		return feeToList;
	}

	public void setFeeToList(List<FeePaymentTO> feeToList) {
		this.feeToList = feeToList;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getApplnNo() {
		return applnNo;
	}

	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}


	public String getChallanPrintedDate() {
		return challanPrintedDate;
	}

	public void setChallanPrintedDate(String challanPrintedDate) {
		this.challanPrintedDate = challanPrintedDate;
	}

	public String getInstallmentReferenceNo() {
		return installmentReferenceNo;
	}

	public void setInstallmentReferenceNo(String installmentReferenceNo) {
		this.installmentReferenceNo = installmentReferenceNo;
	}

	public String getConcessionReferenceNo() {
		return concessionReferenceNo;
	}

	public void setConcessionReferenceNo(String concessionReferenceNo) {
		this.concessionReferenceNo = concessionReferenceNo;
	}

	public String getScholarshipReferenceNo() {
		return scholarshipReferenceNo;
	}

	public void setScholarshipReferenceNo(String scholarshipReferenceNo) {
		this.scholarshipReferenceNo = scholarshipReferenceNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getChalanCreatedTime() {
		return chalanCreatedTime;
	}

	public void setChalanCreatedTime(String chalanCreatedTime) {
		this.chalanCreatedTime = chalanCreatedTime;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getAccwiseTotalPrintString() {
		return accwiseTotalPrintString;
	}

	public void setAccwiseTotalPrintString(String accwiseTotalPrintString) {
		this.accwiseTotalPrintString = accwiseTotalPrintString;
	}

	public Map<Integer, Double> getAccountWiseNonOptionalAmount() {
		return accountWiseNonOptionalAmount;
	}

	public void setAccountWiseNonOptionalAmount(
			Map<Integer, Double> accountWiseNonOptionalAmount) {
		this.accountWiseNonOptionalAmount = accountWiseNonOptionalAmount;
	}

	public Map<Integer, Double> getAccountWiseOptionalAmount() {
		return accountWiseOptionalAmount;
	}

	public void setAccountWiseOptionalAmount(
			Map<Integer, Double> accountWiseOptionalAmount) {
		this.accountWiseOptionalAmount = accountWiseOptionalAmount;
	}

	public Map<Integer, String> getAllFeeAccountMap() {
		return allFeeAccountMap;
	}

	public void setAllFeeAccountMap(Map<Integer, String> allFeeAccountMap) {
		this.allFeeAccountMap = allFeeAccountMap;
	}

	public Map<Integer, Double> getFullAccountWiseTotal() {
		return fullAccountWiseTotal;
	}

	public void setFullAccountWiseTotal(Map<Integer, Double> fullAccountWiseTotal) {
		this.fullAccountWiseTotal = fullAccountWiseTotal;
	}

	public List<PrintChalanTO> getPrintChalanList() {
		return printChalanList;
	}

	public void setPrintChalanList(List<PrintChalanTO> printChalanList) {
		this.printChalanList = printChalanList;
	}

	public Boolean getIsSinglePrint() {
		return isSinglePrint;
	}

	public void setIsSinglePrint(Boolean isSinglePrint) {
		this.isSinglePrint = isSinglePrint;
	}

	public int getLastNo() {
		return lastNo;
	}

	public void setLastNo(int lastNo) {
		this.lastNo = lastNo;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public ConsolidateMarksCardTO getConsolidateMarksCardTO() {
		return consolidateMarksCardTO;
	}

	public void setConsolidateMarksCardTO(
			ConsolidateMarksCardTO consolidateMarksCardTO) {
		this.consolidateMarksCardTO = consolidateMarksCardTO;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public List<Integer> getRevaluationRegClassId() {
		return revaluationRegClassId;
	}

	public void setRevaluationRegClassId(List<Integer> revaluationRegClassId) {
		this.revaluationRegClassId = revaluationRegClassId;
	}

	public List<Integer> getRevaluationSupClassId() {
		return revaluationSupClassId;
	}

	public void setRevaluationSupClassId(List<Integer> revaluationSupClassId) {
		this.revaluationSupClassId = revaluationSupClassId;
	}

	public Map<String, Double> getRevalationFeeMap() {
		return revalationFeeMap;
	}

	public void setRevalationFeeMap(Map<String, Double> revalationFeeMap) {
		this.revalationFeeMap = revalationFeeMap;
	}

	public Map<Integer, String> getRevaluationSubjects() {
		return revaluationSubjects;
	}

	public void setRevaluationSubjects(Map<Integer, String> revaluationSubjects) {
		this.revaluationSubjects = revaluationSubjects;
	}

	public boolean isCheckRevaluation() {
		return checkRevaluation;
	}

	public void setCheckRevaluation(boolean checkRevaluation) {
		this.checkRevaluation = checkRevaluation;
	}

	public boolean isCheckDD() {
		return checkDD;
	}

	public void setCheckDD(boolean checkDD) {
		this.checkDD = checkDD;
	}

	public String getDdNo() {
		return ddNo;
	}

	public void setDdNo(String ddNo) {
		this.ddNo = ddNo;
	}

	public String getDdDate() {
		return ddDate;
	}

	public void setDdDate(String ddDate) {
		this.ddDate = ddDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMarksCardType() {
		return marksCardType;
	}

	public void setMarksCardType(String marksCardType) {
		this.marksCardType = marksCardType;
	}

	public void clearRevaluation(){
		this.amount=null;
		this.ddDate=null;
		this.ddNo=null;
		this.bankName=null;
		this.branchName=null;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Map<Integer, String> getRevDateMap() {
		return revDateMap;
	}

	public void setRevDateMap(Map<Integer, String> revDateMap) {
		this.revDateMap = revDateMap;
	}

	public Map<Integer, String> getSuprevDateMap() {
		return suprevDateMap;
	}

	public void setSuprevDateMap(Map<Integer, String> suprevDateMap) {
		this.suprevDateMap = suprevDateMap;
	}

	public String getRevDate() {
		return revDate;
	}

	public void setRevDate(String revDate) {
		this.revDate = revDate;
	}

	public boolean isAttendanceLogin() {
		return attendanceLogin;
	}

	public void setAttendanceLogin(boolean attendanceLogin) {
		this.attendanceLogin = attendanceLogin;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public Integer getNotifications() {
		return notifications;
	}

	public void setNotifications(Integer notifications) {
		this.notifications = notifications;
	}

	public boolean isCiaEntrys() {
		return ciaEntrys;
	}

	public void setCiaEntrys(boolean ciaEntrys) {
		this.ciaEntrys = ciaEntrys;
	}

	public boolean isSupHallTicket() {
		return supHallTicket;
	}

	public void setSupHallTicket(boolean supHallTicket) {
		this.supHallTicket = supHallTicket;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<OnlinePaymentRecieptsTo> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<OnlinePaymentRecieptsTo> paymentList) {
		this.paymentList = paymentList;
	}

	

	public String getRegularExamId() {
		return regularExamId;
	}

	public void setRegularExamId(String regularExamId) {
		this.regularExamId = regularExamId;
	}

	public String getSuppExamId() {
		return suppExamId;
	}

	public void setSuppExamId(String suppExamId) {
		this.suppExamId = suppExamId;
	}

	public List<MarksCardTO> getRegularExamList() {
		return regularExamList;
	}

	public void setRegularExamList(List<MarksCardTO> regularExamList) {
		this.regularExamList = regularExamList;
	}

	public List<MarksCardTO> getSuppExamList() {
		return suppExamList;
	}

	public void setSuppExamList(List<MarksCardTO> suppExamList) {
		this.suppExamList = suppExamList;
	}

	public boolean isAgreementAccepted() {
		return agreementAccepted;
	}

	public void setAgreementAccepted(boolean agreementAccepted) {
		this.agreementAccepted = agreementAccepted;
	}

	public String getEnteredDob() {
		return enteredDob;
	}

	public void setEnteredDob(String enteredDob) {
		this.enteredDob = enteredDob;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getSmartCardNo() {
		return smartCardNo;
	}

	public void setSmartCardNo(String smartCardNo) {
		this.smartCardNo = smartCardNo;
	}

	public String getValidThruMonth() {
		return validThruMonth;
	}

	public void setValidThruMonth(String validThruMonth) {
		this.validThruMonth = validThruMonth;
	}

	public String getValidThruYear() {
		return validThruYear;
	}

	public void setValidThruYear(String validThruYear) {
		this.validThruYear = validThruYear;
	}

	public boolean isFeesNotConfigured() {
		return feesNotConfigured;
	}

	public void setFeesNotConfigured(boolean feesNotConfigured) {
		this.feesNotConfigured = feesNotConfigured;
	}

	public double getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(double totalFees) {
		this.totalFees = totalFees;
	}

	public int getFinId() {
		return finId;
	}

	public void setFinId(int finId) {
		this.finId = finId;
	}

	public int getOnlinePaymentId() {
		return onlinePaymentId;
	}

	public void setOnlinePaymentId(int onlinePaymentId) {
		this.onlinePaymentId = onlinePaymentId;
	}

	public boolean isPrintCertificateReq() {
		return printCertificateReq;
	}

	public void setPrintCertificateReq(boolean printCertificateReq) {
		this.printCertificateReq = printCertificateReq;
	}

	public String getPrintData() {
		return printData;
	}

	public void setPrintData(String printData) {
		this.printData = printData;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCurrentAddress1() {
		return currentAddress1;
	}

	public void setCurrentAddress1(String currentAddress1) {
		this.currentAddress1 = currentAddress1;
	}

	public String getCurrentAddress2() {
		return currentAddress2;
	}

	public void setCurrentAddress2(String currentAddress2) {
		this.currentAddress2 = currentAddress2;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getCurrentCountry() {
		return currentCountry;
	}

	public void setCurrentCountry(String currentCountry) {
		this.currentCountry = currentCountry;
	}

	public String getPermanentAddress1() {
		return permanentAddress1;
	}

	public void setPermanentAddress1(String permanentAddress1) {
		this.permanentAddress1 = permanentAddress1;
	}

	public String getPermanentAddress2() {
		return permanentAddress2;
	}

	public void setPermanentAddress2(String permanentAddress2) {
		this.permanentAddress2 = permanentAddress2;
	}

	public String getPermanentCity() {
		return permanentCity;
	}

	public void setPermanentCity(String permanentCity) {
		this.permanentCity = permanentCity;
	}

	public String getPermanentState() {
		return permanentState;
	}

	public void setPermanentState(String permanentState) {
		this.permanentState = permanentState;
	}

	public String getPermanentCountry() {
		return permanentCountry;
	}

	public void setPermanentCountry(String permanentCountry) {
		this.permanentCountry = permanentCountry;
	}

	public String getCurrentPincode() {
		return currentPincode;
	}

	public void setCurrentPincode(String currentPincode) {
		this.currentPincode = currentPincode;
	}

	public String getPermanentPincode() {
		return permanentPincode;
	}

	public void setPermanentPincode(String permanentPincode) {
		this.permanentPincode = permanentPincode;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getPhNo1() {
		return phNo1;
	}

	public void setPhNo1(String phNo1) {
		this.phNo1 = phNo1;
	}

	public String getPhNo2() {
		return phNo2;
	}

	public void setPhNo2(String phNo2) {
		this.phNo2 = phNo2;
	}

	public boolean isPeerEvaluationLinkPresent() {
		return peerEvaluationLinkPresent;
	}

	public void setPeerEvaluationLinkPresent(boolean peerEvaluationLinkPresent) {
		this.peerEvaluationLinkPresent = peerEvaluationLinkPresent;
	}

	public boolean isResearchLinkPresent() {
		return researchLinkPresent;
	}

	public void setResearchLinkPresent(boolean researchLinkPresent) {
		this.researchLinkPresent = researchLinkPresent;
	}

	

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getDisplaySem1and2() {
		return displaySem1and2;
	}

	public void setDisplaySem1and2(String displaySem1and2) {
		this.displaySem1and2 = displaySem1and2;
	}

	public Boolean getParticipatingConvocation() {
		return participatingConvocation;
	}

	public void setParticipatingConvocation(Boolean participatingConvocation) {
		this.participatingConvocation = participatingConvocation;
	}

	public Boolean getGuestPassRequired() {
		return guestPassRequired;
	}

	public void setGuestPassRequired(Boolean guestPassRequired) {
		this.guestPassRequired = guestPassRequired;
	}

	public String getRelationshipWithGuest() {
		return relationshipWithGuest;
	}

	public void setRelationshipWithGuest(String relationshipWithGuest) {
		this.relationshipWithGuest = relationshipWithGuest;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public int getSapId() {
		return sapId;
	}

	public void setSapId(int sapId) {
		this.sapId = sapId;
	}

	public Boolean getSapRegExist() {
		return sapRegExist;
	}

	public void setSapRegExist(Boolean sapRegExist) {
		this.sapRegExist = sapRegExist;
	}

	public String getUnivEmailId() {
		return univEmailId;
	}

	public void setUnivEmailId(String univEmailId) {
		this.univEmailId = univEmailId;
	}

	public List<LoginTransactionTo> getInvigilationDutyAllotmentDetails() {
		return invigilationDutyAllotmentDetails;
	}

	public void setInvigilationDutyAllotmentDetails(
			List<LoginTransactionTo> invigilationDutyAllotmentDetails) {
		this.invigilationDutyAllotmentDetails = invigilationDutyAllotmentDetails;
	}

	public Boolean getIsAllotmentDetails() {
		return isAllotmentDetails;
	}

	public void setIsAllotmentDetails(Boolean isAllotmentDetails) {
		this.isAllotmentDetails = isAllotmentDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSapExamDate() {
		return sapExamDate;
	}

	public void setSapExamDate(String sapExamDate) {
		this.sapExamDate = sapExamDate;
	}

	public Boolean getStatusIsPass() {
		return statusIsPass;
	}

	public void setStatusIsPass(Boolean statusIsPass) {
		this.statusIsPass = statusIsPass;
	}

	public Boolean getStatusIsFail() {
		return statusIsFail;
	}

	public void setStatusIsFail(Boolean statusIsFail) {
		this.statusIsFail = statusIsFail;
	}

	public Boolean getStatusIsIsOther() {
		return statusIsIsOther;
	}

	public void setStatusIsIsOther(Boolean statusIsIsOther) {
		this.statusIsIsOther = statusIsIsOther;
	}

	public Boolean getLinkForCJC() {
		return linkForCJC;
	}

	public void setLinkForCJC(Boolean linkForCJC) {
		this.linkForCJC = linkForCJC;
	}

	public String getDisplayLinkExamName() {
		return displayLinkExamName;
	}

	public void setDisplayLinkExamName(String displayLinkExamName) {
		this.displayLinkExamName = displayLinkExamName;
	}
	public void clearMideSemExam(){
		this.midSemStudentId=null;
		this.midSemRepeatId=null;
		this.midSemExamId=null;
		this.midSemRepeatList=null;
		this.repeatExamName=null;
		this.midSemStudentName=null;
		this.midSemRepeatRegNo=null;
		this.midSemClassName=null;
		this.midSemPrint=false;
		this.midSemAmount=null;
		this.midSemAmount=null;
		this.isFeesPaid=null;
		this.midSemRepeatExamId=null;
		this.isDownloaded=false;
		this.midSemTotalAmount=null;
		this.midSemClassId=0;
		this.subjectIdList=null;
		this.attemtsCompleted="false";
		this.attemptsCount=null;
		this.midSemRepeatProgram=null;
		this.midSemRepeatReason=null;
		this.midSemFatherName=null;
		this.midSemGender=null;
		this.midSemAttemptsLeft="0";
		this.midSemCountWords=null;
		this.midSemAggreagatePrint="0";
	}

	public String getMidSemStudentId() {
		return midSemStudentId;
	}

	public void setMidSemStudentId(String midSemStudentId) {
		this.midSemStudentId = midSemStudentId;
	}

	public String getMidSemRepeatId() {
		return midSemRepeatId;
	}

	public void setMidSemRepeatId(String midSemRepeatId) {
		this.midSemRepeatId = midSemRepeatId;
	}

	public String getMidSemRepeatExamId() {
		return midSemRepeatExamId;
	}

	public void setMidSemRepeatExamId(String midSemRepeatExamId) {
		this.midSemRepeatExamId = midSemRepeatExamId;
	}

	public String getMidSemExamId() {
		return midSemExamId;
	}

	public void setMidSemExamId(String midSemExamId) {
		this.midSemExamId = midSemExamId;
	}

	public String getRepeatExamName() {
		return repeatExamName;
	}

	public void setRepeatExamName(String repeatExamName) {
		this.repeatExamName = repeatExamName;
	}

	public String getMidSemStudentName() {
		return midSemStudentName;
	}

	public void setMidSemStudentName(String midSemStudentName) {
		this.midSemStudentName = midSemStudentName;
	}

	public String getMidSemRepeatRegNo() {
		return midSemRepeatRegNo;
	}

	public void setMidSemRepeatRegNo(String midSemRepeatRegNo) {
		this.midSemRepeatRegNo = midSemRepeatRegNo;
	}

	public String getMidSemClassName() {
		return midSemClassName;
	}

	public void setMidSemClassName(String midSemClassName) {
		this.midSemClassName = midSemClassName;
	}

	public int getMidSemClassId() {
		return midSemClassId;
	}

	public void setMidSemClassId(int midSemClassId) {
		this.midSemClassId = midSemClassId;
	}

	public BigDecimal getMidSemAmount() {
		return midSemAmount;
	}

	public void setMidSemAmount(BigDecimal midSemAmount) {
		this.midSemAmount = midSemAmount;
	}

	public List<ExamMidsemRepeatTO> getMidSemRepeatList() {
		return midSemRepeatList;
	}

	public void setMidSemRepeatList(List<ExamMidsemRepeatTO> midSemRepeatList) {
		this.midSemRepeatList = midSemRepeatList;
	}

	public boolean isMidSemPrint() {
		return midSemPrint;
	}

	public void setMidSemPrint(boolean midSemPrint) {
		this.midSemPrint = midSemPrint;
	}

	public String getIsFeesPaid() {
		return isFeesPaid;
	}

	public void setIsFeesPaid(String isFeesPaid) {
		this.isFeesPaid = isFeesPaid;
	}

	public Boolean getIsDownloaded() {
		return isDownloaded;
	}

	public void setIsDownloaded(Boolean isDownloaded) {
		this.isDownloaded = isDownloaded;
	}

	public BigDecimal getMidSemTotalAmount() {
		return midSemTotalAmount;
	}

	public void setMidSemTotalAmount(BigDecimal midSemTotalAmount) {
		this.midSemTotalAmount = midSemTotalAmount;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public List<Integer> getSubjectIdList() {
		return subjectIdList;
	}

	public void setSubjectIdList(List<Integer> subjectIdList) {
		this.subjectIdList = subjectIdList;
	}

	public String getAttemtsCompleted() {
		return attemtsCompleted;
	}

	public void setAttemtsCompleted(String attemtsCompleted) {
		this.attemtsCompleted = attemtsCompleted;
	}

	public String getAttemptsCount() {
		return attemptsCount;
	}

	public void setAttemptsCount(String attemptsCount) {
		this.attemptsCount = attemptsCount;
	}

	public String getMidSemRepeatProgram() {
		return midSemRepeatProgram;
	}

	public void setMidSemRepeatProgram(String midSemRepeatProgram) {
		this.midSemRepeatProgram = midSemRepeatProgram;
	}

	public String getMidSemRepeatReason() {
		return midSemRepeatReason;
	}

	public void setMidSemRepeatReason(String midSemRepeatReason) {
		this.midSemRepeatReason = midSemRepeatReason;
	}

	public String getMidSemFatherName() {
		return midSemFatherName;
	}

	public void setMidSemFatherName(String midSemFatherName) {
		this.midSemFatherName = midSemFatherName;
	}

	public String getMidSemGender() {
		return midSemGender;
	}

	public void setMidSemGender(String midSemGender) {
		this.midSemGender = midSemGender;
	}

	public String getMidSemAttemptsLeft() {
		return midSemAttemptsLeft;
	}

	public void setMidSemAttemptsLeft(String midSemAttemptsLeft) {
		this.midSemAttemptsLeft = midSemAttemptsLeft;
	}

	public String getMidSemCountWords() {
		return midSemCountWords;
	}

	public void setMidSemCountWords(String midSemCountWords) {
		this.midSemCountWords = midSemCountWords;
	}

	public String getMidSemAggreagatePrint() {
		return midSemAggreagatePrint;
	}

	public void setMidSemAggreagatePrint(String midSemAggreagatePrint) {
		this.midSemAggreagatePrint = midSemAggreagatePrint;
	}

	public String getFeesExemption() {
		return feesExemption;
	}

	public void setFeesExemption(String feesExemption) {
		this.feesExemption = feesExemption;
	}

	public String getFeeEndDate() {
		return feeEndDate;
	}

	public void setFeeEndDate(String feeEndDate) {
		this.feeEndDate = feeEndDate;
	}

	public String getRegAppExamId() {
		return regAppExamId;
	}

	public void setRegAppExamId(String regAppExamId) {
		this.regAppExamId = regAppExamId;
	}

	public String getUserOTP() {
		return userOTP;
	}

	public void setUserOTP(String userOTP) {
		this.userOTP = userOTP;
	}

	public Map<Integer, Integer> getSchemeMap() {
		return schemeMap;
	}

	public void setSchemeMap(Map<Integer, Integer> schemeMap) {
		this.schemeMap = schemeMap;
	}

	public String getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}

	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}

	public void reset2(){
		this.subjectMap = null;
	}
	
	public int getRevClassId() {
		return revClassId;
	}

	public void setRevClassId(int revClassId) {
		this.revClassId = revClassId;
	}

	public boolean getIsSupplImpAppDisplay() {
		return isSupplImpAppDisplay;
	}

	public void setIsSupplImpAppDisplay(boolean isSupplImpAppDisplay) {
		this.isSupplImpAppDisplay = isSupplImpAppDisplay;
	}

	public List<Integer> getExamIdList() {
		return examIdList;
	}

	public void setExamIdList(List<Integer> examIdList) {
		this.examIdList = examIdList;
	}
	public String getLearningAndOutcome() {
		return learningAndOutcome;
	}
	public void setLearningAndOutcome(String learningAndOutcome) {
		this.learningAndOutcome = learningAndOutcome;
	}
	public String getOverallMark() {
		return overallMark;
	}
	public void setOverallMark(String overallMark) {
		this.overallMark = overallMark;
	}
	public List<ServiceLearningMarksEntryTo> getServiceLearningMarksEntryToList() {
		return serviceLearningMarksEntryToList;
	}
	public void setServiceLearningMarksEntryToList(
			List<ServiceLearningMarksEntryTo> serviceLearningMarksEntryToList) {
		this.serviceLearningMarksEntryToList = serviceLearningMarksEntryToList;
	}
	public List<ProgrammeEntryTo> getProgrammeEntryToList() {
		return programmeEntryToList;
	}
	public void setProgrammeEntryToList(List<ProgrammeEntryTo> programmeEntryToList) {
		this.programmeEntryToList = programmeEntryToList;
	}
	public Boolean getIsAttendanceShortage() {
		return isAttendanceShortage;
	}
	public void setIsAttendanceShortage(Boolean isAttendanceShortage) {
		this.isAttendanceShortage = isAttendanceShortage;
	}
	public PersonalDataTO getPersonalData() {
		return personalData;
	}
	public void setPersonalData(PersonalDataTO personalData) {
		this.personalData = personalData;
	}
	public List<ResidentCategoryTO> getResidentTypes() {
		return residentTypes;
	}
	public void setResidentTypes(List<ResidentCategoryTO> residentTypes) {
		this.residentTypes = residentTypes;
	}
	public Map<Integer, String> getStateMap() {
		return stateMap;
	}
	public void setStateMap(Map<Integer, String> stateMap) {
		this.stateMap = stateMap;
	}
	public List<NationalityTO> getNationalities() {
		return nationalities;
	}
	public void setNationalities(List<NationalityTO> nationalities) {
		this.nationalities = nationalities;
	}
	public List<CountryTO> getCountries() {
		return countries;
	}
	public void setCountries(List<CountryTO> countries) {
		this.countries = countries;
	}
	public String getBirthState() {
		return birthState;
	}
	public void setBirthState(String birthState) {
		this.birthState = birthState;
	}
	public int getBirthCountryId() {
		return birthCountryId;
	}
	public void setBirthCountryId(int birthCountryId) {
		this.birthCountryId = birthCountryId;
	}
	public int getCurAddrCountyId() {
		return curAddrCountyId;
	}
	public void setCurAddrCountyId(int curAddrCountyId) {
		this.curAddrCountyId = curAddrCountyId;
	}
	public int getPerAddrCountyId() {
		return perAddrCountyId;
	}
	public void setPerAddrCountyId(int perAddrCountyId) {
		this.perAddrCountyId = perAddrCountyId;
	}
	public Map<Integer, String> getCurAddrStateMap() {
		return curAddrStateMap;
	}
	public void setCurAddrStateMap(Map<Integer, String> curAddrStateMap) {
		this.curAddrStateMap = curAddrStateMap;
	}
	public Map<Integer, String> getPerAddrStateMap() {
		return perAddrStateMap;
	}
	public void setPerAddrStateMap(Map<Integer, String> perAddrStateMap) {
		this.perAddrStateMap = perAddrStateMap;
	}
	public Map<Integer, String> getCurrencyMap() {
		return currencyMap;
	}
	public void setCurrencyMap(Map<Integer, String> currencyMap) {
		this.currencyMap = currencyMap;
	}
	public int getParentAddrCountyId() {
		return parentAddrCountyId;
	}
	public void setParentAddrCountyId(int parentAddrCountyId) {
		this.parentAddrCountyId = parentAddrCountyId;
	}
	public int getGuardianAddrCountyId() {
		return guardianAddrCountyId;
	}
	public void setGuardianAddrCountyId(int guardianAddrCountyId) {
		this.guardianAddrCountyId = guardianAddrCountyId;
	}
	public Map<Integer, String> getParentStateMap() {
		return parentStateMap;
	}
	public void setParentStateMap(Map<Integer, String> parentStateMap) {
		this.parentStateMap = parentStateMap;
	}
	public Map<Integer, String> getGuardianStateMap() {
		return guardianStateMap;
	}
	public void setGuardianStateMap(Map<Integer, String> guardianStateMap) {
		this.guardianStateMap = guardianStateMap;
	}
	public List<DistrictTO> getEditPermanentDistrict() {
		return editPermanentDistrict;
	}
	public void setEditPermanentDistrict(List<DistrictTO> editPermanentDistrict) {
		this.editPermanentDistrict = editPermanentDistrict;
	}
	public List<DistrictTO> getEditCurrentDistrict() {
		return editCurrentDistrict;
	}
	public void setEditCurrentDistrict(List<DistrictTO> editCurrentDistrict) {
		this.editCurrentDistrict = editCurrentDistrict;
	}
	public boolean isSameTempAddr() {
		return sameTempAddr;
	}
	public void setSameTempAddr(boolean sameTempAddr) {
		this.sameTempAddr = sameTempAddr;
	}
	public List<OccupationTO> getOccupations() {
		return occupations;
	}
	public void setOccupations(List<OccupationTO> occupations) {
		this.occupations = occupations;
	}
	public List<IncomeTO> getIncomeList() {
		return incomeList;
	}
	public void setIncomeList(List<IncomeTO> incomeList) {
		this.incomeList = incomeList;
	}
	public List<CurrencyTO> getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(List<CurrencyTO> currencyList) {
		this.currencyList = currencyList;
	}
	public boolean isSameParentAddr() {
		return sameParentAddr;
	}
	public void setSameParentAddr(boolean sameParentAddr) {
		this.sameParentAddr = sameParentAddr;
	}
	public List<ReligionTO> getReligions() {
		return religions;
	}
	public void setReligions(List<ReligionTO> religions) {
		this.religions = religions;
	}
	public List<ReligionSectionTO> getSubReligions() {
		return subReligions;
	}
	public void setSubReligions(List<ReligionSectionTO> subReligions) {
		this.subReligions = subReligions;
	}
	public boolean isHandicapped() {
		return handicapped;
	}
	public void setHandicapped(boolean handicapped) {
		this.handicapped = handicapped;
	}
	public boolean isSportsPerson() {
		return sportsPerson;
	}
	public void setSportsPerson(boolean sportsPerson) {
		this.sportsPerson = sportsPerson;
	}
	public Map<Integer, String> getSubReligionMap() {
		return subReligionMap;
	}
	public void setSubReligionMap(Map<Integer, String> subReligionMap) {
		this.subReligionMap = subReligionMap;
	}
	public Map<Integer, String> getSubCasteMap() {
		return subCasteMap;
	}
	public void setSubCasteMap(Map<Integer, String> subCasteMap) {
		this.subCasteMap = subCasteMap;
	}
	public boolean isDateExpired() {
		return dateExpired;
	}
	public void setDateExpired(boolean dateExpired) {
		this.dateExpired = dateExpired;
	}
	public boolean isAided() {
		return aided;
	}
	public void setAided(boolean aided) {
		this.aided = aided;
	}
	public String getNssgrade() {
		return nssgrade;
	}
	public void setNssgrade(String nssgrade) {
		this.nssgrade = nssgrade;
	}
	public boolean isNsscertificate() {
		return nsscertificate;
	}
	public void setNsscertificate(boolean nsscertificate) {
		this.nsscertificate = nsscertificate;
	}
	public String getSports() {
		return sports;
	}
	public void setSports(String sports) {
		this.sports = sports;
	}
	public String getNccgrade() {
		return nccgrade;
	}
	public void setNccgrade(String nccgrade) {
		this.nccgrade = nccgrade;
	}
	public boolean isNcccertificate() {
		return ncccertificate;
	}
	public void setNcccertificate(boolean ncccertificate) {
		this.ncccertificate = ncccertificate;
	}
	public String getArts() {
		return arts;
	}
	public void setArts(String arts) {
		this.arts = arts;
	}
	public String getSportsParticipate() {
		return sportsParticipate;
	}
	public void setSportsParticipate(String sportsParticipate) {
		this.sportsParticipate = sportsParticipate;
	}
	public String getArtsParticipate() {
		return artsParticipate;
	}
	public void setArtsParticipate(String artsParticipate) {
		this.artsParticipate = artsParticipate;
	}
	public Integer getCheckReligionId() {
		return checkReligionId;
	}
	public void setCheckReligionId(Integer checkReligionId) {
		this.checkReligionId = checkReligionId;
	}
	public String getRecomendedBy() {
		return recomendedBy;
	}
	public void setRecomendedBy(String recomendedBy) {
		this.recomendedBy = recomendedBy;
	}
	public String getFocusValue() {
		return focusValue;
	}
	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}
	public AdmApplnTO getApplicantDetails() {
		return applicantDetails;
	}
	public void setApplicantDetails(AdmApplnTO applicantDetails) {
		this.applicantDetails = applicantDetails;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public FormFile getStudentPhoto() {
		return studentPhoto;
	}
	
	public void setStudentPhoto(FormFile studentPhoto) {
		this.studentPhoto = studentPhoto;
	}
	public Boolean getIsUpdated() {
		return isUpdated;
	}
	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	public List<Period> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<Period> periodList) {
		this.periodList = periodList;
	}
	public List<Integer> getCommonSubList() {
		return commonSubList;
	}
	public void setCommonSubList(List<Integer> commonSubList) {
		this.commonSubList = commonSubList;
	}
	public List<Integer> getSecLanguageList() {
		return secLanguageList;
	}
	public void setSecLanguageList(List<Integer> secLanguageList) {
		this.secLanguageList = secLanguageList;
	}
	public List<Integer> getElectiveList() {
		return electiveList;
	}
	public void setElectiveList(List<Integer> electiveList) {
		this.electiveList = electiveList;
	}
	public List<Integer> getCertificateList() {
		return certificateList;
	}
	public void setCertificateList(List<Integer> certificateList) {
		this.certificateList = certificateList;
	}
	public Map<Integer, Map<Integer, String>> getActivityMap() {
		return activityMap;
	}
	public void setActivityMap(Map<Integer, Map<Integer, String>> activityMap) {
		this.activityMap = activityMap;
	}
	public List<Integer> getBatchList() {
		return batchList;
	}
	public void setBatchList(List<Integer> batchList) {
		this.batchList = batchList;
	}
	public Map<Integer, Map<Integer, String>> getBatchMap() {
		return batchMap;
	}
	public void setBatchMap(Map<Integer, Map<Integer, String>> batchMap) {
		this.batchMap = batchMap;
	}
	public List<Integer> getActivityBatchList() {
		return activityBatchList;
	}
	public void setActivityBatchList(List<Integer> activityBatchList) {
		this.activityBatchList = activityBatchList;
	}
	public Map<Integer, Map<Integer, String>> getActivityBatchMap() {
		return activityBatchMap;
	}
	public void setActivityBatchMap(
			Map<Integer, Map<Integer, String>> activityBatchMap) {
		this.activityBatchMap = activityBatchMap;
	}
	public Map<Integer, String> getAttTypeMap() {
		return attTypeMap;
	}
	public void setAttTypeMap(Map<Integer, String> attTypeMap) {
		this.attTypeMap = attTypeMap;
	}
	public Boolean getIsStaff() {
		return isStaff;
	}
	public void setIsStaff(Boolean isStaff) {
		this.isStaff = isStaff;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getTtClassId() {
		return ttClassId;
	}
	public void setTtClassId(int ttClassId) {
		this.ttClassId = ttClassId;
	}
	public String getFinalApprove() {
		return finalApprove;
	}
	public void setFinalApprove(String finalApprove) {
		this.finalApprove = finalApprove;
	}
	public List<StudentLoginTO> getClassTos() {
		return classTos;
	}
	public void setClassTos(List<StudentLoginTO> classTos) {
		this.classTos = classTos;
	}
	public Boolean getTtClassHistoryExists() {
		return ttClassHistoryExists;
	}
	public void setTtClassHistoryExists(Boolean ttClassHistoryExists) {
		this.ttClassHistoryExists = ttClassHistoryExists;
	}
	public List<StudentTO> getPaymentListStudent() {
		return paymentListStudent;
	}
	public void setPaymentListStudent(List<StudentTO> paymentListStudent) {
		this.paymentListStudent = paymentListStudent;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getProductinfo() {
		return productinfo;
	}
	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	public String getPgiStatus() {
		return pgiStatus;
	}
	public void setPgiStatus(String pgiStatus) {
		this.pgiStatus = pgiStatus;
	}
	
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	
	public boolean isPDataEdited() {
		return isPDataEdited;
	}
	public void setPDataEdited(boolean isPDataEdited) {
		this.isPDataEdited = isPDataEdited;
	}
	public boolean isPhotoEdited() {
		return photoEdited;
	}
	public void setPhotoEdited(boolean photoEdited) {
		this.photoEdited = photoEdited;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<DisciplineAndAchivementTo> getDisciplineAndAchivementToList() {
		return disciplineAndAchivementToList;
	}
	public void setDisciplineAndAchivementToList(List<DisciplineAndAchivementTo> disciplineAndAchivementToList) {
		this.disciplineAndAchivementToList = disciplineAndAchivementToList;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public HashMap<Integer, String> getSecondLanguageList() {
		return secondLanguageList;
	}
	public void setSecondLanguageList(HashMap<Integer, String> secondLanguageList) {
		this.secondLanguageList = secondLanguageList;
	}
	public EdnQualificationTO getEdnQualification() {
		return ednQualification;
	}
	public void setEdnQualification(EdnQualificationTO ednQualification) {
		this.ednQualification = ednQualification;
	}
	public Map<Integer, String> getAdmSubjectMap() {
		return admSubjectMap;
	}
	public void setAdmSubjectMap(Map<Integer, String> admSubjectMap) {
		this.admSubjectMap = admSubjectMap;
	}
	public Map<Integer, String> getAdmSubjectLangMap() {
		return admSubjectLangMap;
	}
	public void setAdmSubjectLangMap(Map<Integer, String> admSubjectLangMap) {
		this.admSubjectLangMap = admSubjectLangMap;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public void setDisplayText(boolean isDisplayText) {
		this.isDisplayText = isDisplayText;
	}
	public List<UniversityTO> getUniversities() {
		return universities;
	}
	public void setUniversities(List<UniversityTO> universities) {
		this.universities = universities;
	}
	public CandidateMarkTO getDetailMark() {
		return detailMark;
	}
	public void setDetailMark(CandidateMarkTO detailMark) {
		this.detailMark = detailMark;
	}
	public List<DocChecklist> getDocCheckList() {
		return docCheckList;
	}
	public void setDocCheckList(List<DocChecklist> docCheckList) {
		this.docCheckList = docCheckList;
	}
	public List<ProgressCardTo> getProgressCardList() {
		return progressCardList;
	}
	public void setProgressCardList(List<ProgressCardTo> progressCardList) {
		this.progressCardList = progressCardList;
	}
	public List<AdmittedThroughTO> getAdmittedThroughList() {
		return admittedThroughList;
	}
	public void setAdmittedThroughList(List<AdmittedThroughTO> admittedThroughList) {
		this.admittedThroughList = admittedThroughList;
	}
	public Map<Integer, String> getAdmCoreMap() {
		return admCoreMap;
	}
	public void setAdmCoreMap(Map<Integer, String> admCoreMap) {
		this.admCoreMap = admCoreMap;
	}
	public Map<Integer, String> getAdmComplMap() {
		return admComplMap;
	}
	public void setAdmComplMap(Map<Integer, String> admComplMap) {
		this.admComplMap = admComplMap;
	}
	public Map<Integer, String> getAdmCommonMap() {
		return admCommonMap;
	}
	public void setAdmCommonMap(Map<Integer, String> admCommonMap) {
		this.admCommonMap = admCommonMap;
	}
	public Map<Integer, String> getAdmMainMap() {
		return admMainMap;
	}
	public void setAdmMainMap(Map<Integer, String> admMainMap) {
		this.admMainMap = admMainMap;
	}
	public Map<Integer, String> getAdmSubMap() {
		return admSubMap;
	}
	public void setAdmSubMap(Map<Integer, String> admSubMap) {
		this.admSubMap = admSubMap;
	}
	public String getPatternofStudy() {
		return patternofStudy;
	}
	public void setPatternofStudy(String patternofStudy) {
		this.patternofStudy = patternofStudy;
	}

	
}