package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.FinalMeritListForm;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admission.FinalMeritListSearchTo;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Helper class for Final merit list search.
 *
 */
public class FinalMeritListSearchHelper {
	
	private static final Log log = LogFactory.getLog(FinalMeritListSearchHelper.class);
	
	/**
	 * Converts from BO to TO
	 * @param finalMeritListBO
	 * @return
	 */
	public static List<FinalMeritListSearchTo> getFinalMeritListTOList(
			List finalMeritListBO) throws Exception {
		log.info("entering into getFinalMeritListTOList of FinalMeritListSearchHelper class.");
		List<FinalMeritListSearchTo> finalMeritList = new ArrayList<FinalMeritListSearchTo>();
		if (finalMeritListBO != null) {
			Iterator finalMeritSearchIterator = finalMeritListBO.iterator();
			while (finalMeritSearchIterator.hasNext()) {
				Object[] searchResults = (Object[]) finalMeritSearchIterator.next();
				FinalMeritListSearchTo finalMeritListSearchTo = new FinalMeritListSearchTo();
				AdmAppln admAppln = (AdmAppln) searchResults[0];

				finalMeritListSearchTo.setApplicationId(((Integer) admAppln
						.getApplnNo()).toString());

				if (admAppln.getPersonalData() != null
						&& admAppln.getPersonalData().getEmail() != null
						&& !admAppln.getPersonalData().getEmail().isEmpty()) {
					finalMeritListSearchTo.setApplicantMail(admAppln.getPersonalData().getEmail());
				}
				
				finalMeritListSearchTo.setAdmAppln(admAppln);
				if(admAppln.getCourse()!=null){
					finalMeritListSearchTo.setMaxIntakeCourseId(String.valueOf(admAppln.getCourse().getId()));
				}
				StringBuffer applicantName = new StringBuffer();
				if (admAppln.getPersonalData().getFirstName() != null) {
					 applicantName.append(admAppln.getPersonalData().getFirstName());
					 applicantName.append(' ');
					
					
				}
				if (admAppln.getPersonalData().getMiddleName() != null) {
					 applicantName.append(admAppln.getPersonalData().getMiddleName());
					 applicantName.append(' ');
					
				}
				if (admAppln.getPersonalData().getLastName() != null) {
					 applicantName.append(admAppln.getPersonalData().getLastName());
					 applicantName.append(' ');
					
				}
				finalMeritListSearchTo.setApplicantName(applicantName.toString());
				
				finalMeritListSearchTo.setSelectedCrsName(admAppln.getCourseBySelectedCourseId().getName());

				finalMeritListSearchTo.setApplicantDOB(CommonUtil
						.getStringDate(admAppln.getPersonalData()
								.getDateOfBirth()));
				if(admAppln
						.getPersonalData().getNationality()== null) {
					if(admAppln.getPersonalData().getNationalityOthers() != null) {
						finalMeritListSearchTo.setApplicantNationality(admAppln
								.getPersonalData().getNationalityOthers());
					}
					
				} else {
					finalMeritListSearchTo.setApplicantNationality(admAppln
							.getPersonalData().getNationality().getName());
				}
				
				if (admAppln.getPersonalData().getReligion() == null) {
					if (admAppln.getPersonalData().getReligionOthers() != null) {
						finalMeritListSearchTo.setApplicantReligion(admAppln
								.getPersonalData().getReligionOthers());
					}
				} else {
					finalMeritListSearchTo.setApplicantReligion(admAppln
							.getPersonalData().getReligion().getName());
				}

				if (admAppln.getPersonalData().getReligionSection() == null) {
					if (admAppln.getPersonalData().getReligionSectionOthers() != null) {
						finalMeritListSearchTo.setApplicantSubReligion(admAppln
								.getPersonalData().getReligionSectionOthers());
					}
				} else {
					finalMeritListSearchTo.setApplicantSubReligion(admAppln
							.getPersonalData().getReligionSection().getName());
				}
				if (admAppln.getPersonalData().getCaste() == null) {
					if (admAppln.getPersonalData().getCasteOthers() != null) {
						finalMeritListSearchTo
						.setApplicantCasteCategory(admAppln
								.getPersonalData().getCasteOthers());
					}
				} else {
					finalMeritListSearchTo.setApplicantCasteCategory(admAppln
							.getPersonalData().getCaste().getName());
				}

				Set<InterviewResult> interviewResults = admAppln
				.getInterviewResults();
				Iterator<InterviewResult> interviewResultsIterator = interviewResults
				.iterator();
				int interviewProgramCourseId = -1;
				while (interviewResultsIterator.hasNext()) {
					InterviewResult interviewResult = (InterviewResult) interviewResultsIterator
					.next();
					if (interviewResult.getInterviewProgramCourse() != null && interviewResult.getInterviewProgramCourse().getId() > interviewProgramCourseId) {
						interviewProgramCourseId = interviewResult
						.getInterviewProgramCourse().getId();
						if(interviewResult.getInterviewStatus()!=null){
							finalMeritListSearchTo.setInWaitingList(interviewResult
									.getInterviewStatus().getId());
						}
					}
				}

				/**
				 * Used to display student category in final merit list search
				 */
				if (admAppln.getPersonalData() != null && admAppln.getPersonalData().getResidentCategory() != null && admAppln.getPersonalData().getResidentCategory()
							.getName() != null) {
						finalMeritListSearchTo.setApplicantResidentCategory(admAppln.getPersonalData().getResidentCategory().getName());
				}
			

				if (!admAppln.getCandidatePrerequisiteMarks().isEmpty()) {
					Iterator<CandidatePrerequisiteMarks> candidatesPrerequisiteIterator = admAppln
					.getCandidatePrerequisiteMarks().iterator();
					while (candidatesPrerequisiteIterator.hasNext()) {
						CandidatePrerequisiteMarks candidatePrerequisiteMarks = (CandidatePrerequisiteMarks) candidatesPrerequisiteIterator
						.next();
						finalMeritListSearchTo
						.setPreRequisiteType(candidatePrerequisiteMarks
								.getPrerequisite().getName());
						finalMeritListSearchTo
						.setPrerequisiteMarks(candidatePrerequisiteMarks
								.getPrerequisiteMarksObtained() == null ? ""
										: candidatePrerequisiteMarks
										.getPrerequisiteMarksObtained()
										.toString());
					}
				}

				if (admAppln.getTotalWeightage() != null) {
					finalMeritListSearchTo.setApplicantTotalWeightage(admAppln
							.getTotalWeightage().toString());
				}
				if(admAppln.getPersonalData().getGender()!=null) {
					finalMeritListSearchTo.setApplicantGender(admAppln.getPersonalData().getGender());
				}
				

				finalMeritListSearchTo.setFinalMeritSetId(((Integer) admAppln
						.getId()).toString());
				
				boolean isStudentAdmitted = false;
				if(admAppln.getStudents() != null && !admAppln.getStudents().isEmpty()) {
					Iterator<Student> studentIterator = admAppln.getStudents().iterator();
					while (studentIterator.hasNext()) {
						Student student = (Student) studentIterator.next();
						if(student.getIsAdmitted()!=null && student.getIsAdmitted()) {
							isStudentAdmitted = true;
						}
					}
				}
				if(!isStudentAdmitted) {
					finalMeritList.add(finalMeritListSearchTo);
				}
			}
		}
		log.info("exit of getFinalMeritListTOList of FinalMeritListSearchHelper class.");
		return finalMeritList;
	}
	
	
	/**
	 * Constructs Final merit list selected query.
	 * @param finalMeritListForm
	 * @param isSelected
	 * @param isInterviewSelected
	 * @return
	 */
	public static String getFinalMeritListSelectedQuery(
			FinalMeritListForm finalMeritListForm, Boolean isSelected,
			Boolean isInterviewSelected) {
		log.info("entering into getFinalMeritListSelectedQuery of FinalMeritListSearchHelper class.");
		String searchCriteria = "";

		String statusCriteria = "";
		
		if(finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()){
			if(isSelected == null ) {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.id =   "
					+ finalMeritListForm.getCourseId();
			} else {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.id =   "
				+ finalMeritListForm.getCourseId();
			}
		}else{
			if(isSelected == null ) {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.program.id =   "
					+ finalMeritListForm.getProgramId();
			} else {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.program.id =   "
				+ finalMeritListForm.getProgramId();
			}
		}
		
		if (finalMeritListForm.getCasteCategoryId()!=null && 
				finalMeritListForm.getCasteCategoryId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.caste.id ="
					+ finalMeritListForm.getCasteCategoryId();
		}

		if (finalMeritListForm.getReligionId()!=null 
							&& finalMeritListForm.getReligionId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.religion.id ="
					+ finalMeritListForm.getReligionId();
		}

		if (finalMeritListForm.getSubReligionId()!=null &&
				finalMeritListForm.getSubReligionId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.religionSection.id ="
					+ finalMeritListForm.getSubReligionId();
		}

		if (finalMeritListForm.getNationalityId()!=null 
				&& finalMeritListForm.getNationalityId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.nationality.id ="
					+ finalMeritListForm.getNationalityId();
		}

		if (finalMeritListForm.getResidentCategoryId()!=null && 
				finalMeritListForm.getResidentCategoryId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.residentCategory.id ="
					+ finalMeritListForm.getResidentCategoryId();
		}
		
		
		if (finalMeritListForm.getSportsPerson() != null  ) {
			String sportsPerson = " and admAppln.personalData.isSportsPerson = " 
				+ finalMeritListForm.getSportsPerson() ;
			searchCriteria = searchCriteria + sportsPerson;
		}
		
		if (finalMeritListForm.getHandicapped() != null ) {
			String handicapped = " and admAppln.personalData.isHandicapped = "  
					+ finalMeritListForm.getHandicapped() ;
			
		
			searchCriteria = searchCriteria + handicapped;
		}

		if (finalMeritListForm.getGender() != null
				&& finalMeritListForm.getGender().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.gender =" + "'"
					+ finalMeritListForm.getGender() + "'";
		}

		if (finalMeritListForm.getBelongsTo() != null) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.ruralUrban =" + "'"
					+ finalMeritListForm.getBelongsTo() + "'";
		}

