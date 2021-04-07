package com.kp.cms.handlers.attendance;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.attendance.AttendanceReEntryForm;
import com.kp.cms.helpers.attendance.AttendanceReEntryHelper;
import com.kp.cms.to.attendance.AttendanceReEntryTo;
import com.kp.cms.transactions.attandance.IAttendanceReEntryTransactin;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceReEntryTransactionImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;

public class AttendanceReEntryHandler {
	/**
	 * Singleton object of AttendanceReEntryHandler
	 */
	private static volatile AttendanceReEntryHandler attendanceReEntryHandler = null;
	private static final Log log = LogFactory.getLog(AttendanceReEntryHandler.class);
	private AttendanceReEntryHandler() {
		
	}
	/**
	 * return singleton object of AttendanceReEntryHandler.
	 * @return
	 */
	public static AttendanceReEntryHandler getInstance() {
		if (attendanceReEntryHandler == null) {
			attendanceReEntryHandler = new AttendanceReEntryHandler();
		}
		return attendanceReEntryHandler;
	}
	/**
	 * @param attendanceReEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<Student> getStudents(AttendanceReEntryForm attendanceReEntryForm) throws Exception{
		String query=AttendanceReEntryHelper.getInstance().getStudentQuery(attendanceReEntryForm);
		IAttendanceReEntryTransactin transaction=new AttendanceReEntryTransactionImpl();
		return transaction.getStudent(query);
	}
	/**
	 * @param attendanceReEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getDuplicateAttendance(
			AttendanceReEntryForm attendanceReEntryForm) throws Exception {
		String query=AttendanceReEntryHelper.getInstance().getDuplicateAttendanceQuery(attendanceReEntryForm);
		IAttendanceReEntryTransactin transaction=new AttendanceReEntryTransactionImpl();
		return transaction.getDuplicateAttendance(query,attendanceReEntryForm);
	}
	/**
	 * @param attendanceReEntryForm
	 * @return
	 * @throws Exception
	 */
	public Map<Date, AttendanceReEntryTo> getAttendanceList(AttendanceReEntryForm attendanceReEntryForm) throws Exception{
		String query=AttendanceReEntryHelper.getInstance().getSearchQuery(attendanceReEntryForm);
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		IAttendanceReEntryTransactin transaction=new AttendanceReEntryTransactionImpl();
		Student student = transaction.getStudentByRegNo(AttendanceReEntryHelper.getInstance().getStudentByRegnoQuery(attendanceReEntryForm));
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				int len = applSubjectGroup.size();
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				String commaString;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();
					if(count + 1 < len){
						commaString = ",";
					}else{
						commaString = "";
					}
					subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId()) + commaString;
					count++;
				}
				StringBuffer subjectString = new StringBuffer();
				if(subjGroupString!= null && subjGroupString.length > 0){
					for (int x = 0; x < subjGroupString.length; x++){
						subjectString.append(subjGroupString[x]);	
					}
					
				}
				subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupId(subjectString.toString());
			}
		}
		attendanceReEntryForm.setStuId(student.getId());
		PersonalData personalData=student.getAdmAppln().getPersonalData();
		String name="";
		if(personalData.getFirstName()!=null){
			name=name+personalData.getFirstName();
		}
		if(personalData.getMiddleName()!=null){
			name=name+" "+personalData.getMiddleName();
		}
		if(personalData.getLastName()!=null){
			name=name+" "+personalData.getLastName();
		}
		attendanceReEntryForm.setStudentName(name);
		List<Integer> duplicateList=getAttendanceIdByAttendanceStudent(attendanceReEntryForm);
		List<Integer> batchList=getBatchListByStudent(student.getId(),attendanceReEntryForm.getClasses());
		return AttendanceReEntryHelper.getInstance().convertBoToToList(transaction.getAttendanceList(query),subjectMap,duplicateList,batchList);
	}
	/**
	 * @param id
	 * @param classes
	 * @return
	 */
	private List<Integer> getBatchListByStudent(int studentId, String classes) throws Exception {
		String query=AttendanceReEntryHelper.getBatchListQueryForStudent(studentId,classes);
		IAttendanceReEntryTransactin transaction=new AttendanceReEntryTransactionImpl();
		return transaction.getAttendanceIdByAttendanceStudent(query);
	}
	/**
	 * @param attendanceReEntryTos
	 * @return
	 */
	public boolean saveAttendanceReEntry(List<AttendanceReEntryTo> list,AttendanceReEntryForm attendanceReEntryForm) throws Exception {
		List<AttendanceStudent> finalList=AttendanceReEntryHelper.getInstance().getAttendanceStudentList(list,attendanceReEntryForm);
		IAttendanceReEntryTransactin transaction=new AttendanceReEntryTransactionImpl();
		return transaction.saveAttendanceStudent(finalList);
	}
	
	/**
	 * @param attendanceReEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getAttendanceIdByAttendanceStudent(
			AttendanceReEntryForm attendanceReEntryForm) throws Exception {
		String query=AttendanceReEntryHelper.getInstance().getAttendanceIdByAttendanceStudentQuery(attendanceReEntryForm);
		IAttendanceReEntryTransactin transaction=new AttendanceReEntryTransactionImpl();
		return transaction.getAttendanceIdByAttendanceStudent(query);
	}
}
