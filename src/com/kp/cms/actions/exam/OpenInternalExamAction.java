package com.kp.cms.actions.exam;

import java.util.Calendar;
import java.util.Date;
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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.OpenInternalExamForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.handlers.exam.OpenInternalExamHandler;
import com.kp.cms.to.exam.ExamDefinitionTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class OpenInternalExamAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(OpenInternalExamAction.class);
	OpenInternalExamHandler handler = new OpenInternalExamHandler();
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		OpenInternalExamForm objForm = (OpenInternalExamForm) form; 
		try{
			objForm.clearPage(mapping, request);
			int academicYear=CurrentAcademicYear.getInstance().getAcademicyear();
			objForm.setAcademicYear(String.valueOf(academicYear));
			Map<Integer, String> examNameMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType("Int",objForm.getAcademicYear());
			Map<Integer, String> programTypeMap = handler.getProgramTypeMap();
			objForm.setProgramTypeMap(programTypeMap);
			examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
			objForm.setExamMap(examNameMap);
			List<ExamDefinitionTO> examList = handler.getExamList();
			objForm.setExamList(examList);
			if(objForm.getNewProgramTypeId() != null && !objForm.getNewProgramTypeId().isEmpty() && objForm.getAcademicYear() != null && !objForm.getAcademicYear().isEmpty()){
				Map<Integer, String> claasesMap = CommonAjaxHandler.getInstance().getClassesForProgram(objForm.getNewProgramTypeId(),objForm.getAcademicYear());
				objForm.setClassesMap(claasesMap);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.OPEN_EXAM_FRIST);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the saveDetails in OpenInternalExamAction");
		OpenInternalExamForm objForm = (OpenInternalExamForm) form;
		setUserId(request, objForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		validateDate(objForm,errors);
		HttpSession session = request.getSession(false);
		session.setAttribute("endDate", null);
		try {
			if(errors.isEmpty()){
				String checkDuplicate = handler.checkDuplicate(objForm,session);
				if(checkDuplicate == null || checkDuplicate.isEmpty()){
					boolean save = handler.saveDetails(objForm);
					if(save){
						messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.addsuccess","Exam "));
						saveMessages(request, messages);
						objForm.clearPage(mapping, request);
						List<ExamDefinitionTO> examList = handler.getExamList();
						objForm.setExamList(examList);
					}
				}else{
					String endDate = session.getAttribute("endDate").toString();
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.duplcate.exam.opend",checkDuplicate,endDate));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.OPEN_EXAM_FRIST);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit from the saveDetails in OpenInternalExamAction");
		return mapping.findForward(CMSConstants.OPEN_EXAM_FRIST);
	}
	/**
	 * @param objForm
	 * @param errors 
	 */
	private void validateDate(OpenInternalExamForm objForm, ActionErrors errors) {
		if(objForm.getNewProgramTypeId() == null || objForm.getNewProgramTypeId().isEmpty()){
			errors.add("error",  new ActionError("knowledgepro.admin.course.programType"));
		}
		if(objForm.getStayClass() == null || objForm.getStayClass()[0]==null || objForm.getStayClass()[0].isEmpty()){
			errors.add("error",  new ActionError("admissionFormForm.class.required"));
		}
		
		if(CommonUtil.checkForEmpty(objForm.getStartDate()) && CommonUtil.checkForEmpty(objForm.getEndDate()) &&
				CommonUtil.isValidDate(objForm.getStartDate()) && CommonUtil.isValidDate(objForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(objForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(objForm.getEndDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
		if(objForm.getTheoryPractical() == null || objForm.getTheoryPractical().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.exam.theory.practical.required"));
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editExam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the editExam in OpenInternalExamAction");
		OpenInternalExamForm objForm = (OpenInternalExamForm) form;
		try {
			handler.setExamDetailsToForm(objForm);
			/* code added by sudhir*/
			Map<Integer, String> examNameMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType("Int",objForm.getAcademicYear());
			examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
			objForm.setExamMap(examNameMap);
			/* code added by sudhir ends here */
			request.setAttribute("operation", "edit");
			if(objForm.getNewProgramTypeId() != null && !objForm.getNewProgramTypeId().isEmpty() && objForm.getAcademicYear() != null && !objForm.getAcademicYear().isEmpty()){
				//Map<Integer, String> claasesMap = CommonAjaxHandler.getInstance().getClassesForProgram(objForm.getNewProgramTypeId(), objForm.getAcademicYear());
				Map<Integer, String> claasesMap = CommonAjaxHandler.getInstance().getClassesForProgm(objForm.getNewProgramTypeId(), objForm.getAcademicYear(),objForm.getSelClassesMap());
				objForm.setClassesMap(claasesMap);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit from the editExam in OpenInternalExamAction");
		return mapping.findForward(CMSConstants.OPEN_EXAM_FRIST);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteExam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the editExam in OpenInternalExamAction");
		OpenInternalExamForm objForm = (OpenInternalExamForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			boolean delete = handler.deleteExam(objForm);
			if(delete){
				messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.deletesuccess","Exam "));
				saveMessages(request, messages);
				objForm.clearPage(mapping, request);
				List<ExamDefinitionTO> examList = handler.getExamList();
				objForm.setExamList(examList);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit from the editExam in OpenInternalExamAction");
		return mapping.findForward(CMSConstants.OPEN_EXAM_FRIST);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the saveDetails in OpenInternalExamAction");
		OpenInternalExamForm objForm = (OpenInternalExamForm) form;
		setUserId(request, objForm);
//		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		validateDate(objForm,errors);
		HttpSession session = request.getSession(false);
		session.setAttribute("endDate", null);
		try {
			if(errors.isEmpty()){
				String checkduplicate = handler.checkDuplicate(objForm,session);
				if(checkduplicate == null || checkduplicate.isEmpty()){
					boolean save = handler.updateDetails(objForm);
					if(save){
						messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.updatesuccess","Exam "));
						saveMessages(request, messages);
						objForm.clearPage(mapping, request);
						List<ExamDefinitionTO> examList = handler.getExamList();
						objForm.setExamList(examList);
					}
				}else{
					String endDate = session.getAttribute("endDate").toString();
					request.setAttribute("operation", "edit");
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.duplcate.exam.opend",checkduplicate,endDate));
					saveErrors(request, errors);
				}
			}else{
				request.setAttribute("operation", "edit");
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.OPEN_EXAM_FRIST);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit from the saveDetails in OpenInternalExamAction");
		return mapping.findForward(CMSConstants.OPEN_EXAM_FRIST);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getClassesByProgramIdAndExamId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OpenInternalExamForm objForm = (OpenInternalExamForm) form;
		Map<Integer,String> classesMap =null;
		try{
			int examNameId = Integer.parseInt(objForm.getExamId());
			int programTypeId = Integer.parseInt(objForm.getNewProgramTypeId());
			classesMap = handler.getInternalExamClassesMap(examNameId,programTypeId);
		} catch (Exception e) {
			log.debug(e.getMessage());

		}
		if (objForm.getPropertyName() != null) {
			objForm.getCollectionMap().put(
					objForm.getPropertyName(), classesMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classesMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
}

