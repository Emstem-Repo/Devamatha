package com.kp.cms.transactionsimpl.phd;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.phd.PhdFeePaymentStatusForm;
import com.kp.cms.transactions.phd.IPhdFeePaymentStatus;
import com.kp.cms.utilities.HibernateUtil;

public class PhdFeePaymentStatusTransactionImpl implements IPhdFeePaymentStatus{
	private static final Log log = LogFactory .getLog(PhdFeePaymentStatusTransactionImpl.class);
 private static volatile PhdFeePaymentStatusTransactionImpl transactionImpl = null;
 private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
 public static PhdFeePaymentStatusTransactionImpl getInstance(){
	 if(transactionImpl == null){
		 transactionImpl = new PhdFeePaymentStatusTransactionImpl();
		 return transactionImpl;
	 }
	 return transactionImpl;
 }
	@Override
	public List<Object[]> getFeePaymentStatus(PhdFeePaymentStatusForm feePaymentStatusForm) throws Exception {
		log.info("call of getDocumentSubmissionScheduleList method in PhdDocumentSubmissionScheduleTransactionImpl class.");
		Session session = null;
		ArrayList<Integer> courseId = new ArrayList<Integer>();
		if(feePaymentStatusForm.getSelectedcourseId()!=null){
		String[] co=feePaymentStatusForm.getSelectedcourseId();
		for (int i = 0; i <co.length; i++) {
			if(!co[i].isEmpty()){
				courseId.add(Integer.parseInt(co[i]));
				}
		     }
		}
		List<Object[]> feePaymentMails =null;
		try{
			session = HibernateUtil.getSession(); 
			String str= " select course.name,student.register_no,personal_data.first_name,personal_data.last_name,personal_data.email," +
					   " fee_payment.bill_no,DATE_FORMAT(fee_payment.challen_printed_date,'%d/%m/%Y') as challen_printed_date," +
					   " if(sum(fee_payment_detail.is_installment_payment)>0,sum(fee_payment_detail.amount_paid),fee_payment.total_fee_paid) as total_fee_paid,DATE_FORMAT(fee_payment.fee_paid_date,'%d/%m/%Y') as fee_paid_date,fee_payment.currency_id," +
					   " fee_payment.total_concession_amount,fee_payment.total_amount,fee_payment.is_fee_paid" +
			           " from student "+
                       " inner join adm_appln ON student.adm_appln_id = adm_appln.id "+
                       " and student.is_admitted=1"+
                       " inner join course ON adm_appln.selected_course_id = course.id"+
                       " inner join personal_data ON adm_appln.personal_data_id = personal_data.id"+
                       " inner join program ON course.program_id = program.id"+
                       " inner join program_type ON program.program_type_id = program_type.id"+
                       " left join fee_payment on fee_payment.student_id = student.id"+
                       " and fee_payment.is_challan_canceled=0"+
                       " and fee_payment.academic_year="+feePaymentStatusForm.getYear()+
                       " left join currency on fee_payment.currency_id = currency.id "+
                       " left join fee_payment_detail on fee_payment_detail.fee_payment_id = fee_payment.id" +
                       " left join fee_payment_detail_feegroup on fee_payment_detail_feegroup.fee_payment_detail_id = fee_payment_detail.id" +
                       " and ((fee_payment_detail_feegroup.is_optional is null) or (fee_payment_detail_feegroup.is_optional=0))"+
                       " where adm_appln.applied_year="+feePaymentStatusForm.getBatch()+
                       " and adm_appln.is_cancelled=0" +
                       " and student.is_hide=0"+
                       " and student.id not in (select student_id from EXAM_student_detention_rejoin_details "+
                       " where ((detain=1) or(discontinued=1)) and ((rejoin=0) or (rejoin is null)))";
			
			if(feePaymentStatusForm.getProgramTypeId()!=null && !feePaymentStatusForm.getProgramTypeId().isEmpty()){
				str=str+" and  program_type.id="+feePaymentStatusForm.getProgramTypeId();
			}
			if(courseId==null || courseId.isEmpty()){
				str=str+" group by student.id,fee_payment.bill_no,fee_payment.fee_paid_date" +
						" order by course.id,fee_payment.fee_paid_date,student.register_no";
			}     
			if(courseId!=null && !courseId.isEmpty()){
				str=str+" and course.id in (:courses)"+
				       " group by student.id,fee_payment.bill_no,fee_payment.fee_paid_date" +
				       " order by course.id,fee_payment.fee_paid_date,student.register_no";
			}
			Query query = session.createSQLQuery(str);
			if(courseId!=null && !courseId.isEmpty()){
			query.setParameterList("courses", courseId);
			}
			feePaymentMails= query.list();
			session.flush();
			log.info("end of getDocumentSubmissionScheduleList method in PhdDocumentSubmissionScheduleTransactionImpl class.");
			return feePaymentMails;
		}catch (Exception exception) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(exception);
		 }
		
		
}
	@Override
	public List<Object[]> getCoursesByProgramTypes(String programTypeId)throws Exception {
		Session session=null;
		List<Object[]> courseList=null;
		try {
			session=HibernateUtil.getSession();
			String str=" select cou.name,cou.id,pro.name from Course cou"+
			           " join cou.program pro"+
			           " join pro.programType ptype"+
			           " where ptype.id='"+programTypeId+"'"+
			           " and cou.isActive=1"+
			           " order by pro.name";
			Query query=session.createQuery(str);
			courseList=query.list();
			return courseList;
		} catch (Exception e) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(e);
		 }
	}
	@Override
	public String getcurrency(int parseInt) {
		
		Session session=null;
		String currency=null;
		try {
			session=HibernateUtil.getSession();
			String str=" select cur.currencyCode from Currency cur"+
			           " where cur.id="+parseInt;
			Query query=session.createQuery(str);
			currency=(String) query.uniqueResult();
			return currency;
		} catch (Exception e) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
		 }
		return currency ;
	}
	
	}
