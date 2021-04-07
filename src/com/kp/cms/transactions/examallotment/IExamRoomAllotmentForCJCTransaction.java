package com.kp.cms.transactions.examallotment;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentForCJCForm;
import com.kp.cms.to.admin.StudentTO;

public interface IExamRoomAllotmentForCJCTransaction {

	public List getDetailsForQuery(String roomDetailsQuery)throws Exception;

	public String getRegisterNo(String hqlQuery)throws Exception;

	public Map<String,StudentTO> getStudentForSelectedClass( String academicYear, String registerNo, int noOfStudents, int classId)
			throws Exception;

	public boolean getSaveOrUpdateAllotmentDetails( ExamRoomAllotment allotmentList)throws Exception;

	public LinkedList<String> getRegisterNumbersList( ExamRoomAllotmentForCJCForm objForm, String registerNo,
			int noOfStudents, String string, String mode)throws Exception;

	public Map<String, StudentTO> getStudentsForSelectedSubject( String academicYear, String lastRegisterNo,
			int noOfStudents, int subjectId, String campusId)throws Exception;

	public ExamRoomAllotment getDetailsForQuery1(String getStudentAllotedDetails)throws Exception;

	public List<Object[]> getSubjectDetailsOnDateAndSession( ExamRoomAllotmentForCJCForm objForm)throws Exception;

}
