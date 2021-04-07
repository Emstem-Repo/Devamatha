package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.CertificateCourseTeacher;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.CertificateCourseEntryForm;
import com.kp.cms.forms.admission.StudentCertificateCourseForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;
import com.kp.cms.to.admission.StudentCertificateCourseTO;
import com.kp.cms.utilities.CommonUtil;

public class StudentCertificateCourseHelper {
	public static volatile StudentCertificateCourseHelper studentCertificateCourseHelper = null;
	private static final Log log = LogFactory.getLog(StudentCertificateCourseHelper.class);
	public static StudentCertificateCourseHelper getInstance() {
		if (studentCertificateCourseHelper == null) {
			studentCertificateCourseHelper = new StudentCertificateCourseHelper();
			return studentCertificateCourseHelper;
		}
		return studentCertificateCourseHelper;
	}
	/**
	 * 
	 * @param courseList
	 * @return
	 */
	public List<CertificateCourseTeacherTO> populateCourseBOtoTO(CertificateCourse bo){
		log.info("Start of populateCourseBOtoTO of CertificateCourseHelper");
		CertificateCourseTeacherTO certificateCourseTeacherTO;
		List<CertificateCourseTeacherTO> courseToList = new ArrayList<CertificateCourseTeacherTO>();
//		Iterator<CertificateCourseTeacher> iterator=courseList.iterator();
//		while (iterator.hasNext()) {
			certificateCourseTeacherTO = new CertificateCourseTeacherTO();
			certificateCourseTeacherTO.setCertificateCourseName(bo.getCertificateCourseName());
			certificateCourseTeacherTO.setTeacherName(bo.getUsers().getUserName());
			if(bo.getStartTime()!=null)
			certificateCourseTeacherTO.setStartTime(bo.getStartTime());
			if(bo.getStartTime()!=null)
			certificateCourseTeacherTO.setEndTime(bo.getEndTime());
			certificateCourseTeacherTO.setVenue(bo.getVenue());
			if(bo.getUsers().getEmployee()!=null)
			certificateCourseTeacherTO.setDepartment(bo.getUsers().getEmployee().getDepartment().getName());
			certificateCourseTeacherTO.setDescription(bo.getDescription());
			courseToList.add(certificateCourseTeacherTO);
//		}
		log.info("End of populateCourseBOtoTO of CertificateCourseHelper");
		return courseToList;		
	}
	/**
	 * 
	 * @param student
	 * @return
	 */
	public StudentTO covertToStudentTO(Student student) throws Exception{
		log.info("Start of covertToStudentTO of CertificateCourseHelper");
		PersonalData personalData = new PersonalData();
		StudentTO studentTO = new StudentTO();
		studentTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
		studentTO.setRegisterNo(student.getRegisterNo());
		studentTO.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
		personalData.setGender(student.getAdmAppln().getPersonalData().getGender());
		studentTO.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), CMSConstants.SOURCE_DATE ,CMSConstants.DEST_DATE));
		
		studentTO.setRegisterNo(student.getRegisterNo());
		if(student.getClassSchemewise()!= null && student.getClassSchemewise().getClasses()!= null){
			studentTO.setClassName(student.getClassSchemewise().getClasses().getName());
		}
		personalData.setPermanentAddressLine1(student.getAdmAppln().getPersonalData().getPermanentAddressLine1());
		personalData.setPermanentAddressLine2(student.getAdmAppln().getPersonalData().getPermanentAddressLine2());
		if(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!= null){
			studentTO.setCountryName(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName());
		}
		if(student.getAdmAppln().getPersonalData().getStateByStateId()!= null){
			studentTO.setStateName(student.getAdmAppln().getPersonalData().getStateByStateId().getName());
		}
		personalData.setPhNo1(student.getAdmAppln().getPersonalData().getPhNo1());
		personalData.setPhNo2(student.getAdmAppln().getPersonalData().getPhNo2());
		personalData.setPhNo3(student.getAdmAppln().getPersonalData().getPhNo3());
		
		personalData.setMobileNo1(student.getAdmAppln().getPersonalData().getMobileNo1());
		personalData.setMobileNo2(student.getAdmAppln().getPersonalData().getMobileNo2());
		personalData.setMobileNo3(student.getAdmAppln().getPersonalData().getMobileNo3());
		personalData.setEmail(student.getAdmAppln().getPersonalData().getEmail());
		
		studentTO.setPersonalData(personalData);
		
		studentTO.setCourseName(student.getAdmAppln().getCourse().getName());
		log.info("End of populateCourseBOtoTO of CertificateCourseHelper");
		return studentTO;		
	}
	/**
	 * @param courseForm
	 * @return
	 * @throws Exception
	 */
	public StudentCertificateCourse copyDataFromFormToBO(StudentCertificateCourseForm courseForm) throws Exception{
		StudentCertificateCourse studentCertificateCourse = new StudentCertificateCourse();
		CertificateCourse certificateCourse = new CertificateCourse();
		certificateCourse.setId(courseForm.getCertificateCourseId());
		studentCertificateCourse.setCertificateCourse(certificateCourse);
		return studentCertificateCourse;
		}
	/**
	 * @param studentId
	 * @param courseForm
	 * @return
	 */
	public String getQueryBystudentId(String studentId,StudentCertificateCourseForm courseForm) throws Exception {
		String query="select csd.semesterYearNo,s.admAppln.courseBySelectedCourseId.id,cs.id,csd.academicYear" +
				" from CurriculumSchemeDuration csd" +
				" join csd.classSchemewises cs " +
				" join cs.students s where s.id="+studentId;
		return query;
	}
	

	/**
	 * creating query for checking whehter the student is having certificate course for current semester No
	 * @param courseForm
	 * @return
	 * @throws Exception
	 */
