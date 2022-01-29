package com.kp.cms.transactionsimpl.admission;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCertificateNumber;
import com.kp.cms.bo.admission.StudentCertificateDetails;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.MigrationCertificateForm;
import com.kp.cms.transactions.admission.IMigrationCertificateTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

import common.Logger;

/**
 * @author dIlIp
 *
 */
public class MigrationCertificateTransactionImpl implements IMigrationCertificateTransaction {
	
	private static final Logger log = Logger.getLogger(MigrationCertificateTransactionImpl.class);

	public static volatile MigrationCertificateTransactionImpl migrationCertificateTransactionImpl;

	public static MigrationCertificateTransactionImpl getInstance() {
		if (migrationCertificateTransactionImpl == null) {
			migrationCertificateTransactionImpl = new MigrationCertificateTransactionImpl();
		}
		return migrationCertificateTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IMigrationCertificateTransaction#verifyRegisterNumberAndGetDetails(com.kp.cms.forms.admission.MigrationCertificateForm)
	 */
	public Student verifyRegisterNumberAndGetDetails(MigrationCertificateForm certificateForm) throws Exception {
		
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from Student s where s.isAdmitted=1 and s.admAppln.isCancelled=0" +
					" and (s.isHide=0 or s.isHide is null) and s.registerNo=:registrationNo");
			query.setString("registrationNo",  certificateForm.getRegNo());
			Student student = (Student) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return student;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IMigrationCertificateTransaction#checkForAlreadyPrinted(com.kp.cms.forms.admission.MigrationCertificateForm)
	 */
	public StudentCertificateDetails checkForAlreadyPrinted(MigrationCertificateForm certificateForm) throws Exception {
		
		Session session = null;
		
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from StudentCertificateDetails m where m.studentId.id=:stuId and m.type='Migration'");
			query.setInteger("stuId",  certificateForm.getStuId());
			StudentCertificateDetails details = (StudentCertificateDetails) query.uniqueResult();
			
			if(details!=null){
				certificateForm.setMigrationCertificateNo(details.getCertificateNo());
				String dDate = CommonUtil.ConvertStringToSQLDate2(details.getPrintedDate().toString());
				certificateForm.setMigrationDate(dDate.substring(0, 10));
				
			}else{
				Query query1 = session.createQuery("from StudentCertificateNumber m where m.isActive=1 and m.type='Migration'");
				StudentCertificateNumber migrationNo = (StudentCertificateNumber)query1.uniqueResult();
				
				if(migrationNo!=null){
					
					int currentNo = migrationNo.getCurrentNo();
					String no = addLeadingZeros(String.valueOf(currentNo), 5);
					
					certificateForm.setCurrentNumber(currentNo);
					certificateForm.setMigrationCertificateNo(migrationNo.getPrefix()+"/"+no);
					certificateForm.setMigrationDate(CommonUtil.getTodayDate());
				}else{
					throw new NoSuchFieldError();
				}
				
			}
			
			session.flush();
			//session.close();
			//sessionFactory.close();
			return details;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	
	/**
	 * @param currentNo
	 * @param length
	 * @return
	 */
	private String addLeadingZeros(String currentNo, int length) {
		
		return currentNo.length() < length ? addLeadingZeros("0" + currentNo, length) : currentNo;
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IMigrationCertificateTransaction#saveMigrationCertificate(com.kp.cms.bo.admission.MigrationCertificateDetails)
	 */
	public boolean saveMigrationCertificate(StudentCertificateDetails details) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(details);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IMigrationCertificateTransaction#getStudentAcademicDetails(com.kp.cms.forms.admission.MigrationCertificateForm)
	 */
	public void getStudentAcademicDetails(MigrationCertificateForm certificateForm) throws Exception {
		
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			Query query = session.createSQLQuery("select min(curriculum_scheme_duration.start_date), max(curriculum_scheme_duration.end_date) from EXAM_student_previous_class_details " +
					"inner join classes ON EXAM_student_previous_class_details.class_id = classes.id " +
					"inner join class_schemewise on class_schemewise.class_id = classes.id " +
					"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
					"where student_id=:stuId");
			query.setInteger("stuId",  certificateForm.getStuId());
			
			Object[] obj = (Object[]) query.uniqueResult();
			
			if(obj[0]!=null){
				certificateForm.setStudentAcademicYearFrom(CommonUtil.getMonthFullName(Integer.parseInt(obj[0].toString().substring(5, 7))-1)+" "+obj[0].toString().substring(0, 4));
			}
			
			/*if(obj[1]!=null){
				
				certificateForm.setStudentAcademicYearTo(CommonUtil.getMonthFullName(Integer.parseInt(obj[1].toString().substring(5, 7))-1)+" "+obj[1].toString().substring(0, 4));
			}
			Query query1 = session.createSQLQuery("select curriculum_scheme_duration.end_date, curriculum_scheme_duration.start_date from student " +
					"inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id " +
					"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
					"where student.id=:stuId");
			query1.setInteger("stuId",  certificateForm.getStuId());
			
			Object[] obj1 = (Object[]) query1.uniqueResult();
			
			if(obj[0]==null){
				certificateForm.setStudentAcademicYearFrom(CommonUtil.getMonthFullName(Integer.parseInt(obj1[1].toString().substring(5, 7))-1)+" "+obj1[1].toString().substring(0, 4));
			}
			
			if(obj1[0]!=null){
				certificateForm.setStudentAcademicYearTo(CommonUtil.getMonthFullName(Integer.parseInt(obj1[0].toString().substring(5, 7))-1)+" "+obj1[0].toString().substring(0, 4));
			}*/
			
			if(certificateForm.getStudentAcademicYearTo()==null){
				
				Query query1 = session.createSQLQuery("select concat(ed.year, ed.month) as yearmonth from EXAM_student_final_mark_details fm " +
						"inner join EXAM_definition ed ON fm.exam_id = ed.id " +
						"where fm.student_id =:stuId " +
						"order by yearmonth desc limit 1");
				query1.setInteger("stuId",  certificateForm.getStuId());
				
				List<String> list = query1.list();
				if(list!=null){
					Iterator<String> itr = list.iterator();
					String yearMonth;
					while(itr.hasNext()){
						yearMonth = (String) itr.next();
						certificateForm.setStudentAcademicYearTo(CommonUtil.getMonthFullName(Integer.parseInt(yearMonth.substring(4, 6)))+" "+yearMonth.substring(0, 4));
					}
				}
				
				/*Object[] obj1 = (Object[]) query1.uniqueResult();
				if(obj1[0]!=null && obj1[1]!=null){
					certificateForm.setStudentAcademicYearTo(CommonUtil.getMonthFullName(Integer.parseInt(obj1[0].toString().substring(0,3))-1)+" "+obj1[1].toString().substring(3, 5));
				}*/
				
				
				
			}
			
			
			
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IMigrationCertificateTransaction#saveMigrationCertificateCurrentNumber(com.kp.cms.forms.admission.MigrationCertificateForm)
	 */
	public boolean saveMigrationCertificateCurrentNumber(MigrationCertificateForm certificateForm) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from StudentCertificateNumber m where m.isActive=1 and m.type='Migration'");
				
				StudentCertificateNumber migNo = (StudentCertificateNumber) query.uniqueResult();
				transaction = session.beginTransaction();
				migNo.setCurrentNo(certificateForm.getCurrentNumber()+1);
				migNo.setModifiedBy(certificateForm.getUserId());
				migNo.setLastModifiedDate(new Date());
				session.update(migNo);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reactivating of externalEvaluator in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of reActivateExternalEvaluator of ExternalEvaluator TransactionImpl");
				}			
		}

	public ExamStudentDetentionRejoinDetails verifyStudentDetentionDiscontinued(int studId) throws Exception {
		
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from ExamStudentDetentionRejoinDetails bo where " +
					"((bo.detain=1 or bo.detain=true) or (bo.discontinued=1 or bo.discontinued=true)) " +
					"and (bo.rejoin=0 or bo.rejoin=false or bo.rejoin=null) " +
					"and bo.student.id=:stuId");
			query.setInteger("stuId",  studId);
			ExamStudentDetentionRejoinDetails rejoinDetailsBo = (ExamStudentDetentionRejoinDetails) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return rejoinDetailsBo;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	


}
