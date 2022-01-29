package com.kp.cms.handlers.examallotment;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.examallotment.ExamRoomAvailability;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.forms.examallotment.ExamRoomAvailabilityForm;
import com.kp.cms.helpers.examallotment.ExamRoomAvailabilityHelper;
import com.kp.cms.to.studentfeedback.RoomMasterTo;
import com.kp.cms.transactions.examallotment.IExamRoomAvailabilityTransactions;
import com.kp.cms.transactionsimpl.examallotment.ExamRoomAvailabilityTxnImpln;
import com.kp.cms.utilities.PropertyUtil;

public class ExamRoomAvailabilityHandler {
private static volatile ExamRoomAvailabilityHandler handler = null;
public static ExamRoomAvailabilityHandler getInstance(){
	if(handler == null){
		handler = new ExamRoomAvailabilityHandler();
	}
	return handler;
}
IExamRoomAvailabilityTransactions transaction = ExamRoomAvailabilityTxnImpln.getInstance();
/**
 * @return
 * @throws Exception
 */
	public Map<Integer, String> getWorkLocation() throws Exception {
		String getWorkLocationQuery = ExamRoomAvailabilityHelper.getInstance() .getWorkLocationQuery();
		return transaction.getWorkLocationDetails(getWorkLocationQuery);
	}
/**
 * @param objForm
 * @return
 * @throws Exception
 */
	public List<RoomMasterTo> getRoomAvailability( ExamRoomAvailabilityForm objForm) throws Exception {
		String hqlQuery = ExamRoomAvailabilityHelper.getInstance() .getRoomAvailabilityDetailsQuery(Integer.parseInt(objForm.getWorkLocatId()));
		List<RoomMaster> roomMasterBoList = PropertyUtil.getInstance() .getListOfData(hqlQuery);
		String hqlQuery1 = ExamRoomAvailabilityHelper.getInstance().getAlreadyAllotedRoomsQuery(Integer.parseInt(objForm.getWorkLocatId()));
		Map<Integer,Integer> roomIdsList = transaction.getExamRoomAvailabilityRoomList(hqlQuery1);
		List<RoomMasterTo> roomMasterTos = ExamRoomAvailabilityHelper .getInstance().convertBoListToList(roomMasterBoList,roomIdsList);
		int halfLength = 0;
		int totLength = roomMasterTos.size();
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		objForm.setHalfLength(halfLength);
		return roomMasterTos;
}
/**
 * @param objForm
 * @return
 * @throws Exception
 */
public boolean saveRoomDetails(ExamRoomAvailabilityForm objForm) throws Exception{
	List<ExamRoomAvailability> roomAvailabilitiesList = ExamRoomAvailabilityHelper.getInstance().getSelectedRoomBoList(objForm);
	return transaction.saveList(roomAvailabilitiesList);
}
}
