package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.CategoryWiseStudentForm;
import com.kp.cms.helpers.reports.CategoryWiseStudentHelper;
import com.kp.cms.to.admin.CategoryWithStudentsTO;
import com.kp.cms.transactions.reports.ICategoryWiseStudentTransaction;
import com.kp.cms.transactionsimpl.reports.CategoryWiseStudentTransactionImpl;

public class CategoryWiseStudentHandler {
	private static final Log log = LogFactory.getLog(CategoryWiseStudentHandler.class);
	public static volatile CategoryWiseStudentHandler self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static CategoryWiseStudentHandler getInstance(){
		if(self==null)
			self= new CategoryWiseStudentHandler();
		return self;
	}
	/**
	 * Private constructor for a singleton class
	 */
	private CategoryWiseStudentHandler(){		
	}
	
	/**
	 * 
	 * @param studentForm
	 * @returns students in categorywise
	 * @throws Exception
	 */
	public List<CategoryWithStudentsTO> getStudents(CategoryWiseStudentForm studentForm)throws Exception{
		log.info("Entering in getStudents of CategoryWiseStudentHandler");
		ICategoryWiseStudentTransaction transaction = new CategoryWiseStudentTransactionImpl();		
		List<Student> studentBOList = transaction.getStudents(CategoryWiseStudentHelper.getInstance().getAdmittedStudents(studentForm));
		log.info("Leaving into getStudents of CategoryWiseStudentHandler");
		return CategoryWiseStudentHelper.getInstance().copyStudentBOToTO(studentBOList,studentForm);
	}
}
