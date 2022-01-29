package com.kp.cms.transactionsimpl.admission;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.ConductCertificateDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.transactions.admission.IConductCertificateTransaction;
public class ConductCertificateTransactionImpl implements IConductCertificateTransaction{	
	public List<ConductCertificateDetails> getStudentList(String classId, String fromReg,
			String toReg, String studentId) throws Exception {
		
		
		Session session=null;
		try {
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer(
					"from ConductCertificateDetails s where  "	);
			
			if(fromReg!= null && toReg!= null && !fromReg.trim().isEmpty() && !toReg.trim().isEmpty() && fromReg.trim().equalsIgnoreCase(toReg.trim())){
				sqlString.append("( s.student.admAppln.isSelected=1 and s.student.admAppln.isApproved=1 OR ( s.student.admAppln.isCancelled = 1))");
			}
			else{
				sqlString.append(" s.student.admAppln.isSelected=1 and s.student.admAppln.isApproved=1 ");
			}
			//OR ( s.student.admAppln.isCancelled = 1 and s.student.admAppln.isApproved=1)
			//" and s.tcNo is null"
			if(classId!=null && !classId.trim().isEmpty() && (studentId==null || studentId.trim().isEmpty()) || studentId.equalsIgnoreCase("undefined"))
				sqlString.append(" and s.student.classSchemewise.id="+classId);
						
			if(fromReg!=null && !fromReg.trim().isEmpty())
				sqlString.append(" and s.student.registerNo>='"+fromReg+"'");
			if(toReg!=null && !toReg.trim().isEmpty())
				sqlString.append(" and s.student.registerNo<='"+toReg+"'");
			;
			if(studentId!=null && !studentId.trim().isEmpty())
			{
				if(studentId.equalsIgnoreCase("undefined")==false)
				sqlString.append(" and s.student.id='"+studentId+"'");
			}
			Query  query = session.createQuery(sqlString.toString());
			List<ConductCertificateDetails> studentList = query.list();
			return studentList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
		finally{
			session.flush();
		}
	}
	
	public List<StudentTCDetails> getStudentsByName(String name, String classId) throws Exception {
		
		Session session=null;
		List<StudentTCDetails> student=null;
		try {
			session = HibernateUtil.getSession();
			session.close();
			session = HibernateUtil.getSession();
			/* code changed by sudhir */
			/*StringBuffer sqlString = new StringBuffer(
					"select s from Student s " +
					"where s.registerNo='"+reg+"' " +
					" and s.tcNo is not null");*/
			StringBuffer sqlString = new StringBuffer(
			"select stc from StudentTCDetails  stc join stc.student where" +
			" stc.student.id in (select s.id from Student s " +
			" where s.admAppln.personalData.firstName like '%"+name+"%' ) " +
			" and stc.student.classSchemewise="+Integer.parseInt(classId));		
			
			Query  query = session.createQuery(sqlString.toString());
			student = query.list();			
			return student;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}	
		
	}
	public int getClassTermNumber(int classId) throws Exception {
		int termNumber = 0;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query = "select c.classes.termNumber from ClassSchemewise c where c.id = " +classId;
			termNumber = (Integer) session.createQuery(query).uniqueResult();
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
		}
		return termNumber;
	}

	@Override
	public List<String> getStudentroleNo(String classId, String fromReg,
			String toReg, String studentId) {
		Session session = null;
		List<String> rollNo=null;
		try {
			session = HibernateUtil.getSession();
			String query = "select s.student.rollNo from ConductCertificateDetails s where s.student.admAppln.isSelected=1 and s.student.admAppln.isApproved=1  and s.student.classSchemewise.id=" +classId + "order by s.student.rollNo";
			rollNo=session.createQuery(query).list();
			
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
		}
		return rollNo;
	}

	@Override
	public int getPtype(String classId) {
		Session session = null;
		int ptype=0;
		try {
			session = HibernateUtil.getSession();
			String query = "select c.course.program.programType.id from Classes c where c.id=" +classId;
			ptype= (Integer) session.createQuery(query).uniqueResult();
			
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
		}
		return ptype;
	}
}
