package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.employee.EmpEventVacation;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EmpEventVacationTo;

public class EmpEventVacationForm extends BaseActionForm{
	private String[] department;
	private String toDate;
	private String fromDate;
	private String description;
	private Map<Integer,String> deptMap;
	private List<EmpEventVacationTo> empTo;
	private String id;
	private String mode;
	private EmpEventVacation empEventVacation;
	private Map<String,EmpEventVacation> eventMap;
	private Map<Integer,Integer> boIdDeptMap;
	private Map<Integer,String> streamMap;
	private String streamId;
	private String type;
	private String streamName;
	private String teachingStaff;
	private String isActive;
	private String tempStreamId;
	
	public String[] getDepartment() {
		return department;
	}
	public String getToDate() {
		return toDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public String getDescription() {
		return description;
	}
	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}
	public void setDepartment(String[] department) {
		this.department = department;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}
	
	public List<EmpEventVacationTo> getEmpTo() {
		return empTo;
	}
	public void setEmpTo(List<EmpEventVacationTo> empTo) {
		this.empTo = empTo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String selectedIndex) {
		this.mode = selectedIndex;
	}
	public EmpEventVacation getEmpEventVacation() {
		return empEventVacation;
	}
	public void setEmpEventVacation(EmpEventVacation empEventVacation) {
		this.empEventVacation = empEventVacation;
	}
	public Map<String, EmpEventVacation> getEventMap() {
		return eventMap;
	}
	public void setEventMap(Map<String, EmpEventVacation> eventMap) {
		this.eventMap = eventMap;
	}
	/**
	 * 
	 */
	public void reset(){
		this.department=null;
		this.toDate=null;
		this.fromDate=null;
		this.description=null;
		this.id=null;
		this.mode="add";
		this.teachingStaff="1";
		this.streamId=null;
		this.type=null;
		
		
	}
	
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public void setStreamMap(Map<Integer,String> streamMap) {
		this.streamMap = streamMap;
	}
	public Map<Integer,String> getStreamMap() {
		return streamMap;
	}
	
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}
	public String getStreamName() {
		return streamName;
	}
	public String getTeachingStaff() {
		return teachingStaff;
	}
	public void setTeachingStaff(String teachingStaff) {
		this.teachingStaff = teachingStaff;
	}
	public String getStreamId() {
		return streamId;
	}
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Map<Integer, Integer> getBoIdDeptMap() {
		return boIdDeptMap;
	}
	public void setBoIdDeptMap(Map<Integer, Integer> boIdDeptMap) {
		this.boIdDeptMap = boIdDeptMap;
	}
	public String getTempStreamId() {
		return tempStreamId;
	}
	public void setTempStreamId(String tempStreamId) {
		this.tempStreamId = tempStreamId;
	}
	
	
}
