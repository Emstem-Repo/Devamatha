package com.kp.cms.actions.admission;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.reports.ScoreSheetAction;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.PrintShortageAttendanceForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admission.PrintShortageAttendanceHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.utilities.CommonUtil;

public class PrintShortageAttendanceAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(ScoreSheetAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPrintShortageAttendance(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Score Sheet Batch input");
		PrintShortageAttendanceForm printShortageAttendanceForm = (PrintShortageAttendanceForm) form;
		printShortageAttendanceForm.resetFields();
		setRequiredDatatoForm(printShortageAttendanceForm, request);
		printShortageAttendanceForm.setPrint(false);
		log.info("Exit Score Sheet Batch input");
		
		return mapping.findForward(CMSConstants.PRINT_SHORTAGE_INPUT);
	}

	/**
	 * @param printShortageAttendanceForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(PrintShortageAttendanceForm printShortageAttendanceForm,HttpServletRequest request) throws Exception{
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			printShortageAttendanceForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
		
		if (printShortageAttendanceForm.getProgramId() != null
				&& printShortageAttendanceForm.getProgramId().length() > 0) {
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.valueOf(printShortageAttendanceForm.getProgramId()));
			request.setAttribute("coursesMap", courseMap);
		}
		if (printShortageAttendanceForm.getProgramTypeId() != null && printShortageAttendanceForm.getProgramTypeId().length() > 0) {
			Map collegeMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(printShortageAttendanceForm.getProgramTypeId()));
			request.setAttribute("programMap", collegeMap);
		}
	}
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ScoreSheetAction - getCandidates");
		PrintShortageAttendanceForm printShortageAttendanceForm = (PrintShortageAttendanceForm) form;
		printShortageAttendanceForm.setPrint(false);
		ActionErrors errors = printShortageAttendanceForm.validate(mapping, request);
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.ATTENDANCE_SHORTAGE_TEMPLATE);
		if(list==null || list.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.attendanceShortage.configure"));
		}
		validateTime(printShortageAttendanceForm, errors);
		if (errors.isEmpty()) {
			try {
				List<String> selectedCandidates = PrintShortageAttendanceHandler.getInstance().getListOfStudentDetails(printShortageAttendanceForm, request);
				if (selectedCandidates.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(printShortageAttendanceForm, request);
					log.info("Exit getCandidates size 0");
					return mapping.findForward(CMSConstants.PRINT_SHORTAGE_INPUT);
				} 
				printShortageAttendanceForm.setMessageList(selectedCandidates);
				printShortageAttendanceForm.resetFields();
				printShortageAttendanceForm.setPrint(true);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				printShortageAttendanceForm.setErrorMessage(msg);
				printShortageAttendanceForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(printShortageAttendanceForm, request);			
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.PRINT_SHORTAGE_INPUT);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.PRINT_SHORTAGE_INPUT);
	}
	
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.PRINT_SHORTAGE_RESULT);
	}
	
	/**
	 * Method to validate the time format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateTime(PrintShortageAttendanceForm printShortageAttendanceForm, ActionErrors errors) {
		if(CommonUtil.checkForEmpty(printShortageAttendanceForm.getStartDate()) && CommonUtil.checkForEmpty(printShortageAttendanceForm.getEndDate()) && CommonUtil.isValidDate(printShortageAttendanceForm.getStartDate()) && CommonUtil.isValidDate(printShortageAttendanceForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(printShortageAttendanceForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(printShortageAttendanceForm.getEndDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
	}
}
