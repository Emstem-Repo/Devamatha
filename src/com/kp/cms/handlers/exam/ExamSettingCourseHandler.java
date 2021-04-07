package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.bo.exam.ExamRevaluationTypeBO;
import com.kp.cms.bo.exam.ExamSettingCourseBO;
import com.kp.cms.bo.exam.ExamSettingCourseRevaluationBO;
import com.kp.cms.forms.exam.ExamSettingCourseForm;
import com.kp.cms.helpers.exam.ExamSettingCourseHelper;
import com.kp.cms.to.exam.ExamRevaluationTO;
import com.kp.cms.to.exam.ExamSettingCourseTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.transactionsimpl.exam.ExamSettingCourseImpl;

@SuppressWarnings("unchecked")
public class ExamSettingCourseHandler extends ExamGenHandler {

	ExamSettingCourseImpl impl = new ExamSettingCourseImpl();
	ExamSettingCourseHelper helper = new ExamSettingCourseHelper();

	public List<ExamSettingCourseTO> getList() throws Exception {

		ArrayList<ExamSettingCourseBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamSettingCourseBO.class));
		List<ExamSettingCourseTO> list = helper.convertBOtoTO(listBO);
		return list;
	}
	public List<KeyValueTO> getProgramTypeList() throws Exception {
		ArrayList<ExamProgramTypeUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamProgramTypeUtilBO.class,"programType"));
		return helper.convertBOToTO_ProgramType(listBO);

	}

	public List<ExamRevaluationTO> getRevaluationTypeList() throws Exception {
		ArrayList<ExamRevaluationTypeBO> listRO = new ArrayList(impl
				.select_ActiveOnly(ExamRevaluationTypeBO.class));
		return helper.convertBOToTO_RevaluationType(listRO);
	}

	public void add(List<Integer> courseIdList, String individualPass,
			String aggregatePass, String minReqAttendanceWithFine,
			String minReqAttendanceWithoutFine,
			List<ExamRevaluationTO> revaluation, String improvement,
			String supplementaryForFailedSubject, String userId

	) throws Exception {
		ArrayList<ExamSettingCourseRevaluationBO> listRBO = new ArrayList<ExamSettingCourseRevaluationBO>();
		ExamSettingCourseBO objBO;
		impl.isDuplicated(courseIdList);
		int id = 0;
		for (Integer i : courseIdList) {
			objBO = new ExamSettingCourseBO(id, i.intValue(), improvement,
					(individualPass!= null && !individualPass.trim().isEmpty() ?toBigDecimalNew(individualPass): toBigDecimalNew(null)),
					(aggregatePass!= null && !aggregatePass.trim().isEmpty()? toBigDecimalNew(aggregatePass) :toBigDecimalNew(null)) ,
					(minReqAttendanceWithFine!= null && !minReqAttendanceWithFine.trim().isEmpty() ? toBigDecimalNew(minReqAttendanceWithFine) :toBigDecimalNew(null)),
					(minReqAttendanceWithoutFine!= null && !minReqAttendanceWithoutFine.trim().isEmpty()? toBigDecimalNew(minReqAttendanceWithoutFine):toBigDecimalNew(null)),
					supplementaryForFailedSubject, userId);
			objBO.setIsActive(true);
			int course_id = impl.insert(objBO);
			// listEBO.add(objBO);
			Iterator<ExamRevaluationTO> iter = revaluation.iterator();
			while (iter.hasNext()) {
				ExamRevaluationTO examRevaluationTO = (ExamRevaluationTO) iter
						.next();
				listRBO.add(new ExamSettingCourseRevaluationBO(course_id,
						examRevaluationTO.getOptionValue(), examRevaluationTO
								.getId()));
			}
		}

		impl.insert_List(listRBO);

	}

//	private BigDecimal toBigDecimal(String i) {
//		BigDecimal b;
//		try {
//			b = new BigDecimal(i);
//		} catch (Exception e) {
//			b = new BigDecimal(0);
//		}
//		return b;
//	}

	private BigDecimal toBigDecimalNew(String value) throws Exception {
		BigDecimal b = null;
		if (value != null && value.trim().length() > 0) {
			b = new BigDecimal(value);
		}

		return b;
	}

	/**
	 * Modifies InternalCalMarks for a particular id
	 */
	public void update(int id, int courseId, String individualPass,
			String aggregatePass, String minReqAttendanceWithFine,
			String minReqAttendanceWithoutFine,
			List<ExamRevaluationTO> revaluation, String improvement,
			String supplementaryForFailedSubject, String userId)
			throws Exception {

		// Check for duplication if not duplicated then insert else throw error
		// Check for ReActivation
		impl.isDuplicated(courseId, id);

		impl.delete(id);

		ExamSettingCourseBO objBO = new ExamSettingCourseBO(id, courseId,
				improvement, toBigDecimalNew(individualPass),
				toBigDecimalNew(aggregatePass),
				toBigDecimalNew(minReqAttendanceWithFine),
				toBigDecimalNew(minReqAttendanceWithoutFine),
				supplementaryForFailedSubject, userId);
		objBO.setIsActive(true);
		objBO.setCreatedBy(userId);
		impl.update(objBO);

		ArrayList<ExamSettingCourseRevaluationBO> listRBO = new ArrayList<ExamSettingCourseRevaluationBO>();
		Iterator<ExamRevaluationTO> iter = revaluation.iterator();
		while (iter.hasNext()) {
			ExamRevaluationTO examRevaluationTO = (ExamRevaluationTO) iter
					.next();
			if (examRevaluationTO.getOptionValue().length() > 0) {
				listRBO.add(new ExamSettingCourseRevaluationBO(id,
						examRevaluationTO.getOptionValue(), examRevaluationTO
								.getId()));
			}

		}

		if (listRBO.size() > 0) {
			impl.insert_List(listRBO);
		}
	}

	public void delete(int id, String userId) throws Exception {
		impl.delete_IExamGenBO(id, userId, ExamSettingCourseBO.class);
	}

	public void reactivate(int id, String userId) throws Exception {
		impl.reActivate_IExamGenBO(id, userId, ExamSettingCourseBO.class);
	}

	public ExamSettingCourseForm getUpdatableForm(ExamSettingCourseForm form)
			throws Exception {

		form = helper.createFormObject(form, impl.getExamSettingCourseBO(form
				.getId()));

		return form;
	}

}
