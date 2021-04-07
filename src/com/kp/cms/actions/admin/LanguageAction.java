package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.LanguageForm;
import com.kp.cms.handlers.admin.LanguageHandler;
import com.kp.cms.to.admin.LanguageTO;

/**
 *  A DispatchAction to manages Add,edit, delete actions for  Language. 
 *  @version 1.0 12 Jan 2009
 */
public class LanguageAction  extends BaseDispatchAction {
	

	/**
     * 
     * Performs the add Language action.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	public ActionForward addLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages actionMessages = new ActionMessages();
		
		LanguageForm languageForm = (LanguageForm) form;		
		ActionErrors actionErrors = languageForm.validate(mapping, request);
		
		if (actionErrors.isEmpty()) {
			String languageName = languageForm.getLanguageName();
			LanguageHandler.getHandler().addLanguage(languageName);
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.addsuccess", CMSConstants.MOTHER_TONGUE_FORM);
			actionMessages.add("actionMessages", message);
			saveMessages(request, actionMessages);
			languageForm.reset(mapping, request);
		} else {
			addErrors(request, actionErrors);
		}

		List<LanguageTO> languageList = LanguageHandler.getHandler()
				.getLanguages();
		languageForm.setLanguageList(languageList);
		return mapping.findForward(CMSConstants.LANGUAGE_ENTRY);

	}
	
	/**
	 * Performs the edit Language action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward editLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages actionMessages = new ActionMessages();
		
		LanguageForm languageForm = (LanguageForm) form;
		ActionErrors actionErrors = languageForm.validate(mapping, request);
		if(actionErrors.isEmpty()) {
			String languageName = languageForm.getLanguageName();
			int languageId = languageForm.getLanguageId();
			LanguageHandler.getHandler().editLanguage(languageId, languageName);
			
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.updatesuccess", CMSConstants.MOTHER_TONGUE_FORM);
			actionMessages.add("actionMessages", message);
			saveMessages(request, actionMessages);
			
			
			languageForm.reset(mapping, request);
		} else {
			addErrors(request, actionErrors);
		}
		
		List<LanguageTO> languageList = LanguageHandler.getHandler()
				.getLanguages();
		languageForm.setLanguageList(languageList);
	

		return mapping.findForward(CMSConstants.LANGUAGE_ENTRY);

	}
	
	/**
	 * Performs the delete Language action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward deleteLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LanguageForm languageForm = (LanguageForm) form;
		ActionMessages actionMessages = new ActionMessages();
		
		int languageId = languageForm.getLanguageId();
		LanguageHandler.getHandler().deleteLanguage(languageId);
		ActionMessage message = new ActionMessage(
				"knowledgepro.admin.deletesuccess", CMSConstants.MOTHER_TONGUE_FORM);
		actionMessages.add("actionMessages", message);
		saveMessages(request, actionMessages);
		List<LanguageTO> languageList = LanguageHandler.getHandler()
				.getLanguages();
		languageForm.setLanguageList(languageList);

		return mapping.findForward(CMSConstants.LANGUAGE_ENTRY);

	}
	
	/**
	 * Performs the get Language action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward getLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LanguageForm languageForm = (LanguageForm) form;
		List<LanguageTO> languageList = LanguageHandler.getHandler().getLanguages();
		languageForm.setLanguageList(languageList);
		return mapping.findForward(CMSConstants.LANGUAGE_ENTRY);
		
	}	

}
