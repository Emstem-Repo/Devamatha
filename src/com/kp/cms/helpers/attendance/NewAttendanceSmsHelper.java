package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.attendance.NewAttendanceSmsForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.NewAttendanceSmsTransImpl;

public class NewAttendanceSmsHelper 
{
	private static volatile NewAttendanceSmsHelper newAttendanceSmsHelper = null;
	private static final Log log = LogFactory.getLog(NewAttendanceSmsHelper.class);
	
	public static NewAttendanceSmsHelper getInstance() 
	{
		if (newAttendanceSmsHelper == null) 
		{
			newAttendanceSmsHelper = new NewAttendanceSmsHelper();
			return newAttendanceSmsHelper;
		}
		return newAttendanceSmsHelper;
	}

	public List<StudentTO> convertBoToTO(List<Object[]> absenteeList) 
	{
		List<StudentTO> absenteesTOList=new ArrayList<StudentTO>();
		Iterator<Object[]> itr= absenteeList.iterator();
		List<Integer> stuList=new ArrayList<Integer>();
		List<Integer> periodList=new ArrayList<Integer>();
		List<Integer> subjectList=new ArrayList<Integer>();
		StudentTO stuTO=null;
		while(itr.hasNext())
		{
			Object[] arrObj=itr.next();
			AttendanceStudent attStud=null;
			int studId=0;			
			studId=(Integer)arrObj[3];
			if(!stuList.contains(studId))
			{
				stuList.add(studId);	
				stuTO=new StudentTO();
				stuTO.setId(studId);
				periodList=new ArrayList<Integer>();
				periodList.add((Integer)arrObj[1]);
				subjectList= new ArrayList<Integer>();
				subjectList.add((Integer)arrObj[0]);
				stuTO.setSubList(subjectList);
				stuTO.setPeriodList(periodList);
				absenteesTOList.add(stuTO);
				stuTO.setAttendanceStudentId(arrObj[5].toString());
				
			}
			else
			{
				periodList.add((Integer)arrObj[1]);
				subjectList.add((Integer)arrObj[0]);
				stuTO.setSubList(subjectList);
				stuTO.setPeriodList(periodList);
				String attennStudId=stuTO.getAttendanceStudentId()+","+arrObj[5].toString();
				stuTO.setAttendanceStudentId(attennStudId);
			}
			
		}
		return absenteesTOList;
	}
	public List<StudentTO> copyStudentBoToTO(List<Student> studentList,List<Integer> listOfDetainedStudents, List<StudentTO> absenteesList) throws Exception {
		log.info("Handler : Inside copyStudentBoToTO");
		List<StudentTO> list = new ArrayList<StudentTO>();
		IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
		Iterator<Student> itr = studentList.iterator();
		StudentTO studentTo;
		Student student;
		StringBuffer studentName=new StringBuffer();
		int subject=0;		
		while(itr.hasNext()) {
			
			
			student = itr.next();
			if(!listOfDetainedStudents.contains(student.getId()))
			{
				Iterator<StudentTO> iter=absenteesList.iterator();
				while(iter.hasNext())
				{
					StudentTO studTO=iter.next();
					if(studTO.getId()==student.getId())
					{
						studentTo = new StudentTO();
						studentTo.setId(student.getId());
						studentTo.setRegisterNo(student.getRegisterNo());
						studentTo.setRollNo(student.getRollNo());
						studentTo.setChecked(false);
						studentTo.setTempChecked(false);
						studentTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
						
						if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getFirstName() != null) {
							studentName.append(student.getAdmAppln().getPersonalData().getFirstName()).append(" ");
						} 
						if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getMiddleName() != null) {
							studentName.append(student.getAdmAppln().getPersonalData().getMiddleName()).append(" ");
						}
						if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getLastName() != null) {
							studentName.append(student.getAdmAppln().getPersonalData().getLastName()).append(" ");
						}
						
						//raghu
						if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getParentMob1() != null && student.getAdmAppln().getPersonalData().getParentMob2() != null) 
						
						//if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getParentMob1() != null && student.getAdmAppln().getPersonalData().getMobileNo2() != null) 
							
						{
							if(student.getAdmAppln().getPersonalData().getParentMob1()!=null && !student.getAdmAppln().getPersonalData().getParentMob1().isEmpty())
							{
								studentTo.setMobileNo1(student.getAdmAppln().getPersonalData().getParentMob1());
							}
							else
							{
								studentTo.setMobileNo1("91");
							}
							
							//raghu
							studentTo.setMobileNo2(student.getAdmAppln().getPersonalData().getParentMob2());
							//studentTo.setMobileNo2(student.getAdmAppln().getPersonalData().getMobileNo2());
						}else{
							if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getParentMob2() != null){
								studentTo.setMobileNo1("91");
								studentTo.setMobileNo2(student.getAdmAppln().getPersonalData().getParentMob2());
							}
						}
						studentTo.setStudentName(studentName.toString());
						if(student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null)
						{
							studentTo.setClassName(student.getClassSchemewise().getClasses().getName());
						}
						String periodNames="";
						if(studTO.getPeriodList() != null) {	
							Object[] tempArray = studTO.getPeriodList().toArray();
							String intType ="";
							for(int i=0;i<tempArray.length;i++){
								 intType = intType+tempArray[i];
								 if(i<(tempArray.length-1)){
									 intType = intType+",";
								 }
							}
							String query="select p.periodName from Period p where p.id in ("+intType+")";
							//periodNames=transaction.getPeriodNamesById(query);
							periodNames=NewAttendanceSmsTransImpl.getInstance().getPeriodNamesByIdNew(query);
						}	
						String subjectNames="";
						if(studTO.getSubList()!=null)
						{
							Object[] tempArray1 = studTO.getSubList().toArray();
							String intType1 ="";
							for(int i=0;i<tempArray1.length;i++){
								 intType1 = intType1+tempArray1[i];
								 if(i<(tempArray1.length-1)){
									 intType1 = intType1+",";
								 }
							}
							String subquery1="select s.consldtdMarkCardSubName from Subject s where s.id in ("+intType1+")";
							//periodNames=transaction.getPeriodNamesById(query);
							subjectNames=NewAttendanceSmsTransImpl.getInstance().getSubjectNamesByIdNew(subquery1);
						}
						
