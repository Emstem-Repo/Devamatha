package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.Ostermiller.util.StringTokenizer;
import com.kp.cms.bo.exam.ExamPromotionCriteriaBO;
import com.kp.cms.forms.exam.ExamPromotionCriteriaForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.to.exam.ExamPromotionCriteriaTO;

public class ExamPromotionCriteriaHelper extends ExamGenHelper {

	public List<ExamPromotionCriteriaTO> convertBOToTO_ExamPromotionCriteria(
			List<ExamPromotionCriteriaBO> listBO) {
		List<ExamPromotionCriteriaTO> listTO = new ArrayList<ExamPromotionCriteriaTO>();
		ExamGenHandler gen = new ExamGenHandler();
		for (ExamPromotionCriteriaBO bo : listBO) {

			bo.setName(gen.getCourseName(bo.getCourseId()));
			if (bo.getMaxBacklogCountPrcntg().equals("0"))
				bo.setMaxBacklogCountPrcntg(new java.math.BigDecimal(""));
			if (bo.getMaxBacklogNumber() == 0)
				bo.setMaxBacklogNumber(0);
			listTO.add(new ExamPromotionCriteriaTO(bo.getId(), bo.getName(), bo
					.getFromSchemeId(), bo.getToSchemeId(), bo.getScheme(), bo
					.getMaxBacklogCountPrcntg().toString()
					+ "%", bo.getMaxBacklogNumber()));
		}
		Collections.sort(listTO);
		return listTO;
	}

	public ExamPromotionCriteriaForm createFormObjcet(
			ExamPromotionCriteriaForm objform, ExamPromotionCriteriaBO objbo) {
		String a = objbo.getScheme();

		String schemeArray[];

		HashMap<Integer, String> seperator = new HashMap<Integer, String>();

		StringTokenizer a1 = new StringTokenizer(a);

		int i = 1;
		while (a1.hasNext()) {
			String z = a1.nextElement();
			seperator.put(i, z);

			i++;
		}
		Set<Integer> s = seperator.keySet();

		schemeArray = s.toString().split(",");

		objform.setId(objbo.getId());
		objform.setSchemesMap(seperator);
		String courseName = new ExamGenHandler().getCourseName(objbo
				.getCourseId());

		objform.setCourseId(Integer.toString(objbo.getCourseId()));
		objform.setCourseName(courseName);
		objform.setFromScheme(Integer.toString(objbo.getFromSchemeId()));
		objform.setToScheme(Integer.toString(objbo.getToSchemeId()));
		objform.setScheme(schemeArray);
		objform.setBackLogCountPercentage(objbo.getMaxBacklogCountPrcntg()
				.toString());
		objform
				.setBackLogNumbers(Integer
						.toString(objbo.getMaxBacklogNumber()));
		if (objbo.getMaxBacklogNumber() <= 0) {
			objform.setMaxBacklog("percentage");
			objform.setBackLogNumbers("");
		} else {
			objform.setMaxBacklog("number");
			objform.setBackLogCountPercentage("");
		}

		objform.setOriginalCourseId(Integer.toString(objbo.getCourseId()));
		objform
				.setOriginalFromScheme(Integer
						.toString(objbo.getFromSchemeId()));
		objform.setOriginalToScheme(Integer.toString(objbo.getToSchemeId()));

		objform.setOriginalScheme(objbo.getScheme());
		return objform;
	}

}
