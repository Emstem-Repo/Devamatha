package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.bo.exam.OpenInternalMark;
import com.kp.cms.forms.exam.InternalMarksEntryForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.exam.InternalMarksEntryTO;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.InternalMarksEntryImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


@SuppressWarnings("unchecked")
public class InternalMarksEntryHelper {
	InternalMarksEntryImpl impl = InternalMarksEntryImpl.getInstance();
	/**
	 * @param examList
	 * @param objform 
	 * @param subjectType 
	 * @param opendexamIds 
	 * @return
	 * @throws Exception
	 */
	public void convertBOtoTO(List<Object[]> examList, InternalMarksEntryForm objform, String subjectType, List<OpenInternalExamForClasses> opendexamIds) throws Exception{
		if(examList != null && !examList.isEmpty()){
			
			List<InternalMarksEntryTO> theoryList = new ArrayList<InternalMarksEntryTO>();
			List<InternalMarksEntryTO> practicalList = new ArrayList<InternalMarksEntryTO>();
			INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
			Map<Integer,Map<String, Map<Integer, InternalMarksEntryTO>>> examMap = new HashMap<Integer, Map<String,Map<Integer,InternalMarksEntryTO>>>();
			Map<String, Map<Integer, InternalMarksEntryTO>> classMap = null;
			Map<Integer, InternalMarksEntryTO> subjectMap = null;
			InternalMarksEntryTO oldTO = null;
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			Iterator<Object[]> iterator = examList.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				InternalMarksEntryTO to = new InternalMarksEntryTO();
				
				if(objects[0] != null && !objects[0].toString().isEmpty()){
					to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(objects[0].toString()),"ExamDefinitionBO",true,"name"));
				}
				if(objects[2] != null && objects[2].toString() != null){
					to.setClassName(objects[2].toString());
				}
				if(objects[4] != null && objects[4].toString() != null){
					to.setSubjectName(objects[4].toString());
				}
				if(objects[5] != null){
					to.setSubjectCode(objects[5].toString());
				}
				if(objects[1] != null && !objects[1].toString().isEmpty()){
					to.setClassId(Integer.parseInt(objects[1].toString()));
				}
				if(objects[0] != null && !objects[0].toString().isEmpty()){
					to.setExamId(Integer.parseInt(objects[0].toString()));
				}
				if(objects[3] != null && !objects[3].toString().isEmpty()){
					to.setSubId(Integer.parseInt(objects[3].toString()));
				}
				if(objects[7] != null){
					to.setSubjectType(objects[7].toString());
				}
				if(objects[8] != null){
					to.setCourseId(objects[8].toString());
				}
				if(objects[9] != null){
					to.setSchemeNo(objects[9].toString());
				}
				if(objects[10] != null && objects[10].toString() != null && !objects[10].toString().trim().isEmpty()){
					to.setProgramTypeId(Integer.parseInt(objects[10].toString()));
				}
				if(objects[11] != null && (objects[11].toString().equalsIgnoreCase("T") || objects[11].toString().equalsIgnoreCase("B"))){
					to.setTheoryOpen(true);
				}else{
					to.setTheoryOpen(false);
				}
				if(objects[11] != null && (objects[11].toString().equalsIgnoreCase("P") || objects[11].toString().equalsIgnoreCase("B"))){
					to.setPracticalOpen(true);
				}else{
					to.setPracticalOpen(false);
				}
				if(objects[12] != null && objects[12].toString() != null && !objects[12].toString().trim().isEmpty()){
					to.setEndDate(objects[12].toString());
				}
				String marksQuery=getQueryForAlreadyEnteredMarks(to.getSubId(),to.getExamId(),to.getClassId(),to.getSubjectType());
				List marksList=transaction.getDataForQuery(marksQuery);
				String currentStudentQuery=getQueryForCurrentClassStudents(to.getSubId(),to.getExamId(),to.getClassId());
				List currentStudentList=transaction.getDataForQuery(currentStudentQuery);
				Map<Integer,MarksDetailsTO> existsMarks=new HashMap<Integer, MarksDetailsTO>();
				Set<StudentMarksTO> studentList=new HashSet<StudentMarksTO>();
				if(marksList!=null && !marksList.isEmpty()){
					existsMarks=convertBoDataToMarksMap(marksList);
				}
				boolean enablemarks = checkExamIsOpendOrNot(opendexamIds,to.getSubjectType(),to);
				if(currentStudentList!=null && !currentStudentList.isEmpty()){
					studentList=convertBotoTOListForCurrentStudents(studentList,currentStudentList,existsMarks,listOfDetainedStudents,to,to.getSubjectType(),enablemarks);
				}
				List<StudentMarksTO> list = new ArrayList<StudentMarksTO>(studentList);
				Collections.sort(list);
				to.setMarksList(list);
				if(list.size() <= existsMarks.size()){
					to.setStatus("Completed");
				}else{
					to.setStatus("Pending");
				}
				if(enablemarks){
					to.setFinalStatus("Edit");
				}else{
					to.setFinalStatus("View");
				}
				

