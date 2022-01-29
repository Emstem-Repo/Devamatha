package com.kp.cms.actions.timetable;

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
import com.kp.cms.forms.timetable.TimeAllocationForm;
import com.kp.cms.handlers.timetable.TimeAllocationHandler;
import com.kp.cms.to.timetable.TimeAllocationTo;
import com.kp.cms.utilities.CommonUtil;

public class TimeAllocationAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(TimeAllocationAction.class);
	TimeAllocationHandler handler = TimeAllocationHandler.getinstance();

	public ActionForward initTimeAllocation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Method of StaffAllocationAction.");
		TimeAllocationForm objForm = (TimeAllocationForm) form;
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of StaffAllocationAction.");
		return mapping.findForward(CMSConstants.INIT_TIME_ALLOCATION);
	}

	private void setDefaultDataToScreen(TimeAllocationForm objForm) {
		objForm.setTeachingStaffList(handler.getTeachingStaffList());
	}

	public ActionForward fetchData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into init Method of StaffAllocationAction.");
		TimeAllocationForm objForm = (TimeAllocationForm) form;
		ActionErrors errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objForm);
		if (errors.isEmpty()) {
			objForm.setViewTo(handler.fetchData(objForm.getAcademicYr(),
					Integer.parseInt(objForm.getTeachingStaff()), objForm
							.getUserId()));
			return mapping.findForward(CMSConstants.TIME_ALLOCATION_ENTRY);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of StaffAllocationAction.");
		return mapping.findForward(CMSConstants.INIT_TIME_ALLOCATION);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeAllocationForm objForm = (TimeAllocationForm) form;
		log.info("entering into add Method of StaffAllocationAction Class.");
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		errors = validateData(objForm, errors);
		setUserId(request, objForm);
		if (errors.isEmpty()) {

			int added = handler.addData(objForm);
			if (added > 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamPromotionCriteria.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			objForm.clearPage();
			return mapping.findForward(CMSConstants.INIT_TIME_ALLOCATION);
		} else {
			saveErrors(request, errors);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of add of StaffAllocationAction Class.");
		return mapping.findForward(CMSConstants.TIME_ALLOCATION_ENTRY);
	}

	private ActionErrors validateData(TimeAllocationForm objForm,
			ActionErrors errors) {
		TimeAllocationTo to = objForm.getViewTo();
		if (checkForEmpty(to.getSubjectId())) {
			errors.add("error", new ActionError(
					"admissionFormForm.subject.required"));
		}
		if (checkForEmpty(to.getDay())) {
			errors.add("error", new ActionError("knowledgepro.TimeTable.Day"));
		}
		if (checkForEmpty(to.getRoomCodeId())) {
			errors.add("error", new ActionError(
					"knowledgepro.TimeTable.roomCode"));
		}
		if (to.getPeriods() != null && to.getPeriods().length > 0) {
			String[] periods = to.getPeriods();
			if (periods[0] != null && periods[0].equals("")) {
				errors.add("error", new ActionError(
						"knowledgepro.TimeTable.periods"));
			}
		}
		return errors;
	}

	private boolean checkForEmpty(Integer fieldValue) {
		if (fieldValue == 0 || fieldValue == null) {
			return true;
		} else {
			return false;
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into edit Method of TimeAllocationAction Class.");
		TimeAllocationForm objForm = (TimeAllocationForm) form;
		ActionMessages messages = new ActionMessages();
		int delete = handler.delete(objForm.getId());
		if (delete > 0) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.ExamPromotionCriteria.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of edit of TimeAllocationAction Class.");
		return mapping.findForward(CMSConstants.INIT_TIME_ALLOCATION);
	}

}