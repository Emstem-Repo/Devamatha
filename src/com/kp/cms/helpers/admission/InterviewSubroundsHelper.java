package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.admission.InterviewSubroundsForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewSubroundsTO;
import com.kp.cms.transactions.admission.IInterviewSubroundsTransaction;
import com.kp.cms.transactionsimpl.admission.CopyInterviewDefinitionTransactionImpl;
import com.kp.cms.transactionsimpl.admission.InterviewSubroundsTransactionImpl;

public class InterviewSubroundsHelper {

	/**
	 * @param interviewDefinition
	 * @return TO
	 * @throws Exception
	 */
	public List<InterviewSubroundsTO> convertBOstoTOs(List<InterviewSubRounds> interviewSubroundsList) throws Exception {
	
		List<InterviewSubroundsTO> interviewSubroundsTOList = new ArrayList<InterviewSubroundsTO>();
		InterviewSubRounds interviewSubRound;
		InterviewSubroundsTO interviewSubroundsTO = null;
		ProgramTypeTO programTypeTO;
		ProgramTO programTO;
		CourseTO courseTO;
		InterviewProgramCourseTO interviewProgramCourseTO;
		
		if(interviewSubroundsList != null ){
			Iterator<InterviewSubRounds> iterator = interviewSubroundsList.iterator();
			while (iterator.hasNext()) {
				programTypeTO = new ProgramTypeTO();
				programTO = new ProgramTO();
				courseTO = new CourseTO();
				interviewProgramCourseTO = new InterviewProgramCourseTO();
				interviewSubroundsTO = new InterviewSubroundsTO();
			
				interviewSubRound = (InterviewSubRounds) iterator.next();
				
				interviewSubroundsTO.setId(interviewSubRound.getId());
				interviewSubroundsTO.setProgramTypeId(String.valueOf(interviewSubRound.getInterviewProgramCourse().getProgram().getProgramType().getId()));
				interviewSubroundsTO.setProgramId(String.valueOf(interviewSubRound.getInterviewProgramCourse().getProgram().getId()));
				interviewSubroundsTO.setCourseId(String.valueOf(interviewSubRound.getInterviewProgramCourse().getCourse().getId()));
				interviewSubroundsTO.setInterviewTypeId(String.valueOf(interviewSubRound.getInterviewProgramCourse().getId()));
				interviewSubroundsTO.setInterviewTypeName(interviewSubRound.getInterviewProgramCourse().getName());
				interviewSubroundsTO.setName(interviewSubRound.getName());
				programTypeTO.setProgramTypeId(interviewSubRound.getInterviewProgramCourse().getCourse().getProgram().getProgramType().getId());
				programTO.setProgramTypeTo(programTypeTO);
				programTO.setId(interviewSubRound.getInterviewProgramCourse().getCourse().getProgram().getId());
				courseTO.setProgramTo(programTO);
				courseTO.setId(interviewSubRound.getInterviewProgramCourse().getCourse().getId());
				courseTO.setName(interviewSubRound.getInterviewProgramCourse().getCourse().getName());
				interviewProgramCourseTO.setCourse(courseTO);
				if(interviewSubRound.getNoOfInterviewsPerPanel() != null){
					interviewProgramCourseTO.setInterviewsPerPanel(String.valueOf(interviewSubRound.getNoOfInterviewsPerPanel()));
				}
				interviewSubroundsTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
				interviewSubroundsTO.setAppliedYear(interviewSubRound.getInterviewProgramCourse().getYear());
				interviewSubroundsTOList.add(interviewSubroundsTO);
			}
		}
		return interviewSubroundsTOList;
	}
	
