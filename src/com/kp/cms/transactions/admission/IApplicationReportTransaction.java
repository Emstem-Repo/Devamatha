package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.bo.admin.Student;


/**
 * 
 * @author kshirod.k
 * Transaction interface for Application Report
 *
 */

public interface IApplicationReportTransaction {
	/**
	 * Gets issued applications from Offline Details table
	 */
	public List<OfflineDetails> getIssuedApplications(int courseId, int year)throws Exception;
	/**
	 * 
	 * @param courseId
	 * @param year
	 * @returns all the received applications from AdmAppln table
	 * @throws Exception
	 */
	public List<Student> getreceivedApplications(int courseId, int year)throws Exception;

}
