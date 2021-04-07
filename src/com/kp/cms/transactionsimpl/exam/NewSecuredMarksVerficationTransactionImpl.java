package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.kp.cms.forms.exam.NewSecuredMarksVerficationForm;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewSecuredMarksVerficationTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class NewSecuredMarksVerficationTransactionImpl implements
		INewSecuredMarksVerficationTransaction {
	/**
	 * Singleton object of NewSecuredMarksVerficationTransactionImpl
	 */
	private static volatile NewSecuredMarksVerficationTransactionImpl newSecuredMarksVerficationTransactionImpl = null;
	private static final Log log = LogFactory.getLog(NewSecuredMarksVerficationTransactionImpl.class);
	private NewSecuredMarksVerficationTransactionImpl() {
		
	}
	/**
	 * return singleton object of NewSecuredMarksVerficationTransactionImpl.
	 * @return
	 */
	public static NewSecuredMarksVerficationTransactionImpl getInstance() {
		if (newSecuredMarksVerficationTransactionImpl == null) {
			newSecuredMarksVerficationTransactionImpl = new NewSecuredMarksVerficationTransactionImpl();
		}
		return newSecuredMarksVerficationTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewSecuredMarksVerficationTransaction#getMaxMarkOfSubject(com.kp.cms.forms.exam.NewSecuredMarksVerficationForm)
	 */
	@Override
	public Double getMaxMarkOfSubject(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm)
			throws Exception {
		Session session = null;
		Double maxMarks=null;
		try{
			session = HibernateUtil.getSession();
			String query="select internal.enteredMaxMark," +
					" s.theoryEseEnteredMaxMark, s.practicalEseEnteredMaxMark,ansScript.value from SubjectRuleSettings s" +
					" left join s.examSubjectRuleSettingsSubInternals internal" +
					" left join s.examSubjectRuleSettingsMulEvaluators eval" +
					" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " +
					" where s.course.id="+newSecuredMarksVerficationForm.getCourse()+
					" and s.academicYear="+newSecuredMarksVerficationForm.getYear()+
					" and s.schemeNo=" +newSecuredMarksVerficationForm.getSchemeNo()+
					" and s.subject.id=" +newSecuredMarksVerficationForm.getSubjectId();
			if(newSecuredMarksVerficationForm.getEvaluatorType()!=null && !newSecuredMarksVerficationForm.getEvaluatorType().isEmpty()){
				query=query+" and eval.evaluatorId="+newSecuredMarksVerficationForm.getEvaluatorType()+
				" and eval.isTheoryPractical='"+newSecuredMarksVerficationForm.getSubjectType()+"'";
			}
			if(newSecuredMarksVerficationForm.getAnswerScriptType()!=null && !newSecuredMarksVerficationForm.getAnswerScriptType().isEmpty()){
				query=query+" and ansScript.multipleAnswerScriptId="+newSecuredMarksVerficationForm.getAnswerScriptType()+
				" and ansScript.isTheoryPractical='"+newSecuredMarksVerficationForm.getSubjectType()+"'";
			}	
			if(newSecuredMarksVerficationForm.getExamType().equals("Internal")){
				query=query+" and internal.internalExamTypeId=(select e.internalExamType.id" +
						" from ExamDefinition e where e.id="+newSecuredMarksVerficationForm.getExamId()+
				") and internal.isTheoryPractical='"+newSecuredMarksVerficationForm.getSubjectType()+"'";
			}
			query=query+" and s.isActive=1 group by s.id";
			List<Object[]> list=session.createQuery(query).list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(newSecuredMarksVerficationForm.getExamType().equals("Internal")){
						if(objects[0]!=null)
							maxMarks=new Double(objects[0].toString());
					}else{
						if(newSecuredMarksVerficationForm.getAnswerScriptType()!=null && !newSecuredMarksVerficationForm.getAnswerScriptType().isEmpty()){
							if(objects[3]!=null)
								maxMarks=new Double(objects[3].toString());
						}else{
							if(newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("T") && objects[1]!=null)
								maxMarks=new Double(objects[1].toString());
							if(newSecuredMarksVerficationForm.getSubjectType().equalsIgnoreCase("P") && objects[2]!=null)
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
	@Override
	public boolean saveMarks(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		List<StudentMarksTO> list=newSecuredMarksVerficationForm.getMainList();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<StudentMarksTO> itr=list.iterator();
			while (itr.hasNext()) {
				StudentMarksTO to = (StudentMarksTO) itr.next();
				if(to.getId()>0){
						MarksEntryDetails detailBo=(MarksEntryDetails)session.get(MarksEntryDetails.class, to.getId());
						detailBo.setModifiedBy(newSecuredMarksVerficationForm.getUserId());
						detailBo.setLastModifiedDate(new Date());
						if(to.getMistake()!=null && to.getMistake().equalsIgnoreCase("on"))
							detailBo.setIsMistake(true);
						else
							detailBo.setIsMistake(false);
						
						session.update(detailBo);
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
}
