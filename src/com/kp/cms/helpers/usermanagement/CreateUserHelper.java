package com.kp.cms.helpers.usermanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.tools.codec.Base64Encoder;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.forms.usermanagement.CreateUserForm;
import com.kp.cms.forms.usermanagement.EditUserInfoForm;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;

public class CreateUserHelper {
	/**
	 * Singleton object of CreateUserHelper
	 */
	private static volatile CreateUserHelper createUserHelper = null;
	private static final Log log = LogFactory.getLog(CreateUserHelper.class);
	private CreateUserHelper() {
		
	}
	/**
	 * return singleton object of CreateUserHelper.
	 * @return
	 */
	public static CreateUserHelper getInstance() {
		if (createUserHelper == null) {
			createUserHelper = new CreateUserHelper();
		}
		return createUserHelper;
	}
	/**
	 * @param createUserForm
	 * @return
	 * @throws Exception
	 */
	public Users convertFormToBo(CreateUserForm createUserForm) throws Exception{
		Users users = new Users();
		if(createUserForm.getId()>0)
			users.setId(createUserForm.getId());
		users.setUserName(createUserForm.getUserName());
		users.setPwd(EncryptUtil.getInstance().encryptDES(createUserForm.getPassword()));
		users.setAndroidPwd(Base64Coder.encodeString(createUserForm.getPassword()));
		Roles roles = new Roles();
		roles.setId(Integer.parseInt(createUserForm.getRoleId()));
		users.setRoles(roles);
		
		if(createUserForm.getDepartmentId()!=null && !createUserForm.getDepartmentId().isEmpty())
		{
		Department department = new Department();
		department.setId(Integer.parseInt(createUserForm.getDepartmentId()));
		users.setDepartment(department);
		}
		if(createUserForm.getTillDate()!=null && !createUserForm.getTillDate().isEmpty())
		    users.setTillDate(CommonUtil.ConvertStringToSQLDate(createUserForm.getTillDate()));
		users.setCreatedDate(new Date());
		users.setLastModifiedDate(new Date());
		users.setCreatedBy(createUserForm.getUserId());
		users.setModifiedBy(createUserForm.getUserId());
		users.setIsActive(true);
		users.setIsAddRemarks(createUserForm.getIsAddRemarks());
		users.setIsViewRemarks(createUserForm.getIsViewRemarks());
		users.setIsTeachingStaff(createUserForm.getTeachingStaff());
		users.setIsRestrictedUser(createUserForm.getRestrictedUser());
		users.setEnableAttendanceEntry(createUserForm.getEnableAttendance());
		users.setStaffType(createUserForm.getStaffType());
		users.setActive(createUserForm.getActive());
		users.setMultipleLoginAllow(createUserForm.getMultipleLoginAllow());
		if(createUserForm.getEmployeeId()!=null && !createUserForm.getEmployeeId().isEmpty() && !createUserForm.getEmployeeId().equalsIgnoreCase("0")){
			Employee employee=new Employee();
			employee.setId(Integer.parseInt(createUserForm.getEmployeeId()));
			users.setEmployee(employee);
		}
		if(createUserForm.getGuestId()!=null && !createUserForm.getGuestId().isEmpty() && !createUserForm.getGuestId().equalsIgnoreCase("0")){
			GuestFaculty guest=new GuestFaculty();
			guest.setId(Integer.parseInt(createUserForm.getGuestId()));
			users.setGuest(guest);
		}
		return users;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<UserInfoTO> convertBotoTO(List<Users> list) throws Exception {
		List<UserInfoTO> userInfoToList = new ArrayList<UserInfoTO>();
		Iterator<Users> i = list.iterator();
		Users users;
		UserInfoTO userInfoTO;
		EmployeeTO employeeTO;
		GuestFacultyTO guestFacultyTO;
		RolesTO rolesTO;
		DepartmentEntryTO departmentTO;
		while (i.hasNext()) {
			userInfoTO = new UserInfoTO();
			guestFacultyTO = new GuestFacultyTO();
			employeeTO = new EmployeeTO();
			rolesTO = new RolesTO();
			departmentTO = new DepartmentEntryTO();
			users = (Users) i.next();
			if (users.getEmployee() != null) {
				employeeTO.setId((users.getEmployee() == null ? 0 : users .getEmployee().getId()));
				userInfoTO.setEmpOrGuestId(users .getEmployee().getId());
				employeeTO .setFirstName(users.getEmployee().getFirstName() == null ? "" : users.getEmployee().getFirstName());
				employeeTO .setMiddleName(users.getEmployee().getMiddleName() == null ? "" : users.getEmployee().getMiddleName());
				employeeTO .setLastName(users.getEmployee().getLastName() == null ? "" : users.getEmployee().getLastName());
				if (users.getEmployee().getDob() != null) {
					employeeTO.setDob(CommonUtil.formatDate(users.getEmployee() .getDob(), "dd/MM/yyyy"));
					userInfoTO.setDob(CommonUtil.formatDate(users.getEmployee().getDob(), "dd/MM/yyyy"));
				}
				employeeTO.setName(users.getEmployee().getFirstName() + " "
						+ (users.getEmployee().getMiddleName() != null && users.getEmployee().getMiddleName().trim()
										.length() > 0 ? users.getEmployee() .getMiddleName() : "")
						+ " " + (users.getEmployee().getLastName() != null && users.getEmployee().getLastName().trim()
										.length() > 0 ? users.getEmployee() .getLastName() : ""));
				userInfoTO.setName(users.getEmployee().getFirstName());
				
			}
			if(users.getGuest()!=null)
			{
			guestFacultyTO.setId((users.getGuest() == null ? 0 : users .getGuest().getId()));
		
			userInfoTO.setEmpOrGuestId(users .getGuest().getId());
			guestFacultyTO.setFirstName(users.getGuest().getFirstName() == null ? "" : users.getGuest().getFirstName());
			guestFacultyTO.setName(users.getGuest().getFirstName());
			userInfoTO.setName(users.getGuest().getFirstName());
			if (users.getGuest().getDob() != null) {
				guestFacultyTO.setDob(CommonUtil.formatDate(users.getGuest() .getDob(), "dd/MM/yyyy"));
				userInfoTO.setDob(CommonUtil.formatDate(users.getGuest().getDob(), "dd/MM/yyyy"));
				}
			
			}	
			userInfoTO.setGuestFacultyTO(guestFacultyTO);
			userInfoTO.setEmployeeTO(employeeTO);	
			
			
			if (users.getRoles() != null && users.getRoles().getId() != 0) {
				rolesTO.setId(users.getRoles().getId());
				rolesTO.setName(users.getRoles().getName());
				userInfoTO.setRolesTO(rolesTO);
				userInfoTO.setRolesName(users.getRoles().getName());
			}
			if (users.getDepartment() != null && users.getDepartment().getId() != 0) {
				departmentTO.setId(users.getDepartment().getId());
				departmentTO.setName(users.getDepartment().getName());
				userInfoTO.setDepartment(departmentTO);
				userInfoTO.setDepartmentName(users.getDepartment().getName());
			}
			if (users.getUserName() != null && !users.getUserName().trim().isEmpty()) {
				userInfoTO.setUserName(users.getUserName());
			}
			userInfoTO.setId(users.getId());
			userInfoTO.setIsTeachingStaff(users.getIsTeachingStaff());
			if (users.getPwd() != null && !users.getPwd().trim().isEmpty()) {
				userInfoTO.setPwd(users.getPwd());
			}
			if(users.getStaffType()!=null && !users.getStaffType().isEmpty()){
				userInfoTO.setStaffType(users.getStaffType());
			}
			userInfoTO.setRemarksEntry(users.getIsAddRemarks());
			userInfoTO.setViewRemarks(users.getIsViewRemarks());
			userInfoTO.setIsRestrictedUser(users.getIsRestrictedUser());
			userInfoTO.setActive(users.getActive());
			if(users.getTillDate()!=null){
				userInfoTO.setTillDate(CommonUtil.formatDate(users.getTillDate(), "dd/MM/yyyy"));
			}
			userInfoToList.add(userInfoTO);
		}
		return userInfoToList;
	}
	
	/**
	 * @param createUserForm
	 * @return
	 * @throws Exception
	 */
	public Users convertFormToBoForUpdate(EditUserInfoForm createUserForm) throws Exception{
		Users users = new Users();
		if(createUserForm.getId()>0)
			users.setId(createUserForm.getId());
		users.setUserName(createUserForm.getUserName());
		users.setPwd(EncryptUtil.getInstance().encryptDES(createUserForm.getPassword()));
		users.setAndroidPwd(Base64Coder.encodeString(createUserForm.getPassword()));
		Roles roles = new Roles();
		roles.setId(Integer.parseInt(createUserForm.getRoleId()));
		users.setRoles(roles);
		users.setCreatedDate(new Date());
		users.setLastModifiedDate(new Date());
		users.setCreatedBy(createUserForm.getUserId());
		users.setModifiedBy(createUserForm.getUserId());
		users.setIsActive(true);
		users.setIsAddRemarks(createUserForm.getIsAddRemarks());
		users.setIsViewRemarks(createUserForm.getIsViewRemarks());
		users.setIsTeachingStaff(createUserForm.getTeachingStaff());
		users.setIsRestrictedUser(createUserForm.getRestrictedUser());
		users.setActive(createUserForm.getActive());
		users.setStaffType(createUserForm.getStaffType());
		users.setMultipleLoginAllow(createUserForm.getMultipleLoginAllow());
		users.setIsLoggedIn(createUserForm.getAlreadyLoggedIn());
		if(createUserForm.getDepartmentId()!=null && !createUserForm.getDepartmentId().isEmpty())
		{
		Department department = new Department();
		department.setId(Integer.parseInt(createUserForm.getDepartmentId()));
		users.setDepartment(department);
		}
		users.setEnableAttendanceEntry(createUserForm.getEnableAttendance());
		if(createUserForm.getEmployeeId()!=null && !createUserForm.getEmployeeId().isEmpty() && !createUserForm.getEmployeeId().equalsIgnoreCase("0")){
			Employee employee=new Employee();
			employee.setId(Integer.parseInt(createUserForm.getEmployeeId()));
			users.setEmployee(employee);
		}
		if(createUserForm.getGuestId()!=null && !createUserForm.getGuestId().isEmpty() && !createUserForm.getGuestId().equalsIgnoreCase("0")){
			GuestFaculty guest=new GuestFaculty();
			guest.setId(Integer.parseInt(createUserForm.getGuestId()));
			users.setGuest(guest);
		}
		if(createUserForm.getTillDate()!=null && !createUserForm.getTillDate().isEmpty()){
			users.setTillDate(CommonUtil.ConvertStringToSQLDate(createUserForm.getTillDate()));
		}
		return users;
	}
}
