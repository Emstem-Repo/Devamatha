package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamPromotionCriteriaBO;
import com.kp.cms.forms.exam.ExamPromotionCriteriaForm;
import com.kp.cms.helpers.exam.ExamPromotionCriteriaHelper;
import com.kp.cms.to.exam.ExamPromotionCriteriaTO;
import com.kp.cms.transactionsimpl.exam.ExamPromotionCriteriaImpl;

public class ExamPromotionCriteriaHandler extends ExamGenHandler {

	ExamPromotionCriteriaImpl impl = new ExamPromotionCriteriaImpl();
	ExamPromotionCriteriaHelper helper = new ExamPromotionCriteriaHelper();

	// To Add
	public void addExamPromotionCriteria(String courseId, String fromSchemeId,
			String toSchemeId, String scheme[], String maxBacklog,
			String maxBacklogType) throws Exception {

		ExamPromotionCriteriaBO objBO = null;
		StringBuilder scheme_str= new StringBuilder();
		for (int i = 0; i < scheme.length; i++) {
			scheme_str.append(" ").append(scheme[i]);
		}
		impl.isDuplicated(Integer.parseInt(courseId), fromSchemeId, toSchemeId,
				scheme_str.toString());
		if (maxBacklog.equalsIgnoreCase("percentage")) {

			BigDecimal maxBCPrcntg = new BigDecimal(maxBacklogType);
			objBO = new ExamPromotionCriteriaBO(Integer.parseInt(courseId),
					Integer.parseInt(fromSchemeId), Integer
							.parseInt(toSchemeId), scheme_str.toString(), maxBCPrcntg);
		} else {
			int maxBCPrcntg = Integer.parseInt(maxBacklogType);
			objBO = new ExamPromotionCriteriaBO(Integer.parseInt(courseId),
					Integer.parseInt(fromSchemeId), Integer
							.parseInt(toSchemeId), scheme_str.toString(), maxBCPrcntg);
		}

		impl.insert(objBO);

	}

	// To Update
	public void update_ExamPromotionCriteria(int id, int courseId,
			int fromSchemeId, int toSchemeId, String scheme[],
			String maxBacklog, String maxBacklogType, String originalcourseid,
			String originalfromscheme, String originaltoscheme,
			String originalscheme) throws Exception {
		StringBuilder scheme_str=new StringBuilder();
		for (int i = 0; i < scheme.length; i++) {
			scheme_str.append(" ").append(scheme[i]);
		}

		if (!(courseId == Integer.parseInt(originalcourseid) && (scheme_str
				.equals(originalscheme)))) {
			impl.isDuplicated(courseId, fromSchemeId, toSchemeId, scheme_str.toString());

		}
		impl.update_ExamPromotionCriteria(id, courseId, fromSchemeId,
				toSchemeId, scheme_str.toString(), maxBacklog, maxBacklogType);

	}

	public boolean deleteExamPromotionCriteria(int id) {
		return impl.delete_ExamPromotionCriteria(id);
	}

	// To get the main list
	public List<ExamPromotionCriteriaTO> getMainList() {
		List<ExamPromotionCriteriaBO> listBO = new ArrayList(impl
				.select_All(ExamPromotionCriteriaBO.class));
		return helper.convertBOToTO_ExamPromotionCriteria(listBO);
	}

	public void getUpdatableForm(ExamPromotionCriteriaForm objForm)
			throws Exception {

		objForm = helper.createFormObjcet(objForm, impl
				.loadExamPromotionCriteria(objForm.getId()));

	}

}
