package com.kp.cms.handlers.admission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.helpers.admission.GensmartCardDataHelper;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IGensmartCardDataTransaction;
import com.kp.cms.transactionsimpl.admission.GensmartCardDataTransactionimpl;


public class GensmartCardDataHandler {
	/**
	 * Singleton object of GensmartCardDataHandler
	 */
	private static volatile GensmartCardDataHandler gensmartCardDataHandler = null;
	private static final Log log = LogFactory.getLog(GensmartCardDataHandler.class);
	private GensmartCardDataHandler() {
		
	}
	/**
	 * return singleton object of GensmartCardDataHandler.
	 * @return
	 */
	public static GensmartCardDataHandler getInstance() {
		if (gensmartCardDataHandler == null) {
			gensmartCardDataHandler = new GensmartCardDataHandler();
		}
		return gensmartCardDataHandler;
	}
	
	public Map<Integer,List<StudentTO>> getListOfStudents( GensmartCardDataForm genscDataForm, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		IGensmartCardDataTransaction transaction= GensmartCardDataTransactionimpl.getInstance();
		List<Student> listOfStudents = transaction.getStudentList(genscDataForm);
		Map<Integer,List<StudentTO>> studentMap = GensmartCardDataHelper.getInstance().ConvertBostoTos(listOfStudents,genscDataForm);
		return studentMap;
	}
	/**
	 * @param studentIds
	 * @return
	 * @throws Exception
	 */
	public Map<String, byte[]> getStudentPhotos(List<Integer> studentIds) throws Exception {
		IGensmartCardDataTransaction transaction= GensmartCardDataTransactionimpl.getInstance();
		return transaction.getStudentPhotos(studentIds);
	}
	
	public String getCourseName(int courseId) throws Exception{
		IGensmartCardDataTransaction trans= GensmartCardDataTransactionimpl.getInstance();
		return trans.getCourseName(courseId);
	}
	/**
	 * updating the isSCDataGenerated field in DB if data is generated for students 
	 * @param studentIds
	 * @throws Exception
	 */
	public void update(List<Integer> studentIds,String user) throws Exception {
		IGensmartCardDataTransaction tc= GensmartCardDataTransactionimpl.getInstance();
		tc.update(studentIds,user);
	}
	
	public void getStudentsWithoutPhotosAndRegNos(GensmartCardDataForm genscForm) throws Exception{
		IGensmartCardDataTransaction txn= GensmartCardDataTransactionimpl.getInstance();
		List<Integer> stuIds=txn.getStudentsWithoutPhotosAndRegNos(genscForm);
	}
	
	
	public void getInitialPageData(GensmartCardDataForm genSmartCardDataForm) throws Exception{
		 IGensmartCardDataTransaction txn= GensmartCardDataTransactionimpl.getInstance();
		 Map<String,String> departmentMap=txn.getDepartmentMap();
		 if(departmentMap!=null)
		 {
			 genSmartCardDataForm.setDepartmentMap(departmentMap);
		 }
	}
	/**
	 * passing the form to impl to get the employee data
	 * @param genscDataForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, List<EmployeeTO>> getListOfEmployees(GensmartCardDataForm genscDataForm, HttpServletRequest request) throws Exception{
		// TODO Auto-generated method stub
		IGensmartCardDataTransaction transaction= GensmartCardDataTransactionimpl.getInstance();
		List<Employee> listOfEmployees = transaction.getEmployeeList(genscDataForm);
		Map<Integer,List<EmployeeTO>> employeeMap = GensmartCardDataHelper.getInstance().ConvertEmpBostoTos(listOfEmployees,genscDataForm);
		return employeeMap;
	}
	/**
	 * method to get employee ids without photo or empFingerPrintId
	 * @param genscDataForm
	 * @throws Exception
	 */
	public void getEmployeesWithoutPhotosAndRegNos(GensmartCardDataForm genscDataForm) throws Exception {
		IGensmartCardDataTransaction txn= GensmartCardDataTransactionimpl.getInstance();
		txn.getEmployeesWithoutPhotosAndRegNos(genscDataForm);
	}
	
	/**
	 * passing the deptId to impl class to get dept name
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public String getDepartmentName(int deptId) throws Exception{
		IGensmartCardDataTransaction trans= GensmartCardDataTransactionimpl.getInstance();
		return trans.getDepartmentName(deptId);
	}
	public Map<String, byte[]> getEmployeePhotos(List<Integer> employeeIds) throws Exception {
		IGensmartCardDataTransaction transaction= GensmartCardDataTransactionimpl.getInstance();
		return transaction.getEmployeePhotos(employeeIds);
	}
	/**
	 * updating the employee isSCDataGenerated as true
	 * @param empIdToSetGenerated
	 * @throws Exception
	 */
	public void updateEmployee(List<Integer> empIdToSetGenerated,String user) throws Exception {
		IGensmartCardDataTransaction tc= GensmartCardDataTransactionimpl.getInstance();
		tc.updateEmployee(empIdToSetGenerated,user);
	}
}
