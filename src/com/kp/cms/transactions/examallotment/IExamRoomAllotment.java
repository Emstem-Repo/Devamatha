package com.kp.cms.transactions.examallotment;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentSettingsPoolWise;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.bo.examallotment.StudentClassGroup;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentForm;


public interface IExamRoomAllotment {

	List<Object[]> getCourses(String cycleId, ExamRoomAllotmentForm allotmentForm) throws Exception;

	List<Student> getStudentsForCourse(ExamRoomAllotmentForm allotmentForm, Map<String, List<Integer>> semWiseCourses) throws Exception;

	List<Object[]> getMostNoOfExamCourses(ExamRoomAllotmentForm allotmentForm, Map<String, List<Integer>> semWiseCourses) throws Exception;

	List<RoomMaster> getAvailableRooms(ExamRoomAllotmentForm allotmentForm) throws Exception;

	List<ExamRoomAllotmentCycle> getExamRoomCycles() throws Exception;

	void saveAllotment(List<ExamRoomAllotment> allotmentList, ExamRoomAllotmentForm allotmentForm) throws Exception;

	List<Object[]> getCoursesFromGroups(String cycleId,
			ExamRoomAllotmentForm allotmentForm) throws Exception;

	void removeRoomAllotment(ExamRoomAllotmentForm allotmentForm) throws Exception;

	int getNoOfStudentAllotInSameRoom() throws Exception;

	List<StudentClassGroup> getStudentsForGroupCourse(ExamRoomAllotmentForm allotmentForm,
			Map<String, List<Integer>> semWiseCourses) throws Exception;
	public List<Object[]> getCoursesForSpecialization(String cycleId, String campus)throws Exception;

	public Map<Integer, String>  getSpecializationForStudentsByCourses( ExamRoomAllotmentForm allotmentForm,
			Map<String, List<Integer>> semWiseCourses)throws Exception;

	public List<ExaminationSessions> getSessionsList()throws Exception;

	Map<Integer, List<String>> getcourseWiseClassesDetails(List<Integer> classesList)throws Exception;

	List<Student> getStudentsForSelectedClasses(List<Integer> classesList)throws Exception;

	void removeRoomAllotmentForDateAndSession( ExamRoomAllotmentForm allotmentForm, List<Integer> roomListIds)throws Exception;

	List<RoomMaster> getSelectedRoomsDetails(ExamRoomAllotmentForm allotmentForm, List<Integer> roomListIds)throws Exception;

	void getExamTypeByExamName(ExamRoomAllotmentForm allotmentForm)throws Exception;

	public int getTotalStudentForClasses(List<Integer> classList, String hqlQuery)throws Exception;

	public boolean getDuplicateAllotment(ExamRoomAllotmentForm allotmentForm, List<Integer> classList)throws Exception;

	public boolean isTimeTableDefineForClasses(ExamRoomAllotmentForm allotmentForm, List<Integer> classList)throws Exception;

	public List<Integer> checkSelectedRoomsAlreadyAlloted( ExamRoomAllotmentForm allotmentForm, List<Integer> roomList)throws Exception;

	public boolean isTimeTableDefineForClasses1(ExamRoomAllotmentForm allotmentForm, List<Integer> classList)throws Exception;

	
}
