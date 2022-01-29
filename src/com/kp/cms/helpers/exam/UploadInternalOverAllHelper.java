package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.to.exam.UploadInternalOverAllTo;
import com.kp.cms.transactions.exam.IUploadInternalOverAllTransaction;
import com.kp.cms.transactionsimpl.exam.UploadInternalOverAllTransactionImpl;

public class UploadInternalOverAllHelper {
	/**
	 * Singleton object of UploadInternalOverAllHelper
	 */
	private static volatile UploadInternalOverAllHelper uploadInternalOverAllHelper = null;
	private static final Log log = LogFactory.getLog(UploadInternalOverAllHelper.class);
	private UploadInternalOverAllHelper() {
		
	}
	/**
	 * return singleton object of UploadInternalOverAllHelper.
	 * @return
	 */
	public static UploadInternalOverAllHelper getInstance() {
		if (uploadInternalOverAllHelper == null) {
			uploadInternalOverAllHelper = new UploadInternalOverAllHelper();
		}
		return uploadInternalOverAllHelper;
	}
	/**
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public List<ExamStudentOverallInternalMarkDetailsBO> convertToListToBOList(
			List<UploadInternalOverAllTo> result , String user,String action)  throws Exception{
		List<ExamStudentOverallInternalMarkDetailsBO> finalList=new ArrayList<ExamStudentOverallInternalMarkDetailsBO>();
		
		List<ExamStudentFinalMarkDetailsBO> eseList=new ArrayList<ExamStudentFinalMarkDetailsBO>();
		if(result!=null && !result.isEmpty()){
			Iterator<UploadInternalOverAllTo> itr=result.iterator();
			while (itr.hasNext()) {
				UploadInternalOverAllTo uploadInternalOverAllTo = (UploadInternalOverAllTo) itr.next();
				if(uploadInternalOverAllTo.getMarksType().equalsIgnoreCase("ESEOverall")){
					ExamStudentFinalMarkDetailsBO ese=new ExamStudentFinalMarkDetailsBO();
					ese.setClassId(Integer.parseInt(uploadInternalOverAllTo.getClassId()));
					ese.setExamId(Integer.parseInt(uploadInternalOverAllTo.getExamId()));
					ese.setStudentId(Integer.parseInt(uploadInternalOverAllTo.getStudentId()));
					ese.setSubjectId(Integer.parseInt(uploadInternalOverAllTo.getSubjectId()));
					ese.setStudentTheoryMarks(String.valueOf(uploadInternalOverAllTo.getTheoryMark()));
					ese.setStudentPracticalMarks(String.valueOf(uploadInternalOverAllTo.getPracticalMark()));
					ese.setCreatedBy(user);
					ese.setModifiedBy(user);
					ese.setCreatedDate(new Date());
					ese.setLastModifiedDate(new Date());
					ese.setIsActive(true);
					if(uploadInternalOverAllTo.isPass())
						ese.setPassOrFail("pass");
					else
						ese.setPassOrFail("fail");
					
					eseList.add(ese);
				}
				else{
					ExamStudentOverallInternalMarkDetailsBO bo=new ExamStudentOverallInternalMarkDetailsBO();
					bo.setStudentId(Integer.parseInt(uploadInternalOverAllTo.getStudentId()));
					bo.setSubjectId(Integer.parseInt(uploadInternalOverAllTo.getSubjectId()));
					bo.setClassId(Integer.parseInt(uploadInternalOverAllTo.getClassId()));
					bo.setExamId(Integer.parseInt(uploadInternalOverAllTo.getExamId()));
					if(action.equalsIgnoreCase("internal"))
					{	
						bo.setTheoryTotalSubInternalMarks(String.valueOf(uploadInternalOverAllTo.getTheoryMark()));
						bo.setPracticalTotalSubInternalMarks(String.valueOf(uploadInternalOverAllTo.getPracticalMark()));
						bo.setTheoryTotalMarks(String.valueOf(uploadInternalOverAllTo.getTheoryMark()));
						bo.setPracticalTotalMarks(String.valueOf(uploadInternalOverAllTo.getPracticalMark()));
					}
					else
					if(action.equalsIgnoreCase("attendance"))
					{
						bo.setTheoryTotalAttendenceMarks(String.valueOf(uploadInternalOverAllTo.getTheoryAttendance()));
						bo.setPracticalTotalAttendenceMarks(String.valueOf(uploadInternalOverAllTo.getPracticalAttendance()));
						bo.setTheoryTotalMarks(String.valueOf(uploadInternalOverAllTo.getTheoryAttendance()));
						bo.setPracticalTotalMarks(String.valueOf(uploadInternalOverAllTo.getPracticalAttendance()));
					}
					bo.setCreatedBy(user);
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(user);
					bo.setLastModifiedDate(new Date());
					bo.setIsActive(true);
					if(uploadInternalOverAllTo.isPass())
					bo.setPassOrFail("p");
					else
						bo.setPassOrFail("f");
					finalList.add(bo);
				}
			}
		}
		if(eseList!=null && !eseList.isEmpty()){
			IUploadInternalOverAllTransaction transaction=new UploadInternalOverAllTransactionImpl();
			transaction.uploadESEOverAllData(eseList);
		}
		return finalList;
	}
}
