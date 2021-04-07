package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EmpTypeTo;

public class EmpTypeForm extends BaseActionForm {
	
	private String id;
	private String name;
	private String timeIn;
	private String timeInEnds;
	private String timeOut;
	private String saturdayTimeOut;
	private String halfDayStartTime;
	private String halfDatyEndTime;
	private String leaveInitializeMonth;
	private List<EmpTypeTo> empTypeToList;
	private Map<String,EmpType> empTypeList;
	private Map<String ,EmpType> empTypeMaps;
	private String timeInMin;
	private String timeInEndMIn;
	private String timeOutMin;
	private String saturdayTimeOutMin;
	private String halfDayStartTimeMin;
	private String halfDayEndTimeMin;
	private Map<String,EmpType> resetMap;
	private String button;
	
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getTimeIn() {
		return timeIn;
	}
	public String getTimeInEnds() {
		return timeInEnds;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public String getSaturdayTimeOut() {
		return saturdayTimeOut;
	}
	public String getHalfDayStartTime() {
		return halfDayStartTime;
	}
	public String getHalfDatyEndTime() {
		return halfDatyEndTime;
	}
	public String getLeaveInitializeMonth() {
		return leaveInitializeMonth;
	}
	public List<EmpTypeTo> getEmpTypeToList() {
		return empTypeToList;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}
	public void setTimeInEnds(String timeInEnds) {
		this.timeInEnds = timeInEnds;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public void setSaturdayTimeOut(String saturdayTimeOut) {
		this.saturdayTimeOut = saturdayTimeOut;
	}
	public void setHalfDayStartTime(String halfDayStartTime) {
		this.halfDayStartTime = halfDayStartTime;
	}
	public void setHalfDatyEndTime(String halfDatyEndTime) {
		this.halfDatyEndTime = halfDatyEndTime;
	}
	public void setLeaveInitializeMonth(String leaveInitializeMonth) {
		this.leaveInitializeMonth = leaveInitializeMonth;
	}
	public void setEmpTypeToList(List<EmpTypeTo> empTypeToList) {
		this.empTypeToList = empTypeToList;
	} 
	
	public Map<String,EmpType> getEmpTypeList() {
		return empTypeList;
	}
	public void setEmpTypeList(Map<String,EmpType> empTypeList) {
		this.empTypeList = empTypeList;
	}
	public Map<String, EmpType> getEmpTypeMaps() {
		return empTypeMaps;
	}
	public void setEmpTypeMaps(Map<String, EmpType> empTypeMaps) {
		this.empTypeMaps = empTypeMaps;
	}
	public String getTimeInMin() {
		return timeInMin;
	}
	public String getTimeInEndMIn() {
		return timeInEndMIn;
	}
	public String getTimeOutMin() {
		return timeOutMin;
	}
	public String getSaturdayTimeOutMin() {
		return saturdayTimeOutMin;
	}
	public String getHalfDayStartTimeMin() {
		return halfDayStartTimeMin;
	}
	public String getHalfDayEndTimeMin() {
		return halfDayEndTimeMin;
	}
	public void setTimeInMin(String timeInMin) {
		this.timeInMin = timeInMin;
	}
	public void setTimeInEndMIn(String timeInEndMIn) {
		this.timeInEndMIn = timeInEndMIn;
	}
	public void setTimeOutMin(String timeOutMin) {
		this.timeOutMin = timeOutMin;
	}
	public void setSaturdayTimeOutMin(String saturdayTimeOutMin) {
		this.saturdayTimeOutMin = saturdayTimeOutMin;
	}
	public void setHalfDayStartTimeMin(String halfDayStartTimeMin) {
		this.halfDayStartTimeMin = halfDayStartTimeMin;
	}
	public void setHalfDayEndTimeMin(String halfDayEndTimeMin) {
		this.halfDayEndTimeMin = halfDayEndTimeMin;
	}
	public Map<String, EmpType> getResetMap() {
		return resetMap;
	}
	public void setResetMap(Map<String, EmpType> resetMap) {
		this.resetMap = resetMap;
	}
	public String getButton() {
		return button;
	}
	public void setButton(String button) {
		this.button = button;
	}
	/**
	 * 
	 */
	
	public void resetId(){
		this.id=null;
	}
	public void reset(){
		this.name=null;
		this.timeIn="00";
		this.timeInEnds="00";
		this.timeOut="00";
		this.saturdayTimeOut="00";
		this.halfDayStartTime="00";
		this.halfDatyEndTime="00";
		this.leaveInitializeMonth=null;
		this.timeInMin="00";
		this.timeInEndMIn="00";
		this.timeOutMin="00";
		this.saturdayTimeOutMin="00";
		this.halfDayStartTimeMin="00";
		this.halfDayEndTimeMin="00";
		this.button="Add";
	}
	
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

}
