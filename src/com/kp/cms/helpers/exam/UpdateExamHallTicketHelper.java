package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.exam.UpdateExamHallTicketForm;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;
import com.kp.cms.utilities.CommonUtil;

public class UpdateExamHallTicketHelper {
	/**
	 * Singleton object of UpdateExamHallTicketHelper
	 */
	private static volatile UpdateExamHallTicketHelper updateExamHallTicketHelper = null;
	private static final Log log = LogFactory.getLog(UpdateExamHallTicketHelper.class);
	private UpdateExamHallTicketHelper() {
		
	}
	/**
	 * return singleton object of UpdateExamHallTicketHelper.
	 * @return
	 */
	public static UpdateExamHallTicketHelper getInstance() {
		if (updateExamHallTicketHelper == null) {
			updateExamHallTicketHelper = new UpdateExamHallTicketHelper();
		}
		return updateExamHallTicketHelper;
	}
	/**
	 * @param updateExamHallTicketForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQuery(UpdateExamHallTicketForm updateExamHallTicketForm) throws Exception {
		String query="from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.downloadEndDate='"+CommonUtil.ConvertStringToSQLDate(updateExamHallTicketForm.getEndDate())+"'";
		return query;
	}
	public List<ExamPublishHallTicketTO> getfinalList(List<ExamPublishHallTicketTO> selectedCandidates) throws Exception {
		List<ExamPublishHallTicketTO> finalList=new ArrayList<ExamPublishHallTicketTO>();
		if(selectedCandidates!=null && !selectedCandidates.isEmpty()){
			Iterator<ExamPublishHallTicketTO> itr=selectedCandidates.iterator();
			while (itr.hasNext()) {
				ExamPublishHallTicketTO examPublishHallTicketTO = (ExamPublishHallTicketTO) itr.next();
				if(examPublishHallTicketTO.isChecked()){
					finalList.add(examPublishHallTicketTO);
				}
			}
		}
		return finalList;
	}
}
