package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.actions.fee.FeeAssignmentAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.ApproveLeaveHandler;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.to.attendance.StudentLeaveTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")

public class ApproveLeaveAction extends BaseDispatchAction {

	private static Log log = LogFactory.getLog(FeeAssignmentAction.class);

	/**
	 * Initializes approve leave action screen.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApproveLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initApproveLeave of ApproveLeaveAction class.");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		approveLeaveForm.resetFields();
		try {
			setUserId(request, approveLeaveForm);
			setClassMapToForm(approveLeaveForm);
		} catch (Exception e) {
			log
					.error("Error while initializing approve leave"
							+ e.getMessage());
			String msg = super.handleApplicationException(e);
			approveLeaveForm.setErrorMessage(msg);
			approveLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of initApproveLeave of ApproveLeaveAction class.");
		return mapping.findForward("initApproveLeave");
	}

	/**
	 * Persists approved leaves.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitApproveLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitApproveLeave of ApproveLeaveAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		ActionErrors errors = approveLeaveForm.validate(mapping, request);
        validateDate(approveLeaveForm, messages);

		try {
			if (errors.isEmpty() && messages.isEmpty()) {
				/*int currentYear = calendar.get(Calendar.YEAR);

				approveLeaveForm.setYear(String.valueOf(currentYear));*/

				approveLeaveForm.setRegisterNo(ApproveLeaveHandler
						.getInstance().getIsRegisterNo(approveLeaveForm));

				String inputStr = approveLeaveForm.getRegisterNoEntry();
				String patternStr = ",";
				String[] registerNoString = inputStr.split(patternStr);
				ArrayList<String> registerNoList = new ArrayList<String>();
				Set<String> registerNoSet = new HashSet<String>();
				for (String registerNo : registerNoString) {
					if (registerNo.trim().length() > 0 && registerNoSet.add(registerNo.trim())) {
						registerNoList.add(registerNo.trim());
					}
				}
			
			
				String error = "";
				if (registerNoList.isEmpty()) {
					if (approveLeaveForm.isRegisterNo()) {
						error = "knowledgepro.attendance.activityattendence.invaliedregisterno";
					} else {
						error = "knowledgepro.attendance.activityattendence.invaliedrollno";
					}
				} else {
					approveLeaveForm.setRegisterNoSet(registerNoSet);
				}
				ActionMessage errorMessage = null;
				
				if (error.isEmpty()) {
					errorMessage = ApproveLeaveHandler.getInstance()
					.validateActivityAttendence(approveLeaveForm,
							registerNoList);
				} else {
					errorMessage = new ActionMessage(error);
					
				}

