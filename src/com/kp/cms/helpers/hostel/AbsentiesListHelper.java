package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.forms.hostel.AbsentiesListForm;
import com.kp.cms.to.hostel.AbsentiesListTo;
import com.kp.cms.transactions.hostel.IAbsentiesListTransaction;
import com.kp.cms.transactionsimpl.hostel.AbsentiesListTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class AbsentiesListHelper {
	public static volatile AbsentiesListHelper absentiesListHelper = null;
	private static Log log = LogFactory.getLog(AbsentiesListHelper.class);
	IAbsentiesListTransaction transaction=AbsentiesListTransactionImpl.getInstance();
	/**
	 * instance method
	 * @return
	 */
	public static AbsentiesListHelper getInstance() {
		if (absentiesListHelper == null) {
			absentiesListHelper = new AbsentiesListHelper();
			return absentiesListHelper;
		}
		return absentiesListHelper;
	}
	public String getHlAdmissionBosByCourse(AbsentiesListForm absentiesListForm)throws Exception{
		String hostelId=absentiesListForm.getHostelId();
		String blockId=absentiesListForm.getBlockId();
		String unitId=absentiesListForm.getUnitId();
		String date1=absentiesListForm.getHolidaysFrom();
		java.sql.Date fromDate=CommonUtil.ConvertStringToSQLDate(date1);
		String query=null;
		if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty() && unitId!=null && !unitId.isEmpty()){
			query="from HlAdmissionBo h where h.isActive=1 and (h.isCancelled=0 or h.isCancelled is null) and h.isCheckedIn=1 and (h.checkOut=0 or h.checkOut is null) and h.hostelId.id="+hostelId+
			" and h.roomId.hlBlock.id="+blockId+" and h.roomId.hlUnit.id="+unitId+" and h.checkInDate<='"+fromDate+"'";
		}else if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty()){
			query="from HlAdmissionBo h where h.isActive=1 and (h.isCancelled=0 or h.isCancelled is null) and h.isCheckedIn=1 and (h.checkOut=0 or h.checkOut is null) and h.hostelId.id="+hostelId+
			" and h.roomId.hlBlock.id="+blockId+" and h.checkInDate<='"+fromDate+"'";
		}else if(hostelId!=null && !hostelId.isEmpty()){
			query="from HlAdmissionBo h where h.isActive=1 and (h.isCancelled=0 or h.isCancelled is null) and h.isCheckedIn=1 and (h.checkOut=0 or h.checkOut is null) and h.hostelId.id="+hostelId+" and h.checkInDate<='"+fromDate+"'";
		}else{
			query="from HlAdmissionBo h where h.isActive=1 and (h.isCancelled=0 or h.isCancelled is null) and h.isCheckedIn=1 and (h.checkOut=0 or h.checkOut is null) and h.checkInDate<='"+fromDate+"'";
		}
		return query;
	}
	public String getHostelHolidaysQuery(AbsentiesListForm absentiesListForm)throws Exception{
		String hostelId=absentiesListForm.getHostelId();
		String blockId=absentiesListForm.getBlockId();
		String unitId=absentiesListForm.getUnitId();
		String date1=absentiesListForm.getHolidaysFrom();
		String date2=absentiesListForm.getHolidaysTo();
		java.sql.Date fromDate=CommonUtil.ConvertStringToSQLDate(date1);
		java.sql.Date toDate=CommonUtil.ConvertStringToSQLDate(date2);
		String query=null;
		if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty() && unitId!=null && !unitId.isEmpty()){
			query="from HostelHolidaysBo h where h.isActive=1 and h.holidaysFrom between '"+fromDate+"' and '"+toDate+"'"+
			" OR h.holidaysTo between '"+fromDate+"' and '"+toDate+"' and h.hostelId.id="+hostelId+
			" and h.blockId.id="+blockId+" and h.unitId.id="+unitId;
		}else if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty()){
			query="from HostelHolidaysBo h where h.isActive=1 and h.holidaysFrom between '"+fromDate+"' and '"+toDate+"'"+
			" OR h.holidaysTo between '"+fromDate+"' and '"+toDate+"' and h.hostelId.id="+hostelId+
			" and h.blockId.id="+blockId;
		}else if(hostelId!=null && !hostelId.isEmpty()){
			query="from HostelHolidaysBo h where h.isActive=1 and h.holidaysFrom between '"+fromDate+"' and '"+toDate+"'"+
			" OR h.holidaysTo between '"+fromDate+"' and '"+toDate+"' and h.hostelId.id="+hostelId;
		}else{
			query="from HostelHolidaysBo h where h.isActive=1 and h.holidaysFrom between '"+fromDate+"' and '"+toDate+"'"+
			" OR h.holidaysTo between '"+fromDate+"' and '"+toDate+"'";
		}
		return query;
	}
	public String getMorningAbsentMap(AbsentiesListForm absentiesListForm)throws Exception{
		String hostelId=absentiesListForm.getHostelId();
		String blockId=absentiesListForm.getBlockId();
		String unitId=absentiesListForm.getUnitId();
		String query=null;
		String date1=absentiesListForm.getHolidaysFrom();
		String date2=absentiesListForm.getHolidaysTo();
		java.sql.Date fromDate=CommonUtil.ConvertStringToSQLDate(date1);
		java.sql.Date toDate=CommonUtil.ConvertStringToSQLDate(date2);
		if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty() && unitId!=null && !unitId.isEmpty()){
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.morning is not null "+
			" and attn.hlAdmissionBo.hostelId.id="+hostelId+" and attn.hlAdmissionBo.roomId.hlBlock.id="+blockId+" and attn.hlAdmissionBo.roomId.hlUnit.id="+unitId;
		}else if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty()){
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.morning is not null "+
			" and attn.hlAdmissionBo.hostelId.id="+hostelId+" and attn.hlAdmissionBo.roomId.hlBlock.id="+blockId;
		}else if(hostelId!=null && !hostelId.isEmpty()){
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.morning is not null "+
			" and attn.hlAdmissionBo.hostelId.id="+hostelId;
		}else{
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.morning is not null ";
		}
		return query;
	}
	
	public String getEveningAbsentMap(AbsentiesListForm absentiesListForm)throws Exception{
		String hostelId=absentiesListForm.getHostelId();
		String blockId=absentiesListForm.getBlockId();
		String unitId=absentiesListForm.getUnitId();
		String query=null;
		String date1=absentiesListForm.getHolidaysFrom();
		String date2=absentiesListForm.getHolidaysTo();
		java.sql.Date fromDate=CommonUtil.ConvertStringToSQLDate(date1);
		java.sql.Date toDate=CommonUtil.ConvertStringToSQLDate(date2);
		if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty() && unitId!=null && !unitId.isEmpty()){
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.evening is not null " +
			" and attn.hlAdmissionBo.hostelId.id="+hostelId+" and attn.hlAdmissionBo.roomId.hlBlock.id="+ blockId+"and attn.hlAdmissionBo.roomId.hlUnit.id="+unitId;
		}else if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty()){
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.evening is not null " +
			" and attn.hlAdmissionBo.hostelId.id="+hostelId+" and attn.hlAdmissionBo.roomId.hlBlock.id="+blockId;
		}else if(hostelId!=null && !hostelId.isEmpty()){
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null )" +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.evening is not null " +
			" and attn.hlAdmissionBo.hostelId.id="+hostelId;
		}else{
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.evening is not null ";
		}
		return query;
	}
	public String getPresentDetails(AbsentiesListForm absentiesListForm)throws Exception{
		String hostelId=absentiesListForm.getHostelId();
		String blockId=absentiesListForm.getBlockId();
		String unitId=absentiesListForm.getUnitId();
		String query=null;
		String date1=absentiesListForm.getHolidaysFrom();
		String date2=absentiesListForm.getHolidaysTo();
		java.sql.Date fromDate=CommonUtil.ConvertStringToSQLDate(date1);
		java.sql.Date toDate=CommonUtil.ConvertStringToSQLDate(date2);
		if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty() && unitId!=null && !unitId.isEmpty()){
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.morning != null and attn.evening != null " +
			" and attn.hlAdmissionBo.hostelId.id="+hostelId+" and attn.hlAdmissionBo.roomId.hlBlock.id="+blockId+" and attn.hlAdmissionBo.roomId.hlUnit.id="+unitId;
		}else if(hostelId!=null && !hostelId.isEmpty() && blockId!=null && !blockId.isEmpty()){
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.morning != null and attn.evening != null " +
			" and attn.hlAdmissionBo.hostelId.id="+hostelId+" and attn.hlAdmissionBo.roomId.hlBlock.id="+blockId;
		}else if(hostelId!=null && !hostelId.isEmpty()){
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null )" +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.morning != null and attn.evening != null " +
			" and attn.hlAdmissionBo.hostelId.id="+hostelId;
		}else{
			query="from HlStudentAttendance attn where attn.isActive=1 and attn.date between '"+fromDate+"' and '"+toDate+
			"' and attn.hlAdmissionBo.isActive=1 and (attn.hlAdmissionBo.isCancelled=0 or attn.hlAdmissionBo.isCancelled is null ) " +
			" and attn.hlAdmissionBo.isCheckedIn=1 and (attn.hlAdmissionBo.checkOut=0 or attn.hlAdmissionBo.checkOut is null) and attn.morning != null and attn.evening != null ";
		}
		return query;
	}
	public Date getNextDate(Date date)throws Exception{
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}
	
	public Date getPreviousDate(Date date)throws Exception{
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}
	public List<AbsentiesListTo> convertFinalAbsentiesToAbsentiesTo(Map<Date,Set<Integer>> finalMrngAbsentMap,Map<Date,Set<Integer>> finalEvngAbsentMap,
			Map<Integer,HlAdmissionBo> hlAdmissionMap,Date fromDate,String session)throws Exception{
		Date currentDate=new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(currentDate);
		String curDate=CommonUtil.formatDates(currentDate);
		int hours=calendar.get(Calendar.HOUR_OF_DAY);
		List<AbsentiesListTo> list3=new ArrayList<AbsentiesListTo>();
		List<AbsentiesListTo> list=new ArrayList<AbsentiesListTo>();
		if(finalMrngAbsentMap!=null && !finalMrngAbsentMap.isEmpty()){
			Set<Date> dates=finalMrngAbsentMap.keySet();
			Iterator<Date> iterator=dates.iterator();
			while (iterator.hasNext()) {
				Date date = (Date) iterator.next();
				if(curDate.equalsIgnoreCase(CommonUtil.formatDates(date)) && hours<8){
					continue;
				}else{
					Set<Integer> list4=finalMrngAbsentMap.get(date);
					Iterator<Integer> iterator2=list4.iterator();
					while (iterator2.hasNext()) {
						Integer integer = (Integer) iterator2.next();
						HlAdmissionBo hlAdmissionBo=hlAdmissionMap.get(integer);
						if(hlAdmissionBo!=null){
							AbsentiesListTo absentiesListTo=getAbsentiesTos(hlAdmissionBo);
							absentiesListTo.setDate(CommonUtil.formatDates(date));
							absentiesListTo.setSession("Morning");
							list.add(absentiesListTo);
						}else{
							continue;
						}
					}
					
				
				}
			}
		}
		if(finalEvngAbsentMap!=null && !finalEvngAbsentMap.isEmpty()){
			Set<Date> dates=finalMrngAbsentMap.keySet();
			Iterator<Date> iterator=dates.iterator();
			while (iterator.hasNext()) {
				Date date = (Date) iterator.next();
				if(curDate.equalsIgnoreCase(CommonUtil.formatDates(date)) && hours<20){
					continue;
				}else{
					Set<Integer> list4=finalMrngAbsentMap.get(date);
					Iterator<Integer> iterator2=list4.iterator();
					while (iterator2.hasNext()) {
						Integer integer = (Integer) iterator2.next();
						HlAdmissionBo hlAdmissionBo=hlAdmissionMap.get(integer);
						if(hlAdmissionBo!=null){
							AbsentiesListTo absentiesListTo=getAbsentiesTos(hlAdmissionBo);
							absentiesListTo.setDate(CommonUtil.formatDates(date));
							absentiesListTo.setSession("Evening");
							list.add(absentiesListTo);
						}
					}
				}
			}
		}
		/*if(finalTwoSessAbsentMap!=null && !finalTwoSessAbsentMap.isEmpty()){
			Set<Date> dates=finalTwoSessAbsentMap.keySet();
			Iterator<Date> iterator=dates.iterator();
			while (iterator.hasNext()) {
				Date date = (Date) iterator.next();
				Set<Integer> list4=finalTwoSessAbsentMap.get(date);
				Iterator<Integer> iterator2=list4.iterator();
				AbsentiesListTo absentiesListTo=null;
				while (iterator2.hasNext()) {
					Integer integer = (Integer) iterator2.next();
					HlAdmissionBo hlAdmissionBo=hlAdmissionMap.get(integer);
					if(hlAdmissionBo!=null){
						absentiesListTo=new AbsentiesListTo();
						absentiesListTo=getAbsentiesTos(hlAdmissionBo);
						absentiesListTo.setDate(CommonUtil.formatDates(date));
						if(curDate.equalsIgnoreCase(CommonUtil.formatDates(date)) && hours<8){
							continue;
						}else if(curDate.equalsIgnoreCase(CommonUtil.formatDates(date)) && hours<20){
							absentiesListTo.setSession("Morning");
						}else if(CommonUtil.formatDates(fromDate).equalsIgnoreCase(CommonUtil.formatDates(date)) && session.equalsIgnoreCase("evening")){
							absentiesListTo.setSession("Evening");
						}else{
							absentiesListTo.setSession("Morning, Evening");
						}
						list.add(absentiesListTo);
					}
				}
			}
		
		}*/
		if(list!=null && !list.isEmpty()){
			//set key as hostel for sorting
			Map<String,List<AbsentiesListTo>> map=new HashMap<String, List<AbsentiesListTo>>();
			Iterator<AbsentiesListTo> iterator=list.iterator();
			List<AbsentiesListTo> list2=null;
			while (iterator.hasNext()) {
				AbsentiesListTo absentiesListTo = (AbsentiesListTo) iterator.next();
				if(map.containsKey(absentiesListTo.getHostelName())){
					list2=map.get(absentiesListTo.getHostelName());
					list2.add(absentiesListTo);
					map.remove(absentiesListTo.getHostelName());
					map.put(absentiesListTo.getHostelName(), list2);
				}else{
					list2=new ArrayList<AbsentiesListTo>();
					list2.add(absentiesListTo);
					map.put(absentiesListTo.getHostelName(), list2);
				}
			}
			Map<String,Map<String,List<AbsentiesListTo>>> map2=null;
			if(map!=null && !map.isEmpty()){
				map=CommonUtil.sortMapByKey(map);
				map2=new HashMap<String, Map<String,List<AbsentiesListTo>>>();
				Set<String> hlIds=map.keySet();
				Iterator<String> iterator2=hlIds.iterator();
				Map<String,List<AbsentiesListTo>> map3=null;
				while (iterator2.hasNext()) {
					String string = (String) iterator2.next();
					map3=new HashMap<String, List<AbsentiesListTo>>();
					List<AbsentiesListTo> absentiesListTos=map.get(string);
					Iterator<AbsentiesListTo> iterator3=absentiesListTos.iterator();
					List<AbsentiesListTo> list4=null;
					while (iterator3.hasNext()) {
						AbsentiesListTo absentiesListTo = (AbsentiesListTo) iterator3.next();
						if(map3.containsKey(absentiesListTo.getRegNo())){
							list4=map3.get(absentiesListTo.getRegNo());
							list4.add(absentiesListTo);
							map3.remove(absentiesListTo.getRegNo());
							map3.put(absentiesListTo.getRegNo(), list4);
						}else{
							list4=new ArrayList<AbsentiesListTo>();
							list4.add(absentiesListTo);
							map3.put(absentiesListTo.getRegNo(), list4);
						}
					}
					map3=CommonUtil.sortMapByKey(map3);
					map2.put(string, map3);
				}
			}
			if(map2!=null && !map2.isEmpty()){
				Set<String> hostelIds=map2.keySet();
				Iterator<String> iterator2=hostelIds.iterator();
				while (iterator2.hasNext()) {
					String string = (String) iterator2.next();
					Map<String,List<AbsentiesListTo>> map3=map2.get(string);
					Set<String> regNos=map3.keySet();
					Iterator<String> iterator3=regNos.iterator();
					while (iterator3.hasNext()) {
						String string2 = (String) iterator3.next();
						List<AbsentiesListTo> absentiesListTos=map3.get(string2);
						Iterator<AbsentiesListTo> iterator4=absentiesListTos.iterator();
						while (iterator4.hasNext()) {
							AbsentiesListTo absentiesListTo = (AbsentiesListTo) iterator4.next();
							list3.add(absentiesListTo);
						}
					}
				}
			}
		}
		
		return list3;
	}
	public AbsentiesListTo getAbsentiesTos(HlAdmissionBo hlAdmissionBo)throws Exception{
		AbsentiesListTo absentiesListTo=new AbsentiesListTo();
		absentiesListTo.setHlAdminId(hlAdmissionBo.getId());
		String studentName="";
		String parentname="";
		if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFatherName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFatherName().isEmpty()){
			parentname=hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFatherName();
		}else if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMotherName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMotherName().isEmpty()){
			parentname=hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMotherName();
		}
		absentiesListTo.setParentName(parentname);
		if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty()){
			studentName=hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName();
		}else if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
			studentName=hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName();
		}else{
			studentName=hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getLastName();
		}
		absentiesListTo.setStudentName(studentName);
		if(hlAdmissionBo.getStudentId().getRegisterNo()!=null && !hlAdmissionBo.getStudentId().getRegisterNo().isEmpty()){
			absentiesListTo.setRegNo(hlAdmissionBo.getStudentId().getRegisterNo());
		}
		if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlBlock()!=null){
			absentiesListTo.setBlock(hlAdmissionBo.getRoomId().getHlBlock().getName());
		}
		if(hlAdmissionBo.getRoomId()!=null && hlAdmissionBo.getRoomId().getHlUnit()!=null){
			absentiesListTo.setUnit(hlAdmissionBo.getRoomId().getHlUnit().getName());
		}
		if(hlAdmissionBo.getRoomId()!=null){
			absentiesListTo.setRoom(hlAdmissionBo.getRoomId().getName());
		}
		if(hlAdmissionBo.getBedId()!=null){
			absentiesListTo.setBed(hlAdmissionBo.getBedId().getBedNo());
		}
		if(hlAdmissionBo.getHostelId()!=null){
			absentiesListTo.setHostelName(hlAdmissionBo.getHostelId().getName());
		}
		String Phone="";
		if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
			Phone=Phone+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2();
		}
		if(Phone.length()<10 && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3().isEmpty()){
			Phone=Phone+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3();
		}
		if(Phone.length()<10 && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()
				&& hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3().isEmpty()){
			Phone=Phone+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3();
		}
		absentiesListTo.setContactNo(Phone.trim());
		String ParentPhone="";
		if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
			ParentPhone=ParentPhone+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2();
		}
		if(ParentPhone.length()<10 && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob3()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob3().isEmpty()){
			ParentPhone=ParentPhone+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob3();
		}
		if(ParentPhone.length()<10 && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob3()!=null 
				&& !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob3().isEmpty() && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2()!=null 
				&& !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
			ParentPhone=ParentPhone+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2()+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob3();
		}
		absentiesListTo.setParentContactNo(ParentPhone.trim());
		if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getEmail()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getEmail().isEmpty()){
			absentiesListTo.setMailId(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getEmail());
		}
		String parentMailId=null;
		if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFatherEmail()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFatherEmail().isEmpty()){
			parentMailId=hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFatherEmail();
		}else if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMotherEmail()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMotherEmail().isEmpty()){
			parentMailId=hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMotherEmail();
		}
		absentiesListTo.setParentMailId(parentMailId);
		return absentiesListTo;
	}
	public List<AbsentiesListTo> convertBOToTO(List<HlAdmissionBo> absenteesBos,
			Map<Integer, Map<Integer, Map<Integer, List<Integer>>>> holidaysMap, AbsentiesListForm absentiesListForm) throws Exception{
		List<AbsentiesListTo> tos = new ArrayList<AbsentiesListTo>();
		if(absenteesBos != null){
			Iterator<HlAdmissionBo> iterator = absenteesBos.iterator();
			while (iterator.hasNext()) {
				HlAdmissionBo bo = (HlAdmissionBo) iterator.next();
				boolean absent = true;
				if(bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId() != null && bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getId() != 0 && bo.getHostelId() != null && 
						bo.getRoomId() != null && bo.getRoomId().getHlUnit() != null && bo.getRoomId().getHlBlock() != null){
					if(holidaysMap.containsKey(bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getId())){
						if(holidaysMap.get(bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getId()) != null 
								&& holidaysMap.get(bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getId()).containsKey(bo.getHostelId().getId())){
							if(holidaysMap.get(bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getId()).get(bo.getHostelId().getId()) != null &&
									holidaysMap.get(bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getId()).get(bo.getHostelId().getId()).containsKey(bo.getRoomId().getHlBlock().getId())){
								if(holidaysMap.get(bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getId()).get(bo.getHostelId().getId()).get(bo.getRoomId().getHlBlock().getId()) !=null &&
										holidaysMap.get(bo.getStudentId().getAdmAppln().getCourseBySelectedCourseId().getId()).get(bo.getHostelId().getId()).get(bo.getRoomId().getHlBlock().getId()).contains(bo.getRoomId().getHlUnit().getId())){
									absent = false;
								}
							}
						}
					}
				}
				if(absent){
					//Start
					
					AbsentiesListTo absentiesListTo=new AbsentiesListTo();
					absentiesListTo.setHlAdminId(bo.getId());
					String studentName="";
					String parentname="";
					if(bo.getStudentId().getAdmAppln().getPersonalData().getFatherName()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getFatherName().isEmpty()){
						parentname=bo.getStudentId().getAdmAppln().getPersonalData().getFatherName();
					}else if(bo.getStudentId().getAdmAppln().getPersonalData().getMotherName()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getMotherName().isEmpty()){
						parentname=bo.getStudentId().getAdmAppln().getPersonalData().getMotherName();
					}
					absentiesListTo.setParentName(parentname);
					if(bo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty()){
						studentName=bo.getStudentId().getAdmAppln().getPersonalData().getFirstName();
					}else if(bo.getStudentId().getAdmAppln().getPersonalData().getMiddleName()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
						studentName=bo.getStudentId().getAdmAppln().getPersonalData().getMiddleName();
					}else{
						studentName=bo.getStudentId().getAdmAppln().getPersonalData().getLastName();
					}
					absentiesListTo.setStudentName(studentName);
					if(bo.getStudentId().getRegisterNo()!=null && !bo.getStudentId().getRegisterNo().isEmpty()){
						absentiesListTo.setRegNo(bo.getStudentId().getRegisterNo());
					}
					if(bo.getRoomId()!=null && bo.getRoomId().getHlBlock()!=null){
						absentiesListTo.setBlock(bo.getRoomId().getHlBlock().getName());
					}
					if(bo.getRoomId()!=null && bo.getRoomId().getHlUnit()!=null){
						absentiesListTo.setUnit(bo.getRoomId().getHlUnit().getName());
					}
					if(bo.getRoomId()!=null){
						absentiesListTo.setRoom(bo.getRoomId().getName());
					}
					if(bo.getBedId()!=null){
						absentiesListTo.setBed(bo.getBedId().getBedNo());
					}
					if(bo.getHostelId()!=null){
						absentiesListTo.setHostelName(bo.getHostelId().getName());
					}
					String Phone="";
					if(bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
						Phone=Phone+bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2();
					}
					if(Phone.length()<10 && bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3().isEmpty()){
						Phone=Phone+bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3();
					}
					if(Phone.length()<10 && bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()
							&& bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3().isEmpty()){
						Phone=Phone+bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()+bo.getStudentId().getAdmAppln().getPersonalData().getMobileNo3();
					}
					absentiesListTo.setContactNo(Phone.trim());
					String ParentPhone="";
					if(bo.getStudentId().getAdmAppln().getPersonalData().getParentMob2()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
						ParentPhone=ParentPhone+bo.getStudentId().getAdmAppln().getPersonalData().getParentMob2();
					}
					if(ParentPhone.length()<10 && bo.getStudentId().getAdmAppln().getPersonalData().getParentMob3()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getParentMob3().isEmpty()){
						ParentPhone=ParentPhone+bo.getStudentId().getAdmAppln().getPersonalData().getParentMob3();
					}
					if(ParentPhone.length()<10 && bo.getStudentId().getAdmAppln().getPersonalData().getParentMob3()!=null 
							&& !bo.getStudentId().getAdmAppln().getPersonalData().getParentMob3().isEmpty() && bo.getStudentId().getAdmAppln().getPersonalData().getParentMob2()!=null 
							&& !bo.getStudentId().getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
						ParentPhone=ParentPhone+bo.getStudentId().getAdmAppln().getPersonalData().getParentMob2()+bo.getStudentId().getAdmAppln().getPersonalData().getParentMob3();
					}
					absentiesListTo.setParentContactNo(ParentPhone.trim());
					if(bo.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail().isEmpty()){
						absentiesListTo.setMailId(bo.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail());
					}
					String parentMailId=null;
					if(bo.getStudentId().getAdmAppln().getPersonalData().getFatherEmail()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getFatherEmail().isEmpty()){
						parentMailId=bo.getStudentId().getAdmAppln().getPersonalData().getFatherEmail();
					}else if(bo.getStudentId().getAdmAppln().getPersonalData().getMotherEmail()!=null && !bo.getStudentId().getAdmAppln().getPersonalData().getMotherEmail().isEmpty()){
						parentMailId=bo.getStudentId().getAdmAppln().getPersonalData().getMotherEmail();
					}
					absentiesListTo.setParentMailId(parentMailId);
					absentiesListTo.setDate(absentiesListForm.getHolidaysFrom());
					absentiesListTo.setSession(absentiesListForm.getHolidaysFromSession());
					tos.add(absentiesListTo);
				}
			}
		}
		return tos;
	}
}
