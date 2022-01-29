package com.kp.cms.transactionsimpl.examallotment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.examallotment.ExamRoomAvailability;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.examallotment.IExamRoomAvailabilityTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ExamRoomAvailabilityTxnImpln implements IExamRoomAvailabilityTransactions{
	private static volatile ExamRoomAvailabilityTxnImpln txnImpln = null;
	public static ExamRoomAvailabilityTxnImpln getInstance(){
		if(txnImpln == null){
			txnImpln = new ExamRoomAvailabilityTxnImpln();
		}
		return txnImpln;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAvailabilityTransactions#getWorkLocationDetails(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getWorkLocationDetails( String getWorkLocationQuery) throws Exception {
		Session session = null;
		Map<Integer,String> workLocationMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(getWorkLocationQuery);
			List<EmployeeWorkLocationBO> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<EmployeeWorkLocationBO> workLocationList = list.iterator();
				while (workLocationList.hasNext()) {
					EmployeeWorkLocationBO bo = (EmployeeWorkLocationBO) workLocationList .next();
					workLocationMap.put(bo.getId(), bo.getName());
				}
			}
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}
		workLocationMap =CommonUtil.sortMapByValue(workLocationMap);
		return workLocationMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAvailabilityTransactions#getExamRoomAvailabilityRoomList(java.lang.String)
	 */
	@Override
	public Map<Integer,Integer> getExamRoomAvailabilityRoomList( String hqlQuery1) throws Exception {
		Session session = null;
		Map<Integer,Integer> roomIdsMap = new HashMap<Integer, Integer>();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(hqlQuery1);
			List<ExamRoomAvailability> roomIdsList = query.list();
			if(roomIdsList!=null && !roomIdsList.isEmpty()){
				Iterator<ExamRoomAvailability> iterator = roomIdsList.iterator();
				while (iterator.hasNext()) {
					ExamRoomAvailability bo = (ExamRoomAvailability) iterator .next();
					roomIdsMap.put(bo.getRoomMaster().getId(), bo.getId());
				}
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				throw new ApplicationException(e);
			}
		}
		return roomIdsMap;
	}
	@Override
	public boolean saveList(List<ExamRoomAvailability> roomAvailabilitiesList) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			if(roomAvailabilitiesList!=null && !roomAvailabilitiesList.isEmpty()){
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				tx.begin();
				Iterator<ExamRoomAvailability> iterator = roomAvailabilitiesList.iterator();
				int count = 0;
				while (iterator.hasNext()) {
					ExamRoomAvailability examRoomAvailability = (ExamRoomAvailability) iterator .next();
					session.saveOrUpdate(examRoomAvailability);
					if (++count % 20 == 0) {
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
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return isAdded;
		}
		return isAdded;
	}

}
