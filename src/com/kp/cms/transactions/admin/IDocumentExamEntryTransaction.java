package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.DocTypeExams;

public interface IDocumentExamEntryTransaction {
	//get the DocTypeExams list from database which are active
	List<DocTypeExams> getDocTypeExams() throws Exception;
	//checking the duplicate data in database
	DocTypeExams checkDuplicate(int docId, String examName) throws Exception;
	//adding the doctypexam into database
	boolean addDocExam(DocTypeExams docTypeExams) throws Exception;
	//deleting the docTypeExam in database
	boolean deleteDocExamType(int docTypeExamId, String userId) throws Exception;
	//updating docExamType in database
	boolean updateDocExamType(DocTypeExams docTypeExams) throws Exception;
	//reactivate the Doc Exam Type
	public boolean reactivateDocExamType(int olddocTypeId, String oldExamName,String userId) throws Exception; 
	//edit the Doc Exam Type using DocExamId
	DocTypeExams editDocTypeExam(String docTypeExamId) throws Exception;
}
