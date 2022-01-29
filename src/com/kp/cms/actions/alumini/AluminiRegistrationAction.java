package com.kp.cms.actions.alumini;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.admission.OnlineApplicationAction;
import com.kp.cms.forms.alumini.AluminiRegistrationForm;
import com.kp.cms.handler.alumini.AluminiRegistrationHandler;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;
import com.kp.cms.to.alumni.AlumniEducationDetailsTO;

@SuppressWarnings("deprecation")
public class AluminiRegistrationAction extends OnlineApplicationAction {

	public ActionForward initAlumnRegistration(ActionMapping mapping,
			   								   ActionForm form,
			   								   HttpServletRequest request,
			   								   HttpServletResponse response) throws Exception {
		AluminiRegistrationForm aluminiRegistrationForm = (AluminiRegistrationForm) form;
		try {
			HttpSession session = request.getSession(true);
			session.setAttribute("isAlumini", true);
			aluminiRegistrationForm.clear();
			setMaintenanceAlertMessage(aluminiRegistrationForm, session);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return mapping.findForward("initAlumnRegistrationWelcome");
	}
	
	private void setMaintenanceAlertMessage(AluminiRegistrationForm aluminiRegistrationForm, HttpSession session) throws Exception {
		String maintenanceMessage = MaintenanceAlertHandler.getInstance().getMaintenanceDetailsByDate();
		if (maintenanceMessage != null) {
			aluminiRegistrationForm.setServerDownMessage(maintenanceMessage);
			session.setAttribute("serverDownMessage", maintenanceMessage);
		}
	}
	
	public ActionForward registerAlumnRegistration(ActionMapping mapping,
			   									   ActionForm form,
			   									   HttpServletRequest request,
			   									   HttpServletResponse response) throws Exception {
		AluminiRegistrationForm aluminiRegistrationForm = (AluminiRegistrationForm) form;
		try {
			aluminiRegistrationForm.clear();
			setRequiredDataToForm(aluminiRegistrationForm);
			return mapping.findForward("initAlumnRegistrationDetails");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionError("knowledgepro.admin.errorOccured", " Kindly please try again"));
			saveErrors(request, errors);
			return mapping.findForward("initAlumnRegistrationWelcome");
		}
	}
	
	public ActionForward submitAlumnRegistration(ActionMapping mapping,
			   									 ActionForm form,
			   									 HttpServletRequest request,
			   									 HttpServletResponse response) throws Exception {
		AluminiRegistrationForm aluminiRegistrationForm = (AluminiRegistrationForm) form;
		ActionErrors errors = aluminiRegistrationForm.validate(mapping, request);
		try {
			boolean isDuplicateMobileNumber = AluminiRegistrationHandler.getInstance().checkMobileNumberForDuplication(aluminiRegistrationForm);
			if(isDuplicateMobileNumber) {
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message", "Mobile number already exists, please register with a new mobile number."));
			}
			validatesEducationDetails(aluminiRegistrationForm, errors);
			if("Employee".equalsIgnoreCase(aluminiRegistrationForm.getCurrentStatus()) || 
			   "Employer".equalsIgnoreCase(aluminiRegistrationForm.getCurrentStatus()) ||
			   "Entrepreneur".equalsIgnoreCase(aluminiRegistrationForm.getCurrentStatus())) {
				if(aluminiRegistrationForm.getCompanyName() == null || aluminiRegistrationForm.getCompanyName().isEmpty()) {
					errors.add("error", new ActionError("knowledgepro.alumni.companyName"));
				}
				if(aluminiRegistrationForm.getDesignation() == null || aluminiRegistrationForm.getDesignation().isEmpty()) {
					errors.add("error", new ActionError("knowledgepro.alumni.currentDesignation"));
				}
			}
			else {
				if(aluminiRegistrationForm.getOtherJobDetails() == null || aluminiRegistrationForm.getOtherJobDetails().isEmpty()) {
					errors.add("error", new ActionError("knowledgepro.alumni.emptyOtherJobDescription"));
				}
			}
			
			if(errors.isEmpty()) {
				boolean isSaved = AluminiRegistrationHandler.getInstance().saveAluminiRegistrationDetails(aluminiRegistrationForm);
				if(isSaved) {
					ActionMessages messages = new ActionMessages();
					ActionMessage message = new ActionMessage("knowledgepro.alumni.registrationSuccess", aluminiRegistrationForm.getFirstName());
					messages.add("messages", message);
					saveMessages(request, messages);
				}
				else {
					errors.add("error", new ActionError("knowledgepro.admission.subject.entry.added.failure", " Alumni details!"));
					saveErrors(request, errors);
				}
			}
			else {
				saveErrors(request, errors);
				setRequiredDataToForm(aluminiRegistrationForm);
				return mapping.findForward("initAlumnRegistrationDetails");
			}
			return mapping.findForward("initAlumnRegistrationWelcome");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			errors.add("error", new ActionError("knowledgepro.admin.errorOccured", " Kindly please try again"));
			saveErrors(request, errors);
			return mapping.findForward("initAlumnRegistrationWelcome");
		}
	}
	
