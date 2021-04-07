package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSgpa;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.bo.exam.StudentsImprovementExamDetailsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.PublishSupplementaryImpApplicationForm;
import com.kp.cms.forms.exam.StudentsImprovementExamDetailsForm;
import com.kp.cms.transactions.exam.IStudentsImprovementExamDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class StudentsImprovementExamDetailsTransactionsImpl implements IStudentsImprovementExamDetailsTransaction {

	private static volatile StudentsImprovementExamDetailsTransactionsImpl impl = null;
	private static final Log log = LogFactory.getLog(StudentsImprovementExamDetailsTransactionsImpl.class);
	private StudentsImprovementExamDetailsTransactionsImpl() {

	}
	public static StudentsImprovementExamDetailsTransactionsImpl getInstance() {
		if (impl == null) {
			impl = new StudentsImprovementExamDetailsTransactionsImpl();
		}
		return impl;
	}

	public List<StudentSupplementaryImprovementApplication> loadClassByExamNameAndYear(
			StudentsImprovementExamDetailsForm actionForm) throws Exception {

		Session session=null;
		Transaction transaction=null;
		List list=null;
		try{
			session=InitSessionFactory.getInstance().openSession();
			String str="select supp.classes.id,supp.classes.name," +
					"sche.curriculumSchemeDuration.curriculumScheme.year" +
					" from StudentSupplementaryImprovementApplication supp" +
					" join supp.classes.classSchemewises sche" +
					" where supp.examDefinition.academicYear="+actionForm.getYear()+" and supp.examDefinition.id="+actionForm.getExamId();
			list=session.createQuery(str).list();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during deleting ValuatorCharges data...", e);
			throw  new ApplicationException(e);
		}
		return list;
	}
	
	public List<ExamStudentFinalMarkDetailsBO> getStudentsImpExamDetails(
			StudentsImprovementExamDetailsForm actionForm) throws Exception {

		Session session=null;
		Transaction transaction=null;
		List<ExamStudentFinalMarkDetailsBO> marksBolist=null;
		List<ExamStudentFinalMarkDetailsBO> marksBolistAll=new ArrayList<ExamStudentFinalMarkDetailsBO>();
		List<StudentSupplementaryImprovementApplication> stuList =null;
		StudentSupplementaryImprovementApplication bo = null;
		List<Integer> subjectIds = new ArrayList<Integer>();
		Map<Integer, List<Integer>> stuMap = new HashMap<Integer, List<Integer>>();
		
		try{
			String stuQuery = " from StudentSupplementaryImprovementApplication s " +
          " where s.isImprovement=1 and (isAppearedTheory=1 or isAppearedPractical=1)  "+
          " and s.classes.id=" +Integer.parseInt(actionForm.getClassCodeIdsFrom())  ;
			session=InitSessionFactory.getInstance().openSession();
			
			stuList=(List<StudentSupplementaryImprovementApplication>)session.createQuery(stuQuery).list();
			for(int i =0 ; i<stuList.size(); i++){
				bo = stuList.get(i);
				if(stuMap.containsKey(bo.getStudent().getId())){
					List<Integer> subjectIdsExisting = stuMap.get(bo.getStudent().getId());
					subjectIdsExisting.add(bo.getSubject().getId());
					stuMap.put(bo.getStudent().getId(), subjectIdsExisting);
				}
				else{
					subjectIds = new ArrayList<Integer>();
					subjectIds.add(bo.getSubject().getId());
					stuMap.put(bo.getStudent().getId(), subjectIds);
				}
			}
			
			Map<Integer, List<Integer>> stuMapItr = new HashMap<Integer, List<Integer>>();
			Iterator<Entry<Integer, List<Integer>>> entries = stuMap.entrySet().iterator();
			List<Integer> subIds = new ArrayList<Integer>();
			while (entries.hasNext()) {
			    Map.Entry<Integer,  List<Integer>> entry = entries.next();
			    int stuId = entry.getKey();
			    //if(stuId==1735){
			    subIds = entry.getValue();
			    if( subIds !=null && subIds.size()>0){
					String newQuery = "from ExamStudentFinalMarkDetailsBO e where e.examDefinitionBO.examTypeUtilBO.id=6 and e.studentId = "+stuId+" and e.subjectId in (:subIds) and e.classUtilBO.id="+Integer.parseInt(actionForm.getClassCodeIdsFrom());
					marksBolist = session.createQuery(newQuery).setParameterList("subIds", subIds).list();
			    }
			    if(marksBolist.size()>0)
				marksBolistAll.addAll(marksBolist);
			   // }
			}
			
		
		/*	
			String str="from ExamStudentFinalMarkDetailsBO e where e.studentUtilBO.id in(select s.student.id from StudentSupplementaryImprovementApplication s where s.isImprovement=1 and (isAppearedTheory=1 or isAppearedPractical=1) ) and e.examDefinitionBO.examTypeUtilBO.id=6 and e.classUtilBO.id="+Integer.parseInt(actionForm.getClassCodeIdsFrom()) ;
			marksBolist=(List<ExamStudentFinalMarkDetailsBO>)session.createQuery(str).list();
			*/
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during deleting ValuatorCharges data...", e);
			throw  new ApplicationException(e);
		}
		return marksBolistAll;
	}
	
	
	public List<ExamStudentFinalMarkDetailsBO> getStudentsRegularExamMarks(
			StudentsImprovementExamDetailsForm actionForm) throws Exception {

		Session session=null;
		Transaction transaction=null;
		List<ExamStudentFinalMarkDetailsBO> marksBolist=null;
		
		try{
			session=InitSessionFactory.getInstance().openSession();
			String str="from ExamStudentFinalMarkDetailsBO e where e.examDefinitionBO.examTypeUtilBO.id=1 and e.classUtilBO.id="+Integer.parseInt(actionForm.getClassCodeIdsFrom()) ;
			marksBolist=(List<ExamStudentFinalMarkDetailsBO>)session.createQuery(str).list();
			
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during deleting ValuatorCharges data...", e);
			throw  new ApplicationException(e);
		}
		return marksBolist;
	}
	
	public boolean saveStudentsImprovementExamMarksFlag(List<StudentsImprovementExamDetailsBO> boList) throws Exception {
		log.info("Inside updateSgpa");
		StudentsImprovementExamDetailsBO bo;
		Transaction tx=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Iterator<StudentsImprovementExamDetailsBO> itr = boList.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				bo = itr.next();
				session.save(bo);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			log.info("End of updateSgpa");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			session.flush();
			session.clear();
			log.error("Error occured in updateSgpa");
			throw new ApplicationException(e);
		}
	}
	
	public boolean deleleteAlreadyExistedRecords(int classId)throws Exception {
		log.debug("inside deleleAlreadyExistedRecords");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx= session.beginTransaction();
			String sqlQuey = " delete from students_improvement_exam_detail " +
			" where class_id = "  + classId  ;
			Query query = session.createSQLQuery(sqlQuey);

			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();

			log.debug("leaving deleleAlreadyExistedRecords");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error while deleting"	, e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error while deleting" + e);
			throw new ApplicationException(e);
		}
	}
	
}
