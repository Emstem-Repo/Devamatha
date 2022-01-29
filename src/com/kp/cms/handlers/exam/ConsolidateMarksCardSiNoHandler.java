package com.kp.cms.handlers.exam;

import java.util.List;

import com.kp.cms.bo.exam.ConsolidateMarksCardSiNo;
import com.kp.cms.bo.exam.MarksCardSiNo;
import com.kp.cms.forms.exam.ConsolidateMarksCardSiNoForm;
import com.kp.cms.forms.exam.MarksCardSiNoForm;
import com.kp.cms.helpers.exam.ConsolidateMarksCardSiNoHelper;
import com.kp.cms.helpers.exam.MarksCardSiNoHelper;
import com.kp.cms.to.exam.ConsolidateMarksCardSiNoTO;
import com.kp.cms.to.exam.MarksCardSiNoTO;
import com.kp.cms.transactions.exam.IConsolidateMarksCardSiNoTransaction;
import com.kp.cms.transactions.exam.IMarksCardSiNoTransaction;
import com.kp.cms.transactionsimpl.exam.ConsolidateMarksCardSiNoTransactionImpl;
import com.kp.cms.transactionsimpl.exam.MarksCardSiNoTransactionImpl;

public class ConsolidateMarksCardSiNoHandler {
	private static ConsolidateMarksCardSiNoHandler consolidateMarksCardSiNoHandler=null;
	public static ConsolidateMarksCardSiNoHandler getInstance(){
		if(consolidateMarksCardSiNoHandler==null){
			consolidateMarksCardSiNoHandler = new ConsolidateMarksCardSiNoHandler();
		}
		return consolidateMarksCardSiNoHandler;
	}
	
	IConsolidateMarksCardSiNoTransaction transaction = new ConsolidateMarksCardSiNoTransactionImpl();
	
	
	public boolean save(ConsolidateMarksCardSiNoForm consolidateMarksCardSiNoForm) throws Exception{
		// TODO Auto-generated method stub
		ConsolidateMarksCardSiNo bo = ConsolidateMarksCardSiNoHelper.getInstance().convertFormToBo(consolidateMarksCardSiNoForm);
		return transaction.save(bo);
	}


	public boolean getData(ConsolidateMarksCardSiNoForm consolidateMarksCardSiNoForm)throws Exception {
		// TODO Auto-generated method stub
		return transaction.getDataAvailable(consolidateMarksCardSiNoForm);
	}

//shashi
	public List<ConsolidateMarksCardSiNoTO> getDataConvert()throws Exception {
		// TODO Auto-generated method stub
				return ConsolidateMarksCardSiNoHelper.getInstance().convertBotoTo(transaction.getData());
	}
}