	public ActionForward addEducation(ActionMapping mapping,
				 					  ActionForm form,
				 					  HttpServletRequest request,
				 					  HttpServletResponse response) throws Exception {
		try {
			AluminiRegistrationForm aluminiRegistrationForm = (AluminiRegistrationForm) form;
			List<AlumniEducationDetailsTO> educationDetails = aluminiRegistrationForm.getEducationDetails();
			AlumniEducationDetailsTO educationDetailsTO = new AlumniEducationDetailsTO();
			educationDetails.add(educationDetailsTO);
			aluminiRegistrationForm.setEducationDetails(educationDetails);
			aluminiRegistrationForm.setEducationDetailsSize(aluminiRegistrationForm.getEducationDetails().size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("initAlumnRegistrationDetails");
	}
	
	public ActionForward removeEducation(ActionMapping mapping,
									  	 ActionForm form,
									  	 HttpServletRequest request,
									  	 HttpServletResponse response) throws Exception {
		try {
			AluminiRegistrationForm aluminiRegistrationForm = (AluminiRegistrationForm) form;
			List<AlumniEducationDetailsTO> educationDetails = aluminiRegistrationForm.getEducationDetails();
			if(educationDetails.size() != 1) {
				educationDetails.remove(educationDetails.size()-1);
				aluminiRegistrationForm.setEducationDetails(educationDetails);
			}
			aluminiRegistrationForm.setEducationDetailsSize(aluminiRegistrationForm.getEducationDetails().size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("initAlumnRegistrationDetails");
	}
	
	private void setRequiredDataToForm(AluminiRegistrationForm aluminiRegistrationForm) throws Exception {
		
		aluminiRegistrationForm.setCourseListStartApp(CourseHandler.getInstance().getActiveCourses());
		
		List<AlumniEducationDetailsTO> educationDetails = new ArrayList<AlumniEducationDetailsTO>();
		AlumniEducationDetailsTO educationDetailsTO = new AlumniEducationDetailsTO();
		educationDetails.add(educationDetailsTO);
		aluminiRegistrationForm.setEducationDetails(educationDetails);
		aluminiRegistrationForm.setEducationDetailsSize(aluminiRegistrationForm.getEducationDetails().size());
	}
	
	private ActionErrors validatesEducationDetails(AluminiRegistrationForm aluminiRegistrationForm, ActionErrors errors) throws Exception {
		try {
			List<AlumniEducationDetailsTO> educationDetails = aluminiRegistrationForm.getEducationDetails();
			Iterator<AlumniEducationDetailsTO> it = educationDetails.iterator();
			while (it.hasNext()) {
				AlumniEducationDetailsTO to = it.next();
				if(to.getCourseId() == null || to.getCourseId().isEmpty()) {
					errors.add("error", new ActionError("admissionFormForm.courseId.required"));
				}
				if(to.getBatchFrom() == null || to.getBatchFrom().isEmpty()) {
					errors.add("error", new ActionError("knowledgepro.cancelattendance.Frombatches", " is required!"));
				}
				if(to.getBatchTo() == null || to.getBatchTo().isEmpty()) {
					errors.add("error", new ActionError("knowledgepro.alumni.toBatch", " is required!"));
				}
				if(to.getPassOutYear() == null || to.getPassOutYear().isEmpty()) {
					errors.add("error", new ActionError("admissionFormForm.education.passYear.required"));
				}
				
				if(to.getBatchFrom() != null && !to.getBatchFrom().isEmpty() ||
				   to.getBatchTo() != null && !to.getBatchTo().isEmpty() ||
				   to.getPassOutYear() != null && !to.getPassOutYear().isEmpty()) {
					
					int fromBatch = Integer.parseInt(to.getBatchFrom());
					int toBatch = Integer.parseInt(to.getBatchTo());
					int passOutYear = Integer.parseInt(to.getPassOutYear());
					int year = Calendar.getInstance().get(Calendar.YEAR);
					if(fromBatch > toBatch || fromBatch > passOutYear) {
						errors.add("error", new ActionError("knowledgepro.alumni.errorBatch"));
					}
					if(toBatch > passOutYear) {
						errors.add("error", new ActionError("knowledgepro.alumni.errorBatchPassOut"));
					}
					if(fromBatch > year || toBatch > year || passOutYear > year) {
						errors.add("error", new ActionError("knowledgepro.alumni.invalidYear"));
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}
