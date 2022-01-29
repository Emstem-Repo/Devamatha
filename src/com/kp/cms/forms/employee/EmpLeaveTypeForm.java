package com.kp.cms.forms.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EmpLeaveTypeTo;

public class EmpLeaveTypeForm extends BaseActionForm{
	private String leaveType;
	private String id;
	private String code;
	private List<EmpLeaveTypeTo> empLeaveTypeTo;
	private Map<String,EmpLeaveType> leaveMap;
	private EmpLeaveType empLeaveType;
	private String editOrReset;
	private String isLeave;
	private String isException;
	private Map<String,String> codeMap;
	private String continuousdays;
	private String canapplyonline;
	
	public String getLeaveType() {
		return leaveType;
	}
	public String getId() {
		return id;
	}
	public List<EmpLeaveTypeTo> getEmpLeaveTypeTo() {
		return empLeaveTypeTo;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setEmpLeaveTypeTo(List<EmpLeaveTypeTo> empLeaveTypeTo) {
		this.empLeaveTypeTo = empLeaveTypeTo;
	}
	
	public Map<String, EmpLeaveType> getLeaveMap() {
		return leaveMap;
	}
	public void setLeaveMap(Map<String, EmpLeaveType> leaveMap) {
		this.leaveMap = leaveMap;
	}
	public EmpLeaveType getEmpLeaveType() {
		return empLeaveType;
	}
	public void setEmpLeaveType(EmpLeaveType empLeaveType) {
		this.empLeaveType = empLeaveType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEditOrReset() {
		return editOrReset;
	}
	public void setEditOrReset(String editOrReset) {
		this.editOrReset = editOrReset;
	}
	public String getIsLeave() {
		return isLeave;
	}
	public String getIsException() {
		return isException;
	}
	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}
	public void setIsException(String isException) {
		this.isException = isException;
	}
	public Map<String, String> getCodeMap() {
		return codeMap;
	}
	public void setCodeMap(Map<String, String> codeMap) {
		this.codeMap = codeMap;
	}
	
	public String getContinuousdays() {
		return continuousdays;
	}
	public void setContinuousdays(String continuousdays) {
		this.continuousdays = continuousdays;
	}
	public void clear(){
		this.leaveType=null;
		this.id=null;
		this.code=null;
		this.editOrReset="ADD";
		this.isLeave=null;
		this.isException=null;
		this.codeMap=new HashMap<String, String>();
		this.continuousdays="false";
		this.canapplyonline="false";
	}
	
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public String getCanapplyonline() {
		return canapplyonline;
	}
	public void setCanapplyonline(String canapplyonline) {
		this.canapplyonline = canapplyonline;
	}
	

}
