package com.kp.cms.transactionsimpl.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.transactions.exam.ISupplementaryFeesTxn;

public class SupplementaryFeesTxnImpl implements ISupplementaryFeesTxn {
	/**
	 * Singleton object of SupplementaryFeesTxnImpl
	 */
	private static volatile SupplementaryFeesTxnImpl supplementaryFeesTxnImpl = null;
	private static final Log log = LogFactory.getLog(SupplementaryFeesTxnImpl.class);
	private SupplementaryFeesTxnImpl() {
		
	}
	/**
	 * return singleton object of SupplementaryFeesTxnImpl.
	 * @return
	 */
	public static SupplementaryFeesTxnImpl getInstance() {
		if (supplementaryFeesTxnImpl == null) {
			supplementaryFeesTxnImpl = new SupplementaryFeesTxnImpl();
		}
		return supplementaryFeesTxnImpl;
	}
}
