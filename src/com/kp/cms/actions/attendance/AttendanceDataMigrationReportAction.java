package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.actions.admission.PromoteMarksUploadAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AdmissionBioDataReportForm;
import com.kp.cms.forms.admission.PromoteMarksUploadForm;
import com.kp.cms.forms.attendance.AttendanceDataMigrationForm;
import com.kp.cms.handlers.admission.AdmissionBioDataReportHandler;
import com.kp.cms.handlers.admission.PromoteMarksUploadHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceDataMigrationReportHandler;
import com.kp.cms.to.admission.PromoteMarksUploadTo;
import com.kp.cms.to.attendance.AttnBioDataPucTo;
import com.kp.cms.to.attendance.AttnMarksUploadTo;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AttendanceDataMigrationReportAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(AttendanceDataMigrationReportAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttnDataMigrationReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_DATA_MIGRATION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttnDataMigrationFirstPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttendanceDataMigrationForm attnDataMigrationForm = (AttendanceDataMigrationForm)form;
		attnDataMigrationForm.setCourseName(null);
		HttpSession session = request.getSession(false);
		session.removeAttribute("AttnBioDataTo");
		session.removeAttribute(CMSConstants.EXCEL_BYTES);
		setRequiredDataTOForm(attnDataMigrationForm);
		return mapping.findForward(CMSConstants.ATTN_BIO_DATA_FIRST_PAGE);
	}
	/**
	 * @param attnDataMigrationForm
	 * @throws Exception
	 */
	private void setRequiredDataTOForm( AttendanceDataMigrationForm attnDataMigrationForm)throws Exception {
		String mode = "AttnBioData";
		Map<String,String> courses=PromoteMarksUploadHandler.getInstance().getCourses(mode);
		attnDataMigrationForm.setCourses(courses);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchAttnBioData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttendanceDataMigrationForm attnDataMigrationForm = (AttendanceDataMigrationForm)form;
		ActionErrors errors = new ActionErrors();
		attnDataMigrationForm.setMode(null);
		attnDataMigrationForm.setDownloadExcel(null);
		try{
			if(attnDataMigrationForm.getAcademicYear()!=null && !attnDataMigrationForm.getAcademicYear().isEmpty()){
				HttpSession session = request.getSession();
				 List<AttnBioDataPucTo> attnBioDataTo = AttendanceDataMigrationReportHandler.getInstance().getAttnBioDataSearch(attnDataMigrationForm);
				if(!attnBioDataTo.isEmpty()){
					attnDataMigrationForm.setAttnBioDataList(attnBioDataTo);
					session.setAttribute("AttnBioDataTo",attnBioDataTo );
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.exam.examDefinition.academicYear.required"));
				addErrors(request, errors);
				return mapping.findForward(CMSConstants.ATTN_BIO_DATA_FIRST_PAGE);
			}
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			attnDataMigrationForm.setErrorMessage(msg);
			attnDataMigrationForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.ATTN_BIO_DATA_SECOND_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttendanceDataMigrationForm attnDataMigrationForm = (AttendanceDataMigrationForm)form;
		attnDataMigrationForm.setSelectedColumnsArray(null);
		attnDataMigrationForm.setUnselectedColumnsArray(null);
		return mapping.findForward(CMSConstants.INIT_EXCEL_ATTENDANCE_BIODATA);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AttendanceDataMigrationForm attnDataMigrationForm = (AttendanceDataMigrationForm)form;
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	attnDataMigrationForm.setMode(null);
	attnDataMigrationForm.setDownloadExcel(null);
	
		try {
			if(attnDataMigrationForm.getSelectedColumnsArray()!=null && !attnDataMigrationForm.getSelectedColumnsArray().toString().isEmpty()){
				setUserId(request, attnDataMigrationForm);
				boolean isUpdated =	AttendanceDataMigrationReportHandler.getInstance().exportTOExcel(attnDataMigrationForm,request);
		 		if(isUpdated){
		 			attnDataMigrationForm.setMode("excel");
		 			attnDataMigrationForm.setDownloadExcel("download");
					ActionMessage message = new ActionMessage("knowledgepro.reports.columnsUpdate");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
			}else {
				errors.add("error", new ActionError("knowledgepro.select.atleast.onecolumn"));
				addErrors(request, errors);			
				return mapping.findForward(CMSConstants.INIT_EXCEL_ATTENDANCE_BIODATA);
			}
	
	}catch (ApplicationException ae) {
		String msg = super.handleApplicationException(ae);
		attnDataMigrationForm.setErrorMessage(msg);
		attnDataMigrationForm.setErrorStack(ae.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	catch (Exception e) {
		String msg = super.handleApplicationException(e);
		attnDataMigrationForm.setErrorMessage(msg);
		attnDataMigrationForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}

	return mapping.findForward(CMSConstants.ATTN_BIO_DATA_SECOND_PAGE);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initAttendanceMarks(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AttendanceDataMigrationForm attnDataMigrationForm = (AttendanceDataMigrationForm)form;
	attnDataMigrationForm.reset();
	//setCourseMapToForm(attnDataMigrationForm);
	String mode="AttendanceMarks";
	setDataToForm(attnDataMigrationForm,mode);
	return mapping.findForward(CMSConstants.INIT_ATTENDANCE_MARKS);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initInternalMarks(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AttendanceDataMigrationForm attnDataMigrationForm = (AttendanceDataMigrationForm)form;
	attnDataMigrationForm.reset();
	//setCourseMapToForm(attnDataMigrationForm);
	String mode="InternalMarks";
	setDataToForm(attnDataMigrationForm,mode);
	return mapping.findForward(CMSConstants.INIT_INTERNAL_MARKS);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getAttendanceMarks(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	log.info("entered getPromoteMarks..");	
	AttendanceDataMigrationForm attnDataMigrationForm = (AttendanceDataMigrationForm)form;
	 ActionErrors errors = attnDataMigrationForm.validate(mapping, request);
	HttpSession session=request.getSession();
	if (errors.isEmpty()) {	
		try {
		     List<AttnMarksUploadTo> attnMarksToList=AttendanceDataMigrationReportHandler.getInstance().getAttendanceMarks(attnDataMigrationForm, session);
		     session.setAttribute("attnMarksToList", attnMarksToList);
		}catch(ApplicationException ae){
			String msg = super.handleApplicationException(ae);
			attnDataMigrationForm.setErrorMessage(msg);
			attnDataMigrationForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
				throw e;
		}
	} else {
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_ATTENDANCE_MARKS);
	}
	log.info("Exit getPromoteMarks..");
	return mapping.findForward(CMSConstants.ATTENDANCE_MARKS_REPORT);
}
/**
 * @param attnDataMigrationForm
 * @param mode 
 * @throws Exception
 */
public void setDataToForm(AttendanceDataMigrationForm attnDataMigrationForm, String mode)throws Exception{
	Map<String,String> classMap=AttendanceDataMigrationReportHandler.getInstance().getClasses(mode);
	attnDataMigrationForm.setClassesMap(classMap);
	Map<String,String> testMap=AttendanceDataMigrationReportHandler.getInstance().getTestIdents(mode);
	attnDataMigrationForm.setTestIdentMap(testMap);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getInternalMarks(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	log.info("entered getPromoteMarks..");	
	AttendanceDataMigrationForm attnDataMigrationForm = (AttendanceDataMigrationForm)form;
	 ActionErrors errors = attnDataMigrationForm.validate(mapping, request);
	HttpSession session=request.getSession();
	if (errors.isEmpty()) {	
		try {
		     List<AttnMarksUploadTo> interMarksToList=AttendanceDataMigrationReportHandler.getInstance().getInternalMarks(attnDataMigrationForm, session);
		     session.setAttribute("internalMarksToList", interMarksToList);
		}catch(ApplicationException ae){
			String msg = super.handleApplicationException(ae);
			attnDataMigrationForm.setErrorMessage(msg);
			attnDataMigrationForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
				throw e;
		}
	} else {
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_INTERNAL_MARKS);
	}
	log.info("Exit getPromoteMarks..");
	return mapping.findForward(CMSConstants.INTERNAL_MARKS_REPORT);
}
/**
 * @param marksUploadForm
 * @throws Exception
 */
//public void setCourseMapToForm(AttendanceDataMigrationForm attnDataMigrationForm ) throws Exception {
//	log.info("Entering into setCourseMapToForm of PromoteMarksUploadAction");
//
//	Calendar calendar = Calendar.getInstance();
//	int currentYear = calendar.get(Calendar.YEAR);
//	
//	int year = CurrentAcademicYear.getInstance().getAcademicyear();
//	if (year != 0) {
//		currentYear = year;
//	}
//	if (attnDataMigrationForm.getAcademicYear() != null && !attnDataMigrationForm.getAcademicYear().isEmpty()) {
//		currentYear=Integer.parseInt(attnDataMigrationForm.getAcademicYear());
//	}
//	Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByYear(currentYear);
//	//request.setAttribute("courseMap", courseMap);
//	attnDataMigrationForm.setCourseMap(courseMap);
//
//	log.info("Leaving into setCourseMapToForm of PromoteMarksUploadAction");
//}
}
