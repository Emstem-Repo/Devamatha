package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantWorkExperience;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.Weightage;
import com.kp.cms.bo.admin.WeightageDefinition;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.WeightageDefenitionForm;
import com.kp.cms.to.admission.CoursePrerequisiteWeightageTO;
import com.kp.cms.to.admission.EducationalWeightageAdjustedTO;
import com.kp.cms.to.admission.GeneralWeightageAdjustedTO;
import com.kp.cms.to.admission.InterviewWeightageAdjustedTO;
import com.kp.cms.to.admission.TotalWeightageAdjustedTO;

/**
 * An interface for the Weightage entry transaction.
 *
 */
public interface IWeightageEntryTransaction {

	/**
	 * Get the educational(marks card) weightage types based on course id.
	 * 
	 * @param courseId
	 *            - Represents the course id.
	 * @return - java.util.List of type DocChecklist.
	 * @throws ApplicationException
	 */
	public List<DocChecklist> getEducationalWeightageTypes(Integer courseId,
			Integer year) throws ApplicationException;

	/**
	 * Get the interview type and corresponding weightage based on course type.
	 * 
	 * @param courseId
	 *            - Represents the course id.
	 * @return - java.util.List of type InterviewProgramCourse.
	 * @throws ApplicationException
	 */
	public List<InterviewProgramCourse> getInterviewWeightageTypes(
			Integer courseId, Integer year) throws ApplicationException;

	/**
	 *  Get the country weightage for the course id and year.
	 * @param courseId - Represents the course the coure id.
	 * @param year - Represents the selected year.
	 * @return List of Weightage for the country.
	 * @throws ApplicationException
	 */
	public List getCountryWeightage(Integer courseId, Integer year)
			throws ApplicationException;

	/**
     *  Get the religion section  weightage for the course id and year.
	 * @param courseId - Represents the course the coure id.
	 * @param year - Represents the selected year.
	 * @return List of Weightage for the religion section.
	 * @throws ApplicationException
	 */
	public List getReligionSectionWeightage(Integer courseId, Integer year)
			throws ApplicationException;

	/**
     * Get the institute weightage for the course id and year.
	 * @param courseId - Represents the course the coure id.
	 * @param year - Represents the selected year.
	 * @return List of Weightage for the institute.
	 * @throws ApplicationException
	 */
	public List getInstituteWeightage(Integer courseId, Integer year)
			throws ApplicationException;

	/**
	 * Get the nationality weightage for the course id and year.
	 * @param courseId - Represents the course the coure id.
	 * @param year - Represents the selected year.
	 * @return List of Weightage for the nationality.
	 * @throws ApplicationException
	 */
	public List getNationalityWeightage(Integer courseId, Integer year)
			throws ApplicationException;

	/**
	 * Get the religion weightage for the course id and year.
	 * @param courseId - Represents the course the coure id.
	 * @param year - Represents the selected year.
	 * @return List of Weightage for the religion.
	 * @throws ApplicationException
	 */
	public List getReligionWeightage(Integer courseId, Integer year)
			throws ApplicationException;

	/**
	 * Get the resident category weightage for the course id and year.
	 * @param courseId - Represents the course the coure id.
	 * @param year - Represents the selected year.
	 * @return List of Weightage for the resident category.
	 * @throws ApplicationException
	 */
	public List getResidentCategoryWeightage(Integer courseId, Integer year)
			throws ApplicationException;

	/**
	 * Get the university weightage for the course id and year.
	 * @param courseId - Represents the course the coure id.
	 * @param year - Represents the selected year.
	 * @return List of Weightage for the university.
	 * @throws ApplicationException
	 */
	public List getUniversityWeightage(Integer courseId, Integer year)
			throws ApplicationException;

