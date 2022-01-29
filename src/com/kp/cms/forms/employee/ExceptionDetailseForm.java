package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.EmpAcademicHolidaysDates;
import com.kp.cms.bo.admin.EmpExceptionDetailsDates;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.ExceptionDetailsTo;

@SuppressWarnings("serial")
public class ExceptionDetailseForm extends BaseActionForm {
	
	private String exceptionTypeId;
	private String fromDate;
	private String toDate;
	private String remarks;
	private String fromAM;
	private String fromPM;
	private String toAM;
	private String toPM;
	private Map<Integer, String> exceptionTypeMap;
	private List<ExceptionDetailsTo> exceptionList;
	private int id;
	private String method;
	private Map<Integer,String>employeeMap;
	private String employeeId;
	private String employeeName;
	private int dupId;
	private String[] datesArray;
	private int datesId[];
	private Set<Integer> datesIdSet;
	private List<EmpExceptionDetailsDates>datesToBeDeleted;
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the exceptionList
	 */
	public List<ExceptionDetailsTo> getExceptionList() {
		return exceptionList;
	}
	/**
	 * @param exceptionList the exceptionList to set
	 */
	public void setExceptionList(List<ExceptionDetailsTo> exceptionList) {
		this.exceptionList = exceptionList;
	}
	public String getExceptionTypeId() {
		return exceptionTypeId;
	}
	public void setExceptionTypeId(String exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFromAM() {
		return fromAM;
	}
	public void setFromAM(String fromAM) {
		this.fromAM = fromAM;
	}
	public String getFromPM() {
		return fromPM;
	}
	public void setFromPM(String fromPM) {
		this.fromPM = fromPM;
	}
	public String getToAM() {
		return toAM;
	}
	public void setToAM(String toAM) {
		this.toAM = toAM;
	}
	public String getToPM() {
		return toPM;
	}
	public void setToPM(String toPM) {
		this.toPM = toPM;
	}
	public Map<Integer, String> getExceptionTypeMap() {
		return exceptionTypeMap;
	}
	public void setExceptionTypeMap(Map<Integer, String> exceptionTypeMap) {
		this.exceptionTypeMap = exceptionTypeMap;
	}
	
	
	public Map<Integer, String> getEmployeeMap() {
		return employeeMap;
	}
	public void setEmployeeMap(Map<Integer, String> employeeMap) {
		this.employeeMap = employeeMap;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public String[] getDatesArray() {
		return datesArray;
	}
	public void setDatesArray(String[] datesArray) {
		this.datesArray = datesArray;
	}
	public int[] getDatesId() {
		return datesId;
	}
	public void setDatesId(int[] datesId) {
		this.datesId = datesId;
	}
	public Set<Integer> getDatesIdSet() {
		return datesIdSet;
	}
	public void setDatesIdSet(Set<Integer> datesIdSet) {
		this.datesIdSet = datesIdSet;
	}
	public List<EmpExceptionDetailsDates> getDatesToBeDeleted() {
		return datesToBeDeleted;
	}
	public void setDatesToBeDeleted(List<EmpExceptionDetailsDates> datesToBeDeleted) {
		this.datesToBeDeleted = datesToBeDeleted;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		this.setExceptionTypeId(null);
		this.setFromAM(null);
		this.setToAM(null);
		this.setFromDate(null);
		this.setToDate(null);
		this.setRemarks(null);
		this.setToPM(null);
		this.setFromPM(null);
		this.id=0;
		this.setEmployeeId(null);
		super.setEmployeeId(null);
	}
	
	
}
