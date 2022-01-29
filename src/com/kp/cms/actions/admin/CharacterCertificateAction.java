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
import com.kp.cms.forms.admin.CharacterCertificateForm;
import com.kp.cms.handlers.admin.CharacterCertificateHandler;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class CharacterCertificateAction  extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(CharacterCertificateAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPrintCharacter(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		CharacterCertificateForm characterCertificateForm = (CharacterCertificateForm) form;
		characterCertificateForm.setDate(CommonUtil.getTodayDate());
		characterCertificateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE);
		
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
	public ActionForward printCharacter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CharacterCertificateForm characterCertificateForm = (CharacterCertificateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = characterCertificateForm.validate(mapping, request);
		HttpSession session = request.getSession();
		
		try {
			if(characterCertificateForm.getRegNoFrom() == null || characterCertificateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(characterCertificateForm.getRegNoTo() == null || characterCertificateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(characterCertificateForm.getDate() == null || characterCertificateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CERTIFICATE);
			}
			/*boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(characterCertificateForm.getRegNoFrom().trim(), characterCertificateForm.getRegNoTo().trim()); */
			CharacterCertificateHandler.getInstance().getAddressPrintDetails(characterCertificateForm, request);
			if(characterCertificateForm.getMessageList() == null || characterCertificateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				characterCertificateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_CERTIFICATE);
			}else{
				session.setAttribute("CHARMessageList", characterCertificateForm.getMessageList());
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				characterCertificateForm.setErrorMessage(msg);
				characterCertificateForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}	
		characterCertificateForm.setPrintPage("true");
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE);
	
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printCharacterCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	CharacterCertificateForm characterCertificateForm = (CharacterCertificateForm) form;
	HttpSession session = request.getSession();
	characterCertificateForm.setMessageList((List)session.getAttribute("CHARMessageList"));
	characterCertificateForm.reset(mapping, request);
	return mapping.findForward(CMSConstants.SHOW_CERTIFICATE);
	
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
