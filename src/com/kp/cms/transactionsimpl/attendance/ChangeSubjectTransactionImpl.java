package com.kp.cms.transactionsimpl.attendance;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ChangeSubjectForm;
import com.kp.cms.to.attendance.ChangeSubjectTo;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.attandance.IChangeSubject;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;



public class ChangeSubjectTransactionImpl implements IChangeSubject{
	private static volatile ChangeSubjectTransactionImpl changeSubjectTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ChangeSubjectTransactionImpl.class);
	
	/**
	 * @return
	 */
	public static ChangeSubjectTransactionImpl getInstance() {
		if (changeSubjectTransactionImpl == null) {
			changeSubjectTransactionImpl = new ChangeSubjectTransactionImpl();
		}
		return changeSubjectTransactionImpl;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IChangeSubject#subjectNameBySubjectCode(com.kp.cms.forms.attendance.ChangeSubjectForm)
	 */
	public String subjectNameBySubjectCode(ChangeSubjectForm form) throws Exception {
		Session session = null;
		String  subjectName="";
		try {
			session = HibernateUtil.getSession();
			if(form.getSubjectCode()!=null && !form.getSubjectCode().isEmpty()){
				Query selectedCandidatesQuery=session.createQuery("select s.name from Subject s where s.code='"+form.getSubjectCode()+"'");
				  subjectName= (String)selectedCandidatesQuery.uniqueResult();
			}
			return subjectName;
		} catch (Exception e) {
			log.error("Error while retrieving selected id.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IChangeSubject#getClassBySubjectId(com.kp.cms.forms.attendance.ChangeSubjectForm)
	 */
	public List<Attendance> getClassBySubjectId(ChangeSubjectForm form) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String searchCriteria="";
			searchCriteria= "select a from Attendance a "+							
							" where  a.attendanceDate between '" +CommonUtil.ConvertStringToSQLDate(form.getFromDate())+"'" +
							" and '" + CommonUtil.ConvertStringToSQLDate(form.getToDate())+"'" +
							" and a.subject.code='"+form.getSubjectCode()+"'"+" order by a.attendanceDate";
			Query selectedClassesQuery=session.createQuery(searchCriteria);
			List<Attendance> classList= selectedClassesQuery.list();
		
			return classList;
		} catch (Exception e) {
			log.error("Error while retrieving selected id.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IChangeSubject#getSubjectNamesByClassNames(java.util.Set)
	 */
	public List<ChangeSubjectTo> getSubjectNamesByClassNames(Set<String> classNamesSet) throws Exception {
		Session session = null;
		List<ChangeSubjectTo> subjectList =new ArrayList<ChangeSubjectTo>();
		List<Object[]> classList;
		StringBuilder classnames =new StringBuilder();
		try {
			if(classNamesSet != null && !classNamesSet.isEmpty()){
			Iterator itr=classNamesSet.iterator();
			while(itr.hasNext()){
				String className=(String)itr.next();
				classnames.append(",").append("'").append(className).append("'");
			}
			classnames.deleteCharAt(0);
			session = HibernateUtil.getSession();
			if(classnames!=null && !classnames.toString().isEmpty()){
					Query query=session.createQuery("select csubjects.subject.name||'(' ||csubjects.subject.code ||')',csubjects.subject.id "+
													"from ClassSchemewise c  inner join c.curriculumSchemeDuration.curriculumSchemeSubjects cs "+
													"inner join cs.subjectGroup.subjectGroupSubjectses  csubjects "+
													"where c.classes.name in(" + classnames + ")"+
													" group by csubjects.subject");
					classList=query.list();
					Iterator itr1=classList.iterator();
				while(itr1.hasNext()){
					ChangeSubjectTo to=new ChangeSubjectTo();
						Object[] object=(Object[])itr1.next();
						to.setId(Integer.parseInt(object[1].toString()));
						to.setName(object[0].toString());
						subjectList.add(to);
						}
					}
				
				}
			return subjectList;
		} catch (Exception e) {
			log.error("Error while retrieving selected id.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IChangeSubject#SelectedSubjectList(java.util.List)
	 */
	public List<Attendance> SelectedSubjectList(List<Integer> idList) throws Exception {
		Session session = null;
		StringBuilder ids =new StringBuilder();
		try {
			Iterator itr=idList.iterator();
			while(itr.hasNext()){
				Integer id=(Integer)itr.next();
				ids.append(",").append("'").append(id).append("'");
				}
			ids.deleteCharAt(0);
			session = HibernateUtil.getSession();
			String searchCriteria="";
			
			searchCriteria=	"select a from Attendance a  where  a.id in ("+ids+")";
							
			Query selectedClassesQuery=session.createQuery(searchCriteria);
			List<Attendance> boList= selectedClassesQuery.list();
		
			return boList;
		} catch (Exception e) {
			log.error("Error while retrieving selected id.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IChangeSubject#saveSubjectId(java.util.List, com.kp.cms.forms.attendance.ChangeSubjectForm)
	 */
	public boolean saveSubjectId(List<Attendance> boList,ChangeSubjectForm form) throws Exception{
		
		Transaction tx=null;
		Session session = null;
		try {
			boolean isSaved=false;
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			int subjectId;
			Subject subject = new Subject();
			subject.setId(Integer.parseInt(form.getChangedSubject()));
			
			if(boList!=null && !boList.isEmpty()){
				Iterator itr=boList.iterator();
				while(itr.hasNext()){
					Attendance bo=(Attendance)itr.next();
					bo.setSubject(subject);
					bo.setLastModifiedDate(new Date());
					bo.setModifiedBy(form.getUserId());
					session.update(bo);
					isSaved=true;
				}
			}
			tx.commit();
			session.close();
			return isSaved;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
				throw new ApplicationException(e);
		}
	}
	
}
