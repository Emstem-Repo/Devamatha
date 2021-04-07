package com.kp.cms.actions.admin;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.DateOfBirthPrintForm;
import com.kp.cms.handlers.admin.DateOfBirthPrintHandler;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class DateOfBirthPrintAction  extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(DateOfBirthPrintAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPrintDOB(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		DateOfBirthPrintForm dateOfBirthPrintForm = (DateOfBirthPrintForm) form;
		dateOfBirthPrintForm.setDate(CommonUtil.getTodayDate());
		dateOfBirthPrintForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_PRINTDOB);
		
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
	public ActionForward printDOB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DateOfBirthPrintForm dateOfBirthPrintForm = (DateOfBirthPrintForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = dateOfBirthPrintForm.validate(mapping, request);
		HttpSession session = request.getSession();
		
		try {
			if(dateOfBirthPrintForm.getRegNoFrom() == null || dateOfBirthPrintForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(dateOfBirthPrintForm.getRegNoTo() == null || dateOfBirthPrintForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(dateOfBirthPrintForm.getDate() == null || dateOfBirthPrintForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_PRINTDOB);
			}
			/*boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(dateOfBirthPrintForm.getRegNoFrom().trim(), dateOfBirthPrintForm.getRegNoTo().trim());*/ 
			DateOfBirthPrintHandler.getInstance().getAddressPrintDetails(dateOfBirthPrintForm, request);
			if(dateOfBirthPrintForm.getMessageList() == null || dateOfBirthPrintForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_PRINTDOB);
			}else{
				session.setAttribute("DOBMessageList", dateOfBirthPrintForm.getMessageList());
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				dateOfBirthPrintForm.setErrorMessage(msg);
				dateOfBirthPrintForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}	
		dateOfBirthPrintForm.setPrintPage("true");
		return mapping.findForward(CMSConstants.INIT_PRINTDOB);
	
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward PrintDOBCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	DateOfBirthPrintForm dateOfBirthPrintForm = (DateOfBirthPrintForm) form;
	HttpSession session = request.getSession();
	dateOfBirthPrintForm.setMessageList((List)session.getAttribute("DOBMessageList"));
	dateOfBirthPrintForm.reset(mapping, request);
	return mapping.findForward(CMSConstants.SHOW_DOB);
	
}
	/**
	 * 
	 * @param regdNoFrom
	 * @param regdNoTo
	 * @return Used to validate regd nos.
	 * Only alphanumeric is allowed
	 * @throws Exception
	 */
	private boolean validateRegdNos(String regdNoFrom, String regdNoTo) throws Exception{
		log.info("Entering into validateRegdNos");
		if(StringUtils.isAlphanumeric(regdNoFrom) && StringUtils.isAlphanumeric(regdNoTo)){
			return true;
		}
		else{
			log.info("Leaving into validateRegdNos");
			return false;
		}
	}	
}
