package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.attendance.RemoveAttendanceForm;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.AttendanceReEntryTo;
import com.kp.cms.utilities.CommonUtil;

public class RemoveAttendanceHelper {
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	/**
	 * Singleton object of RemoveAttendanceHandler
	 */
	private static volatile RemoveAttendanceHelper removeAttendanceHelper = null;
	private static final Log log = LogFactory.getLog(RemoveAttendanceHelper.class);
	private RemoveAttendanceHelper() {
		
	}
	/**
	 * return singleton object of RemoveAttendanceHandler.
	 * @return
	 */
	public static RemoveAttendanceHelper getInstance() {
		if (removeAttendanceHelper == null) {
			removeAttendanceHelper = new RemoveAttendanceHelper();
		}
		return removeAttendanceHelper;
	}
	/**
	 * @param removeAttendanceForm
	 * @return
	 * @throws Exception
	 */
	public String getAttendanceSearchQuery(RemoveAttendanceForm removeAttendanceForm) throws Exception {
		
		String [] tempArray = removeAttendanceForm.getSubjects();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<tempArray.length;i++){
			intType.append(tempArray[i]);
			 if(i<(tempArray.length-1)){
				 intType.append(",");
			 }
		}
		
		String query="select s from AttendanceStudent s join s.attendance.attendanceClasses c" +
				" where s.attendance.isCanceled=0" +
				" and s.student.registerNo='"+removeAttendanceForm.getRegNo()+"'" +
				" and c.classSchemewise.id = " +removeAttendanceForm.getClassSchemewiseId()+
				" and s.attendance.subject.id in ("+intType+")";
		return query;
	}
	/**
	 * @param attStuList
	 * @return
	 * @throws Exception
	 */
	public Map<Date, AttendanceReEntryTo> convertBosToMap(List<AttendanceStudent> attStuList) throws Exception {
		Map<Date, AttendanceReEntryTo> finalMap=new HashMap<Date, AttendanceReEntryTo>();
		if(attStuList!=null && !attStuList.isEmpty()){
			Iterator<AttendanceStudent> itr=attStuList.iterator();
			while (itr.hasNext()) {
				AttendanceStudent bo = (AttendanceStudent) itr.next();
				Attendance attendance=bo.getAttendance();
				AttendanceReEntryTo attendanceReEntryTo=null;
				if(finalMap.containsKey(attendance.getAttendanceDate())){
					attendanceReEntryTo=finalMap.remove(attendance.getAttendanceDate());
					attendanceReEntryTo.setAttendanceDate(
							CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(attendance.getAttendanceDate()),RemoveAttendanceHelper.SQL_DATEFORMAT,
									RemoveAttendanceHelper.FROM_DATEFORMAT));
					List<SubjectTO> subList=attendanceReEntryTo.getSubList();
					SubjectTO subjectTO=new SubjectTO();
					subjectTO.setId(bo.getId());
					subjectTO.setSubjectName(attendance.getSubject().getName());
					if(attendance.getSubject().getCode()!=null){
						subjectTO.setSubjectName(subjectTO.getSubjectName()+"("+attendance.getSubject().getCode()+")");
					}
					subList.add(subjectTO);
					attendanceReEntryTo.setSubList(subList);
					finalMap.put(attendance.getAttendanceDate(), attendanceReEntryTo);
				}else{
					attendanceReEntryTo=new AttendanceReEntryTo();
					attendanceReEntryTo.setAttendanceDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(attendance.getAttendanceDate()),RemoveAttendanceHelper.SQL_DATEFORMAT,RemoveAttendanceHelper.FROM_DATEFORMAT));
					List<SubjectTO> subList=new ArrayList<SubjectTO>();
					SubjectTO subjectTO=new SubjectTO();
					subjectTO.setId(bo.getId());
					subjectTO.setSubjectName(attendance.getSubject().getName());
					if(attendance.getSubject().getCode()!=null){
						subjectTO.setSubjectName(subjectTO.getSubjectName()+"("+attendance.getSubject().getCode()+")");
					}
					subList.add(subjectTO);
					attendanceReEntryTo.setSubList(subList);
					finalMap.put(attendance.getAttendanceDate(), attendanceReEntryTo);
				}
			
			}
		}
		return finalMap;
	}
	/**
	 * @param list
	 * @param removeAttendanceForm
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getFinalListToRemove(List<AttendanceReEntryTo> list,RemoveAttendanceForm removeAttendanceForm) throws Exception {
		List<Integer> finalList=new ArrayList<Integer>();
		Iterator<AttendanceReEntryTo> attIterator=list.iterator();
		while (attIterator.hasNext()) {
			AttendanceReEntryTo attendanceReEntryTo = (AttendanceReEntryTo) attIterator.next();
			List<SubjectTO> subList=attendanceReEntryTo.getSubList();
			if(!subList.isEmpty()){
				Iterator<SubjectTO> itr=subList.iterator();
				while (itr.hasNext()) {
					SubjectTO subjectTO = (SubjectTO) itr.next();
					if(subjectTO.isChecked()){
						finalList.add(subjectTO.getId());
					}
				}
			}
		}
		return finalList;
	}
}
