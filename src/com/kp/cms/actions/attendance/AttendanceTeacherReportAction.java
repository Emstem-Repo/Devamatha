package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceTeacherReportForm;
import com.kp.cms.handlers.attendance.AttendanceTeacherReportHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.attendance.AttendanceTeacherReportTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.attendance.StudentAbsentDetailsTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class AttendanceTeacherReportAction  extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AttendanceTeacherReportAction.class);

	public ActionForward initMonthlyTeacherReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initMonthlyTeacherReport..");
		AttendanceTeacherReportForm attendanceTeacherReportForm = (AttendanceTeacherReportForm)form;
		setRequiredDataToForm(attendanceTeacherReportForm,request);
		attendanceTeacherReportForm.resetFields();
		HttpSession session = request.getSession(false);
		session.removeAttribute(CMSConstants.MONTHLY_ATTENDENCE_TEACHER_REPORT);
		log.info("Exit initMonthlyTeacherReport..");
		return mapping.findForward(CMSConstants.INIT_ATTENDENCE_MONTHLY_TEACHER_REPORT);
	}

	public ActionForward submitMonthlyTeacherReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitMonthlyTeacherReport..");
		HttpSession session = request.getSession(false);
		if(session.getAttribute(CMSConstants.MONTHLY_ATTENDENCE_TEACHER_REPORT)==null){
			AttendanceTeacherReportForm attendanceTeacherReportForm = (AttendanceTeacherReportForm)form;
			 ActionMessages errors = attendanceTeacherReportForm.validate(mapping, request);
			validateAttendanceDate(attendanceTeacherReportForm, errors);
			if (errors.isEmpty()) {	
				try {
				 List teacherSearch = AttendanceTeacherReportHandler.getInstance().getMonthlyTeacherAttendanceResults(attendanceTeacherReportForm);
				if(teacherSearch !=null){			
				session.setAttribute(CMSConstants.MONTHLY_ATTENDENCE_TEACHER_REPORT,teacherSearch );
				}
	
				}catch(ApplicationException ae){
					String msg = super.handleApplicationException(ae);
					attendanceTeacherReportForm.setErrorMessage(msg);
					attendanceTeacherReportForm.setErrorStack(ae.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception e) {
						throw e;
				}
			} else {
				addErrors(request, errors);
				setRequiredDataToForm(attendanceTeacherReportForm, request);
				return mapping.findForward(CMSConstants.INIT_ATTENDENCE_MONTHLY_TEACHER_REPORT);
			}
		}
		log.info("Exit submitMonthlyTeacherReport..");
		return mapping.findForward(CMSConstants.SUBMIT_ATTENDENCE_MONTHLY_TEACHER_REPORT);
	}
	
	
	public ActionForward initTeacherReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initTeacherReport..");
		AttendanceTeacherReportForm attendanceTeacherReportForm = (AttendanceTeacherReportForm)form;
		setRequiredDataToForm(attendanceTeacherReportForm,request);
		attendanceTeacherReportForm.resetFields();
		HttpSession session = request.getSession(false);
		session.removeAttribute(CMSConstants.ATTENDENCE_TEACHER_REPORT);
		log.info("Exit initTeacherReport..");
		return mapping.findForward(CMSConstants.INIT_ATTENDENCE_TEACHER_REPORT);
	}
	
	public ActionForward submitTeacherReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submitTeacherReport..");
		HttpSession session = request.getSession(false);
		if(session.getAttribute(CMSConstants.ATTENDENCE_TEACHER_REPORT)==null){
			AttendanceTeacherReportForm attendanceTeacherReportForm = (AttendanceTeacherReportForm)form;
			 ActionMessages errors = attendanceTeacherReportForm.validate(mapping, request);
			validateAttendanceDate(attendanceTeacherReportForm, errors);
			if (errors.isEmpty()) {	
				try {
				 List teacherSearch = AttendanceTeacherReportHandler.getInstance().getTeacherAttendanceResults(attendanceTeacherReportForm);
				if (teacherSearch.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDataToForm(attendanceTeacherReportForm, request);
					return mapping.findForward(CMSConstants.INIT_ATTENDENCE_TEACHER_REPORT);
				} 
				if(teacherSearch !=null){			
				session.setAttribute(CMSConstants.ATTENDENCE_TEACHER_REPORT,teacherSearch );
				}
	
				}catch(ApplicationException ae){
					String msg = super.handleApplicationException(ae);
					attendanceTeacherReportForm.setErrorMessage(msg);
					attendanceTeacherReportForm.setErrorStack(ae.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception e) {
						throw e;
				}
			} else {
				addErrors(request, errors);
				setRequiredDataToForm(attendanceTeacherReportForm, request);
				return mapping.findForward(CMSConstants.INIT_ATTENDENCE_TEACHER_REPORT);
			}
		}	
		log.info("Exit submitTeacherReport..");
		return mapping.findForward(CMSConstants.SUBMIT_ATTENDENCE_TEACHER_REPORT);
	}


	private void setRequiredDataToForm(
			AttendanceTeacherReportForm attendanceSummaryReportForm,
			HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		Map<Integer,String> teachersMap =UserInfoHandler.getInstance().getTeachingStaff();
		attendanceSummaryReportForm.setTeachersMap(teachersMap);

		
	}
	
	/**
	 * 
	 * @param studentSearchForm
	 * @param errors
	 * This method is used to validate DATE 
	 */
	private void validateAttendanceDate(AttendanceTeacherReportForm attendanceSummaryReportForm,
			ActionMessages errors) {
			if(attendanceSummaryReportForm.getStartDate()!=null && !StringUtils.isEmpty(attendanceSummaryReportForm.getStartDate())&& !CommonUtil.isValidDate(attendanceSummaryReportForm.getStartDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_STARTDATE_INVALID));
			}
			if(attendanceSummaryReportForm.getEndDate()!=null && !StringUtils.isEmpty(attendanceSummaryReportForm.getEndDate())&& !CommonUtil.isValidDate(attendanceSummaryReportForm.getEndDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_ENDDATE_INVALID));
			}
			if(errors==null){
				//if start date greater than end date then showing error message
				if(CommonUtil.checkForEmpty(attendanceSummaryReportForm.getStartDate()) && CommonUtil.checkForEmpty(attendanceSummaryReportForm.getEndDate())){
					Date startDate = CommonUtil.ConvertStringToDate(attendanceSummaryReportForm.getStartDate());
					Date endDate = CommonUtil.ConvertStringToDate(attendanceSummaryReportForm.getEndDate());

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
	/**
	 *printing option for Attendance teacher Report
	 */
	public ActionForward printTeacherReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered TeacherReport");
		log.info("Exit TeacherReport");
		return mapping.findForward(CMSConstants.TEACHER_REPORT_PRINT);
	}
	/**
	 * printing option for monthly Attendance teacher Report
	 * @throws Exception
	 */
	public ActionForward printMonthlyTeacherReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered TeacherReport");
		log.info("Exit TeacherReport");
		return mapping.findForward("MonthlyAttendanceReportPrint");
	}
	
	public ActionForward getPeriodDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getPeriodDetails");
		AttendanceTeacherReportForm attendanceTeacherReportForm = (AttendanceTeacherReportForm)form;
		try{
			List<PeriodTO> periodList = AttendanceTeacherReportHandler.getInstance().getAttendanceTeacherPeriodDetails(attendanceTeacherReportForm);
			attendanceTeacherReportForm.setPeriodList(periodList);
		} catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceTeacherReportForm.setErrorMessage(msg);
			attendanceTeacherReportForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into getAbsencePeriodDetails of StudentWiseAttendanceSummaryAction");
		return mapping.findForward(CMSConstants.TEACHER_ATTENDANCE_PERIOD_DETAILS);	
	}
	public ActionForward initAttendanceTeacherReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		//AttendanceTeacherReportForm attendanceTeacherReportForm=(AttendanceTeacherReportForm)form;
		
		
		log.info("entered submitTeacherReport..");
		HttpSession session = request.getSession(false);
		
			AttendanceTeacherReportForm attendanceTeacherReportForm = (AttendanceTeacherReportForm)form;
			setUserId(request, attendanceTeacherReportForm);
			//String uname=attendanceTeacherReportForm.getUserId();
			//String userId=attendanceTeacherReportForm.getUserId();
			ActionMessages errors = new ActionErrors();
			//errors = attendanceTeacherReportForm.validate(mapping, request);
			//validateAttendanceDate(attendanceTeacherReportForm, errors);
			//if (errors.isEmpty()) {	
				
				try{
					List teacherSearch = AttendanceTeacherReportHandler.getInstance().getAttendanceTeacherSummaryReports(attendanceTeacherReportForm);
				if (teacherSearch.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					//setRequiredDataToForm(attendanceTeacherReportForm, request);
					
					return mapping.findForward(CMSConstants.NO_RECORDS);
				} 
				if(teacherSearch !=null){			
				session.setAttribute(CMSConstants.ATTENDENCE_TEACHER_REPORT,teacherSearch );
				}
				}
				catch(ApplicationException ae){
					String msg = super.handleApplicationException(ae);
					attendanceTeacherReportForm.setErrorMessage(msg);
					attendanceTeacherReportForm.setErrorStack(ae.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception e) {
						throw e;
				
			/*} else {
				addErrors(request, errors);
				setRequiredDataToForm(attendanceTeacherReportForm, request);
				return mapping.findForward(CMSConstants.INIT_ATTENDENCE_TEACHER_REPORT);
			}*/
		}	
		log.info("Exit submitTeacherReport..");
		return mapping.findForward(CMSConstants.ATTENDANCE_TEACHER_SUMMARY_REPORT);
		
	}
