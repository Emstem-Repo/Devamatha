package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.exam.AdminMarksCardForm;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.transactions.exam.IProgressCardTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ProgressCardImpl implements IProgressCardTransaction {

	private static IProgressCardTransaction progressCardImpl=null;
	public static IProgressCardTransaction getInstance() {
		if (progressCardImpl==null) {
			progressCardImpl=new ProgressCardImpl();
		}
		return progressCardImpl;
	}
	@Override
	public List<Student> getStudentsByClassName(AdminMarksCardForm adminMarksCardForm, List<Integer> detainedStudents)
			throws Exception {

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			StringBuffer feeApplicantDetailsQuery = new StringBuffer("");
			feeApplicantDetailsQuery.append("from Student student" +
					" where student.admAppln.isSelected = :isSelected" +
					" and student.admAppln.isApproved = 1" +
					//" student.id not in (:detainedStudentList) " +
					" and student.classSchemewise.classes.id=:classId" );
			if (!detainedStudents.isEmpty()) {
				feeApplicantDetailsQuery.append("and student.id not in ("+detainedStudents+")");
			}
				 //   " and student.admAppln.personalData.firstName like '%" + studentName + "%'" );
			
			Query query = session.createQuery(feeApplicantDetailsQuery.toString());
			query.setBoolean("isSelected", true);
			query.setString("classId", adminMarksCardForm.getClassId());
			//query.setParameterList("detainedStudentList", detainedStudents);
			List<Student> tempList = query.list();
			return tempList;
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public Student getStudentsDetails(int sid) throws Exception {

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String quer="select from Student s where s.id="+sid;
			Query query = session.createQuery(quer);
			Student student = (Student) query.uniqueResult();
			return student;
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}

	
	
}
