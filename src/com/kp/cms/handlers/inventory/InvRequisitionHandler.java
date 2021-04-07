package com.kp.cms.handlers.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvRequest;
import com.kp.cms.forms.inventory.InvRequisitionForm;
import com.kp.cms.helpers.inventory.InvRequisitionHelper;
import com.kp.cms.to.inventory.InvRequestTO;
import com.kp.cms.transactions.inventory.IInvRequisitionTxn;
import com.kp.cms.transactionsimpl.inventory.InvRequisitionTxnImpl;

public class InvRequisitionHandler {
	private static volatile InvRequisitionHandler self=null;
	private static Log log = LogFactory.getLog(InvRequisitionHandler.class);

	/**
	 * @return
	 * This method will return the instance of this classes
	 */
	public static InvRequisitionHandler getInstance(){
		if(self==null)
			self= new InvRequisitionHandler();
		return self;
	}
	
	private InvRequisitionHandler(){
		
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<InvRequestTO> getRequestedItems() throws Exception{
		IInvRequisitionTxn txn = InvRequisitionTxnImpl.getInstance();
		List<InvRequest> invRequestList = txn.getRequisitionItems();
		List<InvRequestTO> invRequestTOList = InvRequisitionHelper.convertBOTOTOList(invRequestList);
		return invRequestTOList;
	}
	
	/**
	 * @param invRequisitionForm
	 * @return
	 * @throws Exception
	 */
	public boolean addRequisition(InvRequisitionForm invRequisitionForm) throws Exception{
		boolean isAdded = false;
		IInvRequisitionTxn txn = InvRequisitionTxnImpl.getInstance();
		int reqNo = txn.getMaxRequisitionNo();
		if(reqNo == 0){
			reqNo = reqNo+1;
			invRequisitionForm.setRequisitionNo(String.valueOf(reqNo));
		}else{
			reqNo = reqNo+1;
			invRequisitionForm.setRequisitionNo(String.valueOf(reqNo));
		}

		InvRequest invRequest = InvRequisitionHelper.convertFormToTO(invRequisitionForm);
		isAdded = txn.addRequisition(invRequest); 
		return isAdded;
	}

	/**
	 * @param invRequisitionForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateRequisition(InvRequisitionForm invRequisitionForm) throws Exception {
		boolean isUpdated = false;
		IInvRequisitionTxn txn = InvRequisitionTxnImpl.getInstance();
		List<InvRequest> invRequestList = InvRequisitionHelper.populateBOList(invRequisitionForm);
		isUpdated = txn.updateRequisition(invRequestList); 		
		return isUpdated;
	}

	/**
	 * @param invRequisitionForm
	 * @return
	 * @throws Exception
	 */
	public InvRequestTO getDetails(InvRequisitionForm invRequisitionForm) throws Exception {
		IInvRequisitionTxn txn = InvRequisitionTxnImpl.getInstance();
		int reqId =0;
		if(invRequisitionForm.getDetailId()!=null){
			reqId = Integer.parseInt(invRequisitionForm.getDetailId());
		}
		
		InvRequest invRequest= txn.getDetails(reqId);
		InvRequestTO invRequestTO = InvRequisitionHelper.populateTO(invRequest);
		return invRequestTO;
	}
	/**
	 * @param invRequisitionForm
	 * @return
	 * @throws Exception
	 */
	public List <InvRequestTO> getInvRequestByLocation(InvRequisitionForm invRequisitionForm) throws Exception {
		IInvRequisitionTxn txn = InvRequisitionTxnImpl.getInstance();
		int locationId =0;
		if(invRequisitionForm.getInventoryId()!=null){
			locationId = Integer.parseInt(invRequisitionForm.getInventoryId());
		}
		
		List<InvRequest> invRequestList= txn.getInvRequestByLocation(locationId);
		List <InvRequestTO> invRequestTOList = InvRequisitionHelper.inventoryLocationTO(invRequestList);
		return invRequestTOList;
	}

}
