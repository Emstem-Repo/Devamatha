package com.kp.cms.helpers.studentfeedback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.studentfeedback.FacultyGrades;
import com.kp.cms.helpers.studentfeedback.FacultyGradesHelper;
import com.kp.cms.to.studentfeedback.FacultyGradesTO;

public class FacultyGradesHelper {
	private static final Log log = LogFactory.getLog(FacultyGradesHelper.class);
	private static volatile FacultyGradesHelper facultyGradesHelper = null;

	private FacultyGradesHelper() {
	}
	
	public static FacultyGradesHelper getInstance() {

		if (facultyGradesHelper == null) {
			facultyGradesHelper = new FacultyGradesHelper();
		}
		return facultyGradesHelper;
	}
	
	public FacultyGrades populateTOtoBO(FacultyGradesTO facultyGradesTO) throws Exception{
		log.info("Inside populateTOtoBO of FacultyGradesHelper");
		FacultyGrades facultyGrades = null;
		
		if (facultyGradesTO != null) {
			facultyGrades = new FacultyGrades();
			if(facultyGradesTO.getId()>0)
			{
				facultyGrades.setId(facultyGradesTO.getId());
			}
			if(facultyGradesTO.getGrade()!=null)
				facultyGrades.setGrade(facultyGradesTO.getGrade());
			if (facultyGradesTO.getScaleFrom() != null) {
				
				facultyGrades.setScaleFrom(facultyGradesTO.getScaleFrom());

			}
			if (facultyGradesTO.getScaleTo() != null) {
				facultyGrades.setScaleTo(facultyGradesTO.getScaleTo());
			}
			if (facultyGradesTO.getCreatedBy() != null && !facultyGradesTO.getCreatedBy().isEmpty()) {
				facultyGrades.setCreatedBy(facultyGradesTO.getCreatedBy());
			}
			if (facultyGradesTO.getModifiedBy() != null && !facultyGradesTO.getModifiedBy().isEmpty()) {
				facultyGrades.setModifiedBy(facultyGradesTO.getModifiedBy());
			}
			facultyGrades.setCreatedDate(new Date());
			facultyGrades.setLastModifiedDate(new Date());
			facultyGrades.setIsActive(true);
		}
		log.info("Leaving from populateTOtoBO of ExternalEvaluatorHelper");
		return facultyGrades;
	}
	
	public List<FacultyGradesTO> pupulateFacultyGradesBOtoTO(List<FacultyGrades> facultyList)throws Exception {
		log.info("Entering into pupulateFacultyGradesBOtoTO of FacultyGradesHelper");
		FacultyGradesTO facultyGradesTO = null;

		List<FacultyGradesTO> newFacultList = new ArrayList<FacultyGradesTO>();
		if (facultyList != null && !facultyList.isEmpty()) {
			Iterator<FacultyGrades> iterator = facultyList.iterator();
			while (iterator.hasNext()) {
				FacultyGrades facultyGrades = iterator.next();
				facultyGradesTO = new FacultyGradesTO();
				if (facultyGrades.getId() != 0 && facultyGrades.getGrade() != null) {
					facultyGradesTO.setId(facultyGrades.getId());
					facultyGradesTO.setGrade(facultyGrades.getGrade());
					facultyGradesTO.setScaleFrom(facultyGrades.getScaleFrom());
					facultyGradesTO.setScaleTo(facultyGrades.getScaleTo());

					newFacultList.add(facultyGradesTO);
				}
			}
		}
		log.info("Leaving from pupulateFacultyGradesBOtoTO of FacultyGradesHelper");
		return newFacultList;
	}
	
	public FacultyGrades populateTotoBoUpdate(FacultyGradesTO byTO) throws Exception{
		log.info("Entering into populateTotoBoUpdate of FacultyGradesHelper");
		if (byTO != null) {
			FacultyGrades facultyGrades = new FacultyGrades();
			facultyGrades.setId(byTO.getId());
			facultyGrades.setGrade(byTO.getGrade());
			facultyGrades.setScaleFrom(byTO.getScaleFrom());
			facultyGrades.setScaleTo(byTO.getScaleTo());
						
			facultyGrades.setIsActive(true);
			facultyGrades.setLastModifiedDate(new Date());
			facultyGrades.setModifiedBy(byTO.getModifiedBy());
			if (facultyGrades != null) {
				return facultyGrades;
			}
		}
		log.info("Leaving from populateTotoBoUpdate of FacultyGradesHelper");
		return null;
	}
	
	public FacultyGradesTO populateBotoToEdit(FacultyGrades facultyGrades)throws Exception {
		log.info("Entering into populateBotoToEdit of FacultyGradesHelper");
		FacultyGradesTO facultyGradesTO = new FacultyGradesTO();
		if (facultyGrades != null) {
			facultyGradesTO.setId(facultyGrades.getId());
			if (facultyGrades.getGrade() != null && !facultyGrades.getGrade().isEmpty()) {
				facultyGradesTO.setGrade(facultyGrades.getGrade());
			}
			if (facultyGrades.getScaleFrom() != null) {
				facultyGradesTO.setScaleFrom(facultyGrades.getScaleFrom());
			}
			if (facultyGrades.getScaleTo() != null) {
				facultyGradesTO.setScaleTo(facultyGrades.getScaleTo());
			}
						
		}
		if (facultyGradesTO != null) {
			return facultyGradesTO;
		}
		log.info("Leaving from populateBotoToEdit of FacultyGradesHelper");
		return null;
	}

}
