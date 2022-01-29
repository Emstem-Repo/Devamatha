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
import com.kp.cms.forms.admin.AddressPrintForm;
import com.kp.cms.handlers.admin.AddressPrintHandler;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class AddressPrintAction  extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AddressPrintAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPrintAddress(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		AddressPrintForm addressPrintForm = (AddressPrintForm) form;
		addressPrintForm.setDate(CommonUtil.getTodayDate());
		addressPrintForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_ADDRESS);
		
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
	public ActionForward printAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AddressPrintForm addressPrintForm = (AddressPrintForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = addressPrintForm.validate(mapping, request);
		HttpSession session = request.getSession();
		
		try {
			if(addressPrintForm.getRegNoFrom() == null || addressPrintForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(addressPrintForm.getRegNoTo() == null || addressPrintForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(addressPrintForm.getDate() == null || addressPrintForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRINT_ADDRESS);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(addressPrintForm.getRegNoFrom().trim(), addressPrintForm.getRegNoTo().trim()); 
			if(!isValidRegdNo){
				if(addressPrintForm.getIsRollNo()!= null && "true".equalsIgnoreCase(addressPrintForm.getIsRollNo())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_ROLLNO_TYPE));
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				}
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRINT_ADDRESS);
			}
			AddressPrintHandler.getInstance().getAddressPrintDetails(addressPrintForm, request);
			if(addressPrintForm.getMessageList() == null || addressPrintForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRINT_ADDRESS);
			}else{
				session.setAttribute("AddressMessageList", addressPrintForm.getMessageList());
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				addressPrintForm.setErrorMessage(msg);
				addressPrintForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		addressPrintForm.setPrintPage("true");
		return mapping.findForward(CMSConstants.PRINT_ADDRESS);
	
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward PrintAddressCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	AddressPrintForm addressPrintForm = (AddressPrintForm) form;
	HttpSession session = request.getSession();
	addressPrintForm.setMessageList((List)session.getAttribute("AddressMessageList"));
	addressPrintForm.reset(mapping, request);
	return mapping.findForward(CMSConstants.SHOW_ADDRESS);
	
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