	/**
     * Get the caste weightage for the course id and year.
	 * @param courseId - Represents the course the coure id.
	 * @param year - Represents the selected year.
	 * @return List of Weightage for the university.
	 * @throws ApplicationException
	 */
	public List getCasteWeightage(Integer courseId, Integer year)
			throws ApplicationException;
    
		
	/**
	 * Persists the weightage definition.
	 * @param weightagDefenitionForm - Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void submitWeightageDefenition(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * Get the weightage definition for the course and year.
	 * @param weightagDefenitionForm - Represents WeightageDefenitionForm object.
	 * @return - Weightage object.
	 * @throws ApplicationException
	 */
	public Weightage getWeightageDefenition(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;
    
	public Weightage getWeightageByCourseAndYear(
			int courseId,int year)
			throws ApplicationException;
	/**
	 * Persists the educational weightage.
	 * @param weightagDefenitionForm - Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void submitEducationalWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * Persists the interview weightage.
	 * @param weightagDefenitionForm - Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void submitInterviewWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * Persists the belongs to weightage.
	 * @param weightagDefenitionForm - Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void updateBelongsToWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * Get the list of institutions.
	 * @return - List of college BO
	 * @throws ApplicationException
	 */
	public List<College> getInstituteList() throws ApplicationException;

	/**
	 * get the list of countries.
	 * @return - List of Country BO
	 * @throws ApplicationException
	 */
	public List<Country> getCountryList() throws ApplicationException;

	// =========== updating general weightage
	
	/**
	 * Persists the Caste weightage.
	 */
	public void updateCasteWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * Persists the Religion section weightage.
	 * @param weightagDefenitionForm - Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void updateReligionSectionWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * Persists the country weightage.
	 * @param weightagDefenitionForm- Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void updateCountryWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * Persists the gender weightage.
	 * @param weightagDefenitionForm- Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void updateGenderWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * Persists the institute weightage.
	 * @param weightagDefenitionForm- Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void updateInstituteWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * Persists the nationality weightage.
	 * @param weightagDefenitionForm- Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void updateNationalityWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
	 * 
	 * Persists the religion weightage.
	 * @param weightagDefenitionForm- Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void updateReligionWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
     * 
	 * Persists the resident category weightage.
	 * @param weightagDefenitionForm- Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void updateResidentCategoryWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;

	/**
     * 
	 * Persists the university weightage.
	 * @param weightagDefenitionForm- Represents WeightageDefenitionForm object.
	 * @throws ApplicationException
	 */
	public void updateUniversityWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;
	
	
	
	/**
	 * Get the educational percentage list for the course id and year.
	 * @param courseId - Represents the course id.
	 * @param year - Represents the selected year.
	 * @return List of object array containing educational percentage.
	 * @throws ApplicationException
	 */
	public List getEducationalPercentageList(int courseId, int year)
			throws ApplicationException;

	/**
	 * Persists the educational weighatge adjusted marks.
	 * @param updatedEducationalWeightageList - Represents List of educational weightage adjusted TO
	 * @param userId - Represents the User id.
	 * @throws ApplicationException
	 */
	public void updateEducationalWeightageAdjustedMarks(
			List<EducationalWeightageAdjustedTO> updatedEducationalWeightageList,
			String userId) throws ApplicationException;

	/**
     * Get the interview percentage list for the course id and year.
	 * @param courseId - Represents the course id.
	 * @param year - Represents the selected year.
	 * @return List of object array containing interview percentage.
	 * @throws ApplicationException
	 */
	public List getInterviewPercentageList(int courseId, int year)
			throws ApplicationException;

	/**
	 * Persists the interview weighatge adjusted marks.
	 * @param updatedInterviewWeightageList - Represents List of interview weightage adjusted TO
	 * @param userId - Represents the User id.
	 * @throws ApplicationException
	 */
	public void updateInterviewWeightageAdjustedMarks(
			List<InterviewWeightageAdjustedTO> updatedInterviewWeightageList,
			String userId) throws ApplicationException;

	/**
	 * get the general weightage list for course id and year.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return List of object array containing general weightage.
	 * @throws ApplicationException
	 */
	public List getTotalGeneralWeightage(int courseId, int year)
			throws ApplicationException;

	/**
	 * persists the general weightage adjusted marks.
	 * @param updatedgeneralWeightageList - Represents the List of GeneralWeightageAdjustedTO objects
	 * @param userId - Represents the user id.
	 * @throws ApplicationException
	 */
	public void updateGeneralWeightageAdjustedMarks(
			List<GeneralWeightageAdjustedTO> updatedgeneralWeightageList,
			String userId) throws ApplicationException;

	/**
	 * Get the list of total weighhtage adjusted marks.
	 * @param courseId- Represents the course id.
	 * @param year - Represents the selected year.
	 * @return- List of object array containing total weighhtage adjusted marks.
	 * @throws ApplicationException
	 */
	public List getTotalWeightageAdjustedMarks(int courseId, int year)
			throws ApplicationException;

	/**
	 * persists the total general weightage adjusted marks.
	 * @param updatedtotalWeightageList - Represents list of TotalWeightageAdjustedTO objects
	 * @param userId - Represents the user id.
	 * @throws ApplicationException
	 */
	public void updateTotalWeightageAdjustedMarks(
			List<TotalWeightageAdjustedTO> updatedtotalWeightageList,
			String userId) throws ApplicationException;

	/**
	 * Get the total institutional weightage adjusted marks.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return List of object array containing total institutional weightage.
	 * @throws ApplicationException
	 */
	public List getTotalGeneralInstitutionalWeightage(int courseId, int year)
			throws ApplicationException;
	/**
	 * Get the total interview weightage adjusted marks.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return List of object array containing total  interview weightage.
	 * @throws ApplicationException
	 */
	public List getTotalInterviewWeightageAdjustedMarks(int courseId, int year)
			throws ApplicationException;

	/**
	 * Get the total educational weightage adjusted marks.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return -List of object array containing total  educational weightage.
	 * @throws ApplicationException
	 */
	public List getTotalEducationalWeightageAdjustedMarks(int courseId, int year)
			throws ApplicationException;

	/**
	 * Get the urban weightage definition.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return List of object array containing urban weightage.
	 * @throws ApplicationException
	 */
	public List getUrbanWeightageDefinition(Integer courseId, Integer year)
			throws ApplicationException;

	/**
     * Get the rural weightage definition.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return List of object array containing rural weightage.
	 * @throws ApplicationException
	 */
	public List getRuralWeightageDefinition(Integer courseId, Integer year)
			throws ApplicationException;

	/**
     * Get the female weightage definition.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return List of object array containing female weightage.
	 * @throws ApplicationException
	 */
	public List getFeMaleWeightageDefinition(Integer courseId, Integer year)
			throws ApplicationException;

	/**
     * Get the male weightage definition.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return List of object array containing male weightage.
	 * @throws ApplicationException
	 */
	public List getMaleWeightageDefinition(Integer courseId, Integer year)
			throws ApplicationException;

	/**
	 * Get the prerequisite weightage types.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return List of CoursePrerequisite BO  objects.
	 * @throws ApplicationException
	 */
	public List<CoursePrerequisite> getPrerequisiteWeightageTypes(int courseId,
			int year) throws ApplicationException;

	/**
	 * Persists the prerequisite weightage
	 * @param weightageDefenitionForm
	 * @throws ApplicationException
	 */
	public void submitPrerequisiteWeightage(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException;

	/**
	 * Get the prerequisite percentage list.
	 * @param courseId- Represents the course id.
	 * @param year- Represents the selected year.
	 * @return - List of object array containing prerequisite percentage 
	 * @throws ApplicationException
	 */
	public List getPrerequisitePercentageList(int courseId, int year)
			throws ApplicationException;

	/**
	 * Persists the Prerequisite weightage adjusted marks
	 * @param updatedPercentageList - List of CoursePrerequisiteWeightageTO  objects
	 * @param userId - Represents user id.
	 * @throws ApplicationException
	 */
	public void persistPrerequisiteWeightageAdjustedMarks(
			List<CoursePrerequisiteWeightageTO> updatedPercentageList,
			String userId) throws ApplicationException;

	/**
	 * Get the total prerequisite weightage adjusted marks
	 * @param courseId - Represents the course id.
	 * @param year - Represents the selected year.
	 * @return List of objects containing total prerequisite weightage adjusted marks
	 * @throws ApplicationException
	 */
	
	public List getTotalPrerequisiteWeightageAdjustedMarks(int courseId,
			int year) throws ApplicationException;
	
	
	public List getPreviousQualificationWeightage(Integer courseId, Integer year)
    throws ApplicationException;

	// for getting work experience weightages for different years of experience.
	//here it is retrieving 5 times for 1 to 5 years experiences .  
	
	public List get0to1yearWorkExpWeightageDefinition(Integer courseId, Integer year)
       throws ApplicationException;
	
	public List get1to2yearWorkExpWeightageDefinition(Integer courseId, Integer year)
       throws ApplicationException;
	
	public List get2to3yearWorkExpWeightageDefinition(Integer courseId, Integer year)
       throws ApplicationException;
	
	public List get3to4yearWorkExpWeightageDefinition(Integer courseId, Integer year)
       throws ApplicationException;
	
	public List get4to5yearWorkExpWeightageDefinition(Integer courseId, Integer year)
       throws ApplicationException;
	
	public WeightageDefinition getWeightageDefenitionByWeightageIdAndDocTypeExamId(
			int weightageId,int docTypeExamId)
			throws ApplicationException;
	
	
	public void updatePreviousQualificationWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;
	
	public List getTotalGeneralPreviousQualificationWeightage(int courseId,int year)
     throws ApplicationException;
	
	public void updateWorkExperienceWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException;
	
	// to get work experience using AdmAppln id
	public List<ApplicantWorkExperience> getWorkExperienceList(
			int admApplnId) throws ApplicationException;
	
	// to get final work experience weightage by noOfYeras of experience
	public List getWorkExperienceWeightage(int courseId, int year,String yearsOfExp)
	  throws ApplicationException;
	
	// to get all AdmAppln having workExprience applying for a particular course and a year
	public List<Integer> getAdmApplnHavingWorkExp(int courseId,int year) 
		throws ApplicationException; 
	
	 List<WeightageDefinition> getweightageDefinitions(int courseId, int year)
	   throws ApplicationException ;
	
	

}
