package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IAdmissionBioDataReportTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AdmissionBioDataReportTxnImpl implements IAdmissionBioDataReportTransaction {
	private static volatile AdmissionBioDataReportTxnImpl admissionBioDataReportTxnImpl = null;
	public static AdmissionBioDataReportTxnImpl getInstance(){
		if(admissionBioDataReportTxnImpl == null){
			admissionBioDataReportTxnImpl = new  AdmissionBioDataReportTxnImpl();
			return admissionBioDataReportTxnImpl;
		}
		return admissionBioDataReportTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionBioDataReportTransaction#getCourseMap()
	 */
	@Override
	public List<AdmBioDataCJC> getAdmBioDataDetails(StringBuffer query) throws Exception {
		 List<AdmBioDataCJC> admBioDataList;
		 Session session = null;
		 try{
			 session = HibernateUtil.getSession();
			 Query qry = session.createQuery(query.toString());
			 admBioDataList = qry.list();
		 }catch (Exception e) {
				throw new ApplicationException(e);
			}
		return admBioDataList;
	}
}
