package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.employee.EmployeeInfoForm;
import com.kp.cms.helpers.employee.EmployeeInfoHelper;
import com.kp.cms.helpers.usermanagement.UserInfoHelper;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.admin.EmployeeInformactionTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmployeeKeyValueTO;
import com.kp.cms.to.employee.EmployeeStreamTO;
import com.kp.cms.transactions.employee.IEmployeeInfoTransaction;
import com.kp.cms.transactions.usermanagement.IUserInfoTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.UserInfoTransactionImpl;
import com.sun.accessibility.internal.resources.accessibility;

/**
 * HANDLER CLASS FOR EMPLOYEE INFORMATION
 * 
 */
public class EmployeeInfoHandler {
	private static final Log log = LogFactory.getLog(EmployeeInfoHandler.class);

	public static volatile EmployeeInfoHandler self = null;

	public static EmployeeInfoHandler getInstance() {
		if (self == null) {
			self = new EmployeeInfoHandler();
		}
		return self;
	}

	private EmployeeInfoHandler() {

	}

	/**
	 * fetches the employee details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public EmployeeTO getEmployeeDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		EmployeeTO employeeTo = null;
		Employee employee = txn.getEmployeeDetails(infoForm.getEmployeeDetail()
				.getCode(), infoForm.getEmployeeDetail().getLastName(),
				infoForm.getEmployeeDetail().getFirstName(), infoForm
						.getEmployeeDetail().getMiddleName(), infoForm
						.getEmployeeDetail().getNickName());
		if (employee != null) {
		//	employeeTo = EmployeeInfoHelper.getInstance()
					//.convertEmployeeBoToTO(employeeTo, employee, infoForm);
		}
		return employeeTo;
	}

	//public List<EmpAllowanceTO> getAllowanceMasters() throws Exception {
		// IEmployeeInfoTransaction txn= new EmployeeInfoTransactionImpl();
		// EmpAllowance
		// objBo=EmployeeInfoHelper.getInstance().getEmployeeJobBO();
	//	return EmployeeInfoHelper.getInstance().getAllowanceMasters();
	//}

	/**
	 * saves personal details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean savePersonalDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		if (infoForm.getEmployeeDetail().getFingerPrintId() != null
				&& !infoForm.getEmployeeDetail().getFingerPrintId() .isEmpty()) {
			if (txn.isFingerDuplicated(infoForm.getEmployeeDetail().getFingerPrintId(),infoForm.getEmployeeDetail().getId())) {
				throw new DuplicateException();
			}
		}
		
		
		Employee employee = null;
	//	employee = EmployeeInfoHelper.getInstance()
		//		.getEmployeePersonalBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	public Integer saveUserDetails(EmployeeInfoForm objForm) throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		// if (objForm.getEmployeeDetail().getFirstName() != null
		// && !objForm.getEmployeeDetail().getFirstName().isEmpty()) {
		// if (transaction.isUserNameDuplcated(objForm.getEmployeeDetail()
		// .getFirstName(), 0)) {
		// throw new DuplicateException();
		// }
		// }
		if (txn.isCodeDuplicated(objForm.getEmployeeDetail().getCode(),0)) {
			objForm.setDuplicateIn("code");
			throw new DuplicateException();
		}
		Integer id = null;
		// boolean isUserAdded = false;
		if (transaction != null) {
		//	Users users = EmployeeInfoHelper.getInstance().convertFormToUserBO(
		//			objForm);
		//	id = txn.addUserInfo(users);
			// isUserAdded = transaction.addUserInfo(users, "add");

		}
		return id;

	}

	/**
	 * saves Contact details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveContactDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance()
				.getEmployeeContactBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * saves Emergency details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveEmergencyDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance()
				.getEmployeeEmergencyBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * saves Skill details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveSkillDetails(EmployeeInfoForm infoForm) throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance()
				.getEmployeeSkillBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * saves Immigration details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveImmigrationDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance()
				.getEmployeeImmigrationBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * saves reporting details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveReportingDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance()
				.getEmployeeReportingBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * saves dependent details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveDependentDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance()
				.getEmployeeDependentBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * saves Education details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveEducationDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance()
				.getEmployeeEducationBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * saves Experience details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveExperienceDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance()
				.getEmployeeExperienceBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * saves Language details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveLanguageDetails(EmployeeInfoForm infoForm)
			throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance()
				.getEmployeeLanguageBO(infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * saves job details
	 * 
	 * @param infoForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveJobDetails(EmployeeInfoForm infoForm) throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		Employee employee = EmployeeInfoHelper.getInstance().getEmployeeJobBO(
				infoForm);
		boolean result = txn.saveEmployeePersonalInfo(employee);
		return result;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTypeTO> getLeaveTypes() throws Exception {
		return EmployeeInfoHelper.getInstance().getLeaveTypes();
	}

	/**
	 * @param infoForm
	 * @throws Exception
	 */
	public void addAchievements(EmployeeInfoForm infoForm) throws Exception {
		if (infoForm.getNewAchievementTOs() != null
				&& !infoForm.getNewAchievementTOs().isEmpty()) {
			List<EmpAcheivementTO> oldachievements = infoForm
					.getAchievementTOs();
			if (oldachievements != null) {
				Iterator<EmpAcheivementTO> achItr = infoForm
						.getNewAchievementTOs().iterator();
				while (achItr.hasNext()) {
					EmpAcheivementTO acheivementTO = (EmpAcheivementTO) achItr
							.next();
					if (acheivementTO.getAcheivementName() != null
							&& !StringUtils.isEmpty(acheivementTO
									.getAcheivementName()))
						oldachievements.add(acheivementTO);

				}

			} else {
				List<EmpAcheivementTO> achievements = new ArrayList<EmpAcheivementTO>();
				Iterator<EmpAcheivementTO> achItr = infoForm
						.getNewAchievementTOs().iterator();
				while (achItr.hasNext()) {
					EmpAcheivementTO acheivementTO = (EmpAcheivementTO) achItr
							.next();
					if (acheivementTO.getAcheivementName() != null
							&& !StringUtils.isEmpty(acheivementTO
									.getAcheivementName()))
						achievements.add(acheivementTO);

				}

				infoForm.setAchievementTOs(achievements);
			}
		}

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeTO> getReportingTos() throws Exception {
		List<EmployeeTO> reportingtos = new ArrayList<EmployeeTO>();
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		List<Employee> allEmployees = txn.getAllEmployees();
		EmployeeInfoHelper.getInstance().convertForReportingTos(reportingtos,
				allEmployees);
		return reportingtos;
	}

	public ArrayList<EmployeeInformactionTO> getEmployeeInfoDetails(
			String code, String lastName, String firstName, String middleName,
			String nickName) throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		ArrayList<EmployeeInformactionTO> list = new ArrayList<EmployeeInformactionTO>();

		List<Object[]> employeeInfo = txn.getEmployeeInfoDetails(code,
				lastName, firstName, middleName, nickName);
		if (employeeInfo != null) {
			list = EmployeeInfoHelper.getInstance().convertBoToTO(employeeInfo);
		}
		return list;
	}

