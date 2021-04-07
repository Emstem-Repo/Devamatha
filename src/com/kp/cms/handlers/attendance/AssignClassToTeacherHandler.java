package com.kp.cms.handlers.attendance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.attendance.AssignClassToTeacherForm;
import com.kp.cms.helpers.attendance.AssignClassToTeacherHelper;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.transactions.admin.IDepartmentEntryTransaction;
import com.kp.cms.transactions.attandance.IAssignClassToTeacherTransaction;
import com.kp.cms.transactionsimpl.admin.DepartmentEntryTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.AssignClassToTeacherTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class AssignClassToTeacherHandler {
	 private static final Log log = LogFactory.getLog(AssignClassToTeacherHandler.class);
    public static AssignClassToTeacherHandler assignClassToTeacherHandler=null;
    static{
    		assignClassToTeacherHandler=new AssignClassToTeacherHandler();
    	}
    public static AssignClassToTeacherHandler getInstance(){
    	return assignClassToTeacherHandler;
	}
    /**
     * @param year
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByYear(int year) throws Exception {
    	IAssignClassToTeacherTransaction txImpl=AssignClassToTeacherTransactionImpl.getInstance();
		Map<Integer, String> classMap = txImpl.getClassesByYear(year);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
		
	}
	/**
	 * @param assignClassTeacherForm
	 * @return
	 * @throws Exception 
	 */
	public boolean addTeachers(AssignClassToTeacherForm assignClassTeacherForm) throws Exception {
		boolean IsAdded=false;
		log.info("call of addTeachers method in AssignClassToTeacherHandler class.");
		IAssignClassToTeacherTransaction txImpl=AssignClassToTeacherTransactionImpl.getInstance();
		IsAdded=txImpl.addTeachers(assignClassTeacherForm);
		 log.info("end of addTeachers method in AssignClassToTeacherHandler class.");
		return IsAdded;
	}
	
	public void checkDuplicate(AssignClassToTeacherForm assignClassTeacherForm) throws Exception {
		IAssignClassToTeacherTransaction transaction = new AssignClassToTeacherTransactionImpl();
		String query=AssignClassToTeacherHelper.getInstance().getDuplicateEntryQuery(assignClassTeacherForm);
		boolean flag= transaction.getDuplicates(query,assignClassTeacherForm);	
		if(flag){
			throw new DuplicateException("duplicateEntry");	
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TeacherClassEntryTo> getDetailsByYear(AssignClassToTeacherForm assignClassTeacherForm,int year) throws Exception {
		log.debug("inside handler getDetails");
		IAssignClassToTeacherTransaction txImpl=AssignClassToTeacherTransactionImpl.getInstance();
		List<TeacherClassEntryTo> teacherList=AssignClassToTeacherHelper.getInstance().ConvertBOToTO(txImpl.getDetailsByYear(assignClassTeacherForm,  year));
		log.debug("leaving handler getDetails");
		return teacherList;
	}
	
	@SuppressWarnings("unchecked")
	public List<TeacherClassEntryTo> getDetails(
			AssignClassToTeacherForm assignClassTeacherForm) throws Exception {
		log.debug("inside handler getDetails");
		IAssignClassToTeacherTransaction txImpl=AssignClassToTeacherTransactionImpl.getInstance();
		List<TeacherClassEntryTo> teacherList=AssignClassToTeacherHelper.getInstance().ConvertBOToTO(txImpl.getDetails(assignClassTeacherForm));
		log.debug("leaving handler getDetails");
		return teacherList;
	}
	public boolean deleteTeachers(int id) throws Exception {
		boolean IsDeleted=false;
		log.info("call of deleteTeachers method in AssignClassToTeacherHandler class.");
		IAssignClassToTeacherTransaction txImpl=AssignClassToTeacherTransactionImpl.getInstance();
		IsDeleted=txImpl.deleteTeachers(id);
		return IsDeleted;
	}
	public void editClassEntry(AssignClassToTeacherForm aTeacherForm) throws Exception{
		IAssignClassToTeacherTransaction transaction=new AssignClassToTeacherTransactionImpl();
		TeacherClass teacherClass=transaction.getClassEntrydetails(aTeacherForm.getId());
		AssignClassToTeacherHelper.getInstance().setDepartmentDetails(aTeacherForm,teacherClass);
	}
	public boolean updateClassEntry(AssignClassToTeacherForm assignClassTeacherForm) throws Exception{
		IAssignClassToTeacherTransaction transaction=new AssignClassToTeacherTransactionImpl();
		TeacherClass teacherClass=transaction.getClassEntrydetails(assignClassTeacherForm.getId());
		 teacherClass=AssignClassToTeacherHelper.getInstance().convertFormToBo(assignClassTeacherForm,teacherClass);
		boolean isUpdated=transaction.updateClassEntry(teacherClass);
		return isUpdated;
	}
}
