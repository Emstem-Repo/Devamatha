package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.forms.admin.ProgramTypeForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.UGCoursesTO;

public class ProgramTypeHelper {
	
	
	public static ProgramTypeHelper getInstance() {
		ProgramTypeHelper programTypeHelper=null;
		if (programTypeHelper == null) {
			programTypeHelper = new ProgramTypeHelper();
		}
		return programTypeHelper;
	}
	
	
	
	
	/**
	 * Converts Business Objects to Transaction object of the Program Type.
	 * @param programbo - Represents the Program Type Business objects.
	 * @return List - Program Type Transaction object.
	 */
	
	
	public static List<ProgramTypeTO> convertBOstoTos(
			List<ProgramType> programbolist) {
		List<ProgramTypeTO> programTypeList = new ArrayList<ProgramTypeTO>();
		if (programbolist != null) {
			Iterator<ProgramType> iterator = programbolist.iterator();
			while (iterator.hasNext()) {
				ProgramType programbo = (ProgramType) iterator.next();
				ProgramTypeTO programTypeTo = new ProgramTypeTO();
				programTypeTo.setProgramTypeId(programbo.getId());
				programTypeTo.setProgramTypeName(programbo.getName());
				if(programbo.getAgeFrom() > 0){
					programTypeTo.setAgeFrom(Integer.toString(programbo.getAgeFrom()));
				}
				if(programbo.getAgeTo() > 0){
					programTypeTo.setAgeTo(Integer.toString(programbo.getAgeTo()));
				}
				
				//raghu for admission
				if(programbo.getIsOpen()!=null){
					programTypeTo.setIsOpen(programbo.getIsOpen());
				}else{
					programTypeTo.setIsOpen(false);
				}
				
				
				List<ProgramTO> programsList = new ArrayList<ProgramTO>();
				Set<Program> programs=programbo.getPrograms();
					if(programs!=null){
						
						Iterator<Program> progitr = programs.iterator();
						while (progitr.hasNext()) {
							Program p=(Program)progitr.next();
							Set<Course> courses=p.getCourses();
							List<CourseTO> courseList = new ArrayList<CourseTO>();
								if(courses!=null){
									Iterator<Course> crsitr = courses.iterator();
										while (crsitr.hasNext()) {
											Course course = (Course) crsitr
													.next();
											CourseTO courseTO= new CourseTO();
											courseTO.setId(course.getId());
											courseTO.setName(course.getName());
											courseTO.setCode(course.getCode());
											courseTO.setIsActive(course.getIsActive());
											courseList.add(courseTO);
										}
								}
							
							ProgramTO pto= new ProgramTO();
							pto.setId(p.getId());
							pto.setName(p.getName());
							pto.setCode(p.getCode());
							pto.setIsDisplayLanguageKnown(p.getIsDisplayLanguageKnown());
							pto.setIsHeightWeight(p.getIsHeightWeight());
							pto.setIsMotherTongue(p.getIsMotherTongue());
							pto.setIsDisplayTrainingCourse(p.getIsDisplayTrainingCourse());
							pto.setIsAdditionalInfo(p.getIsAdditionalInfo());
							pto.setIsExtraDetails(p.getIsExtraDetails());
							pto.setIsSecondLanguage(p.getIsSecondLanguage());
							pto.setIsFamilyBackground(p.getIsFamilyBackground());
							pto.setIsTCDetails(p.getIsTCDetails());
							pto.setIsEntranceDetails(p.getIsEntranceDetails());
							pto.setIsLateralDetails(p.getIsLateralDetails());
							pto.setIsTransferCourse(p.getIsTransferCourse());
							pto.setIsExamCenterRequired(p.getIsExamCenterRequired());
							pto.setCourseList(courseList);
							programsList.add(pto);
						}
					}
					programTypeTo.setPrograms(programsList);
				programTypeList.add(programTypeTo);
			}
		}
		return programTypeList;
	}
	/**
	 * 
	 * @param programTypeForm
	 * @return
	 */
	public ProgramType createProgramTypeObject(ProgramTypeForm programTypeForm)
	{
		ProgramType programType = new ProgramType();
		programType.setName(programTypeForm.getProgramTypeName());
		if(programTypeForm.getAgeFrom()!= null && !programTypeForm.getAgeFrom().trim().isEmpty()){
			programType.setAgeFrom(Integer.parseInt(programTypeForm.getAgeFrom()));
		}
		if(programTypeForm.getAgeTo()!= null && !programTypeForm.getAgeTo().trim().isEmpty()){
			programType.setAgeTo(Integer.parseInt(programTypeForm.getAgeTo()));
		}
		programType.setId(programTypeForm.getId());
		programType.setCreatedBy(programTypeForm.getUserId());
		programType.setModifiedBy(programTypeForm.getUserId());
		programType.setCreatedDate(new Date());
		programType.setIsActive(true);
		programType.setLastModifiedDate(new Date());
		
		//raghu for admission
		if(programTypeForm.getIsOpen()!=null && programTypeForm.getIsOpen().equalsIgnoreCase("true")){
			programType.setIsOpen(true);
		}else{
			programType.setIsOpen(false);
		}
		
		
		return programType;
	}

	public static List<UGCoursesTO> convertBotoTos(List<UGCoursesBO> courselist) {
	
		// TODO Auto-generated method stub
		List<UGCoursesTO> crslist=new ArrayList<UGCoursesTO>();
		Iterator<UGCoursesBO> itr=courselist.iterator();
		
		while(itr.hasNext()){
			UGCoursesBO uGCoursesBO=itr.next();
			UGCoursesTO uGCoursesTO=new UGCoursesTO();
			uGCoursesTO.setName(uGCoursesBO.getName());
			uGCoursesTO.setId(uGCoursesBO.getId());
			crslist.add(uGCoursesTO);
		}
		return crslist;
	}

}
