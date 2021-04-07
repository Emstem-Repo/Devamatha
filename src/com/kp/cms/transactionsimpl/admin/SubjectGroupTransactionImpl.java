package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.SubjectGroupEntryForm;
import com.kp.cms.transactions.admin.ISubjectGroupTransaction;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;
/**
 * 
 * @author
 * An implementation class for ISubjectGroupTransaction
 *
 */

public class SubjectGroupTransactionImpl extends ExamGenImpl implements ISubjectGroupTransaction {
	private static Log log = LogFactory.getLog(SubjectGroupTransactionImpl.class);
	
	/**
	 * This method returns subjectid and subjectname from Subjectgroup
	 */

	public List<SubjectGroup> getSubjectGroupDetails(int courseId) throws Exception{
		Session session = null;
		Transaction transaction=null;
		List<SubjectGroup> subjectGroupList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction=session.beginTransaction();
			subjectGroupList = session.createQuery("from SubjectGroup subject where subject.course.id=? and subject.isActive=1 order by subject.name").setInteger(0,courseId).list();
			transaction.commit();

		} catch (Exception e) {
			log.error("Unable to get subjectgroup"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return subjectGroupList;
	}
	
	/**
	 * This method is used to get details of Subject as a type of list.
	 * @return list of type Subject.
	 */
	
	public List<Subject> getSubjectDetails() throws Exception{
		Session session = null;
		List<Subject> subjectList;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			subjectList = session.createQuery("from Subject s where s.isActive = 1 order by s.name").list();

		} catch (Exception e) {
			log.error("Unable to get subjectgroup"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return subjectList;
	}
	
	/**
	 * This method is used to get details of SubjectGroup as a type of list.
	 * @return list of type SubjectGroup. 
	 */
	
	public List<SubjectGroup> getSubjectGroup() throws Exception{
		Session session = null;
		List<SubjectGroup> subjectGroupList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			subjectGroupList = session.createQuery("from SubjectGroup subgroup where subgroup.isActive = 1").list();

		} catch (Exception e) {
			log.error("Unable to get subjectgroup"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return subjectGroupList;
	}
	
	/**
	 * This method is used for duplicate entry check and returns SubjectGroup instance.
	 * @return SubjectGroup BO instance.
	 */
	
	public SubjectGroup subjectGroupNameExist(int courseId, String subjectGroupName) throws Exception{
		Session session = null;
		SubjectGroup subjectGroup;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query q=session.createQuery("from SubjectGroup sgroup where sgroup.course.id= :courseId and sgroup.name= :subjectGroupName ");
			q.setInteger("courseId", courseId);
			q.setString("subjectGroupName",subjectGroupName);
			subjectGroup = (SubjectGroup)q.uniqueResult();
		
		} catch (Exception e) {
			log.error("Unable to get subjectgroup"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return subjectGroup;
	}
	
	/**
	 * This is used to adding a record to database.
	 * @return boolean value.
	 */
	
	public boolean addSubjectGroupEntry(SubjectGroup subjectGroup) throws Exception{
		Session session = null;
		Transaction transaction=null;
		boolean isadded=false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction=session.beginTransaction();
			session.save(subjectGroup);
			transaction.commit();
			isadded=true;
			} catch (Exception e) {
				isadded=false;
			log.error("Unable to get subjectgroup"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isadded;
	}
	
	/**
	 * This method is used to get SubjectGroup based on subjectGroupId.
	 * @return SubjectGroup BO instance.
	 */
	
	public SubjectGroup getSubjectGroupEntry(int subjectGroupId) throws Exception{
		SubjectGroup subjectGroup;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			subjectGroup=(SubjectGroup)session.createQuery("from SubjectGroup sgroup where sgroup.id=?").setInteger(0,subjectGroupId).uniqueResult();
		}catch (Exception e) {
			log.error("Unable to get subjectgroup"+e);
			throw  new ApplicationException(e);
		}
		return subjectGroup;
	}
	
	/**
	 * This method is used for updating a record in database.
	 * @return boolean value.
	 */
	
	public boolean updateSubjectGroupEntry(SubjectGroup subjectGroup) throws Exception{
		Session session = null;
		Transaction transaction=null;
		boolean isUpdated=false;
		try {
			session = InitSessionFactory.getInstance().openSession();
			//session = HibernateUtil.getSession();
			transaction=session.beginTransaction();
			session.saveOrUpdate(subjectGroup);
			transaction.commit();
			isUpdated=true;
		} catch (Exception e) {
			isUpdated=false;
			log.error("Unable to get subjectgroup"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}
	
	/**
	 * This method is used to delete a record in database and setting isActive = false.
	 * @return boolean value.
	 */
	
	public boolean deleteSubjectGroupEntry(int id, String userId) throws Exception{
		Session session = null;
		Transaction transaction=null;
		boolean isdelete=false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction=session.beginTransaction();
			SubjectGroup group=(SubjectGroup)session.get(SubjectGroup.class, id);
			group.setIsActive(Boolean.FALSE);
			group.setModifiedBy(userId);
			group.setLastModifiedDate(new Date());
			transaction.commit();
			isdelete=true;
		} catch (Exception e) {
			isdelete=false;
			log.error("Unable to get subjectgroup"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isdelete;
	}
	
	/**
	 * This method is used for restoring a record from database and setting isActive = true.
	 */
	
	public void reActivateSubjectGroupEntry(SubjectGroupEntryForm subjectGroupEntryForm)throws Exception {
	Session session = null;
	Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from SubjectGroup sgroup where sgroup.course.id= :courseId and sgroup.name = :subjectGroupName");
			query.setString("subjectGroupName",subjectGroupEntryForm.getSubjectGroupName());
			query.setInteger("courseId", Integer.parseInt(subjectGroupEntryForm.getCourseId()));
			SubjectGroup subjectGroup = (SubjectGroup) query.uniqueResult();
			transaction = session.beginTransaction();
			subjectGroup.setIsActive(true);
			subjectGroup.setModifiedBy(subjectGroupEntryForm.getUserId());
			subjectGroup.setLastModifiedDate(new Date());
			session.update(subjectGroup);
			transaction.commit();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
	}

	@Override
	public List<SubjectGroup> getSubjectGroupDetailsByCourseAndTermNo(int courseId, int year, int semesterNo) throws Exception {

		Session session = null;
		List<SubjectGroup> subjectGroupList = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("select c.subjectGroup "
							+ "from CurriculumSchemeSubject c "
							+ "where c.curriculumSchemeDuration.curriculumScheme.course= "
							+ courseId
							+ "and c.curriculumSchemeDuration.curriculumScheme.year="
							+ year+" and c.curriculumSchemeDuration.semesterYearNo="+semesterNo);
			subjectGroupList = query.list();
			session.flush();
			session.close();
		}
		catch (RuntimeException runtime) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return subjectGroupList;
	}
	/**
	 * This method is used to get details of SubjectGroup as a type of list.
	 * @return list of type SubjectGroup. 
	 */
	
	public List<SubjectGroup> getSubjectGroupList(String programTypeId , String programId) throws Exception{
		Session session = null;
		List<SubjectGroup> subjectGroupList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String query = "from SubjectGroup subgroup "+
						   " where subgroup.isActive = 1"+
						   " and subgroup.course.program.id = "+programId+
						   " and subgroup.course.program.programType.id = "+programTypeId;
			subjectGroupList = session.createQuery(query).list();

		} catch (Exception e) {
			log.error("Unable to get subjectgroup"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return subjectGroupList;
	}
}