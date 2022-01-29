package com.kp.cms.handlers.examallotment;

import java.util.List;

import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.forms.examallotment.RoomAllotmentStatusForm;
import com.kp.cms.helpers.examallotment.ExamRoomAllotmentStatusHelper;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentStatusTransactions;
import com.kp.cms.transactionsimpl.examallotment.ExamRoomAllotmentStatusTxnImpl;

public class ExamRoomAllotmentStatusHandler {
	private static volatile ExamRoomAllotmentStatusHandler handler =null;
	private ExamRoomAllotmentStatusHandler(){
	
	}
	public static ExamRoomAllotmentStatusHandler getInstance(){
		if(handler == null){
			handler = new ExamRoomAllotmentStatusHandler();
		}
		return handler;
	}
	IExamRoomAllotmentStatusTransactions transaction= ExamRoomAllotmentStatusTxnImpl.getInstance();
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public List<RoomAllotmentStatusDetailsTo> getRoomsAllotedForCycle( RoomAllotmentStatusForm objForm) throws Exception{
		String hqlQuery = ExamRoomAllotmentStatusHelper.getInstance().getRoomsAllotedForCycleQuery(objForm);
		List<Object[]> examRoomAllotmentBOList = transaction.getRoomsAllotedForCycle(hqlQuery);
		List<RoomAllotmentStatusDetailsTo> roomAllotmentToList= ExamRoomAllotmentStatusHelper.getInstance().convertBoListTOToList(examRoomAllotmentBOList,objForm);
		return roomAllotmentToList;
	}
}
