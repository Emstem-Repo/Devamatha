package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.forms.reports.AdmittedStudentReportForm;
import com.kp.cms.to.reports.AdmittedStudentsReportsTO;
import com.kp.cms.to.reports.ClassWithAdmStudentTO;

public class AdmissionReportHelper {

	private static volatile AdmissionReportHelper admissionReportHelper = null;

	private static final Log log = LogFactory
			.getLog(AdmissionReportHelper.class);

	private AdmissionReportHelper() {

	}

	public static AdmissionReportHelper getInstance() {
		if (admissionReportHelper == null) {
			admissionReportHelper = new AdmissionReportHelper();
		}
		return admissionReportHelper;
	}

	/**
	 * Get the common search criteria query.
	 * 
	 * @param admittedStudentReportForm
	 * @return
	 */
	private StringBuffer getCommonSearchCriteria(
			AdmittedStudentReportForm admittedStudentReportForm) {
		log.info("entering getCommonSearchCriteria of AdmissionReportHelper");
		StringBuffer commonSearchCriteria = new StringBuffer();
		if (admittedStudentReportForm.getAcademicYear() != null
				&& admittedStudentReportForm.getAcademicYear().trim().length() > 0) {
			commonSearchCriteria
					.append("classSchemeWise.curriculumSchemeDuration.academicYear = ");
			commonSearchCriteria.append(admittedStudentReportForm
					.getAcademicYear());
			commonSearchCriteria.append(" and  ");
		}

		if (admittedStudentReportForm.getProgramId() != null
				&& admittedStudentReportForm.getProgramId().trim().length() > 0) {
			commonSearchCriteria.append("admAppln.courseBySelectedCourseId.program.id = ");
			commonSearchCriteria.append(admittedStudentReportForm
					.getProgramId());
			commonSearchCriteria.append(" and  ");
		}

		if (admittedStudentReportForm.getCourseId() != null
				&& admittedStudentReportForm.getCourseId().trim().length() > 0) {
			commonSearchCriteria.append("admAppln.courseBySelectedCourseId.id = ");
			commonSearchCriteria
					.append(admittedStudentReportForm.getCourseId());
			commonSearchCriteria.append(" and  ");
		}

		if (admittedStudentReportForm.getSemister() != null
				&& admittedStudentReportForm.getSemister().trim().length() > 0) {
			commonSearchCriteria
					.append("classSchemeWise.curriculumSchemeDuration.semesterYearNo = ");
			commonSearchCriteria
					.append(admittedStudentReportForm.getSemister());
			commonSearchCriteria.append(" and  ");
		}
		if (admittedStudentReportForm.getProgramTypeId() != null
				&& admittedStudentReportForm.getProgramTypeId().trim().length() > 0) {

			commonSearchCriteria
					.append("admAppln.courseBySelectedCourseId.program.programType.id = ");
			commonSearchCriteria.append(admittedStudentReportForm
					.getProgramTypeId());

		}
		log.info("exit of getCommonSearchCriteria of AdmissionReportHelper");
		return commonSearchCriteria;

	}

	/**
	 * Get the Query for AdmittedStudent report.
	 * 
	 * @param admittedStudentReportForm
	 * @return
	 */
	public String getAdmittedStudentQuery(
			AdmittedStudentReportForm admittedStudentReportForm) {
		StringBuffer commonSearchQuery = getCommonSearchCriteria(admittedStudentReportForm);
		String admittedStudentSearchQuery = " select admAppln,classSchemeWise.classes.id ,classSchemeWise.classes.name "
				+ " from ClassSchemewise classSchemeWise "
				+ " inner join classSchemeWise.classes.course courese  "
				+ " inner join courese.admApplns admAppln "
				+ " inner join admAppln.students students "
				+ " where students.isActive = 1 and " 
				+"students.admAppln.isCancelled = 0 and "
				+ commonSearchQuery;
		return admittedStudentSearchQuery;
	}

