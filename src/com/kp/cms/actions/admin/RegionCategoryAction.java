package com.kp.cms.actions.admin;

/**
 * 
 *  Date Created : 20 Jan 2009 This action class used for Region Category
 *         related config operation
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.RegionCategoryForm;
import com.kp.cms.handlers.admin.RegionCategoryHandler;

@SuppressWarnings("deprecation")
public class RegionCategoryAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(RegionCategoryAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set regionList having regionTo objects to request, forward to
	 *         admittedThroughEntry
	 * @throws Exception
	 */
	public ActionForward initRegionCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering initRegionCategory");
		RegionCategoryForm regionCategoryForm = (RegionCategoryForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setregionCategoryToRequest(request);
		} catch (Exception e) {
			log.error("error initRegionCategory page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				regionCategoryForm.setErrorMessage(msg);
				regionCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initRegionCategory ");

		return mapping.findForward(CMSConstants.REGION_CATEGORY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Region Category
	 * @return to mapping religion entry
	 * @throws Exception
	 */

	public ActionForward addRegionCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside addRegionCategory Action");
		RegionCategoryForm regionCategoryForm = (RegionCategoryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = regionCategoryForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setregionCategoryToRequest(request);
				return mapping.findForward(CMSConstants.REGION_CATEGORY);
			}

			isAdded = RegionCategoryHandler.getInstance().addRegionCategory(
					regionCategoryForm, "Add");
			setregionCategoryToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(CMSConstants.RREGION_EXIST, regionCategoryForm.getName() ));
			saveErrors(request, errors);
			setregionCategoryToRequest(request);
			return mapping.findForward(CMSConstants.REGION_CATEGORY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					CMSConstants.REGION_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setregionCategoryToRequest(request);
			return mapping.findForward(CMSConstants.REGION_CATEGORY);
		} catch (Exception e) {
			log.error("error in final submit of region page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				regionCategoryForm.setErrorMessage(msg);
				regionCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.regcategory.addsuccess",
					regionCategoryForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			regionCategoryForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.admin.regcategory.addfailure",
					regionCategoryForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addRegionCategory Action");
		return mapping.findForward(CMSConstants.REGION_CATEGORY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update Region Category
	 * @return to mapping religion entry
	 * @throws Exception
	 */
	public ActionForward editRegionCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside editRegionCategory Action");
		RegionCategoryForm regionCategoryForm = (RegionCategoryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = regionCategoryForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setregionCategoryToRequest(request);
				request.setAttribute("regionOperation", "edit");
				return mapping.findForward(CMSConstants.REGION_CATEGORY);
			}

			isAdded = RegionCategoryHandler.getInstance().addRegionCategory(
					regionCategoryForm, "Edit");

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(CMSConstants.RREGION_EXIST, regionCategoryForm.getName()));
			saveErrors(request, errors);
			setregionCategoryToRequest(request);
			request.setAttribute("regionOperation", "edit");
			return mapping.findForward(CMSConstants.REGION_CATEGORY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					CMSConstants.REGION_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setregionCategoryToRequest(request);
			request.setAttribute("regionOperation", "edit");
			return mapping.findForward(CMSConstants.REGION_CATEGORY);
		} catch (Exception e) {
			log.error("error in final edit submit of region page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				regionCategoryForm.setErrorMessage(msg);
				regionCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setregionCategoryToRequest(request);
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.regcategory.updatesuccess",
					regionCategoryForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			regionCategoryForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.admin.regcategory.updatefailure",
					regionCategoryForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving editRegionCategory Action");
		request.setAttribute("regionOperation", "edit");
		return mapping.findForward(CMSConstants.REGION_CATEGORY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing Region
	 * @return ActionForward This action method will called when particular
	 *         Region need to be deleted based on the Region id.
	 * @throws Exception
	 */
	public ActionForward deleteRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("inside Delete Region Action");
		RegionCategoryForm regionCategoryForm = (RegionCategoryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (regionCategoryForm.getRegionId() != null) {
				int regionId = Integer.parseInt(regionCategoryForm
						.getRegionId());
				isDeleted = RegionCategoryHandler.getInstance().deleteRegion(
						regionId, false);
			}
		} catch (Exception e) {
			log.error("error in delete Region page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				regionCategoryForm.setErrorMessage(msg);
				regionCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		setregionCategoryToRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.regcategory.deletesuccess",
					regionCategoryForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			regionCategoryForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError(
					"knowledgepro.admin.regcategory.deletefailure",
					regionCategoryForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("inside Delete Region Category Action");
		return mapping.findForward(CMSConstants.REGION_CATEGORY);
	}

	public void setregionCategoryToRequest(HttpServletRequest request)
			throws Exception {
		List regionCategoryList = RegionCategoryHandler.getInstance()
				.getRegionCategory();
		request.setAttribute("regionCategoryList", regionCategoryList);
		log.debug("No of Region Category" + regionCategoryList.size());
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This action method Reactivate the Region.
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward activateRegion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering activateRegion");
		RegionCategoryForm regionCategoryForm = (RegionCategoryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (regionCategoryForm.getRegionId() != null) {
				int regionId = Integer.parseInt(regionCategoryForm.getRegionId());
				isActivated = RegionCategoryHandler.getInstance().deleteRegion(regionId, true);
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(
					CMSConstants.REGION_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setregionCategoryToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(
					CMSConstants.REGION_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateRegion");
		return mapping.findForward(CMSConstants.REGION_CATEGORY);
	}

	
	
}
