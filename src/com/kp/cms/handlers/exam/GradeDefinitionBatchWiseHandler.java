package com.kp.cms.handlers.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamGradeDefinitionBO;
import com.kp.cms.bo.exam.GradeDefinitionBatchWiseBO;
import com.kp.cms.forms.exam.ExamGradeDefinitionForm;
import com.kp.cms.forms.exam.GradeDefinitionBatchWiseForm;
import com.kp.cms.helpers.exam.GradeDefinitionBatchWiseHelper;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.ExamGradeDefinitionTO;
import com.kp.cms.to.exam.GradeDefinitionBatchWisTo;
import com.kp.cms.to.exam.GradeDefinitionBatchWiseTO;
import com.kp.cms.transactionsimpl.exam.GradeDefinitionBatchWiseImpl;

public class GradeDefinitionBatchWiseHandler extends ExamGenHandler {

	GradeDefinitionBatchWiseHelper helper = new GradeDefinitionBatchWiseHelper();
	GradeDefinitionBatchWiseImpl impl = new GradeDefinitionBatchWiseImpl();

	public List<GradeDefinitionBatchWisTo> init() throws Exception {
		ArrayList<GradeDefinitionBatchWiseBO> listBO = new ArrayList(impl.select_ActiveOnly(GradeDefinitionBatchWiseBO.class));
		List<GradeDefinitionBatchWisTo> list = helper.convertBOToTO_courseOnly(listBO);
		return list;

	}

	public ArrayList<GradeDefinitionBatchWiseTO> add(List<Integer> listCourses, GradeDefinitionBatchWiseForm objform)throws Exception {
		ArrayList<GradeDefinitionBatchWiseBO> listBO = new ArrayList(impl.select_ActiveOnly(GradeDefinitionBatchWiseBO.class));
		return helper.convertBOToTO(listBO,objform);

	}

	public void addGDAdd(List<Integer> listCourses, String formBatch, String startPercentage,
			String endPercentage, String grade, String interpretation,
			String resultClass, String gradePoint, String userId)
			throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		Integer formBatche=Integer.parseInt(formBatch);
		BigDecimal gradePointBD = null;
		if(gradePoint!=null&&gradePoint.length()>0){
			gradePointBD = new BigDecimal(gradePoint);
		}
		
		
		GradeDefinitionBatchWiseBO objBO = null;
		for (Integer i : listCourses) {
			objBO = new GradeDefinitionBatchWiseBO(i.intValue(),formBatche,startPercBD,
					endPercBD, grade, interpretation, resultClass,
					gradePointBD, userId);
			impl.isDuplicated(0, i.intValue(),formBatch,startPercBD, endPercBD, grade);
			impl.insert(objBO);

		}

	}

	public void update(int id, int courseID, String frombatch, String startPercentage,
			String endPercentage, String grade, String interpretation,
			String resultClass, String gradePoint, String userId)
			throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		BigDecimal gradePointBD = null;
		if(gradePoint!=null&&gradePoint.trim().length()>0){
			gradePointBD= new BigDecimal(gradePoint);
		}
			

		impl.isDuplicated(id, courseID,frombatch,startPercBD, endPercBD, grade);
		impl.update(id, courseID,frombatch, startPercBD, endPercBD, grade,
				interpretation, resultClass, gradePointBD, userId);

	}

	public void delete_gradeDef(int gradeDefId, String userId) throws Exception {
		impl.delete_IExamGenBO(gradeDefId, userId, GradeDefinitionBatchWiseBO.class);
	}

	public void delete_courseId(int courseId, String userId) throws Exception {
		impl.delete_courseID(courseId, userId);
	}

	public void reactivate(int id, String userId) throws Exception {
		impl.reActivate_IExamGenBO(id, userId, GradeDefinitionBatchWiseBO.class);
	}

	public GradeDefinitionBatchWiseForm getUpdatableForm(GradeDefinitionBatchWiseForm objform, String mode) throws Exception {
		objform = helper.createFormObjcet(objform, impl.loadGradeDefinitionBatchWiseBO(objform.getId()));

		return objform;
	}

	public ArrayList<GradeDefinitionBatchWiseTO> select(
			ArrayList<Integer> listcourseId, GradeDefinitionBatchWiseForm objform) throws Exception {
		return helper.convertBOToTO(impl.select_GradeDefinition(listcourseId),objform);
    	}

	public List<GradeDefinitionBatchWisTo> getListExamCourseYear(ArrayList<Integer> listCourses, String year) {
		return helper.convertBOtoTO_course(impl.select_course(listCourses),year);
		}

	public List<GradeDefinitionBatchWisTo> getListExamCourses(int courseId, String year) {
		ArrayList<Integer> listcourseId = new ArrayList<Integer>();
		listcourseId.add(courseId);
		return getListExamCourses(listcourseId,year);
	}
	public List<GradeDefinitionBatchWisTo> getListExamCourses(	ArrayList<Integer> listCourses,String year) {
		return helper.convertBOtoTO_course(impl.select_course(listCourses),year);
	}
}
