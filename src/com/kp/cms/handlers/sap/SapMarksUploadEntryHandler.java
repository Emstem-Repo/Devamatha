package com.kp.cms.handlers.sap;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.sap.SapMarksUploadEntryForm;
import com.kp.cms.to.sap.SapMarksUploadEntryTO;
import com.kp.cms.transactions.sap.ISapMarksUploadEntryTransaction;
import com.kp.cms.transactionsimpl.sap.SapMarksUploadEntryTransactionImpl;

public class SapMarksUploadEntryHandler {
	
	ISapMarksUploadEntryTransaction transaction=SapMarksUploadEntryTransactionImpl.getInstance();
	private static volatile SapMarksUploadEntryHandler sapMarksUploadEntryHandler = null;
	private static final Log log = LogFactory.getLog(SapMarksUploadEntryHandler.class);
	private SapMarksUploadEntryHandler() {
		
	}
	
	/**
	 * @return
	 */
	public static SapMarksUploadEntryHandler getInstance() {
		if (sapMarksUploadEntryHandler == null) {
			sapMarksUploadEntryHandler = new SapMarksUploadEntryHandler();
		}
		return sapMarksUploadEntryHandler;
	}

	/**
	 * @param regNo
	 * @return
	 * @throws Exception 
	 */
	public Student getStudentDetailsByRegNo(String regNo) throws Exception{
		return transaction.getStudentDetailsByRegNo(regNo);
	}
	/**
	 * @param studentIdList
	 * @param marksEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<MarksEntryDetails> checkSapMarksAlreadyExists(List<SapMarksUploadEntryTO> marksUploadEntryTOList, SapMarksUploadEntryForm marksEntryForm) throws Exception {
		return transaction.checkSapMarksAlreadyExists(marksUploadEntryTOList,marksEntryForm);
	}
	
	/**
	 * @param marksUploadEntryTOList
	 * @param marksEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveSapUploadedData(List<SapMarksUploadEntryTO> marksUploadEntryTOList,SapMarksUploadEntryForm marksEntryForm)throws Exception 
	{
		return transaction.saveSapMarksUploaded(marksUploadEntryTOList,marksEntryForm);
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public Object[] getExamSettings() throws Exception{
		return  transaction.getExamSettingsForCheckingMarks();
		
		
	}
	/**
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getStudentCourseByExamId(String examId) throws Exception{
		
		return transaction.getStudentCourseDetailsByExamId(examId);
		
	}
}
