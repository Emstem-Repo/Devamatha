package com.kp.cms.handlers.phd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.forms.phd.PhdEmployeeForms;
import com.kp.cms.helpers.phd.PhdEmployeeHelpers;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.phd.PhdEmployeeTo;
import com.kp.cms.transactions.phd.IPhdEmployeeTransaction;
import com.kp.cms.transactionsimpl.phd.PhdEmployeeTransactionImpl;

 public class PhdEmployeeHandelers {
	private static final Log log = LogFactory.getLog(PhdEmployeeHandelers.class);
	IPhdEmployeeTransaction empTransaction=PhdEmployeeTransactionImpl.getInstance();
	
	private static volatile PhdEmployeeHandelers instance=null;
	
	/**
	 * 
	 */
	private PhdEmployeeHandelers(){
		
	}
	
	/**
	 * @return
	 */
	public static PhdEmployeeHandelers getInstance(){
		log.info("Start getInstance of PhdEmployeeHandelers");
		if(instance==null){
			instance=new PhdEmployeeHandelers();
		}
		log.info("End getInstance of PhdEmployeeHandelers");
		return instance;
	}

	public void getInitialData(PhdEmployeeForms objform) throws Exception{
		log.info("Start getInitialData of EmployeeInfoHandlerNew");
		
		 Map<String,String> guideShipMap=empTransaction.guideShipMap();
		 if(guideShipMap!=null){
			 objform.setGuideShipMap(guideShipMap);
			
		 }
		 Map<String,String> religionMap=empTransaction.getReligionMap();
		 if(religionMap!=null){
			 objform.setReligionMap(religionMap);
		}
		 Map<String,String> stateMap=empTransaction.getStatesMap();
		 if(stateMap!=null){
			 objform.setStatesMap(stateMap);
		 }
		 
		 Map<String,String> nationalityMap=empTransaction.getNationalityMap();
		 if(nationalityMap!=null)
			 objform.setNationalityMap(nationalityMap);
		 
		 Map<String,String> qualificationLevelMap=empTransaction.getQualificationLevelMap();
		 if(qualificationLevelMap!=null){
			 objform.setQualificationLevelMap(qualificationLevelMap);
		 }
		 Map<String,String> qualificationMap=empTransaction.getQualificationMap();
		 if(qualificationMap!=null){
			 objform.setQualificationMap(qualificationMap);
		 }
		 List<EmpQualificationLevelTo> qualificationFixedTo=empTransaction.getQualificationFixedMap();
		 if(qualificationFixedTo!=null){
			 objform.setPhdEmployeequalificationFixedTo(qualificationFixedTo);
		 }
		 objform.setNoOfBookAuthored("0");
		 objform.setNoOfresearch("0");
			
		log.info("End getInitialData of EmpResumeSubmissionHandler");
	}

	public boolean savePhdEmployee(PhdEmployeeForms objform) throws Exception{
		boolean flag=false;
		PhdEmployee employee=PhdEmployeeHelpers.getInstance().convertFormToBo(objform);
		flag=empTransaction.savePhdEmployee(employee);
		
		return flag;
	}

	public List<PhdEmployeeTo> searchPhdDetails(PhdEmployeeForms objform) throws Exception{
		   List<PhdEmployee> phdBoList=empTransaction.getPhdDetailsList(objform);
		   List<PhdEmployeeTo> phdTooList=PhdEmployeeHelpers.getInstance().convertBosToTOs(phdBoList);
			return phdTooList;
		}

	public void editPhdemployee(PhdEmployeeForms objform) throws Exception{
		PhdEmployee phdBO = empTransaction.getPhdEmployeeById(objform.getId());
		PhdEmployeeHelpers.getInstance().setDataBoToForm(objform, phdBO);
	}

	public boolean deletePhdemployee(PhdEmployeeForms objform) throws Exception{
	      boolean isDeleted = empTransaction.deletePhdemployee(objform.getId());
	      return isDeleted;
	  }
}
