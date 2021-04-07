package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.AssignCertificateCourse;
import com.kp.cms.bo.admin.AssignCertificateCourseDetails;
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.forms.admission.CertificateCourseEntryForm;
import com.kp.cms.forms.admission.StudentCertificateCourseForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.admission.CertificateCourseEntryHelper;
import com.kp.cms.helpers.admission.StudentCertificateCourseHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;
import com.kp.cms.to.admission.StudentCertificateCourseTO;
import com.kp.cms.to.exam.MarksDetailsTO;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.transactions.admission.ICertificateCourseEntryTxn;
import com.kp.cms.transactions.admission.IStudentCertificateCourseTxn;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.admission.CertificateCourseEntryTxnImpl;
import com.kp.cms.transactionsimpl.admission.StudentCertificateCourseTxnImpl;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author user
 *
 */
public class StudentCertificateCourseHandler {
	public static volatile StudentCertificateCourseHandler studentCertificateCourseHandler = null;
	private static final Log log = LogFactory
			.getLog(StudentCertificateCourseHandler.class);

	public static StudentCertificateCourseHandler getInstance() {
		if (studentCertificateCourseHandler == null) {
			studentCertificateCourseHandler = new StudentCertificateCourseHandler();
			return studentCertificateCourseHandler;
		}
		return studentCertificateCourseHandler;
	}

	/**
	 * 
	 * @param programTypeId
	 * @param programId
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public List<CertificateCourseTO> getCertificateMandatoryCourses(
			int programTypeId, int programId, String stream,
			List<Integer> appliedList) throws Exception {
		log
				.info("Start of getCertificateMandatoryCourses of StudentCertificateCourseHandler");
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		List<CertificateCourse> courseBoList = iStudentCertificateCourseTxn
				.getMandatoryCertificateCourses(programTypeId, programId,
						stream);
		if (courseBoList != null && !courseBoList.isEmpty()) {
			return CertificateCourseEntryHelper.getInstance()
					.populateCourseBOtoTO(courseBoList, appliedList);
		}
		log
				.info("End of getCertificateMandatoryCourses of StudentCertificateCourseHandler");
		return null;
	}

	/**
	 * 
	 * @param programTypeId
	 * @param programId
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public List<CertificateCourseTO> getCertificateOptionalCourses(
			int programTypeId, int programId, String stream,
			List<Integer> appliedList) throws Exception {
		log
				.info("Start of getCertificateOptionalCourses of StudentCertificateCourseHandler");
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		List<CertificateCourse> courseBoList = iStudentCertificateCourseTxn
				.getOptionalCertificateCourses(programTypeId, programId, stream);
		if (courseBoList != null && !courseBoList.isEmpty()) {
			return CertificateCourseEntryHelper.getInstance()
					.populateCourseBOtoTO(courseBoList, appliedList);
		}
		log
				.info("End of getCertificateOptionalCourses of StudentCertificateCourseHandler");
		return null;
	}

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public Program getProgramByStudentId(int studentId) throws Exception {
		log
				.info("Start of getProgramByStudentId of StudentCertificateCourseHandler");
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		return iStudentCertificateCourseTxn.getProgramByStudentId(studentId);
	}

	public boolean saveCertificateCourse(
			StudentCertificateCourseForm courseForm, int studentId,
			Integer schemeNo) throws Exception {
		log
				.info("Start of getProgramByStudentId of StudentCertificateCourseHandler");
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		List<CertificateCourseTO> mandatoryCourseTOList = courseForm
				.getMandatorycourseList();
		StudentCertificateCourse studentCertificateCourse = new StudentCertificateCourse();
		CertificateCourse certificateCourse = new CertificateCourse();
		Student student = new Student();
		if (mandatoryCourseTOList != null && mandatoryCourseTOList.size() > 0) {
			Iterator<CertificateCourseTO> itr = mandatoryCourseTOList
					.iterator();
			while (itr.hasNext()) {
				CertificateCourseTO certificateCourseTO = (CertificateCourseTO) itr
						.next();
				if (certificateCourseTO.getCourseCheck() == null
						|| !certificateCourseTO.getCourseCheck()
								.equalsIgnoreCase("on")) {
					continue;
				}
				student.setId(studentId);
				studentCertificateCourse.setStudent(student);
				certificateCourse.setId(certificateCourseTO.getId());
				studentCertificateCourse
						.setCertificateCourse(certificateCourse);
				studentCertificateCourse.setSchemeNo(schemeNo);
				studentCertificateCourse.setCreatedBy(courseForm.getUserId());
				studentCertificateCourse.setModifiedBy(courseForm.getUserId());
				studentCertificateCourse.setCreatedDate(new Date());
				studentCertificateCourse.setLastModifiedDate(new Date());
				studentCertificateCourse.setIsCancelled(false);
				String year=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(studentId,"Student",true,"admAppln.appliedYear");
				if(year!=null && Integer.parseInt(year)>=2011)
					studentCertificateCourse.setIsOptional(false);
				else
					studentCertificateCourse.setIsOptional(true);
				String subjectId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(certificateCourseTO.getId(),"CertificateCourse",true,"subject.id");
				if(subjectId!=null && !subjectId.isEmpty()){
					Subject subject=new Subject();
					subject.setId(Integer.parseInt(subjectId));
					studentCertificateCourse.setSubject(subject);
				}
				
			}
		}
		if (courseForm.getOptionalCourseId() != null
				&& !courseForm.getOptionalCourseId().isEmpty()) {
			student.setId(studentId);
			studentCertificateCourse.setStudent(student);
			certificateCourse.setId(Integer.parseInt(courseForm
					.getOptionalCourseId()));
			studentCertificateCourse.setCertificateCourse(certificateCourse);
			studentCertificateCourse.setSchemeNo(schemeNo);
			studentCertificateCourse.setCreatedBy(courseForm.getUserId());
			studentCertificateCourse.setModifiedBy(courseForm.getUserId());
			studentCertificateCourse.setCreatedDate(new Date());
			studentCertificateCourse.setLastModifiedDate(new Date());
			studentCertificateCourse.setIsCancelled(false);
//			String year=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(studentId,"Student",true,"admAppln.appliedYear");
//			if(year!=null && Integer.parseInt(year)>=2011)
				studentCertificateCourse.setIsOptional(true);
//			else
//				studentCertificateCourse.setIsOptional(false);
			String subjectId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(courseForm .getOptionalCourseId()),"CertificateCourse",true,"subject.id");
			if(subjectId!=null && !subjectId.isEmpty()){
				Subject subject=new Subject();
				subject.setId(Integer.parseInt(subjectId));
				studentCertificateCourse.setSubject(subject);
			}
		}

		return iStudentCertificateCourseTxn
				.addStudentCertificateCourse(studentCertificateCourse);
	}

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public Integer getSchemeNoByStudentId(int studentId) throws Exception {
		log
				.info("Start of getProgramByStudentId of StudentCertificateCourseHandler");
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		return iStudentCertificateCourseTxn.getSchemeNoByStudentId(studentId);
	}

	/**
	 * 
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public List<CertificateCourseTeacherTO> getCertificateCourseTeacher(
			int courseId) throws Exception {
		log
				.info("Start of getCertificateCourseTeacher of StudentCertificateCourseHandler");
		// IStudentCertificateCourseTxn iStudentCertificateCourseTxn =
		// StudentCertificateCourseTxnImpl.getInstance();
		CertificateCourseEntryForm certificateCourseEntryForm = new CertificateCourseEntryForm();
		certificateCourseEntryForm.setId(courseId);
		ICertificateCourseEntryTxn transaction = CertificateCourseEntryTxnImpl
				.getInstance();
		CertificateCourse bo = transaction
				.editCertificateCourse(certificateCourseEntryForm);

		// List<CertificateCourseTeacher> courseTeacherList =
		// iStudentCertificateCourseTxn.getCertificateCourseTeacher(courseId);
		List<CertificateCourseTeacherTO> teacherList = StudentCertificateCourseHelper
				.getInstance().populateCourseBOtoTO(bo);
		return teacherList;
	}

	public Double getFeeAmount(int courseId) throws Exception {
		log.info("Start of getFeeAmount of StudentCertificateCourseHandler");
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		Double amount = iStudentCertificateCourseTxn.getFeeAmount(courseId);
		return amount;
	}

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public StudentTO getStudentDetails(int studentId) throws Exception {
		log
				.info("Start of getProgramByStudentId of StudentCertificateCourseHandler");
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		Student student = iStudentCertificateCourseTxn
				.getStudentDetails(studentId);
		return StudentCertificateCourseHelper.getInstance().covertToStudentTO(
				student);
	}

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getStudentCertificateCourseDetails(int studentId)
			throws Exception {
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		return iStudentCertificateCourseTxn
				.getStudentCertificateCourseDetails(studentId);
	}

	/**
	 * 
	 * @param studentId
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public boolean isAppliedForSemester(int studentId, int schemeNo)
			throws Exception {
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		return iStudentCertificateCourseTxn.isAppliedForSemester(studentId,
				schemeNo);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Integer> getSubjectMap() throws Exception {
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		return iStudentCertificateCourseTxn.getSubjectMap();
	}

	/**
	 * 
	 * @param courseId
	 * @param schemeNo
	 * @param appliedYear
	 * @return
	 * @throws Exception
	 */
	public Integer getAcademicYear(int courseId, int schemeNo, int appliedYear)
			throws Exception {
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		return iStudentCertificateCourseTxn.getAcademicYear(courseId, schemeNo,
				appliedYear);
	}

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<StudentCertificateCourse> getAppliedCourses(int studentId)
			throws Exception {
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		return iStudentCertificateCourseTxn.getAppliedCourses(studentId);
	}

