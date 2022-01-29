package com.kp.cms.handlers.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamGradeDefinitionBO;
import com.kp.cms.forms.exam.ExamGradeDefinitionForm;
import com.kp.cms.helpers.exam.ExamGradeDefinitionHelper;
import com.kp.cms.to.exam.ExamGradeDefinitionTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamGradeDefinitionImpl;

public class ExamGradeDefinitionHandler extends ExamGenHandler {

	ExamGradeDefinitionHelper helper = new ExamGradeDefinitionHelper();
	ExamGradeDefinitionImpl impl = new ExamGradeDefinitionImpl();

	public List<KeyValueTO> init() throws Exception {

		ArrayList<ExamGradeDefinitionBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamGradeDefinitionBO.class));
		List<KeyValueTO> list = helper.convertBOToTO_courseOnly(listBO);
		return list;

	}

	public ArrayList<ExamGradeDefinitionTO> add(List<Integer> listCourses)
			throws Exception {

		ArrayList<ExamGradeDefinitionBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamGradeDefinitionBO.class));
		return helper.convertBOToTO(listBO);

	}

	public void addGDAdd(List<Integer> listCourses, String startPercentage,
			String endPercentage, String grade, String interpretation,
			String resultClass, String gradePoint, String userId)
			throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		BigDecimal gradePointBD = null;
		if(gradePoint!=null&&gradePoint.length()>0){
			gradePointBD = new BigDecimal(gradePoint);
		}
		
		
		ExamGradeDefinitionBO objBO = null;
		for (Integer i : listCourses) {
			objBO = new ExamGradeDefinitionBO(i.intValue(), startPercBD,
					endPercBD, grade, interpretation, resultClass,
					gradePointBD, userId);
			impl.isDuplicated(0, i.intValue(), startPercBD, endPercBD, grade);
			impl.insert(objBO);

		}

	}

	public void update(int id, int courseID, String startPercentage,
			String endPercentage, String grade, String interpretation,
			String resultClass, String gradePoint, String userId)
			throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		BigDecimal gradePointBD = null;
		if(gradePoint!=null&&gradePoint.trim().length()>0){
			gradePointBD= new BigDecimal(gradePoint);
		}
			

		impl.isDuplicated(id, courseID, startPercBD, endPercBD, grade);
		impl.update(id, courseID, startPercBD, endPercBD, grade,
				interpretation, resultClass, gradePointBD, userId);

	}

	public void delete_gradeDef(int gradeDefId, String userId) throws Exception {
		impl.delete_IExamGenBO(gradeDefId, userId, ExamGradeDefinitionBO.class);
	}

	public void delete_courseId(int courseId, String userId) throws Exception {
		impl.delete_courseID(courseId, userId);
	}

	public void reactivate(int id, String userId) throws Exception {
		impl.reActivate_IExamGenBO(id, userId, ExamGradeDefinitionBO.class);
	}

	public ExamGradeDefinitionForm getUpdatableForm(
			ExamGradeDefinitionForm objform, String mode) throws Exception {
		objform = helper.createFormObjcet(objform, impl
				.loadExamGradeClassDefinition(objform.getId()));

		return objform;
	}

	public ArrayList<ExamGradeDefinitionTO> select(
			ArrayList<Integer> listcourseId) throws Exception {
		return helper.convertBOToTO(impl.select_GradeDefinition(listcourseId));

	}

}
