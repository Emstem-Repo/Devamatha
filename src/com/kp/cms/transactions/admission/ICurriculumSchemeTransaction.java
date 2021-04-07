package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.Student;


public interface ICurriculumSchemeTransaction  {

	/**
	 * 
	 * @param courseid
	 * @returns ProgramType Name, Program Name & Course Name based on the
	 *          courseId
	 */

	public Course getCourseProgrmProgramType(int courseid) throws Exception;

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method saves a curricular scheme based on the startdate and
	 *         end date
	 * @throws Exception
	 */
	public boolean setCurriculumScheme(CurriculumScheme scheme)throws Exception;
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  Checking in database for duplicate record based on the courseID and year
	 * @throws Exception
	 */
	
	public List<CurriculumScheme> isExistCourseId(int courseId, int year)throws Exception;
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  gets all the records of curriculumScheme
	 * @throws Exception
	 */
	
	public List<CurriculumScheme> getCurriculumSchemeDetails()throws Exception;
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  gets all the records of curriculumScheme Year wise
	 * @throws Exception
	 */
	public List<CurriculumScheme> getCurriculumSchemeDetailsYearwise(int year)throws Exception;
	
	/**
	 * 
	 * @param Deletes a record of Curriculum scheme based on the ID
	 * @return
	 */
	
	public boolean deleteCurriculumScheme(CurriculumScheme curriculumScheme)throws Exception;
	/**
	 * 
	 * @param 
	 * @return gets curriculumScheme based on the id
	 */
	public CurriculumScheme getCurriculumSchemeDetailsOnId(int id) throws Exception;
	
	/**
	 * 
	 * @param 
	 * @return updates a record in CurricularScheme
	 */
	public boolean updateCurriculumScheme(CurriculumScheme curriculumScheme)throws Exception;
	
	
	public List getCurriculumSchemeCourseByCourse(int courseId,int year) throws Exception; 
	public List<CurriculumSchemeDuration> getScheme(int id, Integer appliedYear,Student student)throws Exception; 
	public CurriculumSchemeDuration getCurriculumSchemeDurationByClassId(int classId);

}
