package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantLateralDetails;
import com.kp.cms.bo.admin.ApplicantMarksDetails;
import com.kp.cms.bo.admin.ApplicantRecommendor;
import com.kp.cms.bo.admin.ApplicantTransferDetails;
import com.kp.cms.bo.admin.ApplicantWorkExperience;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidateEntranceDetails;
import com.kp.cms.bo.admin.CandidateMarks;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.Grade;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewResultDetail;
import com.kp.cms.bo.admin.InterviewStatus;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Recommendor;
import com.kp.cms.bo.admin.StudentVehicleDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplicantWorkExperienceTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidateEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePrerequisiteMarksTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.GradeTO;
import com.kp.cms.to.admin.StudentVehicleDetailsTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.to.admission.InterviewSubroundDisplayTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.utilities.CommonUtil;

public class InterviewResultEntryHelper {

	/**
	 * Method to convert AdmApplnBO to TO
	 * 
	 * @param admApplnBO
	 * @return adminAppTO
	 */
	public AdmApplnTO copyPropertiesValue(AdmAppln admApplnBo,HttpServletRequest request) throws Exception {

		AdmApplnTO adminAppTO = null;
		PersonalDataTO personalDataTO = null;
		CourseTO courseTO = null;
		StudentVehicleDetailsTO vehicleDetailsTO = null;
		CandidateEntranceDetailsTO entranceDetailsTO = null;
		List<EdnQualificationTO> ednQualificationList = null;
		List<ApplicantWorkExperienceTO> workExperienceList = null;
		PreferenceTO preferenceTO = null;
		List<ApplnDocTO> documentsList = null;
		List<CandidatePrerequisiteMarksTO> prereqList = null;
		List<InterviewResultTO> interviewResultList = null;

		if (admApplnBo != null) {
			adminAppTO = new AdmApplnTO();

			adminAppTO.setApplicationId(admApplnBo.getId());
			adminAppTO.setRemark(admApplnBo.getRemarks());
			adminAppTO.setApplnNo(admApplnBo.getApplnNo());
			adminAppTO.setChallanRefNo(admApplnBo.getChallanRefNo());
			adminAppTO.setJournalNo(admApplnBo.getJournalNo());
			adminAppTO.setBankBranch(admApplnBo.getBankBranch());
			adminAppTO.setAppliedYear(admApplnBo.getAppliedYear());
			adminAppTO.setDisplaySecondLanguage(admApplnBo.getCourseBySelectedCourseId()
					.getProgram().getIsSecondLanguage());
			adminAppTO.setDisplayExtraDetails(admApplnBo.getCourseBySelectedCourseId()
					.getProgram().getIsExtraDetails());
			adminAppTO.setDisplayMotherTongue(admApplnBo.getCourseBySelectedCourseId()
					.getProgram().getIsMotherTongue());
			adminAppTO.setDisplayLanguageKnown(admApplnBo.getCourseBySelectedCourseId()
					.getProgram().getIsDisplayLanguageKnown());
			adminAppTO.setDisplayTrainingDetails(admApplnBo.getCourseBySelectedCourseId()
					.getProgram().getIsDisplayTrainingCourse());
			adminAppTO.setDisplayAdditionalInfo(admApplnBo.getCourseBySelectedCourseId()
					.getProgram().getIsAdditionalInfo());
			adminAppTO.setDisplayTCDetails(admApplnBo.getCourseBySelectedCourseId().getProgram()
					.getIsTCDetails());
			adminAppTO.setDisplayFamilyBackground(admApplnBo.getCourseBySelectedCourseId()
					.getProgram().getIsFamilyBackground());
			if (admApplnBo.getCourseBySelectedCourseId().getProgram().getIsLateralDetails()
					&& admApplnBo.getCourseBySelectedCourseId().getProgram()
							.getIsTransferCourse()) {
				adminAppTO.setDisplayLateralTransfer(true);
			} else {
				adminAppTO.setDisplayLateralTransfer(false);
			}
			adminAppTO.setDisplayLateralDetails(admApplnBo.getCourseBySelectedCourseId()
					.getProgram().getIsLateralDetails());
			adminAppTO.setDisplayTransferDetails(admApplnBo.getCourseBySelectedCourseId()
					.getProgram().getIsTransferCourse());

			adminAppTO.setTcNo(admApplnBo.getTcNo());
			adminAppTO.setTcDate(CommonUtil.getStringDate(admApplnBo
					.getTcDate()));
			adminAppTO.setMarkscardNo(admApplnBo.getMarkscardNo());
			adminAppTO.setMarkscardDate(CommonUtil.getStringDate(admApplnBo
					.getMarkscardDate()));

			if (admApplnBo.getDate() != null) {
				adminAppTO.setChallanDate(CommonUtil.getStringDate(admApplnBo
						.getDate()));
			}
			if (admApplnBo.getAmount() != null) {
				adminAppTO.setAmount(String.valueOf(admApplnBo.getAmount()
						.doubleValue()));
			}

			if (admApplnBo.getStudentVehicleDetailses() != null
					&& !admApplnBo.getStudentVehicleDetailses().isEmpty()) {
				vehicleDetailsTO = copyVehicleDetails(admApplnBo
						.getStudentVehicleDetailses());
			}
			adminAppTO.setVehicleDetail(vehicleDetailsTO);

			if (admApplnBo.getCandidateEntranceDetailses() != null
					&& !admApplnBo.getCandidateEntranceDetailses().isEmpty()) {
				entranceDetailsTO = copyEntranceDetails(admApplnBo
						.getCandidateEntranceDetailses());
			}
			adminAppTO.setEntranceDetail(entranceDetailsTO);

			if (admApplnBo.getApplicantLateralDetailses() != null
					&& !admApplnBo.getApplicantLateralDetailses().isEmpty()) {
				copyLateralDetails(admApplnBo.getApplicantLateralDetailses(),
						adminAppTO);
			}

			if (admApplnBo.getApplicantTransferDetailses() != null
					&& !admApplnBo.getApplicantTransferDetailses().isEmpty()) {
				copyTransferDetails(admApplnBo.getApplicantTransferDetailses(),
						adminAppTO);
			}

			if (admApplnBo.getPersonalData() != null) {
				personalDataTO = copyPropertiesValue(admApplnBo
						.getPersonalData());
			}
			adminAppTO.setPersonalData(personalDataTO);

			courseTO = copyPropertiesValue(admApplnBo.getCourseBySelectedCourseId());
			adminAppTO.setCourse(courseTO);

			workExperienceList = new ArrayList<ApplicantWorkExperienceTO>();
			if (admApplnBo.getCourseBySelectedCourseId().getIsWorkExperienceRequired()) {
				workExperienceList = copyWorkExperienceValue(admApplnBo
						.getApplicantWorkExperiences());
			}
			adminAppTO.setWorkExperienceList(workExperienceList);
			//compilation problem solved by manu
            //method name changed by manu because two same method return type is different 
			ednQualificationList = copyPropertiesValues(admApplnBo
					.getPersonalData().getEdnQualifications());
			adminAppTO.setEdnQualificationList(ednQualificationList);

			preferenceTO = copyPropertiesValue(admApplnBo
					.getCandidatePreferences());
			if (admApplnBo.getCourseBySelectedCourseId() != null) {
				preferenceTO.setSelectedPrefId(String.valueOf(admApplnBo
						.getCourseBySelectedCourseId().getId()));
			}
			adminAppTO.setPreference(preferenceTO);

			documentsList = copyPropertiesDocValue(admApplnBo.getApplnDocs(),
					admApplnBo.getAppliedYear());
			adminAppTO.setDocumentsList(documentsList);

			prereqList = copyPrerequisiteDetails(admApplnBo
					.getCandidatePrerequisiteMarks());
			adminAppTO.setPrerequisiteTos(prereqList);

			interviewResultList = copyPropertiesIRValue(admApplnBo);
			adminAppTO.setInterviewResultList(interviewResultList);
			String interviewDate=AdmissionFormHandler.getInstance().getInterviewDateOfApplicant(String.valueOf(adminAppTO.getApplnNo()),adminAppTO.getAppliedYear());
			if(interviewDate!=null)
			adminAppTO.setInterviewDate(interviewDate);
			
			if(admApplnBo.getVerifiedBy()!=null && !admApplnBo.getVerifiedBy().isEmpty()){
				adminAppTO.setVerifiedBy(admApplnBo.getVerifiedBy());
			}else{
				
				HttpSession session=request.getSession();
					adminAppTO.setVerifiedBy(session.getAttribute("username").toString());
			}
		}
		return adminAppTO;
	}

	/**
	 * Method to retrieve applicant related work experience
	 * 
	 * @param applicantWorkExperiences
	 * @return
	 */
	private List<ApplicantWorkExperienceTO> copyWorkExperienceValue(
			Set<ApplicantWorkExperience> applicantWorkExperiences)
			throws Exception {
		List<ApplicantWorkExperienceTO> workExpList = new ArrayList<ApplicantWorkExperienceTO>();
		if (applicantWorkExperiences != null
				&& !applicantWorkExperiences.isEmpty()) {
			Iterator<ApplicantWorkExperience> expItr = applicantWorkExperiences
					.iterator();
			while (expItr.hasNext()) {
				ApplicantWorkExperience workExp = (ApplicantWorkExperience) expItr
						.next();
				ApplicantWorkExperienceTO expTo = new ApplicantWorkExperienceTO();
				boolean nullCheck = false;
				if (workExp.getOrganization() != null
						|| workExp.getPosition() != null
						|| workExp.getFromDate() != null
						|| workExp.getToDate() != null
						|| workExp.getSalary() != null
						|| workExp.getReportingTo() != null) {
					expTo.setId(workExp.getId());
					if (workExp.getFromDate() != null) {
						expTo.setFromDate(CommonUtil.getStringDate(workExp
								.getFromDate()));
						nullCheck = true;
					}
					if (workExp.getToDate() != null) {
						expTo.setToDate(CommonUtil.getStringDate(workExp
								.getToDate()));
						nullCheck = true;
					}
					if (workExp.getOrganization() != null
							&& !workExp.getOrganization().isEmpty()) {
						expTo.setOrganization(workExp.getOrganization());
						nullCheck = true;
					}
					if (workExp.getPosition() != null
							&& !workExp.getPosition().isEmpty()) {
						expTo.setPosition(workExp.getPosition());
						nullCheck = true;
					}
					if (!String.valueOf(workExp.getSalary()).equals("null")) {
						expTo.setSalary(String.valueOf(workExp.getSalary()));
						nullCheck = true;
					}
					if (workExp.getReportingTo() != null
							&& !workExp.getReportingTo().isEmpty()) {
						expTo.setReportingTo(workExp.getReportingTo());
						nullCheck = true;
					}
					if (nullCheck) {
						workExpList.add(expTo);
					}
				}
			}
		}
		return workExpList;
	}

