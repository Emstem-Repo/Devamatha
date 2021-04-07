package com.kp.cms.actions.inventory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.RequestVsIssueForm;
import com.kp.cms.handlers.inventory.InventoryRequestHandler;
import com.kp.cms.handlers.inventory.RequestVsIssueHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.RequestVsIssueTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class RequestVsIssueAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(RequestVsIssueAction.class);
	private static final String REQUEST_VS_ISSUE_REPORT = "requestVsIssueReport";
	
	/**
	 * Method to set the required data to the form to display in requestVsIssue.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInventoryLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered getInventoryLocation RequestVsIssueAction");
		RequestVsIssueForm requestVsIssueForm = (RequestVsIssueForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			requestVsIssueForm.clear();
			getInventoryLocationList(requestVsIssueForm);
			setUserId(request, requestVsIssueForm);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			requestVsIssueForm.setErrorMessage(msg);
			requestVsIssueForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("Exit getInventoryLocation RequestVsIssueAction");		
		return mapping.findForward(CMSConstants.REQUEST_VS_ISSUE);
	}
	
	/**
	 * Method to set all active Inventory Locations to the form
	 * @param inventoryRequestForm
	 * @throws Exception
	 */
	public void getInventoryLocationList(RequestVsIssueForm requestVsIssueForm) throws Exception{
		List<SingleFieldMasterTO> inventoryLocationList = InventoryRequestHandler.getInstance().getInventoryLocation();
		if( inventoryLocationList != null ){
			requestVsIssueForm.setInventoryLocationList(inventoryLocationList);
		}
	}
	
	/**
	 * Method to set the inventory request details to the form to display in requestVsIssue.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward RequestVsIssueDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RequestVsIssueForm requestVsIssueForm = (RequestVsIssueForm) form;
		ActionMessages messages = new ActionMessages();
		
		try{
			HttpSession session = request.getSession(false);
			ActionErrors errors = requestVsIssueForm.validate(mapping, request);
			validateDate(requestVsIssueForm, errors);
		if (errors.isEmpty()) {
			
			int invLocationId = Integer.parseInt(requestVsIssueForm.getInvLocationId());
			String startDate = "";
			String endDate = "";
			if(requestVsIssueForm.getStartDate() !=null && !requestVsIssueForm.getStartDate().trim().isEmpty()){
				startDate = requestVsIssueForm.getStartDate();
			}
			if(requestVsIssueForm.getEndDate()!=null && !requestVsIssueForm.getEndDate().trim().isEmpty()){
				endDate = requestVsIssueForm.getEndDate();
			}
			List<RequestVsIssueTO> requestVsIssueList = RequestVsIssueHandler.getInstance().getRequestVsIssueList(invLocationId, startDate, endDate);
			
			if( requestVsIssueList.size()==0){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.REQUEST_VS_ISSUE);
			}else{
				session.setAttribute(REQUEST_VS_ISSUE_REPORT, requestVsIssueList);
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.REQUEST_VS_ISSUE);
		}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			requestVsIssueForm.setErrorMessage(msg);
			requestVsIssueForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit RequestVsIssueDetails");
		return mapping.findForward(CMSConstants.REQUEST_VS_ISSUE_RESULT);
	}
	
	/**
	 * Method to validate the manually entered date format
	 * @param requestVsIssueForm
	 * @param errors
	 */
	private void validateDate(RequestVsIssueForm requestVsIssueForm, ActionErrors errors) {
		
		if(requestVsIssueForm.getStartDate() != null && !StringUtils.isEmpty(requestVsIssueForm.getStartDate()) && requestVsIssueForm.getEndDate() != null && StringUtils.isEmpty(requestVsIssueForm.getEndDate())){
		 	errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.BOTH_DATES_REQUIRED));
		}else if(requestVsIssueForm.getEndDate() != null && !StringUtils.isEmpty(requestVsIssueForm.getEndDate()) && requestVsIssueForm.getStartDate() != null && StringUtils.isEmpty(requestVsIssueForm.getStartDate())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.BOTH_DATES_REQUIRED));
		}
		if (requestVsIssueForm.getStartDate() != null && !StringUtils.isEmpty(requestVsIssueForm.getStartDate()) && !CommonUtil.isValidDate(requestVsIssueForm.getStartDate())) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_REPORT_STARTDATE_INVALID));
		}
		if (requestVsIssueForm.getEndDate() != null && !StringUtils.isEmpty(requestVsIssueForm.getEndDate()) && !CommonUtil.isValidDate(requestVsIssueForm.getEndDate())) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_REPORT_ENDDATE_INVALID));
		}
		//if start date greater than end date then showing error message
		if(errors.isEmpty()){
			if(CommonUtil.checkForEmpty(requestVsIssueForm.getStartDate()) && CommonUtil.checkForEmpty(requestVsIssueForm.getEndDate())){
				Date startDate = CommonUtil.ConvertStringToDate(requestVsIssueForm.getStartDate());
				Date endDate = CommonUtil.ConvertStringToDate(requestVsIssueForm.getEndDate());
	
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if(daysBetween <= 0) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
				}
			}
		}	
	}
}