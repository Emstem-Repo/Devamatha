package com.kp.cms.actions.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.OpeningBalanceForm;
import com.kp.cms.handlers.inventory.OpeningBalanceHandler;
import com.kp.cms.handlers.inventory.PurchaseOrderHandler;
import com.kp.cms.handlers.inventory.StocksReceiptHandler;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.utilities.CommonUtil;

public class OpeningBalanceAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(OpeningBalanceAction.class);
	
	/**
	 * initialize Stock Reciept page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOpeningBalance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			log.info("enter initOpeningBalance page...");
			OpeningBalanceForm balanceForm= (OpeningBalanceForm)form;
			setUserId(request, balanceForm);

		try {
			
			balanceForm.setLocationId(null);
			balanceForm.setSelectedItemId(null);
			balanceForm.setSelectedItemQty(null);
			balanceForm.setSearchItem(null);
			//fetch inventory locations
			List<InvLocationTO> locations=StocksReceiptHandler.getInstance().getAllInventoryLocation();
			balanceForm.setLocations(locations);
			
			// set item list
			List<InvItemTO> itemList=PurchaseOrderHandler.getInstance().getItemList();
			balanceForm.setItemList(itemList);
		} 
		catch (Exception e) {
			log.error("error in initOpeningBalance...",e);
				throw e;
			
		}
		
		log.info("exit initOpeningBalance page...");
		return mapping.findForward(CMSConstants.INIT_OPENINGBALANCE_PAGE);
	}
	
	
	/**
	 * stock Receipt finalsubmit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitOpeningBalance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			log.info("enter submitOpeningBalance page...");
			OpeningBalanceForm balanceForm= (OpeningBalanceForm)form;
			//validation if needed
			ActionMessages errors=balanceForm.validate(mapping, request);
			if(errors==null)
				errors= new ActionMessages();
		try {
			
			if(balanceForm.getSelectedItemQty()!=null && !StringUtils.isEmpty(balanceForm.getSelectedItemQty())
					&& !CommonUtil.isValidDecimal(balanceForm.getSelectedItemQty())){
				if (errors.get(CMSConstants.OPENINGBALANCE_QUANTITY_INVALID)!=null && !errors.get(CMSConstants.OPENINGBALANCE_QUANTITY_INVALID).hasNext()) {
					errors.add(CMSConstants.OPENINGBALANCE_QUANTITY_INVALID, new ActionError(CMSConstants.OPENINGBALANCE_QUANTITY_INVALID));
				}
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_OPENINGBALANCE_PAGE);
			}
			// save the purchase return items
			boolean result=OpeningBalanceHandler.getInstance().saveOpeningBalance(balanceForm);
			
			if(!result){
				
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionError(CMSConstants.OPENINGBALANCE_SUBMIT_FAILURE);
				messages.add("messages", message);
				saveErrors(request, messages);
				
				return mapping.findForward(CMSConstants.INIT_OPENINGBALANCE_PAGE);
			}
		} 
		catch (Exception e) {
			log.error("error in submitOpeningBalance...",e);
				throw e;
			
		}
		
		log.info("exit submitOpeningBalance page...");
		return mapping.findForward(CMSConstants.OPENINGBALANCE_CONFIRM_PAGE);
	}
}
