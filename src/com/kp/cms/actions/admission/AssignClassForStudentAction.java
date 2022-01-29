package com.kp.cms.actions.admission;

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
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admission.AssignClassForStudentForm;
import com.kp.cms.handlers.admission.AssignClassForStudentHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.AssingClassForStudentTO;

public class AssignClassForStudentAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(GensmartCardDataAction.class);
	
	/**
	 * Method to open the init jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Download Formats Action Batch input");
		AssignClassForStudentForm assignClassForStudentForm = (AssignClassForStudentForm)form;
		HttpSession session = request.getSession();
		try{
			 Map<Integer,String> programMap = AssignClassForStudentHandler.getInstance().getProgramMap();
			session.setAttribute("programMap", programMap);
			assignClassForStudentForm.setProgramId(null);
			assignClassForStudentForm.setAssingClassForStudentTOs(null);
			clearFields(assignClassForStudentForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			assignClassForStudentForm.setErrorMessage(msg);
			assignClassForStudentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_STUDENT);
	}
	
	
	/**
	 * @param assignClassForStudentForm
	 */
	private void clearFields(AssignClassForStudentForm assignClassForStudentForm) {

		assignClassForStudentForm.setProgramId(null);
		assignClassForStudentForm.setCourseId(null);
		assignClassForStudentForm.setClassId(null);
		assignClassForStudentForm.setAssingClassForStudentTOs(null);
		assignClassForStudentForm.setStartNumber(null);
		assignClassForStudentForm.setEndNumber(null);
		assignClassForStudentForm.setLanguageNo("1");
		assignClassForStudentForm.setPercentageNo("2");
		assignClassForStudentForm.setGenderNo("3");
		assignClassForStudentForm.setNameNo("4");
		assignClassForStudentForm.setCategoryNo("5");
		assignClassForStudentForm.setSectionNo("6");
		assignClassForStudentForm.setClasses(null);
		//code added by sudhir
		assignClassForStudentForm.setClassNo("7");
		//
	}
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward studentSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Download Formats Action Batch input");
		HttpSession session = request.getSession();
		AssignClassForStudentForm assignClassForStudentForm = (AssignClassForStudentForm)form;
		ActionErrors errors = new ActionErrors();
		try{
			
			validateForm(assignClassForStudentForm , errors);
			assignClassForStudentForm.setClasses(null);
			if(errors.isEmpty()){
				List<AssingClassForStudentTO> tos =AssignClassForStudentHandler.getInstance().getStudentList(assignClassForStudentForm);
				assignClassForStudentForm.setAssingClassForStudentTOs(tos);
				if(assignClassForStudentForm.getProgramId() != null && !assignClassForStudentForm.getProgramId().isEmpty()){
					Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(	Integer.parseInt(assignClassForStudentForm.getProgramId()));
					session.setAttribute("courseMap", courseMap);
				}
				Map<Integer,String> classMap = new HashMap<Integer, String>();
				if(assignClassForStudentForm.getProgramId() != null && !assignClassForStudentForm.getProgramId().isEmpty() && assignClassForStudentForm.getCourseId() != null && !assignClassForStudentForm.getCourseId().isEmpty()){
					classMap = AssignClassForStudentHandler.getInstance().getClassMap(assignClassForStudentForm.getAcademicYear(),assignClassForStudentForm.getCourseId());
				}
				session.setAttribute("classMap", classMap);
				session.setAttribute("StudentList", tos);
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_STUDENT);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			assignClassForStudentForm.setErrorMessage(msg);
			assignClassForStudentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_STUDENT);
	}
	/**
	 * @param assignClassForStudentForm
	 * @param errors 
	 */
	private void validateForm(AssignClassForStudentForm assignClassForStudentForm, ActionErrors errors) {
		if(assignClassForStudentForm.getProgramId() == null || assignClassForStudentForm.getProgramId().isEmpty() ){
			errors.add(CMSConstants.ERROR, new ActionError("admissionFormForm.programId.required"));
		}
		if( assignClassForStudentForm.getCourseId() == null || assignClassForStudentForm.getCourseId().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("admissionFormForm.courseId.required"));
		}
//		if(assignClassForStudentForm.getLanguageNo() == null || assignClassForStudentForm.getLanguageNo().isEmpty() || assignClassForStudentForm.getPercentageNo() == null || assignClassForStudentForm.getPercentageNo().isEmpty() 
//				|| assignClassForStudentForm.getGenderNo() == null || assignClassForStudentForm.getGenderNo().isEmpty() || assignClassForStudentForm.getNameNo() == null || assignClassForStudentForm.getNameNo().isEmpty() 
//				|| assignClassForStudentForm.getCategoryNo() == null || assignClassForStudentForm.getCategoryNo().isEmpty()){
//			errors.add(CMSConstants.ERROR, new ActionError("assignClassForStudentForm.sortOrder.required"));
//		}
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sortStudentList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Download Formats Action Batch input");
		AssignClassForStudentForm assignClassForStudentForm = (AssignClassForStudentForm)form;
		try{
			List<AssingClassForStudentTO> list = assignClassForStudentForm.getAssingClassForStudentTOs();
			List<AssingClassForStudentTO> tos =AssignClassForStudentHandler.getInstance().getSortedStudentList(assignClassForStudentForm.getProgramId(),assignClassForStudentForm.getSortBy(),list);
			if(assignClassForStudentForm.getSortBy() != null && !assignClassForStudentForm.getSortBy().isEmpty()){
//				Collections.sort(tos, new AssignClassForStudentComparator());
			}
			assignClassForStudentForm.setAssingClassForStudentTOs(tos);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			assignClassForStudentForm.setErrorMessage(msg);
			assignClassForStudentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_STUDENT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assignClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Download Formats Action Batch input");
		AssignClassForStudentForm assignClassForStudentForm = (AssignClassForStudentForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, assignClassForStudentForm);
		ActionErrors errors = new ActionErrors();
		try{
			if(assignClassForStudentForm.getClasses() == null || assignClassForStudentForm.getClasses().length==0){
				errors.add(CMSConstants.ERROR, new ActionError("admissionFormForm.class.required"));
			}
			if(errors.isEmpty()){
				boolean success = AssignClassForStudentHandler.getInstance().assignClass(assignClassForStudentForm);
				if(success){
					ActionMessage message = new ActionMessage("knowledgepro.admission.assignClass.addsuccess");
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_STUDENT);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			assignClassForStudentForm.setErrorMessage(msg);
			assignClassForStudentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		clearFields(assignClassForStudentForm);
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_STUDENT);
	}
	/**
	 * This method will set the user in to the form.
	 * @param request
	 * @param form
	 */
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}		
}
