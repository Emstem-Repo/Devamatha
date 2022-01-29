package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.forms.admin.UGCoursesForm;
import com.kp.cms.to.admin.UGCoursesTO;



public class UGCoursesHelper {
	public static volatile UGCoursesHelper ugCoursesHelper = null;
	public static final Log log = LogFactory.getLog(UGCoursesHelper.class);
    private UGCoursesHelper(){
    	
    }
	public static UGCoursesHelper getInstance() {
		if (ugCoursesHelper == null) {
			ugCoursesHelper = new UGCoursesHelper();
			return ugCoursesHelper;
		}
		return ugCoursesHelper;
	}

	/**
	 * Creating TO's from BO
	 * @param admittedThroughList
	 * @return
	 */
	public List<UGCoursesTO> copyUGCoursesBosToTos(List<UGCoursesBO> ugCoursesList) {
		List<UGCoursesTO> ugCoursesTOList = new ArrayList<UGCoursesTO>();
		Iterator<UGCoursesBO> i = ugCoursesList.iterator();
		UGCoursesBO ugCourses;
		UGCoursesTO ugCoursesTO;
		while (i.hasNext()) {
			ugCoursesTO = new UGCoursesTO();
			ugCourses = i.next();
			ugCoursesTO.setId(ugCourses.getId());
			ugCoursesTO.setName(ugCourses.getName());
			ugCoursesTOList.add(ugCoursesTO);
		}
		log.error("ending of copyUGCoursesBosToTos method in UGCoursesHelper");
		return ugCoursesTOList;
	}
	
	/**
	 * 
	 * @param  AdmittedThroughForm creates BO from admittedThroughForm 
	 *            
	 * @return AdmittedThrough BO object
	 */

	public UGCoursesBO populateUGCoursesDataFormForm(
		UGCoursesForm admittedThroughForm ) throws Exception {
		UGCoursesBO ugCourses = new UGCoursesBO();
		ugCourses.setId(admittedThroughForm.getUgCoursesId());
		ugCourses.setName(admittedThroughForm.getUgCoursesName().trim());
		ugCourses.setIsActive(true);  //in add and edit we can set this as true
		log.error("ending of populateUGCoursesDataFormForm method in UGCoursesHelper");
		return ugCourses;
	}
	
}
