package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HonorsEntryBo;
import com.kp.cms.bo.admin.HonoursCourse;
import com.kp.cms.bo.admin.HonoursCourseApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.HonoursCourseEntryForm;

public interface IHonoursCourseEntryTransaction {

	public Map<Integer, String> getCourseMapDetails()throws Exception;

	public List<HonoursCourse> getHonoursCourseList()throws Exception;

	public boolean getDuplicateResult( HonoursCourseEntryForm honoursCourseEntryForm)throws Exception;

	public boolean saveHonoursCourse(HonoursCourse honoursCourse, String mode)throws Exception;

	public HonoursCourse editHonoursCourse(int id)throws Exception;

	public boolean deleteHonoursCourse(int id, boolean activate, HonoursCourseEntryForm honoursCourseEntryForm)throws Exception;

	public Map<Integer, String> getHonoursCourseMap(String courseId) throws Exception;

	public List<Object[]> getDataForQuery(String query) throws Exception;

	public List<Integer> getSubjectList(String courseId) throws  Exception;

	public List<Object[]> getAttendancePercentage(int studentId) throws Exception;

	public Student getStudentDetails(String studentId) throws Exception;

	public Map<Integer, Boolean> getCertificateMap(String studentId) throws Exception;

	public List<String> getSupplimentaryAppeared(int studentId) throws Exception;

	public void saveAppliedCourse(HonoursCourseApplication bo) throws Exception;

	public List<HonoursCourseApplication> getAppliedStudentCourseDetails(String year, String courseId) throws Exception;

	public String saveDetals(List<HonorsEntryBo> boList) throws Exception;

	public Map<Integer, String> getHonoursCourseMap() throws Exception;

	public Map<Integer, String> getHonoursApplicationCourseMap() throws Exception;

}
