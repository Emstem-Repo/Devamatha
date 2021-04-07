package com.kp.cms.actions.timetable;

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
import com.kp.cms.forms.timetable.ManageTimeTableForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.timetable.ManageTimeTableHandler;
import com.kp.cms.to.timetable.ManageTimeTableTo;

public class ManageTimeTableAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(ManageTimeTableAction.class);
	ManageTimeTableHandler handler = ManageTimeTableHandler.getinstance();

	public ActionForward initManageTimeTable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Method of StaffAllocationAction.");
		ManageTimeTableForm objForm = (ManageTimeTableForm) form;
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of StaffAllocationAction.");
		return mapping.findForward(CMSConstants.INIT_MANAGE_TIME_TABLE);
	}

	private void setDefaultDataToScreen(ManageTimeTableForm objForm) {
		objForm.setClassMap(CommonAjaxHandler.getInstance()
				.getClassesByYearFromGeneratedTimeTable(
						new Date().getYear() + 1900));
	}

	public ActionForward fetchData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into init Method of StaffAllocationAction.");
		ManageTimeTableForm objForm = (ManageTimeTableForm) form;
		ActionErrors errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objForm);
		if (errors.isEmpty()) {
			objForm.setViewTo(handler
					.fetchData(objForm.getAcademicYr(), Integer
							.parseInt(objForm.getClasses()), objForm
							.getUserId(),objForm));
			return mapping.findForward(CMSConstants.MANAGE_TIME_TABLE_ENTRY);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of initStaffAllocation of StaffAllocationAction.");
		return mapping.findForward(CMSConstants.INIT_MANAGE_TIME_TABLE);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ManageTimeTableForm objForm = (ManageTimeTableForm) form;
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
			return mapping.findForward(CMSConstants.INIT_MANAGE_TIME_TABLE);
		} else {
			saveErrors(request, errors);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of add of StaffAllocationAction Class.");
		return mapping.findForward(CMSConstants.MANAGE_TIME_TABLE_ENTRY);
	}

	private ActionErrors validateData(ManageTimeTableForm objForm,
			ActionErrors errors) {
		// ManageTimeTableTo to = objForm.getViewTo();
		// if (checkForEmpty(to.getSubjectId())) {
		// errors.add("error", new ActionError(
		// "admissionFormForm.subject.required"));
		// }
		// if (checkForEmpty(to.getDay())) {
		// errors.add("error", new ActionError("knowledgepro.TimeTable.Day"));
		// }
		// if (checkForEmpty(to.getRoomCodeId())) {
		// errors.add("error", new ActionError(
		// "knowledgepro.TimeTable.roomCode"));
		// }
		// if (to.getPeriods() != null && to.getPeriods().length > 0) {
		// String[] periods = to.getPeriods();
		// if (periods[0] != null && periods[0].equals("")) {
		// errors.add("error", new ActionError(
		// "knowledgepro.TimeTable.periods"));
		// }
		// }
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
		log.info("entering into edit Method of ManageTimeTableAction Class.");
		ManageTimeTableForm objForm = (ManageTimeTableForm) form;
		ActionMessages messages = new ActionMessages();
		int delete = handler.delete(objForm.getId());
		if (delete > 0) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.ExamPromotionCriteria.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		setDefaultDataToScreen(objForm);
		log.info("exit of edit of ManageTimeTableAction Class.");
		return mapping.findForward(CMSConstants.INIT_MANAGE_TIME_TABLE);
	}

}