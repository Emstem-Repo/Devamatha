package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.bo.sap.SapRegistration;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.exam.IExtendSupplyImprApplDateTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExtendSupplyImprApplDateTransImpl implements IExtendSupplyImprApplDateTransaction{
	private static volatile ExtendSupplyImprApplDateTransImpl extendSuppluImprApplDateTransImpl = null;
	private static final Log log = LogFactory.getLog(ExtendSupplyImprApplDateTransImpl.class);
	/**
	 * return singleton object of PublishSupplementaryImpApplicationHandler.
	 * @return
	 */
	public static ExtendSupplyImprApplDateTransImpl getInstance() {
		if (extendSuppluImprApplDateTransImpl == null) {
			extendSuppluImprApplDateTransImpl = new ExtendSupplyImprApplDateTransImpl();
		}
		return extendSuppluImprApplDateTransImpl;
	}
	@Override
	public List<PublishSupplementaryImpApplication> getExamsToExtend(
			int examId) throws Exception {
		Session session = null;
		List<PublishSupplementaryImpApplication> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from PublishSupplementaryImpApplication p where p.isActive=1 and p.exam.id="+examId);
			list = query.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	@Override
	public List<PublishSupplementaryImpApplication>  getBosToUpdate(
			List<Integer> ids) throws Exception {
		List<PublishSupplementaryImpApplication> list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="from PublishSupplementaryImpApplication h where h.isActive=1  and h.id in (:list)";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", ids);
			list=(List<PublishSupplementaryImpApplication>)query.list();
			return list;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
	@Override
	public boolean updateTheData(
			List<PublishSupplementaryImpApplication> pbApplications)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Iterator<PublishSupplementaryImpApplication> iterator=pbApplications.iterator();
			while (iterator.hasNext()) {
				PublishSupplementaryImpApplication publishSupplementaryImpApplication = (PublishSupplementaryImpApplication) iterator.next();
				session.update(publishSupplementaryImpApplication);
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
	public List<ExamPublishHallTicketMarksCardBO> getExamsToExtendRegular(
			int examId) throws Exception {
		Session session = null;
		List<ExamPublishHallTicketMarksCardBO> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from ExamPublishHallTicketMarksCardBO p where p.isActive=1 and p.examId="+examId+" and p.publishFor='Regular Application'");
			list = query.list();
			return list;
			
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	@Override
	public boolean updateTheDataRegular(
			List<ExamPublishHallTicketMarksCardBO> pbApplications)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Iterator<ExamPublishHallTicketMarksCardBO> iterator=pbApplications.iterator();
			while (iterator.hasNext()) {
				ExamPublishHallTicketMarksCardBO publishSupplementaryImpApplication = (ExamPublishHallTicketMarksCardBO) iterator.next();
				session.update(publishSupplementaryImpApplication);
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
	public List<ExamPublishHallTicketMarksCardBO>  getBosToUpdateRegular(
			List<Integer> ids) throws Exception {
		List<ExamPublishHallTicketMarksCardBO> list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="from ExamPublishHallTicketMarksCardBO h where h.isActive=1  and h.id in (:list) and h.publishFor='Regular Application'";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", ids);
			list=(List<ExamPublishHallTicketMarksCardBO>)query.list();
			return list;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
	
	public List<ExamPublishHallTicketMarksCardBO> getExamsToExtendInternalRedo(int examId) throws Exception {
		
		Session session = null;
		List<ExamPublishHallTicketMarksCardBO> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from ExamPublishHallTicketMarksCardBO p where p.isActive=1 and p.examId="+examId+" and p.publishFor='Internal Redo'");
			list = query.list();
			return list;
			
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}		
	}
	
	public List<ExamPublishHallTicketMarksCardBO>  getBosToUpdateInternalRedo(
			List<Integer> ids) throws Exception {
		List<ExamPublishHallTicketMarksCardBO> list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="from ExamPublishHallTicketMarksCardBO h where h.isActive=1  and h.id in (:list) and h.publishFor='Internal Redo'";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", ids);
			list=(List<ExamPublishHallTicketMarksCardBO>)query.list();
			return list;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
}
