package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.AssignCertificateCourseForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.AssignCertificateCourseHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.AssignCertificateCourseDetailsTO;
import com.kp.cms.to.admission.AssignCertificateCourseTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AssignCertificateCourseAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(AssignCertificateCourseAction.class);
	
	/**
	 * Method to set the required data to the form to display it in assignCertificateCourse.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAssignCertificateCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initAssignCertificateCourse in AssignCertificateCourseAction");
		AssignCertificateCourseForm assignCertificateCourseForm = (AssignCertificateCourseForm) form;// Type Casting the ActionForm to required Form
		assignCertificateCourseForm.resetFields();// Reseting the fields to Initially displaying the data in jsp 
		setRequiredDatatoForm(assignCertificateCourseForm);//setting the Required data to a form
		setCertificateCoursetoForm(assignCertificateCourseForm);// setting the certificate course to form accourding to academic year
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		assignCertificateCourseForm.setProgramTypeList(programTypeList);
		assignCertificateCourseForm.setCourseList(new ArrayList<CourseTO>());
		log.info("Entered initAssignCertificateCourse in AssignCertificateCourseAction");
		
		return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE);
	}

	/**
	 * Setting the data to a form
	 * @param assignCertificateCourseForm
	 * @param request
	 */
	private void setRequiredDatatoForm(AssignCertificateCourseForm assignCertificateCourseForm) throws Exception {
		List<CourseTO> courseList = CourseHandler.getInstance().getCourse(0);// Getting the course list which are active from database to display in the course dropdown
		assignCertificateCourseForm.setCourseList(courseList);// course list setting in the form
		
		
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if(assignCertificateCourseForm.getAcademicYear()!=null && !assignCertificateCourseForm.getAcademicYear().isEmpty()){
			currentYear=Integer.parseInt(assignCertificateCourseForm.getAcademicYear());
		}else{
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}
		}
		
		List<AssignCertificateCourseTO> assignCertificateCourseTOs=AssignCertificateCourseHandler.getInstance().getAssignCertificateCourses(currentYear,assignCertificateCourseForm.getSemType());// Getting the AssignCertificate Course which are active
		assignCertificateCourseForm.setAssignCertificateCourseTOs(assignCertificateCourseTOs);// setting to the form 
	}
	/**
	 * Setting the data to a form
	 * @param assignCertificateCourseForm
	 * @param request
	 */
	private void setCertificateCoursetoForm(AssignCertificateCourseForm assignCertificateCourseForm) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if(assignCertificateCourseForm.getAcademicYear()!=null && !assignCertificateCourseForm.getAcademicYear().isEmpty()){
			currentYear=Integer.parseInt(assignCertificateCourseForm.getAcademicYear());
		}else{
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}
		}
		
		List<AssignCertificateCourseDetailsTO> certificateCourseList = AssignCertificateCourseHandler.getInstance().getActiveCertificateCourses(currentYear,assignCertificateCourseForm.getSemType());// getting the certificate Course which are active
		Collections.sort(certificateCourseList);
		assignCertificateCourseForm.setAssignCertificateCourseDetailsTOs(certificateCourseList);// setting the certificate Course in to the form
	}
	/**
	 * adding the docExam to database
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAssignCertificateCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into addDocExam in DocumentExamEntryAction");
		AssignCertificateCourseForm assignCertificateCourseForm = (AssignCertificateCourseForm) form;// Type Casting the ActionForm to required Form
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = assignCertificateCourseForm.validate(mapping, request);
		boolean isAdded = false;
		if (errors.isEmpty()) {
			try{
				isAdded=AssignCertificateCourseHandler.getInstance().addAssignCertificateCourse(assignCertificateCourseForm,"add");
				if(isAdded){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.assignCertificateCourse.add.success"));
					assignCertificateCourseForm.resetFields();
					setRequiredDatatoForm(assignCertificateCourseForm);
					setCertificateCoursetoForm(assignCertificateCourseForm);
					saveMessages(request, messages);
					assignCertificateCourseForm.setCourseList(new ArrayList<CourseTO>());
				}
				else{
					setRequiredDatatoForm(assignCertificateCourseForm);
					setCertificateCoursetoForm(assignCertificateCourseForm);
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.assignCertificateCourse.add.failure"));
					addErrors(request, errors);
				}
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admission.assignCertificateCourse.Duplicate"));
					saveErrors(request, errors);
					setRequiredDatatoForm(assignCertificateCourseForm);
					setCertificateCoursetoForm(assignCertificateCourseForm);
					return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admission.assignCertificateCourse.reactivate"));
					saveErrors(request, errors);
					setRequiredDatatoForm(assignCertificateCourseForm);
					setCertificateCoursetoForm(assignCertificateCourseForm);
					return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				assignCertificateCourseForm.setErrorMessage(msg);
				assignCertificateCourseForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		} else {
			saveErrors(request, errors);
			setRequiredDatatoForm(assignCertificateCourseForm);
			setCertificateCoursetoForm(assignCertificateCourseForm);
		}
		log.info("Exit from addDocExam in DocumentExamEntryAction");
		return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE); 
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteAssignCertificateCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  deleteAssignCertificateCourse action");
		AssignCertificateCourseForm assignCertificateCourseForm = (AssignCertificateCourseForm) form;// Type Casting the ActionForm to required Form
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isDeleted=false;
		try {
			isDeleted=AssignCertificateCourseHandler.getInstance().deleteAssignCertificateCourse(assignCertificateCourseForm,"delete");
			if(isDeleted){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.assignCertificateCourse.delete.success"));
				assignCertificateCourseForm.resetFields();
				setRequiredDatatoForm(assignCertificateCourseForm);
				setCertificateCoursetoForm(assignCertificateCourseForm);
				saveMessages(request, messages);
				assignCertificateCourseForm.setCourseList(new ArrayList<CourseTO>());
			}
			else{
				setRequiredDatatoForm(assignCertificateCourseForm);
				setCertificateCoursetoForm(assignCertificateCourseForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.assignCertificateCourse.delete.failure"));
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in deleteAssignCertificateCourse of DocumentExamEntryAction", e);
			String msg = super.handleApplicationException(e);
			assignCertificateCourseForm.setErrorMessage(msg);
			assignCertificateCourseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit from  deleteAssignCertificateCourse action");
		return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE); 	
	}
	/**
	 * Used when edit button is clicked
	 */

	public ActionForward editAssignCertificateCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into editRoomType of RoomType Action");
		AssignCertificateCourseForm assignCertificateCourseForm = (AssignCertificateCourseForm) form;// Type Casting the ActionForm to required Form
		try {
			setRequiredDatatoForm(assignCertificateCourseForm);
			AssignCertificateCourseHandler.getInstance().editAssignCertificateCourse(assignCertificateCourseForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error occured in editRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			assignCertificateCourseForm.setErrorMessage(msg);
			assignCertificateCourseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into editRoomType of RoomType Action");
		return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE); 	
	}
	
	/**
	 * updating the docTypeExam
	 */
	public ActionForward updateAssignCertificateCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into addDocExam in DocumentExamEntryAction");
		AssignCertificateCourseForm assignCertificateCourseForm = (AssignCertificateCourseForm) form;// Type Casting the ActionForm to required Form
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = assignCertificateCourseForm.validate(mapping, request);
		boolean isUpdate = false;
		if (errors.isEmpty()) {
			try{
				if(isCancelled(request)){
					setRequiredDatatoForm(assignCertificateCourseForm);
					AssignCertificateCourseHandler.getInstance().editAssignCertificateCourse(assignCertificateCourseForm);
					request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE); 
				}
				isUpdate=AssignCertificateCourseHandler.getInstance().addAssignCertificateCourse(assignCertificateCourseForm,"update");
				if(isUpdate){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.assignCertificateCourse.update.success"));
					assignCertificateCourseForm.resetFields();
					setRequiredDatatoForm(assignCertificateCourseForm);
					setCertificateCoursetoForm(assignCertificateCourseForm);
					saveMessages(request, messages);
					assignCertificateCourseForm.setCourseList(new ArrayList<CourseTO>());
				}
				else{
					setRequiredDatatoForm(assignCertificateCourseForm);
					setCertificateCoursetoForm(assignCertificateCourseForm);
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.assignCertificateCourse.update.failure"));
					addErrors(request, errors);
				}
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admission.assignCertificateCourse.Duplicate"));
					saveErrors(request, errors);
					setRequiredDatatoForm(assignCertificateCourseForm);
					setCertificateCoursetoForm(assignCertificateCourseForm);
					return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE);	
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admission.assignCertificateCourse.reactivate"));
					saveErrors(request, errors);
					setRequiredDatatoForm(assignCertificateCourseForm);
					setCertificateCoursetoForm(assignCertificateCourseForm);
					return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				assignCertificateCourseForm.setErrorMessage(msg);
				assignCertificateCourseForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		} else {
			saveErrors(request, errors);
			setRequiredDatatoForm(assignCertificateCourseForm);
			setCertificateCoursetoForm(assignCertificateCourseForm);
		}
		log.info("Exit from addDocExam in DocumentExamEntryAction");
		return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE); 
	}
	/**
	 * Used to reactivate roomtype
	 */
	public ActionForward reactivateAssignCertificateCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  deleteAssignCertificateCourse action");
		AssignCertificateCourseForm assignCertificateCourseForm = (AssignCertificateCourseForm) form;// Type Casting the ActionForm to required Form
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isReactivated=false;
		try {
			isReactivated=AssignCertificateCourseHandler.getInstance().deleteAssignCertificateCourse(assignCertificateCourseForm,"reActive");
			if(isReactivated){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.assignCertificateCourse.reactivate.success"));
				assignCertificateCourseForm.resetFields();
				setRequiredDatatoForm(assignCertificateCourseForm);
				setCertificateCoursetoForm(assignCertificateCourseForm);
				saveMessages(request, messages);
			}
			else{
				setRequiredDatatoForm(assignCertificateCourseForm);
				setCertificateCoursetoForm(assignCertificateCourseForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.assignCertificateCourse.reactivate.failure"));
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in deleteAssignCertificateCourse of DocumentExamEntryAction", e);
			String msg = super.handleApplicationException(e);
			assignCertificateCourseForm.setErrorMessage(msg);
			assignCertificateCourseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit from  deleteAssignCertificateCourse action");
		return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE); 	
	}
	
	
	/**
	 * Method to initialize Class Entry wrt Academic year and isActive=1 to
	 * display in UI
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setCertificateCourses(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entering setClassEntry");
		AssignCertificateCourseForm assignCertificateCourseForm = (AssignCertificateCourseForm) form;// Type Casting the ActionForm to required Form
		try {
			setUserId(request, assignCertificateCourseForm);
			setRequiredDatatoForm(assignCertificateCourseForm);
			setCertificateCoursetoForm(assignCertificateCourseForm);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			assignCertificateCourseForm.setErrorMessage(msg);
			assignCertificateCourseForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving setClassEntry");
		return mapping.findForward(CMSConstants.ASSIGN_CERTIFICATE_COURSE);
	}
}
