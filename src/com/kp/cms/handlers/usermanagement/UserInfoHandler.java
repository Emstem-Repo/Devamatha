package com.kp.cms.handlers.usermanagement;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.usermanagement.UserInfoForm;
import com.kp.cms.helpers.usermanagement.UserInfoHelper;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.transactions.usermanagement.IUserInfoTransaction;
import com.kp.cms.transactionsimpl.usermanagement.UserInfoTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;

public class UserInfoHandler {

	private static volatile UserInfoHandler userInfoHandler = null;

	public static UserInfoHandler getInstance() {
		if (userInfoHandler == null) {
			userInfoHandler = new UserInfoHandler();
		}
		return userInfoHandler;
	}

	/**
	 * @return Department
	 */
	public Map<Integer, String> getDepartment() throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();

		return transaction.getDepartment();
	}

	public Map<Integer, String> getDepartmentMap() throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();

		return transaction.getDepartmentMap();
	}
	
	public Map<Integer, String> getGuestMap() throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();

		return transaction.getGuestMap();
	}
	/**
	 * @return Designation
	 */
	public Map<Integer, String> getDesignation() throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();

		return transaction.getDesignation();
	}

	/**
	 * @return roles
	 */
	public Map<Integer, String> getRoles() throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();

		return transaction.getRoles();
	}

	/**
	 * @param userInfoForm
	 * @return boolean value, true if successful else false
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public boolean addUserInfo(UserInfoForm userInfoForm, String mode)
			throws DuplicateException, Exception {

		IUserInfoTransaction transaction = new UserInfoTransactionImpl();

		if (userInfoForm.getUserName() != null
				&& !userInfoForm.getUserName().isEmpty()) {
			if (transaction.isUserNameDuplcated(userInfoForm.getUserName(),
					userInfoForm.getId())) {
				throw new DuplicateException();
			}
		}
		List<Users>usersList=transaction.getUsersById(userInfoForm.getId());
		Employee employee=null;
		if(usersList.size()!=0)
			employee= usersList.get(0).getEmployee();
		else
			employee=new Employee();
		boolean isUserAdded = false;
		if (transaction != null) {
			UserInfoHelper userInfoHelper = new UserInfoHelper();
			Users users = userInfoHelper.convertFormToBO(userInfoForm, mode,employee);
			isUserAdded = transaction.addUserInfo(users, mode);
		}
		return isUserAdded;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getTeachingStaff() throws Exception {
		Map<Integer, String> teacherMap = new LinkedHashMap<Integer, String>();
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();

		List<Users> list = transaction.getTeachingStaffs();
		Iterator<Users> itr = list.iterator();
		Users users;
		while (itr.hasNext()) {
			users = itr.next();
			if (users.getUserName() != null
					&& users.getUserName().length() != 0) {
				teacherMap.put(users.getId(), users.getUserName());
			}
		}
		return teacherMap;
	}

	/**
	 * @param dob
	 * @param firstname
	 * @param middlename
	 * @param lastName
	 * @param dep
	 * @return
	 * @throws Exception
	 */
	public List<UserInfoTO> getUserDetails(String dob, String firstname,
			String middlename, String lastName, String dep) throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();
		List<Users> userInfoList = transaction.getUsers(dob, firstname,
				middlename, lastName, dep);
		List<UserInfoTO> userInfoTOList = UserInfoHelper.getInstance()
				.copyBosToTos(userInfoList);
		return userInfoTOList;
	}

	/**
	 * copying BO to TO
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<UserInfoTO> getUserDetailsById(int id) throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();
		List<Users> userInfoList = transaction.getUsersById(id);
		List<UserInfoTO> userInfoTOList = UserInfoHelper.getInstance()
				.copyBosToTos(userInfoList);
		return userInfoTOList;
	}

	/**
	 * setting data to form for edit
	 * 
	 * @param userInfoList
	 * @param userInfoForm
	 * @throws Exception
	 */
	public void setRequiredDateToForm(List<UserInfoTO> userInfoList,
			UserInfoForm userInfoForm) throws Exception {
		Iterator<UserInfoTO> itr = userInfoList.iterator();
		UserInfoTO userInfoTO;
		while (itr.hasNext()) {
			userInfoTO = itr.next();
			if(userInfoTO.getEmployeeTO()!=null)
			{
			userInfoForm.setEmplId(userInfoTO.getEmployeeTO().getId());
			userInfoForm
					.setFirstName(userInfoTO.getEmployeeTO().getFirstName());
			userInfoForm.setMiddleName(userInfoTO.getEmployeeTO()
					.getMiddleName());
			userInfoForm.setLastName(userInfoTO.getEmployeeTO().getLastName());

			userInfoForm.setDateOfBirth(userInfoTO.getEmployeeTO().getDob());

			userInfoForm.setFatherName(userInfoTO.getEmployeeTO()
					.getFatherName());
			if (userInfoTO.getEmployeeTO().getDoj() != null) {
				userInfoForm.setDateOfJoining(CommonUtil.formatDate(userInfoTO
						.getEmployeeTO().getDoj(), "dd/MM/yyyy"));
			}
			userInfoForm.setUserName(userInfoTO.getUserName());
			userInfoForm.setBloodGroup(userInfoTO.getEmployeeTO()
					.getBloodGroup());
			userInfoForm.setEmailId(userInfoTO.getEmployeeTO().getEmail());
			if(userInfoTO.getPwd()!=null)
			userInfoForm.setOrigPassword(EncryptUtil.getInstance().decryptDES(userInfoTO.getPwd()));
			userInfoForm.setOrigEmpPhoto(userInfoTO.getEmployeeTO()
					.getEmpPhoto());

			userInfoForm.setPhone1(userInfoTO.getEmployeeTO().getPhone1());
			userInfoForm.setMobile1(userInfoTO.getEmployeeTO().getPhone2());

			if (userInfoTO.getEmployeeTO().getDepartmentTO() != null
					&& userInfoTO.getEmployeeTO().getDepartmentTO().getId() != 0) {
				userInfoForm.setDepartmentId(Integer.toString(userInfoTO
						.getEmployeeTO().getDepartmentTO().getId()));
			}
			if (userInfoTO.getEmployeeTO().getDesignationTO() != null
					&& userInfoTO.getEmployeeTO().getDesignationTO().getId() != 0) {
				userInfoForm.setDesignationId(Integer.toString(userInfoTO
						.getEmployeeTO().getDesignationTO().getId()));
			}
			if (userInfoTO.getRolesTO() != null
					&& userInfoTO.getRolesTO().getId() != 0) {
				userInfoForm.setRoleId(Integer.toString(userInfoTO.getRolesTO()
						.getId()));
			}
			if(userInfoTO.getIsTeachingStaff()!=null){
			userInfoForm.setTeachingStaff(userInfoTO.getIsTeachingStaff());
			}else{
				userInfoForm.setTeachingStaff(false);	
			}
			userInfoForm.setPermanentaddrLine1(userInfoTO.getEmployeeTO()
					.getPermanentAddressLine1());
			userInfoForm.setPermanentaddrLine2(userInfoTO.getEmployeeTO()
					.getPermanentAddressLine2());
			userInfoForm.setPermanentPinCode(userInfoTO.getEmployeeTO()
					.getPermanentAddressZip());
			if (userInfoTO.getEmployeeTO() != null
					&& userInfoTO.getEmployeeTO()
							.getCountryByPermanentAddressCountryId() != null
					&& userInfoTO.getEmployeeTO()
							.getCountryByPermanentAddressCountryId().getId() != 0) {
				userInfoForm.setPermanentCountryId(Integer.toString(userInfoTO
						.getEmployeeTO()
						.getCountryByPermanentAddressCountryId().getId()));
			}
			userInfoForm.setPermanentCity(userInfoTO.getEmployeeTO()
					.getPermanentAddressCity());
			if (userInfoTO.getEmployeeTO() != null
					&& userInfoTO.getEmployeeTO()
							.getStateByPermanentAddressStateId() != null
					&& userInfoTO.getEmployeeTO()
							.getStateByPermanentAddressStateId().getId() != 0) {
				userInfoForm.setPermanentStateId(Integer.toString(userInfoTO
						.getEmployeeTO().getStateByPermanentAddressStateId()
						.getId()));
			}
			userInfoForm.setPermanentOtherState(userInfoTO.getEmployeeTO()
					.getPermanentAddressStateOthers());

			userInfoForm.setCurrentaddrLine1(userInfoTO.getEmployeeTO()
					.getCommunicationAddressLine1());
			userInfoForm.setCurrentaddrLine2(userInfoTO.getEmployeeTO()
					.getCommunicationAddressLine2());
			userInfoForm.setCurrentPinCode(userInfoTO.getEmployeeTO()
					.getCommunicationAddressZip());

			if (userInfoTO.getEmployeeTO() != null
					&& userInfoTO.getEmployeeTO()
							.getCountryByCommunicationAddressCountryId() != null
					&& userInfoTO.getEmployeeTO()
							.getCountryByCommunicationAddressCountryId()
							.getId() != 0) {
				userInfoForm.setCurrentCountryId(Integer.toString(userInfoTO
						.getEmployeeTO()
						.getCountryByCommunicationAddressCountryId().getId()));
			}

			userInfoForm.setCurrentCity(userInfoTO.getEmployeeTO()
					.getCommunicationAddressCity());
			if (userInfoTO.getEmployeeTO() != null
					&& userInfoTO.getEmployeeTO()
							.getStateByCommunicationAddressStateId() != null
					&& userInfoTO.getEmployeeTO()
							.getStateByCommunicationAddressStateId().getId() != 0) {
				userInfoForm.setCurrentStateId(Integer.toString(userInfoTO
						.getEmployeeTO()
						.getStateByCommunicationAddressStateId().getId()));
			}

			userInfoForm.setCurrentOtherState(userInfoTO.getEmployeeTO()
					.getCommunicationAddressStateOthers());
		}
			else if(userInfoTO.getGuestFacultyTO()!=null)
			{

				userInfoForm.setEmplId(userInfoTO.getGuestFacultyTO().getId());
				userInfoForm
						.setFirstName(userInfoTO.getGuestFacultyTO().getFirstName());
				userInfoForm.setMiddleName(userInfoTO.getGuestFacultyTO()
						.getMiddleName());
				userInfoForm.setLastName(userInfoTO.getGuestFacultyTO().getLastName());

				userInfoForm.setDateOfBirth(userInfoTO.getGuestFacultyTO().getDob());

				userInfoForm.setFatherName(userInfoTO.getGuestFacultyTO()
						.getFatherName());
				if (userInfoTO.getGuestFacultyTO().getDoj() != null) {
					userInfoForm.setDateOfJoining(CommonUtil.formatDate(userInfoTO
							.getGuestFacultyTO().getDoj(), "dd/MM/yyyy"));
				}
				userInfoForm.setUserName(userInfoTO.getUserName());
				userInfoForm.setBloodGroup(userInfoTO.getGuestFacultyTO()
						.getBloodGroup());
				userInfoForm.setEmailId(userInfoTO.getGuestFacultyTO().getEmail());
				if(userInfoTO.getPwd()!=null)
				userInfoForm.setOrigPassword(EncryptUtil.getInstance().decryptDES(userInfoTO.getPwd()));
				userInfoForm.setOrigEmpPhoto(userInfoTO.getGuestFacultyTO()
						.getEmpPhoto());

				userInfoForm.setPhone1(userInfoTO.getGuestFacultyTO().getPhone1());
				userInfoForm.setMobile1(userInfoTO.getGuestFacultyTO().getPhone2());

				if (userInfoTO.getGuestFacultyTO().getDepartmentTO() != null
						&& userInfoTO.getGuestFacultyTO().getDepartmentTO().getId() != 0) {
					userInfoForm.setDepartmentId(Integer.toString(userInfoTO
							.getGuestFacultyTO().getDepartmentTO().getId()));
				}
				if (userInfoTO.getGuestFacultyTO().getDesignationTO() != null
						&& userInfoTO.getGuestFacultyTO().getDesignationTO().getId() != 0) {
					userInfoForm.setDesignationId(Integer.toString(userInfoTO
							.getGuestFacultyTO().getDesignationTO().getId()));
				}
				if (userInfoTO.getRolesTO() != null
						&& userInfoTO.getRolesTO().getId() != 0) {
					userInfoForm.setRoleId(Integer.toString(userInfoTO.getRolesTO()
							.getId()));
				}
				if(userInfoTO.getIsTeachingStaff()!=null){
				userInfoForm.setTeachingStaff(userInfoTO.getIsTeachingStaff());
				}else{
					userInfoForm.setTeachingStaff(false);	
				}
				userInfoForm.setPermanentaddrLine1(userInfoTO.getGuestFacultyTO()
						.getPermanentAddressLine1());
				userInfoForm.setPermanentaddrLine2(userInfoTO.getGuestFacultyTO()
						.getPermanentAddressLine2());
				userInfoForm.setPermanentPinCode(userInfoTO.getGuestFacultyTO()
						.getPermanentAddressZip());
				if (userInfoTO.getGuestFacultyTO() != null
						&& userInfoTO.getGuestFacultyTO()
								.getCountryByPermanentAddressCountryId() != null
						&& userInfoTO.getGuestFacultyTO()
								.getCountryByPermanentAddressCountryId().getId() != 0) {
					userInfoForm.setPermanentCountryId(Integer.toString(userInfoTO
							.getGuestFacultyTO()
							.getCountryByPermanentAddressCountryId().getId()));
				}
				userInfoForm.setPermanentCity(userInfoTO.getGuestFacultyTO()
						.getPermanentAddressCity());
				if (userInfoTO.getGuestFacultyTO() != null
						&& userInfoTO.getGuestFacultyTO()
								.getStateByPermanentAddressStateId() != null
						&& userInfoTO.getGuestFacultyTO()
								.getStateByPermanentAddressStateId().getId() != 0) {
					userInfoForm.setPermanentStateId(Integer.toString(userInfoTO
							.getGuestFacultyTO().getStateByPermanentAddressStateId()
							.getId()));
				}
				userInfoForm.setPermanentOtherState(userInfoTO.getGuestFacultyTO()
						.getPermanentAddressStateOthers());

				userInfoForm.setCurrentaddrLine1(userInfoTO.getGuestFacultyTO()
						.getCommunicationAddressLine1());
				userInfoForm.setCurrentaddrLine2(userInfoTO.getGuestFacultyTO()
						.getCommunicationAddressLine2());
				userInfoForm.setCurrentPinCode(userInfoTO.getGuestFacultyTO()
						.getCommunicationAddressZip());

				if (userInfoTO.getGuestFacultyTO() != null
						&& userInfoTO.getGuestFacultyTO()
								.getCountryByCommunicationAddressCountryId() != null
						&& userInfoTO.getGuestFacultyTO()
								.getCountryByCommunicationAddressCountryId()
								.getId() != 0) {
					userInfoForm.setCurrentCountryId(Integer.toString(userInfoTO
							.getGuestFacultyTO()
							.getCountryByCommunicationAddressCountryId().getId()));
				}

				userInfoForm.setCurrentCity(userInfoTO.getGuestFacultyTO()
						.getCommunicationAddressCity());
				if (userInfoTO.getGuestFacultyTO() != null
						&& userInfoTO.getGuestFacultyTO()
								.getStateByCommunicationAddressStateId() != null
						&& userInfoTO.getGuestFacultyTO()
								.getStateByCommunicationAddressStateId().getId() != 0) {
					userInfoForm.setCurrentStateId(Integer.toString(userInfoTO
							.getGuestFacultyTO()
							.getStateByCommunicationAddressStateId().getId()));
				}

				userInfoForm.setCurrentOtherState(userInfoTO.getGuestFacultyTO()
						.getCommunicationAddressStateOthers());
			
			}
			if(userInfoTO.getPwd()!=null)
			userInfoForm.setPassword(EncryptUtil.getInstance().decryptDES(userInfoTO.getPwd()));
			userInfoForm.setIsAddRemarks(userInfoTO.getRemarksEntry());
			userInfoForm.setIsViewRemarks(userInfoTO.getViewRemarks());
			userInfoForm.setRestrictedUser(userInfoTO.getIsRestrictedUser());
			userInfoForm.setStaffType(userInfoTO.getStaffType());
		}
	}

	/**
	 * @param id
	 * @return true if delete successful, else false
	 * @throws Exception
	 */
	public boolean deleteUserInfo(int id) throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();
		boolean result = transaction.deleteUsers(id);
		//Commented by Dilip
		/*int employeeId = transaction.getemployeeId(id);
		int guestId = transaction.getGuestId(id);
		if (employeeId > 0) {
			transaction.deleteEmployee(employeeId);
		}
		if (guestId > 0) {
			transaction.deleteGuest(guestId);
		}*/
		return result;
	}
	
	/** Creating map using employee name to display drop down
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getTeachingStaffByEmployeeName() throws Exception {
		Map<Integer, String> teacherMap = new LinkedHashMap<Integer, String>();
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();

		List<Users> list = transaction.getTeachingStaffs();
		Iterator<Users> itr = list.iterator();
		Users users;
		while (itr.hasNext()) {
			users = itr.next();
			if (users.getUserName() != null && users.getEmployee()!= null) {
				String name="";
				if(users.getEmployee().getFirstName()!=null)
					name=name+users.getEmployee().getFirstName().trim();
				if(users.getEmployee().getMiddleName()!=null)
					name=name+" "+users.getEmployee().getMiddleName().trim();
				if(users.getEmployee().getLastName()!=null)
					name=name+" "+users.getEmployee().getLastName().trim();
				
				teacherMap.put(users.getId(),name);
			}
		}
		return teacherMap;
	}
}