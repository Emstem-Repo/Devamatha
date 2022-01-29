package com.kp.cms.handlers.smartcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.forms.employee.EmployeeReportForm;
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessForm;
import com.kp.cms.forms.smartcard.ScLostCorrectionViewForm;
import com.kp.cms.helpers.employee.EmployeeReportHelper;
import com.kp.cms.helpers.smartcard.ScLostCorrectionViewHelper;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.employee.EmployeeReportTO;
import com.kp.cms.to.smartcard.ScLostCorrectionViewTO;
import com.kp.cms.transactions.admission.IGensmartCardDataTransaction;
import com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction;
import com.kp.cms.transactionsimpl.admission.GensmartCardDataTransactionimpl;
import com.kp.cms.transactionsimpl.smartcard.ScLostCorrectionViewTransactionImpl;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionViewHandler {
	
	private static final Log log=LogFactory.getLog(ScLostCorrectionViewHandler.class);
	public static volatile ScLostCorrectionViewHandler scHandler=null;
	
	public static ScLostCorrectionViewHandler getInstance(){
		if(scHandler==null)
		{
			scHandler=new ScLostCorrectionViewHandler();
			return scHandler;
		}
		return scHandler;
	}
	
	/**
	 * @param scLostCorrectionViewForm
	 * @return
	 * @throws Exception
	 */
	public List<ScLostCorrectionViewTO> getDetailsList(ScLostCorrectionViewForm scLostCorrectionViewForm)throws Exception
	{
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		List<ScLostCorrection> scList =iScTransaction.getDetailsList(ScLostCorrectionViewHelper.getSelectionSearchCriteria(scLostCorrectionViewForm));
		if(scList!=null && !scList.isEmpty()){
			return ScLostCorrectionViewHelper.getInstance().pupulateScHistoryBOtoTO(scList, scLostCorrectionViewForm);
		}
		return new ArrayList<ScLostCorrectionViewTO>();
	}
		
	/**
	 * @param scForm
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,List<StudentTO>> getListOfAllStudents(ScLostCorrectionViewForm scForm, List<Integer> idList) throws Exception 
	{
		List<Student> listOfStudents = new ArrayList<Student>();
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		List<ScLostCorrection>  list =iScTransaction.getAllSelectedList(idList);
		if(list!=null && !list.isEmpty()){
			Iterator<ScLostCorrection> iterator = list.iterator();
			while (iterator.hasNext()) {
				ScLostCorrection scLostCorrection = (ScLostCorrection) iterator .next();
				Student stu = scLostCorrection.getStudentId();
				listOfStudents.add(stu);
			}
		}
		Map<Integer,List<StudentTO>> studentMap = ScLostCorrectionViewHelper.getInstance().ConvertBostoTos(listOfStudents,scForm);
		return studentMap;
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public boolean setStatus(ScLostCorrectionViewForm scForm) throws Exception {
		
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		
			return iScTransaction.setStatus(scForm);
		
	}
	
	/**
	 * @param scLostCorrectionViewForm
	 * @return
	 * @throws Exception
	 */
	public List<ScLostCorrectionViewTO> getDetailsListAfter(ScLostCorrectionViewForm scLostCorrectionViewForm)throws Exception
	{
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		List<ScLostCorrection> scList =iScTransaction.getDetailsListAfter(scLostCorrectionViewForm);
		if(scList!=null && !scList.isEmpty()){
			return ScLostCorrectionViewHelper.getInstance().pupulateScHistoryBOtoTO(scList, scLostCorrectionViewForm);
		}
		return new ArrayList<ScLostCorrectionViewTO>();
	}
	
	/**
	 * @param scForm
	 * @throws Exception
	 */
	public void getStudentsWithoutPhotosAndRegNos(ScLostCorrectionViewForm scForm) throws Exception{
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		List<Integer> stuIds=iScTransaction.getStudentsWithoutPhotosAndRegNos(scForm);
	}
	
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getAnySelected(ScLostCorrectionViewForm scForm) throws Exception{
		List<Integer> list= new ArrayList<Integer>();
		if(scForm.getScList()!=null && !scForm.getScList().isEmpty()){
			Iterator<ScLostCorrectionViewTO> scItr=scForm.getScList().iterator();
			while(scItr.hasNext()){
				ScLostCorrectionViewTO scTO = scItr.next();
				if(scTO.getChecked()!=null && scTO.getChecked().equalsIgnoreCase("on")){
					list.add(scTO.getId());
				}
			}
			return list;
			}
		return list;
		
	}
	
	/**
	 * @param studentIds
	 * @return
	 * @throws Exception
	 */
	public Map<String, byte[]> getStudentPhotos(List<Integer> studentIds) throws Exception {
		
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		return iScTransaction.getStudentPhotos(studentIds);
	}
	
	/**
	 * @param scForm
	 * @param request
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	public boolean exportTOExcel(ScLostCorrectionViewForm scForm, HttpServletRequest request, List<Integer> idList) throws Exception {
		
		boolean isUpdated= false;
		
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		List<ScLostCorrection>  list =iScTransaction.getAllSelectedList(idList);
		ScLostCorrectionViewTO viewTO = ScLostCorrectionViewHelper.getInstance().getSelectedColumns(scForm);
		
		if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
			List<StudentTO> studentTOs = ScLostCorrectionViewHelper.getInstance().convertBOToExcel(list);
			isUpdated = ScLostCorrectionViewHelper.getInstance().convertToExcel(studentTOs,viewTO,request);
		}
		else{
			List<EmployeeTO> employeeTOs = ScLostCorrectionViewHelper.getInstance().convertBOToExcelEmployee(list);
			isUpdated = ScLostCorrectionViewHelper.getInstance().convertToExcelEmployee(employeeTOs,viewTO,request);
		}
		return isUpdated;
	}
	
	/**
	 * @param scForm
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,List<EmployeeTO>> getListOfAllEmployees(ScLostCorrectionViewForm scForm, List<Integer> idList) throws Exception 
	{
		List<Employee> listOfEmployees = new ArrayList<Employee>();
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		List<ScLostCorrection>  list =iScTransaction.getAllSelectedList(idList);
		if(list!=null && !list.isEmpty()){
			Iterator<ScLostCorrection> iterator = list.iterator();
			while (iterator.hasNext()) {
				ScLostCorrection scLostCorrection = (ScLostCorrection) iterator .next();
				Employee emp = scLostCorrection.getEmployeeId();
				listOfEmployees.add(emp);
			}
		}
		Map<Integer,List<EmployeeTO>> employeeMap = ScLostCorrectionViewHelper.getInstance().ConvertBostoTosEmployee(listOfEmployees,scForm);
		return employeeMap;
	}
	
	/**
	 * @param employeeIds
	 * @return
	 * @throws Exception
	 */
	public Map<String, byte[]> getEmployeePhotos(List<Integer> employeeIds) throws Exception {
		
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		return iScTransaction.getEmployeePhotos(employeeIds);
	}

	/**
	 * @param scForm
	 * @throws Exception
	 */
	public void getEmployeesWithoutPhotosAndRegNos(ScLostCorrectionViewForm scForm) throws Exception {
		
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		iScTransaction.getEmployeesWithoutPhotosAndRegNos(scForm);
	}


}
