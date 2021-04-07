package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.employee.OnlineLeaveAppInstructions;
import com.kp.cms.forms.employee.OnlineLeaveAppInstructionForm;
import com.kp.cms.to.employee.OnlineLeaveAppInstructionTO;
import com.kp.cms.transactions.employee.IOnlineLeaveAppInstructionTransaction;
import com.kp.cms.transactionsimpl.employee.OnlineLeaveAppInstructionTxnImpl;

public class OnlineLeaveAppInstructionHandler { 
	private static final Logger log = Logger.getLogger(OnlineLeaveAppInstructionHandler.class);
	public static volatile OnlineLeaveAppInstructionHandler appInstructionHandler = null;
	public static OnlineLeaveAppInstructionHandler getInstance(){
		if(appInstructionHandler == null){
			appInstructionHandler = new OnlineLeaveAppInstructionHandler();
			return appInstructionHandler;
		}
		return appInstructionHandler;
	}
	IOnlineLeaveAppInstructionTransaction iTransaction = OnlineLeaveAppInstructionTxnImpl.getInstance();
	/**
	 * @return
	 * @throws Exception
	 */
	public List<OnlineLeaveAppInstructionTO> getOnlineLeaveInstructions() throws Exception{
		List<OnlineLeaveAppInstructionTO> instructionTOs = new ArrayList<OnlineLeaveAppInstructionTO>();
		List<OnlineLeaveAppInstructions> list = iTransaction.getOnlineLeaveInstructions();
		OnlineLeaveAppInstructionTO to;
		if(list!=null && !list.isEmpty()){
			Iterator<OnlineLeaveAppInstructions> iterator = list.iterator();
			while (iterator.hasNext()) {
				OnlineLeaveAppInstructions onlineLeaveAppInstructions = (OnlineLeaveAppInstructions) iterator .next();
				to = new OnlineLeaveAppInstructionTO();
				to.setId(onlineLeaveAppInstructions.getId());
				to.setDescription(onlineLeaveAppInstructions.getDescription());
				instructionTOs.add(to);
			}
		}
		return instructionTOs;
	}
	/**
	 * @param descMessage
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicateLeaveInstructions(String descMessage)throws Exception {
		boolean isExist=iTransaction.checkDuplicateNewsEvents(descMessage);
		return isExist;
	}
	/**
	 * @param appInstructionForm
	 * @return
	 * @throws Exception
	 */
	public boolean addOnlineLeaveInstructions( OnlineLeaveAppInstructionForm appInstructionForm) throws Exception{
		OnlineLeaveAppInstructions appInstructions = new OnlineLeaveAppInstructions();
		if(appInstructionForm.getDescription()!=null && !appInstructionForm.getDescription().isEmpty()){
			appInstructions.setDescription(appInstructionForm.getDescription());
			appInstructions.setCreatedBy(appInstructionForm.getUserId());
			appInstructions.setCreatedDate(new Date());
		}
		boolean isAdded = iTransaction.saveLeaveInstructions(appInstructions);
		return isAdded;
	}
	/**
	 * @param appInstructionForm
	 * @throws Exception
	 */
	public void editLeaveAppInstructions( OnlineLeaveAppInstructionForm appInstructionForm)throws Exception {
		OnlineLeaveAppInstructions appInstructions = iTransaction.getleaveAppInstructions(appInstructionForm);
		if(appInstructions!=null && !appInstructions.toString().isEmpty()){
			appInstructionForm.setId(appInstructions.getId());
			appInstructionForm.setDescription(appInstructions.getDescription());
		}
	}
	/**
	 * @param leaveAppInsId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteLeaveAppIns(int leaveAppInsId)throws Exception {
		boolean isDeleted = iTransaction.deleteLeaveAppIns(leaveAppInsId);
		return isDeleted;
	}
	/**
	 * @param appInstructionForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateOnlineLeaveInstructions( OnlineLeaveAppInstructionForm appInstructionForm)throws Exception {
		boolean isUpdated = iTransaction.updateLeaveInstructions(appInstructionForm);
		return isUpdated;
	}
	
}