	public List<EmployeeStreamTO> getEmployeeStreamMasters() throws Exception {
		return EmployeeInfoHelper.getInstance().getEmployeeStreamMasters();
	}

	public List<EmployeeKeyValueTO> getDepartmentMasters() throws Exception {
		return EmployeeInfoHelper.getInstance().getDepartmentMasters();
	}

	public List<EmployeeKeyValueTO> getDesignationMasters() throws Exception {
		return EmployeeInfoHelper.getInstance().getDesignationMasters();
	}

	public List<EmployeeKeyValueTO> getWorkLocationMasters() throws Exception {
		return EmployeeInfoHelper.getInstance().getWorkLocationMasters();
	}

	public List<EmployeeKeyValueTO> getEmployeeType() throws Exception {
		return EmployeeInfoHelper.getInstance().getEmployeeType();
	}

	public List<EmployeeKeyValueTO> getlistRoles() throws Exception {
		return EmployeeInfoHelper.getInstance().getlistRoles();
	}

	public boolean updateUserDetails(EmployeeInfoForm objForm) throws Exception {
		IUserInfoTransaction transaction = new UserInfoTransactionImpl();
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		if (objForm.getEmployeeDetail().getUserName() != null
				&& !objForm.getEmployeeDetail().getUserName().isEmpty()) {
			if (txn.isUserNameDuplicated(objForm.getEmployeeDetail().getUserName(),objForm.getEmployeeDetail().getId())) {
				objForm.setDuplicateIn("userName");
				throw new DuplicateException();
			}
		}
		
		if (objForm.getEmployeeDetail().getNewCode() != null
				&& !objForm.getEmployeeDetail().getNewCode().isEmpty()) {
			if (txn.isCodeDuplicated(objForm.getEmployeeDetail().getNewCode(),objForm.getEmployeeDetail().getId())) {
				objForm.setDuplicateIn("code");
				throw new DuplicateException();
			}
		}
		
		int userId = 0;
		if (objForm.getEmployeeDetail().getId() != 0) {
			userId = txn.getUserId(objForm.getEmployeeDetail().getId());
		}

		boolean isUserAdded = false;
		if (objForm.getEmployeeDetail().getId() != 0 && userId != 0) {
			objForm.getEmployeeDetail().setUserId(userId);
			Users users = EmployeeInfoHelper.getInstance().convertFormToUserBO(
					objForm.getEmployeeDetail().getId(), userId,
					objForm.getEmployeeDetail().getUserName(),
					objForm.getEmployeeDetail().getPassword(),
					objForm.getEmployeeDetail().getRoleId(),
					objForm.getEmployeeDetail().getIsAddRemarks(),
					objForm.getEmployeeDetail().getIsViewRemarks(),
					objForm.getEmployeeDetail().getNewFirstName(),
					objForm.getEmployeeDetail().getNewLastName(),
					objForm.getEmployeeDetail().getNewMiddleName(),
					objForm.getEmployeeDetail().getNewNickName(),
					objForm.getEmployeeDetail().getIsTeachingStaff(),
					objForm.getEmployeeDetail().getNewCode());
			isUserAdded = transaction.addUserInfo(users, "update");

		}

		return isUserAdded;
	}

	public EmployeeTO getEmployeeDetailsByEmployeeId(Integer employeeId,
			EmployeeInfoForm objForm) throws Exception {
		IEmployeeInfoTransaction txn = new EmployeeInfoTransactionImpl();
		EmployeeTO employeeTo = null;
		Users user = txn.getUserDetailsByEmployeeId(employeeId);
		if (user != null) {
			employeeTo = EmployeeInfoHelper.getInstance()
					.convertUserBoToEmployeeTO(employeeTo, user, objForm);
		}
		return employeeTo;
	}

}
