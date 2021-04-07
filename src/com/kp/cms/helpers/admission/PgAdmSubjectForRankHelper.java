package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admission.PgAdmSubjectForRank;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.forms.admission.PgAdmSubjectForRankForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.to.admission.PgAdmSubjectForRankTo;

public class PgAdmSubjectForRankHelper {

	public static List<PgAdmSubjectForRankTo> convertBotoTo
	                                  (List<PgAdmSubjectForRank> subjectlist) {

		List<PgAdmSubjectForRankTo> subjecttolist = new ArrayList<PgAdmSubjectForRankTo>();
		Iterator<PgAdmSubjectForRank> iterator = subjectlist.iterator();
		if(subjectlist != null){
		while(iterator.hasNext()){
			PgAdmSubjectForRankTo subjectto = new PgAdmSubjectForRankTo();
			PgAdmSubjectForRank subject = iterator.next();
			ProgramTypeTO programTypeTO=new ProgramTypeTO();
			if(subject.getProgramType()!=null){
				programTypeTO.setProgramTypeId(subject.getProgram().getId());
				programTypeTO.setProgramTypeName(subject.getProgram().getName());
				
			}else{
				programTypeTO.setProgramTypeId(subject.getProgram().getProgramType().getId());
				programTypeTO.setProgramTypeName(subject.getProgram().getProgramType().getName());
				
			}
			subjectto.setProgramTypeTO(programTypeTO);
			CourseTO courseTO=new CourseTO();
			courseTO.setId(subject.getCourse().getId());
			courseTO.setName(subject.getCourse().getName());
			subjectto.setCourseTO(courseTO);
			ProgramTO programTO=new ProgramTO();
			programTO.setId(subject.getProgram().getId());
			programTO.setName(subject.getProgram().getName());
			subjectto.setProgramTO(programTO);
			UGCoursesTO uGCoursesTO=new UGCoursesTO();
			uGCoursesTO.setId(subject.getuGCoursesBO().getId());
			uGCoursesTO.setName(subject.getuGCoursesBO().getName());
			subjectto.setuGCoursesTO(uGCoursesTO);
			subjectto.setId(subject.getId());
			subjecttolist.add(subjectto);
			}
		
           }
		return subjecttolist;
	}

	public static List<PgAdmSubjectForRank> convertTotoBo(
			PgAdmSubjectForRankForm admsbjctfrm, String mode) {
		
			String[] selectedCourseIds=admsbjctfrm.getSelectedCourseId();
			List<PgAdmSubjectForRank> pgAdmSubjectForRanklist=new ArrayList<PgAdmSubjectForRank>();
			if(selectedCourseIds.length!=0){
				for (int i = 0; i < selectedCourseIds.length; i++) {
					PgAdmSubjectForRank admsbjctbo = new PgAdmSubjectForRank();
					   Course course=new Course();
					   course.setId(Integer.parseInt(admsbjctfrm.getSelectedCourse()));
					   admsbjctbo.setCourse(course);
					   Program program=new Program();
					   program.setId(Integer.parseInt(admsbjctfrm.getSelectedProgram()));
					   admsbjctbo.setProgram(program);
					   UGCoursesBO uGCoursesBO=new UGCoursesBO();
					   uGCoursesBO.setId(Integer.parseInt(selectedCourseIds[i]));
					   admsbjctbo.setuGCoursesBO(uGCoursesBO);
					   ProgramType programType=new ProgramType();
					   programType.setId(Integer.parseInt(admsbjctfrm.getProgramTypeId()));
					   admsbjctbo.setProgramType(programType);
					   admsbjctbo.setIsActive(true);
					   if (mode.equals("ADD")) {
							  
							  admsbjctbo.setCreatedBy(admsbjctfrm.getUserId());
							  admsbjctbo.setCreatedDate(new Date());
							  
							  }
						  else if(mode.equals("EDIT")) {
							  admsbjctbo.setModifiedBy(admsbjctfrm.getUserId());
							  admsbjctbo.setLastModifiedDate(new Date());
							  admsbjctbo.setId(admsbjctfrm.getId());
							
						}
					   pgAdmSubjectForRanklist.add(admsbjctbo);
				}
				
			}
		
		   
		  
		 
		return pgAdmSubjectForRanklist;
		
	
	}
	
	

}
