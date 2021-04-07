package com.kp.cms.handlers.admission;

import java.util.List;

import com.kp.cms.helpers.admission.PublishAdmitCardHelper;
import com.kp.cms.to.admission.PublishAdmitCardTO;
import com.kp.cms.transactions.admission.IPublishAdmitCardTransaction;
import com.kp.cms.transactionsimpl.admission.PublishAdmitCardTransactionImpl;

public class PublishAdmitCardHandler {
	
	private static volatile PublishAdmitCardHandler publishAdmitCardHandler = null;
	
	private PublishAdmitCardHandler() {
		// an Empty singleton constructor
	}
	
	public static PublishAdmitCardHandler getInstance() {
		if(publishAdmitCardHandler == null) {
			publishAdmitCardHandler = new PublishAdmitCardHandler();
		}
		return publishAdmitCardHandler;
		
	}
	
	public List<PublishAdmitCardTO> getSelectedList() {

		IPublishAdmitCardTransaction publishAdmitCardTransaction = new PublishAdmitCardTransactionImpl();
		List<PublishAdmitCardTO> publishAdmitCardTO = PublishAdmitCardHelper
				.convertBoToTo(publishAdmitCardTransaction.getCandidatesList());

		return publishAdmitCardTO;

	}

}
