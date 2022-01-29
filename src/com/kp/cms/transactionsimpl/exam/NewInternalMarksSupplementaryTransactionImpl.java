package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.InternalMarkSupplementaryDetails;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.NewInternalMarksSupplementaryForm;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;
import com.kp.cms.transactions.exam.INewInternalMarksSupplementaryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class NewInternalMarksSupplementaryTransactionImpl implements
		INewInternalMarksSupplementaryTransaction {
	/**
	 * Singleton object of NewInternalMarksSupplementaryTransactionImpl
	 */
	private static volatile NewInternalMarksSupplementaryTransactionImpl newInternalMarksSupplementaryTransactionImpl = null;
	private static final Log log = LogFactory.getLog(NewInternalMarksSupplementaryTransactionImpl.class);
	private NewInternalMarksSupplementaryTransactionImpl() {
		
	}
	/**
	 * return singleton object of NewInternalMarksSupplementaryTransactionImpl.
	 * @return
	 */
	public static NewInternalMarksSupplementaryTransactionImpl getInstance() {
		if (newInternalMarksSupplementaryTransactionImpl == null) {
			newInternalMarksSupplementaryTransactionImpl = new NewInternalMarksSupplementaryTransactionImpl();
		}
		return newInternalMarksSupplementaryTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewInternalMarksSupplementaryTransaction#checkValidStudentRegNo(com.kp.cms.forms.exam.NewInternalMarksSupplementaryForm)
	 */
	@Override
	public boolean checkValidStudentRegNo(NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm) throws Exception {
		Session session = null;
		boolean isValid=false;
		try {
			session = HibernateUtil.getSession();
			String query="from Student s where s.admAppln.isCancelled=0" +
					" and s.admAppln.courseBySelectedCourseId.id="+newInternalMarksSupplementaryForm.getCourseId();
			if(newInternalMarksSupplementaryForm.getRegisterNo()!=null && !newInternalMarksSupplementaryForm.getRegisterNo().isEmpty()){
				query=query+" and s.registerNo='"+newInternalMarksSupplementaryForm.getRegisterNo()+"'";
			}
			if(newInternalMarksSupplementaryForm.getRollNo()!=null && !newInternalMarksSupplementaryForm.getRollNo().isEmpty()){
				query=query+" and s.rollNo="+newInternalMarksSupplementaryForm.getRollNo()+"'";
			}
			List list=session.createQuery(query).list();
			if(list!=null && !list.isEmpty())
				isValid=true;
			
			return isValid;
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
	 * @see com.kp.cms.transactions.exam.INewInternalMarksSupplementaryTransaction#getDataForQuery(java.lang.String)
	 */
	@Override
	public List getDataForQuery(String intQuery) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(intQuery);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
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
	@Override
	public boolean saveInternalMarkSupplementaryDetails(List<InternalMarkSupplementaryDetails> bos, String userId) throws Exception {
		log.debug("inside saveInternalMarkSupplementaryDetails");
		Session session = null;
		Transaction transaction = null;
		InternalMarkSupplementaryDetails bo;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<InternalMarkSupplementaryDetails> tcIterator = bos.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();
				if(bo.getId()>0){
					InternalMarkSupplementaryDetails oldBo=(InternalMarkSupplementaryDetails)session.get(InternalMarkSupplementaryDetails.class, bo.getId());
					oldBo.setExam(bo.getExam());
					oldBo.setTheoryTotalSubInternalMarks(bo.getTheoryTotalSubInternalMarks());
					oldBo.setPracticalTotalSubInternalMarks(bo.getPracticalTotalSubInternalMarks());
					oldBo.setLastModifiedDate(bo.getLastModifiedDate());
					session.update(oldBo);
				}else{
					session.save(bo);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
//			session.close();
			log.debug("leaving saveInternalMarkSupplementaryDetails");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in saveInternalMarkSupplementaryDetails impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewInternalMarksSupplementaryTransaction#getMaxMarkOfSubject(com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO)
	 */
	@Override
	public List<Object[]> getMaxMarkOfSubject(ExamInternalMarksSupplementaryTO to,NewInternalMarksSupplementaryForm newInternalMarksSupplementaryForm)
			throws Exception {
		Session session = null;
		List<Object[]> list = null;
		try{
			session = HibernateUtil.getSession();
			int year=0;
			if(newInternalMarksSupplementaryForm.getStudentId() != 0){
				List<StudentPreviousClassHistory> history = session.createQuery("select s from StudentPreviousClassHistory s where s.student.id="+newInternalMarksSupplementaryForm.getStudentId()+" and s.schemeNo="+newInternalMarksSupplementaryForm.getSchemeNo()).list();
				if(history != null &&  !history.isEmpty()){
					if(history.get(0) != null){
						year = history.get(0).getAcademicYear();
					}
				}else{
					Student student = (Student)session.createQuery("from Student s where id="+newInternalMarksSupplementaryForm.getStudentId()+" and s.classSchemewise.classes.termNumber="+newInternalMarksSupplementaryForm.getSchemeNo()).uniqueResult(); 
					if(student != null){
						year = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
					}
				}
			}
			String query="select internal.enteredMaxMark," +
					" s.finalTheoryInternalMaximumMark, s.finalPracticalInternalMaximumMark, ansScript.value from SubjectRuleSettings s" +
					" left join s.examSubjectRuleSettingsSubInternals internal" +
					" left join s.examSubjectRuleSettingsMulEvaluators eval" +
					" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " +
					" where s.course.id="+newInternalMarksSupplementaryForm.getCourseId()+
					" and s.schemeNo=" +newInternalMarksSupplementaryForm.getSchemeNo()+
					" and s.subject.id=" +to.getSubjectId();

			query=query+" and s.academicYear="+year;
		
			query=query+" and s.isActive=1 group by s.id";
			list=session.createQuery(query).list();
		} catch (Exception e) {
			log.error("Error while retrieving ExamAbscentCode.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
		return list;
	}
}
