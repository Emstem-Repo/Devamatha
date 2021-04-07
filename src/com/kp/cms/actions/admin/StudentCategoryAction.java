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
import com.kp.cms.bo.admin.StudentType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.StudentTypeForm;
import com.kp.cms.handlers.admin.StudentTypeHandler;
import com.kp.cms.to.admin.StudentTypeTO;

@SuppressWarnings("deprecation")
public class StudentCategoryAction extends BaseDispatchAction {

	private static Log log = LogFactory.getLog(StudentCategoryAction.class);

	public ActionForward initStudentGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentTypeForm typeform = (StudentTypeForm) form;
		log.debug("Entering initStudentGroup ");
		try {
			List<StudentTypeTO> studentTypeList = StudentTypeHandler
					.getInstance().getStudentType();
			log.debug("Leaving initStudentGroup ");
			typeform.setStudentTypeList(studentTypeList);
		} catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				typeform.setErrorMessage(msg);
				typeform.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("studentCategoryEntry");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addStudentType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentTypeForm studentTypeForm = (StudentTypeForm) form;
		 ActionErrors errors = studentTypeForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		boolean isStudentTypeAdded = false;
		try {
			if (studentTypeForm.getTypeDesc() != null) {
				if (studentTypeForm.getTypeDesc().length() > 100) {
					errors.add("error", new ActionError(
							"knowledgepro.admin.studenttype.descriptionfail"));
					saveErrors(request, errors);
				}
			}

			if (errors.isEmpty()) {
				String studentTypeName = studentTypeForm.getTypeName();
				String studentTypeDesc = studentTypeForm.getTypeDesc();
				StudentType studentType = StudentTypeHandler.getInstance()
						.existanceCheck(studentTypeName);
				if (studentType != null && studentType.getIsActive() != false) {
					errors.add("error", new ActionError(
							"knowledgepro.admin.alreadyexists"));
					saveErrors(request, errors);
				} else if (studentType != null
						&& studentType.getIsActive() == false) {
					errors.add("error", new ActionError(
							"knowledgepro.admin.studenttype.reactivate"));
					saveErrors(request, errors);
				} else {
					isStudentTypeAdded = StudentTypeHandler.getInstance()
							.addStudentType(studentTypeName, studentTypeDesc);
					if (isStudentTypeAdded) {
						ActionMessage message = new ActionMessage(
								"knowledgepro.admin.studenttype.addsuccess",
								studentTypeForm.getTypeName());
						messages.add("messages", message);
						saveMessages(request, messages);
						studentTypeForm.reset(mapping, request);
					} else {
						errors.add("error", new ActionError(
								"knowledgepro.admin.studenttype.addfailure",
								studentTypeForm.getTypeName()));
					}
				}
			} else {
				saveErrors(request, errors);
			}

			List<StudentTypeTO> studentTypeList = StudentTypeHandler
					.getInstance().getStudentType();
			studentTypeForm.setStudentTypeList(studentTypeList);
		} catch (Exception e) {
			log.error("error in adding religion to page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentTypeForm.setErrorMessage(msg);
				studentTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("studentCategoryEntry");
	}

	public ActionForward editStudentType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentTypeForm studentTypeForm = (StudentTypeForm) form;
		 ActionErrors errors = studentTypeForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		boolean isStudentTypeEdited = false;
		try {
			if (studentTypeForm.getTypeDesc() != null) {
				if (studentTypeForm.getTypeDesc().length() > 100) {
					errors.add("error", new ActionError(
							"knowledgepro.admin.studenttype.descriptionfail"));
					saveErrors(request, errors);
				}
			}
			if (errors.isEmpty()) {
				// boolean isExisting = false;
				String typename = studentTypeForm.getTypeName();
				String editedname = studentTypeForm.getEditedName();
				StudentType studentType = null;
				if (!(typename.equalsIgnoreCase(editedname))) {
					studentType = StudentTypeHandler.getInstance()
							.existanceCheck(studentTypeForm.getTypeName());
				}
				if (studentType != null && studentType.getIsActive() != false) {
					errors.add("error", new ActionError(
							"knowledgepro.admin.alreadyexists"));
				} else if ((studentType != null)
						&& (studentType.getIsActive() == false)) {
					errors.add("error", new ActionError(
							"knowledgepro.admin.studenttype.reactivate"));
					saveErrors(request, errors);
				} else {
					int id = Integer.parseInt(studentTypeForm
							.getStudentTypeId());
					String name = studentTypeForm.getTypeName();
					String desc = studentTypeForm.getTypeDesc();
					isStudentTypeEdited = StudentTypeHandler.getInstance()
							.editStudentType(id, name, desc);
					if (isStudentTypeEdited) {
						ActionMessage message = new ActionMessage(
								"knowledgepro.admin.studenttype.updatesuccess",
								studentTypeForm.getTypeName());
						messages.add("messages", message);
						saveMessages(request, messages);
						studentTypeForm.reset(mapping, request);
					} else {
						errors.add("error", new ActionError(
								"knowledgepro.admin.studenttype.updatefailure",
								studentTypeForm.getTypeName()));
					}
				}
			}
			List<StudentTypeTO> studentTypeList = StudentTypeHandler
					.getInstance().getStudentType();
			studentTypeForm.setStudentTypeList(studentTypeList);

		} catch (Exception e) {
			log.error("error in adding religion to page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentTypeForm.setErrorMessage(msg);
				studentTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (!errors.isEmpty()) {
			request.setAttribute("Update", "Update");
			saveErrors(request, errors);
		}
		return mapping.findForward("studentCategoryEntry");
	}

	public ActionForward deleteStudentType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentTypeForm studentTypeForm = (StudentTypeForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = studentTypeForm.validate(mapping, request);
		int id = Integer.parseInt(studentTypeForm.getStudentTypeId());
		String name = studentTypeForm.getTypeName();
		String desc = studentTypeForm.getTypeDesc();
		boolean isDeleted = StudentTypeHandler.getInstance().deleteProgramType(
				id, name, desc);
		try {
			List<StudentTypeTO> studentTypeList = StudentTypeHandler
					.getInstance().getStudentType();
			studentTypeForm.setStudentTypeList(studentTypeList);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.admin.studenttype.failuresuccess",
						studentTypeForm.getTypeName());
				messages.add("messages", message);
				saveMessages(request, messages);
				studentTypeForm.reset(mapping, request);
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.admin.studenttype.failurefailure",
						studentTypeForm.getTypeName()));
			}
			studentTypeForm.reset(mapping, request);
		} catch (Exception e) {
			log.error("error in adding religion to page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentTypeForm.setErrorMessage(msg);
				studentTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("studentCategoryEntry");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward reActivateStudentType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentTypeForm studentTypeForm = (StudentTypeForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = studentTypeForm.validate(mapping, request);
		String studentCategory = studentTypeForm.getTypeName();
		try {
			boolean isActivated = StudentTypeHandler.getInstance()
					.reActivateStudentType(studentCategory);
			List<StudentTypeTO> studentTypeList = StudentTypeHandler
					.getInstance().getStudentType();
			studentTypeForm.setStudentTypeList(studentTypeList);
			if (isActivated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.admin.studenttype.reactivate.success",
						studentTypeForm.getTypeName());
				messages.add("messages", message);
				saveMessages(request, messages);
				studentTypeForm.reset(mapping, request);
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.admin.studenttype.reactivate.failed",
						studentTypeForm.getTypeName()));
			}
			studentTypeForm.reset(mapping, request);
		} catch (Exception e) {
			log.error("error in adding religion to page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentTypeForm.setErrorMessage(msg);
				studentTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("studentCategoryEntry");
	}

}
