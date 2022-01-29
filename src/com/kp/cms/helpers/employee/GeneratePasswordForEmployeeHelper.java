package com.kp.cms.helpers.employee;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.employee.GeneratePasswordForEmployeeForm;

public class GeneratePasswordForEmployeeHelper {

	/**
	 * Singleton object of GeneratePasswordForEmployeeHelper
	 */
	private static volatile GeneratePasswordForEmployeeHelper generatePasswordForEmployeeHelper = null;
	private static final Log log = LogFactory.getLog(GeneratePasswordForEmployeeHelper.class);
	private GeneratePasswordForEmployeeHelper() {
		
	}
	/**
	 * return singleton object of GeneratePasswordForEmployeeHelper.
	 * @return
	 */
	public static GeneratePasswordForEmployeeHelper getInstance() {
		if (generatePasswordForEmployeeHelper == null) {
			generatePasswordForEmployeeHelper = new GeneratePasswordForEmployeeHelper();
		}
		return generatePasswordForEmployeeHelper;
	}
	/**
	 * returns query for the generate criteria selected in the input
	 * @param gnForm
	 * @return
	 * @throws Exception
	 */
	public String getQuery(GeneratePasswordForEmployeeForm gnForm) throws Exception {
		String isTeachingStaff=gnForm.getEmployeeType();
		if(isTeachingStaff!=null && !isTeachingStaff.isEmpty() && isTeachingStaff.equalsIgnoreCase("Teaching")){
			isTeachingStaff="true";
		} else isTeachingStaff="false";
		String query="from Users u where u.employee.id is not null and u.employee.isActive=1 " +
				"and u.isActive=1 and  u.userName is not null and u.employee.active=1 and u.employee.teachingStaff="+isTeachingStaff;
		return query;
	}
}
