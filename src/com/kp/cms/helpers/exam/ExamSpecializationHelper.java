package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.forms.exam.ExamSpecializationMasterForm;
import com.kp.cms.to.exam.ExamSpecializationTO;
/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSpecializationHelper {

	public ExamSpecializationBO convertTOtoBO(int sid,
			String specializationName, int courseId, String createdBy) {
		ExamSpecializationBO objsMBO = new ExamSpecializationBO(sid,
				specializationName, courseId, createdBy, new Date(), createdBy,
				new Date(), true);
		return objsMBO;
	}

	public List<ExamSpecializationTO> convertBOtoTO(
			List<ExamSpecializationBO> assignTypeList) {
		ArrayList<ExamSpecializationTO> listSTO = new ArrayList<ExamSpecializationTO>();
		ExamSpecializationTO sTO = null;
		for (ExamSpecializationBO sBO : assignTypeList) {
			sTO = new ExamSpecializationTO();
			sTO.setId(sBO.getId());
			sTO.setName(sBO.getName());
			sTO.setCourse(sBO.getCourseUtilBO().getCourseName());
			listSTO.add(sTO);
		}
		Collections.sort(listSTO);

		return listSTO;
	}

	public ExamSpecializationTO createBOToTO(ExamSpecializationBO objbo) {
		ExamSpecializationTO objto = new ExamSpecializationTO();
		objto.setId(objbo.getId());
		objto.setName(objbo.getName());
		objto.setCourse(objbo.getCourse());
		return objto;
	}

	public ExamSpecializationMasterForm createFormObjcet(
			ExamSpecializationMasterForm form, ExamSpecializationBO objBO) {
		form.setId(objBO.getId());
		form.setCourseId(Integer.toString(objBO.getCourseId()));
		form.setOrgCourseId(Integer.toString(objBO.getCourseId()));
		form.setSpecializationName(objBO.getName());
		form.setCourseName(objBO.getCourse());
		form.setOrgName(objBO.getName());
		form.setOrgCourseName(objBO.getCourse());
		return form;
	}

}
