package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.forms.admission.CandidateSearchForm;
import com.kp.cms.helpers.reports.AnyStageInAdmissionHelper;
import com.kp.cms.to.admission.CandidateSearchTO;
import com.kp.cms.transactions.admission.ICandidateSearchTxnImpl;
import com.kp.cms.transactionsimpl.admission.CandidateSearchTxnImpl;

public class AnyStageInAdmissionHandler {
private static Log log = LogFactory.getLog(AnyStageInAdmissionHandler.class);
	
	public static volatile AnyStageInAdmissionHandler self=null;
	/**
	 * @return
	 * This method will return instance of this classes
	 */
	public static AnyStageInAdmissionHandler getInstance(){
		if(self==null)
			self= new AnyStageInAdmissionHandler();
		return self;
	}
	private AnyStageInAdmissionHandler(){
		
	}

	/**
	 * @param studentSearchForm
	 * @return
	 * @throws Exception
	 * This method will get the search result of the selected criteria
	 */
	public List<CandidateSearchTO> getStudentSearchResults(
			CandidateSearchForm studentSearchForm) throws Exception {

		ICandidateSearchTxnImpl studentSearchTransactionImpl = CandidateSearchTxnImpl.getInstance();
		String query = AnyStageInAdmissionHelper.getSelectionSearchCriteria(studentSearchForm);
		List<AdmAppln> admapplnBo =	studentSearchTransactionImpl.getStudentSearch(query);
		List<CandidateSearchTO> studentSearchTo = AnyStageInAdmissionHelper.convertBoToTo(admapplnBo);

		return studentSearchTo;
	}
}
