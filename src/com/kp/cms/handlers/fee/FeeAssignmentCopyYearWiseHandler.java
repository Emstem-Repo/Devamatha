package com.kp.cms.handlers.fee;

import java.util.List;

import com.kp.cms.bo.admin.Fee;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.fee.FeeAssignmentCopyForm;
import com.kp.cms.forms.fee.FeeAssignmentCopyYearWiseForm;
import com.kp.cms.helpers.fee.FeeAssignmentCopyHelper;
import com.kp.cms.helpers.fee.FeeAssignmentCopyYearWiseHelper;
import com.kp.cms.transactions.fee.IFeeAssignmentCopyTransaction;
import com.kp.cms.transactions.fee.IFeeAssignmentCopyYearWiseTransaction;
import com.kp.cms.transactionsimpl.fee.FeeAssignmentCopyTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeeAssignmentCopyYearWiseTransactionImpl;

public class FeeAssignmentCopyYearWiseHandler 
{

	private static FeeAssignmentCopyYearWiseHandler assignmentCopyHandler=null;
	private FeeAssignmentCopyYearWiseHandler()
	{
		
	}
	public static FeeAssignmentCopyYearWiseHandler getInstance() 
	{
		if(assignmentCopyHandler==null)
		{
			assignmentCopyHandler=new FeeAssignmentCopyYearWiseHandler();	
		}
		return assignmentCopyHandler;
	}
	IFeeAssignmentCopyYearWiseTransaction assignmentCopyTransaction=new FeeAssignmentCopyYearWiseTransactionImpl();
	
	public boolean copyFeeAssignmentCopy(FeeAssignmentCopyYearWiseForm assignmentCopyForm) throws Exception
	{
		boolean result=false;
		List<Fee>existingFeeDefinitions=assignmentCopyTransaction.getExistingFeeDefinitions(FeeAssignmentCopyYearWiseHelper.getInstance().getQueryToGetExistingFeesDefinition(assignmentCopyForm));
		if(existingFeeDefinitions!=null && existingFeeDefinitions.size()!=0)
		{
			boolean isFeeDefinitionToBeCopiedAlreadyExists=assignmentCopyTransaction.isFeeDefinitionToBeCopiedAlreadyExists(FeeAssignmentCopyYearWiseHelper.getInstance().getQueryToGetFeeDefinitionToBecopiedExists(assignmentCopyForm));
			if(isFeeDefinitionToBeCopiedAlreadyExists)
			{
				List<Fee>feeListToBeCopied=FeeAssignmentCopyYearWiseHelper.getInstance().getFeeListToBeCopied(existingFeeDefinitions,assignmentCopyForm);
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
