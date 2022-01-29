package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.exam.ExamValuationStatusForm;
import com.kp.cms.handlers.admission.StudentSpecialAchivementsHandlers;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.handlers.exam.ExamValuationStatusHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.utilities.CommonUtil;

public class ExamValuationStatusAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExamValuationStatusAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initValuationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initValuationStatus ");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		HttpSession session = request.getSession(false);
		examValuationStatusForm.resetFields();
		try{
			if(session.getAttribute("valuationStatusForDisplay")!=null)
				session.removeAttribute("valuationStatusForDisplay");
			setRequiredDatatoForm(examValuationStatusForm, request);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initValuationStatus ");
		
		return mapping.findForward("initValuationStatus");
	}
	
	/**
	 * @param examValuationStatusForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(ExamValuationStatusForm examValuationStatusForm,HttpServletRequest request) throws Exception{
		Map<Integer, String> examNameMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType(examValuationStatusForm.getExamType(),examValuationStatusForm.getAcademicYear());// getting exam list based on exam Type
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		examValuationStatusForm.setExamNameList(examNameMap);
		
		//List<CourseTO> courseList = ExamValuationStatusHandler.getInstance().getCourseList();
		//examValuationStatusForm.setCourseList(courseList);
		//code added by chandra
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(examValuationStatusForm.getExamType());
		if((examValuationStatusForm.getExamId()==null || examValuationStatusForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			examValuationStatusForm.setExamId(currentExam);
		//Map<Integer, String> courseList=CommonAjaxHandler.getInstance().getCourseByExamName(examValuationStatusForm.getExamId());
		Map<Integer, String> courseList=ExamValuationStatusHandler.getInstance().getCourseByExamName(examValuationStatusForm);
		examValuationStatusForm.setCourceList(courseList);
		Map<Integer, String> termNumberMap = StudentSpecialAchivementsHandlers.getInstance().getTermNumbers();
		HttpSession session = request.getSession();
		session.setAttribute("TermNumberMap", termNumberMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayValuationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered displayValuationStatus input");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		ActionErrors errors=new ActionErrors();
		try{
			if(examValuationStatusForm.getExamId() == null || examValuationStatusForm.getExamId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(examValuationStatusForm.getTermNumber()==null || examValuationStatusForm.getTermNumber().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				saveErrors(request, errors);
			}
			//code added by mehaboob start
			if(examValuationStatusForm.getCourseId()==null || examValuationStatusForm.getCourseId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.course.reqd"));
				saveErrors(request, errors);
			}
			//end
			
			if(errors==null || errors.isEmpty()){
				examValuationStatusForm.setValuationStatus(null);
				ExamValuationStatusHandler.getInstance().displayValuationDetails(examValuationStatusForm);
			}else{
				//code added by mehaboob start to get course by exam
				if(examValuationStatusForm.getExamId()!=null && !examValuationStatusForm.getExamId().isEmpty()){
					Map<Integer, String> courseMap=CommonAjaxExamHandler.getInstance().getCourseByExamName(examValuationStatusForm.getExamId());
					if(!courseMap.isEmpty()){
						examValuationStatusForm.setCourceList(courseMap);
					}
					}
				//end
				return mapping.findForward("initValuationStatus");
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit displayValuationStatus input");
		
		return mapping.findForward("valuationStatus");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewValuationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewValuationDetails input");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		ActionErrors errors=new ActionErrors();
		try{
			if(examValuationStatusForm.getExamId() == null || examValuationStatusForm.getExamId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(examValuationStatusForm.getSubjectId()==null || examValuationStatusForm.getSubjectId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				saveErrors(request, errors);
			}
			if(errors==null || errors.isEmpty()){
				ExamValuationStatusHandler.getInstance().viewValuationDetails(examValuationStatusForm);
			}else{
				return mapping.findForward("initValuationStatus");
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewValuationDetails input");
		
		return mapping.findForward("viewValuationDetails");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewVerificationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewVerificationDetails input");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		ActionErrors errors=new ActionErrors();
		try{
			if(examValuationStatusForm.getExamId() == null || examValuationStatusForm.getExamId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(examValuationStatusForm.getSubjectId()==null || examValuationStatusForm.getSubjectId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				saveErrors(request, errors);
			}
			if(errors==null || errors.isEmpty()){
				ExamValuationStatusHandler.getInstance().viewVerificationDetails(examValuationStatusForm);
			}else{
				return mapping.findForward("initValuationStatus");
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewVerificationDetails input");
		
		return mapping.findForward("viewValuationDetails");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered saveDetails input");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		setUserId(request, examValuationStatusForm);
		ActionMessages messages = new ActionMessages();
		try{
			List<ExamValuationStatusTO> valuationStatus = examValuationStatusForm.getValuationStatus();
			boolean save = ExamValuationStatusHandler.getInstance().saveProcessCompletedDetails(valuationStatus,examValuationStatusForm);
			if(save){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.valuation.process.success"));
				saveMessages(request, messages);
			}
			//examValuationStatusForm.resetFields();
			examValuationStatusForm.resetFieldsForSaveDetails();   //added by chandra
			setRequiredDatatoForm(examValuationStatusForm, request);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit saveDetails input");
		return mapping.findForward("initValuationStatus");
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.actions.BaseDispatchAction#setUserId(javax.servlet.http.HttpServletRequest, com.kp.cms.forms.BaseActionForm)
	 */
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initNewValuationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered initNewValuationStatus ");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		HttpSession session = request.getSession(false);
		examValuationStatusForm.resetFields();
		try{
			if(session.getAttribute("valuationStatusForDisplay")!=null)
				session.removeAttribute("valuationStatusForDisplay");
			setRequiredDatatoForm1(examValuationStatusForm, request);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initNewValuationStatus ");
		
		return mapping.findForward("initNewValuationStatus");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newDisplayValuationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered displayValuationStatus input");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		ActionErrors errors=new ActionErrors();
		HttpSession session = request.getSession(false);
		if(session.getAttribute("valuationStatusForDisplay")==null){
		try{
			if(examValuationStatusForm.getExamId() == null || examValuationStatusForm.getExamId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(!examValuationStatusForm.getFinalYears()&& (examValuationStatusForm.getTermNumber()==null || examValuationStatusForm.getTermNumber().isEmpty())){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				saveErrors(request, errors);
			}
			//code added by mehaboob start
			if(examValuationStatusForm.getCourseId()==null || examValuationStatusForm.getCourseId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.course.reqd"));
				saveErrors(request, errors);
			}
			//end
			if(errors==null || errors.isEmpty()){
				examValuationStatusForm.setValuationStatus(null);
				ExamValuationStatusHandler.getInstance().displayValuationDetails(examValuationStatusForm);
			}else{
				//code added by mehaboob to get course by examId start
				if(examValuationStatusForm.getExamId()!=null && !examValuationStatusForm.getExamId().isEmpty()){
				Map<Integer, String> courseMap=CommonAjaxExamHandler.getInstance().getCourseByExamName(examValuationStatusForm.getExamId());
				if(!courseMap.isEmpty()){
					examValuationStatusForm.setCourceList(courseMap);
				}
				}
				//end
				return mapping.findForward("initNewValuationStatus");
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit displayValuationStatus input");
		session.setAttribute("valuationStatusForDisplay", examValuationStatusForm.getValuationStatus());
		}
		return mapping.findForward("newDisplayValuationStatus");
	}
	/**
	 * @param examValuationStatusForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm1(ExamValuationStatusForm examValuationStatusForm,HttpServletRequest request) throws Exception{
		Map<Integer, String> examNameMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType(examValuationStatusForm.getExamType(),examValuationStatusForm.getAcademicYear());// getting exam list based on exam Type
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		examValuationStatusForm.setExamNameList(examNameMap);
		
		//List<CourseTO> courseList = ExamValuationStatusHandler.getInstance().getCourseList();
		//examValuationStatusForm.setCourseList(courseList);
		//code added by chandra
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(examValuationStatusForm.getExamType());
		if((examValuationStatusForm.getExamId()==null || examValuationStatusForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			examValuationStatusForm.setExamId(currentExam);
		//Map<Integer, String> courseList=CommonAjaxHandler.getInstance().getCourseByExamName(examValuationStatusForm.getExamId());
		Map<Integer, String> courseList=ExamValuationStatusHandler.getInstance().getCourseByExamName(examValuationStatusForm);
		examValuationStatusForm.setCourceList(courseList);
		
		Map<Integer, String> termNumberMap = StudentSpecialAchivementsHandlers.getInstance().getTermNumbers();
		HttpSession session = request.getSession();
		session.setAttribute("TermNumberMap", termNumberMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newViewValuationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewValuationDetails input");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		ActionErrors errors=new ActionErrors();
		try{
			if(examValuationStatusForm.getExamId() == null || examValuationStatusForm.getExamId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(examValuationStatusForm.getSubjectId()==null || examValuationStatusForm.getSubjectId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				saveErrors(request, errors);
			}
			if(errors==null || errors.isEmpty()){
				ExamValuationStatusHandler.getInstance().viewValuationDetails(examValuationStatusForm);
			}else{
				return mapping.findForward("initValuationStatus");
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewValuationDetails input");
		
		return mapping.findForward("newViewValuationDetails");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newViewVerificationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewVerificationDetails input");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		ActionErrors errors=new ActionErrors();
		try{
			if(examValuationStatusForm.getExamId() == null || examValuationStatusForm.getExamId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(examValuationStatusForm.getSubjectId()==null || examValuationStatusForm.getSubjectId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				saveErrors(request, errors);
			}
			if(errors==null || errors.isEmpty()){
				ExamValuationStatusHandler.getInstance().viewVerificationDetails(examValuationStatusForm);
			}else{
				return mapping.findForward("initValuationStatus");
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewVerificationDetails input");
		
		return mapping.findForward("newViewValuationDetails");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetForNewValuationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {

log.info("Entered initNewValuationStatus ");
ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
examValuationStatusForm.resetFields();
HttpSession session = request.getSession(false);
try{
	if(session.getAttribute("valuationStatusForDisplay")!=null)
		session.removeAttribute("valuationStatusForDisplay");
	setRequiredDatatoForm1(examValuationStatusForm, request);
}catch (Exception e) {
	String msg = super.handleApplicationException(e);
	examValuationStatusForm.setErrorMessage(msg);
	examValuationStatusForm.setErrorStack(e.getMessage());
	return mapping.findForward(CMSConstants.ERROR_PAGE);
}
log.info("Exit initNewValuationStatus ");

return mapping.findForward("initNewValuationStatus");
}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetForValuationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initValuationStatus ");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		examValuationStatusForm.resetFields();
		HttpSession session = request.getSession(false);
		try{
			if(session.getAttribute("valuationStatusForDisplay")!=null)
				session.removeAttribute("valuationStatusForDisplay");
			setRequiredDatatoForm(examValuationStatusForm, request);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initValuationStatus ");
		
		return mapping.findForward("initValuationStatus");
	}
	public ActionForward cancelForValuationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initValuationStatus ");
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		HttpSession session = request.getSession(false);
		try{
			if(session.getAttribute("valuationStatusForDisplay")!=null)
				session.removeAttribute("valuationStatusForDisplay");
			setRequiredDatatoForm(examValuationStatusForm, request);
			examValuationStatusForm.resetFieldsForSaveDetails();
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initValuationStatus ");
		
		return mapping.findForward("initValuationStatus");
	}
	
	
	public ActionForward cancelForNewValuationStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {

log.info("Entered initNewValuationStatus ");
ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
HttpSession session = request.getSession(false);
try{
	if(session.getAttribute("valuationStatusForDisplay")!=null)
		session.removeAttribute("valuationStatusForDisplay");
	setRequiredDatatoForm1(examValuationStatusForm, request);
	examValuationStatusForm.resetFieldsForSaveDetails();
}catch (Exception e) {
	String msg = super.handleApplicationException(e);
	examValuationStatusForm.setErrorMessage(msg);
	examValuationStatusForm.setErrorStack(e.getMessage());
	return mapping.findForward(CMSConstants.ERROR_PAGE);
}
log.info("Exit initNewValuationStatus ");

return mapping.findForward("initNewValuationStatus");
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMismatchDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamValuationStatusForm examValuationStatusForm  = (ExamValuationStatusForm)form;
		List<StudentTO> misMatchStuList = new ArrayList<StudentTO>();
		try{
			/*int courseId = 0;
			
			if(examValuationStatusForm.getCourseId()!=null && !examValuationStatusForm.getCourseId().isEmpty()){
				courseId = Integer.parseInt(examValuationStatusForm.getCourseId());
			}*/
			String subjectId_EvaluatorTypeId = null;
			if(examValuationStatusForm.getEvaluatorTypeId()!=null && !examValuationStatusForm.getEvaluatorTypeId().isEmpty()){
				subjectId_EvaluatorTypeId = examValuationStatusForm.getSubjectId()+"_"+examValuationStatusForm.getEvaluatorTypeId();
			} else{
				subjectId_EvaluatorTypeId = examValuationStatusForm.getSubjectId();
			}
			Map<Integer,Map<String,List<StudentTO>>> misMatchMap = examValuationStatusForm.getMisMatchStudentList();
			String subjectNameAndCode = null;
			if(misMatchMap!=null && !misMatchMap.isEmpty()){
				Map<String,List<StudentTO>> mismatchSubjectMap = misMatchMap.get(Integer.parseInt(examValuationStatusForm.getCourseId()));
				if(mismatchSubjectMap!=null && !mismatchSubjectMap.isEmpty()){
					List<StudentTO> misMatchStudentDetails = mismatchSubjectMap.get(subjectId_EvaluatorTypeId);
					if(misMatchStudentDetails!=null && !misMatchStudentDetails.isEmpty()){
						Iterator<StudentTO> iterator  =misMatchStudentDetails.iterator();
						while (iterator.hasNext()) {
							StudentTO studentTO = (StudentTO) iterator.next();
							subjectNameAndCode = studentTO.getSubjectName();
							misMatchStuList.add(studentTO);
						}
					}
				}
			}
			examValuationStatusForm.setSubjectName(subjectNameAndCode);
			Collections.sort(misMatchStuList);
			examValuationStatusForm.setStudents(misMatchStuList);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValuationStatusForm.setErrorMessage(msg);
			examValuationStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("viewMisMatchMarksStudentDetails");
	}
}