	/**
	 * Method to retrieve applicant related vehicle details
	 * 
	 * @param vehicleDetailsSet
	 * @return
	 */
	public StudentVehicleDetailsTO copyVehicleDetails(
			Set<StudentVehicleDetails> vehicleDetailsSet) throws Exception {

		StudentVehicleDetailsTO vehicleTO = null;
		if (vehicleDetailsSet != null) {
			Iterator<StudentVehicleDetails> vehItr = vehicleDetailsSet
					.iterator();
			vehicleTO = new StudentVehicleDetailsTO();
			while (vehItr.hasNext()) {
				StudentVehicleDetails vehDet = (StudentVehicleDetails) vehItr
						.next();
				vehicleTO.setVehicleNo(vehDet.getVehicleNo());
				vehicleTO.setVehicleType(vehDet.getVehicleType());
			}
		}
		return vehicleTO;
	}

	/**
	 * Method to retrieve applicant related Candidate Entrance Details
	 * 
	 * @param candidateEntranceDetailses
	 * @return
	 */
	public CandidateEntranceDetailsTO copyEntranceDetails(
			Set<CandidateEntranceDetails> candidateEntranceDetailses)
			throws Exception {

		CandidateEntranceDetailsTO entranceDetailsTO = null;
		if (candidateEntranceDetailses != null) {
			Iterator<CandidateEntranceDetails> entItr = candidateEntranceDetailses
					.iterator();

			while (entItr.hasNext()) {
				CandidateEntranceDetails candidateEntranceDetails = (CandidateEntranceDetails) entItr
						.next();
				entranceDetailsTO = new CandidateEntranceDetailsTO();
				if (candidateEntranceDetails != null) {

					if (candidateEntranceDetails.getMonthPassing() != null) {
						entranceDetailsTO.setMonthPassing(String
								.valueOf(candidateEntranceDetails
										.getMonthPassing()));
					}
					if (candidateEntranceDetails.getYearPassing() != null) {
						entranceDetailsTO.setYearPassing(String
								.valueOf(candidateEntranceDetails
										.getYearPassing()));
					}

					entranceDetailsTO
							.setEntranceRollNo(candidateEntranceDetails
									.getEntranceRollNo());

					if (candidateEntranceDetails.getMarksObtained() != null)
						entranceDetailsTO
								.setMarksObtained(candidateEntranceDetails
										.getMarksObtained().toString());
					if (candidateEntranceDetails.getTotalMarks() != null)
						entranceDetailsTO
								.setTotalMarks(candidateEntranceDetails
										.getTotalMarks().toString());
				}

			}

		}
		return entranceDetailsTO;
	}

	/**
	 * Method to retrieve applicant related Lateral Details
	 * 
	 * @param applicantLateralDetailses
	 * @param adminAppTO
	 */
	private void copyLateralDetails(
			Set<ApplicantLateralDetails> applicantLateralDetailses,
			AdmApplnTO adminAppTO) throws Exception {
		Iterator<ApplicantLateralDetails> entItr = applicantLateralDetailses
				.iterator();
		adminAppTO.setLateralDetailBos(applicantLateralDetailses);
		List<ApplicantLateralDetailsTO> lateralTos = new ArrayList<ApplicantLateralDetailsTO>();
		ApplicantLateralDetailsTO lateralDetailsTo = null;
		while (entItr.hasNext()) {
			ApplicantLateralDetails entDetails = (ApplicantLateralDetails) entItr
					.next();

			if (entDetails != null) {
				lateralDetailsTo = new ApplicantLateralDetailsTO();
				lateralDetailsTo.setId(entDetails.getId());
				if (entDetails.getAdmAppln() != null) {
					lateralDetailsTo.setAdmApplnId(entDetails.getAdmAppln()
							.getId());
				}
				lateralDetailsTo.setUniversityName(entDetails
						.getUniversityName());
				lateralDetailsTo.setStateName(entDetails.getStateName());
				lateralDetailsTo.setInstituteAddress(entDetails
						.getInstituteAddress());
				lateralDetailsTo.setSemesterNo(entDetails.getSemesterNo());
				lateralDetailsTo.setSemesterName(entDetails.getSemesterName());
				if (entDetails.getMaxMarks() != null) {
					lateralDetailsTo.setMaxMarks(String.valueOf(entDetails
							.getMaxMarks()));
				}
				if (entDetails.getMinMarks() != null) {
					lateralDetailsTo.setMinMarks(String.valueOf(entDetails
							.getMinMarks()));
				}
				if (entDetails.getMarksObtained() != null) {
					lateralDetailsTo.setMarksObtained(String.valueOf(entDetails
							.getMarksObtained()));
				}
				if (entDetails.getYearPass() != null) {
					lateralDetailsTo.setYearPass(String.valueOf(entDetails
							.getYearPass()));
				}
				if (entDetails.getMonthPass() != null) {
					lateralDetailsTo.setMonthPass(String.valueOf(entDetails
							.getMonthPass()));
				}
				lateralTos.add(lateralDetailsTo);
			}
		}
		adminAppTO.setLateralDetails(lateralTos);
	}

	/**
	 * Method to retrieve applicant related Transfer Details
	 * 
	 * @param applicantTransferDetailses
	 * @param adminAppTO
	 */
	private void copyTransferDetails(
			Set<ApplicantTransferDetails> applicantTransferDetailses,
			AdmApplnTO adminAppTO) throws Exception {

		Iterator<ApplicantTransferDetails> entItr = applicantTransferDetailses
				.iterator();
		adminAppTO.setTransferDetailBos(applicantTransferDetailses);
		List<ApplicantTransferDetailsTO> transferTos = new ArrayList<ApplicantTransferDetailsTO>();
		ApplicantTransferDetailsTO transferDetailsTo = null;
		while (entItr.hasNext()) {
			ApplicantTransferDetails entDetails = (ApplicantTransferDetails) entItr
					.next();

			if (entDetails != null) {
				transferDetailsTo = new ApplicantTransferDetailsTO();
				transferDetailsTo.setId(entDetails.getId());
				if (entDetails.getAdmAppln() != null) {
					transferDetailsTo.setAdmApplnId(entDetails.getAdmAppln()
							.getId());
				}

				transferDetailsTo.setUniversityAppNo(entDetails
						.getUniversityAppNo());
				transferDetailsTo.setRegistationNo(entDetails
						.getRegistationNo());
				transferDetailsTo.setInstituteAddr(entDetails
						.getInstituteAddr());
				transferDetailsTo.setArrearDetail(entDetails.getArrearDetail());
				transferDetailsTo.setSemesterName(entDetails.getSemesterName());
				transferDetailsTo.setSemesterNo(entDetails.getSemesterNo());
				if (entDetails.getMaxMarks() != null) {
					transferDetailsTo.setMaxMarks(String.valueOf(entDetails
							.getMaxMarks()));
				}

				if (entDetails.getMinMarks() != null) {
					transferDetailsTo.setMinMarks(String.valueOf(entDetails
							.getMinMarks()));
				}
				if (entDetails.getMarksObtained() != null) {
					transferDetailsTo.setMarksObtained(String
							.valueOf(entDetails.getMarksObtained()));
				}
				if (entDetails.getYearPass() != null) {
					transferDetailsTo.setYearPass(String.valueOf(entDetails
							.getYearPass()));
				}
				if (entDetails.getMonthPass() != null) {
					transferDetailsTo.setMonthPass(String.valueOf(entDetails
							.getMonthPass()));
				}
				transferTos.add(transferDetailsTo);
			}
		}
		adminAppTO.setTransferDetails(transferTos);
	}

