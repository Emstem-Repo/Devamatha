package com.kp.cms.handlers.examallotment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;
import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.forms.examallotment.RoomAllotmentStatusForm;
import com.kp.cms.helpers.examallotment.RoomAllotmentStatusHelper;
import com.kp.cms.to.examallotment.RoomAllotmentStatusTo;
import com.kp.cms.transactions.examallotment.IRoomAllotmentStatusTransaction;
import com.kp.cms.transactionsimpl.examallotment.RoomAllotmentStatusTxnImpl;

public class RoomAllotmentStatusHandler {
  
	private static volatile RoomAllotmentStatusHandler allotmentStatusHandler=null;
	 
	
	/**
	 * @return
	 */
	public static RoomAllotmentStatusHandler getInstance(){
		if(allotmentStatusHandler == null){
			allotmentStatusHandler=new RoomAllotmentStatusHandler();
		}
		return allotmentStatusHandler;
	}
	
	public RoomAllotmentStatusHandler() {

	}

	/**
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getExamNameByAcademicYear(int year) throws Exception {
		IRoomAllotmentStatusTransaction transaction=RoomAllotmentStatusTxnImpl.getInstance();
		Map<Integer, String> examMap=new HashMap<Integer, String>();
		List<ExamDefinitionBO> examDefinitionList=transaction.getExamNameByAcademicYear(year);
		if(examDefinitionList!=null && !examDefinitionList.isEmpty()){
			for (ExamDefinitionBO examDefinitionBO : examDefinitionList) {
				examMap.put(examDefinitionBO.getId(), examDefinitionBO.getName());
				
			}
		}
		return examMap;
		
	}

	/**
	 * @param midOrEnd
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCycleByMidOrEnd(String midOrEnd) throws Exception {
		IRoomAllotmentStatusTransaction transaction=RoomAllotmentStatusTxnImpl.getInstance();
		Map<Integer, String> cycleMap=new LinkedHashMap<Integer, String>();
		List<ExamRoomAllotmentCycle> allotmentCycleList=transaction.getCycleByMidOrEnd(midOrEnd);
		if(allotmentCycleList!=null && !allotmentCycleList.isEmpty()){
			for (ExamRoomAllotmentCycle allotmentCycle : allotmentCycleList) {
				cycleMap.put(allotmentCycle.getId(), allotmentCycle.getCycle());
				
			}
		}
		return cycleMap;
		
	}

	/**
	 * @param workLocationId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getRoomNoByWorkLocationId(int workLocationId) throws Exception {
		IRoomAllotmentStatusTransaction transaction=RoomAllotmentStatusTxnImpl.getInstance();
		Map<Integer, String> roomMasterMap=new LinkedHashMap<Integer, String>();
		List<RoomMaster> roomMasterList=transaction.getRoomNoByWorkLocationId(workLocationId);
		if(roomMasterList!=null && !roomMasterList.isEmpty()){
			for (RoomMaster roomMaster : roomMasterList) {
				roomMasterMap.put(roomMaster.getId(), roomMaster.getRoomNo());
			}
		}
		return roomMasterMap;		
	}

	public  List<RoomAllotmentStatusTo> getStudentDetailsAndDisplayByRoom(RoomAllotmentStatusForm allotmentStatusForm) throws Exception {
		IRoomAllotmentStatusTransaction transaction=RoomAllotmentStatusTxnImpl.getInstance();
		int roomId = Integer.parseInt(allotmentStatusForm.getRoomNo());
		List<RoomEndMidSemRows> endMidSemRows = transaction.getRoomById(roomId,allotmentStatusForm.getMidEndSem());
		int maxRowCount = transaction.getMaxRowCount(roomId,allotmentStatusForm.getMidEndSem());
		List<ExamRoomAllotmentDetails> roomAllotmentDetailsList=transaction.getStudentDetailsByRoom(allotmentStatusForm);
		return RoomAllotmentStatusHelper.getInstance().convertBoToToList(roomAllotmentDetailsList,endMidSemRows,allotmentStatusForm,maxRowCount);
	}

	public boolean updateRegNoAndStudentForAllotedRoom(RoomAllotmentStatusForm allotmentStatusForm,ActionErrors errors) throws Exception {
		boolean isUpdated=false;
		IRoomAllotmentStatusTransaction transaction=RoomAllotmentStatusTxnImpl.getInstance();
		List<ExamRoomAllotmentDetails> allotmentDetailsList=RoomAllotmentStatusHelper.getInstance().checkWhichRegNoToUpdate(allotmentStatusForm,errors);
		if(errors.isEmpty()){
			isUpdated=transaction.updateStudentDetailsForAllotment(allotmentDetailsList);
		}else{
			isUpdated=false;
		}
		return isUpdated;
	}

	public List<RoomAllotmentStatusTo> getRoomBasedDetails( RoomAllotmentStatusForm allotmentStatusForm) throws Exception{
		IRoomAllotmentStatusTransaction transaction=RoomAllotmentStatusTxnImpl.getInstance();
		
		
		return null;
	}
}
