package com.kp.cms.helpers.admission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadSecondLanguageHelper {
	/**
	 * Singleton object of UploadSecondLanguageHelper
	 */
	private static volatile UploadSecondLanguageHelper uploadSecondLanguageHelper = null;
	private static final Log log = LogFactory.getLog(UploadSecondLanguageHelper.class);
	private UploadSecondLanguageHelper() {
		
	}
	/**
	 * return singleton object of UploadSecondLanguageHelper.
	 * @return
	 */
	public static UploadSecondLanguageHelper getInstance() {
		if (uploadSecondLanguageHelper == null) {
			uploadSecondLanguageHelper = new UploadSecondLanguageHelper();
		}
		return uploadSecondLanguageHelper;
	}
	/**
	 * @param applicationYear
	 * @param courseId
	 * @return
	 */
	public String getQuery(int applicationYear, int courseId) {
		String query="select s.registerNo,s.admAppln.personalData.id from Student s where s.registerNo <> null " +
				" and s.registerNo <> '' and s.isActive=1 and s.isAdmitted=1 and s.admAppln.isApproved=1 " +
				" and s.admAppln.appliedYear= "+applicationYear+" and  s.admAppln.courseBySelectedCourseId.id="+courseId;
		return query;
	}
}
