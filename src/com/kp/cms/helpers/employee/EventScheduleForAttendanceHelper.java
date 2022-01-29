package com.kp.cms.helpers.employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EventLocation;
import com.kp.cms.bo.admin.TTPeriodWeek;
import com.kp.cms.bo.employee.EventLoactionBiometricDetailsBo;
import com.kp.cms.bo.employee.EventScheduleForAttendanceBo;
import com.kp.cms.bo.employee.EventScheduleStaffAttendanceBo;
import com.kp.cms.bo.employee.EventScheduleStudentAttendanceBo;
import com.kp.cms.forms.employee.EventLocationBiometricDetailsForm;
import com.kp.cms.forms.employee.EventScheduleForAttendanceForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.transactions.employee.IEventScheduleForAttendanceTransaction;
import com.kp.cms.transactionsimpl.employee.EventScheduleForAttendanceTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;


public class EventScheduleForAttendanceHelper {
	private static final Log log = LogFactory.getLog(EventScheduleForAttendanceHelper.class);
	public static volatile EventScheduleForAttendanceHelper biometric=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static EventScheduleForAttendanceHelper getInstance(){
		if(biometric==null){
			biometric= new EventScheduleForAttendanceHelper();}
		
		return biometric;
	}
	/**
	 * @param listClass
	 * @param eventScheduleform
	 * @return
	 * @throws Exception
	 */
	public EventScheduleForAttendanceBo convertFormToBo(ArrayList<Integer> listClass,EventScheduleForAttendanceForm eventScheduleform) throws Exception{
		EventScheduleForAttendanceBo eventSheduleAttendance=null;
		IEventScheduleForAttendanceTransaction transaction=new EventScheduleForAttendanceTransactionImpl(); 
		//add student data
		if(eventScheduleform.getType().equalsIgnoreCase("Student")){
			 eventSheduleAttendance=new EventScheduleForAttendanceBo();
			 Set<EventScheduleStudentAttendanceBo> studentattendance = new HashSet<EventScheduleStudentAttendanceBo>();
			 Iterator<Integer> iterator = listClass.iterator();
			 while (iterator.hasNext()) {
				 Integer classid = (Integer) iterator .next();
				 int classId=transaction.getClassIdByClassSchemewiseID(classid);
				 EventScheduleStudentAttendanceBo studentattendancebo=new EventScheduleStudentAttendanceBo();
				 Classes clasid=new Classes();
				 clasid.setId(classId);
				 studentattendancebo.setClasses(clasid);
				 studentattendancebo.setCreatedBy(eventScheduleform.getUserId());
				 studentattendancebo.setCreatedDate(new Date());
				 studentattendancebo.setLastModifiedDate(new Date());
				 studentattendancebo.setModifiedBy(eventScheduleform.getUserId());
				 studentattendancebo.setIsActive(true);
				 if(eventScheduleform.getFromPeriod()!=null && !eventScheduleform.getFromPeriod().isEmpty()){
				 int fromPeriodId=transaction.getPeriodIdByClassIdAndPeriodId(eventScheduleform.getFromPeriod(),classid);
				 if(fromPeriodId ==0)
					 studentattendancebo.setFromPeriodId(null);
				 else
				 studentattendancebo.setFromPeriodId(String.valueOf(fromPeriodId));
				 }
				 if(eventScheduleform.getToPeriod()!=null && !eventScheduleform.getToPeriod().isEmpty()){
					 int toPeriodId=transaction.getPeriodIdByClassIdAndPeriodId(eventScheduleform.getToPeriod(),classid);
					 if(toPeriodId ==0)
						 studentattendancebo.setToPeriodId(null);
					 else
					 studentattendancebo.setToPeriodId(String.valueOf(toPeriodId));
				 }
				 studentattendance.add(studentattendancebo);
			 }
			 eventSheduleAttendance.setEventScheduleStudentAttendanceBo(studentattendance);
			 eventSheduleAttendance.setIsStudent(true);
			}//add staff data
			else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
				 eventSheduleAttendance=new EventScheduleForAttendanceBo();
				 Set<EventScheduleStaffAttendanceBo> staffattendance = new HashSet<EventScheduleStaffAttendanceBo>();
				 Iterator<Integer> iterator = listClass.iterator();
				 while (iterator.hasNext()) {
					 Integer depid = (Integer) iterator .next();
					 EventScheduleStaffAttendanceBo staffattendancebo=new EventScheduleStaffAttendanceBo();
					 Department deptId=new Department();
					 deptId.setId(depid);
					 staffattendancebo.setDepartment(deptId);
					 staffattendancebo.setCreatedBy(eventScheduleform.getUserId());
					 staffattendancebo.setCreatedDate(new Date());
					 staffattendancebo.setLastModifiedDate(new Date());
					 staffattendancebo.setModifiedBy(eventScheduleform.getUserId());
					 staffattendancebo.setIsActive(true);
					 staffattendance.add(staffattendancebo);
				 }
				 eventSheduleAttendance.setEventScheduleStaffAttendanceBo(staffattendance);
				 eventSheduleAttendance.setIsStudent(false);
			}
		// add common data
		eventSheduleAttendance.setCreatedBy(eventScheduleform.getUserId());
		eventSheduleAttendance.setCreatedDate(new Date());
		eventSheduleAttendance.setLastModifiedDate(new Date());
		eventSheduleAttendance.setModifiedBy(eventScheduleform.getUserId());
		eventSheduleAttendance.setIsActive(true);
		EventLocation eventLocation=new EventLocation();
		eventLocation.setId(Integer.parseInt(eventScheduleform.getEventLocation()));
		eventSheduleAttendance.setEventLocation(eventLocation);
		eventSheduleAttendance.setEventDescription(eventScheduleform.getEventDesc());
		if(eventScheduleform.getTimeFrom()!=null && !eventScheduleform.getTimeFrom().isEmpty())
		eventSheduleAttendance.setEventTimeFrom(eventScheduleform.getTimeFrom()+":"+eventScheduleform.getTimeFromMin());
		if(eventScheduleform.getTimeTo()!=null && !eventScheduleform.getTimeTo().isEmpty())
		eventSheduleAttendance.setEventTimeTo(eventScheduleform.getTimeTo()+":"+eventScheduleform.getTimeToMIn());
		eventSheduleAttendance.setIsActive(true);
		eventSheduleAttendance.setEventDate(CommonUtil.ConvertStringToSQLDate(eventScheduleform.getEventDate()));
		return eventSheduleAttendance;
	}
	
	
	public void setBotoForm(EventScheduleForAttendanceForm eventScheduleform,EventScheduleForAttendanceBo eventSchedule)throws Exception{
		IEventScheduleForAttendanceTransaction transaction=new EventScheduleForAttendanceTransactionImpl(); 
    	if(eventSchedule!=null){
    		//setting the eventLoation and eventdescription to form
    		eventScheduleform.setEventDesc(eventSchedule.getEventDescription());
    		eventScheduleform.setEventLocation(String.valueOf(eventSchedule.getEventLocation().getId()));
    		// set the event date to form
    		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
    		String text = df.format(eventSchedule.getEventDate());  
    		eventScheduleform.setEventDate(CommonUtil.formatSqlDate1(text));
    		eventScheduleform.setOldEventDate(CommonUtil.formatSqlDate1(text));
    		eventScheduleform.getTimeFrom();
    		//set the event time to form
    		String eventTimeFrom=eventSchedule.getEventTimeFrom();
    		String[] eventScheduleTimeFrom = eventTimeFrom.split(":");
    		String eventTimeTo=eventSchedule.getEventTimeTo();
    		String[] eventScheduleTimeTo = eventTimeTo.split(":");
    		eventScheduleform.setTimeFrom(eventScheduleTimeFrom[0]);
    		eventScheduleform.setTimeFromMin(eventScheduleTimeFrom[1]);
    		eventScheduleform.setTimeTo(eventScheduleTimeTo[0]);
    		eventScheduleform.setTimeToMIn(eventScheduleTimeTo[1]);
    		
    		if(eventScheduleform.getType().equalsIgnoreCase("Student")){
    			Set<EventScheduleStudentAttendanceBo> studentattendanceSet = eventSchedule.getEventScheduleStudentAttendanceBo();
    			if(studentattendanceSet !=null){
    				Map<Integer, String> classmap=new HashMap<Integer, String>();
    				Map<Integer, Integer> oldMapSelectedClass=new HashMap<Integer, Integer>();
    				Map<Integer, Integer> inActiveClassId=new HashMap<Integer, Integer>();
    				int year=0;
    				int classchemewiseID=0;
    				int fromPeriodId=0;
    				int toPeriodId=0;
    				boolean isFromPeriodId=false;
    				boolean getClasschemeWiseId=false;
    				Iterator<EventScheduleStudentAttendanceBo> studentAttItr=studentattendanceSet.iterator();
    				while (studentAttItr.hasNext()) {
    					EventScheduleStudentAttendanceBo studentBo= studentAttItr .next();
    					if(studentBo!=null){
    					if( studentBo.getIsActive()){
    					if(!isFromPeriodId && (studentBo.getFromPeriodId()!=null && !studentBo.getFromPeriodId().isEmpty())&& (studentBo.getToPeriodId()!=null  && !studentBo.getToPeriodId().isEmpty())){
    						isFromPeriodId=true;
    						fromPeriodId=Integer.parseInt(studentBo.getFromPeriodId());
    						toPeriodId=Integer.parseInt(studentBo.getToPeriodId());
    					}
    					// setting the year and selected classes to form
    					Set<ClassSchemewise> classchwise=studentBo.getClasses().getClassSchemewises();
    					if(classchwise !=null){
    						Iterator<ClassSchemewise> classschemewise=classchwise.iterator();
    	    				while (classschemewise.hasNext()) {
    	    					ClassSchemewise classscheme= classschemewise.next();
    	    					classmap.put(classscheme.getId(),studentBo.getClasses().getName());
    	    					oldMapSelectedClass.put(classscheme.getId(),studentBo.getId());
		    	    				if(!getClasschemeWiseId){
		    	    					getClasschemeWiseId=true;
		    	    					classchemewiseID=classscheme.getId();
		    	    					year=(classscheme.getCurriculumSchemeDuration().getAcademicYear());
		    	    					eventScheduleform.setYear(String.valueOf(year));
		    	    				}
    	    					}
    						}
    					}else{
    						inActiveClassId.put(studentBo.getId(), studentBo.getId());
    					}
    				}
    			}
    				if(year==0 ){
    					year=CurrentAcademicYear.getInstance().getAcademicyear();
    				}
    				eventScheduleform.setMapSelectedClass(classmap);
    				eventScheduleform.setOldMapSelectedClass(oldMapSelectedClass);
    				eventScheduleform.setInActiveStudentIdsMap(inActiveClassId);
    				//setting the periods based on passing the classSchemewiseID 
    				if(classchemewiseID!=0){
    					Map<Integer, String> periodMap = CommonAjaxHandler.getInstance().getPeriodByClassSchemewiseId(classchemewiseID);
    					eventScheduleform.setPeriodMap(periodMap);
    				}
    				//setting the classes to form by passing the year
    				if(year >0){
    					Map<Integer,String> mapClass=CommonAjaxHandler.getInstance().getClassesByYear(year);
    					
    					for (Integer key : classmap.keySet()) {
    						if(mapClass.containsKey(key)){
    							mapClass.remove(key);
    						}
    					}
    					eventScheduleform.setMapClass(mapClass);
    				}
    				// setting the selected period from period and to period to form 
    				if(fromPeriodId!=0 && toPeriodId!=0){
    					int fromPeriodName=transaction.getPeriodNameByperiodId(fromPeriodId);
    					eventScheduleform.setFromPeriod(String.valueOf(fromPeriodName));
    					int toPeriodName=transaction.getPeriodNameByperiodId(toPeriodId);
    					eventScheduleform.setToPeriod(String.valueOf(toPeriodName));
    					eventScheduleform.setPeriodSelected(true);
    				}
    			}
    		}else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
    			Set<EventScheduleStaffAttendanceBo> staffattendanceSet = eventSchedule.getEventScheduleStaffAttendanceBo();
    			if(staffattendanceSet !=null){
    				// setting the selected departments to form
    				Map<Integer, String> deptMap=new HashMap();
    				Map<Integer, Integer> oldDeptMap=new HashMap<Integer, Integer>();
    				Map<Integer, Integer> inActiveDeptID=new HashMap<Integer, Integer>();
    				Iterator<EventScheduleStaffAttendanceBo> staffAttItr=staffattendanceSet.iterator();
    				while (staffAttItr.hasNext()) {
    					EventScheduleStaffAttendanceBo staffBo= staffAttItr .next();
    					if(staffBo!=null ){
    					if( staffBo.getIsActive()){
	    					deptMap.put(staffBo.getDepartment().getId(),staffBo.getDepartment().getName());
	    					oldDeptMap.put(staffBo.getDepartment().getId(),staffBo.getId());
    					}else{
    						inActiveDeptID.put(staffBo.getId(), staffBo.getId());
    					}
    				 }
    				}
    				eventScheduleform.setMapSelectedDept(deptMap);
    				eventScheduleform.setOldDeptMap(oldDeptMap);
    				eventScheduleform.setInActiveStaffIdsMap(inActiveDeptID);
    				//setting the departmentlist to from 
    				Map<Integer,String> mapDept=CommonAjaxHandler.getInstance().getDepartments();
    				
    				for (Integer key : deptMap.keySet()) {
						if(mapDept.containsKey(key)){
							mapDept.remove(key);
						}
					}
    				eventScheduleform.setMapDept(mapDept);
    			}
    		}
    		
    	}
    }
	
	
	public EventScheduleForAttendanceBo convertFormToBoInUpdateMode(ArrayList<Integer> listClass,EventScheduleForAttendanceForm eventScheduleform) throws Exception{
		EventScheduleForAttendanceBo eventSheduleAttendance=null;
		IEventScheduleForAttendanceTransaction transaction=new EventScheduleForAttendanceTransactionImpl(); 
		//add student data
		if(eventScheduleform.getType().equalsIgnoreCase("Student")){
			 eventSheduleAttendance=new EventScheduleForAttendanceBo();
			 Map<Integer, Integer> oldSelectedClassMap=new HashMap<Integer, Integer>();
			 Map<Integer, Integer> inActiveStudentIdsMap=eventScheduleform.getInActiveStudentIdsMap();
			 List<Integer> classList=new ArrayList<Integer>();
			 Set<EventScheduleStudentAttendanceBo> studentattendance = new HashSet<EventScheduleStudentAttendanceBo>();
			 Iterator<Integer> iterator = listClass.iterator();
			 while (iterator.hasNext()) {
				 Integer classSchemewiseID = (Integer) iterator .next();
				 int classId=transaction.getClassIdByClassSchemewiseID(classSchemewiseID);
				 oldSelectedClassMap=eventScheduleform.getOldMapSelectedClass();
				 
				     if(oldSelectedClassMap.containsKey(classSchemewiseID)){
				    	 //if selected class was already existed 
				    	 
				    	 EventScheduleStudentAttendanceBo studentattendancebo=new EventScheduleStudentAttendanceBo();
						 Classes clasid=new Classes();
						 clasid.setId(classId);
						 classList.add(classId);
						 studentattendancebo.setId(oldSelectedClassMap.get(classSchemewiseID));
						 studentattendancebo.setClasses(clasid);
						 studentattendancebo.setLastModifiedDate(new Date());
						 studentattendancebo.setModifiedBy(eventScheduleform.getUserId());
						 studentattendancebo.setIsActive(true);
						 if(eventScheduleform.getFromPeriod()!=null && !eventScheduleform.getFromPeriod().isEmpty()){
						 int fromPeriodId=transaction.getPeriodIdByClassIdAndPeriodId(eventScheduleform.getFromPeriod(),classSchemewiseID);
						 if(fromPeriodId ==0)
							 studentattendancebo.setFromPeriodId(null);
						 else
						 studentattendancebo.setFromPeriodId(String.valueOf(fromPeriodId));
						 }
						 if(eventScheduleform.getToPeriod()!=null && !eventScheduleform.getToPeriod().isEmpty()){
							 int toPeriodId=transaction.getPeriodIdByClassIdAndPeriodId(eventScheduleform.getToPeriod(),classSchemewiseID);
							 if(toPeriodId ==0)
								 studentattendancebo.setToPeriodId(null);
							 else
							 studentattendancebo.setToPeriodId(String.valueOf(toPeriodId));
						 }
						 studentattendance.add(studentattendancebo);
				     }else{     //if selected class was newly entered
				    	 EventScheduleStudentAttendanceBo studentattendancebo=new EventScheduleStudentAttendanceBo();
						 Classes clasid=new Classes();
						 clasid.setId(classId);
						 classList.add(classId);
						 studentattendancebo.setClasses(clasid);
						 studentattendancebo.setCreatedBy(eventScheduleform.getUserId());
						 studentattendancebo.setCreatedDate(new Date());
						 studentattendancebo.setLastModifiedDate(new Date());
						 studentattendancebo.setModifiedBy(eventScheduleform.getUserId());
						 studentattendancebo.setIsActive(true);
						 if(eventScheduleform.getFromPeriod()!=null && !eventScheduleform.getFromPeriod().isEmpty()){
						 int fromPeriodId=transaction.getPeriodIdByClassIdAndPeriodId(eventScheduleform.getFromPeriod(),classSchemewiseID);
						 if(fromPeriodId ==0)
							 studentattendancebo.setFromPeriodId(null);
						 else
						 studentattendancebo.setFromPeriodId(String.valueOf(fromPeriodId));
						 }
						 if(eventScheduleform.getToPeriod()!=null && !eventScheduleform.getToPeriod().isEmpty()){
							 int toPeriodId=transaction.getPeriodIdByClassIdAndPeriodId(eventScheduleform.getToPeriod(),classSchemewiseID);
							 if(toPeriodId ==0)
								 studentattendancebo.setToPeriodId(null);
							 else
							 studentattendancebo.setToPeriodId(String.valueOf(toPeriodId));
						 }
						 studentattendance.add(studentattendancebo);
				     }
				 }
			 //delete unselect Event_schedule_student_attendance  records
			 for (Integer key : oldSelectedClassMap.keySet()) {
					if(!listClass.contains(key)){
						EventScheduleStudentAttendanceBo eventSchBo=transaction.getStudentData(oldSelectedClassMap.get(key));
						if(eventSchBo!=null){
							eventSchBo.setLastModifiedDate(new Date());
							eventSchBo.setModifiedBy(eventScheduleform.getUserId());
							eventSchBo.setIsActive(false);
							studentattendance.add(eventSchBo);
						}
					}
				}
			 //add Event_schedule_student_attendance isActive=0 records
			 if(inActiveStudentIdsMap!=null){
				 for (Integer key : inActiveStudentIdsMap.keySet()) {
					 EventScheduleStudentAttendanceBo eventSchBo=transaction.getInActiveStudentId(key);
					 if(eventSchBo!=null){
							studentattendance.add(eventSchBo);
						}
				 }
			 }
			 eventSheduleAttendance.setEventScheduleStudentAttendanceBo(studentattendance);
			 eventSheduleAttendance.setIsStudent(true);
			 eventSheduleAttendance.setClassList(classList);
			}//add staff data
			else if(eventScheduleform.getType().equalsIgnoreCase("Staff")){
				 eventSheduleAttendance=new EventScheduleForAttendanceBo();
				 Set<EventScheduleStaffAttendanceBo> staffattendance = new HashSet<EventScheduleStaffAttendanceBo>();
				 Map<Integer, Integer> oldSelectedDeptMap=new HashMap<Integer, Integer>();
				 Map<Integer, Integer> inActiveStaffIDs=eventScheduleform.getInActiveStaffIdsMap();
				 List<Integer> deptList=new ArrayList<Integer>();
				 Iterator<Integer> iterator = listClass.iterator();
				 while (iterator.hasNext()) {
					 Integer depid = (Integer) iterator .next();
					 oldSelectedDeptMap=eventScheduleform.getOldDeptMap();
					 
					 
					     if(oldSelectedDeptMap.containsKey(depid)){ //if selected department is already existed   
					    	 EventScheduleStaffAttendanceBo staffattendancebo=new EventScheduleStaffAttendanceBo();
							 Department deptId=new Department();
							 deptId.setId(depid);
							 deptList.add(depid);
							 staffattendancebo.setId(oldSelectedDeptMap.get(depid));
							 staffattendancebo.setDepartment(deptId);
							 staffattendancebo.setLastModifiedDate(new Date());
							 staffattendancebo.setModifiedBy(eventScheduleform.getUserId());
							 staffattendancebo.setIsActive(true);
							 staffattendance.add(staffattendancebo);
					     }else{// if selected department is newly entered 
					    	 EventScheduleStaffAttendanceBo staffattendancebo=new EventScheduleStaffAttendanceBo();
							 Department deptId=new Department();
							 deptId.setId(depid);
							 deptList.add(depid);
							 staffattendancebo.setDepartment(deptId);
							 staffattendancebo.setCreatedBy(eventScheduleform.getUserId());
							 staffattendancebo.setCreatedDate(new Date());
							 staffattendancebo.setLastModifiedDate(new Date());
							 staffattendancebo.setModifiedBy(eventScheduleform.getUserId());
							 staffattendancebo.setIsActive(true);
							 staffattendance.add(staffattendancebo);
					     }
					 }
				 //delete unselected departments
				 for (Integer key : oldSelectedDeptMap.keySet()) {
						if(!listClass.contains(key)){
							EventScheduleStaffAttendanceBo eventStaffBo=transaction.getStaffData(oldSelectedDeptMap.get(key));
							if(eventStaffBo!=null){
								eventStaffBo.setLastModifiedDate(new Date());
								eventStaffBo.setModifiedBy(eventScheduleform.getUserId());
								eventStaffBo.setIsActive(false);
								staffattendance.add(eventStaffBo);
							}
						}
					}
				 //add Event_staff_attendance isActive=0 records
				 if(inActiveStaffIDs!=null){
					 for (Integer key : inActiveStaffIDs.keySet()) {
								EventScheduleStaffAttendanceBo eventStaffBo=transaction.getInActiveStaffData(key);
								if(eventStaffBo!=null){
									staffattendance.add(eventStaffBo);
								}
							}
				 		}
				 eventSheduleAttendance.setEventScheduleStaffAttendanceBo(staffattendance);
				 eventSheduleAttendance.setIsStudent(false);
				 eventSheduleAttendance.setDeptList(deptList);
			}
		// add common data
		eventSheduleAttendance.setId(eventScheduleform.getId());
		eventSheduleAttendance.setLastModifiedDate(new Date());
		eventSheduleAttendance.setModifiedBy(eventScheduleform.getUserId());
		eventSheduleAttendance.setIsActive(true);
		EventLocation eventLocation=new EventLocation();
		eventLocation.setId(Integer.parseInt(eventScheduleform.getEventLocation()));
		eventSheduleAttendance.setEventLocation(eventLocation);
		eventSheduleAttendance.setEventDescription(eventScheduleform.getEventDesc());
		if(eventScheduleform.getTimeFrom()!=null && !eventScheduleform.getTimeFrom().isEmpty())
		eventSheduleAttendance.setEventTimeFrom(eventScheduleform.getTimeFrom()+":"+eventScheduleform.getTimeFromMin());
		if(eventScheduleform.getTimeTo()!=null && !eventScheduleform.getTimeTo().isEmpty())
		eventSheduleAttendance.setEventTimeTo(eventScheduleform.getTimeTo()+":"+eventScheduleform.getTimeToMIn());
		eventSheduleAttendance.setIsActive(true);
		eventSheduleAttendance.setEventDate(CommonUtil.ConvertStringToSQLDate(eventScheduleform.getEventDate()));
		return eventSheduleAttendance;
	}
	
}
