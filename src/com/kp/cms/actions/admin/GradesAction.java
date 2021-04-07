package com.kp.cms.actions.admin;


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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.GradesForm;
import com.kp.cms.handlers.admin.GradesHandler;
import com.kp.cms.to.admin.GradeTO;
/**
 * 
 * @author
 *
 */
@SuppressWarnings("deprecation")
public class GradesAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(GradesAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set gradesList having gradeTo objects to request, forward to
	 *         gradesEntry
	 * @throws Exception
	 */
	public ActionForward initGrades(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		GradesForm gradesForm = (GradesForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setGradesListToRequest(request);
		} catch (Exception e) {
			log.error("error initGrades...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				gradesForm.setErrorMessage(msg);
				gradesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.GRADES_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Grade record
	 * @return to mapping gradesEntry
	 * @throws Exception
	 */
	public ActionForward addGrades(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		GradesForm gradesForm = (GradesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = gradesForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if(!errors.isEmpty()) {
				saveErrors(request, errors);
				setGradesListToRequest(request);
				//space should not get added in the table
				if(gradesForm.getGrade().trim().isEmpty()){
					gradesForm.setGrade(null);
				}
					
				return mapping.findForward(CMSConstants.GRADES_ENTRY);
			}
			setUserId(request, gradesForm);
			isAdded = GradesHandler .getInstance().addGrades(gradesForm);
			setGradesListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.grade.exists", gradesForm.getGrade()));
			saveErrors(request, errors);
			setGradesListToRequest(request);
			return mapping.findForward(CMSConstants.GRADES_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of grade page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				gradesForm.setErrorMessage(msg);
				gradesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.grade.addsuccess", gradesForm.getGrade());
			messages.add("messages", message);
			saveMessages(request, messages);
			gradesForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.grade.addfailure", gradesForm.getGrade()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.GRADES_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward This action method will called when particular
	 *         grade need to be deleted based on the id.
	 * @throws Exception
	 */
	
	public ActionForward deleteGrade(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		GradesForm gradesForm = (GradesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (gradesForm.getId() != 0) {
				int GradeId = gradesForm.getId();
				isDeleted = GradesHandler.getInstance().deleteGrades(GradeId,false,gradesForm);  // deleting record based on the id
			}
			setGradesListToRequest(request);
		} catch (Exception e) {
			log.error("error in delete Institute page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				gradesForm.setErrorMessage(msg);
				gradesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.grade.deletesuccess",	gradesForm.getGrade());
			messages.add("messages", message);
			saveMessages(request, messages);
			gradesForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.grade.deletefailure", gradesForm.getGrade()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.GRADES_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This action method will called when particular Grade need to be
	 *         updated based on the id.
	 * @throws Exception
	 */
	public ActionForward updateGrades(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		GradesForm gradesForm = (GradesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = gradesForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setGradesListToRequest(request);
				//space should not get added in the table
				if(gradesForm.getGrade().trim().isEmpty()){
					gradesForm.setGrade(null);
				}
				request.setAttribute("gradesOperation", "edit");
				return mapping.findForward(CMSConstants.GRADES_ENTRY);
			}
			if (gradesForm.getId() != 0) {
				setUserId(request, gradesForm);
				isUpdated = GradesHandler.getInstance().updateGrades(gradesForm);
			}
			setGradesListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.grade.exists"));
			saveErrors(request, errors);
			setGradesListToRequest(request);
			request.setAttribute("gradesOperation", "edit");
			return mapping.findForward(CMSConstants.GRADES_ENTRY);
		} catch (Exception e) {
			log.error("error in update state page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("gradesOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				gradesForm.setErrorMessage(msg);
				gradesForm.setErrorStack(e.getMessage());
				request.setAttribute("gradeOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isUpdated) {
			// successfully deleted.
			ActionMessage message = new ActionMessage("knowledgepro.admin.grade.updatesuccess",	gradesForm.getGrade());
			messages.add("messages", message);
			gradesForm.reset(mapping, request);
			saveMessages(request, messages);
			gradesForm.reset(mapping, request);
		} else {
			// failed to update.
			errors.add("error", new ActionError("knowledgepro.admin.institute.updatefailure", gradesForm.getGrade()));
			saveErrors(request, errors);
			request.setAttribute("gradesOperation", "edit");
			return mapping.findForward(CMSConstants.GRADES_ENTRY);
		}
		request.setAttribute("gradesOperation", "add");
		return mapping.findForward(CMSConstants.GRADES_ENTRY);
	}


	/**
	 * 
	 * @param request
	 *            This method sets the GradesList to Request used to display
	 *            GradesList record on UI.
	 * @throws Exception
	 */
	public void setGradesListToRequest(HttpServletRequest request) throws Exception {
		List<GradeTO> gradesList = GradesHandler.getInstance().getGrades();
		request.setAttribute("gradesList", gradesList);
	}

}

