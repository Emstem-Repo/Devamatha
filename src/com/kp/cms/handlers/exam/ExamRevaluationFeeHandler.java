package com.kp.cms.handlers.exam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.exam.ExamRevaluationFee;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.forms.exam.ExamRevaluationFeeForm;
import com.kp.cms.helpers.employee.PayScaleDetailsHelper;
import com.kp.cms.helpers.exam.ExamRevaluationFeeHelper;
import com.kp.cms.to.employee.PayScaleTO;
import com.kp.cms.to.exam.ExamRevaluationFeeTO;
import com.kp.cms.transactions.employee.IPayScaleTransactions;
import com.kp.cms.transactions.exam.IExamRevaluationFeeTxn;
import com.kp.cms.transactionsimpl.employee.PayScaleTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamRevaluationFeeTxnImpl;

public class ExamRevaluationFeeHandler {
	private static final Log log = LogFactory.getLog(ExamRevaluationFeeHandler.class);
	private static volatile ExamRevaluationFeeHandler instance=null;
	IExamRevaluationFeeTxn transaction=ExamRevaluationFeeTxnImpl.getInstance().getInstance();
	
	public static ExamRevaluationFeeHandler getInstance(){
		log.info("Start getInstance of ExamRevaluationFeeHandler");
		if(instance==null){
			instance=new ExamRevaluationFeeHandler();
		}
		log.info("End getInstance of ExamRevaluationFeeHandler");
		return instance;
	}
	/**
	 * @param examRevaluationFeeForm
	 * @param errors
	 * @param session
	 * @return
	 */
	public boolean duplicateCheck(ExamRevaluationFeeForm examRevaluationFeeForm,ActionErrors errors,HttpSession session){
		boolean duplicate=transaction.duplicateCheck(session,errors,examRevaluationFeeForm);
		return duplicate;
	}
	/**
	 * @param examRevaluationFeeForm
	 * @param mode
	 * @return
	 */
	public boolean addRevaluationFee(ExamRevaluationFeeForm examRevaluationFeeForm,String mode){
		ExamRevaluationFee revaluation=ExamRevaluationFeeHelper.getInstance().convertFormTOBO(examRevaluationFeeForm, mode);
		boolean isAdded=transaction.addRevaluationFee(revaluation);
		return isAdded;
	}
	/**
	 * @param examRevaluationFeeForm
	 * @throws Exception
	 */
	public void editRevaluationFee(ExamRevaluationFeeForm examRevaluationFeeForm)throws Exception{
		ExamRevaluationFee revaluationFee=transaction.getRevaluationFeeById(examRevaluationFeeForm.getId());
		ExamRevaluationFeeHelper.getInstance().setBotoForm(examRevaluationFeeForm, revaluationFee);
	}
	/**
	 * @param examRevaluationFeeForm
	 * @param mode
	 * @return
	 */
	public boolean updateRevaluationFee(ExamRevaluationFeeForm examRevaluationFeeForm,String mode){
		ExamRevaluationFee revaluation=ExamRevaluationFeeHelper.getInstance().convertFormTOBO(examRevaluationFeeForm, mode);
		boolean isUpdated=transaction.updateRevaluationFee(revaluation);
		return isUpdated;
	}
   /**
 * @param examRevaluationFeeForm
 * @return
 * @throws Exception
 */
public boolean deleteRevaluationFee(ExamRevaluationFeeForm examRevaluationFeeForm)throws Exception{
		
		boolean isDeleted=transaction.deleteRevaluationFee(examRevaluationFeeForm.getId());
		return isDeleted;
   }
   /**
 * @param examRevaluationFeeForm
 * @return
 * @throws Exception
 */
public boolean reactivateRevaluationFee(ExamRevaluationFeeForm examRevaluationFeeForm)
	 throws Exception{
      return transaction.reactivateRevaluationFee(examRevaluationFeeForm);
  }
   /**
 * @return
 */
public List<ExamRevaluationFeeTO> getRevaluationFeeList(){
	   List<ExamRevaluationFee> revaluationFee=transaction.getRevaluationFeeList();
	   List<ExamRevaluationFeeTO> revaluationFeeToList=ExamRevaluationFeeHelper.getInstance().convertBosToTOs(revaluationFee);
		return revaluationFeeToList;
	}
}
