package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.CreatePracticalBatchForm;
import com.kp.cms.helpers.attendance.CreatePracticalBatchHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.attendance.ClassSchemewiseTO;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;
import com.kp.cms.transactions.attandance.IActivityAttendenceTransaction;
import com.kp.cms.transactions.attandance.ICreatePracticalBatchTransaction;
import com.kp.cms.transactionsimpl.attendance.ActivityAttendenceTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.CreatePracticalBatchTransactionImpl;


public class CreatePracticalBatchHandler {
	
	private static final Log log = LogFactory.getLog(CreatePracticalBatchHandler.class);
	
	private static volatile CreatePracticalBatchHandler createPracticalBatchHandler = null;
	
	private CreatePracticalBatchHandler() {
	}
	/**
	 * 
	 * @return a single instance when called (Singleton implementation)
	 */
	public static CreatePracticalBatchHandler getInstance() {
		if (createPracticalBatchHandler == null) {
			createPracticalBatchHandler = new CreatePracticalBatchHandler();
		}
		return createPracticalBatchHandler;
	}
	
	ICreatePracticalBatchTransaction transaction=new CreatePracticalBatchTransactionImpl();
	
	/**
	 * 
	 * @param practicalBatchForm
	 * @param batchTO
	 * @returns the subjectGroupIDb in a List based on the subjectID.
	 * Also returns the students whose having those subjectgroup IDS. 
	 * Checking those belongs within the entered regd. no. range
	 *  Checking those belongs within the entered roll. no. range
	 * @throws Exception
	 */
	public List<CreatePracticalBatchTO> searchStudents(CreatePracticalBatchForm practicalBatchForm, CreatePracticalBatchTO batchTO)throws Exception{
		log.info("Entering into searchStudents of CreatePracticalBatchHandler");
		List<String> regNoList = new ArrayList<String>();				
		List<String> rollNoList = new ArrayList<String>();
		List<Student> studentList = new ArrayList<Student>();
		List<CreatePracticalBatchTO>allStudentList = new ArrayList<CreatePracticalBatchTO>();
		CreatePracticalBatchTO createPracticalBatchTO;
		Student student;
		
		int classSchemewiseId=batchTO.getClassSchemewiseTO().getId();
		
		//Get the Student List after searching the students based on the classSchemewiseId
		studentList=transaction.getStudentList(classSchemewiseId);
		
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		//Check if it is configured for regd. no in program table then search on regd no. else search on rollno.
		boolean isRegnoCheck = activityAttendenceTransaction.checkIsRegisterNo(classSchemewiseId);
		if(isRegnoCheck){
			practicalBatchForm.setRegdNoDisplay("yes");
		}
		else{
			practicalBatchForm.setRegdNoDisplay("no");
		}
		if(practicalBatchForm.getRegNoFrom()!=null && !practicalBatchForm.getRegNoFrom().isEmpty() &&
		practicalBatchForm.getRegNoTo()!=null && !practicalBatchForm.getRegNoTo().isEmpty()){
		String regNoFrom = practicalBatchForm.getRegNoFrom();
		String regNoTo = practicalBatchForm.getRegNoTo();		
				
		//Compare the regd No. with the entered range of regd nos in UI. If available make them checked.		
		//Get the start and end regd. No. from formbean and get all the regd nos in between		
			if(transaction!=null){
				if(isRegnoCheck){
					regNoList = transaction.getRequiredRegdNos(classSchemewiseId, regNoFrom,regNoTo); 
				}
				else {
					rollNoList = transaction.getRequiredRollNos(classSchemewiseId, regNoFrom,regNoTo); 	
				}
			}
		}
		
		boolean isReq=false;
		if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
			isReq=true;
		}
		Set<ApplicantSubjectGroup> subGrpList = new HashSet<ApplicantSubjectGroup>();
		
		BatchStudent batchStudent = null;
		
