package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExternalEvaluatorForm;
import com.kp.cms.helpers.exam.ExternalEvaluatorHelper;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.exam.ExternalEvaluatorTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.transactions.exam.IExternalEvaluatorTransaction;
import com.kp.cms.transactionsimpl.exam.ExternalEvaluatorTransactionImpl;

public class ExternalEvaluatorHandler {
private static final Log log = LogFactory.getLog(ExternalEvaluatorHandler.class);
	
	private static volatile ExternalEvaluatorHandler externalEvaluatorHandler = null;

	private ExternalEvaluatorHandler() {
	}
	
	public static ExternalEvaluatorHandler getInstance() {
		if (externalEvaluatorHandler == null) {
			externalEvaluatorHandler = new ExternalEvaluatorHandler();
		}
		return externalEvaluatorHandler;
	}
	IExternalEvaluatorTransaction transaction=new ExternalEvaluatorTransactionImpl();
	
	public boolean addExternalEvaluator(ExternalEvaluatorForm externalEvaluatorForm)throws Exception
	{
		log.info("Inside addExternalEvaluator of ExternalEvaluatorHandler");
		//Capture the formbean properties. and set those to externalEvaluatorTO
		
		ExternalEvaluatorTO externalEvaluatorTO=new ExternalEvaluatorTO();
		if(externalEvaluatorForm!=null){
			externalEvaluatorTO.setName(externalEvaluatorForm.getName());
			if(externalEvaluatorForm.getAddressLine1()!=null && !externalEvaluatorForm.getAddressLine1().isEmpty()){
				externalEvaluatorTO.setAddressLine1(externalEvaluatorForm.getAddressLine1());
			}
			if(externalEvaluatorForm.getAddressLine2()!=null && !externalEvaluatorForm.getAddressLine2().isEmpty()){
				externalEvaluatorTO.setAddressLine2(externalEvaluatorForm.getAddressLine2());
			}
			if(externalEvaluatorForm.getCity()!=null && !externalEvaluatorForm.getCity().isEmpty()){
			externalEvaluatorTO.setCity(externalEvaluatorForm.getCity());
			}
			if(externalEvaluatorForm.getStateId()!=null && !externalEvaluatorForm.getStateId().isEmpty()){
			StateTO stateTO=new StateTO();
			stateTO.setId(Integer.parseInt(externalEvaluatorForm.getStateId()));
			externalEvaluatorTO.setStateTO(stateTO);
			}
			if(externalEvaluatorForm.getCountryId()!=null && !externalEvaluatorForm.getCountryId().isEmpty()){
			CountryTO countryTO=new CountryTO();
			countryTO.setId(Integer.parseInt(externalEvaluatorForm.getCountryId()));
			externalEvaluatorTO.setCountryTO(countryTO);
			}
			if(externalEvaluatorForm.getPin()!=null && !externalEvaluatorForm.getPin().isEmpty()){
			externalEvaluatorTO.setPin(externalEvaluatorForm.getPin());
			}
			if(externalEvaluatorForm.getEmail()!=null && !externalEvaluatorForm.getEmail().isEmpty()){
			externalEvaluatorTO.setEmail(externalEvaluatorForm.getEmail());
			}
			if(externalEvaluatorForm.getMobile()!=null && !externalEvaluatorForm.getMobile().isEmpty()){
			externalEvaluatorTO.setMobile(externalEvaluatorForm.getMobile());
			}
			if(externalEvaluatorForm.getPan()!=null && !externalEvaluatorForm.getPan().isEmpty()){
			externalEvaluatorTO.setPan(externalEvaluatorForm.getPan());
			}
			if(externalEvaluatorForm.getDepartment()!=null && !externalEvaluatorForm.getDepartment().isEmpty()){
			externalEvaluatorTO.setDepartment(externalEvaluatorForm.getDepartment());
			}
			if(externalEvaluatorForm.getBankAccNo()!=null && !externalEvaluatorForm.getBankAccNo().isEmpty()){
				externalEvaluatorTO.setBankAccNo(externalEvaluatorForm.getBankAccNo());
			}
			if(externalEvaluatorForm.getBankName()!=null && !externalEvaluatorForm.getBankName().isEmpty()){
				externalEvaluatorTO.setBankName(externalEvaluatorForm.getBankName());
			}
			if(externalEvaluatorForm.getBankBranch()!=null && !externalEvaluatorForm.getBankBranch().isEmpty()){
				externalEvaluatorTO.setBankBranch(externalEvaluatorForm.getBankBranch());
			}
			if(externalEvaluatorForm.getBankIfscCode()!=null && !externalEvaluatorForm.getBankIfscCode().isEmpty()){
				externalEvaluatorTO.setBankIfscCode(externalEvaluatorForm.getBankIfscCode());
			}
			externalEvaluatorTO.setCreatedBy(externalEvaluatorForm.getUserId());
			externalEvaluatorTO.setModifiedBy(externalEvaluatorForm.getUserId());

		}
		ExamValuators examValuators=ExternalEvaluatorHelper.getInstance().populateTOtoBO(externalEvaluatorTO);
		if(examValuators!=null){
			return transaction.addExternalEvaluator(examValuators);
		}
		log.info("Leaving of addExternalEvaluator of ExternalEvaluatorHandler");
		return false;
	}
	
