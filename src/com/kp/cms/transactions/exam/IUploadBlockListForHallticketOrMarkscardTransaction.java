package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.forms.exam.UploadBlockListForHallticketOrMarkscardForm;

public interface IUploadBlockListForHallticketOrMarkscardTransaction {
	public	Map<String,Integer> getClassIdByClassNameAndYear(String year) throws Exception;
	public	Map<String,Integer> getStudentIdByStudentRegNum(String year) throws Exception;
	boolean uploadBlockListForHallticketOrMarkscard(Map<Integer,ExamBlockUnblockHallTicketBO>  bo,UploadBlockListForHallticketOrMarkscardForm objform ) throws Exception;
	public	ExamBlockUnblockHallTicketBO getExamBo(ExamBlockUnblockHallTicketBO list) throws Exception;
	public int getStudentRegisterNo(int studentId)throws Exception;
	public boolean getStudentIdMapByClassId(int classId,int studentId)throws Exception;
}