	/**
	 * Method to retrieve applicant related Personal Information
	 * 
	 * @param personalDataBO
	 * @return personalDataTO
	 */
	public PersonalDataTO copyPropertiesValue(PersonalData personalData)
			throws Exception {
		PersonalDataTO personalDataTO = null;

		if (personalData != null) {
			personalDataTO = new PersonalDataTO();

			personalDataTO.setFirstName(personalData.getFirstName());
			personalDataTO.setMiddleName(personalData.getMiddleName());
			personalDataTO.setLastName(personalData.getLastName());

			if (personalData.getDateOfBirth() != null) {
				personalDataTO.setDob(CommonUtil.getStringDate(personalData
						.getDateOfBirth()));
			}
			personalDataTO.setBirthPlace(personalData.getBirthPlace());

			if (personalData.getCountryOthers() != null
					&& personalData.getCountryOthers().trim().length() > 0) {
				personalDataTO.setCountryOfBirth(personalData
						.getCountryOthers());
			} else if (personalData.getCountryByCountryId() != null) {
				personalDataTO.setCountryOfBirth(personalData
						.getCountryByCountryId().getName());
			}

			if (personalData.getStateOthers() != null
					&& personalData.getStateOthers().trim().length() > 0) {
				personalDataTO.setStateOfBirth(personalData.getStateOthers());
			} else if (personalData.getStateByStateId() != null) {
				personalDataTO.setStateOfBirth(personalData.getStateByStateId()
						.getName());
			}

			if (personalData.getNationalityOthers() != null
					&& personalData.getNationalityOthers().trim().length() > 0) {
				personalDataTO.setCitizenship(personalData
						.getNationalityOthers());
			} else if (personalData.getNationality() != null) {
				personalDataTO.setCitizenship(personalData.getNationality()
						.getName());
			}

			if (personalData.getResidentCategory() != null) {
				personalDataTO.setResidentCategoryName(personalData
						.getResidentCategory().getName());
			}
			if (personalData.getReligionOthers() != null
					&& personalData.getReligionOthers().trim().length() > 0) {
				personalDataTO
						.setReligionName(personalData.getReligionOthers());
			} else if (personalData.getReligion() != null) {
				personalDataTO.setReligionName(personalData.getReligion()
						.getName());
				personalDataTO.setReligionId(Integer.toString(personalData.getReligion().getId()));
			}
            if(personalData.getDiocese()!=null){
            	personalDataTO.setDioceseName(personalData.getDiocese().getName());
            
            }
            if(personalData.getParish()!=null){
            	personalDataTO.setParishName(personalData.getParish().getName());
            }
			if (personalData.getReligionSectionOthers() != null
					&& personalData.getReligionSectionOthers().trim().length() > 0) {
				personalDataTO.setSubregligionName(personalData
						.getReligionSectionOthers());
			} else if (personalData.getReligionSection() != null) {
				personalDataTO.setSubregligionName(personalData
						.getReligionSection().getName());
			}
			if (personalData.getCasteOthers() != null
					&& personalData.getCasteOthers().trim().length() > 0) {
				personalDataTO.setCasteCategory(personalData.getCasteOthers());
			} else if (personalData.getCaste() != null) {
				personalDataTO.setCasteCategory(personalData.getCaste()
						.getName());
			}
			if(personalData.getRuralUrban()!=null){
				personalDataTO.setRuralUrban(personalData.getRuralUrban());
				personalDataTO.setAreaType(personalData.getRuralUrban());
			}
			personalDataTO.setGender(personalData.getGender());
			if (personalData.getIsSportsPerson() != null) {
				personalDataTO
						.setSportsPerson(personalData.getIsSportsPerson());
			}
			if (personalData.getSportsPersonDescription() != null) {
				personalDataTO.setSportsDescription(personalData
						.getSportsPersonDescription());
			}

			if (personalData.getIsHandicapped() != null) {
				personalDataTO.setHandicapped(personalData.getIsHandicapped());
			}
			if(personalData.getIsNcccertificate()!=null){
				personalDataTO.setNcccertificate(personalData.getIsNcccertificate());
			}
			if(personalData.getIsNcccertificate()!=null){
				personalDataTO.setNcccertificate(personalData.getIsNcccertificate());
			}
			if(personalData.getIsExcervice()!=null){
				personalDataTO.setExservice(personalData.getIsExcervice());
			}
			if(personalData.getIsNcccertificate()!=null && personalData.getIsNcccertificate()){
				personalDataTO.setNccgrades(personalData.getNccgrade());
			}
			else
				personalDataTO.setNccgrades(null);
			if(personalData.getIsNsscertificate()!=null && personalData.getIsNsscertificate()){
				personalDataTO.setNssgrades(personalData.getNssgrade());
			}
			else
				personalDataTO.setNssgrades(null);
			if (personalData.getHandicappedDescription() != null) {
				personalDataTO.setHadnicappedDescription(personalData
						.getHandicappedDescription());
			}

			if (personalData.getSecondLanguage() != null) {
				personalDataTO.setSecondLanguage(personalData
						.getSecondLanguage());
			}

			personalDataTO.setBloodGroup(personalData.getBloodGroup());
			if (personalData.getPhNo1() != null
					&& personalData.getPhNo2() != null
					&& personalData.getPhNo3() != null) {
				personalDataTO.setLandlineNo(personalData.getPhNo1() + " "
						+ personalData.getPhNo2() + " "
						+ personalData.getPhNo3());
			} else if (personalData.getPhNo1() != null
					&& personalData.getPhNo2() != null) {
				personalDataTO.setLandlineNo(personalData.getPhNo1() + " "
						+ personalData.getPhNo2());
			} else if (personalData.getPhNo1() != null) {
				personalDataTO.setLandlineNo(personalData.getPhNo1());
			}
			if (personalData.getMobileNo1() != null
					&& personalData.getMobileNo2() != null
					&& personalData.getMobileNo3() != null) {
				personalDataTO.setMobileNo(personalData.getMobileNo1() + " "
						+ personalData.getMobileNo2() + " "
						+ personalData.getMobileNo3());
			} else if (personalData.getMobileNo1() != null
					&& personalData.getMobileNo2() != null) {
				personalDataTO.setMobileNo(personalData.getMobileNo1() + " "
						+ personalData.getMobileNo2());
			} else if (personalData.getMobileNo1() != null) {
				personalDataTO.setMobileNo(personalData.getMobileNo1());
			}

			personalDataTO.setEmail(personalData.getEmail());

			personalDataTO.setPassportNo(personalData.getPassportNo());
			if (personalData.getCountryByPassportCountryId() != null) {
				personalDataTO.setPassportIssuingCountry(personalData
						.getCountryByPassportCountryId().getName());
			}
			if (personalData.getPassportValidity() != null) {
				personalDataTO.setPassportValidity(CommonUtil
						.getStringDate(personalData.getPassportValidity()));
			}
			personalDataTO.setResidentPermitNo(personalData
					.getResidentPermitNo());
			if (personalData.getResidentPermitDate() != null) {
				personalDataTO.setResidentPermitDate(CommonUtil
						.ConvertStringToDateFormat(CommonUtil
								.getStringDate(personalData
										.getResidentPermitDate()),
								"dd-MMM-yyyy", "dd/MM/yyyy"));
			}

			// Setting applicant's permanent address details to personalData TO
			personalDataTO.setPermanentAddressLine1(personalData
					.getPermanentAddressLine1());
			personalDataTO.setPermanentAddressLine2(personalData
					.getPermanentAddressLine2());
			if (personalData.getCityByPermanentAddressCityId() != null) {
				personalDataTO.setPermanentCityName(personalData
						.getCityByPermanentAddressCityId());
			}
			if (personalData.getPermanentAddressStateOthers() != null
					&& personalData.getPermanentAddressStateOthers().trim()
							.length() > 0) {
				personalDataTO.setPermanentStateName(personalData
						.getPermanentAddressStateOthers());
			} else if (personalData.getStateByPermanentAddressStateId() != null) {
				personalDataTO.setPermanentStateName(personalData
						.getStateByPermanentAddressStateId().getName());
			}
			if (personalData.getPermanentAddressCountryOthers() != null
					&& personalData.getPermanentAddressCountryOthers().trim()
							.length() > 0) {
				personalDataTO.setPermanentCountryName(personalData
						.getPermanentAddressCountryOthers());
			} else if (personalData.getCountryByPermanentAddressCountryId() != null) {
				personalDataTO.setPermanentCountryName(personalData
						.getCountryByPermanentAddressCountryId().getName());
			}
			personalDataTO.setPermanentAddressZipCode(personalData
					.getPermanentAddressZipCode());

			// Setting applicant's current address details to personalData TO
			personalDataTO.setCurrentAddressLine1(personalData
					.getCurrentAddressLine1());
			personalDataTO.setCurrentAddressLine2(personalData
					.getCurrentAddressLine2());
			if (personalData.getCityByCurrentAddressCityId() != null) {
				personalDataTO.setCurrentCityName(personalData
						.getCityByCurrentAddressCityId());
			}
			if (personalData.getCurrentAddressStateOthers() != null
					&& personalData.getCurrentAddressStateOthers().trim()
							.length() > 0) {
				personalDataTO.setCurrentStateName(personalData
						.getCurrentAddressStateOthers());
			} else if (personalData.getStateByCurrentAddressStateId() != null) {
				personalDataTO.setCurrentStateName(personalData
						.getStateByCurrentAddressStateId().getName());
			}
			if (personalData.getCurrentAddressCountryOthers() != null
					&& personalData.getCurrentAddressCountryOthers().trim()
							.length() > 0) {
				personalDataTO.setCurrentCountryName(personalData
						.getCurrentAddressCountryOthers());
			} else if (personalData.getCountryByCurrentAddressCountryId() != null) {
				personalDataTO.setCurrentCountryName(personalData
						.getCountryByCurrentAddressCountryId().getName());
			}
			personalDataTO.setCurrentAddressZipCode(personalData
					.getCurrentAddressZipCode());

			// Setting applicant's father details to personalData TO
			personalDataTO.setFatherName(personalData.getFatherName());
			personalDataTO
					.setFatherEducation(personalData.getFatherEducation());
			if (personalData.getCurrencyByFatherIncomeCurrencyId() != null
					&& personalData.getIncomeByFatherIncomeId() != null
					&& personalData.getIncomeByFatherIncomeId() != null
					&& personalData.getIncomeByFatherIncomeId().getCurrency() != null) {
				personalDataTO.setFatherIncome(personalData
						.getCurrencyByFatherIncomeCurrencyId()
						.getCurrencyCode()
						+ " "
						+ personalData.getIncomeByFatherIncomeId()
								.getIncomeRange());
			} else if (personalData.getIncomeByFatherIncomeId() != null) {
				personalDataTO.setFatherIncome(personalData
						.getIncomeByFatherIncomeId().getIncomeRange());
			}
			if (personalData.getOccupationByFatherOccupationId() != null) {
				personalDataTO.setFatherOccupation(personalData
						.getOccupationByFatherOccupationId().getName());
			}
			personalDataTO.setFatherEmail(personalData.getFatherEmail());

			// Setting applicant's mother details to personalData TO
			personalDataTO.setMotherName(personalData.getMotherName());
			personalDataTO
					.setMotherEducation(personalData.getMotherEducation());

			if (personalData.getCurrencyByMotherIncomeCurrencyId() != null
					&& personalData.getIncomeByMotherIncomeId() != null
					&& personalData.getIncomeByMotherIncomeId().getCurrency() != null) {

				personalDataTO.setMotherIncome(personalData
						.getCurrencyByMotherIncomeCurrencyId()
						.getCurrencyCode()
						+ " "
						+ personalData.getIncomeByMotherIncomeId()
								.getIncomeRange());
			} else if (personalData.getIncomeByMotherIncomeId() != null) {
				personalDataTO.setMotherIncome(personalData
						.getIncomeByMotherIncomeId().getIncomeRange());
			}

			if (personalData.getOccupationByMotherOccupationId() != null) {
				personalDataTO.setMotherOccupation(personalData
						.getOccupationByMotherOccupationId().getName());
			}
			personalDataTO.setMotherEmail(personalData.getMotherEmail());

			// Setting applicant's parent address to personalData TO
			personalDataTO.setParentAddressLine1(personalData
					.getParentAddressLine1());
			personalDataTO.setParentAddressLine2(personalData
					.getParentAddressLine2());
			personalDataTO.setParentAddressLine3(personalData
					.getParentAddressLine3());
			if (personalData.getCityByParentAddressCityId() != null) {
				personalDataTO.setParentCityName(personalData
						.getCityByParentAddressCityId());
			}
			if (personalData.getParentAddressStateOthers() != null
					&& personalData.getParentAddressStateOthers().trim()
							.length() > 0) {
				personalDataTO.setParentStateName(personalData
						.getParentAddressStateOthers());
			} else if (personalData.getStateByParentAddressStateId() != null) {
				personalDataTO.setParentStateName(personalData
						.getStateByParentAddressStateId().getName());
			}
			if (personalData.getParentAddressCountryOthers() != null
					&& personalData.getParentAddressCountryOthers().trim()
							.length() > 0) {
				personalDataTO.setParentCountryName(personalData
						.getParentAddressCountryOthers());
			} else if (personalData.getCountryByParentAddressCountryId() != null) {
				personalDataTO.setParentCountryName(personalData
						.getCountryByParentAddressCountryId().getName());
			}
			personalDataTO.setParentAddressZipCode(personalData
					.getParentAddressZipCode());

			if (personalData.getParentPh1() != null
					&& personalData.getParentPh2() != null
					&& personalData.getParentPh3() != null) {
				personalDataTO.setParentPhone(personalData.getParentPh1() + " "
						+ personalData.getParentPh2() + " "
						+ personalData.getParentPh3());
			} else if (personalData.getParentPh1() != null
					&& personalData.getParentPh2() != null) {
				personalDataTO.setParentPhone(personalData.getParentPh1() + " "
						+ personalData.getParentPh2());
			} else if (personalData.getParentPh1() != null) {
				personalDataTO.setParentPhone(personalData.getParentPh1());
			}

			if (personalData.getParentMob1() != null
					&& personalData.getParentMob2() != null
					&& personalData.getParentMob3() != null) {
				personalDataTO.setParentMobile(personalData.getParentMob1()
						+ " " + personalData.getParentMob2() + " "
						+ personalData.getParentMob3());
			} else if (personalData.getParentMob1() != null
					&& personalData.getParentMob2() != null) {
				personalDataTO.setParentMobile(personalData.getParentMob1()
						+ " " + personalData.getParentMob2());
			} else if (personalData.getParentMob1() != null) {
				personalDataTO.setParentMobile(personalData.getParentMob1());
			}

			// guardian address
			personalDataTO.setGuardianAddressLine1(personalData
					.getGuardianAddressLine1());
			personalDataTO.setGuardianAddressLine2(personalData
					.getGuardianAddressLine2());
			personalDataTO.setGuardianAddressLine3(personalData
					.getGuardianAddressLine3());
			if (personalData.getCityByGuardianAddressCityId() != null) {
				personalDataTO.setCityByGuardianAddressCityId(personalData
						.getCityByGuardianAddressCityId());
			}
			if (personalData.getGuardianAddressStateOthers() != null
					&& personalData.getGuardianAddressStateOthers().trim()
							.length() > 0) {
				personalDataTO.setGuardianStateName(personalData
						.getGuardianAddressStateOthers());
			} else if (personalData.getStateByGuardianAddressStateId() != null) {
				personalDataTO.setGuardianStateName(personalData
						.getStateByGuardianAddressStateId().getName());
			}
			if (personalData.getCountryByGuardianAddressCountryId() != null) {
				personalDataTO.setGuardianCountryName(personalData
						.getCountryByGuardianAddressCountryId().getName());
			}
			personalDataTO.setGuardianAddressZipCode(personalData
					.getGuardianAddressZipCode());
			personalDataTO.setGuardianPh1(personalData.getGuardianPh1());
			personalDataTO.setGuardianPh2(personalData.getGuardianPh2());
			personalDataTO.setGuardianPh3(personalData.getGuardianPh3());
			personalDataTO.setGuardianMob1(personalData.getGuardianMob1());
			personalDataTO.setGuardianMob2(personalData.getGuardianMob2());
			personalDataTO.setGuardianMob3(personalData.getGuardianMob3());

			personalDataTO.setBrotherName(personalData.getBrotherName());
			personalDataTO.setBrotherEducation(personalData
					.getBrotherEducation());
			personalDataTO.setBrotherOccupation(personalData
					.getBrotherOccupation());
			personalDataTO.setBrotherIncome(personalData.getBrotherIncome());
			personalDataTO.setBrotherAge(personalData.getBrotherAge());

			personalDataTO.setSisterName(personalData.getSisterName());
			personalDataTO
					.setSisterEducation(personalData.getSisterEducation());
			personalDataTO.setSisterOccupation(personalData
					.getSisterOccupation());
			personalDataTO.setSisterIncome(personalData.getSisterIncome());
			personalDataTO.setSisterAge(personalData.getSisterAge());

			if (personalData.getHeight() != null
					&& !StringUtils
							.isEmpty(personalData.getHeight().toString())
					&& StringUtils.isNumeric(personalData.getHeight()
							.toString())) {
				personalDataTO.setHeight(personalData.getHeight().toString());
			}

			if (personalData.getWeight() != null
					&& !StringUtils
							.isEmpty(personalData.getWeight().toString())
					&& CommonUtil.isValidDecimal(personalData.getWeight()
							.toString())) {
				personalDataTO.setWeight(personalData.getWeight().toString());
			}

			if (personalData.getLanguageByLanguageRead() != null
					&& !StringUtils.isEmpty(personalData
							.getLanguageByLanguageRead())) {
				personalDataTO.setLanguageByLanguageRead(personalData
						.getLanguageByLanguageRead());
			}
			if (personalData.getLanguageByLanguageWrite() != null
					&& !StringUtils.isEmpty(personalData
							.getLanguageByLanguageWrite())) {
				personalDataTO.setLanguageByLanguageWrite(personalData
						.getLanguageByLanguageWrite());
			}

			if (personalData.getLanguageByLanguageSpeak() != null
					&& !StringUtils.isEmpty(personalData
							.getLanguageByLanguageSpeak())) {
				personalDataTO.setLanguageByLanguageSpeak(personalData
						.getLanguageByLanguageSpeak());
			}

			if (personalData.getMotherTongue() != null
					&& !StringUtils.isEmpty(personalData.getMotherTongue()
							.toString())) {
				personalDataTO.setMotherTongue(personalData.getMotherTongue()
						.getName());
			}

			if (personalData.getTrainingDuration() != null
					&& !StringUtils.isEmpty(personalData.getTrainingDuration()
							.toString())
					&& StringUtils.isNumeric(personalData.getTrainingDuration()
							.toString())) {
				personalDataTO.setTrainingDuration(personalData
						.getTrainingDuration().toString());
			}

			personalDataTO.setTrainingInstAddress(personalData
					.getTrainingInstAddress());
			personalDataTO.setTrainingProgName(personalData
					.getTrainingProgName());
			personalDataTO
					.setTrainingPurpose(personalData.getTrainingPurpose());

			personalDataTO.setCourseKnownBy(personalData.getCourseKnownBy());
			personalDataTO
					.setCourseOptReason(personalData.getCourseOptReason());
			personalDataTO.setStrength(personalData.getStrength());
			personalDataTO.setWeakness(personalData.getWeakness());
			personalDataTO.setOtherAddnInfo(personalData.getOtherAddnInfo());
			personalDataTO.setSecondLanguage(personalData.getSecondLanguage());
			/*if(personalData.getRecommentedBy()!=null && !personalData.getRecommentedBy().isEmpty()){
				personalDataTO.setRecommentedBy(personalData.getRecommentedBy());
				personalDataTO.setIsRecommended(true);
			}*/
			if(personalData.getRecommendedBy()!=null && !personalData.getRecommendedBy().isEmpty()){
				personalDataTO.setRecommendDeatails(personalData.getRecommendedBy());
				personalDataTO.setIsRecommended(true);
			}else{
			personalDataTO.setIsRecommended(false);
			}
		}
		return personalDataTO;
	}

