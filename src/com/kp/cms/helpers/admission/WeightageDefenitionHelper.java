package com.kp.cms.helpers.admission;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantWorkExperience;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewResultDetail;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.Weightage;
import com.kp.cms.bo.admin.WeightageDefinition;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admission.CasteWeightageTO;
import com.kp.cms.to.admission.CountryWeightageTO;
import com.kp.cms.to.admission.CoursePrerequisiteWeightageTO;
import com.kp.cms.to.admission.EducationalWeightageAdjustedTO;
import com.kp.cms.to.admission.EducationalWeightageDefenitionTO;
import com.kp.cms.to.admission.GenderWeightageTO;
import com.kp.cms.to.admission.GeneralWeightageAdjustedTO;
import com.kp.cms.to.admission.InstituteWeightageTO;
import com.kp.cms.to.admission.InterviewSubroundDisplayTO;
import com.kp.cms.to.admission.InterviewWeightageAdjustedTO;
import com.kp.cms.to.admission.InterviewWeightageDefenitionTO;
import com.kp.cms.to.admission.NationalityWeightageTO;
import com.kp.cms.to.admission.PreviousQualificationWeightageTO;
import com.kp.cms.to.admission.ReligionWeightageTO;
import com.kp.cms.to.admission.ResidentCategoryWeightageTO;
import com.kp.cms.to.admission.RuralUrbanWeightageTO;
import com.kp.cms.to.admission.SubReligionWeightageTO;
import com.kp.cms.to.admission.TotalWeightageAdjustedTO;
import com.kp.cms.to.admission.UniversityWeightageTO;
import com.kp.cms.to.admission.WorkExperienceWeightageTO;
import com.kp.cms.transactions.admission.IWeightageEntryTransaction;
import com.kp.cms.transactionsimpl.admission.WeightageEntryTransactionImpl;

/**
 * Singleton class for WeightageDefenitionHelper
 *
 */
public class WeightageDefenitionHelper {

	/**
	 * Single ton object of the WeightageDefenitionHelper object.
	 */
	private static volatile WeightageDefenitionHelper weightageDefenitionHelper = null;
	private static final Log log = LogFactory.getLog(WeightageDefenitionHelper.class);
	private WeightageDefenitionHelper() {

	}

	/**
	 * 
	 * @return Single ton object of the WeightageDefenitionHelper object.
	 */
	public static WeightageDefenitionHelper getInstance() {
		if (weightageDefenitionHelper == null) {
			weightageDefenitionHelper = new WeightageDefenitionHelper();
		}
		return weightageDefenitionHelper;

	}

	/**
	 * Converts from DocChecklistBo to EducationalWeightageDefenitionTO
	 * 
	 * @param docCheckList
	 *            - Represents DocCheckList
	 * @return EducationalWeightageDefenitionTO List
	 */
	public List<EducationalWeightageDefenitionTO> convertBoToTO(
			List<DocChecklist> docCheckList) {
		log.info("entering into convertBoToTO of WeightageDefenitionHelper class.");
		List<EducationalWeightageDefenitionTO> educationToList = new ArrayList<EducationalWeightageDefenitionTO>();
		if (docCheckList != null) {
			Iterator<DocChecklist> docCheckListIterator = docCheckList
					.iterator();
			while (docCheckListIterator.hasNext()) {
				DocChecklist docChecklistBo = (DocChecklist) docCheckListIterator
						.next();
				EducationalWeightageDefenitionTO educationalWeightageDefenitionTO = new EducationalWeightageDefenitionTO();
				educationalWeightageDefenitionTO
						.setDocCheckListId(docChecklistBo.getId());
				educationalWeightageDefenitionTO.setDocumentName(docChecklistBo
						.getDocType().getName());
				if (docChecklistBo.getWeightage() != null) {
					educationalWeightageDefenitionTO
							.setWeightageId(docChecklistBo.getWeightage()
									.getId());
				}
				if (docChecklistBo.getWeightagePercentage() != null) {
					educationalWeightageDefenitionTO
							.setWeightagePercentage(docChecklistBo
									.getWeightagePercentage().toString());
				}

				educationToList.add(educationalWeightageDefenitionTO);
			}
		}
		log.info("exit of convertBoToTO of WeightageDefenitionHelper class.");
		return educationToList;
	}

	/**
	 * Converts from InterviewProgramCourse to InterviewWeightageDefenitionTO
	 * 
	 * @param interviewProgramCourse
	 *            - Represents InterviewProgramCourse
	 * @return InterviewWeightageDefenitionTO List
	 */
	public List<InterviewWeightageDefenitionTO> convertBoToInterviewWeightageDefenitionTO(
			List<InterviewProgramCourse> interviewProgramCourse) {
		log.info("entering into convertBoToInterviewWeightageDefenitionTO of WeightageDefenitionHelper class.");
		List<InterviewWeightageDefenitionTO> interviewTypeList = new ArrayList<InterviewWeightageDefenitionTO>();
		
		if (interviewProgramCourse != null) {
			Iterator<InterviewProgramCourse> interviewProgramCourseIterator = interviewProgramCourse
					.iterator();
			while (interviewProgramCourseIterator.hasNext()) {
				InterviewProgramCourse interviewProgramCourseBo = (InterviewProgramCourse) interviewProgramCourseIterator
						.next();

				InterviewWeightageDefenitionTO interviewWeightageDefenitionTO = new InterviewWeightageDefenitionTO();
				Set<InterviewSubRounds> subRoundSet = interviewProgramCourseBo.getInterviewSubRoundses();
				InterviewSubroundDisplayTO interviewSubroundDisplayTO;
				List<InterviewSubroundDisplayTO> subRoundTOList = new ArrayList<InterviewSubroundDisplayTO>();
				if(subRoundSet!= null && subRoundSet.size() > 0){
					Iterator<InterviewSubRounds> intIterator = subRoundSet.iterator();
					while (intIterator.hasNext()) {
						InterviewSubRounds interviewSubRounds = (InterviewSubRounds) intIterator
								.next();
						interviewSubroundDisplayTO = new InterviewSubroundDisplayTO();
						interviewSubroundDisplayTO.setInterviewTypeId(interviewSubRounds.getInterviewProgramCourse().getId());
						interviewSubroundDisplayTO.setInterviewTypeName(interviewSubRounds.getInterviewProgramCourse().getName());
						interviewSubroundDisplayTO.setSubroundId(interviewSubRounds.getId());
						interviewSubroundDisplayTO.setSubroundName(interviewSubRounds.getName());
						if(interviewSubRounds.getWeightage()!= null){
							interviewSubroundDisplayTO.setWeightageId(interviewSubRounds.getWeightage().getId());
						}
						if(interviewSubRounds.getWeightagePercentage()!= null){
							interviewSubroundDisplayTO.setWeightagePercentage(interviewSubRounds.getWeightagePercentage().toString());
						}
						subRoundTOList.add(interviewSubroundDisplayTO);
					}
					interviewWeightageDefenitionTO.setSubRoundList(subRoundTOList);
				}

				interviewWeightageDefenitionTO
						.setInterviewProgramCourseId(interviewProgramCourseBo
								.getId());
				interviewWeightageDefenitionTO
						.setInterviewType(interviewProgramCourseBo.getName());
				if (interviewProgramCourseBo.getWeightage() != null) {
					interviewWeightageDefenitionTO
							.setWeightageId(interviewProgramCourseBo
									.getWeightage().getId());

				}
				if (interviewProgramCourseBo.getWeightagePercentage() != null) {
					interviewWeightageDefenitionTO
							.setWeightagePercentage(interviewProgramCourseBo
									.getWeightagePercentage().toString());
				}
				interviewTypeList.add(interviewWeightageDefenitionTO);

			}
		}
		log.info("exit of convertBoToInterviewWeightageDefenitionTO of WeightageDefenitionHelper class.");
		return interviewTypeList;
	}

