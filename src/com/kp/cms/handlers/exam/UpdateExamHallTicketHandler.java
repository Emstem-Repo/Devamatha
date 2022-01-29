package com.kp.cms.handlers.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.forms.exam.UpdateExamHallTicketForm;
import com.kp.cms.helpers.exam.ExamPublishHallTicketHelper;
import com.kp.cms.helpers.exam.UpdateExamHallTicketHelper;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;
import com.kp.cms.transactions.exam.IUpdateExamHallTicketTransaction;
import com.kp.cms.transactionsimpl.employee.UpdateExamHallTicketTransactionImpl;

public class UpdateExamHallTicketHandler {
	/**
	 * Singleton object of UpdateExamHallTicketHandler
	 */
	private static volatile UpdateExamHallTicketHandler updateExamHallTicketHandler = null;
	IUpdateExamHallTicketTransaction transaction=new UpdateExamHallTicketTransactionImpl();
	private static final Log log = LogFactory.getLog(UpdateExamHallTicketHandler.class);
	private UpdateExamHallTicketHandler() {
		
	}
	/**
	 * return singleton object of UpdateExamHallTicketHandler.
	 * @return
	 */
	public static UpdateExamHallTicketHandler getInstance() {
		if (updateExamHallTicketHandler == null) {
			updateExamHallTicketHandler = new UpdateExamHallTicketHandler();
		}
		return updateExamHallTicketHandler;
	}
	/**
	 * @param updateExamHallTicketForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamPublishHallTicketTO> getListOfCandidates(UpdateExamHallTicketForm updateExamHallTicketForm) throws Exception {
		String query=UpdateExamHallTicketHelper.getInstance().getSearchQuery(updateExamHallTicketForm);
		List<Object> list=transaction.getList(query);
		ExamPublishHallTicketHelper helper = new ExamPublishHallTicketHelper();
		return  helper.convertBOToTO_mainList(list);
	}
	/**
	 * @param selectedCandidates
	 * @param updateExamHallTicketForm
	 * @return
	 * @throws Exception
	 */
	public boolean updatePublishHallTicket(List<ExamPublishHallTicketTO> selectedCandidates,UpdateExamHallTicketForm updateExamHallTicketForm) throws Exception {
		List<ExamPublishHallTicketTO> finalList=UpdateExamHallTicketHelper.getInstance().getfinalList(selectedCandidates);
		return transaction.updatePublishHallTicketList(finalList,updateExamHallTicketForm);
	}
}