		if (finalMeritListForm.getYear()!=null 
				&& finalMeritListForm.getYear().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.appliedYear = "
					+ finalMeritListForm.getYear();
		}
		
//			searchCriteria = searchCriteria + " and "
//					+ " (admAppln.isApproved = null or admAppln.isApproved = false) ";

		if (finalMeritListForm.getUniversityId()!=null 
				&& finalMeritListForm.getUniversityId().trim().length() > 0) {
			searchCriteria = searchCriteria
					+ " and  ednQualification.docChecklist.isPreviousExam = true and  ednQualification.university.id = "
					+ finalMeritListForm.getUniversityId();
			statusCriteria = statusCriteria
					+ "  join personalData.ednQualifications ednQualification ";
		}

		if (finalMeritListForm.getInstituteId()!=null 
				&& finalMeritListForm.getInstituteId().trim().length() > 0) {
			searchCriteria = searchCriteria
					+ " and  ednQualification.docChecklist.isPreviousExam = true and  ednQualification.college.id = "
					+ finalMeritListForm.getInstituteId();

			if (finalMeritListForm.getUnivercity() !=null 
					&& finalMeritListForm.getUnivercity().trim().length() == 0) {
				statusCriteria = statusCriteria
						+ "  join personalData.ednQualifications ednQualification ";
			}
		}

