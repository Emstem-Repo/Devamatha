package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;

public interface IBlockHallTicketProcessTransaction {

	List<Object[]> getAttendanceForClasses(List<Integer> classesList) throws Exception;

	boolean saveHallTickets(List<ExamBlockUnblockHallTicketBO> hallTicketList) throws Exception;

	Map<Integer, Double> getLeaveMap(List<Integer> classesList) throws Exception;

}
