package com.kp.cms.handlers.admin;

import java.util.List;


import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.helpers.admin.CourseSchemeHelper;
import com.kp.cms.to.admin.CourseSchemeTO;
import com.kp.cms.transactions.admin.ICourseSchemeTransaction;
import com.kp.cms.transactionsimpl.admin.CourseSchemeTransactionImpl;

public class CourseSchemeHandler {
	private static volatile CourseSchemeHandler courseSchemeHandler = null;

	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */

	public static CourseSchemeHandler getInstance() {
		if (courseSchemeHandler == null) {
			courseSchemeHandler = new CourseSchemeHandler();
			return courseSchemeHandler;
		}
		return courseSchemeHandler;
	}
	
	ICourseSchemeTransaction courseSchemeTransaction=new CourseSchemeTransactionImpl();
	/**
	 * 
	 * @return This method returns all the coursename and course ids from course scheme
	 */
	public List<CourseSchemeTO> getCourseScheme() throws Exception
	{
		
		List<CourseScheme> courseSchemeList=courseSchemeTransaction.getCourseScheme();
		return CourseSchemeHelper.getInstance().populateCourseSchemeBOtoTO(courseSchemeList);
	}
	
/**
 * 	
 * @param id
 * @return Gets the name based on the id
 * @throws Exception
 */
	public CourseSchemeTO getNameOnId(int id)throws Exception
	{
		CourseSchemeTO courseSchemeTO=new CourseSchemeTO();
		if(courseSchemeTransaction!=null){
			CourseScheme courseScheme=courseSchemeTransaction.getNameOnId(id);
			if(courseScheme!=null){
				
				courseSchemeTO.setCourseSchemeName(courseScheme.getName());
			}
			return courseSchemeTO;
		}
		return null;		
		
	}

}
