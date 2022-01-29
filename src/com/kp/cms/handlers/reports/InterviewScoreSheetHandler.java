package com.kp.cms.handlers.reports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GroupTemplateInterview;
import com.kp.cms.forms.reports.InterviewScoreSheetForm;
import com.kp.cms.helpers.reports.InterviewScoreSheetHelper;
import com.kp.cms.helpers.reports.ScoreSheetHelper;
import com.kp.cms.to.reports.ScoreSheetTO;
import com.kp.cms.transactions.reports.IInterviewScoreSheetTransaction;
import com.kp.cms.transactions.reports.IScoreSheetTransaction;
import com.kp.cms.transactionsimpl.reports.InterviewScoreSheetTransactionImpl;
import com.kp.cms.transactionsimpl.reports.ScoreSheetTransactionImpl;

public class InterviewScoreSheetHandler {
	/**
	 * Singleton object of InterviewScoreSheetHandler
	 */
	private static volatile InterviewScoreSheetHandler interviewScoreSheetHandler = null;
	private static final Log log = LogFactory.getLog(InterviewScoreSheetHandler.class);
	private InterviewScoreSheetHandler() {
		
	}
	/**
	 * return singleton object of InterviewScoreSheetHandler.
	 * @return
	 */
	public static InterviewScoreSheetHandler getInstance() {
		if (interviewScoreSheetHandler == null) {
			interviewScoreSheetHandler = new InterviewScoreSheetHandler();
		}
		return interviewScoreSheetHandler;
	}
	public List<String> getListOfCandidates(
			InterviewScoreSheetForm interviewScoreSheetForm,
			HttpServletRequest request,Map<Integer,String> templateMap) throws Exception {
		IInterviewScoreSheetTransaction iScoreSheetTransaction=InterviewScoreSheetTransactionImpl.getInstance();
		String searchCriteria=InterviewScoreSheetHelper.getInstance().getScoreSheetSearchQuery(interviewScoreSheetForm);
		List listofStudents=iScoreSheetTransaction.getListOfCandidates(searchCriteria);
		List<String> listOfCandidates=InterviewScoreSheetHelper.getInstance().convertBotoTo(listofStudents, request,templateMap);
		return listOfCandidates;
	}
	/**
	 * @param interviewScoreSheetForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getInterviewProgramCourseMap(InterviewScoreSheetForm interviewScoreSheetForm) throws Exception{
		IInterviewScoreSheetTransaction iScoreSheetTransaction=InterviewScoreSheetTransactionImpl.getInstance();
		String query=InterviewScoreSheetHelper.getInstance().getInterviewProgramCourseMapQuery(interviewScoreSheetForm);
		return iScoreSheetTransaction.getInterviewProgramCourseMap(query);
	}
	/**
	 * @param interviewScoreSheetForm
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getTemplateMap(InterviewScoreSheetForm interviewScoreSheetForm,String templateName) throws Exception{
		IInterviewScoreSheetTransaction iScoreSheetTransaction=InterviewScoreSheetTransactionImpl.getInstance();
		String query=InterviewScoreSheetHelper.getInstance().getTemplateQuery(interviewScoreSheetForm,templateName);
		return iScoreSheetTransaction.getTemplateMap(query);
	}
}
