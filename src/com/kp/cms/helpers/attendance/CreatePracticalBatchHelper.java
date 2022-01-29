package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.CreatePracticalBatchForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.BatchStudentTO;
import com.kp.cms.to.attendance.ClassSchemewiseTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;
import com.kp.cms.transactions.attandance.ICreatePracticalBatchTransaction;
import com.kp.cms.transactionsimpl.attendance.CreatePracticalBatchTransactionImpl;


public class CreatePracticalBatchHelper {
	
	private static final Log log = LogFactory.getLog(CreatePracticalBatchHelper.class);
	
private static volatile CreatePracticalBatchHelper createPracticalBatchHelper = null;
	
	private CreatePracticalBatchHelper() {
	}
	/**
	 * 
	 * @returns a single instance when called (Singleton implementation)
	 */
	public static CreatePracticalBatchHelper getInstance() {
		if (createPracticalBatchHelper == null) {
			createPracticalBatchHelper = new CreatePracticalBatchHelper();
		}
		return createPracticalBatchHelper;
	}
	
	/**
	 * 
	 * @param batchTO
	 * @param studentList
	 * Used in inserting students into batch
	 * Poupulates TO to BO object
	 * @return
	 */
	
	public Batch populateTOtoBO(CreatePracticalBatchTO batchTO, List<CreatePracticalBatchTO> studentList)throws Exception{
		log.info("Entering into populateTOtoBO of CreatePracticalBatchHelper");
		Batch batch = null;
		ClassSchemewise classSchemewise;
		Subject subject;
		Student student;
		Activity activity;
		CreatePracticalBatchTO createPracticalBatchTO;
		BatchStudent batchStudent;
		Set<BatchStudent> batchStudentSet =new HashSet<BatchStudent>(); 
		if(batchTO!=null && studentList!=null){
				batch = new Batch();
				classSchemewise = new ClassSchemewise();
				classSchemewise.setId(batchTO.getClassSchemewiseTO().getId());
				batch.setClassSchemewise(classSchemewise);
				if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
					subject = new Subject();
					subject.setId(batchTO.getSubjectTO().getId());
					batch.setSubject(subject);
				}
				if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
					activity=new Activity();
					activity.setId(batchTO.getActivityTO().getId());
					batch.setActivity(activity);
				}
				batch.setBatchName(batchTO.getBatchName());
				batch.setIsActive(true);
				batch.setCreatedBy(batchTO.getCreatedBy());
				batch.setModifiedBy(batchTO.getCreatedBy());
				batch.setCreatedDate(new Date());
				batch.setLastModifiedDate(new Date());		
			
		Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
			while (iterator.hasNext()) {
			createPracticalBatchTO = iterator.next();
				if(createPracticalBatchTO.isCheckValue()){
					student = new Student();
					batchStudent = new BatchStudent();
					batchStudent.setCreatedBy(batchTO.getCreatedBy());
					batchStudent.setModifiedBy(batchTO.getCreatedBy());
					batchStudent.setCreatedDate(new Date());
					batchStudent.setLastModifiedDate(new Date());
					batchStudent.setIsActive(true);
					student.setId(createPracticalBatchTO.getStudentTO().getId());
					batchStudent.setStudent(student);
					batchStudentSet.add(batchStudent);
				}				
			}
		batch.setBatchStudents(batchStudentSet);	
		}
		log.info("Leaving into populateTOtoBO of CreatePracticalBatchHelper");
		return batch;
	}
	/**
	 * 
	 * @param allBatchBODetailsList
	 * Used in view part. Displays all batch for the class and subject details in UI
	 * Used in edit practical batch section
	 * @return
	 */
	public List<CreatePracticalBatchTO> copyAllBatchBOToTO(List<Batch> allBatchBODetailsList)throws Exception{
		log.info("Entering into copyAllBatchBOToTO of CreatePracticalBatchHelper");
		List<CreatePracticalBatchTO> batchTOList=new ArrayList<CreatePracticalBatchTO>();
		CreatePracticalBatchTO batchTO;
		BatchStudentTO batchStudentTO;
		StudentTO studentTO;
		ClassesTO classesTO;
		SubjectTO subjectTO;
		ClassSchemewiseTO classSchemewiseTO;
		Batch batch;
		BatchStudent batchStudent;
		Set<BatchStudent> batchStudentBOSet;
				
		if(allBatchBODetailsList !=null && !allBatchBODetailsList.isEmpty()){
			Iterator<Batch> iterator=allBatchBODetailsList.iterator();
			while (iterator.hasNext()) {
				batch = iterator.next();				
				batchTO = new CreatePracticalBatchTO();
				batchTO.setId(batch.getId());
				batchTO.setBatchName(batch.getBatchName()!=null ? batch.getBatchName():null);
				
				classSchemewiseTO = new ClassSchemewiseTO();
				if(batch.getClassSchemewise()!=null && batch.getClassSchemewise().getId()!=0){
				classSchemewiseTO.setId(batch.getClassSchemewise().getId());
				}
				
				classesTO = new ClassesTO();
				if(batch.getClassSchemewise()!=null && batch.getClassSchemewise().getClasses()!=null && batch.getClassSchemewise().getClasses().getName()!=null){
				classesTO.setClassName(batch.getClassSchemewise().getClasses().getName());
				}
				classSchemewiseTO.setClassesTo(classesTO);
				batchTO.setClassSchemewiseTO(classSchemewiseTO);
				
				subjectTO = new SubjectTO();
				if(batch.getSubject()!=null && batch.getSubject().getId()!=0){
				subjectTO.setId(batch.getSubject().getId());
				}
				if(batch.getSubject()!=null && batch.getSubject().getName()!=null){
				subjectTO.setName(batch.getSubject().getName());
				}
				batchTO.setSubjectTO(subjectTO);
				
				batchStudentBOSet = batch.getBatchStudents();
				List<BatchStudentTO> batchStudentTOList = new ArrayList<BatchStudentTO>();
				if(batchStudentBOSet!=null && !batchStudentBOSet.isEmpty()){
					Iterator<BatchStudent> iterator1 = batchStudentBOSet.iterator();
					String name = "";
					while (iterator1.hasNext()) {
						batchStudent = iterator1.next();
						if(batchStudent.getIsActive()!=null && batchStudent.getIsActive()){
						batchStudentTO = new BatchStudentTO();
						batchStudentTO.setId(batchStudent.getId());
						
						studentTO = new StudentTO();
						
						StringBuffer studentName = new StringBuffer();
						String studentFullName = "";
						
						if(batchStudent.getStudent()!=null && batchStudent.getStudent().getIsActive()!=null && 
						batchStudent.getStudent().getIsActive() && batchStudent.getStudent().getIsAdmitted()!=null
						&& batchStudent.getStudent().getIsAdmitted() && batchStudent.getStudent().getAdmAppln()!=null &&
						!batchStudent.getStudent().getAdmAppln().getIsCancelled()){
						
						studentTO.setId(batchStudent.getStudent().getId());

						if(	batchStudent.getStudent().getAdmAppln().getPersonalData()!=null && batchStudent.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null){
							studentName = studentName.append(batchStudent.getStudent().getAdmAppln().getPersonalData().getFirstName());
						}
						if(batchStudent.getStudent().getAdmAppln().getPersonalData()!=null && batchStudent.getStudent().getAdmAppln().getPersonalData().getMiddleName()!=null){
							studentName = studentName.append(" ").append(batchStudent.getStudent().getAdmAppln().getPersonalData().getMiddleName());
						}
						if(batchStudent.getStudent().getAdmAppln().getPersonalData()!=null && batchStudent.getStudent().getAdmAppln().getPersonalData().getLastName()!=null){
							studentName = studentName.append(" ").append(batchStudent.getStudent().getAdmAppln().getPersonalData().getLastName());
						}
						if(name.equals("")){
							studentFullName = studentName.toString();
						}
						else{
							studentFullName = ","+ studentName.toString();
						}
						studentTO.setStudentName(studentFullName);
						name = studentFullName;
						
						batchStudentTO.setStudentTO(studentTO);
						batchStudentTOList.add(batchStudentTO);
						}
					}
				}
				}
				batchTO.setBatchStudentTOList(batchStudentTOList);
				batchTOList.add(batchTO);
			}
		}
		log.info("Leaving into copyAllBatchBOToTO of CreatePracticalBatchHelper");
		return batchTOList;
	}
	/**
	 * 
	 * @param studentList
	 * Used while duplication check for regd no/roll no.
	 * @return
	 */
	public List<Integer> getStudentList(List<CreatePracticalBatchTO> studentList)throws Exception{
		log.info("Entering into getStudentList of CreatePracticalBatchHelper");
		CreatePracticalBatchTO createPracticalBatchTO;
		List<Integer> selectedStudentList = new ArrayList<Integer>();		
		if(studentList!=null && !studentList.isEmpty()){
		Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
		while (iterator.hasNext()) {
		createPracticalBatchTO = iterator.next();
			if(createPracticalBatchTO.isCheckValue()){
				selectedStudentList.add(createPracticalBatchTO.getStudentTO().getId());
			}				
		}
		}
		log.info("Leaving into getStudentList of CreatePracticalBatchHelper");
		return selectedStudentList;
	}
	/**
	 * 
	 * @param batchAssignedStudentList
	 * @return
	 * Used to get already batch assigned students for a classschemewiseId and subjectID
	 */
	public List<Integer> getBatchAssignedStudentList(List<Batch> batchAssignedStudentList)throws Exception{
		log.info("Entering into getBatchAssignedStudentList of CreatePracticalBatchHelper");
		List<Integer> assignedStudentList = new ArrayList<Integer>();
		Batch batch = null;
		BatchStudent batchStudent = null;		
		if(batchAssignedStudentList!=null && !batchAssignedStudentList.isEmpty()){
			Iterator<Batch>	iterator = batchAssignedStudentList.iterator();
			while (iterator.hasNext()) {
				batch = iterator.next();
				 Set<BatchStudent> batchStudentBOSet = batch.getBatchStudents();
				if(batchStudentBOSet!=null && !batchStudentBOSet.isEmpty()){
					Iterator<BatchStudent> iterator2 = batchStudentBOSet.iterator();
					while (iterator2.hasNext()) {
						batchStudent = iterator2.next();
						if(batchStudent.getStudent()!=null && batchStudent.getIsActive()
						&& batchStudent.getStudent().getIsActive()!= null && batchStudent.getStudent().getIsActive()){
						assignedStudentList.add(batchStudent.getStudent().getId());
						}
					}					
				}				
			}
		}
		log.info("Leaving into getBatchAssignedStudentList of CreatePracticalBatchHelper");
		return assignedStudentList;
	}
	/**
	 * 
	 * @param student
	 * Used to get student names
	 * @return
	 */
	public String getStudentName(Student student)throws Exception
	{
		log.info("Entering into getStudentName of CreatePracticalBatchHelper");
		StringBuffer buffer = new StringBuffer();
		if(student!=null && student.getAdmAppln()!=null && student.getAdmAppln().getPersonalData()!=null){
			if(student.getAdmAppln().getPersonalData().getFirstName()!=null){
				buffer = buffer.append(student.getAdmAppln().getPersonalData().getFirstName()); 
			}
			if(student.getAdmAppln().getPersonalData().getMiddleName()!=null){
				buffer = buffer.append(" ").append(student.getAdmAppln().getPersonalData().getMiddleName());
			}
			if(student.getAdmAppln().getPersonalData().getLastName()!=null){
				buffer = buffer.append(" ").append(student.getAdmAppln().getPersonalData().getLastName());
			}
		}
		log.info("Leaving into getStudentName of CreatePracticalBatchHelper");
		return buffer.toString();
	}
	
	/**
	 * 
	 * @param batch
	 * Used to get the assigned students to the batch while editing
	 * @return
	 */
	public List<Integer> getAssignedStudentInEdit(Batch batch)throws Exception{
		log.info("Entering into getAssignedStudentInEdit of CreatePracticalBatchHelper");
		List<Integer> assignedStudentList = new ArrayList<Integer>();
		Set<BatchStudent> batchStudentSet = batch.getBatchStudents();
		BatchStudent batchStudent;
		if(batchStudentSet!=null && !batchStudentSet.isEmpty()){
			Iterator<BatchStudent> it = batchStudentSet.iterator();
			while (it.hasNext()) {
				batchStudent = it.next();
				if(batchStudent.getIsActive()!=null && batchStudent.getIsActive()&& batchStudent.getStudent()!=null && 
				batchStudent.getStudent().getId()!=0 && batchStudent.getStudent().getIsAdmitted() && batchStudent.getStudent().getAdmAppln()!= null 
				&& batchStudent.getStudent().getAdmAppln().getIsCancelled()!= null && !batchStudent.getStudent().getAdmAppln().getIsCancelled()){
					assignedStudentList.add(batchStudent.getStudent().getId());
				}
			}
		}
		log.info("Leaving into getAssignedStudentInEdit of CreatePracticalBatchHelper");
		return assignedStudentList;
	}
	/**
	 * 
	 * @param allStudentList
	 * Used in edit
	 * While checking if the student is already present in the searched students list
	 * @param assignedStudentList
	 * @return
	 */
	public List<CreatePracticalBatchTO> checkAssignedStudent(List<CreatePracticalBatchTO> allStudentList,List<Integer> assignedStudentList)throws Exception{
		log.info("Entering into checkAssignedStudent of CreatePracticalBatchHelper");
		CreatePracticalBatchTO createPracticalBatchTO;		
		if(allStudentList!=null && !allStudentList.isEmpty()){
			Iterator<CreatePracticalBatchTO> iterator = allStudentList.iterator();
			while (iterator.hasNext()) {
				createPracticalBatchTO = iterator.next();
				
				if(createPracticalBatchTO.getStudentTO()!=null && 
				assignedStudentList.contains(Integer.valueOf(createPracticalBatchTO.getStudentTO().getId()))){
					createPracticalBatchTO.setDummyCheckValue(true);
					createPracticalBatchTO.setTempCheckValue(true);
					createPracticalBatchTO.setCheckValue(false);
				}
				else{
					createPracticalBatchTO.setDummyCheckValue(false);
					createPracticalBatchTO.setTempCheckValue(false);
					createPracticalBatchTO.setCheckValue(false);
				}
				
			}
		}
		log.info("Leaving into checkAssignedStudent of CreatePracticalBatchHelper");
		return allStudentList;
	}	
	/**
	 * 
	 * @param practicalBatchForm
	 * Used to convert To to BO while updating
	 * @return
	 */
	public Batch poupulateTOToBOWhileUpdate(CreatePracticalBatchForm practicalBatchForm)throws Exception{
		log.info("Entering into poupulateTOToBOWhileUpdate of CreatePracticalBatchHelper");
		Batch batch = null;
		CreatePracticalBatchTO createPracticalBatchTO;
		ClassSchemewise classSchemewise;
		Subject subject;
		Student student;
		Activity activity;
		BatchStudent batchStudent;
		List<Integer> newStudentList = new ArrayList<Integer>();
		
		Set<BatchStudent> batchStudentSet =new HashSet<BatchStudent>(); 		
		List<CreatePracticalBatchTO> studentList = practicalBatchForm.getStudentList();
		List<Integer> oldStudentList = practicalBatchForm.getOldStudentList();
		if(studentList!=null && !studentList.isEmpty()){
			batch= new Batch();
			batch.setId(practicalBatchForm.getBatchId());
			batch.setBatchName(practicalBatchForm.getBatchName());

			classSchemewise = new ClassSchemewise();
			classSchemewise.setId(Integer.parseInt(practicalBatchForm.getClassSchemewiseId()));
			batch.setClassSchemewise(classSchemewise);
			if(practicalBatchForm.getSubjectId()!=null && !practicalBatchForm.getSubjectId().isEmpty()){
			subject = new Subject();
			subject.setId(Integer.parseInt(practicalBatchForm.getSubjectId()));
			batch.setSubject(subject);
			}
			
			if(practicalBatchForm.getActivityId()!=null && !practicalBatchForm.getActivityId().isEmpty()){
				activity=new Activity();
				activity.setId(Integer.parseInt(practicalBatchForm.getActivityId()));
				batch.setActivity(activity);
			}
			
			batch.setModifiedBy(practicalBatchForm.getUserId());
			batch.setLastModifiedDate(new Date());
			batch.setIsActive(true);
			
			Iterator<CreatePracticalBatchTO> iterator = studentList.iterator();
			while (iterator.hasNext()) {
			createPracticalBatchTO = iterator.next();
			//Keeps the record in previous state as active
				if(createPracticalBatchTO.isCheckValue() && 
				oldStudentList.contains(Integer.valueOf(createPracticalBatchTO.getStudentTO().getId()))){
					student = new Student();
					batchStudent = new BatchStudent();					
					batchStudent.setModifiedBy(practicalBatchForm.getUserId());
					batchStudent.setLastModifiedDate(new Date());
					batchStudent.setIsActive(true);
					if(createPracticalBatchTO.getBatchStudentId()!=0){
					batchStudent.setId(createPracticalBatchTO.getBatchStudentId());
					}
					student.setId(createPracticalBatchTO.getStudentTO().getId());
					
					batchStudent.setStudent(student);
					batchStudentSet.add(batchStudent);
				}	
				//Keeps the record in previous state as inactive
				if(!createPracticalBatchTO.isCheckValue() && 
				oldStudentList.contains(Integer.valueOf(createPracticalBatchTO.getStudentTO().getId()))){
					student = new Student();
					batchStudent = new BatchStudent();					
					batchStudent.setModifiedBy(practicalBatchForm.getUserId());
					batchStudent.setLastModifiedDate(new Date());
					batchStudent.setIsActive(false);
						if(createPracticalBatchTO.getBatchStudentId()!=0){
						batchStudent.setId(createPracticalBatchTO.getBatchStudentId());
						}
					student.setId(createPracticalBatchTO.getStudentTO().getId());
					batchStudent.setStudent(student);
					batchStudentSet.add(batchStudent);
				}
				//Inserts new record (Makes isactive true)
				if(createPracticalBatchTO.isCheckValue() && 
				!oldStudentList.contains(Integer.valueOf(createPracticalBatchTO.getStudentTO().getId()))){
					student = new Student();
					batchStudent = new BatchStudent();					
					batchStudent.setModifiedBy(practicalBatchForm.getUserId());
					batchStudent.setLastModifiedDate(new Date());
					batchStudent.setIsActive(true);
					student.setId(createPracticalBatchTO.getStudentTO().getId());
					batchStudent.setStudent(student);
					batchStudentSet.add(batchStudent);
					if(createPracticalBatchTO.getBatchStudentId()!=0){
						batchStudent.setId(createPracticalBatchTO.getBatchStudentId());
					}
					//Add the student ids to the list which is used to check for duplicate for the student
					newStudentList.add(createPracticalBatchTO.getStudentTO().getId());
				}	
			}
			
		batch.setBatchStudents(batchStudentSet);
		}
		//Call the method to check for the duplicate students while update (If the new students are exiting for some other batches of same class and subject)
		List<StudentTO> dupList = checkDuplicateStudentWhileUpdate(newStudentList, batch, practicalBatchForm);
		if(!dupList.isEmpty()){
			practicalBatchForm.setExistingStudentList(dupList);
			if(!dupList.isEmpty() && dupList.size()==1){
				practicalBatchForm.setMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_DUPLICATE);
			}
			if(!dupList.isEmpty() && dupList.size()>1){
				practicalBatchForm.setMessage(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_BATCH_DUPLICATE_ALL);
			}
			throw new BusinessException();
		}		
		log.info("Leaving into poupulateTOToBOWhileUpdate of CreatePracticalBatchHelper");
		return batch;
	}
	
	/**
	 * Used for duplicate check of students while update
	 */
	
	public List<StudentTO> checkDuplicateStudentWhileUpdate(List<Integer> newStudentList, Batch batch, CreatePracticalBatchForm practicalBatchForm)throws Exception{
		log.info("Entering into checkDuplicateStudentWhileUpdate of CreatePracticalBatchHelper");
		//Below condition is used to check the duplicate for students.
		//From the new students selected while update if available for any batch of that class and subject then throw exception
		List<StudentTO> tempList = new ArrayList<StudentTO>();		
		if(newStudentList!=null && !newStudentList.isEmpty()){
			Set<BatchStudent> batchStSet;
			BatchStudent batchStudent2 = null;
			StudentTO studentTO = null;
			
			List<StudentTO> studentList = new ArrayList<StudentTO>();
			ICreatePracticalBatchTransaction transaction = new CreatePracticalBatchTransactionImpl();
			int subjectId=0;
			if(practicalBatchForm.getSubjectId()!=null && !practicalBatchForm.getSubjectId().isEmpty()){
				subjectId=Integer.parseInt(practicalBatchForm.getSubjectId());
			}
			
			int activityId=0;
			if(practicalBatchForm.getActivityId()!=null && !practicalBatchForm.getActivityId().isEmpty()){
				activityId=Integer.parseInt(practicalBatchForm.getActivityId());
			}
			List<Batch> batchAssignedStudentList  = transaction.getStudentsBySubjectAndClassSchemewise(subjectId, Integer.parseInt(practicalBatchForm.getClassSchemewiseId()),activityId);
			
			if(batchAssignedStudentList!=null && !batchAssignedStudentList.isEmpty()){
				Iterator<Batch> iterator = batchAssignedStudentList.iterator();
				while (iterator.hasNext()) {
					batch = iterator.next();
					batchStSet = batch.getBatchStudents();
					if(batchStSet!=null && !batchStSet.isEmpty()){
						Iterator<BatchStudent> iterator2= batchStSet.iterator();
						String name="";
						while (iterator2.hasNext()) {
							batchStudent2 = iterator2.next();
							
							if(batchStudent2.getIsActive()!=null && batchStudent2.getIsActive() && 
							batchStudent2.getStudent()!=null && batchStudent2.getStudent().getIsActive()!=null && batchStudent2.getStudent().getIsActive()){
								studentTO = new StudentTO();
								StringBuffer studentName = new StringBuffer();
								String fullName ="";
								studentTO.setId(batchStudent2.getStudent().getId());
								if(batchStudent2.getStudent().getAdmAppln()!=null && batchStudent2.getStudent().getAdmAppln().getPersonalData()!=null){
									if(batchStudent2.getStudent().getAdmAppln()!=null && batchStudent2.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null){
										studentName = studentName.append(batchStudent2.getStudent().getAdmAppln().getPersonalData().getFirstName());
									}
									if(batchStudent2.getStudent().getAdmAppln()!=null && batchStudent2.getStudent().getAdmAppln().getPersonalData().getMiddleName()!=null){
										studentName = studentName.append(" ").append(batchStudent2.getStudent().getAdmAppln().getPersonalData().getMiddleName());
									}
									if(batchStudent2.getStudent().getAdmAppln()!=null && batchStudent2.getStudent().getAdmAppln().getPersonalData().getLastName()!=null){
										studentName = studentName.append(" ").append(batchStudent2.getStudent().getAdmAppln().getPersonalData().getLastName());
									}
								}
								if(name.equals("")){
									fullName = studentName.toString();
								}
								else{
									fullName = "," + studentName.toString();
								}
								studentTO.setStudentName(fullName);
								name = studentName.toString();
								studentList.add(studentTO);
							}
						}
					}
				}
			}
			
			if(studentList!=null && !studentList.isEmpty()){
				StudentTO studentTO2 = null;
				
				Iterator<StudentTO> iterator = studentList.iterator();
				while (iterator.hasNext()) {
					studentTO2 = iterator.next();
					if(newStudentList.contains(Integer.valueOf(studentTO2.getId()))){
					tempList.add(studentTO2);
						studentList.remove(studentTO2);
						iterator = studentList.iterator();
					}
					
				}				
			}			
			
		}	
		log.info("Leaving into checkDuplicateStudentWhileUpdate of CreatePracticalBatchHelper");
		return tempList;
	}
}
