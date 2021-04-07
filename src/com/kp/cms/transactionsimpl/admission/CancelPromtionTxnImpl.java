package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.CancelPromotionForm;
import com.kp.cms.to.admission.CancelPromotionTo;
import com.kp.cms.transactions.admission.ICancelPromtionTranscation;
import com.kp.cms.transactionsimpl.admin.DepartmentEntryTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;

public class CancelPromtionTxnImpl implements ICancelPromtionTranscation {
	private static final Log log = LogFactory.getLog(CancelPromtionTxnImpl.class);
	private static volatile CancelPromtionTxnImpl cancelPromtionTxnImpl = null;
	public static CancelPromtionTxnImpl getInstance(){
		if(cancelPromtionTxnImpl == null){
			cancelPromtionTxnImpl = new CancelPromtionTxnImpl();
			return cancelPromtionTxnImpl;
		}
		return cancelPromtionTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICancelPromtionTranscation#getSearchCancelPromotionDetails(com.kp.cms.forms.admission.CancelPromotionForm)
	 */
	@Override
	public Student getSearchCancelPromotionDetails( CancelPromotionForm cancelPromotionForm)throws Exception {
		Session session =null;
	//	Transaction transaction = null;
		Student student= null;
		try{
			session=HibernateUtil.getSession();
			String str= "from Student stu where stu.isActive =  1 and stu.registerNo='" +cancelPromotionForm.getRegisterNo()+"'";
			Query query=session.createQuery(str);
			student = (Student) query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			log.error("Error during getting getSearchCancelPromotionDetails..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return student;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICancelPromtionTranscation#getPreviousClassDetails(int, int)
	 */
	@Override
	public ExamStudentPreviousClassDetailsBO getPreviousClassDetails(int stuId,
			int semNo) throws Exception {
		Session session= null;
		ExamStudentPreviousClassDetailsBO preClassDetails;
		try{
			session = HibernateUtil.getSession();
			String str= "from ExamStudentPreviousClassDetailsBO espc where espc.studentId="+stuId +"and espc.schemeNo= " +semNo;
			Query query = session.createQuery(str);
			preClassDetails = (ExamStudentPreviousClassDetailsBO) query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			log.error("Error during getting getPreviousClassDetails..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return preClassDetails;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICancelPromtionTranscation#getClassSchemeWiseDetails(com.kp.cms.to.admission.CancelPromotionTo)
	 */
	@Override
	public ClassSchemewise getClassSchemeWiseDetails( CancelPromotionTo promotionTo) throws Exception {
		Session session= null;
		ClassSchemewise schemewise;
		try{
			session= HibernateUtil.getSession();
			String str= "from ClassSchemewise classScheme where classScheme.classes.id="+promotionTo.getClassId();
			Query query =session.createQuery(str);
			schemewise = (ClassSchemewise) query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			log.error("Error during getting getClassSchemeWiseDetails..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return schemewise;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICancelPromtionTranscation#saveClassSchemeWise(com.kp.cms.to.admission.CancelPromotionTo, int)
	 */
	public boolean saveClassSchemeWise(CancelPromotionTo promotionTo,int stuId)
			throws Exception {
		Session session = null;
		Transaction tx= null;
		Transaction tx1 = null;
		boolean isupdated = false;
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			Student student = (Student)session.get(Student.class, stuId);
			ClassSchemewise classSchemewise = new ClassSchemewise();
			classSchemewise.setId(promotionTo.getClassSchemwiseId());
			student.setClassSchemewise(classSchemewise);
			student.setLastModifiedDate(new Date());
			session.update(student);
			tx.commit();
			session.flush();
			session.close();
			isupdated= true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving saveClassSchemeWise data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving saveClassSchemeWise data..." ,e);
			throw new ApplicationException(e);
		}
		return isupdated;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICancelPromtionTranscation#getSubjectGroupHistory(int, int)
	 */
	@Override
	public List<ExamStudentSubGrpHistoryBO> getSubjectGroupHistory(int stuId, int semNo) throws Exception {
		Session session = null;
		List<ExamStudentSubGrpHistoryBO> subGrpHistoryBO = null;
		try{
			session=HibernateUtil.getSession();
			String str="from ExamStudentSubGrpHistoryBO subGrp where subGrp.studentId="+stuId +"and subGrp.schemeNo="+semNo;
			Query query = session.createQuery(str);
			subGrpHistoryBO=query.list();
			session.flush();
		}catch (Exception e) {
			log.error("Error during getting getSubjectGroupHistory..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return subGrpHistoryBO;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICancelPromtionTranscation#deleteApplicantSubjectGroup(int)
	 */
	public void deleteApplicantSubjectGroup(int admappln) throws Exception {
		Session session=null;
		Transaction tx=null;
		
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			String str="from ApplicantSubjectGroup appSubGrp where appSubGrp.admAppln.id="+admappln;
			Query query = session.createQuery(str);
			List<ApplicantSubjectGroup> list = query.list();
			if(list!=null){
				Iterator<ApplicantSubjectGroup> iterator= list.iterator();
				while (iterator.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) iterator .next();
					session.delete(applicantSubjectGroup);
				}
			}
			tx.commit();
			session.flush();
			session.close();
			
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving deleteApplicantSubjectGroup data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving deleteApplicantSubjectGroup data..." ,e);
			throw new ApplicationException(e);
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICancelPromtionTranscation#updateAppSubGrp(java.util.List, int)
	 */
	@Override
	public boolean saveAppSubGrp(List<CancelPromotionTo> promotionToList,int admappln) throws Exception {
		Session session = null;
		Transaction tx=null;
		boolean isUpdated=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(promotionToList!=null){
				Iterator<CancelPromotionTo> iterator=promotionToList.iterator();
				while (iterator.hasNext()) {
					CancelPromotionTo cancelPromotionTo = (CancelPromotionTo) iterator .next();
					ApplicantSubjectGroup subjectGroup = new ApplicantSubjectGroup();
					AdmAppln appln=new AdmAppln();
					SubjectGroup group=new SubjectGroup();
					appln.setId(admappln);
					subjectGroup.setAdmAppln(appln);
					group.setId(cancelPromotionTo.getSubGrpId());
					subjectGroup.setSubjectGroup(group);
					subjectGroup.setCreatedDate(new Date());
					subjectGroup.setLastModifiedDate(new Date());
					session.save(subjectGroup);
				}
				tx.commit();
				session.flush();
				session.close();
				isUpdated=true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving updateAppSubGrp data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving updateAppSubGrp data..." ,e);
			throw new ApplicationException(e);
		}
		return isUpdated;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICancelPromtionTranscation#deletePreviousSubGrp(java.util.List)
	 */
	@SuppressWarnings("null")
	@Override
	public void deletePreviousSubGrp(List<CancelPromotionTo> promotionToList) throws Exception {
		Session session=null;
		Transaction tx=null;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(promotionToList!=null){
				Iterator<CancelPromotionTo> iterator = promotionToList.iterator();
				while (iterator.hasNext()) {
					CancelPromotionTo cancelPromotionTo = (CancelPromotionTo) iterator.next();
					String str1="from ExamStudentSubGrpHistoryBO previousSubGrp where previousSubGrp.id=" +cancelPromotionTo.getExamSubGrpId();
					Query query2 =session.createQuery(str1);
					ExamStudentSubGrpHistoryBO grpHistoryBO= (ExamStudentSubGrpHistoryBO) query2.uniqueResult();
					session.delete(grpHistoryBO);
				}
			}
			tx.commit();
			session.flush();
			session.close();
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving deletePreviousSubGrp data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving deletePreviousSubGrp data..." ,e);
			throw new ApplicationException(e);
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICancelPromtionTranscation#deletePreviousClasses(com.kp.cms.to.admission.CancelPromotionTo)
	 */
	@Override
	public void deletePreviousClasses(CancelPromotionTo canProTo) throws Exception {
		Session session= null;
		Transaction tx=null;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			String str="from ExamStudentPreviousClassDetailsBO previousClass where previousClass.id="+canProTo.getExamStuClassId();
			Query query =session.createQuery(str);
			ExamStudentPreviousClassDetailsBO classDetailsBO=(ExamStudentPreviousClassDetailsBO) query.uniqueResult();
			session.delete(classDetailsBO);
			tx.commit();
			session.flush();
			session.close();
	}catch (ConstraintViolationException e) {
		tx.rollback();
		log.error("Error during saving deletePreviousClasses data..." ,e);
		throw new BusinessException(e);
	} catch (Exception e) {
		tx.rollback();
		log.error("Error during saving deletePreviousClasses data..." ,e);
		throw new ApplicationException(e);
	}
	}
}