				if (errorMessage == null) {
					approveLeaveForm.setRegisterNoList(registerNoList);

					Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = ApproveLeaveHandler
							.getInstance().constructApproveLeave(
									approveLeaveForm, "add");

					ApproveLeaveHandler.getInstance().updateApproveLeave(
							approveLeaveToMap, approveLeaveForm, "add");

					approveLeaveForm.resetFields();
					message = new ActionMessage(
							"knowledgepro.attendance.activityattendence.approveleave");
					messages.add(CMSConstants.MESSAGES, message);
					addMessages(request, messages);
				} else {

					messages.add(CMSConstants.MESSAGES, errorMessage);
					addErrors(request, messages);
				}
			} else {
				addErrors(request, errors);
				//saveMessages(request, messages);
				//addErrors(request, messages);
				addMessages(request, messages);
			}

			setClassMapToForm(approveLeaveForm);
		} catch (Exception e) {
			log.error("Error while submitting approve leave" + e.getMessage());
			String msg = super.handleApplicationException(e);
			approveLeaveForm.setErrorMessage(msg);
			approveLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of submitApproveLeave of ApproveLeaveAction class.");
		return mapping.findForward("initApproveLeave");
	}

	/**
	 * Initializes modified leave screen.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return redirect to init modify leave with necessary data.
	 * @throws Exception
	 */
	public ActionForward initModifyLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initModifyLeave of ApproveLeaveAction class.");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		approveLeaveForm.setLeaveList(null);
		approveLeaveForm.resetFields();
		log.info("exit of initModifyLeave of ApproveLeaveAction class.");
		return mapping.findForward(CMSConstants.MODIFY_LEAVE_FIRST);
	}

	/**
	 * Performs search action based on search criteria.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return redirect to modify leaves search with necessary data.
	 * @throws Exception
	 */
	public ActionForward modifyLeaveSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into modifyLeaveSearch of ApproveLeaveAction class.");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		approveLeaveForm.setLeaveList(null);
		 ActionMessages errors = approveLeaveForm.validate(mapping, request);
		boolean dateError = false;
		if (approveLeaveForm.getFromDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getFromDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getFromDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_FROMDATREINVALID));
			dateError = true;
		}
		if (approveLeaveForm.getToDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getToDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getToDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_TODATEINVALID));
			dateError = true;
		}
		if (!dateError) {
			validateDate(approveLeaveForm, errors);
		}

		try {
			if (errors.isEmpty()) {
				ApproveLeaveHandler.getInstance().approvedLeaveSearch(
						approveLeaveForm);
				if(approveLeaveForm.getLeaveList().isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_NORECORDS));
					saveErrors(request, errors);
				}
			} else {
				saveErrors(request, errors);
			}
		}  catch (Exception e) {
			String msg = super.handleApplicationException(e);
			approveLeaveForm.setErrorMessage(msg);
			approveLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of modifyLeaveSearch of ApproveLeaveAction class.");
		return mapping.findForward(CMSConstants.MODIFY_LEAVE_FIRST);
	}

	/**
	 * Persists approved leaves.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateApproveLeave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into updateApproveLeave of ApproveLeaveAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		ActionMessages errors = approveLeaveForm.validate(mapping, request);
		if (approveLeaveForm.getFromDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getFromDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getFromDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_FROMDATREINVALID));
		}
		if (approveLeaveForm.getToDate() != null
				&& !StringUtils.isEmpty(approveLeaveForm.getToDate())
				&& !CommonUtil.isValidDate(approveLeaveForm.getToDate())) {
			errors.add(CMSConstants.ERRORS, new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_TODATEINVALID));
		}

		try {
			if (errors.isEmpty()) {
				/*Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				if(year!=0){
					currentYear=year;
				}

				approveLeaveForm.setYear(String.valueOf(currentYear));
				 */
				approveLeaveForm.setRegisterNo(ApproveLeaveHandler
						.getInstance().getIsRegisterNo(approveLeaveForm));

				String inputStr = approveLeaveForm.getRegisterNoEntry();
				String patternStr = ",";
				String[] registerNoString = inputStr.split(patternStr);
				ArrayList<String> registerNoList = new ArrayList<String>();
				Set<String> registerNoSet = new HashSet<String>();
				for (String registerNo : registerNoString) {
					if (registerNo.trim().length() > 0 && registerNoSet.add(registerNo)) {
						registerNoList.add(registerNo);
						
					}
				}
				approveLeaveForm.setRegisterNoSet(registerNoSet);

				ActionMessage errorMessage = ApproveLeaveHandler.getInstance()
						.validateModifyAttendence(approveLeaveForm,
								registerNoList);

				if (errorMessage == null) {
					approveLeaveForm.setRegisterNoList(registerNoList);

					Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = ApproveLeaveHandler
							.getInstance().constructApproveLeave(
									approveLeaveForm, "update");

					ApproveLeaveHandler.getInstance().updateApproveLeave(
							approveLeaveToMap, approveLeaveForm, "update");

					approveLeaveForm.resetFields();
					message = new ActionMessage(
							CMSConstants.ATTENDANCE_LEAVE_MODIFYSUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					addMessages(request, messages);
				} else {
					messages.add(CMSConstants.MESSAGES, errorMessage);
					addErrors(request, messages);
					return mapping
							.findForward(CMSConstants.MODIFY_LEAVE_SECOND);

				}
			} else {
				addErrors(request, errors);
				return mapping.findForward(CMSConstants.MODIFY_LEAVE_SECOND);
			}

			setClassMapToForm(approveLeaveForm);
		} catch (Exception e) {
			errors.add(CMSConstants.ERROR, new ActionMessage(
					CMSConstants.ATTENDANCE_LEAVE_MODIFYFAILURE));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.MODIFY_LEAVE_FIRST);

		}
		log.info("exit of updateApproveLeave of ApproveLeaveAction class.");
		return mapping.findForward(CMSConstants.MODIFY_LEAVE_FIRST);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This action method is to delete single leave approval.
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward deleteLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Action :Entering deleteLeave");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		ActionMessages errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if (approveLeaveForm.getId() != null) {
				ApproveLeaveHandler.getInstance().editLeave(approveLeaveForm);
				Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = ApproveLeaveHandler
						.getInstance().constructApproveLeaveForDelete(approveLeaveForm);

				
				ApproveLeaveHandler.getInstance().deleteLeave(approveLeaveForm,approveLeaveToMap);
				// approveLeaveForm.resetFields();
			List<StudentLeaveTO> approvedLeaves = ApproveLeaveHandler.getInstance().getApprovedLeaves(
						approveLeaveForm);
			approveLeaveForm.setLeaveList(approvedLeaves);
				log
						.debug("Action :Entering deleteFeeAssignment with exception");
				ActionMessage message = new ActionMessage(
						CMSConstants.LEAVE_DELETE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			}
		} catch (Exception e) {
			// failure error message.
			log.debug("Action :Entering deleteFeeAssignment with exception");
			errors.add("error", new ActionMessage(
					CMSConstants.LEAVE_DELETE_FAILURE));
			saveErrors(request, errors);
		}
		log.debug("Action :leaving+ deleteLeave with success");
		return mapping.findForward(CMSConstants.MODIFY_LEAVE_FIRST);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This action method is to edit single leave approval.
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward editLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Action :Entering editLeave");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		ActionMessages errors = new ActionErrors();
		try {
			if (approveLeaveForm.getId() != null) {
				ApproveLeaveHandler.getInstance().editLeave(approveLeaveForm);
				setClassMapToForm(approveLeaveForm);
			}
		} catch (Exception e) {
			log.debug("Action :Entering deleteFeeAssignment with exception");
			errors.add("error", new ActionMessage(
					CMSConstants.LEAVE_DELETE_FAILURE));
			saveErrors(request, errors);
		}
		log.debug("Action :leaving editLeave with success");
		return mapping.findForward(CMSConstants.MODIFY_LEAVE_SECOND);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This action method is to edit single leave approval.
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward resetLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Action :Entering resetLeave");
		ApproveLeaveForm approveLeaveForm = (ApproveLeaveForm) form;
		approveLeaveForm.setRegisterNoEntry(approveLeaveForm.getOldRegisterNoEntry());
		approveLeaveForm.setFromDate(approveLeaveForm.getStudentLeaveTO().getStDate());
		approveLeaveForm.setToDate(approveLeaveForm.getStudentLeaveTO().getEdDate());
		approveLeaveForm.setLeaveType(approveLeaveForm.getStudentLeaveTO().getLeaveType());
		approveLeaveForm.setFromPeriod(approveLeaveForm.getStudentLeaveTO().getStartPeriod());
		approveLeaveForm.setToPeriod(approveLeaveForm.getStudentLeaveTO().getEndPeriod());
		approveLeaveForm.setClassId(String.valueOf(approveLeaveForm.getStudentLeaveTO().getClassSchemewiseId()));
		return mapping.findForward(CMSConstants.MODIFY_LEAVE_SECOND);
	}

	/**
	 * Sets all the classes for the current year in request scope
	 * 
	 * @param activityAttendanceForm
	 * @throws Exception 
	 */

	public void setClassMapToForm(ApproveLeaveForm approveLeaveForm)
			throws Exception {
		log.info("entering into setClassMapToForm of ApproveLeaveAction class.");
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		
		approveLeaveForm.setYear(String.valueOf(currentYear));
		// code by hari
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(year!=0){
			currentYear=year;
		}// end
		approveLeaveForm.setYear(String.valueOf(currentYear));
		Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(
				currentYear);
		approveLeaveForm.setClassMap(classMap);
		if (approveLeaveForm.getClassSchemewiseId() != null
				&& !approveLeaveForm.getClassSchemewiseId().equals("")) {
			Map<Integer,String> periodMap = CommonAjaxHandler.getInstance()
					.getPeriodByClassSchemewiseId(
							Integer.valueOf(approveLeaveForm
									.getClassSchemewiseId()));
			approveLeaveForm.setPeriodMap(periodMap);
		}

		Map<Integer, String> leaveMap = ApproveLeaveHandler.getInstance()
				.getLeaveType();
		approveLeaveForm.setLeaveMap(leaveMap);
		log.info("exit of setClassMapToForm of ApproveLeaveAction class.");
	}

	/**
	 * Performs date validations.
	 * 
	 * @param approveLeaveForm
	 * @return actions messages if validation fails, empty action messages
	 *         otherwise
	 */
	public void validateDate(ApproveLeaveForm approveLeaveForm,
			ActionMessages actionErrors) {
		log.info("entering into validateDate of ApproveLeaveAction class.");
		ActionMessage message=null;
		if (approveLeaveForm.getFromDate() != null
				&& approveLeaveForm.getFromDate().length() != 0
				&& approveLeaveForm.getToDate() != null
				&& approveLeaveForm.getToDate().length() != 0) {
			Date startDate = CommonUtil.ConvertStringToDate(approveLeaveForm
					.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(approveLeaveForm
					.getToDate());
			if(!startDate.after(new Date())&& !endDate.after(new Date())){
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if (daysBetween <= 0) {
				actionErrors.add("error", new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
			}else{
				message = new ActionMessage(
				"knowledgepro.attendance.activityattendence.approveLeave.dateError");
				actionErrors.add(CMSConstants.MESSAGES, message);
			}
		}
		log.info("exit of validateDate of ApproveLeaveAction class.");
	}
}