	/**
	 * Converts from Bo to To.
	 * 
	 * @param admittedStudentsReportList
	 * @return
	 */
	public List<AdmittedStudentsReportsTO> convertBotoTo(
			List<Object[]> admittedStudentsReportList, AdmittedStudentReportForm reportForm) {
		log.info("entering convertBotoTo of AdmissionReportHelper");
		List<AdmittedStudentsReportsTO> admittedStudentsReportsTOList = new ArrayList<AdmittedStudentsReportsTO>();
		Map<String, List<AdmittedStudentsReportsTO>> admittedStudentsReportsTOMap = new HashMap<String, List<AdmittedStudentsReportsTO>>();
		if (admittedStudentsReportList != null) {
			Iterator<Object[]> admittedStudentReportIterator = admittedStudentsReportList
					.iterator();

			while (admittedStudentReportIterator.hasNext()) {
				Object[] objects = (Object[]) admittedStudentReportIterator
						.next();
				AdmAppln admAppln = (AdmAppln) objects[0];
				String className = "";
				if (objects[2] != null) {
					className = (String) objects[2];
				}
				
				List<AdmittedStudentsReportsTO> admittedStudentsReportsTOListForMap = null;
				if (admittedStudentsReportsTOMap.containsKey(className.trim())) {
					admittedStudentsReportsTOListForMap = admittedStudentsReportsTOMap
							.get(className.trim());
				} else {
					admittedStudentsReportsTOListForMap = new ArrayList<AdmittedStudentsReportsTO>();
				}

				AdmittedStudentsReportsTO admittedStudentsReportsTO = new AdmittedStudentsReportsTO();
				admittedStudentsReportsTO
						.setSlNo(String
								.valueOf(admittedStudentsReportsTOListForMap
										.size() + 1));
				admittedStudentsReportsTO.setApplicationNo(String
						.valueOf(admAppln.getApplnNo()));
				StringBuffer applicantName = new StringBuffer();
				if (admAppln.getPersonalData().getFirstName() != null) {
					applicantName.append(admAppln.getPersonalData()
							.getFirstName());
					applicantName.append(' ');

				}
				if (admAppln.getPersonalData().getMiddleName() != null) {
					applicantName.append(admAppln.getPersonalData()
							.getMiddleName());
					applicantName.append(' ');

				}
				if (admAppln.getPersonalData().getLastName() != null) {
					applicantName.append(admAppln.getPersonalData()
							.getLastName());
					applicantName.append(' ');
				}

				admittedStudentsReportsTO.setApplicantName(applicantName
						.toString());
				admittedStudentsReportsTO.setClassName(className);

				if (admAppln.getPersonalData() != null
						&& admAppln.getPersonalData().getEdnQualifications() != null) {

					Set<EdnQualification> ednQualifications = admAppln
							.getPersonalData().getEdnQualifications();
					Iterator<EdnQualification> educationQualificationIterator = ednQualifications
							.iterator();
					while (educationQualificationIterator.hasNext()) {
						EdnQualification ednQualification = (EdnQualification) educationQualificationIterator
								.next();
						if (ednQualification.getDocChecklist() != null
								&& ednQualification.getDocChecklist()
										.getIsPreviousExam()
								&& ednQualification.getPercentage() != null) {

							admittedStudentsReportsTO
									.setPrerequisitePercentage(ednQualification
											.getPercentage().toPlainString());
						}

					}

				}
				admittedStudentsReportsTO.setGender(admAppln.getPersonalData()
						.getGender());
				admittedStudentsReportsTOListForMap
						.add(admittedStudentsReportsTO);
				admittedStudentsReportsTOMap.put(className.trim(),
						admittedStudentsReportsTOListForMap);
				
				
			}

		}
		Iterator<String> keyIterator = admittedStudentsReportsTOMap.keySet()
				.iterator();
		ClassWithAdmStudentTO classStTO; 
		List<ClassWithAdmStudentTO> classWithStudList = new ArrayList<ClassWithAdmStudentTO>();
		while (keyIterator.hasNext()) {
			String string = (String) keyIterator.next();
			admittedStudentsReportsTOList.addAll(admittedStudentsReportsTOMap
					.get(string));
			classStTO= new ClassWithAdmStudentTO();
			classStTO.setClassName(string);
			classStTO.setStudents(admittedStudentsReportsTOMap
					.get(string));
			classWithStudList.add(classStTO);

		}

		log.info("exit of convertBotoTo of AdmissionReportHelper");
		reportForm.setClassWithStudents(classWithStudList);
		return admittedStudentsReportsTOList;

	}

}