/*	public String getQueryForStudentContainCertificateCourse(StudentCertificateCourseForm courseForm) throws Exception{
		String query="from StudentCertificateCourse s where s.isCancelled=0 and s.student.registerNo='"+courseForm.getRegisterNO()+"' and s.schemeNo=( select " +
				" s1.classSchemewise.classes.termNumber from Student s1 where s1.registerNo='"+courseForm.getRegisterNO()+"')";
		return query;// creating and returning the query
	}*/
	
	public String getQueryForStudentContainCertificateCourse(StudentCertificateCourseForm courseForm) throws Exception{
		String query="from StudentCertificateCourse s where s.isCancelled=0 and s.student.registerNo='"+courseForm.getRegisterNO()+"' and s.schemeNo='"+courseForm.getSemester()+"'";
		return query;// creating and returning the query
	}
	public List<StudentCertificateCourseTO> convertBoListToTOList(List<StudentCertificateCourse> list) throws Exception{
		List<StudentCertificateCourseTO> tos=new ArrayList<StudentCertificateCourseTO>();//creating new TO List
		Iterator<StudentCertificateCourse> itr=list.iterator();// Creating Iterator Object For Bo List to iterate
		while (itr.hasNext()) {// Iterating the Bo List using itr object with while loop
			StudentCertificateCourse bo = (StudentCertificateCourse) itr.next();// Getting one by One Bo object using itr object
			StudentCertificateCourseTO to=new StudentCertificateCourseTO();// Creating TO Object
			//Setting the required data to TO Object
			to.setId(bo.getId());
			to.setStudentId(bo.getStudent().getId());
			to.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
			to.setRegisterNo(bo.getStudent().getRegisterNo());
			to.setCertificateCourseName(bo.getCertificateCourse().getCertificateCourseName());
			tos.add(to);// adding to Object in To's List
		}
		return tos;// Returning TO's Object
	}
	

	public Map<Integer, String> convertBoListToTOListCourseName(List<Object[]>  list) throws Exception{
		List<StudentCertificateCourseTO> tos=new ArrayList<StudentCertificateCourseTO>();
		Map<Integer, String> courseMap = new LinkedHashMap<Integer, String>();
		Iterator<Object[]>  itr=list.iterator();
		while (itr.hasNext()) {
			Object[] bo=(Object[])itr.next();
			StudentCertificateCourseTO to=new StudentCertificateCourseTO();
			to.setId(Integer.parseInt(bo[0].toString()));
			to.setCertificateCourseName(bo[1].toString());
			tos.add(to);
			courseMap.put(to.getId(), to.getCertificateCourseName());
		}
		return courseMap;
	}
	public Map<Integer, String> convertListCourseName(List<Object[]>  list) throws Exception{
		List<StudentCertificateCourseTO> tos=new ArrayList<StudentCertificateCourseTO>();
		Map<Integer, String> courseMap = new LinkedHashMap<Integer, String>();
			StudentCertificateCourseTO to=new StudentCertificateCourseTO();
			to.setId(Integer.parseInt("0"));
			to.setCertificateCourseName("No Data Available");
			tos.add(to);
			courseMap.put(to.getId(), to.getCertificateCourseName());
		return courseMap;
	}
	
	
	public String getQueryByselectedCertificateCourse(String selectedCertificateCourse) throws Exception {
		String query=" select adm_appln.selected_course_id"+
					" from student_certificate_course "+
					" inner join student on student_certificate_course.student_id = student.id "+
					" inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
					" inner join adm_appln on student.adm_appln_id = adm_appln.id where certificate_course_id='"+selectedCertificateCourse+"' and adm_appln.is_cancelled='0' and student_certificate_course.is_cancelled='0' group by adm_appln.selected_course_id";
		return query;
	}
	public String getCurriculumIdByselCetifCourse(String selCertCourse) throws Exception {
		String query="SELECT adm_appln.selected_course_id, class_schemewise.curriculum_scheme_duration_id, curriculum_scheme_duration.semester_year_no "+
                     " FROM ((( student student INNER JOIN adm_appln adm_appln ON (student.adm_appln_id = adm_appln.id))"+
                     " INNER JOIN student_certificate_course student_certificate_course "+
                     " ON (student_certificate_course.student_id = student.id))"+
                     " INNER JOIN class_schemewise class_schemewise "+
                     " ON (student.class_schemewise_id = class_schemewise.id)) "+
                     " INNER JOIN curriculum_scheme_duration curriculum_scheme_duration "+
                     " ON (class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id) "+
                     " WHERE certificate_course_id = '"+selCertCourse+"' and adm_appln.is_cancelled='0' and student_certificate_course.is_cancelled='0' group by class_schemewise.curriculum_scheme_duration_id" ;
				
			/*	" select adm_appln.selected_course_id, class_schemewise.curriculum_scheme_duration_id "+
					" from student_certificate_course "+
					" inner join student on student_certificate_course.student_id = student.id "+
					" inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
					" inner join adm_appln on student.adm_appln_id = adm_appln.id where certificate_course_id='"+selCertCourse+"' group by class_schemewise.curriculum_scheme_duration_id";*/
		return query;
	}
	public String getAdmIdSelCetifCourse(String selCertCourse) throws Exception {
		String query="select adm_appln.selected_course_id, adm_appln.id "+
					"from student_certificate_course "+ 
					"inner join student on student_certificate_course.student_id = student.id "+
					"inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
					"INNER JOIN curriculum_scheme_duration curriculum_scheme_duration "+
					"ON (class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id) "+
					"inner join adm_appln on student.adm_appln_id = adm_appln.id "+
		            "where "+
		            "student_certificate_course.scheme_no= curriculum_scheme_duration.semester_year_no " +
		            "and certificate_course_id='"+selCertCourse+"' " +
		            "and adm_appln.is_cancelled='0' and student_certificate_course.is_cancelled='0' group by adm_appln.id";
		
		
		
		/*String query=" select adm_appln.selected_course_id, adm_appln.id "+
					" from student_certificate_course "+
					" inner join student on student_certificate_course.student_id = student.id "+
					" inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
					" inner join adm_appln on student.adm_appln_id = adm_appln.id where certificate_course_id='"+selCertCourse+"' " +
					" and adm_appln.is_cancelled='0' and student_certificate_course.is_cancelled='0' group by adm_appln.id";*/
		return query;
	}
	
	public void convertListCourseCodeName(List<Object[]>  list, StudentCertificateCourseForm courseForm ) throws Exception{
		List<Subject> tos=new ArrayList<Subject>();
		//StudentCertificateCourseForm courseForm = (StudentCertificateCourseForm) form;
		//Map<String, String> courseCodeNameMap = new LinkedHashMap<String, String>();
		Iterator<Object[]>  itr=list.iterator();
		while (itr.hasNext()) {
			Object[] bo=(Object[])itr.next();
			Subject to=new Subject();
			to.setCode(bo[0].toString());
			to.setName(bo[1].toString());
			tos.add(to);
			
			if(tos.size()>1)
			{
				throw new IllegalArgumentException("Multiple record available. Data incorrect");
			}
			else if(tos.size() == 1)
			{
				courseForm.setSubjectCode(to.getCode());
				courseForm.setSubjectName(to.getName());
			}
		
		
	}
	}
	
	
	}
