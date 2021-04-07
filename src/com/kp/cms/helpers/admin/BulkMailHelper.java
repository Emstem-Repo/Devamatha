package com.kp.cms.helpers.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.forms.admin.BulkMailForm;
import com.kp.cms.to.admission.StudentSearchTO;
import com.kp.cms.utilities.CommonUtil;

public class BulkMailHelper {
	
	private static final Log log = LogFactory.getLog(BulkMailHelper.class);
	
	/**
	 * Converts from Bo to TO
	 * @param studentSearchBo
	 * @return
	 */
	public static List<StudentSearchTO> convertBoToTo(List studentSearchBo) {
		log.info("entering into convertBoToTo in BulkMailHelper class.");
		List<StudentSearchTO> studentSearchTo = new ArrayList<StudentSearchTO>();
		if (studentSearchBo != null) {
			Iterator studentSearchIterator = studentSearchBo.iterator();

			while (studentSearchIterator.hasNext()) {
				Object[] searchResults = (Object[]) studentSearchIterator.next();
				AdmAppln admAppln = (AdmAppln) searchResults[0];
				StudentSearchTO studentSearch = new StudentSearchTO();
				studentSearch
						.setApplicationId(((Integer) admAppln.getApplnNo())
								.toString());
				
				StringBuffer applicantName = new StringBuffer();
				if(admAppln.getPersonalData()
						.getFirstName() != null) {
					applicantName.append(admAppln.getPersonalData()
					.getFirstName());
					applicantName.append(' ');
				}
				if(admAppln.getPersonalData().getMiddleName() != null) {
					applicantName.append(admAppln.getPersonalData()
							.getMiddleName());
							applicantName.append(' ');
					
				}
				if(admAppln.getPersonalData().getLastName() != null) {
					applicantName.append(admAppln.getPersonalData()
							.getLastName());
							applicantName.append(' ');
				}
				studentSearch.setApplicantName(applicantName.toString());
				studentSearch.setApplicantDOB(CommonUtil.getStringDate(admAppln
						.getPersonalData().getDateOfBirth()));
				if(admAppln
						.getTotalWeightage() != null) {
					studentSearch.setApplicantTotalWeightage(admAppln
							.getTotalWeightage().toString());
				}
				
				studentSearch.setAdmApplnId(((Integer) admAppln.getId())
						.toString());
				studentSearch.setEmail(admAppln.getPersonalData().getEmail());
				studentSearchTo.add(studentSearch);
			}
		}
		log.info("exit of convertBoToTo in BulkMailHelper class.");
		return studentSearchTo;
	}
	
	/**
	 * get the query for selected students
	 * @param bulkMailForm
	 * @return
	 */
	public static String getSelectedStudentSearch(BulkMailForm bulkMailForm) {
		log.info("entering into getSelectedStudentSearch in BulkMailHelper class.");
		String statusCriteria = commonSearch(bulkMailForm);
		
		String searchCriteria = " select adm_appln.applnNo , " +
				"  adm_appln.personalData.firstName ,	" +
				"  adm_appln.personalData.dateOfBirth, " +
				"  adm_appln.personalData.email, " +
				"  adm_appln.totalWeightage," +
				"  interviewSelected.id   from" +
				"  AdmAppln adm_appln , InterviewSelected  interviewSelected  where" +
				"   adm_appln.id  = interviewSelected.admAppln.id   and interviewSelected.isCardGenerated = false  and  " +
				
				"interviewSelected.interviewProgramCourse.id  = " + bulkMailForm.getInterviewType().trim() +")  " + statusCriteria +
				 "  group by adm_appln.applnNo";
		  
		
		log.info("exit of getSelectedStudentSearch in BulkMailHelper class.");
		return searchCriteria;
		
	}
	
