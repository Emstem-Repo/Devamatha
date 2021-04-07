package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.WorkTimeTO;
import com.kp.cms.to.employee.EmpWorkTimeTO;
import com.kp.cms.to.employee.EmployeeKeyValueTO;

public class WorkTimeEntryForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String inTimeFromHr;
	private String inTimeFromMins;
	private String inTimeToHrs;
	private String inTimeToMins;
	private String outTimeFromHrs;
	private String outTimeFromMins;
	private String outTimeToHrs;
	private String outTimeToMins;
	private int duplId; 
	List<WorkTimeTO> workList;
	List<EmployeeKeyValueTO> listEmployeeType;
	List<EmpWorkTimeTO> empWorkTimeToList;
	
	public List<EmployeeKeyValueTO> getListEmployeeType() {
		return listEmployeeType;
	}
	public void setListEmployeeType(List<EmployeeKeyValueTO> listEmployeeType) {
		this.listEmployeeType = listEmployeeType;
	}
	public List<WorkTimeTO> getWorkList() {
		return workList;
	}
	public void setWorkList(List<WorkTimeTO> workList) {
		this.workList = workList;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getInTimeFromHr() {
		return inTimeFromHr;
	}
	public String getInTimeFromMins() {
		return inTimeFromMins;
	}
	public String getInTimeToHrs() {
		return inTimeToHrs;
	}
	public String getInTimeToMins() {
		return inTimeToMins;
	}
	public String getOutTimeFromHrs() {
		return outTimeFromHrs;
	}
	public String getOutTimeFromMins() {
		return outTimeFromMins;
	}
	public String getOutTimeToHrs() {
		return outTimeToHrs;
	}
	public String getOutTimeToMins() {
		return outTimeToMins;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setInTimeFromHr(String inTimeFromHr) {
		this.inTimeFromHr = inTimeFromHr;
	}
	public void setInTimeFromMins(String inTimeFromMins) {
		this.inTimeFromMins = inTimeFromMins;
	}
	public void setInTimeToHrs(String inTimeToHrs) {
		this.inTimeToHrs = inTimeToHrs;
	}
	public void setInTimeToMins(String inTimeToMins) {
		this.inTimeToMins = inTimeToMins;
	}
	public void setOutTimeFromHrs(String outTimeFromHrs) {
		this.outTimeFromHrs = outTimeFromHrs;
	}
	public void setOutTimeFromMins(String outTimeFromMins) {
		this.outTimeFromMins = outTimeFromMins;
	}
	public void setOutTimeToHrs(String outTimeToHrs) {
		this.outTimeToHrs = outTimeToHrs;
	}
	public void setOutTimeToMins(String outTimeToMins) {
		this.outTimeToMins = outTimeToMins;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public List<EmpWorkTimeTO> getEmpWorkTimeToList() {
		return empWorkTimeToList;
	}
	public void setEmpWorkTimeToList(List<EmpWorkTimeTO> empWorkTimeToList) {
		this.empWorkTimeToList = empWorkTimeToList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
		
}
