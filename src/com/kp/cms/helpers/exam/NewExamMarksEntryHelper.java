package com.kp.cms.helpers.exam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamRevaluationApplicationNew;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.handlers.exam.NewExamMarksEntryHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ExamComparator;

public class NewExamMarksEntryHelper {
	/**
	 * Singleton object of NewExamMarksEntryHelper
	 */
	private static volatile NewExamMarksEntryHelper newExamMarksEntryHelper = null;
	private static final Log log = LogFactory.getLog(NewExamMarksEntryHelper.class);
	static DecimalFormat df = new DecimalFormat("#.##");
	private NewExamMarksEntryHelper() {

	}
	/**
	 * return singleton object of newExamMarksEntryHelper.
	 * @return
	 */
	public static NewExamMarksEntryHelper getInstance() {
		if (newExamMarksEntryHelper == null) {
			newExamMarksEntryHelper = new NewExamMarksEntryHelper();
		}
		return newExamMarksEntryHelper;
	}
	/**
	 * creating query for Checking Marks Entered Through Secured 
	 * @param newExamMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCheckMarksEnteredThroughSecured(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception{
		String query="from MarksEntryDetails m" +
				" where m.subject.id=" +newExamMarksEntryForm.getSubjectId()+
				" and m.marksEntry.classes.course.id= " +newExamMarksEntryForm.getCourseId()+
				" and m.marksEntry.exam.id="+newExamMarksEntryForm.getExamId();
		if(newExamMarksEntryForm.getEvaluatorType()!=null && !newExamMarksEntryForm.getEvaluatorType().isEmpty()){
			query=query+" and m.marksEntry.evaluatorType="+newExamMarksEntryForm.getEvaluatorType();
		}
		if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
			query=query+" and m.marksEntry.answerScript="+newExamMarksEntryForm.getAnswerScriptType();
		}
		if(newExamMarksEntryForm.getSubjectType()!=null && !newExamMarksEntryForm.getSubjectType().isEmpty()){
			if(newExamMarksEntryForm.getSubjectType().equals("1")){
				query=query+" and m.theoryMarks is not null and m.isTheorySecured=1";
			}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
				query=query+" and m.practicalMarks is not null and m.isPracticalSecured=1";
			}else{
				query=query+" and m.theoryMarks is not null and m.practicalMarks is not null and m.isTheorySecured=1 and m.isPracticalSecured=1";
			}
		}
		return query;
	}
	/**
	 * creating query for Already Entered Marks Entered
	 * @param newExamMarksEntryForm
	 * @return
	 */
	public String getQueryForAlreadyEnteredMarks(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		String query="from MarksEntryDetails m" +
				" where m.subject.id=" +newExamMarksEntryForm.getSubjectId()+
				//			" and m.marksEntry.classes.course.id= " +newExamMarksEntryForm.getCourseId()+
				" and m.marksEntry.exam.id="+newExamMarksEntryForm.getExamId();
		if(newExamMarksEntryForm.getEvaluatorType()!=null && !newExamMarksEntryForm.getEvaluatorType().isEmpty()){
			query=query+" and m.marksEntry.evaluatorType="+newExamMarksEntryForm.getEvaluatorType();
		}
		if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
			query=query+" and m.marksEntry.answerScript="+newExamMarksEntryForm.getAnswerScriptType();
		}
		if(newExamMarksEntryForm.getSubjectType()!=null && !newExamMarksEntryForm.getSubjectType().isEmpty()){
			if(newExamMarksEntryForm.getSubjectType().equals("1")){
				query=query+" and m.theoryMarks is not null ";
			}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
				query=query+" and m.practicalMarks is not null ";
			}else{
				query=query+" and m.theoryMarks is not null and m.practicalMarks is not null ";
			}
		}
		return query;
	}
	/**
	 * creating the Bo List to Required Map 
	 * @param marksList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, MarksDetailsTO> convertBoDataToMarksMap(List marksList) throws Exception {
		Map<Integer, MarksDetailsTO> marksMap=new HashMap<Integer, MarksDetailsTO>();
		Iterator<MarksEntryDetails> itr=marksList.iterator();
		while (itr.hasNext()) {
			MarksEntryDetails marksEntryDetails = (MarksEntryDetails) itr.next();
			MarksDetailsTO to=new MarksDetailsTO();
			to.setId(marksEntryDetails.getId());
			to.setTheoryMarks(marksEntryDetails.getTheoryMarks());
			to.setPracticalMarks(marksEntryDetails.getPracticalMarks());
			if(marksEntryDetails.getMarksEntry().getClasses()!=null)
				to.setClassId(marksEntryDetails.getMarksEntry().getClasses().getId());
			to.setMarksId(marksEntryDetails.getMarksEntry().getId());
			if(marksEntryDetails.getIsTheorySecured()!=null && marksEntryDetails.getIsTheorySecured()){
				to.setTheorySecured(true);
			}else{
				to.setTheorySecured(false);
			}
			if(marksEntryDetails.getIsPracticalSecured()!=null && marksEntryDetails.getIsPracticalSecured()){
				to.setPracticalSecured(true);
			}else{
				to.setPracticalSecured(false);
			}
			marksMap.put(marksEntryDetails.getMarksEntry().getStudent().getId(),to);
		}
		return marksMap;
	}
	/**
	 * creating query for getting student for Current Class
	 * @param newExamMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCurrentClassStudents(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		String query="select s,s.classSchemewise.classes.id from Student s" +
				" join s.admAppln.applicantSubjectGroups appSub" +
				" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
				" where s.admAppln.isCancelled=0 and subGroup.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
				" from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+") " +
				" and subGroup.subject.id=" +newExamMarksEntryForm.getSubjectId()+
				//" and s.tcNo is null " +
				" and s.admAppln.courseBySelectedCourseId.id=" +newExamMarksEntryForm.getCourseId()+
				" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+newExamMarksEntryForm.getSchemeNo() ;
		//  "   and s.id in (select er.student.id from ExamRegularApplication er where er.exam.id= "+newExamMarksEntryForm.getExamId()+" " +
		//  " and er.student.classSchemewise.curriculumSchemeDuration.semesterYearNo="+newExamMarksEntryForm.getSchemeNo()+ ") ";
		if(newExamMarksEntryForm.getSection()!=null && !newExamMarksEntryForm.getSection().isEmpty() && !newExamMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and s.classSchemewise.classes.id="+newExamMarksEntryForm.getSection();
		}
		return query;
	}
	/**
	 * @param currentStudentList
	 * @param existsMarks
	 * @return
	 */
	public Set<StudentMarksTO> convertBotoTOListForCurrentStudents(Set<StudentMarksTO> studentList,List currentStudentList, Map<Integer, MarksDetailsTO> existsMarks,List<Integer> listOfDetainedStudents,NewExamMarksEntryForm newExamMarksEntryForm, String roleId/*,Map<Integer,String> oldRegNo*/) throws Exception {
		Iterator<Object[]> itr=currentStudentList.iterator();
		boolean disable=true;
		if(!newExamMarksEntryForm.isInternal() && !newExamMarksEntryForm.isRegular())
			disable=false;
		while (itr.hasNext()) {
			Object[] objects = (Object[]) itr.next();
			if(objects[0]!=null){
				Student student=(Student)objects[0];
				if(!listOfDetainedStudents.contains(student.getId())){
					StudentMarksTO to=new StudentMarksTO();
					to.setStudentId(student.getId());
					to.setName(student.getAdmAppln().getPersonalData().getFirstName());
					/*if(oldRegNo.containsKey(student.getId())){
						to.setRegisterNo(oldRegNo.get(student.getId()));
					}else{*/
					to.setRegisterNo(student.getRegisterNo());
					//					}
					to.setRollNo(student.getRollNo());
					if(existsMarks.containsKey(student.getId())){
						MarksDetailsTO detailTo=existsMarks.get(student.getId());
						to.setId(detailTo.getId());
						to.setPracticalMarks(detailTo.getPracticalMarks());
						to.setTheoryMarks(detailTo.getTheoryMarks());
						to.setOldPracticalMarks(detailTo.getPracticalMarks());
						to.setOldTheoryMarks(detailTo.getTheoryMarks());
						to.setClassId(detailTo.getClassId());
						to.setMarksId(detailTo.getMarksId());
						if(detailTo.isTheorySecured() || disable){
							if (roleId.equalsIgnoreCase("17") || roleId.equalsIgnoreCase("129")	
									|| roleId.equalsIgnoreCase("147")) {
								to.setIsTheorySecured(false);
							}
							else{
								to.setIsTheorySecured(true);
							}
						}
						else
							to.setIsTheorySecured(false);
						if(detailTo.isPracticalSecured() || disable){
							if (roleId.equalsIgnoreCase("17") || roleId.equalsIgnoreCase("129")
									|| roleId.equalsIgnoreCase("147")) {
								to.setIsPracticalSecured(false);
							}
							else{
								to.setIsPracticalSecured(true);
							}
						}
						else
							to.setIsPracticalSecured(false);
						if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
							to.setIsTheory(true);
							to.setIsPractical(false);
						}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
							to.setIsTheory(false);
							to.setIsPractical(true);
						}else{
							to.setIsTheory(true);
							to.setIsPractical(true);
						}

					}
					else{
						to.setIsTheorySecured(false);
						to.setIsPracticalSecured(false);
						if(objects[1]!=null){
							to.setClassId(Integer.parseInt(objects[1].toString()));

						}
						if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
							to.setIsTheory(true);
							to.setIsPractical(false);
						}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
							to.setIsTheory(false);
							to.setIsPractical(true);
						}else{
							to.setIsTheory(true);
							to.setIsPractical(true);
						}
					}
					studentList.add(to);

				}
			}
		}

		return studentList;
	}
	/**
	 * @param newExamMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousClassStudents(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception{
		String query="select s,classHis.classes.id from Student s" +
				" join s.studentPreviousClassesHistory classHis" +
				" join classHis.classes.classSchemewises classSchemewise" +
				" join s.studentSubjectGroupHistory subjHist" +
				" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
				" where s.admAppln.isCancelled=0  and subSet.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
				" from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+")" +
				" and classSchemewise.curriculumSchemeDuration.semesterYearNo=" +newExamMarksEntryForm.getSchemeNo()+
				" and classSchemewise.classes.course.id="+newExamMarksEntryForm.getCourseId()+
				" and subSet.subject.id="+newExamMarksEntryForm.getSubjectId();
		// "   and s.id in (select er.student.id from ExamRegularApplication er where er.exam.id= "+newExamMarksEntryForm.getExamId()+" " +
		// " and er.student.classSchemewise.curriculumSchemeDuration.semesterYearNo="+newExamMarksEntryForm.getSchemeNo()+ ") ";
		if(newExamMarksEntryForm.getSection()!=null && !newExamMarksEntryForm.getSection().isEmpty() && !newExamMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and classSchemewise.classes.id="+newExamMarksEntryForm.getSection();
		}
		query=query+" and subjHist.schemeNo="+newExamMarksEntryForm.getSchemeNo();
		return query;
	}
	/**
	 * @param studentList
	 * @param previousStudentList
	 * @param existsMarks
	 * @return
	 */
	public Set<StudentMarksTO> convertBotoTOListForPreviousClassStudents(Set<StudentMarksTO> studentList, List previousStudentList,Map<Integer, MarksDetailsTO> existsMarks,List<Integer> listOfDetainedStudents,NewExamMarksEntryForm newExamMarksEntryForm/*,Map<Integer,String> oldRegNo*/) throws Exception {
		boolean disable=true;
		if(!newExamMarksEntryForm.isInternal() && !newExamMarksEntryForm.isRegular())
			disable=false;
		Iterator<Object[]> itr=previousStudentList.iterator();
		while (itr.hasNext()) {
			Object[] objects = (Object[]) itr.next();
			if(objects[0]!=null){
				Student student=(Student)objects[0];
				if(!listOfDetainedStudents.contains(student.getId())){

					StudentMarksTO to=new StudentMarksTO();
					to.setStudentId(student.getId());
					to.setName(student.getAdmAppln().getPersonalData().getFirstName());
					to.setRegisterNo(student.getRegisterNo());

					if(existsMarks.containsKey(student.getId())){
						MarksDetailsTO detailTo=existsMarks.get(student.getId());


						to.setId(detailTo.getId());
						to.setPracticalMarks(detailTo.getPracticalMarks());
						to.setTheoryMarks(detailTo.getTheoryMarks());
						to.setOldPracticalMarks(detailTo.getPracticalMarks());
						to.setOldTheoryMarks(detailTo.getTheoryMarks());
						to.setClassId(detailTo.getClassId());
						to.setMarksId(detailTo.getMarksId());
						if(detailTo.isTheorySecured() || disable)
						{
							if (newExamMarksEntryForm.getRoleId().equalsIgnoreCase("17") || newExamMarksEntryForm.getRoleId().equalsIgnoreCase("147")
									|| newExamMarksEntryForm.getRoleId().equalsIgnoreCase("129")) {
								to.setIsTheorySecured(false);
							}
							else{
								to.setIsTheorySecured(true);
							}
						}
						else
							to.setIsTheorySecured(false);
						if(detailTo.isPracticalSecured() || disable)
						{
							if (newExamMarksEntryForm.getRoleId().equalsIgnoreCase("17") || newExamMarksEntryForm.getRoleId().equalsIgnoreCase("147")
									|| newExamMarksEntryForm.getRoleId().equalsIgnoreCase("129")) {
								to.setIsPracticalSecured(false);							}
							else{
								to.setIsPracticalSecured(true);							}
						}
						else
							to.setIsPracticalSecured(false);
						if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
							to.setIsTheory(true);
							to.setIsPractical(false);
						}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
							to.setIsTheory(false);
							to.setIsPractical(true);
						}else{
							to.setIsTheory(true);
							to.setIsPractical(true);
						}
						studentList.add(to);
					}
					else{

						to.setIsTheorySecured(false);
						to.setIsPracticalSecured(false);
						if(objects[1]!=null){
							to.setClassId(Integer.parseInt(objects[1].toString()));

						}
						if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
							to.setIsTheory(true);
							to.setIsPractical(false);
						}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
							to.setIsTheory(false);
							to.setIsPractical(true);
						}else{
							to.setIsTheory(true);
							to.setIsPractical(true);
						}
						studentList.add(to);

					}

				}
			}
		}

		return studentList;
	}
	/**
	 * @param newExamMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryCurrentClassStudents(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		String query="select s,c.id,cd.academicYear  from Student s" +
				" join s.classSchemewise.classes c" +
				" join s.classSchemewise.curriculumSchemeDuration cd" +
				" join s.admAppln.applicantSubjectGroups appSub";
		if(!"5".equals(newExamMarksEntryForm.getExamType())) {
			query=query+" join s.examRegularApplication examRev";
		}				
		query+=" join appSub.subjectGroup.subjectGroupSubjectses subGroup"+
				" join s.studentSupplementaryImprovements supImp with supImp.subject.id="+newExamMarksEntryForm.getSubjectId()+
				" and supImp.examDefinition.id="+newExamMarksEntryForm.getExamId()+
				" where s.admAppln.isCancelled=0 and subGroup.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and s.classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
				" from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+")" +
				" and s.admAppln.courseBySelectedCourseId.id="+newExamMarksEntryForm.getCourseId()+
				" and supImp.classes.course.id="+newExamMarksEntryForm.getCourseId()+
				" and subGroup.subject.id="+newExamMarksEntryForm.getSubjectId()+
				" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+newExamMarksEntryForm.getSchemeNo();				

		if("5".equals(newExamMarksEntryForm.getExamType())) {
			if(newExamMarksEntryForm.getSubjectType()!=null && !newExamMarksEntryForm.getSubjectType().isEmpty()){
				if(newExamMarksEntryForm.getSubjectType().equals("1")){
					query=query+" and supImp.isCIAFailedTheory=1";
				}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
					query=query+" and supImp.isCIAFailedPractical=1";
				}else{
					query=query+" and supImp.isCIAFailedTheory=1 and supImp.isCIAFailedPractical=1";
				}
			}
		}
		else {
			if(newExamMarksEntryForm.getSubjectType()!=null && !newExamMarksEntryForm.getSubjectType().isEmpty()){
				if(Integer.parseInt(newExamMarksEntryForm.getExamId())>112) {
					query += " and examRev.isChallanVerified=1 and examRev.exam.id="+newExamMarksEntryForm.getExamId();
				}
				if(newExamMarksEntryForm.getSubjectType().equals("1")){
					query=query+" and supImp.isAppearedTheory=1";
				}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
					query=query+" and supImp.isAppearedPractical=1";
				}else{
					query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1";
				}
			}
		}		
		if(newExamMarksEntryForm.getSection()!=null && !newExamMarksEntryForm.getSection().isEmpty() && !newExamMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and s.classSchemewise.classes.id="+newExamMarksEntryForm.getSection();
		}
		return query;
	}
	/**
	 * @param studentList
	 * @param currentStudentList
	 * @param existsMarks
	 * @return
	 */
	public Set<StudentMarksTO> convertBotoTOListForSupplementaryCurrentStudents(Set<StudentMarksTO> studentList, List currentStudentList,Map<Integer, MarksDetailsTO> existsMarks,NewExamMarksEntryForm newExamMarksEntryForm,Map<Integer,String> oldRegNo) throws Exception {
		boolean disable=true;
		if(!newExamMarksEntryForm.isInternal() && !newExamMarksEntryForm.isRegular())
			disable=false;
		Iterator<Object[]> itr=currentStudentList.iterator();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		while (itr.hasNext()) {
			Object[] objects = (Object[]) itr.next();
			if(objects[0]!=null){
				Student student=(Student)objects[0];
				StudentMarksTO to=new StudentMarksTO();
				to.setStudentId(student.getId());
				to.setName(student.getAdmAppln().getPersonalData().getFirstName());
				if(oldRegNo.containsKey(student.getId())){
					to.setRegisterNo(oldRegNo.get(student.getId()));
				}else{
					to.setRegisterNo(student.getRegisterNo());
				}
				if(existsMarks.containsKey(student.getId())){
					MarksDetailsTO detailTo=existsMarks.get(student.getId());
					to.setId(detailTo.getId());
					to.setPracticalMarks(detailTo.getPracticalMarks());
					to.setTheoryMarks(detailTo.getTheoryMarks());
					to.setOldPracticalMarks(detailTo.getPracticalMarks());
					to.setOldTheoryMarks(detailTo.getTheoryMarks());
					to.setClassId(detailTo.getClassId());
					to.setMarksId(detailTo.getMarksId());
					if(detailTo.isTheorySecured() || disable)
					{
						if (newExamMarksEntryForm.getRoleId().equalsIgnoreCase("17") || newExamMarksEntryForm.getRoleId().equalsIgnoreCase("147")
								|| newExamMarksEntryForm.getRoleId().equalsIgnoreCase("129")) {
							to.setIsTheorySecured(false);
						}
						else{
							to.setIsTheorySecured(true);
						}
					}
					else
						to.setIsTheorySecured(false);
					if(detailTo.isPracticalSecured() || disable)
					{
						if (newExamMarksEntryForm.getRoleId().equalsIgnoreCase("17") || newExamMarksEntryForm.getRoleId().equalsIgnoreCase("147")
								|| newExamMarksEntryForm.getRoleId().equalsIgnoreCase("129")) {
							to.setIsPracticalSecured(false);							}
						else{
							to.setIsPracticalSecured(true);							}
					}
					else
						to.setIsPracticalSecured(false);
				}else{
					if(objects[1]!=null){
						to.setClassId(Integer.parseInt(objects[1].toString()));
					}
					to.setIsTheorySecured(false);
					to.setIsPracticalSecured(false);
				}
				if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
					to.setIsTheory(true);
					to.setIsPractical(false);
				}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
					to.setIsTheory(false);
					to.setIsPractical(true);
				}else{
					to.setIsTheory(true);
					to.setIsPractical(true);
				}
				if(objects[2] != null && objects[2].toString() != null){
					map.put(student.getId(), Integer.parseInt(objects[2].toString()));
				}
				studentList.add(to);
			}
		}
		newExamMarksEntryForm.setStudentsYearMap(map);
		return studentList;
	}
	/**
	 * @param newExamMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryPreviousClassStudents(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception{
		String query="select s,classHis.classes.id,cd.academicYear from Student s" +
				" join s.studentPreviousClassesHistory classHis" +
				" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
				" join s.studentSubjectGroupHistory subjHist" +
				" join subjHist.subjectGroup.subjectGroupSubjectses subSet";
		if(!"5".equals(newExamMarksEntryForm.getExamType())) {
			query += " join s.examRegularApplication examRev";
		}				
		query += " join s.studentSupplementaryImprovements supImp with supImp.subject.id=" +newExamMarksEntryForm.getSubjectId()+
				" and supImp.examDefinition.id=" +newExamMarksEntryForm.getExamId()+
				" where s.admAppln.isCancelled=0  and subSet.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
				" from ExamDefinitionBO e" +
				" where e.id="+newExamMarksEntryForm.getExamId()+") and classSchemewise.curriculumSchemeDuration.semesterYearNo="+newExamMarksEntryForm.getSchemeNo()+
				" and classSchemewise.classes.course.id="+newExamMarksEntryForm.getCourseId()+
				" and subSet.subject.id="+newExamMarksEntryForm.getSubjectId()+
				" and supImp.classes.course.id=" +newExamMarksEntryForm.getCourseId();

		if("5".equals(newExamMarksEntryForm.getExamType())) {
			if(newExamMarksEntryForm.getSubjectType()!=null && !newExamMarksEntryForm.getSubjectType().isEmpty()){
				if(newExamMarksEntryForm.getSubjectType().equals("1")){
					query=query+" and supImp.isCIAFailedTheory=1";
				}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
					query=query+" and supImp.isCIAFailedPractical=1";
				}else{
					query=query+" and supImp.isCIAFailedTheory=1 and supImp.isCIAFailedPractical=1";
				}
			}
		}
		else {
			if(newExamMarksEntryForm.getSubjectType()!=null && !newExamMarksEntryForm.getSubjectType().isEmpty()){
				if(Integer.parseInt(newExamMarksEntryForm.getExamId())>112) {
					query += " and examRev.isChallanVerified=1 and examRev.exam.id="+newExamMarksEntryForm.getExamId();
				}
				if(newExamMarksEntryForm.getSubjectType().equals("1")){
					query=query+" and supImp.isAppearedTheory=1";
				}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
					query=query+" and supImp.isAppearedPractical=1";
				}else{
					query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1";
				}
			}
		}
		if(newExamMarksEntryForm.getSection()!=null && !newExamMarksEntryForm.getSection().isEmpty() && !newExamMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and classSchemewise.classes.id="+newExamMarksEntryForm.getSection();
		}
		return query;
	}
	/**
	 * @param studentList
	 * @param previousStudentList
	 * @param existsMarks
	 * @return
	 */
	public Set<StudentMarksTO> convertBotoTOListForSupplementaryPreviousClassStudents(Set<StudentMarksTO> studentList, List previousStudentList,Map<Integer, MarksDetailsTO> existsMarks,NewExamMarksEntryForm newExamMarksEntryForm,Map<Integer,String> oldRegNo) throws Exception{
		boolean disable=true;
		if(!newExamMarksEntryForm.isInternal() && !newExamMarksEntryForm.isRegular())
			disable=false;
		Iterator<Object[]> itr=previousStudentList.iterator();
		Map<Integer, Integer> map = newExamMarksEntryForm.getStudentsYearMap();
		while (itr.hasNext()) {
			Object[] objects = (Object[]) itr.next();
			if(objects[0]!=null){
				Student student=(Student)objects[0];
				StudentMarksTO to=new StudentMarksTO();
				to.setStudentId(student.getId());
				to.setName(student.getAdmAppln().getPersonalData().getFirstName());
				/*if(oldRegNo.containsKey(student.getId())){	//	commented as per requested
					to.setRegisterNo(oldRegNo.get(student.getId()));
				}else{*/
				to.setRegisterNo(student.getRegisterNo());
				//}
				if(existsMarks.containsKey(student.getId())){
					MarksDetailsTO detailTo=existsMarks.get(student.getId());
					to.setId(detailTo.getId());
					to.setPracticalMarks(detailTo.getPracticalMarks());
					to.setTheoryMarks(detailTo.getTheoryMarks());
					to.setOldPracticalMarks(detailTo.getPracticalMarks());
					to.setOldTheoryMarks(detailTo.getTheoryMarks());
					to.setClassId(detailTo.getClassId());
					to.setMarksId(detailTo.getMarksId());
					if(detailTo.isTheorySecured() || disable)
					{
						if (newExamMarksEntryForm.getRoleId().equalsIgnoreCase("17") || newExamMarksEntryForm.getRoleId().equalsIgnoreCase("147")
								|| newExamMarksEntryForm.getRoleId().equalsIgnoreCase("129")) {
							to.setIsTheorySecured(false);
						}
						else{
							to.setIsTheorySecured(true);
						}
					}
					else
						to.setIsTheorySecured(false);
					if(detailTo.isPracticalSecured() || disable)
					{
						if (newExamMarksEntryForm.getRoleId().equalsIgnoreCase("17") || newExamMarksEntryForm.getRoleId().equalsIgnoreCase("147")
								|| newExamMarksEntryForm.getRoleId().equalsIgnoreCase("129")) {
							to.setIsPracticalSecured(false);							}
						else{
							to.setIsPracticalSecured(true);							}
					}
					else
						to.setIsPracticalSecured(false);
				}else{
					if(objects[1]!=null){
						to.setClassId(Integer.parseInt(objects[1].toString()));
					}
					to.setIsTheorySecured(false);
					to.setIsPracticalSecured(false);
				}
				if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
					to.setIsTheory(true);
					to.setIsPractical(false);
				}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
					to.setIsTheory(false);
					to.setIsPractical(true);
				}else{
					to.setIsTheory(true);
					to.setIsPractical(true);
				}
				if(map != null){
					if(objects[2] != null && objects[2].toString() != null){
						map.put(student.getId(), Integer.parseInt(objects[2].toString()));
					}
				}
				studentList.add(to);
			}
		}
		newExamMarksEntryForm.setStudentsYearMap(map);
		return studentList;
	}
	/**
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public String getQueryForOldRegisterNos(String schemeNo) throws Exception{
		//		String query="select e.student.id, min(e.registerNo) from ExamStudentDetentionRejoinDetails e left join e.rejoinClassSchemewise classSche left join classSche.classes c where ((e.schemeNo < c.termNumber) or (c.termNumber is null))" +
		//				" and "+schemeNo+"<= e.schemeNo group by e.student.id ";
		String query="select stu.student.id,stu.registerNo " +
				" from StudentOldRegisterNumber stu " +
				" where stu.isActive=1 and stu.schemeNo="+schemeNo+" order by stu.student.id ";
		return query;
	}
	/**
	 * @param oldRegList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getOldRegMap(List oldRegList) throws Exception {
		Map<Integer,String> map=new HashMap<Integer, String>();
		if(oldRegList!=null && !oldRegList.isEmpty()){
			Iterator<Object[]> itr=oldRegList.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if(obj[0]!=null && obj[1]!=null)
					map.put(Integer.parseInt(obj[0].toString()),obj[1].toString());
			}
		}
		return map;
	}
	public String getQueryForRevaluationMarksEntry(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		String revQuery = " select er.student.id from ExamRevaluationApplicationNew er "+
				" where er.classes.course.id="+newExamMarksEntryForm.getCourseId()+"  and er.classes.termNumber="+newExamMarksEntryForm.getSchemeNo()+" and er.isApplied=1 "+
				" and er.exam.id= "+newExamMarksEntryForm.getExamId() +" and er.subject.id="+newExamMarksEntryForm.getSubjectId();
		if(newExamMarksEntryForm.getEntryType().equalsIgnoreCase("R"))
			revQuery = revQuery+	" and er.isRevaluation=1 " ;
		if(newExamMarksEntryForm.getEntryType().equalsIgnoreCase("S"))	
			revQuery = revQuery+	" and er.isScrutiny=1 ";
		if(newExamMarksEntryForm.getEntryType().equalsIgnoreCase("CV"))
			revQuery = revQuery+	" and er.isChallengeValuation=1 ";
		return revQuery;
	}

	public String getQueryForAlreadyEnteredMarksForRevaluation(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		String query="from MarksEntryDetails m" +
				" where m.subject.id=" +newExamMarksEntryForm.getSubjectId()+
				//			" and m.marksEntry.classes.course.id= " +newExamMarksEntryForm.getCourseId()+
				" and m.marksEntry.exam.id="+newExamMarksEntryForm.getExamId() +" and m.marksEntry.student.id in (:studentList) ";

		if(newExamMarksEntryForm.getSubjectType()!=null && !newExamMarksEntryForm.getSubjectType().isEmpty()){
			if(newExamMarksEntryForm.getSubjectType().equals("1")){
				query=query+" and m.theoryMarks is not null ";
			}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
				query=query+" and m.practicalMarks is not null ";
			}else{
				query=query+" and m.theoryMarks is not null and m.practicalMarks is not null ";
			}
		}
		return query;
	}
	public String getQueryForPreviousClassStudentsForAllInternals(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception{
		String query="select s,classHis.classes.id from Student s" +
				" join s.studentPreviousClassesHistory classHis" +
				" join classHis.classes.classSchemewises classSchemewise" +
				" join s.studentSubjectGroupHistory subjHist" +
				" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
				" where s.admAppln.isCancelled=0  and subSet.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and classSchemewise.curriculumSchemeDuration.academicYear="+newExamMarksEntryForm.getYear()+
				" and classSchemewise.curriculumSchemeDuration.semesterYearNo=" +newExamMarksEntryForm.getSchemeNo()+
				" and classSchemewise.classes.course.id="+newExamMarksEntryForm.getCourseId()+
				" and subSet.subject.id="+newExamMarksEntryForm.getSubjectId();
		if(newExamMarksEntryForm.getSection()!=null && !newExamMarksEntryForm.getSection().isEmpty() && !newExamMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and classSchemewise.classes.id="+newExamMarksEntryForm.getSection();
		}
		query=query+" and subjHist.schemeNo="+newExamMarksEntryForm.getSchemeNo();
		return query;
	}
	public Map<String, MarksDetailsTO> convertBoDataToMarksMapForAll(List marksList) throws Exception {
		Map<String, MarksDetailsTO> marksMap=new HashMap<String, MarksDetailsTO>();
		Iterator<MarksEntryDetails> itr=marksList.iterator();
		while (itr.hasNext()) {
			MarksEntryDetails marksEntryDetails = (MarksEntryDetails) itr.next();
			MarksDetailsTO to=new MarksDetailsTO();
			to.setId(marksEntryDetails.getId());
			to.setTheoryMarks(marksEntryDetails.getTheoryMarks());
			to.setPracticalMarks(marksEntryDetails.getPracticalMarks());
			if(marksEntryDetails.getMarksEntry().getClasses()!=null)
				to.setClassId(marksEntryDetails.getMarksEntry().getClasses().getId());
			to.setMarksId(marksEntryDetails.getMarksEntry().getId());
			if(marksEntryDetails.getIsTheorySecured()!=null && marksEntryDetails.getIsTheorySecured()){
				to.setTheorySecured(true);
			}else{
				to.setTheorySecured(false);
			}
			if(marksEntryDetails.getIsPracticalSecured()!=null && marksEntryDetails.getIsPracticalSecured()){
				to.setPracticalSecured(true);
			}else{
				to.setPracticalSecured(false);
			}
			marksMap.put(marksEntryDetails.getMarksEntry().getStudent().getId()+"_"+marksEntryDetails.getMarksEntry().getExam().getId(),to);
		}
		return marksMap;
	}
	public String getQueryForCurrentClassStudentsForAllInternals(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		String query="select s,s.classSchemewise.classes.id from Student s" +
				" join s.admAppln.applicantSubjectGroups appSub" +
				" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
				" where s.admAppln.isCancelled=0 and subGroup.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and s.classSchemewise.curriculumSchemeDuration.academicYear="+newExamMarksEntryForm.getYear() +
				" and subGroup.subject.id=" +newExamMarksEntryForm.getSubjectId()+
				" and s.admAppln.courseBySelectedCourseId.id=" +newExamMarksEntryForm.getCourseId()+
				" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+newExamMarksEntryForm.getSchemeNo();
		if(newExamMarksEntryForm.getSection()!=null && !newExamMarksEntryForm.getSection().isEmpty() && !newExamMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and s.classSchemewise.classes.id="+newExamMarksEntryForm.getSection();
		}
		return query;
	}
	public List<StudentTO> convertBotoTOListForCurrentStudentsForAll(List<StudentTO> allstudentsList,List currentStudentList, Map<String, MarksDetailsTO> existsMarks,List<Integer> listOfDetainedStudents,NewExamMarksEntryForm newExamMarksEntryForm/*,Map<Integer,String> oldRegNo*/) throws Exception {


		Set<Integer> examSet=newExamMarksEntryForm.getExamNameList().keySet();
		Map<Integer,Boolean> examExceeded=new HashMap<Integer, Boolean>();
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		Map<Integer,Double> maxMarkMap=NewExamMarksEntryHandler.getInstance().getMaxMarkOfSubjectForAllInternals(newExamMarksEntryForm);
		Iterator<Integer> examitrtr=examSet.iterator();
		while (examitrtr.hasNext()) {	
			int examId=examitrtr.next();
			newExamMarksEntryForm.setExamid(examId);
			boolean exceeded=txn.isCurrentDateValidForAllInternalMarks(newExamMarksEntryForm);
			newExamMarksEntryForm.setIsExamMaxEntry(false);
			if(CMSConstants.INTERNAL_EXAM_IDS.contains(examId))
				newExamMarksEntryForm.setIsExamMaxEntry(true);
			examExceeded.put(examId, exceeded);
		}
		Iterator<Object[]> itr=currentStudentList.iterator();
		boolean disable=true;
		if(!newExamMarksEntryForm.isInternal() && !newExamMarksEntryForm.isRegular())
			disable=false;
		while (itr.hasNext()) {

			Object[] objects = (Object[]) itr.next();
			if(objects[0]!=null){
				Student student=(Student)objects[0];
				if(!listOfDetainedStudents.contains(student.getId())){
					float totalMarksT=0;
					float totalMarksP=0;
					boolean zeroMarks=false;
					String totalMarksTS=null;
					String totalMarksPS=null;
					StudentTO studentto=new StudentTO();
					studentto.setId(student.getId());
					studentto.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
					studentto.setRegisterNo(student.getRegisterNo());
					studentto.setRollNo(student.getRollNo());
					List<StudentMarksTO> studentMarksList=new ArrayList<StudentMarksTO>();
					Iterator<Integer> examitr=examSet.iterator();
					while (examitr.hasNext()) {

						int examId=examitr.next();
						StudentMarksTO to=new StudentMarksTO();
						to.setStudentId(student.getId());
						//if(to.getStudentId()== 12749){
						to.setName(student.getAdmAppln().getPersonalData().getFirstName());
						/*if(oldRegNo.containsKey(student.getId())){
						to.setRegisterNo(oldRegNo.get(student.getId()));
					}else{*/
						to.setRegisterNo(student.getRegisterNo());
						//					}
						if(newExamMarksEntryForm.getIsRoleIdForDeveloper()){
							to.setAllInternalDateExceeded(true);
						}else{
							to.setAllInternalDateExceeded(examExceeded.get(examId));
						}

						//exist marks to
						to.setIsConversion(false);
						if(existsMarks.containsKey(student.getId()+"_"+examId)){
							zeroMarks=true;
							MarksDetailsTO detailTo=existsMarks.get(student.getId()+"_"+examId);
							to.setId(detailTo.getId());
							to.setPracticalMarks(detailTo.getPracticalMarks());
							to.setTheoryMarks(detailTo.getTheoryMarks());
							to.setOldPracticalMarks(detailTo.getPracticalMarks());
							to.setOldTheoryMarks(detailTo.getTheoryMarks());
							to.setClassId(detailTo.getClassId());
							to.setMarksId(detailTo.getMarksId());
							to.setExamId(""+examId);
							double actualMarks = 0.0;
							if(CMSConstants.INTERNAL_EXAM_IDS.contains(examId)){
								if(detailTo.getTheoryMarks()!=null && !detailTo.getTheoryMarks().isEmpty() && CommonUtil.isValidDecimal(detailTo.getTheoryMarks())){
									double convMark=Double.parseDouble(detailTo.getTheoryMarks());
									//							Iterator<StudentTO> iterator =  (Iterator<StudentTO>) newExamMarksEntryForm.getStudentMarksList();
									//							while(iterator.hasNext()){
									//								StudentTO stuTo = iterator.next();
									//								Iterator<StudentMarksTO> iterator2 = (Iterator<StudentMarksTO>) stuTo.getStudentMarksList();
									//								while(iterator2.hasNext()){
									//									StudentMarksTO marksTO = iterator2.next();
									//									if(to.getStudentId()== marksTO.getStudentId()){
									//										actualMarks =Double.parseDouble(marksTO.getTheoryMarks());
									//									}
									//									
									//								}
									//							}
									convMark=CommonUtil.round((convMark*80)/10, 0);
									//studentto.setMaxMarksInternal(String.valueOf((int)convMark));

									//code for any type marks entry rounding
									if(convMark != 0.0){
										String change = String.valueOf(convMark).substring(3,4);
										if(change.equalsIgnoreCase("0")){
											convMark = Math.round(convMark);
										}
									}

									studentto.setMaxMarksInternal(String.valueOf(convMark));
									//to.setTheoryMarks(String.valueOf((int)convMark));
									to.setTheoryMarks(String.valueOf(convMark));
									studentto.setMaxMarksInternal(detailTo.getTheoryMarks());
									to.setIsConversion(true);
								}else if(detailTo.getTheoryMarks()!=null){
									to.setIsConversion(true);
									studentto.setMaxMarksInternal(String.valueOf(detailTo.getTheoryMarks()));
									to.setTheoryMarks(String.valueOf(detailTo.getTheoryMarks()));

								}

								if(detailTo.getPracticalMarks()!=null && CommonUtil.isValidDecimal(detailTo.getPracticalMarks())){
									double convMark=Double.parseDouble(detailTo.getPracticalMarks());
									convMark=CommonUtil.round((convMark*80)/10, 0);

									//code for any type marks entry rounding
									if(convMark != 0.0){
										String change = String.valueOf(convMark).substring(3,4);
										if(change.equalsIgnoreCase("0")){
											convMark = Math.round(convMark);
										}
									}
									//studentto.setMaxMarksInternal(String.valueOf((int)convMark));
									studentto.setMaxMarksInternal(String.valueOf(convMark));
									//to.setPracticalMarks(String.valueOf((int)convMark));
									to.setPracticalMarks(String.valueOf(convMark));
									studentto.setMaxMarksInternal(detailTo.getPracticalMarks());
									to.setIsConversion(true);
								}else if(detailTo.getPracticalMarks()!=null){
									to.setIsConversion(true);
									studentto.setMaxMarksInternal(String.valueOf(detailTo.getPracticalMarks()));
									to.setPracticalMarks(String.valueOf(detailTo.getPracticalMarks()));

								}

							}
							if(detailTo.isTheorySecured() || disable)
								to.setIsTheorySecured(true);
							else
								to.setIsTheorySecured(false);
							if(detailTo.isPracticalSecured() || disable)
								to.setIsPracticalSecured(true);
							else
								to.setIsPracticalSecured(false);

							if(detailTo.getTheoryMarks()!=null && !StringUtils.isAlpha(detailTo.getTheoryMarks())){
								totalMarksT=totalMarksT+Float.parseFloat(detailTo.getTheoryMarks());
							}else{
								totalMarksTS=detailTo.getTheoryMarks();
							}
							if(detailTo.getPracticalMarks()!=null && !StringUtils.isAlpha(detailTo.getPracticalMarks())){
								totalMarksP=totalMarksP+Float.parseFloat(detailTo.getPracticalMarks());
							}else{
								totalMarksPS=detailTo.getPracticalMarks();
							}

						}
						//new to
						else{
							if(objects[1]!=null){
								to.setClassId(Integer.parseInt(objects[1].toString()));
							}
							to.setExamId(""+examId);
							to.setIsTheorySecured(false);
							to.setIsPracticalSecured(false);
						}


						if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
							to.setIsTheory(true);
							to.setIsPractical(false);
						}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
							to.setIsTheory(false);
							to.setIsPractical(true);
						}else{
							to.setIsTheory(true);
							to.setIsPractical(true);
						}
						if(maxMarkMap.containsKey(examId))
							to.setMaxMarks(maxMarkMap.get(examId));
						studentMarksList.add(to);
						//} particular student checking
					}// exam set	

					if(totalMarksT!=0){
						if(student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getName().equalsIgnoreCase("UG")){
							if(newExamMarksEntryForm.getYear().equalsIgnoreCase("2015")){
								studentto.setTotalInternalMarksT((Math.ceil(totalMarksT))+"");
							}else{
								studentto.setTotalInternalMarksT(df.format(totalMarksT)+"");
							}


						}else{
							studentto.setTotalInternalMarksT((Math.ceil(totalMarksT))+"");
						}
					}else{
						if(totalMarksTS!=null){
							studentto.setTotalInternalMarksT(totalMarksTS);
						}else if(zeroMarks){
							studentto.setTotalInternalMarksT(totalMarksT+"");	
						}
					}

					if(totalMarksP!=0){
						if(student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getName().equalsIgnoreCase("UG")){
							if(newExamMarksEntryForm.getYear().equalsIgnoreCase("2015")){
								studentto.setTotalInternalMarksP((Math.ceil(totalMarksP))+"");
							}else{
								studentto.setTotalInternalMarksP(df.format(totalMarksP)+"");
							}


						}else{
							studentto.setTotalInternalMarksP((Math.ceil(totalMarksP))+"");
						}
					}else{
						if(totalMarksPS!=null){
							studentto.setTotalInternalMarksP(totalMarksPS);
						}else if(zeroMarks){
							studentto.setTotalInternalMarksP(totalMarksP+"");	
						}
					}


					ExamComparator e=new ExamComparator();
					e.setCompare(0);
					Collections.sort(studentMarksList, e);
					studentto.setStudentMarksList(studentMarksList);
					allstudentsList.add(studentto);

					newExamMarksEntryForm.setProgramTypeId(student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId()+"");


				}//detained check
			}//empty check


		}//main students over

		return allstudentsList;
	}
	public List<StudentTO> convertBotoTOListForPreviousClassStudentsForAll(List<StudentTO> allstudentsList, List previousStudentList,Map<String, MarksDetailsTO> existsMarks,List<Integer> listOfDetainedStudents,NewExamMarksEntryForm newExamMarksEntryForm/*,Map<Integer,String> oldRegNo*/) throws Exception {


		Set<Integer> examSet=newExamMarksEntryForm.getExamNameList().keySet();
		Map<Integer,Boolean> examExceeded=new HashMap<Integer, Boolean>();
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		Map<Integer,Double> maxMarkMap=NewExamMarksEntryHandler.getInstance().getMaxMarkOfSubjectForAllInternals(newExamMarksEntryForm);
		Iterator<Integer> examitrtr=examSet.iterator();
		while (examitrtr.hasNext()) {	
			int examId=examitrtr.next();
			newExamMarksEntryForm.setExamid(examId);
			boolean exceeded=txn.isCurrentDateValidForAllInternalMarks(newExamMarksEntryForm);
			newExamMarksEntryForm.setIsExamMaxEntry(false);
			if(CMSConstants.INTERNAL_EXAM_IDS.contains(examId))
				newExamMarksEntryForm.setIsExamMaxEntry(true);
			examExceeded.put(examId, exceeded);
		}

		boolean disable=true;
		if(!newExamMarksEntryForm.isInternal() && !newExamMarksEntryForm.isRegular())
			disable=false;
		Iterator<Object[]> itr=previousStudentList.iterator();
		while (itr.hasNext()) {
			Object[] objects = (Object[]) itr.next();
			if(objects[0]!=null){
				Student student=(Student)objects[0];
				if(!listOfDetainedStudents.contains(student.getId())){
					float totalMarksT=0;
					float totalMarksP=0;
					boolean zeroMarks=false;
					String totalMarksTS=null;
					String totalMarksPS=null;
					StudentTO studentto=new StudentTO();
					studentto.setId(student.getId());
					studentto.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
					studentto.setRegisterNo(student.getRegisterNo());
					studentto.setRollNo(student.getRollNo());
					List<StudentMarksTO> studentMarksList=new ArrayList<StudentMarksTO>();
					Iterator<Integer> examitr=examSet.iterator();
					while (examitr.hasNext()) {

						int examId=examitr.next();

						StudentMarksTO to=new StudentMarksTO();
						to.setStudentId(student.getId());
						to.setName(student.getAdmAppln().getPersonalData().getFirstName());
						/*if(oldRegNo.containsKey(student.getId())){
					to.setRegisterNo(oldRegNo.get(student.getId()));
				}else{*/
						to.setRegisterNo(student.getRegisterNo());
						//				}
						if(newExamMarksEntryForm.getIsRoleIdForDeveloper()){
							to.setAllInternalDateExceeded(true);
						}else{
							to.setAllInternalDateExceeded(examExceeded.get(examId));
						}

						//exist marks to
						to.setIsConversion(false);	
						if(existsMarks.containsKey(student.getId()+"_"+examId)){
							zeroMarks=true;
							MarksDetailsTO detailTo=existsMarks.get(student.getId()+"_"+examId);
							to.setId(detailTo.getId());
							to.setPracticalMarks(detailTo.getPracticalMarks());
							to.setTheoryMarks(detailTo.getTheoryMarks());
							to.setOldPracticalMarks(detailTo.getPracticalMarks());
							to.setOldTheoryMarks(detailTo.getTheoryMarks());
							to.setClassId(detailTo.getClassId());
							to.setMarksId(detailTo.getMarksId());
							to.setExamId(""+examId);
							if(CMSConstants.INTERNAL_EXAM_IDS.contains(examId)){
								if(detailTo.getTheoryMarks()!=null && !detailTo.getTheoryMarks().isEmpty() && CommonUtil.isValidDecimal(detailTo.getTheoryMarks())){
									double convMark=Double.parseDouble(detailTo.getTheoryMarks());
									convMark=CommonUtil.round((convMark*80)/10, 0);
									//code for any type marks entry rounding
									if(convMark != 0.0){
										String change = String.valueOf(convMark).substring(3,4);
										if(change.equalsIgnoreCase("0")){
											convMark = Math.round(convMark);
										}
									}
									//studentto.setMaxMarksInternal(String.valueOf((int)convMark));
									studentto.setMaxMarksInternal(String.valueOf(convMark));
									//to.setTheoryMarks(String.valueOf((int)convMark));
									to.setTheoryMarks(String.valueOf(convMark));
									studentto.setMaxMarksInternal(detailTo.getTheoryMarks());
									to.setIsConversion(true);
								}else if(detailTo.getTheoryMarks()!=null){
									to.setIsConversion(true);
									studentto.setMaxMarksInternal(String.valueOf(detailTo.getTheoryMarks()));
									to.setTheoryMarks(String.valueOf(detailTo.getTheoryMarks()));

								}

								if(detailTo.getPracticalMarks()!=null && CommonUtil.isValidDecimal(detailTo.getPracticalMarks())){
									double convMark=Double.parseDouble(detailTo.getPracticalMarks());
									convMark=CommonUtil.round((convMark*80)/10, 0);

									//code for any type marks entry rounding
									if(convMark != 0.0){
										String change = String.valueOf(convMark).substring(3,4);
										if(change.equalsIgnoreCase("0")){
											convMark = Math.round(convMark);
										}
									}
									//studentto.setMaxMarksInternal(String.valueOf((int)convMark));
									studentto.setMaxMarksInternal(String.valueOf(convMark));
									//to.setPracticalMarks(String.valueOf((int)convMark));
									to.setPracticalMarks(String.valueOf(convMark));
									studentto.setMaxMarksInternal(detailTo.getPracticalMarks());
									to.setIsConversion(true);
								}else if(detailTo.getPracticalMarks()!=null){
									to.setIsConversion(true);
									studentto.setMaxMarksInternal(String.valueOf(detailTo.getPracticalMarks()));
									to.setPracticalMarks(String.valueOf(detailTo.getPracticalMarks()));

								}

							}
							if(detailTo.isTheorySecured() || disable)
								to.setIsTheorySecured(true);
							else
								to.setIsTheorySecured(false);
							if(detailTo.isPracticalSecured() || disable)
								to.setIsPracticalSecured(true);
							else
								to.setIsPracticalSecured(false);

							if(detailTo.getTheoryMarks()!=null && !StringUtils.isAlpha(detailTo.getTheoryMarks())){
								totalMarksT=totalMarksT+Float.parseFloat(detailTo.getTheoryMarks());
							}else{
								totalMarksTS=detailTo.getTheoryMarks();
							}
							if(detailTo.getPracticalMarks()!=null && !StringUtils.isAlpha(detailTo.getPracticalMarks())){
								totalMarksP=totalMarksP+Float.parseFloat(detailTo.getPracticalMarks());
							}else{
								totalMarksPS=detailTo.getPracticalMarks();
							}
						}
						//new marks to
						else{
							to.setExamId(""+examId);
							to.setIsTheorySecured(false);
							to.setIsPracticalSecured(false);
							if(objects[1]!=null){
								to.setClassId(Integer.parseInt(objects[1].toString()));
							}
						}

						if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
							to.setIsTheory(true);
							to.setIsPractical(false);
						}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
							to.setIsTheory(false);
							to.setIsPractical(true);
						}else{
							to.setIsTheory(true);
							to.setIsPractical(true);
						}
						if(maxMarkMap.containsKey(examId))
							to.setMaxMarks(maxMarkMap.get(examId));
						studentMarksList.add(to);
					}//exam set

					if(totalMarksT!=0){

						if(student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getName().equalsIgnoreCase("UG")){
							if(newExamMarksEntryForm.getYear().equalsIgnoreCase("2015")){
								studentto.setTotalInternalMarksT((Math.ceil(totalMarksT))+"");
							}else{
								studentto.setTotalInternalMarksT(df.format(totalMarksT)+"");
							}


						}else{
							studentto.setTotalInternalMarksT((Math.ceil(totalMarksT))+"");
						}


					}else{
						if(totalMarksTS!=null){
							studentto.setTotalInternalMarksT(totalMarksTS);
						}else if(zeroMarks){
							studentto.setTotalInternalMarksT(totalMarksT+"");	
						}
					}

					if(totalMarksP!=0){
						if(student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getName().equalsIgnoreCase("UG")){
							if(newExamMarksEntryForm.getYear().equalsIgnoreCase("2015")){
								studentto.setTotalInternalMarksP((Math.ceil(totalMarksP))+"");
							}else{
								studentto.setTotalInternalMarksP(df.format(totalMarksP)+"");
							}


						}else{
							studentto.setTotalInternalMarksP((Math.ceil(totalMarksP))+"");
						}

					}else{
						if(totalMarksPS!=null){
							studentto.setTotalInternalMarksP(totalMarksPS);
						}else if(zeroMarks){
							studentto.setTotalInternalMarksP(totalMarksP+"");	
						}
					}

					ExamComparator e=new ExamComparator();
					e.setCompare(0);
					Collections.sort(studentMarksList, e);
					studentto.setStudentMarksList(studentMarksList);
					allstudentsList.add(studentto);
					newExamMarksEntryForm.setProgramTypeId(student.getClassSchemewise().getClasses().getCourse().getProgram().getProgramType().getId()+"");

				}//detained list
			}//empty check


		}//main student over
		Collections.sort(allstudentsList);
		return allstudentsList;
	}
	public void addAttendanceMarkToMarkTo(List<ExamStudentOverallInternalMarkDetailsBO> attmark, NewExamMarksEntryForm newExamMarksEntryForm) {
		List<StudentTO> studentList=newExamMarksEntryForm.getStudentMarksList();
		if (!studentList.isEmpty() && studentList!=null && !attmark.isEmpty() && attmark!=null) {
			for (StudentTO studentTO : studentList) {
				for (ExamStudentOverallInternalMarkDetailsBO att : attmark) {
					if (studentTO.getStudentId()==att.getStudentId()) {
						
							StudentMarksTO studentMarkTo=new StudentMarksTO();
							
							if (newExamMarksEntryForm.getSubjectType()=="1" || newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")) {
							studentMarkTo.setAttendanceMark(att.getTheoryTotalAttendenceMarks());
							}
							else{
								studentMarkTo.setAttendanceMark(att.getPracticalTotalAttendenceMarks());
							}
							studentTO.setStudentAttMarksList(studentMarkTo);
					}
				}
			}
		}
		newExamMarksEntryForm.setStudentMarksList(studentList);


	}
}