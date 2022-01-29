package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.forms.admin.DetailedSubjectsForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;

public class DetailedSubjectsHelper {

	private static volatile DetailedSubjectsHelper detailedSubjectsHelper= null;
	private static final Log log = LogFactory.getLog(DetailedSubjectsHelper.class);
	
	/**
	 * 
	 * @return the single instance of this helper class.
	 */
	public static DetailedSubjectsHelper getInstance() {
	      if(detailedSubjectsHelper == null) {
	    	  detailedSubjectsHelper = new DetailedSubjectsHelper();
	    	  return detailedSubjectsHelper;
	      }
	      return detailedSubjectsHelper;
	}
	
	/**
	 * This method will copies data from BO's To's
	 * @param detailedSubjects
	 * @return
	 * @throws Exception
	 */
	public List<DetailedSubjectsTO> copyBosToTos(List<DetailedSubjects> detailedSubjects) throws Exception {
		log.debug("Hepler : Entering the copyBosToTos");
		List<DetailedSubjectsTO> detailedSubjectsList = new ArrayList<DetailedSubjectsTO>();
		Iterator<DetailedSubjects> iterator = detailedSubjects.iterator();
		CourseTO courseTo;
		DetailedSubjects subjects ;
		DetailedSubjectsTO detailedSubjectsTO;
		ProgramTypeTO programTypeTO;
		ProgramTO programTO;
		while(iterator.hasNext()) {
			subjects = iterator.next();
			detailedSubjectsTO = new DetailedSubjectsTO();
			courseTo = new CourseTO();
			programTypeTO = new ProgramTypeTO();
			programTO = new ProgramTO();
			
			programTypeTO.setProgramTypeId(subjects.getCourse().getProgram().getProgramType().getId());
			programTypeTO.setProgramTypeName(subjects.getCourse().getProgram().getProgramType().getName());
			
			programTO.setProgramTypeTo(programTypeTO);
			programTO.setId(subjects.getCourse().getProgram().getId());
			programTO.setName(subjects.getCourse().getProgram().getName());
			
			courseTo.setProgramTo(programTO);
			courseTo.setId(subjects.getCourse().getId());
			courseTo.setName(subjects.getCourse().getName());
			courseTo.setCode(subjects.getCourse().getCode());
			detailedSubjectsTO.setCourseTo(courseTo);
			
			detailedSubjectsTO.setId(subjects.getId());
			detailedSubjectsTO.setSubjectName(subjects.getSubjectName());
			
			detailedSubjectsList.add(detailedSubjectsTO);
		}
		log.debug("Hepler : Leaving the copyBosToTos");
	return detailedSubjectsList;	
	}
	
	/**
	 * This method copies data from form to To
	 * @param detailedSubjectForm
	 * @return
	 * @throws Exception
	 */
	public DetailedSubjects copyFormDataToBo(DetailedSubjectsForm detailedSubjectForm) throws Exception {
		DetailedSubjects detailedSubjects = new DetailedSubjects();
		
		if(detailedSubjectForm.getId().equals("0") || detailedSubjectForm.getId().length() == 0){
			detailedSubjects.setId(0);
		} else {
			detailedSubjects.setId(Integer.parseInt(detailedSubjectForm.getId()));
		}
		
		Course course = new Course();
		course.setId(Integer.parseInt(detailedSubjectForm.getCourse()));
		
		detailedSubjects.setCourse(course);
		detailedSubjects.setSubjectName(detailedSubjectForm.getSubjectName());
		detailedSubjects.setIsActive(true);
		detailedSubjects.setCreatedBy(detailedSubjectForm.getUserId());
		detailedSubjects.setModifiedBy(detailedSubjectForm.getUserId());
		detailedSubjects.setCreatedDate(new Date());
		detailedSubjects.setLastModifiedDate(new Date());
	return detailedSubjects;	
	}
}
