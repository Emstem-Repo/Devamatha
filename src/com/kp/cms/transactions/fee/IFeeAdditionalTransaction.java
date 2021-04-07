package com.kp.cms.transactions.fee;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.FeeAdditional;

public interface IFeeAdditionalTransaction {
	
	public List<FeeAdditional> getFeeAdditional(Set<Integer> feeGroupSet) throws Exception;
	
	public boolean addFeeAdditionalAssignment(FeeAdditional feeAdditional) throws ConstraintViolationException,Exception;
	public List<FeeAdditional> getAllAdditionalFees() throws Exception;
	public boolean deleteFeeAdditionalAssignment(FeeAdditional feeAdditional) throws Exception;
	public boolean activateFeeAdditionalAssignment(FeeAdditional feeAdditional) throws Exception;
	public boolean updateFeeAdditionalAssignment(FeeAdditional feeAdditionale) throws Exception;
	public FeeAdditional getFeeAdditionalByCompositeKeys(FeeAdditional feeOld) throws Exception;
	public FeeAdditional getFeeAdditionalAssignmentById(int feeId) throws Exception;
	public Map<Integer,String> getAllFeesGroup() throws Exception;
	
}
