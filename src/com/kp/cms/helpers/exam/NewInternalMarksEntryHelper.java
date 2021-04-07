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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.forms.exam.NewInternalMarksEntryForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.exam.InternalMarksEntryTO;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewInternalMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class NewInternalMarksEntryHelper {
	INewInternalMarksEntryTransaction transaction = NewInternalMarksEntryTransactionImpl.getInstance();
	private static volatile NewInternalMarksEntryHelper marksEntryHelper = null;
	private static final Log log = LogFactory.getLog(NewInternalMarksEntryHelper.class);
	public static NewInternalMarksEntryHelper getInstance(){
		if(marksEntryHelper == null){
			marksEntryHelper = new NewInternalMarksEntryHelper();
		}
		return marksEntryHelper;
	}
	private NewInternalMarksEntryHelper(){
		
	}
	/**
	 * @param internalMarksEntryForm
	 * @param openExamList
	 * @throws Exception
	 */
	public Map<Integer,Map<String,Map<Integer,OpenInternalExamForClasses>>> convertBoListTOMap( NewInternalMarksEntryForm internalMarksEntryForm,
			List<OpenInternalExamForClasses> openExamList) throws Exception{
		
		Map<Integer,Map<String,Map<Integer,OpenInternalExamForClasses>>> openExamInternalMap = new HashMap<Integer, Map<String,Map<Integer,OpenInternalExamForClasses>>>();
		Map<String,Map<Integer,OpenInternalExamForClasses>> subjectTypeWiseMap = null;
		Map<Integer,OpenInternalExamForClasses> classWiseMap = null;
		if(openExamList!=null && !openExamList.isEmpty()){
			Iterator<OpenInternalExamForClasses> iterator = openExamList.iterator();
			while (iterator.hasNext()) {
				OpenInternalExamForClasses bo = (OpenInternalExamForClasses) iterator .next();
				if(!openExamInternalMap.containsKey(bo.getOpenExam().getExam().getId())){
					subjectTypeWiseMap = new HashMap<String, Map<Integer,OpenInternalExamForClasses>>();
					classWiseMap = new HashMap<Integer, OpenInternalExamForClasses>();
					classWiseMap.put(bo.getClasses().getId(), bo);
				}else{
					 subjectTypeWiseMap =  openExamInternalMap.get(bo.getOpenExam().getExam().getId());
					if(!subjectTypeWiseMap.containsKey(bo.getOpenExam().getTheoryPractical())){
						/*Map<String,Map<Integer,OpenInternalExamForClasses>> subjectTypeWiseMap1  = new HashMap<String, Map<Integer,OpenInternalExamForClasses>>();
						Map<Integer,OpenInternalExamForClasses> classWiseMap1 = new HashMap<Integer, OpenInternalExamForClasses>();*/
						classWiseMap = new HashMap<Integer, OpenInternalExamForClasses>();
						classWiseMap.put(bo.getClasses().getId(), bo);
					}else{
						classWiseMap = subjectTypeWiseMap.get(bo.getOpenExam().getTheoryPractical());
						if(!classWiseMap.containsKey(bo.getClasses().getId())){
							classWiseMap.put(bo.getClasses().getId(), bo);
						}else if(classWiseMap.containsKey(bo.getClasses().getId())){
							classWiseMap.put(bo.getClasses().getId(), bo);
						}
					}
				}
				subjectTypeWiseMap.put(bo.getOpenExam().getTheoryPractical(), classWiseMap);
				openExamInternalMap.put(bo.getOpenExam().getExam().getId(), subjectTypeWiseMap);
			}
		}
		return openExamInternalMap;
	}
	/**
	 * @param internalExamList
	 * @return
	 * @throws Exception
	 */
	public List<InternalMarksEntryTO> convertBoListTOTolist( List<Object[]> internalExamList) throws Exception{
		List<InternalMarksEntryTO> internalMarksEntryTOsList = new ArrayList<InternalMarksEntryTO>();
		Map<Integer,Map<Integer,InternalMarksEntryTO>> examWiseBatchMap = new HashMap<Integer, Map<Integer,InternalMarksEntryTO>>();
		Map<Integer,InternalMarksEntryTO> batchWiseClassMap = null;
		Iterator<Object[]> iterator = internalExamList.iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			InternalMarksEntryTO internalMarksTo = new InternalMarksEntryTO();
			if(obj[13]!=null && !obj[13].toString().isEmpty()){ //obj[13] is a batchId
//				the below condition is to check, In case subject open for Theory and its subject type is Both but the subject attendanceType is practical (means batch is only for practicals not for theory)
//				then we have to consider it as a class not as a batch.
				if(obj[7]!=null && obj[11]!=null && obj[15]!=null && 
						(((obj[7].toString().equalsIgnoreCase("T") || obj[7].toString().equalsIgnoreCase("B")) && obj[11].toString().equalsIgnoreCase("T") && Integer.valueOf(obj[15].toString())==1)
									|| ((obj[7].toString().equalsIgnoreCase("P") || obj[7].toString().equalsIgnoreCase("B")) && obj[11].toString().equalsIgnoreCase("P") && Integer.valueOf(obj[15].toString())==2))){
					// here obj[7] is subject isTheoryPractical and obj[11] is openInternalMarks isTheoryPractical and obj[15] is attendanceTypeId.
					if(examWiseBatchMap.containsKey(Integer.parseInt(obj[0].toString()))){
						batchWiseClassMap = examWiseBatchMap.remove(Integer.parseInt(obj[0].toString()));
					}else{
						batchWiseClassMap = new HashMap<Integer, InternalMarksEntryTO>();
					}
					if(batchWiseClassMap.containsKey(Integer.parseInt(obj[13].toString()))){
						InternalMarksEntryTO oldInternalMarksEntryTo = batchWiseClassMap.remove(Integer.parseInt(obj[13].toString()));
						if(oldInternalMarksEntryTo.getClassesId()!=null && !oldInternalMarksEntryTo.getClassesId().isEmpty()){
							String classId = oldInternalMarksEntryTo.getClassesId();
							oldInternalMarksEntryTo.setClassesId(classId+","+Integer.parseInt(obj[1].toString()));
							List<Integer> classesList = oldInternalMarksEntryTo.getClassList();
							classesList.add(Integer.parseInt(obj[1].toString()));
							oldInternalMarksEntryTo.setClassList(classesList);
						}
						if(oldInternalMarksEntryTo.getClassName()!=null && !oldInternalMarksEntryTo.getClassName().isEmpty()){
							String className = oldInternalMarksEntryTo.getClassName();
							oldInternalMarksEntryTo.setClassName(className+","+obj[2].toString());
						}
						batchWiseClassMap.put(Integer.parseInt(obj[13].toString()), oldInternalMarksEntryTo);
						examWiseBatchMap.put(Integer.parseInt(obj[0].toString()), batchWiseClassMap);
						continue;
					}else{
						List<Integer> classList = new ArrayList<Integer>();
						if(obj[1] != null && !obj[1].toString().isEmpty()){
							internalMarksTo.setClassesId(obj[1].toString());
							internalMarksTo.setClassId(Integer.parseInt(obj[1].toString()));
							classList.add(Integer.parseInt(obj[1].toString()));
							internalMarksTo.setClassList(classList);
						}
					}
				}
			}
			/* ------------------------ common code for batch as well as non batch(class)------------------------------*/
			if(obj[0] != null && !obj[0].toString().isEmpty()){
				internalMarksTo.setExamId(Integer.parseInt(obj[0].toString()));
			}
			if(obj[0] != null && !obj[0].toString().isEmpty()){
				internalMarksTo.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(obj[0].toString()),"ExamDefinitionBO",true,"name"));
			}
			if(obj[1] != null && !obj[1].toString().isEmpty()){
				internalMarksTo.setClassId(Integer.parseInt(obj[1].toString()));
			}
			if(obj[2] != null && obj[2].toString() != null){
				internalMarksTo.setClassName(obj[2].toString());
			}
			if(obj[3] != null && !obj[3].toString().isEmpty()){
				internalMarksTo.setSubId(Integer.parseInt(obj[3].toString()));
			}
			if(obj[4] != null && obj[4].toString() != null){
				internalMarksTo.setSubjectName(obj[4].toString());
			}
			if(obj[5] != null){
				internalMarksTo.setSubjectCode(obj[5].toString());
			}
			if(obj[7] != null){
				internalMarksTo.setSubjectType(obj[7].toString());
			}
			if(obj[8] != null){
				internalMarksTo.setCourseId(obj[8].toString());
			}
			if(obj[9] != null){
				internalMarksTo.setSchemeNo(obj[9].toString());
			}
			if(obj[10] != null && obj[10].toString() != null && !obj[10].toString().trim().isEmpty()){
				internalMarksTo.setProgramTypeId(Integer.parseInt(obj[10].toString()));
			}
			if(obj[11] != null){
				internalMarksTo.setTheoryPractical(obj[11].toString());
			}
			if(obj[11] != null && obj[7] != null && 
					(obj[11].toString().equalsIgnoreCase("T") || obj[11].toString().equalsIgnoreCase("B"))
					&& (obj[7].toString().equalsIgnoreCase("T") || obj[7].toString().equalsIgnoreCase("B"))){
				internalMarksTo.setTheoryOpen(true);
			}else{
				internalMarksTo.setTheoryOpen(false);
			}
			if(obj[11] != null && obj[7] != null &&  
					(obj[11].toString().equalsIgnoreCase("P") || obj[11].toString().equalsIgnoreCase("B"))
					&& (obj[7].toString().equalsIgnoreCase("P") || obj[7].toString().equalsIgnoreCase("B"))){
				internalMarksTo.setPracticalOpen(true);
			}else{
				internalMarksTo.setPracticalOpen(false);
			}
			if(obj[12] != null && obj[12].toString() != null && !obj[12].toString().trim().isEmpty()){
				internalMarksTo.setEndDate(obj[12].toString());
			}
			if(obj[7]!=null && obj[11]!=null && obj[15]!=null){
				if(((obj[7].toString().equalsIgnoreCase("T") || obj[7].toString().equalsIgnoreCase("B")) && obj[11].toString().equalsIgnoreCase("T") && Integer.valueOf(obj[15].toString())==1)
					|| ((obj[7].toString().equalsIgnoreCase("P") || obj[7].toString().equalsIgnoreCase("B")) && obj[11].toString().equalsIgnoreCase("P") && Integer.valueOf(obj[15].toString())==2)){
						if(obj[13]!=null && obj[13].toString()!=null && !obj[13].toString().trim().isEmpty()){
							internalMarksTo.setBatchId(Integer.parseInt(obj[13].toString()));
						}
						if(obj[14]!=null && obj[14].toString()!=null && !obj[14].toString().trim().isEmpty()){
							internalMarksTo.setBatchName(obj[14].toString());
						}
					}else{
						internalMarksTo.setBatchId(0);
						internalMarksTo.setBatchName(null);
					}
			}else{
				internalMarksTo.setBatchId(0);
				internalMarksTo.setBatchName(null);
			}
			/*if(obj[7]!=null && obj[11]!=null && obj[15]!=null && ((obj[7].toString().equalsIgnoreCase("T") || obj[7].toString().equalsIgnoreCase("B")) && obj[11].toString().equalsIgnoreCase("T") && Integer.valueOf(obj[15].toString())==1)
					|| ((obj[7].toString().equalsIgnoreCase("P") || obj[7].toString().equalsIgnoreCase("B")) && obj[11].toString().equalsIgnoreCase("P") && Integer.valueOf(obj[15].toString())==2)){
				if(obj[13]!=null && obj[13].toString()!=null && !obj[13].toString().trim().isEmpty()){
					internalMarksTo.setBatchId(Integer.parseInt(obj[13].toString()));
				}
				if(obj[14]!=null && obj[14].toString()!=null && !obj[14].toString().trim().isEmpty()){
					internalMarksTo.setBatchName(obj[14].toString());
				}
			}else{
				internalMarksTo.setBatchId(0);
				internalMarksTo.setBatchName(null);
			}*/
			
			/*------------------ common code ends here-------------------------------------*/
			if(obj[13]==null ){
				if((internalMarksTo.getSubjectType().equalsIgnoreCase("T")&& (internalMarksTo.isTheoryOpen())) || (internalMarksTo.getSubjectType().equalsIgnoreCase("P")&&(internalMarksTo.isPracticalOpen()))){
					internalMarksEntryTOsList.add(internalMarksTo);
				}else if((internalMarksTo.getSubjectType().equalsIgnoreCase("B")&& internalMarksTo.isTheoryOpen()) || (internalMarksTo.getSubjectType().equalsIgnoreCase("B")&& internalMarksTo.isPracticalOpen())){
					internalMarksEntryTOsList.add(internalMarksTo);
				}
			}else if(obj[13]!=null ){
				if(((obj[7].toString().equalsIgnoreCase("T") || obj[7].toString().equalsIgnoreCase("B")) && obj[11].toString().equalsIgnoreCase("T") && Integer.valueOf(obj[15].toString())==1)
						|| ((obj[7].toString().equalsIgnoreCase("P") || obj[7].toString().equalsIgnoreCase("B")) && obj[11].toString().equalsIgnoreCase("P") && Integer.valueOf(obj[15].toString())==2)){
					batchWiseClassMap.put(Integer.parseInt(obj[13].toString()), internalMarksTo);
					examWiseBatchMap.put(Integer.parseInt(obj[0].toString()), batchWiseClassMap);	
				}else{
						if((internalMarksTo.getSubjectType().equalsIgnoreCase("T")&& (internalMarksTo.isTheoryOpen())) || (internalMarksTo.getSubjectType().equalsIgnoreCase("P")&&(internalMarksTo.isPracticalOpen()))){
							internalMarksEntryTOsList.add(internalMarksTo);
						}else if((internalMarksTo.getSubjectType().equalsIgnoreCase("B")&& internalMarksTo.isTheoryOpen()) || (internalMarksTo.getSubjectType().equalsIgnoreCase("B")&& internalMarksTo.isPracticalOpen())){
							internalMarksEntryTOsList.add(internalMarksTo);
						}
					}
				}
			}
		if(!examWiseBatchMap.isEmpty()){
			Iterator<Map.Entry<Integer, Map<Integer,InternalMarksEntryTO>>> itr1 = examWiseBatchMap.entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry<java.lang.Integer, java.util.Map<java.lang.Integer, com.kp.cms.to.exam.InternalMarksEntryTO>> entry = (Map.Entry<java.lang.Integer, java.util.Map<java.lang.Integer, com.kp.cms.to.exam.InternalMarksEntryTO>>) itr1
						.next();
				Map<Integer,InternalMarksEntryTO> examWiseClassMap1 = entry.getValue();
				if(examWiseClassMap1!=null && !examWiseClassMap1.toString().isEmpty()){
					Iterator<Map.Entry<Integer, InternalMarksEntryTO>> itr2 = examWiseClassMap1.entrySet().iterator();
					while (itr2.hasNext()) {
						Map.Entry<java.lang.Integer, com.kp.cms.to.exam.InternalMarksEntryTO> entry2 = (Map.Entry<java.lang.Integer, com.kp.cms.to.exam.InternalMarksEntryTO>) itr2
								.next();
						InternalMarksEntryTO to = entry2.getValue();
						if((to.getSubjectType().equalsIgnoreCase("T")&& (to.isTheoryOpen())) || (to.getSubjectType().equalsIgnoreCase("P")&&(to.isPracticalOpen()))){
							internalMarksEntryTOsList.add(to);
						}else if((to.getSubjectType().equalsIgnoreCase("B")&& to.isTheoryOpen()) || (to.getSubjectType().equalsIgnoreCase("B")&& to.isPracticalOpen())){
							internalMarksEntryTOsList.add(to);
						}
					}
				}
			}
		}
		return internalMarksEntryTOsList;
	}
	/**
	 * @param listOfDetails
	 * @param internalMarksEntryForm
	 * @param string
	 * @param openExamList
	 * @throws Exception
	 */
	public void convertBOtoTO(List<InternalMarksEntryTO> listOfDetails, NewInternalMarksEntryForm internalMarksEntryForm, String theory,
			Map<Integer,Map<String,Map<Integer,OpenInternalExamForClasses>>> openExamDetailsMap)throws Exception {
		if(listOfDetails!=null && !listOfDetails.isEmpty()){
			List<InternalMarksEntryTO> theoryList = new ArrayList<InternalMarksEntryTO>();
			List<InternalMarksEntryTO> practicalList = new ArrayList<InternalMarksEntryTO>();
			Map<Integer,Map<String, Map<Integer, Map<String,InternalMarksEntryTO>>>> examMap = new HashMap<Integer, Map<String,Map<Integer,Map<String,InternalMarksEntryTO>>>>();
			Map<String, Map<Integer, Map<String,InternalMarksEntryTO>>> classMap = null;
			Map<Integer, Map<String,InternalMarksEntryTO>> subjectMap = null; 
			Map<String,InternalMarksEntryTO> subjectType = null;
			InternalMarksEntryTO oldTO = null;
			List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
//			Iterate the list of Exam Details.
			Iterator<InternalMarksEntryTO> iterator = listOfDetails.iterator();
			while (iterator.hasNext()) {
				InternalMarksEntryTO internalMarksEntryTO = (InternalMarksEntryTO) iterator .next();
				Double maxMarks = transaction.getMaxMarksDefineForSubject(internalMarksEntryTO);
				if(maxMarks == null || maxMarks.toString().isEmpty()){
						continue;
				}
				Map<Integer,MarksDetailsTO> existsMarks=new HashMap<Integer, MarksDetailsTO>();
				Set<StudentMarksTO> studentList=new HashSet<StudentMarksTO>();
				List currentStudentList = null;
				List marksList = null;
//				check whether the subject have  batch or not. if the condition satisfies go to inside ,Otherwise execute the else Condition.
				if(internalMarksEntryTO.getBatchId()!=0){
//					get the list of batch students for the subject and class.
					 currentStudentList=transaction.getQueryForCurrentBatchStudents(internalMarksEntryTO.getSubId(),internalMarksEntryTO.getClassList(),internalMarksEntryTO.getBatchId());
//					get the list of marks of the students, if it is already entered for the exam and subject.
					 marksList = transaction.getAlreadyEnteredMarksForBatchStudents(internalMarksEntryTO.getSubId(),internalMarksEntryTO.getExamId(),internalMarksEntryTO.getSubjectType(),currentStudentList);
				}else{
//					get the list of students, for whom the marks had already entered.
					 String marksQuery=getQueryForAlreadyEnteredMarks(internalMarksEntryTO.getSubId(),internalMarksEntryTO.getExamId(),internalMarksEntryTO.getClassId(),internalMarksEntryTO.getSubjectType());
					 marksList=transaction.getDataForQuery(marksQuery);
//					get the list of current students for that exam,class, and subject.
					 String currentStudentQuery=getQueryForCurrentClassStudents(internalMarksEntryTO.getSubId(),internalMarksEntryTO.getExamId(),internalMarksEntryTO.getClassId());
					 currentStudentList=transaction.getDataForQuery(currentStudentQuery);
				}
					/*-----------common code for batches and non-batch(classes) -----------*/
					if(marksList!=null && !marksList.isEmpty()){
//						convert the list of Exists marks students to Map.In Map Key is StudentId and value is his marks details.
						existsMarks=convertBoDataToMarksMap(marksList);
					}
//					check whether the exam is open for the current day.If it is opened ,make the boolean TRUE Otherwise make the boolean FALSE.
					boolean enablemarks = checkExamIsOpendOrNot(openExamDetailsMap,internalMarksEntryTO.getSubjectType(),internalMarksEntryTO);
					if(currentStudentList!=null && !currentStudentList.isEmpty()){
//						prepare the List of current Students by checking the discontinued Students , exists marks of the students . 
						studentList = convertBotoTOListForCurrentStudents(studentList,currentStudentList,existsMarks,listOfDetainedStudents,internalMarksEntryTO,internalMarksEntryTO.getSubjectType(),enablemarks);
					}
					List<StudentMarksTO> list = new ArrayList<StudentMarksTO>(studentList);
					Collections.sort(list);
					internalMarksEntryTO.setMarksList(list);
//					if the current Students list is less than or equal the existing Marks List ,then the marks entered for the particular subject is COMPLETED
//					OtherWise make the status as a PENDING.
					if(list.size() <= existsMarks.size()){
						internalMarksEntryTO.setStatus("Completed");
					}else{
						internalMarksEntryTO.setStatus("Pending");
					}
//					if the boolean is TRUE make the FinalStatus in Edit mode ,Otherwise make it as a view mode.
					if(enablemarks){
						internalMarksEntryTO.setFinalStatus("Edit");
						
					}else{
						internalMarksEntryTO.setFinalStatus("View");
					}
					if(examMap.containsKey(internalMarksEntryTO.getExamId())){
						classMap = examMap.remove(internalMarksEntryTO.getExamId());
					}else{
						classMap = new HashMap<String, Map<Integer,Map<String,InternalMarksEntryTO>>>();
					}
					if(classMap.containsKey(String.valueOf(internalMarksEntryTO.getClassId()))){
						subjectMap = classMap.remove(String.valueOf(internalMarksEntryTO.getClassId()));
					}else{
						subjectMap = new HashMap<Integer, Map<String,InternalMarksEntryTO>>();
					}
					if(subjectMap.containsKey(internalMarksEntryTO.getSubId())){
						subjectType = subjectMap.remove(internalMarksEntryTO.getSubId());
					}else{
						subjectType = new HashMap<String, InternalMarksEntryTO>();
//						oldTO = new InternalMarksEntryTO();
					}
					if(subjectType.containsKey(internalMarksEntryTO.getTheoryPractical())){
						oldTO = subjectType.remove(internalMarksEntryTO.getTheoryPractical());
					}else{
						oldTO = new InternalMarksEntryTO();
					}
					boolean duplicate = true;
					if(oldTO.getEndDate() != null && internalMarksEntryTO.getEndDate() != null){
						duplicate = checkEndDate(internalMarksEntryTO.getEndDate(),oldTO.getEndDate());
					}
					if(duplicate){
						subjectType.put(internalMarksEntryTO.getTheoryPractical(), internalMarksEntryTO);
						subjectMap.put(internalMarksEntryTO.getSubId(), subjectType);
						if(internalMarksEntryTO.getBatchId()!=0){
							classMap.put(String.valueOf(internalMarksEntryTO.getClassId()+"_"+internalMarksEntryTO.getBatchId()), subjectMap);
						}else{
							classMap.put(String.valueOf(internalMarksEntryTO.getClassId()), subjectMap);
						}
						examMap.put(internalMarksEntryTO.getExamId(), classMap);
					}else{
						subjectType.put(internalMarksEntryTO.getTheoryPractical(), oldTO);
						subjectMap.put(internalMarksEntryTO.getSubId(), subjectType);
						if(internalMarksEntryTO.getBatchId()!=0){
							classMap.put(String.valueOf(internalMarksEntryTO.getClassId()+"_"+internalMarksEntryTO.getBatchId()), subjectMap);
						}else{
							classMap.put(String.valueOf(internalMarksEntryTO.getClassId()), subjectMap);
						}
						examMap.put(internalMarksEntryTO.getExamId(), classMap);
					}
					if(internalMarksEntryTO.getSubjectType() != null && (internalMarksEntryTO.getSubjectType().equalsIgnoreCase("T") || internalMarksEntryTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksEntryTO.isTheoryOpen()){
						theoryList.add(internalMarksEntryTO);
					}
					if(internalMarksEntryTO.getSubjectType() != null && (internalMarksEntryTO.getSubjectType().equalsIgnoreCase("P") || internalMarksEntryTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksEntryTO.isPracticalOpen()){
						practicalList.add(internalMarksEntryTO);
					}
					if(internalMarksEntryTO.getSubjectType() != null && (internalMarksEntryTO.getSubjectType().equalsIgnoreCase("P") || internalMarksEntryTO.getSubjectType().equalsIgnoreCase("B")) && internalMarksEntryTO.isPracticalOpen()){
						
					}
					/*----------common code ends here ------------------*/
			}
			internalMarksEntryForm.setExamMap(examMap);
			internalMarksEntryForm.setTheoryExamMarksDetails(theoryList);
			internalMarksEntryForm.setPracticalExamMarksDetails(practicalList);
		}
	}
	/**
	 * @param newDate
	 * @param oldDate
	 * @return
	 * @throws Exception
	 */
	private boolean checkEndDate(String newDate, String oldDate) throws Exception{
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
	 * @param studentList
	 * @param currentStudentList
	 * @param existsMarks
	 * @param listOfDetainedStudents
	 * @param internalMarksEntryTO
	 * @param subjectType
	 * @param enablemarks
	 * @return
	 * @throws Exception
	 */
	private Set<StudentMarksTO> convertBotoTOListForCurrentStudents( Set<StudentMarksTO> studentList, List currentStudentList,
			Map<Integer, MarksDetailsTO> existsMarks, List<Integer> listOfDetainedStudents, InternalMarksEntryTO internalMarksEntryTO, String subjectType,
			boolean enablemarks)throws Exception {
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
						to.setOldPracticalMarks(detailTo.getPracticalMarks());
						to.setOldTheoryMarks(detailTo.getTheoryMarks());
						to.setClassId(detailTo.getClassId());
						to.setMarksId(detailTo.getMarksId());
						if(subjectType.equalsIgnoreCase("T") || subjectType.equalsIgnoreCase("B")){
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
	 * @param openExamDetailsMap
	 * @param subjectType
	 * @param internalMarksEntryTO
	 * @return
	 * @throws Exception
	 */
	private boolean checkExamIsOpendOrNot( Map<Integer,Map<String,Map<Integer,OpenInternalExamForClasses>>> openExamDetailsMap, 
			String subjectType, InternalMarksEntryTO internalMarksEntryTO) throws Exception{
		boolean enablemarks = true;
		if(openExamDetailsMap!=null && !openExamDetailsMap.isEmpty()){
				if(openExamDetailsMap.containsKey(internalMarksEntryTO.getExamId())){
					Map<String,Map<Integer,OpenInternalExamForClasses>> subjectTypeMap = openExamDetailsMap.get(internalMarksEntryTO.getExamId());
					/*if(subjectTypeMap!=null && !subjectTypeMap.isEmpty()){
						if(subjectTypeMap.containsKey(subjectType) ){
							Map<Integer,OpenInternalExamForClasses> classWiseMap = subjectTypeMap.get(subjectType);
							if(classWiseMap!=null && !classWiseMap.isEmpty()){
								if(classWiseMap.containsKey(internalMarksEntryTO.getClassId())){
									OpenInternalExamForClasses bo = classWiseMap.get(internalMarksEntryTO.getClassId());
										if(bo.getOpenExam().getProgramType() != null && bo.getOpenExam().getProgramType().getId() != 0){
											if(bo.getOpenExam().getExam().getId() == internalMarksEntryTO.getExamId() && bo.getClasses().getId() == internalMarksEntryTO.getClassId()){
												Calendar cal1 = Calendar.getInstance();
												cal1.setTime(new Date());
												Calendar cal2 = Calendar.getInstance();
												cal2.setTime(bo.getOpenExam().getEndDate());
												long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
												if(daysBetween <= 0) {
													enablemarks= false;
												}
											}
										}
									}
								}
							}
						}*/
					if(subjectType.equalsIgnoreCase("T") || subjectType.equalsIgnoreCase("B")){
						if(subjectTypeMap!=null && !subjectTypeMap.isEmpty()){
							if(subjectTypeMap.containsKey("T") || subjectTypeMap.containsKey("B")){
								Map<Integer,OpenInternalExamForClasses> classWiseMap = null;
									if(classWiseMap == null || classWiseMap.isEmpty()){
										classWiseMap = subjectTypeMap.get("T");
									}else{
										classWiseMap = subjectTypeMap.get("B");
									}
									if(classWiseMap!=null && !classWiseMap.isEmpty()){
										if(classWiseMap.containsKey(internalMarksEntryTO.getClassId())){
											OpenInternalExamForClasses bo = classWiseMap.get(internalMarksEntryTO.getClassId());
												if(bo.getOpenExam().getProgramType() != null && bo.getOpenExam().getProgramType().getId() != 0){
													if(bo.getOpenExam().getExam().getId() == internalMarksEntryTO.getExamId() && bo.getClasses().getId() == internalMarksEntryTO.getClassId()){
														Calendar cal1 = Calendar.getInstance();
														cal1.setTime(new Date());
														Calendar cal2 = Calendar.getInstance();
														cal2.setTime(bo.getOpenExam().getEndDate());
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
					} 
					if(subjectType.equalsIgnoreCase("P") || subjectType.equalsIgnoreCase("B")){
						if(subjectTypeMap!=null && !subjectTypeMap.isEmpty()){
							if(subjectTypeMap.containsKey("P") || subjectTypeMap.containsKey("B")){
								Map<Integer,OpenInternalExamForClasses> classWiseMap = null;
									if(classWiseMap == null || classWiseMap.isEmpty()){
										classWiseMap = subjectTypeMap.get("P");
									}else{
										classWiseMap = subjectTypeMap.get("B");
									}
									if(classWiseMap!=null && !classWiseMap.isEmpty()){
										if(classWiseMap.containsKey(internalMarksEntryTO.getClassId())){
											OpenInternalExamForClasses bo = classWiseMap.get(internalMarksEntryTO.getClassId());
												if(bo.getOpenExam().getProgramType() != null && bo.getOpenExam().getProgramType().getId() != 0){
													if(bo.getOpenExam().getExam().getId() == internalMarksEntryTO.getExamId() && bo.getClasses().getId() == internalMarksEntryTO.getClassId()){
														Calendar cal1 = Calendar.getInstance();
														cal1.setTime(new Date());
														Calendar cal2 = Calendar.getInstance();
														cal2.setTime(bo.getOpenExam().getEndDate());
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
					}
				}
			}
		return enablemarks;
	}
	/**
	 * @param marksList
	 * @return
	 * @throws Exception
	 */
	private Map<Integer, MarksDetailsTO> convertBoDataToMarksMap(List marksList) throws Exception{
		Map<Integer, MarksDetailsTO> marksMap=new HashMap<Integer, MarksDetailsTO>(); // key is studentId and value is MarksDetailsTO.
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
	 * @param subId
	 * @param examId
	 * @param classId
	 * @return
	 * @throws Exception
	 */
	private String getQueryForCurrentClassStudents(int subId, int examId, int classId) throws Exception{
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
	 * @param subId
	 * @param examId
	 * @param classId
	 * @param subjectType
	 * @return
	 * @throws Exception
	 */
	private String getQueryForAlreadyEnteredMarks(int subId, int examId, int classId, String subjectType) throws Exception{
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
	return query;
	}
}
