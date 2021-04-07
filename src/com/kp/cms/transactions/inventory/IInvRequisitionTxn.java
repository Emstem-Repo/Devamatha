package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvRequest;
import com.kp.cms.exceptions.ApplicationException;

public interface IInvRequisitionTxn {
	public boolean addRequisition(InvRequest invRequest) throws  Exception ;
	public int getMaxRequisitionNo() throws Exception;
	public List<InvRequest> getRequisitionItems() throws Exception;
	public boolean updateRequisition(List<InvRequest> invRequestList) throws ApplicationException;
	public InvRequest getDetails(int reqId) throws ApplicationException;
	public List<InvRequest> getInvRequestByLocation(int locationId) throws Exception; 
}
