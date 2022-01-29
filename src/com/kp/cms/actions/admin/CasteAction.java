package com.kp.cms.actions.admin;

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
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ReligionTO;

/**
 * DispatchAction to manage Add, Edit, Delete actions for Caste.
 * @author prashanth.mh
 */
public class CasteAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CasteAction.class);
	/**
	 * Performs the get Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward initgetCaste(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CasteForm casteForm = (CasteForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Caste");
		try{
			
	   		
	    setReligionListToRequest(request); //vibin for admission
		List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
		casteForm.setCasteList(casteList);
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				casteForm.setErrorMessage(msg);
				casteForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		return mapping.findForward(CMSConstants.CASTE_ENTRY);
	}
	
	/**
	 * Performs the add Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward addCaste(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CasteForm casteForm = (CasteForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = casteForm.validate(mapping, request);

		boolean isCasteAdded = false;
		if (errors.isEmpty()) {
			String casteName = casteForm.getCasteName();
			try{
			setUserId(request, casteForm);
			//String name=casteForm.getCasteName();
			isCasteAdded = CasteHandler.getInstance().addCaste(casteForm,request);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.Caste.name.exists",casteName));
					saveErrors(request, errors);
					
					setReligionListToRequest(request);
					List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
					casteForm.setCasteList(casteList);
					return mapping.findForward(CMSConstants.CASTE_ENTRY);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.Caste.addfailure.alreadyexist.reactivate",casteName));
					saveErrors(request, errors);
					
					setReligionListToRequest(request);
					List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
					casteForm.setCasteList(casteList);
					return mapping.findForward(CMSConstants.CASTE_ENTRY);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				casteForm.setErrorMessage(msg);
				casteForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isCasteAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.Caste.addsuccess", casteForm.getCasteName());
				messages.add("messages", message);
				saveMessages(request, messages);
				casteForm.reset(mapping, request);
				
				setReligionListToRequest(request);
				List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
				casteForm.setCasteList(casteList);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.Caste.addfailure",  casteForm.getCasteName()));
				saveErrors(request, errors);
				
				setReligionListToRequest(request);
				List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
				casteForm.setCasteList(casteList);
			}
		} else {
			saveErrors(request, errors);
			setReligionListToRequest(request);
			List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
			casteForm.setCasteList(casteList);
			return mapping.findForward(CMSConstants.CASTE_ENTRY);
		}
		
		
		return mapping.findForward(CMSConstants.CASTE_ENTRY);
	}

	/**
	 * Performs the update Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward updateCaste(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CasteForm casteForm = (CasteForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = casteForm.validate(mapping, request);
		String name=casteForm.getCasteName();
		boolean isCasteEdited = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, casteForm);
			
				isCasteEdited=CasteHandler.getInstance().updateCaste(casteForm,request);

			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.Caste.name.exists",name));
					saveErrors(request, errors);
					setReligionListToRequest(request);
					List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
					casteForm.setCasteList(casteList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.CASTE_ENTRY);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.Caste.addfailure.alreadyexist.reactivate",name));
					saveErrors(request, errors);
					setReligionListToRequest(request);
					List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
					casteForm.setCasteList(casteList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.CASTE_ENTRY);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				casteForm.setErrorMessage(msg);
				casteForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		} else {
			saveErrors(request, errors);
			request.setAttribute("operation", "edit");
			setReligionListToRequest(request);
			List<CasteTO> casteList = CasteHandler
			.getInstance().getCastes();
			casteForm.setCasteList(casteList);
			return mapping.findForward(CMSConstants.CASTE_ENTRY);
		}
		
		
		if (isCasteEdited) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.Caste.updatesuccess", casteForm.getCasteName());
			messages.add("messages", message);
			saveMessages(request, messages);
			casteForm.reset(mapping, request);
			setReligionListToRequest(request);
			List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
			casteForm.setCasteList(casteList);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.Caste.updatefailure",  casteForm.getCasteName()));
			saveErrors(request, errors);
			setReligionListToRequest(request);
			List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
			casteForm.setCasteList(casteList);
		}
		return mapping.findForward(CMSConstants.CASTE_ENTRY);
	}

	/**
	 * Performs the delete Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward deleteCaste(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CasteForm casteForm = (CasteForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int casteId = casteForm.getCasteId();
		String name=casteForm.getCasteName();
		boolean isCasteDeleted=false;
		try{
			setUserId(request, casteForm);
		isCasteDeleted = CasteHandler.getInstance()
				.deleteCaste(casteId,casteForm.getUserId());
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			casteForm.setErrorMessage(msg);
			casteForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		
		if (isCasteDeleted) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.Caste.deletesuccess", name);
			messages.add("messages", message);
			saveMessages(request, messages);
			casteForm.reset(mapping, request);
			setReligionListToRequest(request);
			List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
			casteForm.setCasteList(casteList);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.Caste.deletefailure",name));
			saveErrors(request, errors);
			List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
			setReligionListToRequest(request);
			casteForm.setCasteList(casteList);
		}
		return mapping.findForward(CMSConstants.CASTE_ENTRY);
	}
	
	/**
	 * Performs the reActivate Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward reActivateCaste(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CasteForm casteForm = (CasteForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		Caste caste=(Caste)request.getSession().getAttribute("Caste");
		boolean isCasteReActivate=false;
		try{
			setUserId(request, casteForm);
			isCasteReActivate = CasteHandler.getInstance()
				.reActivateCaste(caste,casteForm.getUserId());
		}catch (Exception e) {
			log.error("Error occured in caste Entry Action", e);
			String msg = super.handleApplicationException(e);
			casteForm.setErrorMessage(msg);
			casteForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		
		if (isCasteReActivate) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.Caste.activate");
			messages.add("messages", message);
			saveMessages(request, messages);
			casteForm.reset(mapping, request);
			setReligionListToRequest(request);
			List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
			casteForm.setCasteList(casteList);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.Caste.activatefailure"));
			saveErrors(request, errors);
			
			setReligionListToRequest(request); //vibin for admission
			List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
			casteForm.setCasteList(casteList);
		}
		request.getSession().removeAttribute("Caste");
		return mapping.findForward(CMSConstants.CASTE_ENTRY);
	}
	
	
	public void setReligionListToRequest(HttpServletRequest request) {
		List<ReligionTO> religionList = ReligionHandler.getInstance().getReligion();
		request.setAttribute("religionList", religionList);
		log.debug("leaving setReligionListToRequest in Action");
	}
}