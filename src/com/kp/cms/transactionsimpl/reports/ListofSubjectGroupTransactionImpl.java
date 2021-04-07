package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.ListofSubjectGroupsForm;
import com.kp.cms.transactions.reports.IListofSubjectGroup;
import com.kp.cms.utilities.HibernateUtil;

public class ListofSubjectGroupTransactionImpl implements IListofSubjectGroup {
	private static final Log log = LogFactory.getLog(ListofSubjectGroupTransactionImpl.class);
	private static volatile ListofSubjectGroupTransactionImpl listofSubjectGroupTransactionImpl;
	public static ListofSubjectGroupTransactionImpl getInstance() {
		if (listofSubjectGroupTransactionImpl == null) {
			listofSubjectGroupTransactionImpl = new ListofSubjectGroupTransactionImpl();
			return listofSubjectGroupTransactionImpl;
		}
		return listofSubjectGroupTransactionImpl;
	}
	/**
	 * 
	 */
	public List<SubjectGroup> getSubjectGroup(ListofSubjectGroupsForm subForm) throws Exception {
		log.debug("inside getSubjectGroup");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from SubjectGroup s where isActive=1 and " +
					"s.course.program.programType.id = :programTypeId and s.course.program.id = :programId" );
			if(subForm.getCourseId()!= null && !subForm.getCourseId().trim().isEmpty()){
				sqlString.append(" and s.course.id = :courseId");
			}
			sqlString.append(" order by s.course.name, s.name");
			Query query = session.createQuery(sqlString.toString());
			
			query.setString("programTypeId", subForm.getProgramTypeId());
			query.setString("programId", subForm.getProgramId());
			if(subForm.getCourseId()!= null && !subForm.getCourseId().trim().isEmpty()){
				query.setString("courseId", subForm.getCourseId());
			}
			
			List<SubjectGroup> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getSubjectGroup");
			return list;
		} catch (Exception e) {
			log.error("Error during getting subject groups..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

}
