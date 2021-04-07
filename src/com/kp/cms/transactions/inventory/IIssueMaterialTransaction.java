package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvIssue;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvRequest;
import com.kp.cms.bo.admin.InvTx;

public interface IIssueMaterialTransaction {

	/**
	 * gets request details by requisition No.
	 */
	public InvRequest getRequestByRequisitionNo(int requisitionNo)throws Exception;
	
	//Used to submit the issued items and also the updates the item stock
	public boolean submitIssueMaterial(InvIssue issue,
			List<InvItemStock> updateStockList, List<InvTx> invTXList) throws Exception ;
	
	/**
	 * Gets the issued item details by invRequestID
	 */
	public InvIssue getIssueDetailsByInvRequestID(int invRequestID) throws Exception;
	
	public String getDepartmentByUserName(String userName)throws Exception;
}
