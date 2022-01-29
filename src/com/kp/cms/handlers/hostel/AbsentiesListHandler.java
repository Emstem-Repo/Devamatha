package com.kp.cms.handlers.hostel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.hostel.HlStudentAttendance;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.AbsentiesListForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.hostel.AbsentiesListHelper;
import com.kp.cms.to.hostel.AbsentiesListTo;
import com.kp.cms.transactions.hostel.IAbsentiesListTransaction;
import com.kp.cms.transactionsimpl.hostel.AbsentiesListTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class AbsentiesListHandler {
	public static volatile AbsentiesListHandler absentiesListHandler = null;
	private static Log log = LogFactory.getLog(AbsentiesListHandler.class);
	IAbsentiesListTransaction transaction=AbsentiesListTransactionImpl.getInstance();
	/**
	 * instance method
	 * @return
	 */
	public static AbsentiesListHandler getInstance() {
		if (absentiesListHandler == null) {
			absentiesListHandler = new AbsentiesListHandler();
			return absentiesListHandler;
		}
		return absentiesListHandler;
	}
	public Map<Integer,String> getHostelMap()throws Exception{
		Map<Integer,String> map=transaction.getHostelMap();
		return map;
	}
	public Boolean checkTheDates(AbsentiesListForm absentiesListForm)throws Exception{
		Boolean status=false;
		Date date=new Date();
		Date fDate=CommonUtil.ConvertStringToDate(absentiesListForm.getHolidaysFrom());
		/*Date tDate=CommonUtil.ConvertStringToDate(absentiesListForm.getHolidaysTo());
		if(fDate.compareTo(date)>0 || tDate.compareTo(date)>0){
			status=true;
		}*/
		if(fDate.compareTo(date)>0){
			status=true;
		}
		return status;
	}
	/**
	 * getting the absenties list
	 * @param absentiesListForm
	 * @throws Exception
	 */
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
	
	public Map<Integer,Map<Date,List<Integer>>> getTwoSessionAbsentMapByMngEvgAndPresentMapsByCourse(Map<Integer,List<Integer>> hlAdminListByCourse,
			Map<Integer,Map<Date,List<Integer>>> morningAbsentMapByCourse,Map<Integer,Map<Date,List<Integer>>> eveningAbsentMapByCourse,Map<Integer,Map<Date,List<Integer>>> twoSessionPresentMapByCourse,Date fromDate,Date toDate)throws Exception{
		Map<Integer,Map<Date,List<Integer>>> twoSessAbsentMapByCourse=new HashMap<Integer,Map<Date,List<Integer>>>();
		Set<Integer> courseIds=hlAdminListByCourse.keySet();
		Map<Date,List<Integer>> morningAbMapByDate=null;
		Map<Date,List<Integer>> eveningAbMapByDate=null;
		Map<Date,List<Integer>> twoSessPresMapByDate=null;
		Iterator<Integer> iterator=courseIds.iterator();
		while (iterator.hasNext()) {
			Date fromDate2=fromDate;
			Date toDate2=toDate;
			Integer integer = (Integer) iterator.next();
			morningAbMapByDate=new HashMap<Date, List<Integer>>();
			eveningAbMapByDate=new HashMap<Date, List<Integer>>();
			twoSessPresMapByDate=new HashMap<Date, List<Integer>>();
			if(morningAbsentMapByCourse.containsKey(integer)){
				morningAbMapByDate.putAll(morningAbsentMapByCourse.get(integer));
			}
			if(eveningAbsentMapByCourse.containsKey(integer)){
				eveningAbMapByDate.putAll(eveningAbsentMapByCourse.get(integer));
			}
			if(twoSessionPresentMapByCourse.containsKey(integer)){
				twoSessPresMapByDate.putAll(twoSessionPresentMapByCourse.get(integer));
			}
			Map<Date,List<Integer>> map=null;
			while(fromDate2.compareTo(toDate2)<=0){
				map=new HashMap<Date, List<Integer>>();
				if(morningAbMapByDate!=null && !morningAbMapByDate.isEmpty() && eveningAbMapByDate!=null
						&& !eveningAbMapByDate.isEmpty() && twoSessPresMapByDate!=null && !twoSessPresMapByDate.isEmpty()){
				if(morningAbMapByDate.containsKey(fromDate2) && eveningAbMapByDate.containsKey(fromDate2) && twoSessPresMapByDate.containsKey(fromDate2)){
					List<Integer> eList=eveningAbMapByDate.get(fromDate2);
					List<Integer> mList=morningAbMapByDate.get(fromDate2);
					List<Integer> tList=twoSessPresMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(eList);
					hlAdminIds.remove(mList);
					hlAdminIds.remove(tList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(morningAbMapByDate.containsKey(fromDate2) && eveningAbMapByDate.containsKey(fromDate2)){
					List<Integer> eList=eveningAbMapByDate.get(fromDate2);
					List<Integer> mList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(eList);
					hlAdminIds.remove(mList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(morningAbMapByDate.containsKey(fromDate2) && twoSessPresMapByDate.containsKey(fromDate2)){
					List<Integer> tList=twoSessPresMapByDate.get(fromDate2);
					List<Integer> mList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					hlAdminIds.remove(mList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(eveningAbMapByDate.containsKey(fromDate2) && twoSessPresMapByDate.containsKey(fromDate2)){
					List<Integer> tList=twoSessPresMapByDate.get(fromDate2);
					List<Integer> eList=eveningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					hlAdminIds.remove(eList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(morningAbMapByDate.containsKey(fromDate2)){
					List<Integer> mList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(mList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(eveningAbMapByDate.containsKey(fromDate2)){
					List<Integer> eList=eveningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(eList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(twoSessPresMapByDate.containsKey(fromDate2)){
					List<Integer> tList=twoSessPresMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else{
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}
			}else if(morningAbsentMapByCourse!=null && !morningAbsentMapByCourse.isEmpty() 
						&& eveningAbsentMapByCourse!=null && !eveningAbsentMapByCourse.isEmpty()){
				if(morningAbMapByDate.containsKey(fromDate2) && eveningAbMapByDate.containsKey(fromDate2)){
					List<Integer> eList=eveningAbMapByDate.get(fromDate2);
					List<Integer> mList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(eList);
					hlAdminIds.remove(mList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(morningAbMapByDate.containsKey(fromDate2)){
					List<Integer> mList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(mList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(eveningAbMapByDate.containsKey(fromDate2)){
					List<Integer> eList=eveningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(eList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else{
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}
			}else if(morningAbsentMapByCourse!=null && !morningAbsentMapByCourse.isEmpty() 
					&& twoSessionPresentMapByCourse!=null && !twoSessionPresentMapByCourse.isEmpty()){
				if(morningAbMapByDate.containsKey(fromDate2) && twoSessPresMapByDate.containsKey(fromDate2)){
					List<Integer> tList=twoSessPresMapByDate.get(fromDate2);
					List<Integer> mList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					hlAdminIds.remove(mList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(morningAbMapByDate.containsKey(fromDate2)){
					List<Integer> mList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(mList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(twoSessPresMapByDate.containsKey(fromDate2)){
					List<Integer> tList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else{
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}
			}else if(eveningAbsentMapByCourse!=null && !eveningAbsentMapByCourse.isEmpty() 
					&& twoSessionPresentMapByCourse!=null && !twoSessionPresentMapByCourse.isEmpty()){
				if(eveningAbMapByDate.containsKey(fromDate2) && twoSessPresMapByDate.containsKey(fromDate2)){
					List<Integer> tList=twoSessPresMapByDate.get(fromDate2);
					List<Integer> eList=eveningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					hlAdminIds.remove(eList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(eveningAbMapByDate.containsKey(fromDate2)){
					List<Integer> eList=eveningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(eList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(twoSessionPresentMapByCourse.containsKey(fromDate2)){
					List<Integer> tList=twoSessPresMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else{
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}
			}else if(twoSessionPresentMapByCourse!=null && !twoSessionPresentMapByCourse.isEmpty()){
				if(twoSessionPresentMapByCourse.containsKey(fromDate2)){
					List<Integer> tList=twoSessPresMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else{
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}
			}else if(morningAbsentMapByCourse!=null && !morningAbsentMapByCourse.isEmpty()){
				if(morningAbMapByDate.containsKey(fromDate2)){
					List<Integer> mList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(mList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(twoSessPresMapByDate.containsKey(fromDate2)){
					List<Integer> tList=morningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else{
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}
			}else if(eveningAbsentMapByCourse!=null && !eveningAbsentMapByCourse.isEmpty()){
				if(eveningAbMapByDate.containsKey(fromDate2)){
					List<Integer> eList=eveningAbMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(eList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else if(twoSessionPresentMapByCourse.containsKey(fromDate2)){
					List<Integer> tList=twoSessPresMapByDate.get(fromDate2);
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					hlAdminIds.remove(tList);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}else{
					List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
					map.put(fromDate2, hlAdminIds);
					if(twoSessAbsentMapByCourse.containsKey(integer)){
						map.putAll(twoSessAbsentMapByCourse.get(integer));
						twoSessAbsentMapByCourse.remove(integer);
						twoSessAbsentMapByCourse.put(integer, map);
					}else{
						twoSessAbsentMapByCourse.put(integer, map);
					}
					fromDate2=getNextDate(fromDate2);
				}
			}else{
				List<Integer> hlAdminIds=hlAdminListByCourse.get(integer);
				map.put(fromDate2, hlAdminIds);
				if(twoSessAbsentMapByCourse.containsKey(integer)){
					map.putAll(twoSessAbsentMapByCourse.get(integer));
					twoSessAbsentMapByCourse.remove(integer);
					twoSessAbsentMapByCourse.put(integer, map);
				}else{
					twoSessAbsentMapByCourse.put(integer, map);
				}
				fromDate2=getNextDate(fromDate2);
			}
		}
	}
		return twoSessAbsentMapByCourse;
	}
	public Map<Integer,Map<Date,List<Integer>>> getMorningAbsentMap(Map<Integer,Map<Date,List<Integer>>> morningPresentMapByCourse,Map<Integer,List<Integer>> hlAdminListByCourse,String fromDate,String toDate)throws Exception{
		Map<Integer,Map<Date,List<Integer>>> absentMap=new HashMap<Integer, Map<Date,List<Integer>>>();
		Map<Integer,List<Integer>> admIds=new HashMap<Integer, List<Integer>>();
		admIds.putAll(hlAdminListByCourse);
		Set<Integer> courseIds=admIds.keySet();
		Iterator<Integer> iterator=courseIds.iterator();
		List<Integer> hlAdminIds=null;
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			hlAdminIds=admIds.get(integer);
			Map<Date,List<Integer>> map=null;
			if(morningPresentMapByCourse.containsKey(integer)){
				Map<Date,List<Integer>> presentmap=morningPresentMapByCourse.get(integer);
				java.sql.Date frmDate=CommonUtil.ConvertStringToSQLDate(fromDate);
				
				while(frmDate.compareTo(CommonUtil.ConvertStringToSQLDate(toDate))<=0){
					map=new HashMap<Date, List<Integer>>();
					if(presentmap.containsKey(frmDate)){
						List<Integer> presentids=presentmap.get(frmDate);
						List<Integer> ids=new ArrayList<Integer>();
						ids.addAll(hlAdminIds);
						ids.removeAll(presentids);
						map.put(frmDate, ids);
					}else{
						map.put(frmDate, hlAdminIds);
					}
					absentMap.put(integer, map);
					frmDate=getNextSqlDate(frmDate);
				}
			}else{
				java.sql.Date frmDate=CommonUtil.ConvertStringToSQLDate(fromDate);
				while (frmDate.compareTo(CommonUtil.ConvertStringToSQLDate(toDate))<=0) {
					map=new HashMap<Date, List<Integer>>();
					map.put(frmDate, hlAdminIds);
					absentMap.put(integer, map);
					frmDate=getNextSqlDate(frmDate);
				}
			}
		}
		
		return absentMap;
	}
	/**
	 * @param frmDate
	 * @return
	 */
	private java.sql.Date getNextSqlDate(java.sql.Date frmDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(frmDate);
		cal.add(Calendar.DAY_OF_YEAR,1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date sqlTommorow = new java.sql.Date(cal.getTimeInMillis());
		return sqlTommorow;
	}
	public List<AbsentiesListTo> absenties(AbsentiesListForm absentiesListForm)throws Exception{
		List<AbsentiesListTo> list=null;
			Date fromDate=CommonUtil.ConvertStringToDate(absentiesListForm.getHolidaysFrom());
			//Date toDate=CommonUtil.ConvertStringToDate(absentiesListForm.getHolidaysTo());
			Map<Integer,List<Integer>> hlAdminListByCourse=transaction.getHlAdmissionBosByCourse(AbsentiesListHelper.getInstance().getHlAdmissionBosByCourse(absentiesListForm));
			Map<Integer,Map<Date,List<Integer>>> morningPresentMapByCourse=transaction.getMorningAbsentMapByCourse(AbsentiesListHelper.getInstance().getMorningAbsentMap(absentiesListForm),absentiesListForm,"Morning");
			Map<Integer,Map<Date,List<Integer>>> eveningPresentMapByCourse=transaction.getMorningAbsentMapByCourse(AbsentiesListHelper.getInstance().getEveningAbsentMap(absentiesListForm),absentiesListForm,"Evening");
			//Map<Integer,Map<Date,List<Integer>>> twoSessionPresentMapByCourse=transaction.getMorningAbsentMapByCourse(AbsentiesListHelper.getInstance().getPresentDetails(absentiesListForm));
			if(hlAdminListByCourse!=null && !hlAdminListByCourse.isEmpty()){
				Map<Integer,HlAdmissionBo> hlMap=transaction.getHlAdmissionMap(AbsentiesListHelper.getInstance().getHlAdmissionBosByCourse(absentiesListForm));
				Map<Integer,Map<Date,List<Integer>>> morningAbsMap=getMorningAbsentMap(morningPresentMapByCourse,hlAdminListByCourse,absentiesListForm.getHolidaysFrom(),absentiesListForm.getHolidaysTo());
				Map<Integer,Map<Date,List<Integer>>> eveningAbsMap=getMorningAbsentMap(eveningPresentMapByCourse,hlAdminListByCourse,absentiesListForm.getHolidaysFrom(),absentiesListForm.getHolidaysTo());
				//Map<Integer,Map<Date,List<Integer>>> twoSessionAbsentMapByCourse=getTwoSessionAbsentMapByMngEvgAndPresentMapsByCourse(hlAdminListByCourse,morningAbsentMapByCourse,eveningAbsentMapByCourse,twoSessionPresentMapByCourse,fromDate,toDate);
				// morning, evening and two session absenties map by removing hostel holidays
				//Map<Integer,List<HostelHolidaysBo>> hostelHolidaysByCourse=transaction.getHostelHolidaysByCourse(absentiesListForm);
				Map<Integer,List<HostelHolidaysBo>> hostelHolidaysByCourse=transaction.getHostelHolidaysByCourse(AbsentiesListHelper.getInstance().getHostelHolidaysQuery(absentiesListForm));
				if(hostelHolidaysByCourse!=null && !hostelHolidaysByCourse.isEmpty()){
					Set<Integer> holidaysByCourseId=hostelHolidaysByCourse.keySet();
					Iterator<Integer> iterator=holidaysByCourseId.iterator();
					while (iterator.hasNext()) {
						Integer integer = (Integer) iterator.next();
						
						//if morningAbsentMapByCourse contains the course id
						if(morningAbsMap.containsKey(integer)){
							Map<Date,List<Integer>> mngMapByCourse=getMrngAbMapByCourse(morningAbsMap.get(integer),hostelHolidaysByCourse.get(integer),"morningAbMap",hlMap);
							morningAbsMap.remove(integer);
							if(mngMapByCourse!=null && !mngMapByCourse.isEmpty()){
								morningAbsMap.put(integer, mngMapByCourse);
							}
						}
						//if eveningAbsentMapByCourse contains course  id 
						if(eveningAbsMap.containsKey(integer)){
							Map<Date,List<Integer>> evngMapByCourse=getMrngAbMapByCourse(eveningAbsMap.get(integer),hostelHolidaysByCourse.get(integer),"eveningAbMap",hlMap);
							eveningAbsMap.remove(integer);
							if(evngMapByCourse!=null && !evngMapByCourse.isEmpty()){
								eveningAbsMap.put(integer, evngMapByCourse);
							}
						}
					}
				}
				//get HlLeave by course id
				Map<Integer,Map<Integer,List<HlLeave>>> hlLeaveMapByCourse=transaction.getHLLeavesByCourse(absentiesListForm); 
				//remove the hlAdminIds of twossesAbsent,morninAbsent,eveningAbsent from the hlLeaves based on course and hlAdmission id's
				if(hlLeaveMapByCourse!=null && !hlLeaveMapByCourse.isEmpty()){
					Set<Integer> courseIds=hlLeaveMapByCourse.keySet();
					Iterator<Integer> iterator=courseIds.iterator();
					while (iterator.hasNext()) {
						Integer integer2 = (Integer) iterator.next();
						//if twoSessionAbsentMapByCourse contains course id
						
						//if morningAbsentMapByCourse contains course id
						if(morningAbsMap.containsKey(integer2)){
							Map<Date,List<Integer>> mngMap=getAbsentiesMapByCourseByRemovingFromLeaves(morningAbsMap.get(integer2),hlLeaveMapByCourse.get(integer2),"morningAbMap");
							morningAbsMap.remove(integer2);
							if(mngMap!=null && !mngMap.isEmpty()){
								morningAbsMap.put(integer2, mngMap);
							}
						}
						if(eveningAbsMap.containsKey(integer2)){
							Map<Date,List<Integer>> evngMap=getAbsentiesMapByCourseByRemovingFromLeaves(eveningAbsMap.get(integer2),hlLeaveMapByCourse.get(integer2),"eveningAbMap");
							eveningAbsMap.remove(integer2);
							if(evngMap!=null && !evngMap.isEmpty()){
								eveningAbsMap.put(integer2, evngMap);
							}
						}
					}
				}
				Map<Date,Set<Integer>> finalMrngAbsentMap=finalAbsentiesMap(morningAbsMap);
				Map<Date,Set<Integer>> finalEvngAbsentMap=finalAbsentiesMap(eveningAbsMap);
			//	Map<Date,Set<Integer>> finalTwoSessAbsentMap=finalAbsentiesMap(twoSessionAbsentMapByCourse);
				list=AbsentiesListHelper.getInstance().convertFinalAbsentiesToAbsentiesTo(finalMrngAbsentMap,finalEvngAbsentMap,hlMap,fromDate,absentiesListForm.getHolidaysFromSession());
			}
			return list;
	}
	/**
	 * remove the hlAdminIds by date and course from hostel holidays
	 * @param twoSessAbMap
	 * @param holidayList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,Map<Date,List<Integer>>> getAbsentMapBycourse(Map<Date,List<Integer>> twoSessAbMap,List<HostelHolidaysBo> holidayList,Map<Integer,HlAdmissionBo> hlMap)throws Exception{
		Map<Integer,Map<Date,List<Integer>>> map=new HashMap<Integer, Map<Date,List<Integer>>>();
		Map<Date,List<Integer>> mrngAbMap=new HashMap<Date, List<Integer>>();
		Map<Date,List<Integer>> evngAbMap=new HashMap<Date, List<Integer>>();
		Map<Date,List<Integer>> twoAbMap=new HashMap<Date, List<Integer>>();
		twoAbMap.putAll(twoSessAbMap);
		Set<Date> dates=twoSessAbMap.keySet();
		Iterator<Date> iterator=dates.iterator();
		while (iterator.hasNext()) {
			Date date = (Date) iterator.next();
				if(twoSessAbMap.get(date)!=null && !twoSessAbMap.get(date).isEmpty()){
					Map<Integer,Map<Integer,Map<Integer,List<Integer>>>> mapsByHostelBlockUnit=getMapsByHostelBlockAndUnit(twoSessAbMap.get(date),hlMap);
					Iterator<HostelHolidaysBo> iterator2=holidayList.iterator();
					while (iterator2.hasNext()) {
						HostelHolidaysBo hostelHolidaysBo = (HostelHolidaysBo) iterator2.next();
						if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning")
								&& date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("morning")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=twoAbMap.get(date);
								ids.removeAll(list);
								evngAbMap.put(date,ids);
								twoAbMap.remove(date);
							}
							break;
							
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("evening")
								&& date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("evening")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=twoAbMap.get(date);
								ids.removeAll(list);
								mrngAbMap.put(date,ids);
								twoAbMap.remove(date);
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning")
								&& date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("evening")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=twoAbMap.get(date);
								ids.removeAll(list);
								twoAbMap.remove(date);
								twoAbMap.put(date, ids);
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=twoAbMap.get(date);
								ids.removeAll(list);
								twoAbMap.remove(date);
								twoAbMap.put(date, ids);
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("evening")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=twoAbMap.get(date);
								ids.removeAll(list);
								mrngAbMap.put(date,ids);
								twoAbMap.remove(date);
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("evening")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=twoAbMap.get(date);
								ids.removeAll(list);
								twoAbMap.remove(date);
								twoAbMap.put(date, ids);
							}
							twoAbMap.remove(date);
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("morning")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=twoAbMap.get(date);
								ids.removeAll(list);
								evngAbMap.put(date,ids);
								twoAbMap.remove(date);
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())>0 && date.compareTo(hostelHolidaysBo.getHolidaysTo())<0){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=twoAbMap.get(date);
								ids.removeAll(list);
								twoAbMap.remove(date);
								twoAbMap.put(date, ids);
							}
							break;
						}else{
							continue;
						}
					}
				}
		}
		map.put(1, twoAbMap);
		map.put(2, mrngAbMap);
		map.put(3, evngAbMap);
		
		return map;
	}
	public Map<Date,List<Integer>> getMrngAbMapByCourse(Map<Date,List<Integer>> mrngAbsMap,List<HostelHolidaysBo> holidaysBos,String session,Map<Integer,HlAdmissionBo> hlMap)throws Exception{
		Map<Date,List<Integer>> map=new HashMap<Date, List<Integer>>();
		map.putAll(mrngAbsMap);
		Set<Date> dates=mrngAbsMap.keySet();
		Iterator<Date> iterator=dates.iterator();
		while (iterator.hasNext()) {
			Date date = (Date) iterator.next();
			if(map.get(date)!=null && !map.get(date).isEmpty()){
				Map<Integer,Map<Integer,Map<Integer,List<Integer>>>> mapsByHostelBlockUnit=getMapsByHostelBlockAndUnit(map.get(date),hlMap);
				Iterator<HostelHolidaysBo> iterator2=holidaysBos.iterator();
				while (iterator2.hasNext()) {
					HostelHolidaysBo hostelHolidaysBo = (HostelHolidaysBo) iterator2.next();
					if(session.equalsIgnoreCase("morningAbMap")){
						if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning")
							&& date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("morning")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning")
							&& date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("evening")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning")
							&& hostelHolidaysBo.getHolidaysFrom().compareTo(hostelHolidaysBo.getHolidaysTo())!=0){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && (hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("evening") ||
							hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("morning")) && hostelHolidaysBo.getHolidaysFrom().compareTo(hostelHolidaysBo.getHolidaysTo())!=0){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())>0 && date.compareTo(hostelHolidaysBo.getHolidaysTo())<0){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else{
							continue;
						}
					}else{
						if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("evening")
							&& date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("evening")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning")
							&& date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("evening")){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())==0 && (hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("morning") ||
							hostelHolidaysBo.getHolidaysFromSession().equalsIgnoreCase("evening")) && hostelHolidaysBo.getHolidaysFrom().compareTo(hostelHolidaysBo.getHolidaysTo())!=0){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysTo())==0 && hostelHolidaysBo.getHolidaysToSession().equalsIgnoreCase("evening") 
							&& hostelHolidaysBo.getHolidaysFrom().compareTo(hostelHolidaysBo.getHolidaysTo())!=0){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else if(date.compareTo(hostelHolidaysBo.getHolidaysFrom())>0 && date.compareTo(hostelHolidaysBo.getHolidaysTo())<0){
							List<Integer> list=getHlAdminIdsWhichAreNotInHolidays(mapsByHostelBlockUnit,hostelHolidaysBo);
							if(list!=null && !list.isEmpty()){
								List<Integer> ids=map.get(date);
								ids.removeAll(list);
								map.remove(date);
								if(ids!=null &&!ids.isEmpty()){
									map.put(date, ids);
								}
							}
							break;
						}else{
							continue;
						}
				}
			}
		}
		}
		return map;
	}
	public Map<Integer,Map<Date,List<Integer>>> getAbsentMapBycourseByRemovingFromLeaves(Map<Date,List<Integer>> twoSessAbsentMap,Map<Integer,List<HlLeave>> hlMap)throws Exception{
		Map<Integer,Map<Date,List<Integer>>> map=new HashMap<Integer, Map<Date,List<Integer>>>();
		Map<Date,List<Integer>> twosessAbMap=new HashMap<Date, List<Integer>>();
		Map<Date,List<Integer>> mngAbMap=new HashMap<Date, List<Integer>>();
		Map<Date,List<Integer>> evngAbMap=new HashMap<Date, List<Integer>>();
		if(twoSessAbsentMap!=null && !twoSessAbsentMap.isEmpty()){
			Set<Date> dates=twoSessAbsentMap.keySet();
			Iterator<Date> iterator=dates.iterator();
			while (iterator.hasNext()) {
				Date date = (Date) iterator.next();
				List<Integer> twoSessList=new ArrayList<Integer>();
				List<Integer> mngSessList=new ArrayList<Integer>();
				List<Integer> evngSessList=new ArrayList<Integer>();
				List<Integer> list=twoSessAbsentMap.get(date);
				Iterator<Integer> iterator2=list.iterator();
				while (iterator2.hasNext()) {
					Integer integer = (Integer) iterator2.next();
					if(hlMap.containsKey(integer)){
						List<HlLeave> hlLeaves=hlMap.get(integer);
						Iterator<HlLeave> iterator3=hlLeaves.iterator();
						int i=0;
						while (iterator3.hasNext()) {
							HlLeave hlLeave = (HlLeave) iterator3.next();
							if(date.compareTo(hlLeave.getLeaveFrom())==0 && hlLeave.getFromSession().equalsIgnoreCase("morning")
									&& date.compareTo(hlLeave.getLeaveTo())==0 && hlLeave.getToSession().equalsIgnoreCase("morning")){
								evngSessList.add(integer);
								break;
							}else if(date.compareTo(hlLeave.getLeaveFrom())==0 &&  hlLeave.getFromSession().equalsIgnoreCase("evening")
									&& date.compareTo(hlLeave.getLeaveTo())==0 &&  hlLeave.getToSession().equalsIgnoreCase("evening")){
								mngSessList.add(integer);
								break;
							}else if(date.compareTo(hlLeave.getLeaveFrom())==0 && hlLeave.getFromSession().equalsIgnoreCase("morning")
									&& date.compareTo(hlLeave.getLeaveTo())==0 && hlLeave.getToSession().equalsIgnoreCase("evening")){
								continue;
							}else if(date.compareTo(hlLeave.getLeaveFrom())==0 && hlLeave.getFromSession().equalsIgnoreCase("morning")){
								continue;
							}else if(date.compareTo(hlLeave.getLeaveFrom())==0 && hlLeave.getFromSession().equalsIgnoreCase("evening")){
								mngSessList.add(integer);
								break;
							}else if(date.compareTo(hlLeave.getLeaveTo())==0 && hlLeave.getToSession().equalsIgnoreCase("evening")){
								continue;
							}else if(date.compareTo(hlLeave.getLeaveTo())==0 && hlLeave.getToSession().equalsIgnoreCase("morning")){
								evngSessList.add(integer);
								break;
							}else if(date.compareTo(hlLeave.getLeaveFrom())>0 && date.compareTo(hlLeave.getLeaveTo())<0){
								continue;
							}else{
								i++;
							}
						}
						if(i==hlLeaves.size()){
							twoSessList.add(integer);
						}
					}else{
						twoSessList.add(integer);
					}
				}
				if(twoSessList!=null && !twoSessList.isEmpty()){
					twosessAbMap.put(date, twoSessList);
				}
				if(mngSessList!=null && !mngSessList.isEmpty()){
					mngAbMap.put(date, mngSessList);
				}
				if(evngSessList!=null && !evngSessList.isEmpty()){
					evngAbMap.put(date, evngSessList);
				}
			}
		}
		map.put(1, twosessAbMap);
		map.put(2, mngAbMap);
		map.put(3, evngAbMap);
		return map;
	}
	public Map<Date,List<Integer>> getAbsentiesMapByCourseByRemovingFromLeaves(Map<Date,List<Integer>> mrngMap,Map<Integer,List<HlLeave>> leaveMap,String session)throws Exception{
		Map<Date,List<Integer>> map=new HashMap<Date, List<Integer>>();
		if(mrngMap!=null && !mrngMap.isEmpty()){
			Set<Date> dates=mrngMap.keySet();
			Iterator<Date> iterator=dates.iterator();
			while (iterator.hasNext()) {
				Date date = (Date) iterator.next();
				List<Integer> mngSessList=new ArrayList<Integer>();
				List<Integer> list=mrngMap.get(date);
				Iterator<Integer> iterator2=list.iterator();
				while (iterator2.hasNext()) {
					Integer integer = (Integer) iterator2.next();
					if(leaveMap.containsKey(integer)){
						List<HlLeave> hlLeaves=leaveMap.get(integer);
						Iterator<HlLeave> iterator3=hlLeaves.iterator();
						int i=0;
						while (iterator3.hasNext()){
							HlLeave hlLeave = (HlLeave) iterator3.next();
							if(session.equalsIgnoreCase("morningAbMap")){
								if(date.compareTo(hlLeave.getLeaveFrom())==0 && hlLeave.getFromSession().equalsIgnoreCase("morning")
										&& date.compareTo(hlLeave.getLeaveTo())==0 && hlLeave.getToSession().equalsIgnoreCase("morning")){
									i++;
									break;
								}else if(date.compareTo(hlLeave.getLeaveFrom())==0 && hlLeave.getFromSession().equalsIgnoreCase("morning")
										&& date.compareTo(hlLeave.getLeaveTo())==0 && hlLeave.getToSession().equalsIgnoreCase("evening")){
									i++;
									break;
								}else if(date.compareTo(hlLeave.getLeaveFrom())==0 && hlLeave.getFromSession().equalsIgnoreCase("morning")
										&& hlLeave.getLeaveFrom().compareTo(hlLeave.getLeaveTo())!=0){
									i++;
									break;
								}else if(date.compareTo(hlLeave.getLeaveTo())==0 && (hlLeave.getToSession().equalsIgnoreCase("evening") ||
										hlLeave.getToSession().equalsIgnoreCase("morning")) && hlLeave.getLeaveFrom().compareTo(hlLeave.getLeaveTo())!=0){
									i++;
									break;
								}else if(date.compareTo(hlLeave.getLeaveFrom())>0 && date.compareTo(hlLeave.getLeaveTo())<0){
									i++;
									break;
								}else{
									continue;
								}
							}else{
								if(date.compareTo(hlLeave.getLeaveFrom())==0 && hlLeave.getFromSession().equalsIgnoreCase("evening")
										&& date.compareTo(hlLeave.getLeaveTo())==0 && hlLeave.getToSession().equalsIgnoreCase("evening")){
									i++;
									break;
								}else if(date.compareTo(hlLeave.getLeaveFrom())==0 && hlLeave.getFromSession().equalsIgnoreCase("morning")
										&& date.compareTo(hlLeave.getLeaveTo())==0 && hlLeave.getToSession().equalsIgnoreCase("evening")){
									i++;
									break;
								}else if(date.compareTo(hlLeave.getLeaveFrom())==0 && (hlLeave.getFromSession().equalsIgnoreCase("morning") ||
										hlLeave.getFromSession().equalsIgnoreCase("evening")) && hlLeave.getLeaveFrom().compareTo(hlLeave.getLeaveTo())!=0){
									i++;
									break;
								}else if(date.compareTo(hlLeave.getLeaveTo())==0 && hlLeave.getToSession().equalsIgnoreCase("evening") 
										&& hlLeave.getLeaveFrom().compareTo(hlLeave.getLeaveTo())!=0){
									i++;
									break;
								}else if(date.compareTo(hlLeave.getLeaveFrom())>0 && date.compareTo(hlLeave.getLeaveTo())<0){
									i++;
									break;
								}else{
									continue;
								}
							}
						}

						if(i==0){
							mngSessList.add(integer);
						}
					}else{
						mngSessList.add(integer);
					}
				}
				map.put(date, mngSessList);
			}
		}
		return map;
	}
	public Map<Date,Set<Integer>> finalAbsentiesMap(Map<Integer,Map<Date,List<Integer>>> abMap)throws Exception{
		Map<Date,Set<Integer>> map=new HashMap<Date, Set<Integer>>();
		if(abMap!=null && !abMap.isEmpty()){
			Set<Integer> courseIds=abMap.keySet();
			Iterator<Integer> iterator=courseIds.iterator();
			while (iterator.hasNext()) {
				Integer integer = (Integer) iterator.next();
				Map<Date,List<Integer>> dateMap=abMap.get(integer);
				Set<Integer> list=null;
				Set<Date> dates=dateMap.keySet();
				Iterator<Date> iterator2=dates.iterator();
				while (iterator2.hasNext()) {
					Date date = (Date) iterator2.next();
					if(map.containsKey(date)){
						list=new HashSet<Integer>();
						List<Integer> list1=dateMap.get(date);
						list.addAll(map.get(date));
						list.addAll(list1);
						map.remove(date);
						map.put(date, list);
						
					}else{
						list=new HashSet<Integer>();
						list.addAll(dateMap.get(date));
						map.put(date, list);
					}
				}
				
			}
		}
		return map;
	}
	/**
	 * mail for student
	 * @throws Exception
	 */
	public boolean sendMailForStudentOrParent(List<AbsentiesListTo> list,String mailFor)throws Exception{
		Map<Integer,List<AbsentiesListTo>> map=new HashMap<Integer, List<AbsentiesListTo>>();
		Iterator<AbsentiesListTo> iterator=list.iterator();
		while (iterator.hasNext()) {
			List<AbsentiesListTo> list1=null;
			AbsentiesListTo absentiesListTo = (AbsentiesListTo) iterator.next();
			if(map.containsKey(absentiesListTo.getHlAdminId())){
		 		list1=map.get(absentiesListTo.getHlAdminId());
				list1.add(absentiesListTo);
				map.remove(absentiesListTo.getHlAdminId());
				map.put(absentiesListTo.getHlAdminId(), list1);
			}else{
				list1=new ArrayList<AbsentiesListTo>();
				list1.add(absentiesListTo);
				map.put(absentiesListTo.getHlAdminId(), list1);
			}
		}
		boolean sentMail=false;
		Set<Integer> keySet=map.keySet();
		Iterator<Integer> iterator3=keySet.iterator();
		while (iterator3.hasNext()) {
			Integer integer = (Integer) iterator3.next();
			List<AbsentiesListTo> absentiesListTo=map.get(integer);
			if(mailFor.equalsIgnoreCase("Parent")){
				sentMail=sendMailToParentOrStudent(CMSConstants.HOSTEL_ABSENT_MAIL_FOR_PARENT,absentiesListTo,mailFor);
			}else{
				sentMail=sendMailToParentOrStudent(CMSConstants.HOSTEL_ABSENT_MAIL_FOR_STUDENT,absentiesListTo,mailFor);
			}
		}
    return sentMail;
	}
	public boolean sendMail(String mailID, String sub,String message, String fromName, String fromAddress) {
		boolean sent=false;
			String toAddress=mailID;
			// MAIL TO CONSTRUCTION
			String subject=sub;
			String msg=message;
			MailTO mailto=new MailTO();
			mailto.setFromAddress(fromAddress);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(fromName);
			
			sent=CommonUtil.sendMail(mailto);
		return sent;
}
	public boolean sendSMSToStudentOrParent(String mobileNo,String templateName,String regNo,String studentName,String date,String session,String parentName) throws Exception{
		boolean sentSms=false;
		Properties prop = new Properties();
        InputStream in1 = AbsentiesListHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop.load(in1);
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templateName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO,regNo);
			desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME,studentName);
			desc=desc.replace(CMSConstants.TEMPLATE_DATE,date);
			desc=desc.replace(CMSConstants.TEMPLATE_SESSION,session);
			desc=desc.replace(CMSConstants.TEMPLATE_PARENT_NAME,parentName);
		}
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160) && desc!=null && !desc.isEmpty()){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(mobileNo);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			sentSms=PropertyUtil.getInstance().save(mob);
		}
		return sentSms;
	}
	/**
	 * mail for student
	 * @throws Exception
	 */
	public boolean sendSmsForStudentOrParent(List<AbsentiesListTo> list,String smsFor)throws Exception{
		Map<Integer,List<AbsentiesListTo>> map=new HashMap<Integer, List<AbsentiesListTo>>();
		Iterator<AbsentiesListTo> iterator=list.iterator();
		while (iterator.hasNext()) {
			List<AbsentiesListTo> list1=null;
			AbsentiesListTo absentiesListTo = (AbsentiesListTo) iterator.next();
			if(map.containsKey(absentiesListTo.getHlAdminId())){
		 		list1=map.get(absentiesListTo.getHlAdminId());
				list1.add(absentiesListTo);
				map.remove(absentiesListTo.getHlAdminId());
				map.put(absentiesListTo.getHlAdminId(), list1);
			}else{
				list1=new ArrayList<AbsentiesListTo>();
				list1.add(absentiesListTo);
				map.put(absentiesListTo.getHlAdminId(), list1);
			}
		}
		
		boolean sentSms=false;
		Map<Integer,AbsentiesListTo> map2=new HashMap<Integer, AbsentiesListTo>();
		Set<Integer> hlAdminIds=map.keySet();
		Iterator<Integer> iterator2=hlAdminIds.iterator();
		while (iterator2.hasNext()) {
			Integer integer = (Integer) iterator2.next();
			List<AbsentiesListTo> list2=map.get(integer);
			Date date=null;
			Iterator<AbsentiesListTo> iterator3=list2.iterator();
			while (iterator3.hasNext()) {
				AbsentiesListTo absentiesListTo = (AbsentiesListTo) iterator3.next();
				Date abDate=CommonUtil.ConvertStringToDate(absentiesListTo.getDate());
				if(date!=null && abDate.compareTo(date)>0){
					date=abDate;
					if(map2.containsKey(integer)){
						map2.remove(integer);
						map2.put(integer, absentiesListTo);
					}
				}else{
					date=abDate;
					map2.put(integer, absentiesListTo);
				}
			}
		}
		
		Set<Integer> keySet=map2.keySet();
		Iterator<Integer> iterator3=keySet.iterator();
		while (iterator3.hasNext()) {
			String studentMobileNo="91";
			String parentMobileNo="91";
			Integer integer = (Integer) iterator3.next();
			AbsentiesListTo absentiesListTo=map2.get(integer);
			if(absentiesListTo.getContactNo()!=null && !absentiesListTo.getContactNo().isEmpty()){
				studentMobileNo=studentMobileNo+absentiesListTo.getContactNo();
			}
			if(absentiesListTo.getParentContactNo()!=null && !absentiesListTo.getParentContactNo().isEmpty()){
				parentMobileNo=parentMobileNo+absentiesListTo.getParentContactNo();
			}
			if(smsFor.equalsIgnoreCase("Student")){
				sentSms=sendSMSToStudentOrParent(studentMobileNo,CMSConstants.HOSTEL_ABSENT_SMS_FOR_STUDENT,absentiesListTo.getRegNo(),absentiesListTo.getStudentName(),absentiesListTo.getDate(),absentiesListTo.getSession(),absentiesListTo.getParentName());
			}else{
				sentSms=sendSMSToStudentOrParent(parentMobileNo,CMSConstants.HOSTEL_ABSENT_SMS_FOR_PARENT,absentiesListTo.getRegNo(),absentiesListTo.getStudentName(),absentiesListTo.getDate(),absentiesListTo.getSession(),absentiesListTo.getParentName());
			}
		}
    return sentSms;
	}
	public boolean sendMailToParentOrStudent(String templateName,List<AbsentiesListTo> absentiesListTos,String mailfor) throws Exception{
	boolean sent=false;
	Properties prop = new Properties();
	String desc="";
	try {
		InputStream inStr = CommonUtil.class.getClassLoader()
				.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(inStr);
	} catch (FileNotFoundException e) {
	log.error("Unable to read properties file...", e);
	} catch (IOException e) {
		log.error("Unable to read properties file...", e);
	}
		List<GroupTemplate> list=null;
		//get template and replace dynamic data
		TemplateHandler temphandle=TemplateHandler.getInstance();
		 list= temphandle.getDuplicateCheckList(templateName);
		
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
			String emailID="";
			String studentName="";
			String parentName="";
			String hostelName="";
			String block="";
			String unit="";
			String room="";
			String bed="";
			String regNo="";
			String absentDetails = "<table width = '100%'> <tr> <td width='20'> </td><td><table style='border:1px solid #000000; font-family: verdana; font-size:10pt;' rules='all'><tr> <th> Date </th> <th> Session </th> </tr> ";
			Iterator<AbsentiesListTo> iterator=absentiesListTos.iterator();
			while (iterator.hasNext()) {
				AbsentiesListTo absentiesListTo = (AbsentiesListTo) iterator.next();
				if(absentiesListTo.getRegNo()!=null && !absentiesListTo.getRegNo().isEmpty()){
					regNo=absentiesListTo.getRegNo();
				}
				if(absentiesListTo.getStudentName()!=null && !absentiesListTo.getStudentName().isEmpty()){
					studentName=absentiesListTo.getStudentName();
				}
				if(absentiesListTo.getHostelName()!=null && !absentiesListTo.getHostelName().isEmpty()){
					hostelName=absentiesListTo.getHostelName();
				}
				if(absentiesListTo.getBlock()!=null && !absentiesListTo.getBlock().isEmpty()){
					block=absentiesListTo.getBlock();
				}
				if(absentiesListTo.getUnit()!=null && !absentiesListTo.getUnit().isEmpty()){
					unit=absentiesListTo.getUnit();
				}
				if(absentiesListTo.getRoom()!=null && !absentiesListTo.getRoom().isEmpty()){
					room=absentiesListTo.getRoom();
				}
				if(absentiesListTo.getBed()!=null && !absentiesListTo.getBed().isEmpty()){
					bed=absentiesListTo.getBed();
				}
				if(absentiesListTo.getParentName()!=null && !absentiesListTo.getParentName().isEmpty()){
					parentName=absentiesListTo.getParentName();
				}
				if(absentiesListTo.getParentMailId()!=null && !absentiesListTo.getParentMailId().isEmpty() && mailfor.equalsIgnoreCase("Parent")){
					emailID=absentiesListTo.getParentMailId();
				}else if(absentiesListTo.getMailId()!=null && !absentiesListTo.getMailId().isEmpty() && mailfor.equalsIgnoreCase("Student")){
					emailID=absentiesListTo.getMailId();
				}
				if(absentiesListTo.getDate()!=null && !absentiesListTo.getDate().isEmpty() && absentiesListTo.getSession()!=null
						&& !absentiesListTo.getSession().isEmpty()){
					absentDetails = absentDetails + "<tr><td> "+absentiesListTo.getDate()+"</td> <td align='right'>"+absentiesListTo.getSession()+"</td> </tr>";
				}
				
			}
			absentDetails=absentDetails+"</table> </td> </tr> </table>";
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO,regNo);
			desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME,studentName);
			desc=desc.replace(CMSConstants.TEMPLATE_HOSTEL_NAME,hostelName);
			desc=desc.replace(CMSConstants.TEMPLATE_AUDITORIUM_BLOCK,block);
			desc=desc.replace(CMSConstants.TEMPLATE_UNIT,unit);
			desc=desc.replace(CMSConstants.TEMPLATE_ROOM,room);
			desc=desc.replace(CMSConstants.TEMPLATE_BED,bed);
			desc=desc.replace(CMSConstants.TEMPLATE_PARENT_NAME,parentName);
			desc=desc.replace(CMSConstants.TEMPLATE_ABSENT_DETAILS,absentDetails);
			
			String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
			String fromAddress=CMSConstants.MAIL_USERID;
			String subject="Hostel Absence Intimation Details";
			 sent=sendMail(emailID, subject, desc, fromName, fromAddress);
		}
		return sent;
	}
	public Map<Integer,Map<Integer,Map<Integer,List<Integer>>>> getMapsByHostelBlockAndUnit(List<Integer> twoSessAbMap,Map<Integer,HlAdmissionBo> hlMap)throws Exception{
		Map<Integer,Map<Integer,Map<Integer,List<Integer>>>> map=new HashMap<Integer, Map<Integer,Map<Integer,List<Integer>>>>();
		Iterator<Integer> iterator=twoSessAbMap.iterator();
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			List<Integer> list=null;
			Map<Integer,Map<Integer, List<Integer>>> map1=null;
			Map<Integer,List<Integer>> map2=null;
			if(hlMap.containsKey(integer)){
				HlAdmissionBo hlAdmissionBo=hlMap.get(integer);
				if(map.containsKey(hlAdmissionBo.getHostelId().getId())){
					if(hlAdmissionBo.getRoomId()!=null){
						map1=map.get(hlAdmissionBo.getHostelId().getId());
						if(map1.containsKey(hlAdmissionBo.getRoomId().getHlBlock().getId())){
							map2=map1.get(hlAdmissionBo.getRoomId().getHlBlock().getId());
							if(map2.containsKey(hlAdmissionBo.getRoomId().getHlUnit().getId())){
								list=map2.get(hlAdmissionBo.getRoomId().getHlUnit().getId());
								list.add(hlAdmissionBo.getId());
								map2.remove(hlAdmissionBo.getRoomId().getHlUnit().getId());
								map2.put(hlAdmissionBo.getRoomId().getHlUnit().getId(), list);
								map1.put(hlAdmissionBo.getRoomId().getHlBlock().getId(), map2);
								map.put(hlAdmissionBo.getHostelId().getId(), map1);
							}else{
								list=new ArrayList<Integer>();
								list.add(hlAdmissionBo.getId());
								map2.put(hlAdmissionBo.getRoomId().getHlUnit().getId(), list);
								map1.put(hlAdmissionBo.getRoomId().getHlBlock().getId(), map2);
								map.put(hlAdmissionBo.getHostelId().getId(), map1);
							}
						}else{
							map2=new HashMap<Integer, List<Integer>>();
							list=new ArrayList<Integer>();
							list.add(hlAdmissionBo.getId());
							map2.put(hlAdmissionBo.getRoomId().getHlUnit().getId(), list);
							map1.put(hlAdmissionBo.getRoomId().getHlBlock().getId(), map2);
							map.put(hlAdmissionBo.getHostelId().getId(), map1);
						}
					}
				}else{
					list=new ArrayList<Integer>();
					map1=new HashMap<Integer, Map<Integer,List<Integer>>>();
					map2=new HashMap<Integer, List<Integer>>();
					list.add(hlAdmissionBo.getId());
					if(hlAdmissionBo.getRoomId()!=null){
						map2.put(hlAdmissionBo.getRoomId().getHlUnit().getId(), list);
						map1.put(hlAdmissionBo.getRoomId().getHlBlock().getId(), map2);
					}
					map.put(hlAdmissionBo.getHostelId().getId(), map1);
				}
			}
		}
		return map;
	}
	/** 
	 * get the hlAdminIds which are in hostelHolidays by hostel block and unit
	 * @param map
	 * @param hostelHolidaysBo
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getHlAdminIdsWhichAreNotInHolidays(Map<Integer,Map<Integer,Map<Integer,List<Integer>>>> map,HostelHolidaysBo hostelHolidaysBo)throws Exception{
		List<Integer> list=new ArrayList<Integer>();
		if(map.containsKey(hostelHolidaysBo.getHostelId().getId())){
			Map<Integer,Map<Integer, List<Integer>>> hlAdminIdsByBlockAndUnit=map.get(hostelHolidaysBo.getHostelId().getId());
			if(hlAdminIdsByBlockAndUnit.containsKey(hostelHolidaysBo.getBlockId().getId())){
				Map<Integer,List<Integer>> hlAdminsByUnit=hlAdminIdsByBlockAndUnit.get(hostelHolidaysBo.getBlockId().getId());
				if(hlAdminsByUnit.containsKey(hostelHolidaysBo.getUnitId().getId())){
					List<Integer> hlAdminIds=hlAdminsByUnit.get(hostelHolidaysBo.getUnitId().getId());
					if(hlAdminIds!=null && !hlAdminIds.isEmpty()){
						list.addAll(hlAdminIds);
					}
				}
			}
		}
		return list;
	}
	/**
	 * @param absentiesListForm
	 * @return
	 * @throws Exception
	 */
	public List<AbsentiesListTo> getAbsentiesList(AbsentiesListForm absentiesListForm) throws Exception{
		List<AbsentiesListTo> tos = new ArrayList<AbsentiesListTo>();
		Map<Integer,List<Integer>> hlAdminListByCourse=transaction.getHlAdmissionBosByCourse(AbsentiesListHelper.getInstance().getHlAdmissionBosByCourse(absentiesListForm));
		if(hlAdminListByCourse !=null && !hlAdminListByCourse.isEmpty()){
			Map<Integer,List<Integer>> presentMapByCourse=transaction.getPresentMapByCourseNew(absentiesListForm);
			Map<Integer,List<Integer>> leaveMapByCourse=transaction.getLeavesByCourseWise(absentiesListForm);
			
			Iterator<Entry<Integer, List<Integer>>> iterator = hlAdminListByCourse.entrySet().iterator();
			List<Integer> absenteesList = new ArrayList<Integer>();
			while (iterator.hasNext()) {
				Map.Entry<Integer, List<Integer>> entry = (Map.Entry<Integer, List<Integer>>) iterator.next();
				List<Integer> admIds = entry.getValue();
				if(presentMapByCourse.containsKey(entry.getKey())){
					admIds.removeAll(presentMapByCourse.get(entry.getKey()));
				}
				if(leaveMapByCourse.containsKey(entry.getKey())){
					admIds.removeAll(leaveMapByCourse.get(entry.getKey()));
				}
				// /* code added by chandra for if student having hostel exemption then remove that student from absenttees list
				Map<Integer,List<Integer>> hlExemptionMapByCourse=transaction.getExemptionListByCourseWise(absentiesListForm);
				if(hlExemptionMapByCourse.containsKey(entry.getKey())){
					admIds.removeAll(hlExemptionMapByCourse.get(entry.getKey()));
				}
				
				// if the student hostel is having option  Punching Exemption on Sunday Morning Session then remove sunday morning session from absenties list
				String date1=absentiesListForm.getHolidaysFrom();
				Date date2=CommonUtil.ConvertStringToDate(date1);
				Calendar date = Calendar.getInstance();
			    date.setTime(date2);
				if(date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && absentiesListForm.getHolidaysFromSession().equalsIgnoreCase("Morning")){
					List<Integer> sundyExepList=transaction.getSundayMorningExemptionStudentsList(admIds);
					if(sundyExepList !=null && !sundyExepList.isEmpty()){
						admIds.removeAll(sundyExepList);
					}
				}
				
				// code added by chandra  */
				absenteesList.addAll(admIds);
			}
			if(!absenteesList.isEmpty()){
				Map<Integer, Map<Integer, Map<Integer, List<Integer>>>> holidaysMap = transaction.getHolidaysMap(absentiesListForm);
				List<HlAdmissionBo> absenteesBos = transaction.getHlAdmissionBos(absenteesList);
				tos = AbsentiesListHelper.getInstance().convertBOToTO(absenteesBos,holidaysMap,absentiesListForm);
			}
		}
		
		
		return tos;
	}
	public void getBlockAndUnit(AbsentiesListForm absentiesListForm) throws Exception{
		if(absentiesListForm.getHostelId()!=null && !absentiesListForm.getHostelId().isEmpty()){
			Map<Integer, String> blockMap = CommonAjaxHandler.getInstance().getBlockByHostel(Integer.parseInt(absentiesListForm.getHostelId()));
			if(!blockMap.isEmpty()){
				absentiesListForm.setBlockMap(blockMap);
			}
		}
		if(absentiesListForm.getBlockId()!=null && !absentiesListForm.getBlockId().isEmpty()){
			Map<Integer, String> unitMap = CommonAjaxHandler.getInstance().getUnitByBlock(Integer.parseInt(absentiesListForm.getBlockId()));
			if(!unitMap.isEmpty()){
				absentiesListForm.setUnitMap(unitMap);
			}
		}
	}
}
