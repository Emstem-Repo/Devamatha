package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.bo.exam.SubjectRuleSettingsAssignment;
import com.kp.cms.bo.exam.SubjectRuleSettingsAttendance;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulAnsScript;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulEvaluator;
import com.kp.cms.bo.exam.SubjectRuleSettingsSubInternal;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.to.exam.SubjectRuleSettingCertificateCourseTo;
import com.kp.cms.transactions.exam.ISubjectRuleSettingCertificateCourseTransaction;
import com.kp.cms.transactionsimpl.exam.SubjectRuleSettingCertificateCourseTransactionImpl;


public class SubjectRuleSettingCertificateCourseHelpers {
	private static volatile SubjectRuleSettingCertificateCourseHelpers instance=null;
	/**
	 * 
	 */
	private SubjectRuleSettingCertificateCourseHelpers(){
	}
	/**
	 * @return
	 */
	public static SubjectRuleSettingCertificateCourseHelpers getInstance(){
		if(instance==null){
			instance=new SubjectRuleSettingCertificateCourseHelpers();
		}
		return instance;
	}
	/**
	 * @param phdBoList
	 * @return
	 * @throws Exception
	 */
	public List<SubjectRuleSettingCertificateCourseTo> convertBosToTOs(List<CertificateCourse> cerBoList) throws Exception{
		ArrayList<SubjectRuleSettingCertificateCourseTo> cerTolist=new ArrayList<SubjectRuleSettingCertificateCourseTo>();
		Iterator<CertificateCourse> itr=cerBoList.iterator();
		while (itr.hasNext()) {
			CertificateCourse cerBo = (CertificateCourse) itr.next();
			SubjectRuleSettingCertificateCourseTo cerTo=new SubjectRuleSettingCertificateCourseTo();
			
			if(cerBo.getId()>0){
				cerTo.setId(cerBo.getId());				
			}
			if(cerBo.getId()>0){
				cerTo.setCerCourseId(Integer.toString(cerBo.getId()));				
			}
			if(cerBo.getCertificateCourseName()!=null && !cerBo.getCertificateCourseName().isEmpty()){
				cerTo.setCerCourseName(cerBo.getCertificateCourseName());				
			}
			if(cerBo.getSubject()!=null){
				cerTo.setSubjectId(Integer.toString(cerBo.getSubject().getId()));				
			}
			if(cerBo.getSubject()!=null){
				cerTo.setSubjectName(cerBo.getSubject().getCode()+"-"+cerBo.getSubject().getName());				
			}
			cerTolist.add(cerTo);
			
		}
		return cerTolist;
	}
	/**
	 * @param objform
	 * @param phdBO
	 * @throws Exception 
	 * @throws Exception
	 */
	public boolean getBoObjectForUpdate(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		
		boolean flag=false;
		ISubjectRuleSettingCertificateCourseTransaction curTransaction=SubjectRuleSettingCertificateCourseTransactionImpl.getInstance();
		String schemeNo="";
		String courseId ="";
		String subjectId ="";
		//String courseName ="";
		Iterator<Object[]> irr=subjectRuleSettingsForm.getAllDatas().iterator();
		while (irr.hasNext()) {
			Object[] values =(Object[])irr.next();
			courseId=values[0].toString();
			schemeNo=values[1].toString();
			subjectId=values[2].toString();
			//courseName=values[3].toString();
		
		List<ExamSubjectRuleSettingsBO> allIds=curTransaction.getSubjectrulesIs(subjectRuleSettingsForm.getAcademicYear(),courseId,schemeNo,subjectId);
		if(allIds!=null && !allIds.isEmpty()){
		Iterator<ExamSubjectRuleSettingsBO> itr=allIds.iterator();
		while (itr.hasNext()) {
			ExamSubjectRuleSettingsBO oneId = (ExamSubjectRuleSettingsBO) itr.next();
		
		List<SubjectRuleSettings> bos=new ArrayList<SubjectRuleSettings>();
		
		SubjectRuleSettings bo=new SubjectRuleSettings();

		bo.setId(oneId.getId());
		
		bo.setAcademicYear(Integer.parseInt(subjectRuleSettingsForm.getAcademicYear()));
		Course course=new Course();
		course.setId(oneId.getCourseId());
		bo.setCourse(course);
		Subject subject=new Subject();
		subject.setId(oneId.getSubjectId());
		bo.setSubject(subject);
		bo.setIsActive(true);
		bo.setModifiedBy(subjectRuleSettingsForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setSchemeNo(oneId.getSchemeNo());
		
		
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
		curTransaction.addAll(bos);
		flag=true;
		}
	}else{
		List<SubjectRuleSettings> bos=new ArrayList<SubjectRuleSettings>();
		List<Subject> list=curTransaction.getSubjectsByCourseYearSemester(subjectRuleSettingsForm.getAcademicYear(),courseId,schemeNo,subjectId);
				Iterator<Subject> itr=list.iterator();
				while (itr.hasNext()) {
					Subject subject = (Subject) itr.next();
					SubjectRuleSettings bo=new SubjectRuleSettings();
					bo.setAcademicYear(Integer.parseInt(subjectRuleSettingsForm.getAcademicYear()));
					Course course =new Course();
					course.setId(Integer.parseInt(courseId));
					bo.setCourse(course);
					bo.setSubject(subject);
//					bo.setCourseId(Integer.parseInt());
//					bo.setSubjectId(subject.getId());
					
					bo.setIsActive(true);
					bo.setCreatedBy(subjectRuleSettingsForm.getUserId());
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(subjectRuleSettingsForm.getUserId());
					bo.setLastModifiedDate(new Date());
					bo.setSchemeNo(Integer.parseInt(schemeNo));
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
					flag=true;
				}
	     	curTransaction.addAll(bos);
	     }
	}
		return flag;
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
	private Set<SubjectRuleSettingsMulEvaluator> setMultipleEvaluatorTheoryToBo(SubjectRuleSettings bo,
			ExamSubjectRuleSettingsTheoryESETO teseTo) {
		Set<SubjectRuleSettingsMulEvaluator> evIdSet=new HashSet<SubjectRuleSettingsMulEvaluator>();
		if(teseTo.getMultipleEvaluatorChecked()!=null && teseTo.getMultipleEvaluatorChecked().equals("on")){
			if(teseTo.getEvalId1()!=null && !teseTo.getEvalId1().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(teseTo.getEvalId1()));
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
	private Set<SubjectRuleSettingsMulEvaluator> setMultipleEvaluatorPracticalToBo(
			SubjectRuleSettings bo,
			ExamSubjectRuleSettingsPracticalESETO peseTo) {
		Set<SubjectRuleSettingsMulEvaluator> evIdSet=new HashSet<SubjectRuleSettingsMulEvaluator>();
		if(peseTo.getMultipleEvaluatorChecked()!=null && peseTo.getMultipleEvaluatorChecked().equals("on")){
			if(peseTo.getEvalId1()!=null && !peseTo.getEvalId1().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(peseTo.getEvalId1()));
				evId1.setIsTheoryPractical('p');
				evId1.setNoOfEvaluations(Integer.parseInt(peseTo.getNoOfEvaluations()));
				evId1.setTypeOfEvaluation(peseTo.getSelectTypeOfEvaluation());
				evId1.setIsActive(true);
				if(peseTo.getId1()!=null && !peseTo.getId1().isEmpty()){
					evId1.setId(Integer.parseInt(peseTo.getId1()));
				}
				evIdSet.add(evId1);
			}
			if(peseTo.getEvalId2()!=null && !peseTo.getEvalId2().isEmpty()){
				SubjectRuleSettingsMulEvaluator evId1=new SubjectRuleSettingsMulEvaluator();
				evId1.setEvaluatorId(Integer.parseInt(peseTo.getEvalId2()));
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
}
