package com.kp.cms.helpers.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.exam.UploadStudentBlockForm;

public class UploadStudentBlockHelper {
	/**
	 * Singleton object of UploadStudentBlockHelper
	 */
	private static volatile UploadStudentBlockHelper uploadStudentBlockHandler = null;
	private static final Log log = LogFactory.getLog(UploadStudentBlockHelper.class);
	private UploadStudentBlockHelper() {
		
	}
	/**
	 * return singleton object of UploadStudentBlockHandler.
	 * @return
	 */
	public static UploadStudentBlockHelper getInstance() {
		if (uploadStudentBlockHandler == null) {
			uploadStudentBlockHandler = new UploadStudentBlockHelper();
		}
		return uploadStudentBlockHandler;
	}
	/**
	 * @param uploadStudentBlockForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentDetailQuery(UploadStudentBlockForm uploadStudentBlockForm) throws Exception{
		String query="";
		if (uploadStudentBlockForm.getClassIds().length > 0) {
			String [] tempArray = uploadStudentBlockForm.getClassIds();
			StringBuilder intType =new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
			
		if(uploadStudentBlockForm.getIsPreviousExam().equals("false")){
			query=query+"select s.registerNo,s.id,s.classSchemewise.classes.id from Student s where s.admAppln.isCancelled=0 and s.classSchemewise.classes.id in ("+intType+")";
		}else{
			query=query+"select e.studentUtilBO.registerNo,e.studentId,e.classId from ExamStudentPreviousClassDetailsBO e where e.classId in ("+intType+")";
		}
		}
		return query;
	}
}