		if(studentList!=null && !studentList.isEmpty())
		{			
			Iterator<Student> itr=studentList.iterator();
			while (itr.hasNext()) {
				student = itr.next();
				boolean isCheck=false;
				//Get the subjectGroup List for the student
				if(student.getAdmAppln()!=null && student.getAdmAppln().getApplicantSubjectGroups()!=null){
				subGrpList = student.getAdmAppln().getApplicantSubjectGroups();
				}
				if(isReq){
					isCheck=checkSubjectPresentInGroup(batchTO.getSubjectTO().getId(),subGrpList);
				}else{
					isCheck=true;
				}
								if(isCheck && student.getAdmAppln()!=null 
								&& student.getAdmAppln().getIsCancelled() != null &&  !student.getAdmAppln().getIsCancelled() && 
								student.getIsAdmitted()!= null && student.getIsAdmitted())
								{
									createPracticalBatchTO = new CreatePracticalBatchTO();
									
									StudentTO studentTO = new StudentTO();
									ClassSchemewiseTO classSchemewiseTO = null;
									
									String firstName="";
									String middleName="";
									String lastName="";
									
									//createPracticalBatchTO.setStudentId(String.valueOf(student.getId()));
									studentTO.setId(student.getId());

									//Used to get batch ID
									
									Set<BatchStudent> batchStudentSet = student.getBatchStudents();
									if(batchStudentSet != null && !batchStudentSet.isEmpty()){
										Iterator<BatchStudent> iterator1 = batchStudentSet.iterator();
										while (iterator1.hasNext()) {
											batchStudent = iterator1.next();
											if(batchStudent.getBatch()!=null && batchStudent.getBatch().getId()==practicalBatchForm.getBatchId())
												createPracticalBatchTO.setBatchStudentId(batchStudent.getId());
										}
									}
									
									if(student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null && 
									student.getClassSchemewise().getClasses().getCourse()!=null && student.getClassSchemewise().getClasses().getCourse().getProgram()!=null 
									&& student.getClassSchemewise().getClasses().getCourse().getProgram().getIsRegistrationNo() !=null && 
									student.getClassSchemewise().getClasses().getCourse().getProgram().getIsRegistrationNo()==true)
									{
										classSchemewiseTO = new ClassSchemewiseTO();
										classSchemewiseTO.setId(student.getClassSchemewise().getId());
										
										if(student.getRegisterNo()!=null){
											studentTO.setRegisterNo(student.getRegisterNo());
										}
									}
									else{
										if(student.getRollNo()!=null){
											studentTO.setRollNo(student.getRollNo());
										}
									}									
									
									if(student.getAdmAppln().getPersonalData()!=null){
										if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
										firstName = student.getAdmAppln().getPersonalData().getFirstName();
										}
										if(student.getAdmAppln().getPersonalData().getMiddleName()!=null && !student.getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
										middleName= student.getAdmAppln().getPersonalData().getMiddleName();
										}
										if(student.getAdmAppln().getPersonalData().getLastName()!=null && !student.getAdmAppln().getPersonalData().getLastName().isEmpty()){
										lastName = student.getAdmAppln().getPersonalData().getLastName();
										}
										studentTO.setStudentName(firstName + " " + middleName + " " + lastName);
									}
									createPracticalBatchTO.setCheckValue(false);							
									
									if(isRegnoCheck){
										
										if(student.getRegisterNo()!=null && regNoList!=null && regNoList.contains(student.getRegisterNo())){
											createPracticalBatchTO.setDummyCheckValue(true);
											createPracticalBatchTO.setTempCheckValue(true);
										}
										else{
											createPracticalBatchTO.setDummyCheckValue(false);
											createPracticalBatchTO.setTempCheckValue(false);
										}
									} else {
										if(student.getRollNo()!=null && rollNoList!=null && rollNoList.contains(student.getRollNo())){
												createPracticalBatchTO.setDummyCheckValue(true);
												createPracticalBatchTO.setTempCheckValue(true);
										}
										else{
											createPracticalBatchTO.setDummyCheckValue(false);
											createPracticalBatchTO.setTempCheckValue(false);
										}
									}
									createPracticalBatchTO.setClassSchemewiseTO(classSchemewiseTO);
									createPracticalBatchTO.setStudentTO(studentTO);
									allStudentList.add(createPracticalBatchTO);									
							}
						}
			}
		//Below condition is used to display the student lists in UI in two tables
		int halfLength = 0;
		int totLength = allStudentList.size();
		if(totLength % 2 == 0) {
			halfLength = totLength / 2;
		}
		else{
			halfLength = (totLength / 2) + 1;
		}
		practicalBatchForm.setHalfLength(halfLength);
		log.info("Leaving into searchStudents of CreatePracticalBatchHandler");
		return allStudentList;
	}
	
	/**
	 * 
	 * @param practicalBatchForm
	 * @param batchTO
	 * @return Used while inserting
	 * Returns the result after inserting
	 * @throws Exception
	 */
	public boolean savePracticalBatch(CreatePracticalBatchForm practicalBatchForm, CreatePracticalBatchTO batchTO)throws Exception{
		log.info("Entering into savePracticalBatch of CreatePracticalBatchHandler");
		//Get the form properties and TO (Stored in session) and convert all to BO
		List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
		batchTO.setCreatedBy(practicalBatchForm.getUserId());
		batchTO.setModifiedBy(practicalBatchForm.getUserId());
		batchTO.setBatchName(practicalBatchForm.getBatchName());
		
		Batch batch = CreatePracticalBatchHelper.getInstance().populateTOtoBO(batchTO,studentList);
		if(batch.getBatchStudents().size()==0){
			throw new BusinessException();
		}
		if(transaction !=null){
			return transaction.savePracticalBatch(batch);
		}
		log.info("Leaving into savePracticalBatch of CreatePracticalBatchHandler");
		return false;				
	}
	/**
	 * 
	 * @param batchName
	 * @param batchTO
	 * Used in duplicate check on batch Name, subject and class
	 * @return
	 * @throws Exception
	 */
	public Batch getBatchDetailsbyBatchName(String batchName, CreatePracticalBatchTO batchTO)throws Exception{
		log.info("Entering into getBatchDetailsbyBatchName of CreatePracticalBatchHandler");
		if(transaction !=null){
			return transaction.getBatchDetailsbyBatchName(batchName, batchTO);
		}
		log.info("Leaving into getBatchDetailsbyBatchName of CreatePracticalBatchHandler");
		return new Batch();
	}
	
	/**
	 *  
	 * @param subjectId
	 * @param subGrpList
	 * @return Checks for the subjects present in subjectGroup
	 * @throws Exception
	 */
	
