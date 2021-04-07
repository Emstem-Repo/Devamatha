package com.kp.cms.handlers.usermanagement;

import java.util.List;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.helpers.usermanagement.UserReportHelper;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.transactions.usermanagement.IUserReportTransaction;
import com.kp.cms.transactionsimpl.usermanagement.UserReportTransactionImpl;

public class UserReportHandler {
	private static volatile UserReportHandler userReportHandler = null;

	public static UserReportHandler getInstance() {
		if (userReportHandler == null) {
			userReportHandler = new UserReportHandler();
		}
		return userReportHandler;
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
	public List<UserInfoTO> getUserDetails(String dob, String firstname, String middlename, String lastName, String dep) throws Exception {
		IUserReportTransaction transaction = new UserReportTransactionImpl();
		List<Users> userInfoList = transaction.getUsers(dob, firstname, middlename, lastName, dep);
		List<UserInfoTO> userInfoTOList = UserReportHelper.getInstance().copyBosToTos(userInfoList) ;
		return userInfoTOList;
	}
	
	/**
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<AssignPrivilegeTO> getUserPrivilegesByRole(int roleId) throws Exception {
		List<AccessPrivileges> accPrivilegeList = AssignPrivilegeHandler.getInstance().getPrivilegebyRole(roleId);
		List<AssignPrivilegeTO> assignPrivilegeTOList = UserReportHelper.getInstance().copyAccessPrivilegeBosToTo(accPrivilegeList) ;
		return assignPrivilegeTOList;
		
	}

}
