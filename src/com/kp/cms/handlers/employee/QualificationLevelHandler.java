package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.DuplicateException1;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.exceptions.ReActivateException1;
import com.kp.cms.forms.employee.QualificationLevelForm;
import com.kp.cms.helpers.employee.QualificationLevelHelper;
import com.kp.cms.to.employee.QualificationLevelTO;
import com.kp.cms.transactions.employee.IQualificationLevelTransaction;
import com.kp.cms.transactionsimpl.employee.QualificationLevelTransactionImpl;

public class QualificationLevelHandler {
	private static final Log log = LogFactory.getLog(QualificationLevelHandler.class);
	public static volatile QualificationLevelHandler qualificationLevelHandler=null;
	
	public static QualificationLevelHandler getInstance(){
		if(qualificationLevelHandler == null){
			qualificationLevelHandler = new QualificationLevelHandler();
			return qualificationLevelHandler;
		}
		return qualificationLevelHandler;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<QualificationLevelTO> getQualificationList() throws Exception{
		
		IQualificationLevelTransaction iTransction = QualificationLevelTransactionImpl.getInstance();
		List<QualificationLevelBO> levelBOs=iTransction.getQualificationLevel();
		List<QualificationLevelTO> qualificationLevelTOs=QualificationLevelHelper.getInstance().copyQualificationLevelBOsToTOs(levelBOs);;
		return qualificationLevelTOs;
	}
	/**
	 * @param qualificationLevelForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addQualificationLevel(QualificationLevelForm qualificationLevelForm, String mode) throws Exception{
		IQualificationLevelTransaction iQualificationLevelTransaction=QualificationLevelTransactionImpl.getInstance();
		boolean isAdded=false;
		QualificationLevelBO qualificationLevelBO=null;
		QualificationLevelBO levelBO=null;
		QualificationLevelBO reactivated=null;
		if(mode.equalsIgnoreCase("Add")){
			 qualificationLevelBO=iQualificationLevelTransaction.isDuplicated(qualificationLevelForm);
			 levelBO=iQualificationLevelTransaction.isDuplicateDisplayOrder(qualificationLevelForm);
			 reactivated=iQualificationLevelTransaction.isReactivate(qualificationLevelForm);
		}
		
		if (qualificationLevelBO != null && qualificationLevelBO.getIsActive()) {
			throw new DuplicateException();
		}
		
		if(levelBO!=null && levelBO.getIsActive()){
			throw new DuplicateException();
		}
		if(reactivated !=null && reactivated.getIsActive()){
			throw new DuplicateException();
		}
		else if(reactivated !=null && !reactivated.getIsActive()){
			qualificationLevelForm.setDuplId(reactivated.getId());
			throw new ReActivateException();
		}
		qualificationLevelBO=QualificationLevelHelper.getInstance().copyDataFromFormToBO(qualificationLevelForm, mode);
		isAdded=iQualificationLevelTransaction.addQualificationLevel(qualificationLevelBO, mode);
		return isAdded;
	}
	/**
	 * @param id
	 * @param activate
	 * @param qualificationLevelForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteQualificationLevel(int id, boolean activate,QualificationLevelForm qualificationLevelForm) throws Exception {
		boolean isDeleted=false;
		IQualificationLevelTransaction transaction=QualificationLevelTransactionImpl.getInstance();
		isDeleted=transaction.deleteQualificationLevel(id,activate,qualificationLevelForm);
		return isDeleted;
	}
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public QualificationLevelTO editQualificationLevel(int id) throws Exception {
		IQualificationLevelTransaction transaction=QualificationLevelTransactionImpl.getInstance();
		QualificationLevelBO bos=transaction.editQualificationLevel(id);
		QualificationLevelTO tos=QualificationLevelHelper.getInstance().copyQualificationLevelBOsToTOs1(bos);
		return tos;
	}
	
		/**
		 * @param qualificationLevelForm
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		public boolean updateQualificationLevel(QualificationLevelForm qualificationLevelForm, String mode)throws Exception {
			boolean isUpdated=false;
			IQualificationLevelTransaction transaction=QualificationLevelTransactionImpl.getInstance();
			QualificationLevelBO qualificationLevelBO=null;
			QualificationLevelBO levelBO=null;
			if(mode.equalsIgnoreCase("Edit")){
				 qualificationLevelBO=transaction.isDuplicated(qualificationLevelForm);
				 levelBO=transaction.isDuplicateDisplayOrder(qualificationLevelForm);
			}
			if(qualificationLevelBO!=null){
				if(qualificationLevelForm.getId() != qualificationLevelBO.getId()){
					throw new DuplicateException();
				}
			}
			if(levelBO!=null){
				if(qualificationLevelForm.getId() != levelBO.getId()){
					throw new DuplicateException();
				}
			}
			QualificationLevelBO bo=QualificationLevelHelper.getInstance().copyDataFromFormToBO(qualificationLevelForm, mode);
			isUpdated=transaction.addQualificationLevel(bo, mode);
			return isUpdated;
		}
	}
