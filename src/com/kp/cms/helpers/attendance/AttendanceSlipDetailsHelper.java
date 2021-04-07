package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.StringTokenizer;
import com.kp.cms.actions.attendance.AttendanceEntryAction;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceClass;
import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.utilities.CommonUtil;
//AttendanceSlipDetailsHelper
public class AttendanceSlipDetailsHelper {
	private static AttendanceSlipDetailsHelper attendanceSlipDetailsHelper=new AttendanceSlipDetailsHelper();
	private static final Log log = LogFactory.getLog(AttendanceEntryAction.class);
	public static AttendanceSlipDetailsHelper getInstance() {
		// TODO Auto-generated method stub
		return attendanceSlipDetailsHelper;
	}
	public String getSlipDetailsSearchQuery(AttendanceEntryForm attendanceEntryForm) {
		// TODO Auto-generated method stub
		log.info("Start getSlipDetailsSearchQuery of AttendanceSlipDetailsHelper");
		
		 //String periods=getPeriods(attendanceEntryForm);
		
		String 	query="select ac.attendance from AttendanceClass ac" +
				" where ac.attendance.attendanceDate between '"+CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getFromDate())+ "' and '"+CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getToDate())+"'";
						
		StringBuilder intType =new StringBuilder();
		if (attendanceEntryForm.getClasses().length > 0) {
			String [] tempArray = attendanceEntryForm.getClasses();
			
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
		}
		query = query+ " and ac.classSchemewise.id in ("+ intType+") and ac.attendance.isCanceled=0 group by ac.attendance.id order by ac.attendance.attendanceDate";
		log.info("End getSlipDetailsSearchQuery of AttendanceSlipDetailsHelper");
		return query;
	}
	
	public String getPeriods(AttendanceEntryForm attendanceEntryForm) {
		// TODO Auto-generated method stub
		log.info("Start getPeriods of AttendanceSlipDetailsHelper");
		String periods="select p.period.periodName from AttendanceClass ac join ac.attendance.attendancePeriods p "+
		"where ac.classSchemewise.id in (";
		StringBuilder intType =new StringBuilder();
		if (attendanceEntryForm.getClasses().length > 0) {
			String [] tempArray = attendanceEntryForm.getClasses();
			
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
		}
		periods=periods+intType+") group by p.period.periodName order by p.period.periodName ";
		log.info("End getPeriods of AttendanceSlipDetailsHelper");
		return periods;
	}
	
	public static String getClassNames(AttendanceEntryForm attendanceEntryForm){
		log.info("Start getClassNames of AttendanceSlipDetailsHelper");
		String classNames="select c.classes.name from ClassSchemewise c where c.id in (";
		StringBuilder intType =new StringBuilder();
		if (attendanceEntryForm.getClasses().length > 0) {
			String [] tempArray = attendanceEntryForm.getClasses();
			
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
		}
		classNames=classNames+intType+")";
		log.info("End getClassNames of AttendanceSlipDetailsHelper");
		return classNames;
	}
	
	public static String getClasses(List<String> classesList){
		StringBuilder className =new StringBuilder();
		if(classesList.size()>0){
			for(int i=0;i<classesList.size();i++){
				className.append(classesList.get(i));
				if(i<classesList.size()-1){
					className.append(",");
				}
			}
		}
		return className.toString();
	}
	
	public Map<String, Map<String,List<AttendanceTO>>> convertBotoTo(List<Attendance> attendaceList, List<String> periodList,AttendanceEntryForm attendanceEntryForm, HttpServletRequest request) {
		Map<String,String> subjectMap=new TreeMap<String, String>();
		Map<String, Map<String,List<AttendanceTO>>> attendanceMap = new TreeMap<String, Map<String,List<AttendanceTO>>>();
		String[] classes=attendanceEntryForm.getClasses();
		List<String> classlist=Arrays.asList(classes);
		List<Integer> classlistval=new ArrayList<Integer>();
		Iterator<String> classlistIterator=classlist.iterator();
//		Map<String,String> dayMap=new HashMap<String, String>();
		while(classlistIterator.hasNext()){
			String classId=classlistIterator.next();
			if(classId!=null && !classId.isEmpty())
			classlistval.add(Integer.parseInt(classId));
		}
		Iterator<Attendance> attendanceListIterator=attendaceList.iterator();
		String pervPeriodName=null;
		while(attendanceListIterator.hasNext()){
			Attendance attendanceBO=attendanceListIterator.next();
			String attendanceDate=CommonUtil.ConvertStringToDateFormat(attendanceBO.getAttendanceDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy");
			if(!attendanceMap.containsKey(attendanceDate)){
				Map<String,List<AttendanceTO>> periodMap=new TreeMap<String,List<AttendanceTO>>();
				Iterator<String> periodListIterator=periodList.iterator();
				while(periodListIterator.hasNext()){
					periodMap.put(periodListIterator.next(),new ArrayList<AttendanceTO>());
				}
				Set<AttendancePeriod> attendancePeriods=attendanceBO.getAttendancePeriods();
				Iterator<AttendancePeriod> attendancePeriodsiIterator=attendancePeriods.iterator();
				while(attendancePeriodsiIterator.hasNext()){
					AttendancePeriod attendancePeriod=attendancePeriodsiIterator.next();
					if(classlistval.contains(attendancePeriod.getPeriod().getClassSchemewise().getId())){
					pervPeriodName=attendancePeriod.getPeriod().getPeriodName();
					if(periodList.contains(pervPeriodName)){
						List<AttendanceTO> periodMapList=periodMap.get(pervPeriodName);
						periodMapList.add(getAttendanceTO(attendanceBO,attendanceEntryForm,subjectMap));
						periodMap.put(pervPeriodName,periodMapList);
					}
					}
				}
				attendanceMap.put(attendanceDate,periodMap);
			}else{
				Map<String,List<AttendanceTO>> periodMap=attendanceMap.get(attendanceDate);
				Set<AttendancePeriod> attendancePeriods=attendanceBO.getAttendancePeriods();
				Iterator<AttendancePeriod> attendancePeriodsiIterator=attendancePeriods.iterator();
				while(attendancePeriodsiIterator.hasNext()){
						AttendancePeriod attendancePeriod=attendancePeriodsiIterator.next();
						if(classlistval.contains(attendancePeriod.getPeriod().getClassSchemewise().getId())){			
							String periodName=attendancePeriod.getPeriod().getPeriodName();
							if(periodList.contains(periodName)){
							List<AttendanceTO> periodMapList=periodMap.get(periodName);
							periodMapList.add(getAttendanceTO(attendanceBO,attendanceEntryForm,subjectMap));
							periodMap.put(periodName,periodMapList);
							}
						}
				}
				attendanceMap.put(attendanceDate, periodMap);
			}
		}
//		HttpSession session=request.getSession();
//		session.setAttribute("dayMap",dayMap);
		return attendanceMap;
	}
	
	public AttendanceTO getAttendanceTO(Attendance attendance,AttendanceEntryForm attendanceEntryForm,Map<String,String> subjectMap){
				AttendanceTO attendanceTO=new AttendanceTO();
				if(attendance != null){
					attendanceTO.setSubject(attendance.getSubject().getCode());
					attendanceTO.setSubjectName(attendance.getSubject().getName());
						if(!subjectMap.containsKey(attendance.getSubject().getCode())){
							subjectMap.put(attendance.getSubject().getCode(),attendance.getSubject().getName());
						}
						attendanceEntryForm.setSubMap(subjectMap);
					
					attendanceTO.setDates(CommonUtil.ConvertStringToDateFormat(attendance.getAttendanceDate().toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
					Set<AttendanceInstructor> instructorSet=attendance.getAttendanceInstructors();
					if(instructorSet!=null && !instructorSet.isEmpty()){
						String instructorName="";
						Iterator<AttendanceInstructor> instItr=instructorSet.iterator();
						while (instItr.hasNext()) {
							AttendanceInstructor attendanceInstructor = (AttendanceInstructor) instItr.next();
							if(instructorName.isEmpty()){
								instructorName=attendanceInstructor.getUsers().getUserName();
							}else{
								instructorName=instructorName+","+attendanceInstructor.getUsers().getUserName();
							}
						}
						   attendanceTO.setTeachers(instructorName);
					}
				}
				return attendanceTO;
	}
	
}
