package com.kp.cms.handlers.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.ListofRollRegNoForm;
import com.kp.cms.helpers.reports.ListofRollRegNoHelper;
import com.kp.cms.transactions.reports.IListofRollRegNoTransaction;
import com.kp.cms.transactionsimpl.reports.ListofRollRegNoTxnImpl;

public class ListofRollRegNoHandler {

	private static final Log log = LogFactory.getLog(ListofRollRegNoHandler.class);
	public static volatile ListofRollRegNoHandler listofRollRegNoHandler = null;

	public static ListofRollRegNoHandler getInstance() {
		if (listofRollRegNoHandler == null) {
			listofRollRegNoHandler = new ListofRollRegNoHandler();
			return listofRollRegNoHandler;
		}
		return listofRollRegNoHandler;
	}

	/**
	 * 
	 * @param regNoForm
	 * @throws Exception
	 */
	public void getListofRegNoRollNo(ListofRollRegNoForm regNoForm) throws Exception {
		log.debug("inside getListofRegNoRollNo");
		IListofRollRegNoTransaction iTransaction = ListofRollRegNoTxnImpl.getInstance();
		
		int classId =0; 
		if(regNoForm.getClassId() != null && !regNoForm.getClassId().trim().isEmpty()){
			Integer.parseInt(regNoForm.getClassId());
		}
		List<Student> stuList = iTransaction.getListofRollRegdNos(Integer.parseInt(regNoForm.getProgramTypeId()), classId);
		
		ListofRollRegNoHelper.getInstance().copyBosToList(stuList, regNoForm);
	}	  		
}
