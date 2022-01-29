package com.kp.cms.helpers.employee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadPayScaleGradeHelper {
	/**
	 * Singleton object of UploadPayScaleGradeHelper
	 */
	private static volatile UploadPayScaleGradeHelper uploadPayScaleGradeHelper = null;
	private static final Log log = LogFactory.getLog(UploadPayScaleGradeHelper.class);
	private UploadPayScaleGradeHelper() {
		
	}
	/**
	 * return singleton object of UploadPayScaleGradeHelper.
	 * @return
	 */
	public static UploadPayScaleGradeHelper getInstance() {
		if (uploadPayScaleGradeHelper == null) {
			uploadPayScaleGradeHelper = new UploadPayScaleGradeHelper();
		}
		return uploadPayScaleGradeHelper;
	}
}
