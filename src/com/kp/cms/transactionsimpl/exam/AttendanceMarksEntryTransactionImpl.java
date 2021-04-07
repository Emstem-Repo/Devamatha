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
import org.hibernate.mapping.Array;
import org.zefer.html.doc.q;

import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamSubCoursewiseAttendanceMarksBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.exam.IAttendanceMarksEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class AttendanceMarksEntryTransactionImpl implements IAttendanceMarksEntryTransaction {
	private static volatile AttendanceMarksEntryTransactionImpl attendanceMarksEntryTransactionImpl= null;
	private static final Log log = LogFactory.getLog(AttendanceMarksEntryTransactionImpl.class);
	public static AttendanceMarksEntryTransactionImpl getInstance()
	{
		if(attendanceMarksEntryTransactionImpl== null)
		{
			AttendanceMarksEntryTransactionImpl attendanceMarksEntryTransactionImpl = new AttendanceMarksEntryTransactionImpl();
			return attendanceMarksEntryTransactionImpl; 
		}
		return attendanceMarksEntryTransactionImpl;
	}
	@Override
	public List<ExamCourseUtilBO> getCourseList() throws Exception {
        log.debug("call of getCourseList method in   AttendanceMarksEntryTransactionImpl.class");
        List<ExamCourseUtilBO> courseList = new ArrayList<ExamCourseUtilBO>();
        Session session = null;
        try
        {  
        	session = HibernateUtil.getSession();
			String HQL = "from ExamCourseUtilBO bo where bo.isActive = 1 and bo.program.isActive=1 group by bo.program.programType,bo.program.programName,bo.courseName"
					+ " order by bo.program.programType desc";
			Query query = session.createQuery(HQL);
			courseList = query.list();
        }
        catch (Exception e) {
        	log.error("Error in getCourseList method in   AttendanceMarksEntryTransactionImpl.class");
        	throw new ApplicationException(e);
			// TODO: handle exception
		}
        finally
        {
        	if(session!=null)
        	{
        		
        	}
        }
        log.debug("end of getCourseList method in   AttendanceMarksEntryTransactionImpl.class");
		return courseList;
	}
	@Override
	public boolean addAttendanceMark(List<ExamSubCoursewiseAttendanceMarksBO> attendaceBoList)throws Exception {
		log.debug("call of addAttendanceMark method in AttendanceMarksEntryTransactionImpl.class");
		boolean isAdded = false;
		Session session= null;
		Transaction transaction = null;
		List<ExamSubCoursewiseAttendanceMarksBO> removeBoList=attendaceBoList;
		
		
		int count=0;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamSubCoursewiseAttendanceMarksBO> iterator = attendaceBoList.iterator();
			while(iterator.hasNext())
			{
				ExamSubCoursewiseAttendanceMarksBO bo = iterator.next();
				session.saveOrUpdate(bo);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
				count = count+1;
			}
			transaction.commit();
			isAdded = true;
		}
		catch (Exception e) {
			log.error("Error in addAttendanceMark method in AttendanceMarksEntryTransactionImpl.class");
			throw new ApplicationException(e);
			
			// TODO: handle exception
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
			}
		}
		log.debug("end of addAttendanceMark method in AttendanceMarksEntryTransactionImpl.class");
		return isAdded;
	}
	@Override
	public int getSchemIDforCourseAndYear(int courseId, int year,int schemeNumber) throws Exception {
	    log.debug("call of getSchemIDforCourseAndYear method in AttendanceMarksEntryTransactionImpl.class");
	    Session session = null;
	    int schemeID = 0;
	    try {
	    	session = HibernateUtil.getSession();
	    	String sql = " select cur.courseSchemeId from com.kp.cms.bo.exam.CurriculumSchemeUtilBO cur   inner join cur.curriculumSchemeDurationUtilBOSet dur  "+
	    	" where cur.courseId = " + courseId+ " and dur.academicYear = "+year+" group by cur.courseSchemeId" ;


	    	Query queri = session.createQuery(sql);
	    	queri.setMaxResults(1);
	    	if(queri.uniqueResult()!=null)
	    	{

	    		schemeID = Integer.parseInt(queri.uniqueResult().toString());
	    	}
	    } catch (Exception e) {
	    	log.error("Error in getClassSchemeForStudent..." + e);

	    } finally {
	    	if (session != null) {
	    		session.close();

	    	}
	    }
	    log.debug("end of getSchemIDforCourseAndYear method in AttendanceMarksEntryTransactionImpl.class");
	    return schemeID;

	}
	@Override
	public List<Object[]> getAttendanceList()throws Exception {
		log.debug("call of getAttendanceList method in AttendanceMarksEntryTransactionImpl.class");
		List<Object[]> attendanceListBos= new ArrayList<Object[]>();
		Session session = null;
		try
		{
			session = InitSessionFactory.getInstance().openSession();
			String sqlQuery = " SELECT EXAM_sub_coursewise_attendance_marks.subject_id as subjectId, "+
							  " count(EXAM_sub_coursewise_attendance_marks.subject_id) as cnt, "+
							  " EXAM_sub_coursewise_attendance_marks.attendance_marks as marks, "+
							  " EXAM_sub_coursewise_attendance_marks.from_prcntg_attndnc as from_perc, "+
							  " EXAM_sub_coursewise_attendance_marks.to_prcntg_attndnc as to_perc,"+
							  " EXAM_sub_coursewise_attendance_marks.course_id as courseId, "+
							  " course.name as courseName "+
							  " FROM   EXAM_sub_coursewise_attendance_marks EXAM_sub_coursewise_attendance_marks "+
							  " INNER JOIN "+
							  " course course "+
							  " ON (EXAM_sub_coursewise_attendance_marks.course_id = course.id) "+

							  " group by EXAM_sub_coursewise_attendance_marks.course_id,EXAM_sub_coursewise_attendance_marks.from_prcntg_attndnc, "+
							  " EXAM_sub_coursewise_attendance_marks.to_prcntg_attndnc,EXAM_sub_coursewise_attendance_marks.attendance_marks";
			Query query = session.createSQLQuery(sqlQuery);
			attendanceListBos = query.list();
		}
		catch (Exception e) {
			log.error("Error in getAttendanceList method in AttendanceMarksEntryTransactionImpl.class");
			throw new ApplicationException(e);
			
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		log.debug("end of getAttendanceList method in AttendanceMarksEntryTransactionImpl.class");
		return attendanceListBos;
	}
	@Override
	public boolean deleteExixtingData(Map<Integer, List<Integer>> deleteExistingMap) throws Exception {
		log.debug("call of deleteExixtingData method in AttendanceMarksEntryTransactionImpl.class");
		boolean isDeleted = false;
		Session session = null;
		Session session1 = null;
		
		Transaction transaction = null;
		try
		{
			
			for(Integer course: deleteExistingMap.keySet())
			{
				int count =0;
				List<ExamSubCoursewiseAttendanceMarksBO>  bos = new ArrayList<ExamSubCoursewiseAttendanceMarksBO>();
				session = HibernateUtil.getSession();
				session1 = HibernateUtil.getSession();
				transaction = session.beginTransaction();
				transaction.begin();
				List<Integer> deleteSubject = deleteExistingMap.get(course);
				if(deleteSubject.size()>0){
					String sql = "select m from ExamSubCoursewiseAttendanceMarksBO m  where m.courseId=:course and m.subjectId in (:subject)";
					Query query = session1.createQuery(sql);
					query.setInteger("course", course);
					query.setParameterList("subject", deleteSubject);
					bos = query.list();
					Iterator<ExamSubCoursewiseAttendanceMarksBO> iterator= bos.iterator();
					while(iterator.hasNext())
					{
						ExamSubCoursewiseAttendanceMarksBO bo = iterator.next();
						session.delete(bo);
						if(++count % 20 == 0){
							session.flush();
							session.clear();
						}
						count=count+1;
					}
					transaction.commit();
					session.flush();
					isDeleted =true;
				}

				
			}
		}
		catch (Exception e) {
			transaction.rollback();
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
		log.debug("call of deleteExixtingData method in AttendanceMarksEntryTransactionImpl.class");
		return isDeleted;
	}

}
