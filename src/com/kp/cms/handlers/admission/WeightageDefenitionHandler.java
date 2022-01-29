package com.kp.cms.handlers.admission;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.Weightage;
import com.kp.cms.bo.admin.WeightageDefinition;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.WeightageDefenitionForm;
import com.kp.cms.helpers.admission.WeightageDefenitionHelper;
import com.kp.cms.to.admission.CasteWeightageTO;
import com.kp.cms.to.admission.CountryWeightageTO;
import com.kp.cms.to.admission.CoursePrerequisiteWeightageTO;
import com.kp.cms.to.admission.EducationalWeightageAdjustedTO;
import com.kp.cms.to.admission.EducationalWeightageDefenitionTO;
import com.kp.cms.to.admission.GenderWeightageTO;
import com.kp.cms.to.admission.GeneralWeightageAdjustedTO;
import com.kp.cms.to.admission.InstituteWeightageTO;
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

public class WeightageDefenitionHandler {

	private static volatile WeightageDefenitionHandler weightageDefenitionHandler = null;
	private static final Log log = LogFactory.getLog(WeightageDefenitionHandler.class);
	private WeightageDefenitionHandler() {

	}

	public static WeightageDefenitionHandler getInstance() {
		if (weightageDefenitionHandler == null) {
			weightageDefenitionHandler = new WeightageDefenitionHandler();
		}
		return weightageDefenitionHandler;
	}

