package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.mapping.Array;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.attendance.AttendanceReEntryForm;
import com.kp.cms.forms.attendance.CocurricularAttendnaceEntryForm;
import com.kp.cms.helpers.attendance.AttendanceReEntryHelper;
import com.kp.cms.helpers.attendance.CocurricularAttendnaceEntryHelper;
import com.kp.cms.helpers.attendance.PeriodHelper;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.to.attendance.CocurricularAttendnaceEntryTo;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.transactions.attandance.IAttendanceReEntryTransactin;
import com.kp.cms.transactions.attandance.ICocurricularAttendnaceEntryTransaction;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceReEntryTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.CocurricularAttendnaceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;

public class CocurricularAttendnaceEntryHandler {
	private static volatile CocurricularAttendnaceEntryHandler cocurricularAttendnaceEntryHandler= null;
	private static final Log log = LogFactory.getLog(CocurricularAttendnaceEntryHandler.class);
	private CocurricularAttendnaceEntryHandler()
	{
		
	}
    // get Instance Method 
	public static CocurricularAttendnaceEntryHandler getInstance()
	{
		if(cocurricularAttendnaceEntryHandler== null)
		{
			CocurricularAttendnaceEntryHandler cocurricularAttendnaceEntryHandler = new CocurricularAttendnaceEntryHandler();
			return cocurricularAttendnaceEntryHandler; 
		}
		return cocurricularAttendnaceEntryHandler;
	}
	public List<Student> getStudents(CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm) throws Exception {
		log.debug("call of getStudents method in CocurricularAttendnaceEntryHandler.class");
		String studentQuery = CocurricularAttendnaceEntryHelper.getInstance().getStudentQuery(cocurricularAttendnaceEntryForm);
		ICocurricularAttendnaceEntryTransaction transaction=  CocurricularAttendnaceEntryTransactionImpl.getInstance();
		List<Student> studentList = transaction.getStudentList(studentQuery);
		log.debug("end of getStudents method in CocurricularAttendnaceEntryHandler.class");
		return studentList;
	}
	public Map<Date, CocurricularAttendnaceEntryTo> getCocurricularAttendanceMap(CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm) throws Exception {
		log.debug("call of getCocurricularAttendanceMap method CocurricularAttendnaceEntryHandler.class");
		ICocurricularAttendnaceEntryTransaction transaction =  CocurricularAttendnaceEntryTransactionImpl.getInstance();
		
		List<CocurricularAttendnaceEntryTo> list = new ArrayList<CocurricularAttendnaceEntryTo>();
	    List<Period> periodList =  transaction.getPeriodListByClass(cocurricularAttendnaceEntryForm.getClasses()); 
	    List<PeriodTO> periodTOList = PeriodHelper.getInstance().copyPeriodBOToTO(periodList);
		Map<Date, CocurricularAttendnaceEntryTo> attendacePeriodMap = new HashMap<Date, CocurricularAttendnaceEntryTo>();
		Student student = transaction.getStudentByRegNo(CocurricularAttendnaceEntryHelper.getInstance().getStudentByRegnoQuery(cocurricularAttendnaceEntryForm));
		List<Object[]> attendanceObjectsList = new ArrayList<Object[]>();
		List<Object[]> duplicateList = new ArrayList<Object[]>();
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		subjectMap  = transaction.getSubjectMap();
		if(student!= null )
		{
			cocurricularAttendnaceEntryForm.setStuId(student.getId());
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
			cocurricularAttendnaceEntryForm.setStudentName(name);
			
			String attendanceDuplicateQuery = CocurricularAttendnaceEntryHelper.getInstance().getAttendanceDuplicateQuery(cocurricularAttendnaceEntryForm,student.getId());
			duplicateList = transaction.getDuplicateRecords(attendanceDuplicateQuery);
			String attendanceQuery =  CocurricularAttendnaceEntryHelper.getInstance().getAttendanceQuery(cocurricularAttendnaceEntryForm,student.getId());
			attendanceObjectsList = transaction.getAttendnaceListByDates(attendanceQuery);
			//list = CocurricularAttendnaceEntryHelper.getInstance().convertBolistTOtoList(attendanceObjectsList,cocurricularAttendnaceEntryForm,periodTOList,duplicateList);
			attendacePeriodMap= CocurricularAttendnaceEntryHelper.getInstance().getAttendancePeriodMap(attendanceObjectsList,cocurricularAttendnaceEntryForm,periodTOList,duplicateList,subjectMap);
		}
		log.debug("end of getCocurricularAttendanceMap method CocurricularAttendnaceEntryHandler.class"); 
		return attendacePeriodMap;
	}
	public Map<Integer, String> getActivityMap() throws Exception {
		log.debug("call of getActivityMap method in CocurricularAttendnaceEntryHandler.class");
		Map<Integer, String> cocurricularActivity = new HashMap<Integer, String>();
		ICocurricularAttendnaceEntryTransaction transaction = new CocurricularAttendnaceEntryTransactionImpl().getInstance();
		cocurricularActivity  = transaction.getCocurricularActivity();
		log.debug("end of getActivityMap method in CocurricularAttendnaceEntryHandler.class");
		return cocurricularActivity;
	}
	public Boolean saveCocurricularLeaveDetails(CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm) throws Exception {
		log.debug("call of saveCocurricularLeaveDetails method in CocurricularAttendnaceEntryHandler.class");
		boolean isAdded= false;
		ICocurricularAttendnaceEntryTransaction transaction =  CocurricularAttendnaceEntryTransactionImpl.getInstance();
		List<StuCocurrLeave> cocurricularList = CocurricularAttendnaceEntryHelper.getInstance().createBoObject(cocurricularAttendnaceEntryForm);
		List<StuCocurrLeave> cocurricularListNew = new ArrayList<StuCocurrLeave>();		
		if(cocurricularAttendnaceEntryForm.getApproveLeaveTOs()!=null)
		{
			List<ApproveLeaveTO> approveLeaveTOs = cocurricularAttendnaceEntryForm.getApproveLeaveTOs();
			Iterator<StuCocurrLeave> iterator = cocurricularList.iterator();
			List<Integer> dupCheckLis = new ArrayList<Integer>();
			/*
			 * checking duplicate id in StuCocurrLeave object and removing the duplicate objects
			 * */
			while(iterator.hasNext())
			{
				StuCocurrLeave cocurrLeave = iterator.next();
				if(!dupCheckLis.contains(cocurrLeave.getId()))
				{
					if(cocurrLeave.getId()!=0) // for new record id will be 0 no need of duplicate check
					{
						dupCheckLis.add(cocurrLeave.getId());
						
					}
					cocurricularListNew.add(cocurrLeave); // adding to new list without having any duplicate object
				}
			}
			
			isAdded = transaction.saveCocurricularLeaveDetails(cocurricularListNew,approveLeaveTOs,cocurricularAttendnaceEntryForm);
			if(isAdded==true)
				cocurricularAttendnaceEntryForm.setCurrLeaveCount(cocurricularList.size());
		}
		log.debug("end of saveCocurricularLeaveDetails method in CocurricularAttendnaceEntryHandler.class");
		return isAdded;
	}
	
	
}
