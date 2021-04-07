package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.reports.IRegNoOrRollNoInStickerTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class RegNoOrRollNoInStickerTxnImpl implements IRegNoOrRollNoInStickerTransaction {
	private static final Log log = LogFactory.getLog(RegNoOrRollNoInStickerTxnImpl.class);
	public static volatile RegNoOrRollNoInStickerTxnImpl regNoOrRollNoInStickerTxnImpl = null;

	public static RegNoOrRollNoInStickerTxnImpl getInstance() {
		if (regNoOrRollNoInStickerTxnImpl == null) {
			regNoOrRollNoInStickerTxnImpl = new RegNoOrRollNoInStickerTxnImpl();
			return regNoOrRollNoInStickerTxnImpl;
		}
		return regNoOrRollNoInStickerTxnImpl;
	}

	/**
	 * Is used to get the regd nos between a range
	 */
	public List<Student> getRequiredRegdNos(String regNoFrom, String regNoTo, int progTypeId) throws Exception{
		log.debug("start getRequiredRegdNos");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session =InitSessionFactory.getInstance().openSession();	*/
			session = HibernateUtil.getSession();
			List<Student> regNoList = session.createQuery("from Student student" +
						" where student.isActive = 1 and student.registerNo between '" + regNoFrom +"' and '"+ regNoTo+
						"' and  admAppln.courseBySelectedCourseId.program.programType.id = '" + progTypeId + "'" +
						" order by registerNo").list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getRequiredRegdNos");
			return regNoList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	/*
	 * 
	 */
	public List<Student> getRequiredRollNos(String rollNoFrom, String rollNoTo, int progTypeId) throws Exception{
		log.debug("start getRequiredRollNos");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session =InitSessionFactory.getInstance().openSession();*/
			session = HibernateUtil.getSession();
			List<Student> rollNoList = session.createQuery("from Student student" +
						" where student.isActive = 1 and student.rollNo between '" + rollNoFrom +"' and '"+ rollNoTo +
						"' and  admAppln.courseBySelectedCourseId.program.programType.id = '" + progTypeId + "'" +						
						" order by rollNo").list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getRequiredRollNos");
			return rollNoList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
		
	
}
