package com.kp.cms.handlers.admission;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.forms.admission.CourseChangeForm;
import com.kp.cms.transactions.admission.ICourseChangeTransaction;
import com.kp.cms.transactionsimpl.admission.CourseChangeTransactionImpl;

public class CourseChangeHandler {
	private static final Log log = LogFactory.getLog(CourseChangeHandler.class);
	public static volatile CourseChangeHandler courseChangeHandler = null;

	public static CourseChangeHandler getInstance() {
		if (courseChangeHandler == null) {
			courseChangeHandler = new CourseChangeHandler();
			return courseChangeHandler;
		}
		return courseChangeHandler;
	}	
	
	/**
	 * 
	 * @param courseChangeForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateCourse(CourseChangeForm courseChangeForm) throws Exception {
		log.debug("inside updateCourse");
		ICourseChangeTransaction iCourseChangeTransaction = CourseChangeTransactionImpl.getInstance();
		boolean isAdded = false;
		
		isAdded = iCourseChangeTransaction.updateSelectedCourse(courseChangeForm.getCourse(), courseChangeForm.getApplicationNumber(), courseChangeForm.getApplicationYear(), courseChangeForm.getUserId()); 
		log.debug("leaving updateCourse");
		return isAdded;
	}
	/**
	 * 
	 * @param courseChangeForm
	 * @throws Exception
	 */
	public void assignCourseDetails(CourseChangeForm courseChangeForm) throws Exception {
		log.debug("inside assignCourseDetails");
		ICourseChangeTransaction iCourseChangeTransaction = CourseChangeTransactionImpl.getInstance();
		
		AdmAppln admAppln = iCourseChangeTransaction.getAdmAppln(Integer.parseInt(courseChangeForm.getApplicationNumber()), courseChangeForm.getApplicationYear());
		courseChangeForm.setFromCourse(admAppln.getCourseBySelectedCourseId().getName() + "(" + admAppln.getCourseBySelectedCourseId().getCode() + ")");
		courseChangeForm.setProgramTypeId(Integer.toString(admAppln.getCourseBySelectedCourseId().getProgram().getProgramType().getId()));
		StringBuffer name = new StringBuffer();
		name.append(admAppln.getPersonalData().getFirstName());
		if(admAppln.getPersonalData().getMiddleName()!= null && !admAppln.getPersonalData().getMiddleName().trim().isEmpty()){
			name.append(" " + admAppln.getPersonalData().getMiddleName());
		}
		if(admAppln.getPersonalData().getLastName()!= null && !admAppln.getPersonalData().getLastName().trim().isEmpty()){
			name.append(" " + admAppln.getPersonalData().getLastName());
		}
		courseChangeForm.setNameOfStudent(name.toString());
		courseChangeForm.setAppliedCourse(admAppln.getCourse().getName());
		courseChangeForm.setModifiedBy(admAppln.getModifiedBy());
		//courseChangeForm.setLastModifiedDate(new Date());
		log.debug("leaving assignCourseDetails");
	}
	/**
	 * 
	 * @param courseChangeForm
	 * @return
	 * @throws Exception
	 */
	public List<Course> getCourseByProgramType(CourseChangeForm courseChangeForm) throws Exception {
		log.debug("inside getCourseByProgramType");
		ICourseChangeTransaction iCourseChangeTransaction = CourseChangeTransactionImpl.getInstance();
		
		return iCourseChangeTransaction.getCoursesByProgramType(Integer.parseInt(courseChangeForm.getProgramTypeId()));
	}		
}
