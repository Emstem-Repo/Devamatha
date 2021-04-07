package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.exam.ExamRoomTypeBO;
import com.kp.cms.helpers.exam.ExamRoomTypeHelper;
import com.kp.cms.transactionsimpl.exam.ExamRoomTypeImpl;

public class ExamRoomTypeHandler {
	ExamRoomTypeHelper objExamHelper = new ExamRoomTypeHelper();

	ExamRoomTypeImpl impl = new ExamRoomTypeImpl();

	public List<ExamRoomTypeBO> getERTList() {
		List<ExamRoomTypeBO> ERTList = new ArrayList(impl
				.select_ActiveOnly("ExamRoomTypeBO"));
		return ERTList;

	}

	public void addERT(String examRoomTypeName, String examRoomTypeDesc,
			String userId) throws Exception {
		int eRTID = 0;

		// Check for duplication if not duplicated then insert else throw error
		// Check for ReActivation
		if (impl
				.isDuplicated_IExamGenBO(examRoomTypeName, ExamRoomTypeBO.class)) {
			ExamRoomTypeBO objERTBO = objExamHelper.convertERTTOtoBO(eRTID,
					examRoomTypeName, examRoomTypeDesc, userId, new Date(),
					new Date(), userId, true);
			impl.insert(objERTBO);
		}
	}

	/**
	 * Modifies Assignment type master for a particular id
	 */

	public void updateERT(int eRTID, String eRTName, String eRTDesc,
			String userId, String origERTName) throws Exception {
		// Check for duplication if not duplicated then insert else throw
		// error
		if (impl.isDuplicated(eRTName, eRTID)) {
			impl.updateExamRoomType(eRTID, eRTName, eRTDesc, userId);
		}
		// }
	}

	/**
	 * delete Assignment type master for a particular id IsActive flag is set to
	 * 0/false
	 */
	public void deleteERT(int eRTID, String userId) throws Exception {
		impl.delete_IExamGenBO(eRTID, userId, ExamRoomTypeBO.class);
	}

	public void reActivateERT(String examRoomTypeName, String userId)
			throws Exception {
		impl.reActivate_IExamGenBO(examRoomTypeName, userId,
				ExamRoomTypeBO.class);
	}
}
