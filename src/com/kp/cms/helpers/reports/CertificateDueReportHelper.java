package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.CertificateDueReportForm;
import com.kp.cms.handlers.employee.PrincipalAppraisalHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CommonUtil;

public class CertificateDueReportHelper {
	private static final Log log = LogFactory.getLog(PrincipalAppraisalHandler.class);
	public static volatile CertificateDueReportHelper certificateDueReportHelper = null;

	private CertificateDueReportHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static CertificateDueReportHelper getInstance() {
		if (certificateDueReportHelper == null) {
			certificateDueReportHelper = new CertificateDueReportHelper();
		}
		return certificateDueReportHelper;
	}
	/**
	 * 
	 * @param certificateForm
	 * @returns the search query
	 */

	public String getSearchQuery(CertificateDueReportForm certificateForm)throws Exception {
		log.info("Entering into getSearchQuery of CertificateDueReportHelper");
		String absenceSearchCriteria = "from ApplnDoc doc "
			+ "where doc.hardcopySubmitted = 0 "
			+ "and doc.isPhoto = 0 "
			+ "and doc.notApplicable = 0 "
			+ "and doc.submissionDate <" + "'" + CommonUtil.ConvertStringToSQLDate(certificateForm.getDueDate()) + "'";
		log.info("Leaving into getSearchQuery of CertificateDueReportHelper");
		return absenceSearchCriteria;
	}
	
	/**
	 * @param applnDocBOList
	 * @returns the details of certificate due students as a List
	 */
	public List<StudentTO> prepareCertificateDueStudents(
			List<ApplnDoc> applnDocBOList) throws Exception{
		log.info("Entering into prepareCertificateDueStudents of CertificateDueReportHelper");
		List<StudentTO> studentList = new ArrayList<StudentTO>();
		Set<Integer>studentIDSet = new HashSet<Integer>();
		StudentTO studentTO = null;
		StringBuffer buffer = null;
		if(applnDocBOList!=null){
			Iterator<ApplnDoc>docIterator = applnDocBOList.iterator();
			while (docIterator.hasNext()) {
				ApplnDoc applnDoc = docIterator.next();
				if(applnDoc.getAdmAppln()!=null && applnDoc.getAdmAppln().getStudents()!=null){
					Iterator<Student>studIterator = applnDoc.getAdmAppln().getStudents().iterator();
					while (studIterator.hasNext()) {
							Student student = studIterator.next();
							if(!studentIDSet.contains(student.getId())){
								studentTO = new StudentTO();
								buffer = new StringBuffer();
								if(student.getRegisterNo()!=null){
									studentTO.setRegisterNo(student.getRegisterNo());
								}
								if(student.getAdmAppln()!=null){
										if(student.getAdmAppln().getApplnNo()!= 0){
											studentTO.setApplicationNo(student.getAdmAppln().getApplnNo());
										}
										if(student.getAdmAppln().getPersonalData()!=null){
											if(student.getAdmAppln().getPersonalData().getFirstName()!=null){
												buffer.append(student.getAdmAppln().getPersonalData().getFirstName());
											}
											if(student.getAdmAppln().getPersonalData().getMiddleName()!=null){
												buffer.append(" "+student.getAdmAppln().getPersonalData().getMiddleName());
											}
											if(student.getAdmAppln().getPersonalData().getLastName()!=null){
												buffer.append(" "+student.getAdmAppln().getPersonalData().getLastName());
											}
											studentTO.setStudentName(buffer.toString());
										}
										if(student.getAdmAppln().getCourseBySelectedCourseId()!=null && student.getAdmAppln().getCourse().getName()!=null){
											studentTO.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
										}
										
								}
								if(student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null
								&&  student.getClassSchemewise().getClasses().getTermNumber()!=null){
									studentTO.setSemester(String.valueOf(student.getClassSchemewise().getClasses().getTermNumber()));
								}
								studentList.add(studentTO);
								studentIDSet.add(student.getId());
							}
					}
				}
			}
		}
		log.info("Leaving into prepareCertificateDueStudents of CertificateDueReportHelper");
		return studentList;
	}
}
