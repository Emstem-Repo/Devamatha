package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PeersEvaluationFeedbackInstructions;
import com.kp.cms.forms.admin.PeersEvaluationFeedbackInstuctionsForm;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;

public class PeersEvaluationFeedbackInstructionsHelper {
	private static volatile PeersEvaluationFeedbackInstructionsHelper helper = null;
	private static final Log log = LogFactory.getLog(PeersEvaluationFeedbackInstructionsHelper.class);
	public static PeersEvaluationFeedbackInstructionsHelper getInstance(){
		if(helper == null){
			helper = new PeersEvaluationFeedbackInstructionsHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	public List<StudentFeedbackInstructionsTO> convertBOListToTOList(
			List<PeersEvaluationFeedbackInstructions> boList)throws Exception {
		List<StudentFeedbackInstructionsTO> toList = new ArrayList<StudentFeedbackInstructionsTO>();
		if(boList!=null && !boList.isEmpty()){
			Iterator<PeersEvaluationFeedbackInstructions> iterator = boList.iterator();
			while (iterator.hasNext()) {
				PeersEvaluationFeedbackInstructions bo = (PeersEvaluationFeedbackInstructions) iterator
						.next();
				StudentFeedbackInstructionsTO to = new StudentFeedbackInstructionsTO();
				if(bo.getId()!=0){
					to.setId(bo.getId());
				}
				if(bo.getDescription()!=null && !bo.getDescription().isEmpty()){
					to.setDescription(bo.getDescription());
				}
				toList.add(to);
			}
		}
		return toList;
	}
	/**
	 * @param instructionsForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public PeersEvaluationFeedbackInstructions saveOrupdateInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm,String mode) throws Exception{
		PeersEvaluationFeedbackInstructions instructions = new PeersEvaluationFeedbackInstructions();
		if(mode.equalsIgnoreCase("Add")){
			if(instructionsForm.getDescription()!=null && !instructionsForm.getDescription().isEmpty()){
				instructions.setDescription(instructionsForm.getDescription());
			}
			instructions.setCreatedBy(instructionsForm.getUserId());
			instructions.setCreatedDate(new Date());
		}else if(mode.equalsIgnoreCase("Edit")){
			if(instructionsForm.getId()!=0){
				instructions.setId(instructionsForm.getId());
			}
			if(instructionsForm.getDescription()!=null && !instructionsForm.getDescription().isEmpty()){
				instructions.setDescription(instructionsForm.getDescription());
			}
			instructions.setModifiedBy(instructionsForm.getUserId());
			instructions.setLastModifiedDate(new Date());
		}
		return instructions;
	}
}
