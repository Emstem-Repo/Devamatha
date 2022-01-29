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
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessForm;
import com.kp.cms.forms.smartcard.ScLostCorrectionViewForm;
import com.kp.cms.helpers.smartcard.ScLostCorrectionProcessHelper;
import com.kp.cms.helpers.smartcard.ScLostCorrectionViewHelper;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.smartcard.ScLostCorrectionProcessTO;
import com.kp.cms.to.smartcard.ScLostCorrectionViewTO;
import com.kp.cms.transactions.smartcard.IScLostCorrectionProcessTransaction;
import com.kp.cms.transactions.smartcard.IScLostCorrectionViewTransaction;
import com.kp.cms.transactionsimpl.smartcard.ScLostCorrectionProcessTransactionImpl;
import com.kp.cms.transactionsimpl.smartcard.ScLostCorrectionViewTransactionImpl;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionProcessHandler {
	
	private static final Log log=LogFactory.getLog(ScLostCorrectionProcessHandler.class);
	public static volatile ScLostCorrectionProcessHandler scHandler=null;
	
	public static ScLostCorrectionProcessHandler getInstance(){
		if(scHandler==null)
		{
			scHandler=new ScLostCorrectionProcessHandler();
			return scHandler;
		}
		return scHandler;
	}
	
	/**
	 * @param scLostCorrectionProcessForm
	 * @return
	 * @throws Exception
	 */
	public List<ScLostCorrectionProcessTO> getDetailsList(ScLostCorrectionProcessForm scLostCorrectionProcessForm)throws Exception
	{
		IScLostCorrectionProcessTransaction iScTransaction = ScLostCorrectionProcessTransactionImpl.getInstance();
		List<ScLostCorrection> scList =iScTransaction.getDetailsList(scLostCorrectionProcessForm);
		if(scList!=null && !scList.isEmpty()){
			return ScLostCorrectionProcessHelper.getInstance().pupulateScProcessBOtoTO(scList, scLostCorrectionProcessForm);
		}
		return new ArrayList<ScLostCorrectionProcessTO>();
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public boolean setStatus(ScLostCorrectionProcessForm scForm) throws Exception {
		
		IScLostCorrectionProcessTransaction iScTransaction = ScLostCorrectionProcessTransactionImpl.getInstance();
		
			return iScTransaction.setStatus(scForm);
	}
	
	
	/**
	 * @param scForm
	 * @throws Exception
	 */
	public void getStudentsWithoutPhotosAndRegNos(ScLostCorrectionProcessForm scForm) throws Exception{
		IScLostCorrectionProcessTransaction iScTransaction = ScLostCorrectionProcessTransactionImpl.getInstance();
		List<Integer> stuIds=iScTransaction.getStudentsWithoutPhotosAndRegNos(scForm);
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getAnySelected(ScLostCorrectionProcessForm scForm) throws Exception{
		List<Integer> list= new ArrayList<Integer>();
		if(scForm.getScList()!=null && !scForm.getScList().isEmpty()){
			Iterator<ScLostCorrectionProcessTO> scItr=scForm.getScList().iterator();
			while(scItr.hasNext()){
				ScLostCorrectionProcessTO scTO = scItr.next();
				if(scTO.getChecked()!=null && scTO.getChecked().equalsIgnoreCase("on")){
					list.add(scTO.getId());
				}
			}
			return list;
			}
		return list;
	}
	
	/**
	 * @param scForm
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,List<StudentTO>> getListOfAllStudents(ScLostCorrectionProcessForm scForm, List<Integer> idList) throws Exception 
	{
		List<Student> listOfStudents = new ArrayList<Student>();
		IScLostCorrectionProcessTransaction iScTransaction = ScLostCorrectionProcessTransactionImpl.getInstance();
		List<ScLostCorrection>  list =iScTransaction.getAllStudentList(idList);
		if(list!=null && !list.isEmpty()){
			Iterator<ScLostCorrection> iterator = list.iterator();
			while (iterator.hasNext()) {
				ScLostCorrection scLostCorrection = (ScLostCorrection) iterator .next();
				Student stu = scLostCorrection.getStudentId();
				listOfStudents.add(stu);
			}
		}
		Map<Integer,List<StudentTO>> studentMap = ScLostCorrectionProcessHelper.getInstance().ConvertBostoTos(listOfStudents,scForm);
		return studentMap;
	}
	
	/**
	 * @param scForm
	 * @param request
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	public boolean exportTOExcel(ScLostCorrectionProcessForm scForm, HttpServletRequest request, List<Integer> idList) throws Exception {
		
		boolean isUpdated= false;
		
		IScLostCorrectionViewTransaction iScTransaction = ScLostCorrectionViewTransactionImpl.getInstance();
		List<ScLostCorrection>  list =iScTransaction.getAllSelectedList(idList);
		ScLostCorrectionProcessTO processTO = ScLostCorrectionProcessHelper.getInstance().getColumns(scForm);
		
		if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
			List<StudentTO> studentTOs = ScLostCorrectionViewHelper.getInstance().convertBOToExcel(list);
			isUpdated = ScLostCorrectionProcessHelper.getInstance().convertToExcel(studentTOs,processTO,request);
		}
		else{
			List<EmployeeTO> employeeTOs = ScLostCorrectionViewHelper.getInstance().convertBOToExcelEmployee(list);
			isUpdated = ScLostCorrectionProcessHelper.getInstance().convertToExcelEmployee(employeeTOs,processTO,request);
		}
		
		return isUpdated;
	}

	/**
	 * @param scForm
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,List<EmployeeTO>> getListOfAllEmployees(ScLostCorrectionProcessForm scForm, List<Integer> idList) throws Exception 
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
		Map<Integer,List<EmployeeTO>> employeeMap = ScLostCorrectionProcessHelper.getInstance().ConvertBostoTosEmployee(listOfEmployees,scForm);
		return employeeMap;
	}
	
	/**
	 * @param scForm
	 * @throws Exception
	 */
	public void getEmployeesWithoutPhotosAndRegNos(ScLostCorrectionProcessForm scForm) throws Exception {
		
		IScLostCorrectionProcessTransaction iScTransaction = ScLostCorrectionProcessTransactionImpl.getInstance();
		iScTransaction.getEmployeesWithoutPhotosAndRegNos(scForm);
	}

}