public boolean checkSubjectPresentInGroup(int subjectId,Set<ApplicantSubjectGroup> subGrpList) throws Exception {
	log.info("Entering into checkSubjectPresentInGroup of CreatePracticalBatchHandler");
		if(subGrpList.isEmpty()){
			return false;
		}
		boolean result = false;
		ApplicantSubjectGroup applicantSubjectGroup;
		SubjectGroupSubjects subjectGroupSubjects ;
		Iterator<ApplicantSubjectGroup> itr = subGrpList.iterator();
		label :while(itr.hasNext()) {
			applicantSubjectGroup = itr.next();
			if(applicantSubjectGroup.getSubjectGroup()!= null && applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses()!= null){
			Iterator<SubjectGroupSubjects> itr1 = applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses().iterator();
			while(itr1.hasNext()) {
				subjectGroupSubjects = itr1.next();
					if(subjectGroupSubjects.getSubject() != null && subjectGroupSubjects.getSubject().getId() == subjectId) {
						result = true;
						continue label;
					}					
			}
		}
		}
		log.info("Leaving into checkSubjectPresentInGroup of CreatePracticalBatchHandler");
		return result;		
	}
	
	/**
	 * 
	 * @param classSchemewiseId
	 * @param subjectId
	 * @return Used in edit practical batch.
	 * Works in View section to display all the practical batch details
	 * @throws Exception
	 */
	
	public List<CreatePracticalBatchTO> getPracticalBatchDetailsBySubjectClass(int classSchemewiseId, int subjectId,int activityId)throws Exception{
		log.info("Entering into getPracticalBatchDetailsBySubjectClass of CreatePracticalBatchHandler");
		if(transaction!=null){
			List<Batch> allBatchBODetailsList = transaction.getPracticalBatchDetailsBySubjectClass(classSchemewiseId, subjectId,activityId);
			if(allBatchBODetailsList!=null && !allBatchBODetailsList.isEmpty()){
				return CreatePracticalBatchHelper.getInstance().copyAllBatchBOToTO(allBatchBODetailsList);
			}
		}
		log.info("Leaving into getPracticalBatchDetailsBySubjectClass of CreatePracticalBatchHandler");
		return new ArrayList<CreatePracticalBatchTO>();
	}
	/**
	 * 
	 * @param practicalBatchForm
	 * @param batchTO
	 * Used while clicking edit button.
	 * Gets everyting based on batchId
	 * @throws Exception
	 */
	public void getBatchDetailsById(CreatePracticalBatchForm practicalBatchForm, CreatePracticalBatchTO batchTO)throws Exception{
		log.info("Entering into getBatchDetailsById of CreatePracticalBatchHandler");
		int batchId = practicalBatchForm.getBatchId();
		Batch batch = null;
		if(transaction!=null){
			batch = transaction.getBatchDetailsById(batchId);
		}
		if(batch!=null){
			practicalBatchForm.setBatchId(batch.getId());
			if(batch.getBatchName()!=null){
			practicalBatchForm.setBatchName(batch.getBatchName());
			practicalBatchForm.setOldBatchName(batch.getBatchName());
			}
			if(batch.getSubject() != null){
				practicalBatchForm.setSubjectId(String.valueOf(batch.getSubject().getId()));
			}
			if(batch.getClassSchemewise()!=null){
				practicalBatchForm.setClassSchemewiseId(String.valueOf(batch.getClassSchemewise().getId()));
			}
			if(batch.getActivity()!=null){
				practicalBatchForm.setActivityId(String.valueOf(batch.getActivity().getId()));
				practicalBatchForm.setAttendanceTypeId(String.valueOf(batch.getActivity().getAttendanceType().getId()));
			}
			//Get the assigned students to the batch
			List<Integer> assignedStudentList = CreatePracticalBatchHelper.getInstance().getAssignedStudentInEdit(batch);
			//Store the list in form. Will be use while update
			practicalBatchForm.setOldStudentList(assignedStudentList);
			
			//Get all students for the class and subject by calling the handler method
			List<CreatePracticalBatchTO> allStudentList = CreatePracticalBatchHandler.getInstance().searchStudents(practicalBatchForm,batchTO);
			//Compare the students who are already assigned among the search student list
			List<CreatePracticalBatchTO> finalStudentList = CreatePracticalBatchHelper.getInstance().checkAssignedStudent(allStudentList,assignedStudentList);
			practicalBatchForm.setStudentList(finalStudentList);			
			log.info("Leaving into getBatchDetailsById of CreatePracticalBatchHandler");
		}
	}
	/**
	 * 
	 * @param practicalBatchForm
	 * @param batchTO
	 * @return Regd No./Roll No. for student (duplicate check while creating batches)
	 * @throws Exception
	 */
	public List<StudentTO> duplicateCheckforStudent(CreatePracticalBatchForm practicalBatchForm, CreatePracticalBatchTO batchTO)throws Exception
	{
		log.info("Entering into duplicateCheckforStudent of CreatePracticalBatchHandler");
		
		int subjectId = 0;
		if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
			subjectId=batchTO.getSubjectTO().getId();
		}
		int activityId=0;
		if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
			activityId=batchTO.getActivityTO().getId();
		}
		int classSchemewiseId = batchTO.getClassSchemewiseTO().getId();
		List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
		List<Batch> batchAssignedStudentList = new ArrayList<Batch>();
		List<Integer> assignedStudentList = new ArrayList<Integer>();
		List<StudentTO>duplicateList=new ArrayList<StudentTO>();
		StudentTO studentTO = null;		
		//Send the list to helper and the selected student
		List<Integer> selectedStudentList = CreatePracticalBatchHelper.getInstance().getStudentList(studentList);
		//Get the already present students for the current classSchemewsieId and subjectId
		if(transaction != null){
		batchAssignedStudentList = transaction.getStudentsBySubjectAndClassSchemewise(subjectId, classSchemewiseId,activityId);
		}
		if(batchAssignedStudentList!=null && !batchAssignedStudentList.isEmpty()){
			assignedStudentList = CreatePracticalBatchHelper.getInstance().getBatchAssignedStudentList(batchAssignedStudentList);
		}
		
		List<Integer> finalList = new ArrayList<Integer>();
		finalList.addAll(selectedStudentList);
		finalList.addAll(assignedStudentList);
		
		Set<Integer> finalSet = new HashSet<Integer>();
		finalSet.addAll(finalList);		

		Iterator<Integer> iterator= finalList.iterator();
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		tempList.addAll(finalList);
		
		while (iterator.hasNext()) {
			Integer stuId = (Integer) iterator.next();
			if(finalSet.contains(stuId)){
				finalSet.remove(stuId);
				finalList.remove(stuId);
				iterator= finalList.iterator();
				tempList.remove(stuId);
			}
		}
			if(tempList!=null && !tempList.isEmpty()){
					Iterator<Integer> iterator1= tempList.iterator();
					String name="";
					while (iterator1.hasNext()) {
						Integer obj = (Integer) iterator1.next();
						if(selectedStudentList.contains(obj)){
						int studentId = obj.intValue();
						Student student = transaction.getStudentsById(studentId);
						String studentName = "";
						String fullName ="";						
						if(student!=null){				
							studentName = CreatePracticalBatchHelper.getInstance().getStudentName(student);
							studentTO = new StudentTO();
							if(name.equals("")){
								fullName = studentName;
							}
							else{
								fullName = "," + studentName;
							}
							
						studentTO.setStudentName(fullName);
						name = studentName;
						duplicateList.add(studentTO);
						}
						}
				}
			}
				
		if(duplicateList != null && !duplicateList.isEmpty() && duplicateList.size()==1){
			practicalBatchForm.setMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_DUPLICATE);
		}
		if(duplicateList != null && !duplicateList.isEmpty() && duplicateList.size()>1){
			practicalBatchForm.setMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_DUPLICATE_ALL);
		}		
		log.info("Leaving into duplicateCheckforStudent of CreatePracticalBatchHandler");
		return duplicateList;
	}	
	
	/**
	 * 
	 * @param batchId
	 * @param userId
	 * @return Used to delete a practical batch
	 * @throws Exception
	 */
	public boolean deletePracticalBatch(int batchId, String userId)throws Exception{
		log.info("Entering into deletePracticalBatch of CreatePracticalBatchHandler");
		if(transaction!=null){
			Batch batch = transaction.getBatchDetailsById(batchId);
			batch.setIsActive(false);
			batch.setModifiedBy(userId);
			batch.setLastModifiedDate(new Date());
			return transaction.deletePracticalBatch(batch);
		}
		log.info("Leaving into deletePracticalBatch of CreatePracticalBatchHandler");
		return false;
	}
	/**
	 * 
	 * @param batchName
	 * @param userId
	 * @param classSchemewiseId
	 * @param subjectId
	 * @return Method is used to reactivate the practical Batch
	 * @throws Exception
	 */
	public boolean reActivatePracticalBatch(String batchName,String userId,int classSchemewiseId,int subjectId,int activityId)throws Exception{
		log.info("Entering into reActivatePracticalBatch of CreatePracticalBatchHandler");
		if(transaction!=null){
			return transaction.reActivatePracticalBatch(batchName,userId,classSchemewiseId,subjectId,activityId);
		}
		log.info("Leaving into reActivatePracticalBatch of CreatePracticalBatchHandler");
		return false;
	}
	
	/**
	 * 
	 * @param practicalBatchForm
	 * @return Used while updating a practical batch
	 * @throws Exception
	 */
	
	public boolean updatePracticalBatch(CreatePracticalBatchForm practicalBatchForm)throws Exception{
		log.info("Entering into updatePracticalBatch of CreatePracticalBatchHandler");
		Batch batch = CreatePracticalBatchHelper.getInstance().poupulateTOToBOWhileUpdate(practicalBatchForm);
		if(transaction!=null){
			return transaction.updatePracticalBatch(batch);
		}
		else{
			log.info("Leaving into updatePracticalBatch of CreatePracticalBatchHandler");
			return false;
		}
	}
}
