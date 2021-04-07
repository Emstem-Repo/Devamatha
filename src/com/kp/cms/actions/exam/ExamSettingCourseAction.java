package com.kp.cms.actions.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamSettingCourseForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamSettingCourseHandler;
import com.kp.cms.to.exam.ExamRevaluationTO;

@SuppressWarnings("deprecation")
public class ExamSettingCourseAction extends BaseDispatchAction {
	ExamSettingCourseHandler handler = new ExamSettingCourseHandler();
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = new ActionErrors();

	public ActionForward initExamSettingCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ExamSettingCourseForm objForm = (ExamSettingCourseForm) form;
		errors.clear();
		objForm.clearPage();
		try {
			setRequiredDataToForm(objForm, request);
		} catch (Exception e) {

			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EXAM_SETTING_COURSE);
	}

	private void setRequiredDataToForm(ExamSettingCourseForm objForm,
			HttpServletRequest request) throws Exception {
		objForm.setSettingCourseList(handler.getList());
		objForm.setProgramTypeList(handler.getProgramTypeList());
		objForm.setRevaluationTypeList(handler.getRevaluationTypeList());

	}

	public ActionForward addExamSettingCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSettingCourseForm objForm = (ExamSettingCourseForm) form;

		errors.clear();
		messages.clear();
		errors = objForm.validate(mapping, request);

		saveErrors(request, errors);

		if (errors.isEmpty()) {
			errors = getValidation(objForm, request);
		}
		saveErrors(request, errors);

		setUserId(request, objForm);
		String[] str = request.getParameterValues("selectedCourse");
		String[] str1 = request.getParameterValues("programTypeId");
		String pids = null;
		if (str1 != null && str1.length > 0) {
			for (int x = 0; x < str1.length; x++) {
				pids = str1[x] + ",";
			}
		}

		if (errors.isEmpty()) {
			try {
				List<Integer> listCourses = new ArrayList<Integer>();
				for (int x = 0; x < str.length; x++) {
					listCourses.add(Integer.parseInt(str[x]));

				}

				objForm.setListCourses(listCourses);
				handler.add(objForm.getListCourses(), objForm
						.getIndividualPass(), objForm.getAggregatePass(),
						objForm.getMinReqAttendanceWithFine(), objForm
								.getMinReqAttendanceWithoutFine(), objForm
								.getRevaluationTypeList(), objForm
								.getImprovement(), objForm
								.getSupplementaryForFailedSubject(), objForm
								.getUserId());

				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.settingCourse.addsuccess");

				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage();
				errors.clear();
			} catch (DuplicateException e1) {
				errors.clear();
				errors.add("error", new ActionError(
						"knowledgepro.exam.settingCourse.exists"));
				saveErrors(request, errors);
				objForm.clearPage();

			} catch (ReActivateException e1) {
				errors.clear();
				errors.add("error", new ActionError(
						"knowledgepro.exam.settingCourse.reactivate", e1
								.getID()));
				saveErrors(request, errors);
				objForm.clearPage();
			} catch (Exception e) {
				errors.clear();
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);

			}
			setRequiredDataToForm(objForm, request);
			return mapping.findForward(CMSConstants.EXAM_SETTING_COURSE);
		} else {

			List<ExamRevaluationTO> list = objForm.getRevaluationTypeList();
			List<ExamRevaluationTO> list1 = new ArrayList<ExamRevaluationTO>();
			ExamRevaluationTO rTO;
			for (ExamRevaluationTO to : list) {
				rTO = new ExamRevaluationTO(to.getId(),
						to.getRevaluationType(), to.getOptionValue());
				list1.add(rTO);
			}
			objForm.setRevaluationTypeList(list1);

			if (pids != null && pids.trim().length() > 0) {
				String programIds = pids.substring(0, pids.length() - 1);
				objForm.setCoursesMap(handler
						.getCoursesByProgramTypes(programIds));
			}
			objForm.setSettingCourseList(handler.getList());
			objForm.setProgramTypeList(handler.getProgramTypeList());
			return mapping.findForward(CMSConstants.EXAM_SETTING_COURSE);
		}

	}

	public ActionForward editExamSettingCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSettingCourseForm objForm = (ExamSettingCourseForm) form;

		objForm = handler.getUpdatableForm(objForm);
		request.getSession().removeAttribute("baseActionForm");
		objForm.setSettingCourseList(handler.getList());

		objForm.setCoursesMap(new ExamGenHandler().getProgramCourse(Integer
				.parseInt(objForm.getProgramTypeId())));
		request.setAttribute("ExamSettingCourseOperation", "edit");
		request.setAttribute("Update", "Update");

		request.setAttribute("courseId", objForm.getCourseId());
		return mapping.findForward(CMSConstants.EXAM_SETTING_COURSE);
	}

	public ActionForward updateExamSettingCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ExamSettingCourseForm objForm = (ExamSettingCourseForm) form;

		errors.clear();
		messages.clear();
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = objForm.validate(mapping, request);
		saveErrors(request, errors);

		if (errors.isEmpty()) {
			errors = getValidation(objForm, request);
		}
		saveErrors(request, errors);
		setUserId(request, objForm);
		if (errors.isEmpty()) {
			try {
				handler.update(objForm.getId(), Integer.parseInt(objForm
						.getCourseId()), objForm.getIndividualPass(), objForm
						.getAggregatePass(), objForm
						.getMinReqAttendanceWithFine(), objForm
						.getMinReqAttendanceWithoutFine(), objForm
						.getRevaluationTypeList(), objForm.getImprovement(),
						objForm.getSupplementaryForFailedSubject(), objForm
								.getUserId());

				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.settingCourse.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage();
				errors.clear();
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.settingCourse.exists"));
				saveErrors(request, errors);
				objForm.clearPage();
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.settingCourse.reactivate", e1
								.getID()));
				saveErrors(request, errors);

				objForm.clearPage();
			} catch (BusinessException e) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				objForm.clearPage();
			} catch (ApplicationException e) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
			}
			setRequiredDataToForm(objForm, request);
			return mapping.findForward(CMSConstants.EXAM_SETTING_COURSE);

		} else {

			List<ExamRevaluationTO> list = objForm.getRevaluationTypeList();
			List<ExamRevaluationTO> list1 = new ArrayList<ExamRevaluationTO>();
			ExamRevaluationTO rTO;
			for (ExamRevaluationTO to : list) {
				rTO = new ExamRevaluationTO(to.getId(),
						to.getRevaluationType(), to.getOptionValue());
				list1.add(rTO);
			}
			objForm.setRevaluationTypeList(list1);

			request.setAttribute("ExamSettingCourseOperation", "edit");
			request.setAttribute("Update", "Update");
			objForm.setSettingCourseList(handler.getList());
			objForm.setProgramTypeList(handler.getProgramTypeList());
			return mapping.findForward(CMSConstants.EXAM_SETTING_COURSE);
		}

	}

	public ActionForward deleteExamSettingCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		messages.clear();
		ExamSettingCourseForm objForm = (ExamSettingCourseForm) form;
		try {
			setUserId(request, objForm);
			handler.delete(objForm.getId(), objForm.getUserId());
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.settingCourse.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			objForm.clearPage();
			errors.clear();

		} catch (BusinessException e) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			objForm.clearPage();
		} catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			objForm.clearPage();
		} finally {
			setRequiredDataToForm(objForm, request);
		}
		return mapping.findForward(CMSConstants.EXAM_SETTING_COURSE);

	}

	public ActionForward reactivate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		messages.clear();
		ExamSettingCourseForm objForm = (ExamSettingCourseForm) form;
		try {
			setUserId(request, objForm);
			handler.reactivate(objForm.getId(), objForm.getUserId());
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.settingCourse.activatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			objForm.clearPage();
			errors.clear();

		} catch (BusinessException e) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			objForm.clearPage();
		} catch (ApplicationException e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			objForm.clearPage();
		} finally {
			setRequiredDataToForm(objForm, request);
		}
		return mapping.findForward(CMSConstants.EXAM_SETTING_COURSE);

	}

	private ActionErrors getValidation(ExamSettingCourseForm form,
			HttpServletRequest request) {
		BigDecimal b1, b2;
		double d1, d2;
		boolean flag = false;
		boolean temp = false;
		if ((form.getAggregatePass().length() == 0
				|| form.getAggregatePass().isEmpty() || Double.parseDouble(form
				.getAggregatePass()) == 0))
				 {
			     flag=true;
			     
		}
		
		if(form.getIndividualPass().length() == 0
					|| form.getIndividualPass().isEmpty() || Double
					.parseDouble(form.getIndividualPass()) == 0) {
				
				temp=true;
						
					}
		
		if(flag && temp)
		{
			errors.add("error", new ActionError(
			"knowledgepro.exam.settingCourse.passCriteria.oneOnly"));
			flag = false;
		}

//		if ((form.getAggregatePass().length() > 0 || !form.getAggregatePass()
//				.isEmpty())
//				&& (form.getIndividualPass().length() > 0 || !form
//						.getIndividualPass().isEmpty())) {
//
//			if (Double.parseDouble(form.getAggregatePass()) > 0
//					&& Double.parseDouble(form.getIndividualPass()) > 0) {
//				errors
//						.add(
//								"error",
//								new ActionError(
//										"knowledgepro.exam.settingCourse.passCriteria.oneOnly"));
//			}
//		}
		if (form.getAggregatePass() != null
				&& form.getAggregatePass().length() != 0) {
			if (Double.valueOf(form.getAggregatePass()) > 100) {
				flag = true;
				errors.add("error", new ActionError(
						"knowledgepro.exam.examSettingCourse.maximum"));
			}
		}
		if (form.getIndividualPass() != null
				&& form.getIndividualPass().length() != 0) {

			if (Double.valueOf(form.getIndividualPass()) > 100)
				if (!flag) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.examSettingCourse.maximum"));
				}

		}

		if (form.getMinReqAttendanceWithFine()!= null && form.getMinReqAttendanceWithFine().length() > 0 &&
				 form.getMinReqAttendanceWithoutFine()!= null && form.getMinReqAttendanceWithoutFine().length() > 0) {
			b1 = new BigDecimal(form.getMinReqAttendanceWithFine());

			b2 = new BigDecimal(form.getMinReqAttendanceWithoutFine());

			d1 = b1.doubleValue();
			d2 = b2.doubleValue();
			if (d1 > d2) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.settingCourse.withFineGreaterwithoutFine"));

			}
		}
		boolean err = false;
		if(!form.getCollege().equalsIgnoreCase("cjc")){
			Iterator<ExamRevaluationTO> iter = form.getRevaluationTypeList()
					.iterator();
			while (iter.hasNext()) {
				ExamRevaluationTO examRevaluationTO = (ExamRevaluationTO) iter
						.next();
				if (examRevaluationTO.getOptionValue().equals("")) {
					err = true;
	
				}
	
			}
			if (err) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.settingCourse.revaluation.required"));
			}
		}
		saveErrors(request, errors);
		return errors;
	}

}
