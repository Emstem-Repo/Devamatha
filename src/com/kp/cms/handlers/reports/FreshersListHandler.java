package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.FreshersListForm;
import com.kp.cms.helpers.reports.FreshersListHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.reports.IFreshersListTransaction;
import com.kp.cms.transactionsimpl.reports.FreshersListTransactionImpl;

public class FreshersListHandler {
private static final Log log = LogFactory.getLog(FreshersListHandler.class);
	
	public static volatile FreshersListHandler freshersListHandler = null;

	private FreshersListHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static FreshersListHandler getInstance() {
		if (freshersListHandler == null) {
			freshersListHandler = new FreshersListHandler();
		}
		return freshersListHandler;
	}
	/**
	 * 
	 * @param listForm
	 * @returns fresher student List
	 */

	public List<StudentTO> getFresherStudentList(FreshersListForm listForm) throws Exception{
		log.info("Entering into getFresherStudentList of FreshersListHandler");
		IFreshersListTransaction transaction = new FreshersListTransactionImpl();
		List<Object[]> fresherBOList = transaction.getFresherStudentList(FreshersListHelper.getInstance().getSearchCriteria(listForm));
		log.info("Leaving into getFresherStudentList of FreshersListHandler");
		return FreshersListHelper.getInstance().populateStudentListByClassWise(fresherBOList);
	}
}
