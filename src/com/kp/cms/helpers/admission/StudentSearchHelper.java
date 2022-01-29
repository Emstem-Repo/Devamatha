package com.kp.cms.helpers.admission;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.forms.admission.StudentSearchForm;
import com.kp.cms.to.admission.StudentSearchTO;
import com.kp.cms.transactions.admission.ICandidateSearchTxnImpl;
import com.kp.cms.transactions.admission.IStudentSearchTransaction;
import com.kp.cms.transactionsimpl.admission.CandidateSearchTxnImpl;
import com.kp.cms.transactionsimpl.admission.StudentSearchTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Helper class for the Interview selection.
 *
 */
public class StudentSearchHelper {

	private static final Log log = LogFactory.getLog(StudentSearchHelper.class);
	/**
	 * Converts from BO to To
	 * @param studentSearchBo
	 * @param studentSearchForm
	 * @return
	 */
	public static List<StudentSearchTO> convertBoToTo(List studentSearchBo,
			StudentSearchForm studentSearchForm) {
		log.info("entering into convertBoToTo of StudentSearchHelper class.");
		List<StudentSearchTO> studentSearchTo = new ArrayList<StudentSearchTO>();
		Set<Integer> admIdSet=new HashSet<Integer>();
		//added for photo available check
		if (studentSearchBo != null) {
			List<Integer> admIdsForSearch=new ArrayList<Integer>();
			if(!studentSearchBo.isEmpty()){
			Iterator itr= studentSearchBo.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				AdmAppln admAppln = (AdmAppln)object[0];
				admIdsForSearch.add(Integer.valueOf(admAppln.getId()));
			}
		}
			ICandidateSearchTxnImpl studentSearchTransactionImpl=CandidateSearchTxnImpl.getInstance();
			List<Integer> admId= studentSearchTransactionImpl.getStudentListWithPhotos(admIdsForSearch);
			
		//ends
			
			Iterator studentSearchIterator = studentSearchBo.iterator();

			while (studentSearchIterator.hasNext()) {
				Object[] searchResults = (Object[]) studentSearchIterator.next();
				StudentSearchTO studentSearch = new StudentSearchTO();
				int noOfSubRounds=0;
				int resultSize=0;
				boolean subroundExists=false;
				AdmAppln admAppln = (AdmAppln) searchResults[0];
// added to set the photo available or not
				if(admId.contains(admAppln.getId())){
			studentSearch.setPhotoAvaialble(true);	
				}else
					studentSearch.setPhotoAvaialble(false);	
				//ends
				
				studentSearch
						.setApplicationId(((Integer) admAppln.getApplnNo())
								.toString());
				studentSearch.setSelectedCrsId(String.valueOf(admAppln.getCourseBySelectedCourseId().getId()));
				studentSearch.setSelectedCrsName(admAppln.getCourseBySelectedCourseId().getName());
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
				if(admAppln.getPersonalData().getGender()!=null) {
					studentSearch.setApplicantGender(admAppln.getPersonalData().getGender());
				}
				
				studentSearch.setApplicantName(applicantName.toString());
				studentSearch.setApplicantDOB(CommonUtil.getStringDate(admAppln
						.getPersonalData().getDateOfBirth()));
				if (admAppln.getTotalWeightage() != null) {
					studentSearch.setApplicantTotalWeightage(admAppln
							.getTotalWeightage().toString());
				}
				
				if(admAppln.getJournalNo()!= null){
					studentSearch.setJournalNo(admAppln.getJournalNo());
				}
				if(admAppln.getExamCenter()!= null && admAppln.getExamCenter().getCenter()!= null){
					studentSearch.setExamCenterName(admAppln.getExamCenter().getCenter());
				}
				if(admAppln.getAdmStatus()!= null){
					int size= admAppln.getAdmStatus().length();
					if(size>50)
					{
					String ShortStatus= admAppln.getAdmStatus().substring(0, 50);
					studentSearch.setShortStatus(ShortStatus.trim());
					}
					else
					{
						studentSearch.setShortStatus(admAppln.getAdmStatus().trim());	
					}
					
					studentSearch.setStatus(admAppln.getAdmStatus().trim());
					
				}
				studentSearch.setAdmApplnId(((Integer) admAppln.getId())
						.toString());
				if ((studentSearchForm.getPreviousInterViewType() != null && !studentSearchForm
						.getPreviousInterViewType().equals(""))) {
					int interviewType = Integer.valueOf(studentSearchForm
							.getPreviousInterViewType());
					//this is for subrounds of previous interview
					InterviewResult selectedResult=null;
					Set<InterviewResult> interviewResults = admAppln
							.getInterviewResults();
					if(admAppln.getInterviewResults()!=null){
						Iterator<InterviewResult> failItr=admAppln.getInterviewResults().iterator();
						while (failItr.hasNext()) {
							InterviewResult failResult = (InterviewResult) failItr.next();
							if(failResult.getInterviewProgramCourse().getId() == interviewType && failResult.getInterviewStatus()!=null && failResult.getInterviewStatus().getId()!=2)
								resultSize++;
						}
						
					}
					Iterator<InterviewResult> interviewResultsIterator = interviewResults
							.iterator();
					while (interviewResultsIterator.hasNext()) {
						InterviewResult interviewResult = (InterviewResult) interviewResultsIterator
								.next();
						
//						
//						if(interviewResult.getInterviewProgramCourse()!=null && 
//								interviewResult.getInterviewProgramCourse().getInterviewSubRoundses()!=null 
//								&& !interviewResult.getInterviewProgramCourse().getInterviewSubRoundses().isEmpty()){
////							Iterator<InterviewSubRounds> roundItr=interviewResult.getInterviewProgramCourse().getInterviewSubRoundses().iterator();
//							subroundExists=true;
//
//							
//							
//						}
						
						if (interviewResult.getInterviewProgramCourse().getId() == interviewType) {
							selectedResult=interviewResult;
							if(interviewResult.getInterviewStatus()!=null){
							if (interviewResult.getInterviewStatus().getId() == 3) {
								studentSearch.setInWaitingList(interviewResult
										.getInterviewStatus().getId());
							} else {
								studentSearch.setInWaitingList(interviewResult
										.getInterviewStatus().getId());
							}
							}

						}

					}
					
					if(selectedResult!=null){
						if(selectedResult.getInterviewProgramCourse()!=null && 
								selectedResult.getInterviewProgramCourse().getInterviewSubRoundses()!=null 
								&& !selectedResult.getInterviewProgramCourse().getInterviewSubRoundses().isEmpty()){
							Iterator<InterviewSubRounds> roundItr=selectedResult.getInterviewProgramCourse().getInterviewSubRoundses().iterator();

							while (roundItr.hasNext()) {
								InterviewSubRounds subRounds = (InterviewSubRounds) roundItr.next();
								if(subRounds.getIsActive()!=null && subRounds.getIsActive()){
									noOfSubRounds++;
									subroundExists=true;
								}
							}
							
							
						}
					}
					
					
				}
				if (searchResults.length == 3) {
					if (searchResults[2] != null
							&& searchResults[2] instanceof InterviewSelected) {
						InterviewSelected interviewSelected = (InterviewSelected) searchResults[2];
						studentSearch
								.setInterviewSelectedId(((Integer) interviewSelected
										.getId()).toString());
					}
				} else if (searchResults.length > 3) {
					if (searchResults[3] != null
							&& searchResults[3] instanceof InterviewSelected) {
						InterviewSelected interviewSelected = (InterviewSelected) searchResults[3];
						studentSearch
								.setInterviewSelectedId(((Integer) interviewSelected
										.getId()).toString());
					}
				}

				if (!admAppln.getCandidatePrerequisiteMarks().isEmpty()) {
					Iterator<CandidatePrerequisiteMarks> candidatesPrerequisiteIterator = admAppln
							.getCandidatePrerequisiteMarks().iterator();
					while (candidatesPrerequisiteIterator.hasNext()) {
						CandidatePrerequisiteMarks candidatePrerequisiteMarks = (CandidatePrerequisiteMarks) candidatesPrerequisiteIterator
								.next();
						studentSearch
								.setPreRequisiteType(candidatePrerequisiteMarks
										.getPrerequisite().getName());
						studentSearch
								.setPrerequisiteMarks(candidatePrerequisiteMarks
										.getPrerequisiteMarksObtained() == null ? ""
										: candidatePrerequisiteMarks
												.getPrerequisiteMarksObtained()
												.toString());
					}
				}
				
				studentSearch.setInterviewProgCrsId(studentSearchForm.getSearchedIntrvwType());
				if(!admIdSet.contains(admAppln.getId())){
				if(subroundExists){
				if(noOfSubRounds>0 && noOfSubRounds==resultSize)
					studentSearchTo.add(studentSearch);
					admIdSet.add(admAppln.getId());
				}else{
					studentSearchTo.add(studentSearch);
					admIdSet.add(admAppln.getId());
				}
				}
			}
		}
		log.info("exit of getSelectedStudents of StudentSearchHelper class.");
		return studentSearchTo;
	}

	/**
	 * Constructs selected student search query.
	 * @param studentSearchForm
	 * @return
	 */
	public static String getSelectedStudentSearch(
			StudentSearchForm studentSearchForm) {
		log.info("entering into getSelectedStudentSearch of StudentSearchHelper class.");
		StringBuffer statusCriteria = new StringBuffer(commonSearch(studentSearchForm));
		String ednJoin = "";
		if (studentSearchForm.getUniversity().trim().length() > 0
				|| studentSearchForm.getInstitute().trim().length() > 0
				|| studentSearchForm.getPercentageFrom().trim().length() > 0
				|| studentSearchForm.getPercentageTo().trim().length() > 0) {

			ednJoin = "	join personalData.ednQualifications educationQualification ";
//			statusCriteria = statusCriteria.append("and educationQualification.docChecklist.isPreviousExam = true ");
		}
		String searchCriteria = "from AdmAppln admAppln inner join admAppln.personalData personalData inner join admAppln.interviewSelecteds  interviewSelected with interviewSelected.isCardGenerated = false  and "
				+ " interviewSelected.interviewProgramCourse.id  = "
				+ studentSearchForm.getSearchedIntrvwType()
				+ ednJoin
				+ "where " +

				statusCriteria + "   and admAppln.isSelected = false";
		log.info("exit of getSelectedStudentSearch of StudentSearchHelper class.");
		return searchCriteria;

	}

	/**
	 * Constructs common search query
	 * @param studentSearchForm
	 * @return
	 */
	private static String commonByPassSearch(StudentSearchForm studentSearchForm) {
		StringBuffer searchCriteria = new StringBuffer();
		log.info("entering into commonSearch of StudentSearchHelper class.");
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
			String residentCategory = " and admAppln.personalData.residentCategory.name like "
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
			String blodGroup = " and admAppln.personalData.bloodGroup = " + "'"
					+ studentSearchForm.getBloodGroup() + "'";
			searchCriteria.append(blodGroup);
		}

		if (studentSearchForm.getSportsPerson() != null) {
			String sportsPerson = " and admAppln.personalData.isSportsPerson = "
					+ studentSearchForm.getSportsPerson();
			searchCriteria.append(sportsPerson);
		}

		if (studentSearchForm.getHandicapped() != null) {
			String handicapped = " and admAppln.personalData.isHandicapped = "
					+ studentSearchForm.getHandicapped();

			searchCriteria.append(handicapped);
		}

		if (studentSearchForm.getWeightage().trim().length() > 0) {
			String weightage = " and admAppln.totalWeightage >= "
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

		if (studentSearchForm.getInstitute().trim().length() > 0) {
			String institute = "";

			institute = "   and educationQualification.college.name like "
					+ "'" + studentSearchForm.getInstitute() + "%" + "'";

			searchCriteria.append(institute);
		}

		if (studentSearchForm.getPercentageFrom().trim().length() > 0) {
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getPercentageFrom()));
			String percentage = "";
			percentage = "   and educationQualification.percentage >= "
					+ percentageValue;
			searchCriteria.append(percentage);
		}

		// searchCriteria = searchCriteria + " and "
		// + " (admAppln.isApproved = null or admAppln.isApproved = false) ";

		if (studentSearchForm.getPercentageTo().trim().length() > 0) {
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getPercentageTo()));
			String percentage = "";
			percentage = "   and educationQualification.percentage <= "
					+ percentageValue;

			searchCriteria.append(percentage);
		}
		log.info("exit of commonSearch of StudentSearchHelper class.");
		return searchCriteria.toString();
	}
	
	
	
	/**
	 * Constructs common search query
	 * @param studentSearchForm
	 * @return
	 */
	private static String commonSearch(StudentSearchForm studentSearchForm) {
		StringBuffer searchCriteria = new StringBuffer();
		log.info("entering into commonSearch of StudentSearchHelper class.");
		if (studentSearchForm.getCourseId().trim().length() > 0) {
			String course = " admAppln.courseBySelectedCourseId.id = "
					+ studentSearchForm.getCourseId();
			searchCriteria.append(course);
		}

		if (studentSearchForm.getYear().trim().length() > 0) {
			String appliedYear ="";
			if(studentSearchForm.getCourseId().trim().length() > 0){
				appliedYear = "and admAppln.appliedYear = "
					+ studentSearchForm.getYear();
			}else{
				appliedYear = " admAppln.appliedYear = "
					+ studentSearchForm.getYear();
			}
			searchCriteria.append(appliedYear);
		}

		if (studentSearchForm.getNationalityId().trim().length() > 0) {
			String nationality = " and admAppln.personalData.nationality.id ="
					+ "'" + studentSearchForm.getNationalityId() + "'";
			searchCriteria.append(nationality);
		}

		if (studentSearchForm.getBirthCountry().trim().length() > 0) {
			String birthCountry = " and admAppln.personalData.countryByCountryId.id ="
					+ "'" + studentSearchForm.getBirthCountry() + "'";
			searchCriteria.append(birthCountry);
		}

		if (studentSearchForm.getBirthState().trim().length() > 0) {
			String birthState = " and admAppln.personalData.stateByStateId.id ="
					+ "'" + studentSearchForm.getBirthState() + "'";
			searchCriteria.append(birthState);
		}

		if (studentSearchForm.getResidentCategoryId().trim().length() > 0) {
			String residentCategory = "and admAppln.personalData.residentCategory.id ="
					+ studentSearchForm.getResidentCategoryId();
			searchCriteria.append(residentCategory);
		}

		if (studentSearchForm.getReligionId().trim().length() > 0) {
			String religionName = " and admAppln.personalData.religion.id = "
					+ "'" + studentSearchForm.getReligionId() +  "'";
			searchCriteria.append(religionName);
		}

		if (studentSearchForm.getSubReligionId().trim().length() > 0) {
			String subReligionName = "  and admAppln.personalData.religionSection.id = "
					+ "'" + studentSearchForm.getSubReligionId() +"'";
			searchCriteria.append(subReligionName);
		}

		if (studentSearchForm.getCasteCategoryId().trim().length() > 0) {
			String casteCategory = "  and admAppln.personalData.caste.id = "
					+ "'" + studentSearchForm.getCasteCategoryId() + "'";
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

		if (studentSearchForm.getSportsPerson() != null) {
			String sportsPerson = " and admAppln.personalData.isSportsPerson = "
					+ studentSearchForm.getSportsPerson();
			searchCriteria.append(sportsPerson);
		}

		if (studentSearchForm.getHandicapped() != null) {
			String handicapped = " and admAppln.personalData.isHandicapped = "
					+ studentSearchForm.getHandicapped();

			searchCriteria.append(handicapped);
		}

		if (studentSearchForm.getWeightage().trim().length() > 0) {
			String weightage = " and admAppln.totalWeightage >= "
					+ new BigDecimal(studentSearchForm.getWeightage());
			searchCriteria.append(weightage);
		}

		if (studentSearchForm.getApplicantName().trim().length() > 0) {
			String applicantName = " and admAppln.personalData.firstName  like  "
					+ "'" + studentSearchForm.getApplicantName() + "%" + "'";
			searchCriteria.append(applicantName);
		}

		if (studentSearchForm.getUniversity().trim().length() > 0) {
			String university = "   and educationQualification.university.id= "
					+ "'" + studentSearchForm.getUniversity() +"'";
			searchCriteria.append(university);

		}

		if (studentSearchForm.getInstitute().trim().length() > 0) {
			String institute = "";

			institute = "   and educationQualification.college.id = "
					+ "'" + studentSearchForm.getInstitute() +  "'";

			searchCriteria.append(institute);
		}

		if (studentSearchForm.getPercentageFrom().trim().length() > 0) {
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getPercentageFrom()));
			String percentage = "";
			percentage = "   and educationQualification.percentage >= "
					+ studentSearchForm.getPercentageFrom().trim();
			searchCriteria.append(percentage);
		}

		// searchCriteria = searchCriteria + " and "
		// + " (admAppln.isApproved = null or admAppln.isApproved = false) ";

		if (studentSearchForm.getPercentageTo().trim().length() > 0) {
			/*BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getPercentageTo()));*/
			String percentage = "";
			percentage = "   and educationQualification.percentage <= "
					+ studentSearchForm.getPercentageTo().trim();

			searchCriteria.append(percentage);
		}
		/*Avoids listing Cancelled students in selected students list and to be selected list */
		searchCriteria.append(" and (admAppln.isCancelled=false or admAppln.isCancelled=null) ");
		log.info("exit of commonSearch of StudentSearchHelper class.");
		return searchCriteria.toString();
	}

	/**
	 * constructs bypass interview selection search criteria query.
	 * @param studentSearchForm
	 * @param isSelected
	 * @return
	 */
	public static String getBypassInterviewSelectionSearchCriteria(
			StudentSearchForm studentSearchForm, boolean isSelected) {
		log.info("entering into getBypassInterviewSelectionSearchCriteria of StudentSearchHelper class.");
		String statusCriteria = commonByPassSearch(studentSearchForm);
		String searchCriteria;
		String ednJoin = "";
		if (studentSearchForm.getUniversity().trim().length() > 0
				|| studentSearchForm.getInstitute().trim().length() > 0
				|| studentSearchForm.getPercentageFrom().trim().length() > 0
				|| studentSearchForm.getPercentageTo().trim().length() > 0) {

			ednJoin = "	join personalData.ednQualifications educationQualification ";
			/*statusCriteria = statusCriteria
					+ "and educationQualification.docChecklist. = true ";*/
		}
		if (studentSearchForm.getInterviewDate()!=null
				&& !studentSearchForm.getInterviewDate().isEmpty()) {
			ednJoin = "	join admAppln.interviewCards icard ";
			statusCriteria = statusCriteria
					+ " and icard.interview.date = '"+CommonUtil.ConvertStringToSQLDate(studentSearchForm.getInterviewDate())+"'";
		}
		if (isSelected == false) {
			statusCriteria = statusCriteria + " and admAppln.isSelected = "
					+ isSelected + " and  (admAppln.isBypassed =" + isSelected
					+ " or admAppln.isBypassed = null) ";
		} else {
			statusCriteria = statusCriteria + " and admAppln.isSelected = "
					+ isSelected + " and admAppln.isBypassed =" + isSelected;
		}
		
		searchCriteria = " from AdmAppln admAppln "
				+ " inner join admAppln.personalData personalData " + ednJoin
				+ " where " + statusCriteria;
		log.info("exit of getBypassInterviewSelectionSearchCriteria of StudentSearchHelper class.");
		return searchCriteria;
	}

	/**
	 * constructs selection search criteria query.
	 * @param studentSearchForm
	 * @return
	 */
	public static String getSelectionSearchCriteria(
			StudentSearchForm studentSearchForm) throws Exception {
		log.info("entering into getSelectionSearchCriteria of StudentSearchHelper class.");
		IStudentSearchTransaction studentSearchTransactionImpl = new StudentSearchTransactionImpl();
		String statusCriteria = commonSearch(studentSearchForm);

		String searchCriteria;
		String ednJoin = "";
		if (studentSearchForm.getUniversity().trim().length() > 0
				|| studentSearchForm.getInstitute().trim().length() > 0
				|| studentSearchForm.getPercentageFrom().trim().length() > 0
				|| studentSearchForm.getPercentageTo().trim().length() > 0) {

			ednJoin = "	join personalData.ednQualifications educationQualification ";
			/*statusCriteria = statusCriteria
					+ "and educationQualification.docChecklist.isPreviousExam = true ";*/
		}
		
		// Check for previous round exists ot not, if, get the ID and check cleared or not
		//use txn.getpreviousRoundId(studentSearchForm.getSearchedIntrvwType())
		//use the id in below query for result check
		int previousRoundId=studentSearchTransactionImpl.getpreviousRoundId(studentSearchForm.getSearchedIntrvwType());
		if (studentSearchForm.getSearchedIntrvwType()!=null && !StringUtils.isEmpty(studentSearchForm.getSearchedIntrvwType()) && previousRoundId!=0) {
			studentSearchForm.setPreviousInterViewType(String.valueOf(previousRoundId));
			searchCriteria = " from AdmAppln admAppln "
					+ " inner join admAppln.personalData personalData "
					+ ednJoin
					+ " 	inner join admAppln.interviewResults interviewResult with interviewResult.interviewProgramCourse.id = "
					+ previousRoundId
					+

					"	and interviewResult.interviewStatus.id != 2"
					+ " where "
					+ statusCriteria
					+ "   and admAppln.isSelected = false  and (admAppln.isBypassed = false" 
							+ " or admAppln.isBypassed = null)"
					+ "	and admAppln.id not in ( select interviewSelected.admAppln.id from  InterviewSelected interviewSelected where "
					+ "    interviewSelected.interviewProgramCourse.id = "
					+ studentSearchForm.getSearchedIntrvwType() + "   )"
					+ "  group by admAppln.id ";

		} else {
			// otherwise
			if (studentSearchForm.getSearchedIntrvwType()!=null && !StringUtils.isEmpty(studentSearchForm.getSearchedIntrvwType())) {
				String interviewType = "  and interviewProgramCourse.id  = "
						+ studentSearchForm.getSearchedIntrvwType();
				statusCriteria = statusCriteria + interviewType;
			}

			if (studentSearchForm.getCourseId()!=null && !StringUtils.isEmpty(studentSearchForm.getCourseId())) {
				searchCriteria = " 	from AdmAppln admAppln "
						+ " 	inner join admAppln.personalData personalData inner join admAppln.courseBySelectedCourseId.interviewProgramCourses interviewProgramCourse "
						+ ednJoin
						+ " where interviewProgramCourse.course.id = admAppln.courseBySelectedCourseId.id and  interviewProgramCourse.year = admAppln.appliedYear  and "
						+ statusCriteria
						+ "   and admAppln.isSelected = false  and  (admAppln.isBypassed = false"
						+ " or admAppln.isBypassed = null)"
						+ " and admAppln.id not in "
						+ " 	( select interviewSelected.admAppln.id from  "
						+ "  	InterviewSelected interviewSelected where interviewSelected.admAppln.courseBySelectedCourseId.id = "
						+ studentSearchForm.getCourseId()
						+ " and interviewSelected.admAppln.appliedYear = "
						+ studentSearchForm.getYear() + ")";
			}else{
				searchCriteria = " 	from AdmAppln admAppln "
					+ " 	inner join admAppln.personalData personalData inner join admAppln.courseBySelectedCourseId.interviewProgramCourses interviewProgramCourse "
					+ ednJoin
					+ " where interviewProgramCourse.course.id = admAppln.courseBySelectedCourseId.id and  interviewProgramCourse.year = admAppln.appliedYear  and "
					+ statusCriteria
					+ "   and admAppln.isSelected = false  and  (admAppln.isBypassed = false"
					+ " or admAppln.isBypassed = null)"
					+ " and admAppln.id not in "
					+ " 	( select interviewSelected.admAppln.id from  "
					+ "  	InterviewSelected interviewSelected where interviewSelected.admAppln.courseBySelectedCourseId.program.id = "
					+ studentSearchForm.getProgramId()
					+ " and interviewSelected.admAppln.appliedYear = "
					+ studentSearchForm.getYear() + ")";
			}

		}
		
		if(studentSearchForm.getAppliedDateFrom().trim().length() > 0) {
			String appDateFrom = " and admAppln.createdDate >= '"+CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAppliedDateFrom().trim())+"'";
			searchCriteria = searchCriteria + appDateFrom;
		}
		if(studentSearchForm.getAppliedDateTo().trim().length() > 0) {
			String appDateTo = " and admAppln.createdDate <= '"+CommonUtil.ConvertStringToSQLDate(studentSearchForm.getAppliedDateTo().trim())+"'";
			searchCriteria = searchCriteria + appDateTo;
		}
		
		log.info("exit of getSelectionSearchCriteria of StudentSearchHelper class.");
		return searchCriteria;
	}
}