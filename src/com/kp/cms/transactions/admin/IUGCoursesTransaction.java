package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.forms.admin.AdmittedThroughForm;
import com.kp.cms.forms.admin.UGCoursesForm;

public interface IUGCoursesTransaction {

	public List<UGCoursesBO> getUGCourses() throws Exception;
	public boolean addUGCourses(UGCoursesBO ugCourses, String mode) throws Exception;
	public boolean deleteUGCourses(int id, Boolean activate, UGCoursesForm ugCoursesForm) throws Exception;
	public UGCoursesBO isUGCoursesDuplcated(UGCoursesBO oldadmittedThrough)
	throws Exception;	
	
}
