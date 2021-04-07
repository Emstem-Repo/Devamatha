package com.kp.cms.helpers.reports;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.PerformaIIIForm;
import com.kp.cms.to.reports.PerformaIIIMapTO;
import com.kp.cms.to.reports.PerformaIIITO;

public class PerformaIIIHelper {
	
private static final Log log = LogFactory.getLog(PerformaIIIHelper.class);
	
	/**
	 * @param studentsAttendanceReportForm
	 * @return
	 */
	private StringBuffer commonSearch(PerformaIIIForm performaIIIForm) {
		log.info("entered commonSearch..");
		StringBuffer searchCriteria = new StringBuffer();
		
		if (performaIIIForm.getProgramId().trim().length() > 0) {
			String program = "student.admAppln.courseBySelectedCourseId.program.id = "
				+ performaIIIForm.getProgramId();
			searchCriteria.append(program);
		}else if (performaIIIForm.getProgramTypeId().trim().length() > 0) {
			String programType = "student.admAppln.courseBySelectedCourseId.program.programType.id = "
				+ performaIIIForm.getProgramTypeId();
			searchCriteria.append(programType);
		}

		if (performaIIIForm.getSemister().trim().length() > 0) {
			String semister = " and student.classSchemewise.classes.termNumber = "
					+ performaIIIForm.getSemister();
			searchCriteria.append(semister);
		}		
		log.info("exit commonSearch..");
		return searchCriteria;
	}
	
	/**
	 * @param performaIIIForm
	 * @return
	 */
	public String getSelectionCriteria(
			PerformaIIIForm performaIIIForm) {
		
		log.info("Entered getSelectionCriteria..");
		StringBuffer statusCriteria = commonSearch(performaIIIForm);
		
		String searchCriteria= "";
		
		searchCriteria = " select student.admAppln.courseBySelectedCourseId.id, " +
				" student.admAppln.courseBySelectedCourseId.name,	" +
				" student.admAppln.courseBySelectedCourseId.maxIntake, " +
				" student.admAppln.personalData.residentCategory.id, " +
				" student.admAppln.personalData.residentCategory.name " +
				" from Student student " +
				" where " +statusCriteria+
				" and student.isAdmitted = 1 " +
				" order by student.admAppln.courseBySelectedCourseId.id";
		
		log.info("Exit getSelectionCriteria..");
		return searchCriteria;
	}
	
	/**
	 * @param performIIIBOList
	 * @return
	 */
	public Map<Integer, PerformaIIITO> convertBoToTo(List<Student> performIIIBOList) {
			
		log.info("entered convertBotoTo..");
		Map<Integer, PerformaIIITO> courseIntakeDetailsMap = new HashMap<Integer, PerformaIIITO>();
		Map<Integer, PerformaIIIMapTO> categoryMap = null;
		PerformaIIITO performaIIITO = null;
		PerformaIIIMapTO performaIIIMapTO = null;
		
		if(performIIIBOList !=null){
			Iterator heldItr = performIIIBOList.iterator();
			
			while (heldItr.hasNext()) {

				Object[] courseIntakeDetails = (Object[]) heldItr.next();
				
				if (courseIntakeDetailsMap.containsKey((Integer) courseIntakeDetails[0])) {
					performaIIITO = courseIntakeDetailsMap.get((Integer) courseIntakeDetails[0]);
					categoryMap = performaIIITO.getCategoryMap();
					
					if(categoryMap.containsKey((Integer) courseIntakeDetails[3])){
						performaIIIMapTO = categoryMap.get((Integer) courseIntakeDetails[3]);
						
						int count = performaIIIMapTO.getIntakeValue();
						count++;
						performaIIIMapTO.setIntakeValue(count);
						performaIIIMapTO.setCategoryName((String)courseIntakeDetails[4]);
						categoryMap.put((Integer) courseIntakeDetails[3], performaIIIMapTO);
					}else{
						performaIIIMapTO = new PerformaIIIMapTO();
						
						performaIIIMapTO.setCategoryName((String)courseIntakeDetails[4]);
						performaIIIMapTO.setIntakeValue(1);
						categoryMap.put((Integer) courseIntakeDetails[3], performaIIIMapTO);
						performaIIITO.setCategoryMap(categoryMap);
					}
				}else{
					performaIIITO = new PerformaIIITO();
					if(courseIntakeDetails[1] != null){
						performaIIITO.setCourseName((String) courseIntakeDetails[1]);
					}
					if(courseIntakeDetails[2] != null){
						performaIIITO.setCourseIntake(courseIntakeDetails[2].toString());
					}
					if(courseIntakeDetails[3] != null){
						performaIIIMapTO = new PerformaIIIMapTO();
						categoryMap = new HashMap<Integer, PerformaIIIMapTO>();
						
						performaIIIMapTO.setCategoryName(courseIntakeDetails[4].toString());
						performaIIIMapTO.setIntakeValue(1);
						categoryMap.put((Integer) courseIntakeDetails[3], performaIIIMapTO);
						performaIIITO.setCategoryMap(categoryMap);
					}
					courseIntakeDetailsMap.put((Integer) courseIntakeDetails[0], performaIIITO);
				}
			}
		}	
		log.info("exit convertBotoTo..");
		return courseIntakeDetailsMap;
	}
}