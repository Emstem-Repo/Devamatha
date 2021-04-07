package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Collection;
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
import com.kp.cms.forms.attendance.AttendanceReEntryForm;
import com.kp.cms.helpers.admission.StudentEditHelper;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.AttendanceReEntryTo;
import com.kp.cms.utilities.CommonUtil;

public class AttendanceReEntryHelper {
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * Singleton object of attendanceReEntryHelper
	 */
	private static volatile AttendanceReEntryHelper attendanceReEntryHelper = null;
	private static final Log log = LogFactory.getLog(AttendanceReEntryHelper.class);
	private AttendanceReEntryHelper() {
		
	}
	/**
	 * return singleton object of attendanceReEntryHelper.
	 * @return
	 */
	public static AttendanceReEntryHelper getInstance() {
		if (attendanceReEntryHelper == null) {
			attendanceReEntryHelper = new AttendanceReEntryHelper();
		}
		return attendanceReEntryHelper;
	}
	/**
	 * @param attendanceReEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentQuery(AttendanceReEntryForm attendanceReEntryForm) throws Exception {
		String query="from Student s  " +
				" where s.classSchemewise.id = " +attendanceReEntryForm.getClasses()+
				" and isAdmitted = 1 and s.isActive = 1 and (s.isHide = 0 or s.isHide is null)";
		if(attendanceReEntryForm.getAppNo().equals("3")){
			query=query+" and s.registerNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}else if(attendanceReEntryForm.getAppNo().equals("2")){
			query=query+" and s.rollNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}else{
			query=query+" and s.admAppln.applnNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}
		return query;
	}
	/**
	 * @param attendanceReEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getDuplicateAttendanceQuery(AttendanceReEntryForm attendanceReEntryForm) throws Exception {
		String query="select min(s.attendance.attendanceDate),max(s.attendance.attendanceDate) from AttendanceStudent s " +
				" join s.attendance.attendanceClasses ac" +
				" where s.attendance.attendanceDate between '"+CommonUtil.ConvertStringToSQLDate(attendanceReEntryForm.getFromDate())+"' " +
				" and '"+CommonUtil.ConvertStringToSQLDate(attendanceReEntryForm.getToDate())+"' " +
				" and ac.classSchemewise.id="+attendanceReEntryForm.getClasses();
		if(attendanceReEntryForm.getAppNo().equals("3")){
			query=query+" and s.student.registerNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}else if(attendanceReEntryForm.getAppNo().equals("2")){
			query=query+" and s.student.rollNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}else{
			query=query+" and s.student.admAppln.applnNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}
		return query;
	}
	/**
	 * @param attendanceReEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQuery(AttendanceReEntryForm attendanceReEntryForm) throws Exception{
		String query="select a " +
				" from Attendance a" +
				" join a.attendanceClasses ac where ac.classSchemewise.id="+attendanceReEntryForm.getClasses() +
				" and a.attendanceDate between '"+CommonUtil.ConvertStringToSQLDate(attendanceReEntryForm.getFromDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(attendanceReEntryForm.getToDate())+"' order by a.attendanceDate";
		return query;
	}
	/**
	 * @param attendanceList
	 * @return
	 * @throws Exception
	 */
	public Map<Date, AttendanceReEntryTo> convertBoToToList(List<Attendance> attendanceList,HashMap<Integer, String> subjectMap,List<Integer> duplicateList,List<Integer> batchList) throws Exception {
		Map<Date, AttendanceReEntryTo> finalMap=new HashMap<Date, AttendanceReEntryTo>();
		AttendanceReEntryTo attendanceReEntryTo=null;
		if(attendanceList!=null && !attendanceList.isEmpty() && !subjectMap.isEmpty()){
			Iterator<Attendance> itr=attendanceList.iterator();
			while (itr.hasNext()) {
				Attendance attendance = (Attendance) itr.next();
				if(!duplicateList.contains(attendance.getId())){
					if(finalMap.containsKey(attendance.getAttendanceDate())){
						attendanceReEntryTo=finalMap.remove(attendance.getAttendanceDate());
						if(attendance.getSubject()!=null){
						attendanceReEntryTo.setAttendanceDate(
								CommonUtil.ConvertStringToDateFormat(
										CommonUtil.getStringDate(attendance.getAttendanceDate()),
										AttendanceReEntryHelper.SQL_DATEFORMAT,
										AttendanceReEntryHelper.FROM_DATEFORMAT));
						List<SubjectTO> subList=attendanceReEntryTo.getSubList();
						SubjectTO subjectTO=new SubjectTO();
						subjectTO.setId(attendance.getId());
						subjectTO.setSubjectName(attendance.getSubject().getName());
						if(attendance.getSubject().getCode()!=null){
							subjectTO.setSubjectName(subjectTO.getSubjectName()+"("+attendance.getSubject().getCode()+")");
						}
						if(subjectMap.containsKey(attendance.getSubject().getId())){
							if(attendance.getBatch()!=null){
								if(batchList!=null && !batchList.isEmpty()){
									if(batchList.contains(attendance.getBatch().getId())){
										subList.add(subjectTO);
									}
								}
							}else{
								subList.add(subjectTO);
							}
						}
						attendanceReEntryTo.setSubList(subList);
						finalMap.put(attendance.getAttendanceDate(), attendanceReEntryTo);
						}
					}else{
						if(attendance.getSubject()!=null){
						attendanceReEntryTo=new AttendanceReEntryTo();
						attendanceReEntryTo.setAttendanceDate(
								CommonUtil.ConvertStringToDateFormat(
										CommonUtil.getStringDate(attendance.getAttendanceDate()),
										AttendanceReEntryHelper.SQL_DATEFORMAT,
										AttendanceReEntryHelper.FROM_DATEFORMAT));
						List<SubjectTO> subList=new ArrayList<SubjectTO>();
						SubjectTO subjectTO=new SubjectTO();
						subjectTO.setId(attendance.getId());
						subjectTO.setSubjectName(attendance.getSubject().getName());
						if(attendance.getSubject().getCode()!=null){
							subjectTO.setSubjectName(subjectTO.getSubjectName()+"("+attendance.getSubject().getCode()+")");
						}
						if(subjectMap.containsKey(attendance.getSubject().getId())){
							if(attendance.getBatch()!=null){
								if(batchList!=null && !batchList.isEmpty()){
									if(batchList.contains(attendance.getBatch().getId())){
										subList.add(subjectTO);
									}
								}
							}else{
								subList.add(subjectTO);
							}
						}
						attendanceReEntryTo.setSubList(subList);
						finalMap.put(attendance.getAttendanceDate(), attendanceReEntryTo);
						}
					}
				}	
				}
		}
		return finalMap;
	}
	/**
	 * @param attendanceReEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentByRegnoQuery(AttendanceReEntryForm attendanceReEntryForm) throws Exception {
		//String query="select s from Student s where s.isActive=1  and (s.isHide = 0 or s.isHide is null) and s.admAppln.appliedYear='"+attendanceReEntryForm.getYear()+"'";
		//raghu did for 2,3,4,5,6 th students
		String query="select s from Student s where s.isActive=1  and (s.isHide = 0 or s.isHide is null) and s.admAppln.isCancelled=0 and s.classSchemewise.curriculumSchemeDuration.academicYear='"+attendanceReEntryForm.getYear()+"'";
		
		if(attendanceReEntryForm.getAppNo().equals("3")){
			query=query+" and s.registerNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}else if(attendanceReEntryForm.getAppNo().equals("2")){
			query=query+" and s.rollNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}else{
			query=query+" and s.admAppln.applnNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}
		return query;
	}
	/**
	 * @param attendanceReEntryTos
	 * @return
	 * @throws Exception
	 */
	public List<AttendanceStudent> getAttendanceStudentList(
			List<AttendanceReEntryTo> list,AttendanceReEntryForm attendanceReEntryForm) throws Exception {
		List<AttendanceStudent> finalList=new ArrayList<AttendanceStudent>();
		Iterator<AttendanceReEntryTo> attIterator=list.iterator();
		Student student=new Student();
		student.setId(attendanceReEntryForm.getStuId());
		while (attIterator.hasNext()) {
			AttendanceReEntryTo attendanceReEntryTo = (AttendanceReEntryTo) attIterator.next();
			List<SubjectTO> subList=attendanceReEntryTo.getSubList();
			if(!subList.isEmpty()){
				Iterator<SubjectTO> itr=subList.iterator();
				while (itr.hasNext()) {
					SubjectTO subjectTO = (SubjectTO) itr.next();
					AttendanceStudent attendanceStudent=new AttendanceStudent();
					Attendance attendance=new Attendance();
					attendance.setId(subjectTO.getId());
					attendanceStudent.setAttendance(attendance);
					attendanceStudent.setCreatedBy(attendanceReEntryForm.getUserId());
					attendanceStudent.setCreatedDate(new Date());
					attendanceStudent.setModifiedBy(attendanceReEntryForm.getUserId());
					attendanceStudent.setLastModifiedDate(new Date());
					if(subjectTO.isChecked()){
						attendanceStudent.setIsPresent(false);
					}else{
						attendanceStudent.setIsPresent(true);
					}
					attendanceStudent.setIsCoCurricularLeave(false);
					attendanceStudent.setIsOnLeave(false);
					attendanceStudent.setStudent(student);
					finalList.add(attendanceStudent);
				}
			}
		}
		return finalList;
	}
	
