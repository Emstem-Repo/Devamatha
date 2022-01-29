package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.AdmissionIncompleteForm;
import com.kp.cms.to.admin.StudentTO;

public class AdmissionIncompleteHelper {
	private static final Log log = LogFactory.getLog(AdmissionIncompleteHelper.class);
	public static volatile AdmissionIncompleteHelper self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static AdmissionIncompleteHelper getInstance(){
		if(self==null)
			self= new AdmissionIncompleteHelper();
		return self;
	}
	/**
	 * Private constructor for a singleton class
	 */
	private AdmissionIncompleteHelper(){		
	}
	/**
	 * 
	 * @param incompleteForm
	 * @returns seacrh criteria for incomplete admission students
	 * @throws Exception
	 */
	public String getSelectionCriteria(AdmissionIncompleteForm incompleteForm)throws Exception {
		log.info("entered getSelectionCriteria. of AdmissionIncompleteHelper");
		String query = "from Student st "
			+ "where st.admAppln.isSelected = 1 " 
			+ "and st.admAppln.isApproved = 0";
		
		if(incompleteForm.getCourseId().length()>0){
			query = query + " and st.admAppln.courseBySelectedCourseId.id = " + Integer.parseInt(incompleteForm.getCourseId());
		}
		if(incompleteForm.getProgramId().length()>0){
			query = query + " and st.admAppln.courseBySelectedCourseId.program.id = " + Integer.parseInt(incompleteForm.getProgramId());
		}
		if(incompleteForm.getProgramTypeId().length()>0){
			query = query + " and st.admAppln.courseBySelectedCourseId.program.programType.id = " + Integer.parseInt(incompleteForm.getProgramTypeId());
		}
		query = query + " order by st.admAppln.applnNo";
		log.info("Leaving into getSelectionCriteria. of AdmissionIncompleteHelper");
		return query;
	}
	/**
	 * 
	 * @param studentBOList
	 * @returns incomplete admission students
	 * @throws Exception
	 */
	public List<StudentTO> copyStudentBosToTOs(List<Student> studentBOList) throws Exception{
		log.info("entered copyStudentBosToTOs. of AdmissionIncompleteHelper");
		List<StudentTO> studentList = new ArrayList<StudentTO>();
		StudentTO studentTO = null;
		if(studentBOList!=null){
			Iterator<Student> studIterator = studentBOList.iterator();
			while (studIterator.hasNext()) {
				Student student = studIterator.next();
				studentTO = new StudentTO();
				StringBuffer buffer = new StringBuffer();
				
				if(student.getAdmAppln()!=null){
					if(student.getAdmAppln().getApplnNo()!=0){
						studentTO.setApplicationNo(student.getAdmAppln().getApplnNo());
					}
					if(student.getAdmAppln().getCourseBySelectedCourseId()!=null){
						studentTO.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
					}
					if(student.getAdmAppln().getAdmittedThrough()!=null && student.getAdmAppln().getAdmittedThrough().getName()!=null){
						studentTO.setAdmittedThroughCode(student.getAdmAppln().getAdmittedThrough().getName());	
					}
					if(student.getAdmAppln().getPersonalData()!=null){
						if(student.getAdmAppln().getPersonalData().getFirstName()!=null){
							buffer.append(student.getAdmAppln().getPersonalData().getFirstName());
						}
						if(student.getAdmAppln().getPersonalData().getMiddleName()!=null){
							buffer.append(" " + student.getAdmAppln().getPersonalData().getMiddleName());
						}
						if(student.getAdmAppln().getPersonalData().getLastName()!=null){
							buffer.append(" " + student.getAdmAppln().getPersonalData().getLastName());
						}
					}
					studentTO.setStudentName(buffer.toString());
				}
				studentList.add(studentTO);
			}
		}
		log.info("Leaving into copyStudentBosToTOs. of AdmissionIncompleteHelper");
		return studentList;
	}
}
