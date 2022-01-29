package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;

public class ExamBlockUnblockHallTicketImpl extends ExamGenImpl {

	private static final Log log = LogFactory
			.getLog(ExamBlockUnblockHallTicketImpl.class);

	@SuppressWarnings("unchecked")
	public ArrayList<ExamDefinitionBO> select() throws Exception {
		Session session = null;
		ArrayList<ExamDefinitionBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamDefinitionBO.class)
					.addOrder(Order.desc("isCurrent"));

			crit.add(Restrictions.eq("delIsActive", true));
			crit.add(Restrictions.eq("isActive", true));

			list = new ArrayList(crit.list());
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;

	}

	public void delete(ArrayList<Integer> listBlockUnblockId) throws Exception {

		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction t = session.beginTransaction();
			String hql = "delete from ExamBlockUnblockHallTicketBO where id in (:listBlockUnblockIds)";
			Query query = session.createQuery(hql);
			query.setParameterList("listBlockUnblockIds", listBlockUnblockId);
			query.executeUpdate();
			t.commit();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ExamBlockUnblockHallTicketBO> select_checkMarks(
			int examId, ArrayList<Integer> listClassId, boolean useHallTicket,String type) throws Exception {
		Session session = null;
		ArrayList<ExamBlockUnblockHallTicketBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamBlockUnblockHallTicketBO.class);
			crit.add(Restrictions.eq("examId", examId));
			crit.add(Restrictions.in("classId", listClassId));
			if (useHallTicket) {
				crit.add(Restrictions.eq("hallTktOrMarksCard", "H"));
			} else {
				crit.add(Restrictions.eq("hallTktOrMarksCard", type));
			}
			list = new ArrayList(crit.list());
			session.flush();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public int getCurrentExam() throws Exception {
		String id = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "select d.id from ExamDefinitionBO d where d.isCurrent=1";

		Query query = session.createQuery(HQL);

		List list = null;
		try {
			list = query.list();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		Iterator<Object[]> itr = list.iterator();
		while (itr.hasNext()) {
			Object row = (Object) itr.next();
			id = row.toString();

		}
		int value = 0;
		if (id == null) {
			id = "0";
		} else {
			value = Integer.parseInt(id);
		}
		return value;
	}
	
	/**
	 * 
	 * @param classList
	 * @return
	 */
	public ArrayList<ExamStudentPreviousClassDetailsBO> getPreviousStudentDetails(String classList) {
		Session session = null;
		ArrayList<ExamStudentPreviousClassDetailsBO> list;
		try {

			String hql = "from ExamStudentPreviousClassDetailsBO e "
					+ " where classId in (" + classList + ") and (e.studentUtilBO.admApplnUtilBO.isCancelled=0 or e.studentUtilBO.admApplnUtilBO.isCancelled is null)";//added by chandra

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);
			list = new ArrayList<ExamStudentPreviousClassDetailsBO>(query.list());
			session.flush();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamStudentPreviousClassDetailsBO>();
		}
		return list;
	}
	
	/**
	 * 
	 * @param classList
	 * @return
	 */
	public ArrayList<ExamSupplementaryImprovementApplicationBO> getSupStudentDetails(String classList, int examId) {
		Session session = null;
		ArrayList<ExamSupplementaryImprovementApplicationBO> list;
		try {

			String hql = "from ExamSupplementaryImprovementApplicationBO imp "
					+ " where imp.classes.id in   (" + classList + ")" 
					+ "  and (imp.isAppearedTheory = 1 or "
					+ " imp.isFailedPractical = 1) and imp.examId = " + examId 
					+ "and (imp.studentUtilBO.admApplnUtilBO.isCancelled=0 or imp.studentUtilBO.admApplnUtilBO.isCancelled is null) "   //added by chandra
					+ "group by studentId";
					 

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);
			list = new ArrayList<ExamSupplementaryImprovementApplicationBO>(query.list());
			session.flush();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamSupplementaryImprovementApplicationBO>();
		}
		return list;
	}
	
	/**
	 * 
	 * @param examId
	 * @return
	 */
	public Integer getExamType(int examId) {
		Session session = null;
		int type = 0;
		try {

			String hql = "select examTypeID from ExamDefinitionBO e where e.id = " + examId;
					 
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);
		    List<Integer> examTypeId = query.list();
			if(examTypeId.get(0) != null && examTypeId.get(0) > 0){
				type = examTypeId.get(0);
			}
			session.flush();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return type;
	}

	public List<Object[]> getListOfBlockedCandidates(
			String examType, String typeId, String year, String examId, ArrayList<Integer> listClassIds) {
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try{
			session = HibernateUtil.getSession();
			String query = "select EXAM_block_unblock_hall_tkt_marks_card.id as blockId,EXAM_definition.id as examId,student.id as sId,classes.id as classId," +
							" personal_data.first_name,EXAM_block_unblock_hall_tkt_marks_card.block_reason,student.register_no as regNo, classes.name as className" +
							" from  EXAM_block_unblock_hall_tkt_marks_card " +
							" JOIN student ON EXAM_block_unblock_hall_tkt_marks_card.student_id = student.id " +
							" JOIN adm_appln ON student.adm_appln_id = adm_appln.id " +
							" JOIN personal_data ON adm_appln.personal_data_id = personal_data.id " +
							" JOIN classes ON EXAM_block_unblock_hall_tkt_marks_card.class_id = classes.id " +
							" JOIN EXAM_definition ON EXAM_block_unblock_hall_tkt_marks_card.exam_id = EXAM_definition.id " +
							" JOIN exam_type ON EXAM_definition.exam_type_id = exam_type.id " +
							" WHERE EXAM_definition.academic_year= "+year;
							if(examId != null && !examId.isEmpty())
								query = query + " AND EXAM_definition.id="+examId;
							if(!listClassIds.isEmpty())
								query = query + " AND classes.id in(:Classes)";
							if(typeId.equalsIgnoreCase("1"))
								query = query + " AND EXAM_block_unblock_hall_tkt_marks_card.hall_tkt_or_marks_card='H' ";
							else if(typeId.equalsIgnoreCase("3"))
								query = query + " AND EXAM_block_unblock_hall_tkt_marks_card.hall_tkt_or_marks_card='A' ";
							
							else
								query = query + " AND EXAM_block_unblock_hall_tkt_marks_card.hall_tkt_or_marks_card='M' ";
							query = query + " AND exam_type.id="+examType+" order by EXAM_definition.id,classes.name,student.register_no"; 
			if(!listClassIds.isEmpty())
				list = session.createSQLQuery(query).setParameterList("Classes", listClassIds).list();
			else
				list = session.createSQLQuery(query).list();
		}catch (Exception e) {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	/**
	 * @param bolist
	 * @throws Exception
	 */
	public void updateStudentBlockUnBlockAndReason(List<ExamBlockUnblockHallTicketBO> bolist) throws Exception{
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			for (ExamBlockUnblockHallTicketBO bo : bolist) {
				session.update(bo);
			}
			tx.commit();
			session.flush();
			session.close();
		}catch (Exception e) {
			if(session != null)
				session.close();
			if(tx != null)
				tx.rollback();
			throw new Exception(e);
		}
	}
	
}
