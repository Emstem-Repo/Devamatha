package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.helpers.admin.PreRequesiteExamHelper;
import com.kp.cms.to.admin.PrerequisiteTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;

public class PreRequesiteExamHandler 
{
	public static volatile PreRequesiteExamHandler preRequesiteExamHandler = null;

	public static PreRequesiteExamHandler getInstance() {
		if (preRequesiteExamHandler == null) {
			preRequesiteExamHandler = new PreRequesiteExamHandler();
			return preRequesiteExamHandler;
		}
		return preRequesiteExamHandler;
	}
	
	public List <PrerequisiteTO> getPrerequisite() throws Exception
	{
		
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl
		.getInstance();
		List<Prerequisite> prerequisiteList = singleFieldMasterTransaction
		.getPrerequisiteFields();
		List <PrerequisiteTO> preTo=PreRequesiteExamHelper.getInstance().getTOFromBO(prerequisiteList);
		return preTo;
	}
	
}
