package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.PrintPasswordForm;
import com.kp.cms.transactions.admin.IPasswordPrintTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class PasswordPrintTransactionImpl implements IPasswordPrintTransaction {
	public static volatile PasswordPrintTransactionImpl passwordPrintTransactionImpl = null;

	public static PasswordPrintTransactionImpl getInstance() {
		if (passwordPrintTransactionImpl == null) {
			passwordPrintTransactionImpl = new PasswordPrintTransactionImpl();
			return passwordPrintTransactionImpl;
		}
		return passwordPrintTransactionImpl;
	}
	/**
	 * Is used to get the regd nos between a range
	 */
	@SuppressWarnings("unchecked")
	public List<Student> getRequiredRegdNos(String regNoFrom, String regNoTo) throws Exception{
		Session session = null;
		List<Student> regNoList;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =InitSessionFactory.getInstance().openSession();		
			session = HibernateUtil.getSession();
			regNoList = session.createQuery("from Student student" +
						" where student.isActive = 1 and student.registerNo between '" + regNoFrom + "' and '"+ regNoTo +"' and student.admAppln.isCancelled=0 order by student.registerNo").list();
				//Query query = session.createQuery(quer);
				//regNoList = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return regNoList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}/*
		finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
			*/
	}
	/*
	 * 
	 */
	public List<Student> getRequiredRollNos(String rollNoFrom, String rollNoTo) throws Exception{
		Session session = null;
		List<Student> rollNoList;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			rollNoList = session.createQuery("from Student student" +
						" where student.isActive = 1 and student.rollNo between '" + rollNoFrom +"' and '"+ rollNoTo +"' and student.admAppln.isCancelled=0 order by student.registerNo").list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return rollNoList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}/*
		finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}*/
	}
	@Override
	public List<Student> getPasswordPrintDetailsByProgrammAndSemOrClass(
			PrintPasswordForm passwordForm) throws Exception {
		Session session = null;
		List<Student> studentsList=new ArrayList<Student>();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			if(passwordForm.getAcademicYear()!=null && passwordForm.getProgramm()!=null && passwordForm.getSemester()!=null && !passwordForm.getAcademicYear().isEmpty() && !passwordForm.getProgramm().isEmpty() && !passwordForm.getSemester().isEmpty())
			{
				studentsList = session.createQuery("from Student student where student.isActive = 1"+
						"and student.classSchemewise.curriculumSchemeDuration.academicYear='" + passwordForm.getAcademicYear() +"' and student.classSchemewise.classes.termNumber='" + passwordForm.getSemester() +"'"+
                        "and student.classSchemewise.classes.course.program.id='" + passwordForm.getProgramm() +"' and student.admAppln.isCancelled=0 order by student.registerNo").list();
			
			}
			else if(passwordForm.getAcademicYear()!=null && !passwordForm.getAcademicYear().isEmpty() && passwordForm.getClasses()!=null && !passwordForm.getClasses().isEmpty())
			{
				studentsList = session.createQuery("from Student student where student.isActive = 1 and student.classSchemewise.curriculumSchemeDuration.academicYear='" + passwordForm.getAcademicYear() +"'"+
                                                  //"and student.classSchemewise.classes.id='" + passwordForm.getClasses() +"' and student.admAppln.isCancelled=0 order by student.registerNo").list();
			                                   "and student.classSchemewise.id='" + passwordForm.getClasses() +"' and student.admAppln.isCancelled=0 order by student.registerNo").list();
			}  
				session.flush();
			//session.close();
			//sessionFactory.close();
			return studentsList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
	
}
