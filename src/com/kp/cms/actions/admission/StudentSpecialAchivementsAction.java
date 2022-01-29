package com.kp.cms.actions.admission;

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
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.StudentSpecialAchivementsForm;
import com.kp.cms.handlers.admission.StudentSpecialAchivementsHandlers;
import com.kp.cms.to.admission.SpecialAchivementsTo;

public class StudentSpecialAchivementsAction extends BaseDispatchAction 
	{
		private static final Log log = LogFactory.getLog(StudentSpecialAchivementsAction.class);
	
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initStudentAchivements(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
				StudentSpecialAchivementsForm objForm = (StudentSpecialAchivementsForm) form;
				objForm.clear();
				try
				{
					Map<Integer, String> termNumberMap = StudentSpecialAchivementsHandlers.getInstance().getTermNumbers();
					HttpSession session = request.getSession();
					session.setAttribute("TermNumberMap", termNumberMap);
					List<SpecialAchivementsTo>achivementList=StudentSpecialAchivementsHandlers.getInstance().getAchivementList();
					objForm.setAchivementList(achivementList);
				}
				catch (Exception e) {
					// TODO: handle exception
					String msg = super.handleApplicationException(e);
					objForm.setErrorMessage(msg);
					objForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				return mapping.findForward(CMSConstants.SPECIAL_ACHIVEMENTS);
		}
		
		/** to add the achivements
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward addStudentAchivements(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
		{
			log.debug("inside addStudentAchivements  Action");
			String templateString = request.getParameter(CMSConstants.EDITOR_DEFAULT);
			StudentSpecialAchivementsForm objForm = (StudentSpecialAchivementsForm) form;
			objForm.setAchivements(templateString);
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = objForm.validate(mapping, request);
			try
			{
				if(objForm.getTermNumber()==null || objForm.getTermNumber().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				}
				if(errors.isEmpty())
				{
					setUserId(request, objForm);
					String regNo=objForm.getRegisterNo();
					Student student=StudentSpecialAchivementsHandlers.getInstance().validRegNo(regNo);
					if(student!=null)
					{
						boolean isAdded ;
						isAdded = StudentSpecialAchivementsHandlers.getInstance().addAchivements(objForm,student);
						if(isAdded)
						{
							List<SpecialAchivementsTo>achivementList=StudentSpecialAchivementsHandlers.getInstance().getAchivementList();
							objForm.setAchivementList(achivementList);
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ACHIVEMENTS_ADDED_SUCCESS));
							saveMessages(request, messages);
							objForm.clear();
						}
						else 
						{
							errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ACHIVEMENTS_ADDED_FAILED));
							saveErrors(request, errors);
						}
					}
					else
					{
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.REGISTERNO_INVALID));
						saveErrors(request, errors);
					}
				}
				else 
				{
					saveErrors(request, errors);
				}
			}
			catch (DuplicateException e) 
			{
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.special.achivements.duplicate",objForm.getRegisterNo()));
				saveErrors(request, errors);
			}
			catch (ReActivateException e) 
			{
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.special.achivements.reactivate"));
				saveErrors(request, errors);
			}
			catch (Exception e) 
			{
				log.error("error in adding achivements of Action.", e);
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.SPECIAL_ACHIVEMENTS);
		}
		
		public ActionForward deleteAchivement(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
		{
			StudentSpecialAchivementsForm objForm = (StudentSpecialAchivementsForm) form;
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			try
			{
				boolean isDeleted=StudentSpecialAchivementsHandlers.getInstance().deleteStudentAchivements(objForm.getId());
				if(isDeleted)
				{
					
					List<SpecialAchivementsTo>achivementList=StudentSpecialAchivementsHandlers.getInstance().getAchivementList();
					objForm.setAchivementList(achivementList);
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.special.achivements.delete.success"));
					saveMessages(request, messages);
					objForm.clear();
				}
				else
				{
					errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.special.achivements.delete.failed"));
					saveErrors(request, errors);
				}
				
			}
			catch (Exception e) 
			{
					// TODO: handle exception
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.SPECIAL_ACHIVEMENTS);
		}
		
		public ActionForward editAchivement(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
		{
			StudentSpecialAchivementsForm objForm = (StudentSpecialAchivementsForm) form;
			ActionErrors errors = new ActionErrors();
			try
			{
				boolean isEdit=StudentSpecialAchivementsHandlers.getInstance().editAchivement(objForm);
				if(!isEdit)
				{
					errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.special.achivements.edit.failed"));
					saveErrors(request, errors);
				}
				else
				{
					request.setAttribute("operation", "edit");
				}
				
			}
			catch (Exception e) 
			{
					// TODO: handle exception
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.SPECIAL_ACHIVEMENTS);
		}
		
		public ActionForward updateAchivement(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
		{
			String templateString = request.getParameter(CMSConstants.EDITOR_DEFAULT);
			StudentSpecialAchivementsForm objForm = (StudentSpecialAchivementsForm) form;
			objForm.setAchivements(templateString);
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = objForm.validate(mapping, request);
			try
			{
				if(objForm.getTermNumber()==null || objForm.getTermNumber().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				}
				if(errors.isEmpty())
				{
					setUserId(request, objForm);
					String regNo=objForm.getRegisterNo();
					Student student=StudentSpecialAchivementsHandlers.getInstance().validRegNo(regNo);
					if(student!=null)
					{
						boolean isAdded ;
						isAdded = StudentSpecialAchivementsHandlers.getInstance().updateAchivements(objForm,student);
						if(isAdded)
						{
							List<SpecialAchivementsTo>achivementList=StudentSpecialAchivementsHandlers.getInstance().getAchivementList();
							objForm.setAchivementList(achivementList);
							messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.special.achivements.update.success"));
							saveMessages(request, messages);
							objForm.clear();
						}
						else 
						{
							errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.special.achivements.update.failed"));
							saveErrors(request, errors);
							request.setAttribute("operation", "edit");
						}
					}
					else
					{
						request.setAttribute("operation", "edit");
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.REGISTERNO_INVALID));
						saveErrors(request, errors);
					}
				}
				else 
				{
					request.setAttribute("operation", "edit");
					saveErrors(request, errors);
				}
			}
			catch (DuplicateException e) 
			{
				request.setAttribute("operation", "edit");
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.special.achivements.duplicate",objForm.getRegisterNo()));
				saveErrors(request, errors);
			}
			catch (ReActivateException e) 
			{
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.special.achivements.reactivate"));
				saveErrors(request, errors);
			}
			catch (Exception e) 
			{
				log.error("error in adding achivements of Action.", e);
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.SPECIAL_ACHIVEMENTS);
		}
	
		public ActionForward reActivateAchivement(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
		{
			StudentSpecialAchivementsForm objForm = (StudentSpecialAchivementsForm) form;
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			try
			{
				boolean isReactivate=StudentSpecialAchivementsHandlers.getInstance().reActivateStudentAchivements(objForm.getReactivateId());
				if(isReactivate)
				{
					
					List<SpecialAchivementsTo>achivementList=StudentSpecialAchivementsHandlers.getInstance().getAchivementList();
					objForm.setAchivementList(achivementList);
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.special.achivements.reactive.success"));
					saveMessages(request, messages);
					objForm.clear();
				}
				else
				{
					errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.special.achivements.reactive.failed"));
					saveErrors(request, errors);
				}
				
			}
			catch (Exception e) 
			{
					// TODO: handle exception
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.SPECIAL_ACHIVEMENTS);
		}
		
}
