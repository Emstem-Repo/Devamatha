package com.kp.cms.actions.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.EvaluationStudentFeedbackForm;
import com.kp.cms.handlers.admin.EvaluationStudentFeedbackHandler;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;
import com.kp.cms.to.admin.TeacherClassSubjectTO;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.to.studentfeedback.ExitEvaluationQuestionTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;

public class EvaluationStudentFeedbackAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEvaluationStudentFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EvaluationStudentFeedbackForm evaStudentFeedbackForm = (EvaluationStudentFeedbackForm)form;
		setUserId(request, evaStudentFeedbackForm);
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		String courseId = (String) session.getAttribute("stuCourseId");
		String classId = (String) session.getAttribute("ClassId");
		/* newly added code */
		int admApplnId = (Integer) session.getAttribute("admApplnId");
		String classSchemeWiseId = (String) session.getAttribute("classSchemeWiseId");
		/*----------------*/
		evaStudentFeedbackForm.setTeacherClassSubjectToList(null);
		evaStudentFeedbackForm.setTeacherId(0);
		evaStudentFeedbackForm.setTeacherName(null);
		evaStudentFeedbackForm.setSubjectId(null);
		evaStudentFeedbackForm.setSubjectName(null);
		evaStudentFeedbackForm.setSubjectNo(0);
		evaStudentFeedbackForm.setStudentId(0);
		evaStudentFeedbackForm.setFacultyEvaluationTo(null);
		evaStudentFeedbackForm.setInstructionsList(null);
		evaStudentFeedbackForm.setEvaluationFeedbackId(0);
		evaStudentFeedbackForm.setTotalSubjects(0);
		evaStudentFeedbackForm.setSessionId(0);
		evaStudentFeedbackForm.setAdmApplnId(0);
		evaStudentFeedbackForm.setClassSchemewiseId(null);
		evaStudentFeedbackForm.setExitEvalRemarks(null);
		try{
			int sessionId = (Integer) session.getAttribute("SESSIONID");
			boolean isExitEval=(Boolean)session.getAttribute("ISEXITEVAL");
			evaStudentFeedbackForm.setSessionId(sessionId);
			evaStudentFeedbackForm.setIsExitEval(isExitEval);
			ISingleFieldMasterTransaction txn1=SingleFieldMasterTransactionImpl.getInstance();
			Student student=(Student) txn1.getMasterEntryDataById(Student.class,Integer.parseInt(studentid));
			if(student!=null && student.getAdmAppln()!=null && student.getAdmAppln().getPersonalData()!=null)
			evaStudentFeedbackForm.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
			if(student!=null && student.getAdmAppln()!=null && student.getAdmAppln().getCourseBySelectedCourseId()!=null)
			evaStudentFeedbackForm.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
			evaStudentFeedbackForm.setStudentId(Integer.parseInt(studentid));
			evaStudentFeedbackForm.setCourseId(courseId);
			evaStudentFeedbackForm.setClassId(classId);
			evaStudentFeedbackForm.setAdmApplnId(admApplnId);
			evaStudentFeedbackForm.setClassSchemewiseId(classSchemeWiseId);
			EvaluationStudentFeedbackHandler.getInstance().getProgramTypeId(evaStudentFeedbackForm);
			if((Integer.parseInt(evaStudentFeedbackForm.getProgramTypeId()))==1){
			EvaluationStudentFeedbackHandler.getInstance().getStudentTotalAttendancePercentage(Integer.valueOf(studentid.trim()),Integer.valueOf(classId.trim()),evaStudentFeedbackForm);
			//EvaluationStudentFeedbackHandler.getInstance().calculateStudentAttendance(Integer.valueOf(studentid.trim()),evaStudentFeedbackForm,courseId);
			/*InputStream inStr = CommonUtil.class.getClassLoader()
								.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES)
			
			prop.load(inStr);;*/
			String attendancePercentage=CMSConstants.ATTENDANCE_PERCENTAGE;
			Float maxPercentage = Float.valueOf(attendancePercentage);
			Float studentPercentage = Float.valueOf(evaStudentFeedbackForm.getTotalPercentage());
			if(studentPercentage < maxPercentage){
				return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK_ATTENDANCE_SHORTAGE);
			   }
			}
			boolean isExist = EvaluationStudentFeedbackHandler.getInstance().checkStudentIsAlreadyExist(evaStudentFeedbackForm);
			if(isExist){
				return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK_ALREADY_EXIST);
			}
			EvaluationStudentFeedbackHandler.getInstance().getStuFeedbackInstructions(evaStudentFeedbackForm);
		}catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			evaStudentFeedbackForm.setErrorMessage(msg);
			evaStudentFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTeachersAndSubjectDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EvaluationStudentFeedbackForm evaStudentFeedbackForm = (EvaluationStudentFeedbackForm)form;
		setUserId(request, evaStudentFeedbackForm);
		try{
			List<TeacherClassSubjectTO> facultyEvaluationList = EvaluationStudentFeedbackHandler.getInstance().getDetails(evaStudentFeedbackForm);
			evaStudentFeedbackForm.setTeacherClassSubjectToList(facultyEvaluationList);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			evaStudentFeedbackForm.setErrorMessage(msg);
			evaStudentFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EVAL_STU_FEEDBACK_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAnsweringCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EvaluationStudentFeedbackForm evaStudentFeedbackForm = (EvaluationStudentFeedbackForm)form;
		setUserId(request, evaStudentFeedbackForm);
		try{
			EvaluationStudentFeedbackHandler.getInstance().getTeacherNameAndSubjectName(evaStudentFeedbackForm);
			List<EvaStudentFeedBackQuestionTo> questionListTo = EvaluationStudentFeedbackHandler.getInstance().getQuestionList(evaStudentFeedbackForm);
			evaStudentFeedbackForm.setQuestionListTo(questionListTo);
		}catch (BusinessException businessException) {
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			evaStudentFeedbackForm.setErrorMessage(msg);
			evaStudentFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.FACULTY_EVAL_ANSWERING_CHECK);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward submitFacultyEvaluationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EvaluationStudentFeedbackForm evaStudentFeedbackForm = (EvaluationStudentFeedbackForm)form;
		setUserId(request, evaStudentFeedbackForm);
		HttpSession session = request.getSession(true);
		try{
			evaStudentFeedbackForm = EvaluationStudentFeedbackHandler.getInstance().saveEvaluationFacultyDetailsToList(evaStudentFeedbackForm);
			Integer subjectNo = evaStudentFeedbackForm.getSubjectNo();
			Integer totalSubjects = evaStudentFeedbackForm.getTotalSubjects();
			//for displaying next teacher and subject details
			EvaluationStudentFeedbackHandler.getInstance().getTeacherNameAndSubjectName(evaStudentFeedbackForm);
			List<EvaStudentFeedBackQuestionTo> questionListTo = EvaluationStudentFeedbackHandler.getInstance().getQuestionList(evaStudentFeedbackForm);
			evaStudentFeedbackForm.setQuestionListTo(questionListTo);
			//
			int countSubjects=1;
			if(totalSubjects.equals(subjectNo)){
				// by bhargav for subject Evalution
				List<TeacherClassSubjectTO> list = evaStudentFeedbackForm.getTeacherClassSubjectToList();
				Map<Integer,TeacherClassSubjectTO> map=new LinkedHashMap<Integer, TeacherClassSubjectTO>();
				if(list != null && !list.isEmpty()){
					Iterator<TeacherClassSubjectTO> iterator = list.iterator();					
					while (iterator.hasNext()) {
						TeacherClassSubjectTO teacherClassSubjectTO = (TeacherClassSubjectTO) iterator .next();
						if(!map.containsKey(teacherClassSubjectTO.getSubjectId())){
							teacherClassSubjectTO.setSubjectNo(countSubjects);
							teacherClassSubjectTO.setDone(false);
							countSubjects++;
						  map.put(teacherClassSubjectTO.getSubjectId(), teacherClassSubjectTO);
						}
					}
				}
				 List<TeacherClassSubjectTO> studentSubjectWiseFeedbackList = new ArrayList(map.values());
				 evaStudentFeedbackForm.setStudentFeedbackSubjectWiseTotalSubjects(studentSubjectWiseFeedbackList.size());
				 evaStudentFeedbackForm.setStudentFeedbackSubjectWise(studentSubjectWiseFeedbackList);
				return mapping.findForward(CMSConstants.EVAL_STU_SUBWISE_FEEDBACK_DETAILS);
			}
			evaStudentFeedbackForm.setRemarks(null);
			evaStudentFeedbackForm.setAdditionalInfo(null);
		} catch (BusinessException businessException) {
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			evaStudentFeedbackForm.setErrorMessage(msg);
			evaStudentFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
			return mapping.findForward(CMSConstants.FACULTY_EVAL_ANSWERING_CHECK);
		}
	
	public ActionForward getExitEvaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EvaluationStudentFeedbackForm evaStudentFeedbackForm = (EvaluationStudentFeedbackForm)form;
		setUserId(request, evaStudentFeedbackForm);
		try{
			EvaluationStudentFeedbackHandler.getInstance().getTeacherNameAndSubjectName(evaStudentFeedbackForm);
			//Map<String,List<ExitEvaluationQuestionTo>> questionMapTo = EvaluationStudentFeedbackHandler.getInstance().getExitEvalQuestionMap(evaStudentFeedbackForm);
			List<ExitEvaluationQuestionTo> questionListTo=EvaluationStudentFeedbackHandler.getInstance().getExitEvalQuestionList(evaStudentFeedbackForm);
			evaStudentFeedbackForm.setExitEvalQuestionList(questionListTo);
		}catch (BusinessException businessException) {
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			evaStudentFeedbackForm.setErrorMessage(msg);
			evaStudentFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EXIT_EVAL_ANSWERING_CHECK);
	}
	
	public ActionForward submitExitEvaluationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		EvaluationStudentFeedbackForm evaStudentFeedbackForm = (EvaluationStudentFeedbackForm)form;
		setUserId(request, evaStudentFeedbackForm);
		HttpSession session = request.getSession(true);
		try{
		
			int sessionId = (Integer) session.getAttribute("SESSIONID");
			boolean isExitEval=(Boolean)session.getAttribute("ISEXITEVAL");
			evaStudentFeedbackForm.setSessionId(sessionId);
			evaStudentFeedbackForm.setIsExitEval(isExitEval);		
			boolean isAdded = EvaluationStudentFeedbackHandler.getInstance().submitExitEvaluationList(evaStudentFeedbackForm);
			if(isAdded){
				evaStudentFeedbackForm.setFacultyEvaluationTo(null);
				evaStudentFeedbackForm.setRemarks(null);
				evaStudentFeedbackForm.setAdditionalInfo(null);
				return mapping.findForward(CMSConstants.EVAL_STU_FEEDBACK_SUCCESSFUL);
			}else{
				evaStudentFeedbackForm.setRemarks(null);
				evaStudentFeedbackForm.setAdditionalInfo(null);
				return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK_ALREADY_EXIST);
			}

		} catch (BusinessException businessException) {
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			evaStudentFeedbackForm.setErrorMessage(msg);
			evaStudentFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
	}

	public ActionForward getSubjectWiseAnsweringCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EvaluationStudentFeedbackForm evaStudentFeedbackForm = (EvaluationStudentFeedbackForm)form;
		setUserId(request, evaStudentFeedbackForm);
		try{
			EvaluationStudentFeedbackHandler.getInstance().getSubjectName(evaStudentFeedbackForm);
			List<EvaStudentFeedBackQuestionTo> questionListTo = EvaluationStudentFeedbackHandler.getInstance().getQuestionListForSubjectEvalution(evaStudentFeedbackForm);
			evaStudentFeedbackForm.setQuestionListSubjectWiseTo(questionListTo);
		}catch (BusinessException businessException) {
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			evaStudentFeedbackForm.setErrorMessage(msg);
			evaStudentFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EVAL_STU_SUBWISE_FEEDBACK);
	}
	
	public ActionForward submitFacultyAndAubjectWiseEvaluationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		EvaluationStudentFeedbackForm evaStudentFeedbackForm = (EvaluationStudentFeedbackForm)form;
		setUserId(request, evaStudentFeedbackForm);
		HttpSession session = request.getSession(true);
		try{
			evaStudentFeedbackForm = EvaluationStudentFeedbackHandler.getInstance().saveEvaluationFacultyDetailsSubjectWiseToList(evaStudentFeedbackForm);
			Integer subjectNo = evaStudentFeedbackForm.getSubjectNo();
			Integer totalSubjects = evaStudentFeedbackForm.getStudentFeedbackSubjectWiseTotalSubjects();
			//for displaying next teacher and subject details
			EvaluationStudentFeedbackHandler.getInstance().getSubjectName(evaStudentFeedbackForm);
			List<EvaStudentFeedBackQuestionTo> questionListTo = EvaluationStudentFeedbackHandler.getInstance().getQuestionListForSubjectEvalution(evaStudentFeedbackForm);
			evaStudentFeedbackForm.setQuestionListSubjectWiseTo(questionListTo);
			//
			if(totalSubjects.equals(subjectNo)){
				int sessionId = (Integer) session.getAttribute("SESSIONID");
				boolean isExitEval=(Boolean)session.getAttribute("ISEXITEVAL");
				evaStudentFeedbackForm.setSessionId(sessionId);
				evaStudentFeedbackForm.setIsExitEval(isExitEval);
				if(!EvaluationStudentFeedbackHandler.getInstance().checkExitEvalIsAlreadyExist(evaStudentFeedbackForm) && evaStudentFeedbackForm.getIsExitEval()){
					return mapping.findForward(CMSConstants.EXIT_EVALUATION);
				}
				boolean isAdded = EvaluationStudentFeedbackHandler.getInstance().submitEvaluationFacultyList(evaStudentFeedbackForm);
				System.out.println("faculty eval----"+isAdded);
				
				if(isAdded){
					evaStudentFeedbackForm.setFacultyEvaluationTo(null);
					return mapping.findForward(CMSConstants.EVAL_STU_FEEDBACK_SUCCESSFUL);
				}else{
					return mapping.findForward(CMSConstants.INIT_EVAL_STU_FEEDBACK_ALREADY_EXIST);
				}
			}
			evaStudentFeedbackForm.setRemarks(null);
			evaStudentFeedbackForm.setAdditionalInfo(null);
		} catch (BusinessException businessException) {
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			evaStudentFeedbackForm.setErrorMessage(msg);
			evaStudentFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
			return mapping.findForward(CMSConstants.EVAL_STU_SUBWISE_FEEDBACK);
	}
	
}
