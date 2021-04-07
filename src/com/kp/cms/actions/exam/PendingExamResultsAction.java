package com.kp.cms.actions.exam;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.PendingExamResultsForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.PendingExamResultsHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.PendingExamResultsTO;

public class PendingExamResultsAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PendingExamResultsAction.class);
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
	public ActionForward initPendingExamResults(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		  PendingExamResultsForm objForm = (PendingExamResultsForm) form;
		  objForm.setExamType(null);
		   objForm.clearPage();
		   errors = objForm.validate(mapping, request);
		   setUserId(request, objForm);
		   String year=objForm.getYear();
		   if(year==null){
			Calendar calendar = Calendar.getInstance();
			year =String.valueOf(calendar.get(Calendar.YEAR));
		     }
		   setExamToList(objForm, request,year);
		   objForm.setMode(null);
	        return mapping.findForward(CMSConstants.PENDING_EXAM_RESULT);
	}

	/**
	 * @param objForm
	 * @param request
	 * @param year 
	 * @return
	 * @throws Exception
	 */
	private PendingExamResultsForm setExamToList(PendingExamResultsForm objForm,HttpServletRequest request, String year) throws Exception {
		
		   ExamAssignStudentsToRoomHandler examAssignStudenthandler = new ExamAssignStudentsToRoomHandler();
		   objForm.setExamTypeList((HashMap<Integer, String>) examAssignStudenthandler.getRegularExamTypeList());
		   if(objForm.getExamType()==null || objForm.getExamType().isEmpty())
		   {
			   objForm.setExamType(null);
		   }
		   Map<Integer,String> examMap= CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objForm.getExamType(),Integer.parseInt(year));
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
	public ActionForward searchpendingExamResults (ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        log.info("call of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        PendingExamResultsForm objForm = (PendingExamResultsForm) form;
        ActionErrors errors = objForm.validate(mapping, request);
       // HttpSession session = request.getSession();
        objForm.setMode(null);
       if(errors.isEmpty())
        {
            try
            {
            	searchpendingExamResults(objForm,errors);
            	String year=objForm.getYear();
       		    if(year==null){
       			Calendar calendar = Calendar.getInstance();
       			year =String.valueOf(calendar.get(Calendar.YEAR));
       		     }
       		   setExamToList(objForm, request,year);
       		   if(errors!=null && !errors.isEmpty()){
			    addErrors(request, errors);
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
        	objForm.setExamType(null);
            saveErrors(request, errors);
            return mapping.findForward(CMSConstants.PENDING_EXAM_RESULT);
        }
        log.info("end of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.PENDING_EXAM_RESULT);
    }

	private void searchpendingExamResults(PendingExamResultsForm objForm,ActionErrors errors) throws Exception {
		List<PendingExamResultsTO> pendingResult=PendingExamResultsHandler.getInstance().PendingExamResultsList(objForm,errors);
		objForm.setExamPendingResultList(pendingResult);
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
		log.info("entered exportToExcel..");
		PendingExamResultsForm objForm = (PendingExamResultsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			objForm.setMode(null);
		boolean isUpdated =	PendingExamResultsHandler.getInstance().exportTOExcel(objForm,request,errors);
			 		if(isUpdated){
			 		ActionMessage message = new ActionMessage("knowledgepro.reports.pendingexamresult");
			 		objForm.setMode("excel");
						messages.add("messages", message);
						saveMessages(request, messages);
					
				}else {
					errors.add("error", new ActionError("knowledgepro.select.pendingexamresult"));
					addErrors(request, errors);			
					return mapping.findForward(CMSConstants.PENDING_EXAM_RESULT);
				}
		
		}catch (Exception e) {
			log.error("error occured in exportToExcel in AdmissionBioDataReportAction",e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	
		log.info("exit exportToExcel..");
		return mapping.findForward(CMSConstants.PENDING_EXAM_RESULT);
}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewDetails input");
		PendingExamResultsForm objForm = (PendingExamResultsForm) form;
		ActionErrors errors=new ActionErrors();
		try{
			if(objForm.getExamId() == null || objForm.getExamId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(objForm.getClassId()==null || objForm.getClassId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				saveErrors(request, errors);
			}
			if(errors==null || errors.isEmpty()){
				PendingExamResultsHandler.getInstance().getviewDetails(objForm);
			}else{
				return mapping.findForward(CMSConstants.PENDING_EXAM_RESULT);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewVerificationDetails input");
		
		return mapping.findForward("viewSuppliDetails");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayValuationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("DisplayValuationStatus input");
		PendingExamResultsForm objForm = (PendingExamResultsForm) form;
		try{
			PendingExamResultsHandler.getInstance().displayValuationDetails(objForm);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit displayValuationStatus input");
		
		return mapping.findForward("displayValuationStatusForPendingExamResults");
	}
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewValuationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewValuationDetails input");
		PendingExamResultsForm objForm = (PendingExamResultsForm) form;
		try{
				PendingExamResultsHandler.getInstance().viewValuationDetails(objForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewValuationDetails input");
		
		return mapping.findForward("viewValuationStatusForpendingExamResuls");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewVerificationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewVerificationDetails input");
		PendingExamResultsForm objForm = (PendingExamResultsForm) form;
		try{
			PendingExamResultsHandler.getInstance().viewVerificationDetails(objForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewVerificationDetails input");
		
		return mapping.findForward("viewValuationStatusForpendingExamResuls");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMismatchDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PendingExamResultsForm objForm = (PendingExamResultsForm) form;
				List<StudentTO> misMatchStuList = new ArrayList<StudentTO>();
				try{
					/*int courseId = 0;
					
					if(examValuationStatusForm.getCourseId()!=null && !examValuationStatusForm.getCourseId().isEmpty()){
						courseId = Integer.parseInt(examValuationStatusForm.getCourseId());
					}*/
					String subjectId_EvaluatorTypeId = null;
					if(objForm.getEvaluatorTypeId()!=null && !objForm.getEvaluatorTypeId().isEmpty()){
						subjectId_EvaluatorTypeId = objForm.getSubjectId()+"_"+objForm.getEvaluatorTypeId();
					} else{
						subjectId_EvaluatorTypeId = objForm.getSubjectId();
					}
					Map<Integer,Map<String,List<StudentTO>>> misMatchMap = objForm.getMisMatchStudentList();
					String subjectNameAndCode = null;
					if(misMatchMap!=null && !misMatchMap.isEmpty()){
						Map<String,List<StudentTO>> mismatchSubjectMap = misMatchMap.get(Integer.parseInt(objForm.getClassId()));
						if(mismatchSubjectMap!=null && !mismatchSubjectMap.isEmpty()){
							List<StudentTO> misMatchStudentDetails = mismatchSubjectMap.get(subjectId_EvaluatorTypeId);
							if(misMatchStudentDetails!=null && !misMatchStudentDetails.isEmpty()){
								Iterator<StudentTO> iterator  =misMatchStudentDetails.iterator();
								while (iterator.hasNext()) {
									StudentTO studentTO = (StudentTO) iterator.next();
									subjectNameAndCode = studentTO.getSubjectName();
									misMatchStuList.add(studentTO);
								}
							}
						}
					}
					objForm.setSubjectName(subjectNameAndCode);
					Collections.sort(misMatchStuList);
					objForm.setStudents(misMatchStuList);
				}catch (Exception e) {
					String msg = super.handleApplicationException(e);
					objForm.setErrorMessage(msg);
					objForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				return mapping.findForward("viewMisMatchMarksForpendingExamResuls");
		}
}
