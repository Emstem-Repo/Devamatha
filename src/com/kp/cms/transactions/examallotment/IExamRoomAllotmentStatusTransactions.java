package com.kp.cms.transactions.examallotment;

import java.util.List;

import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;

public interface IExamRoomAllotmentStatusTransactions {

	public List<Object[]> getRoomsAllotedForCycle(String hqlQuery)throws Exception;

}
