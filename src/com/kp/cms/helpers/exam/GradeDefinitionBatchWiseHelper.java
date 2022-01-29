package com.kp.cms.helpers.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamGradeDefinitionBO;
import com.kp.cms.bo.exam.GradeDefinitionBatchWiseBO;
import com.kp.cms.forms.exam.ExamGradeDefinitionForm;
import com.kp.cms.forms.exam.GradeDefinitionBatchWiseForm;
import com.kp.cms.to.exam.ExamCceFactorTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.ExamGradeDefinitionTO;
import com.kp.cms.to.exam.GradeDefinitionBatchWisTo;
import com.kp.cms.to.exam.GradeDefinitionBatchWiseTO;
import com.kp.cms.utilities.GradeDefinitionBatchWiseComparator;

public class GradeDefinitionBatchWiseHelper {

	public GradeDefinitionBatchWiseForm createFormObjcet(GradeDefinitionBatchWiseForm objform, GradeDefinitionBatchWiseBO objbo) throws Exception  {
		objform.setCourseIds(Integer.toString(objbo.getCourseId()));

		objform.setId(objbo.getId());
		objform.setStartPercentage(objbo.getStartPercentage().toString());
		objform.setEndPercentage(objbo.getEndPercentage().toString());
		objform.setGrade(objbo.getGrade());
		objform.setInterpretation(objbo.getInterpretation());
		objform.setResultClass(objbo.getResultClass());
		objform.setFromBatch(Integer.toString(objbo.getFromBatch()));
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
		objform.setOrgfromBatch(Integer.toString(objbo.getFromBatch()));
		objform.setOrgCourseid(objbo.getCourseId());

		return objform;
	}

	public ArrayList<GradeDefinitionBatchWiseTO> convertBOToTO(	ArrayList<GradeDefinitionBatchWiseBO> listBO, GradeDefinitionBatchWiseForm objform) throws Exception  {
		ArrayList<GradeDefinitionBatchWiseTO> listTO = new ArrayList<GradeDefinitionBatchWiseTO>();
		for (GradeDefinitionBatchWiseBO eBO : listBO) {
			objform.setFromBatch(Integer.toString(eBO.getFromBatch()));
			String gradePoint = "";
			if (eBO.getGradePoint() != null
					&& eBO.getGradePoint().toString().length() > 0) {
				gradePoint = eBO.getGradePoint().toString();
			}
			GradeDefinitionBatchWiseTO eTO = new GradeDefinitionBatchWiseTO(eBO.getId(), eBO.getCourseBO()
					.getPTypeProgramCourse(),Integer.toString(eBO.getFromBatch()), eBO.getStartPercentage()
					.toString(), eBO.getEndPercentage().toString(), eBO
					.getGrade(), eBO.getInterpretation(), eBO.getResultClass(),
					gradePoint);
			listTO.add(eTO);
		}
		return listTO;
	}

	public List<GradeDefinitionBatchWisTo> convertBOToTO_courseOnly(ArrayList<GradeDefinitionBatchWiseBO> listBO) throws Exception  {
		
		ArrayList<Integer> tempCourseList = new ArrayList<Integer>();
		ArrayList<GradeDefinitionBatchWisTo> listTO = new ArrayList<GradeDefinitionBatchWisTo>();
		for (GradeDefinitionBatchWiseBO eBO : listBO) {
			if (!tempCourseList.contains(eBO.getCourseId())) {
				GradeDefinitionBatchWisTo eTO= new GradeDefinitionBatchWisTo();
				tempCourseList.add(eBO.getCourseId());
				eTO.setId(eBO.getCourseId());
				eTO.setCourse(eBO.getCourseBO().getPTypeProgramCourse());
				eTO.setFromBatch(Integer.toString(eBO.getFromBatch()));
				listTO.add(eTO);
			}
		}
	//	Collections.sort(listTO,new GradeDefinitionBatchWiseComparator());
		return listTO;
	}

	public List<GradeDefinitionBatchWisTo> convertBOtoTO_course(ArrayList<ExamCourseUtilBO> selectCourse, String year) {
		ArrayList<GradeDefinitionBatchWisTo> list = new ArrayList<GradeDefinitionBatchWisTo>();
		ExamCourseUtilTO to = null;
		for (ExamCourseUtilBO am : selectCourse) {
			GradeDefinitionBatchWisTo eTO= new GradeDefinitionBatchWisTo();
			eTO.setCourse(am.getPTypeProgramCourse());
			eTO.setFromBatch(year);
			list.add(eTO);
		}
	//	Collections.sort(list);
		return list;
	}
}
