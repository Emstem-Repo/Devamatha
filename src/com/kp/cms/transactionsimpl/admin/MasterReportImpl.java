package com.kp.cms.transactionsimpl.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IMasterReportTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class MasterReportImpl implements IMasterReportTxn{

	
	
	@Override
	public Map getMasterResult(String searchCriteria)
			throws Exception {
	

		log.info("entered getStudentAttendance..");
		Session session = null;
		List masterReportList = null;
		Map masterMap = new HashMap();
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session=HibernateUtil.getSession();
//			masterReportList = session.createQuery("from "+searchCriteria+" master").list();
			if(searchCriteria.equalsIgnoreCase("NewsEvents")){
				masterReportList = session.createQuery("select master, (select users.userName from Users users where users.id= master.createdBy and users.isActive=1) " +
						" from "+searchCriteria+" master").list();  //tables which have only created by fields
				
			}
			else
			{
				masterReportList = session.createQuery("select master, (select users.userName from Users users where users.id= master.createdBy and users.isActive=1)" +
										", (select users.userName from Users users where users.id= master.modifiedBy and users.isActive=1)" +
										" from "+searchCriteria+" master").list();
			
			}
	
			ClassMetadata cm = InitSessionFactory.getInstance().getClassMetadata(Class.forName("com.kp.cms.bo.admin."+searchCriteria));
			String field[] = cm.getPropertyNames();
			masterMap.put("metadata", field);
			masterMap.put("masterList", masterReportList);  
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit getStudentAttendance..");
		return masterMap;
	}
	private static final Log log = LogFactory.getLog(MasterReportImpl.class);
	public static volatile MasterReportImpl self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static MasterReportImpl getInstance(){
		if(self==null)
			self= new MasterReportImpl();
		return self;
	}
	/**
	 * 
	 */
	private MasterReportImpl(){
		
	}

}