	/**
	 * Converts from BO to TO
	 * @param ruralBO
	 * @param urbanBO
	 * @return
	 */
	public List<RuralUrbanWeightageTO> convertBoToRuralUrbanWeightageTOList(
			WeightageDefinition ruralBO, WeightageDefinition urbanBO) {
		log.info("entering into convertBoToRuralUrbanWeightageTOList of WeightageDefenitionHelper class.");
		List<RuralUrbanWeightageTO> ruralUrbanWeightageTOlist = new ArrayList<RuralUrbanWeightageTO>();

		if (ruralBO == null && urbanBO == null) {
			RuralUrbanWeightageTO ruralWeightageTO = new RuralUrbanWeightageTO();
			ruralWeightageTO.setRuralUrban("Rural");
			ruralUrbanWeightageTOlist.add(ruralWeightageTO);
			RuralUrbanWeightageTO urbanWeightageTO = new RuralUrbanWeightageTO();
			urbanWeightageTO.setRuralUrban("Urban");
			ruralUrbanWeightageTOlist.add(urbanWeightageTO);

		} 	else if (ruralBO != null && urbanBO != null) {
			RuralUrbanWeightageTO urbanWeightageTO = new RuralUrbanWeightageTO();
			urbanWeightageTO.setRuralUrban("Urban");
			urbanWeightageTO.setWeightageId(urbanBO.getId());
			if (urbanBO.getWeightage_1() != null) {
				urbanWeightageTO.setWeightagePercentage(urbanBO
						.getWeightage_1().toString());
			}
			ruralUrbanWeightageTOlist.add(urbanWeightageTO);

			RuralUrbanWeightageTO ruralWeightageTO = new RuralUrbanWeightageTO();
			ruralWeightageTO.setRuralUrban("Rural");
			ruralWeightageTO.setWeightageId(ruralBO.getId());
			if (ruralBO.getWeightage_1() != null) {
				ruralWeightageTO.setWeightagePercentage(ruralBO
						.getWeightage_1().toString());
			}
			ruralUrbanWeightageTOlist.add(ruralWeightageTO);
		} else if (ruralBO != null) {

			RuralUrbanWeightageTO ruralWeightageTO = new RuralUrbanWeightageTO();
			ruralWeightageTO.setRuralUrban("Rural");
			ruralWeightageTO.setWeightageId(ruralBO.getId());
			if (ruralBO.getWeightage_1() != null) {
				ruralWeightageTO.setWeightagePercentage(ruralBO
						.getWeightage_1().toString());
			}
			ruralUrbanWeightageTOlist.add(ruralWeightageTO);

			RuralUrbanWeightageTO urbanWeightageTO = new RuralUrbanWeightageTO();
			urbanWeightageTO.setRuralUrban("Urban");
			ruralUrbanWeightageTOlist.add(urbanWeightageTO);

		} else if (urbanBO != null) {

			RuralUrbanWeightageTO urbanWeightageTO = new RuralUrbanWeightageTO();
			urbanWeightageTO.setRuralUrban("Urban");
			urbanWeightageTO.setWeightageId(urbanBO.getId());
			if (urbanBO.getWeightage_1() != null) {
				urbanWeightageTO.setWeightagePercentage(urbanBO
						.getWeightage_1().toString());
			}
			ruralUrbanWeightageTOlist.add(urbanWeightageTO);
			RuralUrbanWeightageTO ruralWeightageTO = new RuralUrbanWeightageTO();
			ruralWeightageTO.setRuralUrban("Rural");
			ruralUrbanWeightageTOlist.add(ruralWeightageTO);

		} 
		log.info("exit of convertBoToRuralUrbanWeightageTOList of WeightageDefenitionHelper class.");
		return ruralUrbanWeightageTOlist;

	}

	/**
	 * Get the general weightage types
	 * @return
	 */
	public Map<Integer, String> getWeightageType() {
		log.info("entering into getWeightageType of WeightageDefenitionHelper class.");
		Map<Integer, String> weightageTypeMap = new HashMap<Integer, String>();

		weightageTypeMap.put(1, "Belongs To");
		weightageTypeMap.put(2, "Caste Category");
		weightageTypeMap.put(4, "Country");
		weightageTypeMap.put(5, "Gender");
		weightageTypeMap.put(6, "Institute");
		weightageTypeMap.put(7, "Nationality");
		weightageTypeMap.put(8, "Religion");
		weightageTypeMap.put(9, "Resident Category");
		weightageTypeMap.put(3, "Sub Religion");
		weightageTypeMap.put(10, "University");
		weightageTypeMap.put(11, "Previous Qualification");
		weightageTypeMap.put(12,"Work Experience");
		log.info("exit of getWeightageType of WeightageDefenitionHelper class.");
		return weightageTypeMap;

	}

	/**
	 * Converts from BO to TO
	 * @param countryWeightageBO
	 * @return
	 */
	public List<CountryWeightageTO> convertBoTocountryWeightageTOList(
			List countryWeightageBO) {
		log.info("entering into convertBoTocountryWeightageTOList of WeightageDefenitionHelper class.");
		List<CountryWeightageTO> countryWeightageTOList = new ArrayList<CountryWeightageTO>();
		if (countryWeightageBO != null) {
			Iterator countryWeightageIterator = countryWeightageBO.iterator();
			while (countryWeightageIterator.hasNext()) {
				Object[] weightageDefinition = (Object[]) countryWeightageIterator
						.next();
				CountryWeightageTO countryWeightageTO = new CountryWeightageTO();
				countryWeightageTO.setCountryId(Integer
						.valueOf(weightageDefinition[0].toString()));
				countryWeightageTO.setCountryName(weightageDefinition[1]
						.toString());
				if (weightageDefinition[2] != null) {
					countryWeightageTO.setWeightageId(Integer
							.valueOf(weightageDefinition[2].toString()));
				}
				if (weightageDefinition[3] != null) {
					countryWeightageTO
							.setWeightagePercentage(weightageDefinition[3]
									.toString());
				}
				countryWeightageTOList.add(countryWeightageTO);
			}
		}
		log.info("exit of convertBoTocountryWeightageTOList of WeightageDefenitionHelper class.");
		return countryWeightageTOList;
	}

	/**
	 * Converts from BO to TO
	 * @param maleWeightageBO
	 * @param femaleWeightageBO
	 * @return
	 */
	public List<GenderWeightageTO> convertBoTogenderWeightageTOList(
			WeightageDefinition maleWeightageBO,WeightageDefinition femaleWeightageBO  ) {
		log.info("entering into convertBoTogenderWeightageTOList of WeightageDefenitionHelper class.");

		List<GenderWeightageTO> genderWeightageTOlist = new ArrayList<GenderWeightageTO>();
		if (maleWeightageBO == null && femaleWeightageBO == null) {
			GenderWeightageTO maleWeightageTO = new GenderWeightageTO();
			maleWeightageTO.setGetnder("Male");
			genderWeightageTOlist.add(maleWeightageTO);
			GenderWeightageTO femaleWeightageTO = new GenderWeightageTO();
			femaleWeightageTO.setGetnder("Female");
			genderWeightageTOlist.add(femaleWeightageTO);

		}else if(maleWeightageBO != null && femaleWeightageBO != null) {

			GenderWeightageTO maleWeightageTO = new GenderWeightageTO();
			maleWeightageTO.setGetnder("Male");
			maleWeightageTO.setWeightageId(maleWeightageBO
					.getId());
			if (maleWeightageBO.getWeightage_1() != null) {
				maleWeightageTO
						.setWeightagePercentage(maleWeightageBO
								.getWeightage_1().toString());
			}
			genderWeightageTOlist.add(maleWeightageTO);
			
			GenderWeightageTO femaleWeightageTO = new GenderWeightageTO();
			femaleWeightageTO.setGetnder("Female");
			femaleWeightageTO
					.setWeightageId(femaleWeightageBO.getId());
			if (femaleWeightageBO.getWeightage_1() != null) {
				femaleWeightageTO
						.setWeightagePercentage(femaleWeightageBO
								.getWeightage_1().toString());
			}
			genderWeightageTOlist.add(femaleWeightageTO);
		} 
		else if(maleWeightageBO != null) {
			GenderWeightageTO maleWeightageTO = new GenderWeightageTO();
			maleWeightageTO.setGetnder("Male");
			maleWeightageTO.setWeightageId(maleWeightageBO
					.getId());
			if (maleWeightageBO.getWeightage_1() != null) {
				maleWeightageTO
						.setWeightagePercentage(maleWeightageBO
								.getWeightage_1().toString());
			}
			genderWeightageTOlist.add(maleWeightageTO);
			GenderWeightageTO femaleWeightageTO = new GenderWeightageTO();
			femaleWeightageTO.setGetnder("Female");
			genderWeightageTOlist.add(femaleWeightageTO);
		
		} else if (femaleWeightageBO != null) {
			GenderWeightageTO femaleWeightageTO = new GenderWeightageTO();
			femaleWeightageTO.setGetnder("Female");
			femaleWeightageTO
					.setWeightageId(femaleWeightageBO.getId());
			if (femaleWeightageBO.getWeightage_1() != null) {
				femaleWeightageTO
						.setWeightagePercentage(femaleWeightageBO
								.getWeightage_1().toString());
			}
			genderWeightageTOlist.add(femaleWeightageTO);
			GenderWeightageTO maleWeightageTO = new GenderWeightageTO();
			maleWeightageTO.setGetnder("Male");
			genderWeightageTOlist.add(maleWeightageTO);

		
		} 
		log.info("exit of convertBoTogenderWeightageTOList of WeightageDefenitionHelper class.");
		return genderWeightageTOlist;

	}

	/**
	 * Converts from BO to TO
	 * @param instituteWeightageBO
	 * @return
	 */
	public List<InstituteWeightageTO> convertBoToinstituteWeightageTOList(
			List instituteWeightageBO) {
		log.info("entering into convertBoToinstituteWeightageTOList of WeightageDefenitionHelper class.");
		List<InstituteWeightageTO> instituteWeightageTOList = new ArrayList<InstituteWeightageTO>();
		if (instituteWeightageBO != null) {
			Iterator instituteWeightageIterator = instituteWeightageBO
					.iterator();
			while (instituteWeightageIterator.hasNext()) {
				Object[] weightageDefinition = (Object[]) instituteWeightageIterator
						.next();
				InstituteWeightageTO instititeWeightageTO = new InstituteWeightageTO();
				instititeWeightageTO.setInstituteId(Integer
						.valueOf(weightageDefinition[0].toString()));
				instititeWeightageTO.setInstituteName(weightageDefinition[1]
						.toString());
				if (weightageDefinition[2] != null) {
					instititeWeightageTO.setWeightageId(Integer
							.valueOf(weightageDefinition[2].toString()));
				}
				if (weightageDefinition[3] != null) {
					instititeWeightageTO
							.setWeightagePercentage(weightageDefinition[3]
									.toString());
				}
				instituteWeightageTOList.add(instititeWeightageTO);
			}
		}
		log.info("exit of convertBoToinstituteWeightageTOList of WeightageDefenitionHelper class.");
		return instituteWeightageTOList;

	}

