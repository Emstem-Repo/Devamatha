package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
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

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamAssignmentTypeMasterBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.bo.exam.SubjectRuleSettingsAssignment;
import com.kp.cms.bo.exam.SubjectRuleSettingsAttendance;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulAnsScript;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulEvaluator;
import com.kp.cms.bo.exam.SubjectRuleSettingsSubInternal;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class SubjectRuleSettingsTransactionImpl implements
		ISubjectRuleSettingsTransaction {
	/**
	 * Singleton object of SubjectRuleSettingsHelper
	 */
	private static volatile SubjectRuleSettingsTransactionImpl subjectRuleSettingsTransactionImpl = null;
	private static final Log log = LogFactory.getLog(SubjectRuleSettingsTransactionImpl.class);
	private SubjectRuleSettingsTransactionImpl() {
		
	}
	/**
	 * return singleton object of SubjectRuleSettingsTransactionImpl.
	 * @return
	 */
	public static SubjectRuleSettingsTransactionImpl getInstance() {
		if (subjectRuleSettingsTransactionImpl == null) {
			subjectRuleSettingsTransactionImpl = new SubjectRuleSettingsTransactionImpl();
		}
		return subjectRuleSettingsTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction#isDuplicateCheck(java.lang.String)
	 */
	@Override
	public List<ExamSubjectRuleSettingsBO> isDuplicateCheck(String query)
			throws Exception {
		Session session = null;
		List<ExamSubjectRuleSettingsBO> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public List<SubjectGroup> getSubjectGroupsForInput(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		Session session = null;
		List<SubjectGroup> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String schemeNo="";
			if(subjectRuleSettingsForm.getSchemeNo()!=null && !subjectRuleSettingsForm.getSchemeNo().isEmpty()){
				schemeNo=subjectRuleSettingsForm.getSchemeNo();
			}else{
				if(!subjectRuleSettingsForm.getSchemeType().isEmpty()){
					if(subjectRuleSettingsForm.getSchemeType().equalsIgnoreCase("1")){
						schemeNo="1,3,5,7,9";
					}else if(subjectRuleSettingsForm.getSchemeType().equalsIgnoreCase("2")){
						schemeNo="2,4,6,8,10";
					}else{
						schemeNo="1,2,3,4,5,6,7,8,9,10";
					}
				}
			}
			String [] tempArray = subjectRuleSettingsForm.getCourseIds();
			StringBuilder intType = new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				 intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
			String query="select cs.subjectGroup from CurriculumSchemeDuration c left join c.curriculumSchemeSubjects cs where c.curriculumScheme.course.id in ("+intType+") and c.academicYear ="+subjectRuleSettingsForm.getAcademicYear() +
					" and c.semesterYearNo in ("+schemeNo+") group by cs.subjectGroup.id";
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
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
	 * @see com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction#getAllExamSubjectRuleSettingsSubInternals()
	 */
	@Override
	public List<ExamInternalExamTypeBO> getAllExamSubjectRuleSettingsSubInternals()
			throws Exception {
		Session session = null;
		List<ExamInternalExamTypeBO> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="select b from ExamInternalExamTypeBO b where b.isActive=1";
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public List<ExamAssignmentTypeMasterBO> getAllAssignment() throws Exception {
		Session session = null;
		List<ExamAssignmentTypeMasterBO> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="from ExamAssignmentTypeMasterBO b where b.isActive=1";
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public List<ExamMultipleAnswerScriptMasterBO> getAllMultipleAnswerScript()
			throws Exception {
		Session session = null;
		List<ExamMultipleAnswerScriptMasterBO> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="from ExamMultipleAnswerScriptMasterBO b where b.isActive=1";
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public List<Subject> getSubjectsByCourseYearSemester(String academicYear,
			String courseId, String schemeNo) throws Exception {
		Session session = null;
		List<Subject> subjects = null;
		try {
			session = HibernateUtil.getSession();
			String query="select ss.subject from CurriculumSchemeDuration cd" +
					" join cd.curriculumSchemeSubjects cs" +
					" join cs.subjectGroup.subjectGroupSubjectses ss" +
					" where cd.semesterYearNo="+schemeNo+" and cd.academicYear="+academicYear+
					" and cd.curriculumScheme.course.id="+courseId+" and ss.isActive=1 and ss.subject.isActive=1 group by ss.subject.id";
			Query selectedCandidatesQuery=session.createQuery(query);
			subjects = selectedCandidatesQuery.list();
			return subjects;
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
	 * @see com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction#addAll(java.util.List)
	 */
	@Override
	public boolean addAll(List<SubjectRuleSettings> bos) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		SubjectRuleSettings bo;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<SubjectRuleSettings> tcIterator = bos.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();
				session.saveOrUpdate(bo);
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
	public List<String> checkExited(String query, String[] courseIds)
			throws Exception {
		Session session = null;
		List<String> courseName=new ArrayList<String>();
		List<Integer> existList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			existList = selectedCandidatesQuery.list();
			for(int i=0;i<courseIds.length;i++){
				if(!existList.contains(Integer.parseInt(courseIds[i]))){
					String name=(String)session.createQuery("select c.name from Course c where c.id="+courseIds[i]).uniqueResult();
					courseName.add(name);
				}
			}
			return courseName;
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
	 * @see com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction#getSubjectListByQuery(java.lang.String)
	 */
	@Override
	public List<Object[]> getSubjectListByQuery(String query) throws Exception {
		Session session = null;
		List<Object[]> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
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
	 * @see com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction#getBOobjectByQuery(java.lang.String)
	 */
	@Override
	public SubjectRuleSettings getBOobjectByQuery(String query)
			throws Exception {
		Session session = null;
		SubjectRuleSettings bo = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			bo = (SubjectRuleSettings)selectedCandidatesQuery.uniqueResult();
			return bo;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public boolean deleteSubjectRuleSettingsForSubject(
			SubjectRuleSettingsForm subjectRuleSettingsForm, String query)
			throws Exception {
		Session session = null;
		SubjectRuleSettings bo = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Transaction tx=session.beginTransaction();
			Query selectedCandidatesQuery=session.createQuery(query);
			bo = (SubjectRuleSettings)selectedCandidatesQuery.uniqueResult();
			bo.setIsActive(false);
			Query q=session.createQuery("update SubjectRuleSettingsAssignment a set a.isActive=0 where a.examSubjectRuleSettings.id="+bo.getId());
			q.executeUpdate();
			Query q1=session.createQuery("update SubjectRuleSettingsAttendance a set a.isActive=0 where a.examSubjectRuleSettings.id="+bo.getId());
			q1.executeUpdate();
			Query q2=session.createQuery("update SubjectRuleSettingsMulAnsScript a set a.isActive=0 where a.examSubjectRuleSettings.id="+bo.getId());
			q2.executeUpdate();
			Query q3=session.createQuery("update SubjectRuleSettingsMulEvaluator a set a.isActive=0 where a.examSubjectRuleSettings.id="+bo.getId());
			q3.executeUpdate();
			Query q4=session.createQuery("update SubjectRuleSettingsSubInternal a set a.isActive=0 where a.examSubjectRuleSettings.id="+bo.getId());
			q4.executeUpdate();
			
			tx.commit();
			return true;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public boolean reActivateSubjectRuleSettings(int id) throws Exception {
		Session session = null;
		SubjectRuleSettings bo = null;
		try {
			if(id>0){
			session = InitSessionFactory.getInstance().openSession();
			Transaction tx=session.beginTransaction();
			bo = (SubjectRuleSettings)session.get(SubjectRuleSettings.class,id);
			bo.setIsActive(true);
			Query q=session.createQuery("update SubjectRuleSettingsAssignment a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
			q.executeUpdate();
			Query q1=session.createQuery("update SubjectRuleSettingsAttendance a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
			q1.executeUpdate();
			Query q2=session.createQuery("update SubjectRuleSettingsMulAnsScript a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
			q2.executeUpdate();
			Query q3=session.createQuery("update SubjectRuleSettingsMulEvaluator a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
			q3.executeUpdate();
			Query q4=session.createQuery("update SubjectRuleSettingsSubInternal a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
			q4.executeUpdate();
			
			tx.commit();
			return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public boolean deleteCompleteSubjectRuleSettings(SubjectRuleSettingsForm subjectRuleSettingsForm, String query)
			throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		List<ExamSubjectRuleSettingsBO> boList = null;
		ExamSubjectRuleSettingsBO bo;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query selectedCandidatesQuery=session.createQuery(query);
			boList = selectedCandidatesQuery.list();
			Iterator<ExamSubjectRuleSettingsBO> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();
				bo.setIsActive(false);
				session.update(bo);
				Query q=session.createQuery("update SubjectRuleSettingsAssignment a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
				q.executeUpdate();
				Query q1=session.createQuery("update SubjectRuleSettingsAttendance a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
				q1.executeUpdate();
				Query q2=session.createQuery("update SubjectRuleSettingsMulAnsScript a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
				q2.executeUpdate();
				Query q3=session.createQuery("update SubjectRuleSettingsMulEvaluator a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
				q3.executeUpdate();
				Query q4=session.createQuery("update SubjectRuleSettingsSubInternal a set a.isActive=1 where a.examSubjectRuleSettings.id="+bo.getId());
				q4.executeUpdate();
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
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ISubjectRuleSettingsTransaction#checkExitedForCopy(java.lang.String, java.lang.String[])
	 */
	public List<String> checkExitedForCopy(String query, String[] courseIds) throws Exception {
	Session session = null;
	List<String> courseName=new ArrayList<String>();
	List<Integer> existList = null;
	try {
		session = HibernateUtil.getSession();
		Query selectedCandidatesQuery=session.createQuery(query);
		existList = selectedCandidatesQuery.list();
		for(int i=0;i<courseIds.length;i++){
			if(existList.contains(Integer.parseInt(courseIds[i]))){
				String name=(String)session.createQuery("select c.name from Course c where c.id="+courseIds[i]).uniqueResult();
				courseName.add(name);
			}
		}
		return courseName;
	} catch (Exception e) {
		log.error("Error while retrieving selected candidates.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
	}
	
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 * @throws Exception
	 */
	public List<String> getSubjectsForCopy(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		Session session = null;
		List<Object[]> subList = null;
		List<String> str=new ArrayList<String>();
		try {
			session = HibernateUtil.getSession();
			String schemeNo="";
			if(subjectRuleSettingsForm.getSchemeNo()!=null && !subjectRuleSettingsForm.getSchemeNo().isEmpty()){
				schemeNo=subjectRuleSettingsForm.getSchemeNo();
			}else{
				if(!subjectRuleSettingsForm.getSchemeType().isEmpty()){
					if(subjectRuleSettingsForm.getSchemeType().equalsIgnoreCase("1")){
						schemeNo="1,3,5,7,9";
					}else if(subjectRuleSettingsForm.getSchemeType().equalsIgnoreCase("2")){
						schemeNo="2,4,6,8,10";
					}else{
						schemeNo="1,2,3,4,5,6,7,8,9,10";
					}
				}
			}
			String [] tempArray = subjectRuleSettingsForm.getCourseIds();
			StringBuilder intType = new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				 intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
			String q="select cd.curriculumScheme.course.id,ss.subject.id,cd.semesterYearNo from CurriculumSchemeDuration cd" +
					" join cd.curriculumSchemeSubjects cs" +
					" join cs.subjectGroup.subjectGroupSubjectses ss" +
					" where cd.semesterYearNo in ("+schemeNo+") and cd.academicYear="+subjectRuleSettingsForm.getToYear()+
					" and cd.curriculumScheme.course.id in("+intType+")";
			Query selectedCandidatesQuery=session.createQuery(q);
			subList = selectedCandidatesQuery.list();
			if(subList!=null && !subList.isEmpty()){
				Iterator<Object[]> itr=subList.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if(obj[0]!=null && obj[1]!=null && obj[2]!=null){
						str.add(obj[0].toString()+"_"+obj[1].toString()+"_"+obj[2].toString());
					}
				}
			}
			return str;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public boolean copySubjectRuleSettings(List<String> list, String query,
			SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		List<SubjectRuleSettings> boList = null;
		SubjectRuleSettings bo;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query selectedCandidatesQuery=session.createQuery(query);
			boList = selectedCandidatesQuery.list();
			Iterator<SubjectRuleSettings> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();
				if(bo.getSubject()!=null && bo.getSchemeNo()!=null && bo.getCourse()!=null){
					if(list.contains(bo.getCourse().getId()+"_"+bo.getSubject().getId()+"_"+bo.getSchemeNo())){
						SubjectRuleSettings bo1=new SubjectRuleSettings();
						bo1.setAcademicYear(Integer.parseInt(subjectRuleSettingsForm.getToYear()));
						bo1.setCourse(bo.getCourse());
						bo1.setCreatedBy(subjectRuleSettingsForm.getUserId());
						bo1.setCreatedDate(new Date());
						bo1.setModifiedBy(subjectRuleSettingsForm.getUserId());
						bo1.setLastModifiedDate(new Date());
						bo1.setIsActive(true);
						bo1.setSchemeNo(bo.getSchemeNo());
						bo1.setSubject(bo.getSubject());
						bo1.setFinalPracticalInternalEnteredMaxMark(bo.getFinalPracticalInternalEnteredMaxMark());
						bo1.setFinalPracticalInternalIsAssignment(bo.getFinalPracticalInternalIsAssignment());
						bo1.setFinalPracticalInternalIsAttendance(bo.getFinalPracticalInternalIsAttendance());
						bo1.setFinalPracticalInternalIsSubInternal(bo.getFinalPracticalInternalIsSubInternal());
						bo1.setFinalPracticalInternalMaximumMark(bo.getFinalPracticalInternalMaximumMark());
						bo1.setFinalPracticalInternalMinimumMark(bo.getFinalPracticalInternalMinimumMark());
						bo1.setFinalTheoryInternalEnteredMaxMark(bo.getFinalTheoryInternalEnteredMaxMark());
						bo1.setFinalTheoryInternalIsAssignment(bo.getFinalTheoryInternalIsAssignment());
						bo1.setFinalTheoryInternalIsAttendance(bo.getFinalTheoryInternalIsAttendance());
						bo1.setFinalTheoryInternalIsAttendance(bo.getFinalTheoryInternalIsAttendance());
						bo1.setFinalTheoryInternalIsSubInternal(bo.getFinalTheoryInternalIsSubInternal());
						bo1.setFinalTheoryInternalMaximumMark(bo.getFinalTheoryInternalMaximumMark());
						bo1.setFinalTheoryInternalEnteredMaxMark(bo.getFinalTheoryInternalEnteredMaxMark());
						bo1.setFinalTheoryInternalMaximumMark(bo.getFinalTheoryInternalMaximumMark());
						bo1.setFinalTheoryInternalMinimumMark(bo.getFinalTheoryInternalMinimumMark());
						bo1.setPracticalEseEnteredMaxMark(bo.getPracticalEseEnteredMaxMark());
						bo1.setPracticalEseIsMultipleAnswerScript(bo.getPracticalEseIsMultipleAnswerScript());
						bo1.setPracticalEseIsMultipleEvaluator(bo.getPracticalEseIsMultipleEvaluator());
						bo1.setPracticalEseIsRegular(bo.getPracticalEseIsRegular());
						bo1.setPracticalEseMaximumMark(bo.getPracticalEseMaximumMark());
						bo1.setPracticalEseMinimumMark(bo.getPracticalEseMinimumMark());
						bo1.setPracticalEseTheoryFinalMaximumMark(bo.getPracticalEseTheoryFinalMaximumMark());
						bo1.setPracticalEseTheoryFinalMinimumMark(bo.getPracticalEseTheoryFinalMinimumMark());
						bo1.setPracticalIntEntryMaxMarksTotal(bo.getPracticalIntEntryMaxMarksTotal());
						bo1.setPracticalIntMaxMarksTotal(bo.getPracticalIntMaxMarksTotal());
						bo1.setPracticalIntMinMarksTotal(bo.getPracticalIntMinMarksTotal());
						bo1.setSelectBestOfPracticalInternal(bo.getSelectBestOfPracticalInternal());
						bo1.setSelectBestOfTheoryInternal(bo.getSelectBestOfTheoryInternal());
						bo1.setSubjectFinalIsAttendance(bo.getSubjectFinalIsAttendance());
						bo1.setSubjectFinalIsInternalExam(bo.getSubjectFinalIsInternalExam());
						bo1.setSubjectFinalIsPracticalExam(bo.getSubjectFinalIsPracticalExam());
						bo1.setSubjectFinalIsTheoryExam(bo.getSubjectFinalIsTheoryExam());
						bo1.setSubjectFinalMaximum(bo.getSubjectFinalMaximum());
						bo1.setSubjectFinalMinimum(bo.getSubjectFinalMinimum());
						bo1.setSubjectFinalValuated(bo.getSubjectFinalValuated());
						bo1.setTheoryEseEnteredMaxMark(bo.getTheoryEseEnteredMaxMark());
						bo1.setTheoryEseIsMultipleAnswerScript(bo.getTheoryEseIsMultipleAnswerScript());
						bo1.setTheoryEseIsMultipleEvaluator(bo.getTheoryEseIsMultipleEvaluator());
						bo1.setTheoryEseIsRegular(bo.getTheoryEseIsRegular());
						bo1.setTheoryEseMaximumMark(bo.getTheoryEseMaximumMark());
						bo1.setTheoryEseMinimumMark(bo.getTheoryEseMinimumMark());
						bo1.setTheoryIntEntryMaxMarksTotal(bo.getTheoryIntEntryMaxMarksTotal());
						bo1.setTheoryIntMaxMarksTotal(bo.getTheoryIntMaxMarksTotal());
						bo1.setTheoryIntMinMarksTotal(bo.getTheoryIntMinMarksTotal());
						bo1.setTheoryEseTheoryFinalMaximumMark(bo.getTheoryEseTheoryFinalMaximumMark());
						bo1.setTheoryEseTheoryFinalMinimumMark(bo.getTheoryEseTheoryFinalMinimumMark());
						
						Set<SubjectRuleSettingsMulEvaluator>  oldEvl=bo.getExamSubjectRuleSettingsMulEvaluators();
						if(oldEvl!=null && !oldEvl.isEmpty()){
							Set<SubjectRuleSettingsMulEvaluator> newEvl=new HashSet<SubjectRuleSettingsMulEvaluator>();
							Iterator<SubjectRuleSettingsMulEvaluator> evlItr=oldEvl.iterator();
							while (evlItr.hasNext()) {
								SubjectRuleSettingsMulEvaluator ev = (SubjectRuleSettingsMulEvaluator) evlItr.next();
								SubjectRuleSettingsMulEvaluator ev1=new SubjectRuleSettingsMulEvaluator();
								ev1.setEvaluatorId(ev.getEvaluatorId());
								ev1.setIsActive(true);
								ev1.setIsTheoryPractical(ev.getIsTheoryPractical());
								ev1.setNoOfEvaluations(ev.getNoOfEvaluations());
								ev1.setTypeOfEvaluation(ev.getTypeOfEvaluation());
								newEvl.add(ev1);
							}
							bo1.setExamSubjectRuleSettingsMulEvaluators(newEvl);
						}
						
						Set<SubjectRuleSettingsAssignment> oldass=bo.getExamSubjectRuleSettingsAssignments();
						if(oldass!=null && !oldass.isEmpty()){
							Set<SubjectRuleSettingsAssignment> newass=new HashSet<SubjectRuleSettingsAssignment>();
							Iterator<SubjectRuleSettingsAssignment> evlItr=oldass.iterator();
							while (evlItr.hasNext()) {
								SubjectRuleSettingsAssignment as = (SubjectRuleSettingsAssignment) evlItr.next();
								SubjectRuleSettingsAssignment as1=new SubjectRuleSettingsAssignment();
								as1.setAssignmentTypeId(as.getAssignmentTypeId());
								as1.setIsActive(true);
								as1.setIsTheoryPractical(as.getIsTheoryPractical());
								as1.setMaximumMark(as.getMaximumMark());
								as1.setMinimumMark(as.getMinimumMark());
								newass.add(as1);
							}
							bo1.setExamSubjectRuleSettingsAssignments(newass);
						}
						
						Set<SubjectRuleSettingsAttendance> oldatt=bo.getExamSubjectRuleSettingsAttendances();
						if(oldatt!=null && !oldatt.isEmpty()){
							Set<SubjectRuleSettingsAttendance> newatt=new HashSet<SubjectRuleSettingsAttendance>();
							Iterator<SubjectRuleSettingsAttendance> evlItr=oldatt.iterator();
							while (evlItr.hasNext()) {
								SubjectRuleSettingsAttendance at = (SubjectRuleSettingsAttendance) evlItr.next();
								SubjectRuleSettingsAttendance at1=new SubjectRuleSettingsAttendance();
								at1.setAttendanceTypeId(at.getAttendanceTypeId());
								at1.setIsActive(true);
								at1.setIsTheoryPractical(at.getIsTheoryPractical());
								at1.setIsCoCurricular(at.getIsCoCurricular());
								at1.setIsLeave(at.getIsLeave());
								newatt.add(at1);
							}
							bo1.setExamSubjectRuleSettingsAttendances(newatt);
						}
						
						Set<SubjectRuleSettingsMulAnsScript> oldAns=bo.getExamSubjectRuleSettingsMulAnsScripts();
						if(oldAns!=null && !oldAns.isEmpty()){
							Set<SubjectRuleSettingsMulAnsScript> newAns=new HashSet<SubjectRuleSettingsMulAnsScript>();
							Iterator<SubjectRuleSettingsMulAnsScript> evlItr=oldAns.iterator();
							while (evlItr.hasNext()) {
								SubjectRuleSettingsMulAnsScript at = (SubjectRuleSettingsMulAnsScript) evlItr.next();
								SubjectRuleSettingsMulAnsScript at1=new SubjectRuleSettingsMulAnsScript();
								at1.setMultipleAnswerScriptId(at.getMultipleAnswerScriptId());
								at1.setIsActive(true);
								at1.setIsTheoryPractical(at.getIsTheoryPractical());
								at1.setValue(at.getValue());
								newAns.add(at1);
							}
							bo1.setExamSubjectRuleSettingsMulAnsScripts(newAns);
						}
						
						Set<SubjectRuleSettingsSubInternal> oldInt=bo.getExamSubjectRuleSettingsSubInternals();
						if(oldInt!=null && !oldInt.isEmpty()){
							Set<SubjectRuleSettingsSubInternal> newInt=new HashSet<SubjectRuleSettingsSubInternal>();
							Iterator<SubjectRuleSettingsSubInternal> evlItr=oldInt.iterator();
							while (evlItr.hasNext()) {
								SubjectRuleSettingsSubInternal at = (SubjectRuleSettingsSubInternal) evlItr.next();
								SubjectRuleSettingsSubInternal at1=new SubjectRuleSettingsSubInternal();
								at1.setEnteredMaxMark(at.getEnteredMaxMark());
								at1.setIsActive(true);
								at1.setIsTheoryPractical(at.getIsTheoryPractical());
								at1.setInternalExamTypeId(at.getInternalExamTypeId());
								at1.setMaximumMark(at.getMaximumMark());
								at1.setMinimumMark(at.getMinimumMark());
								newInt.add(at1);
							}
							bo1.setExamSubjectRuleSettingsSubInternals(newInt);
						}
						session.save(bo1);
					}
				}
				if(++count % 20 == 0){
					session.flush();
//					session.clear();
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
	public String getNamesForBos(String bo, String ids)
			throws Exception {
		Session session = null;
		StringBuilder names =new StringBuilder();
		List<String> list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("select b.name from "+bo+" b where b.isActive=1 and b.id in ("+ids+")");
			list = selectedCandidatesQuery.list();
			if(list!=null && !list.isEmpty()){
				Iterator<String> itr=list.iterator();
				while (itr.hasNext()) {
					String st = (String) itr.next();
					names.append(" ").append(st);
				}
			}
			return names.toString();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		}
}
