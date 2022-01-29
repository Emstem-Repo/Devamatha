package com.kp.cms.forms.smartcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.smartcard.ScLostCorrectionViewTO;

public class ScLostCorrectionViewForm extends BaseActionForm {
	
	private int id;
	private String status1;
	private String status2;
	private String type;
	private Boolean print;
	List<ScLostCorrectionViewTO> scList;
	private String isDownload;
	private Map<Integer,List<StudentTO>> smartCardData;
	private List<Integer> studentIds;
	private List<String> studentsWithoutPhotos;
	private List<String> studentsWithoutRegNos;
	private String reasonForRejection;
	private String downloadExcel;
	private String isEmployee;
	//private String tempIsEmployee;
	private String empDepartment;
	private String empId;
	private String empFingerPrintId;
	private List<Integer> employeeIds;
	private Map<Integer,List<EmployeeTO>> smartCardDataEmployee;
	private List<String> employeesWithoutPhotos;
	private List<String> employeesWithoutFingerPrintIds;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	


	public String getStatus1() {
		return status1;
	}


	public void setStatus1(String status1) {
		this.status1 = status1;
	}


	public String getStatus2() {
		return status2;
	}


	public void setStatus2(String status2) {
		this.status2 = status2;
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


	public Map<Integer, List<StudentTO>> getSmartCardData() {
		return smartCardData;
	}


	public void setSmartCardData(Map<Integer, List<StudentTO>> smartCardData) {
		this.smartCardData = smartCardData;
	}


	public List<Integer> getStudentIds() {
		return studentIds;
	}


	public void setStudentIds(List<Integer> studentIds) {
		this.studentIds = studentIds;
	}


	public String getIsDownload() {
		return isDownload;
	}


	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}


	public List<ScLostCorrectionViewTO> getScList() {
		return scList;
	}


	public void setScList(List<ScLostCorrectionViewTO> scList) {
		this.scList = scList;
	}


	public Boolean getPrint() {
		return print;
	}

	public void setPrint(Boolean print) {
		this.print = print;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	
	public String getDownloadExcel() {
		return downloadExcel;
	}

	public void setDownloadExcel(String downloadExcel) {
		this.downloadExcel = downloadExcel;
	}

	public String getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}

	public String getEmpDepartment() {
		return empDepartment;
	}

	public void setEmpDepartment(String empDepartment) {
		this.empDepartment = empDepartment;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpFingerPrintId() {
		return empFingerPrintId;
	}

	public void setEmpFingerPrintId(String empFingerPrintId) {
		this.empFingerPrintId = empFingerPrintId;
	}

	public List<Integer> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(List<Integer> employeeIds) {
		this.employeeIds = employeeIds;
	}

	public Map<Integer, List<EmployeeTO>> getSmartCardDataEmployee() {
		return smartCardDataEmployee;
	}

	public void setSmartCardDataEmployee(
			Map<Integer, List<EmployeeTO>> smartCardDataEmployee) {
		this.smartCardDataEmployee = smartCardDataEmployee;
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


	public void clearAll()
	{
		this.status1="Applied";
		this.type=null;
		this.isDownload=null;
		this.reasonForRejection=null;
		this.empDepartment=null;
		this.empFingerPrintId=null;
		this.empId=null;
		this.isEmployee="Student";
		
	}
	
	

}
