package com.kp.cms.helpers.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadStudentSubjectHistoryHelper {
	/**
	 * Singleton object of UploadStudentSubjectHistoryHelper
	 */
	private static volatile UploadStudentSubjectHistoryHelper uploadStudentSubjectHistoryHelper = null;
	private static final Log log = LogFactory.getLog(UploadStudentSubjectHistoryHelper.class);
	private UploadStudentSubjectHistoryHelper() {
		
	}
	/**
	 * return singleton object of UploadStudentSubjectHistoryHelper.
	 * @return
	 */
	public static UploadStudentSubjectHistoryHelper getInstance() {
		if (uploadStudentSubjectHistoryHelper == null) {
			uploadStudentSubjectHistoryHelper = new UploadStudentSubjectHistoryHelper();
		}
		return uploadStudentSubjectHistoryHelper;
	}
}
