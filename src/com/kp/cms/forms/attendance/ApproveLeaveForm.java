package com.kp.cms.forms.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.StudentLeaveTO;

public class ApproveLeaveForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String classSchemewiseId;

	private String fromDate;

	private String toDate;

	private String fromPeriod;

	private String toPeriod;

	private String registerNoEntry;

	private boolean isRegisterNo;
	
	private String leaveType;
	
	private ArrayList<String> registerNoList;

	private String year;
	
	private String id;
	
	private Period periodFrom;
	
	private Period periodTo;
	
	private List<StudentLeaveTO> leaveList = new ArrayList<StudentLeaveTO>(); 
	
	private Map<Integer,String> classMap = new HashMap<Integer, String>();
	private Map<Integer,String> periodMap = new HashMap<Integer, String>();
	private Map<Integer,String> leaveMap = new HashMap<Integer, String>();
	
	private Set<String> registerNoSet;
	
	private String oldRegisterNoEntry;
	private StudentLeaveTO studentLeaveTO;
	
	
	private List<AttendanceTypeTO> attendanceTypeList;
	
	private String activityTypeId;
	private String dummyFromDate;
	private String dummyToDate;
	private String checkyesNo;
	private String notSelectedClassIds;
	private Map<Integer,String> mapSelectedClass;
	private String classSchemewiseIds;
	private String fullDay;
	private Boolean periodSelected;
	private String selectedClassesArray1;
	private String classValues;
	private String existingCurrLeaveRegNos;
	
	private String fromPeriodName;
	private String toPeriodName;
	private String futureFromPeriod;
	private String futureToPeriod;
	
	
	
	private String semType;
	
	
	
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

	public String getFromPeriod() {
		return fromPeriod;
	}

	public void setFromPeriod(String fromPeriod) {
		this.fromPeriod = fromPeriod;
	}

	public String getToPeriod() {
		return toPeriod;
	}

	public void setToPeriod(String toPeriod) {
		this.toPeriod = toPeriod;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getClassSchemewiseId() {
		return classSchemewiseId;
	}

	public void setClassSchemewiseId(String classSchemewiseId) {
		this.classSchemewiseId = classSchemewiseId;
	}

	public String getRegisterNoEntry() {
		return registerNoEntry;
	}

	public void setRegisterNoEntry(String registerNoEntry) {
		this.registerNoEntry = registerNoEntry;
	}

	public boolean isRegisterNo() {
		return isRegisterNo;
	}

	public void setRegisterNo(boolean isRegisterNo) {
		this.isRegisterNo = isRegisterNo;
	}

	public void resetFields() {
		this.classSchemewiseId = null;
		this.fromDate = null;
		this.toDate = null;
		this.fromPeriod = null;
		this.toPeriod = null;
		this.registerNoEntry = null;
		this.leaveType = null;
		this.studentLeaveTO = null;
		this.leaveList = new ArrayList<StudentLeaveTO>();
		this.activityTypeId = null;
		this.attendanceTypeId =  null;
		this.attendanceTypeList = null;
		this.id="0";
		this.checkyesNo=null;
		this.fullDay="No";
		this.periodSelected=false;
		this.registerNoList=null;
		this.mapSelectedClass=null;
		this.semType=null;
		this.fromPeriodName=null;
		this.toPeriodName=null;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public ArrayList<String> getRegisterNoList() {
		return registerNoList;
	}

	public void setRegisterNoList(ArrayList<String> registerNoList) {
		this.registerNoList = registerNoList;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	/**
	 * @return the leaveList
	 */
	public List<StudentLeaveTO> getLeaveList() {
		return leaveList;
	}

	/**
	 * @param leaveList the leaveList to set
	 */
	public void setLeaveList(List<StudentLeaveTO> leaveList) {
		this.leaveList = leaveList;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the leaveMap
	 */
	public Map<Integer, String> getLeaveMap() {
		return leaveMap;
	}

	/**
	 * @param leaveMap the leaveMap to set
	 */
	public void setLeaveMap(Map<Integer, String> leaveMap) {
		this.leaveMap = leaveMap;
	}
	
	/**
	 * @return the studentLeaveTO
	 */
	public StudentLeaveTO getStudentLeaveTO() {
		return studentLeaveTO;
	}

	/**
	 * @param studentLeaveTO the studentLeaveTO to set
	 */
	public void setStudentLeaveTO(StudentLeaveTO studentLeaveTO) {
		this.studentLeaveTO = studentLeaveTO;
	}

	/**
	 * @return the oldRegisterNoEntry
	 */
	public String getOldRegisterNoEntry() {
		return oldRegisterNoEntry;
	}

	/**
	 * @param oldRegisterNoEntry the oldRegisterNoEntry to set
	 */
	public void setOldRegisterNoEntry(String oldRegisterNoEntry) {
		this.oldRegisterNoEntry = oldRegisterNoEntry;
	}

	public Set<String> getRegisterNoSet() {
		return registerNoSet;
	}

	public void setRegisterNoSet(Set<String> registerNoSet) {
		this.registerNoSet = registerNoSet;
	}

	public Period getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Period periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Period getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Period periodTo) {
		this.periodTo = periodTo;
	}

	public List<AttendanceTypeTO> getAttendanceTypeList() {
		return attendanceTypeList;
	}

	public void setAttendanceTypeList(List<AttendanceTypeTO> attendanceTypeList) {
		this.attendanceTypeList = attendanceTypeList;
	}

	public String getActivityTypeId() {
		return activityTypeId;
	}

	public void setActivityTypeId(String activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

	public String getDummyFromDate() {
		return dummyFromDate;
	}

	public void setDummyFromDate(String dummyFromDate) {
		this.dummyFromDate = dummyFromDate;
	}

	public String getDummyToDate() {
		return dummyToDate;
	}

	public void setDummyToDate(String dummyToDate) {
		this.dummyToDate = dummyToDate;
	}

	public String getCheckyesNo() {
		return checkyesNo;
	}

	public void setCheckyesNo(String checkyesNo) {
		this.checkyesNo = checkyesNo;
	}

	public String getNotSelectedClassIds() {
		return notSelectedClassIds;
	}

	public void setNotSelectedClassIds(String notSelectedClassIds) {
		this.notSelectedClassIds = notSelectedClassIds;
	}

	public Map<Integer, String> getMapSelectedClass() {
		return mapSelectedClass;
	}

	public void setMapSelectedClass(Map<Integer, String> mapSelectedClass) {
		this.mapSelectedClass = mapSelectedClass;
	}

	public String getClassSchemewiseIds() {
		return classSchemewiseIds;
	}

	public void setClassSchemewiseIds(String classSchemewiseIds) {
		this.classSchemewiseIds = classSchemewiseIds;
	}

	public String getFullDay() {
		return fullDay;
	}

	public void setFullDay(String fullDay) {
		this.fullDay = fullDay;
	}

	public Boolean getPeriodSelected() {
		return periodSelected;
	}

	public void setPeriodSelected(Boolean periodSelected) {
		this.periodSelected = periodSelected;
	}

	public String getSelectedClassesArray1() {
		return selectedClassesArray1;
	}

	public void setSelectedClassesArray1(String selectedClassesArray1) {
		this.selectedClassesArray1 = selectedClassesArray1;
	}

	public String getClassValues() {
		return classValues;
	}

	public void setClassValues(String classValues) {
		this.classValues = classValues;
	}

	public String getExistingCurrLeaveRegNos() {
		return existingCurrLeaveRegNos;
	}

	public void setExistingCurrLeaveRegNos(String existingCurrLeaveRegNos) {
		this.existingCurrLeaveRegNos = existingCurrLeaveRegNos;
	}

	public String getSemType() {
		return semType;
	}

	public void setSemType(String semType) {
		this.semType = semType;
	}

	public String getFromPeriodName() {
		return fromPeriodName;
	}

	public void setFromPeriodName(String fromPeriodName) {
		this.fromPeriodName = fromPeriodName;
	}

	public String getToPeriodName() {
		return toPeriodName;
	}

	public void setToPeriodName(String toPeriodName) {
		this.toPeriodName = toPeriodName;
	}

	public String getFutureFromPeriod() {
		return futureFromPeriod;
	}

	public void setFutureFromPeriod(String futureFromPeriod) {
		this.futureFromPeriod = futureFromPeriod;
	}

	public String getFutureToPeriod() {
		return futureToPeriod;
	}

	public void setFutureToPeriod(String futureToPeriod) {
		this.futureToPeriod = futureToPeriod;
	}

}
