package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AttendanceCondonation;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.ExamRevaluationApplicationNew;
import com.kp.cms.bo.exam.ExamSettingsBO;
import com.kp.cms.bo.exam.ExamStudentCCPA;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.ModerationMarksEntryBo;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.forms.exam.NewSupplementaryImpApplicationForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class NewExamMarksEntryTransactionImpl implements INewExamMarksEntryTransaction {

	/**
	 * Singleton object of NewExamMarksEntryTransactionImpl
	 */
	private static volatile NewExamMarksEntryTransactionImpl newExamMarksEntryTransactionImpl = null;
	private static final Log log = LogFactory.getLog(NewExamMarksEntryTransactionImpl.class);
	private NewExamMarksEntryTransactionImpl() {
		
	}
	/**
	 * return singleton object of NewExamMarksEntryTransactionImpl.
	 * @return
	 */
	public static NewExamMarksEntryTransactionImpl getInstance() {
		if (newExamMarksEntryTransactionImpl == null) {
			newExamMarksEntryTransactionImpl = new NewExamMarksEntryTransactionImpl();
		}
		return newExamMarksEntryTransactionImpl;
	}
	/* 
	 * returning the data for the input query
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewExamMarksEntryTransaction#getDataForQuery(java.lang.String)
	 */
	@Override
	public List getDataForQuery(String query) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			list = selectedCandidatesQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				//session.flush();
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getDataForQueryClasses(String query) throws Exception {
		Session session = null;
		List<Integer> list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
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
	@Override
	public boolean saveMarks(NewExamMarksEntryForm newExamMarksEntryForm)
			throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		List<StudentMarksTO> list=newExamMarksEntryForm.getStudentList();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<StudentMarksTO> itr=list.iterator();
			while (itr.hasNext()) {
				StudentMarksTO to = (StudentMarksTO) itr.next();
			//	if(to.getStudentId()==1967){
				if(to.getId()>0){
					if((to.getIsPractical() && !to.getPracticalMarks().equals(to.getOldPracticalMarks())) || (to.getIsTheory() && !to.getTheoryMarks().equals(to.getOldTheoryMarks()))){
						MarksEntryDetails detailBo=(MarksEntryDetails)session.get(MarksEntryDetails.class, to.getId());
						if(to.getIsPractical()){
							detailBo.setPracticalMarks(to.getPracticalMarks().trim());
						}
						if(to.getIsTheory()){
							detailBo.setTheoryMarks(to.getTheoryMarks().trim());
						}
						detailBo.setModifiedBy(newExamMarksEntryForm.getUserId());
						detailBo.setLastModifiedDate(new Date());
						session.update(detailBo);
					}
				}else{
					if((to.getIsTheory() && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()) || (to.getIsPractical() && to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty())){
						MarksEntry marksEntry=null;
						String query="from MarksEntry m where m.exam.id="+newExamMarksEntryForm.getExamId()+" and m.student.id="+to.getStudentId()+" and m.classes.id="+to.getClassId();
						if(newExamMarksEntryForm.getEvaluatorType()!=null && !newExamMarksEntryForm.getEvaluatorType().isEmpty())
							query=query+" and m.evaluatorType="+newExamMarksEntryForm.getEvaluatorType();
						else
							query=query+" and m.evaluatorType is null";
						if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty())
							query=query+" and m.answerScript="+newExamMarksEntryForm.getAnswerScriptType();
						else
							query=query+" and m.answerScript is null";
						List<MarksEntry> marksEntrys=session.createQuery(query).list();
						if(marksEntrys==null || marksEntrys.isEmpty()){
							marksEntry=new MarksEntry();
							Student student=new Student();
							student.setId(to.getStudentId());
							marksEntry.setStudent(student);
							ExamDefinitionBO exam=new ExamDefinitionBO();
							exam.setId(Integer.parseInt(newExamMarksEntryForm.getExamId()));
							marksEntry.setExam(exam);
							if(newExamMarksEntryForm.getEvaluatorType()!=null && !newExamMarksEntryForm.getEvaluatorType().isEmpty()){
								marksEntry.setEvaluatorType(Integer.parseInt(newExamMarksEntryForm.getEvaluatorType()));
							}
							if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
								marksEntry.setAnswerScript(Integer.parseInt(newExamMarksEntryForm.getAnswerScriptType()));
							}
							Classes classes=new Classes();
							classes.setId(to.getClassId());
							marksEntry.setClasses(classes);
							marksEntry.setCreatedBy(newExamMarksEntryForm.getUserId());
							marksEntry.setCreatedDate(new Date());
							marksEntry.setModifiedBy(newExamMarksEntryForm.getUserId());
							marksEntry.setLastModifiedDate(new Date());
							Set<MarksEntryDetails> marksEntryDetails=new HashSet<MarksEntryDetails>();
							
							MarksEntryDetails detail=new MarksEntryDetails();
							Subject subject=new Subject();
							subject.setId(Integer.parseInt(newExamMarksEntryForm.getSubjectId()));
							detail.setSubject(subject);
							if(to.getIsTheory()){
								detail.setTheoryMarks(to.getTheoryMarks().trim());
								detail.setIsTheorySecured(false);
							}
							if(to.getIsPractical()){
								detail.setPracticalMarks(to.getPracticalMarks().trim());
								detail.setIsPracticalSecured(false);
							}
							detail.setCreatedBy(newExamMarksEntryForm.getUserId());
							detail.setCreatedDate(new Date());
							detail.setModifiedBy(newExamMarksEntryForm.getUserId());
							detail.setLastModifiedDate(new Date());
							//code added by chandra
							detail.setIsGracing(false);
							//
							marksEntryDetails.add(detail);
							marksEntry.setMarksDetails(marksEntryDetails);
							session.save(marksEntry);
						}else{
							Iterator<MarksEntry> marksitr=marksEntrys.iterator();
							if (marksitr.hasNext()) {
								 marksEntry = (MarksEntry) marksitr.next();
							}
							MarksEntryDetails detail=(MarksEntryDetails)session.createQuery("from MarksEntryDetails m where m.marksEntry.id="+marksEntry.getId()+"" +
									" and m.subject.id="+newExamMarksEntryForm.getSubjectId()).uniqueResult();
							if(detail==null){
								detail=new MarksEntryDetails();
								Subject subject=new Subject();
								subject.setId(Integer.parseInt(newExamMarksEntryForm.getSubjectId()));
								detail.setSubject(subject);
								detail.setCreatedBy(newExamMarksEntryForm.getUserId());
								detail.setCreatedDate(new Date());
								//code added by chandra
								detail.setIsGracing(false);
								//
							}
							detail.setMarksEntry(marksEntry);
							if(to.getIsTheory()){
								detail.setTheoryMarks(to.getTheoryMarks().trim());
								detail.setIsTheorySecured(false);
							}
							if(to.getIsPractical()){
								detail.setPracticalMarks(to.getPracticalMarks().trim());
								detail.setIsPracticalSecured(false);
							}
							detail.setModifiedBy(newExamMarksEntryForm.getUserId());
							detail.setLastModifiedDate(new Date());
							marksEntry.setModifiedBy(newExamMarksEntryForm.getUserId());
							marksEntry.setLastModifiedDate(new Date());
							session.saveOrUpdate(detail);
							session.update(marksEntry);
						}
					}
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			//}
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
	 * @see com.kp.cms.transactions.exam.INewExamMarksEntryTransaction#getExamAbscentCode()
	 */
	public List<String> getExamAbscentCode()throws Exception{
		Session session = null;
		List<String> examAbscentCode=new ArrayList<String>();
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from ExamSettingsBO e ");
			List<ExamSettingsBO> examSettingsBOs =query.list();
			if(examSettingsBOs!=null && !examSettingsBOs.isEmpty()){
				
				Iterator<ExamSettingsBO> itr = examSettingsBOs.iterator();
				while (itr.hasNext()) {
					ExamSettingsBO examSettingsBO = (ExamSettingsBO) itr
							.next();
					if(examSettingsBO.getAbsentCodeMarkEntry()!=null){
						examAbscentCode.add(examSettingsBO.getAbsentCodeMarkEntry());
					}
					if(examSettingsBO.getNotProcedCodeMarkEntry()!=null){
						examAbscentCode.add(examSettingsBO.getNotProcedCodeMarkEntry());
					}
					if(examSettingsBO.getWithHeldCodeMarkEntry()!=null){
						examAbscentCode.add(examSettingsBO.getWithHeldCodeMarkEntry());
					}
				}
			}
			return examAbscentCode;
		} catch (Exception e) {
			log.error("Error while retrieving ExamAbscentCode.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	public Double getMaxMarkOfSubject(NewSecuredMarksEntryForm newSecuredMarksEntryForm)throws Exception{
		Session session = null;
		Double maxMarks=null;
		try{
			session = HibernateUtil.getSession();
			String query="select internal.enteredMaxMark," +
					" s.theoryEseEnteredMaxMark, s.practicalEseEnteredMaxMark,ansScript.value from SubjectRuleSettings s" +
					" left join s.examSubjectRuleSettingsSubInternals internal" +
					" left join s.examSubjectRuleSettingsMulEvaluators eval" +
					" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " +
					" where s.course.id="+newSecuredMarksEntryForm.getCourse()+
					" and s.academicYear="+newSecuredMarksEntryForm.getYear()+
					" and s.schemeNo=" +newSecuredMarksEntryForm.getSchemeNo()+
					" and s.subject.id=" +newSecuredMarksEntryForm.getSubjectId();
			if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
				query=query+" and eval.evaluatorId="+newSecuredMarksEntryForm.getEvaluatorType()+
				" and eval.isTheoryPractical='"+newSecuredMarksEntryForm.getSubjectType()+"'";
			}
			if(newSecuredMarksEntryForm.getAnswerScriptType()!=null && !newSecuredMarksEntryForm.getAnswerScriptType().isEmpty()){
				query=query+" and ansScript.multipleAnswerScriptId="+newSecuredMarksEntryForm.getAnswerScriptType()+
				" and ansScript.isTheoryPractical='"+newSecuredMarksEntryForm.getSubjectType()+"'";
			}	
			if(newSecuredMarksEntryForm.getExamType().equals("Internal")){
				query=query+" and internal.internalExamTypeId=(select e.internalExamType.id" +
						" from ExamDefinition e where e.id="+newSecuredMarksEntryForm.getExamId()+
				") and internal.isTheoryPractical='"+newSecuredMarksEntryForm.getSubjectType()+"'";
			}
			query=query+" and s.isActive=1 group by s.id";
			List<Object[]> list=session.createQuery(query).list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(newSecuredMarksEntryForm.getExamType().equals("Internal")){
						if(objects[0]!=null)
							maxMarks=new Double(objects[0].toString());
					}else{
						if(newSecuredMarksEntryForm.getAnswerScriptType()!=null && !newSecuredMarksEntryForm.getAnswerScriptType().isEmpty()){
							if(objects[3]!=null)
								maxMarks=new Double(objects[3].toString());
						}else{
							if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("T") && objects[1]!=null)
								maxMarks=new Double(objects[1].toString());
							if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("P") && objects[2]!=null)
								maxMarks=new Double(objects[2].toString());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error while retrieving ExamAbscentCode.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return maxMarks;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewExamMarksEntryTransaction#getExamDiffPercentage()
	 */
	@Override
	public BigDecimal getExamDiffPercentage() throws Exception {
		Session session = null;
		BigDecimal diffPercentage=null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from ExamSettingsBO e ");
			List<ExamSettingsBO> examSettingsBOs =query.list();
			if(examSettingsBOs!=null && !examSettingsBOs.isEmpty()){
				
				Iterator<ExamSettingsBO> itr = examSettingsBOs.iterator();
				while (itr.hasNext()) {
					ExamSettingsBO examSettingsBO = (ExamSettingsBO) itr
							.next();
					diffPercentage=examSettingsBO.getMaxAllwdDiffPrcntgMultiEvaluator();
				}
			}
			return diffPercentage;
		} catch (Exception e) {
			log.error("Error while retrieving ExamAbscentCode.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Double getMaxMarkOfSubject(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		Session session = null;
		Double maxMarks=null;
		String subType="";
		if(newExamMarksEntryForm.getSubjectType()!=null){
			if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
				subType="t";
			}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
				subType="p";
			}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("11")){
				subType="t";// Still its not completed
			}
		}
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		try{
			session = HibernateUtil.getSession();
			String query="select internal.enteredMaxMark," +
					" s.theoryEseEnteredMaxMark, s.practicalEseEnteredMaxMark, ansScript.value, s.academicYear from SubjectRuleSettings s" +
					" left join s.examSubjectRuleSettingsSubInternals internal" +
					" left join s.examSubjectRuleSettingsMulEvaluators eval" +
					" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " +
					" where s.course.id="+newExamMarksEntryForm.getCourseId()+
					" and s.schemeNo=" +newExamMarksEntryForm.getSchemeNo()+
					" and s.subject.id=" +newExamMarksEntryForm.getSubjectId();
			if(newExamMarksEntryForm.getEvaluatorType()!=null && !newExamMarksEntryForm.getEvaluatorType().isEmpty()){
				query=query+" and eval.evaluatorId="+newExamMarksEntryForm.getEvaluatorType()+
				" and eval.isTheoryPractical='"+subType+"'";
			}
			if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
				query=query+" and ansScript.multipleAnswerScriptId="+newExamMarksEntryForm.getAnswerScriptType()+
				" and ansScript.isTheoryPractical='"+subType+"'";
			}	
			if(newExamMarksEntryForm.getExamType().equals("Internal")){
				query=query+" and internal.internalExamTypeId=(select e.internalExamType.id" +
						" from ExamDefinition e where e.id="+newExamMarksEntryForm.getExamId()+
				") and internal.isTheoryPractical='"+subType+"'";
			}
			/*if(newExamMarksEntryForm.getExamType().equals("Supplementary")){
				query=query+" and s.academicYear>=(select e.examForJoiningBatch from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+") ";
			}*/
			query=query+" and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+") and s.isActive=1 group by s.id";
			List<Object[]> list=session.createQuery(query).list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(newExamMarksEntryForm.getExamType().equals("Internal")){
						if(objects[0]!=null){
							maxMarks=new Double(objects[0].toString());
							if(objects[4]!=null && objects[4].toString()!=null)
								map.put(Integer.parseInt(objects[4].toString()), maxMarks);
						}
					}else{
						if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
							if(objects[3]!=null){
								maxMarks=new Double(objects[3].toString());
								if(objects[4]!=null && objects[4].toString()!=null)
									map.put(Integer.parseInt(objects[4].toString()), maxMarks);
							}
						}else{
							if(newExamMarksEntryForm.getSubjectType().equals("1")){
								if(objects[1]!=null){
									maxMarks=new Double(objects[1].toString());
									if(objects[4]!=null && objects[4].toString()!=null)
										map.put(Integer.parseInt(objects[4].toString()), maxMarks);
								}
							}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
								if(objects[2]!=null){
									maxMarks=new Double(objects[2].toString());
									if(objects[4]!=null && objects[4].toString()!=null)
										map.put(Integer.parseInt(objects[4].toString()), maxMarks);
								}
							}else{
								if(objects[1]!=null){
									maxMarks=new Double(objects[1].toString());
									if(objects[4]!=null && objects[4].toString()!=null)
										map.put(Integer.parseInt(objects[4].toString()), maxMarks);
								}
							}
						}
					}
				}
			}
			newExamMarksEntryForm.setMaxMarksMap(map);
		} catch (Exception e) {
			log.error("Error while retrieving ExamAbscentCode.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
		return maxMarks;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewExamMarksEntryTransaction#getOldRegMap(java.util.List)
	 */
	@Override
	public Map<String, String> getOldRegMap(List<Integer> schemeList)
			throws Exception {
		Session session = null;
		List list = null;
		Map<String, String> map=new HashMap<String, String>();
		try {
			session = HibernateUtil.getSession();
			if(schemeList!=null && !schemeList.isEmpty()){
				Iterator<Integer> itr=schemeList.iterator();
				while (itr.hasNext()) {
					Integer schemeNo = (Integer) itr.next();
					String query="select stu.student.id,stu.registerNo " +
								 " from StudentOldRegisterNumber stu " +
								 " where stu.isActive=1 and stu.schemeNo="+schemeNo+" order by stu.student.id ";
					list=session.createQuery(query).list();
					if(list!=null && !list.isEmpty()){
						Iterator<Object[]> litr=list.iterator();
						while (litr.hasNext()) {
							Object[] obj = (Object[]) litr.next();
							if(obj[0] != null && obj[1] != null){
								map.put(obj[0].toString()+"_"+schemeNo,obj[1].toString());
							}
						}
					}
				}
			}
			return map;
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
	public Map<String, byte[]> getStudentPhtos(List<Integer> studentIds,boolean isReg)
			throws Exception {
		Session session = null;
		Map<String, byte[]> studentPhotos=new HashMap<String, byte[]>();
		List list = null;
		try {
			if(studentIds!=null && !studentIds.isEmpty()){
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=null;
				if(isReg)
					selectedCandidatesQuery=session.createQuery("select s.registerNo, docs.document from Student s" +
						" join s.admAppln.applnDocs docs where (s.isHide=0 or s.isHide is null) and s.admAppln.isCancelled=0 and docs.isPhoto=1 and docs.document is not null and docs.document!='' and s.id in (:studentList) group by s.id").setParameterList("studentList",studentIds);
				else
					selectedCandidatesQuery=session.createQuery("select s.id, docs.document from Student s" +
					" join s.admAppln.applnDocs docs where (s.isHide=0 or s.isHide is null) and s.admAppln.isCancelled=0 and docs.isPhoto=1 and docs.document is not null and docs.document!='' and s.id in (:studentList) group by s.id").setParameterList("studentList",studentIds);
				list = selectedCandidatesQuery.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
						if(objects[0]!=null && objects[1]!=null)
							studentPhotos.put(objects[0].toString(),(byte[])objects[1]);
					}
				}
			}
			return studentPhotos;
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
	public ExamBlockUnblockHallTicketBO getExamBlockUnblockHallTicket(
			Integer studentId, int examId, int classId,String hallOrMark) throws Exception {
		Session session = null;
		ExamBlockUnblockHallTicketBO bo= null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from ExamBlockUnblockHallTicketBO e where e.studentId= " +studentId+
					" and e.classId= " +classId+
					" and e.examId= " +examId+
					" and e.hallTktOrMarksCard='"+hallOrMark+"'");
			bo =(ExamBlockUnblockHallTicketBO) selectedCandidatesQuery.uniqueResult();
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
	/**
	 * @param studentIds
	 * @param isReg
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, byte[]> getStudentPhtosMap(List<Integer> studentIds,boolean isReg)throws Exception {
		Session session = null;
		Map<Integer, byte[]> studentPhotos=new HashMap<Integer, byte[]>();
		List list = null;
		try {
			if(studentIds!=null && !studentIds.isEmpty()){
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=null;
				if(isReg)
					selectedCandidatesQuery=session.createQuery("select s.registerNo, docs.document from Student s" +
						" join s.admAppln.applnDocs docs where (s.isHide=0 or s.isHide is null) and s.admAppln.isCancelled=0 and docs.isPhoto=1 and docs.document is not null and docs.document!='' and s.id in (:studentList) group by s.id").setParameterList("studentList",studentIds);
				else
					selectedCandidatesQuery=session.createQuery("select s.id, docs.document from Student s" +
					" join s.admAppln.applnDocs docs where (s.isHide=0 or s.isHide is null) and s.admAppln.isCancelled=0 and docs.isPhoto=1 and docs.document is not null and docs.document!='' and s.id in (:studentList) group by s.id").setParameterList("studentList",studentIds);
				list = selectedCandidatesQuery.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
						if(objects[0]!=null && objects[1]!=null)
							studentPhotos.put(Integer.parseInt(objects[0].toString()),(byte[])objects[1]);
					}
				}
			}
			return studentPhotos;
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
	public boolean checkAggregateResultClassWise(int sid) throws Exception {
		Session session = null;
		boolean result = false;
		try{
			session = HibernateUtil.getSession();
			String sql = "select sum(aggr_fail) from ( select " +
					" if(EXAM_exam_settings_course.aggregate_pass_prcntg > " +
					"((sum(if(ac.section_id!=1 && ac.section_id!=3 && dont_add_in_total=0 && subType='Theory',ac.theoryObtain,0)+ (if(ac.section_id!=1 && ac.section_id!=3 && dont_add_in_total=0 && subType='Practical',ac.practicalObtain,0)))/ " +
					" (sum(if(ac.section_id!=1 && ac.section_id!=3 && dont_add_in_total=0 && subType='Theory',ac.theoryMax,0)+ (if(ac.section_id!=1 && ac.section_id!=3 && dont_add_in_total=0 && subType='Practical',ac.practicalMax,0))) " +
					" ))*100), 1, 0) as aggr_fail, " +
					" ac.student_id " +
					" from consolidated_marks_card ac " +
					" left join EXAM_exam_settings_course on ac.course_id=EXAM_exam_settings_course.course_id " +
					" where ac.student_id not in (select student_id from EXAM_student_detention_rejoin_details " +
					" where ((detain=1) or (discontinued=1)) and ((rejoin=0) or (rejoin is null))) " +
					" and section is not null " +
					" group by ac.register_no, class_id " +
					" )aggr where aggr.student_id="+sid; 
			BigDecimal fail = (BigDecimal) session.createSQLQuery(sql).uniqueResult();
			if(fail.intValue() == 1){
				result = true;
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewExamMarksEntryTransaction#getSubjects(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<Integer, String> getSubjects(String examId, String subCode,
			String examType, String year) throws Exception {
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			List<Subject> subjects = new ArrayList<Subject>();
			if(examType != null && examType.equalsIgnoreCase("Supplementary")){
				String query = "select sup.subject from StudentSupplementaryImprovementApplication sup" +
								" where (sup.isAppearedTheory=1 or sup.isAppearedPractical=1) " +
								" and sup.examDefinition.id="+examId+" group by sup.subject.id";
				if(subCode != null && subCode.equalsIgnoreCase("sCode")){
					query = query + " order by sup.subject.code";
				}else{
					query = query + " order by sup.subject.name";
				}
				subjects = session.createQuery(query).list();
			}else{
				String HQL1 = "select distinct schemeNo from ExamExamCourseSchemeDetailsBO e where e.examId = "+ examId;
				List<Integer> schemeNoList = session.createQuery(HQL1).list();
				String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.examId = "+ examId;
				List<Integer> courseList = session.createQuery(HQL).list();
				if((courseList != null && !courseList.isEmpty())&& (schemeNoList != null && !schemeNoList.isEmpty())){
					String query = "select groupsub.subject from SubjectGroup sub " +
					" join sub.curriculumSchemeSubjects curr " +
					" join  curr.curriculumSchemeDuration.classSchemewises cls" +
					" join sub.subjectGroupSubjectses groupsub" +
					" where sub.course.id in(:Courses) and cls.classes.termNumber in(:schemeNo)" +
					" and sub.isActive=1 and groupsub.isActive=1" +
					" and curr.curriculumSchemeDuration.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id= "+ examId +
					") group by groupsub.subject.id";
					if(subCode != null && subCode.equalsIgnoreCase("sCode")){
						query = query + " order by groupsub.subject.code";
					}else{
						query = query + " order by groupsub.subject.name";
					}
					subjects = session.createQuery(query).setParameterList("Courses", courseList).setParameterList("schemeNo", schemeNoList).list();
				}
			}
			if(subjects != null){
				Iterator<Subject> iterator = subjects.iterator();
				while (iterator.hasNext()) {
					Subject subject = (Subject) iterator.next();
					if(subject.getId() != 0){
						if(subCode != null && subCode.equalsIgnoreCase("sCode"))
							map.put(subject.getId(), subject.getCode()+" "+subject.getName());
						else
							map.put(subject.getId(), subject.getName()+" "+subject.getCode());
					}
				}
			}
			map = CommonUtil.sortMapByValue(map);
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return map;
	}
	/**
	 * 
	 * @param examId
	 * @return
	 */
	public String getCourseidByExamId(String examId) {
		Session session = null;
		ArrayList<Integer> list;
		StringBuffer courseIds = new StringBuffer();
		String courses = "";
		try {
			session = HibernateUtil.getSession();
			String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.examId = "
					+ examId;

			Query query = session.createQuery(HQL);
			list = new ArrayList<Integer>(query.list());

			Iterator<Integer> itr = list.iterator();
			while (itr.hasNext()) {
				Integer id = (Integer) itr.next();
				courseIds.append(id.toString() + ",");
			}
			courses = courseIds.toString();
			if (courses.endsWith(",") == true) {
				courses = StringUtils.chop(courses);
			}
			session.flush();
			session.close();

		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return courses;
	}
	
	public Boolean getExtendedDate(int studentId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception
	{

		Session session = null;
		Boolean extended = false;
			try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery(" from PublishSupplementaryImpApplication p " +
			" where p.isActive=1 and p.exam.delIsActive=1 and p.exam.id in ( select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
			" where s.student.id= " +studentId+
			" and ((s.isFailedPractical=1 or s.isFailedTheory=1) or (s.isSupplementary=1 or s.isImprovement=1))and s.classes.id in(p.classCode.id)) " +
			" and (p.endDate <'" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
			" and (p.extendedDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"') group by p.exam.id "); 

		List<PublishSupplementaryImpApplication> list =query.list();
		if (list != null && !list.isEmpty()){
			newSupplementaryImpApplicationForm.setExtended(true);
			extended=true;
			query = session.createQuery(" from PublishSupplementaryImpApplication p " +
					" where p.isActive=1 and p.exam.delIsActive=1 and p.exam.id in ( select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
					" where s.student.id= " +studentId+
					" and ((s.isFailedPractical=1 or s.isFailedTheory=1) or (s.isSupplementary=1 or s.isImprovement=1))and s.classes.id in(p.classCode.id)) " +
					" and (p.extendedFineStartDate <='" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
					" and (p.extendedFineDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"') group by p.exam.id ");
			list=query.list();
			if(list!=null && !list.isEmpty()){
				newSupplementaryImpApplicationForm.setIsFine(true);
			}
			else
			{
				query = session.createQuery(" from PublishSupplementaryImpApplication p " +
						" where p.isActive=1 and p.exam.delIsActive=1 and p.exam.id in ( select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
						" where s.student.id= " +studentId+
						" and ((s.isFailedPractical=1 or s.isFailedTheory=1) or (s.isSupplementary=1 or s.isImprovement=1))and s.classes.id in(p.classCode.id)) " +
						" and (p.extendedSuperFineStartDate <='" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
						" and (p.extendedSuperFineDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"') group by p.exam.id ");
				list=query.list();
				if(list!=null && !list.isEmpty()){
					newSupplementaryImpApplicationForm.setIsSuperFine(true);
				}
			}
		}
		session.flush();
		} catch (Exception e) {
		session.flush();
		log.error("Error during checking duplicate code.." + e);
		throw new ApplicationException(e);
		}
			return extended;
	}
	
	
	@Override
	public List<Integer> getExemptionStudentsListBySubjectId(
			String subjectId,String examId) throws Exception {
		Session session = null;
		List<Integer> exemptionsList = null;
			try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery("select examption.studentId.id from ExamMidsemExemption examption join examption.examMidsemExemptionDetails  exemptionDetails where" +
				"  exemptionDetails.subject.id='"+subjectId+"' and examption.examId.id='"+examId+"' and examption.isActive=1 and exemptionDetails.isActive=1 "); 

		exemptionsList =query.list();
		session.flush();
		} catch (Exception e) {
		session.flush();
		log.error("Error during getExemptionStudentsListBySubjectId code.." + e);
		throw new ApplicationException(e);
	}
		return exemptionsList;
	}
	
	@Override
	public String getApplicationNumber(int studentId) throws Exception {
		log.debug("call of getApplicationNumber method in newExamMarksEntryTransactionImpl.class");
		Session session = null;
		String appno="";
		try
		{
			session  = HibernateUtil.getSession();
			String hql ="select m.admAppln.applnNo from  Student m where m.id=:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", studentId);
			  appno = query.uniqueResult().toString();
			
		}
		catch (Exception e) {
			log.error("Error" +e.getMessage());
			throw new ApplicationException(e);
		}
		log.debug("end of getApplicationNumber method in newExamMarksEntryTransactionImpl.class");
		return appno;
	}
	
	
	//raghu
	@Override
	public List<StudentSupplementaryImprovementApplication> getDataForSQLQuery(String query) throws Exception {
		Session session = null;
		List<StudentSupplementaryImprovementApplication> list = null;
		try {
			session = HibernateUtil.getSession();
			SQLQuery subjectQuery = session.createSQLQuery(query);
			subjectQuery.addEntity(StudentSupplementaryImprovementApplication.class);
			list = subjectQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				//session.flush();
			}
		}
		
	}
	
	public List<Object> checkRevaluationAppAvailable(List<Integer> classIds) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			String query="select eb.examId,eb.classId,eb.classUtilBO.termNumber from ExamPublishHallTicketMarksCardBO eb where eb.isActive=1 and eb.publishFor='Revaluation/Scrutiny' and eb.examDefinitionBO.examTypeUtilBO.name='Regular'"; 
			
			if(classIds != null && !classIds.isEmpty()) {
				query +=" and eb.classes.id in (:classIds)"; 
			}
			
			query += " and ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between eb.downloadStartDate and eb.revaluationEndDate)  "; 
			Query selectedCandidatesQuery=session.createQuery(query);
			if(classIds != null && !classIds.isEmpty()) {
				selectedCandidatesQuery.setParameterList("classIds", classIds);
			}
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
	public Boolean getExtendedDateForRegular(int classId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception
	{
	
		Session session = null;
		Boolean extended = false;
			try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery( "from ExamPublishHallTicketMarksCardBO eb where eb.isActive=1 and eb.publishFor='Regular Application' and eb.examDefinitionBO.examTypeUtilBO.name='Regular' and eb.classes.id=" +classId +
			" and ((eb.downloadEndDate <'" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
			" and (eb.extendedDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"'))"); 
	
		List<ExamPublishHallTicketMarksCardBO> list =query.list();
		if (list != null && !list.isEmpty()){
			newSupplementaryImpApplicationForm.setExtended(true);
			extended=true;
			query = session.createQuery( "from ExamPublishHallTicketMarksCardBO eb where eb.isActive=1 and eb.publishFor='Regular Application' and eb.examDefinitionBO.examTypeUtilBO.name='Regular' and eb.classes.id=" +classId +
					" and ((eb.extendedFineStartDate <='" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
					" and (eb.extendedFineDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"'))"); 
			list =query.list();
			if (list != null && !list.isEmpty()){
				newSupplementaryImpApplicationForm.setIsFine(true);
			}
			else
			{
				query = session.createQuery( "from ExamPublishHallTicketMarksCardBO eb where eb.isActive=1 and eb.publishFor='Regular Application' and eb.examDefinitionBO.examTypeUtilBO.name='Regular' and eb.classes.id=" +classId +
						" and ((eb.extendedSuperFineStartDate <='" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
						" and (eb.extendedSuperFineDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"'))"); 
				list =query.list();
				if (list != null && !list.isEmpty()){
					newSupplementaryImpApplicationForm.setIsSuperFine(true);
				}
				
			}
		}
		session.flush();
		} catch (Exception e) {
		session.flush();
		log.error("Error during checking duplicate code.." + e);
		throw new ApplicationException(e);
		}
			return extended;
	}
	@Override
	public List<StudentFinalMarkDetails> getDataForSQLQueryForRevaluation(String query) throws Exception {
		Session session = null;
		List<StudentFinalMarkDetails> list = null;
		try {
			session = HibernateUtil.getSession();
			SQLQuery subjectQuery = session.createSQLQuery(query);
			subjectQuery.addEntity(StudentFinalMarkDetails.class);
			list = subjectQuery.list();
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
	
	public Boolean getExtendedDateForRevaluation(int classId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception
	{
	
		Session session = null;
		Boolean extended = false;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery( "from ExamPublishHallTicketMarksCardBO eb where eb.isActive=1 and eb.publishFor='Revaluation/Scrutiny' and eb.examDefinitionBO.examTypeUtilBO.name='Regular' and eb.classes.id=" +classId +
					" and ((eb.downloadEndDate <'" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
					" and (eb.extendedDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"'))"); 
	
			List<ExamPublishHallTicketMarksCardBO> list =query.list();
			if (list != null && !list.isEmpty()){
				newSupplementaryImpApplicationForm.setExtended(true);
				extended=true;
				query = session.createQuery( "from ExamPublishHallTicketMarksCardBO eb where eb.isActive=1 and eb.publishFor='Revaluation/Scrutiny' and eb.examDefinitionBO.examTypeUtilBO.name='Regular' and eb.classes.id=" +classId +
						" and ((eb.extendedFineStartDate <='" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
						" and (eb.extendedFineDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"'))"); 
				list =query.list();
				if (list != null && !list.isEmpty()){
					newSupplementaryImpApplicationForm.setIsFine(true);
				}
				else
				{
					query = session.createQuery( "from ExamPublishHallTicketMarksCardBO eb where eb.isActive=1 and eb.publishFor='Revaluation/Scrutiny' and eb.examDefinitionBO.examTypeUtilBO.name='Regular' and eb.classes.id=" +classId +
							" and ((eb.extendedSuperFineStartDate <='" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
							" and (eb.extendedSuperFineDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"'))"); 
					list =query.list();
					if (list != null && !list.isEmpty()){
						newSupplementaryImpApplicationForm.setIsSuperFine(true);
					}
	
				}
			}
			session.flush();
		} catch (Exception e) {
			session.flush();
			log.error("Error during checking duplicate code.." + e);
			throw new ApplicationException(e);
		}
		return extended;
	}
	
	public List<ExamRevaluationApplicationNew> getDataForForRevaluationEdit(String query) throws Exception {
		Session session = null;
		List<ExamRevaluationApplicationNew> list = null;
		try {
			session = HibernateUtil.getSession();
			SQLQuery subjectQuery = session.createSQLQuery(query);
			subjectQuery.addEntity(ExamRevaluationApplicationNew.class);
			list = subjectQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				//session.close();
			}
		}
		
	}
	
	public List getDataForQuery(String query, List studList) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query).setParameterList("studentList", studList);
			list = selectedCandidatesQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				//session.flush();
			}
		}
		
	}
	
	public boolean saveRevaluationMarks(List<ModerationMarksEntryBo> boList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ModerationMarksEntryBo tcLChecklist;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ModerationMarksEntryBo> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.saveOrUpdate(tcLChecklist);	
			}
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	
	}
	
	public ExamStudentCCPA getCCPAdetails(int studentId) throws Exception {
		
		Session session = null;
		ExamStudentCCPA examStudentCCPA ;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			Query query = session
					.createQuery("from ExamStudentCCPA e where e.student.id="+studentId);
			
			examStudentCCPA = (ExamStudentCCPA) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("impl: leaving isCastDuplcated");
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return examStudentCCPA;
	}
	
	public List getDataForQuerySQL(String query) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createSQLQuery(query);
			list = selectedCandidatesQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				//session.flush();
			}
		}
		
	}
	
	@Override
	public String getOpenCourseName(int studentId,int subjectTypeid,int streamId) throws Exception {
		log.debug("call of getOpenCourseName method in newExamMarksEntryTransactionImpl.class");
		Session session = null;
		String appno="";
		try
		{
			session  = HibernateUtil.getSession();
			String hql ="select c.subject.name from ConsolidateMarksCard c where c.student.id="+studentId+" and c.subjectTypeId="+subjectTypeid +" and c.subjectStreamId="+String.valueOf(streamId);
			Query query = session.createQuery(hql);
		
			  appno = query.uniqueResult().toString();
			
		}
		catch (Exception e) {
			log.error("Error" +e.getMessage());
			throw new ApplicationException(e);
		}
		log.debug("end of getApplicationNumber method in newExamMarksEntryTransactionImpl.class");
		return appno;
	}
	
	public Boolean getExtendedDateInternalRedo(int studentId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception
	{

		Session session = null;
		Boolean extended = false;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from ExamPublishHallTicketMarksCardBO p " +
											  " where p.isActive=1 " +
											  "   and p.examDefinitionBO.delIsActive=1 " +
											  "   and p.examDefinitionBO.id in ( select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
																				" where s.student.id= " +studentId+
																				"   and (s.isCIAFailedTheory=1 or s.isCIAFailedPractical=1 or s.isSupplementary=1) and s.classes.id in (p.classes.id)) " +
																				"   and (p.downloadEndDate <'" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
																				"   and (p.extendedDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"') group by p.examDefinitionBO.id "); 
	
			List<PublishSupplementaryImpApplication> list =query.list();
			
			if (list != null && !list.isEmpty()){
				
				newSupplementaryImpApplicationForm.setExtended(true);
				extended=true;
				query = session.createQuery(" from ExamPublishHallTicketMarksCardBO p " +
											" where p.isActive=1 " +
											"	and p.examDefinitionBO.delIsActive=1 " +
											"	and p.examDefinitionBO.id in ( select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
																			  " where s.student.id= " +studentId+
																			  "   and (s.isCIAFailedTheory=1 or s.isCIAFailedPractical=1 or s.isSupplementary=1) and s.classes.id in (p.classes.id)) " +
																			  "   and (p.extendedFineStartDate <='" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
																			  "   and (p.extendedDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"') group by p.examDefinitionBO.id ");
				list=query.list();
				
				if(list!=null && !list.isEmpty()){
					newSupplementaryImpApplicationForm.setIsFine(true);
				}
				else
				{
					query = session.createQuery(" from ExamPublishHallTicketMarksCardBO p " +
												" where p.isActive=1 " +
												"	and p.examDefinitionBO.delIsActive=1 " +
												"	and p.examDefinitionBO.id in ( select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
																	  			  " where s.student.id= " +studentId+
																	  			  "   and (s.isCIAFailedTheory=1 or s.isCIAFailedPractical=1 or s.isSupplementary=1) and s.classes.id in (p.classes.id)) " +
																	  			  "   and (p.extendedSuperFineStartDate <='" +CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"')" +
																	  			  "   and (p.extendedSuperFineDate >='"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"') group by p.examDefinitionBO.id ");
					list=query.list();
					if(list!=null && !list.isEmpty()){
						newSupplementaryImpApplicationForm.setIsSuperFine(true);
					}
				}
			}
			session.flush();
		}
		catch (Exception e) {
			
			e.printStackTrace();
			session.flush();
			throw new ApplicationException(e);
		}
		
		return extended;
	}
	
	public boolean isCondonationPaid(int studentId , int classId ) throws Exception{
		AttendanceCondonation attCondonation=null;
		Query q1=null;
		Session session=null;
		boolean flag=false;
		String query="from AttendanceCondonation ac where ac.student.id=:studId and ac.classes.id=:classId";
		try {
			session=HibernateUtil.getSession();
			q1=session.createQuery(query);
			q1.setParameter("studId", studentId);
			q1.setParameter("classId", classId);
			attCondonation=(AttendanceCondonation)q1.uniqueResult();
			if(attCondonation!=null){
				flag=true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
		
		
	}
	@Override
	public List getQueryForAlreadyEnteredMarksForAllInternal(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			String query="from MarksEntryDetails m" +
			" where m.subject.id=" +newExamMarksEntryForm.getSubjectId()+
//			" and m.marksEntry.classes.course.id= " +newExamMarksEntryForm.getCourseId()+
			" and m.marksEntry.exam.id in(:examIds)";
		
		if(newExamMarksEntryForm.getSubjectType()!=null && !newExamMarksEntryForm.getSubjectType().isEmpty()){
			if(newExamMarksEntryForm.getSubjectType().equals("1")){
				query=query+" and m.theoryMarks is not null ";
			}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
				query=query+" and m.practicalMarks is not null ";
			}else{
				query=query+" and m.theoryMarks is not null and m.practicalMarks is not null ";
			}
		}
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesQuery.setParameterList("examIds", newExamMarksEntryForm.getExamNameList().keySet());
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
	@Override
	public Map<Integer,Double> getMaxMarkOfSubjectForAllInternals(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		Session session = null;
		Double maxMarks=null;
		String subType="";
		if(newExamMarksEntryForm.getSubjectType()!=null){
			if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
				subType="t";
			}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
				subType="p";
			}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("11")){
				subType="t";// Still its not completed
			}
		}
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		Map<Integer, Double> allmap = new HashMap<Integer, Double>();
		try{
			session = HibernateUtil.getSession();
			
			Set<Integer> examIds=newExamMarksEntryForm.getExamNameList().keySet();
			Iterator<Integer> itrexam=examIds.iterator();
			while (itrexam.hasNext()) {
			Integer examId=itrexam.next();
			String query="select internal.enteredMaxMark," +
					" s.theoryEseEnteredMaxMark, s.practicalEseEnteredMaxMark, ansScript.value, s.academicYear from SubjectRuleSettings s" +
					" left join s.examSubjectRuleSettingsSubInternals internal" +
					" left join s.examSubjectRuleSettingsMulEvaluators eval" +
					" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " +
					" where s.course.id="+newExamMarksEntryForm.getCourseId()+
					" and s.schemeNo=" +newExamMarksEntryForm.getSchemeNo()+
					" and s.subject.id=" +newExamMarksEntryForm.getSubjectId();
			/*if(newExamMarksEntryForm.getEvaluatorType()!=null && !newExamMarksEntryForm.getEvaluatorType().isEmpty()){
				query=query+" and eval.evaluatorId="+newExamMarksEntryForm.getEvaluatorType()+
				" and eval.isTheoryPractical='"+subType+"'";
			}*/
			/*if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
				query=query+" and ansScript.multipleAnswerScriptId="+newExamMarksEntryForm.getAnswerScriptType()+
				" and ansScript.isTheoryPractical='"+subType+"'";
			}*/	
			if(newExamMarksEntryForm.getExamType().equals("Internal")){
				query=query+" and internal.internalExamTypeId=(select e.internalExamType.id" +
						" from ExamDefinition e where e.id="+examId+
				") and internal.isTheoryPractical='"+subType+"'";
			}
			/*if(newExamMarksEntryForm.getExamType().equals("Supplementary")){
				query=query+" and s.academicYear>=(select e.examForJoiningBatch from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+") ";
			}else{
				query=query+" and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+") ";
			}*/
			query=query+" and s.isActive=1 group by s.id";
			List<Object[]> list=session.createQuery(query).list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(newExamMarksEntryForm.getExamType().equals("Internal")){
						if(objects[0]!=null){
							maxMarks=new Double(objects[0].toString());
							if(objects[4]!=null && objects[4].toString()!=null)
								map.put(Integer.parseInt(objects[4].toString()), maxMarks);
								allmap.put(examId, maxMarks);
						}
					}/*else{
						if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
							if(objects[3]!=null){
								maxMarks=new Double(objects[3].toString());
								if(objects[4]!=null && objects[4].toString()!=null)
									map.put(Integer.parseInt(objects[4].toString()), maxMarks);
							}
						}else{
							if(newExamMarksEntryForm.getSubjectType().equals("1")){
								if(objects[1]!=null){
									maxMarks=new Double(objects[1].toString());
									if(objects[4]!=null && objects[4].toString()!=null)
										map.put(Integer.parseInt(objects[4].toString()), maxMarks);
								}
							}else if(newExamMarksEntryForm.getSubjectType().equals("0")){
								if(objects[2]!=null){
									maxMarks=new Double(objects[2].toString());
									if(objects[4]!=null && objects[4].toString()!=null)
										map.put(Integer.parseInt(objects[4].toString()), maxMarks);
								}
							}else{
								if(objects[1]!=null){
									maxMarks=new Double(objects[1].toString());
									if(objects[4]!=null && objects[4].toString()!=null)
										map.put(Integer.parseInt(objects[4].toString()), maxMarks);
								}
							}
						}
					}*/
				}
			}
			newExamMarksEntryForm.setMaxMarksMap(map);
			
			}
			
			
		} catch (Exception e) {
			log.error("Error while retrieving ExamAbscentCode.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
		return allmap;
	}
	@Override
	public boolean isCurrentDateValidForAllInternalMarks(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception {
		Session session = null;
		try {
			String date = CommonUtil.getTodayDate();
			Date curDate = CommonUtil.ConvertStringToSQLDate(date);
			session = HibernateUtil.getSession();
			String queryString = "from ExamPublishHallTicketMarksCardBO e where e.isActive=1 and e.classUtilBO.courseId="+newExamMarksEntryForm.getCourseId()+" and e.classUtilBO.termNumber="+newExamMarksEntryForm.getSchemeNo()+
			" and e.examId ="+newExamMarksEntryForm.getExamid()+" and  e.publishFor = 'Internal Marks Entry' and '"+curDate+"' between e.downloadStartDate and e.downloadEndDate";  
			
			Query query = session.createQuery(queryString);
			//query.setParameterList("examIds", newExamMarksEntryForm.getExamNameList().keySet());		
			List<ExamPublishHallTicketMarksCardBO> publishList = query.list();
			
			if(publishList!= null && publishList.size() > 0){
				return true;
			}
			else{
				return false;
			}
			
			
		} catch (Exception e) {
			log.error("Error in isCurrentDateValidForAllInternalMarks..." ,e);
			session.close();
			throw new ApplicationException(e);
		}
	}
	@Override
	public boolean saveMarksForAllInternals(NewExamMarksEntryForm newExamMarksEntryForm)
			throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		List<StudentTO> allstudentlist=newExamMarksEntryForm.getStudentMarksList();
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<StudentTO> stuiter=allstudentlist.iterator();
			while (stuiter.hasNext()) {
				StudentTO studentTO = stuiter.next();
					
			List<StudentMarksTO> list=studentTO.getStudentMarksList();
			Iterator<StudentMarksTO> itr=list.iterator();
			while (itr.hasNext()) {
				StudentMarksTO to = (StudentMarksTO) itr.next();
				if(to.getId()>0){
					
					if((to.getIsPractical() && !to.getPracticalMarks().equals(to.getOldPracticalMarks())) || (to.getIsTheory() && !to.getTheoryMarks().equals(to.getOldTheoryMarks()))){
						MarksEntryDetails detailBo=(MarksEntryDetails)session.get(MarksEntryDetails.class, to.getId());
						if(to.getIsPractical()){
							if(CMSConstants.INTERNAL_EXAM_IDS.contains(Integer.parseInt(to.getExamId())) 
									&& to.getPracticalMarks()!=null && !StringUtils.isAlpha(to.getPracticalMarks())){
								String val=to.getPracticalMarks();
								double r=(Double.parseDouble(val)*10)/to.getMaxMarks();
								detailBo.setPracticalMarks(String.valueOf(CommonUtil.Round(r, 2)));
							}else{
								detailBo.setPracticalMarks(to.getPracticalMarks().trim());
							}
						}
						if(to.getIsTheory()){
							if(CMSConstants.INTERNAL_EXAM_IDS.contains(Integer.parseInt(to.getExamId()))
									&& to.getTheoryMarks()!=null && !StringUtils.isAlpha(to.getTheoryMarks())){
								String val=to.getTheoryMarks();
								double r=(Double.parseDouble(val)*10)/to.getMaxMarks();
								detailBo.setTheoryMarks(String.valueOf(CommonUtil.Round(r, 2)));
							}else{
								detailBo.setTheoryMarks(to.getTheoryMarks().trim());
							}
						}
						detailBo.setModifiedBy(newExamMarksEntryForm.getUserId());
						detailBo.setLastModifiedDate(new Date());
						session.update(detailBo);
					}
				}else{
					if((to.getIsTheory() && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()) || (to.getIsPractical() && to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty())){
						MarksEntry marksEntry=null;
						String query="from MarksEntry m where m.exam.id="+to.getExamId()+" and m.student.id="+to.getStudentId()+" and m.classes.id="+to.getClassId();
						if(newExamMarksEntryForm.getEvaluatorType()!=null && !newExamMarksEntryForm.getEvaluatorType().isEmpty())
							query=query+" and m.evaluatorType="+newExamMarksEntryForm.getEvaluatorType();
						else
							query=query+" and m.evaluatorType is null";
						if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty())
							query=query+" and m.answerScript="+newExamMarksEntryForm.getAnswerScriptType();
						else
							query=query+" and m.answerScript is null";
						List<MarksEntry> marksEntrys=session.createQuery(query).list();
						if(marksEntrys==null || marksEntrys.isEmpty()){
							marksEntry=new MarksEntry();
							Student student=new Student();
							student.setId(to.getStudentId());
							marksEntry.setStudent(student);
							ExamDefinitionBO exam=new ExamDefinitionBO();
							exam.setId(Integer.parseInt(to.getExamId()));
							marksEntry.setExam(exam);
							if(newExamMarksEntryForm.getEvaluatorType()!=null && !newExamMarksEntryForm.getEvaluatorType().isEmpty()){
								marksEntry.setEvaluatorType(Integer.parseInt(newExamMarksEntryForm.getEvaluatorType()));
							}
							if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
								marksEntry.setAnswerScript(Integer.parseInt(newExamMarksEntryForm.getAnswerScriptType()));
							}
							Classes classes=new Classes();
							classes.setId(to.getClassId());
							marksEntry.setClasses(classes);
							marksEntry.setCreatedBy(newExamMarksEntryForm.getUserId());
							marksEntry.setCreatedDate(new Date());
							marksEntry.setModifiedBy(newExamMarksEntryForm.getUserId());
							marksEntry.setLastModifiedDate(new Date());
							Set<MarksEntryDetails> marksEntryDetails=new HashSet<MarksEntryDetails>();
							
							MarksEntryDetails detail=new MarksEntryDetails();
							Subject subject=new Subject();
							subject.setId(Integer.parseInt(newExamMarksEntryForm.getSubjectId()));
							detail.setSubject(subject);
							if(to.getIsTheory()){
								if(CMSConstants.INTERNAL_EXAM_IDS.contains(Integer.parseInt(to.getExamId()))
										&& to.getTheoryMarks()!=null && !StringUtils.isAlpha(to.getTheoryMarks())){
									String val=to.getTheoryMarks();
									double r=(Double.parseDouble(val)*10)/to.getMaxMarks();
									detail.setTheoryMarks(String.valueOf(CommonUtil.Round(r, 2)));
								}else{
									detail.setTheoryMarks(to.getTheoryMarks().trim());
								}
								detail.setIsTheorySecured(false);
							}
							if(to.getIsPractical()){
								if(CMSConstants.INTERNAL_EXAM_IDS.contains(Integer.parseInt(to.getExamId()))
										&& to.getPracticalMarks()!=null && !StringUtils.isAlpha(to.getPracticalMarks())){
									String val=to.getPracticalMarks();
									double r=(Double.parseDouble(val)*10)/to.getMaxMarks();
									detail.setPracticalMarks(String.valueOf(CommonUtil.Round(r, 2)));
								}else{
									detail.setPracticalMarks(to.getPracticalMarks().trim());
								}
								detail.setIsPracticalSecured(false);
							}
							detail.setCreatedBy(newExamMarksEntryForm.getUserId());
							detail.setCreatedDate(new Date());
							detail.setModifiedBy(newExamMarksEntryForm.getUserId());
							detail.setLastModifiedDate(new Date());
							//code added by chandra
							detail.setIsGracing(false);
							//
							marksEntryDetails.add(detail);
							marksEntry.setMarksDetails(marksEntryDetails);
							session.save(marksEntry);
						}else{
							Iterator<MarksEntry> marksitr=marksEntrys.iterator();
							if (marksitr.hasNext()) {
								 marksEntry = (MarksEntry) marksitr.next();
							}
							MarksEntryDetails detail=(MarksEntryDetails)session.createQuery("from MarksEntryDetails m where m.marksEntry.id="+marksEntry.getId()+"" +
									" and m.subject.id="+newExamMarksEntryForm.getSubjectId()).uniqueResult();
							if(detail==null){
								detail=new MarksEntryDetails();
								Subject subject=new Subject();
								subject.setId(Integer.parseInt(newExamMarksEntryForm.getSubjectId()));
								detail.setSubject(subject);
								detail.setCreatedBy(newExamMarksEntryForm.getUserId());
								detail.setCreatedDate(new Date());
								//code added by chandra
								detail.setIsGracing(false);
								//
							}
							detail.setMarksEntry(marksEntry);
							if(to.getIsTheory()){
								if(CMSConstants.INTERNAL_EXAM_IDS.contains(Integer.parseInt(to.getExamId()))
										&& to.getTheoryMarks()!=null && !StringUtils.isAlpha(to.getTheoryMarks())){
									String val=to.getTheoryMarks();
									double r=(Double.parseDouble(val)*10)/to.getMaxMarks();
									detail.setTheoryMarks(String.valueOf(CommonUtil.Round(r, 2)));
								}else{
									detail.setTheoryMarks(to.getTheoryMarks().trim());
								}
								detail.setIsTheorySecured(false);
							}
							if(to.getIsPractical()){
								if(CMSConstants.INTERNAL_EXAM_IDS.contains(Integer.parseInt(to.getExamId()))
										&& to.getPracticalMarks()!=null && !StringUtils.isAlpha(to.getPracticalMarks())){
									String val=to.getPracticalMarks();
									double r=(Double.parseDouble(val)*10)/to.getMaxMarks();
									detail.setPracticalMarks(String.valueOf(CommonUtil.Round(r, 2)));
								}else{
									detail.setPracticalMarks(to.getPracticalMarks().trim());
								}
								detail.setIsPracticalSecured(false);
							}
							detail.setModifiedBy(newExamMarksEntryForm.getUserId());
							detail.setLastModifiedDate(new Date());
							marksEntry.setModifiedBy(newExamMarksEntryForm.getUserId());
							marksEntry.setLastModifiedDate(new Date());
							session.saveOrUpdate(detail);
							session.update(marksEntry);
						}
					}
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}//sub close
			
			}//main close
			
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
	public List getAttendanceMarks(NewExamMarksEntryForm newExamMarksEntryForm) throws ApplicationException {
		Session session = null;
		List<ExamStudentOverallInternalMarkDetailsBO> list = null;
		try {
			session = HibernateUtil.getSession();
			String query="from ExamStudentOverallInternalMarkDetailsBO m" +
			" where m.subject.id=" +newExamMarksEntryForm.getSubjectId()+
			" and m.examId in(:examIds) and m.subject.subjectTypeId="+newExamMarksEntryForm.getSubjectType();
		
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesQuery.setParameterList("examIds", newExamMarksEntryForm.getExamNameList().keySet());
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

	
}