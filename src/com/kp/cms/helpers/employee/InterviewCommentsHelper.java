package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.bo.admin.EmpEducationDetails;
import com.kp.cms.bo.admin.EmpInterview;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpPreviousOrg;
import com.kp.cms.forms.employee.InterviewCommentsForm;
import com.kp.cms.to.employee.AchievementsTO;
import com.kp.cms.to.employee.EdicationDetailsTO;
import com.kp.cms.to.employee.InterviewCommentsTO;
import com.kp.cms.to.employee.ManualAttendanceEntryTO;
import com.kp.cms.to.employee.ProfessionalExperienceTO;
import com.kp.cms.utilities.CommonUtil;

public class InterviewCommentsHelper {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(InterviewCommentsHelper.class);
	public static volatile InterviewCommentsHelper objHelper = null;

	public static InterviewCommentsHelper getInstance() {
		if (objHelper == null) {
			objHelper = new InterviewCommentsHelper();
			return objHelper;
		}
		return objHelper;
	}

	public List<InterviewCommentsTO> convertBOToTO(List<Object[]> list)
			throws Exception {
		ArrayList<InterviewCommentsTO> listOfDetails = new ArrayList<InterviewCommentsTO>();
		InterviewCommentsTO TO = null;
		Object[] employeeName;

		if (list != null) {
			Iterator<Object[]> iterator = list.iterator();
			while (iterator.hasNext()) {
				TO = new InterviewCommentsTO();
				employeeName = (Object[]) iterator.next();
				if (employeeName[0] != null) {
					TO.setId(Integer.parseInt(employeeName[0].toString()));
				}
				if (employeeName[1] != null) {
					TO.setName(employeeName[1].toString());

				}
				if (employeeName[2] != null) {
					TO.setEmail(employeeName[2].toString());
				}
				if (employeeName[3] != null) {
					TO.setStatus(employeeName[3].toString());
				}
				listOfDetails.add(TO);
			}
		}

		return listOfDetails;
	}

	public EmpInterview convertFormToBO(int id,int interviewCommentsId, String dateOfInterview,
			String comments, String evaluatedBy, String marksCards,
			String experienceCertificate, String userId) {
		EmpInterview objBO = new EmpInterview();
		if(interviewCommentsId>0){
			objBO.setId(interviewCommentsId);
		}
		EmpOnlineResume oBO = new EmpOnlineResume();
		oBO.setId(id);
		objBO.setEmpOnlineResume(oBO);
		objBO.setInterviewDate(dateOfInterview != null
				&& dateOfInterview.trim().length() > 0
				&& CommonUtil.isValidDate(dateOfInterview) ? CommonUtil
				.ConvertStringToSQLDate(dateOfInterview) : null);
		objBO
				.setComments(comments != null && comments.trim().length() > 0 ? comments
						: null);
		objBO.setEvaluatedBy(evaluatedBy != null
				&& evaluatedBy.trim().length() > 0 ? evaluatedBy : null);

		if (marksCards != null && marksCards.contains("on")) {
			objBO.setIsMarksCardsVerified(true);
		} else {
			objBO.setIsMarksCardsVerified(false);
		}

		if (experienceCertificate != null
				&& experienceCertificate.contains("on")) {
			objBO.setIsExperienceCertificateVerified(true);
		} else {
			objBO.setIsExperienceCertificateVerified(false);
		}
		objBO.setCreatedBy(userId);
		objBO.setCreatedDate(new Date());
		objBO.setModifiedBy(userId);
		objBO.setLastModifiedDate(new Date());
		objBO.setIsActive(true);
		return objBO;
	}

