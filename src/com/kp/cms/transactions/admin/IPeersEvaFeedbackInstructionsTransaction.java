package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.PeersEvaluationFeedbackInstructions;
import com.kp.cms.forms.admin.PeersEvaluationFeedbackInstuctionsForm;

public interface IPeersEvaFeedbackInstructionsTransaction {

	List<PeersEvaluationFeedbackInstructions> getInstructionsList()throws Exception;

	boolean getCheckDuplicateInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm)throws Exception;

	boolean saveOrUpdateInstructions( PeersEvaluationFeedbackInstructions instructionsBo,String mode)throws  Exception;

	PeersEvaluationFeedbackInstructions getEditFeedbackInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm) throws Exception;

	boolean deletePeersInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm)throws Exception;

}
