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
import com.kp.cms.forms.admin.OccupationForm;
import com.kp.cms.handlers.admin.OccupationTransactionHandler;
import com.kp.cms.to.admin.OccupationTO;

/**
 *  A DispatchAction to manages Add,edit, delete actions for Occupation. 
 *  @version 1.0 12 Jan 2009
 */
public class OccupationAction extends BaseDispatchAction {

	/**
	 * 
	 * Performs the add Occupation action.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward addOccupation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages actionMessages = new ActionMessages();
		
		OccupationForm occupationForm = (OccupationForm) form;
		ActionErrors actionErrors = occupationForm.validate(mapping, request);
		if (actionErrors.isEmpty()) {
			String occupationName = occupationForm.getOccupationName();

			OccupationTransactionHandler.getInstance().addOccupation(
					occupationName);
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.addsuccess", CMSConstants.OCCUPATION_FORM);
			actionMessages.add("actionMessages", message);
			saveMessages(request, actionMessages);
			
			occupationForm.reset(mapping, request);
		} else {

			addErrors(request, actionErrors);
		}
		List<OccupationTO> occupationList = OccupationTransactionHandler
				.getInstance().getAllOccupation();
		occupationForm.setOccupationList(occupationList);
		return mapping.findForward(CMSConstants.OCCUPATION_ENTRY);

	}

	/**
	 * Performs the edit Occupation action.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred.
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward editOccupation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages actionMessages = new ActionMessages();
		
		OccupationForm occupationForm = (OccupationForm) form;
		ActionErrors actionErrors = occupationForm.validate(mapping, request);
		if (actionErrors.isEmpty()) {
			String occupationName = occupationForm.getOccupationName();
			int occupationId = occupationForm.getOccupationId();
			OccupationTransactionHandler.getInstance().editOccupation(
					occupationId, occupationName);
			
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.updatesuccess", CMSConstants.OCCUPATION_FORM);
			actionMessages.add("actionMessages", message);
			saveMessages(request, actionMessages);
			
			occupationForm.reset(mapping, request);
		} else {
			addErrors(request, actionErrors);
		}
		List<OccupationTO> occupationList = OccupationTransactionHandler
				.getInstance().getAllOccupation();
		occupationForm.setOccupationList(occupationList);

		return mapping.findForward(CMSConstants.OCCUPATION_ENTRY);

	}

	/**
	 * Performs the delete Occupation action.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred.
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward deleteOccupation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages actionMessages = new ActionMessages();
		
		OccupationForm occupationForm = (OccupationForm) form;
		int occupationId = occupationForm.getOccupationId();
		OccupationTransactionHandler.getInstance().deleteOccupation(
				occupationId);
		ActionMessage message = new ActionMessage(
				"knowledgepro.admin.deletesuccess", CMSConstants.OCCUPATION_FORM);
		actionMessages.add("actionMessages", message);
		saveMessages(request, actionMessages);
		
		List<OccupationTO> occupationList = OccupationTransactionHandler
				.getInstance().getAllOccupation();
		occupationForm.setOccupationList(occupationList);

		return mapping.findForward(CMSConstants.OCCUPATION_ENTRY);

	}

	/**
	 * Performs the get Occupation action.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred.
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward getOccupation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OccupationForm occupationForm = (OccupationForm) form;
		List<OccupationTO> occupationList = OccupationTransactionHandler.getInstance()
				.getAllOccupation();
		occupationForm.setOccupationList(occupationList);
		return mapping.findForward(CMSConstants.OCCUPATION_ENTRY);

	}


}
