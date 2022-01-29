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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.timetable.DurationAllocationForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.timetable.DurationAllocationHandler;

public class DurationAllocationAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(DurationAllocationAction.class);
	DurationAllocationHandler handler = DurationAllocationHandler.getinstance();

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
	public ActionForward initDurationAllocation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Method of StaffAllocationAction.");
		DurationAllocationForm objForm = (DurationAllocationForm) form;
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of StaffAllocationAction.");
		return mapping.findForward(CMSConstants.INIT_DURATION_ALLOCATION);
	}

	private void setDefaultDataToScreen(DurationAllocationForm objForm) {
		objForm.setCourseList(new CommonAjaxExamHandler()
				.getCoursesByAcademicYear(new Date().getYear() + 1900));
	}

	public ActionForward fetchData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into init Method of StaffAllocationAction.");
		DurationAllocationForm objForm = (DurationAllocationForm) form;
		ActionErrors errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			objForm
					.setBottomGrid(handler.fetchData(objForm.getAcademicYr(),
							Integer.parseInt(objForm.getCourse()), objForm
									.getUserId()));
			return mapping.findForward(CMSConstants.DURATION_ALLOCATION_ENTRY);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of StaffAllocationAction.");
		return mapping.findForward(CMSConstants.INIT_DURATION_ALLOCATION);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DurationAllocationForm objForm = (DurationAllocationForm) form;
		log.info("entering into add Method of StaffAllocationAction Class.");

		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		setUserId(request, objForm);
		if (errors.isEmpty()) {
			int added = handler.addData(objForm.getBottomGrid(), objForm
					.getAcademicYr(), Integer.parseInt(objForm.getCourse()),
					objForm.getUserId());
			if (added > 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamPromotionCriteria.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			objForm.clearPage();
			return mapping.findForward(CMSConstants.INIT_DURATION_ALLOCATION);
		} else {
			saveErrors(request, errors);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of add of StaffAllocationAction Class.");
		return mapping.findForward(CMSConstants.DURATION_ALLOCATION_ENTRY);
	}
}