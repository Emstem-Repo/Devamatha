package com.kp.cms.transactions.hostel;

import java.util.Date;
import java.util.List;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.bo.exam.ExamMarksEntryBO;

public interface IHostelStudentEvaluationTransaction {
	
	 public List<HlApplicationForm> getHlApplicationForm(int hostelId,Date fromDate,Date toDate,String year)throws Exception;
	 public List<HlDisciplinaryType> getDisciplinaryTypes()throws Exception;
	 public AttendanceStudent getAttendanceStudent(int studentId) throws Exception;
	 public List getAttendanceStudents(Date fromDate,Date toDate,int academicYear,int studentId) throws Exception;
	 public List getAttendanceStudentsAttended(Date fromDate,Date toDate,int academicYear,int studentId) throws Exception;
	 public ExamMarksEntryBO getExamMarksEntryBO(int studentId)throws Exception;
	 public Subject getSubject(int subjectId)throws Exception;

}
