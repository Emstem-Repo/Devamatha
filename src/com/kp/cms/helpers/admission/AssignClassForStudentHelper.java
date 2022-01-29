package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.AssignClassForStudentForm;
import com.kp.cms.to.admission.AssingClassForStudentTO;

public class AssignClassForStudentHelper {
	/**
	 * Singleton object of AssignCertificateCourseHelper
	 */
	private static volatile AssignClassForStudentHelper assignCertificateCourseHelper = null;
	private static final Log log = LogFactory.getLog(AssignClassForStudentHelper.class);
	private AssignClassForStudentHelper() {
		
	}
	/**
	 * return singleton object of AssignCertificateCourseHelper.
	 * @return
	 */
	public static AssignClassForStudentHelper getInstance() {
		if (assignCertificateCourseHelper == null) {
			assignCertificateCourseHelper = new AssignClassForStudentHelper();
		}
		return assignCertificateCourseHelper;
	}
	/**
	 * @param stuList
	 * @return
	 * @throws Exception
	 */
	public List<AssingClassForStudentTO> convertBOtoTO(List<Student> stuList) throws Exception{
		List<AssingClassForStudentTO> list = new ArrayList<AssingClassForStudentTO>();
		if(stuList != null ){
			Iterator<Student> iterator = stuList.iterator();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				AssingClassForStudentTO to = new AssingClassForStudentTO();
				if(student.getId() != 0){
					to.setStudentId(String.valueOf(student.getId()));
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFirstName() != null){
					to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getSecondLanguage() != null){
					to.setSecondLanguage(student.getAdmAppln().getPersonalData().getSecondLanguage());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					to.setGender(student.getAdmAppln().getPersonalData().getGender());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getTotalWeightage() != null){
					to.setPercentage(String.valueOf(student.getAdmAppln().getTotalWeightage().doubleValue()));
				}else{
					to.setPercentage("0.0");
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					to.setRegNo(student.getRegisterNo());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getApplnNo() != 0 ){
					to.setApplNo(String.valueOf(student.getAdmAppln().getApplnNo()));
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
					to.setClassName(student.getClassSchemewise().getClasses().getName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourse() != null && student.getAdmAppln().getCourse().getName() != null){
					to.setCourseName(student.getAdmAppln().getCourse().getName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getResidentCategory() != null && student.getAdmAppln().getPersonalData().getResidentCategory().getName() != null){
					to.setCategory(student.getAdmAppln().getPersonalData().getResidentCategory().getName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getCaste() != null && student.getAdmAppln().getPersonalData().getCaste().getName() != null){
					to.setSectionName(student.getAdmAppln().getPersonalData().getCaste().getName());
				}
				list.add(to);
			}
		}
		
		return list;
	}
	/**
	 * @param assignClassForStudentForm
	 * @return
	 * @throws Exception
	 */
	public List<Student> getStudentListWithClass(AssignClassForStudentForm assignClassForStudentForm) throws Exception{
		List<Student> stuList = new ArrayList<Student>();
		List<AssingClassForStudentTO> tos = assignClassForStudentForm.getAssingClassForStudentTOs();
		if(tos != null && !tos.isEmpty()){
			Iterator<AssingClassForStudentTO> iterator = tos.iterator();
			while (iterator.hasNext()) {
				AssingClassForStudentTO assingClassForStudentTO = (AssingClassForStudentTO) iterator.next();
				if(assingClassForStudentTO.getChecked().equalsIgnoreCase("on")){
					Student student = new Student();
					ClassSchemewise classSchemewise = new ClassSchemewise();
					Classes classes = new Classes();
					classes.setId(Integer.parseInt(assignClassForStudentForm.getClassId()));
					classSchemewise.setClasses(classes );
					student.setClassSchemewise(classSchemewise);
					student.setId(Integer.parseInt(assingClassForStudentTO.getStudentId()));
					stuList.add(student);
				}
				
			}
		}
		return stuList;
	}
}
