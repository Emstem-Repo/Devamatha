package com.kp.cms.actions.sap;

import java.util.Date;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.sap.ExamHallEntryForm;
import com.kp.cms.handlers.admission.DisciplinaryDetailsHandler;
import com.kp.cms.handlers.sap.ExamHallEntryHandler;
import com.kp.cms.utilities.CommonUtil;

public class ExamHallEntryAction extends BaseDispatchAction{
	ExamHallEntryHandler handler=ExamHallEntryHandler.getInsatnce();
/**
 * display the first page of exam hall entry by giri
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward initExamHallEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamHallEntryForm examHallEntryForm = (ExamHallEntryForm) form;
		reset(examHallEntryForm);
		return mapping.findForward(CMSConstants.INIT_EXAM_HALL_ENTRY);
	}
	/**
	 * to get student details by regNo 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		String regNo = request.getParameter("regNo");
       // HttpSession session = request.getSession(false);
	    if (regNo != null) {
	    	handler.getStudentDetails(regNo,request);
	    	return mapping.findForward("ajaxResponseForStudentData");
		} else {
			errors.add("error", new ActionError("knowledgepro.exam.UnvRegEntry.reg_entry"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_EXAM_HALL_ENTRY);
		}
	}
	/**
	 * to get student details by regNo 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateExamDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamHallEntryForm examHallEntryForm = (ExamHallEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
	   try {
			   if(examHallEntryForm.getRegNo()!=null && !examHallEntryForm.getRegNo().isEmpty()){
				    	boolean flag=handler.updateExamDetails(examHallEntryForm);
				    	if(flag){
				    		//success
				    		ActionMessage message = new ActionMessage("knowledgepro.sap.exam.update.success");
							messages.add("messages", message);
							saveMessages(request, messages);
							reset(examHallEntryForm);
				    	}else{
				    		// failed
				    		handler.getstudentData(examHallEntryForm);
							request.setAttribute("admOperation", "add");
							errors.add("error", new ActionError("knowledgepro.sap.exam.update.fail"));
							saveErrors(request, errors);
				    	}
			   }else{
				   errors.add("error", new ActionError("knowledgepro.hostel.leave.registerNo.required"));
					saveErrors(request, errors);
			   }
	   	} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				examHallEntryForm.setErrorMessage(msg);
				examHallEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	return mapping.findForward(CMSConstants.INIT_EXAM_HALL_ENTRY);
	}
	private void reset(ExamHallEntryForm examHallEntryForm) throws Exception{
		examHallEntryForm.setName(null);
		examHallEntryForm.setClas(null);
		examHallEntryForm.setDate(null);
		examHallEntryForm.setSession(null);
		examHallEntryForm.setVenue(null);
		examHallEntryForm.setRegNo(null);
		examHallEntryForm.setPresent(null);
	}
}
