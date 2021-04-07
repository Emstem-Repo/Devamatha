package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.hostel.HlStudentAttendance;
import com.kp.cms.bo.hostel.HostelExemptionDetailsBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.to.hostel.HostelUnitsTO;

public class StudentHostelAttendance implements Job{
	private static final Log log = LogFactory.getLog(StudentSendingMailJob.class);	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			Date date = new Date();
			List<Integer> attIds = getPunchedStudentList(date);
			Map<Integer, Map<Integer, Map<Integer, List<Integer>>>> holidaysMap = getholidaysMap(date);
//			List<Integer> vocationCourses = getVocationCourses(date);
			List<Integer> leaveList = getLeaveForStudent(date);
			List<Integer> ExemptionList = getExceptionForStudent(date);
			List<HlAdmissionBo> notPunchedList = getNotPunchedStudentList(attIds);
			if(notPunchedList != null){
				Iterator<HlAdmissionBo> iterator = notPunchedList.iterator();
				Map<Integer, HostelUnitsTO> map = new HashMap<Integer, HostelUnitsTO>();
				while (iterator.hasNext()) {
					HlAdmissionBo bo = (HlAdmissionBo) iterator.next();
					boolean absent = true;
					if(bo.getCourseId() != null && bo.getCourseId().getId() != 0 && bo.getHostelId() != null && 
							bo.getRoomId() != null && bo.getRoomId().getHlUnit() != null && bo.getRoomId().getHlBlock() != null){
						/*if(vocationCourses.contains(bo.getCourseId().getId())){
							absent = false;
						}*/
						if(holidaysMap.containsKey(bo.getCourseId().getId())){
							if(holidaysMap.get(bo.getCourseId().getId()) != null 
									&& holidaysMap.get(bo.getCourseId().getId()).containsKey(bo.getHostelId().getId())){
								if(holidaysMap.get(bo.getCourseId().getId()).get(bo.getHostelId().getId()) != null &&
										holidaysMap.get(bo.getCourseId().getId()).get(bo.getHostelId().getId()).containsKey(bo.getRoomId().getHlBlock().getId())){
									if(holidaysMap.get(bo.getCourseId().getId()).get(bo.getHostelId().getId()).get(bo.getRoomId().getHlBlock().getId()) !=null &&
											holidaysMap.get(bo.getCourseId().getId()).get(bo.getHostelId().getId()).get(bo.getRoomId().getHlBlock().getId()).contains(bo.getRoomId().getHlUnit().getId())){
										absent = false;
									}
								}
							}
						}
					}
					if(leaveList.contains(bo.getId())){
						absent = false;
					}
					// /* code added by chandra 
					
					if(ExemptionList.contains(bo.getId())){
						absent = false;
					}
					
					if(bo.getRoomId()!=null && bo.getRoomId().getHlUnit()!=null){
						if(bo.getRoomId().getHlUnit().getPunchExepSundaySession() !=null && bo.getRoomId().getHlUnit().getPunchExepSundaySession()){
							Calendar date1 = Calendar.getInstance();
							if(date1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
								if(date.getHours()<12){
									absent = false;
								}
							}
						}
					}
					// */
					if(absent){
						if(bo.getRoomId() != null && bo.getRoomId().getHlUnit() != null){
							HostelUnitsTO to = null;
							if(map.containsKey(bo.getRoomId().getHlUnit().getId())){
								to = map.remove(bo.getRoomId().getHlUnit().getId());
							}else{
								to = new HostelUnitsTO();
								if(bo.getRoomId().getHlUnit().getSmsForPrimaryCon() != null && bo.getRoomId().getHlUnit().getSmsForPrimaryCon())
									to.setPrimaryContactMobile(bo.getRoomId().getHlUnit().getPrimaryContactMobile());
								if(bo.getRoomId().getHlUnit().getSmsForSecondCon() != null && bo.getRoomId().getHlUnit().getSmsForSecondCon())
									to.setSecContactMobile(bo.getRoomId().getHlUnit().getSecondaryContactMobile());
								if(bo.getRoomId().getHlHostel() != null && bo.getRoomId().getHlHostel().getName() != null){
									to.setHostelName(bo.getRoomId().getHlHostel().getName());
								}
								if(bo.getRoomId().getHlBlock() != null && bo.getRoomId().getHlBlock().getName() != null){
									to.setBlockName(bo.getRoomId().getHlBlock().getName());
								}
								if(bo.getRoomId().getHlUnit().getName() != null){
									to.setName(bo.getRoomId().getHlUnit().getName());
								}
							}
							to.setNoOfAbsance(to.getNoOfAbsance()+1);
							if(date.getHours()<12){
								if(bo.getRoomId().getHlUnit().getSmsOnMorning() != null && bo.getRoomId().getHlUnit().getSmsOnMorning())
									to.setSmsOnMorning("Yes");
							}else{
								if(bo.getRoomId().getHlUnit().getSmsOnEvening() != null && bo.getRoomId().getHlUnit().getSmsOnEvening())
									to.setSmsOnEvening("Yes");
							}
							map.put(bo.getRoomId().getHlUnit().getId(), to);
						}
					}
						
				}
				if(map != null && !map.isEmpty()){
					String desc1="";
					SMSTemplateHandler temphandle1=SMSTemplateHandler.getInstance();
					List<SMSTemplate> list1= temphandle1.getDuplicateCheckList("Hostel Absentees");
					if(list1 != null && !list1.isEmpty()) {
						desc1 = list1.get(0).getTemplateDescription();
					}
					if(desc1 != null && !desc1.isEmpty()){
						Iterator<Entry<Integer, HostelUnitsTO>> iterator2 = map.entrySet().iterator();
						Properties prop1 = new Properties();
						InputStream in1 = ExamValuatorPreInfo.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
						prop1.load(in1);
						String senderNumber=prop1.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
						String senderName=prop1.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
						while (iterator2.hasNext()) {
							Map.Entry<Integer, HostelUnitsTO> entry = (Map.Entry<Integer, HostelUnitsTO>) iterator2.next();
							HostelUnitsTO to = entry.getValue();
							desc1 = list1.get(0).getTemplateDescription();
							if(date.getHours()<12){
								if(to.getSmsOnMorning() != null && to.getSmsOnMorning().equalsIgnoreCase("YES")){
									desc1 = desc1.replace("[HOSTEL]", to.getHostelName());
									desc1 = desc1.replace("[UNIT]", to.getName());
									desc1 = desc1.replace("[BLOCK]", to.getBlockName());
									desc1 = desc1.replace("[NO_OF_ABSENTEES]", String.valueOf(to.getNoOfAbsance()));
									
									if(to.getPrimaryContactMobile() != null && !to.getPrimaryContactMobile().isEmpty()){
										MobileMessaging mob=new MobileMessaging();
										mob.setDestinationNumber(to.getPrimaryContactMobile());
										mob.setMessageBody(desc1);
										mob.setMessagePriority(3);
										mob.setSenderName(senderName);
										mob.setSenderNumber(senderNumber);
										mob.setMessageEnqueueDate(new java.util.Date());
										mob.setIsMessageSent(false);
										PropertyUtil.getInstance().save(mob);
									}
									if(to.getSecContactMobile() != null && !to.getSecContactMobile().isEmpty()){
										MobileMessaging mob=new MobileMessaging();
										mob.setDestinationNumber(to.getSecContactMobile());
										mob.setMessageBody(desc1);
										mob.setMessagePriority(3);
										mob.setSenderName(senderName);
										mob.setSenderNumber(senderNumber);
										mob.setMessageEnqueueDate(new java.util.Date());
										mob.setIsMessageSent(false);
										PropertyUtil.getInstance().save(mob);
									}
								}
							}else{
								if(to.getSmsOnEvening() != null && to.getSmsOnEvening().equalsIgnoreCase("YES")){
									desc1 = desc1.replace("[HOSTEL]", to.getHostelName());
									desc1 = desc1.replace("[UNIT]", to.getName());
									desc1 = desc1.replace("[BLOCK]", to.getBlockName());
									desc1 = desc1.replace("[NO_OF_ABSENTEES]", String.valueOf(to.getNoOfAbsance()));
									
									if(to.getPrimaryContactMobile() != null && !to.getPrimaryContactMobile().isEmpty()){
										MobileMessaging mob=new MobileMessaging();
										mob.setDestinationNumber(to.getPrimaryContactMobile());
										mob.setMessageBody(desc1);
										mob.setMessagePriority(3);
										mob.setSenderName(senderName);
										mob.setSenderNumber(senderNumber);
										mob.setMessageEnqueueDate(new java.util.Date());
										mob.setIsMessageSent(false);
										PropertyUtil.getInstance().save(mob);
									}
									if(to.getSecContactMobile() != null && !to.getSecContactMobile().isEmpty()){
										MobileMessaging mob=new MobileMessaging();
										mob.setDestinationNumber(to.getSecContactMobile());
										mob.setMessageBody(desc1);
										mob.setMessagePriority(3);
										mob.setSenderName(senderName);
										mob.setSenderNumber(senderNumber);
										mob.setMessageEnqueueDate(new java.util.Date());
										mob.setIsMessageSent(false);
										PropertyUtil.getInstance().save(mob);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error in StudentHostelAttendance"
					+ Calendar.getInstance().getTimeInMillis()+e.getMessage());
			e.printStackTrace();
		}
	}
	

	private Map<Integer, Map<Integer, Map<Integer, List<Integer>>>> getholidaysMap(Date date) throws Exception{
		Map<Integer, Map<Integer, Map<Integer, List<Integer>>>> map = new HashMap<Integer, Map<Integer,Map<Integer,List<Integer>>>>();
		Map<Integer, Map<Integer, List<Integer>>> hostelMap = null;
		Map<Integer, List<Integer>> blockMap = null;
		List<Integer> unitList = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			List<HostelHolidaysBo> bos = session.createQuery("select h from HostelHolidaysBo h where current_date() between h.holidaysFrom and h.holidaysTo and h.isActive=1").list();
			if(bos != null){
				boolean add = false;
				Iterator<HostelHolidaysBo> iterator = bos.iterator();
				while (iterator.hasNext()) {
					HostelHolidaysBo bo = (HostelHolidaysBo) iterator.next();
					add = false;
					if(bo.getHolidaysFrom() != null && bo.getHolidaysTo() != null && bo.getCourseId() != null){
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(bo.getHolidaysFrom());
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(bo.getHolidaysTo());
						
						if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1 && CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(date.getHours()>=12 && bo.getHolidaysToSession().equalsIgnoreCase("evening")){
								add = true;
							}else if(date.getHours()<12 && bo.getHolidaysFromSession().equalsIgnoreCase("morning")){
								add = true;
							}
						}else if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1){
							if(date.getHours()<12 && bo.getHolidaysFromSession().equalsIgnoreCase("morning")){
								add = true;
							}else if(date.getHours()>=12 && (bo.getHolidaysFromSession().equalsIgnoreCase("evening") || bo.getHolidaysFromSession().equalsIgnoreCase("morning"))){
								add = true;
							}
						}else if(CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(date.getHours()>=12 && bo.getHolidaysToSession().equalsIgnoreCase("evening")){
								add = true;
							}else if(date.getHours()<12 && (bo.getHolidaysToSession().equalsIgnoreCase("morning") || bo.getHolidaysToSession().equalsIgnoreCase("evening"))){
								add = true;
							}
						}else{
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


	/**
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private List<Integer> getPunchedStudentList(Date date) throws Exception{
		List<Integer> list = new ArrayList<Integer>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			if(date.getHours()<12)
				list = session.createQuery("select att.hlAdmissionBo.id from HlStudentAttendance att where att.date = current_date() and att.morning != null and att.morning !=''").list();
			else
				list = session.createQuery("select att.hlAdmissionBo.id from HlStudentAttendance att where att.date = current_date() and att.evening != null and att.evening !=''").list();
		}catch (Exception e) {
			if(session != null)
				session.close();
		}finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	/**
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private List<Integer> getVocationCourses(Date date) throws Exception{
		List<Integer> list = new ArrayList<Integer>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			List<HostelHolidaysBo> bos = session.createQuery("select h from HostelHolidaysBo h where current_date() between h.holidaysFrom and h.holidaysTo and h.isActive=1").list();
			if(bos != null){
				Iterator<HostelHolidaysBo> iterator = bos.iterator();
				while (iterator.hasNext()) {
					HostelHolidaysBo bo = (HostelHolidaysBo) iterator.next();
					if(bo.getHolidaysFrom() != null && bo.getHolidaysTo() != null && bo.getCourseId() != null){
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(bo.getHolidaysFrom());
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(bo.getHolidaysTo());
						
						if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1 && CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(date.getHours()>=12 && bo.getHolidaysToSession().equalsIgnoreCase("evening")){
								list.add(bo.getCourseId().getId());
							}else if(date.getHours()<12 && bo.getHolidaysFromSession().equalsIgnoreCase("morning")){
								list.add(bo.getCourseId().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1){
							if(date.getHours()<12 && bo.getHolidaysFromSession().equalsIgnoreCase("morning")){
								list.add(bo.getCourseId().getId());
							}else if(date.getHours()>=12 && (bo.getHolidaysFromSession().equalsIgnoreCase("evening") || bo.getHolidaysFromSession().equalsIgnoreCase("morning"))){
								list.add(bo.getCourseId().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(date.getHours()>=12 && bo.getHolidaysToSession().equalsIgnoreCase("evening")){
								list.add(bo.getCourseId().getId());
							}else if(date.getHours()<12 && (bo.getHolidaysToSession().equalsIgnoreCase("morning") || bo.getHolidaysToSession().equalsIgnoreCase("evening"))){
								list.add(bo.getCourseId().getId());
							}
						}else{
							list.add(bo.getCourseId().getId());
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
		return list;
	}
	/**
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private List<Integer> getLeaveForStudent(Date date) throws Exception{
		List<Integer> list = new ArrayList<Integer>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			List<HlLeave> bos = session.createQuery("from HlLeave l where current_date() between l.leaveFrom and l.leaveTo and l.isActive=1 and l.isApproved=1").list();
			if(bos != null){
				Iterator<HlLeave> iterator = bos.iterator();
				while (iterator.hasNext()) {
					HlLeave bo = (HlLeave) iterator.next();
					if(bo.getLeaveFrom() != null && bo.getLeaveTo() != null && bo.getHlAdmissionBo() != null){
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(bo.getLeaveFrom());
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(bo.getLeaveTo());
						
						if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1 && CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(date.getHours()>=12 && bo.getToSession().equalsIgnoreCase("evening")){
								list.add(bo.getHlAdmissionBo().getId());
							}else if(date.getHours()<12 && bo.getFromSession().equalsIgnoreCase("morning")){
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1){
							if(date.getHours()<12 && bo.getFromSession().equalsIgnoreCase("morning")){
								list.add(bo.getHlAdmissionBo().getId());
							}else if(date.getHours()>=12 && (bo.getFromSession().equalsIgnoreCase("morning") || bo.getFromSession().equalsIgnoreCase("evening"))){
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(date.getHours()>=12 && bo.getToSession().equalsIgnoreCase("evening")){
								list.add(bo.getHlAdmissionBo().getId());
							}else if(date.getHours()<12 && (bo.getToSession().equalsIgnoreCase("evening") || bo.getToSession().equalsIgnoreCase("morning"))){
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else{
							list.add(bo.getHlAdmissionBo().getId());
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
		return list;
	}
	/**
	 * @param attIds
	 * @throws Exception
	 */
	private List<HlAdmissionBo> getNotPunchedStudentList(List<Integer> attIds) throws Exception{
		List<HlAdmissionBo> list = new ArrayList<HlAdmissionBo>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			if(attIds != null && !attIds.isEmpty())
				list = session.createQuery("from HlAdmissionBo h where (h.isCancelled=0 or h.isCancelled is null) and h.isCheckedIn=1 and (h.checkOut=0 or h.checkOut is null) and h.isActive=1 and h.biometricId != null and h.id not in(:attId)").setParameterList("attId", attIds).list();
			else
				list = session.createQuery("from HlAdmissionBo h where (h.isCancelled=0 or h.isCancelled is null) and h.isCheckedIn=1 and h.biometricId != null and (h.checkOut=0 or h.checkOut is null) and h.isActive=1").list();
		}catch (Exception e) {
			if(session != null)
				session.close();
		}finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	
	/**
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private List<Integer> getExceptionForStudent(Date date) throws Exception{
		List<Integer> list = new ArrayList<Integer>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			List<HostelExemptionDetailsBo> bos = session.createQuery("from HostelExemptionDetailsBo hd where current_date() between hd.hostelExemptionBo.fromDate and hd.hostelExemptionBo.toDate and hd.isActive=1 and  hd.hostelExemptionBo.isActive=1").list();
			if(bos != null){
				Iterator<HostelExemptionDetailsBo> iterator = bos.iterator();
				while (iterator.hasNext()) {
					HostelExemptionDetailsBo bo = (HostelExemptionDetailsBo) iterator.next();
					if(bo.getHostelExemptionBo().getFromDate() != null && bo.getHostelExemptionBo().getToDate() != null && bo.getHlAdmissionBo() != null){
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(bo.getHostelExemptionBo().getFromDate());
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(bo.getHostelExemptionBo().getToDate());
						
						if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1 && CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(date.getHours()>=12 && bo.getHostelExemptionBo().getToSession().equalsIgnoreCase("evening")){
								list.add(bo.getHlAdmissionBo().getId());
							}else if(date.getHours()<12 && bo.getHostelExemptionBo().getFromSession().equalsIgnoreCase("morning")){
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal1, cal) == 1){
							if(date.getHours()<12 && bo.getHostelExemptionBo().getFromSession().equalsIgnoreCase("morning")){
								list.add(bo.getHlAdmissionBo().getId());
							}else if(date.getHours()>=12 && (bo.getHostelExemptionBo().getFromSession().equalsIgnoreCase("morning") || bo.getHostelExemptionBo().getFromSession().equalsIgnoreCase("evening"))){
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else if(CommonUtil.getDaysBetweenDates(cal2, cal) == 1){
							if(date.getHours()>=12 && bo.getHostelExemptionBo().getToSession().equalsIgnoreCase("evening")){
								list.add(bo.getHlAdmissionBo().getId());
							}else if(date.getHours()<12 && (bo.getHostelExemptionBo().getToSession().equalsIgnoreCase("evening") || bo.getHostelExemptionBo().getToSession().equalsIgnoreCase("morning"))){
								list.add(bo.getHlAdmissionBo().getId());
							}
						}else{
							list.add(bo.getHlAdmissionBo().getId());
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
		return list;
	}
}
