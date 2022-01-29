package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.exam.ExamUpdateProcessForm;
import com.kp.cms.handlers.exam.ExamUpdateProcessHandler;
import com.kp.cms.to.exam.BatchClassTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamUpdateProcessAction extends BaseDispatchAction {
	ExamUpdateProcessHandler handler = new ExamUpdateProcessHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward initExamUpdateProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamUpdateProcessForm objform = (ExamUpdateProcessForm) form;
		objform.clearPage(mapping, request);
		objform.setExamList(handler.getExamNameList());
		objform.setIsPreviousExam("true");
		return mapping.findForward(CMSConstants.EXAM_UPDATE_PROCESS);
	}

	public ActionForward fetchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamUpdateProcessForm objform = (ExamUpdateProcessForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validate_calculate(objform);
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			Integer examId = null, academicYear = null;
			int processId = Integer.parseInt(objform.getProcess());
			objform.setProcessName(handler.getProcessName(processId));
			objform.setExamId(objform.getExamId());
			if (CommonUtil.checkForEmpty(objform.getExamId())) {
				examId = Integer.parseInt(objform.getExamId());
				objform.setExamName(handler.getExamNameByExamId(examId));
			}
			if (CommonUtil.checkForEmpty(objform.getAcademicYear())) {
				academicYear = Integer.parseInt(objform.getAcademicYear());
			}
			ArrayList<BatchClassTO> listClasses = handler.getClasses(examId,
					academicYear,objform.getProcessName());

			if (listClasses != null && listClasses.size() > 0) {
				objform.setListClasses(listClasses);
				objform.setListCount(Integer.toString(listClasses.size()));
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.exam.examEligibilitySetUp.error"));
				saveErrors(request, errors);
				objform.clearPage(mapping, request);
				objform.setExamList(handler.getExamNameList());
				return mapping.findForward(CMSConstants.EXAM_UPDATE_PROCESS);

			}
			return mapping.findForward(CMSConstants.EXAM_UPDATE_PROCESS_ADD);
		} else {
			objform.setExamList(handler.getExamNameList());
			return mapping.findForward(CMSConstants.EXAM_UPDATE_PROCESS);
		}

	}

	private ActionErrors validate_calculate(ExamUpdateProcessForm objform) {
		if (objform.getProcess() != null) {
			if (!(objform.getExamId() != null && objform.getExamId().trim()
					.length() > 0)) {
				if (objform.getProcess().equals("1")
						|| objform.getProcess().equals("2")
						|| objform.getProcess().equals("3")) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamUpdateProcess.examName"));

				}
			}
		}
		if (objform.getProcess().equals("4")
				|| objform.getProcess().equals("5")) {
			if (objform.getAcademicYear() == null
					|| objform.getAcademicYear().length() == 0) {

				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamUpdateProcess.academicYear"));

			}
		}

		return errors;
	}

	@SuppressWarnings("unchecked")
	public ActionForward updateProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamUpdateProcessForm objform = (ExamUpdateProcessForm) form;
		errors.clear();
		messages.clear();
		if (errors.isEmpty()) {
			try {
				Integer academicYear = null, examId = null;
				if (CommonUtil.checkForEmpty(objform.getExamId())) {
					examId = Integer.parseInt(objform.getExamId());
				}
				int processId = Integer.parseInt(objform.getProcess());
				if (CommonUtil.checkForEmpty(objform.getAcademicYear())) {
					academicYear = Integer.parseInt(objform.getAcademicYear());
				}
	
				boolean checkPromotionCriteria = false;
				if (objform.getPromotionCritariaCheck() != null
						&& objform.getPromotionCritariaCheck().equalsIgnoreCase(
								"on")) {
					checkPromotionCriteria = true;
				} else {
					checkPromotionCriteria = false;
				}
				boolean checkExamFeePaid = false;
				if (objform.getExamFeePaidCheck() != null
						&& objform.getExamFeePaidCheck().equalsIgnoreCase("on")) {
					checkExamFeePaid = true;
				} else {
					checkExamFeePaid = false;
				}
				ArrayList<BatchClassTO> listClasses = objform.getListClasses();
				ArrayList<BatchClassTO> listSelected = new ArrayList<BatchClassTO>();
				for (Iterator iterator = listClasses.iterator(); iterator.hasNext();) {
					BatchClassTO batchClassTO = (BatchClassTO) iterator.next();
					if (batchClassTO.getBatchCheck() != null) {
						listSelected.add(batchClassTO);
					}
				}
				handler.validatePromotionProcess(processId, listSelected, examId, academicYear,
						checkPromotionCriteria, checkExamFeePaid,objform.getIsPreviousExam(), errors);
				if(errors!=null && errors.isEmpty()){
					handler.update(processId, listSelected, examId, academicYear,
							checkPromotionCriteria, checkExamFeePaid,objform.getIsPreviousExam(), errors);
				}else{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.EXAM_UPDATE_PROCESS_ADD);
				}
				objform.clearPage(mapping, request);
				objform.setExamList(handler.getExamNameList());
				return mapping.findForward(CMSConstants.EXAM_UPDATE_PROCESS);
			} catch (Exception e) {
			if(e instanceof DataNotFoundException) {
	    		saveErrors(request,errors);
	    		Integer examId = null, academicYear = null;
	    		if (CommonUtil.checkForEmpty(objform.getExamId())) {
					examId = Integer.parseInt(objform.getExamId());
				}
				if (CommonUtil.checkForEmpty(objform.getAcademicYear())) {
					academicYear = Integer.parseInt(objform.getAcademicYear());
				}
	    		ArrayList<BatchClassTO> listClasses = handler.getClasses(examId,
						academicYear,objform.getProcessName());

				if (listClasses != null && listClasses.size() > 0) {
					objform.setListClasses(listClasses);
					objform.setListCount(Integer.toString(listClasses.size()));
				}
	    		return mapping.findForward(CMSConstants.EXAM_UPDATE_PROCESS_ADD);
			}
			else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					objform.setErrorMessage(msg);
					objform.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		} else {
			return mapping.findForward(CMSConstants.EXAM_UPDATE_PROCESS_ADD);

		}

	}
}
