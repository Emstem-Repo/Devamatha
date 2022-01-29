package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
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
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.StudentTranscriptPrintForm;
import com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import common.Logger;

/**
 * @author dIlIp
 *
 */
public class StudentTranscriptPrintTransactionImpl implements IStudentTranscriptPrintTransaction {
	
	private static final Logger log = Logger.getLogger(StudentTranscriptPrintTransactionImpl.class);

	public static volatile StudentTranscriptPrintTransactionImpl studentMarksCardsPrintTransactionImpl;

	/**
	 * singleton
	 */
	public static StudentTranscriptPrintTransactionImpl getInstance() {
		if (studentMarksCardsPrintTransactionImpl == null) {
			studentMarksCardsPrintTransactionImpl = new StudentTranscriptPrintTransactionImpl();
		}
		return studentMarksCardsPrintTransactionImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction#verifyRegisterNumberAndGetDetails(com.kp.cms.forms.admission.StudentTranscriptPrintForm)
	 */
	@Override
	public Student verifyRegisterNumberAndGetDetails(StudentTranscriptPrintForm certificateForm) throws Exception {
		
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
	 * @see com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction#checkForAlreadyPrinted(com.kp.cms.forms.admission.StudentTranscriptPrintForm)
	 */
	public StudentCertificateDetails checkForAlreadyPrinted(StudentTranscriptPrintForm certificateForm) throws Exception {
		
		Session session = null;
		
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from StudentCertificateDetails m where m.studentId.id=:stuId and m.type='Transcript'");
			query.setInteger("stuId",  certificateForm.getStuId());
			StudentCertificateDetails details = (StudentCertificateDetails) query.uniqueResult();
			
			if(details!=null){
				certificateForm.setTranscriptNo(details.getCertificateNo());
				certificateForm.setTranscriptDate(CommonUtil.getStringDateWithOrdianlFullMonthNameCommaYear(details.getPrintedDate()));
				
			}else{
				Query query1 = session.createQuery("from StudentCertificateNumber m where m.isActive=1 and m.type='Transcript'");
				StudentCertificateNumber certificateNumber = (StudentCertificateNumber)query1.uniqueResult();
				
				if(certificateNumber != null){
					int currentNo = certificateNumber.getCurrentNo();
					String no = addLeadingZeros(String.valueOf(currentNo), 5);
					
					certificateForm.setCurrentNumber(currentNo);
					certificateForm.setTranscriptNo(certificateNumber.getPrefix()+"/"+no);
					certificateForm.setTranscriptDate(CommonUtil.getStringDateWithOrdianlFullMonthNameCommaYear(new Date()));
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
	 * @see com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction#saveStudentMarksCardsPrint(com.kp.cms.bo.admission.StudentCertificateDetails)
	 */
	public boolean saveStudentMarksCardsPrint(StudentCertificateDetails details) throws Exception {
		
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
	 * @see com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction#saveStudentCertificateNumberCurrentNumber(com.kp.cms.forms.admission.StudentTranscriptPrintForm)
	 */
	public boolean saveStudentCertificateNumberCurrentNumber(StudentTranscriptPrintForm certificateForm) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from StudentCertificateNumber m where m.isActive=1 and m.type='Transcript'");
				
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
				log.error("Exception occured in reactivating of saveStudentCertificateNumberCurrentNumber in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of saveStudentCertificateNumberCurrentNumber TransactionImpl");
				}
		}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction#getClassId(int, int)
	 */
	public int getClassId(int studentId, int schemeNo) throws Exception {
		Session session = null;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamStudentPreviousClassDetailsBO e where e.studentId=" + studentId +" and e.schemeNo="+schemeNo);

			ExamStudentPreviousClassDetailsBO previous = (ExamStudentPreviousClassDetailsBO) query.uniqueResult();
			
			if(previous!=null && previous.getClassId() > 0){
				return previous.getClassId();
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			log.error("Error in getClassId..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction#getStudentMarks(java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	public List getStudentMarks(String consolidateQuery, int studentId) throws Exception {
		Session session = null;
		List hallTicketList = null;
		try {
			session = HibernateUtil.getSession();
			 hallTicketList=session.createSQLQuery(consolidateQuery).setParameter("id", studentId).list();
			return hallTicketList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction#getSupplimentaryAppeared(int)
	 */
	@SuppressWarnings("unchecked")
	public List<String> getSupplimentaryAppeared(int studentId) throws Exception {
		Session session = null;
		List<String> list = new ArrayList<String>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from StudentSupplementaryImprovementApplication s where (s.isAppearedTheory=1 or s.isAppearedPractical=1) and s.student.id in (:id)").setParameter("id",studentId);
			List<StudentSupplementaryImprovementApplication> selectedCandidatesList = selectedCandidatesQuery.list();
			if(selectedCandidatesList!=null && !selectedCandidatesList.isEmpty()){
				Iterator<StudentSupplementaryImprovementApplication> itr=selectedCandidatesList.iterator();
				while (itr.hasNext()) {
					StudentSupplementaryImprovementApplication bo = itr.next();
					if(bo.getIsAppearedTheory()!=null && bo.getIsAppearedTheory()){
						if(!list.contains(bo.getStudent().getId()+"_"+bo.getSchemeNo()+"_"+bo.getSubject().getId()+"_T")){
							list.add(bo.getStudent().getId()+"_"+bo.getSchemeNo()+"_"+bo.getSubject().getId()+"_T");
						}
					}
					if(bo.getIsAppearedPractical()!=null && bo.getIsAppearedPractical()){
						if(!list.contains(bo.getStudent().getId()+"_"+bo.getSchemeNo()+"_"+bo.getSubject().getId()+"_P")){
							list.add(bo.getStudent().getId()+"_"+bo.getSchemeNo()+"_"+bo.getSubject().getId()+"_P");
						}
					}
				}
			}
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction#getStudentAcademicDetails(com.kp.cms.forms.admission.StudentTranscriptPrintForm)
	 */
	@SuppressWarnings("unchecked")
	public void getStudentAcademicDetails(StudentTranscriptPrintForm certificateForm) throws Exception {
		
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
				certificateForm.setStudentAcademicYearFrom(obj[0].toString().substring(0, 4));
			}
			
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
						certificateForm.setStudentAcademicYearTo(yearMonth.substring(0, 4));
					}
				}
								
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
	 * @see com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction#verifyStudentDetentionDiscontinued(int)
	 */
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