	/**
	 * Method to retrieve applicant related Course Details
	 * 
	 * @param courseBO
	 * @return courseTO
	 */
	public CourseTO copyPropertiesValue(Course course) throws Exception {
		CourseTO courseTO = null;

		if (course != null) {
			courseTO = new CourseTO();
			courseTO.setId(course.getId());
			courseTO.setProgramId(course.getProgram().getId());
			course.getProgram().getId();
			courseTO.setCode(course.getName());
			if (course.getProgram() != null) {
				courseTO.setProgramCode(course.getProgram().getName());
				courseTO.setProgramId(course.getProgram().getId());
			}
			if (course.getProgram() != null
					&& course.getProgram().getProgramType() != null) {
				courseTO.setProgramTypeCode(course.getProgram()
						.getProgramType().getName());
				courseTO.setProgramTypeId(course.getProgram().getProgramType()
						.getId());
			}
			if (course.getIsWorkExperienceRequired().equals(true)) {
				courseTO.setIsWorkExperienceRequired("Yes");
			} else {
				courseTO.setIsWorkExperienceRequired("No");
			}
		}
		return courseTO;
	}

	/**
	 * Method to retrieve applicant related Educational Qualification Details
	 * 
	 * @param qualificationSetBO
	 * @return List ednQualificationListTO
	 */
	public List<EdnQualificationTO> copyPropertiesValues(
			Set<EdnQualification> qualificationSet) throws Exception {

		List<EdnQualificationTO> ednQualificationList = new ArrayList<EdnQualificationTO>();
		EdnQualificationTO ednQualificationTO = null;

		if (qualificationSet != null) {
			Iterator<EdnQualification> iterator = qualificationSet.iterator();
			while (iterator.hasNext()) {
				EdnQualification ednQualificationBO = iterator.next();

				ednQualificationTO = new EdnQualificationTO();
				ednQualificationTO.setId(ednQualificationBO.getId());
				if(ednQualificationBO.getPercentage()!=null){
					ednQualificationTO.setPercentage(ednQualificationBO.getPercentage().toString());
				}
				if (ednQualificationBO.getDocChecklist() != null
						&& !ednQualificationBO.getDocChecklist()
								.getIsPreviousExam()) {
					ednQualificationTO.setLastExam(true);
				}
				if (ednQualificationBO.getDocChecklist() != null
						&& ednQualificationBO.getDocChecklist()
								.getIsExamRequired())
					ednQualificationTO.setExamConfigured(true);
				// copy doc type exam
				if (ednQualificationBO.getDocTypeExams() != null) {
					ednQualificationTO.setSelectedExamId(String
							.valueOf(ednQualificationBO.getDocTypeExams()
									.getId()));
					if (ednQualificationBO.getDocTypeExams().getName() != null)
						ednQualificationTO.setSelectedExamName(String
								.valueOf(ednQualificationBO.getDocTypeExams()
										.getName()));
				}
				if (ednQualificationBO.getDocChecklist() != null
						&& ednQualificationBO.getDocChecklist().getDocType() != null) {
					ednQualificationTO.setDocCheckListId(ednQualificationBO
							.getDocChecklist().getId());
					ednQualificationTO.setDocName(ednQualificationBO
							.getDocChecklist().getDocType().getName());
					ednQualificationTO.setSemesterWise(false);
					ednQualificationTO.setConsolidated(true);
					if (ednQualificationBO.getDocChecklist() != null
							&& ednQualificationBO.getDocChecklist()
									.getIsActive()
							&& ednQualificationBO.getDocChecklist()
									.getIsMarksCard()
							&& !ednQualificationBO.getDocChecklist()
									.getIsConsolidatedMarks()
							&& ednQualificationBO.getDocChecklist()
									.getIsSemesterWise()) {
						ednQualificationTO.setLanguage(ednQualificationBO
								.getDocChecklist().getIsIncludeLanguage());
						ednQualificationTO.setSemesterWise(true);
						ednQualificationTO.setConsolidated(false);
						Set<ApplicantMarksDetails> detailMarks = ednQualificationBO
								.getApplicantMarksDetailses();
						if (detailMarks != null && !detailMarks.isEmpty()) {
							Set<ApplicantMarkDetailsTO> markdetails = new HashSet<ApplicantMarkDetailsTO>();
							ApplicantMarksDetails detailMarkBO = null;
							Iterator<ApplicantMarksDetails> markItr = detailMarks
									.iterator();
							while (markItr.hasNext()) {
								detailMarkBO = (ApplicantMarksDetails) markItr
										.next();
								ApplicantMarkDetailsTO markTO = new ApplicantMarkDetailsTO();
								convertApplicantMarkBOtoTO(detailMarkBO,
										markTO, ednQualificationTO.isLastExam());
								markdetails.add(markTO);
							}
							if (!markdetails.isEmpty()) {
								ednQualificationTO.setSemesterList(markdetails);
							} else {
								for (int i = 1; i <= CMSConstants.MAX_CANDIDATE_SEMESTERS; i++) {
									ApplicantMarkDetailsTO markDetailsTo = new ApplicantMarkDetailsTO();
									markDetailsTo.setSemesterNo(i);
									markDetailsTo
											.setLastExam(ednQualificationTO
													.isLastExam());
									markdetails.add(markDetailsTo);
								}
								ednQualificationTO.setSemesterList(markdetails);
							}
						} else {
							Set<ApplicantMarkDetailsTO> markdetails = new HashSet<ApplicantMarkDetailsTO>();
							for (int i = 1; i <= CMSConstants.MAX_CANDIDATE_SEMESTERS; i++) {
								ApplicantMarkDetailsTO markDetailsTo = new ApplicantMarkDetailsTO();
								markDetailsTo.setSemesterNo(i);
								markDetailsTo.setLastExam(ednQualificationTO
										.isLastExam());
								markdetails.add(markDetailsTo);
							}
							ednQualificationTO.setSemesterList(markdetails);
						}
					} else if (ednQualificationBO.getDocChecklist() != null
							&& ednQualificationBO.getDocChecklist()
									.getIsActive()
							&& ednQualificationBO.getDocChecklist()
									.getIsMarksCard()
							&& !ednQualificationBO.getDocChecklist()
									.getIsConsolidatedMarks()
							&& !ednQualificationBO.getDocChecklist()
									.getIsSemesterWise()) {
						ednQualificationTO.setConsolidated(false);
						Set<CandidateMarks> detailMarks = ednQualificationBO
								.getCandidateMarkses();
						if (detailMarks != null && !detailMarks.isEmpty()) {
							CandidateMarks detailMarkBO = null;
							Iterator<CandidateMarks> markItr = detailMarks
									.iterator();
							while (markItr.hasNext()) {
								detailMarkBO = (CandidateMarks) markItr.next();
								CandidateMarkTO markTO = new CandidateMarkTO();
								convertDetailMarkBOtoTO(detailMarkBO, markTO);
								ednQualificationTO.setDetailmark(markTO);
							}
						} else {
							ednQualificationTO.setDetailmark(null);
						}
					}
				}
				if (ednQualificationBO.getUniversityOthers() != null
						&& !ednQualificationBO.getUniversityOthers().isEmpty()) {
					ednQualificationTO.setUniversityName(ednQualificationBO
							.getUniversityOthers());
				} else if (ednQualificationBO.getUniversity() != null) {
					ednQualificationTO.setUniversityName(ednQualificationBO
							.getUniversity().getName());
				}
				if (ednQualificationBO.getInstitutionNameOthers() != null
						&& !ednQualificationBO.getInstitutionNameOthers()
								.isEmpty()) {
					ednQualificationTO.setInstitutionName(ednQualificationBO
							.getInstitutionNameOthers());
				} else if (ednQualificationBO.getCollege() != null) {
					ednQualificationTO.setInstitutionName(ednQualificationBO
							.getCollege().getName());
				}
				if (ednQualificationBO.getYearPassing() != null) {
					ednQualificationTO.setYearPassing(ednQualificationBO
							.getYearPassing());
				}
				if (ednQualificationBO.getMonthPassing() != null) {
					ednQualificationTO.setMonthPassing(ednQualificationBO
							.getMonthPassing());
				}
				if (ednQualificationBO.getPreviousRegNo() != null) {
					ednQualificationTO.setPreviousRegNo(ednQualificationBO
							.getPreviousRegNo());
				}
				if (ednQualificationBO.getMarksObtained() != null) {
					ednQualificationTO.setMarksObtained(String
							.valueOf(ednQualificationBO.getMarksObtained()
									.intValue()));
				}
				if (ednQualificationBO.getTotalMarks() != null) {
					ednQualificationTO.setTotalMarks(String
							.valueOf(ednQualificationBO.getTotalMarks()));
				}
				ednQualificationTO.setNoOfAttempts(ednQualificationBO
						.getNoOfAttempts());

					List<UniversityTO> universityList = UniversityHandler
							.getInstance().getUniversity();
					if (ednQualificationTO.getUniversityId() != null
							&& !ednQualificationTO.getUniversityId()
									.equalsIgnoreCase("Other")) {
						ednQualificationTO.setUniversityList(universityList);
						if (ednQualificationTO.getInstitutionId() != null
								&& !ednQualificationTO.getInstitutionId()
										.equalsIgnoreCase("Other")) {
							Iterator<UniversityTO> colItr = universityList
									.iterator();
							while (colItr.hasNext()) {
								UniversityTO unTO = (UniversityTO) colItr
										.next();
								if (unTO.getId() == Integer
										.parseInt(ednQualificationTO
												.getUniversityId())) {
									ednQualificationTO.setInstituteList(unTO
											.getCollegeTos());
								}
							}
						}
					}

				ednQualificationList.add(ednQualificationTO);
			}
		}
		//sorting added by manu 
		Collections.sort(ednQualificationList);
		return ednQualificationList;
	}

