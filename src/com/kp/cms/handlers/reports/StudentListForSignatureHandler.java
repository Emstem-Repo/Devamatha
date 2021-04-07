package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.StudentListForSignatureForm;
import com.kp.cms.helpers.reports.StudentListForSignatureHelper;
import com.kp.cms.to.reports.StudentListForSignatureTO;
import com.kp.cms.transactions.reports.IStudentListForSignatureTransaction;
import com.kp.cms.transactionsimpl.reports.StudentListForSignatureTransactionImpl;

public class StudentListForSignatureHandler {
	private static final Log log = LogFactory.getLog(StudentListForSignatureHandler.class);

	public static volatile StudentListForSignatureHandler studentListForSignatureHandler = null;
	public static StudentListForSignatureHandler getInstance(){
		if(studentListForSignatureHandler == null){
			studentListForSignatureHandler = new StudentListForSignatureHandler();  
		}
		return studentListForSignatureHandler;
	}	

	/**
	 * 
	 * @param studentListForSignatureForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentListForSignatureTO> getStudentListForSignature(StudentListForSignatureForm studentListForSignatureForm) throws Exception{
		log.debug("inside getStudentListForSignature");
		IStudentListForSignatureTransaction tran = StudentListForSignatureTransactionImpl.getInstance(); 
		List<Student> studentList = tran.getStudentList(studentListForSignatureForm);
		List<StudentListForSignatureTO> studentListToList = StudentListForSignatureHelper.getInstance().copyBosToTO(studentList, studentListForSignatureForm);
		log.debug("exit getStudentListForSignature");
		return studentListToList;
		
	}	

}
