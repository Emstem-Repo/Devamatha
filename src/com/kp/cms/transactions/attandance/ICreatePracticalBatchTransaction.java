package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;

/**
 * @author kshirod.k
 * Transaction interface for CreatePracticalBatch
 *
 */
public interface ICreatePracticalBatchTransaction {
	
	/**
	 * Used to get SubjectGroupIds
	 */
	public List<SubjectGroupSubjects> getSubjectGroupDetails(int subjectId)throws Exception;
	
	/**
	 * Used to get Student List based on the classSchemewiseId
	 */
	
	public List<Student> getStudentList(int classSchemewiseId)throws Exception;
	
	/**
	 * Used to get regd nos within a range entered in UI
	 */
	
	public List<String> getRequiredRegdNos(int classSchemewiseId, String regNoFrom,String regNoTo)throws Exception;

	/**
	 * Used while saving
	 */
	public boolean savePracticalBatch(Batch batch)throws Exception;
	/**
	 * Used while duplicate check on batchName
	 */
	public Batch getBatchDetailsbyBatchName(String batchName, CreatePracticalBatchTO batchTO)throws Exception;
	
	/**
	 * Used to get roll nos within a range entered in UI
	 */
	
	public List<String> getRequiredRollNos(int classSchemewiseId, String regNoFrom,String regNoTo)throws Exception;
	
	/**
	 * Used to view all the practical batch details.
	 * Used in edit practical batch section
	 */
	public List<Batch> getPracticalBatchDetailsBySubjectClass(int classSchemewiseId, int subjectId,int activityId)throws Exception;
	
	/**
	 * Used while editing
	 * Gets batch details based on the batchId
	 */
	public Batch getBatchDetailsById(int batchId)throws Exception;
	
	/**
	 * Used for duplication check of students
	 */
	public List<Batch> getStudentsBySubjectAndClassSchemewise(int subjectId, int classSchemewiseId,int activityId)throws Exception;
	
	/**
	 * Get student names by ID
	 */
	public Student getStudentsById(int id)throws Exception;
	
	/**
	 * Used to delete a practical batch
	 */
	public boolean deletePracticalBatch(Batch batch)throws Exception;
	
	/**
	 * Method is used to reactivate practical bacth
	 * 
	 */
	public boolean reActivatePracticalBatch(String batchName,String userId,int classSchemewiseId,int subjectId,int activityId)throws Exception;
	
	/**
	 * Used to update a practical batch
	 */
	public boolean updatePracticalBatch(Batch batch)throws Exception;
	}
