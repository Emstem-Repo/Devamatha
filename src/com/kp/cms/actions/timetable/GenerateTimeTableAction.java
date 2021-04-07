package com.kp.cms.actions.timetable;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.timetable.GenerateTimeTableForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.timetable.GenerateTimeTableHandler;

public class GenerateTimeTableAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(GenerateTimeTableAction.class);
	GenerateTimeTableHandler handler = GenerateTimeTableHandler.getinstance();

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
	public ActionForward initGenerateTimeTable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Method of GenerateTimeTableAction.");
		GenerateTimeTableForm objForm = (GenerateTimeTableForm) form;
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of GenerateTimeTableAction.");
		return mapping.findForward(CMSConstants.INIT_GENERATE_TIME_TABLE);
	}

	private void setDefaultDataToScreen(GenerateTimeTableForm objForm) {
		objForm.setClassMap(CommonAjaxHandler.getInstance().getClassesByYear(
				new Date().getYear() + 1900));
	}

	public ActionForward fetchData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into init Method of GenerateTimeTableAction.");
		GenerateTimeTableForm objForm = (GenerateTimeTableForm) form;
		ActionErrors errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objForm);
		if (errors.isEmpty()) {
			objForm.setListTo(handler.fetchData(objForm));
			return mapping
					.findForward(CMSConstants.GENERATED_TIME_TABLE_DISPLAY);
		}

		log.info("exit of initStaffAllocation of GenerateTimeTableAction.");
		return mapping.findForward(CMSConstants.INIT_GENERATE_TIME_TABLE);
	}
}