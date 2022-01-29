package com.kp.cms.actions.timetable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.timetable.ManageWorkingDaysForm;
import com.kp.cms.handlers.timetable.ManageWorkingDaysHandler;

public class ManageWorkingDaysAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(ManageWorkingDaysAction.class);
	ManageWorkingDaysHandler handler = ManageWorkingDaysHandler.getinstance();

	/**
	 * 
	 * Performs the initialize Manage HolidayList Action.
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
	public ActionForward initManageWorkingDays(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Method of ManageWorkingDaysAction.");
		ManageWorkingDaysForm objForm = (ManageWorkingDaysForm) form;
		objForm.clearPage();
		log.info("exit of initManageHolidayList of ManageWorkingDaysAction.");
		return mapping.findForward(CMSConstants.INIT_MANAGE_WORKING_DAYS);
	}

}