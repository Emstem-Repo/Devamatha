package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.RemoveAttendanceForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.RemoveAttendanceHandler;
import com.kp.cms.to.attendance.AttendanceReEntryTo;
import com.kp.cms.utilities.CurrentAcademicYear;

public class RemoveAttendanceAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(RemoveAttendanceAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This method will be invoke when the particular link clicked.
	 * 
	 */
	public ActionForward initAttendanceRemove(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceRemove");
		RemoveAttendanceForm removeAttendanceForm = (RemoveAttendanceForm) form;		
		try {
			setUserId(request, removeAttendanceForm);
			removeAttendanceForm.resetFields();
			setClassMapToRequest(request, removeAttendanceForm);
		}  catch (Exception e) {
			String msg = super.handleApplicationException(e);
			removeAttendanceForm.setErrorMessage(msg);
			removeAttendanceForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving from initAttendanceRemove");
		return mapping.findForward(CMSConstants.ATTENDANCE_REMOVE);
	}
	
	/**
	 * 
	 * @param request
	 * @param practicalBatchForm
	 * Sets all the classes for the current year in request scope
	 * @throws Exception
	 */

	public void setClassMapToRequest(HttpServletRequest request, RemoveAttendanceForm removeAttendanceForm)throws Exception {
		log.info("Entering into setClassMapToRequest of CreatePracticalBatchAction");		
		try {
				if (removeAttendanceForm.getYear() == null || removeAttendanceForm.getYear().isEmpty()) {
					//Below statements is used to get the current year and for the year get the class Map.
					Calendar calendar = Calendar.getInstance();
					int currentYear = calendar.get(Calendar.YEAR);
					
					// code by hari
					int year=CurrentAcademicYear.getInstance().getAcademicyear();
					if(year!=0){
						currentYear=year;
					}// end
					Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
					request.setAttribute("classMap", classMap);
				}
				//Otherwise get the classMap for the selected year
				else {
					int year = Integer.parseInt(removeAttendanceForm.getYear().trim());
					Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
					request.setAttribute("classMap", classMap);
			}
			//Used to get subjectMap for based on the class
				if(removeAttendanceForm.getClassSchemewiseId()!=null && removeAttendanceForm.getClassSchemewiseId().length()!=0){
					Map<Integer,String> subjectMap = new HashMap<Integer,String>();
					ClassSchemewise classSchemewise = CommonAjaxHandler.getInstance().getDetailsonClassSchemewiseId(Integer.parseInt(removeAttendanceForm.getClassSchemewiseId()));
						if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=null && classSchemewise.getClasses().getCourse().getId()!=0 && classSchemewise.getClasses().getTermNumber()!=0){
							int year=classSchemewise.getCurriculumSchemeDuration().getAcademicYear();
							int courseId=classSchemewise.getClasses().getCourse().getId();
							int term=classSchemewise.getClasses().getTermNumber();
							subjectMap = CommonAjaxHandler.getInstance().getSubjectByCourseIdTermYear(courseId,year,term);
						}
				request.setAttribute("subjectMap", subjectMap);
			}
			} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in Create Practical Batch Action", e);
			throw new ApplicationException(e);
		}
			log.info("Leaving into setClassMapToRequest of CreatePracticalBatchAction");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This will be called when second page of attendance will be needed.
	 * this method will redirect to different pages based on the requirement.
	 */
	public ActionForward initAttendanceRemoveSecondPage(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntrySecondPage");
		RemoveAttendanceForm removeAttendanceForm = (RemoveAttendanceForm) form;		
		ActionErrors errors=removeAttendanceForm.validate(mapping, request);
		String[] str = request.getParameterValues("subjects");
		if(str==null || str.length==0){
			errors.add(CMSConstants.ERROR,new ActionError("admissionFormForm.subject.required"));
		}
		try {
			if(errors.isEmpty()){
				Map<Date, AttendanceReEntryTo> attendanceReEntryTos=RemoveAttendanceHandler.getInstance().getAttendanceList(removeAttendanceForm);
				if(attendanceReEntryTos.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setClassMapToRequest(request, removeAttendanceForm);
					log.info("Exit initAttendanceRemoveSecondPage - size 0");
					return mapping.findForward(CMSConstants.ATTENDANCE_REMOVE);
				}else{
					Collection<AttendanceReEntryTo> clist=attendanceReEntryTos.values();
					List<AttendanceReEntryTo> list=new ArrayList<AttendanceReEntryTo>(clist);
					Collections.sort(list);
					removeAttendanceForm.setList(list);
					removeAttendanceForm.setAttendanceReEntryTos(attendanceReEntryTos);
				}
			}else{
				setClassMapToRequest(request, removeAttendanceForm);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ATTENDANCE_REMOVE);
			}
		}  catch (Exception e) {
	 		log.debug(e.getMessage());
	 		setClassMapToRequest(request, removeAttendanceForm);
	 		saveErrors(request,errors);
	 		return mapping.findForward(CMSConstants.ATTENDANCE_REMOVE);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_REMOVE_SECOND);
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This will be called when second page of attendance will be needed.
	 * this method will redirect to different pages based on the requirement.
	 */
	public ActionForward removeAttendanceForStudent(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initAttendanceEntrySecondPage");
		RemoveAttendanceForm removeAttendanceForm = (RemoveAttendanceForm) form;	
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		try {
			boolean result=false;
			setUserId(request, removeAttendanceForm);
			List<AttendanceReEntryTo> list=removeAttendanceForm.getList();
			if(list!=null && !list.isEmpty()){
				result=RemoveAttendanceHandler.getInstance().saveAttendanceReEntry(list,removeAttendanceForm);
			}
			if (result) {
				ActionMessage message = new ActionMessage("knowledgepro.attendancereentry.added.successfully");
				messages.add("messages", message);
				saveMessages(request, messages);
				removeAttendanceForm.resetFields();
				setClassMapToRequest(request, removeAttendanceForm);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.attendanceentry.added.failure"));
				saveErrors(request, errors);
				setClassMapToRequest(request, removeAttendanceForm);
			}
			}  catch (Exception e) {
	 		log.debug(e.getMessage());
	 		setClassMapToRequest(request, removeAttendanceForm);
	 		return mapping.findForward(CMSConstants.ATTENDANCE_REMOVE);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_REMOVE);
	}
}
