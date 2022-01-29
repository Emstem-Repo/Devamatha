package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UpdateStudentSGPAHelper {
	
	private static final Log log = LogFactory.getLog(UpdateStudentSGPAHelper.class);
	public static volatile UpdateStudentSGPAHelper updateStudentSGPAHelper = null;
	
	public static UpdateStudentSGPAHelper getInstance() {
	if (updateStudentSGPAHelper == null) {
		updateStudentSGPAHelper = new UpdateStudentSGPAHelper();
		return updateStudentSGPAHelper;
	}
	return updateStudentSGPAHelper;
	}
	public Integer convertIntenalMarks(
			List<Object[]> studentsInternalMarkForSubj) {

		Integer internalMark = 0;
		Integer totalIntMarks = 0;
		if (studentsInternalMarkForSubj != null) {
			//Iterator itr = studentsInternalMarkForSubj.iterator();
			for (Iterator iterator = studentsInternalMarkForSubj.iterator(); iterator
					.hasNext();) {

				Object[] row = (Object[]) iterator.next();

				if (row[0] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[0].toString()).intValue());
				}
				if (row[1] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[1].toString()).intValue());
				}
				if (row[2] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[2].toString()).intValue());
				}
				if (row[3] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[3].toString()).intValue());
				}
				if (row[4] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[4].toString()).intValue());
				}
				if (row[5] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[5].toString()).intValue());
				}

			}
			totalIntMarks = totalIntMarks + internalMark;
		}
		return totalIntMarks;

	}

}
