package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamSubCoursewiseAttendanceMarksBO;
import com.kp.cms.bo.exam.ExamSubCoursewiseGradeDefnBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.exam.IGradeClassDefinitionEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class GradeClassDefinitionEntryTransactionImpl implements IGradeClassDefinitionEntryTransaction {
	private static volatile GradeClassDefinitionEntryTransactionImpl gradeClassDefinitionEntryTransactionImpl= null;
	private static final Log log = LogFactory.getLog(GradeClassDefinitionEntryTransactionImpl.class);
	public static GradeClassDefinitionEntryTransactionImpl getInstance()
	{
		if(gradeClassDefinitionEntryTransactionImpl== null)
		{
			GradeClassDefinitionEntryTransactionImpl gradeClassDefinitionEntryTransactionImpl = new GradeClassDefinitionEntryTransactionImpl();
			return gradeClassDefinitionEntryTransactionImpl; 
		}
		return gradeClassDefinitionEntryTransactionImpl;
	}
	@Override
	public boolean addGradeClassBoList(List<ExamSubCoursewiseGradeDefnBO> gradeClassBoList)throws Exception {
		log.debug("call of addGradeClassBoList method in GradeClassDefinitionEntryTransactionImpl.class");
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		int count=0;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamSubCoursewiseGradeDefnBO> iterator = gradeClassBoList.iterator();
			while(iterator.hasNext())
			{
				ExamSubCoursewiseGradeDefnBO defnBO = iterator.next();
				session.saveOrUpdate(defnBO);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
				count= count+1;
			}
			transaction.commit();
			isAdded = true;
		}
		catch (Exception e) {
			transaction.rollback();
			log.error("Error in addGradeClassBoList method in GradeClassDefinitionEntryTransactionImpl.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
				session.close();
			}
		}
		log.debug("end of addGradeClassBoList method in GradeClassDefinitionEntryTransactionImpl.class");
		return isAdded;
	}
	@Override
	public boolean deleteExixtingData(Map<Integer, List<Integer>> deleteExistingMap,int academicYear) throws Exception {
		log.debug("call of deleteExixtingData method in GradeClassDefinitionEntryTransactionImpl.class");
		boolean isDeleted = false;
		Session session = null;
		Session session1 = null;
		
		Transaction transaction = null;
		try
		{
			if(!deleteExistingMap.isEmpty())
			{
				for(Integer course: deleteExistingMap.keySet())
				{
					int count =0;
					List<ExamSubCoursewiseGradeDefnBO>  bos = new ArrayList<ExamSubCoursewiseGradeDefnBO>();
					session = HibernateUtil.getSession();
					session1 = HibernateUtil.getSession();
					transaction = session.beginTransaction();
					transaction.begin();
					List<Integer> deleteSubject = deleteExistingMap.get(course);
					String sql = "select m from ExamSubCoursewiseGradeDefnBO m  where m.courseId=:course and m.academicYear= :academicYear and m.subjectId in (:subject)";
					Query query = session1.createQuery(sql);
					query.setInteger("course", course);
					query.setInteger("academicYear", academicYear);
					query.setParameterList("subject", deleteSubject);
					bos = query.list();
					Iterator<ExamSubCoursewiseGradeDefnBO> iterator= bos.iterator();
					while(iterator.hasNext())
					{
						ExamSubCoursewiseGradeDefnBO bo = iterator.next();
						session.delete(bo);
						if(++count % 20 == 0){
							session.flush();
							session.clear();
						}
						count=count+1;
					}
					transaction.commit();
					session.flush();
				}
				isDeleted =true;
			}
			else
			{
				isDeleted =false;
			}
			
		}
		catch (Exception e) {
			transaction.rollback();
			log.error("Error deleteExixtingData method in GradeClassDefinitionEntryTransactionImpl.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			if(session!=null)
			{
				session1.close();
			}
		}
		log.debug("call of deleteExixtingData method in GradeClassDefinitionEntryTransactionImpl.class");
		return isDeleted;
	}
	@Override
	public List<Object[]> getGradeClassDefinitionList() throws Exception {
		log.debug("call of getGradeClassDefinitionList method in GradeClassDefinitionEntryTransactionImpl.class");
		List<Object[]> boList = new ArrayList<Object[]>();
		Session  session = null;
		try
		{
			session =  InitSessionFactory.getInstance().openSession();
			String sql = " SELECT course.id as courseId, "+
						 " course.name as courseName, "+
						 " EXAM_sub_coursewise_grade_defn.subject_id as subjectId, "+
						 " count(EXAM_sub_coursewise_grade_defn.subject_id) as cnt, "+
						 " EXAM_sub_coursewise_grade_defn.start_prcntg_grade as start_perc, "+
						 " EXAM_sub_coursewise_grade_defn.end_prcntg_grade as end_perc, "+
						 " EXAM_sub_coursewise_grade_defn.grade as grade,"+
						 " EXAM_sub_coursewise_grade_defn.result_class as result, "+
						 " EXAM_sub_coursewise_grade_defn.grade_point as point "+
						 " FROM    EXAM_sub_coursewise_grade_defn EXAM_sub_coursewise_grade_defn "+
						 " INNER JOIN "+
						 " course course "+
						 " ON (EXAM_sub_coursewise_grade_defn.course_id = course.id) "+
						 " group by EXAM_sub_coursewise_grade_defn.course_id,EXAM_sub_coursewise_grade_defn.start_prcntg_grade, "+
						 " EXAM_sub_coursewise_grade_defn.end_prcntg_grade";
			Query query = session.createSQLQuery(sql);
			boList = query.list();
		}
		catch (Exception e) {
			log.error("Error in getGradeClassDefinitionList method in GradeClassDefinitionEntryTransactionImpl.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		log.debug("end of getGradeClassDefinitionList method in GradeClassDefinitionEntryTransactionImpl.class");
		return boList;
	}
	
}
