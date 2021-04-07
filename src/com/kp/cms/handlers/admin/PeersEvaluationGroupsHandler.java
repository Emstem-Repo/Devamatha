package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.PeersEvaluationGroups;
import com.kp.cms.forms.admin.AssignPeersGroupsForm;
import com.kp.cms.forms.admin.PeersEvaluationGroupsForm;
import com.kp.cms.helpers.admin.PeersEvaluationGroupsHelper;
import com.kp.cms.to.admin.AssignPeersGroupsTo;
import com.kp.cms.to.admin.PeersEvaluationGroupsTO;
import com.kp.cms.transactions.admin.IPeersEvaluationGroupsTransaction;
import com.kp.cms.transactionsimpl.admin.PeersEvaluationGroupsTxnImpl;

public class PeersEvaluationGroupsHandler {
 public static volatile PeersEvaluationGroupsHandler handler = null;
 public static PeersEvaluationGroupsHandler getInstance(){
	 if(handler == null){
		 handler = new PeersEvaluationGroupsHandler();
		 return handler;
	 }
	 return handler;
 }
 IPeersEvaluationGroupsTransaction transaction = PeersEvaluationGroupsTxnImpl.getInstance();
/**
 * @return
 * @throws Exception
 */
public List<PeersEvaluationGroupsTO> getPeersEvaluationGroupsList() throws Exception{
	List<PeersEvaluationGroups> peersEvaluationGroupBo=transaction.getPeersEvaluationGroupsList();
	List<PeersEvaluationGroupsTO> groupsTOs = PeersEvaluationGroupsHelper.getInstance().convertBOToTO(peersEvaluationGroupBo);
	return groupsTOs;
}
/**
 * @param peersEvaluationGroupsForm
 * @return
 * @throws Exception
 */
public boolean checkDuplicate( PeersEvaluationGroupsForm peersEvaluationGroupsForm) throws Exception{
	boolean isDuplicate = transaction.checkDuplicate(peersEvaluationGroupsForm);
	return isDuplicate;
}
/**
 * @param peersEvaluationGroupsForm
 * @param mode 
 * @return
 * @throws Exception
 */
public boolean addPeersEvaluationGroups( PeersEvaluationGroupsForm peersEvaluationGroupsForm, String mode) throws Exception{
	PeersEvaluationGroups evaluationGroups = PeersEvaluationGroupsHelper.getInstance().convertFormTOTo(peersEvaluationGroupsForm,mode);
	boolean isAdded = transaction.addPeersEvaluationGroups(evaluationGroups,mode);
	return isAdded;
}
/**
 * @param peersEvaluationGroupsForm 
 * @return
 * @throws Exception
 */
public boolean deletePeersEvaluationGroups(PeersEvaluationGroupsForm peersEvaluationGroupsForm)throws Exception {
	boolean isDeleted = transaction.deletePeersEvaluationGroups(peersEvaluationGroupsForm);
	return isDeleted;
}

}
