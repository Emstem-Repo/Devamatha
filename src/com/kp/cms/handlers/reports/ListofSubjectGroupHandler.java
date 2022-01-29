package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.forms.reports.ListofSubjectGroupsForm;
import com.kp.cms.helpers.reports.ListofSubjectGroupHelper;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.transactions.reports.IListofSubjectGroup;
import com.kp.cms.transactionsimpl.reports.ListofSubjectGroupTransactionImpl;

public class ListofSubjectGroupHandler {
	public static final Log log = LogFactory.getLog(ListofSubjectGroupHandler.class);
	public static volatile ListofSubjectGroupHandler listofSubjectGroupHandler = null;
	public static ListofSubjectGroupHandler getInstance() {
		if (listofSubjectGroupHandler == null) {
			listofSubjectGroupHandler = new ListofSubjectGroupHandler();
			return listofSubjectGroupHandler;
		}
		return listofSubjectGroupHandler;
	}
	/**
	 * 
	 * @param subForm
	 * @return
	 * @throws Exception
	 */

	public List<SubjectGroupTO> getSubjectGroupDetails(ListofSubjectGroupsForm subForm) throws Exception {
		log.debug("start getSubjectGroupDetails");
		IListofSubjectGroup listofSub = new ListofSubjectGroupTransactionImpl();
		List<SubjectGroup> subGroupList = listofSub.getSubjectGroup(subForm);
		List<SubjectGroupTO> subGroupTOList = ListofSubjectGroupHelper.getInstance().copyBosToTO(subGroupList);
		log.debug("exit getSubjectGroupDetails");
		return subGroupTOList;
	}
	
}
