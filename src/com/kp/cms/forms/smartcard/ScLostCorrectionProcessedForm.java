package com.kp.cms.forms.smartcard;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.smartcard.ScLostCorrectionProcessedTO;

public class ScLostCorrectionProcessedForm extends BaseActionForm {
	
	private int id;
	private String status;
	List<ScLostCorrectionProcessedTO> scList;
	private String checked;
	//private String statusDisplay;
	private Student studentBO;
	//private String rejectedRemarks;
	private String isEmployee;
	private String empDepartment;
	private String empId;
	private String empFingerPrintId;
	private String editedRemarks;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void clear()
	{
		this.status="Processed";
		this.checked=null;
		this.isEmployee="Student";
		this.empDepartment=null;
		this.empFingerPrintId=null;
		this.empId=null;
		this.editedRemarks=null;
	}

	
	public Student getStudentBO() {
		return studentBO;
	}

	public void setStudentBO(Student studentBO) {
		this.studentBO = studentBO;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ScLostCorrectionProcessedTO> getScList() {
		return scList;
	}

	public void setScList(List<ScLostCorrectionProcessedTO> scList) {
		this.scList = scList;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
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

	public String getEditedRemarks() {
		return editedRemarks;
	}

	public void setEditedRemarks(String editedRemarks) {
		this.editedRemarks = editedRemarks;
	}
	

}
