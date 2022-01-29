package com.kp.cms.helpers.usermanagement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.MenusTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.utilities.CommonUtil;

public class UserReportHelper {
	private static volatile UserReportHelper userReportHelper = null;

	public static UserReportHelper getInstance() {
		if (userReportHelper == null) {
			userReportHelper = new UserReportHelper();
		}
		return userReportHelper;
	}
	/**
	 * 
	 * @param userInfoList
	 * @return
	 */
	public List<UserInfoTO> copyBosToTos(List<Users> userInfoList) {
		List<UserInfoTO> userInfoToList = new ArrayList<UserInfoTO>();
//		Iterator<Users> i = userInfoList.iterator();
		Iterator i = userInfoList.iterator();
		Users users;
		UserInfoTO userInfoTO;
		EmployeeTO employeeTO;
		RolesTO rolesTO;
		SingleFieldMasterTO singleFieldMasterTO;  //department
		SingleFieldMasterTO designationTO;
		CountryTO permAddCountryTo;
		CountryTO commAddCountryTo;
		StateTO permAddStateTo;
		StateTO curAddStateTo;
		while(i.hasNext()) {
			userInfoTO = new UserInfoTO();
			employeeTO = new EmployeeTO();
			rolesTO = new RolesTO();
			singleFieldMasterTO = new SingleFieldMasterTO();
//			users = (Users)i.next();
			Object[] it = (Object[]) i.next();
			users = (Users) it[0];
			String createdBy = (String) it[1];
			String modifiedBy = (String) it[2];
			

			employeeTO.setId(users.getEmployee().getId());
			employeeTO.setFirstName(users.getEmployee().getFirstName());
			employeeTO.setMiddleName(users.getEmployee().getMiddleName());
			employeeTO.setLastName(users.getEmployee().getLastName());
			employeeTO.setName(users.getEmployee().getFirstName() + " " + users.getEmployee().getMiddleName() + " " + users.getEmployee().getLastName());
			employeeTO.setDob(CommonUtil.formatDate(users.getEmployee().getDob(), "dd/MM/yyyy"));
			employeeTO.setName(users.getEmployee().getFirstName() + " " + users.getEmployee().getMiddleName() + " " + users.getEmployee().getLastName());
			employeeTO.setFatherName(users.getEmployee().getFatherName());
			employeeTO.setDateOfJoining(CommonUtil.formatDate(users.getEmployee().getDoj(), "dd/MM/yyyy"));
			employeeTO.setBloodGroup(users.getEmployee().getBloodGroup());
		//	employeeTO.setPhone1(users.getEmployee().getPhone1());
		//	employeeTO.setPhone2(users.getEmployee().getPhone2());
			employeeTO.setPermanentAddressLine1(users.getEmployee().getPermanentAddressLine1());
			employeeTO.setPermanentAddressLine2(users.getEmployee().getPermanentAddressLine2());
			employeeTO.setPermanentAddressCity(users.getEmployee().getPermanentAddressCity());
			employeeTO.setPermanentAddressZip(users.getEmployee().getPermanentAddressZip());
			employeeTO.setPermanentAddressStateOthers(users.getEmployee().getCommunicationAddressStateOthers());
			
			if(users.getEmployee()!= null && users.getEmployee().getCountryByCommunicationAddressCountryId()!= null && users.getEmployee().getCountryByCommunicationAddressCountryId().getId()!= 0){
				permAddCountryTo = new CountryTO();
				permAddCountryTo.setId(users.getEmployee().getCountryByCommunicationAddressCountryId().getId());
				employeeTO.setCountryByPermanentAddressCountryId(permAddCountryTo);
			}
			if(users.getEmployee()!= null && users.getEmployee().getCountryByCommunicationAddressCountryId()!= null && users.getEmployee().getCountryByCommunicationAddressCountryId().getId()!= 0){
				commAddCountryTo = new CountryTO();
				commAddCountryTo.setId(users.getEmployee().getCountryByCommunicationAddressCountryId().getId());
				employeeTO.setCountryByCommunicationAddressCountryId(commAddCountryTo);
			}
			employeeTO.setCommunicationAddressLine1(users.getEmployee().getCommunicationAddressLine1());
			employeeTO.setCommunicationAddressLine2(users.getEmployee().getCommunicationAddressLine2());
			employeeTO.setCommunicationAddressZip(users.getEmployee().getCommunicationAddressZip());
			employeeTO.setCommunicationAddressCity(users.getEmployee().getCommunicationAddressCity());
			employeeTO.setCommunicationAddressStateOthers(users.getEmployee().getCommunicationAddressStateOthers());
			
			employeeTO.setEmail(users.getEmployee().getEmail());
			if(users.getEmployee()!=null && users.getEmployee().getStateByPermanentAddressStateId()!=null 
					&& users.getEmployee().getStateByPermanentAddressStateId().getId()!=0){
				permAddStateTo = new StateTO();
				permAddStateTo.setId(users.getEmployee().getStateByPermanentAddressStateId().getId());
				employeeTO.setStateByPermanentAddressStateId(permAddStateTo);
			}
			
			if(users.getEmployee() != null && users.getEmployee().getStateByCommunicationAddressStateId() != null && 
					users.getEmployee().getStateByCommunicationAddressStateId().getId() != 0){
				curAddStateTo = new StateTO();
				curAddStateTo.setId(users.getEmployee().getStateByCommunicationAddressStateId().getId());
				employeeTO.setStateByCommunicationAddressStateId(curAddStateTo);
			}
			singleFieldMasterTO.setId(users.getEmployee().getDepartment().getId());
			singleFieldMasterTO.setName(users.getEmployee().getDepartment().getName());
			employeeTO.setDepartmentTO(singleFieldMasterTO);
			designationTO = new SingleFieldMasterTO();
			designationTO.setId(users.getEmployee().getDesignation().getId());
			designationTO.setName(users.getEmployee().getDesignation().getName());
			employeeTO.setDesignationTO(designationTO);
			userInfoTO.setEmployeeTO(employeeTO);
			rolesTO.setId(users.getRoles().getId());
			rolesTO.setName(users.getRoles().getName());
			userInfoTO.setUserName(users.getUserName());
			userInfoTO.setId(users.getId());
			userInfoTO.setIsTeachingStaff(users.getIsTeachingStaff());
			userInfoTO.setPwd(users.getPwd());
			if(users.getLastLoggedIn() != null){
				userInfoTO.setLastLoggedIn(CommonUtil.formatDate(users.getLastLoggedIn(), "dd/MM/yyyy"));
			}
			userInfoTO.setModifiedBy(modifiedBy);
			userInfoTO.setCreatedBy(createdBy);
			if(users.getCreatedDate()!= null){
				userInfoTO.setCreateddate(CommonUtil.formatDate(users.getCreatedDate(), "dd/MM/yyyy"));
			}
			if(users.getLastModifiedDate()!= null){
				userInfoTO.setModifieddate(CommonUtil.formatDate(users.getLastModifiedDate(),"dd/MM/yyyy"));
			}
			
			 Set<AccessPrivileges> privilageSet = users.getRoles().getAccessPrivilegeses(); 
			Iterator<AccessPrivileges> itr = privilageSet.iterator();
			List<AssignPrivilegeTO> accessPrivilegesList = new ArrayList<AssignPrivilegeTO>();
			 
			while (itr.hasNext()){
				AccessPrivileges accessPrivileges = itr.next();
				AssignPrivilegeTO assignPrivilegeTO = new AssignPrivilegeTO();
				MenusTO menusTO = new MenusTO();
				menusTO.setName(accessPrivileges.getMenus().getDisplayName());
				assignPrivilegeTO.setMenusTO(menusTO);
				accessPrivilegesList.add(assignPrivilegeTO);
			}
			rolesTO.setAssignPrivilegeTOList(accessPrivilegesList);
			userInfoTO.setRolesTO(rolesTO);
			userInfoToList.add(userInfoTO);
		}
		return userInfoToList;   
	}
	/**
	 * setting TO to display in the UI while clicking on the role link
	 * @param accprivilegeList
	 * @return
	 */
	
	public List<AssignPrivilegeTO> copyAccessPrivilegeBosToTo(List<AccessPrivileges> accprivilegeList) {
		List<AssignPrivilegeTO> assignPrivilegeTOList = new ArrayList<AssignPrivilegeTO>();
		Iterator<AccessPrivileges> it = accprivilegeList.iterator();
		while (it.hasNext()){
			AssignPrivilegeTO assignPrivilegeTO = new AssignPrivilegeTO();
			MenusTO menusTO = new MenusTO();
			AccessPrivileges accessPrivileges = it.next();
			menusTO.setName(accessPrivileges.getMenus().getDisplayName());
			assignPrivilegeTO.setMenusTO(menusTO);
			assignPrivilegeTOList.add(assignPrivilegeTO);
		}
		return assignPrivilegeTOList;
	}
}
