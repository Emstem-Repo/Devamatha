package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.forms.exam.ExamSpecializationMasterForm;
import com.kp.cms.helpers.exam.ExamSpecializationHelper;
import com.kp.cms.to.exam.ExamSpecializationTO;
import com.kp.cms.transactionsimpl.exam.ExamSpecializationImpl;
/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSpecializationHandler extends ExamGenHandler {
	ExamSpecializationImpl impl = new ExamSpecializationImpl();
	ExamSpecializationHelper helper = new ExamSpecializationHelper();

	public List<ExamSpecializationTO> getSpecializationType() throws Exception {
		List<ExamSpecializationBO> AssignTypeList = new ArrayList(impl
				.select_ActiveOnly(ExamSpecializationBO.class));
		List<ExamSpecializationTO> SPList = helper
				.convertBOtoTO(AssignTypeList);
		return SPList;

	}

	public void addSMaster(String specializationName, int courseId,
			String userId) throws Exception {
		int sID = 0;
		ExamSpecializationBO objbo = helper.convertTOtoBO(sID,
				specializationName, courseId, userId);
		// Check for duplication if not duplicated then insert else throw error
		// Check for ReActivation

		if (!impl.isSSDuplicated(0,specializationName, courseId)) {
			impl.insert(objbo);

		}

	}

	public void updateSMaster(int id, String specializationName, int courseId,
			String userId) throws Exception {
		if (!impl.isSSDuplicated(id,specializationName, courseId)) {
			impl.updateSpecilizaction(id,specializationName,courseId,userId);

		}
	}

	public void deleteSpelizaction(int id, String userId) throws Exception {
		impl.delete(id, userId);
	}

	public void reactivateSpecialization(int id, String userId)
			throws Exception {
		impl.reActivate_IExamGenBO(id, userId,ExamSpecializationBO.class);
	}

	public ExamSpecializationMasterForm getUpdatableForm(
			ExamSpecializationMasterForm form, String mode) throws Exception {
		form = helper.createFormObjcet(form, impl.loadSSMaster(form.getId()));
		return form;
	}
}
