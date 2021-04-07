package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.bo.exam.ExamRoomTypeBO;
import com.kp.cms.forms.exam.ExamRoomMasterForm;
import com.kp.cms.helpers.exam.ExamRoomMasterHelper;
import com.kp.cms.to.exam.ExamRoomMasterTO;
import com.kp.cms.transactionsimpl.exam.ExamRoomMasterImpl;

public class ExamRoomMasterHandler {
	ExamRoomMasterHelper helper = new ExamRoomMasterHelper();

	ExamRoomMasterImpl impl = new ExamRoomMasterImpl();

	public List<ExamRoomMasterTO> getERMList() throws Exception {

		List<ExamRoomMasterBO> ERMList = new ArrayList(impl
				.select_ActiveOnly("ExamRoomMasterBO"));
		return helper.createBOToTO(ERMList);
	}

	// public void addERT(String examRoomTypeName, String examRoomTypeDesc,
	// String userId) throws Exception {
	// int eRTID = 0;
	//
	// // Check for duplication if not duplicated then insert else throw error
	// // Check for ReActivation
	// if (impl
	// .isDuplicated_IExamGenBO(examRoomTypeName, ExamRoomTypeBO.class)) {
	// ExamRoomTypeBO objERTBO = objExamHelper.convertERTTOtoBO(eRTID,
	// examRoomTypeName, examRoomTypeDesc, userId, new Date(),
	// new Date(), userId, true);
	// impl.insert(objERTBO);
	// }
	// }
	//
	// /**
	// * Modifies Assignment type master for a particular id
	// */
	//
	// public void updateERT(int eRTID, String eRTName, String eRTDesc,
	// String userId, String origERTName) throws Exception {
	// // Check for duplication if not duplicated then insert else throw
	// // error
	// if (impl.isDuplicated(eRTName, eRTID)) {
	// impl.updateExamRoomType(eRTID, eRTName, eRTDesc, userId);
	// }
	// // }
	// }

	public void add(ExamRoomMasterForm objERMForm, boolean shouldAdd)
			throws Exception {
		ExamRoomMasterBO objERMBO = helper
				.createBoObjcet(objERMForm, shouldAdd);
		// Check for duplication if not duplicated then insert else throw error
		// Check for ReActivation
		if (!impl.isDuplicated_add(objERMBO.getRoomNo(), objERMForm
				.getRoomTypeId())) {

			impl.insert(objERMBO);

		}
	}

	public void update(ExamRoomMasterForm objERMForm, boolean shouldAdd)
			throws Exception {
		ExamRoomMasterBO objERMBO = helper
				.createBoObjcet(objERMForm, shouldAdd);
	// Check for duplication if not duplicated then insert else throw error
		// Check for ReActivation
		if (!impl.isDuplicated(objERMBO.getRoomNo(),
				objERMForm.getRoomTypeId(), objERMForm.getId())) {
			impl.update(objERMBO);

		}
	}

	public ExamRoomMasterForm getUpdatableForm(ExamRoomMasterForm form,
			String mode) throws Exception {
		form = helper.createFormObjcet(form, helper.createBOToTO(impl
				.loadRoomMaster(form.getId())));

		return form;
	}

	public boolean delete(int id, String userId) throws Exception {
		return impl.delete_IExamGenBO(id, userId, ExamRoomMasterBO.class);
	}

	public boolean reactivation(int id, String userId) throws Exception {
		return impl.reActivate_IExamGenBO(id, userId, ExamRoomMasterBO.class);
	}

}
