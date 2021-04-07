package com.kp.cms.helpers.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.util.ArrayList;
import java.util.Collections;

import com.kp.cms.bo.exam.GradePointRangeBO;
import com.kp.cms.forms.exam.GradePointRangeForm;
import com.kp.cms.to.exam.GradePointRangeTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.ExamGradeDefinitionComparator;

public class GradePointRangeHelper {

	public GradePointRangeForm createFormObjcet(
			GradePointRangeForm objform, GradePointRangeBO objbo) throws Exception  {
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
		objform.setAppliedYear(objbo.getAppliedYear());

		return objform;
	}

	public ArrayList<GradePointRangeTO> convertBOToTO(
			ArrayList<GradePointRangeBO> listBO) throws Exception  {
		ArrayList<GradePointRangeTO> listTO = new ArrayList<GradePointRangeTO>();
		for (GradePointRangeBO eBO : listBO) {
			String gradePoint = "";
			if (eBO.getGradePoint() != null
					&& eBO.getGradePoint().toString().length() > 0) {
				gradePoint = eBO.getGradePoint().toString();
			}
			GradePointRangeTO eTO = new GradePointRangeTO(eBO.getId(), eBO.getCourseBO()
					.getPTypeProgramCourse(), eBO.getStartPercentage()
					.toString(), eBO.getEndPercentage().toString(), eBO
					.getGrade(), eBO.getInterpretation(), eBO.getResultClass(),
					gradePoint,eBO.getAppliedYear(),eBO.getCourseBO().getCourseID());
			listTO.add(eTO);
		}
		return listTO;
	}

	public ArrayList<KeyValueTO> convertBOToTO_courseOnly(
			ArrayList<GradePointRangeBO> listBO) throws Exception  {
		
		ArrayList<Integer> tempCourseList = new ArrayList<Integer>();
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		KeyValueTO eTO;
		for (GradePointRangeBO eBO : listBO) {
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
	
	// Ashwini added 
	public ArrayList<GradePointRangeTO> convertBOToTO_courseOnlyAccordingToBatch(
			ArrayList<GradePointRangeBO> listBO) throws Exception  {
		
		ArrayList<Integer> tempCourseList = new ArrayList<Integer>();
		ArrayList<GradePointRangeTO> listTO = new ArrayList<GradePointRangeTO>();
		GradePointRangeTO eTO;
		for (GradePointRangeBO eBO : listBO) {
			eTO = new GradePointRangeTO();
			eTO.setId(eBO.getId());
			eTO.setCourseId(eBO.getCourseBO().getCourseID());
			eTO.setCourse(eBO.getCourseBO().getCourseName());
			eTO.setAppliedYear(eBO.getAppliedYear());
				listTO.add(eTO);
		}
			//Collections.sort(listTO,new ExamGradeDefinitionComparator());
		return listTO;
	}
}
