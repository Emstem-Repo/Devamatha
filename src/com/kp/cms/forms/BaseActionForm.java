package com.kp.cms.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.Resources;
import org.apache.struts.validator.ValidatorForm;

import com.kp.cms.constants.CMSConstants;

/**
 * This will be above all the actionform. this class contains methods to handle
 * some validations. date 9/jan/2009
 */
public class BaseActionForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String stateId;
	private String programTypeId;
	private String programId;
	private String religionId;
	private String universityId;
	private String courseId;
	private String subjectGroupId;
	private String interviewTypeId;
	private String errorMessage;
	private String errorStack;
	private String year;
	private String semister;
	private String courseName;
	private String programTypeName;
	private String programName;
	private Map collectionMap = new HashMap();
	private String propertyName;
	private String classcode;
	private String userId = "";
	private String classId;
	public String subjectId;
	private String attendanceTypeids;
	private String activityId;
	private String className;
	private String subjectName;
	private String classSchemewiseId;
	public String attendanceTypeId;
	private String selectedClassesArray;
	private String hostelId;
	private String blockId;
	private String itemCategoryId;
	private String purchaseUomId;
	private String issueUomId;
	private String invLocationId;
	private String itemId;
	private String secPrefId;
	private String departmentId;
	private String employeeId;
	private String organizationName;
	// for cash collection page
	private String optionNo;
	private String appRegRollno;
	private String academicYear;
	private String nameOfStudent;
	private int accoId;
	private String amount;
	private String fromScheme;
	private String toScheme;
	// for Hostel Allocation
	private String floorNo;
	private String roomId;
	private String schemeId;
	private String examCenterId;
	// EXAM
	private String type;
	private String marksByReg;
	private String evaluatorId;

	private String answerScriptId;
	private String assignmentOverallType;
	private String schemeNo;
	private ArrayList<Integer> listClassId;
	private String[] classListId;

	private String classCodeIds;
	private String marksForReg;
	private String subjectType;
	private String marks;

	private String date;
	private String time;
	private String currentExam;
	private String dateTimeValue;

	// for Exam Module
	private String examName;
	private String joiningBatch;
	private int examTypeId;
	private String examType;
	private String regNo;
	private String rollNo;
	private String baseStudentId;
	
	
	private String qualificationId;
	private String isPreviousExam;
	private String stime;
	private String etime;
	private String ipAddress;
	private String feeGroupId;
	private String result;
	private String empCode;
	private String fingerPrintId;
	private String payscaleId;
	private String empTypeId;
	private String streamId;
	private String teachingStaff1;
	private String month;
	private Map<Integer,String> courseMap;
	private Map<Integer, String> classMap;
    private String templateName;
    private String valuatorName;
    private String addNewType;
    private String mainPage;
    private String destinationMethod;
    private String campusId;
    private String superMainPage;
    private String superAddNewType;
    private String mainPageExists;
    private String guestId;
    private String appNo;
    private String receivedThrough;
    private String smartCardNo;
	private String dob;
	private String originalDob;
	private String validThruMonth;
	private String validThruYear;
	private String startDate;
	private String endDate;
	private String guideName;
	private String hostelroomType;
	private int hostelroomTypeId;
	private String selectedProgramArray;
	private String hostelGender;
	private String unitId;
	private String hostelAppNo;
	private boolean waitingList;
	private boolean studentInWaitingList;
	private String exam1Id;
	private String selectedClassArray;
	private String allClassesArray;
	private String locationId;
	private String workLocatId;
	private int examid;
	private String deanaryName;
	private String selectionProcessDate;
    private String selectionProcessVenue;	
	private String selectScheduleId;
	private String firstPrefId;
	private String selectVenueId;
	private String programYear;
	private String applnNo;
	private String oldSelectedVenue;
	private String teacher;
	private String[] programIdList;
	private String evenOrOdd;
	private String chalanNo;

	private boolean isTeacherLogin;
	private String attendanceDate;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.reset(mapping, request);
		this.errorMessage = null;
		this.semister = null;
		this.locationId =null;
	}

	public void clear1() {
		this.examType = null;
		this.roomId = null;
	}

	public void clear() {
		// super.reset(mapping, request);
		//this.programTypeId = null;
		this.countryId = null;
		this.stateId = null;
		this.errorMessage = null;
		this.programId = null;
		this.courseId = null;
		this.semister = null;
		this.subjectGroupId = null;
		this.collectionMap = null;
		this.year = null;
		this.courseName = null;
		this.classId = null;
		this.subjectId = null;
		this.itemCategoryId = null;
		this.purchaseUomId = null;
		this.issueUomId = null;
		this.invLocationId = null;
		this.itemId = null;
		this.departmentId = null;
		this.hostelId = null;
		this.blockId = null;
		this.result = null;
		this.className = null;
		this.unitId = null;
		this.teacher=null;
		this.schemeNo=null;
		this.academicYear=null;
		this.classSchemewiseId=null;
	}

	public String getExam1Id() {
		return exam1Id;
	}

	public void setExamId(String exam1Id) {
		this.exam1Id = exam1Id;
	}

	public String getErrorStack() {
		return errorStack;
	}

	public void setErrorStack(String errorStack) {
		this.errorStack = errorStack;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private String countryId;

	/**
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId
	 *            the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the stateId
	 */
	public String getStateId() {
		return stateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getInterviewTypeId() {
		return interviewTypeId;
	}

	public void setInterviewTypeId(String interviewTypeId) {
		this.interviewTypeId = interviewTypeId;
	}

	/**
	 * @return the programTypeId
	 */
	public String getProgramTypeId() {
		return programTypeId;
	}

	/**
	 * @param programTypeId
	 *            the programTypeId to set
	 */
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	/**
	 * @return the programId
	 */
	public String getProgramId() {
		return programId;
	}

	/**
	 * @param programId
	 *            the programId to set
	 */
	public void setProgramId(String programId) {
		this.programId = programId;
	}

	/**
	 * @return the religionId
	 */
	public String getReligionId() {
		return religionId;
	}

	/**
	 * @param religionId
	 *            the religionId to set
	 */
	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	/**
	 * @return the universityId
	 */
	public String getUniversityId() {
		return universityId;
	}

	/**
	 * @param universityId
	 *            the universityId to set
	 */
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId
	 *            the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the subjectGroupId
	 */
	public String getSubjectGroupId() {
		return subjectGroupId;
	}

	/**
	 * @param subjectGroupId
	 *            the subjectGroupId to set
	 */
	public void setSubjectGroupId(String subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the semister
	 */
	public String getSemister() {
		return semister;
	}

	/**
	 * @param semister
	 *            the semister to set
	 */
	public void setSemister(String semister) {
		this.semister = semister;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**
	 * 
	 * @param mapping
	 * @param request
	 * @param formName
	 * @return ActionErrors This will load the validation based on the pagetype
	 *         and key.
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, String formName) {
		ActionErrors errors = new ActionErrors();

		String pageType = request.getParameter(CMSConstants.PAGETYPE);

		try {
			int pageNum = Integer.parseInt(pageType);
			setPage(pageNum);
		} catch (NumberFormatException ne) {

		}

		ServletContext context = getServlet().getServletContext();

		String validationKey = null;
		if (null == formName) {
			validationKey = this.getValidationKey(mapping, pageType);
		} else {
			validationKey = formName + "#" + pageType;

		}

		Validator validator = Resources.initValidator(validationKey, this,
				context, request, errors, page);

		try {
			validatorResults = validator.validate();
		} catch (ValidatorException e) {

		}
		return errors;
	}

	/**
	 * 
	 * @param mapping
	 * @param pageType
	 * @return PageType validation key Ex : counryStateCity#1,
	 *         counryStateCity#2..
	 */
	private String getValidationKey(ActionMapping mapping, String pageType) {
		String baseKey = mapping.getAttribute();
		if (null != pageType) {
			return baseKey + "#" + pageType;
		} else {
			return baseKey;
		}
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName
	 *            the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the programTypeName
	 */
	public String getProgramTypeName() {
		return programTypeName;
	}

	/**
	 * @param programTypeName
	 *            the programTypeName to set
	 */
	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName
	 *            the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * @return the collectionMap
	 */
	public Map getCollectionMap() {
		return collectionMap;
	}

	/**
	 * @param collectionMap
	 *            the collectionMap to set
	 */
	public void setCollectionMap(Map collectionMap) {
		this.collectionMap = collectionMap;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName
	 *            the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the classcode
	 */
	public String getClasscode() {
		return classcode;
	}

	/**
	 * @param classcode
	 *            the classcode to set
	 */
	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String string) {
		this.subjectId = string;
	}

	public String getAttendanceTypeids() {
		return attendanceTypeids;
	}

	public void setAttendanceTypeids(String attendanceTypeids) {
		this.attendanceTypeids = attendanceTypeids;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the attendanceTypeId
	 */
	public String getAttendanceTypeId() {
		return attendanceTypeId;
	}

	/**
	 * @param attendanceTypeId
	 *            the attendanceTypeId to set
	 */
	public void setAttendanceTypeId(String attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}

	public String getClassSchemewiseId() {
		return classSchemewiseId;
	}

	/**
	 * @return the selectedClassesArray
	 */
	public String getSelectedClassesArray() {
		return selectedClassesArray;
	}

	/**
	 * @param selectedClassesArray
	 *            the selectedClassesArray to set
	 */
	public void setSelectedClassesArray(String selectedClassesArray) {
		this.selectedClassesArray = selectedClassesArray;
	}

	public void setClassSchemewiseId(String classSchemewiseId) {
		this.classSchemewiseId = classSchemewiseId;
	}

	public String getHostelId() {
		return hostelId;
	}

	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}

	public String getSecPrefId() {
		return secPrefId;
	}

	public void setSecPrefId(String secPrefId) {
		this.secPrefId = secPrefId;
	}

	public String getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public String getPurchaseUomId() {
		return purchaseUomId;
	}

	public void setPurchaseUomId(String purchaseUomId) {
		this.purchaseUomId = purchaseUomId;
	}

	public String getIssueUomId() {
		return issueUomId;
	}

	public void setIssueUomId(String issueUomId) {
		this.issueUomId = issueUomId;
	}

	public String getInvLocationId() {
		return invLocationId;
	}

	public void setInvLocationId(String invLocationId) {
		this.invLocationId = invLocationId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	// petticash values setters and getters

	public String getOptionNo() {
		return optionNo;
	}

	public void setOptionNo(String optionNo) {
		this.optionNo = optionNo;
	}

	public String getAppRegRollno() {
		return appRegRollno;
	}

	public void setAppRegRollno(String appRegRollno) {
		this.appRegRollno = appRegRollno;
	}

	public String getNameOfStudent() {
		return nameOfStudent;
	}

	public void setNameOfStudent(String nameOfStudent) {
		this.nameOfStudent = nameOfStudent;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public int getAccoId() {
		return accoId;
	}

	public void setAccoId(int accoId) {
		this.accoId = accoId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getJoiningBatch() {
		return joiningBatch;
	}

	public void setJoiningBatch(String joiningBatch) {
		this.joiningBatch = joiningBatch;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public int getExamTypeId() {
		return examTypeId;
	}

	public void setExamTypeId(int examTypeId) {
		this.examTypeId = examTypeId;
	}

	public void setFromScheme(String fromScheme) {
		this.fromScheme = fromScheme;
	}

	public String getFromScheme() {
		return fromScheme;
	}

	public void setToScheme(String toScheme) {
		this.toScheme = toScheme;
	}

	public String getToScheme() {
		return toScheme;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMarksByReg() {
		return marksByReg;
	}

	public void setMarksByReg(String marksByReg) {
		this.marksByReg = marksByReg;
	}

	public String getEvaluatorId() {
		return evaluatorId;
	}

	public void setEvaluatorId(String evaluatorId) {
		this.evaluatorId = evaluatorId;
	}

	public String getAnswerScriptId() {
		return answerScriptId;
	}

	public void setAnswerScriptId(String answerScriptId) {
		this.answerScriptId = answerScriptId;
	}

	public String getAssignmentOverallType() {
		return assignmentOverallType;
	}

	public void setAssignmentOverallType(String assignmentOverallType) {
		this.assignmentOverallType = assignmentOverallType;
	}

	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public ArrayList<Integer> getListClassId() {
		return listClassId;
	}

	public void setListClassId(ArrayList<Integer> listClassId) {
		this.listClassId = listClassId;
	}

	public String[] getClassListId() {
		return classListId;
	}

	public void setClassListId(String[] classListId) {
		this.classListId = classListId;
	}

	public String getClassCodeIds() {
		return classCodeIds;
	}

	public void setClassCodeIds(String classCodeIds) {
		this.classCodeIds = classCodeIds;
	}

	public String getMarksForReg() {
		return marksForReg;
	}

	public void setMarksForReg(String marksForReg) {
		this.marksForReg = marksForReg;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCurrentExam() {
		return currentExam;
	}

	public void setCurrentExam(String currentExam) {
		this.currentExam = currentExam;
	}

	public String getDateTimeValue() {
		return dateTimeValue;
	}

	public void setDateTimeValue(String dateTimeValue) {
		this.dateTimeValue = dateTimeValue;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getExamCenterId() {
		return examCenterId;
	}

	public void setExamCenterId(String examCenterId) {
		this.examCenterId = examCenterId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getQualificationId() {
		return qualificationId;
	}

	public void setBaseStudentId(String baseStudentId) {
		this.baseStudentId = baseStudentId;
	}

	public String getBaseStudentId() {
		return baseStudentId;
	}

	public String getIsPreviousExam() {
		return isPreviousExam;
	}

	public void setIsPreviousExam(String isPreviousExam) {
		this.isPreviousExam = isPreviousExam;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getFeeGroupId() {
		return feeGroupId;
	}

	public void setFeeGroupId(String feeGroupId) {
		this.feeGroupId = feeGroupId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getFingerPrintId() {
		return fingerPrintId;
	}

	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}

	public String getPayscaleId() {
		return payscaleId;
	}

	public void setPayscaleId(String payscaleId) {
		this.payscaleId = payscaleId;
	}

	public String getEmpTypeId() {
		return empTypeId;
	}

	public void setEmpTypeId(String empTypeId) {
		this.empTypeId = empTypeId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public String getStreamId() {
		return streamId;
	}

	public String getTeachingStaff1() {
		return teachingStaff1;
	}

	public void setTeachingStaff1(String teachingStaff1) {
		this.teachingStaff1 = teachingStaff1;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}

	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getValuatorName() {
		return valuatorName;
	}

	public void setValuatorName(String valuatorName) {
		this.valuatorName = valuatorName;
	}

    public String getAddNewType() {
		return addNewType;
	}

	public void setAddNewType(String addNewType) {
		this.addNewType = addNewType;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getDestinationMethod() {
		return destinationMethod;
	}

	public void setDestinationMethod(String destinationMethod) {
		this.destinationMethod = destinationMethod;
	}

	public String getCampusId() {
		return campusId;
	}

	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}

	public String getSuperMainPage() {
		return superMainPage;
	}

	public void setSuperMainPage(String superMainPage) {
		this.superMainPage = superMainPage;
	}

	public String getSuperAddNewType() {
		return superAddNewType;
	}

	public void setSuperAddNewType(String superAddNewType) {
		this.superAddNewType = superAddNewType;
	}

	public String getMainPageExists() {
		return mainPageExists;
	}

	public void setMainPageExists(String mainPageExists) {
		this.mainPageExists = mainPageExists;
	}

	public void clear2(){
    	this.programTypeId=null;
    	this.type=null;
    	this.amount=null;
    }

	public String getGuestId() {
		return guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getReceivedThrough() {
		return receivedThrough;
	}

	public void setReceivedThrough(String receivedThrough) {
		this.receivedThrough = receivedThrough;
	}

	public String getSmartCardNo() {
		return smartCardNo;
	}

	public void setSmartCardNo(String smartCardNo) {
		this.smartCardNo = smartCardNo;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getOriginalDob() {
		return originalDob;
	}

	public void setOriginalDob(String originalDob) {
		this.originalDob = originalDob;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public String getHostelroomType() {
		return hostelroomType;
	}

	public void setHostelroomType(String hostelroomType) {
		this.hostelroomType = hostelroomType;
	}

	public int getHostelroomTypeId() {
		return hostelroomTypeId;
	}

	public void setHostelroomTypeId(int hostelroomTypeId) {
		this.hostelroomTypeId = hostelroomTypeId;
	}

	public String getSelectedProgramArray() {
		return selectedProgramArray;
	}

	public void setSelectedProgramArray(String selectedProgramArray) {
		this.selectedProgramArray = selectedProgramArray;
	}

	public String getHostelGender() {
		return hostelGender;
	}

	public void setHostelGender(String hostelGender) {
		this.hostelGender = hostelGender;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	
	public String getHostelAppNo() {
		return hostelAppNo;
	}

	public void setHostelAppNo(String hostelAppNo) {
		this.hostelAppNo = hostelAppNo;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	

	public boolean isWaitingList() {
		return waitingList;
	}

	public void setWaitingList(boolean waitingList) {
		this.waitingList = waitingList;
	}
	

	public boolean isStudentInWaitingList() {
		return studentInWaitingList;
	}

	public void setStudentInWaitingList(boolean studentInWaitingList) {
		this.studentInWaitingList = studentInWaitingList;
	}

	public void clear3(){
		this.hostelId=null;
		this.roomId=null;
		this.academicYear=null;
		this.examType=null;
		this.examName=null;
		}

	public String getSelectedClassArray() {
		return selectedClassArray;
	}

	public void setSelectedClassArray(String selectedClassArray) {
		this.selectedClassArray = selectedClassArray;
	}

	public String getAllClassesArray() {
		return allClassesArray;
	}

	public void setAllClassesArray(String allClassesArray) {
		this.allClassesArray = allClassesArray;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getWorkLocatId() {
		return workLocatId;
	}

	public void setWorkLocatId(String workLocatId) {
		this.workLocatId = workLocatId;
	}

	public String getDeanaryName() {
		return deanaryName;
	}

	public void setDeanaryName(String deanaryName) {
		this.deanaryName = deanaryName;
	}

	public int getExamid() {
		return examid;
	}

	public void setExamid(int examid) {
		this.examid = examid;
	}

	public String getSelectionProcessDate() {
		return selectionProcessDate;
	}

	public void setSelectionProcessDate(String selectionProcessDate) {
		this.selectionProcessDate = selectionProcessDate;
	}

	public String getSelectionProcessVenue() {
		return selectionProcessVenue;
	}

	public void setSelectionProcessVenue(String selectionProcessVenue) {
		this.selectionProcessVenue = selectionProcessVenue;
	}

	public String getSelectScheduleId() {
		return selectScheduleId;
	}

	public void setSelectScheduleId(String selectScheduleId) {
		this.selectScheduleId = selectScheduleId;
	}

	public String getFirstPrefId() {
		return firstPrefId;
	}

	public void setFirstPrefId(String firstPrefId) {
		this.firstPrefId = firstPrefId;
	}

	public String getSelectVenueId() {
		return selectVenueId;
	}

	public void setSelectVenueId(String selectVenueId) {
		this.selectVenueId = selectVenueId;
	}

	public String getProgramYear() {
		return programYear;
	}

	public void setProgramYear(String programYear) {
		this.programYear = programYear;
	}

	public String getApplnNo() {
		return applnNo;
	}

	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}

	public String getOldSelectedVenue() {
		return oldSelectedVenue;
	}

	public void setOldSelectedVenue(String oldSelectedVenue) {
		this.oldSelectedVenue = oldSelectedVenue;
	}

	public void setExam1Id(String exam1Id) {
		this.exam1Id = exam1Id;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public String[] getProgramIdList() {
		return programIdList;
	}

	public void setProgramIdList(String[] programIdList) {
		this.programIdList = programIdList;
	}

	public String getEvenOrOdd() {
		return evenOrOdd;
	}

	public void setEvenOrOdd(String evenOrOdd) {
		this.evenOrOdd = evenOrOdd;
	}

	public String getChalanNo() {
		return chalanNo;
	}

	public void setChalanNo(String chalanNo) {
		this.chalanNo = chalanNo;
	}

	public boolean getIsTeacherLogin() {
		return isTeacherLogin;
	}

	public void setIsTeacherLogin(boolean isTeacherLogin) {
		this.isTeacherLogin = isTeacherLogin;
	}

	public String getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
}