package com.kp.cms.helpers.alumini;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.alumini.AlumniEducationDetails;
import com.kp.cms.bo.alumini.AlumniRegistrationDetails;
import com.kp.cms.forms.alumini.AluminiRegistrationForm;
import com.kp.cms.to.alumni.AlumniEducationDetailsTO;

public class AluminiRegistrationHelper {

	private static volatile AluminiRegistrationHelper obj;
	
	private AluminiRegistrationHelper() {
		
	}
	
	public static AluminiRegistrationHelper getInstance() {
		if(obj == null) {
			obj = new AluminiRegistrationHelper();
		}
		return obj;
	}
	
	public AlumniRegistrationDetails convertFormToBO(AluminiRegistrationForm aluminiRegistrationForm) throws Exception {
		AlumniRegistrationDetails alumniRegistrationDetails = new AlumniRegistrationDetails();
		
		alumniRegistrationDetails.setFirstName(aluminiRegistrationForm.getFirstName());
		alumniRegistrationDetails.setMobileNumber(aluminiRegistrationForm.getMobileNo2());
		alumniRegistrationDetails.setEmail(aluminiRegistrationForm.getEmailId());
		alumniRegistrationDetails.setCurrentStatus(aluminiRegistrationForm.getCurrentStatus());
		alumniRegistrationDetails.setComapnyName(aluminiRegistrationForm.getCompanyName());
		alumniRegistrationDetails.setDesignation(aluminiRegistrationForm.getDesignation());
		alumniRegistrationDetails.setOtherJobInfo(aluminiRegistrationForm.getOtherJobDetails());
		alumniRegistrationDetails.setCountry(aluminiRegistrationForm.getCountryName());
		alumniRegistrationDetails.setCity(aluminiRegistrationForm.getCity());
		alumniRegistrationDetails.setHighestQualification(aluminiRegistrationForm.getHighestQualification());
		alumniRegistrationDetails.setCreatedDate(new Date());
		
		List<AlumniEducationDetailsTO> educationDetails = aluminiRegistrationForm.getEducationDetails();
		Set<AlumniEducationDetails> educationDetailsSet = new HashSet<AlumniEducationDetails>();
		Iterator<AlumniEducationDetailsTO> it = educationDetails.iterator();
		while(it.hasNext()) {
			AlumniEducationDetailsTO to = it.next();
			AlumniEducationDetails bo = new AlumniEducationDetails();
			
			Course course = new Course();
			course.setId(Integer.parseInt(to.getCourseId()));
			bo.setCourse(course);
			
			bo.setBatchFrom(Integer.parseInt(to.getBatchFrom()));
			bo.setBatchTo(Integer.parseInt(to.getBatchTo()));
			bo.setPassOutYear(Integer.parseInt(to.getBatchTo()));
			
			bo.setCreatedDate(new Date());
			
			educationDetailsSet.add(bo);
			
		}
		
		alumniRegistrationDetails.setAlumniEducationDetails(educationDetailsSet);
		
		return alumniRegistrationDetails;
	}
}
