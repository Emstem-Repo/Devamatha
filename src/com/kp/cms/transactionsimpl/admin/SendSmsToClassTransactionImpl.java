package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.SendSmsToClassForm;
import com.kp.cms.transactions.admin.ISendSmsToClassTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SendSmsToClassTransactionImpl implements
		ISendSmsToClassTransaction {
	private static final Log log = LogFactory.getLog(SendSmsToClassTransactionImpl.class);
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISendSmsToClassTransaction#getStudentForClass(java.lang.String)
	 */
	@Override
	public List<Student> getStudentForClass(String studentForClassQuery) {
		log.info("Entered into SendSmsToClassTransactionImpl-getStudentForClass");
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(studentForClassQuery);
			List<Student> stuList=query.list();
			return stuList;
		} catch (Exception e) {
			log.info("Error Occured into getStudentForClass"+e);
		}
		log.info("Exists From SendSmsToClassTransactionImpl-getStudentForClass");
		return null;
	}

	public Student getStudentDetails(SendSmsToClassForm sendSmsToClassForm)throws Exception{
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			String queryForStudent = "from Student s where s.admAppln.appliedYear="+sendSmsToClassForm.getApplicationYear();
			if(sendSmsToClassForm.getApplicationNumber()!=null && !sendSmsToClassForm.getApplicationNumber().isEmpty()){
				queryForStudent = queryForStudent+" and s.admAppln.applnNo="+sendSmsToClassForm.getApplicationNumber();
		    }
			if(sendSmsToClassForm.getRegisterNumber()!=null && !sendSmsToClassForm.getRegisterNumber().isEmpty()){
				queryForStudent = queryForStudent+" and s.registerNo=" +sendSmsToClassForm.getRegisterNumber();
		    }
			queryForStudent = queryForStudent+" and s.isAdmitted=1 and s.admAppln.isCancelled=0 and s.admAppln.isSelected=1";
			Query query=session.createQuery(queryForStudent);
			Student stuBO=(Student)query.uniqueResult();
			return stuBO;
		} catch (Exception e) {
			log.info("Error Occured into getStudentForClass"+e);
		}
		log.info("Exists From SendSmsToClassTransactionImpl-getStudentForClass");
		return null;
	}
}