	public List<NationalityWeightageTO> convertBoTonationalityWeightageTOList(
			List nationalityWeightageBO) {
		log.info("entering into convertBoTonationalityWeightageTOList of WeightageDefenitionHelper class.");
		List<NationalityWeightageTO> nationalityWeightageTOList = new ArrayList<NationalityWeightageTO>();

	//	List nationalityIdList = new ArrayList();

		if (nationalityWeightageBO != null) {
			Iterator nationalityWeightageIterator = nationalityWeightageBO
					.iterator();
			while (nationalityWeightageIterator.hasNext()) {
				Object[] weightageDefinition = (Object[]) nationalityWeightageIterator
						.next();
				NationalityWeightageTO nationalityWeightageTO = new NationalityWeightageTO();
				nationalityWeightageTO.setNationalityId(Integer
						.valueOf(weightageDefinition[0].toString()));
				//nationalityIdList.add(weightageDefinition[0].toString());
				nationalityWeightageTO
						.setNationalityName(weightageDefinition[1].toString());
				if (weightageDefinition[2] != null) {
					nationalityWeightageTO.setWeightageId(Integer
							.valueOf(weightageDefinition[2].toString()));
				}
				if (weightageDefinition[3] != null) {
					nationalityWeightageTO
							.setWeightagePercentage(weightageDefinition[3]
									.toString());
				}
				nationalityWeightageTOList.add(nationalityWeightageTO);
			}
		}
		log.info("exit of convertBoTonationalityWeightageTOList of WeightageDefenitionHelper class.");
		return nationalityWeightageTOList;

	}

	/**
	 * Converts from BO to TO
	 * @param religionWeightageBO
	 * @return
	 */
	public List<ReligionWeightageTO> convertBoToReligionWeightageTOList(
			List religionWeightageBO) {
		log.info("entering into convertBoToReligionWeightageTOList of WeightageDefenitionHelper class.");
		List<ReligionWeightageTO> religionWeightageTOList = new ArrayList<ReligionWeightageTO>();
		if (religionWeightageBO != null) {
			Iterator religionWeightageIterator = religionWeightageBO.iterator();
			while (religionWeightageIterator.hasNext()) {
				Object[] weightageDefinition = (Object[]) religionWeightageIterator
						.next();

				ReligionWeightageTO religionWeightageTO = new ReligionWeightageTO();
				religionWeightageTO.setReligionId(Integer
						.valueOf(weightageDefinition[0].toString()));
				religionWeightageTO.setReligionName(weightageDefinition[1]
						.toString());
				if (weightageDefinition[2] != null) {
					religionWeightageTO.setWeightageId(Integer
							.valueOf(weightageDefinition[2].toString()));
				}
				if (weightageDefinition[3] != null) {
					religionWeightageTO
							.setWeightagePercentage(weightageDefinition[3]
									.toString());
				}

				religionWeightageTOList.add(religionWeightageTO);
			}
		}
		log.info("exit of convertBoToReligionWeightageTOList of WeightageDefenitionHelper class.");
		return religionWeightageTOList;

	}

	/**
	 * Converts from BO to TO
	 * @param residentCategoryWeightageBO
	 * @return
	 */
	public List<ResidentCategoryWeightageTO> convertBoToResidentCategoryWeightageTOList(
			List residentCategoryWeightageBO) {
		log.info("entering into convertBoToResidentCategoryWeightageTOList of WeightageDefenitionHelper class.");
		List<ResidentCategoryWeightageTO> residentCategoryWeightageTOList = new ArrayList<ResidentCategoryWeightageTO>();
		if (residentCategoryWeightageBO != null) {
			Iterator residentCategoryWeightageIterator = residentCategoryWeightageBO
					.iterator();
			while (residentCategoryWeightageIterator.hasNext()) {
				Object[] weightageDefinition = (Object[]) residentCategoryWeightageIterator
						.next();

				ResidentCategoryWeightageTO residentCategoryWeightageTO = new ResidentCategoryWeightageTO();
				residentCategoryWeightageTO.setReseidentCategoryId(Integer
						.valueOf(weightageDefinition[0].toString()));
				residentCategoryWeightageTO
						.setReseidentCategoryName(weightageDefinition[1]
								.toString());
				if (weightageDefinition[2] != null) {
					residentCategoryWeightageTO.setWeightageId(Integer
							.valueOf(weightageDefinition[2].toString()));
				}
				if (weightageDefinition[3] != null) {
					residentCategoryWeightageTO
							.setWeightagePercentage(weightageDefinition[3]
									.toString());
				}

				residentCategoryWeightageTOList
						.add(residentCategoryWeightageTO);
			}
		}
		log.info("exit of convertBoToResidentCategoryWeightageTOList of WeightageDefenitionHelper class.");

		return residentCategoryWeightageTOList;

	}

	/**
	 * Converts from BO to TO
	 * @param universityWeightageBO
	 * @return
	 */
	public List<UniversityWeightageTO> convertBoToUniversityWeightageTOList(
			List universityWeightageBO) {
		log.info("entering into convertBoToUniversityWeightageTOList of WeightageDefenitionHelper class.");
		List<UniversityWeightageTO> universityWeightageTOList = new ArrayList<UniversityWeightageTO>();
		if (universityWeightageBO != null) {
			Iterator universityWeightageIterator = universityWeightageBO
					.iterator();
			while (universityWeightageIterator.hasNext()) {
				Object[] weightageDefinition = (Object[]) universityWeightageIterator
						.next();

				UniversityWeightageTO universityWeightageTO = new UniversityWeightageTO();
				universityWeightageTO.setUniversityId(Integer
						.valueOf(weightageDefinition[0].toString()));
				universityWeightageTO.setUniversityName(weightageDefinition[1]
						.toString());
				if (weightageDefinition[2] != null) {
					universityWeightageTO.setWeightageId(Integer
							.valueOf(weightageDefinition[2].toString()));
				}
				if (weightageDefinition[3] != null) {
					universityWeightageTO
							.setWeightagePercentage(weightageDefinition[3]
									.toString());
				}

				universityWeightageTOList.add(universityWeightageTO);
			}
		}

		log.info("exit of convertBoToUniversityWeightageTOList of WeightageDefenitionHelper class.");
		return universityWeightageTOList;

	}

	/**
	 * Converts from BO to TO
	 * @param religionSectionWeightageBO
	 * @return
	 */
	public List<SubReligionWeightageTO> convertBoToReligionSectionWeightageTOList(
			List religionSectionWeightageBO) {
		log.info("entering into convertBoToReligionSectionWeightageTOList of WeightageDefenitionHelper class.");
		List<SubReligionWeightageTO> religionSectionWeightageTOList = new ArrayList<SubReligionWeightageTO>();
		if (religionSectionWeightageBO != null) {
			Iterator religionSectionWeightageIterator = religionSectionWeightageBO
					.iterator();
		
			while (religionSectionWeightageIterator.hasNext()) {
				Object[] weightageDefinition = (Object[]) religionSectionWeightageIterator
						.next();

				SubReligionWeightageTO religionSectionWeightageTO = new SubReligionWeightageTO();
				religionSectionWeightageTO.setReligionSectionId(Integer
						.valueOf(weightageDefinition[0].toString()));
				religionSectionWeightageTO
						.setReligionSectionName(weightageDefinition[1].toString());
				if (weightageDefinition[2] != null) {
					religionSectionWeightageTO.setWeightageId(Integer
							.valueOf(weightageDefinition[2].toString()));
				}
				if (weightageDefinition[3] != null) {
					religionSectionWeightageTO
							.setWeightagePercentage(weightageDefinition[3]
									.toString());
				}

				religionSectionWeightageTOList.add(religionSectionWeightageTO);
			}
		}
		log.info("exit of convertBoToReligionSectionWeightageTOList of WeightageDefenitionHelper class.");

		return religionSectionWeightageTOList;

	}

	/**
	 * Converts from BO to TO
	 * @param casteWeightageBO
	 * @return
	 */
	public List<CasteWeightageTO> convertBoToCasteWeightageTOList(
			List casteWeightageBO) {
		log.info("entering into convertBoToCasteWeightageTOList of WeightageDefenitionHelper class.");
		List<CasteWeightageTO> casteWeightageTOList = new ArrayList<CasteWeightageTO>();
		if (casteWeightageBO != null) {
			Iterator casteCategoryWeightageIterator = casteWeightageBO
					.iterator();
			while (casteCategoryWeightageIterator.hasNext()) {
				Object[] weightageDefinition = (Object[]) casteCategoryWeightageIterator
						.next();

				CasteWeightageTO casteWeightageTO = new CasteWeightageTO();
				casteWeightageTO.setCasteId(Integer
						.valueOf(weightageDefinition[0].toString()));
				casteWeightageTO
						.setCasteName(weightageDefinition[1].toString());
				if (weightageDefinition[2] != null) {
					casteWeightageTO.setWeightageId(Integer
							.valueOf(weightageDefinition[2].toString()));
				}
				if (weightageDefinition[3] != null) {
					casteWeightageTO
							.setWeightagePercentage(weightageDefinition[3]
									.toString());
				}

				casteWeightageTOList.add(casteWeightageTO);
			}
		}
		log.info("exit of convertBoToCasteWeightageTOList of WeightageDefenitionHelper class.");
		return casteWeightageTOList;
	}
	
	// for previous qualification option in general weightage dropdownList
	
