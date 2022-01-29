package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admission.DemandSlipInstruction;
import com.kp.cms.forms.admission.FeeDemandSlipInstructionForm;

	public interface IFeeDemandSlipInstructionTransaction {
	
		public DemandSlipInstruction checkDuplicate(int courseId,int schemeNo) throws Exception;

		public boolean addSlipInstruction(DemandSlipInstruction deSlipInstructionBO) throws Exception;

		public DemandSlipInstruction getDetailsToEdit(int id) throws Exception;

		public List<DemandSlipInstruction> getDetailsToDisplay() throws Exception;

		public boolean deleteSlipInstruction(int id,Boolean activate, String userId) throws Exception;
	
	}