		if (finalMeritListForm.getWeightageFrom()!=null 
				&& finalMeritListForm.getWeightageFrom().trim().length() > 0) {
			Double weightageFrom = Double.valueOf(finalMeritListForm
					.getWeightageFrom());
			searchCriteria = searchCriteria + " and "
					+ " admAppln.totalWeightage  >= " + weightageFrom;
		}

		if (finalMeritListForm.getWeightageTo()!=null && 
				finalMeritListForm.getWeightageTo().trim().length() > 0) {
			Double weightageTo = Double.valueOf(finalMeritListForm
					.getWeightageTo());
			searchCriteria = searchCriteria + " and "
					+ " admAppln.totalWeightage  <= " + weightageTo;
		}

		if (isInterviewSelected != null && !finalMeritListForm.isInterviewPresentForCourse() && finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()) {
			searchCriteria = searchCriteria
					
					+ " and "  +
					"  (admAppln.id  in ( select interviewResult.admAppln.id  from InterviewResult interviewResult  where "
					+ "  interviewResult.interviewProgramCourse.id in"
					+ "  (select  max(interviewProgramCourse.id) from InterviewProgramCourse interviewProgramCourse where"
					+ "  interviewProgramCourse.course.id = "
					+ finalMeritListForm.getCourseId()
					+ "  and interviewProgramCourse.isActive = true "
					+ " and interviewProgramCourse.year = "
					+ finalMeritListForm.getYear() + ")"
					+ "  and interviewResult.interviewStatus.id !="+CMSConstants.INTERVIEW_FAIL_ID+")or admAppln.isPreferenceUpdated=1 or admAppln.isBypassed=1)";
		}else{
			if (isInterviewSelected != null && !finalMeritListForm.isInterviewPresentForCourse()) {
				searchCriteria = searchCriteria
						
						+ " and "  +
						"  (admAppln.id  in ( select interviewResult.admAppln.id  from InterviewResult interviewResult  where "
						+ "  interviewResult.interviewProgramCourse.program.id in"
						+ "  (select  max(interviewProgramCourse.program.id) from InterviewProgramCourse interviewProgramCourse where"
						+ "  interviewProgramCourse.program.id = "
						+ finalMeritListForm.getProgramId()
						+ "  and interviewProgramCourse.isActive = true "
						+ " and interviewProgramCourse.year = "
						+ finalMeritListForm.getYear() + ")"
						+ "  and interviewResult.interviewStatus.id !="+CMSConstants.INTERVIEW_FAIL_ID+")or admAppln.isPreferenceUpdated=1 or admAppln.isBypassed=1)";
			}
		}

