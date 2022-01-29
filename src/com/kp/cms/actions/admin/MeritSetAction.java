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
import com.kp.cms.forms.admin.MeritSetForm;
import com.kp.cms.handlers.admin.MeritSetHandler;
import com.kp.cms.to.admin.MeritSetTO;

/**
 *  A DispatchAction to manages Add,edit, delete actions for merit set. 
 *  @version 1.0 12 Jan 2009
 */
public class MeritSetAction  extends BaseDispatchAction {
	
	
	/**
     * 
     * Performs the add Merit set action.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	public ActionForward addMeritSet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();
		MeritSetForm meritSetForm = (MeritSetForm) form;
		ActionErrors errors = meritSetForm.validate(mapping, request);

		if (errors.isEmpty()) {
			String meritSetName = meritSetForm.getMeritSetName();
			MeritSetHandler.getInstance().addMeritSet(meritSetName);
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.addsuccess", CMSConstants.MERIT_SET_FORM);
			messages.add("messages", message);
			saveMessages(request, messages);
			meritSetForm.reset(mapping, request);
		} else {
			addErrors(request, errors);
		}
		List<MeritSetTO> meritSetList = MeritSetHandler.getInstance()
				.getAllMeritSet();
		meritSetForm.setMeritSetList(meritSetList);
		return mapping.findForward(CMSConstants.MERIT_SET_ENTRY);

	}
	
	/**
	 * Performs the edit Merit set action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward editMeritSet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		
		MeritSetForm meritSetForm = (MeritSetForm) form;
		ActionErrors actionErrors = meritSetForm.validate(mapping, request);
		
		if(actionErrors.isEmpty()) {
			String meritSetName = meritSetForm.getMeritSetName();
			int meritsetId = meritSetForm.getMeritSetId();
			MeritSetHandler.getInstance().editMeritSet(meritsetId,meritSetName);
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.updatesuccess", CMSConstants.MERIT_SET_FORM);
			messages.add("messages", message);
			saveMessages(request, messages);
			meritSetForm.reset(mapping, request);
		} else {
			addErrors(request, actionErrors);
		}
		
		List<MeritSetTO> meritSetList = MeritSetHandler.getInstance().getAllMeritSet();
		meritSetForm.setMeritSetList(meritSetList);
		
		
		return mapping.findForward(CMSConstants.MERIT_SET_ENTRY);
		
	}
	
	/**
	 * Performs the delete Merit set action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward deleteMeritSet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MeritSetForm meritSetForm = (MeritSetForm) form;
		ActionMessages messages = new ActionMessages();
		
		int meritsetId = meritSetForm.getMeritSetId();
		MeritSetHandler.getInstance().deleteMeritSet(meritsetId);
		ActionMessage message = new ActionMessage(
				"knowledgepro.admin.deletesuccess", CMSConstants.MERIT_SET_FORM);
		messages.add("messages", message);
		saveMessages(request, messages);
		List<MeritSetTO> meritSetList = MeritSetHandler.getInstance()
				.getAllMeritSet();
		meritSetForm.setMeritSetList(meritSetList);

		return mapping.findForward(CMSConstants.MERIT_SET_ENTRY);

	}
	
	/**
	 * Performs the get Merit set action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward getMeritSet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MeritSetForm meritSetForm = (MeritSetForm) form;
		List<MeritSetTO> meritSetList = MeritSetHandler.getInstance().getAllMeritSet();
		meritSetForm.setMeritSetList(meritSetList);
		return mapping.findForward(CMSConstants.MERIT_SET_ENTRY);
		
	}
	
}
