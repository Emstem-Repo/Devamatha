package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.StudentAttendanceForExamForm;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.IStudentAttendanceForExamTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentAttendanceForExamTransactionImpl implements
		IStudentAttendanceForExamTransaction {
	/**
	 * Singleton object of StudentAttendanceForExamTransactionImpl
	 */
	private static volatile StudentAttendanceForExamTransactionImpl studentAttendanceForExamTransactionImpl = null;
	private static final Log log = LogFactory.getLog(StudentAttendanceForExamTransactionImpl.class);
	private StudentAttendanceForExamTransactionImpl() {
		
	}
	/**
	 * return singleton object of StudentAttendanceForExamTransactionImpl.
	 * @return
	 */
	public static StudentAttendanceForExamTransactionImpl getInstance() {
		if (studentAttendanceForExamTransactionImpl == null) {
			studentAttendanceForExamTransactionImpl = new StudentAttendanceForExamTransactionImpl();
		}
		return studentAttendanceForExamTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IStudentAttendanceForExamTransaction#getDataForQuery(java.lang.String, java.util.List)
	 */
	public List getDataForQuery(String query,List<Integer> idList) throws Exception {
		Session session = null;
		String intType ="";
		Iterator<Integer> itr=idList.iterator();
		while (itr.hasNext()) {
			Integer regNo = itr.next();
			if(intType.isEmpty())
				intType="'"+regNo+"'";
			else
				intType=intType+",'"+regNo+"'";
		}
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query+" and s.id in ("+intType+")");
			list = selectedCandidatesQuery.list();
			return list;
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
	 * @see com.kp.cms.transactions.exam.IStudentAttendanceForExamTransaction#getDataForMarksQuery(java.lang.String, java.util.List)
	 */
	@Override
	public List<String> getDataForMarksQuery(String query, List<Integer> idList) throws Exception {
		Session session = null;
		String intType ="";
		Iterator<Integer> itr=idList.iterator();
		while (itr.hasNext()) {
			Integer regNo = itr.next();
			if(intType.isEmpty())
				intType="'"+regNo+"'";
			else
				intType=intType+",'"+regNo+"'";
		}
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query+" and m.marksEntry.student.id in ("+intType+") group by m.marksEntry.student.registerNo");
			list = selectedCandidatesQuery.list();
			return list;
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
	 * @see com.kp.cms.transactions.exam.IStudentAttendanceForExamTransaction#saveMarks(java.util.List, com.kp.cms.forms.exam.StudentAttendanceForExamForm)
	 */
	@Override
	public boolean saveMarks(List<StudentMarksTO> stuList,StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<StudentMarksTO> itr=stuList.iterator();
			while (itr.hasNext()) {
				StudentMarksTO to = (StudentMarksTO) itr.next();
				
				if((studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("t") && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()) || (studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("p") && to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty())){
					MarksEntry marksEntry=null;
					String query="from MarksEntry m where m.exam.id="+studentAttendanceForExamForm.getExamId()+" and m.student.id="+to.getStudentId()+" and m.classes.id="+to.getClassId();
					if(to.getEvaId()!=null && !to.getEvaId().isEmpty())
						query=query+" and m.evaluatorType="+to.getEvaId();
					else
						query=query+" and m.evaluatorType is null";
					if(to.getAnsId()!=null && !to.getAnsId().isEmpty())
						query=query+" and m.answerScript="+to.getAnsId();
					else
						query=query+" and m.answerScript is null";
					List<MarksEntry> marksEntrys=session.createQuery(query).list();
					if(marksEntrys==null || marksEntrys.isEmpty()){
						marksEntry=new MarksEntry();
						Student student=new Student();
						student.setId(to.getStudentId());
						marksEntry.setStudent(student);
						ExamDefinitionBO exam=new ExamDefinitionBO();
						exam.setId(Integer.parseInt(studentAttendanceForExamForm.getExamId()));
						marksEntry.setExam(exam);
						if(to.getEvaId()!=null && !to.getEvaId().isEmpty()){
							marksEntry.setEvaluatorType(Integer.parseInt(to.getEvaId()));
						}
						if(to.getAnsId()!=null && !to.getAnsId().isEmpty()){
							marksEntry.setAnswerScript(Integer.parseInt(to.getAnsId()));
						}
						Classes classes=new Classes();
						classes.setId(to.getClassId());
						marksEntry.setClasses(classes);
						marksEntry.setCreatedBy(studentAttendanceForExamForm.getUserId());
						marksEntry.setCreatedDate(new Date());
						marksEntry.setModifiedBy(studentAttendanceForExamForm.getUserId());
						marksEntry.setLastModifiedDate(new Date());
						Set<MarksEntryDetails> marksEntryDetails=new HashSet<MarksEntryDetails>();
						MarksEntryDetails detail=new MarksEntryDetails();
						Subject subject=new Subject();
						subject.setId(Integer.parseInt(studentAttendanceForExamForm.getSubjectId()));
						detail.setSubject(subject);
						detail.setTheoryMarks(to.getTheoryMarks());
						detail.setPracticalMarks(to.getPracticalMarks());
						detail.setCreatedBy(studentAttendanceForExamForm.getUserId());
						detail.setCreatedDate(new Date());
						detail.setModifiedBy(studentAttendanceForExamForm.getUserId());
						detail.setLastModifiedDate(new Date());
						if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("t")){
							detail.setTheoryMarks(to.getTheoryMarks());
							detail.setIsTheorySecured(false);
						}
						if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("p")){
							detail.setPracticalMarks(to.getPracticalMarks());
							detail.setIsPracticalSecured(false);
						}
						
						marksEntryDetails.add(detail);
						marksEntry.setMarksDetails(marksEntryDetails);
						session.save(marksEntry);
					}else{
						Iterator<MarksEntry> marksitr=marksEntrys.iterator();
						if (marksitr.hasNext()) {
							 marksEntry = (MarksEntry) marksitr.next();
						}
						MarksEntryDetails detail=(MarksEntryDetails)session.createQuery("from MarksEntryDetails m where m.marksEntry.id="+marksEntry.getId()+"" +
								" and m.subject.id="+studentAttendanceForExamForm.getSubjectId()).uniqueResult();
						if(detail==null){
							detail=new MarksEntryDetails();
							Subject subject=new Subject();
							subject.setId(Integer.parseInt(studentAttendanceForExamForm.getSubjectId()));
							detail.setSubject(subject);
							detail.setCreatedBy(studentAttendanceForExamForm.getUserId());
							detail.setCreatedDate(new Date());
						}
						detail.setMarksEntry(marksEntry);
						
						if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("t")){
							detail.setTheoryMarks(to.getTheoryMarks());
							detail.setIsTheorySecured(false);
						}
						if(studentAttendanceForExamForm.getSubjectType().equalsIgnoreCase("p")){
							detail.setPracticalMarks(to.getPracticalMarks());
							detail.setIsPracticalSecured(false);
						}
						detail.setModifiedBy(studentAttendanceForExamForm.getUserId());
						detail.setLastModifiedDate(new Date());
						marksEntry.setModifiedBy(studentAttendanceForExamForm.getUserId());
						marksEntry.setLastModifiedDate(new Date());
						session.saveOrUpdate(detail);
						session.update(marksEntry);
					}
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	@Override
	public List getDataForQuery1(String query,
			List<String> registerNoList) throws Exception {
		Session session = null;
		StringBuilder intType = new StringBuilder();
		Iterator<String> itr=registerNoList.iterator();
		while (itr.hasNext()) {
			String regNo = itr.next();
			if(intType.toString().isEmpty())
			    intType.append("'").append(regNo).append("'");
			else
			    intType.append(",'").append(regNo).append("'");
		}
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query+" and s.registerNo in ("+intType+")");
			list = selectedCandidatesQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
		
	
	public List<Object[]> getStuExamDetails(String stuExamDetailsQuery) throws Exception {
		Session session = null;
		List<Object[]> stuExamDetails = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			stuExamDetails=session.createSQLQuery(stuExamDetailsQuery).list();
			return stuExamDetails;
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
}
