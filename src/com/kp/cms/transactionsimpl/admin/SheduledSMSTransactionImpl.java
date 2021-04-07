package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.MobileMessagingSchedule;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.transactions.admin.ISheduledSMSTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SheduledSMSTransactionImpl implements ISheduledSMSTransaction {

	public List<Student> getStudentList(String claIds)throws Exception{
		Session session =null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Student s where s.classSchemewise.id in ("+claIds+") and isAdmitted = 1 and s.isActive = 1 and s.admAppln.isCancelled=0");
			List<Student> students=query.list();
			return students;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			session.flush();
//			session.close();
		}
		return null;
	}
	
}
