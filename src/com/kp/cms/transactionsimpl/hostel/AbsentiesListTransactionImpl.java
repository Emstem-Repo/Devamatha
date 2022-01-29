package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.hostel.HlStudentAttendance;
import com.kp.cms.bo.hostel.HostelExemptionDetailsBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.AbsentiesListForm;
import com.kp.cms.handlers.hostel.AbsentiesListHandler;
import com.kp.cms.transactions.hostel.IAbsentiesListTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AbsentiesListTransactionImpl implements IAbsentiesListTransaction{
	public static volatile AbsentiesListTransactionImpl absentiesListTransactionImpl= null;
	public static AbsentiesListTransactionImpl getInstance() {
		if (absentiesListTransactionImpl == null) {
			absentiesListTransactionImpl = new AbsentiesListTransactionImpl();
			return absentiesListTransactionImpl;
		}
		return absentiesListTransactionImpl;
	}
	
	@Override
	public Map<Integer, String> getHostelMap() throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Map<Integer,String> map=new HashMap<Integer, String>();
		List<HlHostel> hlHostels=null;
		try{
			String HQL="from HlHostel h where h.isActive=1";
			Query query = session.createQuery(HQL);
			hlHostels=(List<HlHostel>)query.list();
			if(hlHostels!=null && !hlHostels.isEmpty()){
				Iterator<HlHostel> iterator=hlHostels.iterator();
				while (iterator.hasNext()) {
					HlHostel hlHostel = (HlHostel) iterator.next();
					map.put(hlHostel.getId(), hlHostel.getName());
				}
			}
			return map;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	@Override
	public List<HlAdmissionBo> getHlAdmissionBos(List<Integer> list)
			throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<HlAdmissionBo> hlAdmissionBos=null;
		try{
			String HQL="from HlAdmissionBo h where h.id in (:list)";
			Query query = session.createQuery(HQL);
			query.setParameterList("list", list);
			hlAdmissionBos=(List<HlAdmissionBo>)query.list();
			return hlAdmissionBos;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	@Override
	public List<Integer> getHlAdmissionBos(String hqlQuery) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<Integer> list=null;
		try{
			Query query = session.createQuery(hqlQuery);
			list=(List<Integer>)query.list();
			return list;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	
	@Override
	public Map<Integer, List<Integer>> getHlAdmissionBosByCourse(String hqlQuery)
			throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Map<Integer, List<Integer>> map=new HashMap<Integer, List<Integer>>();
		List<HlAdmissionBo> hlAdmissionBos=null;
		try{
			Query query = session.createQuery(hqlQuery);
			hlAdmissionBos=(List<HlAdmissionBo>)query.list();
			if(hlAdmissionBos!=null && !hlAdmissionBos.isEmpty()){
				Iterator<HlAdmissionBo> iterator=hlAdmissionBos.iterator();
				while (iterator.hasNext()) {
					List<Integer> list=null;
					HlAdmissionBo hlAdmissionBo = (HlAdmissionBo) iterator.next();
					if(map.containsKey(hlAdmissionBo.getCourseId().getId()) && map!=null && !map.isEmpty()){
						list=map.get(hlAdmissionBo.getCourseId().getId());
						list.add(hlAdmissionBo.getId());
						map.remove(hlAdmissionBo.getCourseId().getId());
						map.put(hlAdmissionBo.getCourseId().getId(), list);
					}else{
						list=new ArrayList<Integer>();
						list.add(hlAdmissionBo.getId());
						map.put(hlAdmissionBo.getCourseId().getId(), list);
					}
				}
			}
			return map;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	@Override
	public Map<Integer, Map<Date, List<Integer>>> getMorningAbsentMapByCourse(
			String hqlQuery,AbsentiesListForm absentiesListForm, String isMorning) throws Exception {
		Map<Integer, Map<Date, List<Integer>>> mrngAbsentMapByCourse=new HashMap<Integer, Map<Date,List<Integer>>>();
		Map<Date, List<Integer>> submap = null;
		List<Integer> presentList = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<HlStudentAttendance> list=null;
		try{
			Query query = session.createQuery(hqlQuery);
			list=(List<HlStudentAttendance>)query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<HlStudentAttendance> iterator=list.iterator();
				while (iterator.hasNext()) {
					List<HlStudentAttendance> list1=null;
					HlStudentAttendance hlStudentAttendance = (HlStudentAttendance) iterator.next();
					if(hlStudentAttendance.getHlAdmissionBo() != null && hlStudentAttendance.getHlAdmissionBo().getCourseId() != null && 
							hlStudentAttendance.getDate() != null){
						if(hlStudentAttendance.getDate().compareTo(CommonUtil.ConvertDateStringToSQLDate(absentiesListForm.getHolidaysFrom()))==0 && absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("Evening")){
							break;
						}
						if(hlStudentAttendance.getDate().compareTo(CommonUtil.ConvertDateStringToSQLDate(absentiesListForm.getHolidaysTo()))==0 && absentiesListForm.getHolidaysToSession().equalsIgnoreCase("Morning")){
							break;
						}
						if(mrngAbsentMapByCourse.containsKey(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId())){
							submap = mrngAbsentMapByCourse.remove(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId());
						}else{
							submap = new HashMap<Date, List<Integer>>();
						}
						if(submap.containsKey(hlStudentAttendance.getDate())){
							presentList = submap.remove(hlStudentAttendance.getDate());
						}else{
							presentList = new ArrayList<Integer>();
						}
						presentList.add(hlStudentAttendance.getHlAdmissionBo().getId());
						submap.put(hlStudentAttendance.getDate(), presentList);
						mrngAbsentMapByCourse.put(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId(), submap);
					}
					
				/*	if(map.containsKey(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId())){
						list1=map.get(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId());
						list1.add(hlStudentAttendance);
						map.remove(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId());
						map.put(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId(), list1);
					}else{
						list1=new ArrayList<HlStudentAttendance>();
						list1.add(hlStudentAttendance);
						map.put(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId(), list1);
					}*/
				}
			}
			/*if(map!=null && !map.isEmpty()){
				Set<Integer> courseIds=map.keySet();
				Iterator<Integer> iterator=courseIds.iterator();
				List<HlStudentAttendance> hlStudentAttendances=null;
				while (iterator.hasNext()) {
					Integer integer = (Integer) iterator.next();
					hlStudentAttendances=map.get(integer);
					Map<Date,List<Integer>> map2=new HashMap<Date, List<Integer>>();
					if(hlStudentAttendances!=null && !hlStudentAttendances.isEmpty()){
						Iterator<HlStudentAttendance> iterator2=hlStudentAttendances.iterator();
						while (iterator2.hasNext()) {
							List<Integer> list2=null;
							HlStudentAttendance hlStudentAttendance = (HlStudentAttendance) iterator2.next();
							if(map.containsKey(hlStudentAttendance.getDate())){
								list2=map2.get(hlStudentAttendance.getDate());
								list2.add(hlStudentAttendance.getHlAdmissionBo().getId());
								map2.remove(hlStudentAttendance.getDate());
								map2.put(hlStudentAttendance.getDate(), list2);
							}else{
								list2=new ArrayList<Integer>();
								list2.add(hlStudentAttendance.getHlAdmissionBo().getId());
								map2.put(hlStudentAttendance.getDate(), list2);
							}
						}
					}
					mrngAbsentMapByCourse.put(integer, map2);
				}
			}*/
			return mrngAbsentMapByCourse;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	@Override
	public Map<Integer, List<HostelHolidaysBo>> getHostelHolidaysByCourse(String hqlQuery) throws Exception {
		Map<Integer, List<HostelHolidaysBo>> map=new HashMap<Integer, List<HostelHolidaysBo>>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<HostelHolidaysBo> holidaysBos=null;
		try{
			Query query = session.createQuery(hqlQuery);
			holidaysBos=query.list();
			if(holidaysBos!=null && !holidaysBos.isEmpty()){
				Iterator<HostelHolidaysBo> iterator=holidaysBos.iterator();
				while (iterator.hasNext()) {
					List<HostelHolidaysBo> list=null;
					HostelHolidaysBo hostelHolidaysBo = (HostelHolidaysBo) iterator	.next();
					if(map.containsKey(hostelHolidaysBo.getCourseId().getId())){
						list=map.get(hostelHolidaysBo.getCourseId().getId());
						list.add(hostelHolidaysBo);
						map.remove(hostelHolidaysBo.getCourseId().getId());
						map.put(hostelHolidaysBo.getCourseId().getId(), list);
					}else{
						list=new ArrayList<HostelHolidaysBo>();
						list.add(hostelHolidaysBo);
						map.put(hostelHolidaysBo.getCourseId().getId(), list);
					}
				}
			}
			return map;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	@Override
	public Map<Integer,Map<Integer,List<HlLeave>>> getHLLeavesByCourse(
			AbsentiesListForm absentiesListForm) throws Exception {
		Map<Integer,Map<Integer,List<HlLeave>>> leaveMap=new HashMap<Integer, Map<Integer,List<HlLeave>>>();
		Map<Integer, List<HlLeave>> map=null;
		List<HlLeave> list=null;
		String date1=absentiesListForm.getHolidaysFrom();
		String date2=absentiesListForm.getHolidaysTo();
		java.sql.Date fromDate=CommonUtil.ConvertStringToSQLDate(date1);
		java.sql.Date toDate=CommonUtil.ConvertStringToSQLDate(date2);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<HlLeave> hlLeaves=null;
		try{
			String HQL="from HlLeave h where h.isActive=1 and h.isApproved=1 and (h.leaveFrom between '"+fromDate+"' and '"+toDate+"'"+
			" OR h.leaveTo between '"+fromDate+"' and '"+toDate+"')";
			Query query = session.createQuery(HQL);
			hlLeaves=query.list();
			if(hlLeaves!=null && !hlLeaves.isEmpty()){
				Iterator<HlLeave> iterator=hlLeaves.iterator();
				while (iterator.hasNext()) {
					
					HlLeave hlLeave = (HlLeave) iterator.next();
					if(hlLeave.getHlAdmissionBo() != null && hlLeave.getHlAdmissionBo().getCourseId() != null ){
						if(leaveMap.containsKey(hlLeave.getHlAdmissionBo().getCourseId().getId())){
							map = leaveMap.remove(hlLeave.getHlAdmissionBo().getCourseId().getId());
						}else{
							map = new HashMap<Integer, List<HlLeave>>();
						}
						if(map.containsKey(hlLeave.getHlAdmissionBo().getId())){
							list = map.remove(hlLeave.getHlAdmissionBo().getId());
						}else{
							list = new ArrayList<HlLeave>();
						}
						list.add(hlLeave);
						map.put(hlLeave.getHlAdmissionBo().getId(), list);
						leaveMap.put(hlLeave.getHlAdmissionBo().getCourseId().getId(), map);
					}
				}
			}
			return leaveMap;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	@Override
	public Map<Integer,HlAdmissionBo> getHlAdmissionMap(String hqlQuery)
			throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Map<Integer, HlAdmissionBo> map=new HashMap<Integer, HlAdmissionBo>();
		List<HlAdmissionBo> hlAdmissionBos=null;
		try{
			Query query = session.createQuery(hqlQuery);
			hlAdmissionBos=(List<HlAdmissionBo>)query.list();
			if(hlAdmissionBos!=null && !hlAdmissionBos.isEmpty()){
				Iterator<HlAdmissionBo> iterator=hlAdmissionBos.iterator();
				while (iterator.hasNext()) {
					HlAdmissionBo hlAdmissionBo = (HlAdmissionBo) iterator.next();
						map.put(hlAdmissionBo.getId(), hlAdmissionBo);
				}
			}
			return map;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}

	@Override
	public Map<Integer, List<Integer>> getPresentMapByCourseNew(
			AbsentiesListForm absentiesListForm) throws Exception {
		Map<Integer, List<Integer>> presentMap=new HashMap<Integer, List<Integer>>();
		List<Integer> list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = getQuery(absentiesListForm); 
			Query query = session.createQuery(hqlQuery);
			List<HlStudentAttendance> hlStudentAttendances=query.list();
			if(hlStudentAttendances!=null && !hlStudentAttendances.isEmpty()){
				Iterator<HlStudentAttendance> iterator=hlStudentAttendances.iterator();
				while (iterator.hasNext()) {
					HlStudentAttendance hlStudentAttendance = (HlStudentAttendance) iterator.next();
					if(hlStudentAttendance.getHlAdmissionBo().getCourseId()!=null){
						if(presentMap.containsKey(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId())){
							list = presentMap.remove(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId());
						}else{
							list=new ArrayList<Integer>();
						}
						list.add(hlStudentAttendance.getHlAdmissionBo().getId());
						presentMap.put(hlStudentAttendance.getHlAdmissionBo().getCourseId().getId(), list);
					}
					
				}
			}
			
		}catch (Exception e) {
			throw new ApplicationException();
		}
		return presentMap;
	}

	/**
	 * @param absentiesListForm
	 * @return
	 */
	private String getQuery(AbsentiesListForm absentiesListForm) {
		String hostelId=absentiesListForm.getHostelId();
		String blockId=absentiesListForm.getBlockId();
		String unitId=absentiesListForm.getUnitId();
		String date1=absentiesListForm.getHolidaysFrom();
		java.sql.Date fromDate=CommonUtil.ConvertStringToSQLDate(date1);
		String query="from HlStudentAttendance attn where attn.isActive=1 and attn.date = '"+fromDate+
		"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
		" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) ";
		if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("Morning")){
			query = query +" and attn.morning is not null";
		}else{
			query = query +" and attn.evening is not null";
		}
		if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty() && unitId!=null && !unitId.isEmpty()){
			query = query +" and attn.hlAdmissionBo.hostelId.id="+hostelId+" and attn.hlAdmissionBo.roomId.hlBlock.id="+blockId+" and attn.hlAdmissionBo.roomId.hlUnit.id="+unitId;
		}else if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty()){
			query = query +" and attn.hlAdmissionBo.hostelId.id="+hostelId+" and attn.hlAdmissionBo.roomId.hlBlock.id="+blockId;
		}else if(hostelId!=null && !hostelId.isEmpty()){
			query = query +" and attn.hlAdmissionBo.hostelId.id="+hostelId;
		}
		return query;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IAbsentiesListTransaction#getLeavesByCourseWise(com.kp.cms.forms.hostel.AbsentiesListForm)
	 */
	@Override
	public Map<Integer, List<Integer>> getLeavesByCourseWise(AbsentiesListForm absentiesListForm) throws Exception {
		Map<Integer, List<Integer>> leaveMap=new HashMap<Integer, List<Integer>>();
		List<Integer> list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			List<HlLeave> bos = session.createQuery("from HlLeave l where '"+CommonUtil.ConvertStringToSQLDate(absentiesListForm.getHolidaysFrom())+"' between l.leaveFrom and l.leaveTo and l.isActive=1 and l.isApproved=1").list();
			if(bos != null){
				Iterator<HlLeave> iterator = bos.iterator();
				while (iterator.hasNext()) {
					HlLeave bo = (HlLeave) iterator.next();
					if(bo.getLeaveFrom() != null && bo.getLeaveTo() != null && bo.getHlAdmissionBo() != null && bo.getHlAdmissionBo().getCourseId() != null){
						
						if(leaveMap.containsKey(bo.getHlAdmissionBo().getCourseId().getId())){
							list = leaveMap.remove(bo.getHlAdmissionBo().getCourseId().getId());
						}else{
							list = new ArrayList<Integer>();
						}
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(CommonUtil.ConvertStringToDate(absentiesListForm.getHolidaysFrom()));
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(bo.getLeaveFrom());
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(bo.getLeaveTo());
						
						if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1 && CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("evening") && bo.getToSession().equalsIgnoreCase("evening")){
								list.add(bo.getHlAdmissionBo().getId());
							}else if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("morning") && bo.getFromSession().equalsIgnoreCase("morning")){
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1){
							if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("morning") && bo.getFromSession().equalsIgnoreCase("morning")){
								list.add(bo.getHlAdmissionBo().getId());
							}else if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("evening") && (bo.getFromSession().equalsIgnoreCase("morning") || bo.getFromSession().equalsIgnoreCase("evening"))){
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("evening") && bo.getToSession().equalsIgnoreCase("evening")){
								list.add(bo.getHlAdmissionBo().getId());
							}else if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("morning") && (bo.getToSession().equalsIgnoreCase("evening") || bo.getToSession().equalsIgnoreCase("morning"))){
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else{
							list.add(bo.getHlAdmissionBo().getId());
						}
						if(!list.isEmpty())
							leaveMap.put(bo.getHlAdmissionBo().getCourseId().getId(), list);
					}
				}
			}
		}catch (Exception e) {
			throw new ApplicationException();
		}
		return leaveMap;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IAbsentiesListTransaction#getHolidaysMap(com.kp.cms.forms.hostel.AbsentiesListForm)
	 */
	@Override
	public Map<Integer, Map<Integer, Map<Integer, List<Integer>>>> getHolidaysMap(
			AbsentiesListForm absentiesListForm) throws Exception {
		Map<Integer, Map<Integer, Map<Integer, List<Integer>>>> map = new HashMap<Integer, Map<Integer,Map<Integer,List<Integer>>>>();
		Map<Integer, Map<Integer, List<Integer>>> hostelMap = null;
		Map<Integer, List<Integer>> blockMap = null;
		List<Integer> unitList = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			List<HostelHolidaysBo> bos = session.createQuery("select h from HostelHolidaysBo h where '"+CommonUtil.ConvertStringToSQLDate(absentiesListForm.getHolidaysFrom())+
					"' between h.holidaysFrom and h.holidaysTo and h.isActive=1").list();
			if(bos != null){
				boolean add = false;
				Iterator<HostelHolidaysBo> iterator = bos.iterator();
				while (iterator.hasNext()) {
					HostelHolidaysBo bo = (HostelHolidaysBo) iterator.next();
					add = false;
					if(bo.getHolidaysFrom() != null && bo.getHolidaysTo() != null && bo.getCourseId() != null){
						if(new java.sql.Date(bo.getHolidaysFrom().getTime()).compareTo(CommonUtil.ConvertStringToSQLDate(absentiesListForm.getHolidaysFrom()))==0 && 
								bo.getHolidaysFromSession().equalsIgnoreCase(absentiesListForm.getHolidaysFromSession())){
								add = true;
							
						}else if(new java.sql.Date(bo.getHolidaysTo().getTime()).compareTo(CommonUtil.ConvertStringToSQLDate(absentiesListForm.getHolidaysFrom()))==0 &&
								bo.getHolidaysToSession().equalsIgnoreCase(absentiesListForm.getHolidaysFromSession())){
								add = true;
						}else if(new java.sql.Date(bo.getHolidaysTo().getTime()).compareTo(CommonUtil.ConvertStringToSQLDate(absentiesListForm.getHolidaysFrom()))>=0
								&& new java.sql.Date(bo.getHolidaysFrom().getTime()).compareTo(CommonUtil.ConvertStringToSQLDate(absentiesListForm.getHolidaysFrom()))<=0){
							add = true;
						}
						
						if(add && bo.getHostelId() != null && bo.getBlockId() != null && bo.getUnitId() != null){
							if(map.containsKey(bo.getCourseId().getId())){
								hostelMap = map.remove(bo.getCourseId().getId());
							}else{
								hostelMap = new HashMap<Integer, Map<Integer,List<Integer>>>();
							}
							if(hostelMap.containsKey(bo.getHostelId().getId())){
								blockMap = hostelMap.remove(bo.getHostelId().getId());
							}else{
								blockMap = new HashMap<Integer, List<Integer>>();
							}
							if(blockMap.containsKey(bo.getBlockId().getId())){
								unitList = blockMap.remove(bo.getBlockId().getId());
							}else{
								unitList = new ArrayList<Integer>();
							}
							unitList.add(bo.getUnitId().getId());
							blockMap.put(bo.getBlockId().getId(), unitList);
							hostelMap.put(bo.getHostelId().getId(), blockMap);
							map.put(bo.getCourseId().getId(), hostelMap);
						}
					}
				}
			}
		}catch (Exception e) {
			if(session != null)
				session.close();
		}finally {
			if (session != null) {
				session.flush();
			}
		}
		return map;
	}
	
	
// /* code added by chandra for if student having hostel exemption then remove that student from absenttees list
	
	public Map<Integer, List<Integer>> getExemptionListByCourseWise(AbsentiesListForm absentiesListForm) throws Exception {
		Map<Integer, List<Integer>> exempMap=new HashMap<Integer, List<Integer>>();
		List<Integer> list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			List<HostelExemptionDetailsBo> bos = session.createQuery("from HostelExemptionDetailsBo hd where '"+CommonUtil.ConvertStringToSQLDate(absentiesListForm.getHolidaysFrom())+ "' between hd.hostelExemptionBo.fromDate and hd.hostelExemptionBo.toDate and hd.isActive=1 and  hd.hostelExemptionBo.isActive=1").list();
			if(bos != null){
				Iterator<HostelExemptionDetailsBo> iterator = bos.iterator();
				while (iterator.hasNext()) {
					HostelExemptionDetailsBo bo = (HostelExemptionDetailsBo) iterator.next();
					if(bo.getHostelExemptionBo().getFromDate() != null && bo.getHostelExemptionBo().getToDate() != null && bo.getHlAdmissionBo() != null && bo.getHlAdmissionBo().getCourseId() != null){
						
						if(exempMap.containsKey(bo.getHlAdmissionBo().getCourseId().getId())){
							list = exempMap.remove(bo.getHlAdmissionBo().getCourseId().getId());
						}else{
							list = new ArrayList<Integer>();
						}
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(CommonUtil.ConvertStringToDate(absentiesListForm.getHolidaysFrom()));
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(bo.getHostelExemptionBo().getFromDate());
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(bo.getHostelExemptionBo().getToDate());
						
						if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1 && CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("evening") && bo.getHostelExemptionBo().getToSession().equalsIgnoreCase("evening")){
								if(!list.contains(bo.getHlAdmissionBo().getId()))
									list.add(bo.getHlAdmissionBo().getId());
									
							}else if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("morning") && bo.getHostelExemptionBo().getFromSession().equalsIgnoreCase("morning")){
								if(!list.contains(bo.getHlAdmissionBo().getId()))
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1){
							if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("morning") && bo.getHostelExemptionBo().getFromSession().equalsIgnoreCase("morning")){
								if(!list.contains(bo.getHlAdmissionBo().getId()))
								list.add(bo.getHlAdmissionBo().getId());
							}else if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("evening") && (bo.getHostelExemptionBo().getFromSession().equalsIgnoreCase("morning") || bo.getHostelExemptionBo().getFromSession().equalsIgnoreCase("evening"))){
								if(!list.contains(bo.getHlAdmissionBo().getId()))
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("evening") && bo.getHostelExemptionBo().getToSession().equalsIgnoreCase("evening")){
								if(!list.contains(bo.getHlAdmissionBo().getId()))
								list.add(bo.getHlAdmissionBo().getId());
							}else if(absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("morning") && (bo.getHostelExemptionBo().getToSession().equalsIgnoreCase("evening") || bo.getHostelExemptionBo().getToSession().equalsIgnoreCase("morning"))){
								if(!list.contains(bo.getHlAdmissionBo().getId()))
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else{
							if(!list.contains(bo.getHlAdmissionBo().getId()))
							list.add(bo.getHlAdmissionBo().getId());
						}
						if(!list.isEmpty())
							exempMap.put(bo.getHlAdmissionBo().getCourseId().getId(), list);
					}
				}
			}
		}catch (Exception e) {
			throw new ApplicationException();
		}
		return exempMap;
	}
	
	public List<Integer> getSundayMorningExemptionStudentsList(List<Integer> admIdList)throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<Integer> list=null;
		try{if(admIdList !=null && !admIdList.isEmpty()){
				String HQL="select h.id from HlAdmissionBo h where h.roomId.hlUnit.punchExepSundaySession=1 and h.isActive=1  and h.id in (:list)";
				Query query = session.createQuery(HQL);
				query.setParameterList("list", admIdList);
				list=(List<Integer>)query.list();
		}
			return list;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 

	}
	// code added by chandra */
	
}
