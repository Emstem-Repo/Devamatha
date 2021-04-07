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

public class UploadExamStudentFinalMarksHelper {
	/**
	 * Singleton object of UploadExamStudentFinalMarksHelper
	 */
	private static volatile UploadExamStudentFinalMarksHelper uploadExamStudentFinalMarksHelper = null;
	private static final Log log = LogFactory.getLog(UploadExamStudentFinalMarksHelper.class);
	private UploadExamStudentFinalMarksHelper() {
		
	}
	/**
	 * return singleton object of UploadExamStudentFinalMarksHelper.
	 * @return
	 */
	public static UploadExamStudentFinalMarksHelper getInstance() {
		if (uploadExamStudentFinalMarksHelper == null) {
			uploadExamStudentFinalMarksHelper = new UploadExamStudentFinalMarksHelper();
		}
		return uploadExamStudentFinalMarksHelper;
	}
	public List<ExamStudentFinalMarkDetailsBO> convertToListToBOList(
			List<UploadInternalOverAllTo> result, String user, String action) throws Exception {
		List<ExamStudentFinalMarkDetailsBO> finalList=new ArrayList<ExamStudentFinalMarkDetailsBO>();
		if(result!=null && !result.isEmpty()){
			Iterator<UploadInternalOverAllTo> itr=result.iterator();
			while (itr.hasNext()) {
				UploadInternalOverAllTo uploadInternalOverAllTo = (UploadInternalOverAllTo) itr.next();
				ExamStudentFinalMarkDetailsBO bo=new ExamStudentFinalMarkDetailsBO();
				bo.setStudentId(Integer.parseInt(uploadInternalOverAllTo.getStudentId()));
				bo.setSubjectId(Integer.parseInt(uploadInternalOverAllTo.getSubjectId()));
				bo.setClassId(Integer.parseInt(uploadInternalOverAllTo.getClassId()));
				bo.setExamId(Integer.parseInt(uploadInternalOverAllTo.getExamId()));
				bo.setStudentTheoryMarks(String.valueOf(uploadInternalOverAllTo.getTheoryMark()));
				bo.setStudentPracticalMarks(String.valueOf(uploadInternalOverAllTo.getPracticalMark()));
				bo.setCreatedBy(user);
				bo.setCreatedDate(new Date());
				bo.setModifiedBy(user);
				bo.setLastModifiedDate(new Date());
				bo.setIsActive(true);
				if(uploadInternalOverAllTo.isPass())
				bo.setPassOrFail("pass");
				else
					bo.setPassOrFail("fail");
				finalList.add(bo);
			}
		}
		return finalList;
	}
	
}