		if (isSelected != null) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.isSelected = " + isSelected;
		}
		
		String finalMeritListqQuery = "  from AdmAppln admAppln  inner join admAppln.personalData personalData "
				+ "  left outer join personalData.caste left outer join personalData.religion"
				+ " left outer join personalData.religionSection "
				+ statusCriteria
				+ " where   " + searchCriteria+" and admAppln.isCancelled=0";
		log.info("exit of getFinalMeritListSelectedQuery of FinalMeritListSearchHelper class.");
		return finalMeritListqQuery;
	}
	
	
	
	
	/**
	 * Constructs Final merit list search query.
	 * @param finalMeritListForm
	 * @param isSelected
	 * @param isInterviewSelected
	 * @return
	 */
	public static String getFinalMeritListSearchQuery(
			FinalMeritListForm finalMeritListForm, Boolean isSelected,
			Boolean isInterviewSelected) {
		log.info("entering into getFinalMeritListSearchQuery of FinalMeritListSearchHelper class.");
		String searchCriteria = "";

		String statusCriteria = "";
		
		if(finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()){
			if(isSelected == null ) {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.id =   "
					+ finalMeritListForm.getCourseId();
			} else {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.id =   "
				+ finalMeritListForm.getCourseId();
			}
		}else{
			if(isSelected == null ) {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.program.id =   "
					+ finalMeritListForm.getProgramId();
			} else {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.program.id =   "
				+ finalMeritListForm.getProgramId();
			}
		}
		
		if (finalMeritListForm.getCasteCategoryId()!=null && 
				finalMeritListForm.getCasteCategoryId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.caste.id ="
					+ finalMeritListForm.getCasteCategoryId();
		}

		if (finalMeritListForm.getReligionId()!=null 
							&& finalMeritListForm.getReligionId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.religion.id ="
					+ finalMeritListForm.getReligionId();
		}

		if (finalMeritListForm.getSubReligionId()!=null &&
				finalMeritListForm.getSubReligionId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.religionSection.id ="
					+ finalMeritListForm.getSubReligionId();
		}

		if (finalMeritListForm.getNationalityId()!=null 
				&& finalMeritListForm.getNationalityId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.nationality.id ="
					+ finalMeritListForm.getNationalityId();
		}

		if (finalMeritListForm.getResidentCategoryId()!=null && 
				finalMeritListForm.getResidentCategoryId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.residentCategory.id ="
					+ finalMeritListForm.getResidentCategoryId();
		}
		
		
		if (finalMeritListForm.getSportsPerson() != null  ) {
			String sportsPerson = " and admAppln.personalData.isSportsPerson = " 
				+ finalMeritListForm.getSportsPerson() ;
			searchCriteria = searchCriteria + sportsPerson;
		}
		
		if (finalMeritListForm.getHandicapped() != null ) {
			String handicapped = " and admAppln.personalData.isHandicapped = "  
					+ finalMeritListForm.getHandicapped() ;
			
		
			searchCriteria = searchCriteria + handicapped;
		}

		if (finalMeritListForm.getGender() != null
				&& finalMeritListForm.getGender().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.gender =" + "'"
					+ finalMeritListForm.getGender() + "'";
		}

		if (finalMeritListForm.getBelongsTo() != null) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.ruralUrban =" + "'"
					+ finalMeritListForm.getBelongsTo() + "'";
		}

		if (finalMeritListForm.getYear()!=null 
				&& finalMeritListForm.getYear().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.appliedYear = "
					+ finalMeritListForm.getYear();
		}
		
