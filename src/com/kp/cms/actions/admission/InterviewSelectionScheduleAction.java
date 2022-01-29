package com.kp.cms.actions.admission;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.admission.InterviewSelectionScheduleForm;
import com.kp.cms.handlers.admission.InterviewSelectionScheduleHandler;
import com.kp.cms.to.admission.InterviewSelectionScheduleTO;

public class InterviewSelectionScheduleAction extends BaseDispatchAction{
	InterviewSelectionScheduleHandler interviewSelectionScheduleHandler=InterviewSelectionScheduleHandler.getInstance();
	//init method
	public ActionForward initInterviewSelectionSchedule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		resetFormFields(interviewSelectionScheduleForm);
		// This gets the current date and time.
		Calendar calendar = Calendar.getInstance();   
		int year=calendar.get(Calendar.YEAR); 
		interviewSelectionScheduleForm.setAcademicYear(String.valueOf(year));
		//get programMap
		interviewSelectionScheduleHandler.getProgramMap(interviewSelectionScheduleForm);
		//get all the records
		interviewSelectionScheduleHandler.getInterviewSelectionScheduleRecords(interviewSelectionScheduleForm);
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	// set null values to form fields
	private void resetFormFields(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) {
		interviewSelectionScheduleForm.setProgramId(null);
		interviewSelectionScheduleForm.setCutOffDate(null);
		interviewSelectionScheduleForm.setSelectionProcessDate(null);
		interviewSelectionScheduleForm.setMaxNumOfSeatsOffline(null);
		interviewSelectionScheduleForm.setMaxNumOfSeatsOnline(null);
		interviewSelectionScheduleForm.setVenueList(null);
		interviewSelectionScheduleForm.setTimeList(null);
		interviewSelectionScheduleForm.setVenueFlag(false);
		interviewSelectionScheduleForm.setTimeFlag(false);
		interviewSelectionScheduleForm.setFlag(null);
		interviewSelectionScheduleForm.setTotalCount(0);
		interviewSelectionScheduleForm.setDateChangedFlag("false");
		interviewSelectionScheduleForm.setTimeChangedFlag("false");
		interviewSelectionScheduleForm.setVenueId(0);
		interviewSelectionScheduleForm.setVenueMap(null);
		interviewSelectionScheduleForm.setIsCardgenetated(false);
	}
	/**
	 * method used to add  fine entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInterviewSelectionSchedule(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = interviewSelectionScheduleForm.validate(mapping, request);
		boolean isAdded=false;
		try {
			if(errors.isEmpty()){
				setUserId(request, interviewSelectionScheduleForm);
				boolean duplicate=interviewSelectionScheduleHandler.checkDuplicate(interviewSelectionScheduleForm);
				if(duplicate){
					//duplicate
					errors.add("error", new ActionError("knowledgepro.emp.holidays.add.exist"));
					saveErrors(request, errors);
					request.setAttribute("admn", "venue");
					return mapping.findForward("initInterviewSelectionSchedule");
				}else{
					String flag=interviewSelectionScheduleHandler.validateTheLists(interviewSelectionScheduleForm);
					if(flag!=null && !flag.isEmpty()){
						if(flag.equalsIgnoreCase("venue")){
							errors.add("error", new ActionError("interviewProcessForm.venue.required"));
							saveErrors(request, errors);
						}else if(flag.equalsIgnoreCase("time")){
							errors.add("error", new ActionError("knowledgepro.admission.time.no.of.candidates.required"));
							saveErrors(request, errors);
						}else if(flag.equalsIgnoreCase("candidates")){
							errors.add("error", new ActionError("knowledgepro.admission.total.no.of.candidates.exceeds"));
							saveErrors(request, errors);
						}else if(flag.equalsIgnoreCase("bigTime")){
							errors.add("error", new ActionError("knowledgepro.admission.time.greater.hencetime"));
							saveErrors(request, errors);
						}
						request.setAttribute("admn", "venue");
					}else{
						isAdded=interviewSelectionScheduleHandler.addInterviewSelectionSchedule(interviewSelectionScheduleForm);
						if(isAdded){
							// success .
							//get all the records
							//interviewSelectionScheduleHandler.getInterviewSelectionScheduleRecords(interviewSelectionScheduleForm);
							ActionMessage message = new ActionMessage("knowledgepro.phd.Setting.addsuccess");
							messages.add("messages", message);
							saveMessages(request, messages);
							resetFormFields(interviewSelectionScheduleForm);
						
						}else{
							// failed
							request.setAttribute("admn", "venue");
							errors.add("error", new ActionError("knowledgepro.phd.Setting.addfailure"));
							saveErrors(request, errors);
						}
					}
				}
				
			}else{
				if(interviewSelectionScheduleForm.getProgramId()!=null && !interviewSelectionScheduleForm.getProgramId().isEmpty()){
					request.setAttribute("admn", "venue");
				}
				saveErrors(request, errors);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewSelectionScheduleForm.setErrorMessage(msg);
				interviewSelectionScheduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	/**
	 * delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = interviewSelectionScheduleForm.validate(mapping, request);
		try {
				boolean flag=false;
				setUserId(request, interviewSelectionScheduleForm);
				flag=interviewSelectionScheduleHandler.delete(interviewSelectionScheduleForm);
				if(flag){
					// success .
					//get all the records
					//interviewSelectionScheduleHandler.getInterviewSelectionScheduleRecords(interviewSelectionScheduleForm);
					ActionMessage message = new ActionMessage("nowledgepro.phd.Setting.deletesuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
				}else{
					// failed
					errors.add("error", new ActionError("knowledgepro.phd.Setting.deletefailure"));
					saveErrors(request, errors);
				}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewSelectionScheduleForm.setErrorMessage(msg);
				interviewSelectionScheduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	/**
	 * delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward venues(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		interviewSelectionScheduleForm.setVenueList(null);
		interviewSelectionScheduleForm.setVenueFlag(false);
		interviewSelectionScheduleForm.setTimeFlag(false);
		try {
				setUserId(request, interviewSelectionScheduleForm);
				interviewSelectionScheduleHandler.venues(interviewSelectionScheduleForm);
				if(interviewSelectionScheduleForm.getFlag()!=null && interviewSelectionScheduleForm.getFlag().equalsIgnoreCase("edit")){
					request.setAttribute("admOperation", "edit");
					if(interviewSelectionScheduleForm.getTimeList().size()>1){
						interviewSelectionScheduleForm.setTimeFlag(true);
					}
					interviewSelectionScheduleForm.setFlag(null);
				}
				interviewSelectionScheduleForm.setTimeListSize(interviewSelectionScheduleForm.getTimeList().size());
				request.setAttribute("admn", "venue");
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewSelectionScheduleForm.setErrorMessage(msg);
				interviewSelectionScheduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	/**
	 * add more venues
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMoreVenues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			if(interviewSelectionScheduleForm.getProgramId()!=null && !interviewSelectionScheduleForm.getProgramId().isEmpty()){
				interviewSelectionScheduleHandler.addMoreVenues(interviewSelectionScheduleForm);
				if(interviewSelectionScheduleForm.getFlag()!=null && interviewSelectionScheduleForm.getFlag().equalsIgnoreCase("edit")){
					request.setAttribute("admOperation", "edit");
					interviewSelectionScheduleForm.setFlag(null);
				}
			}else{
				errors.add("error", new ActionError("admissionFormForm.programId.required"));
				saveErrors(request, errors);
			}
			request.setAttribute("admn", "venue");
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewSelectionScheduleForm.setErrorMessage(msg);
			interviewSelectionScheduleForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	/**
	 * add more venues
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMoreTimes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		try {
		interviewSelectionScheduleHandler.addMoreTimes(interviewSelectionScheduleForm);
		if(interviewSelectionScheduleForm.getFlag()!=null && interviewSelectionScheduleForm.getFlag().equalsIgnoreCase("edit")){
			request.setAttribute("admOperation", "edit");
			interviewSelectionScheduleForm.setFlag(null);
		}
		interviewSelectionScheduleForm.setTimeListSize(interviewSelectionScheduleForm.getTimeList().size());
		request.setAttribute("admn", "venue");
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewSelectionScheduleForm.setErrorMessage(msg);
			interviewSelectionScheduleForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	/**
	 * remove the last record of venues
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeMoreVenues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		try {
			interviewSelectionScheduleHandler.removeMoreVenues(interviewSelectionScheduleForm);
			if(interviewSelectionScheduleForm.getFlag()!=null && interviewSelectionScheduleForm.getFlag().equalsIgnoreCase("edit")){
				request.setAttribute("admOperation", "edit");
				interviewSelectionScheduleForm.setFlag(null);
			}
			request.setAttribute("admn", "venue");
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewSelectionScheduleForm.setErrorMessage(msg);
			interviewSelectionScheduleForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	/**
	 * remove the last record of times
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeMoreTimes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		try {
			interviewSelectionScheduleHandler.removeMoreTimes(interviewSelectionScheduleForm);
			if(interviewSelectionScheduleForm.getFlag()!=null && interviewSelectionScheduleForm.getFlag().equalsIgnoreCase("edit")){
				request.setAttribute("admOperation", "edit");
				interviewSelectionScheduleForm.setFlag(null);
			}
			interviewSelectionScheduleForm.setTimeListSize(interviewSelectionScheduleForm.getTimeList().size());
			request.setAttribute("admn", "venue");
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewSelectionScheduleForm.setErrorMessage(msg);
			interviewSelectionScheduleForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	/**
	 * get the details for edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		resetFormFields(interviewSelectionScheduleForm);
		try {
			interviewSelectionScheduleHandler.edit(interviewSelectionScheduleForm);
			interviewSelectionScheduleForm.setTimeListSize(interviewSelectionScheduleForm.getTimeList().size());
			request.setAttribute("admOperation", "edit");
			request.setAttribute("admn", "venue");
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewSelectionScheduleForm.setErrorMessage(msg);
			interviewSelectionScheduleForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	/**
	 * method used to add  fine entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateInterviewSelectionSchedule(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = interviewSelectionScheduleForm.validate(mapping, request);
		boolean isUpdate=false;
		try {
			if(errors.isEmpty()){
				setUserId(request, interviewSelectionScheduleForm);
				boolean duplicate=interviewSelectionScheduleHandler.checkDuplicate(interviewSelectionScheduleForm);
				if(duplicate){
					//duplicate
					request.setAttribute("admn", "venue");
					request.setAttribute("admOperation", "edit");
					errors.add("error", new ActionError("knowledgepro.emp.holidays.add.exist"));
					saveErrors(request, errors);
					return mapping.findForward("initInterviewSelectionSchedule");
				}else{
					String flag=interviewSelectionScheduleHandler.validateTheLists(interviewSelectionScheduleForm);
					if(flag!=null && !flag.isEmpty()){
						request.setAttribute("admn", "venue");
						request.setAttribute("admOperation", "edit");
						if(flag.equalsIgnoreCase("venue")){
							errors.add("error", new ActionError("interviewProcessForm.venue.required"));
							saveErrors(request, errors);
						}else if(flag.equalsIgnoreCase("time")){
							errors.add("error", new ActionError("knowledgepro.admission.time.no.of.candidates.required"));
							saveErrors(request, errors);
						}else if(flag.equalsIgnoreCase("candidates")){
							errors.add("error", new ActionError("knowledgepro.admission.total.no.of.candidates.exceeds"));
							saveErrors(request, errors);
						}else if(flag.equalsIgnoreCase("bigTime")){
							errors.add("error", new ActionError("knowledgepro.admission.time.greater.hencetime"));
							saveErrors(request, errors);
						}
					}else{
						isUpdate=interviewSelectionScheduleHandler.update(interviewSelectionScheduleForm);
						if(interviewSelectionScheduleForm.getDateChangedFlag().equalsIgnoreCase("true")){
							//interviewSelectionScheduleHandler.sendSMSMailToStudents(interviewSelectionScheduleForm);
						}
						if(isUpdate){
							// success .
							//get all the records
							//interviewSelectionScheduleHandler.getInterviewSelectionScheduleRecords(interviewSelectionScheduleForm);
							ActionMessage message = new ActionMessage("knowledgepro.hostel.status.updated");
							messages.add("messages", message);
							saveMessages(request, messages);
							resetFormFields(interviewSelectionScheduleForm);
						
						}else{
							// failed
							request.setAttribute("admn", "venue");
							request.setAttribute("admOperation", "edit");
							errors.add("error", new ActionError("knowledgepro.admission.certificate.course.teacher.updatefailure"));
							saveErrors(request, errors);
						}
					}
				}
				
			}else{
				request.setAttribute("admn", "venue");
				request.setAttribute("admOperation", "edit");
				saveErrors(request, errors);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewSelectionScheduleForm.setErrorMessage(msg);
				interviewSelectionScheduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInterviewSelectionScheduleRecords(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InterviewSelectionScheduleForm interviewSelectionScheduleForm=(InterviewSelectionScheduleForm)form;
       // HttpSession session = request.getSession(false);
	    if (interviewSelectionScheduleForm.getAcademicYear()!= null && !interviewSelectionScheduleForm.getAcademicYear().isEmpty()) {
	    	List<InterviewSelectionScheduleTO> list= interviewSelectionScheduleHandler.getSelectionScheduleRecords(interviewSelectionScheduleForm,request);
	    	if(list!=null && !list.isEmpty()){
	    		request.setAttribute("ToList", list);
	    		return mapping.findForward("ajaxResponseForSelectionList"); 
	    	}
		}
		return mapping.findForward("initInterviewSelectionSchedule");
	}
}