	public List<PreviousQualificationWeightageTO> convertBoToPreviousQualificationWeightageTOList(
			List<DocChecklist> qualificationWeightageBO,int courseId,int year)throws ApplicationException {
		log.info("entering into convertBoToPreviousQualificationWeightageTOList of WeightageDefenitionHelper class.");
		List<PreviousQualificationWeightageTO> qualificationWeightageTOList = new ArrayList<PreviousQualificationWeightageTO>();
		if (qualificationWeightageBO != null) {
			Iterator qualificationWeightageIterator = qualificationWeightageBO.iterator();
			   while (qualificationWeightageIterator.hasNext()) {   
				DocChecklist docChecklistBo = (DocChecklist) qualificationWeightageIterator.next();
				IWeightageEntryTransaction weightageDefinitionEntry = new WeightageEntryTransactionImpl();
				if(docChecklistBo!=null)
				{
					if(docChecklistBo.getDocType()!=null)
				    {	                                 					
						if(docChecklistBo.getDocType().getDocTypeExamses()!=null){
							for(DocTypeExams exam:docChecklistBo.getDocType().getDocTypeExamses())
							{
								PreviousQualificationWeightageTO qualificationWeightageTO = new PreviousQualificationWeightageTO();
								if(docChecklistBo.getDocType()!=null){
									qualificationWeightageTO.setDocTypeName(docChecklistBo.getDocType().getName());
								}
								qualificationWeightageTO.setDocTypeExamId(exam.getId());
								qualificationWeightageTO.setExamName(exam.getName());
								Weightage weightage=weightageDefinitionEntry.getWeightageByCourseAndYear(courseId, year);
								if(weightage!=null){
									
									WeightageDefinition weightageDefinition=weightageDefinitionEntry.
										   getWeightageDefenitionByWeightageIdAndDocTypeExamId(weightage.getId(),exam.getId());
									if(weightageDefinition!=null){
										qualificationWeightageTO.setWeightageId(weightageDefinition.getId());
										if(weightageDefinition.getWeightage_1()!=null){
											qualificationWeightageTO.setWeightagePercentage(weightageDefinition.getWeightage_1().toString());
										}
									}
								}
								qualificationWeightageTOList.add(qualificationWeightageTO);
						    }
						}
				      }
				    }				
			      }
		      }
			log.info("exit of convertBoToUniversityWeightageTOList of WeightageDefenitionHelper class.");
			return qualificationWeightageTOList;
		    
			} 
	// work experience weightage in general dropdown
	
