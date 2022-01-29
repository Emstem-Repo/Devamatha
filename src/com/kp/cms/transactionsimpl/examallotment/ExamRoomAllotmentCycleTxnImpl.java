package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycleDetails;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentCycleForm;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentCycleTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExamRoomAllotmentCycleTxnImpl implements IExamRoomAllotmentCycleTransaction{
	private static volatile ExamRoomAllotmentCycleTxnImpl impl ;
	public static ExamRoomAllotmentCycleTxnImpl getInstance(){
		if(impl == null){
			impl =new ExamRoomAllotmentCycleTxnImpl();
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentCycleTransaction#saveRoomCycleBo(com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle)
	 */
	@Override
	public boolean saveRoomCycleBo(List<ExamRoomAllotmentCycleDetails> boList) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			if(boList!=null && !boList.isEmpty()){
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				tx.begin();
				Iterator<ExamRoomAllotmentCycleDetails> iterator = boList.iterator();
				int count=0;
				while (iterator.hasNext()) {
					ExamRoomAllotmentCycleDetails bo = (ExamRoomAllotmentCycleDetails) iterator .next();
					session.save(bo);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				}
				tx.commit();
				session.flush();
				session.close();
				isAdded = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentCycleTransaction#getListOfData(java.lang.String)
	 */
	@Override
	public List<Course> getListOfData(String hqlQuery) throws Exception {
		Session session = null;
		List<Course> courseIdList = new ArrayList<Course>();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(hqlQuery);
			courseIdList = query.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return courseIdList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentCycleTransaction#deleteRoomCycle(int, boolean)
	 */
	@Override
	public boolean deleteRoomCycle(int id, boolean activate,String userId) throws Exception {
		Session session = null;
		boolean isDeleted = false;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			ExamRoomAllotmentCycle bo = (ExamRoomAllotmentCycle) session.get(ExamRoomAllotmentCycle.class, id);
			bo.setIsActive(false);
			bo.setLastModifiedDate(new Date());
			bo.setModifiedBy(userId);
			session.update(bo);
			tx.commit();
			session.flush();
			session.close();
			isDeleted = true;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return isDeleted;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentCycleTransaction#getMidEndAndSessionByCycleId(int)
	 */
	@Override
	public ExamRoomAllotmentCycle getMidEndAndSessionByCycleId(int cycleId)
			throws Exception {
		Session session = null;
		ExamRoomAllotmentCycle allotmentCycle = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from ExamRoomAllotmentCycle cycle where cycle.isActive=1 and cycle.id="+cycleId;
			Query query = session.createQuery(str);
			allotmentCycle = (ExamRoomAllotmentCycle) query.uniqueResult();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return allotmentCycle;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentCycleTransaction#getRoomAllotCycleDetails()
	 */
	@Override
	public List<ExamRoomAllotmentCycleDetails> getRoomAllotCycleDetails() throws Exception {
		Session session =null;
		List<ExamRoomAllotmentCycleDetails> boList = new ArrayList<ExamRoomAllotmentCycleDetails>();
			try{
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from ExamRoomAllotmentCycleDetails cycleDetails where cycleDetails.isActive=1");
				boList = query.list();
			}catch (Exception e) {
				// TODO: handle exception
			}
		// TODO Auto-generated method stub
		return boList;
	}
	@Override
	public boolean updateAllotmentCycleDetails( List<ExamRoomAllotmentCycleDetails> boList,ExamRoomAllotmentCycleForm objForm,String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if(boList!=null && !boList.isEmpty()){
				Iterator<ExamRoomAllotmentCycleDetails> iterator = boList.iterator();
				int count =0;
				while (iterator.hasNext()) {
					ExamRoomAllotmentCycleDetails bo = (ExamRoomAllotmentCycleDetails) iterator .next();
					if(mode.equalsIgnoreCase("delete")){
						bo.setIsActive(false);
						bo.setModifiedBy(objForm.getUserId());
						bo.setLastModifiedDate(new Date());
					}
					session.saveOrUpdate(bo);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				}
				tx.commit();
				session.flush();
				session.close();
				isDeleted = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}
	@Override
	public ExamRoomAllotmentCycle getMidSemSchemeNoByCycleId(String cycleId) throws Exception {
		ExamRoomAllotmentCycle cycle = null;
		Session session  = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from ExamRoomAllotmentCycle cycle where cycle.isActive=1 and cycle.id="+Integer.parseInt(cycleId);
			Query query = session.createQuery(str);
			cycle = (ExamRoomAllotmentCycle) query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cycle;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentCycleTransaction#getExaminationSessionMap()
	 */
	@Override
	public Map<Integer, String> getExaminationSessionMap() throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		Session session  = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from ExaminationSessions e where e.isActive=1";
			Query query = session.createQuery(str);
			List<ExaminationSessions> list = query.list();
			if(list != null){
				for (ExaminationSessions examinationSessions : list) {
					if(examinationSessions.getId() != 0 && examinationSessions.getSession() != null)
						map.put(examinationSessions.getId(), examinationSessions.getSession());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
