package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ChangeSubjectForm;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.to.exam.RevaluationOrRetotallingMarksEntryTo;
import com.kp.cms.transactions.exam.IRevaluationOrRetotallingMarksEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;



public class RevaluationOrRetotallingMarksEntryTransactionImpl implements IRevaluationOrRetotallingMarksEntryTransaction{
	private static volatile RevaluationOrRetotallingMarksEntryTransactionImpl revaluationOrRetotallingMarksEntryTransactionImpl = null;
	private static final Log log = LogFactory.getLog(RevaluationOrRetotallingMarksEntryTransactionImpl.class);
	
	/**
	 * @return
	 */
	public static RevaluationOrRetotallingMarksEntryTransactionImpl getInstance() {
		if (revaluationOrRetotallingMarksEntryTransactionImpl == null) {
			revaluationOrRetotallingMarksEntryTransactionImpl = new RevaluationOrRetotallingMarksEntryTransactionImpl();
		}
		return revaluationOrRetotallingMarksEntryTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IRevaluationOrRetotallingMarksEntryTransaction#getStudentDetailsList(com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm)
	 */
	public List<ExamRevaluationApplication> getStudentDetailsList(RevaluationOrRetotallingMarksEntryForm form) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery(" select e from ExamRevaluationApplication e "+
																"where e.isActive=1 and e.student.id="+form.getStudentId()+"and e.exam.id="+form.getExamId());
			 List<ExamRevaluationApplication>  List = query.list();
			return List;
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
 * @see com.kp.cms.transactions.exam.IRevaluationOrRetotallingMarksEntryTransaction#saveRevaluationOrRetotalingStudentRecords(java.util.List, com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm)
 */
public boolean saveRevaluationOrRetotalingStudentRecords(List<RevaluationOrRetotallingMarksEntryTo> toList,RevaluationOrRetotallingMarksEntryForm form) throws Exception{
		Transaction tx=null;
		Session session = null;
		try {
			boolean isSaved=false;
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			 
			if(toList!=null && !toList.isEmpty()){
				Iterator itr=toList.iterator();
				while(itr.hasNext()){
					RevaluationOrRetotallingMarksEntryTo to=(RevaluationOrRetotallingMarksEntryTo)itr.next();
					Query query=session.createQuery("select a from ExamRevaluationAppDetails a  where a.isActive=1 and a.id="+to.getId());
					ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query.uniqueResult();
					if(bo !=null){
					bo.setMarks(to.getMarks());
					bo.setMark1(to.getMarks1());
					bo.setMark2(to.getMarks2());
					bo.setLastModifiedDate(new Date());
					bo.setModifiedBy(form.getUserId());
					//new
					bo.setMark3(to.getMarks3());
					session.update(bo);
					isSaved=true;
							}
						}
					}
			  
			tx.commit();
			session.close();
			return isSaved;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
				throw new ApplicationException(e);
		}
	}
	
public Double getMaxMarkOfSubject(RevaluationOrRetotallingMarksEntryForm form,int subjectId) throws Exception {
	Session session = null;
	Double maxMarks=null;
	String subType="";
	Map<Integer, Double> map = new HashMap<Integer, Double>();
	try{
		session = HibernateUtil.getSession();
		String query="select s.theoryEseEnteredMaxMark, s.academicYear from SubjectRuleSettings s" +
				" where s.course.id="+form.getCourseid()+
				" and s.schemeNo=" +form.getSchemeno()+
				" and s.subject.id=" +subjectId+
				"and s.academicYear="+form.getSelectYear()+"and s.isActive=1";
		/*if(form.getExamType().equals("Supplementary")){
			query=query+" and s.academicYear>=(select e.examForJoiningBatch from ExamDefinitionBO e where e.id="+form.getExamId()+") ";
		}else{
			query=query+" and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+form.getExamId()+") ";
		}*/
		
		List<Object[]> list=session.createQuery(query).list();
		if(list!=null && !list.isEmpty()){
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null){
						maxMarks=new Double(objects[0].toString());
						if(objects[1]!=null && objects[1].toString()!=null)
							map.put(Integer.parseInt(objects[1].toString()), maxMarks);
					}
				}
			}
		form.setMaxMarksMap(map);
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

public boolean getEvaluatorType(int subjectId,RevaluationOrRetotallingMarksEntryForm form) throws Exception{
	Transaction tx=null;
	Session session = null;
	boolean isMultipleEvaluators=false;
	try {
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
				Query query=session.createQuery("select s.theoryEseIsMultipleEvaluator from SubjectRuleSettings s" +
				" where s.course.id="+form.getCourseid()+
				" and s.schemeNo=" +form.getSchemeno()+
				" and s.subject.id=" +subjectId+
				"and s.academicYear="+form.getSelectYear()+"and s.isActive=1");
				boolean bo= (Boolean)query.uniqueResult();
		  if(bo==true){
			  isMultipleEvaluators=true;
		  }
		
		return isMultipleEvaluators;
	} catch (Exception e) {
			throw new ApplicationException(e);
		}
	}

//new
public List getEvaluatorTypes(ExamRevaluationAppDetails ed) throws Exception{
	Transaction tx=null;
	Session session = null;
	List list = null;
	try {
		session = HibernateUtil.getSession();
		Query query=session.createQuery("select max(e.evaluatorType) from MarksEntry e inner join e.marksDetails em " +
				" where e.student.id ="+ed.getExamRevApp().getStudent().getId()+
				" and e.exam.id="+ed.getExamRevApp().getExam().getId()+
				" and em.subject.id="+ed.getSubject().getId()+
				" and e.classes.id="+ed.getExamRevApp().getClasses().getId());
		list = query.list();
		return list;
	
	} catch (Exception e) {
			throw new ApplicationException(e);
		}
	}


}
