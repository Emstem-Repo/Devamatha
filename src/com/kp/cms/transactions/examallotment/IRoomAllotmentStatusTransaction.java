package com.kp.cms.transactions.examallotment;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;
import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.forms.examallotment.RoomAllotmentStatusForm;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;

public interface IRoomAllotmentStatusTransaction {

	public List<ExamDefinitionBO> getExamNameByAcademicYear(int year) throws Exception;

	public List<ExamRoomAllotmentCycle> getCycleByMidOrEnd(String midOrEnd) throws Exception;

	public List<RoomMaster> getRoomNoByWorkLocationId(int workLocationId) throws Exception;

	public List<ExamRoomAllotmentDetails> getStudentDetailsByRoom(RoomAllotmentStatusForm allotmentStatusForm)throws Exception;

	public ExamRoomAllotmentDetails checkStudentAlreadyAllocatedByRegNo(String registerNo,RoomAllotmentStatusForm allotmentStatusForm)throws Exception;

	public Student getStudentDetailsByRegNo(String registerNo)throws Exception;

	public boolean updateStudentDetailsForAllotment(List<ExamRoomAllotmentDetails> allotmentDetailsList)throws Exception;

	public ExamRoomAllotmentDetails getDataFromOrigRegNo(RoomAllotmentStatusDetailsTo statusDetailsTo ,RoomAllotmentStatusForm allotmentStatusForm)throws Exception;

	public List<RoomEndMidSemRows> getRoomById(int roomId,String endMidSem)throws Exception;

	public ExamRoomAllotment getRoomAllotment( RoomAllotmentStatusForm allotmentStatusForm)throws Exception;

	public int getMaxRowCount(int roomId, String midEndSem)throws Exception;

}