	/**
	 * @param detailMarkBO
	 * @param markTO
	 * @param lastExam
	 */
	private void convertApplicantMarkBOtoTO(ApplicantMarksDetails detailMarkBO,
			ApplicantMarkDetailsTO markTO, boolean lastExam) throws Exception {
		if (detailMarkBO != null) {
			markTO.setId(detailMarkBO.getId());
			markTO.setEdnQualificationId(detailMarkBO.getEdnQualification()
					.getId());
			markTO.setLastExam(lastExam);
			markTO.setMarksObtained(String.valueOf(detailMarkBO
					.getMarksObtained()));
			markTO.setMaxMarks(String.valueOf(detailMarkBO.getMaxMarks()));
			markTO.setSemesterName(detailMarkBO.getSemesterName());
			markTO.setSemesterNo(detailMarkBO.getSemesterNo());
			if (detailMarkBO.getMarksObtainedLanguagewise() != null) {
				markTO.setMarksObtained_languagewise(String
						.valueOf(detailMarkBO.getMarksObtainedLanguagewise()));
			}
			if (detailMarkBO.getMaxMarksLanguagewise() != null) {
				markTO.setMaxMarks_languagewise(String.valueOf(detailMarkBO
						.getMaxMarksLanguagewise()));
			}
		}
	}

