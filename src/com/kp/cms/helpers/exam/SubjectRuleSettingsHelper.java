package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamAssignmentTypeMasterBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.bo.exam.SubjectRuleSettingsAssignment;
import com.kp.cms.bo.exam.SubjectRuleSettingsAttendance;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulAnsScript;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulEvaluator;
import com.kp.cms.bo.exam.SubjectRuleSettingsSubInternal;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction;
import com.kp.cms.transactionsimpl.exam.SubjectRuleSettingsTransactionImpl;

public class SubjectRuleSettingsHelper {
	ISubjectRuleSettingsTransaction transaction=SubjectRuleSettingsTransactionImpl.getInstance();
	/**
	 * Singleton object of SubjectRuleSettingsHelper
	 */
	private static volatile SubjectRuleSettingsHelper subjectRuleSettingsHelper = null;
	private static final Log log = LogFactory.getLog(SubjectRuleSettingsHelper.class);
	private SubjectRuleSettingsHelper() {
		
	}
	/**
	 * return singleton object of SubjectRuleSettingsHelper.
	 * @return
	 */
	public static SubjectRuleSettingsHelper getInstance() {
		if (subjectRuleSettingsHelper == null) {
			subjectRuleSettingsHelper = new SubjectRuleSettingsHelper();
		}
		return subjectRuleSettingsHelper;
	}
	public String getDuplicateQuery(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception{
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
		String query="select e from ExamSubjectRuleSettingsBO e" +
				" where e.academicYear= " +subjectRuleSettingsForm.getAcademicYear()+
				" and e.courseId in ("+intType+") and e.schemeNo in ("+schemeNo+") group by e.courseId";
		return query;
	}
	public List<ExamSubjectRuleSettingsSubInternalTO> convertExamInternalExamTypeBotoToList(List<ExamInternalExamTypeBO> bos) throws Exception {
		List<ExamSubjectRuleSettingsSubInternalTO> tos=new ArrayList<ExamSubjectRuleSettingsSubInternalTO>();
		if(bos!=null && !bos.isEmpty()){
			Iterator<ExamInternalExamTypeBO> itr=bos.iterator();
			while (itr.hasNext()) {
				ExamInternalExamTypeBO bo = (ExamInternalExamTypeBO) itr.next();
				ExamSubjectRuleSettingsSubInternalTO to=new ExamSubjectRuleSettingsSubInternalTO();
				to.setInternalExamTypeId(String.valueOf(bo.getId()));
				to.setType(bo.getName());
				tos.add(to);
			}
		}
		return tos;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<ExamSubjectRuleSettingsAssignmentTO> convertExamAssignmentBotoToList(
			List<ExamAssignmentTypeMasterBO> list) throws Exception {
		List<ExamSubjectRuleSettingsAssignmentTO> tos=new ArrayList<ExamSubjectRuleSettingsAssignmentTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<ExamAssignmentTypeMasterBO> itr=list.iterator();
			while (itr.hasNext()) {
				ExamAssignmentTypeMasterBO bo = (ExamAssignmentTypeMasterBO) itr.next();
				ExamSubjectRuleSettingsAssignmentTO to=new ExamSubjectRuleSettingsAssignmentTO();
				to.setAssignmentTypeId(bo.getId());
				to.setName(bo.getName());
				tos.add(to);
			}
		}
		return tos;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> convertExamMultipleAnswerScriptBotoToList(List<ExamMultipleAnswerScriptMasterBO> list) throws Exception{
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> tos=new ArrayList<ExamSubjectRuleSettingsMultipleAnswerScriptTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<ExamMultipleAnswerScriptMasterBO> itr=list.iterator();
			while (itr.hasNext()) {
				ExamMultipleAnswerScriptMasterBO bo = (ExamMultipleAnswerScriptMasterBO) itr.next();
				ExamSubjectRuleSettingsMultipleAnswerScriptTO to=new ExamSubjectRuleSettingsMultipleAnswerScriptTO();
				to.setMultipleAnswerScriptId(bo.getId());
				to.setName(bo.getName());
				tos.add(to);
			}
		}
		return tos;
	}
	public List<SubjectRuleSettings> getBoObject(SubjectRuleSettingsForm subjectRuleSettingsForm, String mode) throws Exception {
		String schemeNo="";
		if(subjectRuleSettingsForm.getSchemeNo()!=null && !subjectRuleSettingsForm.getSchemeNo().isEmpty()){
			schemeNo=subjectRuleSettingsForm.getSchemeNo();
		}else{
			if(subjectRuleSettingsForm.getSchemeType()!=null){
				if(subjectRuleSettingsForm.getSchemeType().equalsIgnoreCase("1")){
					schemeNo="1,3,5,7,9";
				}else if(subjectRuleSettingsForm.getSchemeType().equalsIgnoreCase("2")){
					schemeNo="2,4,6,8,10";
				}else {
					schemeNo="1,2,3,4,5,6,7,8,9,10";
				}
			}
		}
		List<SubjectRuleSettings> bos=new ArrayList<SubjectRuleSettings>();
		String[] schemes=schemeNo.split(",");
		String[] selecCourses=subjectRuleSettingsForm.getCourseIds();
		for (int j = 0; j < selecCourses.length; j++) {
			for (int i = 0; i < schemes.length; i++) {
				List<Subject> list=transaction.getSubjectsByCourseYearSemester(subjectRuleSettingsForm.getAcademicYear(),selecCourses[j],schemes[i]);
				Iterator<Subject> itr=list.iterator();
				while (itr.hasNext()) {
					Subject subject = (Subject) itr.next();
					SubjectRuleSettings bo=new SubjectRuleSettings();
					bo.setAcademicYear(Integer.parseInt(subjectRuleSettingsForm.getAcademicYear()));
					Course course =new Course();
					course.setId(Integer.parseInt(selecCourses[j]));
					bo.setCourse(course);
					bo.setSubject(subject);
//					bo.setCourseId(Integer.parseInt());
//					bo.setSubjectId(subject.getId());
					
					bo.setIsActive(true);
					bo.setCreatedBy(subjectRuleSettingsForm.getUserId());
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(subjectRuleSettingsForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bo.setSchemeNo(Integer.parseInt(schemes[i]));
					// setting Internal Data To Subject Rule Setting Bo
					Set<SubjectRuleSettingsSubInternal> subInt1=setSubInternalTheoryDataToBo(subjectRuleSettingsForm.getEsto());
					Set<SubjectRuleSettingsSubInternal> subInt2=setSubInternalPracticalDataToBo(subjectRuleSettingsForm.getPesto());
					Set<SubjectRuleSettingsSubInternal> totalInt=new HashSet<SubjectRuleSettingsSubInternal>();
					totalInt.addAll(subInt1);
					totalInt.addAll(subInt2);
					if(!totalInt.isEmpty()){
						bo.setExamSubjectRuleSettingsSubInternals(totalInt);
					}
					
					
					// setting assignment Data To Subject Rule Setting Bo
					Set<SubjectRuleSettingsAssignment> ass1=setAssignmentTheoryDataToBo(bo,subjectRuleSettingsForm.getEsto());
					Set<SubjectRuleSettingsAssignment> ass2=setAssignmentPracticalDataToBo(bo,subjectRuleSettingsForm.getPesto());
					Set<SubjectRuleSettingsAssignment> totalAss=new HashSet<SubjectRuleSettingsAssignment>();
					totalAss.addAll(ass1);
					totalAss.addAll(ass2);
					if(!totalAss.isEmpty()){
						bo.setExamSubjectRuleSettingsAssignments(totalAss);
					}
					
					
					// setting attendance Data To Subject Rule Setting Bo
					SubjectRuleSettingsAttendance attTheory=setAttendanceTheoryDataToBo(bo,subjectRuleSettingsForm.getEsto());
					SubjectRuleSettingsAttendance attPractical=setAttendancePracticalDataToBo(bo,subjectRuleSettingsForm.getPesto());
					
					if(attTheory!=null || attPractical!=null){
						Set<SubjectRuleSettingsAttendance> attSet=new HashSet<SubjectRuleSettingsAttendance>();
						if(attTheory!=null)
							attSet.add(attTheory);
						if(attPractical!=null)
							attSet.add(attPractical);
					
						bo.setExamSubjectRuleSettingsAttendances(attSet);
					}
					
					// setting multiple AnswerScript to Subject Rule Setting Bo
					Set<SubjectRuleSettingsMulAnsScript> mulAns1=setMultipleAnswerScriptTheoryToSubjectRuleSetting(bo,subjectRuleSettingsForm.getTeseTo());
					Set<SubjectRuleSettingsMulAnsScript> mulAns2=setMultipleAnswerScriptPracticalToSubjectRuleSetting(bo,subjectRuleSettingsForm.getPeseTo());
					Set<SubjectRuleSettingsMulAnsScript> totalMulAns=new HashSet<SubjectRuleSettingsMulAnsScript>();
					totalMulAns.addAll(mulAns1);
					totalMulAns.addAll(mulAns2);
					if(!totalMulAns.isEmpty()){
						bo.setExamSubjectRuleSettingsMulAnsScripts(totalMulAns);
					}
					
					
					// setting Multiple Evaluator  TO Subject Rule Setting
					Set<SubjectRuleSettingsMulEvaluator> evIdSet1=setMultipleEvaluatorTheoryToBo(bo,subjectRuleSettingsForm.getTeseTo());
					Set<SubjectRuleSettingsMulEvaluator> evIdSet2=setMultipleEvaluatorPracticalToBo(bo,subjectRuleSettingsForm.getPeseTo());
					
					Set<SubjectRuleSettingsMulEvaluator> totalEvIds=new HashSet<SubjectRuleSettingsMulEvaluator>();
					totalEvIds.addAll(evIdSet1);
					totalEvIds.addAll(evIdSet2);
					if(!totalEvIds.isEmpty()){
						bo.setExamSubjectRuleSettingsMulEvaluators(totalEvIds);
					}
					
					ExamSubjectRuleSettingsTheoryInternalTO esto=subjectRuleSettingsForm.getEsto();
					if(esto.getSelectTheBest()!=null && !esto.getSelectTheBest().isEmpty()){
						bo.setSelectBestOfTheoryInternal(Integer.parseInt(esto.getSelectTheBest()));
					}
					if(esto.getSubInternalChecked()!=null && esto.getSubInternalChecked().equalsIgnoreCase("on")){
						bo.setFinalTheoryInternalIsSubInternal(true);
					}else{
						bo.setFinalTheoryInternalIsSubInternal(false);
					}
					if(esto.getAttendanceChecked()!=null && esto.getAttendanceChecked().equalsIgnoreCase("on")){
						bo.setFinalTheoryInternalIsAttendance(true);
					}else{
						bo.setFinalTheoryInternalIsAttendance(false);
					}
					if(esto.getAssignmentChecked()!=null && esto.getAssignmentChecked().equalsIgnoreCase("on")){
						bo.setFinalTheoryInternalIsAssignment(true);
					}else{
						bo.setFinalTheoryInternalIsAssignment(false);
					}
					if(esto.getFinalInternalMinimumMarks()!=null && !esto.getFinalInternalMinimumMarks().isEmpty()){
						bo.setFinalTheoryInternalMinimumMark(BigDecimal.valueOf(Double.valueOf(esto.getFinalInternalMinimumMarks())));
					}
					if(esto.getFinalInternalMaximumMarks()!=null && !esto.getFinalInternalMaximumMarks().isEmpty()){
						bo.setFinalTheoryInternalMaximumMark(BigDecimal.valueOf(Double.valueOf(esto.getFinalInternalMaximumMarks())));
					}
					if(esto.getFinalEntryMaximumMarks()!=null && !esto.getFinalEntryMaximumMarks().isEmpty()){
						bo.setFinalTheoryInternalEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(esto.getFinalEntryMaximumMarks())));
					}
					if(esto.getTotalentryMaximumMarks()!=null && !esto.getTotalentryMaximumMarks().isEmpty()){
						bo.setTheoryIntEntryMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(esto.getTotalentryMaximumMarks())));
					}
					if(esto.getTotalMaximumMarks()!=null && !esto.getTotalMaximumMarks().isEmpty()){
						bo.setTheoryIntMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(esto.getTotalMaximumMarks())));
					}
					if(esto.getTotalMinimummumMarks()!=null && !esto.getTotalMinimummumMarks().isEmpty()){
						bo.setTheoryIntMinMarksTotal(BigDecimal.valueOf(Double.valueOf(esto.getTotalMinimummumMarks())));
					}
					
