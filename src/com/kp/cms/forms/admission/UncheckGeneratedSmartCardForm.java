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

public class UncheckGeneratedSmartCardForm extends BaseActionForm {
	private String regNoFrom;
	private String regNoTo;
	private List<ProgramTypeTO> programTypeList;
	private List<StudentTO> generatedStudentList;
	private String joiningBatch;
	private String programType;
	private String program;
	private String course;
	// added for smart card data uncheck of employees screen
	private Map<String, String> departmentMap;
	private String empIdFrom;
	private String empIdTo;
	private String departmentName;
	private List<EmployeeTO> generatedEmployeeList;
	
	
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

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<StudentTO> getGeneratedStudentList() {
		return generatedStudentList;
	}

	public void setGeneratedStudentList(List<StudentTO> generatedStudentList) {
		this.generatedStudentList = generatedStudentList;
	}

	public String getJoiningBatch() {
		return joiningBatch;
	}

	public void setJoiningBatch(String joiningBatch) {
		this.joiningBatch = joiningBatch;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
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
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public List<EmployeeTO> getGeneratedEmployeeList() {
		return generatedEmployeeList;
	}

	public void setGeneratedEmployeeList(List<EmployeeTO> generatedEmployeeList) {
		this.generatedEmployeeList = generatedEmployeeList;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	/**
	 * To reset the form properties
	 */
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		super.setYear(null);
		this.setRegNoFrom(null);
		this.setRegNoTo(null);
		this.generatedStudentList=null;
		this.setEmpIdFrom(null);
		this.setEmpIdTo(null);
		this.setGeneratedEmployeeList(null);
		
	}

}
