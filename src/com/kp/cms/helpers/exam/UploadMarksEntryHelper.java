package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailUtilBo;
import com.kp.cms.bo.exam.ExamMarksEntryUtilBO;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.UploadMarksEntryForm;
import com.kp.cms.handlers.exam.UploadMarksEntryHandler;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.to.exam.UploadMarksEntryTO;
import com.kp.cms.utilities.CommonUtil;


public class UploadMarksEntryHelper 
{
	private static volatile UploadMarksEntryHelper uploadMarksEntryHelper = null;
	private UploadMarksEntryHelper() {
		
	}
	
	public static UploadMarksEntryHelper getInstance() 
	{
		if (uploadMarksEntryHelper == null) {
			uploadMarksEntryHelper = new UploadMarksEntryHelper();
		}
		return uploadMarksEntryHelper;
	}

	public List<ExamMarksEntryUtilBO> convertTOToBO(String userId,List<UploadMarksEntryTO> evaluatorList) throws Exception
	{
		List<ExamMarksEntryUtilBO> boList=new ArrayList<ExamMarksEntryUtilBO>();
		UploadMarksEntryHandler handler=UploadMarksEntryHandler.getInstance();
		for(UploadMarksEntryTO entryTO:evaluatorList)
		{
			Integer marksId=0;
			Integer subjectId=0;
			ExamMarksEntryUtilBO entryUtilBO=null;
			entryUtilBO=handler.getMarkEntryDetails(entryTO.getExamId(),entryTO.getStudentId(),entryTO.getEvaluatorId());
			Set<ExamMarksEntryDetailUtilBo> detailUtilBos=null;
			if(entryUtilBO==null)
			{	
				entryUtilBO=new ExamMarksEntryUtilBO();
				ExamDefinitionBO definitionBO=new ExamDefinitionBO();
				definitionBO.setId(Integer.parseInt(entryTO.getExamId()));
				entryUtilBO.setExamDefinitionBO(definitionBO);
				StudentUtilBO studentUtilBO=new StudentUtilBO();
				studentUtilBO.setId(Integer.parseInt(entryTO.getStudentId()));
				entryUtilBO.setStudentUtilBO(studentUtilBO);
				entryUtilBO.setCreatedBy(userId);
				entryUtilBO.setCreatedDate(new Date());
				entryUtilBO.setIsSecured(0);
				entryUtilBO.setEvaluatorTypeId(entryTO.getEvaluatorId());
				detailUtilBos=new HashSet<ExamMarksEntryDetailUtilBo>();
				
			}	
			else
			{
				detailUtilBos=entryUtilBO.getExamMarksEntryDetailsBOset();
				marksId=entryUtilBO.getId();
			}
			
			
			//mark details start
			ExamMarksEntryDetailUtilBo detailUtilBo=new ExamMarksEntryDetailUtilBo();
			
			if(entryTO.getSubjectType().equalsIgnoreCase("T"))
			{
				detailUtilBo.setTheoryMarks(entryTO.getMarks());
				detailUtilBo.setPreviousEvaluatorTheoryMarks(entryTO.getMarks());
			}
			else
			{
				detailUtilBo.setPracticalMarks(entryTO.getMarks());
				detailUtilBo.setPreviousEvaluatorPracticalMarks(entryTO.getMarks());
			}
			
			SubjectUtilBO subjectUtilBO=new SubjectUtilBO();
			subjectUtilBO.setId(Integer.parseInt(entryTO.getSubjectId()));
			subjectId=subjectUtilBO.getId();
			detailUtilBo.setSubjectUtilBO(subjectUtilBO);
			detailUtilBo.setCreatedBy(userId);
			detailUtilBo.setCreatedDate(new Date());
			if(!handler.isDuplicateForMarkDetails(marksId,subjectId))
				detailUtilBos.add(detailUtilBo);
			
			entryUtilBO.setExamMarksEntryDetailsBOset(detailUtilBos);
			// ends here
			
			boList.add(entryUtilBO);
		}
		return boList;
	}

