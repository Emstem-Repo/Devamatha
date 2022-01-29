package com.kp.cms.transactionsimpl.timetable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.timetable.ViewTeacherWiseTimeTableForm;
import com.kp.cms.transactions.timetable.IViewTeacherWiseTimeTableTransactions;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ViewTeacherWiseTimeTableTxnImpl implements IViewTeacherWiseTimeTableTransactions {
	
	private static volatile ViewTeacherWiseTimeTableTxnImpl tableTxnImpl = null;
	/**
	 * private Constructor is defined, avoid to create object anywhere for these class.
	 */
	private ViewTeacherWiseTimeTableTxnImpl(){
		
	}
	/**
	 * SingleTon Object Created for Class.
	 */
	public static ViewTeacherWiseTimeTableTxnImpl getInstance(){
		if(tableTxnImpl == null){
			tableTxnImpl = new ViewTeacherWiseTimeTableTxnImpl();
		}
		return tableTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.timetable.IViewTeacherWiseTimeTableTransactions#getListDataByQuery(java.lang.String)
	 */
	@Override
	public List<Object[]> getListDataByQuery(String sql_Query) throws Exception {
		Session session= null;
		List<Object[]> list = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery(sql_Query).addScalar("periodId", Hibernate.STRING).addScalar("periodName", Hibernate.STRING)
			.addScalar("weekDay", Hibernate.STRING). addScalar("className", Hibernate.STRING).addScalar("subjectName", Hibernate.STRING).
			addScalar("startTime", Hibernate.STRING).addScalar("roomName", Hibernate.STRING).addScalar("subCode",Hibernate.STRING);
			list = query.list();
		}catch (Exception e) {
			throw e;
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.timetable.IViewTeacherWiseTimeTableTransactions#getListDataByQuery(java.lang.String, java.lang.String)
	 */
	public List<Object[]> getListDataByQuery1(String sql_Query) throws Exception {
		Session session= null;
		List<Object[]> list = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery(sql_Query).addScalar("periodId", Hibernate.STRING).addScalar("periodName", Hibernate.STRING)
			.addScalar("weekDay", Hibernate.STRING). addScalar("className", Hibernate.STRING).addScalar("subjectName", Hibernate.STRING).
			addScalar("startTime", Hibernate.STRING).addScalar("roomName", Hibernate.STRING).addScalar("subCode",Hibernate.STRING)
			.addScalar("teacherName",Hibernate.STRING);
			list = query.list();
		}catch (Exception e) {
			throw e;
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.timetable.IViewTeacherWiseTimeTableTransactions#getPeriodLists(java.util.Set)
	 */
	@Override
	public List<Period> getPeriodLists(Set<Integer> periodIdList) throws Exception {
		Session session =null;
		List<Period> periodList = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from Period period where period.isActive=1 and period.id in (:periodIds)"+
						" group by period.id ";
			Query query = session.createQuery(str);
			query.setParameterList("periodIds", periodIdList);
			periodList = query.list();
		}catch (Exception e) {
			throw e;
		}
		return periodList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.timetable.IViewTeacherWiseTimeTableTransactions#getPeriodGroupList(java.util.Set)
	 */
	@Override
	public List<Period> getPeriodGroupList(Set<Integer> periodId) throws Exception {
		Session session =null;
		List<Period> periodList = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from Period period where period.isActive=1 and period.id in (:periodIds)"+
						 " group by period.periodName ";
			Query query = session.createQuery(str);
			query.setParameterList("periodIds", periodId);
			periodList = query.list();
		}catch (Exception e) {
			throw e;
		}
		return periodList;}
	@Override
	public String getHoursCount(String userId) throws Exception {
		Session session =null;
		String periodCount = null;
		try{
			session = HibernateUtil.getSession();
			String str =
					" select count(users_id) as cnt"+
					" from (select "+
					" users.id as users_id"+
					" from tt_users"+
					" inner join users ON users.id = tt_users.user_id"+
					" inner join tt_subject_batch ON tt_subject_batch.id = tt_users.tt_subject_id"+
					" inner join tt_period_week ON tt_period_week.id = tt_subject_batch.tt_period_id"+
					" inner join period ON period.id = tt_period_week.period_id"+
					" group by users.id,period.period_name,tt_period_week.week_day) as class"+
					" where class.users_id="+userId;
			SQLQuery query = session.createSQLQuery(str);
			periodCount = (String) query.uniqueResult().toString();
		}catch (Exception e) {
			throw e;
		}
		return periodCount;
		}
	@Override
	public List<Object[]> getDeptWiseTimeTable(String deptTimetableQuery) throws Exception {
		Session session =null;
		List<Object[]> objList=null;
		try{
			session = HibernateUtil.getSession();
			String str=deptTimetableQuery;
			SQLQuery query = session.createSQLQuery(str).addScalar("periodId", Hibernate.STRING).addScalar("periodName", Hibernate.STRING)
					.addScalar("weekDay", Hibernate.STRING). addScalar("className", Hibernate.STRING).addScalar("subCode",Hibernate.STRING)
					.addScalar("classSchemeId",Hibernate.STRING).addScalar("className",Hibernate.STRING).addScalar("empCode",Hibernate.STRING)
					.addScalar("subTypeId",Hibernate.STRING).addScalar("subTypeName",Hibernate.STRING).addScalar("sem",Hibernate.STRING);
			objList = (List<Object[]>) query.list();
		}catch (Exception e) {
			throw e;
		}
		return objList;
		}
	@Override
	public Map<Integer, String> getDeptList(ViewTeacherWiseTimeTableForm objForm) throws Exception {
		Session session =null;
		List<Department> deptList=null;
		Map<Integer, String> deptMap=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String str="from Department d where d.isActive=1 and d.isAcademic=1";
			Query query = session.createQuery(str);
			deptList = (List<Department>) query.list();
			for (Department department : deptList) {
				deptMap.put(department.getId(), department.getName());
			}
		}catch (Exception e) {
			throw e;
		}
		return deptMap;
		}
}