//	Method gets absent students for particular period
	public ActionForward getAbsentStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		//AttendanceTeacherReportForm attendanceTeacherReportForm=(AttendanceTeacherReportForm)form;
		log.info("entered submitTeacherReport..");
		AttendanceTeacherReportForm attendanceTeacherReportForm = (AttendanceTeacherReportForm)form;
		try{
			List<StudentAbsentDetailsTO> studentTO=AttendanceTeacherReportHandler.getInstance().getAbsentStudents(attendanceTeacherReportForm);
		attendanceTeacherReportForm.setStudentAbsentList(studentTO);
		}catch (Exception e) {		
			log.error("Error while getting the AbsencePeriodDetails"+e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceTeacherReportForm.setErrorMessage(msg);
			attendanceTeacherReportForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_STUDENT_ABSENTS);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInchargeTeacherClasses(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		AttendanceTeacherReportForm attendanceTeacherReportForm = (AttendanceTeacherReportForm)form;
		HttpSession session=request.getSession();
		ActionMessages errors = new ActionErrors();
		try{
			//if(session.getAttribute("attendanceTeacherReport")==null){
				setUserId(request, attendanceTeacherReportForm);
				List<AttendanceTeacherReportTO> incharge=AttendanceTeacherReportHandler.getInstance().getInchargeTeachersClassDetails(attendanceTeacherReportForm);
				if(incharge.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					//setRequiredDataToForm(attendanceTeacherReportForm, request);
					return mapping.findForward(CMSConstants.NO_RECORDS);
				}if(incharge!=null){
					session.setAttribute("attendanceTeacherReport", incharge);
				//}
		}}
			catch(Exception exception){
			log.error("Error while getting the AbsencePeriodDetails"+exception.getMessage());
			String msg = super.handleApplicationException(exception);
			attendanceTeacherReportForm.setErrorMessage(msg);
			attendanceTeacherReportForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_INCHARGE_TEACHER_DETAILS);
	}
}
