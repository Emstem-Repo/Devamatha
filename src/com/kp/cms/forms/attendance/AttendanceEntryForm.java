package com.kp.cms.forms.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.ActivityTO;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.attendance.TeacherDepartmentTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class AttendanceEntryForm extends BaseActionForm{

	private String fromDate;
	private String toDate;
	private String year;
	private String attendancedate;
	private String[] classes;
	private String className;
	private String[] teachers;
	private String[] periods;
	private String hoursHeld;
	private String batchId;
	private String activityId;
	private Map<Integer,String> teachersMap;
	

	private Map<Integer,String> departmentMap;
    private Map<Integer,String>	periodMap = new LinkedHashMap<Integer, String>(); 
	private Map<Integer,String> attendanceTypes;
	private Map<Integer,String> activityMap;
	private Map<Integer,String> classMap = new HashMap<Integer, String>();
	private Map<String,String> subjectMap = new LinkedHashMap<String, String>();
	private Map<Integer,String> batchMap = new HashMap<Integer, String>();
	private String classMandatry;
	private String subjectMandatory;
	private String periodMandatory;
	private String classSelectedIndex;
	private String teacherSelectedIndex;
	private String periodSelectedIndex;
	
	
	// 2nd page related properties.
	private String prefix;
	private String absentees;
	private boolean isSecondPage;
	
	// 3rd page related properties.
	private List<StudentTO> studentList;
	private int halfLength;
	private boolean regNoDisplay;
	
	private String attenType;
	private String acaYear;
	private String attendanceClass;
	private String attendanceSubject;
	private String attendanceTeacher;
	private String attendancePeriod;
	private String attendanceBatchName;
	private String attendanceActivity;
	private Map<String, String> subMap;
	private List<Users> users;
	private String roleId;
	private String accessForRoleId;
	// modify 
	
	public List<Users> getUsers() {
		return users;
	}


	public void setUsers(List<Users> users) {
		this.users = users;
	}


	private String attendanceId;
	private List<AttendanceTO> attendanceList;
	private String oldDate;
	private String newDate;
	private String[] oldPeriods;
	private Boolean isStaff;
	private String numericCode;
	private String slipNo;
	private String isSMSRequired;
	private String displayRequired;
	private List<String> dateList;
    private List<TeacherDepartmentTO> teacherDepartmentTO;
    private String departmentName;
    private String teacherName;
    private List<ClassesTO> classList;
    private List<PeriodTO> periodList;
    private List<SubjectTO> subjectList;
    private List<ActivityTO> activityList;
    private List<AttendanceTypeTO> attendanceTypeList;
    private String classId;
    private String checked;
    private String classIds;
    private List<TeacherClassEntryTo> teachersList;
    private String timeTableFormat;
    private boolean timeTableAvailable;
    private Map<Integer, String> additionalUserMap;
	private Map<Integer,String> additionalPeriods;
	private String[] additionalUserIds;
	private String[] additionalPeriodIds;
	private String[] orginalTeacher;
	private String[] orginalPeriod;
    private String currentPeriodName;
    private String isTheoryPractical;
    private List<Integer> batchList;
    private Map<Integer, Integer> subjectBatchMap;
    private String[] batchIdsArray;
    private String batchNameWithCode;
    private Map<Integer, List<String>> subjectClassMap;
    
    //raghu
    List<String> periodsList;
    private List<List<StudentTO>> studentsList;
    private String empName;
	private Boolean isTimeTable;
	private String[] attendanceInstructorId;
	private String[] attendanceClassId;
	private String[] attendancePeriodId;
	private String periodName;
	private int periodCount;
	private String isMobileAccess;
    private String subjectIdHidden;
    private String[] classesHidden;
    private Map<Integer,String> attCoTeachers;
    private String[] coAtteachers;
    private String dutyType;
    private String classType;
    private String platform;
    private String topic;
    private String dutyTypeOther;
    private String platformOther;
    
    
    
	public String[] getClassesHidden() {
		return classesHidden;
	}


	public void setClassesHidden(String[] classesHidden) {
		this.classesHidden = classesHidden;
	}


	public String getSubjectIdHidden() {
		return subjectIdHidden;
	}


	public void setSubjectIdHidden(String subjectIdHidden) {
		this.subjectIdHidden = subjectIdHidden;
	}


	public String getIsMobileAccess() {
		return isMobileAccess;
	}


	public void setIsMobileAccess(String isMobileAccess) {
		this.isMobileAccess = isMobileAccess;
	}


	public String getPeriodName() {
		return periodName;
	}


	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}


	public int getPeriodCount() {
		return periodCount;
	}


	public void setPeriodCount(int periodCount) {
		this.periodCount = periodCount;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public List<List<StudentTO>> getStudentsList() {
		return studentsList;
	}


	public void setStudentsList(List<List<StudentTO>> studentsList) {
		this.studentsList = studentsList;
	}


	public List<String> getPeriodsList() {
		return periodsList;
	}


	public void setPeriodsList(List<String> periodsList) {
		this.periodsList = periodsList;
	}


	public List<TeacherDepartmentTO> getTeacherDepartmentTO() {
		return teacherDepartmentTO;
	}


	public void setTeacherDepartmentTO(List<TeacherDepartmentTO> teacherDepartmentTO) {
		this.teacherDepartmentTO = teacherDepartmentTO;
	}


	/**
	 * @return the attendanceTypes
	 */
	public Map<Integer, String> getAttendanceTypes() {
		return attendanceTypes;
	}


	/**
	 * @param attendanceTypes the attendanceTypes to set
	 */
	public void setAttendanceTypes(Map<Integer, String> attendanceTypes) {
		this.attendanceTypes = attendanceTypes;
	}


	private String teacherMandatory;
	private String batchMandatory;
	private String activityTypeMandatory;
	private List<Department> departmentList;
	private String departmentId;
	// vinodha
	private String timeHours;
	private String timeMins;
	private String origAttTimeHours;
	private String origAttTimeMins;
	private String  errormsg;
	private List<AttendanceTO> attTimeRestrictedList;
	private int attTimeRestrictionId;


	public void clear() {
		super.clear();
		this.teachers = null;
		this.periods =null;
		this.hoursHeld = null;
		this.batchId = null;
		this.activityId = null;
		this.periodMap = new LinkedHashMap<Integer, String>();
		this.subjectMap = new LinkedHashMap<String, String>();
		this.subMap=new TreeMap<String, String>();
		this.batchMap = new HashMap<Integer, String>();
		this.activityMap = new HashMap<Integer, String>();
		this.prefix = null;
		this.absentees = null;
		this.acaYear= null;
		this.attendanceClass= null;
		this.className=null;
		this.attendanceSubject= null;
		this.attendanceTeacher= null;
		this.attendancePeriod= null;
		this.attendanceBatchName= null;
		this.attendanceActivity= null;
		this.regNoDisplay = false;
		this.classMandatry = null;
		this.teacherMandatory = null;
		this.periodMandatory = null;
		this.subjectMandatory = null;
		this.batchMandatory = null;
		this.activityTypeMandatory = null;
		this.isSecondPage = false;
		this.numericCode=null;
		this.slipNo=null;
		this.isSMSRequired="No";
		this.departmentId=null;
		this.teachersList=null;
		this.timeTableFormat="yes";
		this.batchList=null;
		this.subjectBatchMap=null;
		this.batchIdsArray=null;
		this.batchNameWithCode=null;
		this.subjectClassMap=null;
		this.errormsg=null;
	}

	public void clearAll(){
		clear();
		super.attendanceTypeId = null;
		this.classes = null;
		this.attendanceList = new ArrayList<AttendanceTO>();
		this.isStaff=false;
		this.batchId=null;
		this.fromDate=null;
		this.toDate=null;
		this.attendancedate=null;
		this.classList=null;
		this.attendanceTypeList=null;
		this.activityList=null;
		this.periodList=null;
		this.subjectList=null;
		this.additionalPeriods=null;
		this.additionalUserMap=null;
		this.additionalPeriodIds=null;
		this.additionalUserIds=null;
		this.orginalPeriod=null;
		this.orginalTeacher=null;
		this.currentPeriodName=null;
		this.errormsg=null;
	}
	
	public Map<String, String> getSubMap() {
		return subMap;
	}

	public void setSubMap(Map<String, String> subMap) {
		this.subMap = subMap;
	} 
	
	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}

	
	/**
	 * @return the classes
	 */
	public String[] getClasses() {
		return classes;
	}


	/**
	 * @param classes the classes to set
	 */
	public void setClasses(String[] classes) {
		this.classes = classes;
	}


	
	/**
	 * @return the teachers
	 */
	public String[] getTeachers() {
		return teachers;
	}


	/**
	 * @param teachers the teachers to set
	 */
	public void setTeachers(String[] teachers) {
		this.teachers = teachers;
	}


	/**
	 * @return the period
	 */
	public String[] getPeriods() {
		return periods;
	}


	/**
	 * @param period the period to set
	 */
	public void setPeriods(String[] periods) {
		this.periods = periods;
	}


	/**
	 * @return the hoursHeld
	 */
	public String getHoursHeld() {
		return hoursHeld;
	}


	/**
	 * @param hoursHeld the hoursHeld to set
	 */
	public void setHoursHeld(String hoursHeld) {
		this.hoursHeld = hoursHeld;
	}


	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}


	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}


	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}


	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}


	/**
	 * @return the teachersMap
	 */
	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}


	/**
	 * @param teachersMap the teachersMap to set
	 */
	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}


	/**
	 * @return the periodMap
	 */
	public Map<Integer, String> getPeriodMap() {
		return periodMap;
	}


	/**
	 * @param periodMap the periodMap to set
	 */
	public void setPeriodMap(Map<Integer, String> periodMap) {
		this.periodMap = periodMap;
	}



	/**
	 * @return the classMandatry
	 */
	public String getClassMandatry() {
		return classMandatry;
	}


	/**
	 * @param classMandatry the classMandatry to set
	 */
	public void setClassMandatry(String classMandatry) {
		this.classMandatry = classMandatry;
	}


	/**
	 * @return the subjectMandatory
	 */
	public String getSubjectMandatory() {
		return subjectMandatory;
	}


	/**
	 * @param subjectMandatory the subjectMandatory to set
	 */
	public void setSubjectMandatory(String subjectMandatory) {
		this.subjectMandatory = subjectMandatory;
	}


	/**
	 * @return the periodMandatory
	 */
	public String getPeriodMandatory() {
		return periodMandatory;
	}


	/**
	 * @param periodMandatory the periodMandatory to set
	 */
	public void setPeriodMandatory(String periodMandatory) {
		this.periodMandatory = periodMandatory;
	}


	/**
	 * @return the teacherMandatory
	 */
	public String getTeacherMandatory() {
		return teacherMandatory;
	}


	/**
	 * @param teacherMandatory the teacherMandatory to set
	 */
	public void setTeacherMandatory(String teacherMandatory) {
		this.teacherMandatory = teacherMandatory;
	}


	/**
	 * @return the batchMandatory
	 */
	public String getBatchMandatory() {
		return batchMandatory;
	}


	/**
	 * @param batchMandatory the batchMandatory to set
	 */
	public void setBatchMandatory(String batchMandatory) {
		this.batchMandatory = batchMandatory;
	}

	
	/**
	 * @return the activityTypeMandatory
	 */
	public String getActivityTypeMandatory() {
		return activityTypeMandatory;
	}


	/**
	 * @param activityTypeMandatory the activityTypeMandatory to set
	 */
	public void setActivityTypeMandatory(String activityTypeMandatory) {
		this.activityTypeMandatory = activityTypeMandatory;
	}


	/**
	 * @return the attendancedate
	 */
	public String getAttendancedate() {
		return attendancedate;
	}


	/**
	 * @param attendancedate the attendancedate to set
	 */
	public void setAttendancedate(String attendancedate) {
		this.attendancedate = attendancedate;
	}


	/**
	 * @return the activityMap
	 */
	public Map<Integer, String> getActivityMap() {
		return activityMap;
	}


	/**
	 * @param activityMap the activityMap to set
	 */
	public void setActivityMap(Map<Integer, String> activityMap) {
		this.activityMap = activityMap;
	}


	/**
	 * @return the classMap
	 */
	public Map<Integer, String> getClassMap() {
		return classMap;
	}


	/**
	 * @param classMap the classMap to set
	 */
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}





	/**
	 * @return the batchMap
	 */
	public Map<Integer, String> getBatchMap() {
		return batchMap;
	}


	/**
	 * @param batchMap the batchMap to set
	 */
	public void setBatchMap(Map<Integer, String> batchMap) {
		this.batchMap = batchMap;
	}


	

	/**
	 * @return the classSelectedIndex
	 */
	public String getClassSelectedIndex() {
		return classSelectedIndex;
	}


	/**
	 * @param classSelectedIndex the classSelectedIndex to set
	 */
	public void setClassSelectedIndex(String classSelectedIndex) {
		this.classSelectedIndex = classSelectedIndex;
	}


	/**
	 * @return the teacherSelectedIndex
	 */
	public String getTeacherSelectedIndex() {
		return teacherSelectedIndex;
	}


	/**
	 * @param teacherSelectedIndex the teacherSelectedIndex to set
	 */
	public void setTeacherSelectedIndex(String teacherSelectedIndex) {
		this.teacherSelectedIndex = teacherSelectedIndex;
	}


	/**
	 * @return the periodSelectedIndex
	 */
	public String getPeriodSelectedIndex() {
		return periodSelectedIndex;
	}


	/**
	 * @param periodSelectedIndex the periodSelectedIndex to set
	 */
	public void setPeriodSelectedIndex(String periodSelectedIndex) {
		this.periodSelectedIndex = periodSelectedIndex;
	}


	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}


	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	/**
	 * @return the absentees
	 */
	public String getAbsentees() {
		return absentees;
	}


	/**
	 * @param absentees the absentees to set
	 */
	public void setAbsentees(String absentees) {
		this.absentees = absentees;
	}


	/**
	 * @return the studentList
	 */
	public List<StudentTO> getStudentList() {
		return studentList;
	}


	/**
	 * @param studentList the studentList to set
	 */
	public void setStudentList(List<StudentTO> studentList) {
		this.studentList = studentList;
	}


	/**
	 * @return the halfLength
	 */
	public int getHalfLength() {
		return halfLength;
	}


	/**
	 * @param halfLength the halfLength to set
	 */
	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}


	/**
	 * @return the attenType
	 */
	public String getAttenType() {
		return attenType;
	}


	/**
	 * @param attenType the attenType to set
	 */
	public void setAttenType(String attenType) {
		this.attenType = attenType;
	}


	/**
	 * @return the acaYear
	 */
	public String getAcaYear() {
		return acaYear;
	}


	/**
	 * @return the isRegNoDisplay
	 */
	public boolean getRegNoDisplay() {
		return regNoDisplay;
	}


	/**
	 * @param isRegNoDisplay the isRegNoDisplay to set
	 */
	public void setRegNoDisplay(boolean regNoDisplay) {
		this.regNoDisplay = regNoDisplay;
	}


	/**
	 * @param acaYear the acaYear to set
	 */
	public void setAcaYear(String acaYear) {
		this.acaYear = acaYear;
	}


	/**
	 * @return the attendanceClass
	 */
	public String getAttendanceClass() {
		return attendanceClass;
	}


	/**
	 * @param attendanceClass the attendanceClass to set
	 */
	public void setAttendanceClass(String attendanceClass) {
		this.attendanceClass = attendanceClass;
	}


	/**
	 * @return the attendanceSubject
	 */
	public String getAttendanceSubject() {
		return attendanceSubject;
	}


	/**
	 * @param attendanceSubject the attendanceSubject to set
	 */
	public void setAttendanceSubject(String attendanceSubject) {
		this.attendanceSubject = attendanceSubject;
	}


	/**
	 * @return the attendanceTeacher
	 */
	public String getAttendanceTeacher() {
		return attendanceTeacher;
	}


	/**
	 * @param attendanceTeacher the attendanceTeacher to set
	 */
	public void setAttendanceTeacher(String attendanceTeacher) {
		this.attendanceTeacher = attendanceTeacher;
	}


	/**
	 * @return the attendancePeriod
	 */
	public String getAttendancePeriod() {
		return attendancePeriod;
	}


	/**
	 * @param attendancePeriod the attendancePeriod to set
	 */
	public void setAttendancePeriod(String attendancePeriod) {
		this.attendancePeriod = attendancePeriod;
	}


	/**
	 * @return the attendanceBatchName
	 */
	public String getAttendanceBatchName() {
		return attendanceBatchName;
	}


	/**
	 * @param attendanceBatchName the attendanceBatchName to set
	 */
	public void setAttendanceBatchName(String attendanceBatchName) {
		this.attendanceBatchName = attendanceBatchName;
	}


	/**
	 * @return the attendanceActivity
	 */
	public String getAttendanceActivity() {
		return attendanceActivity;
	}


	/**
	 * @param attendanceActivity the attendanceActivity to set
	 */
	public void setAttendanceActivity(String attendanceActivity) {
		this.attendanceActivity = attendanceActivity;
	}


	/**
	 * @return the attendanceId
	 */
	public String getAttendanceId() {
		return attendanceId;
	}


	/**
	 * @param attendanceId the attendanceId to set
	 */
	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
	}


	/**
	 * @return the attendanceList
	 */
	public List<AttendanceTO> getAttendanceList() {
		return attendanceList;
	}


	/**
	 * @param attendanceList the attendanceList to set
	 */
	public void setAttendanceList(List<AttendanceTO> attendanceList) {
		this.attendanceList = attendanceList;
	}


	/**
	 * @return the oldDate
	 */
	public String getOldDate() {
		return oldDate;
	}


	/**
	 * @param oldDate the oldDate to set
	 */
	public void setOldDate(String oldDate) {
		this.oldDate = oldDate;
	}


	/**
	 * @return the oldPeriods
	 */
	public String[] getOldPeriods() {
		return oldPeriods;
	}


	/**
	 * @param oldPeriods the oldPeriods to set
	 */
	public void setOldPeriods(String[] oldPeriods) {
		this.oldPeriods = oldPeriods;
	}


	/**
	 * @return the isSecondPage
	 */
	public boolean getIsSecondPage() {
		return isSecondPage;
	}


	/**
	 * @param isSecondPage the isSecondPage to set
	 */
	public void setSecondPage(boolean isSecondPage) {
		this.isSecondPage = isSecondPage;
	}


	public Boolean getIsStaff() {
		return isStaff;
	}


	public void setIsStaff(Boolean isStaff) {
		this.isStaff = isStaff;
	}


	public String getNumericCode() {
		return numericCode;
	}


	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}


	public String getSlipNo() {
		return slipNo;
	}


	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}


	public String getIsSMSRequired() {
		return isSMSRequired;
	}


	public void setIsSMSRequired(String isSMSRequired) {
		this.isSMSRequired = isSMSRequired;
	}


	public String getDisplayRequired() {
		return displayRequired;
	}
	public void setDisplayRequired(String displayRequired) {
		this.displayRequired = displayRequired;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getNewDate() {
		return newDate;
	}


	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}

	public List<String> getDateList() {
		return dateList;
	}


	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.forms.BaseActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		boolean invalidDate = false;
		if(this.classSelectedIndex != null && this.classSelectedIndex.equals("-1")) {
			this.classes = null;
		}
		if(this.teacherSelectedIndex != null && this.teacherSelectedIndex.equals("-1")) {
			this.teachers = null;
		}
		if(this.periodSelectedIndex != null && this.periodSelectedIndex.equals("-1")) {
			this.periods = null;
		}
		if(this.classMandatry != null && this.classMandatry.equalsIgnoreCase("yes")){
			String[] selectedClasses=this.classes;
			boolean classError=true;
			if(selectedClasses != null){
				for (int i = 0; i < selectedClasses.length; i++) {
					if(selectedClasses[i] != null){
						classError=false;
					}
				}
			}
			if(classError){
				actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_CLASS_REQUIRED));
			}
		}
		if(this.classMandatry != null && this.classMandatry.equalsIgnoreCase("yes") && (this.classes == null || this.classes.length == 0)) {
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_CLASS_REQUIRED));
		} 
		if(this.subjectMandatory != null &&  this.subjectMandatory.equals("yes") && (this.subjectId == null || this.subjectId.length() == 0)) {
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_SUBJECT_REQUIRED));
		}
		if(this.teacherMandatory != null  && this.teacherMandatory.equals("yes") && (this.teachers == null || this.teachers.length ==0 )) {
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_TEACHER_REQUIRED));
		}
		if(this.periodMandatory != null && this.periodMandatory.equals("yes")){
			String[] selectedPeriods=this.periods;
			boolean periodError=true;
			if(selectedPeriods != null){
				for (int i = 0; i < selectedPeriods.length; i++) {
					if(selectedPeriods[i] != null){
						periodError=false;
					}
				}
			}
			if(periodError){
				actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_PERIOD_REQUIRED));
			}
		}
		if(this.periodMandatory != null && this.periodMandatory.equals("yes") && (this.periods == null || this.periods.length ==0 )) {
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_PERIOD_REQUIRED));		
		}
		//batchIdsArray Added by mahi
		if(this.batchId == null || this.batchId.length() ==0 ){
			if(this.batchIdsArray==null || this.batchIdsArray.length==0){
				if(this.batchMandatory != null && this.batchMandatory.equals("yes") && (this.batchId == null || this.batchId.length() ==0 )) {
					actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_BATCH_REQUIRED));
				}	
			}else{
				for (String batchId : this.batchIdsArray) {
					if(batchId==null || batchId.isEmpty()){
						if(this.batchMandatory != null && this.batchMandatory.equals("yes") && (batchId == null || batchId.isEmpty())) {
							actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_BATCH_REQUIRED));
						}	
					}
					}
			}
		}
		if(this.activityTypeMandatory != null && this.activityTypeMandatory.equals("yes") && (this.activityId == null || this.activityId.length() ==0 )) {
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_ACTIVITY_REQUIRED));
		}
		if(this.hoursHeld != null && this.hoursHeld.trim().equals("0")){
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_HOURSHELD_NOTZERO));
		}
		if(this.attendancedate!=null && !StringUtils.isEmpty(this.attendancedate)&& !CommonUtil.isValidDate(this.attendancedate)){
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			invalidDate = true;
		}	
		if(this.activityId !=null && this.activityId.length()!=0 && this.subjectId != null && this.subjectId.length() !=0) 
		{
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_BOTHATTENSUB_NOTALLOWED));
		}
		
		if(this.attendancedate != null && this.attendancedate.length() != 0 && !invalidDate) {
		String formattedString=CommonUtil.ConvertStringToDateFormat(this.attendancedate, "dd/MM/yyyy","MM/dd/yyyy");
		Date date = new Date(formattedString);
		Date curdate = new Date();
		if (date.compareTo(curdate) == 1) {
			actionErrors.add("errors",new ActionMessage(CMSConstants.FEE_NO_FUTURE_DATE_));
		}
		}
		return actionErrors;
	}
	

	public List<Department> getDepartmentList() {
		return departmentList;
	}


	public String getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}


	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}


	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public String getTeacherName() {
		return teacherName;
	}


	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


	public List<ClassesTO> getClassList() {
		return classList;
	}


	public void setClassList(List<ClassesTO> classList) {
		this.classList = classList;
	}


	public List<PeriodTO> getPeriodList() {
		return periodList;
	}


	public void setPeriodList(List<PeriodTO> periodList) {
		this.periodList = periodList;
	}


	public List<SubjectTO> getSubjectList() {
		return subjectList;
	}


	public void setSubjectList(List<SubjectTO> subjectList) {
		this.subjectList = subjectList;
	}


	public List<ActivityTO> getActivityList() {
		return activityList;
	}


	public void setActivityList(List<ActivityTO> activityList) {
		this.activityList = activityList;
	}


	public List<AttendanceTypeTO> getAttendanceTypeList() {
		return attendanceTypeList;
	}


	public void setAttendanceTypeList(List<AttendanceTypeTO> attendanceTypeList) {
		this.attendanceTypeList = attendanceTypeList;
	}


	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public String getChecked() {
		return checked;
	}


	public void setChecked(String checked) {
		this.checked = checked;
	}


	public String getClassIds() {
		return classIds;
	}


	public void setClassIds(String classIds) {
		this.classIds = classIds;
	}


	public List<TeacherClassEntryTo> getTeachersList() {
		return teachersList;
	}


	public void setTeachersList(List<TeacherClassEntryTo> teachersList) {
		this.teachersList = teachersList;
	}


	public String getTimeTableFormat() {
		return timeTableFormat;
	}


	public void setTimeTableFormat(String timeTableFormat) {
		this.timeTableFormat = timeTableFormat;
	}


	public boolean isTimeTableAvailable() {
		return timeTableAvailable;
	}


	public void setTimeTableAvailable(boolean timeTableAvailable) {
		this.timeTableAvailable = timeTableAvailable;
	}


	public Map<Integer, String> getAdditionalUserMap() {
		return additionalUserMap;
	}


	public void setAdditionalUserMap(Map<Integer, String> additionalUserMap) {
		this.additionalUserMap = additionalUserMap;
	}


	public Map<Integer, String> getAdditionalPeriods() {
		return additionalPeriods;
	}


	public void setAdditionalPeriods(Map<Integer, String> additionalPeriods) {
		this.additionalPeriods = additionalPeriods;
	}


	public String[] getAdditionalUserIds() {
		return additionalUserIds;
	}


	public void setAdditionalUserIds(String[] additionalUserIds) {
		this.additionalUserIds = additionalUserIds;
	}


	public String[] getAdditionalPeriodIds() {
		return additionalPeriodIds;
	}


	public void setAdditionalPeriodIds(String[] additionalPeriodIds) {
		this.additionalPeriodIds = additionalPeriodIds;
	}


	public String[] getOrginalTeacher() {
		return orginalTeacher;
	}


	public void setOrginalTeacher(String[] orginalTeacher) {
		this.orginalTeacher = orginalTeacher;
	}


	public String[] getOrginalPeriod() {
		return orginalPeriod;
	}


	public void setOrginalPeriod(String[] orginalPeriod) {
		this.orginalPeriod = orginalPeriod;
	}


	public String getCurrentPeriodName() {
		return currentPeriodName;
	}


	public void setCurrentPeriodName(String currentPeriodName) {
		this.currentPeriodName = currentPeriodName;
	}


	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}


	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}


	public Map<Integer, String> getDepartmentMap() {
		return departmentMap;
	}


	public void setDepartmentMap(Map<Integer, String> departmentMap) {
		this.departmentMap = departmentMap;
	}


	public Map<String, String> getSubjectMap() {
		return subjectMap;
	}


	public void setSubjectMap(Map<String, String> subjectMap) {
		this.subjectMap = subjectMap;
	}


	public List<Integer> getBatchList() {
		return batchList;
	}


	public void setBatchList(List<Integer> batchList) {
		this.batchList = batchList;
	}


	public Map<Integer, Integer> getSubjectBatchMap() {
		return subjectBatchMap;
	}


	public void setSubjectBatchMap(Map<Integer, Integer> subjectBatchMap) {
		this.subjectBatchMap = subjectBatchMap;
	}

	public String[] getBatchIdsArray() {
		return batchIdsArray;
	}


	public void setBatchIdsArray(String[] batchIdsArray) {
		this.batchIdsArray = batchIdsArray;
	}


	public String getBatchNameWithCode() {
		return batchNameWithCode;
	}


	public void setBatchNameWithCode(String batchNameWithCode) {
		this.batchNameWithCode = batchNameWithCode;
	}


	public Map<Integer, List<String>> getSubjectClassMap() {
		return subjectClassMap;
	}


	public void setSubjectClassMap(Map<Integer, List<String>> subjectClassMap) {
		this.subjectClassMap = subjectClassMap;
	}


	public Boolean getIsTimeTable() {
		return isTimeTable;
	}


	public void setIsTimeTable(Boolean isTimeTable) {
		this.isTimeTable = isTimeTable;
	}


	public String[] getAttendanceInstructorId() {
		return attendanceInstructorId;
	}


	public void setAttendanceInstructorId(String[] attendanceInstructorId) {
		this.attendanceInstructorId = attendanceInstructorId;
	}


	public String[] getAttendanceClassId() {
		return attendanceClassId;
	}


	public void setAttendanceClassId(String[] attendanceClassId) {
		this.attendanceClassId = attendanceClassId;
	}


	public String[] getAttendancePeriodId() {
		return attendancePeriodId;
	}


	public void setAttendancePeriodId(String[] attendancePeriodId) {
		this.attendancePeriodId = attendancePeriodId;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getAccessForRoleId() {
		return accessForRoleId;
	}


	public void setAccessForRoleId(String accessForRoleId) {
		this.accessForRoleId = accessForRoleId;
	}


	public String getErrormsg() {
		return errormsg;
	}


	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}


	public String getTimeHours() {
		return timeHours;
	}


	public void setTimeHours(String timeHours) {
		this.timeHours = timeHours;
	}


	public String getTimeMins() {
		return timeMins;
	}


	public void setTimeMins(String timeMins) {
		this.timeMins = timeMins;
	}
	
	public List<AttendanceTO> getAttTimeRestrictedList() {
		return attTimeRestrictedList;
	}


	public void setAttTimeRestrictedList(List<AttendanceTO> attTimeRestrictedList) {
		this.attTimeRestrictedList = attTimeRestrictedList;
	}


	public int getAttTimeRestrictionId() {
		return attTimeRestrictionId;
	}


	public void setAttTimeRestrictionId(int attTimeRestrictionId) {
		this.attTimeRestrictionId = attTimeRestrictionId;
	}


	public String getOrigAttTimeHours() {
		return origAttTimeHours;
	}


	public void setOrigAttTimeHours(String origAttTimeHours) {
		this.origAttTimeHours = origAttTimeHours;
	}


	public String getOrigAttTimeMins() {
		return origAttTimeMins;
	}


	public void setOrigAttTimeMins(String origAttTimeMins) {
		this.origAttTimeMins = origAttTimeMins;
	}
	
	public ActionErrors validate1(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}


	public Map<Integer, String> getAttCoTeachers() {
		return attCoTeachers;
	}


	public void setAttCoTeachers(Map<Integer, String> attCoTeachers) {
		this.attCoTeachers = attCoTeachers;
	}


	public String[] getCoAtteachers() {
		return coAtteachers;
	}


	public void setCoAtteachers(String[] coAtteachers) {
		this.coAtteachers = coAtteachers;
	}


	public String getDutyType() {
		return dutyType;
	}


	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}


	


	public String getPlatform() {
		return platform;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public String getClassType() {
		return classType;
	}


	public void setClassType(String classType) {
		this.classType = classType;
	}


	public String getDutyTypeOther() {
		return dutyTypeOther;
	}


	public void setDutyTypeOther(String dutyTypeOther) {
		this.dutyTypeOther = dutyTypeOther;
	}


	public String getPlatformOther() {
		return platformOther;
	}


	public void setPlatformOther(String platformOther) {
		this.platformOther = platformOther;
	}
	

}
