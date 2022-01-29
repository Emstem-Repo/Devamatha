package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ConsolidatedMarksCardProgrammePart;
import com.kp.cms.bo.exam.ExamGradeDefinitionBO;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.exam.NewConsolidatedMarksCardTo;
import com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ConsolidatedMarksCardTransactionImpl implements
		IConsolidatedMarksCardTransaction {
	/**
	 * Singleton object of ConsolidatedMarksCardTransactionImpl
	 */
	private static volatile ConsolidatedMarksCardTransactionImpl consolidatedMarksCardTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ConsolidatedMarksCardTransactionImpl.class);
	private ConsolidatedMarksCardTransactionImpl() {
		
	}
	/**
	 * return singleton object of ConsolidatedMarksCardTransactionImpl.
	 * @return
	 */
	public static ConsolidatedMarksCardTransactionImpl getInstance() {
		if (consolidatedMarksCardTransactionImpl == null) {
			consolidatedMarksCardTransactionImpl = new ConsolidatedMarksCardTransactionImpl();
		}
		return consolidatedMarksCardTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction#getSemesterWiseDetails(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Map<Integer, NewConsolidatedMarksCardTo>> getSemesterWiseDetails( List<Integer> studentIds) throws Exception {
		Session session = null;
		List<Student> list = null;
		Map<Integer, Map<Integer, NewConsolidatedMarksCardTo>> detailMap=new HashMap<Integer, Map<Integer,NewConsolidatedMarksCardTo>>();
		Map<Integer, NewConsolidatedMarksCardTo> schemeMap;
		NewConsolidatedMarksCardTo to;
		List<Integer> subjectId;
		Set<SubjectGroupSubjects> subGroupSubjects;
		try {
			session = HibernateUtil.getSession();
			// current class
			Query selectedCandidatesQuery=session.createQuery("from Student s where id in (:ids)").setParameterList("ids",studentIds);
			list = selectedCandidatesQuery.list();
			Iterator<Student> itr=list.iterator();
			while (itr.hasNext()) {
				Student bo=itr.next();
				if(!detailMap.containsKey(bo.getId()) && bo.getClassSchemewise()!=null){
					to=new NewConsolidatedMarksCardTo();
					to.setClassId(bo.getClassSchemewise().getClasses().getId());
					to.setSchemeNo(bo.getClassSchemewise().getClasses().getTermNumber());
					subjectId=new ArrayList<Integer>();
					Set<ApplicantSubjectGroup> subGrp=bo.getAdmAppln().getApplicantSubjectGroups();
					if(subGrp!=null && !subGrp.isEmpty()){
						Iterator<ApplicantSubjectGroup> subGrpItr=subGrp.iterator();
						while (subGrpItr.hasNext()) {
							ApplicantSubjectGroup appSubGrp= subGrpItr .next();
							subGroupSubjects=appSubGrp.getSubjectGroup().getSubjectGroupSubjectses();
							if(subGroupSubjects!=null && !subGroupSubjects.isEmpty()){
								Iterator<SubjectGroupSubjects> subIterator=subGroupSubjects.iterator();
								while (subIterator.hasNext()) {
									SubjectGroupSubjects subjectGroupSubjects =subIterator .next();
									if(subjectGroupSubjects.getIsActive() && subjectGroupSubjects.getSubject().getIsActive()){
										subjectId.add(subjectGroupSubjects.getSubject().getId());
									}
								}
							}
						}
					}
					to.setSubjectIds(subjectId);
					schemeMap=new HashMap<Integer, NewConsolidatedMarksCardTo>();
					schemeMap.put(to.getSchemeNo(),to);
					detailMap.put(bo.getId(),schemeMap);
				}
			}
			
			
			// previous History
			
			List<Object[]> previousList=session.createQuery("select s.id,classHis.schemeNo,subSet.subject.id from Student s" +
										" join s.studentPreviousClassesHistory classHis" +
										" join classHis.classes.classSchemewises classSchemewise" +
										" join classSchemewise.curriculumSchemeDuration cd" +
										" join s.studentSubjectGroupHistory subjHist " +
										" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
										" where s.id in (:ids)"+
										" and classHis.schemeNo=subjHist.schemeNo order by s.id").setParameterList("ids",studentIds).list();
			
			if(previousList!=null && !previousList.isEmpty()){
				Iterator<Object[]> previousitr=previousList.iterator();
				while (previousitr.hasNext()) {
					Object[] obj =previousitr.next();
					if(obj[0]!=null && obj[1]!=null && obj[2]!=null){
						
						if(detailMap.containsKey(Integer.parseInt(obj[0].toString()))){
							schemeMap=detailMap.remove(Integer.parseInt(obj[0].toString()));
						}else
							schemeMap=new HashMap<Integer, NewConsolidatedMarksCardTo>();
							
						if(schemeMap.containsKey(Integer.parseInt(obj[1].toString())))
							to=schemeMap.remove(Integer.parseInt(obj[1].toString()));
						else
							to=new NewConsolidatedMarksCardTo();
						
						if(to.getSubjectIds()!=null && !to.getSubjectIds().isEmpty())
							subjectId=to.getSubjectIds();
						else
							subjectId=new ArrayList<Integer>();
						
						subjectId.add(Integer.parseInt(obj[2].toString()));
						to.setSubjectIds(subjectId);
						to.setSchemeNo(Integer.parseInt(obj[1].toString()));
						
						schemeMap.put(Integer.parseInt(obj[1].toString()),to);
						detailMap.put(Integer.parseInt(obj[0].toString()),schemeMap);
					}
				}
			}
			
			return detailMap;
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
	 * @see com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction#getMarksDetails(int, com.kp.cms.to.exam.NewConsolidatedMarksCardTo)
	 */
	public List<Object[]> getMarksDetails(int studentId, NewConsolidatedMarksCardTo to) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("");
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction#getStudentMarks(java.lang.String, java.util.List)
	 */
	public List getStudentMarks(String consolidateQuery,
			List<Integer> studentIds) throws Exception {
		Session session = null;
		List hallTicketList = null;
		try {
			session = HibernateUtil.getSession();
			 hallTicketList=session.createSQLQuery(consolidateQuery).setParameterList("ids", studentIds).list();
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
	 * @see com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction#saveConsolidateMarks(java.util.Map)
	 */
	@Override
	public boolean saveConsolidateMarks(
			Map<Integer, Map<String, ConsolidateMarksCard>> boMap,int courseId,int year)
			throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Transaction transaction1 = session.beginTransaction();
			session.createQuery(
					"delete from ConsolidateMarksCard where selectedCourseId="
							+ courseId + " and appliedYear=" + year).executeUpdate();
			transaction1.commit();
			ConsolidateMarksCard bo;
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator tcIterator = boMap.entrySet().iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				Map.Entry<Integer,Map<String,ConsolidateMarksCard>> pairs = (Map.Entry)tcIterator.next();
				Iterator itr=pairs.getValue().entrySet().iterator();
				while (itr.hasNext()) {
					Map.Entry<String,ConsolidateMarksCard> mapPair= (Map.Entry)itr.next();
					bo=mapPair.getValue();
					session.save(bo);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
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
	 * @see com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction#getSupplimentaryAppeared(java.util.List)
	 */
	public List<String> getSupplimentaryAppeared(List<Integer> studentIds) throws Exception {
		Session session = null;
		List<String> list = new ArrayList<String>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from StudentSupplementaryImprovementApplication s where (s.isAppearedTheory=1 or s.isAppearedPractical=1) and s.student.id in (:ids)").setParameterList("ids",studentIds);
			List<StudentSupplementaryImprovementApplication> selectedCandidatesList = selectedCandidatesQuery.list();
			if(selectedCandidatesList!=null && !selectedCandidatesList.isEmpty()){
				Iterator<StudentSupplementaryImprovementApplication> itr=selectedCandidatesList.iterator();
				while (itr.hasNext()) {
					StudentSupplementaryImprovementApplication bo =itr .next();
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
	
	public List<ExamGradeDefinitionBO> getGradeDefenitionsForACourse(int courseId) throws Exception
	{
		Session session = null;
		List<ExamGradeDefinitionBO> gradeList = new ArrayList<ExamGradeDefinitionBO>();
		
		try
		{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from ExamGradeDefinitionBO gradeDef where gradeDef.courseBO.courseID = :courseId and gradeDef.isActive = 1");
			query.setInteger("courseId", courseId);
			gradeList = query.list();
			session.flush();
			return gradeList;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			if(session != null)
			{
				session.flush();
				session.close();				
			}
			throw new ApplicationException();
		}
	}
	
	public boolean saveConsolidateMarksProgrammePart(Map<String, ConsolidatedMarksCardProgrammePart> map, int courseId, int year) throws Exception
	{
		Session session = null;
		Transaction saveTransaction = null;
		
		try
		{
			session = HibernateUtil.getSession();
			Transaction delTransaction = session.beginTransaction();
			session.createQuery("delete from ConsolidatedMarksCardProgrammePart cmcpt where cmcpt.course.id = "+ courseId + " and cmcpt.appliedYear=" + year).executeUpdate();
			delTransaction.commit();
			
			saveTransaction = session.beginTransaction();
			int count = 0;
			Set<String> set = map.keySet();
			Iterator<String> it = set.iterator();
			while(it.hasNext())
			{
				ConsolidatedMarksCardProgrammePart marksCardProgrammePart = map.get(it.next());
				session.save(marksCardProgrammePart);
				
				if(count++ % 20 == 0)
				{
					session.flush();
					session.clear();
				}
			}
			saveTransaction.commit();
			session.flush();
			session.close();
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			if(session != null)
			{
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}
	public Integer getExamCountBystudentIdSubjectIdAndClassId(int studentId,int classId,int subjectId,int examId) throws Exception {
		Session session = null;
		Long examcount=null;
		Integer count=0;
		try {
			 session = HibernateUtil.getSession();
			 String str = " select count(s.examDefinition.id) from StudentSupplementaryImprovementApplication s where "+
                          "  s.classes.id = " + classId + " and s.subject.id = " + subjectId + " and s.examDefinition.id<="+examId+" and s.student.id = " + studentId;
				Query query = session.createQuery(str);
			 examcount=(Long) query.uniqueResult();
			 count = examcount.intValue();
			if(count!=0)	  
			return count;
		 } catch (Exception e) { 
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return null;
	}
}
