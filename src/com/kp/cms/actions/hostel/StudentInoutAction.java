package com.kp.cms.actions.hostel;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import com.kp.cms.bo.admin.HlInOut;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelCheckoutForm;
import com.kp.cms.handlers.hostel.HostelCheckinHandler;
import com.kp.cms.handlers.hostel.HostelCheckoutHandler;
import com.kp.cms.to.hostel.HlDamageTO;
import com.kp.cms.to.hostel.HostelCheckoutTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.forms.hostel.StudentInoutForm;
import com.kp.cms.handlers.hostel.StudentInoutHandler;
import com.kp.cms.to.hostel.StudentInoutTo;
import com.kp.cms.utilities.CommonUtil;

public class StudentInoutAction extends BaseDispatchAction{

	private static Log log = LogFactory.getLog(HostelCheckoutAction.class);
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentInout(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Entering initStudentInout of StudentInoutAction");
			StudentInoutForm studentInoutForm = (StudentInoutForm) form;
			studentInoutForm.clearMyFields();
			setRequestedDataToForm(studentInoutForm);
			log.debug("Exiting initStudentInout of StudentInoutAction");
			
		} catch (Exception npe) {
	    }
		return mapping.findForward(CMSConstants.STUDENT_IN_OUT);
	}
	

	private void setRequestedDataToForm(StudentInoutForm studentInoutForm) throws Exception {
		List<HostelTO> hostelList = HostelCheckinHandler.getInstance().getHostelDetails();
		studentInoutForm.setHostelList(hostelList);
	}


	/**
	 * getting the hostel StudentInout details for input fields
	 */
	public ActionForward getStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering getStudentDetails of getStudentInout Action");
		StudentInoutForm studentInoutForm = (StudentInoutForm) form;
		 ActionErrors errors = studentInoutForm.validate(mapping, request);
		validateStudentInout(studentInoutForm,errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, studentInoutForm);
				List<StudentInoutTo> studentInoutToList= StudentInoutHandler.getInstance().getStudentDetails(studentInoutForm);
				if(studentInoutToList==null || studentInoutToList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					studentInoutForm.clearMyFields();
					return mapping.findForward(CMSConstants.STUDENT_IN_OUT);
				} 
				studentInoutForm.setStudentInoutToList(studentInoutToList);
				request.getSession().setAttribute("studentInoutToList",studentInoutToList);
				//setAllDataToSecondPage(studentInoutForm,studentInoutTo);
		}
			else{
				saveErrors(request, errors);
				studentInoutForm.clearMyFields();
				return mapping.findForward(CMSConstants.STUDENT_IN_OUT);
			}
		} catch (Exception e) {
			log.error("Error in getStudentDetails");
				String msg = super.handleApplicationException(e);
				studentInoutForm.setErrorMessage(msg);
				studentInoutForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("Exiting getStudentDetails of getStudentInout");
		return mapping.findForward(CMSConstants.DISPLAY_STUDENT_IN_OUT);
	}
	
	/**
	 * common validation for input jsp
	 * @param studentInoutForm
	 * @param errors
	 */
	private void validateStudentInout(StudentInoutForm studentInoutForm,ActionErrors errors) {
		int count = 0;
		if(studentInoutForm.getHostelId()== null || studentInoutForm.getHostelId().trim().isEmpty()){
				errors.add(CMSConstants.ERRORS, new ActionError("errors.required","Hostel Name"));
		}
		if(studentInoutForm.getStudId()!= null && !studentInoutForm.getStudId().trim().isEmpty()){
			if(validSpecialChar(studentInoutForm.getStudId())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_SPECIAL_NOT_ALLOWED));
			}
		}
		else if(studentInoutForm.getStudName()!= null && !studentInoutForm.getStudName().trim().isEmpty()){
			if(validSpecialChar(studentInoutForm.getStudName())){
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_SPECIAL_NOT_ALLOWED));
			}
		}
		
		if(studentInoutForm.getStudId() != null && studentInoutForm.getStudId().length() !=0){
 			count = count + 1;
 		}
 		if(studentInoutForm.getStudName() !=null && studentInoutForm.getStudName().length() !=0){
 			count = count + 1;
 		}
 		
 		if((studentInoutForm.getStudId().length() == 0 && studentInoutForm.getStudName().length() == 0)) {
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_IDORNAME_REQUIRED));
		}
	 	else if (count > 1 ) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_STUDENT_BOTH_NOTREQUIRED));
	 	}
	}
	
	/**		
	 * submitting form details from a jsp page
	 * @param hostelCheckinForm
	 * @param errors
	 */
	public ActionForward submitStudentDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			log.info("Entering submitHostelCheckoutDetails of HostelCheckinAction");
			StudentInoutForm studentInoutForm = (StudentInoutForm) form;
			setUserId(request, studentInoutForm);
			ActionMessages messages = new ActionMessages();
			ActionErrors errors =  studentInoutForm.validate(mapping, request);
			String checkDetailsSaved="false";
			boolean checkStudentInOutForADay=false;
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				//setAllDataToSecondPage(studentInoutForm,hostelCheckoutTo);				
				return mapping.findForward(CMSConstants.DISPLAY_STUDENT_IN_OUT);
			}
			else{
				try {
					List<StudentInoutTo> studentInoutToList =studentInoutForm.getStudentInoutToList(); 
					Iterator<StudentInoutTo > stdIterator = studentInoutToList.iterator();
					StudentInoutTo studentInoutTo=null;
					int value=0;
					while (stdIterator.hasNext()) {	
						studentInoutTo = (StudentInoutTo) stdIterator.next();
						if(studentInoutTo.getInTime()!=null && !studentInoutTo.getInTime().isEmpty()&& studentInoutTo.getOutTime()!=null  && !studentInoutTo.getOutTime().isEmpty()){
							if(CommonUtil.ConvertStringToSQLDate(studentInoutTo.getOutTime()).before(CommonUtil.ConvertStringToSQLDate(studentInoutTo.getInTime()))||CommonUtil.ConvertStringToSQLDate(studentInoutTo.getOutTime()).equals(CommonUtil.ConvertStringToSQLDate(studentInoutTo.getInTime()))){
							value= value+1;
							}
							else{
								messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_STUDENT_ENTER_CORRECT_TIME));
								saveMessages(request, messages);
								studentInoutForm.clearMyFields();
								return mapping.findForward(CMSConstants.DISPLAY_STUDENT_IN_OUT);
							}
						}
					}
					if(value>0){
						
						checkStudentInOutForADay=StudentInoutHandler.getInstance().checkStudentInOutForADay(studentInoutToList);
						if(!checkStudentInOutForADay)
						{	
							checkDetailsSaved = StudentInoutHandler.getInstance().submitStudentDetails(studentInoutForm,studentInoutToList);
						}
						else
						{
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_STUDENT_IN_OUT_ALREADY_ENTERED));
							saveMessages(request, messages);
							return mapping.findForward(CMSConstants.DISPLAY_STUDENT_IN_OUT);
						}
							
					}
					else{
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_STUDENT_DATE_REQUIRED));
						saveMessages(request, messages);
						studentInoutForm.clearMyFields();
						return mapping.findForward(CMSConstants.DISPLAY_STUDENT_IN_OUT);
						
					}
					
					//StudentInoutTo studentInoutTo =(StudentInoutTo)request.getSession().getAttribute("studentDetailsList");
					//checkDetailsSaved = StudentInoutHandler.getInstance().submitStudentDetails(studentInoutForm,studentInoutToList);
					
					if(checkDetailsSaved.equalsIgnoreCase(CMSConstants.HOSTEL_STUDENT_TRUE)){
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_STUDENT_DETAILS_SUCCESS));
						saveMessages(request, messages);
						studentInoutForm.clearMyFields();
						return mapping.findForward(CMSConstants.STUDENT_IN_OUT);
					}
					else{
						errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_STUDENT_DETAILS_FAIL));
						saveErrors(request, errors);
						studentInoutForm.clearMyFields();
						return mapping.findForward(CMSConstants.STUDENT_IN_OUT);
					
					}
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					studentInoutForm.setErrorMessage(msg);
					studentInoutForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			//return mapping.findForward(CMSConstants.INIT_HOSTEL_CHECKOUT);
	}
	
	/**
	 * validate special characters of regNo
	 * @param regRollNo
	 * @return
	 */
	private boolean validSpecialChar(String regRollNo)
	{
		boolean result=false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9  \\s \t]+");
        Matcher matcher = pattern.matcher(regRollNo);
        result = matcher.find();
        return result;
    }
	
	
	
}
