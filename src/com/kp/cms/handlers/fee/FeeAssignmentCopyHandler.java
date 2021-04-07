package com.kp.cms.handlers.fee;

import java.util.List;

import com.kp.cms.bo.admin.Fee;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.fee.FeeAssignmentCopyForm;
import com.kp.cms.helpers.fee.FeeAssignmentCopyHelper;
import com.kp.cms.transactions.fee.IFeeAssignmentCopyTransaction;
import com.kp.cms.transactionsimpl.fee.FeeAssignmentCopyTransactionImpl;

public class FeeAssignmentCopyHandler 
{

	private static FeeAssignmentCopyHandler assignmentCopyHandler=null;
	private FeeAssignmentCopyHandler()
	{
		
	}
	public static FeeAssignmentCopyHandler getInstance() 
	{
		if(assignmentCopyHandler==null)
		{
			assignmentCopyHandler=new FeeAssignmentCopyHandler();	
		}
		return assignmentCopyHandler;
	}
	IFeeAssignmentCopyTransaction assignmentCopyTransaction=new FeeAssignmentCopyTransactionImpl();
	
	public boolean copyFeeAssignmentCopy(FeeAssignmentCopyForm assignmentCopyForm) throws Exception
	{
		boolean result=false;
		List<Fee>existingFeeDefinitions=assignmentCopyTransaction.getExistingFeeDefinitions(FeeAssignmentCopyHelper.getInstance().getQueryToGetExistingFeesDefinition(assignmentCopyForm));
		if(existingFeeDefinitions!=null && existingFeeDefinitions.size()!=0)
		{
			boolean isFeeDefinitionToBeCopiedAlreadyExists=assignmentCopyTransaction.isFeeDefinitionToBeCopiedAlreadyExists(FeeAssignmentCopyHelper.getInstance().getQueryToGetFeeDefinitionToBecopiedExists(assignmentCopyForm));
			if(isFeeDefinitionToBeCopiedAlreadyExists)
			{
				List<Fee>feeListToBeCopied=FeeAssignmentCopyHelper.getInstance().getFeeListToBeCopied(existingFeeDefinitions,assignmentCopyForm);
				result=assignmentCopyTransaction.copyFees(feeListToBeCopied);
			}
			else
			{
				throw new DataNotFoundException("Fee definition already exists");
			}
		}
		else
		{
			throw new DataNotFoundException("No Existing FeeDefinitions");
		}
		return result;	
		
	}
	public boolean copyFeeAssignmentCopyCourse(FeeAssignmentCopyForm assignmentCopyForm) throws Exception
	{
		boolean result=false;
		
		List<Fee>existingFeeDefinitions=assignmentCopyTransaction.getExistingFeeDefinitions(FeeAssignmentCopyHelper.getInstance().getQueryToGetExistingFeesDefinitionCourse(assignmentCopyForm));
		if(existingFeeDefinitions!=null && existingFeeDefinitions.size()!=0)
		{
			boolean isFeeDefinitionToBeCopiedAlreadyExists=assignmentCopyTransaction.isFeeDefinitionToBeCopiedAlreadyExists(FeeAssignmentCopyHelper.getInstance().getQueryToGetFeeDefinitionToBecopiedExistsCourse(assignmentCopyForm));
			if(isFeeDefinitionToBeCopiedAlreadyExists)
			{
				List<Fee>feeListToBeCopied=FeeAssignmentCopyHelper.getInstance().getFeeListToBeCopiedCourse(existingFeeDefinitions,assignmentCopyForm);
				result=assignmentCopyTransaction.copyFees(feeListToBeCopied);
			}
			else
			{
				throw new DataNotFoundException("Fee definition already exists");
			}
		}
		else
		{
			throw new DataNotFoundException("No Existing FeeDefinitions");
		}
		return result;	
		
	}

}
