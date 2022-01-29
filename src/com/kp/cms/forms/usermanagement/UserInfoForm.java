package com.kp.cms.forms.usermanagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.usermanagement.UserInfoTO;

public class UserInfoForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String middleName;
	private String lastName;
	private String fatherName;
	private String userName;
	private String password;
	private String emailId;
	private String dateOfBirth;
	private String dateOfJoining;
	private String bloodGroup;
	private String phone1;
	private String phone2;
	private String phone3;
	private String mobile1;
	private String mobile2;
	private String mobile3;
	private String departmentId;
	private String designationId;
	private String roleId;
	private String permanentaddrLine1;
	private String permanentaddrLine2;
	private String permanentCity;
	private String permanentStateId;
	private String permanentCountryId;
	private String permanentPinCode;
	private String currentaddrLine1;
	private String currentaddrLine2;
	private String currentCity;
	private String currentStateId;
	private String currentCountryId;
	private String currentPinCode;
	private Map<Integer, String> department;
	private Map<Integer, String> designation;
	private Map<Integer, String> roles;
	private Map<Integer, String> country;
	private boolean sameTempAddr;
	private String currentOtherState;
	private String permanentOtherState;
	private boolean teachingStaff;
	private String searchDob;
	private String searchDepartment;
	private String firstsearchName;
	private String middlesearchName;
	private String lastsearchName;
	private int id;
	private int emplId;
	private String origPassword;
	private Boolean isAddRemarks;
	private Boolean isViewRemarks;
	private FormFile empPhoto;
	private byte[] origEmpPhoto;
	private boolean restrictedUser;
	private String guestId;
	private String staffType;
	
	private List<UserInfoTO> userInfoTOList;
	
	public boolean isTeachingStaff() {
		return teachingStaff;
	}
	public void setTeachingStaff(boolean teachingStaff) {
		this.teachingStaff = teachingStaff;
	}
	public String getCurrentOtherState() {
		return currentOtherState;
	}
	public void setCurrentOtherState(String currentOtherState) {
		this.currentOtherState = currentOtherState;
	}
	public String getPermanentOtherState() {
		return permanentOtherState;
	}
	public void setPermanentOtherState(String permanentOtherState) {
		this.permanentOtherState = permanentOtherState;
	}	
	public boolean isSameTempAddr() {
		return sameTempAddr;
	}
	public void setSameTempAddr(boolean sameTempAddr) {
		this.sameTempAddr = sameTempAddr;
	}
	public Map<Integer, String> getCountry() {
		return country;
	}
	public void setCountry(Map<Integer, String> country) {
		this.country = country;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPermanentaddrLine1() {
		return permanentaddrLine1;
	}
	public void setPermanentaddrLine1(String permanentaddrLine1) {
		this.permanentaddrLine1 = permanentaddrLine1;
	}
	public String getPermanentaddrLine2() {
		return permanentaddrLine2;
	}
	public void setPermanentaddrLine2(String permanentaddrLine2) {
		this.permanentaddrLine2 = permanentaddrLine2;
	}
	public String getPermanentCity() {
		return permanentCity;
	}
	public void setPermanentCity(String permanentCity) {
		this.permanentCity = permanentCity;
	}
	public String getPermanentStateId() {
		return permanentStateId;
	}
	public void setPermanentStateId(String permanentStateId) {
		this.permanentStateId = permanentStateId;
	}
	public String getPermanentCountryId() {
		return permanentCountryId;
	}
	public void setPermanentCountryId(String permanentCountryId) {
		this.permanentCountryId = permanentCountryId;
	}
	public String getPermanentPinCode() {
		return permanentPinCode;
	}
	public void setPermanentPinCode(String permanentPinCode) {
		this.permanentPinCode = permanentPinCode;
	}
	public String getCurrentaddrLine1() {
		return currentaddrLine1;
	}
	public void setCurrentaddrLine1(String currentaddrLine1) {
		this.currentaddrLine1 = currentaddrLine1;
	}
	public String getCurrentaddrLine2() {
		return currentaddrLine2;
	}
	public void setCurrentaddrLine2(String currentaddrLine2) {
		this.currentaddrLine2 = currentaddrLine2;
	}
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getCurrentStateId() {
		return currentStateId;
	}
	public void setCurrentStateId(String currentStateId) {
		this.currentStateId = currentStateId;
	}
	public String getCurrentCountryId() {
		return currentCountryId;
	}
	public void setCurrentCountryId(String currentCountryId) {
		this.currentCountryId = currentCountryId;
	}
	public String getCurrentPinCode() {
		return currentPinCode;
	}
	public void setCurrentPinCode(String currentPinCode) {
		this.currentPinCode = currentPinCode;
	}
	public Map<Integer, String> getDepartment() {
		return department;
	}
	public void setDepartment(Map<Integer, String> department) {
		this.department = department;
	}
	public Map<Integer, String> getDesignation() {
		return designation;
	}
	public void setDesignation(Map<Integer, String> designation) {
		this.designation = designation;
	}
	public Map<Integer, String> getRoles() {
		return roles;
	}
	public void setRoles(Map<Integer, String> roles) {
		this.roles = roles;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getMobile3() {
		return mobile3;
	}
	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}
	public String getSearchDob() {
		return searchDob;
	}
	public String getSearchDepartment() {
		return searchDepartment;
	}
	public void setSearchDob(String searchDob) {
		this.searchDob = searchDob;
	}
	public void setSearchDepartment(String searchDepartment) {
		this.searchDepartment = searchDepartment;
	}
	public String getFirstsearchName() {
		return firstsearchName;
	}
	public String getMiddlesearchName() {
		return middlesearchName;
	}
	public String getLastsearchName() {
		return lastsearchName;
	}
	public void setFirstsearchName(String firstsearchName) {
		this.firstsearchName = firstsearchName;
	}
	public void setMiddlesearchName(String middlesearchName) {
		this.middlesearchName = middlesearchName;
	}
	public void setLastsearchName(String lastsearchName) {
		this.lastsearchName = lastsearchName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getEmplId() {
		return emplId;
	}
	public void setEmplId(int emplId) {
		this.emplId = emplId;
	}
	
	public Boolean getIsAddRemarks() {
		return isAddRemarks;
	}
	public Boolean getIsViewRemarks() {
		return isViewRemarks;
	}
	public void setIsAddRemarks(Boolean isAddRemarks) {
		this.isAddRemarks = isAddRemarks;
	}
	public void setIsViewRemarks(Boolean isViewRemarks) {
		this.isViewRemarks = isViewRemarks;
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getOrigPassword() {
		return origPassword;
	}
	public void setOrigPassword(String origPassword) {
		this.origPassword = origPassword;
	}
	public FormFile getEmpPhoto() {
		return empPhoto;
	}
	public void setEmpPhoto(FormFile empPhoto) {
		this.empPhoto = empPhoto;
	}
	public byte[] getOrigEmpPhoto() {
		return origEmpPhoto;
	}
	public void setOrigEmpPhoto(byte[] origEmpPhoto) {
		this.origEmpPhoto = origEmpPhoto;
	}
	public void setUserInfoTOList(List<UserInfoTO> userInfoTOList) {
		this.userInfoTOList = userInfoTOList;
	}
	public List<UserInfoTO> getUserInfoTOList() {
		return userInfoTOList;
	}
	public void setRestrictedUser(boolean restrictedUser) {
		this.restrictedUser = restrictedUser;
	}
	public boolean isRestrictedUser() {
		return restrictedUser;
	}
	public String getGuestId() {
		return guestId;
	}
	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}
	public String getStaffType() {
		return staffType;
	}
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}
	
	
	
}