	/**
	 * @param userId
	 * @param evaluator1List
	 * @return
	 * @throws Exception
	 */
	public List<StudentMarksTO> convertTotoFianlData(List<UploadMarksEntryTO> evList,Map<String,Integer> classIdMap,String subjectType) throws Exception {
		List<StudentMarksTO> finalList=new ArrayList<StudentMarksTO>();
		Iterator<UploadMarksEntryTO> itr=evList.iterator();
		while (itr.hasNext()) {
			UploadMarksEntryTO to = (UploadMarksEntryTO) itr.next();
			if(to.getStudentId()!=null && classIdMap.containsKey(to.getStudentId())){
				StudentMarksTO sto=new StudentMarksTO();
				sto.setStudentId(Integer.parseInt(to.getStudentId()));
				sto.setClassId(classIdMap.get(to.getStudentId()));
				String Marks=to.getMarks();
				if(Marks!=null && CommonUtil.isValidDecimal(Marks)){
					if(Marks.length()==1){
						Marks="0"+Marks;
					}
				}
				if (subjectType.equalsIgnoreCase("t")){
					sto.setIsTheory(true);
					sto.setTheoryMarks(Marks);
				}else if (subjectType.equalsIgnoreCase("p")){
					sto.setIsPractical(true);
					sto.setPracticalMarks(Marks);
				}
				if(to.getEvaluatorId()!=null)
				sto.setEvaId(String.valueOf(to.getEvaluatorId()));
				finalList.add(sto);
			}
		}
		return finalList;
	}
	
