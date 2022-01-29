package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.FeesConcessionReportForm;
import com.kp.cms.transactions.reports.IFeesConcessionReportTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class FeesConcessionReportTxnImpl implements IFeesConcessionReportTxn{
	private static final Log log = LogFactory.getLog(FeesConcessionReportTxnImpl.class);
	public static volatile FeesConcessionReportTxnImpl feesConcessionReportTxnImpl = null;

	public static FeesConcessionReportTxnImpl getInstance() {
		if (feesConcessionReportTxnImpl == null) {
			feesConcessionReportTxnImpl = new FeesConcessionReportTxnImpl();
			return feesConcessionReportTxnImpl;
		}
		return feesConcessionReportTxnImpl;
	}
	/**
	 * getting concession details
	 */
	public List<Object[]> getFeeConcessionDetails(FeesConcessionReportForm concessionReportForm) throws Exception{
		log.debug("start getFeeConcessionDetails");
		Session session = null;
		try {
 			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session =InitSessionFactory.getInstance().openSession();*/
			session = HibernateUtil.getSession();
			
			StringBuffer feePaymentString = new StringBuffer("select sum(fee.concessionAmount), fee.feePayment.studentName,"+
				"fee.feePayment.challenPrintedDate, fee.feePayment.applicationNo,"+
				"fee.feePayment.registrationNo, fee.feePayment.student.classSchemewise.classes.name,"+
				"fee.feePayment.concessionVoucherNo, sum(fee.feePayment.totalAmount), fee.feePayment.isCancelChallan" +
				" from FeePaymentDetail fee" +
				" where fee.feePayment.course.program.programType.id = :programType" +
				" and  fee.concessionAmount > 0 ");

			
			if(concessionReportForm.getDivId()!= null && !concessionReportForm.getDivId().trim().isEmpty()){
				feePaymentString.append(" and fee.feeAccount.feeDivision.id = :divId");
			}
			if(concessionReportForm.getAccId()!= null && !concessionReportForm.getAccId().trim().isEmpty()){
				feePaymentString.append(" and fee.feeAccount.id = :accId");
			}
			if(concessionReportForm.getClassId()!= null && !concessionReportForm.getClassId().trim().isEmpty() && concessionReportForm.getClassOrDate()){
				feePaymentString.append(" and fee.feePayment.student.classSchemewise.classes.id = :classId");
			}
			if(concessionReportForm.getStartDate()!= null && !concessionReportForm.getStartDate().trim().isEmpty() && !concessionReportForm.getClassOrDate()){
				feePaymentString.append(" and fee.feePayment.challenPrintedDate between :startDate and :endDate");
			}
			feePaymentString.append(" group by fee.feePayment.id order by fee.feePayment.challenPrintedDate, fee.feePayment.student.classSchemewise.classes.id");
			
			Query  query = session.createQuery(feePaymentString.toString());
			query.setString("programType", concessionReportForm.getProgramTypeId());
			if(concessionReportForm.getStartDate()!= null && !concessionReportForm.getStartDate().trim().isEmpty() && !concessionReportForm.getClassOrDate()){
				query.setDate("startDate", CommonUtil.ConvertStringToSQLDate(concessionReportForm.getStartDate()));
				query.setDate("endDate", CommonUtil.ConvertStringToSQLDate(concessionReportForm.getEndDate()));
			}
			if(concessionReportForm.getClassId()!= null && !concessionReportForm.getClassId().trim().isEmpty() && concessionReportForm.getClassOrDate()){
				query.setString("classId", concessionReportForm.getClassId());
			}
			if(concessionReportForm.getDivId()!= null && !concessionReportForm.getDivId().trim().isEmpty()){
				query.setString("divId", concessionReportForm.getDivId());
			}
			if(concessionReportForm.getAccId()!= null && !concessionReportForm.getAccId().trim().isEmpty()){
				query.setString("accId", concessionReportForm.getAccId());
			}
			List<Object[]> feePaymentList = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getFeeConcessionDetails");
			return feePaymentList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}

		
}
