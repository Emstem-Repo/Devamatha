package com.kp.cms.actions.reports;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.IssuedReceivedReportForm;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.StockTransferHandler;
import com.kp.cms.handlers.reports.IssuedReceivedReportHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.to.reports.IssuedReceivedTO;

@SuppressWarnings("deprecation")
public class IssuedReceivedReportAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(IssuedReceivedReportAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initIssuedReceivedReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of initIssuedReceivedReport in IssuedReceivedReportAction class...");
		IssuedReceivedReportForm issuedReceivedReportForm = (IssuedReceivedReportForm)form; 
		issuedReceivedReportForm.resetInventory();
		setInvLocationDetails(issuedReceivedReportForm);
		setItemsListtoForm(issuedReceivedReportForm);
		HttpSession session = request.getSession(false);
		session.removeAttribute("issuedReceivedReport");
		log.info("exit of initIssuedReceivedReport in IssuedReceivedReportAction class...");
		return mapping.findForward("initIssuedReceivedReport");
	}

	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward submitIssuedReceivedReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of submitIssuedReceivedReport in IssuedReceivedReportAction class...");
		IssuedReceivedReportForm issuedReceivedReportForm = (IssuedReceivedReportForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("issuedReceivedReport")==null){
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			 ActionErrors errors = issuedReceivedReportForm.validate(mapping, request);
			validateSalvageReport(issuedReceivedReportForm,errors);
			if (errors.isEmpty()) {
				try {
					setUserId(request,issuedReceivedReportForm);	
					List<IssuedReceivedTO> issuedReceivedList = IssuedReceivedReportHandler.getInstance().getItemTransactions(issuedReceivedReportForm);
					if(issuedReceivedList!=null && !issuedReceivedList.isEmpty()){
						session.setAttribute("issuedReceivedReport", issuedReceivedList);
					}else{
						message = new ActionMessage("knowledgepro.norecords");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
	
				} catch (ApplicationException ae) {
					String msg = super.handleApplicationException(ae);
					issuedReceivedReportForm.setErrorMessage(msg);
					issuedReceivedReportForm.setErrorStack(ae.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception e) {
					throw e;
				}
			} else {
				addErrors(request, errors);
				setInvLocationDetails(issuedReceivedReportForm);
				setItemsListtoForm(issuedReceivedReportForm);
				return mapping.findForward("initIssuedReceivedReport");
			}
		}	
		log.info("exit of submitIssuedReceivedReport in IssuedReceivedReportAction class...");
		return mapping.findForward("submitIssuedReceivedReport");
	}

	/**
	 * This method is used to load inventory location details.
	 * @param issuedReceivedReportForm
	 * @throws Exception
	 */
		
	public void setInvLocationDetails(IssuedReceivedReportForm issuedReceivedReportForm)throws Exception{
		log.info("entering into setInvLocationDetails in IssuedReceivedReportAction class..");
		List<SingleFieldMasterTO> invLocDetails = StockTransferHandler.getInstance().getAllInventoryLocation();
		issuedReceivedReportForm.setInventoryList(invLocDetails);
		log.info("exit of setInvLocationDetails in IssuedReceivedReportAction class..");
	}

	/**
	 * This method is used to save items to list.
	 * @param issuedReceivedReportForm
	 * @throws Exception
	 */
	
	public void setItemsListtoForm(IssuedReceivedReportForm issuedReceivedReportForm) throws Exception{
		log.info("entering into setItemsListtoForm in IssuedReceivedReportAction class..");
		List<ItemTO> itemList = ItemHandler.getInstance().getItemList(0);
		issuedReceivedReportForm.setItemList(itemList);
		Map<String, ItemTO> itemMap = new HashMap<String, ItemTO>();
		if(itemList!=null && !itemList.isEmpty()){
			Iterator<ItemTO> ItemIterator = itemList.iterator();
			while (ItemIterator.hasNext()) {
				ItemTO itemTO = ItemIterator.next();
				itemMap.put(itemTO.getId(), itemTO);
			}
		}
		issuedReceivedReportForm.setItemMap(itemMap);
		log.info("exit of setItemsListtoForm in IssuedReceivedReportAction class..");
	}
	/**
	 * @param issuedReceivedReportForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateSalvageReport(IssuedReceivedReportForm issuedReceivedReportForm, ActionErrors errors)throws Exception {
		log.info("entering into validateSalvageReport in InvSalvageReportAction class..");
		if(StringUtils.isEmpty(issuedReceivedReportForm.getInvLocationId()) 
				&& StringUtils.isEmpty(issuedReceivedReportForm.getItemId()) 
				&& StringUtils.isEmpty(issuedReceivedReportForm.getSearchItem())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.INVENTORY_LOCTION_ITEM_REQUIRE));
		}
		log.info("exit of validateSalvageReport in InvSalvageReportAction class..");
	}
}