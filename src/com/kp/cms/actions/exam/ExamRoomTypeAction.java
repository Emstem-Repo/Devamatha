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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamRoomTypeForm;
import com.kp.cms.handlers.exam.ExamRoomTypeHandler;

@SuppressWarnings("deprecation")
public class ExamRoomTypeAction extends BaseDispatchAction {
	ExamRoomTypeHandler objERTHandler = new ExamRoomTypeHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	// gets initial list of Room types
	public ActionForward initExamRoomType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRoomTypeForm objform = (ExamRoomTypeForm) form;

		objform.setExamRoomTypeName("");
		objform.setExamRoomTypeDesc("");
		objform.setRoomTypeList(objERTHandler.getERTList());

		return mapping.findForward(CMSConstants.EXAM_ROOM_TYPE);
	}

	// Add assignment type
	public ActionForward addERT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ExamRoomTypeForm objERTForm = (ExamRoomTypeForm) form;
		errors.clear();
		messages.clear();
		errors = objERTForm.validate(mapping, request);
		errors = getValidation(objERTForm.getExamRoomTypeName(), objERTForm
				.getExamRoomTypeDesc(), request);
		setUserId(request, objERTForm);

		saveErrors(request, errors);

		if (errors.isEmpty()) {
			try {
				objERTHandler.addERT(objERTForm.getExamRoomTypeName(),
						objERTForm.getExamRoomTypeDesc(), objERTForm
								.getUserId());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.roomType.addsuccess");

				messages.add("messages", message);
				saveMessages(request, messages);
				objERTForm.setRoomTypeList(objERTHandler.getERTList());
				objERTForm.clearPage(mapping, request);

			} catch (DuplicateException e1) {
				errors.clear();
				errors.add("error", new ActionError(
						"knowledgepro.exam.roomType.exists"));
				saveErrors(request, errors);
			} catch (ReActivateException e1) {

				errors.clear();
				errors.add("error", new ActionError(
						"knowledgepro.exam.roomType.reactivate", 1));
				saveErrors(request, errors);
			} catch (Exception e) {

				errors.add("error", new ActionError(
						"knowledgepro.exam.roomType.addfailure"));
				saveErrors(request, errors);
			}
		}

		return mapping.findForward(CMSConstants.EXAM_ROOM_TYPE);
	}

	// modify assignment type

	public ActionForward updateERT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomTypeForm objERTForm = (ExamRoomTypeForm) form;
		errors.clear();
		errors = objERTForm.validate(mapping, request);
		errors = getValidation(objERTForm.getExamRoomTypeName(), objERTForm
				.getExamRoomTypeDesc(), request);
		setUserId(request, objERTForm);
		String origERTName = request.getParameter("origaTMName");
		String eRTName = request.getParameter("examRoomTypeName");

		if (errors.isEmpty()) {
			try {
				objERTHandler.updateERT(objERTForm.getExamRoomTypeId(),
						eRTName, objERTForm.getExamRoomTypeDesc(), objERTForm
								.getUserId(), origERTName);
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.roomType.modifiedsuccess");
				messages.clear();
				messages.add("messages", message);
				saveMessages(request, messages);
				objERTForm.setRoomTypeList(objERTHandler.getERTList());
				objERTForm.clearPage(mapping, request);
			}

			catch (DuplicateException e1) {
				errors.clear();
				errors.add("error", new ActionError(
						"knowledgepro.exam.roomType.exists"));
				saveErrors(request, errors);
			} catch (ReActivateException e1) {

				errors.clear();
				errors.add("error", new ActionError(
						"knowledgepro.exam.roomType.reactivate", e1.getID()));
				saveErrors(request, errors);
			} catch (Exception e) {

				errors.clear();
				errors.add("error", new ActionError(
						"knowledgepro.exam.roomType.addfailure"));
				saveErrors(request, errors);
			}
		} else {
			request.setAttribute("examRoomTypeOperation", "edit");
			request.setAttribute("Update", "Update");
			objERTForm.setRoomTypeList(objERTHandler.getERTList());

		}
		return mapping.findForward(CMSConstants.EXAM_ROOM_TYPE);
	}

	// delete Room type

	public ActionForward deleteERT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomTypeForm objERTForm = (ExamRoomTypeForm) form;
		errors = objERTForm.validate(mapping, request);
		setUserId(request, objERTForm);
		String ertID = request.getParameter("examRoomTypeId");
		int eRTID = Integer.parseInt(ertID);
		try {
			objERTHandler.deleteERT(eRTID, objERTForm.getUserId());
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.roomType.requried.deleted", "");
			messages.clear();
			messages.add("messages", message);
			saveMessages(request, messages);
		} catch (Exception e) {
			errors.clear();
			errors.add("error", new ActionError(
					"knowledgepro.exam.roomType.deletefailure"));
			saveErrors(request, errors);

		} finally {
			objERTForm.setRoomTypeList(objERTHandler.getERTList());

			objERTForm.clearPage(mapping, request);
		}

		return mapping.findForward(CMSConstants.EXAM_ROOM_TYPE);

	}

	// Reactivate assignment type

	public ActionForward reActivateERT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomTypeForm objERTForm = (ExamRoomTypeForm) form;
		errors = objERTForm.validate(mapping, request);
		setUserId(request, objERTForm);
		try {

			objERTHandler.reActivateERT(objERTForm.getExamRoomTypeName(),
					objERTForm.getUserId());
		} catch (Exception e) {
			errors.clear();

			errors.add("error", new ActionError(
					"knowledgepro.exam.roomType.activatefailure"));
			saveErrors(request, errors);
		} finally {
			objERTForm.setRoomTypeList(objERTHandler.getERTList());
		}
		errors.clear();

		errors.add("error", new ActionError(
				"knowledgepro.exam.roomtype.activatesuccess"));
		saveErrors(request, errors);
		objERTForm.clearPage(mapping, request);

		return mapping.findForward(CMSConstants.EXAM_ROOM_TYPE);

	}

	private ActionErrors getValidation(String roomType, String roomDesc,
			HttpServletRequest request) {
		if (roomType.trim().length() == 0) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomType.requried.req"));
		}
		if (splCharValidate(roomType.trim(), "\\& \\-")) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.roomType.splCharNotAllowed"));
		}
		if (roomDesc.trim() != null && roomDesc.trim().length() > 255) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.exam.roomType.desc.overlimit"));

		}
		saveErrors(request, errors);
		return errors;
	}

	private boolean splCharValidate(String name, String splChar) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9" + splChar + "]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}

}