						studentTo.setPeriodName(periodNames);
						studentTo.setSubjectName(subjectNames);
						studentTo.setPeriodList(studTO.getPeriodList());
						studentTo.setSubList(studTO.getSubList());
						
						studentTo.setAttendanceStudentId(studTO.getAttendanceStudentId());
						list.add(studentTo);
						studentName = new StringBuffer();
					}
					
				}
				
			
					
				
		}
		}
		log.info("Handler : Leaving copyStudentBoToTO");
	    return list;	
	}

	public List<StudentTO> copyStudentBoToTO(List<Student> studentList,List<Integer> listOfDetainedStudents,NewAttendanceSmsForm newAttendanceSmsForm) throws Exception {
		log.info("Handler : Inside copyStudentBoToTO");
		List<StudentTO> list = new ArrayList<StudentTO>();
		IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
		Iterator<Student> itr = studentList.iterator();
		StudentTO studentTo;
		Student student;
		StringBuffer studentName=new StringBuffer();
		int subject=0;		
		while(itr.hasNext()) {
			
			
			student = itr.next();
			if(!listOfDetainedStudents.contains(student.getId()))
			{
						studentTo = new StudentTO();
						studentTo.setId(student.getId());
						studentTo.setRegisterNo(student.getRegisterNo());
						studentTo.setRollNo(student.getRollNo());
						studentTo.setChecked(false);
						studentTo.setTempChecked(false);
						studentTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
						
						if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getFirstName() != null) {
							studentName.append(student.getAdmAppln().getPersonalData().getFirstName()).append(" ");
						} 
						if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getMiddleName() != null) {
							studentName.append(student.getAdmAppln().getPersonalData().getMiddleName()).append(" ");
						}
						if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getLastName() != null) {
							studentName.append(student.getAdmAppln().getPersonalData().getLastName()).append(" ");
						}
						
						//raghu
						if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getParentMob1() != null && student.getAdmAppln().getPersonalData().getParentMob2() != null) 
						
						//if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getParentMob1() != null && student.getAdmAppln().getPersonalData().getMobileNo2() != null) 
							
						{
							if(student.getAdmAppln().getPersonalData().getParentMob1()!=null && !student.getAdmAppln().getPersonalData().getParentMob1().isEmpty())
							{
								studentTo.setMobileNo1(student.getAdmAppln().getPersonalData().getParentMob1());
							}
							else
							{
								studentTo.setMobileNo1("91");
							}
							
							//raghu
							studentTo.setMobileNo2(student.getAdmAppln().getPersonalData().getParentMob2());
							//studentTo.setMobileNo2(student.getAdmAppln().getPersonalData().getMobileNo2());
						}
						studentTo.setStudentName(studentName.toString());
						if(student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null)
						{
							studentTo.setClassName(student.getClassSchemewise().getClasses().getName());
						}
						String periodNames="";
						if(newAttendanceSmsForm.getTeacherNotEnteredPeriodList() != null) {	
							Object[] tempArray = newAttendanceSmsForm.getTeacherNotEnteredPeriodList().toArray();
							String intType ="";
							for(int i=0;i<tempArray.length;i++){
								 intType = intType+tempArray[i];
								 if(i<(tempArray.length-1)){
									 intType = intType+",";
								 }
							}
							String query="select p.periodName from Period p where p.id in ("+intType+")";
							//periodNames=transaction.getPeriodNamesById(query);
							periodNames=NewAttendanceSmsTransImpl.getInstance().getPeriodNamesByIdNew(query);
						}	
						
						studentTo.setPeriodName(periodNames);
						studentTo.setPeriodList(newAttendanceSmsForm.getTeacherNotEnteredPeriodList());
						
					
						list.add(studentTo);
						studentName = new StringBuffer();
					}	
		}

	    return list;	
	}

	public List<StudentTO> convertBoToTOforSMS(List<Object[]> absenteeList) throws Exception{
		List<StudentTO> absenteesTOList=new ArrayList<StudentTO>();
		Iterator<Object[]> itr= absenteeList.iterator();
		List<Integer> stuList=new ArrayList<Integer>();
		List<Integer> periodList=new ArrayList<Integer>();
		List<Integer> subjectList=new ArrayList<Integer>();
		StudentTO stuTO=null;
		while(itr.hasNext())
		{
			Object[] arrObj=itr.next();
			int studId=0;			
			studId=(Integer)arrObj[0];
			if(!stuList.contains(studId))
			{
				stuList.add(studId);	
				stuTO=new StudentTO();
				stuTO.setId(studId);
				periodList=new ArrayList<Integer>();
				periodList.add((Integer)arrObj[4]);
				subjectList= new ArrayList<Integer>();
				subjectList.add((Integer)arrObj[2]);
				stuTO.setSubList(subjectList);
				stuTO.setPeriodList(periodList);
				absenteesTOList.add(stuTO);
				
			}
			else
			{
				periodList.add((Integer)arrObj[4]);
				subjectList.add((Integer)arrObj[2]);
				stuTO.setSubList(subjectList);
				stuTO.setPeriodList(periodList);
			}
			
		}
		return absenteesTOList;
	}
}
