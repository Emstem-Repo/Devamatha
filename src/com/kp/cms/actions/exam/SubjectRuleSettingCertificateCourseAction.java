package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.handlers.exam.SubjectRuleSettingCertificateCourseHandelers;
import com.kp.cms.handlers.exam.SubjectRuleSettingsHandler;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.to.exam.SubjectRuleSettingCertificateCourseTo;
import com.kp.cms.utilities.CommonUtil;

public class SubjectRuleSettingCertificateCourseAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(SubjectRuleSettingCertificateCourseAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSubjectRuleCertificate (ActionMapping mapping,ActionForm form,
			    HttpServletRequest request, HttpServletResponse response)throws Exception{
	SubjectRuleSettingsForm objform=(SubjectRuleSettingsForm) form;
	objform.clearPage();
	setUserId(request,objform);
	return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE);
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchPhdDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	   throws Exception{
		SubjectRuleSettingsForm objform=(SubjectRuleSettingsForm)form;
		ActionErrors errors=objform.validate(mapping, request);
		if(errors.isEmpty()){
	      try {
		  objform.setSubRulCerCourList(null);
		  setDataToList(objform,errors);
			} catch (Exception exception) {
				if (exception instanceof ApplicationException) {
					String msg = super.handleApplicationException(exception);
					objform.setErrorMessage(msg);
					objform.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
			}
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE);
		  }
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE);
	}	
	/**
	 * @param objform
	 * @param errors
	 * @throws Exception
	 */
	private void setDataToList(SubjectRuleSettingsForm objform,ActionErrors errors) throws Exception{
		   List<SubjectRuleSettingCertificateCourseTo> subRulList=SubjectRuleSettingCertificateCourseHandelers.getInstance().searchSubCerDetails(objform);
		   if(subRulList!=null && !subRulList.isEmpty()){
		   objform.setSubRulCerCourList(subRulList);
		   objform.setCountcheck(subRulList.size());
		   }else{
			    errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
		   }
	}
	public ActionForward addCertificateDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ScoreSheetAction - getCandidates");
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		 ActionErrors errors = subjectRuleSettingsForm.validate(mapping, request);
		Iterator<SubjectRuleSettingCertificateCourseTo> itr=subjectRuleSettingsForm.getSubRulCerCourList().iterator();
		boolean flag=false;
		while (itr.hasNext()) {
			SubjectRuleSettingCertificateCourseTo subjectRuleTo = (SubjectRuleSettingCertificateCourseTo) itr.next();
			if(subjectRuleTo.getChecked()!=null && !subjectRuleTo.getChecked().isEmpty()){
				flag=true;
			}
			
		}
		if(!flag){
			 errors.add("error", new ActionError("knowledgepro.exam.subjectrule.not.selected"));
		}
		if (errors.isEmpty()) {
			try {
				List<SubjectGroup> list=SubjectRuleSettingCertificateCourseHandelers.getInstance().getSubjectsForInput(subjectRuleSettingsForm);
				if(list==null || list.isEmpty()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.subject.rule.settings.subjectgroup.notDefined",subjectRuleSettingsForm.getAcademicYear()));
					selectCheckedUnchecked(subjectRuleSettingsForm);
				}
				if(errors.isEmpty()){
					setDataToFormInAddMode(subjectRuleSettingsForm);
					subjectRuleSettingsForm.setMethodType("add");
				}else {
					addErrors(request, errors);
			//		setRequiredDatatoForm(subjectRuleSettingsForm, request);			
					log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
					return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				subjectRuleSettingsForm.setErrorMessage(msg);
				subjectRuleSettingsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
		//	setRequiredDatatoForm(subjectRuleSettingsForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE_ADD);
	}
	

	private void selectCheckedUnchecked(SubjectRuleSettingsForm subjectRuleSettingsForm) {
		//List<SubjectRuleSettingCertificateCourseTo> showData=new ArrayList<SubjectRuleSettingCertificateCourseTo>();
		Iterator<SubjectRuleSettingCertificateCourseTo> itr=subjectRuleSettingsForm.getSubRulCerCourList().iterator();
        while (itr.hasNext()) {
	    SubjectRuleSettingCertificateCourseTo subjectRuleTo = (SubjectRuleSettingCertificateCourseTo) itr.next();
	    if(subjectRuleTo.getChecked()!=null && !subjectRuleTo.getChecked().isEmpty()){
	       subjectRuleTo.setTempChecked("on");
	       subjectRuleTo.setChecked(null);
	    }else{
	    	subjectRuleTo.setTempChecked(null);
		    subjectRuleTo.setChecked(null);
	    }
	
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
			return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE_ADD);
		}
		log.info("Exit theoryESE input");
		return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_THEORY_ESE);
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
		Iterator<ExamSubjectRuleSettingsAssignmentTO> itr=subjectRuleSettingsForm.getPesto().getAssignmentList().iterator();
		while(itr.hasNext()) {
			ExamSubjectRuleSettingsAssignmentTO examSubjectTO = (ExamSubjectRuleSettingsAssignmentTO) itr.next();
			examSubjectTO.setMinimumAssignMarks(null);
			examSubjectTO.setMaximumAssignMarks(null);
			
		}
		if(!errors.isEmpty()){
			// To Solve the Check box problem
			resetCheckBoxForTheoryEse(subjectRuleSettingsForm);
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_THEORY_ESE);
		}
		log.info("Exit theoryESE input");
		return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_THEORY_PRACTICAL_INTERNAL_PRACTICAL);
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
			return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_THEORY_PRACTICAL_INTERNAL_PRACTICAL);
		}
		log.info("Exit theoryESE input");
		return mapping.findForward(CMSConstants.SUBJECTRULE_CERTIFICATE_PRACTICAL_ESE);
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
			return mapping.findForward(CMSConstants.SUBJECTRULE_CERTIFICATE_PRACTICAL_ESE);
		}
		log.info("Exit theoryESE input");
		return mapping.findForward(CMSConstants.SUBJECTRULE_CERTIFICATE_SUBJECT_FINAL);
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
		
		return mapping.findForward(CMSConstants.SUBJECTRULE_CERTIFICATE_PRACTICAL_ESE);
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
		
		return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_THEORY_PRACTICAL_INTERNAL_PRACTICAL);
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
		
		return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_THEORY_ESE);
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
		
		return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE_ADD);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelSubjectRuleSettingCertificate (ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ScoreSheetAction - getCandidates");
		SubjectRuleSettingsForm subjectRuleSettingsForm = (SubjectRuleSettingsForm) form;
		selectCheckedUnchecked(subjectRuleSettingsForm);
		subjectRuleSettingsForm.resetFields1();
		return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE);
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
				 boolean isAdded=SubjectRuleSettingCertificateCourseHandelers.getInstance().saveSubjectRules(subjectRuleSettingsForm);
				  if(isAdded){
						ActionMessage message = new ActionMessage("knowledgepro.exam.subjectrulesettings.success");
						messages.add("messages", message);
						saveMessages(request, messages);
						subjectRuleSettingsForm.clearPage();
						subjectRuleSettingsForm.clearFields();
						subjectRuleSettingsForm.resetFields1();
						subjectRuleSettingsForm.resetFields();
					}else{
						errors.add("error", new ActionError("knowledgepro.exam.subjectrulesettings.failure"));
						selectCheckedUnchecked(subjectRuleSettingsForm);
						saveErrors(request, errors);
					}
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
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.SUBJECTRULE_CERTIFICATE_SUBJECT_FINAL);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.SUBJECT_RULE_CERTIFICATE_COURSE);
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
	
   }
