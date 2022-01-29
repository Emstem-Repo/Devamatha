package com.kp.cms.helpers.reports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.CandidateSearchForm;
import com.kp.cms.to.admission.CandidateSearchTO;
import com.kp.cms.utilities.CommonUtil;

public class AnyStageInAdmissionHelper {
	/**
	 * @param studentSearchForm
	 * @return
	 * This method will build final query 
	 */
	public static String getSelectionSearchCriteria(
			CandidateSearchForm studentSearchForm) {

		String statusCriteria = commonSearch(studentSearchForm);
		
		String searchCriteria;
		
		String ednJoin = "";
		if (studentSearchForm.getUniversity().trim().length() > 0
				|| studentSearchForm.getInstitute().trim().length() > 0
				|| studentSearchForm.getMarksObtained().trim().length() > 0) {

			ednJoin = "	join personalData.ednQualifications educationQualification ";
			statusCriteria = statusCriteria
					+ "and educationQualification.docChecklist.isPreviousExam = true ";
		}
		
		if (studentSearchForm.getInterviewType().trim().length() > 0) {
			ednJoin = ednJoin + "  inner join admAppln.interviewSelecteds interviewselected "; 
			if (studentSearchForm.getInterviewResult().trim().length() == 0) {
				ednJoin = ednJoin +	" left join admAppln.interviewResults interviewResults "; 
			}
			
		}
		if (studentSearchForm.getInterviewStartDate().trim().length() > 0 | studentSearchForm.getInterviewEndDate().trim().length() > 0) {
			ednJoin = ednJoin + "  inner join admAppln.interviewCards interviewCards "; 
		}
		
		if (studentSearchForm.getInterviewResult().trim().length() > 0) {
			ednJoin = ednJoin + "  inner join admAppln.interviewResults interviewResults "; 
		}
		
		
			searchCriteria = " from AdmAppln admAppln inner join admAppln.personalData personalData inner join admAppln.students students" + ednJoin;
			if(!StringUtils.isEmpty(statusCriteria)) {
				searchCriteria = searchCriteria + " where " + statusCriteria;
			}
		return searchCriteria;
	}
	/**
	 * @param studentSearchForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String commonSearch(CandidateSearchForm studentSearchForm) {

		String searchCriteria = "";
		boolean containSearchCriteria = false;
		if (studentSearchForm.getProgramTypeId().trim().length() > 0) {
			containSearchCriteria=true;
			String course = "  admAppln.courseBySelectedCourseId.program.programType.id = "
					+ studentSearchForm.getProgramTypeId();
			searchCriteria = searchCriteria + course;
		}
		
		if (studentSearchForm.getProgramId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String course = "  admAppln.courseBySelectedCourseId.program.id = "
					+ studentSearchForm.getProgramId();
			searchCriteria = searchCriteria + course;
		}
		if (studentSearchForm.getCourseId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String course = " admAppln.courseBySelectedCourseId.id = "
					+ studentSearchForm.getCourseId();
			searchCriteria = searchCriteria + course;
		}
		if (studentSearchForm.getYear().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String appliedYear = "admAppln.appliedYear = "
					+ studentSearchForm.getYear();
			searchCriteria = searchCriteria + appliedYear;
		}
		if (studentSearchForm.getNationalityId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String nationality = "admAppln.personalData.nationality.name like"
					+ "'" + studentSearchForm.getNationalityId() + "%" + "'";
			searchCriteria = searchCriteria + nationality;
		}
		if (studentSearchForm.getBirthCountry().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String birthCountry = "admAppln.personalData.countryByCountryId.name like"
					+ "'" + studentSearchForm.getBirthCountry() + "%" + "'";
			searchCriteria = searchCriteria + birthCountry;
		}
		if (studentSearchForm.getBirthState().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String birthState = "admAppln.personalData.stateByStateId.name like"
					+ "'" + studentSearchForm.getBirthState() + "%" + "'";
			searchCriteria = searchCriteria + birthState;
		}
		if (studentSearchForm.getResidentCategoryId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String residentCategory = "admAppln.personalData.residentCategory.name like "
					+ "'"
					+ studentSearchForm.getResidentCategoryId()
					+ "%"
					+ "'";
			searchCriteria = searchCriteria + residentCategory;
		}
		if (studentSearchForm.getReligionId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String religionName = "admAppln.personalData.religion.name like "
					+ "'" + studentSearchForm.getReligionId() + "%" + "'";
			searchCriteria = searchCriteria + religionName;
		}
		if (studentSearchForm.getSubReligionId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String subReligionName = "admAppln.personalData.religionSection.name like "
					+ "'" + studentSearchForm.getSubReligionId() + "%" + "'";
			searchCriteria = searchCriteria + subReligionName;
		}
		if (studentSearchForm.getCasteCategoryId().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String casteCategory = "admAppln.personalData.caste.name like "
					+ "'" + studentSearchForm.getCasteCategoryId() + "%" + "'";
			searchCriteria = searchCriteria + casteCategory;
		}
		if (studentSearchForm.getBelongsTo().equals('R')
				|| studentSearchForm.getBelongsTo().equals('U')) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String belongsTo = "admAppln.personalData.ruralUrban = " + "'"
					+ studentSearchForm.getBelongsTo() + "'";
			searchCriteria = searchCriteria + belongsTo;
		}
		if (studentSearchForm.getGender().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String gender = "admAppln.personalData.gender = " + "'"
					+ studentSearchForm.getGender() + "'";
			searchCriteria = searchCriteria + gender;
		}
		if (studentSearchForm.getBloodGroup().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String blodGroup = "admAppln.personalData.bloodGroup = " + "'"
					+ studentSearchForm.getBloodGroup() + "'";
			searchCriteria = searchCriteria + blodGroup;
		}
		if (studentSearchForm.getWeightage().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String weightage = "admAppln.totalWeightage >= "
					+ new BigDecimal(studentSearchForm.getWeightage());
			searchCriteria = searchCriteria + weightage;
		}
		
		if (studentSearchForm.getWeightageTO().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String weightage = "admAppln.totalWeightage <= "
					+ new BigDecimal(studentSearchForm.getWeightageTO());
			searchCriteria = searchCriteria + weightage;
		}
		
		if (studentSearchForm.getApplicantName().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String applicantName = "admAppln.personalData.firstName  like  "
					+ "'" + studentSearchForm.getApplicantName() + "%" + "'";
			searchCriteria = searchCriteria + applicantName;
		}
		if (studentSearchForm.getUniversity().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String university = "educationQualification.university.name like "
					+ "'" + studentSearchForm.getUniversity() + "%" + "'";
			searchCriteria = searchCriteria + university;

		}
		if (studentSearchForm.getInstitute().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			String institute = "";

			institute = "educationQualification.college.name like "
					+ "'" + studentSearchForm.getInstitute() + "%" + "'";

			searchCriteria = searchCriteria + institute;
		}
		if (studentSearchForm.getMarksObtained().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getMarksObtained()));
			String percentage = "";
			percentage = "educationQualification.percentage >= "
					+ percentageValue;
			searchCriteria = searchCriteria + percentage;
		}

		if (studentSearchForm.getMarksObtainedTO().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}

			containSearchCriteria=true;
			BigDecimal percentageValue = BigDecimal.valueOf(Float
					.valueOf(studentSearchForm.getMarksObtainedTO()));
			String percentage = "";
			percentage = "educationQualification.percentage <= "
					+ percentageValue;
			searchCriteria = searchCriteria + percentage;
		}

		
		if (studentSearchForm.getInterviewType().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			
			containSearchCriteria=true;
			String interviewType = "  interviewselected.interviewProgramCourse.id = "
					+ studentSearchForm.getInterviewType();
			searchCriteria = searchCriteria + interviewType;
		}
		
		if (studentSearchForm.getInterviewResult().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String interviewType = " interviewResults.interviewStatus.id = "
					+ studentSearchForm.getInterviewResult();
			String interviewResult = " and interviewResults.interviewProgramCourse.id = "
				+ studentSearchForm.getInterviewType();
			searchCriteria = searchCriteria + interviewType + interviewResult;
		}
		
		if (studentSearchForm.getInterviewStartDate().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String interviewStartDate = " interviewCards.interview.date >=' "
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getInterviewStartDate())+"'";
			searchCriteria = searchCriteria + interviewStartDate;
		}
		if (studentSearchForm.getInterviewEndDate().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String interviewEndDate = " interviewCards.interview.date <=' "
					+ CommonUtil.ConvertStringToSQLDate(studentSearchForm.getInterviewEndDate())+"'";
			searchCriteria = searchCriteria + interviewEndDate;
		}
		if(studentSearchForm.getStatus().trim().length() > 0){
			if(studentSearchForm.getStatus().equals("selected")) {
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" admAppln.isSelected = true  and admAppln.isApproved = true and students.isAdmitted = false ";			
			}
			else if(studentSearchForm.getStatus().equals("rejected")) {
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" admAppln.isSelected = false and admAppln.isCancelled = false ";			
			} else if(studentSearchForm.getStatus().equals("canceled")){
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" admAppln.isCancelled = true ";
			}else if(studentSearchForm.getStatus().equals("admitted")){
				if(containSearchCriteria) {
					searchCriteria = searchCriteria + "  and  ";
				}

				searchCriteria = searchCriteria +" admAppln.isSelected = true  and admAppln.isApproved = true and students.isAdmitted = true ";
			}
		}
		searchCriteria = searchCriteria + " order by admAppln.applnNo ";
		return searchCriteria;	
	}
	/**
	 * @param studentSearchBo
	 * @return
	 * This classes is used to convert BO's to TO's
	 * @throws Exception 
	 */
	public static List<CandidateSearchTO> convertBoToTo(List studentSearchBo) throws Exception {

		List<CandidateSearchTO> candidateSearchTOList = new ArrayList<CandidateSearchTO>();
		try{
			if (studentSearchBo != null) {
				Iterator itrstudentBO = studentSearchBo.iterator();			
				while (itrstudentBO.hasNext()) {
					Object[] object = (Object[]) itrstudentBO.next();
					AdmAppln admAppln = (AdmAppln)object[0];
					
						CandidateSearchTO candidateSearchTO = new CandidateSearchTO();		
						if(admAppln.getApplnNo()!=0){
							candidateSearchTO.setApplnNo(admAppln.getApplnNo());					
						}					
						if(admAppln.getTotalWeightage()!=null){
							candidateSearchTO.setTotalWeightage(admAppln.getTotalWeightage().doubleValue());				
						}
						if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getFirstName()!=null){
							candidateSearchTO.setName(admAppln.getPersonalData().getFirstName());
							if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getMiddleName()!=null){
								candidateSearchTO.setName(admAppln.getPersonalData().getFirstName()+" "+admAppln.getPersonalData().getMiddleName());
							}
							if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getLastName()!=null){
								candidateSearchTO.setName(admAppln.getPersonalData().getFirstName()+" "+admAppln.getPersonalData().getLastName());
							}
							if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getMiddleName()!=null && admAppln.getPersonalData().getLastName()!=null){
								candidateSearchTO.setName(admAppln.getPersonalData().getFirstName()+" "+admAppln.getPersonalData().getMiddleName()+" "+admAppln.getPersonalData().getLastName());
							}	
						}
						if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getDateOfBirth()!=null){
							candidateSearchTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(admAppln.getPersonalData().getDateOfBirth().toString(), "yyyy-mm-dd", "dd-mm-yyyy"));
						}
						if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getGender()!=null){
							candidateSearchTO.setGender(admAppln.getPersonalData().getGender());
						}
						if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getBloodGroup()!=null){
							candidateSearchTO.setBloodGroup(admAppln.getPersonalData().getBloodGroup());
						}
						if(admAppln.getChallanRefNo()!=null){
							candidateSearchTO.setChallanNo(admAppln.getChallanRefNo());
						}
						if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getEmail()!=null){
							candidateSearchTO.setEmail(admAppln.getPersonalData().getEmail());
						}	
						double marksObtained = 0;
						double totalMarks = 0;
						double percentage = 0;
						if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getEdnQualifications()!=null){
							Iterator<EdnQualification> ednIterator = admAppln.getPersonalData().getEdnQualifications().iterator();
							while (ednIterator.hasNext()) {
								EdnQualification ednQualification = ednIterator.next();
								if(ednQualification.getDocChecklist() != null && 
								ednQualification.getDocChecklist().getIsPreviousExam()!=null && ednQualification.getDocChecklist().getIsPreviousExam()){
									if(ednQualification.getMarksObtained()!=null){
										candidateSearchTO.setObtainedMark(String.valueOf(ednQualification.getMarksObtained()));
										marksObtained = ednQualification.getMarksObtained().doubleValue();
									}
									if(ednQualification.getTotalMarks()!=null){
										candidateSearchTO.setTotalmark(String.valueOf(ednQualification.getTotalMarks()));
										totalMarks = ednQualification.getTotalMarks().doubleValue();
									}
									if(marksObtained != 0 && totalMarks != 0){
									percentage = marksObtained/totalMarks*100;
									}
									candidateSearchTO.setPercentage(String.valueOf(Integer.valueOf((int)percentage).intValue()));
								}
							}
							
						}
						String isAdmitted = null;
						if(admAppln.getStudents()!=null){
							Iterator<Student> iterator = admAppln.getStudents().iterator();
							while (iterator.hasNext()) {
								Student student = iterator.next();
								if(student.getIsAdmitted()!=null){
									isAdmitted = student.getIsAdmitted().toString();
								}
							}
						}
						
						
						//Below condition is used to know the current status of the application
						String status="";
						/*if(admAppln.getIsSelected()!=null && admAppln.getIsCancelled()!=null &&
						isAdmitted != null && !new Boolean(isAdmitted) &&
						!admAppln.getIsSelected()&& !admAppln.getIsCancelled()){*/
						if(admAppln.getIsSelected()!=null && admAppln.getIsCancelled()!=null &&
								!admAppln.getIsSelected()&& !admAppln.getIsCancelled()){
							
							Set<InterviewSelected> interviewSelectedSet = admAppln.getInterviewSelecteds();
							Set<InterviewResult> interviewresultSet = admAppln.getInterviewResults();
							boolean subroundexist=false;
							boolean cardgenerated=false;
							InterviewSelected oldInterviewSelected = null;												
							if(interviewSelectedSet!=null && !interviewSelectedSet.isEmpty() && interviewSelectedSet.size()>1){							
								//Used to get the last interviewselected ID
								//Gets the last main round Id
								//Mainly used to know the last round status. If no then get the old round status
								List<InterviewSelected> interviewSelectedList = new ArrayList<InterviewSelected>();
								interviewSelectedList.addAll(interviewSelectedSet);
								//Sort all main rounds based on the interviewselected Id
								Collections.sort(interviewSelectedList);							
								InterviewSelected lastMainRoundRecord = interviewSelectedList.get(interviewSelectedSet.size()-1);
								int lastMainRoundBOId = 0;
								int lastMainRoundId = 0;							
								if(lastMainRoundRecord!=null){
									lastMainRoundBOId = lastMainRoundRecord.getId();
									if(lastMainRoundRecord.getInterviewProgramCourse()!=null){
									lastMainRoundId = lastMainRoundRecord.getInterviewProgramCourse().getId();
									}
								}										
								Iterator<InterviewSelected> intvwIterator = interviewSelectedList.iterator();
								while (intvwIterator.hasNext()) {
									InterviewSelected interviewSelected = intvwIterator.next();												
									if(interviewSelected.getInterviewProgramCourse()!= null){									
										int currentMainRoundId = interviewSelected.getId();									
										if(currentMainRoundId == lastMainRoundBOId){															
										Iterator<InterviewResult> resIterator = interviewresultSet.iterator();
										while (resIterator.hasNext()) {
											InterviewResult interviewResult = (InterviewResult) resIterator.next();
											//check subround exists or not
											if(interviewResult.getInterviewProgramCourse()!=null && interviewResult.getInterviewProgramCourse().getId()==lastMainRoundId){
												subroundexist=true;
												status=CMSConstants.RESULT_UNDER_PROCESS;
												
												if(interviewResult.getInterviewProgramCourse()!=null){
													if(interviewResult.getInterviewProgramCourse().getName()!=null){
														candidateSearchTO.setStatus(interviewResult.getInterviewProgramCourse().getName() + " " + status);
													}
												}							
											}
										}									
										if(!subroundexist){
											cardgenerated=interviewSelected.getIsCardGenerated();
											if(cardgenerated)
											{
												//Call for Interview
												status=CMSConstants.CALL_FOR;
												if(interviewSelected.getInterviewProgramCourse()!=null){
													if(interviewSelected.getInterviewProgramCourse().getName()!=null){
														candidateSearchTO.setStatus(status +interviewSelected.getInterviewProgramCourse().getName());
													}
												}														
											}else if(!cardgenerated)
											{
												//get old status 
												status=CMSConstants.RESULT_UNDER_PROCESS;
													
												if(oldInterviewSelected.getInterviewProgramCourse()!=null){												
														if(oldInterviewSelected.getInterviewProgramCourse().getName()!=null){
															candidateSearchTO.setStatus(oldInterviewSelected.getInterviewProgramCourse().getName()+" " + status);
														}
													}													
											}
											}			
										}					
									}				
									//Keeps the old main round status	
									oldInterviewSelected = interviewSelected;
								}
							}
							else if(interviewSelectedSet == null || interviewSelectedSet.isEmpty()){
								//Only application is submitted
								status=CMSConstants.ADMISSION_ADMISSIONSTATUS_APPLICATION_UNDERREVIEW;
								candidateSearchTO.setStatus(status);
							}
							else if(interviewSelectedSet!=null && !interviewSelectedSet.isEmpty() && interviewSelectedSet.size()==1){
								Iterator<InterviewSelected> iterator = interviewSelectedSet.iterator();
								while (iterator.hasNext()) {
									InterviewSelected interviewSelected = iterator.next();
									
									if(interviewSelected.getInterviewProgramCourse()!= null){
										int selectProgcrsID=interviewSelected.getInterviewProgramCourse().getId();
										
										Iterator<InterviewResult> resIterator = interviewresultSet.iterator();
										while (resIterator.hasNext()) {
											InterviewResult interviewResult = (InterviewResult) resIterator.next();
											//check subround exists or not
											if(interviewResult.getInterviewProgramCourse()!=null && interviewResult.getInterviewProgramCourse().getId()==selectProgcrsID){
												subroundexist=true;
												status=CMSConstants.RESULT_UNDER_PROCESS;
												candidateSearchTO.setStatus(status);
											}
										}								
										if(!subroundexist){
											cardgenerated=interviewSelected.getIsCardGenerated();
											if(cardgenerated)
											{
												//Call for Interview
												status=CMSConstants.CALL_FOR;
												
												if(interviewSelected.getInterviewProgramCourse()!=null){
													if(interviewSelected.getInterviewProgramCourse().getName()!=null){
														candidateSearchTO.setStatus(status + interviewSelected.getInterviewProgramCourse().getName());
													}
												}													
											}else if(!cardgenerated)
											{
												//get old status 
												status=CMSConstants.ADMISSION_ADMISSIONSTATUS_APPLICATION_UNDERREVIEW;
												candidateSearchTO.setStatus(status);					
											}
										}
									}			
								}
							}				
						}
						/*else if(admAppln.getIsSelected()!=null && admAppln.getIsApproved()!=null && isAdmitted != null
							&& admAppln.getIsSelected() && admAppln.getIsApproved() && !new Boolean(isAdmitted)){*/
						else if(admAppln.getIsSelected()!=null && admAppln.getIsApproved()!=null 
								&& admAppln.getIsSelected() && admAppln.getIsApproved()){
							status = CMSConstants.SELECTED_FOR_ADMISSION;
							candidateSearchTO.setStatus(status);						
						}
						else if(admAppln.getIsSelected()!=null && admAppln.getIsApproved()==null 
								&& admAppln.getIsSelected()){
							status = CMSConstants.ADMISSION_ADMISSIONSTATUS_APPLICATION_UNDERREVIEW;
							candidateSearchTO.setStatus(status);						
						}
						else if(admAppln.getIsSelected()!=null && admAppln.getIsApproved()!=null 
								&& admAppln.getIsSelected() && !admAppln.getIsApproved()){
							status = CMSConstants.ADMISSION_ADMISSIONSTATUS_APPLICATION_UNDERREVIEW;
							candidateSearchTO.setStatus(status);						
						}
						else if(admAppln.getIsSelected()!=null && admAppln.getIsApproved()!=null && isAdmitted != null
							&& admAppln.getIsSelected() && admAppln.getIsApproved() && Boolean.valueOf(isAdmitted)){
							status = "Admitted";
							candidateSearchTO.setStatus(status);
						}		
						else if(admAppln.getIsSelected()!=null && admAppln.getIsCancelled()!=null && !admAppln.getIsSelected() && admAppln.getIsCancelled()){
							status = CMSConstants.ADMISSION_ADMISSIONSTATUS_APPLICATION_CANCELLED;
							candidateSearchTO.setStatus(status);
						}
					candidateSearchTOList.add(candidateSearchTO);				
					}
				}
	}
		catch(Exception e){
			throw new ApplicationException();
		}
		return candidateSearchTOList;
	
	}
}
