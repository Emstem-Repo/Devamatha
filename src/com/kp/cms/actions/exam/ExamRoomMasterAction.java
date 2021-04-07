package com.kp.cms.actions.exam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamRoomMasterForm;
import com.kp.cms.handlers.exam.ExamRoomMasterHandler;
import com.kp.cms.handlers.exam.ExamRoomTypeHandler;

public class ExamRoomMasterAction extends BaseDispatchAction {
	ExamRoomMasterHandler objERMHandler = new ExamRoomMasterHandler();
	ActionErrors errors = new ActionErrors();
	ExamRoomTypeHandler objERTHandler = new ExamRoomTypeHandler();
	String mode = "";

	// gets initial list of Room master
	public ActionForward initExamRoomMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRoomMasterForm objERMForm = (ExamRoomMasterForm) form;
		errors = objERMForm.validate(mapping, request);
		setUserId(request, objERMForm);
		objERMForm.clearPage(mapping, request);
		setRequestToList(objERMForm, request);

		return mapping.findForward(CMSConstants.EXAM_ROOM_MASTER);
	}

	public ActionForward addExamRoomMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRoomMasterForm objForm = (ExamRoomMasterForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = objForm.validate(mapping, request);
		errors = getValidation(objForm, request);
		try {
			setUserId(request, objForm);

			if (errors.isEmpty()) {
				objERMHandler.add(objForm, true);

				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.roomMaster.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage(mapping, request);
			}
		} catch (DuplicateException e1) {
			errors.clear();
			errors.add("error", new ActionError(
					"knowledgepro.exam.roomMaster.addexist"));
			saveErrors(request, errors);
			objForm.clearPage(mapping, request);
		} catch (ReActivateException e1) {
			errors.clear();
			errors.add("error", new ActionError(
					"knowledgepro.exam.roomMaster.reactivate", e1.getID()));
			saveErrors(request, errors);
			objForm.clearPage(mapping, request);
		} catch (Exception e) {
			errors.clear();
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);

			objForm.clearPage(mapping, request);
		} finally {
			setRequestToList(objForm, request);

		}

		return mapping.findForward(CMSConstants.EXAM_ROOM_MASTER);
	}

	private ExamRoomMasterForm setRequestToList(ExamRoomMasterForm objERMForm,
			HttpServletRequest request) throws Exception {
		objERMForm.setRoomTypeList(objERTHandler.getERTList());
		request.setAttribute("roomTypeList", objERMForm.getRoomTypeList());

		// Get Room Master details to display in table form.
		objERMForm.setRoomMasterList(objERMHandler.getERMList());

		return objERMForm;
	}

	public ActionForward editExamRoomMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRoomMasterForm objERMForm = (ExamRoomMasterForm) form;
		String mode = "Edit";
		objERMForm = objERMHandler.getUpdatableForm(objERMForm, mode);
		setRequestToList(objERMForm, request);
		request.setAttribute("examRoomMasterOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_ROOM_MASTER);
	}

	public ActionForward updateExamRoomMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ExamRoomMasterForm objForm = (ExamRoomMasterForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = getValidation(objForm, request);
		//boolean isUpdate = false;
		try {
			setUserId(request, objForm);
			if (isCancelled(request)) {
				mode = "Edit";
				objForm = (ExamRoomMasterForm) objERMHandler.getUpdatableForm(
						objForm, mode);

				request.setAttribute("Update", "Update");
				return mapping.findForward(CMSConstants.EXAM_ROOM_MASTER);
			}

			if (errors.isEmpty()) {
				objERMHandler.update(objForm, false);
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.roomMaster.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage(mapping, request);
			}
		} catch (DuplicateException e1) {
			errors.clear();
			errors.add("error", new ActionError(
					"knowledgepro.exam.roomMaster.addexist"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors.clear();
			errors.add("error", new ActionError(
					"knowledgepro.exam.roomMaster.reactivate", e1.getID()));
			saveErrors(request, errors);
			objForm.clearPage(mapping, request);
		} finally {
			setRequestToList(objForm, request);
		}
		if (!errors.isEmpty()) {

			saveErrors(request, errors);
			request.setAttribute("examRoomMasterOperation", "edit");
			request.setAttribute("Update", "Update");
		}

		return mapping.findForward(CMSConstants.EXAM_ROOM_MASTER);
	}

	public ActionForward deleteExamRoomMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ExamRoomMasterForm objForm = (ExamRoomMasterForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		String mode = "Delete";
		boolean isDelete = false;
		try {
			setUserId(request, objForm);

			objERMHandler.delete(objForm.getId(), objForm.getUserId());
			if (isDelete) {

				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.roomMaster.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage(mapping, request);
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.exam.roomMaster.deletefail"));
				saveErrors(request, errors);
				objForm.clearPage(mapping, request);
			}

		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				objForm.clearPage(mapping, request);

			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				objForm.clearPage(mapping, request);

			} else {
				throw e;
			}

		} finally {
			setRequestToList(objForm, request);
		}
		return mapping.findForward(CMSConstants.EXAM_ROOM_MASTER);

	}

	public ActionForward reactivateExamRoomMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ExamRoomMasterForm objForm = (ExamRoomMasterForm) form;
		ActionMessages messages = new ActionMessages();
		//String mode = "reactivate";
		//boolean isDelete = false;
		try {
			setUserId(request, objForm);

			objERMHandler.reactivation(objForm.getId(), objForm.getUserId());

		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				objForm.clearPage(mapping, request);

			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				objForm.clearPage(mapping, request);

			} else {
				throw e;
			}

		} finally {
			setRequestToList(objForm, request);
		}
		return mapping.findForward(CMSConstants.EXAM_ROOM_MASTER);

	}

	private boolean splCharValidate(String name, String splChar) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9" + splChar + "]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}

	private ActionErrors getValidation(ExamRoomMasterForm objERMForm,
			HttpServletRequest request) {
		errors.clear();
		if (objERMForm.getRoomTypeId() < 0) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.roomMaster.roomTypeSelect"));
		}
		if (objERMForm.getRoomNo().trim().length() == 0) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomMaster.roomNoRequired"));
		}
		if (objERMForm.getRoomCapacity() == 0) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomMaster.roomCapacityRequired"));
		}
		if (objERMForm.getRoomCapacityForExams() == 0) {
			errors
					.add(
							CMSConstants.ERROR,
							new ActionError(
									"knowledgepro.exam.roomMaster.roomCapacityForExamsRequired"));
		}
		if (objERMForm.getRoomCapacityForInternalExams() == 0) {
			errors
					.add(
							CMSConstants.ERROR,
							new ActionError(
									"knowledgepro.exam.roomMaster.roomCapacityForIntExamsRequired"));
		}
		
		
		if (objERMForm.getRoomCapacity() < objERMForm.getRoomCapacityForExams()) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomMaster.roomCapLessThanExamCap",
					"Room Capacity For Exams"));

		}
		if (objERMForm.getRoomCapacity() < objERMForm
				.getRoomCapacityForInternalExams()) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomMaster.roomCapLessThanExamCap",
					"Room Capacity For Internal Exams"));

		}
		if (objERMForm.getFloorNo().trim().length() == 0) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomMaster.floorNoRequired"));
		}
		if (objERMForm.getComments().trim() != null
				&& objERMForm.getComments().length() > 255) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomMaster.commentsOverLimit"));

		}
		if (splCharValidate(objERMForm.getRoomNo().trim(), "")) {
			errors.add("error",
					new ActionError(
							"knowledgepro.exam.roomMaster.splCharNotAllowed",
							"Room No"));
		}
		if (splCharValidate(objERMForm.getFloorNo().trim(), "")) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.roomMaster.splCharNotAllowed",
					"Floor No"));
		}
		if (objERMForm.getRoomCapacity()< 0) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomMaster.negValue","Room capacity"));
		}
		if (objERMForm.getRoomCapacityForExams()< 0) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomMaster.negValue","Room capacity for exams"));
		}
		if (objERMForm.getRoomCapacityForInternalExams()< 0) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomMaster.negValue","Room capacity for internal exams"));
		}
		if(objERMForm.getBlockNo().trim().length() == 0){
			errors.add(CMSConstants.ERROR, new ActionError(
			"knowledgepro.exam.roomMaster.blockNoRequired"));
}
		
		saveErrors(request, errors);
		return errors;
	}
}
