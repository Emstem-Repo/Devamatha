package com.kp.cms.transactionsimpl.admin;

import com.kp.cms.transactions.admin.ISendBulkSmsToStudentParents;
import com.kp.cms.bo.admin.Student;
import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import com.kp.cms.utilities.HibernateUtil;

public class SendBulkSmsToStudentParentsImpl implements ISendBulkSmsToStudentParents{

	
	//this is for getting bulk students sms
	public List<Student> getStudents(int year, int programId,Set<Integer> tempcourseset)
	throws Exception {
		Session session = null;
		List<Student> stList;
		try{
			StringBuffer quer=new StringBuffer("from Student st where  st.isAdmitted=1  and st.admAppln.isCancelled=0 and " +
					" st.id not in (select s.student.id from ExamStudentDetentionRejoinDetails s "+
			        " where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)) " +
					" and st.admAppln.courseBySelectedCourseId.program.id=" +programId +" and st.admAppln.appliedYear="+year);
	 if(tempcourseset!=null && !tempcourseset.isEmpty()){
		 quer.append(" and st.admAppln.courseBySelectedCourseId.id in (:courselist)");
	 }
	 quer.append(" order by st.admAppln.courseBySelectedCourseId.id");
	 session = HibernateUtil.getSession();
	 Query query=session.createQuery(quer.toString());
	 if(tempcourseset!=null && !tempcourseset.isEmpty()){
		 query.setParameterList("courselist", tempcourseset);
	 }
	 stList=query.list();
	 
	}catch(Exception e){
			throw e;
	}
	
	return stList;
	
	}
	
	public List<Student> getAllStudents(int accYear, int programTypeId)
	throws Exception {
		Session session = null;
		List<Student> stList;
		try{
			StringBuffer quer=new StringBuffer("from Student st where  st.isAdmitted=1  and st.admAppln.isCancelled=0 and " +
					" st.id not in (select s.student.id from ExamStudentDetentionRejoinDetails s "+
					" where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)) " +
					" and st.classSchemewise.classes.course.program.programType.id="+programTypeId+" and st.classSchemewise.curriculumSchemeDuration.academicYear="+accYear);
			quer.append(" order by st.admAppln.courseBySelectedCourseId.id");
			session = HibernateUtil.getSession();
			Query query=session.createQuery(quer.toString());

			stList=query.list();

		}catch(Exception e){
			throw e;
		}

		return stList;

	}
	
}
