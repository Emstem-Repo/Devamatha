package com.kp.cms.actions.examallotment;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.examallotment.ExamInvigilatorDutyEditForm;
import com.kp.cms.forms.examallotment.TeacherwiseExemptionForm;
import com.kp.cms.handlers.examallotment.TeacherwiseExemptionHandler;

public class TeacherwiseExemptionAction extends BaseDispatchAction{
	TeacherwiseExemptionHandler handler=TeacherwiseExemptionHandler.getInstance();
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTeacherwiseExemption(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeacherwiseExemptionForm teacherwiseExemptionForm=(TeacherwiseExemptionForm)form;
		reset(teacherwiseExemptionForm);
		setRequiredDataToForm(teacherwiseExemptionForm);
		return mapping.findForward("initTeacherwiseExemption");
	}

	private void reset(TeacherwiseExemptionForm teacherwiseExemptionForm) throws Exception{
		teacherwiseExemptionForm.setDeptId(null);
		teacherwiseExemptionForm.setLocationId(null);
		teacherwiseExemptionForm.setList(null);
		teacherwiseExemptionForm.setExamId(null);
		teacherwiseExemptionForm.setDeanaryId(null);
		teacherwiseExemptionForm.setExamInvigilatorsMap(null);
		teacherwiseExemptionForm.setCount(null);
	}

	private void setRequiredDataToForm(TeacherwiseExemptionForm teacherwiseExemptionForm) throws Exception{
		//set exammap,deanarymap and department map
		handler.getAllMaps(teacherwiseExemptionForm);
		 
	}
	/**
	 * search the invigilators
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTeachersList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeacherwiseExemptionForm teacherwiseExemptionForm=(TeacherwiseExemptionForm)form;
		teacherwiseExemptionForm.setList(null);
		teacherwiseExemptionForm.setAddMoreFlag(false);
		teacherwiseExemptionForm.setAddMorelist(null);
		teacherwiseExemptionForm.setCount(null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherwiseExemptionForm.validate(mapping, request);
		try {
			if(!errors.isEmpty() && errors!=null){
				saveErrors(request, errors);
			}else{
				
				handler.getTeachersList(teacherwiseExemptionForm);
				return mapping .findForward("teachersExemptionList");
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			teacherwiseExemptionForm.setErrorMessage(msg);
			teacherwiseExemptionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initTeacherwiseExemption");
	}
	/**
	 * search the invigilators
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteInvigilator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeacherwiseExemptionForm teacherwiseExemptionForm=(TeacherwiseExemptionForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			setUserId(request, teacherwiseExemptionForm); 
			boolean flag=handler.deleteInvigilators(teacherwiseExemptionForm);
			if(flag){
				// success deleted
				ActionMessage message = new ActionMessage("knowledgepro.petticash.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.attn.classteacher.deletefailure"));
				saveErrors(request, errors);
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			teacherwiseExemptionForm.setErrorMessage(msg);
			teacherwiseExemptionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("teachersExemptionList");
	}
	/**
	 * search the invigilators
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeacherwiseExemptionForm teacherwiseExemptionForm=(TeacherwiseExemptionForm)form;
		try {
		handler.addMore(teacherwiseExemptionForm);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			teacherwiseExemptionForm.setErrorMessage(msg);
			teacherwiseExemptionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("teachersExemptionList");
	}
	/**
	 * search the invigilators
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteMore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeacherwiseExemptionForm teacherwiseExemptionForm=(TeacherwiseExemptionForm)form;
		try {
			handler.deleteMore(teacherwiseExemptionForm);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			teacherwiseExemptionForm.setErrorMessage(msg);
			teacherwiseExemptionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("teachersExemptionList");
	}
	/**
	 * search the invigilators
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTeacherOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeacherwiseExemptionForm teacherwiseExemptionForm=(TeacherwiseExemptionForm)form;
		teacherwiseExemptionForm.setFocus(null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			setUserId(request, teacherwiseExemptionForm); 
			handler.addTeacherOrUpdate(teacherwiseExemptionForm,request);
			if(request.getAttribute("list")!=null && request.getAttribute("addMoreList")!=null &&
					request.getAttribute("list").equals("success") && request.getAttribute("addMoreList").equals("success")){
				teacherwiseExemptionForm.setList(null);
				teacherwiseExemptionForm.setAddMorelist(null);
				teacherwiseExemptionForm.setAddMoreFlag(false);
				handler.getTeachersList(teacherwiseExemptionForm);
				ActionMessage message = new ActionMessage("knowledgepro.exam.allotment.addandupdate.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else if(request.getAttribute("list")!=null && request.getAttribute("addMoreList")!=null &&
					request.getAttribute("list").equals("fail") && request.getAttribute("addMoreList").equals("fail")){
				errors.add("error", new ActionError("knowledgepro.exam.allotment.addandupdate.fail"));
				saveErrors(request, errors);
			}else if(request.getAttribute("list")!=null && request.getAttribute("list").equals("success")){
				teacherwiseExemptionForm.setList(null);
				handler.getTeachersList(teacherwiseExemptionForm);
				// success deleted
				ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Teacher Exemption");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else if(request.getAttribute("list")!=null && request.getAttribute("list").equals("fail")){
				errors.add("error", new ActionError("knowledgepro.exam.revaluation.marks.update.failure","Teacher Exemption"));
				saveErrors(request, errors);
			}else if(request.getAttribute("addMoreList")!=null && request.getAttribute("addMoreList").equals("success")){
				teacherwiseExemptionForm.setList(null);
				teacherwiseExemptionForm.setAddMorelist(null);
				teacherwiseExemptionForm.setAddMoreFlag(false);
				handler.getTeachersList(teacherwiseExemptionForm);
				ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess","Teacher Exemption");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else if(request.getAttribute("addMoreList")!=null && request.getAttribute("addMoreList").equals("fail")){
				errors.add("error", new ActionError("knowledgepro.exam.update.process.failure","Teacher Exemption adding"));
				saveErrors(request, errors);
			}else{
				errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.duty.update.add.no.records"));
				saveErrors(request, errors);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if(request.getAttribute("date")!=null && request.getAttribute("date").equals("date")){
				errors.add("error", new ActionError("admissionFormForm.applicationDate.required",request.getAttribute("date"),request.getAttribute("session")));
				saveErrors(request, errors);
				return mapping.findForward("teachersExemptionList");
			}
			if(request.getAttribute("session")!=null && request.getAttribute("session").equals("session")){
				errors.add("error", new ActionError("knowledge.inv.datewise.session.required",request.getAttribute("date"),request.getAttribute("session")));
				saveErrors(request, errors);
				return mapping.findForward("teachersExemptionList");
			}
			if(request.getAttribute("duplicate")!=null && request.getAttribute("duplicate").equals("duplicate")){
				errors.add("error", new ActionError("knowledgepro.exam.allotment.exemption.teacher.duplicate",request.getAttribute("date"),request.getAttribute("session")));
				saveErrors(request, errors);
				return mapping.findForward("teachersExemptionList");
			}
			if(request.getAttribute("list")!=null && request.getAttribute("list").equals("list")){
				errors.add("error", new ActionError("knowledgepro.exam.allotment.exemption.teacher.duplicate",request.getAttribute("date"),request.getAttribute("session")));
				saveErrors(request, errors);
				return mapping.findForward("teachersExemptionList");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				teacherwiseExemptionForm.setErrorMessage(msg);
				teacherwiseExemptionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("teachersExemptionList");
	}
}