	/**
	 * @param attendanceReEntryForm
	 * @return
	 * @throws Exception
	 */
	public String getAttendanceIdByAttendanceStudentQuery(AttendanceReEntryForm attendanceReEntryForm) throws Exception {
		String query="select s.attendance.id from AttendanceStudent s " +
				" join s.attendance.attendanceClasses ac" +
				" where s.attendance.attendanceDate between '"+CommonUtil.ConvertStringToSQLDate(attendanceReEntryForm.getFromDate())+"' " +
				" and '"+CommonUtil.ConvertStringToSQLDate(attendanceReEntryForm.getToDate())+"' " +
				" and ac.classSchemewise.id="+attendanceReEntryForm.getClasses();
		if(attendanceReEntryForm.getAppNo().equals("3")){
			query=query+" and s.student.registerNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}else if(attendanceReEntryForm.getAppNo().equals("2")){
			query=query+" and s.student.rollNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}else{
			query=query+" and s.student.admAppln.applnNo='"+ attendanceReEntryForm.getRegNo()+"'";
		}
		return query;
	}
	/**
	 * @param studentId
	 * @param classes
	 * @return
	 */
	public static String getBatchListQueryForStudent(int studentId,String classes) {
		String query="select b.batch.id from BatchStudent b where b.student.id=" +studentId+
				" and b.classSchemewise.id="+classes+" and b.isActive=1 and b.batch.isActive=1 and (b.student.isHide = 0 or b.student.isHide is null)";
		return query;
	}
}