	public List<WorkExperienceWeightageTO> convertBoToWorkExperienceWeightageTOList(
			WeightageDefinition for0to1yearBO, WeightageDefinition for1to2yearsBO,
			WeightageDefinition for2to3yearsBO,WeightageDefinition for3to4yearsBO,
			WeightageDefinition for4to5yearsBO) {
		log.info("entering into convertBoToWorkExperienceWeightageTOList of WeightageDefenitionHelper class.");
		
		List<WorkExperienceWeightageTO> workExperienceWeightageTOlist = new ArrayList<WorkExperienceWeightageTO>();

		if (for0to1yearBO == null && for1to2yearsBO == null && for2to3yearsBO== null && 
				for3to4yearsBO==null && for4to5yearsBO==null) {
			
			WorkExperienceWeightageTO for0to1YearWeightageTO = new WorkExperienceWeightageTO();
			for0to1YearWeightageTO.setExperienceName("0 - 1 year");
			workExperienceWeightageTOlist.add(for0to1YearWeightageTO);
			
			WorkExperienceWeightageTO for1to2YearWeightageTO = new WorkExperienceWeightageTO();
			for1to2YearWeightageTO.setExperienceName("1 - 2 years");
			workExperienceWeightageTOlist.add(for1to2YearWeightageTO);
			
			WorkExperienceWeightageTO for2to3YearWeightageTO = new WorkExperienceWeightageTO();
			for2to3YearWeightageTO.setExperienceName("2 - 3 years");
			workExperienceWeightageTOlist.add(for2to3YearWeightageTO);
			
			WorkExperienceWeightageTO for3to4YearWeightageTO = new WorkExperienceWeightageTO();
			for3to4YearWeightageTO.setExperienceName("3 - 4 years");
			workExperienceWeightageTOlist.add(for3to4YearWeightageTO);
			
			WorkExperienceWeightageTO for4to5YearWeightageTO = new WorkExperienceWeightageTO();
			for4to5YearWeightageTO.setExperienceName("4 - 5 years");
			workExperienceWeightageTOlist.add(for4to5YearWeightageTO);
			

		} 	else if (for0to1yearBO != null && for1to2yearsBO != null && for2to3yearsBO != null && 
				for3to4yearsBO!= null && for4to5yearsBO!= null) {
			
			WorkExperienceWeightageTO for0to1YearWeightageTO = new WorkExperienceWeightageTO();
			for0to1YearWeightageTO.setExperienceName("0 - 1 year");
			for0to1YearWeightageTO.setWeightageId(for0to1yearBO.getId());
			if (for0to1yearBO.getWeightage_1() != null) {
				for0to1YearWeightageTO.setWeightagePercentage(for0to1yearBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for0to1YearWeightageTO);

			WorkExperienceWeightageTO for1to2YearWeightageTO = new WorkExperienceWeightageTO();
			for1to2YearWeightageTO.setExperienceName("1 - 2 years");
			for1to2YearWeightageTO.setWeightageId(for1to2yearsBO.getId());
			if (for1to2yearsBO.getWeightage_1() != null) {
				for1to2YearWeightageTO.setWeightagePercentage(for1to2yearsBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for1to2YearWeightageTO);
			
			WorkExperienceWeightageTO for2to3YearWeightageTO = new WorkExperienceWeightageTO();
			for2to3YearWeightageTO.setExperienceName("2 - 3 years");
			for2to3YearWeightageTO.setWeightageId(for2to3yearsBO.getId());
			if (for2to3yearsBO.getWeightage_1() != null) {
				for2to3YearWeightageTO.setWeightagePercentage(for2to3yearsBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for2to3YearWeightageTO);
			
			WorkExperienceWeightageTO for3to4YearWeightageTO = new WorkExperienceWeightageTO();
			for3to4YearWeightageTO.setExperienceName("3 - 4 years");
			for3to4YearWeightageTO.setWeightageId(for3to4yearsBO.getId());
			if (for3to4yearsBO.getWeightage_1() != null) {
				for3to4YearWeightageTO.setWeightagePercentage(for3to4yearsBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for3to4YearWeightageTO);
			
			WorkExperienceWeightageTO for4to5YearWeightageTO = new WorkExperienceWeightageTO();
			for4to5YearWeightageTO.setExperienceName("4 - 5 years");
			for4to5YearWeightageTO.setWeightageId(for4to5yearsBO.getId());
			if (for4to5yearsBO.getWeightage_1() != null) {
				for4to5YearWeightageTO.setWeightagePercentage(for4to5yearsBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for4to5YearWeightageTO);
		} else if (for0to1yearBO != null) {

			WorkExperienceWeightageTO for0to1yearWeightageTO = new WorkExperienceWeightageTO();
			for0to1yearWeightageTO.setExperienceName("0 - 1 year");
			for0to1yearWeightageTO.setWeightageId(for0to1yearBO.getId());
			if (for0to1yearBO.getWeightage_1() != null) {
				for0to1yearWeightageTO.setWeightagePercentage(for0to1yearBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for0to1yearWeightageTO);

		}else if (for1to2yearsBO != null) {

			WorkExperienceWeightageTO for1to2yearsWeightageTO = new WorkExperienceWeightageTO();
			for1to2yearsWeightageTO.setExperienceName("1 - 2 years");
			for1to2yearsWeightageTO.setWeightageId(for1to2yearsBO.getId());
			if (for1to2yearsBO.getWeightage_1() != null) {
				for1to2yearsWeightageTO.setWeightagePercentage(for1to2yearsBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for1to2yearsWeightageTO);

		}else if (for2to3yearsBO != null) {

			WorkExperienceWeightageTO for2to3yearsWeightageTO= new WorkExperienceWeightageTO();
			for2to3yearsWeightageTO.setExperienceName("2 - 3 years");
			for2to3yearsWeightageTO.setWeightageId(for2to3yearsBO.getId());
			if (for2to3yearsBO.getWeightage_1() != null) {
				for2to3yearsWeightageTO.setWeightagePercentage(for2to3yearsBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for2to3yearsWeightageTO);

		}else if (for3to4yearsBO != null) {

			WorkExperienceWeightageTO for3to4yearWeightageTO = new WorkExperienceWeightageTO();
			for3to4yearWeightageTO.setExperienceName("3 - 4 years");
			for3to4yearWeightageTO.setWeightageId(for3to4yearsBO.getId());
			if (for3to4yearsBO.getWeightage_1() != null) {
				for3to4yearWeightageTO.setWeightagePercentage(for3to4yearsBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for3to4yearWeightageTO);

		}else if (for4to5yearsBO != null) {

			WorkExperienceWeightageTO for4to5yearWeightageTO = new WorkExperienceWeightageTO();
			for4to5yearWeightageTO.setExperienceName("4 - 5 years");
			for4to5yearWeightageTO.setWeightageId(for4to5yearsBO.getId());
			if (for4to5yearsBO.getWeightage_1() != null) {
				for4to5yearWeightageTO.setWeightagePercentage(for4to5yearsBO
						.getWeightage_1().toString());
			}
			workExperienceWeightageTOlist.add(for4to5yearWeightageTO);

		} 
		log.info("exit of convertBoToWorkExperienceWeightageTOList of WeightageDefenitionHelper class.");
		return workExperienceWeightageTOlist;

	}
	
	/**
	 * updates the educational weightage adjusted marks.
	 * @param educationalPercentageList
	 * @return
	 */
	public List<EducationalWeightageAdjustedTO> updateWeightageAdjustedMarks(
			List educationalPercentageList) {
		log.info("entering into updateWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		List<EducationalWeightageAdjustedTO> educationPercentageList = new ArrayList<EducationalWeightageAdjustedTO>();

		if (educationalPercentageList != null) {

			Iterator educationalPercentageIterator = educationalPercentageList
					.iterator();

			while (educationalPercentageIterator.hasNext()) {

				Object[] educationPercentageObject = (Object[]) educationalPercentageIterator
						.next();
				EdnQualification educationQualification = (EdnQualification) educationPercentageObject[0];
//				NewEdnQualification educationQualification = (NewEdnQualification) educationPercentageObject[0];
				EducationalWeightageAdjustedTO educationalPercentageTO = new EducationalWeightageAdjustedTO();
				educationalPercentageTO
						.setEducationQualificationId(educationQualification
								.getId());
				double weightageAdjustedMarks = 0;
				// Calculating percentage for individual education
				double educationalPercentage = educationQualification
						.getPercentage() == null ? 0.0 : educationQualification
						.getPercentage().doubleValue();
				double educationalWeightage = educationQualification
						.getDocChecklist().getWeightagePercentage() == null ? 0.0
						: educationQualification.getDocChecklist()
								.getWeightagePercentage().doubleValue();

				// Calculating weighatge adjusted marks
				weightageAdjustedMarks = (educationalPercentage * educationalWeightage) / 100;

				educationalPercentageTO
						.setWeightageAdjustedMarks(new BigDecimal(
								weightageAdjustedMarks));
				educationPercentageList.add(educationalPercentageTO);
			}

		}
		log.info("exit of updateWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		return educationPercentageList;

	}
	
	
	/**
	 * updates prerequisite weighatage adjusted marks.
	 * @param prerequisitePercentage
	 * @return
	 */
	public List<CoursePrerequisiteWeightageTO> updatePrerequisiteWeightageAdjustedMarks(
			List prerequisitePercentage) {
		log.info("entering into updatePrerequisiteWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		List<CoursePrerequisiteWeightageTO> prerequisitePercentageList = new ArrayList<CoursePrerequisiteWeightageTO>();

		if (prerequisitePercentage != null) {
			Iterator prerequisitePercentageIterator = prerequisitePercentage
					.iterator();
			while (prerequisitePercentageIterator.hasNext()) {

				Object[] educationPercentage = (Object[]) prerequisitePercentageIterator
						.next();
				CandidatePrerequisiteMarks candidatePrerequisiteMarks = (CandidatePrerequisiteMarks) educationPercentage[0];
				CoursePrerequisite coursePrerequisite = (CoursePrerequisite) educationPercentage[2];
				CoursePrerequisiteWeightageTO educationalPercentageTO = new CoursePrerequisiteWeightageTO();

				educationalPercentageTO
						.setCandidatesPrerequisiteId(candidatePrerequisiteMarks
								.getId());

				double weightageAdjustedMarks = 0;

				double prerequisiteMarksObtained = candidatePrerequisiteMarks
						.getPrerequisiteMarksObtained() == null ? 0.0
						: candidatePrerequisiteMarks
								.getPrerequisiteMarksObtained().floatValue();
				
				double prerequisiteTotalMarks = candidatePrerequisiteMarks
						.getPrerequisiteTotalMarks() == null ? 0.0
						: candidatePrerequisiteMarks
								.getPrerequisiteTotalMarks().floatValue();
				double prerequisiteMarks = 0;
				if(prerequisiteTotalMarks != 0) {
					prerequisiteMarks =	(100 * prerequisiteMarksObtained)/prerequisiteTotalMarks;
				}
				
				double weightagePercentage = coursePrerequisite
						.getWeightagePercentage() == null ? 0.0
						: coursePrerequisite.getWeightagePercentage()
								.doubleValue();
				weightageAdjustedMarks = (prerequisiteMarks * weightagePercentage) / 100;

				educationalPercentageTO
						.setWeightageAdjustedMarks(new BigDecimal(
								weightageAdjustedMarks));
				prerequisitePercentageList.add(educationalPercentageTO);
			}

		}
		log.info("exit of updatePrerequisiteWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		return prerequisitePercentageList;

	}

	/**
	 * updates interview weightage adjusted marks.
	 * @param interviewPercentageList
	 * @param weightage 
	 * @return
	 */
	public List<InterviewWeightageAdjustedTO> updateInterviewWeightageAdjustedMarks(
			List interviewPercentageList, Weightage weightage) {
		log.info("entering into updateInterviewWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		List<InterviewWeightageAdjustedTO> interviewWeightageAdjustedTO = new ArrayList<InterviewWeightageAdjustedTO>();
		
		
		if (interviewPercentageList != null) {
			Iterator interviewPercentageIterator = interviewPercentageList.iterator();
			while (interviewPercentageIterator.hasNext()) {
				Object[] interviewPercentageObject = (Object[]) interviewPercentageIterator
						.next();
				InterviewResult interviewResult = (InterviewResult) interviewPercentageObject[0];		
				InterviewWeightageAdjustedTO interviewPercentageTO = new InterviewWeightageAdjustedTO();
				interviewPercentageTO.setInterviewResultId(interviewResult);
				
				double weightagePercentage = 0.0;
				if(interviewResult.getInterviewSubRounds()==null){
					weightagePercentage = interviewResult
					.getInterviewProgramCourse()
					.getWeightagePercentage() == null ? 0.0
					: interviewResult.getInterviewProgramCourse()
							.getWeightagePercentage().doubleValue();
				}
				else{
					weightagePercentage = interviewResult.getInterviewSubRounds()
						.getWeightagePercentage() == null ? 0.0
					: interviewResult.getInterviewSubRounds()
							.getWeightagePercentage().doubleValue();
					
				}
					
					
				
				// get all grades and take average
				double gradeMarks = 0.0;
				if (interviewResult.getInterviewResultDetails() != null
						&& !interviewResult.getInterviewResultDetails()
								.isEmpty()) {
					int size = interviewResult.getInterviewResultDetails()
							.size();
					double totalgrade = 0.0;
					Iterator<InterviewResultDetail> detItr = interviewResult
							.getInterviewResultDetails().iterator();
					while (detItr.hasNext()) {
						InterviewResultDetail interviewResultDetail = (InterviewResultDetail) detItr
								.next();
						if (interviewResultDetail.getGrade() != null){
							totalgrade = totalgrade
									+ interviewResultDetail.getGrade()
											.getMarks();
						}
						else if( interviewResultDetail.getPercentage()!= null) {
							totalgrade = totalgrade	+ interviewResultDetail.getPercentage().doubleValue();
						}

					}
					gradeMarks = totalgrade / size;

				}		
				double weightageAdjustedMarks = 0;
				weightageAdjustedMarks = (weightagePercentage * gradeMarks) / 100;
				interviewPercentageTO.setWeightageAdjustedMarks(new BigDecimal(weightageAdjustedMarks));
				interviewWeightageAdjustedTO.add(interviewPercentageTO);					
				
			}
		}
		
		/*
		
		int totalsize=0;
		if (interviewPercentageList != null) {
			totalsize=interviewPercentageList.size();
			Iterator interviewPercentageIterator = interviewPercentageList
					.iterator();
			//this set will contain all results having subrounds
			Set<Integer> subroundResults=new HashSet<Integer>();
			//this map will contain all marks of admappln
			Map<Integer, Map<InterviewSubRounds, Double>> applnMarks=new HashMap<Integer, Map<InterviewSubRounds, Double>>();
			
			while (interviewPercentageIterator.hasNext()) {
				Object[] interviewPercentageObject = (Object[]) interviewPercentageIterator
						.next();
				InterviewResult interviewResult = (InterviewResult) interviewPercentageObject[0];
				//this map will contain all marks of subrounds
				Map<InterviewSubRounds, Double> subroundMarks=new HashMap<InterviewSubRounds, Double>();
				//no subrounds
				if (interviewResult.getInterviewSubRounds()==null) {
					InterviewWeightageAdjustedTO interviewPercentageTO = new InterviewWeightageAdjustedTO();
					interviewPercentageTO.setInterviewResultId(interviewResult);
					double weightagePercentage = interviewResult
							.getInterviewProgramCourse()
							.getWeightagePercentage() == null ? 0.0
							: interviewResult.getInterviewProgramCourse()
									.getWeightagePercentage().doubleValue();
					// get all grades and take average
					double gradeMarks = 0.0;
					if (interviewResult.getInterviewResultDetails() != null
							&& !interviewResult.getInterviewResultDetails()
									.isEmpty()) {
						int size = interviewResult.getInterviewResultDetails()
								.size();
						double totalgrade = 0.0;
						Iterator<InterviewResultDetail> detItr = interviewResult
								.getInterviewResultDetails().iterator();
						while (detItr.hasNext()) {
							InterviewResultDetail interviewResultDetail = (InterviewResultDetail) detItr
									.next();
							if (interviewResultDetail.getGrade() != null){
								totalgrade = totalgrade
										+ interviewResultDetail.getGrade()
												.getMarks();
							}
							else if( interviewResultDetail.getPercentage()!= null) {
								totalgrade = totalgrade	+ interviewResultDetail.getPercentage().doubleValue();
							}

						}
						gradeMarks = totalgrade / size;

					}
					double weightageAdjustedMarks = 0;
					weightageAdjustedMarks = (weightagePercentage * gradeMarks) / 100;
					interviewPercentageTO
							.setWeightageAdjustedMarks(new BigDecimal(
									weightageAdjustedMarks));
					interviewWeightageAdjustedTO.add(interviewPercentageTO);
				}
				//subrounds exist
				else{ 
					subroundResults.add(interviewResult.getId());
					InterviewWeightageAdjustedTO interviewPercentageTO = new InterviewWeightageAdjustedTO();
					interviewPercentageTO.setSubRound(true);
					interviewPercentageTO.setInterviewResultId(interviewResult);
					double weightagePercentage = interviewResult
							.getInterviewProgramCourse()
							.getWeightagePercentage() == null ? 0.0
							: interviewResult.getInterviewProgramCourse()
									.getWeightagePercentage().doubleValue();
					
					
					// calculate sub round marks
					Set<Integer> subroundIds= new HashSet<Integer>();
					

						
						if(!subroundIds.contains(interviewResult.getInterviewSubRounds().getId())){
							subroundIds.add(interviewResult.getInterviewSubRounds().getId());
							
							//get all multiple panel marks
							double gradeMarks = 0.0;
							if (interviewResult.getInterviewResultDetails() != null
									&& !interviewResult.getInterviewResultDetails()
											.isEmpty()) {
								int size = interviewResult.getInterviewResultDetails()
										.size();
								double totalgrade = 0.0;
								Iterator<InterviewResultDetail> detItr = interviewResult
										.getInterviewResultDetails().iterator();
								while (detItr.hasNext()) {
									InterviewResultDetail interviewResultDetail = (InterviewResultDetail) detItr
											.next();
									if (interviewResultDetail.getGrade() != null){
										totalgrade = totalgrade
												+ interviewResultDetail.getGrade()
														.getMarks();
									}
									else if( interviewResultDetail.getPercentage()!= null) 
									{
										totalgrade = totalgrade	+ interviewResultDetail.getPercentage().doubleValue();
									}

								}
								gradeMarks = totalgrade / size;

							}
							
							subroundMarks.put(interviewResult.getInterviewSubRounds(), gradeMarks);
							
						}else{
							
							double oldgradeMarks =subroundMarks.get(interviewResult.getInterviewSubRounds().getId());
							//get all multiple panel marks
							double gradeMarks = 0.0;
							if (interviewResult.getInterviewResultDetails() != null
									&& !interviewResult.getInterviewResultDetails()
											.isEmpty()) {
								int size = interviewResult.getInterviewResultDetails()
										.size();
								double totalgrade = 0.0;
								Iterator<InterviewResultDetail> detItr = interviewResult
										.getInterviewResultDetails().iterator();
								while (detItr.hasNext()) {
									InterviewResultDetail interviewResultDetail = (InterviewResultDetail) detItr
											.next();
									if (interviewResultDetail.getGrade() != null){
										totalgrade = totalgrade
												+ interviewResultDetail.getGrade()
														.getMarks();
									}
									else if( interviewResultDetail.getPercentage()!= null){
										totalgrade = totalgrade	+ interviewResultDetail.getPercentage().doubleValue();
									}

								}
								gradeMarks = totalgrade / size;

							}
							oldgradeMarks=oldgradeMarks+gradeMarks;
							subroundMarks.put(interviewResult.getInterviewSubRounds(), oldgradeMarks);
						}
						//put interview result id and its sub round marks
						applnMarks.put(interviewResult.getId(), subroundMarks);
						

					
				}
			}
			
			// iterate again results to separate subround containings,calculate mainround marks
			
			Iterator subroundIterator = interviewPercentageList.iterator();
			HashMap<InterviewProgramCourse, Double> mainroundMarks=new HashMap<InterviewProgramCourse, Double>();
			HashMap<Integer, HashMap<InterviewProgramCourse, Double>> applnroundMarks=new HashMap<Integer, HashMap<InterviewProgramCourse, Double>>();
			while (subroundIterator.hasNext()) {
				Object[] interviewPercentageObject = (Object[]) subroundIterator.next();
				InterviewResult interviewResult = (InterviewResult) interviewPercentageObject[0];
				Map<InterviewSubRounds, Double> subroundMarks=applnMarks.get(interviewResult.getId());
				if(subroundResults.contains(interviewResult.getId())){
					if(subroundMarks!=null && subroundMarks.containsKey(interviewResult.getInterviewSubRounds())){
						if(applnroundMarks.containsKey(interviewResult.getAdmAppln().getId()))
							mainroundMarks=applnroundMarks.get(interviewResult.getAdmAppln().getId());
						else
							mainroundMarks=new HashMap<InterviewProgramCourse, Double>();
						//check for main round already there or not
						if(mainroundMarks.containsKey(interviewResult.getInterviewProgramCourse())){
							double oldmark=mainroundMarks.get(interviewResult.getInterviewProgramCourse());
							double newMark=oldmark+subroundMarks.get(interviewResult.getInterviewSubRounds());
							mainroundMarks.put(interviewResult.getInterviewProgramCourse(), newMark);
							applnroundMarks.put(interviewResult.getAdmAppln().getId(), mainroundMarks);
						}else{
							mainroundMarks.put(interviewResult.getInterviewProgramCourse(), subroundMarks.get(interviewResult.getInterviewSubRounds()));
							applnroundMarks.put(interviewResult.getAdmAppln().getId(), mainroundMarks);
						}

					}
				}
			}
			
			double totalweigh=0.0;
			double adjustment=0.0;
			if(weightage!=null && weightage.getInterviewWeightage()!=null)
				adjustment=weightage.getInterviewWeightage().doubleValue();
			
			Iterator mainIterator = interviewPercentageList.iterator();

			while (mainIterator.hasNext()) {
				Object[] interviewPercentageObject = (Object[]) mainIterator.next();
				InterviewResult interviewResult = (InterviewResult) interviewPercentageObject[0];
				if(subroundResults.contains(interviewResult.getId())){
					 double averagemark=0.0;
					 double weightagePercentage = interviewResult
						.getInterviewProgramCourse()
						.getWeightagePercentage() == null ? 0.0
						: interviewResult.getInterviewProgramCourse()
								.getWeightagePercentage().doubleValue();
					int mainRoundSize=0;
					 Set<InterviewResult> totalresults=interviewResult.getInterviewProgramCourse().getInterviewResults();
					 if(totalresults!=null){
						 Iterator<InterviewResult> totItr=totalresults.iterator();
						 while (totItr.hasNext()) {
							InterviewResult result2 = (InterviewResult) totItr.next();
							if(result2.getAdmAppln().getId()==interviewResult.getAdmAppln().getId())
								mainRoundSize++;
						}
					 }
					int subroundSize=0;
					if(interviewResult.getInterviewProgramCourse().getInterviewSubRoundses()!=null){
						Iterator<InterviewSubRounds> roundItr=interviewResult.getInterviewProgramCourse().getInterviewSubRoundses().iterator();
						while (roundItr.hasNext()) {
							InterviewSubRounds subRound = (InterviewSubRounds) roundItr.next();
							if(subRound.getIsActive()!=null && subRound.getIsActive())
								subroundSize++;
						}
					}
					mainroundMarks=applnroundMarks.get(interviewResult.getAdmAppln().getId());
					if(mainroundMarks.containsKey(interviewResult.getInterviewProgramCourse())){
						double totalmark=mainroundMarks.get(interviewResult.getInterviewProgramCourse());
						// as we are updating each row,hence  divide it to mainround size 2 times 
						if(subroundSize==0){
							averagemark=(totalmark/(mainRoundSize))/mainRoundSize;
						}else{
//							averagemark=(totalmark/(subroundSize*mainRoundSize))/mainRoundSize;
							averagemark=(totalmark/(subroundSize))/subroundSize;
						}
						//averagemark=(totalmark/(subroundSize*mainRoundSize))/mainRoundSize;
						double weightageAdjustedMarks = 0;
						weightageAdjustedMarks = (weightagePercentage * averagemark) / 100;
						//applying final weightage			
						
						InterviewWeightageAdjustedTO interviewPercentageTO = new InterviewWeightageAdjustedTO();
						interviewPercentageTO.setInterviewResultId(interviewResult);
						interviewPercentageTO
								.setWeightageAdjustedMarks(new BigDecimal(
										weightageAdjustedMarks));
						interviewWeightageAdjustedTO.add(interviewPercentageTO);
					}
				}
			}

		}
		*/
		log.info("exit of updateInterviewWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		return interviewWeightageAdjustedTO;
	}

	/**
	 * updates total weightage adjusted marks.
	 * @param totalAdjustedMarks
	 * @param educationalWeightage
	 * @param interviewWeightage
	 * @param prerequisiteMap
	 * @return
	 */
	public List<TotalWeightageAdjustedTO> getUpdateTotalWeightageAdjustedMarks(
			List totalAdjustedMarks, HashMap<Integer, BigDecimal> educationalWeightage, HashMap<Integer, BigDecimal> interviewWeightage, HashMap<Integer, BigDecimal> prerequisiteMap) {
		log.info("entering into getUpdateTotalWeightageAdjustedMarks of WeightageDefenitionHelper class.");

		List<TotalWeightageAdjustedTO> totalWeightageAdjustedTOList = new ArrayList<TotalWeightageAdjustedTO>();

		if (totalAdjustedMarks != null) {
			Iterator totalAdjustedIterator = totalAdjustedMarks.iterator();
			while (totalAdjustedIterator.hasNext()) {
				Object[] totalweightageMarks = (Object[]) totalAdjustedIterator
						.next();
				TotalWeightageAdjustedTO totalWeightageAdjustedTO = new TotalWeightageAdjustedTO();
				
				float totalWeightageAdjustedMarks = 0;
				float interViewPercentageWeightage = 0;
				float educationalPercentageWeightage = 0;
				float prerequisitePercentageWeightage = 0;
				float generalWeightageAdjustedMarks = 0;
				
				totalWeightageAdjustedTO
						.setAdmApplnId((Integer) totalweightageMarks[0]);
			
				if (totalweightageMarks[1] != null) {
					generalWeightageAdjustedMarks = ((BigDecimal) totalweightageMarks[1])
							.floatValue();
				}

				if ( totalweightageMarks[2] != null && interviewWeightage.get(totalweightageMarks[0]) != null) {
					interViewPercentageWeightage = (interviewWeightage.get(totalweightageMarks[0])).floatValue()
							* ((BigDecimal) totalweightageMarks[2])
									.floatValue() / 100;
				}

				if (totalweightageMarks[3] != null && educationalWeightage.get(totalweightageMarks[0]) != null) {
					educationalPercentageWeightage = ( educationalWeightage.get(totalweightageMarks[0]))
							.floatValue()
							* ((BigDecimal) totalweightageMarks[3])
									.floatValue() / 100;
				}
				
				if (totalweightageMarks[4] != null && prerequisiteMap.get(totalweightageMarks[0]) != null) {
					prerequisitePercentageWeightage = ( prerequisiteMap.get(totalweightageMarks[0]))
							.floatValue()
							* ((BigDecimal) totalweightageMarks[4])
									.floatValue() / 100;
				}

				totalWeightageAdjustedMarks = interViewPercentageWeightage
						+ educationalPercentageWeightage
						+ generalWeightageAdjustedMarks + prerequisitePercentageWeightage;
				totalWeightageAdjustedTO
						.setWeightageAdjustedMarks(new BigDecimal(
								totalWeightageAdjustedMarks));
				totalWeightageAdjustedTOList.add(totalWeightageAdjustedTO);
			}

		}
		log.info("exit of getUpdateTotalWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		return totalWeightageAdjustedTOList;

	}

	/**
	 * updates general weightage adjusted marks.
	 * @param updatedGeneralWeightage
	 * @return
	 */
	public List<GeneralWeightageAdjustedTO> getUpdateGeneralWeightageAdjustedMarks(
			List updatedGeneralWeightage) {
		log.info("entering into getUpdateGeneralWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		List<GeneralWeightageAdjustedTO> generalWeightageAdjustedTOList = new ArrayList<GeneralWeightageAdjustedTO>();
		if (updatedGeneralWeightage != null) {
			Iterator updatedGeneralWeightageIterator = updatedGeneralWeightage
					.iterator();
			while (updatedGeneralWeightageIterator.hasNext()) {
				Object[] generalWeightage = (Object[]) updatedGeneralWeightageIterator
						.next();
				GeneralWeightageAdjustedTO generalWeightageAdjustedTO = new GeneralWeightageAdjustedTO();
				generalWeightageAdjustedTO
						.setAdmApplnId((Integer) generalWeightage[0]);
				generalWeightageAdjustedTO
						.setWeightageAdjustedMarks((BigDecimal) generalWeightage[1]);
				generalWeightageAdjustedTOList.add(generalWeightageAdjustedTO);
			}
		}
		
		log.info("exit of getUpdateGeneralWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		return generalWeightageAdjustedTOList;

	}
	
	/**
	 * Converts from list to Map.
	 * @param educationalList
	 * @return
	 */
	public HashMap<Integer, BigDecimal> convertListToMap(List educationalList) {
		log.info("entering into convertListToMap of WeightageDefenitionHelper class.");
		HashMap<Integer, BigDecimal> educationalMap = new HashMap<Integer, BigDecimal>();
		if (educationalList != null) {
			Iterator educationalIterator = educationalList.iterator();
			while (educationalIterator.hasNext()) {
				Object[] object = (Object[]) educationalIterator.next();
				educationalMap.put((Integer) object[0], ((BigDecimal) object[1]) == null ? new BigDecimal("0.0") :(BigDecimal) object[1]);
			}
		}
		log.info("exit of convertListToMap of WeightageDefenitionHelper class.");
		return educationalMap;

	}
	
	/**
	 * Converts prerequisite weightage list to map.
	 * @param prerequisiteList
	 * @return
	 */
	public HashMap<Integer, BigDecimal> convertPrerequisiteListToMap(
			List prerequisiteList) {
		log.info("entering into convertPrerequisiteListToMap of WeightageDefenitionHelper class.");
		HashMap<Integer, BigDecimal> educationalMap = new HashMap<Integer, BigDecimal>();
		if (prerequisiteList != null) {
			Iterator prerequisiteIterator = prerequisiteList.iterator();
			while (prerequisiteIterator.hasNext()) {
				Object[] coursePrerequisiteWeightage = (Object[]) prerequisiteIterator
						.next();
				
				educationalMap
						.put(
								(Integer) coursePrerequisiteWeightage[0],
								coursePrerequisiteWeightage[1] == null ? new BigDecimal(
										"0.0")
										: (BigDecimal)coursePrerequisiteWeightage[1]);
			}

		}
		log.info("exit of convertPrerequisiteListToMap of WeightageDefenitionHelper class.");
		return educationalMap;

	}

	/**
	 * Converts from BO to TO
	 * @param coursePrerequisiteBo
	 * @return
	 */
	public List<CoursePrerequisiteWeightageTO> convertBoToPrerequisiteWeightageDefenitionTO(
			List<CoursePrerequisite> coursePrerequisiteBo) {
		log.info("entering into convertBoToPrerequisiteWeightageDefenitionTO of WeightageDefenitionHelper class.");
		List<CoursePrerequisiteWeightageTO> prerequisiteTypeList = new ArrayList<CoursePrerequisiteWeightageTO>();
		
		if(coursePrerequisiteBo != null) {
			Iterator<CoursePrerequisite> coursePrerequisiteIterator = coursePrerequisiteBo.iterator();
			while (coursePrerequisiteIterator.hasNext()) {
				CoursePrerequisite coursePrerequisite = (CoursePrerequisite) coursePrerequisiteIterator
						.next();
				CoursePrerequisiteWeightageTO coursePrerequisiteWeightageTO = new CoursePrerequisiteWeightageTO();
				coursePrerequisiteWeightageTO.setCoursePrerequisiteid(coursePrerequisite.getId());
				coursePrerequisiteWeightageTO.setPrerequisiteName(coursePrerequisite.getPrerequisite().getName());
				if(coursePrerequisite.getWeightage() != null) {
					coursePrerequisiteWeightageTO.setWeightageid(coursePrerequisite.getWeightage().getId());
				}
				if(coursePrerequisite.getWeightagePercentage() != null) {
					coursePrerequisiteWeightageTO.setWeightagePercentage(coursePrerequisite.getWeightagePercentage().toString());
				}
				prerequisiteTypeList.add(coursePrerequisiteWeightageTO);
			}
		}
		log.info("exit of convertBoToPrerequisiteWeightageDefenitionTO of WeightageDefenitionHelper class.");
		return prerequisiteTypeList;
	 }
	
	// combining for institute, prev qualification and work exp
	
	public List<GeneralWeightageAdjustedTO> getUpdateGeneralInstitutePreviousQualfnAndWorkExpWeightageAdjustedMarks(
			List<GeneralWeightageAdjustedTO> updatedGeneralWeightage,List institutionalWeightages,
			List prevQualificationWeightages,List workExpWeightages) {
		
		log.info("entering into getUpdateGeneralInstitutePreviousQualfnAndWorkExpWeightageAdjustedMarks of WeightageDefenitionHelper class.");

		Map<Integer, BigDecimal> institutionalWeightageMap=new HashMap<Integer, BigDecimal>();
		Map<Integer, BigDecimal> prevQualificationWeightageMap = new HashMap<Integer, BigDecimal>();
		Map<Integer, BigDecimal> workExpWeightageMap = new HashMap<Integer, BigDecimal>();

		List<GeneralWeightageAdjustedTO> newWeightage = new ArrayList<GeneralWeightageAdjustedTO>();

		Iterator institutionalWeightageIterator =institutionalWeightages.iterator();
		
		Iterator prevQualificationWeightageIterator = prevQualificationWeightages.iterator();
		
		Iterator workExpWeightageIterator = workExpWeightages.iterator();
		
		while (institutionalWeightageIterator.hasNext()) {
			Object[] object = (Object[]) institutionalWeightageIterator.next();
			institutionalWeightageMap.put((Integer) object[0],
					(BigDecimal) object[1]);
		}
		
		while (prevQualificationWeightageIterator.hasNext()) {
			Object[] object = (Object[]) prevQualificationWeightageIterator.next();
			prevQualificationWeightageMap.put((Integer) object[0],
					(BigDecimal) object[1]);
		}
		while (workExpWeightageIterator.hasNext()) {
			Object[] object = (Object[]) workExpWeightageIterator.next();
			workExpWeightageMap.put((Integer) object[0],
					(BigDecimal) object[1]);
		}
		checkTOList(updatedGeneralWeightage,institutionalWeightageMap,prevQualificationWeightageMap,workExpWeightageMap);
		Iterator<GeneralWeightageAdjustedTO> weightageIterator = updatedGeneralWeightage
				.iterator();

		while (weightageIterator.hasNext()) {
			GeneralWeightageAdjustedTO generalWeightageAdjustedTO = (GeneralWeightageAdjustedTO) weightageIterator
					.next();
			Float weightageAdjustedMarks=Float.valueOf(0);
			if (generalWeightageAdjustedTO.getWeightageAdjustedMarks() != null) {
				weightageAdjustedMarks= generalWeightageAdjustedTO
						.getWeightageAdjustedMarks().floatValue();
			}
			if (institutionalWeightageMap.get(generalWeightageAdjustedTO
					.getAdmApplnId()) != null) {
				weightageAdjustedMarks = weightageAdjustedMarks
						+ institutionalWeightageMap.get(
								generalWeightageAdjustedTO.getAdmApplnId())
								.floatValue();
			}
			if (prevQualificationWeightageMap.get(generalWeightageAdjustedTO
					.getAdmApplnId()) != null) {
				weightageAdjustedMarks = weightageAdjustedMarks
						+ prevQualificationWeightageMap.get(
								generalWeightageAdjustedTO.getAdmApplnId())
								.floatValue();
			   }
				if (workExpWeightageMap.get(generalWeightageAdjustedTO
						.getAdmApplnId()) != null) {
					weightageAdjustedMarks = weightageAdjustedMarks
							+ workExpWeightageMap.get(
									generalWeightageAdjustedTO.getAdmApplnId())
									.floatValue();	
			   }
			generalWeightageAdjustedTO
					.setWeightageAdjustedMarks(new BigDecimal(
							weightageAdjustedMarks));
			newWeightage.add(generalWeightageAdjustedTO);
			
	    }
		log.info("exit of getUpdateGeneralInstitutePreviousQualfnAndWorkExpWeightageAdjustedMarks of WeightageDefenitionHelper class.");
		return newWeightage;

	}
	
   public List getTotalGeneralWorkExperienceWeightage(int courseId,int year)throws ApplicationException{
		
		IWeightageEntryTransaction weightageDefinitionEntry = new WeightageEntryTransactionImpl();
		List updatedWorkExperienceWeightageList=new  ArrayList();
		Map<String, BigDecimal> weightageDefinitionMap = new HashMap<String, BigDecimal>();
		List<WeightageDefinition> weightageDefinitions=weightageDefinitionEntry.getweightageDefinitions(courseId,year);
		for(WeightageDefinition weightageDef :weightageDefinitions)
		{
			weightageDefinitionMap.put(weightageDef.getWorkExp(),weightageDef.getWeightage_1());
		}
		String yearsOfExp="";
		List<Integer> admApplnIds=weightageDefinitionEntry.getAdmApplnHavingWorkExp(courseId,year);
		for(Integer admApplnId: admApplnIds)
		{
			int totalDays=0;
			List<ApplicantWorkExperience> workExperiences=weightageDefinitionEntry.getWorkExperienceList(admApplnId);
			for(ApplicantWorkExperience workExperience:workExperiences)
			{
				Calendar c1 = new GregorianCalendar();
		        Calendar c2 = new GregorianCalendar();				
				
				if(workExperience.getFromDate()!=null)
				{
					Date startDate=workExperience.getFromDate();
					c1.setTime(startDate);
				}
				if(workExperience.getToDate()!=null)
				{
				  Date	endDate=workExperience.getToDate();
					 c2.setTime(endDate);
				}
		       
		        if(c1.after(c2))
		        {
		            throw new RuntimeException("End Date earlier than Start Date");
		        }
		        totalDays += c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		        int y2 = c2.get(Calendar.YEAR);

		        while (c1.get(Calendar.YEAR) != y2)
		        {
		        	totalDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
		            c1.add(Calendar.YEAR, 1);
		        }
			}
			double noOfYear=(double)totalDays/365;
            if(noOfYear<=1)
            {
         	  yearsOfExp="0-1";    // it is explicitly setting "0-1" since workexp is hardcoded and                   
            }                       //saved as "0-1" in weightage definition 
            else if(noOfYear<=2)
            {
         	   yearsOfExp="1-2";  
            }
            else if(noOfYear<=3)
            {
         	   yearsOfExp="2-3";  
            }
            else if(noOfYear<=4)
            {
         	   yearsOfExp="3-4";  
            }
            else if(noOfYear<=5)
            {
         	   yearsOfExp="4-5";  
            }else if(noOfYear>5){
               yearsOfExp="4-5";
            }
            BigDecimal weightage=weightageDefinitionMap.get(yearsOfExp);
            Object[] workExpWeightages = new Object[2];

            workExpWeightages[0]=admApplnId;
            workExpWeightages[1]=weightage;
			updatedWorkExperienceWeightageList.add(workExpWeightages );
		}
		
		return updatedWorkExperienceWeightageList;
	}
	
	   private void checkTOList(List<GeneralWeightageAdjustedTO>updatedGeneralWeightage, Map<Integer, BigDecimal> institutionalWeightageMap,Map<Integer, BigDecimal> prevQualificationWeightageMap, Map<Integer, BigDecimal>workExpWeightageMap)
	   {
           Set<Integer> admIdFromGeneral = new HashSet<Integer>();
           Set<Integer> admAppIds = new HashSet<Integer>();
           admAppIds.addAll(institutionalWeightageMap.keySet());
           admAppIds.addAll(prevQualificationWeightageMap.keySet());
           admAppIds.addAll(workExpWeightageMap.keySet());
           for(GeneralWeightageAdjustedTO
           generalWeightageAdjustedTO:updatedGeneralWeightage)
           {
               admIdFromGeneral.add(generalWeightageAdjustedTO.getAdmApplnId());
           }
	            /*
				   Collection<Integer> missingIds=new ArrayList<Integer>();
				   for(Integer admpplnId:admAppIds)
				   {
				       if(!admIdFromGeneral.contains(admpplnId))
				       {
				           missingIds.add(admpplnId);     
				       }
				   }
				      
		       */
	     Collection<Integer> missingIds =CollectionUtils.subtract(admAppIds,admIdFromGeneral);
	     for(Integer missingAdmId:missingIds)
	     {
		      GeneralWeightageAdjustedTO generalWeightageAdjustedTO = new GeneralWeightageAdjustedTO();
	          generalWeightageAdjustedTO.setAdmApplnId(missingAdmId);
	          generalWeightageAdjustedTO.setWeightageAdjustedMarks(new BigDecimal(0));
	          updatedGeneralWeightage.add(generalWeightageAdjustedTO);
	     }
	
      }
	   

		/*public List getTotalGeneralWorkExperienceWeightage(List updatedgeneralWeightageList,int courseId,int year)throws ApplicationException{
			
			IWeightageEntryTransaction weightageDefinitionEntry = new WeightageEntryTransactionImpl();
			List updatedWorkExperienceWeightageList=new  ArrayList();
			Map<String, BigDecimal> weightageDefinitionMap = new HashMap<String, BigDecimal>();
			List<WeightageDefinition> weightageDefinitions=weightageDefinitionEntry.getweightageDefinitions(courseId,year);
			for(WeightageDefinition weightageDef :weightageDefinitions)
			{
				weightageDefinitionMap.put(weightageDef.getWorkExp(),weightageDef.getWeightage_1());
			}
			String yearsOfExp="";
			
			for(Object generalWeightage: updatedgeneralWeightageList)
			{
				int totalDays=0;
				Object[] object = (Object[])generalWeightage;
				Integer admApplnId=(Integer)object[0];
				List<ApplicantWorkExperience> workExperiences=weightageDefinitionEntry.getWorkExperienceList(admApplnId);
				for(ApplicantWorkExperience workExperience:workExperiences)
				{
					Calendar c1 = new GregorianCalendar();
			        Calendar c2 = new GregorianCalendar();				
					
					if(workExperience.getFromDate()!=null)
					{
						Date startDate=workExperience.getFromDate();
						c1.setTime(startDate);
					}
					if(workExperience.getToDate()!=null)
					{
					  Date	endDate=workExperience.getToDate();
						 c2.setTime(endDate);
					}
			       
			        if(c1.after(c2))
			        {
			            throw new RuntimeException("End Date earlier than Start Date");
			        }
			        totalDays += c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
			        int y2 = c2.get(Calendar.YEAR);

			        while (c1.get(Calendar.YEAR) != y2)
			        {
			        	totalDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
			            c1.add(Calendar.YEAR, 1);
			        }
				}
				int noOfYear=totalDays/365;
	            if(noOfYear<=1)
	            {
	         	  yearsOfExp="0-1";    // it is explicitly setting "0-1" since workexp is hardcoded and                   
	            }                       //saved as "0-1" in weightage definition 
	            else if(noOfYear<=2)
	            {
	         	   yearsOfExp="1-2";  
	            }
	            else if(noOfYear<=3)
	            {
	         	   yearsOfExp="2-3";  
	            }
	            else if(noOfYear<=4)
	            {
	         	   yearsOfExp="3-4";  
	            }
	            else if(noOfYear<=5)
	            {
	         	   yearsOfExp="4-5";  
	            }
	            BigDecimal weightage=weightageDefinitionMap.get(yearsOfExp);
	            Object[] workExpWeightages = new Object[2];

	            workExpWeightages[0]=admApplnId;
	            workExpWeightages[1]=weightage;
	            
				//List finalCalculatedWorkExperienceWeightage=weightageDefinitionEntry.getWorkExperienceWeightage(courseId, year, yearsOfExp);
				updatedWorkExperienceWeightageList.add(workExpWeightages );
			}
			
			return updatedWorkExperienceWeightageList;
		}*/
   }