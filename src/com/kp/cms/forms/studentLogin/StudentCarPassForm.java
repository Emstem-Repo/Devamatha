package com.kp.cms.forms.studentLogin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.studentLogin.StudentCarPassTo;

public class StudentCarPassForm extends BaseActionForm {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String emergencyContactNo;
	private String vehicleNo;
	private String modelOfVehicle;
	private String studentId;
	private StudentCarPassTo studentcarPassTo;
	private int regCarPasses;
	private boolean checkDisableText;
	
	
	
	
	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}
	public void setEmergencyContactNo(String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getModelOfVehicle() {
		return modelOfVehicle;
	}
	public void setModelOfVehicle(String modelOfVehicle) {
		this.modelOfVehicle = modelOfVehicle;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public StudentCarPassTo getStudentcarPassTo() {
		return studentcarPassTo;
	}
	public void setStudentcarPassTo(StudentCarPassTo studentcarPassTo) {
		this.studentcarPassTo = studentcarPassTo;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
        this.modelOfVehicle=null;
        this.vehicleNo=null;
        this.emergencyContactNo=null;
        this.checkDisableText=false;
	}
	public int getRegCarPasses() {
		return regCarPasses;
	}
	public void setRegCarPasses(int regCarPasses) {
		this.regCarPasses = regCarPasses;
	}
	public boolean isCheckDisableText() {
		return checkDisableText;
	}
	public void setCheckDisableText(boolean checkDisableText) {
		this.checkDisableText = checkDisableText;
	}

}