//			searchCriteria = searchCriteria + " and "
//					+ " (admAppln.isApproved = null or admAppln.isApproved = false) ";

		if (finalMeritListForm.getUniversityId()!=null 
				&& finalMeritListForm.getUniversityId().trim().length() > 0) {
			searchCriteria = searchCriteria
					+ " and  ednQualification.docChecklist.isPreviousExam = true and  ednQualification.university.id = "
					+ finalMeritListForm.getUniversityId();
			statusCriteria = statusCriteria
					+ "  join personalData.ednQualifications ednQualification ";
		}

		if (finalMeritListForm.getInstituteId()!=null 
				&& finalMeritListForm.getInstituteId().trim().length() > 0) {
			searchCriteria = searchCriteria
					+ " and  ednQualification.docChecklist.isPreviousExam = true and  ednQualification.college.id = "
					+ finalMeritListForm.getInstituteId();

			if (finalMeritListForm.getUnivercity() !=null 
					&& finalMeritListForm.getUnivercity().trim().length() == 0) {
				statusCriteria = statusCriteria
						+ "  join personalData.ednQualifications ednQualification ";
			}
		}

		if (finalMeritListForm.getWeightageFrom()!=null 
				&& finalMeritListForm.getWeightageFrom().trim().length() > 0) {
			Double weightageFrom = Double.valueOf(finalMeritListForm
					.getWeightageFrom());
			searchCriteria = searchCriteria + " and "
					+ " admAppln.totalWeightage  >= " + weightageFrom;
		}

		if (finalMeritListForm.getWeightageTo()!=null && 
				finalMeritListForm.getWeightageTo().trim().length() > 0) {
			Double weightageTo = Double.valueOf(finalMeritListForm
					.getWeightageTo());
			searchCriteria = searchCriteria + " and "
					+ " admAppln.totalWeightage  <= " + weightageTo;
		}
		 if(finalMeritListForm.getApplnNo()!=null && !finalMeritListForm.getApplnNo().isEmpty()){
			 searchCriteria = searchCriteria + " and admAppln.applnNo = " + finalMeritListForm.getApplnNo();
		 }
		
		if (isInterviewSelected != null && !finalMeritListForm.isInterviewPresentForCourse() && finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()) {
			searchCriteria = searchCriteria
					
					+ " and "  +
					"  (admAppln.id  in ( select interviewResult.admAppln.id  from InterviewResult interviewResult  where "
					+ "  interviewResult.interviewProgramCourse.id in"
					+ "  (select  max(interviewProgramCourse.id) from InterviewProgramCourse interviewProgramCourse where"
					+ "  interviewProgramCourse.course.id = "
					+ finalMeritListForm.getCourseId()
					+ "  and interviewProgramCourse.isActive = true "
					+ " and interviewProgramCourse.year = "
					+ finalMeritListForm.getYear() + ")"
					+ "  and interviewResult.interviewStatus.id !="+CMSConstants.INTERVIEW_FAIL_ID+") or admAppln.isPreferenceUpdated=1 or admAppln.isBypassed=1)";
		}else{
			if (isInterviewSelected != null && !finalMeritListForm.isInterviewPresentForCourse()) {
				searchCriteria = searchCriteria
						
						+ " and "  +
						"  (admAppln.id  in ( select interviewResult.admAppln.id  from InterviewResult interviewResult  where "
						+ "  interviewResult.interviewProgramCourse.id in"+" (select  interviewProgramCourse.id " +
						"  from InterviewProgramCourse interviewProgramCourse" +
						"  where  interviewProgramCourse.program.id = "+ finalMeritListForm.getProgramId() +
						"  and interviewProgramCourse.isActive = true " +
						"  and interviewProgramCourse.year = "+finalMeritListForm.getYear() 
						+" and interviewProgramCourse.sequence = (select  max( interviewProgramCourse1.sequence)" +
						"  from InterviewProgramCourse interviewProgramCourse1" +
						"  where  interviewProgramCourse1.program.id = "+finalMeritListForm.getProgramId() 
						+"  and interviewProgramCourse1.isActive = true  and interviewProgramCourse1.year ="+finalMeritListForm.getYear()+" ))"
						+ " and interviewResult.interviewStatus.id !="+CMSConstants.INTERVIEW_FAIL_ID+")or admAppln.isPreferenceUpdated=1  or admAppln.isBypassed=1)";
			}
		}

		if (isSelected != null) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.isSelected = " + isSelected;
		}

		String finalMeritListqQuery = "  from AdmAppln admAppln  inner join admAppln.personalData personalData "
				+ "  left outer join personalData.caste left outer join personalData.religion"
				+ " left outer join personalData.religionSection "
				+ statusCriteria
				+ " where   " + searchCriteria+" and admAppln.isCancelled=0";
		log.info("exit of getFinalMeritListSearchQuery of FinalMeritListSearchHelper class.");
		return finalMeritListqQuery +" order by admAppln.applnNo";
	}
	
	
	/**
	 * Constructs Final merit list search query for approval.
	 * @param finalMeritListForm
	 * @param isSelected
	 * @param isInterviewSelected
	 * @return
	 */
	public static String getFinalMeritListApproveSearchQuery(
			FinalMeritListForm finalMeritListForm, Boolean isSelected,
			Boolean isInterviewSelected) {
		log.info("entering into getFinalMeritListSearchQuery of FinalMeritListSearchHelper class.");
		String searchCriteria = "";

		String statusCriteria = "";
		
		if(finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()){
			if(isSelected == null ) {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.id =   "
					+ finalMeritListForm.getCourseId();
			} else {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.id =   "
				+ finalMeritListForm.getCourseId();
			}
		}else{
			if(isSelected == null ) {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.program.id =   "
					+ finalMeritListForm.getProgramId();
			} else {
				searchCriteria = searchCriteria + " admAppln.courseBySelectedCourseId.program.id =   "
				+ finalMeritListForm.getProgramId();
			}
		}
		
		if (finalMeritListForm.getCasteCategoryId()!=null && 
				finalMeritListForm.getCasteCategoryId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.caste.id ="
					+ finalMeritListForm.getCasteCategoryId();
		}

		if (finalMeritListForm.getReligionId()!=null 
							&& finalMeritListForm.getReligionId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.religion.id ="
					+ finalMeritListForm.getReligionId();
		}

		if (finalMeritListForm.getSubReligionId()!=null &&
				finalMeritListForm.getSubReligionId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.religionSection.id ="
					+ finalMeritListForm.getSubReligionId();
		}

		if (finalMeritListForm.getNationalityId()!=null 
				&& finalMeritListForm.getNationalityId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.nationality.id ="
					+ finalMeritListForm.getNationalityId();
		}

		if (finalMeritListForm.getResidentCategoryId()!=null && 
				finalMeritListForm.getResidentCategoryId().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.residentCategory.id ="
					+ finalMeritListForm.getResidentCategoryId();
		}
		
		
		if (finalMeritListForm.getSportsPerson() != null  ) {
			String sportsPerson = " and admAppln.personalData.isSportsPerson = " 
				+ finalMeritListForm.getSportsPerson() ;
			searchCriteria = searchCriteria + sportsPerson;
		}
		
		if (finalMeritListForm.getHandicapped() != null ) {
			String handicapped = " and admAppln.personalData.isHandicapped = "  
					+ finalMeritListForm.getHandicapped() ;
			
		
			searchCriteria = searchCriteria + handicapped;
		}

		if (finalMeritListForm.getGender() != null
				&& finalMeritListForm.getGender().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.gender =" + "'"
					+ finalMeritListForm.getGender() + "'";
		}

		if (finalMeritListForm.getBelongsTo() != null) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.personalData.ruralUrban =" + "'"
					+ finalMeritListForm.getBelongsTo() + "'";
		}

		if (finalMeritListForm.getYear()!=null 
				&& finalMeritListForm.getYear().trim().length() > 0) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.appliedYear = "
					+ finalMeritListForm.getYear();
		}
		
