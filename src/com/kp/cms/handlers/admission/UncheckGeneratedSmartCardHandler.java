package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.UncheckGeneratedSmartCardForm;
import com.kp.cms.helpers.admission.UncheckGeneratedSmartCardHelper;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IUncheckGeneratedSmartCardTransaction;
import com.kp.cms.transactionsimpl.admission.UncheckGeneratedSmartCardTransactionimpl;

public class UncheckGeneratedSmartCardHandler {
	/**
	 * Singleton object of UncheckGeneratedSmartCardHandler
	 */
	private static volatile UncheckGeneratedSmartCardHandler uncheckGeneratedSmartCardHandler = null;
	private static final Log log = LogFactory.getLog(UncheckGeneratedSmartCardHandler.class);
	private UncheckGeneratedSmartCardHandler() {
		
	}
	/**
	 * return singleton object of UncheckGeneratedSmartCardHandler.
	 * @return
	 */
	public static UncheckGeneratedSmartCardHandler getInstance() {
		if (uncheckGeneratedSmartCardHandler == null) {
			uncheckGeneratedSmartCardHandler = new UncheckGeneratedSmartCardHandler();
		}
		return uncheckGeneratedSmartCardHandler;
	}
	/**
	 * Getting the student list for whom smart card data has been generated 
	 * @param uncheckForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> getStudentList(UncheckGeneratedSmartCardForm uncheckForm) throws Exception{
		IUncheckGeneratedSmartCardTransaction txn = UncheckGeneratedSmartCardTransactionimpl.getInstance();
		List<Student> studentBo= txn.getGeneratedStudentList(uncheckForm);
		List<StudentTO> listOfStudents=UncheckGeneratedSmartCardHelper.getInstance().convertBostoTOs(studentBo);
		return listOfStudents;
	}
	/**
	 * Updating the isSCDataGenerated field in DB to 1 for the list of students in the form
	 * @param generatedStudentList
	 * @return
	 * @throws Exception
	 */
	public boolean updateGeneratedFlag(List<StudentTO> generatedStudentList,UncheckGeneratedSmartCardForm uncheckForm) throws Exception {
		IUncheckGeneratedSmartCardTransaction transaction = UncheckGeneratedSmartCardTransactionimpl.getInstance();
		boolean flagSet=transaction.updateGeneratedFlag(generatedStudentList,uncheckForm);
		return flagSet;
	}
	/**
	 * passing the form to impl to fetch the list of employees
	 * @param uncheckForm
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeTO> getEmployeeList(UncheckGeneratedSmartCardForm uncheckForm) throws Exception {
		IUncheckGeneratedSmartCardTransaction txn = UncheckGeneratedSmartCardTransactionimpl.getInstance();
		List<Employee> emplBo= txn.getGeneratedEmployeeList(uncheckForm);
		List<EmployeeTO> listOfEmployees=UncheckGeneratedSmartCardHelper.getInstance().convertEmpBostoTOs(emplBo);
		return listOfEmployees;
	}
	
	/**passing the TO to impl to set the flag as false for selected employees
	 * @param generatedEmployeeList
	 * @param uncheckForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateGeneratedEmployeeFlag(List<EmployeeTO> generatedEmployeeList,UncheckGeneratedSmartCardForm uncheckForm) throws Exception {
		IUncheckGeneratedSmartCardTransaction transaction = UncheckGeneratedSmartCardTransactionimpl.getInstance();
		boolean flagSet=transaction.updateGeneratedEmployeeFlag(generatedEmployeeList,uncheckForm);
		return flagSet;
	}
}
