package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.admission.InterviewDefinitionForm;
import com.kp.cms.to.admission.InterviewProgramCourseTO;

public class InterviewDefinitionHelper {
	
	/**
	 * @param interviewDefinition
	 * @return TO
	 * @throws Exception
	 */
	public List<InterviewProgramCourseTO> convertBOstoTOs(List<InterviewProgramCourse> interviewDefinition) throws Exception {
	
		List<InterviewProgramCourseTO> interviewDefinitionList = new ArrayList<InterviewProgramCourseTO>();
		InterviewProgramCourse interviewProgramCourse;
		
		if(interviewDefinition != null ){
			InterviewProgramCourseTO interviewProgramCourseTO = null;
			Iterator<InterviewProgramCourse> iterator = interviewDefinition.iterator();
			while (iterator.hasNext()) {
				interviewProgramCourseTO = new InterviewProgramCourseTO();
			
				interviewProgramCourse = (InterviewProgramCourse) iterator.next();
				
				interviewProgramCourseTO.setId(interviewProgramCourse.getId());
				if( interviewProgramCourse.getProgram() != null && interviewProgramCourse.getProgram().getProgramType() != null ){
					interviewProgramCourseTO.setProgramTypeId(interviewProgramCourse.getProgram().getProgramType().getId());
					interviewProgramCourseTO.setProgramTypeName(interviewProgramCourse.getProgram().getProgramType().getName());
				}
				if( interviewProgramCourse.getProgram() != null ){
					interviewProgramCourseTO.setProgramId(interviewProgramCourse.getProgram().getId());
					interviewProgramCourseTO.setProgramName(interviewProgramCourse.getProgram().getName());
				}
				if( interviewProgramCourse.getCourse() != null ){
					interviewProgramCourseTO.setCourseId(interviewProgramCourse.getCourse().getId());
					interviewProgramCourseTO.setCourseName(interviewProgramCourse.getCourse().getName());
				}
				interviewProgramCourseTO.setYear(interviewProgramCourse.getYear());
				interviewProgramCourseTO.setName(interviewProgramCourse.getName());
				interviewProgramCourseTO.setSequence(interviewProgramCourse.getSequence());
				interviewProgramCourseTO.setContent(interviewProgramCourse.getContent());
				if(interviewProgramCourse.getNoOfInterviewsPerPanel() != null){
					interviewProgramCourseTO.setInterviewsPerPanel(String.valueOf(interviewProgramCourse.getNoOfInterviewsPerPanel()));
				}
				
				interviewDefinitionList.add(interviewProgramCourseTO);
			}
		}
		return interviewDefinitionList;
	}

	/**
	 * @param interviewDefinitionForm
	 * @param mode
	 * @return InterviewProgramCourse
	 * @throws Exception
	 */
	public InterviewProgramCourse convertBOs(InterviewDefinitionForm interviewDefinitionForm, String mode) throws Exception {
		InterviewProgramCourse interviewProgramCourse = null;
		if(interviewDefinitionForm != null ){
			interviewProgramCourse = new InterviewProgramCourse();
			Program program = new Program();
			if( interviewDefinitionForm.getProgramId() != null){
				program.setId(Integer.parseInt(interviewDefinitionForm.getProgramId()));
			}
			Course course = new Course();
			if( interviewDefinitionForm.getCourseId() !=  null){
				course.setId(Integer.parseInt(interviewDefinitionForm.getCourseId()));
			}
			interviewProgramCourse.setId(interviewDefinitionForm.getId());
			interviewProgramCourse.setProgram(program);
			interviewProgramCourse.setCourse(course);
			if ( interviewDefinitionForm.getYear() != null){
				interviewProgramCourse.setYear(Integer.parseInt(interviewDefinitionForm.getYear()));
			}
			interviewProgramCourse.setName(interviewDefinitionForm.getInterviewType());
			interviewProgramCourse.setSequence(interviewDefinitionForm.getSequence());
			interviewProgramCourse.setContent(interviewDefinitionForm.getIntCardContent());
			interviewProgramCourse.setNoOfInterviewsPerPanel(Integer.parseInt(interviewDefinitionForm.getInterviewsPerPanel().trim()));
			if (mode.equalsIgnoreCase("Add")) {
				interviewProgramCourse.setCreatedDate(new Date());
				interviewProgramCourse.setCreatedBy(interviewDefinitionForm.getUserId());
			}
			interviewProgramCourse.setLastModifiedDate(new Date());
			interviewProgramCourse.setModifiedBy(interviewDefinitionForm.getUserId());
			interviewProgramCourse.setIsActive(true);
		}
		return interviewProgramCourse;
	}
}