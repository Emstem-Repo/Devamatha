package com.kp.cms.forms.usermanagement;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class CreateUserForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private Boolean isAddRemarks;
	private Boolean isViewRemarks;
	private Boolean restrictedUser;
	private String confirmPassword;
	private Boolean teachingStaff;
	private String roleId;
	private String employeeId;
	private String guestId;
	private String departmentId;
	private Map<Integer, String> roles;
	private Map<Integer, String> employeeMap;
	private Map<Integer, String> guestMap;
	private Map<Integer, String> departmentMap;
	private int id;
	private Boolean enableAttendance;
	private Boolean active;
	private String staffType;
	private Boolean multipleLoginAllow;
	private String tillDate;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAddRemarks() {
		return isAddRemarks;
	}

	public void setIsAddRemarks(Boolean isAddRemarks) {
		this.isAddRemarks = isAddRemarks;
	}

	public Boolean getIsViewRemarks() {
		return isViewRemarks;
	}

	public void setIsViewRemarks(Boolean isViewRemarks) {
		this.isViewRemarks = isViewRemarks;
	}

	public Boolean getRestrictedUser() {
		return restrictedUser;
	}

	public void setRestrictedUser(Boolean restrictedUser) {
		this.restrictedUser = restrictedUser;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Boolean getTeachingStaff() {
		return teachingStaff;
	}

	public void setTeachingStaff(Boolean teachingStaff) {
		this.teachingStaff = teachingStaff;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Map<Integer, String> getRoles() {
		return roles;
	}

	public void setRoles(Map<Integer, String> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<Integer, String> getEmployeeMap() {
		return employeeMap;
	}

	public void setEmployeeMap(Map<Integer, String> employeeMap) {
		this.employeeMap = employeeMap;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields(){
		this.active=true;
		this.isAddRemarks=false;
		this.isViewRemarks=false;
		this.restrictedUser=false;
		this.teachingStaff=false;
		this.id=0;
		this.userName=null;
		this.password=null;
		this.roleId=null;
		this.employeeId=null;
		this.departmentId=null;
		this.enableAttendance=false;
		this.guestId=null;
		this.staffType="employee";
		this.multipleLoginAllow=false;
		this.tillDate = null;
		
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Map<Integer, String> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(Map<Integer, String> departmentMap) {
		this.departmentMap = departmentMap;
	}

	public Boolean getEnableAttendance() {
		return enableAttendance;
	}

	public void setEnableAttendance(Boolean enableAttendance) {
		this.enableAttendance = enableAttendance;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getGuestId() {
		return guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	public Map<Integer, String> getGuestMap() {
		return guestMap;
	}

	public void setGuestMap(Map<Integer, String> guestMap) {
		this.guestMap = guestMap;
	}

	public String getStaffType() {
		return staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	public Boolean getMultipleLoginAllow() {
		return multipleLoginAllow;
	}

	public void setMultipleLoginAllow(Boolean multipleLoginAllow) {
		this.multipleLoginAllow = multipleLoginAllow;
	}

	public String getTillDate() {
		return tillDate;
	}

	public void setTillDate(String tillDate) {
		this.tillDate = tillDate;
	}

}
