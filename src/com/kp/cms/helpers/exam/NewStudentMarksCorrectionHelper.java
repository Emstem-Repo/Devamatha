package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.forms.exam.NewStudentMarksCorrectionForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.exam.NewStudentMarkCorrectionTo;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.to.exam.SubjectMarksTO;

public class NewStudentMarksCorrectionHelper {
	/**
	 * Singleton object of NewStudentMarksCorrectionHelper
	 */
	private static volatile NewStudentMarksCorrectionHelper newStudentMarksCorrectionHelper = null;
	private static final Log log = LogFactory.getLog(NewStudentMarksCorrectionHelper.class);
	private NewStudentMarksCorrectionHelper() {
		
	}
	/**
	 * return singleton object of NewStudentMarksCorrectionHelper.
	 * @return
	 */
	public static NewStudentMarksCorrectionHelper getInstance() {
		if (newStudentMarksCorrectionHelper == null) {
			newStudentMarksCorrectionHelper = new NewStudentMarksCorrectionHelper();
		}
		return newStudentMarksCorrectionHelper;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public String getMarksEnteredForExamQuery(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception{
		String query="";
		if(newStudentMarksCorrectionForm.getMarkType()==null || newStudentMarksCorrectionForm.getMarkType().isEmpty()){
			query="from MarksEntryDetails md" +
				  " where md.marksEntry.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
				  " and md.marksEntry.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo();
			if(newStudentMarksCorrectionForm.getEvaluatorType()!=null && !newStudentMarksCorrectionForm.getEvaluatorType().isEmpty()){
				query=query+" and md.marksEntry.evaluatorType="+newStudentMarksCorrectionForm.getEvaluatorType();
			}else{
				query=query+" and md.marksEntry.evaluatorType is null";
			}
			if(newStudentMarksCorrectionForm.getAnswerScript()!=null && !newStudentMarksCorrectionForm.getAnswerScript().isEmpty()){
				query=query+" and md.marksEntry.answerScript="+newStudentMarksCorrectionForm.getAnswerScript();
			}else{
				query=query+" and md.marksEntry.answerScript is null";
			}
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Internal overAll")){
			query="from StudentOverallInternalMarkDetails s" +
					" where s.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
					" and s.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo();
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Regular overAll")){
			query="from StudentFinalMarkDetails s" +
					" where s.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
					" and s.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo();
		}
		return query;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public String checkStudentIsValidQuery(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception {
		String query="";
		if(newStudentMarksCorrectionForm.getMarkType()==null || newStudentMarksCorrectionForm.getMarkType().isEmpty()){
			query="from MarksEntryDetails md" +
				  " where md.marksEntry.exam.id=" +newStudentMarksCorrectionForm.getExamId()+" and md.marksEntry.student.id="+newStudentMarksCorrectionForm.getStudentId()+
				  " and md.marksEntry.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo();
//			if(newStudentMarksCorrectionForm.getRegNo()!=null && !newStudentMarksCorrectionForm.getRegNo().isEmpty())
//				query=query+" and md.marksEntry.student.registerNo='"+newStudentMarksCorrectionForm.getRegNo()+"'";
//			if(newStudentMarksCorrectionForm.getRollNo()!=null && !newStudentMarksCorrectionForm.getRollNo().isEmpty())
//				query=query+" and md.marksEntry.student.rollNo='"+newStudentMarksCorrectionForm.getRollNo()+"'";
			if(newStudentMarksCorrectionForm.getEvaluatorType()!=null && !newStudentMarksCorrectionForm.getEvaluatorType().isEmpty()){
				query=query+" and md.marksEntry.evaluatorType="+newStudentMarksCorrectionForm.getEvaluatorType();
			}else{
				query=query+" and md.marksEntry.evaluatorType is null";
			}
			if(newStudentMarksCorrectionForm.getAnswerScript()!=null && !newStudentMarksCorrectionForm.getAnswerScript().isEmpty()){
				query=query+" and md.marksEntry.answerScript="+newStudentMarksCorrectionForm.getAnswerScript();
			}else{
				query=query+" and md.marksEntry.answerScript is null";
			}
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Internal overAll")){
			query="from StudentOverallInternalMarkDetails s" +
					" where s.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
					" and s.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo()+" and md.marksEntry.student.id="+newStudentMarksCorrectionForm.getStudentId();;
//			if(newStudentMarksCorrectionForm.getRegNo()!=null && !newStudentMarksCorrectionForm.getRegNo().isEmpty())
//				query=query+" and s.student.registerNo='"+newStudentMarksCorrectionForm.getRegNo()+"'";
//			if(newStudentMarksCorrectionForm.getRollNo()!=null && !newStudentMarksCorrectionForm.getRollNo().isEmpty())
//				query=query+" and s.student.rollNo='"+newStudentMarksCorrectionForm.getRollNo()+"'";
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Regular overAll")){
			query="from StudentFinalMarkDetails s" +
					" where s.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
					" and s.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo()+" and md.marksEntry.student.id="+newStudentMarksCorrectionForm.getStudentId();;
//			if(newStudentMarksCorrectionForm.getRegNo()!=null && !newStudentMarksCorrectionForm.getRegNo().isEmpty())
//				query=query+" and s.student.registerNo='"+newStudentMarksCorrectionForm.getRegNo()+"'";
//			if(newStudentMarksCorrectionForm.getRollNo()!=null && !newStudentMarksCorrectionForm.getRollNo().isEmpty())
//				query=query+" and s.student.rollNo='"+newStudentMarksCorrectionForm.getRollNo()+"'";
		}
		return query;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 */
	public String getOldMarksQuery(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) {
		String query="";
		if(newStudentMarksCorrectionForm.getMarkType()==null || newStudentMarksCorrectionForm.getMarkType().isEmpty()){
			query="select cd.subject.id" +
					" from MarksEntryCorrectionDetails cd" +
					" where cd.marksEntry.id in (";
			query=query+" select md.marksEntry.id from MarksEntryDetails md" +
				  " where md.marksEntry.exam.id=" +newStudentMarksCorrectionForm.getExamId()+" and md.marksEntry.student.id="+newStudentMarksCorrectionForm.getStudentId()+
				  " and md.marksEntry.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo();
//			if(newStudentMarksCorrectionForm.getRegNo()!=null && !newStudentMarksCorrectionForm.getRegNo().isEmpty())
//				query=query+" and md.marksEntry.student.registerNo='"+newStudentMarksCorrectionForm.getRegNo()+"'";
//			if(newStudentMarksCorrectionForm.getRollNo()!=null && !newStudentMarksCorrectionForm.getRollNo().isEmpty())
//				query=query+" and md.marksEntry.student.rollNo='"+newStudentMarksCorrectionForm.getRollNo()+"'";
			if(newStudentMarksCorrectionForm.getEvaluatorType()!=null && !newStudentMarksCorrectionForm.getEvaluatorType().isEmpty()){
				query=query+" and md.marksEntry.evaluatorType="+newStudentMarksCorrectionForm.getEvaluatorType();
			}else{
				query=query+" and md.marksEntry.evaluatorType is null";
			}
			if(newStudentMarksCorrectionForm.getAnswerScript()!=null && !newStudentMarksCorrectionForm.getAnswerScript().isEmpty()){
				query=query+" and md.marksEntry.answerScript="+newStudentMarksCorrectionForm.getAnswerScript();
			}else{
				query=query+" and md.marksEntry.answerScript is null";
			}
		
			query=query+") group by cd.subject.id";
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Internal overAll")){
			query="select cd.subject.id" +
					" from MarksEntryCorrectionDetails cd" +
					" where cd.studentOverAllMark.id in (";
			query=query+" select s.id from StudentOverallInternalMarkDetails s" +
					" where s.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
					" and s.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo()+" and s.student.id="+newStudentMarksCorrectionForm.getStudentId();
//			if(newStudentMarksCorrectionForm.getRegNo()!=null && !newStudentMarksCorrectionForm.getRegNo().isEmpty())
//				query=query+" and s.student.registerNo='"+newStudentMarksCorrectionForm.getRegNo()+"'";
//			if(newStudentMarksCorrectionForm.getRollNo()!=null && !newStudentMarksCorrectionForm.getRollNo().isEmpty())
//				query=query+" and s.student.rollNo='"+newStudentMarksCorrectionForm.getRollNo()+"'";
		
			query=query+") group by cd.subject.id";
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Regular overAll")){
			query="select cd.subject.id" +
					" from MarksEntryCorrectionDetails cd" +
					" where cd.studentFinalMark.id in (";
			query=query+"select s.id from StudentFinalMarkDetails s" +
					" where s.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
					" and s.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo()+" and s.student.id="+newStudentMarksCorrectionForm.getStudentId();
//			if(newStudentMarksCorrectionForm.getRegNo()!=null && !newStudentMarksCorrectionForm.getRegNo().isEmpty())
//				query=query+" and s.student.registerNo='"+newStudentMarksCorrectionForm.getRegNo()+"'";
//			if(newStudentMarksCorrectionForm.getRollNo()!=null && !newStudentMarksCorrectionForm.getRollNo().isEmpty())
//				query=query+" and s.student.rollNo='"+newStudentMarksCorrectionForm.getRollNo()+"'";
			query=query+") group by cd.subject.id";
		}
		return query;
	}
	/**
	 * @param marksList
	 * @param oldMarksList
	 * @return
	 * @throws Exception
	 */
	public List<NewStudentMarkCorrectionTo> convertMarksEntryBotoTO(List marksList,List<Integer> oldMarksList,NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception{
		List<NewStudentMarkCorrectionTo> list=new ArrayList<NewStudentMarkCorrectionTo>();
		Iterator<MarksEntryDetails> itr=marksList.iterator();
		int count=0;
		while (itr.hasNext()) {
			MarksEntryDetails bo = (MarksEntryDetails) itr.next();
			NewStudentMarkCorrectionTo to=new NewStudentMarkCorrectionTo();
			if(oldMarksList!=null && oldMarksList.contains(bo.getSubject().getId())){
				to.setIsOldMarks(true);
			}else{
				to.setIsOldMarks(false);
			}
			to.setStudentId(bo.getMarksEntry().getStudent().getId());
			if(count==0){
				newStudentMarksCorrectionForm.setStudentId(bo.getMarksEntry().getStudent().getId());
				newStudentMarksCorrectionForm.setStudentName(bo.getMarksEntry().getStudent().getAdmAppln().getPersonalData().getFirstName());
				newStudentMarksCorrectionForm.setMarksCardNo(bo.getMarksEntry().getMarksCardNo());
				if(bo.getMarksEntry().getClasses()!=null)
					newStudentMarksCorrectionForm.setCourseId(String.valueOf(bo.getMarksEntry().getClasses().getCourse().getId()));
				count+=1;
			}
			to.setMarksEntryId(bo.getMarksEntry().getId());
			to.setMarksEntryDetailsId(bo.getId());
			to.setSubjectId(bo.getSubject().getId());
			to.setSubjectName(bo.getSubject().getName());
			to.setSubjectCode(bo.getSubject().getCode());
			to.setTheoryMarks(bo.getTheoryMarks());
			to.setOldTheoryMarks(bo.getTheoryMarks());
			to.setPracticalMarks(bo.getPracticalMarks());
			to.setOldPracticalMarks(bo.getPracticalMarks());
			if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("t")){
				to.setIsTheory(true);
				to.setIsPractical(false);
			}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("p")){
				to.setIsTheory(false);
				to.setIsPractical(true);
			}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
				to.setIsTheory(true);
				to.setIsPractical(true);	
			}
			if(bo.getMarksEntry().getClasses()!=null){
				Iterator<ClassSchemewise> classSet=bo.getMarksEntry().getClasses().getClassSchemewises().iterator();
				if (classSet.hasNext()) {
					ClassSchemewise classSchemewise = (ClassSchemewise) classSet .next();
					newStudentMarksCorrectionForm.setYear(classSchemewise.getCurriculumSchemeDuration().getAcademicYear().toString());
				}
			}
			list.add(to);
		}
		return list;
	}
	/**
	 * @param marksList
	 * @param oldMarksList
	 * @return
	 * @throws Exception
	 */
	public List<NewStudentMarkCorrectionTo> convertInternalOverAllBotoTO(List marksList,List<Integer> oldMarksList,NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception{
		List<NewStudentMarkCorrectionTo> list=new ArrayList<NewStudentMarkCorrectionTo>();
		Iterator<StudentOverallInternalMarkDetails> itr=marksList.iterator();
		int count=0;
		while (itr.hasNext()) {
			StudentOverallInternalMarkDetails bo = (StudentOverallInternalMarkDetails) itr.next();
			NewStudentMarkCorrectionTo to=new NewStudentMarkCorrectionTo();
			if(oldMarksList!=null && oldMarksList.contains(bo.getSubject().getId())){
				to.setIsOldMarks(true);
			}else{
				to.setIsOldMarks(false);
			}
			to.setStudentId(bo.getStudent().getId());
			if(count==0){
				newStudentMarksCorrectionForm.setStudentId(bo.getStudent().getId());
				newStudentMarksCorrectionForm.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
				newStudentMarksCorrectionForm.setMarksCardNo("");
				newStudentMarksCorrectionForm.setCourseId(String.valueOf(bo.getClasses().getCourse().getId()));
				count+=1;
			}
			to.setInternalOverAllId(bo.getId());
			to.setSubjectId(bo.getSubject().getId());
			to.setSubjectName(bo.getSubject().getName());
			to.setSubjectCode(bo.getSubject().getCode());
			to.setTheoryMarks(bo.getTheoryTotalSubInternalMarks());
			to.setOldTheoryMarks(bo.getTheoryTotalSubInternalMarks());
			to.setPracticalMarks(bo.getPracticalTotalSubInternalMarks());
			to.setOldPracticalMarks(bo.getPracticalTotalSubInternalMarks());
			if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("t")){
				to.setIsTheory(true);
				to.setIsPractical(false);
			}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("p")){
				to.setIsTheory(false);
				to.setIsPractical(true);
			}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
				to.setIsTheory(true);
				to.setIsPractical(true);	
			}
			if(bo.getClasses()!=null){
				Iterator<ClassSchemewise> classSet=bo.getClasses().getClassSchemewises().iterator();
				if (classSet.hasNext()) {
					ClassSchemewise classSchemewise = (ClassSchemewise) classSet .next();
					newStudentMarksCorrectionForm.setYear(classSchemewise.getCurriculumSchemeDuration().getAcademicYear().toString());
				}
			}
			list.add(to);
		}
		return list;
	}
	/**
	 * @param marksList
	 * @param oldMarksList
	 * @return
	 * @throws Exception
	 */
	public List<NewStudentMarkCorrectionTo> convertRegularOverAllBotoTO(List marksList,List<Integer> oldMarksList,NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception{
		List<NewStudentMarkCorrectionTo> list=new ArrayList<NewStudentMarkCorrectionTo>();
		Iterator<StudentFinalMarkDetails> itr=marksList.iterator();
		int count=0;
		while (itr.hasNext()) {
			StudentFinalMarkDetails bo = (StudentFinalMarkDetails) itr.next();
			NewStudentMarkCorrectionTo to=new NewStudentMarkCorrectionTo();
			if(oldMarksList!=null && oldMarksList.contains(bo.getSubject().getId())){
				to.setIsOldMarks(true);
			}else{
				to.setIsOldMarks(false);
			}
			to.setStudentId(bo.getStudent().getId());
			if(count==0){
				newStudentMarksCorrectionForm.setStudentId(bo.getStudent().getId());
				newStudentMarksCorrectionForm.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
				newStudentMarksCorrectionForm.setMarksCardNo("");
				newStudentMarksCorrectionForm.setCourseId(String.valueOf(bo.getClasses().getCourse().getId()));
				count+=1;
			}
			to.setRegularOverAllId(bo.getId());
			to.setSubjectId(bo.getSubject().getId());
			to.setSubjectName(bo.getSubject().getName());
			to.setSubjectCode(bo.getSubject().getCode());
			to.setTheoryMarks(bo.getStudentTheoryMarks());
			to.setOldTheoryMarks(bo.getStudentTheoryMarks());
			to.setPracticalMarks(bo.getStudentPracticalMarks());
			to.setOldPracticalMarks(bo.getStudentPracticalMarks());
			if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("t")){
				to.setIsTheory(true);
				to.setIsPractical(false);
			}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("p")){
				to.setIsTheory(false);
				to.setIsPractical(true);
			}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
				to.setIsTheory(true);
				to.setIsPractical(true);	
			}
			if(bo.getClasses()!=null){
				Iterator<ClassSchemewise> classSet=bo.getClasses().getClassSchemewises().iterator();
				if (classSet.hasNext()) {
					ClassSchemewise classSchemewise = (ClassSchemewise) classSet .next();
					newStudentMarksCorrectionForm.setYear(classSchemewise.getCurriculumSchemeDuration().getAcademicYear().toString());
				}
			}
			list.add(to);
		}
		return list;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public List<MarksEntryCorrectionDetails> convertTotoBoList(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception {
		List<NewStudentMarkCorrectionTo> toList=newStudentMarksCorrectionForm.getMarksList();
		List<MarksEntryCorrectionDetails> boList=new ArrayList<MarksEntryCorrectionDetails>();
		Iterator<NewStudentMarkCorrectionTo> itr=toList.iterator();
		while (itr.hasNext()) {
			NewStudentMarkCorrectionTo to = (NewStudentMarkCorrectionTo) itr.next();
			boolean isTheoryChanged=false;
			boolean isPracticalChanged=false;
			if(to.getIsTheory()){
				if(to.getOldTheoryMarks()!=null && !to.getTheoryMarks().equals(to.getOldTheoryMarks())){
					isTheoryChanged=true;
				}
			}
			if(to.getIsPractical()){
				if(to.getOldPracticalMarks()!=null && !to.getPracticalMarks().equals(to.getOldPracticalMarks())){
					isPracticalChanged=true;
				}
			}
			if(isPracticalChanged || isTheoryChanged){
				MarksEntryCorrectionDetails bo=new MarksEntryCorrectionDetails();
				if(to.getMarksEntryId()>0){
					MarksEntry marksEntry=new MarksEntry();
					marksEntry.setId(to.getMarksEntryId());
					bo.setMarksEntry(marksEntry);
				}
				if(to.getInternalOverAllId()>0){
					StudentOverallInternalMarkDetails internalMarkDetails=new StudentOverallInternalMarkDetails();
					internalMarkDetails.setId(to.getInternalOverAllId());
					bo.setStudentOverAllMark(internalMarkDetails);
				}
				if(to.getRegularOverAllId()>0){
					StudentFinalMarkDetails details=new StudentFinalMarkDetails();
					details.setId(to.getRegularOverAllId());
					bo.setStudentFinalMark(details);
				}
				
				Subject subject=new Subject();
				subject.setId(to.getSubjectId());
				bo.setSubject(subject);
				
				if(isPracticalChanged){
					bo.setPracticalMarks(to.getOldPracticalMarks());
					bo.setPreviousEvaluatorPracticalMarks(to.getOldPracticalMarks());
				}
				if(isTheoryChanged){
					bo.setTheoryMarks(to.getOldTheoryMarks());
					bo.setPreviousEvaluatorTheoryMarks(to.getOldTheoryMarks());
				}
				bo.setComments(to.getComments());
				if(to.getMistake()!=null && to.getMistake().equalsIgnoreCase("on"))
					bo.setIsMistake(true);
				else
					bo.setIsMistake(false);
				if(to.getRetest()!=null && to.getRetest().equalsIgnoreCase("on"))
					bo.setIsRetest(true);
				else
					bo.setIsRetest(false);
				bo.setCreatedBy(newStudentMarksCorrectionForm.getUserId());
				bo.setCreatedDate(new Date());
				bo.setModifiedBy(newStudentMarksCorrectionForm.getUserId());
				bo.setLastModifiedDate(new Date());
				
				boList.add(bo);
			}
		}
		return boList;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentOldMarksBySubjectId(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception{

		String query="";
		if(newStudentMarksCorrectionForm.getMarkType()==null || newStudentMarksCorrectionForm.getMarkType().isEmpty()){
			query=" from MarksEntryCorrectionDetails cd" +
					" where cd.marksEntry.id in (";
			query=query+" select md.marksEntry.id from MarksEntryDetails md" +
				  " where md.marksEntry.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
				  " and md.marksEntry.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo()+
				  " and md.marksEntry.student.id="+newStudentMarksCorrectionForm.getStudentId();
			if(newStudentMarksCorrectionForm.getEvaluatorType()!=null && !newStudentMarksCorrectionForm.getEvaluatorType().isEmpty()){
				query=query+" and md.marksEntry.evaluatorType="+newStudentMarksCorrectionForm.getEvaluatorType();
			}else{
				query=query+" and md.marksEntry.evaluatorType is null";
			}
			if(newStudentMarksCorrectionForm.getAnswerScript()!=null && !newStudentMarksCorrectionForm.getAnswerScript().isEmpty()){
				query=query+" and md.marksEntry.answerScript="+newStudentMarksCorrectionForm.getAnswerScript();
			}else{
				query=query+" and md.marksEntry.answerScript is null";
			}
			if(newStudentMarksCorrectionForm.getSubjectId()!=null && !newStudentMarksCorrectionForm.getSubjectId().isEmpty()){
				query=query+" and md.subject.id="+newStudentMarksCorrectionForm.getSubjectId();
			}
			query=query+")";
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Internal overAll")){
			query=" from MarksEntryCorrectionDetails cd" +
					" where cd.studentOverAllMark.id in (select s.id from StudentOverallInternalMarkDetails s" +
					" where s.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
					" and s.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo();
			if(newStudentMarksCorrectionForm.getSubjectId()!=null && !newStudentMarksCorrectionForm.getSubjectId().isEmpty()){
				query=query+" and s.subject.id="+newStudentMarksCorrectionForm.getSubjectId();
			}
			query=query+" and s.student.id="+newStudentMarksCorrectionForm.getStudentId()+")";
		}else if(newStudentMarksCorrectionForm.getMarkType()!=null && newStudentMarksCorrectionForm.getMarkType().equalsIgnoreCase("Regular overAll")){
			query=" from MarksEntryCorrectionDetails cd" +
					" where cd.studentFinalMark.id in (select s.id from StudentFinalMarkDetails s" +
					" where s.exam.id=" +newStudentMarksCorrectionForm.getExamId()+
					" and s.classes.termNumber="+newStudentMarksCorrectionForm.getSchemeNo();
			if(newStudentMarksCorrectionForm.getSubjectId()!=null && !newStudentMarksCorrectionForm.getSubjectId().isEmpty()){
				query=query+" and s.subject.id="+newStudentMarksCorrectionForm.getSubjectId();
			}
			query=query+" and s.student.id="+newStudentMarksCorrectionForm.getStudentId()+")";
		}
		return query;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<StudentMarkDetailsTO> convertBOtoTo(List list) throws Exception {
		List<StudentMarkDetailsTO> toList=new ArrayList<StudentMarkDetailsTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<MarksEntryCorrectionDetails> itr=list.iterator();
			while (itr.hasNext()) {
				MarksEntryCorrectionDetails bo = (MarksEntryCorrectionDetails) itr.next();
				StudentMarkDetailsTO to=new StudentMarkDetailsTO();
				to.setSubjectId(bo.getSubject().getId());
				to.setSubjectName(bo.getSubject().getName());
				to.setComment(bo.getComments());
				to.setStuTheoryRegMark(bo.getTheoryMarks());
				to.setStuPracRegMark(bo.getPracticalMarks());
				to.setCorrectedDate(bo.getLastModifiedDate().toString());
				toList.add(to);
			}
		}
		return toList;
	}
	/**
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public String getMaxMarksQuery(NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception {
		String query="select s.subject.id,internal.enteredMaxMark," +
		" s.theoryEseEnteredMaxMark, s.practicalEseEnteredMaxMark, ansScript.value,internal.isTheoryPractical,ansScript.isTheoryPractical from SubjectRuleSettings s" +
		" left join s.examSubjectRuleSettingsSubInternals internal" +
		" left join s.examSubjectRuleSettingsMulEvaluators eval" +
		" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " +
		" where s.course.id="+newStudentMarksCorrectionForm.getCourseId()+
		" and s.schemeNo=" +newStudentMarksCorrectionForm.getSchemeNo();
		if(newStudentMarksCorrectionForm.getEvaluatorType()!=null && !newStudentMarksCorrectionForm.getEvaluatorType().isEmpty()){
			query=query+" and eval.evaluatorId="+newStudentMarksCorrectionForm.getEvaluatorType();
		}
		if(newStudentMarksCorrectionForm.getAnswerScript()!=null && !newStudentMarksCorrectionForm.getAnswerScript().isEmpty()){
			query=query+" and ansScript.multipleAnswerScriptId="+newStudentMarksCorrectionForm.getAnswerScript();
		}	
		if(newStudentMarksCorrectionForm.getExamType().equals("Internal")){
			query=query+" and internal.internalExamTypeId=(select e.internalExamType.id" +
					" from ExamDefinition e where e.id="+newStudentMarksCorrectionForm.getExamId()+")";
		}
		if(newStudentMarksCorrectionForm.getYear()!=null && !newStudentMarksCorrectionForm.getYear().isEmpty()){
			query=query+" and s.academicYear="+newStudentMarksCorrectionForm.getYear();
		}else{
			if(newStudentMarksCorrectionForm.getExamType().equals("Supplementary")){
				query=query+" and s.academicYear=(select e.examForJoiningBatch from ExamDefinitionBO e where e.id="+newStudentMarksCorrectionForm.getExamId()+") ";
			}else{
				query=query+" and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+newStudentMarksCorrectionForm.getExamId()+") ";
			}
		}
		query=query+" and s.isActive=1";
		return query;
	}
	/**
	 * @param list
	 * @param newStudentMarksCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, SubjectMarksTO> getMaxMarksMap(List list,NewStudentMarksCorrectionForm newStudentMarksCorrectionForm) throws Exception {
		Map<Integer, SubjectMarksTO> map=new HashMap<Integer, SubjectMarksTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				
				if(obj[0]!=null){
					String subType=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(obj[0].toString()),"Subject",true,"isTheoryPractical");
					if(subType!=null){
					if(map.containsKey(Integer.parseInt(obj[0].toString()))){
						SubjectMarksTO to=map.remove(Integer.parseInt(obj[0].toString()));
						if(newStudentMarksCorrectionForm.getExamType().equals("Internal")){
							if(obj[5]!=null && obj[5].toString().equalsIgnoreCase("t")){
								if(obj[1]!=null)
								to.setFinalTheoryMarks(obj[1].toString());
							}
							if(obj[5]!=null && obj[5].toString().equalsIgnoreCase("p")){
								if(obj[5]!=null)
									to.setFinalPracticalMarks(obj[5].toString());
							}
						}else{
							if(newStudentMarksCorrectionForm.getAnswerScriptType()!=null && !newStudentMarksCorrectionForm.getAnswerScriptType().isEmpty()){
								if(obj[6]!=null && obj[6].toString().equalsIgnoreCase("t")){
									if(obj[4]!=null)
									to.setFinalTheoryMarks(obj[4].toString());
								}
								if(obj[6]!=null && obj[6].toString().equalsIgnoreCase("p")){
									if(obj[4]!=null)
										to.setFinalPracticalMarks(obj[4].toString());
								}
							}else{
								if(subType.equalsIgnoreCase("T") && obj[2]!=null){
									to.setFinalTheoryMarks(obj[2].toString());
								}else if(subType.equalsIgnoreCase("P") && obj[3]!=null){
									to.setFinalPracticalMarks(obj[3].toString());
								}else if(subType.equalsIgnoreCase("B")){
									if(obj[2]!=null)
										to.setFinalTheoryMarks(obj[2].toString());
									if(obj[3]!=null)
										to.setFinalPracticalMarks(obj[3].toString());
								}
							}
						}
						
						map.put(Integer.parseInt(obj[0].toString()),to);
					}else{
						SubjectMarksTO to=new SubjectMarksTO();
						
						if(newStudentMarksCorrectionForm.getExamType().equals("Internal")){
							if(obj[5]!=null && obj[5].toString().equalsIgnoreCase("t")){
								if(obj[1]!=null)
								to.setFinalTheoryMarks(obj[1].toString());
							}
							if(obj[5]!=null && obj[5].toString().equalsIgnoreCase("p")){
								if(obj[5]!=null)
									to.setFinalPracticalMarks(obj[5].toString());
							}
						}else{
							if(newStudentMarksCorrectionForm.getAnswerScript()!=null && !newStudentMarksCorrectionForm.getAnswerScript().isEmpty()){
								if(obj[6]!=null && obj[6].toString().equalsIgnoreCase("t")){
									if(obj[4]!=null)
									to.setFinalTheoryMarks(obj[4].toString());
								}
								if(obj[6]!=null && obj[6].toString().equalsIgnoreCase("p")){
									if(obj[4]!=null)
										to.setFinalPracticalMarks(obj[4].toString());
								}
							}else{
								if(subType.equalsIgnoreCase("T") && obj[2]!=null){
									to.setFinalTheoryMarks(obj[2].toString());
								}else if(subType.equalsIgnoreCase("P") && obj[3]!=null){
									to.setFinalPracticalMarks(obj[3].toString());
								}else if(subType.equalsIgnoreCase("B")){
									if(obj[2]!=null)
										to.setFinalTheoryMarks(obj[2].toString());
									if(obj[3]!=null)
										to.setFinalPracticalMarks(obj[3].toString());
								}
							}
						}
						
						
						
						
						
						
						map.put(Integer.parseInt(obj[0].toString()),to);
					}
					}
				}
			}
		}
		return map;
	}
}
