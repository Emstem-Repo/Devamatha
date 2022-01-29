package com.kp.cms.actions.hostel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.actions.admin.EvaStudentFeedbackOpenConnectionAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.EvaStudentFeedbackOpenConnectionForm;
import com.kp.cms.forms.admission.PrintShortageAttendanceForm;
import com.kp.cms.forms.hostel.HolidaysForm;
import com.kp.cms.handlers.admin.EvaStudentFeedbackOpenConnectionHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.AbsentiesListHandler;
import com.kp.cms.handlers.hostel.HolidaysHandler;
import com.kp.cms.to.admin.EvaStudentFeedbackOpenConnectionTo;
import com.kp.cms.to.hostel.HostelHolidaysTo;
import com.kp.cms.utilities.CommonUtil;

public class HostelHolidaysAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EvaStudentFeedbackOpenConnectionAction.class);
	/**
	 * initHolidays() to set required fields to form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHolidays(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HolidaysForm holidaysForm=(HolidaysForm)form;
		clear(holidaysForm);
		holidaysForm.setCourseMap(null);
		setRequiredDatatoForm(holidaysForm, request);
		setHostelHolidaysList(holidaysForm);
		return mapping.findForward(CMSConstants.HOLIDAYS_INIT);
	}
	/**
	 * set required data to form
	 * @param holidaysForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(HolidaysForm holidaysForm,HttpServletRequest request) throws Exception{
		Map<Integer,String> hostelMap= AbsentiesListHandler.getInstance().getHostelMap();
		holidaysForm.setHostelMap(hostelMap);
		 Map<String, String> ProgramMap = HolidaysHandler.getInstance().getPrograms();
		holidaysForm.setProgramMap(ProgramMap);
		
	}
	/**
	 * save hostel holidays details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveHostelHolidaysDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HolidaysForm holidaysForm = (HolidaysForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = holidaysForm.validate(mapping, request);
		validateStartDate(holidaysForm, errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, holidaysForm);
				String add="add";
				boolean duplicatecheck = HolidaysHandler.getInstance().duplicateCheckForAdd(holidaysForm);
				if (!duplicatecheck){
					boolean isAdded = HolidaysHandler.getInstance().submithostelHolidaysDetails(holidaysForm);
					if (isAdded) {
						ActionMessage message = new ActionError( "knowledgepro.hostel.holidays.addsuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						clear(holidaysForm);
					} else {
						holidaysForm.clear();
						errors .add( "error", new ActionError( "knowledgepro.hostel.holidays.addfail"));
						saveErrors(request, errors);
					}
				}else {
					errors.add("error",new ActionError("knowledgepro.hostel.holidays.alreadyexists"));
					saveErrors(request, errors);
					holidaysForm.clear();
				}
				
			} else {
				saveErrors(request, errors);
				Map<Integer, String> courseMap = HolidaysHandler.getInstance().courseMap();
				holidaysForm.setCourseMap(courseMap);
			}

		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setHostelHolidaysList(holidaysForm);
		return mapping .findForward(CMSConstants.HOLIDAYS_INIT);
	}
	/**
	 * set the hostel Holidays details to form
	 * @param holidaysForm
	 * @throws Exception
	 */
	private void setHostelHolidaysList( HolidaysForm holidaysForm) throws Exception {
		List<HostelHolidaysTo> toList = HolidaysHandler.getInstance().getDetails();
		holidaysForm.setHostelHolidaysTo(toList);
	}
	/**
	 * delete Hostel Holidays Details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteHostelHolidays(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HolidaysForm holidaysForm = (HolidaysForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = holidaysForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				boolean isDeleted = HolidaysHandler.getInstance().deleteHolidaysDtails(holidaysForm);
				if (isDeleted) {
					ActionMessage message = new ActionError( "knowledgepro.hostel.holidays.deletesuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					clear(holidaysForm);
				} else {
					errors .add( "error", new ActionError( "knowledgepro.hostel.holidays.deletefail"));
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
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setHostelHolidaysList(holidaysForm);
		return mapping .findForward(CMSConstants.HOLIDAYS_INIT);
	}
	/**
	 * edit hostel holidays details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editHostelHolidaysDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HolidaysForm holidaysForm = (HolidaysForm) form;
		ActionMessages messages = new ActionMessages();
		boolean flag = true;
		try {
			clear(holidaysForm);
			setUserId(request, holidaysForm);
			HolidaysHandler.getInstance().getHostelHolidaysDetails(holidaysForm);
			
		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		request.setAttribute("openConnection", "edit");
		setRequiredDatatoForm(holidaysForm, request);
		setCourseMapToForm(holidaysForm);
		setBlockAndUnitMap(holidaysForm);
		return mapping.findForward(CMSConstants.HOLIDAYS_INIT);
	}
/**
 * set courseMap to form
 * @param holidaysForm
 * @throws Exception
 */
	private void setCourseMapToForm( HolidaysForm holidaysForm) throws Exception {
		String progId=null;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		if(holidaysForm.getProgramsId()!=null){
		progId=holidaysForm.getProgramsId()[0];
		courseMap = HolidaysHandler.getInstance().getCourseMap(progId);
		}
		holidaysForm.setCourseMap(courseMap);
	}
	/**
	 * updating the hostel holidays details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateHostelHolidaysDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HolidaysForm holidaysForm = (HolidaysForm) form;
		setUserId(request, holidaysForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = holidaysForm.validate(mapping, request);
		validateStartDate(holidaysForm, errors); 
		try {
			if (errors.isEmpty()) {
				boolean duplicatecheck = HolidaysHandler.getInstance().duplicateCheck(holidaysForm);
				if (!duplicatecheck){
					boolean isUpdated = HolidaysHandler.getInstance().updateHostelHolidaysDetails(holidaysForm);
					if (isUpdated) {
						ActionMessage message = new ActionError("knowledgepro.hostel.holidays.updatesuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						clear(holidaysForm);
						holidaysForm.setCourseMap(null);
					} else {
						
						errors .add( "error", new ActionError( "knowledgepro.hostel.holidays.updatefail"));
						request.setAttribute("openConnection", "edit");
						setRequiredDatatoForm(holidaysForm, request);
						setCourseMapToForm(holidaysForm);
						return mapping.findForward(CMSConstants.HOLIDAYS_INIT);
					}
				}else{
					errors.add("error",new ActionError("knowledgepro.hostel.holidays.alreadyexists"));
					saveErrors(request, errors);
					holidaysForm.clear();
				}
				}else {
				saveErrors(request, errors);
				request.setAttribute("openConnection", "edit");
				setRequiredDatatoForm(holidaysForm, request);
				setCourseMapToForm(holidaysForm);
				return mapping .findForward(CMSConstants.HOLIDAYS_INIT);
			}
		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			holidaysForm.setErrorMessage(msg);
			holidaysForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		request.setAttribute("openConnection", "add");
		setHostelHolidaysList(holidaysForm);
		return mapping .findForward(CMSConstants.HOLIDAYS_INIT);
	}
	/**
	 * validate date
	 * @param connectionForm
	 * @param errors
	 */
	private void validateStartDate(HolidaysForm holidaysForm,ActionErrors errors) {
		if (holidaysForm.getHolidaysFrom() != null && !StringUtils.isEmpty(holidaysForm.getHolidaysFrom())
				&& !CommonUtil.isValidDate(holidaysForm.getHolidaysFrom())) {
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null
					&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if (holidaysForm.getHolidaysTo()!= null
				&& !StringUtils.isEmpty(holidaysForm.getHolidaysTo())
				&& !CommonUtil.isValidDate(holidaysForm.getHolidaysTo())) {
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null
					&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if (holidaysForm.getHolidaysTo() != null
				&& !StringUtils.isEmpty(holidaysForm.getHolidaysTo())
				&& CommonUtil.isValidDate(holidaysForm.getHolidaysTo())) {

			if (CommonUtil.checkForEmpty(holidaysForm.getHolidaysFrom())
					&& CommonUtil.checkForEmpty(holidaysForm.getHolidaysTo())) {
				Date startDate = CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysFrom());
				Date endDate = CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysTo());

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if (daysBetween <= 0) {
					errors.add("error", new ActionError(CMSConstants.HOSTEL_HOLIDAYS_DATE));

				}
			}
		}
	}
public void setBlockAndUnitMap(HolidaysForm holidaysForm)throws Exception{
	Map<Integer, String> blockMap=CommonAjaxHandler.getInstance().getBlockByHostel(Integer.parseInt(holidaysForm.getHostelId()));
	holidaysForm.setBlockMap(blockMap);
	Map<Integer, String> unitMap = CommonAjaxHandler.getInstance().getUnitByBlock(Integer.parseInt(holidaysForm.getBlockId()));
	holidaysForm.setUnitMap(unitMap);
}
public void clear(HolidaysForm holidaysForm) {
	holidaysForm.setProgramsId(null);
	holidaysForm.setHolidaysFrom(null);
	holidaysForm.setHolidaysFromSession(null);
	holidaysForm.setHolidaysTo(null);
	holidaysForm.setHolidaysToSession(null);
	holidaysForm.setCoursesId(null);
	holidaysForm.setFlag(false);
	holidaysForm.setHolidaysOrVacation(null);
	holidaysForm.setDescription(null);
	holidaysForm.setHostelId(null);
	holidaysForm.setBlockId(null);
	holidaysForm.setUnitId(null);
	holidaysForm.setBlockMap(null);
	holidaysForm.setUnitMap(null);
}
}
