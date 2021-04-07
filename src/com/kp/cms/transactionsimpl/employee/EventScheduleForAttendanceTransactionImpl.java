package com.kp.cms.transactionsimpl.employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EventLocation;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.TTPeriodWeek;
import com.kp.cms.bo.employee.EventLoactionBiometricDetailsBo;
import com.kp.cms.bo.employee.EventScheduleForAttendanceBo;
import com.kp.cms.bo.employee.EventScheduleStaffAttendanceBo;
import com.kp.cms.bo.employee.EventScheduleStudentAttendanceBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.employee.EventScheduleForAttendanceForm;
import com.kp.cms.transactions.employee.IEventScheduleForAttendanceTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class EventScheduleForAttendanceTransactionImpl implements IEventScheduleForAttendanceTransaction{
	private static final Log log = LogFactory.getLog(EventScheduleForAttendanceTransactionImpl.class);
	public static volatile EventScheduleForAttendanceTransactionImpl biometric=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static EventScheduleForAttendanceTransactionImpl getInstance(){
		if(biometric==null){
			biometric= new EventScheduleForAttendanceTransactionImpl();}
		return biometric;
	}
	/* getting event location
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEventScheduleForAttendanceTransaction#getEventLocationData()
	 */
	public Map<String, String> getEventLocationData() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EventLocation e where e.isActive=1");
			List<EventLocation> list=query.list();
			if(list!=null){
				Iterator<EventLocation> iterator=list.iterator();
				while(iterator.hasNext()){
					EventLocation eventLocation=iterator.next();
					if(eventLocation.getId()!=0 && eventLocation.getName()!=null && !eventLocation.getName().isEmpty())
					map.put(String.valueOf(eventLocation.getId()),eventLocation.getName());
				}
			}
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEventScheduleForAttendanceTransaction#addStudentOrStaffData(com.kp.cms.bo.employee.EventScheduleForAttendanceBo, com.kp.cms.forms.employee.EventScheduleForAttendanceForm)
	 */
	public boolean addStudentOrStaffData(EventScheduleForAttendanceBo bo,EventScheduleForAttendanceForm eventScheduleform)
	throws Exception {
			Session session=null;
			Transaction transaction=null;
			boolean isAdded=false;
			boolean isDuplicate=false;
			boolean flag=false;
			String dupClasses="Duplicate entry found for the following Classes please check and enter :";
			String dupDpartments="Duplicate entry found for the following Departments please check and enter :";
			try{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				transaction.begin();
				//checking the duplicate data by passing the event location and event date
				String quer="from EventScheduleForAttendanceBo bo where bo.eventLocation.id=:eventLocationId  and bo.eventDate=:eventDate and  bo.isActive=1";
				Query query=session.createQuery(quer);
				query.setInteger("eventLocationId", bo.getEventLocation().getId());
				query.setDate("eventDate", bo.getEventDate());
				List<EventScheduleForAttendanceBo> list=query.list();
				if(list !=null && !list.isEmpty()){
				// checking duplicate class entry for student by passing FromTime
				if(eventScheduleform.getType().equalsIgnoreCase("Student")){	
				Set<EventScheduleStudentAttendanceBo> EventScheduleStudentSet=bo.getEventScheduleStudentAttendanceBo();
				if(EventScheduleStudentSet !=null && !EventScheduleStudentSet.isEmpty()){
					Iterator<EventScheduleStudentAttendanceBo> studentItr=EventScheduleStudentSet.iterator();
					while (studentItr.hasNext()) {
						EventScheduleStudentAttendanceBo currentStudentBo= studentItr .next();
						int currentcalssId=currentStudentBo.getClasses().getId();
							Iterator<EventScheduleForAttendanceBo> iterator=list.iterator();
								while(iterator.hasNext()){
									EventScheduleForAttendanceBo eventScheduleBo=iterator.next();
									String pattern = "HH:mm";
									SimpleDateFormat sdf = new SimpleDateFormat(pattern);
									String fromtime=eventScheduleBo.getEventTimeFrom();
									 fromtime= fromtime.substring(0, 5);
									
									
									String totime=eventScheduleBo.getEventTimeTo();
									 totime= totime.substring(0, 5);
									
									
									String currentFromTime=bo.getEventTimeFrom();
									currentFromTime= currentFromTime.substring(0, 5);
									
									
									Date fromTime=sdf.parse(fromtime);
									Date toTime=sdf.parse(totime);
									Date TimeFrom=sdf.parse(currentFromTime);
									
									Set<EventScheduleStudentAttendanceBo> previousStudentDetailSet=eventScheduleBo.getEventScheduleStudentAttendanceBo();
									if(previousStudentDetailSet !=null && !previousStudentDetailSet.isEmpty()){
										Iterator<EventScheduleStudentAttendanceBo> studentitr=previousStudentDetailSet.iterator();
										while (studentitr.hasNext()) {
											EventScheduleStudentAttendanceBo previousStudentBo= studentitr .next();
											int previousCalssId=previousStudentBo.getClasses().getId();
									
												if((fromTime.compareTo(TimeFrom)<=0) && (TimeFrom.compareTo(toTime)<= 0) && currentcalssId==previousCalssId){
														isDuplicate=true;
														String str="from Classes c where c.isActive=1  and c.id="+currentcalssId;
														Query query1=session.createQuery(str);
														Classes classes=(Classes)query1.uniqueResult();
														if(classes !=null ){
															dupClasses=dupClasses + classes.getName()+",";
															flag=true;
														}
													}
										}
									}
								}
						}
				  }
					if(flag){
						if(dupClasses!=null && !dupClasses.isEmpty()){
							dupClasses = dupClasses.substring(0, (dupClasses.length() - 1));
							}
						eventScheduleform.setDupClasses(dupClasses);
						eventScheduleform.setFlag(true);
					}
				}//checking duplicate department entry for staff by passing FromTime
				else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
					
					Set<EventScheduleStaffAttendanceBo> EventScheduleStaffSet=bo.getEventScheduleStaffAttendanceBo();
					if(EventScheduleStaffSet !=null && !EventScheduleStaffSet.isEmpty()){
						Iterator<EventScheduleStaffAttendanceBo> studentItr=EventScheduleStaffSet.iterator();
						while (studentItr.hasNext()) {
							EventScheduleStaffAttendanceBo currentStaffBo= studentItr .next();
							int currentDeptId=currentStaffBo.getDepartment().getId();
								Iterator<EventScheduleForAttendanceBo> iterator=list.iterator();
									while(iterator.hasNext()){
										EventScheduleForAttendanceBo eventScheduleBo=iterator.next();
										String pattern = "HH:mm";
										SimpleDateFormat sdf = new SimpleDateFormat(pattern);
										String fromtime=eventScheduleBo.getEventTimeFrom();
										 fromtime= fromtime.substring(0, 5);
										
										 String totime=eventScheduleBo.getEventTimeTo();
										 totime= totime.substring(0, 5);
										
										
										String currentFromTime=bo.getEventTimeFrom();
										currentFromTime= currentFromTime.substring(0, 5);
										
										
										Date fromTime=sdf.parse(fromtime);
										Date toTime=sdf.parse(totime);
										Date TimeFrom=sdf.parse(currentFromTime);
										
										Set<EventScheduleStaffAttendanceBo> previousStaffDetailSet=eventScheduleBo.getEventScheduleStaffAttendanceBo();
										if(previousStaffDetailSet !=null && !previousStaffDetailSet.isEmpty()){
											Iterator<EventScheduleStaffAttendanceBo> studentitr=previousStaffDetailSet.iterator();
											while (studentitr.hasNext()) {
												EventScheduleStaffAttendanceBo previousStaffBo= studentitr .next();
												int previousDeptId=previousStaffBo.getDepartment().getId();
										
													if((fromTime.compareTo(TimeFrom)<=0) && (TimeFrom.compareTo(toTime)<= 0) && currentDeptId==previousDeptId){
															isDuplicate=true;
															String str=" from Department d where isActive=1 and d.id="+currentDeptId;
															Query query1=session.createQuery(str);
															Department dept=(Department)query1.uniqueResult();
															if(dept !=null ){
																dupDpartments=dupDpartments + dept.getName()+",";
																flag=true;
															}
														}
													}
												}
											}
										}
									}
						if(flag){
							if(dupDpartments!=null && !dupDpartments.isEmpty()){
								dupDpartments = dupDpartments.substring(0, (dupDpartments.length() - 1));
								}
							eventScheduleform.setDupDepartments(dupDpartments);
							eventScheduleform.setFlag1(true);
						}
					}
				}
				//if duplicate data not found save student or staff data
				if(bo !=null && !isDuplicate){
					
					session.save(bo);
					transaction.commit();
					isAdded=true;
					
				}
				
			}catch(Exception exception){
				if (transaction != null)
					transaction.rollback();
				log.debug("Error during saving data...", exception);
			}
			finally{
				session.flush();
				session.close();
			}
			return isAdded;

		}
	
	/* getting period id by passing period id and class id
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEventScheduleForAttendanceTransaction#getPeriodIdByClassIdAndPeriodId(java.lang.String, java.lang.Integer)
	 */
	public int getPeriodIdByClassIdAndPeriodId(String PeriodId,Integer SchemewiseId) throws Exception {
		log.debug("Txn Impl : Entering getClassNamesByIds ");
		Session session = null;
		Integer  id=null;
		try {
			 session = HibernateUtil.getSession();
					
			 Query query = session.createQuery("select p.id" +
			 		" from Period p where p.periodName in (select p1.periodName from Period p1 where p1.id=("+Integer.parseInt(PeriodId)+"))" +
			 		" and p.isActive=1 and p.classSchemewise.id =("+SchemewiseId+")");
			   id=(Integer) query.uniqueResult();
			if(id!=null)
			return id;
			else 
			return	0;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getClassNamesByIds with Exception");
			 throw e;
		 }
	}
	
	
	public int getClassIdByClassSchemewiseID(Integer classSchemewiseid) throws Exception {
		log.debug("Txn Impl : Entering getClassNamesByIds ");
		Session session = null;
		Integer  id=0;
		int classSchemewiseId=0;
		
		try {
			 session = HibernateUtil.getSession();
			 if(classSchemewiseid !=null){
				 classSchemewiseId=classSchemewiseid.intValue(); 	
			 Query query = session.createQuery("select c.classes.id from ClassSchemewise c where  c.classes.isActive=1  and c.id="+classSchemewiseId);
			   id=(Integer) query.uniqueResult();
			 }
			return id;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getClassNamesByIds with Exception");
			 throw e;
		 }
	}
	
	
	public List<EventScheduleForAttendanceBo> getstudentAndStaffData() throws Exception {
		Session session=null;
		List<EventScheduleForAttendanceBo> list=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EventScheduleForAttendanceBo e where e.isActive=1");
			 list=query.list();
			
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return list;
	}
	
	public EventScheduleForAttendanceBo getEventSchAttDetailsById(int id) throws Exception {
		Session session=null;
		EventScheduleForAttendanceBo eventSchedule=null;
		try{
			session=HibernateUtil.getSession();
			String str="from EventScheduleForAttendanceBo eventSchAtt where eventSchAtt.isActive=1 and eventSchAtt.id="+id;
			Query query=session.createQuery(str);
			eventSchedule=(EventScheduleForAttendanceBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting EventLoactionBiometricDetailsBo by id..." , e);
			session.flush();
			session.close();
		}
		return eventSchedule;
	
	}
	
	
	public int getPeriodNameByperiodId(int periodId) throws Exception {
		log.debug("Txn Impl : Entering getClassIdByClassSchemewiseID ");
		Session session = null;
		int periodid=0;
		
		try {
			 session = HibernateUtil.getSession();
			 if(periodId !=0){
			 Query query = session.createQuery("select p.id from Period p where p.isActive=1 and p.id="+periodId);
			 periodid=(Integer) query.uniqueResult();
			 }
			return periodid;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getClassIdByClassSchemewiseID with Exception");
			 throw e;
		 }
	}
	
	public boolean dupcheckingForStudentOrStaffData(EventScheduleForAttendanceBo bo,EventScheduleForAttendanceForm eventScheduleform)
	throws Exception {
			Session session=null;
			Transaction transaction=null;
			boolean isAdded=false;
			boolean isDuplicate=false;
			boolean flag=false;
			String dupClasses="Duplicate entry found for the following Classes please check and enter :";
			String dupDpartments="Duplicate entry found for the following Departments please check and enter :";
			
			try{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				transaction.begin();
				//checking the duplicate data by passing the event location and event date
				
			if(((eventScheduleform.getEventDate()!=null)&&(!eventScheduleform.getEventDate().isEmpty()))&&((eventScheduleform.getOldEventDate()!=null) &&(!eventScheduleform.getOldEventDate().isEmpty()))&&(!eventScheduleform.getEventDate().equalsIgnoreCase(eventScheduleform.getOldEventDate()))){
				
				String quer="from EventScheduleForAttendanceBo bo where bo.eventLocation.id=:eventLocationId  and bo.eventDate=:eventDate and  bo.isActive=1";
				Query query=session.createQuery(quer);
				query.setInteger("eventLocationId", bo.getEventLocation().getId());
				query.setDate("eventDate", bo.getEventDate());
				List<EventScheduleForAttendanceBo> list=query.list();
				if(list !=null && !list.isEmpty()){
				// checking duplicate class entry for student by passing FromTime
				if(eventScheduleform.getType().equalsIgnoreCase("Student")){	
				Set<EventScheduleStudentAttendanceBo> EventScheduleStudentSet=bo.getEventScheduleStudentAttendanceBo();
				if(EventScheduleStudentSet !=null && !EventScheduleStudentSet.isEmpty()){
					Iterator<EventScheduleStudentAttendanceBo> studentItr=EventScheduleStudentSet.iterator();
					while (studentItr.hasNext()) {
						EventScheduleStudentAttendanceBo currentStudentBo= studentItr .next();
						int currentcalssId=currentStudentBo.getClasses().getId();
							Iterator<EventScheduleForAttendanceBo> iterator=list.iterator();
								while(iterator.hasNext()){
									EventScheduleForAttendanceBo eventScheduleBo=iterator.next();
									String pattern = "HH:mm";
									SimpleDateFormat sdf = new SimpleDateFormat(pattern);
									String fromtime=eventScheduleBo.getEventTimeFrom();
									 fromtime= fromtime.substring(0, 5);
									
									
									String totime=eventScheduleBo.getEventTimeTo();
									 totime= totime.substring(0, 5);
									
									
									String currentFromTime=bo.getEventTimeFrom();
									currentFromTime= currentFromTime.substring(0, 5);
									
									
									Date fromTime=sdf.parse(fromtime);
									Date toTime=sdf.parse(totime);
									Date TimeFrom=sdf.parse(currentFromTime);
									
									Set<EventScheduleStudentAttendanceBo> previousStudentDetailSet=eventScheduleBo.getEventScheduleStudentAttendanceBo();
									if(previousStudentDetailSet !=null && !previousStudentDetailSet.isEmpty()){
										Iterator<EventScheduleStudentAttendanceBo> studentitr=previousStudentDetailSet.iterator();
										while (studentitr.hasNext()) {
											EventScheduleStudentAttendanceBo previousStudentBo= studentitr .next();
											if(previousStudentBo.getIsActive()){
											int previousCalssId=previousStudentBo.getClasses().getId();
									
												if((fromTime.compareTo(TimeFrom)<=0) && (TimeFrom.compareTo(toTime)<= 0) && currentcalssId==previousCalssId){
														isDuplicate=true;
														String str=" from Classes c where c.isActive=1  and c.id="+currentcalssId;
														Query query1=session.createQuery(str);
														Classes classes=(Classes)query1.uniqueResult();
														if(classes !=null ){
															dupClasses=dupClasses + classes.getName()+",";
															flag=true;
														}
													}
											}
										}
									}
								}
						}
				  }
				
					if(flag){
						if(dupClasses!=null && !dupClasses.isEmpty()){
							dupClasses = dupClasses.substring(0, (dupClasses.length() - 1));
							}
						eventScheduleform.setDupClasses(dupClasses);
						eventScheduleform.setFlag(true);
					}
				}//checking duplicate department entry for staff by passing FromTime
				else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
					
					Set<EventScheduleStaffAttendanceBo> EventScheduleStaffSet=bo.getEventScheduleStaffAttendanceBo();
					if(EventScheduleStaffSet !=null && !EventScheduleStaffSet.isEmpty()){
						Iterator<EventScheduleStaffAttendanceBo> studentItr=EventScheduleStaffSet.iterator();
						while (studentItr.hasNext()) {
							EventScheduleStaffAttendanceBo currentStaffBo= studentItr .next();
							int currentDeptId=currentStaffBo.getDepartment().getId();
								Iterator<EventScheduleForAttendanceBo> iterator=list.iterator();
									while(iterator.hasNext()){
										EventScheduleForAttendanceBo eventScheduleBo=iterator.next();
										String pattern = "HH:mm";
										SimpleDateFormat sdf = new SimpleDateFormat(pattern);
										String fromtime=eventScheduleBo.getEventTimeFrom();
										 fromtime= fromtime.substring(0, 5);
										
										 String totime=eventScheduleBo.getEventTimeTo();
										 totime= totime.substring(0, 5);
										
										
										String currentFromTime=bo.getEventTimeFrom();
										currentFromTime= currentFromTime.substring(0, 5);
										
										
										Date fromTime=sdf.parse(fromtime);
										Date toTime=sdf.parse(totime);
										Date TimeFrom=sdf.parse(currentFromTime);
										
										Set<EventScheduleStaffAttendanceBo> previousStaffDetailSet=eventScheduleBo.getEventScheduleStaffAttendanceBo();
										if(previousStaffDetailSet !=null && !previousStaffDetailSet.isEmpty()){
											Iterator<EventScheduleStaffAttendanceBo> studentitr=previousStaffDetailSet.iterator();
											while (studentitr.hasNext()) {
												EventScheduleStaffAttendanceBo previousStaffBo= studentitr .next();
												if(previousStaffBo.getIsActive()){
												int previousDeptId=previousStaffBo.getDepartment().getId();
										
													if((fromTime.compareTo(TimeFrom)<=0) && (TimeFrom.compareTo(toTime)<= 0) && currentDeptId==previousDeptId){
															isDuplicate=true;
															String str=" from Department d where isActive=1 and d.id="+currentDeptId;
															Query query1=session.createQuery(str);
															Department dept=(Department)query1.uniqueResult();
															if(dept !=null ){
																dupDpartments=dupDpartments + dept.getName()+",";
																flag=true;
															}
														}
													}
												}
												}
											}
										}
									}
					
						if(flag){
							if(dupDpartments!=null && !dupDpartments.isEmpty()){
								dupDpartments = dupDpartments.substring(0, (dupDpartments.length() - 1));
								}
							eventScheduleform.setDupDepartments(dupDpartments);
							eventScheduleform.setFlag1(true);
						}
					}
				}
				//if duplicate data not found save student or staff data
				if(isDuplicate){
					isAdded=true;
				}
			
			
			}else{
				
				String quer="from EventScheduleForAttendanceBo bo where bo.eventLocation.id=:eventLocationId  and bo.eventDate=:eventDate and  bo.isActive=1";
				Query query=session.createQuery(quer);
				query.setInteger("eventLocationId", bo.getEventLocation().getId());
				query.setDate("eventDate", bo.getEventDate());
				List<EventScheduleForAttendanceBo> list=query.list();
				if(list !=null && !list.isEmpty()){
				// checking duplicate class entry for student by passing FromTime
				if(eventScheduleform.getType().equalsIgnoreCase("Student")){	
				Set<EventScheduleStudentAttendanceBo> EventScheduleStudentSet=bo.getEventScheduleStudentAttendanceBo();
				if(EventScheduleStudentSet !=null && !EventScheduleStudentSet.isEmpty()){
					Iterator<EventScheduleStudentAttendanceBo> studentItr=EventScheduleStudentSet.iterator();
					while (studentItr.hasNext()) {
						EventScheduleStudentAttendanceBo currentStudentBo= studentItr .next();
						if(currentStudentBo.getId()==0){
						int currentcalssId=currentStudentBo.getClasses().getId();
							Iterator<EventScheduleForAttendanceBo> iterator=list.iterator();
								while(iterator.hasNext()){
									EventScheduleForAttendanceBo eventScheduleBo=iterator.next();
									String pattern = "HH:mm";
									SimpleDateFormat sdf = new SimpleDateFormat(pattern);
									String fromtime=eventScheduleBo.getEventTimeFrom();
									 fromtime= fromtime.substring(0, 5);
									
									
									String totime=eventScheduleBo.getEventTimeTo();
									 totime= totime.substring(0, 5);
									
									
									String currentFromTime=bo.getEventTimeFrom();
									currentFromTime= currentFromTime.substring(0, 5);
									
									
									Date fromTime=sdf.parse(fromtime);
									Date toTime=sdf.parse(totime);
									Date TimeFrom=sdf.parse(currentFromTime);
									
									Set<EventScheduleStudentAttendanceBo> previousStudentDetailSet=eventScheduleBo.getEventScheduleStudentAttendanceBo();
									if(previousStudentDetailSet !=null && !previousStudentDetailSet.isEmpty()){
										Iterator<EventScheduleStudentAttendanceBo> studentitr=previousStudentDetailSet.iterator();
										while (studentitr.hasNext()) {
											EventScheduleStudentAttendanceBo previousStudentBo= studentitr .next();
											if(previousStudentBo.getIsActive()){
											int previousCalssId=previousStudentBo.getClasses().getId();
									
												if((fromTime.compareTo(TimeFrom)<=0) && (TimeFrom.compareTo(toTime)<= 0) && currentcalssId==previousCalssId){
														isDuplicate=true;
														String str=" from Classes c where c.isActive=1  and c.id="+currentcalssId;
														Query query1=session.createQuery(str);
														Classes classes=(Classes)query1.uniqueResult();
														if(classes !=null ){
															dupClasses=dupClasses + classes.getName()+",";
															flag=true;
														}
													}
											}
										}
									}
								}
						}
				  }
				}
					if(flag){
						if(dupClasses!=null && !dupClasses.isEmpty()){
							dupClasses = dupClasses.substring(0, (dupClasses.length() - 1));
							}
						eventScheduleform.setDupClasses(dupClasses);
						eventScheduleform.setFlag(true);
					}
				}//checking duplicate department entry for staff by passing FromTime
				else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
					
					Set<EventScheduleStaffAttendanceBo> EventScheduleStaffSet=bo.getEventScheduleStaffAttendanceBo();
					if(EventScheduleStaffSet !=null && !EventScheduleStaffSet.isEmpty()){
						Iterator<EventScheduleStaffAttendanceBo> studentItr=EventScheduleStaffSet.iterator();
						while (studentItr.hasNext()) {
							EventScheduleStaffAttendanceBo currentStaffBo= studentItr .next();
							if(currentStaffBo.getId()==0){
							int currentDeptId=currentStaffBo.getDepartment().getId();
								Iterator<EventScheduleForAttendanceBo> iterator=list.iterator();
									while(iterator.hasNext()){
										EventScheduleForAttendanceBo eventScheduleBo=iterator.next();
										String pattern = "HH:mm";
										SimpleDateFormat sdf = new SimpleDateFormat(pattern);
										String fromtime=eventScheduleBo.getEventTimeFrom();
										 fromtime= fromtime.substring(0, 5);
										
										 String totime=eventScheduleBo.getEventTimeTo();
										 totime= totime.substring(0, 5);
										
										
										String currentFromTime=bo.getEventTimeFrom();
										currentFromTime= currentFromTime.substring(0, 5);
										
										
										Date fromTime=sdf.parse(fromtime);
										Date toTime=sdf.parse(totime);
										Date TimeFrom=sdf.parse(currentFromTime);
										
										Set<EventScheduleStaffAttendanceBo> previousStaffDetailSet=eventScheduleBo.getEventScheduleStaffAttendanceBo();
										if(previousStaffDetailSet !=null && !previousStaffDetailSet.isEmpty()){
											Iterator<EventScheduleStaffAttendanceBo> studentitr=previousStaffDetailSet.iterator();
											while (studentitr.hasNext()) {
												EventScheduleStaffAttendanceBo previousStaffBo= studentitr .next();
												if(previousStaffBo.getIsActive()){
												int previousDeptId=previousStaffBo.getDepartment().getId();
										
													if((fromTime.compareTo(TimeFrom)<=0) && (TimeFrom.compareTo(toTime)<= 0) && currentDeptId==previousDeptId){
															isDuplicate=true;
															String str=" from Department d where isActive=1 and d.id="+currentDeptId;
															Query query1=session.createQuery(str);
															Department dept=(Department)query1.uniqueResult();
															if(dept !=null ){
																dupDpartments=dupDpartments + dept.getName()+",";
																flag=true;
															}
														}
													}
												}
											}
										}
									}
						}
					}
						if(flag){
							if(dupDpartments!=null && !dupDpartments.isEmpty()){
								dupDpartments = dupDpartments.substring(0, (dupDpartments.length() - 1));
								}
							eventScheduleform.setDupDepartments(dupDpartments);
							eventScheduleform.setFlag1(true);
						}
					}
				}
				//if duplicate data not found save student or staff data
				if(isDuplicate){
					isAdded=true;
				}
			}
			}catch(Exception exception){
				if (transaction != null)
					transaction.rollback();
				log.debug("Error during saving data...", exception);
			}
			finally{
				session.flush();
				session.close();
			}
			return isAdded;

		}
	
	
	
	
	public boolean addOrUpdateStudentOrStaffData(EventScheduleForAttendanceBo bo,EventScheduleForAttendanceForm eventScheduleform)
	throws Exception {
		
			Session session=null;
			Transaction transaction=null;
			boolean isAdded=false;
			try{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				transaction.begin();
					session.saveOrUpdate(bo);
					transaction.commit();
					isAdded=true;
				
			}catch(Exception exception){
				if (transaction != null)
					transaction.rollback();
				log.debug("Error during saving data...", exception);
			}
			finally{
				session.flush();
				session.close();
			}
			return isAdded;

		}
	
	
	public boolean deleteEventScheduleStudentOrStaffDetails(int id,EventScheduleForAttendanceForm eventScheduleform) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isDeleted=false;
		try{
			    session=InitSessionFactory.getInstance().openSession();
			    String str="from EventScheduleForAttendanceBo metric where metric.id="+id;
			    EventScheduleForAttendanceBo eventScheduleAttBo=(EventScheduleForAttendanceBo)session.createQuery(str).uniqueResult();
			    transaction=session.beginTransaction();
			    if(eventScheduleAttBo!=null){
			    	if(eventScheduleform.getType().equalsIgnoreCase("Student")){	
						Set<EventScheduleStudentAttendanceBo> EventScheduleStudentSet=eventScheduleAttBo.getEventScheduleStudentAttendanceBo();
						if(EventScheduleStudentSet !=null && !EventScheduleStudentSet.isEmpty()){
							Iterator<EventScheduleStudentAttendanceBo> studentItr=EventScheduleStudentSet.iterator();
							while (studentItr.hasNext()) {
								EventScheduleStudentAttendanceBo studentBo= studentItr .next();
								if(studentBo!=null)
								studentBo.setIsActive(false);
							}
						}
			    	}else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
			    		
						Set<EventScheduleStaffAttendanceBo> EventScheduleStafftSet=eventScheduleAttBo.getEventScheduleStaffAttendanceBo();
						if(EventScheduleStafftSet !=null && !EventScheduleStafftSet.isEmpty()){
							Iterator<EventScheduleStaffAttendanceBo> studentItr=EventScheduleStafftSet.iterator();
							while (studentItr.hasNext()) {
								EventScheduleStaffAttendanceBo stafftBo= studentItr .next();
								if(stafftBo!=null)
									stafftBo.setIsActive(false);
							}
						}
			    	
			    	}
			    	
			    	
			    eventScheduleAttBo.setIsActive(false);
			    }
			    session.update(eventScheduleAttBo);
			    transaction.commit();
			    isDeleted=true;
			    session.close();
		  }catch(Exception e){
			    if (transaction != null)
				   transaction.rollback();
			log.debug("Error during deleting biometricDetails data...", e);
		}
		return isDeleted;

	}
	
	public EventScheduleStudentAttendanceBo getStudentData(int id) throws Exception {
		Session session=null;
		EventScheduleStudentAttendanceBo eventStAtt=null;
		try{
			session=HibernateUtil.getSession();
			String str="from EventScheduleStudentAttendanceBo stAtt where stAtt.isActive=1 and stAtt.id="+id;
			Query query=session.createQuery(str);
			eventStAtt=(EventScheduleStudentAttendanceBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting EventLoactionBiometricDetailsBo by id..." , e);
			session.flush();
			session.close();
		}
		return eventStAtt;
	
	}
	
	public EventScheduleStaffAttendanceBo getStaffData(int id) throws Exception {
		Session session=null;
		EventScheduleStaffAttendanceBo eventStAtt=null;
		try{
			session=HibernateUtil.getSession();
			String str="from EventScheduleStaffAttendanceBo stffAtt where stffAtt.isActive=1 and stffAtt.id="+id;
			Query query=session.createQuery(str);
			eventStAtt=(EventScheduleStaffAttendanceBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting EventLoactionBiometricDetailsBo by id..." , e);
			session.flush();
			session.close();
		}
		return eventStAtt;
	
	}
	
	public EventScheduleStudentAttendanceBo getInActiveStudentId(int id) throws Exception {
		Session session=null;
		EventScheduleStudentAttendanceBo eventStAtt=null;
		try{
			session=HibernateUtil.getSession();
			String str="from EventScheduleStudentAttendanceBo stAtt where stAtt.isActive=0 and stAtt.id="+id;
			Query query=session.createQuery(str);
			eventStAtt=(EventScheduleStudentAttendanceBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting EventLoactionBiometricDetailsBo by id..." , e);
			session.flush();
			session.close();
		}
		return eventStAtt;
	
	}
	
	public EventScheduleStaffAttendanceBo getInActiveStaffData(int id) throws Exception {
		Session session=null;
		EventScheduleStaffAttendanceBo eventStAtt=null;
		try{
			session=HibernateUtil.getSession();
			String str="from EventScheduleStaffAttendanceBo stffAtt where stffAtt.isActive=0 and stffAtt.id="+id;
			Query query=session.createQuery(str);
			eventStAtt=(EventScheduleStaffAttendanceBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting EventLoactionBiometricDetailsBo by id..." , e);
			session.flush();
			session.close();
		}
		return eventStAtt;
	
	}
}
