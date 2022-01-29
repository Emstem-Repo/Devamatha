package com.kp.cms.actions.admin;

import java.util.Map;
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
import com.kp.cms.forms.admin.StudentSupportRequestForm;
import com.kp.cms.handlers.admin.StudentSupportRequestHandler;
import com.kp.cms.utilities.CommonUtil;

public class StudentSupportRequestAction extends BaseDispatchAction{
	StudentSupportRequestHandler handler=StudentSupportRequestHandler.getInstance();
	/**
	 * initial page for student
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentSupportRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		HttpSession session = request.getSession();
		String studentId=String.valueOf(session.getAttribute("studentid"));
		setRequiredData(studentSupportRequestForm,studentId);
		reset(studentSupportRequestForm);
		return mapping.findForward("initStudentSupportRequest");
	}
	private void setRequiredData(
			StudentSupportRequestForm studentSupportRequestForm, String studentId) throws Exception{
		Map<Integer,String> map=handler.getSupportCategory();
		studentSupportRequestForm.setCategoryMap(CommonUtil.sortMapByValue(map));
		handler.getStudentSupportRequest(studentId,studentSupportRequestForm);
	}
	private void reset(StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		studentSupportRequestForm.setCategoryId(null);
		studentSupportRequestForm.setDescription(null);
	}
	/**
	 *  save data in db
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addStudentRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		String studentId=String.valueOf(session.getAttribute("studentid"));
		try{
			if(studentSupportRequestForm.getCategoryId()==null || studentSupportRequestForm.getCategoryId().isEmpty() || studentSupportRequestForm.getCategoryId().equals("")){
				errors.add("error", new ActionError("knowledgepro.admin.FineCategory.required"));
				saveErrors(request, errors);
			}else if(studentSupportRequestForm.getDescription()==null || studentSupportRequestForm.getDescription().isEmpty() || studentSupportRequestForm.getDescription().trim().equals("")){
				errors.add("error", new ActionError("knowledgepro.admin.tc.dec.required"));
				saveErrors(request, errors);
			}else if(studentSupportRequestForm.getDescription()==null || studentSupportRequestForm.getDescription().isEmpty() || studentSupportRequestForm.getDescription().length()>500){
				errors.add("error", new ActionError("knowledgepro.admin.tc.dec.length.more"));
				saveErrors(request, errors);
			}else{
				setUserId(request, studentSupportRequestForm); 
				boolean flag=handler.addStudentRequest(studentSupportRequestForm,studentId,request);
				if(flag){
					Object[] objects=handler.getAdminSupportRequestId(request.getAttribute("id").toString());
					String requestId=null;
					if(objects[1]!=null){
						requestId="S"+objects[0].toString();
					}else{
						requestId="ST"+objects[0].toString();
					}
					if(objects[4]!=null){
						handler.sendMailWhoseMailForCategory(objects[4].toString(),objects[3].toString(),objects[2].toString(),requestId);
					}
					ActionMessage message = new ActionMessage("knowledgepro.admin.support.request.add.success",requestId);
					messages.add("messages", message);
					saveMessages(request, messages);
					reset(studentSupportRequestForm);
				}else{
					errors.add("error", new ActionError("knowledgepro.admin.support.request.add.fail"));
					saveErrors(request, errors);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		handler.getStudentSupportRequest(studentId,studentSupportRequestForm);
		return mapping.findForward("initStudentSupportRequest");
	}
	/**
	 * initial page for Admin
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSupportRequestForAdmin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		reset(studentSupportRequestForm);
		setUserId(request, studentSupportRequestForm);
		setRequiredDataForAdmin(studentSupportRequestForm);
		return mapping.findForward("initAdminSupportRequest");
	}
	
	private void setRequiredDataForAdmin(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		Map<Integer,String> map=handler.getSupportCategoryForAdmin();
		studentSupportRequestForm.setCategoryMap(CommonUtil.sortMapByValue(map));
		handler.getAdminSupportRequest(studentSupportRequestForm);
	}
	/**
	 *  save data in db
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAdminSupportRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{
			if(studentSupportRequestForm.getCategoryId()==null || studentSupportRequestForm.getCategoryId().isEmpty() || studentSupportRequestForm.getCategoryId().equals("")){
				errors.add("error", new ActionError("knowledgepro.admin.FineCategory.required"));
				saveErrors(request, errors);
			}else if(studentSupportRequestForm.getDescription()==null || studentSupportRequestForm.getDescription().isEmpty() || studentSupportRequestForm.getDescription().trim().equals("")){
				errors.add("error", new ActionError("knowledgepro.admin.tc.dec.required"));
				saveErrors(request, errors);
			}else if(studentSupportRequestForm.getDescription()==null || studentSupportRequestForm.getDescription().isEmpty() || studentSupportRequestForm.getDescription().length()>500){
				errors.add("error", new ActionError("knowledgepro.admin.tc.dec.length.more"));
				saveErrors(request, errors);
			}else{
				setUserId(request, studentSupportRequestForm); 
				boolean flag=handler.addAdminSupportRequest(studentSupportRequestForm,request);
				if(flag){
					Object[] objects=handler.getAdminSupportRequestId(request.getAttribute("id").toString());
					String requestId=null;
					if(objects[1]!=null){
						requestId="S"+objects[0].toString();
					}else{
						requestId="ST"+objects[0].toString();
					}
					if(objects[4]!=null){
						handler.sendMailWhoseMailForCategory(objects[4].toString(),objects[3].toString(),objects[2].toString(),requestId);
					}
					ActionMessage message = new ActionMessage("knowledgepro.admin.support.request.add.success",requestId);
					messages.add("messages", message);
					saveMessages(request, messages);
					reset(studentSupportRequestForm);
				}else{
					errors.add("error", new ActionError("knowledgepro.admin.support.request.add.fail"));
					saveErrors(request, errors);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		handler.getAdminSupportRequest(studentSupportRequestForm);
		return mapping.findForward("initAdminSupportRequest");
	}
	/**
	 *  get the pending support requests
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPendingSupportRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		studentSupportRequestForm.setList(null);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{
			setUserId(request, studentSupportRequestForm); 
			boolean flag=handler.getPendingListOfSupportReq(studentSupportRequestForm);
			if(!flag){
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initPendingSupportRequest");
	}
	/**
	 *  update the categoryId
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{
			setUserId(request, studentSupportRequestForm); 
			handler.updateCategory(studentSupportRequestForm);
			clearTheDate(studentSupportRequestForm);
			boolean flag=handler.getPendingListOfSupportReq(studentSupportRequestForm);
			if(!flag){
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initPendingSupportRequest");
	}
	private void clearTheDate(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		studentSupportRequestForm.setList(null);
		studentSupportRequestForm.setId(0);
		studentSupportRequestForm.setCategoryId(null);
		studentSupportRequestForm.setRemarks(null);
	}
	/**
	 *  update the status and remarks
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStatusAndRemarks(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{
			setUserId(request, studentSupportRequestForm); 
			boolean flag1=handler.updateStatusAndRemarks(studentSupportRequestForm);
			if(flag1){
				ActionMessage message = new ActionMessage("knowledgepro.exam.attendanceMarks.updated");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.exam.ExamMarksEntry.Students.Fail"));
				saveErrors(request, errors);
			}
			clearTheDate(studentSupportRequestForm);
			boolean flag=handler.getPendingListOfSupportReq(studentSupportRequestForm);
			if(!flag){
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initPendingSupportRequest");
	}
	/**
	 * admin view to search
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdminView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		studentSupportRequestForm.setNoOfDays("5");
		studentSupportRequestForm.setList(null);
		studentSupportRequestForm.setCategoryId(null);
		studentSupportRequestForm.setStatus(null);
		setRequiredDataForAdminView(studentSupportRequestForm);
		return mapping.findForward("initAdminSupportRequestView");
	}
	
	private void setRequiredDataForAdminView(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		Map<Integer,String> map=handler.getDeptMapForAdmin();
		studentSupportRequestForm.setDeptMap(CommonUtil.sortMapByValue(map));
	}
	/**
	 *  get the pending support requests
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchTheSupportRequestList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		studentSupportRequestForm.setList(null);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{
			setUserId(request, studentSupportRequestForm); 
			if(studentSupportRequestForm.getDeptId()!=null || studentSupportRequestForm.getStatus()!=null || studentSupportRequestForm.getNoOfDays()!=null){
				boolean flag=handler.searchTheSupportRequestList(studentSupportRequestForm);
				if(!flag){
					errors.add("error", new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.select.anyone"));
				saveErrors(request, errors);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initAdminSupportRequestView");
	}
	/**
	 *  update the categoryId by admin
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCategoryByAdmin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		ActionMessages messages = new ActionMessages();
		try{
			setUserId(request, studentSupportRequestForm); 
			handler.updateCategoryByAdmin(studentSupportRequestForm);
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initAdminSupportRequestView");
	}
	/**
	 *  update the status and remarks
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStatusAndRemarksByAdmin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{
			setUserId(request, studentSupportRequestForm); 
			boolean flag=handler.updateStatusAndRemarksByAdmin(studentSupportRequestForm);
			if(flag){
				ActionMessage message = new ActionMessage("knowledgepro.exam.attendanceMarks.updated");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.exam.ExamMarksEntry.Students.Fail"));
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initAdminSupportRequestView");
	}
	/**
	 * get student or user previous support requests
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentOrUserDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		studentSupportRequestForm.setPreviousList(null);
		studentSupportRequestForm.setPreviousName(null);
		studentSupportRequestForm.setClassOrDepartment(null);
		handler.getPreviousSupportRequests(studentSupportRequestForm);
		return mapping.findForward("initPreviousSupportRequests");
	}
	/**
	 * support request for students
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSupportRequestForStudent(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		studentSupportRequestForm.setRegNo(null);
		studentSupportRequestForm.setNameOfStudent(null);
		clearTheFields(studentSupportRequestForm);
		return mapping.findForward("initSupportRequestForStudent");
	}
	/**
	 *  get students support requests
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward supportRequestForStudent(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		clearTheFields(studentSupportRequestForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		studentSupportRequestForm.setNameOfStudent(null);
		try{
			setUserId(request, studentSupportRequestForm); 
			if(studentSupportRequestForm.getRegNo()!=null && !studentSupportRequestForm.getRegNo().isEmpty()){
				handler.supportRequestForStudent(studentSupportRequestForm,request);
			}else{
				errors.add("error", new ActionError("knowledgepro.admission.cancelPromotion.isrequired"));
				saveErrors(request, errors);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if(request.getAttribute("list")!=null && request.getAttribute("list").equals("list")){
				errors.add("error", new ActionError("knowledgepro.support.supportrequest.register.invalid"));
				saveErrors(request, errors);
				return mapping.findForward("initSupportRequestForStudent");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initSupportRequestForStudent");
	}
	private void clearTheFields(
			StudentSupportRequestForm studentSupportRequestForm) throws Exception{
		studentSupportRequestForm.setList(null);
		studentSupportRequestForm.setFlag(null);
		studentSupportRequestForm.setCategoryMap(null);
		studentSupportRequestForm.setDescription(null);
		studentSupportRequestForm.setCategoryId(null);
	}
	/**
	 *  save data in db
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSupportRequestForStudent(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StudentSupportRequestForm studentSupportRequestForm = (StudentSupportRequestForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{
			if(studentSupportRequestForm.getCategoryId()==null || studentSupportRequestForm.getCategoryId().isEmpty() || studentSupportRequestForm.getCategoryId().equals("")){
				errors.add("error", new ActionError("knowledgepro.admin.FineCategory.required"));
				saveErrors(request, errors);
			}else if(studentSupportRequestForm.getDescription()==null || studentSupportRequestForm.getDescription().isEmpty() || studentSupportRequestForm.getDescription().trim().equals("")){
				errors.add("error", new ActionError("knowledgepro.admin.tc.dec.required"));
				saveErrors(request, errors);
			}else if(studentSupportRequestForm.getDescription()==null || studentSupportRequestForm.getDescription().isEmpty() || studentSupportRequestForm.getDescription().length()>500){
				errors.add("error", new ActionError("knowledgepro.admin.tc.dec.length.more"));
				saveErrors(request, errors);
			}else{
				setUserId(request, studentSupportRequestForm); 
				String studentId=String.valueOf(studentSupportRequestForm.getId());
				boolean flag=handler.addStudentRequest(studentSupportRequestForm, studentId, request);
				if(flag){
					Object[] objects=handler.getAdminSupportRequestId(request.getAttribute("id").toString());
					String requestId=null;
					if(objects[1]!=null){
						requestId="S"+objects[0].toString();
					}else{
						requestId="ST"+objects[0].toString();
					}
					if(objects[4]!=null){
						handler.sendMailWhoseMailForCategory(objects[4].toString(),objects[3].toString(),objects[2].toString(),requestId);
					}
					ActionMessage message = new ActionMessage("knowledgepro.admin.support.request.add.success1",requestId);
					messages.add("messages", message);
					saveMessages(request, messages);
					reset(studentSupportRequestForm);
				}else{
					errors.add("error", new ActionError("knowledgepro.admin.support.request.add.fail"));
					saveErrors(request, errors);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSupportRequestForm.setErrorMessage(msg);
				studentSupportRequestForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		handler.supportRequestForStudent(studentSupportRequestForm,request);
		return mapping.findForward("initSupportRequestForStudent");
	}
}
