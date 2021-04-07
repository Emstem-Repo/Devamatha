package com.kp.cms.forms.timetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.timetable.TTSubjectBatchTo;
import com.kp.cms.to.timetable.TimeTableClassTo;

/**
 * @author balaji
 *
 */
public class TimeTableForClassForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private String classId;
	private List<TimeTableClassTo> classTos;
	Map<Integer, String> classMap;
	private String msg;
	List<Period> periodList;
	private int count;
	private int position;
	private Map<Integer, String> subjectMap;
	List<TTSubjectBatchTo> subjectList;
	List<TTSubjectBatchTo> subjectListForCopy;
	private boolean flag;
	private String displayWarning;
	private String displayWarning1;
	private Boolean isStaff;
	private String empName;
	private String clsOrcrs;
	private String roleId;
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Boolean getIsStaff() {
		return isStaff;
	}
	public void setIsStaff(Boolean isStaff) {
		this.isStaff = isStaff;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<TTSubjectBatchTo> getSubjectListForCopy() {
		return subjectListForCopy;
	}
	public void setSubjectListForCopy(List<TTSubjectBatchTo> subjectListForCopy) {
		this.subjectListForCopy = subjectListForCopy;
	}

	List<TTSubjectBatchTo> subjectList1;
	public List<TTSubjectBatchTo> getSubjectList1() {
		return subjectList1;
	}
	public void setSubjectList1(List<TTSubjectBatchTo> subjectList1) {
		this.subjectList1 = subjectList1;
	}

	List<Integer> commonSubList;
	List<Integer> secLanguageList;
	List<Integer> batchList;
	List<Integer> electiveList;
	List<Integer> certificateList;

	Map<Integer,Map<Integer,String>> batchMap;
	Map<Integer,String> attTypeMap;
	Map<Integer,String> teachersMap;
	private int ttClassId;
	private int ttPeriodWeekId;
	private int periodId;
	private String week;
	private Boolean deleteSubject;
	private int deleteCount;
	private String finalApprove;
	private Boolean deleteBackButton;
	private boolean changed;
	private Boolean ttClassHistoryExists;
	Map<String,List<TimeTableClassTo>> historyMap;
	List<Integer> activityBatchList;
	Map<Integer,Map<Integer,String>> activityBatchMap;
	Map<Integer,Map<Integer,String>> activityMap;
	Map<Integer, String> departmentMap;
	private String periodName;
	private String academicYear;
	private int fromDay;
	private int  toDay;
	private int  fromPeriodId;
	private int  toPeriodId;
	private String  errormsg;
	private String periods[];
	private String teachers[];
	private String weeks[];
	private String classes[];
	private String toggler;
	private Map<Integer,String> periodMap;
	private Map<Integer,String> weekMap;
	private String attType;
	
	public String  getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public int  getFromDay() {
		return fromDay;
	}
	public void setFromDay(int  fromDay) {
		this.fromDay = fromDay;
	}
	public int getToDay() {
		return toDay;
	}
	public void setToDay(int  toDay) {
		this.toDay = toDay;
	}
	public int  getFromPeriodId() {
		return fromPeriodId;
	}
	public void setFromPeriodId(int  fromPeriodId) {
		this.fromPeriodId = fromPeriodId;
	}
	public int  getToPeriodId() {
		return toPeriodId;
	}
	public void setToPeriodId(int  toPeriodId) {
		this.toPeriodId = toPeriodId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public List<TimeTableClassTo> getClassTos() {
		return classTos;
	}
	public void setClassTos(List<TimeTableClassTo> classTos) {
		this.classTos = classTos;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Period> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<Period> periodList) {
		this.periodList = periodList;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	public List<TTSubjectBatchTo> getSubjectList() {
		return subjectList;
	}
	public Map<Integer, String> getPeriodMap() {
		return periodMap;
	}
	public void setPeriodMap(Map<Integer, String> periodMap) {
		this.periodMap = periodMap;
	}
	public void setSubjectList(List<TTSubjectBatchTo> subjectList) {
		this.subjectList = subjectList;
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
	public List<Integer> getBatchList() {
		return batchList;
	}
	public void setBatchList(List<Integer> batchList) {
		this.batchList = batchList;
	}
	public List<Integer> getElectiveList() {
		return electiveList;
	}
	public void setElectiveList(List<Integer> electiveList) {
		this.electiveList = electiveList;
	}
	public Map<Integer, Map<Integer, String>> getBatchMap() {
		return batchMap;
	}
	public void setBatchMap(Map<Integer, Map<Integer, String>> batchMap) {
		this.batchMap = batchMap;
	}
	public Map<Integer, String> getAttTypeMap() {
		return attTypeMap;
	}
	public void setAttTypeMap(Map<Integer, String> attTypeMap) {
		this.attTypeMap = attTypeMap;
	}
	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}
	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}
	public int getTtClassId() {
		return ttClassId;
	}
	public void setTtClassId(int ttClassId) {
		this.ttClassId = ttClassId;
	}
	public int getTtPeriodWeekId() {
		return ttPeriodWeekId;
	}
	public void setTtPeriodWeekId(int ttPeriodWeekId) {
		this.ttPeriodWeekId = ttPeriodWeekId;
	}
	public int getPeriodId() {
		return periodId;
	}
	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public Boolean getDeleteSubject() {
		return deleteSubject;
	}
	public void setDeleteSubject(Boolean deleteSubject) {
		this.deleteSubject = deleteSubject;
	}
	public int getDeleteCount() {
		return deleteCount;
	}
	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}
	public String getFinalApprove() {
		return finalApprove;
	}
	public void setFinalApprove(String finalApprove) {
		this.finalApprove = finalApprove;
	}
	public Boolean getDeleteBackButton() {
		return deleteBackButton;
	}
	public void setDeleteBackButton(Boolean deleteBackButton) {
		this.deleteBackButton = deleteBackButton;
	}
	public boolean isChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	public Boolean getTtClassHistoryExists() {
		return ttClassHistoryExists;
	}
	public void setTtClassHistoryExists(Boolean ttClassHistoryExists) {
		this.ttClassHistoryExists = ttClassHistoryExists;
	}
	public Map<String, List<TimeTableClassTo>> getHistoryMap() {
		return historyMap;
	}
	public void setHistoryMap(Map<String, List<TimeTableClassTo>> historyMap) {
		this.historyMap = historyMap;
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
	
	public Map<Integer, Map<Integer, String>> getActivityMap() {
		return activityMap;
	}
	public void setActivityMap(Map<Integer, Map<Integer, String>> activityMap) {
		this.activityMap = activityMap;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public List<Integer> getCertificateList() {
		return certificateList;
	}
	public void setCertificateList(List<Integer> certificateList) {
		this.certificateList = certificateList;
	}
	public Map<Integer, String> getDepartmentMap() {
		return departmentMap;
	}
	public void setDepartmentMap(Map<Integer, String> departmentMap) {
		this.departmentMap = departmentMap;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	/**
	 * 
	 */
	public void resetFields() {
		this.classTos=null;
		this.msg=null;
		this.count=0;
		this.position=0;
		this.subjectList=null;
		this.commonSubList=null;
		this.secLanguageList=null;
		this.batchList=null;
		this.electiveList=null;
		this.batchMap=null;
		this.attTypeMap=null;
		this.teachersMap=null;
		this.ttClassId=0;
		this.ttPeriodWeekId=0;
		this.periodId=0;
		this.week=null;
		this.deleteSubject=false;
		this.deleteCount=0;
		this.finalApprove="on";
		this.deleteBackButton=false;
		this.changed=false;
		this.activityBatchList=null;
		this.activityBatchMap=null;
		this.activityMap=null;
		this.certificateList=null;
		this.periodName=null;
		this.errormsg=null;
		this.displayWarning=null;
		this.displayWarning1=null;
	}
	/**
	 * 
	 */
	public void resetFormFields(){
		super.setYear(null);
		this.classId=null;
		this.errormsg=null;
	}
	
	public void resetPeriodFields(){
		this.ttPeriodWeekId=0;
		this.periodId=0;
		this.week=null;
		this.deleteSubject=false;
		this.deleteCount=0;
//		this.finalApprove="off";
		this.deleteBackButton=false;
		this.changed=false;
		this.periodName=null;
		this.errormsg=null;
	}
	public void resetDayAndPeriodFields(){
		this.fromDay=0;
		this.toDay=0;
		this.fromPeriodId=0;
		this.toPeriodId=0;
		this.flag = false;
		this.errormsg=null;
	}
	public void resetDayAndPeriodFieldsForCorrecrPeriod(){
		this.fromDay=0;
		this.toDay=0;
		this.fromPeriodId=0;
		this.toPeriodId=0;
		this.flag = false;
		
	}
	public String getDisplayWarning() {
		return displayWarning;
	}
	public void setDisplayWarning(String displayWarning) {
		this.displayWarning = displayWarning;
	}
	public String getDisplayWarning1() {
		return displayWarning1;
	}
	public void setDisplayWarning1(String displayWarning1) {
		this.displayWarning1 = displayWarning1;
	}
	public String getClsOrcrs() {
		return clsOrcrs;
	}
	public void setClsOrcrs(String clsOrcrs) {
		this.clsOrcrs = clsOrcrs;
	}
	public String[] getPeriods() {
		return periods;
	}
	public void setPeriods(String[] periods) {
		this.periods = periods;
	}
	public String[] getWeeks() {
		return weeks;
	}
	public void setWeeks(String[] weeks) {
		this.weeks = weeks;
	}
	
	public String[] getClasses() {
		return classes;
	}
	public void setClasses(String[] classes) {
		this.classes = classes;
	}
	public String getToggler() {
		return toggler;
	}
	public void setToggler(String toggler) {
		this.toggler = toggler;
	}
	public Map<Integer, String> getWeekMap() {
		return weekMap;
	}
	public void setWeekMap(Map<Integer, String> weekMap) {
		this.weekMap = weekMap;
	}
	public String[] getTeachers() {
		return teachers;
	}
	public void setTeachers(String[] teachers) {
		this.teachers = teachers;
	}
	public String getAttType() {
		return attType;
	}
	public void setAttType(String attType) {
		this.attType = attType;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}