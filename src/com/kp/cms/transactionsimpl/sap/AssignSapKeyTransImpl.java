package com.kp.cms.transactionsimpl.sap;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.hostel.HlStudentAttendance;
import com.kp.cms.bo.sap.SapKeysBo;
import com.kp.cms.bo.sap.SapRegistration;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.sap.IAssignSapKeyTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AssignSapKeyTransImpl implements IAssignSapKeyTransaction{
	public static volatile AssignSapKeyTransImpl assignSapKeyTransImpl = null;
	private static Log log = LogFactory.getLog(AssignSapKeyTransImpl.class);
	public static AssignSapKeyTransImpl getInstance() {
		if (assignSapKeyTransImpl == null) {
			assignSapKeyTransImpl = new AssignSapKeyTransImpl();
			return assignSapKeyTransImpl;
		}
		return assignSapKeyTransImpl;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SapRegistration> getRegisteredStudentsForSap(
			Date convertStringToSQLDate, Date convertStringToSQLDate2,
			String status,String classId) throws Exception {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL=null;
			if(status.equalsIgnoreCase("Pending")){
				HQL="from SapRegistration h where h.isActive=1 and h.sapKeyId is null";
			}else{
				HQL="from SapRegistration h where h.isActive=1 and h.sapKeyId is not null";
			}
			if(convertStringToSQLDate!=null && convertStringToSQLDate2!=null){
				HQL=HQL+" and date(h.registeredDate) between '"+convertStringToSQLDate+"' and '"+convertStringToSQLDate2+"'";
			}else if(convertStringToSQLDate!=null){
				HQL=HQL+" and date(h.registeredDate) >= '"+convertStringToSQLDate+"'";
			}else if(convertStringToSQLDate2!=null){
				HQL=HQL+" and date(h.registeredDate) <= '"+convertStringToSQLDate2+"'";
			}
			if(classId!=null && !classId.isEmpty()){
				HQL=HQL+" and h.stdId.classSchemewise.classes.id="+Integer.parseInt(classId);
			}
			Query query = session.createQuery(HQL);
			List<SapRegistration> list=query.list();
			return list;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	@Override
	public Map<Integer,List<Integer>> getKeys() throws Exception {
		Session session = null;
		Map<Integer,List<Integer>> map=new HashMap<Integer, List<Integer>>();
		try{
			session = HibernateUtil.getSession();
			String sqlQuery="select sap_keys.work_location_id , sap_keys.id from sap_keys " +
					"	left join sap_registration on sap_registration.sap_keys_id = sap_keys.id and sap_registration.is_active=1" +
					"	where sap_keys.is_active=1 and sap_registration.id is null";
			Query query = session.createSQLQuery(sqlQuery);
			List<Object[]> list=query.list();
			if(list != null){
				List<Integer> list1=null;
				Iterator<Object[]> iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[0]!=null && objects[0].toString()!=null){
						if(map.containsKey(Integer.parseInt(objects[0].toString()))){
							list1=map.get(Integer.parseInt(objects[0].toString()));
						}else{
							list1=new ArrayList<Integer>();
						}
						list1.add(Integer.parseInt(objects[1].toString()));
						map.put(Integer.parseInt(objects[0].toString()), list1);
					}
				}
			}
			return map;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
	@Override
	public boolean updateSapRegistration(List<SapRegistration> sapRegistrations)
			throws Exception {
				Session session = null;
				Transaction tx = null;
				boolean result = false;
				try {
					session = HibernateUtil.getSession();
					tx = session.beginTransaction();
					tx.begin();
					Iterator<SapRegistration> iterator=sapRegistrations.iterator();
					while (iterator.hasNext()) {
						SapRegistration sapRegistration = (SapRegistration) iterator.next();
						session.update(sapRegistration);
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
	public List<SapRegistration> getStudentsWhoHadUpdatedSapKeys(
			List<Integer> updatedStudents) throws Exception {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="from SapRegistration h where h.isActive=1 and h.sapKeyId is not null and h.id in (:list)";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", updatedStudents);
			List<SapRegistration> list=(List<SapRegistration>)query.list();
			return list;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
	@Override
	public Map<Integer, SapRegistration> getSapRegistration() throws Exception {
		Session session = null;
		Map<Integer, SapRegistration> map=new HashMap<Integer, SapRegistration>();
		try{
			session = HibernateUtil.getSession();
			String HQL="from SapRegistration h where h.isActive=1";
			Query query = session.createQuery(HQL);
			List<SapRegistration> list=query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<SapRegistration> iterator=list.iterator();
				while (iterator.hasNext()) {
					SapRegistration sapRegistration = (SapRegistration) iterator.next();
					map.put(sapRegistration.getId(), sapRegistration);
				}
			}
			return map;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	
	}
	@Override
	public List<Integer> getAdminApplIds() throws Exception {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String HQL="select adm.id from AdmAppln adm where adm.courseBySelectedCourseId in (select c.id from Course c where (c.program in ( 4, 74, 81, 82) or c.id=1))";
			Query query = session.createQuery(HQL);
			List<Integer> list=query.list();
			return list;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
	@Override
	public Map<Integer, String> getclassMap() throws Exception {
		Session session = null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String sqlQuery="select distinct classes.id, classes.name from sap_registration" +
					" inner join student on sap_registration.student_id = student.id" +
					" inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id" +
					" inner join classes ON class_schemewise.class_id = classes.id" +
					" order by classes.name";
			Query query = session.createSQLQuery(sqlQuery);
			List<Object[]> list=query.list();
			if(list != null){
				Iterator<Object[]> iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[0]!=null && objects[0].toString()!=null){
						map.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
					}
				}
			}
			return map;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}

}
