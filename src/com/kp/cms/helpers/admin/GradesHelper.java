package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Grade;
import com.kp.cms.forms.admin.GradesForm;
import com.kp.cms.to.admin.GradeTO;



public class GradesHelper {
	private static final Log log = LogFactory.getLog(GradesHelper.class);
	public static volatile GradesHelper gradesHelper = null;
	public static GradesHelper getInstance() {
	      if(gradesHelper == null) {
	    	  gradesHelper = new GradesHelper();
	    	  return gradesHelper;
	      }
	      return gradesHelper;
	}

	/**
	 * 
	 * @param gradesList
	 *            this will copy the Grade BO to TO
	 * @return gradesList having gradeTO objects.
	 */
	public List<GradeTO> copyGradeBosToTos(List<Grade> gradeList) {
		List<GradeTO> gradeTOList = new ArrayList<GradeTO>();
		Iterator<Grade> iterator = gradeList.iterator();
		Grade grade;
		GradeTO gradeTO;
		while (iterator.hasNext()) {
			gradeTO = new GradeTO();
			grade = (Grade) iterator.next();
			gradeTO.setId(grade.getId());
			gradeTO.setGrade(grade.getGrade());
			gradeTO.setMark(grade.getMarks());
			gradeTOList.add(gradeTO);
		}
		log.debug("leaving copyGradeBosToTos");
		return gradeTOList;
	}
	/**
	 * 
	 * @param gradeForm
	 *        Creates Grade BO from gradeForm.
	 * @return Grade BO Object.
	 * @throws Exception may throw Number format Exception while copying.
	 */
	public Grade copyDataFromFormToBO(GradesForm gradesForm) throws Exception{
		Grade grade = new Grade();
		if(gradesForm.getId() != 0) {
			grade.setId(gradesForm.getId());
		}
		grade.setGrade(gradesForm.getGrade().trim());
		if(gradesForm.getMark()!=null && !gradesForm.getMark().equals("")){
			grade.setMarks(Integer.parseInt(gradesForm.getMark()));
		}
		grade.setIsActive(true);
		grade.setCreatedBy(gradesForm.getUserId());
		grade.setModifiedBy(gradesForm.getUserId());
		grade.setCreatedDate(new Date());
		grade.setLastModifiedDate(new Date());
		log.debug("leaving copyDataFromFormToBO");
		return grade;
		}
}