					ExamSubjectRuleSettingsTheoryESETO teseTo=subjectRuleSettingsForm.getTeseTo();
					if(teseTo!=null){
						if(teseTo.getRegularTheoryESEChecked()!=null && teseTo.getRegularTheoryESEChecked().equalsIgnoreCase("on")){
							bo.setTheoryEseIsRegular(true);
						}else{
							bo.setTheoryEseIsRegular(false);
						}
						if(teseTo.getMultipleAnswerScriptsChecked()!=null && teseTo.getMultipleAnswerScriptsChecked().equalsIgnoreCase("on")){
							bo.setTheoryEseIsMultipleAnswerScript(true);
						}else{
							bo.setTheoryEseIsMultipleAnswerScript(false);
						}
						if(teseTo.getMultipleEvaluatorChecked()!=null && teseTo.getMultipleEvaluatorChecked().equalsIgnoreCase("on")){
							bo.setTheoryEseIsMultipleEvaluator(true);
						}else{
							bo.setTheoryEseIsMultipleEvaluator(false);
						}
						if(teseTo.getMaximumEntryMarksTheoryESE()!=null && !teseTo.getMaximumEntryMarksTheoryESE().isEmpty()){
							bo.setTheoryEseEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMaximumEntryMarksTheoryESE())));
						}
						if(teseTo.getMinimumMarksTheoryESE()!=null && !teseTo.getMinimumMarksTheoryESE().isEmpty()){
							bo.setTheoryEseMinimumMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMinimumMarksTheoryESE())));
						}
						if(teseTo.getMaximumMarksTheoryESE()!=null && !teseTo.getMaximumMarksTheoryESE().isEmpty()){
							bo.setTheoryEseMaximumMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMaximumMarksTheoryESE())));
						}
						if(teseTo.getMinimumTheoryFinalMarksTheoryESE()!=null && !teseTo.getMinimumTheoryFinalMarksTheoryESE().isEmpty()){
							bo.setTheoryEseTheoryFinalMinimumMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMinimumTheoryFinalMarksTheoryESE())));
						}
						if(teseTo.getMaximumTheoryFinalMarksTheoryESE()!=null && !teseTo.getMaximumTheoryFinalMarksTheoryESE().isEmpty()){
							bo.setTheoryEseTheoryFinalMaximumMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMaximumTheoryFinalMarksTheoryESE())));
						}
						if(teseTo.getSupplementaryChecked()!=null && !teseTo.getSupplementaryChecked().isEmpty() && teseTo.getSupplementaryChecked().equals("on")){
							bo.setTheoryIsSupplementary(true);
						}else{
							bo.setTheoryIsSupplementary(false);
						}
						if(teseTo.getSupplementaryMinMarks()!=null && !teseTo.getSupplementaryMinMarks().isEmpty()){
							bo.setTheorySupplementaryMinMarksTotal(BigDecimal.valueOf(Double.valueOf(teseTo.getSupplementaryMinMarks())));
						}
						if(teseTo.getSupplementaryMaxMarks()!=null && !teseTo.getSupplementaryMaxMarks().isEmpty()){
							bo.setTheorySupplementaryMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(teseTo.getSupplementaryMaxMarks())));
						}
					}
					
					
					ExamSubjectRuleSettingsPracticalInternalTO pesto=subjectRuleSettingsForm.getPesto();
					if(pesto!=null){
						if(pesto.getSelectTheBest()!=null && !pesto.getSelectTheBest().isEmpty()){
							bo.setSelectBestOfPracticalInternal(Integer.parseInt(pesto.getSelectTheBest()));
						}
						if(pesto.getFinalInternalMinimumMarks()!=null && !pesto.getFinalInternalMinimumMarks().isEmpty()){
							bo.setFinalPracticalInternalMinimumMark(BigDecimal.valueOf(Double.valueOf(pesto.getFinalInternalMinimumMarks())));
						}
						if(pesto.getFinalInternalMaximumMarks()!=null && !pesto.getFinalInternalMaximumMarks().isEmpty() ){
							bo.setFinalPracticalInternalMaximumMark(BigDecimal.valueOf(Double.valueOf(pesto.getFinalInternalMaximumMarks())));
						}
						if(pesto.getFinalEntryMaximumMarks()!=null && !pesto.getFinalEntryMaximumMarks().isEmpty()){
							bo.setFinalPracticalInternalEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(pesto.getFinalEntryMaximumMarks())));
						}
						if(pesto.getAttendanceChecked()!=null && pesto.getAttendanceChecked().equalsIgnoreCase("on")){
							bo.setFinalPracticalInternalIsAttendance(true);
						}else{
							bo.setFinalPracticalInternalIsAttendance(false);
						}
						if(pesto.getSubInternalChecked()!=null && pesto.getSubInternalChecked().equalsIgnoreCase("on")){
							bo.setFinalPracticalInternalIsSubInternal(true);
						}else{
							bo.setFinalPracticalInternalIsSubInternal(false);
						}
						if(pesto.getAssignmentChecked()!=null && pesto.getAssignmentChecked().equalsIgnoreCase("on")){
							bo.setFinalPracticalInternalIsAssignment(true);
						}else{
							bo.setFinalPracticalInternalIsAssignment(false);
						}
						
						if(pesto.getTotalentryMaximumMarks()!=null && !pesto.getTotalentryMaximumMarks().isEmpty()){
							bo.setPracticalIntEntryMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(pesto.getTotalentryMaximumMarks())));
						}
						if(pesto.getTotalMaximumMarks()!=null && !pesto.getTotalMaximumMarks().isEmpty()){
							bo.setPracticalIntMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(pesto.getTotalMaximumMarks())));
						}
						if(pesto.getTotalMinimummumMarks()!=null && !pesto.getTotalMinimummumMarks().isEmpty()){
							bo.setPracticalIntMinMarksTotal(BigDecimal.valueOf(Double.valueOf(pesto.getTotalMinimummumMarks())));
						}
						
					}
					
					
					ExamSubjectRuleSettingsPracticalESETO peseTo=subjectRuleSettingsForm.getPeseTo();
					if(peseTo!=null){
						if(peseTo.getRegularPracticalESEChecked()!=null && peseTo.getRegularPracticalESEChecked().equalsIgnoreCase("on")){
							bo.setPracticalEseIsRegular(true);
						}else{
							bo.setPracticalEseIsRegular(false);
						}
						if(peseTo.getMultipleAnswerScriptsChecked()!=null && peseTo.getMultipleAnswerScriptsChecked().equalsIgnoreCase("on")){
							bo.setPracticalEseIsMultipleAnswerScript(true);
						}else{
							bo.setPracticalEseIsMultipleAnswerScript(false);
						}
						if(peseTo.getMultipleEvaluatorChecked()!=null && peseTo.getMultipleEvaluatorChecked().equalsIgnoreCase("on")){
							bo.setPracticalEseIsMultipleEvaluator(true);
						}else{
							bo.setPracticalEseIsMultipleEvaluator(false);
						}
						if(peseTo.getMaximumEntryMarksPracticalESE()!=null && !peseTo.getMaximumEntryMarksPracticalESE().isEmpty()){
							bo.setPracticalEseEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMaximumEntryMarksPracticalESE())));
						}
						if(peseTo.getMinimumMarksPracticalESE()!=null && !peseTo.getMinimumMarksPracticalESE().isEmpty()){
							bo.setPracticalEseMinimumMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMinimumMarksPracticalESE())));
						}
						if(peseTo.getMaximumMarksPracticalESE()!=null && !peseTo.getMaximumMarksPracticalESE().isEmpty()){
							bo.setPracticalEseMaximumMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMaximumMarksPracticalESE())));
						}
						if(peseTo.getMaximumTheoryFinalMarksPracticalESE()!=null && !peseTo.getMaximumTheoryFinalMarksPracticalESE().isEmpty()){
							bo.setPracticalEseTheoryFinalMaximumMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMaximumTheoryFinalMarksPracticalESE())));
						}
						if(peseTo.getMinimumTheoryFinalMarksPracticalESE()!=null && !peseTo.getMinimumTheoryFinalMarksPracticalESE().isEmpty()){
							bo.setPracticalEseTheoryFinalMinimumMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMinimumTheoryFinalMarksPracticalESE())));
						}
						if(peseTo.getSupplementaryChecked()!=null && !peseTo.getSupplementaryChecked().isEmpty() && peseTo.getSupplementaryChecked().equals("on")){
							bo.setPracticalIsSupplementary(true);
						}else{
							bo.setPracticalIsSupplementary(false);
						}
						if(peseTo.getSupplementaryMinMarks()!=null && !peseTo.getSupplementaryMinMarks().isEmpty()){
							bo.setPracticalSupplementaryMinMarksTotal(BigDecimal.valueOf(Double.valueOf(teseTo.getSupplementaryMinMarks())));
						}
						if(peseTo.getSupplementaryMaxMarks()!=null && !peseTo.getSupplementaryMaxMarks().isEmpty()){
							bo.setPracticalSupplementaryMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(teseTo.getSupplementaryMaxMarks())));
						}
					}
					
					if(subjectRuleSettingsForm.getTheoryExam()!=null && subjectRuleSettingsForm.getTheoryExam().equalsIgnoreCase("on")){
						bo.setSubjectFinalIsTheoryExam(true);
					}else{
						bo.setSubjectFinalIsTheoryExam(false);
					}
					if(subjectRuleSettingsForm.getPracticalExam()!=null && subjectRuleSettingsForm.getPracticalExam().equalsIgnoreCase("on")){
						bo.setSubjectFinalIsPracticalExam(true);
					}else{
						bo.setSubjectFinalIsPracticalExam(false);
					}
					if(subjectRuleSettingsForm.getAttendance()!=null && subjectRuleSettingsForm.getAttendance().equalsIgnoreCase("on")){
						bo.setSubjectFinalIsAttendance(true);
					}else{
						bo.setSubjectFinalIsAttendance(false);
					}
					if(subjectRuleSettingsForm.getInternalExam()!=null && subjectRuleSettingsForm.getInternalExam().equalsIgnoreCase("on")){
						bo.setSubjectFinalIsInternalExam(true);
					}else{
						bo.setSubjectFinalIsInternalExam(false);
					}
					
					if(subjectRuleSettingsForm.getMinimum()!=null && !subjectRuleSettingsForm.getMinimum().isEmpty()){
						bo.setSubjectFinalMinimum(BigDecimal.valueOf(Double.valueOf(subjectRuleSettingsForm.getMinimum())));
					}
					if(subjectRuleSettingsForm.getMaximum()!=null && !subjectRuleSettingsForm.getMaximum().isEmpty()){
						bo.setSubjectFinalMaximum(BigDecimal.valueOf(Double.valueOf(subjectRuleSettingsForm.getMaximum())));
					}
					if(subjectRuleSettingsForm.getValuated()!=null && !subjectRuleSettingsForm.getValuated().isEmpty()){
						bo.setSubjectFinalValuated(BigDecimal.valueOf(Double.valueOf(subjectRuleSettingsForm.getValuated())));
					}
					bos.add(bo);
				}
			}
		}
		return bos;
	}
	/**
	 * @param esto
	 * @return
	 */
	private Set<SubjectRuleSettingsSubInternal> setSubInternalPracticalDataToBo(
			ExamSubjectRuleSettingsPracticalInternalTO esto) {
		Set<SubjectRuleSettingsSubInternal> intSet=new HashSet<SubjectRuleSettingsSubInternal>();
		List<ExamSubjectRuleSettingsSubInternalTO> list=esto.getSubInternalList();
		if(list!=null && !list.isEmpty()){
			Iterator<ExamSubjectRuleSettingsSubInternalTO> itr=list.iterator();
			while (itr.hasNext()) {
				ExamSubjectRuleSettingsSubInternalTO to = (ExamSubjectRuleSettingsSubInternalTO) itr.next();
				if((to.getMinimumMarks()!=null && !to.getMinimumMarks().isEmpty()) || (to.getMaximumMarks()!=null && !to.getMaximumMarks().isEmpty()) || (to.getEntryMaximumMarks()!=null && !to.getEntryMaximumMarks().isEmpty())){
					SubjectRuleSettingsSubInternal bo=new SubjectRuleSettingsSubInternal();
					if(to.getId()!=null && !to.getId().isEmpty()){
						bo.setId(Integer.parseInt(to.getId()));
					}
					if(to.getMinimumMarks()!=null && !to.getMinimumMarks().isEmpty()){
						bo.setMinimumMark(BigDecimal.valueOf(Double.valueOf(to.getMinimumMarks())));
					}
					if(to.getMaximumMarks()!=null && !to.getMaximumMarks().isEmpty()){
						bo.setMaximumMark(BigDecimal.valueOf(Double.valueOf(to.getMaximumMarks())));
					}
					if(to.getEntryMaximumMarks()!=null && !to.getEntryMaximumMarks().isEmpty()){
						bo.setEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(to.getEntryMaximumMarks())));
					}
					bo.setInternalExamTypeId(Integer.parseInt(to.getInternalExamTypeId()));
					bo.setIsTheoryPractical('p');
					bo.setIsActive(true);
					intSet.add(bo);
				} 
			}
		}
		return intSet;
	}
	/**
	 * @param esto
	 * @return
	 */
	private Set<SubjectRuleSettingsSubInternal> setSubInternalTheoryDataToBo(
			ExamSubjectRuleSettingsTheoryInternalTO esto) {
		Set<SubjectRuleSettingsSubInternal> intSet=new HashSet<SubjectRuleSettingsSubInternal>();
		List<ExamSubjectRuleSettingsSubInternalTO> list=esto.getSubInternalList();
		if(list!=null && !list.isEmpty()){
			Iterator<ExamSubjectRuleSettingsSubInternalTO> itr=list.iterator();
			while (itr.hasNext()) {
				ExamSubjectRuleSettingsSubInternalTO to = (ExamSubjectRuleSettingsSubInternalTO) itr.next();
				if((to.getMinimumMarks()!=null && !to.getMinimumMarks().isEmpty()) || (to.getMaximumMarks()!=null && !to.getMaximumMarks().isEmpty()) || (to.getEntryMaximumMarks()!=null && !to.getEntryMaximumMarks().isEmpty())){
					SubjectRuleSettingsSubInternal bo=new SubjectRuleSettingsSubInternal();
					if(to.getId()!=null && !to.getId().isEmpty()){
						bo.setId(Integer.parseInt(to.getId()));
					}
					if(to.getMinimumMarks()!=null && !to.getMinimumMarks().isEmpty()){
						bo.setMinimumMark(BigDecimal.valueOf(Double.valueOf(to.getMinimumMarks())));
					}
					if(to.getMaximumMarks()!=null && !to.getMaximumMarks().isEmpty()){
						bo.setMaximumMark(BigDecimal.valueOf(Double.valueOf(to.getMaximumMarks())));
					}
					if(to.getEntryMaximumMarks()!=null && !to.getEntryMaximumMarks().isEmpty()){
						bo.setEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(to.getEntryMaximumMarks())));
					}
					bo.setInternalExamTypeId(Integer.parseInt(to.getInternalExamTypeId()));
					bo.setIsTheoryPractical('t');
					bo.setIsActive(true);
					intSet.add(bo);
				} 
			}
		}
		return intSet;
	}
	/**
	 * @param bo
	 * @param peseTo
	 */
	private Set<SubjectRuleSettingsMulEvaluator> setMultipleEvaluatorPracticalToBo(
			SubjectRuleSettings bo,
			ExamSubjectRuleSettingsPracticalESETO peseTo) {
		Set<SubjectRuleSettingsMulEvaluator> evIdSet=new HashSet<SubjectRuleSettingsMulEvaluator>();
		if(peseTo.getMultipleEvaluatorChecked()!=null && peseTo.getMultipleEvaluatorChecked().equals("on")){
			if(peseTo.getEvalId1()!=null && !peseTo.getEvalId1().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(peseTo.getEvalId1()));
				if(peseTo.getId1()!=null && !peseTo.getId1().isEmpty()){
					evId1.setId(Integer.parseInt(peseTo.getId1()));
				}
				evIdSet.add(evId1);
				if(peseTo.getReviewer()!=null){
					if(peseTo.getReviewer().equalsIgnoreCase("r1")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}
				
				evId1.setIsTheoryPractical('p');
				evId1.setNoOfEvaluations(Integer.parseInt(peseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(peseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
			}
			if(peseTo.getEvalId2()!=null && !peseTo.getEvalId2().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(peseTo.getEvalId2()));
				if(peseTo.getReviewer()!=null){
					if(peseTo.getReviewer().equalsIgnoreCase("r2")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}

				evId1.setIsTheoryPractical('p');
				evId1.setNoOfEvaluations(Integer.parseInt(peseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(peseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(peseTo.getId2()!=null && !peseTo.getId1().isEmpty()){
					evId1.setId(Integer.parseInt(peseTo.getId2()));
				}
				evIdSet.add(evId1);
			}
			if(peseTo.getEvalId3()!=null && !peseTo.getEvalId3().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(peseTo.getEvalId3()));
				if(peseTo.getReviewer()!=null){
					if(peseTo.getReviewer().equalsIgnoreCase("r3")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}
				evId1.setIsTheoryPractical('p');
				evId1.setNoOfEvaluations(Integer.parseInt(peseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(peseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(peseTo.getId3()!=null && !peseTo.getId3().isEmpty()){
					evId1.setId(Integer.parseInt(peseTo.getId3()));
				}
				evIdSet.add(evId1);
			}
			if(peseTo.getEvalId4()!=null && !peseTo.getEvalId4().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(peseTo.getEvalId4()));
				if(peseTo.getReviewer()!=null){
					if(peseTo.getReviewer().equalsIgnoreCase("r4")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}
				evId1.setIsTheoryPractical('p');
				evId1.setNoOfEvaluations(Integer.parseInt(peseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(peseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(peseTo.getId4()!=null && !peseTo.getId4().isEmpty()){
					evId1.setId(Integer.parseInt(peseTo.getId4()));
				}
				evIdSet.add(evId1);
			}
			if(peseTo.getEvalId5()!=null && !peseTo.getEvalId5().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(peseTo.getEvalId5()));
				if(peseTo.getReviewer()!=null){
					if(peseTo.getReviewer().equalsIgnoreCase("r5")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}
				evId1.setIsTheoryPractical('p');
				evId1.setNoOfEvaluations(Integer.parseInt(peseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(peseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(peseTo.getId5()!=null && !peseTo.getId5().isEmpty()){
					evId1.setId(Integer.parseInt(peseTo.getId5()));
				}
				evIdSet.add(evId1);
			}
		}
		return evIdSet;
	}
	/**
	 * @param bo
	 * @param teseTo
	 */
	private Set<SubjectRuleSettingsMulEvaluator> setMultipleEvaluatorTheoryToBo(SubjectRuleSettings bo,
			ExamSubjectRuleSettingsTheoryESETO teseTo) {
		Set<SubjectRuleSettingsMulEvaluator> evIdSet=new HashSet<SubjectRuleSettingsMulEvaluator>();
		if(teseTo.getMultipleEvaluatorChecked()!=null && teseTo.getMultipleEvaluatorChecked().equals("on")){
			if(teseTo.getEvalId1()!=null && !teseTo.getEvalId1().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(teseTo.getEvalId1()));
				if(teseTo.getReviewer()!=null){
					if(teseTo.getReviewer().equalsIgnoreCase("r1")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}
				evId1.setIsTheoryPractical('t');
				evId1.setNoOfEvaluations(Integer.parseInt(teseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(teseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(teseTo.getId1()!=null && !teseTo.getId1().isEmpty()){
					evId1.setId(Integer.parseInt(teseTo.getId1()));
				}
				evIdSet.add(evId1);
			}
			if(teseTo.getEvalId2()!=null && !teseTo.getEvalId2().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(teseTo.getEvalId2()));
				if(teseTo.getReviewer()!=null){
					if(teseTo.getReviewer().equalsIgnoreCase("r2")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}
				evId1.setIsTheoryPractical('t');
				evId1.setNoOfEvaluations(Integer.parseInt(teseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(teseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(teseTo.getId2()!=null && !teseTo.getId2().isEmpty()){
					evId1.setId(Integer.parseInt(teseTo.getId2()));
				}
				evIdSet.add(evId1);
			}
			if(teseTo.getEvalId3()!=null && !teseTo.getEvalId3().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(teseTo.getEvalId3()));
				if(teseTo.getReviewer()!=null){
					if(teseTo.getReviewer().equalsIgnoreCase("r3")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}
				evId1.setIsTheoryPractical('t');
				evId1.setNoOfEvaluations(Integer.parseInt(teseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(teseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(teseTo.getId3()!=null && !teseTo.getId3().isEmpty()){
					evId1.setId(Integer.parseInt(teseTo.getId3()));
				}
				evIdSet.add(evId1);
			}
			if(teseTo.getEvalId4()!=null && !teseTo.getEvalId4().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(teseTo.getEvalId4()));
				if(teseTo.getReviewer()!=null){
					if(teseTo.getReviewer().equalsIgnoreCase("r4")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}
				evId1.setIsTheoryPractical('t');
				evId1.setNoOfEvaluations(Integer.parseInt(teseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(teseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(teseTo.getId4()!=null && !teseTo.getId4().isEmpty()){
					evId1.setId(Integer.parseInt(teseTo.getId4()));
				}
				evIdSet.add(evId1);
			}
			if(teseTo.getEvalId5()!=null && !teseTo.getEvalId5().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(teseTo.getEvalId5()));
				if(teseTo.getReviewer()!=null){
					if(teseTo.getReviewer().equalsIgnoreCase("r5")){
						evId1.setIsReviewer(true);
					}else{
						evId1.setIsReviewer(false);
					}
				}else{
					evId1.setIsReviewer(false);
				}
				evId1.setIsTheoryPractical('t');
				evId1.setNoOfEvaluations(Integer.parseInt(teseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(teseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(teseTo.getId5()!=null && !teseTo.getId5().isEmpty()){
					evId1.setId(Integer.parseInt(teseTo.getId5()));
				}
				evIdSet.add(evId1);
			}
		}
		return evIdSet;
	}
	/**
	 * @param bo
	 * @param peseTo
	 */
	private Set<SubjectRuleSettingsMulAnsScript> setMultipleAnswerScriptPracticalToSubjectRuleSetting(
			SubjectRuleSettings bo,
			ExamSubjectRuleSettingsPracticalESETO peseTo) {
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList=peseTo.getMultipleAnswerScriptList();
		Set<SubjectRuleSettingsMulAnsScript> ansBos=new HashSet<SubjectRuleSettingsMulAnsScript>();
		if(multipleAnswerScriptList!=null && !multipleAnswerScriptList.isEmpty() && peseTo.getMultipleAnswerScriptsChecked()!=null && peseTo.getMultipleAnswerScriptsChecked().equalsIgnoreCase("on")){
			Iterator<ExamSubjectRuleSettingsMultipleAnswerScriptTO> itr=multipleAnswerScriptList.iterator();
			while (itr.hasNext()) {
				ExamSubjectRuleSettingsMultipleAnswerScriptTO to = (ExamSubjectRuleSettingsMultipleAnswerScriptTO) itr.next();
				if(to.getMultipleAnswerScriptTheoryESE()!=null && !to.getMultipleAnswerScriptTheoryESE().isEmpty()){
					SubjectRuleSettingsMulAnsScript ansBo=new SubjectRuleSettingsMulAnsScript();
					ansBo.setIsActive(true);
					ansBo.setIsTheoryPractical('p');
					ansBo.setMultipleAnswerScriptId(to.getMultipleAnswerScriptId());
					ansBo.setValue(BigDecimal.valueOf(Double.valueOf(to.getMultipleAnswerScriptTheoryESE())));
					if(to.getId()!=null && !to.getId().isEmpty()){
						ansBo.setId(Integer.parseInt(to.getId()));
					}
					ansBos.add(ansBo);
				}
			}
		}
		return ansBos;
	}
	/**
	 * @param bo
	 * @param teseTo
	 */
	private Set<SubjectRuleSettingsMulAnsScript> setMultipleAnswerScriptTheoryToSubjectRuleSetting(SubjectRuleSettings bo,
			ExamSubjectRuleSettingsTheoryESETO teseTo) {
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList=teseTo.getMultipleAnswerScriptList();
		Set<SubjectRuleSettingsMulAnsScript> ansBos=new HashSet<SubjectRuleSettingsMulAnsScript>();
		if(multipleAnswerScriptList!=null && !multipleAnswerScriptList.isEmpty() && teseTo.getMultipleAnswerScriptsChecked()!=null && teseTo.getMultipleAnswerScriptsChecked().equalsIgnoreCase("on")){
			Iterator<ExamSubjectRuleSettingsMultipleAnswerScriptTO> itr=multipleAnswerScriptList.iterator();
			while (itr.hasNext()) {
				ExamSubjectRuleSettingsMultipleAnswerScriptTO to = (ExamSubjectRuleSettingsMultipleAnswerScriptTO) itr.next();
				if(to.getMultipleAnswerScriptTheoryESE()!=null && !to.getMultipleAnswerScriptTheoryESE().isEmpty()){
					SubjectRuleSettingsMulAnsScript ansBo=new SubjectRuleSettingsMulAnsScript();
				ansBo.setIsActive(true);
				ansBo.setIsTheoryPractical('t');
				ansBo.setMultipleAnswerScriptId(to.getMultipleAnswerScriptId());
				ansBo.setValue(BigDecimal.valueOf(Double.valueOf(to.getMultipleAnswerScriptTheoryESE())));
				if(to.getId()!=null && !to.getId().isEmpty()){
					ansBo.setId(Integer.parseInt(to.getId()));
				}
				ansBos.add(ansBo);
				}
			}
		}
		return ansBos;
	}
	/**
	 * @param bo
	 * @param pesto
	 */
	private SubjectRuleSettingsAttendance setAttendancePracticalDataToBo(SubjectRuleSettings bo,
			ExamSubjectRuleSettingsPracticalInternalTO pesto) {
		SubjectRuleSettingsAttendance attBo=null;
		if(pesto.getAttendanceTypeId()!=null && !pesto.getAttendanceTypeId().isEmpty()){
			attBo=new SubjectRuleSettingsAttendance();
			attBo.setAttendanceTypeId(Integer.parseInt(pesto.getAttendanceTypeId()));
			attBo.setIsActive(true);
			
			if(pesto.getLeaveAttendance()!=null && pesto.getLeaveAttendance().equalsIgnoreCase("on"))
				attBo.setIsLeave(true);
			else
				attBo.setIsLeave(false);
			
			if(pesto.getCoCurricularAttendance()!=null && pesto.getCoCurricularAttendance().equalsIgnoreCase("on"))
				attBo.setIsCoCurricular(true);
			else
				attBo.setIsCoCurricular(false);
			
			attBo.setIsTheoryPractical('p');
			
			if(pesto.getSubjectRuleAttendanceId()>0)
				attBo.setId(pesto.getSubjectRuleAttendanceId());
		}
		return attBo;
	}
	/**
	 * @param bo
	 * @param esto
	 */
	private SubjectRuleSettingsAttendance setAttendanceTheoryDataToBo(SubjectRuleSettings bo,ExamSubjectRuleSettingsTheoryInternalTO esto) {
		SubjectRuleSettingsAttendance attBo=null;
		if(esto.getAttendanceTypeId()!=null && !esto.getAttendanceTypeId().isEmpty()){
			attBo=new SubjectRuleSettingsAttendance();
			attBo.setAttendanceTypeId(Integer.parseInt(esto.getAttendanceTypeId()));
			attBo.setIsActive(true);
			
			if(esto.getLeaveAttendance()!=null && esto.getLeaveAttendance().equalsIgnoreCase("on"))
				attBo.setIsLeave(true);
			else
				attBo.setIsLeave(false);
			
			if(esto.getCoCurricularAttendance()!=null && esto.getCoCurricularAttendance().equalsIgnoreCase("on"))
				attBo.setIsCoCurricular(true);
			else
				attBo.setIsCoCurricular(false);
			
			attBo.setIsTheoryPractical('t');
			if(esto.getSubjectRuleAttendanceId()>0)
				attBo.setId(esto.getSubjectRuleAttendanceId());
			
		}
		return attBo;
	}
	/**
	 * @param bo
	 * @param pesto
	 */
	private Set<SubjectRuleSettingsAssignment> setAssignmentPracticalDataToBo(SubjectRuleSettings bo,
			ExamSubjectRuleSettingsPracticalInternalTO pesto) {
		List<ExamSubjectRuleSettingsAssignmentTO> list=pesto.getAssignmentList();
		Set<SubjectRuleSettingsAssignment> bolist=new HashSet<SubjectRuleSettingsAssignment>();
		if(list!=null && !list.isEmpty()){
			Iterator<ExamSubjectRuleSettingsAssignmentTO> itr=list.iterator();
			while (itr.hasNext()) {
				ExamSubjectRuleSettingsAssignmentTO to= (ExamSubjectRuleSettingsAssignmentTO) itr.next();
				SubjectRuleSettingsAssignment ao=new SubjectRuleSettingsAssignment();
				if((to.getMinimumAssignMarks()!=null && !to.getMinimumAssignMarks().isEmpty()) || (to.getMaximumAssignMarks()!=null && !to.getMaximumAssignMarks().isEmpty())){
					ao.setAssignmentTypeId(to.getAssignmentTypeId());
					if(to.getMinimumAssignMarks()!=null && to.getMinimumAssignMarks().isEmpty())
					ao.setMaximumMark(new BigDecimal(to.getMinimumAssignMarks()));
					if(to.getMinimumAssignMarks()!=null && to.getMinimumAssignMarks().isEmpty())
					ao.setMinimumMark(new BigDecimal(to.getMinimumAssignMarks()));
					ao.setIsActive(true);
					ao.setIsTheoryPractical('p');
					if(to.getId()!=null && !to.getId().isEmpty()){
						ao.setId(Integer.parseInt(to.getId()));
					}
					bolist.add(ao);
				}
			}
		}
		return bolist;
	}
	/**
	 * @param bo
	 * @param esto
	 * @throws Exception
	 */
	private Set<SubjectRuleSettingsAssignment> setAssignmentTheoryDataToBo(SubjectRuleSettings bo,ExamSubjectRuleSettingsTheoryInternalTO esto) throws Exception {
		List<ExamSubjectRuleSettingsAssignmentTO> list=esto.getAssignmentList();
		Set<SubjectRuleSettingsAssignment> bolist=new HashSet<SubjectRuleSettingsAssignment>();
		if(list!=null && !list.isEmpty()){
			Iterator<ExamSubjectRuleSettingsAssignmentTO> itr=list.iterator();
			while (itr.hasNext()) {
				ExamSubjectRuleSettingsAssignmentTO to= (ExamSubjectRuleSettingsAssignmentTO) itr.next();
				SubjectRuleSettingsAssignment ao=new SubjectRuleSettingsAssignment();
				if((to.getMinimumAssignMarks()!=null && !to.getMinimumAssignMarks().isEmpty()) || (to.getMaximumAssignMarks()!=null && !to.getMaximumAssignMarks().isEmpty())){
					ao.setAssignmentTypeId(to.getAssignmentTypeId());
					if(to.getMinimumAssignMarks()!=null && to.getMinimumAssignMarks().isEmpty())
					ao.setMaximumMark(new BigDecimal(to.getMinimumAssignMarks()));
					if(to.getMinimumAssignMarks()!=null && to.getMinimumAssignMarks().isEmpty())
					ao.setMinimumMark(new BigDecimal(to.getMinimumAssignMarks()));
					ao.setIsActive(true);
					ao.setIsTheoryPractical('t');
					if(to.getId()!=null && !to.getId().isEmpty()){
						ao.setId(Integer.parseInt(to.getId()));
					}
					bolist.add(ao);
				}
			}
		}
		return bolist;
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public String getCheckExistedQuery(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
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
		String query="select e.courseId from ExamSubjectRuleSettingsBO e" +
				" where e.academicYear= " +subjectRuleSettingsForm.getAcademicYear()+
				" and e.courseId in ("+intType+") and e.schemeNo in ("+schemeNo+") group by e.courseId";
		return query;
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public String getSubjectsForCourse(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		String [] tempArray = subjectRuleSettingsForm.getCourseIds();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		
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
		
		String query="select s.subject.name,s.subject.id," +
				"c.curriculumSchemeDuration.semesterYearNo," +
				"c.curriculumSchemeDuration.academicYear," +
				"c.curriculumSchemeDuration.curriculumScheme.course.id," +
				"c.curriculumSchemeDuration.curriculumScheme.course.name,s.subject.code  " +
				" from CurriculumSchemeSubject c" +
				" join c.subjectGroup.subjectGroupSubjectses s" +
				" where c.curriculumSchemeDuration.curriculumScheme.course in ("+intType+")" +
				" and c.curriculumSchemeDuration.academicYear=" +subjectRuleSettingsForm.getAcademicYear()+
				" and s.subject.isActive=1 and s.isActive=1 and c.curriculumSchemeDuration.semesterYearNo in ("+schemeNo+")"+
				" group by c.curriculumSchemeDuration.semesterYearNo,s.subject.id,c.curriculumSchemeDuration.curriculumScheme.course.id,c.curriculumSchemeDuration.academicYear" +
				" order by c.curriculumSchemeDuration.semesterYearNo";
		return query;
	}
	/**
	 * @param subjList
	 * @return
	 */
	public List<SubjectTO> convertBotoTo(List<Object[]> subjList) {
		List<SubjectTO> subTo=new ArrayList<SubjectTO>();
		if(subjList!=null && !subjList.isEmpty()){
			Iterator<Object[]> itr=subjList.iterator();
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				SubjectTO to=new SubjectTO();
				if(objects[1]!=null && objects[4]!=null){
					to.setId(Integer.parseInt(objects[1].toString()));
					to.setSubjectName(objects[0].toString()+"("+objects[6].toString()+")");
					to.setCourseId(objects[4].toString());
					to.setSchemeNo(objects[2].toString()+" semester");
					to.setSemister(objects[2].toString());
					to.setAcademicYear(objects[3].toString());
					to.setCourseName(objects[5].toString());
					subTo.add(to);
				}
			}
		}
		return subTo;
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public String getBoQuery(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		String query="from SubjectRuleSettings s where s.academicYear="+subjectRuleSettingsForm.getAcademicYear() +
				" and s.course.id="+subjectRuleSettingsForm.getCourseId()+" and s.schemeNo="+subjectRuleSettingsForm.getSchemeNo()+" and s.subject.id="+subjectRuleSettingsForm.getSubjectId();
		return query;
	}
	/**
	 * @param subInt
	 * @param c
	 * @return
	 */
	public Map<Integer, ExamSubjectRuleSettingsSubInternalTO> converInternalBoToTo(Set<SubjectRuleSettingsSubInternal> subInt, char c) {
		Map<Integer, ExamSubjectRuleSettingsSubInternalTO> map=new HashMap<Integer, ExamSubjectRuleSettingsSubInternalTO>();
		Iterator<SubjectRuleSettingsSubInternal> itr=subInt.iterator();
		while (itr.hasNext()) {
			SubjectRuleSettingsSubInternal bo = (SubjectRuleSettingsSubInternal) itr.next();
			if(bo.getIsTheoryPractical().equals(c)){
				ExamSubjectRuleSettingsSubInternalTO to=new ExamSubjectRuleSettingsSubInternalTO();
				to.setId(String.valueOf(bo.getId()));
				to.setInternalExamTypeId(String.valueOf(bo.getInternalExamTypeId()));
				to.setIsTheoryPractical(String.valueOf(bo.getIsTheoryPractical()));
				if(bo.getMinimumMark()!=null){
					to.setMinimumMarks(String.valueOf(bo.getMinimumMark()));
				}
				if(bo.getMaximumMark()!=null){
					to.setMaximumMarks(String.valueOf(bo.getMaximumMark()));
				}
				if(bo.getEnteredMaxMark()!=null){
					to.setEntryMaximumMarks(String.valueOf(bo.getEnteredMaxMark()));
				}
				map.put(bo.getInternalExamTypeId(), to);
			}
		}
		
		return map;
	}
	/**
	 * @param assignment
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, ExamSubjectRuleSettingsAssignmentTO> converAssignmentBoToTo(Set<SubjectRuleSettingsAssignment> assignment, char c) throws Exception {
		Map<Integer, ExamSubjectRuleSettingsAssignmentTO> map=new HashMap<Integer, ExamSubjectRuleSettingsAssignmentTO>();
		Iterator<SubjectRuleSettingsAssignment> itr=assignment.iterator();
		while (itr.hasNext()) {
			SubjectRuleSettingsAssignment bo = (SubjectRuleSettingsAssignment) itr.next();
			if(bo.getIsTheoryPractical().equals(c)){
				ExamSubjectRuleSettingsAssignmentTO to=new ExamSubjectRuleSettingsAssignmentTO();
				to.setId(String.valueOf(bo.getId()));
				to.setAssignmentTypeId(bo.getAssignmentTypeId());
				to.setIsTheoryPractical(String.valueOf(bo.getIsTheoryPractical()));
				if(bo.getMinimumMark()!=null){
					to.setMinimumAssignMarks(String.valueOf(bo.getMinimumMark()));
				}
				if(bo.getMaximumMark()!=null){
					to.setMaximumAssignMarks(String.valueOf(bo.getMaximumMark()));
				}
				map.put(bo.getAssignmentTypeId(), to);
			}
		}
		return map;
	}
	/**
	 * @param mulAns
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, ExamSubjectRuleSettingsMultipleAnswerScriptTO> converMulAnsBoToTo(Set<SubjectRuleSettingsMulAnsScript> mulAns, char c) throws Exception {
		Map<Integer, ExamSubjectRuleSettingsMultipleAnswerScriptTO> map=new HashMap<Integer, ExamSubjectRuleSettingsMultipleAnswerScriptTO>();
		Iterator<SubjectRuleSettingsMulAnsScript> itr=mulAns.iterator();
		while (itr.hasNext()) {
			SubjectRuleSettingsMulAnsScript bo = (SubjectRuleSettingsMulAnsScript) itr.next();
			if(bo.getIsTheoryPractical().equals(c)){
				ExamSubjectRuleSettingsMultipleAnswerScriptTO to=new ExamSubjectRuleSettingsMultipleAnswerScriptTO();
				to.setId(String.valueOf(bo.getId()));
				to.setMultipleAnswerScriptId(bo.getMultipleAnswerScriptId());
				to.setIsTheoryPractical(String.valueOf(bo.getIsTheoryPractical()));
				if(bo.getValue()!=null){
					to.setMultipleAnswerScriptTheoryESE(String.valueOf(bo.getValue()));
				}
				map.put(bo.getMultipleAnswerScriptId(), to);
			}
		}
		return map;
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<SubjectRuleSettings> getBoObjectForUpdate(SubjectRuleSettingsForm subjectRuleSettingsForm, String mode) throws Exception {
		List<SubjectRuleSettings> bos=new ArrayList<SubjectRuleSettings>();
		
		SubjectRuleSettings bo=new SubjectRuleSettings();

		if(subjectRuleSettingsForm.getId()>0)
		bo.setId(subjectRuleSettingsForm.getId());
		
		bo.setAcademicYear(Integer.parseInt(subjectRuleSettingsForm.getAcademicYear()));
//		bo.setCourseId(Integer.parseInt(subjectRuleSettingsForm.getCourseId()));
//		bo.setSubjectId(Integer.parseInt(subjectRuleSettingsForm.getSubjectId()));
		Course course=new Course();
		course.setId(Integer.parseInt(subjectRuleSettingsForm.getCourseId()));
		bo.setCourse(course);
		Subject subject=new Subject();
		subject.setId(Integer.parseInt(subjectRuleSettingsForm.getSubjectId()));
		bo.setSubject(subject);
		bo.setIsActive(true);
		bo.setModifiedBy(subjectRuleSettingsForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setSchemeNo(Integer.parseInt(subjectRuleSettingsForm.getSchemeNo()));
		
		
		// setting Internal Data To Subject Rule Setting Bo
		Set<SubjectRuleSettingsSubInternal> subInt1=setSubInternalTheoryDataToBo(subjectRuleSettingsForm.getEsto());
		Set<SubjectRuleSettingsSubInternal> subInt2=setSubInternalPracticalDataToBo(subjectRuleSettingsForm.getPesto());
		Set<SubjectRuleSettingsSubInternal> totalInt=new HashSet<SubjectRuleSettingsSubInternal>();
		totalInt.addAll(subInt1);
		totalInt.addAll(subInt2);
		if(!totalInt.isEmpty()){
			bo.setExamSubjectRuleSettingsSubInternals(totalInt);
		}
		
		
		// setting assignment Data To Subject Rule Setting Bo
		Set<SubjectRuleSettingsAssignment> ass1=setAssignmentTheoryDataToBo(bo,subjectRuleSettingsForm.getEsto());
		Set<SubjectRuleSettingsAssignment> ass2=setAssignmentPracticalDataToBo(bo,subjectRuleSettingsForm.getPesto());
		Set<SubjectRuleSettingsAssignment> totalAss=new HashSet<SubjectRuleSettingsAssignment>();
		totalAss.addAll(ass1);
		totalAss.addAll(ass2);
		if(!totalAss.isEmpty()){
			bo.setExamSubjectRuleSettingsAssignments(totalAss);
		}
		
		
		// setting attendance Data To Subject Rule Setting Bo
		SubjectRuleSettingsAttendance attTheory=setAttendanceTheoryDataToBo(bo,subjectRuleSettingsForm.getEsto());
		SubjectRuleSettingsAttendance attPractical=setAttendancePracticalDataToBo(bo,subjectRuleSettingsForm.getPesto());
		
		if(attTheory!=null || attPractical!=null){
			Set<SubjectRuleSettingsAttendance> attSet=new HashSet<SubjectRuleSettingsAttendance>();
			if(attTheory!=null)
				attSet.add(attTheory);
			if(attPractical!=null)
				attSet.add(attPractical);
		
			bo.setExamSubjectRuleSettingsAttendances(attSet);
		}
		
		// setting multiple AnswerScript to Subject Rule Setting Bo
		Set<SubjectRuleSettingsMulAnsScript> mulAns1=setMultipleAnswerScriptTheoryToSubjectRuleSetting(bo,subjectRuleSettingsForm.getTeseTo());
		Set<SubjectRuleSettingsMulAnsScript> mulAns2=setMultipleAnswerScriptPracticalToSubjectRuleSetting(bo,subjectRuleSettingsForm.getPeseTo());
		Set<SubjectRuleSettingsMulAnsScript> totalMulAns=new HashSet<SubjectRuleSettingsMulAnsScript>();
		totalMulAns.addAll(mulAns1);
		totalMulAns.addAll(mulAns2);
		if(!totalMulAns.isEmpty()){
			bo.setExamSubjectRuleSettingsMulAnsScripts(totalMulAns);
		}
		
		
		// setting Multiple Evaluator  TO Subject Rule Setting
		Set<SubjectRuleSettingsMulEvaluator> evIdSet1=setMultipleEvaluatorTheoryToBo(bo,subjectRuleSettingsForm.getTeseTo());
		Set<SubjectRuleSettingsMulEvaluator> evIdSet2=setMultipleEvaluatorPracticalToBo(bo,subjectRuleSettingsForm.getPeseTo());
		
		Set<SubjectRuleSettingsMulEvaluator> totalEvIds=new HashSet<SubjectRuleSettingsMulEvaluator>();
		totalEvIds.addAll(evIdSet1);
		totalEvIds.addAll(evIdSet2);
		if(!totalEvIds.isEmpty()){
			bo.setExamSubjectRuleSettingsMulEvaluators(totalEvIds);
		}
		
		ExamSubjectRuleSettingsTheoryInternalTO esto=subjectRuleSettingsForm.getEsto();
		if(esto.getSelectTheBest()!=null && !esto.getSelectTheBest().isEmpty()){
			bo.setSelectBestOfTheoryInternal(Integer.parseInt(esto.getSelectTheBest()));
		}
		if(esto.getSubInternalChecked()!=null && esto.getSubInternalChecked().equalsIgnoreCase("on")){
			bo.setFinalTheoryInternalIsSubInternal(true);
		}else{
			bo.setFinalTheoryInternalIsSubInternal(false);
		}
		if(esto.getAttendanceChecked()!=null && esto.getAttendanceChecked().equalsIgnoreCase("on")){
			bo.setFinalTheoryInternalIsAttendance(true);
		}else{
			bo.setFinalTheoryInternalIsAttendance(false);
		}
		if(esto.getAssignmentChecked()!=null && esto.getAssignmentChecked().equalsIgnoreCase("on")){
			bo.setFinalTheoryInternalIsAssignment(true);
		}else{
			bo.setFinalTheoryInternalIsAssignment(false);
		}
		if(esto.getFinalInternalMinimumMarks()!=null && !esto.getFinalInternalMinimumMarks().isEmpty()){
			bo.setFinalTheoryInternalMinimumMark(BigDecimal.valueOf(Double.valueOf(esto.getFinalInternalMinimumMarks())));
		}
		if(esto.getFinalInternalMaximumMarks()!=null && !esto.getFinalInternalMaximumMarks().isEmpty()){
			bo.setFinalTheoryInternalMaximumMark(BigDecimal.valueOf(Double.valueOf(esto.getFinalInternalMaximumMarks())));
		}
		if(esto.getFinalEntryMaximumMarks()!=null && !esto.getFinalEntryMaximumMarks().isEmpty()){
			bo.setFinalTheoryInternalEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(esto.getFinalEntryMaximumMarks())));
		}
		if(esto.getTotalentryMaximumMarks()!=null && !esto.getTotalentryMaximumMarks().isEmpty()){
			bo.setTheoryIntEntryMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(esto.getTotalentryMaximumMarks())));
		}
		if(esto.getTotalMaximumMarks()!=null && !esto.getTotalMaximumMarks().isEmpty()){
			bo.setTheoryIntMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(esto.getTotalMaximumMarks())));
		}
		if(esto.getTotalMinimummumMarks()!=null && !esto.getTotalMinimummumMarks().isEmpty()){
			bo.setTheoryIntMinMarksTotal(BigDecimal.valueOf(Double.valueOf(esto.getTotalMinimummumMarks())));
		}
		
		ExamSubjectRuleSettingsTheoryESETO teseTo=subjectRuleSettingsForm.getTeseTo();
		if(teseTo!=null){
			if(teseTo.getRegularTheoryESEChecked()!=null && teseTo.getRegularTheoryESEChecked().equalsIgnoreCase("on")){
				bo.setTheoryEseIsRegular(true);
			}else{
				bo.setTheoryEseIsRegular(false);
			}
			if(teseTo.getMultipleAnswerScriptsChecked()!=null && teseTo.getMultipleAnswerScriptsChecked().equalsIgnoreCase("on")){
				bo.setTheoryEseIsMultipleAnswerScript(true);
			}else{
				bo.setTheoryEseIsMultipleAnswerScript(false);
			}
			if(teseTo.getMultipleEvaluatorChecked()!=null && teseTo.getMultipleEvaluatorChecked().equalsIgnoreCase("on")){
				bo.setTheoryEseIsMultipleEvaluator(true);
			}else{
				bo.setTheoryEseIsMultipleEvaluator(false);
			}
			if(teseTo.getMaximumEntryMarksTheoryESE()!=null && !teseTo.getMaximumEntryMarksTheoryESE().isEmpty()){
				bo.setTheoryEseEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMaximumEntryMarksTheoryESE())));
			}
			if(teseTo.getMinimumMarksTheoryESE()!=null && !teseTo.getMinimumMarksTheoryESE().isEmpty()){
				bo.setTheoryEseMinimumMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMinimumMarksTheoryESE())));
			}
			if(teseTo.getMaximumMarksTheoryESE()!=null && !teseTo.getMaximumMarksTheoryESE().isEmpty()){
				bo.setTheoryEseMaximumMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMaximumMarksTheoryESE())));
			}
			if(teseTo.getMinimumTheoryFinalMarksTheoryESE()!=null && !teseTo.getMinimumTheoryFinalMarksTheoryESE().isEmpty()){
				bo.setTheoryEseTheoryFinalMinimumMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMinimumTheoryFinalMarksTheoryESE())));
			}
			if(teseTo.getMaximumTheoryFinalMarksTheoryESE()!=null && !teseTo.getMaximumTheoryFinalMarksTheoryESE().isEmpty()){
				bo.setTheoryEseTheoryFinalMaximumMark(BigDecimal.valueOf(Double.valueOf(teseTo.getMaximumTheoryFinalMarksTheoryESE())));
			}
			if(teseTo.getSupplementaryChecked()!=null && !teseTo.getSupplementaryChecked().isEmpty() && teseTo.getSupplementaryChecked().equals("on")){
				bo.setTheoryIsSupplementary(true);
			}else{
				bo.setTheoryIsSupplementary(false);
			}
			if(teseTo.getSupplementaryMinMarks()!=null && !teseTo.getSupplementaryMinMarks().isEmpty()){
				bo.setTheorySupplementaryMinMarksTotal(BigDecimal.valueOf(Double.valueOf(teseTo.getSupplementaryMinMarks())));
			}
			if(teseTo.getSupplementaryMaxMarks()!=null && !teseTo.getSupplementaryMaxMarks().isEmpty()){
				bo.setTheorySupplementaryMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(teseTo.getSupplementaryMaxMarks())));
			}
		}
		
		
		ExamSubjectRuleSettingsPracticalInternalTO pesto=subjectRuleSettingsForm.getPesto();
		if(pesto!=null){
			if(pesto.getSelectTheBest()!=null && !pesto.getSelectTheBest().isEmpty()){
				bo.setSelectBestOfPracticalInternal(Integer.parseInt(pesto.getSelectTheBest()));
			}
			if(pesto.getFinalInternalMinimumMarks()!=null && !pesto.getFinalInternalMinimumMarks().isEmpty()){
				bo.setFinalPracticalInternalMinimumMark(BigDecimal.valueOf(Double.valueOf(pesto.getFinalInternalMinimumMarks())));
			}
			if(pesto.getFinalInternalMaximumMarks()!=null && !pesto.getFinalInternalMaximumMarks().isEmpty() ){
				bo.setFinalPracticalInternalMaximumMark(BigDecimal.valueOf(Double.valueOf(pesto.getFinalInternalMaximumMarks())));
			}
			if(pesto.getFinalEntryMaximumMarks()!=null && !pesto.getFinalEntryMaximumMarks().isEmpty()){
				bo.setFinalPracticalInternalEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(pesto.getFinalEntryMaximumMarks())));
			}
			if(pesto.getAttendanceChecked()!=null && pesto.getAttendanceChecked().equalsIgnoreCase("on")){
				bo.setFinalPracticalInternalIsAttendance(true);
			}else{
				bo.setFinalPracticalInternalIsAttendance(false);
			}
			if(pesto.getSubInternalChecked()!=null && pesto.getSubInternalChecked().equalsIgnoreCase("on")){
				bo.setFinalPracticalInternalIsSubInternal(true);
			}else{
				bo.setFinalPracticalInternalIsSubInternal(false);
			}
			if(pesto.getAssignmentChecked()!=null && pesto.getAssignmentChecked().equalsIgnoreCase("on")){
				bo.setFinalPracticalInternalIsAssignment(true);
			}else{
				bo.setFinalPracticalInternalIsAssignment(false);
			}
			
			if(pesto.getTotalentryMaximumMarks()!=null && !pesto.getTotalentryMaximumMarks().isEmpty()){
				bo.setPracticalIntEntryMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(pesto.getTotalentryMaximumMarks())));
			}
			if(pesto.getTotalMaximumMarks()!=null && !pesto.getTotalMaximumMarks().isEmpty()){
				bo.setPracticalIntMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(pesto.getTotalMaximumMarks())));
			}
			if(pesto.getTotalMinimummumMarks()!=null && !pesto.getTotalMinimummumMarks().isEmpty()){
				bo.setPracticalIntMinMarksTotal(BigDecimal.valueOf(Double.valueOf(pesto.getTotalMinimummumMarks())));
			}
		}
		
		
		ExamSubjectRuleSettingsPracticalESETO peseTo=subjectRuleSettingsForm.getPeseTo();
		if(peseTo!=null){
			if(peseTo.getRegularPracticalESEChecked()!=null && peseTo.getRegularPracticalESEChecked().equalsIgnoreCase("on")){
				bo.setPracticalEseIsRegular(true);
			}else{
				bo.setPracticalEseIsRegular(false);
			}
			if(peseTo.getMultipleAnswerScriptsChecked()!=null && peseTo.getMultipleAnswerScriptsChecked().equalsIgnoreCase("on")){
				bo.setPracticalEseIsMultipleAnswerScript(true);
			}else{
				bo.setPracticalEseIsMultipleAnswerScript(false);
			}
			if(peseTo.getMultipleEvaluatorChecked()!=null && peseTo.getMultipleEvaluatorChecked().equalsIgnoreCase("on")){
				bo.setPracticalEseIsMultipleEvaluator(true);
			}else{
				bo.setPracticalEseIsMultipleEvaluator(false);
			}
			if(peseTo.getMaximumEntryMarksPracticalESE()!=null && !peseTo.getMaximumEntryMarksPracticalESE().isEmpty()){
				bo.setPracticalEseEnteredMaxMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMaximumEntryMarksPracticalESE())));
			}
			if(peseTo.getMinimumMarksPracticalESE()!=null && !peseTo.getMinimumMarksPracticalESE().isEmpty()){
				bo.setPracticalEseMinimumMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMinimumMarksPracticalESE())));
			}
			if(peseTo.getMaximumMarksPracticalESE()!=null && !peseTo.getMaximumMarksPracticalESE().isEmpty()){
				bo.setPracticalEseMaximumMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMaximumMarksPracticalESE())));
			}
			if(peseTo.getMaximumTheoryFinalMarksPracticalESE()!=null && !peseTo.getMaximumTheoryFinalMarksPracticalESE().isEmpty()){
				bo.setPracticalEseTheoryFinalMaximumMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMaximumTheoryFinalMarksPracticalESE())));
			}
			if(peseTo.getMinimumTheoryFinalMarksPracticalESE()!=null && !peseTo.getMinimumTheoryFinalMarksPracticalESE().isEmpty()){
				bo.setPracticalEseTheoryFinalMinimumMark(BigDecimal.valueOf(Double.valueOf(peseTo.getMinimumTheoryFinalMarksPracticalESE())));
			}
			if(peseTo.getSupplementaryChecked()!=null && !peseTo.getSupplementaryChecked().isEmpty() && peseTo.getSupplementaryChecked().equals("on")){
				bo.setPracticalIsSupplementary(true);
			}else{
				bo.setPracticalIsSupplementary(false);
			}
			if(peseTo.getSupplementaryMinMarks()!=null && !peseTo.getSupplementaryMinMarks().isEmpty()){
				bo.setPracticalSupplementaryMinMarksTotal(BigDecimal.valueOf(Double.valueOf(teseTo.getSupplementaryMinMarks())));
			}
			if(peseTo.getSupplementaryMaxMarks()!=null && !peseTo.getSupplementaryMaxMarks().isEmpty()){
				bo.setPracticalSupplementaryMaxMarksTotal(BigDecimal.valueOf(Double.valueOf(teseTo.getSupplementaryMaxMarks())));
			}
		}
		
		if(subjectRuleSettingsForm.getTheoryExam()!=null && subjectRuleSettingsForm.getTheoryExam().equalsIgnoreCase("on")){
			bo.setSubjectFinalIsTheoryExam(true);
		}else{
			bo.setSubjectFinalIsTheoryExam(false);
		}
		if(subjectRuleSettingsForm.getPracticalExam()!=null && subjectRuleSettingsForm.getPracticalExam().equalsIgnoreCase("on")){
			bo.setSubjectFinalIsPracticalExam(true);
		}else{
			bo.setSubjectFinalIsPracticalExam(false);
		}
		if(subjectRuleSettingsForm.getAttendance()!=null && subjectRuleSettingsForm.getAttendance().equalsIgnoreCase("on")){
			bo.setSubjectFinalIsAttendance(true);
		}else{
			bo.setSubjectFinalIsAttendance(false);
		}
		if(subjectRuleSettingsForm.getInternalExam()!=null && subjectRuleSettingsForm.getInternalExam().equalsIgnoreCase("on")){
			bo.setSubjectFinalIsInternalExam(true);
		}else{
			bo.setSubjectFinalIsInternalExam(false);
		}
		
		if(subjectRuleSettingsForm.getMinimum()!=null && !subjectRuleSettingsForm.getMinimum().isEmpty()){
			bo.setSubjectFinalMinimum(BigDecimal.valueOf(Double.valueOf(subjectRuleSettingsForm.getMinimum())));
		}
		if(subjectRuleSettingsForm.getMaximum()!=null && !subjectRuleSettingsForm.getMaximum().isEmpty()){
			bo.setSubjectFinalMaximum(BigDecimal.valueOf(Double.valueOf(subjectRuleSettingsForm.getMaximum())));
		}
		if(subjectRuleSettingsForm.getValuated()!=null && !subjectRuleSettingsForm.getValuated().isEmpty()){
			bo.setSubjectFinalValuated(BigDecimal.valueOf(Double.valueOf(subjectRuleSettingsForm.getValuated())));
		}
		bos.add(bo);
	
		return bos;
	}
	
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public String getCheckExistedForCopyQuery(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
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
		String query="select e.courseId from ExamSubjectRuleSettingsBO e" +
				" where e.academicYear= " +subjectRuleSettingsForm.getToYear()+
				" and e.courseId in ("+intType+") and e.schemeNo in ("+schemeNo+") group by e.courseId";
		return query;
	}
}
