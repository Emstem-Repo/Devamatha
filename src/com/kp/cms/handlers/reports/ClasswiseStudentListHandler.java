package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.ClasswiseStudentListForm;
import com.kp.cms.helpers.reports.ClasswiseStudentListHelper;
import com.kp.cms.to.reports.ClassStudentListTO;
import com.kp.cms.transactions.reports.IClasswiseStudentListTxn;
import com.kp.cms.transactionsimpl.reports.ClasswiseStudentListTxnImpl;

public class ClasswiseStudentListHandler {

	private static final Log log = LogFactory.getLog(ClasswiseStudentListHandler.class);

	public static volatile ClasswiseStudentListHandler casswiseStudentListHandler = null;
	public static ClasswiseStudentListHandler getInstance(){
		if(casswiseStudentListHandler == null){
			casswiseStudentListHandler = new ClasswiseStudentListHandler();  
		}
		return casswiseStudentListHandler;
	}	

	/**
	 * 
	 * @param studentListForSignatureForm
	 * @return
	 * @throws Exception
	 */
	public List<ClassStudentListTO> getStudentList(ClasswiseStudentListForm stListForm) throws Exception{
		log.debug("inside getStudentList");
		String searchCriteria = ClasswiseStudentListHelper.getInstance().getSearchCriteria(stListForm);
		IClasswiseStudentListTxn tran = ClasswiseStudentListTxnImpl.getInstance(); 
		List<Student> studentList = tran.getStudentList(searchCriteria);
		List<ClassStudentListTO> clasStudentToList = ClasswiseStudentListHelper.getInstance().copyBosToTO(studentList, stListForm);
		log.debug("exit getStudentList");
		return clasStudentToList;
		
	}	
	
}