	public InterviewCommentsTO convertBOToTO(EmpOnlineResume bo) {
		InterviewCommentsTO to = new InterviewCommentsTO();
		to.setName(bo.getName());
		to.setAddressLine1(getValue(bo.getAddressLine1()));
		to.setAddressLine2(getValue(bo.getAddressLine2()));
		to.setAddressLine3(getValue(bo.getAddressLine3()));
		if(bo.getNationality()!=null)
		to.setNationality(getValue(bo.getNationality().getName()));
		to.setZipCode(bo.getCode());
		to.setGender(bo.getGender());
		to.setCountry(bo.getCountry().getName());
		to.setMaritalStatus(bo.getMaritalStatus());
		to.setCity(bo.getCity());
		to.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil
				.getStringDate(bo.getDateOfBirth()), "dd-MMM-yyyy",
				"dd/MM/yyyy"));
		to.setPhone1(getValue(bo.getPhNo1()));
		to.setPhone2(getValue(bo.getPhNo2()));
		to.setPhone3(getValue(bo.getPhNo3()));
		if(bo.getAge()!=null)
		to.setAge(bo.getAge());
		to.setMobPhone1(getValue(bo.getMobileNo1()));
		to.setMobPhone2(getValue(bo.getMobileNo2()));
		to.setMobPhone3(getValue(bo.getMobileNo3()));
		to.setEmail(bo.getEmail());
		if (bo.getEmpJobType() != null) {
			//to.setJobType(getValue(bo.getEmpJobType().getName()));
		}

		to.setEmploymentStatus(getValue(bo.getEmploymentStatus()));
		to.setExpectedSalary(getSalary(bo.getExpectedSalaryLakhs(), bo
				.getExpectedSalaryThousands()));
		to.setDesiredPost(getValue(bo.getDesiredPost()));
		if (bo.getDepartment() != null) {
			to.setDepartmentAppliedFor(getValue(bo.getDepartment().getName()));
		}

		to.setDateOfJoining(CommonUtil.ConvertStringToDateFormat(CommonUtil
				.getStringDate(bo.getDateOfJoining()), "dd-MMM-yyyy",
				"dd/MM/yyyy"));
		to.setVacancyType(getValue(bo.getInformationKnown()));
		to.setRecommendedBy(getValue(bo.getRecommendedBy()));

		Set<EmpEducationDetails> educationDetailsSet = bo
				.getEducationDetailsSet();
		List<EdicationDetailsTO> listOfEdicationDetails = new ArrayList<EdicationDetailsTO>();
		if (educationDetailsSet != null && educationDetailsSet.size() != 0) {
			EdicationDetailsTO objEDTO = null;
			for (EmpEducationDetails ed : educationDetailsSet) {
				objEDTO = new EdicationDetailsTO();
				objEDTO.setCourse(ed.getEmpQualificationLevel().getName());
				objEDTO.setCourseName(ed.getCourseName());
				objEDTO.setYearOfPassing(ed.getPassingYear());
				objEDTO.setGrade(ed.getGrade());
				objEDTO.setInstituteUniversity(ed.getNameOfInstitute());
				listOfEdicationDetails.add(objEDTO);
			}
		}
		to.setListOfEdicationDetails(listOfEdicationDetails);

		Set<EmpAcheivement> acheivementSet = bo.getAcheivementSet();
		List<AchievementsTO> listOfAchievements = new ArrayList<AchievementsTO>();
		if (acheivementSet != null && acheivementSet.size() != 0) {

			AchievementsTO objATO = null;
			for (EmpAcheivement abo : acheivementSet) {
				objATO = new AchievementsTO();
				objATO.setAchievements(abo.getAcheivementName());
				objATO.setDetails(abo.getDetails());
				listOfAchievements.add(objATO);
			}
		}
		to.setListOfAchievements(listOfAchievements);

		Set<EmpPreviousOrg> previousOrgSet = bo.getPreviousOrgSet();
		List<ProfessionalExperienceTO> listOfProExp = new ArrayList<ProfessionalExperienceTO>();
		if (previousOrgSet != null && previousOrgSet.size() != 0) {
			ProfessionalExperienceTO proExpTO = null;
			for (EmpPreviousOrg obPOBO : previousOrgSet) {
				proExpTO = new ProfessionalExperienceTO();
				if (obPOBO.getIsCurrentlyWorking()!=null && obPOBO.getIsCurrentlyWorking()) {
					proExpTO.setCurrentlyWorking("Yes");
				} else {
					proExpTO.setCurrentlyWorking("No");
				}

				proExpTO.setTeachingExperience(getExperience(obPOBO
						.getTeachingExpYears(), obPOBO.getTeachingExpMonths()));
				if(obPOBO.getEmpEducationMaster()!=null)
				proExpTO.setEducation(obPOBO.getEmpEducationMaster().getName());
				proExpTO.setIndustryExperience(getExperience(obPOBO
						.getIndustryExpYears(), obPOBO.getIndustryExpMonths()));
				if(obPOBO.getEmpQualificationLevel()!=null)
				proExpTO.setQualificationLevel(obPOBO
						.getEmpQualificationLevel().getName());
				proExpTO.setTotalExperience(getExperience(obPOBO
						.getTotalExpYears(), obPOBO.getTotalExpMonths()));
				if(obPOBO.getEmpFunctionalArea()!=null)
				proExpTO.setFunctionalArea(obPOBO.getEmpFunctionalArea()
						.getName());

				proExpTO.setCurrentDesignation(obPOBO.getCurrentDesignation());
				proExpTO
						.setCurrentOrganisation(obPOBO.getCurrentOrganisation());
				proExpTO.setCurrentSalary(getSalary(obPOBO
						.getCurrentSalaryLakhs(), obPOBO
						.getCurrentSalaryThousands()));

				proExpTO.setPreviousOrganisation(obPOBO.getPreviousOrgName());
				listOfProExp.add(proExpTO);
			}
		}
		to.setListOfProfessionalExperience(listOfProExp);

		return to;
	}

	private String getSalary(Integer currentSalaryLakhs,
			Integer currentSalaryThousands) {
		String salary = "";
		if (currentSalaryLakhs != null && currentSalaryLakhs > 0) {
			salary = currentSalaryLakhs.toString();
		}
		if (currentSalaryThousands != null && currentSalaryThousands > 0) {
			salary = salary + "." + currentSalaryThousands.toString()
					+ " lakhs(per annum)";
		} else {
			salary = salary + " Thousands(per annum)";
		}
		return salary;
	}

	private String getExperience(Integer teachingExpYears,
			Integer teachingExpMonths) {
		String experience = "";
		if (teachingExpYears != null && teachingExpYears > 0) {
			experience = teachingExpYears.toString() + " Years ";
		}
		if (teachingExpMonths != null && teachingExpMonths > 0) {
			experience = experience + teachingExpMonths.toString() + " Months";
		}
		return experience;
	}

	private String getValue(String value) {
		if (value != null && value.trim().length() > 0) {
			return value;
		} else {
			return "";
		}

	}
	
	public List<EmpOnlineResume> convertFormToBO(List<InterviewCommentsTO> listOfDetails) {
	
		List<EmpOnlineResume>onlineResumeList=new ArrayList<EmpOnlineResume>();
		EmpOnlineResume onlineResumeObj=null;
		for(InterviewCommentsTO details:listOfDetails)
		{
			if(!details.getStatus().equalsIgnoreCase(""))
			{	
				onlineResumeObj=new EmpOnlineResume();
				onlineResumeObj.setId(details.getId());
				onlineResumeObj.setStatus(details.getStatus());
				onlineResumeList.add(onlineResumeObj);
			}	
		}
		return onlineResumeList;
	}
}
