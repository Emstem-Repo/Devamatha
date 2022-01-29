package com.kp.cms.transactionsimpl.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AdmFeeMain;
import com.kp.cms.bo.admin.FeesFeeDetails;
import com.kp.cms.bo.admin.PettyCashCollectionDetails;
import com.kp.cms.forms.fee.FeeMainDetailsReportForm;
import com.kp.cms.transactions.fee.IFeeMainDetailsTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class FeeMainDetailsReportTxnImpl implements IFeeMainDetailsTxn{
	private static final Log log = LogFactory
	.getLog(FeeMainDetailsReportTxnImpl.class);
private static volatile FeeMainDetailsReportTxnImpl feeMainTxnImpl = null;
	
	public static FeeMainDetailsReportTxnImpl getInstance() {
		   if(feeMainTxnImpl == null ){
			   feeMainTxnImpl = new FeeMainDetailsReportTxnImpl();
			   return feeMainTxnImpl;
		   }
		   return feeMainTxnImpl;
	}

	@Override
	public List<AdmFeeMain> getFeeMainDetails(
			FeeMainDetailsReportForm feeMainDetailsReportForm) throws Exception {
		log.info("entering into getFeeMainDetails in FeeMainDetailsReportTxnImpl class");
		Session session = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			 String queryString="from AdmFeeMain fee where fee.date >='"+CommonUtil.ConvertStringToSQLDate(feeMainDetailsReportForm.getStartDate())+"' and fee.date <= '"+CommonUtil.ConvertStringToSQLDate(feeMainDetailsReportForm.getEndDate())+"' and fee.academicYear ="+feeMainDetailsReportForm.getAcademicYear();
			 Query query = session.createQuery(queryString);
			 List<AdmFeeMain> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getFeeMainDetails in FeeMainDetailsReportTxnImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getFeeMainDetails data..." , e);
			 throw e;
		 }
	}

	@Override
	public List<FeesFeeDetails> getFeeDetailsWithBillNo(int billNo)
			throws Exception {
		log.info("entering into getFeeDetailsWithBillNo in FeeMainDetailsReportTxnImpl class");
		Session session = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			 String queryString="from FeesFeeDetails details where details.billNo='"+billNo+"'";
			 Query query = session.createQuery(queryString);
			 List<FeesFeeDetails> details = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getFeeDetailsWithBillNo in FeeMainDetailsReportTxnImpl class");
			 return details;
		 } catch (Exception e) {
			 log.error("Error during getFeeDetailsWithBillNo data..." , e);
			 throw e;
		 }
	}
}
