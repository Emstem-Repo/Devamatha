package com.kp.cms.handlers.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.ScoreSheetForm;
import com.kp.cms.helpers.reports.ScoreSheetHelper;
import com.kp.cms.to.reports.ScoreSheetTO;
import com.kp.cms.transactions.reports.IScoreSheetTransaction;
import com.kp.cms.transactionsimpl.reports.ScoreSheetTransactionImpl;

public class ScoreSheetHandler {
	/**
	 * Singleton object of ScoreSheetHandler
	 */
	private static volatile ScoreSheetHandler scoreSheetHandler = null;
	private static final Log log = LogFactory.getLog(ScoreSheetHandler.class);
	private ScoreSheetHandler() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static ScoreSheetHandler getInstance() {
		if (scoreSheetHandler == null) {
			scoreSheetHandler = new ScoreSheetHandler();
		}
		return scoreSheetHandler;
	}
	public List<ScoreSheetTO> getListOfCandidates(ScoreSheetForm scoreSheetForm, HttpServletRequest request) throws Exception{
		IScoreSheetTransaction iScoreSheetTransaction=new ScoreSheetTransactionImpl();
		String searchCriteria=ScoreSheetHelper.getInstance().getScoreSheetSearchQuery(scoreSheetForm);
		List listofStudents=iScoreSheetTransaction.getListOfCandidates(searchCriteria);
		List<ScoreSheetTO> listOfCandidates=ScoreSheetHelper.getInstance().convertBotoTo(listofStudents, request);
		return listOfCandidates;
		
	}
	
	
}
