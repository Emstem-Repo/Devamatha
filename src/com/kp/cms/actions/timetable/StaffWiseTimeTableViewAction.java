package com.kp.cms.actions.timetable;

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
import com.kp.cms.forms.timetable.StaffWiseTimeTableViewForm;
import com.kp.cms.handlers.timetable.StaffWiseTimeTableViewHandler;

public class StaffWiseTimeTableViewAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(StaffWiseTimeTableViewAction.class);
	StaffWiseTimeTableViewHandler handler = StaffWiseTimeTableViewHandler
			.getinstance();

	public ActionForward initStaffWiseTimeTableView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Method of StaffAllocationAction.");
		StaffWiseTimeTableViewForm objForm = (StaffWiseTimeTableViewForm) form;
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of StaffAllocationAction.");
		return mapping.findForward(CMSConstants.INIT_STAFF_WISE_TIME_TABLE);
	}

	private void setDefaultDataToScreen(StaffWiseTimeTableViewForm objForm) {
		objForm.setTeachingStaffList(handler.getTeachingStaffList());
	}

	public ActionForward fetchData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into init Method of StaffAllocationAction.");
		StaffWiseTimeTableViewForm objForm = (StaffWiseTimeTableViewForm) form;
		ActionErrors errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objForm);
		if (errors.isEmpty()) {
			handler.fetchData(objForm);
			return mapping
					.findForward(CMSConstants.STAFF_WISE_TIME_TABLE_DISPLAY);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of StaffAllocationAction.");
		return mapping.findForward(CMSConstants.INIT_STAFF_WISE_TIME_TABLE);
	}

}