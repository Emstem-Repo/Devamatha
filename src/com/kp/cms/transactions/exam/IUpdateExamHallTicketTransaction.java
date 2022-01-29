package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.forms.exam.UpdateExamHallTicketForm;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;

public interface IUpdateExamHallTicketTransaction {

	List<Object> getList(String query) throws Exception;

	boolean updatePublishHallTicketList(List<ExamPublishHallTicketTO> finalList,UpdateExamHallTicketForm updateExamHallTicketForm) throws Exception;

}
