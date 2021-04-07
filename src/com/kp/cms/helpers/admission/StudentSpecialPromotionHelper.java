package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.StudentSpecialPromotionForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.StudentRollNoComparator;

public class StudentSpecialPromotionHelper {
	/**
	 * Singleton object of StudentSpecialPromotionHelper
	 */
	private static volatile StudentSpecialPromotionHelper studentSpecialPromotionHelper = null;
	private static final Log log = LogFactory.getLog(StudentSpecialPromotionHelper.class);
	private StudentSpecialPromotionHelper() {
		
	}
	/**
	 * return singleton object of studentSpecialPromotionHelper.
	 * @return
	 */
	public static StudentSpecialPromotionHelper getInstance() {
		if (studentSpecialPromotionHelper == null) {
			studentSpecialPromotionHelper = new StudentSpecialPromotionHelper();
		}
		return studentSpecialPromotionHelper;
	}
	/**
	 * @param studentSpecialPromotionForm
	 * @return
	 * @throws Exception
	 */
	public String getQueryForInput(StudentSpecialPromotionForm studentSpecialPromotionForm) throws Exception {
		String query="from Student s where  s.admAppln.isCancelled=0 and (s.isHide = 0 or s.isHide is null) " +
		" and s.isActive=1 and s.tcNo is null and s.classSchemewise.id="+studentSpecialPromotionForm.getClassId();
		if(studentSpecialPromotionForm.getRegisterNo()!=null && !studentSpecialPromotionForm.getRegisterNo().isEmpty()){
			query=query+" and s.registerNo='"+studentSpecialPromotionForm.getRegisterNo()+"'";
		}
		return query+" order by s.admAppln.personalData.firstName";
	}
	/**
	 * @param studentList
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBotoTo(List<Student> studentList) throws Exception {
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		List<StudentTO> list=new ArrayList<StudentTO>();
		if(studentList!=null && !studentList.isEmpty()){
			Iterator<Student> itr=studentList.iterator();
			while (itr.hasNext()) {
				Student bo = (Student) itr.next();
				if(listOfDetainedStudents!=null && !listOfDetainedStudents.isEmpty() && !listOfDetainedStudents.contains(bo.getId())){
					StudentTO to=new StudentTO();
					to.setId(bo.getId());
					to.setRegisterNo(bo.getRegisterNo());
					to.setStudentName(bo.getAdmAppln().getPersonalData().getFirstName());
					to.setCourseId(bo.getAdmAppln().getCourseBySelectedCourseId().getId());
					list.add(to);
				}
				else{
					if(listOfDetainedStudents==null || listOfDetainedStudents.isEmpty()){
						StudentTO to=new StudentTO();
						to.setId(bo.getId());
						to.setRegisterNo(bo.getRegisterNo());
						to.setStudentName(bo.getAdmAppln().getPersonalData().getFirstName());
						to.setCourseId(bo.getAdmAppln().getCourseBySelectedCourseId().getId());
						list.add(to);
					}
				}


			}
		}
		StudentRollNoComparator sRollNo = new StudentRollNoComparator();
		sRollNo.setRegNoCheck(true);
		Collections.sort(list, sRollNo);
		return list;
	}
}
