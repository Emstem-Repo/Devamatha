package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Grade;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewResultDetail;
import com.kp.cms.bo.admin.InterviewStatus;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.forms.admission.InterviewBatchEntryForm;
import com.kp.cms.to.admission.InterviewResultDetailTO;
import com.kp.cms.to.admission.InterviewResultTO;

public class InterviewBatchEntryHelper {
	
	/**
	 * Method to convert BO to TO
	 * @param applicantDetails
	 * @param interviewTypeId
	 * @param interviewSubroundId
	 * @return
	 */
	public List<InterviewResultTO> convertBotoTo(List applicantDetails, String interviewTypeId, String interviewSubroundId, int interviewersPerPanel) {
		List<InterviewResultTO> applicantDetailsList = new ArrayList<InterviewResultTO>();
		if (applicantDetails != null) {
			Iterator applicantDetailsItr = applicantDetails.iterator();

			while (applicantDetailsItr.hasNext()) {
				Object[] searchResults = (Object[]) applicantDetailsItr.next();
				InterviewResultTO applicantSearch = new InterviewResultTO();
				InterviewResult interviewResult = null;
				Set<InterviewResultDetail> interviewResultDetailSet = null;
				List<InterviewResultDetailTO> iResultDetailTOList = new ArrayList<InterviewResultDetailTO>();
				applicantSearch.setApplicationId(searchResults[0].toString());
				applicantSearch.setApplicationNo(searchResults[1].toString());
				applicantSearch.setAppliedYear(searchResults[2].toString());
				if(searchResults[4] == null && searchResults[5] ==null){
					applicantSearch.setApplicantName(searchResults[3].toString());
				}else if(searchResults[4] == null){
					applicantSearch.setApplicantName(searchResults[3].toString() +" "+ searchResults[5].toString());
				}else{
					applicantSearch.setApplicantName(searchResults[3].toString() +" "+ searchResults[4].toString() +" "+ searchResults[5].toString());
				}
				
				applicantSearch.setCourseId(searchResults[6].toString());
				applicantSearch.setInterviewProgramCourseId(interviewTypeId);
				if(interviewSubroundId != null && !interviewSubroundId.isEmpty()){
					applicantSearch.setInterviewSubroundId(Integer.parseInt(interviewSubroundId));
				}
				if( searchResults[7] != null ){
					interviewResult = (InterviewResult) searchResults[7];
				}
				
				if( interviewResult!=null && interviewResult.getInterviewStatus() != null ){
					applicantSearch.setInterviewStatusId(String.valueOf(interviewResult.getInterviewStatus().getId()));
				}else{
					applicantSearch.setInterviewStatusId("0");
				}
				InterviewResultDetailTO iResultDetailTO;
				if( interviewResult!=null && interviewResult.getInterviewResultDetails() !=null){
					interviewResultDetailSet = interviewResult.getInterviewResultDetails();
					
					Iterator<InterviewResultDetail> interviewResultDetailItr = interviewResultDetailSet.iterator();
					while (interviewResultDetailItr.hasNext()) {
						InterviewResultDetail resultDetail = (InterviewResultDetail) interviewResultDetailItr.next();
						
						if( resultDetail !=null && resultDetail.getGrade()!=null && resultDetail.getInterviewResult()!=null){
							iResultDetailTO = new InterviewResultDetailTO();
							
							iResultDetailTO.setId(resultDetail.getId());
							iResultDetailTO.setGradeObtainedId(resultDetail.getGrade().getId());
							iResultDetailTO.setInterviewResultId(resultDetail.getInterviewResult().getId());
							iResultDetailTOList.add(iResultDetailTO);
							applicantSearch.setInterviewResultDetail(iResultDetailTOList);
						}
					}
				}else{
					iResultDetailTO = new InterviewResultDetailTO();
					iResultDetailTO.setId(0);
					iResultDetailTO.setGradeObtainedId(0);
					iResultDetailTO.setInterviewResultId(0);
					iResultDetailTOList.add(iResultDetailTO);
					applicantSearch.setInterviewResultDetail(iResultDetailTOList);
				}
				int gradeListSize = 1;
				
				if (interviewResult!=null && interviewResult.getInterviewResultDetails()!=null && !interviewResult.getInterviewResultDetails().isEmpty()){
					gradeListSize = interviewResult.getInterviewResultDetails().size();
				}
				
				if(gradeListSize < interviewersPerPanel ){
					for (int i = gradeListSize; i < interviewersPerPanel; i++) {
						iResultDetailTO = new InterviewResultDetailTO();
						iResultDetailTO.setId(0);
						iResultDetailTO.setGradeObtainedId(0);
						iResultDetailTO.setInterviewResultId(0);
						iResultDetailTOList.add(iResultDetailTO);
						applicantSearch.setInterviewResultDetail(iResultDetailTOList);
					}
				}
				
				if( interviewResult!=null && interviewResult.getComments() !=null && !interviewResult.getComments().isEmpty()){
					applicantSearch.setComments(interviewResult.getComments());
				}else{
					applicantSearch.setComments("");
				}
				
				if( interviewResult != null){
					applicantSearch.setId(interviewResult.getId());
				}
				applicantDetailsList.add(applicantSearch);
			}
		}	
		return applicantDetailsList;
	}
	
