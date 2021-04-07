package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PeersEvaluationFeedbackInstructions;
import com.kp.cms.forms.admin.PeersEvaluationFeedbackInstuctionsForm;
import com.kp.cms.helpers.admin.PeersEvaluationFeedbackInstructionsHelper;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;
import com.kp.cms.transactions.admin.IPeersEvaFeedbackInstructionsTransaction;
import com.kp.cms.transactionsimpl.admin.PeersEvaluationFeedbackInstructionsTxnImpl;

public class PeersEvaluationFeedbackInstructionsHandler {
	private static volatile PeersEvaluationFeedbackInstructionsHandler handler = null;
	private static final Log log = LogFactory.getLog(PeersEvaluationFeedbackInstructionsHandler.class);
	public static PeersEvaluationFeedbackInstructionsHandler getInstance(){
		if(handler == null){
			handler = new PeersEvaluationFeedbackInstructionsHandler();
			return handler;
		}
		return handler;
	}
	IPeersEvaFeedbackInstructionsTransaction itransaction = new PeersEvaluationFeedbackInstructionsTxnImpl();
	/**
	 * @return
	 * @throws Exception
	 */
	public List<StudentFeedbackInstructionsTO> getPeersInstructionsList()throws Exception {
		List<PeersEvaluationFeedbackInstructions> boList = itransaction.getInstructionsList();
		List<StudentFeedbackInstructionsTO> instructionsTOsList = PeersEvaluationFeedbackInstructionsHelper.getInstance().convertBOListToTOList(boList);
		return instructionsTOsList;
	}
	/**
	 * @param instructionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicateInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm) throws Exception{
		boolean isDuplicate = itransaction.getCheckDuplicateInstructions(instructionsForm);
		return isDuplicate;
	}
	/**
	 * @param instructionsForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addPeersInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm,String mode) throws Exception{
		PeersEvaluationFeedbackInstructions instructionsBo = PeersEvaluationFeedbackInstructionsHelper.getInstance().saveOrupdateInstructions(instructionsForm,mode);
		boolean isAdded = itransaction.saveOrUpdateInstructions(instructionsBo,mode);
		return isAdded;
	}
	/**
	 * @param instructionsForm
	 * @throws Exception
	 */
	public void editFeedbackInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm)throws Exception {
		PeersEvaluationFeedbackInstructions instructions = itransaction.getEditFeedbackInstructions(instructionsForm);
		if(instructions!=null && !instructions.toString().isEmpty()){
			instructionsForm.setId(instructions.getId());
			instructionsForm.setDescription(instructions.getDescription());
		}
		
	}
	public boolean deleteInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm) throws Exception{
		boolean isDelete = itransaction.deletePeersInstructions(instructionsForm);
		return isDelete;
	}
}
