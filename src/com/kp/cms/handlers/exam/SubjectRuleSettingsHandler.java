package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamAssignmentTypeMasterBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.bo.exam.SubjectRuleSettingsAssignment;
import com.kp.cms.bo.exam.SubjectRuleSettingsAttendance;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulAnsScript;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulEvaluator;
import com.kp.cms.bo.exam.SubjectRuleSettingsSubInternal;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.helpers.exam.SubjectRuleSettingsHelper;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction;
import com.kp.cms.transactionsimpl.exam.SubjectRuleSettingsTransactionImpl;

public class SubjectRuleSettingsHandler {
	
	ISubjectRuleSettingsTransaction transaction=SubjectRuleSettingsTransactionImpl.getInstance();
	
	/**
	 * Singleton object of SubjectRuleSettingsHandler
	 */
	private static volatile SubjectRuleSettingsHandler subjectRuleSettingsHandler = null;
	private static final Log log = LogFactory.getLog(SubjectRuleSettingsHandler.class);
	private SubjectRuleSettingsHandler() {
		
	}
	/**
	 * return singleton object of SubjectRuleSettingsHandler.
	 * @return
	 */
	public static SubjectRuleSettingsHandler getInstance() {
		if (subjectRuleSettingsHandler == null) {
			subjectRuleSettingsHandler = new SubjectRuleSettingsHandler();
		}
		return subjectRuleSettingsHandler;
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamSubjectRuleSettingsBO> isDuplicateCheck(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception{
		String query=SubjectRuleSettingsHelper.getInstance().getDuplicateQuery(subjectRuleSettingsForm);
		return transaction.isDuplicateCheck(query);
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public List<SubjectGroup> getSubjectsForInput(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		return transaction.getSubjectGroupsForInput(subjectRuleSettingsForm);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ExamSubjectRuleSettingsSubInternalTO> getAllExamSubjectRuleSettingsSubInternals() throws Exception {
		List<ExamInternalExamTypeBO> bos=transaction.getAllExamSubjectRuleSettingsSubInternals();
		return SubjectRuleSettingsHelper.getInstance().convertExamInternalExamTypeBotoToList(bos);
	}
	public List<ExamSubjectRuleSettingsAssignmentTO> getAllAssignment() throws Exception {
		List<ExamAssignmentTypeMasterBO> list=transaction.getAllAssignment();
		return SubjectRuleSettingsHelper.getInstance().convertExamAssignmentBotoToList(list);
	}
	public List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> getAllMultipleAnswerScript() throws Exception {
		List<ExamMultipleAnswerScriptMasterBO> list=transaction.getAllMultipleAnswerScript();
		return SubjectRuleSettingsHelper.getInstance().convertExamMultipleAnswerScriptBotoToList(list);
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public boolean saveSubjectRules(SubjectRuleSettingsForm subjectRuleSettingsForm, String mode) throws Exception {
		List<SubjectRuleSettings> bos;
		if(subjectRuleSettingsForm.getMethodType().equals("add")){
			bos=SubjectRuleSettingsHelper.getInstance().getBoObject(subjectRuleSettingsForm,mode);
		}else{
			bos=SubjectRuleSettingsHelper.getInstance().getBoObjectForUpdate(subjectRuleSettingsForm,mode);
		}
		return transaction.addAll(bos);
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public List<String> checkExisted(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		String query=SubjectRuleSettingsHelper.getInstance().getCheckExistedQuery(subjectRuleSettingsForm);
		return transaction.checkExited(query,subjectRuleSettingsForm.getCourseIds());
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 */
	public List<SubjectTO> getSubjectsToForInput(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		String query=SubjectRuleSettingsHelper.getInstance().getSubjectsForCourse(subjectRuleSettingsForm);
		List<Object[]> subjList=transaction.getSubjectListByQuery(query);
		return SubjectRuleSettingsHelper.getInstance().convertBotoTo(subjList);
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public SubjectRuleSettings getSubjectRuleSettingsBo(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		String query = SubjectRuleSettingsHelper.getInstance().getBoQuery(subjectRuleSettingsForm);
		return transaction.getBOobjectByQuery(query);
	}
	/**
	 * @param bo
	 * @param subjectRuleSettingsForm
	 * @throws Exception
	 */
	public void setSubjectRuleSettingDataToForm(SubjectRuleSettings bo,	SubjectRuleSettingsForm subjectRuleSettingsForm)  throws Exception{
		if(bo.getSubject()!=null)
			subjectRuleSettingsForm.setSubjectId(String.valueOf(bo.getSubject().getId()));
		subjectRuleSettingsForm.setSchemeNo(String.valueOf(bo.getSchemeNo()));
		subjectRuleSettingsForm.setId(bo.getId());
		if(bo.getCourse()!=null)
			subjectRuleSettingsForm.setCourseId(String.valueOf(bo.getCourse().getId()));
		subjectRuleSettingsForm.setAcademicYear(String.valueOf(bo.getAcademicYear()));
		
		ExamSubjectRuleSettingsTheoryInternalTO esto=new ExamSubjectRuleSettingsTheoryInternalTO();
		if(bo.getSelectBestOfTheoryInternal()!=null)
		esto.setSelectTheBest(String.valueOf(bo.getSelectBestOfTheoryInternal()));
		if(bo.getTheoryIntMinMarksTotal()!=null)
			esto.setTotalMinimummumMarks(String.valueOf(bo.getTheoryIntMinMarksTotal()));
		if(bo.getTheoryIntMaxMarksTotal()!=null)
			esto.setTotalMaximumMarks(String.valueOf(bo.getTheoryIntMaxMarksTotal()));
		if(bo.getTheoryIntEntryMaxMarksTotal()!=null)
			esto.setTotalentryMaximumMarks(String.valueOf(bo.getTheoryIntEntryMaxMarksTotal()));
		
		if(bo.getFinalTheoryInternalIsSubInternal()!=null && bo.getFinalTheoryInternalIsSubInternal())
			esto.setDupsubInternalChecked("on");
		
		if(bo.getFinalTheoryInternalIsAssignment()!=null && bo.getFinalTheoryInternalIsAssignment())
			esto.setDupassignmentChecked("on");
		
		if(bo.getFinalTheoryInternalIsAttendance()!=null && bo.getFinalTheoryInternalIsAttendance())
			esto.setDupattendanceChecked("on");
		
		if(bo.getFinalTheoryInternalEnteredMaxMark()!=null)
			esto.setFinalEntryMaximumMarks(String.valueOf(bo.getFinalTheoryInternalEnteredMaxMark()));
		
		if(bo.getFinalTheoryInternalMaximumMark()!=null)
			esto.setFinalInternalMaximumMarks(String.valueOf(bo.getFinalTheoryInternalMaximumMark()));
		
		if(bo.getFinalTheoryInternalMinimumMark()!=null)
			esto.setFinalInternalMinimumMarks(String.valueOf(bo.getFinalTheoryInternalMinimumMark()));
		
		List<ExamSubjectRuleSettingsSubInternalTO> subInternalTOs=SubjectRuleSettingsHandler.getInstance().getAllExamSubjectRuleSettingsSubInternals();
		Set<SubjectRuleSettingsSubInternal> subInt=bo.getExamSubjectRuleSettingsSubInternals();
		if(subInt!=null && !subInt.isEmpty()){
			Map<Integer,ExamSubjectRuleSettingsSubInternalTO> theorySubInternal=SubjectRuleSettingsHelper.getInstance().converInternalBoToTo(subInt,'t'); 
			for(int i=0; i<subInternalTOs.size();i++){
				ExamSubjectRuleSettingsSubInternalTO to = (ExamSubjectRuleSettingsSubInternalTO) subInternalTOs.get(i);
				if(theorySubInternal.containsKey(Integer.parseInt(to.getInternalExamTypeId()))){
					subInternalTOs.remove(to);
					ExamSubjectRuleSettingsSubInternalTO to1=theorySubInternal.get(Integer.parseInt(to.getInternalExamTypeId()));
					to1.setType(to.getType());
					subInternalTOs.add(i,to1);
				}
			}
		}
		esto.setSubInternalList(subInternalTOs);
		
		List<AttendanceTypeTO> attendanceTypeList = AttendanceTypeHandler.getInstance().getAttendanceType(); 
		esto.setAttendanceTypeList(attendanceTypeList);
		
		
		
		List<ExamSubjectRuleSettingsAssignmentTO> assList=SubjectRuleSettingsHandler.getInstance().getAllAssignment();
		Set<SubjectRuleSettingsAssignment> assignment=bo.getExamSubjectRuleSettingsAssignments();
		if(assignment!=null && !assignment.isEmpty()){
			Map<Integer,ExamSubjectRuleSettingsAssignmentTO> theoryAssignment=SubjectRuleSettingsHelper.getInstance().converAssignmentBoToTo(assignment,'t'); 
			for(int i=0; i<assList.size();i++){
				ExamSubjectRuleSettingsAssignmentTO to = (ExamSubjectRuleSettingsAssignmentTO) assList.get(i);
				if(theoryAssignment.containsKey(to.getAssignmentTypeId())){
					assList.remove(to);
					ExamSubjectRuleSettingsAssignmentTO to1=theoryAssignment.get(to.getAssignmentTypeId());
					to1.setName(to.getName());
					assList.add(i,to1);
				}
			}
		}
		esto.setAssignmentList(assList);
		
		ExamSubjectRuleSettingsTheoryESETO teseTo=new ExamSubjectRuleSettingsTheoryESETO();
		
		if(bo.getTheoryEseIsRegular()!=null && bo.getTheoryEseIsRegular())
			teseTo.setDupregularTheoryESEChecked("on");
		
		if(bo.getTheoryEseIsMultipleAnswerScript()!=null && bo.getTheoryEseIsMultipleAnswerScript())
			teseTo.setDupmultipleAnswerScriptsChecked("on");
		
		if(bo.getTheoryEseIsMultipleEvaluator()!=null && bo.getTheoryEseIsMultipleEvaluator())
			teseTo.setDupmultipleEvaluatorChecked("on");
		
		if(bo.getTheoryEseEnteredMaxMark()!=null)
			teseTo.setMaximumEntryMarksTheoryESE(String.valueOf(bo.getTheoryEseEnteredMaxMark()));
		
		if(bo.getTheoryEseMaximumMark()!=null)
			teseTo.setMaximumMarksTheoryESE(String.valueOf(bo.getTheoryEseMaximumMark()));
		
		if(bo.getTheoryEseMinimumMark()!=null)
			teseTo.setMinimumMarksTheoryESE(String.valueOf(bo.getTheoryEseMinimumMark()));
		
		if(bo.getTheoryEseTheoryFinalMinimumMark()!=null)
			teseTo.setMinimumTheoryFinalMarksTheoryESE(String.valueOf(bo.getTheoryEseTheoryFinalMinimumMark()));
		
		if(bo.getTheoryEseTheoryFinalMaximumMark()!=null)
			teseTo.setMaximumTheoryFinalMarksTheoryESE(String.valueOf(bo.getTheoryEseTheoryFinalMaximumMark()));
		
		if(bo.getTheorySupplementaryMaxMarksTotal()!=null)
			teseTo.setSupplementaryMaxMarks(String.valueOf(bo.getTheorySupplementaryMaxMarksTotal()));
		
		if(bo.getTheorySupplementaryMinMarksTotal()!=null)
			teseTo.setSupplementaryMinMarks(String.valueOf(bo.getTheorySupplementaryMinMarksTotal()));
		
		if(bo.getTheoryIsSupplementary()!=null && bo.getTheoryIsSupplementary()){
			teseTo.setDupSupplementaryChecked("on");
		}
		
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScript=SubjectRuleSettingsHandler.getInstance().getAllMultipleAnswerScript(); 
		Set<SubjectRuleSettingsMulAnsScript> mulAns=bo.getExamSubjectRuleSettingsMulAnsScripts();
		if(mulAns!=null && !mulAns.isEmpty()){
			Map<Integer,ExamSubjectRuleSettingsMultipleAnswerScriptTO> theoryMulAns=SubjectRuleSettingsHelper.getInstance().converMulAnsBoToTo(mulAns,'t'); 
			for(int i=0; i<multipleAnswerScript.size();i++){
				ExamSubjectRuleSettingsMultipleAnswerScriptTO to = (ExamSubjectRuleSettingsMultipleAnswerScriptTO) multipleAnswerScript.get(i);
				if(theoryMulAns.containsKey(to.getMultipleAnswerScriptId())){
					multipleAnswerScript.remove(to);
					ExamSubjectRuleSettingsMultipleAnswerScriptTO to1=theoryMulAns.get(to.getMultipleAnswerScriptId());
					to1.setName(to.getName());
					multipleAnswerScript.add(i,to1);
				}
			}
 		}
		teseTo.setMultipleAnswerScriptList(multipleAnswerScript);
		
		
		List<ExamSubjectRuleSettingsSubInternalTO> practicalsubInternalTOs=SubjectRuleSettingsHandler.getInstance().getAllExamSubjectRuleSettingsSubInternals();
		ExamSubjectRuleSettingsPracticalInternalTO pesto=new ExamSubjectRuleSettingsPracticalInternalTO();
		
		if(bo.getSelectBestOfPracticalInternal()!=null)
			pesto.setSelectTheBest(String.valueOf(bo.getSelectBestOfPracticalInternal()));
			if(bo.getPracticalIntMinMarksTotal()!=null)
				pesto.setTotalMinimummumMarks(String.valueOf(bo.getPracticalIntMinMarksTotal()));
			
			if(bo.getPracticalIntMaxMarksTotal()!=null)
				pesto.setTotalMaximumMarks(String.valueOf(bo.getPracticalIntMaxMarksTotal()));
			
			if(bo.getPracticalIntEntryMaxMarksTotal()!=null)
				pesto.setTotalentryMaximumMarks(String.valueOf(bo.getPracticalIntEntryMaxMarksTotal()));
			
			if(bo.getFinalPracticalInternalIsSubInternal()!=null && bo.getFinalPracticalInternalIsSubInternal())
				pesto.setDupsubInternalChecked("on");
			
			if(bo.getFinalPracticalInternalIsAssignment()!=null && bo.getFinalPracticalInternalIsAssignment())
				pesto.setDupassignmentChecked("on");
			
			if(bo.getFinalPracticalInternalIsAttendance()!=null && bo.getFinalPracticalInternalIsAttendance())
				pesto.setDupattendanceChecked("on");
			
			if(bo.getFinalPracticalInternalEnteredMaxMark()!=null)
				pesto.setFinalEntryMaximumMarks(String.valueOf(bo.getFinalPracticalInternalEnteredMaxMark()));
			
			if(bo.getFinalPracticalInternalMaximumMark()!=null)
				pesto.setFinalInternalMaximumMarks(String.valueOf(bo.getFinalPracticalInternalMaximumMark()));
			
			if(bo.getFinalPracticalInternalMinimumMark()!=null)
				pesto.setFinalInternalMinimumMarks(String.valueOf(bo.getFinalPracticalInternalMinimumMark()));
		
		
		
		
		
		
		
		if(practicalsubInternalTOs!=null && !practicalsubInternalTOs.isEmpty()){
			Map<Integer,ExamSubjectRuleSettingsSubInternalTO> practicalSubInternal=SubjectRuleSettingsHelper.getInstance().converInternalBoToTo(subInt,'p'); 
			for(int i=0; i<practicalsubInternalTOs.size();i++){
				ExamSubjectRuleSettingsSubInternalTO to = (ExamSubjectRuleSettingsSubInternalTO) practicalsubInternalTOs.get(i);
				if(practicalSubInternal.containsKey(Integer.parseInt(to.getInternalExamTypeId()))){
					practicalsubInternalTOs.remove(to);
					ExamSubjectRuleSettingsSubInternalTO to1=practicalSubInternal.get(Integer.parseInt(to.getInternalExamTypeId()));
					to1.setType(to.getType());
					practicalsubInternalTOs.add(i,to1);
				}
			}
		}
		pesto.setSubInternalList(practicalsubInternalTOs);
		pesto.setAttendanceTypeList(attendanceTypeList);
		
		Set<SubjectRuleSettingsAttendance> attBo=bo.getExamSubjectRuleSettingsAttendances();
		if(attBo!=null && !attBo.isEmpty()){
			Iterator<SubjectRuleSettingsAttendance> itr=attBo.iterator();
			while (itr.hasNext()) {
				SubjectRuleSettingsAttendance att = (SubjectRuleSettingsAttendance) itr.next();
				if(att.getIsTheoryPractical().equals('t')){
					
					if(att.getIsLeave()!=null && att.getIsLeave())
						esto.setDupLeave("on");
					
					if(att.getIsCoCurricular()!=null && att.getIsCoCurricular())
						esto.setDupCoCurricular("on");
					
					if(att.getAttendanceTypeId()!=null)
						esto.setAttendanceTypeId(String.valueOf(att.getAttendanceTypeId()));
					
					esto.setSubjectRuleAttendanceId(att.getId());
				}else{
					
					if(att.getIsLeave()!=null && att.getIsLeave())
						pesto.setDupLeave("on");
					
					if(att.getIsCoCurricular()!=null && att.getIsCoCurricular())
						pesto.setDupCoCurricular("on");
					
					if(att.getAttendanceTypeId()!=null)
						pesto.setAttendanceTypeId(String.valueOf(att.getAttendanceTypeId()));
					
					pesto.setSubjectRuleAttendanceId(att.getId());
				}
				
			}
		}
		List<ExamSubjectRuleSettingsAssignmentTO> practcialassList=SubjectRuleSettingsHandler.getInstance().getAllAssignment();
		if(practcialassList!=null && !practcialassList.isEmpty()){
			Map<Integer,ExamSubjectRuleSettingsAssignmentTO> practicalAssignment=SubjectRuleSettingsHelper.getInstance().converAssignmentBoToTo(assignment,'p'); 
			for(int i=0; i<practcialassList.size();i++){
				ExamSubjectRuleSettingsAssignmentTO to = (ExamSubjectRuleSettingsAssignmentTO) practcialassList.get(i);
				if(practicalAssignment.containsKey(to.getAssignmentTypeId())){
					practcialassList.remove(to);
					ExamSubjectRuleSettingsAssignmentTO to1=practicalAssignment.get(to.getAssignmentTypeId());
					to1.setName(to.getName());
					practcialassList.add(i,to1);
				}
			}
		}
		pesto.setAssignmentList(practcialassList);
		
		
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> practicalmultipleAnswerScript=SubjectRuleSettingsHandler.getInstance().getAllMultipleAnswerScript();
		ExamSubjectRuleSettingsPracticalESETO peseTo=new ExamSubjectRuleSettingsPracticalESETO();
		
		if(bo.getPracticalEseIsRegular()!=null && bo.getPracticalEseIsRegular())
			peseTo.setDupregularPracticalESEChecked("on");
		
		if(bo.getPracticalEseIsMultipleAnswerScript()!=null && bo.getPracticalEseIsMultipleAnswerScript())
			peseTo.setDupmultipleAnswerScriptsChecked("on");
		
		if(bo.getPracticalEseIsMultipleEvaluator()!=null && bo.getPracticalEseIsMultipleEvaluator())
			peseTo.setDupmultipleEvaluatorChecked("on");
		
		if(bo.getPracticalEseEnteredMaxMark()!=null)
			peseTo.setMaximumEntryMarksPracticalESE(String.valueOf(bo.getPracticalEseEnteredMaxMark()));
		
		if(bo.getPracticalEseMaximumMark()!=null)
			peseTo.setMaximumMarksPracticalESE(String.valueOf(bo.getPracticalEseMaximumMark()));
		
		if(bo.getPracticalEseMinimumMark()!=null)
			peseTo.setMinimumMarksPracticalESE(String.valueOf(bo.getPracticalEseMinimumMark()));
		
		if(bo.getPracticalEseTheoryFinalMinimumMark()!=null)
			peseTo.setMinimumTheoryFinalMarksPracticalESE(String.valueOf(bo.getPracticalEseTheoryFinalMinimumMark()));
		
		if(bo.getPracticalEseTheoryFinalMaximumMark()!=null)
			peseTo.setMaximumTheoryFinalMarksPracticalESE(String.valueOf(bo.getPracticalEseTheoryFinalMaximumMark()));
		
		if(bo.getPracticalSupplementaryMaxMarksTotal()!=null)
			peseTo.setSupplementaryMaxMarks(String.valueOf(bo.getPracticalSupplementaryMaxMarksTotal()));
		
		if(bo.getPracticalSupplementaryMinMarksTotal()!=null)
			peseTo.setSupplementaryMinMarks(String.valueOf(bo.getPracticalSupplementaryMinMarksTotal()));
		
		if(bo.getPracticalIsSupplementary()!=null && bo.getPracticalIsSupplementary()){
			peseTo.setDupSupplementaryChecked("on");
		}
		
		
		if(practicalmultipleAnswerScript!=null && !practicalmultipleAnswerScript.isEmpty()){
			Map<Integer,ExamSubjectRuleSettingsMultipleAnswerScriptTO> practicalMulAns=SubjectRuleSettingsHelper.getInstance().converMulAnsBoToTo(mulAns,'p'); 
			for(int i=0; i<practicalmultipleAnswerScript.size();i++){
				ExamSubjectRuleSettingsMultipleAnswerScriptTO to = (ExamSubjectRuleSettingsMultipleAnswerScriptTO) practicalmultipleAnswerScript.get(i);
				if(practicalMulAns.containsKey(to.getMultipleAnswerScriptId())){
					practicalmultipleAnswerScript.remove(to);
					ExamSubjectRuleSettingsMultipleAnswerScriptTO to1=practicalMulAns.get(to.getMultipleAnswerScriptId());
					to1.setName(to.getName());
					practicalmultipleAnswerScript.add(i,to1);
				}
			}
		}
		peseTo.setMultipleAnswerScriptList(practicalmultipleAnswerScript);
		
		Set<SubjectRuleSettingsMulEvaluator> evSet=bo.getExamSubjectRuleSettingsMulEvaluators();
		if(evSet!=null && !evSet.isEmpty()){
			Iterator<SubjectRuleSettingsMulEvaluator> itr=evSet.iterator();
			while (itr.hasNext()) {
				SubjectRuleSettingsMulEvaluator ev = (SubjectRuleSettingsMulEvaluator) itr.next();
				if(ev.getIsTheoryPractical().equals('t')){
					if(ev.getEvaluatorId()!=null){
						teseTo.setNoOfEvaluations(String.valueOf(ev.getNoOfEvaluations()));
						teseTo.setSelectTypeOfEvaluation(ev.getTypeOfEvaluation());
						if(ev.getEvaluatorId()==1){
							teseTo.setEvalId1(String.valueOf(ev.getEvaluatorId()));
							teseTo.setId1(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								teseTo.setReviewer("r1");
							}
							
						}
						if(ev.getEvaluatorId()==2){
							teseTo.setEvalId2(String.valueOf(ev.getEvaluatorId()));
							teseTo.setId2(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								teseTo.setReviewer("r2");
							}
							
						}
						if(ev.getEvaluatorId()==3){
							teseTo.setEvalId3(String.valueOf(ev.getEvaluatorId()));
							teseTo.setId3(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								teseTo.setReviewer("r3");
							}
						}
						if(ev.getEvaluatorId()==4){
							teseTo.setEvalId4(String.valueOf(ev.getEvaluatorId()));
							teseTo.setId4(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								teseTo.setReviewer("r4");
							}
							
						}
						if(ev.getEvaluatorId()==5){
							teseTo.setEvalId5(String.valueOf(ev.getEvaluatorId()));
							teseTo.setId5(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								teseTo.setReviewer("r5");
							}
						}
					}
				}else{
					peseTo.setNoOfEvaluations(String.valueOf(ev.getNoOfEvaluations()));
					peseTo.setSelectTypeOfEvaluation(ev.getTypeOfEvaluation());
					if(ev.getEvaluatorId()!=null){
						if(ev.getEvaluatorId()==1){
							peseTo.setEvalId1(String.valueOf(ev.getEvaluatorId()));
							peseTo.setId1(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								peseTo.setReviewer("r1");
							}
							
						}
						if(ev.getEvaluatorId()==2){
							peseTo.setEvalId2(String.valueOf(ev.getEvaluatorId()));
							peseTo.setId2(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								peseTo.setReviewer("r2");
							}
						}
						if(ev.getEvaluatorId()==3){
							peseTo.setEvalId3(String.valueOf(ev.getEvaluatorId()));
							peseTo.setId3(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								peseTo.setReviewer("r3");
							}
							
						}
						if(ev.getEvaluatorId()==4){
							peseTo.setEvalId4(String.valueOf(ev.getEvaluatorId()));
							peseTo.setId4(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								peseTo.setReviewer("r4");
							}
						}
						if(ev.getEvaluatorId()==5){
							peseTo.setEvalId5(String.valueOf(ev.getEvaluatorId()));
							peseTo.setId5(String.valueOf(ev.getId()));
							if(ev.getIsReviewer()!=null && ev.getIsReviewer()){
								peseTo.setReviewer("r5");
							}
						}
							
					}
				}
				
			}
		}
		if(bo.getSubjectFinalIsAttendance()!=null && bo.getSubjectFinalIsAttendance())
			subjectRuleSettingsForm.setDupattendance("on");
		if(bo.getSubjectFinalIsInternalExam()!=null && bo.getSubjectFinalIsInternalExam())
			subjectRuleSettingsForm.setDupinternalExam("on");
		if(bo.getSubjectFinalIsPracticalExam()!=null && bo.getSubjectFinalIsPracticalExam())
			subjectRuleSettingsForm.setDuppracticalExam("on");
		if(bo.getSubjectFinalIsTheoryExam()!=null && bo.getSubjectFinalIsTheoryExam())
			subjectRuleSettingsForm.setDuptheoryExam("on");
		if(bo.getSubjectFinalMaximum()!=null)
			subjectRuleSettingsForm.setMaximum(String.valueOf(bo.getSubjectFinalMaximum()));
		if(bo.getSubjectFinalMinimum()!=null)
			subjectRuleSettingsForm.setMinimum(String.valueOf(bo.getSubjectFinalMinimum()));
		if(bo.getSubjectFinalValuated()!=null)
			subjectRuleSettingsForm.setValuated(String.valueOf(bo.getSubjectFinalValuated()));
			
		subjectRuleSettingsForm.setEsto(esto);
		subjectRuleSettingsForm.setTeseTo(teseTo);
		subjectRuleSettingsForm.setPesto(pesto);
		subjectRuleSettingsForm.setPeseTo(peseTo);
	
		
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @throws Exception
	 */
	public boolean deleteSubjectRuleSettingsForSubject(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		String query = SubjectRuleSettingsHelper.getInstance().getBoQuery(subjectRuleSettingsForm);
		return transaction.deleteSubjectRuleSettingsForSubject(subjectRuleSettingsForm,query);
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateSubjectRuleSettingsForSubject(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception{
		return transaction.reActivateSubjectRuleSettings(subjectRuleSettingsForm.getId());
	}
	/** 
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteCompleteSubjectRuleSettings(SubjectRuleSettingsForm subjectRuleSettingsForm)  throws Exception{
		String query=SubjectRuleSettingsHelper.getInstance().getDuplicateQuery(subjectRuleSettingsForm);
		return transaction.deleteCompleteSubjectRuleSettings(subjectRuleSettingsForm,query);
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public List<String> checkExistedForCopy(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		String query=SubjectRuleSettingsHelper.getInstance().getCheckExistedForCopyQuery(subjectRuleSettingsForm);
		return transaction.checkExitedForCopy(query, subjectRuleSettingsForm.getCourseIds());
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public List<String> getSubjectsForCopy(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception{
		return transaction.getSubjectsForCopy(subjectRuleSettingsForm);
	}
	/**
	 * @param list
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public boolean copySubjectRuleSettings(List<String> list,SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		String schemeNo="";
		if(subjectRuleSettingsForm.getSchemeNo()!=null && !subjectRuleSettingsForm.getSchemeNo().isEmpty()){
			schemeNo=subjectRuleSettingsForm.getSchemeNo();
		}else{
			if(!subjectRuleSettingsForm.getSchemeType().isEmpty()){
				if(subjectRuleSettingsForm.getSchemeType().equalsIgnoreCase("1")){
					schemeNo="1,3,5,7,9";
				}else if(subjectRuleSettingsForm.getSchemeType().equalsIgnoreCase("2")){
					schemeNo="2,4,6,8,10";
				}else{
					schemeNo="1,2,3,4,5,6,7,8,9,10";
				}
			}
		}
		String [] tempArray = subjectRuleSettingsForm.getCourseIds();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<tempArray.length;i++){
			 intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		String query="from SubjectRuleSettings s where s.isActive=1 and s.course.id in ("+intType+") " +
				"and s.academicYear=" +subjectRuleSettingsForm.getAcademicYear()+
				" and s.schemeNo in ("+schemeNo+")";
		return transaction.copySubjectRuleSettings(list,query,subjectRuleSettingsForm);
	}
}
