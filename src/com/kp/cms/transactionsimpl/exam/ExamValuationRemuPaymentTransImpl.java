package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.exam.ExamValuationRemuneration;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.bo.sap.SapRegistration;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamValuationRemuPaymentForm;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.transactions.exam.IExamValuationRemuPaymentTrans;
import com.kp.cms.utilities.HibernateUtil;

public class ExamValuationRemuPaymentTransImpl implements IExamValuationRemuPaymentTrans{
	public static volatile ExamValuationRemuPaymentTransImpl examValuationRemuPaymentTransImpl = null;
	public static ExamValuationRemuPaymentTransImpl getInstance() {
		if (examValuationRemuPaymentTransImpl == null) {
			examValuationRemuPaymentTransImpl = new ExamValuationRemuPaymentTransImpl();
			return examValuationRemuPaymentTransImpl;
		}
		return examValuationRemuPaymentTransImpl;
	}
	@Override
	public List<Object[]> getListOfSearchedValuators(String query)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query sqlQuery=session.createSQLQuery(query);
			List<Object[]> list =(List<Object[]>) sqlQuery.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public boolean update(
			List<ExamValuationRemuneration> examValuationRemunerations)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Iterator<ExamValuationRemuneration> iterator=examValuationRemunerations.iterator();
			while (iterator.hasNext()) {
				ExamValuationRemuneration examValuationRemuneration = (ExamValuationRemuneration) iterator.next();
				session.merge(examValuationRemuneration);
			}
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return result;
}
	@Override
	public List<ExamValuationRemuneration> getExamValuationRemunirations(
			List<Integer> integers) throws Exception {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="from ExamValuationRemuneration h where h.isActive=1  and h.id in (:list)";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", integers);
			List<ExamValuationRemuneration> list=(List<ExamValuationRemuneration>)query.list();
			return list;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
	
	public GuestFaculty getGuestFaculty(String name) throws Exception {
		Session session = null;
		GuestFaculty guestFaculty = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from GuestFaculty e where e.firstName= :name");
			query.setString("name", name);
			guestFaculty = (GuestFaculty)query.uniqueResult();
		} catch (Exception e) {			
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return guestFaculty;	
	}
	@Override
	public List<Object[]> getBoardMeetingCharges(
			ExamValuationRemuPaymentForm examValuationRemuPaymentForm)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query = "select (vcharge.board_meeting_charge * count(distinct s.id)) as chrg,remun.bill_no,remun.user_id as user,date(remun.created_date) as bill_date,remun.external_id as external" +
					" from exam_remuneration_details as remun_detail " +
					" inner join exam_valuation_remuneration as remun  ON remun_detail.remuneration_id = remun.id " +
					" inner join subject as s ON remun_detail.subject_id = s.id " +
					" inner join subject_group_subjects as sgs on sgs.subject_id = s.id " +
					" inner join subject_group as sg ON sgs.subject_group_id = sg.id " +
					" inner join course crs ON sg.course_id = crs.id " +
					" inner join program pgm ON crs.program_id = pgm.id " +
					" inner join program_type pt ON pgm.program_type_id = pt.id " +
					" INNER JOIN valuation_charges vcharge " +
					" ON vcharge.program_type_id = pt.id  AND vcharge.is_active = 1 " +
					" where remun_detail.total_scripts <> 0 " +
					" and remun_detail.total_scripts is not null " +
					" and remun_detail.is_active=1 and remun_detail.is_board_meeting_applicable=1";
					if(examValuationRemuPaymentForm.getValuatorsType().equalsIgnoreCase("I")){
						query=query+" and remun.user_id is not null";
					}else if(examValuationRemuPaymentForm.getValuatorsType().equalsIgnoreCase("E")){
						query=query+" and remun.user_id is null";
					}
					if(examValuationRemuPaymentForm.getPaidStauts().equalsIgnoreCase("paid")){
						query=query+" and remun.is_paid=1";
					}else{
						query=query+" and (remun.is_paid=0 or remun.is_paid is null)";
					}
				query=query+" and remun.is_active=1 group by remun.bill_no, pt.id";
			Query sqlQuery=session.createSQLQuery(query );
			List<Object[]> list =(List<Object[]>) sqlQuery.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
	}
}
