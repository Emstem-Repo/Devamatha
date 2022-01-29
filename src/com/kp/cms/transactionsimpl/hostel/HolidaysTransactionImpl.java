package com.kp.cms.transactionsimpl.hostel;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HolidaysForm;
import com.kp.cms.transactions.hostel.IHolidaysTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class HolidaysTransactionImpl implements IHolidaysTransaction {
	/**
	 * instance()
	 */
	public static volatile HolidaysTransactionImpl holidaysTransactionImpl = null;

	public static HolidaysTransactionImpl getInstance() {
		if (holidaysTransactionImpl == null) {
			holidaysTransactionImpl = new HolidaysTransactionImpl();
		}
		return holidaysTransactionImpl;
	}
	public Map<String,String> getProgramsMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Program d where d.isActive=1 ");
			List<Program> list=query.list();
			if(list!=null){
				Iterator<Program> iterator=list.iterator();
				while(iterator.hasNext()){
					Program program=iterator.next();
					map.put(String.valueOf(program.getId()),program.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
			return map;
	}
	@Override
	public List<Integer> getCourseIdList(int programId) throws Exception {
		Session session=null;
		List<Integer> list=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("select c.id from Course c where c.isActive=1 and c.program.id="+programId);
			list=query.list();
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		return list;
	}
	public boolean saveHostelHolidays(List<HostelHolidaysBo> boList) throws Exception{
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session  = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if(boList!=null && !boList.isEmpty()){
				Iterator<HostelHolidaysBo> iterator = boList.iterator();
				while (iterator.hasNext()) {
					HostelHolidaysBo hostelHolidaysBo = (HostelHolidaysBo) iterator.next();
					session.save(hostelHolidaysBo);
				}
			}
			tx.commit();
			isAdded = true;
			}catch (Exception e) {
				isAdded =false;
				throw new ApplicationException(e);
			}
			finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
		return isAdded;
		}
	@Override
	public List<HostelHolidaysBo> getHostelHolidaysList() throws Exception {
		Session session=null;
		List<HostelHolidaysBo> hList=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HostelHolidaysBo d where d.isActive=1 ");
			 hList=query.list();
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		return hList;
	}
	@Override
	public boolean deleteHolidaysDetails(HolidaysForm holidaysForm)throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			HostelHolidaysBo holidaysBo = (HostelHolidaysBo)session.get(HostelHolidaysBo.class, holidaysForm.getId());
			holidaysBo.setLastModifiedDate(new Date());
			holidaysBo.setModifiedBy(holidaysForm.getUserId());
			holidaysBo.setIsActive(false);
			session.update(holidaysBo);
			tx.commit();
			isDeleted = true;
		}catch (Exception e) {
			isDeleted = false;
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
				}
			}
		return isDeleted;
	}
	@Override
	public HostelHolidaysBo getHostelHolidaysDetails(int id) throws Exception {
		Session session=null;
		HostelHolidaysBo hostelHolidaysBo=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HostelHolidaysBo d where d.isActive=1 and d.id="+id);
			hostelHolidaysBo=(HostelHolidaysBo)query.uniqueResult();
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		return hostelHolidaysBo;
	}
	@Override
	public Map<Integer, String> getCourseMap(String id) throws Exception {
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Course d where d.isActive=1 and d.program.id="+id+"and d.onlyForApplication=0");
			List<Course> list=query.list();
			if(list!=null){
				Iterator<Course> iterator=list.iterator();
				while(iterator.hasNext()){
					Course program=iterator.next();
					map.put(program.getId(),program.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
			return map;
	}
	@Override
	public boolean updateHostelHolidaysDetails(HostelHolidaysBo holidaysBo)throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			 transaction=session.beginTransaction();
				transaction.begin();
					session.update(holidaysBo);
				transaction.commit();
				flag=true;
		}catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}finally{
			if(session !=null){
				session.flush();
				}
		}
		return flag;
	}
	@Override
	public List<Integer> getProgIdList() throws Exception {

		Session session=null;
		List<Integer> list=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("select c.id from Program c where isActive=1");
			list=query.list();
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		return list;
	
	}
	public Map<Integer,String> getCoursemap() throws Exception {
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Course d where d.isActive=1 and d.onlyForApplication=0");
			List<Course> list=query.list();
			if(list!=null){
				Iterator<Course> iterator=list.iterator();
				while(iterator.hasNext()){
					Course course=iterator.next();
					map.put(course.getId(),course.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
			return map;
	}
	@Override
	public List<HostelHolidaysBo> checkDuplicate(HolidaysForm holidaysForm)
			throws Exception {
		String date1=holidaysForm.getHolidaysFrom();
		String date2=holidaysForm.getHolidaysTo();
		java.sql.Date fromDate=CommonUtil.ConvertStringToSQLDate(date1);
		java.sql.Date toDate=CommonUtil.ConvertStringToSQLDate(date2);
		String courseId[]=holidaysForm.getCoursesId();
		int cid=Integer.parseInt(courseId[0]);
		String progId[]=holidaysForm.getProgramsId();
		int pid=Integer.parseInt(progId[0]);
		String holidayType=holidaysForm.getHolidaysOrVacation();
		int hostelId=Integer.parseInt(holidaysForm.getHostelId());
		int blockId=Integer.parseInt(holidaysForm.getBlockId());
		int unitId=Integer.parseInt(holidaysForm.getUnitId());
		Session session=null;
		List<HostelHolidaysBo> hList=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HostelHolidaysBo d where d.isActive=1 and d.programId.id="+pid +" and d.hostelId="+hostelId+" and d.blockId="+blockId+" and d.unitId="+unitId+
					" and d.courseId.id="+cid+" and d.holidaysOrVaction='"+holidayType+"' and (d.holidaysFrom between '"+fromDate+"' and '"+toDate+"'" +
					"or d.holidaysTo between '"+fromDate+"' and '"+toDate+"')");
			 hList=query.list();
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		return hList;
	}
}
