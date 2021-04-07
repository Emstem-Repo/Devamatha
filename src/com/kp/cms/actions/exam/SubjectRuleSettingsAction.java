package com.kp.cms.actions.exam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.exam.SubjectRuleSettingsHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction;
import com.kp.cms.transactionsimpl.exam.SubjectRuleSettingsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class SubjectRuleSettingsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SubjectRuleSettingsAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSubjectRuleSet(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initSubjectRuleSet input");
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		subjectRuleSettingsForm.resetFields();
		setRequiredDatatoForm(subjectRuleSettingsForm,request);
		log.info("Exit initSubjectRuleSet input");
		
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}

	/**
	 * setting the data to form
	 * @param subjectRuleSettingsForm
	 * @param request
	 */
	private void setRequiredDatatoForm(SubjectRuleSettingsForm subjectRuleSettingsForm,HttpServletRequest request) throws Exception {
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			subjectRuleSettingsForm.setProgramTypeList(programTypeList);
		}
		Map<Integer,String> courseMap = new HashMap<Integer,String>();
		if(subjectRuleSettingsForm.getProgramTypeId()!=null && !subjectRuleSettingsForm.getProgramTypeId().isEmpty()){
			List<KeyValueTO> list = CommonAjaxHandler.getInstance().getCoursesByProgramTypes1(subjectRuleSettingsForm.getProgramTypeId());
			if(list!=null && !list.isEmpty()){
				Iterator<KeyValueTO> itr=list.iterator();
				while (itr.hasNext()) {
					KeyValueTO keyValueTO = (KeyValueTO) itr.next();
					courseMap.put(keyValueTO.getId(),keyValueTO.getDisplay());
				}
			}
			request.setAttribute("coursesMap",courseMap);
		}
	}
	
	
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		 ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		String[] str = request.getParameterValues("courseIds");
		if(str==null || str.length==0){
			if (errors.get("admissionFormForm.courseId.required") != null&& !errors.get("admissionFormForm.courseId.required").hasNext()) {
				errors.add("admissionFormForm.courseId.required",new ActionError("admissionFormForm.courseId.required"));
			}
		}
		if (errors.isEmpty()) {
			try {
				List<ExamSubjectRuleSettingsBO> duplicateList=SubjectRuleSettingsHandler.getInstance().isDuplicateCheck(subjectRuleSettingsForm);
				if(duplicateList!=null && !duplicateList.isEmpty()){
					Iterator<ExamSubjectRuleSettingsBO> itr=duplicateList.iterator();
					StringBuilder courseName=new StringBuilder();
					while (itr.hasNext()) {
						ExamSubjectRuleSettingsBO examSubjectRuleSettingsBO = (ExamSubjectRuleSettingsBO) itr.next();
						courseName.append(" ").append(examSubjectRuleSettingsBO.getExamCourseUtilBO().getCourseName());
					}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subject.rule.settings.isDuplicate",courseName));
				}
				List<SubjectGroup> list=SubjectRuleSettingsHandler.getInstance().getSubjectsForInput(subjectRuleSettingsForm);
				if(list==null || list.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subject.rule.settings.subjectgroup.notDefined",subjectRuleSettingsForm.getAcademicYear()));
				}
				if(errors.isEmpty()){
					setDataToFormInAddMode(subjectRuleSettingsForm);
					subjectRuleSettingsForm.setMethodType("add");
					setNamesToForm(subjectRuleSettingsForm);
				}else {
					addErrors(request, errors);
					setRequiredDatatoForm(subjectRuleSettingsForm, request);			
					log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_ADD);
	}

	/**
	 * @param subjectRuleSettingsForm
	 * @throws Exception
	 */
	private void setNamesToForm(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		ISubjectRuleSettingsTransaction transaction=SubjectRuleSettingsTransactionImpl.getInstance();
		StringBuilder intType=new StringBuilder();
		String[] tempArray=subjectRuleSettingsForm.getCourseIds();
		for(int i=0;i<tempArray.length;i++){
			 intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		String coursename=transaction.getNamesForBos("Course",intType.toString());
		subjectRuleSettingsForm.setCourseName(coursename);
		String programName=transaction.getNamesForBos("ProgramType", subjectRuleSettingsForm.getProgramTypeId());
		subjectRuleSettingsForm.setProgramTypeName(programName);
		
		if(subjectRuleSettingsForm.getSubjectId()!=null && !subjectRuleSettingsForm.getSubjectId().isEmpty()){
			String subjectName= transaction.getNamesForBos("Subject", subjectRuleSettingsForm.getSubjectId());
			subjectRuleSettingsForm.setSubject(subjectName);
		}
		
		if(subjectRuleSettingsForm.getCourseId()!=null && !subjectRuleSettingsForm.getCourseId().isEmpty()){
			String courseName= transaction.getNamesForBos("Course",subjectRuleSettingsForm.getCourseId());
			subjectRuleSettingsForm.setCourse(courseName);
		}
	}

	/**
	 * @param subjectRuleSettingsForm
	 * @throws Exception
	 */
	private void setDataToFormInAddMode(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		ExamSubjectRuleSettingsTheoryInternalTO esto=new ExamSubjectRuleSettingsTheoryInternalTO();
		List<ExamSubjectRuleSettingsSubInternalTO> subInternalTOs=SubjectRuleSettingsHandler.getInstance().getAllExamSubjectRuleSettingsSubInternals();
		esto.setSubInternalList(subInternalTOs);
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		esto.setAttendanceTypeList(attendanceTypeList);
		List<ExamSubjectRuleSettingsAssignmentTO> assList=SubjectRuleSettingsHandler.getInstance().getAllAssignment();
		esto.setAssignmentList(assList);
		
		ExamSubjectRuleSettingsTheoryESETO teseTo=new ExamSubjectRuleSettingsTheoryESETO();
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScript=SubjectRuleSettingsHandler.getInstance().getAllMultipleAnswerScript(); 
		teseTo.setMultipleAnswerScriptList(multipleAnswerScript);
		List<ExamSubjectRuleSettingsSubInternalTO> practicalsubInternalTOs=SubjectRuleSettingsHandler.getInstance().getAllExamSubjectRuleSettingsSubInternals();
		ExamSubjectRuleSettingsPracticalInternalTO pesto=new ExamSubjectRuleSettingsPracticalInternalTO();
		pesto.setSubInternalList(practicalsubInternalTOs);
		pesto.setAttendanceTypeList(attendanceTypeList);
		pesto.setAssignmentList(assList);
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> practicalmultipleAnswerScript=SubjectRuleSettingsHandler.getInstance().getAllMultipleAnswerScript();
		ExamSubjectRuleSettingsPracticalESETO peseTo=new ExamSubjectRuleSettingsPracticalESETO();
		peseTo.setMultipleAnswerScriptList(practicalmultipleAnswerScript);
		
		subjectRuleSettingsForm.setEsto(esto);
		subjectRuleSettingsForm.setTeseTo(teseTo);
		subjectRuleSettingsForm.setPesto(pesto);
		subjectRuleSettingsForm.setPeseTo(peseTo);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward theoryESE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered theoryESE input");
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionErrors errors=new ActionErrors();
		validateTheoryInternal(subjectRuleSettingsForm,errors);
		if(!errors.isEmpty()){
			saveErrors(request, errors);
			// To Solve the Check box problem
			resetCheckBoxForTheoryInternal(subjectRuleSettingsForm);
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_ADD);
		}
		log.info("Exit theoryESE input");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_ESE);
	}
	/**
	 * @param subjectRuleSettingsForm
	 */
	private void resetCheckBoxForTheoryInternal(SubjectRuleSettingsForm subjectRuleSettingsForm) {
		// TODO Auto-generated method stub
		ExamSubjectRuleSettingsTheoryInternalTO esto= subjectRuleSettingsForm.getEsto();
		esto.setDupassignmentChecked(esto.getAssignmentChecked());
		esto.setDupattendanceChecked(esto.getAttendanceChecked());
		esto.setDupCoCurricular(esto.getCoCurricularAttendance());
		esto.setDupLeave(esto.getLeaveAttendance());
		esto.setDupsubInternalChecked(esto.getSubInternalChecked());
		esto.setAssignmentChecked(null);
		esto.setAttendanceChecked(null);
		esto.setCoCurricularAttendance(null);
		esto.setSubInternalChecked(null);
		esto.setLeaveAttendance(null);
		subjectRuleSettingsForm.setEsto(esto);
	}

	/**
	 * @param subjectRuleSettingsForm
	 * @param errors
	 */
	private void validateTheoryInternal(SubjectRuleSettingsForm subjectRuleSettingsForm, ActionErrors errors) throws Exception {
		ExamSubjectRuleSettingsTheoryInternalTO esto= subjectRuleSettingsForm.getEsto();
		boolean checkInternal=false;
		if((esto.getFinalInternalMinimumMarks()!=null && !esto.getFinalInternalMinimumMarks().isEmpty()) || (esto.getFinalEntryMaximumMarks()!=null && !esto.getFinalEntryMaximumMarks().isEmpty())
				||(esto.getFinalInternalMaximumMarks()!=null && !esto.getFinalInternalMaximumMarks().isEmpty())){
			checkInternal=true;
			if((esto.getSubInternalChecked()==null || esto.getSubInternalChecked().isEmpty()) && (esto.getAttendanceChecked()==null || esto.getAttendanceChecked().isEmpty()) && (esto.getAssignmentChecked()==null ||esto.getAssignmentChecked().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectRuleSettings.finalInternalMark.required"));
			}
		}
		if((esto.getSubInternalChecked()!=null && !esto.getSubInternalChecked().isEmpty()) || (esto.getAttendanceChecked()!=null && !esto.getAttendanceChecked().isEmpty()) || (esto.getAssignmentChecked()!=null && !esto.getAssignmentChecked().isEmpty())){
			checkInternal=true;
			if((esto.getFinalInternalMinimumMarks()==null || esto.getFinalInternalMinimumMarks().isEmpty()) && (esto.getFinalEntryMaximumMarks()==null || esto.getFinalEntryMaximumMarks().isEmpty())
				&& (esto.getFinalInternalMaximumMarks()==null || esto.getFinalInternalMaximumMarks().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectRuleSettings.finalInternalMark.required1"));
			}
		}
		
		if(esto.getAttendanceTypeId()!=null && !esto.getAttendanceTypeId().isEmpty()){
			checkInternal=true;
			if((esto.getLeaveAttendance()==null || esto.getLeaveAttendance().isEmpty()) && (esto.getCoCurricularAttendance()==null || esto.getCoCurricularAttendance().isEmpty()) ){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.attendance.attendanceType.required"));
			} 
		}
		
		if((esto.getLeaveAttendance()!=null && !esto.getLeaveAttendance().isEmpty()) || (esto.getCoCurricularAttendance()!=null && !esto.getCoCurricularAttendance().isEmpty()) ){
			checkInternal=true;
			if(esto.getAttendanceTypeId()==null || esto.getAttendanceTypeId().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.attendance.attendanceType.required1"));
			} 
		}
		if(esto.getFinalInternalMinimumMarks()!=null && !esto.getFinalInternalMinimumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(esto.getFinalInternalMinimumMarks())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.minimum.marks.numeric"));
			}else{
				checkInternal=true;
			}
		}
		if(esto.getFinalEntryMaximumMarks()!=null && !esto.getFinalEntryMaximumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(esto.getFinalEntryMaximumMarks())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.entry.maximum.marks.numeric"));
			}else{
				checkInternal=true;
			}
		}
		if(esto.getFinalInternalMaximumMarks()!=null && !esto.getFinalInternalMaximumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(esto.getFinalInternalMaximumMarks())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.maximum.marks.numeric"));
			}else{
				checkInternal=true;
			}
		}
		
		if(esto.getAttendanceTypeId()!=null && !esto.getAttendanceTypeId().isEmpty()){
			checkInternal=true;
			if(esto.getAttendanceChecked()==null || esto.getAttendanceChecked().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.attendance.finalInternalmarks"));
			}
		}
		double total=0.0;
		double individual=0.0;
		
		List<ExamSubjectRuleSettingsSubInternalTO> subInternalList=esto.getSubInternalList();
		if(subInternalList!=null && !subInternalList.isEmpty()){
			Iterator<ExamSubjectRuleSettingsSubInternalTO> itr=subInternalList.iterator();
			while (itr.hasNext()) {
				ExamSubjectRuleSettingsSubInternalTO to = (ExamSubjectRuleSettingsSubInternalTO) itr.next();
				if(to.getMinimumMarks()!=null && !to.getMinimumMarks().isEmpty()){
					if(!CommonUtil.isValidDecimal(to.getMinimumMarks())){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.minimum.marks.numeric"));
					}else{
						checkInternal=true;
					}
				}
				if(to.getMaximumMarks()!=null && !to.getMaximumMarks().isEmpty()){
					if(!CommonUtil.isValidDecimal(to.getMaximumMarks())){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.maximum.marks.numeric"));
					}else{
						checkInternal=true;
					}
				}
				if(to.getEntryMaximumMarks()!=null && !to.getEntryMaximumMarks().isEmpty() ){
					if(!CommonUtil.isValidDecimal(to.getEntryMaximumMarks())){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.entry.maximum.marks.numeric"));
					}else{
						checkInternal=true;
						individual=individual+Double.parseDouble(to.getEntryMaximumMarks());
					}
				}
			}
		}
		if(esto.getTotalentryMaximumMarks()!=null && !esto.getTotalentryMaximumMarks().isEmpty()){
			checkInternal=true;
			if( CommonUtil.isValidDecimal(esto.getTotalentryMaximumMarks())){
				total=Double.parseDouble(esto.getTotalentryMaximumMarks());
				if(individual!=total){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.entry.total.maximum.marks.isNotValid"));
				}
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.entry.total.maximum.marks.numeric"));
			}
		}
		if(esto.getTotalMinimummumMarks()!=null && !esto.getTotalMinimummumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(esto.getTotalMinimummumMarks())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.total.minimum.marks.numeric"));
			}else{
				checkInternal=true;
			}
		}
		if(esto.getTotalMaximumMarks()!=null && !esto.getTotalMaximumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(esto.getTotalMaximumMarks())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.total.maximum.marks.numeric"));
			}else{
				checkInternal=true;
			}
		}
		subjectRuleSettingsForm.setCheckInternal(checkInternal);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward practicalInternal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered theoryESE input");
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionErrors errors=new ActionErrors();
		validateTheoryESE(subjectRuleSettingsForm,errors);
		if(!errors.isEmpty()){
			// To Solve the Check box problem
			resetCheckBoxForTheoryEse(subjectRuleSettingsForm);
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_ESE);
		}
		log.info("Exit theoryESE input");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_PRACTICAL_INTERNAL_PRACTICAL);
	}
	
	/**
	 * @param subjectRuleSettingsForm
	 * @throws Exception
	 */
	private void resetCheckBoxForTheoryEse(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		ExamSubjectRuleSettingsTheoryESETO teseTo=subjectRuleSettingsForm.getTeseTo();
		teseTo.setDupmultipleAnswerScriptsChecked(teseTo.getMultipleAnswerScriptsChecked());
		teseTo.setDupmultipleEvaluatorChecked(teseTo.getMultipleEvaluatorChecked());
		teseTo.setDupregularTheoryESEChecked(teseTo.getRegularTheoryESEChecked());
		teseTo.setRegularTheoryESEChecked(null);
		teseTo.setMultipleAnswerScriptsChecked(null);
		teseTo.setMultipleEvaluatorChecked(null);
		teseTo.setDupSupplementaryChecked(teseTo.getSupplementaryChecked());
		teseTo.setSupplementaryChecked(null);
		subjectRuleSettingsForm.setTeseTo(teseTo);
	}

	/**
	 * @param subjectRuleSettingsForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateTheoryESE(SubjectRuleSettingsForm subjectRuleSettingsForm, ActionErrors errors) throws Exception{
		ExamSubjectRuleSettingsTheoryESETO teseTo=subjectRuleSettingsForm.getTeseTo();
		boolean checkTheory=false;
		if(teseTo.getRegularTheoryESEChecked()!=null && !teseTo.getRegularTheoryESEChecked().isEmpty() && teseTo.getRegularTheoryESEChecked().equals("on")){
			checkTheory=true;
			if((teseTo.getMultipleAnswerScriptsChecked()!=null && !teseTo.getMultipleAnswerScriptsChecked().isEmpty()) ||
					(teseTo.getMultipleEvaluatorChecked()!=null && !teseTo.getMultipleEvaluatorChecked().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.regular.checked.isnotValid"));
			}
		}
		if(teseTo.getMinimumMarksTheoryESE()!=null && !teseTo.getMinimumMarksTheoryESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(teseTo.getMinimumMarksTheoryESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Minimum Marks"));
			else
				checkTheory=true;
		}
		if(teseTo.getMaximumMarksTheoryESE()!=null && !teseTo.getMaximumMarksTheoryESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(teseTo.getMaximumMarksTheoryESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Maximum Marks"));
			else
				checkTheory=true;
		}
		if(teseTo.getMaximumEntryMarksTheoryESE()!=null && !teseTo.getMaximumEntryMarksTheoryESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(teseTo.getMaximumEntryMarksTheoryESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Entry Maximum Marks"));
			else
				checkTheory=true;
		}
		if(checkTheory){
			if((teseTo.getMultipleAnswerScriptsChecked()==null || teseTo.getMultipleAnswerScriptsChecked().isEmpty()) &&
					(teseTo.getMultipleEvaluatorChecked()==null || teseTo.getMultipleEvaluatorChecked().isEmpty()) && 
					(teseTo.getRegularTheoryESEChecked()==null || teseTo.getRegularTheoryESEChecked().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Regular or Multiple Answer Script or Multiple Evaluator should be selected"));
			}
		}
		if(teseTo.getMinimumTheoryFinalMarksTheoryESE()!=null && !teseTo.getMinimumTheoryFinalMarksTheoryESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(teseTo.getMinimumTheoryFinalMarksTheoryESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Final Minimum Marks"));
			else
				checkTheory=true;
		}
		if(teseTo.getMaximumTheoryFinalMarksTheoryESE()!=null && !teseTo.getMaximumTheoryFinalMarksTheoryESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(teseTo.getMaximumTheoryFinalMarksTheoryESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Final Maximum Marks"));
			else
				checkTheory=true;
		}
		if((teseTo.getMultipleAnswerScriptsChecked()!=null && !teseTo.getMultipleAnswerScriptsChecked().isEmpty()) ||
				(teseTo.getMultipleEvaluatorChecked()!=null && !teseTo.getMultipleEvaluatorChecked().isEmpty())){
			checkTheory=true;
			if(teseTo.getRegularTheoryESEChecked()!=null && !teseTo.getRegularTheoryESEChecked().isEmpty() && teseTo.getRegularTheoryESEChecked().equals("on")){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multiple.evaluator.checked.isnotValid"));
			}
		}
		
		if(teseTo.getMultipleAnswerScriptsChecked()!=null && !teseTo.getMultipleAnswerScriptsChecked().isEmpty()){
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList=teseTo.getMultipleAnswerScriptList();
			if(multipleAnswerScriptList==null || multipleAnswerScriptList.isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multipleanswer.script.required"));
			}else{
				boolean isEntered=false;
				Iterator<ExamSubjectRuleSettingsMultipleAnswerScriptTO> itr=multipleAnswerScriptList.iterator();
				while (itr.hasNext()) {
					ExamSubjectRuleSettingsMultipleAnswerScriptTO to = (ExamSubjectRuleSettingsMultipleAnswerScriptTO) itr.next();
					if(to.getMultipleAnswerScriptTheoryESE()!=null && !to.getMultipleAnswerScriptTheoryESE().isEmpty()){
						isEntered=true;
						if(!CommonUtil.isValidDecimal(to.getMultipleAnswerScriptTheoryESE())){
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multipleanswer.script.numeric"));
						}else{
							checkTheory=true;
						}
					}
				}
				if(!isEntered){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multipleanswer.script.required"));
				}
			} 
		}
		if(teseTo.getMultipleEvaluatorChecked()!=null && !teseTo.getMultipleEvaluatorChecked().isEmpty()){
			if(teseTo.getNoOfEvaluations()==null || teseTo.getNoOfEvaluations().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","No. of Evaluation"));
			}else{
				if(!CommonUtil.isValidDecimal(teseTo.getNoOfEvaluations())){
					errors.add(CMSConstants.ERROR,new ActionError("errors.integer","No. of Evaluation"));
				}else{
					int evaluator=Integer.parseInt(teseTo.getNoOfEvaluations());
					int count=0;
					if(teseTo.getEvalId1()!=null && !teseTo.getEvalId1().isEmpty()){
						count++;
						checkTheory=true;
					}
					if(teseTo.getEvalId2()!=null && !teseTo.getEvalId2().isEmpty()){
						count++;
						checkTheory=true;
					}
					if(teseTo.getEvalId3()!=null && !teseTo.getEvalId3().isEmpty()){
						count++;
						checkTheory=true;
					}
					if(teseTo.getEvalId4()!=null && !teseTo.getEvalId4().isEmpty()){
						count++;
						checkTheory=true;
					}
					if(teseTo.getEvalId5()!=null && !teseTo.getEvalId5().isEmpty()){
						count++;
						checkTheory=true;
					}
					if(evaluator!=count){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multiple.evaluator.id.required",count));
					}
				}
			}
			if(teseTo.getSelectTypeOfEvaluation()==null || teseTo.getSelectTypeOfEvaluation().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Type of Evaluation"));
			}
		}
		if(teseTo.getSupplementaryChecked()!=null && !teseTo.getSupplementaryChecked().isEmpty() && teseTo.getSupplementaryChecked().equals("on")){
			if(teseTo.getSupplementaryMaxMarks()==null || teseTo.getSupplementaryMaxMarks().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Supplementary Max Marks"));
			}else{
				if(!CommonUtil.isValidDecimal(teseTo.getSupplementaryMaxMarks())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Supplementary Max Marks"));
				}
			}
			if(teseTo.getSupplementaryMinMarks()==null || teseTo.getSupplementaryMinMarks().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Supplementary Min Marks"));
			}else{
				if(!CommonUtil.isValidDecimal(teseTo.getSupplementaryMinMarks())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Supplementary Min Marks"));
				}
			}
		}
		subjectRuleSettingsForm.setCheckTheory(checkTheory);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward practicalESE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered theoryESE input");
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionErrors errors=new ActionErrors();
		validatePracticalInternal(subjectRuleSettingsForm,errors);
		if(!errors.isEmpty()){
			saveErrors(request, errors);
			// FOr Check Box
			resetCheckBoxForPracticalInternal(subjectRuleSettingsForm);
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_PRACTICAL_INTERNAL_PRACTICAL);
		}
		log.info("Exit theoryESE input");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_PRACTICAL_ESE);
	}
	
	private void resetCheckBoxForPracticalInternal(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		ExamSubjectRuleSettingsPracticalInternalTO pesto=subjectRuleSettingsForm.getPesto();
		pesto.setDupassignmentChecked(pesto.getAssignmentChecked());
		pesto.setDupattendanceChecked(pesto.getAttendanceChecked());
		pesto.setDupCoCurricular(pesto.getCoCurricularAttendance());
		pesto.setDupLeave(pesto.getLeaveAttendance());
		pesto.setDupsubInternalChecked(pesto.getSubInternalChecked());
		pesto.setAssignmentChecked(null);
		pesto.setAttendanceChecked(null);
		pesto.setCoCurricularAttendance(null);
		pesto.setSubInternalChecked(null);
		pesto.setLeaveAttendance(null);
		subjectRuleSettingsForm.setPesto(pesto);
	}

	private void validatePracticalInternal(SubjectRuleSettingsForm subjectRuleSettingsForm, ActionErrors errors) throws Exception{
		ExamSubjectRuleSettingsPracticalInternalTO pesto=subjectRuleSettingsForm.getPesto();
		boolean checkInternal=subjectRuleSettingsForm.isCheckInternal();
		
		if((pesto.getFinalInternalMinimumMarks()!=null && !pesto.getFinalInternalMinimumMarks().isEmpty()) || (pesto.getFinalEntryMaximumMarks()!=null && !pesto.getFinalEntryMaximumMarks().isEmpty())
				||(pesto.getFinalInternalMaximumMarks()!=null && !pesto.getFinalInternalMaximumMarks().isEmpty())){
			checkInternal=true;
			if((pesto.getSubInternalChecked()==null || pesto.getSubInternalChecked().isEmpty()) && (pesto.getAttendanceChecked()==null || pesto.getAttendanceChecked().isEmpty()) && (pesto.getAssignmentChecked()==null ||pesto.getAssignmentChecked().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectRuleSettings.finalInternalMark.required"));
			}
		}
		if((pesto.getSubInternalChecked()!=null && !pesto.getSubInternalChecked().isEmpty()) || (pesto.getAttendanceChecked()!=null && !pesto.getAttendanceChecked().isEmpty()) || (pesto.getAssignmentChecked()!=null && !pesto.getAssignmentChecked().isEmpty())){
			checkInternal=true;
			if((pesto.getFinalInternalMinimumMarks()==null || pesto.getFinalInternalMinimumMarks().isEmpty()) && (pesto.getFinalEntryMaximumMarks()==null || pesto.getFinalEntryMaximumMarks().isEmpty())
				&& (pesto.getFinalInternalMaximumMarks()==null || pesto.getFinalInternalMaximumMarks().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectRuleSettings.finalInternalMark.required1"));
			}
		}
		
		if(pesto.getAttendanceTypeId()!=null && !pesto.getAttendanceTypeId().isEmpty()){
			checkInternal=true;
			if((pesto.getLeaveAttendance()==null || pesto.getLeaveAttendance().isEmpty()) && (pesto.getCoCurricularAttendance()==null || pesto.getCoCurricularAttendance().isEmpty()) ){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.attendance.attendanceType.required"));
			} 
		}
		
		if((pesto.getLeaveAttendance()!=null && !pesto.getLeaveAttendance().isEmpty()) || (pesto.getCoCurricularAttendance()!=null && !pesto.getCoCurricularAttendance().isEmpty()) ){
			checkInternal=true;
			if(pesto.getAttendanceTypeId()==null || pesto.getAttendanceTypeId().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.attendance.attendanceType.required1"));
			} 
		}
		if(pesto.getFinalInternalMinimumMarks()!=null && !pesto.getFinalInternalMinimumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(pesto.getFinalInternalMinimumMarks()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.minimum.marks.numeric"));
			else
				checkInternal=true;
		}
		if(pesto.getFinalEntryMaximumMarks()!=null && !pesto.getFinalEntryMaximumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(pesto.getFinalEntryMaximumMarks()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.entry.maximum.marks.numeric"));
			else
				checkInternal=true;
		}
		if(pesto.getFinalInternalMaximumMarks()!=null && !pesto.getFinalInternalMaximumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(pesto.getFinalInternalMaximumMarks()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.maximum.marks.numeric"));
			else
				checkInternal=true;
		}
		
		if(pesto.getAttendanceTypeId()!=null && !pesto.getAttendanceTypeId().isEmpty()){
			checkInternal=true;
			if(pesto.getAttendanceChecked()==null || pesto.getAttendanceChecked().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.attendance.finalInternalmarks"));
			}
		}
		double total=0.0;
		double individual=0.0;
		
		List<ExamSubjectRuleSettingsSubInternalTO> subInternalList=pesto.getSubInternalList();
		if(subInternalList!=null && !subInternalList.isEmpty()){
			Iterator<ExamSubjectRuleSettingsSubInternalTO> itr=subInternalList.iterator();
			while (itr.hasNext()) {
				ExamSubjectRuleSettingsSubInternalTO to = (ExamSubjectRuleSettingsSubInternalTO) itr.next();
				if(to.getMinimumMarks()!=null && !to.getMinimumMarks().isEmpty()){
					if(!CommonUtil.isValidDecimal(to.getMinimumMarks()))
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.minimum.marks.numeric"));
					else
						checkInternal=true;
				}
				if(to.getMaximumMarks()!=null && !to.getMaximumMarks().isEmpty()){
					if(!CommonUtil.isValidDecimal(to.getMaximumMarks()))
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.maximum.marks.numeric"));
					else
						checkInternal=true;
				}
				if(to.getEntryMaximumMarks()!=null && !to.getEntryMaximumMarks().isEmpty() ){
					if(!CommonUtil.isValidDecimal(to.getEntryMaximumMarks())){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.entry.maximum.marks.numeric"));
					}else{
						checkInternal=true;
						individual=individual+Double.parseDouble(to.getEntryMaximumMarks());
					}
				}
			}
		}
		if(pesto.getTotalentryMaximumMarks()!=null && !pesto.getTotalentryMaximumMarks().isEmpty()){
			if( CommonUtil.isValidDecimal(pesto.getTotalentryMaximumMarks())){
				total=Double.parseDouble(pesto.getTotalentryMaximumMarks());
				checkInternal=true;
				if(individual!=total){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.entry.total.maximum.marks.isNotValid"));
				}
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.entry.total.maximum.marks.numeric"));
			}
		}
		if(pesto.getTotalMinimummumMarks()!=null && !pesto.getTotalMinimummumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(pesto.getTotalMinimummumMarks()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.total.minimum.marks.numeric"));
			else
				checkInternal=true;
		}
		if(pesto.getTotalMaximumMarks()!=null && !pesto.getTotalMaximumMarks().isEmpty()){
			if(!CommonUtil.isValidDecimal(pesto.getTotalMaximumMarks()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.total.maximum.marks.numeric"));
			else
				checkInternal=true;
		}
		
		subjectRuleSettingsForm.setCheckInternal(checkInternal);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward subjectFinal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered theoryESE input");
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionErrors errors =new ActionErrors();
		validatepracticalESE(subjectRuleSettingsForm,errors);
		if(!errors.isEmpty()){
			// To Solve Check Box Problem
			resetCheckBoxForPracticalEse(subjectRuleSettingsForm);
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_PRACTICAL_ESE);
		}
		log.info("Exit theoryESE input");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SUBJECT_FINAL);
	}

	/**
	 * @param subjectRuleSettingsForm
	 * @throws Exception
	 */
	private void resetCheckBoxForPracticalEse(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		ExamSubjectRuleSettingsPracticalESETO peseTo=subjectRuleSettingsForm.getPeseTo();
		peseTo.setDupmultipleAnswerScriptsChecked(peseTo.getMultipleAnswerScriptsChecked());
		peseTo.setDupmultipleEvaluatorChecked(peseTo.getMultipleEvaluatorChecked());
		peseTo.setDupregularPracticalESEChecked(peseTo.getRegularPracticalESEChecked());
		peseTo.setRegularPracticalESEChecked(null);
		peseTo.setMultipleAnswerScriptsChecked(null);
		peseTo.setMultipleEvaluatorChecked(null);
		peseTo.setDupSupplementaryChecked(peseTo.getSupplementaryChecked());
		peseTo.setSupplementaryChecked(null);
		subjectRuleSettingsForm.setPeseTo(peseTo);
		
	}

	private void validatepracticalESE(SubjectRuleSettingsForm subjectRuleSettingsForm, ActionErrors errors) throws Exception{
		ExamSubjectRuleSettingsPracticalESETO peseTo=subjectRuleSettingsForm.getPeseTo();
		boolean checkPractical=false;
		if(peseTo.getRegularPracticalESEChecked()!=null && !peseTo.getRegularPracticalESEChecked().isEmpty() 
				&& peseTo.getRegularPracticalESEChecked().equals("on")){
			checkPractical=true;
			if((peseTo.getMultipleAnswerScriptsChecked()!=null && !peseTo.getMultipleAnswerScriptsChecked().isEmpty()) ||
					(peseTo.getMultipleEvaluatorChecked()!=null && !peseTo.getMultipleEvaluatorChecked().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.regular.checked.isnotValid"));
			}
		}
		if(peseTo.getMinimumMarksPracticalESE()!=null && !peseTo.getMinimumMarksPracticalESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(peseTo.getMinimumMarksPracticalESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Minimum Marks"));
			else
				checkPractical=true;
		}
		if(peseTo.getMaximumMarksPracticalESE()!=null && !peseTo.getMaximumMarksPracticalESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(peseTo.getMaximumMarksPracticalESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Maximum Marks"));
			else
				checkPractical=true;
		}
		if(peseTo.getMaximumEntryMarksPracticalESE()!=null && !peseTo.getMaximumEntryMarksPracticalESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(peseTo.getMaximumEntryMarksPracticalESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Entry Maximum Marks"));
			else
				checkPractical=true;
		}
		if(checkPractical){
			if((peseTo.getMultipleAnswerScriptsChecked()==null || peseTo.getMultipleAnswerScriptsChecked().isEmpty()) &&
					(peseTo.getMultipleEvaluatorChecked()==null || peseTo.getMultipleEvaluatorChecked().isEmpty()) && 
					(peseTo.getRegularPracticalESEChecked()==null || peseTo.getRegularPracticalESEChecked().isEmpty())){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Regular or Multiple Answer Script or Multiple Evaluator should be selected"));
			}
		}
		if(peseTo.getMinimumTheoryFinalMarksPracticalESE()!=null && !peseTo.getMinimumTheoryFinalMarksPracticalESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(peseTo.getMinimumTheoryFinalMarksPracticalESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Final Minimum Marks"));
			else
				checkPractical=true;
		}
		if(peseTo.getMaximumTheoryFinalMarksPracticalESE()!=null && !peseTo.getMaximumTheoryFinalMarksPracticalESE().isEmpty()){
			if(!CommonUtil.isValidDecimal(peseTo.getMaximumTheoryFinalMarksPracticalESE()))
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Final Maximum Marks"));
			else
				checkPractical=true;
		}
		
		if((peseTo.getMultipleAnswerScriptsChecked()!=null && !peseTo.getMultipleAnswerScriptsChecked().isEmpty()) ||
				(peseTo.getMultipleEvaluatorChecked()!=null && !peseTo.getMultipleEvaluatorChecked().isEmpty())){
			checkPractical=true;
			if(peseTo.getRegularPracticalESEChecked()!=null && !peseTo.getRegularPracticalESEChecked().isEmpty() && peseTo.getRegularPracticalESEChecked().equals("on")){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multiple.evaluator.checked.isnotValid"));
			}
		}
		
		if(peseTo.getMultipleAnswerScriptsChecked()!=null && !peseTo.getMultipleAnswerScriptsChecked().isEmpty()){
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList=peseTo.getMultipleAnswerScriptList();
			if(multipleAnswerScriptList==null || multipleAnswerScriptList.isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multipleanswer.script.required"));
			}else{
				boolean isEntered=false;
				Iterator<ExamSubjectRuleSettingsMultipleAnswerScriptTO> itr=multipleAnswerScriptList.iterator();
				while (itr.hasNext()) {
					ExamSubjectRuleSettingsMultipleAnswerScriptTO to = (ExamSubjectRuleSettingsMultipleAnswerScriptTO) itr.next();
					if(to.getMultipleAnswerScriptTheoryESE()!=null && !to.getMultipleAnswerScriptTheoryESE().isEmpty()){
						isEntered=true;
						if(!CommonUtil.isValidDecimal(to.getMultipleAnswerScriptTheoryESE())){
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multipleanswer.script.numeric"));
						}else{
							checkPractical=true;
						}
					}
				}
				if(!isEntered){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multipleanswer.script.required"));
				}
			} 
		}
		if(peseTo.getMultipleEvaluatorChecked()!=null && !peseTo.getMultipleEvaluatorChecked().isEmpty()){
			if(peseTo.getNoOfEvaluations()==null || peseTo.getNoOfEvaluations().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","No. of Evaluation"));
			}else{
				if(!CommonUtil.isValidDecimal(peseTo.getNoOfEvaluations())){
					errors.add(CMSConstants.ERROR,new ActionError("errors.integer","No. of Evaluation"));
				}else{
					int evaluator=Integer.parseInt(peseTo.getNoOfEvaluations());
					int count=0;
					if(peseTo.getEvalId1()!=null && !peseTo.getEvalId1().isEmpty()){
						checkPractical=true;
						count++;
					}
					if(peseTo.getEvalId2()!=null && !peseTo.getEvalId2().isEmpty()){
						checkPractical=true;
						count++;
					}
					if(peseTo.getEvalId3()!=null && !peseTo.getEvalId3().isEmpty()){
						checkPractical=true;
						count++;
					}
					if(peseTo.getEvalId4()!=null && !peseTo.getEvalId4().isEmpty()){
						checkPractical=true;
						count++;
					}
					if(peseTo.getEvalId5()!=null && !peseTo.getEvalId5().isEmpty()){
						checkPractical=true;
						count++;
					}
					if(evaluator!=count){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.subject.rule.settings.multiple.evaluator.id.required",count));
					}
				}
			}
			if(peseTo.getSelectTypeOfEvaluation()==null || peseTo.getSelectTypeOfEvaluation().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Type of Evaluation"));
			}
		}
		if(peseTo.getSupplementaryChecked()!=null && !peseTo.getSupplementaryChecked().isEmpty() && peseTo.getSupplementaryChecked().equals("on")){
			if(peseTo.getSupplementaryMaxMarks()==null || peseTo.getSupplementaryMaxMarks().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Supplementary Max Marks"));
			}else{
				if(!CommonUtil.isValidDecimal(peseTo.getSupplementaryMaxMarks())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Supplementary Max Marks"));
				}
			}
			if(peseTo.getSupplementaryMinMarks()==null || peseTo.getSupplementaryMinMarks().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Supplementary Min Marks"));
			}else{
				if(!CommonUtil.isValidDecimal(peseTo.getSupplementaryMinMarks())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.decimal","Supplementary Min Marks"));
				}
			}
		}
		subjectRuleSettingsForm.setCheckpractical(checkPractical);
	}
	
	public ActionForward saveSubjectRules(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionMessages messages=new ActionMessages(); 
		ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		validateSubjectFinal(subjectRuleSettingsForm,errors);
		if (errors.isEmpty()) {
			try {
				boolean isAdded=SubjectRuleSettingsHandler.getInstance().saveSubjectRules(subjectRuleSettingsForm,"add");
				if(subjectRuleSettingsForm.getMethodType().equals("add")){
					if(isAdded){
						ActionMessage message = new ActionMessage("knowledgepro.exam.subjectrulesettings.success");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else{
						errors.add("error", new ActionError("knowledgepro.exam.subjectrulesettings.failure"));
						saveErrors(request, errors);
					}
				}else{
					if(isAdded){
						ActionMessage message = new ActionMessage("knowledgepro.exam.subjectrulesettings.update.success");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else{
						errors.add("error", new ActionError("knowledgepro.exam.subjectrulesettings.failure"));
						saveErrors(request, errors);
					}
					subjectRuleSettingsForm.resetFields1();
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
				}
				subjectRuleSettingsForm.resetFields();
				setRequiredDatatoForm(subjectRuleSettingsForm,request);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			// For Check Box Problem
			resetCheckBoxSubjectFinal(subjectRuleSettingsForm);
			addErrors(request, errors);
			setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SUBJECT_FINAL);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}

	/**
	 * @param subjectRuleSettingsForm
	 */
	private void resetCheckBoxSubjectFinal(SubjectRuleSettingsForm subjectRuleSettingsForm) {
		subjectRuleSettingsForm.setDupattendance(subjectRuleSettingsForm.getAttendance());
		subjectRuleSettingsForm.setDupinternalExam(subjectRuleSettingsForm.getInternalExam());
		subjectRuleSettingsForm.setDuppracticalExam(subjectRuleSettingsForm.getPracticalExam());
		subjectRuleSettingsForm.setDuptheoryExam(subjectRuleSettingsForm.getTheoryExam());
		subjectRuleSettingsForm.setAttendance(null);
		subjectRuleSettingsForm.setInternalExam(null);
		subjectRuleSettingsForm.setPracticalExam(null);
		subjectRuleSettingsForm.setTheoryExam(null);
	}

	/**
	 * @param subjectRuleSettingsForm
	 * @param errors
	 */
	private void validateSubjectFinal(SubjectRuleSettingsForm subjectRuleSettingsForm, ActionErrors errors) throws Exception {
		
		if(subjectRuleSettingsForm.getTheoryExam()!=null && !subjectRuleSettingsForm.getTheoryExam().isEmpty() && subjectRuleSettingsForm.getTheoryExam().equals("on")){
			if(!subjectRuleSettingsForm.isCheckTheory()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subject.rule.settings.check","Theory Ese"));
			}
		}
		
		if(subjectRuleSettingsForm.getPracticalExam()!=null && !subjectRuleSettingsForm.getPracticalExam().isEmpty() && subjectRuleSettingsForm.getPracticalExam().equals("on")){
			if(!subjectRuleSettingsForm.isCheckpractical()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subject.rule.settings.check","Practical Ese"));
			}
		}
		
		if(subjectRuleSettingsForm.getInternalExam()!=null && !subjectRuleSettingsForm.getInternalExam().isEmpty() && subjectRuleSettingsForm.getInternalExam().equals("on")){
			if(!subjectRuleSettingsForm.isCheckInternal()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subject.rule.settings.check","Theory Internal / Practical Internal"));
			}
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
	public ActionForward backPracticalEse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered - backPracticalEse");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		resetCheckBoxForPracticalEse(subjectRuleSettingsForm);
		
		log.info("Entered  - backPracticalEse");
		
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_PRACTICAL_ESE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backPracticalInternal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered - backPracticalInternal");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		resetCheckBoxForPracticalInternal(subjectRuleSettingsForm);
		
		log.info("Entered  - backPracticalInternal");
		
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_PRACTICAL_INTERNAL_PRACTICAL);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backTheoryEse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered - backTheoryEse");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		resetCheckBoxForTheoryEse(subjectRuleSettingsForm);
		
		log.info("Entered  - backTheoryEse");
		
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_THEORY_ESE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backTheoryInternal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered - backTheoryInternal");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		resetCheckBoxForTheoryInternal(subjectRuleSettingsForm);
		
		log.info("Entered  - backTheoryInternal");
		
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_ADD);
	}
	
	public ActionForward editSubjectRuleSetting(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		 ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		String st = request.getParameter("courseIds");
		String str[]=st.split(",");
		if(str==null || str.length==0 || st.isEmpty()){
			if (errors.get("admissionFormForm.courseId.required") != null&& !errors.get("admissionFormForm.courseId.required").hasNext()) {
				errors.add("admissionFormForm.courseId.required",new ActionError("admissionFormForm.courseId.required"));
			}
		}
		if (errors.isEmpty()) {
			try {
				subjectRuleSettingsForm.setCourseIds(str);
				List<String> notExist=SubjectRuleSettingsHandler.getInstance().checkExisted(subjectRuleSettingsForm);
				if(notExist!=null && !notExist.isEmpty()){
					Iterator<String> itr=notExist.iterator();
					StringBuilder courseName =new StringBuilder();
					while (itr.hasNext()) {
						String name = (String) itr.next();
						courseName.append(" ").append(name);
					}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectrulesettings.notExisted",courseName));
				}
				 
				List<SubjectTO> list=SubjectRuleSettingsHandler.getInstance().getSubjectsToForInput(subjectRuleSettingsForm);
				if(list==null || list.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
				}
				if(errors.isEmpty()){
					subjectRuleSettingsForm.setSubList(list);
					setNamesToForm(subjectRuleSettingsForm);
				}else {
					addErrors(request, errors);
					setRequiredDatatoForm(subjectRuleSettingsForm, request);			
					log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editSubjectRuleSettingsForSubject(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		 ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				subjectRuleSettingsForm.setId(0);
				SubjectRuleSettings bo=SubjectRuleSettingsHandler.getInstance().getSubjectRuleSettingsBo(subjectRuleSettingsForm);
			    if(bo==null){
			    	setDataToFormInAddMode(subjectRuleSettingsForm);
			    	subjectRuleSettingsForm.setMethodType("update");
			    	setNamesToForm(subjectRuleSettingsForm);
			    	return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_ADD);
//			    	errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectrulesettings.notExisted"," The Selected Subject"));
			    }else if(!bo.getIsActive()){
			    	subjectRuleSettingsForm.setId(bo.getId());
			    	errors.add("error", new ActionError("knowledgepro.exam.subjectRuleSettings.reactivate", bo.getId()));
			    }
				if(errors.isEmpty()){
					if(bo.getIsActive()){
					SubjectRuleSettingsHandler.getInstance().setSubjectRuleSettingDataToForm(bo,subjectRuleSettingsForm);
					}
				}else {
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
				}
				subjectRuleSettingsForm.setMethodType("update");
				setNamesToForm(subjectRuleSettingsForm);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError("knowledgepro.exam.subjectRuleSettings.reactivate", e1.getID()));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_ADD);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSubjectRuleSettingsForSubject(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionMessages messages=new ActionMessages(); 
		ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		setUserId(request, subjectRuleSettingsForm);
		
		if (errors.isEmpty()) {
			try {
				SubjectRuleSettings bo=SubjectRuleSettingsHandler.getInstance().getSubjectRuleSettingsBo(subjectRuleSettingsForm);
			    if(bo==null){
			    	errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectrulesettings.notExisted"," The Selected Subject"));
			    }
				if(errors.isEmpty()){
					boolean isDelete=SubjectRuleSettingsHandler.getInstance().deleteSubjectRuleSettingsForSubject(subjectRuleSettingsForm);
					if(isDelete){
						ActionMessage message = new ActionMessage("knowledgepro.exam.subjectrulesettings.deleted");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else{
						errors.add("error", new ActionError("knowledgepro.exam.subjectrulesettings.deleted.failed"));
						saveErrors(request, errors);
					}
				}else {
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
				}
				subjectRuleSettingsForm.setMethodType("update");
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reActivateSubjectRuleSettingsForSubject(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionMessages messages=new ActionMessages(); 
		ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		setUserId(request, subjectRuleSettingsForm);
		
		if (errors.isEmpty()) {
			try {
				if(errors.isEmpty()){
					boolean isReActivate=SubjectRuleSettingsHandler.getInstance().reActivateSubjectRuleSettingsForSubject(subjectRuleSettingsForm);
					if(isReActivate){
						ActionMessage message = new ActionMessage("knowledgepro.exam.subjectRuleSettings.reactivated");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else{
						errors.add("error", new ActionError("knowledgepro.exam.subjectRuleSettings.reactivation.fail"));
						saveErrors(request, errors);
					}
					subjectRuleSettingsForm.setId(0);
				}else {
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSubjectRuleSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionMessages messages=new ActionMessages(); 
		ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		String[] str = request.getParameterValues("courseIds");
		if(str==null || str.length==0){
			if (errors.get("admissionFormForm.courseId.required") != null&& !errors.get("admissionFormForm.courseId.required").hasNext()) {
				errors.add("admissionFormForm.courseId.required",new ActionError("admissionFormForm.courseId.required"));
			}
		}
		if (errors.isEmpty()) {
			try {
				List<ExamSubjectRuleSettingsBO> duplicateList=SubjectRuleSettingsHandler.getInstance().isDuplicateCheck(subjectRuleSettingsForm);
				if(duplicateList==null || duplicateList.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.viewTimeTable.norecordsfound"));
				}
				if(errors.isEmpty()){
					 boolean isDeleted=SubjectRuleSettingsHandler.getInstance().deleteCompleteSubjectRuleSettings(subjectRuleSettingsForm);
					 if(isDeleted){
						 	subjectRuleSettingsForm.resetFields();
							ActionMessage message = new ActionMessage("knowledgepro.exam.subjectrulesettings.deleted");
							messages.add("messages", message);
							saveMessages(request, messages);
						}else{
							errors.add("error", new ActionError("knowledgepro.exam.subjectrulesettings.deleted.failed"));
							saveErrors(request, errors);
						}
				}else {
					addErrors(request, errors);
					setRequiredDatatoForm(subjectRuleSettingsForm, request);			
					log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}
	
	
	public ActionForward copySubjectRuleSetting(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		String st = request.getParameter("courseIds");
		String str[]=st.split(",");
		if(str==null || str.length==0 || st.isEmpty()){
			if (errors.get("admissionFormForm.courseId.required") != null&& !errors.get("admissionFormForm.courseId.required").hasNext()) {
				errors.add("admissionFormForm.courseId.required",new ActionError("admissionFormForm.courseId.required"));
			}
		}
		if (errors.isEmpty()) {
			try {
				subjectRuleSettingsForm.setCourseIds(str);
				List<String> notExist=SubjectRuleSettingsHandler.getInstance().checkExisted(subjectRuleSettingsForm);
				if(notExist!=null && !notExist.isEmpty()){
					Iterator<String> itr=notExist.iterator();
					StringBuilder courseName=new StringBuilder();
					while (itr.hasNext()) {
						String name = (String) itr.next();
						courseName.append(" ").append(name);
					}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectrulesettings.notExisted",courseName));
				}
				if(errors.isEmpty()){
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS_COPY);
				}else {
					addErrors(request, errors);
					setRequiredDatatoForm(subjectRuleSettingsForm, request);			
					log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
		}
	}
	
	
	public ActionForward copySubjectRuleSettingByYear(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		ActionMessages messages=new ActionMessages(); 
		ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		setUserId(request, subjectRuleSettingsForm);
		if (errors.isEmpty()) {
			try {
				List<String> notExist=SubjectRuleSettingsHandler.getInstance().checkExistedForCopy(subjectRuleSettingsForm);
				if(notExist!=null && !notExist.isEmpty()){
					Iterator<String> itr=notExist.iterator();
					StringBuilder courseName = new StringBuilder();
					while (itr.hasNext()) {
						String name = (String) itr.next();
						courseName.append(" ").append(name);
					}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subjectrulesettings.Existed",courseName));
				}
				List<String> list=SubjectRuleSettingsHandler.getInstance().getSubjectsForCopy(subjectRuleSettingsForm);
				if(list==null || list.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subject.rule.settings.subjectgroup.notDefined",subjectRuleSettingsForm.getToYear()));
				}
				if(errors.isEmpty()){
					boolean isCopy=SubjectRuleSettingsHandler.getInstance().copySubjectRuleSettings(list,subjectRuleSettingsForm);
					if(isCopy){
					 	subjectRuleSettingsForm.resetFields();
						ActionMessage message = new ActionMessage("knowledgepro.exam.subjectrulesettings.copy");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else{
						errors.add("error", new ActionError("knowledgepro.exam.subjectrulesettings.copy.failed"));
						saveErrors(request, errors);
					}
				}else {
					addErrors(request, errors);
//					setRequiredDatatoForm(subjectRuleSettingsForm, request);			
					log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
					return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS_COPY);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_SETTINGS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelSubjectRuleSetting(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ScoreSheetAction - getCandidates");
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		subjectRuleSettingsForm.resetFields1();
		return mapping.findForward(CMSConstants.EXAM_SUBJECTRULE_EDIT);
	}
}
