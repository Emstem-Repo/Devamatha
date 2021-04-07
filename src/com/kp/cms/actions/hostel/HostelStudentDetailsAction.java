package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.DisciplinaryDetailsAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelStudentDetailsForm;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelStudentDetailsHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HostelTO;

public class HostelStudentDetailsAction extends BaseDispatchAction {
	
	private static final Logger log = Logger.getLogger(HostelDisciplinaryDetailsAction.class);
	HostelStudentDetailsHandler handler=new HostelStudentDetailsHandler();
	private static final String MESSAGE_KEY = "messages";
	
  public ActionForward initHostelStudentInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
  {
	  HostelStudentDetailsForm studentDetailsForm=(HostelStudentDetailsForm) form;
      studentDetailsForm.reset();
      studentDetailsForm.setHostelId(null);
      setHostelListToRequest(request);
	  return mapping.findForward(CMSConstants.HOSTEL_STUDENT_DETAILS);
	
  }
  
  
  public void setHostelListToRequest(HttpServletRequest request) throws Exception {
		log.info("Start of setHostelListToRequest in HostelDisciplinaryDetailsAction class");
		 List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		log.info("End of setHostelListToRequest in HostelDisciplinaryDetailsAction class");
	}
  
  public ActionForward getSearchedHostelStudents(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  {
	  HostelStudentDetailsForm studentDetailsForm=(HostelStudentDetailsForm) form;
	  ActionMessages errors = studentDetailsForm.validate(mapping, request);
		try {
			if(request.getSession().getAttribute("DisplinaryPhotoBytes")!=null){
				request.getSession().removeAttribute("DisplinaryPhotoBytes");
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			    setHostelListToRequest(request);
				return mapping
						.findForward(CMSConstants.HOSTEL_STUDENT_DETAILS);
			}
			if(studentDetailsForm.getRegNoOrRollNo()!=null || studentDetailsForm.getStudentName()!=null) 
			{
			boolean isCanceled = false;
			if(studentDetailsForm.getRegNoOrRollNo()!=null && !studentDetailsForm.getRegNoOrRollNo().isEmpty()){
				isCanceled = handler.checkHostelStudentIsCanceled(studentDetailsForm);
			}
			if(isCanceled){
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_STUDENT_ISACTIVE, studentDetailsForm.getRemarks());
				errors.add(HostelStudentDetailsAction.MESSAGE_KEY, message);
				saveErrors(request, errors);
				studentDetailsForm.setRemarks(null);
				return mapping
						.findForward(CMSConstants.HOSTEL_STUDENT_DETAILS);
			}
			List<HlAdmissionTo> admissionToList = handler.getHostelSearchedStudents(studentDetailsForm);
			if (admissionToList.isEmpty()) {

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(HostelStudentDetailsAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
                setHostelListToRequest(request);
                studentDetailsForm.reset();
                studentDetailsForm.setHostelId(null);
				return mapping
						.findForward(CMSConstants.HOSTEL_STUDENT_DETAILS);

			}
			studentDetailsForm.setHlAdmissionToList(admissionToList);
			}
			else
			{
				ActionMessage message = null;
				message = new ActionMessage("knowledgepro.hostel.enter.regNoRollNo.Or.Name");
				errors.add(HostelStudentDetailsAction.MESSAGE_KEY, message);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("error in getSearchedStudents...", e);
			String msg = super.handleApplicationException(e);
			studentDetailsForm.setErrorMessage(msg);
			studentDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		log.info("exit getSearchedStudents..");
		return mapping.findForward(CMSConstants.HOSTEL_STUDENT_VIEW_LIST);
  }
  
  public ActionForward getHostelStudentDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)
  {
	  return mapping.findForward(CMSConstants.HOSTEL_STUDENT_VIEW_DETAILS_LIST);
  }
  
}
