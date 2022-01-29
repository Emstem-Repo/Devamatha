package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.PeersEvaluationGroups;
import com.kp.cms.forms.admin.PeersEvaluationGroupsForm;
import com.kp.cms.to.admin.PeersEvaluationGroupsTO;

public class PeersEvaluationGroupsHelper {
	public static volatile PeersEvaluationGroupsHelper helper = null;
	public static PeersEvaluationGroupsHelper getInstance(){
		if(helper == null){
			helper = new PeersEvaluationGroupsHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param peersEvaluationGroupBo
	 * @return
	 * @throws Exception
	 */
	public List<PeersEvaluationGroupsTO> convertBOToTO( List<PeersEvaluationGroups> peersEvaluationGroupBo) throws Exception{
		List<PeersEvaluationGroupsTO> list = new ArrayList<PeersEvaluationGroupsTO>();
		if(peersEvaluationGroupBo!=null && !peersEvaluationGroupBo.isEmpty()){
			Iterator<PeersEvaluationGroups> iterator = peersEvaluationGroupBo.iterator();
			while (iterator.hasNext()) {
				PeersEvaluationGroups peersEvaluationGroups = (PeersEvaluationGroups) iterator .next();
				PeersEvaluationGroupsTO to = new PeersEvaluationGroupsTO();
				if(peersEvaluationGroups.getId()!=0){
					to.setId(peersEvaluationGroups.getId());
				}
				if(peersEvaluationGroups.getName()!=null && !peersEvaluationGroups.getName().isEmpty()){
					to.setName(peersEvaluationGroups.getName());
				}
				list.add(to);
			}
		}
		return list;
	}
	/**
	 * @param peersEvaluationGroupsForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public PeersEvaluationGroups convertFormTOTo( PeersEvaluationGroupsForm peersEvaluationGroupsForm, String mode) throws Exception{
		PeersEvaluationGroups evaluationGroups = new PeersEvaluationGroups();
		if(mode.equalsIgnoreCase("Add")){
			if(peersEvaluationGroupsForm.getId()!=0){
				evaluationGroups.setId(peersEvaluationGroupsForm.getId());
			}
			if(peersEvaluationGroupsForm.getName()!=null && !peersEvaluationGroupsForm.getName().isEmpty()){
				evaluationGroups.setName(peersEvaluationGroupsForm.getName());
			}
			evaluationGroups.setCreatedBy(peersEvaluationGroupsForm.getUserId());
			evaluationGroups.setCreatedDate(new Date());
			evaluationGroups.setIsActive(true);
		}else if(mode.equalsIgnoreCase("Edit")){
			if(peersEvaluationGroupsForm.getId()!=0){
				evaluationGroups.setId(peersEvaluationGroupsForm.getId());
			}
			if(peersEvaluationGroupsForm.getName()!=null && !peersEvaluationGroupsForm.getName().isEmpty()){
				evaluationGroups.setName(peersEvaluationGroupsForm.getName());
			}
			evaluationGroups.setIsActive(true);
			evaluationGroups.setModifiedBy(peersEvaluationGroupsForm.getUserId());
			evaluationGroups.setLastModifiedBy(new Date());
		}
		return evaluationGroups;
	}
}
