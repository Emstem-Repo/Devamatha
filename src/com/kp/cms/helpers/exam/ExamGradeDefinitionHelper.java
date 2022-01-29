package com.kp.cms.helpers.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.util.ArrayList;
import java.util.Collections;

import com.kp.cms.bo.exam.ExamGradeDefinitionBO;
import com.kp.cms.forms.exam.ExamGradeDefinitionForm;
import com.kp.cms.to.exam.ExamGradeDefinitionTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.ExamGradeDefinitionComparator;

public class ExamGradeDefinitionHelper {

	public ExamGradeDefinitionForm createFormObjcet(
			ExamGradeDefinitionForm objform, ExamGradeDefinitionBO objbo) throws Exception  {
		objform.setCourseIds(Integer.toString(objbo.getCourseId()));

		objform.setId(objbo.getId());
		objform.setStartPercentage(objbo.getStartPercentage().toString());
		objform.setEndPercentage(objbo.getEndPercentage().toString());
		objform.setGrade(objbo.getGrade());
		objform.setInterpretation(objbo.getInterpretation());
		objform.setResultClass(objbo.getResultClass());
		String gradePoint="";
		if(objbo.getGradePoint()!=null){
			gradePoint=objbo.getGradePoint().toString();
		}
		objform.setGradePoint(gradePoint);
		objform.setCourseName(Integer.toString(objbo.getCourseId()));
		objform.setOrgStartPercentage(objbo.getStartPercentage().toString());
		objform.setOrgEndPercentage(objbo.getEndPercentage().toString());
		objform.setOrgGrade(objbo.getGrade());
		objform.setOrgInterpretation(objbo.getInterpretation());
		objform.setOrgResultClass(objbo.getResultClass());
		objform.setOrgGradePoint(gradePoint);
		objform.setOrgCourseid(objbo.getCourseId());

		return objform;
	}

	public ArrayList<ExamGradeDefinitionTO> convertBOToTO(
			ArrayList<ExamGradeDefinitionBO> listBO) throws Exception  {
		ArrayList<ExamGradeDefinitionTO> listTO = new ArrayList<ExamGradeDefinitionTO>();
		for (ExamGradeDefinitionBO eBO : listBO) {
			String gradePoint = "";
			if (eBO.getGradePoint() != null
					&& eBO.getGradePoint().toString().length() > 0) {
				gradePoint = eBO.getGradePoint().toString();
			}
			ExamGradeDefinitionTO eTO = new ExamGradeDefinitionTO(eBO.getId(), eBO.getCourseBO()
					.getPTypeProgramCourse(), eBO.getStartPercentage()
					.toString(), eBO.getEndPercentage().toString(), eBO
					.getGrade(), eBO.getInterpretation(), eBO.getResultClass(),
					gradePoint);
			listTO.add(eTO);
		}
		return listTO;
	}

	public ArrayList<KeyValueTO> convertBOToTO_courseOnly(
			ArrayList<ExamGradeDefinitionBO> listBO) throws Exception  {
		
		ArrayList<Integer> tempCourseList = new ArrayList<Integer>();
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		KeyValueTO eTO;
		for (ExamGradeDefinitionBO eBO : listBO) {
			if (!tempCourseList.contains(eBO.getCourseId())) {
				tempCourseList.add(eBO.getCourseId());
				//Collections.sort(tempCourseList,new ExamGradeDefinitionListComparator());
				eTO = new KeyValueTO(eBO.getCourseId(), eBO.getCourseBO()
						.getPTypeProgramCourse());
				listTO.add(eTO);
			}
		}
			Collections.sort(listTO,new ExamGradeDefinitionComparator());
		return listTO;
	}
}
