package com.kp.cms.actions.inventory;


import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.inventory.AmcDueIn30DaysForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.inventory.AmcDueIn30DaysHandler;
import com.kp.cms.handlers.inventory.InventoryRequestHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class AmcDueIn30DaysReportAction extends BaseDispatchAction {

	private static Log log = LogFactory.getLog(AmcDueIn30DaysReportAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAmcReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("inside initAmcReport");
		AmcDueIn30DaysForm amcDueIn30DaysForm = (AmcDueIn30DaysForm) form;
		amcDueIn30DaysForm.setDate(null);
		HttpSession session = request.getSession(false);
		session.removeAttribute("amcList");
		return mapping.findForward(CMSConstants.AMC_DUE_REPORT);

	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return list of amc details based on the search criteria
	 * @throws Exception
	 */
	public ActionForward getDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									throws Exception {
		log.debug("inside getDetails");
		AmcDueIn30DaysForm amcDueIn30DaysForm = (AmcDueIn30DaysForm) form;

		HttpSession session = request.getSession(false);
		if(session.getAttribute("cancelledList")==null){
			 ActionErrors errors = amcDueIn30DaysForm.validate(mapping, request);
			boolean isValidStartDate;
			try {
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.AMC_DUE_REPORT);
				}
				isValidStartDate = CommonUtil.isValidDate(amcDueIn30DaysForm.getDate());
				if(!isValidStartDate){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.AMC_DUE_REPORT);		
				}
				
				String startDate = amcDueIn30DaysForm.getDate();
				
				Calendar cal = Calendar.getInstance();
				int day = Integer.parseInt(startDate.substring(0, 2));
				int month = Integer.parseInt(startDate.substring(3, 5));
				int year = Integer.parseInt(startDate.substring(6, 10));  

				Date stdate = CommonUtil.ConvertStringToSQLDate(startDate);
				
				cal.set(year, month, day);
				cal.add(Calendar.DATE, 30);

				int enDate = cal.get(Calendar.DATE); 
				int enMonth = cal.get(Calendar.MONTH);
				int enYear = cal.get(Calendar.YEAR);
				Date endDate =  CommonUtil.ConvertStringToSQLDate(Integer.toString(enDate) + "/" + Integer.toString(enMonth) + "/" + Integer.toString(enYear)); 
						
				List<InvAmcTO> amcList  = AmcDueIn30DaysHandler.getInstance().getAmcDetails(stdate, endDate);
				session.setAttribute("amcList",amcList);
			} catch (Exception e) {
				log.error("error in getDetails...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					amcDueIn30DaysForm.setErrorMessage(msg);
					amcDueIn30DaysForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}		
		}
		log.debug("leaving getDetails");
		return mapping.findForward(CMSConstants.SUBMIT_AMC_DUE_REPORT);
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
	public ActionForward initAMCWarrantyMail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("inside initAMCWarrantyMail");
		
		AmcDueIn30DaysForm amcDueIn30DaysForm = (AmcDueIn30DaysForm) form;
		amcDueIn30DaysForm.clear();
		getInventoryLocationList(amcDueIn30DaysForm);
		
		return mapping.findForward(CMSConstants.AMC_WARRANTY_DUE_MAIL_SEARCH);
	}
	
	/**
	 * Method to set all active Inventory Locations to the form
	 * @param inventoryRequestForm
	 * @throws Exception
	 */
	public void getInventoryLocationList(AmcDueIn30DaysForm amcDueIn30DaysForm) throws Exception{
		List<SingleFieldMasterTO> inventoryLocationList = InventoryRequestHandler.getInstance().getInventoryLocation();
		if( inventoryLocationList != null ){
			amcDueIn30DaysForm.setInventoryLocationList(inventoryLocationList);
		}
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
	public ActionForward getWarrantyExpiryDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									throws Exception {
		log.debug("inside getWarrantyExpiryDetails");
		AmcDueIn30DaysForm amcDueIn30DaysForm = (AmcDueIn30DaysForm) form;
		 ActionErrors errors = amcDueIn30DaysForm.validate(mapping, request);
		int invLocationId = 0;
			try {
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.AMC_WARRANTY_DUE_MAIL_SEARCH);
				}else{
					if(amcDueIn30DaysForm.getInvLocationId()!=null && !amcDueIn30DaysForm.getInvLocationId().trim().isEmpty()){
						invLocationId = Integer.parseInt(amcDueIn30DaysForm.getInvLocationId());
					}						
					List<InvAmcTO> amcTOList  = AmcDueIn30DaysHandler.getInstance().getWarrantyExpiryDetails(CommonUtil.ConvertStringToSQLDate(amcDueIn30DaysForm.getFromDate()), CommonUtil.ConvertStringToSQLDate(amcDueIn30DaysForm.getToDate()), invLocationId);
					if(amcTOList.size()==0){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.AMC_WARRANTY_DUE_MAIL_SEARCH);
					}
					amcDueIn30DaysForm.setAmcTOList(amcTOList);
				}
			} catch (Exception e) {
				log.error("error in getWarrantyExpiryDetails...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					amcDueIn30DaysForm.setErrorMessage(msg);
					amcDueIn30DaysForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}	
		}
		log.debug("leaving getWarrantyExpiryDetails");
		return mapping.findForward(CMSConstants.AMC_WARRANTY_DUE_MAIL);
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
	public ActionForward sendWarrantyExpiryMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									throws Exception {
		log.debug("inside sendWarrantyExpiryMail");
		AmcDueIn30DaysForm amcDueIn30DaysForm = (AmcDueIn30DaysForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		ActionMessage message = null;
		try {
			List<GroupTemplate> groupTemplateList = TemplateHandler.getInstance().getDuplicateCheckList(0, CMSConstants.AMC_WARRANTY_DUE);
			String templateDescription = "";
			
			Iterator<GroupTemplate> iterator = groupTemplateList.iterator();
			while (iterator.hasNext()) {
				GroupTemplate groupTemplate = (GroupTemplate) iterator.next();
				templateDescription = groupTemplate.getTemplateDescription();
			}
			amcDueIn30DaysForm.setTemplateDescription(templateDescription);
									
			if(AmcDueIn30DaysHandler.getInstance().sendWarrantyExpiryMail(amcDueIn30DaysForm)){
				message = new ActionMessage("knowledgepro.admin.bulkmail.sent");
				messages.add(CMSConstants.MESSAGES, message);
				addMessages(request, messages);
			}else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.bulkmail.notsent"));
				saveErrors(request, errors);
			}
			amcDueIn30DaysForm.clear();
			getInventoryLocationList(amcDueIn30DaysForm);
		} catch (Exception e) {
			log.error("error in sendWarrantyExpiryMail...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				amcDueIn30DaysForm.setErrorMessage(msg);
				amcDueIn30DaysForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}	
		}
		log.debug("leaving sendWarrantyExpiryMail");
		return mapping.findForward(CMSConstants.AMC_WARRANTY_DUE_MAIL_SEARCH);
	}	
}
