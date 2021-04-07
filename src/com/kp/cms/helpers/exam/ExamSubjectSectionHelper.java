package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.exam.ExamSubjectSectionMasterBO;
import com.kp.cms.forms.exam.ExamSubjectSectionForm;
import com.kp.cms.to.exam.ExamSubjectSectionMasterTO;


/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSubjectSectionHelper {

	public ExamSubjectSectionMasterBO convertToBO(int id, String name,
			int isinitialise, String UserID) {

		ExamSubjectSectionMasterBO objssBO = new ExamSubjectSectionMasterBO(id,
				name, isinitialise, UserID, new Date(), UserID, new Date(),
				true);
		return objssBO;
	}

	public List<ExamSubjectSectionMasterTO> convertBOtoTO(
			ArrayList<ExamSubjectSectionMasterBO> listSSBO) {
		ArrayList<ExamSubjectSectionMasterTO> listSSTO = new ArrayList<ExamSubjectSectionMasterTO>();
		ExamSubjectSectionMasterTO ssTO = null;
		for (ExamSubjectSectionMasterBO subjectSectionMasterBO : listSSBO) {
			ssTO = new ExamSubjectSectionMasterTO();
			ssTO.setId(subjectSectionMasterBO.getId());
			ssTO.setName(subjectSectionMasterBO.getName());
			ssTO.setIsinitialise(subjectSectionMasterBO.getIsinitialise());
			listSSTO.add(ssTO);
		}
		
// Modification done by Mary. Sorting list.
		Collections.sort(listSSTO);
		return listSSTO;
	}

	public ExamSubjectSectionMasterTO createBOToTO(
			ExamSubjectSectionMasterBO objbo) {
		ExamSubjectSectionMasterTO objto = new ExamSubjectSectionMasterTO();
		objto.setId(objbo.getId());
		objto.setName(objbo.getName());
		objto.setIsinitialise(objbo.getIsinitialise());

		return objto;
	}

	public ExamSubjectSectionForm createFormObjcet(ExamSubjectSectionForm form,
			ExamSubjectSectionMasterTO objTO) {
		form.setId(objTO.getId());
		form.setName(objTO.getName());
		form.setOrgName(objTO.getName());
		if (objTO.getIsinitialise() == 1) {
			form.setIsinitialise("on");
			form.setOrgIsinitialise("on");
		} else {
			form.setIsinitialise("off");
			form.setOrgIsinitialise("off");
		}
		return form;
	}

}
