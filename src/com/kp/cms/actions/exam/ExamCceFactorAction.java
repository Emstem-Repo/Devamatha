package com.kp.cms.actions.exam;



import java.util.HashMap;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamCceFactorForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamCceFactorHandler;
import com.kp.cms.to.exam.ExamCceFactorTO;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamCceFactorAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(ExamCceFactorAction.class);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	
	// gets initial list of Exam Definition
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamCceFactor(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		   ExamCceFactorForm objForm = (ExamCceFactorForm) form;
		   objForm.clearPage();
		   errors = objForm.validate(mapping, request);
		   setUserId(request, objForm);
		   
		   String year=objForm.getYear();
		   if(year==null){
			  year=Integer.toString(CurrentAcademicYear.getInstance().getAcademicyear());
		     }
		   
		   setRequestToList(objForm, request,year);
		   setRequestedDataToForm(objForm);
		   return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
	}

	/**
	 * @param objForm
	 * @param request
	 * @param year 
	 * @return
	 * @throws Exception
	 */
	private ExamCceFactorForm setRequestToList(ExamCceFactorForm objForm,HttpServletRequest request, String year) throws Exception {
		   Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamByYear(year); 
		  objForm.setExamNameMap(examMap);
		   Map<Integer,String> subjectMap=CommonAjaxHandler.getInstance().getSubjectByYear(year);
		   objForm.setSubjectMap(subjectMap);
		   ExamAssignStudentsToRoomHandler examAssignStudenthandler = new ExamAssignStudentsToRoomHandler();
		   objForm.setExamTypeList((HashMap<Integer, String>) examAssignStudenthandler.getExamTypeList());
		   if(objForm.getExamType()==null || objForm.getExamType().isEmpty())
		   {
			   objForm.setExamType(null);
		   }
		   examMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objForm.getExamType(),Integer.parseInt(year));
		   objForm.setExamNameMap(examMap);
		   return objForm;
	}
	/**
	 * @param mappingobjForm.getExamType()
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addExamCceFactor (ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		   log.debug("inside addExamCceFactor in Action");
		  ExamCceFactorForm objForm = (ExamCceFactorForm) form;
		  setUserId(request, objForm);
		     ActionMessages messages = new ActionMessages();
		     ActionErrors errors = objForm.validate(mapping, request);
		     HttpSession session = request.getSession();
		     setUserId(request, objForm);
		     String mode="Add"; 
		  if(errors.isEmpty())
		     {
		         try
		         {
		        	 boolean isAdded = false;
		             isAdded = ExamCceFactorHandler.getInstance().addExamCceFactor(objForm, errors,session);
		                 if(isAdded)
		                 {
		                     messages.add("messages", new ActionMessage("knowledgepro.ccefactor.addsuccess"));
		                     saveMessages(request, messages);
		                     
		               } else
		                 { if(errors.isEmpty()){
		                     errors.add("error", new ActionError("knowledgepro.ccefactor.addfailure"));
		                     addErrors(request, errors);
		                 }else{
		                	 String year=objForm.getYear();
		          		   if(year==null){
		          			  year=Integer.toString(CurrentAcademicYear.getInstance().getAcademicyear());
		          		     }
		                	 addErrors(request, errors);
		                	 setRequestToList(objForm, request,year);
		          		     setRequestedDataToForm(objForm);
		                	 return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
		                 }
		                 }
		             
		         }
		         catch(Exception exception)
		         {
		             log.error("Error occured in caste Entry Action", exception);
		             String msg = super.handleApplicationException(exception);
		             objForm.setErrorMessage(msg);
		             objForm.setErrorStack(exception.getMessage());
		             return mapping.findForward(CMSConstants.ERROR_PAGE);
		         }
		     } else
		     {
		         saveErrors(request, errors);
		         setRequestToList(objForm, request,objForm.getYear());
		         return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
		     }
		     log.info("end of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
		     objForm.clearPage();
             String year=objForm.getYear();
  		     if(year==null){
  			 year=Integer.toString(CurrentAcademicYear.getInstance().getAcademicyear());
  		     }
  		     setRequestToList(objForm, request,year);
  		     objForm.setYear(year);
  		     setRequestedDataToForm(objForm);
		     return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
 }

	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setRequestedDataToForm(ExamCceFactorForm objForm) throws Exception{
		String year=objForm.getYear();
		     if(year==null){
		    	 year=Integer.toString(CurrentAcademicYear.getInstance().getAcademicyear());
		     }
    	List<ExamCceFactorTO> cceList = ExamCceFactorHandler.getInstance().getExamCceFactorList(year);
    	if(!cceList.isEmpty()){
    	objForm.setExamCceFactorList(cceList);
    	}
		
	}
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editExamCceFactor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
     {
	log.info("call of editFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
	 ExamCceFactorForm objForm = (ExamCceFactorForm) form;
     //ActionErrors errors = objForm.validate(mapping, request);
    log.debug("Entering editFeedBackQuestion ");
    try
    { String mode="year";
    	ExamCceFactorHandler.getInstance().editExamCceFactor(objForm,mode);
    	setRequestToList(objForm, request,objForm.getYear());
    	request.setAttribute("CceFactor", "edit");
        log.debug("Leaving editFeedBackQuestion ");
    }
    catch(Exception e)
    {
        log.error("error in editing FeedBackQuestion...", e);
        String msg = super.handleApplicationException(e);
        objForm.setErrorMessage(msg);
        objForm.setErrorStack(e.getMessage());
        return mapping.findForward(CMSConstants.ERROR_PAGE);
    }
    log.info("end of editFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
    return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
}
	
	
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateExamCceFactor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
 {
 	log.debug("Enter: updateFeedBackQuestion Action");
 	 ExamCceFactorForm objForm = (ExamCceFactorForm) form;
     HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		boolean isUpdated = false;
		if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					objForm.reset(mapping, request);
			        String formName = mapping.getName();
			        request.getSession().setAttribute("formName", formName);
			        String mode="";
			        ExamCceFactorHandler.getInstance().editExamCceFactor(objForm,mode);
			        request.setAttribute("CceFactor", "edit");
			        return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
				}
				setUserId(request, objForm);
				isUpdated = ExamCceFactorHandler.getInstance().addExamCceFactor(objForm, errors,session);
			    if (isUpdated) {
             ActionMessage message = new ActionMessage("knowledgepro.ccefactor.updatesuccess");
             messages.add("messages", message);
             saveMessages(request, messages);
             objForm.reset(mapping, request);
         } else {
             errors.add("error", new ActionError("knowledgepro.ccefactor.updatefailure"));
             addErrors(request, errors);
             objForm.reset(mapping, request);
         }
			} catch (Exception e) {
	            log.error("Error occured in edit valuatorcharges", e);
	            String msg = super.handleApplicationException(e);
	            objForm.setErrorMessage(msg);
	            objForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }}else{
				saveErrors(request, errors);
				setRequestedDataToForm(objForm);
		        request.setAttribute("CceFactor", "edit");
				return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
			}objForm.clearPage();
	        String year=objForm.getYear();
		   if(year==null){
			   year=Integer.toString(CurrentAcademicYear.getInstance().getAcademicyear());
		     }
		   
		  setRequestToList(objForm, request,year);
		  objForm.setYear(year);
		 setRequestedDataToForm(objForm);
     log.debug("Exit: action class updateFeedBackQuestion");
     return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
 }
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteExamCceFactor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
	log.debug("Action class. Delete valuatorCharges ");
	 ExamCceFactorForm objForm = (ExamCceFactorForm) form;
    ActionMessages messages = new ActionMessages();
    try
    {
        boolean isDeleted = ExamCceFactorHandler.getInstance().deleteExamCceFactor(objForm);
        if(isDeleted)
        {
            ActionMessage message = new ActionMessage("knowledgepro.ccefactor.deletesuccess");
            messages.add("messages", message);
            saveMessages(request, messages);
        } else
        {
            ActionMessage message = new ActionMessage("knowledgepro.ccefactor.deletefailure");
            messages.add("messages", message);
            saveMessages(request, messages);
        }
             
    }
    catch(Exception e)
    {
        log.error("error submit valuatorCharges...", e);
        if(e instanceof ApplicationException)
        {
            String msg = super.handleApplicationException(e);
            objForm.setErrorMessage(msg);
            objForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        } else
        {
            String msg = super.handleApplicationException(e);
            objForm.setErrorMessage(msg);
            objForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
    }
    objForm.clearPage();
    String year=objForm.getYear();
	     if(year==null){
	    	 year=Integer.toString(CurrentAcademicYear.getInstance().getAcademicyear());
	     }
	     setRequestToList(objForm, request,year);
	     objForm.setYear(year);
		 setRequestedDataToForm(objForm);
    log.debug("Action class. Delete valuatorCharges ");
    return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCCEfactorLists(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {

		log.info("Entering setClassEntry");
		ExamCceFactorForm objForm = (ExamCceFactorForm) form;
		ActionMessages messages = new ActionMessages();
		objForm.setExamCceFactorList(null);
		try {
			setUserId(request, objForm);
			String year=objForm.getYear();
		     if(year==null){
		    	 year=Integer.toString(CurrentAcademicYear.getInstance().getAcademicyear());
		     }
		     setRequestToList(objForm, request,year);
			setRequestedDataToForm(objForm);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving setClassEntry");
		
		return mapping.findForward(CMSConstants.EXAM_CCE_FACTOR);
	}
}
