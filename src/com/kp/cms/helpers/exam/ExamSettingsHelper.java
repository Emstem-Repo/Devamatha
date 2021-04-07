package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kp.cms.bo.exam.ExamSettingsBO;
import com.kp.cms.forms.exam.ExamSettingsForm;
import com.kp.cms.to.exam.ExamSettingsTO;

/**
 * Dec 25, 2009 Created By 9Elements
 */
public class ExamSettingsHelper {

	public List<ExamSettingsTO> convertBOtoTO(ArrayList<ExamSettingsBO> listBO,
			String organizationName) throws Exception {
		ArrayList<ExamSettingsTO> listTO = new ArrayList<ExamSettingsTO>();
		ExamSettingsTO eTO = null;
		for (ExamSettingsBO bo : listBO) {
			eTO = new ExamSettingsTO();
			eTO.setAbsentCodeMarkEntry(bo.getAbsentCodeMarkEntry());
			eTO.setNotProcedCodeMarkEntry(bo.getNotProcedCodeMarkEntry());
			eTO.setSecuredMarkEntryBy(bo.getSecuredMarkEntryBy());
			eTO.setWithHeldCodeMarkEntry(bo.getWithHeldCodeMarkEntry());
			// eTO.setMaxAllwdDiffPrcntgMultiEvaluator(bo.getMaxAllwdDiffPrcntgMultiEvaluator().toString());
			// eTO.setGradeForFail(bo.getGradeForFail());
			// eTO.setGradePointForFail(bo.getGradePointForFail().toString());
			eTO.setOrganisationName(organizationName);
			eTO.setId(bo.getId());
			listTO.add(eTO);
		}
		Collections.sort(listTO);
		return listTO;

	}

	public ExamSettingsForm createFormObject(ExamSettingsForm form,
			ExamSettingsBO eBO) throws Exception {
		form.setAbsentCodeMarkEntry(eBO.getAbsentCodeMarkEntry());
		form.setWithHeldCodeMarkEntry(eBO.getWithHeldCodeMarkEntry());
		form.setNotProcedCodeMarkEntry(eBO.getNotProcedCodeMarkEntry());
		form.setSecuredMarkEntryBy(eBO.getSecuredMarkEntryBy());
		form.setMaxAllwdDiffPrcntgMultiEvaluator(eBO
				.getMaxAllwdDiffPrcntgMultiEvaluator().toString());
		form.setGradeForFail(eBO.getGradeForFail());
		form.setGradePointForFail(eBO.getGradePointForFail().toString());
		form.setId(eBO.getId());

		form.setOrgAbsentCodeMarkEntry(eBO.getAbsentCodeMarkEntry());
		form.setOrgNotProcedCodeMarkEntry(eBO.getNotProcedCodeMarkEntry());
		form.setOrgSecuredMarkEntryBy(eBO.getSecuredMarkEntryBy());
		form.setOrgMaxAllwdDiffPrcntgMultiEvaluator(eBO
				.getMaxAllwdDiffPrcntgMultiEvaluator().toString());
		form.setOrgGradeForFail(eBO.getGradeForFail());
		form.setOrgGradePointForFail(eBO.getGradePointForFail().toString());

		return form;
	}

}
