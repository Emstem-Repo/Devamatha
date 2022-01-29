package com.kp.cms.transactionsimpl.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.fees.FeeRefund;
import com.kp.cms.forms.fee.FeeRefundForm;
import com.kp.cms.transactions.fee.IFeeRefundTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class FeeRefundTxnImpl implements IFeeRefundTransaction {
	

    public static volatile FeeRefundTxnImpl feeRefundTxnImpl = null;
    private static Log log = LogFactory.getLog(FeeRefundTxnImpl.class);
  
    /**
     * @return
     */
    /**
     * @return
     */
    public static FeeRefundTxnImpl getInstance() {
		if (feeRefundTxnImpl == null) {
			feeRefundTxnImpl = new FeeRefundTxnImpl();
			return feeRefundTxnImpl;
		}
		return feeRefundTxnImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeRefundTransaction#getStudentDetailsByChallanNo(com.kp.cms.forms.fee.FeeRefundForm)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStudentDetailsByChallanNo(FeeRefundForm refundForm)throws Exception {
		Session session=null;
		List<Object[]> paymentList=null;
		try{
			session=HibernateUtil.getSession();
			String quer=" select student.register_no,personal_data.first_name as studentName,classes.name as className, "+
						" fee_payment.total_fee_paid,fee_group.name as groupName,fee_payment.challen_printed_date," +
						" student.id as studentId from fee_payment_detail_feegroup "+
						" inner join fee_payment_detail ON fee_payment_detail_feegroup.fee_payment_detail_id = fee_payment_detail.id "+
						" inner join fee_group ON fee_payment_detail_feegroup.fee_group_id = fee_group.id "+
						" inner join fee_payment ON fee_payment_detail.fee_payment_id = fee_payment.id "+
						" inner join student on fee_payment.student_id = student.id "+
						" inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
						" inner join classes ON class_schemewise.class_id = classes.id "+
						" inner join adm_appln ON student.adm_appln_id = adm_appln.id "+
						" inner join personal_data ON adm_appln.personal_data_id = personal_data.id "+
						" where fee_payment.bill_no='"+refundForm.getChallanNo()+"' "+
						" and fee_payment.academic_year='"+refundForm.getAcademicYear()+"'"+
						" and fee_payment.is_challan_canceled=0 "+
						" and fee_payment_detail_feegroup.is_active=1 "+
						" group by student.id";
			Query query=session.createSQLQuery(quer);
			paymentList = query.list();
		}catch(Exception e){
			log.info("exception occured in getStudentDetailsByChallanNo Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return paymentList;
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeRefundTransaction#getPayModeList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FeePaymentMode> getPayModeList() throws Exception {
		Session session=null;
		List<FeePaymentMode> paymentList=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from FeePaymentMode fee where fee.isActive=1";
			Query query=session.createQuery(quer);
			paymentList = query.list();
		}catch(Exception e){
			log.info("exception occured in getStudentDetailsByChallanNo Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return paymentList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeRefundTransaction#checkChallanNoAlreadyExist(com.kp.cms.forms.fee.FeeRefundForm)
	 */
	@Override
	public FeeRefund checkChallanNoAlreadyExist(FeeRefundForm refundForm)
			throws Exception {
		Session session=null;
		FeeRefund feeRefund=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from FeeRefund refund where refund.academicYear='"+refundForm.getAcademicYear()+"'" +
					" and refund.challanNo='"+refundForm.getChallanNo()+"' and refund.isActive=1" +
					" and refund.challanDate='"+CommonUtil.ConvertStringToSQLDate(refundForm.getChallanDate())+"'";
			Query query=session.createQuery(quer);
			feeRefund = (FeeRefund) query.uniqueResult();
		}catch(Exception e){
			log.info("exception occured in getStudentDetailsByChallanNo Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return feeRefund;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeRefundTransaction#saveFeeRefundAmount(com.kp.cms.bo.fees.FeeRefund)
	 */
	@Override
	public boolean saveOrUpdateFeeRefundAmount(FeeRefund refund,String mode) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isSaved=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
			session.save(refund);
			}else{
				session.merge(refund);
			}
			isSaved=true;
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saveFeeRefundAmount data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return isSaved;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeRefundTransaction#getFeeRefundDetailsById(int)
	 */
	@Override
	public FeeRefund getFeeRefundDetailsById(int refundId) throws Exception {
		Session session=null;
		FeeRefund feeRefund=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from FeeRefund refund where refund.isActive=1 and refund.id="+refundId;
			Query query=session.createQuery(quer);
			feeRefund = (FeeRefund) query.uniqueResult();
		}catch(Exception e){
			log.info("exception occured in getStudentDetailsByChallanNo Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return feeRefund;
	}

}