	/**
	 * Method to convert TO to BO
	 * @param interviewResults
	 * @param userId
	 * @return
	 */
	public List<InterviewResult> convertTotoBo(List<InterviewResultTO> interviewResults, String userId) {
		List<InterviewResult> applicantDetailsList = new ArrayList<InterviewResult>();
		if (interviewResults != null) {
			Iterator<InterviewResultTO> interviewResultsItr = interviewResults.iterator();

			while (interviewResultsItr.hasNext()) {
				InterviewResultTO interviewResultTO = interviewResultsItr.next();
				
				if( interviewResultTO.getInterviewResultDetail() != null && interviewResultTO.getInterviewStatusId() !=null && !interviewResultTO.getInterviewResultDetail().isEmpty() && !interviewResultTO.getInterviewStatusId().equals("0")){
					InterviewResult interviewResult = new InterviewResult();
					List<InterviewResultDetailTO> interviewResultDetailList = new ArrayList<InterviewResultDetailTO>();
					InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
					Set<InterviewResultDetail> interviewResultDetailSet = new HashSet<InterviewResultDetail>();
					interviewProgramCourse.setId(Integer.valueOf(interviewResultTO.getInterviewProgramCourseId()));
					
					if(interviewResultTO.getInterviewSubroundId() != 0){
						InterviewSubRounds interviewSubRounds = new InterviewSubRounds();
						interviewSubRounds.setId(interviewResultTO.getInterviewSubroundId());
						interviewResult.setInterviewSubRounds(interviewSubRounds);
					}
					
					InterviewStatus interviewStatus = new InterviewStatus();
					interviewStatus.setId(Integer.valueOf(interviewResultTO.getInterviewStatusId()));
					
					AdmAppln admAppln = new AdmAppln();
					admAppln.setId(Integer.valueOf(interviewResultTO.getApplicationId()));
					
					if(interviewResultTO.getInterviewResultDetail() !=null && !interviewResultTO.getInterviewResultDetail().isEmpty()){
						interviewResultDetailList = interviewResultTO.getInterviewResultDetail();
					}
					
					if( interviewResultDetailList != null){
						Iterator<InterviewResultDetailTO> resultDetailItr = interviewResultDetailList.iterator();
						
						while (resultDetailItr.hasNext()) {
							InterviewResultDetailTO resultDetailTO = (InterviewResultDetailTO) resultDetailItr.next();
							
							if( resultDetailTO.getGradeObtainedId() != 0){
								Grade grade = new Grade();
								grade.setId(resultDetailTO.getGradeObtainedId());
								
								InterviewResultDetail iResultDetail = new InterviewResultDetail();
								if (resultDetailTO.getId() > 0) {
									iResultDetail.setId(resultDetailTO.getId());
								}
								iResultDetail.setGrade(grade);
								
								interviewResultDetailSet.add(iResultDetail);
							}
						}
					}
					if(interviewResultDetailSet != null && !interviewResultDetailSet.isEmpty()){
						if(interviewResultTO.getId()>0){
							interviewResult.setId(interviewResultTO.getId());
						}
						interviewResult.setInterviewResultDetails(interviewResultDetailSet);
						interviewResult.setAdmAppln(admAppln);
						interviewResult.setInterviewProgramCourse(interviewProgramCourse);
						interviewResult.setInterviewStatus(interviewStatus);
						interviewResult.setComments(interviewResultTO.getComments());
						if(interviewResultTO.getId() == 0){
							interviewResult.setCreatedBy(userId);
							interviewResult.setCreatedDate(new Date());
						}
						interviewResult.setModifiedBy(userId);
						interviewResult.setLastModifiedDate(new Date());
						interviewResult.setIsActive(true);
						
						applicantDetailsList.add(interviewResult);
					}					
				}
			}
		}	
		return applicantDetailsList;
	}

	/**
	 * Method to convert form values to TO
	 * @param interviewBatchEntryForm
	 * @return
	 */
	public InterviewResultTO convertFormtoTo(InterviewBatchEntryForm interviewBatchEntryForm) {
		
		InterviewResultTO interviewBatchEntryTO =  new InterviewResultTO();
		
		interviewBatchEntryTO.setAppliedYear(interviewBatchEntryForm.getAppliedYear());
		interviewBatchEntryTO.setCourseId(interviewBatchEntryForm.getCourseId());
		interviewBatchEntryTO.setInterviewTypeId(Integer.parseInt(interviewBatchEntryForm.getInterviewTypeId()));
		if(interviewBatchEntryForm.getInterviewSubroundId()!= null && !interviewBatchEntryForm.getInterviewSubroundId().isEmpty() && Integer.parseInt(interviewBatchEntryForm.getSubroundCount())!= 0){
			interviewBatchEntryTO.setInterviewSubroundId(Integer.parseInt(interviewBatchEntryForm.getInterviewSubroundId()));
		}
		interviewBatchEntryTO.setInterviewDate(interviewBatchEntryForm.getInterviewDate());
		interviewBatchEntryTO.setStartTimeHours(interviewBatchEntryForm.getStartingTimeHours());
		interviewBatchEntryTO.setStartTimeMins(interviewBatchEntryForm.getStartingTimeMins());
		interviewBatchEntryTO.setEndTimeHours(interviewBatchEntryForm.getEndingTimeHours());
		interviewBatchEntryTO.setEndTimeMins(interviewBatchEntryForm.getEndingTimeMins());
		
		return interviewBatchEntryTO;
	}
}