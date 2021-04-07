package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamSubjectSectionMasterBO;
import com.kp.cms.forms.exam.ExamSubjectSectionForm;
import com.kp.cms.helpers.exam.ExamSubjectSectionHelper;
import com.kp.cms.to.exam.ExamSubjectSectionMasterTO;
import com.kp.cms.transactionsimpl.exam.ExamSubjectSectionImpl;

/**
 * Dec 14, 2009 Created By 9Elements
 */
@SuppressWarnings("unchecked")
public class ExamSubjectSectionHandler {
	ExamSubjectSectionImpl impl = new ExamSubjectSectionImpl();
	ExamSubjectSectionHelper helper = new ExamSubjectSectionHelper();

	
	public List<ExamSubjectSectionMasterTO> getSubjectSectionType()
			throws Exception {
		ArrayList<ExamSubjectSectionMasterBO> listSSBO = new ArrayList(impl
				.select_ActiveOnly(ExamSubjectSectionMasterBO.class));
		List<ExamSubjectSectionMasterTO> listSS = helper
				.convertBOtoTO(listSSBO);
		return listSS;
	}

	public void addSSMaster(String name, int isInitialise, String userId)
			throws Exception {
		ExamSubjectSectionMasterBO objSSBO = helper.convertToBO(0, name,
				isInitialise, userId);
		// Check for duplication if not duplicated then insert else throw error
		// Check for ReActivation
		if (!impl.isDuplicated(0, name)) {
			impl.insert(objSSBO);
		}
	}

	/**
	 * Modifies Subject Selection master for a particular id
	 */
	public void updateSubjectSelection(int id, String name, int isinitialise,
			String userId) throws Exception {

		// Check for duplication if not duplicated then insert else throw
		if (!impl.isDuplicated(id, name)) {
			impl.update(id, name, isinitialise, userId);
		}
	}

	public void deleteSubjectSection(int id, String userId) throws Exception {
		impl.delete(id, userId);
	}

	public void updateReactivateSSMaster(String name, String userId)
			throws Exception {
		impl.reActivateSS(name, userId);
	}
	public ExamSubjectSectionForm getUpdatableForm(ExamSubjectSectionForm form,
			String mode) throws Exception {
		form = helper.createFormObjcet(form, helper.createBOToTO(impl
				.loadSSMaster(form.getId())));
		return form;
	}

	
}
