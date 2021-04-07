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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.InvSalvageReportForm;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.SalvageItemHandler;
import com.kp.cms.handlers.inventory.StockTransferHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvSalvageTO;
import com.kp.cms.to.inventory.ItemTO;

@SuppressWarnings("deprecation")
public class InvSalvageReportAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(InvSalvageReportAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initSalvageReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of initSalvageReport in InvSalvageReportAction class...");
		InvSalvageReportForm salvageReportForm = (InvSalvageReportForm)form; 
		salvageReportForm.reset();
		setInvLocationDetails(salvageReportForm);
		setItemsListtoForm(salvageReportForm);
		HttpSession session = request.getSession(false);
		session.removeAttribute("SalvageList");
		log.info("exit of initSalvageReport in InvSalvageReportAction class...");
		return mapping.findForward(CMSConstants.INIT_SALVAGE_REPORT);
	}
	
	
	public ActionForward submitSalvageReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitSalvageReport in InvSalvageReportAction class..");
		InvSalvageReportForm salvageReportForm = (InvSalvageReportForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("SalvageList")==null){
			ActionErrors errors = new ActionErrors();
			validateSalvageReport(salvageReportForm,errors);
			if(errors.isEmpty()){
				try {
					setUserId(request, salvageReportForm);
					List<InvSalvageTO> salvageList = SalvageItemHandler.getInstance().getReportDetails(salvageReportForm);
					if(salvageList != null && salvageList.size() != 0){
						session.setAttribute("SalvageList", salvageList);
						salvageReportForm.reset();
						setInvLocationDetails(salvageReportForm);
						setItemsListtoForm(salvageReportForm);
					}
					
				}catch (Exception e) {
					log.error("Error occured in submitSalvageReport of InvSalvageReportAction", e);
					String msg = super.handleApplicationException(e);
					salvageReportForm.setErrorMessage(msg);
					salvageReportForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}else{
				addErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_SALVAGE_REPORT);
			}
		}	
		log.info("exit of submitSalvageReport in InvSalvageReportAction class..");
		return mapping.findForward(CMSConstants.SALVAGE_REPORT_RESULT);
	}
	
	private void validateSalvageReport(InvSalvageReportForm salvageReportForm, ActionErrors errors)throws Exception {
		log.info("entering into validateSalvageReport in InvSalvageReportAction class..");
		if(StringUtils.isEmpty(salvageReportForm.getInvLocationId()) 
				&& StringUtils.isEmpty(salvageReportForm.getItemId()) 
				&& StringUtils.isEmpty(salvageReportForm.getSearchItem())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.INVENTORY_LOCTION_ITEM_REQUIRE));
		}
		log.info("exit of validateSalvageReport in InvSalvageReportAction class..");
	}
	
	/**
	 * This method is used to load inventory location details.
	 * @param salvageReportForm
	 * @throws Exception
	 */
		
	public void setInvLocationDetails(InvSalvageReportForm salvageReportForm)throws Exception{
		log.info("entering into setInvLocationDetails in InvSalvageReportAction class..");
		List<SingleFieldMasterTO> invLocDetails = StockTransferHandler.getInstance().getAllInventoryLocation();
		salvageReportForm.setInventoryList(invLocDetails);
		log.info("exit of setInvLocationDetails in InvSalvageReportAction class..");
	}

	/**
	 * This method is used to save items to list.
	 * @param salvageReportForm
	 * @throws Exception
	 */
	
	public void setItemsListtoForm(InvSalvageReportForm salvageReportForm) throws Exception{
		log.info("entering into setItemsListtoForm in ItemIssueAction class..");
		List<ItemTO> itemList = ItemHandler.getInstance().getItemList(0);
		salvageReportForm.setItemList(itemList);
		Map<String, ItemTO> itemMap = new HashMap<String, ItemTO>();
		if(itemList!=null && !itemList.isEmpty()){
			Iterator<ItemTO> ItemIterator = itemList.iterator();
			while (ItemIterator.hasNext()) {
				ItemTO itemTO = ItemIterator.next();
				itemMap.put(itemTO.getId(), itemTO);
			}
		}
		salvageReportForm.setItemMap(itemMap);
		log.info("exit of setItemsListtoForm in ItemIssueAction class..");
	}
}