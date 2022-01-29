package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;

public class GensmartCardDataForm extends BaseActionForm {
	private List<ProgramTypeTO> programTypeList;
	private Map<Integer,List<StudentTO>> smartCardData;
	private boolean print;
	private List<Integer> studentIds;
	private String regNoFrom;
	private String regNoTo;
	private List<String> studentsWithoutPhotos;
	private List<String> studentsWithoutRegNos;
	private boolean isDataGenerated;
	private int studentCount;
// For employees smart card data generation
	private Map<String,String> departmentMap;
	private String empIdFrom;
	private String empIdTo;
	private Map<Integer,List<EmployeeTO>> smartCardDataEmp ; 
	private List<Integer> employeeIds;
	private List<String> employeesWithoutPhotos;
	private List<String> employeesWithoutFingerPrintIds;
	
	
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public Map<Integer, List<StudentTO>> getSmartCardData() {
		return smartCardData;
	}
	public void setSmartCardData(Map<Integer, List<StudentTO>> smartCardData) {
		this.smartCardData = smartCardData;
	}
	public boolean isPrint() {
		return print;
	}
	public void setPrint(boolean print) {
		this.print = print;
	}
	public List<Integer> getStudentIds() {
		return studentIds;
	}
	public void setStudentIds(List<Integer> studentIds) {
		this.studentIds = studentIds;
	}
	public String getRegNoFrom() {
		return regNoFrom;
	}
	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}
	public String getRegNoTo() {
		return regNoTo;
	}
	public void setRegNoTo(String regNoTo) {
		this.regNoTo = regNoTo;
	}
	public List<String> getStudentsWithoutPhotos() {
		return studentsWithoutPhotos;
	}
	public void setStudentsWithoutPhotos(List<String> studentsWithoutPhotos) {
		this.studentsWithoutPhotos = studentsWithoutPhotos;
	}
	public List<String> getStudentsWithoutRegNos() {
		return studentsWithoutRegNos;
	}
	public void setStudentsWithoutRegNos(List<String> studentsWithoutRegNos) {
		this.studentsWithoutRegNos = studentsWithoutRegNos;
	}
	public boolean isDataGenerated() {
		return isDataGenerated;
	}
	public void setDataGenerated(boolean isDataGenerated) {
		this.isDataGenerated = isDataGenerated;
	}
	public int getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}
	public Map<String, String> getDepartmentMap() {
		return departmentMap;
	}
	public void setDepartmentMap(Map<String, String> departmentMap) {
		this.departmentMap = departmentMap;
	}
	public String getEmpIdFrom() {
		return empIdFrom;
	}
	public void setEmpIdFrom(String empIdFrom) {
		this.empIdFrom = empIdFrom;
	}
	public String getEmpIdTo() {
		return empIdTo;
	}
	public void setEmpIdTo(String empIdTo) {
		this.empIdTo = empIdTo;
	}
	public Map<Integer, List<EmployeeTO>> getSmartCardDataEmp() {
		return smartCardDataEmp;
	}
	public void setSmartCardDataEmp(Map<Integer, List<EmployeeTO>> smartCardDataEmp) {
		this.smartCardDataEmp = smartCardDataEmp;
	}
	public List<Integer> getEmployeeIds() {
		return employeeIds;
	}
	public void setEmployeeIds(List<Integer> employeeIds) {
		this.employeeIds = employeeIds;
	}
	public List<String> getEmployeesWithoutPhotos() {
		return employeesWithoutPhotos;
	}
	public void setEmployeesWithoutPhotos(List<String> employeesWithoutPhotos) {
		this.employeesWithoutPhotos = employeesWithoutPhotos;
	}
	public List<String> getEmployeesWithoutFingerPrintIds() {
		return employeesWithoutFingerPrintIds;
	}
	public void setEmployeesWithoutFingerPrintIds(
			List<String> employeesWithoutFingerPrintIds) {
		this.employeesWithoutFingerPrintIds = employeesWithoutFingerPrintIds;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		super.setYear(null);
		super.setDepartmentId(null);
		this.setRegNoFrom(null);
		this.setRegNoTo(null);
		this.setEmpIdFrom(null);
		this.setEmpIdTo(null);
		this.setDepartmentMap(null);
		
	}

}