	/**
	 * Constructs the common search query.
	 * @param studentSearchForm
	 * @return
	 */
	private static String commonSearch(BulkMailForm studentSearchForm) {
		log.info("entering into commonSearch in BulkMailHelper class.");
		StringBuffer searchCriteria = new StringBuffer() ;
		
		if (studentSearchForm.getCourseId().trim().length() > 0) {
			String course = " admAppln.courseBySelectedCourseId.id = "
					+ studentSearchForm.getCourseId();
			searchCriteria.append(course);
		}

		if (studentSearchForm.getYear().trim().length() > 0) {
			String appliedYear = " and admAppln.appliedYear = "
					+ studentSearchForm.getYear();
			searchCriteria.append(appliedYear);
		}

		if (studentSearchForm.getNationalityId().trim().length() > 0) {
			String nationality = " and admAppln.personalData.nationality.name like"
					+ "'" + studentSearchForm.getNationalityId() + "%" + "'";
			searchCriteria.append(nationality);
		}

		if (studentSearchForm.getYear().trim().length() > 0) {
			String appliedYear = " and admAppln.appliedYear = "
					+ studentSearchForm.getYear();
			searchCriteria.append(appliedYear);
		}

		if (studentSearchForm.getNationalityId().trim().length() > 0) {
			String nationality = " and admAppln.personalData.nationality.name like"
					+ "'" + studentSearchForm.getNationalityId() + "%" + "'";
			searchCriteria.append(nationality);
		}
		
		if (studentSearchForm.getBirthCountry().trim().length() > 0) {
			String birthCountry = " and admAppln.personalData.countryByCountryId.name like"
					+ "'" + studentSearchForm.getBirthCountry() + "%" + "'";
			searchCriteria.append(birthCountry);
		}
		
		if (studentSearchForm.getBirthState().trim().length() > 0) {
			String birthState = " and admAppln.personalData.stateByStateId.name like"
					+ "'" + studentSearchForm.getBirthState() + "%" + "'";
			searchCriteria.append(birthState);
		}

		if (studentSearchForm.getResidentCategoryId().trim().length() > 0) {
			String residentCategory = "and admAppln.personalData.residentCategory.name like "
					+ "'"
					+ studentSearchForm.getResidentCategoryId()
					+ "%"
					+ "'";
			searchCriteria.append(residentCategory);
		}

		if (studentSearchForm.getReligionId().trim().length() > 0) {
			String religionName = " and admAppln.personalData.religion.name like "
					+ "'" + studentSearchForm.getReligionId() + "%" + "'";
			searchCriteria.append(religionName);
		}

		if (studentSearchForm.getSubReligionId().trim().length() > 0) {
			String subReligionName = "  and admAppln.personalData.religionSection.name like "
					+ "'" + studentSearchForm.getSubReligionId() + "%" + "'";
			searchCriteria.append(subReligionName);
		}

		if (studentSearchForm.getCasteCategoryId().trim().length() > 0) {
			String casteCategory = "  and admAppln.personalData.caste.name like "
					+ "'" + studentSearchForm.getCasteCategoryId() + "%" + "'";
			searchCriteria.append(casteCategory);
		}

		if (studentSearchForm.getBelongsTo().equals('R')
				|| studentSearchForm.getBelongsTo().equals('U')) {
			String belongsTo = " and admAppln.personalData.ruralUrban = " + "'"
					+ studentSearchForm.getBelongsTo() + "'";
			searchCriteria.append(belongsTo);
		}

		if (studentSearchForm.getGender().trim().length() > 0) {
			String gender = " and admAppln.personalData.gender = " + "'"
					+ studentSearchForm.getGender() + "'";
			searchCriteria.append(gender);
		}

		if (studentSearchForm.getBloodGroup().trim().length() > 0) {
			String blodGroup = "and admAppln.personalData.bloodGroup = " + "'"
					+ studentSearchForm.getBloodGroup() + "'";
			searchCriteria.append(blodGroup);
		}


		if (studentSearchForm.getWeightage().trim().length() > 0) {
			String weightage = " and admAppln.totalWeightage = "
					+ new BigDecimal(studentSearchForm.getWeightage());
			searchCriteria.append(weightage);
		}

		if (studentSearchForm.getApplicantName().trim().length() > 0) {
			String applicantName = " and admAppln.personalData.firstName  like  "
					+ "'" + studentSearchForm.getApplicantName() + "%" + "'";
			searchCriteria.append(applicantName);
		}
		
		if (studentSearchForm.getUniversity().trim().length() > 0) {
			String university = "   and educationQualification.university.name like "
					+ "'" + studentSearchForm.getUniversity() + "%" + "'";
			searchCriteria.append(university);

		}
		
		if (studentSearchForm.getInterviewType() != null
				&& !studentSearchForm.getInterviewType().isEmpty()) {

			searchCriteria
					.append(" and interviewSelected.interviewProgramCourse.id = ");
			searchCriteria.append(studentSearchForm.getInterviewType());
		}
		
		if (studentSearchForm.getInstitute().trim().length() > 0) {
			String institute = "";

			institute = "   and educationQualification.college.name like "
					+ "'" + studentSearchForm.getInstitute() + "%" + "'";

			searchCriteria.append(institute);
		}
		
		if (studentSearchForm.getMarksObtained().trim().length() > 0) {
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getMarksObtained()));
			String percentage = "";
			percentage = "   and educationQualification.percentage >= "
					+ percentageValue;
			searchCriteria.append(percentage);
		}
		log.info("exit of commonSearch in BulkMailHelper class.");
		return searchCriteria.toString();
	}
	
	/**
	 * get the selection search criteria
	 * @param studentSearchForm
	 * @return
	 */
	public static String getSelectionSearchCriteria(
			BulkMailForm studentSearchForm) {
		log.info("entering into getSelectionSearchCriteria in BulkMailHelper class.");
		String statusCriteria = commonSearch(studentSearchForm);
		String searchCriteria;
		String ednJoin = "";
		if (studentSearchForm.getUniversity().trim().length() > 0
				|| studentSearchForm.getInstitute().trim().length() > 0
				|| studentSearchForm.getMarksObtained().trim().length() > 0) {
			ednJoin = "	join personalData.ednQualifications educationQualification ";
			statusCriteria = statusCriteria
					+ "  and educationQualification.docChecklist.isPreviousExam = true ";
		}
		
		if(studentSearchForm.getInterviewType() != null
				&& !studentSearchForm.getInterviewType().isEmpty()){
			ednJoin = "	inner join admAppln.interviewSelecteds interviewSelected  ";
			statusCriteria = statusCriteria	+ " and interviewSelected.isCardGenerated = true ";
		}

		searchCriteria = "  from AdmAppln admAppln  "
				+ "  inner join admAppln.personalData personalData   " 
				+ ednJoin + " where  "
			    + statusCriteria;
		log.info("exit of getSelectionSearchCriteria in BulkMailHelper class.");
		return searchCriteria;

	}
}