				if(examMap.containsKey(Integer.parseInt(objects[0].toString()))){
					classMap = examMap.remove(Integer.parseInt(objects[0].toString()));
				}else{
					classMap = new HashMap<String, Map<Integer,InternalMarksEntryTO>>();
				}
				if(classMap.containsKey(to.getClassId())){
					subjectMap = classMap.remove(to.getClassId());
				}else{
					subjectMap = new HashMap<Integer, InternalMarksEntryTO>();
				}
				if(subjectMap.containsKey(to.getSubId())){
					oldTO = subjectMap.remove(to.getSubId());
				}else{
					oldTO = new InternalMarksEntryTO();
				}
				boolean duplicate = true;
				if(oldTO.getEndDate() != null && to.getEndDate() != null){
					duplicate = checkEndDate(to.getEndDate(),oldTO.getEndDate());
				}
				if(duplicate){
					subjectMap.put(to.getSubId(), to);
					classMap.put(String.valueOf(to.getClassId()), subjectMap);
					examMap.put(to.getExamId(), classMap);
				}else{
					subjectMap.put(to.getSubId(), oldTO);
					classMap.put(String.valueOf(to.getClassId()), subjectMap);
					examMap.put(to.getExamId(), classMap);
				}
				if(to.getSubjectType() != null && (to.getSubjectType().equalsIgnoreCase("T") || to.getSubjectType().equalsIgnoreCase("B")) && to.isTheoryOpen()){
					theoryList.add(to);
				}
				if(to.getSubjectType() != null && (to.getSubjectType().equalsIgnoreCase("P") || to.getSubjectType().equalsIgnoreCase("B")) && to.isPracticalOpen()){
					practicalList.add(to);
				}
			}
			objform.setExamMap(examMap);
			objform.setExamDeatails(theoryList);
			objform.setPracticalDeatails(practicalList);
		}
		/*else
		{
			objform.setExamMap(null);
			objform.setExamDeatails(null);
			objform.setPracticalDeatails(null);
		}*/
	}
	
	private boolean checkEndDate(String newDate, String oldDate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(CommonUtil.ConvertStringToDate(newDate));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(CommonUtil.ConvertStringToDate(oldDate));
		long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		if(daysBetween >= 0) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	private String getQueryForAlreadyEnteredMarks() {
		String query="from MarksEntryDetails m" +
					" where m.marksEntry.student.admAppln.isCancelled=0" +
					" and (m.marksEntry.student.isHide=0 or m.marksEntry.student.isHide is null) " +
					" and m.marksEntry.student.isActive=1";
	return query;
	}

	private boolean checkExamIsOpendOrNot(List<OpenInternalExamForClasses> opendexamIds,
			String subjectType, InternalMarksEntryTO to) throws Exception{
		boolean enablemarks = true;
		if(opendexamIds != null && !opendexamIds.isEmpty()){
			Iterator<OpenInternalExamForClasses> iterator = opendexamIds.iterator();
			while (iterator.hasNext()) {
				OpenInternalExamForClasses openInternalMark = (OpenInternalExamForClasses) iterator.next();
				if(subjectType.equalsIgnoreCase("T") || subjectType.equalsIgnoreCase("B")){
					if(openInternalMark.getOpenExam() != null && (openInternalMark.getOpenExam().getTheoryPractical().equalsIgnoreCase("T") || openInternalMark.getOpenExam().getTheoryPractical().equalsIgnoreCase("B"))){
						if(openInternalMark.getOpenExam().getProgramType() != null && openInternalMark.getOpenExam().getProgramType().getId() != 0){
							if(openInternalMark.getOpenExam().getExam().getId() == to.getExamId() && openInternalMark.getClasses().getId() == to.getClassId()){
								Calendar cal1 = Calendar.getInstance();
								cal1.setTime(new Date());
								Calendar cal2 = Calendar.getInstance();
								cal2.setTime(openInternalMark.getOpenExam().getEndDate());
								long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
								if(daysBetween <= 0) {
									enablemarks= false;
								}
							}
						}
					}
				}
				if(subjectType.equalsIgnoreCase("P") || subjectType.equalsIgnoreCase("B")){
					if(openInternalMark.getOpenExam() != null && (openInternalMark.getOpenExam().getTheoryPractical().equalsIgnoreCase("P") || openInternalMark.getOpenExam().getTheoryPractical().equalsIgnoreCase("B"))){
						if(openInternalMark.getOpenExam().getProgramType() != null && openInternalMark.getOpenExam().getProgramType().getId() != 0){
							if(openInternalMark.getOpenExam().getExam().getId() == to.getExamId() && openInternalMark.getClasses().getId() == to.getClassId()){
								Calendar cal1 = Calendar.getInstance();
								cal1.setTime(new Date());
								Calendar cal2 = Calendar.getInstance();
								cal2.setTime(openInternalMark.getOpenExam().getEndDate());
								long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
								if(daysBetween <= 0) {
									enablemarks= false;
								}
							}
						}
					}
				}
			}
		}
		return enablemarks;
	}
	/**
	 * @param currentStudentList
	 * @param existsMarks
	 * @param marksTo 
	 * @param opendexamIds 
	 * @return
	 */
	public Set<StudentMarksTO> convertBotoTOListForCurrentStudents(Set<StudentMarksTO> studentList,List currentStudentList, 
			Map<Integer, MarksDetailsTO> existsMarks,List<Integer> listOfDetainedStudents, InternalMarksEntryTO marksTo,
			String subjectType, boolean enablemarks) throws Exception {
		Iterator<Object[]> itr=currentStudentList.iterator();
		boolean disable=true;
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
						if(subjectType.equalsIgnoreCase("T") || subjectType.equalsIgnoreCase("B")){
//							if(detailTo.isTheorySecured() || disable)
//								to.setIsTheorySecured(true);
//							else
//								to.setIsTheorySecured(false);
							if(enablemarks){
								to.setIsTheorySecured(false);
							}else{
								to.setIsTheorySecured(true);
							}
							if(to.getTheoryMarks() != null && (to.getTheoryMarks().equalsIgnoreCase("AA")||to.getTheoryMarks().equalsIgnoreCase("NP"))){
								to.setIsTheorySecured(true);
							}
						}
						if(subjectType.equalsIgnoreCase("P") || subjectType.equalsIgnoreCase("B")){
//							if(detailTo.isPracticalSecured() || disable)
//								to.setIsPracticalSecured(true);
//							else
//								to.setIsPracticalSecured(false);
							if(enablemarks){
								to.setIsPracticalSecured(false);
							}else{
								to.setIsPracticalSecured(true);
							}
							if(to.getPracticalMarks() != null && (to.getPracticalMarks().equalsIgnoreCase("AA")||to.getPracticalMarks().equalsIgnoreCase("NP"))){
								to.setIsPracticalSecured(true);
							}
						}
					}else{
						if(objects[1]!=null){
							to.setClassId(Integer.parseInt(objects[1].toString()));
						}
						if(enablemarks){
							to.setIsTheorySecured(false);
							to.setIsPracticalSecured(false);
						}else{
							to.setIsTheorySecured(true);
							to.setIsPracticalSecured(true);
						}
					}
					if(subjectType.equalsIgnoreCase("B")){
						to.setIsTheory(true);
						to.setIsPractical(true);
					}else if(subjectType.equalsIgnoreCase("T") || subjectType.equalsIgnoreCase("B")){
						to.setIsTheory(true);
						to.setIsPractical(false);
					}else if(subjectType.equalsIgnoreCase("P") || subjectType.equalsIgnoreCase("B")){
						to.setIsTheory(false);
						to.setIsPractical(true);
					}
					studentList.add(to);
				}
			}
		}
		
		return studentList;
	}

	/**
	 * @param subId
	 * @param examId
	 * @param classId 
	 * @return
	 */
	private String getQueryForAlreadyEnteredMarks(int subId, int examId, int classId,String subjectType) {
		String query="from MarksEntryDetails m" +
		" where m.subject.id=" +subId+
		" and m.marksEntry.classes.id= " +classId+
		" and m.marksEntry.exam.id="+examId;
		if(subjectType.equalsIgnoreCase("B")){
			query=query+" and (m.theoryMarks is not null or m.practicalMarks is not null) ";
		}else if(subjectType.equalsIgnoreCase("T") || subjectType.equalsIgnoreCase("B")){
			query=query+" and m.theoryMarks is not null ";
		}else if(subjectType.equalsIgnoreCase("P") || subjectType.equalsIgnoreCase("B")){
			query=query+" and m.practicalMarks is not null ";
		}
		query = query + " and m.marksEntry.student.admAppln.isCancelled=0" +
				" and (m.marksEntry.student.isHide=0 or m.marksEntry.student.isHide is null) " +
				" and m.marksEntry.student.isActive=1";
//		query=query+" and m.theoryMarks is not null ";
//		query=query+" and m.practicalMarks is not null ";
//		query=query+" and m.theoryMarks is not null and m.practicalMarks is not null ";
	
	return query;
	}
	/**
	 * creating query for getting student for Current Class
	 * @param newExamMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForCurrentClassStudents(int subId, int examId, int classId) throws Exception {
		String query="select s,s.classSchemewise.classes.id from Student s" +
				" join s.admAppln.applicantSubjectGroups appSub" +
				" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
				" where s.admAppln.isCancelled=0 and subGroup.isActive=1 and s.isActive=1 " +
				" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
				" from ExamDefinitionBO e where e.id="+examId+") " +
				" and subGroup.subject.id=" +subId+				
				" and (s.isHide=0 or s.isHide is null) and s.classSchemewise.classes.id="+classId;
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
	 * @param examList
	 * @param objform 
	 * @param subjectType 
	 * @param opendexamIds 
	 * @return
	 * @throws Exception
	 */
	public void convertBOtoTOForHODView(List<Object[]> examList, InternalMarksEntryForm objform, String subjectType, List<OpenInternalExamForClasses> opendexamIds) throws Exception{
		if(examList != null && !examList.isEmpty()){
			
			List<InternalMarksEntryTO> theoryList = new ArrayList<InternalMarksEntryTO>();
			List<InternalMarksEntryTO> practicalList = new ArrayList<InternalMarksEntryTO>();
			INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
			Map<Integer,Map<String, Map<Integer, InternalMarksEntryTO>>> examMap = new HashMap<Integer, Map<String,Map<Integer,InternalMarksEntryTO>>>();
			Map<String, Map<Integer, InternalMarksEntryTO>> classMap = null;
			Map<Integer, InternalMarksEntryTO> subjectMap = null;
			InternalMarksEntryTO oldTO = null;
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			Iterator<Object[]> iterator = examList.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				InternalMarksEntryTO to = new InternalMarksEntryTO();
				
				if(objects[0] != null && !objects[0].toString().isEmpty()){
					to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(objects[0].toString()),"ExamDefinitionBO",true,"name"));
				}
				if(objects[2] != null && objects[2].toString() != null){
					to.setClassName(objects[2].toString());
				}
				if(objects[4] != null && objects[4].toString() != null){
					to.setSubjectName(objects[4].toString());
				}
				if(objects[5] != null){
					to.setSubjectCode(objects[5].toString());
				}
				if(objects[1] != null && !objects[1].toString().isEmpty()){
					to.setClassId(Integer.parseInt(objects[1].toString()));
				}
				if(objects[0] != null && !objects[0].toString().isEmpty()){
					to.setExamId(Integer.parseInt(objects[0].toString()));
				}
				if(objects[3] != null && !objects[3].toString().isEmpty()){
					to.setSubId(Integer.parseInt(objects[3].toString()));
				}
				if(objects[7] != null){
					to.setSubjectType(objects[7].toString());
				}
				if(objects[8] != null){
					to.setCourseId(objects[8].toString());
				}
				if(objects[9] != null){
					to.setSchemeNo(objects[9].toString());
				}
				if(objects[10] != null && objects[10].toString() != null && !objects[10].toString().trim().isEmpty()){
					to.setProgramTypeId(Integer.parseInt(objects[10].toString()));
				}
				if(objects[11] != null && (objects[11].toString().equalsIgnoreCase("T") || objects[11].toString().equalsIgnoreCase("B"))){
					to.setTheoryOpen(true);
				}else{
					to.setTheoryOpen(false);
				}
				if(objects[11] != null && (objects[11].toString().equalsIgnoreCase("P") || objects[11].toString().equalsIgnoreCase("B"))){
					to.setPracticalOpen(true);
				}else{
					to.setPracticalOpen(false);
				}
				if(objects[12] != null && objects[12].toString() != null && !objects[12].toString().trim().isEmpty()){
					to.setEndDate(objects[12].toString());
				}
				String marksQuery=getQueryForAlreadyEnteredMarks(to.getSubId(),to.getExamId(),to.getClassId(),to.getSubjectType());
				List marksList=transaction.getDataForQuery(marksQuery);
				String currentStudentQuery=getQueryForCurrentClassStudents(to.getSubId(),to.getExamId(),to.getClassId());
				List currentStudentList=transaction.getDataForQuery(currentStudentQuery);
				Map<Integer,MarksDetailsTO> existsMarks=new HashMap<Integer, MarksDetailsTO>();
				Set<StudentMarksTO> studentList=new HashSet<StudentMarksTO>();
				if(marksList!=null && !marksList.isEmpty()){
					existsMarks=convertBoDataToMarksMap(marksList);
				}
				boolean enablemarks = false;
				if(currentStudentList!=null && !currentStudentList.isEmpty()){
					studentList=convertBotoTOListForCurrentStudents(studentList,currentStudentList,existsMarks,listOfDetainedStudents,to,to.getSubjectType(),enablemarks);
				}
				List<StudentMarksTO> list = new ArrayList<StudentMarksTO>(studentList);
				Collections.sort(list);
				to.setMarksList(list);
				if(list.size() <= existsMarks.size()){
					to.setStatus("Completed");
				}else{
					to.setStatus("Pending");
				}
				to.setFinalStatus("View");
				
				if(examMap.containsKey(Integer.parseInt(objects[0].toString()))){
					classMap = examMap.remove(Integer.parseInt(objects[0].toString()));
				}else{
					classMap = new HashMap<String, Map<Integer,InternalMarksEntryTO>>();
				}
				if(classMap.containsKey(to.getClassId())){
					subjectMap = classMap.remove(to.getClassId());
				}else{
					subjectMap = new HashMap<Integer, InternalMarksEntryTO>();
				}
				if(subjectMap.containsKey(to.getSubId())){
					oldTO = subjectMap.remove(to.getSubId());
				}else{
					oldTO = new InternalMarksEntryTO();
				}
				boolean duplicate = true;
				if(oldTO.getEndDate() != null && to.getEndDate() != null){
					duplicate = checkEndDate(to.getEndDate(),oldTO.getEndDate());
				}
				if(duplicate){
					subjectMap.put(to.getSubId(), to);
					classMap.put(String.valueOf(to.getClassId()), subjectMap);
					examMap.put(to.getExamId(), classMap);
				}else{
					subjectMap.put(to.getSubId(), oldTO);
					classMap.put(String.valueOf(to.getClassId()), subjectMap);
					examMap.put(to.getExamId(), classMap);
				}
				if(to.getSubjectType() != null && (to.getSubjectType().equalsIgnoreCase("T") || to.getSubjectType().equalsIgnoreCase("B")) && to.isTheoryOpen()){
					theoryList.add(to);
				}
				if(to.getSubjectType() != null && (to.getSubjectType().equalsIgnoreCase("P") || to.getSubjectType().equalsIgnoreCase("B")) && to.isPracticalOpen()){
					practicalList.add(to);
				}
			}
			objform.setExamMap(examMap);
			objform.setExamDeatails(theoryList);
			objform.setPracticalDeatails(practicalList);
		}
	}

	/**
	 * @param examList
	 * @return
	 * @throws Exception
	 */
	public List<InternalMarksEntryTO> convertBoListTOTolist( List<Object[]> examList)throws Exception { 
		List<InternalMarksEntryTO> internalMarksToList = new ArrayList<InternalMarksEntryTO>();
		if(examList!=null && !examList.isEmpty()){
			Map<Integer,Map<Integer,InternalMarksEntryTO>> batchmainMap = new HashMap<Integer, Map<Integer,InternalMarksEntryTO>>();
			Map<Integer,InternalMarksEntryTO> batchMap;
			Iterator<Object[]> iterator = examList.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
//				if(Integer.parseInt(objects[0].toString()) == 234){
				InternalMarksEntryTO to = new InternalMarksEntryTO();
					if(objects[13]!=null && !objects[13].toString().isEmpty()){
						if(objects[7]!=null && objects[11]!=null && objects[15]!=null &&
								(((objects[7].toString().equalsIgnoreCase("T") || objects[7].toString().equalsIgnoreCase("B")) && objects[11].toString().equalsIgnoreCase("T") && Integer.valueOf(objects[15].toString())==1)
											|| ((objects[7].toString().equalsIgnoreCase("P") || objects[7].toString().equalsIgnoreCase("B")) && objects[11].toString().equalsIgnoreCase("P") && Integer.valueOf(objects[15].toString())==2))){
							if(batchmainMap.containsKey(Integer.parseInt(objects[0].toString()))){
								batchMap = batchmainMap.remove(Integer.parseInt(objects[0].toString()));
							}else{
								batchMap = new HashMap<Integer, InternalMarksEntryTO>();
							}
							if(batchMap.containsKey(Integer.parseInt(objects[13].toString()))){
								InternalMarksEntryTO oldTO = batchMap.remove(Integer.parseInt(objects[13].toString()));
								if(oldTO.getClassesId()!=null && !oldTO.getClassesId().isEmpty()){
									String classId = oldTO.getClassesId();
									oldTO.setClassesId(classId+","+Integer.parseInt(objects[1].toString()));
									List<Integer> classesList = oldTO.getClassList();
									classesList.add(Integer.parseInt(objects[1].toString()));
									oldTO.setClassList(classesList);
								}
								if(oldTO.getClassName()!=null && !oldTO.getClassName().isEmpty()){
									String className = oldTO.getClassName();
									oldTO.setClassName(className+","+objects[2].toString());
								}
								batchMap.put(Integer.parseInt(objects[13].toString()), oldTO);
							}else{
								List<Integer> classList = new ArrayList<Integer>();
								if(objects[0] != null && !objects[0].toString().isEmpty()){
									to.setExamId(Integer.parseInt(objects[0].toString()));
								}
								if(objects[1] != null && !objects[1].toString().isEmpty()){
									to.setClassesId(objects[1].toString());
									to.setClassId(Integer.parseInt(objects[1].toString()));
									classList.add(Integer.parseInt(objects[1].toString()));
									to.setClassList(classList);
								}
								if(objects[2] != null && objects[2].toString() != null){
									to.setClassName(objects[2].toString());
								}
								if(objects[3] != null && !objects[3].toString().isEmpty()){
									to.setSubId(Integer.parseInt(objects[3].toString()));
								}
								if(objects[4] != null && objects[4].toString() != null){
									to.setSubjectName(objects[4].toString());
								}
								if(objects[5] != null){
									to.setSubjectCode(objects[5].toString());
								}
								if(objects[0] != null && !objects[0].toString().isEmpty()){
									to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(objects[0].toString()),"ExamDefinitionBO",true,"name"));
								}
								if(objects[7] != null){
									to.setSubjectType(objects[7].toString());
								}
								if(objects[8] != null){
									to.setCourseId(objects[8].toString());
								}
								if(objects[9] != null){
									to.setSchemeNo(objects[9].toString());
								}
								if(objects[10] != null && objects[10].toString() != null && !objects[10].toString().trim().isEmpty()){
									to.setProgramTypeId(Integer.parseInt(objects[10].toString()));
								}
								if(objects[11] != null && objects[7] != null && 
										(objects[11].toString().equalsIgnoreCase("T") || objects[11].toString().equalsIgnoreCase("B"))
										&& (objects[7].toString().equalsIgnoreCase("T") || objects[7].toString().equalsIgnoreCase("B"))){
									to.setTheoryOpen(true);
								}else{
									to.setTheoryOpen(false);
								}
								if(objects[11] != null && objects[7] != null &&  
										(objects[11].toString().equalsIgnoreCase("P") || objects[11].toString().equalsIgnoreCase("B"))
										&& (objects[7].toString().equalsIgnoreCase("P") || objects[7].toString().equalsIgnoreCase("B"))){
									to.setPracticalOpen(true);
								}else{
									to.setPracticalOpen(false);
								}
								if(objects[12] != null && objects[12].toString() != null && !objects[12].toString().trim().isEmpty()){
									to.setEndDate(objects[12].toString());
								}
								if(objects[13]!=null && objects[13].toString()!=null && !objects[13].toString().trim().isEmpty()){
									to.setBatchId(Integer.parseInt(objects[13].toString()));
								}
								if(objects[14]!=null && objects[14].toString()!=null && !objects[14].toString().trim().isEmpty()){
									to.setBatchName(objects[14].toString());
								}/*code modified */
								if((to.getSubjectType().equalsIgnoreCase("T")&& (to.isTheoryOpen())) || (to.getSubjectType().equalsIgnoreCase("P")&&(to.isPracticalOpen()))){
									batchMap.put(Integer.parseInt(objects[13].toString()), to);
								}else if((to.getSubjectType().equalsIgnoreCase("B")&& to.isTheoryOpen()) || (to.getSubjectType().equalsIgnoreCase("B")&& to.isPracticalOpen())){
									batchMap.put(Integer.parseInt(objects[13].toString()), to);
								}
								//batchMap.put(Integer.parseInt(objects[13].toString()), to);
								/*------------*/
							}
							batchmainMap.put(Integer.parseInt(objects[0].toString()), batchMap);
						}else{
							List<Integer> classList = new ArrayList<Integer>();
							if(objects[0] != null && !objects[0].toString().isEmpty()){
								to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(objects[0].toString()),"ExamDefinitionBO",true,"name"));
							}
							if(objects[0] != null && !objects[0].toString().isEmpty()){
								to.setExamId(Integer.parseInt(objects[0].toString()));
							}
							if(objects[1] != null && !objects[1].toString().isEmpty()){
								to.setClassId(Integer.parseInt(objects[1].toString()));
								classList.add(Integer.parseInt(objects[1].toString()));
								to.setClassList(classList);
							}
							if(objects[2] != null && objects[2].toString() != null){
								to.setClassName(objects[2].toString());
							}
							if(objects[3] != null && !objects[3].toString().isEmpty()){
								to.setSubId(Integer.parseInt(objects[3].toString()));
							}
							if(objects[4] != null && objects[4].toString() != null){
								to.setSubjectName(objects[4].toString());
							}
							if(objects[5] != null){
								to.setSubjectCode(objects[5].toString());
							}
							/*if(objects[6] != null && objects[6].toString()!=null && !objects[6].toString().trim().isEmpty()){
								to.setExamName(objects[6].toString());
							}*/
							if(objects[7] != null){
								to.setSubjectType(objects[7].toString());
							}
							if(objects[8] != null){
								to.setCourseId(objects[8].toString());
							}
							if(objects[9] != null){
								to.setSchemeNo(objects[9].toString());
							}
							if(objects[10] != null && objects[10].toString() != null && !objects[10].toString().trim().isEmpty()){
								to.setProgramTypeId(Integer.parseInt(objects[10].toString()));
							}
							if(objects[11] != null && (objects[11].toString().equalsIgnoreCase("T") || objects[11].toString().equalsIgnoreCase("B"))){
								to.setTheoryOpen(true);
							}else{
								to.setTheoryOpen(false);
							}
							if(objects[11] != null && (objects[11].toString().equalsIgnoreCase("P") || objects[11].toString().equalsIgnoreCase("B"))){
								to.setPracticalOpen(true);
							}else{
								to.setPracticalOpen(false);
							}
							if(objects[12] != null && objects[12].toString() != null && !objects[12].toString().trim().isEmpty()){
								to.setEndDate(objects[12].toString());
							}
							/*if(objects[13]!=null && objects[13].toString()!=null && !objects[13].toString().trim().isEmpty()){
								to.setBatchId(Integer.parseInt(objects[13].toString()));
							}
							if(objects[14]!=null && objects[14].toString()!=null && !objects[14].toString().trim().isEmpty()){
								to.setBatchName(objects[14].toString());
							}*/
							
							if((to.getSubjectType().equalsIgnoreCase("T")&& (to.isTheoryOpen())) || (to.getSubjectType().equalsIgnoreCase("P")&&(to.isPracticalOpen()))){
								internalMarksToList.add(to);
							}else if((to.getSubjectType().equalsIgnoreCase("B")&& to.isTheoryOpen()) || (to.getSubjectType().equalsIgnoreCase("B")&& to.isPracticalOpen())){
								internalMarksToList.add(to);
							}
						}
					}else{
						if(objects[0] != null && !objects[0].toString().isEmpty()){
							to.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(objects[0].toString()),"ExamDefinitionBO",true,"name"));
						}
						if(objects[0] != null && !objects[0].toString().isEmpty()){
							to.setExamId(Integer.parseInt(objects[0].toString()));
						}
						if(objects[1] != null && !objects[1].toString().isEmpty()){
							to.setClassId(Integer.parseInt(objects[1].toString()));
						}
						if(objects[2] != null && objects[2].toString() != null){
							to.setClassName(objects[2].toString());
						}
						if(objects[3] != null && !objects[3].toString().isEmpty()){
							to.setSubId(Integer.parseInt(objects[3].toString()));
						}
						if(objects[4] != null && objects[4].toString() != null){
							to.setSubjectName(objects[4].toString());
						}
						if(objects[5] != null){
							to.setSubjectCode(objects[5].toString());
						}
						/*if(objects[6] != null && objects[6].toString()!=null && !objects[6].toString().trim().isEmpty()){
							to.setExamName(objects[6].toString());
						}*/
						if(objects[7] != null){
							to.setSubjectType(objects[7].toString());
						}
						if(objects[8] != null){
							to.setCourseId(objects[8].toString());
						}
						if(objects[9] != null){
							to.setSchemeNo(objects[9].toString());
						}
						if(objects[10] != null && objects[10].toString() != null && !objects[10].toString().trim().isEmpty()){
							to.setProgramTypeId(Integer.parseInt(objects[10].toString()));
						}
						if(objects[11] != null && (objects[11].toString().equalsIgnoreCase("T") || objects[11].toString().equalsIgnoreCase("B"))){
							to.setTheoryOpen(true);
						}else{
							to.setTheoryOpen(false);
						}
						if(objects[11] != null && (objects[11].toString().equalsIgnoreCase("P") || objects[11].toString().equalsIgnoreCase("B"))){
							to.setPracticalOpen(true);
						}else{
							to.setPracticalOpen(false);
						}
						if(objects[12] != null && objects[12].toString() != null && !objects[12].toString().trim().isEmpty()){
							to.setEndDate(objects[12].toString());
						}
						if(objects[13]!=null && objects[13].toString()!=null && !objects[13].toString().trim().isEmpty()){
							to.setBatchId(Integer.parseInt(objects[13].toString()));
						}
						if(objects[14]!=null && objects[14].toString()!=null && !objects[14].toString().trim().isEmpty()){
							to.setBatchName(objects[14].toString());
						}
					}/*code modified by sudhir*/
				if(objects[13]==null ){
					if((to.getSubjectType().equalsIgnoreCase("T")&& (to.isTheoryOpen())) || (to.getSubjectType().equalsIgnoreCase("P")&&(to.isPracticalOpen()))){
						internalMarksToList.add(to);
					}else if((to.getSubjectType().equalsIgnoreCase("B")&& to.isTheoryOpen()) || (to.getSubjectType().equalsIgnoreCase("B")&& to.isPracticalOpen())){
						internalMarksToList.add(to);
					}
					//internalMarksToList.add(to);
				}
	//		} 
			}
				
			if(!batchmainMap.isEmpty()){
				Iterator<Map.Entry<Integer, Map<Integer,InternalMarksEntryTO>>> itr1 = batchmainMap.entrySet().iterator();
				while (itr1.hasNext()) {
					Map.Entry<java.lang.Integer, java.util.Map<java.lang.Integer, com.kp.cms.to.exam.InternalMarksEntryTO>> entry = (Map.Entry<java.lang.Integer, java.util.Map<java.lang.Integer, com.kp.cms.to.exam.InternalMarksEntryTO>>) itr1
							.next();
					Map<Integer,InternalMarksEntryTO> batchMap1 = entry.getValue();
					if(batchMap1!=null && !batchMap1.toString().isEmpty()){
						Iterator<Map.Entry<Integer, InternalMarksEntryTO>> itr2 = batchMap1.entrySet().iterator();
						while (itr2.hasNext()) {
							Map.Entry<java.lang.Integer, com.kp.cms.to.exam.InternalMarksEntryTO> entry2 = (Map.Entry<java.lang.Integer, com.kp.cms.to.exam.InternalMarksEntryTO>) itr2
									.next();
							InternalMarksEntryTO to = entry2.getValue();
							/* Code modified by sudhir */
							if((to.getSubjectType().equalsIgnoreCase("T")&& (to.isTheoryOpen())) || (to.getSubjectType().equalsIgnoreCase("P")&&(to.isPracticalOpen()))){
								internalMarksToList.add(to);
							}else if((to.getSubjectType().equalsIgnoreCase("B")&& to.isTheoryOpen()) || (to.getSubjectType().equalsIgnoreCase("B")&& to.isPracticalOpen())){
								internalMarksToList.add(to);
							}
							//internalMarksToList.add(to);
						}
					}
				}
				
			}
			}
		return internalMarksToList;
	}

	/**
	 * @param listOfDetails
	 * @param objform
	 * @param string
	 * @param opendExamList
	 * @throws Exception
	 */
	public void convertBOtoTO1(List<InternalMarksEntryTO> listOfDetails, InternalMarksEntryForm objform, String string, List<OpenInternalExamForClasses> opendexamIds) throws Exception{
		if(listOfDetails!=null && !listOfDetails.isEmpty()){
			
			List<InternalMarksEntryTO> theoryList = new ArrayList<InternalMarksEntryTO>();
			List<InternalMarksEntryTO> practicalList = new ArrayList<InternalMarksEntryTO>();
			INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
			Map<Integer,Map<String, Map<Integer, InternalMarksEntryTO>>> examMap = new HashMap<Integer, Map<String,Map<Integer,InternalMarksEntryTO>>>();
			Map<String, Map<Integer, InternalMarksEntryTO>> classMap = null;
			Map<Integer, InternalMarksEntryTO> subjectMap = null; 
			InternalMarksEntryTO oldTO = null;
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			Iterator<InternalMarksEntryTO> iterator = listOfDetails.iterator();
			while (iterator.hasNext()) {
				InternalMarksEntryTO internalMarksTO =  iterator.next();
//				if(internalMarksTO.getExamId() == 234){
				if(internalMarksTO.getBatchId()!=0){
					Map<Integer,MarksDetailsTO> existsMarks=new HashMap<Integer, MarksDetailsTO>();
					Set<StudentMarksTO> studentList=new HashSet<StudentMarksTO>();
					List currentStudentList=impl.getQueryForCurrentBatchStudents(internalMarksTO.getSubId(),internalMarksTO.getClassList(),internalMarksTO.getBatchId());
					
					List marksList = impl.getAlreadyEnteredMarksForBatchStudents(internalMarksTO.getSubId(),internalMarksTO.getExamId(),internalMarksTO.getClassesId(),internalMarksTO.getSubjectType(),currentStudentList);
					if(marksList!=null && !marksList.isEmpty()){
						existsMarks=convertBoDataToMarksMap(marksList);
					}
					boolean enablemarks = checkExamIsOpendOrNot(opendexamIds,internalMarksTO.getSubjectType(),internalMarksTO);
					if(currentStudentList!=null && !currentStudentList.isEmpty()){
						studentList = convertBotoTOListForCurrentStudents1(studentList,currentStudentList,existsMarks,listOfDetainedStudents,internalMarksTO,internalMarksTO.getSubjectType(),enablemarks);
					}
					List<StudentMarksTO> list = new ArrayList<StudentMarksTO>(studentList);
					Collections.sort(list);
					internalMarksTO.setMarksList(list);
					if(list.size() <= existsMarks.size()){
						internalMarksTO.setStatus("Completed");
					}else{
						internalMarksTO.setStatus("Pending");
					}
					if(enablemarks){
						internalMarksTO.setFinalStatus("Edit");
						
					}else{
						internalMarksTO.setFinalStatus("View");
					}
					
					
					if(examMap.containsKey(internalMarksTO.getExamId())){
						classMap = examMap.remove(internalMarksTO.getExamId());
					}else{
						classMap = new HashMap<String, Map<Integer,InternalMarksEntryTO>>();
					}
					if(classMap.containsKey(String.valueOf(internalMarksTO.getClassId()))){
						subjectMap = classMap.remove(String.valueOf(internalMarksTO.getClassId()));
					}else{
						subjectMap = new HashMap<Integer, InternalMarksEntryTO>();
					}
					if(subjectMap.containsKey(internalMarksTO.getSubId())){
						oldTO = subjectMap.remove(internalMarksTO.getSubId());
					}else{
						oldTO = new InternalMarksEntryTO();
					}
//					if(subjectMap!=null){
//					
//					}else{
//						subjectMap = new HashMap<Integer, InternalMarksEntryTO>();
//						oldTO = new InternalMarksEntryTO();
//					}
					boolean duplicate = true;
					if(oldTO.getEndDate() != null && internalMarksTO.getEndDate() != null){
						duplicate = checkEndDate(internalMarksTO.getEndDate(),oldTO.getEndDate());
					}
					if(duplicate){
						subjectMap.put(internalMarksTO.getSubId(), internalMarksTO);
						classMap.put(String.valueOf(internalMarksTO.getBatchId()), subjectMap);
						examMap.put(internalMarksTO.getExamId(), classMap);
					}else{
						subjectMap.put(internalMarksTO.getSubId(), oldTO);
						classMap.put(String.valueOf(internalMarksTO.getBatchId()), subjectMap);
						examMap.put(internalMarksTO.getExamId(), classMap);
					}
					if(internalMarksTO.getSubjectType() != null && (internalMarksTO.getSubjectType().equalsIgnoreCase("T") || internalMarksTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksTO.isTheoryOpen()){
						theoryList.add(internalMarksTO);
					}
					if(internalMarksTO.getSubjectType() != null && (internalMarksTO.getSubjectType().equalsIgnoreCase("P") || internalMarksTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksTO.isPracticalOpen()){
						practicalList.add(internalMarksTO);
					}
				}else{
					String marksQuery=getQueryForAlreadyEnteredMarks(internalMarksTO.getSubId(),internalMarksTO.getExamId(),internalMarksTO.getClassId(),internalMarksTO.getSubjectType());
					List marksList=transaction.getDataForQuery(marksQuery);
					String currentStudentQuery=getQueryForCurrentClassStudents(internalMarksTO.getSubId(),internalMarksTO.getExamId(),internalMarksTO.getClassId());
					List currentStudentList=transaction.getDataForQuery(currentStudentQuery);
					Map<Integer,MarksDetailsTO> existsMarks=new HashMap<Integer, MarksDetailsTO>();
					Set<StudentMarksTO> studentList=new HashSet<StudentMarksTO>();
					if(marksList!=null && !marksList.isEmpty()){
						existsMarks=convertBoDataToMarksMap(marksList);
					}
					boolean enablemarks = checkExamIsOpendOrNot(opendexamIds,internalMarksTO.getSubjectType(),internalMarksTO);
					if(currentStudentList!=null && !currentStudentList.isEmpty()){
						studentList=convertBotoTOListForCurrentStudents(studentList,currentStudentList,existsMarks,listOfDetainedStudents,internalMarksTO,internalMarksTO.getSubjectType(),enablemarks);
					}
					List<StudentMarksTO> list = new ArrayList<StudentMarksTO>(studentList);
					Collections.sort(list);
					internalMarksTO.setMarksList(list);
					if(list.size() <= existsMarks.size()){
						internalMarksTO.setStatus("Completed");
					}else{
						internalMarksTO.setStatus("Pending");
					}
					if(enablemarks){
						internalMarksTO.setFinalStatus("Edit");
						
					}else{
						internalMarksTO.setFinalStatus("View");
					}
					
					
					if(examMap.containsKey(internalMarksTO.getExamId())){
						classMap = examMap.remove(internalMarksTO.getExamId());
					}else{
						classMap = new HashMap<String, Map<Integer,InternalMarksEntryTO>>();
					}
					if(classMap.containsKey(String.valueOf(internalMarksTO.getClassId()))){
						subjectMap = classMap.remove(String.valueOf(internalMarksTO.getClassId()));
					}else{
						subjectMap = new HashMap<Integer, InternalMarksEntryTO>();
					}
					if(subjectMap.containsKey(internalMarksTO.getSubId())){
						oldTO = subjectMap.remove(internalMarksTO.getSubId());
					}else{
						oldTO = new InternalMarksEntryTO();
					}
					boolean duplicate = true;
					if(oldTO.getEndDate() != null && internalMarksTO.getEndDate() != null){
						duplicate = checkEndDate(internalMarksTO.getEndDate(),oldTO.getEndDate());
					}
					if(duplicate){
						subjectMap.put(internalMarksTO.getSubId(), internalMarksTO);
						classMap.put(String.valueOf(internalMarksTO.getClassId()), subjectMap);
						examMap.put(internalMarksTO.getExamId(), classMap);
					}else{
						subjectMap.put(internalMarksTO.getSubId(), oldTO);
						classMap.put(String.valueOf(internalMarksTO.getClassId()), subjectMap);
						examMap.put(internalMarksTO.getExamId(), classMap);
					}
					if(internalMarksTO.getSubjectType() != null && (internalMarksTO.getSubjectType().equalsIgnoreCase("T") || internalMarksTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksTO.isTheoryOpen()){
						theoryList.add(internalMarksTO);
					}
					if(internalMarksTO.getSubjectType() != null && (internalMarksTO.getSubjectType().equalsIgnoreCase("P") || internalMarksTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksTO.isPracticalOpen()){
						practicalList.add(internalMarksTO);
					}
				}
//		}
			}
			objform.setExamMap(examMap);
			objform.setExamDeatails(theoryList);
			objform.setPracticalDeatails(practicalList);
		} 
	}

	/**
	 * @param studentList
	 * @param currentStudentList
	 * @param existsMarks
	 * @param listOfDetainedStudents
	 * @param internalMarksTO
	 * @param subjectType
	 * @param enablemarks
	 * @return
	 * @throws Exception
	 */
	private Set<StudentMarksTO> convertBotoTOListForCurrentStudents1( Set<StudentMarksTO> studentList, List currentStudentList, Map<Integer, MarksDetailsTO> existsMarks,
			List<Integer> listOfDetainedStudents, InternalMarksEntryTO internalMarksTO, String subjectType, boolean enablemarks) throws Exception{
		Iterator<Object[]> itr=currentStudentList.iterator();
		boolean disable=true;
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
						if(subjectType.equalsIgnoreCase("T") || subjectType.equalsIgnoreCase("B")){
//							if(detailTo.isTheorySecured() || disable)
//								to.setIsTheorySecured(true);
//							else
//								to.setIsTheorySecured(false);
							if(enablemarks){
								to.setIsTheorySecured(false);
							}else{
								to.setIsTheorySecured(true);
							}
							if(to.getTheoryMarks() != null && (to.getTheoryMarks().equalsIgnoreCase("AA")||to.getTheoryMarks().equalsIgnoreCase("NP"))){
								to.setIsTheorySecured(true);
							}
						}
						if(subjectType.equalsIgnoreCase("P") || subjectType.equalsIgnoreCase("B")){
//							if(detailTo.isPracticalSecured() || disable)
//								to.setIsPracticalSecured(true);
//							else
//								to.setIsPracticalSecured(false);
							if(enablemarks){
								to.setIsPracticalSecured(false);
							}else{
								to.setIsPracticalSecured(true);
							}
							if(to.getPracticalMarks() != null && (to.getPracticalMarks().equalsIgnoreCase("AA")||to.getPracticalMarks().equalsIgnoreCase("NP"))){
								to.setIsPracticalSecured(true);
							}
						}
					}else{
						if(objects[2]!=null){
							to.setClassId(Integer.parseInt(objects[2].toString()));
						}
						if(enablemarks){
							to.setIsTheorySecured(false);
							to.setIsPracticalSecured(false);
						}else{
							to.setIsTheorySecured(true);
							to.setIsPracticalSecured(true);
						}
					}
					if(subjectType.equalsIgnoreCase("B")){
						to.setIsTheory(true);
						to.setIsPractical(true);
					}else if(subjectType.equalsIgnoreCase("T") || subjectType.equalsIgnoreCase("B")){
						to.setIsTheory(true);
						to.setIsPractical(false);
					}else if(subjectType.equalsIgnoreCase("P") || subjectType.equalsIgnoreCase("B")){
						to.setIsTheory(false);
						to.setIsPractical(true);
					}
					studentList.add(to);
				}
			}
		}
		
		return studentList;
	}

	/**
	 * @param subId
	 * @param classesId
	 * @param batchId
	 * @return
	 * @throws Exception
	 */
	private String getQueryForCurrentBatchStudents(int subId, String classesId, int batchId) throws Exception{
		Session session = null;
		String str = "select bs.student,bs.student.id from Batch batch join batch.batchStudents bs " +
					 " where batch.isActive = 1 and batch.id = "+batchId+
					 " and batch.subject.id ="+subId+
					 " and batch.classSchemewise in (select cls.id from ClassSchemewise cls where cls.isActive = 1 and cls.classes.id="+classesId+") and bs.isActive = 1";
		return str;
	}

	/**
	 * @param listOfDetails
	 * @param objform
	 * @param string
	 * @param opendExamList
	 * @throws Exception
	 */
	public void convertBOtoTOForHODView1(List<InternalMarksEntryTO> listOfDetails, InternalMarksEntryForm objform, String string, List<OpenInternalExamForClasses> opendExamList) throws Exception{
		if(listOfDetails!=null && !listOfDetails.isEmpty()){
			
			List<InternalMarksEntryTO> theoryList = new ArrayList<InternalMarksEntryTO>();
			List<InternalMarksEntryTO> practicalList = new ArrayList<InternalMarksEntryTO>();
			INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
			Map<Integer,Map<String, Map<Integer, InternalMarksEntryTO>>> examMap = new HashMap<Integer, Map<String,Map<Integer,InternalMarksEntryTO>>>();
			Map<String, Map<Integer, InternalMarksEntryTO>> classMap = null;
			Map<Integer, InternalMarksEntryTO> subjectMap = null;
			InternalMarksEntryTO oldTO = null;
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
			Iterator<InternalMarksEntryTO> iterator = listOfDetails.iterator();
			while (iterator.hasNext()) {
				InternalMarksEntryTO internalMarksTO =  iterator.next();
				if(internalMarksTO.getBatchId()!=0){
					Map<Integer,MarksDetailsTO> existsMarks=new HashMap<Integer, MarksDetailsTO>();
					Set<StudentMarksTO> studentList=new HashSet<StudentMarksTO>();
					List currentStudentList=impl.getQueryForCurrentBatchStudents(internalMarksTO.getSubId(),internalMarksTO.getClassList(),internalMarksTO.getBatchId());
					
					List marksList = impl.getAlreadyEnteredMarksForBatchStudents(internalMarksTO.getSubId(),internalMarksTO.getExamId(),internalMarksTO.getClassesId(),internalMarksTO.getSubjectType(),currentStudentList);
					if(marksList!=null && !marksList.isEmpty()){
						existsMarks=convertBoDataToMarksMap(marksList);
					}
					boolean enablemarks = false;
					if(currentStudentList!=null && !currentStudentList.isEmpty()){
						studentList = convertBotoTOListForCurrentStudents1(studentList,currentStudentList,existsMarks,listOfDetainedStudents,internalMarksTO,internalMarksTO.getSubjectType(),enablemarks);
					}
					List<StudentMarksTO> list = new ArrayList<StudentMarksTO>(studentList);
					Collections.sort(list);
					internalMarksTO.setMarksList(list);
					if(list.size() <= existsMarks.size()){
						internalMarksTO.setStatus("Completed");
					}else{
						internalMarksTO.setStatus("Pending");
					}
					
						internalMarksTO.setFinalStatus("View");
					
					if(examMap.containsKey(internalMarksTO.getExamId())){
						classMap = examMap.remove(internalMarksTO.getExamId());
					}else{
						classMap = new HashMap<String, Map<Integer,InternalMarksEntryTO>>();
					}
					if(classMap.containsKey(String.valueOf(internalMarksTO.getClassId()))){
						subjectMap = classMap.remove(String.valueOf(internalMarksTO.getClassId()));
					}else{
						subjectMap = new HashMap<Integer, InternalMarksEntryTO>();
					}
					if(subjectMap.containsKey(internalMarksTO.getSubId())){
						oldTO = subjectMap.remove(internalMarksTO.getSubId());
					}else{
						oldTO = new InternalMarksEntryTO();
					}
					/*if(subjectMap!=null){
					
					}else{
						subjectMap = new HashMap<Integer, InternalMarksEntryTO>();
						oldTO = new InternalMarksEntryTO();
					}*/
					boolean duplicate = true;
					if(oldTO.getEndDate() != null && internalMarksTO.getEndDate() != null){
						duplicate = checkEndDate(internalMarksTO.getEndDate(),oldTO.getEndDate());
					}
					if(duplicate){
						subjectMap.put(internalMarksTO.getSubId(), internalMarksTO);
						classMap.put(String.valueOf(internalMarksTO.getBatchId()), subjectMap);
						examMap.put(internalMarksTO.getExamId(), classMap);
					}else{
						subjectMap.put(internalMarksTO.getSubId(), oldTO);
						classMap.put(String.valueOf(internalMarksTO.getBatchId()), subjectMap);
						examMap.put(internalMarksTO.getExamId(), classMap);
					}
					if(internalMarksTO.getSubjectType() != null && (internalMarksTO.getSubjectType().equalsIgnoreCase("T") || internalMarksTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksTO.isTheoryOpen()){
						theoryList.add(internalMarksTO);
					}
					if(internalMarksTO.getSubjectType() != null && (internalMarksTO.getSubjectType().equalsIgnoreCase("P") || internalMarksTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksTO.isPracticalOpen()){
						practicalList.add(internalMarksTO);
					}
				}else{
					String marksQuery=getQueryForAlreadyEnteredMarks(internalMarksTO.getSubId(),internalMarksTO.getExamId(),internalMarksTO.getClassId(),internalMarksTO.getSubjectType());
					List marksList=transaction.getDataForQuery(marksQuery);
					String currentStudentQuery=getQueryForCurrentClassStudents(internalMarksTO.getSubId(),internalMarksTO.getExamId(),internalMarksTO.getClassId());
					List currentStudentList=transaction.getDataForQuery(currentStudentQuery);
					Map<Integer,MarksDetailsTO> existsMarks=new HashMap<Integer, MarksDetailsTO>();
					Set<StudentMarksTO> studentList=new HashSet<StudentMarksTO>();
					if(marksList!=null && !marksList.isEmpty()){
						existsMarks=convertBoDataToMarksMap(marksList);
					}
					boolean enablemarks = false;
					if(currentStudentList!=null && !currentStudentList.isEmpty()){
						studentList=convertBotoTOListForCurrentStudents(studentList,currentStudentList,existsMarks,listOfDetainedStudents,internalMarksTO,internalMarksTO.getSubjectType(),enablemarks);
					}
					List<StudentMarksTO> list = new ArrayList<StudentMarksTO>(studentList);
					Collections.sort(list);
					internalMarksTO.setMarksList(list);
					if(list.size() <= existsMarks.size()){
						internalMarksTO.setStatus("Completed");
					}else{
						internalMarksTO.setStatus("Pending");
					}
						internalMarksTO.setFinalStatus("View");
					
					
					if(examMap.containsKey(internalMarksTO.getExamId())){
						classMap = examMap.remove(internalMarksTO.getExamId());
					}else{
						classMap = new HashMap<String, Map<Integer,InternalMarksEntryTO>>();
					}
					if(classMap.containsKey(String.valueOf(internalMarksTO.getClassId()))){
						subjectMap = classMap.remove(String.valueOf(internalMarksTO.getClassId()));
					}else{
						subjectMap = new HashMap<Integer, InternalMarksEntryTO>();
					}
					if(subjectMap.containsKey(internalMarksTO.getSubId())){
						oldTO = subjectMap.remove(internalMarksTO.getSubId());
					}else{
						oldTO = new InternalMarksEntryTO();
					}
					boolean duplicate = true;
					if(oldTO.getEndDate() != null && internalMarksTO.getEndDate() != null){
						duplicate = checkEndDate(internalMarksTO.getEndDate(),oldTO.getEndDate());
					}
					if(duplicate){
						subjectMap.put(internalMarksTO.getSubId(), internalMarksTO);
						classMap.put(String.valueOf(internalMarksTO.getClassId()), subjectMap);
						examMap.put(internalMarksTO.getExamId(), classMap);
					}else{
						subjectMap.put(internalMarksTO.getSubId(), oldTO);
						classMap.put(String.valueOf(internalMarksTO.getClassId()), subjectMap);
						examMap.put(internalMarksTO.getExamId(), classMap);
					}
					if(internalMarksTO.getSubjectType() != null && (internalMarksTO.getSubjectType().equalsIgnoreCase("T") || internalMarksTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksTO.isTheoryOpen()){
						theoryList.add(internalMarksTO);
					}
					if(internalMarksTO.getSubjectType() != null && (internalMarksTO.getSubjectType().equalsIgnoreCase("P") || internalMarksTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksTO.isPracticalOpen()){
						practicalList.add(internalMarksTO);
					}
				}
			}
			objform.setExamMap(examMap);
			objform.setExamDeatails(theoryList);
			objform.setPracticalDeatails(practicalList);
		}
	}
}
