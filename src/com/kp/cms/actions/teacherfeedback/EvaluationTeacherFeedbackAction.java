package com.kp.cms.actions.teacherfeedback;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackOpenConnection;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.teacherfeedback.EvaluationTeacherFeedbackForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.teacherfeedback.EvaTeacherFeedBackQuestionHandler;
import com.kp.cms.handlers.teacherfeedback.EvaluationTeacherFeedbackHandler;
import com.kp.cms.handlers.teacherfeedback.TeacherFeedbackInstructionsHandler;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackQuestionTo;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackTo;
import com.kp.cms.to.teacherfeedback.TeacherFeedbackInstructionsTO;
import com.kp.cms.transactionsimpl.attendance.AcademicyearTransactionImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

public class EvaluationTeacherFeedbackAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EvaluationTeacherFeedbackAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEvaluationTeacherFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm)form;
		HttpSession session=request.getSession();
		evaTeacherFeedbackForm.clear();
		evaTeacherFeedbackForm.setTeacher(request.getSession().getAttribute("uid").toString());
		evaTeacherFeedbackForm.setRoleId(Integer.parseInt(session.getAttribute("rid").toString()));
		setRequiredDataToForm(evaTeacherFeedbackForm);
		setUserId(request, evaTeacherFeedbackForm);
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK);
	}

	private void setRequiredDataToForm(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception {
		if(evaTeacherFeedbackForm.getYear()==null){
			AcademicyearTransactionImpl impl = new AcademicyearTransactionImpl();
			int curYear = impl.getCurrentAcademicYearforTeacher();
			evaTeacherFeedbackForm.setYear(new Integer(curYear).toString());
		}
		

		List<EvaTeacherFeedbackOpenConnection> connectionClasses = EvaluationTeacherFeedbackHandler.getInstance().getOpenConnections(evaTeacherFeedbackForm);
		Map<String, String> connOpenClasses = new HashMap<String, String>();
		if(connectionClasses!=null){
			Iterator<EvaTeacherFeedbackOpenConnection> itr = connectionClasses.iterator();
			while(itr.hasNext()){
				EvaTeacherFeedbackOpenConnection bo = itr.next();
				connOpenClasses.put(bo.getClassesId().getId()+"_"+bo.getFeedbackSession().getId(), bo.getClassesId().getName());
			}
		}
		evaTeacherFeedbackForm.setClassMapForFeedback(connOpenClasses);	
	}	
	
	public ActionForward getClassesDataToForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm) form;

		if(evaTeacherFeedbackForm.getYear()==null){
			AcademicyearTransactionImpl impl = new AcademicyearTransactionImpl();
			int curYear = impl.getCurrentAcademicYearforTeacher();
			evaTeacherFeedbackForm.setYear(new Integer(curYear).toString());
		}
		

		List<EvaTeacherFeedbackOpenConnection> connectionClasses = EvaluationTeacherFeedbackHandler.getInstance().getOpenConnections(evaTeacherFeedbackForm);
		Map<String, String> connOpenClasses = null;
		if(connectionClasses!=null){
			Iterator<EvaTeacherFeedbackOpenConnection> itr = connectionClasses.iterator();
			while(itr.hasNext()){
				EvaTeacherFeedbackOpenConnection bo = itr.next();
					connOpenClasses = new HashMap<String, String>();
					connOpenClasses.put(bo.getClassesId().getId()+"_"+bo.getFeedbackSession().getId(), bo.getClassesId().getName());
			}
		}
		evaTeacherFeedbackForm.setClassMapForFeedback(connOpenClasses);	
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK);

	}	
	
	public ActionForward getSubjectsByClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			if (evaTeacherFeedbackForm.getClassSchemewiseId() != null
					&& evaTeacherFeedbackForm.getClassSchemewiseId().length() != 0) {				
			
					subjectMap = EvaluationTeacherFeedbackHandler.getInstance()
							.getSubjectsByClassForTeacher(evaTeacherFeedbackForm);
					evaTeacherFeedbackForm.setSubjectMap(subjectMap);
			}
			
			setRequiredDataToForm(evaTeacherFeedbackForm);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK);
	}
	
	public ActionForward getDetailsForFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm)form;
		try{
			List<TeacherFeedbackInstructionsTO> toList = TeacherFeedbackInstructionsHandler.getInstance().getInstructions();
			evaTeacherFeedbackForm.setInstructionsTOsList(toList);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			evaTeacherFeedbackForm.setErrorMessage(msg);
			evaTeacherFeedbackForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_INSTS);
	}
	
	public ActionForward getStudentsForFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm)form;
		try{
			List<EvaluationTeacherFeedbackTo> studentFeedBackList = EvaluationTeacherFeedbackHandler.getInstance().getStudentsForFeedback(evaTeacherFeedbackForm);
			evaTeacherFeedbackForm.setStudentList(studentFeedBackList);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			evaTeacherFeedbackForm.setErrorMessage(msg);
			evaTeacherFeedbackForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EVAL_TEACHER_FEEDBACK_STUDENTS);
	}
	
	public ActionForward startTeachersEvaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm)form;
		setUserId(request, evaTeacherFeedbackForm);
		try{
			evaTeacherFeedbackForm.setErrorMsg(null);
			evaTeacherFeedbackForm.setSubmitSuccessfully(false);
			evaTeacherFeedbackForm.setEvaluationCompleted(false);
			evaTeacherFeedbackForm.setLastStudent(false);
			/* iterating the list of students and picking one student details */
			EvaluationTeacherFeedbackHandler.getInstance().setStudentDetailsToForm(evaTeacherFeedbackForm);
			 /* taking the list of questions and putting it into the formbean*/
			List<EvaTeacherFeedBackQuestionTo> questionTos = EvaTeacherFeedBackQuestionHandler.getInstance().getFeedBackQuestionList();
			evaTeacherFeedbackForm.setEvaTeacherQuestionsToList(questionTos);
			 if(evaTeacherFeedbackForm.isEvaluationCompleted()){
				 evaTeacherFeedbackForm.setExist(true);
					return mapping.findForward(CMSConstants.ALREADY_EXIST_OR_SUBMITTED_SUCCESSFULLY_TEACHERS_EVALUATION);
			 }
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			evaTeacherFeedbackForm.setErrorMessage(msg);
			evaTeacherFeedbackForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.START_STUDENTS_EVAL_FEEDBACK);
	}
	
	public ActionForward submitStudentsEvaluationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm)form;
		setUserId(request, evaTeacherFeedbackForm);
		ActionMessages messages = new ActionMessages();
		
		try{
			// valiadating answers
			if(evaTeacherFeedbackForm.getEvaTeacherQuestionsToList().size()!=0){
				Iterator<EvaTeacherFeedBackQuestionTo> itr = evaTeacherFeedbackForm.getEvaTeacherQuestionsToList().iterator();
				while(itr.hasNext()){
					EvaTeacherFeedBackQuestionTo to = itr.next();
					if(to.getAnswer()==null){
						evaTeacherFeedbackForm.setErrorMsg("Please answer all the questions");
						return mapping.findForward(CMSConstants.START_STUDENTS_EVAL_FEEDBACK);
					}
				}
			}
			evaTeacherFeedbackForm.setErrorMsg(null);
			evaTeacherFeedbackForm.setPreviousStudentName(evaTeacherFeedbackForm.getNameOfStudent());
			Integer currentStudentNo = null ;
			Integer totalStudentNo = null;
			if(evaTeacherFeedbackForm.getStudentNo()!=0){
				currentStudentNo = evaTeacherFeedbackForm.getStudentNo();
			}
			if(evaTeacherFeedbackForm.getTotalStudents()!=0){
				totalStudentNo = evaTeacherFeedbackForm.getTotalStudents();
			}
			/* putting into the EvaluationTeacherFeedbackTo list of each submitted Student Rating Details and setting that tosList to the form */
			boolean isDuplicate = EvaluationTeacherFeedbackHandler.getInstance().storeEachStudentRatingDetails(evaTeacherFeedbackForm);
			 /*  save the bo into the database   */
			if(!isDuplicate){
				boolean isAdded = EvaluationTeacherFeedbackHandler.getInstance().saveStudentRatingDetailsInToBO(evaTeacherFeedbackForm);
				if(isAdded){
					evaTeacherFeedbackForm.setSubmitSuccessfully(true);
					evaTeacherFeedbackForm.setTempTeachersEvaList(null);
				}
			}else {
				evaTeacherFeedbackForm.setErrorMsg("Already Feedback has been given for this Student "+evaTeacherFeedbackForm.getNameOfStudent());
			}
			if(totalStudentNo.equals(currentStudentNo)){
				List<EvaluationTeacherFeedbackTo> toList = EvaluationTeacherFeedbackHandler.getInstance().getStudentsForFeedback(evaTeacherFeedbackForm);
				evaTeacherFeedbackForm.setStudentList(toList);
				evaTeacherFeedbackForm.setSubmitSuccessfully(false);
				evaTeacherFeedbackForm.setLastStudent(true);
			}
			evaTeacherFeedbackForm.setRemarks(null);			
			
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			evaTeacherFeedbackForm.setErrorMessage(msg);
			evaTeacherFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
			
		return mapping.findForward(CMSConstants.START_STUDENTS_EVAL_FEEDBACK);		
	}

	public ActionForward initEvaluationTeacherFeebackReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm)form;
		evaTeacherFeedbackForm.clear();
		HttpSession session = request.getSession(false);
		if(session.getAttribute("rid")!=null){
			evaTeacherFeedbackForm.setRoleId(Integer.parseInt(session.getAttribute("rid").toString()));
		}
			
		setUserId(request, evaTeacherFeedbackForm);
		ActionMessages messages = new ActionMessages();		
		try{
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			evaTeacherFeedbackForm.setYear(String.valueOf(currentYear));
			if(evaTeacherFeedbackForm.getRoleId()==19){
				evaTeacherFeedbackForm.setIsTeacher(true);
				Map<Integer,String> classMap = EvaluationTeacherFeedbackHandler.getInstance().getClassesByTeacherAndYearForReport(evaTeacherFeedbackForm);
				evaTeacherFeedbackForm.setClassMap(classMap);
			}else{
				evaTeacherFeedbackForm.setIsTeacher(false);
				Map<Integer,String> teacherMap = EvaluationTeacherFeedbackHandler.getInstance().getTeacherMapByYearForReport(evaTeacherFeedbackForm);
				evaTeacherFeedbackForm.setTeacherMap(teacherMap);
			}
			
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			evaTeacherFeedbackForm.setErrorMessage(msg);
			evaTeacherFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_REPORT);		
	}

	public Map<Integer,String> setupClassMapToRequest(EvaluationTeacherFeedbackForm evaTeacherFeedbackForm) throws Exception{
		log.info("Entering into setpClassMapToRequest of EvaluationTeacherFeedbackAction");
		Map<Integer,String> classMap = new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			return classMap;
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error("Error occured in setpClassMapToRequest of EvaluationTeacherFeedbackAction");
		}
		log.info("Leaving into setpClassMapToRequest of EvaluationTeacherFeedbackAction");
		return classMap;
	}
	
	public ActionForward getSubjectsByClassForReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			if (evaTeacherFeedbackForm.getClassSchemewiseId() != null
					&& evaTeacherFeedbackForm.getClassSchemewiseId().length() != 0) {				
			
					subjectMap = EvaluationTeacherFeedbackHandler.getInstance()
							.getSubjectsByClassForTeacher(evaTeacherFeedbackForm);
					evaTeacherFeedbackForm.setSubjectMap(subjectMap);
			}
			
			setRequiredDataToForm(evaTeacherFeedbackForm);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_REPORT);
	}
	
	public ActionForward getClassesDataToFormForReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm) form;
		Map<Integer, String> classesForReport  = EvaluationTeacherFeedbackHandler.getInstance().getClassesByTeacherAndYearForReport(evaTeacherFeedbackForm);
		evaTeacherFeedbackForm.setClassMap(classesForReport);	
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_REPORT);

	}	
	
	public ActionForward getTeacherFeedbackDetailsForReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm)form;
		try{
			setUserId(request, evaTeacherFeedbackForm);
			Map<Integer, EvaluationTeacherFeedbackTo> studentFeedBackMap = EvaluationTeacherFeedbackHandler.getInstance().getTecherFeedbackDetailsForReport(evaTeacherFeedbackForm);
			evaTeacherFeedbackForm.setStudentsFeedbackMap(studentFeedBackMap);
			List<EvaTeacherFeedBackQuestionTo> questionTos = EvaTeacherFeedBackQuestionHandler.getInstance().getFeedBackQuestionList();
			Collections.sort(questionTos);
			evaTeacherFeedbackForm.setTotalQuestions(questionTos.size());
			evaTeacherFeedbackForm.setEvaTeacherQuestionsToList(questionTos);
			EvaluationTeacherFeedbackHandler.getInstance().getTeacherEvaluatedDetails(evaTeacherFeedbackForm,studentFeedBackMap);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			evaTeacherFeedbackForm.setErrorMessage(msg);
			evaTeacherFeedbackForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		evaTeacherFeedbackForm.setTotalStudents(evaTeacherFeedbackForm.getStudentsFeedbackMap().size());
		if(evaTeacherFeedbackForm.isComparisionReport())
			return mapping.findForward(CMSConstants.EVAL_TEACHER_FEEDBACK__COMAPARISION_REPORT);
		else
			return mapping.findForward(CMSConstants.EVAL_TEACHER_FEEDBACK_REPORT);
	}
	
	public ActionForward initEvaluationTeacherFeebackComparisionReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm)form;
		evaTeacherFeedbackForm.clear();
		HttpSession session = request.getSession(false);
		if(session.getAttribute("rid")!=null){
			evaTeacherFeedbackForm.setRoleId(Integer.parseInt(session.getAttribute("rid").toString()));
		}
		setUserId(request, evaTeacherFeedbackForm);
		evaTeacherFeedbackForm.setComparisionReport(true);
		ActionMessages messages = new ActionMessages();		
		try{
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			evaTeacherFeedbackForm.setYear(String.valueOf(currentYear));
			if(evaTeacherFeedbackForm.getRoleId()==19){
				evaTeacherFeedbackForm.setIsTeacher(true);
				Map<Integer,String> classMap = EvaluationTeacherFeedbackHandler.getInstance().getClassesByTeacherAndYearForReport(evaTeacherFeedbackForm);
				evaTeacherFeedbackForm.setClassMap(classMap);
			}else{
				evaTeacherFeedbackForm.setIsTeacher(false);
				Map<Integer,String> teacherMap = EvaluationTeacherFeedbackHandler.getInstance().getTeacherMapByYearForReport(evaTeacherFeedbackForm);
				evaTeacherFeedbackForm.setTeacherMap(teacherMap);
			}
			
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			evaTeacherFeedbackForm.setErrorMessage(msg);
			evaTeacherFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_REPORT);		
	}
	
	public ActionForward getgetTeachersByYearForReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EvaluationTeacherFeedbackForm evaTeacherFeedbackForm = (EvaluationTeacherFeedbackForm) form;
		Map<Integer, String> teacherMap = new HashMap<Integer, String>();
		try {
			teacherMap = EvaluationTeacherFeedbackHandler.getInstance()
						.getTeacherMapByYearForReport(evaTeacherFeedbackForm);
			evaTeacherFeedbackForm.setTeacherMap(teacherMap);			
			setRequiredDataToForm(evaTeacherFeedbackForm);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return mapping.findForward(CMSConstants.INIT_EVAL_TEACHER_FEEDBACK_REPORT);
	}
}
