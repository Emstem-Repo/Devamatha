package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EmployeeSettingsTO;

@SuppressWarnings("serial")
public class EmployeeSettingsForm extends BaseActionForm{
	private int id;
	private String ageOfRetirement;
	private String smsAlert;
	private String absenceChecking;
	private String accumulateLeaveType;
	private String currentApplicationNo;
	private List<EmployeeSettingsTO> empSettListTO;
	private Map<Integer,String> empLeave;
	private String origAgeOfRetire;
	private String origSmsAlert;
	private String origAbsenceCheck;
	private String origEmpLeave;
	private String origAppNo;
	private String machineIdForOfflineINPunch;
	private String machineIdForOfflineOUTPunch;
	private String origMachineINpunch;
	private String origMachineOUTpunch;
	public String getAgeOfRetirement() {
		return ageOfRetirement;
	}
	public void setAgeOfRetirement(String ageOfRetirement) {
		this.ageOfRetirement = ageOfRetirement;
	}
	public String getSmsAlert() {
		return smsAlert;
	}
	public void setSmsAlert(String smsAlert) {
		this.smsAlert = smsAlert;
	}
	public String getAbsenceChecking() {
		return absenceChecking;
	}
	public void setAbsenceChecking(String absenceChecking) {
		this.absenceChecking = absenceChecking;
	}
	public String getAccumulateLeaveType() {
		return accumulateLeaveType;
	}
	public void setAccumulateLeaveType(String accumulateLeaveType) {
		this.accumulateLeaveType = accumulateLeaveType;
	}
	public String getCurrentApplicationNo() {
		return currentApplicationNo;
	}
	public void setCurrentApplicationNo(String currentApplicationNo) {
		this.currentApplicationNo = currentApplicationNo;
	}
	public List<EmployeeSettingsTO> getEmpSettListTO() {
		return empSettListTO;
	}
	public void setEmpSettListTO(List<EmployeeSettingsTO> empSettListTO) {
		this.empSettListTO = empSettListTO;
	}
	public Map<Integer, String> getEmpLeave() {
		return empLeave;
	}
	public void setEmpLeave(Map<Integer, String> empLeave) {
		this.empLeave = empLeave;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void reset(){
		this.ageOfRetirement=null;
		this.absenceChecking=null;
		this.currentApplicationNo=null;
		this.accumulateLeaveType=null;
		this.smsAlert=null;
		this.machineIdForOfflineINPunch=null;
		this.machineIdForOfflineOUTPunch=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getOrigAgeOfRetire() {
		return origAgeOfRetire;
	}
	public void setOrigAgeOfRetire(String origAgeOfRetire) {
		this.origAgeOfRetire = origAgeOfRetire;
	}
	public String getOrigSmsAlert() {
		return origSmsAlert;
	}
	public void setOrigSmsAlert(String origSmsAlert) {
		this.origSmsAlert = origSmsAlert;
	}
	public String getOrigAbsenceCheck() {
		return origAbsenceCheck;
	}
	public void setOrigAbsenceCheck(String origAbsenceCheck) {
		this.origAbsenceCheck = origAbsenceCheck;
	}
	public String getOrigEmpLeave() {
		return origEmpLeave;
	}
	public void setOrigEmpLeave(String origEmpLeave) {
		this.origEmpLeave = origEmpLeave;
	}
	public String getOrigAppNo() {
		return origAppNo;
	}
	public void setOrigAppNo(String origAppNo) {
		this.origAppNo = origAppNo;
	}
	public String getOrigMachineINpunch() {
		return origMachineINpunch;
	}
	public void setOrigMachineINpunch(String origMachineINpunch) {
		this.origMachineINpunch = origMachineINpunch;
	}
	public String getOrigMachineOUTpunch() {
		return origMachineOUTpunch;
	}
	public void setOrigMachineOUTpunch(String origMachineOUTpunch) {
		this.origMachineOUTpunch = origMachineOUTpunch;
	}
	public String getMachineIdForOfflineINPunch() {
		return machineIdForOfflineINPunch;
	}
	public void setMachineIdForOfflineINPunch(String machineIdForOfflineINPunch) {
		this.machineIdForOfflineINPunch = machineIdForOfflineINPunch;
	}
	public String getMachineIdForOfflineOUTPunch() {
		return machineIdForOfflineOUTPunch;
	}
	public void setMachineIdForOfflineOUTPunch(String machineIdForOfflineOUTPunch) {
		this.machineIdForOfflineOUTPunch = machineIdForOfflineOUTPunch;
	}
}
