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

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.forms.smartcard.ScLostCorrectionForm;
import com.kp.cms.helpers.smartcard.ScLostCorrectionHelper;
import com.kp.cms.to.smartcard.ScLostCorrectionTo;
import com.kp.cms.transactions.smartcard.IScLostCorrectionTransaction;
import com.kp.cms.transactionsimpl.smartcard.ScLostCorrectionTransactionImpl;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionHandler {
	
	private static final Log log=LogFactory.getLog(ScLostCorrectionHandler.class);
	public static volatile ScLostCorrectionHandler scHandler=null;
	
	public static ScLostCorrectionHandler getInstance(){
		if(scHandler==null)
		{
			scHandler=new ScLostCorrectionHandler();
			return scHandler;
		}
		return scHandler;
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public Student verifyRegisterNumberAndGetStudentDetails(ScLostCorrectionForm scForm) throws Exception
	{
		IScLostCorrectionTransaction iScTransaction = ScLostCorrectionTransactionImpl.getInstance();
		Student student=iScTransaction.verifyRegisterNumberAndGetDetails(scForm);
		return student;
		
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public Employee verifyEmployeeIdAndGetEmployeeDetails(ScLostCorrectionForm scForm) throws Exception
	{
		IScLostCorrectionTransaction iScTransaction = ScLostCorrectionTransactionImpl.getInstance();
		Employee employee=iScTransaction.verifyEmployeeIdAndGetEmployeeDetails(scForm);
		return employee;
		
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public boolean addScLostCorrection(ScLostCorrectionForm scForm)throws Exception
	{
		log.info("Inside addScLostCorrection of ScLostCorrectionHandler");
		IScLostCorrectionTransaction iScTransaction = ScLostCorrectionTransactionImpl.getInstance();
		
		ScLostCorrection scLostCorrection=new ScLostCorrection();
		Student student = new Student();
		Employee employee = new Employee();
		
		if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
			student.setId(Integer.parseInt(scForm.getStuId()));
			scLostCorrection.setStudentId(student);
			scLostCorrection.setIsEmployee(false);
		}
		else if(scForm.getIsEmployee().equalsIgnoreCase("Employee")){
			employee.setId(Integer.parseInt(scForm.getEmpId()));
			scLostCorrection.setEmployeeId(employee);
			scLostCorrection.setIsEmployee(true);
		}
		scLostCorrection.setStatus("Applied");
		scLostCorrection.setDateOfSubmission(new Date());
		scLostCorrection.setCardReason(scForm.getCardType());
		scLostCorrection.setIsTextFileRequired(true);
		scLostCorrection.setCreatedBy(scForm.getUserId());
		scLostCorrection.setModifiedBy(scForm.getUserId());
		scLostCorrection.setCreatedDate(new Date());
		scLostCorrection.setLastModifiedDate(new Date());
		scLostCorrection.setIsActive(true);
		scLostCorrection.setOldSmartCardNum(scForm.getOldSmartCardNo());
		if(scForm.getRemarks()!=null && !scForm.getRemarks().isEmpty())
			scLostCorrection.setRemarks(scForm.getRemarks());
		
		
		if(scLostCorrection!=null){
			return iScTransaction.addScLostCorrection(scLostCorrection);
		}
		
		log.info("Leaving of addScLostCorrection of ScLostCorrectionHandler");
		return false;
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public ScLostCorrection checkForDuplicate(ScLostCorrectionForm scForm)throws Exception
	{
		IScLostCorrectionTransaction iScTransaction = ScLostCorrectionTransactionImpl.getInstance();
		return iScTransaction.checkForDuplicate(scForm);
		
	}
	
	/**
	 * @param scLostCorrectionForm
	 * @return
	 * @throws Exception
	 */
	public List<ScLostCorrectionTo> getScHistory(ScLostCorrectionForm scLostCorrectionForm)throws Exception
	{
		IScLostCorrectionTransaction iScTransaction = ScLostCorrectionTransactionImpl.getInstance();
		List<ScLostCorrection> scHistoryList =iScTransaction.getScHistory(scLostCorrectionForm);
		if(scHistoryList!=null && !scHistoryList.isEmpty()){
			return ScLostCorrectionHelper.getInstance().pupulateScHistoryBOtoTO(scHistoryList);
		}
		return new ArrayList<ScLostCorrectionTo>();
	}
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public ScLostCorrection checkForPresent(ScLostCorrectionForm scForm)throws Exception
	{
		IScLostCorrectionTransaction iScTransaction = ScLostCorrectionTransactionImpl.getInstance();
		return iScTransaction.checkForPresent(scForm);
		
	}
	
	
	/**
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean cancelScLostCorrection(int id, String userId)throws Exception
	{
		IScLostCorrectionTransaction iScTransaction = ScLostCorrectionTransactionImpl.getInstance();
		if(iScTransaction!=null){
			return iScTransaction.cancelScLostCorrection(id, userId);
		}
		return false;
		
	}
	
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public Student verifyRegisterNumberAndGetStudentDetailsAcc(ScLostCorrectionForm scForm) throws Exception
	{
		IScLostCorrectionTransaction iScTransaction = ScLostCorrectionTransactionImpl.getInstance();

		Student student=iScTransaction.verifyRegisterNumberAndGetDetailsAcc(ScLostCorrectionHelper.getSelectionSearchCriteria(scForm));
		return student;
		
	}

}