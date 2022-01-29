package com.kp.cms.transactionsimpl.admission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.transactions.admission.IGroupEntryTransaction;

public class GroupEntryTransactionImpl implements IGroupEntryTransaction {
	/**
	 * Singleton object of GroupEntryTransactionImpl
	 */
	private static volatile GroupEntryTransactionImpl groupEntryTransactionImpl = null;
	private static final Log log = LogFactory.getLog(GroupEntryTransactionImpl.class);
	private GroupEntryTransactionImpl() {
		
	}
	/**
	 * return singleton object of GroupEntryTransactionImpl.
	 * @return
	 */
	public static GroupEntryTransactionImpl getInstance() {
		if (groupEntryTransactionImpl == null) {
			groupEntryTransactionImpl = new GroupEntryTransactionImpl();
		}
		return groupEntryTransactionImpl;
	}
}
