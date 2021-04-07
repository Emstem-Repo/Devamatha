package com.kp.cms.actions.exam;

import java.util.HashMap;
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
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamPromotionCriteriaForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamPromotionCriteriaHandler;
import com.kp.cms.to.exam.ExamPromotionCriteriaTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamPromotionCriteriaImpl;

@SuppressWarnings("deprecation")
public class ExamPromotionCriteriaAction extends BaseDispatchAction {
	ExamPromotionCriteriaHandler handler = new ExamPromotionCriteriaHandler();
	CourseTransactionImpl implCourse= new CourseTransactionImpl();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward initExamPromotionCriteria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPromotionCriteriaForm objEPCForm = (ExamPromotionCriteriaForm) form;
		setUserId(request, objEPCForm);
		objEPCForm.clearPage(mapping, request);
		setRequestToList(objEPCForm, request);
		return mapping.findForward(CMSConstants.EXAM_PROMOTION_CRITERIA);
	}

	private void setRequestToList(ExamPromotionCriteriaForm objEPCForm,
			HttpServletRequest request) {
		// get list of course name in form of ProgramType-Program-Course
		 try {
			 HashMap<Integer,String> courseMap=implCourse.getCourseMap();
		    if(courseMap!=null){
			objEPCForm.setCourseMap(courseMap);
		 }
		//objEPCForm.setCourseMap(handler.getCourseListHashMap());
		objEPCForm.setMainList(handler.getMainList());
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}

	public ActionForward addExamPromotionCriteria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPromotionCriteriaForm objForm = (ExamPromotionCriteriaForm) form;
		messages.clear();
		errors.clear();
		errors = objForm.validate(mapping, request);
		errors = validateData(objForm);
		saveErrors(request, errors);
		try {
			setUserId(request, objForm);
			if (errors.isEmpty()) {

				String courseid = objForm.getCourseId();
				String fromscheme = objForm.getFromScheme();
				String toscheme = objForm.getToScheme();
				String maxradio = objForm.getMaxBacklog();
				String scheme[] = objForm.getScheme();
				String backlogperc = objForm.getBackLogCountPercentage();
				String backlognum = objForm.getBackLogNumbers();
				if (maxradio != null && maxradio.equals("percentage")) {

					if (!(backlogperc != null && backlogperc.trim().length() > 0)) {

						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.ExamPromotionCriteria.MaxBacklog.Text"));
						saveErrors(request, errors);
					} else {

						handler.addExamPromotionCriteria(courseid, fromscheme,
								toscheme, scheme, maxradio, backlogperc);

						ActionMessage message = new ActionMessage(
								"knowledgepro.exam.ExamPromotionCriteria.addsuccess");
						messages.add("messages", message);

						saveMessages(request, messages);
					}

				} else {
					if (!(backlognum != null && backlognum.trim().length() > 0)) {

						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.ExamPromotionCriteria.MaxBacklogNumber.Text"));
						saveErrors(request, errors);
					} else {

						handler.addExamPromotionCriteria(courseid, fromscheme,
								toscheme, scheme, maxradio, backlognum);

						ActionMessage message = new ActionMessage(
								"knowledgepro.exam.ExamPromotionCriteria.addsuccess");
						messages.add("messages", message);

						saveMessages(request, messages);
					}

				}

			}

		} catch (NullPointerException ex) {
			ex.printStackTrace();
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamPromotionCriteria.MaxBacklog"));
			saveErrors(request, errors);

		} catch (DuplicateException ex) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.Entry.exists"));
			saveErrors(request, errors);

		} finally {
			objForm.clearPage(mapping, request);
			setRequestToList(objForm, request);
		}
		return mapping.findForward(CMSConstants.EXAM_PROMOTION_CRITERIA);
	}

	private ActionErrors validateData(ExamPromotionCriteriaForm objForm) {
		if (objForm.getMaxBacklog() == null) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamPromotionCriteria.MaxBacklog"));
		}
		return errors;
	}

	public ActionForward editExamPromotionCriteria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPromotionCriteriaForm objEPCForm = (ExamPromotionCriteriaForm) form;
		errors = objEPCForm.validate(mapping, request);

		try {
			setUserId(request, objEPCForm);

			objEPCForm.clearPage(mapping, request);
			handler.getUpdatableForm(objEPCForm);
			ExamGenHandler egh = new ExamGenHandler();

			HashMap<Integer, String> h = egh.getSchemeNoByCourse(objEPCForm
					.getCourseId());

			request.setAttribute("schemeMap", h);
			request.setAttribute("operation", "edit");

		} catch (Exception ex) {

		} finally {
			ExamGenHandler egh = new ExamGenHandler();

			HashMap<Integer, String> h = egh.getSchemeNoByCourse(objEPCForm
					.getCourseId());

			request.setAttribute("schemeMap", h);
			request.setAttribute("schemesMap", objEPCForm.getSchemesMap());
			request.setAttribute("operation", "edit");
			setRequestToList(objEPCForm, request);
		}
		return mapping.findForward(CMSConstants.EXAM_PROMOTION_CRITERIA);
	}

	public ActionForward deleteExamPromotionCriteria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPromotionCriteriaForm objForm = (ExamPromotionCriteriaForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		boolean edit = true;
		try {
			setUserId(request, objForm);
			if (edit) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamPromotionCriteria.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage(mapping, request);

				 handler.deleteExamPromotionCriteria(objForm.getId());

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			objForm.clearPage(mapping, request);
			setRequestToList(objForm, request);
		}
		return mapping.findForward(CMSConstants.EXAM_PROMOTION_CRITERIA);
	}

	public ActionForward updateExamPromotionCriteria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPromotionCriteriaForm objForm = (ExamPromotionCriteriaForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		errors.clear();
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		String a[] = objForm.getScheme();
		if (errors.isEmpty()) {
			try {
				setUserId(request, objForm);
				int id = objForm.getId();

				int courseid = Integer.parseInt(objForm.getCourseId());
				int fromscheme = Integer.parseInt(objForm.getFromScheme());
				int toscheme = Integer.parseInt(objForm.getToScheme());
				String maxradio = objForm.getMaxBacklog();

				String backlogperc = objForm.getBackLogCountPercentage();

				String backlognum = objForm.getBackLogNumbers();
				String originalcourseid = objForm.getOriginalCourseId();
				String originalfromscheme = objForm.getOriginalFromScheme();
				String originaltoscheme = objForm.getOriginalToScheme();
				String originalscheme = objForm.getOriginalScheme();

				if (maxradio.equalsIgnoreCase("percentage")) {
					handler.update_ExamPromotionCriteria(id, courseid,
							fromscheme, toscheme, a, maxradio, backlogperc,
							originalcourseid, originalfromscheme,
							originaltoscheme, originalscheme);
				} else {
					handler.update_ExamPromotionCriteria(id, courseid,
							fromscheme, toscheme, a, maxradio, backlognum,
							originalcourseid, originalfromscheme,
							originaltoscheme, originalscheme);
				}

				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamPromotionCriteria.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage(mapping, request);
				setRequestToList(objForm, request);

			} catch (NullPointerException e1) {
				e1.printStackTrace();
			} catch (Exception e2) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.CouseScheme.exists"));
				saveErrors(request, errors);
				handler.getUpdatableForm(objForm);
				ExamGenHandler egh = new ExamGenHandler();

				HashMap<Integer, String> h = egh.getSchemeNoByCourse(objForm
						.getCourseId());

				request.setAttribute("schemeMap", h);
				request.setAttribute("operation", "edit");
				request.setAttribute("schemesMap", objForm.getSchemesMap());

				setRequestToList(objForm, request);
			} finally {
				// objForm.clearPage(mapping, request);
				setRequestToList(objForm, request);
			}
		}
		setRequestToList(objForm, request);
		return mapping.findForward(CMSConstants.EXAM_PROMOTION_CRITERIA);
	}
}