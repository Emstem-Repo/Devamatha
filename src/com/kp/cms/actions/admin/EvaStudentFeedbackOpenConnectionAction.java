package com.kp.cms.actions.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.EvaStudentFeedbackOpenConnectionForm;
import com.kp.cms.handlers.admin.EvaStudentFeedbackOpenConnectionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.EvaStudentFeedbackOpenConnectionTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class EvaStudentFeedbackOpenConnectionAction extends BaseDispatchAction {
	private static final Log log = LogFactory
			.getLog(EvaStudentFeedbackOpenConnectionAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentFeedbackOpenConnection(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaStudentFeedbackOpenConnectionForm connectionForm = (EvaStudentFeedbackOpenConnectionForm) form;
		connectionForm.clear();
		try {
			connectionForm.setFlag(false);
			setUserId(request, connectionForm);
			setClassesToRequest(connectionForm, request); // getting class ids & // names and setting // in map
			setOpenConnectionList(connectionForm);
			setSessionList(connectionForm,request);
		} catch (Exception e) {
			log .error("Error while initializing initStudentFeedbackOpenConnection" + e.getMessage());
			String msg = super.handleApplicationException(e);
			connectionForm.setErrorMessage(msg);
			connectionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.STUDENT_FEEDBACK_OPEN_CONNECTION);
	}

	private void setSessionList( EvaStudentFeedbackOpenConnectionForm connectionForm, HttpServletRequest request) throws Exception{
		Map<Integer,String> feedbackSessionMap = EvaStudentFeedbackOpenConnectionHandler.getInstance().getSessionList(connectionForm);
		request.setAttribute("SessionMap", feedbackSessionMap);
	}

	/**
	 * @param connectionForm
	 * @throws Exception
	 */
	private void setOpenConnectionList( EvaStudentFeedbackOpenConnectionForm connectionForm) throws Exception {
		// added by manu filter by academic year
        int year=0;
        if(connectionForm.getAcademicYear()!=null && !connectionForm.getAcademicYear().isEmpty()){
        year=Integer.parseInt(connectionForm.getAcademicYear());
        }
        if(year==0){
		year = CurrentAcademicYear.getInstance().getAcademicyear();
		}
        String sessionId=connectionForm.getSessionId();
        //end
		List<EvaStudentFeedbackOpenConnectionTo> toList = EvaStudentFeedbackOpenConnectionHandler.getInstance().getDetails(year,sessionId);
		connectionForm.setOpenConnectionTo(toList);
	}

	/**
	 * @param connectionForm
	 * @throws Exception
	 */
	private void setClassesToRequest(
			EvaStudentFeedbackOpenConnectionForm connectionForm,
			HttpServletRequest request) throws Exception {
		Map<Integer, String> classMap;
		try {
			if (connectionForm.getAcademicYear() == null) {
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}// end
				// The below map contains key as id and value as name of class.
				classMap = CommonAjaxHandler.getInstance()
						.getClassesByYearForMuliSelect(currentYear);
			} else {
				classMap = CommonAjaxHandler.getInstance()
						.getClassesByYearForMuliSelect(
								Integer.parseInt(connectionForm .getAcademicYear()));
			}

			request.setAttribute("classMap", classMap);
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitOpenConnectionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaStudentFeedbackOpenConnectionForm connectionForm = (EvaStudentFeedbackOpenConnectionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = connectionForm.validate(mapping, request);
		
		validateStartDate(connectionForm, errors);
		try {
			if (errors.isEmpty()) {
				
				setUserId(request, connectionForm);
				boolean duplicatecheck = EvaStudentFeedbackOpenConnectionHandler
						.getInstance().duplicateCheck(connectionForm);
				if (!duplicatecheck) {
					boolean isAdded = EvaStudentFeedbackOpenConnectionHandler
							.getInstance().submitOpenConnectionDetails(
									connectionForm);
					if (isAdded) {
						ActionMessage message = new ActionError( "knowledgepro.admin.faculty.evaluation.addsuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						connectionForm.clear();
					} else {
						connectionForm.clear();
						errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.addfailure"));
						saveErrors(request, errors);
					}
				} else {
					errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.exists.already"));
					setSpecializationListByClass(connectionForm, request);
					saveErrors(request, errors);
				}
			} else {
				saveErrors(request, errors);
			}

		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			connectionForm.setErrorMessage(msg);
			connectionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		connectionForm.setFlag(false);
		setOpenConnectionList(connectionForm);
		setClassesToRequest(connectionForm, request);
		setSessionList(connectionForm,request);
		
		return mapping .findForward(CMSConstants.STUDENT_FEEDBACK_OPEN_CONNECTION);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editOpenConnectionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaStudentFeedbackOpenConnectionForm connectionForm = (EvaStudentFeedbackOpenConnectionForm) form;
		ActionMessages messages = new ActionMessages();
		boolean flag = true;
		try {
			setUserId(request, connectionForm);
			EvaStudentFeedbackOpenConnectionHandler.getInstance()
					.getEditOpenConnectionDetails(connectionForm);
			connectionForm.setFlag(flag);
		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			connectionForm.setErrorMessage(msg);
			connectionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		request.setAttribute("openConnection", "edit");
		setOpenConnectionList(connectionForm);
		setSessionList(connectionForm,request);
		setClassesToRequest(connectionForm, request);
		setSpecializationListByClass(connectionForm, request);
		return mapping
				.findForward(CMSConstants.STUDENT_FEEDBACK_OPEN_CONNECTION);
	}

	private void setSpecializationListByClass( EvaStudentFeedbackOpenConnectionForm connectionForm, HttpServletRequest request)throws Exception {
		if(connectionForm.getClassesId()!=null && !connectionForm.getClassesId().toString().isEmpty()){
			String selectedClasses[] = connectionForm.getClassesId() ;
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			Map<Integer, String> specializationMap = CommonAjaxHandler.getInstance().getSpecializationByClassId(classesIdsSet);
		request.setAttribute("specializationMap", specializationMap);
	}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteOpenConnection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaStudentFeedbackOpenConnectionForm connectionForm = (EvaStudentFeedbackOpenConnectionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = connectionForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				boolean isDeleted = EvaStudentFeedbackOpenConnectionHandler
						.getInstance().deleteOpenConnection(connectionForm);
				if (isDeleted) {
					ActionMessage message = new ActionError( "knowledgepro.admin.faculty.evaluation.deletedsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					connectionForm.clear();
				} else {
					errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.deletedfailure"));
					saveErrors(request, errors);
				}
			}
		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			connectionForm.setErrorMessage(msg);
			connectionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setOpenConnectionList(connectionForm);
		setClassesToRequest(connectionForm, request);
		setSessionList(connectionForm,request);
		return mapping .findForward(CMSConstants.STUDENT_FEEDBACK_OPEN_CONNECTION);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateOpenConnection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaStudentFeedbackOpenConnectionForm connectionForm = (EvaStudentFeedbackOpenConnectionForm) form;
		setUserId(request, connectionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = connectionForm.validate(mapping, request);
		validateStartDate(connectionForm, errors);
		try {
			if (errors.isEmpty()) {
				boolean duplicatecheck = EvaStudentFeedbackOpenConnectionHandler
						.getInstance().duplicateCheck(connectionForm);
				if (!duplicatecheck) {
					boolean isUpdated = EvaStudentFeedbackOpenConnectionHandler
							.getInstance().updateOpenConnectionDetails(
									connectionForm);
					if (isUpdated) {
						ActionMessage message = new ActionError(
								"knowledgepro.admin.faculty.evaluation.updatedsuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						connectionForm.clear();
					} else {
						
						errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.updatedfailure"));
						saveErrors(request, errors);
						request.setAttribute("openConnection", "edit");
						setOpenConnectionList(connectionForm);
						setClassesToRequest(connectionForm, request);
						setSessionList(connectionForm,request);
						return mapping .findForward(CMSConstants.STUDENT_FEEDBACK_OPEN_CONNECTION);
					}
				} else {
					errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.exists.already"));
					saveErrors(request, errors);
					request.setAttribute("openConnection", "edit");
					setOpenConnectionList(connectionForm);
					setClassesToRequest(connectionForm, request);
					setSessionList(connectionForm,request);
					setSpecializationListByClass(connectionForm, request);
					return mapping .findForward(CMSConstants.STUDENT_FEEDBACK_OPEN_CONNECTION);
				}
			} else {
				saveErrors(request, errors);
				request.setAttribute("openConnection", "edit");
				setOpenConnectionList(connectionForm);
				setClassesToRequest(connectionForm, request);
				setSessionList(connectionForm,request);
				return mapping .findForward(CMSConstants.STUDENT_FEEDBACK_OPEN_CONNECTION);
			}
		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			connectionForm.setErrorMessage(msg);
			connectionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		connectionForm.setFlag(false);
		request.setAttribute("openConnection", "add");
		setOpenConnectionList(connectionForm);
		setClassesToRequest(connectionForm, request);
		setSessionList(connectionForm,request);
		return mapping .findForward(CMSConstants.STUDENT_FEEDBACK_OPEN_CONNECTION);
	}

	/**
	 * @param commonTemplateForm
	 * @param errors
	 */
	/**
	 * @param commonTemplateForm
	 * @param errors
	 * @param mapping
	 */
	@SuppressWarnings("unused")
	private void validateStartDate(
			EvaStudentFeedbackOpenConnectionForm connectionForm,
			ActionErrors errors) {
		if (connectionForm.getStartDate() != null
				&& !StringUtils.isEmpty(connectionForm.getStartDate())
				&& !CommonUtil.isValidDate(connectionForm.getStartDate())) {
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null
					&& !errors.get(
							CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID)
							.hasNext()) {
				errors
						.add(
								CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,
								new ActionError(
										CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if (connectionForm.getEndDate() != null
				&& !StringUtils.isEmpty(connectionForm.getEndDate())
				&& !CommonUtil.isValidDate(connectionForm.getEndDate())) {
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null
					&& !errors.get(
							CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID)
							.hasNext()) {
				errors
						.add(
								CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,
								new ActionError(
										CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if (connectionForm.getEndDate() != null
				&& !StringUtils.isEmpty(connectionForm.getEndDate())
				&& CommonUtil.isValidDate(connectionForm.getEndDate())) {

			if (CommonUtil.checkForEmpty(connectionForm.getStartDate())
					&& CommonUtil.checkForEmpty(connectionForm.getEndDate())) {
				Date startDate = CommonUtil.ConvertStringToDate(connectionForm
						.getStartDate());
				Date endDate = CommonUtil.ConvertStringToDate(connectionForm
						.getEndDate());

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if (daysBetween <= 0) {
					errors.add("error", new ActionError(
							CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));

				}
			}
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getClassesAndSession(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EvaStudentFeedbackOpenConnectionForm connectionForm = (EvaStudentFeedbackOpenConnectionForm) form;
		setUserId(request, connectionForm);
		try{
			setClassesToRequest(connectionForm, request);
			setSessionList(connectionForm,request);
			setOpenConnectionList(connectionForm);
		}catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			connectionForm.setErrorMessage(msg);
			connectionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.STUDENT_FEEDBACK_OPEN_CONNECTION);
	}
	
}