	/**
	 * 
	 * @param interviewSubroundsForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public InterviewSubRounds convertBOs(InterviewSubroundsForm interviewSubroundsForm, String mode) throws Exception {
		InterviewSubRounds interviewSubRound = null;
		if(interviewSubroundsForm != null ){
			interviewSubRound = new InterviewSubRounds();
			
			interviewSubRound.setId(interviewSubroundsForm.getId());
			interviewSubRound.setName(interviewSubroundsForm.getSubroundName());
			InterviewProgramCourse interviewDefinition = new InterviewProgramCourse();
			if( interviewSubroundsForm.getInterviewTypeId() !=  null){
				interviewDefinition.setId(Integer.parseInt(interviewSubroundsForm.getInterviewTypeId()));
			}
			interviewSubRound.setNoOfInterviewsPerPanel(Integer.parseInt(interviewSubroundsForm.getInterviewsPerPanel().trim()));
			interviewSubRound.setInterviewProgramCourse(interviewDefinition);
			if (mode.equalsIgnoreCase("Add")) {
				interviewSubRound.setCreatedDate(new Date());
				interviewSubRound.setCreatedBy(interviewSubroundsForm.getUserId());
			}
			interviewSubRound.setModifiedBy(interviewSubroundsForm.getUserId());
			interviewSubRound.setLastModifiedDate(new Date());
			interviewSubRound.setIsActive(true);
		}
		return interviewSubRound;
	}

	/**
	 * filtering the list obtained from BO to a particular FromYear and also converting the BO to TO
	 * @param interviewSubRounds
	 * @return
	 * @throws Exception
	 */
	public List<InterviewSubroundsTO> convertBOstoSubRoundsTOs(List<InterviewSubRounds> interviewSubRounds,int currentYear) throws Exception {

		List<InterviewSubroundsTO> intlist = new ArrayList<InterviewSubroundsTO>();
		if(interviewSubRounds!=null && !interviewSubRounds.isEmpty()){
		Iterator<InterviewSubRounds> iter = interviewSubRounds.iterator();
		InterviewSubroundsTO intTO;
		while (iter.hasNext()) {
			InterviewSubRounds interview = (InterviewSubRounds) iter.next();
			intTO = new InterviewSubroundsTO();
		if(interview.getInterviewProgramCourse().getYear().toString().equalsIgnoreCase(String.valueOf(currentYear))){
				
			if(interview.getInterviewProgramCourse()!=null && interview.getInterviewProgramCourse().getCourse()!=null 
					&& interview.getInterviewProgramCourse().getCourse().getName()!=null){
			intTO.setCourseName(interview.getInterviewProgramCourse().getCourse().getName());
			intTO.setCourseId(String.valueOf(interview.getInterviewProgramCourse().getCourse().getId()));
			}
			if(interview.getInterviewProgramCourse()!=null && interview.getInterviewProgramCourse().getYear()!=null){
				intTO.setYear(interview.getInterviewProgramCourse().getYear().toString());
				intTO.setInterviewTypeId(interview.getInterviewProgramCourse().getId().toString());
				intTO.setSequence(interview.getInterviewProgramCourse().getSequence());
			}
			
			if(interview.getInterviewProgramCourse()!=null && interview.getInterviewProgramCourse().getName()!=null){
				intTO.setInterviewTypeName(interview.getInterviewProgramCourse().getName());
			}
			if(interview.getName()!=null){
				intTO.setName(interview.getName());
			}
			if(interview.getNoOfInterviewsPerPanel()!=null ){
				intTO.setNoOfInterviewersPerPanel(interview.getNoOfInterviewsPerPanel());
			}
			
			String year1 = String.valueOf((interview.getInterviewProgramCourse().getYear()) + 1);
			intTO.setCombinedYear(String.valueOf(interview.getInterviewProgramCourse().getYear().toString())+"-"+year1);
			intlist.add(intTO);
			}
		}
	}
		return intlist;
	}

	/**
	 * converting TO to BO to save to the database
	 * @param copyForm
	 * @return
	 * @throws Exception
	 */
	public List<InterviewSubRounds> convertTOToBO(InterviewSubroundsForm copyForm) throws Exception {

		List<InterviewSubRounds> intSubRoundBOList= new ArrayList<InterviewSubRounds>();
		List<InterviewSubroundsTO> dispList = copyForm.getInterviewSubroundsList();
		for(InterviewSubroundsTO disp:dispList){
			if(disp.isIntDefinitionSel())
			{
				
						
						int year=Integer.parseInt(copyForm.getToYear());
						String query=" from InterviewSubRounds interviewSubRounds where interviewSubRounds.interviewProgramCourse.course.id=" +disp.getCourseId()+
								" and interviewSubRounds.interviewProgramCourse.year= " +year+
								" and interviewSubRounds.interviewProgramCourse.sequence=" +disp.getSequence()+
								"and interviewSubRounds.name= '"+disp.getName()+"'"; 
						
						boolean isDup;
						IInterviewSubroundsTransaction transaction= new InterviewSubroundsTransactionImpl();
						isDup= transaction.checkDuplicate(query);
						if(!isDup){
						String intPrgCourseId=transaction.getInterviewProgramCourseId(disp.getCourseId(),year,disp.getSequence());
							if(intPrgCourseId!=null && !intPrgCourseId.isEmpty()){
								InterviewSubRounds intSubRounds= new InterviewSubRounds();
								InterviewProgramCourse intPrgCourse=new InterviewProgramCourse();
								intPrgCourse.setId(Integer.parseInt(intPrgCourseId));
								intSubRounds.setInterviewProgramCourse(intPrgCourse);
								intSubRounds.setIsActive(true);
								intSubRounds.setCreatedBy(copyForm.getUserId());
								intSubRounds.setCreatedDate(new Date());
								intSubRounds.setModifiedBy(copyForm.getUserId());
								intSubRounds.setLastModifiedDate(new Date());
								intSubRounds.setName(disp.getName());
								intSubRounds.setNoOfInterviewsPerPanel(Integer.valueOf(disp.getNoOfInterviewersPerPanel()));
								
								intSubRoundBOList.add(intSubRounds);
							}
						}
				
			}
		}
		return intSubRoundBOList;
	}
}
