package com.kp.cms.handlers.inventory;

import java.util.List;

import com.kp.cms.helpers.inventory.RequestVsIssueHelper;
import com.kp.cms.to.inventory.RequestVsIssueTO;
import com.kp.cms.transactions.inventory.IRequestVsIssueTransaction;
import com.kp.cms.transactionsimpl.inventory.RequestVsIssueTxnImpl;

public class RequestVsIssueHandler {

	private static volatile RequestVsIssueHandler requestVsIssueHandler = null;

	private RequestVsIssueHandler() {
	}

	public static RequestVsIssueHandler getInstance() {
		if (requestVsIssueHandler == null) {
			requestVsIssueHandler = new RequestVsIssueHandler();
		}
		return requestVsIssueHandler;
	}
	
	/**
	 * 
	 * @param invLocationId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<RequestVsIssueTO> getRequestVsIssueList(int invLocationId, String startDate, String endDate) throws Exception {
		IRequestVsIssueTransaction requestVsIssueTransaction = new RequestVsIssueTxnImpl();
		RequestVsIssueHelper requestVsIssueHelper = new RequestVsIssueHelper();
		
		List<Object[]> requestIssueList = requestVsIssueTransaction.getRequestIssueList(invLocationId, startDate, endDate);
		
		List<RequestVsIssueTO> requestVsIssueTOList = requestVsIssueHelper.convertBOstoTOs(requestIssueList);
		
		return requestVsIssueTOList;
	}
}
