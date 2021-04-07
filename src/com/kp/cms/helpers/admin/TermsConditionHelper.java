package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.bo.admin.TermsConditions;
import com.kp.cms.forms.admin.TermsConditionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.to.admin.TermsConditionTO;

;

/**
 * 
 * A helper class for TermsCondition Screen
 * 
 */

public class TermsConditionHelper {

	public static volatile TermsConditionHelper termsConditionHelper = null;

	/**
	 * 
	 * @return
	 */
	public static TermsConditionHelper getInstance() {
		if (termsConditionHelper == null) {
			termsConditionHelper = new TermsConditionHelper();
		}
		return termsConditionHelper;
	}
	/**
	 * setting form values to BO
	 * @param termsConditionForm
	 * @return
	 * @throws Exception
	 */
	public TermsConditions populateTermsConditions(
			TermsConditionForm termsConditionForm) throws Exception {
		Course course = new Course();
		TermsConditions termsConditions = new TermsConditions();
		termsConditions.setId(termsConditionForm.getId());
		course.setId(Integer.parseInt(termsConditionForm.getCourseId()));
		termsConditions.setCourse(course);
		termsConditions.setIsActive(true);
		termsConditions.setYear(Integer.parseInt(termsConditionForm.getYears()));
		termsConditions.setDescription(termsConditionForm.getDescription());
		termsConditions.setCreatedDate(new Date());
		termsConditions.setLastModifiedDate(new Date());
		termsConditions.setCreatedBy(termsConditionForm.getUserId());
		termsConditions.setModifiedBy(termsConditionForm.getUserId());
		
		return termsConditions;
	}

