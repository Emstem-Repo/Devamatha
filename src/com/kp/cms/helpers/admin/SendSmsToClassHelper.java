package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.SendSmsToClassForm;
import com.kp.cms.to.admin.StudentTO;

public class SendSmsToClassHelper {
	/**
	 * Singleton object of SendSmsToClassHelper
	 */
	private static volatile SendSmsToClassHelper sendSmsToClassHelper = null;
	private static final Log log = LogFactory.getLog(SendSmsToClassHelper.class);
	private SendSmsToClassHelper() {
		
	}
	/**
	 * return singleton object of SendSmsToClassHelper.
	 * @return
	 */
	public static SendSmsToClassHelper getInstance() {
		if (sendSmsToClassHelper == null) {
			sendSmsToClassHelper = new SendSmsToClassHelper();
		}
		return sendSmsToClassHelper;
	}
	/**
	 * @param sendSmsToClassForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentForClassQuery(SendSmsToClassForm sendSmsToClassForm) throws Exception{
		// TODO Auto-generated method stub
		log.info("Entered SendSmsToClassHelper-getStudentForClassQuery");
		
		String query="from Student s where s.classSchemewise.id in ("+sendSmsToClassForm.getClassId()
		+") and isAdmitted = 1 and s.isActive = 1 and s.admAppln.isCancelled=0";
		
		log.info("Exists SendSmsToClassHelper-getStudentForClassQuery");
		return query;
	}
	/**
	 * @param studentList
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBoListToToList(List<Student> studentList) throws Exception {
		List<StudentTO> stuList=new ArrayList<StudentTO>();
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<Student> itr=studentList.iterator();
			while (itr.hasNext()) {
				Student student = (Student) itr.next();
				StudentTO studentTO=new StudentTO();
				PersonalData data=student.getAdmAppln().getPersonalData();
				if(data.getParentMob1()!=null){
					studentTO.setMobileNo1(data.getParentMob1());
				}
				if(data.getParentMob2()!=null){
					studentTO.setMobileNo2(data.getParentMob2());
				}
				String name="";
				if(data.getFirstName()!=null){
					name=name+data.getFirstName();
				}
				if(data.getMiddleName()!=null){
					name=name+data.getMiddleName();
				}
				if(data.getLastName()!=null){
					name=name+data.getLastName();
				}
				studentTO.setStudentName(name);
				studentTO.setRegisterNo(student.getRegisterNo());
				studentTO.setRollNo(student.getRollNo());
				stuList.add(studentTO);
			}
		}
		return stuList;
	}
	public StudentTO convertStudentBOToStudentTO(Student studentBO)throws Exception {
		// TODO Auto-generated method stub
		if(studentBO!=null){
			String name = null;
			StudentTO to = new StudentTO();
			if(studentBO.getAdmAppln().getPersonalData().getFirstName()!=null && studentBO.getAdmAppln().getPersonalData().getFirstName().length()>0){
				name = studentBO.getAdmAppln().getPersonalData().getFirstName();
			}
			if(studentBO.getAdmAppln().getPersonalData().getMiddleName()!=null && studentBO.getAdmAppln().getPersonalData().getMiddleName().length()>0){
				name = name+" "+ studentBO.getAdmAppln().getPersonalData().getMiddleName();
			}
			if(studentBO.getAdmAppln().getPersonalData().getLastName()!=null && studentBO.getAdmAppln().getPersonalData().getLastName().length()>0){
				name = name+" "+studentBO.getAdmAppln().getPersonalData().getLastName();
			}
			if(name!=null){
				to.setStudentName(name);
			}
			if(studentBO.getRegisterNo()!=null){
				to.setRegisterNo(studentBO.getRegisterNo());
			}
			to.setApplicationNo(studentBO.getAdmAppln().getApplnNo());
			if(studentBO.getAdmAppln().getCourse().getName()!=null){
				to.setCourseName(studentBO.getAdmAppln().getCourse().getName());
			}
			if(studentBO.getAdmAppln().getClass().getName()!=null){
				to.setClassName(studentBO.getClassSchemewise().getClasses().getName());
			}
			if(studentBO.getAdmAppln().getPersonalData().getParentMob1()!=null){
				to.setMobileNo1(studentBO.getAdmAppln().getPersonalData().getParentMob1());
			}
			if(studentBO.getAdmAppln().getPersonalData().getParentMob2()!=null){
				to.setMobileNo2(studentBO.getAdmAppln().getPersonalData().getParentMob2());
			}
			return to;
		}
		return null;
	}
}
