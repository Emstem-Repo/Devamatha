package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.StudentDetailsReportForm;
import com.kp.cms.transactions.reports.IStudentDetailsReportTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * @author dIlIp
 *
 */
public class StudentDetailsReportTransactionImpl implements IStudentDetailsReportTransaction {
	
	private static final Log log = LogFactory.getLog(StudentDetailsReportTransactionImpl.class);
	public static volatile StudentDetailsReportTransactionImpl studentDetailsReportTransactionImpl = null;

	public static StudentDetailsReportTransactionImpl getInstance() {
		if (studentDetailsReportTransactionImpl == null) {
			studentDetailsReportTransactionImpl = new StudentDetailsReportTransactionImpl();
		}
		return studentDetailsReportTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IStudentDetailsReportTransaction#getSearchedStudents(java.lang.StringBuffer)
	 */
	public List<Student> getSearchedStudents(StringBuffer query) throws Exception {
		Session session = null;
		List<Student> students = null;
		try{
			session=HibernateUtil.getSession();
			Query query1= session.createQuery(query.toString());
			students = query1.list();
			}
		catch (Exception e) {
			throw new ApplicationException(e);
			}
		return students;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IStudentDetailsReportTransaction#getDeanery()
	 */
	public List<String> getDeanery() throws Exception  {
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String query = "select distinct p.stream from Program p where p.isActive=1 and p.stream is not null and p.stream is not ''";
			List<String> program = session.createQuery(query).list();
			return program;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				session.close();
			}	
			throw  new ApplicationException(e);
			
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IStudentDetailsReportTransaction#getExamStudentDetentionRejoinDetails(java.lang.String, int, int, java.lang.String[], int, java.lang.String, int)
	 */
	public List<ExamStudentDetentionRejoinDetails> getExamStudentDetentionRejoinDetails(StudentDetailsReportForm stForm) {
		
		List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetailslist=new ArrayList<ExamStudentDetentionRejoinDetails>();
		Session session=null;
		try{
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			
			StringBuffer stringBuffer = new StringBuffer("from ExamStudentDetentionRejoinDetails ex where ex.classSchemewise.curriculumSchemeDuration.academicYear="+Integer.valueOf(stForm.getAcademicYear()));
			
			if(stForm.getIsCurrentYear().equalsIgnoreCase("previous"))
				stringBuffer = stringBuffer.append(" ");
			
			if (stForm.getProgramTypeId()!=null && !stForm.getProgramTypeId().isEmpty()) 
				stringBuffer = stringBuffer.append(" and ex.student.admAppln.courseBySelectedCourseId.program.programType.id="+Integer.parseInt(stForm.getProgramTypeId()));
			
			if (stForm.getProgramId()!=null && !stForm.getProgramId().isEmpty()) 
				stringBuffer = stringBuffer.append(" and ex.student.admAppln.courseBySelectedCourseId.program.id="+Integer.parseInt(stForm.getProgramId()));
			
			if(stForm.getCourseId()!=null && !stForm.getCourseId().isEmpty())
				stringBuffer = stringBuffer.append(" and ex.student.admAppln.courseBySelectedCourseId.id="+Integer.parseInt(stForm.getCourseId()));
			
			if(stForm.getDeaneryName()!=null && !stForm.getDeaneryName().isEmpty())
				stringBuffer = stringBuffer.append(" and ex.student.admAppln.courseBySelectedCourseId.program.stream like '"+stForm.getDeaneryName()+"'");
			
			if (stForm.getSelectedClass()!=null && !stForm.getSelectedClass().toString().isEmpty()) {
				StringBuffer buff = new StringBuffer();
				for(int i=0;i<stForm.getSelectedClass().length;i++){
					buff = buff.append(stForm.getSelectedClass()[i]);
					if(i<(stForm.getSelectedClass().length-1)){
						buff = buff.append(",");
					}
				}
				stringBuffer = stringBuffer.append(" and ex.classSchemewise.classes.id IN ("+buff+")");
			}
			if(stForm.getStatus()!=null && !stForm.getStatus().isEmpty() && stForm.getStatus().equalsIgnoreCase("Detained"))
				stringBuffer = stringBuffer.append(" and ex.detain=1 and (ex.rejoin=0 or ex.rejoin is null)");
			
			else if(stForm.getStatus()!=null && !stForm.getStatus().isEmpty() && stForm.getStatus().equalsIgnoreCase("Discontinued"))
				stringBuffer = stringBuffer.append(" and ex.discontinued=1 and (ex.rejoin=0 or ex.rejoin is null)");
			
			if(stForm.getSemester()!=null && !stForm.getSemester().isEmpty())
				stringBuffer = stringBuffer.append(" and ex.classSchemewise.classes.termNumber="+Integer.parseInt(stForm.getSemester()));
			
			if(stForm.getIsFinalYrStudents()!=null && stForm.getIsFinalYrStudents().equalsIgnoreCase("Yes"))
				stringBuffer = stringBuffer.append(" and ex.classSchemewise.curriculumSchemeDuration.curriculumScheme.noScheme=ex.classSchemewise.classes.termNumber");
			
			Query query1= session.createQuery(stringBuffer.toString());
			examStudentDetentionRejoinDetailslist = query1.list();
		}catch (Exception exception) {
			// TODO: handle exception
			if(session!=null){
				session.flush();
				session.close();
			}
			
		}
		
		return examStudentDetentionRejoinDetailslist;
	}


}