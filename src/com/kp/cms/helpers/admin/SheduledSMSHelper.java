package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.handlers.admin.SheduledSMSHandler;
import com.kp.cms.to.admin.StudentTO;

public class SheduledSMSHelper {

	/**
	 * Singleton object of SendSmsToClassHandler
	 */
	private static volatile SheduledSMSHelper sheduledSMSHelper = null;
	private static final Log log = LogFactory.getLog(SheduledSMSHelper.class);
	private SheduledSMSHelper() {
		
	}
	/**
	 * return singleton object of SendSmsToClassHandler.
	 * @return
	 */
	public static SheduledSMSHelper getInstance() {
		if (sheduledSMSHelper == null) {
			sheduledSMSHelper = new SheduledSMSHelper();
		}
		return sheduledSMSHelper;
	}
	public List<StudentTO> convertBOToTO(List<Student> students)throws Exception {
		// TODO Auto-generated method stub
		if(students!=null){
			List<StudentTO> toList = new ArrayList<StudentTO>();
			Iterator<Student> itr = students.iterator();
			while (itr.hasNext()) {
				Student student = (Student) itr.next();
				StudentTO to = new StudentTO();
				to.setId(student.getId());
				PersonalData pd = student.getAdmAppln().getPersonalData();
				if(pd.getParentMob1()!=null){
					to.setMobileNo1(pd.getParentMob1());
				}
				if(pd.getParentMob2()!=null){
					to.setMobileNo2(pd.getParentMob2());
				}
				String name="";
				if(pd.getFirstName()!=null){
					name=name+pd.getFirstName();
				}
				if(pd.getMiddleName()!=null){
					name=name+pd.getMiddleName();
				}
				if(pd.getLastName()!=null){
					name=name+pd.getLastName();
				}
				to.setStudentName(name);
				to.setRegisterNo(student.getRegisterNo());
				to.setRollNo(student.getRollNo());
				toList.add(to);
			}
			return toList;
		}
		return null;
	}
}