	/**
	 * Get the educational weightage defenition.
	 * 
	 * @param courseId
	 * @return
	 * @throws ApplicationException
	 */
	public List<EducationalWeightageDefenitionTO> getEducationalWeightageDefenitionList(
			int courseId, int year) throws ApplicationException {
		log.info("entering into getEducationalWeightageDefenitionList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();

		List<DocChecklist> docCheckListBo = weightageDefenitionEntry
				.getEducationalWeightageTypes(courseId, year);
		List<EducationalWeightageDefenitionTO> educationalWeightageDefenitionTO = WeightageDefenitionHelper
				.getInstance().convertBoToTO(docCheckListBo);
		log.info("exit of getEducationalWeightageDefenitionList of WeightageDefenitionHandler class.");
		return educationalWeightageDefenitionTO;
	}

	/**
	 * Get the interview weightage defenition list.
	 * 
	 * @param courseId
	 * @return
	 * @throws ApplicationException
	 */
	public List<InterviewWeightageDefenitionTO> getInterviewWeightageDefenitionList(
			int courseId, int year) throws ApplicationException {
		log.info("entering into getInterviewWeightageDefenitionList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List<InterviewProgramCourse> interviewProgramCourseBo = weightageDefenitionEntry
				.getInterviewWeightageTypes(courseId, year);
		List<InterviewWeightageDefenitionTO> interviewWeightageDefenitionTO = WeightageDefenitionHelper
				.getInstance().convertBoToInterviewWeightageDefenitionTO(
						interviewProgramCourseBo);
		log.info("exit of getInterviewWeightageDefenitionList of WeightageDefenitionHandler class.");
		return interviewWeightageDefenitionTO;
	}

	/**
	 * get prerequisite weightage definition list.
	 * 
	 * @param courseId
	 * @return
	 * @throws ApplicationException
	 */
	public List<CoursePrerequisiteWeightageTO> getPrerequisiteWeightageDefenitionList(
			int courseId, int year) throws ApplicationException {
		log.info("entering into getPrerequisiteWeightageDefenitionList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List<CoursePrerequisite> coursePrerequisiteBo = weightageDefenitionEntry
				.getPrerequisiteWeightageTypes(courseId, year);
		List<CoursePrerequisiteWeightageTO> coursePrerequisiteTO = WeightageDefenitionHelper
				.getInstance().convertBoToPrerequisiteWeightageDefenitionTO(
						coursePrerequisiteBo);
		log.info("exit of getPrerequisiteWeightageDefenitionList of WeightageDefenitionHandler class.");
		return coursePrerequisiteTO;
	}

	/**
	 * get the urban weightage list TO object.
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<RuralUrbanWeightageTO> getRuralUrbanWeightageList(int courseId,
			int year) throws ApplicationException {
		log.info("entering into getRuralUrbanWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List ruralWeightageDefinition = weightageDefenitionEntry
				.getRuralWeightageDefinition(courseId, year);
		WeightageDefinition ruralWeightageBO = null;
		WeightageDefinition urbanWeightageBO = null;

		if (ruralWeightageDefinition != null
				&& !ruralWeightageDefinition.isEmpty()) {
			Object[] ruralResults = ((Object[]) ruralWeightageDefinition.get(0));
			ruralWeightageBO = (WeightageDefinition) ruralResults[0];
		}
		List urbanWeightageDefinition = weightageDefenitionEntry
				.getUrbanWeightageDefinition(courseId, year);
		if (urbanWeightageDefinition != null
				&& !urbanWeightageDefinition.isEmpty()) {
			Object[] urbanResults = ((Object[]) urbanWeightageDefinition.get(0));
			urbanWeightageBO = (WeightageDefinition) urbanResults[0];
		}

		List<RuralUrbanWeightageTO> ruralUrbanWeightageTO = WeightageDefenitionHelper
				.getInstance().convertBoToRuralUrbanWeightageTOList(
						ruralWeightageBO, urbanWeightageBO);
		log.info("exit of getRuralUrbanWeightageList of WeightageDefenitionHandler class.");
		return ruralUrbanWeightageTO;
	}

	/**
	 * Get the list of CountryWeightageTO object.
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<CountryWeightageTO> getCountryWeightageList(int courseId,
			int year) throws ApplicationException {
		log.info("entering into getCountryWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List countryWeightageBO = weightageDefenitionEntry.getCountryWeightage(
				courseId, year);
		List<CountryWeightageTO> countryWeightageTO = WeightageDefenitionHelper
				.getInstance().convertBoTocountryWeightageTOList(
						countryWeightageBO);
		log.info("exit of getCountryWeightageList of WeightageDefenitionHandler class.");
		return countryWeightageTO;
	}

	/**
	 * get the list of GenderWeightageTO object.
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<GenderWeightageTO> getGenderWeightageList(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getGenderWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List maleWeightageDefinition = weightageDefenitionEntry
				.getMaleWeightageDefinition(courseId, year);
		WeightageDefinition maleWeightageBO = null;

		if (maleWeightageDefinition != null
				&& !maleWeightageDefinition.isEmpty()) {
			Object[] maleResults = ((Object[]) maleWeightageDefinition.get(0));

			maleWeightageBO = (WeightageDefinition) maleResults[0];
		}
		List femaleWeightageDefinition = weightageDefenitionEntry
				.getFeMaleWeightageDefinition(courseId, year);
		WeightageDefinition femaleWeightageBO = null;
		if (femaleWeightageDefinition != null
				&& !femaleWeightageDefinition.isEmpty()) {
			Object[] femaleResults = ((Object[]) femaleWeightageDefinition
					.get(0));
			femaleWeightageBO = (WeightageDefinition) femaleResults[0];
		}

		List<GenderWeightageTO> genderWeightageTO = WeightageDefenitionHelper
				.getInstance().convertBoTogenderWeightageTOList(
						maleWeightageBO, femaleWeightageBO);
		log.info("exit of getGenderWeightageList of WeightageDefenitionHandler class.");
		return genderWeightageTO;
	}

	/**
	 * get the list of InstituteWeightageTO object.
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<InstituteWeightageTO> getInstituteWeightageList(int courseId,
			int year) throws ApplicationException {
		log.info("entering into getInstituteWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List instituteWeightageBO = weightageDefenitionEntry
				.getInstituteWeightage(courseId, year);
		List<InstituteWeightageTO> instituteWeightageTO = WeightageDefenitionHelper
				.getInstance().convertBoToinstituteWeightageTOList(
						instituteWeightageBO);
		log.info("exit of getInstituteWeightageList of WeightageDefenitionHandler class.");
		return instituteWeightageTO;
	}

	/**
	 * get the list of NationalityWeightageTO object.
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<NationalityWeightageTO> getNationalityWeightageList(
			int courseId, int year) throws ApplicationException {
		log.info("entering into getNationalityWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List nationalityWeightageBO = weightageDefenitionEntry
				.getNationalityWeightage(courseId, year);
		List<NationalityWeightageTO> nationalityWeightageTO = WeightageDefenitionHelper
				.getInstance().convertBoTonationalityWeightageTOList(
						nationalityWeightageBO);
		log.info("exit of getNationalityWeightageList of WeightageDefenitionHandler class.");
		return nationalityWeightageTO;
	}

	/**
	 * get the list of ReligionWeightageTO object
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<ReligionWeightageTO> getReligionWeightageList(int courseId,
			int year) throws ApplicationException {
		log.info("entering into getReligionWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List religionWeightageBO = weightageDefenitionEntry
				.getReligionWeightage(courseId, year);
		List<ReligionWeightageTO> religionWeightageTO = WeightageDefenitionHelper
				.getInstance().convertBoToReligionWeightageTOList(
						religionWeightageBO);
		log.info("exit of getReligionWeightageList of WeightageDefenitionHandler class.");
		return religionWeightageTO;
	}

	/**
	 * get the list of ResidentCategoryWeightageTO object.
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<ResidentCategoryWeightageTO> getResidentCategoryWeightageList(
			int courseId, int year) throws ApplicationException {
		log.info("entering into getResidentCategoryWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List residentCategoryWeightageBO = weightageDefenitionEntry
				.getResidentCategoryWeightage(courseId, year);
		List<ResidentCategoryWeightageTO> residentCategoryWeightageTO = WeightageDefenitionHelper
				.getInstance().convertBoToResidentCategoryWeightageTOList(
						residentCategoryWeightageBO);
		log.info("exit of getResidentCategoryWeightageList of WeightageDefenitionHandler class.");
		return residentCategoryWeightageTO;
	}

	/**
	 * get the list of UniversityWeightageTO object.
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<UniversityWeightageTO> getUnbiversityWeightageList(
			int courseId, int year) throws ApplicationException {
		log.info("entering into getUnbiversityWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List universityWeightageBO = weightageDefenitionEntry
				.getUniversityWeightage(courseId, year);
		List<UniversityWeightageTO> universityWeightageTO = WeightageDefenitionHelper
				.getInstance().convertBoToUniversityWeightageTOList(
						universityWeightageBO);
		log.info("exit of getUnbiversityWeightageList of WeightageDefenitionHandler class.");
		return universityWeightageTO;
	}

	/**
	 * get the list of SubReligionWeightageTO object.
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<SubReligionWeightageTO> getReligionSectionWeightageList(
			int courseId, int year) throws ApplicationException {
		log.info("entering into getReligionSectionWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List religionSectionWeightageBO = weightageDefenitionEntry
				.getReligionSectionWeightage(courseId, year);
		List<SubReligionWeightageTO> religionSectionWeightageTO = WeightageDefenitionHelper
				.getInstance().convertBoToReligionSectionWeightageTOList(
						religionSectionWeightageBO);
		log.info("exit of getReligionSectionWeightageList of WeightageDefenitionHandler class.");
		return religionSectionWeightageTO;
	}

	/**
	 * get the list of CasteWeightageTO object.
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws ApplicationException
	 */
	public List<CasteWeightageTO> getCasteWeightageList(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getCasteWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		List casteWeightageBO = weightageDefenitionEntry.getCasteWeightage(
				courseId, year);
		List<CasteWeightageTO> casteWeightageTO = WeightageDefenitionHelper
				.getInstance()
				.convertBoToCasteWeightageTOList(casteWeightageBO);
		log.info("exit of getCasteWeightageList of WeightageDefenitionHandler class.");
		return casteWeightageTO;
	}
    
	

	/**
	 * Persists the weightage definition.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void submitWeightageDefenition(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into submitWeightageDefenition of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		weightageDefenitionEntry
				.submitWeightageDefenition(weightageDefenitionForm);
		log.info("exit of submitWeightageDefenition of WeightageDefenitionHandler class.");
	}

	/**
	 * Persists the educational weightage definition.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void submitEducationWeightageDefenition(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into submitEducationWeightageDefenition of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		weightageDefenitionEntry
				.submitEducationalWeightage(weightageDefenitionForm);
		log.info("exit of submitEducationWeightageDefenition of WeightageDefenitionHandler class.");
	}

	/**
	 * persists the interview weightage definition.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void submitInterviewWeightageDefenition(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into submitInterviewWeightageDefenition of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		weightageDefenitionEntry
				.submitInterviewWeightage(weightageDefenitionForm);
		log.info("exit of submitInterviewWeightageDefenition of WeightageDefenitionHandler class.");
	}

	/**
	 * Set the weightage defenition entry.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void setWeightageDefenitionEntry(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into setWeightageDefenitionEntry of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		Weightage weightage = weightageDefenitionEntry
				.getWeightageDefenition(weightageDefenitionForm);
		if (weightage != null) {
			weightageDefenitionForm.setWeightageId(weightage.getId());

			Double educationalWeightage = weightage.getEducationWeightage() == null ? 0.0
					: weightage.getEducationWeightage().doubleValue();
			Double interviewWeightage = weightage.getInterviewWeightage() == null ? 0.0
					: weightage.getInterviewWeightage().doubleValue();
			Double prerequisiteWeightage = weightage.getPrerequisiteWeightage() == null ? 0.0
					: weightage.getPrerequisiteWeightage().doubleValue();

			Double totalWeightage = educationalWeightage + interviewWeightage
					+ prerequisiteWeightage;

			weightageDefenitionForm
					.setEducationWeightage(educationalWeightage == null ? "0"
							: educationalWeightage.toString());
			weightageDefenitionForm
					.setInterviewWeightage(interviewWeightage == null ? "0"
							: interviewWeightage.toString());
			weightageDefenitionForm
					.setPrerequisiteWeightage(prerequisiteWeightage == null ? "0"
							: prerequisiteWeightage.toString());
			weightageDefenitionForm
					.setTotalWeightage(totalWeightage == null ? "0"
							: totalWeightage.toString());
		}
		log.info("exit of setWeightageDefenitionEntry of WeightageDefenitionHandler class.");
	}

	/**
	 * Get the weightage defenition.
	 * @param weightageDefenitionForm
	 * @return
	 * @throws ApplicationException
	 */
	public Weightage getweightageDefenition(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into getweightageDefenition of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		Weightage weightage = weightageDefenitionEntry
				.getWeightageDefenition(weightageDefenitionForm);
		log.info("exit of getweightageDefenition of WeightageDefenitionHandler class.");
		return weightage;
	}

	/**
	 * Get the list of institutions.
	 * @return
	 * @throws ApplicationException
	 */
	public List<College> getInstituteList() throws ApplicationException {
		log.info("entering into getInstituteList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		log.info("exit of getInstituteList of WeightageDefenitionHandler class.");
		return weightageDefenitionEntry.getInstituteList();
	}

	/**
	 * Get the list of countries.
	 * @return
	 * @throws ApplicationException
	 */
	public List<Country> getCountryList() throws ApplicationException {
		log.info("entering into getCountryList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		log.info("exit of getCountryList of WeightageDefenitionHandler class.");
		return weightageDefenitionEntry.getCountryList();
	}

	/**
	 * Persists the general weightage.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void updateGeneralWeightage(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateGeneralWeightage of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase("1")) {
			weightageDefenitionEntry
					.updateBelongsToWeightage(weightageDefenitionForm);
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"2")) {
			weightageDefenitionEntry
					.updateCasteWeightage(weightageDefenitionForm);
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"3")) {
			weightageDefenitionEntry
					.updateReligionSectionWeightage(weightageDefenitionForm);
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"4")) {
			weightageDefenitionEntry
					.updateCountryWeightage(weightageDefenitionForm);
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"5")) {
			weightageDefenitionEntry
					.updateGenderWeightage(weightageDefenitionForm);
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"6")) {
			weightageDefenitionEntry
					.updateInstituteWeightage(weightageDefenitionForm);
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"7")) {
			weightageDefenitionEntry
					.updateNationalityWeightage(weightageDefenitionForm);
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"8")) {
			weightageDefenitionEntry
					.updateReligionWeightage(weightageDefenitionForm);
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"9")) {
			weightageDefenitionEntry
					.updateResidentCategoryWeightage(weightageDefenitionForm);
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"10")) {
			weightageDefenitionEntry
					.updateUniversityWeightage(weightageDefenitionForm);
		}
		else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
		"11")) {
	    weightageDefenitionEntry
			.updatePreviousQualificationWeightage(weightageDefenitionForm);
		}
		else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
		"12")) {
	    weightageDefenitionEntry
			.updateWorkExperienceWeightage(weightageDefenitionForm);
		}
		log.info("exit of updateGeneralWeightage of WeightageDefenitionHandler class.");
	}

	/**
	 * Updates the educational weightage adjusted marks.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void updateIndividualEducationalWeightageAdjustedMarks(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateIndividualEducationalWeightageAdjustedMarks of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		String courseId = weightageDefenitionForm.getCourseId();
		String year = weightageDefenitionForm.getYear();
		List educationalPercentageList = weightageDefenitionEntry
				.getEducationalPercentageList(Integer.valueOf(courseId),
						Integer.valueOf(year));

		List<EducationalWeightageAdjustedTO> updatedPercentageList = WeightageDefenitionHelper
				.getInstance().updateWeightageAdjustedMarks(
						educationalPercentageList);
		weightageDefenitionEntry.updateEducationalWeightageAdjustedMarks(
				updatedPercentageList, weightageDefenitionForm.getUserId());
		log.info("exit of updateIndividualEducationalWeightageAdjustedMarks of WeightageDefenitionHandler class.");
	}

	/**
	 * Updates the interview weightage adjusted marks.
	 * @param weightageDefenitionForm
	 * @param weightage 
	 * @throws ApplicationException
	 */
	public void updateIndividualInterviewWeightageAdjustedMarks(
			WeightageDefenitionForm weightageDefenitionForm, Weightage weightage)
			throws ApplicationException {
		log.info("entering into updateIndividualInterviewWeightageAdjustedMarks of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		String courseId = weightageDefenitionForm.getCourseId();
		String year = weightageDefenitionForm.getYear();
		List interviewPercentageList = weightageDefenitionEntry
				.getInterviewPercentageList(Integer.valueOf(courseId), Integer
						.valueOf(year));
		List<InterviewWeightageAdjustedTO> updatedPercentageList = WeightageDefenitionHelper
				.getInstance().updateInterviewWeightageAdjustedMarks(
						interviewPercentageList,weightage);
		weightageDefenitionEntry.updateInterviewWeightageAdjustedMarks(
				updatedPercentageList, weightageDefenitionForm.getUserId());
		log.info("exit of updateIndividualInterviewWeightageAdjustedMarks of WeightageDefenitionHandler class.");
	}

	/**
	 * Updates the general weightage adjusted marks.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void updateIndividualGeneralWeightageAdjustedMarks(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateIndividualGeneralWeightageAdjustedMarks of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		String courseId = weightageDefenitionForm.getCourseId();
		String year = weightageDefenitionForm.getYear();

		List updatedgeneralWeightageList = weightageDefenitionEntry
				.getTotalGeneralWeightage(Integer.valueOf(courseId), Integer
						.valueOf(year));
		List<GeneralWeightageAdjustedTO> generalWeightageAdjustedList = WeightageDefenitionHelper
				.getInstance().getUpdateGeneralWeightageAdjustedMarks(
						updatedgeneralWeightageList);
        
		// institute weightage list
		List updateGeneralInstitutionalWeightageList = weightageDefenitionEntry
				.getTotalGeneralInstitutionalWeightage(Integer
						.valueOf(courseId), Integer.valueOf(year));
		
		// previous qualification weightage list
		List updateGeneralPreviousQualificationWeightageList = weightageDefenitionEntry
		  .getTotalGeneralPreviousQualificationWeightage(Integer
				.valueOf(courseId), Integer.valueOf(year));

		
		// work experience weightage list
		/*List updateGeneralWorkExperienceWeightageList = WeightageDefenitionHelper.getInstance().
		getTotalGeneralWorkExperienceWeightage(updatedgeneralWeightageList,Integer.valueOf(courseId), Integer.valueOf(year));*/	
		
		List updateGeneralWorkExperienceWeightageList = WeightageDefenitionHelper.getInstance().
		    getTotalGeneralWorkExperienceWeightage(Integer.valueOf(courseId), Integer.valueOf(year));
	   
		// for getting institute,prev qualification and work experience weightage update 2gether at a time 
		List<GeneralWeightageAdjustedTO> updatedInstitutePrevQualificationAndWorkExperienceWeightageList = WeightageDefenitionHelper
		.getInstance()
		.getUpdateGeneralInstitutePreviousQualfnAndWorkExpWeightageAdjustedMarks(
				generalWeightageAdjustedList,updateGeneralInstitutionalWeightageList,updateGeneralPreviousQualificationWeightageList,
				updateGeneralWorkExperienceWeightageList);
	
      	 // for final institute, previous qualification and work exp weightage update
      	weightageDefenitionEntry.updateGeneralWeightageAdjustedMarks(
      			updatedInstitutePrevQualificationAndWorkExperienceWeightageList, weightageDefenitionForm.getUserId());
      	
		
		log.info("exit of updateIndividualGeneralWeightageAdjustedMarks of WeightageDefenitionHandler class.");
	}

	/**
	 * updates total weightage adjusted marks.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void updateIndividualTotalWeightageAdjustedMarks(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateIndividualTotalWeightageAdjustedMarks of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		int courseId = Integer.parseInt(weightageDefenitionForm.getCourseId());
		int year = Integer.parseInt(weightageDefenitionForm.getYear());

		List totalWeightageAdjustedMarks = weightageDefenitionEntry
				.getTotalWeightageAdjustedMarks(courseId, year);

		List getTotaleducationalWeightageAdjustedMarks = weightageDefenitionEntry
				.getTotalEducationalWeightageAdjustedMarks(courseId, year);

		List getTotalInterviewWeightageAdjustedMarks = weightageDefenitionEntry
				.getTotalInterviewWeightageAdjustedMarks(courseId, year);

		List getTotalPrerequisiteWeightageAdjustedMarks = weightageDefenitionEntry
				.getTotalPrerequisiteWeightageAdjustedMarks(courseId, year);

		HashMap<Integer, BigDecimal> prerequisiteMap = WeightageDefenitionHelper
				.getInstance().convertPrerequisiteListToMap(
						getTotalPrerequisiteWeightageAdjustedMarks);

		
		List<TotalWeightageAdjustedTO> totalWeightageAdjustedToList = WeightageDefenitionHelper
				.getInstance()
				.getUpdateTotalWeightageAdjustedMarks(
						totalWeightageAdjustedMarks,
						WeightageDefenitionHelper
								.getInstance()
								.convertListToMap(
										getTotaleducationalWeightageAdjustedMarks),
						WeightageDefenitionHelper
								.getInstance()
								.convertListToMap(
										getTotalInterviewWeightageAdjustedMarks),
						prerequisiteMap);

		weightageDefenitionEntry.updateTotalWeightageAdjustedMarks(
				totalWeightageAdjustedToList, weightageDefenitionForm
						.getUserId());
		log.info("exit of updateIndividualTotalWeightageAdjustedMarks of WeightageDefenitionHandler class.");
	}

	/**
	 * Persists the prerequisite weightage definition.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void submitPrerequisiteWeightageDefenition(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into submitPrerequisiteWeightageDefenition of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		weightageDefenitionEntry
				.submitPrerequisiteWeightage(weightageDefenitionForm);
		log.info("exit of submitPrerequisiteWeightageDefenition of WeightageDefenitionHandler class.");
	}

	/**
	 * Persists the prerequisite weightage definition.
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void updateIndividualPrerequisiteWeightageAdjustedMarks(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateIndividualPrerequisiteWeightageAdjustedMarks of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		int courseId = Integer.valueOf(weightageDefenitionForm.getCourseId());
		int year = Integer.valueOf(weightageDefenitionForm.getYear());
		List educationalPercentageList = weightageDefenitionEntry
				.getPrerequisitePercentageList(courseId, year);

		List<CoursePrerequisiteWeightageTO> updatedPercentageList = WeightageDefenitionHelper
				.getInstance().updatePrerequisiteWeightageAdjustedMarks(
						educationalPercentageList);

		weightageDefenitionEntry.persistPrerequisiteWeightageAdjustedMarks(
				updatedPercentageList, weightageDefenitionForm.getUserId());
		log.info("exit of updateIndividualPrerequisiteWeightageAdjustedMarks of WeightageDefenitionHandler class.");
	}
 
	public List<PreviousQualificationWeightageTO> getPreviousQualificationWeightageList(
			int courseId, int year) throws ApplicationException {
		log.info("entering into getPreviousQualificationWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		/*List previousQualiWeightageBO = weightageDefenitionEntry
				.getPreviousQualificationWeightage(courseId, year);*/
		List<DocChecklist> educationalWeightageTypeBOs=weightageDefenitionEntry.getEducationalWeightageTypes(courseId,year);
		List<PreviousQualificationWeightageTO> qualificationyWeightageTOs = WeightageDefenitionHelper
				.getInstance().convertBoToPreviousQualificationWeightageTOList(educationalWeightageTypeBOs,courseId,year);		
		log.info("exit of getPreviousQualificationWeightageList of WeightageDefenitionHandler class.");
		return qualificationyWeightageTOs;
	}
	
	public List<WorkExperienceWeightageTO> getWorkExperienceWeightageList(
			int courseId, int year) throws ApplicationException {
		log.info("entering into getWorkExperienceWeightageList of WeightageDefenitionHandler class.");
		IWeightageEntryTransaction weightageDefenitionEntry = new WeightageEntryTransactionImpl();
		
		WeightageDefinition for0to1yearWeightageBO = null;
		WeightageDefinition for1to2yearWeightageBO = null;
		WeightageDefinition for2to3yearWeightageBO = null;
		WeightageDefinition for3to4yearWeightageBO = null;
		WeightageDefinition for4to5yearWeightageBO = null;
		
		List for0to1yearWeightageDefinition = weightageDefenitionEntry
		.get0to1yearWorkExpWeightageDefinition(courseId, year);
		
		if (for0to1yearWeightageDefinition != null
				&& !for0to1yearWeightageDefinition.isEmpty()) {                 // for 0-1 year
			Object[] for0to1yearResults = ((Object[]) for0to1yearWeightageDefinition.get(0));
			for0to1yearWeightageBO = (WeightageDefinition) for0to1yearResults[0];
		}
		
		List for1to2yearWeightageDefinition = weightageDefenitionEntry
		.get1to2yearWorkExpWeightageDefinition(courseId, year);
		
		if (for1to2yearWeightageDefinition != null
				&& !for1to2yearWeightageDefinition.isEmpty()) {                 // for 1-2 year
			Object[] for1to2yearResults = ((Object[]) for1to2yearWeightageDefinition.get(0));
			for1to2yearWeightageBO = (WeightageDefinition) for1to2yearResults[0];
		}
		
		List for2to3yearWeightageDefinition = weightageDefenitionEntry
		.get2to3yearWorkExpWeightageDefinition(courseId, year);
		
		if (for2to3yearWeightageDefinition != null
				&& !for2to3yearWeightageDefinition.isEmpty()) {                 // for 2-3 year
			Object[] for2to3yearResults = ((Object[]) for2to3yearWeightageDefinition.get(0));
			for2to3yearWeightageBO = (WeightageDefinition) for2to3yearResults[0];
		}
		
		List for3to4yearWeightageDefinition = weightageDefenitionEntry
		.get3to4yearWorkExpWeightageDefinition(courseId, year);
		
		if (for3to4yearWeightageDefinition != null
				&& !for3to4yearWeightageDefinition.isEmpty()) {                 // for 3-4 year
			Object[] for3to4yearResults = ((Object[]) for3to4yearWeightageDefinition.get(0));
			for3to4yearWeightageBO = (WeightageDefinition) for3to4yearResults[0];
		}
		
		List for4to5yearWeightageDefinition = weightageDefenitionEntry
		.get4to5yearWorkExpWeightageDefinition(courseId, year);
		
		if (for4to5yearWeightageDefinition != null
				&& !for4to5yearWeightageDefinition.isEmpty()) {                 // for 4-5 year
			Object[] for4to5yearResults = ((Object[]) for4to5yearWeightageDefinition.get(0));
			for4to5yearWeightageBO = (WeightageDefinition) for4to5yearResults[0];
		}
		
		List<WorkExperienceWeightageTO> workExpWeightageTOs = WeightageDefenitionHelper
				.getInstance().convertBoToWorkExperienceWeightageTOList(for0to1yearWeightageBO,for1to2yearWeightageBO,
				for2to3yearWeightageBO,for3to4yearWeightageBO,for4to5yearWeightageBO);		
		log.info("exit of getWorkExperienceWeightageList of WeightageDefenitionHandler class.");
		return workExpWeightageTOs;
	}
}
