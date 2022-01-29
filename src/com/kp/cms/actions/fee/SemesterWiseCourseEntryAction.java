package com.kp.cms.actions.fee;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
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
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Fee;
import com.kp.cms.bo.admin.SemesterWiseCourseBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.fee.SemesterWiseCourseEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.fee.FeeAssignmentHandler;
import com.kp.cms.utilities.CurrentAcademicYear;

public class SemesterWiseCourseEntryAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(SemesterWiseCourseEntryAction.class);
	
	public ActionForward initSemesterWiseCourseEntry(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SemesterWiseCourseEntryForm semesterWiseCourseEntryForm = (SemesterWiseCourseEntryForm)form;
		semesterWiseCourseEntryForm.clear();
		setUserId(request, semesterWiseCourseEntryForm);
		try{
			setRequiredDataToForm(semesterWiseCourseEntryForm,request);
		}catch (Exception e) {
			log.debug(e.getMessage());
	    	log.error("error in loading fee Account...",e);
			String msg = super.handleApplicationException(e);
			semesterWiseCourseEntryForm.setErrorMessage(msg);
			semesterWiseCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.SEMESTER_WISE_COURSE_ENTRY);
	}

	private void setRequiredDataToForm(
			SemesterWiseCourseEntryForm semesterWiseCourseEntryForm,
			HttpServletRequest request) {
		Map<Integer, String> courseMap = setCourseMapToForm(semesterWiseCourseEntryForm,request);
		semesterWiseCourseEntryForm.setCourseMap(courseMap);
	}

	private Map<Integer, String> setCourseMapToForm(
			SemesterWiseCourseEntryForm semesterWiseCourseEntryForm,
			HttpServletRequest request) {
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		try{
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			if(semesterWiseCourseEntryForm.getAcademicYear()!=null && !semesterWiseCourseEntryForm.getAcademicYear().isEmpty()){
				currentYear=Integer.parseInt(semesterWiseCourseEntryForm.getYear());
			}
			courseMap = FeeAssignmentHandler.getInstance().getCourse(year);
			//request.setAttribute("courseMap", courseMap);
			
		}catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		return courseMap;
	}

	public ActionForward saveSemesterWiseCourse(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		SemesterWiseCourseEntryForm semesterWiseCourseEntryForm = (SemesterWiseCourseEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = semesterWiseCourseEntryForm.validate(mapping, request);
		boolean duplicate = false;
		boolean isUpdate = false;
		boolean isSave = false;
		Fee fee = null;
		SemesterWiseCourseBO bo = null;
		Course course = FeeAssignmentHandler.getInstance().getCourseObject(semesterWiseCourseEntryForm.getCourseId());
		if(course != null && !course.toString().isEmpty()){
			semesterWiseCourseEntryForm.setProgramId(String.valueOf(course.getProgram().getId()));
			semesterWiseCourseEntryForm.setProgramTypeId(String.valueOf(course.getProgram().getProgramType().getId()));
		}
		try{
			setUserId(request, semesterWiseCourseEntryForm);
			//fee = FeeAssignmentHandler.getInstance().getduplicateRecord(semesterWiseCourseEntryForm);
			bo = FeeAssignmentHandler.getInstance().getExistingRecord(semesterWiseCourseEntryForm);
			if(bo != null){
				isUpdate = FeeAssignmentHandler.getInstance().saveFeeDetails(semesterWiseCourseEntryForm,bo);
			}else {
				isSave = FeeAssignmentHandler.getInstance().saveFeeDetails(semesterWiseCourseEntryForm,bo);
			}
			if(isSave){
				ActionMessage message = new ActionMessage("knowledgepro.fee.addsuccess"," Student Semester Fees");
				messages.add("messages", message);
				saveMessages(request, messages);
				semesterWiseCourseEntryForm.clear();
				return mapping.findForward(CMSConstants.SEMESTER_WISE_COURSE_ENTRY);
		 }else if(isUpdate){
			 ActionMessage message = new ActionMessage("knowledgepro.fee.updatesuccess"," Student Semester Fees");
				messages.add("messages", message);
				saveMessages(request, messages);
				semesterWiseCourseEntryForm.clear();
				return mapping.findForward(CMSConstants.SEMESTER_WISE_COURSE_ENTRY);
		 }else {
			 errors.add("error", new ActionError("kknowledgepro.fee.addfailure", "Student Semester Fees"));
				saveErrors(request,errors);
				semesterWiseCourseEntryForm.clear();
				return mapping.findForward(CMSConstants.SEMESTER_WISE_COURSE_ENTRY);
		 }
			
		}catch (Exception exception) {
			 exception.printStackTrace();
				if(exception instanceof DataNotFoundException) {
					errors.add("error", new ActionError("knowledgepro.exam.tokenRegistration.noMatchingExam"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.SEMESTER_WISE_COURSE_ENTRY);
				}	
				String msg = super.handleApplicationException(exception);
				semesterWiseCourseEntryForm.setErrorMessage(msg);
				semesterWiseCourseEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);				
			}
	}
	


}