//			searchCriteria = searchCriteria + " and "
//					+ " (admAppln.isApproved = null or admAppln.isApproved = false) ";

		if (finalMeritListForm.getUniversityId()!=null 
				&& finalMeritListForm.getUniversityId().trim().length() > 0) {
			searchCriteria = searchCriteria
					+ " and  ednQualification.docChecklist.isPreviousExam = true and  ednQualification.university.id = "
					+ finalMeritListForm.getUniversityId();
			statusCriteria = statusCriteria
					+ "  join personalData.ednQualifications ednQualification ";
		}

		if (finalMeritListForm.getInstituteId()!=null 
				&& finalMeritListForm.getInstituteId().trim().length() > 0) {
			searchCriteria = searchCriteria
					+ " and  ednQualification.docChecklist.isPreviousExam = true and  ednQualification.college.id = "
					+ finalMeritListForm.getInstituteId();

			if (finalMeritListForm.getUnivercity() !=null 
					&& finalMeritListForm.getUnivercity().trim().length() == 0) {
				statusCriteria = statusCriteria
						+ "  join personalData.ednQualifications ednQualification ";
			}
		}

		if (finalMeritListForm.getWeightageFrom()!=null 
				&& finalMeritListForm.getWeightageFrom().trim().length() > 0) {
			Double weightageFrom = Double.valueOf(finalMeritListForm
					.getWeightageFrom());
			searchCriteria = searchCriteria + " and "
					+ " admAppln.totalWeightage  >= " + weightageFrom;
		}

		if (finalMeritListForm.getWeightageTo()!=null && 
				finalMeritListForm.getWeightageTo().trim().length() > 0) {
			Double weightageTo = Double.valueOf(finalMeritListForm
					.getWeightageTo());
			searchCriteria = searchCriteria + " and "
					+ " admAppln.totalWeightage  <= " + weightageTo;
		}
		 if(finalMeritListForm.getApplnNo()!=null && !finalMeritListForm.getApplnNo().isEmpty()){
			 searchCriteria = searchCriteria + " and admAppln.applnNo = " + finalMeritListForm.getApplnNo();
		 }

		if (isInterviewSelected != null && !finalMeritListForm.isInterviewPresentForCourse() && finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()) {
			searchCriteria = searchCriteria
					
					+ " and "  +
					"  (admAppln.id  in ( select interviewResult.admAppln.id  from InterviewResult interviewResult  where "
					+ "  interviewResult.interviewProgramCourse.id in"
					+ "  (select  max(interviewProgramCourse.id) from InterviewProgramCourse interviewProgramCourse where"
					+ "  interviewProgramCourse.course.id = "
					+ finalMeritListForm.getCourseId()
					+ "  and interviewProgramCourse.isActive = true "
					+ " and interviewProgramCourse.year = "
					+ finalMeritListForm.getYear() + ")"
					+ "  and interviewResult.interviewStatus.id !="+CMSConstants.INTERVIEW_FAIL_ID+") or admAppln.isPreferenceUpdated=1 or admAppln.isBypassed=1)";
		}else{
			if (isInterviewSelected != null && !finalMeritListForm.isInterviewPresentForCourse()) {
				searchCriteria = searchCriteria
						
						+ " and "  +
						"  (admAppln.id  in ( select interviewResult.admAppln.id  from InterviewResult interviewResult  where "
						+ "  interviewResult.interviewProgramCourse.program.id in"
						+ "  (select  max(interviewProgramCourse.program.id) from InterviewProgramCourse interviewProgramCourse where"
						+ "  interviewProgramCourse.program.id = "
						+ finalMeritListForm.getProgramId()
						+ "  and interviewProgramCourse.isActive = true "
						+ " and interviewProgramCourse.year = "
						+ finalMeritListForm.getYear() + ")"
						+ "  and interviewResult.interviewStatus.id !="+CMSConstants.INTERVIEW_FAIL_ID+")or admAppln.isPreferenceUpdated=1  or admAppln.isBypassed=1)";
			}
		}

		if (isSelected != null) {
			searchCriteria = searchCriteria + " and "
					+ " admAppln.isSelected = " + isSelected;
		}

		String finalMeritListqQuery = "  from AdmAppln admAppln  inner join admAppln.personalData personalData "
				+ "  left outer join personalData.caste left outer join personalData.religion"
				+ " left outer join personalData.religionSection "
				+ statusCriteria
				+ " where   " + searchCriteria+" and admAppln.isFinalMeritApproved=0";
		log.info("exit of getFinalMeritListSearchQuery of FinalMeritListSearchHelper class.");
		return finalMeritListqQuery;
	}
	
	/**
	 * Get the nationalities TO list.
	 * @return
	 * @throws Exception
	 */
	public static List<NationalityTO> getNationalities()throws Exception {
		log.info("entering into getNationalities of FinalMeritListSearchHelper class.");
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<Nationality> nationBOs=txn.getNationalities();
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		List<NationalityTO> nationTOs=helper.convertNationalityBOtoTO(nationBOs);
		log.info("exit of getNationalities of FinalMeritListSearchHelper class.");
		return nationTOs;
	}

	/**
	 * 
	 * @param finalMeritListBO
	 * @return
	 * @throws Exception
	 */
	public static Map<Integer, Integer> getSelectedCountCoursewise(
			List finalMeritListBO) throws Exception {
		
		Map<Integer, Integer> selectedMap = new HashMap<Integer, Integer>();
		
		if(finalMeritListBO !=null){
			Iterator intakeItr = finalMeritListBO.iterator();
			int count = 0;
			
			while (intakeItr.hasNext()) {

				Object[] coursewiseIntake = (Object[]) intakeItr.next();
				
				AdmAppln admAppln = (AdmAppln) coursewiseIntake[0];
				
				if(selectedMap.containsKey(Integer.valueOf(admAppln.getCourse().getId()))){
					count = selectedMap.get(admAppln.getCourse().getId());
					count = count +1;
					selectedMap.put(Integer.valueOf(admAppln.getCourse().getId()), Integer.valueOf(count));
				}else{
					
					selectedMap.put(Integer.valueOf(admAppln.getCourse().getId()), Integer.valueOf(1));
				}
			}
		}	
		return selectedMap;
	}
}
