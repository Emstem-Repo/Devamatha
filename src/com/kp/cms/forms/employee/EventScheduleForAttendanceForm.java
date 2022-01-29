package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EventScheduleForAttendanceTo;

public class EventScheduleForAttendanceForm extends BaseActionForm{
	private int id;
	private String type;
	private String eventDesc;
	private String eventLocation;
	private String eventDate;
	private String timeFrom;
	private String timeFromMin;
	private String timeTo;
	private String timeToMIn;
	private Map<String,String> eventLocationMap;
	private Map<Integer, String> mapClass;
	private Map<Integer, String> mapSelectedClass;
	private Map<Integer, Integer> oldMapSelectedClass;
	private Map<Integer, Integer> oldDeptMap;
	private String deptSelectedFrom;
	private String deptSelectedTo;
	private String isAttendanceRequired;
	private String classIdsFrom;
	private String classIdsTo;
	private String fromPeriod;
	private String toPeriod;
	private Map<Integer,String> periodMap;
	private String classValues;
	private String dupClasses;
	private String dupDepartments;
	private boolean flag;
	private boolean flag1;
	private Map<Integer, String> mapDept;
	private Map<Integer, String> mapSelectedDept;
	private List<EventScheduleForAttendanceTo> studentList;
	private List<EventScheduleForAttendanceTo> staffList;
	private String year;
	private String oldEventDate;
	private Map<Integer, Integer> inActiveStudentIdsMap;
	private Map<Integer, Integer> inActiveStaffIdsMap;
	private Boolean periodSelected;
	
	
	public Map<Integer, Integer> getInActiveStudentIdsMap() {
		return inActiveStudentIdsMap;
	}
	public void setInActiveStudentIdsMap(Map<Integer, Integer> inActiveStudentIdsMap) {
		this.inActiveStudentIdsMap = inActiveStudentIdsMap;
	}
	public Map<Integer, Integer> getInActiveStaffIdsMap() {
		return inActiveStaffIdsMap;
	}
	public void setInActiveStaffIdsMap(Map<Integer, Integer> inActiveStaffIdsMap) {
		this.inActiveStaffIdsMap = inActiveStaffIdsMap;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	public String getTimeFromMin() {
		return timeFromMin;
	}
	public void setTimeFromMin(String timeFromMin) {
		this.timeFromMin = timeFromMin;
	}
	public String getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}
	public String getTimeToMIn() {
		return timeToMIn;
	}
	public void setTimeToMIn(String timeToMIn) {
		this.timeToMIn = timeToMIn;
	}
	public void reset(){
		this.eventDesc=null;
		this.eventLocation=null;
		this.eventDate=null;
		this.timeFrom="00";
		this.timeFromMin="00";
		this.timeTo="00";
		this.timeToMIn="00";
		this.type="Student";
		this.isAttendanceRequired="No";
		this.flag=false;
		this.flag1=false;
		this.year=null;
		this.mapSelectedClass=null;
		this.mapSelectedDept=null;
		this.inActiveStudentIdsMap=null;
		this.inActiveStaffIdsMap=null;
		this.periodSelected=false;
	}
	public void resetFoeStaff(){
		this.type="Staff";
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public Map<String, String> getEventLocationMap() {
		return eventLocationMap;
	}
	public void setEventLocationMap(Map<String, String> eventLocationMap) {
		this.eventLocationMap = eventLocationMap;
	}
	public Map<Integer, String> getMapClass() {
		return mapClass;
	}
	public void setMapClass(Map<Integer, String> mapClass) {
		this.mapClass = mapClass;
	}
	public Map<Integer, String> getMapSelectedClass() {
		return mapSelectedClass;
	}
	public void setMapSelectedClass(Map<Integer, String> mapSelectedClass) {
		this.mapSelectedClass = mapSelectedClass;
	}
	public String getDeptSelectedFrom() {
		return deptSelectedFrom;
	}
	public void setDeptSelectedFrom(String deptSelectedFrom) {
		this.deptSelectedFrom = deptSelectedFrom;
	}
	public String getDeptSelectedTo() {
		return deptSelectedTo;
	}
	public void setDeptSelectedTo(String deptSelectedTo) {
		this.deptSelectedTo = deptSelectedTo;
	}
	public String getIsAttendanceRequired() {
		return isAttendanceRequired;
	}
	public void setIsAttendanceRequired(String isAttendanceRequired) {
		this.isAttendanceRequired = isAttendanceRequired;
	}
	public String getClassIdsFrom() {
		return classIdsFrom;
	}
	public void setClassIdsFrom(String classIdsFrom) {
		this.classIdsFrom = classIdsFrom;
	}
	public String getClassIdsTo() {
		return classIdsTo;
	}
	public void setClassIdsTo(String classIdsTo) {
		this.classIdsTo = classIdsTo;
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
	
	public String getClassValues() {
		return classValues;
	}
	public void setClassValues(String classValues) {
		this.classValues = classValues;
	}
	public String getDupClasses() {
		return dupClasses;
	}
	public void setDupClasses(String dupClasses) {
		this.dupClasses = dupClasses;
	}
	public String getDupDepartments() {
		return dupDepartments;
	}
	public void setDupDepartments(String dupDepartments) {
		this.dupDepartments = dupDepartments;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public boolean isFlag1() {
		return flag1;
	}
	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}
	public Map<Integer, String> getMapDept() {
		return mapDept;
	}
	public void setMapDept(Map<Integer, String> mapDept) {
		this.mapDept = mapDept;
	}
	public Map<Integer, String> getMapSelectedDept() {
		return mapSelectedDept;
	}
	public void setMapSelectedDept(Map<Integer, String> mapSelectedDept) {
		this.mapSelectedDept = mapSelectedDept;
	}
	public List<EventScheduleForAttendanceTo> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<EventScheduleForAttendanceTo> studentList) {
		this.studentList = studentList;
	}
	public List<EventScheduleForAttendanceTo> getStaffList() {
		return staffList;
	}
	public void setStaffList(List<EventScheduleForAttendanceTo> staffList) {
		this.staffList = staffList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<Integer, String> getPeriodMap() {
		return periodMap;
	}
	public void setPeriodMap(Map<Integer, String> periodMap) {
		this.periodMap = periodMap;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Map<Integer, Integer> getOldMapSelectedClass() {
		return oldMapSelectedClass;
	}
	public void setOldMapSelectedClass(Map<Integer, Integer> oldMapSelectedClass) {
		this.oldMapSelectedClass = oldMapSelectedClass;
	}
	public Map<Integer, Integer> getOldDeptMap() {
		return oldDeptMap;
	}
	public void setOldDeptMap(Map<Integer, Integer> oldDeptMap) {
		this.oldDeptMap = oldDeptMap;
	}
	
	public String getOldEventDate() {
		return oldEventDate;
	}
	public void setOldEventDate(String oldEventDate) {
		this.oldEventDate = oldEventDate;
	}
	public Boolean getPeriodSelected() {
		return periodSelected;
	}
	public void setPeriodSelected(Boolean periodSelected) {
		this.periodSelected = periodSelected;
	}
	
	
	

}
