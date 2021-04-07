package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.CourseScheme;
/**
 * 
 * @author kshirod.k
 * A transaction interface for Coursescheme
 *
 */

public interface ICourseSchemeTransaction {
	/**
	 *  Returns all the coursename and course ids from course scheme
	 * @return
	 */
	public List<CourseScheme> getCourseScheme() throws Exception ;
	
	/**
	 * Get name on the basis of id
	 */
	
	public CourseScheme getNameOnId(int id) throws Exception;

}
