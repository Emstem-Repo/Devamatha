package com.kp.cms.handlers.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.GradePointRangeBO;
import com.kp.cms.forms.exam.GradePointRangeForm;
import com.kp.cms.helpers.exam.ExamGradeDefinitionHelper;
import com.kp.cms.helpers.exam.GradePointRangeHelper;
import com.kp.cms.to.exam.GradePointRangeTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamGradeDefinitionImpl;
import com.kp.cms.transactionsimpl.exam.GradePointRangeImpl;

public class GradePointRangeHandler extends ExamGenHandler {

	GradePointRangeHelper helper = new GradePointRangeHelper();
	GradePointRangeImpl impl = new GradePointRangeImpl();

	public List<KeyValueTO> init() throws Exception {

		ArrayList<GradePointRangeBO> listBO = new ArrayList(impl
				.select_ActiveOnly(GradePointRangeBO.class));
		List<KeyValueTO> list = helper.convertBOToTO_courseOnly(listBO);
		return list;

	}
	// AShwini added
	
	public List<GradePointRangeTO> initNew() throws Exception {

		ArrayList<GradePointRangeBO> listBO = new ArrayList(impl
				.select_ActiveOnly(GradePointRangeBO.class));
		List<GradePointRangeTO> list = helper.convertBOToTO_courseOnlyAccordingToBatch(listBO);
		return list;

	}

	public ArrayList<GradePointRangeTO> add(List<Integer> listCourses)
			throws Exception {

		ArrayList<GradePointRangeBO> listBO = new ArrayList(impl
				.select_ActiveOnly(GradePointRangeBO.class));
		return helper.convertBOToTO(listBO);

	}

	public void addGDAdd(List<Integer> listCourses, String startPercentage,
			String endPercentage, String grade, String interpretation,
			String resultClass, String gradePoint, String userId,GradePointRangeForm objform)
			throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		BigDecimal gradePointBD = null;
		if(gradePoint!=null&&gradePoint.length()>0){
			gradePointBD = new BigDecimal(gradePoint);
		}
		
		
		GradePointRangeBO objBO = null;
		for (Integer i : listCourses) {
			objBO = new GradePointRangeBO(i.intValue(), startPercBD,
					endPercBD, grade, interpretation, resultClass,
					gradePointBD, userId,objform.getAppliedYear());
			//impl.isDuplicated(0, i.intValue(), startPercBD, endPercBD, grade,objform);
			impl.insert(objBO);

		}

	}

	public void update(int id, int courseID, String startPercentage,
			String endPercentage, String grade, String interpretation,
			String resultClass, String gradePoint, String userId,GradePointRangeForm objform)
			throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		BigDecimal gradePointBD = null;
		if(gradePoint!=null&&gradePoint.trim().length()>0){
			gradePointBD= new BigDecimal(gradePoint);
		}
			

		impl.isDuplicated(id, courseID, startPercBD, endPercBD, grade,objform);
		impl.update(id, courseID, startPercBD, endPercBD, grade,
				interpretation, resultClass, gradePointBD, userId);

	}

	public void delete_gradeDef(int gradeDefId, String userId) throws Exception {
		impl.delete_IExamGenBO(gradeDefId, userId, GradePointRangeBO.class);
	}

	public void delete_courseId(int courseId, String userId) throws Exception {
		impl.delete_courseID(courseId, userId);
	}

	public void reactivate(int id, String userId) throws Exception {
		impl.reActivate_IExamGenBO(id, userId, GradePointRangeBO.class);
	}

	public GradePointRangeForm getUpdatableForm(
			GradePointRangeForm objform, String mode) throws Exception {
		objform = helper.createFormObjcet(objform, impl
				.loadExamGradeClassDefinition(objform.getId()));

		return objform;
	}

	public ArrayList<GradePointRangeTO> select(
			ArrayList<Integer> listcourseId,String appliedYear) throws Exception {
		return helper.convertBOToTO(impl.select_GradeDefinition(listcourseId,appliedYear));

	}

}
