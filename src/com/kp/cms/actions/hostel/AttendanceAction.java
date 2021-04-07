package com.kp.cms.actions.hostel;

import java.util.Date;
import java.util.List;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.hostel.AttendanceForm;
import com.kp.cms.handlers.hostel.AttendanceHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.hostel.HostelGroupStudentTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IAttendanceTransactions;
import com.kp.cms.transactionsimpl.hostel.AttendanceTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class AttendanceAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(AttendanceAction.class);
	/**
	 * initialize
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("inside initAttendance");
		AttendanceForm attForm = (AttendanceForm) form;
		resetFields(attForm);
		try {
			setHostelEntryDetailsToRequest(request);
			setUserId(request, attForm);
		} catch (Exception e) {
			log.error("error in initAttendance...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attForm.setErrorMessage(msg);
				attForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		return mapping.findForward(CMSConstants.HOSTEL_ATTENDANCE);
	}
	
	/**
	 * forward to next page with student details based on the search criteria
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("inside loadStudent");
		AttendanceForm attForm = (AttendanceForm) form;
		 ActionErrors errors = attForm.validate(mapping, request);
		try {
			if (attForm.getAttendanceDate() != null && !StringUtils.isEmpty(attForm.getAttendanceDate())) {
				if (CommonUtil.isValidDate(attForm.getAttendanceDate())) {
					boolean isValid = validatefutureDate(attForm.getAttendanceDate());
					if (!isValid) {
						if (errors.get(CMSConstants.HOSTEL_ATT_DATE_LARGE) != null && !errors.get(CMSConstants.HOSTEL_ATT_DATE_LARGE).hasNext()) {
							errors.add(CMSConstants.HOSTEL_ATT_DATE_LARGE, new ActionError(CMSConstants.HOSTEL_ATT_DATE_LARGE));
							saveErrors(request, errors);
						}
					}
				}
			}		
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setHostelEntryDetailsToRequest(request);
				return mapping.findForward(CMSConstants.HOSTEL_ATTENDANCE);
			}
			if(attForm.getAttendanceDate()!= null && !attForm.getAttendanceDate().trim().isEmpty() &&
				 attForm.getHostelId()!= null && !attForm.getHostelId().trim().isEmpty() && attForm.getHlGroupId()!= null
			   && !attForm.getHlGroupId().trim().isEmpty()){
				IAttendanceTransactions iaTransactions =AttendanceTransactionImpl.getInstance();
				Boolean result = iaTransactions.isAttendanceEntryDuplicated(attForm.getAttendanceDate(), attForm.getHlGroupId(), attForm.getHostelId());
				if(result!= null && result){
					throw new DuplicateException();
				}
			}
			assignStudentListToFormByHostelId(attForm);
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("hostel.att.entry.exists", attForm.getAttendanceDate()));
			saveErrors(request, errors);
			setHostelEntryDetailsToRequest(request);
			return mapping.findForward(CMSConstants.HOSTEL_ATTENDANCE);
		}catch (Exception e) {
			log.error("error in initAttendance...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attForm.setErrorMessage(msg);
				attForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		return mapping.findForward(CMSConstants.HOSTEL_ATTENDANCE_SUBMIT);
	}
	
	/**
	 * save attendance to table.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return saveRooms
	 * @throws Exception
	 */
	public ActionForward saveAttendance(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside saveAttendance");
		AttendanceForm attForm = (AttendanceForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = attForm.validate(mapping, request);
		Boolean isAdded;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setHostelEntryDetailsToRequest(request);
				return mapping.findForward(CMSConstants.HOSTEL_ATTENDANCE_SUBMIT);
			}
			isAdded = AttendanceHandler.getInstance().saveAttendance(attForm); 
			setHostelEntryDetailsToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("hostel.att.entry.exists", attForm.getAttendanceDate()));
			saveErrors(request, errors);
			setHostelEntryDetailsToRequest(request);
			return mapping.findForward(CMSConstants.HOSTEL_ATTENDANCE);
		} catch (Exception e) {
			log.error("error in saveAttendance...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attForm.setErrorMessage(msg);
				attForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.hostel.att.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			resetFields(attForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.att.addfailure"));
			saveErrors(request, errors);
		}
		
		log.debug("leaving saveAttendance");
		return mapping.findForward(CMSConstants.HOSTEL_ATTENDANCE);
	}	
	
	/**
	 * setting hostelList to request
	 * @param request
	 * @throws Exception 
	 */
	public void setHostelEntryDetailsToRequest(HttpServletRequest request) throws Exception{
		log.debug("start setHostelEntryDetailsToRequest");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.debug("exit setHostelEntryDetailsToRequest");
	}

	/**
	 * setting sudetn list to form
	 * @param request
	 * @throws Exception 
	 */
	public void assignStudentListToFormByHostelId(AttendanceForm attForm) throws Exception{
		log.debug("start assignStudentListToFormByHostelId");
		List<HostelGroupStudentTO> studList = AttendanceHandler.getInstance().getStudentDetails(Integer.parseInt(attForm.getHlGroupId())); 
		attForm.setHostelGroupStudentList(studList);
		log.debug("exit assignStudentListToFormByHostelId");
	}

	/**reset fields
	*/	
	public void resetFields(AttendanceForm attForm){
		attForm.setHostelId(null);
		attForm.setHlGroupId(null);
		attForm.setAttendanceDate(CommonUtil.getTodayDate());
	}
/**
	 * Method to check the entered date is not a future date
	 * @param dateString
	 * @return
	 */
	private boolean validatefutureDate(String dateString) {
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, "dd/MM/yyyy","MM/dd/yyyy");
		Date date = new Date(formattedString);
		Date curdate = new Date();
		if (date.compareTo(curdate) == 1){
			return false;
		}
		else{
			return true;
		}
	
	}
}
