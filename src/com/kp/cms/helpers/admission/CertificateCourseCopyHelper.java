package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.CertificateCourseCopyForm;
import com.kp.cms.to.admission.CertificateCourseTO;

public class CertificateCourseCopyHelper {
	
	public static volatile CertificateCourseCopyHelper certificateCourseCopyHelper = null;
	public static CertificateCourseCopyHelper getInstance() {
	      if(certificateCourseCopyHelper == null) {
	    	  certificateCourseCopyHelper = new CertificateCourseCopyHelper();
	    	  return certificateCourseCopyHelper;
	      }
	      return certificateCourseCopyHelper;
	}

	
	/**
	 * 
	 * 
	 * @param fromCertificateCourses
	 * @param toCertificateCourses
	 * @param copyForm
	 * @return
	 * @throws Exception
	 */
	public List<CertificateCourse> copyFromCourseToToCourse(List<CertificateCourse> fromCertificateCourses,List<CertificateCourse>toCertificateCourses, CertificateCourseCopyForm copyForm) throws Exception
	{
		if(fromCertificateCourses!=null && !fromCertificateCourses.isEmpty())
		{
	List<CertificateCourse> coursesList=new ArrayList<CertificateCourse>();
		Iterator<CertificateCourse> iterator=fromCertificateCourses.iterator();
		while(iterator.hasNext())
		{
			CertificateCourse fromCourse=iterator.next();
			Iterator<CertificateCourse> iterator2=toCertificateCourses.iterator();
			while(iterator2.hasNext())
			{
				CertificateCourse toCourse=iterator2.next();
				if(fromCourse.getCertificateCourseName().equalsIgnoreCase(toCourse.getCertificateCourseName()))
				{
					throw new BusinessException();
				}
			
				
				
			}
            CertificateCourse copyCourse=new CertificateCourse();
            copyCourse.setCertificateCourseName(fromCourse.getCertificateCourseName());
			if(copyForm.getToSemType()!=null && !copyForm.getToSemType().isEmpty()){
				copyCourse.setSemType(copyForm.getToSemType());
			}
			copyCourse.setYear(Integer.parseInt(copyForm.getToAcademicYear()));
			//toCourse.setId(fromCourse.getId());
			copyCourse.setMaxIntake(fromCourse.getMaxIntake());
			copyCourse.setVenue(fromCourse.getVenue());
			copyCourse.setStartTime(fromCourse.getStartTime());
			copyCourse.setEndTime(fromCourse.getEndTime());
			copyCourse.setExtracurricular(fromCourse.getExtracurricular());
			copyCourse.setCreatedBy(copyForm.getUserId());
			copyCourse.setCreatedDate(new Date());
			copyCourse.setModifiedBy(copyForm.getUserId());
			copyCourse.setLastModifiedDate(new Date());
			copyCourse.setDescription(fromCourse.getDescription());
			copyCourse.setUsers(fromCourse.getUsers());
			copyCourse.setIsActive(fromCourse.getIsActive());
			copyCourse.setSubject(fromCourse.getSubject());
			copyCourse.setGroups(fromCourse.getGroups());
			copyCourse.setStudentCertificateCourses(fromCourse.getStudentCertificateCourses());
			coursesList.add(copyCourse);
			
			
		}
		
		
		return coursesList;
		}
		else
		{
		throw new ApplicationException();	
		}
	}
}
