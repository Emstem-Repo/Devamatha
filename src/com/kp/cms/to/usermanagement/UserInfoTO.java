package com.kp.cms.to.usermanagement;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.employee.GuestFacultyTO;

public class UserInfoTO {
	private int id;
	private StudentTO studentTO;
	private RolesTO rolesTO;
	private EmployeeTO employeeTO;
	private GuestFacultyTO guestFacultyTO;
	private String userName;
	private String pwd;
	private Boolean isTeachingStaff;
	private String lastLoggedIn;
	private String modifiedBy;
	private String modifieddate;
	private String createdBy;
	private String createddate;
	private Boolean remarksEntry;
	private Boolean viewRemarks;
	private DepartmentEntryTO department;
	private String departmentName;
	private String designationName;
	private String rolesName;
	private Boolean isRestrictedUser;
	private Boolean enableAtendanceEntry;
	private Boolean active;
	private String staffType;
	private String name;
	private String dob;
	private int empOrGuestId;
	private Boolean isLoggedIn;
	private Boolean multipleLoginAllow;
	private String tillDate;
	private boolean checked;
	
	//raghu
	private String phNo;
	private int rollid;
	
	public StudentTO getStudentTO() {
		return studentTO;
	}
	public RolesTO getRolesTO() {
		return rolesTO;
	}
	public EmployeeTO getEmployeeTO() {
		return employeeTO;
	}
	public String getUserName() {
		return userName;
	}
	public String getPwd() {
		return pwd;
	}
	public Boolean getIsTeachingStaff() {
		return isTeachingStaff;
	}
	public void setStudentTO(StudentTO studentTO) {
		this.studentTO = studentTO;
	}
	public void setRolesTO(RolesTO rolesTO) {
		this.rolesTO = rolesTO;
	}
	public void setEmployeeTO(EmployeeTO employeeTO) {
		this.employeeTO = employeeTO;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setIsTeachingStaff(Boolean isTeachingStaff) {
		this.isTeachingStaff = isTeachingStaff;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastLoggedIn() {
		return lastLoggedIn;
	}
	public void setLastLoggedIn(String lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public String getModifieddate() {
		return modifieddate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setModifieddate(String modifieddate) {
		this.modifieddate = modifieddate;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public Boolean getRemarksEntry() {
		return remarksEntry;
	}
	public Boolean getViewRemarks() {
		return viewRemarks;
	}
	public void setRemarksEntry(Boolean remarksEntry) {
		this.remarksEntry = remarksEntry;
	}
	public void setViewRemarks(Boolean viewRemarks) {
		this.viewRemarks = viewRemarks;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getRolesName() {
		return rolesName;
	}
	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}
	public void setIsRestrictedUser(Boolean isRestrictedUser) {
		this.isRestrictedUser = isRestrictedUser;
	}
	public Boolean getIsRestrictedUser() {
		return isRestrictedUser;
	}
	public DepartmentEntryTO getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentEntryTO department) {
		this.department = department;
	}
	public Boolean getEnableAtendanceEntry() {
		return enableAtendanceEntry;
	}
	public void setEnableAtendanceEntry(Boolean enableAtendanceEntry) {
		this.enableAtendanceEntry = enableAtendanceEntry;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getStaffType() {
		return staffType;
	}
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}
	public GuestFacultyTO getGuestFacultyTO() {
		return guestFacultyTO;
	}
	public void setGuestFacultyTO(GuestFacultyTO guestFacultyTO) {
		this.guestFacultyTO = guestFacultyTO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public int getEmpOrGuestId() {
		return empOrGuestId;
	}
	public void setEmpOrGuestId(int empOrGuestId) {
		this.empOrGuestId = empOrGuestId;
	}
	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}
	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
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
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getPhNo() {
		return phNo;
	}
	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}
	public int getRollid() {
		return rollid;
	}
	public void setRollid(int rollid) {
		this.rollid = rollid;
	}

	
}
