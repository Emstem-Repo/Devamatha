package com.kp.cms.actions.examallotment;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentPoolHandler;
import com.kp.cms.handlers.hostel.HolidaysHandler;
import com.kp.cms.to.examallotment.ExamRoomAllotmentPoolTo;

public class ExamRoomAllotmentPoolAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentPoolAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamPoolAllotmentSingleField(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentPoolForm allotmentPoolForm =(ExamRoomAllotmentPoolForm) form;
		allotmentPoolForm.resetFields();
		setDataToForm(allotmentPoolForm);
		log.info("Entered initExamPoolAllotmentSingleField input");
		return mapping.findForward(CMSConstants.INIT_SINGLE_FIELD_POOL_CREATION);
	}
	
	
	public void setDataToForm(ExamRoomAllotmentPoolForm allotmentPoolForm)throws Exception{
		List<ExamRoomAllotmentPoolTo> allotmentPoolToList = ExamRoomAllotmentPoolHandler.getInstance().getPoolDetails(allotmentPoolForm);
		allotmentPoolForm.setAllotmentPoolToList(allotmentPoolToList);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addExamPoolName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentPoolForm allotmentPoolForm=(ExamRoomAllotmentPoolForm) form;
		setUserId(request, allotmentPoolForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		String mode = "Add";
		log.info("Entered addExamPoolName input");
		try{
			boolean isAdded = false;
			boolean isDuplicate=ExamRoomAllotmentPoolHandler.getInstance().duplicateCheck(allotmentPoolForm,errors,session);
			if(!isDuplicate){
				isAdded = ExamRoomAllotmentPoolHandler.getInstance().addPoolNameDetails(allotmentPoolForm, mode);
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.pool.addsuccess"));
					saveMessages(request, messages);
					allotmentPoolForm.resetFields();
					log.info("added addExamPoolName method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.pool.addfailure"));
					addErrors(request, errors);
					allotmentPoolForm.resetFields();
				}
			}else{
				addErrors(request, errors);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				allotmentPoolForm.setErrorMessage(msg);
				allotmentPoolForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setDataToForm(allotmentPoolForm);
		return mapping.findForward(CMSConstants.INIT_SINGLE_FIELD_POOL_CREATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivatePoolDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering reactivatePoolDetails ExamRoomAllotmentPoolAction");
		ExamRoomAllotmentPoolForm allotmentPoolForm = (ExamRoomAllotmentPoolForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, allotmentPoolForm);
			boolean isReactivate;
			String duplicateId=session.getAttribute("ReactivateId").toString();
			allotmentPoolForm.setId(Integer.parseInt(duplicateId));
			isReactivate = ExamRoomAllotmentPoolHandler.getInstance().reactivatePoolDetails(allotmentPoolForm);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.allotment.pool.details.activate"));
				allotmentPoolForm.resetFields();
				saveMessages(request, messages);
			}
			else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.allotment.pool.details.activate.fail"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivatePoolDetails of ExamRoomAllotmentPoolAction", e);
			String msg = super.handleApplicationException(e);
			allotmentPoolForm.setErrorMessage(msg);
			allotmentPoolForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setDataToForm(allotmentPoolForm);
		log.info("Leaving into reactivatePoolDetails of ExamRoomAllotmentPoolAction");
		return mapping.findForward(CMSConstants.INIT_SINGLE_FIELD_POOL_CREATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPoolDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentPoolForm allotmentPoolForm = (ExamRoomAllotmentPoolForm) form;
		try {
			ExamRoomAllotmentPoolHandler.getInstance().editPoolDetails(allotmentPoolForm);
			request.setAttribute("operation", "edit");
		} catch (Exception e) {
			log.error("error in editing editPoolDetails...", e);
			String msg = super.handleApplicationException(e);
			allotmentPoolForm.setErrorMessage(msg);
			allotmentPoolForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_SINGLE_FIELD_POOL_CREATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePoolDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatePoolDetails ExamRoomAllotmentPoolAction");
		ExamRoomAllotmentPoolForm allotmentPoolForm = (ExamRoomAllotmentPoolForm) form;
		HttpSession session=request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isUpdated = false;
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				allotmentPoolForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				ExamRoomAllotmentPoolHandler.getInstance().editPoolDetails(allotmentPoolForm);
				request.setAttribute("operation", "edit");
			}else{
				setUserId(request, allotmentPoolForm); // setting user id to update
				boolean isDuplicate=ExamRoomAllotmentPoolHandler.getInstance().duplicateCheck(allotmentPoolForm,errors,session);
				if(!isDuplicate){
					isUpdated = ExamRoomAllotmentPoolHandler.getInstance().addPoolNameDetails(allotmentPoolForm, "Edit");
				if (isUpdated) {
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.allotment.pool.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					allotmentPoolForm.resetFields();
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.pool.update.failed"));
					addErrors(request, errors);
					allotmentPoolForm.resetFields();
				}}else{
					request.setAttribute("operation", "edit");
					addErrors(request, errors);
				}
			}
			
		} catch (Exception e) {
			log.error("Error occured in edit pool Details", e);
			String msg = super.handleApplicationException(e);
			allotmentPoolForm.setErrorMessage(msg);
			allotmentPoolForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
        setDataToForm(allotmentPoolForm);
		log.debug("Exit: action class ExamRoomAllotmentPoolAction edit Pool Details");
		return mapping.findForward(CMSConstants.INIT_SINGLE_FIELD_POOL_CREATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePoolDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering deletePoolDetails method in ExamroomAllotmentAction");
		ExamRoomAllotmentPoolForm allotmentPoolForm = (ExamRoomAllotmentPoolForm)form;
		ActionMessages messages = new ActionMessages();
		try {
			boolean isDeleted = ExamRoomAllotmentPoolHandler.getInstance().deletePoolDetails(allotmentPoolForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.allotment.pool.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.allotment.pool.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			allotmentPoolForm.resetFields();
			setDataToForm(allotmentPoolForm);
		} catch (Exception e) {
			log.error("error In Deletin Pool Details...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				allotmentPoolForm.setErrorMessage(msg);
				allotmentPoolForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				allotmentPoolForm.setErrorMessage(msg);
				allotmentPoolForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete Pool Details ");
		return mapping.findForward(CMSConstants.INIT_SINGLE_FIELD_POOL_CREATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamRoomAllotmentPoolSettings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentPoolForm allotmentPoolForm=(ExamRoomAllotmentPoolForm) form;
		allotmentPoolForm.resetFields();
		setExamRoomAllotmentPoolSettingDataToForm(allotmentPoolForm);
		log.info("Entered initExamPoolAllotmentSingleField input");
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_POOL_SETTING);
	}
	
	/**
	 * @param examRoomAllotmentPoolForm
	 * @throws Exception
	 */
	public void setExamRoomAllotmentPoolSettingDataToForm(ExamRoomAllotmentPoolForm examRoomAllotmentPoolForm) throws Exception{
	       Map<Integer, String> examAllotpoolMap=ExamRoomAllotmentPoolHandler.getInstance().getExamRoomPoolDetails(examRoomAllotmentPoolForm);	
	       examRoomAllotmentPoolForm.setExamAllotRoomPoolMap(examAllotpoolMap);
	       Map<Integer, String> courseMap=HolidaysHandler.getInstance().courseMap();
	       examRoomAllotmentPoolForm.setCourseMap(courseMap);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCourseListByMidOrEndAndSchemeNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 ExamRoomAllotmentPoolForm allotmentPoolForm=(ExamRoomAllotmentPoolForm) form;
		 try{
		 Map<Integer, String> courseMap=ExamRoomAllotmentPoolHandler.getInstance().getCourseDetailsMap(allotmentPoolForm);
		 allotmentPoolForm.setCourseMap(courseMap);
		 } catch (Exception e) {
				log.error("Error occured in edit pool Details", e);
				String msg = super.handleApplicationException(e);
				allotmentPoolForm.setErrorMessage(msg);
				allotmentPoolForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		 return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_POOL_SETTING);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addExamRoomAllotPoolWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentPoolForm allotmentPoolForm=(ExamRoomAllotmentPoolForm) form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, allotmentPoolForm);
		ActionErrors errors=allotmentPoolForm.validate(mapping, request);
		if(errors.isEmpty()){
	    	try{
	    		if(allotmentPoolForm.getSelectedCourses()==null || allotmentPoolForm.getSelectedCourses().isEmpty()){
	    			errors.add("error", new ActionError("knowledgepro.exam.allotment.pool.wise.settings.course.required"));
	    			saveErrors(request, errors);
	    		}else{
	    		boolean isAdded=false;
	    		isAdded=ExamRoomAllotmentPoolHandler.getInstance().addExamRoomAllotMentPoolWise(allotmentPoolForm);
	    		if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.pool.wise.settings.addsuccess"));
					saveMessages(request, messages);
					allotmentPoolForm.resetFields();
					setExamRoomAllotmentPoolSettingDataToForm(allotmentPoolForm);
					log.info("added addExamPoolName method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.pool.wise.settings.addfailure"));
					addErrors(request, errors);
					allotmentPoolForm.resetFields();
					setExamRoomAllotmentPoolSettingDataToForm(allotmentPoolForm);
				}
	    		}
		    	} catch (Exception e) {
				log.error("Error occured in edit pool Details", e);
				String msg = super.handleApplicationException(e);
				allotmentPoolForm.setErrorMessage(msg);
				allotmentPoolForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_POOL_SETTING);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPoolWiseSettingDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        ExamRoomAllotmentPoolForm allotmentPoolForm=(ExamRoomAllotmentPoolForm) form;
        try {
			ExamRoomAllotmentPoolHandler.getInstance().editPoolWiseSettingsDetails(allotmentPoolForm);
			request.setAttribute("operation", "edit");
		} catch (Exception e) {
			log.error("error in editing editPoolDetails...", e);
			String msg = super.handleApplicationException(e);
			allotmentPoolForm.setErrorMessage(msg);
			allotmentPoolForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_POOL_SETTING);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRoomAllotMentPoolWiseDetailsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		   ExamRoomAllotmentPoolForm allotmentPoolForm=(ExamRoomAllotmentPoolForm) form;
		try {
			ExamRoomAllotmentPoolHandler.getInstance().getExamRoomAllotMentPoolWiseList(allotmentPoolForm,allotmentPoolForm.getMidOrEndSem());
		} catch (Exception e) {
			log.error("error in editing editPoolDetails...", e);
			String msg = super.handleApplicationException(e);
			allotmentPoolForm.setErrorMessage(msg);
			allotmentPoolForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_POOL_SETTING);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateExamRoomAllotPoolWiseDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentPoolForm allotmentPoolForm=(ExamRoomAllotmentPoolForm) form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, allotmentPoolForm);
		ActionErrors errors=allotmentPoolForm.validate(mapping, request);
		String mode="Update";
		if(errors.isEmpty()){
	    	try{
	    		boolean isUpdate=false;
	    		isUpdate=ExamRoomAllotmentPoolHandler.getInstance().updateExamRoomAllotMentPoolWise(allotmentPoolForm,mode);
	    		if (isUpdate) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.pool.wise.settings.updated.success"));
					saveMessages(request, messages);
					allotmentPoolForm.resetFields();
					setExamRoomAllotmentPoolSettingDataToForm(allotmentPoolForm);
					log.info("updated updateExamRoomAllotPoolWiseDetails method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.pool.wise.settings.updated.failure"));
					addErrors(request, errors);
					allotmentPoolForm.resetFields();
					setExamRoomAllotmentPoolSettingDataToForm(allotmentPoolForm);
				}
		    	} catch (Exception e) {
				log.error("Error occured in edit pool Details", e);
				String msg = super.handleApplicationException(e);
				allotmentPoolForm.setErrorMessage(msg);
				allotmentPoolForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_POOL_SETTING);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteRoomAllotPoolDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentPoolForm allotmentPoolForm=(ExamRoomAllotmentPoolForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, allotmentPoolForm);
		String mode="Delete";
	    	try{
	    		boolean isDeleted=false;
	    		isDeleted=ExamRoomAllotmentPoolHandler.getInstance().updateExamRoomAllotMentPoolWise(allotmentPoolForm,mode);
	    		if (isDeleted) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.pool.wise.settings.deleted.success"));
					saveMessages(request, messages);
					allotmentPoolForm.resetFields();
					setExamRoomAllotmentPoolSettingDataToForm(allotmentPoolForm);
					log.info("added deleteRoomAllotPoolDetails method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.pool.wise.settings.deleting.failure"));
					addErrors(request, errors);
					allotmentPoolForm.resetFields();
					setExamRoomAllotmentPoolSettingDataToForm(allotmentPoolForm);
				}
		    	} catch (Exception e) {
				log.error("Error occured in edit pool Details", e);
				String msg = super.handleApplicationException(e);
				allotmentPoolForm.setErrorMessage(msg);
				allotmentPoolForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_POOL_SETTING);
	}
}
