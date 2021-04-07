package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.to.usermanagement.UserInfoTO;

public class BulkSMStoEmployeesHelper {
	private static volatile BulkSMStoEmployeesHelper bulkSMStoEmployeesHelper = null;
	private static final Log log = LogFactory.getLog(BulkSMStoEmployeesHelper.class);
	public static BulkSMStoEmployeesHelper getInstance()
	{
		if(bulkSMStoEmployeesHelper==null)
		{
			bulkSMStoEmployeesHelper = new BulkSMStoEmployeesHelper();
		}
		return bulkSMStoEmployeesHelper;
		
	}
	public List<UserInfoTO> convertBOtoTOList(List<Users> userBolist) throws Exception{
		log.debug("call of convertBOtoTOList method in BulkSMStoEmployeesHelper.class");
		List<UserInfoTO> userList = new ArrayList<UserInfoTO>();
		
		if(userBolist!=null)
		{
			Iterator<Users> iterator = userBolist.iterator();
			while(iterator.hasNext())
			{
				Users users = iterator.next();
				UserInfoTO infoTO = new UserInfoTO();
				infoTO.setId(users.getId());
				infoTO.setPhNo(users.getEmployee().getCurrentAddressMobile1());
				infoTO.setRollid(users.getRoles().getId());
				if(users.getUserName()!=null)
				{
					infoTO.setUserName(users.getUserName());
					
				}
				String empName ="";
				if(users.getEmployee()!=null)
				{
				if(users.getEmployee().getFirstName()!=null)
				{
					empName = users.getEmployee().getFirstName();
				}
				if(users.getEmployee().getMiddleName()!=null)
				{
					empName = empName + " " + users.getEmployee().getMiddleName();
				}
				if(users.getEmployee().getLastName()!=null)
				{
					empName = empName + " " + users.getEmployee().getLastName();
				}
				}
				infoTO.setName(empName);
				userList.add(infoTO);
			}
			
		}
		log.debug("end of convertBOtoTOList method in BulkSMStoEmployeesHelper.class");
		return userList;
	}
}
