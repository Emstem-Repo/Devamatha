package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.forms.exam.BlockHallTicketProcessForm;
import com.kp.cms.helpers.exam.BlockHallTicketProcessHelper;
import com.kp.cms.to.reports.StudentsAttendanceReportTO;
import com.kp.cms.transactions.exam.IBlockHallTicketProcessTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.BlockHallTicketProcessTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class BlockHallTicketProcessHandler {
	/**
	 * Singleton object of BlockHallTicketProcessHandler
	 */
	private static volatile BlockHallTicketProcessHandler blockHallTicketProcessHandler = null;
	private static final Log log = LogFactory.getLog(BlockHallTicketProcessHandler.class);
	private BlockHallTicketProcessHandler() {
		
	}
	/**
	 * return singleton object of BlockHallTicketProcessHandler.
	 * @return
	 */
	public static BlockHallTicketProcessHandler getInstance() {
		if (blockHallTicketProcessHandler == null) {
			blockHallTicketProcessHandler = new BlockHallTicketProcessHandler();
		}
		return blockHallTicketProcessHandler;
	}
	/**
	 * @param blockHallTicketProcessForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentsAttendanceReportTO> getListOfCandidates( BlockHallTicketProcessForm blockHallTicketProcessForm) throws Exception {
		log.info("Entered in to getListOfCandidates");
		String query=BlockHallTicketProcessHelper.getInstance().getClassesQueryForExam(blockHallTicketProcessForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		IBlockHallTicketProcessTransaction transaction2=BlockHallTicketProcessTransactionImpl.getInstance();
		List<Integer> classesList=transaction.getDataForQuery(query);
		String blockQuery=BlockHallTicketProcessHelper.getInstance().blockQuery(blockHallTicketProcessForm);
		List<Object[]> blockList=transaction.getDataForQuery(blockQuery);
		Map<Integer,ExamBlockUnblockHallTicketBO> blockMap=BlockHallTicketProcessHelper.getInstance().convertBotoMap(blockList);
		List<Object[]> list=new ArrayList<Object[]>();
		if (!classesList.isEmpty()) {
			list=transaction2.getAttendanceForClasses(classesList);
		}
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
//		Map<Integer,Double> leaveMap=transaction2.getLeaveMap(classesList);
		log.info("Exit From getListOfCandidates");
		return BlockHallTicketProcessHelper.getInstance().convertBoListtoToList(list,blockHallTicketProcessForm,blockMap,listOfDetainedStudents);
	}
	/**
	 * @param blockHallTicketProcessForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveBlockHallTicketProcess( BlockHallTicketProcessForm blockHallTicketProcessForm) throws Exception {
		List<ExamBlockUnblockHallTicketBO> hallTicketList=BlockHallTicketProcessHelper.getInstance().convertTOtoBOList(blockHallTicketProcessForm);
		IBlockHallTicketProcessTransaction transaction2=BlockHallTicketProcessTransactionImpl.getInstance();
		return transaction2.saveHallTickets(hallTicketList);
	}
}
