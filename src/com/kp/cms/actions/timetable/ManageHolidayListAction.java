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
import com.kp.cms.forms.timetable.ManageHolidayListForm;
import com.kp.cms.handlers.timetable.ManageHolidayListHandler;
import com.kp.cms.to.timetable.ManageHolidayListTo;
import com.kp.cms.utilities.CommonUtil;

public class ManageHolidayListAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(ManageHolidayListAction.class);
	ManageHolidayListHandler handler = ManageHolidayListHandler.getinstance();

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
	public ActionForward initManageHolidayList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Method of ManageHolidayListAction.");
		ManageHolidayListForm objForm = (ManageHolidayListForm) form;
		objForm.clearPage();
		setGrid(objForm);
		log.info("exit of initManageHolidayList of ManageHolidayListAction.");
		return mapping.findForward(CMSConstants.INIT_MANAGE_HOLIDAY_LIST);
	}

	private void setGrid(ManageHolidayListForm objForm) {
		objForm.setBottomGrid(handler.getBottomGrid());

	}

	/**
	 * 
	 * It Adds holidays for a given academic year
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
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into add Method of ManageHolidayListAction Class.");
		ManageHolidayListForm manageHolidayListForm = (ManageHolidayListForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = manageHolidayListForm.validate(mapping, request);
		errors = validateInputData(manageHolidayListForm, errors);
		setUserId(request, manageHolidayListForm);
		if (errors.isEmpty()) {
			int added = handler.addDetails(manageHolidayListForm
					.getAcademicYr(), manageHolidayListForm.getEndDate(),
					manageHolidayListForm.getStartDate(), manageHolidayListForm
							.getHolidayName(), manageHolidayListForm
							.getUserId());
			if (added > 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamPromotionCriteria.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			manageHolidayListForm.clearPage();
		} else {
			saveErrors(request, errors);
		}
		setGrid(manageHolidayListForm);
		log.info("exit of add of ManageHolidayListAction Class.");
		return mapping.findForward(CMSConstants.INIT_MANAGE_HOLIDAY_LIST);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into edit Method of ManageHolidayListAction Class.");
		ManageHolidayListForm objForm = (ManageHolidayListForm) form;
		// clearErrorsAndMessaages(objForm);
		setGrid(objForm);
		setToDetailsToForm(handler.editDetails(objForm.getId()), objForm);
		request.setAttribute("edit", "edit");
		log.info("exit of edit of ManageHolidayListAction Class.");
		return mapping.findForward(CMSConstants.INIT_MANAGE_HOLIDAY_LIST);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into edit Method of ManageHolidayListAction Class.");
		ManageHolidayListForm objForm = (ManageHolidayListForm) form;
		ActionMessages messages = new ActionMessages();
		// clearErrorsAndMessaages(objForm);
		int delete = handler.delete(objForm.getId());
		if (delete > 0) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.ExamPromotionCriteria.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		setGrid(objForm);
		log.info("exit of edit of ManageHolidayListAction Class.");
		return mapping.findForward(CMSConstants.INIT_MANAGE_HOLIDAY_LIST);
	}

	/*
	 * private void clearErrorsAndMessaages(ManageHolidayListForm objForm) {
	 * errors.clear(); messages.clear();
	 * 
	 * }
	 */

	private void setToDetailsToForm(ManageHolidayListTo editDetails,
			ManageHolidayListForm objForm) {
		objForm.setAcademicYr(editDetails.getAcademicYear());
		objForm.setStartDate(CommonUtil.formatDate(editDetails.getFromDate(),
				"dd/MM/yyyy"));
		objForm.setEndDate(CommonUtil.formatDate(editDetails.getToDate(),
				"dd/MM/yyyy"));
		objForm.setHolidayName(editDetails.getHolidayName());
		objForm.setMetaTo(editDetails);

	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log
				.info("entering into update Method of ManageHolidayListAction Class.");
		ManageHolidayListForm objForm = (ManageHolidayListForm) form;
		// clearErrorsAndMessaages(objForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		errors = validateInputData(objForm, errors);
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			int updated = handler.updateDetails(objForm.getAcademicYr(),
					objForm.getEndDate(), objForm.getStartDate(), objForm
							.getHolidayName(), objForm.getUserId(), objForm
							.getMetaTo());
			if (updated > 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.assignStudentsToRoom.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			objForm.clearPage();
		} else {
			request.setAttribute("edit", "edit");
		}
		setGrid(objForm);
		log.info("exit of update of ManageHolidayListAction Class.");
		return mapping.findForward(CMSConstants.INIT_MANAGE_HOLIDAY_LIST);
	}

	private ActionErrors validateInputData(ManageHolidayListForm objForm,
			ActionErrors errors) {
		if (!(errors != null && errors.size() > 0)) {
			Date startDate = CommonUtil.ConvertStringToSQLDate(objForm
					.getStartDate());
			Date endDate = CommonUtil.ConvertStringToSQLDate(objForm
					.getEndDate());
			if (CommonUtil.getDaysDiff(startDate, endDate) < 0) {
				errors.add("error", new ActionError(
						"knowledgepro.TimeTable.startDateEndDate"));

			}
		}

		return errors;
	}
}