	/**
	 * Gets all the externalEvaluator where isActive=1
	 */
	
	public List<ExternalEvaluatorTO> getExternalEvaluatorDetails()throws Exception
	{
		log.info("Inside of getExternalEvaluatorDetails of ExternalEvaluatorHandler");
		List<ExamValuators> externalList =transaction.getExternalEvaluatorDetails();
		if(externalList!=null && !externalList.isEmpty()){
			return ExternalEvaluatorHelper.getInstance().pupulateExamValuatorsBOtoTO(externalList);
		}
		log.info("Leaving from getExternalEvaluatorDetails of ExternalEvaluatorHandler");
		return new ArrayList<ExternalEvaluatorTO>();
	}
	
	/**
	 * Deletes a externalEvaluator for a code
	 */
	
	public boolean deleteExternalEvaluator(int externalEvaluatorId, String userId)throws Exception{
		log.info("Inside of deleteExternalEvaluator of ExternalEvaluatorHandler");
		if(transaction!=null){
			return transaction.deleteExternalEvaluator(externalEvaluatorId, userId);
		}
		log.info("Leaving of deleteExternalEvaluator of ExternalEvaluatorHandler");
		return false;
	}
	
	public boolean updateExternalEvaluator(ExternalEvaluatorForm byForm)throws Exception
	{
		log.info("Inside of updateExternalEvaluator of ExternalEvaluatorHandler");
		ExternalEvaluatorTO byTO=new ExternalEvaluatorTO();
		String name="";
		boolean orgName=false;
		name=byForm.getName();
		orgName = transaction.checkForDuplicateonName1(name, byForm);
		if (orgName){
			throw new DuplicateException();
		}
		else{
		
		if(byForm!=null){
			byTO.setId(byForm.getId());
			byTO.setName(byForm.getName());
			byTO.setAddressLine1(byForm.getAddressLine1());
			byTO.setModifiedBy(byForm.getUserId());
			if(byForm.getAddressLine2()!=null && !byForm.getAddressLine2().isEmpty()){
			byTO.setAddressLine2(byForm.getAddressLine2());
			}
			byTO.setCity(byForm.getCity());
			byTO.setPin(byForm.getPin());
			byTO.setEmail(byForm.getEmail());
			byTO.setMobile(byForm.getMobile());
			byTO.setPan(byForm.getPan());
			byTO.setDepartment(byForm.getDepartment());
			byTO.setBankAccNo(byForm.getBankAccNo());
			byTO.setBankName(byForm.getBankName());
			byTO.setBankBranch(byForm.getBankBranch());
			byTO.setBankIfscCode(byForm.getBankIfscCode());
			if(byForm.getCountryId()!=null && !byForm.getCountryId().isEmpty()){
			CountryTO countryTO=new CountryTO();
			countryTO.setId(Integer.parseInt(byForm.getCountryId()));
			byTO.setCountryTO(countryTO);
			}
			if(byForm.getStateId()!=null && !byForm.getStateId().isEmpty()){
			StateTO stateTO=new StateTO();
			stateTO.setId(Integer.parseInt(byForm.getStateId()));
			byTO.setStateTO(stateTO);
			}
		}
		if(byTO!=null){
			ExamValuators examValuators=ExternalEvaluatorHelper.getInstance().populateTotoBoUpdate(byTO);
			if(transaction!=null){
				return transaction.updateExternalEvaluator(examValuators);				
			}
		}
		log.info("Leaving of updateExternalEvaluator of ExternalEvaluatorHandler");
		return false;
		}
	}

	/**
	 * Reactivate externalEvaluator (Makes isActive=1 if it is 0)
	 */
	
	public boolean reActivateExternalEvaluator(String name, String userId)throws Exception
	{
	log.info("Inside into reActivateExternalEvaluator of ExternalEvaluatorHandler");
	if(transaction!=null){
		return transaction.reActivateExternalEvaluator(name, userId);
	}
	log.info("Leaving of reActivateExternalEvaluator of ExternalEvaluatorHandler");
	return false;
	}
	
	/**
	 * Gets a row based on the Id
	 */
	public ExternalEvaluatorTO getDetailsonId(int extId) throws Exception{
		log.info("Inside into getDetailsonId of ExternalEvaluatorHandler");
		ExamValuators examValuators=transaction.getDetailsonId(extId);
		log.info("End of getDetailsonId of ExternalEvaluatorHandler");
		return ExternalEvaluatorHelper.getInstance().populateBotoToEdit(examValuators);
	}
	
	public ExamValuators checkForDuplicateonName(String name)throws Exception
	{
		log.info("Inside into checkForDuplicateonName of ExternalEvaluatorHandler");
		return transaction.checkForDuplicateonName(name);
		
	}

}
