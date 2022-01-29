package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.ListofRollRegNoForm;
import com.kp.cms.to.reports.ClasswithStudentTO;
import com.kp.cms.to.reports.ListofRollRegNoReportTO;

public class ListofRollRegNoHelper {


	public static volatile ListofRollRegNoHelper listofRollRegNoHelper = null;

	public static ListofRollRegNoHelper getInstance() {
		if (listofRollRegNoHelper == null) {
			listofRollRegNoHelper = new ListofRollRegNoHelper();
			return listofRollRegNoHelper;
		}
		return listofRollRegNoHelper;
	}

	/**
	 * 
	 * @param studList
	 * @return
	 * @throws Exception
	 */
	public void copyBosToList(List<Student> studList, ListofRollRegNoForm regNoForm) throws Exception {
		Iterator<Student> iterator = studList.iterator();
		Student student;
		List<ListofRollRegNoReportTO> studentToList = null;
		ListofRollRegNoReportTO studentTO;
		StringBuffer studentName = new StringBuffer();
		Set<Integer> classidSet = new HashSet<Integer>();
		Map<String, List<ListofRollRegNoReportTO>> studentMapList = new HashMap<String, List<ListofRollRegNoReportTO>>();
		int classId;
		String className;
		int slNo = 0;
		while (iterator.hasNext()) {
			studentTO = new ListofRollRegNoReportTO();
			student = (Student) iterator.next();
			classId = 0;
			className = "";
			if(student.getClassSchemewise()!= null && student.getClassSchemewise().getClasses()!= null){
				classId = student.getClassSchemewise().getClasses().getId();
				className = student.getClassSchemewise().getClasses().getName();
			}
			else{
				continue;
			}
			if(!classidSet.contains(classId)){
				slNo = 1;
				studentToList = new ArrayList<ListofRollRegNoReportTO>();
			}
			else {
				slNo = slNo + 1;
				studentToList = studentMapList.get(className); 
			}
			
			studentTO.setSlNo(slNo);
			if(student.getRegisterNo()!= null && !student.getRegisterNo().trim().isEmpty()){
				studentTO.setRegisterNo(student.getRegisterNo());
			}
			if(student.getRollNo()!= null && !student.getRollNo().trim().isEmpty()){
				studentTO.setRollNo(student.getRollNo());
			}
			
			if(student.getClassSchemewise()!= null && student.getClassSchemewise().getClasses()!= null &&
					student.getClassSchemewise().getClasses().getName()!= null){
				studentTO.setClassName(student.getClassSchemewise().getClasses().getName());
			}
			studentName = new StringBuffer();
			
			studentName.append(student.getAdmAppln().getPersonalData().getFirstName());
			if(student.getAdmAppln().getPersonalData().getMiddleName()!= null && !student.getAdmAppln().getPersonalData().getMiddleName().trim().isEmpty()){
				studentName.append(" ");
				studentName.append(student.getAdmAppln().getPersonalData().getMiddleName());
			}
			if(student.getAdmAppln().getPersonalData().getLastName()!= null && !student.getAdmAppln().getPersonalData().getLastName().trim().isEmpty()){
				studentName.append(" ");
				studentName.append(student.getAdmAppln().getPersonalData().getLastName());
			}			
			
			studentTO.setStudentName(studentName.toString());

			
			classidSet.add(classId);
			
			studentToList.add(studentTO);	
			studentMapList.put(className, studentToList);
		}
		
		List<ListofRollRegNoReportTO> totalList= new ArrayList<ListofRollRegNoReportTO>();
		List<ClasswithStudentTO> withStudentList= new ArrayList<ClasswithStudentTO>();
		
		if(studentMapList!=null && studentMapList.keySet()!=null && !studentMapList.keySet().isEmpty()){
			Iterator<String> rollNoRegNoMapItr=studentMapList.keySet().iterator();
			while (rollNoRegNoMapItr.hasNext()) {
				String classesName = rollNoRegNoMapItr.next();
				ClasswithStudentTO stTo= new ClasswithStudentTO();
				stTo.setClassName(classesName);
				stTo.setStudents(studentMapList.get(classesName));
				totalList.addAll(studentMapList.get(classesName));
				withStudentList.add(stTo);
			}
		}
		regNoForm.setTotalstudentList(totalList);
		regNoForm.setStudentListWithClass(withStudentList);
		
	}
	
	
}