	public String getQueryForAlreadyEnteredMarks(UploadMarksEntryForm uploadMarksEntryForm) throws Exception {
		String query="from MarksEntryDetails m" +
			" where m.subject.id=" +uploadMarksEntryForm.getSubjectId()+
//			" and m.marksEntry.classes.course.id= " +uploadMarksEntryForm.getCourseId()+
			" and m.marksEntry.exam.id="+uploadMarksEntryForm.getExamId();
		if(uploadMarksEntryForm.getEvaluatorType()!=null && !uploadMarksEntryForm.getEvaluatorType().isEmpty()){
			query=query+" and m.marksEntry.evaluatorType="+uploadMarksEntryForm.getEvaluatorType();
		}
		if(uploadMarksEntryForm.getAnswerScriptType()!=null && !uploadMarksEntryForm.getAnswerScriptType().isEmpty()){
			query=query+" and m.marksEntry.answerScript="+uploadMarksEntryForm.getAnswerScriptType();
		}
		if(uploadMarksEntryForm.getSubjectType()!=null && !uploadMarksEntryForm.getSubjectType().isEmpty()){
			if(uploadMarksEntryForm.getSubjectType().equals("1")){
				query=query+" and m.theoryMarks is not null ";
			}else if(uploadMarksEntryForm.getSubjectType().equals("0")){
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
	 * @param uploadMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCurrentClassStudents(UploadMarksEntryForm uploadMarksEntryForm) throws Exception {
		String query="select s,s.classSchemewise.classes.id from Student s" +
				" join s.admAppln.applicantSubjectGroups appSub" +
				" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
				" where s.admAppln.isCancelled=0 and subGroup.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
				" from ExamDefinitionBO e where e.id="+uploadMarksEntryForm.getExamId()+") " +
				" and subGroup.subject.id=" +uploadMarksEntryForm.getSubjectId()+
				" and s.admAppln.courseBySelectedCourseId.id=" +uploadMarksEntryForm.getCourseId()+
				" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+uploadMarksEntryForm.getSchemeId();
		/*if(uploadMarksEntryForm.getSection()!=null && !uploadMarksEntryForm.getSection().isEmpty() && !uploadMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and s.classSchemewise.classes.id="+uploadMarksEntryForm.getcgetSection();
		}*/
		return query;
	}
	/**
	 * @param currentStudentList
	 * @param existsMarks
	 * @return
	 */
	public Set<StudentMarksTO> convertBotoTOListForCurrentStudents(Set<StudentMarksTO> studentList,List currentStudentList, Map<Integer, MarksDetailsTO> existsMarks,List<Integer> listOfDetainedStudents,UploadMarksEntryForm uploadMarksEntryForm/*,Map<Integer,String> oldRegNo*/) throws Exception {
		Iterator<Object[]> itr=currentStudentList.iterator();
		boolean disable=true;
		if(!uploadMarksEntryForm.isInternal() && !uploadMarksEntryForm.isRegular())
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
							to.setIsTheorySecured(true);
						else
							to.setIsTheorySecured(false);
						if(detailTo.isPracticalSecured() || disable)
							to.setIsPracticalSecured(true);
						else
							to.setIsPracticalSecured(false);
					}else{
						if(objects[1]!=null){
							to.setClassId(Integer.parseInt(objects[1].toString()));
						}
						to.setIsTheorySecured(false);
						to.setIsPracticalSecured(false);
					}
					if(uploadMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
						to.setIsTheory(true);
						to.setIsPractical(false);
					}else if(uploadMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
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
		
		return studentList;
	}
	/**
	 * @param uploadMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForPreviousClassStudents(UploadMarksEntryForm uploadMarksEntryForm) throws Exception{
		String query="select s,classHis.classes.id from Student s" +
				" join s.studentPreviousClassesHistory classHis" +
				" join classHis.classes.classSchemewises classSchemewise" +
				" join s.studentSubjectGroupHistory subjHist" +
				" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
				" where s.admAppln.isCancelled=0  and subSet.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
				" from ExamDefinitionBO e where e.id="+uploadMarksEntryForm.getExamId()+")" +
				" and classSchemewise.curriculumSchemeDuration.semesterYearNo=" +uploadMarksEntryForm.getSchemeId()+
				" and classSchemewise.classes.course.id="+uploadMarksEntryForm.getCourseId()+
				" and subSet.subject.id="+uploadMarksEntryForm.getSubjectId();
		/*if(uploadMarksEntryForm.getSection()!=null && !uploadMarksEntryForm.getSection().isEmpty() && !uploadMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and classSchemewise.classes.id="+uploadMarksEntryForm.getSection();
		}*/
		query=query+" and subjHist.schemeNo="+uploadMarksEntryForm.getSchemeId();
		return query;
	}
	/**
	 * @param studentList
	 * @param previousStudentList
	 * @param existsMarks
	 * @return
	 */
	public Set<StudentMarksTO> convertBotoTOListForPreviousClassStudents(Set<StudentMarksTO> studentList, List previousStudentList,Map<Integer, MarksDetailsTO> existsMarks,List<Integer> listOfDetainedStudents,UploadMarksEntryForm uploadMarksEntryForm/*,Map<Integer,String> oldRegNo*/) throws Exception {
		boolean disable=true;
		if(!uploadMarksEntryForm.isInternal() && !uploadMarksEntryForm.isRegular())
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
					/*if(oldRegNo.containsKey(student.getId())){
						to.setRegisterNo(oldRegNo.get(student.getId()));
					}else{*/
						to.setRegisterNo(student.getRegisterNo());
//					}
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
							to.setIsTheorySecured(true);
						else
							to.setIsTheorySecured(false);
						if(detailTo.isPracticalSecured() || disable)
							to.setIsPracticalSecured(true);
						else
							to.setIsPracticalSecured(false);
					}else{
						to.setIsTheorySecured(false);
						to.setIsPracticalSecured(false);
						if(objects[1]!=null){
							to.setClassId(Integer.parseInt(objects[1].toString()));
						}
					}
					if(uploadMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
						to.setIsTheory(true);
						to.setIsPractical(false);
					}else if(uploadMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
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
		
		return studentList;
	}
	/**
	 * @param uploadMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryCurrentClassStudents(UploadMarksEntryForm uploadMarksEntryForm) throws Exception {
		String query="select s,c.id from Student s" +
				" join s.classSchemewise.classes c" +
				" join s.admAppln.applicantSubjectGroups appSub" +
				" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
				" join s.studentSupplementaryImprovements supImp with supImp.subject.id="+uploadMarksEntryForm.getSubjectId()+
				" and supImp.examDefinition.id="+uploadMarksEntryForm.getExamId()+
				" where s.admAppln.isCancelled=0 and subGroup.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and s.classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
				" from ExamDefinitionBO e where e.id="+uploadMarksEntryForm.getExamId()+")" +
				" and s.admAppln.courseBySelectedCourseId.id="+uploadMarksEntryForm.getCourseId()+
				" and supImp.classes.course.id="+uploadMarksEntryForm.getCourseId()+
				" and subGroup.subject.id="+uploadMarksEntryForm.getSubjectId()+
				" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+uploadMarksEntryForm.getSchemeId()+" and subGroup.isActive=1 ";
		if(uploadMarksEntryForm.getSubjectType()!=null && !uploadMarksEntryForm.getSubjectType().isEmpty()){
			if(uploadMarksEntryForm.getSubjectType().equals("1")){
				query=query+" and supImp.isAppearedTheory=1";
			}else if(uploadMarksEntryForm.getSubjectType().equals("0")){
				query=query+" and supImp.isAppearedPractical=1";
			}else{
				query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1";
			}
		}
		/*if(uploadMarksEntryForm.getSection()!=null && !uploadMarksEntryForm.getSection().isEmpty() && !uploadMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and s.classSchemewise.classes.id="+uploadMarksEntryForm.getSection();
		}*/
		return query;
	}
	/**
	 * @param studentList
	 * @param currentStudentList
	 * @param existsMarks
	 * @return
	 */
	public Set<StudentMarksTO> convertBotoTOListForSupplementaryCurrentStudents(Set<StudentMarksTO> studentList, List currentStudentList,Map<Integer, MarksDetailsTO> existsMarks,UploadMarksEntryForm uploadMarksEntryForm,Map<Integer,String> oldRegNo) throws Exception {
		boolean disable=true;
		if(!uploadMarksEntryForm.isInternal() && !uploadMarksEntryForm.isRegular())
			disable=false;
		Iterator<Object[]> itr=currentStudentList.iterator();
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
						to.setIsTheorySecured(true);
					else
						to.setIsTheorySecured(false);
					if(detailTo.isPracticalSecured() || disable)
						to.setIsPracticalSecured(true);
					else
						to.setIsPracticalSecured(false);
				}else{
					if(objects[1]!=null){
						to.setClassId(Integer.parseInt(objects[1].toString()));
					}
					to.setIsTheorySecured(false);
					to.setIsPracticalSecured(false);
				}
				if(uploadMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
					to.setIsTheory(true);
					to.setIsPractical(false);
				}else if(uploadMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
					to.setIsTheory(false);
					to.setIsPractical(true);
				}else{
					to.setIsTheory(true);
					to.setIsPractical(true);
				}
				studentList.add(to);
			}
		}
		return studentList;
	}
	/**
	 * @param uploadMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForSupplementaryPreviousClassStudents(UploadMarksEntryForm uploadMarksEntryForm) throws Exception{
		String query="select s,classHis.classes.id from Student s" +
				" join s.studentPreviousClassesHistory classHis" +
				" join classHis.classes.classSchemewises classSchemewise" +
				" join s.studentSubjectGroupHistory subjHist" +
				" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
				" join s.studentSupplementaryImprovements supImp with supImp.subject.id=" +uploadMarksEntryForm.getSubjectId()+
				" and supImp.examDefinition.id=" +uploadMarksEntryForm.getExamId()+
				" where s.admAppln.isCancelled=0  and subSet.isActive=1 and (s.isHide=0 or s.isHide is null)" +
				" and classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
				" from ExamDefinitionBO e" +
				" where e.id="+uploadMarksEntryForm.getExamId()+") and classSchemewise.curriculumSchemeDuration.semesterYearNo="+uploadMarksEntryForm.getSchemeId()+
				" and classSchemewise.classes.course.id="+uploadMarksEntryForm.getCourseId()+
				" and subSet.subject.id="+uploadMarksEntryForm.getSubjectId()+
				" and supImp.classes.course.id=" +uploadMarksEntryForm.getCourseId();
		if(uploadMarksEntryForm.getSubjectType()!=null && !uploadMarksEntryForm.getSubjectType().isEmpty()){
			if(uploadMarksEntryForm.getSubjectType().equals("1")){
				query=query+" and supImp.isAppearedTheory=1";
			}else if(uploadMarksEntryForm.getSubjectType().equals("0")){
				query=query+" and supImp.isAppearedPractical=1";
			}else{
				query=query+" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1";
			}
		}
		/*if(uploadMarksEntryForm.getSection()!=null && !uploadMarksEntryForm.getSection().isEmpty() && !uploadMarksEntryForm.getSection().equalsIgnoreCase("0")){
			query=query+" and classSchemewise.classes.id="+uploadMarksEntryForm.getSection();
		}*/
		return query;
	}
	/**
	 * @param studentList
	 * @param previousStudentList
	 * @param existsMarks
	 * @return
	 */
	public Set<StudentMarksTO> convertBotoTOListForSupplementaryPreviousClassStudents(Set<StudentMarksTO> studentList, List previousStudentList,Map<Integer, MarksDetailsTO> existsMarks,UploadMarksEntryForm uploadMarksEntryForm,Map<Integer,String> oldRegNo) throws Exception{
		boolean disable=true;
		if(!uploadMarksEntryForm.isInternal() && !uploadMarksEntryForm.isRegular())
			disable=false;
		Iterator<Object[]> itr=previousStudentList.iterator();
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
						to.setIsTheorySecured(true);
					else
						to.setIsTheorySecured(false);
					if(detailTo.isPracticalSecured() || disable)
						to.setIsPracticalSecured(true);
					else
						to.setIsPracticalSecured(false);
				}else{
					if(objects[1]!=null){
						to.setClassId(Integer.parseInt(objects[1].toString()));
					}
					to.setIsTheorySecured(false);
					to.setIsPracticalSecured(false);
				}
				if(uploadMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
					to.setIsTheory(true);
					to.setIsPractical(false);
				}else if(uploadMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
					to.setIsTheory(false);
					to.setIsPractical(true);
				}else{
					to.setIsTheory(true);
					to.setIsPractical(true);
				}
				studentList.add(to);
			}
		}
		return studentList;
	}
	/**
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public String getQueryForOldRegisterNos(String schemeNo) throws Exception{
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
	
	
	
	
}
