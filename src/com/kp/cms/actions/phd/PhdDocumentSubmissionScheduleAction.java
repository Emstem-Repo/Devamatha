package com.kp.cms.actions.phd;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.phd.PhdMailBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.phd.PhdDocumentSubmissionScheduleHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.phd.DocumentDetailsSubmissionTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.JobScheduler;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class PhdDocumentSubmissionScheduleAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(PhdDocumentSubmissionScheduleAction.class);
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initPhdDocumentSubmissionSchedule(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			documentSubmissionScheduleForm.clearList();
			try{
			List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
			Iterator<ProgramTypeTO> iterator = programTypeList.iterator();
			while (iterator.hasNext()) {
				ProgramTypeTO programbo = (ProgramTypeTO) iterator.next();
				if(programbo.getProgramTypeId() == 4){
					documentSubmissionScheduleForm.setCurrentProgramType(programbo.getProgramTypeId());
					break;
				}
			}
			ArrayList<KeyValueTO> list=new ArrayList<KeyValueTO>();
			ArrayList<CourseTO> courseList=new ArrayList<CourseTO>();
			if(documentSubmissionScheduleForm.getCurrentProgramType() !=0){
				list=CommonAjaxHandler.getInstance().getCoursesByProgramTypes1(String.valueOf(documentSubmissionScheduleForm.getCurrentProgramType()));
			}
			
			Iterator<KeyValueTO> iterator1 = list.iterator();
			while (iterator1.hasNext()) {
				KeyValueTO to = (KeyValueTO) iterator1.next();
				CourseTO courseTo=new CourseTO();
				courseTo.setId(to.getId());
				courseTo.setName(to.getDisplay());
				courseList.add(courseTo);
			}
			documentSubmissionScheduleForm.setProgramTypeList(programTypeList);
			documentSubmissionScheduleForm.setCourseList(courseList);
			}catch (Exception exception) {
				log.error("Error occured in getStudentDetails of AssignSubjectGroupHistoryAction",exception);
				String msg = super.handleApplicationException(exception);
				documentSubmissionScheduleForm.setErrorMessage(msg);
				documentSubmissionScheduleForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
		}
	  /**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public ActionForward getStudentDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			documentSubmissionScheduleForm.clearList1();
			 ActionErrors errors = documentSubmissionScheduleForm.validate(mapping, request);
			  if(errors.isEmpty())
			     {
			try{
				
				List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos = PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentSubmissionScheduleList(documentSubmissionScheduleForm);
				if(documentSubmissionTos == null || documentSubmissionTos.isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
					setCourseToForm(documentSubmissionScheduleForm);
					return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
				}
				documentSubmissionScheduleForm.setCountcheck(documentSubmissionTos.size());
				documentSubmissionScheduleForm.setStudentDetailsList(documentSubmissionTos);// setting to the form 
				setDocumentList(documentSubmissionScheduleForm);
				setCourseToForm(documentSubmissionScheduleForm);
				setDocumentDetails(request);
				}catch (Exception exception) {
				log.error("Error occured in getStudentDetails of AssignSubjectGroupHistoryAction",exception);
				String msg = super.handleApplicationException(exception);
				documentSubmissionScheduleForm.setErrorMessage(msg);
				documentSubmissionScheduleForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}	} else
		     {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
			}
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
		}
		/**
		 * @param documentSubmissionScheduleForm
		 * @throws Exception
		 */
		private void setCourseToForm(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception{
			List<CourseTO> listValues=PhdDocumentSubmissionScheduleHandler.getInstance().getCoursesByProgramTypes(documentSubmissionScheduleForm.getProgramTypeId());
			documentSubmissionScheduleForm.setCourseList(listValues);
	}
		/**
		 * @param request 
		 * @param assignSubGrpHistory
		 * @throws Exception 
		 */
		private void setDocumentDetails(HttpServletRequest request) throws Exception{
			List<DocumentDetailsSubmissionTO> listValues=PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentDetailsList();
	        request.getSession().setAttribute("DocumentDetails", listValues);
	}
		private void setDocumentList(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception{
			List<DocumentDetailsSubmissionTO> listValues=PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentList(documentSubmissionScheduleForm);
			documentSubmissionScheduleForm.setDocumentList(listValues);
			}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public ActionForward submitShow(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			   log.debug("inside addExamCceFactor in Action");
			   PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			   boolean flag=false;
			     ActionErrors errors = documentSubmissionScheduleForm.validate(mapping, request);
				  setUserId(request, documentSubmissionScheduleForm);
				  ActionMessages messages = new ActionMessages();
				  Iterator<DocumentDetailsSubmissionTO> formdocument=documentSubmissionScheduleForm.getDocumentList().iterator();
				  while (formdocument.hasNext()) {
				  DocumentDetailsSubmissionTO document = (DocumentDetailsSubmissionTO) formdocument.next();
				  if(document.getChecked1()!=null && !document.getChecked1().isEmpty()){
					  document.setTempChecked1("on");
				  } if(document.getChecked2()!=null && !document.getChecked2().isEmpty()){
					  document.setTempChecked2("on");
				  } if(document.getChecked3()!=null && !document.getChecked3().isEmpty()){
					  document.setTempChecked3("on");
				  } if(document.getChecked4()!=null && !document.getChecked4().isEmpty()){
					  document.setTempChecked4("on");
				  }
				  if((document.getAssignDate()==null || document.getAssignDate().isEmpty()) &&
				    (document.getChecked1()==null || document.getChecked1().isEmpty()) &&
				    (document.getChecked2()==null || document.getChecked2().isEmpty()) &&
				    (document.getChecked3()==null || document.getChecked3().isEmpty()) &&
				    (document.getChecked4()==null || document.getChecked4().isEmpty())){
					  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.cancelattendance.norecord"));
				  }else{
					  flag=true;
				  }
					
				 }if(flag==true){
					 errors.clear();
				 }
				  if(errors.isEmpty())
				     {
						try {
							boolean isAdded = false;
							isAdded = PhdDocumentSubmissionScheduleHandler.getInstance().addSubCategory(documentSubmissionScheduleForm,"Add",errors);
							if(!isAdded){
								addErrors(request, errors);
								return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
							}
							List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos = PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentSubmissionScheduleList(documentSubmissionScheduleForm);
							documentSubmissionScheduleForm.setStudentDetailsList(documentSubmissionTos);
							documentSubmissionScheduleForm.setCountcheck(documentSubmissionTos.size());
							setDocumentList(documentSubmissionScheduleForm);
							if (isAdded && errors.isEmpty()) {
								messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.Document.Submission.Schedule.addsuccess"));
								saveMessages(request, messages);
								setCourseToForm(documentSubmissionScheduleForm);
								setDocumentDetails(request);
							} else if(isAdded && errors!=null){
								addErrors(request, errors);
							}
							else{
								errors.add("error", new ActionError( "knowledgepro.Document.Submission.Schedule.addfailure"));
								addErrors(request, errors);
							}
							}
						catch (Exception exception) {
							log.error("Error occured in caste Entry Action", exception);
							String msg = super.handleApplicationException(exception);
							documentSubmissionScheduleForm.setErrorMessage(msg);
							documentSubmissionScheduleForm.setErrorStack(exception.getMessage());
							return mapping.findForward(CMSConstants.ERROR_PAGE);
						}
					} else
				     {  errors.clear();
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.cancelattendance.norecord"));
						saveErrors(request, errors);
						setCourseToForm(documentSubmissionScheduleForm);
						documentSubmissionScheduleForm.setDocumentId(null);
						setDocumentDetails(request);
						return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
					}
				     documentSubmissionScheduleForm.setDocumentId(null);
				     log.info("end of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
				     return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
		 }
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward AddDocumentToList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos=PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentSubmissionNotChange(documentSubmissionScheduleForm);
			documentSubmissionScheduleForm.setStudentDetailsList(documentSubmissionTos);
			documentSubmissionScheduleForm.setCountcheck(documentSubmissionTos.size());
			List<DocumentDetailsSubmissionTO> listValue = documentSubmissionScheduleForm.getDocumentList();
			if(listValue!=null && !listValue.isEmpty()){
				boolean flag=false;
			if(documentSubmissionScheduleForm.getDocumentId()!=null && !documentSubmissionScheduleForm.getDocumentId().isEmpty()){
				DocumentDetailsSubmissionTO to=PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentDetails(documentSubmissionScheduleForm);
				Iterator<DocumentDetailsSubmissionTO> itrr=listValue.iterator();
				while (itrr.hasNext()) {
					DocumentDetailsSubmissionTO detailsSubmission = (DocumentDetailsSubmissionTO) itrr.next();
					if(detailsSubmission.getId()==to.getId()){
						flag=false;
					}
				}if(!flag){
					listValue.add(to);
				}
				documentSubmissionScheduleForm.setDocumentList(listValue);
			     }
			}else{
				if(documentSubmissionScheduleForm.getDocumentId()!=null && !documentSubmissionScheduleForm.getDocumentId().isEmpty()){
				List<DocumentDetailsSubmissionTO> tos=PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentDetailsTo(documentSubmissionScheduleForm);
				documentSubmissionScheduleForm.setDocumentList(tos);
			       }
			    }
			log.info("End of resetExperienceInfo of EmpResumeSubmissionForm");
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
	}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward deleteDocumentList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			List<DocumentDetailsSubmissionTO> listValue = documentSubmissionScheduleForm.getDocumentList();
			Iterator<DocumentDetailsSubmissionTO> itr=listValue.iterator();
			while (itr.hasNext()) {
				DocumentDetailsSubmissionTO object = (DocumentDetailsSubmissionTO) itr.next();
				if(object.getId()==documentSubmissionScheduleForm.getId()){
					String mode="documentList";
				  PhdDocumentSubmissionScheduleHandler.getInstance().deletePhdDocumentDetails(documentSubmissionScheduleForm,mode);
					listValue.remove(object);
				   documentSubmissionScheduleForm.setDocumentList(listValue);
				    List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos=PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentSubmissionNotChange(documentSubmissionScheduleForm);
					documentSubmissionScheduleForm.setStudentDetailsList(documentSubmissionTos);
					documentSubmissionScheduleForm.setCountcheck(documentSubmissionTos.size());
				   return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
				}
			 }
			List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos=PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentSubmissionNotChange(documentSubmissionScheduleForm);
			setDocumentDetails(request);
			documentSubmissionScheduleForm.setStudentDetailsList(documentSubmissionTos);
			documentSubmissionScheduleForm.setCountcheck(documentSubmissionTos.size());
		    log.info("End of resetExperienceInfo of EmpResumeSubmissionForm");
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
	}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward editStudentDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			log.debug("Entering ValuatorCharges ");
			try {
				setDocumentList(documentSubmissionScheduleForm);
				PhdDocumentSubmissionScheduleHandler.getInstance().editStudentDetails(documentSubmissionScheduleForm);
				request.setAttribute("DocumentDetailsList", "edit");
				//request.setAttribute("Update", "Update");// setting update attribute
				log.debug("Leaving editValuatorCharges ");
			} catch (Exception e) {
				log.error("error in editing ValuatorCharges...", e);
				String msg = super.handleApplicationException(e);
				documentSubmissionScheduleForm.setErrorMessage(msg);
				documentSubmissionScheduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			setDocumentDetails(request);
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public ActionForward updateDocumentDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
			log.debug("Enter: updatevaluatorCharges Action");
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = documentSubmissionScheduleForm.validate(mapping, request);
			boolean isUpdated = false;
	        if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					documentSubmissionScheduleForm.reset(mapping, request);
					String formName = mapping.getName();
					request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
					PhdDocumentSubmissionScheduleHandler.getInstance().editStudentDetails(documentSubmissionScheduleForm);
					request.setAttribute("DocumentDetailsList", "edit");
					return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
				}
				setUserId(request, documentSubmissionScheduleForm); // setting user id to update
				
				isUpdated = PhdDocumentSubmissionScheduleHandler.getInstance().updateDocumentDetails(documentSubmissionScheduleForm,"Edit");
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.Document.Submission.Schedule.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				errors.add("error", new ActionError("knowledgepro.Document.Submission.Schedule.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
			}
			} catch (Exception e) {
				log.error("Error occured in edit valuatorcharges", e);
				String msg = super.handleApplicationException(e);
				documentSubmissionScheduleForm.setErrorMessage(msg);
				documentSubmissionScheduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}}else{
				saveErrors(request, errors);
				List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos = PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentSubmissionScheduleList(documentSubmissionScheduleForm);
				documentSubmissionScheduleForm.setStudentDetailsList(documentSubmissionTos);
				documentSubmissionScheduleForm.setCountcheck(documentSubmissionTos.size());
				request.setAttribute("DocumentDetailsList", "edit");
				return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
			}
	        List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos = PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentSubmissionScheduleList(documentSubmissionScheduleForm);
			documentSubmissionScheduleForm.setStudentDetailsList(documentSubmissionTos);
			documentSubmissionScheduleForm.setCountcheck(documentSubmissionTos.size());
	        setDocumentList(documentSubmissionScheduleForm);
			log.debug("Exit: action class updatevaluatorCharges");
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward deletePhdDocumentDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			log.debug("Entering ");
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			ActionMessages messages = new ActionMessages();
             String mode="details";
			try {
				boolean isDeleted =PhdDocumentSubmissionScheduleHandler.getInstance().deletePhdDocumentDetails(documentSubmissionScheduleForm,mode);
				if (isDeleted) {
					ActionMessage message = new ActionMessage("knowledgepro.Document.Submission.Schedule.delete.success");
					messages.add("messages", message);
					saveMessages(request, messages);
				} else {
					ActionMessage message = new ActionMessage("knowledgepro.Document.Submission.Schedule.delete.failed");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
				 List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos = PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentSubmissionScheduleList(documentSubmissionScheduleForm);
					documentSubmissionScheduleForm.setStudentDetailsList(documentSubmissionTos);
					documentSubmissionScheduleForm.setCountcheck(documentSubmissionTos.size());
			        setDocumentList(documentSubmissionScheduleForm);
					setCourseToForm(documentSubmissionScheduleForm);
					setDocumentDetails(request);
			} catch (Exception e) {
				log.error("error submit valuatorCharges...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					documentSubmissionScheduleForm.setErrorMessage(msg);
					documentSubmissionScheduleForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					String msg = super.handleApplicationException(e);
					documentSubmissionScheduleForm.setErrorMessage(msg);
					documentSubmissionScheduleForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			log.debug("Action class. Delete valuatorCharges ");
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_SCHEDULE);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initPhdDocumentSubmission(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
			PhdDocumentSubmissionScheduleForm objForm = (PhdDocumentSubmissionScheduleForm)form;
			objForm.setRegisterNo(null);
		    objForm.clearPage1();
		   setUserId(request, objForm);
		   return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_STUDENT);
	     }
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward searchStudentList(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	    throws Exception {

			log.info("Entering searchStudentList");
			PhdDocumentSubmissionScheduleForm objForm = (PhdDocumentSubmissionScheduleForm)form;
			ActionMessages messages = new ActionMessages();
			 ActionErrors errors = objForm.validate(mapping, request);
			objForm.clearPage1();
			if(errors.isEmpty()){
		try {
		     	 setUserId(request, objForm);
				 List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos=PhdDocumentSubmissionScheduleHandler.getInstance().getDocumentSubmissionByReg(objForm);
				 objForm.setStudentDetailsList(documentSubmissionTos);
			 if(documentSubmissionTos==null || documentSubmissionTos.isEmpty()){
					ActionMessage message = new ActionMessage("knowledgepro.norecords");
					messages.add("messages", message);
					saveMessages(request, messages);
			 }
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_STUDENT);
			}
			log.info("Leaving searchStudentList");
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_STUDENT);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward editSaveDocumentSubmission(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	    throws Exception {

			log.info("Entering editSaveDocumentSubmission");
			PhdDocumentSubmissionScheduleForm objForm = (PhdDocumentSubmissionScheduleForm)form;
			ActionMessages messages = new ActionMessages();
			 ActionErrors errors = objForm.validate(mapping, request);
			boolean isAdded=false;
			if(errors.isEmpty()){
		try {
		     	 setUserId(request, objForm);
		     	 isAdded=PhdDocumentSubmissionScheduleHandler.getInstance().upDateDocumentSubmission(objForm);
		     	if (isAdded) {
					ActionMessage message = new ActionMessage("knowledgepro.Document.Submission.added");
					messages.add("messages", message);
					saveMessages(request, messages);
				} else {
					ActionMessage message = new ActionMessage("knowledgepro.Document.Submission.failed");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}}else{
				saveErrors(request, errors);
				objForm.clearPage1();
				objForm.setRegisterNo(null);
				return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_STUDENT);
			}
			log.info("Leaving editSaveDocumentSubmission");
			objForm.clearPage1();
			objForm.setRegisterNo(null);
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION_STUDENT);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initPendingDocument(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			try{
			documentSubmissionScheduleForm.clearList();
			documentSubmissionScheduleForm.setStartDate(null);
			List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
			Iterator<ProgramTypeTO> iterator = programTypeList.iterator();
			while (iterator.hasNext()) {
				ProgramTypeTO programbo = (ProgramTypeTO) iterator.next();
				if(programbo.getProgramTypeId() == 4){
					documentSubmissionScheduleForm.setCurrentProgramType(programbo.getProgramTypeId());
					break;
				}
			}
			ArrayList<KeyValueTO> list=new ArrayList<KeyValueTO>();
			ArrayList<CourseTO> courseList=new ArrayList<CourseTO>();
			if(documentSubmissionScheduleForm.getCurrentProgramType() !=0){
				list=CommonAjaxHandler.getInstance().getCoursesByProgramTypes1(String.valueOf(documentSubmissionScheduleForm.getCurrentProgramType()));
			}
			
			Iterator<KeyValueTO> iterator1 = list.iterator();
			while (iterator1.hasNext()) {
				KeyValueTO to = (KeyValueTO) iterator1.next();
				CourseTO courseTo=new CourseTO();
				courseTo.setId(to.getId());
				courseTo.setName(to.getDisplay());
				courseList.add(courseTo);
			}
			documentSubmissionScheduleForm.setProgramTypeList(programTypeList);
			documentSubmissionScheduleForm.setCourseList(courseList);
			}catch (Exception exception) {
				log.error("Error occured in initPendingDocument of PhdDocumentSubmissionScheduleAction",exception);
				String msg = super.handleApplicationException(exception);
				documentSubmissionScheduleForm.setErrorMessage(msg);
				documentSubmissionScheduleForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}	
			return mapping.findForward(CMSConstants.PENDIND_DOCUMENT_SEARCH);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public ActionForward getDocumentPending(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			documentSubmissionScheduleForm.clearList1();
			 ActionErrors errors = documentSubmissionScheduleForm.validate(mapping, request);
			  if(errors.isEmpty())
			     {
			try{
				
				List<PhdDocumentSubmissionScheduleTO> pendingDocument = PhdDocumentSubmissionScheduleHandler.getInstance().getPendingDocumentSearch(documentSubmissionScheduleForm);
				if(pendingDocument == null || pendingDocument.isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
					setCourseToForm(documentSubmissionScheduleForm);
					return mapping.findForward(CMSConstants.PENDIND_DOCUMENT_SEARCH);
				}
				documentSubmissionScheduleForm.setStudentDetailsList(pendingDocument);// setting to the form 
				setCourseToForm(documentSubmissionScheduleForm);
				}catch (Exception exception) {
				log.error("Error occured in getStudentDetails of AssignSubjectGroupHistoryAction",exception);
				String msg = super.handleApplicationException(exception);
				documentSubmissionScheduleForm.setErrorMessage(msg);
				documentSubmissionScheduleForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}	} else
		     {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PENDIND_DOCUMENT_SEARCH);
			}
			return mapping.findForward(CMSConstants.PENDIND_DOCUMENT_SEARCH);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public ActionForward SendMailsPendingDocument(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm = (PhdDocumentSubmissionScheduleForm)form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = new ActionErrors();
			int i=0;
			try{
				Properties prop = new Properties();
				InputStream in = PhdDocumentSubmissionScheduleAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		        try
		        {
		        	prop.load(in);
		        }
		        catch (Exception e) {
					// TODO: handle exception
		        	e.printStackTrace();
				}
				String officeId=prop.getProperty("knowledgepro.phd.office.mail.toaddress");
				String adminmail=CMSConstants.MAIL_USERID;
				List<PhdDocumentSubmissionSchedule> mailIds = AdmissionFormTransactionImpl.getInstance().PendingDocumentSearch(documentSubmissionScheduleForm);
				if(mailIds != null){
					Iterator<PhdDocumentSubmissionSchedule> iterator = mailIds.iterator();
					TemplateHandler temphandle=TemplateHandler.getInstance();
					List<GroupTemplate> list= temphandle.getDuplicateCheckList("Phd- Document Pending Due mails");
					if(list != null && !list.isEmpty()) {
						while (iterator.hasNext()) {
							PhdDocumentSubmissionSchedule pendingDetails = (PhdDocumentSubmissionSchedule) iterator.next();
							MailTO mailto = new MailTO();
							Iterator<PhdDocumentSubmissionScheduleTO> documentTo=documentSubmissionScheduleForm.getStudentDetailsList().iterator();
					 while (documentTo.hasNext()) {
							PhdDocumentSubmissionScheduleTO sendMail = (PhdDocumentSubmissionScheduleTO) documentTo.next();
								
							if(sendMail.getRegisterNo().equalsIgnoreCase(pendingDetails.getStudentId().getRegisterNo()) && 
							  sendMail.getDocumentName().equalsIgnoreCase(pendingDetails.getDocumentId().getDocumentName()) && 
							  sendMail.getChecked()!=null)
							{
							if(pendingDetails.getStudentId().getAdmAppln().getPersonalData().getEmail() != null && !pendingDetails.getStudentId().getAdmAppln().getPersonalData().getEmail().trim().isEmpty()){
							PhdMailBo mail=	new PhdMailBo(pendingDetails.getId(),pendingDetails.getStudentId(),pendingDetails.getDocumentId(),new Date(),pendingDetails.getAssignDate(),"Document Due Mail",pendingDetails.getCreatedBy(),pendingDetails.getCreatedDate());					
							PropertyUtil.getInstance().save(mail);
							String desc ="";
							if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
								desc = list.get(0).getTemplateDescription();
							}
							desc = desc.replace("[CERTIFICATE_NAMES]", pendingDetails.getDocumentId().getDocumentName());
							desc = desc.replace("[RegisterNo]", pendingDetails.getStudentId().getRegisterNo());
							desc = desc.replace("[NAME]", pendingDetails.getStudentId().getAdmAppln().getPersonalData().getFirstName());
							desc = desc.replace("[BATCH]", Integer.toString(pendingDetails.getStudentId().getAdmAppln().getAppliedYear()));
							desc = desc.replace("[COURSE]", pendingDetails.getCourseId().getName());
							desc = desc.replace("[SUBMISSION_DATE]", CommonUtil.getStringDate(pendingDetails.getAssignDate()));
							mailto.setFromAddress(adminmail);
							mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
							mailto.setMessage(desc);
							mailto.setSubject("Document Pending Due mails - Christ University");
							String mails=pendingDetails.getStudentId().getAdmAppln().getPersonalData().getEmail()+","+officeId;
							String[] mail1=mails.split(",");
							for(int j=0;j<mail1.length;j++){
							mailto.setToAddress(mail1[j]);
							CommonUtil.sendMail(mailto);
							i++;
							}
							}
							
							}	
						}
					  }
					}
				}
			}catch (Exception exception) {
				log.error("Error occured in getStudentDetails of AssignSubjectGroupHistoryAction",exception);
				String msg = super.handleApplicationException(exception);
				documentSubmissionScheduleForm.setErrorMessage(msg);
				documentSubmissionScheduleForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}if(i>0){	
				ActionMessage message = new ActionMessage("knowledgepro.phd.sendMails.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				}else{
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
				}
			return mapping.findForward(CMSConstants.PENDIND_DOCUMENT_SEARCH);
		}
}