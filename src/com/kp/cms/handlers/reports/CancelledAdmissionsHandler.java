package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.CancelledAdmissionsForm;
import com.kp.cms.helpers.reports.CancelledAdmissionsHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.reports.ICancelledAdmissionsTransaction;
import com.kp.cms.transactionsimpl.reports.CancelledAdmissionsTransactionImpl;

public class CancelledAdmissionsHandler {
	public static final Log log = LogFactory.getLog(CancelledAdmissionsHandler.class);
	public static volatile CancelledAdmissionsHandler cancelledAdmissionsHandler = null;
	public static CancelledAdmissionsHandler getInstance() {
		if (cancelledAdmissionsHandler == null) {
			cancelledAdmissionsHandler = new CancelledAdmissionsHandler();
			return cancelledAdmissionsHandler;
		}
		return cancelledAdmissionsHandler;
	}
	/**
	 * 
	 * @param subForm
	 * @return
	 * @throws Exception
	 */

	public List<StudentTO> getCancelledAdmissionDetails(CancelledAdmissionsForm cancelledAdmForm) throws Exception {
		log.debug("start getCancelledAdmissionDetails");
		ICancelledAdmissionsTransaction cancelledAdmTransaction = new CancelledAdmissionsTransactionImpl();
		List<Student> studentList = cancelledAdmTransaction.getCancelledAdmissions(cancelledAdmForm);
		List<StudentTO> stuList = CancelledAdmissionsHelper.getInstance().copyBosToTO(studentList);
		log.debug("exit getSubjectGroupDetails");
		return stuList;
	}

}
