package com.kp.cms.actions.timetable;

import java.util.ArrayList;
import java.util.Date;

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
import com.kp.cms.forms.timetable.StaffAllocationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.timetable.StaffAllocationHandler;

public class StaffAllocationAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(StaffAllocationAction.class);
	StaffAllocationHandler handler = StaffAllocationHandler.getinstance();

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
	public ActionForward initStaffAllocation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Method of StaffAllocationAction.");
		StaffAllocationForm objForm = (StaffAllocationForm) form;
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of StaffAllocationAction.");
		return mapping.findForward(CMSConstants.INIT_STAFF_ALLOCATION);
	}

	private void setDefaultDataToScreen(StaffAllocationForm objForm) {
		objForm.setTeachingStaffList(handler.getTeachingStaffList());
		objForm.setBottomGrid(handler.setBottomGrid());
		objForm.setListOfClasses(CommonAjaxHandler.getInstance()
				.getClassesByYear(new Date().getYear() + 1900));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StaffAllocationForm objForm = (StaffAllocationForm) form;
		log.info("entering into add Method of StaffAllocationAction Class.");

		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		setUserId(request, objForm);
		if (errors.isEmpty()) {
			ArrayList<String> duplicateMessageList = new ArrayList<String>();
			int added = handler.addData(Integer.parseInt(objForm
					.getTeachingStaff()), objForm.getAcademicYr(), objForm
					.getClassName(), objForm.getClassValues(), objForm
					.getUserId(), duplicateMessageList);
			if (added > 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamPromotionCriteria.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			if (duplicateMessageList != null && duplicateMessageList.size() > 0) {
				errors.add("error", new ActionError(
						"knowledgepro.TimeTable.duplicateValue"));
				saveErrors(request, errors);
			}
			objForm.clearPage();
		} else {
			saveErrors(request, errors);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of add of StaffAllocationAction Class.");
		return mapping.findForward(CMSConstants.INIT_STAFF_ALLOCATION);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into edit Method of StaffAllocationAction Class.");
		StaffAllocationForm objForm = (StaffAllocationForm) form;
		ActionMessages messages = new ActionMessages();
		int delete = handler.delete(objForm.getId());
		if (delete > 0) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.ExamPromotionCriteria.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of edit of StaffAllocationAction Class.");
		return mapping.findForward(CMSConstants.INIT_STAFF_ALLOCATION);
	}

}