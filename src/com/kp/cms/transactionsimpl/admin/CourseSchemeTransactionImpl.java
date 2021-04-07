package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.ICourseSchemeTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CourseSchemeTransactionImpl implements ICourseSchemeTransaction {
	private static final Log log = LogFactory.getLog(CourseSchemeTransactionImpl.class);
	/**
	 * This This method returns all the coursename and course ids from course scheme where isActive=1
	 */
	public List<CourseScheme> getCourseScheme() throws Exception {
		Session session = null;
		List<CourseScheme> courseSchemeList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();

			courseSchemeList = session.createQuery("from CourseScheme coursescheme where isActive=1").list();

		} catch (Exception e) {
			log.error("Unable to get data from Coursescheme",e);
			throw new ApplicationException(e);
		}
			finally{
			if (session != null){
				session.flush();
				//session.close();
			}
			}

		return courseSchemeList;
	}
	
	/*
	 * This method gets the name based on the id
	 */
	
	public CourseScheme getNameOnId(int id) throws Exception{
		Session session = null;
		CourseScheme courseScheme=null;
		try {
			session = InitSessionFactory.getInstance().openSession();

			courseScheme = (CourseScheme) session.createQuery("from CourseScheme scheme where scheme.id=?").setInteger(0,
			id).uniqueResult();
			if(courseScheme!=null){
				return courseScheme;
			}
		} catch (Exception e) {
			log.error("Unable to get data from Coursescheme",e);
			throw new ApplicationException(e);
		}
			finally{
			if (session != null){
				session.flush();
				session.close();
			}
			}

		return null;
		
	}
}
