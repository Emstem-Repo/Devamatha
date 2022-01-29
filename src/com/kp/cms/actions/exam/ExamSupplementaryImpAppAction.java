package com.kp.cms.actions.exam;

import java.util.List;
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamSupplementaryImpAppForm;
import com.kp.cms.handlers.exam.ExamSupplementaryImpAppHandler;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationSubjectTO;

@SuppressWarnings("deprecation")
public class ExamSupplementaryImpAppAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamSupplementaryImpAppHandler handler = new ExamSupplementaryImpAppHandler();

	public ActionForward initSupplementaryImpApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSupplementaryImpAppForm objform = (ExamSupplementaryImpAppForm) form;
		objform.setAddOrEdit("");
		objform.setSupplementaryImprovement(null);
		objform.setExamNameId(null);
		objform.setRegNo(null);
		objform.setRollNo(null);
		objform.setListExamName(handler.getExamNameList_SI());
		objform.clearPage();
		return mapping
				.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION);
	}
	 public ActionForward cancel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	    	
	    	return mapping.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION_SEARCH);
	    }
	public ActionForward Search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSupplementaryImpAppForm objform = (ExamSupplementaryImpAppForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		objform.setAddOrEdit("");
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			try {

				objform = handler.getStudentNameList(objform);
				if (objform.getListStudentName() != null
						&& objform.getListStudentName().size() > 0) {
					return mapping
							.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION_SEARCH);
				} else {
					objform.setListExamName(handler.getExamNameList_SI());
					ActionMessage message = new ActionMessage(
							"knowledgepro.norecords");
					messages.add("messages", message);
					saveMessages(request, messages);
					return initSupplementaryImpApplication(mapping, objform,
							request, response);

				}
			} catch (BusinessException e) {
				errors
						.add(
								"errors",
								new ActionError(
										"knowledgepro.exam.ExamMarksEntry.Students.Inconsistent"));
				saveErrors(request, errors);
				objform.setListExamName(handler.getExamNameList_SI());
				return mapping
						.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION);
			}
		}
		if (objform.getExamNameId().isEmpty()) {
			objform.setCourseList(null);
		}
		if (objform.getCourseId().isEmpty()) {
			objform.setSchemeNameList(null);
		}
		request.setAttribute("retain", "yes");
		objform = retainVals(objform);

		return mapping
				.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION);
	}

	public ActionForward deleteExamSupplementaryImpApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSupplementaryImpAppForm objform = (ExamSupplementaryImpAppForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String studentId = request.getParameter("studentId");

		int schemeNo = (objform.getSchemeNo() != null
				&& objform.getSchemeNo().trim().length() > 0 ? Integer
				.parseInt(objform.getSchemeNo()) : 0);

		handler.delete(Integer.parseInt(studentId), schemeNo);
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.supplementaryApplication.deletesuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.clearPage();
		return mapping
				.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION);
	}

	public ActionForward Add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSupplementaryImpAppForm objform = (ExamSupplementaryImpAppForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validate_Add(objform);
		saveErrors(request, errors);
		objform.setAddOrEdit("add");		
		if (errors.isEmpty()) {
			try {
				objform = handler.getlistSubjects(objform);

				if (objform.getListSubject() != null
						&& objform.getListSubject().size() > 0) {
					return mapping
							.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION_ADD);
				} else {
					ActionMessage message = new ActionMessage(
							"knowledgepro.norecords");
					messages.add("messages", message);
					saveMessages(request, messages);

				}
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.sup.details.already.exists"));
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION);
			} catch (BusinessException e) {
				errors
						.add(
								"errors",
								new ActionError(
										"knowledgepro.exam.ExamMarksEntry.Students.Inconsistent"));
				saveErrors(request, errors);
				objform.setListExamName(handler.getExamNameList_SI());
				return mapping
						.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION);
			}
		}
		// objform.setListExamName(handler.getExamNameList_SI());
		return initSupplementaryImpApplication(mapping, objform, request,
				response);

	}

	// Add Supplementary Imp Application
	public ActionForward addExamSupplementaryImpApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSupplementaryImpAppForm objform = (ExamSupplementaryImpAppForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);

		if (errors.isEmpty()) {

			List<ExamSupplementaryImpApplicationSubjectTO> listSubject = objform
					.getListSubject();
			handler.add(listSubject, (objform.getExamNameId() != null
					&& objform.getExamNameId().trim().length() > 0 ? Integer
					.parseInt(objform.getExamNameId()) : 0), objform
					.getUserId(), (null == objform.getStudentId() ? 0 : Integer
					.parseInt(objform.getStudentId())), objform
					.getSupplementaryImprovement(), (null == objform
					.getSchemeNo() ? 0 : Integer
					.parseInt(objform.getSchemeNo())));
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.addsuccess",
					"Supplementary Improvement Application");
			messages.add("messages", message);
			saveMessages(request, messages);
			/*
			 * return initSupplementaryImpApplication(mapping, objform, request,
			 * response);
			 */
			if (objform.getAddOrEdit().equalsIgnoreCase("add")) {
				objform.setAddOrEdit("");
				return initSupplementaryImpApplication(mapping, objform,
						request, response);
				
			} else {
				return mapping
						.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION_SEARCH);
			}

		} else {
			return mapping
					.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION_ADD);
		}
	}

	// ===============
	// edit Supplementary Imp Application

	public ActionForward editSupplementaryImpApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSupplementaryImpAppForm objform = (ExamSupplementaryImpAppForm) form;
		errors.clear();
		messages.clear();
		objform = handler.getUpdatableForm(objform);
		return mapping
				.findForward(CMSConstants.EXAM_SUPPLEMENTARY_IMP_APPLICTION_ADD);

	}

	private ActionErrors validate_Add(ExamSupplementaryImpAppForm objform) {

		String regNo = objform.getRegNo().trim();
		String rollNo = objform.getRollNo().trim();
		if ((regNo == null || regNo.trim().equals(""))
				&& (rollNo == null || rollNo.trim().equals(""))) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamInternalMark.regNoOrrollNumber"));
		}
		if (objform.getSchemeNo() == null
				|| objform.getSchemeNo().trim().isEmpty()) {
			errors.add("error", new ActionError(
					"knowledgepro.fee.semister.required"));
		}
		return errors;
	}

	private ExamSupplementaryImpAppForm retainVals(
			ExamSupplementaryImpAppForm objform) throws NumberFormatException,
			BusinessException {
		if (objform.getExamNameId() != null
				&& objform.getExamNameId().length() > 0) {
			objform.setCourseList(handler.getCourseByExamNameRegNoRollNo(
					Integer.parseInt(objform.getExamNameId()), objform
							.getRegNo(), objform.getRollNo()));
		}
		if (objform.getCourseId() != null && objform.getCourseId().length() > 0) {
			objform.setSchemeNameList(handler.getSchemeNoByCourse(objform
					.getCourseId()));
		}
		return objform;
	}

}
