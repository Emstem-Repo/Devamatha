package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zefer.cache.b;
import org.zefer.html.doc.q;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.CoCurricularTeacherBO;
import com.kp.cms.bo.admin.CoCurricularTeacherSubjectsBO;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AssignCocurricularSubjectTeacherForm;
import com.kp.cms.transactions.attandance.IAssignCocurricularSubjectTeacherTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class AssignCocurricularSubjectTeacherTransactionImpl implements
		IAssignCocurricularSubjectTeacherTransaction {
	private static volatile AssignCocurricularSubjectTeacherTransactionImpl assignCocurricularSubjectTeacherTransactionImpl = null;
	private static final Log log = LogFactory
			.getLog(AssignCocurricularSubjectTeacherTransactionImpl.class);

	public static AssignCocurricularSubjectTeacherTransactionImpl getInstance() {
		if (assignCocurricularSubjectTeacherTransactionImpl == null) {
			assignCocurricularSubjectTeacherTransactionImpl = new AssignCocurricularSubjectTeacherTransactionImpl();
		}
		return assignCocurricularSubjectTeacherTransactionImpl;
	}

	@Override
	public Map<Integer, String> getUsers() throws Exception {
		log
				.debug("call of getUsers method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		Map<Integer, String> userMap = new HashMap<Integer, String>();
		List<Object[]> list = new ArrayList<Object[]>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String hqlQuery = "select d.id,d.userName from  Users d where d.isActive=1";
			Query query = session.createQuery(hqlQuery);
			list = query.list();
			if (list != null && list.size() > 0) {
				Iterator<Object[]> iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] object = iterator.next();
					userMap.put(Integer.parseInt(object[0].toString()),
							object[1].toString());
				}
			}
		} catch (Exception e) {
			log.error("Error in get getUsers" + e.getMessage());
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log
				.debug("end of getUsers method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		return userMap;
	}

	@Override
	public Map<Integer, String> getActivityMap() throws Exception {
		log
				.debug("call of getActivityMap method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		List<Object[]> list = new ArrayList<Object[]>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String hqlQuery = "select d.id,d.name from  Activity d where d.isActive=1";
			Query query = session.createQuery(hqlQuery);
			list = query.list();
			if (list != null && list.size() > 0) {
				Iterator<Object[]> iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] object = iterator.next();
					activityMap.put(Integer.parseInt(object[0].toString()),
							object[1].toString());
				}
			}
		} catch (Exception e) {
			log.error("Error in get getActivityMap" + e.getMessage());
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log
				.debug("end of getActivityMap method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		return activityMap;
	}

	@Override
	public List<CoCurricularTeacherBO> checkDuplicate(String teacherID)
			throws Exception {
		log
				.debug("call of checkDuplicate method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		List<CoCurricularTeacherBO> coCurricularTeacherBOs = new ArrayList<CoCurricularTeacherBO>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String hqlQuery = "select m from CoCurricularTeacherBO m where m.users.id="
					+ teacherID;
			Query query = session.createQuery(hqlQuery);
			coCurricularTeacherBOs = query.list();
		} catch (Exception e) {
			log
					.debug("Error in checkDuplicate method in AssignCocurricularSubjectTeacherTransactionImpl.class"
							+ e.getMessage());
			throw new ApplicationException(e);
			// TODO: handle exception
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log
				.debug("end of checkDuplicate method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		return coCurricularTeacherBOs;
	}

	@Override
	public boolean saveCocurricularSubjectTeacher(
			CoCurricularTeacherBO coCurricularTeacherBO) throws Exception {
		log
				.debug("call of saveCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(coCurricularTeacherBO);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			log
					.error("Error in saveCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherTransactionImpl.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log
				.debug("end of saveCocurricularSubjectTeacher method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		return isAdded;
	}

	public boolean reactiveCocurricularSubjectTeacher(String dupliateId,
			String userid) throws Exception {
		log
				.debug("call reactiveCocurricularSubjectTeacher method AssignCocurricularSubjectTeacherTransactionImpl.class ");
		boolean isReactived = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			CoCurricularTeacherBO bo = (CoCurricularTeacherBO) session.get(
					CoCurricularTeacherBO.class, Integer.parseInt(dupliateId));
			bo.setIsActive(Boolean.TRUE);
			bo.setLastModifiedDate(new Date());
			bo.setModifiedBy(userid);
			session.update(bo);
			transaction.commit();
			isReactived = true;

		} catch (Exception e) {
			transaction.rollback();
			log
					.error("Error reactiveCocurricularSubjectTeacher method AssignCocurricularSubjectTeacherTransactionImpl.class");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log
				.debug("end reactiveCocurricularSubjectTeacher method AssignCocurricularSubjectTeacherTransactionImpl.class ");
		return isReactived;
	}

	public List<CoCurricularTeacherBO> getCocurricularList() throws Exception {
		log
				.debug("call of getCocurricularList method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		List<CoCurricularTeacherBO> boList = new ArrayList<CoCurricularTeacherBO>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String hqlQuery = "select m from  CoCurricularTeacherBO  m where m.isActive=1";
			Query query = session.createQuery(hqlQuery);
			boList = query.list();

		} catch (Exception e) {
			log
					.error("Error in getCocurricularList method in AssignCocurricularSubjectTeacherTransactionImpl.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		} finally {
			if (session != null) {

			}
		}
		log
				.debug("end of getCocurricularList method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		return boList;
	}

	public boolean deleteCocurricularTeacher(
			AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm)
			throws Exception {
		log
				.debug("call of deleteCocurricularTeacher method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		boolean isDeleted = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			CoCurricularTeacherBO bo = (CoCurricularTeacherBO) session.get(
					CoCurricularTeacherBO.class, Integer
							.parseInt(assignCocurricularSubjectTeacherForm
									.getId()));
			bo.setIsActive(Boolean.FALSE);
			bo.setLastModifiedDate(new Date());
			bo.setModifiedBy(assignCocurricularSubjectTeacherForm.getUserId());
			session.update(bo);
			transaction.commit();
			session.flush();
			isDeleted = true;

		} catch (Exception e) {
			log
					.error("Error in deleteCocurricularTeacher method in AssignCocurricularSubjectTeacherTransactionImpl.class");
			transaction.rollback();
			throw new ApplicationException(e);

			// TODO: handle exception
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log
				.debug("end of deleteCocurricularTeacher method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		return isDeleted;
	}

	@Override
	public CoCurricularTeacherBO editCocurricularTeacher(String id)
			throws Exception {
		log
				.debug("call of editCocurricularTeacher method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		Session session = null;
		CoCurricularTeacherBO coCurricularTeacherBO = new CoCurricularTeacherBO();
		try {
			session = InitSessionFactory.getInstance().openSession();
			coCurricularTeacherBO = (CoCurricularTeacherBO) session.get(
					CoCurricularTeacherBO.class, Integer.parseInt(id));

		} catch (Exception e) {
			throw new ApplicationException(e);
			// TODO: handle exception
		}

		log
				.debug("end of editCocurricularTeacher method in AssignCocurricularSubjectTeacherTransactionImpl.class");
		return coCurricularTeacherBO;
	}

	@Override
	public boolean updateCocurricularSubjectTeacher(
			CoCurricularTeacherBO coCurricularTeacherBO) throws Exception {
		log
				.debug("call of updateCocurricularSubjectTeacher method AssignCocurricularSubjectTeacherTransactionImpl.class");
		boolean isUpdate = false;
		Session session = null;
		Session session1 = null;
		Transaction transaction = null;
		Transaction transaction1 = null;
		List<CoCurricularTeacherSubjectsBO> list = new ArrayList<CoCurricularTeacherSubjectsBO>();
		try {
			// delete old
			session1 = HibernateUtil.getSession();
			transaction1 = session1.beginTransaction();
			Query query = session1
					.createQuery("select m from CoCurricularTeacherSubjectsBO m where m.coCurricularTeacherBO.id="
							+ coCurricularTeacherBO.getId());
			list = query.list();
			Iterator<CoCurricularTeacherSubjectsBO> iterator = list.iterator();
			while (iterator.hasNext()) {
				CoCurricularTeacherSubjectsBO bo = iterator.next();
				session1.delete(bo);
			}
			transaction1.commit();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(coCurricularTeacherBO);
			transaction.commit();
			session.flush();
			isUpdate = true;
		} catch (Exception e) {
			transaction.commit();
			throw new ApplicationException(e);
			// TODO: handle exception
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}

		log
				.debug("end of updateCocurricularSubjectTeacher method AssignCocurricularSubjectTeacherTransactionImpl.class");
		return isUpdate;
	}

}
