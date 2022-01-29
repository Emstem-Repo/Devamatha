package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.PeersEvaluationGroups;
import com.kp.cms.forms.admin.PeersEvaluationGroupsForm;
import com.kp.cms.to.admin.AssignPeersGroupsTo;

public interface IPeersEvaluationGroupsTransaction {

	public List<PeersEvaluationGroups> getPeersEvaluationGroupsList()throws Exception;

	public boolean checkDuplicate( PeersEvaluationGroupsForm peersEvaluationGroupsForm)throws Exception;

	public boolean addPeersEvaluationGroups( PeersEvaluationGroups evaluationGroups, String mode)throws Exception;

	public boolean deletePeersEvaluationGroups( PeersEvaluationGroupsForm peersEvaluationGroupsForm)throws Exception;

	

}
