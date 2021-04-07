package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.StudentSpecialPromotionForm;
import com.kp.cms.helpers.admission.StudentSpecialPromotionHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IStudentSpecialPromotionTransaction;
import com.kp.cms.transactionsimpl.admission.StudentSpecialPromotionTransactionImpl;

public class StudentSpecialPromotionHandler {
	/**
	 * Singleton object of StudentSpecialPromotionHandler
	 */
	private static volatile StudentSpecialPromotionHandler studentSpecialPromotionHandler = null;
	private static final Log log = LogFactory.getLog(StudentSpecialPromotionHandler.class);
	private StudentSpecialPromotionHandler() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static StudentSpecialPromotionHandler getInstance() {
		if (studentSpecialPromotionHandler == null) {
			studentSpecialPromotionHandler = new StudentSpecialPromotionHandler();
		}
		return studentSpecialPromotionHandler;
	}
	/**
	 * @param studentSpecialPromotionForm
	 * @param request
	 * @return
	 */
	public List<StudentTO> getListOfCandidates(StudentSpecialPromotionForm studentSpecialPromotionForm, HttpServletRequest request) throws Exception{
		String query=StudentSpecialPromotionHelper.getInstance().getQueryForInput(studentSpecialPromotionForm);
		IStudentSpecialPromotionTransaction transaction=StudentSpecialPromotionTransactionImpl.getInstance();
		List<Student> studentList=transaction.getStudentDetails(query);
		return StudentSpecialPromotionHelper.getInstance().convertBotoTo(studentList);
	}
	/**
	 * @param studentSpecialPromotionForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateStudentClass(StudentSpecialPromotionForm studentSpecialPromotionForm) throws Exception {
		IStudentSpecialPromotionTransaction transaction=StudentSpecialPromotionTransactionImpl.getInstance();
		return transaction.updateStudentsDetails(studentSpecialPromotionForm);
	}
	/**
	 * @param studentSpecialPromotionForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getPromotionClasses(StudentSpecialPromotionForm studentSpecialPromotionForm) throws Exception {
		IStudentSpecialPromotionTransaction transaction=StudentSpecialPromotionTransactionImpl.getInstance();
		return transaction.getPromotionClasses(studentSpecialPromotionForm.getClassId(), studentSpecialPromotionForm.getCourseId());
	}
}
