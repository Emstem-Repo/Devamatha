package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.admin.AdmApplnTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CommonUtil;

public class CancelledAdmissionsHelper {
	private static final Log log = LogFactory.getLog(CancelledAdmissionsHelper.class);
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	
	private static volatile CancelledAdmissionsHelper cancelledAdmissionsHelper;
	public static CancelledAdmissionsHelper getInstance() {
		if (cancelledAdmissionsHelper == null) {
			cancelledAdmissionsHelper = new CancelledAdmissionsHelper();
			return cancelledAdmissionsHelper;
		}
		return cancelledAdmissionsHelper;
	}

	/**
	 * 
	 * @param stuList
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> copyBosToTO(List<Student> stuList) throws Exception {
		log.debug("inside copyBosToTO");
		StudentTO studentTO;
		AdmApplnTO admApplnTO;
		Course course = new Course();
		List<StudentTO> studentToList = new ArrayList<StudentTO>();
		Student student;
		Iterator<Student> studentItr = stuList.iterator();
		
		while (studentItr.hasNext()){
			student = studentItr.next();
			studentTO = new StudentTO();
			admApplnTO = new AdmApplnTO();
			if(student.getRollNo()!= null){
				studentTO.setRollNo(student.getRollNo());
			}
			if(student.getRegisterNo()!= null){
				studentTO.setRegisterNo(student.getRegisterNo());
			}
			
			StringBuffer studentName = new StringBuffer();
			if(student.getAdmAppln().getPersonalData().getFirstName()!= null){
				studentName.append(student.getAdmAppln().getPersonalData().getFirstName());
			}
			if(student.getAdmAppln().getPersonalData().getMiddleName()!= null){
				if(studentName.toString()!= null && !studentName.toString().trim().isEmpty()){
					studentName.append(" ");
				}
				studentName.append(student.getAdmAppln().getPersonalData().getMiddleName());
			}
			if(student.getAdmAppln().getPersonalData().getLastName()!= null){
				if(studentName.toString()!= null && !studentName.toString().trim().isEmpty()){
					studentName.append(" ");
				}
				studentName.append(" " + student.getAdmAppln().getPersonalData().getLastName());
			}
			studentTO.setStudentName(studentName.toString());
			course.setName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
			course.setCode(student.getAdmAppln().getCourseBySelectedCourseId().getCode());
			studentTO.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
			admApplnTO.setApplnNo(Integer.toString(student.getAdmAppln().getApplnNo()));
			admApplnTO.setCourse(course);
			if(student.getAdmAppln().getCancelDate()!=null)
			admApplnTO.setCancelDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getCancelDate()), CancelledAdmissionsHelper.SQL_DATEFORMAT,CancelledAdmissionsHelper.FROM_DATEFORMAT));
			studentTO.setAdmApplnTO(admApplnTO);
			studentToList.add(studentTO);
		}
		log.debug("exit copyBosToTO");
		return studentToList;
	}

}
