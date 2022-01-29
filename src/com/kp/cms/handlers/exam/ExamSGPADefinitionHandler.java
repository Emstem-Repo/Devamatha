package com.kp.cms.handlers.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamSGPADefinitionBO;
import com.kp.cms.forms.exam.ExamSGPADefinitionForm;
import com.kp.cms.helpers.exam.ExamSGPADefinitionHelper;
import com.kp.cms.to.exam.ExamSGPADefinitionTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamSGPADefinitionImpl;

public class ExamSGPADefinitionHandler extends ExamGenHandler {

	ExamSGPADefinitionHelper helper = new ExamSGPADefinitionHelper();
	ExamSGPADefinitionImpl impl = new ExamSGPADefinitionImpl();

	public List<KeyValueTO> init() throws Exception {

		ArrayList<ExamSGPADefinitionBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamSGPADefinitionBO.class));
		List<KeyValueTO> list = helper.convertBOToTO_courseOnly(listBO);
		return list;

	}

	public ArrayList<ExamSGPADefinitionTO> add(List<Integer> listCourses)
			throws Exception {

		ArrayList<ExamSGPADefinitionBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamSGPADefinitionBO.class));
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
		
		
		ExamSGPADefinitionBO objBO = null;
		for (Integer i : listCourses) {
			objBO = new ExamSGPADefinitionBO(i.intValue(), startPercBD,
					endPercBD, grade, interpretation,userId);
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
		impl.delete_IExamGenBO(gradeDefId, userId, ExamSGPADefinitionBO.class);
	}

	public void delete_courseId(int courseId, String userId) throws Exception {
		impl.delete_courseID(courseId, userId);
	}

	public void reactivate(int id, String userId) throws Exception {
		impl.reActivate_IExamGenBO(id, userId, ExamSGPADefinitionBO.class);
	}

	public ExamSGPADefinitionForm getUpdatableForm(
			ExamSGPADefinitionForm objform, String mode) throws Exception {
		objform = helper.createFormObjcet(objform, impl
				.loadExamSGPAClassDefinition(objform.getId()));

		return objform;
	}

	public ArrayList<ExamSGPADefinitionTO> select(
			ArrayList<Integer> listcourseId) throws Exception {
		return helper.convertBOToTO(impl.select_SGPADefinition(listcourseId));

	}

}
