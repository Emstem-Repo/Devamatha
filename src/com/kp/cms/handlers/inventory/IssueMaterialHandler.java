package com.kp.cms.handlers.inventory;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.InvIssue;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvRequest;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.IssueMaterialForm;
import com.kp.cms.helpers.inventory.IssueMaterialHelper;
import com.kp.cms.helpers.inventory.StockTransferHelper;
import com.kp.cms.to.inventory.ItemStockTO;
import com.kp.cms.transactions.inventory.IIssueMaterialTransaction;
import com.kp.cms.transactions.inventory.IStockTransferTransaction;
import com.kp.cms.transactionsimpl.inventory.IssueMaterialTransactionImpl;
import com.kp.cms.transactionsimpl.inventory.StockTransferTransactionImpl;

@SuppressWarnings("deprecation")
public class IssueMaterialHandler {
	private static final Log log = LogFactory.getLog(IssueMaterialHandler.class);
	public static volatile IssueMaterialHandler issueMaterialHandler = null;

	private IssueMaterialHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static IssueMaterialHandler getInstance() {
		if (issueMaterialHandler == null) {
			issueMaterialHandler = new IssueMaterialHandler();
		}
		return issueMaterialHandler;
	}
	/**
	 * Gets item details by requisition no.
	 */
	public void setItemDetailsToForm(IssueMaterialForm materialForm, ActionErrors errors)throws Exception{
		log.info("Entering into setItemDetailsToForm IssueMaterialHandler");
		int requisitionNo = Integer.parseInt(materialForm.getRequisitionNo().trim());
		IIssueMaterialTransaction transaction = new IssueMaterialTransactionImpl();
		InvRequest invRequest = transaction.getRequestByRequisitionNo(requisitionNo);
		if(invRequest!=null){
			IssueMaterialHelper.getInstance().copyInvReqestBOToTO(invRequest, materialForm,errors);
		}
		else{
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_NO_RESULTS_FOUND));
		}
		log.info("Leaving into setItemDetailsToForm IssueMaterialHandler");
	}
	/**
	 * 
	 * @param materialForm
	 * @returns the result after requesting for submitting the issue materials
	 * @throws Exception
	 */
	public boolean submitIssueMaterials(IssueMaterialForm materialForm, ActionErrors errors)throws Exception{
		log.info("Entering into submitIssueMaterials IssueMaterialHandler");
		IStockTransferTransaction transaction = new StockTransferTransactionImpl();
		IIssueMaterialTransaction materialTransaction = new IssueMaterialTransactionImpl();
		List<InvItemStock> updateStockList = null;
		InvIssue issue = null;
		boolean result = false;
		//Gets the stock items based on the inventory
		List<InvItemStock> itemStockList = transaction.getItemStockOnInventory(Integer.valueOf(materialForm.getMaterialTO().getInvLocationId().trim()));		
		//Used to create stock map.
		Map<String, ItemStockTO> itemStockMap = StockTransferHelper.getInstance().createStockMapOnInventoyCode(itemStockList);
		if(itemStockMap == null || itemStockMap.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STOCK_TRANSFER_STOCK_UNAVAILABLE_IN_ITEM_STOCK));
			result = false;
		}
		if(errors.isEmpty()){
			itemStockMap = IssueMaterialHelper.getInstance().updateItemStock(itemStockMap, materialForm, errors);
			if(errors.isEmpty()){
				//Get if items are issued for this invrequest Id earlier
				int invRequestId = Integer.valueOf(materialForm.getMaterialTO().getInvRequestId());
				issue = materialTransaction.getIssueDetailsByInvRequestID(invRequestId);
				if(issue != null){
					issue = IssueMaterialHelper.getInstance().updatePreviousIssuedItems(issue, materialForm, errors);
				}
				else{
				//create the newly issued Bo. If it issues for the very 1st time
					issue = IssueMaterialHelper.getInstance().prepareMaterialBOSForIssue(materialForm);
				}
				if(errors.isEmpty()){
				//create the list of stock bos
					updateStockList = IssueMaterialHelper.getInstance().covertupdateItemStockTOToBO(itemStockMap, materialForm);
					result = true;
				}
			}			
		}
		if(result){		
			result =  materialTransaction.submitIssueMaterial(issue, updateStockList, materialForm.getInvTxList());
		}
		log.info("Leaving into submitIssueMaterials IssueMaterialHandler");		
		return result;
	}
}
