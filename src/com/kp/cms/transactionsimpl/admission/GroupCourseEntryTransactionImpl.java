package com.kp.cms.transactionsimpl.admission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.transactions.admission.IGroupCourseEntryTransaction;

public class GroupCourseEntryTransactionImpl implements
		IGroupCourseEntryTransaction {

	/**
	 * Singleton object of GroupCourseEntryTransactionImpl
	 */
	private static volatile GroupCourseEntryTransactionImpl groupCourseEntryTransactionImpl = null;
	private static final Log log = LogFactory.getLog(GroupCourseEntryTransactionImpl.class);
	private GroupCourseEntryTransactionImpl() {
		
	}
	/**
	 * return singleton object of GroupCourseEntryTransactionImpl.
	 * @return
	 */
	public static GroupCourseEntryTransactionImpl getInstance() {
		if (groupCourseEntryTransactionImpl == null) {
			groupCourseEntryTransactionImpl = new GroupCourseEntryTransactionImpl();
		}
		return groupCourseEntryTransactionImpl;
	}
}
