package com.kp.cms.helpers.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.util.ArrayList;
import java.util.Collections;

import com.kp.cms.bo.exam.ExamSGPADefinitionBO;
import com.kp.cms.forms.exam.ExamSGPADefinitionForm;
import com.kp.cms.to.exam.ExamSGPADefinitionTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.ExamGradeDefinitionComparator;

public class ExamSGPADefinitionHelper {

	public ExamSGPADefinitionForm createFormObjcet(
			ExamSGPADefinitionForm objform, ExamSGPADefinitionBO objbo) throws Exception  {
		objform.setCourseIds(Integer.toString(objbo.getCourseId()));

		objform.setId(objbo.getId());
		objform.setStartPercentage(objbo.getStartPercentage().toString());
		objform.setEndPercentage(objbo.getEndPercentage().toString());
		objform.setGrade(objbo.getGrade());
		objform.setInterpretation(objbo.getInterpretation());
		
		String gradePoint="";
		
		objform.setGradePoint(gradePoint);
		objform.setCourseName(Integer.toString(objbo.getCourseId()));
		objform.setOrgStartPercentage(objbo.getStartPercentage().toString());
		objform.setOrgEndPercentage(objbo.getEndPercentage().toString());
		objform.setOrgGrade(objbo.getGrade());
		objform.setOrgInterpretation(objbo.getInterpretation());
		
		objform.setOrgGradePoint(gradePoint);
		objform.setOrgCourseid(objbo.getCourseId());

		return objform;
	}

	public ArrayList<ExamSGPADefinitionTO> convertBOToTO(
			ArrayList<ExamSGPADefinitionBO> listBO) throws Exception  {
		ArrayList<ExamSGPADefinitionTO> listTO = new ArrayList<ExamSGPADefinitionTO>();
		for (ExamSGPADefinitionBO eBO : listBO) {
			String gradePoint = "";
			
			ExamSGPADefinitionTO eTO = new ExamSGPADefinitionTO(eBO.getId(), eBO.getCourseBO()
					.getPTypeProgramCourse(), eBO.getStartPercentage()
					.toString(), eBO.getEndPercentage().toString(), eBO
					.getGrade(), eBO.getInterpretation());
			listTO.add(eTO);
		}
		return listTO;
	}

	public ArrayList<KeyValueTO> convertBOToTO_courseOnly(
			ArrayList<ExamSGPADefinitionBO> listBO) throws Exception  {
		
		ArrayList<Integer> tempCourseList = new ArrayList<Integer>();
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		KeyValueTO eTO;
		for (ExamSGPADefinitionBO eBO : listBO) {
			if (!tempCourseList.contains(eBO.getCourseId())) {
				tempCourseList.add(eBO.getCourseId());
				//Collections.sort(tempCourseList,new ExamSGPADefinitionListComparator());
				eTO = new KeyValueTO(eBO.getCourseId(), eBO.getCourseBO()
						.getPTypeProgramCourse());
				listTO.add(eTO);
			}
		}
			Collections.sort(listTO,new ExamGradeDefinitionComparator());
		return listTO;
	}
}
