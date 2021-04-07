package com.kp.cms.helpers.usermanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.UserInfoForm;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;

public class UserInfoHelper {
	private static volatile UserInfoHelper userInfoHelper = null;

	public static UserInfoHelper getInstance() {
		if (userInfoHelper == null) {
			userInfoHelper = new UserInfoHelper();
		}
		return userInfoHelper;
	}

	/**
	 * Method to convert form field values to BO
	 * 
	 * @param userInfoForm
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public Users convertFormToBO(UserInfoForm userInfoForm, String mode,Employee employee)
			throws FileNotFoundException, IOException {

		Users users = new Users();
		employee.setFirstName(userInfoForm.getFirstName());
		employee.setMiddleName(userInfoForm.getMiddleName());
		employee.setLastName(userInfoForm.getLastName());
		employee.setFatherName(userInfoForm.getFatherName());
		employee.setEmail(userInfoForm.getEmailId());

		employee.setPermanentAddressLine1(userInfoForm.getPermanentaddrLine1());
		employee.setPermanentAddressLine2(userInfoForm.getPermanentaddrLine2());
		employee.setPermanentAddressCity(userInfoForm.getPermanentCity());
		if (userInfoForm.getPermanentStateId().equals("Other")) {
			employee.setPermanentAddressStateOthers(userInfoForm
					.getPermanentOtherState());
		} else {
			if (userInfoForm.getPermanentStateId() != null
					&& !userInfoForm.getPermanentStateId().trim().isEmpty()) {
				State permanentState = new State();
				permanentState.setId(Integer.parseInt(userInfoForm
						.getPermanentStateId()));
				employee.setStateByPermanentAddressStateId(permanentState);
			}
		}

		if (userInfoForm.getPermanentCountryId() != null
				&& !userInfoForm.getPermanentCountryId().trim().isEmpty()) {
			Country permanentCountry = new Country();
			permanentCountry.setId(Integer.parseInt(userInfoForm
					.getPermanentCountryId()));
			employee.setCountryByPermanentAddressCountryId(permanentCountry);
		}

		employee.setPermanentAddressZip(userInfoForm.getPermanentPinCode());

		if (userInfoForm.isSameTempAddr()) {
			employee.setCommunicationAddressLine1(userInfoForm
					.getPermanentaddrLine1());
			employee.setCommunicationAddressLine2(userInfoForm
					.getPermanentaddrLine2());
			employee.setCommunicationAddressCity(userInfoForm
					.getPermanentCity());
			if (userInfoForm.getPermanentStateId().equals("Other")) {
				employee.setCommunicationAddressStateOthers(userInfoForm
						.getPermanentOtherState());
			} else {
				if (userInfoForm.getPermanentStateId() != null
						&& !userInfoForm.getPermanentStateId().trim().isEmpty()) {
					State currentState = new State();
					currentState.setId(Integer.parseInt(userInfoForm
							.getPermanentStateId()));
					employee
							.setStateByCommunicationAddressStateId(currentState);
				}
			}
			if (userInfoForm.getPermanentCountryId() != null
					&& !userInfoForm.getPermanentCountryId().trim().isEmpty()) {
				Country currentCountry = new Country();
				currentCountry.setId(Integer.parseInt(userInfoForm
						.getPermanentCountryId()));
				employee
						.setCountryByCommunicationAddressCountryId(currentCountry);
			}
			employee.setCommunicationAddressZip(userInfoForm
					.getPermanentPinCode());

		} else {
			employee.setCommunicationAddressLine1(userInfoForm
					.getCurrentaddrLine1());
			employee.setCommunicationAddressLine2(userInfoForm
					.getCurrentaddrLine2());
			employee.setCommunicationAddressCity(userInfoForm.getCurrentCity());
			if (userInfoForm.getCurrentStateId().equals("Other")) {
				employee.setCommunicationAddressStateOthers(userInfoForm
						.getCurrentOtherState());
			} else {
				if (userInfoForm.getCurrentStateId() != null
						&& !userInfoForm.getCurrentStateId().trim().isEmpty()) {
					State currentState = new State();
					currentState.setId(Integer.parseInt(userInfoForm
							.getCurrentStateId()));
					employee
							.setStateByCommunicationAddressStateId(currentState);
				}
			}

			if (userInfoForm.getCurrentCountryId() != null
					&& !userInfoForm.getCurrentCountryId().trim().isEmpty()) {
				Country currentCountry = new Country();
				currentCountry.setId(Integer.parseInt(userInfoForm
						.getCurrentCountryId()));
				employee
						.setCountryByCommunicationAddressCountryId(currentCountry);
			}
			employee.setCommunicationAddressZip(userInfoForm
					.getCurrentPinCode());
		}

		Department department = new Department();
		department.setId(Integer.parseInt(userInfoForm.getDepartmentId()));
		employee.setDepartment(department);

		Designation designation = new Designation();
		designation.setId(Integer.parseInt(userInfoForm.getDesignationId()));
		employee.setDesignation(designation);

		employee.setDob(CommonUtil.ConvertStringToSQLDate(userInfoForm
				.getDateOfBirth()));
		employee.setDoj(CommonUtil.ConvertStringToSQLDate(userInfoForm
				.getDateOfJoining()));
		employee.setBloodGroup(userInfoForm.getBloodGroup());
	//	employee.setPhone1(userInfoForm.getPhone1());
	//	employee.setPhone2(userInfoForm.getMobile1());

//		if (userInfoForm.getEmpPhoto() != null
//				&& userInfoForm.getEmpPhoto().getFileData() != null
//				&& userInfoForm.getEmpPhoto().getFileName() != null
//				&& !userInfoForm.getEmpPhoto().getFileName().isEmpty()
//				&& userInfoForm.getEmpPhoto().getContentType() != null
//				&& !userInfoForm.getEmpPhoto().getContentType().isEmpty()) {
//
//			employee.setEmpPhoto(userInfoForm.getEmpPhoto().getFileData());
//		} else if (mode.equalsIgnoreCase("edit")
//				&& userInfoForm.getOrigEmpPhoto() != null) {
//			employee.setEmpPhoto(userInfoForm.getOrigEmpPhoto());
//		}

		employee.setCreatedDate(new Date());
		employee.setIsActive(true);

		if (userInfoForm.getUserName() != null
				&& !userInfoForm.getUserName().trim().isEmpty()) {
			users.setUserName(userInfoForm.getUserName());
		}
		if (mode.equalsIgnoreCase(CMSConstants.EDIT_OPERATION)) {
			if (userInfoForm.getPassword() != null
					&& !userInfoForm.getPassword().trim().isEmpty()) {
				if (userInfoForm.getOrigPassword() != null
						&& !userInfoForm.getOrigPassword().trim().isEmpty()) {
					if (!(userInfoForm.getOrigPassword().equals(userInfoForm
							.getPassword()))) {
						users.setPwd(EncryptUtil.getInstance().encryptDES(
								userInfoForm.getPassword()));
						users.setAndroidPwd(Base64Coder.encodeString(userInfoForm.getPassword()));
					} else {
						users.setPwd(EncryptUtil.getInstance().encryptDES(userInfoForm.getPassword()));
						users.setAndroidPwd(Base64Coder.encodeString(userInfoForm.getPassword()));
					}
				} else {
					users.setPwd(EncryptUtil.getInstance().encryptDES(
							userInfoForm.getPassword()));
					users.setAndroidPwd(Base64Coder.encodeString(userInfoForm.getPassword()));
				}
			}
		} else {
			if (userInfoForm.getUserName() != null
					&& !userInfoForm.getUserName().trim().isEmpty()) {
				users.setPwd(EncryptUtil.getInstance().encryptDES(
						userInfoForm.getPassword()));
				users.setAndroidPwd(Base64Coder.encodeString(userInfoForm.getPassword()));
			}
		}
		Roles roles = new Roles();
		roles.setId(Integer.parseInt(userInfoForm.getRoleId()));
		if (mode.equalsIgnoreCase(CMSConstants.EDIT_OPERATION)) {
			users.setId(userInfoForm.getId());
			employee.setId(userInfoForm.getEmplId());
		}
		users.setRoles(roles);
		users.setEmployee(employee);
		users.setCreatedDate(new Date());
		users.setLastModifiedDate(new Date());
		users.setCreatedBy(userInfoForm.getUserId());
		users.setModifiedBy(userInfoForm.getUserId());
		users.setIsActive(true);
		users.setIsAddRemarks(userInfoForm.getIsAddRemarks());
		users.setIsViewRemarks(userInfoForm.getIsViewRemarks());
		users.setIsTeachingStaff(userInfoForm.isTeachingStaff());
		users.setIsRestrictedUser(userInfoForm.isRestrictedUser());
		return users;
	}

	/**
	 * 
	 * @param userInfoList
	 * @return
	 */
	public List<UserInfoTO> copyBosToTos(List<Users> userInfoList) {
		List<UserInfoTO> userInfoToList = new ArrayList<UserInfoTO>();
		Iterator<Users> i = userInfoList.iterator();
		Users users;
		UserInfoTO userInfoTO;
		EmployeeTO employeeTO;
		GuestFacultyTO guestFacultyTO;
		RolesTO rolesTO;
		DepartmentEntryTO departmentTO;
		SingleFieldMasterTO singleFieldMasterTO; // department
		SingleFieldMasterTO designationTO;
		CountryTO permAddCountryTo;
		CountryTO commAddCountryTo;
		StateTO permAddStateTo;
		StateTO curAddStateTo;
		while (i.hasNext()) {
			userInfoTO = new UserInfoTO();
			guestFacultyTO = new GuestFacultyTO();
			employeeTO = new EmployeeTO();
			rolesTO = new RolesTO();
			departmentTO= new DepartmentEntryTO();
			singleFieldMasterTO = new SingleFieldMasterTO();
			users = (Users) i.next();
			if (users.getEmployee() != null) {
				employeeTO.setId((users.getEmployee() == null ? 0 : users
						.getEmployee().getId()));
				employeeTO
						.setFirstName(users.getEmployee().getFirstName() == null ? ""
								: users.getEmployee().getFirstName());
				employeeTO
						.setMiddleName(users.getEmployee().getMiddleName() == null ? ""
								: users.getEmployee().getMiddleName());
				employeeTO
						.setLastName(users.getEmployee().getLastName() == null ? ""
								: users.getEmployee().getLastName());
				if (users.getEmployee().getDob() != null) {
					employeeTO.setDob(CommonUtil.formatDate(users.getEmployee()
							.getDob(), "dd/MM/yyyy"));
				}

				employeeTO.setName(users.getEmployee().getFirstName()
						+ " "
						+ (users.getEmployee().getMiddleName() != null
								&& users.getEmployee().getMiddleName().trim()
										.length() > 0 ? users.getEmployee()
								.getMiddleName() : "")
						+ " "
						+ (users.getEmployee().getLastName() != null
								&& users.getEmployee().getLastName().trim()
										.length() > 0 ? users.getEmployee()
								.getLastName() : ""));
				employeeTO
						.setFatherName(users.getEmployee().getFatherName() == null ? ""
								: users.getEmployee().getFatherName());
				employeeTO.setDoj(users.getEmployee().getDoj());
				employeeTO.setBloodGroup(users.getEmployee().getBloodGroup());
			//	employeeTO.setPhone1(users.getEmployee().getPhone1());
			//	employeeTO.setPhone2(users.getEmployee().getPhone2());
				employeeTO.setPermanentAddressLine1(users.getEmployee()
						.getPermanentAddressLine1());
				employeeTO.setPermanentAddressLine2(users.getEmployee()
						.getPermanentAddressLine2());
				employeeTO.setPermanentAddressCity(users.getEmployee()
						.getPermanentAddressCity());
				employeeTO.setPermanentAddressZip(users.getEmployee()
						.getPermanentAddressZip());
				employeeTO.setPermanentAddressStateOthers(users.getEmployee()
						.getCommunicationAddressStateOthers());


				if (users.getEmployee() != null
						&& users.getEmployee()
								.getCountryByCommunicationAddressCountryId() != null
						&& users.getEmployee()
								.getCountryByCommunicationAddressCountryId()
								.getId() != 0) {
					permAddCountryTo = new CountryTO();
					permAddCountryTo.setId(users.getEmployee()
							.getCountryByCommunicationAddressCountryId()
							.getId());
					employeeTO
							.setCountryByPermanentAddressCountryId(permAddCountryTo);
				}
				if (users.getEmployee() != null
						&& users.getEmployee()
								.getCountryByCommunicationAddressCountryId() != null
						&& users.getEmployee()
								.getCountryByCommunicationAddressCountryId()
								.getId() != 0) {
					commAddCountryTo = new CountryTO();
					commAddCountryTo.setId(users.getEmployee()
							.getCountryByCommunicationAddressCountryId()
							.getId());
					employeeTO
							.setCountryByCommunicationAddressCountryId(commAddCountryTo);
				}
				employeeTO.setCommunicationAddressLine1(users.getEmployee()
						.getCommunicationAddressLine1());
				employeeTO.setCommunicationAddressLine2(users.getEmployee()
						.getCommunicationAddressLine2());
				employeeTO.setCommunicationAddressZip(users.getEmployee()
						.getCommunicationAddressZip());
				employeeTO.setCommunicationAddressCity(users.getEmployee()
						.getCommunicationAddressCity());
				employeeTO.setCommunicationAddressStateOthers(users
						.getEmployee().getCommunicationAddressStateOthers());

				employeeTO.setEmail(users.getEmployee().getEmail());
				if (users.getEmployee() != null
						&& users.getEmployee()
								.getStateByPermanentAddressStateId() != null
						&& users.getEmployee()
								.getStateByPermanentAddressStateId().getId() != 0) {
					permAddStateTo = new StateTO();
					permAddStateTo.setId(users.getEmployee()
							.getStateByPermanentAddressStateId().getId());
					employeeTO
							.setStateByPermanentAddressStateId(permAddStateTo);
				}

				if (users.getEmployee() != null
						&& users.getEmployee()
								.getStateByCommunicationAddressStateId() != null
						&& users.getEmployee()
								.getStateByCommunicationAddressStateId()
								.getId() != 0) {
					curAddStateTo = new StateTO();
					curAddStateTo.setId(users.getEmployee()
							.getStateByCommunicationAddressStateId().getId());
					employeeTO
							.setStateByCommunicationAddressStateId(curAddStateTo);
				}
				if (users.getEmployee().getDepartment() != null
						&& users.getEmployee().getDepartment().getId() != 0) {
					departmentTO.setId(users.getEmployee()
							.getDepartment().getId());
					departmentTO.setName(users.getEmployee()
							.getDepartment().getName());
					employeeTO.setDepartmentTo(departmentTO);
					userInfoTO.setDepartmentName(users.getEmployee().getDepartment().getName());
				}
				 
								
				if (users.getEmployee().getDesignation() != null
						&& users.getEmployee().getDesignation().getId() != 0) {
					designationTO = new SingleFieldMasterTO();
					designationTO.setId(users.getEmployee().getDesignation()
							.getId());
					designationTO.setName(users.getEmployee().getDesignation()
							.getName());
					employeeTO.setDesignationTO(designationTO);
					userInfoTO.setDesignationName(users.getEmployee()
							.getDesignation().getName());
				}
			}else if(users.getGuest()!=null)
			{
				guestFacultyTO.setId((users.getGuest() == null ? 0 : users
						.getGuest().getId()));
			guestFacultyTO
						.setFirstName(users.getGuest().getFirstName() == null ? ""
								: users.getGuest().getFirstName());
				
				if (users.getGuest().getDob() != null) {
					guestFacultyTO.setDob(CommonUtil.formatDate(users.getGuest()
							.getDob(), "dd/MM/yyyy"));
				}

				guestFacultyTO.setName(users.getGuest().getFirstName());
				
				
		
				guestFacultyTO.setPermanentAddressLine1(users.getGuest()
						.getPermanentAddressLine1());
				guestFacultyTO.setPermanentAddressLine2(users.getGuest()
						.getPermanentAddressLine2());
				guestFacultyTO.setPermanentAddressCity(users.getGuest()
						.getPermanentAddressCity());
				guestFacultyTO.setPermanentAddressZip(users.getGuest()
						.getPermanentAddressZip());
				guestFacultyTO.setPermanentAddressStateOthers(users.getGuest()
						.getCommunicationAddressStateOthers());


				if (users.getGuest() != null
						&& users.getGuest()
								.getCountryByCommunicationAddressCountryId() != null
						&& users.getGuest()
								.getCountryByCommunicationAddressCountryId()
								.getId() != 0) {
					permAddCountryTo = new CountryTO();
					permAddCountryTo.setId(users.getGuest()
							.getCountryByCommunicationAddressCountryId()
							.getId());
					guestFacultyTO
							.setCountryByPermanentAddressCountryId(permAddCountryTo);
				}
				if (users.getGuest()!= null
						&& users.getGuest()
								.getCountryByCommunicationAddressCountryId() != null
						&& users.getGuest()
								.getCountryByCommunicationAddressCountryId()
								.getId() != 0) {
					commAddCountryTo = new CountryTO();
					commAddCountryTo.setId(users.getGuest()
							.getCountryByCommunicationAddressCountryId()
							.getId());
					guestFacultyTO
							.setCountryByCommunicationAddressCountryId(commAddCountryTo);
				}
				guestFacultyTO.setCommunicationAddressLine1(users.getGuest()
						.getCommunicationAddressLine1());
				guestFacultyTO.setCommunicationAddressLine2(users.getGuest()
						.getCommunicationAddressLine2());
				guestFacultyTO.setCommunicationAddressZip(users.getGuest()
						.getCommunicationAddressZip());
				guestFacultyTO.setCommunicationAddressCity(users.getGuest()
						.getCommunicationAddressCity());
				guestFacultyTO.setCommunicationAddressStateOthers(users
						.getGuest().getCommunicationAddressStateOthers());

				guestFacultyTO.setEmail(users.getGuest().getEmail());
				if (users.getGuest() != null
						&& users.getGuest()
								.getStateByPermanentAddressStateId() != null
						&& users.getGuest()
								.getStateByPermanentAddressStateId().getId() != 0) {
					permAddStateTo = new StateTO();
					permAddStateTo.setId(users.getGuest()
							.getStateByPermanentAddressStateId().getId());
					guestFacultyTO
							.setStateByPermanentAddressStateId(permAddStateTo);
				}

				if (users.getGuest() != null
						&& users.getGuest()
								.getStateByCommunicationAddressStateId() != null
						&& users.getGuest()
								.getStateByCommunicationAddressStateId()
								.getId() != 0) {
					curAddStateTo = new StateTO();
					curAddStateTo.setId(users.getGuest()
							.getStateByCommunicationAddressStateId().getId());
					guestFacultyTO
							.setStateByCommunicationAddressStateId(curAddStateTo);
				}
				if (users.getGuest().getDepartment() != null
						&& users.getGuest().getDepartment().getId() != 0) {
					departmentTO.setId(users.getGuest()
							.getDepartment().getId());
					departmentTO.setName(users.getGuest()
							.getDepartment().getName());
					guestFacultyTO.setDepartmentTo(departmentTO);
					userInfoTO.setDepartmentName(users.getGuest().getDepartment().getName());
				}
				 
								
				if (users.getGuest().getDesignation() != null
						&& users.getGuest().getDesignation().getId() != 0) {
					designationTO = new SingleFieldMasterTO();
					designationTO.setId(users.getGuest().getDesignation()
							.getId());
					designationTO.setName(users.getGuest().getDesignation()
							.getName());
					guestFacultyTO.setDesignationTO(designationTO);
					userInfoTO.setDesignationName(users.getGuest()
							.getDesignation().getName());
		}
		}	else
			{
			if ( users.getDepartment() != null && users.getDepartment().getId()!= 0)
			{
				departmentTO.setId(users.getDepartment().getId());
				departmentTO.setName(users.getDepartment().getName());
				userInfoTO.setDepartment(departmentTO);
				userInfoTO.setDepartmentName(users.getDepartment().getName());
				
			}
			}
			if(employeeTO.getId()>0)
			{
			userInfoTO.setEmployeeTO(employeeTO);
			}
			if(guestFacultyTO.getId()>0)
			{
			userInfoTO.setGuestFacultyTO(guestFacultyTO);
			}
			if (users.getRoles() != null && users.getRoles().getId() != 0) {
				rolesTO.setId(users.getRoles().getId());
				rolesTO.setName(users.getRoles().getName());
				userInfoTO.setRolesTO(rolesTO);
				userInfoTO.setRolesName(users.getRoles().getName());
			}
			if (users.getUserName() != null
					&& !users.getUserName().trim().isEmpty()) {
				userInfoTO.setUserName(users.getUserName());
			}
			userInfoTO.setId(users.getId());
			userInfoTO.setIsTeachingStaff(users.getIsTeachingStaff());
			if (users.getPwd() != null && !users.getPwd().trim().isEmpty()) {
				userInfoTO.setPwd(users.getPwd());
			}
			userInfoTO.setStaffType(users.getStaffType());
			userInfoTO.setRemarksEntry(users.getIsAddRemarks());
			userInfoTO.setViewRemarks(users.getIsViewRemarks());
			userInfoTO.setIsRestrictedUser(users.getIsRestrictedUser());
			userInfoTO.setEnableAtendanceEntry(users.getEnableAttendanceEntry());
			userInfoTO.setActive(users.getActive());
			if(users.getIsLoggedIn()!=null)
			{
			userInfoTO.setIsLoggedIn(users.getIsLoggedIn());
			}
			if(users.getMultipleLoginAllow()!=null)
			{
			userInfoTO.setMultipleLoginAllow(users.getMultipleLoginAllow());
			}
			if(users.getTillDate()!=null){
				userInfoTO.setTillDate(CommonUtil.formatDate(users.getTillDate(), "dd/MM/yyyy"));
			}
			userInfoToList.add(userInfoTO);
			
		}
		return userInfoToList;
	}
}
