package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.StudentListForSignatureForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.reports.StudentListForSignatureTO;

public class StudentListForSignatureHelper {
	private static final Log log = LogFactory.getLog(StudentListForSignatureHelper.class);
	public static volatile StudentListForSignatureHelper studentListForSignatureHelper = null;
	public static StudentListForSignatureHelper getInstance(){
		if(studentListForSignatureHelper == null){
			studentListForSignatureHelper = new StudentListForSignatureHelper();  
		}
		return studentListForSignatureHelper;
	}
		

	/**
	 * 
	 * @param studentList
	 * @return
	 * @throws Exception
	 */
	public List<StudentListForSignatureTO> copyBosToTO(List<Student> studentList, StudentListForSignatureForm  studentListForSignatureForm) throws Exception {
		log.debug("inside copyBosToTO");
		StudentListForSignatureTO listForSignatureTO;
		List<StudentListForSignatureTO> studentToList = new ArrayList<StudentListForSignatureTO>();
		SingleFieldMasterTO coulmnTo = new SingleFieldMasterTO(); 
		Student student;
		Iterator<Student> itr = studentList.iterator();
		int slNo = 0;
		StringBuffer studentName = new StringBuffer();
		while (itr.hasNext()){
			List<SingleFieldMasterTO> coulmnNameList = new ArrayList<SingleFieldMasterTO>();
			listForSignatureTO = new StudentListForSignatureTO(); 
			student = itr.next();
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
			listForSignatureTO.setStudentName(studentName.toString());
			
			StringBuffer rollNoRegNo = new StringBuffer();
			if(student.getRollNo()!= null &&  !student.getRollNo().trim().isEmpty() && 
				student.getRegisterNo()!= null && !student.getRegisterNo().trim().isEmpty()	){
				rollNoRegNo.append(student.getRollNo());
				rollNoRegNo.append("/");
				rollNoRegNo.append(student.getRegisterNo());
						
			}
			else if (student.getRollNo()!= null &&  !student.getRollNo().trim().isEmpty()){
				rollNoRegNo.append(student.getRollNo());
			}
			else if (student.getRegisterNo()!= null && !student.getRegisterNo().trim().isEmpty()){
				rollNoRegNo.append(student.getRegisterNo());
			}
			listForSignatureTO.setRollOrRegNo(rollNoRegNo.toString());
			slNo = slNo + 1;
			listForSignatureTO.setSlNo(slNo);
			coulmnTo.setName(studentListForSignatureForm.getColumnHeading());
			coulmnNameList.add(coulmnTo);
			listForSignatureTO.setColumnName(coulmnNameList);
			studentToList.add(listForSignatureTO);
		}
		log.debug("exit copyBosToTO");
		return studentToList;
	}	
	
}
