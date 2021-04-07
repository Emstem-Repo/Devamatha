package com.kp.cms.actions.admission;

import java.util.List;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.forms.admission.ExamCenterForm;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.admission.ExamCenterHandler;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admission.ExamCenterTO;
import com.kp.cms.to.exam.MarksCardTO;

	public class ExamCenterAction extends BaseDispatchAction{
		private static final Log log=LogFactory.getLog(ExamCenterAction.class);
		private static final String ProgramId = "ProgramId";
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initExamCenter(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ExamCenterForm examCenterForm = (ExamCenterForm) form;
			examCenterForm.clear();
			setUserId(request, examCenterForm);
			setProgramListToForm(examCenterForm);
			setExamCenterListToForm(examCenterForm);
			return mapping.findForward(CMSConstants.EXAM_CENTER);
		}
		
		/** Used to get Program List
		 * @param examCenterForm
		 * @throws Exception
		 */
		public void setProgramListToForm(ExamCenterForm examCenterForm)	throws Exception {
			List<ProgramTO> programList = ProgramHandler.getInstance().getProgram();
			examCenterForm.setProgramList(programList);
			log.debug("leaving setProgramListToForm");
		}
		
		/**
		 * @param examCenterForm
		 * @throws Exception
		 */
		public void setExamCenterListToForm(ExamCenterForm examCenterForm) throws Exception{
			List<ExamCenterTO> examCenterList=ExamCenterHandler.getInstance().getExamCenterDetails();
			examCenterForm.setExamCenterList(examCenterList);
		}
		
		/** Adding Exam Center
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward addExamCenter(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ExamCenterForm examCenterForm = (ExamCenterForm) form;
			ActionMessages messages=new ActionMessages();
			ActionErrors errors=examCenterForm.validate(mapping, request);
			
			validateSeatNo(examCenterForm, errors, request);
			boolean isAdded=false;
			try{
				if(errors.isEmpty()){
					
					isAdded=ExamCenterHandler.getInstance().addExamCenter(examCenterForm,"Add");
					if (isAdded) {
						// success .
						ActionMessage message = new ActionMessage("knowledgepro.admission.center.added.success", examCenterForm.getCenter());
						messages.add("messages", message);
						saveMessages(request, messages);
						setExamCenterListToForm(examCenterForm);
						examCenterForm.clear();
					} else {
						// failed
						errors.add("error", new ActionError("knowledgepro.admission.center.added.failure", examCenterForm.getCenter()));
						saveErrors(request, errors);
						setExamCenterListToForm(examCenterForm);
						return mapping.findForward(CMSConstants.EXAM_CENTER);
					}
				}else{
					saveErrors(request, errors);
				}
				
			}catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.admission.center.exists"));
				saveErrors(request, errors);
				//setProgramListToForm(examCenterForm);
				setExamCenterListToForm(examCenterForm);
				return mapping.findForward(CMSConstants.EXAM_CENTER);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError("knowledgepro.admission.center.reActivate"));
				saveErrors(request, errors);
				//setProgramListToForm(examCenterForm);
				setExamCenterListToForm(examCenterForm);
				return mapping.findForward(CMSConstants.EXAM_CENTER);
			} catch (Exception e) {
				log.error("error in final submit of course page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					examCenterForm.setErrorMessage(msg);
					examCenterForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			return mapping.findForward(CMSConstants.EXAM_CENTER);
		}
		
		/** Editing Exam Center
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward editExamCenter(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
				HttpServletResponse response) throws Exception {

			ExamCenterForm examCenterForm = (ExamCenterForm) form;
			int id=examCenterForm.getId();
			ExamCenterHandler.getInstance().getExamCenterDetailsToEdit(examCenterForm,id);
			log.debug("leaving editExamCenter");
			request.setAttribute("centerOperation", "Edit");
			return mapping.findForward(CMSConstants.EXAM_CENTER);
		}
		
		/** Deleting Exam Center
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward deleteExamCenter(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			ExamCenterForm examCenterForm = (ExamCenterForm) form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = new ActionErrors();
			boolean isDeleted = false;
			try {
				if(examCenterForm.getId()>0) {
					int centerId = examCenterForm.getId();
					isDeleted = ExamCenterHandler.getInstance().deleteExamCenter(centerId, false, examCenterForm);
				}
			} catch (Exception e) {
				log.error("error in final deleting ExamCenter page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					examCenterForm.setErrorMessage(msg);
					examCenterForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			if (isDeleted) {
				// success deleted
				ActionMessage message = new ActionMessage("knowledgepro.admission.center.deletesuccess", examCenterForm.getCenter());
				messages.add("messages", message);
				saveMessages(request, messages);
				setExamCenterListToForm(examCenterForm);
				examCenterForm.clear();
			} else {
				// failure error message.
				errors.add("error", new ActionError("knowledgepro.admission.center.deletefailure", examCenterForm.getCenter()));
				saveErrors(request, errors);
			}
			return mapping.findForward(CMSConstants.EXAM_CENTER);
		}
		
		/** ReActivate Exam Center
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward reActivateCenter(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
				HttpServletResponse response) throws Exception {

			ExamCenterForm examCenterForm = (ExamCenterForm) form;
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			boolean isActivated = false;
			try {
				if (examCenterForm.getDupId() != 0) {
					int duplCenterId = examCenterForm.getDupId();
					isActivated = ExamCenterHandler.getInstance().deleteExamCenter(duplCenterId, true, examCenterForm);
				}
			} catch (Exception e) {
				errors.add("error", new ActionError("knowledgepro.admission.center.reActivate.failure"));
				saveErrors(request, errors);
			}
			if (isActivated) {
				ActionMessage message = new ActionMessage("knowledgepro.admission.center.reActivate.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				setExamCenterListToForm(examCenterForm);
				examCenterForm.clear();
			}
			//request.setAttribute("centerOperation", "add");
			log.debug("leaving reActivateCenter");
			return mapping.findForward(CMSConstants.EXAM_CENTER);
			}
		
		/** Updating Exam Center
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward updateExamCenter(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ExamCenterForm examCenterForm = (ExamCenterForm) form;
			ActionMessages messages=new ActionMessages();
			ActionErrors errors=examCenterForm.validate(mapping, request);
			validateSeatNo(examCenterForm, errors, request);
			boolean isupdated=false;
			try{
				if (isCancelled(request)) {
					int id=examCenterForm.getId();
					ExamCenterHandler.getInstance().getExamCenterDetailsToEdit(examCenterForm,id);
					request.setAttribute("centerOperation", "Edit");
					return mapping.findForward(CMSConstants.EXAM_CENTER);
				}
				if(errors.isEmpty()){
					
					isupdated=ExamCenterHandler.getInstance().addExamCenter(examCenterForm,"Edit");
						if (isupdated) {
							// success .
							ActionMessage message = new ActionMessage("knowledgepro.admission.center.update.success", examCenterForm.getCenter());
							messages.add("messages", message);
							saveMessages(request, messages);
							setExamCenterListToForm(examCenterForm);
							examCenterForm.clear();
						} else {
							// failed
							errors.add("error", new ActionError("knowledgepro.admission.center.update.failure", examCenterForm.getCenter()));
							saveErrors(request, errors);
							setExamCenterListToForm(examCenterForm);
							request.setAttribute("centerOperation", "Edit");
							return mapping.findForward(CMSConstants.EXAM_CENTER);
						}
				}else{
					request.setAttribute("centerOperation", "Edit");
					saveErrors(request, errors);
				}
				
			}catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.admission.center.exists"));
				saveErrors(request, errors);
				setExamCenterListToForm(examCenterForm);
				request.setAttribute("centerOperation", "Edit");
				return mapping.findForward(CMSConstants.EXAM_CENTER);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError("knowledgepro.admission.center.reActivate"));
				saveErrors(request, errors);
				setExamCenterListToForm(examCenterForm);
				request.setAttribute("centerOperation", "Edit");
				return mapping.findForward(CMSConstants.EXAM_CENTER);
			} catch (Exception e) {
				log.error("error in final update of center page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					examCenterForm.setErrorMessage(msg);
					examCenterForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			return mapping.findForward(CMSConstants.EXAM_CENTER);
		}
		
		/** Validating SeatNo
		 * @param examCenterForm
		 * @param errors
		 * @param request
		 * @throws Exception
		 */
		public void validateSeatNo(ExamCenterForm examCenterForm,ActionErrors errors,HttpServletRequest request) throws Exception{
		 
			if(examCenterForm.getProgramId()!=null && !examCenterForm.getProgramId().isEmpty()){
				boolean isexamCentreRequired=ExamCenterHandler.getInstance().getExamCenterDefinedInProgram(Integer.parseInt(examCenterForm.getProgramId()));
				if(isexamCentreRequired)
					examCenterForm.setIsSeatNoValidationRequired("true");
				else
					examCenterForm.setIsSeatNoValidationRequired("false");
					
					if(isexamCentreRequired)
					{
						if(examCenterForm.getSeatNoFrom()==null || examCenterForm.getSeatNoFrom().isEmpty()){
							errors.add("error", new ActionError("knowledgepro.admission.seatNoFrom.required"));
							saveErrors(request, errors);
						}
						if(examCenterForm.getSeatNoTo()==null || examCenterForm.getSeatNoTo().isEmpty()){
							errors.add("error", new ActionError("knowledgepro.admission.seatNoTo.required"));
							saveErrors(request, errors);
						}
						if(examCenterForm.getSeatNoPrefix()==null || examCenterForm.getSeatNoPrefix().isEmpty()){
							errors.add("error", new ActionError("knowledgepro.admission.seatNoPrefix.required"));
							saveErrors(request, errors);
						}
						if(examCenterForm.getCurrentSeatNo()==null || examCenterForm.getCurrentSeatNo().isEmpty()){
							errors.add("error", new ActionError("knowledgepro.admission.currentSeatNo.required"));
							saveErrors(request, errors);
						}
						if(examCenterForm.getSeatNoFrom()!=null && !examCenterForm.getSeatNoFrom().isEmpty() && examCenterForm.getSeatNoTo()!=null && !examCenterForm.getSeatNoTo().isEmpty() && examCenterForm.getCurrentSeatNo()!=null && !examCenterForm.getCurrentSeatNo().isEmpty())
						{
							int seatNoFrom = Integer.parseInt(examCenterForm.getSeatNoFrom());
							int seatNoTo = Integer.parseInt(examCenterForm.getSeatNoTo());
							int currentSeatNo = Integer.parseInt(examCenterForm.getCurrentSeatNo());
							if(seatNoTo<seatNoFrom){
								errors.add("error", new ActionError("knowledgepro.admission.validate.seatNo"));
								saveErrors(request, errors);
							}
							if(currentSeatNo<seatNoFrom || currentSeatNo>seatNoTo){
								errors.add("error", new ActionError("knowledgepro.admission.validate.currentseatNo"));
								saveErrors(request, errors);
							}
						}
					}
		}
		}
		
		public ActionForward getExamCentreSeatNoRequired(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ExamCenterForm examCenterForm = (ExamCenterForm) form;
				//HttpSession session = request.getSession(false);
				String pgmId = examCenterForm.getProgramId();
				if (pgmId != null) {
					//session.setAttribute(ExamCenterAction.ProgramId, pgmId);
					boolean isexamCentreRequired=ExamCenterHandler.getInstance().getExamCenterDefinedInProgram(Integer.parseInt(pgmId));
					if(isexamCentreRequired){
						request.setAttribute("isSeatNoValidationRequired", true);
						//examCenterForm.setIsSeatNoValidationRequired("true");
					}else{
						request.setAttribute("isSeatNoValidationRequired", false);
						//examCenterForm.setIsSeatNoValidationRequired("false");
					}
				}
		return mapping.findForward(CMSConstants.EXAM_CENTRE_VALIDATION_AJAX);
		}
}
