package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.forms.exam.ExamRoomMasterForm;
import com.kp.cms.to.exam.ExamRoomMasterTO;

public class ExamRoomMasterHelper {

	// public ExamRoomMasterBO convertFormToBO(ExamRoomMasterForm objERMForm) {
	// int alloted = 0;
	// ExamRoomMasterBO objERMBO = new ExamRoomMasterBO();
	//
	// objERMBO = new ExamRoomMasterBO(objERMForm.getId(), objERMForm
	// .getRoomTypeId(), objERMForm.getRoomNo(), objERMForm
	// .getRoomCapacity(), objERMForm.getComments(), alloted,
	// objERMForm.getRoomCapacityForExams(), objERMForm
	// .getRoomCapacityForInternalExams(), objERMForm
	// .getFloorNo(), objERMForm.getUserId(), new Date(),
	// new Date(), objERMForm.getUserId(), true);
	//
	// return objERMBO;
	// }

	public ExamRoomMasterBO createBoObjcet(ExamRoomMasterForm form,
			boolean shouldAdd) {

		ExamRoomMasterBO objERMBO = new ExamRoomMasterBO();
		objERMBO.setId(form.getId());
		objERMBO.setRoomNo(form.getRoomNo());
		objERMBO.setRoomCapacity(form.getRoomCapacity());
		objERMBO.setExamCapacity(form.getRoomCapacityForExams());
		objERMBO.setFloorNo(form.getFloorNo());
		if (form.getComments().length() > 0) {
			objERMBO.setComments(form.getComments());
		}
		objERMBO
				.setInternalExamCapacity(form.getRoomCapacityForInternalExams());
		objERMBO.setRoomTypeId(form.getRoomTypeId());
		objERMBO.setIsActive(true);
		if (shouldAdd) {
			objERMBO.setCreatedBy(form.getUserId());
			objERMBO.setModifiedBy(form.getUserId());
			objERMBO.setCreatedDate(new Date());
		} else {
			objERMBO.setModifiedBy(form.getUserId());
			objERMBO.setLastModifiedDate(new Date());
		}
		/* code added by sudhir */
		objERMBO.setBlockNo(form.getBlockNo());
		/* --------------------*/
		return objERMBO;
	}

	public ExamRoomMasterTO createBOToTO(ExamRoomMasterBO objERMBO) {

		ExamRoomMasterTO eTO = new ExamRoomMasterTO();
		eTO.setId(objERMBO.getId());
		eTO.setRoomTypeId(objERMBO.getExamRoomTypeBO().getId());
		eTO.setRoomType(objERMBO.getExamRoomTypeBO().getName());
		eTO.setRoomNo(objERMBO.getRoomNo());
		eTO.setRoomCapacity(objERMBO.getRoomCapacity());
		eTO.setExamCapacity(objERMBO.getExamCapacity());
		eTO.setFloorNo(objERMBO.getFloorNo());
		eTO.setComments(objERMBO.getComments());

		eTO.setInternalExamCapacity(objERMBO.getInternalExamCapacity());
		/* code added by sudhir */
		eTO.setBlockNo(objERMBO.getBlockNo());
		return eTO;
	}

	public List<ExamRoomMasterTO> createBOToTO(List<ExamRoomMasterBO> listEMBO) {

		List<ExamRoomMasterTO> listETO = new ArrayList<ExamRoomMasterTO>();

		Iterator<ExamRoomMasterBO> itr = listEMBO.iterator();
		ExamRoomMasterBO objERMBO = null;
		ExamRoomMasterTO eTO = null;

		while (itr.hasNext()) {
			objERMBO = itr.next();

			eTO = new ExamRoomMasterTO();
			eTO.setId(objERMBO.getId());
			eTO.setRoomTypeId(objERMBO.getExamRoomTypeBO().getId());
			eTO.setRoomType(objERMBO.getExamRoomTypeBO().getName());
			eTO.setRoomNo(objERMBO.getRoomNo());
			eTO.setRoomCapacity(objERMBO.getRoomCapacity());
			eTO.setExamCapacity(objERMBO.getExamCapacity());
			eTO.setFloorNo(objERMBO.getFloorNo());
			eTO.setComments(objERMBO.getComments());
			eTO.setInternalExamCapacity(objERMBO.getInternalExamCapacity());
			/* code added by sudhir */
			eTO.setBlockNo(objERMBO.getBlockNo());
			/* --------------------*/
			listETO.add(eTO);
		}
		Collections.sort(listETO);
		return listETO;
	}

	public ExamRoomMasterForm createFormObjcet(ExamRoomMasterForm form,
			ExamRoomMasterTO eTO) {

		form.setId(eTO.getId());
		form.setRoomNo(eTO.getRoomNo());
		form.setRoomCapacity(eTO.getRoomCapacity());
		form.setRoomCapacityForExams(eTO.getExamCapacity());
		form.setRoomCapacityForInternalExams(eTO.getInternalExamCapacity());
		form.setFloorNo(eTO.getFloorNo());
		form.setComments(eTO.getComments());
		form.setRoomTypeId(eTO.getRoomTypeId());
		form.setRoomType(eTO.getRoomType());
		/* code added by sudhir */
		form.setBlockNo(eTO.getBlockNo());
		return form;
	}
}
