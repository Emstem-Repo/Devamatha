package com.kp.cms.helpers.exam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;

public class NewSecuredMarksEntryHelper {
	/**
	 * Singleton object of NewSecuredMarksEntryHelper
	 */
	private static volatile NewSecuredMarksEntryHelper newSecuredMarksEntryHelper = null;
	private static final Log log = LogFactory.getLog(NewSecuredMarksEntryHelper.class);
	private NewSecuredMarksEntryHelper() {
		
	}
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static NewSecuredMarksEntryHelper getInstance() {
		if (newSecuredMarksEntryHelper == null) {
			newSecuredMarksEntryHelper = new NewSecuredMarksEntryHelper();
		}
		return newSecuredMarksEntryHelper;
	}
	public String getQueryForCheckMarksEnteredThroughSecured(NewSecuredMarksEntryForm newSecuredMarksEntryForm) {
		String query="from MarksEntryDetails m" +
		" where m.subject.id=" +newSecuredMarksEntryForm.getSubjectId()+
		" and m.marksEntry.exam.id="+newSecuredMarksEntryForm.getExamId();
		if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
			query=query+" and m.marksEntry.evaluatorType="+newSecuredMarksEntryForm.getEvaluatorType();
		}
		if(newSecuredMarksEntryForm.getAnswerScriptType()!=null && !newSecuredMarksEntryForm.getAnswerScriptType().isEmpty()){
			query=query+" and m.marksEntry.answerScript="+newSecuredMarksEntryForm.getAnswerScriptType();
		}
		if(newSecuredMarksEntryForm.getSubjectType()!=null && !newSecuredMarksEntryForm.getSubjectType().isEmpty()){
			if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
				query=query+" and m.theoryMarks is not null and m.isTheorySecured=0";
			}else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
				query=query+" and m.practicalMarks is not null and m.isPracticalSecured=0";
			}else{
				query=query+" and m.theoryMarks is not null and m.practicalMarks is not null and m.isPracticalSecured=0 and m.isTheorySecured=0";
			}
		}
		return query;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForAlreadyEnteredMarks(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		String query="from MarksEntryDetails m" +
		" where m.subject.id=" +newSecuredMarksEntryForm.getSubjectId()+
		" and m.marksEntry.exam.id="+newSecuredMarksEntryForm.getExamId();
	if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
		query=query+" and m.marksEntry.evaluatorType="+newSecuredMarksEntryForm.getEvaluatorType();
	}
	if(newSecuredMarksEntryForm.getAnswerScriptType()!=null && !newSecuredMarksEntryForm.getAnswerScriptType().isEmpty()){
		query=query+" and m.marksEntry.answerScript="+newSecuredMarksEntryForm.getAnswerScriptType();
	}
	if(newSecuredMarksEntryForm.getSubjectType()!=null && !newSecuredMarksEntryForm.getSubjectType().isEmpty()){
		if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
			query=query+" and m.theoryMarks is not null ";
		}else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
			query=query+" and m.practicalMarks is not null ";
		}else{
			query=query+" and m.theoryMarks is not null and m.practicalMarks is not null ";
		}
	}
	return query;
	}
	/**
	 * @param marksList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, MarksDetailsTO> convertBoDataToMarksMap(List marksList) throws Exception{
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
			marksMap.put(marksEntryDetails.getMarksEntry().getStudent().getId(),to);
		}
		return marksMap;
	}
	public String getQueryForCurrentClassStudents(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		String query="select s,s.classSchemewise.classes.id,s.classSchemewise.classes.course.id,s.classSchemewise.classes.termNumber,s.classSchemewise.curriculumSchemeDuration.academicYear from Student s" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" where s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+newSecuredMarksEntryForm.getExamId()+") " +
		" and subGroup.subject.id=" +newSecuredMarksEntryForm.getSubjectId();
		return query;
	}
	/**
	 * @param studentList
	 * @param currentStudentList
	 * @param existsMarks
	 * @param listOfDetainedStudents
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentMarksTO> convertBotoTOListForCurrentStudents(List<StudentMarksTO> studentList, List currentStudentList,Map<Integer, MarksDetailsTO> existsMarks,List<Integer> listOfDetainedStudents,NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{

		Iterator<Object[]> itr=currentStudentList.iterator();
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
						to.setClassId(detailTo.getClassId());
						to.setMarksId(detailTo.getMarksId());
					}else{
						if(objects[1]!=null){
							to.setClassId(Integer.parseInt(objects[1].toString()));
						}
					}
					if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
						to.setIsTheory(true);
						to.setIsPractical(false);
					}else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
						to.setIsTheory(false);
						to.setIsPractical(true);
					}else{
						to.setIsTheory(true);
						to.setIsPractical(true);
					}
					if(objects[2]!=null)
						to.setCourseId(Integer.parseInt(objects[2].toString()));
					if(objects[3]!=null)
						to.setSchemeNo(Integer.parseInt(objects[3].toString()));
					if(objects[4]!=null)
						to.setYear(Integer.parseInt(objects[4].toString()));
					studentList.add(to);
				}
			}
		}
		return studentList;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousClassStudents(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		String query="select s,classHis.classes.id,classHis.classes.course.id,classHis.classes.termNumber,cd.academicYear from Student s" +
		" join s.studentPreviousClassesHistory classHis" +
		" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
		" join s.studentSubjectGroupHistory subjHist" +
		" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
		" where s.admAppln.isCancelled=0 and subSet.isActive=1 " +
		" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
		" from ExamDefinitionBO e where e.id="+newSecuredMarksEntryForm.getExamId()+")" +
		" and subSet.subject.id="+newSecuredMarksEntryForm.getSubjectId()+" and classHis.schemeNo=subjHist.schemeNo";
		return query;
	}
	/**
	 * @param studentList
	 * @param previousStudentList
	 * @param existsMarks
	 * @param listOfDetainedStudents
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentMarksTO> convertBotoTOListForPreviousClassStudents(List<StudentMarksTO> studentList, List previousStudentList,Map<Integer, MarksDetailsTO> existsMarks,List<Integer> listOfDetainedStudents,NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		Iterator<Object[]> itr=previousStudentList.iterator();
		while (itr.hasNext()) {
			Object[] objects = (Object[]) itr.next();
			if(objects[0]!=null){
				Student student=(Student)objects[0];
//				if(student.getId()==18714){// remove this
				if(student.getClassSchemewise()!=null){
				int currentClassSchemeNo=student.getClassSchemewise().getClasses().getTermNumber()-1;
				int previousSchemeNo=0;
				if(objects[3]!=null){
					previousSchemeNo=Integer.parseInt(objects[3].toString());
				}
				if(currentClassSchemeNo==previousSchemeNo){
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
						to.setClassId(detailTo.getClassId());
						to.setMarksId(detailTo.getMarksId());
					}else{
						if(objects[1]!=null){
							to.setClassId(Integer.parseInt(objects[1].toString()));
						}
					}
					if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
						to.setIsTheory(true);
						to.setIsPractical(false);
					}else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
						to.setIsTheory(false);
						to.setIsPractical(true);
					}else{
						to.setIsTheory(true);
						to.setIsPractical(true);
					}
					if(objects[2]!=null)
						to.setCourseId(Integer.parseInt(objects[2].toString()));
					if(objects[3]!=null)
						to.setSchemeNo(Integer.parseInt(objects[3].toString()));
					if(objects[4]!=null)
						to.setYear(Integer.parseInt(objects[4].toString()));
					studentList.add(to);
//				}//remove this
				}
				}
				}
			}
		}
		
		return studentList;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryCurrentClassStudents(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		String query="select s,c.id,c.course.id,c.termNumber,cd.academicYear from Student s" +
		" join s.classSchemewise.classes c" +
		" join s.classSchemewise.curriculumSchemeDuration cd" +
		" join s.admAppln.applicantSubjectGroups appSub" +
		" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
		" join s.studentSupplementaryImprovements supImp with supImp.subject.id="+newSecuredMarksEntryForm.getSubjectId()+
		" and supImp.examDefinition.id="+newSecuredMarksEntryForm.getExamId()+
		" where s.admAppln.isCancelled=0 and subGroup.isActive=1 " +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
		" from ExamDefinitionBO e where e.id="+newSecuredMarksEntryForm.getExamId()+")" +
		" and subGroup.subject.id="+newSecuredMarksEntryForm.getSubjectId()+
		" and cd.semesterYearNo="+newSecuredMarksEntryForm.getSchemeNo();
		if(newSecuredMarksEntryForm.getSubjectType()!=null && !newSecuredMarksEntryForm.getSubjectType().isEmpty()){
			if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
				query=query+" and supImp.isAppearedTheory=1";
			}else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
				query=query+" and supImp.isAppearedPractical=1";
			}else{
				query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1";
			}
		}
		return query;
	}
	/**
	 * @param studentList
	 * @param currentStudentList
	 * @param existsMarks
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentMarksTO> convertBotoTOListForSupplementaryCurrentStudents(List<StudentMarksTO> studentList, List currentStudentList,Map<Integer, MarksDetailsTO> existsMarks,NewSecuredMarksEntryForm newSecuredMarksEntryForm,Map<Integer, String> oldRegMap) throws Exception{
		Iterator<Object[]> itr=currentStudentList.iterator();
		while (itr.hasNext()) {
			Object[] objects = (Object[]) itr.next();
			if(objects[0]!=null){
				Student student=(Student)objects[0];
				StudentMarksTO to=new StudentMarksTO();
				to.setStudentId(student.getId());
				to.setName(student.getAdmAppln().getPersonalData().getFirstName());
				if(oldRegMap.containsKey(student.getId())){
					to.setRegisterNo(oldRegMap.get(student.getId()));
				}else{
					to.setRegisterNo(student.getRegisterNo());
				}
				if(existsMarks.containsKey(student.getId())){
					MarksDetailsTO detailTo=existsMarks.get(student.getId());
					to.setId(detailTo.getId());
					to.setPracticalMarks(detailTo.getPracticalMarks());
					to.setTheoryMarks(detailTo.getTheoryMarks());
					to.setClassId(detailTo.getClassId());
					to.setMarksId(detailTo.getMarksId());
				}else{
					if(objects[1]!=null){
						to.setClassId(Integer.parseInt(objects[1].toString()));
					}
				}
				if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
					to.setIsTheory(true);
					to.setIsPractical(false);
				}else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
					to.setIsTheory(false);
					to.setIsPractical(true);
				}else{
					to.setIsTheory(true);
					to.setIsPractical(true);
				}
				if(objects[2]!=null)
					to.setCourseId(Integer.parseInt(objects[2].toString()));
				if(objects[3]!=null)
					to.setSchemeNo(Integer.parseInt(objects[3].toString()));
				if(objects[4]!=null)
					to.setYear(Integer.parseInt(objects[4].toString()));
				studentList.add(to);
			}
		}
		return studentList;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryPreviousClassStudents(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		String query="select s,classHis.classes.id,classHis.classes.course.id,classHis.classes.termNumber,cd.academicYear from Student s" +
			" join s.studentPreviousClassesHistory classHis" +
			" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
			" join s.studentSubjectGroupHistory subjHist" +
			" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
			" join s.studentSupplementaryImprovements supImp with supImp.subject.id=" +newSecuredMarksEntryForm.getSubjectId()+
			" and supImp.examDefinition.id=" +newSecuredMarksEntryForm.getExamId()+
			" where s.admAppln.isCancelled=0 and subSet.isActive=1" +
			" and classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
			" from ExamDefinitionBO e" +
			" where e.id="+newSecuredMarksEntryForm.getExamId()+") and classSchemewise.curriculumSchemeDuration.semesterYearNo="+newSecuredMarksEntryForm.getSchemeNo()+
			" and subSet.subject.id="+newSecuredMarksEntryForm.getSubjectId();
		if(newSecuredMarksEntryForm.getSubjectType()!=null && !newSecuredMarksEntryForm.getSubjectType().isEmpty()){
			if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
				query=query+" and supImp.isAppearedTheory=1";
			}else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
				query=query+" and supImp.isAppearedPractical=1";
			}else{
				query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1";
			}
		}
		return query;
	}
	/**
	 * @param studentList
	 * @param previousStudentList
	 * @param existsMarks
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentMarksTO> convertBotoTOListForSupplementaryPreviousClassStudents(List<StudentMarksTO> studentList, List previousStudentList,Map<Integer, MarksDetailsTO> existsMarks,NewSecuredMarksEntryForm newSecuredMarksEntryForm,Map<Integer, String> oldRegMap) throws Exception{
		Iterator<Object[]> itr=previousStudentList.iterator();
		while (itr.hasNext()) {
			Object[] objects = (Object[]) itr.next();
			if(objects[0]!=null){
				Student student=(Student)objects[0];
				StudentMarksTO to=new StudentMarksTO();
				to.setStudentId(student.getId());
				to.setName(student.getAdmAppln().getPersonalData().getFirstName());
				if(oldRegMap.containsKey(student.getId())){
					to.setRegisterNo(oldRegMap.get(student.getId()));
				}else{
					to.setRegisterNo(student.getRegisterNo());
				}
				if(existsMarks.containsKey(student.getId())){
					MarksDetailsTO detailTo=existsMarks.get(student.getId());
					to.setId(detailTo.getId());
					to.setPracticalMarks(detailTo.getPracticalMarks());
					to.setTheoryMarks(detailTo.getTheoryMarks());
					to.setClassId(detailTo.getClassId());
					to.setMarksId(detailTo.getMarksId());
				}else{
					if(objects[1]!=null){
						to.setClassId(Integer.parseInt(objects[1].toString()));
					}
				}
				if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
					to.setIsTheory(true);
					to.setIsPractical(false);
				}else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
					to.setIsTheory(false);
					to.setIsPractical(true);
				}else{
					to.setIsTheory(true);
					to.setIsPractical(true);
				}
				if(objects[2]!=null)
					to.setCourseId(Integer.parseInt(objects[2].toString()));
				if(objects[3]!=null)
					to.setSchemeNo(Integer.parseInt(objects[3].toString()));
				if(objects[4]!=null)
					to.setYear(Integer.parseInt(objects[4].toString()));
				studentList.add(to);
			}
		}
		return studentList;
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForEvaluatorEnteredMarks(NewSecuredMarksEntryForm newSecuredMarksEntryForm) throws Exception{
		String query="from MarksEntryDetails m" +
			" where m.subject.id=" +newSecuredMarksEntryForm.getSubjectId()+
			" and m.marksEntry.exam.id="+newSecuredMarksEntryForm.getExamId();
		if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
			query=query+" and m.marksEntry.evaluatorType !="+newSecuredMarksEntryForm.getEvaluatorType();
		}
		if(newSecuredMarksEntryForm.getAnswerScriptType()!=null && !newSecuredMarksEntryForm.getAnswerScriptType().isEmpty()){
			query=query+" and m.marksEntry.answerScript !="+newSecuredMarksEntryForm.getAnswerScriptType();
		}
		if(newSecuredMarksEntryForm.getSubjectType()!=null && !newSecuredMarksEntryForm.getSubjectType().isEmpty()){
			if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
				query=query+" and m.theoryMarks is not null and m.isTheorySecured=1";
			}else if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
				query=query+" and m.practicalMarks is not null and m.isPracticalSecured=1 ";
			}else{
				query=query+" and m.theoryMarks is not null and m.practicalMarks is not null and m.isTheorySecured=1 and m.isPracticalSecured=1";
			}
		}
		return query;
	}
	/**
	 * @param evalMarksList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Map<Integer, String>> convertBotoEvaMap(List evalMarksList,String subjectType) throws Exception{
		Map<Integer, Map<Integer, String>> map=new HashMap<Integer, Map<Integer,String>>();
		Iterator<MarksEntryDetails> itr=evalMarksList.iterator();
		while (itr.hasNext()) {
			MarksEntryDetails marksEntryDetails = (MarksEntryDetails) itr.next();
			if(marksEntryDetails.getMarksEntry().getEvaluatorType()!=null){
				if(subjectType.equalsIgnoreCase("t") && marksEntryDetails.getTheoryMarks()!=null){
					if(map.containsKey(marksEntryDetails.getMarksEntry().getStudent().getId())){
						Map<Integer,String> m=map.remove(marksEntryDetails.getMarksEntry().getStudent().getId());
						m.put(marksEntryDetails.getMarksEntry().getEvaluatorType(),marksEntryDetails.getTheoryMarks());
						map.put(marksEntryDetails.getMarksEntry().getStudent().getId(), m);
					}else{
						Map<Integer,String> m=new HashMap<Integer, String>();
						m.put(marksEntryDetails.getMarksEntry().getEvaluatorType(),marksEntryDetails.getTheoryMarks());
						map.put(marksEntryDetails.getMarksEntry().getStudent().getId(), m);
					}
				}else if(subjectType.equalsIgnoreCase("p") && marksEntryDetails.getPracticalMarks()!=null){
					if(map.containsKey(marksEntryDetails.getMarksEntry().getStudent().getId())){
						Map<Integer,String> m=map.remove(marksEntryDetails.getMarksEntry().getStudent().getId());
						m.put(marksEntryDetails.getMarksEntry().getEvaluatorType(),marksEntryDetails.getPracticalMarks());
						map.put(marksEntryDetails.getMarksEntry().getStudent().getId(), m);
					}else{
						Map<Integer,String> m=new HashMap<Integer, String>();
						m.put(marksEntryDetails.getMarksEntry().getEvaluatorType(),marksEntryDetails.getPracticalMarks());
						map.put(marksEntryDetails.getMarksEntry().getStudent().getId(), m);
					}
				} 
			}
		}
		return map;
	}
}
