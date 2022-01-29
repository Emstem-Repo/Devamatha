package com.kp.cms.handlers.reports;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.forms.reports.SectionWiseForm;
import com.kp.cms.helpers.reports.SectionWiseReportHelper;
import com.kp.cms.to.reports.SectionWiseReportTO;
import com.kp.cms.transactions.reports.ISectionWiseReportTransaction;
import com.kp.cms.transactionsimpl.reports.SectionWiseTransactionImpl;

public class SectionWiseReportHandler {

	private static final Log log = LogFactory.getLog(SectionWiseReportHandler.class);
	
	public static volatile SectionWiseReportHandler wiseReportHandler = null;
	
	/**
	 * This method is used to create a single instance of SectionWiseReportHandler.
	 * @return unique instance of SectionWiseReportHandler class.
	 */
	
	public static SectionWiseReportHandler getInstance() {
		if (wiseReportHandler == null) {
			wiseReportHandler = new SectionWiseReportHandler();
			return wiseReportHandler;
		}
		return wiseReportHandler;
	}
	
	public Map<Integer,List<SectionWiseReportTO>> getSectionWiseReportDetails(SectionWiseForm sectionWiseForm) throws Exception{
		log.info("entered getSectionWiseReportDetails method of SectionWiseReportHandler class.");
		ISectionWiseReportTransaction transaction = SectionWiseTransactionImpl.getInstance();
		List<Object[]> list = transaction.getSectionWiseReportDetails(SectionWiseReportHelper.getSelectionSearchCriteria(sectionWiseForm));
		Map<Integer,ExamStudentDetentionRejoinDetails> detantionMap=transaction.getAllStudentDetentionDetails();
		if(list != null && !list.isEmpty()){
			Map<Integer,List<SectionWiseReportTO>> studentsMap = SectionWiseReportHelper.convertBOstoTOs(list,sectionWiseForm,detantionMap);
			return studentsMap;
		}
		log.info("Exit of getSectionWiseReportDetails method of SectionWiseReportHandler class.");
		return new LinkedHashMap<Integer,List<SectionWiseReportTO>>();
	}
}
