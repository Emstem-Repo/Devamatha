package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.helpers.reports.CialOverAllReportHelper;
import com.kp.cms.to.reports.CiaOverAllReportTO;
import com.kp.cms.transactions.reports.ICiaOverallReportTxn;
import com.kp.cms.transactionsimpl.reports.CiaOverallReportTxnImpl;

public class CiaOverallReportHandler {
	private static final Log log = LogFactory.getLog(CiaOverallReportHandler.class);
	private static volatile CiaOverallReportHandler cialOverallReportHandler = null;
	

	public static CiaOverallReportHandler getInstance() {
		if (cialOverallReportHandler == null) {
			cialOverallReportHandler = new CiaOverallReportHandler();
		}
		return cialOverallReportHandler;
	}
	
	public List<CiaOverAllReportTO> getCiaOverAllMarks(int studentId, int classId, int examId) throws Exception{
		log.info("Entering into getCiaOverAllMarks in Handler");
		ICiaOverallReportTxn transaction = new CiaOverallReportTxnImpl();
		List<ExamStudentOverallInternalMarkDetailsBO> overAllMarksList = transaction.getStudentWiseOverAllExamMarkDetails(studentId, classId, examId);
		List<CiaOverAllReportTO> overAllMarksTOList = CialOverAllReportHelper.getInstance().convertBoListToTOList(overAllMarksList);
		log.info("Leaving getCiaOverAllMarks in Handler");
		return overAllMarksTOList;
	}
	/**
	 * 
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	public int getExamIdByClassId(int classId) throws Exception{
		ICiaOverallReportTxn transaction = new CiaOverallReportTxnImpl();
		return transaction.getExamIdByClassId(classId);
	}

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public int getClassId(int studentId) throws Exception {
		ICiaOverallReportTxn transaction = new CiaOverallReportTxnImpl();
		return transaction.getClassId(studentId);
	}
	/**
	 * 
	 * @param studentId
	 * @param curClassId
	 * @return
	 * @throws Exception
	 */
	public ExamPublishExamResultsBO getPublishedClassId(int studentId, int curClassId) throws Exception {
		ICiaOverallReportTxn transaction = new CiaOverallReportTxnImpl();
		return transaction.getClassIds(studentId, curClassId);
	}
	
}
