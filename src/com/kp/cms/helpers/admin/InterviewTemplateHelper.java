package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.GroupTemplateInterview;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.InterviewTemplateForm;
import com.kp.cms.to.admin.GroupTemplateTO;
import com.kp.cms.to.admin.InterviewTemplateTo;

public class InterviewTemplateHelper {
	/**
	 * Singleton object of InterviewTemplateHelper
	 */
	private static volatile InterviewTemplateHelper interviewTemplateHelper = null;
	private static final Log log = LogFactory.getLog(InterviewTemplateHelper.class);
	private InterviewTemplateHelper() {
		
	}
	/**
	 * return singleton object of InterviewTemplateHelper.
	 * @return
	 */
	public static InterviewTemplateHelper getInstance() {
		if (interviewTemplateHelper == null) {
			interviewTemplateHelper = new InterviewTemplateHelper();
		}
		return interviewTemplateHelper;
	}
	/**
	 * @param interviewTemplateForm
	 * @param add
	 * @return
	 * @throws Exception
	 */
	public GroupTemplateInterview getGroupTemplateInterviewObject(
			InterviewTemplateForm interviewTemplateForm, String mode) throws Exception {
		log.debug("entering the getTemplateObject");
		GroupTemplateInterview groupTemplate = new GroupTemplateInterview();
		ProgramType programType = new ProgramType();
		if(interviewTemplateForm.getProgramTypeId() != null && !interviewTemplateForm.getProgramTypeId().trim().isEmpty()){
			programType.setId(Integer.valueOf(interviewTemplateForm.getProgramTypeId()));
			groupTemplate.setProgramType(programType);
		}
		Program program = new Program();
		if(interviewTemplateForm.getProgramId() != null && !interviewTemplateForm.getProgramId().trim().isEmpty()){
			program.setId(Integer.valueOf(interviewTemplateForm.getProgramId()));
			groupTemplate.setProgram(program);
		}
		Course course = new Course();
		if(interviewTemplateForm.getCourseId() != null && !interviewTemplateForm.getCourseId().trim().isEmpty() ){
			course.setId(Integer.valueOf(interviewTemplateForm.getCourseId()));
			groupTemplate.setCourse(course);
		}
		InterviewProgramCourse interviewProgramCourse=new InterviewProgramCourse();
		if(interviewTemplateForm.getInterviewId() != null && !interviewTemplateForm.getInterviewId().trim().isEmpty() ){
			interviewProgramCourse.setId(Integer.valueOf(interviewTemplateForm.getInterviewId()));
			groupTemplate.setInterviewProgramCourse(interviewProgramCourse);
		}
		
		InterviewSubRounds interviewSubRounds=new InterviewSubRounds();
		if(interviewTemplateForm.getInterviewSubroundId()!=null && !interviewTemplateForm.getInterviewSubroundId().isEmpty()){
			interviewSubRounds.setId(Integer.parseInt(interviewTemplateForm.getInterviewSubroundId()));
			groupTemplate.setInterviewSubRounds(interviewSubRounds);
		}
		
		groupTemplate.setTemplateName(interviewTemplateForm.getTemplateName());
		groupTemplate.setTemplateDescription(interviewTemplateForm.getTemplateDescription());
		if(mode.equalsIgnoreCase(CMSConstants.ADD)) {
			groupTemplate.setCreatedBy(interviewTemplateForm.getUserId());
			groupTemplate.setCreatedDate(new Date());
		}
		groupTemplate.setYear(Integer.parseInt(interviewTemplateForm.getAppliedYear()));
		groupTemplate.setLastModifiedDate(new Date());
		groupTemplate.setModifiedBy(interviewTemplateForm.getUserId());
		log.debug("leaving the getTemplateObject");
		return groupTemplate;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<InterviewTemplateTo> convertBOtoTO(
			List<GroupTemplateInterview> list) throws Exception {
		log.debug("entering the getTemplateObject");
		
		List<InterviewTemplateTo> groupTemplateTOList = new ArrayList<InterviewTemplateTo>();
		Iterator<GroupTemplateInterview> groupTemplateItr = list.iterator();
		
		while (groupTemplateItr.hasNext()) {
			GroupTemplateInterview groupTemplate = (GroupTemplateInterview) groupTemplateItr.next();
			
			InterviewTemplateTo groupTemplateTO = new InterviewTemplateTo();
			
			if(groupTemplate.getProgramType() != null){
				groupTemplateTO.setProgramTypeId(String.valueOf(groupTemplate.getProgramType().getId()));
				groupTemplateTO.setProgramTypeName(groupTemplate.getProgramType().getName());
			}else{
				groupTemplateTO.setProgramTypeName(CMSConstants.NA);
			}
			
			if(groupTemplate.getProgram() != null){
				groupTemplateTO.setProgramId(String.valueOf(groupTemplate.getProgram().getId()));
				groupTemplateTO.setProgramName(groupTemplate.getProgram().getName());
			}else{
				groupTemplateTO.setProgramName(CMSConstants.NA);
			}

			if(groupTemplate.getCourse() != null){
				groupTemplateTO.setCourseId(String.valueOf(groupTemplate.getCourse().getId()));
				groupTemplateTO.setCourseName(groupTemplate.getCourse().getName());
			}else{
				groupTemplateTO.setCourseName(CMSConstants.NA);
			}
			if(groupTemplate.getCourse() != null){
				groupTemplateTO.setCourseId(String.valueOf(groupTemplate.getCourse().getId()));
				groupTemplateTO.setCourseName(groupTemplate.getCourse().getName());
			}else{
				groupTemplateTO.setCourseName(CMSConstants.NA);
			}
			if(groupTemplate.getInterviewProgramCourse() != null){
				groupTemplateTO.setInterviewId(String.valueOf(groupTemplate.getInterviewProgramCourse().getId()));
				groupTemplateTO.setInterviewName(groupTemplate.getInterviewProgramCourse().getName());
			}else{
				groupTemplateTO.setInterviewName(CMSConstants.NA);
			}
			if(groupTemplate.getInterviewSubRounds() != null){
				groupTemplateTO.setInterviewSubRoundId(String.valueOf(groupTemplate.getInterviewSubRounds().getId()));
				groupTemplateTO.setInterviewSubRoundName(groupTemplate.getInterviewSubRounds().getName());
			}else{
				groupTemplateTO.setInterviewSubRoundName(CMSConstants.NA);
			}
			
			groupTemplateTO.setId(groupTemplate.getId());
			groupTemplateTO.setTemplateName(groupTemplate.getTemplateName());
			groupTemplateTO.setTemplateDescription(groupTemplate.getTemplateDescription());
			groupTemplateTO.setYear(Integer.toString(groupTemplate.getYear()));
			groupTemplateTOList.add(groupTemplateTO);
		}
		
		log.debug("leaving the getTemplateObject");
		return groupTemplateTOList;
	}
}