	/**
	 * 
	 * @param studentId
	 * @param admAppln
	 * @return
	 * @throws Exception
	 */
	public Integer getCompletedCourseCount(int studentId, AdmAppln admAppln)
			throws Exception {
		String query="select s.schemeNo,s.subject.id,s.subject.isTheoryPractical from StudentCertificateCourse s where s.isCancelled=0 and s.isOptional=0 and s.student.id="+studentId;
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		IDownloadHallTicketTransaction transaction1= new DownloadHallTicketTransactionImpl();
		List<Object[]> studentCertificate=transaction.getDataForQuery(query);
		int count=0;
		boolean isMaxRecord=false;
		if(admAppln.getCourseBySelectedCourseId().getId()!=18){
			isMaxRecord=true;
		}
		if(studentCertificate!=null && !studentCertificate.isEmpty()){
			Iterator<Object[]> itr=studentCertificate.iterator();
			
			Map<Integer,StudentMarkDetailsTO> totSubMap=new HashMap<Integer, StudentMarkDetailsTO>();//subject and marks details to verify max or latest and keep in the map at last
			while (itr.hasNext()) {
				Object[] obj=itr.next();
				if(obj[0]!=null && obj[1]!=null && obj[2]!=null){
					int schemeNo=Integer.parseInt(obj[0].toString());
					int subjectId=Integer.parseInt(obj[1].toString());
					String isTheoryPractical=obj[2].toString();
					String marksQuery=" select EXAM_internal_mark_supplementary_details.id, classes.name as className, classes.id as classId, student.register_no, student.id StudentID, EXAM_definition.id ExamDefId, " +
					" EXAM_definition.year, EXAM_definition.month, EXAM_definition.name as examName, subject.id SubjectID, subject.name as subName, " +
					" EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.theory_total_attendance_mark, " +
					" EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.practical_total_attendance_mark, " +
					" EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark as intTheorymark, EXAM_internal_mark_supplementary_details.theory_total_attendance_mark as intTheoryAttMark, " +
					" EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark  as intPracticalMark, EXAM_internal_mark_supplementary_details.practical_total_attendance_mark as intPracticalAttMark, " +
					" EXAM_student_final_mark_details.student_theory_marks as finalStudentTheoryMarks,  " +
					" EXAM_student_final_mark_details.student_practical_marks as finalStudentPracticalMarks, " +
					" subject_type.name as subTypeName, subject.is_theory_practical " +
					" from EXAM_student_overall_internal_mark_details " +
					" inner join student ON EXAM_student_overall_internal_mark_details.student_id = student.id " +
					" inner join classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id " +
					" inner join subject ON EXAM_student_overall_internal_mark_details.subject_id = subject.id " +
					" inner join subject_type ON subject.subject_type_id = subject_type.id " +
					" left join EXAM_student_final_mark_details on EXAM_student_final_mark_details.student_id = student.id " +
					" and EXAM_student_final_mark_details.class_id = classes.id  " +
					" and EXAM_student_final_mark_details.subject_id = subject.id " +
					" and ((EXAM_student_final_mark_details.student_theory_marks is not null) or (EXAM_student_final_mark_details.student_practical_marks is not null)) " +
					" left join EXAM_internal_mark_supplementary_details on EXAM_internal_mark_supplementary_details.class_id = classes.id " +
					" and EXAM_internal_mark_supplementary_details.student_id = student.id " +
					" and EXAM_internal_mark_supplementary_details.subject_id = subject.id " +
					" left join EXAM_definition ON EXAM_definition.id = if(EXAM_student_final_mark_details.exam_id is null, " +
					" (if(EXAM_internal_mark_supplementary_details.exam_id is null, EXAM_student_overall_internal_mark_details.exam_id, EXAM_internal_mark_supplementary_details.exam_id)),  " +
					"                                                     EXAM_student_final_mark_details.exam_id) " +
					" where student.id = " +studentId+
					" and classes.term_number ="+schemeNo+" and subject.id in ("+subjectId+")" +
					" order by student.id, subject.id, EXAM_definition.year DESC, EXAM_definition.month DESC  ";
					List<Object[]> marksList=transaction1.getStudentHallTicket(marksQuery);
					if(marksList!=null && !marksList.isEmpty()){
						Iterator<Object[]> markItr=marksList.iterator();
						while (markItr.hasNext()) {
							Object[] objects = (Object[]) markItr.next();
							if(objects[9]!=null){
								StudentMarkDetailsTO markDetailsTO= new StudentMarkDetailsTO();
								if(objects[4]!=null)
									markDetailsTO.setStudentId(Integer.parseInt(objects[4].toString()));
								if(objects[9]!=null)
									markDetailsTO.setSubjectId(Integer.parseInt(objects[9].toString()));
								double theoryRegMark=0;
								boolean isTheoryAlpha=false;
								if (objects[15] != null || objects[16] != null) {
									if(objects[15]!=null){
										if(!StringUtils.isAlpha(objects[15].toString()))
											theoryRegMark=theoryRegMark+Double.parseDouble(objects[15].toString());
										else
											isTheoryAlpha=true;
									}
									if(objects[16]!=null){
										if(!StringUtils.isAlpha(objects[16].toString()))
											theoryRegMark=theoryRegMark+Double.parseDouble(objects[16].toString());
										else
											isTheoryAlpha=true;
									}
								}else{
									if(objects[11]!=null){
										if(!StringUtils.isAlpha(objects[11].toString()))
											theoryRegMark=theoryRegMark+Double.parseDouble(objects[11].toString());
										else
											isTheoryAlpha=true;
									}
									if(objects[12]!=null){
										if(!StringUtils.isAlpha(objects[12].toString()))
											theoryRegMark=theoryRegMark+Double.parseDouble(objects[12].toString());
										else
											isTheoryAlpha=true;
									}
								}
								if(!isTheoryAlpha)
									markDetailsTO.setStuTheoryIntMark(String.valueOf(theoryRegMark));
								else
									markDetailsTO.setStuTheoryIntMark("AA");
								double practicalRegMark=0;
								boolean isPracticalAlpha=false;
								if (objects[17] != null || objects[18] != null) {
									if(objects[17]!=null){
										if(!StringUtils.isAlpha(objects[17].toString()))
											practicalRegMark=practicalRegMark+Double.parseDouble(objects[17].toString());
										else
											isPracticalAlpha=true;
									}if(objects[18]!=null){
										if(!StringUtils.isAlpha(objects[18].toString()))
											practicalRegMark=practicalRegMark+Double.parseDouble(objects[18].toString());
										else
											isPracticalAlpha=true;
									}
								}else{
									if(objects[13]!=null){
										if(!StringUtils.isAlpha(objects[13].toString()))
											practicalRegMark=practicalRegMark+Double.parseDouble(objects[13].toString());
										else
											isPracticalAlpha=true;
									}
									if(objects[14]!=null){
										if(!StringUtils.isAlpha(objects[14].toString()))
											practicalRegMark=practicalRegMark+Double.parseDouble(objects[14].toString());
										else
											isPracticalAlpha=true;
									}
								}
								if(!isPracticalAlpha)
									markDetailsTO.setStuPracIntMark(String.valueOf(practicalRegMark));
								else
									markDetailsTO.setStuPracIntMark("AA");
								
								if (objects[19] != null) {
									markDetailsTO.setStuTheoryRegMark(objects[19].toString());
								}
								if (objects[20] != null) {
									markDetailsTO.setStuPracRegMark(objects[20].toString());
								}
								if (objects[22] != null) {
									markDetailsTO.setIs_theory_practical(objects[22].toString());
								}
								if(isMaxRecord){
									if(totSubMap.containsKey(markDetailsTO.getSubjectId())){
										StudentMarkDetailsTO markDetailsTO2=totSubMap.remove(markDetailsTO.getSubjectId());
										StudentMarkDetailsTO maxMarks=checkMaxBetweenTOs(markDetailsTO,markDetailsTO2);
										totSubMap.put(markDetailsTO.getSubjectId(),maxMarks);
									}else{
										totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
									}
								}else{
									if(!totSubMap.containsKey(markDetailsTO.getSubjectId())){
										totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
									}
								}
							}
						
						}
						
					}
				}
			}
			List<StudentMarkDetailsTO> totalList = new ArrayList<StudentMarkDetailsTO>(totSubMap.values());
			if(!totalList.isEmpty()){
				Iterator<StudentMarkDetailsTO> totalitr=totalList.iterator();
				while (totalitr.hasNext()) {
					StudentMarkDetailsTO markDetailsTO = totalitr .next();
					double stuTotalTheoryMarks=0;
					double stuTotalPracticalMarks=0;
					double subTotalMarks=40;
					// The Real Logic has to implement Here
					if(markDetailsTO.getIs_theory_practical().equalsIgnoreCase("T")){
						if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
							stuTotalTheoryMarks=stuTotalTheoryMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
						}
						if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
							stuTotalTheoryMarks=stuTotalTheoryMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
						}
						if(stuTotalTheoryMarks>=subTotalMarks){
							count=count+1;
						}
					}
					if(markDetailsTO.getIs_theory_practical().equalsIgnoreCase("P")){
						if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
							stuTotalPracticalMarks=stuTotalPracticalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
						}
						if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
							stuTotalPracticalMarks=stuTotalPracticalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
						}
						if(stuTotalPracticalMarks>=subTotalMarks){
							count=count+1;
						}
					}
					if(markDetailsTO.getIs_theory_practical().equalsIgnoreCase("B")){
						if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
							stuTotalTheoryMarks=stuTotalTheoryMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
						}
						if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
							stuTotalTheoryMarks=stuTotalTheoryMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
						}
						if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
							stuTotalPracticalMarks=stuTotalPracticalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
						}
						if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
							stuTotalPracticalMarks=stuTotalPracticalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
						}
						if(stuTotalPracticalMarks>=subTotalMarks && stuTotalTheoryMarks>=subTotalMarks){
							count=count+1;
						}
					}
				}
			}
		}
		return count;
	}

	/**
	 * @param regNo
	 * @return
	 * @throws Exception
	 */
	public String getCertificateCourseStudentId(String regNo) throws Exception {
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		return iStudentCertificateCourseTxn
				.getCertificateCourseStudentId(regNo);
	}

	public boolean studentCertificateCourseCount(
			StudentCertificateCourseForm courseForm, Integer schemeNo)
			throws Exception {
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();
		StudentCertificateCourse studentCertificateCourse = StudentCertificateCourseHelper
				.getInstance().copyDataFromFormToBO(courseForm);
		studentCertificateCourse.setSchemeNo(schemeNo);
		long count = iStudentCertificateCourseTxn
				.studentCertificateCourseCount(studentCertificateCourse);
		Integer maxIntake = iStudentCertificateCourseTxn
				.getCertificateCourseMaxIntake(studentCertificateCourse);
		if (count < maxIntake) {
			return true;
		}
		return false;
	}

	/**
	 * @param studentId
	 * @param courseForm
	 * @throws Exception
	 */
	public void getCertificateCourseAccourdingToStudentId(String studentId,
			StudentCertificateCourseForm courseForm) throws Exception {
		String dataQuery = StudentCertificateCourseHelper.getInstance()
				.getQueryBystudentId(studentId, courseForm);
		IStudentCertificateCourseTxn transaction = StudentCertificateCourseTxnImpl
				.getInstance();
		Integer SchemeNo = Integer.parseInt(courseForm.getSemester());
		String semType = "ODD";
		List studentData = transaction.getDataForQuery(dataQuery);
		List<CertificateCourseTO> mandatorycourseList = new ArrayList<CertificateCourseTO>();
		List<CertificateCourseTO> optionalCourseList = new ArrayList<CertificateCourseTO>();
		if (studentData != null && !studentData.isEmpty()) {
			int semNo = 0;
			int courseId = 0;
			//int classId = 0;
			int year = 0;
			Iterator itr = studentData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if (obj[0] != null) {
					semNo = Integer.parseInt(obj[0].toString());
				}
				if (obj[1] != null) {
					courseId = Integer.parseInt(obj[1].toString());
				}
				/*if (obj[2] != null) {
					classId = Integer.parseInt(obj[2].toString());
				}*/
				if (obj[3] != null) {
					year = Integer.parseInt(obj[3].toString());
				}
			}
			if (semNo < SchemeNo) {

				if (SchemeNo % 2 == 0) {
					semType = "EVEN";
				}

			} else if (semNo >= SchemeNo) {
				if (semNo % 2 == 0) {
					semType = "EVEN";
				}
			}

			Map<Integer, String> maxIntakeMap = new HashMap<Integer, String>();
			String maxIntakeQuery = "select c.id,count(s.id),c.maxIntake from CertificateCourse c left join c.studentCertificateCourses s with s.isCancelled=0 where c.isActive=1"
					+ " and c.year="
					+ year
					+ " and c.semType='"
					+ semType
					+ "' group by c.id ";
			List maxIntakeList = transaction.getDataForQuery(maxIntakeQuery);
			if (maxIntakeList != null && !maxIntakeList.isEmpty()) {
				Iterator<Object[]> maxItr = maxIntakeList.iterator();
				while (maxItr.hasNext()) {
					Object[] maxIntake = (Object[]) maxItr.next();
					maxIntakeMap.put(Integer.parseInt(maxIntake[0].toString()),
							maxIntake[1].toString() + "/"
									+ maxIntake[2].toString());
				}
			}
			List<Integer> existsList = new ArrayList<Integer>();
			String query = "select a from AssignCertificateCourse a join a.assignCertificateCourseDetails acd where a.isActive=1 and acd.certificateCourse.isActive=1 and a.semType='"
					+ semType
					+ "' and a.academicYear="
					+ year
					+ " and a.course.id=" + courseId + " group by a.id";
			List mandatoryList = transaction.getDataForQuery(query);
			if (mandatoryList != null && !mandatoryList.isEmpty()) {
				Iterator<AssignCertificateCourse> mandatoryitr = mandatoryList
						.iterator();
				while (mandatoryitr.hasNext()) {
					AssignCertificateCourse bo = (AssignCertificateCourse) mandatoryitr
							.next();
					if (bo.getAssignCertificateCourseDetails() != null
							&& !bo.getAssignCertificateCourseDetails()
									.isEmpty()) {
						Iterator<AssignCertificateCourseDetails> acdItr = bo
								.getAssignCertificateCourseDetails().iterator();
						while (acdItr.hasNext()) {
							AssignCertificateCourseDetails acdBo = (AssignCertificateCourseDetails) acdItr
									.next();
							CertificateCourseTO to = new CertificateCourseTO();
							to.setId(acdBo.getCertificateCourse().getId());
							to.setCourseName(acdBo.getCertificateCourse()
									.getCertificateCourseName());
							if (maxIntakeMap.containsKey(acdBo
									.getCertificateCourse().getId())) {
								to.setMaxIntake(maxIntakeMap.get(acdBo
										.getCertificateCourse().getId()));
							}
							existsList.add(to.getId());
							mandatorycourseList.add(to);
						}
					}

				}
			}
			String optionalQuery = " from CertificateCourse c where c.isActive=1 and c.semType='"
					+ semType + "' and c.year=" + year;
			List optionalList = transaction.getDataForQuery(optionalQuery);
			if (optionalList != null && !optionalList.isEmpty()) {
				Iterator<CertificateCourse> mandatoryitr = optionalList
						.iterator();
				while (mandatoryitr.hasNext()) {
					CertificateCourse bo = (CertificateCourse) mandatoryitr
							.next();
					if (!existsList.contains(bo.getId())) {
						CertificateCourseTO to = new CertificateCourseTO();
						to.setId(bo.getId());
						to.setCourseName(bo.getCertificateCourseName());
						to.setMaxIntake(Integer.toString(bo.getMaxIntake()));
						optionalCourseList.add(to);
					}
				}
			}
		}
		Collections.sort(mandatorycourseList);
		Collections.sort(optionalCourseList);
		courseForm.setMandatorycourseList(mandatorycourseList);
		courseForm.setOptionalCourseList(optionalCourseList);
	}

	/**
	 * Checking the Input Register No is valid are Not
	 * 
	 * @param registerNO
	 * @return
	 * @throws Exception
	 */
	public boolean checkRegisterNoIsValid(String registerNO) throws Exception {
		boolean isValid = false;// By default we are keeping it is false
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();// Creating Transaction Object for Interacting
								// with the Database
		String studentId = iStudentCertificateCourseTxn
				.getCertificateCourseStudentId(registerNO);// Cheking the
															// Register No is
															// valid or not by
															// passing register
															// no for
															// transaction
															// method
		if (studentId != null && !studentId.isEmpty()) {
			isValid = true;// Checking the student id is not empty the make is
							// valid is true.
		}
		return isValid;
	}

	/**
	 * Check the student is contain certificate Course for current semester
	 * 
	 * @param courseForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkStudentContainsCertificateCourse(
			StudentCertificateCourseForm courseForm) throws Exception {
		boolean isContain = false;// By default Intializing false
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();// Creating Transaction Object for Interacting
								// with the Database
		String query = StudentCertificateCourseHelper.getInstance()
				.getQueryForStudentContainCertificateCourse(courseForm);// creating
																		// query
																		// for
																		// input
																		// data
		List<StudentCertificateCourse> list = iStudentCertificateCourseTxn
				.getStudentCertificateListForCancallation(query);
		if (list != null && !list.isEmpty()) {// Cheking the list is not null
												// and not empty for making
												// isContain true
			isContain = true;
			List<StudentCertificateCourseTO> toList = StudentCertificateCourseHelper
					.getInstance().convertBoListToTOList(list);// converting Bo
																// list to TO
																// List
			courseForm.setStudentCertificateCourse(toList);
		}
		return isContain;
	}

	/**
	 * @param courseForm
	 * @return
	 */
	public boolean updateCertificateCourse(
			StudentCertificateCourseForm courseForm) throws Exception {
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();// Creating Transaction Object for Interacting
								// with the Database
		return iStudentCertificateCourseTxn.updateCertificateCourse(courseForm
				.getStudentCertificateCourse(), courseForm.getUserId());
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCertificateCourseList(
			StudentCertificateCourseForm courseForm) {
		String semType = courseForm.getSemType();
		if (semType == null)
			semType = "ODD";
		String academicYear = courseForm.getAcademicYear();
		// List<StudentCertificateCourseTO> courseName= new
		// ArrayList<StudentCertificateCourseTO>();

		
		String query = "select id, certificateCourseName from CertificateCourse where isActive=1 and semType='"
			+ semType + "' and year='" + academicYear + "'";

		Map<Integer, String> courseMap = null;

		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();// Creating Transaction Object for Interacting
								// with the Database
		List<Object[]> objectList;
		try {
			objectList = iStudentCertificateCourseTxn.getCourseName(query);
			
			if (objectList != null && !objectList.isEmpty()) {
				courseMap = StudentCertificateCourseHelper.getInstance()
						.convertBoListToTOListCourseName(objectList);
			}
			else
			{
				courseMap = StudentCertificateCourseHelper.getInstance()
				.convertListCourseName(objectList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		courseMap = (HashMap<Integer, String>) CommonUtil
				.sortMapByValue(courseMap);
		
		return courseMap;
	}

	public void getCertificateCodeName(String selectedCourse,
			StudentCertificateCourseForm courseForm) {

		// String
		// query="select s.code, s.name from Subject s where s.isCertificateCourse= 1 and s.certificateCourse.id='"+selectedCourse+"'";
		String query = "select c.subject.code,c.subject.name from CertificateCourse c where c.id="
				+ selectedCourse;
		// Map<String, String> CourseCodeName=null;

		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();// Creating Transaction Object for Interacting
								// with the Database
		List<Object[]> objectList;
		try {
			objectList = iStudentCertificateCourseTxn.getCourseCodeName(query);
			if (objectList != null && !objectList.isEmpty()) {
				StudentCertificateCourseHelper.getInstance()
						.convertListCourseCodeName(objectList, courseForm);// converting
																			// Bo
																			// list
																			// to
																			// TO
																			// List

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<StudentCertificateCourseTO> getCertificateCourseStudentList(
			String SelectedCourse) throws Exception {
		String dataQuery = StudentCertificateCourseHelper.getInstance()
				.getQueryByselectedCertificateCourse(SelectedCourse);
		IStudentCertificateCourseTxn transaction = StudentCertificateCourseTxnImpl
				.getInstance();
		List studentData = transaction.getCourseData(dataQuery);
		List<StudentCertificateCourseTO> toList = new ArrayList<StudentCertificateCourseTO>();
		if (studentData != null && !studentData.isEmpty()) {

			Iterator itr = studentData.iterator();
			while (itr.hasNext()) {
				Object obj = (Object) itr.next();
				StudentCertificateCourseTO to = new StudentCertificateCourseTO();

				if (obj != null) {
					to.setSelectedCourseId(obj.toString());
				}
				toList.add(to);

			}
		}
		return toList;
	}

	public List<StudentCertificateCourseTO> getAdmApplnIdList(
			String SelectedCourse) throws Exception {
		String dataQuery = StudentCertificateCourseHelper.getInstance()
				.getAdmIdSelCetifCourse(SelectedCourse);
		IStudentCertificateCourseTxn transaction = StudentCertificateCourseTxnImpl
				.getInstance();
		List studentData = transaction.getCourseData(dataQuery);
		List<StudentCertificateCourseTO> toList = new ArrayList<StudentCertificateCourseTO>();
		if (studentData != null && !studentData.isEmpty()) {

			Iterator itr = studentData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				StudentCertificateCourseTO to = new StudentCertificateCourseTO();
				if (obj[0] != null) {

					to.setSelectedCourseId(obj[0].toString());
				}
				if (obj[1] != null) {

					to.setAdmApplnId(obj[1].toString());
				}

				toList.add(to);

			}
		}
		return toList;
	}

	public List<StudentCertificateCourseTO> getCurriculmdIdList(
			String SelectedCourse) throws Exception {
		String dataQuery = StudentCertificateCourseHelper.getInstance()
				.getCurriculumIdByselCetifCourse(SelectedCourse);
		IStudentCertificateCourseTxn transaction = StudentCertificateCourseTxnImpl
				.getInstance();
		List studentData = transaction.getCourseData(dataQuery);
		List<StudentCertificateCourseTO> toList = new ArrayList<StudentCertificateCourseTO>();
		if (studentData != null && !studentData.isEmpty()) {

			Iterator itr = studentData.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				StudentCertificateCourseTO to = new StudentCertificateCourseTO();
				if (obj[0] != null) {
					to.setSelectedCourseId(obj[0].toString());
				}
				if (obj[1] != null) {

					to.setCurriculumSchemeDurationId(obj[1].toString());
				}
				if (obj[2] != null) {
					to.setSemester(obj[2].toString());
				}

				toList.add(to);

			}
		}
		return toList;
	}

	public boolean insertSubGrpToSubjectGroupTbl(
			List<StudentCertificateCourseTO> list,
			StudentCertificateCourseForm courseForm, String userId) {
		boolean result = false;
		String SelectedCourseId = null;
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl .getInstance();// Creating Transaction Object for Interacting with the Database
		List DataExist;
		try {
			if (list != null && !list.isEmpty()) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					StudentCertificateCourseTO bo = (StudentCertificateCourseTO) itr
							.next();
					SelectedCourseId = bo.getSelectedCourseId();
					String SubjectGroupName = courseForm.getSubjectGroupName();
					String query = "select s.name from SubjectGroup s where s.isCommonSubGrp = 0 and s.isActive = 1 and s.course.id='"
							+ SelectedCourseId
							+ "' and s.name='"
							+ SubjectGroupName + "'";
					DataExist = iStudentCertificateCourseTxn
							.getSubjectGroupNameExist(query);
					if (DataExist == null || DataExist.isEmpty()) {
						// Inserting value into subject Group table
						SubjectGroup sb = new SubjectGroup();
						Course c = new Course();
						c.setId(Integer.parseInt(SelectedCourseId));
						sb.setName(SubjectGroupName);
						sb.setCreatedBy(userId);
						sb.setModifiedBy(userId);
						sb.setCourse(c);
						sb.setCreatedDate(new Date());
						sb.setIsActive(true);
						sb.setIsCommonSubGrp(false);
						iStudentCertificateCourseTxn.savesubjectGroupName(sb);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @param list
	 * @param courseForm
	 * @param userId
	 */
	public void insertCurriculumId(List<StudentCertificateCourseTO> list,
			StudentCertificateCourseForm courseForm, String userId) {
		String SubGrpName = null;
		String CourseId = null;
		String academicYear = null;
		String CurriculumSchDurationId = null;
		String Semester = null;
		//Date Today = new Date();
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();// Creating Transaction Object for Interacting
								// with the Database
		List DataExist;
		List DuplicatData;
		int Flag = 1;
		try {
			if (list != null && !list.isEmpty()) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					StudentCertificateCourseTO bo = (StudentCertificateCourseTO) itr
							.next();
					SubGrpName = courseForm.getSubjectGroupName();
					academicYear = courseForm.getAcademicYear();
					// AdmApplnId=bo.getAdmApplnId();
					CourseId = bo.getSelectedCourseId();
					Semester = bo.getSemester();
					CurriculumSchDurationId = bo
							.getCurriculumSchemeDurationId();
					String Query1 = "select css.subjectGroup.name from CurriculumSchemeSubject css where css.curriculumSchemeDuration.semesterYearNo='"
							+ Semester
							+ "' and css.subjectGroup.name='"
							+ SubGrpName
							+ "' and css.curriculumSchemeDuration.academicYear='"
							+ academicYear
							+ "' and css.curriculumSchemeDuration.id='"
							+ CurriculumSchDurationId + "'";
					DuplicatData = iStudentCertificateCourseTxn
							.getDuplicateInCurriculum(Query1);
					/*String SubjectGroupName = courseForm.getSubjectGroupName();
					String DupSubGrpName = null;*/
					if (DuplicatData == null || DuplicatData.isEmpty()) {
					/*	Iterator<Object> Itr1 = DuplicatData.iterator();
						while (Itr1.hasNext()) {
							Object subName = (Object) Itr1.next();
							DupSubGrpName = subName.toString();
							if (!subName.equals(SubjectGroupName))
								Flag = 1;
							else
								Flag = 0;
						}
					}
					if (Flag == 1) {*/
						String query = "select s.id from SubjectGroup s where s.isCommonSubGrp = 0 and s.isActive = 1 and s.name='"
								+ SubGrpName
								+ "' and s.course.id='"
								+ CourseId
								+ "'";
						DataExist = iStudentCertificateCourseTxn
								.getSubjectGroupNameExist(query);
						// String
						// SubjectGroupName=courseForm.getSubjectGroupName();
						String SubID = null;
						if (DataExist != null && !DataExist.isEmpty()) {
							Iterator<Object> Itr = DataExist.iterator();
							while (Itr.hasNext()) {
								Object subid = (Object) Itr.next();
								SubID = subid.toString();

								// Inserting value into Curriculum_Subject_group
								// table

								CurriculumSchemeSubject css = new CurriculumSchemeSubject();

								CurriculumSchemeDuration csd = new CurriculumSchemeDuration();
								csd.setId(Integer
										.parseInt(CurriculumSchDurationId));
								SubjectGroup sb = new SubjectGroup();
								sb.setId(Integer.parseInt(SubID));
								css.setSubjectGroup(sb);
								css.setCreatedBy(userId);
								css.setCreatedDate(new Date());
								css.setModifiedBy(userId);
								css.setLastModifiedDate(new Date());
								css.setCurriculumSchemeDuration(csd);

								iStudentCertificateCourseTxn
										.curriculumSchemeSubject(css);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertAdmApplnId(List<StudentCertificateCourseTO> list,
			StudentCertificateCourseForm courseForm, String userId) {
		String SubGrpName = null;
		String AdmApplnId = null;
		String CourseId = null;
		List DuplicatData = null;
		//Date Today = new Date();
		IStudentCertificateCourseTxn iStudentCertificateCourseTxn = StudentCertificateCourseTxnImpl
				.getInstance();// Creating Transaction Object for Interacting
								// with the Database
		List DataExist;
		int Flag = 1;
		try {
			if (list != null && !list.isEmpty()) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					StudentCertificateCourseTO bo = (StudentCertificateCourseTO) itr
							.next();
					SubGrpName = courseForm.getSubjectGroupName();
					AdmApplnId = bo.getAdmApplnId();
					CourseId = bo.getSelectedCourseId();

					String Query1 = "select asg.subjectGroup.name from ApplicantSubjectGroup asg where asg.admAppln.courseBySelectedCourseId='"
							+ CourseId
							+ "' and asg.subjectGroup.name='"
							+ SubGrpName
							+ "' and asg.admAppln.id='"
							+ AdmApplnId + "'";
					DuplicatData = iStudentCertificateCourseTxn
							.getDuplicateInApplicantSubGrp(Query1);

					String DupSubGrpName = null;
					if (DuplicatData == null || DuplicatData.isEmpty()) {
						// Iterator<Object> Itr1=DuplicatData.iterator();
						// while (Itr1.hasNext()){
						// Object subName = (Object) Itr1.next();
						// DupSubGrpName = subName.toString();
						// if(!subName.equals(SubGrpName))
						// {
						String query = "select s.id from SubjectGroup s where s.isCommonSubGrp = 0 and s.isActive = 1 and s.name='"
								+ SubGrpName
								+ "' and s.course.id='"
								+ CourseId
								+ "'";
						DataExist = iStudentCertificateCourseTxn
								.getSubjectGroupNameExist(query);
						String SubID = null;
						if (DataExist != null && !DataExist.isEmpty()) {
							Iterator<Object> Itr = DataExist.iterator();
							while (Itr.hasNext()) {
								Object subid = (Object) Itr.next();
								SubID = subid.toString();
								// Inserting value into applicant_Subject_group
								// table

								ApplicantSubjectGroup asg = new ApplicantSubjectGroup();
								SubjectGroup sb1 = new SubjectGroup();
								sb1.setId(Integer.parseInt(SubID));
								AdmAppln a = new AdmAppln();
								a.setId(Integer.parseInt(AdmApplnId));

								asg.setAdmAppln(a);
								asg.setSubjectGroup(sb1);
								asg.setCreatedBy(userId);
								asg.setCreatedDate(new Date());
								asg.setModifiedBy(userId);
								asg.setLastModifiedDate(new Date());
								iStudentCertificateCourseTxn
										.applicantSubjectGroup(asg);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param list
	 * @param courseForm
	 * @param userId
	 * @throws Exception
	 */
	public void getSubjectGroupId(List<StudentCertificateCourseTO> list,
			StudentCertificateCourseForm courseForm, String userId)
			throws Exception {
		int subjectId = 0;
		String SubName = null;
		if (list != null && !list.isEmpty()) {
			Iterator itr2 = list.iterator();
			while (itr2.hasNext()) {
				StudentCertificateCourseTO bo = (StudentCertificateCourseTO) itr2
						.next();
				String SubjectCode = courseForm.getSubjectCode();
				String subQuery = "select s.id, s.name from Subject s where s.code='" + SubjectCode + "'";
				IStudentCertificateCourseTxn Subtransaction = StudentCertificateCourseTxnImpl
						.getInstance();
				List SubList = Subtransaction.getSubject(subQuery);
				if (SubList != null && !SubList.isEmpty()) {
					Iterator itr = SubList.iterator();
					while (itr.hasNext()) {
						Object[] obj = (Object[]) itr.next();
						Subject sub = new Subject();
						if (obj[0] != null) {
							sub.setId(Integer.parseInt(obj[0].toString()));
							subjectId = sub.getId();
							sub.setName(obj[1].toString());
							SubName = sub.getName();
							String SubjectGroupName = courseForm
									.getSubjectGroupName();

							String dataQuery = "select sg.id from SubjectGroup sg where sg.name='"
									+ SubjectGroupName + "'";
							IStudentCertificateCourseTxn transaction = StudentCertificateCourseTxnImpl
									.getInstance();
							List SubGrpList = transaction.getSubject(dataQuery);
							if (SubGrpList != null && !SubGrpList.isEmpty()) {
								Iterator itr1 = SubGrpList.iterator();
								while (itr1.hasNext()) {
									Object obj1 = (Object) itr1.next();
									SubjectGroup SubGrpto = new SubjectGroup();
									if (obj1 != null) {
										SubGrpto.setId(Integer.parseInt(obj1
												.toString()));
										String subjectGpId = (String
												.valueOf(SubGrpto.getId()));

										// inserting value into subject group
										// subject table

										// Mary modification

										/* check before making changes..... */

										// String
										// Query1="select sgs.subjectGroup.id from SubjectGroupSubjects  sgs where sgs.subjectGroup.name= '"+SubjectGroupName+"' and sgs.subjectGroup.course.id= '"+CourseId+"'";
										String Query1 = "select sgs.subjectGroup.id  from SubjectGroupSubjects  sgs where sgs.subjectGroup.id='"
												+ subjectGpId
												+ "'  and sgs.subject.id='"
												+ subjectId + "'";
										List DuplicatData = Subtransaction
												.getDuplicateSubGrpInSGS(Query1);
										// String DupSubGrpName=null;
										if (DuplicatData == null
												|| DuplicatData.isEmpty()) {
											SubGrpto.setId(Integer
													.parseInt(obj1.toString()));
											SubjectGroupSubjects sbs = new SubjectGroupSubjects();
											sbs.setSubjectGroup(SubGrpto);
											sbs.setSubject(sub);
											sbs.setCreatedBy(userId);
											sbs.setCreatedDate(new Date());
											sbs.setIsActive(true);
											transaction.savesubgrpSub(sbs);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
public StudentMarkDetailsTO checkMaxBetweenTOs(StudentMarkDetailsTO to1,StudentMarkDetailsTO to2) throws Exception {
		
		StudentMarkDetailsTO  markDetailsTO =new StudentMarkDetailsTO();
		// The Real Logic has to implement Here
		if(to1!=null && to2!=null){
			markDetailsTO.setSubjectId(to1.getSubjectId());
			markDetailsTO.setIs_theory_practical(to1.getIs_theory_practical());
			// Theory Int Marks
			if(to1.getStuTheoryIntMark()!=null && !to1.getStuTheoryIntMark().isEmpty()){
				if(StringUtils.isAlpha(to1.getStuTheoryIntMark().trim())){
					if(to2.getStuTheoryIntMark()!=null && !to2.getStuTheoryIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryIntMark().trim())){
						markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
					}else{
						markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
					}
				}else{
					if(to2.getStuTheoryIntMark()!=null && !to2.getStuTheoryIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryIntMark().trim())){
						if(Double.parseDouble(to1.getStuTheoryIntMark()) < Double.parseDouble(to2.getStuTheoryIntMark()))
							markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
						else
							markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
					}else{
						markDetailsTO.setStuTheoryIntMark(to1.getStuTheoryIntMark());
					}
				}
			}else{
				if(to2.getStuTheoryIntMark()!=null && !to2.getStuTheoryIntMark().isEmpty()){
					markDetailsTO.setStuTheoryIntMark(to2.getStuTheoryIntMark());
				}
			}
			
			// Practical Int Mark
			if(to1.getStuPracIntMark()!=null && !to1.getStuPracIntMark().isEmpty()){
				if(StringUtils.isAlpha(to1.getStuPracIntMark().trim())){
					if(to2.getStuPracIntMark()!=null && !to2.getStuPracIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracIntMark().trim())){
						markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
					}else{
						markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
					}
				}else{
					if(to2.getStuPracIntMark()!=null && !to2.getStuPracIntMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracIntMark().trim())){
						if(Double.parseDouble(to1.getStuPracIntMark()) < Double.parseDouble(to2.getStuPracIntMark()))
							markDetailsTO.setStuTheoryIntMark(to2.getStuPracIntMark());
						else
							markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
					}else{
						markDetailsTO.setStuPracIntMark(to1.getStuPracIntMark());
					}
				}
			}else{
				if(to2.getStuPracIntMark()!=null && !to2.getStuPracIntMark().isEmpty()){
					markDetailsTO.setStuPracIntMark(to2.getStuPracIntMark());
				}
			}
			
			// Theory Reg Mark
			if(to1.getStuTheoryRegMark()!=null && !to1.getStuTheoryRegMark().isEmpty()){
				if(StringUtils.isAlpha(to1.getStuTheoryRegMark().trim())){
					if(to2.getStuTheoryRegMark()!=null && !to2.getStuTheoryRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryRegMark().trim())){
						markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
					}else{
						markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
					}
				}else{
					if(to2.getStuTheoryRegMark()!=null && !to2.getStuTheoryRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuTheoryRegMark().trim())){
						if(Double.parseDouble(to1.getStuTheoryRegMark()) < Double.parseDouble(to2.getStuTheoryRegMark()))
							markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
						else
							markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
					}else{
						markDetailsTO.setStuTheoryRegMark(to1.getStuTheoryRegMark());
					}
				}
			}else{
				if(to2.getStuTheoryRegMark()!=null && !to2.getStuTheoryRegMark().isEmpty()){
					markDetailsTO.setStuTheoryRegMark(to2.getStuTheoryRegMark());
				}
			}
			
			
			// practical Reg Mark
			if(to1.getStuPracRegMark()!=null && !to1.getStuPracRegMark().isEmpty()){
				if(StringUtils.isAlpha(to1.getStuPracRegMark().trim())){
					if(to2.getStuPracRegMark()!=null && !to2.getStuPracRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracRegMark().trim())){
						markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
					}else{
						markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
					}
				}else{
					if(to2.getStuPracRegMark()!=null && !to2.getStuPracRegMark().isEmpty() && !StringUtils.isAlpha(to2.getStuPracRegMark().trim())){
						if(Double.parseDouble(to1.getStuPracRegMark()) < Double.parseDouble(to2.getStuPracRegMark()))
							markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
						else
							markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
					}else{
						markDetailsTO.setStuPracRegMark(to1.getStuPracRegMark());
					}
				}
			}else{
				if(to2.getStuPracRegMark()!=null && !to2.getStuPracRegMark().isEmpty()){
					markDetailsTO.setStuPracRegMark(to2.getStuPracRegMark());
				}
			}
		}
		return markDetailsTO;
	}
/**
 * 
 * @param studentId
 * @param request 
 * @param termNo 
 * @param admAppln
 * @return
 * @throws Exception
 */
public List<StudentMarkDetailsTO> getCompletedCourseCount111(int studentId, int courseId, HttpServletRequest request, int termNo)
		throws Exception {
	List<StudentMarkDetailsTO> totalList=null;
	String query="select s.schemeNo,s.subject.id,s.subject.isTheoryPractical,s.isOptional from StudentCertificateCourse s where s.isCancelled=0 and s.student.id="+studentId;
	INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
	IDownloadHallTicketTransaction transaction1= new DownloadHallTicketTransactionImpl();
	List<Object[]> studentCertificate=transaction.getDataForQuery(query);
	int count=0;
	boolean flag=false;
	boolean isMaxRecord=false;
	if(courseId!=18){
		isMaxRecord=true;
	}
	if(studentCertificate!=null && !studentCertificate.isEmpty()){
		Iterator<Object[]> itr=studentCertificate.iterator();
		
		Map<Integer,StudentMarkDetailsTO> totSubMap=new HashMap<Integer, StudentMarkDetailsTO>();//subject and marks details to verify max or latest and keep in the map at last
		while (itr.hasNext()) {
			Object[] obj=itr.next();
			if(obj[0]!=null && obj[1]!=null && obj[2]!=null){
				int schemeNo=Integer.parseInt(obj[0].toString());
				int subjectId=Integer.parseInt(obj[1].toString());
				String isTheoryPractical=obj[2].toString();
				String marksQuery=" select EXAM_internal_mark_supplementary_details.id, classes.name as className, classes.id as classId, student.register_no, student.id StudentID, EXAM_definition.id ExamDefId, " +
				" EXAM_definition.year, EXAM_definition.month, EXAM_definition.name as examName, subject.id SubjectID, subject.name as subName, " +
				" EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.theory_total_attendance_mark, " +
				" EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.practical_total_attendance_mark, " +
				" EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark as intTheorymark, EXAM_internal_mark_supplementary_details.theory_total_attendance_mark as intTheoryAttMark, " +
				" EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark  as intPracticalMark, EXAM_internal_mark_supplementary_details.practical_total_attendance_mark as intPracticalAttMark, " +
				" EXAM_student_final_mark_details.student_theory_marks as finalStudentTheoryMarks,  " +
				" EXAM_student_final_mark_details.student_practical_marks as finalStudentPracticalMarks, " +
				" subject_type.name as subTypeName, subject.is_theory_practical " +
				" from EXAM_student_overall_internal_mark_details " +
				" inner join student ON EXAM_student_overall_internal_mark_details.student_id = student.id " +
				" inner join classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id " +
				" inner join subject ON EXAM_student_overall_internal_mark_details.subject_id = subject.id " +
				" inner join EXAM_publish_exam_results  on EXAM_student_overall_internal_mark_details.class_id=EXAM_publish_exam_results.class_id"+
                " and EXAM_publish_exam_results.exam_id = EXAM_student_overall_internal_mark_details.exam_id"+
				" inner join subject_type ON subject.subject_type_id = subject_type.id " +
				" left join EXAM_student_final_mark_details on EXAM_student_final_mark_details.student_id = student.id " +
				" and EXAM_student_final_mark_details.class_id = classes.id  " +
				" and EXAM_student_final_mark_details.subject_id = subject.id " +
				" and ((EXAM_student_final_mark_details.student_theory_marks is not null) or (EXAM_student_final_mark_details.student_practical_marks is not null)) " +
				" left join EXAM_internal_mark_supplementary_details on EXAM_internal_mark_supplementary_details.class_id = classes.id " +
				" and EXAM_internal_mark_supplementary_details.student_id = student.id " +
				" and EXAM_internal_mark_supplementary_details.subject_id = subject.id " +
				" left join EXAM_definition ON EXAM_definition.id = if(EXAM_student_final_mark_details.exam_id is null, " +
				" (if(EXAM_internal_mark_supplementary_details.exam_id is null, EXAM_student_overall_internal_mark_details.exam_id, EXAM_internal_mark_supplementary_details.exam_id)),  " +
				"                                                     EXAM_student_final_mark_details.exam_id) " +
				" where student.id = " +studentId+
				" and classes.term_number ="+schemeNo+" and subject.id in ("+subjectId+")" +
				"group by student.id,subject.id,classes.id,ExamDefId order by student.id, subject.id, EXAM_definition.year DESC, EXAM_definition.month DESC  ";
				List<Object[]> marksList=transaction1.getStudentHallTicket(marksQuery);
				if(marksList!=null && !marksList.isEmpty()){
					Iterator<Object[]> markItr=marksList.iterator();
					while (markItr.hasNext()) {
						Object[] objects = (Object[]) markItr.next();
						if(objects[9]!=null){
							StudentMarkDetailsTO markDetailsTO= new StudentMarkDetailsTO();
							//added by giri
							if(objects[10]!=null)
								markDetailsTO.setSubjectName(objects[10].toString());
							if(obj[3].toString().equalsIgnoreCase("true")){
								markDetailsTO.setMandOrOptional("Optional");
							}else{
								markDetailsTO.setMandOrOptional("Mandatory");
							}
							if(obj[0]!=null){
								if(Integer.parseInt(obj[0].toString())==termNo){
									flag=true;
								}
							}
							markDetailsTO.setSemester(obj[0].toString());
							//end by giri
							if(objects[4]!=null)
								markDetailsTO.setStudentId(Integer.parseInt(objects[4].toString()));
							if(objects[9]!=null)
								markDetailsTO.setSubjectId(Integer.parseInt(objects[9].toString()));
							double theoryRegMark=0;
							boolean isTheoryAlpha=false;
							if (objects[15] != null || objects[16] != null) {
								if(objects[15]!=null){
									if(!StringUtils.isAlpha(objects[15].toString()))
										theoryRegMark=theoryRegMark+Double.parseDouble(objects[15].toString());
									else
										isTheoryAlpha=true;
								}
								if(objects[16]!=null){
									if(!StringUtils.isAlpha(objects[16].toString()))
										theoryRegMark=theoryRegMark+Double.parseDouble(objects[16].toString());
									else
										isTheoryAlpha=true;
								}
							}else{
								if(objects[11]!=null){
									if(!StringUtils.isAlpha(objects[11].toString()))
										theoryRegMark=theoryRegMark+Double.parseDouble(objects[11].toString());
									else
										isTheoryAlpha=true;
								}
								if(objects[12]!=null){
									if(!StringUtils.isAlpha(objects[12].toString()))
										theoryRegMark=theoryRegMark+Double.parseDouble(objects[12].toString());
									else
										isTheoryAlpha=true;
								}
							}
							if(!isTheoryAlpha)
								markDetailsTO.setStuTheoryIntMark(String.valueOf(theoryRegMark));
							else
								markDetailsTO.setStuTheoryIntMark("AA");
							double practicalRegMark=0;
							boolean isPracticalAlpha=false;
							if (objects[17] != null || objects[18] != null) {
								if(objects[17]!=null){
									if(!StringUtils.isAlpha(objects[17].toString()))
										practicalRegMark=practicalRegMark+Double.parseDouble(objects[17].toString());
									else
										isPracticalAlpha=true;
								}if(objects[18]!=null){
									if(!StringUtils.isAlpha(objects[18].toString()))
										practicalRegMark=practicalRegMark+Double.parseDouble(objects[18].toString());
									else
										isPracticalAlpha=true;
								}
							}else{
								if(objects[13]!=null){
									if(!StringUtils.isAlpha(objects[13].toString()))
										practicalRegMark=practicalRegMark+Double.parseDouble(objects[13].toString());
									else
										isPracticalAlpha=true;
								}
								if(objects[14]!=null){
									if(!StringUtils.isAlpha(objects[14].toString()))
										practicalRegMark=practicalRegMark+Double.parseDouble(objects[14].toString());
									else
										isPracticalAlpha=true;
								}
							}
							if(!isPracticalAlpha)
								markDetailsTO.setStuPracIntMark(String.valueOf(practicalRegMark));
							else
								markDetailsTO.setStuPracIntMark("AA");
							
							if (objects[19] != null) {
								markDetailsTO.setStuTheoryRegMark(objects[19].toString());
							}
							if (objects[20] != null) {
								markDetailsTO.setStuPracRegMark(objects[20].toString());
							}
							if (objects[22] != null) {
								markDetailsTO.setIs_theory_practical(objects[22].toString());
							}
							if(isMaxRecord){
								if(totSubMap.containsKey(markDetailsTO.getSubjectId())){
									StudentMarkDetailsTO markDetailsTO2=totSubMap.remove(markDetailsTO.getSubjectId());
									StudentMarkDetailsTO maxMarks=checkMaxBetweenTOs(markDetailsTO,markDetailsTO2);
									totSubMap.put(markDetailsTO.getSubjectId(),maxMarks);
								}else{
									totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
								}
							}else{
								if(!totSubMap.containsKey(markDetailsTO.getSubjectId())){
									totSubMap.put(markDetailsTO.getSubjectId(),markDetailsTO);
								}
							}
						}
					
					}
					
				}
			}
		}
		totalList = new ArrayList<StudentMarkDetailsTO>(totSubMap.values());
		if(!totalList.isEmpty()){
			Iterator<StudentMarkDetailsTO> totalitr=totalList.iterator();
			while (totalitr.hasNext()) {
				StudentMarkDetailsTO markDetailsTO = totalitr .next();
				double stuTotalTheoryMarks=0;
				double stuTotalPracticalMarks=0;
				double subTotalMarks=40;
				// The Real Logic has to implement Here
				if(markDetailsTO.getIs_theory_practical().equalsIgnoreCase("T")){
					if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
						stuTotalTheoryMarks=stuTotalTheoryMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
					}
					if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
						stuTotalTheoryMarks=stuTotalTheoryMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
					}
					if(stuTotalTheoryMarks>=subTotalMarks){
						markDetailsTO.setStatus("Pass");
						if(markDetailsTO.getMandOrOptional().equalsIgnoreCase("Mandatory")){
							count=count+1;
						}
					}else{
						markDetailsTO.setStatus("Fail");
					}
				}
				if(markDetailsTO.getIs_theory_practical().equalsIgnoreCase("P")){
					if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
						stuTotalPracticalMarks=stuTotalPracticalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
					}
					if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
						stuTotalPracticalMarks=stuTotalPracticalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
					}
					if(stuTotalPracticalMarks>=subTotalMarks){
						markDetailsTO.setStatus("Pass");
						if(markDetailsTO.getMandOrOptional().equalsIgnoreCase("Mandatory")){
							count=count+1;
						}
					}else{
						markDetailsTO.setStatus("Fail");
					}
				}
				if(markDetailsTO.getIs_theory_practical().equalsIgnoreCase("B")){
					if(markDetailsTO.getStuTheoryRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryRegMark().trim())){
						stuTotalTheoryMarks=stuTotalTheoryMarks+Double.parseDouble(markDetailsTO.getStuTheoryRegMark());
					}
					if(markDetailsTO.getStuTheoryIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuTheoryIntMark().trim())){
						stuTotalTheoryMarks=stuTotalTheoryMarks+Double.parseDouble(markDetailsTO.getStuTheoryIntMark());
					}
					if(markDetailsTO.getStuPracRegMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracRegMark().trim())){
						stuTotalPracticalMarks=stuTotalPracticalMarks+Double.parseDouble(markDetailsTO.getStuPracRegMark());
					}
					if(markDetailsTO.getStuPracIntMark()!=null && !StringUtils.isAlpha(markDetailsTO.getStuPracIntMark().trim())){
						stuTotalPracticalMarks=stuTotalPracticalMarks+Double.parseDouble(markDetailsTO.getStuPracIntMark());
					}
					if(stuTotalPracticalMarks>=subTotalMarks && stuTotalTheoryMarks>=subTotalMarks){
						markDetailsTO.setStatus("Pass");
						if(markDetailsTO.getMandOrOptional().equalsIgnoreCase("Mandatory")){
							count=count+1;
						}
					}else{
						markDetailsTO.setStatus("Fail");
					}
				}
			}
		}
	}
	//start by giri if current term no not in the totalList
	if(!flag){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("from StudentCertificateCourse s where s.isCancelled=0 and s.student.id="+studentId+"s.schemeNo="+termNo);
		StudentCertificateCourse studentCertificateCourse=transaction1.getStudentCertificateCourseOnGoing(studentId,termNo);
		if(studentCertificateCourse!=null){
			StudentMarkDetailsTO markDetailsTO=new StudentMarkDetailsTO();
			if(studentCertificateCourse.getSubject().getName()!=null){
				markDetailsTO.setSubjectName(studentCertificateCourse.getSubject().getName());
			}
			if(studentCertificateCourse.getIsOptional()){
				markDetailsTO.setMandOrOptional("Optional");
			}else{
				markDetailsTO.setMandOrOptional("Mandatory");
			}
			markDetailsTO.setSemester(String.valueOf(termNo));
			markDetailsTO.setStatus("Ongoing");
			totalList.add(markDetailsTO);
		}
	}
	//end by giri
	request.setAttribute("NoOfCompletedCourses", count);
	return totalList;
}
}