	/**
	 * @param detailMarkBO
	 * @param markTO
	 */
	private void convertDetailMarkBOtoTO(CandidateMarks detailMarkBO,
			CandidateMarkTO markTO) throws Exception {
		if (detailMarkBO != null) {
			markTO.setId(detailMarkBO.getId());
			markTO.setSubject1(detailMarkBO.getSubject1());
			markTO.setSubject2(detailMarkBO.getSubject2());
			markTO.setSubject3(detailMarkBO.getSubject3());
			markTO.setSubject4(detailMarkBO.getSubject4());
			markTO.setSubject5(detailMarkBO.getSubject5());
			markTO.setSubject6(detailMarkBO.getSubject6());
			markTO.setSubject7(detailMarkBO.getSubject7());
			markTO.setSubject8(detailMarkBO.getSubject8());
			markTO.setSubject9(detailMarkBO.getSubject9());
			markTO.setSubject10(detailMarkBO.getSubject10());
			markTO.setSubject11(detailMarkBO.getSubject11());
			markTO.setSubject12(detailMarkBO.getSubject12());
			markTO.setSubject13(detailMarkBO.getSubject13());
			markTO.setSubject14(detailMarkBO.getSubject14());
			markTO.setSubject15(detailMarkBO.getSubject15());
			markTO.setSubject16(detailMarkBO.getSubject16());
			markTO.setSubject17(detailMarkBO.getSubject17());
			markTO.setSubject18(detailMarkBO.getSubject18());
			markTO.setSubject19(detailMarkBO.getSubject19());
			markTO.setSubject20(detailMarkBO.getSubject20());

			if (detailMarkBO.getSubject1TotalMarks() != null
					&& detailMarkBO.getSubject1TotalMarks().intValue() != 0) {
				markTO.setSubject1TotalMarks(String.valueOf(detailMarkBO
						.getSubject1TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject2TotalMarks() != null
					&& detailMarkBO.getSubject2TotalMarks().intValue() != 0) {
				markTO.setSubject2TotalMarks(String.valueOf(detailMarkBO
						.getSubject2TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject3TotalMarks() != null
					&& detailMarkBO.getSubject3TotalMarks().intValue() != 0) {
				markTO.setSubject3TotalMarks(String.valueOf(detailMarkBO
						.getSubject3TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject4TotalMarks() != null
					&& detailMarkBO.getSubject4TotalMarks().intValue() != 0) {
				markTO.setSubject4TotalMarks(String.valueOf(detailMarkBO
						.getSubject4TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject5TotalMarks() != null
					&& detailMarkBO.getSubject5TotalMarks().intValue() != 0) {
				markTO.setSubject5TotalMarks(String.valueOf(detailMarkBO
						.getSubject5TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject6TotalMarks() != null
					&& detailMarkBO.getSubject6TotalMarks().intValue() != 0) {
				markTO.setSubject6TotalMarks(String.valueOf(detailMarkBO
						.getSubject6TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject7TotalMarks() != null
					&& detailMarkBO.getSubject7TotalMarks().intValue() != 0) {
				markTO.setSubject7TotalMarks(String.valueOf(detailMarkBO
						.getSubject7TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject8TotalMarks() != null
					&& detailMarkBO.getSubject8TotalMarks().intValue() != 0) {
				markTO.setSubject8TotalMarks(String.valueOf(detailMarkBO
						.getSubject8TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject9TotalMarks() != null
					&& detailMarkBO.getSubject9TotalMarks().intValue() != 0) {
				markTO.setSubject9TotalMarks(String.valueOf(detailMarkBO
						.getSubject9TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject10TotalMarks() != null
					&& detailMarkBO.getSubject10TotalMarks().intValue() != 0) {
				markTO.setSubject10TotalMarks(String.valueOf(detailMarkBO
						.getSubject10TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject11TotalMarks() != null
					&& detailMarkBO.getSubject11TotalMarks().intValue() != 0) {
				markTO.setSubject11TotalMarks(String.valueOf(detailMarkBO
						.getSubject11TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject12TotalMarks() != null
					&& detailMarkBO.getSubject12TotalMarks().intValue() != 0) {
				markTO.setSubject12TotalMarks(String.valueOf(detailMarkBO
						.getSubject12TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject13TotalMarks() != null
					&& detailMarkBO.getSubject13TotalMarks().intValue() != 0) {
				markTO.setSubject13TotalMarks(String.valueOf(detailMarkBO
						.getSubject13TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject14TotalMarks() != null
					&& detailMarkBO.getSubject14TotalMarks().intValue() != 0) {
				markTO.setSubject14TotalMarks(String.valueOf(detailMarkBO
						.getSubject14TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject15TotalMarks() != null
					&& detailMarkBO.getSubject15TotalMarks().intValue() != 0) {
				markTO.setSubject15TotalMarks(String.valueOf(detailMarkBO
						.getSubject15TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject16TotalMarks() != null
					&& detailMarkBO.getSubject16TotalMarks().intValue() != 0) {
				markTO.setSubject16TotalMarks(String.valueOf(detailMarkBO
						.getSubject16TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject17TotalMarks() != null
					&& detailMarkBO.getSubject17TotalMarks().intValue() != 0) {
				markTO.setSubject17TotalMarks(String.valueOf(detailMarkBO
						.getSubject17TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject18TotalMarks() != null
					&& detailMarkBO.getSubject18TotalMarks().intValue() != 0) {
				markTO.setSubject18TotalMarks(String.valueOf(detailMarkBO
						.getSubject18TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject19TotalMarks() != null
					&& detailMarkBO.getSubject19TotalMarks().intValue() != 0) {
				markTO.setSubject19TotalMarks(String.valueOf(detailMarkBO
						.getSubject19TotalMarks().intValue()));
			}
			if (detailMarkBO.getSubject20TotalMarks() != null
					&& detailMarkBO.getSubject20TotalMarks().intValue() != 0) {
				markTO.setSubject20TotalMarks(String.valueOf(detailMarkBO
						.getSubject20TotalMarks().intValue()));
			}

			if (detailMarkBO.getSubject1ObtainedMarks() != null
					&& detailMarkBO.getSubject1ObtainedMarks().intValue() != 0) {
				markTO.setSubject1ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject1ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject2ObtainedMarks() != null
					&& detailMarkBO.getSubject2ObtainedMarks().intValue() != 0) {
				markTO.setSubject2ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject2ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject3ObtainedMarks() != null
					&& detailMarkBO.getSubject3ObtainedMarks().intValue() != 0) {
				markTO.setSubject3ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject3ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject4ObtainedMarks() != null
					&& detailMarkBO.getSubject4ObtainedMarks().intValue() != 0) {
				markTO.setSubject4ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject4ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject5ObtainedMarks() != null
					&& detailMarkBO.getSubject5ObtainedMarks().intValue() != 0) {
				markTO.setSubject5ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject5ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject6ObtainedMarks() != null
					&& detailMarkBO.getSubject6ObtainedMarks().intValue() != 0) {
				markTO.setSubject6ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject6ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject7ObtainedMarks() != null
					&& detailMarkBO.getSubject7ObtainedMarks().intValue() != 0) {
				markTO.setSubject7ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject7ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject8ObtainedMarks() != null
					&& detailMarkBO.getSubject8ObtainedMarks().intValue() != 0) {
				markTO.setSubject8ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject8ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject9ObtainedMarks() != null
					&& detailMarkBO.getSubject9ObtainedMarks().intValue() != 0) {
				markTO.setSubject9ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject9ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject10ObtainedMarks() != null
					&& detailMarkBO.getSubject10ObtainedMarks().intValue() != 0) {
				markTO.setSubject10ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject10ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject11ObtainedMarks() != null
					&& detailMarkBO.getSubject11ObtainedMarks().intValue() != 0) {
				markTO.setSubject11ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject11ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject12ObtainedMarks() != null
					&& detailMarkBO.getSubject12ObtainedMarks().intValue() != 0) {
				markTO.setSubject12ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject12ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject13ObtainedMarks() != null
					&& detailMarkBO.getSubject13ObtainedMarks().intValue() != 0) {
				markTO.setSubject13ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject13ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject14ObtainedMarks() != null
					&& detailMarkBO.getSubject14ObtainedMarks().intValue() != 0) {
				markTO.setSubject14ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject14ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject15ObtainedMarks() != null
					&& detailMarkBO.getSubject15ObtainedMarks().intValue() != 0) {
				markTO.setSubject15ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject15ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject16ObtainedMarks() != null
					&& detailMarkBO.getSubject16ObtainedMarks().intValue() != 0) {
				markTO.setSubject16ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject16ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject17ObtainedMarks() != null
					&& detailMarkBO.getSubject17ObtainedMarks().intValue() != 0) {
				markTO.setSubject17ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject17ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject18ObtainedMarks() != null
					&& detailMarkBO.getSubject18ObtainedMarks().intValue() != 0) {
				markTO.setSubject18ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject18ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject19ObtainedMarks() != null
					&& detailMarkBO.getSubject19ObtainedMarks().intValue() != 0) {
				markTO.setSubject19ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject19ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getSubject20ObtainedMarks() != null
					&& detailMarkBO.getSubject20ObtainedMarks().intValue() != 0) {
				markTO.setSubject20ObtainedMarks(String.valueOf(detailMarkBO
						.getSubject20ObtainedMarks().intValue()));
			}
			if (detailMarkBO.getTotalMarks() != null
					&& detailMarkBO.getTotalMarks().intValue() != 0) {
				markTO.setTotalMarks(String.valueOf(detailMarkBO
						.getTotalMarks().intValue()));
			}
			if (detailMarkBO.getTotalObtainedMarks() != null
					&& detailMarkBO.getTotalObtainedMarks().intValue() != 0) {
				markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO
						.getTotalObtainedMarks().intValue()));
			}
		}
	}

	/**
	 * @param preferencesSetBO
	 * @return
	 */
	public PreferenceTO copyPropertiesValue(
			Set<CandidatePreference> preferencesSet) throws Exception {
		PreferenceTO preferenceTO = null;

		if (preferencesSet != null) {
			Iterator<CandidatePreference> iterator = preferencesSet.iterator();
			preferenceTO = new PreferenceTO();

			while (iterator.hasNext()) {
				CandidatePreference candidatePreferenceBO = (CandidatePreference) iterator
						.next();

				// select the preferences depending upon the preference no.
				if (candidatePreferenceBO.getCourse() != null
						&& candidatePreferenceBO.getCourse().getProgram() != null
						&& candidatePreferenceBO.getCourse().getProgram()
								.getProgramType() != null) {
					if (candidatePreferenceBO.getPrefNo() == 1) {
						preferenceTO.setId(candidatePreferenceBO.getId());
						preferenceTO.setFirstPerfId(candidatePreferenceBO
								.getId());
						preferenceTO
								.setFirstprefCourseName(candidatePreferenceBO
										.getCourse().getName());
						preferenceTO.setFirstPrefCourseId(String
								.valueOf(candidatePreferenceBO.getCourse()
										.getId()));
						preferenceTO.setFirstprefPgmName(candidatePreferenceBO
								.getCourse().getProgram().getName());
						preferenceTO
								.setFirstprefPgmTypeName(candidatePreferenceBO
										.getCourse().getProgram()
										.getProgramType().getName());
						preferenceTO.setFirstPrefProgramId(String
								.valueOf(candidatePreferenceBO.getCourse()
										.getProgram().getId()));
						preferenceTO
								.setFirstPrefProgramTypeId(String
										.valueOf(candidatePreferenceBO
												.getCourse().getProgram()
												.getProgramType().getId()));
					} else if (candidatePreferenceBO.getPrefNo() == 2) {
						preferenceTO.setId(candidatePreferenceBO.getId());
						preferenceTO.setSecondPerfId(candidatePreferenceBO
								.getId());
						preferenceTO
								.setSecondprefCourseName(candidatePreferenceBO
										.getCourse().getName());
						preferenceTO.setSecondPrefCourseId(String
								.valueOf(candidatePreferenceBO.getCourse()
										.getId()));
						preferenceTO.setSecondprefPgmName(candidatePreferenceBO
								.getCourse().getProgram().getName());
						preferenceTO
								.setSecondprefPgmTypeName(candidatePreferenceBO
										.getCourse().getProgram()
										.getProgramType().getName());
						preferenceTO.setSecondPrefProgramId(String
								.valueOf(candidatePreferenceBO.getCourse()
										.getProgram().getId()));
						preferenceTO
								.setSecondPrefProgramTypeId(String
										.valueOf(candidatePreferenceBO
												.getCourse().getProgram()
												.getProgramType().getId()));
					} else if (candidatePreferenceBO.getPrefNo() == 3) {
						preferenceTO.setId(candidatePreferenceBO.getId());
						preferenceTO.setThirdPerfId(candidatePreferenceBO
								.getId());
						preferenceTO
								.setThirdprefCourseName(candidatePreferenceBO
										.getCourse().getName());
						preferenceTO.setThirdPrefCourseId(String
								.valueOf(candidatePreferenceBO.getCourse()
										.getId()));
						preferenceTO.setThirdprefPgmName(candidatePreferenceBO
								.getCourse().getProgram().getName());
						preferenceTO
								.setThirdprefPgmTypeName(candidatePreferenceBO
										.getCourse().getProgram()
										.getProgramType().getName());
						preferenceTO.setThirdPrefProgramId(String
								.valueOf(candidatePreferenceBO.getCourse()
										.getProgram().getId()));
						preferenceTO
								.setThirdPrefProgramTypeId(String
										.valueOf(candidatePreferenceBO
												.getCourse().getProgram()
												.getProgramType().getId()));
					}
				}
			}
		}
		return preferenceTO;
	}

	/**
	 * Method to retrieve applicant related documents
	 * 
	 * @param docUploadSetBO
	 * @return documentsListTO
	 */
	public List<ApplnDocTO> copyPropertiesDocValue(Set<ApplnDoc> docUploadSet,
			int appliedYear) throws Exception {
		List<ApplnDocTO> documentsList = new ArrayList<ApplnDocTO>();
		ApplnDocTO applnDocTO = null;

		if (docUploadSet != null) {
			Iterator<ApplnDoc> iterator = docUploadSet.iterator();
			while (iterator.hasNext()) {
				ApplnDoc applnDocBO = (ApplnDoc) iterator.next();

				applnDocTO = new ApplnDocTO();

				applnDocTO.setId(applnDocBO.getId());
				if (applnDocBO.getDocType() != null) {
					applnDocTO.setDocTypeId(applnDocBO.getDocType().getId());
				}
				applnDocTO.setName(applnDocBO.getName());
				applnDocTO.setContentType(applnDocBO.getContentType());
				if (applnDocBO.getIsVerified() != null
						&& applnDocBO.getIsVerified()) {
					applnDocTO.setVerified(true);
				} else {
					applnDocTO.setVerified(false);
				}
				if (applnDocBO.getDocument() != null) {
					applnDocTO.setDocumentPresent(true);
					applnDocTO.setCurrDocument(applnDocBO.getDocument());

				} else {
					applnDocTO.setDocumentPresent(false);
				}

				Set<DocChecklist> docChecklistDoc = null;
				if (applnDocBO.getDocType() != null) {
					docChecklistDoc = applnDocBO.getDocType()
							.getDocChecklists();
				}

				if (docChecklistDoc != null) {
					Iterator<DocChecklist> checklistItr = docChecklistDoc
							.iterator();
					while (checklistItr.hasNext()) {
						DocChecklist docChecklistBO = (DocChecklist) checklistItr
								.next();
						// condition to check whether course id and applicant
						// course id are matching
						if (docChecklistBO.getCourse().getId() == applnDocBO
								.getAdmAppln().getCourseBySelectedCourseId().getId()
								&& docChecklistBO.getYear() == appliedYear) {
							if (docChecklistBO.getNeedToProduce() != null
									&& docChecklistBO.getNeedToProduce()
									&& docChecklistBO.getIsActive()) {
								applnDocTO.setNeedToProduce(true);
							} else {
								applnDocTO.setNeedToProduce(false);
							}
						}
					}
				}
				documentsList.add(applnDocTO);
			}
		}
		return documentsList;
	}

	/**
	 * @param candidatePrerequisiteMarks
	 * @return
	 */
	private List<CandidatePrerequisiteMarksTO> copyPrerequisiteDetails(
			Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks)
			throws Exception {
		List<CandidatePrerequisiteMarksTO> toList = new ArrayList<CandidatePrerequisiteMarksTO>();
		if (candidatePrerequisiteMarks != null) {
			Iterator<CandidatePrerequisiteMarks> candItr = candidatePrerequisiteMarks
					.iterator();
			while (candItr.hasNext()) {
				CandidatePrerequisiteMarks prereqBo = (CandidatePrerequisiteMarks) candItr
						.next();
				if (prereqBo.getIsActive()) {
					CandidatePrerequisiteMarksTO prerequisteMarksTo = new CandidatePrerequisiteMarksTO();
					prerequisteMarksTo.setId(prereqBo.getId());
					if (prereqBo.getPrerequisite() != null) {
						prerequisteMarksTo.setPrerequisiteName(prereqBo
								.getPrerequisite().getName());
					}
					prerequisteMarksTo.setExamMonth(String.valueOf(prereqBo
							.getExamMonth()));
					prerequisteMarksTo.setExamYear(String.valueOf(prereqBo
							.getExamYear()));
					prerequisteMarksTo.setPrerequisiteMarksObtained(String
							.valueOf(prereqBo.getPrerequisiteMarksObtained()));
					prerequisteMarksTo.setRollNo(prereqBo.getRollNo());
					toList.add(prerequisteMarksTo);
				}
			}
		}
		return toList;
	}

	/**
	 * @param interviewResultSetBO
	 * @return List interviewResultListTO
	 */
	public List<InterviewResultTO> copyPropertiesIRValue(AdmAppln admApplnBo)
			throws Exception {
		List<InterviewResultTO> interviewResultList = new ArrayList<InterviewResultTO>();
		InterviewResultTO interviewResultTO = null;
		Set<InterviewResult> interviewResultSet = admApplnBo
				.getInterviewResults();
		Set<ApplicantRecommendor> applicantRecommendorSet = admApplnBo
				.getApplicantRecommendors();
		if (interviewResultSet != null) {
			Iterator<InterviewResult> iterator = interviewResultSet.iterator();
			while (iterator.hasNext()) {
				InterviewResult interviewResultBO = (InterviewResult) iterator
						.next();

				interviewResultTO = new InterviewResultTO();

				 if (interviewResultBO.getInterviewProgramCourse() != null) {
				 interviewResultTO.setInterviewType(interviewResultBO
				 .getInterviewProgramCourse().getName());
				 }
				if (interviewResultBO.getInterviewStatus() != null) {
					interviewResultTO.setInterviewStatus(interviewResultBO
							.getInterviewStatus().getName());
				}
				Set<InterviewResultDetail> interviewResultDetailSet = interviewResultBO
						.getInterviewResultDetails();
				if (interviewResultDetailSet != null) {
					Iterator<InterviewResultDetail> resultDetailItr = interviewResultDetailSet
							.iterator();
					StringBuffer gradeObtained = new StringBuffer();

					while (resultDetailItr.hasNext()) {
						InterviewResultDetail interviewResultDetail = (InterviewResultDetail) resultDetailItr
								.next();
						if(interviewResultDetail.getGrade()!= null && interviewResultDetail.getGrade().getGrade()!= null
							&& !interviewResultDetail.getGrade().getGrade().trim().isEmpty()	){
							
							gradeObtained = gradeObtained
									.append(interviewResultDetail.getGrade()
											.getGrade());
							if (!interviewResultDetail.getGrade().getGrade().isEmpty()) {
								gradeObtained.append(',');
							}
						}
						else
						{
							if(interviewResultDetail.getPercentage()!= null){
							gradeObtained = gradeObtained
							.append(interviewResultDetail.getPercentage().toString());
							if (!interviewResultDetail.getPercentage()
											.toString().isEmpty()) {
								gradeObtained.append(',');
							}
							}
						}
						
					}
					interviewResultTO
							.setMarksObtained(gradeObtained.toString());
				}
				interviewResultTO.setComments(interviewResultBO.getComments());
				if (admApplnBo.getCourseBySelectedCourseId() != null) {
					interviewResultTO.setSelectedPrefId(String
							.valueOf(admApplnBo.getCourseBySelectedCourseId()
									.getId()));
				}
				if (interviewResultBO.getInterviewSubRounds() != null) {
					interviewResultTO.setSubroundName(interviewResultBO
							.getInterviewSubRounds().getName());
				}
				if (applicantRecommendorSet != null) {
					Iterator<ApplicantRecommendor> recommendor = applicantRecommendorSet
							.iterator();
					while (recommendor.hasNext()) {
						ApplicantRecommendor applicantRecommendorBO = (ApplicantRecommendor) recommendor
								.next();
						if (interviewResultBO.getInterviewProgramCourse() != null
								&& applicantRecommendorBO.getInterviewProgramCourse() != null
								&& interviewResultBO.getInterviewProgramCourse().getId().equals(applicantRecommendorBO
										.getInterviewProgramCourse().getId())) {
							interviewResultTO
									.setReferredBy(applicantRecommendorBO.getRecommendor().getName());
						}
					}
				}
				interviewResultList.add(interviewResultTO);
			}
		}
		return interviewResultList;
	}

	/**
	 * @param applicationId
	 * @param uploadtoList
	 * @return List<ApplnDocBO>
	 */
	public List<ApplnDoc> getDocUploadBO(int applicationId,
			List<ApplnDocTO> uploadtoList) throws Exception {
		List<ApplnDoc> uploadbolist = new ArrayList<ApplnDoc>();

		try {
			if (uploadtoList != null) {
				Iterator<ApplnDocTO> toItr = uploadtoList.iterator();
				while (toItr.hasNext()) {
					ApplnDocTO uploadto = (ApplnDocTO) toItr.next();
					ApplnDoc uploadBO = new ApplnDoc();
					AdmAppln admAppln = new AdmAppln();
					admAppln.setId(applicationId);
					DocType docType = new DocType();
					if (uploadto != null) {
						docType.setId(uploadto.getDocTypeId());
					
					uploadBO.setId(uploadto.getId());
					uploadBO.setAdmAppln(admAppln);
					uploadBO.setDocType(docType);
					uploadBO.setIsVerified(uploadto.isVerified());

					// overwrite the existing document if and only if the
					// uploaded document is not null
					if (uploadto.getDocument() != null
							&& uploadto.getDocument().getFileName().length() != 0) {
						uploadBO.setDocument(uploadto.getDocument()
								.getFileData());
						uploadBO.setName(uploadto.getDocument().getFileName());
						uploadBO.setContentType(uploadto.getDocument()
								.getContentType());
					}}
					uploadbolist.add(uploadBO);
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return uploadbolist;
	}

	/**
	 * @param applicationId
	 * @param interviewTypeId
	 * @param interviewStatusId
	 * @param gradeObtainedId
	 * @param comments
	 * @param selectedPrefId
	 * @return InterviewResultBO
	 */
	public InterviewResult getInterviewResultBO(int applicationId,
			int interviewTypeId, int interviewStatusId, int gradeObtainedId,
			String comments, int selectedPrefId, String userId,
			int interviewSubroundId) throws Exception {

		InterviewResultDetail interviewResultDetail = new InterviewResultDetail();
		InterviewResult interviewResult = new InterviewResult();
		InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
		interviewProgramCourse.setId(interviewTypeId);
		InterviewStatus interviewStatus = new InterviewStatus();
		interviewStatus.setId(interviewStatusId);
		AdmAppln admAppln = new AdmAppln();
		admAppln.setId(applicationId);
		Grade grade = new Grade();
		grade.setId(gradeObtainedId);

		interviewResultDetail.setGrade(grade);
		HashSet<InterviewResultDetail> interviewResultDetailSet = new HashSet<InterviewResultDetail>();
		interviewResultDetailSet.add(interviewResultDetail);

		if (interviewSubroundId != 0) {
			InterviewSubRounds interviewSubRounds = new InterviewSubRounds();
			interviewSubRounds.setId(interviewSubroundId);
			interviewResult.setInterviewSubRounds(interviewSubRounds);
		}
		interviewResult.setAdmAppln(admAppln);
		interviewResult.setInterviewProgramCourse(interviewProgramCourse);
		interviewResult.setInterviewStatus(interviewStatus);
		interviewResult.setComments(comments);
		interviewResult.setCreatedDate(new Date());
		interviewResult.setCreatedBy(userId);
		interviewResult.setLastModifiedDate(new Date());
		interviewResult.setModifiedBy(userId);
		interviewResult.setSelectedPreference(selectedPrefId);
		interviewResult.setIsActive(true);
		interviewResult.setInterviewResultDetails(interviewResultDetailSet);

		return interviewResult;
	}

	/**
	 * @param applicationId
	 * @param interviewTypeId
	 * @param referredById
	 * @return ApplicantRecommendorBO
	 */
	public ApplicantRecommendor getApplicantRecommendorBO(int applicationId,
			int interviewTypeId, int referredById, String userId)
			throws Exception {

		ApplicantRecommendor applicantRecommendor = new ApplicantRecommendor();
		Recommendor recommendor = new Recommendor();
		recommendor.setId(referredById);
		AdmAppln admAppln = new AdmAppln();
		admAppln.setId(applicationId);
		InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
		interviewProgramCourse.setId(interviewTypeId);
		applicantRecommendor.setAdmAppln(admAppln);
		applicantRecommendor.setRecommendor(recommendor);
		applicantRecommendor.setInterviewProgramCourse(interviewProgramCourse);
		applicantRecommendor.setCreatedDate(new Date());
		applicantRecommendor.setCreatedBy(userId);
		applicantRecommendor.setLastModifiedDate(new Date());
		applicantRecommendor.setModifiedBy(userId);

		return applicantRecommendor;
	}

	/**
	 * This method is used to convert TO to BO.
	 * 
	 * @param interviewResultList
	 * @param user
	 * @return list of type InterviewResult.
	 */
	public List<InterviewResult> convertTOtoBO(
			List<InterviewResultTO> interviewResultList, String user)
			throws Exception {
		List<InterviewResult> interviewList = new ArrayList<InterviewResult>();
		InterviewResultTO interviewResultTO;
		Iterator<InterviewResultTO> itr = interviewResultList.iterator();
		while (itr.hasNext()) {
			interviewResultTO = (InterviewResultTO) itr.next();
			InterviewResult interviewResult = new InterviewResult();

			if (interviewResultTO != null
					&& interviewResultTO.getAdmApplnTO() != null
					&& interviewResultTO.getInterviewProgramCourseTO() != null) {
				if (interviewResultTO.getAdmApplnTO() != null) {
					AdmAppln admAppln = new AdmAppln();
					admAppln.setId(interviewResultTO.getAdmApplnTO().getId());
					interviewResult.setAdmAppln(admAppln);
				}
				if (interviewResultTO.getInterviewProgramCourseTO() != null) {
					InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
					interviewProgramCourse.setId(interviewResultTO
							.getInterviewProgramCourseTO().getId());
					interviewResult
							.setInterviewProgramCourse(interviewProgramCourse);
				}
				if (interviewResultTO.getInterviewSubroundId() != 0) {
					InterviewSubRounds interviewSubRounds = new InterviewSubRounds();
					interviewSubRounds.setId(interviewResultTO
							.getInterviewSubroundId());
					interviewResult.setInterviewSubRounds(interviewSubRounds);
				}
				if (interviewResultTO.getInterviewStatusTO() != null) {
					InterviewStatus interviewStatus = new InterviewStatus();
					interviewStatus.setId(interviewResultTO
							.getInterviewStatusTO().getId());
					interviewResult.setInterviewStatus(interviewStatus);
				}

				HashSet<InterviewResultDetail> interviewResultDetailSet = new LinkedHashSet<InterviewResultDetail>();
				if (interviewResultTO.getGradeList() != null
						&& interviewResultTO.getGradeList().size() != 0) {
					InterviewResultDetail interviewResultDetail;
					Iterator<GradeTO> iterator = interviewResultTO
							.getGradeList().iterator();
					while (iterator.hasNext()) {
						Grade grade = new Grade();
						interviewResultDetail = new InterviewResultDetail();
						GradeTO gradeTO = (GradeTO) iterator.next();
						grade.setId(gradeTO.getId());
						interviewResultDetail.setGrade(grade);

						interviewResultDetailSet.add(interviewResultDetail);
					}
				}
				if (!interviewResultDetailSet.isEmpty()) {
					interviewResult
							.setInterviewResultDetails(interviewResultDetailSet);
				}

				interviewResult.setCreatedBy(user);
				interviewResult.setComments(interviewResultTO.getComments());
				interviewResult.setCreatedDate(new Date());
				interviewResult.setModifiedBy(user);
				interviewResult.setLastModifiedDate(new Date());
				interviewResult.setIsActive(true);

				interviewList.add(interviewResult);
			}
		}
		return interviewList;
	}

	public ArrayList<InterviewSubroundDisplayTO> convertToDisplay_subround(
			ArrayList<InterviewSubroundDisplayTO> interviewersPerPanel_subround) {

		ArrayList<InterviewSubroundDisplayTO> returnList = new ArrayList<InterviewSubroundDisplayTO>();
		HashMap<Integer, ArrayList<InterviewSubroundDisplayTO>> map = new HashMap<Integer, ArrayList<InterviewSubroundDisplayTO>>();
		ArrayList<InterviewSubroundDisplayTO> list;

		ArrayList<Integer> listInterviewTypes = new ArrayList<Integer>();
		// segregate the list for each interview type
		for (InterviewSubroundDisplayTO inputTO : interviewersPerPanel_subround) {
			if (map.containsKey(inputTO.getInterviewTypeId())) {
				list = map.remove(inputTO.getInterviewTypeId());
				list.add(inputTO);
				map.put(inputTO.getInterviewTypeId(), list);
			} else {
				listInterviewTypes.add(inputTO.getInterviewTypeId());
				list = new ArrayList<InterviewSubroundDisplayTO>();
				list.add(inputTO);
				map.put(inputTO.getInterviewTypeId(), list);
			}
		}
		// 

		// only one interview type
		if (listInterviewTypes.size() == 1) {
			returnList = convertToProperName(map.get(listInterviewTypes.get(0)));
		} else if (listInterviewTypes.size() > 1){
			int totalSubrounds = interviewersPerPanel_subround.size()
					/ listInterviewTypes.size();
			for (int row = 0; row < totalSubrounds; row++) {
				InterviewSubroundDisplayTO dispTO = new InterviewSubroundDisplayTO();
				for (Integer i : listInterviewTypes) {
					dispTO.setSubroundName_INTEL(map.get(i).get(row)
							.getInterviewTypeName(), map.get(i).get(row)
							.getSubroundName());
					dispTO.setInterviewTypeId(map.get(i).get(row)
							.getInterviewTypeId());
					dispTO.setNoOfInterviewers(map.get(i).get(row)
							.getNoOfInterviewers());
				}
				returnList.add(dispTO);
			}
		}
		return returnList;
	}

	private ArrayList<InterviewSubroundDisplayTO> convertToProperName(
			ArrayList<InterviewSubroundDisplayTO> input) {
		for (InterviewSubroundDisplayTO i : input) {
			i.setSubroundName_INTEL_bothPresent();
		}
		return input;
	}

	public ArrayList<InterviewSubroundDisplayTO> convertToDisplay_mainround(
			ArrayList<InterviewSubroundDisplayTO> interviewersPerPanel_mainround) {
		ArrayList<InterviewSubroundDisplayTO> returnList = new ArrayList<InterviewSubroundDisplayTO>();
		if (interviewersPerPanel_mainround != null) {
			if (interviewersPerPanel_mainround.size() == 1) {
				returnList.add(new InterviewSubroundDisplayTO(
						interviewersPerPanel_mainround.get(0)));
			} else {
				InterviewSubroundDisplayTO iTO = new InterviewSubroundDisplayTO();
				for (InterviewSubroundDisplayTO interviewSubroundDisplayTO : interviewersPerPanel_mainround) {
					iTO.setInterviewTypeName_INTEL(interviewSubroundDisplayTO
							.getInterviewTypeName());
					iTO.setInterviewTypeId(interviewSubroundDisplayTO
							.getInterviewTypeId());
					iTO.setNoOfInterviewers(interviewSubroundDisplayTO
							.getNoOfInterviewers());
				}
				returnList.add(iTO);
			}
		}

		return returnList;
	}
}