	/**
	 * copying Bo values to TO for UI display
	 * @param termsConditionList
	 * @param id
	 * @return
	 */
	public List<TermsConditionTO> copyTermsAndConditionsBosToTos(List<TermsConditions> termsConditionList, int id) {
		List<TermsConditionTO> termsandConditionsToList = new ArrayList<TermsConditionTO>();
		Iterator<TermsConditions> iterator = termsConditionList.iterator();
		TermsConditions termsCondition;
		TermsConditionTO termsConditionTO;
		CourseTO courseTo;
		ProgramTypeTO programTypeTo;
		ProgramTO programTo;

		while (iterator.hasNext()) {
			termsConditionTO = new TermsConditionTO();
			programTo = new ProgramTO();
			programTypeTo = new ProgramTypeTO();
			courseTo = new CourseTO();

			termsCondition = iterator.next();
			programTypeTo.setProgramTypeName(termsCondition.getCourse()
					.getProgram().getProgramType().getName());
			programTypeTo.setProgramTypeId(termsCondition.getCourse()
					.getProgram().getProgramType().getId());
			programTo.setId(termsCondition.getCourse().getProgram().getId());
			programTo
					.setName(termsCondition.getCourse().getProgram().getName());
			programTo.setProgramTypeTo(programTypeTo);

			courseTo.setName(termsCondition.getCourse().getName());
			courseTo.setId(termsCondition.getCourse().getId());
			courseTo.setProgramTo(programTo);

			termsConditionTO.setCourseTo(courseTo);
			if (id == 0) {
				termsConditionTO.setDescription(splitString(termsCondition
						.getDescription()));
			} else {
				termsConditionTO
						.setDescription(termsCondition.getDescription());
			}

			termsConditionTO.setId(termsCondition.getId());
			termsConditionTO.setYear(termsCondition.getYear());
			termsConditionTO.setEndYear(termsCondition.getYear() + 1);
			
			
			termsandConditionsToList.add(termsConditionTO);

		}
		return termsandConditionsToList;
	}

	
	/**
	 * 
	 * @param This
	 *            method is used to split the string if it is long (For keeping
	 *            50 aplhabets)
	 * @return
	 */
	public static String splitString(String value) {
		StringBuffer appendedvalue = new StringBuffer("");
		int length = value.length();
		String[] temp = new String[length];
		int begindex = 0, endindex = 50;
		int count = 0;

		while (true) {
			if (endindex < length) {
				temp[count] = value.substring(begindex, endindex);
				begindex = begindex + 50;
				endindex = endindex + 50;
				appendedvalue = appendedvalue.append(temp[count] + "\n");
			} else {
				if (count == 0){
					temp[count] = value.substring(0, length);
				}
				temp[count] = value.substring(begindex, length);
				appendedvalue = appendedvalue.append(temp[count]);
				break;
			}
			count++;
		}
		return appendedvalue.toString();

	}
	/**
	 * 
	 * @param termsConditionForm
	 * @return
	 * @throws Exception
	 */
	public List<TermsConditionChecklist> populateTermsConditionsDetails(TermsConditionForm termsConditionForm) throws Exception {
		List<TermsConditionChecklist> tcBOList = new ArrayList<TermsConditionChecklist>();
		Iterator<TermsConditionChecklistTO> tcIter = termsConditionForm.getTermsConditionList().iterator();
		TermsConditionChecklist tcChecklist = new TermsConditionChecklist();
		TermsConditionChecklistTO tcChecklistTO = new TermsConditionChecklistTO();
		Course course = new Course();
		if(termsConditionForm.getCourseId()!= null && !termsConditionForm.getCourseId().isEmpty()){
			course.setId(Integer.parseInt(termsConditionForm.getCourseId()));
		}
		while(tcIter.hasNext()){
			tcChecklist = new TermsConditionChecklist();
			tcChecklistTO = tcIter.next();
			if(tcChecklistTO.getChecklistDescription()== null || tcChecklistTO.getChecklistDescription().trim().isEmpty()){
				continue;
			}
			tcChecklist.setChecklistDescription(tcChecklistTO.getChecklistDescription());
			tcChecklist.setIsMandatory(tcChecklistTO.isMandatory());
			tcChecklist.setCourse(course);
			tcChecklist.setCreatedDate(new Date());
			tcChecklist.setLastModifiedDate(new Date());
			tcChecklist.setCreatedBy(termsConditionForm.getUserId());
			tcChecklist.setModifiedBy(termsConditionForm.getUserId());
			tcChecklist.setIsActive(true);
			/*if(termsConditionForm.getYears()!= null){
				tcChecklist.setYear(Integer.parseInt(termsConditionForm.getYears()));
			}*/
			tcBOList.add(tcChecklist);
		}
			
		return tcBOList;
		
	}
	/**
	 * copying Bo values to TO for UI display
	 * @param tcCheckListToList
	 * @param id
	 * @return
	 */
	public List<TermsConditionChecklistTO> copyTermsAndConditionsCheckListBosToTos(List<TermsConditionChecklist> tcBOList) {
		List<TermsConditionChecklistTO> tcCheckListToList = new ArrayList<TermsConditionChecklistTO>();
		Iterator<TermsConditionChecklist> iterator = tcBOList.iterator();
		TermsConditionChecklist terChecklist;
		TermsConditionChecklistTO teChecklistTO;
		CourseTO courseTo;
		ProgramTypeTO programTypeTo;
		ProgramTO programTo;

		while (iterator.hasNext()) {
			teChecklistTO = new TermsConditionChecklistTO();
			programTo = new ProgramTO();
			programTypeTo = new ProgramTypeTO();
			courseTo = new CourseTO();

			terChecklist = iterator.next();
			programTypeTo.setProgramTypeName(terChecklist.getCourse()
					.getProgram().getProgramType().getName());
			programTypeTo.setProgramTypeId(terChecklist.getCourse()
					.getProgram().getProgramType().getId());
			programTo.setId(terChecklist.getCourse().getProgram().getId());
			programTo
					.setName(terChecklist.getCourse().getProgram().getName());
			programTo.setProgramTypeTo(programTypeTo);

			courseTo.setName(terChecklist.getCourse().getName());
			courseTo.setId(terChecklist.getCourse().getId());
			courseTo.setProgramTo(programTo);

			teChecklistTO.setCourseTO(courseTo);
			/*teChecklistTO.setYear(terChecklist.getYear());
			teChecklistTO.setEndYear(terChecklist.getYear() + 1);*/
			teChecklistTO.setChecklistDescription(terChecklist.getChecklistDescription());
			teChecklistTO.setMandatory(terChecklist.getIsMandatory());
			if(terChecklist.getIsMandatory()){
				teChecklistTO.setIsMandatory("Yes");
			}
			else
			{
				teChecklistTO.setIsMandatory("No");
			}
			teChecklistTO.setId(terChecklist.getId());
		
			
			tcCheckListToList.add(teChecklistTO);

		}
		return tcCheckListToList;
	}

	
}
