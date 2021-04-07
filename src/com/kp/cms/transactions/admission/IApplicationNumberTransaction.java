package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.ApplicationNumberForm;

public interface IApplicationNumberTransaction {
	public boolean addApplicaionNumber(ApplicationNumber applicationNumber, String mode) throws  DuplicateException, Exception; 
	public List<ApplicationNumber> getApplicationNumber(int id,int currentYear) throws Exception;	
	public boolean deleteApplicationNumber(int appId,Boolean activate,ApplicationNumberForm applicationNumberForm) throws Exception;
	public Boolean isApplNoDuplcated(int year, int onLineFrom, int OnlineTo, int OffLineFrom, int OffLineTo, int id) throws Exception;
	public CourseApplicationNumber getCourseApplNo(int id) throws Exception;
	public Boolean checkIsCourseDuplicated(int courseId, int year) throws Exception;
	public boolean deleteCourseApplicationNumber(List<CourseApplicationNumber> courseApplicationList) throws Exception;
	public List<CourseApplicationNumber> getCourseApplicationNumber(int id) throws Exception;
}
