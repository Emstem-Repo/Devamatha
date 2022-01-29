package com.kp.cms.helpers.attendance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceClass;
import com.kp.cms.forms.attendance.ChangeSubjectForm;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.ChangeSubjectTo;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.attandance.IChangeSubject;
import com.kp.cms.transactionsimpl.attendance.ChangeSubjectTransactionImpl;
import com.kp.cms.utilities.CommonUtil;



public class ChangeSubjectHelper {
	private static volatile ChangeSubjectHelper changeSubjectHelper = null;
	private static final Log log = LogFactory.getLog(ChangeSubjectHelper.class);
	
	/**
	 * @return
	 */
	public static ChangeSubjectHelper getInstance() {
		if (changeSubjectHelper == null) {
			changeSubjectHelper = new ChangeSubjectHelper();
		}
		return changeSubjectHelper;
	}
	
	/**
	 * @param classesList
	 * @return
	 * @throws Exception
	 */
	public List<AttendanceTO> convertBOtoTO(List<Attendance> boList,ChangeSubjectForm form) throws Exception{
		IChangeSubject transaction=ChangeSubjectTransactionImpl.getInstance();
		List<AttendanceTO> tos= new ArrayList<AttendanceTO>();
		Set<String> s=new HashSet<String>();
		if(boList != null && !boList.isEmpty()){
			Iterator<Attendance> iterator = boList.iterator();
			while (iterator.hasNext()) {
				Attendance bo = (Attendance) iterator.next();
				form.setSubjectName(bo.getSubject().getName());
				AttendanceTO to = new AttendanceTO();
				to.setId(bo.getId());
				to.setDates(formatDate(bo.getAttendanceDate()));
				Set<AttendanceClass> set = bo.getAttendanceClasses();
				if(set != null){
					String className ="";
					Iterator<AttendanceClass> iterator2 = set.iterator();
					while (iterator2.hasNext()) {
						AttendanceClass attendanceClass = (AttendanceClass) iterator2.next();
						if(className.isEmpty()){
							className= attendanceClass.getClassSchemewise().getClasses().getName();
						}else{
							className = className + ", " +attendanceClass.getClassSchemewise().getClasses().getName();
						}
					}
					String sentence= className;
		    		String[] words = sentence.split(",");  
		    		for (String word : words){ 
		    			s.add(word);	
		    		}
					to.setClassName(className);
				}
				tos.add(to);
			}
		}
		List<ChangeSubjectTo> list=transaction.getSubjectNamesByClassNames(s);
		form.setSubjectList(list);
		
		 return tos;
	}
	/**
	 * @param map
	 * @param form
	 * @return
	 * @throws Exception
	 */
	/*public List<ChangeSubjectTo> convertMapToList(Map<Date,String> map,ChangeSubjectForm form) throws Exception{
		IChangeSubject transaction=ChangeSubjectTransactionImpl.getInstance();
		List<ChangeSubjectTo> verificationList=new ArrayList<ChangeSubjectTo>();
		if(map!=null && !map.isEmpty()){
				Iterator<Entry<Date, String>> iterator = map.entrySet().iterator();
				Set<String> s=new HashSet<String>();
				while (iterator.hasNext()) {
					ChangeSubjectTo to=new ChangeSubjectTo();
					Map.Entry<Date, String> entry = (Map.Entry<Date, String>) iterator.next();
					to.setClassname(entry.getValue());
					String date = formatDate(entry.getKey());
					to.setDate(date);
					to.setSqlDate(entry.getKey());
					verificationList.add(to);
					
					String sentence= entry.getValue();
		    		String[] words = sentence.split(",");  
		    		for (String word : words){ 
		    			s.add(word);	
		    		}
				}
				List<ChangeSubjectTo> list=transaction.getSubjectNamesByClassNames(s);
				form.setSubjectList(list);
		}
		return verificationList;
		}*/
	/**
	 * @param date
	 * @return
	 */
	public String formatDate(Date date){
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String newDate=formatter.format(date);
		return newDate;
	}
}
