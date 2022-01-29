package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlAttendance;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HlLeaveType;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.to.hostel.DisciplinaryTypeTO;
import com.kp.cms.to.hostel.HlAttendanceTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.to.hostel.ResidentStudentInfoTO;
import com.kp.cms.transactions.hostel.IResidentStudentInfo;
import com.kp.cms.transactionsimpl.hostel.ResidentStudentInfoTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ResidentStudentInfoHelper {
	/**
	 * Singleton object of ResidentStudentInfoHelper
	 */
	private static volatile ResidentStudentInfoHelper residentStudentInfoHelper = null;
	private static final Log log = LogFactory.getLog(ResidentStudentInfoHelper.class);
	private ResidentStudentInfoHelper() {
		
	}
	/**
	 * return singleton object of ResidentStudentInfoHelper.
	 * @return
	 */
	public static ResidentStudentInfoHelper getInstance() {
		if (residentStudentInfoHelper == null) {
			residentStudentInfoHelper = new ResidentStudentInfoHelper();
		}
		return residentStudentInfoHelper;
	}
	IResidentStudentInfo transaction=new ResidentStudentInfoTransactionImpl();
	/**
	 * create query based on Id
	 */
	public String getSearchCriteriaById(String studentId,String hostelId) throws Exception {
		String query=null;
		if(studentId!=null){
			query="select ha from HlApplicationForm ha join ha.admAppln.students s where ha.hlStatus.id=2 and  s.registerNo ='"+studentId +"' or s.rollNo='"+studentId+"' and ha.hlHostelByHlApprovedHostelId.id="+hostelId;
		}
		return query;
	}
	/**
	 * common query for any condition
	 */
	private String commonQuery() {
		String query="select ha.hlHostelByHlAppliedHostelId.name," +
				"ha.hlRoomTypeByHlAppliedRoomTypeId.name," +
				"ha from HlApplicationForm ha";
		return query;
	}
	/**
	 * building the query based on name
	 */
	public String getSearchCriteriaByName(String studentName,String hostelId) throws Exception{
		String query=null;
		if(studentName!=null){
			query="from HlApplicationForm ha where ha.hlStatus.id=2 and ha.admAppln.personalData.firstName like '"+studentName+"%' and ha.hlHostelByHlApprovedHostelId.id="+hostelId;
		}
		return query;
	}
	/**
	 * converting the BO to TO
	 */
	public ResidentStudentInfoTO convertBOtoTO(HlApplicationForm hlApplicationForm) throws Exception{
		ResidentStudentInfoTO residentTo=new ResidentStudentInfoTO();
		String studentName="";
		String applNo="";
		int appId=0;
		Set<Integer> appSet=new HashSet<Integer>();
		if(hlApplicationForm.getId()>0){
			residentTo.setHostelApplnId(hlApplicationForm.getId());
		}
		if(hlApplicationForm.getHlHostelByHlAppliedHostelId().getName()!=null){
		residentTo.setHostelType(hlApplicationForm.getHlHostelByHlAppliedHostelId().getName());
		}
		
		if(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName()!=null){
			residentTo.setRoomType(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName());
		}
		AdmAppln admAppln=hlApplicationForm.getAdmAppln();
		if(admAppln!=null){
		if(hlApplicationForm.getAdmAppln().getApplnNo()!=0 && Integer.toString(hlApplicationForm.getAdmAppln().getApplnNo())!=null){
			residentTo.setApplNo(Integer.toString(hlApplicationForm.getAdmAppln().getApplnNo()));
			appId=hlApplicationForm.getAdmAppln().getId();
		}
		if(admAppln.getAppliedYear()!=null && admAppln.getAppliedYear()>0){
			residentTo.setYear(admAppln.getAppliedYear());
		}
		if(admAppln.getCourseBySelectedCourseId()!=null){
		if(admAppln.getCourseBySelectedCourseId().getId()>0){
			residentTo.setCourseId(admAppln.getCourseBySelectedCourseId().getId());
		}
		}
		PersonalData personalData=admAppln.getPersonalData();
		applNo=Integer.toString(admAppln.getApplnNo());
		residentTo.setApplNo1(Integer.toString(admAppln.getApplnNo()));
		Set<Student> studentSet=admAppln.getStudents();
		Iterator<Student> itr=studentSet.iterator();
		if (itr.hasNext()) {
			Student student=(Student)itr.next();
			residentTo.setStudentId(student.getId());
			if(student.getRegisterNo()!=null){
				if(residentTo.getApplNo()!=null){
					residentTo.setApplNo(residentTo.getApplNo()+"/"+student.getRegisterNo());
				}else{
					residentTo.setApplNo(student.getRegisterNo());
				}
			}
			if(student.getRollNo()!=null){
				if(residentTo.getApplNo()!=null){
					residentTo.setApplNo(residentTo.getApplNo()+"/"+student.getRollNo());
				}else{
					residentTo.setApplNo(student.getRollNo());
				}
			}
		}
		
		if(personalData.getMiddleName() == null && personalData.getLastName() ==null){
			residentTo.setStudentName(personalData.getFirstName());
			studentName=residentTo.getStudentName();
		}else if(personalData.getLastName() ==null){
			residentTo.setStudentName(personalData.getFirstName() +" "+ personalData.getMiddleName());
			studentName=residentTo.getStudentName();
		}else if(personalData.getMiddleName() ==null){
			residentTo.setStudentName(personalData.getFirstName() +" "+ personalData.getLastName());
			studentName=residentTo.getStudentName();
		}
		else{
			residentTo.setStudentName(personalData.getFirstName() +" "+ personalData.getMiddleName() +" "+ personalData.getLastName());
			studentName=residentTo.getStudentName();
		}
		}
		
		Set<HlDisciplinaryDetails> disciplinarySet=hlApplicationForm.getHlDisciplinaryDetailses();
		List<DisciplinaryTypeTO> disciplinaryToList=new ArrayList<DisciplinaryTypeTO>();
		if(disciplinarySet!=null && !disciplinarySet.isEmpty()){
			Iterator<HlDisciplinaryDetails> i=disciplinarySet.iterator();
			while (i.hasNext()) {
				HlDisciplinaryDetails hlDisciplinaryDetails = (HlDisciplinaryDetails) i.next();
				String date=hlDisciplinaryDetails.getDate().toString();
				String reason=hlDisciplinaryDetails.getComments();
				HlDisciplinaryType disciplinaryType=hlDisciplinaryDetails.getHlDisciplinaryType();
				if(disciplinaryType!=null){
				DisciplinaryTypeTO disciplinaryTo=new DisciplinaryTypeTO();
				disciplinaryTo.setId(disciplinaryType.getId());
				disciplinaryTo.setName(disciplinaryType.getName());
				if(date!=null)
				disciplinaryTo.setDate(CommonUtil.formatSqlDate1(date));
				if(reason!=null)
				disciplinaryTo.setDescription(reason);
				disciplinaryTo.setStudentName(studentName);
				disciplinaryTo.setApplNo(applNo);
				disciplinaryToList.add(disciplinaryTo);
				}
			}
		}
		Collections.sort(disciplinaryToList);
		residentTo.setDisciplinaryToList(disciplinaryToList);
		Set<HlLeave> hlLeaves=hlApplicationForm.getHlLeaves();
		List<LeaveTypeTo> leaveList=new ArrayList<LeaveTypeTo>();
		if(hlLeaves!=null && !hlLeaves.isEmpty()){
			Iterator<HlLeave> leaveItr=hlLeaves.iterator();
			while (leaveItr.hasNext()) {
				HlLeave hlLeave = (HlLeave) leaveItr.next();
				String firstDate=hlLeave.getStartDate().toString();
				String lastDate=hlLeave.getEndDate().toString();
				String reason=hlLeave.getReasons();
				HlLeaveType leaveType=hlLeave.getHlLeaveType();
				if(leaveType!=null){
				LeaveTypeTo lto=new LeaveTypeTo();
				lto.setName(leaveType.getName());
				lto.setId(leaveType.getId());
				if(firstDate!=null)
					lto.setStartDate(CommonUtil.formatSqlDate1(firstDate));
				if(lastDate!=null)
					lto.setEndDate(CommonUtil.formatSqlDate1(lastDate));
				if(reason!=null)
					lto.setReasons(reason);
				lto.setStudentName(studentName);
				leaveList.add(lto);
				}
			}
		}
		residentTo.setLeaveList(leaveList);
		List<HlAttendance> attList=transaction.getAttendanceList(hlApplicationForm.getId());
		Map<Integer,Map<Integer,Integer>> absentMap=getAbsentsMap(attList);
		List<HlAttendanceTO> list=new ArrayList<HlAttendanceTO>();
		Set<String> duplicateSet=new HashSet<String>();
		if(attList!=null && !attList.isEmpty()){
			Iterator<HlAttendance> attitr=attList.iterator();
			while (attitr.hasNext()) {
				HlAttendance hlAttendance = (HlAttendance) attitr.next();
				Date attDate=hlAttendance.getAttendanceDate();
				Calendar cal=Calendar.getInstance();
				cal.setTime(attDate);
				int month=cal.get(Calendar.MONTH)+1;
				int year=cal.get(Calendar.YEAR);
				StringBuffer monthName=new StringBuffer(CommonUtil.getMonthForNumber(month));
				monthName.append(" ");
				monthName.append(year);
				String monthYear=monthName.toString();
				if(absentMap.containsKey(year)){
					if(absentMap.get(year)!=null && absentMap.get(year).containsKey(month) && !duplicateSet.contains(monthYear)){
						int absents=absentMap.get(year).get(month);
						HlAttendanceTO hlto=new HlAttendanceTO();
						hlto.setMonthYear(monthYear);
						hlto.setNoOfAbsance(absents);
						list.add(hlto);
						duplicateSet.add(monthYear);
					}
				}
				
			}
		}
		if(!appSet.contains(appId)){
		residentTo.setList(list);
		appSet.add(appId);
		}
		return residentTo;
	}
	/**
	 * @param attList
	 * @return
	 */
	private Map<Integer,Map<Integer,Integer>> getAbsentsMap(List<HlAttendance> attList) {
		Map<Integer,Integer> absentMap=new HashMap<Integer,Integer>();
		Map<Integer,Map<Integer,Integer>> yearMap=new HashMap<Integer,Map<Integer,Integer>>();
		if(attList!=null && !attList.isEmpty()){
			Iterator<HlAttendance> attitr=attList.iterator();
			while (attitr.hasNext()) {
				HlAttendance hlAttendance = (HlAttendance) attitr.next();
				Date attDate=hlAttendance.getAttendanceDate();
				Calendar cal=Calendar.getInstance();
				cal.setTime(attDate);
				int month=cal.get(Calendar.MONTH)+1;
				int year=cal.get(Calendar.YEAR);
				
				
				// year present
				if(yearMap.containsKey(year)){
					//month already present
						if(yearMap.get(year).containsKey(month)){
						int oldvalue=yearMap.get(year).get(month);
						oldvalue++;
						yearMap.get(year).put(month,oldvalue);
						yearMap.put(year, yearMap.get(year));
						}else{
							yearMap.get(year).put(month, 1);
							yearMap.put(year, yearMap.get(year));
						}
				}else{
					//month already present
					if(absentMap.containsKey(month)){
						int oldvalue=absentMap.get(month);
						oldvalue++;
						absentMap.put(month,oldvalue);
						yearMap.put(year, absentMap);
					}else{
						absentMap.put(month, 1);
						yearMap.put(year, absentMap);
					}
					
					
				}
				
			}
		}
		return yearMap;
	}
	public List<ResidentStudentInfoTO> convertBOListtoTOList (
			List<HlApplicationForm> list) throws Exception {
		List<ResidentStudentInfoTO> residentList=new ArrayList<ResidentStudentInfoTO>();
		Iterator<HlApplicationForm> boItr=list.iterator();
		while (boItr.hasNext()) {
			HlApplicationForm hlApplicationForm = (HlApplicationForm) boItr.next();
			ResidentStudentInfoTO rto=convertBOtoTO(hlApplicationForm);
			residentList.add(rto);
		}
		return residentList;
	}
}
