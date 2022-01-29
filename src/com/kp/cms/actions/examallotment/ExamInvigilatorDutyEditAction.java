package com.kp.cms.actions.examallotment;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.handlers.examallotment.ExamInvigilatorDutyEditHandler;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyEditTo;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyExemptionTo;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;

public class ExamInvigilatorDutyEditAction extends BaseDispatchAction{
	ExamInvigilatorDutyEditHandler examInvigilatorDutyEditHandler=ExamInvigilatorDutyEditHandler.getInstance();
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamInvigilatorsDutyEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm=(ExamInvigilatorDutyEditForm)form;
		reset(examInvigilatorDutyEditForm);
		setRequiredDataToForm(examInvigilatorDutyEditForm);
		return mapping.findForward("initExamInvigilatorsDutyEdit");
	}
	
	private void setRequiredDataToForm(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		//set exammap,workLocationMap
		examInvigilatorDutyEditHandler.getAllMaps(examInvigilatorDutyEditForm);
		
	}

	private void reset(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		examInvigilatorDutyEditForm.setLocationId(null);
		examInvigilatorDutyEditForm.setExamId(null);
		examInvigilatorDutyEditForm.setExamExaminerType(null);
		examInvigilatorDutyEditForm.setAcademicYear(null);
		examInvigilatorDutyEditForm.setExamRoomNo(null);
		examInvigilatorDutyEditForm.setExamFacultyId(null);
		examInvigilatorDutyEditForm.setExamDate(null);
		examInvigilatorDutyEditForm.setExamSession(null);
		examInvigilatorDutyEditForm.setExamMap(null);
		examInvigilatorDutyEditForm.setWorkLocationMap(null);
		examInvigilatorDutyEditForm.setList(null);
		examInvigilatorDutyEditForm.setAddMorelist(null);
		examInvigilatorDutyEditForm.setAddMoreFlag(false);
		examInvigilatorDutyEditForm.setExamSessionMap(null);
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
	public ActionForward searchInvigilatorsToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm=(ExamInvigilatorDutyEditForm)form;
		examInvigilatorDutyEditForm.setList(null);
		examInvigilatorDutyEditForm.setAddMorelist(null);
		examInvigilatorDutyEditForm.setAddMoreFlag(false);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examInvigilatorDutyEditForm.validate(mapping, request);
		try {
			if(!errors.isEmpty() && errors!=null){
				saveErrors(request, errors);
			}else if((examInvigilatorDutyEditForm.getExamDate()!=null && !examInvigilatorDutyEditForm.getExamDate().isEmpty())
					|| (examInvigilatorDutyEditForm.getExamSession()!=null && !examInvigilatorDutyEditForm.getExamSession().isEmpty())
					|| (examInvigilatorDutyEditForm.getExamFacultyId()!=null && !examInvigilatorDutyEditForm.getExamFacultyId().isEmpty())
					|| (examInvigilatorDutyEditForm.getExamRoomNo()!=null && !examInvigilatorDutyEditForm.getExamRoomNo().isEmpty())
					|| (examInvigilatorDutyEditForm.getExamExaminerType()!=null && !examInvigilatorDutyEditForm.getExamExaminerType().isEmpty())){
				
				examInvigilatorDutyEditHandler.getInvigilatorsListToEdit(examInvigilatorDutyEditForm);
				//setRequiredDataToForm(examInvigilatorDutyEditForm);
				return mapping .findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EDIT_LIST);
			}else{
				errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.exam.select.field"));
				saveErrors(request, errors);
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			examInvigilatorDutyEditForm.setErrorMessage(msg);
			examInvigilatorDutyEditForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		examInvigilatorDutyEditHandler.getFacultyAndRoomNoByCampus(examInvigilatorDutyEditForm);
		return mapping.findForward("initExamInvigilatorsDutyEdit");
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
		ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm=(ExamInvigilatorDutyEditForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			boolean flag=examInvigilatorDutyEditHandler.deleteInvigilators(examInvigilatorDutyEditForm);
			if(flag){
				// success deleted
				ActionMessage message = new ActionMessage("knowledgepro.admin.deletesuccess","Invigilator");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.attn.classteacher.deletefailure"));
				saveErrors(request, errors);
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			examInvigilatorDutyEditForm.setErrorMessage(msg);
			examInvigilatorDutyEditForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EDIT_LIST);
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
		ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm=(ExamInvigilatorDutyEditForm)form;
		try {
		examInvigilatorDutyEditHandler.addMore(examInvigilatorDutyEditForm);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			examInvigilatorDutyEditForm.setErrorMessage(msg);
			examInvigilatorDutyEditForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EDIT_LIST);
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
		ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm=(ExamInvigilatorDutyEditForm)form;
		try {
		examInvigilatorDutyEditHandler.deleteMore(examInvigilatorDutyEditForm);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			examInvigilatorDutyEditForm.setErrorMessage(msg);
			examInvigilatorDutyEditForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EDIT_LIST);
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
	public ActionForward addInvigilatorOrUpdateIsApproved(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm=(ExamInvigilatorDutyEditForm)form;
		examInvigilatorDutyEditForm.setFocus(null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			setUserId(request, examInvigilatorDutyEditForm);
			examInvigilatorDutyEditHandler.addInvigilatorOrUpdateIsApproved(examInvigilatorDutyEditForm,request);
			if(request.getAttribute("list")!=null && request.getAttribute("addMoreList")!=null &&
					request.getAttribute("list").equals("success") && request.getAttribute("addMoreList").equals("success")){
				examInvigilatorDutyEditForm.setList(null);
				examInvigilatorDutyEditForm.setAddMorelist(null);
				examInvigilatorDutyEditForm.setAddMoreFlag(false);
				examInvigilatorDutyEditHandler.getInvigilatorsListToEdit(examInvigilatorDutyEditForm);
				ActionMessage message = new ActionMessage("knowledgepro.exam.allotment.invigilator.duty.edit.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else if(request.getAttribute("list")!=null && request.getAttribute("addMoreList")!=null &&
					request.getAttribute("list").equals("fail") && request.getAttribute("addMoreList").equals("fail")){
				errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.duty.edit.fail"));
				saveErrors(request, errors);
			}else if(request.getAttribute("list")!=null && request.getAttribute("list").equals("success")){
				examInvigilatorDutyEditForm.setList(null);
				examInvigilatorDutyEditHandler.getInvigilatorsListToEdit(examInvigilatorDutyEditForm);
				// success deleted
				ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Invigilator");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else if(request.getAttribute("list")!=null && request.getAttribute("list").equals("fail")){
				errors.add("error", new ActionError("knowledgepro.exam.revaluation.marks.update.failure","Invigilator"));
				saveErrors(request, errors);
			}else if(request.getAttribute("list")!=null && request.getAttribute("addMoreList")!=null && request.getAttribute("list").equals("success")){
				examInvigilatorDutyEditForm.setList(null);
				examInvigilatorDutyEditForm.setAddMorelist(null);
				examInvigilatorDutyEditForm.setAddMoreFlag(false);
				examInvigilatorDutyEditHandler.getInvigilatorsListToEdit(examInvigilatorDutyEditForm);
				ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess","Invigilator");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else if(request.getAttribute("list")!=null && request.getAttribute("addMoreList")!=null && request.getAttribute("list").equals("fail")){
				errors.add("error", new ActionError("knowledgepro.exam.update.process.failure","Invigilator adding"));
				saveErrors(request, errors);
			}else if(request.getAttribute("list")==null && request.getAttribute("addMoreList")!=null &&
					 request.getAttribute("addMoreList").equals("success")){
				examInvigilatorDutyEditForm.setList(null);
				examInvigilatorDutyEditForm.setAddMorelist(null);
				examInvigilatorDutyEditForm.setAddMoreFlag(false);
				examInvigilatorDutyEditHandler.getInvigilatorsListToEdit(examInvigilatorDutyEditForm);
				ActionMessage message = new ActionMessage("knowledgepro.exam.allotment.invigilator.duty.edit.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else if(request.getAttribute("list")==null && request.getAttribute("addMoreList")!=null &&
					 request.getAttribute("addMoreList").equals("fail")){
				errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.duty.edit.fail"));
				saveErrors(request, errors);
			}else{
				errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.duty.update.add.no.records"));
				saveErrors(request, errors);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if(request.getAttribute("all")!=null && request.getAttribute("all").equals("all")){
				errors.add("error", new ActionError("errors.required",request.getAttribute("field")));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EDIT_LIST);
			}
			if(request.getAttribute("list")!=null && request.getAttribute("list").equals("list")){
				errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.duty.duplicate",request.getAttribute("date"),request.getAttribute("session")));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EDIT_LIST);
			}
			if(request.getAttribute("list1")!=null && request.getAttribute("list1").equals("list1")){
				errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.duty.duplicate1",request.getAttribute("date"),request.getAttribute("session")));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EDIT_LIST);
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				examInvigilatorDutyEditForm.setErrorMessage(msg);
				examInvigilatorDutyEditForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EDIT_LIST);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamInvigilatorsDutyToApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm=(ExamInvigilatorDutyEditForm)form;
		reset(examInvigilatorDutyEditForm);
		setRequiredDataToForm(examInvigilatorDutyEditForm);
		return mapping.findForward("initExamInvigilatorsDutyToApprove");
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
	public ActionForward approveTheInvigilatorsDuty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm=(ExamInvigilatorDutyEditForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examInvigilatorDutyEditForm.validate(mapping, request);
		try {
			if(errors.isEmpty()){
				setUserId(request, examInvigilatorDutyEditForm);
				boolean flag=examInvigilatorDutyEditHandler.approveTheInvigilatorsDuty(examInvigilatorDutyEditForm,request);
				if(flag){
					reset(examInvigilatorDutyEditForm);
					setRequiredDataToForm(examInvigilatorDutyEditForm);
					ActionMessage message = new ActionMessage("knowledgepro.exam.allotment.invigilator.duty.update.approved.success");
					messages.add("messages", message);
					saveMessages(request, messages);
				}else{
					errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.duty.update.approved.fail"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if(request.getAttribute("list")!=null && request.getAttribute("list").equals("list")){
				errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.duty.update.approved.norecords"));
				saveErrors(request, errors);
				return mapping.findForward("initExamInvigilatorsDutyToApprove");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				examInvigilatorDutyEditForm.setErrorMessage(msg);
				examInvigilatorDutyEditForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initExamInvigilatorsDutyToApprove");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward examInvigilatorsDutyEditFirstPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm=(ExamInvigilatorDutyEditForm)form;
		setRequiredDataToForm(examInvigilatorDutyEditForm);
		Map<Integer,String> facultyMap=CommonAjaxImpl.getInstance().getFacultyByCampus(examInvigilatorDutyEditForm.getLocationId());
		examInvigilatorDutyEditForm.setFacultyMap(facultyMap);
		Map<Integer,String> roomNoMap=CommonAjaxImpl.getInstance().getRoomNosByCampus(examInvigilatorDutyEditForm.getLocationId());
		examInvigilatorDutyEditForm.setRoomNoMap(roomNoMap);
		examInvigilatorDutyEditForm.setExamRoomNo(null);
		examInvigilatorDutyEditForm.setExamFacultyId(null);
		examInvigilatorDutyEditForm.setExamDate(null);
		examInvigilatorDutyEditForm.setExamSession(null);
		examInvigilatorDutyEditForm.setExamExaminerType(null);
		return mapping.findForward("initExamInvigilatorsDutyEdit");
	}
}
